import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by jmuletr on 16/03/2017.
 */
public class Robot {
    //objecte mapa per on es moura el robot
    Mapa map;
    //cami que ha seguit
    String ruta = "";
    //variables per coneixer si s'ha utilitzat un teleport o un inversor i per coneixer si l'inversor esta actiu
    boolean invers = false;
    boolean empleat = false;
    //cua de direccions
    Queue<Character> dir = novaQeue(invers);
    //direccio actual
    char direccio = dir.poll();
    //posicio actual del robot
    int[] posicio = new int[2];
    //posicio de x i $ (s'hutilitzen a bestRun)
    int[] posicioX;
    int[] posicio$;
    //pasos donats fins al desti (s'hutilitza a bestRun)
    int pasos = 0;
    //variable per calcular la direccio que prendra el robot (s'hutilitza a bestRun)
    int heuristica;
    //variable per activar o desactivar la funcio benderArt que dibuixa al robot bender en ascii art
    boolean dibuixar;
    boolean debugger;
        //constructor de robot on s'inicialitzen i asignen totes les variables
        Robot(Mapa map, boolean dib, boolean debug){
            posicio[0]  = map.posicioX[0];
            posicio[1]  = map.posicioX[1];
            posicioX = map.posicioX;
            posicio$ = map.posicio$;
            this.map = map;
            dibuixar = dib;
            debugger = debug;
        }

        String run (){
            while (map.m[posicio[0]][posicio[1]] != '$') {
                deb(debugger);
                if (map.posicioX[0] == 0 && map.posicioX[1] == 0) return null;
                if (dir.isEmpty()) dir = novaQeue(invers);
                if (direccio == 'S') {
                    if (map.m[posicio[0] + 1][posicio[1]] != '#') {
                        ruta += "S";
                        posicio[0]++;
                        empleat = false;
                    } else {
                        dir = novaQeue(invers);
                        direccio = camviDireccio(dir, posicio);
                    }
                } else if (direccio == 'N') {
                    if (posicio[0] - 1 >=0 && map.m[posicio[0] - 1][posicio[1]] != '#') {
                        ruta += "N";
                        posicio[0]--;
                        empleat = false;
                    } else {
                        dir = novaQeue(invers);
                        direccio = camviDireccio(dir, posicio);
                    }
                } else if (direccio == 'E') {
                    if (map.m[posicio[0]][posicio[1] + 1] != '#') {
                        ruta += "E";
                        posicio[1]++;
                        empleat = false;
                    } else {
                        dir = novaQeue(invers);
                        direccio = camviDireccio(dir, posicio);
                    }
                } else if (direccio == 'W') {
                    if (posicio[1] - 1 >=0 && map.m[posicio[0]][posicio[1] - 1] != '#') {
                        ruta += "W";
                        posicio[1]--;
                        empleat = false;
                    } else {
                        dir = novaQeue(invers);
                        direccio = camviDireccio(dir, posicio);
                    }
                }
                if (map.m[posicio[0]][posicio[1]] == 'I' && !empleat) {
                    if (invers == false) {
                        invers = true;
                    } else invers = false;
                    dir = novaQeue(invers);
                    direccio = dir.poll();
                    empleat = true;
                }
                if (map.m[posicio[0]][posicio[1]] == 'T' && !empleat) {
                    posicio = tele(this.posicio);
                    empleat = true;
                }
                if (ruta.length() >= map.tamanyMaxX*map.tamanyMaxY) return null;
            }
            if (dibuixar) benderArt();
            return ruta;
        }

    private Queue<Character> novaQeue(boolean i) {
        Queue<Character> dir = new LinkedList<>();
        if (!i) {
            Collections.addAll(dir, 'S', 'E', 'N', 'W');
        } else Collections.addAll(dir, 'N', 'W', 'S', 'E');
        return dir;
    }

