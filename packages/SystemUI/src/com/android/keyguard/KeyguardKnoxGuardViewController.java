package com.android.keyguard;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.RemoteLockInfo;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.settingslib.net.DataUsageController;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.widget.SystemUIEditText;
import com.android.systemui.widget.SystemUIImageView;
import com.android.systemui.widget.SystemUITextView;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

public final class KeyguardKnoxGuardViewController extends KeyguardSecAbsKeyInputViewController {
    public int mAttemptCount;
    public final AnonymousClass2 mCheckPasswordCallback;
    public final SystemUITextView mCompanyNameTextView;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass1 mConfigurationListener;
    public CountDownTimer mCountdownTimer;
    public int mCurrentOrientation;
    public final RelativeLayout mCustomerAppContainer;
    public final SystemUITextView mCustomerAppHeaderTextView;
    public final SystemUIImageView mCustomerAppImageView;
    public final SystemUIImageView mDataButton;
    public final DataUsageController mDataController;
    public final AnonymousClass5 mHandler;
    public final InputMethodManager mImm;
    public final IntentFilter mIntentFilter;
    public final SystemUITextView mLockMessageTextView;
    public ILockSettings mLockSettingsService;
    public final LinearLayout mMessageContainer;
    public final AnonymousClass6 mMobileDataObserver;
    public final RelativeLayout mOptionContainer;
    public final SystemUITextView mOptionHeaderTextView;
    public final RelativeLayout mPhoneContainer;
    public final SystemUITextView mPhoneHeaderTextView;
    public final SystemUITextView mPhoneSubTextTextView;
    public final SystemUIEditText mPinEditText;
    public final SystemUITextView mPinMessageTextView;
    public final AnonymousClass7 mReceiver;
    public RemoteLockInfo mRemoteLockInfo;
    public final ScreenLifecycle mScreenLifecycle;
    public final AnonymousClass4 mScreenObserver;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public boolean mSkipPin;
    public final TelephonyManager mTelephonyManager;
    public final LinearLayout mTopContainer;
    public final KeyguardUpdateMonitorCallback mUpdateCallback;
    public final SystemUIImageView mWifiButton;
    public final WifiManager mWifiManager;

    /* renamed from: com.android.keyguard.KeyguardKnoxGuardViewController$3, reason: invalid class name */
    class AnonymousClass3 extends KeyguardUpdateMonitorCallback {
        public AnonymousClass3() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onRemoteLockInfoChanged() {
            Log.d("KeyguardKnoxGuardView", "onRemoteLockInfoChanged");
            post(new KeyguardKnoxGuardViewController$$ExternalSyntheticLambda8(this, 1));
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onSimStateChanged(int i, int i2, int i3) {
            RecyclerView$$ExternalSyntheticOutline0.m(i3, "KeyguardKnoxGuardView", RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "onSimStateChanged subID : ", ", slotId : ", ", simState : "));
            if (i3 == 1 || i3 == 5) {
                KeyguardKnoxGuardViewController.this.updateNetworkSettingsButton();
            }
        }
    }

    /* renamed from: -$$Nest$mcheckUnlockAttempts, reason: not valid java name */
    public static void m833$$Nest$mcheckUnlockAttempts(KeyguardKnoxGuardViewController keyguardKnoxGuardViewController, int i, long j) {
        long j2;
        keyguardKnoxGuardViewController.getClass();
        Log.d("KeyguardKnoxGuardView", "checkUnlockAttempts " + i);
        ((KeyguardKnoxGuardView) keyguardKnoxGuardViewController.mView).setPasswordEntryInputEnabled(true);
        SelectedUserInteractor selectedUserInteractor = keyguardKnoxGuardViewController.mSelectedUserInteractor;
        if (i == 0) {
            try {
                keyguardKnoxGuardViewController.getLockSettings().setRemoteLock(selectedUserInteractor.getSelectedUserId(), new RemoteLockInfo.Builder(3, false).build());
            } catch (RemoteException e) {
                Log.d("KeyguardKnoxGuardView", "Failed KNOXGUARD LOCK clear!!" + e);
            }
            KeyguardUnlockInfo.setUnlockTriggerByRemoteLock(3);
            keyguardKnoxGuardViewController.getKeyguardSecurityCallback().dismiss(true, selectedUserInteractor.getSelectedUserId(), keyguardKnoxGuardViewController.mSecurityMode);
            return;
        }
        RemoteLockInfo remoteLockInfo = keyguardKnoxGuardViewController.mRemoteLockInfo;
        if (remoteLockInfo != null) {
            if (j > 0 && remoteLockInfo.lockTimeOut != j) {
                Log.d("KeyguardKnoxGuardView", "update lockTimeout " + keyguardKnoxGuardViewController.mRemoteLockInfo.lockTimeOut + " => " + j);
                keyguardKnoxGuardViewController.mRemoteLockInfo.lockTimeOut = j;
            }
            RemoteLockInfo remoteLockInfo2 = keyguardKnoxGuardViewController.mRemoteLockInfo;
            int i2 = remoteLockInfo2.allowFailCount;
            if (i2 > 0 && remoteLockInfo2.lockTimeOut > 0 && i > 0 && i % i2 == 0) {
                ((KeyguardKnoxGuardView) keyguardKnoxGuardViewController.mView).setPasswordEntryInputEnabled(false);
                int selectedUserId = selectedUserInteractor.getSelectedUserId();
                if (keyguardKnoxGuardViewController.mRemoteLockInfo == null) {
                    j2 = -1;
                } else {
                    long currentTimeMillis = System.currentTimeMillis() + keyguardKnoxGuardViewController.mRemoteLockInfo.lockTimeOut;
                    keyguardKnoxGuardViewController.setLong(selectedUserId, Anchor$$ExternalSyntheticOutline0.m(keyguardKnoxGuardViewController.mRemoteLockInfo.lockType, "remotelockscreen.lockoutdeadline", new StringBuilder()), currentTimeMillis);
                    j2 = currentTimeMillis;
                }
                int selectedUserId2 = selectedUserInteractor.getSelectedUserId();
                if (keyguardKnoxGuardViewController.mRemoteLockInfo == null) {
                    i = -1;
                } else {
                    keyguardKnoxGuardViewController.setLong(selectedUserId2, Anchor$$ExternalSyntheticOutline0.m(keyguardKnoxGuardViewController.mRemoteLockInfo.lockType, "remotelockscreen.failedunlockcount", new StringBuilder()), i);
                }
                keyguardKnoxGuardViewController.mAttemptCount = i;
                keyguardKnoxGuardViewController.handleAttemptLockout(j2);
                return;
            }
        }
        Resources resources = keyguardKnoxGuardViewController.getResources();
        ((KeyguardKnoxGuardView) keyguardKnoxGuardViewController.mView).getClass();
        String string = resources.getString(R.string.kg_remote_lock_incorrect_pin);
        SystemUITextView systemUITextView = keyguardKnoxGuardViewController.mPinMessageTextView;
        systemUITextView.setText(string);
        systemUITextView.setVisibility(0);
        new Handler().postDelayed(new KeyguardKnoxGuardViewController$$ExternalSyntheticLambda8(keyguardKnoxGuardViewController, 0), 3000L);
    }

