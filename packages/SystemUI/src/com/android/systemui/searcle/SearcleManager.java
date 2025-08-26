package com.android.systemui.searcle;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.widget.Toast;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.views.SamsungNavigationBarView;
import com.android.systemui.searcle.SearcleManager;
import com.android.systemui.searcle.omni.SimpleBroadcastReceiver;
import com.android.systemui.util.ContextUtil;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.CustomDeviceManager;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes2.dex */
public final class SearcleManager {
    public final Context context;
    public final BroadcastDispatcher ctsBroadcastDispatcher;
    public final SearcleManager$ctsBroadcastReceiver$1 ctsBroadcastReceiver;
    public int currentDownCount;
    public final SemDesktopModeManager desktopModeManager;
    public CTSPackageState isInstalledCTSApp;
    public final boolean isSupportDCMotor;
    public boolean isUnavailableSearchApp;
    public SamsungNavigationBarView navigationBarView;
    public final SearcleTipPopup tipPopup;
    public Toast toast;
    public CharSequence toastMsg;
    public final Vibrator vibrator;
    public String invokedPackageName = "";
    public final SysUiState sysUiState = (SysUiState) Dependency.sDependency.getDependencyInner(SysUiState.class);
    private final SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
    public final KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);

    /* renamed from: com.android.systemui.searcle.SearcleManager$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SearcleManager.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Context context = SearcleManager.this.context;
            OmniAPI.mContext = context;
            Log.i("OmniAPI", "requestUpdateOmniPackageInfo");
            OmniAPI.THREAD_POOL_EXECUTOR.execute(new OmniAPI$$ExternalSyntheticLambda1());
            SimpleBroadcastReceiver simpleBroadcastReceiver = OmniAPI.mOmniPackageReceiver;
            String[] strArr = {"android.intent.action.PACKAGE_ADDED", "android.intent.action.PACKAGE_CHANGED", "android.intent.action.PACKAGE_REMOVED"};
            simpleBroadcastReceiver.getClass();
            IntentFilter intentFilter = new IntentFilter();
            for (int i = 0; i < 3; i++) {
                intentFilter.addAction(strArr[i]);
            }
            intentFilter.addDataScheme("package");
            if (!TextUtils.isEmpty("com.google.android.googlequicksearchbox")) {
                intentFilter.addDataSchemeSpecificPart("com.google.android.googlequicksearchbox", 0);
            }
            context.registerReceiver(simpleBroadcastReceiver, intentFilter, 2);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class CTSPackageState {
        public static final /* synthetic */ CTSPackageState[] $VALUES;
        public static final CTSPackageState INSTALLED;
        public static final CTSPackageState UNINSTALLED;
        public static final CTSPackageState UNKNOWN;

        static {
            CTSPackageState cTSPackageState = new CTSPackageState("UNKNOWN", 0);
            UNKNOWN = cTSPackageState;
            CTSPackageState cTSPackageState2 = new CTSPackageState("INSTALLED", 1);
            INSTALLED = cTSPackageState2;
            CTSPackageState cTSPackageState3 = new CTSPackageState("UNINSTALLED", 2);
            UNINSTALLED = cTSPackageState3;
            CTSPackageState[] cTSPackageStateArr = {cTSPackageState, cTSPackageState2, cTSPackageState3};
            $VALUES = cTSPackageStateArr;
            EnumEntriesKt.enumEntries(cTSPackageStateArr);
        }

        private CTSPackageState(String str, int i) {
        }

        public static CTSPackageState valueOf(String str) {
            return (CTSPackageState) Enum.valueOf(CTSPackageState.class, str);
        }

        public static CTSPackageState[] values() {
            return (CTSPackageState[]) $VALUES.clone();
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r5v5, types: [com.android.systemui.searcle.SearcleManager$ctsBroadcastReceiver$1] */
    public SearcleManager(Context context) {
        this.context = context;
        Object systemService = context.getSystemService("desktopmode");
        this.desktopModeManager = systemService instanceof SemDesktopModeManager ? (SemDesktopModeManager) systemService : null;
        Object systemService2 = context.getSystemService("vibrator");
        Vibrator vibrator = systemService2 instanceof Vibrator ? (Vibrator) systemService2 : null;
        this.vibrator = vibrator;
        this.tipPopup = new SearcleTipPopup(context);
        this.isInstalledCTSApp = CTSPackageState.UNKNOWN;
        this.ctsBroadcastDispatcher = (BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class);
        this.ctsBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.searcle.SearcleManager$ctsBroadcastReceiver$1
            public static final void onReceive$restSearcleTip(SearcleManager searcleManager, Context context2) {
                SearcleTipPopup searcleTipPopup = searcleManager.tipPopup;
                if (searcleTipPopup.isTipPopupShowing) {
                    searcleTipPopup.hideImmediate();
                }
                searcleManager.currentDownCount = 0;
                SearcleTipPopupUtil.INSTANCE.getClass();
                Prefs.get(context2).edit().remove("SearcleTipFirstSeenTime").apply();
                Prefs.get(context2).edit().remove("SearcleTipCount").apply();
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String schemeSpecificPart;
                if (intent.getData() != null) {
                    Uri data = intent.getData();
                    data.getClass();
                    schemeSpecificPart = data.getSchemeSpecificPart();
                } else {
                    schemeSpecificPart = null;
                }
                if (Intrinsics.areEqual(schemeSpecificPart, "android.permissionui.cts") || Intrinsics.areEqual(schemeSpecificPart, "android.voicerecognition.cts") || Intrinsics.areEqual(schemeSpecificPart, "android.input.cts") || Intrinsics.areEqual(schemeSpecificPart, "com.google.android.permissionui.gts")) {
                    Log.d("SearcleManager", "onReceive action = " + intent.getAction());
                    String action = intent.getAction();
                    if (action != null) {
                        int iHashCode = action.hashCode();
                        if (iHashCode == 525384130) {
                            if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                                SearcleManager searcleManager = this.this$0;
                                searcleManager.isInstalledCTSApp = SearcleManager.CTSPackageState.UNINSTALLED;
                                onReceive$restSearcleTip(searcleManager, context2);
                                return;
                            }
                            return;
                        }
                        if (iHashCode == 1544582882 && action.equals("android.intent.action.PACKAGE_ADDED")) {
                            SearcleManager searcleManager2 = this.this$0;
                            searcleManager2.isInstalledCTSApp = SearcleManager.CTSPackageState.INSTALLED;
                            onReceive$restSearcleTip(searcleManager2, context2);
                        }
                    }
                }
            }
        };
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(DefaultIoScheduler.INSTANCE), null, null, new AnonymousClass1(null), 3);
        if (vibrator != null) {
            this.isSupportDCMotor = vibrator.semGetSupportedVibrationType() == 1;
        }
    }

    public static final int access$getOmniEntryPoint(SearcleManager searcleManager) {
        return !Intrinsics.areEqual(searcleManager.settingsHelper.isNavigationMode(), "0") ? 1 : 2;
    }

    public final boolean isAppInstalled(String str) throws PackageManager.NameNotFoundException {
        try {
            this.context.getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final boolean isSupportBixbyTouch() {
        return BasicRune.SUPPORT_BIXBY_TOUCH && !this.settingsHelper.isCNSupportCTS();
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0251  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01fe  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startSearcleByHomeKey(boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        String packageName;
        SemDesktopModeState desktopModeState;
        if (!this.settingsHelper.isSearcleEnabled() || !this.settingsHelper.isSearchAllEntryPointsEnabled()) {
            return;
        }
        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("startSearcleByHomeKey down = ", ", longPress = ", "SearcleManager", z, z2);
        boolean z5 = true;
        if (!z2) {
            if (z || z2) {
                return;
            }
            boolean z6 = this.isUnavailableSearchApp;
            if (z6) {
                EmergencyButtonController$$ExternalSyntheticOutline0.m("startSearcleByHomeKey isUnavailableSearchApp = ", "SearcleManager", z6);
                this.isUnavailableSearchApp = false;
                this.toastMsg = null;
                this.toast = null;
            }
            int i = this.currentDownCount;
            SearcleTipPopupUtil searcleTipPopupUtil = SearcleTipPopupUtil.INSTANCE;
            Context context = this.context;
            searcleTipPopupUtil.getClass();
            Log.d("SearcleManager", MutableVectorKt$$ExternalSyntheticOutline0.m(i, SearcleTipPopupUtil.getSearcleTipCount(context), "startSearcleByHomeKey currentDownCount = ", " tipShownCount = ", " "));
            SearcleTipPopup searcleTipPopup = this.tipPopup;
            if (searcleTipPopup.isTipPopupShowing) {
                return;
            }
            if (this.isInstalledCTSApp == CTSPackageState.UNKNOWN) {
                this.isInstalledCTSApp = (isAppInstalled("android.permissionui.cts") || isAppInstalled("android.voicerecognition.cts") || isAppInstalled("android.input.cts") || isAppInstalled("com.google.android.permissionui.gts")) ? CTSPackageState.INSTALLED : CTSPackageState.UNINSTALLED;
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
                intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                intentFilter.addDataScheme("package");
                intentFilter.addDataSchemeSpecificPart("android.permissionui.cts", 0);
                intentFilter.addDataSchemeSpecificPart("android.voicerecognition.cts", 0);
                intentFilter.addDataSchemeSpecificPart("android.input.cts", 0);
                intentFilter.addDataSchemeSpecificPart("com.google.android.permissionui.gts", 0);
                Unit unit = Unit.INSTANCE;
                BroadcastDispatcher.registerReceiver$default(this.ctsBroadcastDispatcher, this.ctsBroadcastReceiver, intentFilter, null, null, 0, null, 60);
            }
            Log.d("SearcleManager", "startSearcleByHomeKey isInstalledCTSApp = " + this.isInstalledCTSApp);
            if ((SearcleTipPopupUtil.getSearcleTipCount(this.context) < 2) && this.isInstalledCTSApp == CTSPackageState.UNINSTALLED) {
                int i2 = this.currentDownCount + 1;
                int searcleTipCount = SearcleTipPopupUtil.getSearcleTipCount(this.context);
                String timeFormatString = SearcleTipPopupUtil.getTimeFormatString(Prefs.get(this.context).getLong("SearcleTipFirstSeenTime", 0L));
                String timeFormatString2 = SearcleTipPopupUtil.getTimeFormatString(System.currentTimeMillis());
                StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i2, searcleTipCount, "startSearcleByHomeKey currentDownCount = ", " tipShownCount = ", " firstShownTime = ");
                sbM.append(timeFormatString);
                sbM.append(" currentTime = ");
                sbM.append(timeFormatString2);
                Log.d("SearcleManager", sbM.toString());
                if (SearcleTipPopupUtil.getSearcleTipCount(this.context) == 0) {
                    int i3 = this.currentDownCount + 1;
                    this.currentDownCount = i3;
                    if (i3 >= 10) {
                        searcleTipPopup.showSearcleTip(false);
                        Prefs.get(this.context).edit().putLong("SearcleTipFirstSeenTime", System.currentTimeMillis()).apply();
                        return;
                    }
                    return;
                }
                Context context2 = this.context;
                int searcleTipCount2 = SearcleTipPopupUtil.getSearcleTipCount(context2);
                if (searcleTipCount2 != 0 && (searcleTipCount2 != 1 || System.currentTimeMillis() - Prefs.get(context2).getLong("SearcleTipFirstSeenTime", 0L) < 259200000)) {
                    z5 = false;
                }
                if (z5) {
                    Log.d("SearcleManager", "startSearcleByHomeKey remind popup!");
                    searcleTipPopup.showSearcleTip(false);
                    this.ctsBroadcastDispatcher.unregisterReceiver(this.ctsBroadcastReceiver);
                    return;
                }
                return;
            }
            return;
        }
        SemDesktopModeManager semDesktopModeManager = this.desktopModeManager;
        if (semDesktopModeManager != null && (desktopModeState = semDesktopModeManager.getDesktopModeState()) != null && desktopModeState.enabled == 4) {
            Log.i("SearcleManager", "isUnAvailableSearcle = dex mode");
        } else if (BasicRune.MAINTENANCE_MODE) {
            Log.i("SearcleManager", "isUnAvailableSearcle = maintenance mode");
        } else if (this.keyguardUpdateMonitor.isKidsModeRunning()) {
            Log.i("SearcleManager", "isUnAvailableSearcle = kids mode");
            this.toastMsg = this.context.getString(R.string.searcle_can_not_open_kids_mode);
        } else {
            if (CustomDeviceManager.getInstance().getProKioskManager().getProKioskState()) {
                Log.d("SearcleManager", "isKioskMode : proKiosk mode");
            } else {
                try {
                    if (EnterpriseDeviceManager.getInstance(this.context).getKioskMode().isKioskModeEnabled()) {
                        Log.d("SearcleManager", "isKioskMode : Kiosk mode");
                    }
                } catch (SecurityException e) {
                    Log.w("SearcleManager", "SecurityException: " + e);
                }
                boolean zIsNavigationBarGestureWhileHidden = this.settingsHelper.isNavigationBarGestureWhileHidden();
                ActivityManager activityManager = ContextUtil.INSTANCE.getActivityManager(this.context);
                if (activityManager != null && activityManager.isInLockTaskMode()) {
                    Log.i("SearcleManager", "isUnAvailableSearcle = Pinned app");
                    this.toastMsg = this.context.getString(zIsNavigationBarGestureWhileHidden ? R.string.sec_screen_pinning_toast_gesture_nav : R.string.sec_screen_pinning_toast);
                } else if (this.settingsHelper.isOneHandModeRunning()) {
                    Log.i("SearcleManager", "isUnAvailableSearcle = one hand mode");
                    if (isSupportBixbyTouch()) {
                        Context context3 = this.context;
                        this.toastMsg = context3.getString(R.string.ps_can_not_open_one_hand_mode, context3.getString(R.string.touch_to_search_label));
                    } else {
                        this.toastMsg = this.context.getString(R.string.searcle_can_not_open_one_hand_mode);
                    }
                } else {
                    SysUiState sysUiState = this.sysUiState;
                    if ((sysUiState.getFlags() & 2052) != 0) {
                        Log.i("SearcleManager", "isUnAvailableSearcle = notification shade");
                    } else {
                        if (!zIsNavigationBarGestureWhileHidden || (sysUiState.getFlags() & 584) == 0) {
                            if (isSupportBixbyTouch()) {
                                Context context4 = this.context;
                                if (BixbyTouchAPI.sActivityManager == null) {
                                    BixbyTouchAPI.sActivityManager = (ActivityManager) context4.getSystemService("activity");
                                }
                                boolean zIsVisible = false;
                                for (ActivityManager.RunningTaskInfo runningTaskInfo : BixbyTouchAPI.sActivityManager.getRunningTasks(zIsNavigationBarGestureWhileHidden ? 2 : 1)) {
                                    ComponentName componentName = runningTaskInfo.topActivity;
                                    if (componentName != null && "com.samsung.android.bixbytouch".equals(componentName.getPackageName()) && ("com.samsung.android.bixbytouch.activity.ShowCPCardsActivity".equals(componentName.getClassName()) || "com.samsung.android.bixbytouch.activity.WelcomePageActivity".equals(componentName.getClassName()))) {
                                        zIsVisible = runningTaskInfo.isVisible();
                                    }
                                }
                                if (!zIsVisible) {
                                    if (BixbyTouchAPI.sActivityManager == null) {
                                        BixbyTouchAPI.sActivityManager = (ActivityManager) context4.getSystemService("activity");
                                    }
                                    List<ActivityManager.RunningServiceInfo> runningServices = BixbyTouchAPI.sActivityManager.getRunningServices(Integer.MAX_VALUE);
                                    if (runningServices != null) {
                                        Iterator<ActivityManager.RunningServiceInfo> it = runningServices.iterator();
                                        while (true) {
                                            if (it.hasNext()) {
                                                if ("com.samsung.android.bixbytouch.service.ScrollDetectUIService".equals(it.next().service.getClassName())) {
                                                    zIsVisible = true;
                                                    break;
                                                }
                                            } else {
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (zIsVisible) {
                                    Log.i("SearcleManager", "isUnAvailableSearcle = isLaunchingBixbyTouch");
                                }
                            }
                            z3 = true;
                            if (z3) {
                                if (BasicRune.SUPPORT_SEARCLE || (BasicRune.SUPPORT_BIXBY_TOUCH && this.settingsHelper.isCNSupportCTS())) {
                                    if (!OmniAPI.mIsOmniPackageEnabled) {
                                        this.toastMsg = this.context.getString(R.string.searcle_can_not_open_google_app_is_disabled);
                                    }
                                    if (!OmniAPI.mIsOmniPackageEnabled) {
                                    }
                                }
                                z4 = false;
                            } else {
                                z4 = true;
                            }
                            this.isUnavailableSearchApp = z4;
                            if (!z4) {
                                Log.d("SearcleManager", "startSearcleByHomeKey isUnavailableSearchApp = " + z4);
                                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.searcle.SearcleManager$showToast$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        SearcleManager searcleManager = this.this$0;
                                        CharSequence charSequence = searcleManager.toastMsg;
                                        if (charSequence != null) {
                                            Toast toast = searcleManager.toast;
                                            if (toast != null) {
                                                toast.cancel();
                                                toast.setText(charSequence);
                                            }
                                            Toast toastMakeText = Toast.makeText(searcleManager.context, charSequence, 0);
                                            searcleManager.toast = toastMakeText;
                                            if (toastMakeText != null) {
                                                toastMakeText.show();
                                            }
                                        }
                                    }
                                });
                                return;
                            }
                            if (!this.settingsHelper.isNavigationBarGestureWhileHidden()) {
                                ActivityManager activityManager2 = ContextUtil.INSTANCE.getActivityManager(this.context);
                                String str = "";
                                if (activityManager2 != null) {
                                    try {
                                        Iterator<ActivityManager.RunningTaskInfo> it2 = activityManager2.getRunningTasks(1).iterator();
                                        if (it2.hasNext()) {
                                            ComponentName componentName2 = it2.next().topActivity;
                                            if (componentName2 != null && (packageName = componentName2.getPackageName()) != null) {
                                                str = packageName;
                                            }
                                        } else {
                                            Unit unit2 = Unit.INSTANCE;
                                        }
                                    } catch (SecurityException e2) {
                                        Log.w("SearcleManager", "getTopActivity SecurityException " + e2);
                                    }
                                }
                                this.invokedPackageName = str;
                            }
                            StringBuilder sbM2 = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("invokeSearcle invokedPackageName = ", this.invokedPackageName, ", isSupportedDCMotor = ");
                            boolean z7 = this.isSupportDCMotor;
                            sbM2.append(z7);
                            Log.d("SearcleManager", sbM2.toString());
                            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.searcle.SearcleManager$invokeSearcle$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SearcleTipPopup searcleTipPopup2 = this.this$0.tipPopup;
                                    if (searcleTipPopup2.isTipPopupShowing) {
                                        searcleTipPopup2.hide();
                                    }
                                    SearcleTipPopupUtil searcleTipPopupUtil2 = SearcleTipPopupUtil.INSTANCE;
                                    Context context5 = this.this$0.context;
                                    searcleTipPopupUtil2.getClass();
                                    if (SearcleTipPopupUtil.getSearcleTipCount(context5) < 2) {
                                        Log.d("SearcleManager", "invokeSearcle skip remind");
                                        Prefs.putInt(this.this$0.context, "SearcleTipCount", 2);
                                        SearcleManager searcleManager = this.this$0;
                                        if (searcleManager.isInstalledCTSApp != SearcleManager.CTSPackageState.UNKNOWN) {
                                            searcleManager.ctsBroadcastDispatcher.unregisterReceiver(searcleManager.ctsBroadcastReceiver);
                                        }
                                    }
                                }
                            });
                            if (z7) {
                                Vibrator vibrator = this.vibrator;
                                if (vibrator != null) {
                                    vibrator.vibrate(VibrationEffect.semCreateWaveform(HapticFeedbackConstants.semGetVibrationIndex(100), -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH));
                                }
                            } else {
                                DefaultScheduler defaultScheduler = Dispatchers.Default;
                                BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(DefaultIoScheduler.INSTANCE), null, null, new SearcleManager$invokeSearcle$2(this, null), 3);
                            }
                            if (isSupportBixbyTouch()) {
                                Context context5 = this.context;
                                try {
                                    Intent intent = new Intent("com.samsung.android.bixbytouch.ACTION_START_CIRCLE_TO_SEARCH");
                                    intent.setPackage("com.samsung.android.bixbytouch");
                                    context5.startServiceAsUser(intent, UserHandle.CURRENT);
                                } catch (Exception e3) {
                                    Log.e("BixbyTouchAPI", "invokeBixbyTouch", e3);
                                }
                            } else {
                                if (!BasicRune.SUPPORT_SEARCLE && (!BasicRune.SUPPORT_BIXBY_TOUCH || !this.settingsHelper.isCNSupportCTS())) {
                                    z5 = false;
                                }
                                if (z5) {
                                    DefaultScheduler defaultScheduler2 = Dispatchers.Default;
                                    BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(DefaultIoScheduler.INSTANCE), null, null, new SearcleManager$invokeSearcle$3(this, null), 3);
                                } else {
                                    Log.d("SearcleManager", "invokeSearcle not support search api");
                                }
                            }
                            if (this.isUnavailableSearchApp && this.settingsHelper.isNavigationBarGestureWhileHidden()) {
                                this.isUnavailableSearchApp = false;
                                this.toastMsg = null;
                                this.toast = null;
                                return;
                            }
                            return;
                        }
                        Log.i("SearcleManager", "isUnAvailableSearcle = keyguard");
                        if (isSupportBixbyTouch()) {
                            Context context6 = this.context;
                            this.toastMsg = context6.getString(R.string.ps_can_not_open_lockscreen, context6.getString(R.string.touch_to_search_label));
                        } else {
                            this.toastMsg = this.context.getString(R.string.searcle_can_not_open_lockscreen);
                        }
                    }
                }
            }
            Log.i("SearcleManager", "isUnAvailableSearcle = kiosk mode");
        }
        z3 = false;
        if (z3) {
        }
        this.isUnavailableSearchApp = z4;
        if (!z4) {
        }
    }
}
