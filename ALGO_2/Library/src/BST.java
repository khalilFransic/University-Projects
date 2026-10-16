import java.util.ArrayList;
import java.util.List;

public class BST {
    Node root ;
    public BST( ) {
        this.root = null;
    }

    public Node search(int value) {

        return searchHelper(this.root, value);
    }

    private Node searchHelper(Node current, int value) {

        if (current == null) {
            return current;
        }
        if (current.value == value) {
            return current;
        }
        if (current.value < value) {
            return searchHelper(current.right, value);
        }

        return searchHelper(current.left, value);
    }

    private int height(Node node) {
        return (node == null) ? 0 : node.height;
    }

    private int getBalance(Node node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }
    public void insert(int bookId, int copies,String author,String title) {
        this.root = insertHelper(this.root, bookId, copies,author,title);
    }
    private Node insertHelper(Node current, int bookId, int copies, String author, String title) {

        if (current == null) {
            return new Node(bookId, copies,author,title);
        }

        if (bookId < current.value) {
            current.left = insertHelper(current.left, bookId, copies ,author,title);
        } else if (bookId > current.value) {
            current.right = insertHelper(current.right, bookId, copies ,author,title);
        } else {

            System.out.println("Book ID " + bookId + " already exists. Copies left unchanged.");
            return current;
        }

        current.height = 1 + Math.max(height(current.left), height(current.right));

        int balance = getBalance(current);

        if (balance > 1 && bookId < current.left.value) {
            return rightRotate(current);
        }

        if (balance < -1 && bookId > current.right.value) {
            return leftRotate(current);
        }

        if (balance > 1 && bookId > current.left.value) {
            current.left = leftRotate(current.left);
            return rightRotate(current);
        }

        if (balance < -1 && bookId < current.right.value) {
            current.right = rightRotate(current.right);
            return leftRotate(current);
        }

        return current;
    }
    public void delete(int value) {
        this.root = deleteHelper(this.root, value);
    }
    private Node deleteHelper(Node current, int value) {

        if (current == null) {
            return current;
        }

        if (value < current.value) {
            current.left = deleteHelper(current.left, value);
        } else if (value > current.value) {
            current.right = deleteHelper(current.right, value);
        } else {

            if ((current.left == null) || (current.right == null)) {
                Node temp = (current.left != null) ? current.left : current.right;

                if (temp == null) {

                    temp = current;
                    current = null;
                } else {

                    current = temp;
                }
            } else {

                Node temp = minValueNode(current.right);

                current.value = temp.value;

                current.right = deleteHelper(current.right, temp.value);
            }
        }

        if (current == null) {
            return current;
        }

        current.height = Math.max(height(current.left), height(current.right)) + 1;

        int balance = getBalance(current);

        if (balance > 1 && getBalance(current.left) >= 0) {
            return rightRotate(current);
        }

        if (balance > 1 && getBalance(current.left) < 0) {
            current.left = leftRotate(current.left);
            return rightRotate(current);
        }

        if (balance < -1 && getBalance(current.right) <= 0) {
            return leftRotate(current);
        }

        if (balance < -1 && getBalance(current.right) > 0) {
            current.right = rightRotate(current.right);
            return leftRotate(current);
        }

        return current;
    }
    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    public void setCopies(int bookId, int newCopyCount) {
        setCopiesHelper(this.root, bookId, newCopyCount);
    }
    private void setCopiesHelper(Node current, int bookId, int newCopyCount) {
        if (current == null) {
            System.out.println("Book ID " + bookId + " not found.");
            return;
        }

        if (bookId == current.value) {
            current.copies = newCopyCount;
            System.out.println("Book " + bookId + " updated to " + newCopyCount + " copies.");
            return;
        }

        if (bookId < current.value) {
            setCopiesHelper(current.left, bookId, newCopyCount);
        } else {
            setCopiesHelper(current.right, bookId, newCopyCount);
        }
    }
    public void inorder() {
        copies = 0;
        inorderHelper(this.root);
        System.out.println("there are "+copies+" Books left");
    }
    int copies;
    private void inorderHelper(Node current) {
        if (current == null) return;
        inorderHelper(current.left);
        copies+=current.copies;
        inorderHelper(current.right);


    }
    public List<Node> showBooks(){
        books.clear();
        List<Node> result = showBooksHelper(this.root);
        return result;
    }
    List<Node> books = new ArrayList<>();
    private List<Node> showBooksHelper(Node current) {
        if (current == null) return books;
        showBooksHelper(current.left);
        books.add(current);
        showBooksHelper(current.right);
        return books;
    }

}
