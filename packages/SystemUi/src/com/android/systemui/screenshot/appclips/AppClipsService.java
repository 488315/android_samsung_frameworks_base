package com.android.systemui.screenshot.appclips;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.os.UserHandle;
import android.os.UserManager;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.android.internal.infra.ServiceConnector;
import com.android.internal.statusbar.IAppClipsService;
import com.android.p038wm.shell.bubbles.BubbleController;
import com.android.p038wm.shell.bubbles.Bubbles;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class AppClipsService extends Service {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final boolean mAreTaskAndTimeIndependentPrerequisitesMet;
    public final DevicePolicyManager mDevicePolicyManager;
    public final Optional mOptionalBubbles;
    ServiceConnector<IAppClipsService> mProxyConnectorToMainProfile;
    public final UserManager mUserManager;

    public AppClipsService(Context context, FeatureFlags featureFlags, Optional<Bubbles> optional, DevicePolicyManager devicePolicyManager, UserManager userManager) {
        this.mOptionalBubbles = optional;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mUserManager = userManager;
        boolean z = false;
        if (userManager.isManagedProfile()) {
            this.mAreTaskAndTimeIndependentPrerequisitesMet = false;
            UserHandle mainUser = userManager.getMainUser();
            if (mainUser == null) {
                return;
            }
            this.mProxyConnectorToMainProfile = new ServiceConnector.Impl(context, new Intent(context, (Class<?>) AppClipsService.class), 1073741857, mainUser.getIdentifier(), new AppClipsService$$ExternalSyntheticLambda0());
            return;
        }
        if (((FeatureFlagsRelease) featureFlags).isEnabled(Flags.SCREENSHOT_APP_CLIPS) && !optional.isEmpty()) {
            try {
                ComponentName unflattenFromString = ComponentName.unflattenFromString(context.getString(R.string.config_screenshotAppClipsActivityComponent));
                if (unflattenFromString != null && !unflattenFromString.getPackageName().isEmpty() && !unflattenFromString.getClassName().isEmpty()) {
                    z = true;
                }
            } catch (Resources.NotFoundException unused) {
            }
        }
        this.mAreTaskAndTimeIndependentPrerequisitesMet = z;
        this.mProxyConnectorToMainProfile = null;
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new IAppClipsService.Stub() { // from class: com.android.systemui.screenshot.appclips.AppClipsService.1
            public final boolean canLaunchCaptureContentActivityForNote(final int i) {
                if (!AppClipsService.this.mUserManager.isManagedProfile()) {
                    AppClipsService appClipsService = AppClipsService.this;
                    if (appClipsService.mAreTaskAndTimeIndependentPrerequisitesMet && ((BubbleController.BubblesImpl) ((Bubbles) appClipsService.mOptionalBubbles.get())).mCachedState.mAppBubbleTaskIds.values().contains(Integer.valueOf(i))) {
                        return !AppClipsService.this.mDevicePolicyManager.getScreenCaptureDisabled(null);
                    }
                    return false;
                }
                ServiceConnector<IAppClipsService> serviceConnector = AppClipsService.this.mProxyConnectorToMainProfile;
                if (serviceConnector == null) {
                    return false;
                }
                try {
                    return ((Boolean) serviceConnector.postForResult(new ServiceConnector.Job() { // from class: com.android.systemui.screenshot.appclips.AppClipsService$$ExternalSyntheticLambda1
                        public final Object run(Object obj) {
                            int i2 = i;
                            int i3 = AppClipsService.$r8$clinit;
                            return Boolean.valueOf(((IAppClipsService) obj).canLaunchCaptureContentActivityForNote(i2));
                        }
                    }).get()).booleanValue();
                } catch (InterruptedException | ExecutionException e) {
                    AbsAdapter$1$$ExternalSyntheticOutline0.m39m("Exception from service\n", e, "AppClipsService");
                    return false;
                }
            }
        };
    }
}
