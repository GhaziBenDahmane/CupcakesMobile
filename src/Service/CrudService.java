package Service;

import java.util.List;
import java.util.Map;

/**
 *
 * @author ding
 * @param <T>
 */
public interface CrudService<T> {

    List<T> getAll();

    T get(int id);

    void add(T a);

    void update(T a);

    void delete(T a);

    void deleteId(int a);

    T fromMap(Map a);
}
