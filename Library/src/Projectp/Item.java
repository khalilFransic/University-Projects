
package Projectp;
abstract public class Item {
    protected int itemId;
    protected String title;
    protected int year;
    protected String subject;

    public Item() {
    }

    public Item(int itemId, String title, int year, String subject) {
        this.itemId = itemId;
        this.title = title;
        this.year = year;
        this.subject = subject;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    abstract void printinfo();
}
