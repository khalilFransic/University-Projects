/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Projectp;

/**
 *
 * @author ASUS
 */

import java.time.LocalDate;
import java.time.Month;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        ArrayList<Member> members = new ArrayList<>();
        ArrayList<Item> iteams = new ArrayList<>();

        Library library = new Library(members, iteams);
        Book book1 = new Book("Dani", 100, "library", 5, "AI", 2010, "Ai");
        Book book2 = new Book("lana", 150, "library", 7, "DataStructer", 2000, "social");
        Book book3 = new Book("rami", 90, "library", 9, "Math", 2017, "social");
        Book book4 = new Book("essa", 180, "library", 14, "Coding", 2010, "imaginary");
        Book book5 = new Book("dahdal", 1990, "library", 16, "Programming", 2019, "imaginary");

        Project project1 = new Project("firsst year", 10, "Library", 2010, "Java", "خليها على الله");
        Project project2 = new Project("first year", 16, "Parking", 2018, "C++", "2خليها على الله");
        Project project3 = new Project("third year", 18, "Hospital", 2011, "C#", "3خليها على الله");
        Project project4 = new Project("third year", 15, "School", 2012, "Paython", "4خليها على الله");

        Member member1 = new Member(10, "khalil", iteams);
        Member member2 = new Member(19, "ahmad", iteams);
        Member member3 = new Member(18, "tony", iteams);
        Member member4 = new Member(7, "khoder", iteams);

        iteams.add(book5);
        iteams.add(book1);
        iteams.add(book2);
        iteams.add(book3);
        iteams.add(book4);
        iteams.add(project1);
        iteams.add(project2);
        iteams.add(project3);
        iteams.add(project4);
        members.add(member1);
        members.add(member2);
        members.add(member3);
        members.add(member4);
        boolean on = true;
        int choices;
        while (on) {
            System.out.println(" 1-Enter the programe");
            System.out.println(" 0-Exit");
            choices = in.nextInt();

            switch (choices) {
                case 1:
                    int choice = 0;
                    System.out.println("what do you need to choose");
                    System.out.println(" 1-show all book");
                    System.out.println(" 2-show all members and there borrowed books");
                    System.out.println(" 3-add a member or opject");
                    System.out.println(" 4-search for a book or project");
                    System.out.println(" 5-want to borrow ");
                    System.out.println(" 6-want to return a book or project");
                    System.out.println(" 7-show the peoject for the third year");
                    System.out.println(" 8-show  Ai books");
                    System.out.println(" 9-show late members");
                    System.out.println(" 10-show all borrwed book");
                    System.out.println(" 11-show all project in speecifice specialty");
                    System.out.println(" 12-show the members that borrowed items in specific date");
                    choice = in.nextInt();
                    in.nextLine();
                    switch (choice) {
                        case 1:
                            for (Item b : iteams) {
                                if (b instanceof Book) {
                                    System.out.println("we have: " + b.getTitle());
                                }
                            }
                            break;
                        case 2:
                            for (Member m : members) {
                                System.out.println(m.getName());
                                if (m.getPersonalItems().size() == 0)
                                    System.out.println(" has not borrowed any  books");
                                m.borrowedBooks();
                            }
                            break;
                        case 3:
                            System.out.println("chose what you need to add");
                            System.out.println(" 1-book");
                            System.out.println(" 2-project");
                            System.out.println(" 3-member");
                            int add;
                            add = in.nextInt();
                            in.nextLine();
                            switch (add) {
                                case 1:
                                    Book book = new Book();
                                    System.out.println("Add the title of the book:");
                                    
                                    String title = in.nextLine();
                                    book.setTitle(title);

                                    System.out.println("Add the number of pages:");
                                    int pages = in.nextInt();
                                    book.setPages(pages);
                                    in.nextLine();

                                    System.out.println("Add the name of the author:");
                                    String author = in.nextLine();
                                    book.setAuthor(author);

                                    System.out.println("Add the name of the publisher:");
                                    String publisher = in.nextLine();
                                    book.setPublisher(publisher);

                                    System.out.println("Add the item ID:");
                                    int itemid = in.nextInt();
                                    book.setItemId(itemid);
                                    in.nextLine();

                                    System.out.println("Add the name of the subject:");
                                    String subject = in.nextLine();
                                    book.setSubject(subject);

                                    System.out.println("Add the year:");
                                    int year = in.nextInt();
                                    book.setYear(year);
                                    in.nextLine();

                                    book.printinfo();

                                    iteams.add(book);
                                    break;

                                case 2:
                                    Project project = new Project();
                                    System.out.println("Add the title of the project:");
                                    in.nextLine();
                                    title = in.nextLine();
                                    project.setTitle(title);

                                    System.out.println("Add the item ID:");
                                    itemid = in.nextInt();
                                    in.nextLine();
                                    project.setItemId(itemid);
                                    System.out.println("Add the name of the subject:");
                                    subject = in.nextLine();
                                    project.setSubject(subject);

                                    System.out.println("add the name of the  team");
                                    
                                    project.setTeam( in.nextLine());

                                    System.out.println("Add the year:");
                                    year = in.nextInt();
                                    in.nextLine();
                                    project.setYear(year);

                                    System.out.println("add the level of the project");
                                    String level = in.nextLine();
                                    project.setLevel(level);

                                    iteams.add(project);

                                    break;

                                case 3:
                                    Member member = new Member();
                                    System.out.println("Enter the name of the member");
                                    
                                    member.setName( in.nextLine());

                                    System.out.println("Enter the id of the member");
                                    

                                    member.setmemberId( in.nextInt());
                                    members.add(member);

                                    break;
                            }

                            break;
                        case 4:

                            System.out.println("how do you want to search");
                            System.out.println("1- by name");
                            System.out.println("2- by subject");
                            System.out.println("3- by ID");
                            int num1;
                            num1 = in.nextInt();
                            switch (num1) {
                                case 1:
                                    System.out.println("Enter the name");
                                    in.nextLine();
                                    String name = in.nextLine();
                                    Item x = null;
                                    boolean condition = false;
                                    for (Item b : iteams) {
                                        if (name.equals(b.title)) {
                                            x = b;
                                            condition = true;
                                            break;
                                        }
                                    }
                                    if (condition) {
                                        System.out.println("the result is");
                                        x.printinfo();
                                    } else
                                        System.out.println("nit found");
                                    break;

                                case 2:
                                    System.out.println("Enter the subject");
                                    in.nextLine();
                                    name = in.nextLine();
                                    x = null;
                                    condition = false;
                                    for (Item b : iteams) {
                                        if (name.equals(b.subject)) {
                                            x = b;
                                            condition = true;
                                            break;
                                        }
                                    }
                                    if (condition) {
                                        System.out.println("the result is");
                                        x.printinfo();
                                    } else
                                        System.out.println("nit found");

                                    break;

                                case 3:
                                    System.out.println("Enter the id");
                                    in.nextLine();
                                    int id = in.nextInt();
                                    x = null;
                                    condition = false;
                                    for (Item b : iteams) {
                                        if (id == (b.itemId)) {
                                            x = b;
                                            condition = true;
                                            break;
                                        }
                                    }
                                    if (!condition) {
                                        System.out.println("the result is");
                                        x.printinfo();
                                    } else
                                        System.out.println("nit found");

                                    break;
                            }

                        case 5:
                            int num;
                            System.out.println(" 1-book");
                            System.out.println(" 2-project");
                            num = in.nextInt();
                            System.out.println("Enter the name of the member");
                            in.nextLine();
                            String member = in.nextLine();
                            switch (num) {
                                case 1:
                                    System.out.println("Enter the name of the book");
                                    String titleofthebook = in.nextLine();
                                    library.borrowBook(member, titleofthebook);

                                    break;

                                case 2:
                                    System.out.println("Enter the name of the project");
                                    String titleotheproject = in.nextLine();
                                    library.borrowProject(member, titleotheproject);

                                    break;

                            }
                            break;
                        case 6:
                            System.out.println(" 1-book");
                            System.out.println(" 2-project");
                            num = in.nextInt();
                            System.out.println("Enter the name of the member");
                            in.nextLine();
                            member = in.nextLine();
                            switch (num) {
                                case 1:
                                    System.out.println("Enter the name of the book");
                                    String titleofthebook = in.nextLine();
                                    library.returnBook(member, titleofthebook);
                                    break;
                                case 2:
                                    System.out.println("Enter the name of the project");
                                    String titleotheproject = in.nextLine();
                                    library.returnProject(member, titleotheproject);
                                    break;
                            }
                            break;
                        case 7:
                            for (Item x : iteams) {
                                if (x instanceof Project && ((Project) x).getLevel().equals("third year")) {
                                    System.out.println(x.getTitle());
                                }
                            }
                            break;
                        case 8:
                            for (Member m : members) {
                                if (m.getPersonalItems().size() == 0)
                                    System.out.println(m.getName() + " has not borrowed any Ai books");
                                m.showTheAIBook();
                            }
                            break;

                        case 9:
                            LocalDate fakeDate = LocalDate.of(2026, Month.MARCH, 2);
                            library.lateMembers(library.setCurrentDate(fakeDate));
                            break;

                        case 10:
                            for (Member m : members) {
                                m.borrowedBooks();
                            }
                            break;
                        case 11:
                            System.out.println("Enter the specialization");
                            String specialization = in.nextLine();
                            for (Item x : iteams) {
                                if (x instanceof Project && x.getTitle().equals(specialization))
                                    System.out.println(x.getTitle());
                            }
                            break;
                        case 12:
                            System.out.print("Enter the date (e.g. 2025-07-05): ");
                            String date = in.nextLine();

                            if (date.isEmpty()) {
                                System.out.println("no input provided");
                                break;
                            }
                            LocalDate time = LocalDate.parse(date);
                            library.showItemsInspecificeTime(time);

                            // LocalDate fakeDate=LocalDate.of(10, Month.MARCH, 11);
                            // library.lateMembers(library.setCurrentDate(fakeDate));
                            break;

                    }
                    break;

                case 0:
                    on = false;
                    break;

                // Done
                // In the end, the journey was exhausting, but every line of code was written
                // with struggle.

            }
        }
    }
}

