package com.ds;  

/*
 * Heap.java
 * -----------------------
 * A basic and extendable implementation of Heap (Min/Max).
 * 
 * Features:
 * - Supports both MaxHeap and MinHeap types.
 * - Can be heapified using loops or recursion.
 * - Heap Sort with configurable options.
 * - CLI interaction (basic menu-driven interface).
 * - Future-ready for separation into CLI vs Data Structure classes.
 * 
 * Usage:
 * heap_sort(array, useLoop, useMaxHeap)
 * 
 * TODO:
 * - Separate CLI logic into a different class/package.
 * - Add generic type support for non-primitive data.
 * - Improve exception handling and input validation.
 */

import java.util.Scanner;
import java.util.NoSuchElementException;

public class Heap {
    private int[] heap =new int[1];
    private int size=0;
    Scanner mainScanner = new Scanner(System.in);
    private boolean isMaxHeap;
    
    public Heap(){
        this("x");
    }

    public Heap(String heap_type){
        if (heap_type.equalsIgnoreCase("x")) {
            this.isMaxHeap=true;
        }else if(heap_type.equalsIgnoreCase("n")){
            this.isMaxHeap=false;
        }else{
            throw new IllegalArgumentException("Invalid heap type!");
        }
    }

    public Heap(int[] arr,boolean isMaxHeap){
            this.isMaxHeap = isMaxHeap;
            for(int value : arr){
                this.insert(value);
            }
        }
    public static void run(Heap obj){
        
        boolean exiting= false;

        System.out.print("\n\tDo want to create max or min Heap?(X/N)\n\t\t\t\t\t\t: ");
        String heap_type=obj.mainScanner.next();
        try{
            while (!heap_type.equalsIgnoreCase("x") && !heap_type.equalsIgnoreCase("n")) {
            System.out.print("\tPlease enter \"X\" or \"x\" OR \"N\" or \"n\" for max and min heap respectively.\n\t\t\t\t\t\t: ");
            heap_type=obj.mainScanner.next();
            }
            if (heap_type.equalsIgnoreCase("x")) {
                obj.isMaxHeap=true ;
            }else{
                obj.isMaxHeap=false;
            }
            do {
                System.out.println("\n>>>press:\n\t-> 1 for insertion");
                System.out.println("      \n\t-> 2 for deletion");
                System.out.println("      \n\t-> 3 to peek");
                System.out.println("      \n\t-> 4 to view");
                System.out.println("      \n\t-> 5 to toogle Heap type");
                System.out.println("      \n\t-> 6 to exit");

                
                int choice;
                do{
                    System.out.print("\nplease enter(1-6) : ");
                    while (!obj.mainScanner.hasNextInt()) {
                        System.out.println("please enter a number from to 1 to 6!");
                        obj.mainScanner.next();
                    }
                    choice=obj.mainScanner.nextInt();
                }while((choice<1 || choice>6));
                try {
                    switch (choice) {
                    case 1:
                        if (obj.size==0) System.out.print("\nEnter the value of root = ");
                        else System.out.print("\nEnter the value of node : ");
                        int value=obj.mainScanner.nextInt();
                        obj.insert(value);                        
                        break;

                    case 2:
                        obj.remove();
                        break;

                    case 3:
                        obj.peek();
                        break;

                    case 4:
                        obj.view();
                        break;

                    case 5:
                        obj.toggleHeapType();
                        break;

                    case 6:
                    exiting=true;
                    break;

                    default:
                    System.out.println("\nthis statement should never be printed");
                    break;
                }
                } catch (NoSuchElementException e) {
                    System.out.println("\nERROR: Cannot perform operation. The heap is empty!\n");
                } catch (Exception e) {
                    System.out.println("\nAn unexpected error occurred: " + e.getMessage() + "\n");
                }
                
                
                // [Deprecated] Prompt to continue after each operation
                // This feature was originally meant to ask users if they want to continue after each operation.
                // Keeping it disabled for now to avoid unnecessary interruptions during interactive CLI usage.
                // TODO: Re-enable when CLI supports session toggles or step-by-step modes
                
                // if (!exiting) {
                    //     String again;
                    //     System.out.println("\nDo you want to continue?");
                    //     again=obj.mainScanner.next();
                    //     while(!again.equalsIgnoreCase("yes") && !again.equalsIgnoreCase("no")) {
                        //         System.out.println("\nenter either yes or no!!");
                        //         System.out.println("\nDo you want to continue (yes/no)?");
                        //         again=obj.mainScanner.next();
                        //     }
                        //     exiting=again.equalsIgnoreCase("no");
                        // }
                        
            }while (!exiting);
        }finally{
            System.out.println("\nExiting program. Goodbye!");
            obj.mainScanner.close();
        }
    }
        
        
    private static void swap(int [] arr,int a,int b){
        int temp = arr[a];
        arr[a]=arr[b];
        arr[b]=temp;
    }
    private static void reverse(int[] arr){
        int low=0,high=arr.length-1;
        while (low<high) {
            swap(arr, low, high);
            low++;
            high--;
        }
    }
        
