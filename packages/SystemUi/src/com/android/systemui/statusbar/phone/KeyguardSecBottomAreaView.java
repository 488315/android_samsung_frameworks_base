package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextUtils;
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
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.SecLockIconViewController;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.p009ui.binder.KeyguardBottomAreaViewBinder;
import com.android.systemui.keyguard.p009ui.binder.KeyguardBottomAreaViewBinder$bind$2;
import com.android.systemui.keyguard.p009ui.binder.KeyguardSecBottomAreaViewBinder;
import com.android.systemui.keyguard.p009ui.binder.KeyguardSecBottomAreaViewBinder$bind$1;
import com.android.systemui.keyguard.p009ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.pluginlock.PluginLockData;
import com.android.systemui.pluginlock.PluginLockDataImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
    public boolean isPluginLockOverlayView;
    public final Lazy leftShortcutArea$delegate;
    public final Lazy leftView$delegate;
    public final Lazy mDisplay$delegate;
    public final Lazy rightShortcutArea$delegate;
    public final Lazy rightView$delegate;
    public Function0 setUsimTextAreaVisibility;
    public Function0 showShortcutsIfPossible;
    public Function0 updateLeftAffordanceIcon;
    public Function0 updateRightAffordanceIcon;
    public final Lazy upperFPIndication$delegate;
    public KeyguardUsimTextView usimCarrierText;
    public LinearLayout usimTextArea;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public final Function0 getUpdateLeftAffordanceIcon() {
        Function0 function0 = this.updateLeftAffordanceIcon;
        if (function0 != null) {
            return function0;
        }
        return null;
    }

    public final Function0 getUpdateRightAffordanceIcon() {
        Function0 function0 = this.updateRightAffordanceIcon;
        if (function0 != null) {
            return function0;
        }
        return null;
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaView, android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaView
    public final void init(KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, FalsingManager falsingManager, SecLockIconViewController secLockIconViewController, KeyguardBottomAreaView.MessageDisplayer messageDisplayer, VibratorHelper vibratorHelper, ActivityStarter activityStarter) {
        if (this.binding != null) {
            getBinding().destroy();
        }
        this.binding = KeyguardSecBottomAreaViewBinder.bind(this, keyguardBottomAreaViewModel);
        this.lockIconViewController = secLockIconViewController;
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
        EmergencyButton emergencyButton = this.emergencyButton;
        if (emergencyButton == null) {
            return false;
        }
        Rect rect = new Rect();
        emergencyButton.getGlobalVisibleRect(rect);
        Intrinsics.checkNotNull(motionEvent);
        return rect.contains((int) motionEvent.getX(), (int) motionEvent.getY());
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardBottomAreaView, android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (!((KeyguardFastBioUnlockController) Dependency.get(KeyguardFastBioUnlockController.class)).isFastWakeAndUnlockMode()) {
            KeyguardSecBottomAreaViewBinder$bind$1 keyguardSecBottomAreaViewBinder$bind$1 = (KeyguardSecBottomAreaViewBinder$bind$1) getBinding();
            StateFlowImpl stateFlowImpl = (StateFlowImpl) keyguardSecBottomAreaViewBinder$bind$1.$configurationBasedDimensions;
            KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions copy$default = KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions.copy$default((KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions) stateFlowImpl.getValue());
            keyguardSecBottomAreaViewBinder$bind$1.$view.updateShortcutPosition(copy$default);
            stateFlowImpl.setValue(copy$default);
            ((KeyguardSecBottomAreaViewBinder$bind$1) getBinding()).updateIndicationPosition();
        }
        super.onApplyWindowInsets(windowInsets);
        return windowInsets;
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
        if (!LsRune.LOCKUI_BOTTOM_USIM_TEXT) {
            Log.d("KeyguardSecBottomAreaView", "onFinishInflate: USIM is null");
            return;
        }
        this.usimTextArea = (LinearLayout) findViewById(R.id.usim_text_area);
        this.emergencyButton = (EmergencyButton) findViewById(R.id.emergency_call_button);
        ViewStub viewStub = (ViewStub) findViewById(R.id.stub_keyguard_usim_text);
        if (viewStub != null) {
            viewStub.inflate();
            KeyguardUsimTextView keyguardUsimTextView = (KeyguardUsimTextView) findViewById(R.id.keyguard_usim_carrier_text);
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
    public final void setBinding(KeyguardBottomAreaViewBinder$bind$2 keyguardBottomAreaViewBinder$bind$2) {
        this.binding = keyguardBottomAreaViewBinder$bind$2;
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0207  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateIndicationPosition(KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions configurationBasedDimensions) {
        int width;
        int dimensionPixelSize;
        int i;
        PluginLockData pluginLockData;
        int dimensionPixelSize2;
        if (this.isKeyguardVisible) {
            Resources resources = getResources();
            int i2 = resources.getConfiguration().orientation;
            int lineCount = new StaticLayout(((TextView) this.indicationText$delegate.getValue()).getText(), ((TextView) this.indicationText$delegate.getValue()).getPaint(), resources.getDimensionPixelSize(i2 == 2 ? R.dimen.keyguard_indication_default_width_land : R.dimen.keyguard_indication_default_width), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false).getLineCount();
            boolean z = ((TextView) this.indicationText$delegate.getValue()).getEllipsize() == TextUtils.TruncateAt.MARQUEE || lineCount == 1;
            boolean z2 = LsRune.LOCKUI_BOTTOM_USIM_TEXT;
            if (z2) {
                if (i2 != 2 || ((TextView) this.disclosureIndicationText$delegate.getValue()).getText() == null) {
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
            Point point = DeviceState.sDisplaySize;
            configurationBasedDimensions.indicationAreaBottomMargin = DeviceType.isTablet() ? resources.getDimensionPixelSize(R.dimen.keyguard_indication_1_line_bottom_margin_tablet) : z ? resources.getDimensionPixelSize(R.dimen.keyguard_indication_1_line_bottom_margin) : lineCount != 2 ? lineCount != 3 ? lineCount != 4 ? resources.getDimensionPixelSize(R.dimen.keyguard_indication_4_line_bottom_margin) : resources.getDimensionPixelSize(R.dimen.keyguard_indication_4_line_bottom_margin) : resources.getDimensionPixelSize(R.dimen.keyguard_indication_3_line_bottom_margin) : resources.getDimensionPixelSize(R.dimen.keyguard_indication_2_line_bottom_margin);
            if (!this.isPluginLockOverlayView && DeviceState.isInDisplayFpSensorPositionHigh() && getDisplay().getRotation() == 0) {
                configurationBasedDimensions.upperFPIndicationBottomMargin = DeviceState.sInDisplayFingerprintHeight;
                ((KeyguardIndicationTextView) this.upperFPIndication$delegate.getValue()).setVisibility(0);
            } else {
                ((KeyguardIndicationTextView) this.upperFPIndication$delegate.getValue()).setVisibility(8);
            }
            if (DeviceType.isTablet()) {
                width = configurationBasedDimensions.buttonSizePx.getWidth() + configurationBasedDimensions.shortcutSideMargin;
                dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.keyguard_indication_text_single_line_side_padding_tablet);
            } else {
                if (z) {
                    i = configurationBasedDimensions.shortcutSideMargin;
                    configurationBasedDimensions.indicationAreaSideMargin = i;
                    if (i2 == 2) {
                        configurationBasedDimensions.indicationAreaBottomMargin = z ? resources.getDimensionPixelSize(R.dimen.keyguard_indication_1_line_bottom_margin_land) : lineCount != 2 ? lineCount != 3 ? lineCount != 4 ? resources.getDimensionPixelSize(R.dimen.keyguard_indication_4_line_bottom_margin_land) : resources.getDimensionPixelSize(R.dimen.keyguard_indication_4_line_bottom_margin_land) : resources.getDimensionPixelSize(R.dimen.keyguard_indication_3_line_bottom_margin_land) : resources.getDimensionPixelSize(R.dimen.keyguard_indication_2_line_bottom_margin_land);
                        if (z2) {
                            KeyguardUsimTextView keyguardUsimTextView3 = this.usimCarrierText;
                            if (keyguardUsimTextView3 == null) {
                                keyguardUsimTextView3 = null;
                            }
                            if (keyguardUsimTextView3.getVisibility() == 0) {
                                KeyguardUsimTextView keyguardUsimTextView4 = this.usimCarrierText;
                                if ((keyguardUsimTextView4 != null ? keyguardUsimTextView4 : null).getText() != null) {
                                    configurationBasedDimensions.indicationAreaBottomMargin = resources.getDimensionPixelSize(R.dimen.keyguard_shrotcut_default_side_margin_land_usimtext);
                                }
                            }
                        }
                        if (DeviceType.isTablet()) {
                            configurationBasedDimensions.indicationAreaBottomMargin = resources.getDimensionPixelSize(((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isStandalone() ? R.dimen.keyguard_shrotcut_default_bottom_margin_tablet_dex : R.dimen.keyguard_shrotcut_default_bottom_margin_tablet_land);
                        }
                        configurationBasedDimensions.indicationAreaSideMargin = resources.getDimensionPixelSize(R.dimen.keyguard_indication_area_side_padding) + configurationBasedDimensions.buttonSizePx.getWidth() + configurationBasedDimensions.shortcutSideMargin;
                    }
                    if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).isFingerprintOptionEnabled() && ((getDisplay().getRotation() == 0 || getDisplay().getRotation() == 2) && !DeviceState.isInDisplayFpSensorPositionHigh())) {
                        configurationBasedDimensions.indicationAreaBottomMargin = resources.getDimensionPixelSize(R.dimen.keyguard_indication_margin_bottom_fingerprint_low) + DeviceState.sInDisplayFingerprintHeight;
                    }
                    pluginLockData = this.pluginLockData;
                    if (pluginLockData != null && ((PluginLockDataImpl) pluginLockData).isAvailable()) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("LockStar is available bottom : ", configurationBasedDimensions.indicationAreaBottomMargin, "KeyguardSecBottomAreaView");
                        PluginLockData pluginLockData2 = this.pluginLockData;
                        Intrinsics.checkNotNull(pluginLockData2);
                        PluginLockDataImpl pluginLockDataImpl = (PluginLockDataImpl) pluginLockData2;
                        configurationBasedDimensions.indicationAreaBottomMargin = pluginLockDataImpl.mData != null ? -1 : pluginLockDataImpl.isLandscape() ? pluginLockDataImpl.mData.getIndicationData().getHelpTextData().getPaddingBottomLand().intValue() : pluginLockDataImpl.mData.getIndicationData().getHelpTextData().getPaddingBottom().intValue();
                    }
                    if (!LsRune.LOCKUI_SUB_DISPLAY_LOCK && ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened && i2 == 2) {
                        dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.keyguard_usim_text_margin_bottom_land_opened);
                    } else {
                        dimensionPixelSize2 = resources.getDimensionPixelSize(i2 != 1 ? R.dimen.keyguard_usim_text_margin_bottom : R.dimen.keyguard_usim_text_margin_bottom_land);
                    }
                    configurationBasedDimensions.usimTextAreaBottomMargin = dimensionPixelSize2;
                    configurationBasedDimensions.isOverlayView = this.isPluginLockOverlayView;
                }
                width = configurationBasedDimensions.buttonSizePx.getWidth() + configurationBasedDimensions.shortcutSideMargin;
                dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.keyguard_indication_text_multi_line_side_padding);
            }
            i = dimensionPixelSize + width;
            configurationBasedDimensions.indicationAreaSideMargin = i;
            if (i2 == 2) {
            }
            if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                configurationBasedDimensions.indicationAreaBottomMargin = resources.getDimensionPixelSize(R.dimen.keyguard_indication_margin_bottom_fingerprint_low) + DeviceState.sInDisplayFingerprintHeight;
            }
            pluginLockData = this.pluginLockData;
            if (pluginLockData != null) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("LockStar is available bottom : ", configurationBasedDimensions.indicationAreaBottomMargin, "KeyguardSecBottomAreaView");
                PluginLockData pluginLockData22 = this.pluginLockData;
                Intrinsics.checkNotNull(pluginLockData22);
                PluginLockDataImpl pluginLockDataImpl2 = (PluginLockDataImpl) pluginLockData22;
                configurationBasedDimensions.indicationAreaBottomMargin = pluginLockDataImpl2.mData != null ? -1 : pluginLockDataImpl2.isLandscape() ? pluginLockDataImpl2.mData.getIndicationData().getHelpTextData().getPaddingBottomLand().intValue() : pluginLockDataImpl2.mData.getIndicationData().getHelpTextData().getPaddingBottom().intValue();
            }
            if (!LsRune.LOCKUI_SUB_DISPLAY_LOCK) {
            }
            dimensionPixelSize2 = resources.getDimensionPixelSize(i2 != 1 ? R.dimen.keyguard_usim_text_margin_bottom : R.dimen.keyguard_usim_text_margin_bottom_land);
            configurationBasedDimensions.usimTextAreaBottomMargin = dimensionPixelSize2;
            configurationBasedDimensions.isOverlayView = this.isPluginLockOverlayView;
        }
    }

    public final void updateLayout() {
        try {
            ((Display) this.mDisplay$delegate.getValue()).getRealMetrics((DisplayMetrics) this.displayMetrics$delegate.getValue());
            ((KeyguardSecBottomAreaViewBinder$bind$1) getBinding()).onConfigurationChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void updateShortcutPosition(KeyguardSecBottomAreaViewBinder.ConfigurationBasedDimensions configurationBasedDimensions) {
        if (this.isKeyguardVisible) {
            Resources resources = getResources();
            int i = resources.getConfiguration().orientation;
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.keyguard_shrotcut_default_size);
            if (!LsRune.LOCKUI_SUB_DISPLAY_LOCK) {
                Point point = DeviceState.sDisplaySize;
                if (DeviceType.isTablet()) {
                    configurationBasedDimensions.shortcutBottomMargin = resources.getDimensionPixelSize(R.dimen.keyguard_shrotcut_default_bottom_margin_tablet);
                    configurationBasedDimensions.shortcutSideMargin = resources.getDimensionPixelSize(i == 1 ? R.dimen.keyguard_shrotcut_default_side_margin_tablet : R.dimen.keyguard_shrotcut_default_side_margin_land_tablet);
                    dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.keyguard_shrotcut_default_size_tablet);
                } else if (i == 1) {
                    int i2 = ((DisplayMetrics) this.displayMetrics$delegate.getValue()).heightPixels;
                    configurationBasedDimensions.shortcutSideMargin = (int) (((DisplayMetrics) this.displayMetrics$delegate.getValue()).widthPixels * 0.067d);
                    configurationBasedDimensions.shortcutBottomMargin = (int) (i2 * 0.024d);
                } else {
                    configurationBasedDimensions.shortcutSideMargin = resources.getDimensionPixelSize(R.dimen.keyguard_shrotcut_default_side_margin_land);
                    configurationBasedDimensions.shortcutBottomMargin = resources.getDimensionPixelSize(R.dimen.keyguard_shrotcut_default_bottom_margin);
                }
            } else if (((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
                configurationBasedDimensions.shortcutSideMargin = i == 1 ? resources.getDimensionPixelSize(R.dimen.keyguard_shrotcut_default_side_margin_sub_opend) : resources.getDimensionPixelSize(R.dimen.keyguard_shrotcut_default_side_margin_sub_opend_land);
                configurationBasedDimensions.shortcutBottomMargin = resources.getDimensionPixelSize(R.dimen.keyguard_shrotcut_default_bottom_margin_sub_opend);
            } else {
                configurationBasedDimensions.shortcutSideMargin = i == 1 ? resources.getDimensionPixelSize(R.dimen.keyguard_shrotcut_default_side_margin_sub_closed) : resources.getDimensionPixelSize(R.dimen.keyguard_shrotcut_default_side_margin_sub_closed_land);
                configurationBasedDimensions.shortcutBottomMargin = resources.getDimensionPixelSize(R.dimen.keyguard_shrotcut_default_bottom_margin_sub_closed);
            }
            configurationBasedDimensions.buttonSizePx = new Size(dimensionPixelSize, dimensionPixelSize);
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
