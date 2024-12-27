package com.android.keyguard;

import android.database.ContentObserver;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.MigrateClocksToBlueprint;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.ui.view.InWindowLauncherUnlockAnimationManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.NotificationIconContainerAlwaysOnDisplayViewBinder;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class KeyguardClockSwitchController extends ViewController implements Dumpable {
    public NotificationIconContainer mAodIconContainer;
    public final Executor mBgExecutor;
    public boolean mCanShowDoubleLineClock;
    public final AnonymousClass4 mClockChangedListener;
    public final ClockEventController mClockEventController;
    public final ClockRegistry mClockRegistry;
    public int mCurrentClockSize;
    public ViewGroup mDateWeatherView;
    public final AnonymousClass1 mDoubleLineClockObserver;
    public final DumpManager mDumpManager;
    public final FeatureFlagsClassic mFeatureFlags;
    public final InWindowLauncherUnlockAnimationManager mInWindowLauncherUnlockAnimationManager;
    public boolean mIsActiveDreamLockscreenHosted;
    final Consumer<Boolean> mIsActiveDreamLockscreenHostedCallback;
    public int mKeyguardDateWeatherViewInvisibility;
    public final KeyguardSliceViewController mKeyguardSliceViewController;
    public final KeyguardUnlockAnimationController mKeyguardUnlockAnimationController;
    public final AnonymousClass3 mKeyguardUnlockAnimationListener;
    public FrameLayout mLargeClockFrame;
    public final LogBuffer mLogBuffer;
    public final NotificationIconAreaController mNotificationIconAreaController;
    public final SecureSettings mSecureSettings;
    public final AnonymousClass2 mShowWeatherObserver;
    public boolean mShownOnSecondaryDisplay;
    public FrameLayout mSmallClockFrame;
    public final LockscreenSmartspaceController mSmartspaceController;
    public View mSmartspaceView;
    public ViewGroup mStatusArea;
    public final StatusBarStateController mStatusBarStateController;
    public final DelayableExecutor mUiExecutor;
    public View mWeatherView;

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.keyguard.KeyguardClockSwitchController$4] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.keyguard.KeyguardClockSwitchController$1] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.keyguard.KeyguardClockSwitchController$2] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.keyguard.KeyguardClockSwitchController$3] */
    public KeyguardClockSwitchController(KeyguardClockSwitch keyguardClockSwitch, StatusBarStateController statusBarStateController, ClockRegistry clockRegistry, KeyguardSliceViewController keyguardSliceViewController, NotificationIconAreaController notificationIconAreaController, LockscreenSmartspaceController lockscreenSmartspaceController, NotificationIconContainerAlwaysOnDisplayViewBinder notificationIconContainerAlwaysOnDisplayViewBinder, KeyguardUnlockAnimationController keyguardUnlockAnimationController, SecureSettings secureSettings, DelayableExecutor delayableExecutor, Executor executor, DumpManager dumpManager, ClockEventController clockEventController, LogBuffer logBuffer, KeyguardInteractor keyguardInteractor, KeyguardClockInteractor keyguardClockInteractor, FeatureFlagsClassic featureFlagsClassic, InWindowLauncherUnlockAnimationManager inWindowLauncherUnlockAnimationManager) {
        super(keyguardClockSwitch);
        this.mCurrentClockSize = 1;
        this.mKeyguardDateWeatherViewInvisibility = 4;
        this.mShownOnSecondaryDisplay = false;
        this.mIsActiveDreamLockscreenHosted = false;
        this.mCanShowDoubleLineClock = true;
        this.mIsActiveDreamLockscreenHostedCallback = new Consumer() { // from class: com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                KeyguardClockSwitchController keyguardClockSwitchController = KeyguardClockSwitchController.this;
                Boolean bool = (Boolean) obj;
                if (keyguardClockSwitchController.mIsActiveDreamLockscreenHosted == bool.booleanValue()) {
                    return;
                }
                keyguardClockSwitchController.mIsActiveDreamLockscreenHosted = bool.booleanValue();
                if (keyguardClockSwitchController.mStatusArea != null) {
                    keyguardClockSwitchController.mUiExecutor.execute(new KeyguardClockSwitchController$$ExternalSyntheticLambda2(keyguardClockSwitchController, 6));
                }
            }
        };
        Handler handler = null;
        this.mDoubleLineClockObserver = new ContentObserver(handler) { // from class: com.android.keyguard.KeyguardClockSwitchController.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                KeyguardClockSwitchController.this.updateDoubleLineClock();
            }
        };
        this.mShowWeatherObserver = new ContentObserver(handler) { // from class: com.android.keyguard.KeyguardClockSwitchController.2
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                KeyguardClockSwitchController keyguardClockSwitchController = KeyguardClockSwitchController.this;
                if (keyguardClockSwitchController.mWeatherView != null) {
                    keyguardClockSwitchController.mUiExecutor.execute(new KeyguardClockSwitchController$$ExternalSyntheticLambda2(keyguardClockSwitchController, 4));
                }
            }
        };
        this.mKeyguardUnlockAnimationListener = new KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener() { // from class: com.android.keyguard.KeyguardClockSwitchController.3
            @Override // com.android.systemui.keyguard.KeyguardUnlockAnimationController.KeyguardUnlockAnimationListener
            public final void onUnlockAnimationFinished() {
                ViewGroup viewGroup = KeyguardClockSwitchController.this.mStatusArea;
                if (viewGroup != null) {
                    viewGroup.setClipChildren(true);
                }
            }
        };
        this.mStatusBarStateController = statusBarStateController;
        this.mClockRegistry = clockRegistry;
        this.mKeyguardSliceViewController = keyguardSliceViewController;
        this.mNotificationIconAreaController = notificationIconAreaController;
        this.mSmartspaceController = lockscreenSmartspaceController;
        this.mSecureSettings = secureSettings;
        this.mUiExecutor = delayableExecutor;
        this.mBgExecutor = executor;
        this.mKeyguardUnlockAnimationController = keyguardUnlockAnimationController;
        this.mDumpManager = dumpManager;
        this.mClockEventController = clockEventController;
        this.mLogBuffer = logBuffer;
        ((KeyguardClockSwitch) this.mView).mLogBuffer = logBuffer;
        this.mFeatureFlags = featureFlagsClassic;
        this.mInWindowLauncherUnlockAnimationManager = inWindowLauncherUnlockAnimationManager;
        this.mClockChangedListener = new ClockRegistry.ClockChangeListener() { // from class: com.android.keyguard.KeyguardClockSwitchController.4
            @Override // com.android.systemui.shared.clocks.ClockRegistry.ClockChangeListener
            public final void onCurrentClockChanged() {
                MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
                Flags.migrateClocksToBlueprint();
                KeyguardClockSwitchController keyguardClockSwitchController = KeyguardClockSwitchController.this;
                keyguardClockSwitchController.setClock(keyguardClockSwitchController.mClockRegistry.createCurrentClock());
            }
        };
    }

    public final void addDateWeatherView() {
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        ViewGroup viewGroup = (ViewGroup) this.mView;
        LockscreenSmartspaceController lockscreenSmartspaceController = this.mSmartspaceController;
        this.mDateWeatherView = (ViewGroup) lockscreenSmartspaceController.buildAndConnectDateView(viewGroup);
        this.mStatusArea.addView(this.mDateWeatherView, 0, new LinearLayout.LayoutParams(-1, -2));
        this.mDateWeatherView.setPaddingRelative(getContext().getResources().getDimensionPixelSize(R.dimen.below_clock_padding_start), 0, getContext().getResources().getDimensionPixelSize(R.dimen.below_clock_padding_end), 0);
        Flags.migrateClocksToBlueprint();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        ViewGroup viewGroup2 = (ViewGroup) this.mView;
        lockscreenSmartspaceController.execution.assertIsMainThread();
        if (!lockscreenSmartspaceController.isEnabled()) {
            throw new RuntimeException("Cannot build view when not enabled");
        }
        if (!lockscreenSmartspaceController.isDateWeatherDecoupled()) {
            throw new RuntimeException("Cannot build weather view when not decoupled");
        }
        View buildView = lockscreenSmartspaceController.buildView(viewGroup2, lockscreenSmartspaceController.weatherPlugin, null);
        lockscreenSmartspaceController.connectSession();
        this.mWeatherView = buildView;
        this.mDateWeatherView.addView(this.mWeatherView, this.mDateWeatherView.getChildCount() == 0 ? 0 : 1, layoutParams);
        this.mWeatherView.setPaddingRelative(0, 0, 4, 0);
    }

    public final void addSmartspaceView() {
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        Flags.FEATURE_FLAGS.getClass();
        ViewGroup viewGroup = (ViewGroup) this.mView;
        LockscreenSmartspaceController lockscreenSmartspaceController = this.mSmartspaceController;
        lockscreenSmartspaceController.execution.assertIsMainThread();
        if (!lockscreenSmartspaceController.isEnabled()) {
            throw new RuntimeException("Cannot build view when not enabled");
        }
        View buildView = lockscreenSmartspaceController.buildView(viewGroup, lockscreenSmartspaceController.plugin, lockscreenSmartspaceController.configPlugin);
        lockscreenSmartspaceController.connectSession();
        this.mSmartspaceView = buildView;
        if (buildView != null) {
            this.mStatusArea.addView(this.mSmartspaceView, 0, new LinearLayout.LayoutParams(-1, -2));
            this.mSmartspaceView.setPaddingRelative(getContext().getResources().getDimensionPixelSize(R.dimen.below_clock_padding_start), 0, getContext().getResources().getDimensionPixelSize(R.dimen.below_clock_padding_end), 0);
            View view = this.mSmartspaceView;
            this.mKeyguardUnlockAnimationController.lockscreenSmartspace = view;
            this.mInWindowLauncherUnlockAnimationManager.lockscreenSmartspace = view;
            ((KeyguardClockSwitch) this.mView).mSmartspace = view;
        }
    }

    public final void displayClock(int i, boolean z) {
        if (this.mCanShowDoubleLineClock || i != 0) {
            this.mCurrentClockSize = i;
            setDateWeatherVisibility();
            ClockController clock = getClock();
            KeyguardClockSwitch keyguardClockSwitch = (KeyguardClockSwitch) this.mView;
            Integer num = keyguardClockSwitch.mDisplayedClockSize;
            if (num == null || i != num.intValue()) {
                if (keyguardClockSwitch.mChildrenAreLaidOut) {
                    keyguardClockSwitch.updateClockViews(i == 0, z);
                }
                keyguardClockSwitch.mDisplayedClockSize = Integer.valueOf(i);
                r3 = true;
            }
            if (clock != null && z && r3 && i == 0) {
                this.mUiExecutor.executeDelayed(new KeyguardClockSwitchController$$ExternalSyntheticLambda2(clock, 7), 133L);
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(new StringBuilder("currentClockSizeLarge: "), this.mCurrentClockSize == 0, printWriter, "mCanShowDoubleLineClock: "), this.mCanShowDoubleLineClock, printWriter);
        ((KeyguardClockSwitch) this.mView).dump(printWriter);
        ClockRegistry clockRegistry = this.mClockRegistry;
        clockRegistry.getClass();
        printWriter.println("ClockRegistry:");
        printWriter.println("  settings = " + clockRegistry.settings);
        for (Map.Entry entry : clockRegistry.availableClocks.entrySet()) {
            printWriter.println("  availableClocks[" + ((String) entry.getKey()) + "] = " + ((ClockRegistry.ClockInfo) entry.getValue()));
        }
        ClockController clock = getClock();
        if (clock != null) {
            clock.dump(printWriter);
        }
        this.mClockEventController.getClass();
    }

    public final ClockController getClock() {
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        return this.mClockEventController.clock;
    }

    public final KeyguardClockSwitch getView() {
        return (KeyguardClockSwitch) this.mView;
    }

    public final void onConfigChanged() {
        ((KeyguardClockSwitch) this.mView).onConfigChanged();
        ((KeyguardClockSwitch) this.mView).getResources().getDimensionPixelSize(R.dimen.keyguard_clock_top_margin);
        ((KeyguardClockSwitch) this.mView).getResources().getDimensionPixelSize(R.dimen.keyguard_large_clock_top_margin);
        this.mKeyguardDateWeatherViewInvisibility = ((KeyguardClockSwitch) this.mView).getResources().getInteger(R.integer.keyguard_date_weather_view_invisibility);
        ((KeyguardClockSwitch) this.mView).updateClockTargetRegions();
        setDateWeatherVisibility();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        this.mKeyguardSliceViewController.init();
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        this.mSmallClockFrame = (FrameLayout) ((KeyguardClockSwitch) this.mView).findViewById(R.id.lockscreen_clock_view);
        this.mLargeClockFrame = (FrameLayout) ((KeyguardClockSwitch) this.mView).findViewById(R.id.lockscreen_clock_view_large);
        DumpManager dumpManager = this.mDumpManager;
        dumpManager.unregisterDumpable("KeyguardClockSwitchController");
        DumpManager.registerDumpable$default(dumpManager, "KeyguardClockSwitchController", this);
        com.android.systemui.flags.Flags flags = com.android.systemui.flags.Flags.INSTANCE;
        this.mFeatureFlags.getClass();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        int i = 0;
        ClockRegistry clockRegistry = this.mClockRegistry;
        clockRegistry.f74assert.isMainThread();
        ((ArrayList) clockRegistry.clockChangeListeners).add(this.mClockChangedListener);
        setClock(clockRegistry.createCurrentClock());
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        View view = this.mView;
        ClockEventController clockEventController = this.mClockEventController;
        clockEventController.registerListeners(view);
        ((KeyguardClockSwitch) this.mView).getResources().getDimensionPixelSize(R.dimen.keyguard_clock_top_margin);
        ((KeyguardClockSwitch) this.mView).getResources().getDimensionPixelSize(R.dimen.keyguard_large_clock_top_margin);
        this.mKeyguardDateWeatherViewInvisibility = ((KeyguardClockSwitch) this.mView).getResources().getInteger(R.integer.keyguard_date_weather_view_invisibility);
        if (this.mShownOnSecondaryDisplay) {
            ClockController clockController = ((KeyguardClockSwitch) this.mView).mClock;
            if (clockController != null) {
                clockController.getLargeClock().getEvents().onSecondaryDisplayChanged(true);
            }
            clockEventController.largeClockOnSecondaryDisplay = true;
            clockEventController.updateFontSizes();
            displayClock(0, false);
            ((KeyguardClockSwitch) this.mView).findViewById(R.id.keyguard_slice_view).setVisibility(8);
            View findViewById = ((KeyguardClockSwitch) this.mView).findViewById(R.id.left_aligned_notification_icon_container);
            if (findViewById != null) {
                findViewById.setVisibility(8);
                return;
            }
            return;
        }
        Flags.migrateClocksToBlueprint();
        NotificationIconContainer notificationIconContainer = (NotificationIconContainer) ((KeyguardClockSwitch) this.mView).findViewById(R.id.left_aligned_notification_icon_container);
        int i2 = NotificationIconContainerRefactor.$r8$clinit;
        this.mNotificationIconAreaController.setupAodIcons();
        this.mAodIconContainer = notificationIconContainer;
        this.mStatusArea = (ViewGroup) ((KeyguardClockSwitch) this.mView).findViewById(R.id.keyguard_status_area);
        this.mBgExecutor.execute(new KeyguardClockSwitchController$$ExternalSyntheticLambda2(this, i));
        updateDoubleLineClock();
        this.mKeyguardUnlockAnimationController.listeners.add(this.mKeyguardUnlockAnimationListener);
        LockscreenSmartspaceController lockscreenSmartspaceController = this.mSmartspaceController;
        if (lockscreenSmartspaceController.isEnabled()) {
            View findViewById2 = ((KeyguardClockSwitch) this.mView).findViewById(R.id.keyguard_slice_view);
            this.mStatusArea.indexOfChild(findViewById2);
            findViewById2.setVisibility(8);
            removeViewsFromStatusArea();
            addSmartspaceView();
            if (lockscreenSmartspaceController.isDateWeatherDecoupled()) {
                addDateWeatherView();
            }
        }
        Flags.migrateClocksToBlueprint();
        setDateWeatherVisibility();
        if (this.mWeatherView != null) {
            this.mUiExecutor.execute(new KeyguardClockSwitchController$$ExternalSyntheticLambda2(this, 4));
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ClockRegistry clockRegistry = this.mClockRegistry;
        clockRegistry.f74assert.isMainThread();
        ((ArrayList) clockRegistry.clockChangeListeners).remove(this.mClockChangedListener);
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        this.mClockEventController.unregisterListeners();
        setClock(null);
        this.mBgExecutor.execute(new KeyguardClockSwitchController$$ExternalSyntheticLambda2(this, 2));
        this.mKeyguardUnlockAnimationController.listeners.remove(this.mKeyguardUnlockAnimationListener);
    }

    public final void removeViewsFromStatusArea() {
        for (int childCount = this.mStatusArea.getChildCount() - 1; childCount >= 0; childCount--) {
            if (this.mStatusArea.getChildAt(childCount).getTag(R.id.tag_smartspace_view) != null) {
                this.mStatusArea.removeViewAt(childCount);
            }
        }
    }

    public final void setClock(ClockController clockController) {
        LogBuffer logBuffer;
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        if (clockController != null && (logBuffer = this.mLogBuffer) != null) {
            logBuffer.log("KeyguardClockSwitchController", LogLevel.INFO, "New Clock", null);
        }
        this.mClockEventController.setClock(clockController);
        KeyguardClockSwitch keyguardClockSwitch = (KeyguardClockSwitch) this.mView;
        this.mStatusBarStateController.getState();
        keyguardClockSwitch.mClock = clockController;
        keyguardClockSwitch.mSmallClockFrame.removeAllViews();
        keyguardClockSwitch.mLargeClockFrame.removeAllViews();
        if (clockController == null) {
            LogBuffer logBuffer2 = keyguardClockSwitch.mLogBuffer;
            if (logBuffer2 != null) {
                logBuffer2.log("KeyguardClockSwitch", LogLevel.ERROR, "No clock being shown", null);
            }
        } else {
            LogBuffer logBuffer3 = keyguardClockSwitch.mLogBuffer;
            if (logBuffer3 != null) {
                logBuffer3.log("KeyguardClockSwitch", LogLevel.INFO, "Attached new clock views to switch", null);
            }
            keyguardClockSwitch.mSmallClockFrame.addView(clockController.getSmallClock().getView());
            keyguardClockSwitch.mLargeClockFrame.addView(clockController.getLargeClock().getView());
            keyguardClockSwitch.updateClockTargetRegions();
            keyguardClockSwitch.updateStatusArea(false);
        }
        setDateWeatherVisibility();
    }

    public final void setDateWeatherVisibility() {
        if (this.mDateWeatherView != null) {
            this.mUiExecutor.execute(new KeyguardClockSwitchController$$ExternalSyntheticLambda2(this, 1));
        }
    }

    public final void setLockscreenClockY(int i) {
        T t = this.mView;
        if (((KeyguardClockSwitch) t).screenOffsetYPadding != i) {
            ((KeyguardClockSwitch) t).screenOffsetYPadding = i;
            ((KeyguardClockSwitch) t).post(new KeyguardClockSwitchController$$ExternalSyntheticLambda2(this, 3));
        }
    }

    public final void setSplitShadeCentered(boolean z) {
        KeyguardClockSwitch keyguardClockSwitch = (KeyguardClockSwitch) this.mView;
        if (keyguardClockSwitch.mSplitShadeCentered != z) {
            keyguardClockSwitch.mSplitShadeCentered = z;
            keyguardClockSwitch.updateStatusArea(true);
        }
    }

    public final void updateDoubleLineClock() {
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        boolean z = this.mSecureSettings.getIntForUser("lockscreen_use_double_line_clock", ((KeyguardClockSwitch) this.mView).getResources().getInteger(android.R.integer.config_keyChordPowerVolumeUp), -2) != 0;
        this.mCanShowDoubleLineClock = z;
        if (z) {
            return;
        }
        this.mUiExecutor.execute(new KeyguardClockSwitchController$$ExternalSyntheticLambda2(this, 5));
    }

    public final void updatePosition(int i, float f, AnimationProperties animationProperties, boolean z) {
        if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1) {
            i = -i;
        }
        MigrateClocksToBlueprint migrateClocksToBlueprint = MigrateClocksToBlueprint.INSTANCE;
        Flags.migrateClocksToBlueprint();
        float f2 = i;
        PropertyAnimator.setProperty(this.mSmallClockFrame, AnimatableProperty.TRANSLATION_X, f2, animationProperties, z);
        PropertyAnimator.setProperty(this.mLargeClockFrame, AnimatableProperty.SCALE_X, f, animationProperties, z);
        PropertyAnimator.setProperty(this.mLargeClockFrame, AnimatableProperty.SCALE_Y, f, animationProperties, z);
        ViewGroup viewGroup = this.mStatusArea;
        if (viewGroup != null) {
            PropertyAnimator.setProperty(viewGroup, KeyguardStatusAreaView.TRANSLATE_X_AOD, f2, animationProperties, z);
        }
    }
}