    public void insert(int value){                    
        if (this.size==this.heap.length) {
            grow();
        }
        this.heap[this.size]=value;
        this.size++;
        heapifyUp(this.size);
        System.out.print("\nnew heap : ");
        for(int i=0;i<this.size;i++){
            System.out.print(this.heap[i]+"  ");
        }
    }
        
    public int remove(){
        if (size==0) throw new NoSuchElementException();

        int root=heap[0];
        heap[0]=heap[size-1];
        size--;
        heapifyDown();
        System.out.println("\nroot reomoved = "+root);
        return root;
    }
        
    public int peek(){
        if (size==0) throw new NoSuchElementException();

        System.out.println("\nroot = "+this.heap[0]);
        return heap[0];
    }
        
    public void view(){
        if (size==0) throw new NoSuchElementException();

        System.out.print("\nenter l for level view or p for pretty view => ");
        String tree_choice=mainScanner.next();
        while (!tree_choice.equalsIgnoreCase("l") && !tree_choice.equalsIgnoreCase("w")) {
            System.out.println("Please enter either \"L\" or \"W\" !");
            tree_choice=mainScanner.next();
            
        }
        if(tree_choice.equalsIgnoreCase("l")){
            this.viewLevel();
        }else{
            this.prettyView();
        }
    }
        
    public void viewLevel(){
        if (size==0) throw new NoSuchElementException();

        int index=0;
        int level=0;
        int elementInLevel=1;
        System.out.println("\nHeap Tree View (Level by Level):");
        while (index<size) {
            System.out.print("Level "+level+":");
            int count=0;
            while (count<elementInLevel && index<size) {
                System.out.print(heap[index]+" ");
                index++;
                count++;
            }
            System.out.println();
            level++;
            elementInLevel*=2;
        }
    }

    
    //Displays the heap in a visually appealing tree format.
     
    public void prettyView() {
        if (size == 0) throw new NoSuchElementException();

        int maxLevel = (int) (Math.log(size) / Math.log(2));
        prettyViewRecursive(0, 0, maxLevel);
    }

    public void prettyViewRecursive(int index, int level, int maxLevel) {
        if (size==0) throw new NoSuchElementException();

        if (index >= size) {
            return;
        }
        // Print right child first
        prettyViewRecursive(2 * index + 2, level + 1, maxLevel);

        // Print current node
        for (int i = 0; i < level; i++) {
            System.out.print("    ");
        }
        System.out.println("-> " + heap[index]);

        // Print left child
        prettyViewRecursive(2 * index + 1, level + 1, maxLevel);
    }

        
    public void toggleHeapType(){
        if (size==0) throw new NoSuchElementException();
        
        this.isMaxHeap=!this.isMaxHeap;
        if (this.isMaxHeap) {
            heapify_MAX_loop(this.heap,this.size);
        }else{
            heapify_MIN_loop(this.heap,this.size);
        }
        if(this.isMaxHeap){
            System.out.println("\n\ttoggled to max heap.");
        }else{
            System.out.println("\n\ttoggled to min heap.");
        }
    }
        
    private void grow(){
        int[] newHeap=new int[heap.length*2];
        System.out.println("new heap size = "+heap.length*2);
        for(int i=0;i<size;i++){
            newHeap[i]=heap[i];
        }
        heap=newHeap;
    }
        
    public void heapifyUp(int size){
        int index=size-1;
        while (index>0) {
            int parent=(index-1)/2;
            if(( isMaxHeap && ( heap[index] > heap[parent])) || ( !isMaxHeap && (heap[index] <  heap[parent]))){
                swap(heap, parent, index);
                index=parent;
            }else break;
        }
    }
        
