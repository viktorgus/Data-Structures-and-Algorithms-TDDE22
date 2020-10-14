import java.util.Arrays;


public class SymbolTable {
	private static final int INIT_CAPACITY = 7;

	/* Number of key-value pairs in the symbol table */
	private int N;
	/* Size of linear probing table */
	private int M;
	/* The keys */
	private String[] keys;
	/* The values */
	private Character[] vals;
	
	/**
	 * Create an empty hash table - use 7 as default size
	 */
	public SymbolTable() {
		this(INIT_CAPACITY);
	}

	/**
	 * Create linear probing hash table of given capacity
	 */
	public SymbolTable(int capacity) {
		N = 0;
		M = capacity;
		keys = new String[M];
		vals = new Character[M];
	}

	/**
	 * Return the number of key-value pairs in the symbol table
	 */
	public int size() {
		return N;
	}

	/**
	 * Is the symbol table empty?
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Does a key-value pair with the given key exist in the symbol table?
	 */
	public boolean contains(String key) {
		return get(key) != null;
	}

	/**
	 * Hash function for keys - returns value between 0 and M-1
	 */
	public int hash(String key) {
		int i;
		int v = 0;

		for (i = 0; i < key.length(); i++) {
			v += key.charAt(i);
		}
		return v % M;
	}
	private boolean isFull() {
		return N==M;
	}
	
	private int probe(String key, Integer i) {
		
		return (hash(key)+i) % M;
	}
	/**
	 * Insert the key-value pair into the symbol table
	 */
	
	
	
	public void put(String key, Character val) {
		int i = 0;
		if (isFull() || key==null) {
			return;
		}
			while(keys[probe(key,i)]!=null) {
				if(keys[probe(key,i)].equals(key)) break;
				i+=1;
				if(i>M) {
					break;
				}
			}
			if(keys[probe(key,i)]==null) {
				N++;
				vals[probe(key,i)]=val;
				keys[probe(key,i)]=key;
			}else if (keys[probe(key,i)].equals(key)) {
				vals[probe(key,i)]=val;
			}

	} // dummy code

	/**
	 * Return the value associated with the given key, null if no such value
	 */
	public Character get(String key) {
		int i=0;
		while((keys[probe(key,i)]!=null) && i<=M) {
			if (keys[probe(key,i)].equals(key)) return vals[probe(key,i)];
			i++;
		}
		return null;
		
	} // dummy code

	/**
	 * Delete the key (and associated value) from the symbol table
	 */
	public void delete(String key) {
		int i=0;
		while((keys[probe(key,i)]!=null) && i<M) {
			if (keys[probe(key,i)].equals(key)) {
				keys[probe(key,i)]=null;
				vals[probe(key,i)]=null;
				rehash();
				N--;
			}
			i++;
		}
		
	} // dummy code

	private void rehash() {
		Character[] oldVals = vals.clone();
		String[] oldKeys = keys.clone();
		
		Arrays.fill(vals,null);
		Arrays.fill(keys,null);
		
		for(int i=0;i<M;i++) {
			if(oldKeys[i]!=null) {
				put(oldKeys[i],oldVals[i]);
			}
		}
	}

	/**
	 * Print the contents of the symbol table
	 */
	public void dump() {
		String str = "";

		for (int i = 0; i < M; i++) {
			str = str + i + ". " + vals[i];
			if (keys[i] != null) {
				str = str + " " + keys[i] + " (";
				str = str + hash(keys[i]) + ")";
			} else {
				str = str + " -";
			}
			System.out.println(str);
			str = "";
		}
	}
}

//Verkar vara Open Hashing (closed adressing) då en collision uppstår, så sätts inte värdet in i tablån. 2500 p=0.99. 1150 entries hade 50% chans för kollision.
// produkt över alla n ((Storlek-n)/Storlek)
// antalet kombinationer där inga kollisioner sker minskar kraftigt mot antalet kombinationer där kombinationer sker mot antalet element.
