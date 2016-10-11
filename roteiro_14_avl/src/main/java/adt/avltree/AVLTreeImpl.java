
package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

   /**
    * 
    * Performs consistency validations within a AVL Tree instance
   * 
   * @author Claudio Campelo
   *
   * @param <T>
   */
   public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {
    
     public void insert(T element) {
      insert(element, super.getRoot(), new BSTNode<T>());
    }
    
    private void insert(T element, BSTNode<T> node, BSTNode<T> parent) {
      if (element == null) {
       return;
     }
     
     if (node.isEmpty()) {
       node.setData(element);
       node.setLeft(new BSTNode<T>());
       node.setRight(new BSTNode<T>());
       node.setParent(parent);
     } else if (element.compareTo(node.getData()) < 0) {
       insert(element, (BSTNode<T>) node.getLeft(), node);
     } else if (element.compareTo(node.getData()) > 0) {
       insert(element, (BSTNode<T>) node.getRight(), node);
     }
     rebalance(node);
   }
   
   public void remove(T element) {
    if (element == null) {
     return;
   }
   
   BSTNode<T> found = search(element);
   
   remove(found);
 }
 
 protected void remove(BSTNode<T> node) {
  if (node == null) {
   return;
 }
        // if the node is leaf
 if (node.isLeaf()) {
   if (node.equals(this.root)) {
    this.root = new BSTNode<T>();
  } else {
    if (node.getParent().getLeft().equals(node)) {
     node.getParent().setLeft(new BSTNode<T>());
   } else {
     node.getParent().setRight(new BSTNode<T>());
   }
 }
 rebalanceUp(node);
           // if there is only one son
} else if (!node.getRight().isEmpty() && node.getLeft().isEmpty()) {
 if (node.equals(this.root)) {
  this.root = (BSTNode<T>) node.getRight();
} else {
              // check if the node is on the left side of it's parent
  if (node.getParent().getLeft().equals(node)) {
   node.getParent().setLeft(node.getRight());
 } else {
   node.getParent().setRight(node.getRight());
 }
 node.getRight().setParent(node.getParent());
}
rebalanceUp(node);
} else if (node.getRight().isEmpty() && !node.getLeft().isEmpty()) {
 if (node.equals(this.root)) {
  this.root = (BSTNode<T>) node.getLeft();
} else {
  if (node.getParent().getLeft().equals(node)) {
   node.getParent().setLeft(node.getLeft());
 } else {
   node.getParent().setRight(node.getLeft());
 }
 node.getLeft().setParent(node.getParent());
}
rebalanceUp(node);
} else {
 BSTNode<T> help = sucessor(node.getData());
 if (help == null) {
  help = predecessor(node.getData());
}
T auxData = node.getData();
node.setData(help.getData());
help.setData(auxData);
remove(help);
}
}

    // AUXILIARY
protected int calculateBalance(BSTNode<T> node) {
 
 if (!node.isEmpty()) {
  return height((BSTNode<T>) node.getLeft()) - super.height((BSTNode<T>) node.getRight());
}

return 0;
}

    // AUXILIARY
protected void rebalance(BSTNode<T> node) {
 if (node == null || node.isEmpty()) {
  return;
}

int balance = calculateBalance(node);

if (Math.abs(balance) <= 1) {
  return;
}

if (balance > 0) {
 
  int sonBalance = calculateBalance((BSTNode<T>) node.getLeft());
  
  if (sonBalance < 0) {
   this.leftRotation((BSTNode<T>) node.getLeft());
 }
 this.rightRotation(node);
} else {
  int sonBalance = calculateBalance((BSTNode<T>) node.getRight());
  
  if (sonBalance > 0) {
   this.rightRotation((BSTNode<T>) node.getRight());
 }
 
 this.leftRotation(node);
}
}

    // AUXILIARY
protected void rebalanceUp(BSTNode<T> node) {
 if (node == null || node.isEmpty()) {
  return;
}

BSTNode<T> parent = (BSTNode<T>) node.getParent();

while (!parent.isEmpty()) {
  rebalance(parent);
  parent = (BSTNode<T>) parent.getParent();
}
}

    // AUXILIARY
protected void leftRotation(BSTNode<T> node) {
 if (node == null) {
  return;
}
BSTNode<T> aux = Util.leftRotation(node);
if (root.equals(node)) {
  root = aux;
}
}

    // AUXILIARY
protected void rightRotation(BSTNode<T> node) {
 if (node == null) {
  return;
}

BSTNode<T> aux = Util.rightRotation(node);
if (this.root.equals(node)) {
  root = aux;
}

}

}