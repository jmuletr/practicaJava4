import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by jmuletr on 16/03/2017.
 */
public class Robot {
    Mapa map;
    String ruta = "";
    boolean invers = false;
    boolean empleat = false;
    Queue<Character> dir = novaQeue(invers);
    char direccio = dir.poll();
    int[] posicio;
    int[] posicioX;
    int[] posicio$;
    int pasos = 0;
    int heuristica;
    boolean dibuixar;
        Robot(Mapa map, boolean dib){
            posicio  = map.posicioX;
            posicioX = map.posicioX;
            posicio$ = map.posicio$;
            this.map = map;
            dibuixar = dib;
        }

        String run (){
            while (map.m[posicio[0]][posicio[1]] != '$') {
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

    int bestRun() {
        while (map.m[posicio[0]][posicio[1]] != '$') {
            System.out.println(pasos);
            if (map.posicioX[0] == 0 && map.posicioX[1] == 0) return 0;
            if (dir.isEmpty()) dir = novaQeue(invers);
            direccio = calcDireccio(posicio, posicio$);
            if (direccio == 'S') {
                if (map.m[posicio[0] + 1][posicio[1]] != '#') {
                    pasos++;
                    posicio[0]++;
                    empleat = false;
                }else {
                    map.replace(posicio);
                    pasos = 0;
                    posicio = posicioX;
                }
            } else if (direccio == 'N') {
                if (posicio[0] - 1 >=0 && map.m[posicio[0] - 1][posicio[1]] != '#') {
                    pasos++;
                    posicio[0]--;
                    empleat = false;
                }else {
                    map.replace(posicio);
                    pasos = 0;
                    posicio = posicioX;
                }
            } else if (direccio == 'E') {
                if (map.m[posicio[0]][posicio[1] + 1] != '#') {
                    pasos++;
                    posicio[1]++;
                    empleat = false;
                }else {
                    map.replace(posicio);
                    pasos = 0;
                    posicio = posicioX;
                }
            } else if (direccio == 'W') {
                if (posicio[1] - 1 >=0 && map.m[posicio[0]][posicio[1] - 1] != '#') {
                    pasos++;
                    posicio[1]--;
                    empleat = false;
                }else {
                    map.replace(posicio);
                    pasos = 0;
                    posicio = posicioX;
                }
            }
            if (map.m[posicio[0]][posicio[1]] == 'T' && !empleat) {
                posicio = tele(this.posicio);
                empleat = true;
            }
            if (pasos >= map.tamanyMaxX*map.tamanyMaxY) return 0;
        }
        if (dibuixar) benderArt();
        return pasos;
    }

    private char calcDireccio(int[] posicio, int[] posicio$) {
        heuristica = 0;
        int p[] = new int[2];
        char dir = 'S';
            if (map.m[posicio[0] + 1][posicio[1]] != '#') {
                p[0] = posicio[0] + 1;
                p[1] = posicio[1];
                if (heuristica > calcHeuristica(posicio$,p) || heuristica == 0) {
                    dir = 'S';
                    heuristica = calcHeuristica(posicio$, p);
                }
            }

            if (posicio[0] - 1 >=0 && map.m[posicio[0] - 1][posicio[1]] != '#') {
                p[0] = posicio[0] - 1;
                p[1] = posicio[1];
                if (heuristica > calcHeuristica(posicio$,p) || heuristica == 0){
                    dir = 'N';
                    heuristica = calcHeuristica(posicio$,p);
                }
            }

            if (map.m[posicio[0]][posicio[1] + 1] != '#') {
                p[0] = posicio[0];
                p[1] = posicio[1] + 1;
                if (heuristica > calcHeuristica(posicio$,p) || heuristica == 0){
                    dir =  'E';
                    heuristica = calcHeuristica(posicio$,p);
                }
            }

            if (posicio[1] - 1 >=0 && map.m[posicio[0]][posicio[1] - 1] != '#') {
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


