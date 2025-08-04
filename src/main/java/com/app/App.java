package com.app;
import com.ds.Heap;
import com.ds.HeapCLI;
import java.util.Arrays;
import java.util.Scanner;

public class App{
        public static void main(String[] args) {

        //sorting an array using heap sort
        System.out.println("--- 1. Heap Sort Demonstration ---");
        int[] arr={35,10,30,45,70,10,55,5,20,90,105};
        Heap heap1=new Heap();
        System.out.println("original array:"+Arrays.toString(arr));
        heap1.heap_sort(arr,false);
        System.err.println("sorted array (ascending): "+Arrays.toString(arr));
        
        pressEnterToContinue();

        // array is already sorted, let's use it to build a heap
        System.out.println("\n--- 2. Manipulating an Existing Heap via CLI ---");
        Heap heap2=new Heap(arr);
        HeapCLI.run(heap2);
        System.out.println("Heap after CLI manipulation: " + Arrays.toString(heap2.getHeap()));

        pressEnterToContinue();

        System.out.println("\n--- 3. Creating and Manipulating a New Heap via CLI ---");
        // Run the CLI, which will prompt the user to create a new heap
        Heap heap3 = HeapCLI.run();
        System.out.println("Heap created using CLI : "+Arrays.toString(heap3.getHeap()));;
    }
    private static void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue to the next demonstration...");
        try (Scanner scanner = new Scanner(System.in)) {
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
