package com.android.systemui.screenshot;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.Log;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScreenshotNotificationSmartActionsProvider;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;
import com.android.systemui.screenshot.sep.ScreenshotUtils;
import com.samsung.android.app.smartcapture.screenshot.lib.IScreenshotService;
import com.samsung.android.app.smartcapture.screenshot.lib.RemoteScreenshotInterface;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.sec.ims.ImsManager;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SaveImageInBackgroundTask extends AsyncTask {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final String mBixbyCaptureSharedActivityName;
    public final String mBixbyCaptureSharedPackageName;
    public final Bitmap.CompressFormat mCompressFormat;
    public final Context mContext;
    public final String mImageDisplayName;
    public final ImageExporter mImageExporter;
    public final String mImageFileName;
    public final String mImageFilePath;
    public long mImageTime;
    public final boolean mIsBixbyCaptureShared;
    public final ScreenshotController.SaveImageInBackgroundData mParams;
    public String mScreenshotId;
    public RemoteScreenshotInterface mScreenshotInterface;
    public final ScreenshotSmartActions mScreenshotSmartActions;
    public final ScreenshotNotificationSmartActionsProvider mSmartActionsProvider;
    public final Random mRandom = new Random();
    public final long mScrollCaptureTransactionId = System.currentTimeMillis();
    public boolean mIsScrollCaptureConnectionListenerInvoked = false;
    public boolean mIsSavingFailed = false;
    public final ScreenshotController.SavedImageData mImageData = new ScreenshotController.SavedImageData();
    public final ScreenshotController.QuickShareData mQuickShareData = new ScreenshotController.QuickShareData();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.screenshot.SaveImageInBackgroundTask$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final /* synthetic */ ScreenCaptureHelper val$captureHelper;
        public final /* synthetic */ long val$connectionStartTime;

        public AnonymousClass1(long j, ScreenCaptureHelper screenCaptureHelper) {
            this.val$connectionStartTime = j;
            this.val$captureHelper = screenCaptureHelper;
        }

        public final void onConnectionResult(boolean z) {
            int i = SaveImageInBackgroundTask.$r8$clinit;
            StringBuilder m = RowView$$ExternalSyntheticOutline0.m("onConnectionResult : success = ", " / elapsed = ", z);
            m.append(System.currentTimeMillis() - this.val$connectionStartTime);
            Log.d("Screenshot", m.toString());
            SaveImageInBackgroundTask saveImageInBackgroundTask = SaveImageInBackgroundTask.this;
            if (saveImageInBackgroundTask.mIsSavingFailed) {
                saveImageInBackgroundTask.mScreenshotInterface.disconnect();
                SaveImageInBackgroundTask.this.mScreenshotInterface = null;
                Log.e("Screenshot", "SaveImageInBackgroundTask : Disconnect ScrollCapture service because saving image failed");
            } else if (z) {
                Bundle bundle = new Bundle();
                bundle.putInt("originId", this.val$captureHelper.screenCaptureOrigin);
                bundle.putInt("captureMode", this.val$captureHelper.screenCaptureType);
                bundle.putInt("captureDisplay", this.val$captureHelper.capturedDisplayId);
                bundle.putInt("rotation", this.val$captureHelper.displayRotation);
                bundle.putInt("safeInsetLeft", this.val$captureHelper.safeInsetLeft);
                bundle.putInt("safeInsetTop", this.val$captureHelper.safeInsetTop);
                bundle.putInt("safeInsetRight", this.val$captureHelper.safeInsetRight);
                bundle.putInt("safeInsetBottom", this.val$captureHelper.safeInsetBottom);
                bundle.putBoolean("isSubDisplayCapture", ScreenshotUtils.isSubDisplayCapture(this.val$captureHelper.capturedDisplayId));
                Context context = SaveImageInBackgroundTask.this.mContext;
                ScreenCaptureHelper screenCaptureHelper = this.val$captureHelper;
                if (screenCaptureHelper.screenCaptureType == 1) {
                    bundle.putBoolean("statusBarVisible", screenCaptureHelper.isStatusBarVisible);
                    bundle.putBoolean("navigationBarVisible", this.val$captureHelper.isNavigationBarVisible);
                    bundle.putInt("statusBarHeight", this.val$captureHelper.statusBarHeight);
                    bundle.putInt("navigationBarHeight", this.val$captureHelper.navigationBarHeight);
                }
                if (SaveImageInBackgroundTask.this.mIsBixbyCaptureShared) {
                    bundle.putBoolean("isSmartCaptureVisible", false);
                }
                SaveImageInBackgroundTask saveImageInBackgroundTask2 = SaveImageInBackgroundTask.this;
                RemoteScreenshotInterface remoteScreenshotInterface = saveImageInBackgroundTask2.mScreenshotInterface;
                long j = saveImageInBackgroundTask2.mScrollCaptureTransactionId;
                String str = saveImageInBackgroundTask2.mImageFilePath;
                remoteScreenshotInterface.getClass();
                Log.d("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotStarted");
                IScreenshotService iScreenshotService = remoteScreenshotInterface.mService;
                if (iScreenshotService != null) {
                    try {
                        ((IScreenshotService.Stub.Proxy) iScreenshotService).onGlobalScreenshotStarted(j, str, bundle);
                    } catch (Exception e) {
                        Log.e("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotStarted : e=" + e);
                        Log.e("[ScrCap]_RemoteScreenshotInterface", e.toString());
                    }
                } else {
                    Log.e("[ScrCap]_RemoteScreenshotInterface", "notifyGlobalScreenshotStarted : No service connection");
                }
            } else {
                Log.e("Screenshot", "SaveImageInBackgroundTask : Failed to connect to ScrollCapture service");
                SaveImageInBackgroundTask.this.mScreenshotInterface = null;
            }
            RemoteScreenshotInterface remoteScreenshotInterface2 = SaveImageInBackgroundTask.this.mScreenshotInterface;
            if (remoteScreenshotInterface2 != null) {
                synchronized (remoteScreenshotInterface2) {
                    SaveImageInBackgroundTask saveImageInBackgroundTask3 = SaveImageInBackgroundTask.this;
                    saveImageInBackgroundTask3.mIsScrollCaptureConnectionListenerInvoked = true;
                    saveImageInBackgroundTask3.mScreenshotInterface.notify();
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x027b  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0298  */
    /* JADX WARN: Removed duplicated region for block: B:58:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0284  */
    /* JADX WARN: Type inference failed for: r8v2, types: [com.samsung.android.app.smartcapture.screenshot.lib.RemoteScreenshotInterface$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SaveImageInBackgroundTask(android.content.Context r8, com.android.systemui.flags.FeatureFlags r9, com.android.systemui.screenshot.ImageExporter r10, com.android.systemui.screenshot.ScreenshotSmartActions r11, com.android.systemui.screenshot.ScreenshotController.SaveImageInBackgroundData r12, com.android.systemui.screenshot.ScreenshotNotificationSmartActionsProvider r13) {
        /*
            Method dump skipped, instructions count: 685
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.SaveImageInBackgroundTask.<init>(android.content.Context, com.android.systemui.flags.FeatureFlags, com.android.systemui.screenshot.ImageExporter, com.android.systemui.screenshot.ScreenshotSmartActions, com.android.systemui.screenshot.ScreenshotController$SaveImageInBackgroundData, com.android.systemui.screenshot.ScreenshotNotificationSmartActionsProvider):void");
    }

    public static String getSubjectString(long j) {
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Screenshot (", DateFormat.getDateTimeInstance().format(new Date(j)), ")");
    }

    public static boolean isFormatPNG(Context context) {
        int semGetMyUserId = UserHandle.semGetMyUserId();
        String string = (150 > semGetMyUserId || 160 < semGetMyUserId) ? Settings.System.getString(context.getContentResolver(), "smart_capture_screenshot_format") : Settings.System.getStringForUser(context.getContentResolver(), "smart_capture_screenshot_format", 0);
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("screenshotFormatValue : ", string, "Screenshot");
        return string != null && string.equals("PNG");
    }

    public final List buildSmartActions(Context context, List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Notification.Action action = (Notification.Action) it.next();
            Bundle extras = action.getExtras();
            String string = extras.getString(ImsManager.INTENT_PARAM_RCS_ENABLE_TYPE, "Smart Action");
            Intent addFlags = new Intent(context, (Class<?>) SmartActionsReceiver.class).putExtra("android:screenshot_action_intent", action.actionIntent).addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
            addFlags.putExtra("android:screenshot_action_type", string).putExtra("android:screenshot_id", this.mScreenshotId).putExtra("android:smart_actions_enabled", true);
            arrayList.add(new Notification.Action.Builder(action.getIcon(), action.title, PendingIntent.getBroadcast(context, this.mRandom.nextInt(), addFlags, 335544320)).setContextual(true).addExtras(extras).build());
        }
        return arrayList;
    }

    public Notification.Action createQuickShareAction(Notification.Action action, String str, Uri uri, long j, Bitmap bitmap, UserHandle userHandle) {
        if (action == null) {
            return null;
        }
        if (action.actionIntent.isImmutable()) {
            Notification.Action queryQuickShareAction = queryQuickShareAction(str, bitmap, userHandle, uri);
            if (queryQuickShareAction == null || !queryQuickShareAction.title.toString().contentEquals(action.title)) {
                return null;
            }
            action = queryQuickShareAction;
        }
        Intent putExtra = new Intent(this.mContext, (Class<?>) SmartActionsReceiver.class).putExtra("android:screenshot_action_intent", action.actionIntent);
        Intent intent = new Intent();
        intent.setType("image/png");
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.putExtra("android.intent.extra.SUBJECT", getSubjectString(j));
        intent.setClipData(new ClipData(new ClipDescription("content", new String[]{"image/png"}), new ClipData.Item(uri)));
        intent.addFlags(1);
        Intent addFlags = putExtra.putExtra("android:screenshot_action_intent_fillin", intent).addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        Bundle extras = action.getExtras();
        addFlags.putExtra("android:screenshot_action_type", extras.getString(ImsManager.INTENT_PARAM_RCS_ENABLE_TYPE, "Smart Action")).putExtra("android:screenshot_id", str).putExtra("android:smart_actions_enabled", true);
        return new Notification.Action.Builder(action.getIcon(), action.title, PendingIntent.getBroadcast(this.mContext, this.mRandom.nextInt(), addFlags, 335544320)).setContextual(true).addExtras(extras).build();
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0311  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x034e  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0357  */
    @Override // android.os.AsyncTask
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object doInBackground(java.lang.Object[] r20) {
        /*
            Method dump skipped, instructions count: 1057
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.SaveImageInBackgroundTask.doInBackground(java.lang.Object[]):java.lang.Object");
    }

    @Override // android.os.AsyncTask
    public final void onCancelled(Object obj) {
        ScreenshotController.SavedImageData savedImageData = this.mImageData;
        savedImageData.uri = null;
        savedImageData.smartActions = null;
        savedImageData.quickShareAction = null;
        savedImageData.subject = null;
        this.mQuickShareData.quickShareAction = null;
        this.mParams.mActionsReadyListener.onActionsReady(savedImageData);
        this.mParams.finisher.accept(null);
        this.mParams.image = null;
    }

    public Notification.Action queryQuickShareAction(String str, Bitmap bitmap, UserHandle userHandle, Uri uri) {
        ScreenshotSmartActions screenshotSmartActions = this.mScreenshotSmartActions;
        ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider = this.mSmartActionsProvider;
        ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType screenshotSmartActionType = ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType.REGULAR_SMART_ACTIONS;
        screenshotSmartActions.getClass();
        CompletableFuture smartActionsFuture = ScreenshotSmartActions.getSmartActionsFuture(bitmap, screenshotNotificationSmartActionsProvider, true);
        int i = DeviceConfig.getInt("systemui", "screenshot_notification_quick_share_actions_timeout_ms", 500);
        ScreenshotSmartActions screenshotSmartActions2 = this.mScreenshotSmartActions;
        ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider2 = this.mSmartActionsProvider;
        screenshotSmartActions2.getClass();
        List smartActions = ScreenshotSmartActions.getSmartActions(smartActionsFuture, i, screenshotNotificationSmartActionsProvider2);
        if (smartActions.isEmpty()) {
            return null;
        }
        return (Notification.Action) smartActions.get(0);
    }
}
