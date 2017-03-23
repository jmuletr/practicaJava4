/**
 * Created by jmuletr on 15/03/2017.
 */
class Bender {
    //posar dibuixar a true per imprimir per pantalla el robot bender en arrivar al signe $
    boolean dibuixar = false;
    //posar a true per activar el debugger on mostra graficament per terminal el mapa i la posicio de bender
    boolean debug = false;
    //variable on es guarda l'objecte mapa i l'objecte robot
    Mapa map;
    Robot bender;

    // Constructor: ens passen el mapa en forma d'String
    public Bender(String mapa) {
        //constructor de bender on es creen els objectes mapa i robot i s'asignen a les seves variables corresponents
        map = new Mapa(mapa);
        bender = new Robot(map, dibuixar, debug);
    }


    // Navegar fins a l'objectiu («$»).
// El valor retornat pel mètode consisteix en una cadena de
// caràcters on cada lletra pot tenir els valors «S», «N», «W» o «E»,
// segons la posició del robot a cada moment.
    public String run() {
        //retornam el cami recorregut per bender com a string generat cridant la funcio run del objecte robot
        return bender.run();
    }

    public int bestRun() {
        //retornam les pases donades per arribar al seu desti en forma de int cridant la funcio bestRun
        // del objecte robot
        return bender.bestRun();
    }
}