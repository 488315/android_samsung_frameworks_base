package com.android.keyguard;

import android.app.SemWallpaperColors;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.LinearLayout;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.RemoteLockInfo;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIButton;
import com.android.systemui.widget.SystemUITextView;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardRMMViewController extends KeyguardSecPinBasedInputViewController {
    public final SystemUIButton mCallButton;
    public final IRemoteCallbackStubC07111 mCheckPasswordCallback;
    public final SystemUITextView mClientContact;
    public final SystemUITextView mClientMessage;
    public String mClientName;
    public final ConfigurationController mConfigurationController;
    public final C07133 mConfigurationListener;
    public int mCurrentOrientation;
    public final HandlerC07144 mHandler;
    public final boolean mIsVoiceCapacity;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public ILockSettings mLockSettingsService;
    public final LinearLayout mMessageArea;
    public String mPhoneNumber;
    public RemoteLockInfo mRemoteLockInfo;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardRMMViewController$2 */
    public final class C07122 extends KeyguardUpdateMonitorCallback {
        public C07122() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onRemoteLockInfoChanged() {
            post(new Runnable() { // from class: com.android.keyguard.KeyguardRMMViewController$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardRMMViewController.this.setRMMInfo();
                }
            });
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.keyguard.KeyguardRMMViewController$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.keyguard.KeyguardRMMViewController$3] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.keyguard.KeyguardRMMViewController$4] */
    public KeyguardRMMViewController(KeyguardRMMView keyguardRMMView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector, FeatureFlags featureFlags, SecRotationWatcher secRotationWatcher, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, ConfigurationController configurationController) {
        super(keyguardRMMView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, emergencyButtonController, falsingCollector, featureFlags, secRotationWatcher, vibrationUtil, accessibilityManager, configurationController);
        this.mRemoteLockInfo = null;
        this.mLockSettingsService = null;
        this.mCheckPasswordCallback = new IRemoteCallback.Stub() { // from class: com.android.keyguard.KeyguardRMMViewController.1
            public final void sendResult(Bundle bundle) {
                int i = bundle.getInt("result");
                removeMessages(2);
                sendMessage(obtainMessage(2, Integer.valueOf(i)));
            }
        };
        this.mKeyguardUpdateMonitorCallback = new C07122();
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardRMMViewController.3
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                KeyguardRMMViewController keyguardRMMViewController = KeyguardRMMViewController.this;
                int i = keyguardRMMViewController.mCurrentOrientation;
                int i2 = configuration.orientation;
                if (i != i2) {
                    keyguardRMMViewController.mCurrentOrientation = i2;
                    keyguardRMMViewController.updateRMMLayout();
                }
            }
        };
        this.mHandler = new Handler(Looper.myLooper()) { // from class: com.android.keyguard.KeyguardRMMViewController.4
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i;
                long currentTimeMillis;
                if (message.what == 2) {
                    KeyguardRMMViewController keyguardRMMViewController = KeyguardRMMViewController.this;
                    int intValue = ((Integer) message.obj).intValue();
                    keyguardRMMViewController.getClass();
                    Log.d("KeyguardRMMView", "checkUnlockAttempts " + intValue);
                    ((KeyguardRMMView) keyguardRMMViewController.mView).setPasswordEntryInputEnabled(true);
                    ((KeyguardRMMView) keyguardRMMViewController.mView).resetPasswordText(true, true);
                    if (intValue == 0) {
                        try {
                            keyguardRMMViewController.getLockSettings().setRemoteLock(KeyguardUpdateMonitor.getCurrentUser(), new RemoteLockInfo.Builder(2, false).build());
                        } catch (RemoteException e) {
                            Log.d("KeyguardRMMView", "Failed CIS LOCK clear!!" + e);
                        }
                        KeyguardUnlockInfo.setUnlockTriggerByRemoteLock(2);
                        keyguardRMMViewController.getKeyguardSecurityCallback().dismiss(KeyguardUpdateMonitor.getCurrentUser(), keyguardRMMViewController.mSecurityMode, true);
                        return;
                    }
                    RemoteLockInfo remoteLockInfo = keyguardRMMViewController.mRemoteLockInfo;
                    if (remoteLockInfo == null || (i = remoteLockInfo.allowFailCount) <= 0 || remoteLockInfo.lockTimeOut <= 0 || intValue <= 0 || intValue % i != 0) {
                        Resources resources = keyguardRMMViewController.getResources();
                        ((KeyguardRMMView) keyguardRMMViewController.mView).getClass();
                        String string = resources.getString(R.string.kg_incorrect_pin);
                        KeyguardSecMessageAreaController keyguardSecMessageAreaController = keyguardRMMViewController.mMessageAreaController;
                        keyguardSecMessageAreaController.setMessage(string, false);
                        keyguardSecMessageAreaController.displayFailedAnimation();
                        return;
                    }
                    int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                    if (keyguardRMMViewController.mRemoteLockInfo == null) {
                        currentTimeMillis = -1;
                    } else {
                        currentTimeMillis = System.currentTimeMillis() + keyguardRMMViewController.mRemoteLockInfo.lockTimeOut;
                        keyguardRMMViewController.setLong(currentTimeMillis, ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder(), keyguardRMMViewController.mRemoteLockInfo.lockType, "remotelockscreen.lockoutdeadline"), currentUser);
                    }
                    keyguardRMMViewController.handleAttemptLockout(currentTimeMillis);
                }
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mConfigurationController = configurationController;
        this.mIsVoiceCapacity = getResources().getBoolean(android.R.bool.config_supportsSystemDecorsOnSecondaryDisplays);
        this.mClientMessage = (SystemUITextView) ((KeyguardRMMView) this.mView).findViewById(R.id.keyguard_rmm_message);
        this.mClientContact = (SystemUITextView) ((KeyguardRMMView) this.mView).findViewById(R.id.keyguard_rmm_contact_text);
        this.mCallButton = (SystemUIButton) ((KeyguardRMMView) this.mView).findViewById(R.id.keyguard_rmm_contact_button);
        this.mMessageArea = (LinearLayout) ((KeyguardRMMView) this.mView).findViewById(R.id.message_area);
        this.mCurrentOrientation = getResources().getConfiguration().orientation;
        SystemUITextView systemUITextView = (SystemUITextView) ((KeyguardRMMView) this.mView).findViewById(R.id.keyguard_rmm_phone_locked);
        if (systemUITextView != null) {
            systemUITextView.setSelected(true);
        }
        setRMMInfo();
        View findViewById = ((KeyguardRMMView) this.mView).findViewById(R.id.key_enter);
        if (findViewById != null) {
            findViewById.setOnClickListener(new KeyguardRMMViewController$$ExternalSyntheticLambda0(this, 0));
        }
        updateDrawableTint();
        updateRMMLayout();
    }

    public final ILockSettings getLockSettings() {
        if (this.mLockSettingsService == null) {
            this.mLockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
        }
        return this.mLockSettingsService;
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final int getSecurityViewId() {
        return R.id.keyguard_rmm_view;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public final void onPause() {
        setRMMInfo();
        updateRMMLayout();
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void resetState() {
        long j;
        long j2;
        super.resetState();
        setRMMInfo();
        updateRMMLayout();
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isDeviceDisabledForMaxFailedAttempt()) {
            disableDevicePermanently();
            return;
        }
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        if (this.mRemoteLockInfo == null) {
            j2 = -1;
        } else {
            try {
                j = getLockSettings().getLong(ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder(), this.mRemoteLockInfo.lockType, "remotelockscreen.lockoutdeadline"), 0L, currentUser);
            } catch (RemoteException unused) {
                j = 0;
            }
            long currentTimeMillis = System.currentTimeMillis();
            if (j <= currentTimeMillis) {
                setLong(0L, ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder(), this.mRemoteLockInfo.lockType, "remotelockscreen.lockoutdeadline"), currentUser);
                j2 = 0;
            } else {
                if (j > currentTimeMillis + this.mRemoteLockInfo.lockTimeOut) {
                    j = System.currentTimeMillis() + this.mRemoteLockInfo.lockTimeOut;
                    setLong(j, ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder(), this.mRemoteLockInfo.lockType, "remotelockscreen.lockoutdeadline"), currentUser);
                }
                j2 = j;
            }
        }
        if (j2 > 0) {
            handleAttemptLockout(j2);
            return;
        }
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController != null) {
            keyguardSecMessageAreaController.setMessage(getResources().getString(R.string.kg_rmm_lock_instructions, this.mClientName), false);
        }
    }

    public final void setLong(long j, String str, int i) {
        try {
            getLockSettings().setLong(str, j, i);
        } catch (RemoteException e) {
            Log.e("KeyguardRMMView", "Couldn't write long " + str + e);
        }
    }

    public final void setRMMInfo() {
        String str;
        RemoteLockInfo remoteLockInfo = this.mKeyguardUpdateMonitor.getRemoteLockInfo();
        this.mRemoteLockInfo = remoteLockInfo;
        if (remoteLockInfo == null) {
            Log.d("KeyguardRMMView", "mRemoteLockInfo is null - dismiss");
            getKeyguardSecurityCallback().dismiss(KeyguardUpdateMonitor.getCurrentUser(), this.mSecurityMode, true);
            return;
        }
        CharSequence charSequence = remoteLockInfo.message;
        if (charSequence != null) {
            String replaceAll = charSequence.toString().replaceAll("\\r\\n|\\r|\\n", " ");
            if (this.mClientMessage != null) {
                if (replaceAll.trim().isEmpty()) {
                    this.mClientMessage.setVisibility(8);
                } else {
                    if (this.mClientMessage.getLineCount() > getResources().getInteger(R.integer.kg_rmm_message_max_line)) {
                        this.mClientMessage.setMovementMethod(new ScrollingMovementMethod());
                        this.mClientMessage.setScrollBarStyle(16777216);
                        this.mClientMessage.setScrollbarFadingEnabled(false);
                    }
                    this.mClientMessage.setText(replaceAll);
                }
            }
        } else {
            Log.d("KeyguardRMMView", "mRemoteLockInfo.message is null");
            this.mClientMessage.setVisibility(8);
        }
        CharSequence charSequence2 = this.mRemoteLockInfo.clientName;
        if (charSequence2 != null) {
            this.mClientName = charSequence2.toString();
        }
        CharSequence charSequence3 = this.mRemoteLockInfo.phoneNumber;
        if (charSequence3 == null || this.mClientName == null) {
            Log.d("KeyguardRMMView", "mRemoteLockInfo.phoneNumber is null");
        } else {
            this.mPhoneNumber = charSequence3.toString();
            SystemUITextView systemUITextView = this.mClientContact;
            if (systemUITextView != null) {
                systemUITextView.setText(this.mClientName);
            }
        }
        if (this.mClientContact == null || this.mCallButton == null) {
            return;
        }
        if (!this.mIsVoiceCapacity || (str = this.mPhoneNumber) == null || str.isEmpty()) {
            this.mClientContact.setVisibility(8);
            this.mCallButton.setVisibility(8);
            return;
        }
        this.mClientContact.setVisibility(0);
        this.mCallButton.setText(this.mPhoneNumber);
        this.mCallButton.setVisibility(0);
        this.mCallButton.setOnClickListener(new KeyguardRMMViewController$$ExternalSyntheticLambda0(this, 1));
        this.mClientContact.setVisibility(0);
        this.mCallButton.setVisibility(0);
    }

    public final void updateDrawableTint() {
        if (this.mCallButton == null) {
            return;
        }
        boolean isWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("background");
        for (Drawable drawable : this.mCallButton.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(isWhiteKeyguardWallpaper ? getResources().getColor(R.color.kg_fmm_drawable_tint_white_bg) : getResources().getColor(R.color.kg_fmm_drawable_tint), PorterDuff.Mode.SRC_IN);
            }
        }
    }

    public final void updateRMMLayout() {
        postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardRMMViewController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardRMMViewController keyguardRMMViewController = KeyguardRMMViewController.this;
                LinearLayout linearLayout = (LinearLayout) ((KeyguardRMMView) keyguardRMMViewController.mView).findViewById(R.id.rmm_message);
                LinearLayout linearLayout2 = (LinearLayout) ((KeyguardRMMView) keyguardRMMViewController.mView).findViewById(R.id.rmm_contact_area);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
                if (keyguardRMMViewController.mCurrentOrientation == 1) {
                    layoutParams.weight = 1.0f;
                    layoutParams2.weight = 0.0f;
                } else {
                    layoutParams.weight = 1.0f;
                    layoutParams2.weight = 1.0f;
                }
                linearLayout.setLayoutParams(layoutParams);
                linearLayout2.setLayoutParams(layoutParams2);
                boolean z = keyguardRMMViewController.mCurrentOrientation == 1;
                boolean isLandscapePolicyAllowed = keyguardRMMViewController.isLandscapePolicyAllowed();
                Resources resources = keyguardRMMViewController.getResources();
                LinearLayout linearLayout3 = keyguardRMMViewController.mMessageArea;
                if (linearLayout3 != null) {
                    LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) linearLayout3.getLayoutParams();
                    layoutParams3.topMargin = resources.getDimensionPixelSize(R.dimen.kg_biometric_view_height) + resources.getDimensionPixelSize(R.dimen.status_bar_height) + (z ? resources.getDimensionPixelSize(R.dimen.kg_lock_icon_top_margin) : 0);
                    layoutParams3.weight = isLandscapePolicyAllowed ? 0.0f : 1.0f;
                    keyguardRMMViewController.mMessageArea.setGravity(isLandscapePolicyAllowed ? 49 : 17);
                    int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.kg_message_area_padding_side);
                    if (isLandscapePolicyAllowed) {
                        keyguardRMMViewController.mMessageArea.setPadding(0, 0, dimensionPixelSize, 0);
                    }
                    keyguardRMMViewController.mMessageArea.setLayoutParams(layoutParams3);
                }
                SystemUITextView systemUITextView = keyguardRMMViewController.mClientMessage;
                if (systemUITextView != null) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) systemUITextView.getLayoutParams();
                    marginLayoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.kg_fmm_message_margin_top);
                    keyguardRMMViewController.mClientMessage.setLayoutParams(marginLayoutParams);
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
        Log.d("KeyguardRMMView", "verifyPasswordAndUnlock()");
        byte[] passwordText = getPasswordText();
        ((KeyguardRMMView) this.mView).setPasswordEntryInputEnabled(false);
        if (passwordText.length > 3) {
            try {
                getLockSettings().checkRemoteLockPassword(2, passwordText, KeyguardUpdateMonitor.getCurrentUser(), this.mCheckPasswordCallback);
            } catch (RemoteException unused) {
                Log.d("KeyguardRMMView", "Can't connect RMM_LOCK");
            }
            Arrays.fill(passwordText, (byte) 0);
            return;
        }
        Resources resources = getResources();
        ((KeyguardRMMView) this.mView).getClass();
        String string = resources.getString(R.string.kg_incorrect_pin);
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        keyguardSecMessageAreaController.setMessage(string, false);
        keyguardSecMessageAreaController.displayFailedAnimation();
        ((KeyguardRMMView) this.mView).resetPasswordText(true, true);
        ((KeyguardRMMView) this.mView).setPasswordEntryInputEnabled(true);
        Arrays.fill(passwordText, (byte) 0);
    }
}
