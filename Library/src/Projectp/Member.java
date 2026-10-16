package Projectp;

import java.time.LocalDate;
import java.util.ArrayList;

public class Member {
    private int memberId;
    private String name;
    private LocalDate now = LocalDate.now();
    private ArrayList<Item> personalItems;

    public Member() {
        this.personalItems = new ArrayList<>();
    }

    public Member(int memberId, String name, ArrayList<Item> items) {
        this.memberId = memberId;
        this.name = name;
        Library.setItems(items);
        personalItems = new ArrayList<>();
    }

    public int getmemberId() {
        return memberId;
    }

    public void setmemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNow(LocalDate now) {
        this.now = now;
    }

    public LocalDate getNow() {
        return now;
    }

    public ArrayList<Item> getPersonalItems() {
        return personalItems;
    }

    public void borrowBook(String book) {
        boolean borrow = false;
        Item m = null;
        for (Item x : Library.getItems()) {
            if (x instanceof Book) {
                if (book.equals(x.title)) {
                    System.out.println(x.title + " have borrowed");
                    now = LocalDate.now();
                    System.out.println("The time of borrow:" + now);
                    borrow = true;
                    m = x;
                    break;
                }
            }
        }
        if (borrow) {
            personalItems.add(m);
            Library.getItems().remove(m);
        }
    }

    public void showTheAIBook() {
        for (Item x : personalItems) {
            if (x instanceof Book) {
                if (x.getSubject().equals("Ai")) {
                    System.out.println(getName());
                    System.out.println(x.getTitle());
                }
            }
        }
    }

    public void returnBook(String b) {
        Item m = null;
        boolean returnBook = false;
        for (Item x : personalItems) {
            if (x instanceof Book) {
                if (x.title.equals(b)) {
                    returnBook = true;
                    System.out.println("you have borrow this book thank you for return it");
                    now = LocalDate.now();
                    System.out.println("The time of return:" + now);
                    m = x;
                }
            }
        }
        if (returnBook) {
            Library.getItems().add(m);
            personalItems.remove(m);
        }

    }

    public void borrowProject(String project) {
        Item m = null;
        boolean borrowopject = false;
        for (Item x : Library.getItems()) {
            if (x instanceof Project) {
                if (project.equals(x.title)) {
                    borrowopject = true;
                    System.out.println(x.title + " have borrowed");
                    now = LocalDate.now();
                    System.out.println("The time of borrow:" + now);
                    m = x;
                    break;
                }
            }
        }
        if (borrowopject) {
            personalItems.add(m);
            Library.getItems().remove(m);
        }
    }

    public void retunProject(String b) {
        Item m = null;
        boolean returnopject = false;
        for (Item x : personalItems) {
            if (x instanceof Project) {
                if (x.title.equals(b)) {
                    returnopject = true;
                    System.out.println("you have borrow this project thank you for return it");
                    now = LocalDate.now();
                    System.out.println("The time of return:" + now);
                    m = x;
                }
            }
        }
        if (returnopject) {
            Library.getItems().add(m);
            personalItems.remove(m);
        }
    }

    public void borrowedBooks() {
        boolean check = false;
        System.out.println("Member Name "+name);
        for (Item t : personalItems) {
            System.out.println("the borrowed books are");
            if (t instanceof Book)
                System.out.println(t.getTitle());
                check=true;
        }
        if(!check)
            System.out.println("there are no borrowed books");
    }

}
