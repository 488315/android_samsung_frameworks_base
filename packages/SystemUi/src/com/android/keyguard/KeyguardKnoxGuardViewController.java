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
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
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
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.widget.SystemUIEditText;
import com.android.systemui.widget.SystemUIImageView;
import com.android.systemui.widget.SystemUITextView;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.function.IntConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardKnoxGuardViewController extends KeyguardSecAbsKeyInputViewController {
    public static int numberOfAttemptsDone;
    public final IRemoteCallbackStubC06921 mCheckPasswordCallback;
    public final SystemUITextView mCompanyNameTextView;
    public CountDownTimer mCountdownTimer;
    public final RelativeLayout mCustomerAppContainer;
    public final SystemUITextView mCustomerAppHeaderTextView;
    public final SystemUIImageView mCustomerAppImageView;
    public final SystemUIImageView mDataButton;
    public final DataUsageController mDataController;
    public final HandlerC06954 mHandler;
    public final InputMethodManager mImm;
    public final IntentFilter mIntentFilter;
    public final SystemUITextView mLockMessageTextView;
    public ILockSettings mLockSettingsService;
    public final LinearLayout mMessageContainer;
    public final C06965 mMobileDataObserver;
    public final RelativeLayout mOptionContainer;
    public final SystemUITextView mOptionHeaderTextView;
    public final RelativeLayout mPhoneContainer;
    public final SystemUITextView mPhoneHeaderTextView;
    public final SystemUITextView mPhoneSubTextTextView;
    public final SystemUIEditText mPinEditText;
    public final SystemUITextView mPinMessageTextView;
    public final C06976 mReceiver;
    public RemoteLockInfo mRemoteLockInfo;
    public final KeyguardKnoxGuardViewController$$ExternalSyntheticLambda1 mRotationConsumer;
    public final SecRotationWatcher mRotationWatcher;
    public final ScreenLifecycle mScreenLifecycle;
    public final C06943 mScreenObserver;
    public boolean mSkipPin;
    public final TelephonyManager mTelephonyManager;
    public final LinearLayout mTopContainer;
    public final C06932 mUpdateCallback;
    public final SystemUIImageView mWifiButton;
    public final WifiManager mWifiManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardKnoxGuardViewController$2 */
    public final class C06932 extends KeyguardUpdateMonitorCallback {
        public C06932() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onRemoteLockInfoChanged() {
            Log.d("KeyguardKnoxGuardView", "onRemoteLockInfoChanged");
            post(new Runnable() { // from class: com.android.keyguard.KeyguardKnoxGuardViewController$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardKnoxGuardViewController.this.setKnoxGuardInfo();
                }
            });
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onSimStateChanged(int i, int i2, int i3) {
            RecyclerView$$ExternalSyntheticOutline0.m46m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("onSimStateChanged subID : ", i, ", slotId : ", i2, ", simState : "), i3, "KeyguardKnoxGuardView");
            if (i3 == 1 || i3 == 5) {
                KeyguardKnoxGuardViewController.this.updateNetworkSettingsButton();
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r1v14, types: [com.android.keyguard.KeyguardKnoxGuardViewController$5] */
    /* JADX WARN: Type inference failed for: r1v16, types: [com.android.keyguard.KeyguardKnoxGuardViewController$6] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.keyguard.KeyguardKnoxGuardViewController$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.keyguard.KeyguardKnoxGuardViewController$3] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.keyguard.KeyguardKnoxGuardViewController$4] */
    public KeyguardKnoxGuardViewController(KeyguardKnoxGuardView keyguardKnoxGuardView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController, FeatureFlags featureFlags, SecRotationWatcher secRotationWatcher, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, InputMethodManager inputMethodManager, TelephonyManager telephonyManager, WifiManager wifiManager, NetworkController networkController, ScreenLifecycle screenLifecycle) {
        super(keyguardKnoxGuardView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, falsingCollector, emergencyButtonController, featureFlags, secRotationWatcher, vibrationUtil, accessibilityManager);
        this.mRemoteLockInfo = null;
        this.mLockSettingsService = null;
        this.mSkipPin = false;
        this.mCountdownTimer = null;
        this.mRotationConsumer = new IntConsumer() { // from class: com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticLambda1
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                KeyguardKnoxGuardViewController.this.updateTopContainer();
            }
        };
        this.mCheckPasswordCallback = new IRemoteCallback.Stub() { // from class: com.android.keyguard.KeyguardKnoxGuardViewController.1
            public final void sendResult(Bundle bundle) {
                int i = bundle.getInt("result");
                long j = bundle.getLong("timeout");
                Log.d("KeyguardKnoxGuardView", "Unlock attempt result : " + i + " timeout : " + j);
                removeMessages(2);
                sendMessage(obtainMessage(2, i, 0, Long.valueOf(j)));
            }
        };
        this.mUpdateCallback = new C06932();
        this.mScreenObserver = new ScreenLifecycle.Observer() { // from class: com.android.keyguard.KeyguardKnoxGuardViewController.3
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurnedOff() {
                KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = KeyguardKnoxGuardViewController.this;
                keyguardKnoxGuardViewController.mHandler.removeMessages(3);
                HandlerC06954 handlerC06954 = keyguardKnoxGuardViewController.mHandler;
                handlerC06954.sendMessage(handlerC06954.obtainMessage(3));
            }
        };
        this.mHandler = new Handler(Looper.myLooper()) { // from class: com.android.keyguard.KeyguardKnoxGuardViewController.4
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                long currentTimeMillis;
                int i = message.what;
                if (i != 2) {
                    if (i != 3) {
                        return;
                    }
                    KeyguardKnoxGuardViewController.this.resetPinErrorMessage();
                    return;
                }
                final KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = KeyguardKnoxGuardViewController.this;
                int i2 = message.arg1;
                long longValue = ((Long) message.obj).longValue();
                keyguardKnoxGuardViewController.getClass();
                Log.d("KeyguardKnoxGuardView", "checkUnlockAttempts " + i2);
                ((KeyguardKnoxGuardView) keyguardKnoxGuardViewController.mView).setPasswordEntryInputEnabled(true);
                if (i2 == 0) {
                    try {
                        keyguardKnoxGuardViewController.getLockSettings().setRemoteLock(KeyguardUpdateMonitor.getCurrentUser(), new RemoteLockInfo.Builder(3, false).build());
                    } catch (RemoteException e) {
                        Log.d("KeyguardKnoxGuardView", "Failed KNOXGUARD LOCK clear!!" + e);
                    }
                    KeyguardUnlockInfo.setUnlockTriggerByRemoteLock(3);
                    keyguardKnoxGuardViewController.getKeyguardSecurityCallback().dismiss(KeyguardUpdateMonitor.getCurrentUser(), keyguardKnoxGuardViewController.mSecurityMode, true);
                    return;
                }
                RemoteLockInfo remoteLockInfo = keyguardKnoxGuardViewController.mRemoteLockInfo;
                if (remoteLockInfo != null) {
                    if (longValue > 0 && remoteLockInfo.lockTimeOut != longValue) {
                        Log.d("KeyguardKnoxGuardView", "update lockTimeout " + keyguardKnoxGuardViewController.mRemoteLockInfo.lockTimeOut + " => " + longValue);
                        keyguardKnoxGuardViewController.mRemoteLockInfo.lockTimeOut = longValue;
                    }
                    RemoteLockInfo remoteLockInfo2 = keyguardKnoxGuardViewController.mRemoteLockInfo;
                    int i3 = remoteLockInfo2.allowFailCount;
                    if (i3 > 0 && remoteLockInfo2.lockTimeOut > 0 && i2 > 0 && i2 % i3 == 0) {
                        ((KeyguardKnoxGuardView) keyguardKnoxGuardViewController.mView).setPasswordEntryInputEnabled(false);
                        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                        if (keyguardKnoxGuardViewController.mRemoteLockInfo == null) {
                            currentTimeMillis = -1;
                        } else {
                            currentTimeMillis = System.currentTimeMillis() + keyguardKnoxGuardViewController.mRemoteLockInfo.lockTimeOut;
                            keyguardKnoxGuardViewController.setLong(currentTimeMillis, ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder(), keyguardKnoxGuardViewController.mRemoteLockInfo.lockType, "remotelockscreen.lockoutdeadline"), currentUser);
                        }
                        int currentUser2 = KeyguardUpdateMonitor.getCurrentUser();
                        if (keyguardKnoxGuardViewController.mRemoteLockInfo == null) {
                            i2 = -1;
                        } else {
                            keyguardKnoxGuardViewController.setLong(i2, ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder(), keyguardKnoxGuardViewController.mRemoteLockInfo.lockType, "remotelockscreen.failedunlockcount"), currentUser2);
                        }
                        KeyguardKnoxGuardViewController.numberOfAttemptsDone = i2;
                        keyguardKnoxGuardViewController.handleAttemptLockout(currentTimeMillis);
                        return;
                    }
                }
                Resources resources = keyguardKnoxGuardViewController.getResources();
                ((KeyguardKnoxGuardView) keyguardKnoxGuardViewController.mView).getClass();
                String string = resources.getString(R.string.kg_remote_lock_incorrect_pin);
                SystemUITextView systemUITextView = keyguardKnoxGuardViewController.mPinMessageTextView;
                systemUITextView.setText(string);
                systemUITextView.setVisibility(0);
                new Handler().postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardKnoxGuardViewController.this.resetPinErrorMessage();
                    }
                }, 3000L);
            }
        };
        this.mRotationWatcher = secRotationWatcher;
        this.mImm = inputMethodManager;
        this.mWifiManager = wifiManager;
        this.mTelephonyManager = telephonyManager;
        this.mDataController = ((NetworkControllerImpl) networkController).mDataUsageController;
        this.mScreenLifecycle = screenLifecycle;
        this.mMobileDataObserver = new ContentObserver(new Handler()) { // from class: com.android.keyguard.KeyguardKnoxGuardViewController.5
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                DataUsageController dataUsageController = KeyguardKnoxGuardViewController.this.mDataController;
                boolean z2 = dataUsageController != null && dataUsageController.isMobileDataEnabled();
                if (KeyguardKnoxGuardViewController.this.mDataButton != null) {
                    StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("mobileData settings changed mobileDataEnabled ", z2, " visibility :");
                    m49m.append(KeyguardKnoxGuardViewController.this.mDataButton.getVisibility());
                    Log.d("KeyguardKnoxGuardView", m49m.toString());
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
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.keyguard.KeyguardKnoxGuardViewController.6
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction())) {
                    boolean z = intent.getIntExtra("wifi_state", 4) == 3;
                    if (KeyguardKnoxGuardViewController.this.mWifiButton != null) {
                        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("WIFI_STATE_CHANGED_ACTION received : enabled = ", z, " visibility :");
                        m49m.append(KeyguardKnoxGuardViewController.this.mWifiButton.getVisibility());
                        Log.d("KeyguardKnoxGuardView", m49m.toString());
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
        this.mCountdownTimer = new CountDownTimer(j - System.currentTimeMillis(), 1000L) { // from class: com.android.keyguard.KeyguardKnoxGuardViewController.8
            @Override // android.os.CountDownTimer
            public final void onFinish() {
                KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = KeyguardKnoxGuardViewController.this;
                keyguardKnoxGuardViewController.mCountdownTimer = null;
                keyguardKnoxGuardViewController.resetPinErrorMessage();
                ((KeyguardKnoxGuardView) KeyguardKnoxGuardViewController.this.mView).setPasswordEntryEnabled(true);
                KeyguardKnoxGuardViewController.this.resetState();
            }

            @Override // android.os.CountDownTimer
            public final void onTick(long j2) {
                int i = (int) (j2 / 1000);
                int i2 = i / 60;
                int i3 = i2 / 60;
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("onTick() secondsRemaining: ", i, "KeyguardKnoxGuardView");
                if (i > 3600) {
                    KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = KeyguardKnoxGuardViewController.this;
                    keyguardKnoxGuardViewController.mPinMessageTextView.setText(keyguardKnoxGuardViewController.getResources().getQuantityString(R.plurals.kg_knox_guard_incorrect_pin_remaining_hours_left_phone, i3, Integer.valueOf(KeyguardKnoxGuardViewController.numberOfAttemptsDone), Integer.valueOf(i3)));
                    KeyguardKnoxGuardViewController.this.mPinMessageTextView.setVisibility(0);
                } else if (i > 60) {
                    KeyguardKnoxGuardViewController keyguardKnoxGuardViewController2 = KeyguardKnoxGuardViewController.this;
                    keyguardKnoxGuardViewController2.mPinMessageTextView.setText(keyguardKnoxGuardViewController2.getResources().getQuantityString(R.plurals.kg_knox_guard_incorrect_pin_remaining_mins_left_phone, i2, Integer.valueOf(KeyguardKnoxGuardViewController.numberOfAttemptsDone), Integer.valueOf(i2)));
                    KeyguardKnoxGuardViewController.this.mPinMessageTextView.setVisibility(0);
                } else if (i > 0) {
                    KeyguardKnoxGuardViewController keyguardKnoxGuardViewController3 = KeyguardKnoxGuardViewController.this;
                    keyguardKnoxGuardViewController3.mPinMessageTextView.setText(keyguardKnoxGuardViewController3.getResources().getQuantityString(R.plurals.kg_knox_guard_incorrect_pin_remaining_seconds_left_phone, i, Integer.valueOf(KeyguardKnoxGuardViewController.numberOfAttemptsDone), Integer.valueOf(i)));
                    KeyguardKnoxGuardViewController.this.mPinMessageTextView.setVisibility(0);
                }
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
        Uri uriFor = Settings.Global.getUriFor("mobile_data");
        final int i = 0;
        C06965 c06965 = this.mMobileDataObserver;
        contentResolver.registerContentObserver(uriFor, false, c06965);
        contentResolver.registerContentObserver(Settings.Global.getUriFor("data_roaming"), false, c06965);
        context.registerReceiver(this.mReceiver, this.mIntentFilter, null, null);
        if (DeviceState.shouldEnableKeyguardScreenRotation(context)) {
            this.mRotationWatcher.addCallback(this.mRotationConsumer);
        }
        View.OnClickListener onClickListener = new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticLambda2
            public final /* synthetic */ KeyguardKnoxGuardViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = this.f$0;
                        keyguardKnoxGuardViewController.onUserInput();
                        keyguardKnoxGuardViewController.mImm.showSoftInput(keyguardKnoxGuardViewController.mPinEditText, 1);
                        break;
                    default:
                        KeyguardKnoxGuardViewController keyguardKnoxGuardViewController2 = this.f$0;
                        keyguardKnoxGuardViewController2.getClass();
                        Log.d("KeyguardKnoxGuardView", "mWifiButton OnClick");
                        keyguardKnoxGuardViewController2.mWifiManager.setWifiEnabled(true);
                        break;
                }
            }
        };
        SystemUIEditText systemUIEditText = this.mPinEditText;
        systemUIEditText.setOnClickListener(onClickListener);
        systemUIEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticLambda3
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
        systemUIEditText.addTextChangedListener(new TextWatcher() { // from class: com.android.keyguard.KeyguardKnoxGuardViewController.7
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
        this.mWifiButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticLambda2
            public final /* synthetic */ KeyguardKnoxGuardViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        KeyguardKnoxGuardViewController keyguardKnoxGuardViewController = this.f$0;
                        keyguardKnoxGuardViewController.onUserInput();
                        keyguardKnoxGuardViewController.mImm.showSoftInput(keyguardKnoxGuardViewController.mPinEditText, 1);
                        break;
                    default:
                        KeyguardKnoxGuardViewController keyguardKnoxGuardViewController2 = this.f$0;
                        keyguardKnoxGuardViewController2.getClass();
                        Log.d("KeyguardKnoxGuardView", "mWifiButton OnClick");
                        keyguardKnoxGuardViewController2.mWifiManager.setWifiEnabled(true);
                        break;
                }
            }
        });
        this.mDataButton.setOnClickListener(new KeyguardKnoxGuardViewController$$ExternalSyntheticLambda4(this, context, i));
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
            this.mRotationWatcher.removeCallback(this.mRotationConsumer);
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
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        long j3 = 0;
        if (this.mRemoteLockInfo == null) {
            j2 = -1;
        } else {
            try {
                j = getLockSettings().getLong(ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder(), this.mRemoteLockInfo.lockType, "remotelockscreen.lockoutdeadline"), 0L, currentUser);
            } catch (RemoteException unused) {
                j = 0;
            }
            long currentTimeMillis = System.currentTimeMillis();
            if (j > currentTimeMillis || j == 0) {
                if (j > currentTimeMillis + this.mRemoteLockInfo.lockTimeOut) {
                    j = System.currentTimeMillis() + this.mRemoteLockInfo.lockTimeOut;
                    setLong(j, ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder(), this.mRemoteLockInfo.lockType, "remotelockscreen.lockoutdeadline"), currentUser);
                }
                j2 = j;
            } else {
                setLong(0L, ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder(), this.mRemoteLockInfo.lockType, "remotelockscreen.lockoutdeadline"), currentUser);
                j2 = 0;
            }
        }
        if (j2 > 0) {
            int currentUser2 = KeyguardUpdateMonitor.getCurrentUser();
            if (this.mRemoteLockInfo == null) {
                i = -1;
            } else {
                try {
                    j3 = getLockSettings().getLong(ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder(), this.mRemoteLockInfo.lockType, "remotelockscreen.failedunlockcount"), 0L, currentUser2);
                } catch (RemoteException unused2) {
                }
                i = (int) j3;
            }
            numberOfAttemptsDone = i;
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
        final int i = 1;
        if (remoteLockInfo == null) {
            Log.d("KeyguardKnoxGuardView", "mRemoteLockInfo is null - dismiss");
            CountDownTimer countDownTimer = this.mCountdownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
                this.mCountdownTimer = null;
                resetPinErrorMessage();
                ((KeyguardKnoxGuardView) this.mView).setPasswordEntryEnabled(true);
            }
            keyguardSecurityCallback.dismiss(KeyguardUpdateMonitor.getCurrentUser(), this.mSecurityMode, true);
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
        boolean z = this.mRemoteLockInfo.skipSupportContainer;
        RelativeLayout relativeLayout = this.mOptionContainer;
        final int i2 = 0;
        if (z) {
            relativeLayout.setVisibility(8);
            Log.d("KeyguardKnoxGuardView", "mRemoteLockInfo.skipSupportContainer is true");
        } else {
            relativeLayout.setVisibility(0);
            this.mOptionHeaderTextView.setText(getResources().getString(R.string.kg_remote_lock_option_header));
            relativeLayout.setOnClickListener(new KeyguardKnoxGuardViewController$$ExternalSyntheticLambda4(this, keyguardSecurityCallback, i));
        }
        Bundle bundle = this.mRemoteLockInfo.bundle;
        RelativeLayout relativeLayout2 = this.mCustomerAppContainer;
        if (bundle == null || bundle.getCharSequence("customer_package_name") == null || (systemUITextView3 = this.mCustomerAppHeaderTextView) == null) {
            if (relativeLayout2 != null) {
                relativeLayout2.setVisibility(8);
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
            boolean z2 = !TextUtils.isEmpty(charSequence5);
            SystemUIImageView systemUIImageView = this.mCustomerAppImageView;
            if (applicationInfo != null) {
                if (!z2) {
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
                if (!z2) {
                    charSequence5 = charSequence4;
                }
                systemUITextView3.setText(charSequence5);
                systemUIImageView.setImageResource(R.drawable.kg_knox_guard_ic_default_app);
            }
            Log.d("KeyguardKnoxGuardView", "customerPackageName : " + charSequence4 + ",  isAppNameExist : " + z2);
            if (relativeLayout2 != null && !charSequence4.isEmpty()) {
                relativeLayout2.setVisibility(0);
                relativeLayout2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticLambda5
                    public final /* synthetic */ KeyguardKnoxGuardViewController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
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
        RelativeLayout relativeLayout3 = this.mPhoneContainer;
        if (charSequence6 == null || (systemUITextView = this.mPhoneSubTextTextView) == null || (systemUITextView2 = this.mPhoneHeaderTextView) == null) {
            relativeLayout3.setVisibility(8);
            Log.d("KeyguardKnoxGuardView", "mRemoteLockInfo.phoneNumber is null");
        } else {
            final String trim = charSequence6.toString().trim();
            if (trim.isEmpty()) {
                relativeLayout3.setVisibility(8);
            } else {
                relativeLayout3.setVisibility(0);
                systemUITextView2.setText(getResources().getString(R.string.kg_remote_lock_accessibility_call, str));
                systemUITextView.setText(trim);
                relativeLayout3.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticLambda5
                    public final /* synthetic */ KeyguardKnoxGuardViewController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
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
        boolean z3 = this.mSkipPin;
        SystemUITextView systemUITextView6 = this.mPinMessageTextView;
        SystemUIEditText systemUIEditText = this.mPinEditText;
        if (z3) {
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
        ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).getDisplay(0).getMetrics(displayMetrics);
        displayMetrics.scaledDensity = configuration.fontScale * displayMetrics.density;
        getResources().updateConfiguration(configuration, displayMetrics);
    }

    public final void setLong(long j, String str, int i) {
        try {
            getLockSettings().setLong(str, j, i);
        } catch (RemoteException e) {
            Log.e("KeyguardKnoxGuardView", "Couldn't write long " + str + e);
        }
    }

    public final void showToast(String str) {
        HandlerC06954 handlerC06954 = this.mHandler;
        if (handlerC06954.hasMessages(4)) {
            return;
        }
        Log.d("KeyguardKnoxGuardView", "showToast : " + str);
        Toast.makeText(getContext(), str, 0).show();
        handlerC06954.sendMessageDelayed(handlerC06954.obtainMessage(4), 2000L);
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void updateLayout() {
        super.updateLayout();
        updateTopContainer();
    }

    public final void updateNetworkSettingsButton() {
        int i = 0;
        WifiManager wifiManager = this.mWifiManager;
        boolean z = wifiManager != null && wifiManager.isWifiEnabled();
        DataUsageController dataUsageController = this.mDataController;
        boolean z2 = dataUsageController != null && dataUsageController.isMobileDataEnabled();
        boolean isWiFiOnlyDevice = DeviceType.isWiFiOnlyDevice();
        boolean isAllSimState = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.isAllSimState();
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("updateNetworkSettingsButton wifi : ", z, ",  mobileData : ", z2, ",  wifiOnly : "), isWiFiOnlyDevice, ",  noSimState : ", isAllSimState, "KeyguardKnoxGuardView");
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
            linearLayout.setLayoutParams(layoutParams);
        }
        LinearLayout linearLayout2 = this.mMessageContainer;
        if (linearLayout2 == null || (systemUITextView = this.mLockMessageTextView) == null) {
            return;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) linearLayout2.getLayoutParams();
        marginLayoutParams.setMargins(marginLayoutParams.leftMargin, i, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
        linearLayout2.setLayoutParams(marginLayoutParams);
        linearLayout2.setMinimumHeight((int) (resources.getFloat(R.dimen.kg_knox_guard_message_area_min_height_ratio) * f));
        RelativeLayout relativeLayout = this.mPhoneContainer;
        int visibility = relativeLayout.getVisibility();
        RelativeLayout relativeLayout2 = this.mCustomerAppContainer;
        systemUITextView.setMaxHeight((int) (resources.getFloat((visibility == 0 && relativeLayout2.getVisibility() == 0) ? R.dimen.kg_knox_guard_message_area_max_height_with_3_buttons_ratio : (relativeLayout.getVisibility() == 0 || relativeLayout2.getVisibility() == 0) ? R.dimen.kg_knox_guard_message_area_max_height_with_2_buttons_ratio : R.dimen.kg_knox_guard_message_area_max_height_ratio) * f));
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void verifyPasswordAndUnlock() {
        Log.d("KeyguardKnoxGuardView", "verifyPasswordAndUnlock()");
        byte[] charSequenceToByteArray = KeyguardSecAbsKeyInputViewController.charSequenceToByteArray(this.mPinEditText.getText());
        ((KeyguardKnoxGuardView) this.mView).setPasswordEntryInputEnabled(false);
        resetPinErrorMessage();
        if (charSequenceToByteArray.length > 3) {
            try {
                getLockSettings().checkRemoteLockPassword(3, charSequenceToByteArray, KeyguardUpdateMonitor.getCurrentUser(), this.mCheckPasswordCallback);
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
