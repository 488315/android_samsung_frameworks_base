package com.android.keyguard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingA11yDelegate;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class KeyguardSecSecurityContainer extends KeyguardSecurityContainer {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ArrowViewMode implements SecViewMode {
        public ConstraintLayout mView;
        public KeyguardSecurityViewFlipper mViewFlipper;
        public int mViewFlipperWidth;

        @Override // com.android.keyguard.KeyguardSecSecurityContainer.SecViewMode
        public final void init(ConstraintLayout constraintLayout, KeyguardSecurityViewFlipper keyguardSecurityViewFlipper) {
            this.mView = constraintLayout;
            this.mViewFlipper = keyguardSecurityViewFlipper;
            updateSecurityViewFlipperWidth();
            updateSecurityViewPosition(((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getBouncerOneHandPosition(), false);
        }

        public final void updateSecurityViewFlipperWidth() {
            this.mViewFlipperWidth = DeviceType.isTablet() ? this.mView.getResources().getDimensionPixelSize(R.dimen.kg_message_area_width_tablet) : SecurityUtils.getMainSecurityViewFlipperSize(this.mView.getContext(), false);
        }

        @Override // com.android.keyguard.KeyguardSecSecurityContainer.SecViewMode
        public final void updateSecurityViewPosition(int i, boolean z) {
            int i2;
            if (z) {
                TransitionManager.beginDelayedTransition(this.mView, new KeyguardSecurityViewTransition());
            }
            updateSecurityViewFlipperWidth();
            ConstraintSet constraintSet = new ConstraintSet();
            Resources resources = this.mView.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(DeviceType.isTablet() ? R.dimen.kg_security_view_side_margin_tablet : R.dimen.kg_security_view_side_margin);
            int rotation = DeviceState.shouldEnableKeyguardScreenRotation(this.mView.getContext()) ? DeviceState.getRotation(resources.getConfiguration().windowConfiguration.getRotation()) : 0;
            int inDisplayFingerprintHeight = ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isInDisplayFingerprintMarginAccepted() ? DeviceState.getInDisplayFingerprintHeight() : 0;
            if (i == 0 ? rotation == 3 : !(i != 2 || rotation != 1)) {
                dimensionPixelSize += inDisplayFingerprintHeight;
            }
            int i3 = dimensionPixelSize;
            Resources resources2 = this.mView.getResources();
            int dimensionPixelSize2 = LsRune.SECURITY_NAVBAR_ENABLED ? resources2.getDimensionPixelSize(android.R.dimen.resolver_empty_state_container_padding_top) : 0;
            int rotation2 = DeviceState.shouldEnableKeyguardScreenRotation(this.mView.getContext()) ? DeviceState.getRotation(resources2.getConfiguration().windowConfiguration.getRotation()) : 0;
            if (((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isInDisplayFingerprintMarginAccepted()) {
                i2 = DeviceState.getInDisplayFingerprintHeight();
                if (DeviceType.isTablet()) {
                    i2 += this.mView.getResources().getDimensionPixelSize(R.dimen.kg_emergency_button_margin_bottom_for_tablet_fingerprint);
                }
            } else {
                i2 = 0;
            }
            int i4 = (rotation2 != 0 || i2 == 0) ? dimensionPixelSize2 : i2;
            if (i == 0) {
                constraintSet.connect(this.mViewFlipper.getId(), 1, 0, 1, i3);
            } else if (i == 2) {
                constraintSet.connect(this.mViewFlipper.getId(), 2, 0, 2, i3);
            } else {
                constraintSet.connect(this.mViewFlipper.getId(), 1, 0, 1);
                constraintSet.connect(this.mViewFlipper.getId(), 2, 0, 2);
            }
            constraintSet.connect(this.mViewFlipper.getId(), 4, 0, 4, i4);
            constraintSet.constrainWidth(this.mViewFlipper.getId(), this.mViewFlipperWidth);
            constraintSet.applyTo(this.mView);
        }
    }

    public KeyguardSecSecurityContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // com.android.keyguard.KeyguardSecurityContainer
    public final void initMode(int i, GlobalSettings globalSettings, FalsingManager falsingManager, UserSwitcherController userSwitcherController, KeyguardSecurityContainerController$$ExternalSyntheticLambda3 keyguardSecurityContainerController$$ExternalSyntheticLambda3, FalsingA11yDelegate falsingA11yDelegate) {
        if (this.mCurrentMode == i) {
            return;
        }
        Log.i("KeyguardSecSecurityContainer", "Switching mode from " + modeToString(this.mCurrentMode) + " to " + modeToString(i));
        this.mCurrentMode = i;
        this.mViewMode.onDestroy();
        this.mViewMode = i == 3 ? new ArrowViewMode() : new SecDefaultViewMode();
        this.mGlobalSettings = globalSettings;
        this.mFalsingManager = falsingManager;
        this.mFalsingA11yDelegate = falsingA11yDelegate;
        this.mUserSwitcherController = userSwitcherController;
        setupViewMode();
    }

    @Override // com.android.keyguard.KeyguardSecurityContainer
    public final String modeToString(int i) {
        return i == 3 ? "ArrowView" : super.modeToString(i);
    }

    @Override // android.view.View
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).setFocusForBiometrics(2, z);
    }

    @Override // com.android.keyguard.KeyguardSecurityContainer
    public final void setupViewMode() {
        KeyguardSecurityViewFlipper keyguardSecurityViewFlipper = this.mSecurityViewFlipper;
        if (keyguardSecurityViewFlipper == null) {
            return;
        }
        KeyguardSecurityContainer.ViewMode viewMode = this.mViewMode;
        if (viewMode instanceof SecViewMode) {
            ((SecViewMode) viewMode).init(this, keyguardSecurityViewFlipper);
        } else {
            super.setupViewMode();
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityContainer
    public final void showAlmostAtWipeDialog(int i, int i2, int i3) {
        if (i3 == 1) {
            showDialog(i == 1 ? ((ViewGroup) this).mContext.getString(R.string.kg_sec_1_failed_attempt_almost_at_wipe, Integer.valueOf(i2)) : i2 == 1 ? ((ViewGroup) this).mContext.getString(R.string.kg_sec_1_remaining_count_almost_at_wipe, Integer.valueOf(i)) : ((ViewGroup) this).mContext.getString(R.string.kg_sec_failed_attempts_almost_at_wipe, Integer.valueOf(i), Integer.valueOf(i2)));
        } else {
            super.showAlmostAtWipeDialog(i, i2, i3);
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityContainer
    public final void showDialog(String str) {
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mAlertDialog = null;
        }
        final KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
        AlertDialog create = new AlertDialog.Builder(((ViewGroup) this).mContext, R.style.Theme_SystemUI_DayNightDialog).setTitle((CharSequence) null).setMessage(str).setCancelable(false).setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() { // from class: com.android.keyguard.KeyguardSecSecurityContainer$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                KeyguardSecSecurityContainer keyguardSecSecurityContainer = KeyguardSecSecurityContainer.this;
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = keyguardUpdateMonitor;
                int i2 = KeyguardSecSecurityContainer.$r8$clinit;
                keyguardSecSecurityContainer.getClass();
                keyguardUpdateMonitor2.setDisableBiometricBySecurityDialog(false);
                keyguardSecSecurityContainer.mViewMode.reset();
                keyguardSecSecurityContainer.mDisappearAnimRunning = false;
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.keyguard.KeyguardSecSecurityContainer$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = KeyguardUpdateMonitor.this;
                int i = KeyguardSecSecurityContainer.$r8$clinit;
                keyguardUpdateMonitor2.setDisableBiometricBySecurityDialog(false);
            }
        }).create();
        this.mAlertDialog = create;
        if (!(((ViewGroup) this).mContext instanceof Activity)) {
            Window window = create.getWindow();
            Objects.requireNonNull(window);
            window.setType(2009);
        }
        keyguardUpdateMonitor.setDisableBiometricBySecurityDialog(true);
        this.mAlertDialog.show();
    }

    public KeyguardSecSecurityContainer(Context context) {
        this(context, null, 0);
    }

    public KeyguardSecSecurityContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SecDefaultViewMode implements SecViewMode {
        @Override // com.android.keyguard.KeyguardSecSecurityContainer.SecViewMode
        public final void init(ConstraintLayout constraintLayout, KeyguardSecurityViewFlipper keyguardSecurityViewFlipper) {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface SecViewMode extends KeyguardSecurityContainer.ViewMode {
        void init(ConstraintLayout constraintLayout, KeyguardSecurityViewFlipper keyguardSecurityViewFlipper);

        default void updateSecurityViewPosition(int i, boolean z) {
        }
    }
}
