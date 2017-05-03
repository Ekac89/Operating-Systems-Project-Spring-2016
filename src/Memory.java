    import java.io.FileWriter;
        import java.util.Random;
        import java.io.IOException;
    /**import java.io.BufferedReader;
     * Created by Chris on 4/26/2017.
     */
    public class Memory {

        static int[] memory = new int[16];
        public static void main(String[] args) {

            int Size = 5;
            int remove = 0;
            Random randomGenerator = new Random();
            int id = 1;
            AddProcessFromMem(Size, memory, id);
            RemoveProcessFromMem(id, memory);

                PrintProcessFromMem(memory);


        }
        //add
        public static int[]  AddProcessFromMem(int Size, int[] memory, int id) {
            int MemCount = 0;
            if (Size <= 15) {
                for (int c = 0; c <= 15; c++) {
                    if (memory[c] == 0) {
                        MemCount++;
                        if (MemCount == Size) {
                            for (int i = 0; i < MemCount; i++) {
                                memory[c - i] = id;
                            }
                            c = 17;
                        }
                    }else if (memory[c] != 0) {
                        MemCount = 0;
                    }
                }
            }
            return (memory);
        }
        //remove
        public static int[] RemoveProcessFromMem(int id, int[] memory) {

            for (int c = 0; c <= 15; c++){
                if (memory[c] == id) {
                    memory[c] = 0;
                }
                if (memory[c] == 0) {
                }
            }
            return (memory);
        }
        //print
        public static int[] PrintProcessFromMem(int[] memory) {

            for (int c = 0; c <= 15; ++c) {

                System.out.println(memory[c] + " | ");
            }

            System.out.println();

            return (memory);
        }
    }



