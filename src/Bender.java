/**
 * Created by jmuletr on 15/03/2017.
 */
class Bender {
    //posar dibuixar a true per imprimir per pantalla el robot bender en arrivar al signe $
    boolean dibuixar = false;
    Mapa map;

    // Constructor: ens passen el mapa en forma d'String
    public Bender(String mapa) {
        map = new Mapa(mapa);
    }


    // Navegar fins a l'objectiu («$»).
// El valor retornat pel mètode consisteix en una cadena de
// caràcters on cada lletra pot tenir els valors «S», «N», «W» o «E»,
// segons la posició del robot a cada moment.
    /*S (South), E (East), N (North), W (West)
    * inversa N (North), W (West), S (South), E (East)*/
    public String run() {
        Robot bender = new Robot(map, dibuixar);
        return bender.run();
    }


}