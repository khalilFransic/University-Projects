 package Projectp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

public class Library {

    private ArrayList<Member> members = new ArrayList<>();
    private static ArrayList<Item> items = new ArrayList<>();
    private LocalDate currentDate = LocalDate.now();

    public Library(ArrayList<Member> members, ArrayList<Item> iteams) {
        this.members = members;
        Library.items = iteams;
    }

    public void setBook(Book b) {

        items.add(b);
    }

    public void setMember(Member member) {
        members.add(member);
    }

    public void setproject(Project p) {
        items.add(p);
    }

    public static ArrayList<Item> getItems() {
        return items;
    }

    public static void setItems(ArrayList<Item> items) {
        Library.items = items;
    }

    public void borrowBook(String member, String book) {
        Member member2 = null;
        boolean found = false;
        for (Member m : members) {
            if (member.equals(m.getName())) {
                found = true;
                member2 = m;
                break;
            }
        }
        if (!found) {
            System.out.println("member is not available");
            return;
        }

        boolean found1 = false;
        for (Item b : items) {
            if (b instanceof Book) {
                if (book.equalsIgnoreCase(b.title)) {
                    found1 = true;
                    
                    break;
                }
            }
        }
        if (!found1) {
            System.out.println("the book is no longer available");
            return;
        }

        if (member2.getPersonalItems().size() > 3)
            System.out.println("the member:" + member2.getName() + "reach her limit");

        else {
            member2.borrowBook(book);
            LocalDate now = LocalDate.now();
            LocalDate after7Days = now.plusDays(7);
            System.out.println("the limit time for the borrowing is :" + after7Days);
        }
    }

    public void returnBook(String member, String book) {
        Member member2 = null;
        boolean found = false;
        for (Member m : members) {
            if (member.equals(m.getName())) {
                found = true;
                member2 = m;
                break;
            }
        }
        if (!found) {
            System.out.println("member is not available");
            return;
        }

        boolean found1 = false;
        for (Item b : member2.getPersonalItems()) {
            if (b instanceof Book) {
                if (book.equals(b.title)) {
                    found1 = true;
                    
                    break;
                }
            }
        }
        if (!found1) {
            System.out.println("the book is no longer borrowed");
            return;
        }

        member2.returnBook(book);
    }

    public void borrowProject(String member, String book) {
        Member member2 = null;
        boolean found = false;
        for (Member m : members) {
            if (member.equals(m.getName())) {
                found = true;
                member2 = m;
                break;
            }
        }
        if (!found) {
            System.out.println("member is not available");
            return;
        }

        boolean found1 = false;
        for (Item b : items) {
            if (b instanceof Project) {
                if (book.equalsIgnoreCase(b.title)) {
                    found1 = true;
                    
                    break;
                }
            }
        }
        if (!found1) {
            System.out.println("the project is no longer available");
            return;
        }

        if (member2.getPersonalItems().size() > 3)
            System.out.println("the member:" + member2.getName() + "reach her limit");

        else {
            member2.borrowProject(book);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime after7Days = now.plusDays(7);
            System.out.println("the limit  time for the borrow is :" + after7Days);
        }
    }

    public void returnProject(String member, String project) {
        Member member2 = null;
        boolean found = false;
        for (Member m : members) {
            if (member.equals(m.getName())) {
                found = true;
                member2 = m;
                break;
            }
        }
        if (!found) {
            System.out.println("member is not available");
            return;
        }

        boolean found1 = false;
        for (Item b : member2.getPersonalItems()) {
            if (b instanceof Project) {
                if (project.equals(b.title))
                    found1 = true;
                
                break;
            }
        }
        if (!found1) {
            System.out.println("the projrct is no longer borrowed");
            return;
        }
        member2.retunProject(project);
    }

    public LocalDate setCurrentDate(LocalDate fakenow) {
        return this.currentDate = fakenow;
    }

    public void lateMembers(LocalDate currentDate1) {
        boolean check = false;
        for (Member m : members) {
            if (m.getPersonalItems().size() == 0)
                continue;
            if (currentDate1.isAfter(m.getNow().plusDays(7)))
                System.out.println(m.getName() + " is late");
            check = true;
        }
        if (!check)
            System.out.println("there is no late member");
    }

    public void showItemsInspecificeTime(LocalDate time) {
        LocalDate fake = LocalDate.of(2025, Month.JULY, 10);
        boolean check = false;
        for (Member m : members) {
            m.setNow(setCurrentDate(fake));
            if (m.getNow().isEqual(time)) {
                System.out.println("the items are" + m.getName());
                check = true;
            }
        }
        if (!check)
            System.out.println("thers is not any borrowed procces in this time");
    }
}
