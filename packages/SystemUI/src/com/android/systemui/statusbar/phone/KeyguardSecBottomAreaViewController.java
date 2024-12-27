package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.devicepolicy.DevicePolicyManagerExtKt;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import com.android.systemui.keyguard.shared.model.CameraLaunchSourceModel;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$1;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.CameraLauncher;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlowImpl;

public final class KeyguardSecBottomAreaViewController extends KeyguardBottomAreaViewController {
    public static final long APPEAR_ANIM_DURATION;
    public static final String KEY_HELP_TEXT_BOTTOM;
    public static final String KEY_HELP_TEXT_HEIGHT;
    public static final String KEY_HELP_TEXT_VISIBILITY;
    public final ActivityStarter activityStarter;
    public final Lazy binding$delegate;
    public final Lazy bottomDozeArea$delegate;
    public final BroadcastDispatcher broadcastDispatcher;
    public final dagger.Lazy centralSurfacesLazy;
    public final DevicePolicyManager devicePolicyManager;
    public final DumpManager dumpManager;
    public AnimatorSet helpTextAnimSet;
    public final Lazy indicationArea$delegate;
    public final Lazy indicationText$delegate;
    public boolean isAllShortcutDisabled;
    public boolean isDozing;
    public boolean isIndicationUpdatable;
    public boolean isKeyguardVisible;
    public boolean isSecure;
    public boolean isShortcutAnimRunning;
    public boolean isUserSetupComplete;
    public boolean isUserUnlocked;
    public final boolean isUsimTextAreaShowing;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final KeyguardSecBottomAreaViewController$keyguardUpdateMonitorCallbackForShortcuts$1 keyguardUpdateMonitorCallbackForShortcuts;
    public final Lazy leftShortcutArea$delegate;
    public final Lazy leftShortcutEffectview$delegate;
    public final Lazy leftView$delegate;
    public final KeyguardSecBottomAreaViewController$mDevicePolicyReceiver$1 mDevicePolicyReceiver;
    public boolean mEasyMode;
    public final PathInterpolator mInterpolator;
    public boolean mPermDisableState;
    public boolean mSavingMode;
    private final SettingsHelper.OnChangedCallback mShortcutCallback;
    public final KeyguardSecBottomAreaViewController$mWakefulnessObserver$1 mWakefulnessObserver;
    public boolean nowBarVisible;
    public final KeyguardQuickAffordanceInteractor quickAffordanceInteractor;
    public final dagger.Lazy quickSettingsControllerLazy;
    public final KeyguardSecBottomAreaViewController$restoreRunnable$1 restoreRunnable;
    public final Lazy rightShortcutArea$delegate;
    public final Lazy rightShortcutEffectview$delegate;
    public final Lazy rightView$delegate;
    public final SelectedUserInteractor selectedUserInteractor;
    private final SettingsHelper settingsHelper;
    public final KeyguardSecBottomAreaViewController$shortcutAnimRunnable$1 shortcutAnimRunnable;
    public final KeyguardShortcutManager shortcutManager;
    public final KeyguardSecBottomAreaViewController$shortcutManagerCallback$1 shortcutManagerCallback;
    public boolean showShortcutAnim;
    public final KeyguardSecBottomAreaViewController$startDelayRunnable$1 startDelayRunnable;
    public final WakefulnessLifecycle wakefulnessLifecycle;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        APPEAR_ANIM_DURATION = 1000L;
        KEY_HELP_TEXT_VISIBILITY = "help_text_visibility";
        KEY_HELP_TEXT_HEIGHT = "help_text_height";
        KEY_HELP_TEXT_BOTTOM = "help_text_margin";
    }

    /* JADX WARN: Type inference failed for: r1v17, types: [com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$keyguardUpdateMonitorCallbackForShortcuts$1] */
    /* JADX WARN: Type inference failed for: r1v20, types: [com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$mWakefulnessObserver$1] */
    /* JADX WARN: Type inference failed for: r1v22, types: [com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$shortcutAnimRunnable$1] */
    /* JADX WARN: Type inference failed for: r1v23, types: [com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$startDelayRunnable$1] */
    /* JADX WARN: Type inference failed for: r1v24, types: [com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$restoreRunnable$1] */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$mDevicePolicyReceiver$1] */
    public KeyguardSecBottomAreaViewController(ActivityStarter activityStarter, DevicePolicyManager devicePolicyManager, BroadcastDispatcher broadcastDispatcher, dagger.Lazy lazy, KeyguardInteractor keyguardInteractor, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, dagger.Lazy lazy2, SettingsHelper settingsHelper, dagger.Lazy lazy3, WakefulnessLifecycle wakefulnessLifecycle, SelectedUserInteractor selectedUserInteractor, KeyguardBottomAreaView keyguardBottomAreaView, LockscreenSmartspaceController lockscreenSmartspaceController, FeatureFlagsClassic featureFlagsClassic, DumpManager dumpManager) {
        super((KeyguardSecBottomAreaView) keyguardBottomAreaView, lockscreenSmartspaceController, featureFlagsClassic);
        this.activityStarter = activityStarter;
        this.devicePolicyManager = devicePolicyManager;
        this.broadcastDispatcher = broadcastDispatcher;
        this.centralSurfacesLazy = lazy;
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardStateController = keyguardStateController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.quickAffordanceInteractor = keyguardQuickAffordanceInteractor;
        this.quickSettingsControllerLazy = lazy2;
        this.settingsHelper = settingsHelper;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.selectedUserInteractor = selectedUserInteractor;
        this.dumpManager = dumpManager;
        KeyguardShortcutManager keyguardShortcutManager = (KeyguardShortcutManager) lazy3.get();
        this.shortcutManager = keyguardShortcutManager;
        this.leftView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$leftView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                View view;
                view = ((ViewController) KeyguardSecBottomAreaViewController.this).mView;
                return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) view).leftView$delegate.getValue();
            }
        });
        this.rightView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$rightView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                View view;
                view = ((ViewController) KeyguardSecBottomAreaViewController.this).mView;
                return ((KeyguardSecBottomAreaView) view).getRightView();
            }
        });
        this.leftShortcutArea$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$leftShortcutArea$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                View view;
                view = ((ViewController) KeyguardSecBottomAreaViewController.this).mView;
                return (View) ((KeyguardSecBottomAreaView) view).leftShortcutArea$delegate.getValue();
            }
        });
        this.rightShortcutArea$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$rightShortcutArea$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                View view;
                view = ((ViewController) KeyguardSecBottomAreaViewController.this).mView;
                return (View) ((KeyguardSecBottomAreaView) view).rightShortcutArea$delegate.getValue();
            }
        });
        this.leftShortcutEffectview$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$leftShortcutEffectview$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                View view;
                view = ((ViewController) KeyguardSecBottomAreaViewController.this).mView;
                return (View) ((KeyguardSecBottomAreaView) view).leftShortcutEffectview$delegate.getValue();
            }
        });
        this.rightShortcutEffectview$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$rightShortcutEffectview$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                View view;
                view = ((ViewController) KeyguardSecBottomAreaViewController.this).mView;
                return (View) ((KeyguardSecBottomAreaView) view).rightShortcutEffectview$delegate.getValue();
            }
        });
        this.indicationArea$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$indicationArea$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                View view;
                view = ((ViewController) KeyguardSecBottomAreaViewController.this).mView;
                return (ViewGroup) ((KeyguardSecBottomAreaView) view).indicationArea$delegate.getValue();
            }
        });
        this.indicationText$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$indicationText$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                View view;
                view = ((ViewController) KeyguardSecBottomAreaViewController.this).mView;
                return (TextView) ((KeyguardSecBottomAreaView) view).indicationText$delegate.getValue();
            }
        });
        this.binding$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$binding$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                View view;
                view = ((ViewController) KeyguardSecBottomAreaViewController.this).mView;
                KeyguardBottomAreaViewBinder.Binding binding = ((KeyguardSecBottomAreaView) view).binding;
                if (binding == null) {
                    binding = null;
                }
                return (KeyguardSecBottomAreaViewBinder$bind$1) binding;
            }
        });
        this.bottomDozeArea$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$bottomDozeArea$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                View view;
                view = ((ViewController) KeyguardSecBottomAreaViewController.this).mView;
                return (FrameLayout) ((KeyguardSecBottomAreaView) view).bottomDozeArea$delegate.getValue();
            }
        });
        this.isUsimTextAreaShowing = true;
        this.mDevicePolicyReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$mDevicePolicyReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                View view;
                view = ((ViewController) KeyguardSecBottomAreaViewController.this).mView;
                final KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                ((KeyguardSecBottomAreaView) view).post(new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$mDevicePolicyReceiver$1$onReceive$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController2 = KeyguardSecBottomAreaViewController.this;
                        String str = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                        List<KeyguardSecAffordanceView> listOf = CollectionsKt__CollectionsKt.listOf(keyguardSecBottomAreaViewController2.getLeftView(), KeyguardSecBottomAreaViewController.this.getRightView());
                        KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController3 = KeyguardSecBottomAreaViewController.this;
                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listOf, 10));
                        for (KeyguardSecAffordanceView keyguardSecAffordanceView : listOf) {
                            if (keyguardSecAffordanceView.mShortcutForCamera) {
                                keyguardSecBottomAreaViewController3.updateAffordanceIcon(keyguardSecAffordanceView);
                            }
                            arrayList.add(Unit.INSTANCE);
                        }
                    }
                });
            }
        };
        this.isUserUnlocked = LsRune.KEYGUARD_FBE ? keyguardUpdateMonitor.isUserUnlocked() : true;
        this.mSavingMode = settingsHelper.isUltraPowerSavingMode() || settingsHelper.isEmergencyMode();
        this.mEasyMode = settingsHelper.isEasyModeOn();
        this.mPermDisableState = CscRune.SECURITY_SIM_PERM_DISABLED && keyguardUpdateMonitor.isIccBlockedPermanently();
        this.isUserSetupComplete = Settings.Secure.getInt(getContext().getContentResolver(), "user_setup_complete", 0) == 1;
        this.mShortcutCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$mShortcutCallback$1
            /* JADX WARN: Code restructure failed: missing block: B:34:0x0090, code lost:
            
                if (r0.isEmergencyMode() != false) goto L31;
             */
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onChanged(android.net.Uri r4) {
                /*
                    r3 = this;
                    if (r4 != 0) goto L3
                    return
                L3:
                    java.lang.String r0 = "ultra_powersaving_mode"
                    android.net.Uri r0 = android.provider.Settings.System.getUriFor(r0)
                    boolean r0 = r4.equals(r0)
                    r1 = 0
                    r2 = 1
                    com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController r3 = com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController.this
                    if (r0 != 0) goto L7c
                    java.lang.String r0 = "minimal_battery_use"
                    android.net.Uri r0 = android.provider.Settings.System.getUriFor(r0)
                    boolean r0 = r4.equals(r0)
                    if (r0 != 0) goto L7c
                    java.lang.String r0 = "emergency_mode"
                    android.net.Uri r0 = android.provider.Settings.System.getUriFor(r0)
                    boolean r0 = r4.equals(r0)
                    if (r0 == 0) goto L2d
                    goto L7c
                L2d:
                    java.lang.String r0 = "easy_mode_switch"
                    android.net.Uri r0 = android.provider.Settings.System.getUriFor(r0)
                    boolean r0 = r4.equals(r0)
                    if (r0 == 0) goto L4d
                    boolean r4 = r3.mEasyMode
                    com.android.systemui.util.SettingsHelper r0 = com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController.access$getSettingsHelper$p(r3)
                    boolean r0 = r0.isEasyModeOn()
                    r3.mEasyMode = r0
                    boolean r0 = r3.mEasyMode
                    if (r4 == r0) goto L9c
                    r3.onDensityOrFontScaleChanged(r2)
                    goto L9c
                L4d:
                    java.lang.String r0 = "lock_shortcut_type"
                    android.net.Uri r0 = android.provider.Settings.System.getUriFor(r0)
                    boolean r0 = r4.equals(r0)
                    if (r0 == 0) goto L66
                    boolean r4 = r3.mSavingMode
                    if (r4 != 0) goto L61
                    boolean r4 = r3.mEasyMode
                    if (r4 == 0) goto L62
                L61:
                    r1 = r2
                L62:
                    r3.onDensityOrFontScaleChanged(r1)
                    goto L9c
                L66:
                    java.lang.String r0 = "display_cutout_hide_notch"
                    android.net.Uri r0 = android.provider.Settings.Secure.getUriFor(r0)
                    boolean r4 = r4.equals(r0)
                    if (r4 == 0) goto L9c
                    android.view.View r3 = com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController.access$getMView$p$s2038760804(r3)
                    com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView r3 = (com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView) r3
                    r3.updateLayout()
                    goto L9c
                L7c:
                    boolean r4 = r3.mSavingMode
                    com.android.systemui.util.SettingsHelper r0 = com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController.access$getSettingsHelper$p(r3)
                    boolean r0 = r0.isUltraPowerSavingMode()
                    if (r0 != 0) goto L92
                    com.android.systemui.util.SettingsHelper r0 = com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController.access$getSettingsHelper$p(r3)
                    boolean r0 = r0.isEmergencyMode()
                    if (r0 == 0) goto L93
                L92:
                    r1 = r2
                L93:
                    r3.mSavingMode = r1
                    boolean r0 = r3.mSavingMode
                    if (r4 == r0) goto L9c
                    r3.onDensityOrFontScaleChanged(r2)
                L9c:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$mShortcutCallback$1.onChanged(android.net.Uri):void");
            }
        };
        this.keyguardUpdateMonitorCallbackForShortcuts = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$keyguardUpdateMonitorCallbackForShortcuts$1
            public boolean mOutOfService;

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) {
                View view;
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                keyguardSecBottomAreaViewController.isKeyguardVisible = z;
                view = ((ViewController) keyguardSecBottomAreaViewController).mView;
                ((KeyguardSecBottomAreaView) view).isKeyguardVisible = keyguardSecBottomAreaViewController.isKeyguardVisible;
                KeyguardSecBottomAreaViewController.access$setIndicationUpdatable(keyguardSecBottomAreaViewController, z);
                keyguardSecBottomAreaViewController.getBinding().updateIndicationPosition();
                super.onKeyguardVisibilityChanged(z);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onLockModeChanged() {
                View view;
                Log.d("KeyguardSecBottomAreaViewController", "onLockModeChanged");
                view = ((ViewController) KeyguardSecBottomAreaViewController.this).mView;
                ((KeyguardSecBottomAreaView) view).updateLayout();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onRefreshCarrierInfo(Intent intent) {
                boolean isOutOfService;
                boolean z = CscRune.SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE;
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                if (z && this.mOutOfService != (isOutOfService = keyguardSecBottomAreaViewController.keyguardUpdateMonitor.isOutOfService())) {
                    this.mOutOfService = isOutOfService;
                    List<KeyguardSecAffordanceView> listOf = CollectionsKt__CollectionsKt.listOf(keyguardSecBottomAreaViewController.getLeftView(), keyguardSecBottomAreaViewController.getRightView());
                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listOf, 10));
                    for (KeyguardSecAffordanceView keyguardSecAffordanceView : listOf) {
                        if (keyguardSecAffordanceView.mIsShortcutForPhone) {
                            keyguardSecBottomAreaViewController.updateAffordanceIcon(keyguardSecAffordanceView);
                        }
                        arrayList.add(Unit.INSTANCE);
                    }
                }
                String str = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                keyguardSecBottomAreaViewController.getBinding().updateIndicationPosition();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimStateChanged(int i, int i2, int i3) {
                View view;
                boolean z = CscRune.LOCKUI_BOTTOM_USIM_TEXT;
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                if (z) {
                    view = ((ViewController) keyguardSecBottomAreaViewController).mView;
                    ((KeyguardSecBottomAreaView) view).currentSimState = i3;
                }
                if (CscRune.SECURITY_SIM_PERM_DISABLED) {
                    keyguardSecBottomAreaViewController.mPermDisableState = keyguardSecBottomAreaViewController.keyguardUpdateMonitor.isIccBlockedPermanently();
                }
                if (((TextView) keyguardSecBottomAreaViewController.indicationText$delegate.getValue()) != null) {
                    boolean z2 = keyguardSecBottomAreaViewController.mPermDisableState;
                    Lazy lazy4 = keyguardSecBottomAreaViewController.indicationText$delegate;
                    if (z2) {
                        ((TextView) lazy4.getValue()).setVisibility(4);
                    } else if (keyguardSecBottomAreaViewController.isSecure) {
                        if (!keyguardSecBottomAreaViewController.keyguardUpdateMonitor.getUserCanSkipBouncer(keyguardSecBottomAreaViewController.selectedUserInteractor.getSelectedUserId(false))) {
                            ((TextView) lazy4.getValue()).setVisibility(0);
                        }
                    }
                }
                keyguardSecBottomAreaViewController.getBinding().updateIndicationPosition();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitchComplete(int i) {
                View view;
                view = ((ViewController) KeyguardSecBottomAreaViewController.this).mView;
                ((KeyguardSecBottomAreaView) view).updateLayout();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserUnlocked() {
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                boolean isUserUnlocked = keyguardSecBottomAreaViewController.keyguardUpdateMonitor.isUserUnlocked();
                if (keyguardSecBottomAreaViewController.isUserUnlocked != isUserUnlocked) {
                    keyguardSecBottomAreaViewController.isUserUnlocked = isUserUnlocked;
                }
                boolean z = keyguardSecBottomAreaViewController.isUserUnlocked;
                KeyguardSecBottomAreaViewController.access$showShortcutsIfPossible(keyguardSecBottomAreaViewController);
            }
        };
        this.isIndicationUpdatable = true;
        this.isAllShortcutDisabled = (keyguardShortcutManager.hasShortcut(0) || keyguardShortcutManager.hasShortcut(1)) ? false : true;
        this.mInterpolator = new PathInterpolator(0.25f, 0.25f, 0.0f, 1.0f);
        this.mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$mWakefulnessObserver$1
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                View view;
                View view2;
                String str = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                keyguardSecBottomAreaViewController.getIndicationArea().setAlpha(0.0f);
                KeyguardSecBottomAreaViewController.access$setIndicationUpdatable(keyguardSecBottomAreaViewController, !keyguardSecBottomAreaViewController.isKeyguardVisible);
                view = ((ViewController) keyguardSecBottomAreaViewController).mView;
                Handler handler = ((KeyguardSecBottomAreaView) view).getHandler();
                KeyguardSecBottomAreaViewController$shortcutAnimRunnable$1 keyguardSecBottomAreaViewController$shortcutAnimRunnable$1 = keyguardSecBottomAreaViewController.shortcutAnimRunnable;
                if (handler.hasCallbacks(keyguardSecBottomAreaViewController$shortcutAnimRunnable$1)) {
                    view2 = ((ViewController) keyguardSecBottomAreaViewController).mView;
                    ((KeyguardSecBottomAreaView) view2).getHandler().removeCallbacks(keyguardSecBottomAreaViewController$shortcutAnimRunnable$1);
                }
                keyguardSecBottomAreaViewController.showShortcutAnim = false;
                keyguardSecBottomAreaViewController.isShortcutAnimRunning = false;
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onPostFinishedWakingUp() {
                View view;
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                view = ((ViewController) keyguardSecBottomAreaViewController).mView;
                ((KeyguardSecBottomAreaView) view).getHandler().postDelayed(keyguardSecBottomAreaViewController.shortcutAnimRunnable, 100L);
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                final KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                if (!keyguardSecBottomAreaViewController.isKeyguardVisible || keyguardSecBottomAreaViewController.keyguardUpdateMonitor.isFullscreenBouncer() || ((KeyguardFastBioUnlockController) Dependency.sDependency.getDependencyInner(KeyguardFastBioUnlockController.class)).isFastWakeAndUnlockMode()) {
                    return;
                }
                if (LsRune.LOCKUI_SUB_DISPLAY_LOCK && !keyguardSecBottomAreaViewController.isSecure && keyguardSecBottomAreaViewController.wakefulnessLifecycle.mLastWakeReason == 9) {
                    return;
                }
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(keyguardSecBottomAreaViewController.getIndicationArea(), (Property<ViewGroup, Float>) View.SCALE_X, 0.8f, 1.0f);
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(keyguardSecBottomAreaViewController.getIndicationArea(), (Property<ViewGroup, Float>) View.SCALE_Y, 0.8f, 1.0f);
                ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(keyguardSecBottomAreaViewController.getIndicationArea(), (Property<ViewGroup, Float>) View.ALPHA, 0.0f, 1.0f);
                ofFloat3.setInterpolator(new LinearInterpolator());
                AnimatorSet animatorSet = new AnimatorSet();
                keyguardSecBottomAreaViewController.helpTextAnimSet = animatorSet;
                animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
                AnimatorSet animatorSet2 = keyguardSecBottomAreaViewController.helpTextAnimSet;
                Intrinsics.checkNotNull(animatorSet2);
                animatorSet2.setInterpolator(keyguardSecBottomAreaViewController.mInterpolator);
                AnimatorSet animatorSet3 = keyguardSecBottomAreaViewController.helpTextAnimSet;
                Intrinsics.checkNotNull(animatorSet3);
                animatorSet3.setDuration(KeyguardSecBottomAreaViewController.APPEAR_ANIM_DURATION);
                AnimatorSet animatorSet4 = keyguardSecBottomAreaViewController.helpTextAnimSet;
                Intrinsics.checkNotNull(animatorSet4);
                animatorSet4.setStartDelay(0L);
                AnimatorSet animatorSet5 = keyguardSecBottomAreaViewController.helpTextAnimSet;
                Intrinsics.checkNotNull(animatorSet5);
                animatorSet5.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$startIndicationAppearAnimation$1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        KeyguardSecBottomAreaViewController.access$setIndicationUpdatable(KeyguardSecBottomAreaViewController.this, true);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        KeyguardSecBottomAreaViewController.access$setIndicationUpdatable(KeyguardSecBottomAreaViewController.this, true);
                        KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController2 = KeyguardSecBottomAreaViewController.this;
                        keyguardSecBottomAreaViewController2.helpTextAnimSet = null;
                        keyguardSecBottomAreaViewController2.getIndicationArea().setScaleX(1.0f);
                        KeyguardSecBottomAreaViewController.this.getIndicationArea().setScaleY(1.0f);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        KeyguardSecBottomAreaViewController.access$setIndicationUpdatable(KeyguardSecBottomAreaViewController.this, false);
                    }
                });
                AnimatorSet animatorSet6 = keyguardSecBottomAreaViewController.helpTextAnimSet;
                Intrinsics.checkNotNull(animatorSet6);
                animatorSet6.start();
            }
        };
        this.shortcutManagerCallback = new KeyguardSecBottomAreaViewController$shortcutManagerCallback$1(this);
        this.shortcutAnimRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$shortcutAnimRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSecBottomAreaViewController.this.showShortcutAnim = true;
            }
        };
        this.startDelayRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$startDelayRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                View view;
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                String str = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                KeyguardSecAffordanceView leftView = keyguardSecBottomAreaViewController.getLeftView();
                final KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController2 = KeyguardSecBottomAreaViewController.this;
                leftView.setNowBarVisibility(new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$startDelayRunnable$1.1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((Boolean) obj).getClass();
                        KeyguardSecBottomAreaViewController.access$updateFinalShortcut(KeyguardSecBottomAreaViewController.this);
                    }
                }, keyguardSecBottomAreaViewController2.nowBarVisible, true);
                KeyguardSecAffordanceView rightView = KeyguardSecBottomAreaViewController.this.getRightView();
                final KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController3 = KeyguardSecBottomAreaViewController.this;
                rightView.setNowBarVisibility(new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$startDelayRunnable$1.2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((Boolean) obj).getClass();
                        KeyguardSecBottomAreaViewController.access$updateFinalShortcut(KeyguardSecBottomAreaViewController.this);
                    }
                }, keyguardSecBottomAreaViewController3.nowBarVisible, true);
                view = ((ViewController) KeyguardSecBottomAreaViewController.this).mView;
                ((KeyguardSecBottomAreaView) view).getHandler().postDelayed(KeyguardSecBottomAreaViewController.this.restoreRunnable, 600L);
            }
        };
        this.restoreRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$restoreRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                boolean z = keyguardSecBottomAreaViewController.nowBarVisible;
                KeyguardSecBottomAreaViewController.access$updateFinalShortcut(keyguardSecBottomAreaViewController);
            }
        };
    }

    public static final void access$setIndicationUpdatable(KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController, boolean z) {
        if (keyguardSecBottomAreaViewController.isIndicationUpdatable == z) {
            return;
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("mCanIndicationAreaUpdate set to ", "KeyguardSecBottomAreaViewController", z);
        keyguardSecBottomAreaViewController.isIndicationUpdatable = z;
    }

    public static final void access$showShortcutsIfPossible(KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController) {
        if (keyguardSecBottomAreaViewController.shouldDisableShortcut()) {
            return;
        }
        Function0 function0 = ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).updateRightAffordanceIcon;
        if (function0 == null) {
            function0 = null;
        }
        function0.invoke();
        Function0 function02 = ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).updateLeftAffordanceIcon;
        (function02 != null ? function02 : null).invoke();
    }

    public static final void access$updateFinalShortcut(KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController) {
        Handler handler = ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).getHandler();
        KeyguardSecBottomAreaViewController$restoreRunnable$1 keyguardSecBottomAreaViewController$restoreRunnable$1 = keyguardSecBottomAreaViewController.restoreRunnable;
        if (handler.hasCallbacks(keyguardSecBottomAreaViewController$restoreRunnable$1)) {
            ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).getHandler().removeCallbacks(keyguardSecBottomAreaViewController$restoreRunnable$1);
        }
        Handler handler2 = ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).getHandler();
        KeyguardSecBottomAreaViewController$startDelayRunnable$1 keyguardSecBottomAreaViewController$startDelayRunnable$1 = keyguardSecBottomAreaViewController.startDelayRunnable;
        if (handler2.hasCallbacks(keyguardSecBottomAreaViewController$startDelayRunnable$1)) {
            ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).getHandler().removeCallbacks(keyguardSecBottomAreaViewController$startDelayRunnable$1);
        }
        boolean z = keyguardSecBottomAreaViewController.nowBarVisible;
        KeyguardShortcutManager keyguardShortcutManager = keyguardSecBottomAreaViewController.shortcutManager;
        keyguardShortcutManager.isNowBarVisible = z;
        keyguardShortcutManager.updateShortcutIcons();
        KeyguardSecBottomAreaView keyguardSecBottomAreaView = (KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView;
        keyguardSecBottomAreaView.isNowBarVisible = keyguardSecBottomAreaViewController.nowBarVisible;
        KeyguardBottomAreaViewBinder.Binding binding = keyguardSecBottomAreaView.binding;
        if (binding == null) {
            binding = null;
        }
        ((KeyguardSecBottomAreaViewBinder$bind$1) binding).updateShortcutPosition();
        keyguardSecBottomAreaViewController.isShortcutAnimRunning = false;
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController
    public final void cancelIndicationAreaAnim() {
        AnimatorSet animatorSet = this.helpTextAnimSet;
        if (animatorSet != null) {
            Intrinsics.checkNotNull(animatorSet);
            if (animatorSet.isRunning()) {
                AnimatorSet animatorSet2 = this.helpTextAnimSet;
                Intrinsics.checkNotNull(animatorSet2);
                animatorSet2.cancel();
            }
        }
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        SelectedUserInteractor selectedUserInteractor = this.selectedUserInteractor;
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("  CurrentUserId = ", selectedUserInteractor.getSelectedUserId(false), printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  isNowBarVisible = ", this.nowBarVisible, printWriter);
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("  bottomAreaVisibility = ", ((KeyguardSecBottomAreaView) this.mView).getVisibility(), printWriter);
        printWriter.println("  bottomAreaAlpha = " + ((KeyguardSecBottomAreaView) this.mView).getAlpha());
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  shouldDisableShortcut = ", shouldDisableShortcut(), printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  permDisableState = ", this.mPermDisableState, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  savingMode = ", this.mSavingMode, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  shouldDisableShortcutWithMdm = ", !this.shortcutManager.isShortcutVisibleForMDM, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  !isUserUnlocked = ", LsRune.KEYGUARD_FBE && !this.isUserUnlocked, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  isPluginLockOverlayView = ", ((KeyguardSecBottomAreaView) this.mView).isPluginLockOverlayView, printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  isDozing = ", this.isDozing, printWriter);
        printWriter.println("  areKeyguardShortcutsDisabled = " + DevicePolicyManagerExtKt.areKeyguardShortcutsDisabled$default(this.devicePolicyManager, selectedUserInteractor.getSelectedUserId(false)));
        if (getBinding() != null) {
            KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions configurationBasedDimensions = (KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions) ((StateFlowImpl) getBinding().$configurationBasedDimensions).getValue();
            printWriter.println("  shortcutSize = " + configurationBasedDimensions.buttonSizePx);
            UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("  shortcutBottomMargin = ", configurationBasedDimensions.shortcutBottomMargin, printWriter);
            UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("  shortcutSideMargin = ", configurationBasedDimensions.shortcutSideMargin, printWriter);
        }
        printWriter.println("  LeftShortcut:");
        if (getLeftView() != null) {
            printWriter.println("      visibility = " + getLeftView().getVisibility());
            printWriter.println("      alpha = " + getLeftView().getAlpha());
            UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("      size = ", getLeftView().getWidth(), printWriter);
            int[] iArr = new int[2];
            getLeftView().getLocationOnScreen(iArr);
            printWriter.println("      left = " + iArr[0] + ", top = " + iArr[1]);
        }
        printWriter.println("  RightShortcut:");
        if (getRightView() != null) {
            printWriter.println("      visibility = " + getRightView().getVisibility());
            printWriter.println("      alpha = " + getRightView().getAlpha());
            UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("      size = ", getRightView().getWidth(), printWriter);
            int[] iArr2 = new int[2];
            getRightView().getLocationOnScreen(iArr2);
            printWriter.println("      left = " + iArr2[0] + ", top = " + iArr2[1]);
        }
    }

    public final KeyguardSecBottomAreaViewBinder$bind$1 getBinding() {
        return (KeyguardSecBottomAreaViewBinder$bind$1) this.binding$delegate.getValue();
    }

    public final ViewGroup getIndicationArea() {
        return (ViewGroup) this.indicationArea$delegate.getValue();
    }

    public final KeyguardSecAffordanceView getLeftView() {
        return (KeyguardSecAffordanceView) this.leftView$delegate.getValue();
    }

    public final KeyguardSecAffordanceView getRightView() {
        return (KeyguardSecAffordanceView) this.rightView$delegate.getValue();
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController
    public final ArrayList getShortCutAreaViews() {
        ArrayList arrayList = new ArrayList();
        arrayList.add((View) this.leftShortcutEffectview$delegate.getValue());
        arrayList.add((View) this.rightShortcutEffectview$delegate.getValue());
        return arrayList;
    }

    @Override // com.android.systemui.pluginlock.PluginLockBottomAreaCallback
    public final boolean isNoUnlockNeed(String str) {
        LogUtil.d("KeyguardSecBottomAreaViewController", KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("isNoUnlockNeed mIsSecure: ", this.isSecure), new Object[0]);
        if (str == null || str.length() == 0 || !this.isSecure) {
            return false;
        }
        KeyguardShortcutManager keyguardShortcutManager = this.shortcutManager;
        keyguardShortcutManager.getClass();
        if (str == null || str.length() == 0) {
            return false;
        }
        return Intrinsics.areEqual(str, "com.sec.android.app.camera") || keyguardShortcutManager.isShortcutPermission(str);
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController
    public final void launchAffordance(boolean z) {
        ComponentName component;
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_LAUNCH_SHORTCUT, z ? "2" : "1");
        String currentScreenID = SystemUIAnalytics.getCurrentScreenID();
        String str = z ? SystemUIAnalytics.EID_LAUNCH_RIGHT_SHORTCUT : SystemUIAnalytics.EID_LAUNCH_LEFT_SHORTCUT;
        KeyguardShortcutManager keyguardShortcutManager = this.shortcutManager;
        Intent intent = keyguardShortcutManager.getIntent(z ? 1 : 0);
        SystemUIAnalytics.sendEventLog(currentScreenID, str, (intent == null || (component = intent.getComponent()) == null) ? null : component.getPackageName());
        if (KeyguardShortcutManager.isSamsungCameraPackage(keyguardShortcutManager.shortcutsData[z ? 1 : 0].componentName)) {
            CameraLauncher cameraLauncher = (CameraLauncher) ((CentralSurfacesImpl) ((CentralSurfaces) this.centralSurfacesLazy.get())).mCameraLauncherLazy.get();
            CameraLaunchSourceModel cameraLaunchSourceModel = CameraLaunchSourceModel.QUICK_AFFORDANCE;
            this.keyguardInteractor.getClass();
            cameraLauncher.launchCamera(KeyguardInteractor.cameraLaunchSourceModelToInt(cameraLaunchSourceModel), true);
            return;
        }
        if (!keyguardShortcutManager.isShortcutForPhone(z ? 1 : 0)) {
            this.quickAffordanceInteractor.onQuickAffordanceTriggered(((KeyguardQuickAffordanceConfig) ((ArrayList) keyguardShortcutManager.getQuickAffordanceConfigList()).get((z ? KeyguardQuickAffordancePosition.BOTTOM_END : KeyguardQuickAffordancePosition.BOTTOM_START).ordinal())).getKey(), null, z ? "bottom_end" : "bottom_start");
            return;
        }
        final TelecomManager from = TelecomManager.from(getContext());
        if (from.isInManagedCall()) {
            AsyncTask.execute(new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$launchPhone$1
                @Override // java.lang.Runnable
                public final void run() {
                    from.showInCallScreen(false);
                }
            });
        } else {
            KeyguardShortcutManager.Companion.getClass();
            this.activityStarter.startActivity(KeyguardShortcutManager.PHONE_INTENT, false);
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockBottomAreaCallback
    public final void launchApp(ComponentName componentName) {
        LogUtil.d("KeyguardSecBottomAreaViewController", AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("launchApp pkg: ", componentName.getPackageName()), new Object[0]);
        KeyguardShortcutManager keyguardShortcutManager = this.shortcutManager;
        keyguardShortcutManager.getClass();
        if (KeyguardShortcutManager.isSamsungCameraPackage(componentName)) {
            CameraLauncher cameraLauncher = (CameraLauncher) ((CentralSurfacesImpl) ((CentralSurfaces) this.centralSurfacesLazy.get())).mCameraLauncherLazy.get();
            CameraLaunchSourceModel cameraLaunchSourceModel = CameraLaunchSourceModel.QUICK_AFFORDANCE;
            this.keyguardInteractor.getClass();
            cameraLauncher.launchCamera(KeyguardInteractor.cameraLaunchSourceModelToInt(cameraLaunchSourceModel), true);
            return;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(componentName);
        intent.putExtra("isSecure", keyguardShortcutManager.isSecure());
        intent.addFlags(268500992);
        this.quickAffordanceInteractor.launchQuickAffordance(intent, true);
    }

    public final void onDensityOrFontScaleChanged(boolean z) {
        ((KeyguardSecBottomAreaView) this.mView).updateLayout();
        if (!z || (!this.mSavingMode && !this.mEasyMode)) {
            getLeftView().updateDisplayParameters();
            getRightView().updateDisplayParameters();
            ((KeyguardSecBottomAreaView) this.mView).postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$onDensityOrFontScaleChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardShortcutManager keyguardShortcutManager = KeyguardSecBottomAreaViewController.this.shortcutManager;
                    keyguardShortcutManager.getShortcutIconSizeValue();
                    keyguardShortcutManager.updateShortcuts();
                }
            }, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
        } else {
            KeyguardSecAffordanceView leftView = getLeftView();
            KeyguardShortcutManager keyguardShortcutManager = this.shortcutManager;
            updateCustomShortcutIcon(leftView, 0, keyguardShortcutManager.hasShortcut(0));
            updateCustomShortcutIcon(getRightView(), 1, keyguardShortcutManager.hasShortcut(1));
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
        this.settingsHelper.registerCallback(this.mShortcutCallback, (Uri[]) Arrays.copyOf(new Uri[]{Settings.System.getUriFor(SettingsHelper.INDEX_ULTRA_POWERSAVING_MODE), Settings.System.getUriFor(SettingsHelper.INDEX_MINIMAL_BATTERY_USE), Settings.System.getUriFor(SettingsHelper.INDEX_EMERGENCY_MODE), Settings.System.getUriFor(SettingsHelper.INDEX_EASY_MODE_SWITCH), Settings.System.getUriFor(SettingsHelper.INDEX_LOCK_SHORTCUT_TYPE), Settings.Secure.getUriFor("display_cutout_hide_notch")}, 6));
        ((KeyguardSecBottomAreaView) this.mView).showShortcutsIfPossible = new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$onInit$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KeyguardSecBottomAreaViewController.access$showShortcutsIfPossible(KeyguardSecBottomAreaViewController.this);
                return Unit.INSTANCE;
            }
        };
        ((KeyguardSecBottomAreaView) this.mView).updateLeftAffordanceIcon = new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$onInit$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                String str = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                keyguardSecBottomAreaViewController.updateCustomShortcutIcon(keyguardSecBottomAreaViewController.getLeftView(), 0, KeyguardSecBottomAreaViewController.this.shortcutManager.hasShortcut(0));
                return Unit.INSTANCE;
            }
        };
        ((KeyguardSecBottomAreaView) this.mView).updateRightAffordanceIcon = new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$onInit$3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                String str = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                keyguardSecBottomAreaViewController.updateCustomShortcutIcon(keyguardSecBottomAreaViewController.getRightView(), 1, KeyguardSecBottomAreaViewController.this.shortcutManager.hasShortcut(1));
                return Unit.INSTANCE;
            }
        };
        ((KeyguardSecBottomAreaView) this.mView).setUsimTextAreaVisibility = new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$onInit$4
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                String str = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                keyguardSecBottomAreaViewController.setUsimTextAreaVisibility();
                return Unit.INSTANCE;
            }
        };
        DumpManager.registerDumpable$default(this.dumpManager, "KeyguardSecBottomAreaViewController", this);
    }

    @Override // com.android.systemui.pluginlock.PluginLockBottomAreaCallback
    public final Bundle onUiInfoRequested(boolean z) {
        Bundle bundle = new Bundle();
        KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions configurationBasedDimensions = (KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions) ((StateFlowImpl) getBinding().$configurationBasedDimensions).getValue();
        bundle.putInt("shortcut_enable", Settings.System.getInt(getContext().getContentResolver(), SettingsHelper.INDEX_LOCK_SHORTCUT_MASTER_ENABLED, 1) == 1 ? 0 : 4);
        bundle.putString("shortcut_info", Settings.System.getString(getContext().getContentResolver(), SettingsHelper.INDEX_AWESOME_SHORTCUT_APP_LIST));
        bundle.putInt("shortcut_bottom", configurationBasedDimensions.shortcutBottomMargin);
        bundle.putInt("shortcut_side", configurationBasedDimensions.shortcutSideMargin);
        bundle.putInt("shortcut_size", configurationBasedDimensions.buttonSizePx.getWidth());
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
            DeviceState.setInDisplayFingerprintSensorPosition(getContext().getResources().getDisplayMetrics());
            bundle.putInt("finger_print_height", DeviceState.getInDisplayFingerprintHeight());
            bundle.putInt("finger_print_image_size", DeviceState.getInDisplayFingerprintImageSize());
            bundle.putInt("finger_print_margin", DeviceState.getInDisplayFingerprintMarginBottom());
            bundle.putBoolean("finger_print_enabled", this.keyguardUpdateMonitor.isFingerprintOptionEnabled());
        }
        bundle.putInt(KEY_HELP_TEXT_VISIBILITY, 0);
        bundle.putInt(KEY_HELP_TEXT_HEIGHT, getResources().getDimensionPixelSize(R.dimen.keyguard_indication_text_default_size) * 3);
        bundle.putInt(KEY_HELP_TEXT_BOTTOM, configurationBasedDimensions.indicationAreaBottomMargin);
        Log.d("KeyguardSecBottomAreaViewController", "onUiInfoRequested() : " + bundle);
        return bundle;
    }

    @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
    public final void onUnlockedChanged() {
        Function0 function0 = ((KeyguardSecBottomAreaView) this.mView).updateRightAffordanceIcon;
        if (function0 == null) {
            function0 = null;
        }
        function0.invoke();
        Function0 function02 = ((KeyguardSecBottomAreaView) this.mView).updateLeftAffordanceIcon;
        (function02 != null ? function02 : null).invoke();
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
        boolean z = keyguardStateControllerImpl.mTrusted;
        this.isSecure = keyguardStateControllerImpl.mSecure;
        setUsimTextAreaVisibility();
        if (this.keyguardUpdateMonitor.isKeyguardUnlocking() || !this.isKeyguardVisible) {
            Log.d("KeyguardSecBottomAreaViewController", "onUnlockMethodStateChanged not keyguardShowing status return!");
        }
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.keyguardUpdateMonitor.registerCallback(this.keyguardUpdateMonitorCallbackForShortcuts);
        KeyguardShortcutManager keyguardShortcutManager = this.shortcutManager;
        KeyguardSecBottomAreaViewController$shortcutManagerCallback$1 keyguardSecBottomAreaViewController$shortcutManagerCallback$1 = this.shortcutManagerCallback;
        synchronized (keyguardShortcutManager) {
            int size = keyguardShortcutManager.shortcutCallbacks.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    keyguardSecBottomAreaViewController$shortcutManagerCallback$1.getClass();
                    keyguardShortcutManager.shortcutCallbacks.add(new WeakReference(keyguardSecBottomAreaViewController$shortcutManagerCallback$1));
                    if (UserManager.get(keyguardShortcutManager.context).isUserUnlocked(keyguardShortcutManager.selectedUserInteractor.getSelectedUserId(false))) {
                        for (int i2 = 0; i2 < 2; i2++) {
                            keyguardSecBottomAreaViewController$shortcutManagerCallback$1.updateShortcutView(i2);
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                } else {
                    if (((WeakReference) keyguardShortcutManager.shortcutCallbacks.get(i)).get() == keyguardSecBottomAreaViewController$shortcutManagerCallback$1) {
                        Log.d("KeyguardShortcutManager", "registerCallback already registered: " + keyguardSecBottomAreaViewController$shortcutManagerCallback$1);
                        break;
                    }
                    i++;
                }
            }
        }
        ((KeyguardStateControllerImpl) this.keyguardStateController).addCallback(this);
        BroadcastDispatcher.registerReceiver$default(this.broadcastDispatcher, this.mDevicePolicyReceiver, new IntentFilter("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED"), null, UserHandle.ALL, 0, null, 48);
        this.wakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
        boolean z = keyguardStateControllerImpl.mTrusted;
        this.isSecure = keyguardStateControllerImpl.mSecure;
        if (CscRune.LOCKUI_BOTTOM_USIM_TEXT) {
            setUsimTextAreaVisibility();
        }
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.keyguardUpdateMonitor.removeCallback(this.keyguardUpdateMonitorCallbackForShortcuts);
        KeyguardShortcutManager keyguardShortcutManager = this.shortcutManager;
        final KeyguardSecBottomAreaViewController$shortcutManagerCallback$1 keyguardSecBottomAreaViewController$shortcutManagerCallback$1 = this.shortcutManagerCallback;
        synchronized (keyguardShortcutManager) {
            try {
                if (keyguardShortcutManager.shortcutCallbacks.removeIf(new Predicate() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$unregisterCallback$1$1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((WeakReference) obj).get() == KeyguardShortcutManager.ShortcutCallback.this;
                    }
                })) {
                    Log.d("KeyguardShortcutManager", "Callback removed successfully , callback was : " + keyguardSecBottomAreaViewController$shortcutManagerCallback$1);
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        for (int i = 0; i < 2; i++) {
            if (keyguardShortcutManager.isTaskType(i)) {
                keyguardShortcutManager.keyguardBottomAreaShortcutTask[i].removeListener();
            }
        }
        ((KeyguardStateControllerImpl) this.keyguardStateController).removeCallback(this);
        this.broadcastDispatcher.unregisterReceiver(this.mDevicePolicyReceiver);
        this.wakefulnessLifecycle.removeObserver(this.mWakefulnessObserver);
    }

    @Override // com.android.systemui.pluginlock.PluginLockBottomAreaCallback
    public final void onViewModeChanged(int i) {
        T t = this.mView;
        ((KeyguardSecBottomAreaView) t).isPluginLockOverlayView = i == 1;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onViewModeChanged() ShortcutInvisible: ", "KeyguardSecBottomAreaViewController", ((KeyguardSecBottomAreaView) t).isPluginLockOverlayView);
        ((KeyguardSecBottomAreaView) this.mView).updateLayout();
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController
    public final void setAffordanceAlpha(float f) {
        EmergencyButton emergencyButton;
        getLeftView().setAlpha(f);
        getRightView().setAlpha(f);
        if (this.isIndicationUpdatable) {
            getIndicationArea().setAlpha(f);
        }
        if (((KeyguardIndicationTextView) ((KeyguardSecBottomAreaView) this.mView).upperFPIndication$delegate.getValue()) != null) {
            ((KeyguardIndicationTextView) ((KeyguardSecBottomAreaView) this.mView).upperFPIndication$delegate.getValue()).setAlpha(f);
        }
        LinearLayout linearLayout = ((KeyguardSecBottomAreaView) this.mView).usimTextArea;
        if (linearLayout != null) {
            Intrinsics.checkNotNull(linearLayout);
            linearLayout.setAlpha(f);
        }
        if (CscRune.LOCKUI_BOTTOM_USIM_TEXT && (emergencyButton = ((KeyguardSecBottomAreaView) this.mView).emergencyButton) != null) {
            Intrinsics.checkNotNull(emergencyButton);
            emergencyButton.setAlpha(f);
        }
        if (((QuickSettingsController) this.quickSettingsControllerLazy.get()).getExpanded$1()) {
            cancelIndicationAreaAnim();
        }
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController
    public final void setDozing(boolean z) {
        this.isDozing = z;
        Function0 function0 = ((KeyguardSecBottomAreaView) this.mView).updateRightAffordanceIcon;
        if (function0 == null) {
            function0 = null;
        }
        function0.invoke();
        Function0 function02 = ((KeyguardSecBottomAreaView) this.mView).updateLeftAffordanceIcon;
        (function02 != null ? function02 : null).invoke();
        if (CscRune.LOCKUI_BOTTOM_USIM_TEXT) {
            setUsimTextAreaVisibility();
        }
        Lazy lazy = this.leftShortcutArea$delegate;
        Lazy lazy2 = this.rightShortcutArea$delegate;
        Lazy lazy3 = this.bottomDozeArea$delegate;
        if (z) {
            ((FrameLayout) lazy3.getValue()).setVisibility(0);
            getIndicationArea().setVisibility(4);
            ((View) lazy2.getValue()).setVisibility(4);
            ((View) lazy.getValue()).setVisibility(4);
            Log.d("KeyguardSecBottomAreaViewController", ": ");
            return;
        }
        ((FrameLayout) lazy3.getValue()).removeAllViews();
        ((FrameLayout) lazy3.getValue()).setVisibility(4);
        getIndicationArea().setVisibility(0);
        ((View) lazy2.getValue()).setVisibility(0);
        ((View) lazy.getValue()).setVisibility(0);
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController
    public final void setNowBarVisibility(boolean z) {
        boolean z2 = this.nowBarVisible;
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("setNowBarVisibility  isVisible: ", ", oldVisible: ", ", showShortcutAnim: ", z, z2), this.showShortcutAnim, ", isShortcutAnimRunning: ", this.isShortcutAnimRunning, "KeyguardSecBottomAreaViewController");
        this.nowBarVisible = z;
        Handler handler = ((KeyguardSecBottomAreaView) this.mView).getHandler();
        KeyguardSecBottomAreaViewController$startDelayRunnable$1 keyguardSecBottomAreaViewController$startDelayRunnable$1 = this.startDelayRunnable;
        if (handler.hasCallbacks(keyguardSecBottomAreaViewController$startDelayRunnable$1)) {
            ((KeyguardSecBottomAreaView) this.mView).getHandler().removeCallbacks(keyguardSecBottomAreaViewController$startDelayRunnable$1);
        }
        if (this.showShortcutAnim) {
            if (this.isShortcutAnimRunning) {
                return;
            }
            showShortcutAnimation(20L, this.nowBarVisible);
        } else {
            getLeftView().setNowBarVisibility(new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$setNowBarVisibility$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((Boolean) obj).getClass();
                    KeyguardSecBottomAreaViewController.access$updateFinalShortcut(KeyguardSecBottomAreaViewController.this);
                }
            }, this.nowBarVisible, false);
            getRightView().setNowBarVisibility(new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$setNowBarVisibility$2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((Boolean) obj).getClass();
                    KeyguardSecBottomAreaViewController.access$updateFinalShortcut(KeyguardSecBottomAreaViewController.this);
                }
            }, this.nowBarVisible, false);
        }
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController
    public final void setUserSetupComplete$1(boolean z) {
        this.isUserSetupComplete = z;
        Function0 function0 = ((KeyguardSecBottomAreaView) this.mView).updateRightAffordanceIcon;
        if (function0 == null) {
            function0 = null;
        }
        function0.invoke();
        Function0 function02 = ((KeyguardSecBottomAreaView) this.mView).updateLeftAffordanceIcon;
        (function02 != null ? function02 : null).invoke();
    }

    public final void setUsimTextAreaVisibility() {
        KeyguardSecBottomAreaView keyguardSecBottomAreaView = (KeyguardSecBottomAreaView) this.mView;
        LinearLayout linearLayout = keyguardSecBottomAreaView.usimTextArea;
        if (linearLayout == null) {
            return;
        }
        if (!CscRune.LOCKUI_BOTTOM_USIM_TEXT) {
            keyguardSecBottomAreaView.removeView(linearLayout);
            keyguardSecBottomAreaView.usimTextArea = null;
            return;
        }
        if (this.isDozing) {
            Intrinsics.checkNotNull(linearLayout);
            linearLayout.setVisibility(8);
            return;
        }
        boolean isIccBlockedPermanently = this.keyguardUpdateMonitor.isIccBlockedPermanently();
        boolean z = this.isUsimTextAreaShowing;
        if (isIccBlockedPermanently) {
            LinearLayout linearLayout2 = keyguardSecBottomAreaView.usimTextArea;
            Intrinsics.checkNotNull(linearLayout2);
            linearLayout2.setVisibility(z ? 0 : 8);
        } else if (this.isSecure) {
            LinearLayout linearLayout3 = keyguardSecBottomAreaView.usimTextArea;
            Intrinsics.checkNotNull(linearLayout3);
            linearLayout3.setVisibility(8);
        } else if (z) {
            LinearLayout linearLayout4 = keyguardSecBottomAreaView.usimTextArea;
            Intrinsics.checkNotNull(linearLayout4);
            linearLayout4.setVisibility(0);
        } else {
            LinearLayout linearLayout5 = keyguardSecBottomAreaView.usimTextArea;
            Intrinsics.checkNotNull(linearLayout5);
            linearLayout5.setVisibility(8);
        }
    }

    public final boolean shouldDisableShortcut() {
        if (this.mPermDisableState || this.mSavingMode || (!this.shortcutManager.isShortcutVisibleForMDM) || this.keyguardUpdateMonitor.isKidsModeRunning()) {
            return true;
        }
        return (LsRune.KEYGUARD_FBE && !this.isUserUnlocked) || ((KeyguardSecBottomAreaView) this.mView).isPluginLockOverlayView || this.isDozing || DevicePolicyManagerExtKt.areKeyguardShortcutsDisabled$default(this.devicePolicyManager, this.selectedUserInteractor.getSelectedUserId(false));
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController
    public final void showShortcutAnimation(boolean z) {
        showShortcutAnimation(0L, z);
    }

    public final void updateAffordanceIcon(KeyguardSecAffordanceView keyguardSecAffordanceView) {
        Function0 function0;
        boolean z = keyguardSecAffordanceView.mRight;
        Function0 function02 = null;
        KeyguardSecBottomAreaView keyguardSecBottomAreaView = (KeyguardSecBottomAreaView) this.mView;
        if (!z ? (function0 = keyguardSecBottomAreaView.updateLeftAffordanceIcon) != null : (function0 = keyguardSecBottomAreaView.updateRightAffordanceIcon) != null) {
            function02 = function0;
        }
        function02.invoke();
    }

    @Override // com.android.systemui.pluginlock.PluginLockBottomAreaCallback
    public final void updateBottomView() {
        Log.d("KeyguardSecBottomAreaViewController", "updateBottomView");
        getBinding().updateIndicationPosition();
    }

    public final void updateCustomShortcutIcon(KeyguardSecAffordanceView keyguardSecAffordanceView, int i, boolean z) {
        boolean z2 = !shouldDisableShortcut() && z;
        if (keyguardSecAffordanceView == null || ((KeyguardFastBioUnlockController) Dependency.sDependency.getDependencyInner(KeyguardFastBioUnlockController.class)).isFastWakeAndUnlockMode()) {
            return;
        }
        KeyguardShortcutManager keyguardShortcutManager = this.shortcutManager;
        if (keyguardShortcutManager.hasShortcut(i) && KeyguardShortcutManager.isSamsungCameraPackage(keyguardShortcutManager.shortcutsData[i].componentName) && !shouldDisableShortcut()) {
            dagger.Lazy lazy = this.centralSurfacesLazy;
            boolean z3 = (lazy.get() == null || ((CentralSurfacesImpl) ((CentralSurfaces) lazy.get())).isCameraAllowedByAdmin()) ? false : true;
            boolean z4 = !z3 && keyguardShortcutManager.isLockTaskPermitted("com.sec.android.app.camera") && this.isUserSetupComplete;
            EmergencyButtonController$$ExternalSyntheticOutline0.m("updateCameraVisibility isCameraDisabled:", " visible:", "KeyguardSecBottomAreaViewController", z3, z4);
            z2 = z2 && z4;
        }
        if (!z2) {
            keyguardSecAffordanceView.setVisibility(8);
            return;
        }
        keyguardSecAffordanceView.setVisibility(this.isUserSetupComplete ? 0 : 8);
        keyguardSecAffordanceView.setImageDrawable(keyguardShortcutManager.getShortcutDrawable(i));
        keyguardSecAffordanceView.setContentDescription(keyguardShortcutManager.getShortcutContentDescription(i));
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController
    public final void updateIndicationPosition() {
        Log.d("KeyguardSecBottomAreaViewController", "updateIndicationPosition");
        getBinding().updateIndicationPosition();
    }

    public final void showShortcutAnimation(long j, boolean z) {
        Log.d("KeyguardSecBottomAreaViewController", "showShortcutAnimation  show: " + z + ", delay: " + j);
        this.nowBarVisible = z;
        Handler handler = ((KeyguardSecBottomAreaView) this.mView).getHandler();
        KeyguardSecBottomAreaViewController$startDelayRunnable$1 keyguardSecBottomAreaViewController$startDelayRunnable$1 = this.startDelayRunnable;
        if (handler.hasCallbacks(keyguardSecBottomAreaViewController$startDelayRunnable$1)) {
            ((KeyguardSecBottomAreaView) this.mView).getHandler().removeCallbacks(keyguardSecBottomAreaViewController$startDelayRunnable$1);
        }
        if (j == 0) {
            getLeftView().setNowBarVisibility(new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$showShortcutAnimation$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((Boolean) obj).getClass();
                    KeyguardSecBottomAreaViewController.access$updateFinalShortcut(KeyguardSecBottomAreaViewController.this);
                }
            }, this.nowBarVisible, true);
            getRightView().setNowBarVisibility(new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$showShortcutAnimation$2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((Boolean) obj).getClass();
                    KeyguardSecBottomAreaViewController.access$updateFinalShortcut(KeyguardSecBottomAreaViewController.this);
                }
            }, this.nowBarVisible, true);
        } else {
            ((KeyguardSecBottomAreaView) this.mView).getHandler().postDelayed(keyguardSecBottomAreaViewController$startDelayRunnable$1, j);
        }
        this.isShortcutAnimRunning = true;
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController
    public final void onDensityOrFontScaleChanged() {
        onDensityOrFontScaleChanged(false);
    }
}
