/*
 * LinkedTree.java
 *
 * Computer Science 112
 *
 * Modifications and additions by:
 *     name:
 *     username:
 */

import java.util.*;

/*
 * LinkedTree - a class that represents a binary tree containing data
 * items with integer keys.  If the nodes are inserted using the
 * insert method, the result will be a binary search tree.
 */
public class LinkedTree {
    // An inner class for the nodes in the tree
    private class Node {
        private int key;         // the key field
        private LLList data;     // list of data values for this key
        private Node left;       // reference to the left child/subtree
        private Node right;      // reference to the right child/subtree
        private Node parent;     // reference to the parent. NOT YET MAINTAINED!
        
        private Node(int key, Object data){
            this.key = key;
            this.data = new LLList();
            this.data.addItem(data, 0);
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }
    
    // the root of the tree as a whole
    private Node root;
    
    public LinkedTree() {
        root = null;
    }
    
    /*
     * Prints the keys of the tree in the order given by a preorder traversal.
     * Invokes the recursive preorderPrintTree method to do the work.
     */
    public void preorderPrint() {
        if (root != null) {
            preorderPrintTree(root);      
        }
        System.out.println();
    }
    
    /*
     * Recursively performs a preorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the 
     * entire tree. 
     */
    private static void preorderPrintTree(Node root) {
        System.out.print(root.key + " ");
        if (root.left != null) {
            preorderPrintTree(root.left);
        }
        if (root.right != null) {
            preorderPrintTree(root.right);
        }
    }
    
    /*
     * Prints the keys of the tree in the order given by a postorder traversal.
     * Invokes the recursive postorderPrintTree method to do the work.
     */
    public void postorderPrint() {
        if (root != null) {
            postorderPrintTree(root);      
        }
        System.out.println();
    }
    
    /*
     * Recursively performs a postorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the 
     * entire tree. 
     */
    private static void postorderPrintTree(Node root) {
        if (root.left != null) {
            postorderPrintTree(root.left);
        }
        if (root.right != null) {
            postorderPrintTree(root.right);
        }
        System.out.print(root.key + " ");
    }
    
    /*
     * Prints the keys of the tree in the order given by an inorder traversal.
     * Invokes the recursive inorderPrintTree method to do the work.
     */
    public void inorderPrint() {
        if (root != null) {
            inorderPrintTree(root);      
        }
        System.out.println();
    }
    
    /*
     * Recursively performs an inorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the 
     * entire tree. 
     */
    private static void inorderPrintTree(Node root) {
        if (root.left != null) {
            inorderPrintTree(root.left);
        }
        System.out.print(root.key + " ");
        if (root.right != null) {
            inorderPrintTree(root.right);
        }
    }
    
    /* 
     * Inner class for temporarily associating a node's depth
     * with the node, so that levelOrderPrint can print the levels
     * of the tree on separate lines.
     */
    private class NodePlusDepth {
        private Node node;
        private int depth;
        
        private NodePlusDepth(Node node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }
    
    /*
     * Prints the keys of the tree in the order given by a
     * level-order traversal.
     */
    public void levelOrderPrint() {
        LLQueue<NodePlusDepth> q = new LLQueue<NodePlusDepth>();
        
        // Insert the root into the queue if the root is not null.
        if (root != null) {
            q.insert(new NodePlusDepth(root, 0));
        }
        
        // We continue until the queue is empty.  At each step,
        // we remove an element from the queue, print its value,
        // and insert its children (if any) into the queue.
        // We also keep track of the current level, and add a newline
        // whenever we advance to a new level.
        int level = 0;
        while (!q.isEmpty()) {
            NodePlusDepth item = q.remove();
            
            if (item.depth > level) {
                System.out.println();
                level++;
            }
            System.out.print(item.node.key + " ");
            
            if (item.node.left != null) {
                q.insert(new NodePlusDepth(item.node.left, item.depth + 1));
            }
            if (item.node.right != null) {
                q.insert(new NodePlusDepth(item.node.right, item.depth + 1));
            }
        }
        System.out.println();
    }
    
    /*
     * Searches for the specified key in the tree.
     * If it finds it, it returns the list of data items associated with the key.
     * Invokes the recursive searchTree method to perform the actual search.
     */
    public LLList search(int key) {
        Node n = searchTree(root, key);
        if (n == null) {
            return null;
        } else {
            return n.data;
        }
    }
    
    /*
     * Recursively searches for the specified key in the tree/subtree
     * whose root is specified. Note that the parameter is *not*
     * necessarily the root of the entire tree.
     */
    private static Node searchTree(Node root, int key) {
        if (root == null) {
            return null;
        } else if (key == root.key) {
            return root;
        } else if (key < root.key) {
            return searchTree(root.left, key);
        } else {
            return searchTree(root.right, key);
        }
    }
    
