package com.example.musicserver.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * <p>
 * 
 * </p>
 *
 * @author Xiangly Peng
 * @since 2022-12-30
 */
@Data
@TableName(value = "admin")
public class Admin extends Model<Admin> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String password;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
