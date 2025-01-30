package com.android.systemui.searcle;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.plugins.circle2search.BixbyTouchCircle2SearchAPI;
import com.android.systemui.plugins.omni.AssistStateManager;
import com.android.systemui.searcle.OmniAPI;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.volume.util.SystemServiceExtension;
import com.samsung.android.app.SemMultiWindowManager;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.CustomDeviceManager;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SearcleManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public int currentDownCount;
    public final SemDesktopModeManager desktopModeManager;
    public boolean isUnavailableSearchApp;
    public final KeyguardUpdateMonitor keyguradUpdateMonitor;
    public NavigationBarView navigationBarView;
    public final SettingsHelper settingsHelper;
    public final SearcleTipPopup tipPopup;
    public Toast toast;
    public CharSequence toastMsg;
    public String invokedPackageName = "";
    public final SysUiState sysUiState = (SysUiState) Dependency.get(SysUiState.class);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.searcle.SearcleManager$1", m277f = "SearcleManager.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.searcle.SearcleManager$1 */
    public static final class C23601 extends SuspendLambda implements Function2 {
        int label;

        public C23601(Continuation<? super C23601> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SearcleManager.this.new C23601(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C23601) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Context context = SearcleManager.this.context;
            Intent intent = OmniAPI.INTENT_ACTION_ASSIST;
            OmniAPI.mAssistStateManager = AssistStateManager.INSTANCE.lambda$get$0(context);
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[OmniAPI.UnexecutableType.values().length];
            try {
                iArr[OmniAPI.UnexecutableType.SERVICE_UNAVAILABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[OmniAPI.UnexecutableType.GOOGLE_APP_DISABLED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[OmniAPI.UnexecutableType.GOOGLE_IS_NOT_DIGITAL_ASSISTANT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public SearcleManager(Context context) {
        this.context = context;
        new SemMultiWindowManager();
        this.settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        this.keyguradUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
        Object systemService = context.getSystemService("desktopmode");
        this.desktopModeManager = systemService instanceof SemDesktopModeManager ? (SemDesktopModeManager) systemService : null;
        this.tipPopup = new SearcleTipPopup(context);
        BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.f667IO), null, null, new C23601(null), 3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0039, code lost:
    
        if (r1 == null) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void invokeSearcle() {
        String str;
        SettingsHelper settingsHelper = this.settingsHelper;
        boolean isNavigationBarGestureWhileHidden = settingsHelper.isNavigationBarGestureWhileHidden();
        Context context = this.context;
        if (!isNavigationBarGestureWhileHidden) {
            try {
                SystemServiceExtension.INSTANCE.getClass();
                Iterator<ActivityManager.RunningTaskInfo> it = ((ActivityManager) context.getSystemService(ActivityManager.class)).getRunningTasks(1).iterator();
                if (it.hasNext()) {
                    ComponentName componentName = it.next().topActivity;
                    str = componentName != null ? componentName.getPackageName() : null;
                }
            } catch (SecurityException e) {
                Log.w("SearcleManager", "getTopActivity SecurityException " + e);
            }
            str = "";
            this.invokedPackageName = str;
        }
        Log.d("SearcleManager", "invokeSearcle invokedPackageName = " + this.invokedPackageName);
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.searcle.SearcleManager$invokeSearcle$1
            @Override // java.lang.Runnable
            public final void run() {
                SearcleTipPopup searcleTipPopup = SearcleManager.this.tipPopup;
                if (searcleTipPopup.isTipPopupShowing) {
                    searcleTipPopup.hide();
                }
                SearcleTipPopupUtil searcleTipPopupUtil = SearcleTipPopupUtil.INSTANCE;
                Context context2 = SearcleManager.this.context;
                searcleTipPopupUtil.getClass();
                if (Prefs.getInt(context2, "SearcleTipCount", 0) < 2) {
                    Log.d("SearcleManager", "invokeSearcle skip remind");
                    Prefs.putInt(SearcleManager.this.context, "SearcleTipCount", 2);
                }
            }
        });
        DefaultIoScheduler defaultIoScheduler = Dispatchers.f667IO;
        BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(defaultIoScheduler), null, null, new SearcleManager$invokeSearcle$2(this, null), 3);
        if (isSupportTouchToSearch()) {
            BixbyTouchCircle2SearchAPI.invokeCircle2Search(context);
        } else {
            BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(defaultIoScheduler), null, null, new SearcleManager$invokeSearcle$3(this, null), 3);
        }
        if (this.isUnavailableSearchApp && settingsHelper.isNavigationBarGestureWhileHidden()) {
            this.isUnavailableSearchApp = false;
            this.toastMsg = null;
            this.toast = null;
        }
    }

    public final boolean isSupportTouchToSearch() {
        boolean z = BasicRune.BIXBY_TOUCH_SUPPORT_CIRCLE2SEARCH;
        if (!z) {
            return false;
        }
        SettingsHelper settingsHelper = this.settingsHelper;
        settingsHelper.getClass();
        return !(z && settingsHelper.mItemLists.get("cn_support_circe_to_search").getIntValue() == 1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0165, code lost:
    
        if ((r4 && r0.mItemLists.get("cn_support_circe_to_search").getIntValue() == 1) != false) goto L77;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0086  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isUnavailableSearchAppCheck() {
        boolean z;
        boolean z2;
        boolean z3;
        OmniAPI.UnexecutableType unexecutableType;
        boolean z4;
        SemDesktopModeState desktopModeState;
        SettingsHelper settingsHelper = this.settingsHelper;
        Context context = this.context;
        SemDesktopModeManager semDesktopModeManager = this.desktopModeManager;
        if (semDesktopModeManager != null && (desktopModeState = semDesktopModeManager.getDesktopModeState()) != null && desktopModeState.enabled == 4) {
            Log.i("SearcleManager", "isUnAvailableSearcle = dex mode");
        } else if (BasicRune.MAINTENANCE_MODE) {
            Log.i("SearcleManager", "isUnAvailableSearcle = maintenance mode");
        } else if (this.keyguradUpdateMonitor.isKidsModeRunning()) {
            Log.i("SearcleManager", "isUnAvailableSearcle = kids mode");
            this.toastMsg = context.getString(R.string.searcle_can_not_open_kids_mode);
        } else {
            if (CustomDeviceManager.getInstance().getProKioskManager().getProKioskState()) {
                Log.d("SearcleManager", "isKioskMode : proKiosk mode");
            } else {
                try {
                    if (EnterpriseDeviceManager.getInstance(context).getKioskMode().isKioskModeEnabled()) {
                        Log.d("SearcleManager", "isKioskMode : Kiosk mode");
                    }
                } catch (SecurityException e) {
                    Log.w("SearcleManager", "SecurityException: " + e);
                }
                z = false;
                if (z) {
                    boolean isNavigationBarGestureWhileHidden = settingsHelper.isNavigationBarGestureWhileHidden();
                    SystemServiceExtension.INSTANCE.getClass();
                    if (((ActivityManager) context.getSystemService(ActivityManager.class)).isInLockTaskMode()) {
                        Log.i("SearcleManager", "isUnAvailableSearcle = Pinned app");
                        this.toastMsg = context.getString(isNavigationBarGestureWhileHidden ? R.string.sec_screen_pinning_toast_gesture_nav : R.string.sec_screen_pinning_toast);
                    } else if (settingsHelper.isOneHandModeRunning()) {
                        Log.i("SearcleManager", "isUnAvailableSearcle = one hand mode");
                        if (isSupportTouchToSearch()) {
                            this.toastMsg = context.getString(R.string.ps_can_not_open_one_hand_mode, context.getString(R.string.touch_to_search_label));
                        } else {
                            this.toastMsg = context.getString(R.string.searcle_can_not_open_one_hand_mode);
                        }
                    } else {
                        long j = this.sysUiState.mFlags;
                        if ((2052 & j) != 0) {
                            Log.i("SearcleManager", "isUnAvailableSearcle = notification shade");
                        } else {
                            if (isNavigationBarGestureWhileHidden) {
                                if ((j & 584) != 0) {
                                    Log.i("SearcleManager", "isUnAvailableSearcle = keyguard");
                                    if (isSupportTouchToSearch()) {
                                        this.toastMsg = context.getString(R.string.ps_can_not_open_lockscreen, context.getString(R.string.touch_to_search_label));
                                    } else {
                                        this.toastMsg = context.getString(R.string.searcle_can_not_open_lockscreen);
                                    }
                                }
                            }
                            if (!isSupportTouchToSearch() || !BixbyTouchCircle2SearchAPI.isLaunchingCircle2Search(context, isNavigationBarGestureWhileHidden)) {
                                z2 = true;
                                if (z2) {
                                    return true;
                                }
                                if (!BasicRune.SUPPORT_SEARCLE) {
                                    boolean z5 = BasicRune.BIXBY_TOUCH_SUPPORT_CIRCLE2SEARCH;
                                    if (z5) {
                                        settingsHelper.getClass();
                                    }
                                    z3 = false;
                                    if (z3) {
                                        if (OmniAPI.mAssistStateManager.isOmniPackageEnabled()) {
                                            Optional<Boolean> isCsHelperAvailable = OmniAPI.mAssistStateManager.isCsHelperAvailable();
                                            Optional<Boolean> isVisAvailable = OmniAPI.mAssistStateManager.isVisAvailable();
                                            if (isCsHelperAvailable.isPresent() || isVisAvailable.isPresent()) {
                                                Boolean bool = Boolean.FALSE;
                                                if (!isCsHelperAvailable.orElse(bool).booleanValue()) {
                                                    boolean booleanValue = isVisAvailable.orElse(bool).booleanValue();
                                                    if (!booleanValue) {
                                                        Log.i("OmniAPI", "isInvokeAvailable : Omni not available");
                                                        unexecutableType = OmniAPI.UnexecutableType.SERVICE_UNAVAILABLE;
                                                    } else if (booleanValue && !OmniAPI.isGoogleDefaultAssistant(context)) {
                                                        Log.i("OmniAPI", "isInvokeAvailable isVisAvailable : Google is not default assistant");
                                                        unexecutableType = OmniAPI.UnexecutableType.GOOGLE_IS_NOT_DIGITAL_ASSISTANT;
                                                    }
                                                }
                                                unexecutableType = OmniAPI.UnexecutableType.AVAILABLE;
                                            } else if (!OmniAPI.mAssistStateManager.isInitSession() || OmniAPI.mAssistStateManager.isOmniAvailable()) {
                                                if (OmniAPI.mAssistStateManager.isOmniAvailable() && !OmniAPI.isGoogleDefaultAssistant(context)) {
                                                    Log.i("OmniAPI", "isInvokeAvailable : Google is not default assistant");
                                                    unexecutableType = OmniAPI.UnexecutableType.GOOGLE_IS_NOT_DIGITAL_ASSISTANT;
                                                }
                                                unexecutableType = OmniAPI.UnexecutableType.AVAILABLE;
                                            } else {
                                                Log.i("OmniAPI", "isInvokeAvailable : Omni not available");
                                                unexecutableType = OmniAPI.UnexecutableType.SERVICE_UNAVAILABLE;
                                            }
                                        } else {
                                            Log.i("OmniAPI", "isInvokeAvailable : Google app is disabled");
                                            unexecutableType = OmniAPI.UnexecutableType.GOOGLE_APP_DISABLED;
                                        }
                                        int i = unexecutableType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[unexecutableType.ordinal()];
                                        if (i == 1) {
                                            this.toastMsg = context.getString(R.string.searcle_can_not_open_searcle_service_not_available);
                                        } else if (i == 2) {
                                            this.toastMsg = context.getString(R.string.searcle_can_not_open_google_app_is_disabled);
                                        } else if (i != 3) {
                                            z4 = true;
                                            if (!z4) {
                                                return true;
                                            }
                                        } else {
                                            this.toastMsg = context.getString(R.string.searcle_can_not_open_google_is_not_digital_assistant);
                                        }
                                        z4 = false;
                                        if (!z4) {
                                        }
                                    }
                                    return false;
                                }
                                z3 = true;
                                if (z3) {
                                }
                                return false;
                            }
                            Log.i("SearcleManager", "isUnAvailableSearcle = isLaunchingCircle2Search");
                        }
                    }
                } else {
                    Log.i("SearcleManager", "isUnAvailableSearcle = kiosk mode");
                }
            }
            z = true;
            if (z) {
            }
        }
        z2 = false;
        if (z2) {
        }
    }

    public final void showToast() {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.searcle.SearcleManager$showToast$1
            @Override // java.lang.Runnable
            public final void run() {
                SearcleManager searcleManager = SearcleManager.this;
                CharSequence charSequence = searcleManager.toastMsg;
                if (charSequence != null) {
                    Toast toast = searcleManager.toast;
                    if (toast != null) {
                        toast.cancel();
                        toast.setText(charSequence);
                    }
                    Toast makeText = Toast.makeText(searcleManager.context, charSequence, 0);
                    searcleManager.toast = makeText;
                    if (makeText != null) {
                        makeText.show();
                    }
                }
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0055, code lost:
    
        if ((r13 && r0.mItemLists.get("cn_support_circe_to_search").getIntValue() == 1) != false) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startSearcleByHomeKey(boolean z, boolean z2) {
        SettingsHelper settingsHelper = this.settingsHelper;
        settingsHelper.getClass();
        boolean z3 = true;
        if (BasicRune.SEARCLE && settingsHelper.mItemLists.get("touch_and_hold_to_search").getIntValue() == 1) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m59m("startSearcleByHomeKey down = ", z, ", longPress = ", z2, "SearcleManager");
            Context context = this.context;
            if (z2) {
                this.isUnavailableSearchApp = isUnavailableSearchAppCheck();
                if (!BasicRune.SUPPORT_SEARCLE) {
                    boolean z4 = BasicRune.BIXBY_TOUCH_SUPPORT_CIRCLE2SEARCH;
                    if (z4) {
                        settingsHelper.getClass();
                    }
                    z3 = false;
                }
                if (z3 && !OmniAPI.mAssistStateManager.isInitSession()) {
                    Log.d("SearcleManager", "startSearcleByHomeKey GlobalSearchSession is not initialized");
                    OmniAPI.mAssistStateManager.initSearchSession(context, new Consumer() { // from class: com.android.systemui.searcle.SearcleManager$startSearcleByHomeKey$1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            if (((Boolean) obj).booleanValue()) {
                                final SearcleManager searcleManager = SearcleManager.this;
                                OmniAPI.mAssistStateManager.updateIsOmniAvailableFromAppSearch(new Consumer() { // from class: com.android.systemui.searcle.SearcleManager$startSearcleByHomeKey$1.1
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj2) {
                                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                                        SearcleManager searcleManager2 = SearcleManager.this;
                                        if (!booleanValue) {
                                            searcleManager2.toastMsg = searcleManager2.context.getString(R.string.searcle_can_not_open_searcle_service_not_available);
                                            searcleManager2.showToast();
                                            return;
                                        }
                                        int i = SearcleManager.$r8$clinit;
                                        boolean isUnavailableSearchAppCheck = searcleManager2.isUnavailableSearchAppCheck();
                                        searcleManager2.isUnavailableSearchApp = isUnavailableSearchAppCheck;
                                        if (!isUnavailableSearchAppCheck) {
                                            searcleManager2.invokeSearcle();
                                            return;
                                        }
                                        Log.d("SearcleManager", "startSearcleByHomeKey retry isUnavailableSearchApp = " + isUnavailableSearchAppCheck);
                                        searcleManager2.showToast();
                                    }
                                });
                            } else {
                                Log.d("SearcleManager", "startSearcleByHomeKey GlobalSearchSession is not initialized : Retry failed.");
                                SearcleManager searcleManager2 = SearcleManager.this;
                                searcleManager2.toastMsg = searcleManager2.context.getString(R.string.searcle_can_not_open_searcle_service_not_available);
                                searcleManager2.showToast();
                            }
                        }
                    });
                    return;
                }
                boolean z5 = this.isUnavailableSearchApp;
                if (!z5) {
                    invokeSearcle();
                    return;
                }
                Log.d("SearcleManager", "startSearcleByHomeKey isUnavailableSearchApp = " + z5);
                showToast();
                return;
            }
            if (z || z2) {
                return;
            }
            boolean z6 = this.isUnavailableSearchApp;
            if (z6) {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("startSearcleByHomeKey isUnavailableSearchApp = ", z6, "SearcleManager");
                this.isUnavailableSearchApp = false;
                this.toastMsg = null;
                this.toast = null;
            }
            int i = this.currentDownCount;
            SearcleTipPopupUtil.INSTANCE.getClass();
            Log.d("SearcleManager", SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("startSearcleByHomeKey currentDownCount = ", i, " tipShownCount = ", Prefs.getInt(context, "SearcleTipCount", 0), " "));
            SearcleTipPopup searcleTipPopup = this.tipPopup;
            if (searcleTipPopup.isTipPopupShowing) {
                return;
            }
            if (Prefs.getInt(context, "SearcleTipCount", 0) < 2) {
                int i2 = this.currentDownCount + 1;
                int i3 = Prefs.getInt(context, "SearcleTipCount", 0);
                String timeFormatString = SearcleTipPopupUtil.getTimeFormatString(context.getSharedPreferences(context.getPackageName(), 0).getLong("SearcleTipFirstSeenTime", 0L));
                String timeFormatString2 = SearcleTipPopupUtil.getTimeFormatString(System.currentTimeMillis());
                StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("startSearcleByHomeKey currentDownCount = ", i2, " tipShownCount = ", i3, " firstShownTime = ");
                m45m.append(timeFormatString);
                m45m.append(" currentTime = ");
                m45m.append(timeFormatString2);
                Log.d("SearcleManager", m45m.toString());
                if (Prefs.getInt(context, "SearcleTipCount", 0) == 0) {
                    int i4 = this.currentDownCount + 1;
                    this.currentDownCount = i4;
                    if (i4 >= 10) {
                        searcleTipPopup.showSearcleTip(false);
                        context.getSharedPreferences(context.getPackageName(), 0).edit().putLong("SearcleTipFirstSeenTime", System.currentTimeMillis()).apply();
                        return;
                    }
                    return;
                }
                int i5 = Prefs.getInt(context, "SearcleTipCount", 0);
                if (i5 != 0 && (i5 != 1 || System.currentTimeMillis() - context.getSharedPreferences(context.getPackageName(), 0).getLong("SearcleTipFirstSeenTime", 0L) < 259200000)) {
                    z3 = false;
                }
                if (z3) {
                    Log.d("SearcleManager", "startSearcleByHomeKey remind popup!");
                    searcleTipPopup.showSearcleTip(false);
                }
            }
        }
    }
}
