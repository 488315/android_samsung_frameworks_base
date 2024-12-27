package com.samsung.accessory.manager.authentication;

import android.os.Bundle;

import java.io.UnsupportedEncodingException;

public final class AuthenticationResult {
    public boolean isKeyChanged;
    public byte[] mByteArrayExtraData;
    public byte[] mByteArrayManagerURI;
    public int mConnectivityType;
    public byte[] mExtraID;
    public String mRequestPkg;
    public String mStringExtraData;
    public String mStringManagerURI;
    public String publicKey;
    public final Bundle mBundle = new Bundle();
    public int mReason = 90;
    public int apiState = 0;
    public int isUrlExist = 0;

    public final Bundle getResultBundle() {
        byte[] bArr = this.mExtraID;
        if (bArr != null) {
            this.mBundle.putByteArray("extra", bArr);
        }
        return this.mBundle;
    }

    public final void setExtraData(byte[] bArr) {
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

    public final void setManagerURI(byte[] bArr) {
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

    public final void setReason(int i) {
        this.mReason = i;
        if (i != 0) {
            this.mBundle.putInt("reason", i);
        } else {
            this.mBundle.putInt("reason", 0);
            this.isKeyChanged = false;
        }
    }
}
