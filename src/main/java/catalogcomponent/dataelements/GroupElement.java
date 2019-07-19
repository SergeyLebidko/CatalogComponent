package catalogcomponent.dataelements;

public class GroupElement {

    private int id;
    private Integer parentId;
    private String name;

    public GroupElement(int id, Integer parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
