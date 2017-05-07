import java.io.*;
import java.util.*;
import java.util.function.BiConsumer;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int M = in.nextInt();
        BitSet[] B = new BitSet[] { new BitSet(N), new BitSet(N) };
        Map<String, BiConsumer<Integer, Integer>> operator = new HashMap<>();
        operator.put("AND", (idx1, idx2) -> B[idx1-1].and(B[idx2-1]));
        operator.put("OR", (idx1, idx2) -> B[idx1-1].or(B[idx2-1]));
        operator.put("XOR", (idx1, idx2) -> B[idx1-1].xor(B[idx2-1]));
        operator.put("FLIP", (idx1, idx2) -> B[idx1-1].flip(idx2));
        operator.put("SET", (idx1, idx2) -> B[idx1-1].set(idx2));
        
        for (int i=0; i<M; i++) {
            operator.get(in.next()).accept(in.nextInt(), in.nextInt());
            System.out.println(B[0].cardinality() + " " + B[1].cardinality());
        }
        
        in.close();
    }
}