    private int[] tele(int[] posicio) {
        int[] pos = new int[2];
        for (int i = 0; i < map.tamanyMaxY; i++) {
            for (int j = 0; j < map.tamanyMaxX; j++) {
                if (map.m[i][j] == 'T' && (i != posicio[0] || j != posicio[1])) {
                    pos[0] = i;
                    pos[1] = j;
                }
            }
        }
        return pos;
    }

    private char camviDireccio(Queue<Character> dir, int[] posicio) {
        for (int i = 0; i < 4; i++) {
            if (dir.element() == 'S') {
                if (map.m[posicio[0] + 1][posicio[1]] != '#') {
                    return dir.poll();
                } else dir.remove();
            } else if (dir.element() == 'N') {
                if (posicio[0] - 1 >=0 && map.m[posicio[0] - 1][posicio[1]] != '#') {
                    return dir.poll();
                } else dir.remove();
            } else if (dir.element() == 'E') {
                if (map.m[posicio[0]][posicio[1] + 1] != '#') {
                    return dir.poll();
                } else dir.remove();
            } else if (dir.element() == 'W') {
                if (posicio[1] - 1 >=0 && map.m[posicio[0]][posicio[1] - 1] != '#') {
                    return dir.poll();
                } else dir.remove();
            }
        }
        return 'S';
    }


    //A partir d'aquest punt es el codi per als segons test (no esta complet nomes pasa els dos primers mapes)
    int bestRun() {
        while (map.m[posicio[0]][posicio[1]] != '$') {
            deb(debugger);
            if (posicio[0] == posicioX[0] && posicio[1] == posicioX[1]){
                pasos = 0;
            }
            if (map.posicioX[0] == 0 && map.posicioX[1] == 0) return 0;
            if (dir.isEmpty()) dir = novaQeue(invers);
            direccio = calcDireccio(posicio, posicio$);
            if (direccio == 'S') {
                if (map.m[posicio[0] + 1][posicio[1]] != '#' && map.m[posicio[0] + 1][posicio[1]] != 'P') {
                    pasos++;
                    posicio[0]++;
                    empleat = false;
                }else {
                    map.replace(posicio[0] + 1, posicio[1], '#');
                    if(posicio[0] == posicioX[0] && posicio[1] == posicioX[1]){
                        pasos = 0;
                        posicio[0] = posicioX[0];
                        posicio[1] = posicioX[1];
                    }
                }
            } else if (direccio == 'N') {
                if (posicio[0] - 1 >=0 && map.m[posicio[0] - 1][posicio[1]] != '#' && posicio[0] - 1 >=0 && map.m[posicio[0] - 1][posicio[1]] != 'P') {
                    pasos++;
                    posicio[0]--;
                    empleat = false;
                }else {
                    map.replace(posicio[0] - 1, posicio[1], '#');
                    if(posicio[0] == posicioX[0] && posicio[1] == posicioX[1]){
                        pasos = 0;
                        posicio[0] = posicioX[0];
                        posicio[1] = posicioX[1];
                    }
                }
            } else if (direccio == 'E') {
                if (map.m[posicio[0]][posicio[1] + 1] != '#' && map.m[posicio[0]][posicio[1] + 1] != 'P') {
                    pasos++;
                    posicio[1]++;
                    empleat = false;
                }else {
                    map.replace(posicio[0], posicio[1] + 1,'#');
                    if(posicio[0] == posicioX[0] && posicio[1] == posicioX[1]){
                        pasos = 0;
                        posicio[0] = posicioX[0];
                        posicio[1] = posicioX[1];
                    }
                }
            } else if (direccio == 'W') {
                if (posicio[1] - 1 >=0 && map.m[posicio[0]][posicio[1] - 1] != '#' && map.m[posicio[0]][posicio[1] - 1] != 'P') {
                    pasos++;
                    posicio[1]--;
                    empleat = false;
                }else {
                    map.replace(posicio[0],posicio[1] - 1, '#');
                    if(posicio[0] == posicioX[0] && posicio[1] == posicioX[1]){
                        pasos = 0;
                        posicio[0] = posicioX[0];
                        posicio[1] = posicioX[1];
                    }
                }
            }
            if (map.m[posicio[0]][posicio[1]] == 'T' && !empleat) {
                posicio = tele(this.posicio);
                empleat = true;
            }
            if (pasos >= map.tamanyMaxX*map.tamanyMaxY) return 0;
            map.replace(posicio[0], posicio[1],'P');
        }
        if (dibuixar) benderArt();
        return pasos;
    }

