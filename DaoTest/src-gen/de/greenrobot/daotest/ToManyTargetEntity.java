package de.greenrobot.daotest;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table TO_MANY_TARGET_ENTITY.
 */
public class ToManyTargetEntity {


    private Long toManyId;
    private Long toManyIdDesc;
    private Long id;
    private String targetJoinProperty;

    public ToManyTargetEntity() {
    }

    public ToManyTargetEntity(Long id) {
        this.id = id;
    }

    public ToManyTargetEntity(Long toManyId, Long toManyIdDesc, Long id, String targetJoinProperty) {
        this.toManyId = toManyId;
        this.toManyIdDesc = toManyIdDesc;
        this.id = id;
        this.targetJoinProperty = targetJoinProperty;
    }

    public Long getToManyId() {
        return toManyId;
    }

    public void setToManyId(Long toManyId) {
        this.toManyId = toManyId;
    }

    public Long getToManyIdDesc() {
        return toManyIdDesc;
    }

    public void setToManyIdDesc(Long toManyIdDesc) {
        this.toManyIdDesc = toManyIdDesc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTargetJoinProperty() {
        return targetJoinProperty;
    }

    public void setTargetJoinProperty(String targetJoinProperty) {
        this.targetJoinProperty = targetJoinProperty;
    }

}
