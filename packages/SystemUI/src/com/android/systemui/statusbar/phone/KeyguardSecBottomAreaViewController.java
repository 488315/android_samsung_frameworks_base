package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.SemWallpaperColors;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.devicepolicy.DevicePolicyManagerExtKt;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.keyguard.RankedStateListener;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import com.android.systemui.keyguard.shared.model.CameraLaunchType;
import com.android.systemui.pluginlock.PluginLockBottomAreaCallback;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.CameraLauncher;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$shortcutManagerCallback$1;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.android.systemui.widget.SystemUIWidgetUtil;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class KeyguardSecBottomAreaViewController extends ViewController implements KeyguardStateController.Callback, PluginLockBottomAreaCallback, KeyguardFoldController.StateListener, SystemUIWidgetCallback, Dumpable {
    public static final long APPEAR_ANIM_DURATION;
    public static final String KEY_HELP_TEXT_BOTTOM;
    public static final String KEY_HELP_TEXT_HEIGHT;
    public static final String KEY_HELP_TEXT_VISIBILITY;
    public final ActivityStarter activityStarter;
    public final Lazy bottomDozeArea$delegate;
    public final BroadcastDispatcher broadcastDispatcher;
    public final dagger.Lazy centralSurfacesLazy;
    public final DevicePolicyManager devicePolicyManager;
    public final DumpManager dumpManager;
    public final KeyguardEditModeController editModeController;
    public final KeyguardSecBottomAreaViewController$editModeLisner$1 editModeLisner;
    public final KeyguardFoldController foldController;
    public AnimatorSet helpTextAnimSet;
    public final Lazy indicationArea$delegate;
    public final Lazy indicationText$delegate;
    public boolean isAllShortcutDisabled;
    public boolean isDozing;
    public boolean isIndicationUpdatable;
    public boolean isKeyguardVisible;
    public boolean isNowBarExpanded;
    public boolean isSecure;
    public boolean isShortcutAnimRunning;
    public boolean isUserSetupComplete;
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
    public final dagger.Lazy pluginLockStarManagerLazy;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public static View $r8$lambda$3tiNAI20Fxhrbl_RX2nDpohwyjw(KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController) {
        return (View) ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).leftShortcutEffectview$delegate.getValue();
    }

    /* renamed from: $r8$lambda$D4kp5cmGQ_rGLdyL6o-XY1kQhJo, reason: not valid java name */
    public static ViewGroup m3070$r8$lambda$D4kp5cmGQ_rGLdyL6oXY1kQhJo(KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController) {
        return (ViewGroup) ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).indicationArea$delegate.getValue();
    }

    /* renamed from: $r8$lambda$Jgv21cVoSLZlA51xet8mwVw-iBs, reason: not valid java name */
    public static View m3071$r8$lambda$Jgv21cVoSLZlA51xet8mwVwiBs(KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController) {
        return (View) ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).leftShortcutArea$delegate.getValue();
    }

    public static View $r8$lambda$M7LuDowF8AhrK_kb4xF8Rm3VIPs(KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController) {
        return (View) ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).rightShortcutArea$delegate.getValue();
    }

    /* renamed from: $r8$lambda$YOkFmR-iWxOyhXrD-zbAm3sPmT8, reason: not valid java name */
    public static TextView m3072$r8$lambda$YOkFmRiWxOyhXrDzbAm3sPmT8(KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController) {
        return (TextView) ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).indicationText$delegate.getValue();
    }

    public static FrameLayout $r8$lambda$ZdrlybJBwxxHo4Ff_gUFuEpgBw8(KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController) {
        return (FrameLayout) ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).bottomDozeArea$delegate.getValue();
    }

    public static View $r8$lambda$dPSJRC_qbr4lGWx6ve6RagZjQOo(KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController) {
        return (View) ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).rightShortcutEffectview$delegate.getValue();
    }

    static {
        new Companion(null);
        APPEAR_ANIM_DURATION = 1000L;
        KEY_HELP_TEXT_VISIBILITY = "help_text_visibility";
        KEY_HELP_TEXT_HEIGHT = "help_text_height";
        KEY_HELP_TEXT_BOTTOM = "help_text_margin";
    }

    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$keyguardUpdateMonitorCallbackForShortcuts$1] */
    /* JADX WARN: Type inference failed for: r1v14, types: [com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$mWakefulnessObserver$1] */
    /* JADX WARN: Type inference failed for: r1v16, types: [com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$shortcutAnimRunnable$1] */
    /* JADX WARN: Type inference failed for: r1v17, types: [com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$startDelayRunnable$1] */
    /* JADX WARN: Type inference failed for: r1v18, types: [com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$restoreRunnable$1] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$editModeLisner$1] */
    /* JADX WARN: Type inference failed for: r4v10, types: [com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$mDevicePolicyReceiver$1] */
    public KeyguardSecBottomAreaViewController(ActivityStarter activityStarter, DevicePolicyManager devicePolicyManager, BroadcastDispatcher broadcastDispatcher, dagger.Lazy lazy, KeyguardInteractor keyguardInteractor, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, dagger.Lazy lazy2, SettingsHelper settingsHelper, dagger.Lazy lazy3, WakefulnessLifecycle wakefulnessLifecycle, SelectedUserInteractor selectedUserInteractor, KeyguardSecBottomAreaView keyguardSecBottomAreaView, LockscreenSmartspaceController lockscreenSmartspaceController, FeatureFlagsClassic featureFlagsClassic, DumpManager dumpManager, KeyguardEditModeController keyguardEditModeController, dagger.Lazy lazy4, KeyguardFoldController keyguardFoldController) {
        super(keyguardSecBottomAreaView);
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
        this.editModeController = keyguardEditModeController;
        this.pluginLockStarManagerLazy = lazy4;
        this.foldController = keyguardFoldController;
        KeyguardShortcutManager keyguardShortcutManager = (KeyguardShortcutManager) lazy3.get();
        this.shortcutManager = keyguardShortcutManager;
        this.editModeLisner = new KeyguardEditModeController.Listener() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$editModeLisner$1
            @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
            public final void onAnimationStarted(boolean z) {
                if (z) {
                    return;
                }
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                if (KeyguardSecBottomAreaViewController.access$isEditorShortcutTransitionNeeded(0, keyguardSecBottomAreaViewController) && !keyguardSecBottomAreaViewController.isNowBarExpanded) {
                    KeyguardSecBottomAreaViewController.access$changeDimToBlur(keyguardSecBottomAreaViewController.getLeftView(), keyguardSecBottomAreaViewController);
                }
                if (!KeyguardSecBottomAreaViewController.access$isEditorShortcutTransitionNeeded(1, keyguardSecBottomAreaViewController) || keyguardSecBottomAreaViewController.isNowBarExpanded) {
                    return;
                }
                KeyguardSecBottomAreaViewController.access$changeDimToBlur(keyguardSecBottomAreaViewController.getRightView(), keyguardSecBottomAreaViewController);
            }

            @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
            public final void onTouchDownCanceled() {
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                if (KeyguardSecBottomAreaViewController.access$isEditorShortcutTransitionNeeded(0, keyguardSecBottomAreaViewController) && !keyguardSecBottomAreaViewController.isNowBarExpanded) {
                    KeyguardSecBottomAreaViewController.access$changeDimToBlur(keyguardSecBottomAreaViewController.getLeftView(), keyguardSecBottomAreaViewController);
                }
                if (!KeyguardSecBottomAreaViewController.access$isEditorShortcutTransitionNeeded(1, keyguardSecBottomAreaViewController) || keyguardSecBottomAreaViewController.isNowBarExpanded) {
                    return;
                }
                KeyguardSecBottomAreaViewController.access$changeDimToBlur(keyguardSecBottomAreaViewController.getRightView(), keyguardSecBottomAreaViewController);
            }

            @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
            public final void onTouchDownStarted() {
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                if (KeyguardSecBottomAreaViewController.access$isEditorShortcutTransitionNeeded(0, keyguardSecBottomAreaViewController) && !keyguardSecBottomAreaViewController.isNowBarExpanded) {
                    KeyguardSecBottomAreaViewController.access$changeBlurToDim(keyguardSecBottomAreaViewController.getLeftView(), keyguardSecBottomAreaViewController);
                }
                if (!KeyguardSecBottomAreaViewController.access$isEditorShortcutTransitionNeeded(1, keyguardSecBottomAreaViewController) || keyguardSecBottomAreaViewController.isNowBarExpanded) {
                    return;
                }
                KeyguardSecBottomAreaViewController.access$changeBlurToDim(keyguardSecBottomAreaViewController.getRightView(), keyguardSecBottomAreaViewController);
            }

            @Override // com.android.systemui.keyguard.KeyguardEditModeController.Listener
            public final void onAnimationEnded() {
            }
        };
        this.leftView$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0(7, this));
        this.rightView$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0(8, this));
        this.leftShortcutArea$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0(9, this));
        this.rightShortcutArea$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0(10, this));
        this.leftShortcutEffectview$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0(11, this));
        this.rightShortcutEffectview$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0(12, this));
        this.indicationArea$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0(1, this));
        this.indicationText$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0(2, this));
        this.bottomDozeArea$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0(3, this));
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
                        List<KeyguardSecAffordanceView> asList = Arrays.asList(keyguardSecBottomAreaViewController2.getLeftView(), KeyguardSecBottomAreaViewController.this.getRightView());
                        KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController3 = KeyguardSecBottomAreaViewController.this;
                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(asList, 10));
                        for (KeyguardSecAffordanceView keyguardSecAffordanceView : asList) {
                            if (keyguardSecAffordanceView.mShortcutForCamera) {
                                keyguardSecBottomAreaViewController3.updateAffordanceIcon(keyguardSecAffordanceView);
                            }
                            arrayList.add(Unit.INSTANCE);
                        }
                    }
                });
            }
        };
        this.mSavingMode = settingsHelper.isUltraPowerSavingMode() || settingsHelper.isEmergencyMode();
        this.mEasyMode = settingsHelper.isEasyModeOn();
        this.mPermDisableState = CscRune.SECURITY_SIM_PERM_DISABLED && keyguardUpdateMonitor.isIccBlockedPermanently();
        this.isUserSetupComplete = Settings.Secure.getInt(getContext().getContentResolver(), SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0) == 1;
        this.mShortcutCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$mShortcutCallback$1
            /* JADX WARN: Code restructure failed: missing block: B:33:0x0091, code lost:
            
                if (r0.isEmergencyMode() != false) goto L34;
             */
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onChanged(android.net.Uri r4) {
                /*
                    r3 = this;
                    if (r4 != 0) goto L4
                    goto L9d
                L4:
                    java.lang.String r0 = "ultra_powersaving_mode"
                    android.net.Uri r0 = android.provider.Settings.System.getUriFor(r0)
                    boolean r0 = r4.equals(r0)
                    r1 = 0
                    r2 = 1
                    com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController r3 = com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController.this
                    if (r0 != 0) goto L7d
                    java.lang.String r0 = "minimal_battery_use"
                    android.net.Uri r0 = android.provider.Settings.System.getUriFor(r0)
                    boolean r0 = r4.equals(r0)
                    if (r0 != 0) goto L7d
                    java.lang.String r0 = "emergency_mode"
                    android.net.Uri r0 = android.provider.Settings.System.getUriFor(r0)
                    boolean r0 = r4.equals(r0)
                    if (r0 == 0) goto L2e
                    goto L7d
                L2e:
                    java.lang.String r0 = "easy_mode_switch"
                    android.net.Uri r0 = android.provider.Settings.System.getUriFor(r0)
                    boolean r0 = r4.equals(r0)
                    if (r0 == 0) goto L4e
                    boolean r4 = r3.mEasyMode
                    com.android.systemui.util.SettingsHelper r0 = com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController.access$getSettingsHelper$p(r3)
                    boolean r0 = r0.isEasyModeOn()
                    r3.mEasyMode = r0
                    boolean r0 = r3.mEasyMode
                    if (r4 == r0) goto L9d
                    r3.onDensityOrFontScaleChanged(r2)
                    return
                L4e:
                    java.lang.String r0 = "lock_shortcut_type"
                    android.net.Uri r0 = android.provider.Settings.System.getUriFor(r0)
                    boolean r0 = r4.equals(r0)
                    if (r0 == 0) goto L67
                    boolean r4 = r3.mSavingMode
                    if (r4 != 0) goto L62
                    boolean r4 = r3.mEasyMode
                    if (r4 == 0) goto L63
                L62:
                    r1 = r2
                L63:
                    r3.onDensityOrFontScaleChanged(r1)
                    return
                L67:
                    java.lang.String r0 = "display_cutout_hide_notch"
                    android.net.Uri r0 = android.provider.Settings.Secure.getUriFor(r0)
                    boolean r4 = r4.equals(r0)
                    if (r4 == 0) goto L9d
                    android.view.View r3 = com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController.access$getMView$p$s2038760804(r3)
                    com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView r3 = (com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView) r3
                    r3.updateLayout()
                    return
                L7d:
                    boolean r4 = r3.mSavingMode
                    com.android.systemui.util.SettingsHelper r0 = com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController.access$getSettingsHelper$p(r3)
                    boolean r0 = r0.isUltraPowerSavingMode()
                    if (r0 != 0) goto L93
                    com.android.systemui.util.SettingsHelper r0 = com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController.access$getSettingsHelper$p(r3)
                    boolean r0 = r0.isEmergencyMode()
                    if (r0 == 0) goto L94
                L93:
                    r1 = r2
                L94:
                    r3.mSavingMode = r1
                    boolean r0 = r3.mSavingMode
                    if (r4 == r0) goto L9d
                    r3.onDensityOrFontScaleChanged(r2)
                L9d:
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
                View view2;
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                keyguardSecBottomAreaViewController.isKeyguardVisible = z;
                view = ((ViewController) keyguardSecBottomAreaViewController).mView;
                ((KeyguardSecBottomAreaView) view).isKeyguardVisible = keyguardSecBottomAreaViewController.isKeyguardVisible;
                KeyguardSecBottomAreaViewController.access$setIndicationUpdatable(keyguardSecBottomAreaViewController, z);
                view2 = ((ViewController) keyguardSecBottomAreaViewController).mView;
                ((KeyguardSecBottomAreaView) view2).updateIndicationPosition();
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
                View view;
                boolean isOutOfService;
                boolean z = CscRune.SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE;
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                if (z && this.mOutOfService != (isOutOfService = keyguardSecBottomAreaViewController.keyguardUpdateMonitor.isOutOfService())) {
                    this.mOutOfService = isOutOfService;
                    List<KeyguardSecAffordanceView> asList = Arrays.asList(keyguardSecBottomAreaViewController.getLeftView(), keyguardSecBottomAreaViewController.getRightView());
                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(asList, 10));
                    for (KeyguardSecAffordanceView keyguardSecAffordanceView : asList) {
                        if (keyguardSecAffordanceView.mIsShortcutForPhone) {
                            keyguardSecBottomAreaViewController.updateAffordanceIcon(keyguardSecAffordanceView);
                        }
                        arrayList.add(Unit.INSTANCE);
                    }
                }
                view = ((ViewController) keyguardSecBottomAreaViewController).mView;
                ((KeyguardSecBottomAreaView) view).updateIndicationPosition();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimStateChanged(int i, int i2, int i3) {
                View view;
                View view2;
                boolean z = CscRune.LOCKUI_BOTTOM_USIM_TEXT;
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                if (z) {
                    view2 = ((ViewController) keyguardSecBottomAreaViewController).mView;
                    ((KeyguardSecBottomAreaView) view2).currentSimState = i3;
                }
                if (CscRune.SECURITY_SIM_PERM_DISABLED) {
                    keyguardSecBottomAreaViewController.mPermDisableState = keyguardSecBottomAreaViewController.keyguardUpdateMonitor.isIccBlockedPermanently();
                }
                if (((TextView) keyguardSecBottomAreaViewController.indicationText$delegate.getValue()) != null) {
                    boolean z2 = keyguardSecBottomAreaViewController.mPermDisableState;
                    Lazy lazy5 = keyguardSecBottomAreaViewController.indicationText$delegate;
                    if (z2) {
                        ((TextView) lazy5.getValue()).setVisibility(4);
                    } else if (keyguardSecBottomAreaViewController.isSecure) {
                        if (!keyguardSecBottomAreaViewController.keyguardUpdateMonitor.getUserCanSkipBouncer(keyguardSecBottomAreaViewController.selectedUserInteractor.getSelectedUserId())) {
                            ((TextView) lazy5.getValue()).setVisibility(0);
                        }
                    }
                }
                view = ((ViewController) keyguardSecBottomAreaViewController).mView;
                ((KeyguardSecBottomAreaView) view).updateIndicationPosition();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitchComplete(int i) {
                View view;
                view = ((ViewController) KeyguardSecBottomAreaViewController.this).mView;
                ((KeyguardSecBottomAreaView) view).updateLayout();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserUnlocked() {
                String str = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                Log.d("KeyguardSecBottomAreaViewController", "onUserUnlocked() :" + keyguardSecBottomAreaViewController.isUserUnlocked$2());
                keyguardSecBottomAreaViewController.showShortcutsIfPossible();
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
                keyguardSecBottomAreaViewController.getLeftView().onPostFinishedWakingUp();
                keyguardSecBottomAreaViewController.getRightView().onPostFinishedWakingUp();
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
                animatorSet2.getClass();
                animatorSet2.setInterpolator(keyguardSecBottomAreaViewController.mInterpolator);
                AnimatorSet animatorSet3 = keyguardSecBottomAreaViewController.helpTextAnimSet;
                animatorSet3.getClass();
                animatorSet3.setDuration(KeyguardSecBottomAreaViewController.APPEAR_ANIM_DURATION);
                AnimatorSet animatorSet4 = keyguardSecBottomAreaViewController.helpTextAnimSet;
                animatorSet4.getClass();
                animatorSet4.setStartDelay(0L);
                AnimatorSet animatorSet5 = keyguardSecBottomAreaViewController.helpTextAnimSet;
                animatorSet5.getClass();
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
                animatorSet6.getClass();
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
                boolean z = keyguardSecBottomAreaViewController2.nowBarVisible;
                Consumer consumer = new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$startDelayRunnable$1.1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((Boolean) obj).getClass();
                        KeyguardSecBottomAreaViewController.access$updateFinalShortcut(KeyguardSecBottomAreaViewController.this);
                    }
                };
                final KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController3 = KeyguardSecBottomAreaViewController.this;
                leftView.setNowBarVisibility(z, consumer, new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$startDelayRunnable$1.2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        KeyguardSecBottomAreaViewController.this.isShortcutAnimRunning = ((Boolean) obj).booleanValue();
                    }
                }, true);
                KeyguardSecAffordanceView rightView = KeyguardSecBottomAreaViewController.this.getRightView();
                final KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController4 = KeyguardSecBottomAreaViewController.this;
                boolean z2 = keyguardSecBottomAreaViewController4.nowBarVisible;
                Consumer consumer2 = new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$startDelayRunnable$1.3
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((Boolean) obj).getClass();
                        KeyguardSecBottomAreaViewController.access$updateFinalShortcut(KeyguardSecBottomAreaViewController.this);
                    }
                };
                final KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController5 = KeyguardSecBottomAreaViewController.this;
                rightView.setNowBarVisibility(z2, consumer2, new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$startDelayRunnable$1.4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        KeyguardSecBottomAreaViewController.this.isShortcutAnimRunning = ((Boolean) obj).booleanValue();
                    }
                }, true);
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

    public static final void access$changeBlurToDim(final KeyguardSecAffordanceView keyguardSecAffordanceView, KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController) {
        keyguardSecBottomAreaViewController.getClass();
        if (keyguardSecAffordanceView != null) {
            keyguardSecAffordanceView.setIsDrawBackgroundCircle(true);
        }
        if (keyguardSecAffordanceView != null) {
            int parseColor = Color.parseColor("#2B2B2B");
            keyguardSecAffordanceView.mDrawBackgroundColor = parseColor;
            if (parseColor != -1) {
                keyguardSecAffordanceView.mBackgroundCirclePaint.setColor(parseColor);
            }
        }
        if (keyguardSecAffordanceView != null) {
            keyguardSecAffordanceView.setDrawBackgroundAlpha(0);
        }
        ValueAnimator ofFloat = ObjectAnimator.ofFloat(1.0f, 0.0f);
        ofFloat.setDuration(500L);
        ofFloat.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$changeBlurToDim$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView2;
                Drawable background;
                int floatValue = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 255);
                KeyguardSecAffordanceView keyguardSecAffordanceView3 = KeyguardSecAffordanceView.this;
                if (keyguardSecAffordanceView3 != null && (background = keyguardSecAffordanceView3.getBackground()) != null) {
                    background.setAlpha(floatValue);
                }
                if (floatValue == 0 && (keyguardSecAffordanceView2 = KeyguardSecAffordanceView.this) != null) {
                    keyguardSecAffordanceView2.updateBgBlur(false);
                }
                KeyguardSecAffordanceView keyguardSecAffordanceView4 = KeyguardSecAffordanceView.this;
                if (keyguardSecAffordanceView4 != null) {
                    keyguardSecAffordanceView4.setDrawBackgroundAlpha((int) ((255 - floatValue) * 0.5d));
                }
            }
        });
        ofFloat.start();
    }

    public static final void access$changeDimToBlur(final KeyguardSecAffordanceView keyguardSecAffordanceView, final KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController) {
        Drawable background;
        keyguardSecBottomAreaViewController.getClass();
        if (keyguardSecAffordanceView != null) {
            keyguardSecAffordanceView.setIsDrawBackgroundCircle(true);
        }
        if (keyguardSecAffordanceView != null) {
            int parseColor = Color.parseColor("#2B2B2B");
            keyguardSecAffordanceView.mDrawBackgroundColor = parseColor;
            if (parseColor != -1) {
                keyguardSecAffordanceView.mBackgroundCirclePaint.setColor(parseColor);
            }
        }
        if (keyguardSecAffordanceView != null) {
            keyguardSecAffordanceView.updateBgBlur(keyguardSecBottomAreaViewController.keyguardStateController.isVisible());
        }
        if (keyguardSecAffordanceView != null && (background = keyguardSecAffordanceView.getBackground()) != null) {
            background.setAlpha(0);
        }
        ValueAnimator ofFloat = ObjectAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(500L);
        ofFloat.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$changeDimToBlur$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView2;
                Drawable background2;
                int floatValue = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 255);
                KeyguardSecAffordanceView keyguardSecAffordanceView3 = KeyguardSecAffordanceView.this;
                if (keyguardSecAffordanceView3 != null && (background2 = keyguardSecAffordanceView3.getBackground()) != null) {
                    background2.setAlpha(floatValue);
                }
                KeyguardSecAffordanceView keyguardSecAffordanceView4 = KeyguardSecAffordanceView.this;
                if (keyguardSecAffordanceView4 != null) {
                    keyguardSecAffordanceView4.setDrawBackgroundAlpha((int) ((255 - floatValue) * 0.5d));
                }
                if (floatValue != 255 || (keyguardSecAffordanceView2 = KeyguardSecAffordanceView.this) == null) {
                    return;
                }
                keyguardSecAffordanceView2.updateBgBlur(keyguardSecBottomAreaViewController.keyguardStateController.isVisible());
            }
        });
        ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$changeDimToBlur$lambda$5$$inlined$doOnEnd$1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                KeyguardSecAffordanceView keyguardSecAffordanceView2 = KeyguardSecAffordanceView.this;
                if (keyguardSecAffordanceView2 != null) {
                    keyguardSecAffordanceView2.mDrawBackgroundColor = -1;
                }
                if (keyguardSecAffordanceView2 != null) {
                    keyguardSecAffordanceView2.setIsDrawBackgroundCircle(false);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        ofFloat.start();
    }

    public static final boolean access$isEditorShortcutTransitionNeeded(int i, KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController) {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardSecBottomAreaViewController.keyguardStateController;
        return (keyguardStateControllerImpl.mCanDismissLockScreen || !keyguardStateControllerImpl.mSecure) && keyguardSecBottomAreaViewController.shortcutManager.isMonotoneIcon(i);
    }

    public static final void access$setIndicationUpdatable(KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController, boolean z) {
        if (keyguardSecBottomAreaViewController.isIndicationUpdatable == z) {
            return;
        }
        EmergencyButtonController$$ExternalSyntheticOutline0.m("mCanIndicationAreaUpdate set to ", "KeyguardSecBottomAreaViewController", z);
        keyguardSecBottomAreaViewController.isIndicationUpdatable = z;
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
        ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).updateShortcutPosition();
        keyguardSecBottomAreaViewController.isShortcutAnimRunning = false;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        SelectedUserInteractor selectedUserInteractor = this.selectedUserInteractor;
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  CurrentUserId = ", selectedUserInteractor.getSelectedUserId(), printWriter);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  isNowBarVisible = ", this.nowBarVisible);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  bottomAreaVisibility = ", ((KeyguardSecBottomAreaView) this.mView).getVisibility(), printWriter);
        printWriter.println("  bottomAreaAlpha = " + ((KeyguardSecBottomAreaView) this.mView).getAlpha());
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  shouldDisableShortcut = ", shouldDisableShortcut());
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  permDisableState = ", this.mPermDisableState);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  savingMode = ", this.mSavingMode);
        printWriter.println("  shouldDisableShortcutWithMdm = " + (!this.shortcutManager.isShortcutVisibleForMDM));
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  isUserUnlocked = ", isUserUnlocked$2());
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  isPluginLockOverlayView = ", ((KeyguardSecBottomAreaView) this.mView).isPluginLockOverlayView);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  isDozing = ", this.isDozing);
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  areKeyguardShortcutsDisabled = ", DevicePolicyManagerExtKt.areKeyguardShortcutsDisabled$default(this.devicePolicyManager, selectedUserInteractor.getSelectedUserId()));
        KeyguardSecBottomAreaView.ConfigurationBasedDimensions configurationBasedDimensions = (KeyguardSecBottomAreaView.ConfigurationBasedDimensions) ((KeyguardSecBottomAreaView) this.mView).configurationBasedDimensions.getValue();
        printWriter.println("  shortcutSize = " + configurationBasedDimensions.buttonSizePx);
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  shortcutBottomMargin = ", configurationBasedDimensions.shortcutBottomMargin, printWriter);
        printWriter.println("  shortcutSideMargin = " + configurationBasedDimensions.shortcutSideMargin);
        printWriter.println("  LeftShortcut:");
        if (getLeftView() != null) {
            printWriter.println("      visibility = " + getLeftView().getVisibility());
            printWriter.println("      alpha = " + getLeftView().getAlpha());
            DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("      size = ", getLeftView().getWidth(), printWriter);
            int[] iArr = new int[2];
            getLeftView().getLocationOnScreen(iArr);
            printWriter.println("      left = " + iArr[0] + ", top = " + iArr[1]);
        }
        printWriter.println("  RightShortcut:");
        if (getRightView() != null) {
            printWriter.println("      visibility = " + getRightView().getVisibility());
            printWriter.println("      alpha = " + getRightView().getAlpha());
            DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("      size = ", getRightView().getWidth(), printWriter);
            int[] iArr2 = new int[2];
            getRightView().getLocationOnScreen(iArr2);
            printWriter.println("      left = " + iArr2[0] + ", top = " + iArr2[1]);
        }
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

    public final int getUsimTextAreaHeight() {
        LinearLayout linearLayout = ((KeyguardSecBottomAreaView) this.mView).usimTextArea;
        int i = 0;
        if (linearLayout != null) {
            if (linearLayout.getVisibility() != 0) {
                linearLayout = null;
            }
            if (linearLayout != null) {
                EmergencyButton emergencyButton = ((KeyguardSecBottomAreaView) this.mView).emergencyButton;
                if (emergencyButton != null) {
                    if (emergencyButton.getVisibility() != 0) {
                        emergencyButton = null;
                    }
                    if (emergencyButton != null) {
                        i = ((KeyguardSecBottomAreaView) this.mView).getResources().getDimensionPixelSize(R.dimen.keyguard_emergency_button);
                    }
                }
                KeyguardUsimTextView keyguardUsimTextView = ((KeyguardSecBottomAreaView) this.mView).usimCarrierText;
                if (keyguardUsimTextView == null) {
                    keyguardUsimTextView = null;
                }
                if (keyguardUsimTextView != null) {
                    if ((keyguardUsimTextView.getVisibility() == 0 ? keyguardUsimTextView : null) != null) {
                        return ((KeyguardSecBottomAreaView) this.mView).getResources().getDimensionPixelSize(R.dimen.keyguard_usim_text_area) + i;
                    }
                }
            }
        }
        return i;
    }

    public final KeyguardSecBottomAreaView getView() {
        return (KeyguardSecBottomAreaView) this.mView;
    }

    @Override // com.android.systemui.pluginlock.PluginLockBottomAreaCallback
    public final boolean isNoUnlockNeed(String str) {
        LogUtil.d("KeyguardSecBottomAreaViewController", KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("isNoUnlockNeed mIsSecure: ", this.isSecure), new Object[0]);
        if (str != null && str.length() != 0 && this.isSecure) {
            KeyguardShortcutManager keyguardShortcutManager = this.shortcutManager;
            keyguardShortcutManager.getClass();
            if (str != null && str.length() != 0 && (Intrinsics.areEqual(str, "com.sec.android.app.camera") || keyguardShortcutManager.isShortcutPermission(str))) {
                return true;
            }
        }
        return false;
    }

    public final boolean isUserUnlocked$2() {
        if (LsRune.KEYGUARD_FBE) {
            return this.keyguardUpdateMonitor.isUserUnlocked$1();
        }
        return true;
    }

    @Override // com.android.systemui.pluginlock.PluginLockBottomAreaCallback
    public final void launchApp(ComponentName componentName) {
        LogUtil.d("KeyguardSecBottomAreaViewController", AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("launchApp pkg: ", componentName.getPackageName()), new Object[0]);
        KeyguardShortcutManager keyguardShortcutManager = this.shortcutManager;
        keyguardShortcutManager.getClass();
        if (KeyguardShortcutManager.isSamsungCameraPackage(componentName)) {
            CameraLauncher cameraLauncher = (CameraLauncher) ((CentralSurfacesImpl) ((CentralSurfaces) this.centralSurfacesLazy.get())).mCameraLauncherLazy.get();
            CameraLaunchType cameraLaunchType = CameraLaunchType.QUICK_AFFORDANCE;
            this.keyguardInteractor.getClass();
            cameraLauncher.launchCamera(KeyguardInteractor.cameraLaunchSourceModelToInt(cameraLaunchType), true);
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
                    keyguardShortcutManager.shortcutIconSize = keyguardShortcutManager.getShortcutIconSizeValue(keyguardShortcutManager.isNowBarVisible);
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

    @Override // com.android.systemui.keyguard.KeyguardFoldController.StateListener
    public final void onFoldStateChanged(boolean z) {
        ((KeyguardSecBottomAreaView) this.mView).postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$onFoldStateChanged$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = KeyguardSecBottomAreaViewController.this;
                keyguardSecBottomAreaViewController.onDensityOrFontScaleChanged(keyguardSecBottomAreaViewController.mSavingMode || keyguardSecBottomAreaViewController.mEasyMode);
            }
        }, 100L);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
        this.settingsHelper.registerCallback(this.mShortcutCallback, (Uri[]) Arrays.copyOf(new Uri[]{Settings.System.getUriFor(SettingsHelper.INDEX_ULTRA_POWERSAVING_MODE), Settings.System.getUriFor(SettingsHelper.INDEX_MINIMAL_BATTERY_USE), Settings.System.getUriFor(SettingsHelper.INDEX_EMERGENCY_MODE), Settings.System.getUriFor(SettingsHelper.INDEX_EASY_MODE_SWITCH), Settings.System.getUriFor(SettingsHelper.INDEX_LOCK_SHORTCUT_TYPE), Settings.Secure.getUriFor("display_cutout_hide_notch")}, 6));
        KeyguardSecBottomAreaView keyguardSecBottomAreaView = (KeyguardSecBottomAreaView) this.mView;
        keyguardSecBottomAreaView.showShortcutsIfPossible = new KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0(0, this);
        keyguardSecBottomAreaView.updateLeftAffordanceIcon = new KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0(4, this);
        keyguardSecBottomAreaView.updateRightAffordanceIcon = new KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0(5, this);
        keyguardSecBottomAreaView.setUsimTextAreaVisibility = new KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0(6, this);
        keyguardSecBottomAreaView.pluginLockStarManagerLazy = this.pluginLockStarManagerLazy;
        DumpManager.registerDumpable$default(this.dumpManager, "KeyguardSecBottomAreaViewController", this);
    }

    @Override // com.android.systemui.pluginlock.PluginLockBottomAreaCallback
    public final Bundle onUiInfoRequested(boolean z) {
        Bundle bundle = new Bundle();
        KeyguardSecBottomAreaView.ConfigurationBasedDimensions configurationBasedDimensions = (KeyguardSecBottomAreaView.ConfigurationBasedDimensions) ((KeyguardSecBottomAreaView) this.mView).configurationBasedDimensions.getValue();
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
        KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 = ((KeyguardSecBottomAreaView) this.mView).updateRightAffordanceIcon;
        if (keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 == null) {
            keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 = null;
        }
        keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0.invoke();
        KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 = ((KeyguardSecBottomAreaView) this.mView).updateLeftAffordanceIcon;
        (keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 != null ? keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 : null).invoke();
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
        boolean z = keyguardStateControllerImpl.mTrusted;
        this.isSecure = keyguardStateControllerImpl.mSecure;
        setUsimTextAreaVisibility();
        if (this.keyguardUpdateMonitor.isKeyguardUnlocking() || !this.isKeyguardVisible) {
            Log.d("KeyguardSecBottomAreaViewController", "onUnlockMethodStateChanged not keyguardShowing status return!");
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.keyguardUpdateMonitor.registerCallback(this.keyguardUpdateMonitorCallbackForShortcuts);
        KeyguardShortcutManager keyguardShortcutManager = this.shortcutManager;
        KeyguardSecBottomAreaViewController$shortcutManagerCallback$1 keyguardSecBottomAreaViewController$shortcutManagerCallback$1 = this.shortcutManagerCallback;
        synchronized (keyguardShortcutManager) {
            int size = keyguardShortcutManager.shortcutCallbacks.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    Objects.toString(keyguardSecBottomAreaViewController$shortcutManagerCallback$1);
                    keyguardShortcutManager.shortcutCallbacks.add(new WeakReference(keyguardSecBottomAreaViewController$shortcutManagerCallback$1));
                    if (UserManager.get(keyguardShortcutManager.context).isUserUnlocked(keyguardShortcutManager.selectedUserInteractor.getSelectedUserId())) {
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
        ((ArrayList) ((KeyguardEditModeControllerImpl) this.editModeController).listeners).add(this.editModeLisner);
        if (LsRune.LOCKUI_SUB_DISPLAY_LOCK) {
            ((KeyguardFoldControllerImpl) this.foldController).addCallback(this, 6, false);
        }
        SystemUIWidgetUtil.registerSystemUIWidgetCallback(this, -1L);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        RankedStateListener rankedStateListener;
        this.keyguardUpdateMonitor.removeCallback(this.keyguardUpdateMonitorCallbackForShortcuts);
        KeyguardShortcutManager keyguardShortcutManager = this.shortcutManager;
        final KeyguardSecBottomAreaViewController$shortcutManagerCallback$1 keyguardSecBottomAreaViewController$shortcutManagerCallback$1 = this.shortcutManagerCallback;
        synchronized (keyguardShortcutManager) {
            try {
                ArrayList arrayList = keyguardShortcutManager.shortcutCallbacks;
                final Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        KeyguardShortcutManager.Companion companion = KeyguardShortcutManager.Companion;
                        return Boolean.valueOf(((WeakReference) obj).get() == KeyguardSecBottomAreaViewController$shortcutManagerCallback$1.this);
                    }
                };
                if (arrayList.removeIf(new Predicate() { // from class: com.android.systemui.statusbar.KeyguardShortcutManager$sam$java_util_function_Predicate$0
                    @Override // java.util.function.Predicate
                    public final /* synthetic */ boolean test(Object obj) {
                        return ((Boolean) Function1.this.mo779invoke(obj)).booleanValue();
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
        ((ArrayList) ((KeyguardEditModeControllerImpl) this.editModeController).listeners).remove(this.editModeLisner);
        if (LsRune.LOCKUI_SUB_DISPLAY_LOCK) {
            KeyguardFoldControllerImpl keyguardFoldControllerImpl = (KeyguardFoldControllerImpl) this.foldController;
            keyguardFoldControllerImpl.getClass();
            Iterator it = Arrays.asList(keyguardFoldControllerImpl.highRankedStateListeners, keyguardFoldControllerImpl.normalRankedStateListeners).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                List list = (List) it.next();
                Iterator it2 = list.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        rankedStateListener = (RankedStateListener) it2.next();
                        if (Intrinsics.areEqual(rankedStateListener.stateListener, this)) {
                            break;
                        }
                    } else {
                        rankedStateListener = null;
                        break;
                    }
                }
                if (rankedStateListener != null) {
                    list.remove(rankedStateListener);
                    break;
                }
            }
        }
        if (WallpaperEventNotifier.getInstance() != null) {
            WallpaperEventNotifier.getInstance().removeCallback(false, this);
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockBottomAreaCallback
    public final void onViewModeChanged(int i) {
        KeyguardSecBottomAreaView keyguardSecBottomAreaView = (KeyguardSecBottomAreaView) this.mView;
        boolean z = i == 1;
        keyguardSecBottomAreaView.isPluginLockOverlayView = z;
        EmergencyButtonController$$ExternalSyntheticOutline0.m("onViewModeChanged() ShortcutInvisible: ", "KeyguardSecBottomAreaViewController", z);
        ((KeyguardSecBottomAreaView) this.mView).updateLayout();
        KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 = ((KeyguardSecBottomAreaView) this.mView).updateLeftAffordanceIcon;
        if (keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 == null) {
            keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 = null;
        }
        keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0.invoke();
        KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 = ((KeyguardSecBottomAreaView) this.mView).updateRightAffordanceIcon;
        (keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 != null ? keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 : null).invoke();
    }

    public final void setAffordanceAlpha(float f) {
        AnimatorSet animatorSet;
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
            linearLayout.getClass();
            linearLayout.setAlpha(f);
        }
        if (CscRune.LOCKUI_BOTTOM_USIM_TEXT && (emergencyButton = ((KeyguardSecBottomAreaView) this.mView).emergencyButton) != null) {
            emergencyButton.getClass();
            emergencyButton.setAlpha(f);
        }
        if (((QuickSettingsController) this.quickSettingsControllerLazy.get()).getExpanded() && (animatorSet = this.helpTextAnimSet) != null && animatorSet.isRunning()) {
            AnimatorSet animatorSet2 = this.helpTextAnimSet;
            animatorSet2.getClass();
            animatorSet2.cancel();
        }
    }

    public final void setDozing(boolean z) {
        this.isDozing = z;
        KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 = ((KeyguardSecBottomAreaView) this.mView).updateRightAffordanceIcon;
        if (keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 == null) {
            keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 = null;
        }
        keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0.invoke();
        KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 = ((KeyguardSecBottomAreaView) this.mView).updateLeftAffordanceIcon;
        (keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 != null ? keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 : null).invoke();
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

    public final void setNowBarVisibility(boolean z) {
        boolean z2 = this.nowBarVisible;
        CarrierTextManager$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("setNowBarVisibility  isVisible: ", ", oldVisible: ", ", showShortcutAnim: ", z, z2), this.showShortcutAnim, ", isShortcutAnimRunning: ", this.isShortcutAnimRunning, "KeyguardSecBottomAreaViewController");
        this.nowBarVisible = z;
        KeyguardSecBottomAreaView keyguardSecBottomAreaView = (KeyguardSecBottomAreaView) this.mView;
        keyguardSecBottomAreaView.isNowBarVisible = z;
        keyguardSecBottomAreaView.updateIndicationPosition();
        ((KeyguardEditModeControllerImpl) this.editModeController).isNowBarVisible = this.nowBarVisible;
        Handler handler = ((KeyguardSecBottomAreaView) this.mView).getHandler();
        KeyguardSecBottomAreaViewController$startDelayRunnable$1 keyguardSecBottomAreaViewController$startDelayRunnable$1 = this.startDelayRunnable;
        if (handler.hasCallbacks(keyguardSecBottomAreaViewController$startDelayRunnable$1)) {
            ((KeyguardSecBottomAreaView) this.mView).getHandler().removeCallbacks(keyguardSecBottomAreaViewController$startDelayRunnable$1);
        }
        if (!this.showShortcutAnim) {
            getLeftView().setNowBarVisibility(this.nowBarVisible, new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$setNowBarVisibility$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((Boolean) obj).getClass();
                    KeyguardSecBottomAreaViewController.access$updateFinalShortcut(KeyguardSecBottomAreaViewController.this);
                }
            }, new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$setNowBarVisibility$2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    KeyguardSecBottomAreaViewController.this.isShortcutAnimRunning = ((Boolean) obj).booleanValue();
                }
            }, false);
            getRightView().setNowBarVisibility(this.nowBarVisible, new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$setNowBarVisibility$3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((Boolean) obj).getClass();
                    KeyguardSecBottomAreaViewController.access$updateFinalShortcut(KeyguardSecBottomAreaViewController.this);
                }
            }, new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$setNowBarVisibility$4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    KeyguardSecBottomAreaViewController.this.isShortcutAnimRunning = ((Boolean) obj).booleanValue();
                }
            }, false);
        } else {
            if (this.isShortcutAnimRunning) {
                return;
            }
            showShortcutAnimation(20L, this.nowBarVisible);
        }
    }

    public final void setUserSetupComplete(boolean z) {
        this.isUserSetupComplete = z;
        KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 = ((KeyguardSecBottomAreaView) this.mView).updateRightAffordanceIcon;
        if (keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 == null) {
            keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 = null;
        }
        keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0.invoke();
        KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 = ((KeyguardSecBottomAreaView) this.mView).updateLeftAffordanceIcon;
        (keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 != null ? keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 : null).invoke();
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
            linearLayout.setVisibility(8);
            return;
        }
        boolean isIccBlockedPermanently = this.keyguardUpdateMonitor.isIccBlockedPermanently();
        boolean z = this.isUsimTextAreaShowing;
        if (isIccBlockedPermanently) {
            LinearLayout linearLayout2 = keyguardSecBottomAreaView.usimTextArea;
            linearLayout2.getClass();
            linearLayout2.setVisibility(z ? 0 : 8);
        } else if (this.isSecure) {
            LinearLayout linearLayout3 = keyguardSecBottomAreaView.usimTextArea;
            linearLayout3.getClass();
            linearLayout3.setVisibility(8);
        } else if (z) {
            LinearLayout linearLayout4 = keyguardSecBottomAreaView.usimTextArea;
            linearLayout4.getClass();
            linearLayout4.setVisibility(0);
        } else {
            LinearLayout linearLayout5 = keyguardSecBottomAreaView.usimTextArea;
            linearLayout5.getClass();
            linearLayout5.setVisibility(8);
        }
    }

    public final boolean shouldDisableShortcut() {
        return this.mPermDisableState || this.mSavingMode || !this.shortcutManager.isShortcutVisibleForMDM || this.keyguardUpdateMonitor.isKidsModeRunning() || !isUserUnlocked$2() || ((KeyguardSecBottomAreaView) this.mView).isPluginLockOverlayView || this.isDozing || DevicePolicyManagerExtKt.areKeyguardShortcutsDisabled$default(this.devicePolicyManager, this.selectedUserInteractor.getSelectedUserId());
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
            getLeftView().setNowBarVisibility(this.nowBarVisible, new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$showShortcutAnimation$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((Boolean) obj).getClass();
                    KeyguardSecBottomAreaViewController.access$updateFinalShortcut(KeyguardSecBottomAreaViewController.this);
                }
            }, new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$showShortcutAnimation$2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    KeyguardSecBottomAreaViewController.this.isShortcutAnimRunning = ((Boolean) obj).booleanValue();
                }
            }, true);
            getRightView().setNowBarVisibility(this.nowBarVisible, new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$showShortcutAnimation$3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((Boolean) obj).getClass();
                    KeyguardSecBottomAreaViewController.access$updateFinalShortcut(KeyguardSecBottomAreaViewController.this);
                }
            }, new Consumer() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$showShortcutAnimation$4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    KeyguardSecBottomAreaViewController.this.isShortcutAnimRunning = ((Boolean) obj).booleanValue();
                }
            }, true);
        } else {
            ((KeyguardSecBottomAreaView) this.mView).getHandler().postDelayed(keyguardSecBottomAreaViewController$startDelayRunnable$1, j);
        }
        this.isShortcutAnimRunning = true;
    }

    public final void showShortcutsIfPossible() {
        if (shouldDisableShortcut()) {
            return;
        }
        KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 = ((KeyguardSecBottomAreaView) this.mView).updateRightAffordanceIcon;
        if (keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 == null) {
            keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 = null;
        }
        keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0.invoke();
        KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 = ((KeyguardSecBottomAreaView) this.mView).updateLeftAffordanceIcon;
        (keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 != null ? keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 : null).invoke();
    }

    public final void updateAffordanceIcon(KeyguardSecAffordanceView keyguardSecAffordanceView) {
        boolean z = keyguardSecAffordanceView.mRight;
        KeyguardSecBottomAreaView keyguardSecBottomAreaView = (KeyguardSecBottomAreaView) this.mView;
        if (z) {
            KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 = keyguardSecBottomAreaView.updateRightAffordanceIcon;
            (keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 != null ? keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 : null).invoke();
        } else {
            KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 = keyguardSecBottomAreaView.updateLeftAffordanceIcon;
            (keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 != null ? keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 : null).invoke();
        }
    }

    @Override // com.android.systemui.pluginlock.PluginLockBottomAreaCallback
    public final void updateBottomView() {
        Log.d("KeyguardSecBottomAreaViewController", "updateBottomView");
        ((KeyguardSecBottomAreaView) this.mView).updateIndicationPosition();
    }

    public final void updateCustomShortcutIcon(KeyguardSecAffordanceView keyguardSecAffordanceView, int i, boolean z) {
        boolean z2;
        boolean z3 = !shouldDisableShortcut() && z;
        if (keyguardSecAffordanceView == null || ((KeyguardFastBioUnlockController) Dependency.sDependency.getDependencyInner(KeyguardFastBioUnlockController.class)).isFastWakeAndUnlockMode()) {
            return;
        }
        KeyguardShortcutManager keyguardShortcutManager = this.shortcutManager;
        if (keyguardShortcutManager.hasShortcut(i) && KeyguardShortcutManager.isSamsungCameraPackage(keyguardShortcutManager.shortcutsData[i].componentName) && !shouldDisableShortcut()) {
            dagger.Lazy lazy = this.centralSurfacesLazy;
            if (lazy.get() != null) {
                CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) ((CentralSurfaces) lazy.get());
                DevicePolicyManager devicePolicyManager = centralSurfacesImpl.mDevicePolicyManager;
                NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) centralSurfacesImpl.mLockscreenUserManager;
                if (devicePolicyManager.getCameraDisabled(null, notificationLockscreenUserManagerImpl.mCurrentUserId) || (((KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController).mShowing && centralSurfacesImpl.mStatusBarKeyguardViewManager.isSecure() && (centralSurfacesImpl.mDevicePolicyManager.getKeyguardDisabledFeatures(null, notificationLockscreenUserManagerImpl.mCurrentUserId) & 2) != 0)) {
                    z2 = true;
                    boolean z4 = z2 && keyguardShortcutManager.isLockTaskPermitted("com.sec.android.app.camera") && this.isUserSetupComplete;
                    KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("updateCameraVisibility isCameraDisabled:", " visible:", "KeyguardSecBottomAreaViewController", z2, z4);
                    z3 = !z3 && z4;
                }
            }
            z2 = false;
            if (z2) {
            }
            KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("updateCameraVisibility isCameraDisabled:", " visible:", "KeyguardSecBottomAreaViewController", z2, z4);
            z3 = !z3 && z4;
        }
        if (!z3) {
            keyguardSecAffordanceView.setVisibility(8);
            return;
        }
        keyguardSecAffordanceView.setVisibility(this.isUserSetupComplete ? 0 : 8);
        keyguardSecAffordanceView.setImageDrawable(keyguardShortcutManager.getShortcutDrawable(i));
        keyguardSecAffordanceView.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$updateCustomShortcutIcon$1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.setClassName("android.widget.Button");
            }
        });
        keyguardSecAffordanceView.setContentDescription(keyguardShortcutManager.getShortcutContentDescription(i));
    }

    public final void updateIndicationPosition() {
        Log.d("KeyguardSecBottomAreaViewController", "updateIndicationPosition");
        ((KeyguardSecBottomAreaView) this.mView).updateIndicationPosition();
    }

    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        KeyguardShortcutManager keyguardShortcutManager = this.shortcutManager;
        keyguardShortcutManager.getClass();
        if (semWallpaperColors == null || !LsRune.LOCKUI_SHORTCUT_BLUR_BG) {
            Log.d("KeyguardShortcutManager", "updateWallpaperProperties: null");
        } else {
            try {
                int HSVToColor = Color.HSVToColor(semWallpaperColors.get(256L).getHSV());
                boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
                double d = ((HSVToColor >>> 16) & 255) / 255.0d;
                double d2 = ((HSVToColor >>> 8) & 255) / 255.0d;
                double d3 = (HSVToColor & 255) / 255.0d;
                double pow = d < 0.04045d ? d / 12.92d : Math.pow((d / 1.055d) + 0.05213270142180095d, 2.4d);
                double pow2 = ((d3 < 0.04045d ? d3 / 12.92d : Math.pow((d3 / 1.055d) + 0.05213270142180095d, 2.4d)) * 0.0722d) + ((d2 < 0.04045d ? d2 / 12.92d : Math.pow((d2 / 1.055d) + 0.05213270142180095d, 2.4d)) * 0.7152d) + (pow * 0.2126d);
                keyguardShortcutManager.wallpaperBrightness = (int) Math.max(0.0d, ((pow2 > 0.008856d ? Math.cbrt(pow2) : 0.13793103448275862d + (pow2 * 7.787068965517241d)) * 116.0d) - 16.0d);
            } catch (Exception e) {
                keyguardShortcutManager.wallpaperBrightness = -1;
                Log.e("KeyguardShortcutManager", "updateWallpaperProperties: " + e.getMessage());
            }
            ListPopupWindow$$ExternalSyntheticOutline0.m(keyguardShortcutManager.wallpaperBrightness, "updateWallpaperProperties wallpaperBrightness   : ", "KeyguardShortcutManager");
        }
        getLeftView().updateStyle();
        getRightView().updateStyle();
    }
}
