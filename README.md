# Compressed-Trie-Dictonary
Implementation of an English dictionary using a compressed Trie, based on the English alphabet (a-z) radix.
Compressed Trie nodes without multiple children merge into one. Joined nodes represent strings instead of individual characters.
Features:

1. -i word : Inserts the word 'word' into the Trie.

2. -r word : Deletes the word 'word' from the Trie.

3. -f word : Searches for the word 'word' in the Trie.

4. -p : Prints the pre-order traversal of the Trie. If a node is terminal, the string "#" is printed immediately after its content.

5. -d : Prints the set of words in the stored dictionary in alphabetical order.

6. -w word X : Searches the Trie for all words of the same length as the word 'word' that are exactly X characters away from the given word.

7. -s suffix : Searches the Trie for words containing the given suffix.

8. -q : Terminates the program.







