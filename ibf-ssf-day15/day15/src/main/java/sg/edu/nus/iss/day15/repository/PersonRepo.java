package sg.edu.nus.iss.day15.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day15.utils.Util;

@Repository
public class PersonRepo {

    @Autowired
    @Qualifier(Util.REDIS_ONE) // Means telling this method to use the bean that is named "Util.REDIS_ONE"
    RedisTemplate<String, String> template;

    // create - Add item to the list, if no list, it will create a new list
    @SuppressWarnings("null")
    public Long addToList(String key, String value) {
        return template.opsForList().rightPush(key, value);
    }

    // read (all)
    @SuppressWarnings("null")
    public List<String> getList(String key) {
        return template.opsForList().range(key, 0, -1);
    }

    // update
    @SuppressWarnings("null")
    public boolean updatePerson(String key, long index, String newValue) {
        boolean isUpdated = false;
        ListOperations<String, String> listOps = template.opsForList();
    
        // Check if the index is valid
        if (index >= 0 && index < listOps.size(key)) {
            listOps.set(key, index, newValue); // Set the new value at the specified index
            isUpdated = true;
        }
        return isUpdated;
    }

    // delete (single)
    @SuppressWarnings("null")
    public Boolean deletePerson(String key, String value) {
        Boolean isDeleted = false;
        ListOperations<String, String> listOps = template.opsForList();

        Long foundIndex = template.opsForList().indexOf(key, value);
 
        if (foundIndex >= 0) {
            listOps.remove(key, 1, value);
            isDeleted = true;
        }
        return isDeleted;
    }

    // delete (all)
    @SuppressWarnings("null")
    public boolean deleteAll(String key) {
        boolean isAllDeleted = false;
        ListOperations<String, String> listOps = template.opsForList();
        
        // Get the size of the list
        long listSize = listOps.size(key);
        
        // If the list exists and has elements, delete it
        if (listSize > 0) {
            template.delete(key);
            isAllDeleted = true;
        }
        return isAllDeleted;
    }
}
