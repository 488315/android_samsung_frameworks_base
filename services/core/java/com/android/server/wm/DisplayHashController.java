package com.android.server.wm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.service.displayhash.IDisplayHashingService;
import android.util.Slog;
import android.view.MagnificationSpec;

import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public final class DisplayHashController {
    public final Context mContext;
    public Map mDisplayHashAlgorithms;
    public long mLastRequestTimeMs;
    public int mLastRequestUid;
    public DisplayHashingServiceConnection mServiceConnection;
    public final Object mServiceConnectionLock = new Object();
    public final Object mDisplayHashAlgorithmsLock = new Object();
    public final float[] mTmpFloat9 = new float[9];
    public final Matrix mTmpMatrix = new Matrix();
    public final RectF mTmpRectF = new RectF();
    public final Object mIntervalBetweenRequestsLock = new Object();
    public int mIntervalBetweenRequestMillis = -1;
    public boolean mDisplayHashThrottlingEnabled = true;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final byte[] mSalt = UUID.randomUUID().toString().getBytes();

    public interface Command {
        void run(IDisplayHashingService iDisplayHashingService);
    }

    public final class DisplayHashingServiceConnection implements ServiceConnection {
        public ArrayList mQueuedCommands;
        public IDisplayHashingService mRemoteService;

        public DisplayHashingServiceConnection() {}

        @Override // android.content.ServiceConnection
        public final void onBindingDied(ComponentName componentName) {
            synchronized (DisplayHashController.this.mServiceConnectionLock) {
                this.mRemoteService = null;
            }
        }

        @Override // android.content.ServiceConnection
        public final void onNullBinding(ComponentName componentName) {
            synchronized (DisplayHashController.this.mServiceConnectionLock) {
                this.mRemoteService = null;
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            synchronized (DisplayHashController.this.mServiceConnectionLock) {
                this.mRemoteService = IDisplayHashingService.Stub.asInterface(iBinder);
                ArrayList arrayList = this.mQueuedCommands;
                if (arrayList != null) {
                    int size = arrayList.size();
                    for (int i = 0; i < size; i++) {
                        try {
                            ((Command) this.mQueuedCommands.get(i)).run(this.mRemoteService);
                        } catch (RemoteException e) {
                            Slog.w(
                                    "WindowManager",
                                    "exception calling " + componentName + ": " + e);
                        }
                    }
                    this.mQueuedCommands = null;
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            synchronized (DisplayHashController.this.mServiceConnectionLock) {
                this.mRemoteService = null;
            }
        }
    }

    public final class Handler extends android.os.Handler {
        public Handler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what == 1) {
                synchronized (DisplayHashController.this.mServiceConnectionLock) {
                    try {
                        DisplayHashController displayHashController = DisplayHashController.this;
                        DisplayHashingServiceConnection displayHashingServiceConnection =
                                displayHashController.mServiceConnection;
                        if (displayHashingServiceConnection != null) {
                            displayHashController.mContext.unbindService(
                                    displayHashingServiceConnection);
                            DisplayHashController.this.mServiceConnection = null;
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public final class SyncCommand {
        public final CountDownLatch mCountDownLatch = new CountDownLatch(1);
        public Bundle mResult;

        public SyncCommand() {}

        public final Bundle run(final BiConsumer biConsumer) {
            DisplayHashController.this.connectAndRun(
                    new Command() { // from class:
                                    // com.android.server.wm.DisplayHashController$SyncCommand$$ExternalSyntheticLambda0
                        @Override // com.android.server.wm.DisplayHashController.Command
                        public final void run(IDisplayHashingService iDisplayHashingService) {
                            BiConsumer biConsumer2 = biConsumer;
                            final DisplayHashController.SyncCommand syncCommand =
                                    DisplayHashController.SyncCommand.this;
                            syncCommand.getClass();
                            biConsumer2.accept(
                                    iDisplayHashingService,
                                    new RemoteCallback(
                                            new RemoteCallback
                                                    .OnResultListener() { // from class:
                                                                          // com.android.server.wm.DisplayHashController$SyncCommand$$ExternalSyntheticLambda1
                                                public final void onResult(Bundle bundle) {
                                                    DisplayHashController.SyncCommand syncCommand2 =
                                                            DisplayHashController.SyncCommand.this;
                                                    syncCommand2.mResult = bundle;
                                                    syncCommand2.mCountDownLatch.countDown();
                                                }
                                            }));
                        }
                    });
            try {
                this.mCountDownLatch.await(5L, TimeUnit.SECONDS);
            } catch (Exception e) {
                Slog.e("WindowManager", "Failed to wait for command", e);
            }
            return this.mResult;
        }
    }

    public DisplayHashController(Context context) {
        this.mContext = context;
    }

    public static void sendDisplayHashError(int i, RemoteCallback remoteCallback) {
        Bundle bundle = new Bundle();
        bundle.putInt("DISPLAY_HASH_ERROR_CODE", i);
        remoteCallback.sendResult(bundle);
    }

    public final void calculateDisplayHashBoundsLocked(
            WindowState windowState, Rect rect, Rect rect2) {
        rect2.set(rect);
        DisplayContent displayContent = windowState.getDisplayContent();
        if (displayContent == null) {
            return;
        }
        Rect rect3 = new Rect();
        windowState.getBounds(rect3);
        rect3.offsetTo(0, 0);
        rect2.intersectUnchecked(rect3);
        if (rect2.isEmpty()) {
            return;
        }
        windowState.getTransformationMatrix(this.mTmpFloat9, this.mTmpMatrix);
        this.mTmpRectF.set(rect2);
        Matrix matrix = this.mTmpMatrix;
        RectF rectF = this.mTmpRectF;
        matrix.mapRect(rectF, rectF);
        RectF rectF2 = this.mTmpRectF;
        rect2.set((int) rectF2.left, (int) rectF2.top, (int) rectF2.right, (int) rectF2.bottom);
        MagnificationSpec magnificationSpec = displayContent.mMagnificationSpec;
        if (magnificationSpec != null) {
            rect2.scale(magnificationSpec.scale);
            rect2.offset((int) magnificationSpec.offsetX, (int) magnificationSpec.offsetY);
        }
        if (rect2.isEmpty()) {
            return;
        }
        rect2.intersectUnchecked(displayContent.getBounds());
    }

    public final void connectAndRun(Command command) {
        ComponentName serviceComponentName;
        synchronized (this.mServiceConnectionLock) {
            try {
                Handler handler = this.mHandler;
                handler.removeMessages(1);
                handler.sendEmptyMessageDelayed(1, 10000L);
                if (this.mServiceConnection == null
                        && (serviceComponentName = getServiceComponentName()) != null) {
                    Intent intent = new Intent();
                    intent.setComponent(serviceComponentName);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        DisplayHashingServiceConnection displayHashingServiceConnection =
                                new DisplayHashingServiceConnection();
                        this.mServiceConnection = displayHashingServiceConnection;
                        this.mContext.bindService(intent, displayHashingServiceConnection, 1);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                DisplayHashingServiceConnection displayHashingServiceConnection2 =
                        this.mServiceConnection;
                if (displayHashingServiceConnection2 != null) {
                    IDisplayHashingService iDisplayHashingService =
                            displayHashingServiceConnection2.mRemoteService;
                    if (iDisplayHashingService == null) {
                        if (displayHashingServiceConnection2.mQueuedCommands == null) {
                            displayHashingServiceConnection2.mQueuedCommands = new ArrayList(1);
                        }
                        displayHashingServiceConnection2.mQueuedCommands.add(command);
                    } else {
                        try {
                            command.run(iDisplayHashingService);
                        } catch (RemoteException e) {
                            AccountManagerService$$ExternalSyntheticOutline0.m(
                                    "exception calling service: ", e, "WindowManager");
                        }
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public final Map getDisplayHashAlgorithms() {
        synchronized (this.mDisplayHashAlgorithmsLock) {
            try {
                Map map = this.mDisplayHashAlgorithms;
                if (map != null) {
                    return map;
                }
                Bundle run =
                        new SyncCommand()
                                .run(new DisplayHashController$$ExternalSyntheticLambda1(1));
                this.mDisplayHashAlgorithms = new HashMap(run.size());
                for (String str : run.keySet()) {
                    ((HashMap) this.mDisplayHashAlgorithms).put(str, run.getParcelable(str));
                }
                return this.mDisplayHashAlgorithms;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.ComponentName getServiceComponentName() {
        /*
            r6 = this;
            android.content.Context r0 = r6.mContext
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            java.lang.String r0 = r0.getServicesSystemSharedLibraryPackageName()
            java.lang.String r1 = "WindowManager"
            r2 = 0
            if (r0 != 0) goto L17
            java.lang.String r6 = "no external services package!"
            android.util.Slog.w(r1, r6)
        L15:
            r6 = r2
            goto L3c
        L17:
            java.lang.String r3 = "android.service.displayhash.DisplayHashingService"
            android.content.Intent r0 = com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0.m(r3, r0)
            long r3 = android.os.Binder.clearCallingIdentity()
            android.content.Context r6 = r6.mContext     // Catch: java.lang.Throwable -> L6c
            android.content.pm.PackageManager r6 = r6.getPackageManager()     // Catch: java.lang.Throwable -> L6c
            r5 = 132(0x84, float:1.85E-43)
            android.content.pm.ResolveInfo r6 = r6.resolveService(r0, r5)     // Catch: java.lang.Throwable -> L6c
            android.os.Binder.restoreCallingIdentity(r3)
            if (r6 == 0) goto L36
            android.content.pm.ServiceInfo r6 = r6.serviceInfo
            if (r6 != 0) goto L3c
        L36:
            java.lang.String r6 = "No valid components found."
            android.util.Slog.w(r1, r6)
            goto L15
        L3c:
            if (r6 != 0) goto L3f
            return r2
        L3f:
            android.content.ComponentName r0 = new android.content.ComponentName
            java.lang.String r3 = r6.packageName
            java.lang.String r4 = r6.name
            r0.<init>(r3, r4)
            java.lang.String r3 = "android.permission.BIND_DISPLAY_HASHING_SERVICE"
            java.lang.String r6 = r6.permission
            boolean r6 = r3.equals(r6)
            if (r6 != 0) goto L6b
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = r0.flattenToShortString()
            r6.append(r0)
            java.lang.String r0 = " requires permission android.permission.BIND_DISPLAY_HASHING_SERVICE"
            r6.append(r0)
            java.lang.String r6 = r6.toString()
            android.util.Slog.w(r1, r6)
            return r2
        L6b:
            return r0
        L6c:
            r6 = move-exception
            android.os.Binder.restoreCallingIdentity(r3)
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DisplayHashController.getServiceComponentName():android.content.ComponentName");
    }
}
