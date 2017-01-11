

package asg_3;
import BasicIO.ASCIIDataFile;

/*
    Student: Trevor Vanderee
    Number: 5877022
    Course: COSC 2P03
    Assignment 3
 */

public class Sorting{
     private ASCIIDataFile in;
     private Node list,p;
     private int max;
     private int trickleCount;

     public Sorting( ){
          int n;
          int k;
          max = Integer.MIN_VALUE;
          k = 0;
          list = new Node (null,null);
          p = list;
          in = new ASCIIDataFile( );
          n = in.readInt();
          k = in.readInt();
          for (int i = 0; i < n/k ; i++) {
               int[] heap = buildArray(k);

               p.next = new Node(null, heap);
               p = p.next;
          }
          System.out.println("Orginal List");
          printList(list,"");
          p=list;
          while(p.next!=null){
               p=p.next;
               p.item = heapSort(p.item);
          }
          list = radixSort(list,k);
          System.out.println("Sorted List");
          emptyList(list);
          trickleCount=0;
          //partB();
          in.close();
          System.exit(0);
     }
     private Node GenerateRandomNumbers(int n,int k){
          Node out = new Node(null,null);
          p = out;
          for (int i = 0; i < n/k ; i++) {
               int[] heap = randomArray(k);
               heap = heapSort(heap);
               printHeap(heap);
               p.next = new Node(null, heap);
               p = p.next;
          }
          return out;
     }
     /*
     GenerateRandomNumbers Creates a list of random arrays
     generated in 'randomArray'
      */
     private int[]randomArray(int k){
          int[] temp = new int[k+1];
          temp[0]=Integer.MIN_VALUE;
          for(int i =1; i<k+1; i++){
               temp[i] = (int)(Math.random()*10000)+1;
          }
          return temp;
     }
     /*
     Random Array is used to create an array filled with integers
     from 1 to 10000 at complete random.
      */
     private int[] buildArray(int k ){
          int[] temp= new int[k+1];
          temp[0] = Integer.MIN_VALUE;
          for(int i = 1; i<=k; i++){
               temp[i] = in.readInt();
               if(temp[i]>max){
                    max = temp[i];
               }
          }
          return temp;
     }
     /*
     buildArray reads in integers from a data file and fils
     an array based on given values
      */
     private int[] heapSort(int[] in){
          int temp,child;
          int size = in.length-1;
          for(int i = (int)Math.floor(size/2); i>=1; i--){
               check(i,in);
          }
          return in;
     }
     /*
     heapSort takes an array as input and sorts the array
     into minHeap format
      */
     private int[] check(int i,int[] in){
          int temp,rChild;
          if (in[i] > in[i*2]) {
               temp=in[i];
                 trickleCount++;
               in[i]=in[i*2];
                 trickleCount++;
               in[i*2]=temp;
                 trickleCount++;
               if(i*4<in.length-1)
                    check(i*2,in);
          }
          rChild = (i*2)+1;
          if(rChild<in.length){
               if(in[i] > in[rChild]) {
                    temp = in[i];
                    trickleCount++;
                    in[i] = in[rChild];
                    trickleCount++;
                    in[rChild] = temp;
                    trickleCount++;
                    if(rChild*2<=in.length)
                         check(i*2,in);
               }
          }
          return in;

     }
     /*
     check takes an array and recursively checks to see that
     the parent is greater than its children in all circumstances
      */
     private Node radixSort(Node in, int k){
          Node[] buckets = new Node[max+1];
          Node p,q,r;
          Node out = null;
          int big = k;
          int[] arr;
          int locate;
         if(in.next.next == null) {
               System.out.println("true");
               return in;
          }
         for(int i = 0; i<max+1; i++){
             buckets[i] = new Node(null,null);
         }
         while(k>1){
              out = new Node(null,null);
              r=in;
              while(r.next!=null) {
                   r = r.next;
                   arr = r.item;
                   locate = arr[k-1];
                   p = buckets[locate];
                   while(p.next!=null){
                        p=p.next;
                   }
                   p.next = new Node(null, arr);
              }
              q=out;
              for(int i = 0; i <= max; i++){
                   p = buckets[i];
                   while(p.next!=null){
                        p=p.next;
                        q.next = new Node(null,p.item);
                        q=q.next;
                   }
                   buckets[i].next = null;
              }
              k--;
              in = out;
              printList(out,"Radix Sort "+(big-k));
         };
         return out;
     }
     /*
     radixSort takes a list of values and  sorts them
     from greatest to least
      */
     private void emptyList(Node in){
          if(in==null)
               return;
          int print, size;
          int[] wait,add;
          Node p,q;
          int[] arr;
          while(true) {
               p = in;
               if(p.next==null)
                    break;
               q=p;
               p=p.next;
               arr = p.item;
               printList(in,"");
               System.out.println("Item Removed: " +arr[1]);
               System.out.println("Remaining List:");
               while (true) {
                    if(p.next!=null) {
                         q = p;
                         p = p.next;
                         q.item[1] = p.item[1];
                         trickleCount++;
                         wait = q.item;
                         q.item = heapSort(wait);
                    }else if (p.next == null) {
                         size = p.item.length;
                         p.item[1] = p.item[size-1];
                         trickleCount++;
                         add = new int[size - 1];
                         for (int i = 0; i < size - 1; i++) {
                              add[i] = p.item[i];
                              trickleCount++;
                         }
                         p.item = add;
                         size = p.item.length;
                         if (size == 1) {
                              q.next = null;
                              p.item = null;
                              break;
                         }
                         heapSort(p.item);
                         break;
                    }
               }
          }
     }
     /*
     emptyList takes a list and (if sorted) will output the
     sorted values while clearing the list
      */
     private void partB( ){

          double start,end;
          start = System.currentTimeMillis();
          list = GenerateRandomNumbers(10000,10000);
          //printList(list,"hi");
          System.out.println("Swaps: "+trickleCount);
          max = 10000;
          System.out.println(list);
          list = radixSort(list,10001);
          trickleCount = 0;
          emptyList(list);
          end = System.currentTimeMillis();
          System.out.println("Total Swaps: " + trickleCount);
          System.out.println("Total time: " + (end-start));
     }
     /*
     used as a  template for part B
      */
     private void printList(Node ls, String message){
          Node p;
          p = ls;
          while(p.next!=null){
               p = p.next;
               System.out.println(message);
               printHeap(p.item);
          }
     }
     /*
     Takes a list and prints its values
      */
     private void printHeap(int[] hp){
          int t = hp.length;
          for(int i = 1; i<t; i++){
               System.out.println(hp[i]);
          }
     }
     /*
     Takes an array and prints its values
      */
     public static void main(String[]args){Sorting s = new  Sorting();}
}
