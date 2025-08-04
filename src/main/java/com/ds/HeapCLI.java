package com.ds;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class HeapCLI {
    static Scanner mainScanner = new Scanner(System.in);

    private static String getInput(String prompt){
        System.out.println(prompt);
        return  mainScanner.nextLine().trim();
    }
    private static int getIntInput(String prompt){
        System.out.println(prompt);
        while (true) {
            String input= mainScanner.nextLine().trim();
            try{
                return  Integer.parseInt(input);
            }catch(NumberFormatException e){
                System.out.println("Please enter a integer");
            }
        }
    }
    private static boolean promptHeapType(){
        String heap_type=getInput("\n\tDo want to create max or min Heap?(X/N)\n\t\t\t\t\t\t: ");
            while (!heap_type.equalsIgnoreCase("x") && !heap_type.equalsIgnoreCase("n")) {
            System.out.print("\tPlease enter \"X\" or \"x\" OR \"N\" or \"n\" for max and min heap respectively.\n\t\t\t\t\t\t: ");
            heap_type=mainScanner.next();
            }
            if (heap_type.equalsIgnoreCase("x")) {
                return true;
            }else{
                return false;
            }
    }

    private static boolean isValidInput(String input,String[] valids,int size){
        boolean validity=false;
        for(int i=0;i<size;i++){
            validity=validity || (input.equalsIgnoreCase(valids[i]));
        }
        return validity;
    }

    //create an instance of heap and do operation on it
    public static Heap run(){
        boolean isMaxHeap=promptHeapType();
        Heap heap;
        if (isMaxHeap) {
            heap = new Heap("x");
        }else{
            heap = new Heap("n");
        }
        run(heap);
        return heap;
    }

    private static final String[] VALID_COMMANDS = 
    { "1", "2", "3", "4", "5", "6", "insert", "delete", "peek", "view", "toggle", "exit" };
    
    //to do operation on an instance of heap

    public static void run(Heap obj){
        boolean exiting=false;
        do {
                String choice="";
                do {
                    choice=getInput("\n>>>enter:\n\t\t"+
                                    "-> 1/insert for insertion\n\t\t"+
                                    "-> 2/delete to remove\n\t\t"+
                                    "-> 3/peek to peek\n\t\t"+
                                    "-> 4/view to view\n\t\t"+
                                    "-> 5/toggle to toogle Heap type\n\t\t"+
                                    "-> 6/exit to exit");                    
                } while (!isValidInput(choice,VALID_COMMANDS,VALID_COMMANDS.length));                
                try {
                    switch (choice) {
                    case "1":
                    case "insert": 
                        String prompt;
                        if (obj.getHeapSize()==0) prompt="\nEnter the value of root = ";
                        else prompt= "\nEnter the value of node : ";
                        int value=getIntInput(prompt);
                        obj.insert(value);
                        System.out.print("\nnew heap : ");
                        for(int i=0;i<obj.getHeapSize();i++){
                            System.out.print(obj.getHeap()[i]+"  ");
                        }                        
                        break;

                    case "2":
                    case "delete":
                        int rootRemoved =obj.remove();
                        System.out.println("\nroot reomoved = "+rootRemoved);
                        break;

                    case "3":
                    case "peek":
                        int rootPeek=obj.peek();
                        System.out.println("\nroot = "+rootPeek);
                        break;

                    case "4":
                    case "view":
                        view(obj);
                        break;

                    case "5":
                    case "toggle":
                        obj.toggleHeapType();
                        if(obj.getIsMaxHeap()){
                            System.out.println("\n\ttoggled to max heap.");
                        }else{
                            System.out.println("\n\ttoggled to min heap.");
                        }
                        break;

                    case "6":
                    case "exit":
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
            }while (!exiting);
    }

    public static void view(Heap obj){
        if (obj.getHeapSize()==0) throw new NoSuchElementException();

        String tree_choice=getInput("\nenter l for level view or p for pretty view => ");
        while (!tree_choice.equalsIgnoreCase("l") && !tree_choice.equalsIgnoreCase("p")) {
            tree_choice=getInput("Please enter either \"L\" or \"W\" !");
            
        }
        if(tree_choice.equalsIgnoreCase("l")){
            viewLevel(obj);
        }else{
            prettyView(obj);
        }
    }
    
    //Displays the heap  level vise format.

    public static void viewLevel(Heap obj){
        if (obj.getHeapSize()==0) throw new NoSuchElementException();

        int index=0;
        int level=0;
        int elementInLevel=1;

        System.out.println("\nHeap Tree View (Level by Level):");
        while (index<obj.getHeapSize()) {
            System.out.print("Level "+level+":");
            int count=0;
            while (count<elementInLevel && index<obj.getHeapSize()) {
                System.out.print(obj.getHeap()[index]+" ");
                index++;
                count++;
            }
            System.out.println();
            level++;
            elementInLevel*=2;
        }
    }
    
    //Displays the heap in a visually appealing tree format.
     
    public static void prettyView(Heap obj) {
        if (obj.getHeapSize() == 0) throw new NoSuchElementException();

        int maxLevel = (int) (Math.log(obj.getHeapSize()) / Math.log(2));
        prettyViewRecursive(obj,0, 0, maxLevel);
    }

    public static void prettyViewRecursive(Heap obj,int index, int level, int maxLevel) {
        if (obj.getHeapSize()==0) throw new NoSuchElementException();

        if (index >= obj.getHeapSize()) {
            return;
        }
        // Print right child first
        prettyViewRecursive(obj,2 * index + 2, level + 1, maxLevel);

        // Print current node
        for (int i = 0; i < level; i++) {
            System.out.print("    ");
        }
        System.out.println("-> " + obj.getHeap()[index]);

        // Print left child
        prettyViewRecursive(obj,2 * index + 1, level + 1, maxLevel);
    }
    // mainScanner.close();
}
