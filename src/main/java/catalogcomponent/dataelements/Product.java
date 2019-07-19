package catalogcomponent.dataelements;

@DataDescription(columnNames = {"Номер", "Наименование", "Характеристики", "Состояние", "Цена", "Количество"})
public class Product implements GroupDataElement {

    private int groupId;

    private int id;
    private String name;
    private String specification;
    private String state;
    private int price;
    private int count;

    public Product(int groupId, int id, String name, String specification, String state, int price, int count) {
        this.groupId = groupId;
        this.id = id;
        this.name = name;
        this.specification = specification;
        this.state = state;
        this.price = price;
        this.count = count;
    }

    @Override
    public int getGroupId() {
        return groupId;
    }

    @Override
    public Object getField(int fieldIndex) {
        if (fieldIndex == 0) return id;
        if (fieldIndex == 1) return name;
        if (fieldIndex == 2) return specification;
        if (fieldIndex == 3) return state;
        if (fieldIndex == 4) return price;
        if (fieldIndex == 5) return count;
        return null;
    }

}
