package com.nir.coupons.entity;


import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "username", unique = true, nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    @Size(min = 4, max = 45)
    private String password;

    @Column(name = "user_type", nullable = false)
    private String userType;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "isVerify", nullable = false)
    public boolean isVerify=false;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @OneToOne(mappedBy = "userId", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private VerificationTokenEntity verificationTokenEntity;


    public UserEntity() {
    }

    public UserEntity(int id, String userName, String password, String userType, Integer companyId) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
        this.companyId = companyId;
        this.isVerify = false;
    }

    public UserEntity(String userName, String password, String userType, Integer companyId) {
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public @Size(min = 4, max = 45) String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 4, max = 45) String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public boolean isVerify() {
        return isVerify;
    }

    public void setVerify(boolean verify) {
        isVerify = verify;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public VerificationTokenEntity getVerificationTokenEntity() {
        return verificationTokenEntity;
    }

    public void setVerificationTokenEntity(VerificationTokenEntity verificationTokenEntity) {
        this.verificationTokenEntity = verificationTokenEntity;
    }
}