    /* JADX WARN: Type inference failed for: r1v16, types: [com.android.keyguard.KeyguardKnoxGuardViewController$6] */
    /* JADX WARN: Type inference failed for: r1v18, types: [com.android.keyguard.KeyguardKnoxGuardViewController$7] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.keyguard.KeyguardKnoxGuardViewController$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.keyguard.KeyguardKnoxGuardViewController$2] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.keyguard.KeyguardKnoxGuardViewController$4] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.keyguard.KeyguardKnoxGuardViewController$5] */
    public KeyguardKnoxGuardViewController(KeyguardKnoxGuardView keyguardKnoxGuardView, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor, InputMethodManager inputMethodManager, TelephonyManager telephonyManager, WifiManager wifiManager, NetworkController networkController, ScreenLifecycle screenLifecycle) {
        super(keyguardKnoxGuardView, configurationController, vibrationUtil, accessibilityManager, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, falsingCollector, emergencyButtonController, featureFlags, selectedUserInteractor);
        this.mRemoteLockInfo = null;
        this.mLockSettingsService = null;
        this.mSkipPin = false;
        this.mAttemptCount = 0;
        this.mCountdownTimer = null;
        this.mCurrentOrientation = 1;
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardKnoxGuardViewController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onOrientationChanged(int i) {
                KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = KeyguardKnoxGuardViewController.this;
                if (keyguardKnoxGuardViewController.mCurrentOrientation != i) {
                    keyguardKnoxGuardViewController.mCurrentOrientation = i;
                    keyguardKnoxGuardViewController.updateTopContainer();
                }
            }
        };
        this.mCheckPasswordCallback = new IRemoteCallback.Stub() { // from class: com.android.keyguard.KeyguardKnoxGuardViewController.2
            public final void sendResult(Bundle bundle) {
                int i = bundle.getInt("result");
                long j = bundle.getLong("timeout");
                Log.d("KeyguardKnoxGuardView", "Unlock attempt result : " + i + " timeout : " + j);
                removeMessages(2);
                sendMessage(obtainMessage(2, i, 0, Long.valueOf(j)));
            }
        };
        this.mUpdateCallback = new AnonymousClass3();
        this.mScreenObserver = new ScreenLifecycle.Observer() { // from class: com.android.keyguard.KeyguardKnoxGuardViewController.4
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurnedOff() {
                KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = KeyguardKnoxGuardViewController.this;
                keyguardKnoxGuardViewController.mHandler.removeMessages(3);
                AnonymousClass5 anonymousClass5 = keyguardKnoxGuardViewController.mHandler;
                anonymousClass5.sendMessage(anonymousClass5.obtainMessage(3));
            }
        };
        this.mHandler = new Handler(Looper.myLooper()) { // from class: com.android.keyguard.KeyguardKnoxGuardViewController.5
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = KeyguardKnoxGuardViewController.this;
                if (i == 2) {
                    KeyguardKnoxGuardViewController.m833$$Nest$mcheckUnlockAttempts(keyguardKnoxGuardViewController, message.arg1, ((Long) message.obj).longValue());
                } else {
                    if (i != 3) {
                        return;
                    }
                    keyguardKnoxGuardViewController.resetPinErrorMessage();
                }
            }
        };
        this.mConfigurationController = configurationController;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mImm = inputMethodManager;
        this.mWifiManager = wifiManager;
        this.mTelephonyManager = telephonyManager;
        this.mDataController = ((NetworkControllerImpl) networkController).mDataUsageController;
        this.mScreenLifecycle = screenLifecycle;
        this.mMobileDataObserver = new ContentObserver(new Handler()) { // from class: com.android.keyguard.KeyguardKnoxGuardViewController.6
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                DataUsageController dataUsageController = KeyguardKnoxGuardViewController.this.mDataController;
                boolean z2 = dataUsageController != null && dataUsageController.isMobileDataEnabled();
                if (KeyguardKnoxGuardViewController.this.mDataButton != null) {
                    StringBuilder m = RowView$$ExternalSyntheticOutline0.m("mobileData settings changed mobileDataEnabled ", " visibility :", z2);
                    m.append(KeyguardKnoxGuardViewController.this.mDataButton.getVisibility());
                    Log.d("KeyguardKnoxGuardView", m.toString());
                    if (!z2 || KeyguardKnoxGuardViewController.this.mDataButton.getVisibility() != 0) {
                        KeyguardKnoxGuardViewController.this.mDataButton.setVisibility(0);
                        return;
                    }
                    KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = KeyguardKnoxGuardViewController.this;
                    keyguardKnoxGuardViewController.showToast(keyguardKnoxGuardViewController.getContext().getString(R.string.kg_knox_guard_mobile_data_turned_on_toast));
                    KeyguardKnoxGuardViewController.this.mDataButton.setVisibility(8);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        this.mIntentFilter = intentFilter;
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.keyguard.KeyguardKnoxGuardViewController.7
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction())) {
                    boolean z = intent.getIntExtra("wifi_state", 4) == 3;
                    if (KeyguardKnoxGuardViewController.this.mWifiButton != null) {
                        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("WIFI_STATE_CHANGED_ACTION received : enabled = ", " visibility :", z);
                        m.append(KeyguardKnoxGuardViewController.this.mWifiButton.getVisibility());
                        Log.d("KeyguardKnoxGuardView", m.toString());
                        if (z && KeyguardKnoxGuardViewController.this.mWifiButton.getVisibility() == 0) {
                            KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = KeyguardKnoxGuardViewController.this;
                            keyguardKnoxGuardViewController.showToast(keyguardKnoxGuardViewController.getContext().getString(R.string.kg_knox_guard_wifi_turned_on_toast));
                            KeyguardKnoxGuardViewController.this.mWifiButton.setVisibility(8);
                        }
                    }
                }
            }
        };
        this.mTopContainer = (LinearLayout) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.top_container);
        this.mMessageContainer = (LinearLayout) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.knox_guard_message_container);
        this.mCompanyNameTextView = (SystemUITextView) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.keyguard_knox_guard_company_name);
        this.mLockMessageTextView = (SystemUITextView) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.keyguard_knox_guard_lock_message);
        this.mPinEditText = (SystemUIEditText) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.keyguard_knox_guard_pin_view);
        this.mPinMessageTextView = (SystemUITextView) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.keyguard_knox_pin_message);
        this.mWifiButton = (SystemUIImageView) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.keyguard_wifi_on_button);
        this.mDataButton = (SystemUIImageView) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.keyguard_data_on_button);
        this.mOptionHeaderTextView = (SystemUITextView) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.option_header);
        this.mCustomerAppContainer = (RelativeLayout) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.app_container);
        this.mPhoneContainer = (RelativeLayout) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.phone_container);
        this.mCustomerAppHeaderTextView = (SystemUITextView) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.app_header);
        this.mPhoneHeaderTextView = (SystemUITextView) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.phone_header);
        this.mPhoneSubTextTextView = (SystemUITextView) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.phone_sub_text);
        this.mOptionContainer = (RelativeLayout) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.option_container);
        this.mEcaView = ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.keyguard_selector_fade_container);
        this.mCustomerAppImageView = (SystemUIImageView) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.app_image);
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final int getInitialMessageResId() {
        return 0;
    }

    public final ILockSettings getLockSettings() {
        if (this.mLockSettingsService == null) {
            this.mLockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
        }
        return this.mLockSettingsService;
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void handleAttemptLockout(long j) {
        ((KeyguardKnoxGuardView) this.mView).setPasswordEntryEnabled(false);
        CountDownTimer countDownTimer = this.mCountdownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.mCountdownTimer = null;
        }
        this.mCountdownTimer = new CountDownTimer(j - System.currentTimeMillis(), 1000L) { // from class: com.android.keyguard.KeyguardKnoxGuardViewController.9
            @Override // android.os.CountDownTimer
            public final void onFinish() {
                KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = KeyguardKnoxGuardViewController.this;
                keyguardKnoxGuardViewController.mCountdownTimer = null;
                keyguardKnoxGuardViewController.resetPinErrorMessage();
                ((KeyguardKnoxGuardView) ((ViewController) KeyguardKnoxGuardViewController.this).mView).setPasswordEntryEnabled(true);
                KeyguardKnoxGuardViewController.this.resetState();
            }

            @Override // android.os.CountDownTimer
            public final void onTick(long j2) {
                int i = (int) (j2 / 1000);
                int i2 = i / 60;
                int i3 = i2 / 60;
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onTick() secondsRemaining: ", "KeyguardKnoxGuardView");
                String format = i > 3600 ? i3 > 1 ? String.format(KeyguardKnoxGuardViewController.this.getResources().getString(R.string.kg_knox_guard_incorrect_pin_remaining_hours_left_phone, Integer.valueOf(KeyguardKnoxGuardViewController.this.mAttemptCount), Integer.valueOf(i3)), new Object[0]) : String.format(KeyguardKnoxGuardViewController.this.getResources().getString(R.string.kg_knox_guard_incorrect_pin_remaining_hour_left_phone, Integer.valueOf(KeyguardKnoxGuardViewController.this.mAttemptCount)), new Object[0]) : i > 60 ? i2 > 1 ? String.format(KeyguardKnoxGuardViewController.this.getResources().getString(R.string.kg_knox_guard_incorrect_pin_remaining_mins_left_phone, Integer.valueOf(KeyguardKnoxGuardViewController.this.mAttemptCount), Integer.valueOf(i2)), new Object[0]) : String.format(KeyguardKnoxGuardViewController.this.getResources().getString(R.string.kg_knox_guard_incorrect_pin_remaining_min_left_phone, Integer.valueOf(KeyguardKnoxGuardViewController.this.mAttemptCount)), new Object[0]) : i > 0 ? i > 1 ? String.format(KeyguardKnoxGuardViewController.this.getResources().getString(R.string.kg_knox_guard_incorrect_pin_remaining_seconds_left_phone, Integer.valueOf(KeyguardKnoxGuardViewController.this.mAttemptCount), Integer.valueOf(i)), new Object[0]) : String.format(KeyguardKnoxGuardViewController.this.getResources().getString(R.string.kg_knox_guard_incorrect_pin_remaining_second_left_phone, Integer.valueOf(KeyguardKnoxGuardViewController.this.mAttemptCount)), new Object[0]) : null;
                KeyguardKnoxGuardViewController.this.mPinMessageTextView.setText(format);
                KeyguardKnoxGuardViewController.this.mPinMessageTextView.setVisibility(format == null ? 8 : 0);
            }
        }.start();
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardSecurityView
    public final boolean needsInput() {
        return true;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public final void onPause() {
        this.mImm.semForceHideSoftInput();
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void onUserInput() {
        if (getKeyguardSecurityCallback() != null) {
            getKeyguardSecurityCallback().userActivity();
        }
        resetPinErrorMessage();
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        Context context = getContext();
        ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.registerCallback(this.mUpdateCallback);
        this.mScreenLifecycle.addObserver(this.mScreenObserver);
        ContentResolver contentResolver = context.getContentResolver();
        Uri uriFor = Settings.Global.getUriFor(SettingsHelper.INDEX_MOBILE_DATA);
        AnonymousClass6 anonymousClass6 = this.mMobileDataObserver;
        contentResolver.registerContentObserver(uriFor, false, anonymousClass6);
        contentResolver.registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_DATA_ROAMING), false, anonymousClass6);
        context.registerReceiver(this.mReceiver, this.mIntentFilter, null, null);
        if (DeviceState.shouldEnableKeyguardScreenRotation(context)) {
            this.mCurrentOrientation = getContext().getResources().getConfiguration().orientation;
            ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        }
        final int i = 0;
        View.OnClickListener onClickListener = new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticLambda1
            public final /* synthetic */ KeyguardKnoxGuardViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = i;
                KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = this.f$0;
                switch (i2) {
                    case 0:
                        keyguardKnoxGuardViewController.onUserInput();
                        keyguardKnoxGuardViewController.mImm.showSoftInput(keyguardKnoxGuardViewController.mPinEditText, 1);
                        break;
                    default:
                        keyguardKnoxGuardViewController.getClass();
                        Log.d("KeyguardKnoxGuardView", "mWifiButton OnClick");
                        keyguardKnoxGuardViewController.mWifiManager.setWifiEnabled(true);
                        break;
                }
            }
        };
        SystemUIEditText systemUIEditText = this.mPinEditText;
        systemUIEditText.setOnClickListener(onClickListener);
        systemUIEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticLambda2
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = KeyguardKnoxGuardViewController.this;
                keyguardKnoxGuardViewController.getClass();
                if ((keyEvent == null || keyEvent.getKeyCode() != 66) && i2 != 6) {
                    return false;
                }
                keyguardKnoxGuardViewController.verifyPasswordAndUnlock();
                return false;
            }
        });
        systemUIEditText.addTextChangedListener(new TextWatcher() { // from class: com.android.keyguard.KeyguardKnoxGuardViewController.8
            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
                if (KeyguardKnoxGuardViewController.this.getKeyguardSecurityCallback() != null) {
                    KeyguardKnoxGuardViewController.this.getKeyguardSecurityCallback().userActivity();
                }
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }
        });
        final int i2 = 1;
        this.mWifiButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticLambda1
            public final /* synthetic */ KeyguardKnoxGuardViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22 = i2;
                KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = this.f$0;
                switch (i22) {
                    case 0:
                        keyguardKnoxGuardViewController.onUserInput();
                        keyguardKnoxGuardViewController.mImm.showSoftInput(keyguardKnoxGuardViewController.mPinEditText, 1);
                        break;
                    default:
                        keyguardKnoxGuardViewController.getClass();
                        Log.d("KeyguardKnoxGuardView", "mWifiButton OnClick");
                        keyguardKnoxGuardViewController.mWifiManager.setWifiEnabled(true);
                        break;
                }
            }
        });
        this.mDataButton.setOnClickListener(new KeyguardKnoxGuardViewController$$ExternalSyntheticLambda4(this, context, 0));
        SystemUIImageView systemUIImageView = (SystemUIImageView) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.phone_image);
        SystemUIImageView systemUIImageView2 = (SystemUIImageView) ((KeyguardKnoxGuardView) this.mView).findViewById(R.id.option_image);
        systemUIImageView.setImageResource(R.drawable.kg_knox_guard_ic_call);
        systemUIImageView2.setImageResource(R.drawable.kg_knox_guard_ic_options);
        resetPinErrorMessage();
        setKnoxGuardInfo();
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.removeCallback(this.mUpdateCallback);
        this.mScreenLifecycle.removeObserver(this.mScreenObserver);
        getContext().getContentResolver().unregisterContentObserver(this.mMobileDataObserver);
        getContext().unregisterReceiver(this.mReceiver);
        if (DeviceState.shouldEnableKeyguardScreenRotation(getContext())) {
            ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        }
    }

    public final void resetPinErrorMessage() {
        this.mPinMessageTextView.setVisibility(this.mCountdownTimer != null ? 0 : 8);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void resetState() {
        long j;
        long j2;
        int i;
        SelectedUserInteractor selectedUserInteractor = this.mSelectedUserInteractor;
        int selectedUserId = selectedUserInteractor.getSelectedUserId(false);
        long j3 = 0;
        if (this.mRemoteLockInfo == null) {
            j2 = -1;
        } else {
            try {
                j = getLockSettings().getLong(Anchor$$ExternalSyntheticOutline0.m(this.mRemoteLockInfo.lockType, "remotelockscreen.lockoutdeadline", new StringBuilder()), 0L, selectedUserId);
            } catch (RemoteException unused) {
                j = 0;
            }
            long currentTimeMillis = System.currentTimeMillis();
            if (j > currentTimeMillis || j == 0) {
                if (j > currentTimeMillis + this.mRemoteLockInfo.lockTimeOut) {
                    j = System.currentTimeMillis() + this.mRemoteLockInfo.lockTimeOut;
                    setLong(selectedUserId, Anchor$$ExternalSyntheticOutline0.m(this.mRemoteLockInfo.lockType, "remotelockscreen.lockoutdeadline", new StringBuilder()), j);
                }
                j2 = j;
            } else {
                setLong(selectedUserId, Anchor$$ExternalSyntheticOutline0.m(this.mRemoteLockInfo.lockType, "remotelockscreen.lockoutdeadline", new StringBuilder()), 0L);
                j2 = 0;
            }
        }
        if (j2 > 0) {
            int selectedUserId2 = selectedUserInteractor.getSelectedUserId(false);
            if (this.mRemoteLockInfo == null) {
                i = -1;
            } else {
                try {
                    j3 = getLockSettings().getLong(Anchor$$ExternalSyntheticOutline0.m(this.mRemoteLockInfo.lockType, "remotelockscreen.failedunlockcount", new StringBuilder()), 0L, selectedUserId2);
                } catch (RemoteException unused2) {
                }
                i = (int) j3;
            }
            this.mAttemptCount = i;
            handleAttemptLockout(j2);
        }
    }

    public final void setKnoxGuardInfo() {
        String str;
        SystemUITextView systemUITextView;
        SystemUITextView systemUITextView2;
        SystemUITextView systemUITextView3;
        SystemUITextView systemUITextView4;
        Log.d("KeyguardKnoxGuardView", "setKnoxGuardInfo");
        final KeyguardSecurityCallback keyguardSecurityCallback = getKeyguardSecurityCallback();
        RemoteLockInfo remoteLockInfo = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.getRemoteLockInfo();
        this.mRemoteLockInfo = remoteLockInfo;
        ApplicationInfo applicationInfo = null;
        if (remoteLockInfo == null) {
            Log.d("KeyguardKnoxGuardView", "mRemoteLockInfo is null - dismiss");
            CountDownTimer countDownTimer = this.mCountdownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
                this.mCountdownTimer = null;
                resetPinErrorMessage();
                ((KeyguardKnoxGuardView) this.mView).setPasswordEntryEnabled(true);
            }
            keyguardSecurityCallback.dismiss(true, this.mSelectedUserInteractor.getSelectedUserId(false), this.mSecurityMode);
            return;
        }
        CharSequence charSequence = remoteLockInfo.clientName;
        if (charSequence != null) {
            str = charSequence.toString().trim();
            if (str.isEmpty()) {
                Log.d("KeyguardKnoxGuardView", "mRemoteLockInfo.clientName is empty");
            }
        } else {
            Log.d("KeyguardKnoxGuardView", "mRemoteLockInfo.clientName is null");
            str = "";
        }
        SystemUITextView systemUITextView5 = this.mCompanyNameTextView;
        if (systemUITextView5 != null) {
            systemUITextView5.setText(str);
        }
        CharSequence charSequence2 = this.mRemoteLockInfo.message;
        if (charSequence2 != null) {
            String charSequence3 = charSequence2.toString();
            if (charSequence3 != null && (systemUITextView4 = this.mLockMessageTextView) != null) {
                systemUITextView4.setMovementMethod(new ScrollingMovementMethod());
                systemUITextView4.setText(charSequence3);
            }
        } else {
            Log.d("KeyguardKnoxGuardView", "mRemoteLockInfo.message is null");
        }
        if (this.mRemoteLockInfo.skipSupportContainer) {
            this.mOptionContainer.setVisibility(8);
            Log.d("KeyguardKnoxGuardView", "mRemoteLockInfo.skipSupportContainer is true");
        } else {
            this.mOptionContainer.setVisibility(0);
            this.mOptionHeaderTextView.setText(getResources().getString(R.string.kg_remote_lock_option_header));
            this.mOptionContainer.setOnClickListener(new KeyguardKnoxGuardViewController$$ExternalSyntheticLambda4(this, keyguardSecurityCallback, 1));
        }
        Bundle bundle = this.mRemoteLockInfo.bundle;
        if (bundle == null || bundle.getCharSequence("customer_package_name") == null || (systemUITextView3 = this.mCustomerAppHeaderTextView) == null) {
            RelativeLayout relativeLayout = this.mCustomerAppContainer;
            if (relativeLayout != null) {
                relativeLayout.setVisibility(8);
            }
            Log.d("KeyguardKnoxGuardView", "mRemoteLockInfo.bundle is null");
        } else {
            Bundle bundle2 = this.mRemoteLockInfo.bundle;
            final String charSequence4 = bundle2.getCharSequence("customer_package_name").toString();
            CharSequence charSequence5 = bundle2.getCharSequence("customer_app_name");
            PackageManager packageManager = getContext().getPackageManager();
            try {
                applicationInfo = packageManager.getApplicationInfo(charSequence4, 0);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("KeyguardKnoxGuardView", "NameNotFoundException while updating icon : " + e.getMessage());
            }
            boolean z = !TextUtils.isEmpty(charSequence5);
            SystemUIImageView systemUIImageView = this.mCustomerAppImageView;
            if (applicationInfo != null) {
                if (!z) {
                    charSequence5 = applicationInfo.loadLabel(packageManager).toString();
                }
                systemUITextView3.setText(charSequence5);
                Drawable loadIcon = applicationInfo.loadIcon(packageManager, true, 0);
                if (loadIcon == null) {
                    loadIcon = applicationInfo.loadIcon(packageManager);
                }
                Bitmap createBitmap = Bitmap.createBitmap(getResources().getDimensionPixelSize(R.dimen.kg_knox_guard_contact_image_width), getResources().getDimensionPixelSize(R.dimen.kg_knox_guard_contact_image_height), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                loadIcon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                loadIcon.draw(canvas);
                createBitmap.setDensity(getResources().getDisplayMetrics().densityDpi);
                systemUIImageView.setImageDrawable(new BitmapDrawable(getResources(), createBitmap));
            } else {
                if (!z) {
                    charSequence5 = charSequence4;
                }
                systemUITextView3.setText(charSequence5);
                systemUIImageView.setImageResource(R.drawable.kg_knox_guard_ic_default_app);
            }
            Log.d("KeyguardKnoxGuardView", "customerPackageName : " + charSequence4 + ",  isAppNameExist : " + z);
            if (this.mCustomerAppContainer != null && !charSequence4.isEmpty()) {
                this.mCustomerAppContainer.setVisibility(0);
                final int i = 0;
                this.mCustomerAppContainer.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticLambda6
                    public final /* synthetic */ KeyguardKnoxGuardViewController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = this.f$0;
                                String str2 = charSequence4;
                                KeyguardSecurityCallback keyguardSecurityCallback2 = keyguardSecurityCallback;
                                keyguardKnoxGuardViewController.resetPinErrorMessage();
                                try {
                                    Log.d("KeyguardKnoxGuardView", "click customer app button");
                                    Intent intent = new Intent("com.samsung.kgclient.intent.action.CUSTOMER_APP");
                                    intent.setClassName("com.samsung.android.kgclient", "com.samsung.android.kgclient.receiver.KGIntentReceiver");
                                    if (str2 != null) {
                                        intent.putExtra("customerPackageName", str2);
                                    }
                                    intent.addFlags(32);
                                    keyguardKnoxGuardViewController.getContext().sendBroadcastAsUser(intent, UserHandle.CURRENT, "com.samsung.android.knoxguard.STATUS");
                                    if (keyguardSecurityCallback2 != null) {
                                        keyguardSecurityCallback2.userActivity();
                                    }
                                } catch (ActivityNotFoundException e2) {
                                    Log.w("KeyguardKnoxGuardView", "Can't find the component " + e2);
                                }
                                ((KeyguardAbsKeyInputViewController) keyguardKnoxGuardViewController).mKeyguardUpdateMonitor.reportEmergencyCallAction();
                                break;
                            default:
                                KeyguardKnoxGuardViewController keyguardKnoxGuardViewController2 = this.f$0;
                                String str3 = charSequence4;
                                KeyguardSecurityCallback keyguardSecurityCallback3 = keyguardSecurityCallback;
                                keyguardKnoxGuardViewController2.resetPinErrorMessage();
                                TelephonyManager telephonyManager = keyguardKnoxGuardViewController2.mTelephonyManager;
                                if (!(telephonyManager == null ? false : telephonyManager.isVoiceCapable())) {
                                    Log.d("KeyguardKnoxGuardView", "not support call");
                                    keyguardKnoxGuardViewController2.showToast(keyguardKnoxGuardViewController2.getContext().getString(R.string.kg_knox_guard_call_not_support_toast));
                                    break;
                                } else {
                                    Intent intent2 = new Intent("android.intent.action.CALL_PRIVILEGED", Uri.fromParts("tel", str3, null));
                                    intent2.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                    try {
                                        Log.d("KeyguardKnoxGuardView", "click call button");
                                        keyguardKnoxGuardViewController2.getContext().startActivityAsUser(intent2, UserHandle.CURRENT);
                                        if (keyguardSecurityCallback3 != null) {
                                            keyguardSecurityCallback3.userActivity();
                                            break;
                                        }
                                    } catch (ActivityNotFoundException e3) {
                                        Log.w("KeyguardKnoxGuardView", "Can't find the component " + e3);
                                        return;
                                    }
                                }
                                break;
                        }
                    }
                });
            }
        }
        CharSequence charSequence6 = this.mRemoteLockInfo.phoneNumber;
        if (charSequence6 == null || (systemUITextView = this.mPhoneSubTextTextView) == null || (systemUITextView2 = this.mPhoneHeaderTextView) == null) {
            this.mPhoneContainer.setVisibility(8);
            Log.d("KeyguardKnoxGuardView", "mRemoteLockInfo.phoneNumber is null");
        } else {
            final String trim = charSequence6.toString().trim();
            if (trim.isEmpty()) {
                this.mPhoneContainer.setVisibility(8);
            } else {
                this.mPhoneContainer.setVisibility(0);
                systemUITextView2.setText(getResources().getString(R.string.kg_remote_lock_accessibility_call, str));
                systemUITextView.setText(trim);
                final int i2 = 1;
                this.mPhoneContainer.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticLambda6
                    public final /* synthetic */ KeyguardKnoxGuardViewController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = this.f$0;
                                String str2 = trim;
                                KeyguardSecurityCallback keyguardSecurityCallback2 = keyguardSecurityCallback;
                                keyguardKnoxGuardViewController.resetPinErrorMessage();
                                try {
                                    Log.d("KeyguardKnoxGuardView", "click customer app button");
                                    Intent intent = new Intent("com.samsung.kgclient.intent.action.CUSTOMER_APP");
                                    intent.setClassName("com.samsung.android.kgclient", "com.samsung.android.kgclient.receiver.KGIntentReceiver");
                                    if (str2 != null) {
                                        intent.putExtra("customerPackageName", str2);
                                    }
                                    intent.addFlags(32);
                                    keyguardKnoxGuardViewController.getContext().sendBroadcastAsUser(intent, UserHandle.CURRENT, "com.samsung.android.knoxguard.STATUS");
                                    if (keyguardSecurityCallback2 != null) {
                                        keyguardSecurityCallback2.userActivity();
                                    }
                                } catch (ActivityNotFoundException e2) {
                                    Log.w("KeyguardKnoxGuardView", "Can't find the component " + e2);
                                }
                                ((KeyguardAbsKeyInputViewController) keyguardKnoxGuardViewController).mKeyguardUpdateMonitor.reportEmergencyCallAction();
                                break;
                            default:
                                KeyguardKnoxGuardViewController keyguardKnoxGuardViewController2 = this.f$0;
                                String str3 = trim;
                                KeyguardSecurityCallback keyguardSecurityCallback3 = keyguardSecurityCallback;
                                keyguardKnoxGuardViewController2.resetPinErrorMessage();
                                TelephonyManager telephonyManager = keyguardKnoxGuardViewController2.mTelephonyManager;
                                if (!(telephonyManager == null ? false : telephonyManager.isVoiceCapable())) {
                                    Log.d("KeyguardKnoxGuardView", "not support call");
                                    keyguardKnoxGuardViewController2.showToast(keyguardKnoxGuardViewController2.getContext().getString(R.string.kg_knox_guard_call_not_support_toast));
                                    break;
                                } else {
                                    Intent intent2 = new Intent("android.intent.action.CALL_PRIVILEGED", Uri.fromParts("tel", str3, null));
                                    intent2.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                    try {
                                        Log.d("KeyguardKnoxGuardView", "click call button");
                                        keyguardKnoxGuardViewController2.getContext().startActivityAsUser(intent2, UserHandle.CURRENT);
                                        if (keyguardSecurityCallback3 != null) {
                                            keyguardSecurityCallback3.userActivity();
                                            break;
                                        }
                                    } catch (ActivityNotFoundException e3) {
                                        Log.w("KeyguardKnoxGuardView", "Can't find the component " + e3);
                                        return;
                                    }
                                }
                                break;
                        }
                    }
                });
            }
        }
        this.mSkipPin = this.mRemoteLockInfo.skipPinContainer;
        updateNetworkSettingsButton();
        boolean z2 = this.mSkipPin;
        SystemUITextView systemUITextView6 = this.mPinMessageTextView;
        SystemUIEditText systemUIEditText = this.mPinEditText;
        if (z2) {
            systemUIEditText.setVisibility(8);
            systemUITextView6.setVisibility(8);
        } else {
            systemUIEditText.setVisibility(0);
            systemUITextView6.setVisibility(0);
            resetPinErrorMessage();
        }
        Configuration configuration = getResources().getConfiguration();
        if (configuration.fontScale > 1.0f) {
            configuration.fontScale = 1.0f;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).getDisplay(0).getMetrics(displayMetrics);
        displayMetrics.scaledDensity = configuration.fontScale * displayMetrics.density;
        getResources().updateConfiguration(configuration, displayMetrics);
    }

    public final void setLong(int i, String str, long j) {
        try {
            getLockSettings().setLong(str, j, i);
        } catch (RemoteException e) {
            Log.e("KeyguardKnoxGuardView", "Couldn't write long " + str + e);
        }
    }

    public final void showToast(String str) {
        AnonymousClass5 anonymousClass5 = this.mHandler;
        if (anonymousClass5.hasMessages(4)) {
            return;
        }
        Log.d("KeyguardKnoxGuardView", "showToast : " + str);
        Toast.makeText(getContext(), str, 0).show();
        anonymousClass5.sendMessageDelayed(anonymousClass5.obtainMessage(4), DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void updateLayout() {
        super.updateLayout();
        updateTopContainer();
    }

    public final void updateNetworkSettingsButton() {
        WifiManager wifiManager = this.mWifiManager;
        int i = 0;
        boolean z = wifiManager != null && wifiManager.isWifiEnabled();
        DataUsageController dataUsageController = this.mDataController;
        boolean z2 = dataUsageController != null && dataUsageController.isMobileDataEnabled();
        boolean isWiFiOnlyDevice = DeviceType.isWiFiOnlyDevice();
        boolean isAllSimState = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.isAllSimState();
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("updateNetworkSettingsButton wifi : ", ",  mobileData : ", ",  wifiOnly : ", z, z2), isWiFiOnlyDevice, ",  noSimState : ", isAllSimState, "KeyguardKnoxGuardView");
        SystemUIImageView systemUIImageView = this.mWifiButton;
        if (systemUIImageView != null) {
            systemUIImageView.setVisibility(z ? 8 : 0);
        }
        SystemUIImageView systemUIImageView2 = this.mDataButton;
        if (systemUIImageView2 != null) {
            if (isWiFiOnlyDevice || (z2 && !isAllSimState)) {
                i = 8;
            }
            systemUIImageView2.setVisibility(i);
        }
    }

    public final void updateTopContainer() {
        SystemUITextView systemUITextView;
        LinearLayout linearLayout;
        int displayHeight = DeviceState.getDisplayHeight(getContext());
        Resources resources = getResources();
        float f = displayHeight;
        int i = (int) (resources.getFloat(R.dimen.kg_knox_guard_top_margin_ratio) * f);
        if (DeviceType.isTablet() && (linearLayout = this.mTopContainer) != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams.width = (resources.getDimensionPixelSize(R.dimen.kg_message_area_width_tablet) - resources.getDimensionPixelSize(R.dimen.kg_knox_guard_view_margin_start)) - resources.getDimensionPixelSize(R.dimen.kg_knox_guard_view_margin_end);
            this.mTopContainer.setLayoutParams(layoutParams);
        }
        LinearLayout linearLayout2 = this.mMessageContainer;
        if (linearLayout2 == null || (systemUITextView = this.mLockMessageTextView) == null) {
            return;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) linearLayout2.getLayoutParams();
        marginLayoutParams.setMargins(marginLayoutParams.leftMargin, i, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
        this.mMessageContainer.setLayoutParams(marginLayoutParams);
        this.mMessageContainer.setMinimumHeight((int) (resources.getFloat(R.dimen.kg_knox_guard_message_area_min_height_ratio) * f));
        systemUITextView.setMaxHeight((int) (resources.getFloat((this.mPhoneContainer.getVisibility() == 0 && this.mCustomerAppContainer.getVisibility() == 0) ? R.dimen.kg_knox_guard_message_area_max_height_with_3_buttons_ratio : (this.mPhoneContainer.getVisibility() == 0 || this.mCustomerAppContainer.getVisibility() == 0) ? R.dimen.kg_knox_guard_message_area_max_height_with_2_buttons_ratio : R.dimen.kg_knox_guard_message_area_max_height_ratio) * f));
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void verifyPasswordAndUnlock() {
        Log.d("KeyguardKnoxGuardView", "verifyPasswordAndUnlock()");
        byte[] charSequenceToByteArray = KeyguardSecAbsKeyInputViewController.charSequenceToByteArray(this.mPinEditText.getText());
        ((KeyguardKnoxGuardView) this.mView).setPasswordEntryInputEnabled(false);
        resetPinErrorMessage();
        if (charSequenceToByteArray.length > 3) {
            try {
                getLockSettings().checkRemoteLockPassword(3, charSequenceToByteArray, this.mSelectedUserInteractor.getSelectedUserId(false), this.mCheckPasswordCallback);
                ((KeyguardKnoxGuardView) this.mView).resetPasswordText(true, true);
                return;
            } catch (RemoteException unused) {
                Log.d("KeyguardKnoxGuardView", "Can't connect KNOX_GUARD");
                return;
            }
        }
        if (charSequenceToByteArray.length != 0) {
            Resources resources = getResources();
            ((KeyguardKnoxGuardView) this.mView).getClass();
            String string = resources.getString(R.string.kg_remote_lock_incorrect_pin);
            SystemUITextView systemUITextView = this.mPinMessageTextView;
            systemUITextView.setText(string);
            systemUITextView.setVisibility(0);
        }
        ((KeyguardKnoxGuardView) this.mView).resetPasswordText(true, true);
        ((KeyguardKnoxGuardView) this.mView).setPasswordEntryInputEnabled(true);
    }
}
