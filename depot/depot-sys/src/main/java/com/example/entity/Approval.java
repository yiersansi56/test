package com.example.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author hyz02
 * @since 2022-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Approval对象", description="")
public class Approval implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using= ToStringSerializer.class)
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @JsonSerialize(using= ToStringSerializer.class)
    @ApiModelProperty(value = "商品id")
    private Long goods;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "管理员id")
    private Integer adminId;

    @ApiModelProperty(value = "审批数量")
    private Integer count;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createtime;

    @ApiModelProperty(value = "申请理由")
    private String apply;

    @ApiModelProperty(value = "回复")
    private String reply;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @TableField(exist = false)
    private String radio;

    private Integer isValid;


}
