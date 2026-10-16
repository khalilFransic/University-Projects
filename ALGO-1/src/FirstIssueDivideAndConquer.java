import java.util.ArrayList;

public class FirstIssueDivideAndConquer {


    public static int dist( Stars a, Stars b ) {
        int dx=a.x-b.x;
        int dy=a.y-b.y;
        int dz=a.z-b.z;

        return dx*dx+dy*dy +dz*dz;

    }
    public static int bruteForce( ArrayList< Stars > stars ) {
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < stars.size(); i++) {
            for (int j = i + 1; j < stars.size(); j++) {
                min = Math.min(min, dist(stars.get(i), stars.get(j)));
            }
        }
        return min;
    }

    static int stripClosest(ArrayList<Stars> strip, int d) {

        int min = d;

        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size(); j++) {

                int dy = strip.get(j).y - strip.get(i).y;
                if (dy*dy >= min) break;

                min = Math.min(min , dist(strip.get(i), strip.get(j)));
            }
        }
        return min;
    }
    public static int div( ArrayList< Stars > stars ){

        if(stars.size()<=3){
            return bruteForce(stars);
        }
        int mid = stars.size()/2;
        int midX=stars.get(mid).x;
        ArrayList<Stars> left=new ArrayList<>(mid);
        ArrayList<Stars> right=new ArrayList<>(stars.size()-mid);

        int dLeft=div(left);
        int dRight=div(right);


        int d=Math.min(dLeft,dRight);

        ArrayList<Stars> strip=new ArrayList<>();
        for(Stars s: stars){
            int dx=s.x-midX;
            if(dx*dx<d){
                strip.add(s);
            }
        }

        return Math.min(d, stripClosest(strip, d));
    }


}
