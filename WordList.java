/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordlist;


/**
 * @author Nan Shang
 */
public class WordList {
    private int size;
    private String[] a;
    private String[] b;
    private int cap;

    public WordList (int capacity){
        this.cap = capacity;
        a = new String[cap];
        for (int i = 0; i<cap;i++){
            a[i] = "";
        }
        size = 0;
    }
    
    public WordList(String[] arrayOfWords){
        this.cap = (arrayOfWords.length)*2;
        a = new String[cap];
        
        for (int i=0;i<cap;i++)
           a[i]="";

        arrayOfWords = sort(arrayOfWords);
        arrayOfWords = duplicate(arrayOfWords);

        
        for(int i=0;i<(arrayOfWords.length);i++){
            a[i] = arrayOfWords[i];
            if(arrayOfWords != null) size++;
        }
        

        
    }
    
    private String[] sort(String[] array){
        
        int L = array.length;
        String  buffer;
        for (int i=0; i<L; i++){
            for (int j = i+1; j<L; j++){
                if ((array[i].compareTo(array[j]) > 0)){ //checking for swap
                    buffer = array[i];
                    array[i]=array[j];
                    array[j]=buffer;
                }
            }
        }
        return(array);
    }
    
    public String []  duplicate(String []array){ 
    
        String [] b = new String [array.length];
        for(int i=0;i<array.length;i++){   //copy array to b
            b[i]= array[i];
        }
        for(int i=0;i<array.length-1;i++){  //as the array is sorted, compare each two near-by elements
            if(array[i].equals(array[i+1])){
                b = remove(array,i);   //delete the postion in b
            }
        }
    return b;
}
    
    public String [] remove(String[] array, int p){
    
        int k=0;
        String[] temp = new String[array.length-1];
        for(int i = 0; i < array.length; i++) {
            if(i != p) 
                temp[k++] = array[i];    // if the index is not deleting postion, deep copy, otherwide skip
        }
    return temp;
} 

    public int getSize(){
        return (size);
    }
    
    public int getCapacity(){
        return (cap);
    }
    
    public String getWordAt(int i) throws ArrayIndexOutOfBoundsException{
        if(i< size)        
            return(a[i]);
        else throw new ArrayIndexOutOfBoundsException ("The index is not on list");
    
    }
    
    public String toString() {
        String longString = "";
	if(size==0)
	    longString = "The list is empty";
	else
	    longString = a[0];
        for (int i = 1; i < size; i++) {
            longString =longString + "\n" + a[i];
        }
        return longString;
    }
    
    //not done
    public void insert(String newword) {

        int counter =0;

        if (this.size == 0){
            this.a[0] = newword;
            size =1;
        }else if(find(newword)== -1){

            if(this.size >= this.cap){ //new wordlist with twice of the size
               
                this.cap = (this.cap)*2;
                String [] array2 = new String[this.size+1];
                
                for (int i = 0; i<this.size; i++){
                   array2[i] = a[i];
                }
                array2[this.size] = newword;
                size ++;
                for(int i =0; i< this.size; i ++){
                    System.out.println("located in "+i+ " array has " + array2[i]);
                }
                for (int i=this.size+1;i<this.cap;i++)
                   array2[i]="";

                array2 = sort(array2);
                array2 = duplicate(array2);
                a = array2;
                    
           }
            else if(this.size < this.cap){
                
                for(int flag =0; flag < this.size ; flag ++){
                    if (newword.compareTo(a[flag]) > 0){
                        counter ++;
                    }else if(newword.compareTo(a[flag])<0){
                        for (int i = this.size; i>flag; i--){
                            a[i] = a[i-1];
                        }
                        a[counter] = newword;
                    }
                }
                if (newword.compareTo(a[size-1])>0) //last case
                    a[size]=newword;
                this.size++;
            }

        }else if(find(newword)< (-1))
           System.out.println("the word is in the list it is: " + find(newword) + newword);

    }
    
    public int find(String word){
        int right = this.getSize() -1;
        int mid;
        int left=0;  
        
        while(left <= right){
            mid = (left + right)/2;
            
            if (a[mid]==word) return mid;
            else if(a[mid].compareTo(word)<0)left = mid +1;
            else right = mid -1;       
        }
        return -1; //word not found
    }
   
