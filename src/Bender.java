import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by jmuletr on 15/03/2017.
 */
class Bender {
    char[][] map;
    int[] posicioX = new int[2];
    int tamanyX = 0;
    int tamanyY = 0;

    // Constructor: ens passen el mapa en forma d'String
    public Bender(String mapa) {
        String[] temp = mapa.split("\\n");
        tamanyY = temp.length;
        tamanyX = temp[0].length();
        map = new char[tamanyY][tamanyX];
        for (int i = 0; i < tamanyY; i++) {
            for (int j = 0; j < tamanyX; j++) {
                map[i][j] = temp[i].charAt(j);
                if (temp[i].charAt(j) == 'X') {
                    posicioX[0] = i;
                    posicioX[1] = j;
                }
            }
        }
    }


    // Navegar fins a l'objectiu («$»).
// El valor retornat pel mètode consisteix en una cadena de
// caràcters on cada lletra pot tenir els valors «S», «N», «W» o «E»,
// segons la posició del robot a cada moment.
    /*S (South), E (East), N (North), W (West)
    * inversa N (North), W (West), S (South), E (East)*/
    public String run() {
        String ruta = "";
        boolean invers = false;
        boolean empleat = false;
        Queue<Character> dir = novaQeue(invers);
        char direccio = dir.poll();
        int[] posicio = posicioX;
        while (map[posicio[0]][posicio[1]] != '$') {
            if (dir.isEmpty()) dir = novaQeue(invers);
            if (direccio == 'S') {
                if (map[posicio[0] + 1][posicio[1]] != '#') {
                    ruta += "S";
                    posicio[0]++;
                    empleat = false;
                } else direccio = dir.poll();
            } else if (direccio == 'N') {
                if (map[posicio[0] - 1][posicio[1]] != '#') {
                    ruta += "N";
                    posicio[0]--;
                    empleat = false;
                } else direccio = dir.poll();
            } else if (direccio == 'E') {
                if (map[posicio[0]][posicio[1] + 1] != '#') {
                    ruta += "E";
                    posicio[1]++;
                    empleat = false;
                } else direccio = dir.poll();
            } else if (direccio == 'W') {
                if (map[posicio[0]][posicio[1] - 1] != '#') {
                    ruta += "W";
                    posicio[1]--;
                    empleat = false;
                } else direccio = dir.poll();
            }
            if (map[posicio[0]][posicio[1]] == 'I' && empleat == false) {
                if (invers == false) {
                    invers = true;
                } else invers = false;
                dir = novaQeue(invers);
                direccio = dir.poll();
                empleat = true;
            }
            if (map[posicio[0]][posicio[1]] == 'T') {
                posicio = tele(posicio);
            }
        }
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
        for (int i = 0; i < tamanyY; i++) {
            for (int j = 0; j < tamanyX; j++) {
                if (map[i][j] == 'T' && (i != posicio[0] && j != posicio[1])) {
                    pos[0] = i;
                    pos[1] = j;
                }
            }
        }
        return pos;
    }

//    private char camviDireccio(Queue<Character> dir, int[] posicio) {
//
//
//        if (dir.element() == 'S') {
//            if (map[posicio[0] + 1][posicio[1]] != '#') {
//                return dir.poll();
//            }
//        } else if (dir.element() == 'N') {
//            if (map[posicio[0] - 1][posicio[1]] != '#') {
//                return dir.poll();
//            }
//        } else if (dir.element() == 'E') {
//            if (map[posicio[0]][posicio[1] + 1] != '#') {
//                return dir.poll();
//            }
//        } else if (dir.element() == 'W') {
//            if (map[posicio[0]][posicio[1] - 1] != '#') {
//                return dir.poll();
//            }
//        }
//
//        return 'S';
//    }
}