package ce326.hw1;

public class Trie {
    TrieNode root ;

    public Trie(TrieNode w){
        root = w;
    }


    // -i : Insert word method-------------------------------------------------------------
    public void insert(TrieNode currentNode, String word, String startingWord) {
        // Find the index of first letter of word
        int index = word.charAt(0)-'a';
        // Case 0: Node is our root
        // check if we are at root
        if (currentNode.word == null){
            // We are in the root, so we check
            // the child in index spot
            if (currentNode.children[index] == null){
                // Create new node for word
                TrieNode wordNode = new TrieNode(word,true);
                currentNode.setChild(index, wordNode);
                wordNode.setParent(currentNode);
                System.out.format("ADD %s OK\n", startingWord);
            }else{
                // Child in index has node so
                // we recursively insert word
                insert(currentNode.children[index], word, startingWord);
            }
        }else {
            // Other cases.
            // Find overlap of words(min length)
            int overlap = Math.min(currentNode.word.length(), word.length());
            int count;
            // For loop to find the amount of
            // same letters of overlapping parts
            for (count = 0; count < overlap; count++) {
                // Comparing the characters until
                // we find a different character
                if (currentNode.word.charAt(count) != word.charAt(count)) {
                    break;
                }
            }
            // Case 1: Words don't match at all.This means the first letter
            // of the nodeWord is different from the insertWord
            if (count == 0) {
                // If child in index spot is empty we add new node to it
                if (currentNode.children[index] == null) {
                    TrieNode wordNode = new TrieNode(word, true);
                    currentNode.setChild(index, wordNode);
                    wordNode.setParent(currentNode);
                    System.out.format("ADD %s OK\n", startingWord);
                }else {
                    // Child not empty we recursively move
                    // and insert in the childNode
                    insert(currentNode.children[index], word, startingWord);
                }
            }else {
                // Case 2: Overlapped parts match.
                // Case 2.1: insertWord > nodeWord
                // Case 2.2: NodeWord > insertWord
                // Case 2.3: NodeWord == insertWord
                if (count == overlap) {
                    //case 2.1
                    if (word.length() > currentNode.word.length()) {
                        // Create new suffix based on overlap
                        // (end of insertWord that doesn't match)
                        String suffix = word.substring(overlap);
                        // Find new index of suffix
                        // Check if child of node is empty
                        index = suffix.charAt(0) - 'a';
                        if (currentNode.children[index] == null) {
                            // Add suffixNode to children of currentNode
                            TrieNode suffixNode = new TrieNode(suffix, true);
                            currentNode.setChild(index, suffixNode);
                            suffixNode.setParent(currentNode);
                            System.out.format("ADD %s OK\n", startingWord);
                        }else {
                            // Child not empty we recursively move in trie
                            // and insert suffix in the childNode
                            insert(currentNode.children[index], suffix, startingWord);
                        }
                        // Case 2.2
                    }else if (word.length() < currentNode.word.length()) {
                        // Create prefix and suffix of node word:
                        // prefix goes to currentNode and suffix goes
                        // to new node which is child of currentNode
                        String prefix = currentNode.word.substring(0, overlap);
                        String suffix = currentNode.word.substring(overlap);
                        currentNode.setWord(prefix);
                        // New node with suffix
                        TrieNode suffixNode = new TrieNode(suffix);
                        suffixNode.setParent(currentNode);
                        // If currentNode terminal then suffixNode terminal
                        if (currentNode.getIsTerminalNode()) {
                            suffixNode.setIsTerminalNode(true);
                        }
                        // CurrenNode is terminal as prefix
                        //in part or whole of inserted word
                        currentNode.setIsTerminalNode(true);
                        // Copy all children of currentNode to suffixNode
                        for (int i = 0; i < 26; i++) {
                            if (currentNode.children[i] != null) {
                                suffixNode.setChild(i, currentNode.children[i]);
                                // Parent of child becomes suffix node
                                if (suffixNode.children[i] != null) {
                                    suffixNode.children[i].setParent(suffixNode);
                                }
                                // Delete currentNode's children after transferring
                                currentNode.children[i] = null;
                            }
                        }
                        // Add suffixNode to children of current
                        // and find index of suffix to place
                        // in correct children spot
                        index = suffix.charAt(0) - 'a';
                        currentNode.setChild(index, suffixNode);
                        System.out.format("ADD %s OK\n", startingWord);
                        // Case 2.3(nodeWord and insertWord are equal
                    } else {
                        // Check if currentNode terminal
                        if (currentNode.isTerminalNode) {
                            // Word already exists
                            System.out.format("ADD %s NOK\n", startingWord);
                        } else {
                            // Make currentNode terminal
                            currentNode.setIsTerminalNode(true);
                            System.out.format("ADD %s OK\n", startingWord);
                        }
                    }
                }else {
                    // Case 3: count < overlap
                    // This means that both nodeWord and
                    // insertWord have remaining suffixes
                    // after their matching prefixes.
                    // Find common prefix
                    String prefix = currentNode.word.substring(0, count);
                    // Find suffix of nodeWord and insertWord.
                    String suffixInsertWord = word.substring(count);
                    String suffixNodeWord = currentNode.word.substring(count);
                    // Create two new nodes for 1.suffixInsertWord and 2.suffixInsertWord
                    // 1.
                    TrieNode suffixInsertWordNode;
                    suffixInsertWordNode = new TrieNode(suffixInsertWord, true);
                    suffixInsertWordNode.setParent(currentNode);

                    // 2.
                    TrieNode suffixNodeWordNode;
                    suffixNodeWordNode = new TrieNode(suffixNodeWord);
                    //suffix gets terminal from currentNode
                    if(currentNode.getIsTerminalNode()){
                        suffixNodeWordNode.setIsTerminalNode(true);
                    }
                    suffixNodeWordNode.setParent(currentNode);
                    // Change currentNodeWord to prefix and not terminal anymore
                    currentNode.setWord(prefix);
                    currentNode.setIsTerminalNode(false);
                    // Check if currentNode and suffixNodeWordNode
                    // both have a child in index.
                    // Copy all non-null children of childNode to currentNode
                    for (int i = 0; i < 26; i++) {
                        if (currentNode.children[i] != null) {
                            suffixNodeWordNode.setChild(i, currentNode.children[i]);
                            // parent of child becomes suffix node
                            if (suffixNodeWordNode.children[i] != null) {
                                suffixNodeWordNode.children[i].setParent(suffixNodeWordNode);

                            }
                            // delete currentNode's children after transferring
                            currentNode.children[i] = null;
                        }
                    }
                    // Find index of suffixNodeWord
                    int indexNodeWord = suffixNodeWord.charAt(0) - 'a';
                    // Add suffixNodeWordNode to children of
                    // currentNode and place in correct children
                    // spot with the help of indexInsertWord
                    currentNode.setChild(indexNodeWord, suffixNodeWordNode);
                    // Find index of suffixInsertWord
                    int indexInsertWord = suffixInsertWord.charAt(0) - 'a';
                    // Add suffixInsertWordNode to children of
                    // currentNode and place in correct children
                    // spot with the help of indexNodeWord;
                    currentNode.setChild(indexInsertWord, suffixInsertWordNode);
                    System.out.format("ADD %s OK\n", startingWord);
                }
            }
        }
    }

