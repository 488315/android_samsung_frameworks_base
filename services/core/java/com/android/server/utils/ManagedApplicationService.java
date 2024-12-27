package com.android.server.utils;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.service.vr.IVrListener;
import android.util.Slog;
import com.android.server.input.KeyboardMetricsCollector;
import com.android.server.vr.VrManagerService;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ManagedApplicationService {
    public IInterface mBoundInterface;
    public final VrManagerService.AnonymousClass3 mChecker;
    public final int mClientLabel;
    public final ComponentName mComponent;
    public AnonymousClass1 mConnection;
    public final Context mContext;
    public final VrManagerService.AnonymousClass1 mEventCb;
    public final Handler mHandler;
    public long mLastRetryTimeMs;
    public VrManagerService.AnonymousClass6 mPendingEvent;
    public int mRetryCount;
    public final int mRetryType;
    public boolean mRetrying;
    public final String mSettingsAction;
    public final int mUserId;
    public final ManagedApplicationService$$ExternalSyntheticLambda0 mRetryRunnable = new Runnable() { // from class: com.android.server.utils.ManagedApplicationService$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            ManagedApplicationService managedApplicationService = ManagedApplicationService.this;
            synchronized (managedApplicationService.mLock) {
                try {
                    if (managedApplicationService.mConnection == null) {
                        return;
                    }
                    if (managedApplicationService.mRetrying) {
                        Slog.i("ManagedApplicationService", "Attempting to reconnect " + managedApplicationService.mComponent + "...");
                        managedApplicationService.disconnect();
                        if (managedApplicationService.checkAndDeliverServiceDiedCbLocked()) {
                            return;
                        }
                        managedApplicationService.queueRetryLocked();
                        managedApplicationService.connect();
                    }
                } finally {
                }
            }
        }
    };
    public final Object mLock = new Object();
    public long mNextRetryDurationMs = 2000;
    public final boolean mIsImportant = true;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.utils.ManagedApplicationService$1, reason: invalid class name */
    public final class AnonymousClass1 implements ServiceConnection {
        public AnonymousClass1() {
        }

        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            long currentTimeMillis = System.currentTimeMillis();
            ManagedApplicationService.this.getClass();
            Slog.w("ManagedApplicationService", "Service binding died: " + componentName);
            synchronized (ManagedApplicationService.this.mLock) {
                try {
                    ManagedApplicationService managedApplicationService = ManagedApplicationService.this;
                    if (managedApplicationService.mConnection != this) {
                        return;
                    }
                    managedApplicationService.mHandler.post(new ManagedApplicationService$$ExternalSyntheticLambda1(3, currentTimeMillis, this));
                    ManagedApplicationService managedApplicationService2 = ManagedApplicationService.this;
                    managedApplicationService2.mBoundInterface = null;
                    managedApplicationService2.startRetriesLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            VrManagerService.AnonymousClass6 anonymousClass6;
            long currentTimeMillis = System.currentTimeMillis();
            ManagedApplicationService.this.getClass();
            Slog.i("ManagedApplicationService", "Service connected: " + componentName);
            synchronized (ManagedApplicationService.this.mLock) {
                try {
                    ManagedApplicationService managedApplicationService = ManagedApplicationService.this;
                    if (managedApplicationService.mConnection != this) {
                        return;
                    }
                    managedApplicationService.mHandler.post(new ManagedApplicationService$$ExternalSyntheticLambda1(1, currentTimeMillis, this));
                    ManagedApplicationService managedApplicationService2 = ManagedApplicationService.this;
                    managedApplicationService2.mRetrying = false;
                    managedApplicationService2.mHandler.removeCallbacks(managedApplicationService2.mRetryRunnable);
                    ManagedApplicationService managedApplicationService3 = ManagedApplicationService.this;
                    IInterface iInterface = null;
                    managedApplicationService3.mBoundInterface = null;
                    if (managedApplicationService3.mChecker != null) {
                        managedApplicationService3.mBoundInterface = IVrListener.Stub.asInterface(iBinder);
                        ManagedApplicationService managedApplicationService4 = ManagedApplicationService.this;
                        VrManagerService.AnonymousClass3 anonymousClass3 = managedApplicationService4.mChecker;
                        IInterface iInterface2 = managedApplicationService4.mBoundInterface;
                        anonymousClass3.getClass();
                        if (!(iInterface2 instanceof IVrListener)) {
                            ManagedApplicationService.this.mBoundInterface = null;
                            Slog.w("ManagedApplicationService", "Invalid binder from " + componentName);
                            ManagedApplicationService.this.startRetriesLocked();
                            return;
                        }
                        ManagedApplicationService managedApplicationService5 = ManagedApplicationService.this;
                        IInterface iInterface3 = managedApplicationService5.mBoundInterface;
                        anonymousClass6 = managedApplicationService5.mPendingEvent;
                        managedApplicationService5.mPendingEvent = null;
                        iInterface = iInterface3;
                    } else {
                        anonymousClass6 = null;
                    }
                    if (iInterface == null || anonymousClass6 == null) {
                        return;
                    }
                    try {
                        ((IVrListener) iInterface).focusedActivityChanged(anonymousClass6.val$c, anonymousClass6.val$b, anonymousClass6.val$pid);
                    } catch (RemoteException | RuntimeException e) {
                        ManagedApplicationService.this.getClass();
                        Slog.e("ManagedApplicationService", "Received exception from user service: ", e);
                        ManagedApplicationService.this.startRetriesLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            long currentTimeMillis = System.currentTimeMillis();
            ManagedApplicationService.this.getClass();
            Slog.w("ManagedApplicationService", "Service disconnected: " + componentName);
            synchronized (ManagedApplicationService.this.mLock) {
                try {
                    ManagedApplicationService managedApplicationService = ManagedApplicationService.this;
                    if (managedApplicationService.mConnection != this) {
                        return;
                    }
                    managedApplicationService.mHandler.post(new ManagedApplicationService$$ExternalSyntheticLambda1(2, currentTimeMillis, this));
                    ManagedApplicationService managedApplicationService2 = ManagedApplicationService.this;
                    managedApplicationService2.mBoundInterface = null;
                    managedApplicationService2.startRetriesLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LogEvent implements LogFormattable {
        public final ComponentName component;
        public final int event;
        public final long timestamp;

        public LogEvent(ComponentName componentName, long j, int i) {
            this.timestamp = j;
            this.component = componentName;
            this.event = i;
        }

        @Override // com.android.server.utils.ManagedApplicationService.LogFormattable
        public final String toLogString(SimpleDateFormat simpleDateFormat) {
            StringBuilder sb = new StringBuilder();
            sb.append(simpleDateFormat.format(new Date(this.timestamp)));
            sb.append("   ");
            int i = this.event;
            sb.append(i != 1 ? i != 2 ? i != 3 ? i != 4 ? "Unknown Event Occurred" : "Permanently Stopped" : "Binding Died For" : "Disconnected" : "Connected");
            sb.append(" Managed Service: ");
            ComponentName componentName = this.component;
            sb.append(componentName == null ? KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG : componentName.flattenToString());
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface LogFormattable {
        String toLogString(SimpleDateFormat simpleDateFormat);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.utils.ManagedApplicationService$$ExternalSyntheticLambda0] */
    public ManagedApplicationService(Context context, ComponentName componentName, int i, int i2, String str, VrManagerService.AnonymousClass3 anonymousClass3, int i3, Handler handler, VrManagerService.AnonymousClass1 anonymousClass1) {
        this.mContext = context;
        this.mComponent = componentName;
        this.mUserId = i;
        this.mClientLabel = i2;
        this.mSettingsAction = str;
        this.mChecker = anonymousClass3;
        this.mRetryType = i3;
        this.mHandler = handler;
        this.mEventCb = anonymousClass1;
    }

    public final boolean checkAndDeliverServiceDiedCbLocked() {
        int i = this.mRetryType;
        if (i != 2 && (i != 3 || this.mRetryCount < 4)) {
            return false;
        }
        Slog.e("ManagedApplicationService", "Service " + this.mComponent + " has died too much, not retrying.");
        if (this.mEventCb == null) {
            return true;
        }
        this.mHandler.post(new ManagedApplicationService$$ExternalSyntheticLambda1(0, System.currentTimeMillis(), this));
        return true;
    }

    public final void connect() {
        synchronized (this.mLock) {
            try {
                if (this.mConnection != null) {
                    return;
                }
                Intent component = new Intent().setComponent(this.mComponent);
                int i = this.mClientLabel;
                if (i != 0) {
                    component.putExtra("android.intent.extra.client_label", i);
                }
                if (this.mSettingsAction != null) {
                    component.putExtra("android.intent.extra.client_intent", PendingIntent.getActivity(this.mContext, 0, new Intent(this.mSettingsAction), 67108864));
                }
                AnonymousClass1 anonymousClass1 = new AnonymousClass1();
                this.mConnection = anonymousClass1;
                try {
                    if (!this.mContext.bindServiceAsUser(component, anonymousClass1, this.mIsImportant ? 67108929 : 67108865, new UserHandle(this.mUserId))) {
                        Slog.w("ManagedApplicationService", "Unable to bind service: " + component);
                        startRetriesLocked();
                    }
                } catch (SecurityException e) {
                    Slog.w("ManagedApplicationService", "Unable to bind service: " + component, e);
                    startRetriesLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void disconnect() {
        synchronized (this.mLock) {
            try {
                AnonymousClass1 anonymousClass1 = this.mConnection;
                if (anonymousClass1 == null) {
                    return;
                }
                this.mContext.unbindService(anonymousClass1);
                this.mConnection = null;
                this.mBoundInterface = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void queueRetryLocked() {
        long uptimeMillis = SystemClock.uptimeMillis();
        if (uptimeMillis - this.mLastRetryTimeMs > 64000) {
            this.mNextRetryDurationMs = 2000L;
            this.mRetryCount = 0;
        }
        this.mLastRetryTimeMs = uptimeMillis;
        this.mHandler.postDelayed(this.mRetryRunnable, this.mNextRetryDurationMs);
        this.mNextRetryDurationMs = Math.min(this.mNextRetryDurationMs * 2, 16000L);
        this.mRetryCount++;
    }

    public final void startRetriesLocked() {
        if (checkAndDeliverServiceDiedCbLocked()) {
            disconnect();
        } else {
            if (this.mRetrying) {
                return;
            }
            this.mRetrying = true;
            queueRetryLocked();
        }
    }
}
