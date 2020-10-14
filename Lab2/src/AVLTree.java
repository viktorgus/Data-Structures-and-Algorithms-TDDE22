import java.util.Objects;

/**
 * Implementation of an AVL Tree.
 *
 * @author Magnus Nielsen (magnus.nielsen@liu.se) Partially based on existing
 *         C++-laborations by Tommy Olsson and Filip Strömbäck.
 */
public class AVLTree<T extends Comparable<T>> {

	private AVLTreeNode<T> root;

	/**
	 * Default constructor.
	 */
	public AVLTree() {
		root = null;
	}

	public void insert(T data) {
		if (root == null) {
			root = new AVLTreeNode<>(data);
		} else {
			try {
				root = root.insert(data);
			} catch (AVLTreeException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Remove the node which contains the key.
	 *
	 * @param key - Generic parameter with the key value which to remove from the
	 *            tree.
	 */
	public void remove(T key) {

		if (!member(key)) {
			return;
		}
		
		root.simpleRemove(key,root);
		root = balance(root);

	}

	private AVLTreeNode<T> balance(AVLTreeNode<T> t) {
		if(t.left!=null) {
			t.left = balance(t.left);
		}if(t.right!=null) {
			t.right=balance(t.right);
		}

		if(t != null ) {
		if (t.nodeHeight(t.left) - t.nodeHeight(t.right) == 2) {
			if (t.nodeHeight(t.left.left) > t.nodeHeight(t.left.right)) {
				return t.singleRotationWithLeftChild(t);
			} else {
				return t.doubleRotationWithLeftChild(t);
			}
		} 
		
		else if (t.nodeHeight(t.right) - t.nodeHeight(t.left) == 2) {
			if (t.nodeHeight(t.right.right) > t.nodeHeight(t.right.left)) {
				return t.singleRotationWithRightChild(t);
			} else {
				return t.doubleRotationWithRightChild(t);
			}
		} 
		else {
			
		}
		t.calculateHeight(t);	
		
		}
		return t;
	}
	/**
	 * Check if the tree contains a node with the key.
	 *
	 * @param key - Generic key value for which to search.
	 * @return boolean, true if the key exists in the tree, false otherwise.
	 */
	public boolean member(T key) {
		return root.find(key) != null;
	}

	/**
	 * Look up the key in the tree, returns the element if it exists.
	 *
	 * @param key - Generic value for which to search.
	 * @return the element containing (or equalling) the key.
	 */
	public T find(T key) {
		if (root == null) {
			throw new AVLTreeException("Value not found in the tree!");
		}
		AVLTreeNode<T> tmp = root.find(key);
		if (tmp == null) {
			throw new AVLTreeException("Value not found in the tree!");
		}
		return tmp.element;
	}

	/**
	 * Find the smallest element in the tree.
	 *
	 * @return the element containing the smallest value in the tree.
	 */
	public T findMin() {
		if (root == null) {
			throw new AVLTreeException("Cannot find a smallest element in an empty tree!");
		}
		return root.findMin().element;
	}

	/**
	 * Find the largest element in the tree.
	 *
	 * @return the element containing the largest value in the tree.
	 */
	public T findMax() {
		if (root == null) {
			throw new AVLTreeException("Cannot find a largest element in an empty tree!");
		}
		return root.findMax().element;
	}

	/**
	 * Check if the tree is empty.
	 *
	 * @return boolean, true if empty and false otherwise.
	 */
	public boolean empty() {
		return root == null;
	}

	/**
	 * Clear the tree.
	 */
	public void clear() {
		root = root.clear();
	}

	/**
	 * Swap trees x and y with each other.
	 *
	 * @param x - first tree to swap.
	 * @param y - second tree to swap.
	 */
	public void swap(AVLTree<T> x, AVLTree<T> y) {
		x.swap(y);
	}

	/**
	 * Swap this tree with another.
	 *
	 * @param t - the tree to swap with.
	 */
	private void swap(AVLTree<T> t) {
		AVLTreeNode<T> tmp = t.root;
		t.root = root;
		root = tmp;
	}

	/**
	 * Getter for the root of the tree.
	 *
	 * @return the root of the tree.
	 */
	public AVLTreeNode getRoot() {
		return root;
	}

	/**
	 * Find the height of the node with a given key.
	 *
	 * @param key - Generic parameter containing the key.
	 * @return int representing the height of the node containing the key.
	 */
	public int getNodeHeight(T key) {
		return Objects.requireNonNull(root.find(key)).height;
	}

	////////////////////////////////////////////////////////////////////////////
	// //
	// AVL TREE NODE //
	// //
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Contains the AVL Tree Node, with rotations and corresponding help functions.
	 * 
	 * @author Magnus Nielsen (magnus.nielsen@liu.se) Partially based on
	 *         C++-laborations by Tommy Olsson and Filip Strömbäck.
	 */
	public class AVLTreeNode<T extends Comparable<T>> {

		private int height;
		private T element;
		private AVLTreeNode<T> left, right;

		/**
		 * Constructor.
		 *
		 * @param data - Generic parameter containing the data element for the node.
		 */
		public AVLTreeNode(T data) {
			element = data;
		}

		/**
		 * Constructor.
		 *
		 * @param data - Generic parameter containing the data element for the node.
		 * @param l    - AVLTreeNode<T> containing the intended left child for the node
		 *             being created.
		 * @param r    - AVLTreeNode<T> containing the intended right child for the node
		 *             being created.
		 */
		public AVLTreeNode(T data, AVLTreeNode<T> l, AVLTreeNode<T> r) {
			element = data;
			left = l;
			right = r;
		}

		////////////////////////////////////////////////////////////////////////////
		// //
		// ROTATIONS //
		// //
		////////////////////////////////////////////////////////////////////////////

		/**
		 * Single rotation, left to right, using pivot as pivot.
		 *
		 * @param pivot - AVLTreeNode used as pivot for the rotation.
		 * @return AVLTreeNode with the new node for the pivot location in the tree.
		 */
		private AVLTreeNode<T> singleRotationWithLeftChild(AVLTreeNode<T> pivot) {
			AVLTreeNode<T> temp = pivot.left;

			pivot.left = temp.right;
			temp.right = pivot;

			calculateHeight(pivot);
			calculateHeight(temp);
			return temp;
		}

		/**
		 * Single rotation, right to left, using pivot as pivot.
		 *
		 * @param pivot - AVLTreeNode used as pivot for the rotation.
		 * @return AVLTreeNode with the new node for the pivot location in the tree.
		 */
		private AVLTreeNode<T> singleRotationWithRightChild(AVLTreeNode<T> pivot) {
			AVLTreeNode<T> temp = pivot.right;

			pivot.right = temp.left;
			temp.left = pivot;

			calculateHeight(pivot);
			calculateHeight(temp);
			return temp;
		}

		/**
		 * Double rotation, left to right, using pivot as pivot.
		 *
		 * @param pivot - AVLTreeNode used as pivot for the rotation.
		 * @return AVLTreeNode with the new node for the pivot location in the tree.
		 */
		private AVLTreeNode<T> doubleRotationWithLeftChild(AVLTreeNode<T> pivot) {
			pivot.left = singleRotationWithRightChild(pivot.left);
			return singleRotationWithLeftChild(pivot);
		}

		/**
		 * Double rotation, right to left, using pivot as pivot.
		 *
		 * @param pivot - AVLTreeNode used as pivot for the rotation.
		 * @return AVLTreeNode with the new node for the pivot location in the tre.
		 */
		private AVLTreeNode<T> doubleRotationWithRightChild(AVLTreeNode<T> pivot) {
			pivot.right = singleRotationWithLeftChild(pivot.right);
			return singleRotationWithRightChild(pivot);
		}

		////////////////////////////////////////////////////////////////////////////
		// //
		// NODE FUNCTIONALITY //
		// //
		////////////////////////////////////////////////////////////////////////////

		/**
		 * Adjust the height for a given node n.
		 *
		 * @param n - AVLTreeNode for which to calculate the height.
		 */
		private void calculateHeight(AVLTreeNode<T> n) {
			n.height = 1 + Math.max(nodeHeight(n.left), nodeHeight(n.right));
		}

		/**
		 * Insert element into the tree originating from t. Check balance and adjust as
		 * needed.
		 *
		 * @param data - Generic element parameter to be inserted.
		 * @return AVLTreeNode containing the appropriate node for the call position,
		 *         after insertion.
		 */
		private AVLTreeNode<T> insert(T data) {
			if (data.compareTo(element) < 0) {
				if (left == null) {
					left = new AVLTreeNode<>(data);
				} else {
					left = left.insert(data);
				}

				if (nodeHeight(left) - nodeHeight(right) == 2) {
					if (data.compareTo(left.element) < 0) {
						return singleRotationWithLeftChild(this);
					} else {
						return doubleRotationWithLeftChild(this);
					}
				} else {
					calculateHeight(this);
				}
			} else if (element.compareTo(data) < 0) {
				if (right == null) {
					right = new AVLTreeNode<>(data);
				} else {
					right = right.insert(data);
				}

				if (nodeHeight(right) - nodeHeight(left) == 2) {
					if (data.compareTo(right.element) > 0) {
						return singleRotationWithRightChild(this);
					} else {
						return doubleRotationWithRightChild(this);
					}
				} else {
					calculateHeight(this);
				}
			} else {
				throw new AVLTreeException("Element already exists.");
			}
			return this;
		}

		/**
		 * Returns the height of (sub) tree node.
		 *
		 * @param n - AVLTreeNode for which to get the height.
		 */
		private int nodeHeight(AVLTreeNode<T> n) {
			if (n != null) {
				return n.height;
			}
			return -1;
		}

		/**
		 * Search for a node containing the key. If found: return the node, otherwise
		 * return null.
		 *
		 * @param key - Generic key type for which to search.
		 * @return AVLTreeNode containing the key, if not found null be returned.
		 */
		private AVLTreeNode<T> find(T key) {
			if (key.compareTo(element) < 0) {
				if (left == null) {
					return null;
				}
				return left.find(key);
			} else if (element.compareTo(key) < 0) {
				if (right == null) {
					return null;
				}
				return right.find(key);
			} else {
				return this;
			}
		}

		/**
		 * Looks for the node with the smallest value in the tree (the leftmost node),
		 * and returns that node. If the tree is empty, null will be returned.
		 *
		 * @return AVLTreeNode with the smallest value in the tree, null if the tree is
		 *         empty.
		 */
		private AVLTreeNode<T> findMin() {
			if (left == null) {
				return this;
			} else {
				return left.findMin();
			}
		}

		/**
		 * Looks for the node with the largest value in the tree (the rightmost node),
		 * and returns that node. If the tree is empty, null will be returned.
		 *
		 * @return AVLTreeNode with the largest value in the tree, null if the tree is
		 *         empty.
		 */

		private AVLTreeNode<T> findMax() {
			if (right == null) {
				return this;
			} else {
				return right.findMax();
			}
		}

		/**
		 * Clear the given tree completely.
		 *
		 * @return null (to be used for clearing the root node.
		 */
		private AVLTreeNode<T> clear() {
			if (left != null) {
				left = left.clear();
			}
			if (right != null) {
				right = right.clear();
			}
			return null;
		}

		/**
		 * Simple remove will remove in a lazy fashion, as with non balancing trees. Can
		 * be used as a guide when implementing the real remove function.
		 *
		 * @param x - Generic parameter with the key to be deleted.
		 * @param t - The (sub) tree from which to remove.
		 * @return - AVLTreeNode<T>, the correct node for the source position in the
		 *         tree.
		 */
		

		private AVLTreeNode<T> simpleRemove(T x, AVLTreeNode<T> t) {
			if (t == null) {
				return null;
			}

			if (x.compareTo(t.element) < 0) {
				t.left = simpleRemove(x, t.left);
			} else if (t.element.compareTo(x) < 0) {
				t.right = simpleRemove(x, t.right);
			} else {
				if (t.left != null && t.right != null) {
					// The node has two children, so we replace it with the next node inorder.
					AVLTreeNode<T> tmp = t.right.findMin();
					t.element = tmp.element;
					t.right = simpleRemove(tmp.element, t.right);
				} else {
					// The node has, at most, one child.
					if (t.left == null) {
						return t.right;
					} else {
						return t.left;
					}
				}
			}
			return t;
		}

		/**
		 * Getter for element.
		 *
		 * @return the element in said node.
		 */
		public T getElement() {
			return element;
		}

		/**
		 * Getter for the left hand tree node.
		 *
		 * @return the left hand tree node.
		 */
		public AVLTreeNode<T> getLeft() {
			return left;
		}

		/**
		 * Getter for the right hand tree node.
		 *
		 * @return the right hand tree node.
		 */
		public AVLTreeNode<T> getRight() {
			return right;
		}
	}
}
