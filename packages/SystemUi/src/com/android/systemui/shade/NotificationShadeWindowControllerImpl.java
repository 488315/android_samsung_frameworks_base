package com.android.systemui.shade;

import android.R;
import android.app.IActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Trace;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.Rune;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.DumpsysTableLogger;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.VisibilityController;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.StatusBarWindowCallback;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.LogUtil;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationShadeWindowControllerImpl implements NotificationShadeWindowController, Dumpable, ConfigurationController.ConfigurationListener {
    public final IActivityManager mActivityManager;
    public final AuthController mAuthController;
    public final SysuiColorExtractor mColorExtractor;
    public final Context mContext;
    public int mDeferWindowLayoutParams;
    public final DozeParameters mDozeParameters;
    public StatusBarTouchableRegionManager$$ExternalSyntheticLambda0 mForcePluginOpenListener;
    public boolean mHasTopUi;
    public boolean mHasTopUiChanged;
    public final SecNotificationShadeWindowControllerHelperImpl mHelper;
    public final IndicatorCutoutUtil mIndicatorCutoutUtil;
    public final KeyguardBypassController mKeyguardBypassController;
    public final float mKeyguardMaxRefreshRate;
    public final float mKeyguardPreferredRefreshRate;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardViewMediator mKeyguardViewMediator;
    public boolean mLastKeyguardRotationAllowed;
    public CentralSurfacesImpl$$ExternalSyntheticLambda0 mListener;
    public final ShadeWindowLogger mLogger;
    public WindowManager.LayoutParams mLp;
    public final WindowManager.LayoutParams mLpChanged;
    public ViewGroup mNotificationShadeView;
    public float mScreenBrightnessDoze;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public Consumer mScrimsVisibilityListener;
    public final C24441 mStateListener;
    public final WindowManager mWindowManager;
    public final NotificationShadeWindowState mCurrentState = new NotificationShadeWindowState();
    public final ArrayList mCallbacks = new ArrayList();
    public final NotificationShadeWindowState.Buffer mStateBuffer = new NotificationShadeWindowState.Buffer(100);

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.plugins.statusbar.StatusBarStateController$StateListener, com.android.systemui.shade.NotificationShadeWindowControllerImpl$1] */
    public NotificationShadeWindowControllerImpl(SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl, Context context, WindowManager windowManager, IActivityManager iActivityManager, DozeParameters dozeParameters, StatusBarStateController statusBarStateController, ConfigurationController configurationController, KeyguardViewMediator keyguardViewMediator, KeyguardBypassController keyguardBypassController, SysuiColorExtractor sysuiColorExtractor, DumpManager dumpManager, KeyguardStateController keyguardStateController, ScreenOffAnimationController screenOffAnimationController, AuthController authController, ShadeExpansionStateManager shadeExpansionStateManager, ShadeWindowLogger shadeWindowLogger, IndicatorCutoutUtil indicatorCutoutUtil) {
        ?? r5 = new StatusBarStateController.StateListener() { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl.1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDozingChanged(boolean z) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = NotificationShadeWindowControllerImpl.this;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState.dozing = z;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onDreamingChanged(boolean z) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = NotificationShadeWindowControllerImpl.this;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState.dreaming = z;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = NotificationShadeWindowControllerImpl.this;
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState.statusBarState = i;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
            }
        };
        this.mStateListener = r5;
        final int i = 0;
        final int i2 = 1;
        final int i3 = 2;
        secNotificationShadeWindowControllerHelperImpl.provider = new SecNotificationShadeWindowControllerHelperImpl.Provider(new Supplier(this) { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda9
            public final /* synthetic */ NotificationShadeWindowControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i) {
                    case 0:
                        return this.f$0.mCurrentState;
                    case 1:
                        return this.f$0.mLpChanged;
                    default:
                        return this.f$0.mLp;
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda9
            public final /* synthetic */ NotificationShadeWindowControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i2) {
                    case 0:
                        return this.f$0.mCurrentState;
                    case 1:
                        return this.f$0.mLpChanged;
                    default:
                        return this.f$0.mLp;
                }
            }
        }, new Supplier(this) { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda9
            public final /* synthetic */ NotificationShadeWindowControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Supplier
            public final Object get() {
                switch (i3) {
                    case 0:
                        return this.f$0.mCurrentState;
                    case 1:
                        return this.f$0.mLpChanged;
                    default:
                        return this.f$0.mLp;
                }
            }
        }, new Predicate() { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = NotificationShadeWindowControllerImpl.this;
                return notificationShadeWindowControllerImpl.isExpanded(notificationShadeWindowControllerImpl.mCurrentState, ((Boolean) obj).booleanValue());
            }
        }, new BooleanSupplier() { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda11
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                NotificationShadeWindowControllerImpl.this.getClass();
                return Build.IS_DEBUGGABLE;
            }
        }, new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda12(this, i));
        this.mHelper = secNotificationShadeWindowControllerHelperImpl;
        this.mContext = context;
        this.mWindowManager = windowManager;
        this.mActivityManager = iActivityManager;
        this.mDozeParameters = dozeParameters;
        this.mKeyguardStateController = keyguardStateController;
        this.mLogger = shadeWindowLogger;
        this.mScreenBrightnessDoze = dozeParameters.mResources.getInteger(R.integer.config_screenBrightnessSettingMinimum) / 255.0f;
        this.mLpChanged = new WindowManager.LayoutParams();
        this.mKeyguardViewMediator = keyguardViewMediator;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mColorExtractor = sysuiColorExtractor;
        this.mScreenOffAnimationController = screenOffAnimationController;
        String name = NotificationShadeWindowControllerImpl.class.getName();
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, name, this);
        this.mAuthController = authController;
        this.mLastKeyguardRotationAllowed = ((KeyguardStateControllerImpl) keyguardStateController).isKeyguardScreenRotationAllowed();
        context.getResources().getInteger(com.android.systemui.R.integer.config_lockScreenDisplayTimeout);
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) ((SysuiStatusBarStateController) statusBarStateController);
        synchronized (statusBarStateControllerImpl.mListeners) {
            statusBarStateControllerImpl.addListenerInternalLocked(r5, 1);
        }
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        shadeExpansionStateManager.addQsExpansionListener(new ShadeQsExpansionListener() { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda1
            @Override // com.android.systemui.shade.ShadeQsExpansionListener
            public final void onQsExpansionChanged(boolean z) {
                Boolean valueOf = Boolean.valueOf(z);
                NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = NotificationShadeWindowControllerImpl.this;
                notificationShadeWindowControllerImpl.getClass();
                boolean booleanValue = valueOf.booleanValue();
                NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
                notificationShadeWindowState.qsExpanded = booleanValue;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
            }
        });
        shadeExpansionStateManager.addFullExpansionListener(new ShadeFullExpansionListener() { // from class: com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda2
            @Override // com.android.systemui.shade.ShadeFullExpansionListener
            public final void onShadeExpansionFullyChanged(boolean z) {
                NotificationShadeWindowControllerImpl.this.onShadeExpansionFullyChanged(Boolean.valueOf(z));
            }
        });
        float f = -1.0f;
        if (context.getResources().getInteger(com.android.systemui.R.integer.config_keyguardRefreshRate) > -1.0f) {
            Display.Mode[] supportedModes = context.getDisplay().getSupportedModes();
            int length = supportedModes.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    break;
                }
                Display.Mode mode = supportedModes[i4];
                if (Math.abs(mode.getRefreshRate() - r2) <= 0.1d) {
                    f = mode.getRefreshRate();
                    break;
                }
                i4++;
            }
        }
        this.mKeyguardPreferredRefreshRate = f;
        this.mKeyguardMaxRefreshRate = context.getResources().getInteger(com.android.systemui.R.integer.config_keyguardMaxRefreshRate);
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = this.mHelper;
        Objects.requireNonNull(secNotificationShadeWindowControllerHelperImpl2);
        Rune.runIf((Runnable) new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda3(secNotificationShadeWindowControllerHelperImpl2, i), true);
        this.mIndicatorCutoutUtil = indicatorCutoutUtil;
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x02e0, code lost:
    
        r8 = 8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x02e3, code lost:
    
        r8 = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x02e7, code lost:
    
        r8 = r2.getLayoutParamsChanged();
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x02f1, code lost:
    
        if (r32.isKeyguardShowingAndNotOccluded() == false) goto L210;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x02f3, code lost:
    
        r9 = r32.statusBarState;
        r10 = r2.keyguardUpdateMonitor;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x02f7, code lost:
    
        if (r9 == 1) goto L182;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x02fd, code lost:
    
        if (r10.isFullscreenBouncer() == false) goto L210;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x0301, code lost:
    
        if (r32.qsExpanded != false) goto L210;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x0303, code lost:
    
        r9 = r2.pluginLockMediator;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x0305, code lost:
    
        if (r9 == null) goto L187;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0307, code lost:
    
        r9 = ((com.android.systemui.pluginlock.PluginLockMediatorImpl) r9).isDynamicLockEnabled();
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x030f, code lost:
    
        if (r9 == false) goto L192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0313, code lost:
    
        if (r32.userScreenTimeOut == false) goto L192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0315, code lost:
    
        r8.userActivityTimeout = -1;
        r8.screenDimDuration = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0360, code lost:
    
        if (r32.isKeyguardShowingAndNotOccluded() == false) goto L220;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x0364, code lost:
    
        if (r32.statusBarState != 1) goto L220;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0368, code lost:
    
        if (r32.qsExpanded != false) goto L220;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x036c, code lost:
    
        if (r32.forceUserActivity != false) goto L220;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x036e, code lost:
    
        r6.inputFeatures |= 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x037a, code lost:
    
        r2 = !r32.isKeyguardShowingAndNotOccluded();
        r7 = r31.mNotificationShadeView;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0381, code lost:
    
        if (r7 == null) goto L226;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0387, code lost:
    
        if (r7.getFitsSystemWindows() == r2) goto L226;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x0389, code lost:
    
        r31.mNotificationShadeView.setFitsSystemWindows(r2);
        r31.mNotificationShadeView.requestApplyInsets();
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0395, code lost:
    
        if (r32.headsUpNotificationShowing == false) goto L229;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x0397, code lost:
    
        r6.flags |= 32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x03a6, code lost:
    
        if (r32.forceDozeBrightness == false) goto L233;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x03a8, code lost:
    
        r6.screenBrightness = r31.mScreenBrightnessDoze;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x03b5, code lost:
    
        if (r3.isEmpty() == false) goto L240;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x03bb, code lost:
    
        if (isExpanded(r32) == false) goto L239;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x03be, code lost:
    
        r2 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x03c1, code lost:
    
        r31.mHasTopUiChanged = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x03c5, code lost:
    
        if (r32.windowNotTouchable == false) goto L244;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x03c7, code lost:
    
        r6.flags |= 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x03d8, code lost:
    
        if (isExpanded(r32) != false) goto L248;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x03da, code lost:
    
        r6.privateFlags |= 16777216;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x03ea, code lost:
    
        com.android.systemui.Rune.runIf(new com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda4(r31, r32, r4), true);
        applyWindowLayoutParams();
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x03f9, code lost:
    
        if (r31.mHasTopUi == r31.mHasTopUiChanged) goto L252;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x03fb, code lost:
    
        com.android.systemui.DejankUtils.whitelistIpcs(new com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda3(r31, r5));
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x0403, code lost:
    
        notifyStateChangedCallbacks();
        com.android.systemui.Rune.runIf(new com.android.systemui.shade.NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda4(r31, r32, r5), true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x040e, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x03e2, code lost:
    
        r6.privateFlags &= -16777217;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x03ce, code lost:
    
        r6.flags &= -17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x03c0, code lost:
    
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x03ad, code lost:
    
        r6.screenBrightness = -1.0f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x039e, code lost:
    
        r6.flags &= -33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x0374, code lost:
    
        r6.inputFeatures &= -3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x031a, code lost:
    
        if (r7 != false) goto L198;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x031e, code lost:
    
        if (r32.bouncerShowing == false) goto L198;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0320, code lost:
    
        r9 = r32.keyguardUserActivityTimeout;
        r12 = 10000;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0326, code lost:
    
        if (r9 >= 10000) goto L209;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0328, code lost:
    
        r9 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x0351, code lost:
    
        r8.userActivityTimeout = r9;
        r8.screenDimDuration = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x0334, code lost:
    
        if (android.view.accessibility.AccessibilityManager.getInstance(r2.context).isTouchExplorationEnabled() == false) goto L203;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0336, code lost:
    
        r12 = r32.keyguardUserActivityTimeout;
        r14 = com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl.AWAKE_INTERVAL_DEFAULT_MS_WITH_ACCESSIBILITY;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x033c, code lost:
    
        if (r12 >= r14) goto L203;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x033e, code lost:
    
        r9 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x0344, code lost:
    
        if (r10.isFaceOptionEnabled() == false) goto L208;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x0346, code lost:
    
        r9 = r32.keyguardUserActivityTimeout;
        r12 = com.android.systemui.shade.SecNotificationShadeWindowControllerHelperImpl.AWAKE_INTERVAL_DEFAULT_MS_WITH_FACE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x034c, code lost:
    
        if (r9 >= r12) goto L208;
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x034f, code lost:
    
        r9 = r32.keyguardUserActivityTimeout;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x030e, code lost:
    
        r9 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x0358, code lost:
    
        r8.userActivityTimeout = -1;
        r8.screenDimDuration = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x0256, code lost:
    
        r10 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x0246, code lost:
    
        r10 = r32.dozing;
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x025a, code lost:
    
        if (r2.isKeyguardScreenRotation == false) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:184:0x025e, code lost:
    
        if (r32.screenOrientationNoSensor != false) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x0260, code lost:
    
        r10 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x0263, code lost:
    
        if (r10 == false) goto L154;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x0269, code lost:
    
        if (com.android.systemui.util.DeviceType.isTablet() != false) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x026b, code lost:
    
        r10 = r2.rotation;
        r12 = r2.displayLifecycle;
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x0273, code lost:
    
        if (r10 == r12.getRotation()) goto L150;
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x0275, code lost:
    
        r10 = r12.getRotation();
        r2.rotation = r10;
        androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0.m10m("adjustScreenOrientation: rotation=", r10, "NotificationShadeWindowController");
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x0284, code lost:
    
        if (r2.rotation != 2) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x0287, code lost:
    
        r10 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x0262, code lost:
    
        r10 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x01bf, code lost:
    
        if (com.android.systemui.statusbar.NotificationRemoteInputManager.ENABLE_REMOTE_INPUT == false) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x01c3, code lost:
    
        if (r32.remoteInputActive != false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x01cb, code lost:
    
        if (r31.mScreenOffAnimationController.shouldIgnoreKeyguardTouches() == false) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x01d9, code lost:
    
        if (r32.isKeyguardShowingAndNotOccluded() != false) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x01db, code lost:
    
        if (r7 == false) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x01de, code lost:
    
        r6.flags = (r6.flags | 8) & (-131073);
     */
    /* JADX WARN: Code restructure failed: missing block: B:206:0x01e6, code lost:
    
        r7 = r6.flags & (-9);
        r6.flags = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x01f0, code lost:
    
        if (com.android.systemui.LsRune.SECURITY_BOUNCER_WINDOW == false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x01f2, code lost:
    
        r6.flags = r7 | 131072;
     */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x01f8, code lost:
    
        if (r32.keyguardNeedsInput == false) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x01fe, code lost:
    
        if (r32.isKeyguardShowingAndNotOccluded() == false) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x0200, code lost:
    
        r6.flags &= -131073;
     */
    /* JADX WARN: Code restructure failed: missing block: B:214:0x0206, code lost:
    
        r6.flags |= 131072;
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x01ad, code lost:
    
        r7 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x0196, code lost:
    
        r7.flags |= 8192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x018a, code lost:
    
        if (r32.securedWindow != false) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0186, code lost:
    
        if (r8.isDebuggableSupplier.getAsBoolean() != false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x018e, code lost:
    
        if (com.android.systemui.LsRune.KEYGUARD_EM_TOKEN_CAPTURE_WINDOW == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0194, code lost:
    
        if (r2.engineerModeManager.isCaptureEnabled != false) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x019d, code lost:
    
        r7.flags &= -8193;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x01a5, code lost:
    
        if (r32.notificationShadeFocusable == false) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x01a9, code lost:
    
        if (r32.panelExpanded == false) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x01ab, code lost:
    
        r7 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x01b3, code lost:
    
        if (r32.bouncerShowing == false) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x01b7, code lost:
    
        if (r32.keyguardOccluded != false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x01bb, code lost:
    
        if (r32.keyguardNeedsInput != false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x01cd, code lost:
    
        r6.flags = (r6.flags & (-9)) & (-131073);
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x020b, code lost:
    
        r7 = com.android.systemui.LsRune.SECURITY_BOUNCER_WINDOW;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x020d, code lost:
    
        if (r7 == false) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0212, code lost:
    
        if (r32.bouncerShowing != false) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0216, code lost:
    
        if (com.android.systemui.statusbar.NotificationRemoteInputManager.ENABLE_REMOTE_INPUT == false) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x021a, code lost:
    
        if (r32.remoteInputActive == false) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x021d, code lost:
    
        r6.privateFlags &= -8388609;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0226, code lost:
    
        r6.privateFlags |= com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x022d, code lost:
    
        r8 = r2.getLayoutParamsChanged();
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0234, code lost:
    
        if (r32.bouncerShowing != false) goto L138;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x023a, code lost:
    
        if (r32.isKeyguardShowingAndNotOccluded() != false) goto L138;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x023e, code lost:
    
        if (com.android.systemui.LsRune.COVER_SUPPORTED == false) goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0242, code lost:
    
        if (r32.isCoverClosed == false) goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0244, code lost:
    
        r10 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0248, code lost:
    
        if (r10 == false) goto L131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x024b, code lost:
    
        if (r7 != false) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x024f, code lost:
    
        if (r2.isKeyguardScreenRotation != false) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0253, code lost:
    
        if (r32.bouncerShowing == false) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0289, code lost:
    
        r10 = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x028a, code lost:
    
        r8.screenOrientation = r10;
        r8 = isExpanded(r32);
        r10 = r31.mLogger;
        r10.getClass();
        r12 = com.android.systemui.log.LogLevel.DEBUG;
        r13 = com.android.systemui.shade.ShadeWindowLogger$logApplyVisibility$2.INSTANCE;
        r15 = r10.buffer;
        r9 = r15.obtain("systemui.shadewindow", r12, r13, null);
        r9.setBool1(r8);
        r15.commit(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x02aa, code lost:
    
        if (r32.forcePluginOpen == false) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x02ac, code lost:
    
        r9 = r31.mListener;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x02ae, code lost:
    
        if (r9 == null) goto L160;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x02b0, code lost:
    
        com.android.systemui.statusbar.phone.CentralSurfacesImpl.C30224.this.mOverlays.forEach(new com.android.systemui.statusbar.phone.CentralSurfacesImpl$4$Callback$$ExternalSyntheticLambda0(r8));
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x02c0, code lost:
    
        r10.mo153d("Visibility forced to be true");
        r8 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x02c6, code lost:
    
        r9 = r2.visibilityMonitor;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x02ca, code lost:
    
        if (r9.cancelExecToken == null) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x02ce, code lost:
    
        if (r8 == r9.needsExpand) goto L166;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x02d1, code lost:
    
        r9.cancelExecToken(true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x02d4, code lost:
    
        r9 = r2.notificationShadeView;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x02d6, code lost:
    
        if (r9 == null) goto L176;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x02d8, code lost:
    
        if (r8 == false) goto L171;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x02da, code lost:
    
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x02e4, code lost:
    
        r9.setVisibility(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x02de, code lost:
    
        if (r32.forceInvisible == false) goto L174;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void apply(final NotificationShadeWindowState notificationShadeWindowState) {
        boolean z = notificationShadeWindowState.keyguardShowing;
        boolean z2 = notificationShadeWindowState.keyguardOccluded;
        boolean z3 = notificationShadeWindowState.keyguardNeedsInput;
        boolean z4 = notificationShadeWindowState.panelVisible;
        boolean z5 = notificationShadeWindowState.panelExpanded;
        boolean z6 = notificationShadeWindowState.notificationShadeFocusable;
        boolean z7 = notificationShadeWindowState.bouncerShowing;
        boolean z8 = notificationShadeWindowState.keyguardFadingAway;
        boolean z9 = notificationShadeWindowState.keyguardGoingAway;
        boolean z10 = notificationShadeWindowState.qsExpanded;
        boolean z11 = notificationShadeWindowState.headsUpNotificationShowing;
        boolean z12 = notificationShadeWindowState.lightRevealScrimOpaque;
        boolean z13 = notificationShadeWindowState.forceWindowCollapsed;
        boolean z14 = notificationShadeWindowState.forceDozeBrightness;
        boolean z15 = notificationShadeWindowState.forceUserActivity;
        boolean z16 = notificationShadeWindowState.launchingActivityFromNotification;
        boolean z17 = notificationShadeWindowState.mediaBackdropShowing;
        boolean z18 = notificationShadeWindowState.wallpaperSupportsAmbientMode;
        boolean z19 = notificationShadeWindowState.windowNotTouchable;
        int i = notificationShadeWindowState.statusBarState;
        boolean z20 = notificationShadeWindowState.remoteInputActive;
        boolean z21 = notificationShadeWindowState.forcePluginOpen;
        boolean z22 = notificationShadeWindowState.dozing;
        int i2 = notificationShadeWindowState.scrimsVisibility;
        int i3 = notificationShadeWindowState.backgroundBlurRadius;
        long j = notificationShadeWindowState.keyguardUserActivityTimeout;
        NotificationShadeWindowState notificationShadeWindowState2 = (NotificationShadeWindowState) this.mStateBuffer.buffer.advance();
        notificationShadeWindowState2.keyguardShowing = z;
        notificationShadeWindowState2.keyguardOccluded = z2;
        notificationShadeWindowState2.keyguardNeedsInput = z3;
        notificationShadeWindowState2.panelVisible = z4;
        notificationShadeWindowState2.panelExpanded = z5;
        notificationShadeWindowState2.notificationShadeFocusable = z6;
        notificationShadeWindowState2.bouncerShowing = z7;
        notificationShadeWindowState2.keyguardFadingAway = z8;
        notificationShadeWindowState2.keyguardGoingAway = z9;
        notificationShadeWindowState2.qsExpanded = z10;
        notificationShadeWindowState2.headsUpNotificationShowing = z11;
        notificationShadeWindowState2.lightRevealScrimOpaque = z12;
        notificationShadeWindowState2.forceWindowCollapsed = z13;
        notificationShadeWindowState2.forceDozeBrightness = z14;
        notificationShadeWindowState2.forceUserActivity = z15;
        notificationShadeWindowState2.launchingActivityFromNotification = z16;
        notificationShadeWindowState2.mediaBackdropShowing = z17;
        notificationShadeWindowState2.wallpaperSupportsAmbientMode = z18;
        notificationShadeWindowState2.windowNotTouchable = z19;
        Set set = notificationShadeWindowState2.componentsForcingTopUi;
        set.clear();
        Set set2 = notificationShadeWindowState.componentsForcingTopUi;
        set.addAll(set2);
        Set set3 = notificationShadeWindowState2.forceOpenTokens;
        set3.clear();
        set3.addAll(notificationShadeWindowState.forceOpenTokens);
        notificationShadeWindowState2.statusBarState = i;
        notificationShadeWindowState2.remoteInputActive = z20;
        notificationShadeWindowState2.forcePluginOpen = z21;
        notificationShadeWindowState2.dozing = z22;
        notificationShadeWindowState2.scrimsVisibility = i2;
        notificationShadeWindowState2.backgroundBlurRadius = i3;
        notificationShadeWindowState2.keyguardUserActivityTimeout = j;
        final int i4 = 0;
        final int i5 = 1;
        boolean z23 = notificationShadeWindowState.keyguardShowing || (notificationShadeWindowState.dozing && this.mDozeParameters.getAlwaysOn());
        WindowManager.LayoutParams layoutParams = this.mLpChanged;
        if (!z23 || notificationShadeWindowState.mediaBackdropShowing || notificationShadeWindowState.lightRevealScrimOpaque) {
            layoutParams.flags &= -1048577;
        } else {
            layoutParams.flags |= QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING;
        }
        if (notificationShadeWindowState.dozing) {
            layoutParams.privateFlags |= 524288;
        } else {
            layoutParams.privateFlags &= -524289;
        }
        float f = this.mKeyguardPreferredRefreshRate;
        if (f > 0.0f) {
            if (((notificationShadeWindowState.statusBarState != 1 || notificationShadeWindowState.keyguardFadingAway || notificationShadeWindowState.keyguardGoingAway) ? false : true) && this.mAuthController.isUdfpsEnrolled(KeyguardUpdateMonitor.getCurrentUser())) {
                layoutParams.preferredMaxDisplayRefreshRate = f;
                layoutParams.preferredMinDisplayRefreshRate = f;
            } else {
                layoutParams.preferredMaxDisplayRefreshRate = 0.0f;
                layoutParams.preferredMinDisplayRefreshRate = 0.0f;
            }
            Trace.setCounter("display_set_preferred_refresh_rate", (long) layoutParams.preferredMaxDisplayRefreshRate);
        } else {
            float f2 = this.mKeyguardMaxRefreshRate;
            if (f2 > 0.0f) {
                boolean z24 = this.mKeyguardBypassController.getBypassEnabled() && notificationShadeWindowState.statusBarState == 1 && !notificationShadeWindowState.keyguardFadingAway && !notificationShadeWindowState.keyguardGoingAway;
                if (notificationShadeWindowState.dozing || z24) {
                    layoutParams.preferredMaxDisplayRefreshRate = f2;
                } else {
                    layoutParams.preferredMaxDisplayRefreshRate = 0.0f;
                }
                Trace.setCounter("display_max_refresh_rate", (long) layoutParams.preferredMaxDisplayRefreshRate);
            }
        }
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = this.mHelper;
        WindowManager.LayoutParams layoutParamsChanged = secNotificationShadeWindowControllerHelperImpl.getLayoutParamsChanged();
        if (notificationShadeWindowState.bouncerShowing) {
            SecNotificationShadeWindowControllerHelperImpl.Provider provider = secNotificationShadeWindowControllerHelperImpl.provider;
            if (provider == null) {
                provider = null;
            }
        }
    }

    public final void applyWindowLayoutParams() {
        WindowManager.LayoutParams layoutParams;
        if (this.mDeferWindowLayoutParams != 0 || (layoutParams = this.mLp) == null || layoutParams.copyFrom(this.mLpChanged) == 0) {
            return;
        }
        WindowManager.LayoutParams layoutParams2 = this.mLp;
        ShadeWindowLogger shadeWindowLogger = this.mLogger;
        shadeWindowLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        ShadeWindowLogger$logApplyingWindowLayoutParams$2 shadeWindowLogger$logApplyingWindowLayoutParams$2 = new Function1() { // from class: com.android.systemui.shade.ShadeWindowLogger$logApplyingWindowLayoutParams$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyAttributes$$ExternalSyntheticOutline0.m21m("Applying new window layout params: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = shadeWindowLogger.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shadewindow", logLevel, shadeWindowLogger$logApplyingWindowLayoutParams$2, null);
        obtain.setStr1(layoutParams2.toString());
        logBuffer.commit(obtain);
        Trace.beginSection("updateViewLayout");
        this.mWindowManager.updateViewLayout(this.mNotificationShadeView, this.mLp);
        Trace.endSection();
    }

    public final void batchApplyWindowLayoutParams(Runnable runnable) {
        this.mDeferWindowLayoutParams++;
        runnable.run();
        this.mDeferWindowLayoutParams--;
        applyWindowLayoutParams();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m81m = LockIconView$$ExternalSyntheticOutline0.m81m(LockIconView$$ExternalSyntheticOutline0.m81m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "NotificationShadeWindowController:", "  mKeyguardMaxRefreshRate="), this.mKeyguardMaxRefreshRate, printWriter, "  mKeyguardPreferredRefreshRate="), this.mKeyguardPreferredRefreshRate, printWriter, "  mDeferWindowLayoutParams=");
        m81m.append(this.mDeferWindowLayoutParams);
        printWriter.println(m81m.toString());
        Rune.runIf((Runnable) new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda6(this, printWriter, 0), true);
        printWriter.println(this.mCurrentState);
        ViewGroup viewGroup = this.mNotificationShadeView;
        if (viewGroup != null && viewGroup.getViewRootImpl() != null) {
            this.mNotificationShadeView.getViewRootImpl().dump("  ", printWriter);
        }
        new DumpsysTableLogger("NotificationShadeWindowController", NotificationShadeWindowState.TABLE_HEADERS, SequencesKt___SequencesKt.toList(new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(this.mStateBuffer.buffer), new Function1() { // from class: com.android.systemui.shade.NotificationShadeWindowState$Buffer$toList$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return (List) ((NotificationShadeWindowState) obj).asStringList$delegate.getValue();
            }
        }))).printTableData(printWriter);
    }

    public final boolean isExpanded(NotificationShadeWindowState notificationShadeWindowState) {
        this.mHelper.getClass();
        return isExpanded(notificationShadeWindowState, Log.isLoggable(SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG, 3));
    }

    public final void notifyStateChangedCallbacks() {
        for (StatusBarWindowCallback statusBarWindowCallback : (List) this.mCallbacks.stream().map(new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda7()).filter(new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda8()).collect(Collectors.toList())) {
            NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
            statusBarWindowCallback.onStateChanged(notificationShadeWindowState.keyguardShowing, notificationShadeWindowState.keyguardOccluded, notificationShadeWindowState.keyguardGoingAway, notificationShadeWindowState.bouncerShowing, notificationShadeWindowState.dozing, notificationShadeWindowState.panelExpanded, notificationShadeWindowState.dreaming);
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        boolean isKeyguardScreenRotationAllowed = ((KeyguardStateControllerImpl) this.mKeyguardStateController).isKeyguardScreenRotationAllowed();
        if (this.mLastKeyguardRotationAllowed != isKeyguardScreenRotationAllowed) {
            apply(this.mCurrentState);
            this.mLastKeyguardRotationAllowed = isKeyguardScreenRotationAllowed;
        }
    }

    @Override // com.android.systemui.statusbar.RemoteInputController.Callback
    public final void onRemoteInputActive(boolean z) {
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        notificationShadeWindowState.remoteInputActive = z;
        apply(notificationShadeWindowState);
    }

    public void onShadeExpansionFullyChanged(Boolean bool) {
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        if (notificationShadeWindowState.panelExpanded != bool.booleanValue()) {
            notificationShadeWindowState.panelExpanded = bool.booleanValue();
            apply(notificationShadeWindowState);
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onThemeChanged() {
        if (this.mNotificationShadeView == null) {
            return;
        }
        boolean supportsDarkText = this.mColorExtractor.mNeutralColorsLock.supportsDarkText();
        int systemUiVisibility = this.mNotificationShadeView.getSystemUiVisibility();
        this.mNotificationShadeView.setSystemUiVisibility(supportsDarkText ? systemUiVisibility | 16 | 8192 : systemUiVisibility & (-17) & (-8193));
    }

    public final void registerCallback(StatusBarWindowCallback statusBarWindowCallback) {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.mCallbacks;
            if (i >= arrayList.size()) {
                arrayList.add(new WeakReference(statusBarWindowCallback));
                return;
            } else if (((WeakReference) arrayList.get(i)).get() == statusBarWindowCallback) {
                return;
            } else {
                i++;
            }
        }
    }

    public final void setForcePluginOpen(Object obj, boolean z) {
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        if (z) {
            notificationShadeWindowState.forceOpenTokens.add(obj);
        } else {
            notificationShadeWindowState.forceOpenTokens.remove(obj);
        }
        boolean z2 = notificationShadeWindowState.forcePluginOpen;
        notificationShadeWindowState.forcePluginOpen = !notificationShadeWindowState.forceOpenTokens.isEmpty();
        if (z2 != notificationShadeWindowState.forcePluginOpen) {
            apply(notificationShadeWindowState);
            StatusBarTouchableRegionManager$$ExternalSyntheticLambda0 statusBarTouchableRegionManager$$ExternalSyntheticLambda0 = this.mForcePluginOpenListener;
            if (statusBarTouchableRegionManager$$ExternalSyntheticLambda0 != null) {
                boolean z3 = notificationShadeWindowState.forcePluginOpen;
                statusBarTouchableRegionManager$$ExternalSyntheticLambda0.f$0.updateTouchableRegion();
            }
        }
    }

    public final void setKeyguardFadingAway(boolean z) {
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl = this.mHelper;
        Objects.requireNonNull(secNotificationShadeWindowControllerHelperImpl);
        NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda0 notificationShadeWindowControllerImpl$$ExternalSyntheticLambda0 = new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda0(secNotificationShadeWindowControllerHelperImpl, 0);
        boolean z2 = Rune.SYSUI_MULTI_SIM;
        notificationShadeWindowControllerImpl$$ExternalSyntheticLambda0.accept(Boolean.valueOf(z));
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        notificationShadeWindowState.keyguardFadingAway = z;
        apply(notificationShadeWindowState);
    }

    public final void setNotificationShadeFocusable(boolean z) {
        ShadeWindowLogger shadeWindowLogger = this.mLogger;
        shadeWindowLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        ShadeWindowLogger$logShadeFocusable$2 shadeWindowLogger$logShadeFocusable$2 = new Function1() { // from class: com.android.systemui.shade.ShadeWindowLogger$logShadeFocusable$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AbstractC0866xb1ce8deb.m86m("Updating shade, should be focusable : ", ((LogMessage) obj).getBool1());
            }
        };
        LogBuffer logBuffer = shadeWindowLogger.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shadewindow", logLevel, shadeWindowLogger$logShadeFocusable$2, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        notificationShadeWindowState.notificationShadeFocusable = z;
        apply(notificationShadeWindowState);
    }

    public final void setPanelVisible(boolean z) {
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        if (notificationShadeWindowState.panelVisible == z && notificationShadeWindowState.notificationShadeFocusable == z) {
            return;
        }
        ShadeWindowLogger shadeWindowLogger = this.mLogger;
        shadeWindowLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        ShadeWindowLogger$logShadeVisibleAndFocusable$2 shadeWindowLogger$logShadeVisibleAndFocusable$2 = new Function1() { // from class: com.android.systemui.shade.ShadeWindowLogger$logShadeVisibleAndFocusable$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AbstractC0866xb1ce8deb.m86m("Updating shade, should be visible and focusable: ", ((LogMessage) obj).getBool1());
            }
        };
        LogBuffer logBuffer = shadeWindowLogger.buffer;
        LogMessage obtain = logBuffer.obtain("systemui.shadewindow", logLevel, shadeWindowLogger$logShadeVisibleAndFocusable$2, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
        notificationShadeWindowState.panelVisible = z;
        notificationShadeWindowState.notificationShadeFocusable = z;
        apply(notificationShadeWindowState);
    }

    public final void setRequestTopUi(String str, boolean z) {
        NotificationShadeWindowState notificationShadeWindowState = this.mCurrentState;
        if (z) {
            notificationShadeWindowState.componentsForcingTopUi.add(str);
        } else {
            notificationShadeWindowState.componentsForcingTopUi.remove(str);
        }
        apply(notificationShadeWindowState);
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x00a6, code lost:
    
        if ((r5 == 15) != false) goto L71;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isExpanded(NotificationShadeWindowState notificationShadeWindowState, boolean z) {
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl;
        boolean z2;
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl;
        Object[] objArr;
        if ((notificationShadeWindowState.forceWindowCollapsed || !(notificationShadeWindowState.isKeyguardShowingAndNotOccluded() || notificationShadeWindowState.panelVisible || notificationShadeWindowState.keyguardFadingAway || notificationShadeWindowState.bouncerShowing || notificationShadeWindowState.headsUpNotificationShowing || notificationShadeWindowState.scrimsVisibility != 0)) && notificationShadeWindowState.backgroundBlurRadius <= 0 && !notificationShadeWindowState.launchingActivityFromNotification) {
            notificationShadeWindowControllerImpl = this;
            z2 = false;
        } else {
            notificationShadeWindowControllerImpl = this;
            z2 = true;
        }
        SecNotificationShadeWindowControllerHelperImpl secNotificationShadeWindowControllerHelperImpl2 = notificationShadeWindowControllerImpl.mHelper;
        secNotificationShadeWindowControllerHelperImpl2.getClass();
        boolean z3 = notificationShadeWindowState.forceInvisible;
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = secNotificationShadeWindowControllerHelperImpl2.fastUnlockController;
        if (z2) {
            if (keyguardFastBioUnlockController.isEnabled()) {
                VisibilityController visibilityController = keyguardFastBioUnlockController.curVisibilityController;
                if ((visibilityController != null && visibilityController.needToBeInvisibleWindow()) != false) {
                    objArr = true;
                    if (objArr != false && z3) {
                        z2 = false;
                    }
                }
            }
            objArr = false;
            if (objArr != false) {
                z2 = false;
            }
        } else if (z3) {
            if (keyguardFastBioUnlockController.needsBlankScreen) {
                VisibilityController visibilityController2 = keyguardFastBioUnlockController.curVisibilityController;
                if (visibilityController2 != null) {
                    visibilityController2.resetForceInvisible(false);
                }
            } else {
                keyguardFastBioUnlockController.reset();
            }
        } else {
            keyguardFastBioUnlockController.getClass();
        }
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK && !keyguardFastBioUnlockController.isFastUnlockMode() && !keyguardFastBioUnlockController.isFastWakeAndUnlockMode()) {
            if (z2 && notificationShadeWindowState.forceInvisible) {
                z2 = false;
            } else if (!z2) {
                secNotificationShadeWindowControllerHelperImpl2.resetForceInvisible(false);
            }
        }
        if (LsRune.COVER_SUPPORTED && notificationShadeWindowState.isCoverClosed) {
            if (notificationShadeWindowState.coverAppShowing && !z2) {
                int i = notificationShadeWindowState.coverType;
                Point point = DeviceState.sDisplaySize;
            }
            z2 = notificationShadeWindowState.dozing || !DeviceState.isCoverUIType(notificationShadeWindowState.coverType);
        }
        if (z || secNotificationShadeWindowControllerHelperImpl2.isLastExpanded != z2) {
            int i2 = !notificationShadeWindowState.forceWindowCollapsed ? 1 : 0;
            Map map = LogUtil.beginTimes;
            boolean isKeyguardShowingAndNotOccluded = notificationShadeWindowState.isKeyguardShowingAndNotOccluded();
            boolean z4 = notificationShadeWindowState.panelVisible;
            boolean z5 = notificationShadeWindowState.keyguardFadingAway;
            boolean z6 = notificationShadeWindowState.bouncerShowing;
            boolean z7 = notificationShadeWindowState.headsUpNotificationShowing;
            int i3 = notificationShadeWindowState.scrimsVisibility;
            int i4 = notificationShadeWindowState.backgroundBlurRadius;
            boolean z8 = notificationShadeWindowState.launchingActivityFromNotification;
            boolean z9 = notificationShadeWindowState.dozing;
            boolean z10 = notificationShadeWindowState.forceInvisible;
            boolean z11 = notificationShadeWindowState.isCoverClosed;
            boolean z12 = notificationShadeWindowState.coverAppShowing;
            int i5 = notificationShadeWindowState.coverType;
            StringBuilder m66m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m66m("isExpanded=", z2, "\n!forceCollapsed=", i2, ", keyguard=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m66m, isKeyguardShowingAndNotOccluded ? 1 : 0, ", panel=", z4 ? 1 : 0, ", fadingAway=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m66m, z5 ? 1 : 0, ", bouncer=", z6 ? 1 : 0, ", headsUp=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m66m, z7 ? 1 : 0, ", scrim=", i3, ", blur=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m66m, i4, ", launchingActivity=", z8 ? 1 : 0, ", dozing=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m66m, z9 ? 1 : 0, ", forceInvisible=", z10 ? 1 : 0, ", coverClosed=");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m66m, z11 ? 1 : 0, ", coverApp=", z12 ? 1 : 0, ", coverType=");
            m66m.append(i5);
            com.android.systemui.keyguard.Log.m138d(SecNotificationShadeWindowControllerHelperImpl.DEBUG_TAG, m66m.toString());
            secNotificationShadeWindowControllerHelperImpl = secNotificationShadeWindowControllerHelperImpl2;
        } else {
            secNotificationShadeWindowControllerHelperImpl = secNotificationShadeWindowControllerHelperImpl2;
        }
        if (secNotificationShadeWindowControllerHelperImpl.isLastExpanded != z2) {
            Iterator it = CollectionsKt___CollectionsKt.toList(secNotificationShadeWindowControllerHelperImpl.visibilityMonitor.isExpandedChangedListeners).iterator();
            while (it.hasNext()) {
                ((Function1) it.next()).invoke(Boolean.valueOf(z2));
            }
        }
        secNotificationShadeWindowControllerHelperImpl.isLastExpanded = z2;
        return z2;
    }
}
