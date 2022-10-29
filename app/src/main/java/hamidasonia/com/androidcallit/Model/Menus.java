package hamidasonia.com.androidcallit.Model;

/**
 * Created by Mbahman on 12/29/2017.
 */

public class Menus {
    private String Name, Image, Description, MenuId, Number;

    public Menus() {
    }

    public Menus(String name, String image, String description, String menuId, String number) {
        Name = name;
        Image = image;
        Description = description;
        MenuId = menuId;
        Number = number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}
