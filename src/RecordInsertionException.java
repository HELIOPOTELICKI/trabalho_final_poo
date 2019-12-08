/**
 * Trabalho Final - POO
 * Equipe:
 * 
 * Back-End:  Hélio Potelicki & Luis Felipe Zaguini
 * Front-End: Murilo Eger & Pedro Roweder
 *
 */

public class RecordInsertionException extends Exception {
	private static final long serialVersionUID = 1L;
	private int index;
	
	public RecordInsertionException(int index, String message) {
		super(message);
		
		this.index = index;
	}
	
	public int getIndex() {
		return this.index;
	}
}
