package com.android.internal.app;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.util.Log;
import android.util.Slog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.R;
import com.android.internal.app.IntentForwarderActivity;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.samsung.android.knox.SemPersonaManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
    private static final int REQUEST_CONFIRM_CREDENTIALS = 1;
    private static final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
    private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    private static final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    private static final String TARGET_USER_ID = "targetUserId";
    private static final String TEL_SCHEME = "tel";
    protected ExecutorService mExecutorService;
    private Injector mInjector;
    private MetricsLogger mMetricsLogger;
    private Intent tempIntent;
    private int userId;
    public static String TAG = "IntentForwarderActivity";
    public static String FORWARD_INTENT_TO_PARENT = SemPersonaManager.ICON_CLASS_FOR_INTENT_FORWARD_TO_PARENT;
    public static String FORWARD_INTENT_TO_MANAGED_PROFILE = SemPersonaManager.ICON_CLASS_FOR_INTENT_FORWARD_TO_PROFILE;
    private static final Set<String> ALLOWED_TEXT_MESSAGE_SCHEMES = new HashSet(Arrays.asList(Context.SMS_SERVICE, "smsto", "mms", "mmsto"));
    private static final ComponentName RESOLVER_COMPONENT_NAME = new ComponentName("android", ResolverActivity.class.getName());
    public static String FORWARD_INTENT_TO_MANAGED_PROFILE4 = SemPersonaManager.ICON_CLASS_FOR_SECUREFOLDER_FORWARD_TO_PROFILE;
    private final int TARGET_USER_ID_DEFAULT_VALUE = -1;
    private int mFinalTargetUserId = -1;
    private int mFinalCallingUserId = -1;
    private BroadcastReceiver mBroadcastReceiver = null;
    private boolean mWaitingForresponse = false;
    private SemPersonaManager mPersona = null;
    private KeyguardManager mKeyguardManager = null;
    private UserManager mUserManager = null;

    public interface Injector {
        IPackageManager getIPackageManager();

        PackageManager getPackageManager();

        UserManager getUserManager();

        CompletableFuture<ResolveInfo> resolveActivityAsUser(Intent intent, int i, int i2);

        void showToast(String str, int i);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        try {
            if (this.mBroadcastReceiver != null) {
                unregisterReceiver(this.mBroadcastReceiver);
                this.mBroadcastReceiver = null;
            }
        } catch (IllegalArgumentException e) {
            Slog.e(TAG, "receiver not registered.");
        }
        super.onDestroy();
        this.mExecutorService.shutdown();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        int targetUserIdForKnox;
        String userMessage;
        int targetUserId;
        UserInfo managedProfile;
        int whichContainer;
        String userMessage2;
        super.onCreate(savedInstanceState);
        this.mInjector = createInjector();
        this.mExecutorService = Executors.newSingleThreadExecutor();
        Intent intentReceived = getIntent();
        String className = intentReceived.getComponent().getClassName();
        this.mUserManager = (UserManager) getSystemService("user");
        this.mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        this.mPersona = (SemPersonaManager) getSystemService("persona");
        try {
            targetUserIdForKnox = intentReceived.getIntExtra("crossProfileTargetUserId", -1);
        } catch (Exception e) {
            Slog.e(TAG, "No crossProfileTargetUserId!");
            targetUserIdForKnox = -1;
        }
        Slog.e(TAG, "targetUserIdForKnox:" + targetUserIdForKnox);
        if (className.equals(FORWARD_INTENT_TO_PARENT)) {
            int callingId = getUserId();
            if (SemPersonaManager.isSecureFolderId(callingId)) {
                userMessage2 = getForwardToPersonalMessageFromSecureFolder();
            } else {
                userMessage2 = getForwardToPersonalMessage();
            }
            int targetUserId2 = getProfileParent();
            getMetricsLogger().write(new LogMaker(MetricsProto.MetricsEvent.ACTION_SWITCH_SHARE_PROFILE).setSubtype(1));
            userMessage = userMessage2;
            targetUserId = targetUserId2;
            managedProfile = null;
        } else if (className.contains(FORWARD_INTENT_TO_MANAGED_PROFILE)) {
            String userMessage3 = getForwardToWorkMessage();
            UserInfo managedProfile2 = getManagedProfile();
            if (targetUserIdForKnox != -1) {
                whichContainer = targetUserIdForKnox;
            } else {
                int whichContainer2 = -1;
                if (FORWARD_INTENT_TO_MANAGED_PROFILE4.equalsIgnoreCase(className)) {
                    whichContainer2 = 131072;
                }
                if (whichContainer2 != -1) {
                    whichContainer = SemPersonaManager.getSecureFolderId(this);
                } else {
                    whichContainer = managedProfile2 == null ? -10000 : managedProfile2.id;
                }
            }
            getMetricsLogger().write(new LogMaker(MetricsProto.MetricsEvent.ACTION_SWITCH_SHARE_PROFILE).setSubtype(2));
            userMessage = userMessage3;
            targetUserId = whichContainer;
            managedProfile = managedProfile2;
        } else {
            String userMessage4 = TAG;
            Slog.wtf(userMessage4, IntentForwarderActivity.class.getName() + " cannot be called directly");
            userMessage = null;
            targetUserId = -10000;
            managedProfile = null;
        }
        if (targetUserId == -10000) {
            finish();
            return;
        }
        if (Intent.ACTION_CHOOSER.equals(intentReceived.getAction())) {
            launchChooserActivityWithCorrectTab(intentReceived, className);
            return;
        }
        int callingUserId = getUserId();
        Intent newIntent = canForward(intentReceived, getUserId(), targetUserId, this.mInjector.getIPackageManager(), getContentResolver());
        this.mFinalCallingUserId = callingUserId;
        this.mFinalTargetUserId = targetUserId;
        Slog.d(TAG, "forward intent from : " + this.mFinalCallingUserId + " to " + this.mFinalTargetUserId);
        if (newIntent == null) {
            Slog.wtf(TAG, "the intent: " + intentReceived + " cannot be forwarded from user " + callingUserId + " to user " + targetUserId);
            finish();
            return;
        }
        newIntent.prepareToLeaveUser(callingUserId);
        CompletableFuture<ResolveInfo> targetResolveInfoFuture = this.mInjector.resolveActivityAsUser(newIntent, 65536, targetUserId);
        if (isPrivateProfile(callingUserId)) {
            buildAndExecuteForPrivateProfile(intentReceived, className, newIntent, callingUserId, targetUserId);
        } else {
            buildAndExecute(targetResolveInfoFuture, intentReceived, className, newIntent, callingUserId, targetUserId, userMessage, managedProfile);
        }
    }

    private void buildAndExecute(CompletableFuture<ResolveInfo> targetResolveInfoFuture, final Intent intentReceived, final String className, final Intent newIntent, final int callingUserId, final int targetUserId, final String userMessage, final UserInfo managedProfile) {
        targetResolveInfoFuture.thenApplyAsync(new Function() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ResolveInfo lambda$buildAndExecute$0;
                lambda$buildAndExecute$0 = IntentForwarderActivity.this.lambda$buildAndExecute$0(callingUserId, targetUserId, newIntent, intentReceived, className, (ResolveInfo) obj);
                return lambda$buildAndExecute$0;
            }
        }, (Executor) this.mExecutorService).thenAcceptAsync((Consumer<? super U>) new Consumer() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                IntentForwarderActivity.this.lambda$buildAndExecute$1(className, intentReceived, userMessage, newIntent, managedProfile, (ResolveInfo) obj);
            }
        }, getApplicationContext().getMainExecutor());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ResolveInfo lambda$buildAndExecute$0(int callingUserId, int targetUserId, Intent newIntent, Intent intentReceived, String className, ResolveInfo targetResolveInfo) {
        if ((callingUserId == 0 && SemPersonaManager.isSecureFolderId(targetUserId)) || (SemPersonaManager.isSecureFolderId(callingUserId) && targetUserId == 0)) {
            Slog.d(TAG, "startActivityAsCaller 1 : " + targetUserId);
            startActivityAsCaller(newIntent, targetUserId);
            return targetResolveInfo;
        }
        if (isResolverActivityResolveInfo(targetResolveInfo)) {
            launchResolverActivityWithCorrectTab(intentReceived, className, newIntent, callingUserId, targetUserId, false);
        } else if (className.equals(FORWARD_INTENT_TO_PARENT)) {
            startActivityAsCaller(newIntent, targetUserId);
        }
        return targetResolveInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buildAndExecute$1(String className, Intent intentReceived, String userMessage, Intent newIntent, UserInfo managedProfile, ResolveInfo result) {
        if (className.equals(FORWARD_INTENT_TO_PARENT)) {
            maybeShowDisclosure(intentReceived, result, userMessage);
            finish();
        } else if (className.equals(FORWARD_INTENT_TO_MANAGED_PROFILE)) {
            maybeShowUserConsentMiniResolver(result, newIntent, managedProfile);
        }
        if (className.equals(FORWARD_INTENT_TO_MANAGED_PROFILE4)) {
            Slog.wtf(TAG, "waiting response | " + this.mWaitingForresponse);
            if (!this.mWaitingForresponse) {
                finish();
            }
        }
    }

    private void buildAndExecuteForPrivateProfile(final Intent intentReceived, final String className, final Intent newIntent, final int callingUserId, final int targetUserId) {
        CompletableFuture<ResolveInfo> targetResolveInfoFuture = this.mInjector.resolveActivityAsUser(newIntent, 65536, targetUserId);
        targetResolveInfoFuture.thenAcceptAsync(new Consumer() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                IntentForwarderActivity.this.lambda$buildAndExecuteForPrivateProfile$2(intentReceived, className, newIntent, callingUserId, targetUserId, (ResolveInfo) obj);
            }
        }, getApplicationContext().getMainExecutor());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buildAndExecuteForPrivateProfile$2(Intent intentReceived, String className, Intent newIntent, int callingUserId, int targetUserId, ResolveInfo targetResolveInfo) {
        if (isResolverActivityResolveInfo(targetResolveInfo)) {
            launchResolverActivityWithCorrectTab(intentReceived, className, newIntent, callingUserId, targetUserId, true);
        } else {
            maybeShowUserConsentMiniResolverPrivate(targetResolveInfo, newIntent, targetUserId);
        }
    }

    private void maybeShowUserConsentMiniResolver(ResolveInfo target, Intent launchIntent, UserInfo managedProfile) {
        if (target == null || isIntentForwarderResolveInfo(target) || !isDeviceProvisioned()) {
            finish();
            return;
        }
        int targetUserId = managedProfile == null ? -10000 : managedProfile.id;
        String callingPackage = getCallingPackage();
        boolean privilegedCallerAskedToSkipUserConsent = launchIntent.getBooleanExtra(EXTRA_SKIP_USER_CONFIRMATION, false) && callingPackage != null && getPackageManager().checkPermission(Manifest.permission.INTERACT_ACROSS_USERS, callingPackage) == 0;
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(DevicePolicyManager.class);
        ComponentName profileOwnerName = devicePolicyManager.getProfileOwnerAsUser(targetUserId);
        boolean intentToLaunchProfileOwner = profileOwnerName != null && profileOwnerName.getPackageName().equals(target.getComponentInfo().packageName);
        if (privilegedCallerAskedToSkipUserConsent || intentToLaunchProfileOwner) {
            Log.i("IntentForwarderActivity", String.format("Skipping user consent for redirection into the managed profile for intent [%s], privilegedCallerAskedToSkipUserConsent=[%s], intentToLaunchProfileOwner=[%s]", launchIntent, Boolean.valueOf(privilegedCallerAskedToSkipUserConsent), Boolean.valueOf(intentToLaunchProfileOwner)));
            startActivityAsCaller(launchIntent, targetUserId);
            finish();
            return;
        }
        Log.i("IntentForwarderActivity", String.format("Showing user consent for redirection into the managed profile for intent [%s] and  calling package [%s]", launchIntent, callingPackage));
        PackageManager packageManagerForTargetUser = createContextAsUser(UserHandle.of(targetUserId), 0).getPackageManager();
        buildMiniResolver(target, launchIntent, targetUserId, getOpenInWorkMessage(launchIntent, target.loadLabel(packageManagerForTargetUser)), packageManagerForTargetUser);
        ((Button) findViewById(R.id.button_open)).setText(getOpenInWorkButtonString(launchIntent));
        View telephonyInfo = findViewById(R.id.miniresolver_info_section);
        if ((isDialerIntent(launchIntent) || isTextMessageIntent(launchIntent)) && devicePolicyManager.getManagedSubscriptionsPolicy().getPolicyType() == 1) {
            telephonyInfo.setVisibility(0);
            ((TextView) findViewById(R.id.miniresolver_info_section_text)).lambda$setTextAsync$0(getWorkTelephonyInfoSectionMessage(launchIntent));
        } else {
            telephonyInfo.setVisibility(8);
        }
    }

    private void maybeShowUserConsentMiniResolverPrivate(ResolveInfo target, Intent launchIntent, int targetUserId) {
        if (target == null || isIntentForwarderResolveInfo(target)) {
            finish();
            return;
        }
        String callingPackage = getCallingPackage();
        Log.i("IntentForwarderActivity", String.format("Showing user consent for redirection into the main profile for intent [%s] and  calling package [%s]", launchIntent, callingPackage));
        PackageManager packageManagerForTargetUser = createContextAsUser(UserHandle.of(targetUserId), 0).getPackageManager();
        buildMiniResolver(target, launchIntent, targetUserId, getString(R.string.miniresolver_open_in_personal, target.loadLabel(packageManagerForTargetUser)), packageManagerForTargetUser);
        View telephonyInfo = findViewById(R.id.miniresolver_info_section);
        telephonyInfo.setVisibility(0);
        if (isTextMessageIntent(launchIntent)) {
            ((TextView) findViewById(R.id.miniresolver_info_section_text)).setText(R.string.miniresolver_private_space_messages_information);
        } else {
            ((TextView) findViewById(R.id.miniresolver_info_section_text)).setText(R.string.miniresolver_private_space_phone_information);
        }
    }

    private void buildMiniResolver(ResolveInfo target, final Intent launchIntent, final int targetUserId, String resolverTitle, PackageManager pmForTargetUser) {
        setContentView(R.layout.miniresolver);
        findViewById(R.id.title_container).setElevation(0.0f);
        ImageView icon = (ImageView) findViewById(16908294);
        icon.lambda$setImageURIAsync$0(getAppIcon(target, launchIntent, targetUserId, pmForTargetUser));
        View buttonContainer = findViewById(R.id.button_bar_container);
        buttonContainer.setPadding(0, 0, 0, buttonContainer.getPaddingBottom());
        ((TextView) findViewById(R.id.open_cross_profile)).lambda$setTextAsync$0(resolverTitle);
        ((Button) findViewById(R.id.use_same_profile_browser)).setText(17039360);
        findViewById(R.id.use_same_profile_browser).setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IntentForwarderActivity.this.lambda$buildMiniResolver$3(view);
            }
        });
        findViewById(R.id.button_open).setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IntentForwarderActivity.this.lambda$buildMiniResolver$4(launchIntent, targetUserId, view);
            }
        });
        View contentView = findViewById(R.id.contentPanel);
        contentView.setAlpha(0.0f);
        contentView.animate().alpha(1.0f).translationY(contentView.getHeight()).setListener(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buildMiniResolver$3(View v) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buildMiniResolver$4(Intent launchIntent, int targetUserId, View v) {
        startActivityAsCaller(launchIntent, ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.activity_open_enter, R.anim.push_down_out).toBundle(), false, targetUserId);
        finish();
    }

    private Drawable getAppIcon(ResolveInfo target, Intent launchIntent, int targetUserId, PackageManager packageManagerForTargetUser) {
        if (isDialerIntent(launchIntent)) {
            TelecomManager telecomManager = (TelecomManager) getApplicationContext().getSystemService(TelecomManager.class);
            String defaultDialerPackageName = telecomManager.getDefaultDialerPackage(UserHandle.of(targetUserId));
            try {
                return packageManagerForTargetUser.getApplicationInfo(defaultDialerPackageName, 0).loadIcon(packageManagerForTargetUser);
            } catch (PackageManager.NameNotFoundException e) {
                Slog.w(TAG, "Cannot load icon for default dialer package");
            }
        }
        return target.loadIcon(packageManagerForTargetUser);
    }

    private int getOpenInWorkButtonString(Intent launchIntent) {
        if (isDialerIntent(launchIntent)) {
            return R.string.miniresolver_call;
        }
        if (isTextMessageIntent(launchIntent)) {
            return R.string.miniresolver_switch;
        }
        return R.string.whichViewApplicationLabel;
    }

    private String getOpenInWorkMessage(Intent launchIntent, final CharSequence targetLabel) {
        if (isDialerIntent(launchIntent)) {
            return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.MINIRESOLVER_CALL_FROM_WORK, new Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final Object get() {
                    String lambda$getOpenInWorkMessage$5;
                    lambda$getOpenInWorkMessage$5 = IntentForwarderActivity.this.lambda$getOpenInWorkMessage$5();
                    return lambda$getOpenInWorkMessage$5;
                }
            });
        }
        if (isTextMessageIntent(launchIntent)) {
            return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.MINIRESOLVER_SWITCH_TO_WORK, new Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda7
                @Override // java.util.function.Supplier
                public final Object get() {
                    String lambda$getOpenInWorkMessage$6;
                    lambda$getOpenInWorkMessage$6 = IntentForwarderActivity.this.lambda$getOpenInWorkMessage$6();
                    return lambda$getOpenInWorkMessage$6;
                }
            });
        }
        return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.MINIRESOLVER_OPEN_WORK, new Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                String lambda$getOpenInWorkMessage$7;
                lambda$getOpenInWorkMessage$7 = IntentForwarderActivity.this.lambda$getOpenInWorkMessage$7(targetLabel);
                return lambda$getOpenInWorkMessage$7;
            }
        }, targetLabel);
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
    public /* synthetic */ String lambda$getOpenInWorkMessage$7(CharSequence targetLabel) {
        return getString(R.string.miniresolver_open_work, targetLabel);
    }

    private String getWorkTelephonyInfoSectionMessage(Intent launchIntent) {
        if (isDialerIntent(launchIntent)) {
            return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString("Core.MINIRESOLVER_WORK_TELEPHONY_INFORMATION", new Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    String lambda$getWorkTelephonyInfoSectionMessage$8;
                    lambda$getWorkTelephonyInfoSectionMessage$8 = IntentForwarderActivity.this.lambda$getWorkTelephonyInfoSectionMessage$8();
                    return lambda$getWorkTelephonyInfoSectionMessage$8;
                }
            });
        }
        if (isTextMessageIntent(launchIntent)) {
            return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString("Core.MINIRESOLVER_WORK_TELEPHONY_INFORMATION", new Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    String lambda$getWorkTelephonyInfoSectionMessage$9;
                    lambda$getWorkTelephonyInfoSectionMessage$9 = IntentForwarderActivity.this.lambda$getWorkTelephonyInfoSectionMessage$9();
                    return lambda$getWorkTelephonyInfoSectionMessage$9;
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

    private String getForwardToPersonalMessageFromSecureFolder() {
        return String.format(getString(R.string.forward_intent_from), getString(R.string.secure_folder));
    }

    private String getForwardToPersonalMessage() {
        return ((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.FORWARD_INTENT_TO_PERSONAL, new Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final Object get() {
                String lambda$getForwardToPersonalMessage$10;
                lambda$getForwardToPersonalMessage$10 = IntentForwarderActivity.this.lambda$getForwardToPersonalMessage$10();
                return lambda$getForwardToPersonalMessage$10;
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
                String lambda$getForwardToWorkMessage$11;
                lambda$getForwardToWorkMessage$11 = IntentForwarderActivity.this.lambda$getForwardToWorkMessage$11();
                return lambda$getForwardToWorkMessage$11;
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
        return activityInfo.name.equals(FORWARD_INTENT_TO_PARENT) || activityInfo.name.contains(FORWARD_INTENT_TO_MANAGED_PROFILE);
    }

    private boolean isResolverActivityResolveInfo(ResolveInfo resolveInfo) {
        return (resolveInfo == null || resolveInfo.activityInfo == null || !RESOLVER_COMPONENT_NAME.equals(resolveInfo.activityInfo.getComponentName())) ? false : true;
    }

    private void maybeShowDisclosure(Intent intentReceived, ResolveInfo resolveInfo, String message) {
        if (SemPersonaManager.getAppSeparationConfig() != null) {
            return;
        }
        if (shouldShowDisclosure(resolveInfo, intentReceived) && message != null) {
            this.mInjector.showToast(message, 1);
        } else if ((intentReceived.getAction().equals(Intent.ACTION_SEND) || intentReceived.getAction().equals(Intent.ACTION_SEND_MULTIPLE)) && SemPersonaManager.isSecureFolderId(getUserId())) {
            showForwardFromSecureFolderToast();
        }
    }

    private void showForwardFromSecureFolderToast() {
        String message = getForwardToPersonalMessageFromSecureFolder();
        Slog.d(TAG, "showForwardFromSecureFolderToast | " + message);
        this.mInjector.showToast(message, 0);
    }

    private void startActivityAsCaller(Intent newIntent, int userId) {
        Intent drivingIntent;
        try {
            int callingUserId = getUserId();
            boolean forwardfromKnox = false;
            if (SemPersonaManager.isKnoxId(callingUserId)) {
                forwardfromKnox = true;
            }
            if (Intent.ACTION_CHOOSER.equals(newIntent.getAction())) {
                drivingIntent = (Intent) newIntent.getParcelableExtra("android.intent.extra.INTENT");
                Slog.wtf(TAG, "chooser | extra_intent");
            } else {
                drivingIntent = newIntent;
                Slog.wtf(TAG, "new intent");
            }
            if (drivingIntent != null && ((drivingIntent.getAction().equals(Intent.ACTION_SEND) || drivingIntent.getAction().equals(Intent.ACTION_SEND_MULTIPLE)) && (SemPersonaManager.isSecureFolderId(userId) || SemPersonaManager.isSecureFolderId(callingUserId)))) {
                Slog.wtf(TAG, "chooser | EXTRA_EXCLUDE_COMPONENTS");
                drivingIntent.fixUris(callingUserId);
                getResources();
                Intent ChooserIntent = Intent.createChooser(drivingIntent, Resources.getSystem().getText(R.string.whichSendApplication));
                Parcelable[] pa = drivingIntent.getParcelableArrayExtra(Intent.EXTRA_EXCLUDE_COMPONENTS);
                ArrayList<ComponentName> srcExcludeComponents = getCompListfromParcelableList(pa);
                ArrayList<ComponentName> policyExcludeComponents = new ArrayList<>();
                if (this.mPersona != null) {
                    if (forwardfromKnox && userId == 0) {
                        policyExcludeComponents = this.mPersona.getExcludeComponentList(true, SemPersonaManager.isSecureFolderId(callingUserId));
                    } else if (callingUserId == 0) {
                        policyExcludeComponents = this.mPersona.getExcludeComponentList(false, SemPersonaManager.isSecureFolderId(userId));
                    }
                }
                ArrayList<ComponentName> finalExcludeComponents = getFinalExcludeCompList(srcExcludeComponents, policyExcludeComponents);
                if (finalExcludeComponents != null) {
                    ChooserIntent.putExtra(Intent.EXTRA_EXCLUDE_COMPONENTS, (Parcelable[]) finalExcludeComponents.toArray(new Parcelable[0]));
                }
                if (callingUserId == 0 && SemPersonaManager.isSecureFolderId(userId)) {
                    ArrayList<Uri> intentUris = getUriFromIntent(drivingIntent);
                    if (intentUris == null) {
                        Slog.wtf(TAG, "intentUris is null");
                    } else if (intentUris.size() < 250) {
                        Slog.wtf(TAG, "storeData");
                        if (isAbleToUsingCopyToSecureFolder(drivingIntent)) {
                            Slog.wtf(TAG, "register storeData");
                            Intent alternativeIntent = new Intent(drivingIntent);
                            alternativeIntent.setAction("com.sec.knox.action.storeData");
                            alternativeIntent.addFlags(50331649);
                            alternativeIntent.putExtra("crossProfileTargetUserId", callingUserId);
                            Parcelable[] addIntents = {alternativeIntent};
                            ChooserIntent.putExtra(Intent.EXTRA_ALTERNATE_INTENTS, addIntents);
                        }
                    } else {
                        Slog.wtf(TAG, "intentUris exceeds 250 | " + intentUris.size());
                    }
                }
                this.tempIntent = ChooserIntent;
                UserInfo ui = this.mUserManager.getUserInfo(userId);
                if (!SemPersonaManager.isSecureFolderId(userId) || !this.mKeyguardManager.isDeviceSecure(userId) || !this.mKeyguardManager.isDeviceLocked(userId) || ui.isSuperLocked()) {
                    startActivityAsCaller(ChooserIntent, null, false, userId);
                    return;
                } else {
                    showKeyguard(userId);
                    return;
                }
            }
            if (!SemPersonaManager.isSecureFolderId(userId)) {
                startActivityAsCaller(newIntent, null, false, userId);
            }
        } catch (RuntimeException e) {
            Slog.wtf(TAG, "Unable to launch as UID " + getLaunchedFromUid() + " package " + getLaunchedFromPackage() + ", while running in " + ActivityThread.currentProcessName(), e);
        }
    }

    private void launchChooserActivityWithCorrectTab(Intent intentReceived, String className) {
        int selectedProfile = findSelectedProfile(className);
        sanitizeIntent(intentReceived);
        intentReceived.putExtra("com.android.internal.app.ResolverActivity.EXTRA_SELECTED_PROFILE", selectedProfile);
        Intent innerIntent = (Intent) intentReceived.getParcelableExtra("android.intent.extra.INTENT", Intent.class);
        if (innerIntent == null) {
            Slog.wtf(TAG, "Cannot start a chooser intent with no extra android.intent.extra.INTENT");
            return;
        }
        sanitizeIntent(innerIntent);
        int userId = getUserId();
        innerIntent.fixUris(userId);
        intentReceived.putExtra("android.intent.extra.INTENT", innerIntent);
        if (SemPersonaManager.isSecureFolderId(userId)) {
            userId = 0;
        }
        startActivityAsCaller(intentReceived, null, false, userId);
        if (SemPersonaManager.isSecureFolderId(getUserId())) {
            showForwardFromSecureFolderToast();
        }
        finish();
    }

    private void launchResolverActivityWithCorrectTab(Intent intentReceived, String className, Intent newIntent, int callingUserId, int targetUserId, boolean singleTabOnly) {
        ResolveInfo callingResolveInfo = this.mInjector.resolveActivityAsUser(newIntent, 65536, callingUserId).join();
        int userId = isIntentForwarderResolveInfo(callingResolveInfo) ? targetUserId : callingUserId;
        int selectedProfile = findSelectedProfile(className);
        sanitizeIntent(intentReceived);
        intentReceived.putExtra("com.android.internal.app.ResolverActivity.EXTRA_SELECTED_PROFILE", selectedProfile);
        intentReceived.putExtra("com.android.internal.app.ResolverActivity.EXTRA_CALLING_USER", UserHandle.of(callingUserId));
        if (singleTabOnly) {
            intentReceived.putExtra("com.android.internal.app.ResolverActivity.EXTRA_RESTRICT_TO_SINGLE_USER", true);
        }
        startActivityAsCaller(intentReceived, null, false, userId);
        finish();
    }

    private int findSelectedProfile(String className) {
        if (className.equals(FORWARD_INTENT_TO_PARENT)) {
            return 0;
        }
        if (className.equals(FORWARD_INTENT_TO_MANAGED_PROFILE)) {
            return 1;
        }
        return -1;
    }

    private boolean shouldShowDisclosure(ResolveInfo ri, Intent intent) {
        if (!isDeviceProvisioned()) {
            return false;
        }
        if (ri == null || ri.activityInfo == null) {
            return true;
        }
        if (ri.activityInfo.applicationInfo.isSystemApp() && (isDialerIntent(intent) || isTextMessageIntent(intent))) {
            return false;
        }
        return true ^ isTargetResolverOrChooserActivity(ri.activityInfo);
    }

    private boolean isDeviceProvisioned() {
        return Settings.Global.getInt(getContentResolver(), "device_provisioned", 0) != 0;
    }

    private boolean isTextMessageIntent(Intent intent) {
        return (Intent.ACTION_SENDTO.equals(intent.getAction()) || isViewActionIntent(intent)) && ALLOWED_TEXT_MESSAGE_SCHEMES.contains(intent.getScheme());
    }

    private boolean isDialerIntent(Intent intent) {
        return Intent.ACTION_DIAL.equals(intent.getAction()) || Intent.ACTION_CALL.equals(intent.getAction()) || Intent.ACTION_CALL_PRIVILEGED.equals(intent.getAction()) || Intent.ACTION_CALL_EMERGENCY.equals(intent.getAction()) || (isViewActionIntent(intent) && "tel".equals(intent.getScheme()));
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

    static Intent canForward(Intent incomingIntent, int sourceUserId, int targetUserId, IPackageManager packageManager, ContentResolver contentResolver) {
        Intent forwardIntent = new Intent(incomingIntent);
        forwardIntent.addFlags(50331648);
        sanitizeIntent(forwardIntent);
        Intent intentToCheck = forwardIntent;
        if (Intent.ACTION_CHOOSER.equals(forwardIntent.getAction())) {
            return null;
        }
        if (forwardIntent.getSelector() != null) {
            intentToCheck = forwardIntent.getSelector();
        }
        String resolvedType = intentToCheck.resolveTypeIfNeeded(contentResolver);
        sanitizeIntent(intentToCheck);
        try {
        } catch (RemoteException e) {
            Slog.e(TAG, "PackageManagerService is dead?");
        }
        if (packageManager.canForwardTo(intentToCheck, resolvedType, sourceUserId, targetUserId)) {
            return forwardIntent;
        }
        return null;
    }

    private UserInfo getManagedProfile() {
        List<UserInfo> relatedUsers = this.mInjector.getUserManager().getProfiles(UserHandle.myUserId());
        for (UserInfo userInfo : relatedUsers) {
            if (userInfo.isManagedProfile()) {
                return userInfo;
            }
        }
        Slog.wtf(TAG, FORWARD_INTENT_TO_MANAGED_PROFILE + " has been called, but there is no managed profile");
        return null;
    }

    private UserInfo getPrivateProfile() {
        List<UserInfo> relatedUsers = this.mInjector.getUserManager().getProfiles(UserHandle.myUserId());
        for (UserInfo userInfo : relatedUsers) {
            if (userInfo.isPrivateProfile()) {
                return userInfo;
            }
        }
        return null;
    }

    private int getProfileParent() {
        UserInfo parent = this.mInjector.getUserManager().getProfileParent(UserHandle.myUserId());
        if (parent == null) {
            Slog.wtf(TAG, FORWARD_INTENT_TO_PARENT + " has been called, but there is no parent");
            return -10000;
        }
        return parent.id;
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

    private boolean isPrivateProfile(int userId) {
        UserInfo privateProfile = getPrivateProfile();
        return privateSpaceFlagsEnabled() && privateProfile != null && privateProfile.id == userId;
    }

    private boolean privateSpaceFlagsEnabled() {
        return Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures() && android.multiuser.Flags.enablePrivateSpaceIntentRedirection();
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
        public CompletableFuture<ResolveInfo> resolveActivityAsUser(final Intent intent, final int flags, final int userId) {
            return CompletableFuture.supplyAsync(new Supplier() { // from class: com.android.internal.app.IntentForwarderActivity$InjectorImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    ResolveInfo lambda$resolveActivityAsUser$0;
                    lambda$resolveActivityAsUser$0 = IntentForwarderActivity.InjectorImpl.this.lambda$resolveActivityAsUser$0(intent, flags, userId);
                    return lambda$resolveActivityAsUser$0;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ ResolveInfo lambda$resolveActivityAsUser$0(Intent intent, int flags, int userId) {
            return getPackageManager().resolveActivityAsUser(intent, flags, userId);
        }

        @Override // com.android.internal.app.IntentForwarderActivity.Injector
        public void showToast(String message, int duration) {
            Toast.makeText(IntentForwarderActivity.this, message, duration).show();
        }
    }

    private void showKeyguard(int userId) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.internal.app.IntentForwarderActivity.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    if (intent != null && intent.getAction() == null) {
                        return;
                    }
                    String action = intent.getAction();
                    Slog.e(IntentForwarderActivity.TAG, "onReceive, action : " + action);
                    if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
                        String reason = intent.getStringExtra("reason");
                        Slog.e(IntentForwarderActivity.TAG, "reason :" + reason);
                        if (IntentForwarderActivity.SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
                            IntentForwarderActivity.this.finish();
                        } else if (IntentForwarderActivity.SYSTEM_DIALOG_REASON_RECENT_APPS.equals(reason)) {
                            IntentForwarderActivity.this.finish();
                        }
                    }
                }
            }
        };
        registerReceiver(this.mBroadcastReceiver, filter);
        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        Intent confirmCredentialIntent = km.createConfirmDeviceCredentialIntent(null, null, userId);
        if (confirmCredentialIntent == null) {
            return;
        }
        this.mWaitingForresponse = true;
        startActivityForResult(confirmCredentialIntent, 1);
    }

    @Override // android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mWaitingForresponse = false;
        if (requestCode == 1) {
            if (resultCode == -1) {
                Slog.e(TAG, "unlock keyguard");
                StrictMode.disableDeathOnFileUriExposure();
                try {
                    startActivityAsCaller(this.tempIntent, null, false, this.mFinalTargetUserId);
                } finally {
                    StrictMode.enableDeathOnFileUriExposure();
                }
            } else {
                Slog.e(TAG, "cancel keyguard");
            }
        } else {
            if (this.mBroadcastReceiver != null) {
                unregisterReceiver(this.mBroadcastReceiver);
                this.mBroadcastReceiver = null;
            }
            Slog.e(TAG, "unknown response");
        }
        finish();
    }

    private boolean isAbleToUsingCopyToSecureFolder(Intent intent) {
        boolean isPossibleToStoreIntoB2C = false;
        Parcelable[] pa = intent.getParcelableArrayExtra(Intent.EXTRA_EXCLUDE_COMPONENTS);
        String classNameSF = SemPersonaManager.SECUREFOLDER_PACKAGE + ".switcher.B2CStoreFilesActivity";
        if (pa != null) {
            for (int i = 0; i < pa.length; i++) {
                if (pa[i] instanceof ComponentName) {
                    ComponentName excludeComp = (ComponentName) pa[i];
                    if (SemPersonaManager.SECUREFOLDER_PACKAGE.equals(excludeComp.getPackageName()) && classNameSF.equals(excludeComp.getClassName())) {
                        Slog.d(TAG, "isWantToB2CStore | not support " + excludeComp.getPackageName());
                        return false;
                    }
                }
            }
        }
        ArrayList<Uri> srcUris = getUriFromIntent(intent);
        if (srcUris != null && srcUris.size() > 0) {
            int i2 = 0;
            while (true) {
                if (i2 >= srcUris.size()) {
                    break;
                }
                Uri tempUri = srcUris.get(i2);
                if (tempUri != null) {
                    if (tempUri.getUserInfo() != null && !tempUri.getUserInfo().equals(String.valueOf(0))) {
                        isPossibleToStoreIntoB2C = false;
                        Slog.d(TAG, "isAbleToUsingCopyToSecureFolder | false | due to userId | " + tempUri.getUserInfo());
                        break;
                    }
                    if ("file".equals(tempUri.getScheme())) {
                        isPossibleToStoreIntoB2C = true;
                    } else if ("content".equals(tempUri.getScheme())) {
                        String authority = tempUri.getAuthority();
                        if (authority != null) {
                            if (authority.contains("@")) {
                                int nPos = authority.lastIndexOf("@");
                                authority = authority.substring(nPos + 1);
                            }
                            if (authority.toLowerCase().startsWith("media")) {
                                isPossibleToStoreIntoB2C = true;
                            } else {
                                isPossibleToStoreIntoB2C = false;
                                Slog.d(TAG, "isAbleToUsingCopyToSecureFolder | false | " + tempUri);
                                break;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        isPossibleToStoreIntoB2C = false;
                        Slog.d(TAG, "isAbleToUsingCopyToSecureFolder | false | " + tempUri);
                        break;
                    }
                }
                i2++;
            }
        }
        Slog.d(TAG, "isAbleToUsingCopyToSecureFolder | " + isPossibleToStoreIntoB2C);
        return isPossibleToStoreIntoB2C;
    }

    private ArrayList<ComponentName> getCompListfromParcelableList(Parcelable[] pa) {
        ArrayList<ComponentName> compArray = null;
        if (pa != null && pa.length > 0) {
            compArray = new ArrayList<>();
            for (int i = 0; i < pa.length; i++) {
                if (pa[i] instanceof ComponentName) {
                    compArray.add((ComponentName) pa[i]);
                }
            }
        }
        return compArray;
    }

    private ArrayList<ComponentName> getFinalExcludeCompList(ArrayList<ComponentName> src_exclcomp, ArrayList<ComponentName> policy_exclcomp) {
        ArrayList<ComponentName> finalCompArray = null;
        if (src_exclcomp != null && src_exclcomp.size() > 0) {
            finalCompArray = new ArrayList<>();
            for (int i = 0; i < src_exclcomp.size(); i++) {
                finalCompArray.add(src_exclcomp.get(i));
                Slog.wtf(TAG, "Filtered src excl component #" + i + " a ComponentName: " + src_exclcomp.get(i));
            }
        }
        if (policy_exclcomp != null && policy_exclcomp.size() > 0) {
            if (finalCompArray == null) {
                finalCompArray = new ArrayList<>();
            }
            for (int i2 = 0; i2 < policy_exclcomp.size(); i2++) {
                finalCompArray.add(policy_exclcomp.get(i2));
                Slog.wtf(TAG, "Filtered policy excl component #" + i2 + " a ComponentName: " + policy_exclcomp.get(i2));
            }
        }
        return finalCompArray;
    }

    private ArrayList<Uri> getUriFromIntent(Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SEND)) {
            ArrayList<Uri> uris = new ArrayList<>();
            uris.add((Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM));
            return uris;
        }
        if (!intent.getAction().equals(Intent.ACTION_SEND_MULTIPLE)) {
            return null;
        }
        return intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
    }
}
