package com.android.server.location.contexthub;

import android.hardware.location.ContextHubTransaction;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class ContextHubServiceTransaction {
    public boolean mIsComplete;
    public final Integer mMessageSequenceNumber;
    public final Long mNanoAppId;
    public final String mPackage;
    public final int mTransactionId;
    public final int mTransactionType;

    public ContextHubServiceTransaction(int i, int i2, long j, String str) {
        this.mIsComplete = false;
        this.mTransactionId = i;
        this.mTransactionType = i2;
        this.mNanoAppId = Long.valueOf(j);
        this.mPackage = str;
        this.mMessageSequenceNumber = null;
    }

    public ContextHubServiceTransaction(int i, int i2, String str) {
        this.mIsComplete = false;
        this.mTransactionId = i;
        this.mTransactionType = i2;
        this.mNanoAppId = null;
        this.mPackage = str;
        this.mMessageSequenceNumber = null;
    }

    public ContextHubServiceTransaction(int i, String str, int i2) {
        this.mIsComplete = false;
        this.mTransactionId = i;
        this.mTransactionType = 5;
        this.mNanoAppId = null;
        this.mPackage = str;
        this.mMessageSequenceNumber = Integer.valueOf(i2);
    }

    public void onQueryResponse(int i, List list) {}

    public abstract int onTransact();

    public abstract void onTransactionComplete(int i);

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ContextHubTransaction.typeToString(this.mTransactionType, true));
        sb.append(" (");
        Long l = this.mNanoAppId;
        if (l != null) {
            sb.append("appId = 0x");
            sb.append(Long.toHexString(l.longValue()));
            sb.append(", ");
        }
        sb.append("package = ");
        sb.append(this.mPackage);
        Integer num = this.mMessageSequenceNumber;
        if (num != null) {
            sb.append(", messageSequenceNumber = ");
            sb.append(num);
        }
        sb.append(")");
        return sb.toString();
    }
}
