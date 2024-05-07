
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String REGEX_EMAIL_PATTERN = "^[a-zA-Z][\\\\w-]+@([\\\\w]+\\\\.[\\\\w]+|[\\\\w]+\\\\.[\\\\w]{2,}\\\\.[\\\\w]{2,})$";
    private static final String REGEX_PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,15}$";
    private static final UserService USERSERVICE = new UserService();

    public void displayMenuHome() throws IOException {
        System.out.println("1. Đăng nhập");
        System.out.println("2. Đăng ký");
        System.out.println("3. Quên mật khẩu");
        System.out.println("Please choose : ");
        int choice = Integer.parseInt(SCANNER.nextLine());
        switch (choice) {
            case 1:
                System.out.println("Đã chọn 1:");
                login();
                break;
            case 2:
                System.out.println("Đã chọn 2:");
                register();
                break;
            case 3:
                System.out.println("Đã chọn 3:");
                forgotPassword();
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
        switch (choice2) {

            case 1:
                System.out.println("Da chon 1 : ");
                changeUserName(userLogin);
                System.out.println("Thay đổi Username thành công ! UserName mới của bạn là :" + userLogin.getUserName());
                break;
            case 2:
                System.out.println("Da chon 2 : ");
                changeUserEmail(userLogin);
                System.out.println("Thay đổi Email thành công ! Email mới của bạn là :" + userLogin.getUserEmail());
                break;
            case 3:
                System.out.println("Da chon 3 : ");
                changeUserPassword(userLogin);
                System.out.println("Thay đổi Password thành công ! Password mới của bạn là :" + userLogin.getUserPassword());
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

    public void login() throws IOException {
        Menu menu = new Menu();
        User userLogin = null;

        while (userLogin == null) {
            System.out.println("Email đăng nhập: ");
            String email = SCANNER.nextLine();
            System.out.println("Mật khẩu: ");
            String password = SCANNER.nextLine();
            userLogin = USERSERVICE.checkUser(email, password);
            if (userLogin != null) {
                System.out.println("Đăng nhập thành công!");
            } else {
                System.out.println("Tài khoản hoặc mật khẩu không chính xác");
            }
        }
        menu.displayMenuLogin(userLogin);
    }

    public void register() throws IOException {
        ArrayList<User> users = USERSERVICE.getUsers();
        Menu menu = new Menu();
        User newUser = new User();
        changeUserName(newUser);
        changeUserEmail(newUser);
        changeUserPassword(newUser);
        System.out.println("Chúc mừng ! Bạn đã tạo tài khoản thành công. Xin mời đăng nhập:");
        users.add(newUser);
        menu.displayMenuHome();
    }

    public void forgotPassword() throws IOException {
        System.out.println("\n********* ĐỔI MẬT KHẨU *********");
        ArrayList<User> users = USERSERVICE.getUsers();
        boolean checkInvalidEmail = false;
        while (!checkInvalidEmail) {
            System.out.println("Nhập địa chỉ mail của bạn: ");
            String email = SCANNER.nextLine();
            checkInvalidEmail = true;
            if (USERSERVICE.checkEmail(email)) {
                for (User user : users) {
                    if (user.getUserEmail().equalsIgnoreCase(email)) {
                        changeUserPassword(user);
                        Menu menu = new Menu();
                        menu.displayMenuHome();
                        break;
                    }
                }
            } else {
                checkInvalidEmail = false;
                System.out.println("Email không tồn tại !");
            }
        }
    }

    public void changeUserName(User userLogin) {
            boolean checkUsername = false;
            while (!checkUsername) {
                System.out.println("Nhập Username mới");
                String newUserName = SCANNER.nextLine();
                if (USERSERVICE.checkUsername(newUserName)) {
                    System.out.println("Username đã tồn tại ! Vui lòng chọn Username khác : ");
                } else {
                    checkUsername = true;
                    userLogin.setUserName(newUserName);
                }
            }
    }
    public void changeUserEmail(User userLogin) {
        boolean checkEmail = false;
        while (!checkEmail) {
            System.out.println("Nhập Email mới");
            String newUserEmail = SCANNER.nextLine();
            if (USERSERVICE.checkEmail(newUserEmail)) {
                System.out.println("Email đã tồn tại ! Nhập email khác:");
            } else if (Pattern.matches(REGEX_EMAIL_PATTERN, newUserEmail)) {
                System.out.println("Định dạng email không đúng!");
            } else {
                checkEmail = true;
                userLogin.setUserEmail(newUserEmail);
            }
        }
    }

    public void changeUserPassword(User userLogin) {
        boolean checkPassword = false;
        System.out.println("Password must contain at least one digit [0-9].at least one lowercase Latin character [a-z].at least one uppercase Latin character [A-Z].at least one special character.a length of at least 8 characters and a maximum of 20 characters ");
        while (!checkPassword) {
            System.out.println("Nhập Password mới");
            String newUserPassword = SCANNER.nextLine();
            if (Pattern.matches(REGEX_PASSWORD_PATTERN, newUserPassword)) {
                System.out.println("Định dạng password không đúng!");
            } else {
                checkPassword = true;
                userLogin.setUserPassword(newUserPassword);
            }
        }
    }
}

