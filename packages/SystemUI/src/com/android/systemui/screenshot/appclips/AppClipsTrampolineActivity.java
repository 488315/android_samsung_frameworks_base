package com.android.systemui.screenshot.appclips;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.infra.ServiceConnector;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IAppClipsService;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.notetask.NoteTaskController;
import com.android.systemui.notetask.NoteTaskEntryPoint;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

public class AppClipsTrampolineActivity extends Activity {
    public final ServiceConnector mAppClipsServiceConnector;
    public final Executor mBgExecutor;
    public final BroadcastSender mBroadcastSender;
    public Intent mKillAppClipsBroadcastIntent;
    public final Executor mMainExecutor;
    public final NoteTaskController mNoteTaskController;
    public final PackageManager mPackageManager;
    public final ResultReceiver mResultReceiver;
    public final UiEventLogger mUiEventLogger;
    public UserHandle mUserHandle;
    public static final String EXTRA_SCREENSHOT_URI = AbstractResolvableFuture$$ExternalSyntheticOutline0.m("AppClipsTrampolineActivity", "SCREENSHOT_URI");
    public static final String ACTION_FINISH_FROM_TRAMPOLINE = AbstractResolvableFuture$$ExternalSyntheticOutline0.m("AppClipsTrampolineActivity", "FINISH_FROM_TRAMPOLINE");
    public static final String EXTRA_RESULT_RECEIVER = AbstractResolvableFuture$$ExternalSyntheticOutline0.m("AppClipsTrampolineActivity", "RESULT_RECEIVER");
    public static final String EXTRA_CALLING_PACKAGE_NAME = AbstractResolvableFuture$$ExternalSyntheticOutline0.m("AppClipsTrampolineActivity", "CALLING_PACKAGE_NAME");
    public static final PackageManager.ApplicationInfoFlags APPLICATION_INFO_FLAGS = PackageManager.ApplicationInfoFlags.of(0);

    public final class AppClipsResultReceiver extends ResultReceiver {
        public AppClipsResultReceiver(Handler handler) {
            super(handler);
        }

        @Override // android.os.ResultReceiver
        public final void onReceiveResult(int i, Bundle bundle) {
            if (AppClipsTrampolineActivity.this.isFinishing()) {
                return;
            }
            Intent intent = new Intent();
            int i2 = bundle != null ? bundle.getInt("android.intent.extra.CAPTURE_CONTENT_FOR_NOTE_STATUS_CODE", 1) : 1;
            intent.putExtra("android.intent.extra.CAPTURE_CONTENT_FOR_NOTE_STATUS_CODE", i2);
            if (i2 == 0) {
                intent.setData((Uri) bundle.getParcelable(AppClipsTrampolineActivity.EXTRA_SCREENSHOT_URI, Uri.class));
            }
            AppClipsTrampolineActivity appClipsTrampolineActivity = AppClipsTrampolineActivity.this;
            appClipsTrampolineActivity.mKillAppClipsBroadcastIntent = null;
            appClipsTrampolineActivity.mNoteTaskController.showNoteTaskAsUser(NoteTaskEntryPoint.APP_CLIPS, appClipsTrampolineActivity.mUserHandle);
            AppClipsTrampolineActivity.this.setResult(-1, intent);
            AppClipsTrampolineActivity.this.finish();
        }
    }

    public AppClipsTrampolineActivity(Context context, NoteTaskController noteTaskController, PackageManager packageManager, UiEventLogger uiEventLogger, BroadcastSender broadcastSender, Executor executor, Executor executor2, Handler handler) {
        this.mNoteTaskController = noteTaskController;
        this.mPackageManager = packageManager;
        this.mUiEventLogger = uiEventLogger;
        this.mBroadcastSender = broadcastSender;
        this.mBgExecutor = executor;
        this.mMainExecutor = executor2;
        AppClipsResultReceiver appClipsResultReceiver = new AppClipsResultReceiver(handler);
        Parcel obtain = Parcel.obtain();
        appClipsResultReceiver.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        ResultReceiver resultReceiver = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(obtain);
        obtain.recycle();
        this.mResultReceiver = resultReceiver;
        this.mAppClipsServiceConnector = new ServiceConnector.Impl(context, new Intent(context, (Class<?>) AppClipsService.class), 1073741857, 0, new AppClipsTrampolineActivity$$ExternalSyntheticLambda1());
    }

    public ResultReceiver getResultReceiverForTest() {
        return this.mResultReceiver;
    }