    private void deb(boolean d) {
        if (d){
            for (int i = 0; i < map.m.length; i++) {
                for (int j = 0; j < map.m[i].length; j++) {
                    if (i == posicio[0] && j == posicio[1]){
                        System.out.print('B');
                    }else System.out.print(map.m[i][j]);
                }
                System.out.println("");
            }
            System.out.println(pasos);
        }
    }

    private char calcDireccio(int[] posicio, int[] posicio$) {
        heuristica = 0;
        int p[] = new int[2];
        char dir = 'S';
            if (map.m[posicio[0] + 1][posicio[1]] != '#' && map.m[posicio[0] + 1][posicio[1]] != 'P') {
                p[0] = posicio[0] + 1;
                p[1] = posicio[1];
                if (heuristica > calcHeuristica(posicio$,p) || heuristica == 0) {
                    dir = 'S';
                    heuristica = calcHeuristica(posicio$, p);
                }
            }

            if (posicio[0] - 1 >=0 && map.m[posicio[0] - 1][posicio[1]] != '#'&& posicio[0] - 1 >=0 && map.m[posicio[0] - 1][posicio[1]] != 'P') {
                p[0] = posicio[0] - 1;
                p[1] = posicio[1];
                if (heuristica > calcHeuristica(posicio$,p) || heuristica == 0){
                    dir = 'N';
                    heuristica = calcHeuristica(posicio$,p);
                }
            }

            if (map.m[posicio[0]][posicio[1] + 1] != '#' && map.m[posicio[0]][posicio[1] + 1] != 'P') {
                p[0] = posicio[0];
                p[1] = posicio[1] + 1;
                if (heuristica > calcHeuristica(posicio$,p) || heuristica == 0){
                    dir =  'E';
                    heuristica = calcHeuristica(posicio$,p);
                }
            }

            if (posicio[1] - 1 >=0 && map.m[posicio[0]][posicio[1] - 1] != '#' && map.m[posicio[0]][posicio[1] - 1] != 'P') {
                p[0] = posicio[0];
                p[1] = posicio[1] - 1;
                if (heuristica > calcHeuristica(posicio$,p) || heuristica == 0){
                    dir = 'W';
                    heuristica = calcHeuristica(posicio$,p);
                }
            }
        return dir;
    }

    private int calcHeuristica (int[] posicio$, int[] posicio){
        int pos0, pos1;
        if (posicio$[0] > posicio[0]){
            pos0 = posicio$[0] - posicio[0];
        } else pos0 = posicio[0] - posicio$[0];
        if (posicio$[1] > posicio[1]){
            pos1 = posicio$[1] - posicio[1];
        } else pos1 = posicio[1] - posicio$[1];
        return pos0 + pos1;
    }

    private void benderArt(){
        System.out.println("      _\n" + "     ( )\n" + "      H\n" + "      H\n" + "     _H_ \n" +
                "  .-'-.-'-.\n" + " /         \\\n" + "|           |\n" + "|   .-------'._\n" +
                "|  / /  '.' '. \\\n" + "|  \\ \\ @   @ / / \n" + "|   '---------'        \n" +
                "|    _______|  \n" + "|  .'-+-+-+|  \n" + "|  '.-+-+-+|         \n" +
                "|    \"\"\"\"\"\" |\n" + "'-.__   __.-'\n" + "     \"\"\"  \n");
    }

}


