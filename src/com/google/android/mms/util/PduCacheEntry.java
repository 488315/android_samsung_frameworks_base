package com.google.android.mms.util;

import com.google.android.mms.pdu.GenericPdu;

/* loaded from: classes6.dex */
public final class PduCacheEntry {
    private final int mMessageBox;
    private final GenericPdu mPdu;
    private final long mThreadId;

    public PduCacheEntry(GenericPdu genericPdu, int i, long j) {
        this.mPdu = genericPdu;
        this.mMessageBox = i;
        this.mThreadId = j;
    }

    public GenericPdu getPdu() {
        return this.mPdu;
    }

    public int getMessageBox() {
        return this.mMessageBox;
    }

    public long getThreadId() {
        return this.mThreadId;
    }
}
