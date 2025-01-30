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
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import java.util.function.IntConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class StrongAuthPopup extends AlertDialog implements View.OnApplyWindowInsetsListener {
    public final Context mContext;
    public final Handler mHandler;
    public boolean mIsSIPVisible;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final EditText mPasswordEntry;
    public int mRotation;
    public final StrongAuthPopup$$ExternalSyntheticLambda1 mRotationConsumer;
    public final SecRotationWatcher mRotationWatcher;
    public final StrongAuthPopup$$ExternalSyntheticLambda0 mRunnable;
    public final KeyguardSecurityModel.SecurityMode mSecurityMode;
    public final C08472 mTextWatcher;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.StrongAuthPopup$1 */
    public final class C08461 extends KeyguardUpdateMonitorCallback {
        public C08461() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
            if (z) {
                return;
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.keyguard.StrongAuthPopup$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    StrongAuthPopup.this.dismiss();
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.StrongAuthPopup$3 */
    public abstract /* synthetic */ class AbstractC08483 {

        /* renamed from: $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode */
        public static final /* synthetic */ int[] f216xdc0e830a;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            f216xdc0e830a = iArr;
            try {
                iArr[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f216xdc0e830a[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f216xdc0e830a[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.keyguard.StrongAuthPopup$$ExternalSyntheticLambda1, java.util.function.IntConsumer] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public StrongAuthPopup(Context context, KeyguardSecurityModel.SecurityMode securityMode, EditText editText) {
        super(context, securityMode == r0 ? R.style.keyguard_password_strong_auth_popup_style : R.style.keyguard_strong_auth_popup_style);
        KeyguardSecurityModel.SecurityMode securityMode2 = KeyguardSecurityModel.SecurityMode.Password;
        this.mHandler = new Handler(Looper.getMainLooper());
        C08461 c08461 = new C08461();
        this.mKeyguardUpdateMonitorCallback = c08461;
        C08472 c08472 = new C08472();
        this.mTextWatcher = c08472;
        this.mRunnable = new StrongAuthPopup$$ExternalSyntheticLambda0(this, 1);
        SecRotationWatcher secRotationWatcher = (SecRotationWatcher) Dependency.get(SecRotationWatcher.class);
        this.mRotationWatcher = secRotationWatcher;
        ?? r5 = new IntConsumer() { // from class: com.android.keyguard.StrongAuthPopup$$ExternalSyntheticLambda1
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                StrongAuthPopup strongAuthPopup = StrongAuthPopup.this;
                strongAuthPopup.mHandler.post(strongAuthPopup.mRunnable);
            }
        };
        this.mRotationConsumer = r5;
        this.mContext = context;
        this.mSecurityMode = securityMode;
        this.mPasswordEntry = editText;
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        if (DeviceState.shouldEnableKeyguardScreenRotation(context) || DeviceType.isTablet()) {
            secRotationWatcher.addCallback(r5);
        }
        keyguardUpdateMonitor.registerCallback(c08461);
        if ((securityMode == securityMode2) && editText != null) {
            editText.addTextChangedListener(c08472);
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
        View inflate = View.inflate(context, R.layout.keyguard_strong_auth_popup, null);
        ((TextView) inflate.findViewById(R.id.strong_auth_popup_message)).setText(KeyguardTextBuilder.getInstance(context).getStrongAuthTimeOutMessage(securityMode));
        setView(inflate);
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        EditText editText;
        if (DeviceState.shouldEnableKeyguardScreenRotation(this.mContext) || DeviceType.isTablet()) {
            this.mRotationWatcher.removeCallback(this.mRotationConsumer);
        }
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        if ((this.mSecurityMode == KeyguardSecurityModel.SecurityMode.Password) && (editText = this.mPasswordEntry) != null) {
            editText.removeTextChangedListener(this.mTextWatcher);
        }
        super.dismiss();
    }

    public final int getNavigationBarSize() {
        if (LsRune.SECURITY_NAVBAR_ENABLED) {
            return this.mContext.getResources().getDimensionPixelSize(android.R.dimen.notification_content_margin_end);
        }
        return 0;
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        boolean isVisible = windowInsets.isVisible(WindowInsets.Type.ime());
        if (this.mIsSIPVisible != isVisible) {
            this.mIsSIPVisible = isVisible;
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
        int dimensionPixelSize5 = resources.getDimensionPixelSize(R.dimen.kg_strong_auth_timeout_popup_margin);
        boolean z = LsRune.SECURITY_BIOMETRICS_TABLET;
        char c = 1;
        if (z) {
            calculateLandscapeViewWidth = resources.getDimensionPixelSize(R.dimen.kg_strong_auth_timeout_popup_size_tablet);
        } else if (this.mKeyguardUpdateMonitor.isDualDisplayPolicyAllowed()) {
            calculateLandscapeViewWidth = SecurityUtils.getMainSecurityViewFlipperSize(this.mContext, this.mSecurityMode == KeyguardSecurityModel.SecurityMode.Password);
        } else {
            calculateLandscapeViewWidth = rotation == 1 || rotation == 3 ? SecurityUtils.calculateLandscapeViewWidth(width, this.mContext) - dimensionPixelSize5 : width - (dimensionPixelSize5 * 2);
        }
        attributes.width = calculateLandscapeViewWidth;
        attributes.height = -2;
        attributes.layoutInDisplayCutoutMode = 1;
        boolean z2 = rotation == 1 || rotation == 3;
        int[] iArr = SecurityUtils.sImeHeight;
        if (!z2 || z || this.mKeyguardUpdateMonitor.isDualDisplayPolicyAllowed()) {
            attributes.gravity = 80;
            attributes.x = 0;
            boolean isTablet = DeviceType.isTablet();
            Resources resources2 = this.mContext.getResources();
            int i4 = (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning()) ? DeviceState.sInDisplayFingerprintHeight : 0;
            int i5 = AbstractC08483.f216xdc0e830a[this.mSecurityMode.ordinal()];
            int i6 = R.dimen.keyguard_bottom_area_emergency_button_area_min_height_tablet;
            if (i5 != 1) {
                if (i5 == 2) {
                    if (this.mIsSIPVisible) {
                        int i7 = this.mRotation;
                        if (i7 != 1 && i7 != 3) {
                            c = 0;
                        }
                        i2 = iArr[c];
                    } else {
                        i2 = 0;
                    }
                    int dimensionPixelSize6 = resources2.getDimensionPixelSize(isTablet ? R.dimen.kg_security_password_input_box_margin_bottom_tablet : R.dimen.kg_password_container_margin_bottom) + resources2.getDimensionPixelSize(isTablet ? R.dimen.kg_security_input_box_height_tablet : R.dimen.kg_security_input_box_height) + resources2.getDimensionPixelSize(isTablet ? R.dimen.kg_security_input_box_margin_top_tablet : R.dimen.kg_security_input_box_margin_top);
                    if (i4 != 0) {
                        dimensionPixelSize4 = i4 - getNavigationBarSize();
                    } else {
                        if (!isTablet) {
                            i6 = R.dimen.keyguard_bottom_area_emergency_button_area_min_height;
                        }
                        dimensionPixelSize4 = resources2.getDimensionPixelSize(i6) + resources2.getDimensionPixelSize(isTablet ? R.dimen.kg_password_eca_margin_bottom_tablet : R.dimen.kg_password_eca_margin_bottom);
                    }
                    r9 = (i2 != 0 ? i2 - getNavigationBarSize() : 0) + dimensionPixelSize6 + dimensionPixelSize4;
                } else if (i5 == 3) {
                    dimensionPixelSize = resources2.getDimensionPixelSize(isTablet ? R.dimen.kg_pattern_lock_pattern_view_margin_bottom_tablet : R.dimen.kg_pattern_lock_pattern_view_margin_bottom) + resources2.getDimensionPixelSize(isTablet ? R.dimen.kg_pattern_lock_pattern_view_height_tablet : R.dimen.kg_pattern_lock_pattern_view_height);
                    if (i4 != 0) {
                        navigationBarSize = getNavigationBarSize();
                        i = i4 - navigationBarSize;
                    } else {
                        if (!isTablet) {
                            i6 = R.dimen.keyguard_bottom_area_emergency_button_area_min_height;
                        }
                        dimensionPixelSize2 = resources2.getDimensionPixelSize(i6);
                        dimensionPixelSize3 = resources2.getDimensionPixelSize(isTablet ? R.dimen.kg_pattern_eca_margin_bottom_tablet : R.dimen.kg_pattern_eca_margin_bottom);
                        i = dimensionPixelSize3 + dimensionPixelSize2;
                    }
                }
                attributes.y = r9;
            } else {
                dimensionPixelSize = resources2.getDimensionPixelSize(isTablet ? R.dimen.kg_pin_container_margin_bottom_tablet : R.dimen.kg_pin_container_margin_bottom) + (isTablet ? SecurityUtils.getTabletPINContainerHeight(this.mContext) : (LsRune.SECURITY_SUB_DISPLAY_LOCK && ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) ? SecurityUtils.getFoldPINContainerHeight(this.mContext) : SecurityUtils.getPINContainerHeight(this.mContext));
                if (i4 != 0) {
                    navigationBarSize = getNavigationBarSize();
                    i = i4 - navigationBarSize;
                } else {
                    if (!isTablet) {
                        i6 = R.dimen.keyguard_bottom_area_emergency_button_area_min_height;
                    }
                    dimensionPixelSize2 = resources2.getDimensionPixelSize(i6);
                    dimensionPixelSize3 = resources2.getDimensionPixelSize(isTablet ? R.dimen.kg_pin_eca_margin_bottom_tablet : R.dimen.kg_pin_eca_margin_bottom);
                    i = dimensionPixelSize3 + dimensionPixelSize2;
                }
            }
            r9 = dimensionPixelSize + i;
            attributes.y = r9;
        } else {
            if (this.mSecurityMode == KeyguardSecurityModel.SecurityMode.Password) {
                if (this.mIsSIPVisible) {
                    i3 = iArr[(rotation == 1 || rotation == 3) ? (char) 1 : (char) 0];
                } else {
                    i3 = 0;
                }
                attributes.gravity = i3 == 0 ? 16 : 80;
                attributes.y = i3 != 0 ? this.mContext.getResources().getDimensionPixelSize(R.dimen.kg_strong_auth_timeout_popup_margin) + i3 : 0;
            } else {
                attributes.gravity = 16;
                attributes.y = 0;
            }
            attributes.gravity |= 3;
            attributes.x = rotation == 1 ? getNavigationBarSize() : 0;
        }
        window.setAttributes(attributes);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.StrongAuthPopup$2 */
    public final class C08472 implements TextWatcher {
        public C08472() {
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
            StrongAuthPopup.this.mHandler.post(new StrongAuthPopup$$ExternalSyntheticLambda0(this, 2));
        }

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }
}
