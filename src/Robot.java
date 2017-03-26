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
    int[] posicioT;
    //pasos donats fins al desti (s'hutilitza a bestRun)
    int pasos = 1;
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
            posicioT = map.posicioT;
        }
        //funcio on es retorna el cami recorregut amb direccions predefinides
        String run (){
            //bucle que s'executa fins que s'arriva al desti
            while (map.m[posicio[0]][posicio[1]] != '$') {
                //si debbugger es true es crida a la funcio deb
                if (debugger)deb();
                //si no hi ha X es retorna null
                if (map.posicioX[0] == 0 && map.posicioX[1] == 0) return null;
                //si la cua de direccions es vuida es genera una nova
                if (dir.isEmpty()) dir = novaQeue(invers);
                //es mira la direccio que es du i es comproba si es pot avançar en dita direccio, en cas afirmatiu es cambia la posicio i
                //s'afegeix la direccio a la ruta.
                //en cas contrari es genera una nova cua i es comprova a quina direccio es pot avançar.
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
                //si pasam per una I camviam l'ordre de direccions i generam la nova cua
                if (map.m[posicio[0]][posicio[1]] == 'I' && !empleat) {
                    if (invers == false) {
                        invers = true;
                    } else invers = false;
                    dir = novaQeue(invers);
                    direccio = dir.poll();
                    empleat = true;
                }
                //si pasam per una T cridam la funcio tele per mourernos a l'altre T
                if (map.m[posicio[0]][posicio[1]] == 'T' && !empleat) {
                    posicio = tele(this.posicio);
                    empleat = true;
                }
                //si no em arribat al desti una vegada fet tantes pases com posicions te el mapa retornam null
                if (ruta.length() >= map.tamanyMaxX*map.tamanyMaxY) return null;
            }
            //si dibuixar es true cridam la funcio benderArt
            if (dibuixar) benderArt();
            //si em arrivat al desti retornam la ruta
            return ruta;
        }
    //funcio que genera una nova cua depenguent de si es la cua normal o la inversa
    private Queue<Character> novaQeue(boolean i) {
        Queue<Character> dir = new LinkedList<>();
        if (!i) {
            Collections.addAll(dir, 'S', 'E', 'N', 'W');
        } else Collections.addAll(dir, 'N', 'W', 'S', 'E');
        return dir;
    }
    //funcio que mou la posicio de una T a l'altre
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
    //funcio per a camviar de direccio seguint l'ordre que dona la cua de direccions
    private char camviDireccio(Queue<Character> dir, int[] posicio) {
        for (int i = 0; i < 4; i++) {
            if (dir.element() == 'S') {
                if (map.m[posicio[0] + 1][posicio[1]] != '#') return dir.poll();
                else dir.remove();
            } else if (dir.element() == 'N') {
                if (posicio[0] - 1 >=0 && map.m[posicio[0] - 1][posicio[1]] != '#') return dir.poll();
                else dir.remove();
            } else if (dir.element() == 'E') {
                if (map.m[posicio[0]][posicio[1] + 1] != '#') return dir.poll();
                else dir.remove();
            } else {
                if (posicio[1] - 1 >=0 && map.m[posicio[0]][posicio[1] - 1] != '#') return dir.poll();
                else dir.remove();
            }
        }
      return 'S';
    }


    //Funcio per trobar el cami mes optim al desti
    public int bestRun() {
        //cream un mapa numeric del tamany del mapa original
        int[][] mapaNumeric = new int[map.tamanyMaxY][map.tamanyMaxX];
        //mentres no arrivem al desti continuam numerant el mapa
        while (mapaNumeric[posicio$[0]][posicio$[1]] == 0) {
            enumerarMapa(mapaNumeric, pasos);
            pasos++;
            if (debugger)deb(mapaNumeric);
        }
        //retornam les pases nesesaries per arribar al desti
        return pasos;
    }


    private void enumerarMapa(int[][] mapaNumeric, int contador) {
        int[] posT = posicioT;
        boolean empleat = false;
        //posam valor als cuadres de devora la X
        if (contador == 1){
            if (this.posicioX[0] + 1 < map.tamanyMaxY && map.m[posicioX[0] + 1][posicioX[1]] != '#') mapaNumeric[posicioX[0] + 1][posicioX[1]] = pasos;
            if (this.posicioX[1] + 1 < map.tamanyMaxX && map.m[posicioX[0]][posicioX[1] + 1] != '#') mapaNumeric[posicioX[0]][posicioX[1] + 1] = pasos;
            if (this.posicioX[0] - 1 > 0 && map.m[posicioX[0] - 1][posicioX[1]] != '#') mapaNumeric[posicioX[0] - 1][posicioX[1]] = pasos;
            if (this.posicioX[1] - 1 > 0 && map.m[posicioX[0]][posicioX[1] - 1] != '#') mapaNumeric[posicioX[0]][posicioX[1] - 1] = pasos;
        }
        //bucle anidat per recorre tot el mapa
        for (int i = 0; i < map.tamanyMaxY; i++) {
            for (int j = 0; j < map.tamanyMaxX; j++) {
                if (i == posicioX[0] && j == posicioX[1] || map.m[i][j] == '#') continue;
                //if per tenir en compte el teletransport
                if (!empleat && mapaNumeric[posT[0]][posT[1]] > 0) {
                    mapaNumeric[posT[2]][posT[3]] = mapaNumeric[posT[0]][posT[1]];
                    empleat = true;
                //numeram els quadrats abjacents als numerats
                } else {
                    if (i + 1 < map.tamanyMaxY && map.m[i + 1][j] != '#' && mapaNumeric[i][j] == contador)
                        mapaNumeric[i + 1][j] = mapaNumeric[i][j] + 1;
                    if (j + 1 < map.tamanyMaxX && map.m[i][j + 1] != '#' && mapaNumeric[i][j] == contador)
                        mapaNumeric[i][j + 1] = mapaNumeric[i][j] + 1;
                    if (i - 1 > 0 && map.m[i - 1][j] != '#' && mapaNumeric[i][j] == contador)
                        mapaNumeric[i - 1][j] = mapaNumeric[i][j] + 1;
                    if (j - 1 > 0 && map.m[i][j - 1] != '#' && mapaNumeric[i][j] == contador)
                        mapaNumeric[i][j - 1] = mapaNumeric[i][j] + 1;
                }
            }

        }
    }

    private void deb() {
            for (int i = 0; i < map.m.length; i++) {
                for (int j = 0; j < map.m[i].length; j++) {
                    if (i == posicio[0] && j == posicio[1]){
                        System.out.print('B');
                    }else System.out.print(map.m[i][j]);
                }
                System.out.println("");
            }
    }

    private void deb(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i == posicio[0] && j == posicio[1]){
                    System.out.print(Text.ANSI_GREEN + 'X' + Text.ANSI_RESET);
                }else if(i == posicio$[0] && j == posicio$[1]){
                    System.out.print(Text.ANSI_RED + '$' + Text.ANSI_RESET);
                }else if(this.map.m[i][j] == 'T'){
                    System.out.print(Text.ANSI_PURPLE + 'T' + Text.ANSI_RESET);
                }else if(this.map.m[i][j] == '#'){
                    System.out.print(Text.ANSI_YELLOW + '#' + Text.ANSI_RESET);
                }
                else System.out.print(map[i][j]);
            }
            System.out.println("");
        }
        System.out.println(Text.ANSI_BLUE + pasos + Text.ANSI_RESET);
    }

    //funcio que dibuixa a bender en ascii art
    private void benderArt(){
        System.out.println("      _\n" + "     ( )\n" + "      H\n" + "      H\n" + "     _H_ \n" +
                "  .-'-.-'-.\n" + " /         \\\n" + "|           |\n" + "|   .-------'._\n" +
                "|  / /  '.' '. \\\n" + "|  \\ \\ @   @ / / \n" + "|   '---------'        \n" +
                "|    _______|  \n" + "|  .'-+-+-+|  \n" + "|  '.-+-+-+|         \n" +
                "|    \"\"\"\"\"\" |\n" + "'-.__   __.-'\n" + "     \"\"\"  \n");
    }

}


