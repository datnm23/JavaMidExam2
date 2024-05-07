
import java.io.IOException;
import java.util.Scanner;

public class Menu {
    private static final Scanner SCANNER = new Scanner(System.in);

    public void displayMenuHome() throws IOException {
        UserService userService = new UserService();
        System.out.println("1. Đăng nhập");
        System.out.println("2. Đăng ký");
        System.out.println("3. Quên mật khẩu");
        System.out.println("Please choose : ");
        int choice = Integer.parseInt(SCANNER.nextLine());
        switch (choice) {
            case 1:
                System.out.println("Đã chọn 1:");

                userService.login();
                break;
            case 2:
                System.out.println("Đã chọn 2:");
                userService.register();
                break;
            case 3:
                System.out.println("Đã chọn 3:");
                userService.forgotPassword();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    public void displayMenuLogin(User userLogin) throws IOException {
        System.out.println("Chào mừng " + (userLogin.getUserName() + ", bạn có thể thực hiện các công việc sau:"));
        System.out.println("1. Thay đổi username");
        System.out.println("2. Thay đổi email");
        System.out.println("3. Thay đổi mật khẩu");
        System.out.println("4. Đăng xuất");
        System.out.println("5. Thoát chương trình");
        System.out.println("Please chooose : ");

        int choice2 = Integer.parseInt(SCANNER.nextLine());
        UserService userService = new UserService();
        switch (choice2) {

            case 1:
                System.out.println("Da chon 1 : ");
                userService.changeUserName(userLogin);
                break;
            case 2:
                System.out.println("Da chon 2 : ");
                userService.changeUserEmail(userLogin);
                break;
            case 3:
                System.out.println("Da chon 3 : ");
                userService.changeUserPassword(userLogin);
                break;
            case 4:
                Menu menu = new Menu();
                menu.displayMenuHome();
                break;
            case 5:
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

}

