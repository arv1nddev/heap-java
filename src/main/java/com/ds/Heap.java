package com.ds;  

import java.util.Arrays;

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
 * - Add generic type support for non-primitive data.
 * - Improve exception handling and input validation.
 */

import java.util.NoSuchElementException;

public class Heap {
    private int[] heap =new int[1];
    private int size=0;
    private boolean isMaxHeap;
    
    public Heap(String heap_type){
        if (heap_type.equalsIgnoreCase("x")) {
            this.isMaxHeap=true;
        }else if(heap_type.equalsIgnoreCase("n")){
            this.isMaxHeap=false;
        }else{
            throw new IllegalArgumentException("Invalid heap type!");
        }
    }
    public Heap(int [] arr){
        this(arr,true);
    }
    //taking O(n) thorough this,instaed of O(nlogn) by isnerting elements one by one
    public Heap(int[] arr,boolean isMaxHeap){
        this.isMaxHeap = isMaxHeap;
        this.heap=Arrays.copyOf(arr, arr.length);
        this.size=arr.length;
        
        if (isMaxHeap) {
            heapify_MAX_loop(this.heap, this.size);
        }else{
            heapify_MIN_loop(this.heap, this.size);
        }
    }
    
    public int[] getHeap(){
        return Arrays.copyOf(this.heap, this.size);
    }
    int getHeapSize(){
        return this.size;
    }
    boolean getIsMaxHeap(){
        return this.isMaxHeap;
    }
    void setIsMaxHeap(boolean isMaxHeap){
        this.isMaxHeap=isMaxHeap;
    }

    public Heap(){
        this("x");
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
    }
        
    public int remove(){
        if (size==0) throw new NoSuchElementException();

        int root=heap[0];
        heap[0]=heap[size-1];
        size--;
        heapifyDown();
        return root;
    }
        
    public int peek(){
        if (size==0) throw new NoSuchElementException();
        return heap[0];
    }
        
    public void toggleHeapType(){
        if (size==0) throw new NoSuchElementException();
        
        this.isMaxHeap=!this.isMaxHeap;
        if (this.isMaxHeap) {
            heapify_MAX_loop(this.heap,this.size);
        }else{
            heapify_MIN_loop(this.heap,this.size);
        }
    }
        
    private void grow(){
        int[] newHeap=new int[heap.length*2];
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
    public static void heap_sort(int[] arr){
        heap_sort(arr,true,true);
    }
    public static void heap_sort(int[] arr,boolean use_loop){
        heap_sort(arr,use_loop,true);
    }
    public static void heap_sort(int[] arr,boolean use_loop,boolean MAX){
        
        int size=arr.length;
        
        if (MAX) {
            if (use_loop) {
                heapify_MAX_loop(arr);
                // no prints in heap logic
                // System.out.println("using loop to heapify...");
            }else{
                // no prints in heap logic
                // System.out.println("using recursion to heapify...");
                heapify_MAX_recr(arr,size/2-1);
            }
                // no prints in heap logic
            // System.out.println("using max heap for sorting...");
            for(int i=size-1;i>0;i--){
                swap(arr, 0, i);
                heapify_MAX(arr,i,0);
            }
        }else{
            if (use_loop) {
                // no prints in heap logic
                // System.out.println("using loop to heapify...");
                heapify_MIN_loop(arr);
            }else{
                // no prints in heap logic
                // System.out.println("using recursion to heapify...");
                heapify_MIN_recr(arr,size/2-1);
            }
                // no prints in heap logic
            // System.out.println("using min heap for sorting...");
            for(int i=size-1;i>0;i--){
                swap(arr, 0, i);
                heapify_MIN(arr,i,0);
            }
            reverse(arr);
        }
    }
        
    //to convert an array into max heap using recurrsion without size given 
    public static void heapify_MAX_recr(int[] arr,int index){
        int size=arr.length;
        heapify_MAX_recr(arr,size,index);
    }
    
    //to convert an array into max heap using recurrsion without size given 
    public static void heapify_MAX_recr(int[] arr,int size,int index){
        if (index<0) return;
        heapify_MAX(arr, size, index);
        heapify_MAX_recr(arr,size,index-1);
    }
        
    //to convert an array into max heap using loop  without size given 
    public static void heapify_MAX_loop(int[] arr){
        int size=arr.length;
        heapify_MAX_recr(arr, size);
    }
    //to convert an array into max heap using loop  without size given 
    public static void heapify_MAX_loop(int[] arr,int size){
        for(int i=size/2-1;i>=0;i--){
            heapify_MAX(arr, size, i);
        }
    }
    
    //to convert a subarray into max heap at index index 
    public static void heapify_MAX(int[] arr,int size,int index){
        
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
    public static void heapify_MIN_recr(int[] arr,int index){
        int size=arr.length;
        heapify_MIN_recr(arr, size,index);
    }

    //to heapify an array using reccursion with size given 
    public static void heapify_MIN_recr(int[] arr,int size,int index){
        if(index < 0) return;
        heapify_MIN(arr, size, index);
        heapify_MIN_recr(arr,size,index-1);
    }

    //to heapify an array using loop without size given 
    public static void heapify_MIN_loop(int[] arr){
        int size=arr.length;
        heapify_MIN_loop(arr,size);
    }
    
    //to heapify an array using loop with size given
    public static void heapify_MIN_loop(int[] arr,int size){
        for(int i=size/2-1;i>=0;i--){
            heapify_MIN(arr, size, i);
        }
    }
    //to convert a subarray into min heap at index index
    public static void heapify_MIN(int[] arr,int size,int index){
        
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