package com.android.internal.app;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.metrics.LogMaker;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.util.Log;
import android.util.Slog;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.R;
import com.android.internal.app.chooser.TargetInfo;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.samsung.android.knox.SemPersonaManager;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public class IntentForwarderActivity extends Activity {
    public static final String EXTRA_SKIP_USER_CONFIRMATION = "com.android.internal.app.EXTRA_SKIP_USER_CONFIRMATION";
    public static String FORWARD_INTENT_TO_MANAGED_PROFILE = "com.android.internal.app.ForwardIntentToManagedProfile";
    public static String FORWARD_INTENT_TO_PARENT = "com.android.internal.app.ForwardIntentToParent";
    public static String TAG = "IntentForwarderActivity";
    private static final String TEL_SCHEME = "tel";
    protected ExecutorService mExecutorService;
    private Injector mInjector;
    private MetricsLogger mMetricsLogger;
    private static final Set<String> ALLOWED_TEXT_MESSAGE_SCHEMES = new HashSet(Arrays.asList(Context.SMS_SERVICE, "smsto", "mms", "mmsto"));
    private static final ComponentName RESOLVER_COMPONENT_NAME = new ComponentName("android", ResolverActivity.class.getName());

    public interface Injector {
        IPackageManager getIPackageManager();

        PackageManager getPackageManager();

        UserManager getUserManager();

        CompletableFuture<ResolveInfo> resolveActivityAsUser(Intent intent, int i, int i2);

        void showToast(String str, int i);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.mExecutorService.shutdown();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setMiniresolverPadding();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        int profileParent;
        String str;
        UserInfo userInfo;
        super.onCreate(bundle);
        this.mInjector = createInjector();
        this.mExecutorService = Executors.newSingleThreadExecutor();
        Intent intent = getIntent();
        String className = intent.getComponent().getClassName();
        if (className.equals(FORWARD_INTENT_TO_PARENT)) {
            String forwardToPersonalMessage = getForwardToPersonalMessage();
            profileParent = getProfileParent();
            getMetricsLogger().write(new LogMaker(MetricsProto.MetricsEvent.ACTION_SWITCH_SHARE_PROFILE).setSubtype(1));
            str = forwardToPersonalMessage;
            userInfo = null;
        } else if (className.equals(FORWARD_INTENT_TO_MANAGED_PROFILE)) {
            String forwardToWorkMessage = getForwardToWorkMessage();
            UserInfo managedProfile = getManagedProfile();
            profileParent = managedProfile == null ? -10000 : managedProfile.id;
            getMetricsLogger().write(new LogMaker(MetricsProto.MetricsEvent.ACTION_SWITCH_SHARE_PROFILE).setSubtype(2));
            userInfo = managedProfile;
            str = forwardToWorkMessage;
        } else {
            Slog.wtf(TAG, IntentForwarderActivity.class.getName() + " cannot be called directly");
            profileParent = -10000;
            str = null;
            userInfo = null;
        }
        if (profileParent == -10000) {
            finish();
            return;
        }
        if (Intent.ACTION_CHOOSER.equals(intent.getAction())) {
            launchChooserActivityWithCorrectTab(intent, className);
            return;
        }
        int userId = getUserId();
        Intent intentCanForward = canForward(intent, getUserId(), profileParent, this.mInjector.getIPackageManager(), getContentResolver());
        if (intentCanForward == null) {
            Slog.wtf(TAG, "the intent: " + intent + " cannot be forwarded from user " + userId + " to user " + profileParent);
            finish();
            return;
        }
        intentCanForward.prepareToLeaveUser(userId);
        CompletableFuture<ResolveInfo> completableFutureResolveActivityAsUser = this.mInjector.resolveActivityAsUser(intentCanForward, 65536, profileParent);
        if (isPrivateProfile(userId)) {
            buildAndExecuteForPrivateProfile(intent, className, intentCanForward, userId, profileParent);
        } else {
            buildAndExecute(completableFutureResolveActivityAsUser, intent, className, intentCanForward, userId, profileParent, str, userInfo);
        }
    }

    private void buildAndExecute(CompletableFuture<ResolveInfo> completableFuture, final Intent intent, final String str, final Intent intent2, final int i, final int i2, final String str2, final UserInfo userInfo) {
        completableFuture.thenApplyAsync(new Function() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.lambda$buildAndExecute$0(intent, str, intent2, i, i2, (ResolveInfo) obj);
            }
        }, (Executor) this.mExecutorService).thenAcceptAsync((Consumer<? super U>) new Consumer() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$buildAndExecute$1(str, intent, str2, intent2, userInfo, (ResolveInfo) obj);
            }
        }, getApplicationContext().getMainExecutor());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ResolveInfo lambda$buildAndExecute$0(Intent intent, String str, Intent intent2, int i, int i2, ResolveInfo resolveInfo) {
        if (isResolverActivityResolveInfo(resolveInfo)) {
            launchResolverActivityWithCorrectTab(intent, str, intent2, i, i2, false);
            return resolveInfo;
        }
        if (str.equals(FORWARD_INTENT_TO_PARENT)) {
            startActivityAsCaller(intent2, i2);
        }
        return resolveInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buildAndExecute$1(String str, Intent intent, String str2, Intent intent2, UserInfo userInfo, ResolveInfo resolveInfo) {
        if (str.equals(FORWARD_INTENT_TO_PARENT)) {
            maybeShowDisclosure(intent, resolveInfo, str2);
            finish();
        } else if (str.equals(FORWARD_INTENT_TO_MANAGED_PROFILE)) {
            maybeShowUserConsentMiniResolver(resolveInfo, intent2, userInfo);
        }
    }

    private void buildAndExecuteForPrivateProfile(final Intent intent, final String str, final Intent intent2, final int i, final int i2) {
        this.mInjector.resolveActivityAsUser(intent2, 65536, i2).thenAcceptAsync(new Consumer() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$buildAndExecuteForPrivateProfile$2(intent, str, intent2, i, i2, (ResolveInfo) obj);
            }
        }, getApplicationContext().getMainExecutor());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buildAndExecuteForPrivateProfile$2(Intent intent, String str, Intent intent2, int i, int i2, ResolveInfo resolveInfo) {
        if (isResolverActivityResolveInfo(resolveInfo)) {
            launchResolverActivityWithCorrectTab(intent, str, intent2, i, i2, true);
        } else {
            maybeShowUserConsentMiniResolverPrivate(resolveInfo, intent2, i2);
        }
    }

    private void maybeShowUserConsentMiniResolver(ResolveInfo resolveInfo, Intent intent, UserInfo userInfo) {
        if (resolveInfo == null || isIntentForwarderResolveInfo(resolveInfo) || !isDeviceProvisioned()) {
            finish();
            return;
        }
        int i = userInfo == null ? -10000 : userInfo.id;
        String callingPackage = getCallingPackage();
        boolean z = intent.getBooleanExtra(EXTRA_SKIP_USER_CONFIRMATION, false) && callingPackage != null && getPackageManager().checkPermission(Manifest.permission.INTERACT_ACROSS_USERS, callingPackage) == 0;
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(DevicePolicyManager.class);
        ComponentName profileOwnerAsUser = devicePolicyManager.getProfileOwnerAsUser(i);
        boolean z2 = profileOwnerAsUser != null && profileOwnerAsUser.getPackageName().equals(resolveInfo.getComponentInfo().packageName);
        if (z || z2) {
            Log.i("IntentForwarderActivity", String.format("Skipping user consent for redirection into the managed profile for intent [%s], privilegedCallerAskedToSkipUserConsent=[%s], intentToLaunchProfileOwner=[%s]", intent, Boolean.valueOf(z), Boolean.valueOf(z2)));
            startActivityAsCaller(intent, i);
            finish();
            return;
        }
        Log.i("IntentForwarderActivity", String.format("Showing user consent for redirection into the managed profile for intent [%s] and  calling package [%s]", intent, callingPackage));
        PackageManager packageManager = createContextAsUser(UserHandle.of(i), 0).getPackageManager();
        buildMiniResolver(resolveInfo, intent, i, getOpenInWorkMessage(intent, resolveInfo.loadLabel(packageManager)), packageManager);
        ((Button) findViewById(R.id.button_open)).setText(getOpenInWorkButtonString(intent));
        View viewFindViewById = findViewById(R.id.miniresolver_info_section);
        if ((isDialerIntent(intent) || isTextMessageIntent(intent)) && devicePolicyManager.getManagedSubscriptionsPolicy().getPolicyType() == 1) {
            viewFindViewById.setVisibility(0);
            ((TextView) findViewById(R.id.miniresolver_info_section_text)).lambda$setTextAsync$0(getWorkTelephonyInfoSectionMessage(intent));
        } else {
            viewFindViewById.setVisibility(8);
        }
    }

    private void maybeShowUserConsentMiniResolverPrivate(ResolveInfo resolveInfo, Intent intent, int i) {
        if (resolveInfo == null || isIntentForwarderResolveInfo(resolveInfo)) {
            finish();
            return;
        }
        Log.i("IntentForwarderActivity", String.format("Showing user consent for redirection into the main profile for intent [%s] and  calling package [%s]", intent, getCallingPackage()));
        PackageManager packageManager = createContextAsUser(UserHandle.of(i), 0).getPackageManager();
        buildMiniResolver(resolveInfo, intent, i, getString(R.string.miniresolver_open_in_personal, resolveInfo.loadLabel(packageManager)), packageManager);
        findViewById(R.id.miniresolver_info_section).setVisibility(0);
        if (isTextMessageIntent(intent)) {
            ((TextView) findViewById(R.id.miniresolver_info_section_text)).setText(R.string.miniresolver_private_space_messages_information);
        } else {
            ((TextView) findViewById(R.id.miniresolver_info_section_text)).setText(R.string.miniresolver_private_space_phone_information);
        }
    }

    private void buildMiniResolver(ResolveInfo resolveInfo, final Intent intent, final int i, String str, PackageManager packageManager) {
        setContentView(R.layout.miniresolver);
        findViewById(R.id.title_container).setElevation(0.0f);
        ((ImageView) findViewById(16908294)).lambda$setImageURIAsync$0(getAppIcon(resolveInfo, intent, i, packageManager));
        setMiniresolverPadding();
        ((TextView) findViewById(R.id.open_cross_profile)).lambda$setTextAsync$0(str);
        ((Button) findViewById(R.id.use_same_profile_browser)).setText(17039360);
        findViewById(R.id.use_same_profile_browser).setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$buildMiniResolver$3(view);
            }
        });
        findViewById(R.id.button_open).setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$buildMiniResolver$4(intent, i, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buildMiniResolver$3(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buildMiniResolver$4(Intent intent, int i, View view) {
        TargetInfo.refreshIntentCreatorToken(intent);
        startActivityAsCaller(intent, ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.activity_open_enter, R.anim.push_down_out).toBundle(), false, i);
        finish();
    }

    private Drawable getAppIcon(ResolveInfo resolveInfo, Intent intent, int i, PackageManager packageManager) {
        if (isDialerIntent(intent)) {
            try {
                return packageManager.getApplicationInfo(((TelecomManager) getApplicationContext().getSystemService(TelecomManager.class)).getDefaultDialerPackage(UserHandle.of(i)), 0).loadIcon(packageManager);
            } catch (PackageManager.NameNotFoundException unused) {
                Slog.w(TAG, "Cannot load icon for default dialer package");
            }
        }
        return resolveInfo.loadIcon(packageManager);
    }

    private int getOpenInWorkButtonString(Intent intent) {
        return isDialerIntent(intent) ? R.string.miniresolver_call : isTextMessageIntent(intent) ? R.string.miniresolver_switch : R.string.whichViewApplicationLabel;
    }

    private String getOpenInWorkMessage(Intent intent, final CharSequence charSequence) {
        if (isDialerIntent(intent)) {
            return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.MINIRESOLVER_CALL_FROM_WORK, new Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getOpenInWorkMessage$5();
                }
            });
        }
        if (isTextMessageIntent(intent)) {
            return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.MINIRESOLVER_SWITCH_TO_WORK, new Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda7
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getOpenInWorkMessage$6();
                }
            });
        }
        return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.MINIRESOLVER_OPEN_WORK, new Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$getOpenInWorkMessage$7(charSequence);
            }
        }, charSequence);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getOpenInWorkMessage$5() {
        return getString(R.string.miniresolver_call_in_work);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getOpenInWorkMessage$6() {
        return getString(R.string.miniresolver_switch_to_work);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getOpenInWorkMessage$7(CharSequence charSequence) {
        return getString(R.string.miniresolver_open_work, charSequence);
    }

    private String getWorkTelephonyInfoSectionMessage(Intent intent) {
        if (isDialerIntent(intent)) {
            return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString("Core.MINIRESOLVER_WORK_TELEPHONY_INFORMATION", new Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getWorkTelephonyInfoSectionMessage$8();
                }
            });
        }
        if (isTextMessageIntent(intent)) {
            return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString("Core.MINIRESOLVER_WORK_TELEPHONY_INFORMATION", new Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getWorkTelephonyInfoSectionMessage$9();
                }
            });
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getWorkTelephonyInfoSectionMessage$8() {
        return getString(R.string.miniresolver_call_information);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getWorkTelephonyInfoSectionMessage$9() {
        return getString(R.string.miniresolver_sms_information);
    }

    private String getForwardToPersonalMessage() {
        return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.FORWARD_INTENT_TO_PERSONAL, new Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$getForwardToPersonalMessage$10();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getForwardToPersonalMessage$10() {
        return getString(R.string.forward_intent_to_owner);
    }

    private String getForwardToWorkMessage() {
        return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.FORWARD_INTENT_TO_WORK, new Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$getForwardToWorkMessage$11();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$getForwardToWorkMessage$11() {
        return getString(R.string.forward_intent_to_work);
    }

    private boolean isIntentForwarderResolveInfo(ResolveInfo resolveInfo) {
        ActivityInfo activityInfo;
        if (resolveInfo == null || (activityInfo = resolveInfo.activityInfo) == null || !"android".equals(activityInfo.packageName)) {
            return false;
        }
        return activityInfo.name.equals(FORWARD_INTENT_TO_PARENT) || activityInfo.name.equals(FORWARD_INTENT_TO_MANAGED_PROFILE);
    }

    private boolean isResolverActivityResolveInfo(ResolveInfo resolveInfo) {
        return (resolveInfo == null || resolveInfo.activityInfo == null || !RESOLVER_COMPONENT_NAME.equals(resolveInfo.activityInfo.getComponentName())) ? false : true;
    }

    private void maybeShowDisclosure(Intent intent, ResolveInfo resolveInfo, String str) {
        if (SemPersonaManager.getAppSeparationConfig() == null && shouldShowDisclosure(resolveInfo, intent) && str != null) {
            this.mInjector.showToast(str, 1);
        }
    }

    private void startActivityAsCaller(Intent intent, int i) {
        try {
            TargetInfo.refreshIntentCreatorToken(intent);
            startActivityAsCaller(intent, null, false, i);
        } catch (RuntimeException e) {
            Slog.wtf(TAG, "Unable to launch as UID " + getLaunchedFromUid() + " package " + getLaunchedFromPackage() + ", while running in " + ActivityThread.currentProcessName(), e);
        }
    }

    private void launchChooserActivityWithCorrectTab(Intent intent, String str) {
        int iFindSelectedProfile = findSelectedProfile(str);
        sanitizeIntent(intent);
        intent.putExtra("com.android.internal.app.ResolverActivity.EXTRA_SELECTED_PROFILE", iFindSelectedProfile);
        Intent intent2 = (Intent) intent.getParcelableExtra("android.intent.extra.INTENT", Intent.class);
        if (intent2 == null) {
            Slog.wtf(TAG, "Cannot start a chooser intent with no extra android.intent.extra.INTENT");
            return;
        }
        sanitizeIntent(intent2);
        TargetInfo.refreshIntentCreatorToken(intent);
        startActivityAsCaller(intent, null, false, getUserId());
        finish();
    }

    private void launchResolverActivityWithCorrectTab(Intent intent, String str, Intent intent2, int i, int i2, boolean z) {
        if (!isIntentForwarderResolveInfo(this.mInjector.resolveActivityAsUser(intent2, 65536, i).join())) {
            i2 = i;
        }
        int iFindSelectedProfile = findSelectedProfile(str);
        sanitizeIntent(intent);
        intent.putExtra("com.android.internal.app.ResolverActivity.EXTRA_SELECTED_PROFILE", iFindSelectedProfile);
        intent.putExtra("com.android.internal.app.ResolverActivity.EXTRA_CALLING_USER", UserHandle.of(i));
        if (z) {
            intent.putExtra("com.android.internal.app.ResolverActivity.EXTRA_RESTRICT_TO_SINGLE_USER", true);
        }
        TargetInfo.refreshIntentCreatorToken(intent);
        startActivityAsCaller(intent, null, false, i2);
        finish();
    }

    private int findSelectedProfile(String str) {
        if (str.equals(FORWARD_INTENT_TO_PARENT)) {
            return 0;
        }
        return str.equals(FORWARD_INTENT_TO_MANAGED_PROFILE) ? 1 : -1;
    }

    private boolean shouldShowDisclosure(ResolveInfo resolveInfo, Intent intent) {
        if (!isDeviceProvisioned()) {
            return false;
        }
        if (resolveInfo == null || resolveInfo.activityInfo == null) {
            return true;
        }
        if (resolveInfo.activityInfo.applicationInfo.isSystemApp() && (isDialerIntent(intent) || isTextMessageIntent(intent))) {
            return false;
        }
        return !isTargetResolverOrChooserActivity(resolveInfo.activityInfo);
    }

    private boolean isDeviceProvisioned() {
        return Settings.Global.getInt(getContentResolver(), "device_provisioned", 0) != 0;
    }

    private boolean isTextMessageIntent(Intent intent) {
        return (Intent.ACTION_SENDTO.equals(intent.getAction()) || isViewActionIntent(intent)) && ALLOWED_TEXT_MESSAGE_SCHEMES.contains(intent.getScheme());
    }

    private boolean isDialerIntent(Intent intent) {
        if (Intent.ACTION_DIAL.equals(intent.getAction()) || Intent.ACTION_CALL.equals(intent.getAction()) || Intent.ACTION_CALL_PRIVILEGED.equals(intent.getAction()) || Intent.ACTION_CALL_EMERGENCY.equals(intent.getAction())) {
            return true;
        }
        return isViewActionIntent(intent) && "tel".equals(intent.getScheme());
    }

    private boolean isViewActionIntent(Intent intent) {
        return "android.intent.action.VIEW".equals(intent.getAction()) && intent.hasCategory(Intent.CATEGORY_BROWSABLE);
    }

    private boolean isTargetResolverOrChooserActivity(ActivityInfo activityInfo) {
        if ("android".equals(activityInfo.packageName)) {
            return ResolverActivity.class.getName().equals(activityInfo.name) || ChooserActivity.class.getName().equals(activityInfo.name);
        }
        return false;
    }

    static Intent canForward(Intent intent, int i, int i2, IPackageManager iPackageManager, ContentResolver contentResolver) {
        Intent intent2 = new Intent(intent);
        intent2.addFlags(50331648);
        sanitizeIntent(intent2);
        if (!canForwardInner(intent2, i, i2, iPackageManager, contentResolver)) {
            return null;
        }
        if (intent2.getSelector() != null) {
            sanitizeIntent(intent2.getSelector());
            if (!canForwardInner(intent2.getSelector(), i, i2, iPackageManager, contentResolver)) {
                return null;
            }
        }
        return intent2;
    }

    private static boolean canForwardInner(Intent intent, int i, int i2, IPackageManager iPackageManager, ContentResolver contentResolver) {
        if (Intent.ACTION_CHOOSER.equals(intent.getAction())) {
            return false;
        }
        try {
        } catch (RemoteException unused) {
            Slog.e(TAG, "PackageManagerService is dead?");
        }
        return iPackageManager.canForwardTo(intent, intent.resolveTypeIfNeeded(contentResolver), i, i2);
    }

    private UserInfo getManagedProfile() {
        for (UserInfo userInfo : this.mInjector.getUserManager().getProfiles(UserHandle.myUserId())) {
            if (userInfo.isManagedProfile()) {
                return userInfo;
            }
        }
        Slog.wtf(TAG, FORWARD_INTENT_TO_MANAGED_PROFILE + " has been called, but there is no managed profile");
        return null;
    }

    private UserInfo getPrivateProfile() {
        for (UserInfo userInfo : this.mInjector.getUserManager().getProfiles(UserHandle.myUserId())) {
            if (userInfo.isPrivateProfile()) {
                return userInfo;
            }
        }
        return null;
    }

    private int getProfileParent() {
        UserInfo profileParent = this.mInjector.getUserManager().getProfileParent(UserHandle.myUserId());
        if (profileParent == null) {
            Slog.wtf(TAG, FORWARD_INTENT_TO_PARENT + " has been called, but there is no parent");
            return -10000;
        }
        return profileParent.id;
    }

    private static void sanitizeIntent(Intent intent) {
        intent.setPackage(null);
        intent.setComponent(null);
    }

    protected MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new MetricsLogger();
        }
        return this.mMetricsLogger;
    }

    private boolean isPrivateProfile(int i) {
        UserInfo privateProfile = getPrivateProfile();
        return privateSpaceFlagsEnabled() && privateProfile != null && privateProfile.id == i;
    }

    private boolean privateSpaceFlagsEnabled() {
        return Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures() && android.multiuser.Flags.enablePrivateSpaceIntentRedirection();
    }

    private void setMiniresolverPadding() {
        View viewFindViewById = findViewById(R.id.button_bar_container);
        if (viewFindViewById != null) {
            viewFindViewById.setPadding(0, 0, 0, getWindowManager().getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.systemBars()).bottom + getResources().getDimensionPixelOffset(R.dimen.resolver_button_bar_spacing));
        }
    }

    protected Injector createInjector() {
        return new InjectorImpl();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class InjectorImpl implements Injector {
        private InjectorImpl() {
        }

        @Override // com.android.internal.app.IntentForwarderActivity.Injector
        public IPackageManager getIPackageManager() {
            return AppGlobals.getPackageManager();
        }

        @Override // com.android.internal.app.IntentForwarderActivity.Injector
        public UserManager getUserManager() {
            return (UserManager) IntentForwarderActivity.this.getSystemService(UserManager.class);
        }

        @Override // com.android.internal.app.IntentForwarderActivity.Injector
        public PackageManager getPackageManager() {
            return IntentForwarderActivity.this.getPackageManager();
        }

        @Override // com.android.internal.app.IntentForwarderActivity.Injector
        public CompletableFuture<ResolveInfo> resolveActivityAsUser(final Intent intent, final int i, final int i2) {
            return CompletableFuture.supplyAsync(new Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$InjectorImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$resolveActivityAsUser$0(intent, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ ResolveInfo lambda$resolveActivityAsUser$0(Intent intent, int i, int i2) {
            return getPackageManager().resolveActivityAsUser(intent, i, i2);
        }

        @Override // com.android.internal.app.IntentForwarderActivity.Injector
        public void showToast(String str, int i) {
            Toast.makeText(IntentForwarderActivity.this, str, i).show();
        }
    }
}