    //-r: Remove word method---------------------------------------------------------
    public void remove(TrieNode currentNode, String word, String startingWord) {
        //Find the index of first letter of word
        int index = word.charAt(0)-'a';
        //Check if we are at root
        if (currentNode.word == null) {
            // We are in the root, so we check
            // the child in index spot
            if (currentNode.children[index] == null) {
                // Child empty so word doesn't exist
                System.out.format("RMV %s NOK\n", startingWord);
            } else {
                // ChildNode in index has word, so
                // we recursively find removeWord in it
                remove(currentNode.children[index], word, startingWord);
            }
        }else {
            // We are in a filled node
            // Find overlap of words(min length)
            int overlap = Math.min(currentNode.word.length(), word.length());
            // Compare the removeWord with the nodeWord
            // until we reach end of overlap
            boolean mismatch = false;
            for (int count = 0; count < overlap; count++) {
                // comparing the characters until
                // we find a different character
                if (currentNode.word.charAt(count) != word.charAt(count)) {
                    //We found a mismatch so word doesn't exist
                    mismatch = true;
                    break;
                }
            }
            if (mismatch) {
                System.out.format("RMV %s NOK\n", startingWord);
            }else if (word.length() == currentNode.word.length()) {
                // Compare the length of removeWord and nodeWord
                // If removeWord == nodeWord
                // Check if node is terminal
                if (currentNode.getIsTerminalNode()){
                    // We found the word, so we can remove it
                    // Find the amount of children of node
                    int childCount = 0;
                    // Index of child to check if we only have one
                    int indexChild = 0;
                    for (int count = 0; count<26; count++) {
                        if(currentNode.children[count] != null) {
                            childCount++;
                            indexChild = count;
                        }
                    }
                    // Case 1: node has more than one child
                    if (childCount > 1) {
                        // Node is not terminal anymore
                        currentNode.setIsTerminalNode(false);
                        System.out.format("RMV %s OK\n", startingWord);
                    } else if (childCount == 1) {
                        // Case 2: node has one child
                        // We need to merge currentNode with childNode
                        currentNode.word += currentNode.children[indexChild].word;
                        // If childNode not terminal
                        if (!currentNode.children[indexChild].getIsTerminalNode()) {
                            // Merged currentNode is not terminal
                            currentNode.setIsTerminalNode(false);
                        }
                        // Check if child of currentNode and child of
                        // childNode both have a child in indexChild.
                        if(currentNode.children[indexChild].children[indexChild] != null) {
                            // Copy all non-null children of childNode to currentNode
                            for (int i = 0; i < 26; i++) {
                                if (currentNode.children[indexChild].children[i] != null) {
                                    currentNode.setChild(i, currentNode.children[indexChild].children[i]);
                                    //parent of child becomes current node
                                    if (currentNode.children[i] != null) {
                                        currentNode.children[i].setParent(currentNode);
                                    }
                                }
                            }
                        }else {
                            // Copy all non-null children of childNode to
                            // currentNode and delete child of currentNode
                            // in indexChild as it is the old one.
                            for (int i = 0; i < 26; i++) {
                                if (currentNode.children[indexChild].children[i] != null && i != indexChild) {
                                    currentNode.setChild(i, currentNode.children[indexChild].children[i]);
                                    //parent of child becomes current node
                                    if (currentNode.children[i] != null) {
                                        currentNode.children[i].setParent(currentNode);
                                    }
                                }
                            }
                            currentNode.children[indexChild] = null;
                        }
                        System.out.format("RMV %s OK\n", startingWord);
                    }else {
                        // Case 3: Node has no children, so we remove it
                        //if we are in root
                        currentNode.parent.children[index] = null;
                        // Find the amount of children of node
                        int parentChildCount = 0;
                        // Index of currentNode in children of its parent
                        for (int count = 0; count<26; count++) {
                            if (currentNode.parent.children[count] != null) {
                                parentChildCount++;
                                indexChild = count;
                            }
                        }
                        if (parentChildCount == 1 &&
                                !currentNode.parent.getIsTerminalNode() &&
                                currentNode.parent.parent != null) {
                            // Parent has one child,
                            // is not terminal, and
                            // we are not in root.
                            // We need to merge childNode with its parent
                            currentNode.parent.word += currentNode.parent.children[indexChild].word;
                            // If child is terminal merged is terminal as well
                            if (currentNode.parent.children[indexChild].getIsTerminalNode()) {
                                currentNode.parent.setIsTerminalNode(true);
                            }
                            // Check if child of ChildNode and child of
                            // parentNode both have a child in index.
                            if (currentNode.parent.children[indexChild].children[indexChild] != null) {
                                // Copy all non-null children of childNode to parentNode
                                for (int i = 0; i < 26; i++) {
                                    if (currentNode.parent.children[indexChild].children[i] != null) {
                                        currentNode.parent.setChild(i, currentNode.parent.children[indexChild].children[i]);
                                        // Parent of child becomes parentNode
                                        if (currentNode.parent.children[i] != null) {
                                            currentNode.parent.children[i].setParent(currentNode.parent);
                                        }
                                    }
                                }
                            }else {
                                // Copy all non-null children of childNode to parentNode
                                //except child in index and delete child of
                                //parentNode in indexChild as it is the old one.
                                for (int i = 0; i < 26; i++) {
                                    if (currentNode.parent.children[indexChild].children[i] != null && i != indexChild) {
                                        currentNode.parent.setChild(i, currentNode.parent.children[indexChild].children[i]);
                                        // Parent of child becomes parentNode
                                        if (currentNode.parent.children[i] != null) {
                                            currentNode.parent.children[i].setParent(currentNode.parent);
                                        }
                                    }
                                }
                                currentNode.parent.children[indexChild] = null;
                            }
                            System.out.format("RMV %s OK\n", startingWord);
                        }else {
                            //No merge needed
                            System.out.format("RMV %s OK\n", startingWord);
                        }
                    }
                }else {
                    // Node not terminal so word doesn't exist
                    System.out.format("RMV %s NOK\n", startingWord);
                }
            } else if (word.length() < currentNode.word.length()) {
                // RemoveWord < nodeWord, so our word doesn't exist
                System.out.format("RMV %s NOK\n", startingWord);
            }else {
                // RemoveWord > nodeWord, so search
                // suffix of removeWord in children.
                // We find suffix of removeWord
                String suffix = word.substring(overlap);
                // Find the index of first letter of suffix
                index = suffix.charAt(0)-'a';
                // Check if ChildNode in index spot is empty
                if(currentNode.children[index] == null) {
                    // Child empty so word doesn't exist
                    System.out.format("RMV %s NOK\n", startingWord);
                }else {
                    // Recursively find suffix in childNode
                    remove(currentNode.children[index], suffix, startingWord);
                }
            }
        }
    }

