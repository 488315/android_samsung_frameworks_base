package com.android.internal.org.bouncycastle.jce.provider;

import com.android.internal.org.bouncycastle.asn1.x509.ReasonFlags;

/* loaded from: classes5.dex */
class ReasonsMask {
    static final ReasonsMask allReasons = new ReasonsMask(33023);
    private int _reasons;

    ReasonsMask(ReasonFlags reasonFlags) {
        this._reasons = reasonFlags.intValue();
    }

    private ReasonsMask(int i) {
        this._reasons = i;
    }

    ReasonsMask() {
        this(0);
    }

    void addReasons(ReasonsMask reasonsMask) {
        this._reasons = reasonsMask.getReasons() | this._reasons;
    }

    boolean isAllReasons() {
        return this._reasons == allReasons._reasons;
    }

    ReasonsMask intersect(ReasonsMask reasonsMask) {
        ReasonsMask reasonsMask2 = new ReasonsMask();
        reasonsMask2.addReasons(new ReasonsMask(this._reasons & reasonsMask.getReasons()));
        return reasonsMask2;
    }

    boolean hasNewReasons(ReasonsMask reasonsMask) {
        return ((this._reasons ^ reasonsMask.getReasons()) | this._reasons) != 0;
    }

    int getReasons() {
        return this._reasons;
    }
}