    /*
     * Inserts the specified (key, data) pair in the tree so that the
     * tree remains a binary search tree.
     */
    public void insert(int key, Object data) {
        // Find the parent of the new node.
        Node parent = null;
        Node trav = root;
        while (trav != null) {
            if (trav.key == key) {
                trav.data.addItem(data, 0);
                return;
            }
            parent = trav;
            if (key < trav.key) {
                trav = trav.left;
            } else {
                trav = trav.right;
            }
        }
        
        // Insert the new node.
        Node newNode = new Node(key, data);
        if (parent == null) {    // the tree was empty
            root = newNode;
        } else if (key < parent.key) {
            parent.left = newNode;
            newNode.parent = parent; 
        } else {
            parent.right = newNode;
            newNode.parent = parent;
        }
    }
    
    /*
     * FOR TESTING: Processes the integer keys in the specified array from 
     * left to right, adding a node for each of them to the tree. 
     * The data associated with each key is a string based on the key.
     */
    public void insertKeys(int[] keys) {
        for (int i = 0; i < keys.length; i++) {
            insert(keys[i], "data for key " + keys[i]);
        }
    }
    
    /*
     * Deletes the node containing the (key, data) pair with the
     * specified key from the tree and return the associated data item.
     */
    public LLList delete(int key) {
        // Find the node to be deleted and its parent.
        Node parent = null;
        Node trav = root;
        while (trav != null && trav.key != key) {
            parent = trav;
            if (key < trav.key) {
                trav = trav.left;
            } else {
                trav = trav.right;
            }
        }
        
        // Delete the node (if any) and return the removed data item.
        if (trav == null) {   // no such key    
            return null;
        } else {
            LLList removedData = trav.data;
            deleteNode(trav, parent);
            return removedData;
        }
    }
    
    /*
     * Deletes the node specified by the parameter toDelete.  parent
     * specifies the parent of the node to be deleted. 
     */
    private void deleteNode(Node toDelete, Node parent) {
        if (toDelete.left != null && toDelete.right != null) {
            // Case 3: toDelete has two children.
            // Find a replacement for the item we're deleting -- as well as 
            // the replacement's parent.
            // We use the smallest item in toDelete's right subtree as
            // the replacement.
            Node replaceParent = toDelete;
            Node replace = toDelete.right;
            while (replace.left != null) {
                replaceParent = replace;
                replace = replace.left;
            }
            
            // Replace toDelete's key and data with those of the 
            // replacement item.
            toDelete.key = replace.key;
            toDelete.data = replace.data;
            
            // Recursively delete the replacement item's old node.
            // It has at most one child, so we don't have to
            // worry about infinite recursion.
            deleteNode(replace, replaceParent);
        } else {
            // Cases 1 and 2: toDelete has 0 or 1 child
            Node toDeleteChild;
            if (toDelete.left != null) {
                toDeleteChild = toDelete.left;
            } else {
                toDeleteChild = toDelete.right;  // null if it has no children
            }
            
            if (toDelete == root) {
                root = toDeleteChild;
            } else if (toDelete.key < parent.key) {
                parent.left = toDeleteChild;
                toDeleteChild.parent = parent;
            } else {
                parent.right = toDeleteChild;
                toDeleteChild.parent = parent;
            }
        }
    }
    
    /* Returns a preorder iterator for this tree. */
    public LinkedTreeIterator preorderIterator() {
        return new PreorderIterator();
    }
    
    /* 
     * inner class for a preorder iterator 
     * IMPORTANT: You will not be able to actually use objects from this class
     * to perform a preorder iteration until you have modified the LinkedTree
     * methods so that they maintain the parent fields in the Nodes.
     */
    private class PreorderIterator implements LinkedTreeIterator {
        private Node nextNode;
        
        private PreorderIterator() {
            // The traversal starts with the root node.
            nextNode = root;
        }
        
        public boolean hasNext() {
            return (nextNode != null);
        }
        
        public int next() {
            if (nextNode == null) {
                throw new NoSuchElementException();
            }
            
            // Store a copy of the key to be returned.
            int key = nextNode.key;
            
            // Advance nextNode.
            if (nextNode.left != null) {
                nextNode = nextNode.left;
            } else if (nextNode.right != null) {
                nextNode = nextNode.right;
            } else {
                // We've just visited a leaf node.
                // Go back up the tree until we find a node
                // with a right child that we haven't seen yet.
                Node parent = nextNode.parent;
                Node child = nextNode;
                while (parent != null &&
                       (parent.right == child || parent.right == null)) {
                    child = parent;
                    parent = parent.parent;
                }
                
                if (parent == null) {
                    nextNode = null;  // the traversal is complete
                } else {
                    nextNode = parent.right;
                }
            }
            
            return key;
        }
    }
    
