import java.util.Vector;

/**
 * Clase con todos los métodos estáticos que se encarga de iniciar y gestionar el juego
 * @author marmol
 *
 */
public class Actividad4 {
	private static final int N_JUGADORES = 16;
	private static final int JUGADOR_INICIAL = 0;
	private static final int CRITERIO_FIN = 0;
	private static final int N_MAX_JUGADAS = 20;	
	
	private static Vector<Ping> jugadores;
	private static Vector<Thread> threads;
	private static int indiceJugadorActual = 0;
	private static Pelota pelota;
	
	private static int nJugadas = 0;	
	private static Object lock = new Object();
	
	
	private static boolean seSuperoNumJugadas(){
		return nJugadas >= N_MAX_JUGADAS;
	}
	
	public static boolean estaPartidaFinalizada(){
		switch(CRITERIO_FIN){
			case 0: default:
				return seSuperoNumJugadas();
				
		}
	}
	
	/**
	 * Pasa la pelota al siguiente jugador
	 * Pasa la pelota e incrementa el contador de jugadas
	 */
	public static void pasarSiguienteJugador(){
		if(jugadores.size() == 0) return;
		if(estaPartidaFinalizada()){
			for(Ping jugador: jugadores){
				jugador.finalizarPartida();
			}
		} else {
			indiceJugadorActual=(indiceJugadorActual+1)%jugadores.size();
			jugadores.get(indiceJugadorActual).recibirPelota(pelota);
			synchronized(lock){
				nJugadas++;
			}
		}
	}
	
	/**
	 * Main
	 * @param args Argumentos del programa
	 */
	public static void main(String[] args){
		pelota = new Pelota();
		jugadores = new Vector<Ping>();
		threads = new Vector<Thread>();
		
		//Crear objetos e hilos
		for(int i = 0; i < N_JUGADORES; i++){
			jugadores.add(new Ping(i));
			threads.add(new Thread(jugadores.lastElement()));
		}
		
		//Empezar hilos
		for(int i = 0; i < N_JUGADORES; i++){
			threads.get(i).start();
		}
			
		indiceJugadorActual = JUGADOR_INICIAL - 1;
		
		//Empezar partido
		double msInicio = System.currentTimeMillis();
		pasarSiguienteJugador();
		
		//Prueba interrupción ejercicio 9
		threads.get(5).interrupt();
		
		//Join
		for(int i = 0; i < N_JUGADORES; i++){
			try {
				threads.get(i).join();
			} catch(InterruptedException e){}
		}
		
		double msPartido = System.currentTimeMillis() - msInicio;
		
		System.out.println("Acabó. Tiempo: " + msPartido + " ms");
	}
}
