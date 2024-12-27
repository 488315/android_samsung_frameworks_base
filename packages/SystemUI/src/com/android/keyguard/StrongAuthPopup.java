package com.android.keyguard;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.EditText;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;

public final class StrongAuthPopup extends AlertDialog implements View.OnApplyWindowInsetsListener {
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass3 mConfigurationListener;
    public final Context mContext;
    public int mCurrentOrientation;
    public final Handler mHandler;
    public int mImeHeight;
    public boolean mIsSIPVisible;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final EditText mPasswordEntry;
    public int mRotation;
    public final StrongAuthPopup$$ExternalSyntheticLambda1 mRunnable;
    public final KeyguardSecurityModel.SecurityMode mSecurityMode;
    public final AnonymousClass2 mTextWatcher;

    /* renamed from: com.android.keyguard.StrongAuthPopup$1, reason: invalid class name */
    class AnonymousClass1 extends KeyguardUpdateMonitorCallback {
        public AnonymousClass1() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
            if (z) {
                return;
            }
            new Handler(Looper.getMainLooper()).post(new StrongAuthPopup$1$$ExternalSyntheticLambda0(this, 0));
        }
    }

    /* renamed from: com.android.keyguard.StrongAuthPopup$4, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass4 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode = iArr;
            try {
                iArr[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public StrongAuthPopup(android.content.Context r10, com.android.keyguard.KeyguardSecurityModel.SecurityMode r11, android.widget.EditText r12) {
        /*
            r9 = this;
            com.android.keyguard.KeyguardSecurityModel$SecurityMode r0 = com.android.keyguard.KeyguardSecurityModel.SecurityMode.Password
            if (r11 != r0) goto L8
            r1 = 2132019430(0x7f1408e6, float:1.9677195E38)
            goto Lb
        L8:
            r1 = 2132019436(0x7f1408ec, float:1.9677207E38)
        Lb:
            r9.<init>(r10, r1)
            android.os.Handler r1 = new android.os.Handler
            android.os.Looper r2 = android.os.Looper.getMainLooper()
            r1.<init>(r2)
            r9.mHandler = r1
            com.android.keyguard.StrongAuthPopup$1 r1 = new com.android.keyguard.StrongAuthPopup$1
            r1.<init>()
            r9.mKeyguardUpdateMonitorCallback = r1
            com.android.keyguard.StrongAuthPopup$2 r2 = new com.android.keyguard.StrongAuthPopup$2
            r2.<init>()
            r9.mTextWatcher = r2
            com.android.keyguard.StrongAuthPopup$$ExternalSyntheticLambda1 r3 = new com.android.keyguard.StrongAuthPopup$$ExternalSyntheticLambda1
            r4 = 0
            r3.<init>(r9, r4)
            r9.mRunnable = r3
            r3 = 1
            r9.mCurrentOrientation = r3
            com.android.systemui.Dependency r4 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.statusbar.policy.ConfigurationController> r5 = com.android.systemui.statusbar.policy.ConfigurationController.class
            java.lang.Object r4 = r4.getDependencyInner(r5)
            com.android.systemui.statusbar.policy.ConfigurationController r4 = (com.android.systemui.statusbar.policy.ConfigurationController) r4
            r9.mConfigurationController = r4
            com.android.keyguard.StrongAuthPopup$3 r5 = new com.android.keyguard.StrongAuthPopup$3
            r5.<init>()
            r9.mConfigurationListener = r5
            r6 = 0
            r9.mImeHeight = r6
            r9.mContext = r10
            r9.mSecurityMode = r11
            r9.mPasswordEntry = r12
            java.lang.Class<com.android.keyguard.KeyguardUpdateMonitor> r7 = com.android.keyguard.KeyguardUpdateMonitor.class
            com.android.systemui.Dependency r8 = com.android.systemui.Dependency.sDependency
            java.lang.Object r7 = r8.getDependencyInner(r7)
            com.android.keyguard.KeyguardUpdateMonitor r7 = (com.android.keyguard.KeyguardUpdateMonitor) r7
            r9.mKeyguardUpdateMonitor = r7
            boolean r8 = com.android.systemui.util.DeviceState.shouldEnableKeyguardScreenRotation(r10)
            if (r8 == 0) goto L75
            android.content.Context r8 = r9.getContext()
            android.content.res.Resources r8 = r8.getResources()
            android.content.res.Configuration r8 = r8.getConfiguration()
            int r8 = r8.orientation
            r9.mCurrentOrientation = r8
            com.android.systemui.statusbar.phone.ConfigurationControllerImpl r4 = (com.android.systemui.statusbar.phone.ConfigurationControllerImpl) r4
            r4.addCallback(r5)
        L75:
            r7.registerCallback(r1)
            if (r11 != r0) goto L7f
            if (r12 == 0) goto L7f
            r12.addTextChangedListener(r2)
        L7f:
            android.view.Window r12 = r9.getWindow()
            r0 = 2009(0x7d9, float:2.815E-42)
            r12.setType(r0)
            r0 = 786472(0xc0028, float:1.102082E-39)
            r12.addFlags(r0)
            android.graphics.drawable.ColorDrawable r0 = new android.graphics.drawable.ColorDrawable
            r0.<init>(r6)
            r12.setBackgroundDrawable(r0)
            android.view.WindowManager$LayoutParams r0 = r12.getAttributes()
            android.view.WindowManager$LayoutParams r1 = r12.getAttributes()
            int r1 = r1.getFitInsetsTypes()
            int r2 = android.view.WindowInsets.Type.statusBars()
            int r2 = ~r2
            r1 = r1 & r2
            r0.setFitInsetsTypes(r1)
            r0 = 2
            r12.clearFlags(r0)
            r9.setCanceledOnTouchOutside(r3)
            r12.setDecorFitsSystemWindows(r6)
            android.view.View r12 = r12.getDecorView()
            r12.setOnApplyWindowInsetsListener(r9)
            r12 = 2131558803(0x7f0d0193, float:1.8742932E38)
            r0 = 0
            android.view.View r12 = android.view.View.inflate(r10, r12, r0)
            r0 = 2131364865(0x7f0a0c01, float:1.834958E38)
            android.view.View r0 = r12.findViewById(r0)
            android.widget.TextView r0 = (android.widget.TextView) r0
            com.android.keyguard.KeyguardTextBuilder r10 = com.android.keyguard.KeyguardTextBuilder.getInstance(r10)
            java.lang.String r10 = r10.getStrongAuthTimeOutMessage(r11)
            r0.setText(r10)
            r9.setView(r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.StrongAuthPopup.<init>(android.content.Context, com.android.keyguard.KeyguardSecurityModel$SecurityMode, android.widget.EditText):void");
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        EditText editText;
        if (DeviceState.shouldEnableKeyguardScreenRotation(this.mContext)) {
            ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        }
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        if (this.mSecurityMode == KeyguardSecurityModel.SecurityMode.Password && (editText = this.mPasswordEntry) != null) {
            editText.removeTextChangedListener(this.mTextWatcher);
        }
        super.dismiss();
    }

    public final int getNavigationBarSize() {
        if (LsRune.SECURITY_NAVBAR_ENABLED) {
            return this.mContext.getResources().getDimensionPixelSize(R.dimen.resolver_empty_state_container_padding_top);
        }
        return 0;
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        int rotation = DeviceState.getRotation(this.mContext.getResources().getConfiguration().windowConfiguration.getRotation());
        int[] iArr = SecurityUtils.sImeHeight;
        char c = 1;
        if (rotation != 1 && rotation != 3) {
            c = 0;
        }
        int i = iArr[c];
        this.mIsSIPVisible = windowInsets.isVisible(WindowInsets.Type.ime());
        if (this.mImeHeight != i) {
            this.mImeHeight = i;
            this.mHandler.removeCallbacks(this.mRunnable);
            this.mHandler.post(this.mRunnable);
        }
        return WindowInsets.CONSUMED;
    }

    public final void updatePopup() {
        int calculateLandscapeViewWidth;
        int dimensionPixelSize;
        int dimensionPixelSize2;
        int dimensionPixelSize3;
        int navigationBarSize;
        int i;
        int i2;
        int dimensionPixelSize4;
        int i3;
        int rotation = DeviceState.getRotation(this.mContext.getResources().getConfiguration().windowConfiguration.getRotation());
        if (this.mHandler.hasCallbacks(this.mRunnable)) {
            return;
        }
        this.mRotation = rotation;
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        Resources resources = this.mContext.getResources();
        int width = resources.getConfiguration().windowConfiguration.getBounds().width();
        int dimensionPixelSize5 = resources.getDimensionPixelSize(com.android.systemui.R.dimen.kg_strong_auth_timeout_popup_margin);
        boolean z = LsRune.SECURITY_BIOMETRICS_TABLET;
        char c = 1;
        if (z) {
            calculateLandscapeViewWidth = resources.getDimensionPixelSize(com.android.systemui.R.dimen.kg_strong_auth_timeout_popup_size_tablet);
        } else if (this.mKeyguardUpdateMonitor.isDualDisplayPolicyAllowed()) {
            calculateLandscapeViewWidth = SecurityUtils.getMainSecurityViewFlipperSize(this.mContext, this.mSecurityMode == KeyguardSecurityModel.SecurityMode.Password);
        } else {
            calculateLandscapeViewWidth = (rotation == 1 || rotation == 3) ? SecurityUtils.calculateLandscapeViewWidth(width, this.mContext) - dimensionPixelSize5 : width - (dimensionPixelSize5 * 2);
        }
        attributes.width = calculateLandscapeViewWidth;
        attributes.height = -2;
        attributes.layoutInDisplayCutoutMode = 1;
        if (!(rotation == 1 || rotation == 3) || z || this.mKeyguardUpdateMonitor.isDualDisplayPolicyAllowed()) {
            attributes.gravity = 80;
            attributes.x = 0;
            boolean isTablet = DeviceType.isTablet();
            Resources resources2 = this.mContext.getResources();
            int inDisplayFingerprintHeight = (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning()) ? DeviceState.getInDisplayFingerprintHeight() : 0;
            int i4 = AnonymousClass4.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[this.mSecurityMode.ordinal()];
            int i5 = com.android.systemui.R.dimen.keyguard_bottom_area_emergency_button_area_min_height;
            if (i4 != 1) {
                if (i4 == 2) {
                    if (this.mIsSIPVisible) {
                        int i6 = this.mRotation;
                        int[] iArr = SecurityUtils.sImeHeight;
                        if (i6 != 1 && i6 != 3) {
                            c = 0;
                        }
                        i2 = iArr[c];
                    } else {
                        i2 = 0;
                    }
                    int dimensionPixelSize6 = resources2.getDimensionPixelSize(isTablet ? com.android.systemui.R.dimen.kg_security_password_input_box_margin_bottom_tablet : com.android.systemui.R.dimen.kg_password_container_margin_bottom) + resources2.getDimensionPixelSize(isTablet ? com.android.systemui.R.dimen.kg_security_input_box_height_tablet : com.android.systemui.R.dimen.kg_security_input_box_height) + resources2.getDimensionPixelSize(isTablet ? com.android.systemui.R.dimen.kg_security_input_box_margin_top_tablet : com.android.systemui.R.dimen.kg_security_input_box_margin_top);
                    if (inDisplayFingerprintHeight != 0) {
                        dimensionPixelSize4 = inDisplayFingerprintHeight - getNavigationBarSize();
                    } else {
                        if (isTablet) {
                            i5 = com.android.systemui.R.dimen.keyguard_bottom_area_emergency_button_area_min_height_tablet;
                        }
                        dimensionPixelSize4 = resources2.getDimensionPixelSize(i5) + resources2.getDimensionPixelSize(isTablet ? com.android.systemui.R.dimen.kg_password_eca_margin_bottom_tablet : com.android.systemui.R.dimen.kg_password_eca_margin_bottom);
                    }
                    r9 = (i2 != 0 ? i2 - getNavigationBarSize() : 0) + dimensionPixelSize6 + dimensionPixelSize4;
                } else if (i4 == 3) {
                    dimensionPixelSize = resources2.getDimensionPixelSize(isTablet ? com.android.systemui.R.dimen.kg_pattern_lock_pattern_view_margin_bottom_tablet : com.android.systemui.R.dimen.kg_pattern_lock_pattern_view_margin_bottom) + resources2.getDimensionPixelSize(isTablet ? com.android.systemui.R.dimen.kg_pattern_lock_pattern_view_height_tablet : com.android.systemui.R.dimen.kg_pattern_lock_pattern_view_height);
                    if (inDisplayFingerprintHeight != 0) {
                        navigationBarSize = getNavigationBarSize();
                        i = inDisplayFingerprintHeight - navigationBarSize;
                    } else {
                        if (isTablet) {
                            i5 = com.android.systemui.R.dimen.keyguard_bottom_area_emergency_button_area_min_height_tablet;
                        }
                        dimensionPixelSize2 = resources2.getDimensionPixelSize(i5);
                        dimensionPixelSize3 = resources2.getDimensionPixelSize(isTablet ? com.android.systemui.R.dimen.kg_pattern_eca_margin_bottom_tablet : com.android.systemui.R.dimen.kg_pattern_eca_margin_bottom);
                        i = dimensionPixelSize3 + dimensionPixelSize2;
                    }
                }
                attributes.y = r9;
            } else {
                dimensionPixelSize = resources2.getDimensionPixelSize(isTablet ? com.android.systemui.R.dimen.kg_pin_container_margin_bottom_tablet : com.android.systemui.R.dimen.kg_pin_container_margin_bottom) + (isTablet ? SecurityUtils.getTabletPINContainerHeight(this.mContext) : (LsRune.SECURITY_SUB_DISPLAY_LOCK && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) ? SecurityUtils.getFoldPINContainerHeight(this.mContext) : SecurityUtils.getPINContainerHeight(this.mContext));
                if (inDisplayFingerprintHeight != 0) {
                    navigationBarSize = getNavigationBarSize();
                    i = inDisplayFingerprintHeight - navigationBarSize;
                } else {
                    if (isTablet) {
                        i5 = com.android.systemui.R.dimen.keyguard_bottom_area_emergency_button_area_min_height_tablet;
                    }
                    dimensionPixelSize2 = resources2.getDimensionPixelSize(i5);
                    dimensionPixelSize3 = resources2.getDimensionPixelSize(isTablet ? com.android.systemui.R.dimen.kg_pin_eca_margin_bottom_tablet : com.android.systemui.R.dimen.kg_pin_eca_margin_bottom);
                    i = dimensionPixelSize3 + dimensionPixelSize2;
                }
            }
            r9 = dimensionPixelSize + i;
            attributes.y = r9;
        } else {
            if (this.mSecurityMode == KeyguardSecurityModel.SecurityMode.Password) {
                if (this.mIsSIPVisible) {
                    i3 = SecurityUtils.sImeHeight[(rotation == 1 || rotation == 3) ? (char) 1 : (char) 0];
                } else {
                    i3 = 0;
                }
                attributes.gravity = i3 == 0 ? 16 : 80;
                attributes.y = i3 != 0 ? StrongAuthPopup$$ExternalSyntheticOutline0.m(this.mContext, com.android.systemui.R.dimen.kg_strong_auth_timeout_popup_margin, i3) : 0;
            } else {
                attributes.gravity = 16;
                attributes.y = 0;
            }
            attributes.gravity |= 3;
            attributes.x = rotation == 1 ? getNavigationBarSize() : 0;
        }
        window.setAttributes(attributes);
    }

    /* renamed from: com.android.keyguard.StrongAuthPopup$2, reason: invalid class name */
    public final class AnonymousClass2 implements TextWatcher {
        public AnonymousClass2() {
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
            StrongAuthPopup.this.mHandler.post(new StrongAuthPopup$1$$ExternalSyntheticLambda0(this, 1));
        }

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }
}
