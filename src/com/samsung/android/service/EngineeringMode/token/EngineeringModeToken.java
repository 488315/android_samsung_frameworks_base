package com.samsung.android.service.EngineeringMode.token;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public class EngineeringModeToken {
    private static EngineeringModeToken mEngineeringModeToken;
    private int mOTPtime;
    private String mPrefix;
    private String mType;
    private String mVersion;
    private CommonItemCollection mTokenInfo = null;
    private CommonItemCollection mDeviceInfo = null;
    private CommonItemCollection mIssuerInfo = null;
    private CommonItemCollection mModeInfo = null;
    private CommonItemCollection mValidityInfo = null;
    private ModeItemCollection mModeDB = null;
    private GroupItemCollection mGroupDB = null;
    private CommonItemCollection mIntegrityInfo = null;

    public String getPrefix() {
        return this.mPrefix;
    }

    public String getType() {
        return this.mType;
    }

    public String getVersion() {
        return this.mVersion;
    }

    public void setPrefix(String str) {
        this.mPrefix = str;
    }

    public void setType(String str) {
        this.mType = str;
    }

    public void setVersion(String str) {
        this.mVersion = str;
    }

    public int getOTPTime() {
        return this.mOTPtime;
    }

    public CommonItemCollection getTokenInfo() {
        return this.mTokenInfo;
    }

    public CommonItemCollection getDeviceInfo() {
        return this.mDeviceInfo;
    }

    public CommonItemCollection getIssuerInfo() {
        return this.mIssuerInfo;
    }

    public CommonItemCollection getModeInfo() {
        return this.mModeInfo;
    }

    public CommonItemCollection getValidityInfo() {
        return this.mValidityInfo;
    }

    public ModeItemCollection getModeDB() {
        return this.mModeDB;
    }

    public GroupItemCollection getGroupDB() {
        return this.mGroupDB;
    }

    public CommonItemCollection getIntegrityInfo() {
        return this.mIntegrityInfo;
    }

    public void pushTokenInfo(int i, int i2, byte[] bArr) {
        if (this.mTokenInfo == null) {
            this.mTokenInfo = new CommonItemCollection("TOKE", new ArrayList());
        }
        this.mTokenInfo.addCommonItem(i, i2, bArr);
    }

    public void pushDeviceInfo(int i, int i2, byte[] bArr) {
        if (this.mDeviceInfo == null) {
            this.mDeviceInfo = new CommonItemCollection("DEVI", new ArrayList());
        }
        this.mDeviceInfo.addCommonItem(i, i2, bArr);
    }

    public void pushIssuerInfo(int i, int i2, byte[] bArr) {
        if (this.mIssuerInfo == null) {
            this.mIssuerInfo = new CommonItemCollection("ISSU", new ArrayList());
        }
        this.mIssuerInfo.addCommonItem(i, i2, bArr);
    }

    public void pushModeInfo(int i, int i2, byte[] bArr) {
        if (this.mModeInfo == null) {
            this.mModeInfo = new CommonItemCollection("MODE", new ArrayList());
        }
        this.mModeInfo.addCommonItem(i, i2, bArr);
    }

    public void pushValidityInfo(int i, int i2, byte[] bArr) {
        if (this.mValidityInfo == null) {
            this.mValidityInfo = new CommonItemCollection("VALI", new ArrayList());
        }
        this.mValidityInfo.addCommonItem(i, i2, bArr);
    }

    public void pushModeDB(int i, String str, String str2, int i2) {
        if (this.mModeDB == null) {
            this.mModeDB = new ModeItemCollection("MODB", new ArrayList());
        }
        this.mModeDB.addModeItemCollection(i, str, str2, i2);
    }

    public void pushAttrToModeItem(int i, int i2, int i3, byte[] bArr) {
        ModeItemCollection modeItemCollection = this.mModeDB;
        if (modeItemCollection != null) {
            modeItemCollection.addAttrToModeItem(i, i2, i3, bArr);
        }
    }

    public void pushGroupDB(int i, String str, String str2) {
        if (this.mGroupDB == null) {
            this.mGroupDB = new GroupItemCollection("GRDB", new ArrayList());
        }
        this.mGroupDB.addGroupItemCollection(i, str, str2);
    }

    public void pushAttrToGroupItem(int i, int i2, int i3, byte[] bArr) {
        GroupItemCollection groupItemCollection = this.mGroupDB;
        if (groupItemCollection != null) {
            groupItemCollection.addAttrToGroupItem(i, i2, i3, bArr);
        }
    }

    public void pushIntegrityInfo(int i, int i2, byte[] bArr) {
        if (this.mIntegrityInfo == null) {
            this.mIntegrityInfo = new CommonItemCollection("INTE", new ArrayList());
        }
        this.mIntegrityInfo.addCommonItem(i, i2, bArr);
    }

    public void pushOTPTime(int i) {
        this.mOTPtime = i;
    }
}
