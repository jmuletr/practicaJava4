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
    boolean dibuixar;
        Robot(Mapa map, boolean dib){
            posicio  = map.posicioX;
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

    private void benderArt(){
        System.out.println("      _\n" + "     ( )\n" + "      H\n" + "      H\n" + "     _H_ \n" +
                "  .-'-.-'-.\n" + " /         \\\n" + "|           |\n" + "|   .-------'._\n" +
                "|  / /  '.' '. \\\n" + "|  \\ \\ @   @ / / \n" + "|   '---------'        \n" +
                "|    _______|  \n" + "|  .'-+-+-+|  \n" + "|  '.-+-+-+|         \n" +
                "|    \"\"\"\"\"\" |\n" + "'-.__   __.-'\n" + "     \"\"\"  \n");
    }
}


