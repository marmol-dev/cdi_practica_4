/**
 * Implementa los métodos relacionados con el jugador
 * @author marmol
 *
 */
public class Ping implements Runnable {
	private Pelota pelota;
	private int id;
	private Object lock;
	private boolean partidaFinalizada;
	private boolean esperando;
	Ping(int id){
		this.id = id;
		this.lock = new Object();
		this.partidaFinalizada = false;
	}
	
	/**
	 * Recibe la pelota el jugador
	 * Este método nunca es invocado por el propio jugador
	 * @param pelota La pelota
	 */
	public synchronized void recibirPelota(Pelota pelota){
		System.out.println("El hilo " + id + " recibió la pelota");
		this.pelota = pelota;
		this.notify();
	}
	
	/**
	 * Comprueba si tiene la pelota
	 * Es un método sincronizado para producir una relacion happens-before
	 * @return Si tiene la pelota o no
	 */
	private boolean tienePelota(){
		return this.pelota != null;
	}
	
	/**
	 * Finaliza la partida
	 */
	public synchronized void finalizarPartida(){
		this.partidaFinalizada=true;
		if(this.esperando){
			this.notify();
		}
	}

	
	/**
	 * Método principal que invoca el thread
	 */
	public void run(){
		synchronized(this){
			while(!tienePelota() && !partidaFinalizada){
				this.esperando = true;
				try {
					 this.wait();
				} catch(InterruptedException e){};
				this.esperando = false;
			}
		}
		while(!partidaFinalizada){
			System.out.println("El hilo " + id + " lanza la pelota");
			
			pelota=null;
			Actividad4.pasarSiguienteJugador();
			
			synchronized(this){
				while(!tienePelota() && !partidaFinalizada){
					this.esperando = true;
					try {
						this.wait();
					} catch(InterruptedException e){};
					this.esperando = false;
				}
			}
		}
		
	}
}
