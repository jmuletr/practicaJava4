/**
 * Created by jmuletr on 16/03/2017.
 */
public class Mapa {
    //mapa construit en array de caracters
    char[][] m;
    //cordenades de la X
    int[] posicioX = new int[2];
    //altura i amplada maxima que tindra el mapa
    int tamanyMaxX = 0;
    int tamanyMaxY = 0;
    //posicio del desti
    int[] posicio$ = new int[2];
    int[] posicioT = new int[4];
    boolean primer = true;

    Mapa(String mapa){
        //es divideix el mapa en arrays de string on cada un representa una linea del mapa
        String[] temp = mapa.split("\\n");
        //calculam l'altura del mapa
        tamanyMaxY = temp.length;
        //sercam l'ample maxim que tindra el mapa recorreguent tots els arrays de temp i quedanos amb l'amplada maxima
        for (int i = 0; i < tamanyMaxY; i++) {
            if (temp[i].length() > tamanyMaxX) {
                tamanyMaxX = temp[i].length();
            }
        }
        //inicialitzam la variable m que contindra el mapa amb els tamanys maxims que pugui tenir el mapa tant d'alt
        //com d'ample
        m = new char[tamanyMaxY][tamanyMaxX];
        //bucle on omplirem l'array m amb els caracters dels arrays de temp
        for (int i = 0; i < tamanyMaxY; i++) {
            for (int j = 0; j < tamanyMaxX; j++) {
                //si j es major o igual que el string que tractam pasam al proxim sino asignam el caracter a la posicio que li correspon
                if (j >= temp[i].length()) break;
                m[i][j] = temp[i].charAt(j);
                //si el caracter es X o $ guardam les seves cordenades a la variable corresponent
                if (temp[i].charAt(j) == 'X') {
                    posicioX[0] = i;
                    posicioX[1] = j;
                }
                if (temp[i].charAt(j) == '$') {
                    posicio$[0] = i;
                    posicio$[1] = j;
                }
                if (temp[i].charAt(j) == 'T') {
                    if (primer){
                        posicioT[0] = i;
                        posicioT[1] = j;
                        primer = false;
                    } else{
                        posicioT[2] = i;
                        posicioT[3] = j;
                    }
                }
            }
        }
    }
}
