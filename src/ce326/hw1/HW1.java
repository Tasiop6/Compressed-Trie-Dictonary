package ce326.hw1;

import java.util.Scanner;

public class HW1 {

    public static boolean notOnlyAlphabet(String str, int n) {
        //Check if word doesn't only have ASCII characters
        if (!str.matches("\\A\\p{ASCII}*\\z")) {
            return true;
        }
        //Traverse the string from start to end
        for (int i = 0; i < n; i++) {
            //Check if the specified character
            //is not a letter then return true
            if (!Character.isLetter(str.charAt(i))) {
                return true;

            }
        }
        //Word only has letters
        return false;
    }

    public static void main(String[] args) {
        TrieNode root = new TrieNode();
        Trie trie = new Trie(root);
        Scanner sc = new Scanner(System.in);
        System.out.println("?: ");
        while (sc.hasNext()) {
            //read case
            String cases = sc.next();
            switch (cases) {

                //Insert
                case "-i":
                    //Read word after case and check if it contains
                    //only english characters
                    String insertWord = sc.next();
                    if (notOnlyAlphabet(insertWord, insertWord.length())) {
                        break;
                    }
                    //Convert letters to lower case
                    insertWord = insertWord.toLowerCase();
                    //Start insert method from root
                    trie.insert(root, insertWord, insertWord);
                    break;

                //Remove
                case "-r":
                    //Read word after case and check if it contains
                    //only english characters
                    String removeWord = sc.next();
                    if (notOnlyAlphabet(removeWord, removeWord.length())) {
                        break;
                    }
                    //Convert letters to lower case
                    removeWord = removeWord.toLowerCase();
                    trie.remove(root, removeWord, removeWord);
                    break;

                //Find
                case "-f":
                    //Read word after case and check if it
                    //contains only english characters
                    String findWord = sc.next();
                    if (notOnlyAlphabet(findWord, findWord.length())) {
                        break;
                    }
                    //Convert letters to lower case
                    findWord = findWord.toLowerCase();
                    //Return result of find and print right message
                    trie.find(root, findWord, findWord);
                    break;

                //Pre-order
                case "-p":
                    System.out.print("PreOrder: ");
                    //Start pre-order method from root
                    trie.preOrder(root);
                    System.out.println();
                    break;

                //Dictionary
                case "-d":
                    System.out.format("\n***** Dictionary *****\n");
                    trie.dictionary(root, "");
                    System.out.println();
                    break;

                //Distance
                case "-w":
                    //Read word after case and check if it
                    //contains only english characters
                    String distanceWord = sc.next();
                    if (notOnlyAlphabet(distanceWord, distanceWord.length())) {
                        break;
                    }
                    //Convert letters to lower case
                    distanceWord = distanceWord.toLowerCase();
                    //Read distance after word
                    int distance = sc.nextInt();
                    System.out.format("\nDistant words of %s (%d):\n", distanceWord, distance);
                    trie.distantWords(root, "", distanceWord, distance);
                    System.out.println();
                    break;

                //Suffix
                case "-s":
                    //Read suffix after case and check if it
                    //contains only english characters
                    String suffix = sc.next();
                    if (notOnlyAlphabet(suffix, suffix.length())) {
                        break;
                    }
                    suffix = suffix.toLowerCase();
                    System.out.format("\nWords with suffix %s:\n", suffix);
                    trie.sameSuffix(root, "", suffix);
                    System.out.println();
                    break;

                //Quit
                case "-q":
                    System.out.println("Bye bye!");
                    return;
            }
            System.out.println("?: ");
        }
    }
}