/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bstset;

/**
 * 
 * @author Nan Shang
 */
public class BSTSet {
    private TNode root;
    private int size;
    
    public BSTSet(){
        root = null;
        size =0;
    }
    
    public BSTSet(int [] input){
        input = duplicate(input);
        size = input.length;
        TNode temp,p;
        boolean flag = true;
        
        root = new TNode(input[0],null,null);

        for(int i =1; i<size; i++){
            
            temp = new TNode(input[i],null,null);
            //System.out.println("temp number is "+input[i]);
            flag = true;
            p = root;
            while(flag == true){
                if(p.element > input[i]){ //input smaller than the tester node
                    
                    if(p.left == null){
                        p.left = temp;
                        flag = false;
                    }else{
                        p = p.left;
                    }
                }else {
                    if(p.right == null){
                        flag = false;
                        p.right = temp;
                        
                    }else{
                        p = p.right;
                    }
                } 
            }
        }
    }
    
    //self made
    private int [] duplicate(int [] input){
        int len = input.length;
        int counter=0;
        
        for(int i =0; i<len; i++){
            for (int j =i+1; j<len-counter; j++){
                //System.out.println("** i is " + i + " j is "+j);
                //System.out.println(" i is "+input[i] + " and j is " +input[j]);
                if(input[i] ==input[j]){
                    for(int k =j; k<len-1;k++){
                        input[k] = input[k+1];
                    }
                    counter++;
                }
            }
        }
        int [] temp = new int [len-counter];
        for(int i=0; i<temp.length; i++){
            temp[i] = input[i];
        }        //System.out.println("the temp is done");        
        return temp;

    }
    
    public boolean isIn(int v){
        boolean check = false;
        TNode p = root;
        
        for(int i =0; i<size; i++){
            while(check = true){
                if(p.element > v){ //input smaller than the tester node
                    if(p.left == null){
                        check = false;
                        return check;
                    }else{
                        p = p.left;
                    }
                }else if(p.element < v){
                    if(p.right == null){
                        check = false;
                        return check;
                    }else{
                        p = p.right;
                    }
                }else{
                    check = true;
                    return check;
                } 
            }
        }
        return check;
    }
    
    public void add(int v){
        boolean flag = true;
        TNode p = root;
        TNode temp = new TNode(v,null,null);
        if(root == null){
            root = temp;
        }else if(isIn(v)){
            System.out.println("the input value is already exist in the list\n");
        }else{
            while(flag == true){
                if(p.element > v){ //input smaller than the tester node
                    if(p.left == null){
                        p.left = temp;
                        flag = false;
                    }else{
                        p = p.left;
                    }
                }else if(p.element < v){
                    if(p.right == null){
                        p.right = temp;
                        flag = false;
                    }else{
                        p = p.right;
                    }
                }else{
                    flag = false;
                } 
            }
        }
    }

    public boolean remove(int v){
        boolean flag = true;
        TNode p = root, buffer=root;
        int position=0; //0 = center, 1 = left, 2 = right
        
        if(!isIn(v)){
            System.out.println("the value is not found in the tree");
            return false;
        }else{
            while (flag){ // find the position at p + keep in track of the position
                if(v < p.element){
                    buffer = p;
                    p = p.left;
                    position = 1;
                }else if(v > p.element){
                    buffer = p;
                    p = p.right;
                    position = 2;
                } else {
                    flag = false;
                }
            }
            if(p.left==null && p.right ==null){ //NO nodes for both of them
                if(position ==1){
                    buffer.left = null;
                }else if(position ==2){
                    buffer.right = null;
                }
            }else if(p.left != null && p.right ==null){ //left has no node
                if(position ==1){
                    buffer.left = p.left;
                }else if(position == 2){
                    buffer.right = p.left;
                }
            }else if(p.right != null && p.left ==null){ // right las no node
                if(position ==1){
                    buffer.left = p.right;
                }else if(position == 2){
                    buffer.right = p.right;
                }
            }else{//delete node has 2 nodes underneath
                flag = true;
                TNode temp2 = p.left, buffer2 = p.left;
                while(flag){
                    if(temp2.right != null){
                        buffer2 = temp2;
                        temp2 = temp2.right;
                    }else
                        flag = false;
                }
                if(temp2.left != null){ //if there is a left branch at the most right branch
                    buffer2.right = temp2.left; // pass the left branch back to the node on top of it
                }else{
                    buffer2.right = null;
                }
                if(position ==1){
                    buffer.left = temp2;
                }else if(position ==2){
                    buffer.right = temp2;
                }
            }
        }
        return true;
    }
    
    
    //personally made
    private int [] Convert(BSTSet n){;
        TNode p = n.root;
        int i=0;
        int [] list = new int[n.size];
        list = Element(p,list,0);
        
        return list;
    }
    
    
    private int [] Element(TNode n,int [] list,int counter){
        if(n!=null){            
                Element(n.left,list,counter);
                
                if(list[counter]!=0){
                    while(list[counter]!=0)
                        counter ++;
                 }
                
                list[counter++] = n.element;
                
                Element(n.right,list,counter);
            }
        return list;
    }
 
