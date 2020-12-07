package gvs.repository;

import gvs.dto.UserItem;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    public Map<Long, UserItem> userMap = new HashMap<>();

    public boolean addUser(Long userId, UserItem userItem) {
        userMap.put(userId, userItem);
        return true;
    }

    public UserItem getUser(Long userId) {
        return userMap.get(userId);
    }
}
