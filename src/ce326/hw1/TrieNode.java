package ce326.hw1;

public class TrieNode {
    String word;
    TrieNode[] children;
    boolean isTerminalNode;
    TrieNode parent;

    public TrieNode() {
        children = new TrieNode[26];
    }

    public TrieNode(String word) {
        setWord(word);
        children = new TrieNode[26];
    }

    public TrieNode(String word,boolean bool) {
        setWord(word);
        children = new TrieNode[26];
        isTerminalNode = bool;
    }

    public void setWord(String word){              //sets word of node
        this.word = word;
    }

    public void setIsTerminalNode(boolean b){   //sets boolean node(if end of word)
        isTerminalNode = b;
    } //sets boolean of node(if end of word)
    public boolean getIsTerminalNode() {
        return isTerminalNode;
    }

    public void setParent(TrieNode parent){
        this.parent = parent;
    }

    public void setChild(int i, TrieNode c){
        if(i > 25){
            return;
        }
        children[i] = c;
    }
}

