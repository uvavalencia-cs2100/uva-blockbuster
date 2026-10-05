package entity;

public interface ViewOptions {

    /*
     * Returns a single-line view of the entity.
     */
    String getSingleLineView();

    /*
     * Returns a detailed view of the entity.
     */
    String getDetailedView();

    /*
     * Returns a log view of the entity.
     */
    String getLogView();
    
}
