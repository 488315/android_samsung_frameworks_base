package com.android.keyguard;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
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
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.sec.ims.settings.ImsProfile;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardCarrierViewController extends KeyguardInputViewController {
    public AnonymousClass3 mBroadcastReceiver;
    public final Context mContext;
    public final AnonymousClass4 mEmergencyButtonCallback;
    public final EmergencyButtonController mEmergencyButtonController;
    public boolean mIsShowingOwnerCallButton;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final LockPatternUtils mLockPatternUtils;
    public final Button mOwnerCallButton;
    public final TextView mOwnerInfo;
    public String mOwnerMessage;
    public int mPhoneState;
    public final AnonymousClass1 mPhoneStateListener;
    public final TelephonyManager mTelephonyManager;
    public final Button mUnlockButton;

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.keyguard.KeyguardCarrierViewController$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.keyguard.KeyguardCarrierViewController$4] */
    public KeyguardCarrierViewController(KeyguardCarrierView keyguardCarrierView, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, EmergencyButtonController emergencyButtonController, TelephonyManager telephonyManager, KeyguardMessageAreaController.Factory factory, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor) {
        super(keyguardCarrierView, securityMode, keyguardSecurityCallback, emergencyButtonController, factory, featureFlags, selectedUserInteractor);
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
                KeyguardCarrierViewController keyguardCarrierViewController = KeyguardCarrierViewController.this;
                keyguardCarrierViewController.mPhoneState = i;
                Button button = keyguardCarrierViewController.mOwnerCallButton;
                if (button == null || keyguardCarrierViewController.mUnlockButton == null) {
                    return;
                }
                if (i == 2) {
                    button.setVisibility(8);
                    keyguardCarrierViewController.mUnlockButton.setVisibility(8);
                } else {
                    keyguardCarrierViewController.setVisibleOwnerCallButton(false);
                    keyguardCarrierViewController.mUnlockButton.setVisibility(0);
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
        super.onPause();
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
                        keyguardSecurityCallback.dismiss(true, KeyguardCarrierViewController.this.mSelectedUserInteractor.getSelectedUserId(false), KeyguardCarrierViewController.this.mSecurityMode);
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
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(intentFilter, this.mBroadcastReceiver);
        ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).registerCallback(this.mKeyguardUpdateMonitorCallback);
        this.mTelephonyManager.listen(this.mPhoneStateListener, 1);
        setCarrierLockPlusInfo();
        TextView textView = this.mOwnerInfo;
        if (textView != null && (str = this.mOwnerMessage) != null) {
            textView.setText(str);
        }
        Button button = this.mOwnerCallButton;
        if (button != null) {
            final int i = 0;
            button.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticLambda1
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
                                return;
                            }
                        default:
                            KeyguardCarrierViewController keyguardCarrierViewController2 = this.f$0;
                            KeyguardSecurityCallback keyguardSecurityCallback3 = keyguardSecurityCallback;
                            if (keyguardCarrierViewController2.mLockPatternUtils.isCarrierPasswordSaved(keyguardCarrierViewController2.mSelectedUserInteractor.getSelectedUserId())) {
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
            final int i2 = 1;
            button2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticLambda1
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
                                return;
                            }
                        default:
                            KeyguardCarrierViewController keyguardCarrierViewController2 = this.f$0;
                            KeyguardSecurityCallback keyguardSecurityCallback3 = keyguardSecurityCallback;
                            if (keyguardCarrierViewController2.mLockPatternUtils.isCarrierPasswordSaved(keyguardCarrierViewController2.mSelectedUserInteractor.getSelectedUserId())) {
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
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).unregisterReceiver(this.mBroadcastReceiver);
        ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).removeCallback(this.mKeyguardUpdateMonitorCallback);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x007f A[Catch: IOException -> 0x008d, TryCatch #1 {IOException -> 0x008d, blocks: (B:14:0x0074, B:16:0x007f, B:17:0x009a, B:19:0x00a0, B:21:0x00b6, B:22:0x00bb, B:25:0x00c0, B:27:0x008f), top: B:13:0x0074 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00a0 A[Catch: IOException -> 0x008d, TryCatch #1 {IOException -> 0x008d, blocks: (B:14:0x0074, B:16:0x007f, B:17:0x009a, B:19:0x00a0, B:21:0x00b6, B:22:0x00bb, B:25:0x00c0, B:27:0x008f), top: B:13:0x0074 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c0 A[Catch: IOException -> 0x008d, TRY_LEAVE, TryCatch #1 {IOException -> 0x008d, blocks: (B:14:0x0074, B:16:0x007f, B:17:0x009a, B:19:0x00a0, B:21:0x00b6, B:22:0x00bb, B:25:0x00c0, B:27:0x008f), top: B:13:0x0074 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x008f A[Catch: IOException -> 0x008d, TryCatch #1 {IOException -> 0x008d, blocks: (B:14:0x0074, B:16:0x007f, B:17:0x009a, B:19:0x00a0, B:21:0x00b6, B:22:0x00bb, B:25:0x00c0, B:27:0x008f), top: B:13:0x0074 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setCarrierLockPlusInfo() {
        /*
            r11 = this;
            java.lang.String r0 = "/efs/sec_efs/sktdm_mem/enclawlockmsg.txt"
            java.lang.String r1 = ":"
            java.lang.String r2 = "KeyguardCarrierView"
            com.android.internal.widget.LockPatternUtils r3 = r11.mLockPatternUtils
            r4 = 0
            r5 = 256(0x100, float:3.59E-43)
            r6 = 0
            java.io.File r7 = new java.io.File     // Catch: java.io.IOException -> L27
            r7.<init>(r0)     // Catch: java.io.IOException -> L27
            boolean r7 = r7.exists()     // Catch: java.io.IOException -> L27
            if (r7 == 0) goto L29
            java.io.File r7 = new java.io.File     // Catch: java.io.IOException -> L27
            java.lang.String r8 = "/efs/sec_efs/sktdm_mem/encwlawp.txt"
            r7.<init>(r8)     // Catch: java.io.IOException -> L27
            java.lang.String r7 = android.os.FileUtils.readTextFile(r7, r5, r6)     // Catch: java.io.IOException -> L27
            java.lang.String r7 = decryptCarrierLockPlusMsg(r7)     // Catch: java.io.IOException -> L27
            goto L34
        L27:
            r7 = move-exception
            goto L61
        L29:
            java.io.File r7 = new java.io.File     // Catch: java.io.IOException -> L27
            java.lang.String r8 = "/efs/sec_efs/sktdm_mem/wlawp.txt"
            r7.<init>(r8)     // Catch: java.io.IOException -> L27
            java.lang.String r7 = android.os.FileUtils.readTextFile(r7, r5, r6)     // Catch: java.io.IOException -> L27
        L34:
            boolean r8 = android.text.TextUtils.isEmpty(r7)     // Catch: java.io.IOException -> L27
            if (r8 != 0) goto L5a
            java.lang.String[] r7 = r7.split(r1)     // Catch: java.io.IOException -> L27
            r7 = r7[r4]     // Catch: java.io.IOException -> L27
            if (r7 != 0) goto L43
            goto L5f
        L43:
            int r8 = r7.length()     // Catch: java.io.IOException -> L27
            byte[] r8 = new byte[r8]     // Catch: java.io.IOException -> L27
            r9 = r4
        L4a:
            int r10 = r7.length()     // Catch: java.io.IOException -> L27
            if (r9 >= r10) goto L6a
            char r10 = r7.charAt(r9)     // Catch: java.io.IOException -> L27
            byte r10 = (byte) r10     // Catch: java.io.IOException -> L27
            r8[r9] = r10     // Catch: java.io.IOException -> L27
            int r9 = r9 + 1
            goto L4a
        L5a:
            java.lang.String r7 = "getCarrierLockPlusPassword(), password is null"
            android.util.Log.d(r2, r7)     // Catch: java.io.IOException -> L27
        L5f:
            r8 = r6
            goto L6a
        L61:
            java.lang.String r8 = "getCarrierLockPlusPassword(), IOException!!"
            android.util.Log.d(r2, r8)
            r7.printStackTrace()
            goto L5f
        L6a:
            com.android.systemui.user.domain.interactor.SelectedUserInteractor r7 = r11.mSelectedUserInteractor
            int r7 = r7.getSelectedUserId(r4)
            r9 = 1
            r3.saveRemoteLockPassword(r9, r8, r7)
            java.io.File r3 = new java.io.File     // Catch: java.io.IOException -> L8d
            r3.<init>(r0)     // Catch: java.io.IOException -> L8d
            boolean r3 = r3.exists()     // Catch: java.io.IOException -> L8d
            if (r3 == 0) goto L8f
            java.io.File r3 = new java.io.File     // Catch: java.io.IOException -> L8d
            r3.<init>(r0)     // Catch: java.io.IOException -> L8d
            java.lang.String r0 = android.os.FileUtils.readTextFile(r3, r5, r6)     // Catch: java.io.IOException -> L8d
            java.lang.String r0 = decryptCarrierLockPlusMsg(r0)     // Catch: java.io.IOException -> L8d
            goto L9a
        L8d:
            r11 = move-exception
            goto Lc7
        L8f:
            java.io.File r0 = new java.io.File     // Catch: java.io.IOException -> L8d
            java.lang.String r3 = "/efs/sec_efs/sktdm_mem/lawlockmsg.txt"
            r0.<init>(r3)     // Catch: java.io.IOException -> L8d
            java.lang.String r0 = android.os.FileUtils.readTextFile(r0, r5, r6)     // Catch: java.io.IOException -> L8d
        L9a:
            boolean r3 = android.text.TextUtils.isEmpty(r0)     // Catch: java.io.IOException -> L8d
            if (r3 != 0) goto Lc0
            java.lang.String[] r0 = r0.split(r1)     // Catch: java.io.IOException -> L8d
            r1 = r0[r9]     // Catch: java.io.IOException -> L8d
            java.lang.String r3 = "1"
            boolean r1 = r1.equals(r3)     // Catch: java.io.IOException -> L8d
            r11.mIsShowingOwnerCallButton = r1     // Catch: java.io.IOException -> L8d
            r11.setVisibleOwnerCallButton(r4)     // Catch: java.io.IOException -> L8d
            android.widget.TextView r1 = r11.mOwnerInfo     // Catch: java.io.IOException -> L8d
            r3 = 3
            if (r1 == 0) goto Lbb
            r4 = r0[r3]     // Catch: java.io.IOException -> L8d
            r1.setText(r4)     // Catch: java.io.IOException -> L8d
        Lbb:
            r0 = r0[r3]     // Catch: java.io.IOException -> L8d
            r11.mOwnerMessage = r0     // Catch: java.io.IOException -> L8d
            goto Ld0
        Lc0:
            java.lang.String r11 = "updateCarrierLockPlusMessage(), message is null"
            android.util.Log.d(r2, r11)     // Catch: java.io.IOException -> L8d
            goto Ld0
        Lc7:
            java.lang.String r0 = "updateCarrierLockPlusMessage(), IOException"
            android.util.Log.e(r2, r0)
            r11.printStackTrace()
        Ld0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardCarrierViewController.setCarrierLockPlusInfo():void");
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
                KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(sb, this.mIsShowingOwnerCallButton, ", isSimStateReadyOrLoaded =", z2, "KeyguardCarrierView");
            }
            if (serviceState != null && serviceState.getState() == 0 && z2 && this.mIsShowingOwnerCallButton && this.mPhoneState != 2) {
                this.mOwnerCallButton.setVisibility(0);
            } else {
                this.mOwnerCallButton.setVisibility(8);
            }
        }
    }
}
