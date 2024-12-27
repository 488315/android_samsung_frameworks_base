package com.samsung.android.biometrics.app.setting.prompt.knox;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
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

import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public class UCMAuthCredentialPasswordView extends UCMAuthCredentialView
        implements TextView.OnEditorActionListener, TextWatcher {
    public static String mAgentTitle;
    public static String mStorageType;
    public static String mVendorID;
    public final InputMethodManager mImm;
    public ImeAwareEditText mPasswordField;
    public ProgressDialog mProgressDialog;
    public final StateMachine mStateMachine;
    public static final boolean DBG = Debug.semIsProductDev();
    public static String mCsName = null;
    public static String mVendorName = null;
    public static String mCsNameUri = null;

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    public final class StateMachine {
        public int mAtmRemain;
        public int mErrorState;
        public String mInputPin;
        public String mInputPuk;
        public int mState;

        public StateMachine() {}

        public static String printState(int i) {
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

        public final int getState() {
            if (UCMAuthCredentialPasswordView.DBG) {
                Log.d(
                        "BSS_UCMAuthCredentialPasswordView",
                        "getState : ".concat(printState(this.mState)));
            }
            return this.mState;
        }

        public final void setState(int i) {
            if (UCMAuthCredentialPasswordView.DBG) {
                Log.d("BSS_UCMAuthCredentialPasswordView", "setState : " + i);
            }
            this.mState = i;
        }
    }

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    public final class UCMAsyncTask extends AsyncTask {
        public final String csNameUri;
        public final int opCode;

        public UCMAsyncTask(String str, int i) {
            this.opCode = -1;
            this.csNameUri = null;
            if (UCMAuthCredentialPasswordView.DBG) {
                Log.d(
                        "BSS_UCMAuthCredentialPasswordView",
                        "UCMAsyncTask csNameUri : " + str + " opCode : " + i);
            }
            this.opCode = i;
            this.csNameUri = str;
        }

        /* JADX WARN: Code restructure failed: missing block: B:32:0x008e, code lost:

           if (r0.trim().length() > 0) goto L41;
        */
        /* JADX WARN: Removed duplicated region for block: B:103:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:54:0x00e7  */
        /* JADX WARN: Removed duplicated region for block: B:55:0x00f5  */
        /* JADX WARN: Removed duplicated region for block: B:64:0x0288  */
        /* JADX WARN: Removed duplicated region for block: B:70:0x02a0  */
        /* JADX WARN: Removed duplicated region for block: B:79:0x02e7  */
        /* JADX WARN: Removed duplicated region for block: B:81:? A[RETURN, SYNTHETIC] */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object doInBackground(java.lang.Object[] r18) {
            /*
                Method dump skipped, instructions count: 838
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.UCMAsyncTask.doInBackground(java.lang.Object[]):java.lang.Object");
        }

        public final String getRemainingCount(int i) {
            return i <= 1
                    ? UCMAuthCredentialPasswordView.this
                            .getResources()
                            .getString(
                                    R.string.keyguard_password_attempt_count_pin_code,
                                    Integer.valueOf(i))
                    : UCMAuthCredentialPasswordView.this
                            .getResources()
                            .getString(
                                    R.string.keyguard_password_attempts_count_pin_code,
                                    Integer.valueOf(i));
        }

        /* JADX WARN: Removed duplicated region for block: B:41:0x0067  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x007a  */
        /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
        @Override // android.os.AsyncTask
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onPostExecute(java.lang.Object r4) {
            /*
                r3 = this;
                java.lang.String r0 = "BSS_UCMAuthCredentialPasswordView"
                java.lang.Integer r4 = (java.lang.Integer) r4
                com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView r4 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.this     // Catch: java.lang.Exception -> L3f
                com.samsung.android.biometrics.app.setting.prompt.PromptConfig r1 = r4.mPromptConfig     // Catch: java.lang.Exception -> L3f
                boolean r1 = r1.mIsManagedProfile     // Catch: java.lang.Exception -> L3f
                java.lang.String r2 = "stopProgress"
                if (r1 != 0) goto L41
                com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView$StateMachine r4 = r4.mStateMachine     // Catch: java.lang.Exception -> L3f
                int r4 = r4.getState()     // Catch: java.lang.Exception -> L3f
                r1 = 65537(0x10001, float:9.1837E-41)
                if (r4 != r1) goto L41
                com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView r4 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.this     // Catch: java.lang.Exception -> L3f
                boolean r1 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.DBG     // Catch: java.lang.Exception -> L3f
                if (r1 == 0) goto L25
                r4.getClass()     // Catch: java.lang.Exception -> L3f
                android.util.Log.d(r0, r2)     // Catch: java.lang.Exception -> L3f
            L25:
                android.app.ProgressDialog r4 = r4.mProgressDialog     // Catch: java.lang.Exception -> L3f
                if (r4 == 0) goto L31
                r4.dismiss()     // Catch: java.lang.Exception -> L2d
                goto L31
            L2d:
                r4 = move-exception
                r4.printStackTrace()     // Catch: java.lang.Exception -> L3f
            L31:
                com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView r3 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.this     // Catch: java.lang.Exception -> L3f
                com.samsung.android.biometrics.app.setting.prompt.PromptConfig r3 = r3.mPromptConfig     // Catch: java.lang.Exception -> L3f
                com.samsung.android.biometrics.app.setting.prompt.BiometricPromptCallback r3 = r3.mCallback     // Catch: java.lang.Exception -> L3f
                com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient r3 = (com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient) r3     // Catch: java.lang.Exception -> L3f
                r4 = 5
                r1 = 0
                r3.onDismissed(r4, r1)     // Catch: java.lang.Exception -> L3f
                goto L7d
            L3f:
                r3 = move-exception
                goto L63
            L41:
                r3.updateUI()     // Catch: java.lang.Exception -> L3f
                int r4 = r3.opCode     // Catch: java.lang.Exception -> L3f
                r1 = 99
                if (r4 == r1) goto L7d
                com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView r3 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.this     // Catch: java.lang.Exception -> L3f
                boolean r4 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.DBG     // Catch: java.lang.Exception -> L3f
                if (r4 == 0) goto L56
                r3.getClass()     // Catch: java.lang.Exception -> L3f
                android.util.Log.d(r0, r2)     // Catch: java.lang.Exception -> L3f
            L56:
                android.app.ProgressDialog r3 = r3.mProgressDialog     // Catch: java.lang.Exception -> L3f
                if (r3 == 0) goto L7d
                r3.dismiss()     // Catch: java.lang.Exception -> L5e
                goto L7d
            L5e:
                r3 = move-exception
                r3.printStackTrace()     // Catch: java.lang.Exception -> L3f
                goto L7d
            L63:
                boolean r4 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.DBG
                if (r4 == 0) goto L78
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                java.lang.String r2 = "Exception "
                r1.<init>(r2)
                r1.append(r3)
                java.lang.String r1 = r1.toString()
                android.util.Log.e(r0, r1)
            L78:
                if (r4 == 0) goto L7d
                r3.printStackTrace()
            L7d:
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.UCMAsyncTask.onPostExecute(java.lang.Object):void");
        }

        @Override // android.os.AsyncTask
        public final void onPreExecute() {
            if (this.opCode != 99) {
                UCMAuthCredentialPasswordView.m10$$Nest$mstartProgress(
                        UCMAuthCredentialPasswordView.this);
            }
        }

        public final void updateUI() {
            if (UCMAuthCredentialPasswordView.DBG) {
                Log.d("BSS_UCMAuthCredentialPasswordView", "updateUI");
            }
            UCMAuthCredentialPasswordView uCMAuthCredentialPasswordView =
                    UCMAuthCredentialPasswordView.this;
            StateMachine stateMachine = uCMAuthCredentialPasswordView.mStateMachine;
            int i = stateMachine.mErrorState;
            String str = UCMUtils.mChildSafeMsg;
            if (str == null) {
                str = null;
            }
            int i2 = R.string.ucm_enter_puk;
            if (i == 0) {
                int state = stateMachine.getState();
                Resources resources = UCMAuthCredentialPasswordView.this.getResources();
                switch (state) {
                    case 65536:
                        i2 = R.string.confirm_ucm_your_pin_header;
                        break;
                    case 65537:
                        break;
                    case 65538:
                        i2 = R.string.ucm_enter_new_pin;
                        break;
                    case 65539:
                        i2 = R.string.ucm_confirm_pin;
                        break;
                    case 65540:
                        i2 = R.string.success_puk_string;
                        break;
                    default:
                        i2 = R.string.ucm_failed_to_connect_smartcard;
                        break;
                }
                String string = resources.getString(i2);
                if (state != 65536 && state != 65537) {
                    str = string;
                } else if (str == null) {
                    str =
                            UCMAuthCredentialPasswordView.this
                                            .getResources()
                                            .getString(R.string.cryptkeeper_wrong_pin)
                                    + "\n"
                                    + getRemainingCount(
                                            UCMAuthCredentialPasswordView.this
                                                    .mStateMachine
                                                    .mAtmRemain);
                }
            } else if (i == 65542) {
                str =
                        uCMAuthCredentialPasswordView
                                .getResources()
                                .getString(R.string.ucm_pins_do_not_match);
            } else if (i != 32) {
                if (i != 33) {
                    try {
                        String detailErrorMessage =
                                uCMAuthCredentialPasswordView.getUCMService() != null
                                        ? UCMAuthCredentialPasswordView.this
                                                .getUCMService()
                                                .getDetailErrorMessage(
                                                        UCMAuthCredentialPasswordView.mCsNameUri, i)
                                        : "";
                        if (detailErrorMessage == null) {
                            detailErrorMessage =
                                    UCMUtils.getErrorMessage(
                                            UCMAuthCredentialPasswordView.this.getContext(), i);
                        }
                        str = detailErrorMessage;
                    } catch (Exception unused) {
                        str =
                                UCMUtils.getErrorMessage(
                                        UCMAuthCredentialPasswordView.this.getContext(), i);
                    }
                } else {
                    str =
                            UCMAuthCredentialPasswordView.this
                                            .getResources()
                                            .getString(R.string.ucm_incorrect_puk)
                                    + "\n"
                                    + getRemainingCount(
                                            UCMAuthCredentialPasswordView.this
                                                    .mStateMachine
                                                    .mAtmRemain);
                }
            } else if (stateMachine.getState() == 65537) {
                str =
                        UCMAuthCredentialPasswordView.this
                                        .getResources()
                                        .getString(R.string.ucm_enter_puk)
                                + "\n"
                                + getRemainingCount(
                                        UCMAuthCredentialPasswordView.this
                                                .mStateMachine
                                                .mAtmRemain);
            } else if (str == null) {
                str =
                        UCMAuthCredentialPasswordView.this
                                        .getResources()
                                        .getString(R.string.cryptkeeper_wrong_pin)
                                + "\n"
                                + getRemainingCount(
                                        UCMAuthCredentialPasswordView.this
                                                .mStateMachine
                                                .mAtmRemain);
            }
            TextView textView = UCMAuthCredentialPasswordView.this.mDescriptionView;
            if (textView != null) {
                textView.setText(str);
                UCMAuthCredentialPasswordView.this.mDescriptionView.setVisibility(0);
            }
            UCMAuthCredentialPasswordView.this.resetState();
            UCMAuthCredentialPasswordView.this.mStateMachine.mErrorState = 0;
        }
    }

    /* renamed from: -$$Nest$mstartProgress, reason: not valid java name */
    public static void m10$$Nest$mstartProgress(
            UCMAuthCredentialPasswordView uCMAuthCredentialPasswordView) {
        if (DBG) {
            uCMAuthCredentialPasswordView.getClass();
            Log.d("BSS_UCMAuthCredentialPasswordView", "startProgress");
        }
        if (uCMAuthCredentialPasswordView.mProgressDialog == null) {
            ProgressDialog progressDialog =
                    new ProgressDialog(
                            ((LinearLayout) uCMAuthCredentialPasswordView)
                                    .mContext.getApplicationContext());
            uCMAuthCredentialPasswordView.mProgressDialog = progressDialog;
            progressDialog.setMessage("ready");
            uCMAuthCredentialPasswordView.mProgressDialog.setIndeterminate(true);
        }
        if (uCMAuthCredentialPasswordView.mProgressDialog != null) {
            try {
                Log.d("BSS_UCMAuthCredentialPasswordView", "start dialog");
                uCMAuthCredentialPasswordView.mProgressDialog.show();
            } catch (Exception e) {
                Log.e("BSS_UCMAuthCredentialPasswordView", "Exception " + e);
                e.printStackTrace();
            }
        }
    }

    public UCMAuthCredentialPasswordView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStateMachine = new StateMachine();
        this.mImm =
                (InputMethodManager)
                        ((LinearLayout) this).mContext.getSystemService(InputMethodManager.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IUcmService getUCMService() {
        IUcmService asInterface =
                IUcmService.Stub.asInterface(
                        ServiceManager.getService("com.samsung.ucs.ucsservice"));
        if (asInterface == null) {
            Log.d("BSS_UCMAuthCredentialPasswordView", "Failed to get UCM service");
        }
        return asInterface;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x006b A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:10:0x001a, B:13:0x0023, B:15:0x002d, B:18:0x0034, B:20:0x0038, B:21:0x0056, B:23:0x0060, B:26:0x0067, B:28:0x006b, B:29:0x0086, B:31:0x0090, B:34:0x0097, B:36:0x009b, B:39:0x00ad, B:41:0x00b1, B:44:0x007d, B:46:0x0081, B:47:0x004d, B:49:0x0051), top: B:9:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0090 A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:10:0x001a, B:13:0x0023, B:15:0x002d, B:18:0x0034, B:20:0x0038, B:21:0x0056, B:23:0x0060, B:26:0x0067, B:28:0x006b, B:29:0x0086, B:31:0x0090, B:34:0x0097, B:36:0x009b, B:39:0x00ad, B:41:0x00b1, B:44:0x007d, B:46:0x0081, B:47:0x004d, B:49:0x0051), top: B:9:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x009b A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:10:0x001a, B:13:0x0023, B:15:0x002d, B:18:0x0034, B:20:0x0038, B:21:0x0056, B:23:0x0060, B:26:0x0067, B:28:0x006b, B:29:0x0086, B:31:0x0090, B:34:0x0097, B:36:0x009b, B:39:0x00ad, B:41:0x00b1, B:44:0x007d, B:46:0x0081, B:47:0x004d, B:49:0x0051), top: B:9:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00b1 A[Catch: Exception -> 0x004a, TRY_LEAVE, TryCatch #0 {Exception -> 0x004a, blocks: (B:10:0x001a, B:13:0x0023, B:15:0x002d, B:18:0x0034, B:20:0x0038, B:21:0x0056, B:23:0x0060, B:26:0x0067, B:28:0x006b, B:29:0x0086, B:31:0x0090, B:34:0x0097, B:36:0x009b, B:39:0x00ad, B:41:0x00b1, B:44:0x007d, B:46:0x0081, B:47:0x004d, B:49:0x0051), top: B:9:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0081 A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:10:0x001a, B:13:0x0023, B:15:0x002d, B:18:0x0034, B:20:0x0038, B:21:0x0056, B:23:0x0060, B:26:0x0067, B:28:0x006b, B:29:0x0086, B:31:0x0090, B:34:0x0097, B:36:0x009b, B:39:0x00ad, B:41:0x00b1, B:44:0x007d, B:46:0x0081, B:47:0x004d, B:49:0x0051), top: B:9:0x001a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void getVendorID() {
        /*
            r6 = this;
            java.lang.String r0 = ""
            java.lang.String r1 = "agentTitle : "
            java.lang.String r2 = "storageType : "
            java.lang.String r3 = "vendorID : "
            com.samsung.android.knox.ucm.core.IUcmService r6 = r6.getUCMService()
            java.lang.String r4 = "BSS_UCMAuthCredentialPasswordView"
            if (r6 != 0) goto L1a
            boolean r6 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.DBG
            if (r6 == 0) goto L19
            java.lang.String r6 = "failed to get UCM service"
            android.util.Log.d(r4, r6)
        L19:
            return
        L1a:
            java.lang.String r5 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.mCsNameUri     // Catch: java.lang.Exception -> L4a
            android.os.Bundle r6 = r6.getAgentInfo(r5)     // Catch: java.lang.Exception -> L4a
            if (r6 != 0) goto L23
            return
        L23:
            java.lang.String r5 = "vendorId"
            java.lang.String r5 = r6.getString(r5, r0)     // Catch: java.lang.Exception -> L4a
            com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.mVendorID = r5     // Catch: java.lang.Exception -> L4a
            if (r5 == 0) goto L4d
            boolean r5 = r5.isEmpty()     // Catch: java.lang.Exception -> L4a
            if (r5 == 0) goto L34
            goto L4d
        L34:
            boolean r5 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.DBG     // Catch: java.lang.Exception -> L4a
            if (r5 == 0) goto L56
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L4a
            r5.<init>(r3)     // Catch: java.lang.Exception -> L4a
            java.lang.String r3 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.mVendorID     // Catch: java.lang.Exception -> L4a
            r5.append(r3)     // Catch: java.lang.Exception -> L4a
            java.lang.String r3 = r5.toString()     // Catch: java.lang.Exception -> L4a
            android.util.Log.d(r4, r3)     // Catch: java.lang.Exception -> L4a
            goto L56
        L4a:
            r6 = move-exception
            goto Lb7
        L4d:
            boolean r3 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.DBG     // Catch: java.lang.Exception -> L4a
            if (r3 == 0) goto L56
            java.lang.String r3 = "NO vendorID info"
            android.util.Log.d(r4, r3)     // Catch: java.lang.Exception -> L4a
        L56:
            java.lang.String r3 = "storageType"
            java.lang.String r3 = r6.getString(r3, r0)     // Catch: java.lang.Exception -> L4a
            com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.mStorageType = r3     // Catch: java.lang.Exception -> L4a
            if (r3 == 0) goto L7d
            boolean r3 = r3.isEmpty()     // Catch: java.lang.Exception -> L4a
            if (r3 == 0) goto L67
            goto L7d
        L67:
            boolean r3 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.DBG     // Catch: java.lang.Exception -> L4a
            if (r3 == 0) goto L86
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L4a
            r3.<init>(r2)     // Catch: java.lang.Exception -> L4a
            java.lang.String r2 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.mStorageType     // Catch: java.lang.Exception -> L4a
            r3.append(r2)     // Catch: java.lang.Exception -> L4a
            java.lang.String r2 = r3.toString()     // Catch: java.lang.Exception -> L4a
            android.util.Log.d(r4, r2)     // Catch: java.lang.Exception -> L4a
            goto L86
        L7d:
            boolean r2 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.DBG     // Catch: java.lang.Exception -> L4a
            if (r2 == 0) goto L86
            java.lang.String r2 = "NO storageType info"
            android.util.Log.d(r4, r2)     // Catch: java.lang.Exception -> L4a
        L86:
            java.lang.String r2 = "title"
            java.lang.String r6 = r6.getString(r2, r0)     // Catch: java.lang.Exception -> L4a
            com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.mAgentTitle = r6     // Catch: java.lang.Exception -> L4a
            if (r6 == 0) goto Lad
            boolean r6 = r6.isEmpty()     // Catch: java.lang.Exception -> L4a
            if (r6 == 0) goto L97
            goto Lad
        L97:
            boolean r6 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.DBG     // Catch: java.lang.Exception -> L4a
            if (r6 == 0) goto Lba
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L4a
            r6.<init>(r1)     // Catch: java.lang.Exception -> L4a
            java.lang.String r0 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.mAgentTitle     // Catch: java.lang.Exception -> L4a
            r6.append(r0)     // Catch: java.lang.Exception -> L4a
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Exception -> L4a
            android.util.Log.d(r4, r6)     // Catch: java.lang.Exception -> L4a
            goto Lba
        Lad:
            boolean r6 = com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.DBG     // Catch: java.lang.Exception -> L4a
            if (r6 == 0) goto Lba
            java.lang.String r6 = "NO agentTitle info"
            android.util.Log.d(r4, r6)     // Catch: java.lang.Exception -> L4a
            goto Lba
        Lb7:
            r6.printStackTrace()
        Lba:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView.getVendorID():void");
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialView,
              // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mErrorView.setText(R.string.biometric_prompt_unlock_pin);
        mVendorName = UCMUtils.getUCMKeyguardVendorName(this.mPromptConfig.mUserId);
        String uCMKeyguardStorageForUser =
                UCMUtils.getUCMKeyguardStorageForUser(this.mPromptConfig.mUserId);
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.widget.TextView.OnEditorActionListener
    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        String[] strArr;
        int i2 = 0;
        boolean z = keyEvent == null && (i == 0 || i == 6 || i == 5);
        boolean z2 = DBG;
        if (z2) {
            Log.d(
                    "BSS_UCMAuthCredentialPasswordView",
                    "onEditorAction isSoftImeEvent: " + z + ", actionId: " + i);
        }
        boolean z3 =
                keyEvent != null
                        && KeyEvent.isConfirmKey(keyEvent.getKeyCode())
                        && keyEvent.getAction() == 0;
        if (z2) {
            Log.d("BSS_UCMAuthCredentialPasswordView", "onEditorAction isKeyboardEnterKey: " + z3);
        }
        if (!z && !z3) {
            return false;
        }
        if (z2) {
            Log.d("BSS_UCMAuthCredentialPasswordView", "checkPin");
        }
        AsyncTask asyncTask = this.mPendingLockCheck;
        if (asyncTask != null) {
            asyncTask.cancel(false);
        }
        String editable = this.mPasswordField.getText().toString();
        if (z2) {
            Log.d("BSS_UCMAuthCredentialPasswordView", "checkPin pin: " + editable);
        }
        if (editable == null || editable.length() <= 0) {
            resetState();
        } else {
            StateMachine stateMachine = this.mStateMachine;
            int state = stateMachine.getState();
            if (z2) {
                Log.d(
                        "BSS_UCMAuthCredentialPasswordView",
                        "next mCurState : ".concat(StateMachine.printState(state)));
            }
            switch (state) {
                case 65536:
                    strArr = new String[] {editable};
                    break;
                case 65537:
                    stateMachine.mInputPuk = editable;
                    stateMachine.mState = 65538;
                    strArr = null;
                    i2 = 99;
                    break;
                case 65538:
                    stateMachine.mInputPin = editable;
                    stateMachine.mState = 65539;
                    strArr = null;
                    i2 = 99;
                    break;
                case 65539:
                    if (!editable.equals(stateMachine.mInputPin)) {
                        stateMachine.mState = 65538;
                        stateMachine.mErrorState = 65542;
                        strArr = null;
                        i2 = 99;
                        break;
                    } else {
                        strArr = new String[] {stateMachine.mInputPuk, stateMachine.mInputPin};
                        i2 = 2;
                        break;
                    }
                default:
                    strArr = null;
                    i2 = 99;
                    break;
            }
            UCMAuthCredentialPasswordView.this.new UCMAsyncTask(mCsNameUri, i2).execute(strArr);
        }
        return true;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialView,
              // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        ImeAwareEditText imeAwareEditText = this.mPasswordField;
        if (imeAwareEditText != null) {
            imeAwareEditText.setEnabled(true);
            this.mPasswordField.requestFocus();
            this.mPasswordField.scheduleShowSoftInput();
        }
    }

    public final void resetState() {
        if (this.mStateMachine.mErrorState != 65542) {
            if (DBG) {
                Log.d(
                        "BSS_UCMAuthCredentialPasswordView",
                        "resetState set the passwordentry as null");
            }
            this.mPasswordField.setText((CharSequence) null);
        }
        this.mPasswordField.setEnabled(true);
        this.mPasswordField.setFocusable(true);
        this.mPasswordField.setFocusableInTouchMode(true);
        this.mPasswordField.requestFocus();
        postDelayed(
                new Runnable() { // from class:
                                 // com.samsung.android.biometrics.app.setting.prompt.knox.UCMAuthCredentialPasswordView$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        UCMAuthCredentialPasswordView uCMAuthCredentialPasswordView =
                                UCMAuthCredentialPasswordView.this;
                        uCMAuthCredentialPasswordView.mPasswordField.requestFocus();
                        uCMAuthCredentialPasswordView.mImm.showSoftInput(
                                uCMAuthCredentialPasswordView.mPasswordField, 1);
                    }
                },
                100L);
    }

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {}

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {}

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
}
