package com.dl.demo.domain.repository;

import com.dl.demo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long>
{
}
