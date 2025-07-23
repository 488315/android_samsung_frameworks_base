package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.pluginlock.PluginLockData;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.util.DeviceState;
import com.samsung.systemui.splugins.lockstar.LockStarValues;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class KeyguardSecBottomAreaView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy bottomDozeArea$delegate;
    public final StateFlowImpl configurationBasedDimensions;
    public int currentOrientation;
    public int currentSimState;
    public final Lazy disclosureIndicationText$delegate;
    public final Lazy displayMetrics$delegate;
    public RepeatWhenAttachedKt$repeatWhenAttached$1 disposableHandle;
    public EmergencyButton emergencyButton;
    public final Lazy indicationArea$delegate;
    public final Lazy indicationText$delegate;
    public boolean isKeyguardVisible;
    public int isLastVisibility;
    public boolean isNowBarVisible;
    public boolean isPluginLockOverlayView;
    public final Lazy leftShortcutArea$delegate;
    public final Lazy leftShortcutEffectview$delegate;
    public final Lazy leftView$delegate;
    public final Lazy mDisplay$delegate;
    public PluginLockData pluginLockData;
    public dagger.Lazy pluginLockStarManagerLazy;
    public final Lazy rightShortcutArea$delegate;
    public final Lazy rightShortcutEffectview$delegate;
    public final Lazy rightView$delegate;
    public KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 setUsimTextAreaVisibility;
    public final Lazy shortcutManager$delegate;
    public KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 showShortcutsIfPossible;
    public KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 updateLeftAffordanceIcon;
    public KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 updateRightAffordanceIcon;
    public final Lazy upperFPIndication$delegate;
    public KeyguardUsimTextView usimCarrierText;
    public LinearLayout usimTextArea;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public KeyguardSecBottomAreaView(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    public final KeyguardSecAffordanceView getLeftView() {
        return (KeyguardSecAffordanceView) this.leftView$delegate.getValue();
    }

    public final KeyguardSecAffordanceView getRightView() {
        return (KeyguardSecAffordanceView) this.rightView$delegate.getValue();
    }

    public final KeyguardShortcutManager getShortcutManager() {
        return (KeyguardShortcutManager) this.shortcutManager$delegate.getValue();
    }

    public final boolean isInEmergencyButtonArea(MotionEvent motionEvent) {
        LinearLayout linearLayout = this.usimTextArea;
        if (linearLayout == null || linearLayout.getVisibility() != 0) {
            Log.d("KeyguardSecBottomAreaView", "isInEmergencyButtonArea() - usimTextArea is not visible");
            return false;
        }
        EmergencyButton emergencyButton = this.emergencyButton;
        if (emergencyButton == null || emergencyButton.getVisibility() == 8) {
            return false;
        }
        Rect rect = new Rect();
        emergencyButton.getGlobalVisibleRect(rect);
        motionEvent.getClass();
        return rect.contains((int) motionEvent.getX(), (int) motionEvent.getY());
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (!((KeyguardFastBioUnlockController) Dependency.sDependency.getDependencyInner(KeyguardFastBioUnlockController.class)).isFastWakeAndUnlockMode()) {
            updateShortcutPosition();
            updateIndicationPosition();
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setClipChildren(false);
        setClipToPadding(false);
        KeyguardSecBottomAreaView$bindToViews$1 keyguardSecBottomAreaView$bindToViews$1 = new KeyguardSecBottomAreaView$bindToViews$1(this, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        this.disposableHandle = RepeatWhenAttachedKt.repeatWhenAttached(this, EmptyCoroutineContext.INSTANCE, keyguardSecBottomAreaView$bindToViews$1);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        if (!DeviceState.shouldEnableKeyguardScreenRotation(getContext()) || this.currentOrientation == configuration.orientation) {
            return;
        }
        updateLayout();
        this.currentOrientation = configuration.orientation;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        RepeatWhenAttachedKt$repeatWhenAttached$1 repeatWhenAttachedKt$repeatWhenAttached$1 = this.disposableHandle;
        if (repeatWhenAttachedKt$repeatWhenAttached$1 != null) {
            repeatWhenAttachedKt$repeatWhenAttached$1.dispose();
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        getRightView().init();
        getRightView().mRight = true;
        getLeftView().init();
        this.currentOrientation = getContext().getResources().getConfiguration().orientation;
        super.onFinishInflate();
        if (!CscRune.LOCKUI_BOTTOM_USIM_TEXT) {
            Log.d("KeyguardSecBottomAreaView", "onFinishInflate: USIM is null");
            return;
        }
        this.usimTextArea = (LinearLayout) findViewById(R.id.usim_text_area);
        this.emergencyButton = (EmergencyButton) findViewById(R.id.emergency_call_button);
        ViewStub viewStub = (ViewStub) findViewById(R.id.stub_keyguard_usim_text);
        if (viewStub != null) {
            viewStub.inflate();
            KeyguardUsimTextView keyguardUsimTextView = (KeyguardUsimTextView) requireViewById(R.id.keyguard_usim_carrier_text);
            this.usimCarrierText = keyguardUsimTextView;
            if (keyguardUsimTextView == null) {
                keyguardUsimTextView = null;
            }
            Log.d("KeyguardSecBottomAreaView", "mUsimCarrierText=" + keyguardUsimTextView);
        }
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        if (view != this || this.isLastVisibility == i) {
            return;
        }
        this.isLastVisibility = i;
        if (i == 0) {
            KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 = this.setUsimTextAreaVisibility;
            if (keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 != null) {
                keyguardSecBottomAreaViewController$$ExternalSyntheticLambda0.invoke();
            }
            KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 = this.showShortcutsIfPossible;
            if (keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02 != null) {
                keyguardSecBottomAreaViewController$$ExternalSyntheticLambda02.invoke();
            }
        }
    }

    public final void updateIndicationDimensions(ConfigurationBasedDimensions configurationBasedDimensions) {
        boolean z;
        int paddingBottom;
        PluginLockData pluginLockData;
        int dimensionPixelSize;
        LockStarValues lockStarValues;
        if (this.isKeyguardVisible) {
            Resources resources = getResources();
            int i = resources.getConfiguration().orientation;
            if (CscRune.LOCKUI_BOTTOM_USIM_TEXT) {
                KeyguardUsimTextView keyguardUsimTextView = this.usimCarrierText;
                if (keyguardUsimTextView == null) {
                    keyguardUsimTextView = null;
                }
                if (keyguardUsimTextView != null) {
                    if (i != 2 || ((TextView) this.disclosureIndicationText$delegate.getValue()).getText() == null) {
                        KeyguardUsimTextView keyguardUsimTextView2 = this.usimCarrierText;
                        if (keyguardUsimTextView2 == null) {
                            keyguardUsimTextView2 = null;
                        }
                        keyguardUsimTextView2.updateText(this.currentSimState);
                    } else {
                        KeyguardUsimTextView keyguardUsimTextView3 = this.usimCarrierText;
                        if (keyguardUsimTextView3 == null) {
                            keyguardUsimTextView3 = null;
                        }
                        keyguardUsimTextView3.setVisibility(8);
                    }
                }
            }
            KeyguardShortcutManager shortcutManager = getShortcutManager();
            int i2 = 0;
            int nowBarBottomMargin = shortcutManager != null ? shortcutManager.getNowBarBottomMargin(shortcutManager.context.getResources().getDisplayMetrics().heightPixels, shortcutManager.context.getResources().getConfiguration().orientation) : 0;
            KeyguardShortcutManager shortcutManager2 = getShortcutManager();
            configurationBasedDimensions.indicationAreaBottomMargin = resources.getDimensionPixelSize(R.dimen.keyguard_indication_margin_shortcut) + (shortcutManager2 != null ? shortcutManager2.getNowBarCollapsedHeight() : 0) + nowBarBottomMargin;
            if (!this.isPluginLockOverlayView && DeviceState.isInDisplayFpSensorPositionHigh() && ((Display) this.mDisplay$delegate.getValue()).getRotation() == 0) {
                configurationBasedDimensions.upperFPIndicationBottomMargin = DeviceState.getInDisplayFingerprintHeight();
                ((KeyguardIndicationTextView) this.upperFPIndication$delegate.getValue()).setVisibility(0);
            } else {
                ((KeyguardIndicationTextView) this.upperFPIndication$delegate.getValue()).setVisibility(8);
            }
            int i3 = configurationBasedDimensions.shortcutSideMargin;
            configurationBasedDimensions.indicationAreaSideMargin = i3;
            if (i == 2) {
                configurationBasedDimensions.indicationAreaSideMargin = resources.getDimensionPixelSize(R.dimen.keyguard_indication_area_side_padding) + configurationBasedDimensions.buttonSizePx.getWidth() + i3;
            }
            if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && !DeviceState.isInDisplayFpSensorPositionHigh() && ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isFingerprintOptionEnabled() && (((Display) this.mDisplay$delegate.getValue()).getRotation() == 0 || ((Display) this.mDisplay$delegate.getValue()).getRotation() == 2)) {
                configurationBasedDimensions.indicationAreaBottomMargin = DeviceState.isTablet() ? resources.getDimensionPixelSize(R.dimen.keyguard_indication_text_bottom_margin_tab) + DeviceState.getInDisplayFingerprintHeight() : DeviceState.getInDisplayFingerprintHeight();
                z = false;
            } else {
                z = true;
            }
            dagger.Lazy lazy = this.pluginLockStarManagerLazy;
            PluginLockStarManager pluginLockStarManager = lazy != null ? (PluginLockStarManager) lazy.get() : null;
            if (pluginLockStarManager == null || !pluginLockStarManager.isLockStarEnabled() || (lockStarValues = pluginLockStarManager.getLockStarValues()) == null) {
                PluginLockData pluginLockData2 = this.pluginLockData;
                paddingBottom = (pluginLockData2 == null || !pluginLockData2.isAvailable() || (pluginLockData = this.pluginLockData) == null) ? -1 : pluginLockData.getPaddingBottom(5);
            } else {
                paddingBottom = lockStarValues.getIndicationBottomMargin();
            }
            if (paddingBottom >= 0) {
                configurationBasedDimensions.indicationAreaBottomMargin = paddingBottom;
            }
            int i4 = getContext().getResources().getDisplayMetrics().widthPixels - (configurationBasedDimensions.indicationAreaSideMargin * 2);
            int lineCount = new StaticLayout(((TextView) this.indicationText$delegate.getValue()).getText(), ((TextView) this.indicationText$delegate.getValue()).getPaint(), i4 < 0 ? 0 : i4, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false).getLineCount();
            if (LsRune.LOCKUI_SUB_DISPLAY_LOCK && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened && i == 2) {
                dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.keyguard_usim_text_margin_bottom_land_opened);
            } else if (i == 1) {
                int lineHeight = ((TextView) this.indicationText$delegate.getValue()).getLineHeight();
                if (z) {
                    dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.keyguard_usim_text_margin_bottom);
                } else {
                    dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.keyguard_usim_text_margin_bottom);
                    if (lineCount == 1) {
                        i2 = lineHeight * 2;
                    } else if (lineCount == 2) {
                        i2 = lineHeight;
                    }
                    if (this.isNowBarVisible) {
                        KeyguardShortcutManager shortcutManager3 = getShortcutManager();
                        dimensionPixelSize = shortcutManager3.context.getResources().getDimensionPixelSize(R.dimen.now_bar_bottom_margin) + shortcutManager3.getNowBarCollapsedHeight() + dimensionPixelSize + i2;
                    }
                }
            } else {
                dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.keyguard_usim_text_margin_bottom_land);
            }
            configurationBasedDimensions.usimTextAreaBottomMargin = dimensionPixelSize;
            configurationBasedDimensions.isOverlayView = this.isPluginLockOverlayView;
        }
    }

    public final void updateIndicationPosition() {
        StateFlowImpl stateFlowImpl = this.configurationBasedDimensions;
        ConfigurationBasedDimensions copy$default = ConfigurationBasedDimensions.copy$default((ConfigurationBasedDimensions) stateFlowImpl.getValue());
        updateIndicationDimensions(copy$default);
        stateFlowImpl.updateState(null, copy$default);
    }

    public final void updateLayout() {
        try {
            ((Display) this.mDisplay$delegate.getValue()).getRealMetrics((DisplayMetrics) this.displayMetrics$delegate.getValue());
            StateFlowImpl stateFlowImpl = this.configurationBasedDimensions;
            ConfigurationBasedDimensions copy$default = ConfigurationBasedDimensions.copy$default((ConfigurationBasedDimensions) stateFlowImpl.getValue());
            updateShortcutDimensions(copy$default);
            updateIndicationDimensions(copy$default);
            stateFlowImpl.updateState(null, copy$default);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void updateShortcutDimensions(ConfigurationBasedDimensions configurationBasedDimensions) {
        if (this.isKeyguardVisible) {
            KeyguardShortcutManager shortcutManager = getShortcutManager();
            int shortcutIconSizeValue = shortcutManager != null ? shortcutManager.getShortcutIconSizeValue(shortcutManager.isNowBarVisible) : 0;
            configurationBasedDimensions.buttonSizePx = new Size(shortcutIconSizeValue, shortcutIconSizeValue);
            KeyguardShortcutManager shortcutManager2 = getShortcutManager();
            configurationBasedDimensions.shortcutBottomMargin = shortcutManager2 != null ? shortcutManager2.getShortcutBottomMargin(shortcutManager2.isNowBarVisible) : 0;
            KeyguardShortcutManager shortcutManager3 = getShortcutManager();
            configurationBasedDimensions.shortcutSideMargin = shortcutManager3 != null ? shortcutManager3.getShortcutSideMargin() : 0;
            configurationBasedDimensions.isOverlayView = this.isPluginLockOverlayView;
        }
    }

    public final void updateShortcutPosition() {
        StateFlowImpl stateFlowImpl = this.configurationBasedDimensions;
        ConfigurationBasedDimensions copy$default = ConfigurationBasedDimensions.copy$default((ConfigurationBasedDimensions) stateFlowImpl.getValue());
        updateShortcutDimensions(copy$default);
        stateFlowImpl.updateState(null, copy$default);
    }

    public KeyguardSecBottomAreaView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public KeyguardSecBottomAreaView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ KeyguardSecBottomAreaView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public KeyguardSecBottomAreaView(final Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        final int i3 = 0;
        this.leftView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i3) {
                    case 0:
                        int i4 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button);
                    case 1:
                        int i5 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button_effectview);
                    case 2:
                        int i6 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text);
                    case 3:
                        int i7 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text_bottom);
                    case 4:
                        int i8 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (FrameLayout) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_bottom_doze_area);
                    case 5:
                        int i9 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardIndicationTextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_upper_fingerprint_indication);
                    case 6:
                        int i10 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button);
                    case 7:
                        int i11 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.left_shortcut_area);
                    case 8:
                        int i12 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.right_shortcut_area);
                    case 9:
                        int i13 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (ViewGroup) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_area);
                    case 10:
                        int i14 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button_effectview);
                    default:
                        int i15 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((WindowManager) ((Context) obj).getSystemService("window")).getDefaultDisplay();
                }
            }
        });
        final int i4 = 6;
        this.rightView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i4) {
                    case 0:
                        int i42 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button);
                    case 1:
                        int i5 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button_effectview);
                    case 2:
                        int i6 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text);
                    case 3:
                        int i7 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text_bottom);
                    case 4:
                        int i8 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (FrameLayout) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_bottom_doze_area);
                    case 5:
                        int i9 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardIndicationTextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_upper_fingerprint_indication);
                    case 6:
                        int i10 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button);
                    case 7:
                        int i11 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.left_shortcut_area);
                    case 8:
                        int i12 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.right_shortcut_area);
                    case 9:
                        int i13 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (ViewGroup) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_area);
                    case 10:
                        int i14 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button_effectview);
                    default:
                        int i15 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((WindowManager) ((Context) obj).getSystemService("window")).getDefaultDisplay();
                }
            }
        });
        final int i5 = 7;
        this.leftShortcutArea$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i5) {
                    case 0:
                        int i42 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button);
                    case 1:
                        int i52 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button_effectview);
                    case 2:
                        int i6 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text);
                    case 3:
                        int i7 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text_bottom);
                    case 4:
                        int i8 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (FrameLayout) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_bottom_doze_area);
                    case 5:
                        int i9 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardIndicationTextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_upper_fingerprint_indication);
                    case 6:
                        int i10 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button);
                    case 7:
                        int i11 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.left_shortcut_area);
                    case 8:
                        int i12 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.right_shortcut_area);
                    case 9:
                        int i13 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (ViewGroup) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_area);
                    case 10:
                        int i14 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button_effectview);
                    default:
                        int i15 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((WindowManager) ((Context) obj).getSystemService("window")).getDefaultDisplay();
                }
            }
        });
        final int i6 = 8;
        this.rightShortcutArea$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i6) {
                    case 0:
                        int i42 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button);
                    case 1:
                        int i52 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button_effectview);
                    case 2:
                        int i62 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text);
                    case 3:
                        int i7 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text_bottom);
                    case 4:
                        int i8 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (FrameLayout) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_bottom_doze_area);
                    case 5:
                        int i9 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardIndicationTextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_upper_fingerprint_indication);
                    case 6:
                        int i10 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button);
                    case 7:
                        int i11 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.left_shortcut_area);
                    case 8:
                        int i12 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.right_shortcut_area);
                    case 9:
                        int i13 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (ViewGroup) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_area);
                    case 10:
                        int i14 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button_effectview);
                    default:
                        int i15 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((WindowManager) ((Context) obj).getSystemService("window")).getDefaultDisplay();
                }
            }
        });
        final int i7 = 9;
        this.indicationArea$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i7) {
                    case 0:
                        int i42 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button);
                    case 1:
                        int i52 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button_effectview);
                    case 2:
                        int i62 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text);
                    case 3:
                        int i72 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text_bottom);
                    case 4:
                        int i8 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (FrameLayout) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_bottom_doze_area);
                    case 5:
                        int i9 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardIndicationTextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_upper_fingerprint_indication);
                    case 6:
                        int i10 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button);
                    case 7:
                        int i11 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.left_shortcut_area);
                    case 8:
                        int i12 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.right_shortcut_area);
                    case 9:
                        int i13 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (ViewGroup) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_area);
                    case 10:
                        int i14 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button_effectview);
                    default:
                        int i15 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((WindowManager) ((Context) obj).getSystemService("window")).getDefaultDisplay();
                }
            }
        });
        final int i8 = 10;
        this.leftShortcutEffectview$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i8) {
                    case 0:
                        int i42 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button);
                    case 1:
                        int i52 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button_effectview);
                    case 2:
                        int i62 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text);
                    case 3:
                        int i72 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text_bottom);
                    case 4:
                        int i82 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (FrameLayout) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_bottom_doze_area);
                    case 5:
                        int i9 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardIndicationTextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_upper_fingerprint_indication);
                    case 6:
                        int i10 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button);
                    case 7:
                        int i11 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.left_shortcut_area);
                    case 8:
                        int i12 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.right_shortcut_area);
                    case 9:
                        int i13 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (ViewGroup) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_area);
                    case 10:
                        int i14 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button_effectview);
                    default:
                        int i15 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((WindowManager) ((Context) obj).getSystemService("window")).getDefaultDisplay();
                }
            }
        });
        final int i9 = 1;
        this.rightShortcutEffectview$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i9) {
                    case 0:
                        int i42 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button);
                    case 1:
                        int i52 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button_effectview);
                    case 2:
                        int i62 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text);
                    case 3:
                        int i72 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text_bottom);
                    case 4:
                        int i82 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (FrameLayout) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_bottom_doze_area);
                    case 5:
                        int i92 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardIndicationTextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_upper_fingerprint_indication);
                    case 6:
                        int i10 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button);
                    case 7:
                        int i11 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.left_shortcut_area);
                    case 8:
                        int i12 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.right_shortcut_area);
                    case 9:
                        int i13 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (ViewGroup) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_area);
                    case 10:
                        int i14 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button_effectview);
                    default:
                        int i15 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((WindowManager) ((Context) obj).getSystemService("window")).getDefaultDisplay();
                }
            }
        });
        final int i10 = 2;
        this.indicationText$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i10) {
                    case 0:
                        int i42 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button);
                    case 1:
                        int i52 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button_effectview);
                    case 2:
                        int i62 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text);
                    case 3:
                        int i72 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text_bottom);
                    case 4:
                        int i82 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (FrameLayout) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_bottom_doze_area);
                    case 5:
                        int i92 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardIndicationTextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_upper_fingerprint_indication);
                    case 6:
                        int i102 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button);
                    case 7:
                        int i11 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.left_shortcut_area);
                    case 8:
                        int i12 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.right_shortcut_area);
                    case 9:
                        int i13 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (ViewGroup) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_area);
                    case 10:
                        int i14 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button_effectview);
                    default:
                        int i15 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((WindowManager) ((Context) obj).getSystemService("window")).getDefaultDisplay();
                }
            }
        });
        final int i11 = 3;
        this.disclosureIndicationText$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i11) {
                    case 0:
                        int i42 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button);
                    case 1:
                        int i52 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button_effectview);
                    case 2:
                        int i62 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text);
                    case 3:
                        int i72 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text_bottom);
                    case 4:
                        int i82 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (FrameLayout) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_bottom_doze_area);
                    case 5:
                        int i92 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardIndicationTextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_upper_fingerprint_indication);
                    case 6:
                        int i102 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button);
                    case 7:
                        int i112 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.left_shortcut_area);
                    case 8:
                        int i12 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.right_shortcut_area);
                    case 9:
                        int i13 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (ViewGroup) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_area);
                    case 10:
                        int i14 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button_effectview);
                    default:
                        int i15 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((WindowManager) ((Context) obj).getSystemService("window")).getDefaultDisplay();
                }
            }
        });
        final int i12 = 4;
        this.bottomDozeArea$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i12) {
                    case 0:
                        int i42 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button);
                    case 1:
                        int i52 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button_effectview);
                    case 2:
                        int i62 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text);
                    case 3:
                        int i72 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text_bottom);
                    case 4:
                        int i82 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (FrameLayout) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_bottom_doze_area);
                    case 5:
                        int i92 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardIndicationTextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_upper_fingerprint_indication);
                    case 6:
                        int i102 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button);
                    case 7:
                        int i112 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.left_shortcut_area);
                    case 8:
                        int i122 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.right_shortcut_area);
                    case 9:
                        int i13 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (ViewGroup) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_area);
                    case 10:
                        int i14 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button_effectview);
                    default:
                        int i15 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((WindowManager) ((Context) obj).getSystemService("window")).getDefaultDisplay();
                }
            }
        });
        final int i13 = 5;
        this.upperFPIndication$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i13) {
                    case 0:
                        int i42 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button);
                    case 1:
                        int i52 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button_effectview);
                    case 2:
                        int i62 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text);
                    case 3:
                        int i72 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text_bottom);
                    case 4:
                        int i82 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (FrameLayout) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_bottom_doze_area);
                    case 5:
                        int i92 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardIndicationTextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_upper_fingerprint_indication);
                    case 6:
                        int i102 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button);
                    case 7:
                        int i112 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.left_shortcut_area);
                    case 8:
                        int i122 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.right_shortcut_area);
                    case 9:
                        int i132 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (ViewGroup) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_area);
                    case 10:
                        int i14 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button_effectview);
                    default:
                        int i15 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((WindowManager) ((Context) obj).getSystemService("window")).getDefaultDisplay();
                }
            }
        });
        final int i14 = 0;
        this.shortcutManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i14) {
                    case 0:
                        int i15 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class);
                    default:
                        int i16 = KeyguardSecBottomAreaView.$r8$clinit;
                        return new DisplayMetrics();
                }
            }
        });
        ConfigurationBasedDimensions configurationBasedDimensions = new ConfigurationBasedDimensions(getResources().getDimensionPixelOffset(R.dimen.default_burn_in_prevention_offset), getResources().getDimensionPixelOffset(R.dimen.keyguard_indication_area_padding), 0, 0, 0, null, 0, 0, false, 508, null);
        updateShortcutDimensions(configurationBasedDimensions);
        updateIndicationDimensions(configurationBasedDimensions);
        this.configurationBasedDimensions = StateFlowKt.MutableStateFlow(configurationBasedDimensions);
        final int i15 = 11;
        this.mDisplay$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = context;
                switch (i15) {
                    case 0:
                        int i42 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button);
                    case 1:
                        int i52 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button_effectview);
                    case 2:
                        int i62 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text);
                    case 3:
                        int i72 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (TextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_text_bottom);
                    case 4:
                        int i82 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (FrameLayout) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_bottom_doze_area);
                    case 5:
                        int i92 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardIndicationTextView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_upper_fingerprint_indication);
                    case 6:
                        int i102 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardSecAffordanceView) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.end_button);
                    case 7:
                        int i112 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.left_shortcut_area);
                    case 8:
                        int i122 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.right_shortcut_area);
                    case 9:
                        int i132 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (ViewGroup) ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.keyguard_indication_area);
                    case 10:
                        int i142 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((KeyguardSecBottomAreaView) obj).requireViewById(R.id.start_button_effectview);
                    default:
                        int i152 = KeyguardSecBottomAreaView.$r8$clinit;
                        return ((WindowManager) ((Context) obj).getSystemService("window")).getDefaultDisplay();
                }
            }
        });
        final int i16 = 1;
        this.displayMetrics$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i16) {
                    case 0:
                        int i152 = KeyguardSecBottomAreaView.$r8$clinit;
                        return (KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class);
                    default:
                        int i162 = KeyguardSecBottomAreaView.$r8$clinit;
                        return new DisplayMetrics();
                }
            }
        });
        this.currentSimState = 1;
        this.isLastVisibility = 8;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ConfigurationBasedDimensions {
        public Size buttonSizePx;
        public final int defaultBurnInPreventionYOffsetPx;
        public int indicationAreaBottomMargin;
        public int indicationAreaSideMargin;
        public boolean isOverlayView;
        public int shortcutBottomMargin;
        public int shortcutSideMargin;
        public int upperFPIndicationBottomMargin;
        public int usimTextAreaBottomMargin;

        public ConfigurationBasedDimensions(int i, int i2, int i3, int i4, int i5, Size size, int i6, int i7, boolean z) {
            this.defaultBurnInPreventionYOffsetPx = i;
            this.indicationAreaSideMargin = i2;
            this.indicationAreaBottomMargin = i3;
            this.upperFPIndicationBottomMargin = i4;
            this.usimTextAreaBottomMargin = i5;
            this.buttonSizePx = size;
            this.shortcutSideMargin = i6;
            this.shortcutBottomMargin = i7;
            this.isOverlayView = z;
        }

        public static ConfigurationBasedDimensions copy$default(ConfigurationBasedDimensions configurationBasedDimensions) {
            int i = configurationBasedDimensions.defaultBurnInPreventionYOffsetPx;
            int i2 = configurationBasedDimensions.indicationAreaSideMargin;
            int i3 = configurationBasedDimensions.indicationAreaBottomMargin;
            int i4 = configurationBasedDimensions.upperFPIndicationBottomMargin;
            int i5 = configurationBasedDimensions.usimTextAreaBottomMargin;
            Size size = configurationBasedDimensions.buttonSizePx;
            int i6 = configurationBasedDimensions.shortcutSideMargin;
            int i7 = configurationBasedDimensions.shortcutBottomMargin;
            boolean z = configurationBasedDimensions.isOverlayView;
            configurationBasedDimensions.getClass();
            return new ConfigurationBasedDimensions(i, i2, i3, i4, i5, size, i6, i7, z);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ConfigurationBasedDimensions)) {
                return false;
            }
            ConfigurationBasedDimensions configurationBasedDimensions = (ConfigurationBasedDimensions) obj;
            return this.defaultBurnInPreventionYOffsetPx == configurationBasedDimensions.defaultBurnInPreventionYOffsetPx && this.indicationAreaSideMargin == configurationBasedDimensions.indicationAreaSideMargin && this.indicationAreaBottomMargin == configurationBasedDimensions.indicationAreaBottomMargin && this.upperFPIndicationBottomMargin == configurationBasedDimensions.upperFPIndicationBottomMargin && this.usimTextAreaBottomMargin == configurationBasedDimensions.usimTextAreaBottomMargin && Intrinsics.areEqual(this.buttonSizePx, configurationBasedDimensions.buttonSizePx) && this.shortcutSideMargin == configurationBasedDimensions.shortcutSideMargin && this.shortcutBottomMargin == configurationBasedDimensions.shortcutBottomMargin && this.isOverlayView == configurationBasedDimensions.isOverlayView;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isOverlayView) + ReorderTile$$ExternalSyntheticOutline0.m(this.shortcutBottomMargin, ReorderTile$$ExternalSyntheticOutline0.m(this.shortcutSideMargin, (this.buttonSizePx.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.usimTextAreaBottomMargin, ReorderTile$$ExternalSyntheticOutline0.m(this.upperFPIndicationBottomMargin, ReorderTile$$ExternalSyntheticOutline0.m(this.indicationAreaBottomMargin, ReorderTile$$ExternalSyntheticOutline0.m(this.indicationAreaSideMargin, Integer.hashCode(this.defaultBurnInPreventionYOffsetPx) * 31, 31), 31), 31), 31)) * 31, 31), 31);
        }

        public final String toString() {
            int i = this.indicationAreaSideMargin;
            int i2 = this.indicationAreaBottomMargin;
            int i3 = this.upperFPIndicationBottomMargin;
            int i4 = this.usimTextAreaBottomMargin;
            Size size = this.buttonSizePx;
            int i5 = this.shortcutSideMargin;
            int i6 = this.shortcutBottomMargin;
            boolean z = this.isOverlayView;
            StringBuilder sb = new StringBuilder("ConfigurationBasedDimensions(defaultBurnInPreventionYOffsetPx=");
            ViewPager$$ExternalSyntheticOutline0.m(sb, this.defaultBurnInPreventionYOffsetPx, ", indicationAreaSideMargin=", i, ", indicationAreaBottomMargin=");
            ViewPager$$ExternalSyntheticOutline0.m(sb, i2, ", upperFPIndicationBottomMargin=", i3, ", usimTextAreaBottomMargin=");
            sb.append(i4);
            sb.append(", buttonSizePx=");
            sb.append(size);
            sb.append(", shortcutSideMargin=");
            ViewPager$$ExternalSyntheticOutline0.m(sb, i5, ", shortcutBottomMargin=", i6, ", isOverlayView=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, z, ")");
        }

        public /* synthetic */ ConfigurationBasedDimensions(int i, int i2, int i3, int i4, int i5, Size size, int i6, int i7, boolean z, int i8, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, (i8 & 2) != 0 ? 0 : i2, (i8 & 4) != 0 ? 0 : i3, (i8 & 8) != 0 ? 0 : i4, (i8 & 16) != 0 ? 0 : i5, (i8 & 32) != 0 ? new Size(0, 0) : size, (i8 & 64) != 0 ? 0 : i6, (i8 & 128) != 0 ? 0 : i7, (i8 & 256) != 0 ? false : z);
        }
    }
}
