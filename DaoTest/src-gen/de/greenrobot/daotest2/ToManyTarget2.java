package de.greenrobot.daotest2;


// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table TO_MANY_TARGET2.
 */
public class ToManyTarget2 {


    private Long id;
    private Long fkId;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public ToManyTarget2() {
    }

    public ToManyTarget2(Long id) {
        this.id = id;
    }

    public ToManyTarget2(Long id, Long fkId) {
        this.id = id;
        this.fkId = fkId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFkId() {
        return fkId;
    }

    public void setFkId(Long fkId) {
        this.fkId = fkId;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