    public final void logScreenshotTriggeredUiEvent(String str) {
        int i;
        try {
            i = this.mPackageManager.getApplicationInfoAsUser(str, APPLICATION_INFO_FLAGS, this.mUserHandle.getIdentifier()).uid;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("AppClipsTrampolineActivity", "Couldn't find notes app UID " + e);
            i = 0;
        }
        this.mUiEventLogger.log(AppClipsEvent.SCREENSHOT_FOR_NOTE_TRIGGERED, i, str);
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            return;
        }
        this.mUserHandle = getUser();
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.appclips.AppClipsTrampolineActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                final AppClipsTrampolineActivity appClipsTrampolineActivity = AppClipsTrampolineActivity.this;
                appClipsTrampolineActivity.mAppClipsServiceConnector.postForResult(new ServiceConnector.Job() { // from class: com.android.systemui.screenshot.appclips.AppClipsTrampolineActivity$$ExternalSyntheticLambda2
                    public final Object run(Object obj) {
                        AppClipsTrampolineActivity appClipsTrampolineActivity2 = AppClipsTrampolineActivity.this;
                        String str = AppClipsTrampolineActivity.EXTRA_SCREENSHOT_URI;
                        return Integer.valueOf(((IAppClipsService) obj).canLaunchCaptureContentActivityForNoteInternal(appClipsTrampolineActivity2.getTaskId()));
                    }
                }).whenCompleteAsync(new BiConsumer() { // from class: com.android.systemui.screenshot.appclips.AppClipsTrampolineActivity$$ExternalSyntheticLambda3
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        AppClipsTrampolineActivity appClipsTrampolineActivity2 = AppClipsTrampolineActivity.this;
                        int intValue = ((Integer) obj).intValue();
                        Throwable th = (Throwable) obj2;
                        String str = AppClipsTrampolineActivity.EXTRA_SCREENSHOT_URI;
                        if (appClipsTrampolineActivity2.isFinishing()) {
                            return;
                        }
                        if (th != null) {
                            Log.d("AppClipsTrampolineActivity", "Error querying app clips service", th);
                            appClipsTrampolineActivity2.setErrorResultAndFinish(intValue);
                        } else {
                            if (intValue != 0) {
                                appClipsTrampolineActivity2.setErrorResultAndFinish(intValue);
                                return;
                            }
                            ComponentName unflattenFromString = ComponentName.unflattenFromString(appClipsTrampolineActivity2.getString(R.string.config_screenshotAppClipsActivityComponent));
                            String callingPackage = appClipsTrampolineActivity2.getCallingPackage();
                            try {
                                appClipsTrampolineActivity2.startActivity(new Intent().setComponent(unflattenFromString).addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE).putExtra(AppClipsTrampolineActivity.EXTRA_RESULT_RECEIVER, appClipsTrampolineActivity2.mResultReceiver).putExtra(AppClipsTrampolineActivity.EXTRA_CALLING_PACKAGE_NAME, callingPackage));
                                appClipsTrampolineActivity2.mKillAppClipsBroadcastIntent = new Intent(AppClipsTrampolineActivity.ACTION_FINISH_FROM_TRAMPOLINE).setComponent(unflattenFromString).setPackage(unflattenFromString.getPackageName());
                                appClipsTrampolineActivity2.logScreenshotTriggeredUiEvent(callingPackage);
                            } catch (ActivityNotFoundException unused) {
                                appClipsTrampolineActivity2.setErrorResultAndFinish(1);
                            }
                        }
                    }
                }, appClipsTrampolineActivity.mMainExecutor);
            }
        });
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Intent intent;
        if (isFinishing() && (intent = this.mKillAppClipsBroadcastIntent) != null) {
            this.mBroadcastSender.sendBroadcast$1(intent);
        }
        super.onDestroy();
    }

    public final void setErrorResultAndFinish(int i) {
        setResult(-1, new Intent().putExtra("android.intent.extra.CAPTURE_CONTENT_FOR_NOTE_STATUS_CODE", i));
        finish();
    }

    public AppClipsTrampolineActivity(ServiceConnector<IAppClipsService> serviceConnector, NoteTaskController noteTaskController, PackageManager packageManager, UiEventLogger uiEventLogger, BroadcastSender broadcastSender, Executor executor, Executor executor2, Handler handler) {
        this.mAppClipsServiceConnector = serviceConnector;
        this.mNoteTaskController = noteTaskController;
        this.mPackageManager = packageManager;
        this.mUiEventLogger = uiEventLogger;
        this.mBroadcastSender = broadcastSender;
        this.mBgExecutor = executor;
        this.mMainExecutor = executor2;
        AppClipsResultReceiver appClipsResultReceiver = new AppClipsResultReceiver(handler);
        Parcel obtain = Parcel.obtain();
        appClipsResultReceiver.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        ResultReceiver resultReceiver = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(obtain);
        obtain.recycle();
        this.mResultReceiver = resultReceiver;
    }
}
