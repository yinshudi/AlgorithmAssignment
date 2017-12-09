
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = StdIn.readInt();
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            randomizedQueue.enqueue(item);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }
}