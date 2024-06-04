import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDatabase {
    private static final String FILE_NAME = "users.txt";
    private Map<String, User> users;

    public UserDatabase() {
        users = new HashMap<>();
        loadUsers();
    }

    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            users = reader.lines()
                    .map(line -> line.split("/"))
                    .filter(parts -> parts.length == 3)
                    .collect(Collectors.toMap(
                            parts -> parts[0],
                            parts -> new User(parts[0], parts[1], Integer.parseInt(parts[2]))
                    ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            users.values().forEach(user ->
                    writer.println(user.getUsername() + "/" + user.getPassword() + "/" + user.getLevel())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean login(String username, String password) {
        return users.containsKey(username) && users.get(username).getPassword().equals(password);
    }

    public boolean signUp(String username, String password) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username, password, 1));
            saveUsers();
            return true;
        }
        return false;
    }

    public int getLevel(String username) {
        if (users.containsKey(username)) {
            return users.get(username).getLevel();
        }
        return 1;
    }

    public boolean levelUp(String username, String password, int level) {
        if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
            users.get(username).setLevel(level);
            saveUsers();
            return true;
        }
        return false;
    }
}
