    import java.time.LocalDate;
    import java.util.*;


    public class BorrowedBook {

        LocalDate date;
        BST BST ;
        int arrivalOrder=0;
        ArrayList<Borrowed> borrowedList =new ArrayList<>();


        public BorrowedBook(BST BST){
            this.BST = BST;
        }
        PriorityQueue<Node> maxHeap =
                new PriorityQueue<>(
                        (b1, b2) -> Integer.compare(
                                b2.famousRate,
                                b1.famousRate
                        )
                );
        public int borrowedBookDate(String name,int age,boolean grade,int bookId,String returnTime) {

            Node n = BST.search(bookId);
            if (n == null) {
                date = null;
                System.out.println("book not found!");
                return 1;
            } else if (n.copies == 0) {
                Students nn = new Students(name, age, grade);
                if (nn.graduated) {
                    arrivalOrder++;
                    nn.arrivalOrder = arrivalOrder;
                }
                n.queue.add(nn);
                System.out.println("your name will be add to the wait list");
                return 2;
            } else {
                Borrowed b=null;
                boolean check = false;
                boolean check2 = false;

                for (int i = 0; i < borrowedList.size(); i++) {
                    if (borrowedList.get(i).name.equals(name)) {
                        b = borrowedList.get(i);
                        if (b.limit == 3) {
                            check2 = true;
                            break;
                        }
                    }
                }
                if (check2) {
                    System.out.println("you reached your limit sorry..");
                    return 3;

                } else {
                    n.state = "borrowed";
                    n.copies = n.copies - 1;
                    maxHeap.remove(n);
                    n.famousRate++;
                    maxHeap.add(n);
                    date = LocalDate.now();
                    if(b!=null){
                        b.bookId.add(b.limit, bookId);
                        b.Date.add(b.limit, date);
                        b.returnTime.add(b.limit, returnTime);
                        b.limit++;
                        check = true;
                        System.out.println("book found the Date of borrowing is:" + date);
                    }
                    if (!check) {
                        Borrowed b1 = new Borrowed(name, age, grade, returnTime);
                        b1.bookId.add(b1.limit, bookId);
                        b1.Date.add(b1.limit, date);
                        System.out.println("book found the Date of borrowing is:" + date);
    //                    b1.returnTime.add(b1.limit, returnTime);
                        b1.limit++;

                        borrowedList.add(b1);
                    }
                    return 4;
                }
            }
        }
        public void searchBorrowedBook(int bookId){
            boolean check = false;
            System.out.print("Enter the book ID: ");
            for (Borrowed value : borrowedList) {
                for (int j = 0; j <  value.bookId.size(); j++) {
                    if (value.bookId.get(j).equals(bookId)) {
                        System.out.println("book found");
                        System.out.println( "copies remaining "+ BST.search(bookId).copies);
                        check = true;
                    }
                }
            }
            if(!check){
                System.out.println("book not found");
            }
        }
        public int updates(int oldId, int newId, String oldName, String newName) {

            Node oldBook = BST.search(oldId);
            Node newBook = BST.search(newId);

            if (oldBook == null || newBook == null) {
                return 0;
            }

            for (Borrowed b : borrowedList) {
                if (b == null) continue;

                if (b.name.equals(oldName) && b.bookId.contains(oldId)) {
                    int i = b.bookId.indexOf(oldId);


                    oldBook.copies++;
                    oldBook.famousRate--;
                    newBook.copies--;
                    newBook.famousRate++;


                    b.bookId.set(i, newId);

                    if (newName != null && !newName.isEmpty()) {
                        b.name = newName;
                    }

                    return 1;
                }
            }

            return 2;
        }
        public List<Node> famousRate (){
            PriorityQueue<Node> tempMaxHeap=new PriorityQueue<>(maxHeap);
            Node n =tempMaxHeap.poll();
            List<Node> result = new ArrayList<>();
            for(int i=0;i<3;i++){
                if(n==null){
                    System.out.println("there are not any other ranks");
                }
                else{
                    result.add(n);
                    System.out.println("the id of the book is: ");
                    System.out.println(n.value);
                    System.out.println("the most famous authors is:");
                    System.out.println(n.author);
                    n=tempMaxHeap.poll();
                }
            }

            return result;
        }
        public int returnBook(String name, int bookId){
            Borrowed b=null;
            int found = -1;
            boolean check = false;

            if(BST.search(bookId)==null) {
                System.out.println("book not found");
                return 1;
            }
            else {
                for (Borrowed value : borrowedList) {
                    for ( int j = 0; j < value.limit ; j++) {
                        if(value.name.equals(name) && value.bookId.get(j) == bookId) {
                            b = value;
                            found = j;
                            check = true;
                        }
                    }
                }

                if(!check) {
                    System.out.println("there is an erroneous ");
                    return 2;
                }
                else {
                    if (b.limit == 1) {
                        b.bookId.remove(0);
                        borrowedList.remove(b);
    //                 borrowedList.remove(bookId);
                    } else{
                        b.bookId.remove(found);
                    b.returnTime.remove(found);
                    b.Date.remove(found);
                    }

                    Node n = BST.search(bookId);
                    n.copies++;
                    System.out.println("thank you for returning the book");
                    b.limit--;
                    System.out.println("copies remaining " + BST.search(bookId).copies);
                    return 3;
                }
            }

        }
        public void show(){
            for (Borrowed s : borrowedList) {
                System.out.println(s);
            }

        }

    }
