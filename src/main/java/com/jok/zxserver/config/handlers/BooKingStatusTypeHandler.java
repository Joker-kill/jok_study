package com.jok.zxserver.config.handlers;

import com.jok.zxserver.domain.common.enums.BooKingStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author JOKER
 * create time 2024/8/13 13:32
 */
public class BooKingStatusTypeHandler extends BaseTypeHandler<BooKingStatus> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BooKingStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getStatus());
    }

    @Override
    public BooKingStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int status = rs.getInt(columnName);
        return BooKingStatus.getEnumByStatus(status);
    }

    @Override
    public BooKingStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int status = rs.getInt(columnIndex);
        return BooKingStatus.getEnumByStatus(status);
    }

    @Override
    public BooKingStatus getNullableResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
        int status = cs.getInt(columnIndex);
        return BooKingStatus.getEnumByStatus(status);
    }
}
