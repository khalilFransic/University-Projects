

package Projectp;


public class Book extends Item {
    
    private String author;
    private int pages;
    private String publisher;

    public Book() {
        super();
    }

    public Book(String author, int pages, String publisher, int itemId, String title, int year, String subject) {
        super(itemId, title, year, subject);
        this.author = author;
        this.pages = pages;
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void printinfo() {
        System.out.println("The itemId of book = " + getItemId());
        System.out.println("The title is " + getTitle());
        System.out.println("The year of uplode = " + getYear());
        System.out.println("The subject  = " + getSubject());
        System.out.println("The author is " + getAuthor());
        System.out.println("The pages = " + getPages());
        System.out.println("The publisher is " + getPublisher());
    }

}
