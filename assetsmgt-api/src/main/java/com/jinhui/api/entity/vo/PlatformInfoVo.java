package com.jinhui.api.entity.vo;

import java.util.List;

/**
 * Created by Administrator on 2018/4/24 0024.
 */
public class PlatformInfoVo {


    private String platformId;

    private String platformName;

    private List<Account> accounts;

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }



    public List getAccounts() {
        return accounts;
    }

    public void setAccounts(List accounts) {
        this.accounts = accounts;
    }

    public class Account {
        private String accountName;

        private String accountAddress;

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getAccountAddress() {
            return accountAddress;
        }

        public void setAccountAddress(String accountAddress) {
            this.accountAddress = accountAddress;
        }
    }


}
