package com.android.internal.telephony.uicc.asn1;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

/* loaded from: classes4.dex */
public class TagNotFoundException extends Exception {
    private final int mTag;

    public TagNotFoundException(int i) {
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
