public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("\uD83D\uDDC1");
        new MainFrame("RedInsomnia", MainFrame.LIGHT_THEME);
    }

    public static void startAgain(int theme) {
        new MainFrame("RedInsomnia", theme);
    }
}
