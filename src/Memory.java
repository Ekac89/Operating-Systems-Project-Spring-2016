import java.util.Random;

/**
 * Created by Chris on 4/26/2017.
 */
public class Memory {

        public static void main(String[] args) {
            int[] memory = new int[16];
            int i = 0;
            int totalcount = 0;
            int Size;
            int FreeMem = 16;
            Random randomGenerator = new Random();
            int id = 0;
            int tester = 100;
            PrintProcessFromMem(memory);
            for (int test = 0; test < tester; test++) {
                id ++;
                int adddelete = (randomGenerator.nextInt(2));
                if (adddelete == 1) {
                    Size = (randomGenerator.nextInt(7) + 1);
                    //System.out.print("size = " + Size + "id = " + id + " ");
                    AddProcessFromMem(Size, memory, id);
                }
                if (adddelete == 0) {
                    int remove = (randomGenerator.nextInt(tester)+1);
                    //System.out.print("remove = " + remove + " ");
                    RemoveProcessFromMem(remove, memory);
                }
                PrintProcessFromMem(memory);
            }
        }
        //add
        public static int[]  AddProcessFromMem(int Size, int[] memory, int id) {

            // System.out.print("AddProcessFromMem");

            int MemCount = 0;
            if (Size <= 15) {
                for (int c = 0; c <= 15; c++) {
                    if (memory[c] == 0) {
                        MemCount++;
                        // System.out.println(" MemCount = " + MemCount + " c =  " + c);
                        if (MemCount == Size) {
                            //  System.out.println("here " + MemCount);
                            for (int i = 0; i < MemCount; i++) {

                                // System.out.println("here ==== " + memory[c - i] + " ---- " + id);
                                memory[c - i] = id;
                            }
                            c = 17;
                            //System.out.println("out");
                        }

                    }else if (memory[c] != 0) {
                        MemCount = 0;
                        //System.out.println(" MemCount = " + MemCount + " c =  " + c);
                    }
                }
            }
            return (memory);
        }
        //remove
        public static int[] RemoveProcessFromMem(int remove, int[] memory) {

            // System.out.print("RemoveProcessFromMem");
            for (int c = 0; c <= 15; c++){
                if (memory[c] == remove) {
                    memory[c] = 0;
                }
                if (memory[c] == 0) {
                    //FreeMem++;
                }
            }
            return (memory);
        }
        //print
        public static int[] PrintProcessFromMem(int[] memory) {
            for (int c = 0; c <= 15; ++c) {
                System.out.print(memory[c] + " | ");
            }
            System.out.println();
            return (memory);
        }
    }



