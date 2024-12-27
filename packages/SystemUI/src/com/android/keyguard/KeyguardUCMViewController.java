package com.android.keyguard;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.Window;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.domain.interactor.KeyguardKeyboardInteractor;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.ViewController;
import com.android.systemui.vibrate.VibrationUtil;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import java.util.Objects;

public final class KeyguardUCMViewController extends KeyguardSecPinBasedInputViewController {
    public static final Object syncObj = new Object();
    public String mAgentID;
    public CheckUcmPin mCheckUcmPinThread;
    public CheckUcmPuk mCheckUcmPukThread;
    public String mChildSafeMsg;
    public int mError;
    public UCMAsyncTask mGetStatusThread;
    public boolean mIsSupportBiometricForUCM;
    public String mMISCInfo;
    public int mPinMaxLength;
    public int mPinMinLength;
    public String mPinText;
    public int mPukMaxLength;
    public int mPukMinLength;
    public boolean mPukSupported;
    public String mPukText;
    public int mRemainingAttempts;
    public final StateMachine mStateMachine;
    public int mStatus;
    public final TextView mUCMAgent;
    public final TextView mUCMMiscTagValue;
    public boolean mUnlockOngoing;
    public ProgressDialog mUnlockProgressDialog;
    public final String mVendorName;

    /* renamed from: com.android.keyguard.KeyguardUCMViewController$1, reason: invalid class name */
    public final class AnonymousClass1 extends CheckUcmPin {
        public AnonymousClass1(String str) {
            super(str);
        }

        @Override // com.android.keyguard.KeyguardUCMViewController.CheckUcmPin
        public final void onVerifyPinResponse(int i, int i2, Bundle bundle) {
            ((KeyguardUCMView) ((ViewController) KeyguardUCMViewController.this).mView).post(new KeyguardUCMViewController$1$$ExternalSyntheticLambda0(this, i, i2, bundle));
        }
    }

    /* renamed from: com.android.keyguard.KeyguardUCMViewController$2, reason: invalid class name */
    public final class AnonymousClass2 extends CheckUcmPuk {
        public AnonymousClass2(String str, String str2) {
            super(str, str2);
        }

        @Override // com.android.keyguard.KeyguardUCMViewController.CheckUcmPuk
        public final void onVerifyPukResponse(int i, int i2, Bundle bundle) {
            ((KeyguardUCMView) ((ViewController) KeyguardUCMViewController.this).mView).post(new KeyguardUCMViewController$1$$ExternalSyntheticLambda0(this, i, i2, bundle));
        }
    }

    public abstract class CheckUcmPin extends Thread {
        public final String mPin;

        public CheckUcmPin(String str) {
            Log.d("KeyguardUCMPinView", "new CheckUcmPin");
            this.mPin = str;
        }

