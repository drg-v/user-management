package user.services;

import java.util.List;
import org.springframework.security.core.Authentication;
import user.entities.User;

public interface UserService {

    public List<User> getAll();
    
    public void save(User user);
    
    public void delete(List<Integer> ids);
    
    public void block(List<Integer> ids);
    
    public void unlock(List<Integer> ids);
    
    public Boolean checkAuthentication(Authentication authentiction, List<Integer> ids);
}
