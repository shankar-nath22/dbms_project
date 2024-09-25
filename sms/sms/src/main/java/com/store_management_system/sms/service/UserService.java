package com.store_management_system.sms.service;

import com.store_management_system.sms.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.store_management_system.sms.exception.CustomDatabaseException;
import com.store_management_system.sms.exception.CustomServiceException;
import com.store_management_system.sms.model.User;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
    }
    public List<User> getAllAdminUsers() {
        try {
            return userRepository.findAllAdmin();
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
    }

    public List<User> getUsersWithSameStoreById(Long id) {
        try {
            return userRepository.findUsersWithSameStoreById(id);
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
        
    }

    public User getUserById(Long id) {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
    }
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    public void saveUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
    }
    public void updateUser(User user) {
        try {
            
        userRepository.save(user);
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
    }
    
    public boolean checkBelongToSameStore(Long id,User currentUser){
        try {
            Long storeId1=userRepository.getStoreIdById(id);
            Long storeId2=userRepository.getStoreIdById(currentUser.getId());
            if(storeId1!=null && storeId2!=null && storeId1.equals(storeId2)){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {

            throw new CustomServiceException(e.getMessage(), e);
        }
    }

    public void deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
    }

    public User getUserByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
    }
    public Long getCountUsers(){
        try {
            return userRepository.countUsers();
        } catch (Exception e) {
            
            throw new CustomServiceException(e.getMessage(), e);
        }
        
    }
    public Long getStoreIdById(Long id){
        try {
            return userRepository.getStoreIdById(id);
        } catch (Exception e) {
            throw new CustomServiceException(e.getMessage(), e);
        }
    }
    
}