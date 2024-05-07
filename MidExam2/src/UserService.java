import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class UserService {
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

    public ArrayList<User> getUsers() {
        return users;
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
}
