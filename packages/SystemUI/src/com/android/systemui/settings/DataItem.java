package com.android.systemui.settings;

import com.android.systemui.settings.UserTracker;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DataItem {
    public final WeakReference callback;
    public final Executor executor;

    public DataItem(WeakReference<UserTracker.Callback> weakReference, Executor executor) {
        this.callback = weakReference;
        this.executor = executor;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DataItem)) {
            return false;
        }
        DataItem dataItem = (DataItem) obj;
        return Intrinsics.areEqual(this.callback, dataItem.callback) && Intrinsics.areEqual(this.executor, dataItem.executor);
    }

    public final int hashCode() {
        return this.executor.hashCode() + (this.callback.hashCode() * 31);
    }

    public final String toString() {
        return "DataItem(callback=" + this.callback + ", executor=" + this.executor + ")";
    }
}
