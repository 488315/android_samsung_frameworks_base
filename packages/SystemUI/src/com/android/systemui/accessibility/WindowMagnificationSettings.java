package com.android.systemui.accessibility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.MathUtils;
import android.util.Slog;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.accessibility.A11yRune;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.widget.SeslSeekBar;
import androidx.appcompat.widget.SwitchCompat;
import com.android.internal.accessibility.common.MagnificationConstants;
import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
import com.android.systemui.R;
import com.android.systemui.accessibility.AccessibilityLogger;
import com.android.systemui.accessibility.Magnification;
import com.android.systemui.accessibility.MagnificationGestureDetector;
import com.android.systemui.accessibility.MagnificationSettingsController;
import com.android.systemui.accessibility.SecSeekBarWithIconButtonsView;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.view.SemWindowManager;

public final class WindowMagnificationSettings implements MagnificationGestureDetector.OnGestureListener {
    public final boolean mAllowDiagonalScrolling;
    public SwitchCompat mAllowDiagonalScrollingSwitch;
    public TextView mAllowDiagonalScrollingTitle;
    public LinearLayout mAllowDiagonalScrollingView;
    public final WindowMagnificationSettingsCallback mCallback;
    public final Context mContext;
    public Button mDoneButton;
    public TextView mEditButton;
    public ImageButton mFullScreenButton;
    public final MagnificationGestureDetector mGestureDetector;
    public ImageButton mLargeButton;
    public final AnonymousClass1 mMagnificationCapabilityObserver;
    public TextView mMagnifierSizeTv;
    public ImageButton mMediumButton;
    final WindowManager.LayoutParams mParams;
    public final SecureSettings mSecureSettings;
    public int mSeekBarMagnitude;
    public LinearLayout mSettingView;
    public final SfVsyncFrameCallbackProvider mSfVsyncFrameProvider;
    public ImageButton mSmallButton;
    public final WindowMagnificationSettings$$ExternalSyntheticLambda3 mWindowInsetChangeRunnable;
    public final WindowManager mWindowManager;
    public SecSeekBarWithIconButtonsView mZoomSeekbar;
    public TextView mZoomTv;
    final Rect mDraggableWindowBounds = new Rect();
    public boolean mIsVisible = false;
    public boolean mSingleTapDetected = false;
    public float mScale = 1.0f;
    public final AnonymousClass2 mPanelDelegate = new View.AccessibilityDelegate() { // from class: com.android.systemui.accessibility.WindowMagnificationSettings.2
        @Override // android.view.View.AccessibilityDelegate
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_move_up, WindowMagnificationSettings.this.mContext.getString(R.string.accessibility_control_move_up)));
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_move_down, WindowMagnificationSettings.this.mContext.getString(R.string.accessibility_control_move_down)));
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_move_left, WindowMagnificationSettings.this.mContext.getString(R.string.accessibility_control_move_left)));
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.accessibility_action_move_right, WindowMagnificationSettings.this.mContext.getString(R.string.accessibility_control_move_right)));
        }

        @Override // android.view.View.AccessibilityDelegate
        public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            Rect bounds = WindowMagnificationSettings.this.mWindowManager.getCurrentWindowMetrics().getBounds();
            if (i == R.id.accessibility_action_move_up) {
                WindowMagnificationSettings.this.moveButton$1(0.0f, -bounds.height());
                return true;
            }
            if (i == R.id.accessibility_action_move_down) {
                WindowMagnificationSettings.this.moveButton$1(0.0f, bounds.height());
                return true;
            }
            if (i == R.id.accessibility_action_move_left) {
                WindowMagnificationSettings.this.moveButton$1(-bounds.width(), 0.0f);
                return true;
            }
            if (i != R.id.accessibility_action_move_right) {
                return super.performAccessibilityAction(view, i, bundle);
            }
            WindowMagnificationSettings.this.moveButton$1(bounds.width(), 0.0f);
            return true;
        }
    };
    public final AnonymousClass3 mButtonClickListener = new View.OnClickListener() { // from class: com.android.systemui.accessibility.WindowMagnificationSettings.3
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            int id = view.getId();
            if (id == R.id.magnifier_small_button) {
                WindowMagnificationSettings.m883$$Nest$msetMagnifierSize(WindowMagnificationSettings.this, 1);
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SA_ACCESSIBILITY_SCREEN_MAGNIFICATION_PANNEL, SystemUIAnalytics.SA_ACCESSIBILITY_EVENT_MAGNIFICATION_PANNEL_SIZE_SMALL);
                return;
            }
            if (id == R.id.magnifier_medium_button) {
                WindowMagnificationSettings.m883$$Nest$msetMagnifierSize(WindowMagnificationSettings.this, 2);
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SA_ACCESSIBILITY_SCREEN_MAGNIFICATION_PANNEL, SystemUIAnalytics.SA_ACCESSIBILITY_EVENT_MAGNIFICATION_PANNEL_SIZE_MEDIUM);
                return;
            }
            if (id == R.id.magnifier_large_button) {
                WindowMagnificationSettings.m883$$Nest$msetMagnifierSize(WindowMagnificationSettings.this, 3);
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SA_ACCESSIBILITY_SCREEN_MAGNIFICATION_PANNEL, SystemUIAnalytics.SA_ACCESSIBILITY_EVENT_MAGNIFICATION_PANNEL_SIZE_LARGE);
                return;
            }
            if (id == R.id.magnifier_full_button) {
                WindowMagnificationSettings.m883$$Nest$msetMagnifierSize(WindowMagnificationSettings.this, 4);
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SA_ACCESSIBILITY_SCREEN_MAGNIFICATION_PANNEL, SystemUIAnalytics.SA_ACCESSIBILITY_EVENT_MAGNIFICATION_CHANGE_SIZE_FULL);
                return;
            }
            if (id != R.id.magnifier_edit_button) {
                if (id == R.id.magnifier_horizontal_lock_view) {
                    WindowMagnificationSettings windowMagnificationSettings = WindowMagnificationSettings.this;
                    windowMagnificationSettings.setDiagonalScrolling(!(windowMagnificationSettings.mSecureSettings.getIntForUser("accessibility_allow_diagonal_scrolling", 1, -2) == 1));
                    return;
                } else {
                    if (id == R.id.magnifier_done_button) {
                        WindowMagnificationSettings.this.hideSettingPanel(true);
                        return;
                    }
                    return;
                }
            }
            WindowMagnificationSettings windowMagnificationSettings2 = WindowMagnificationSettings.this;
            MagnificationSettingsController magnificationSettingsController = MagnificationSettingsController.this;
            MagnificationSettingsController.Callback callback = magnificationSettingsController.mSettingsControllerCallback;
            int i = magnificationSettingsController.mDisplayId;
            Magnification.AnonymousClass4 anonymousClass4 = (Magnification.AnonymousClass4) callback;
            Magnification magnification = Magnification.this;
            magnification.mHandler.post(new Magnification$4$$ExternalSyntheticLambda0(anonymousClass4, i));
            magnification.mA11yLogger.uiEventLogger.log(AccessibilityLogger.MagnificationSettingsEvent.MAGNIFICATION_SETTINGS_SIZE_EDITING_ACTIVATED);
            windowMagnificationSettings2.mEditButton.announceForAccessibility(windowMagnificationSettings2.mContext.getString(R.string.accessibility_magnification_window_resizing_guides_shown));
            windowMagnificationSettings2.hideSettingPanel(true);
        }
    };
    public final AnonymousClass4 mScreenOffReceiver = new BroadcastReceiver() { // from class: com.android.systemui.accessibility.WindowMagnificationSettings.4
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            WindowMagnificationSettings.this.hideSettingPanel(true);
        }
    };
    public final AnonymousClass5 mWindowScaleChangeObserver = new ContentObserver(new Handler()) { // from class: com.android.systemui.accessibility.WindowMagnificationSettings.5
        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            float floatForUser = Settings.Secure.getFloatForUser(WindowMagnificationSettings.this.mContext.getContentResolver(), "accessibility_display_magnification_scale", WindowMagnificationSettings.this.mContext.getResources().getInteger(R.integer.magnification_default_scale), -2);
            WindowMagnificationSettings windowMagnificationSettings = WindowMagnificationSettings.this;
            windowMagnificationSettings.getClass();
            int i = (int) ((floatForUser - 1.0f) / 1.0f);
            if (i < 0) {
                i = 0;
            }
            windowMagnificationSettings.mZoomSeekbar.setProgress(i);
        }
    };

    /* renamed from: com.android.systemui.accessibility.WindowMagnificationSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends ContentObserver {
        public AnonymousClass1(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            WindowMagnificationSettings.this.mSettingView.post(new WindowMagnificationSettings$$ExternalSyntheticLambda3(this, 3));
        }
    }

    public final class SliderA11yDelegate extends View.AccessibilityDelegate {
        public /* synthetic */ SliderA11yDelegate(WindowMagnificationSettings windowMagnificationSettings, int i) {
            this(windowMagnificationSettings);
        }

        @Override // android.view.View.AccessibilityDelegate
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(SeekBar.class.getName());
        }

        private SliderA11yDelegate(WindowMagnificationSettings windowMagnificationSettings) {
        }
    }

    public final class ZoomSeekbarChangeListener implements SecSeekBarWithIconButtonsView.OnSeekBarWithIconButtonsChangeListener {
        public /* synthetic */ ZoomSeekbarChangeListener(WindowMagnificationSettings windowMagnificationSettings, int i) {
            this();
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeslSeekBar seslSeekBar, int i, boolean z) {
            if (z) {
                WindowMagnificationSettings windowMagnificationSettings = WindowMagnificationSettings.this;
                float f = (i / windowMagnificationSettings.mSeekBarMagnitude) + 1.0f;
                ((MagnificationSettingsController.AnonymousClass1) windowMagnificationSettings.mCallback).onMagnifierScale(f, false);
                windowMagnificationSettings.mZoomSeekbar.setSeekbarStateDescription(f);
                windowMagnificationSettings.mZoomSeekbar.announceForAccessibility(windowMagnificationSettings.mContext.getResources().getString(R.string.font_scale_percentage, Integer.valueOf(((int) f) * 100)));
            }
        }

        private ZoomSeekbarChangeListener() {
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch() {
        }

        @Override // androidx.appcompat.widget.SeslSeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeslSeekBar seslSeekBar) {
        }
    }

    /* renamed from: -$$Nest$msetMagnifierSize, reason: not valid java name */
    public static void m883$$Nest$msetMagnifierSize(WindowMagnificationSettings windowMagnificationSettings, int i) {
        Settings.Secure.putIntForUser(windowMagnificationSettings.mContext.getContentResolver(), "accessibility_change_magnification_size", i, -2);
        int i2 = 1;
        if (i == 4) {
            Settings.Secure.putIntForUser(windowMagnificationSettings.mContext.getContentResolver(), "accessibility_magnification_mode", 1, -2);
            MagnificationSettingsController magnificationSettingsController = MagnificationSettingsController.this;
            MagnificationSettingsController.Callback callback = magnificationSettingsController.mSettingsControllerCallback;
            Magnification.AnonymousClass4 anonymousClass4 = (Magnification.AnonymousClass4) callback;
            Magnification.this.mHandler.post(new Magnification$4$$ExternalSyntheticLambda2(anonymousClass4, magnificationSettingsController.mDisplayId, i2, 0));
            return;
        }
        if (i != 0) {
            MagnificationSettingsController magnificationSettingsController2 = MagnificationSettingsController.this;
            MagnificationSettingsController.Callback callback2 = magnificationSettingsController2.mSettingsControllerCallback;
            int i3 = magnificationSettingsController2.mDisplayId;
            Magnification.AnonymousClass4 anonymousClass42 = (Magnification.AnonymousClass4) callback2;
            Magnification magnification = Magnification.this;
            magnification.mHandler.post(new Magnification$4$$ExternalSyntheticLambda2(anonymousClass42, i3, i, 1));
            magnification.mA11yLogger.uiEventLogger.logWithPosition(AccessibilityLogger.MagnificationSettingsEvent.MAGNIFICATION_SETTINGS_WINDOW_SIZE_SELECTED, 0, (String) null, i);
            Settings.Secure.putIntForUser(windowMagnificationSettings.mContext.getContentResolver(), "accessibility_magnification_mode", 2, -2);
            MagnificationSettingsController magnificationSettingsController3 = MagnificationSettingsController.this;
            MagnificationSettingsController.Callback callback3 = magnificationSettingsController3.mSettingsControllerCallback;
            Magnification.AnonymousClass4 anonymousClass43 = (Magnification.AnonymousClass4) callback3;
            Magnification.this.mHandler.post(new Magnification$4$$ExternalSyntheticLambda2(anonymousClass43, magnificationSettingsController3.mDisplayId, 2, 0));
            if (i == 1) {
                windowMagnificationSettings.mSettingView.announceForAccessibility(windowMagnificationSettings.mContext.getResources().getString(R.string.accessibility_magnification_partial_screen_small_applied));
            } else if (i == 2) {
                windowMagnificationSettings.mSettingView.announceForAccessibility(windowMagnificationSettings.mContext.getResources().getString(R.string.accessibility_magnification_partial_screen_medium_applied));
            } else if (i == 3) {
                windowMagnificationSettings.mSettingView.announceForAccessibility(windowMagnificationSettings.mContext.getResources().getString(R.string.accessibility_magnification_partial_screen_large_applied));
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.accessibility.WindowMagnificationSettings$2] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.accessibility.WindowMagnificationSettings$3] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.accessibility.WindowMagnificationSettings$4] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.accessibility.WindowMagnificationSettings$5] */
    public WindowMagnificationSettings(Context context, WindowMagnificationSettingsCallback windowMagnificationSettingsCallback, SfVsyncFrameCallbackProvider sfVsyncFrameCallbackProvider, SecureSettings secureSettings) {
        this.mAllowDiagonalScrolling = false;
        this.mContext = context;
        context.setTheme(2132018522);
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mSfVsyncFrameProvider = sfVsyncFrameCallbackProvider;
        this.mCallback = windowMagnificationSettingsCallback;
        this.mSecureSettings = secureSettings;
        this.mAllowDiagonalScrolling = secureSettings.getIntForUser("accessibility_allow_diagonal_scrolling", 1, -2) == 1;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2024, 8, -2);
        layoutParams.gravity = 51;
        layoutParams.accessibilityTitle = context.getString(R.string.accessibility_magnification_title);
        if (context.getDisplayId() == 0 && !A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP) {
            layoutParams.layoutInDisplayCutoutMode = 3;
        }
        layoutParams.setFitInsetsTypes(WindowInsets.Type.systemBars());
        layoutParams.receiveInsetsIgnoringZOrder = true;
        layoutParams.setTitle("MagnificationModeSwitch");
        this.mParams = layoutParams;
        this.mWindowInsetChangeRunnable = new WindowMagnificationSettings$$ExternalSyntheticLambda3(this, 0);
        inflateView();
        this.mGestureDetector = new MagnificationGestureDetector(context, context.getMainThreadHandler(), this);
        this.mMagnificationCapabilityObserver = new AnonymousClass1(context.getMainThreadHandler());
    }

    public static void applyUpToLargeTextSize(Context context, TextView textView, float f) {
        if (context == null || textView == null || f < 0.0f) {
            Slog.w("WindowMagnificationSettings", "parameter error");
            return;
        }
        if (context.getResources().getConfiguration().fontScale > 1.3f) {
            textView.setTextSize(0, (float) Math.floor(Math.ceil(f / r4) * 1.2999999523162842d));
        }
    }

    public final Rect getDraggableWindowBounds$1() {
        WindowMetrics currentWindowMetrics = this.mWindowManager.getCurrentWindowMetrics();
        Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mSettingView.measure(makeMeasureSpec, makeMeasureSpec);
        Rect rect = new Rect(currentWindowMetrics.getBounds());
        rect.offsetTo(0, 0);
        rect.inset(0, 0, this.mSettingView.getMeasuredWidth(), this.mSettingView.getMeasuredHeight());
        rect.inset(insetsIgnoringVisibility);
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.magnification_setting_margin);
        rect.inset(dimensionPixelSize, dimensionPixelSize);
        return rect;
    }

    public ViewGroup getSettingView() {
        return this.mSettingView;
    }

    public final void hideSettingPanel(boolean z) {
        if (this.mIsVisible) {
            this.mSecureSettings.unregisterContentObserverSync(this.mMagnificationCapabilityObserver);
            this.mWindowManager.removeView(this.mSettingView);
            boolean z2 = false;
            this.mIsVisible = false;
            if (z) {
                WindowManager.LayoutParams layoutParams = this.mParams;
                layoutParams.x = 0;
                layoutParams.y = 0;
            }
            this.mContext.unregisterReceiver(this.mScreenOffReceiver);
            MagnificationSettingsController magnificationSettingsController = MagnificationSettingsController.this;
            MagnificationSettingsController.Callback callback = magnificationSettingsController.mSettingsControllerCallback;
            Magnification.AnonymousClass4 anonymousClass4 = (Magnification.AnonymousClass4) callback;
            Magnification.this.mHandler.post(new Magnification$4$$ExternalSyntheticLambda0(anonymousClass4, magnificationSettingsController.mDisplayId, z2, 0));
            this.mContext.getContentResolver().unregisterContentObserver(this.mWindowScaleChangeObserver);
        }
    }

    public final void inflateView() {
        if (A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP && SemWindowManager.getInstance().isFolded()) {
            this.mSettingView = (LinearLayout) View.inflate(this.mContext, R.layout.window_magnification_settings_cover_view, null);
        } else {
            this.mSettingView = (LinearLayout) View.inflate(this.mContext, R.layout.window_magnification_settings_view, null);
        }
        this.mSettingView.setFocusable(true);
        this.mSettingView.setFocusableInTouchMode(true);
        this.mSettingView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.accessibility.WindowMagnificationSettings$$ExternalSyntheticLambda4
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                WindowMagnificationSettings windowMagnificationSettings = WindowMagnificationSettings.this;
                if (windowMagnificationSettings.mIsVisible) {
                    return windowMagnificationSettings.mGestureDetector.onTouch(view, motionEvent);
                }
                return false;
            }
        });
        this.mSettingView.setAccessibilityDelegate(this.mPanelDelegate);
        this.mSmallButton = (ImageButton) this.mSettingView.findViewById(R.id.magnifier_small_button);
        this.mMediumButton = (ImageButton) this.mSettingView.findViewById(R.id.magnifier_medium_button);
        this.mLargeButton = (ImageButton) this.mSettingView.findViewById(R.id.magnifier_large_button);
        this.mDoneButton = (Button) this.mSettingView.findViewById(R.id.magnifier_done_button);
        this.mEditButton = (TextView) this.mSettingView.findViewById(R.id.magnifier_edit_button);
        this.mFullScreenButton = (ImageButton) this.mSettingView.findViewById(R.id.magnifier_full_button);
        this.mAllowDiagonalScrollingTitle = (TextView) this.mSettingView.findViewById(R.id.magnifier_horizontal_lock_title);
        SecSeekBarWithIconButtonsView secSeekBarWithIconButtonsView = (SecSeekBarWithIconButtonsView) this.mSettingView.findViewById(R.id.magnifier_zoom_slider);
        this.mZoomSeekbar = secSeekBarWithIconButtonsView;
        secSeekBarWithIconButtonsView.mSeekbar.setMax((int) ((MagnificationConstants.SCALE_MAX_VALUE - 1.0f) * secSeekBarWithIconButtonsView.mSeekBarChangeMagnitude));
        this.mSeekBarMagnitude = this.mZoomSeekbar.mSeekBarChangeMagnitude;
        float floatForUser = this.mSecureSettings.getFloatForUser("accessibility_display_magnification_scale", 0.0f, -2);
        this.mScale = floatForUser;
        setScaleSeekbar(floatForUser);
        int i = 0;
        this.mZoomSeekbar.mSeekBarListener.mOnSeekBarChangeListener = new ZoomSeekbarChangeListener(this, i);
        this.mSmallButton.setTooltipText(this.mContext.getString(R.string.accessibility_magnification_small));
        this.mMediumButton.setTooltipText(this.mContext.getString(R.string.accessibility_magnification_medium));
        this.mLargeButton.setTooltipText(this.mContext.getString(R.string.accessibility_magnification_large));
        this.mFullScreenButton.setTooltipText(this.mContext.getString(R.string.accessibility_magnification_switch_to_full_screen));
        this.mZoomSeekbar.setAccessibilityDelegate(new SliderA11yDelegate(this, i));
        this.mZoomSeekbar.setSeekbarStateDescription(this.mScale);
        this.mZoomSeekbar.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.accessibility.WindowMagnificationSettings.6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WindowMagnificationSettings.this.mZoomSeekbar.announceForAccessibility(WindowMagnificationSettings.this.mContext.getResources().getString(R.string.font_scale_percentage, Integer.valueOf(((int) WindowMagnificationSettings.this.mScale) * 100)) + WindowMagnificationSettings.this.mContext.getResources().getString(R.string.accessibility_magnification_zoom));
            }
        });
        this.mMagnifierSizeTv = (TextView) this.mSettingView.findViewById(R.id.magnifier_size_title);
        this.mZoomTv = (TextView) this.mSettingView.findViewById(R.id.magnifier_zoom);
        applyUpToLargeTextSize(this.mContext, this.mDoneButton, r0.getResources().getDimensionPixelSize(R.dimen.magnification_setting_text_size));
        applyUpToLargeTextSize(this.mContext, this.mEditButton, r0.getResources().getDimensionPixelSize(R.dimen.magnification_setting_text_size));
        applyUpToLargeTextSize(this.mContext, this.mMagnifierSizeTv, r0.getResources().getDimensionPixelSize(R.dimen.magnification_setting_text_size));
        applyUpToLargeTextSize(this.mContext, this.mAllowDiagonalScrollingTitle, r0.getResources().getDimensionPixelSize(R.dimen.magnification_setting_text_size));
        applyUpToLargeTextSize(this.mContext, this.mZoomTv, r0.getResources().getDimensionPixelSize(R.dimen.magnification_setting_text_size));
        this.mAllowDiagonalScrollingView = (LinearLayout) this.mSettingView.findViewById(R.id.magnifier_horizontal_lock_view);
        SwitchCompat switchCompat = (SwitchCompat) this.mSettingView.findViewById(R.id.magnifier_horizontal_lock_switch);
        this.mAllowDiagonalScrollingSwitch = switchCompat;
        switchCompat.setChecked(this.mAllowDiagonalScrolling);
        this.mSmallButton.setOnClickListener(this.mButtonClickListener);
        this.mMediumButton.setOnClickListener(this.mButtonClickListener);
        this.mLargeButton.setOnClickListener(this.mButtonClickListener);
        this.mDoneButton.setOnClickListener(this.mButtonClickListener);
        this.mFullScreenButton.setOnClickListener(this.mButtonClickListener);
        this.mEditButton.setOnClickListener(this.mButtonClickListener);
        this.mAllowDiagonalScrollingTitle.setSelected(true);
        this.mAllowDiagonalScrollingView.setOnClickListener(this.mButtonClickListener);
        this.mSettingView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.accessibility.WindowMagnificationSettings$$ExternalSyntheticLambda5
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                WindowMagnificationSettings windowMagnificationSettings = WindowMagnificationSettings.this;
                if (windowMagnificationSettings.mSettingView.isAttachedToWindow()) {
                    Handler handler = windowMagnificationSettings.mSettingView.getHandler();
                    WindowMagnificationSettings$$ExternalSyntheticLambda3 windowMagnificationSettings$$ExternalSyntheticLambda3 = windowMagnificationSettings.mWindowInsetChangeRunnable;
                    if (!handler.hasCallbacks(windowMagnificationSettings$$ExternalSyntheticLambda3)) {
                        windowMagnificationSettings.mSettingView.getHandler().post(windowMagnificationSettings$$ExternalSyntheticLambda3);
                    }
                }
                return view.onApplyWindowInsets(windowInsets);
            }
        });
    }

    public boolean isDiagonalScrollingEnabled() {
        return this.mAllowDiagonalScrolling;
    }

    public final void moveButton$1(final float f, final float f2) {
        this.mSfVsyncFrameProvider.postFrameCallback(new Choreographer.FrameCallback() { // from class: com.android.systemui.accessibility.WindowMagnificationSettings$$ExternalSyntheticLambda2
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                WindowMagnificationSettings windowMagnificationSettings = WindowMagnificationSettings.this;
                float f3 = f;
                float f4 = f2;
                WindowManager.LayoutParams layoutParams = windowMagnificationSettings.mParams;
                layoutParams.x = (int) (layoutParams.x + f3);
                layoutParams.y = (int) (layoutParams.y + f4);
                windowMagnificationSettings.updateButtonViewLayoutIfNeeded();
            }
        });
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public final boolean onDrag(View view, float f, float f2) {
        moveButton$1(f, f2);
        return true;
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public final boolean onFinish() {
        if (!this.mSingleTapDetected) {
            showSettingPanel(true);
        }
        this.mSingleTapDetected = false;
        return true;
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public final void onSingleTap(View view) {
        this.mSingleTapDetected = true;
    }

    public void setDiagonalScrolling(boolean z) {
        this.mSecureSettings.putIntForUser("accessibility_allow_diagonal_scrolling", z ? 1 : 0, -2);
        MagnificationSettingsController magnificationSettingsController = MagnificationSettingsController.this;
        MagnificationSettingsController.Callback callback = magnificationSettingsController.mSettingsControllerCallback;
        Magnification.AnonymousClass4 anonymousClass4 = (Magnification.AnonymousClass4) callback;
        Magnification.this.mHandler.post(new Magnification$4$$ExternalSyntheticLambda0(anonymousClass4, magnificationSettingsController.mDisplayId, z, 1));
        this.mAllowDiagonalScrollingSwitch.announceForAccessibility(this.mContext.getResources().getString(R.string.accessibility_allow_diagonal_scrolling));
        this.mAllowDiagonalScrollingSwitch.setChecked(z);
    }

    public final void setScaleSeekbar(float f) {
        int i = (int) ((f - 1.0f) * this.mSeekBarMagnitude);
        if (i < 0) {
            i = 0;
        } else if (i > this.mZoomSeekbar.mSeekbar.getMax()) {
            i = this.mZoomSeekbar.mSeekbar.getMax();
        }
        this.mZoomSeekbar.setProgress(i);
    }

    public final void showSettingPanel(boolean z) {
        if (!this.mIsVisible) {
            updateUIControlsIfNeeded();
            setScaleSeekbar(this.mScale);
            if (z) {
                this.mDraggableWindowBounds.set(getDraggableWindowBounds$1());
                WindowManager.LayoutParams layoutParams = this.mParams;
                Rect rect = this.mDraggableWindowBounds;
                layoutParams.x = rect.right;
                layoutParams.y = rect.bottom;
            }
            SecureSettings secureSettings = this.mSecureSettings;
            int floatForUser = (int) ((secureSettings.getFloatForUser("accessibility_display_magnification_scale", 0.0f, -2) - 1.0f) / 1.0f);
            if (floatForUser < 0) {
                floatForUser = 0;
            }
            this.mZoomSeekbar.setProgress(floatForUser);
            this.mWindowManager.addView(this.mSettingView, this.mParams);
            secureSettings.registerContentObserverForUserSync("accessibility_magnification_capability", this.mMagnificationCapabilityObserver, -2);
            this.mSettingView.post(new WindowMagnificationSettings$$ExternalSyntheticLambda3(this, 2));
            this.mIsVisible = true;
            MagnificationSettingsController magnificationSettingsController = MagnificationSettingsController.this;
            Magnification.AnonymousClass4 anonymousClass4 = (Magnification.AnonymousClass4) magnificationSettingsController.mSettingsControllerCallback;
            Magnification.this.mHandler.post(new Magnification$4$$ExternalSyntheticLambda0(anonymousClass4, magnificationSettingsController.mDisplayId, true, 0));
            final Rect rect2 = new Rect(this.mWindowManager.getCurrentWindowMetrics().getBounds());
            this.mSettingView.post(new Runnable() { // from class: com.android.systemui.accessibility.WindowMagnificationSettings$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WindowMagnificationSettings windowMagnificationSettings = WindowMagnificationSettings.this;
                    Rect rect3 = rect2;
                    windowMagnificationSettings.mParams.x = (rect3.width() - windowMagnificationSettings.mSettingView.getWidth()) / 2;
                    if (windowMagnificationSettings.mSettingView.getWindowToken() != null) {
                        windowMagnificationSettings.mWindowManager.updateViewLayout(windowMagnificationSettings.mSettingView, windowMagnificationSettings.mParams);
                    }
                }
            });
            this.mSettingView.postDelayed(new WindowMagnificationSettings$$ExternalSyntheticLambda3(this, 1), 200L);
            if (z) {
                this.mSettingView.announceForAccessibility(this.mContext.getResources().getString(R.string.accessibility_magnification_settings_panel_description));
            }
        }
        this.mContext.registerReceiver(this.mScreenOffReceiver, new IntentFilter("android.intent.action.SCREEN_OFF"));
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("accessibility_display_magnification_scale"), false, this.mWindowScaleChangeObserver, -1);
    }

    public void updateButtonViewLayoutIfNeeded() {
        if (this.mIsVisible) {
            WindowManager.LayoutParams layoutParams = this.mParams;
            int i = layoutParams.x;
            Rect rect = this.mDraggableWindowBounds;
            layoutParams.x = MathUtils.constrain(i, rect.left, rect.right);
            WindowManager.LayoutParams layoutParams2 = this.mParams;
            int i2 = layoutParams2.y;
            Rect rect2 = this.mDraggableWindowBounds;
            layoutParams2.y = MathUtils.constrain(i2, rect2.top, rect2.bottom);
            this.mWindowManager.updateViewLayout(this.mSettingView, this.mParams);
        }
    }

    public final void updateUIControlsIfNeeded() {
        SecureSettings secureSettings = this.mSecureSettings;
        int intForUser = secureSettings.getIntForUser("accessibility_magnification_capability", 1, -2);
        if (Settings.System.getIntForUser(this.mContext.getContentResolver(), "accessibility_am_magnification_mode", 0, -2) == 1) {
            intForUser = 2;
        }
        if (intForUser == 1) {
            this.mFullScreenButton.setVisibility(0);
            this.mEditButton.setVisibility(4);
            this.mAllowDiagonalScrollingView.setVisibility(8);
        } else if (intForUser == 2) {
            this.mEditButton.setVisibility(0);
            this.mAllowDiagonalScrollingView.setVisibility(0);
            this.mFullScreenButton.setVisibility(8);
        } else if (intForUser == 3) {
            int intForUser2 = secureSettings.getIntForUser("accessibility_magnification_mode", secureSettings.getIntForUser("accessibility_magnification_capability", 1, -2) != 2 ? 1 : 2, -2);
            this.mFullScreenButton.setVisibility(0);
            if (intForUser2 == 1) {
                this.mEditButton.setVisibility(4);
                this.mAllowDiagonalScrollingView.setVisibility(8);
            } else {
                this.mEditButton.setVisibility(0);
                this.mAllowDiagonalScrollingView.setVisibility(0);
            }
        }
        this.mSettingView.requestLayout();
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public final void onStart() {
    }

    @Override // com.android.systemui.accessibility.MagnificationGestureDetector.OnGestureListener
    public final void onLongPressed(View view) {
    }
}
