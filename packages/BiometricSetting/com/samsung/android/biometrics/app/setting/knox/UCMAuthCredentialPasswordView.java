package com.samsung.android.biometrics.app.setting.knox;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.ServiceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImeAwareEditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

/* loaded from: classes.dex */
public class UCMAuthCredentialPasswordView extends UCMAuthCredentialView implements TextView.OnEditorActionListener, TextWatcher {
    private static String mAgentTitle;
    private static String mStorageType;
    private static String mVendorID;
    private final InputMethodManager mImm;
    private ImeAwareEditText mPasswordField;
    private ProgressDialog mProgressDialog;
    StateMachine mStateMachine;
    private static final boolean DBG = Debug.semIsProductDev();
    private static String mCsName = null;
    private static String mVendorName = null;
    private static String mCsNameUri = null;

    public class UCMAsyncTask extends AsyncTask<String, Void, Integer> {
        private String csNameUri;
        private int opCode;

        public UCMAsyncTask(String str, int i) {
            this.opCode = -1;
            this.csNameUri = null;
            if (UCMAuthCredentialPasswordView.DBG) {
                Log.d("BSS_UCMAuthCredentialPasswordView", "UCMAsyncTask csNameUri : " + str + " opCode : " + i);
            }
            this.opCode = i;
            this.csNameUri = str;
        }

        private String getRemainingCount(int i) {
            return i <= 1 ? UCMAuthCredentialPasswordView.this.getResources().getString(R.string.keyguard_password_attempt_count_pin_code, Integer.valueOf(i)) : UCMAuthCredentialPasswordView.this.getResources().getString(R.string.keyguard_password_attempts_count_pin_code, Integer.valueOf(i));
        }

        private void updateUI() {
            String str;
            if (UCMAuthCredentialPasswordView.DBG) {
                Log.d("BSS_UCMAuthCredentialPasswordView", "updateUI");
            }
            int errorState = UCMAuthCredentialPasswordView.this.mStateMachine.getErrorState();
            int i = R.string.ucm_enter_puk;
            if (errorState == 0) {
                int state = UCMAuthCredentialPasswordView.this.mStateMachine.getState();
                Resources resources = UCMAuthCredentialPasswordView.this.getResources();
                switch (state) {
                    case 65536:
                        i = R.string.confirm_ucm_your_pin_header;
                        break;
                    case 65537:
                        break;
                    case 65538:
                        i = R.string.ucm_enter_new_pin;
                        break;
                    case 65539:
                        i = R.string.ucm_confirm_pin;
                        break;
                    case 65540:
                        i = R.string.success_puk_string;
                        break;
                    default:
                        i = R.string.ucm_failed_to_connect_smartcard;
                        break;
                }
                String string = resources.getString(i);
                if (state == 65536 || state == 65537) {
                    str = string + "\n" + getRemainingCount(UCMAuthCredentialPasswordView.this.mStateMachine.getAttemptsRemaining());
                } else {
                    str = string;
                }
            } else if (errorState == 65542) {
                str = UCMAuthCredentialPasswordView.this.getResources().getString(R.string.ucm_pins_do_not_match);
            } else if (errorState != 32) {
                if (errorState != 33) {
                    try {
                        str = UCMAuthCredentialPasswordView.this.getUCMService() != null ? UCMAuthCredentialPasswordView.this.getUCMService().getDetailErrorMessage(UCMAuthCredentialPasswordView.mCsNameUri, errorState) : "";
                        if (str == null) {
                            str = UCMUtils.getErrorMessage(UCMAuthCredentialPasswordView.this.getContext(), errorState);
                        }
                    } catch (Exception unused) {
                        str = UCMUtils.getErrorMessage(UCMAuthCredentialPasswordView.this.getContext(), errorState);
                    }
                } else {
                    str = UCMAuthCredentialPasswordView.this.getResources().getString(R.string.ucm_incorrect_puk) + "\n" + getRemainingCount(UCMAuthCredentialPasswordView.this.mStateMachine.getAttemptsRemaining());
                }
            } else if (UCMAuthCredentialPasswordView.this.mStateMachine.getState() == 65537) {
                str = UCMAuthCredentialPasswordView.this.getResources().getString(R.string.ucm_enter_puk) + "\n" + getRemainingCount(UCMAuthCredentialPasswordView.this.mStateMachine.getAttemptsRemaining());
            } else {
                str = UCMAuthCredentialPasswordView.this.getResources().getString(R.string.cryptkeeper_wrong_pin) + "\n" + getRemainingCount(UCMAuthCredentialPasswordView.this.mStateMachine.getAttemptsRemaining());
            }
            TextView textView = UCMAuthCredentialPasswordView.this.mDescriptionView;
            if (textView != null) {
                textView.setText(str);
            }
            UCMAuthCredentialPasswordView.this.resetState();
            UCMAuthCredentialPasswordView.this.mStateMachine.setErrorState(0);
        }

