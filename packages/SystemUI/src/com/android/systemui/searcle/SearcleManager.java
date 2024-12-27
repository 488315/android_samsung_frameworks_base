package com.android.systemui.searcle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.searcle.SearcleManager;
import com.android.systemui.searcle.omni.SimpleBroadcastReceiver;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopmode.SemDesktopModeManager;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public NavigationBarView navigationBarView;
    public final SearcleTipPopup tipPopup;
    public Toast toast;
    public CharSequence toastMsg;
    public final Vibrator vibrator;
    public String invokedPackageName = "";
    public final SysUiState sysUiState = (SysUiState) Dependency.sDependency.getDependencyInner(SysUiState.class);
    private final SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
    public final KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            String[] strArr = {"android.intent.action.PACKAGE_ADDED", "android.intent.action.PACKAGE_CHANGED", "android.intent.action.PACKAGE_REMOVED"};
            SimpleBroadcastReceiver simpleBroadcastReceiver = OmniAPI.mOmniPackageReceiver;
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
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
                String str;
                if (intent.getData() != null) {
                    Uri data = intent.getData();
                    Intrinsics.checkNotNull(data);
                    str = data.getSchemeSpecificPart();
                } else {
                    str = null;
                }
                if (Intrinsics.areEqual(str, "android.permissionui.cts") || Intrinsics.areEqual(str, "android.voicerecognition.cts")) {
                    Log.d("SearcleManager", "onReceive action = " + intent.getAction());
                    String action = intent.getAction();
                    if (action != null) {
                        int hashCode = action.hashCode();
                        if (hashCode == 525384130) {
                            if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                                SearcleManager searcleManager = SearcleManager.this;
                                searcleManager.isInstalledCTSApp = SearcleManager.CTSPackageState.UNINSTALLED;
                                onReceive$restSearcleTip(searcleManager, context2);
                                return;
                            }
                            return;
                        }
                        if (hashCode == 1544582882 && action.equals("android.intent.action.PACKAGE_ADDED")) {
                            SearcleManager searcleManager2 = SearcleManager.this;
                            searcleManager2.isInstalledCTSApp = SearcleManager.CTSPackageState.INSTALLED;
                            onReceive$restSearcleTip(searcleManager2, context2);
                        }
                    }
                }
            }
        };
        BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.IO), null, null, new AnonymousClass1(null), 3);
        if (vibrator != null) {
            this.isSupportDCMotor = vibrator.semGetSupportedVibrationType() == 1;
        }
    }

    public static final int access$getOmniEntryPoint(SearcleManager searcleManager) {
        return searcleManager.settingsHelper.isNavigationBarGestureWhileHidden() ? 1 : 2;
    }

    public final boolean isSupportBixbyTouch() {
        return BasicRune.SUPPORT_BIXBY_TOUCH && !this.settingsHelper.isCNSupportCTS();
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0216, code lost:
    
        if (com.android.systemui.searcle.OmniAPI.mIsOmniPackageEnabled == false) goto L109;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0220  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0242  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startSearcleByHomeKey(boolean r22, boolean r23) {
        /*
            Method dump skipped, instructions count: 1157
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.searcle.SearcleManager.startSearcleByHomeKey(boolean, boolean):void");
    }
}