    // -f: Find word method------------------------------------------------------------
    public void find(TrieNode currentNode,String word, String startingWord){
        // Find the index of first letter of word
        int index = word.charAt(0)-'a';
        // Check if we are at root
        if (currentNode.word == null) {
            // We are in the root, so we check
            // the child in index spot
            if (currentNode.children[index] == null) {
                // Child empty so word doesn't exist
                System.out.format("FND %s NOK\n", startingWord);
            } else {
                // ChildNode in index has word, so
                // we recursively find word in it
                find(currentNode.children[index], word, startingWord);
            }
        }else {
            // We are in a filled node
            // Find overlap of words(min length)
            int overlap = Math.min(currentNode.word.length(), word.length());
            // Compare the findWord with the nodeWord
            // until we reach end of overlap
            for (int count = 0; count < overlap; count++) {
                // Comparing the characters until
                // we find a different character
                if (currentNode.word.charAt(count) != word.charAt(count)) {
                    // We found a mismatch so word doesn't exist
                    System.out.format("FND %s NOK\n", startingWord);
                }
            }
            // Compare the length of findWord and nodeWord
            // If findWord == nodeWord
            if (word.length() == currentNode.word.length()) {
                // Check if node is terminal
                if (currentNode.getIsTerminalNode()) {
                    // We found the word
                    System.out.format("FND %s OK\n", startingWord);
                }else {
                    // Node not terminal so word doesn't exist
                    System.out.format("FND %s NOK\n", startingWord);
                }
            } else if (word.length() < currentNode.word.length()) {
                // FindWord < nodeWord, so our word doesn't exist
                System.out.format("FND %s NOK\n", startingWord);
            }else {
                // FindWord > nodeWord, so search
                // suffix of findWord in children.
                // We find suffix of findWord
                String suffix = word.substring(overlap);
                // Find the index of first letter of suffix
                index = suffix.charAt(0)-'a';
                // Check if ChildNode in index spot is empty
                if(currentNode.children[index] == null) {
                    // Child empty so word doesn't exist
                    System.out.format("FND %s NOK\n", startingWord);
                }else {
                    // Recursively find suffix in childNode
                    find(currentNode.children[index], suffix, startingWord);
                }
            }
        }
    }

