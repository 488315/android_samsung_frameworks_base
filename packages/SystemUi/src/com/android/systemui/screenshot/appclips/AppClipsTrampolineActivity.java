package com.android.systemui.screenshot.appclips;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.p038wm.shell.bubbles.BubbleController;
import com.android.p038wm.shell.bubbles.Bubbles;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.notetask.NoteTaskController;
import com.android.systemui.notetask.NoteTaskEntryPoint;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.Optional;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class AppClipsTrampolineActivity extends Activity {
    public final DevicePolicyManager mDevicePolicyManager;
    public final FeatureFlags mFeatureFlags;
    public Intent mKillAppClipsBroadcastIntent;
    public final NoteTaskController mNoteTaskController;
    public UserHandle mNotesAppUser;
    public final Optional mOptionalBubbles;
    public final PackageManager mPackageManager;
    public final ResultReceiver mResultReceiver;
    public final UiEventLogger mUiEventLogger;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public static final String EXTRA_SCREENSHOT_URI = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m("AppClipsTrampolineActivity", "SCREENSHOT_URI");
    static final String EXTRA_USE_WP_USER = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m("AppClipsTrampolineActivity", "USE_WP_USER");
    public static final String ACTION_FINISH_FROM_TRAMPOLINE = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m("AppClipsTrampolineActivity", "FINISH_FROM_TRAMPOLINE");
    public static final String EXTRA_RESULT_RECEIVER = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m("AppClipsTrampolineActivity", "RESULT_RECEIVER");
    public static final String EXTRA_CALLING_PACKAGE_NAME = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m("AppClipsTrampolineActivity", "CALLING_PACKAGE_NAME");
    public static final PackageManager.ApplicationInfoFlags APPLICATION_INFO_FLAGS = PackageManager.ApplicationInfoFlags.of(0);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            appClipsTrampolineActivity.mNoteTaskController.showNoteTaskAsUser(NoteTaskEntryPoint.APP_CLIPS, appClipsTrampolineActivity.mNotesAppUser);
            AppClipsTrampolineActivity.this.setResult(-1, intent);
            AppClipsTrampolineActivity.this.finish();
        }
    }

    public AppClipsTrampolineActivity(DevicePolicyManager devicePolicyManager, FeatureFlags featureFlags, Optional<Bubbles> optional, NoteTaskController noteTaskController, PackageManager packageManager, UserTracker userTracker, UiEventLogger uiEventLogger, UserManager userManager, Handler handler) {
        this.mDevicePolicyManager = devicePolicyManager;
        this.mFeatureFlags = featureFlags;
        this.mOptionalBubbles = optional;
        this.mNoteTaskController = noteTaskController;
        this.mPackageManager = packageManager;
        this.mUserTracker = userTracker;
        this.mUiEventLogger = uiEventLogger;
        this.mUserManager = userManager;
        AppClipsResultReceiver appClipsResultReceiver = new AppClipsResultReceiver(handler);
        Parcel obtain = Parcel.obtain();
        appClipsResultReceiver.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        ResultReceiver resultReceiver = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(obtain);
        obtain.recycle();
        this.mResultReceiver = resultReceiver;
    }

    public ResultReceiver getResultReceiverForTest() {
        return this.mResultReceiver;
    }

    public final void logScreenshotTriggeredUiEvent(String str) {
        int i;
        try {
            i = this.mPackageManager.getApplicationInfoAsUser(str, APPLICATION_INFO_FLAGS, this.mNotesAppUser.getIdentifier()).uid;
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
        UserManager userManager = this.mUserManager;
        if (userManager.isManagedProfile()) {
            UserHandle mainUser = userManager.getMainUser();
            if (mainUser == null) {
                setErrorResultAndFinish(1);
            } else {
                startActivityAsUser(new Intent(this, (Class<?>) AppClipsTrampolineActivity.class).putExtra(EXTRA_USE_WP_USER, true).addFlags(QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING), mainUser);
            }
            finish();
            return;
        }
        if (!((FeatureFlagsRelease) this.mFeatureFlags).isEnabled(Flags.SCREENSHOT_APP_CLIPS)) {
            finish();
            return;
        }
        Optional optional = this.mOptionalBubbles;
        if (optional.isEmpty()) {
            setErrorResultAndFinish(1);
            return;
        }
        if (!((BubbleController.BubblesImpl) ((Bubbles) optional.get())).mCachedState.mAppBubbleTaskIds.values().contains(Integer.valueOf(getTaskId()))) {
            setErrorResultAndFinish(3);
            return;
        }
        if (this.mDevicePolicyManager.getScreenCaptureDisabled(null)) {
            setErrorResultAndFinish(4);
            return;
        }
        try {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(getString(R.string.config_screenshotAppClipsActivityComponent));
            if (unflattenFromString == null || unflattenFromString.getPackageName().isEmpty() || unflattenFromString.getClassName().isEmpty()) {
                setErrorResultAndFinish(1);
                return;
            }
            this.mNotesAppUser = getUser();
            if (getIntent().getBooleanExtra(EXTRA_USE_WP_USER, false)) {
                this.mNotesAppUser = (UserHandle) ((UserTrackerImpl) this.mUserTracker).getUserProfiles().stream().filter(new Predicate() { // from class: com.android.systemui.screenshot.appclips.AppClipsTrampolineActivity$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return AppClipsTrampolineActivity.this.mUserManager.isManagedProfile(((UserInfo) obj).id);
                    }
                }).findFirst().map(new AppClipsTrampolineActivity$$ExternalSyntheticLambda1()).orElse(this.mNotesAppUser);
            }
            String callingPackage = getCallingPackage();
            try {
                startActivityAsUser(new Intent().setComponent(unflattenFromString).addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE).putExtra(EXTRA_RESULT_RECEIVER, this.mResultReceiver).putExtra(EXTRA_CALLING_PACKAGE_NAME, callingPackage), this.mNotesAppUser);
                this.mKillAppClipsBroadcastIntent = new Intent(ACTION_FINISH_FROM_TRAMPOLINE).setComponent(unflattenFromString).setPackage(unflattenFromString.getPackageName());
                logScreenshotTriggeredUiEvent(callingPackage);
            } catch (ActivityNotFoundException unused) {
                setErrorResultAndFinish(1);
            }
        } catch (Resources.NotFoundException unused2) {
            setErrorResultAndFinish(1);
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Intent intent;
        super.onDestroy();
        if (!isFinishing() || (intent = this.mKillAppClipsBroadcastIntent) == null) {
            return;
        }
        sendBroadcast(intent, "com.android.systemui.permission.SELF");
    }

    public final void setErrorResultAndFinish(int i) {
        setResult(-1, new Intent().putExtra("android.intent.extra.CAPTURE_CONTENT_FOR_NOTE_STATUS_CODE", i));
        finish();
    }
}
