package task12;


import java.util.ArrayDeque;
import java.util.Queue;

public class QueueProgram {

    public static void main(String[] args) {

        Queue<Integer> queue = new ArrayDeque<>();

        queue.add(10);
        queue.add(20);
        queue.add(30);
        queue.add(40);

        System.out.println("Queue: " + queue);

        queue.remove();

        System.out.println("After removing: " + queue);

        System.out.println("First element: " + queue.peek());
    }
}