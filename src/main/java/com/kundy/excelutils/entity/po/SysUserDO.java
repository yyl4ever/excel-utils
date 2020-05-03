package com.kundy.excelutils.entity.po;

import com.kundy.excelutils.annotation.Excel;
import com.kundy.excelutils.annotation.Excels;

import java.util.Date;

/**
 * @Author: yangyl
 * @Date: 2020-05-03 23:10
 * @Description:
 */
public class SysUserDO {
    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Long userId;

    /**
     * 部门ID
     */
    @Excel(name = "部门ID")
    private Long deptId;

    /**
     * 登录账号
     */
    @Excel(name = "登录账号")
    private String loginName;

    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称")
    private String userName;

    /**
     * 用户类型（00系统用户）
     */
    @Excel(name = "用户类型（00系统用户）")
    private String userType;

    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    private String mobile;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @Excel(name = "用户性别（0男 1女 2未知）", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐加密
     */
    private String salt;

    /**
     * 最后登陆IP
     */
    private String loginIp;

    /**
     * 最后登陆时间
     */
    private Date loginDate;

    /**
     * 数据状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    private Date createDate;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 部门对象 -- import 时支持传入一个字段
     */
    @Excel(name = "导入部门ID", targetAttr = "deptId")
    private SysDeptDO importDept;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public SysDeptDO getImportDept() {
        return importDept;
    }

    public void setImportDept(SysDeptDO importDept) {
        this.importDept = importDept;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SysUserDO{");
        sb.append("userId=").append(userId);
        sb.append(", deptId=").append(deptId);
        sb.append(", loginName='").append(loginName).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", userType='").append(userType).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", avatar='").append(avatar).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", salt='").append(salt).append('\'');
        sb.append(", loginIp='").append(loginIp).append('\'');
        sb.append(", loginDate=").append(loginDate);
        sb.append(", status=").append(status);
        sb.append(", createDate=").append(createDate);
        sb.append(", createBy='").append(createBy).append('\'');
        sb.append(", updateDate=").append(updateDate);
        sb.append(", updateBy='").append(updateBy).append('\'');
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", importDept=").append(importDept);
        sb.append('}');
        return sb.toString();
    }

}