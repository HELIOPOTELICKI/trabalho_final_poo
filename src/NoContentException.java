/**
 * Trabalho Final - POO
 * Equipe:
 * 
 * Back-End:  Hélio Potelicki & Luis Felipe Zaguini
 * Front-End: Murilo Eger & Pedro Roweder
 *
 */

public class NoContentException extends Exception {
	private static final long serialVersionUID = 1L;
	private int index;

	public NoContentException(int index, String field) {
		super(field);
		this.index = index;
	}
	
	public int getIndex() {
		return this.index;
	}
}
