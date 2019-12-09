/**
 * Trabalho Final - POO
 * Equipe: Hélio Potelicki & Luis Felipe Zaguini 
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DatabaseService {
	private ArrayList<DBRow> items;
	private Connection connection;
	private Cache authorCache, tipoMaterialCache, editoraCache, entidadeCache, localPublicacaoCache, palavraChaveCache,
			meioDivulgacaoCache;

	public DatabaseService(Connection connection, ArrayList<DBRow> items) {
		this.items = items;
		this.connection = connection;

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

	private int writeMaterial(DBRow item, Integer autorId, Integer tipoMaterialId, Integer editoraId,
			Integer entidadeId, Integer localPublicacaoId, Integer palavraChaveId, Integer meioDivulgacaoId)
			throws SQLException {
		String insertMaterialQuery = ""
				+ "set @nm_titulo = ?, @ds_ano_producao = ?, @ds_ano_publicacao = ?, @ds_edicao = ?, @nr_paginas = ?, @ds_url_disponivel = ?, @nr_isbn = ?, @nr_issn = ?, @cd_tp_material = ?, @cd_tp_divulgacao = ?, @cd_local_publicacao = ?, @cd_editora = ?, @cd_entidade = ?; "
				+ "insert ignore into material values(null, @nm_titulo, @ds_ano_producao, @ds_ano_publicacao, @ds_edicao, @nr_paginas, @ds_url_disponivel, @nr_isbn, @nr_issn, @cd_tp_material, @cd_tp_divulgacao, @cd_local_publicacao, @cd_editora, @cd_entidade) "
				+ "on duplicate key update cd_material = LAST_INSERT_ID(cd_material), nm_titulo = @nm_titulo, ds_ano_producao = @ds_ano_producao, ds_ano_publicacao = @ds_ano_publicacao, ds_edicao = @ds_edicao, nr_paginas = @nr_paginas, ds_url_disponivel = @ds_url_disponivel, nr_isbn = @nr_isbn, nr_issn = @nr_issn, cd_tp_material = @cd_tp_material, cd_tp_divulgacao = @cd_tp_divulgacao, cd_local_publicacao = @cd_local_publicacao, cd_editora = @cd_editora, cd_entidade = @cd_entidade;";

		PreparedStatement stmt = this.connection.prepareStatement(insertMaterialQuery, Statement.RETURN_GENERATED_KEYS);

		stmt.setString(1, item.getTitulo().trim());
		stmt.setString(2, item.getAnoProducao().trim());
		stmt.setString(3, item.getAnoPublicacao().trim());
		stmt.setString(4, item.getEdicao().trim());
		stmt.setString(5, item.getPaginasMinutos().trim());
		stmt.setString(6, item.getDisponivelEm().trim());
		stmt.setString(7, item.getISBN().trim());
		stmt.setString(8, item.getISSN().trim());
		stmt.setInt(9, tipoMaterialId);
		stmt.setInt(10, meioDivulgacaoId);
		stmt.setInt(11, localPublicacaoId);
		stmt.setInt(12, editoraId);
		stmt.setInt(13, entidadeId);

		stmt.executeUpdate();
		
		ResultSet result = stmt.executeQuery("select LAST_INSERT_ID() as n");
		result.next();

		return result.getInt(1);
	}

	public void write() throws NoContentException, RecordInsertionException, SQLException {
		for (int index = 0; index < items.size(); index++) {
			DBRow item = items.get(index);
			Integer autorId, tipoMaterialId, editoraId, entidadeId, localPublicacaoId, palavraChaveId, meioDivulgacaoId;
			Integer materialId;

			try {
				autorId = this.authorCache.get(item.getAutor(), null);
				tipoMaterialId = this.tipoMaterialCache.get(item.getTipoMaterial(), null);
				editoraId = this.editoraCache.get(item.getEditora(), null);
				localPublicacaoId = this.localPublicacaoCache.get(item.getLocalPublicacao(), null);
				palavraChaveId = this.palavraChaveCache.get(item.getPalavrasChaveDescritores(), null);
				meioDivulgacaoId = this.meioDivulgacaoCache.get(item.getMeioDivulgacao(), null);

				String entidade = item.getEntidade();
				String tipoOrganizacao = item.getTipoOrganizacao();

				if (entidade.contentEquals("")) {
					throw new NoContentException(0, "Entidade");
				}

				if (tipoOrganizacao.contentEquals("")) {
					throw new NoContentException(0, "Tipo de organização");
				}

				if (item.getAnoProducao().contentEquals("")) {
					throw new NoContentException(0, "Ano de Produção");
				}

				if (item.getAnoPublicacao().contentEquals("")) {
					throw new NoContentException(0, "Ano de Publicação");
				}
				
				if (item.getEdicao().contentEquals("")) {
					throw new NoContentException(0, "Edição");
				}

				if (item.getPaginasMinutos().contentEquals("")) {
					throw new NoContentException(0, "Número de páginas/Minutos");
				}

				if (item.getDisponivelEm().contentEquals("")) {
					throw new NoContentException(0, "Disponível em");
				}

				if (item.getISBN().contentEquals("")) {
					throw new NoContentException(0, "ISBN");
				}

				if (item.getISSN().contentEquals("")) {
					throw new NoContentException(0, "ISSN");
				}

				entidadeId = this.entidadeCache.get(item.getEntidade(), (stmt) -> {
					try {
						stmt.setString(1, entidade);
						stmt.setString(2, tipoOrganizacao.contentEquals("governamental") ? "GO" : "NG");

						return null;
					} catch (SQLException e) {
						return e;
					}
				});
			} catch (NoContentException e) {
				throw new NoContentException(index, e.getMessage());
			}

			try {
				materialId = this.writeMaterial(item, autorId, tipoMaterialId, editoraId, entidadeId, localPublicacaoId,
						palavraChaveId, meioDivulgacaoId);

				this.writeMaterialAutor(materialId, autorId);
				this.writeMaterialPalavraChave(palavraChaveId, materialId);

			} catch (SQLException e) {
				throw new RecordInsertionException(index, e.getMessage());
			}
		}
	}

	private void writeMaterialAutor(Integer materialId, Integer autorId) throws SQLException {
		String insertMaterialAutorQuery = "" + "set @cd_material = ?, @cd_autor = ?; "
				+ "insert ignore into material_autor values(@cd_material, @cd_autor) "
				+ "on duplicate key update cd_material = @cd_material, cd_autor = @cd_autor;";

		PreparedStatement stmt = this.connection.prepareStatement(insertMaterialAutorQuery,
				Statement.RETURN_GENERATED_KEYS);

		stmt.setString(1, materialId.toString());
		stmt.setString(2, autorId.toString());

		stmt.executeUpdate();

	}

	private void writeMaterialPalavraChave(Integer palavraChaveId, Integer materialId) throws SQLException {
		String insertMaterialPalavraChaveQuery = "" 
				+ "set @cd_palavra_chave = ?, @cd_material = ?; "
				+ "insert ignore into material_palavra_chave values(@cd_palavra_chave, @cd_material) "
				+ "on duplicate key update cd_palavra_chave = @cd_palavra_chave, cd_material = @cd_material;";

		PreparedStatement stmt = this.connection.prepareStatement(insertMaterialPalavraChaveQuery,
				Statement.RETURN_GENERATED_KEYS);

		stmt.setString(1, palavraChaveId.toString());
		stmt.setString(2, materialId.toString());

		stmt.executeUpdate();

	}
}