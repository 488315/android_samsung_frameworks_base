package android.content.pm;

import android.annotation.SystemApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;

import com.android.internal.R;
import com.android.internal.util.UserIcons;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CrossProfileApps {
    public static final String ACTION_CAN_INTERACT_ACROSS_PROFILES_CHANGED =
            "android.content.pm.action.CAN_INTERACT_ACROSS_PROFILES_CHANGED";
    private final Context mContext;
    private final Resources mResources;
    private final ICrossProfileApps mService;
    private final UserManager mUserManager;

    public CrossProfileApps(Context context, ICrossProfileApps service) {
        this.mContext = context;
        this.mService = service;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mResources = context.getResources();
    }

    public void startMainActivity(ComponentName component, UserHandle targetUser) {
        try {
            this.mService.startActivityAsUser(
                    this.mContext.getIApplicationThread(),
                    this.mContext.getPackageName(),
                    this.mContext.getAttributionTag(),
                    component,
                    targetUser.getIdentifier(),
                    true,
                    this.mContext.getActivityToken(),
                    ActivityOptions.makeBasic().toBundle());
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public void startMainActivity(
            ComponentName component,
            UserHandle targetUser,
            Activity callingActivity,
            Bundle options) {
        try {
            this.mService.startActivityAsUser(
                    this.mContext.getIApplicationThread(),
                    this.mContext.getPackageName(),
                    this.mContext.getAttributionTag(),
                    component,
                    targetUser.getIdentifier(),
                    true,
                    callingActivity != null ? callingActivity.getActivityToken() : null,
                    options);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public void startActivity(Intent intent, UserHandle targetUser, Activity callingActivity) {
        startActivity(intent, targetUser, callingActivity, (Bundle) null);
    }

    public void startActivity(
            Intent intent, UserHandle targetUser, Activity callingActivity, Bundle options) {
        try {
            this.mService.startActivityAsUserByIntent(
                    this.mContext.getIApplicationThread(),
                    this.mContext.getPackageName(),
                    this.mContext.getAttributionTag(),
                    intent,
                    targetUser.getIdentifier(),
                    callingActivity != null ? callingActivity.getActivityToken() : null,
                    options);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void startActivity(
            ComponentName component,
            UserHandle targetUser,
            Activity callingActivity,
            Bundle options) {
        try {
            this.mService.startActivityAsUser(
                    this.mContext.getIApplicationThread(),
                    this.mContext.getPackageName(),
                    this.mContext.getAttributionTag(),
                    component,
                    targetUser.getIdentifier(),
                    false,
                    callingActivity != null ? callingActivity.getActivityToken() : null,
                    options);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void startActivity(ComponentName component, UserHandle targetUser) {
        try {
            this.mService.startActivityAsUser(
                    this.mContext.getIApplicationThread(),
                    this.mContext.getPackageName(),
                    this.mContext.getAttributionTag(),
                    component,
                    targetUser.getIdentifier(),
                    false,
                    null,
                    null);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public List<UserHandle> getTargetUserProfiles() {
        try {
            return this.mService.getTargetUserProfiles(this.mContext.getPackageName());
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public boolean isProfile(UserHandle userHandle) {
        verifyCanAccessUser(userHandle);
        return this.mUserManager.isProfile(userHandle.getIdentifier());
    }

    public boolean isManagedProfile(UserHandle userHandle) {
        verifyCanAccessUser(userHandle);
        return this.mUserManager.isManagedProfile(userHandle.getIdentifier());
    }

    public CharSequence getProfileSwitchingLabel(UserHandle userHandle) {
        verifyCanAccessUser(userHandle);
        final boolean isManagedProfile =
                this.mUserManager.isManagedProfile(userHandle.getIdentifier());
        DevicePolicyManager dpm =
                (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
        final String callingAppLabel = getCallingApplicationLabel().toString();
        return dpm.getResources()
                .getString(
                        getUpdatableProfileSwitchingLabelId(isManagedProfile),
                        new Supplier() { // from class:
                            // android.content.pm.CrossProfileApps$$ExternalSyntheticLambda1
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                String lambda$getProfileSwitchingLabel$0;
                                lambda$getProfileSwitchingLabel$0 =
                                        CrossProfileApps.this.lambda$getProfileSwitchingLabel$0(
                                                isManagedProfile, callingAppLabel);
                                return lambda$getProfileSwitchingLabel$0;
                            }
                        },
                        callingAppLabel);
    }

    private CharSequence getCallingApplicationLabel() {
        PackageManager pm = this.mContext.getPackageManager();
        Intent launchIntent = pm.getLaunchIntentForPackage(this.mContext.getPackageName());
        if (launchIntent == null) {
            return getDefaultCallingApplicationLabel();
        }
        List<ResolveInfo> infos =
                pm.queryIntentActivities(launchIntent, PackageManager.ResolveInfoFlags.of(65536L));
        if (infos.size() > 0) {
            return infos.get(0).loadLabel(pm);
        }
        return getDefaultCallingApplicationLabel();
    }

    private CharSequence getDefaultCallingApplicationLabel() {
        return this.mContext
                .getApplicationInfo()
                .loadSafeLabel(this.mContext.getPackageManager(), 0.0f, 3);
    }

    private String getUpdatableProfileSwitchingLabelId(boolean isManagedProfile) {
        return isManagedProfile
                ? DevicePolicyResources.Strings.Core.SWITCH_TO_WORK_LABEL
                : DevicePolicyResources.Strings.Core.SWITCH_TO_PERSONAL_LABEL;
    }

    /* renamed from: getDefaultProfileSwitchingLabel, reason: merged with bridge method [inline-methods] */
    public String lambda$getProfileSwitchingLabel$0(boolean isManagedProfile, String label) {
        int stringRes =
                isManagedProfile
                        ? R.string.managed_profile_app_label
                        : R.string.user_owner_app_label;
        return this.mResources.getString(stringRes, label);
    }

    public Drawable getProfileSwitchingIconDrawable(UserHandle userHandle) {
        int colorId;
        verifyCanAccessUser(userHandle);
        boolean isManagedProfile = this.mUserManager.isManagedProfile(userHandle.getIdentifier());
        if (isManagedProfile) {
            return this.mContext
                    .getPackageManager()
                    .getUserBadgeForDensityNoBackground(userHandle, 0);
        }
        Drawable personalProfileIcon = UserIcons.getDefaultUserIcon(this.mResources, 0, true);
        if (this.mContext.getResources().getConfiguration().isNightModeActive()) {
            colorId = R.color.profile_badge_1_dark;
        } else {
            colorId = R.color.profile_badge_1;
        }
        personalProfileIcon.setColorFilter(null);
        personalProfileIcon.setTint(this.mResources.getColor(colorId, null));
        return personalProfileIcon;
    }

    public boolean canRequestInteractAcrossProfiles() {
        try {
            return this.mService.canRequestInteractAcrossProfiles(this.mContext.getPackageName());
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public boolean canInteractAcrossProfiles() {
        try {
            return this.mService.canInteractAcrossProfiles(this.mContext.getPackageName());
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public Intent createRequestInteractAcrossProfilesIntent() {
        if (!canRequestInteractAcrossProfiles()) {
            throw new SecurityException(
                    "The calling package can not request to interact across profiles.");
        }
        Intent settingsIntent = new Intent();
        settingsIntent.setAction(Settings.ACTION_MANAGE_CROSS_PROFILE_ACCESS);
        Uri packageUri = Uri.parse("package:" + this.mContext.getPackageName());
        settingsIntent.setData(packageUri);
        return settingsIntent;
    }

    public void setInteractAcrossProfilesAppOp(String packageName, int newMode) {
        try {
            this.mService.setInteractAcrossProfilesAppOp(
                    this.mContext.getUserId(), packageName, newMode);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public boolean canConfigureInteractAcrossProfiles(String packageName) {
        try {
            return this.mService.canConfigureInteractAcrossProfiles(
                    this.mContext.getUserId(), packageName);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public boolean canUserAttemptToConfigureInteractAcrossProfiles(String packageName) {
        try {
            return this.mService.canUserAttemptToConfigureInteractAcrossProfiles(
                    this.mContext.getUserId(), packageName);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    public void resetInteractAcrossProfilesAppOps(
            Collection<String> previousCrossProfilePackages,
            final Set<String> newCrossProfilePackages) {
        if (previousCrossProfilePackages.isEmpty()) {
            return;
        }
        List<String> unsetCrossProfilePackages =
                (List)
                        previousCrossProfilePackages.stream()
                                .filter(
                                        new Predicate() { // from class:
                                            // android.content.pm.CrossProfileApps$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return CrossProfileApps
                                                        .lambda$resetInteractAcrossProfilesAppOps$1(
                                                                newCrossProfilePackages,
                                                                (String) obj);
                                            }
                                        })
                                .collect(Collectors.toList());
        if (unsetCrossProfilePackages.isEmpty()) {
            return;
        }
        try {
            this.mService.resetInteractAcrossProfilesAppOps(
                    this.mContext.getUserId(), unsetCrossProfilePackages);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ boolean lambda$resetInteractAcrossProfilesAppOps$1(
            Set newCrossProfilePackages, String packageName) {
        return !newCrossProfilePackages.contains(packageName);
    }

    public void clearInteractAcrossProfilesAppOps() {
        try {
            this.mService.clearInteractAcrossProfilesAppOps(this.mContext.getUserId());
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    private void verifyCanAccessUser(UserHandle userHandle) {
        if (!getTargetUserProfiles().contains(userHandle)) {
            throw new SecurityException("Not allowed to access " + userHandle);
        }
    }
}
