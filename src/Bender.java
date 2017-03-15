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
        char direccio = 'S';
        boolean invers = false;
        int[] posicio = posicioX;
        while (map[posicio[0]][posicio[1]] != '$'){
            if (direccio == 'S'){
                if (map[posicio[0] + 1][posicio[1]] != '#'){
                    ruta += "S";
                    posicio[0]++;
                }else direccio = camviDireccio(invers, posicio);
            }else if (direccio == 'N'){
                if (map[posicio[0] - 1][posicio[1]] != '#'){
                    ruta += "N";
                    posicio[0]--;
                }else direccio = camviDireccio(invers, posicio);
            }else if (direccio == 'E'){
                if (map[posicio[0]][posicio[1] + 1] != '#'){
                    ruta += "E";
                    posicio[1]++;
                }else direccio = camviDireccio(invers, posicio);
            }else if (direccio == 'W'){
                if (map[posicio[0]][posicio[1] - 1] != '#'){
                    ruta += "W";
                    posicio[1]--;
                }else direccio = camviDireccio(invers, posicio);
            }
            if (map[posicio[0]][posicio[1]] == 'I'){
                if (invers == false) {
                    invers = true;
                    }else invers = false;
                }
            }
        return ruta;
    }

    private char camviDireccio(boolean i, int[] posicio) {
        Queue<Character> dir = new LinkedList<>();
        if (!i){
            Collections.addAll(dir,'S','E','N','W');
        } else Collections.addAll(dir,'N','W','S','E');

        for (char p:dir) {
            if (p == 'S'){
                if (map[posicio[0] + 1][posicio[1]] != '#'){
                    return p;
                }
            }else if (p == 'N'){
                if (map[posicio[0] - 1][posicio[1]] != '#'){
                    return p;
                }
            }else if (p == 'E'){
                if (map[posicio[0]][posicio[1] + 1] != '#'){
                    return p;
                }
            }else if (p == 'W') {
                if (map[posicio[0]][posicio[1] - 1] != '#') {
                    return p;
                }
            }
        }
        return 'S';
    }
}