        public abstract void onVerifyPinResponse(int i, int i2, Bundle bundle);

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                int[] m847$$Nest$mverifyPIN = KeyguardUCMViewController.m847$$Nest$mverifyPIN(KeyguardUCMViewController.this, this.mPin);
                int i = m847$$Nest$mverifyPIN[0];
                KeyguardUCMViewController keyguardUCMViewController = KeyguardUCMViewController.this;
                if (!keyguardUCMViewController.mUnlockOngoing) {
                    Log.d("KeyguardUCMPinView", "In race condition, stop unlock operation");
                    KeyguardUCMViewController.this.mCheckUcmPinThread = null;
                    return;
                }
                Bundle m842$$Nest$mgeneratePassword = KeyguardUCMViewController.m842$$Nest$mgeneratePassword(keyguardUCMViewController, i);
                KeyguardUCMViewController keyguardUCMViewController2 = KeyguardUCMViewController.this;
                if (keyguardUCMViewController2.mUnlockOngoing) {
                    ((KeyguardUCMView) ((ViewController) keyguardUCMViewController2).mView).post(new KeyguardUCMViewController$CheckUcmPin$$ExternalSyntheticLambda0(this, m847$$Nest$mverifyPIN, m842$$Nest$mgeneratePassword));
                } else {
                    Log.d("KeyguardUCMPinView", "In race condition, stop unlock operation");
                    KeyguardUCMViewController.this.mCheckUcmPinThread = null;
                }
            } catch (Exception e) {
                Log.e("KeyguardUCMPinView", "Exception for verifyPIN : ", e);
                ((KeyguardUCMView) ((ViewController) KeyguardUCMViewController.this).mView).post(new KeyguardUCMViewController$CheckUcmPin$$ExternalSyntheticLambda1(this));
            }
        }
    }

    public abstract class CheckUcmPuk extends Thread {
        public final String mPin;
        public final String mPuk;

        public CheckUcmPuk(String str, String str2) {
            this.mPuk = str;
            this.mPin = str2;
        }

        public abstract void onVerifyPukResponse(int i, int i2, Bundle bundle);

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                int[] m848$$Nest$mverifyPUK = KeyguardUCMViewController.m848$$Nest$mverifyPUK(KeyguardUCMViewController.this, this.mPuk, this.mPin);
                int i = m848$$Nest$mverifyPUK[0];
                KeyguardUCMViewController keyguardUCMViewController = KeyguardUCMViewController.this;
                if (!keyguardUCMViewController.mUnlockOngoing) {
                    Log.d("KeyguardUCMPinView", "In race condition, stop unlock operation");
                    KeyguardUCMViewController.this.mCheckUcmPukThread = null;
                    return;
                }
                Bundle m842$$Nest$mgeneratePassword = KeyguardUCMViewController.m842$$Nest$mgeneratePassword(keyguardUCMViewController, i);
                KeyguardUCMViewController keyguardUCMViewController2 = KeyguardUCMViewController.this;
                if (keyguardUCMViewController2.mUnlockOngoing) {
                    ((KeyguardUCMView) ((ViewController) keyguardUCMViewController2).mView).post(new KeyguardUCMViewController$CheckUcmPin$$ExternalSyntheticLambda0(this, m848$$Nest$mverifyPUK, m842$$Nest$mgeneratePassword));
                } else {
                    Log.d("KeyguardUCMPinView", "In race condition, stop unlock operation");
                    KeyguardUCMViewController.this.mCheckUcmPukThread = null;
                }
            } catch (Exception e) {
                Log.e("KeyguardUCMPinView", "RemoteException for supplyPukReportResult:", e);
                ((KeyguardUCMView) ((ViewController) KeyguardUCMViewController.this).mView).post(new KeyguardUCMViewController$CheckUcmPin$$ExternalSyntheticLambda1(this));
            }
        }
    }

    public final class StateMachine {
        public int mRound;
        public int mState;

        public /* synthetic */ StateMachine(KeyguardUCMViewController keyguardUCMViewController, int i) {
            this();
        }

        public final String getDetailErrorMessage(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "getDetailErrorMessage errorCode : ", "KeyguardUCMPinView");
            String str = "";
            KeyguardUCMViewController keyguardUCMViewController = KeyguardUCMViewController.this;
            if (i == 0) {
                return keyguardUCMViewController.getContext().getString(R.string.kg_ucm_unknown_error_occurred);
            }
            try {
                keyguardUCMViewController.getClass();
                IUcmService uCMService = KeyguardUCMViewController.getUCMService();
                if (uCMService != null && (str = uCMService.getDetailErrorMessage(keyguardUCMViewController.getCSUri(), i)) == null) {
                    str = KeyguardUCMViewController.m843$$Nest$mgetErrorMessage(keyguardUCMViewController, i);
                }
            } catch (Exception e) {
                KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("exception in getDetailErrorMessage : "), "KeyguardUCMPinView");
            }
            return str == null ? KeyguardUCMViewController.m843$$Nest$mgetErrorMessage(keyguardUCMViewController, i) : str;
        }

        public final void setStateAndRefreshUIIfNeeded(int i, int i2, boolean z, Bundle bundle) {
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, i2, "StateMachine.setStateAndRefreshUIIfNeeded called : ", " Error : ", "KeyguardUCMPinView");
            final KeyguardUCMViewController keyguardUCMViewController = KeyguardUCMViewController.this;
            String m843$$Nest$mgetErrorMessage = KeyguardUCMViewController.m843$$Nest$mgetErrorMessage(keyguardUCMViewController, i2);
            this.mState = i;
            if (i == -1) {
                Log.d("KeyguardUCMPinView", "setStateAndRefreshUIIfNeeded called : STATE_UNKNOWN");
                keyguardUCMViewController.setMessageSecurityMessageDisplay(getDetailErrorMessage(i2));
                keyguardUCMViewController.mUCMMiscTagValue.setVisibility(8);
                this.mRound = 5;
            }
            SelectedUserInteractor selectedUserInteractor = keyguardUCMViewController.mSelectedUserInteractor;
            switch (i) {
                case 131:
                    Log.d("KeyguardUCMPinView", "setStateAndRefreshUIIfNeeded called : STATE_UNLOCKED");
                    if (!z) {
                        keyguardUCMViewController.mUCMMiscTagValue.setVisibility(8);
                        this.mRound = 0;
                        if (i2 != 0) {
                            keyguardUCMViewController.setMessageSecurityMessageDisplay(m843$$Nest$mgetErrorMessage);
                            break;
                        } else if (bundle != null) {
                            byte[] byteArray = bundle.getByteArray(UcmAgentService.PLUGIN_BYTEARRAY_RESPONSE);
                            int i3 = bundle.getInt(UcmAgentService.PLUGIN_ERROR_CODE, -1);
                            if (byteArray != null && byteArray.length > 0) {
                                final int selectedUserId = selectedUserInteractor.getSelectedUserId(false);
                                AsyncTask asyncTask = keyguardUCMViewController.mPendingLockCheck;
                                if (asyncTask != null) {
                                    asyncTask.cancel(false);
                                }
                                keyguardUCMViewController.mPendingLockCheck = LockPatternChecker.checkCredential(keyguardUCMViewController.mLockPatternUtils, LockscreenCredential.createSmartcardPassword(byteArray), selectedUserId, new LockPatternChecker.OnCheckCallback() { // from class: com.android.keyguard.KeyguardUCMViewController$$ExternalSyntheticLambda0
                                    public final void onChecked(boolean z2, int i4) {
                                        KeyguardUCMViewController keyguardUCMViewController2 = KeyguardUCMViewController.this;
                                        int i5 = selectedUserId;
                                        keyguardUCMViewController2.mPendingLockCheck = null;
                                        keyguardUCMViewController2.onPasswordChecked(i5, i4, z2, true);
                                    }
                                });
                                break;
                            } else {
                                keyguardUCMViewController.setMessageSecurityMessageDisplay(getDetailErrorMessage(i3));
                                break;
                            }
                        } else {
                            Log.d("KeyguardUCMPinView", "failed to get the generatePassword values");
                            keyguardUCMViewController.setMessageSecurityMessageDisplay(keyguardUCMViewController.getContext().getString(R.string.kg_ucm_unknown_error_occurred));
                            break;
                        }
                    } else {
                        if (i2 == 0) {
                            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(keyguardUCMViewController.getContext().getString(R.string.kg_none_pin_none_instructions), "\n");
                            m.append(KeyguardUCMViewController.m844$$Nest$mgetRemainingCount(keyguardUCMViewController, keyguardUCMViewController.mRemainingAttempts));
                            m843$$Nest$mgetErrorMessage = m.toString();
                        }
                        keyguardUCMViewController.setMessageSecurityMessageDisplay(m843$$Nest$mgetErrorMessage);
                        break;
                    }
                case 132:
                    Log.d("KeyguardUCMPinView", "setStateAndRefreshUIIfNeeded called : STATE_LOCKED");
                    String str = keyguardUCMViewController.mChildSafeMsg;
                    if (str != null && !str.isEmpty()) {
                        if (keyguardUCMViewController.mIsSupportBiometricForUCM) {
                            keyguardUCMViewController.mLockPatternUtils.requireStrongAuth(2, selectedUserInteractor.getSelectedUserId());
                        }
                        m843$$Nest$mgetErrorMessage = keyguardUCMViewController.mChildSafeMsg;
                    } else if (i2 == 32) {
                        StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(keyguardUCMViewController.getContext().getString(R.string.kg_wrong_pin), "\n");
                        m2.append(KeyguardUCMViewController.m844$$Nest$mgetRemainingCount(keyguardUCMViewController, keyguardUCMViewController.mRemainingAttempts));
                        m843$$Nest$mgetErrorMessage = m2.toString();
                    } else if (i2 == 0) {
                        StringBuilder m3 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(keyguardUCMViewController.getContext().getString(R.string.kg_none_pin_none_instructions), "\n");
                        m3.append(KeyguardUCMViewController.m844$$Nest$mgetRemainingCount(keyguardUCMViewController, keyguardUCMViewController.mRemainingAttempts));
                        m843$$Nest$mgetErrorMessage = m3.toString();
                    }
                    keyguardUCMViewController.setMessageSecurityMessageDisplay(m843$$Nest$mgetErrorMessage);
                    keyguardUCMViewController.mUCMMiscTagValue.setVisibility(8);
                    this.mRound = 0;
                    break;
                case 133:
                    Log.d("KeyguardUCMPinView", "setStateAndRefreshUIIfNeeded called : STATE_BLOCKED");
                    if (keyguardUCMViewController.mIsSupportBiometricForUCM) {
                        keyguardUCMViewController.mLockPatternUtils.requireStrongAuth(2, selectedUserInteractor.getSelectedUserId());
                    }
                    if (keyguardUCMViewController.mPukSupported) {
                        if (i2 == 33 && keyguardUCMViewController.mRemainingAttempts <= 0) {
                            m843$$Nest$mgetErrorMessage = keyguardUCMViewController.getContext().getString(R.string.kg_ucm_puk_limit_exceeded);
                        } else if (i2 == 33) {
                            StringBuilder m4 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(keyguardUCMViewController.getContext().getString(R.string.kg_ucm_password_wrong_puk_code), "\n");
                            m4.append(KeyguardUCMViewController.m844$$Nest$mgetRemainingCount(keyguardUCMViewController, keyguardUCMViewController.mRemainingAttempts));
                            m843$$Nest$mgetErrorMessage = m4.toString();
                        } else if (i2 == 0) {
                            StringBuilder m5 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(keyguardUCMViewController.getContext().getString(R.string.kg_puk_locked_message), "\n");
                            m5.append(KeyguardUCMViewController.m844$$Nest$mgetRemainingCount(keyguardUCMViewController, keyguardUCMViewController.mRemainingAttempts));
                            m843$$Nest$mgetErrorMessage = m5.toString();
                        } else if (i2 == 32) {
                            StringBuilder m6 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(keyguardUCMViewController.getContext().getString(R.string.kg_puk_locked_message), "\n");
                            m6.append(KeyguardUCMViewController.m844$$Nest$mgetRemainingCount(keyguardUCMViewController, keyguardUCMViewController.mRemainingAttempts));
                            m843$$Nest$mgetErrorMessage = m6.toString();
                        }
                        this.mRound = 1;
                    } else {
                        m843$$Nest$mgetErrorMessage = keyguardUCMViewController.getContext().getString(R.string.lockscreen_smartcard_expired);
                        Log.d("KeyguardUCMPinView", "pinExpireMessage");
                        this.mRound = 5;
                    }
                    String str2 = keyguardUCMViewController.mMISCInfo;
                    if (str2 != null && str2.length() > 0) {
                        keyguardUCMViewController.mUCMMiscTagValue.setVisibility(0);
                        keyguardUCMViewController.mUCMMiscTagValue.setText(keyguardUCMViewController.mMISCInfo);
                    }
                    keyguardUCMViewController.setMessageSecurityMessageDisplay(m843$$Nest$mgetErrorMessage);
                    break;
                default:
                    Log.d("KeyguardUCMPinView", "unknown status nothing to do");
                    break;
            }
        }

        private StateMachine() {
            this.mRound = 0;
            this.mState = -1;
        }
    }

    public final class UCMAsyncTask extends AsyncTask {
        public final int opCode = 1;

        public UCMAsyncTask() {
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            String cSUri;
            Bundle status;
            if (this.opCode == 1) {
                for (int i = 0; i < 10; i++) {
                    KeyguardUCMViewController keyguardUCMViewController = KeyguardUCMViewController.this;
                    synchronized (keyguardUCMViewController) {
                        Log.d("KeyguardUCMPinView", "getAgentInfoAndUpdateStatus called");
                        IUcmService uCMService = KeyguardUCMViewController.getUCMService();
                        if (uCMService == null) {
                            Log.d("KeyguardUCMPinView", "failed to get UCM service");
                        } else {
                            try {
                                cSUri = keyguardUCMViewController.getCSUri();
                                status = uCMService.getStatus(cSUri);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            if (status == null) {
                                Log.d("KeyguardUCMPinView", "failed to get getStatus");
                            } else {
                                if (status.getInt(UcmAgentService.PLUGIN_ERROR_CODE, 0) == 14) {
                                    Log.d("KeyguardUCMPinView", "Boot init condition");
                                    uCMService.updateAgentList();
                                    for (int i2 = 0; i2 < 10; i2++) {
                                        status = uCMService.getStatus(cSUri);
                                        if (status == null) {
                                            Log.d("KeyguardUCMPinView", "failed to get getStatus");
                                            break;
                                        }
                                        if (status.getInt(UcmAgentService.PLUGIN_ERROR_CODE, 0) != 14) {
                                            break;
                                        }
                                        Log.d("KeyguardUCMPinView", "UcmAgentService.ERROR_NO_PLUGIN_AGENT_FOUND error");
                                        try {
                                            Thread.sleep(1000L);
                                        } catch (InterruptedException e2) {
                                            e2.printStackTrace();
                                        }
                                    }
                                }
                                keyguardUCMViewController.mStatus = status.getInt("state", -1);
                                keyguardUCMViewController.mChildSafeMsg = status.getString(UcmAgentService.LOCKSCREEN_MESSAGE, "");
                                keyguardUCMViewController.mMISCInfo = status.getString(UcmAgentService.MISC_INFO, "");
                                keyguardUCMViewController.mPinMinLength = status.getInt("minPinLength", 0);
                                keyguardUCMViewController.mPinMaxLength = status.getInt("maxPinLength", 0);
                                keyguardUCMViewController.mPukMinLength = status.getInt("minPukLength", 0);
                                keyguardUCMViewController.mPukMaxLength = status.getInt("maxPukLength", 0);
                                keyguardUCMViewController.mRemainingAttempts = status.getInt("remainCnt", 0);
                                keyguardUCMViewController.mError = status.getInt(UcmAgentService.PLUGIN_ERROR_CODE, 0);
                                Log.d("KeyguardUCMPinView", "status " + keyguardUCMViewController.mStatus);
                                Log.d("KeyguardUCMPinView", "pin puk " + keyguardUCMViewController.mPinMinLength + " " + keyguardUCMViewController.mPinMaxLength + " " + keyguardUCMViewController.mPukMinLength + " " + keyguardUCMViewController.mPukMaxLength);
                                StringBuilder sb = new StringBuilder();
                                sb.append("misc : ");
                                sb.append(keyguardUCMViewController.mMISCInfo);
                                Log.d("KeyguardUCMPinView", sb.toString());
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("pin remain : ");
                                sb2.append(keyguardUCMViewController.mRemainingAttempts);
                                Log.d("KeyguardUCMPinView", sb2.toString());
                                Log.d("KeyguardUCMPinView", "error : " + keyguardUCMViewController.mError);
                            }
                        }
                    }
                    KeyguardUCMViewController keyguardUCMViewController2 = KeyguardUCMViewController.this;
                    if (keyguardUCMViewController2.mStatus != -1 || keyguardUCMViewController2.mError != 0) {
                        break;
                    }
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e3) {
                        e3.printStackTrace();
                    }
                    Log.d("KeyguardUCMPinView", "GetStatus thread result : " + KeyguardUCMViewController.this.mStatus);
                }
                if (KeyguardUCMViewController.this.getKeyguardSecurityCallback() != null) {
                    KeyguardUCMViewController.this.getKeyguardSecurityCallback().userActivity();
                }
                synchronized (KeyguardUCMViewController.syncObj) {
                    KeyguardUCMViewController.this.mGetStatusThread = null;
                }
            }
            return null;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            KeyguardUCMViewController keyguardUCMViewController = KeyguardUCMViewController.this;
            keyguardUCMViewController.mStateMachine.setStateAndRefreshUIIfNeeded(keyguardUCMViewController.mStatus, keyguardUCMViewController.mError, true, null);
            KeyguardUCMViewController.m846$$Nest$mstopProgress(KeyguardUCMViewController.this);
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            KeyguardUCMViewController.m845$$Nest$mstartProgress(KeyguardUCMViewController.this, 1 == this.opCode);
        }
    }

    /* renamed from: -$$Nest$mgeneratePassword, reason: not valid java name */
    public static Bundle m842$$Nest$mgeneratePassword(KeyguardUCMViewController keyguardUCMViewController, int i) {
        keyguardUCMViewController.getClass();
        Log.d("KeyguardUCMPinView", "generatePassword called");
        if (i != 131) {
            Log.d("KeyguardUCMPinView", "Do not need to get password");
            return null;
        }
        IUcmService uCMService = getUCMService();
        if (uCMService == null) {
            Log.d("KeyguardUCMPinView", "mUcmBinder == null");
            return null;
        }
        try {
            return uCMService.generateKeyguardPassword(0, keyguardUCMViewController.getCSUri(), null);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: -$$Nest$mgetErrorMessage, reason: not valid java name */
    public static String m843$$Nest$mgetErrorMessage(KeyguardUCMViewController keyguardUCMViewController, int i) {
        keyguardUCMViewController.getClass();
        String str = "\n(" + String.format("0x%08X", Integer.valueOf(i)) + ")";
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                break;
            default:
                switch (i) {
                    case 257:
                    case 258:
                    case 259:
                    case 260:
                    case 261:
                    case 262:
                    case 263:
                    case 264:
                    case 265:
                    case 266:
                    case 267:
                    case 268:
                    case 269:
                    case 270:
                    case 271:
                        break;
                    default:
                        switch (i) {
                            case 4096:
                            case 8191:
                            case UcmAgentService.ERROR_APPLET_INSTALLATION /* 150994944 */:
                            case UcmAgentService.ERROR_UNREADABLE_ODE_CONFIGURATION /* 201326848 */:
                            case UcmAgentService.ERROR_INVALID_ODE_CONFIGURATION /* 201327104 */:
                                break;
                            case UcmAgentService.ERROR_APDU_CREATION /* 16777472 */:
                            case UcmAgentService.ERROR_BAD_APPLET_RESPONSE /* 16777728 */:
                            case UcmAgentService.ERROR_SMARTCARD_UNAVAILABLE /* 16777984 */:
                            case UcmAgentService.ERROR_INTERNAL_COMMUNICATION /* 16778240 */:
                            case UcmAgentService.ERROR_OPEN_SESSION_IO_EXCEPTION /* 33554945 */:
                                break;
                            case 134217728:
                                return keyguardUCMViewController.getResources().getString(R.string.kg_ucm_smartcard_error) + str;
                            default:
                                switch (i) {
                                    case UcmAgentService.ERROR_GET_READERS_NULL_POINTER_EXCEPTION /* 33554689 */:
                                    case UcmAgentService.ERROR_GET_READERS_ILLEGAL_STATE_EXCEPTION /* 33554690 */:
                                        break;
                                    default:
                                        switch (i) {
                                            case UcmAgentService.ERROR_OPEN_LOGICAL_CHANNEL_IO_EXCEPTION /* 33555201 */:
                                            case UcmAgentService.ERROR_OPEN_LOGICAL_CHANNEL_ILLEGAL_STATE_EXCEPTION /* 33555202 */:
                                            case UcmAgentService.ERROR_OPEN_LOGICAL_CHANNEL_ILLEGAL_ARGUMENT_EXCEPTION /* 33555203 */:
                                            case UcmAgentService.ERROR_OPEN_LOGICAL_CHANNEL_SECURITY_EXCEPTION /* 33555204 */:
                                            case UcmAgentService.ERROR_OPEN_LOGICAL_CHANNEL_NO_SUCH_ELEMENT_EXCEPTION /* 33555205 */:
                                            case UcmAgentService.ERROR_OPEN_LOGICAL_CHANNEL_UNKNOWN /* 33555206 */:
                                                break;
                                            default:
                                                switch (i) {
                                                    case UcmAgentService.ERROR_TRANSMIT_IO_EXCEPTION /* 33555457 */:
                                                    case UcmAgentService.ERROR_TRANSMIT_ILLEGAL_STATE_EXCEPTION /* 33555458 */:
                                                    case UcmAgentService.ERROR_TRANSMIT_ILLEGAL_ARGUMENT_EXCEPTION /* 33555459 */:
                                                    case UcmAgentService.ERROR_TRANSMIT_SECURITY_EXCEPTION /* 33555460 */:
                                                    case UcmAgentService.ERROR_TRANSMIT_NULL_POINTER_EXCEPTION /* 33555461 */:
                                                    case UcmAgentService.ERROR_TRANSMIT_UNKNOWN /* 33555462 */:
                                                        break;
                                                    default:
                                                        switch (i) {
                                                            case UcmAgentService.ERROR_NO_SESSION_AVAILABLE /* 33555713 */:
                                                            case UcmAgentService.ERROR_FAILED_TO_GET_READER_FOR_STORAGE /* 33555714 */:
                                                                break;
                                                            default:
                                                                switch (i) {
                                                                    case UcmAgentService.ERROR_SCP_UNKNOWN /* 50331648 */:
                                                                    case UcmAgentService.ERROR_SCP_ENCRYPTION_FAILED /* 50331649 */:
                                                                    case UcmAgentService.ERROR_SCP_DECRYPTION_FAILED /* 50331650 */:
                                                                    case UcmAgentService.ERROR_SCP_CREATE_CHANNEL_FAILED /* 50331651 */:
                                                                    case UcmAgentService.ERROR_SCP_NULL_RESPONSE_RECV /* 50331652 */:
                                                                        break;
                                                                    default:
                                                                        if (134217728 >= i || 134283264 <= i) {
                                                                            return keyguardUCMViewController.getResources().getString(R.string.kg_ucm_unknown_error) + str;
                                                                        }
                                                                        String format = String.format("0x%08X", Integer.valueOf(i));
                                                                        return keyguardUCMViewController.getResources().getString(R.string.kg_ucm_smartcard_error) + "\n(" + format.substring(format.length() - 4, format.length()) + ")";
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                        return keyguardUCMViewController.getResources().getString(R.string.kg_ucm_communication_error) + str;
                }
        }
        return keyguardUCMViewController.getResources().getString(R.string.kg_ucm_internal_error) + str;
    }

    /* renamed from: -$$Nest$mgetRemainingCount, reason: not valid java name */
    public static String m844$$Nest$mgetRemainingCount(KeyguardUCMViewController keyguardUCMViewController, int i) {
        return keyguardUCMViewController.getResources().getQuantityString(R.plurals.kg_ucm_attempt_left, i, Integer.valueOf(i));
    }

    /* renamed from: -$$Nest$mstartProgress, reason: not valid java name */
    public static void m845$$Nest$mstartProgress(KeyguardUCMViewController keyguardUCMViewController, boolean z) {
        keyguardUCMViewController.getUnlockProgressDialog(z).show();
        ((KeyguardUCMView) keyguardUCMViewController.mView).setKeepScreenOn(true);
    }

    /* renamed from: -$$Nest$mstopProgress, reason: not valid java name */
    public static void m846$$Nest$mstopProgress(KeyguardUCMViewController keyguardUCMViewController) {
        ProgressDialog progressDialog = keyguardUCMViewController.mUnlockProgressDialog;
        if (progressDialog != null) {
            progressDialog.cancel();
            keyguardUCMViewController.mUnlockProgressDialog = null;
        }
        ((KeyguardUCMView) keyguardUCMViewController.mView).setKeepScreenOn(false);
    }

    /* renamed from: -$$Nest$mverifyPIN, reason: not valid java name */
    public static int[] m847$$Nest$mverifyPIN(KeyguardUCMViewController keyguardUCMViewController, String str) {
        keyguardUCMViewController.getClass();
        Log.d("KeyguardUCMPinView", "verifyPIN called");
        int[] iArr = {-1, -1, -1};
        IUcmService uCMService = getUCMService();
        if (uCMService == null) {
            Log.d("KeyguardUCMPinView", "failed to get UCM service");
        } else {
            try {
                Bundle verifyPin = uCMService.verifyPin(0, keyguardUCMViewController.getCSUri(), str, null);
                keyguardUCMViewController.mChildSafeMsg = verifyPin.getString(UcmAgentService.LOCKSCREEN_MESSAGE, "");
                keyguardUCMViewController.mStatus = verifyPin.getInt("state", -1);
                keyguardUCMViewController.mRemainingAttempts = verifyPin.getInt("remainCnt", -1);
                keyguardUCMViewController.mMISCInfo = verifyPin.getString(UcmAgentService.MISC_INFO, "");
                int i = verifyPin.getInt(UcmAgentService.PLUGIN_ERROR_CODE, -1);
                Log.d("KeyguardUCMPinView", "state : " + keyguardUCMViewController.mStatus);
                Log.d("KeyguardUCMPinView", "remainCnt : " + keyguardUCMViewController.mRemainingAttempts);
                Log.d("KeyguardUCMPinView", "errorCode : " + i);
                if (keyguardUCMViewController.mStatus == 131) {
                    Log.d("KeyguardUCMPinView", "PIN verification succeed");
                } else {
                    Log.d("KeyguardUCMPinView", "PIN verification failed");
                }
                iArr[0] = keyguardUCMViewController.mStatus;
                iArr[1] = keyguardUCMViewController.mRemainingAttempts;
                iArr[2] = i;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return iArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0030, code lost:
    
        if (r10.trim().length() > 0) goto L7;
     */
    /* renamed from: -$$Nest$mverifyPUK, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int[] m848$$Nest$mverifyPUK(com.android.keyguard.KeyguardUCMViewController r13, java.lang.String r14, java.lang.String r15) {
        /*
            r13.getClass()
            java.lang.String r0 = "errorCode : "
            java.lang.String r1 = "remainCnt : "
            java.lang.String r2 = "state : "
            java.lang.String r3 = "verifyPUK called"
            java.lang.String r4 = "KeyguardUCMPinView"
            android.util.Log.d(r4, r3)
            r3 = 3
            int[] r3 = new int[r3]
            r5 = 0
            r6 = -1
            r3[r5] = r6
            r7 = 1
            r3[r7] = r6
            r8 = 2
            r3[r8] = r6
            r9 = 0
            if (r14 != 0) goto L24
        L22:
            r10 = r9
            goto L32
        L24:
            java.lang.String r10 = r14.trim()
            java.lang.String r11 = r10.trim()
            int r11 = r11.length()
            if (r11 <= 0) goto L22
        L32:
            if (r15 != 0) goto L35
            goto L44
        L35:
            java.lang.String r11 = r15.trim()
            java.lang.String r12 = r11.trim()
            int r12 = r12.length()
            if (r12 <= 0) goto L44
            r9 = r11
        L44:
            if (r10 == 0) goto Ld5
            if (r9 != 0) goto L4a
            goto Ld5
        L4a:
            com.samsung.android.knox.ucm.core.IUcmService r9 = getUCMService()
            if (r9 != 0) goto L59
            java.lang.String r13 = "failed to get UCM service"
            android.util.Log.d(r4, r13)
            r3[r5] = r6
            goto Ld7
        L59:
            java.lang.String r10 = r13.getCSUri()     // Catch: android.os.RemoteException -> Lb9
            android.os.Bundle r14 = r9.verifyPuk(r10, r14, r15)     // Catch: android.os.RemoteException -> Lb9
            java.lang.String r15 = "state"
            int r15 = r14.getInt(r15, r6)     // Catch: android.os.RemoteException -> Lb9
            r13.mStatus = r15     // Catch: android.os.RemoteException -> Lb9
            java.lang.String r15 = "remainCnt"
            int r15 = r14.getInt(r15, r6)     // Catch: android.os.RemoteException -> Lb9
            r13.mRemainingAttempts = r15     // Catch: android.os.RemoteException -> Lb9
            java.lang.String r15 = "errorresponse"
            int r14 = r14.getInt(r15, r6)     // Catch: android.os.RemoteException -> Lb9
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> Lb9
            r15.<init>(r2)     // Catch: android.os.RemoteException -> Lb9
            int r2 = r13.mStatus     // Catch: android.os.RemoteException -> Lb9
            r15.append(r2)     // Catch: android.os.RemoteException -> Lb9
            java.lang.String r15 = r15.toString()     // Catch: android.os.RemoteException -> Lb9
            android.util.Log.d(r4, r15)     // Catch: android.os.RemoteException -> Lb9
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> Lb9
            r15.<init>(r1)     // Catch: android.os.RemoteException -> Lb9
            int r1 = r13.mRemainingAttempts     // Catch: android.os.RemoteException -> Lb9
            r15.append(r1)     // Catch: android.os.RemoteException -> Lb9
            java.lang.String r15 = r15.toString()     // Catch: android.os.RemoteException -> Lb9
            android.util.Log.d(r4, r15)     // Catch: android.os.RemoteException -> Lb9
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> Lb9
            r15.<init>(r0)     // Catch: android.os.RemoteException -> Lb9
            r15.append(r14)     // Catch: android.os.RemoteException -> Lb9
            java.lang.String r15 = r15.toString()     // Catch: android.os.RemoteException -> Lb9
            android.util.Log.d(r4, r15)     // Catch: android.os.RemoteException -> Lb9
            int r15 = r13.mStatus     // Catch: android.os.RemoteException -> Lb9
            r0 = 131(0x83, float:1.84E-43)
            if (r15 == r0) goto Lc1
            r0 = 132(0x84, float:1.85E-43)
            if (r15 == r0) goto Lbb
            java.lang.String r15 = "PUK verification failed : BLOCKED"
            android.util.Log.d(r4, r15)     // Catch: android.os.RemoteException -> Lb9
            goto Lc6
        Lb9:
            r13 = move-exception
            goto Ld1
        Lbb:
            java.lang.String r15 = "PUK verification succeed : LOCKED"
            android.util.Log.d(r4, r15)     // Catch: android.os.RemoteException -> Lb9
            goto Lc6
        Lc1:
            java.lang.String r15 = "PUK verification succeed : UNLOCKED"
            android.util.Log.d(r4, r15)     // Catch: android.os.RemoteException -> Lb9
        Lc6:
            int r15 = r13.mStatus     // Catch: android.os.RemoteException -> Lb9
            r3[r5] = r15     // Catch: android.os.RemoteException -> Lb9
            int r13 = r13.mRemainingAttempts     // Catch: android.os.RemoteException -> Lb9
            r3[r7] = r13     // Catch: android.os.RemoteException -> Lb9
            r3[r8] = r14     // Catch: android.os.RemoteException -> Lb9
            goto Ld7
        Ld1:
            r13.printStackTrace()
            goto Ld7
        Ld5:
            r3[r5] = r6
        Ld7:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardUCMViewController.m848$$Nest$mverifyPUK(com.android.keyguard.KeyguardUCMViewController, java.lang.String, java.lang.String):int[]");
    }

    public KeyguardUCMViewController(KeyguardUCMView keyguardUCMView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector, FeatureFlags featureFlags, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, ConfigurationController configurationController, SelectedUserInteractor selectedUserInteractor, KeyguardKeyboardInteractor keyguardKeyboardInteractor) {
        super(keyguardUCMView, configurationController, vibrationUtil, accessibilityManager, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, emergencyButtonController, falsingCollector, featureFlags, selectedUserInteractor, keyguardKeyboardInteractor);
        String str;
        String str2 = null;
        this.mUnlockProgressDialog = null;
        this.mAgentID = null;
        this.mPukSupported = false;
        this.mIsSupportBiometricForUCM = false;
        this.mMISCInfo = null;
        this.mRemainingAttempts = 0;
        this.mPinMinLength = 0;
        this.mPinMaxLength = 0;
        this.mPukMinLength = 0;
        this.mPukMaxLength = 0;
        this.mStatus = -1;
        this.mError = 0;
        this.mStateMachine = new StateMachine(this, 0);
        this.mChildSafeMsg = null;
        this.mUnlockOngoing = false;
        IUcmService uCMService = getUCMService();
        if (uCMService == null) {
            Log.d("KeyguardUCMPinView", "failed to get UCM service");
        } else {
            try {
                str = uCMService.getKeyguardStorageForCurrentUser(this.mSelectedUserInteractor.getSelectedUserId(false));
            } catch (RemoteException e) {
                e.printStackTrace();
                str = null;
            }
            if (str != null && !str.equals("") && !str.equals(SignalSeverity.NONE)) {
                str2 = str;
            }
        }
        this.mVendorName = str2;
        getVendorID();
        this.mUCMMiscTagValue = (TextView) ((KeyguardUCMView) this.mView).findViewById(R.id.ucm_misctag);
        TextView textView = (TextView) ((KeyguardUCMView) this.mView).findViewById(R.id.ucm_csname);
        this.mUCMAgent = textView;
        String str3 = this.mAgentID;
        if (str3 != null) {
            textView.setText(str3);
        } else {
            textView.setVisibility(8);
        }
    }

    public static IUcmService getUCMService() {
        IUcmService asInterface = IUcmService.Stub.asInterface(ServiceManager.getService("com.samsung.ucs.ucsservice"));
        if (asInterface == null) {
            Log.d("KeyguardUCMPinView", "failed to get UCM service");
        }
        return asInterface;
    }

    public final String getCSUri() {
        String uri = UniversalCredentialUtil.getUri(this.mVendorName, "");
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getCSUri returns : ", uri, "KeyguardUCMPinView");
        return uri;
    }

    public final String getPasswordTextByString() {
        PasswordTextView passwordTextView = this.mPasswordEntry;
        return passwordTextView instanceof SecPasswordTextView ? ((SecPasswordTextView) passwordTextView).mText : passwordTextView.getText().toString();
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final int getSecurityViewId() {
        return R.id.keyguard_ucm_pin_view;
    }

    public final Dialog getUnlockProgressDialog(boolean z) {
        Log.d("KeyguardUCMPinView", "getUnlockProgressDialog called");
        if (this.mUnlockProgressDialog == null) {
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            this.mUnlockProgressDialog = progressDialog;
            if (z) {
                progressDialog.setMessage(getContext().getString(R.string.kg_ucm_loading));
            } else {
                progressDialog.setMessage(getContext().getString(R.string.kg_ucm_unlocking));
            }
            this.mUnlockProgressDialog.setIndeterminate(true);
            this.mUnlockProgressDialog.setCancelable(false);
            Window window = this.mUnlockProgressDialog.getWindow();
            Objects.requireNonNull(window);
            window.setType(2009);
        }
        return this.mUnlockProgressDialog;
    }

    public final void getVendorID() {
        Log.d("KeyguardUCMPinView", "getVendorID() called");
        IUcmService uCMService = getUCMService();
        if (uCMService == null) {
            Log.d("KeyguardUCMPinView", "failed to get UCM service");
            return;
        }
        try {
            Bundle agentInfo = uCMService.getAgentInfo(getCSUri());
            if (agentInfo == null) {
                Log.d("KeyguardUCMPinView", "failed to get agentInfo");
                return;
            }
            this.mAgentID = agentInfo.getString("id", "");
            this.mPukSupported = agentInfo.getBoolean(UniversalCredentialUtil.AGENT_IS_PUK_SUPPORTED, false);
            this.mIsSupportBiometricForUCM = agentInfo.getBoolean(UniversalCredentialUtil.AGENT_IS_SUPPORT_BIOMETRIC_FOR_UCM, false);
            Log.d("KeyguardUCMPinView", "mAgentID : " + this.mAgentID + ", mPukSupported : " + this.mPukSupported);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void onPasswordChecked(int i, int i2, boolean z, boolean z2) {
        StringBuilder m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("onPasswordChecked ", i2, " / ", z, " / ");
        m.append(z2);
        m.append(" / ");
        m.append(i);
        Log.e("KeyguardUCMPinView", m.toString());
        if (!z) {
            setMessageSecurityMessageDisplay(getContext().getString(R.string.kg_ucm_password_not_matching));
        } else {
            getKeyguardSecurityCallback().reportUnlockAttempt(i, 0, true);
            getKeyguardSecurityCallback().dismiss(true, i, this.mSecurityMode);
        }
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public final void onPause() {
        super.onPause();
        Log.d("KeyguardUCMPinView", "onPause called");
        this.mUnlockOngoing = false;
        ProgressDialog progressDialog = this.mUnlockProgressDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.mUnlockProgressDialog = null;
        }
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        ProgressDialog progressDialog = this.mUnlockProgressDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.mUnlockProgressDialog = null;
        }
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public final void resetState() {
        super.resetState();
        Log.d("KeyguardUCMPinView", "resetState called");
        getVendorID();
        String str = this.mAgentID;
        if (str != null) {
            this.mUCMAgent.setText(str);
            this.mUCMAgent.setVisibility(0);
        } else {
            this.mUCMAgent.setVisibility(8);
        }
        Log.d("KeyguardUCMPinView", "getStatusAndShowingDialog");
        synchronized (syncObj) {
            try {
                if (this.mGetStatusThread == null) {
                    UCMAsyncTask uCMAsyncTask = new UCMAsyncTask();
                    this.mGetStatusThread = uCMAsyncTask;
                    uCMAsyncTask.execute("");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setMessageSecurityMessageDisplay(CharSequence charSequence) {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController == null) {
            Log.d("KeyguardUCMPinView", "setMessageSecurityMessageDisplay mMessageAreaController is null");
        } else {
            keyguardSecMessageAreaController.setVisibility(0);
            keyguardSecMessageAreaController.setMessage$1(charSequence, false);
        }
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final void verifyPasswordAndUnlock() {
        int i;
        Log.d("KeyguardUCMPinView", "verifyPasswordAndUnlock override called");
        String passwordTextByString = getPasswordTextByString();
        if (passwordTextByString == null || passwordTextByString.length() <= 0) {
            return;
        }
        StateMachine stateMachine = this.mStateMachine;
        stateMachine.getClass();
        Log.d("KeyguardUCMPinView", "StateMachine.getState called");
        int i2 = 0;
        switch (stateMachine.mState) {
            case 131:
            case 132:
                Log.d("KeyguardUCMPinView", "verifyPINAndUnlock called");
                ((KeyguardUCMView) this.mView).setKeepScreenOn(true);
                this.mUnlockOngoing = true;
                getUnlockProgressDialog(false).show();
                if (this.mCheckUcmPinThread == null) {
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(passwordTextByString);
                    this.mCheckUcmPinThread = anonymousClass1;
                    anonymousClass1.start();
                    break;
                }
                break;
            case 133:
                if (this.mIsSupportBiometricForUCM) {
                    this.mLockPatternUtils.requireStrongAuth(2, this.mSelectedUserInteractor.getSelectedUserId());
                }
                StateMachine stateMachine2 = this.mStateMachine;
                stateMachine2.getClass();
                Log.d("KeyguardUCMPinView", "StateMachine.verifyPUKAndUpdateUI called");
                if (stateMachine2.mState == 133) {
                    int i3 = stateMachine2.mRound;
                    int i4 = R.string.kg_ucm_puk_enter_pin_hint;
                    KeyguardUCMViewController keyguardUCMViewController = KeyguardUCMViewController.this;
                    if (i3 != 1) {
                        if (i3 == 2) {
                            keyguardUCMViewController.mPinText = keyguardUCMViewController.getPasswordTextByString();
                            stateMachine2.mRound = 3;
                            i = R.string.kg_ucm_enter_confirm_pin_hint;
                        } else if (i3 != 3) {
                            i4 = 0;
                        } else {
                            keyguardUCMViewController.getClass();
                            Log.d("KeyguardUCMPinView", "confirmPin called");
                            String str = keyguardUCMViewController.mPinText;
                            if (str == null || !str.equals(keyguardUCMViewController.getPasswordTextByString())) {
                                stateMachine2.mRound = 2;
                                i2 = R.string.kg_ucm_invalid_confirm_pin_hint;
                            } else {
                                stateMachine2.mRound = 4;
                                keyguardUCMViewController.getClass();
                                Log.d("KeyguardUCMPinView", "verifyPUKAndUnlock called");
                                ((KeyguardUCMView) keyguardUCMViewController.mView).setKeepScreenOn(true);
                                keyguardUCMViewController.mUnlockOngoing = true;
                                keyguardUCMViewController.getUnlockProgressDialog(false).show();
                                if (keyguardUCMViewController.mCheckUcmPukThread == null) {
                                    AnonymousClass2 anonymousClass2 = keyguardUCMViewController.new AnonymousClass2(keyguardUCMViewController.mPukText, keyguardUCMViewController.mPinText);
                                    keyguardUCMViewController.mCheckUcmPukThread = anonymousClass2;
                                    anonymousClass2.start();
                                }
                                i = R.string.kg_ucm_unlocking;
                            }
                        }
                        i4 = 0;
                        i2 = i;
                    } else {
                        keyguardUCMViewController.mPukText = keyguardUCMViewController.getPasswordTextByString();
                        stateMachine2.mRound = 2;
                        i4 = 0;
                        i2 = R.string.kg_ucm_puk_enter_pin_hint;
                    }
                    ((KeyguardUCMView) keyguardUCMViewController.mView).resetPasswordText(true, true);
                    if (i2 != 0) {
                        if (i4 == 0) {
                            keyguardUCMViewController.setMessageSecurityMessageDisplay(keyguardUCMViewController.getContext().getString(i2));
                            break;
                        } else {
                            keyguardUCMViewController.setMessageSecurityMessageDisplay(keyguardUCMViewController.getContext().getString(i2) + "\n" + keyguardUCMViewController.getContext().getString(i4));
                            break;
                        }
                    }
                }
                break;
            default:
                Log.d("KeyguardUCMPinView", "unknown status nothings to do");
                break;
        }
    }
}
