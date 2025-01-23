package leaderboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;
import java.io.*;
import java.nio.Buffer;



public class leaderboardcl implements leaderboard{
    private static class score {
        String name ;
        int score ;

        public score(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }
    private ArrayList<score> scores;
    public leaderboardcl() {
        scores = new ArrayList<score>();
    }

    

    @Override
    public void printLeaderboard() {
        Collections.sort(scores, new Comparator<score>() {
            @Override
            public int compare(score o1, score o2) {
                return Integer.compare(o2.score, o1.score);
            }
        });
      String gameid=UUID.randomUUID().toString();
      String filename="leaderbaord"+gameid+".txt";
     
      try{
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
    for (score s : scores) {
        writer.write(s.name + " " + s.score + "\n");
    }
    writer.close();

      }catch(IOException e){
          System.out.println(e );

      }
        
    }

    @Override
    public void createScore(int score, String name) {
        scores.add(new score(name, score));
      
        
    }

    @Override
    public void updateScore(int score, String name) {
        for (score s : scores) {
            if (s.name.equals(name)) {
                s.score = score;
            }
        }
    }



    @Override
    public int calculateScore() {
        
        throw new UnsupportedOperationException("Unimplemented method 'calculateScore'");
    }
}