    public void remove(String word){ 
        int index = find(word);
        int k=0;

        if(index>=0){
             String [] Array = new String[a.length-1];
            for(int i = 0; i < a.length; ++i) {
                if(i != index){
                    Array[k++] = a[i];
                }else
                    size=size-1;
            }
            a=Array;
            System.out.println("word is removed");
        }else{
            System.out.println("word is is not there");
        }
    }
    
    
    public WordList sublist(char init, char fin){
        WordList sub = new WordList (this.cap);
        int j=0;
        for(int i = 0; i<this.size; i++){
            if(a[i].charAt(0)>= init && a[i].charAt(0)<= fin){
                sub.a[j] = a[i];
                j++;
                sub.size ++;
            }
        }
        return sub;
    }
   
    
    public static void main(String[] args) {
    // TODO code application logic here
        // inputs start

        int cap=6; //capacity
        int cap2=2; //capacity
        
        int i1=1; //valid position in words2
        int i2=4; //invalid position in words2 ( size<=i2<=capacity-1 )
        int i3=12; //invalid position in words2 ( i3<0 or i3>capacity-1 )
        
        //chosen according to the tests for sublist()
        char c1='i';
        char c2='k';
        char c3='h';
        char c4='m';
        char c5='b';
        char c6='h';  
        char c7='j'; 
        char c8='p'; 
        
        String[] words2={"add", "alpha", "boy", "car"}; //sorted, used to test insert & remove
	String[] words3={"now", "then", "after", "five", "zoom"}; //unsorted, without repetitions - to test constructor 2
        String[] words4={"made","know","seven","know"};//unsorted with repetitions - to test constructor 2
        String[] words5={"fall", "flag","fly","ha", "haha", "ma", "me", "mm","my",  "so"};//to test sublist & find

	String s2="buy";//word not in words2 - test insert inside list
        String s4="boy";//word in words2
        String s7="car";//last word of words2
	String s6="mm";//word in words5 - test find()
	String s1=s7+"ab";
        String s5=s1+"a";
	String s8="aaabbb"; //smaller than first word in words 2
        
        // inputs end 
	//**************************************************************************************

 
        //test constructor 1
        WordList listObj1= new WordList(cap);
        System.out.println("Test 1 - Constructor 1:\n"+"List:\n"+ listObj1.toString());
        System.out.println("capacity="+listObj1.getCapacity());
        System.out.println("size="+listObj1.getSize());        
        
        
        System.out.println("*************************************************************");
        //test constructor 2 with sorted input without repetitions
       
        WordList listObj2= new WordList(words2);
        //test if words are stored correctly
        System.out.println("Test 2 - Constructor 2 (sorted input):\n"+"List:\n"+listObj2.toString());
        System.out.println("capacity="+listObj2.getCapacity());
        System.out.println("size="+listObj2.getSize());

	
	System.out.println("*************************************************************");
        //test constructor 2 - unsorted input without duplicates
        
        WordList listObj4= new WordList(words3);
        //display listObj4
        System.out.println("Test 3 - Constructor 2 (unsorted input without repetitions):\n"+"List:\n"+listObj4.toString());
        System.out.println("capacity="+listObj4.getCapacity());
        System.out.println("size="+listObj4.getSize());

 
	System.out.println("*************************************************************");
        //test constructor 2 - unsorted input & with duplicates
        
        listObj4= new WordList(words4);
        //display listObj4
        System.out.println("Test 4 - Constructor 2 (unsorted input with repetitions):\n"+"List:\n"+listObj4.toString());
        System.out.println("capacity="+listObj4.getCapacity());
        System.out.println("size="+listObj4.getSize());
              
        
        System.out.println("*************************************************************");
        // test insert one word to listObj2 at the end
        
        listObj2.insert(s1);
        System.out.println("Test 5 - insert at end of list, newword: "+s1+"\nList:\n"+listObj2.toString());
        System.out.println("capacity="+listObj2.getCapacity());
        System.out.println("size="+listObj2.getSize());
                
             
        System.out.println("*************************************************************");
        //test insert inside list 
        listObj2=new WordList(words2);
        listObj2.insert(s2);
        System.out.println("Test 6 - insert inside list, newword: "+s2+"\nList:\n"+listObj2.toString());
        System.out.println("capacity="+listObj2.getCapacity());
        System.out.println("size="+listObj2.getSize());


	System.out.println("*************************************************************");
        //test insert at front 
        listObj2=new WordList(words2);
        listObj2.insert(s8);
        System.out.println("Test 7 - insert at front, newword: "+s8+"\nList:\n"+listObj2.toString());
        System.out.println("capacity="+listObj2.getCapacity());
        System.out.println("size="+listObj2.getSize());
        

         System.out.println("*************************************************************");
        //attempt to insert when word is in list
        listObj2=new WordList(words2);
        listObj2.insert(s4);
        System.out.println("Test 8 - attempt to insert when word is in list, newword: "+s4+"\nList:\n"+listObj2.toString());
        System.out.println("capacity="+listObj2.getCapacity());
        System.out.println("size="+listObj2.getSize());
        

         System.out.println("*************************************************************");
        //test insert in empty list
        listObj1= new WordList(cap);
        listObj1.insert(s1);
        System.out.println("Test 9 - insert "+s1+ " in empty list:\n"+"List:\n"+listObj1.toString());
        System.out.println("capacity="+listObj1.getCapacity());
        System.out.println("size="+listObj1.getSize());
        
        
        System.out.println("*************************************************************");
        //test insert when size==capacity and neword not in list 
        listObj2= new WordList(cap2);
        for(int i=0;i<cap2;i++){
            listObj2.insert(s1);
            s1=s1+"a";
        }
        System.out.println("Test 10 - insert - size==capacity, new word not in list, newword: "+s2);
        listObj2.insert(s2);
        System.out.println("List:\n"+listObj2.toString());
        System.out.println("capacity="+listObj2.getCapacity());
        System.out.println("size="+listObj2.getSize());             
        
        
         System.out.println("*************************************************************");
        //test getWordAt valid position
        listObj2=new WordList(words2);
        try{
            System.out.println("Test 11 - getWordAt(i) - valid index i");
            System.out.println(listObj2.getWordAt(i1));
        }
        catch(ArrayIndexOutOfBoundsException e){System.out.println(e);}

        
        System.out.println("*************************************************************");
        //test getWordAt invalid list position
        try{
            System.out.println("Test 12 - getWordAt(i) - invalid index i");
            System.out.println(listObj2.getWordAt(i2));
        }
        catch(ArrayIndexOutOfBoundsException e){System.out.println(e);}


	System.out.println("*************************************************************");
        //test getWordAt invalid list position
        try{
            System.out.println("Test 13 - getWordAt(i) - invalid index i");
            System.out.println(listObj2.getWordAt(i3));
        }
        catch(ArrayIndexOutOfBoundsException e){System.out.println(e);}
        
        
        System.out.println("*************************************************************");
        //test binary search:
        listObj2= new WordList(words5);
        System.out.println("Test 14 - find - key not in list, key is " + s2);
        int searchIndex=listObj2.find(s2);
        System.out.println("returned index: "+ searchIndex);


        System.out.println("*************************************************************");
        System.out.println("Test 15 - find - key  in list, key is " + s6);
        searchIndex=listObj2.find(s6);
        System.out.println("returned index: "+ searchIndex);
        

         System.out.println("*************************************************************");
        //test remove
        System.out.println("Test 16 - remove - word not in list, word is "+s2);
        listObj2=new WordList(words2);
        listObj2.remove(s2);
        System.out.println("List: \n"+listObj2.toString());
        System.out.println("capacity="+listObj2.getCapacity());
        System.out.println("size="+listObj2.getSize());


         System.out.println("*************************************************************");
        //test remove from inside or beginning
        listObj2= new WordList(words2);
        System.out.println("Test 17 - remove from inside or beginning, word is "+s4);
        listObj2.remove(s4);
        System.out.println("List: \n"+listObj2.toString());
        System.out.println("capacity="+listObj2.getCapacity());
        System.out.println("size="+listObj2.getSize());


        System.out.println("*************************************************************");
        //test remove from empty list
        System.out.println("Test 18 - remove from empty list");
        WordList listObj5= new WordList(cap);
        listObj5.remove(s7);
        System.out.println("List: \n"+listObj5.toString());
        System.out.println("capacity="+listObj5.getCapacity());
        System.out.println("size="+listObj5.getSize());

        
        System.out.println("*************************************************************");
        //test sublist  - from empty list - its capacity could be any positive value
        System.out.println("Test 19 - sublist from empty list ");
        WordList listObj6= new WordList(cap);
        WordList listObj7=listObj6.sublist(c3,c4);
        System.out.println("sublist: \n"+listObj7.toString());
        System.out.println("capacity="+listObj7.getCapacity());
        System.out.println("size="+listObj7.getSize());
        
        
        System.out.println("*************************************************************");
        //test sublist  - empty sublist from non empty list - its capacity could be any positive value
        System.out.println("Test 20 - empty sublist from non empty list ");
        listObj6= new WordList(words5);
        listObj7=listObj6.sublist(c1,c2);
        System.out.println("sublist: \n"+listObj7.toString());
        System.out.println("capacity="+listObj7.getCapacity());
        System.out.println("size="+listObj7.getSize());
               
        
        System.out.println("*************************************************************");
        //test sublist - non-empty sublist; words starting with init and fin are in list (more than one for each)
        System.out.println("Test 21 - non-empty sublist ");
        listObj6= new WordList(words5);
        listObj7=listObj6.sublist(c3,c4);
        System.out.println("sublist: \n"+listObj7.toString());
        System.out.println("capacity="+listObj7.getCapacity());
        System.out.println("size="+listObj7.getSize());
        //System.out.println("Initial list: \n"+listObj6.toString());


	System.out.println("*************************************************************");
        //test sublist - non-empty sublist words starting with init and fin are not in list
        System.out.println("Test 22 - non-empty sublist ");
        listObj6= new WordList(words5);
        listObj7=listObj6.sublist(c7,c8);
        System.out.println("sublist: \n"+listObj7.toString());
        System.out.println("capacity="+listObj7.getCapacity());
        System.out.println("size="+listObj7.getSize());
               
        
        
        System.out.println("*************************************************************");
        //test sublist  - sublist is a prefix or suffix
        System.out.println("Test 23 - sublist is a prefix or a suffix");
        listObj6= new WordList(words5);
        listObj7=listObj6.sublist(c5,c6);
        System.out.println("sublist: \n"+listObj7.toString());
        System.out.println("capacity="+listObj7.getCapacity());
        System.out.println("size="+listObj7.getSize());

    }
}

   
