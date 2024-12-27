package com.android.server.knox.zt.devicetrust.task;

import android.os.RemoteException;
import android.util.Log;
import com.android.server.knox.zt.devicetrust.EndpointMonitorImpl;
import com.android.server.knox.zt.devicetrust.EndpointMonitorNative;
import com.android.server.knox.zt.devicetrust.data.EndpointData;
import com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class MonitoringTask implements Runnable {
    public final long mBootTimeNanos;
    public final int mExtras;
    public volatile Predicate mFilter;
    public final int mFlags;
    public volatile IEndpointMonitorListener mListener;
    public final int mMode;
    public final EndpointMonitorNative mNative;
    public final int mType;
    public final int mUid;

    public MonitoringTask(int i, int i2, int i3, int i4, int i5, IEndpointMonitorListener iEndpointMonitorListener, Predicate predicate, EndpointMonitorImpl.Injector injector) {
        this.mType = i;
        this.mUid = i2;
        this.mMode = i3;
        this.mFlags = i4;
        this.mExtras = i5;
        this.mBootTimeNanos = injector.mBootTimeNanos;
        this.mNative = injector.mNative;
        this.mListener = ensureListener(iEndpointMonitorListener);
        this.mFilter = ensureFilter(predicate);
    }

    public static /* synthetic */ boolean lambda$ensureFilter$0(EndpointData endpointData) {
        return true;
    }

    public final boolean checkPermission(int i) {
        return this.mUid == i;
    }

    public final Predicate ensureFilter(Predicate predicate) {
        return predicate != null ? predicate : new MonitoringTask$$ExternalSyntheticLambda0();
    }

    public final IEndpointMonitorListener ensureListener(IEndpointMonitorListener iEndpointMonitorListener) {
        return iEndpointMonitorListener != null ? iEndpointMonitorListener : new IEndpointMonitorListener.Default();
    }

    public final long getBootTimeNanos() {
        return this.mBootTimeNanos;
    }

    public final int getExtras() {
        return this.mExtras;
    }

    public final Predicate getFilter() {
        return this.mFilter;
    }

    public final int getFlags() {
        return this.mFlags;
    }

    public final IEndpointMonitorListener getListener() {
        return this.mListener;
    }

    public final int getMode() {
        return this.mMode;
    }

    public final String getName() {
        return getTag();
    }

    public final EndpointMonitorNative getNative() {
        return this.mNative;
    }

    public final String getTag() {
        return getClass().getSimpleName();
    }

    public final int getType() {
        return this.mType;
    }

    public final int getUid() {
        return this.mUid;
    }

    public final void onEvent(EndpointData endpointData) throws RemoteException {
        IEndpointMonitorListener iEndpointMonitorListener;
        if (this.mFilter.test(endpointData) && (iEndpointMonitorListener = this.mListener) != null) {
            int i = this.mMode;
            if (i == 1) {
                iEndpointMonitorListener.onEventSimplified(this.mType, endpointData.adjustTime(this.mBootTimeNanos).updateExtras(this.mExtras).toLine());
            } else if (i == 2) {
                iEndpointMonitorListener.onEventGeneralized(this.mType, endpointData.adjustTime(this.mBootTimeNanos).updateExtras(this.mExtras).toJson());
            } else {
                if (i != 3) {
                    return;
                }
                iEndpointMonitorListener.onEvent(this.mType, endpointData.adjustTime(this.mBootTimeNanos).updateExtras(this.mExtras).toBundle());
            }
        }
    }

    public abstract void onMonitored();

    public void onTransactionFailure(String str, Throwable th) {
        Log.e(getTag(), str + th);
    }

    @Override // java.lang.Runnable
    public final void run() {
        onMonitored();
    }
}
