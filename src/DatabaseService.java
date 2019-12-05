import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseService {
	private ArrayList<DBRow> items;
	private Cache authorCache, tipoMaterialCache, editoraCache, 
				  entidadeCache, localPublicacaoCache, palavraChaveCache, 
				  meioDivulgacaoCache;
	
	public DatabaseService(Connection connection, ArrayList<DBRow> items) {
		this.items = items;
		
		this.authorCache = new Cache(connection, "Autor")
			.withSelectQuery("select cd_autor from autor where nm_autor = ?;")
			.withInsertQuery("insert into autor values(null, ?);");
		
		this.tipoMaterialCache = new Cache(connection, "Tipo Material")
				.withSelectQuery("select tp_material from tipo_material where ds_material = ?;")
				.withInsertQuery("insert into tipo_material values(null, ?);");
		
		this.editoraCache = new Cache(connection, "Editora")
				.withSelectQuery("select cd_editora from editora where nm_editora = ?;")
				.withInsertQuery("insert into editora values(null, ?);");
	
		this.entidadeCache = new Cache(connection, "Entidade")
				.withSelectQuery("select cd_entidade from entidade where nm_entidade = ?;")
				.withInsertQuery("insert into entidade values(null, ?, ?);");
		
		this.localPublicacaoCache = new Cache(connection, "Local Publicação")
				.withSelectQuery("select cd_local_publicacao from local_publicacao where nm_local_publicacao = ?;")
				.withInsertQuery("insert into local_publicacao values(null, ?);");
		
		this.palavraChaveCache = new Cache(connection, "Palavra Chave")
				.withSelectQuery("select cd_palavra_chave from palavra_chave where ds_palavra_chave = ?;")
				.withInsertQuery("insert into palavra_chave values(null, ?);");
		
		this.meioDivulgacaoCache = new Cache(connection, "Meio de Divulgação")
				.withSelectQuery("select tp_divulgacao from meio_divulgacao where ds_divulgacao = ?;")
				.withInsertQuery("insert into meio_divulgacao values(null, ?);");
	}
	
	public void write() throws NoContentError, SQLException {
		for(DBRow item: items) {
			Integer autorId = this.authorCache.get(item.getAutor(), null);
			
			Integer tipoMaterialId = this.tipoMaterialCache.get(item.getTipoMaterial(), null);
			
			Integer editoraId = this.editoraCache.get(item.getEditora(), null);
			
			Integer entidadeId = this.entidadeCache.get(item.getEntidade(), (stmt) -> {
				try {
					stmt.setString(1, item.getEntidade()); 
					stmt.setString(2, item.getTipoOrganizacao().contentEquals("governamental") ? "GO" : "NG");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
			Integer localPublicacaoId = this.localPublicacaoCache.get(item.getLocalPublicacao(), null);
			
			Integer palavraChaveId = this.palavraChaveCache.get(item.getPalavrasChaveDescritores(), null);
			
			Integer meioDivulgacaoId = this.meioDivulgacaoCache.get(item.getMeioDivulgacao(), null);
		}
	}
}
