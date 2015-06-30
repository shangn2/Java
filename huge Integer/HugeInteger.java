/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hugeinteger;

/**
 *
 * 
 */
import java.util.Random;
import java.lang.Object;

public class HugeInteger {

    private int len;
    private int [] a;
    private int sign; // 1 = -ve && 0 = +ve
    
    
    public HugeInteger(String val){ 
        if(Validity(val) == false){
            System.out.println("the input is invalid");
        }else {
            sign= SignCheck(val);
            char [] t1 = toCharArray(val);
            t1 = Shift(t1, sign);
            a = toIntArray(t1);
            if(sign ==0) 
                len = val.length();
            else len = val.length()-1;
        }
            
    }
    
    //done
    public HugeInteger(int n){
        if(n>0){
            Random ran = new Random();//creating random function
            a = new int[n];
            if(n ==1){ // 0 can be leading term when it has only 1 digit
                a[0] = ran.nextInt(10);
            }else{
                a[0] = ran.nextInt(9)+1;//maksure the first digit is in range 1-9
                for (int i = 1;i<n; i++){
                    a[i] = ran.nextInt(10); //creating random digit from 0-9
                }
            }
            len = n;
        }
    }
    
    // following are useful for constructors
    private boolean Validity(String val){
        boolean check = true; 
        int l = val.length();
        char [] temp = toCharArray(val);
        
        // first digit = 0 and has more than 1 digits
        // first digit is -ve followed with a 0 on 2nd digit
        if(((int)temp[0] == 48)&& (l > 1)){
            return check= false;
        }
        for(int i =0; i < l; i++){
            if(!(((int)temp[i]== 45)||((int)temp[i] >=48 && (int)temp[i] <= 57))){
                return check = false;
            }
        }
        return check;
    }

    private int SignCheck(String val){
        int sign;
        if((int)val.charAt(0) == 45){ 
            sign = 1; 
            }
        else{
            sign = 0;
        }
        return sign;
    }

    private char [] toCharArray(String val){
        int temp;
        if (val == null){//make sure the value list is not empty
            return null;
        }
        int length = val.length();
        char [] array = new char [length];
        for (int i = 0; i< length; i++){
            array[i] = val.charAt(i);//passes back to the array list
        }
        return array;
        
    }
    
    private char [] Shift(char [] list, int sign){
        int length = list.length;
        if(sign == 1){
            char [] a1 = new char [length-1];
            for (int i = 0; i<list.length -1; i++){
                a1[i] = list[i+1];
            }
            len --;
            return a1;            
        } 
        char [] a1 = list;
        return a1;
    }
    
    private int [] toIntArray(char [] val){ 
        int temp;
        if (val == null){//make sure the value list is not empty
            return null;
        }
        int length = val.length;
        int [] array = new int [length];
        for (int i = 0; i< length; i++){
            temp = ((int)val[i])-48;//convert that char at that point into int
            array[i] = temp;//passes back to the array list
        }
        return array;
    }
    
    //useful for methods
    //0=this; 1=h; 2=0
    private int checkLargeN(HugeInteger h){  
        int c1 = this.len;
        int c2 = h.len;
        int check=0; 
        
        if(c1<c2){ //this < h
            check = 1;
        }else if(c1 > c2){// this > h
            check = 0;
        }else{
            boolean flag = true;
            int i = 0;
            while((i<c1)&&flag){ //checking which one has the biggest number
                if(this.a[i] > h.a[i]){ 
                    check =0;
                    flag = false;
                }else if(this.a[i] < h.a[i]){
                    check =1;
                    flag = false;
                }else{
                    i++;
                }
            }
            if(flag == true){ //finished comparing all the digits, they are identical 
                check = 2;
            }
        }
        return check;
    }
    
    private int [] deleteLeading(int [] list){ // delete the extra zeros resulting from calculation
        int len1 = list.length;
        
        int counter=1;
        int x=0;
        boolean flag = true;
        while(x<len1 && flag){ //deleting the leading zeros
            if(list[0] ==0){
                if(list[x] !=0){ //if next one is not 0, terminate the while loop
                    flag = false;
                } else 
                    x++;
                    counter ++;
            }else 
                return list;    
        }
            int [] list2 = new int[len1-x];
            int y =0;
            for(int i =x; i<len1; i++){ //past it on to the new list created
                list2[y] = list[i];
                y++;
            }
            list = list2;
        return list;
    }
   
