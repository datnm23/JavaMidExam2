import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class UserService {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String REGEX_EMAIL_PATTERN = "^[a-zA-Z][\\\\w-]+@([\\\\w]+\\\\.[\\\\w]+|[\\\\w]+\\\\.[\\\\w]{2,}\\\\.[\\\\w]{2,})$";
    private static final String REGEX_PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    private ArrayList<User> users;

    public UserService() {
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader("users.json");
            Type type = new TypeToken<ArrayList<User>>() {
            }.getType();
            users = gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void SyncFile() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            File f = new File("users.json");
            System.out.println(f.getAbsolutePath());
            FileReader writer = new FileReader("users.json");
            gson.toJson(users);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User checkUser(String email, String password) {
        System.out.println(email);
        System.out.println(password);
        for (User user : users) {
            System.out.println(user.getUserEmail());
            System.out.println(user.getUserPassword());
            if (user.getUserPassword().equals(password) && user.getUserEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    public boolean checkUsername(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkEmail(String email) {
        for (User user : users) {
            if (user.getUserEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public User login() throws IOException {
        Menu menu = new Menu();
        User userLogin = null;

        while (userLogin == null) {
            System.out.println("Email đăng nhập: ");
            String email = SCANNER.nextLine();
            System.out.println("Mật khẩu: ");
            String password = SCANNER.nextLine();
            userLogin = checkUser(email, password);
            if (userLogin != null) {
                System.out.println("Đăng nhập thành công!");
            } else {
                System.out.println("Tài khoản hoặc mật khẩu không chính xác");
            }
        }

        menu.displayMenuLogin(userLogin);
        return userLogin;
    }

    public void changeUserName(User userLogin) {
        boolean checkUsername = false;
        while (!checkUsername) {
            System.out.println("Nhập Username mới");
            String newUserName = SCANNER.nextLine();
            if (checkUsername(newUserName)) {
                System.out.println("Username đã tồn tại ! Vui lòng chọn Username khác : ");
            } else {
                checkUsername = true;
                userLogin.setUserName(newUserName);
            }
        }
        System.out.println("Thay đổi Username thành công ! UserName mới của bạn là :" + userLogin.getUserName());

        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    public void changeUserEmail(User userLogin) {
        boolean checkEmail = false;
        while (!checkEmail) {
            System.out.println("Nhập Email mới");
            String newUserEmail = SCANNER.nextLine();
            if (checkEmail(newUserEmail)) {
                System.out.println("Email đã tồn tại ! Nhập email khác:");
            } else if (Pattern.matches(REGEX_EMAIL_PATTERN, newUserEmail)) {
                System.out.println("Định dạng email không đúng!");
            } else {
                checkEmail = true;
                userLogin.setUserEmail(newUserEmail);
            }
        }
        System.out.println("Thay đổi Email thành công ! Email mới của bạn là :" + userLogin.getUserEmail());
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
        System.out.println("Thay đổi Password thành công ! Password mới của bạn là :" + userLogin.getUserPassword());
    }

    public void register() throws IOException {
        System.out.println("Mời bạn nhập Username mới : ");
        User newUser = new User();
        newUser.setUserName(SCANNER.nextLine());
        Menu menu = new Menu();
        boolean checkInvalidEmail = true;

        while (checkInvalidEmail) {
            checkInvalidEmail = false;
            for (User user : users) {
                if (user.getUserName().equalsIgnoreCase(newUser.getUserName())) {
                    System.out.println("Username đã tồn tại ! Vui lòng chọn Username khác : ");
                    newUser.setUserName(SCANNER.nextLine());
                    checkInvalidEmail = true;
                    break;
                }
            }
        }

        System.out.println("Mời bạn nhập Email mới : ");
        newUser.setUserEmail(SCANNER.nextLine());
        boolean notAccept = true;
        while (notAccept) {
            notAccept = false;
            for (User user : users) {
                if (user.getUserEmail().equalsIgnoreCase(newUser.getUserEmail())) {
                    System.out.println("Email đã tồn tại ! Vui lòng chọn Email khác : ");
                    newUser.setUserEmail(SCANNER.nextLine());
                    notAccept = true;
                    break;
                }
            }
        }

        notAccept = true;
        while (notAccept) {
            notAccept = false;
            if (!newUser.getUserEmail().matches(REGEX_EMAIL_PATTERN)) {
                System.out.println("Email không đúng định dạng !");
                newUser.setUserEmail(SCANNER.nextLine());
                System.out.println("Email của bạn" + newUser.getUserEmail());
                notAccept = true;
            }
        }

        System.out.println("Nhập mật khẩu: ");
        newUser.setUserPassword(SCANNER.nextLine());
        notAccept = true;
        while (notAccept) {
            notAccept = false;
            if (!newUser.getUserPassword().matches(REGEX_PASSWORD_PATTERN)) {
                System.out.println("Mật khẩu không hợp lệ: ");
                newUser.setUserPassword(SCANNER.nextLine());
                notAccept = true;
            }
        }
        System.out.println("Chúc mừng ! Bạn đã tạo tài khoản thành công. Xin mời đăng nhập:");
        users.add(newUser);
        menu.displayMenuHome();
    }


    public void forgotPassword() throws IOException {
        System.out.println("\n********* ĐỔI MẬT KHẨU *********");
        boolean checkInvalidEmail = false;
        while (!checkInvalidEmail) {
            System.out.println("Nhập địa chỉ mail của bạn: ");
            String email = SCANNER.nextLine();
            checkInvalidEmail = true;
            if (checkEmail(email)) {
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
}
