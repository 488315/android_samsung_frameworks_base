package com.android.keyguard;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.FileUtils;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.sec.ims.settings.ImsProfile;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardCarrierViewController extends KeyguardInputViewController {
    public C06713 mBroadcastReceiver;
    public final Context mContext;
    public final C06724 mEmergencyButtonCallback;
    public final EmergencyButtonController mEmergencyButtonController;
    public boolean mIsShowingOwnerCallButton;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final LockPatternUtils mLockPatternUtils;
    public final Button mOwnerCallButton;
    public final TextView mOwnerInfo;
    public String mOwnerMessage;
    public int mPhoneState;
    public final C06691 mPhoneStateListener;
    public final TelephonyManager mTelephonyManager;
    public final Button mUnlockButton;

    /* JADX WARN: Type inference failed for: r8v2, types: [com.android.keyguard.KeyguardCarrierViewController$1] */
    /* JADX WARN: Type inference failed for: r8v4, types: [com.android.keyguard.KeyguardCarrierViewController$4] */
    public KeyguardCarrierViewController(KeyguardCarrierView keyguardCarrierView, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, EmergencyButtonController emergencyButtonController, TelephonyManager telephonyManager, KeyguardMessageAreaController.Factory factory, FeatureFlags featureFlags) {
        super(keyguardCarrierView, securityMode, keyguardSecurityCallback, emergencyButtonController, factory, featureFlags);
        this.mPhoneState = 0;
        this.mIsShowingOwnerCallButton = false;
        this.mPhoneStateListener = new PhoneStateListener() { // from class: com.android.keyguard.KeyguardCarrierViewController.1
            @Override // android.telephony.PhoneStateListener
            public final void onServiceStateChanged(ServiceState serviceState) {
                KeyguardCarrierViewController.this.setVisibleOwnerCallButton(false);
            }
        };
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardCarrierViewController.2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onPhoneStateChanged(int i) {
                Button button;
                KeyguardCarrierViewController keyguardCarrierViewController = KeyguardCarrierViewController.this;
                keyguardCarrierViewController.mPhoneState = i;
                Button button2 = keyguardCarrierViewController.mOwnerCallButton;
                if (button2 == null || (button = keyguardCarrierViewController.mUnlockButton) == null) {
                    return;
                }
                if (i == 2) {
                    button2.setVisibility(8);
                    button.setVisibility(8);
                } else {
                    keyguardCarrierViewController.setVisibleOwnerCallButton(false);
                    button.setVisibility(0);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimStateChanged(int i, int i2, int i3) {
                boolean z;
                KeyguardCarrierViewController keyguardCarrierViewController = KeyguardCarrierViewController.this;
                if (i3 != 1) {
                    if (i3 != 5) {
                        return;
                    }
                    keyguardCarrierViewController.setVisibleOwnerCallButton(false);
                    return;
                }
                keyguardCarrierViewController.getClass();
                int phoneCount = TelephonyManager.getDefault().getPhoneCount();
                String str = SystemProperties.get("gsm.sim.state", "");
                if (str != null) {
                    String[] split = str.split(",");
                    Log.d("KeyguardCarrierView", "isSimStateAbsentAll() : simSlotCount = " + phoneCount + ", simStates = " + str);
                    z = true;
                    for (int i4 = 0; i4 < phoneCount && split.length > i4; i4++) {
                        if (!split[i4].equalsIgnoreCase("ABSENT")) {
                            z = false;
                        }
                    }
                } else {
                    z = true;
                }
                if (z) {
                    keyguardCarrierViewController.setVisibleOwnerCallButton(true);
                } else {
                    keyguardCarrierViewController.setVisibleOwnerCallButton(false);
                }
            }
        };
        this.mEmergencyButtonCallback = new EmergencyButtonController.EmergencyButtonCallback() { // from class: com.android.keyguard.KeyguardCarrierViewController.4
            @Override // com.android.keyguard.EmergencyButtonController.EmergencyButtonCallback
            public final void onEmergencyButtonClickedWhenInCall() {
                KeyguardCarrierViewController.this.getKeyguardSecurityCallback().reset();
            }
        };
        this.mContext = getContext();
        this.mLockPatternUtils = lockPatternUtils;
        this.mTelephonyManager = telephonyManager;
        this.mEmergencyButtonController = emergencyButtonController;
        this.mOwnerInfo = (TextView) ((KeyguardCarrierView) this.mView).findViewById(R.id.carrier_owner_info);
        this.mOwnerCallButton = (Button) ((KeyguardCarrierView) this.mView).findViewById(R.id.carrier_owner_call_button);
        this.mUnlockButton = (Button) ((KeyguardCarrierView) this.mView).findViewById(R.id.carrier_unlock_button);
    }

    public static String decryptCarrierLockPlusMsg(String str) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, getKey(), new IvParameterSpec("i_love_office_tg".getBytes("UTF-8")));
            return new String(cipher.doFinal(Base64.decode(str, 0)), StandardCharsets.UTF_8);
        } catch (InvalidAlgorithmParameterException e) {
            Log.i("KeyguardCarrierView", "sec_encrypt.decrypt() InvalidAlgorithmParameterException = " + e.toString());
            return null;
        } catch (InvalidKeyException e2) {
            Log.i("KeyguardCarrierView", "sec_encrypt.decrypt() InvalidKeyException = " + e2.toString());
            return null;
        } catch (NoSuchAlgorithmException e3) {
            Log.i("KeyguardCarrierView", "sec_encrypt.decrypt() NoSuchAlgorithmException = " + e3.toString());
            return null;
        } catch (NoSuchPaddingException e4) {
            Log.i("KeyguardCarrierView", "sec_encrypt.decrypt() NoSuchPaddingException = " + e4.toString());
            return null;
        } catch (Exception e5) {
            Log.i("KeyguardCarrierView", "sec_encrypt.decrypt() Exception = " + e5.toString());
            return null;
        }
    }

    public static SecretKeySpec getKey() {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update("SKT : Find lost phone plus !!!".getBytes("UTF-8"));
        return new SecretKeySpec(messageDigest.digest(), "AES");
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final int getInitialMessageResId() {
        return 0;
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public final boolean needsInput() {
        return false;
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final void onPause() {
        this.mPaused = true;
        if (((KeyguardCarrierView) this.mView).getKeepScreenOn()) {
            ((KeyguardCarrierView) this.mView).setKeepScreenOn(false);
        }
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.keyguard.KeyguardCarrierViewController$3] */
    @Override // com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        String str;
        super.onViewAttached();
        this.mEmergencyButtonController.mEmergencyButtonCallback = this.mEmergencyButtonCallback;
        final KeyguardSecurityCallback keyguardSecurityCallback = getKeyguardSecurityCallback();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sec.android.CarrierLock.DISABLED");
        intentFilter.addAction("com.sec.android.FindingLostPhonePlus.SUBSCRIBE");
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.keyguard.KeyguardCarrierViewController.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d("KeyguardCarrierView", "onReceive: " + action);
                Objects.requireNonNull(action);
                switch (action) {
                    case "com.sec.android.CarrierLock.DISABLED":
                        KeyguardUnlockInfo.setUnlockTriggerByRemoteLock(1);
                        keyguardSecurityCallback.dismiss(KeyguardUpdateMonitor.getCurrentUser(), KeyguardCarrierViewController.this.mSecurityMode, true);
                        break;
                    case "com.sec.android.FindingLostPhonePlus.SUBSCRIBE":
                        KeyguardCarrierViewController.this.setCarrierLockPlusInfo();
                        break;
                    case "android.intent.action.SIM_STATE_CHANGED":
                        if ("LOADED".equals(intent.getStringExtra(ImsProfile.SERVICE_SS))) {
                            KeyguardCarrierViewController keyguardCarrierViewController = KeyguardCarrierViewController.this;
                            keyguardCarrierViewController.mTelephonyManager.listen(keyguardCarrierViewController.mPhoneStateListener, 0);
                            KeyguardCarrierViewController keyguardCarrierViewController2 = KeyguardCarrierViewController.this;
                            keyguardCarrierViewController2.mTelephonyManager.listen(keyguardCarrierViewController2.mPhoneStateListener, 1);
                            break;
                        }
                        break;
                }
            }
        };
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(intentFilter, this.mBroadcastReceiver);
        ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).registerCallback(this.mKeyguardUpdateMonitorCallback);
        final int i = 1;
        this.mTelephonyManager.listen(this.mPhoneStateListener, 1);
        setCarrierLockPlusInfo();
        TextView textView = this.mOwnerInfo;
        if (textView != null && (str = this.mOwnerMessage) != null) {
            textView.setText(str);
        }
        Button button = this.mOwnerCallButton;
        if (button != null) {
            final int i2 = 0;
            button.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticLambda1
                public final /* synthetic */ KeyguardCarrierViewController f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    switch (i2) {
                        case 0:
                            KeyguardCarrierViewController keyguardCarrierViewController = this.f$0;
                            KeyguardSecurityCallback keyguardSecurityCallback2 = keyguardSecurityCallback;
                            keyguardCarrierViewController.getClass();
                            Intent intent = new Intent("android.intent.action.CALL_PRIVILEGED", Uri.fromParts("tel", "0000", null));
                            intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                            try {
                                Log.d("KeyguardCarrierView", "click call button");
                                keyguardCarrierViewController.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
                                keyguardSecurityCallback2.userActivity();
                                break;
                            } catch (ActivityNotFoundException e) {
                                Log.w("KeyguardCarrierView", "Can't find the component " + e);
                            }
                        default:
                            KeyguardCarrierViewController keyguardCarrierViewController2 = this.f$0;
                            KeyguardSecurityCallback keyguardSecurityCallback3 = keyguardSecurityCallback;
                            if (keyguardCarrierViewController2.mLockPatternUtils.isCarrierPasswordSaved(KeyguardUpdateMonitor.getCurrentUser())) {
                                keyguardSecurityCallback3.showBackupSecurity(KeyguardSecurityModel.SecurityMode.SKTCarrierPassword);
                                break;
                            }
                            break;
                    }
                }
            });
            setVisibleOwnerCallButton(false);
        }
        Button button2 = this.mUnlockButton;
        if (button2 != null) {
            button2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticLambda1
                public final /* synthetic */ KeyguardCarrierViewController f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    switch (i) {
                        case 0:
                            KeyguardCarrierViewController keyguardCarrierViewController = this.f$0;
                            KeyguardSecurityCallback keyguardSecurityCallback2 = keyguardSecurityCallback;
                            keyguardCarrierViewController.getClass();
                            Intent intent = new Intent("android.intent.action.CALL_PRIVILEGED", Uri.fromParts("tel", "0000", null));
                            intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                            try {
                                Log.d("KeyguardCarrierView", "click call button");
                                keyguardCarrierViewController.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
                                keyguardSecurityCallback2.userActivity();
                                break;
                            } catch (ActivityNotFoundException e) {
                                Log.w("KeyguardCarrierView", "Can't find the component " + e);
                            }
                        default:
                            KeyguardCarrierViewController keyguardCarrierViewController2 = this.f$0;
                            KeyguardSecurityCallback keyguardSecurityCallback3 = keyguardSecurityCallback;
                            if (keyguardCarrierViewController2.mLockPatternUtils.isCarrierPasswordSaved(KeyguardUpdateMonitor.getCurrentUser())) {
                                keyguardSecurityCallback3.showBackupSecurity(KeyguardSecurityModel.SecurityMode.SKTCarrierPassword);
                                break;
                            }
                            break;
                    }
                }
            });
        }
    }

    @Override // com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        this.mEmergencyButtonController.mEmergencyButtonCallback = null;
        this.mTelephonyManager.listen(this.mPhoneStateListener, 0);
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).unregisterReceiver(this.mBroadcastReceiver);
        ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).removeCallback(this.mKeyguardUpdateMonitorCallback);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x007b A[Catch: IOException -> 0x00c1, TryCatch #0 {IOException -> 0x00c1, blocks: (B:14:0x0070, B:16:0x007b, B:17:0x0094, B:19:0x009a, B:21:0x00b0, B:22:0x00b5, B:25:0x00ba, B:27:0x0089), top: B:13:0x0070 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x009a A[Catch: IOException -> 0x00c1, TryCatch #0 {IOException -> 0x00c1, blocks: (B:14:0x0070, B:16:0x007b, B:17:0x0094, B:19:0x009a, B:21:0x00b0, B:22:0x00b5, B:25:0x00ba, B:27:0x0089), top: B:13:0x0070 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00ba A[Catch: IOException -> 0x00c1, TRY_LEAVE, TryCatch #0 {IOException -> 0x00c1, blocks: (B:14:0x0070, B:16:0x007b, B:17:0x0094, B:19:0x009a, B:21:0x00b0, B:22:0x00b5, B:25:0x00ba, B:27:0x0089), top: B:13:0x0070 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0089 A[Catch: IOException -> 0x00c1, TryCatch #0 {IOException -> 0x00c1, blocks: (B:14:0x0070, B:16:0x007b, B:17:0x0094, B:19:0x009a, B:21:0x00b0, B:22:0x00b5, B:25:0x00ba, B:27:0x0089), top: B:13:0x0070 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setCarrierLockPlusInfo() {
        byte[] bArr;
        String decryptCarrierLockPlusMsg;
        String decryptCarrierLockPlusMsg2;
        try {
            decryptCarrierLockPlusMsg2 = new File("/efs/sec_efs/sktdm_mem/enclawlockmsg.txt").exists() ? decryptCarrierLockPlusMsg(FileUtils.readTextFile(new File("/efs/sec_efs/sktdm_mem/encwlawp.txt"), 256, null)) : FileUtils.readTextFile(new File("/efs/sec_efs/sktdm_mem/wlawp.txt"), 256, null);
        } catch (IOException e) {
            Log.d("KeyguardCarrierView", "getCarrierLockPlusPassword(), IOException!!");
            e.printStackTrace();
        }
        try {
            if (TextUtils.isEmpty(decryptCarrierLockPlusMsg2)) {
                Log.d("KeyguardCarrierView", "getCarrierLockPlusPassword(), password is null");
            } else {
                String str = decryptCarrierLockPlusMsg2.split(":")[0];
                if (str != null) {
                    bArr = new byte[str.length()];
                    for (int i = 0; i < str.length(); i++) {
                        bArr[i] = (byte) str.charAt(i);
                    }
                    this.mLockPatternUtils.saveRemoteLockPassword(1, bArr, KeyguardUpdateMonitor.getCurrentUser());
                    decryptCarrierLockPlusMsg = !new File("/efs/sec_efs/sktdm_mem/enclawlockmsg.txt").exists() ? decryptCarrierLockPlusMsg(FileUtils.readTextFile(new File("/efs/sec_efs/sktdm_mem/enclawlockmsg.txt"), 256, null)) : FileUtils.readTextFile(new File("/efs/sec_efs/sktdm_mem/lawlockmsg.txt"), 256, null);
                    if (!TextUtils.isEmpty(decryptCarrierLockPlusMsg)) {
                        Log.d("KeyguardCarrierView", "updateCarrierLockPlusMessage(), message is null");
                        return;
                    }
                    String[] split = decryptCarrierLockPlusMsg.split(":");
                    this.mIsShowingOwnerCallButton = split[1].equals("1");
                    setVisibleOwnerCallButton(false);
                    TextView textView = this.mOwnerInfo;
                    if (textView != null) {
                        textView.setText(split[3]);
                    }
                    this.mOwnerMessage = split[3];
                    return;
                }
            }
            if (!new File("/efs/sec_efs/sktdm_mem/enclawlockmsg.txt").exists()) {
            }
            if (!TextUtils.isEmpty(decryptCarrierLockPlusMsg)) {
            }
        } catch (IOException e2) {
            Log.e("KeyguardCarrierView", "updateCarrierLockPlusMessage(), IOException");
            e2.printStackTrace();
            return;
        }
        bArr = null;
        this.mLockPatternUtils.saveRemoteLockPassword(1, bArr, KeyguardUpdateMonitor.getCurrentUser());
    }

    public final void setVisibleOwnerCallButton(boolean z) {
        boolean z2;
        Button button = this.mOwnerCallButton;
        if (button != null) {
            if (z) {
                button.setVisibility(8);
                return;
            }
            int phoneCount = TelephonyManager.getDefault().getPhoneCount();
            String str = SystemProperties.get("gsm.sim.state", "");
            Log.d("KeyguardCarrierView", "isSimStateReadyOrLoaded() : simSlotCount = " + phoneCount + ", simStates = " + str);
            if (str != null) {
                String[] split = str.split(",");
                for (int i = 0; i < phoneCount && split.length > i; i++) {
                    String str2 = split[i];
                    z2 = true;
                    if (str2.equalsIgnoreCase("READY") || str2.equalsIgnoreCase("LOADED")) {
                        break;
                    }
                }
            }
            z2 = false;
            TelephonyManager telephonyManager = this.mTelephonyManager;
            ServiceState serviceState = telephonyManager != null ? telephonyManager.getServiceState() : null;
            if (serviceState != null) {
                StringBuilder sb = new StringBuilder("setVisibleOwnerCallButton state = ");
                sb.append(serviceState.getState());
                sb.append(", CallButton =");
                KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(sb, this.mIsShowingOwnerCallButton, ", isSimStateReadyOrLoaded =", z2, "KeyguardCarrierView");
            }
            if (serviceState != null && serviceState.getState() == 0 && z2 && this.mIsShowingOwnerCallButton && this.mPhoneState != 2) {
                button.setVisibility(0);
            } else {
                button.setVisibility(8);
            }
        }
    }
}
