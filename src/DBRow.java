import com.opencsv.bean.CsvBindByPosition;

public class DBRow {
	@CsvBindByPosition(position = 0)
	private String tipoMaterial;
	
	@CsvBindByPosition(position = 1)
	private String meioDivulgacao;
	
	public String getMeioDivulgacao() {
		return meioDivulgacao;
	}

	public void setMeioDivulgacao(String meioDivulgacao) {
		this.meioDivulgacao = meioDivulgacao;
	}

	public String getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(String tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}
}
