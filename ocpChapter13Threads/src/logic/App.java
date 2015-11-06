package logic;

public class App {

    public static void main(final String[] args) {

        printHeader();
        final Scheduler scheduler = new Scheduler();
        scheduler.schedule();
    }

    private static void printHeader() {
        System.err.println("|------------------------------|");
        System.err.println("|----- Total resource: 6 ------|");
        System.err.println("|---- Initial hamburger: 5 ----|");
        System.err.println("|------------------------------|");
    }
}