        @Override // android.os.AsyncTask
        protected final Integer doInBackground(String[] strArr) {
            String str;
            String str2;
            String str3;
            String[] strArr2 = strArr;
            if (UCMAuthCredentialPasswordView.DBG) {
                Log.d("BSS_UCMAuthCredentialPasswordView", "UCMAsyncTask doInBackground : ");
            }
            String str4 = this.csNameUri;
            if (str4 == null || str4.isEmpty()) {
                return null;
            }
            int i = this.opCode;
            if (i == 0) {
                if (strArr2 != null && (str = strArr2[0]) != null) {
                    if (UCMAuthCredentialPasswordView.DBG) {
                        Log.d("BSS_UCMAuthCredentialPasswordView", "verifyPin");
                    }
                    int[] verfiyODEPin = UCMUtils.verfiyODEPin(UCMAuthCredentialPasswordView.this.mPromptConfig.getUserId(), str, this.csNameUri);
                    UCMAuthCredentialPasswordView.this.mStateMachine.setAttemptsRemaining(verfiyODEPin[1]);
                    UCMAuthCredentialPasswordView.this.mStateMachine.setErrorState(verfiyODEPin[2]);
                    switch (verfiyODEPin[0]) {
                        case 131:
                            if (UCMAuthCredentialPasswordView.DBG) {
                                Log.d("BSS_UCMAuthCredentialPasswordView", "verifyPin : STATE_UNLOCKED");
                            }
                            UCMAuthCredentialPasswordView.this.mStateMachine.setState(65540);
                            break;
                        case 132:
                            if (UCMAuthCredentialPasswordView.DBG) {
                                Log.d("BSS_UCMAuthCredentialPasswordView", "verifyPin : STATE_LOCKED");
                                break;
                            }
                            break;
                        case 133:
                            if (UCMAuthCredentialPasswordView.DBG) {
                                Log.d("BSS_UCMAuthCredentialPasswordView", "verifyPin : STATE_BLOCKED");
                            }
                            if (UCMUtils.isSupportBiometricForUCM()) {
                                UCMAuthCredentialPasswordView uCMAuthCredentialPasswordView = UCMAuthCredentialPasswordView.this;
                                uCMAuthCredentialPasswordView.mLockPatternUtils.requireStrongAuth(8192, uCMAuthCredentialPasswordView.mPromptConfig.getUserId());
                            }
                            UCMAuthCredentialPasswordView.this.mStateMachine.setState(65537);
                            break;
                    }
                }
            } else if (i == 1) {
                if (UCMAuthCredentialPasswordView.DBG) {
                    Log.d("BSS_UCMAuthCredentialPasswordView", "getUCMStatus");
                }
                int[] status = UCMUtils.getStatus();
                int i2 = status[0];
                int i3 = status[1];
                int i4 = status[2];
                UCMAuthCredentialPasswordView uCMAuthCredentialPasswordView2 = UCMAuthCredentialPasswordView.this;
                int i5 = status[3];
                uCMAuthCredentialPasswordView2.getClass();
                UCMAuthCredentialPasswordView uCMAuthCredentialPasswordView3 = UCMAuthCredentialPasswordView.this;
                int i6 = status[4];
                uCMAuthCredentialPasswordView3.getClass();
                UCMAuthCredentialPasswordView uCMAuthCredentialPasswordView4 = UCMAuthCredentialPasswordView.this;
                int i7 = status[5];
                uCMAuthCredentialPasswordView4.getClass();
                UCMAuthCredentialPasswordView uCMAuthCredentialPasswordView5 = UCMAuthCredentialPasswordView.this;
                int i8 = status[6];
                uCMAuthCredentialPasswordView5.getClass();
                UCMAuthCredentialPasswordView.this.mStateMachine.setState(i2, i3);
                if (i2 != 131 && i2 != 132 && i2 != 133) {
                    UCMAuthCredentialPasswordView.this.mStateMachine.setErrorState(i4);
                }
            } else if (i == 2 && strArr2 != null && (str2 = strArr2[0]) != null && (str3 = strArr2[1]) != null) {
                if (UCMAuthCredentialPasswordView.DBG) {
                    Log.d("BSS_UCMAuthCredentialPasswordView", "verifyPuk");
                }
                int[] verifyPUK = UCMUtils.verifyPUK(this.csNameUri, str2, str3);
                UCMAuthCredentialPasswordView.this.mStateMachine.setAttemptsRemaining(verifyPUK[1]);
                UCMAuthCredentialPasswordView.this.mStateMachine.setErrorState(verifyPUK[2]);
                if (verifyPUK[0] == 0) {
                    UCMAuthCredentialPasswordView.this.mStateMachine.setState(65540);
                    UCMAuthCredentialPasswordView.this.mStateMachine.setErrorState(0);
                } else {
                    UCMAuthCredentialPasswordView.this.mStateMachine.setState(65537);
                    UCMAuthCredentialPasswordView.this.mStateMachine.setErrorState(33);
                }
            }
            if (!UCMAuthCredentialPasswordView.this.mPromptConfig.isManagedProfile() && UCMAuthCredentialPasswordView.this.mStateMachine.getState() == 65537) {
                return null;
            }
            UCMAuthCredentialPasswordView.m227$$Nest$mcheckPasswordAndUnlock(UCMAuthCredentialPasswordView.this);
            return null;
        }

