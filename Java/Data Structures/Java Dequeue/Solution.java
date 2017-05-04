import java.util.*;
public class test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Deque<Integer> deque = new ArrayDeque<Integer>();
        Map<Integer,Integer> mapUnique = new HashMap<Integer,Integer>();
        int n = in.nextInt();
        int m = in.nextInt();

        int maxUnique = 0, tmpUnique = 0;
        for (int i=0; i<n; i++) {
            int num = in.nextInt();
            deque.add(num);
            if (null == mapUnique.get(num)) mapUnique.put(num, 0);
            mapUnique.put(num, mapUnique.get(num)+1);
            if (mapUnique.get(num) == 1)  tmpUnique++;
            if (deque.size() == m+1) {
                int removedNum = deque.remove();
                int cntRemovedNum = mapUnique.get(removedNum);
                mapUnique.put(removedNum, --cntRemovedNum);
                if (cntRemovedNum == 0)  tmpUnique--;
            }
            if (deque.size() == m && tmpUnique > maxUnique) maxUnique = tmpUnique;
        }

        System.out.println(maxUnique);
        in.close();
    }
}
