package com.android.systemui.screenshot;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.UserManager;
import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.ScreenshotRequest;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.screenshot.ScreenshotNotificationsController;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;
import com.android.systemui.screenshot.sep.ScreenshotErrorController;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TakeScreenshotService extends Service {
    public static boolean sConfigured = false;
    public final Executor mBgExecutor;
    public Bundle mBundle;
    public final Context mContext;
    public final ScreenshotNotificationsController mNotificationsController;
    public ScreenCaptureHelper mScreenCaptureHelper;
    public final ScreenshotErrorController mScreenshotErrorController;
    public final TakeScreenshotExecutor mTakeScreenshotExecutor;
    public final UiEventLogger mUiEventLogger;
    public final UserManager mUserManager;
    public final AnonymousClass1 mCloseSystemDialogs = new BroadcastReceiver() { // from class: com.android.systemui.screenshot.TakeScreenshotService.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            TakeScreenshotExecutorImpl takeScreenshotExecutorImpl;
            InteractiveScreenshotHandler interactiveScreenshotHandler;
            InteractiveScreenshotHandler interactiveScreenshotHandler2;
            if (!PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction()) || (interactiveScreenshotHandler = (takeScreenshotExecutorImpl = (TakeScreenshotExecutorImpl) TakeScreenshotService.this.mTakeScreenshotExecutor).screenshotController) == null || interactiveScreenshotHandler.isPendingSharedTransition() || (interactiveScreenshotHandler2 = takeScreenshotExecutorImpl.screenshotController) == null) {
                return;
            }
            interactiveScreenshotHandler2.requestDismissal(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
        }
    };
    public final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda0
        /* JADX WARN: Removed duplicated region for block: B:117:0x03d4  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x01ba  */
        /* JADX WARN: Removed duplicated region for block: B:92:0x031a  */
        @Override // android.os.Handler.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean handleMessage(android.os.Message r19) {
            /*
                Method dump skipped, instructions count: 1006
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda0.handleMessage(android.os.Message):boolean");
        }
    });

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface RequestCallback {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RequestCallbackImpl implements RequestCallback {
        public final Messenger mReplyTo;

        public RequestCallbackImpl(Messenger messenger) {
            this.mReplyTo = messenger;
        }

        public final void reportError() {
            Messenger messenger = this.mReplyTo;
            boolean z = TakeScreenshotService.sConfigured;
            try {
                messenger.send(Message.obtain(null, 1, null));
            } catch (RemoteException e) {
                Log.d("Screenshot", "ignored remote exception", e);
            }
            try {
                this.mReplyTo.send(Message.obtain((Handler) null, 2));
            } catch (RemoteException e2) {
                Log.d("Screenshot", "ignored remote exception", e2);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.screenshot.TakeScreenshotService$1] */
    public TakeScreenshotService(UserManager userManager, DevicePolicyManager devicePolicyManager, UiEventLogger uiEventLogger, ScreenshotNotificationsController.Factory factory, ScreenshotErrorController screenshotErrorController, Context context, Executor executor, TakeScreenshotExecutor takeScreenshotExecutor) {
        this.mUserManager = userManager;
        this.mUiEventLogger = uiEventLogger;
        this.mNotificationsController = factory.create(0);
        this.mContext = context;
        this.mBgExecutor = executor;
        this.mTakeScreenshotExecutor = takeScreenshotExecutor;
        this.mScreenshotErrorController = screenshotErrorController;
    }

    public final void logFailedRequest(ScreenshotRequest screenshotRequest) {
        ComponentName topComponent = screenshotRequest.getTopComponent();
        String packageName = topComponent == null ? "" : topComponent.getPackageName();
        this.mUiEventLogger.log(ScreenshotEvent.getScreenshotSource(screenshotRequest.getSource()), 0, packageName);
        this.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_CAPTURE_FAILED, 0, packageName);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        registerReceiver(this.mCloseSystemDialogs, new IntentFilter(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS), 2);
        return new Messenger(this.mHandler).getBinder();
    }

    @Override // android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        TakeScreenshotExecutorImpl takeScreenshotExecutorImpl = (TakeScreenshotExecutorImpl) this.mTakeScreenshotExecutor;
        InteractiveScreenshotHandler interactiveScreenshotHandler = takeScreenshotExecutorImpl.screenshotController;
        if (interactiveScreenshotHandler != null) {
            interactiveScreenshotHandler.onDestroy();
        }
        takeScreenshotExecutorImpl.screenshotController = null;
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        InteractiveScreenshotHandler interactiveScreenshotHandler = ((TakeScreenshotExecutorImpl) this.mTakeScreenshotExecutor).screenshotController;
        if (interactiveScreenshotHandler != null) {
            interactiveScreenshotHandler.removeWindow();
        }
        unregisterReceiver(this.mCloseSystemDialogs);
        return false;
    }

    @Override // android.app.Service
    public final void onCreate() {
    }
}
