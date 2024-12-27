package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.keyguard.EmergencyButton;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.SecLockIconViewController;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$4;
import com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder;
import com.android.systemui.keyguard.ui.binder.KeyguardSecBottomAreaViewBinder$bind$1;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.pluginlock.PluginLockData;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DeviceState;
import com.samsung.systemui.splugins.lockstar.LockStarValues;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class KeyguardSecBottomAreaView extends KeyguardBottomAreaView {
    public final KeyguardSecBottomAreaViewBinder binder;
    public KeyguardBottomAreaViewBinder.Binding binding;
    public final Lazy bottomDozeArea$delegate;
    public int currentOrientation;
    public int currentSimState;
    public final Lazy disclosureIndicationText$delegate;
    public final Lazy displayMetrics$delegate;
    public EmergencyButton emergencyButton;
    public final Lazy indicationText$delegate;
    public boolean isKeyguardVisible;
    public int isLastVisibility;
    public boolean isNowBarVisible;
    public boolean isPluginLockOverlayView;
    public final Lazy leftShortcutArea$delegate;
    public final Lazy leftShortcutEffectview$delegate;
    public final Lazy leftView$delegate;
    public final Lazy mDisplay$delegate;
    public final Lazy rightShortcutArea$delegate;
    public final Lazy rightShortcutEffectview$delegate;
    public final Lazy rightView$delegate;
    public Function0 setUsimTextAreaVisibility;
    public Function0 showShortcutsIfPossible;
    public Function0 updateLeftAffordanceIcon;
    public Function0 updateRightAffordanceIcon;
    public final Lazy upperFPIndication$delegate;
    public KeyguardUsimTextView usimCarrierText;
    public LinearLayout usimTextArea;

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

    public KeyguardSecBottomAreaView(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaView
    public final KeyguardBottomAreaViewBinder getBinder() {
        return this.binder;
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaView
    public final KeyguardBottomAreaViewBinder.Binding getBinding() {
        KeyguardBottomAreaViewBinder.Binding binding = this.binding;
        if (binding != null) {
            return binding;
        }
        return null;
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaView
    public final ImageView getLeftView() {
        return (KeyguardSecAffordanceView) this.leftView$delegate.getValue();
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaView, android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaView
    public final void init(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, FalsingManager falsingManager, SecLockIconViewController secLockIconViewController, KeyguardBottomAreaView.MessageDisplayer messageDisplayer, VibratorHelper vibratorHelper, ActivityStarter activityStarter, dagger.Lazy lazy) {
        KeyguardBottomAreaViewBinder.Binding binding = this.binding;
        if (binding != null) {
            if (binding == null) {
                binding = null;
            }
            binding.destroy();
        }
        this.binding = KeyguardSecBottomAreaViewBinder.bind(this, keyguardBottomAreaViewModel);
        this.lockIconViewController = secLockIconViewController;
        this.pluginLockStarManagerLazy = lazy;
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaView
    public final void initEmergencyButton(EmergencyButtonController.Factory factory) {
        EmergencyButton emergencyButton = this.emergencyButton;
        if (emergencyButton != null) {
            Intrinsics.checkNotNull(factory);
            factory.create(emergencyButton).init();
        }
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaView
    public final boolean isInEmergencyButtonArea(MotionEvent motionEvent) {
        LinearLayout linearLayout = this.usimTextArea;
        if (linearLayout == null || linearLayout.getVisibility() != 0) {
            Log.d("KeyguardSecBottomAreaView", "isInEmergencyButtonArea() - usimTextArea is not visible");
            return false;
        }
        EmergencyButton emergencyButton = this.emergencyButton;
        if (emergencyButton == null) {
            return false;
        }
        Rect rect = new Rect();
        emergencyButton.getGlobalVisibleRect(rect);
        Intrinsics.checkNotNull(motionEvent);
        return rect.contains((int) motionEvent.getX(), (int) motionEvent.getY());
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (!((KeyguardFastBioUnlockController) Dependency.sDependency.getDependencyInner(KeyguardFastBioUnlockController.class)).isFastWakeAndUnlockMode()) {
            KeyguardBottomAreaViewBinder.Binding binding = this.binding;
            if (binding == null) {
                binding = null;
            }
            ((KeyguardSecBottomAreaViewBinder$bind$1) binding).updateShortcutPosition();
            KeyguardBottomAreaViewBinder.Binding binding2 = this.binding;
            ((KeyguardSecBottomAreaViewBinder$bind$1) (binding2 != null ? binding2 : null)).updateIndicationPosition();
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        if (!DeviceState.shouldEnableKeyguardScreenRotation(getContext()) || this.currentOrientation == configuration.orientation) {
            return;
        }
        updateLayout();
        this.currentOrientation = configuration.orientation;
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaView, android.view.View
    public final void onFinishInflate() {
        getRightView().init();
        getRightView().mRight = true;
        ((KeyguardSecAffordanceView) this.leftView$delegate.getValue()).init();
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
            Function0 function0 = this.setUsimTextAreaVisibility;
            if (function0 != null) {
                if (function0 == null) {
                    function0 = null;
                }
                function0.invoke();
            }
            Function0 function02 = this.showShortcutsIfPossible;
            if (function02 != null) {
                (function02 != null ? function02 : null).invoke();
            }
        }
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaView
    public final void setAllChildEnabled(View view, boolean z) {
        view.setEnabled(z);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                setAllChildEnabled(viewGroup.getChildAt(i), z);
            }
        }
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaView
    public final void setBinding(KeyguardBottomAreaViewBinder$bind$4 keyguardBottomAreaViewBinder$bind$4) {
        this.binding = keyguardBottomAreaViewBinder$bind$4;
    }

    public final void updateIndicationPosition(KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions configurationBasedDimensions) {
        int paddingBottom;
        PluginLockData pluginLockData;
        int dimensionPixelSize;
        LockStarValues lockStarValues;
        if (this.isKeyguardVisible) {
            Resources resources = getResources();
            int i = resources.getConfiguration().orientation;
            if (CscRune.LOCKUI_BOTTOM_USIM_TEXT) {
                if (i != 2 || ((TextView) this.disclosureIndicationText$delegate.getValue()).getText() == null) {
                    KeyguardUsimTextView keyguardUsimTextView = this.usimCarrierText;
                    if (keyguardUsimTextView == null) {
                        keyguardUsimTextView = null;
                    }
                    keyguardUsimTextView.updateText(this.currentSimState);
                } else {
                    KeyguardUsimTextView keyguardUsimTextView2 = this.usimCarrierText;
                    if (keyguardUsimTextView2 == null) {
                        keyguardUsimTextView2 = null;
                    }
                    keyguardUsimTextView2.setVisibility(8);
                }
            }
            int i2 = getResources().getConfiguration().orientation;
            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            int i3 = displayMetrics.widthPixels;
            int i4 = displayMetrics.heightPixels;
            configurationBasedDimensions.indicationAreaBottomMargin = resources.getDimensionPixelSize(R.dimen.keyguard_indication_margin_shortcut) + ((int) ((i2 == 1 ? i3 : i4) * 0.138d)) + ((int) (i4 * (LsRune.KEYGUARD_SUB_DISPLAY_LOCK ? 0.051d : i2 == 1 ? DeviceState.isTablet() ? 0.059d : 0.0387d : DeviceState.isTablet() ? 0.069d : 0.053d)));
            if (!this.isPluginLockOverlayView && DeviceState.isInDisplayFpSensorPositionHigh() && ((Display) this.mDisplay$delegate.getValue()).getRotation() == 0) {
                configurationBasedDimensions.upperFPIndicationBottomMargin = DeviceState.getInDisplayFingerprintHeight();
                ((KeyguardIndicationTextView) this.upperFPIndication$delegate.getValue()).setVisibility(0);
            } else {
                ((KeyguardIndicationTextView) this.upperFPIndication$delegate.getValue()).setVisibility(8);
            }
            configurationBasedDimensions.indicationAreaSideMargin = configurationBasedDimensions.shortcutSideMargin;
            if (i == 2) {
                if (CscRune.LOCKUI_BOTTOM_USIM_TEXT) {
                    KeyguardUsimTextView keyguardUsimTextView3 = this.usimCarrierText;
                    if (keyguardUsimTextView3 == null) {
                        keyguardUsimTextView3 = null;
                    }
                    if (keyguardUsimTextView3.getVisibility() == 0) {
                        KeyguardUsimTextView keyguardUsimTextView4 = this.usimCarrierText;
                        if (keyguardUsimTextView4 == null) {
                            keyguardUsimTextView4 = null;
                        }
                        if (keyguardUsimTextView4.getText() != null) {
                            configurationBasedDimensions.indicationAreaBottomMargin = resources.getDimensionPixelSize(R.dimen.keyguard_shrotcut_default_side_margin_land_usimtext);
                        }
                    }
                }
                if (DeviceState.isTablet()) {
                    configurationBasedDimensions.indicationAreaBottomMargin = resources.getDimensionPixelSize(((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).isStandalone() ? R.dimen.keyguard_shrotcut_default_bottom_margin_tablet_dex : R.dimen.keyguard_shrotcut_default_bottom_margin_tablet_land);
                }
                configurationBasedDimensions.indicationAreaSideMargin = resources.getDimensionPixelSize(R.dimen.keyguard_indication_area_side_padding) + configurationBasedDimensions.buttonSizePx.getWidth() + configurationBasedDimensions.shortcutSideMargin;
            }
            if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && ((((Display) this.mDisplay$delegate.getValue()).getRotation() == 0 || ((Display) this.mDisplay$delegate.getValue()).getRotation() == 2) && !DeviceState.isInDisplayFpSensorPositionHigh())) {
                configurationBasedDimensions.indicationAreaBottomMargin = DeviceState.getInDisplayFingerprintHeight() + (this.isNowBarVisible ? resources.getDimensionPixelSize(R.dimen.keyguard_indication_margin_bottom_fingerprint_low_with_nowbar) : resources.getDimensionPixelSize(R.dimen.keyguard_indication_margin_bottom_fingerprint_low));
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
            if (LsRune.LOCKUI_SUB_DISPLAY_LOCK && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened && i == 2) {
                dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.keyguard_usim_text_margin_bottom_land_opened);
            } else {
                dimensionPixelSize = resources.getDimensionPixelSize(i == 1 ? R.dimen.keyguard_usim_text_margin_bottom : R.dimen.keyguard_usim_text_margin_bottom_land);
            }
            configurationBasedDimensions.usimTextAreaBottomMargin = dimensionPixelSize;
            configurationBasedDimensions.isOverlayView = this.isPluginLockOverlayView;
        }
    }

    public final void updateLayout() {
        try {
            ((Display) this.mDisplay$delegate.getValue()).getRealMetrics((DisplayMetrics) this.displayMetrics$delegate.getValue());
            KeyguardBottomAreaViewBinder.Binding binding = this.binding;
            if (binding == null) {
                binding = null;
            }
            ((KeyguardSecBottomAreaViewBinder$bind$1) binding).onConfigurationChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void updateShortcutPosition(KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions configurationBasedDimensions) {
        double d;
        if (this.isKeyguardVisible) {
            int i = getResources().getConfiguration().orientation;
            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            int i2 = displayMetrics.widthPixels;
            int i3 = displayMetrics.heightPixels;
            boolean z = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
            double d2 = z ? this.isNowBarVisible ? 0.057d : 0.051d : i == 1 ? DeviceState.isTablet() ? this.isNowBarVisible ? 0.064d : 0.059d : this.isNowBarVisible ? 0.045d : 0.0387d : DeviceState.isTablet() ? 0.069d : this.isNowBarVisible ? 0.078d : 0.053d;
            int i4 = (int) ((i == 1 ? i2 : i3) * (this.isNowBarVisible ? 0.111d : 0.138d));
            double d3 = 0.136d;
            if (DeviceState.isTablet() || (z && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened)) {
                if (i == 1) {
                    d = i2;
                    d3 = 0.128d;
                } else {
                    d = i2;
                }
                configurationBasedDimensions.shortcutSideMargin = (int) (d * d3);
                i4 = (int) ((i == 1 ? i2 : i3) * 0.075d);
            } else {
                configurationBasedDimensions.shortcutSideMargin = (int) (i == 1 ? i2 * 0.066d : i2 * 0.136d);
            }
            configurationBasedDimensions.shortcutBottomMargin = (int) (i3 * d2);
            configurationBasedDimensions.buttonSizePx = new Size(i4, i4);
            configurationBasedDimensions.isOverlayView = this.isPluginLockOverlayView;
        }
    }

    public KeyguardSecBottomAreaView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaView
    public final KeyguardSecAffordanceView getRightView() {
        return (KeyguardSecAffordanceView) this.rightView$delegate.getValue();
    }

    public KeyguardSecBottomAreaView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public /* synthetic */ KeyguardSecBottomAreaView(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public KeyguardSecBottomAreaView(final Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.leftView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$leftView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ImageView leftView;
                leftView = super/*com.android.systemui.statusbar.phone.KeyguardBottomAreaView*/.getLeftView();
                return (KeyguardSecAffordanceView) leftView;
            }
        });
        this.rightView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$rightView$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ImageView rightView;
                rightView = super/*com.android.systemui.statusbar.phone.KeyguardBottomAreaView*/.getRightView();
                return (KeyguardSecAffordanceView) rightView;
            }
        });
        this.leftShortcutArea$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$leftShortcutArea$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return KeyguardSecBottomAreaView.this.requireViewById(R.id.left_shortcut_area);
            }
        });
        this.rightShortcutArea$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$rightShortcutArea$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return KeyguardSecBottomAreaView.this.requireViewById(R.id.right_shortcut_area);
            }
        });
        this.leftShortcutEffectview$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$leftShortcutEffectview$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return KeyguardSecBottomAreaView.this.requireViewById(R.id.start_button_effectview);
            }
        });
        this.rightShortcutEffectview$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$rightShortcutEffectview$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return KeyguardSecBottomAreaView.this.requireViewById(R.id.end_button_effectview);
            }
        });
        this.indicationText$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$indicationText$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (TextView) KeyguardSecBottomAreaView.this.requireViewById(R.id.keyguard_indication_text);
            }
        });
        this.disclosureIndicationText$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$disclosureIndicationText$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (TextView) KeyguardSecBottomAreaView.this.requireViewById(R.id.keyguard_indication_text_bottom);
            }
        });
        this.bottomDozeArea$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$bottomDozeArea$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (FrameLayout) KeyguardSecBottomAreaView.this.requireViewById(R.id.keyguard_bottom_doze_area);
            }
        });
        this.upperFPIndication$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$upperFPIndication$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (KeyguardIndicationTextView) KeyguardSecBottomAreaView.this.requireViewById(R.id.keyguard_upper_fingerprint_indication);
            }
        });
        this.mDisplay$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$mDisplay$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
            }
        });
        this.displayMetrics$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaView$displayMetrics$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new DisplayMetrics();
            }
        });
        this.currentSimState = 1;
        this.isLastVisibility = 8;
        this.binder = KeyguardSecBottomAreaViewBinder.INSTANCE;
    }
}
