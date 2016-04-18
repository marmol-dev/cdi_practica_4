/**
 * Implementa los métodos relacionados con el jugador
 * @author marmol
 *
 */
public class Ping implements Runnable {
	private Pelota pelota;
	private int id;
	private boolean partidaFinalizada;
	Ping(int id){
		this.id = id;
	}
	
	/**
	 * Recibe la pelota el jugador
	 * Este método nunca es invocado por el propio jugador
	 * @param pelota La pelota
	 */
	public synchronized void recibirPelota(Pelota pelota){
		System.out.println("El hilo " + id + " recibió la pelota");
		this.pelota = pelota;
	}
	
	/**
	 * Comprueba si tiene la pelota
	 * Es un método sincronizado para producir una relacion happens-before
	 * @return Si tiene la pelota o no
	 */
	private synchronized boolean tienePelota(){
		return this.pelota != null;
	}

	
	/**
	 * Método principal que invoca el thread
	 */
	public void run(){
		while(!Actividad4.estaPartidaFinalizada()){
			if (tienePelota()){
				System.out.println("El hilo " + id + " lanza la pelota");
				Actividad4.pasarSiguienteJugador();
				pelota = null;
			}
		}
		
	}
}
