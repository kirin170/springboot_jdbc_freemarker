package com.springboot.helloworld.dao.impl;

import com.springboot.helloworld.dao.UserDao;
import com.springboot.helloworld.entity.PageEntity;
import com.springboot.helloworld.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<UserEntity> selectUser(String keyword) {
        String sql = "SELECT * FROM user WHERE CONCAT(id, title, name, wx_number) LIKE ?";
        return jdbcTemplate.query(sql, new Object[]{"%" + keyword + "%"}, new BeanPropertyRowMapper<>(UserEntity.class));
    }

    @Override
    public PageEntity selectPageUser(int start, int size, String keyword) {
    //         查询记录列表
        String sql = "SELECT * FROM user WHERE CONCAT(id, title, name, wx_number) LIKE ? LIMIT ?,?";
    //        查询总记录数
        String recordSql = "SELECT COUNT(id) FROM user WHERE CONCAT(id, title, name, wx_number) LIKE ?";

        List<UserEntity> list = jdbcTemplate.query(sql, new Object[]{"%" + keyword + "%", (start-1)*size, size}, new BeanPropertyRowMapper<>(UserEntity.class));
        int count = jdbcTemplate.queryForObject(recordSql, new Object[]{"%" + keyword + "%"}, Integer.class);
        return new PageEntity(start, size, keyword, count, list);
    }

    @Override
    public int insertUser(UserEntity user) {
        String sql = "INSERT INTO user(title, name, wx_number) VALUES(?, ?, ?)";

        return jdbcTemplate.update(sql, user.getTitle(), user.getName(), user.getWxNumber());
    }

    @Override
    public int updateUser(UserEntity user) {
        String sql = "UPDATE user SET title=?, name=?, wx_number=? WHERE id=?";

        return jdbcTemplate.update(sql, user.getTitle(), user.getName(), user.getWxNumber(), user.getId());
    }

    @Override
    public int deleteUser(int uid) {
        String sql = "DELETE FROM user WHERE id=?";
        return jdbcTemplate.update(sql, uid);
    }
}
