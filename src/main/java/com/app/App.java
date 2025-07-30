package com.app;
import com.ds.Heap;
import java.util.Arrays;

public class App{
        public static void main(String[] args) {
            int[] arr={35,10,30,45,70,10,55,5,20,90,105};
            Heap obj=new Heap();
            System.out.println("og arr:"+Arrays.toString(arr));
            obj.heap_sort(arr,false);
        System.err.println("sorted arr: "+Arrays.toString(arr));
        Heap.run(obj);
    }
}
