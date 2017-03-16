/**
 * Created by jmuletr on 16/03/2017.
 */
public class Mapa {
    char[][] m;
    int[] posicioX = new int[2];
    int tamanyMaxX = 0;
    int tamanyMaxY = 0;

    Mapa(String mapa){
        String[] temp = mapa.split("\\n");
        tamanyMaxY = temp.length;
        for (int i = 0; i < tamanyMaxY; i++) {
            if (temp[i].length() > tamanyMaxX) {
                tamanyMaxX = temp[i].length();
            }
        }
        m = new char[tamanyMaxY][tamanyMaxX];
        for (int i = 0; i < tamanyMaxY; i++) {
            for (int j = 0; j < tamanyMaxX; j++) {
                if (j >= temp[i].length()) break;
                m[i][j] = temp[i].charAt(j);
                if (temp[i].charAt(j) == 'X') {
                    posicioX[0] = i;
                    posicioX[1] = j;
                }
            }
        }
    }
}
