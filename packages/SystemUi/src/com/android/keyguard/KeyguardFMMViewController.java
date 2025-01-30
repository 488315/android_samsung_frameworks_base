package com.android.keyguard;

import android.R;
import android.app.SemWallpaperColors;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.LinearLayout;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIButton;
import com.android.systemui.widget.SystemUITextView;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardFMMViewController extends KeyguardSecPinBasedInputViewController {
    public final SystemUIButton mCallButton;
    public final ConfigurationController mConfigurationController;
    public final C06862 mConfigurationListener;
    public final Handler mHandler;
    public final boolean mIsVoiceCapacity;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LinearLayout mMessageArea;
    public int mOrientation;
    public final SystemUITextView mOwnerContactInfo;
    public final SystemUITextView mOwnerMessage;
    public final KeyguardUpdateMonitorCallback mUpdateCallback;

    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.keyguard.KeyguardFMMViewController$2] */
    public KeyguardFMMViewController(KeyguardFMMView keyguardFMMView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector, FeatureFlags featureFlags, SecRotationWatcher secRotationWatcher, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, ConfigurationController configurationController) {
        super(keyguardFMMView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, emergencyButtonController, falsingCollector, featureFlags, secRotationWatcher, vibrationUtil, accessibilityManager, configurationController);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mOrientation = 1;
        this.mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardFMMViewController.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onRemoteLockInfoChanged() {
                Log.d("KeyguardFMMView", "onRemoteLockInfoChanged");
                KeyguardFMMViewController.this.setFMMInfo();
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardFMMViewController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                KeyguardFMMViewController keyguardFMMViewController = KeyguardFMMViewController.this;
                int i = keyguardFMMViewController.mOrientation;
                int i2 = configuration.orientation;
                if (i != i2) {
                    keyguardFMMViewController.mOrientation = i2;
                    keyguardFMMViewController.updateFMMLayout();
                }
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mConfigurationController = configurationController;
        this.mIsVoiceCapacity = getResources().getBoolean(R.bool.config_supportsSystemDecorsOnSecondaryDisplays);
        this.mOwnerMessage = (SystemUITextView) ((KeyguardFMMView) this.mView).findViewById(com.android.systemui.R.id.keyguard_fmm_message);
        this.mOwnerContactInfo = (SystemUITextView) ((KeyguardFMMView) this.mView).findViewById(com.android.systemui.R.id.keyguard_fmm_owner_phone_num);
        this.mCallButton = (SystemUIButton) ((KeyguardFMMView) this.mView).findViewById(com.android.systemui.R.id.keyguard_fmm_emergency_btn);
        this.mMessageArea = (LinearLayout) ((KeyguardFMMView) this.mView).findViewById(com.android.systemui.R.id.message_area);
        SystemUITextView systemUITextView = (SystemUITextView) ((KeyguardFMMView) this.mView).findViewById(com.android.systemui.R.id.keyguard_fmm_phone_locked);
        if (systemUITextView != null) {
            systemUITextView.setSelected(true);
        }
        setFMMInfo();
        View findViewById = ((KeyguardFMMView) this.mView).findViewById(com.android.systemui.R.id.key_enter);
        if (findViewById != null) {
            findViewById.setOnClickListener(new KeyguardFMMViewController$$ExternalSyntheticLambda2(this, 0));
        }
        updateDrawableTint();
        updateFMMLayout();
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final int getSecurityViewId() {
        return com.android.systemui.R.id.keyguard_fmm_view;
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void onPasswordChecked(int i, int i2, boolean z, boolean z2) {
        long j;
        KeyguardSecMessageAreaController keyguardSecMessageAreaController;
        StringBuilder m66m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m66m("onPasswordChecked ", z, " / ", i2, " / ");
        m66m.append(z2);
        m66m.append(" / ");
        m66m.append(i);
        Log.d("KeyguardFMMView", m66m.toString());
        if (z) {
            this.mLockPatternUtils.saveRemoteLockPassword(0, (byte[]) null, i);
            Intent intent = new Intent("com.samsung.Keyguard.UNLOCK_FMM_ALERT");
            intent.addFlags(16777248);
            getContext().sendBroadcast(intent, "com.samsung.android.permission.LOCK_SECURITY_MONITOR");
            Log.d("KeyguardFMMView", "send Broadcast : com.samsung.Keyguard.UNLOCK_FMM_ALERT");
            this.mKeyguardUpdateMonitor.updateFMMLock(i, true);
            if (this.mKeyguardUpdateMonitor.isSecure(i)) {
                getKeyguardSecurityCallback().reset();
            } else {
                KeyguardUnlockInfo.setUnlockTriggerByRemoteLock(0);
                getKeyguardSecurityCallback().dismiss(i, this.mSecurityMode, true);
            }
        } else {
            if (z2) {
                this.mKeyguardUpdateMonitor.addFailedFMMUnlockAttempt(i);
                int failedFMMUnlockAttempt = this.mKeyguardUpdateMonitor.getFailedFMMUnlockAttempt(i);
                if ((failedFMMUnlockAttempt == 5 || failedFMMUnlockAttempt > 9) && !((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isDeviceDisabledForMaxFailedAttempt()) {
                    j = this.mKeyguardUpdateMonitor.setLockoutAttemptDeadline(i, 30000);
                    handleAttemptLockout(j);
                    if (j == 0 && (keyguardSecMessageAreaController = this.mMessageAreaController) != null) {
                        Resources resources = getResources();
                        ((KeyguardFMMView) this.mView).getClass();
                        keyguardSecMessageAreaController.setMessage(resources.getString(com.android.systemui.R.string.kg_incorrect_pin), false);
                        keyguardSecMessageAreaController.displayFailedAnimation();
                    }
                }
            }
            j = 0;
            if (j == 0) {
                Resources resources2 = getResources();
                ((KeyguardFMMView) this.mView).getClass();
                keyguardSecMessageAreaController.setMessage(resources2.getString(com.android.systemui.R.string.kg_incorrect_pin), false);
                keyguardSecMessageAreaController.displayFailedAnimation();
            }
        }
        ((KeyguardFMMView) this.mView).resetPasswordText(true, !z);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public final void onPause() {
        setFMMInfo();
        updateFMMLayout();
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        this.mKeyguardUpdateMonitor.removeCallback(this.mUpdateCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void resetState() {
        super.resetState();
        setFMMInfo();
        updateFMMLayout();
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isDeviceDisabledForMaxFailedAttempt()) {
            disableDevicePermanently();
            return;
        }
        long lockoutAttemptDeadline = this.mLockPatternUtils.getLockoutAttemptDeadline(KeyguardUpdateMonitor.getCurrentUser());
        if (lockoutAttemptDeadline > 0) {
            handleAttemptLockout(lockoutAttemptDeadline);
            return;
        }
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController != null) {
            keyguardSecMessageAreaController.setMessage(getContext().getString(com.android.systemui.R.string.kg_none_pin_none_instructions), false);
        }
        if (LsRune.SECURITY_VZW_INSTRUCTION) {
            setSubSecurityMessage(com.android.systemui.R.string.kg_pin_sub_instructions_vzw);
        } else {
            setSubSecurityMessage(com.android.systemui.R.string.kg_pin_sub_instructions);
        }
    }

    public final void setFMMInfo() {
        String stringValue = ((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("lock_fmm_Message").getStringValue();
        final String stringValue2 = ((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("lock_fmm_phone").getStringValue();
        SystemUITextView systemUITextView = this.mOwnerMessage;
        if (systemUITextView != null) {
            if (stringValue != null) {
                stringValue = stringValue.replaceAll("\\r\\n|\\r|\\n", " ");
                if (stringValue.trim().isEmpty()) {
                    this.mOwnerMessage.setVisibility(8);
                }
            } else {
                systemUITextView.setVisibility(8);
            }
            if (this.mOwnerMessage.getLineCount() > getResources().getInteger(com.android.systemui.R.integer.kg_fmm_message_max_line)) {
                this.mOwnerMessage.setMovementMethod(new ScrollingMovementMethod());
                this.mOwnerMessage.setScrollBarStyle(16777216);
                this.mOwnerMessage.setScrollbarFadingEnabled(false);
            }
            this.mOwnerMessage.setText(stringValue);
        }
        if (this.mOwnerContactInfo == null || this.mCallButton == null) {
            return;
        }
        if (stringValue2 == null || stringValue2.isEmpty()) {
            this.mOwnerContactInfo.setVisibility(8);
            this.mCallButton.setVisibility(8);
            return;
        }
        this.mCallButton.setText(stringValue2);
        this.mCallButton.setSingleLine();
        if (this.mIsVoiceCapacity) {
            this.mCallButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    KeyguardFMMViewController keyguardFMMViewController = KeyguardFMMViewController.this;
                    String str = stringValue2;
                    keyguardFMMViewController.getClass();
                    Intent intent = new Intent("android.intent.action.CALL_PRIVILEGED", Uri.fromParts("tel", str, null));
                    intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                    try {
                        Log.d("KeyguardFMMView", "click call button : " + keyguardFMMViewController.mIsVoiceCapacity);
                        keyguardFMMViewController.getContext().startActivityAsUser(intent, UserHandle.CURRENT);
                    } catch (ActivityNotFoundException e) {
                        Log.w("KeyguardFMMView", "Can't find the component " + e);
                    }
                }
            });
        } else {
            this.mCallButton.setOnClickListener(new KeyguardFMMViewController$$ExternalSyntheticLambda2(this, 1));
        }
        this.mOwnerContactInfo.setVisibility(0);
        this.mCallButton.setVisibility(0);
    }

    public final void updateDrawableTint() {
        if (this.mCallButton == null) {
            return;
        }
        boolean isWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("background");
        for (Drawable drawable : this.mCallButton.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(isWhiteKeyguardWallpaper ? getResources().getColor(com.android.systemui.R.color.kg_fmm_drawable_tint_white_bg) : getResources().getColor(com.android.systemui.R.color.kg_fmm_drawable_tint), PorterDuff.Mode.SRC_IN);
            }
        }
    }

    public final void updateFMMLayout() {
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardFMMViewController keyguardFMMViewController = KeyguardFMMViewController.this;
                LinearLayout linearLayout = (LinearLayout) ((KeyguardFMMView) keyguardFMMViewController.mView).findViewById(com.android.systemui.R.id.fmm_message);
                LinearLayout linearLayout2 = (LinearLayout) ((KeyguardFMMView) keyguardFMMViewController.mView).findViewById(com.android.systemui.R.id.fmm_phone);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
                if (keyguardFMMViewController.mOrientation == 1 || DeviceType.isTablet()) {
                    layoutParams.weight = 1.0f;
                    layoutParams2.weight = 0.0f;
                } else {
                    layoutParams.weight = 1.0f;
                    layoutParams2.weight = 1.0f;
                }
                linearLayout.setLayoutParams(layoutParams);
                linearLayout2.setLayoutParams(layoutParams2);
                boolean z = keyguardFMMViewController.mOrientation == 1;
                boolean isLandscapePolicyAllowed = keyguardFMMViewController.isLandscapePolicyAllowed();
                Resources resources = keyguardFMMViewController.getResources();
                LinearLayout linearLayout3 = keyguardFMMViewController.mMessageArea;
                if (linearLayout3 != null) {
                    LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) linearLayout3.getLayoutParams();
                    layoutParams3.topMargin = resources.getDimensionPixelSize(com.android.systemui.R.dimen.kg_biometric_view_height) + resources.getDimensionPixelSize(com.android.systemui.R.dimen.status_bar_height) + (z ? resources.getDimensionPixelSize(com.android.systemui.R.dimen.kg_lock_icon_top_margin) : 0);
                    layoutParams3.weight = isLandscapePolicyAllowed ? 0.0f : 1.0f;
                    keyguardFMMViewController.mMessageArea.setGravity(isLandscapePolicyAllowed ? 49 : 17);
                    int dimensionPixelSize = resources.getDimensionPixelSize(com.android.systemui.R.dimen.kg_message_area_padding_side);
                    if (isLandscapePolicyAllowed) {
                        keyguardFMMViewController.mMessageArea.setPadding(0, 0, dimensionPixelSize, 0);
                    }
                    keyguardFMMViewController.mMessageArea.setLayoutParams(layoutParams3);
                }
                SystemUITextView systemUITextView = keyguardFMMViewController.mOwnerMessage;
                if (systemUITextView != null) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) systemUITextView.getLayoutParams();
                    marginLayoutParams.topMargin = resources.getDimensionPixelSize(com.android.systemui.R.dimen.kg_fmm_message_margin_top);
                    keyguardFMMViewController.mOwnerMessage.setLayoutParams(marginLayoutParams);
                }
            }
        }, 100L);
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        initializeBottomContainerView();
        updateDrawableTint();
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void verifyPasswordAndUnlock() {
        final byte[] passwordText = getPasswordText();
        final int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        ((KeyguardFMMView) this.mView).setPasswordEntryInputEnabled(false);
        setOkButtonEnabled(false);
        AsyncTask asyncTask = this.mPendingLockCheck;
        if (asyncTask != null) {
            asyncTask.cancel(false);
        }
        if (passwordText.length > 3) {
            this.mPendingLockCheck = LockPatternChecker.checkRemoteLockPassword(this.mLockPatternUtils, 0, passwordText, currentUser, new LockPatternChecker.OnCheckCallback() { // from class: com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticLambda1
                public final void onChecked(boolean z, int i) {
                    KeyguardFMMViewController keyguardFMMViewController = KeyguardFMMViewController.this;
                    int i2 = currentUser;
                    byte[] bArr = passwordText;
                    ((KeyguardFMMView) keyguardFMMViewController.mView).setPasswordEntryInputEnabled(true);
                    keyguardFMMViewController.mPendingLockCheck = null;
                    keyguardFMMViewController.onPasswordChecked(i2, i, z, true);
                    Arrays.fill(bArr, (byte) 0);
                }
            });
        } else {
            ((KeyguardFMMView) this.mView).setPasswordEntryInputEnabled(true);
            onPasswordChecked(currentUser, 0, false, false);
            Arrays.fill(passwordText, (byte) 0);
        }
    }
}