    public void heapifyDown(){
        if (isMaxHeap) {
            heapify_MAX(heap, size, 0);
        }else{
            heapify_MIN(heap, size, 0);
        }
    }
        
    //heap sort impelementation  :)
    public void heap_sort(int[] arr){
        heap_sort(arr,true,true);
    }
    public void heap_sort(int[] arr,boolean use_loop){
        heap_sort(arr,use_loop,true);
    }
    public void heap_sort(int[] arr,boolean use_loop,boolean MAX){
        
        int size=arr.length;
        
        if (MAX) {
            if (use_loop) {
                heapify_MAX_loop(arr);
                System.out.println("using loop to heapify...");
            }else{
                System.out.println("using recursion to heapify...");
                heapify_MAX_recr(arr,size/2-1);
            }
            System.out.println("using max heap for sorting...");
            for(int i=size-1;i>0;i--){
                swap(arr, 0, i);
                heapify_MAX(arr,i,0);
            }
        }else{
            if (use_loop) {
                System.out.println("using loop to heapify...");
                heapify_MIN_loop(arr);
            }else{
                System.out.println("using recursion to heapify...");
                heapify_MIN_recr(arr,size/2-1);
            }
            System.out.println("using min heap for sorting...");
            for(int i=size-1;i>0;i--){
                swap(arr, 0, i);
                heapify_MIN(arr,i,0);
            }
            reverse(arr);
        }
    }
        
    //to convert an array into max heap using recurrsion without size given 
    public void heapify_MAX_recr(int[] arr,int index){
        int size=arr.length;
        heapify_MAX_recr(arr,size,index);
    }
    
    //to convert an array into max heap using recurrsion without size given 
    public void heapify_MAX_recr(int[] arr,int size,int index){
        if (index<0) return;
        heapify_MAX(arr, size, index);
        heapify_MAX_recr(arr,size,index-1);
    }
        
    //to convert an array into max heap using loop  without size given 
    public void heapify_MAX_loop(int[] arr){
        int size=arr.length;
        heapify_MAX_recr(arr, size);
    }
    //to convert an array into max heap using loop  without size given 
    public void heapify_MAX_loop(int[] arr,int size){
        for(int i=size/2-1;i>=0;i--){
            heapify_MAX(arr, size, i);
        }
    }
    
    //to convert a subarray into max heap at index index 
    public void heapify_MAX(int[] arr,int size,int index){
        
        int largest =index;
        //compare both with parent,swap if greater and call heapify again at child index
        if (2*index+1<size && arr[2*index+1]>arr[largest] ) {
            largest=2*index+1;
        }
        if (2*index+2<size && arr[2*index+2]>arr[largest]) {
            largest=2*index+2;
        }
        if (largest!=index) {
            swap(arr, index, largest);
            heapify_MAX(arr, size, largest);
        }
    }
        
    //to heapify an array using reccursion without size given 
    public void heapify_MIN_recr(int[] arr,int index){
        int size=arr.length;
        heapify_MIN_recr(arr, size,index);
    }

    //to heapify an array using reccursion with size given 
    public void heapify_MIN_recr(int[] arr,int size,int index){
        if(index < 0) return;
        heapify_MIN(arr, size, index);
        heapify_MIN_recr(arr,size,index-1);
    }

    //to heapify an array using loop without size given 
    public void heapify_MIN_loop(int[] arr){
        int size=arr.length;
        heapify_MIN_loop(arr,size);
    }
    
    //to heapify an array using loop with size given
    public void heapify_MIN_loop(int[] arr,int size){
        for(int i=size/2-1;i>=0;i--){
            heapify_MIN(arr, size, i);
        }
    }
    //to convert a subarray into min heap at index index
    public void heapify_MIN(int[] arr,int size,int index){
        
        //lets assume parent node is smalllest
        int smallest=index;
        if (2*index+1 < size && arr[2*index+1] < arr[smallest]) {
            smallest=2*index+1;
        }
        if (2*index+2 < size && arr[2*index+2] < arr[smallest]) {
            smallest=2*index+2;
        }
        if (smallest!=index) {
            swap(arr, smallest, index);
            heapify_MIN(arr, size, smallest);
        }
    }
    
}