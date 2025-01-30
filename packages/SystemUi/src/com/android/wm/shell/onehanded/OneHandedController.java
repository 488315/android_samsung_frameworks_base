package com.android.wm.shell.onehanded;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.window.DisplayAreaInfo;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.DisplayChangeController;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TaskStackListenerCallback;
import com.android.wm.shell.common.TaskStackListenerImpl;
import com.android.wm.shell.sysui.ConfigurationChangeListener;
import com.android.wm.shell.sysui.KeyguardChangeListener;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.sysui.UserChangeListener;
import com.android.systemui.R;
import com.android.systemui.wmshell.WMShell;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class OneHandedController implements RemoteCallable, DisplayChangeController.OnDisplayChangingListener, ConfigurationChangeListener, KeyguardChangeListener, UserChangeListener {
    public final AccessibilityManager mAccessibilityManager;
    public final C40305 mActivatedObserver;
    public final Context mContext;
    public final OneHandedDisplayAreaOrganizer mDisplayAreaOrganizer;
    public final DisplayController mDisplayController;
    public final C40305 mEnabledObserver;
    public WMShell.C374410 mEventCallback;
    public boolean mIsOneHandedEnabled;
    public boolean mIsShortcutEnabled;
    public boolean mIsSwipeToNotificationEnabled;
    public boolean mKeyguardShowing;
    public boolean mLockedDisabled;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public final float mOffSetFraction;
    public final OneHandedAccessibilityUtil mOneHandedAccessibilityUtil;
    public final OneHandedSettingsUtil mOneHandedSettingsUtil;
    public final OneHandedUiEventLogger mOneHandedUiEventLogger;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellController mShellController;
    public final C40305 mShortcutEnabledObserver;
    public final OneHandedState mState;
    public final C40305 mSwipeToNotificationEnabledObserver;
    public boolean mTaskChangeToExit;
    public final TaskStackListenerImpl mTaskStackListener;
    public final OneHandedTimeoutHandler mTimeoutHandler;
    public final OneHandedTouchHandler mTouchHandler;
    public final OneHandedTutorialHandler mTutorialHandler;
    public int mUserId;
    public final OneHandedImpl mImpl = new OneHandedImpl(this, 0);
    public final C40261 mDisplaysChangedListener = new DisplayController.OnDisplaysChangedListener() { // from class: com.android.wm.shell.onehanded.OneHandedController.1
        @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
        public final void onDisplayAdded(int i) {
            if (i == 0) {
                OneHandedController oneHandedController = OneHandedController.this;
                if (oneHandedController.isInitialized()) {
                    oneHandedController.updateDisplayLayout(i);
                }
            }
        }

        @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
        public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
            if (i == 0) {
                OneHandedController oneHandedController = OneHandedController.this;
                if (oneHandedController.isInitialized()) {
                    oneHandedController.updateDisplayLayout(i);
                }
            }
        }
    };
    public final AccessibilityManagerAccessibilityStateChangeListenerC40272 mAccessibilityStateChangeListener = new AccessibilityManager.AccessibilityStateChangeListener() { // from class: com.android.wm.shell.onehanded.OneHandedController.2
        @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
        public final void onAccessibilityStateChanged(boolean z) {
            if (OneHandedController.this.isInitialized()) {
                if (!z) {
                    OneHandedController oneHandedController = OneHandedController.this;
                    OneHandedTimeoutHandler oneHandedTimeoutHandler = oneHandedController.mTimeoutHandler;
                    ContentResolver contentResolver = oneHandedController.mContext.getContentResolver();
                    int i = OneHandedController.this.mUserId;
                    oneHandedController.mOneHandedSettingsUtil.getClass();
                    int intForUser = Settings.Secure.getIntForUser(contentResolver, "one_handed_mode_timeout", 8, i);
                    oneHandedTimeoutHandler.mTimeout = intForUser;
                    oneHandedTimeoutHandler.mTimeoutMs = TimeUnit.SECONDS.toMillis(intForUser);
                    oneHandedTimeoutHandler.resetTimer();
                    return;
                }
                OneHandedController oneHandedController2 = OneHandedController.this;
                OneHandedSettingsUtil oneHandedSettingsUtil = oneHandedController2.mOneHandedSettingsUtil;
                ContentResolver contentResolver2 = oneHandedController2.mContext.getContentResolver();
                int i2 = OneHandedController.this.mUserId;
                oneHandedSettingsUtil.getClass();
                int recommendedTimeoutMillis = OneHandedController.this.mAccessibilityManager.getRecommendedTimeoutMillis(Settings.Secure.getIntForUser(contentResolver2, "one_handed_mode_timeout", 8, i2) * 1000, 4);
                OneHandedTimeoutHandler oneHandedTimeoutHandler2 = OneHandedController.this.mTimeoutHandler;
                int i3 = recommendedTimeoutMillis / 1000;
                oneHandedTimeoutHandler2.mTimeout = i3;
                oneHandedTimeoutHandler2.mTimeoutMs = TimeUnit.SECONDS.toMillis(i3);
                oneHandedTimeoutHandler2.resetTimer();
            }
        }
    };
    public final C40283 mTransitionCallBack = new OneHandedTransitionCallback() { // from class: com.android.wm.shell.onehanded.OneHandedController.3
        @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
        public final void onStartFinished(Rect rect) {
            OneHandedController oneHandedController = OneHandedController.this;
            oneHandedController.mState.setState(2);
            oneHandedController.notifyShortcutStateChanged(2);
        }

        @Override // com.android.wm.shell.onehanded.OneHandedTransitionCallback
        public final void onStopFinished(Rect rect) {
            OneHandedController oneHandedController = OneHandedController.this;
            oneHandedController.mState.setState(0);
            oneHandedController.notifyShortcutStateChanged(0);
        }
    };
    public final C40294 mTaskStackListenerCallback = new TaskStackListenerCallback() { // from class: com.android.wm.shell.onehanded.OneHandedController.4
        @Override // com.android.wm.shell.common.TaskStackListenerCallback
        public final void onTaskCreated() {
            OneHandedController.this.stopOneHanded(5);
        }

        @Override // com.android.wm.shell.common.TaskStackListenerCallback
        public final void onTaskMovedToFront(int i) {
            OneHandedController.this.stopOneHanded(5);
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IOneHandedImpl extends IOneHanded$Stub implements ExternalInterfaceBinder {
        public OneHandedController mController;

        public IOneHandedImpl(OneHandedController oneHandedController) {
            this.mController = oneHandedController;
        }

        @Override // com.android.wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            this.mController = null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OneHandedImpl implements OneHanded {
        public /* synthetic */ OneHandedImpl(OneHandedController oneHandedController, int i) {
            this();
        }

        private OneHandedImpl() {
        }
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.wm.shell.onehanded.OneHandedController$1] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.wm.shell.onehanded.OneHandedController$2] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.wm.shell.onehanded.OneHandedController$3] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.wm.shell.onehanded.OneHandedController$4] */
    /* JADX WARN: Type inference failed for: r3v10, types: [com.android.wm.shell.onehanded.OneHandedController$5] */
    /* JADX WARN: Type inference failed for: r3v12, types: [com.android.wm.shell.onehanded.OneHandedController$5] */
    /* JADX WARN: Type inference failed for: r3v6, types: [com.android.wm.shell.onehanded.OneHandedController$5] */
    /* JADX WARN: Type inference failed for: r3v8, types: [com.android.wm.shell.onehanded.OneHandedController$5] */
    public OneHandedController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellController shellController, DisplayController displayController, OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer, OneHandedTouchHandler oneHandedTouchHandler, OneHandedTutorialHandler oneHandedTutorialHandler, OneHandedSettingsUtil oneHandedSettingsUtil, OneHandedAccessibilityUtil oneHandedAccessibilityUtil, OneHandedTimeoutHandler oneHandedTimeoutHandler, OneHandedState oneHandedState, OneHandedUiEventLogger oneHandedUiEventLogger, TaskStackListenerImpl taskStackListenerImpl, ShellExecutor shellExecutor, Handler handler) {
        this.mContext = context;
        this.mShellCommandHandler = shellCommandHandler;
        this.mShellController = shellController;
        this.mOneHandedSettingsUtil = oneHandedSettingsUtil;
        this.mOneHandedAccessibilityUtil = oneHandedAccessibilityUtil;
        this.mDisplayAreaOrganizer = oneHandedDisplayAreaOrganizer;
        this.mDisplayController = displayController;
        this.mTouchHandler = oneHandedTouchHandler;
        this.mState = oneHandedState;
        this.mTutorialHandler = oneHandedTutorialHandler;
        this.mMainExecutor = shellExecutor;
        this.mMainHandler = handler;
        this.mOneHandedUiEventLogger = oneHandedUiEventLogger;
        this.mTaskStackListener = taskStackListenerImpl;
        this.mAccessibilityManager = AccessibilityManager.getInstance(context);
        int i = SystemProperties.getInt("persist.debug.one_handed_offset_percentage", Math.round(context.getResources().getFraction(R.fraction.config_one_handed_offset, 1, 1) * 100.0f));
        this.mUserId = UserHandle.myUserId();
        this.mOffSetFraction = i / 100.0f;
        ContentResolver contentResolver = context.getContentResolver();
        int i2 = this.mUserId;
        oneHandedSettingsUtil.getClass();
        this.mIsOneHandedEnabled = OneHandedSettingsUtil.getSettingsOneHandedModeEnabled(contentResolver, i2);
        this.mIsSwipeToNotificationEnabled = OneHandedSettingsUtil.getSettingsSwipeToNotificationEnabled(context.getContentResolver(), this.mUserId);
        this.mTimeoutHandler = oneHandedTimeoutHandler;
        final OneHandedController$$ExternalSyntheticLambda0 oneHandedController$$ExternalSyntheticLambda0 = new OneHandedController$$ExternalSyntheticLambda0(this, 1);
        this.mActivatedObserver = new ContentObserver(this, handler) { // from class: com.android.wm.shell.onehanded.OneHandedController.5
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                oneHandedController$$ExternalSyntheticLambda0.run();
            }
        };
        final OneHandedController$$ExternalSyntheticLambda0 oneHandedController$$ExternalSyntheticLambda02 = new OneHandedController$$ExternalSyntheticLambda0(this, 2);
        this.mEnabledObserver = new ContentObserver(this, handler) { // from class: com.android.wm.shell.onehanded.OneHandedController.5
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                oneHandedController$$ExternalSyntheticLambda02.run();
            }
        };
        final OneHandedController$$ExternalSyntheticLambda0 oneHandedController$$ExternalSyntheticLambda03 = new OneHandedController$$ExternalSyntheticLambda0(this, 3);
        this.mSwipeToNotificationEnabledObserver = new ContentObserver(this, handler) { // from class: com.android.wm.shell.onehanded.OneHandedController.5
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                oneHandedController$$ExternalSyntheticLambda03.run();
            }
        };
        final OneHandedController$$ExternalSyntheticLambda0 oneHandedController$$ExternalSyntheticLambda04 = new OneHandedController$$ExternalSyntheticLambda0(this, 4);
        this.mShortcutEnabledObserver = new ContentObserver(this, handler) { // from class: com.android.wm.shell.onehanded.OneHandedController.5
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                oneHandedController$$ExternalSyntheticLambda04.run();
            }
        };
        shellInit.addInitCallback(new OneHandedController$$ExternalSyntheticLambda0(this, 5), this);
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }

    public final boolean isInitialized() {
        if (this.mDisplayAreaOrganizer != null && this.mDisplayController != null && this.mOneHandedSettingsUtil != null) {
            return true;
        }
        Slog.w("OneHandedController", "Components may not initialized yet!");
        return false;
    }

    public boolean isLockedDisabled() {
        return this.mLockedDisabled;
    }

    public boolean isOneHandedEnabled() {
        return this.mIsOneHandedEnabled;
    }

    public boolean isShortcutEnabled() {
        return this.mIsShortcutEnabled;
    }

    public boolean isSwipeToNotificationEnabled() {
        return this.mIsSwipeToNotificationEnabled;
    }

    public void notifyExpandNotification() {
        if (this.mEventCallback != null) {
            ((HandlerExecutor) this.mMainExecutor).execute(new OneHandedController$$ExternalSyntheticLambda0(this, 7));
        }
    }

    public void notifyShortcutStateChanged(int i) {
        if (isShortcutEnabled()) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            int i2 = i == 2 ? 1 : 0;
            int i3 = this.mUserId;
            this.mOneHandedSettingsUtil.getClass();
            Settings.Secure.putIntForUser(contentResolver, "one_handed_mode_activated", i2, i3);
        }
    }

    public void onActivatedActionChanged() {
        if (!isShortcutEnabled()) {
            Slog.w("OneHandedController", "Shortcut not enabled, skip onActivatedActionChanged()");
            return;
        }
        boolean isOneHandedEnabled = isOneHandedEnabled();
        Context context = this.mContext;
        OneHandedSettingsUtil oneHandedSettingsUtil = this.mOneHandedSettingsUtil;
        if (!isOneHandedEnabled) {
            ContentResolver contentResolver = context.getContentResolver();
            int i = this.mUserId;
            oneHandedSettingsUtil.getClass();
            Slog.d("OneHandedController", "Auto enabled One-handed mode by shortcut trigger, success=" + Settings.Secure.putIntForUser(contentResolver, "one_handed_mode_enabled", 1, i));
        }
        if (isSwipeToNotificationEnabled()) {
            notifyExpandNotification();
            return;
        }
        this.mState.getClass();
        boolean z = OneHandedState.sCurrentState == 2;
        ContentResolver contentResolver2 = context.getContentResolver();
        int i2 = this.mUserId;
        oneHandedSettingsUtil.getClass();
        boolean z2 = Settings.Secure.getIntForUser(contentResolver2, "one_handed_mode_activated", 0, i2) == 1;
        if (z ^ z2) {
            if (z2) {
                startOneHanded();
            } else {
                stopOneHanded();
            }
        }
    }

    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
    public final void onConfigurationChanged(Configuration configuration) {
        OneHandedTutorialHandler oneHandedTutorialHandler = this.mTutorialHandler;
        if (oneHandedTutorialHandler == null || !this.mIsOneHandedEnabled || configuration.orientation == 2) {
            return;
        }
        BackgroundWindowManager backgroundWindowManager = oneHandedTutorialHandler.mBackgroundWindowManager;
        int i = backgroundWindowManager.mCurrentState;
        if (i == 1 || i == 2) {
            backgroundWindowManager.updateThemeOnly();
        }
        oneHandedTutorialHandler.removeTutorialFromWindowManager();
        int i2 = oneHandedTutorialHandler.mCurrentState;
        if (i2 == 1 || i2 == 2) {
            oneHandedTutorialHandler.createViewAndAttachToWindow(oneHandedTutorialHandler.mContext);
            ViewGroup viewGroup = oneHandedTutorialHandler.mTargetViewContainer;
            if (viewGroup != null && backgroundWindowManager != null) {
                viewGroup.setBackgroundColor(backgroundWindowManager.getThemeColorForBackground());
            }
            oneHandedTutorialHandler.updateThemeColor();
            oneHandedTutorialHandler.checkTransitionEnd();
        }
    }

    @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
    public final void onDisplayChange(int i, int i2, int i3, DisplayAreaInfo displayAreaInfo, WindowContainerTransaction windowContainerTransaction) {
        if (isInitialized()) {
            Context context = this.mContext;
            ContentResolver contentResolver = context.getContentResolver();
            int i4 = this.mUserId;
            this.mOneHandedSettingsUtil.getClass();
            if (!OneHandedSettingsUtil.getSettingsOneHandedModeEnabled(contentResolver, i4) || OneHandedSettingsUtil.getSettingsSwipeToNotificationEnabled(context.getContentResolver(), this.mUserId)) {
                return;
            }
            this.mState.getClass();
            if (OneHandedState.sCurrentState == 2) {
                this.mOneHandedUiEventLogger.writeEvent(4);
            }
            OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer = this.mDisplayAreaOrganizer;
            DisplayLayout displayLayout = oneHandedDisplayAreaOrganizer.mDisplayLayout;
            if (displayLayout.mRotation == i3) {
                return;
            }
            displayLayout.rotateTo(i3, context.getResources());
            oneHandedDisplayAreaOrganizer.updateDisplayBounds();
            oneHandedDisplayAreaOrganizer.finishOffset(0, 2);
        }
    }

    public void onEnabledSettingChanged() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i = this.mUserId;
        this.mOneHandedSettingsUtil.getClass();
        boolean settingsOneHandedModeEnabled = OneHandedSettingsUtil.getSettingsOneHandedModeEnabled(contentResolver, i);
        this.mOneHandedUiEventLogger.writeEvent(settingsOneHandedModeEnabled ? 8 : 9);
        this.mIsOneHandedEnabled = settingsOneHandedModeEnabled;
        updateOneHandedEnabled();
    }

    @Override // com.android.wm.shell.sysui.KeyguardChangeListener
    public final void onKeyguardVisibilityChanged(boolean z, boolean z2) {
        this.mKeyguardShowing = z;
        stopOneHanded();
    }

    public final void onShortcutEnabledChanged() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i = this.mUserId;
        this.mOneHandedSettingsUtil.getClass();
        String stringForUser = Settings.Secure.getStringForUser(contentResolver, "accessibility_button_targets", i);
        boolean isEmpty = TextUtils.isEmpty(stringForUser);
        String str = OneHandedSettingsUtil.ONE_HANDED_MODE_TARGET_NAME;
        boolean z = true;
        if (isEmpty || !stringForUser.contains(str)) {
            String stringForUser2 = Settings.Secure.getStringForUser(contentResolver, "accessibility_shortcut_target_service", i);
            if (TextUtils.isEmpty(stringForUser2) || !stringForUser2.contains(str)) {
                z = false;
            }
        }
        this.mIsShortcutEnabled = z;
        this.mOneHandedUiEventLogger.writeEvent(z ? 20 : 21);
    }

    public void onSwipeToNotificationEnabledChanged() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i = this.mUserId;
        this.mOneHandedSettingsUtil.getClass();
        boolean settingsSwipeToNotificationEnabled = OneHandedSettingsUtil.getSettingsSwipeToNotificationEnabled(contentResolver, i);
        this.mIsSwipeToNotificationEnabled = settingsSwipeToNotificationEnabled;
        this.mState.getClass();
        notifyShortcutStateChanged(OneHandedState.sCurrentState);
        this.mOneHandedUiEventLogger.writeEvent(settingsSwipeToNotificationEnabled ? 18 : 19);
    }

    @Override // com.android.wm.shell.sysui.UserChangeListener
    public final void onUserChanged$1(int i) {
        Context context = this.mContext;
        ContentResolver contentResolver = context.getContentResolver();
        this.mOneHandedSettingsUtil.getClass();
        if (contentResolver != null) {
            contentResolver.unregisterContentObserver(this.mEnabledObserver);
        }
        ContentResolver contentResolver2 = context.getContentResolver();
        if (contentResolver2 != null) {
            contentResolver2.unregisterContentObserver(this.mSwipeToNotificationEnabledObserver);
        }
        ContentResolver contentResolver3 = context.getContentResolver();
        if (contentResolver3 != null) {
            contentResolver3.unregisterContentObserver(this.mShortcutEnabledObserver);
        }
        this.mUserId = i;
        registerSettingObservers(i);
        updateSettings();
        updateOneHandedEnabled();
    }

    public final void registerSettingObservers(int i) {
        Context context = this.mContext;
        ContentResolver contentResolver = context.getContentResolver();
        this.mOneHandedSettingsUtil.getClass();
        OneHandedSettingsUtil.registerSettingsKeyObserver("one_handed_mode_activated", contentResolver, this.mActivatedObserver, i);
        OneHandedSettingsUtil.registerSettingsKeyObserver("one_handed_mode_enabled", context.getContentResolver(), this.mEnabledObserver, i);
        OneHandedSettingsUtil.registerSettingsKeyObserver("swipe_bottom_to_notification_enabled", context.getContentResolver(), this.mSwipeToNotificationEnabledObserver, i);
        ContentResolver contentResolver2 = context.getContentResolver();
        C40305 c40305 = this.mShortcutEnabledObserver;
        OneHandedSettingsUtil.registerSettingsKeyObserver("accessibility_button_targets", contentResolver2, c40305, i);
        OneHandedSettingsUtil.registerSettingsKeyObserver("accessibility_shortcut_target_service", context.getContentResolver(), c40305, i);
    }

    public void setLockedDisabled(boolean z, boolean z2) {
        boolean z3 = false;
        if (z2 == (this.mIsOneHandedEnabled || this.mIsSwipeToNotificationEnabled)) {
            return;
        }
        if (z && !z2) {
            z3 = true;
        }
        this.mLockedDisabled = z3;
    }

    public void startOneHanded() {
        if (isLockedDisabled() || this.mKeyguardShowing) {
            Slog.d("OneHandedController", "Temporary lock disabled");
            return;
        }
        OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer = this.mDisplayAreaOrganizer;
        if (!oneHandedDisplayAreaOrganizer.mIsReady) {
            ((HandlerExecutor) this.mMainExecutor).executeDelayed(10L, new OneHandedController$$ExternalSyntheticLambda0(this, 6));
            return;
        }
        OneHandedState oneHandedState = this.mState;
        oneHandedState.getClass();
        int i = OneHandedState.sCurrentState;
        if (i == 1 || i == 3) {
            return;
        }
        if (i == 2) {
            return;
        }
        DisplayLayout displayLayout = oneHandedDisplayAreaOrganizer.mDisplayLayout;
        if (displayLayout.mWidth > displayLayout.mHeight) {
            Slog.w("OneHandedController", "One handed mode only support portrait mode");
            return;
        }
        oneHandedState.setState(1);
        int round = Math.round(oneHandedDisplayAreaOrganizer.mDisplayLayout.mHeight * this.mOffSetFraction);
        OneHandedAccessibilityUtil oneHandedAccessibilityUtil = this.mOneHandedAccessibilityUtil;
        oneHandedAccessibilityUtil.announcementForScreenReader(oneHandedAccessibilityUtil.mStartOneHandedDescription);
        oneHandedDisplayAreaOrganizer.scheduleOffset(round);
        this.mTimeoutHandler.resetTimer();
        this.mOneHandedUiEventLogger.writeEvent(0);
    }

    public void stopOneHanded() {
        stopOneHanded(1);
    }

    public void updateDisplayLayout(int i) {
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(i);
        if (displayLayout == null) {
            Slog.w("OneHandedController", "Failed to get new DisplayLayout.");
            return;
        }
        this.mDisplayAreaOrganizer.setDisplayLayout(displayLayout);
        OneHandedTutorialHandler oneHandedTutorialHandler = this.mTutorialHandler;
        oneHandedTutorialHandler.getClass();
        oneHandedTutorialHandler.mDisplayBounds = new Rect(0, 0, displayLayout.mWidth, displayLayout.mHeight);
        int round = Math.round(r0.height() * oneHandedTutorialHandler.mTutorialHeightRatio);
        oneHandedTutorialHandler.mTutorialAreaHeight = round;
        oneHandedTutorialHandler.mAlphaTransitionStart = round * 0.6f;
        BackgroundWindowManager backgroundWindowManager = oneHandedTutorialHandler.mBackgroundWindowManager;
        backgroundWindowManager.getClass();
        backgroundWindowManager.mDisplayBounds = new Rect(0, 0, displayLayout.mWidth, displayLayout.mHeight);
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0010, code lost:
    
        if (com.android.wm.shell.onehanded.OneHandedState.sCurrentState == 2) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateOneHandedEnabled() {
        OneHandedState oneHandedState = this.mState;
        oneHandedState.getClass();
        if (OneHandedState.sCurrentState != 1) {
            oneHandedState.getClass();
        }
        ((HandlerExecutor) this.mMainExecutor).execute(new OneHandedController$$ExternalSyntheticLambda0(this, 0));
        if (isOneHandedEnabled() && !isSwipeToNotificationEnabled()) {
            oneHandedState.getClass();
            notifyShortcutStateChanged(OneHandedState.sCurrentState);
        }
        boolean z = this.mIsOneHandedEnabled;
        OneHandedTouchHandler oneHandedTouchHandler = this.mTouchHandler;
        oneHandedTouchHandler.mIsEnabled = z;
        oneHandedTouchHandler.updateIsEnabled();
        boolean z2 = this.mIsOneHandedEnabled;
        OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer = this.mDisplayAreaOrganizer;
        if (!z2) {
            oneHandedDisplayAreaOrganizer.unregisterOrganizer();
        } else if (oneHandedDisplayAreaOrganizer.getDisplayAreaTokenMap().isEmpty()) {
            oneHandedDisplayAreaOrganizer.registerOrganizer(3);
        }
    }

    public final void updateSettings() {
        boolean isEmpty;
        boolean isEmpty2;
        OneHandedSettingsUtil oneHandedSettingsUtil = this.mOneHandedSettingsUtil;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i = this.mUserId;
        oneHandedSettingsUtil.getClass();
        this.mIsOneHandedEnabled = OneHandedSettingsUtil.getSettingsOneHandedModeEnabled(contentResolver, i);
        updateOneHandedEnabled();
        OneHandedTimeoutHandler oneHandedTimeoutHandler = this.mTimeoutHandler;
        OneHandedSettingsUtil oneHandedSettingsUtil2 = this.mOneHandedSettingsUtil;
        ContentResolver contentResolver2 = this.mContext.getContentResolver();
        int i2 = this.mUserId;
        oneHandedSettingsUtil2.getClass();
        int intForUser = Settings.Secure.getIntForUser(contentResolver2, "one_handed_mode_timeout", 8, i2);
        oneHandedTimeoutHandler.mTimeout = intForUser;
        oneHandedTimeoutHandler.mTimeoutMs = TimeUnit.SECONDS.toMillis(intForUser);
        oneHandedTimeoutHandler.resetTimer();
        OneHandedSettingsUtil oneHandedSettingsUtil3 = this.mOneHandedSettingsUtil;
        ContentResolver contentResolver3 = this.mContext.getContentResolver();
        int i3 = this.mUserId;
        oneHandedSettingsUtil3.getClass();
        boolean z = Settings.Secure.getIntForUser(contentResolver3, "taps_app_to_exit", 1, i3) == 1;
        if (z) {
            this.mTaskStackListener.addListener(this.mTaskStackListenerCallback);
        } else {
            TaskStackListenerImpl taskStackListenerImpl = this.mTaskStackListener;
            C40294 c40294 = this.mTaskStackListenerCallback;
            synchronized (taskStackListenerImpl.mTaskStackListeners) {
                isEmpty = ((ArrayList) taskStackListenerImpl.mTaskStackListeners).isEmpty();
                ((ArrayList) taskStackListenerImpl.mTaskStackListeners).remove(c40294);
                isEmpty2 = ((ArrayList) taskStackListenerImpl.mTaskStackListeners).isEmpty();
            }
            if (!isEmpty && isEmpty2) {
                try {
                    taskStackListenerImpl.mActivityTaskManager.unregisterTaskStackListener(taskStackListenerImpl);
                } catch (Exception e) {
                    Log.w("TaskStackListenerImpl", "Failed to call unregisterTaskStackListener", e);
                }
            }
        }
        this.mTaskChangeToExit = z;
        OneHandedSettingsUtil oneHandedSettingsUtil4 = this.mOneHandedSettingsUtil;
        ContentResolver contentResolver4 = this.mContext.getContentResolver();
        int i4 = this.mUserId;
        oneHandedSettingsUtil4.getClass();
        this.mIsSwipeToNotificationEnabled = OneHandedSettingsUtil.getSettingsSwipeToNotificationEnabled(contentResolver4, i4);
        onShortcutEnabledChanged();
    }

    public final void stopOneHanded(int i) {
        OneHandedState oneHandedState = this.mState;
        oneHandedState.getClass();
        int i2 = OneHandedState.sCurrentState;
        boolean z = true;
        if (i2 != 1 && i2 != 3) {
            z = false;
        }
        if (z || i2 == 0) {
            return;
        }
        oneHandedState.setState(3);
        OneHandedAccessibilityUtil oneHandedAccessibilityUtil = this.mOneHandedAccessibilityUtil;
        oneHandedAccessibilityUtil.announcementForScreenReader(oneHandedAccessibilityUtil.mStopOneHandedDescription);
        this.mDisplayAreaOrganizer.scheduleOffset(0);
        OneHandedTimeoutHandler oneHandedTimeoutHandler = this.mTimeoutHandler;
        ((HandlerExecutor) oneHandedTimeoutHandler.mMainExecutor).removeCallbacks(oneHandedTimeoutHandler.mTimeoutRunnable);
        this.mOneHandedUiEventLogger.writeEvent(i);
    }
}
