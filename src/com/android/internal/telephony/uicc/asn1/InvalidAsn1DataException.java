package com.android.internal.telephony.uicc.asn1;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

/* loaded from: classes4.dex */
public class InvalidAsn1DataException extends Exception {
    private final int mTag;

    public InvalidAsn1DataException(int i, String str) {
        super(str);
        this.mTag = i;
    }

    public InvalidAsn1DataException(int i, String str, Throwable th) {
        super(str, th);
        this.mTag = i;
    }

    public int getTag() {
        return this.mTag;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return super.getMessage() + " (tag=" + this.mTag + NavigationBarInflaterView.KEY_CODE_END;
    }
}
