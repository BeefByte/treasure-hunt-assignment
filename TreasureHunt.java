/**
 * TreasureHunt class for finding the safest path in a grid
 * Alex Pupaza
 * Course: COP3503 - Fall 2025
 */
public class TreasureHunt {
    
    /**
     * Recursive backtracking approach to find minimum risk path
     * Time Complexity: O(2^(n+m))
     */
    public int findMinRiskRecursive(int[][] grid, int row, int col) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // Base case: reached destination
        if (row == rows - 1 && col == cols - 1) {
            return grid[row][col];
        }
        
        // If at bottom row, can only move right
        if (row == rows - 1) {
            return grid[row][col] + findMinRiskRecursive(grid, row, col + 1);
        }
        
        // If at rightmost column, can only move down
        if (col == cols - 1) {
            return grid[row][col] + findMinRiskRecursive(grid, row + 1, col);
        }
        
        // Recursive case: try both right and down moves
        int rightRisk = findMinRiskRecursive(grid, row, col + 1);
        int downRisk = findMinRiskRecursive(grid, row + 1, col);
        
        return grid[row][col] + Math.min(rightRisk, downRisk);
    }
    
    /**
     * Memoization approach to find minimum risk path
     * Time Complexity: O(n × m)
     */
    public int findMinRiskMemoization(int[][] grid, int row, int col, int[][] memo) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // Check if result is already memoized
        if (memo[row][col] != -1) {
            return memo[row][col];
        }
        
        // Base case: reached destination
        if (row == rows - 1 && col == cols - 1) {
            memo[row][col] = grid[row][col];
            return memo[row][col];
        }
        
        int result;
        
        // If at bottom row, can only move right
        if (row == rows - 1) {
            result = grid[row][col] + findMinRiskMemoization(grid, row, col + 1, memo);
        }
        // If at rightmost column, can only move down
        else if (col == cols - 1) {
            result = grid[row][col] + findMinRiskMemoization(grid, row + 1, col, memo);
        }
        // Recursive case: try both right and down moves
        else {
            int rightRisk = findMinRiskMemoization(grid, row, col + 1, memo);
            int downRisk = findMinRiskMemoization(grid, row + 1, col, memo);
            result = grid[row][col] + Math.min(rightRisk, downRisk);
        }
        
        // Store result in memoization table
        memo[row][col] = result;
        return result;
    }
    
    /**
     * Tabulation approach to find minimum risk path
     * Time Complexity: O(n × m)
     */
    public int findMinRiskTabulation(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        
        // Create DP table
        int[][] dp = new int[rows][cols];
        
        // Initialize starting point
        dp[0][0] = grid[0][0];
        
        // Fill first row (can only come from left)
        for (int col = 1; col < cols; col++) {
            dp[0][col] = dp[0][col - 1] + grid[0][col];
        }
        
        // Fill first column (can only come from top)
        for (int row = 1; row < rows; row++) {
            dp[row][0] = dp[row - 1][0] + grid[row][0];
        }
        
        // Fill rest of the table
        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                dp[row][col] = grid[row][col] + Math.min(dp[row - 1][col], dp[row][col - 1]);
            }
        }
        
        return dp[rows - 1][cols - 1];
    }
}
