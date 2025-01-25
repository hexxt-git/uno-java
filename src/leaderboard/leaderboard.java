package leaderboard;

// Interface defining leaderboard operations
// Used to track and persist player scores
public interface leaderboard {
    int calculateScore();                         // Calculate final score
    void createScore(int score, String name);     // Initialize player score
    void updateScore(int score, String name);     // Update existing score
    void printLeaderboard();                      // Save scores to file
}