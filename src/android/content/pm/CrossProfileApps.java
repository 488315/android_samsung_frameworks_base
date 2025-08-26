package android.content.pm;

import android.annotation.SystemApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

/* loaded from: classes.dex */
public class CrossProfileApps {
    public static final String ACTION_CAN_INTERACT_ACROSS_PROFILES_CHANGED = "android.content.pm.action.CAN_INTERACT_ACROSS_PROFILES_CHANGED";
    private final Context mContext;
    private final Resources mResources;
    private final ICrossProfileApps mService;
    private final UserManager mUserManager;

    public CrossProfileApps(Context context, ICrossProfileApps iCrossProfileApps) {
        this.mContext = context;
        this.mService = iCrossProfileApps;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mResources = context.getResources();
    }

    public void startMainActivity(ComponentName componentName, UserHandle userHandle) {
        try {
            this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), componentName, userHandle.getIdentifier(), true, this.mContext.getActivityToken(), ActivityOptions.makeBasic().toBundle());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startMainActivity(ComponentName componentName, UserHandle userHandle, Activity activity, Bundle bundle) {
        try {
            this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), componentName, userHandle.getIdentifier(), true, activity != null ? activity.getActivityToken() : null, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startActivity(Intent intent, UserHandle userHandle, Activity activity) {
        startActivity(intent, userHandle, activity, (Bundle) null);
    }

    public void startActivity(Intent intent, UserHandle userHandle, Activity activity, Bundle bundle) {
        try {
            this.mService.startActivityAsUserByIntent(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), intent, userHandle.getIdentifier(), activity != null ? activity.getActivityToken() : null, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void startActivity(ComponentName componentName, UserHandle userHandle, Activity activity, Bundle bundle) {
        try {
            this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), componentName, userHandle.getIdentifier(), false, activity != null ? activity.getActivityToken() : null, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void startActivity(ComponentName componentName, UserHandle userHandle) {
        try {
            this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), componentName, userHandle.getIdentifier(), false, null, null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<UserHandle> getTargetUserProfiles() {
        try {
            return this.mService.getTargetUserProfiles(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
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
        final boolean zIsManagedProfile = this.mUserManager.isManagedProfile(userHandle.getIdentifier());
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class);
        final String string = getCallingApplicationLabel().toString();
        return devicePolicyManager.getResources().getString(getUpdatableProfileSwitchingLabelId(zIsManagedProfile), new Supplier() { // from class: android.content.pm.CrossProfileApps$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$getProfileSwitchingLabel$0(zIsManagedProfile, string);
            }
        }, string);
    }

    private CharSequence getCallingApplicationLabel() {
        PackageManager packageManager = this.mContext.getPackageManager();
        Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(this.mContext.getPackageName());
        if (launchIntentForPackage == null) {
            return getDefaultCallingApplicationLabel();
        }
        List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(launchIntentForPackage, PackageManager.ResolveInfoFlags.of(65536L));
        if (listQueryIntentActivities.size() > 0) {
            return listQueryIntentActivities.get(0).loadLabel(packageManager);
        }
        return getDefaultCallingApplicationLabel();
    }

    private CharSequence getDefaultCallingApplicationLabel() {
        return this.mContext.getApplicationInfo().loadSafeLabel(this.mContext.getPackageManager(), 0.0f, 3);
    }

    private String getUpdatableProfileSwitchingLabelId(boolean z) {
        return z ? DevicePolicyResources.Strings.Core.SWITCH_TO_WORK_LABEL : DevicePolicyResources.Strings.Core.SWITCH_TO_PERSONAL_LABEL;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: getDefaultProfileSwitchingLabel, reason: merged with bridge method [inline-methods] */
    public String lambda$getProfileSwitchingLabel$0(boolean z, String str) {
        return this.mResources.getString(z ? R.string.managed_profile_app_label : R.string.user_owner_app_label, str);
    }

    public Drawable getProfileSwitchingIconDrawable(UserHandle userHandle) {
        verifyCanAccessUser(userHandle);
        if (this.mUserManager.isManagedProfile(userHandle.getIdentifier())) {
            return this.mContext.getPackageManager().getUserBadgeForDensityNoBackground(userHandle, 0);
        }
        Drawable defaultUserIcon = UserIcons.getDefaultUserIcon(this.mResources, 0, true);
        int i = this.mContext.getResources().getConfiguration().isNightModeActive() ? R.color.profile_badge_1_dark : R.color.profile_badge_1;
        defaultUserIcon.setColorFilter(null);
        defaultUserIcon.setTint(this.mResources.getColor(i, null));
        return defaultUserIcon;
    }

    public boolean canRequestInteractAcrossProfiles() {
        try {
            return this.mService.canRequestInteractAcrossProfiles(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canInteractAcrossProfiles() {
        try {
            return this.mService.canInteractAcrossProfiles(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Intent createRequestInteractAcrossProfilesIntent() {
        if (!canRequestInteractAcrossProfiles()) {
            throw new SecurityException("The calling package can not request to interact across profiles.");
        }
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_MANAGE_CROSS_PROFILE_ACCESS);
        intent.setData(Uri.parse("package:" + this.mContext.getPackageName()));
        return intent;
    }

    public void setInteractAcrossProfilesAppOp(String str, int i) {
        try {
            this.mService.setInteractAcrossProfilesAppOp(this.mContext.getUserId(), str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canConfigureInteractAcrossProfiles(String str) {
        try {
            return this.mService.canConfigureInteractAcrossProfiles(this.mContext.getUserId(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canUserAttemptToConfigureInteractAcrossProfiles(String str) {
        try {
            return this.mService.canUserAttemptToConfigureInteractAcrossProfiles(this.mContext.getUserId(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetInteractAcrossProfilesAppOps(Collection<String> collection, final Set<String> set) {
        if (collection.isEmpty()) {
            return;
        }
        List<String> list = (List) collection.stream().filter(new Predicate() { // from class: android.content.pm.CrossProfileApps$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CrossProfileApps.lambda$resetInteractAcrossProfilesAppOps$1(set, (String) obj);
            }
        }).collect(Collectors.toList());
        if (list.isEmpty()) {
            return;
        }
        try {
            this.mService.resetInteractAcrossProfilesAppOps(this.mContext.getUserId(), list);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ boolean lambda$resetInteractAcrossProfilesAppOps$1(Set set, String str) {
        return !set.contains(str);
    }

    public void clearInteractAcrossProfilesAppOps() {
        try {
            this.mService.clearInteractAcrossProfilesAppOps(this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void verifyCanAccessUser(UserHandle userHandle) {
        if (getTargetUserProfiles().contains(userHandle)) {
            return;
        }
        throw new SecurityException("Not allowed to access " + userHandle);
    }
}
