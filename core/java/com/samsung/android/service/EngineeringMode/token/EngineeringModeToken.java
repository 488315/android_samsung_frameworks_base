package com.samsung.android.service.EngineeringMode.token;

import com.samsung.android.core.SizeCompatInfo;
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

    public void setPrefix(String v) {
        this.mPrefix = v;
    }

    public void setType(String v) {
        this.mType = v;
    }

    public void setVersion(String v) {
        this.mVersion = v;
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

    public void pushTokenInfo(int type, int len, byte[] content) {
        if (this.mTokenInfo == null) {
            this.mTokenInfo = new CommonItemCollection("TOKE", new ArrayList());
        }
        this.mTokenInfo.addCommonItem(type, len, content);
    }

    public void pushDeviceInfo(int type, int len, byte[] content) {
        if (this.mDeviceInfo == null) {
            this.mDeviceInfo = new CommonItemCollection("DEVI", new ArrayList());
        }
        this.mDeviceInfo.addCommonItem(type, len, content);
    }

    public void pushIssuerInfo(int type, int len, byte[] content) {
        if (this.mIssuerInfo == null) {
            this.mIssuerInfo = new CommonItemCollection("ISSU", new ArrayList());
        }
        this.mIssuerInfo.addCommonItem(type, len, content);
    }

    public void pushModeInfo(int type, int len, byte[] content) {
        if (this.mModeInfo == null) {
            this.mModeInfo = new CommonItemCollection(SizeCompatInfo.Key.MODE, new ArrayList());
        }
        this.mModeInfo.addCommonItem(type, len, content);
    }

    public void pushValidityInfo(int type, int len, byte[] content) {
        if (this.mValidityInfo == null) {
            this.mValidityInfo = new CommonItemCollection("VALI", new ArrayList());
        }
        this.mValidityInfo.addCommonItem(type, len, content);
    }

    public void pushModeDB(int modeIndex, String name, String description, int groupIndex) {
        if (this.mModeDB == null) {
            this.mModeDB = new ModeItemCollection("MODB", new ArrayList());
        }
        this.mModeDB.addModeItemCollection(modeIndex, name, description, groupIndex);
    }

    public void pushAttrToModeItem(int modeIndex, int type, int len, byte[] attribute) {
        if (this.mModeDB != null) {
            this.mModeDB.addAttrToModeItem(modeIndex, type, len, attribute);
        }
    }

    public void pushGroupDB(int groupIndex, String name, String description) {
        if (this.mGroupDB == null) {
            this.mGroupDB = new GroupItemCollection("GRDB", new ArrayList());
        }
        this.mGroupDB.addGroupItemCollection(groupIndex, name, description);
    }

    public void pushAttrToGroupItem(int groupIndex, int type, int len, byte[] attribute) {
        if (this.mGroupDB != null) {
            this.mGroupDB.addAttrToGroupItem(groupIndex, type, len, attribute);
        }
    }

    public void pushIntegrityInfo(int type, int len, byte[] content) {
        if (this.mIntegrityInfo == null) {
            this.mIntegrityInfo = new CommonItemCollection("INTE", new ArrayList());
        }
        this.mIntegrityInfo.addCommonItem(type, len, content);
    }

    public void pushOTPTime(int value) {
        this.mOTPtime = value;
    }
}