        @Override // android.os.AsyncTask
        protected final void onPostExecute(Integer num) {
            try {
                if (UCMAuthCredentialPasswordView.this.mPromptConfig.isManagedProfile() || UCMAuthCredentialPasswordView.this.mStateMachine.getState() != 65537) {
                    updateUI();
                    if (this.opCode != 99) {
                        UCMAuthCredentialPasswordView.m231$$Nest$mstopProgress(UCMAuthCredentialPasswordView.this);
                    }
                } else {
                    UCMAuthCredentialPasswordView.m231$$Nest$mstopProgress(UCMAuthCredentialPasswordView.this);
                    UCMAuthCredentialPasswordView.this.mPromptConfig.getCallback().onDismissed(5, null);
                }
            } catch (Exception e) {
                if (UCMAuthCredentialPasswordView.DBG) {
                    Log.e("BSS_UCMAuthCredentialPasswordView", "Exception " + e);
                }
                if (UCMAuthCredentialPasswordView.DBG) {
                    e.printStackTrace();
                }
            }
        }

        @Override // android.os.AsyncTask
        protected final void onPreExecute() {
            if (this.opCode != 99) {
                UCMAuthCredentialPasswordView.m230$$Nest$mstartProgress(UCMAuthCredentialPasswordView.this);
            }
        }
    }

    /* renamed from: $r8$lambda$gSnVhEqq-lQSGu_bwUgQjuDCiIs, reason: not valid java name */
    public static /* synthetic */ void m226$r8$lambda$gSnVhEqqlQSGu_bwUgQjuDCiIs(UCMAuthCredentialPasswordView uCMAuthCredentialPasswordView) {
        uCMAuthCredentialPasswordView.mPasswordField.requestFocus();
        uCMAuthCredentialPasswordView.mImm.showSoftInput(uCMAuthCredentialPasswordView.mPasswordField, 1);
    }

