package com.android.systemui.screenshot;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Process;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Base64;
import android.util.Log;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.profileinstaller.ProfileInstaller$$ExternalSyntheticLambda0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.screenshot.ImageExporter;
import com.android.systemui.screenshot.SaveImageInBackgroundTask;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScreenshotNotificationSmartActionsProvider;
import com.android.systemui.screenshot.sep.ScreenCaptureHelper;
import com.android.systemui.screenshot.sep.ScreenshotUtils;
import com.android.systemui.screenshot.sep.SmartClipDataExtractor;
import com.samsung.android.app.scrollcapture.lib.IScrollCaptureService;
import com.samsung.android.app.scrollcapture.lib.RemoteScrollCaptureInterface;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.sec.ims.ImsManager;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    public final ScreenshotSmartActions mScreenshotSmartActions;
    public RemoteScrollCaptureInterface mScrollCaptureInterface;
    public final Supplier mSharedElementTransition;
    public final ScreenshotNotificationSmartActionsProvider mSmartActionsProvider;
    public final Random mRandom = new Random();
    public final long mScrollCaptureTransactionId = System.currentTimeMillis();
    public boolean mIsScrollCaptureConnectionListenerInvoked = false;
    public boolean mIsSavingFailed = false;
    public final ScreenshotController.SavedImageData mImageData = new ScreenshotController.SavedImageData();
    public final ScreenshotController.QuickShareData mQuickShareData = new ScreenshotController.QuickShareData();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.screenshot.SaveImageInBackgroundTask$1 */
    public final class C23351 {
        public final /* synthetic */ ScreenCaptureHelper val$captureHelper;
        public final /* synthetic */ long val$connectionStartTime;

        public C23351(long j, ScreenCaptureHelper screenCaptureHelper) {
            this.val$connectionStartTime = j;
            this.val$captureHelper = screenCaptureHelper;
        }

        public final void onConnectionResult(boolean z) {
            int i = SaveImageInBackgroundTask.$r8$clinit;
            StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("onConnectionResult : success = ", z, " / elapsed = ");
            m49m.append(System.currentTimeMillis() - this.val$connectionStartTime);
            Log.d("Screenshot", m49m.toString());
            SaveImageInBackgroundTask saveImageInBackgroundTask = SaveImageInBackgroundTask.this;
            if (saveImageInBackgroundTask.mIsSavingFailed) {
                saveImageInBackgroundTask.mScrollCaptureInterface.disconnect();
                SaveImageInBackgroundTask.this.mScrollCaptureInterface = null;
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
                bundle.putBoolean("isSubDisplayCapture", ScreenshotUtils.VALUE_SUB_DISPLAY_POLICY.contains("WATCHFACE") && this.val$captureHelper.capturedDisplayId == 1);
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
                RemoteScrollCaptureInterface remoteScrollCaptureInterface = saveImageInBackgroundTask2.mScrollCaptureInterface;
                long j = saveImageInBackgroundTask2.mScrollCaptureTransactionId;
                String str = saveImageInBackgroundTask2.mImageFilePath;
                remoteScrollCaptureInterface.getClass();
                Log.d("[ScrCap]_RemoteScrollCaptureInterface", "notifyGlobalScreenshotStarted");
                IScrollCaptureService iScrollCaptureService = remoteScrollCaptureInterface.mService;
                if (iScrollCaptureService != null) {
                    try {
                        ((IScrollCaptureService.Stub.Proxy) iScrollCaptureService).onGlobalScreenshotStarted(j, str, bundle);
                    } catch (Exception e) {
                        Log.e("[ScrCap]_RemoteScrollCaptureInterface", "notifyGlobalScreenshotStarted : e=" + e);
                        e.printStackTrace();
                    }
                } else {
                    Log.e("[ScrCap]_RemoteScrollCaptureInterface", "notifyGlobalScreenshotStarted : No service connection");
                }
            } else {
                Log.e("Screenshot", "SaveImageInBackgroundTask : Failed to connect to ScrollCapture service");
                SaveImageInBackgroundTask.this.mScrollCaptureInterface = null;
            }
            RemoteScrollCaptureInterface remoteScrollCaptureInterface2 = SaveImageInBackgroundTask.this.mScrollCaptureInterface;
            if (remoteScrollCaptureInterface2 != null) {
                synchronized (remoteScrollCaptureInterface2) {
                    SaveImageInBackgroundTask saveImageInBackgroundTask3 = SaveImageInBackgroundTask.this;
                    saveImageInBackgroundTask3.mIsScrollCaptureConnectionListenerInvoked = true;
                    saveImageInBackgroundTask3.mScrollCaptureInterface.notify();
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x029c  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x02b9  */
    /* JADX WARN: Removed duplicated region for block: B:61:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x02a5  */
    /* JADX WARN: Type inference failed for: r8v2, types: [com.samsung.android.app.scrollcapture.lib.RemoteScrollCaptureInterface$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SaveImageInBackgroundTask(Context context, FeatureFlags featureFlags, ImageExporter imageExporter, ScreenshotSmartActions screenshotSmartActions, ScreenshotController.SaveImageInBackgroundData saveImageInBackgroundData, Supplier<ScreenshotController.SavedImageData.ActionTransition> supplier, ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider) {
        String str;
        String str2;
        String str3;
        List<ActivityManager.RunningTaskInfo> runningTasks;
        StringBuilder m18m;
        String str4;
        String sb;
        boolean z;
        PackageManager packageManager;
        String string;
        this.mCompressFormat = Bitmap.CompressFormat.PNG;
        this.mBixbyCaptureSharedPackageName = null;
        this.mBixbyCaptureSharedActivityName = null;
        this.mContext = context;
        this.mScreenshotSmartActions = screenshotSmartActions;
        this.mSharedElementTransition = supplier;
        this.mImageExporter = imageExporter;
        if (ScreenshotUtils.VALUE_SUB_DISPLAY_POLICY.contains("WATCHFACE") && saveImageInBackgroundData.captureHelper.capturedDisplayId == 1) {
            str2 = "CoverScreen";
        } else {
            Context context2 = saveImageInBackgroundData.captureHelper.displayContext;
            if (context2 != null && (runningTasks = ((ActivityManager) context2.getSystemService("activity")).getRunningTasks(2)) != null && runningTasks.size() > 0) {
                for (int i = 0; i < runningTasks.size(); i++) {
                    ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(i);
                    if (runningTaskInfo.getDisplayId() == context2.getDisplayId()) {
                        str = runningTaskInfo.topActivity.getPackageName();
                        if (!str.equals(context2.getPackageName())) {
                            break;
                        }
                    }
                }
            }
            str = null;
            if (str != null) {
                PackageManager packageManager2 = context2.getPackageManager();
                try {
                    ApplicationInfo applicationInfo = packageManager2.getApplicationInfo(str, 128);
                    Resources resourcesForApplication = packageManager2.getResourcesForApplication(applicationInfo);
                    Configuration configuration = new Configuration();
                    configuration.locale = new Locale("en");
                    resourcesForApplication.updateConfiguration(configuration, context2.getResources().getDisplayMetrics());
                    str3 = resourcesForApplication.getString(applicationInfo.labelRes);
                } catch (Exception e) {
                    e.printStackTrace();
                    str3 = "";
                }
                if (str3 != null) {
                    str2 = str3.replaceAll("[^\\p{ASCII}]", "").replaceAll(System.getProperty("line.separator"), " ").replaceAll("[\\\\/?%*:|\"<>.]", "");
                    KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m70m("getTopMostApplicationName() : ", str2, "Screenshot");
                }
            }
            Log.d("Screenshot", "getTopMostApplicationName() appName : null");
            str2 = null;
        }
        String format = String.format("Screenshot_%s_%s", new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(System.currentTimeMillis())), str2);
        if (str2 != null && str2.length() == 0) {
            format = format.substring(0, format.length() - 1);
        }
        this.mImageDisplayName = format;
        boolean isFormatPNG = isFormatPNG(this.mContext);
        if (!isFormatPNG) {
            this.mCompressFormat = Bitmap.CompressFormat.JPEG;
        }
        if (isFormatPNG) {
            m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(format);
            str4 = ".png";
        } else {
            m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(format);
            str4 = ".jpg";
        }
        m18m.append(str4);
        String sb2 = m18m.toString();
        this.mImageFileName = sb2;
        String[] screenshotSaveInfo = ScreenshotUtils.getScreenshotSaveInfo(context);
        if (screenshotSaveInfo[0].equals("external_primary")) {
            sb = Environment.getExternalStorageDirectory().toString();
            if (!screenshotSaveInfo[1].isEmpty()) {
                StringBuilder m18m2 = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(sb);
                m18m2.append(File.separator);
                m18m2.append(screenshotSaveInfo[1]);
                sb = m18m2.toString();
            }
        } else {
            StringBuilder sb3 = new StringBuilder();
            String str5 = File.separator;
            sb3.append(str5);
            sb3.append("storage");
            sb3.append(str5);
            sb3.append(screenshotSaveInfo[0]);
            sb = sb3.toString();
            if (!screenshotSaveInfo[1].isEmpty()) {
                StringBuilder m2m = AbstractC0000x2c234b15.m2m(sb, str5);
                m2m.append(screenshotSaveInfo[1]);
                sb = m2m.toString();
            }
        }
        this.mImageFilePath = new File(new File(sb), sb2).getAbsolutePath();
        this.mParams = saveImageInBackgroundData;
        this.mSmartActionsProvider = screenshotNotificationSmartActionsProvider;
        ScreenCaptureHelper screenCaptureHelper = saveImageInBackgroundData.captureHelper;
        this.mIsBixbyCaptureShared = false;
        Bundle bundle = screenCaptureHelper.captureSharedBundle;
        if (screenCaptureHelper.screenCaptureOrigin == 5 && bundle != null && (string = bundle.getString("packageName")) != null) {
            try {
                JSONArray jSONArray = new JSONArray(string);
                if (jSONArray.length() > 0) {
                    this.mBixbyCaptureSharedActivityName = jSONArray.getJSONObject(0).getString("activityName");
                    this.mBixbyCaptureSharedPackageName = jSONArray.getJSONObject(1).getString("packageName");
                }
            } catch (JSONException e2) {
                Log.e("Screenshot", "Exception e : " + e2);
            }
            this.mIsBixbyCaptureShared = true;
        }
        long currentTimeMillis = System.currentTimeMillis();
        final RemoteScrollCaptureInterface remoteScrollCaptureInterface = new RemoteScrollCaptureInterface();
        this.mScrollCaptureInterface = remoteScrollCaptureInterface;
        C23351 c23351 = new C23351(currentTimeMillis, screenCaptureHelper);
        Log.d("[ScrCap]_RemoteScrollCaptureInterface", "connect");
        final long currentTimeMillis2 = System.currentTimeMillis();
        if (remoteScrollCaptureInterface.mService != null) {
            Log.e("[ScrCap]_RemoteScrollCaptureInterface", "connect : Already connected");
            return;
        }
        if (remoteScrollCaptureInterface.mConnection != null) {
            Log.e("[ScrCap]_RemoteScrollCaptureInterface", "connect : Connection already requested");
            return;
        }
        remoteScrollCaptureInterface.mContext = context.getApplicationContext();
        remoteScrollCaptureInterface.mConnectionListener = c23351;
        remoteScrollCaptureInterface.mConnection = new ServiceConnection() { // from class: com.samsung.android.app.scrollcapture.lib.RemoteScrollCaptureInterface.1
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                IScrollCaptureService proxy;
                Log.d("[ScrCap]_RemoteScrollCaptureInterface", "onServiceConnected : Service connected. Elapsed = " + (System.currentTimeMillis() - currentTimeMillis2) + "ms");
                RemoteScrollCaptureInterface remoteScrollCaptureInterface2 = RemoteScrollCaptureInterface.this;
                int i2 = IScrollCaptureService.Stub.$r8$clinit;
                if (iBinder == null) {
                    proxy = null;
                } else {
                    IInterface queryLocalInterface = iBinder.queryLocalInterface("com.samsung.android.app.scrollcapture.lib.IScrollCaptureService");
                    proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof IScrollCaptureService)) ? new IScrollCaptureService.Stub.Proxy(iBinder) : (IScrollCaptureService) queryLocalInterface;
                }
                remoteScrollCaptureInterface2.mService = proxy;
                SaveImageInBackgroundTask.C23351 c233512 = RemoteScrollCaptureInterface.this.mConnectionListener;
                if (c233512 != null) {
                    c233512.onConnectionResult(true);
                }
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                Log.d("[ScrCap]_RemoteScrollCaptureInterface", "onServiceDisconnected : Service disconnected");
                RemoteScrollCaptureInterface.this.mService = null;
            }
        };
        Intent intent = new Intent();
        try {
            packageManager = remoteScrollCaptureInterface.mContext.getPackageManager();
        } catch (PackageManager.NameNotFoundException e3) {
            Log.e("[ScrCap]_RemoteScrollCaptureInterface", "isPackageAvailable : not available. e=" + e3);
        }
        if (packageManager == null) {
            Log.e("[ScrCap]_RemoteScrollCaptureInterface", "isPackageAvailable : Failed to get package manager!");
            z = false;
            if (z) {
                Log.d("[ScrCap]_RemoteScrollCaptureInterface", "ScrollCapture will be binded.");
                intent.setClassName("com.samsung.android.app.scrollcapture", "com.samsung.android.app.scrollcapture.core.ScrollCaptureRemoteService");
            } else {
                Log.d("[ScrCap]_RemoteScrollCaptureInterface", "SmartCapture will be binded.");
                intent.setClassName("com.samsung.android.app.smartcapture", "com.samsung.android.app.scrollcapture.core.ScrollCaptureRemoteService");
            }
            if (remoteScrollCaptureInterface.mContext.bindService(intent, remoteScrollCaptureInterface.mConnection, 1)) {
                Log.e("[ScrCap]_RemoteScrollCaptureInterface", "connect : bindService failed");
                C23351 c233512 = remoteScrollCaptureInterface.mConnectionListener;
                if (c233512 != null) {
                    c233512.onConnectionResult(false);
                }
                remoteScrollCaptureInterface.mContext = null;
                remoteScrollCaptureInterface.mConnection = null;
                remoteScrollCaptureInterface.mService = null;
                remoteScrollCaptureInterface.mConnectionListener = null;
                return;
            }
            return;
        }
        z = packageManager.getPackageInfo("com.samsung.android.app.smartcapture", 0) != null;
        StringBuilder sb4 = new StringBuilder("isPackageAvailable : ");
        sb4.append(z ? "available" : "not available");
        Log.d("[ScrCap]_RemoteScrollCaptureInterface", sb4.toString());
        if (z) {
        }
        if (remoteScrollCaptureInterface.mContext.bindService(intent, remoteScrollCaptureInterface.mConnection, 1)) {
        }
    }

    public static String getSubjectString(long j) {
        return String.format("Screenshot (%s)", DateFormat.getDateTimeInstance().format(new Date(j)));
    }

    public static boolean isFormatPNG(Context context) {
        String str = ScreenshotUtils.VALUE_SUB_DISPLAY_POLICY;
        int semGetMyUserId = UserHandle.semGetMyUserId();
        String string = (150 > semGetMyUserId || 160 < semGetMyUserId) ? Settings.System.getString(context.getContentResolver(), "smart_capture_screenshot_format") : Settings.System.getStringForUser(context.getContentResolver(), "smart_capture_screenshot_format", 0);
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m70m("screenshotFormatValue : ", string, "Screenshot");
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

    public Notification.Action createDeleteAction(Context context, Resources resources, Uri uri, boolean z) {
        return new Notification.Action.Builder(Icon.createWithResource(resources, R.drawable.ic_screenshot_delete), resources.getString(android.R.string.floating_toolbar_open_overflow_description), PendingIntent.getBroadcast(context, this.mContext.getUserId(), new Intent(context, (Class<?>) DeleteScreenshotReceiver.class).putExtra("android:screenshot_uri_id", uri.toString()).putExtra("android:screenshot_id", this.mScreenshotId).putExtra("android:smart_actions_enabled", z).addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE), 1409286144)).build();
    }

    public Supplier<ScreenshotController.SavedImageData.ActionTransition> createEditAction(Context context, Resources resources, Uri uri, boolean z) {
        return new SaveImageInBackgroundTask$$ExternalSyntheticLambda0(this, context, uri, z, resources);
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

    public Supplier<ScreenshotController.SavedImageData.ActionTransition> createShareAction(Context context, Resources resources, Uri uri, boolean z) {
        return new SaveImageInBackgroundTask$$ExternalSyntheticLambda0(this, uri, context, z, resources);
    }

    @Override // android.os.AsyncTask
    public final Object doInBackground(Object[] objArr) {
        Notification.Action queryQuickShareAction;
        boolean z;
        List<ResolveInfo> queryIntentActivities;
        String str;
        List<ActivityManager.RunningTaskInfo> list;
        if (isCancelled()) {
            return null;
        }
        UUID randomUUID = UUID.randomUUID();
        Thread.currentThread().setPriority(10);
        Bitmap bitmap = this.mParams.image;
        this.mScreenshotId = String.format("Screenshot_%s", randomUUID);
        boolean z2 = !(this.mParams.owner != Process.myUserHandle()) && DeviceConfig.getBoolean("systemui", "enable_screenshot_notification_smart_actions", true);
        if (z2) {
            try {
                ScreenshotController.SaveImageInBackgroundData saveImageInBackgroundData = this.mParams;
                if (saveImageInBackgroundData.mQuickShareActionsReadyListener != null && (queryQuickShareAction = queryQuickShareAction(this.mScreenshotId, bitmap, saveImageInBackgroundData.owner, null)) != null) {
                    ScreenshotController.QuickShareData quickShareData = this.mQuickShareData;
                    quickShareData.quickShareAction = queryQuickShareAction;
                    ScreenshotController screenshotController = this.mParams.mQuickShareActionsReadyListener.f$0;
                    screenshotController.getClass();
                    if (quickShareData.quickShareAction != null) {
                        screenshotController.mScreenshotHandler.post(new ScreenshotController$$ExternalSyntheticLambda6(screenshotController, quickShareData, 2));
                    }
                }
            } catch (Exception e) {
                z = true;
                this.mIsSavingFailed = true;
                Log.d("Screenshot", "Failed to store screenshot", e);
                ScreenshotController.SaveImageInBackgroundData saveImageInBackgroundData2 = this.mParams;
                saveImageInBackgroundData2.image = null;
                ScreenshotController.SavedImageData savedImageData = this.mImageData;
                savedImageData.uri = null;
                savedImageData.shareTransition = null;
                savedImageData.editTransition = null;
                savedImageData.smartActions = null;
                savedImageData.quickShareAction = null;
                savedImageData.subject = null;
                this.mQuickShareData.quickShareAction = null;
                ((ScreenshotController$$ExternalSyntheticLambda2) saveImageInBackgroundData2.mActionsReadyListener).onActionsReady(savedImageData);
                this.mParams.finisher.accept(null);
            }
        }
        String lowerCase = ScreenshotUtils.getScreenshotSaveInfo(this.mContext)[0].toLowerCase();
        String[] screenshotSaveInfo = ScreenshotUtils.getScreenshotSaveInfo(this.mContext);
        String str2 = screenshotSaveInfo[1].isEmpty() ? File.separator : screenshotSaveInfo[1];
        Long valueOf = Long.valueOf(System.currentTimeMillis());
        long j = this.mImageTime / 1000;
        String str3 = isFormatPNG(this.mContext) ? "image/png" : "image/jpeg";
        int width = this.mParams.image.getWidth();
        int height = this.mParams.image.getHeight();
        long length = new File(this.mImageFilePath).length();
        boolean z3 = z2;
        Log.i("Screenshot", "doInBackground:  volumeName=" + lowerCase + " relativePath=" + str2 + " mImageFilePath=" + this.mImageFilePath + " mImageDisplayName=" + this.mImageDisplayName + " mImageFileName=" + this.mImageFileName + " imageTime=" + valueOf + " dateSeconds=" + j + " mimeType=" + str3 + " imageWidth=" + width + " imageHeight=" + height + " size=" + length);
        ImageExporter imageExporter = this.mImageExporter;
        String str4 = this.mImageFilePath;
        String str5 = this.mImageDisplayName;
        long longValue = valueOf.longValue();
        SmartClipDataExtractor.WebData webData = this.mParams.webData;
        imageExporter.getClass();
        ImageExporter.mImageFileRelativePath = str2;
        ImageExporter.mVolumeName = lowerCase;
        ImageExporter.mImageFilePath = str4;
        ImageExporter.mImageDisplayName = str5;
        ImageExporter.mImageFileName = str5;
        ImageExporter.mImageTime = longValue;
        ImageExporter.mSecDate = j;
        ImageExporter.mMimeType = str3;
        ImageExporter.mWidth = width;
        ImageExporter.mHeight = height;
        ImageExporter.mSize = length;
        ImageExporter.screenshotsWebData = webData;
        this.mImageExporter.mCompressFormat = this.mCompressFormat;
        JSONObject jSONObject = new JSONObject();
        Context context = this.mContext;
        if (context != null) {
            try {
                list = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(2);
            } catch (Exception e2) {
                Log.e("Screenshot", e2.toString());
                list = null;
            }
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    ComponentName componentName = list.get(i).topActivity;
                    if (componentName != null) {
                        str = componentName.flattenToShortString();
                        break;
                    }
                }
            }
        }
        str = null;
        jSONObject.put("comp", str);
        String encodeToString = Base64.encodeToString(jSONObject.toString().getBytes(), 2);
        this.mImageExporter.getClass();
        ImageExporter.mCapturedAppInfo = encodeToString;
        ImageExporter imageExporter2 = this.mImageExporter;
        ProfileInstaller$$ExternalSyntheticLambda0 profileInstaller$$ExternalSyntheticLambda0 = new ProfileInstaller$$ExternalSyntheticLambda0();
        UserHandle userHandle = this.mParams.owner;
        imageExporter2.getClass();
        ImageExporter.Result result = (ImageExporter.Result) imageExporter2.export(profileInstaller$$ExternalSyntheticLambda0, randomUUID, bitmap, ZonedDateTime.now(), userHandle).get();
        Log.d("Screenshot", "Saved screenshot: " + result);
        Uri uri = result.uri;
        this.mImageTime = result.timestamp;
        ScreenshotSmartActions screenshotSmartActions = this.mScreenshotSmartActions;
        ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider = this.mSmartActionsProvider;
        ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType screenshotSmartActionType = ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType.REGULAR_SMART_ACTIONS;
        UserHandle userHandle2 = this.mParams.owner;
        screenshotSmartActions.getClass();
        CompletableFuture smartActionsFuture = ScreenshotSmartActions.getSmartActionsFuture(bitmap, screenshotNotificationSmartActionsProvider, z3);
        ArrayList arrayList = new ArrayList();
        if (z3) {
            int i2 = DeviceConfig.getInt("systemui", "screenshot_notification_smart_actions_timeout_ms", 1000);
            ScreenshotSmartActions screenshotSmartActions2 = this.mScreenshotSmartActions;
            ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider2 = this.mSmartActionsProvider;
            screenshotSmartActions2.getClass();
            arrayList.addAll(buildSmartActions(this.mContext, ScreenshotSmartActions.getSmartActions(smartActionsFuture, i2, screenshotNotificationSmartActionsProvider2)));
        }
        ScreenshotController.SavedImageData savedImageData2 = this.mImageData;
        savedImageData2.uri = uri;
        savedImageData2.owner = this.mParams.owner;
        savedImageData2.smartActions = arrayList;
        Context context2 = this.mContext;
        savedImageData2.shareTransition = createShareAction(context2, context2.getResources(), uri, z3);
        ScreenshotController.SavedImageData savedImageData3 = this.mImageData;
        Context context3 = this.mContext;
        savedImageData3.editTransition = createEditAction(context3, context3.getResources(), uri, z3);
        ScreenshotController.SavedImageData savedImageData4 = this.mImageData;
        Context context4 = this.mContext;
        createDeleteAction(context4, context4.getResources(), uri, z3);
        savedImageData4.getClass();
        this.mImageData.quickShareAction = createQuickShareAction(this.mQuickShareData.quickShareAction, this.mScreenshotId, uri, this.mImageTime, bitmap, this.mParams.owner);
        this.mImageData.subject = getSubjectString(this.mImageTime);
        ((ScreenshotController$$ExternalSyntheticLambda2) this.mParams.mActionsReadyListener).onActionsReady(this.mImageData);
        this.mParams.finisher.accept(this.mImageData.uri);
        this.mParams.image = null;
        z = true;
        RemoteScrollCaptureInterface remoteScrollCaptureInterface = this.mScrollCaptureInterface;
        if (remoteScrollCaptureInterface != null) {
            synchronized (remoteScrollCaptureInterface) {
                if (!this.mIsScrollCaptureConnectionListenerInvoked) {
                    long currentTimeMillis = System.currentTimeMillis();
                    try {
                        this.mScrollCaptureInterface.wait(1000L);
                    } catch (InterruptedException e3) {
                        Log.e("Screenshot", "doInBackground : Exception thrown during waiting ScrollCapture connection. e=" + e3, e3);
                    }
                    Log.i("Screenshot", "doInBackground : ScrollCapture connection waiting time = " + (System.currentTimeMillis() - currentTimeMillis));
                }
            }
        }
        Bundle bundle = new Bundle();
        bundle.putCharSequenceArrayList("notifiedApps", (ArrayList) this.mParams.notifiedApps);
        RemoteScrollCaptureInterface remoteScrollCaptureInterface2 = this.mScrollCaptureInterface;
        if (remoteScrollCaptureInterface2 != null) {
            long j2 = this.mScrollCaptureTransactionId;
            String str6 = this.mImageFilePath;
            Log.d("[ScrCap]_RemoteScrollCaptureInterface", "notifyGlobalScreenshotFinished");
            IScrollCaptureService iScrollCaptureService = remoteScrollCaptureInterface2.mService;
            if (iScrollCaptureService != null ? z : false) {
                try {
                    ((IScrollCaptureService.Stub.Proxy) iScrollCaptureService).onGlobalScreenshotFinished(j2, str6, bundle);
                } catch (Exception e4) {
                    Log.e("[ScrCap]_RemoteScrollCaptureInterface", "notifyGlobalScreenshotFinished : e=" + e4);
                    e4.printStackTrace();
                }
            } else {
                Log.e("[ScrCap]_RemoteScrollCaptureInterface", "notifyGlobalScreenshotFinished : No service connection");
            }
            this.mScrollCaptureInterface.disconnect();
        }
        if (this.mIsSavingFailed) {
            Log.e("Screenshot", "Failed to save screenshot");
        } else if (this.mIsBixbyCaptureShared) {
            Context context5 = this.mContext;
            String str7 = this.mBixbyCaptureSharedPackageName;
            String str8 = this.mBixbyCaptureSharedActivityName;
            Uri uri2 = this.mImageData.uri;
            StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("startChooserActivity packageName : ", str7, ", activityName : ", str8, ", imageUri : ");
            m87m.append(uri2);
            Log.i("Screenshot", m87m.toString());
            if (uri2 != null) {
                Intent intent = new Intent("android.intent.action.SEND");
                boolean isFormatPNG = isFormatPNG(context5);
                if (str7 != null) {
                    if (str8 != null) {
                        intent.setComponent(new ComponentName(str7, str8));
                    } else {
                        intent.setPackage(str7);
                    }
                }
                intent.setType(isFormatPNG ? "image/png" : "image/jpeg");
                intent.putExtra("android.intent.extra.STREAM", uri2);
                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                intent.addFlags(185073665);
                if (str7 != null) {
                    PackageManager packageManager = context5.getPackageManager();
                    if (packageManager == null || (queryIntentActivities = packageManager.queryIntentActivities(intent, 0)) == null || queryIntentActivities.size() <= 0) {
                        z = false;
                    }
                    if (z) {
                        context5.startActivity(intent);
                    }
                }
                Intent intent2 = new Intent("android.intent.action.SEND");
                intent2.setType(isFormatPNG ? "image/png" : "image/jpeg");
                intent2.putExtra("android.intent.extra.STREAM", uri2);
                Intent createChooser = Intent.createChooser(intent2, null);
                createChooser.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                createChooser.addFlags(185073665);
                context5.startActivity(createChooser);
                return null;
            }
        }
        return null;
    }

    @Override // android.os.AsyncTask
    public final void onCancelled(Object obj) {
        ScreenshotController.SavedImageData savedImageData = this.mImageData;
        savedImageData.uri = null;
        savedImageData.shareTransition = null;
        savedImageData.editTransition = null;
        savedImageData.smartActions = null;
        savedImageData.quickShareAction = null;
        savedImageData.subject = null;
        this.mQuickShareData.quickShareAction = null;
        ((ScreenshotController$$ExternalSyntheticLambda2) this.mParams.mActionsReadyListener).onActionsReady(savedImageData);
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