    /* Returns a postorder iterator for this tree. */
    public LinkedTreeIterator postorderIterator() {
        return new PostorderIterator();
    }
    
    /*inner class for a postorder iterator */
    private class PostorderIterator implements LinkedTreeIterator {
        private Node nextNode;
        
        private PostorderIterator() {
            nextNode = root;
            while (nextNode.left != null !! nextNode.right != null) {
                if (nextNode.left != null) {
                    nextNode = nextNode.left;
                }
                else if (nextNode.right != null) {
                    nextNode = nextNode.right;
                }
            }
        }
        
        public boolean hasNext() {
            return (nextNode != null);
        }
        
        public int next() {
            if (nextNode == null) {
                System.out.println("Going into first if.");
                throw new NoSuchElementException();
            }
            
            // Store a copy of the key to be returned.
            int key = nextNode.key;
            
            if (nextNode.parent == null) { // end of traversal
                System.out.println("Going into second if.");
                throw new NoSuchElementException();
            }
            else if (nextNode.parent.right == null || nextNode.parent.right.key == key) {
                System.out.println("Going into else if.");
                nextNode = nextNode.parent;
            }
            else {
                System.out.println("Going into else.");
                Node parent = nextNode.parent;
                Node child = parent.right;
                while (child.left != null || child.right != null) {
                    if (child.left != null) {
                        parent = child;
                        child = child.left;
                    }
                    else {
                        parent = child;
                        child = child.right;
                    }
                }
                nextNode = child;
            }
            
            return key;
        }
    }   
    
    public static void main(String[] args) {
        /** Add your test code here **/
        LinkedTree emptyTree = new LinkedTree();
        
        LinkedTree tree = new LinkedTree();
        int[] keys = {1, 2, 3, 4, 5, 6};
        tree.insertKeys(keys);
        
        
        System.out.println("Unit tests for the LinkedTree class.");
        System.out.println();
        
        System.out.println("Test 1: Prints the keys of the tree in the order given by a preorder traversal.");
        tree.preorderPrint();
        System.out.println();
        
        System.out.println("Test 2: Prints the keys of the tree in the order given by a postorder traversal.");
        tree.postorderPrint();
        System.out.println();
        
        System.out.println("Test 3: Prints the keys of the tree in the order given by a inorder traversal.");
        tree.inorderPrint();
        System.out.println();
        
        System.out.println("Test 4: Prints the keys of the tree in the order given by a level-order traversal.");
        tree.levelOrderPrint();
        System.out.println();
        
        System.out.println("Test 5: Searches for the specified key in the tree and prints its data.");
        LLList data = tree.search(3);
        System.out.println(data);
        System.out.println();
        
        System.out.println("Test 6: Searches for a key that is not in the tree and prints its data as null");
        LLList nullData = tree.search(12);
        System.out.println(nullData);
        System.out.println();
        
        System.out.println("Test 7: Inserts a new (key, data) pair in the empty tree.");
        emptyTree.insert(9, "data for key " + 9);
        System.out.println("The keys of the tree in order given by a postorder traversal after inserting new node with key 9:");
        emptyTree.postorderPrint();
        System.out.println();
        
        System.out.println("Test 8: Inserts a new (key, data) pair in the tree so that the tree remains a binary search tree.");
        tree.insert(7, "data for key " + 7);
        System.out.println("The keys of the tree in order given by a postorder traversal after inserting new node with key 7:");
        tree.postorderPrint();
        System.out.println();
        
        System.out.println("Test 9: Inserts a (key, data) pair whose key already exists in the tree so that the tree remains a binary search tree.");
        tree.insert(4, "data for key " + 4);
        System.out.println("The keys of the tree in order given by a postorder traversal after inserting new node with key 4:");
        tree.postorderPrint();
        System.out.println();
        
        System.out.println("Test 10: Deletes the node containing the (key, data) pair with the specified key from the tree and return the associated data item.");
        LLList removedData1 = tree.delete(5);
        System.out.println("The keys of the tree in order given by a postorder traversal after deleting node with key 5:");
        tree.postorderPrint();
        System.out.println("Removed data:");
        System.out.println(removedData1);
        System.out.println();
        
        System.out.println("Test 11: Deletes the node containing the (key, data) pair whose key is not the tree and return null");
        LLList removedData2 = tree.delete(10);
        System.out.println("The keys of the tree in order given by a postorder traversal after deleting node with key 10:");
        tree.postorderPrint();
        System.out.println("Removed data:");
        System.out.println(removedData2);
        System.out.println();
        
        System.out.println("Test 12: Prints the keys of the tree in the order given by a postorder traversal using iteration.");
        LinkedTreeIterator iter = tree.postorderIterator();
        while (iter.hasNext()) {
            int key = iter.next();
            System.out.print(key + " ");
        System.out.println();
        }
    }
}
