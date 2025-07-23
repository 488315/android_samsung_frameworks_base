package com.android.ims;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

@Deprecated
/* loaded from: classes5.dex */
public class ImsException extends Exception {
    private int mCode;

    public ImsException() {
    }

    public ImsException(String str) {
        super(str);
        this.mCode = 0;
    }

    public ImsException(String str, int i) {
        super(str + NavigationBarInflaterView.KEY_CODE_START + i + NavigationBarInflaterView.KEY_CODE_END);
        this.mCode = i;
    }

    public ImsException(String str, Throwable th, int i) {
        super(str, th);
        this.mCode = i;
    }

    public int getCode() {
        return this.mCode;
    }
}
