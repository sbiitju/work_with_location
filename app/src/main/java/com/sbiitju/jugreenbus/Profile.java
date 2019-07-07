package com.sbiitju.jugreenbus;

public class Profile {
    String name,dep,mobile,batch;

    public Profile(String name, String dep, String mobile, String batch) {
        this.name = name;
        this.dep = dep;
        this.mobile = mobile;
        this.batch = batch;
    }

    public Profile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
