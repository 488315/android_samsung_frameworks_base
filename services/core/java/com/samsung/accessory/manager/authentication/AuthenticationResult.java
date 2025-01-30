package com.samsung.accessory.manager.authentication;

import android.os.Bundle;
import android.util.Log;
import java.io.UnsupportedEncodingException;

/* loaded from: classes.dex */
public class AuthenticationResult {
    public static final String TAG = "SAccessoryManager_" + AuthenticationResult.class.getSimpleName();
    public boolean isKeyChanged;
    public byte[] mByteArrayExtraData;
    public byte[] mByteArrayManagerURI;
    public int mConnectivityType;
    public byte[] mExtraID;
    public String mRequestPkg;
    public String mStringExtraData;
    public String mStringManagerURI;
    public boolean needKeyChange;
    public String publicKey;
    public Bundle mBundle = new Bundle();
    public boolean mAuthenticationResult = false;
    public int mReason = 90;
    public int apiState = 0;
    public int isUrlExist = 0;

    public String getPublicKey() {
        return this.publicKey;
    }

    public void setPublicKey(String str) {
        this.publicKey = str;
    }

    public boolean needKeyChange() {
        return this.needKeyChange;
    }

    public String getStringManagerURI() {
        return this.mStringManagerURI;
    }

    public String getStringExtraData() {
        return this.mStringExtraData;
    }

    public void setManagerURI(byte[] bArr) {
        this.mByteArrayManagerURI = bArr;
        if (bArr != null) {
            try {
                String str = new String(bArr, "UTF-8");
                this.mStringManagerURI = str;
                this.mStringManagerURI = str.substring(1);
            } catch (UnsupportedEncodingException unused) {
                this.mStringManagerURI = "";
            }
        }
    }

    public byte[] getByteArrayManagerURI() {
        return this.mByteArrayManagerURI;
    }

    public void setExtraData(byte[] bArr) {
        this.mByteArrayExtraData = bArr;
        if (bArr != null) {
            try {
                String str = new String(bArr, "UTF-8");
                this.mStringExtraData = str;
                this.mStringExtraData = str.substring(1);
            } catch (UnsupportedEncodingException unused) {
                this.mStringExtraData = "";
            }
        }
    }

    public byte[] getByteArrayExtraData() {
        return this.mByteArrayExtraData;
    }

    public void setExtraId(byte[] bArr) {
        if (bArr != null) {
            byte[] bArr2 = new byte[bArr.length];
            this.mExtraID = bArr2;
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            if (bArr.length == 23) {
                int i = this.mExtraID[21] & 255;
                if (i == 1) {
                    this.isUrlExist = 1;
                    return;
                } else if (i == 2) {
                    this.isUrlExist = 2;
                    return;
                } else {
                    if (i == 3) {
                        this.isUrlExist = 3;
                        return;
                    }
                    return;
                }
            }
            return;
        }
        Log.e(TAG, "setExtraId : data is null");
    }

    public byte[] getExtraId() {
        return this.mExtraID;
    }

    public void setRequestPackage(String str) {
        this.mRequestPkg = str;
    }

    public String getRequestPackage() {
        return this.mRequestPkg;
    }

    public void setConnectivityType(int i) {
        this.mConnectivityType = i;
    }

    public int getConnectivityType() {
        return this.mConnectivityType;
    }

    public Bundle getResultBundle() {
        byte[] bArr = this.mExtraID;
        if (bArr != null) {
            this.mBundle.putByteArray("extra", bArr);
        }
        return this.mBundle;
    }

    public boolean isKeyChanged() {
        return this.isKeyChanged;
    }

    public void setKeyChanged(boolean z) {
        this.isKeyChanged = z;
    }

    public int getReason() {
        return this.mReason;
    }

    public void setReason(int i) {
        this.mReason = i;
        if (i == 0) {
            this.mBundle.putInt("reason", 0);
            this.isKeyChanged = false;
            this.needKeyChange = false;
            return;
        }
        this.mBundle.putInt("reason", i);
    }

    public int getApiState() {
        return this.apiState;
    }

    public void setApiState(int i) {
        this.apiState = i;
    }
}