    public HugeInteger add(HugeInteger h){
        int l2=0; // new length of the array
        int adder1,adder2,sum,large,newsign=0;
        int carry =0;
        int c1 = this.len;
        int c2 = h.len;
        
        if(this.sign == h.sign){
            if(c1<=c2) //this < h
                l2 = c2 +1;
            else if(c1 > c2){// this > h
            l2 = c1+1;
            }
        }
        else{
            if(c1<=c2) //this < h
                l2 = c2;
            else if(c1 > c2){// this > h
                l2 = c1;
            }
        }
        
        int [] temp = new int[l2]; //creating temporary storage
        int counter = l2-1;
        large = checkLargeN(h);
 
        if (this.sign  == h.sign){
            for (int i = l2-1; i>=0; i--){
                if (c1>0){
                    c1--;
                    adder1 = this.a[c1];
                }else adder1 = 0;
                
                if (c2 >0){
                    c2--;
                    adder2 = h.a[c2];
                }else adder2 = 0;
                
                sum = adder1 + adder2 + carry;
                temp[i] = sum%10;
                carry = sum/10;
            }newsign = this.sign;
        }else{
            
            if(large ==0){ //
                newsign = this.sign;
            }else if(large ==1){
                newsign = h.sign;
            }else if(large ==2){// this and h are identical
                HugeInteger fin = new HugeInteger(1); //final hugeinteger class
                fin.a[0] = 0;
                fin.len = 1;
                fin.sign = 0;
                return (fin);// return 0
            }
            
            for (int i =l2-1; i>=0; i--){
                if(large ==0){//giving out positions
                    if(c1>0){
                        c1--;
                        adder1 = this.a[c1];
                    }else adder1 = 0;
                    
                    if(c2>0){
                        c2--;
                        adder2 = h.a[c2];
                    }else adder2 =0;
                }else {
                    if(c2>0){
                        c2--;
                        adder1 = h.a[c2];
                    }else adder1 =0;
                    
                    if(c1>0){
                        c1--;
                        adder2 = this.a[c1];
                    }else adder2 = 0;
                }
                
                if((adder1-carry) < adder2){
                    
                    sum = adder1 - adder2 - carry + 10;
                    carry = 1;
                }else{
                    sum = adder1 - adder2 - carry;
                    carry =0;
                }
                temp[i] = sum;
            }   
        }
        temp = deleteLeading(temp);

        HugeInteger fin = new HugeInteger(temp.length); //final hugeinteger class
        fin.a = temp;
        fin.sign = newsign;
        fin.len = temp.length;
        
        return(fin);
    }

    public HugeInteger subtract(HugeInteger h){
        int l2=0; // new length of the array
        int adder1,adder2,sum,large,newsign=0;
        int carry =0;
        int c1 = this.len;
        int c2 = h.len;
        large = checkLargeN(h);
        
        if(this.sign != h.sign){
            newsign = this.sign;
            if(c1<=c2) //this < h
                l2 = c2 +1;
            else if(c1 > c2){// this > h
            l2 = c1+1;
            }
        }
        else{
            if(c1<=c2){ //this < h
                l2 = c2;
            }
            else if(c1 > c2){// this > h
                l2 = c1;
            }
            if(large==1)
                newsign = h.sign;
            else if(large ==0) 
                newsign = this.sign;
            else if(large == 2){
                HugeInteger fin = new HugeInteger(1); //final hugeinteger class
                fin.a[0] = 0;
                fin.sign = 0;
                fin.len = 1;
                return (fin);// return 0
        }
        }
        
        int [] temp = new int[l2]; //creating temporary storage 
        int counter = l2-1;
        
        if (this.sign  != h.sign){
            for (int i = l2-1; i>=0; i--){
                if (c1>0){
                    c1--;
                    adder1 = this.a[c1];
                }else adder1 = 0;
                
                if (c2 >0){
                    c2--;
                    adder2 = h.a[c2];
                }else adder2 = 0;
                
                sum = adder1 + adder2 + carry;
                temp[i] = sum%10;
                carry = sum/10;
            }
        }else{
            for (int i =l2-1; i>=0; i--){
                if(large ==0){//giving out positions
                    if(c1>0){
                        c1--;
                        adder1 = this.a[c1];
                    }else adder1 = 0;
                    
                    if(c2>0){
                        c2--;
                        adder2 = h.a[c2];
                    }else adder2 =0;
                }else {
                    if(c2>0){
                        c2--;
                        adder1 = h.a[c2];
                    }else adder1 =0;
                    
                    if(c1>0){
                        c1--;
                        adder2 = this.a[c1];
                    }else adder2 = 0;
                }
                
                if((adder1-carry) < adder2){
                    
                    sum = adder1 - adder2 - carry + 10;
                    carry = 1;
                }else{
                    sum = adder1 - adder2 - carry;
                    carry =0;
                }
                temp[i] = sum;
            }   
        }
        temp = deleteLeading(temp);

        HugeInteger fin = new HugeInteger(temp.length); //final hugeinteger class
        fin.a = temp;
        fin.sign = newsign;
        fin.len = temp.length;
         
        return(fin);
    }
    
