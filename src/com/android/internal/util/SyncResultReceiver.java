package com.android.internal.util;

import android.os.Bundle;
import android.os.Parcelable;
import com.android.internal.os.IResultReceiver;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class SyncResultReceiver extends IResultReceiver.Stub {
    private static final String EXTRA = "EXTRA";
    private Bundle mBundle;
    private final CountDownLatch mLatch = new CountDownLatch(1);
    private int mResult;
    private final int mTimeoutMs;

    public SyncResultReceiver(int i) {
        this.mTimeoutMs = i;
    }

    private void waitResult() throws TimeoutException {
        try {
            if (this.mLatch.await(this.mTimeoutMs, TimeUnit.MILLISECONDS)) {
                return;
            }
            throw new TimeoutException("Not called in " + this.mTimeoutMs + "ms");
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            throw new TimeoutException("Interrupted");
        }
    }

    public int getIntResult() throws TimeoutException {
        waitResult();
        return this.mResult;
    }

    public String getStringResult() throws TimeoutException {
        waitResult();
        Bundle bundle = this.mBundle;
        if (bundle == null) {
            return null;
        }
        return bundle.getString("EXTRA");
    }

    public String[] getStringArrayResult() throws TimeoutException {
        waitResult();
        Bundle bundle = this.mBundle;
        if (bundle == null) {
            return null;
        }
        return bundle.getStringArray("EXTRA");
    }

    public <P extends Parcelable> P getParcelableResult() throws TimeoutException {
        waitResult();
        Bundle bundle = this.mBundle;
        if (bundle == null) {
            return null;
        }
        return (P) bundle.getParcelable("EXTRA");
    }

    public <P extends Parcelable> ArrayList<P> getParcelableListResult() throws TimeoutException {
        waitResult();
        Bundle bundle = this.mBundle;
        if (bundle == null) {
            return null;
        }
        return bundle.getParcelableArrayList("EXTRA");
    }

    public int getOptionalExtraIntResult(int i) throws TimeoutException {
        waitResult();
        Bundle bundle = this.mBundle;
        return (bundle == null || !bundle.containsKey("EXTRA")) ? i : this.mBundle.getInt("EXTRA");
    }

    @Override // com.android.internal.os.IResultReceiver
    public void send(int i, Bundle bundle) {
        this.mResult = i;
        this.mBundle = bundle;
        this.mLatch.countDown();
    }

    public static Bundle bundleFor(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("EXTRA", str);
        return bundle;
    }

    public static Bundle bundleFor(String[] strArr) {
        Bundle bundle = new Bundle();
        bundle.putStringArray("EXTRA", strArr);
        return bundle;
    }

    public static Bundle bundleFor(Parcelable parcelable) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("EXTRA", parcelable);
        return bundle;
    }

    public static Bundle bundleFor(ArrayList<? extends Parcelable> arrayList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("EXTRA", arrayList);
        return bundle;
    }

    public static Bundle bundleFor(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("EXTRA", i);
        return bundle;
    }

    public static final class TimeoutException extends Exception {
        private TimeoutException(String str) {
            super(str);
        }
    }
}
