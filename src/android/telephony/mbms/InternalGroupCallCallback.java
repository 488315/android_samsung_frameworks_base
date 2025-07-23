package android.telephony.mbms;

import android.os.Binder;
import android.telephony.mbms.IGroupCallCallback;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class InternalGroupCallCallback extends IGroupCallCallback.Stub {
    private final GroupCallCallback mAppCallback;
    private final Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalGroupCallCallback(GroupCallCallback groupCallCallback, Executor executor) {
        this.mAppCallback = groupCallCallback;
        this.mExecutor = executor;
    }

    @Override // android.telephony.mbms.IGroupCallCallback
    public void onError(final int i, final String str) {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalGroupCallCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    InternalGroupCallCallback.this.mAppCallback.onError(i, str);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IGroupCallCallback
    public void onGroupCallStateChanged(final int i, final int i2) {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalGroupCallCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    InternalGroupCallCallback.this.mAppCallback.onGroupCallStateChanged(i, i2);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IGroupCallCallback
    public void onBroadcastSignalStrengthUpdated(final int i) {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalGroupCallCallback.3
                @Override // java.lang.Runnable
                public void run() {
                    InternalGroupCallCallback.this.mAppCallback.onBroadcastSignalStrengthUpdated(i);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void stop() {
        this.mIsStopped = true;
    }
}