    // -p: Pre-order method----------------------------------------------------------------
    public void preOrder(TrieNode currentNode){
        // If nodeWord == null we are in root,
        // so we preorder its children.
        if(currentNode.word == null) {
            for (int count = 0; count < 26; count++) {
                // Check if child exists
                if(currentNode.children[count] != null) {
                    preOrder(currentNode.children[count]);
                }
            }
        }else {
            // Node has word
            // Check if it is terminal
            if (currentNode.getIsTerminalNode()) {
                // Print # with the word
                System.out.print(currentNode.word + "# ");
            } else {
                // Node not terminal print just word
                System.out.print(currentNode.word + " ");
            }
            // Now traverse the children in order
            // and recursively preorder them
            for (int count = 0; count < 26; count++) {
                // Check if child exists
                if(currentNode.children[count] != null) {
                    preOrder(currentNode.children[count]);
                }
            }
        }
    }

    //-d: Dictionary method----------------------------------------------------
    public void dictionary(TrieNode currentNode,String word) {
        // If nodeWord == null we are in
        // root, so we search its children.
        if(currentNode.word == null) {
            for (int count = 0; count < 26; count++) {
                // Check if child exists
                if(currentNode.children[count] != null) {
                    dictionary(currentNode.children[count], word);
                }
            }
        }else {
            // Node has word
            // Add nodeWord to our word
            word += currentNode.word;
            // Check if currentNode is terminal
            if (currentNode.isTerminalNode) {
                // Print our concatenated word
                System.out.println(word);
            }
            // Recursively enter children
            // in order to find rest of words
            for (int count = 0; count < 26; count++) {
                // Check if child exists
                if (currentNode.children[count] != null) {
                    dictionary(currentNode.children[count], word);
                }
            }
        }
    }

