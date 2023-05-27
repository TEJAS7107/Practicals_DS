package Practical_8;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Event {
    int processId;
    int timestamp;

    public Event(int processId, int timestamp) {
        this.processId = processId;
        this.timestamp = timestamp;
    }
}

class LamportClock {
    int processId;
    int counter;

    public LamportClock(int processId) {
        this.processId = processId;
        this.counter = 0;
    }

    public void increment() {
        this.counter++;
    }

    public void sendEvent(List<LamportClock> clocks) {
        this.increment();
        for (LamportClock clock : clocks) {
            if (clock.processId != this.processId) {
                clock.update(this.counter);
            }
        }
    }

    public void receiveEvent(int sentValue, List<LamportClock> clocks) {
        this.counter = Math.max(this.counter, sentValue) + 1;
        for (LamportClock clock : clocks) {
            if (clock.processId != this.processId) {
                clock.update(this.counter);
            }
        }
    }

    public void update(int otherValue) {
        this.counter = Math.max(this.counter, otherValue) + 1;
    }

    public int getTimeStamp() {
        return this.counter;
    }
}

public class Main {
    public static void main(String[] args) {
        List<LamportClock> clocks = new ArrayList<>();
        clocks.add(new LamportClock(1));
        clocks.add(new LamportClock(2));
        clocks.add(new LamportClock(3));

        PriorityQueue<Event> pq = new PriorityQueue<>((e1, e2) -> {
            if (e1.timestamp == e2.timestamp) {
                return e1.processId - e2.processId;
            }
            return e1.timestamp - e2.timestamp;
        });

        pq.offer(new Event(1, 2));
        pq.offer(new Event(2, 1));
        pq.offer(new Event(3, 3));

        while (!pq.isEmpty()) {
            Event event = pq.poll();
            System.out.println("Event " + event.processId + " happened at " + event.timestamp);
            LamportClock clock = clocks.get(event.processId - 1);
            if (event.processId == 1) {
                clock.sendEvent(clocks);
            } else {
                clock.receiveEvent(event.timestamp, clocks);
            }
        }

        System.out.println("Final Lamport Clocks:");
        for (LamportClock clock : clocks) {
            System.out.println("Process " + clock.processId + " clock: " + clock.getTimeStamp());
        }
    }
}

