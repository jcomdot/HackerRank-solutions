import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int M = in.nextInt();
        BitSet B1 = new BitSet(N);
        BitSet B2 = new BitSet(N);
        
        for (int i=0; i<M; i++) {
            String operator = in.next();
            int operand1 = in.nextInt();
            int operand2 = in.nextInt();
            BitSet b1, b2;
            if (operand1 == 1) b1 = B1;
            else b1 = B2;
            if (operator.equals("AND") || operator.equals("OR") || operator.equals("XOR")) {
                if (operand2 == 2) b2 = B2;
                else b2 = B1;
                if (operator.equals("AND")) b1.and(b2);
                else if(operator.equals("OR")) b1.or(b2);
                else if(operator.equals("XOR")) b1.xor(b2);
            } else {
                if (operator.equals("SET")) b1.set(operand2);
                else if (operator.equals("FLIP")) b1.flip(operand2);
            }
            
            System.out.println(String.format("%d %d", B1.cardinality(), B2.cardinality()));
        }
    }
}