    public BSTSet union(BSTSet s){
        int len1 = s.size;
        int len2 = this.size;
        
        int [] buffer = Convert(this);
        
        if(len1 ==0){
            System.out.println("The input binary search tree is empty");
            return this;
        }else if(len2 ==0){
            System.out.println("The original binary search tree is empty");
            return s;
        }
        int [] temp = Convert(s);
        for(int i =0; i<temp.length; i++){
            if(!(isIn(temp[i]))){
                add(temp[i]);
                this.size ++;
            }
        }
        
        return this; 
    }
    
    public BSTSet intersection(BSTSet s){
        int len1 = this.size;
        int len2 = s.size;
        int [] temp2,temp3;
        int counter=0;
        BSTSet tree;
        
        if(len1 ==0 || len2 ==0){
            System.out.println("one of the tree is empty");
            BSTSet n = new BSTSet();
            return n;
        }

        int [] temp = Convert(s);
        if(len1>len2){
            temp2 = new int[len2];
        }else{
            temp2 = new int[len1];
        }        
        for(int i =0; i<temp.length; i++){
            if((isIn(temp[i]))){
                temp2[counter] = (temp[i]);
                counter ++;                
            }
        }
        temp3 = new int[counter];
        
        for(int i =0; i<counter; i++){
            temp3[i] = temp2[i];
        }
        tree = new BSTSet(temp3);
        
        return tree; //testing
    }
    
    public int size(){
        return this.size;
    }
    
    public void printBSTSet(){
        if(root==null)
            System.out.println("The set is empty");
            else{
            System.out.print("The set elements are: ");
            printBSTSet(root);
            System.out.print("\n");
        }
    }
     
    private void printBSTSet(TNode t){
        if(t!=null){
            printBSTSet(t.left);
            System.out.print(" " + t.element + ", ");
            printBSTSet(t.right);}
    }

    
    public static void main(String[] args) {
        // TODO code application logic here
            int[] d1 = {1, 3, 2, 4};
            int[] d2 = {5, 6, 5, 1, 2, 2, 4, 0, 20, 32, 3, 17};
            int[] d3 = {17, 2, 29, 18, 30};
            int[] d4 = {7, 1, 10, 12, 19, 21};
            int[] d5 = {21, 11, 17, 30};
            System.out.println("Test1---constructor1" );
            BSTSet a1 = new BSTSet();
            a1.printBSTSet();
            System.out.println("\n");

            System.out.println("Test2---constructor2---normal case" );
            BSTSet a2 = new BSTSet(d1);
            a2.printBSTSet();
            System.out.println("\n");

            System.out.println("Test3---constructor2---with repetitions" );
            BSTSet a3 = new BSTSet(d2);
            a3.printBSTSet();
            System.out.println("\n");
            
            System.out.println("Test4---isIn(1)---yes" );
            System.out.println(a3.isIn(1));
            System.out.println("\n");

            System.out.println("Test5---isIn(8)---no" );
            System.out.println(a3.isIn(8));
            System.out.println("\n");

            System.out.println("Test6---add(2)---with repetitions" );
            BSTSet a4 = new BSTSet(d3);
            a4.printBSTSet();
            a4.add(2);
            a4.printBSTSet();
            System.out.println("\n");

            System.out.println("Test7---add(3)---without repetitions" );
            a4.add(3);
            a4.printBSTSet();
            System.out.println("\n");


            System.out.println("Test9---remove(2)" );
            System.out.println(a3.remove(2));
            a3.printBSTSet();
            System.out.println("\n");

            System.out.println("Test10---remove(6)" );
            System.out.println(a3.remove(6));
            a3.printBSTSet();
            System.out.println("\n");

            System.out.println("Test11---remove(30)" );
            System.out.println(a3.remove(30));
            System.out.println("\n");

            System.out.println("Test12---union()" );
            BSTSet a9 = new BSTSet(d4);
            BSTSet a10 = new BSTSet(d5);
            BSTSet a8 = a10.union(a9);
            a8.printBSTSet();
            System.out.println("\n");


            System.out.println("Test13---union()" );
            //a1.printBSTSet();
            //a9.printBSTSet();
            BSTSet a5 = a9.union(a1);
            a5.printBSTSet();
            System.out.println("\n");

            System.out.println("Test14---intersection()" );
            a9.printBSTSet();
            a10.printBSTSet();
            BSTSet a6 = a9.intersection(a10);
            a6.printBSTSet();
            System.out.println("\n");

            System.out.println("Test15---intersection()-with empty set" );
            a1.printBSTSet();
            a10.printBSTSet();
            BSTSet a7 = a10.intersection(a1);
            
            
            a7.printBSTSet();
            System.out.println("\n");

            System.out.println("Test16---size()" );
            System.out.println(a5.size());

            System.out.println("Test17---size()---empty" );
            System.out.println(a1.size()); 


            
            
        
    }
    
} 
