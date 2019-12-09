/**
 * Trabalho Final - POO
 * Equipe: Hélio Potelicki & Luis Felipe Zaguini 
 */

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Tabela extends JTable {
	private static final long serialVersionUID = 1L;
	private ArrayList<DBRow> items;
	
	public Tabela(DefaultTableModel model, ArrayList<DBRow> items) {
		super(model);
		
		this.items = items;
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int column) {
		super.setValueAt(aValue, rowIndex, column);
		DBRow row = items.get(rowIndex);
		String value = (String) aValue;
		
		switch(column) {
			case 0:
				row.setTipoMaterial(value);
				break;
			case 1:
				row.setMeioDivulgacao(value);
				break;
			case 2:
				row.setEntidade(value);
				break;
			case 3:
				row.setTipoOrganizacao(value);
				break;
			case 4:
				row.setAutor(value);
				break;
			case 5:
				row.setTitulo(value);
				break;
			case 6:
				row.setPalavrasChaveDescritores(value);
				break;
			case 7:
				row.setAnoProducao(value);
				break;
			case 8:
				row.setAnoPublicacao(value);
				break;
			case 9:
				row.setEdicao(value);
				break;
			case 10:
				row.setLocalPublicacao(value);
				break;
			case 11:
				row.setEditora(value);
				break;
			case 12:
				row.setPaginasMinutos(value);
				break;
			case 13:
				row.setDisponivelEm(value);
				break;
			case 14:
				row.setISBN(value);
				break;
			case 15:
				row.setISSN(value);
				break;
		}
	}
}
