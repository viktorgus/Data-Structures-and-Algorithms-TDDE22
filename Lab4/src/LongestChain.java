
class LongestChain
{
  private Queue q; // kö som anänds i breddenförstsökningen
  private String goalWord; // slutord i breddenförstsökningen
  int wordLength;
  final char [] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			     'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			     's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '�',
			     '�', '�', '�' };
  int alphabetLength = alphabet.length;
  WordRec longest;

  public LongestChain(int wordLength) {
    this.wordLength = wordLength;
    q = new Queue();
  }

  // IsGoal kollar om w är slutordet.
  private boolean IsGoal(String w)
  {
    return w.equals(goalWord);
  }

  // MakeSons skapar alla ord som skiljer på en bokstav från x.
  // Om det är första gången i sökningen som ordet skapas så läggs det
  // in i kön q.
  private WordRec MakeSons(WordRec x)
  {
    for (int i = 0; i < wordLength; i++) {
      for (int c = 0; c < alphabetLength; c++) {
	if (alphabet[c] != x.word.charAt(i)) {
	  String res = WordList.Contains(x.word.substring(0,i) +
					 alphabet[c] +
					 x.word.substring(i+1));
	  if (res != null && WordList.MarkAsUsedIfUnused(res)) {
	    WordRec wr = new WordRec(res, x);
	    if (IsGoal(res)) return wr;
	    q.Put(wr);
	  }
	}
      }
    }
    return null;
  }
  
  
  
 
  // BreadthFirst utför en breddenförstsökning från startWord för att
  // hitta kortaste ägen till endWord. Den kortaste ägen returneras
  // som en kedja av ordposter (WordRec).
  // Om det inte finns något sätt att komma till endWord returneras null.
  public WordRec BreadthFirst(String startWord, String endWord)
  {
    WordList.EraseUsed();
    WordRec start = new WordRec(startWord, null);
    WordList.MarkAsUsedIfUnused(startWord);
    goalWord = endWord;
    q.Empty();
    q.Put(start);
    try {
      while (true) {
	WordRec wr = MakeSons((WordRec) q.Get());
	if (wr != null) return wr;
      }
    } catch (Exception e) {
      return null;
    }
  }
  
  
  private void NewMakeSons(WordRec x)
  {
    for (int i = 0; i < wordLength; i++) {
      for (int c = 0; c < alphabetLength; c++) {
	if (alphabet[c] != x.word.charAt(i)) {
	  String res = WordList.Contains(x.word.substring(0,i) +
					 alphabet[c] +
					 x.word.substring(i+1));
	  if (res != null && WordList.MarkAsUsedIfUnused(res)) {
	    WordRec wr = new WordRec(res, x);
	    longest = wr;
	    q.Put(wr);
	  }
	}
      }
    }
  }

//Compute shortest path distances from s, store them in D
  public void FindLongestShortest(String startWord)
  {
	longest = null;
    WordList.EraseUsed();
    WordRec start = new WordRec(startWord, null);
    WordList.MarkAsUsedIfUnused(startWord);
    q.Empty();
    q.Put(start);
      while (!q.IsEmpty()) {
    	  try {
			NewMakeSons((WordRec) q.Get());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	  
      }
      if(longest != null) {
      longest.PrintChain();
      System.out.println(startWord + ": " + longest.ChainLength() + " ord");  
      }else System.out.println(startWord + " has no neighbours");
  
    }

  // CheckAllStartWords hittar den längsta kortaste ägen från något ord
  // till endWord. Den längsta ägen skrivs ut.
  public void CheckAllStartWords(String endWord)
  {
    int maxChainLength = 0;
    WordRec maxChainRec = null;
    for (int i = 0; i < WordList.size; i++) {
      WordRec x = BreadthFirst(WordList.WordAt(i), endWord);
      if (x != null) {
	int len = x.ChainLength();
	if (len > maxChainLength) {
	  maxChainLength = len;
	  maxChainRec = x;
	   x.PrintChain(); // skriv ut den hittills längsta kedjan
	}
      }
    }
    System.out.println(endWord + ": " + maxChainLength + " ord");
    if (maxChainRec != null) maxChainRec.PrintChain();
  }
}