    //-w: Distant words method-------------------------------------------------------------
    public void distantWords(TrieNode currentNode, String nodeWord, String word, int distance) {
        // If nodeWord == null we are in
        // root, so we search its children.
        if(currentNode.word == null) {
            for (int count = 0; count < 26; count++) {
                // Check if child exists
                if(currentNode.children[count] != null) {
                    distantWords(currentNode.children[count], nodeWord, word, distance);
                }
            }
        }else {
            // Node has word
            // Add nodeWord to our word
            nodeWord += currentNode.word;
            // Check if nodeWord is bigger than word
            if (nodeWord.length() > word.length()) {
                return;
            }
            // Check if currentNode is terminal
            if (currentNode.isTerminalNode) {
                // Compare nodeWord with word
                // Check length
                if (nodeWord.length() == word.length()) {
                    // Character counter
                    int charCounter = 0;
                    // Check amount of different characters
                    for (int count = 0; count < word.length(); count++) {
                        // Add to character counter if
                        // you find different characters
                        if (nodeWord.charAt(count) != word.charAt(count)) {
                            charCounter++;
                        }
                    }
                    // Compare character counter with distance
                    if (charCounter == distance) {
                        //Print word
                        System.out.println(nodeWord);
                    }
                }
            }
            // Recursively enter children
            // in order to find rest of
            // words with same distance
            for (int count = 0; count < 26; count++) {
                // Check if child exists
                if (currentNode.children[count] != null) {
                    distantWords(currentNode.children[count], nodeWord, word, distance);
                }
            }
        }
    }

    // -s: Same suffix method -----------------------------------------------------
    public void sameSuffix(TrieNode currentNode, String nodeWord, String suffix) {
        // If nodeWord == null we are in
        // root, so we search its children.
        if(currentNode.word == null) {
            for (int count = 0; count < 26; count++) {
                // Check if child exists
                if(currentNode.children[count] != null) {
                    sameSuffix(currentNode.children[count], nodeWord, suffix);
                }
            }
        }else {
            // Node has word.
            // Add nodeWord to our word
            nodeWord += currentNode.word;
            // Check if currentNode is terminal & nodeWord > suffix
            if (currentNode.isTerminalNode && nodeWord.length() > suffix.length()) {
                // Find nodeWord length and suffix
                // length and compute character
                // amount of prefix of nodeWord
                int nodeWordPrefix =  nodeWord.length() - suffix.length();
                // Find nodeWordSuffix using prefix amount
                String nodeWordSuffix = nodeWord.substring(nodeWordPrefix);
                // Compare nodeWordSuffix with suffix
                if (nodeWordSuffix.equals(suffix)) {
                    // Print nodeWord
                    System.out.println(nodeWord);
                }
            }
            // Recursively enter children
            // in order to find rest words
            // with the same suffix
            for (int count = 0; count < 26; count++) {
                // Check if child exists
                if (currentNode.children[count] != null) {
                    sameSuffix(currentNode.children[count], nodeWord, suffix);
                }
            }
        }
    }
}
