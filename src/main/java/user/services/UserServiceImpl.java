package user.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import user.entities.User;
import user.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
    @Autowired
    private UserRepository repository;
	
    @Override
    public List<User> getAll() {
        return repository.findAll();
	}

    @Override
    public void save(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        Date now = new Date(System.currentTimeMillis());
        user.setRegistration(now);
        user.setLogin(now);
        user.setStatus("active");
        repository.save(user);
    }
    
    public void delete(List<Integer> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public void block(List<Integer> ids) {
        List<User> users = repository.findAllById(ids);
        users.stream().forEach(user -> user.setStatus("blocked"));
        repository.saveAll(users);
    }

    @Override
    public void unlock(List<Integer> ids) {
        List<User> users = repository.findAllById(ids);
        users.stream().forEach(user -> user.setStatus("active"));
        repository.saveAll(users);
    }

    @Override
    public Boolean checkAuthentication(Authentication authentication, List<Integer> ids) {
        User user = repository.findByName(authentication.getName());
        return ids.contains(user.getId());
    }
}