    /* renamed from: -$$Nest$mcheckPasswordAndUnlock, reason: not valid java name */
    static void m227$$Nest$mcheckPasswordAndUnlock(final UCMAuthCredentialPasswordView uCMAuthCredentialPasswordView) {
        if (uCMAuthCredentialPasswordView.mStateMachine.getState() == 65540) {
            LockscreenCredential generatePassword = UCMUtils.generatePassword(uCMAuthCredentialPasswordView.mPromptConfig.getUserId(), mCsNameUri);
            try {
                uCMAuthCredentialPasswordView.mPendingLockCheck = LockPatternChecker.verifyCredential(uCMAuthCredentialPasswordView.mLockPatternUtils, generatePassword, uCMAuthCredentialPasswordView.mPromptConfig.getUserId(), 1, new LockPatternChecker.OnVerifyCallback() { // from class: com.samsung.android.biometrics.app.setting.knox.UCMAuthCredentialPasswordView$$ExternalSyntheticLambda1
                    public final void onVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
                        UCMAuthCredentialPasswordView.this.onCredentialVerified(verifyCredentialResponse);
                    }
                });
                if (generatePassword != null) {
                    generatePassword.close();
                }
            } catch (Throwable th) {
                if (generatePassword != null) {
                    try {
                        generatePassword.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mstartProgress, reason: not valid java name */
    static void m230$$Nest$mstartProgress(UCMAuthCredentialPasswordView uCMAuthCredentialPasswordView) {
        if (DBG) {
            uCMAuthCredentialPasswordView.getClass();
            Log.d("BSS_UCMAuthCredentialPasswordView", "startProgress");
        }
        if (uCMAuthCredentialPasswordView.mProgressDialog == null) {
            ProgressDialog progressDialog = new ProgressDialog(((LinearLayout) uCMAuthCredentialPasswordView).mContext.getApplicationContext());
            uCMAuthCredentialPasswordView.mProgressDialog = progressDialog;
            progressDialog.setMessage("ready");
            uCMAuthCredentialPasswordView.mProgressDialog.setIndeterminate(true);
        }
        if (uCMAuthCredentialPasswordView.mProgressDialog != null) {
            try {
                Toast.makeText(((LinearLayout) uCMAuthCredentialPasswordView).mContext, "start dialog", 0).show();
                uCMAuthCredentialPasswordView.mProgressDialog.show();
            } catch (Exception e) {
                Log.e("BSS_UCMAuthCredentialPasswordView", "Exception " + e);
                e.printStackTrace();
            }
        }
    }

    /* renamed from: -$$Nest$mstopProgress, reason: not valid java name */
    static void m231$$Nest$mstopProgress(UCMAuthCredentialPasswordView uCMAuthCredentialPasswordView) {
        if (DBG) {
            uCMAuthCredentialPasswordView.getClass();
            Log.d("BSS_UCMAuthCredentialPasswordView", "stopProgress");
        }
        ProgressDialog progressDialog = uCMAuthCredentialPasswordView.mProgressDialog;
        if (progressDialog != null) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public UCMAuthCredentialPasswordView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStateMachine = new StateMachine();
        this.mImm = (InputMethodManager) ((LinearLayout) this).mContext.getSystemService(InputMethodManager.class);
        mVendorName = UCMUtils.getUCMKeyguardVendorName();
        String uCMKeyguardStorageForUser = UCMUtils.getUCMKeyguardStorageForUser();
        mCsName = uCMKeyguardStorageForUser;
        mCsNameUri = UniversalCredentialUtil.getUri(uCMKeyguardStorageForUser, "");
        boolean z = DBG;
        if (z) {
            Log.d("BSS_UCMAuthCredentialPasswordView", "mCsName: " + mCsName);
        }
        if (z) {
            Log.d("BSS_UCMAuthCredentialPasswordView", "mCsNameUri: " + mCsNameUri);
        }
        if (z) {
            Log.d("BSS_UCMAuthCredentialPasswordView", "mVendorName: " + mVendorName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IUcmService getUCMService() {
        IUcmService asInterface = IUcmService.Stub.asInterface(ServiceManager.getService("com.samsung.ucs.ucsservice"));
        if (asInterface == null) {
            Log.d("BSS_UCMAuthCredentialPasswordView", "Failed to get UCM service");
        }
        return asInterface;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0068 A[Catch: Exception -> 0x00b4, TryCatch #0 {Exception -> 0x00b4, blocks: (B:10:0x001a, B:13:0x0023, B:15:0x002d, B:18:0x0034, B:20:0x0038, B:21:0x0053, B:23:0x005d, B:26:0x0064, B:28:0x0068, B:29:0x0083, B:31:0x008d, B:34:0x0094, B:36:0x0098, B:39:0x00aa, B:41:0x00ae, B:44:0x007a, B:46:0x007e, B:47:0x004a, B:49:0x004e), top: B:9:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008d A[Catch: Exception -> 0x00b4, TryCatch #0 {Exception -> 0x00b4, blocks: (B:10:0x001a, B:13:0x0023, B:15:0x002d, B:18:0x0034, B:20:0x0038, B:21:0x0053, B:23:0x005d, B:26:0x0064, B:28:0x0068, B:29:0x0083, B:31:0x008d, B:34:0x0094, B:36:0x0098, B:39:0x00aa, B:41:0x00ae, B:44:0x007a, B:46:0x007e, B:47:0x004a, B:49:0x004e), top: B:9:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0098 A[Catch: Exception -> 0x00b4, TryCatch #0 {Exception -> 0x00b4, blocks: (B:10:0x001a, B:13:0x0023, B:15:0x002d, B:18:0x0034, B:20:0x0038, B:21:0x0053, B:23:0x005d, B:26:0x0064, B:28:0x0068, B:29:0x0083, B:31:0x008d, B:34:0x0094, B:36:0x0098, B:39:0x00aa, B:41:0x00ae, B:44:0x007a, B:46:0x007e, B:47:0x004a, B:49:0x004e), top: B:9:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00ae A[Catch: Exception -> 0x00b4, TRY_LEAVE, TryCatch #0 {Exception -> 0x00b4, blocks: (B:10:0x001a, B:13:0x0023, B:15:0x002d, B:18:0x0034, B:20:0x0038, B:21:0x0053, B:23:0x005d, B:26:0x0064, B:28:0x0068, B:29:0x0083, B:31:0x008d, B:34:0x0094, B:36:0x0098, B:39:0x00aa, B:41:0x00ae, B:44:0x007a, B:46:0x007e, B:47:0x004a, B:49:0x004e), top: B:9:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x007e A[Catch: Exception -> 0x00b4, TryCatch #0 {Exception -> 0x00b4, blocks: (B:10:0x001a, B:13:0x0023, B:15:0x002d, B:18:0x0034, B:20:0x0038, B:21:0x0053, B:23:0x005d, B:26:0x0064, B:28:0x0068, B:29:0x0083, B:31:0x008d, B:34:0x0094, B:36:0x0098, B:39:0x00aa, B:41:0x00ae, B:44:0x007a, B:46:0x007e, B:47:0x004a, B:49:0x004e), top: B:9:0x001a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void getVendorID() {
        String string;
        String string2;
        IUcmService uCMService = getUCMService();
        if (uCMService == null) {
            if (DBG) {
                Log.d("BSS_UCMAuthCredentialPasswordView", "failed to get UCM service");
                return;
            }
            return;
        }
        try {
            Bundle agentInfo = uCMService.getAgentInfo(mCsNameUri);
            if (agentInfo == null) {
                return;
            }
            String string3 = agentInfo.getString("vendorId", "");
            mVendorID = string3;
            if (string3 != null && !string3.isEmpty()) {
                if (DBG) {
                    Log.d("BSS_UCMAuthCredentialPasswordView", "vendorID : " + mVendorID);
                }
                string = agentInfo.getString("storageType", "");
                mStorageType = string;
                if (string != null && !string.isEmpty()) {
                    if (DBG) {
                        Log.d("BSS_UCMAuthCredentialPasswordView", "storageType : " + mStorageType);
                    }
                    string2 = agentInfo.getString("title", "");
                    mAgentTitle = string2;
                    if (string2 != null && !string2.isEmpty()) {
                        if (DBG) {
                            return;
                        }
                        Log.d("BSS_UCMAuthCredentialPasswordView", "agentTitle : " + mAgentTitle);
                        return;
                    }
                    if (DBG) {
                        return;
                    }
                    Log.d("BSS_UCMAuthCredentialPasswordView", "NO agentTitle info");
                    return;
                }
                if (DBG) {
                    Log.d("BSS_UCMAuthCredentialPasswordView", "NO storageType info");
                }
                string2 = agentInfo.getString("title", "");
                mAgentTitle = string2;
                if (string2 != null) {
                    if (DBG) {
                    }
                }
                if (DBG) {
                }
            }
            if (DBG) {
                Log.d("BSS_UCMAuthCredentialPasswordView", "NO vendorID info");
            }
            string = agentInfo.getString("storageType", "");
            mStorageType = string;
            if (string != null) {
                if (DBG) {
                }
                string2 = agentInfo.getString("title", "");
                mAgentTitle = string2;
                if (string2 != null) {
                }
                if (DBG) {
                }
            }
            if (DBG) {
            }
            string2 = agentInfo.getString("title", "");
            mAgentTitle = string2;
            if (string2 != null) {
            }
            if (DBG) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetState() {
        if (this.mStateMachine.getErrorState() != 65542) {
            if (DBG) {
                Log.d("BSS_UCMAuthCredentialPasswordView", "resetState set the passwordentry as null");
            }
            this.mPasswordField.setText((CharSequence) null);
        }
        this.mPasswordField.setEnabled(true);
        this.mPasswordField.setFocusable(true);
        this.mPasswordField.setFocusableInTouchMode(true);
        this.mPasswordField.requestFocus();
        postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.knox.UCMAuthCredentialPasswordView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                UCMAuthCredentialPasswordView.m226$r8$lambda$gSnVhEqqlQSGu_bwUgQjuDCiIs(UCMAuthCredentialPasswordView.this);
            }
        }, 100L);
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.UCMAuthCredentialView, android.view.ViewGroup, android.view.View
    protected final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mErrorView.setText(R.string.biometric_prompt_unlock_pin);
        getVendorID();
        ImeAwareEditText findViewById = findViewById(R.id.lockPassword);
        this.mPasswordField = findViewById;
        findViewById.setInputType(18);
        this.mPasswordField.setOnEditorActionListener(this);
        this.mPasswordField.setOnKeyListener(this.mOnKeyListener);
        this.mPasswordField.addTextChangedListener(this);
        this.mPasswordField.requestFocus();
        ImeAwareEditText imeAwareEditText = this.mPasswordField;
        imeAwareEditText.setSelection(imeAwareEditText.getText().length());
        this.mPasswordField.scheduleShowSoftInput();
        new UCMAsyncTask(mCsNameUri, 1).execute("");
    }

    protected final void onCredentialVerified(VerifyCredentialResponse verifyCredentialResponse) {
        if (verifyCredentialResponse.isMatched()) {
            this.mLockPatternUtils.reportSuccessfulPasswordAttempt(this.mPromptConfig.getUserId());
            this.mLockPatternUtils.userPresent(this.mPromptConfig.getUserId());
            long gatekeeperPasswordHandle = verifyCredentialResponse.getGatekeeperPasswordHandle();
            this.mPromptConfig.getCallback().onDismissed(7, this.mLockPatternUtils.verifyGatekeeperPasswordHandle(gatekeeperPasswordHandle, this.mPromptConfig.getOperationId(), this.mPromptConfig.getUserId()).getGatekeeperHAT());
            this.mLockPatternUtils.removeGatekeeperPasswordHandle(gatekeeperPasswordHandle);
        } else {
            this.mLockPatternUtils.reportFailedPasswordAttempt(this.mPromptConfig.getUserId());
        }
        if (verifyCredentialResponse.isMatched()) {
            this.mImm.hideSoftInputFromWindow(getWindowToken(), 0);
        } else {
            this.mPasswordField.setText("");
        }
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean z = keyEvent == null && (i == 0 || i == 6 || i == 5);
        boolean z2 = DBG;
        if (z2) {
            Log.d("BSS_UCMAuthCredentialPasswordView", "onEditorAction isSoftImeEvent: " + z + ", actionId: " + i);
        }
        boolean z3 = keyEvent != null && KeyEvent.isConfirmKey(keyEvent.getKeyCode()) && keyEvent.getAction() == 0;
        if (z2) {
            Log.d("BSS_UCMAuthCredentialPasswordView", "onEditorAction isKeyboardEnterKey: " + z3);
        }
        if (!z && !z3) {
            return false;
        }
        if (z2) {
            Log.d("BSS_UCMAuthCredentialPasswordView", "checkPin");
        }
        AsyncTask<?, ?, ?> asyncTask = this.mPendingLockCheck;
        if (asyncTask != null) {
            asyncTask.cancel(false);
        }
        String obj = this.mPasswordField.getText().toString();
        if (z2) {
            Log.d("BSS_UCMAuthCredentialPasswordView", "checkPin pin: " + obj);
        }
        if (obj == null || obj.length() <= 0) {
            resetState();
        } else {
            this.mStateMachine.next(obj);
        }
        return true;
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.UCMAuthCredentialView, android.view.View
    protected final void onFinishInflate() {
        super.onFinishInflate();
        ImeAwareEditText imeAwareEditText = this.mPasswordField;
        if (imeAwareEditText != null) {
            imeAwareEditText.setEnabled(true);
            this.mPasswordField.requestFocus();
            this.mPasswordField.scheduleShowSoftInput();
        }
    }

    public class StateMachine {
        private int mAtmRemain;
        private int mErrorState;
        String mInputPin;
        String mInputPuk;
        private int mState;

        public StateMachine() {
        }

        private static String printState(int i) {
            switch (i) {
                case 65536:
                    return "ENTER_PIN";
                case 65537:
                    return "ENTER_PUK";
                case 65538:
                    return "ENTER_PIN_PUK";
                case 65539:
                    return "CONFIRM_PIN";
                case 65540:
                    return "DONE";
                case 65541:
                    return "FAIL";
                case 65542:
                    return "ERROR_CONFIRM_PIN_FAIL";
                default:
                    return "";
            }
        }

        public final int getAttemptsRemaining() {
            return this.mAtmRemain;
        }

        public final int getErrorState() {
            return this.mErrorState;
        }

        public final int getState() {
            if (UCMAuthCredentialPasswordView.DBG) {
                Log.d("BSS_UCMAuthCredentialPasswordView", "getState : ".concat(printState(this.mState)));
            }
            return this.mState;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public final void next(String str) {
            String[] strArr;
            int i;
            int state = getState();
            if (UCMAuthCredentialPasswordView.DBG) {
                Log.d("BSS_UCMAuthCredentialPasswordView", "next mCurState : ".concat(printState(state)));
            }
            switch (state) {
                case 65536:
                    strArr = new String[]{str};
                    i = 0;
                    break;
                case 65537:
                    this.mInputPuk = str;
                    this.mState = 65538;
                    strArr = null;
                    i = 99;
                    break;
                case 65538:
                    this.mInputPin = str;
                    this.mState = 65539;
                    strArr = null;
                    i = 99;
                    break;
                case 65539:
                    if (!str.equals(this.mInputPin)) {
                        this.mState = 65538;
                        this.mErrorState = 65542;
                        strArr = null;
                        i = 99;
                        break;
                    } else {
                        strArr = new String[]{this.mInputPuk, this.mInputPin};
                        i = 2;
                        break;
                    }
                default:
                    strArr = null;
                    i = 99;
                    break;
            }
            UCMAuthCredentialPasswordView.this.new UCMAsyncTask(UCMAuthCredentialPasswordView.mCsNameUri, i).execute(strArr);
        }

        public final void setAttemptsRemaining(int i) {
            this.mAtmRemain = i;
        }

        public final void setErrorState(int i) {
            this.mErrorState = i;
        }

        public final void setState(int i, int i2) {
            if (UCMAuthCredentialPasswordView.DBG) {
                Log.d("BSS_UCMAuthCredentialPasswordView", "state : ".concat(printState(i)));
            }
            if (UCMAuthCredentialPasswordView.DBG) {
                Log.d("BSS_UCMAuthCredentialPasswordView", "remainCnt : " + i2);
            }
            this.mAtmRemain = i2;
            this.mErrorState = 0;
            switch (i) {
                case 131:
                case 132:
                    this.mState = 65536;
                    break;
                case 133:
                    this.mState = 65537;
                    break;
                default:
                    this.mState = 65541;
                    break;
            }
        }

        public final void setState(int i) {
            if (UCMAuthCredentialPasswordView.DBG) {
                Log.d("BSS_UCMAuthCredentialPasswordView", "setState : " + i);
            }
            this.mState = i;
        }
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
