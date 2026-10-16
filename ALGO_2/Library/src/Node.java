import java.util.PriorityQueue;

public class Node {
    int value;
    Node right;
    Node left;
    int height;
    int copies;
    String state;
    int famousRate;
    String author;
    String title;
    int totalCopies;
    PriorityQueue<Students> queue =
            new PriorityQueue<>((s1, s2) -> {

                if (s1.graduated != s2.graduated) {
                    return Boolean.compare(s2.graduated, s1.graduated);
                }

                return Long.compare(s1.arrivalOrder, s2.arrivalOrder);
            });
    public String toString() {
        return  title  + value +  author + copies;
    }
    public Node(int value , int copies, String author,
                String title) {
        this.value = value;
        this.right = null;
        this.left = null;
        this.copies = copies;
        this.totalCopies = copies;
        this.height = 1;
        this.state = "existing";
        this.author = author;
        this.title = title;
    }

}
