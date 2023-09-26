package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;
import java.util.stream.IntStream;

/**
 * <ol>
 * <li> Создание таблицы User(ов)
 * <li> Добавление 4 User(ов) в таблицу с данными на свой выбор.После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
 * <li> Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
 * <li> Очистка таблицы User(ов)
 * <li> Удаление таблицы
 *  </ol>
 */
public class Main {
    private static final UserServiceImpl service = new UserServiceImpl();

    public static void main(String[] args) {
        createTable();
        insertUsers();
        getAllUsers();
        clearTable();
        deleteTable();
    }

    private static void createTable() {
        service.createUsersTable();
    }

    private static void insertUsers() {
        String[] names = new String[]{"Ruslan", "Sergey", "Fedya", "Olya"};
        String[] lastNames = new String[]{"Satybaev", "Igorov", "Ivanov", "Olya"};
        byte[] ages = new byte[]{35, 30, 27, 45};

        IntStream.range(0, names.length).forEach(i -> {
            service.saveUser(names[i], lastNames[i], ages[i]);
            System.out.printf("User с именем – %s добавлен в базу данных", names[i]);
            System.out.println();
        });
    }

    private static void getAllUsers() {
        List<User> users = service.getAllUsers();
        users.forEach(System.out::println);
    }

    private static void clearTable() {
        service.cleanUsersTable();
    }

    private static void deleteTable() {
        service.dropUsersTable();
    }
}
