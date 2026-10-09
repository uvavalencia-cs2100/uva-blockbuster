package entity;

import java.util.List;

// Describes how one kind of entity maps to the columns of its CSV file: the names and order of the
// columns, how to turn an entity into its values and how to build one back from a row. It is the
// only thing an entity has to provide for AbstractRepository to store it.
public interface CsvMapping<T extends Entity> {

    // Column names, in file order. They are written as the first line of the file, and they are
    // the names fromCSVRow reads by, so a column is renamed here and in fromCSVRow only. The
    // number of columns is what a line must have to be valid.
    List<String> getCSVColumnNames();

    // The values of the entity as text, in the same order as getCSVColumnNames(). They are joined
    // with commas as they are (no quoting), so an entity must not accept values with a comma.
    List<String> toFields(T entity);

    // Builds an entity from one line of the file, reading the values by column name from the row.
    // The row has already checked the number of columns, and getInt checks integers. The entity
    // constructor checks the rest. Any invalid value must throw IllegalArgumentException: the
    // repository logs it and skips the line.
    T fromCSVRow(CsvRow row);

}
