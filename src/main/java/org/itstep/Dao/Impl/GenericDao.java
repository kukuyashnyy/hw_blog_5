package org.itstep.Dao.Impl;

import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T, ID> {
    ID save(T data) throws SQLException;
    List<T> findAll();
    T findById(ID id);
    void delete(ID id);
    T update(T data) throws SQLException;
}
