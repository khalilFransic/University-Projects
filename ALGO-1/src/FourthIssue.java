public class FourthIssue { //counter of the possible ways
    static int count = 0;

    static int[] pages = {50, 100, 150, 200};

    static void backtrack(int bookIndex, int sumPages, int sumAges) {


        if (bookIndex == 5) {


            int averageAge = sumAges / 5;


            if (averageAge == 3 && sumPages >= 400 && sumPages <= 800) {
                count++;
            }
            return;
        }


        for (int i = 0; i < pages.length; i++) {

            backtrack(
                    bookIndex + 1,
                    sumPages + pages[i],
                    sumAges + (bookIndex + 1)
            );
        }
    }



}
