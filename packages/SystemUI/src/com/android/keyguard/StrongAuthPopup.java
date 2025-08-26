package com.android.keyguard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;

/* loaded from: classes.dex */
public class StrongAuthPopup extends AlertDialog implements View.OnApplyWindowInsetsListener {
    public static final /* synthetic */ int $r8$clinit = 0;
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

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.keyguard.StrongAuthPopup$3, java.lang.Object] */
    public StrongAuthPopup(Context context, KeyguardSecurityModel.SecurityMode securityMode, EditText editText) {
        KeyguardSecurityModel.SecurityMode securityMode2 = KeyguardSecurityModel.SecurityMode.Password;
        super(context, securityMode == securityMode2 ? R.style.keyguard_password_strong_auth_popup_style : R.style.keyguard_strong_auth_popup_style);
        this.mHandler = new Handler(Looper.getMainLooper());
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mKeyguardUpdateMonitorCallback = anonymousClass1;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        this.mTextWatcher = anonymousClass2;
        this.mRunnable = new StrongAuthPopup$$ExternalSyntheticLambda1(this, 0);
        this.mCurrentOrientation = 1;
        ConfigurationController configurationController = (ConfigurationController) Dependency.sDependency.getDependencyInner(ConfigurationController.class);
        this.mConfigurationController = configurationController;
        ?? r5 = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.StrongAuthPopup.3
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onOrientationChanged(int i) {
                StrongAuthPopup strongAuthPopup = StrongAuthPopup.this;
                if (strongAuthPopup.mCurrentOrientation != i) {
                    strongAuthPopup.mCurrentOrientation = i;
                    strongAuthPopup.mHandler.post(strongAuthPopup.mRunnable);
                }
            }
        };
        this.mConfigurationListener = r5;
        this.mImeHeight = 0;
        this.mContext = context;
        this.mSecurityMode = securityMode;
        this.mPasswordEntry = editText;
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        if (DeviceState.shouldEnableKeyguardScreenRotation(context)) {
            this.mCurrentOrientation = getContext().getResources().getConfiguration().orientation;
            ((ConfigurationControllerImpl) configurationController).addCallback(r5);
        }
        keyguardUpdateMonitor.registerCallback(anonymousClass1);
        if (securityMode == securityMode2 && editText != null) {
            editText.addTextChangedListener(anonymousClass2);
        }
        Window window = getWindow();
        window.setType(2009);
        window.addFlags(786472);
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.getAttributes().setFitInsetsTypes(window.getAttributes().getFitInsetsTypes() & (~WindowInsets.Type.statusBars()));
        window.clearFlags(2);
        setCanceledOnTouchOutside(true);
        window.setDecorFitsSystemWindows(false);
        window.getDecorView().setOnApplyWindowInsetsListener(this);
        View viewInflate = View.inflate(context, R.layout.keyguard_strong_auth_popup, null);
        ((TextView) viewInflate.findViewById(R.id.strong_auth_popup_message)).setText(KeyguardTextBuilder.getInstance(context).getStrongAuthTimeOutMessage(securityMode));
        setView(viewInflate);
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
            return this.mContext.getResources().getDimensionPixelSize(android.R.dimen.secondary_waterfall_display_right_edge_size);
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
        int iCalculateLandscapeViewWidth;
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
        int iWidth = resources.getConfiguration().windowConfiguration.getBounds().width();
        int dimensionPixelSize5 = resources.getDimensionPixelSize(R.dimen.kg_strong_auth_timeout_popup_margin);
        boolean z = LsRune.SECURITY_BIOMETRICS_TABLET;
        char c = 1;
        if (z) {
            iCalculateLandscapeViewWidth = resources.getDimensionPixelSize(R.dimen.kg_strong_auth_timeout_popup_size_tablet);
        } else if (this.mKeyguardUpdateMonitor.isDualDisplayPolicyAllowed()) {
            iCalculateLandscapeViewWidth = SecurityUtils.getMainSecurityViewFlipperSize(this.mContext, this.mSecurityMode == KeyguardSecurityModel.SecurityMode.Password);
        } else {
            iCalculateLandscapeViewWidth = (rotation == 1 || rotation == 3) ? SecurityUtils.calculateLandscapeViewWidth(iWidth, this.mContext) - dimensionPixelSize5 : iWidth - (dimensionPixelSize5 * 2);
        }
        attributes.width = iCalculateLandscapeViewWidth;
        attributes.height = -2;
        attributes.layoutInDisplayCutoutMode = 1;
        if (!(rotation == 1 || rotation == 3) || z || this.mKeyguardUpdateMonitor.isDualDisplayPolicyAllowed()) {
            attributes.gravity = 80;
            attributes.x = 0;
            boolean zIsTablet = DeviceType.isTablet();
            Resources resources2 = this.mContext.getResources();
            int inDisplayFingerprintHeight = (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning()) ? DeviceState.getInDisplayFingerprintHeight() : 0;
            int i4 = AnonymousClass4.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[this.mSecurityMode.ordinal()];
            int i5 = R.dimen.keyguard_bottom_area_emergency_button_area_min_height;
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
                    int dimensionPixelSize6 = resources2.getDimensionPixelSize(zIsTablet ? R.dimen.kg_security_password_input_box_margin_bottom_tablet : R.dimen.kg_password_container_margin_bottom) + resources2.getDimensionPixelSize(zIsTablet ? R.dimen.kg_security_input_box_height_tablet : R.dimen.kg_security_input_box_height) + resources2.getDimensionPixelSize(zIsTablet ? R.dimen.kg_security_input_box_margin_top_tablet : R.dimen.kg_security_input_box_margin_top);
                    if (inDisplayFingerprintHeight != 0) {
                        dimensionPixelSize4 = inDisplayFingerprintHeight - getNavigationBarSize();
                    } else {
                        if (zIsTablet) {
                            i5 = R.dimen.keyguard_bottom_area_emergency_button_area_min_height_tablet;
                        }
                        dimensionPixelSize4 = resources2.getDimensionPixelSize(i5) + resources2.getDimensionPixelSize(zIsTablet ? R.dimen.kg_password_eca_margin_bottom_tablet : R.dimen.kg_password_eca_margin_bottom);
                    }
                    navigationBarSize = (i2 != 0 ? i2 - getNavigationBarSize() : 0) + dimensionPixelSize6 + dimensionPixelSize4;
                } else if (i4 == 3) {
                    dimensionPixelSize = resources2.getDimensionPixelSize(zIsTablet ? R.dimen.kg_pattern_lock_pattern_view_margin_bottom_tablet : R.dimen.kg_pattern_lock_pattern_view_margin_bottom) + resources2.getDimensionPixelSize(zIsTablet ? R.dimen.kg_pattern_lock_pattern_view_height_tablet : R.dimen.kg_pattern_lock_pattern_view_height);
                    if (inDisplayFingerprintHeight != 0) {
                        navigationBarSize = getNavigationBarSize();
                        i = inDisplayFingerprintHeight - navigationBarSize;
                    } else {
                        if (zIsTablet) {
                            i5 = R.dimen.keyguard_bottom_area_emergency_button_area_min_height_tablet;
                        }
                        dimensionPixelSize2 = resources2.getDimensionPixelSize(i5);
                        dimensionPixelSize3 = resources2.getDimensionPixelSize(zIsTablet ? R.dimen.kg_pattern_eca_margin_bottom_tablet : R.dimen.kg_pattern_eca_margin_bottom);
                        i = dimensionPixelSize3 + dimensionPixelSize2;
                    }
                }
                attributes.y = navigationBarSize;
            } else {
                dimensionPixelSize = resources2.getDimensionPixelSize(zIsTablet ? R.dimen.kg_pin_container_margin_bottom_tablet : R.dimen.kg_pin_container_margin_bottom) + (zIsTablet ? SecurityUtils.getTabletPINContainerHeight(this.mContext) : (LsRune.SECURITY_SUB_DISPLAY_LOCK && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) ? SecurityUtils.getFoldPINContainerHeight(this.mContext) : SecurityUtils.getPINContainerHeight(this.mContext));
                if (inDisplayFingerprintHeight != 0) {
                    navigationBarSize = getNavigationBarSize();
                    i = inDisplayFingerprintHeight - navigationBarSize;
                } else {
                    if (zIsTablet) {
                        i5 = R.dimen.keyguard_bottom_area_emergency_button_area_min_height_tablet;
                    }
                    dimensionPixelSize2 = resources2.getDimensionPixelSize(i5);
                    dimensionPixelSize3 = resources2.getDimensionPixelSize(zIsTablet ? R.dimen.kg_pin_eca_margin_bottom_tablet : R.dimen.kg_pin_eca_margin_bottom);
                    i = dimensionPixelSize3 + dimensionPixelSize2;
                }
            }
            navigationBarSize = dimensionPixelSize + i;
            attributes.y = navigationBarSize;
        } else {
            if (this.mSecurityMode == KeyguardSecurityModel.SecurityMode.Password) {
                if (this.mIsSIPVisible) {
                    i3 = SecurityUtils.sImeHeight[(rotation == 1 || rotation == 3) ? (char) 1 : (char) 0];
                } else {
                    i3 = 0;
                }
                attributes.gravity = i3 == 0 ? 16 : 80;
                attributes.y = i3 != 0 ? StrongAuthPopup$$ExternalSyntheticOutline0.m(this.mContext, R.dimen.kg_strong_auth_timeout_popup_margin, i3) : 0;
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
    public class AnonymousClass2 implements TextWatcher {
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
