package service;

import model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class UserService {

    private static UserService instance;

    private UserService() {}

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    /*private static volatile UserService instance;
    public static UserService getInstance() {
        UserService localInstance = instance;
        if (localInstance == null) {
            synchronized (UserService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new UserService();
                }
            }
        }
        return localInstance;
    }*/

    /* хранилище данных */
    private Map<Long, User> dataBase = Collections.synchronizedMap(new HashMap<>());
    /* счетчик id */
    private AtomicLong maxId = new AtomicLong(0);
    //    private LongAdder maxId = new LongAdder(); ????
    /* список авторизованных пользователей */
    private Map<Long, User> authMap = Collections.synchronizedMap(new HashMap<>());


    public List<User> getAllUsers() {
        return new ArrayList<>(dataBase.values());
    }

    public User getUserById(Long id) {
        return dataBase.get(id);
    }

    public boolean addUser(User user) {

        if (isExistsThisUser(user)) {
            return false;
        } else {
            /*user.setId(maxId.incrementAndGet());
            dataBase.put(user.getId(), user);*/

            long id = maxId.incrementAndGet();
            User userWithId = new User(id, user.getEmail(), user.getPassword());
            dataBase.put(id, userWithId);
            return true;
        }
    }

    public void deleteAllUser() {
        dataBase.clear();
    }

    public boolean isExistsThisUser(User user) {
        return dataBase.containsValue(user);
       /* User user1 = getAllUsers().stream().filter(u -> u.getEmail().equals(user.getEmail()) && u.getPassword().equals(user.getPassword())).findFirst().orElse(null);
        return user1 != null;*/
    }

    public List<User> getAllAuth() {
        return new ArrayList<>(authMap.values());
    }

    /*public boolean authUser(User user) {
        return authMap.containsValue(user);
    }*/

    public boolean authUser(User user) {
        if (user != null && user.getId() != null && isExistsThisUser(user)) {
            authMap.put(user.getId(), user);
            return true;
        } else {
            return false;
        }
    }

    public void logoutAllUsers() {
        authMap.clear();
    }

    public boolean isUserAuthById(Long id) {
        return authMap.containsKey(id);
    }

}
