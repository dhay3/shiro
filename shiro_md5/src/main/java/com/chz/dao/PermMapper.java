package com.chz.dao;

import com.chz.entity.Perm;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermMapper {
    //然后通过传过来的r_id拿到对应的perm, 多个 perm用in
    @Select("select * from t_perm where p_id in (select p_id from t_r_p where r_id = #{r_id})")
    List<Perm> queryPerms(@Param("r_id") Integer rId);
}