    public HugeInteger multiply(HugeInteger h){
        int newsign,buffer,carry=0;
        int mult1, mult2;
        int large = checkLargeN(h);
        int c1 = this.len;
        int c2 = h.len;
        int len1= c1+c2;
        
        if(this.len ==1|| h.len==1){ //takes care of 0 multiplier case
           if(this.a[0] ==0 || h.a[0]==0){
            HugeInteger fin = new HugeInteger(1); //final hugeinteger class
            fin.a[0] = 0;
            fin.sign = 0;
            fin.len =1;
            return(fin);
            //return (fin);// return 0
           } 
        }
        
        int [] temp = new int [len1];
        //final result
        if (this.sign != h.sign) newsign = 1; //-ve 
        else  newsign = 0; //+ve 
        
        for(int i=len1-1;i>=0; i--){
            temp[i] =0;
        }

        for(int i = c2-1; i>=0;i--){ //multiplying
            for(int j = c1-1; j>=0; j--){
                temp[i+j+1] += this.a[j]*h.a[i]; 
            }
        }
        
        boolean flag=true;
        
        while(flag){
            flag = false;
            for(int i =len1-1; i>=0; i--){
                buffer = temp[i];
                temp[i] = buffer%10;
                if(carry>0)
                    temp[i] = temp[i] + carry;
                carry = buffer/10;
                
                if((buffer >=10)){
                    flag = true;
                }
            }
        }
        temp = deleteLeading(temp);
        HugeInteger fin = new HugeInteger(temp.length); //final hugeinteger class
        fin.a = temp;
        fin.sign = newsign;
        fin.len = temp.length;

    return(fin);
    }
    
    public int compareTo(HugeInteger h){
        int s = 1;
        boolean check = false;
        
        if (this.len == h.len){
            // both +ve or -ve
            if((this.sign == 1 && h.sign == 1) || (this.sign == 0 && h.sign == 0)){
                for (int i = 0; i < this.len; i++){ 
                    if(this.a[i] == h.a[i]){ // checking each array to match
                        s = 0;
                        check = true;
                    }
                    if(check) return s; // exit when they matches
                }                
            }
        }
        return s;
    }

    public String toString(){
        String longString = "";
        if(this.sign ==1){
            longString = "-";
            
            for(int i=0; i<this.len;i++){
                longString += this.a[i];
            }
        }else{
            for(int i=0; i<this.len;i++){
                longString += this.a[i];
            }
        }
        return longString;
    }
  
    public static void main(String[] args) {
        // TODO code application logic here
        int a = 4;
        int c = 6;
        String word1 = "12345";
        String word2 = "a34";
        String word3 = "0001";
        String word4 = "0";
        
        String word5 = "152399025";
        String word6 = "99";
        String word7 = "-12345";
        
        //System.out.println("Free memory (bytes): " + Runtime.getRuntime().freeMemory());
        //long t1 =Runtime.getRuntime().freeMemory();
        //HugeInteger test1 = new HugeInteger(100000000); 
        //long t2 = Runtime.getRuntime().freeMemory();

       //System.out.println("multiplication");

       //HugeInteger test18 = test1.multiply(test1); 
       //System.out.println("result is multiplication between "+ test1.toString() +" and "+test1.toString());
       //System.out.println("testing result is "+ test18.toString());

       //System.out.println("Free memory (bytes): " + (t1-t2));
       
       //System.out.println(" the time usage is ");
       
       HugeInteger test1 = new HugeInteger(100000000);
       HugeInteger test2 = new HugeInteger(10);
       long t1=System.currentTimeMillis();
       test2 = test1.subtract(test1);
       long t2=System.currentTimeMillis();
       
       System.out.println("time used " + (t2-t1));
     
       
    }
    
}
