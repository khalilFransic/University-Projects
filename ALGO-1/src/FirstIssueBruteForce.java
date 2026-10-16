import java.util.ArrayList;

public class FirstIssueBruteForce {
    public static void comparing (ArrayList<Stars> star){

        int minDist=Integer.MAX_VALUE;
        ArrayList<Stars> closeStars=new ArrayList<>();
        int index1=0;
        int index2=0;

        for( int i = 0; i < star.size(); i++){
            for( int j = i+1; j < star.size(); j++){
                int x= star.get(i).x-star.get(j).x;
                int y= star.get(i).y-star.get(j).y;
                int z= star.get(i).z-star.get(j).z;

                int dist = x*x+ y*y + z*z;
                if(dist<minDist){
                    minDist=dist;
                    index1=i;
                    index2=j;
                }
            }
        }
        closeStars.add(star.get(index1));
        closeStars.add(star.get(index2));


        System.out.println("the closet stars are " +closeStars.get(0).name+" and "+closeStars.get(1).name+"the distance is:"+minDist);

    }
}
