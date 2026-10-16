public class Stars {
    int x;
    int y;
    int z;
    String name;
    Stars(int x, int y, int z ,String name ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }
    public Double Distance(Stars s) {
        return Math.sqrt(Math.pow(this.x - s.x, 2) + Math.pow(this.y - s.y, 2));
    }
    public Double Time(Stars s) {
        return (Math.sqrt(Math.pow(this.x - s.x, 2) + Math.pow(this.y - s.y, 2))/2);
    }
}
