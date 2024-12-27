package com.android.systemui.screenshot.appclips;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.util.Log;
import com.android.internal.statusbar.IAppClipsService;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.Optional;

public class AppClipsService extends Service {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final boolean mAreTaskAndTimeIndependentPrerequisitesMet;
    public final DevicePolicyManager mDevicePolicyManager;
    public final Optional mOptionalBubbles;

    public AppClipsService(Context context, FeatureFlags featureFlags, Optional<Bubbles> optional, DevicePolicyManager devicePolicyManager) {
        this.mOptionalBubbles = optional;
        this.mDevicePolicyManager = devicePolicyManager;
        boolean z = false;
        if (!((FeatureFlagsClassicRelease) featureFlags).isEnabled(Flags.SCREENSHOT_APP_CLIPS)) {
            Log.d("AppClipsService", "Feature flag disabled");
        } else if (optional.isEmpty()) {
            Log.d("AppClipsService", "Bubbles not available");
        } else {
            try {
                ComponentName unflattenFromString = ComponentName.unflattenFromString(context.getString(R.string.config_screenshotAppClipsActivityComponent));
                if (unflattenFromString == null) {
                    Log.d("AppClipsService", "AppClips component name not defined");
                } else if (unflattenFromString.getPackageName().isEmpty()) {
                    Log.d("AppClipsService", "AppClips component package name is empty");
                } else if (unflattenFromString.getClassName().isEmpty()) {
                    Log.d("AppClipsService", "AppClips component class name is empty");
                } else {
                    Log.d("AppClipsService", "isComponentValid returned true");
                    Log.d("AppClipsService", "checkIndependentVariables returned true");
                    z = true;
                }
            } catch (Resources.NotFoundException unused) {
                Log.d("AppClipsService", "AppClips activity component resource not defined");
            }
            Log.d("AppClipsService", "checkIndependentVariables returned false");
        }
        this.mAreTaskAndTimeIndependentPrerequisitesMet = z;
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new IAppClipsService.Stub() { // from class: com.android.systemui.screenshot.appclips.AppClipsService.1
            public final boolean canLaunchCaptureContentActivityForNote(int i) {
                if (canLaunchCaptureContentActivityForNoteInternal(i) == 0) {
                    int i2 = AppClipsService.$r8$clinit;
                    Log.d("AppClipsService", String.format("Can launch AppClips returned true for %d", Integer.valueOf(i)));
                    return true;
                }
                int i3 = AppClipsService.$r8$clinit;
                Log.d("AppClipsService", String.format("Can launch AppClips returned false for %d", Integer.valueOf(i)));
                return false;
            }

            public final int canLaunchCaptureContentActivityForNoteInternal(int i) {
                AppClipsService appClipsService = AppClipsService.this;
                if (!appClipsService.mAreTaskAndTimeIndependentPrerequisitesMet) {
                    int i2 = AppClipsService.$r8$clinit;
                    Log.d("AppClipsService", String.format("Task (%d) and time independent prereqs not met", Integer.valueOf(i)));
                    return 1;
                }
                if (!((BubbleController.BubblesImpl) ((Bubbles) appClipsService.mOptionalBubbles.get())).mCachedState.mAppBubbleTaskIds.values().contains(Integer.valueOf(i))) {
                    int i3 = AppClipsService.$r8$clinit;
                    Log.d("AppClipsService", String.format("Taskid %d is not app bubble task", Integer.valueOf(i)));
                    return 3;
                }
                if (AppClipsService.this.mDevicePolicyManager.getScreenCaptureDisabled(null)) {
                    int i4 = AppClipsService.$r8$clinit;
                    Log.d("AppClipsService", String.format("Screen capture disabled by admin, taskId %d", Integer.valueOf(i)));
                    return 4;
                }
                int i5 = AppClipsService.$r8$clinit;
                Log.d("AppClipsService", String.format("Can launch AppClips (internal) successful for %d", Integer.valueOf(i)));
                return 0;
            }
        };
    }
}
