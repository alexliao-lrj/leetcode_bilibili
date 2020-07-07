/**
 * Alex Liao
 * July, 2020
 */
class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        Set<String> res = new HashSet();
        
        Trie trie = new Trie();
        for(String s : words){
            insert(s, trie);
        }
        
        int n = board.length;
        int m = board[0].length;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                Trie cur = trie;
                StringBuilder temp = new StringBuilder();
                temp.append(board[i][j]);
                boolean[][] visited = new boolean[n][m];
                dfs(board, i, j, cur.children[board[i][j] - 'a'], temp, res, visited);
            }
        }
        List<String> ret = new ArrayList();
        ret.addAll(res);
        return ret;
    }
    
    public void dfs(char[][] board, int x, int y, Trie root, StringBuilder temp, Set<String> res, boolean[][] visited){
        if(root == null){
            return;
        }
        
        if(root.isWord){
            res.add(new String(temp.toString()));
        }
    
        visited[x][y] = true;
        int n = board.length;
        int m = board[0].length;
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};
        
        for(int i = 0; i < dx.length; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];
            if(nx >= 0 && nx < n && ny >= 0 && ny < m && !visited[nx][ny]){
                int nc = board[nx][ny] - 'a';
                if(root.children[nc] == null){
                    continue;
                }
                temp.append(board[nx][ny]);
                dfs(board, nx, ny, root.children[nc], temp, res, visited);
                temp.setLength(temp.length() - 1);
            }
        }
        visited[x][y] = false;
    }
    
    private void insert(String s, Trie root){
        Trie cur = root;
        for(int i = 0; i < s.length(); i++){
            int c = s.charAt(i) - 'a';
            if(cur.children[c] == null){
                cur.children[c] = new Trie();
            }
            cur = cur.children[c];
        }
        cur.isWord = true;
    }
    
    private boolean search(String s, Trie root){
        Trie cur = root;
        for(int i = 0; i < s.length(); i++){
            int c = s.charAt(i) - 'a';
            if(cur.children[c] == null){
                return false;
            }
            cur = cur.children[c];
        }
        return cur.isWord;
    }
    
    class Trie{
        boolean isWord;
        Trie[] children;
        public Trie(){
            isWord = false;
            children = new Trie[26];
        }
    }
}