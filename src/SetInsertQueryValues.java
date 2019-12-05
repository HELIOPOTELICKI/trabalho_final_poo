import java.sql.PreparedStatement;

@FunctionalInterface
interface SetInsertQueryValues {
    void apply(PreparedStatement stmt);
}