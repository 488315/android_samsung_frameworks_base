package android.app.admin;

import com.android.internal.os.IResultReceiver;

/* loaded from: classes.dex */
public interface DevicePolicySafetyChecker {
    int getUnsafeOperationReason(int i);

    boolean isSafeOperation(int i);

    void onFactoryReset(IResultReceiver iResultReceiver);

    default UnsafeStateException newUnsafeStateException(int i, int i2) {
        return new UnsafeStateException(i, i2);
    }
}
