package leaderboard;


interface leaderboard {
    int calculateScore();
    void createScore(int score, String name);
    void updateScore(int score, String name);
    void printLeaderboard();
}