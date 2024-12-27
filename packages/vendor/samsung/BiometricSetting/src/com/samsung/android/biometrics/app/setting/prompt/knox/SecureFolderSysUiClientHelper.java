package com.samsung.android.biometrics.app.setting.prompt.knox;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AlertDialog;
import android.app.WindowConfiguration;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.view.menu.SubMenuBuilder$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockPatternView;

import com.samsung.android.biometrics.app.setting.FocusableWindow$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.SemPersonaManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class SecureFolderSysUiClientHelper implements KnoxSysUiClientHelper {
    public static final int[][] DETAIL_TEXTS = {
        new int[] {R.string.draw_unlock_pattern},
        new int[] {R.string.sec_enter_pin_WC},
        new int[] {R.string.sec_enter_password_WC}
    };
    public final LayoutInflater layoutInflater;
    public final AccessibilityManager mAccessibilityManager;
    public AlertDialog mAlertDialog;
    public TextView mBtnForgot;
    public final Context mContext;
    public final DevicePolicyManager mDevicePolicyManager;
    public boolean mIsPasswordShown;
    public final ArrayList mKnoxEventList;
    public final LockPatternUtils mLockPatternUtils;
    public final String mPackageName;
    public final PromptConfig mPromptConfig;
    public TextView mUninstallBtn;

    public SecureFolderSysUiClientHelper(Context context, PromptConfig promptConfig, String str) {
        this.mContext = context;
        this.mPromptConfig = promptConfig;
        this.mPackageName = str;
        this.layoutInflater = LayoutInflater.from(context);
        this.mAccessibilityManager =
                (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        ArrayList arrayList = new ArrayList();
        this.mKnoxEventList = arrayList;
        String concat = getCurrentLockType().concat(" + fingerprint");
        HashMap hashMap = new HashMap();
        hashMap.put("statusID", 100);
        hashMap.put("detail", concat);
        hashMap.put("type", "status");
        arrayList.add(hashMap);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView
            changeCredentialViewIfNeeded(android.view.View r21) {
        /*
            Method dump skipped, instructions count: 892
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.biometrics.app.setting.prompt.knox.SecureFolderSysUiClientHelper.changeCredentialViewIfNeeded(android.view.View):com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView");
    }

    public final String getCurrentLockType() {
        int i = this.mPromptConfig.mCredentialType;
        return i != 1 ? i != 2 ? i != 3 ? "" : "password" : "pattern" : "pin";
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final TextView getDetailsTextView(KnoxAuthCredentialView knoxAuthCredentialView) {
        return (TextView) knoxAuthCredentialView.findViewById(R.id.detailsText);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final String getErrorMessage() {
        TextView textView;
        if (isResetWithSamsungAccountEnable() && (textView = this.mBtnForgot) != null) {
            textView.setVisibility(0);
        }
        int keyguardStoredPasswordQuality =
                this.mLockPatternUtils.getKeyguardStoredPasswordQuality(this.mPromptConfig.mUserId);
        if (keyguardStoredPasswordQuality == 65536) {
            return this.mContext.getResources().getString(R.string.cryptkeeper_wrong_pattern);
        }
        if (keyguardStoredPasswordQuality == 131072 || keyguardStoredPasswordQuality == 196608) {
            return this.mContext.getResources().getString(R.string.cryptkeeper_wrong_pin);
        }
        if (keyguardStoredPasswordQuality == 262144
                || keyguardStoredPasswordQuality == 327680
                || keyguardStoredPasswordQuality == 393216) {
            return this.mContext.getResources().getString(R.string.cryptkeeper_wrong_password);
        }
        return null;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final boolean isForgotbtnDialogShowing() {
        AlertDialog alertDialog = this.mAlertDialog;
        return alertDialog != null && alertDialog.isShowing();
    }

    public final boolean isResetWithSamsungAccountEnable() {
        return Settings.System.getIntForUser(
                        this.mContext.getContentResolver(),
                        "sf_reset_with_samsung_account",
                        1,
                        this.mPromptConfig.mUserId)
                == 1;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final void modifyLayoutParamsIfNeeded(WindowManager.LayoutParams layoutParams) {
        layoutParams.setFitInsetsTypes(
                layoutParams.getFitInsetsTypes() & (~WindowInsets.Type.navigationBars()));
        layoutParams.layoutInDisplayCutoutMode = 1;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final void onAttachedPatternViewToWindow(LockPatternView lockPatternView) {
        lockPatternView.setColors(-1, -1, -65536);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onAttachedToWindow(
            com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView r5,
            android.widget.TextView r6,
            android.widget.TextView r7,
            android.widget.TextView r8,
            android.widget.ImageView r9) {
        /*
            Method dump skipped, instructions count: 287
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.biometrics.app.setting.prompt.knox.SecureFolderSysUiClientHelper.onAttachedToWindow(com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView,"
                    + " android.widget.TextView, android.widget.TextView, android.widget.TextView,"
                    + " android.widget.ImageView):void");
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final void onConfigurationChanged() {
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mAlertDialog.dismiss();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final void onCredentialVerified(int i, boolean z, View view, int i2, TextView textView) {
        PromptConfig promptConfig = this.mPromptConfig;
        if (z) {
            this.mLockPatternUtils.clearBiometricAttemptDeadline(promptConfig.mUserId);
            return;
        }
        if (i2 > 0) {
            int currentFailedPasswordAttempts =
                    this.mDevicePolicyManager.getCurrentFailedPasswordAttempts(
                            promptConfig.mUserId);
            Log.d(
                    "KKG::SecureFolderSysUiClientHelper",
                    "mFailedUnlockAttemptsForSecureFolder " + currentFailedPasswordAttempts);
            if (currentFailedPasswordAttempts >= 15) {
                if (isResetWithSamsungAccountEnable()) {
                    Log.d(
                            "KKG::SecureFolderSysUiClientHelper",
                            "mFailedUnlockAttemptsForSecureFolder ( show Locked View. )");
                    ((BiometricPromptClient) promptConfig.mCallback).onUserCancel(3);
                    showSFLockedView(false, true);
                    return;
                } else {
                    TextView textView2 = this.mUninstallBtn;
                    if (textView2 != null) {
                        textView2.setVisibility(0);
                    }
                }
            }
            if (i != 1) {
                if (i == 2) {
                    if (view instanceof LockPatternView) {
                        ((LockPatternView) view).setVisibility(4);
                        return;
                    }
                    return;
                } else if (i != 3) {
                    throw new IllegalStateException(
                            SubMenuBuilder$$ExternalSyntheticOutline0.m(
                                    i, "Unknown credential type: "));
                }
            }
            if (view instanceof EditText) {
                EditText editText = (EditText) view;
                editText.setInputType(0);
                editText.setClickable(false);
                editText.setCursorVisible(false);
                editText.setFocusableInTouchMode(false);
                ((InputMethodManager) this.mContext.getSystemService(InputMethodManager.class))
                        .hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final void onDetachedFromWindow() {
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mAlertDialog.dismiss();
        }
        Context context = this.mContext;
        ArrayList arrayList = this.mKnoxEventList;
        int i = this.mPromptConfig.mUserId;
        if (SemPersonaManager.isSecureFolderId(i)) {
            Log.d("KKG::KnoxSamsungAnalyticsLogger", "Sending SA logging event");
            Intent intent = new Intent("com.samsung.knox.securefolder.salogging");
            intent.setPackage("com.samsung.knox.securefolder");
            intent.setComponent(
                    new ComponentName(
                            "com.samsung.knox.securefolder",
                            "com.samsung.knox.securefolder.common.util.logging.LoggingReceiver"));
            intent.putExtra("knoxEventList", arrayList);
            context.sendBroadcastAsUser(
                    intent,
                    new UserHandle(i),
                    "com.samsung.android.knox.permission.KNOX_CONTAINER");
        }
        this.mKnoxEventList.clear();
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final void onErrorTimeoutFinish(
            KnoxAuthCredentialView knoxAuthCredentialView, int i, View view) {
        if (i == 1) {
            if (view instanceof EditText) {
                final EditText editText = (EditText) view;
                editText.setClickable(true);
                editText.setInputType(18);
                editText.setCursorVisible(true);
                editText.setFocusableInTouchMode(true);
                final InputMethodManager inputMethodManager =
                        (InputMethodManager)
                                this.mContext.getSystemService(InputMethodManager.class);
                final int i2 = 0;
                knoxAuthCredentialView.postDelayed(
                        new Runnable() { // from class:
                                         // com.samsung.android.biometrics.app.setting.prompt.knox.SecureFolderSysUiClientHelper$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (i2) {
                                    case 0:
                                        EditText editText2 = editText;
                                        InputMethodManager inputMethodManager2 = inputMethodManager;
                                        editText2.requestFocus();
                                        inputMethodManager2.showSoftInput(editText2, 1);
                                        break;
                                    default:
                                        EditText editText3 = editText;
                                        InputMethodManager inputMethodManager3 = inputMethodManager;
                                        editText3.requestFocus();
                                        inputMethodManager3.showSoftInput(editText3, 1);
                                        break;
                                }
                            }
                        },
                        100L);
                return;
            }
            return;
        }
        if (i == 2) {
            if (view instanceof LockPatternView) {
                ((LockPatternView) view).setVisibility(0);
            }
        } else {
            if (i != 3) {
                throw new IllegalStateException(
                        SubMenuBuilder$$ExternalSyntheticOutline0.m(
                                i, "Unknown credential type: "));
            }
            if (view instanceof EditText) {
                final EditText editText2 = (EditText) view;
                editText2.setClickable(true);
                editText2.setInputType(129);
                editText2.setCursorVisible(true);
                editText2.setFocusableInTouchMode(true);
                final InputMethodManager inputMethodManager2 =
                        (InputMethodManager)
                                this.mContext.getSystemService(InputMethodManager.class);
                final int i3 = 1;
                knoxAuthCredentialView.postDelayed(
                        new Runnable() { // from class:
                                         // com.samsung.android.biometrics.app.setting.prompt.knox.SecureFolderSysUiClientHelper$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (i3) {
                                    case 0:
                                        EditText editText22 = editText2;
                                        InputMethodManager inputMethodManager22 =
                                                inputMethodManager2;
                                        editText22.requestFocus();
                                        inputMethodManager22.showSoftInput(editText22, 1);
                                        break;
                                    default:
                                        EditText editText3 = editText2;
                                        InputMethodManager inputMethodManager3 =
                                                inputMethodManager2;
                                        editText3.requestFocus();
                                        inputMethodManager3.showSoftInput(editText3, 1);
                                        break;
                                }
                            }
                        },
                        100L);
            }
        }
    }

    public final void setAuthenticationViewOrientation(
            Configuration configuration, KnoxAuthCredentialView knoxAuthCredentialView) {
        int visibility;
        if (configuration.orientation == 2) {
            knoxAuthCredentialView.setOrientation(0);
        } else {
            knoxAuthCredentialView.setOrientation(1);
        }
        if (isResetWithSamsungAccountEnable()) {
            TextView textView = this.mBtnForgot;
            if (textView != null) {
                visibility = textView.getVisibility();
                this.mBtnForgot.setVisibility(8);
            }
            visibility = 8;
        } else {
            TextView textView2 = this.mUninstallBtn;
            if (textView2 != null) {
                visibility = textView2.getVisibility();
                this.mUninstallBtn.setVisibility(8);
            }
            visibility = 8;
        }
        if (isResetWithSamsungAccountEnable()) {
            TextView textView3 =
                    knoxAuthCredentialView.getOrientation() == 1
                            ? (TextView)
                                    knoxAuthCredentialView.findViewById(R.id.btn_forgot_txt_port)
                            : (TextView)
                                    knoxAuthCredentialView.findViewById(R.id.btn_forgot_txt_land);
            this.mBtnForgot = textView3;
            if (textView3 != null) {
                textView3.setVisibility(visibility);
                TextView textView4 = this.mBtnForgot;
                textView4.setPaintFlags(textView4.getPaintFlags() | 8);
                int i = this.mPromptConfig.mCredentialType;
                if (i == 1) {
                    this.mBtnForgot.setText(
                            this.mContext.getResources().getString(R.string.forgot_pin));
                } else if (i == 2) {
                    this.mBtnForgot.setText(
                            this.mContext.getResources().getString(R.string.forgot_pattern));
                } else if (i == 3) {
                    this.mBtnForgot.setText(
                            this.mContext.getResources().getString(R.string.forgot_password));
                }
                final int i2 = 0;
                this.mBtnForgot.setOnClickListener(
                        new View.OnClickListener(this) { // from class:
                            // com.samsung.android.biometrics.app.setting.prompt.knox.SecureFolderSysUiClientHelper.3
                            public final /* synthetic */ SecureFolderSysUiClientHelper this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                String string;
                                switch (i2) {
                                    case 0:
                                        String str = this.this$0.mPackageName;
                                        boolean z = false;
                                        try {
                                            List tasks =
                                                    ActivityTaskManager.getInstance()
                                                            .getTasks(3, false);
                                            if (tasks != null && !tasks.isEmpty()) {
                                                Iterator it = tasks.iterator();
                                                while (true) {
                                                    if (it.hasNext()) {
                                                        ActivityManager.RunningTaskInfo
                                                                runningTaskInfo =
                                                                        (ActivityManager
                                                                                        .RunningTaskInfo)
                                                                                it.next();
                                                        if (str.equals(
                                                                runningTaskInfo.topActivity
                                                                        .getPackageName())) {
                                                            z =
                                                                    WindowConfiguration
                                                                            .inMultiWindowMode(
                                                                                    runningTaskInfo
                                                                                            .configuration
                                                                                            .windowConfiguration
                                                                                            .getWindowingMode());
                                                        }
                                                    }
                                                }
                                            }
                                        } catch (Exception e) {
                                            FocusableWindow$$ExternalSyntheticOutline0.m(
                                                    e,
                                                    new StringBuilder("isMultiWindowMode: "),
                                                    "KKG::KnoxUtils");
                                        }
                                        Log.d(
                                                "KKG::SecureFolderSysUiClientHelper",
                                                "isInMultiWindowMode : "
                                                        + z
                                                        + ", package : "
                                                        + this.this$0.mPackageName);
                                        if (!Utils.isDesktopMode(this.this$0.mContext) && z) {
                                            Toast.makeText(
                                                            this.this$0.mContext,
                                                            R.string
                                                                    .lock_screen_doesnt_support_multi_window_text,
                                                            1)
                                                    .show();
                                            break;
                                        } else {
                                            final SecureFolderSysUiClientHelper
                                                    secureFolderSysUiClientHelper = this.this$0;
                                            secureFolderSysUiClientHelper.getClass();
                                            try {
                                                AlertDialog.Builder builder =
                                                        new AlertDialog.Builder(
                                                                (secureFolderSysUiClientHelper
                                                                                                .mContext
                                                                                                .getResources()
                                                                                                .getConfiguration()
                                                                                                .uiMode
                                                                                        & 48)
                                                                                == 32
                                                                        ? new ContextThemeWrapper(
                                                                                secureFolderSysUiClientHelper
                                                                                        .mContext
                                                                                        .getApplicationContext(),
                                                                                android.R.style
                                                                                        .Theme
                                                                                        .DeviceDefault)
                                                                        : new ContextThemeWrapper(
                                                                                secureFolderSysUiClientHelper
                                                                                        .mContext
                                                                                        .getApplicationContext(),
                                                                                android.R.style
                                                                                        .Theme
                                                                                        .DeviceDefault
                                                                                        .Light));
                                                int keyguardStoredPasswordQuality =
                                                        secureFolderSysUiClientHelper
                                                                .mLockPatternUtils
                                                                .getKeyguardStoredPasswordQuality(
                                                                        secureFolderSysUiClientHelper
                                                                                .mPromptConfig
                                                                                .mUserId);
                                                if (keyguardStoredPasswordQuality != 65536) {
                                                    if (keyguardStoredPasswordQuality != 131072
                                                            && keyguardStoredPasswordQuality
                                                                    != 196608) {
                                                        if (keyguardStoredPasswordQuality != 262144
                                                                && keyguardStoredPasswordQuality
                                                                        != 327680
                                                                && keyguardStoredPasswordQuality
                                                                        != 393216) {
                                                            if (keyguardStoredPasswordQuality
                                                                    != 458752) {
                                                                if (keyguardStoredPasswordQuality
                                                                        != 524288) {
                                                                    Log.e(
                                                                            "KKG::SecureFolderSysUiClientHelper",
                                                                            "invalid quality"
                                                                                + " error");
                                                                    string =
                                                                            secureFolderSysUiClientHelper
                                                                                    .mContext
                                                                                    .getString(
                                                                                            R.string
                                                                                                    .forgot_pattern_header);
                                                                }
                                                            }
                                                        }
                                                        string =
                                                                secureFolderSysUiClientHelper
                                                                        .mContext.getString(
                                                                        R.string
                                                                                .forgot_password_header);
                                                    }
                                                    string =
                                                            secureFolderSysUiClientHelper.mContext
                                                                    .getString(
                                                                            R.string
                                                                                    .forgot_pin_header);
                                                } else {
                                                    string =
                                                            secureFolderSysUiClientHelper.mContext
                                                                    .getString(
                                                                            R.string
                                                                                    .forgot_pattern_header);
                                                }
                                                builder.setTitle(string);
                                                String string2 =
                                                        SemCscFeature.getInstance()
                                                                .getString(
                                                                        "CscFeature_Common_ReplaceSecBrandAsGalaxy");
                                                if (string2 == null || !string2.equals("TRUE")) {
                                                    builder.setMessage(
                                                            secureFolderSysUiClientHelper.mContext
                                                                    .getString(
                                                                            R.string
                                                                                    .forgot_ppp_dialog));
                                                } else {
                                                    builder.setMessage(
                                                            secureFolderSysUiClientHelper.mContext
                                                                    .getString(
                                                                            R.string
                                                                                    .forgot_ppp_dialog_galaxy));
                                                }
                                                final int i3 = 0;
                                                builder.setPositiveButton(
                                                        R.string.reset,
                                                        new DialogInterface
                                                                .OnClickListener() { // from class:
                                                            // com.samsung.android.biometrics.app.setting.prompt.knox.SecureFolderSysUiClientHelper.5
                                                            @Override // android.content.DialogInterface.OnClickListener
                                                            public final void onClick(
                                                                    DialogInterface dialogInterface,
                                                                    int i4) {
                                                                boolean isAdminActive;
                                                                switch (i3) {
                                                                    case 0:
                                                                        secureFolderSysUiClientHelper
                                                                                .mKnoxEventList.add(
                                                                                KnoxSamsungAnalyticsLogger
                                                                                        .addEvent(
                                                                                                100,
                                                                                                1003,
                                                                                                null));
                                                                        if (!((ActivityManager)
                                                                                        secureFolderSysUiClientHelper
                                                                                                .mContext
                                                                                                .getSystemService(
                                                                                                        "activity"))
                                                                                .isInLockTaskMode()) {
                                                                            ((BiometricPromptClient)
                                                                                            secureFolderSysUiClientHelper
                                                                                                    .mPromptConfig
                                                                                                    .mCallback)
                                                                                    .onUserCancel(
                                                                                            3);
                                                                            secureFolderSysUiClientHelper
                                                                                    .showSFLockedView(
                                                                                            true,
                                                                                            false);
                                                                            dialogInterface
                                                                                    .dismiss();
                                                                            break;
                                                                        } else {
                                                                            Toast.makeText(
                                                                                            secureFolderSysUiClientHelper
                                                                                                    .mContext,
                                                                                            R.string
                                                                                                    .lock_to_app_toast,
                                                                                            1)
                                                                                    .show();
                                                                            break;
                                                                        }
                                                                    case 1:
                                                                        secureFolderSysUiClientHelper
                                                                                .mKnoxEventList.add(
                                                                                KnoxSamsungAnalyticsLogger
                                                                                        .addEvent(
                                                                                                100,
                                                                                                1002,
                                                                                                null));
                                                                        dialogInterface.dismiss();
                                                                        break;
                                                                    case 2:
                                                                        secureFolderSysUiClientHelper
                                                                                .mKnoxEventList.add(
                                                                                KnoxSamsungAnalyticsLogger
                                                                                        .addEvent(
                                                                                                183,
                                                                                                1833,
                                                                                                null));
                                                                        secureFolderSysUiClientHelper
                                                                                .mAlertDialog
                                                                                .getButton(-1)
                                                                                .setEnabled(false);
                                                                        ((BiometricPromptClient)
                                                                                        secureFolderSysUiClientHelper
                                                                                                .mPromptConfig
                                                                                                .mCallback)
                                                                                .onUserCancel(3);
                                                                        SecureFolderSysUiClientHelper
                                                                                secureFolderSysUiClientHelper2 =
                                                                                        secureFolderSysUiClientHelper;
                                                                        Context context =
                                                                                secureFolderSysUiClientHelper2
                                                                                        .mContext;
                                                                        secureFolderSysUiClientHelper2
                                                                                .getClass();
                                                                        Log.d(
                                                                                "KKG::SecureFolderSysUiClientHelper",
                                                                                "removeSecureFolder()");
                                                                        PromptConfig promptConfig =
                                                                                secureFolderSysUiClientHelper2
                                                                                        .mPromptConfig;
                                                                        int i5 =
                                                                                promptConfig
                                                                                        .mUserId;
                                                                        if (i5 <= 0) {
                                                                            Log.d(
                                                                                    "KKG::SecureFolderSysUiClientHelper",
                                                                                    "removeSecureFolder()."
                                                                                        + " Incorrect"
                                                                                        + " User ID"
                                                                                        + " : "
                                                                                            + i5);
                                                                            break;
                                                                        } else {
                                                                            Log.d(
                                                                                    "KKG::SecureFolderSysUiClientHelper",
                                                                                    "setActiveAdminIfNeeded");
                                                                            ComponentName
                                                                                    componentName =
                                                                                            new ComponentName(
                                                                                                    "com.samsung.knox.securefolder",
                                                                                                    "com.samsung.knox.securefolder.containeragent.detector.KnoxDeviceAdminReceiver");
                                                                            DevicePolicyManager
                                                                                    devicePolicyManager =
                                                                                            (DevicePolicyManager)
                                                                                                    context
                                                                                                            .getSystemService(
                                                                                                                    "device_policy");
                                                                            if (devicePolicyManager
                                                                                    == null) {
                                                                                Log.i(
                                                                                        "KKG::SecureFolderSysUiClientHelper",
                                                                                        "DevicePolicyManager"
                                                                                            + " is null");
                                                                                isAdminActive =
                                                                                        false;
                                                                            } else {
                                                                                isAdminActive =
                                                                                        devicePolicyManager
                                                                                                .isAdminActive(
                                                                                                        componentName);
                                                                            }
                                                                            if (!isAdminActive) {
                                                                                Log.d(
                                                                                        "KKG::SecureFolderSysUiClientHelper",
                                                                                        "setActiveAdminIfNeeded():"
                                                                                            + " Setting"
                                                                                            + " active"
                                                                                            + " admin");
                                                                                DevicePolicyManager
                                                                                        devicePolicyManager2 =
                                                                                                (DevicePolicyManager)
                                                                                                        context
                                                                                                                .getSystemService(
                                                                                                                        "device_policy");
                                                                                if (devicePolicyManager2
                                                                                        == null) {
                                                                                    Log.i(
                                                                                            "KKG::SecureFolderSysUiClientHelper",
                                                                                            "DevicePolicyManager"
                                                                                                + " is null");
                                                                                } else {
                                                                                    try {
                                                                                        devicePolicyManager2
                                                                                                .setActiveAdmin(
                                                                                                        componentName,
                                                                                                        false,
                                                                                                        promptConfig
                                                                                                                .mUserId);
                                                                                    } catch (
                                                                                            Exception
                                                                                                    e2) {
                                                                                        Log.d(
                                                                                                "KKG::SecureFolderSysUiClientHelper",
                                                                                                "Error"
                                                                                                    + " setting"
                                                                                                    + " active"
                                                                                                    + " admin"
                                                                                                    + " : "
                                                                                                        + e2
                                                                                                                .getMessage());
                                                                                    }
                                                                                }
                                                                            }
                                                                            Settings.Secure
                                                                                    .putIntForUser(
                                                                                            context
                                                                                                    .getContentResolver(),
                                                                                            "DelFlag",
                                                                                            1,
                                                                                            0);
                                                                            Settings.Secure
                                                                                    .putStringForUser(
                                                                                            context
                                                                                                    .getContentResolver(),
                                                                                            "secure_folder_image_name",
                                                                                            "sf_app_icon_00",
                                                                                            0);
                                                                            Settings.Secure
                                                                                    .putStringForUser(
                                                                                            context
                                                                                                    .getContentResolver(),
                                                                                            "secure_folder_name",
                                                                                            null,
                                                                                            0);
                                                                            try {
                                                                                ((UserManager)
                                                                                                context.createPackageContextAsUser(
                                                                                                                "com.samsung.knox.securefolder",
                                                                                                                0,
                                                                                                                UserHandle
                                                                                                                        .of(
                                                                                                                                i5))
                                                                                                        .getSystemService(
                                                                                                                "user"))
                                                                                        .removeUser(
                                                                                                i5);
                                                                                break;
                                                                            } catch (Exception e3) {
                                                                                Log.d(
                                                                                        "KKG::SecureFolderSysUiClientHelper",
                                                                                        "removeSecureFolderUser()."
                                                                                            + " Exception"
                                                                                            + " : "
                                                                                                + e3
                                                                                                        .getMessage());
                                                                                return;
                                                                            }
                                                                        }
                                                                    default:
                                                                        secureFolderSysUiClientHelper
                                                                                .mKnoxEventList.add(
                                                                                KnoxSamsungAnalyticsLogger
                                                                                        .addEvent(
                                                                                                183,
                                                                                                1832,
                                                                                                null));
                                                                        dialogInterface.dismiss();
                                                                        break;
                                                                }
                                                            }
                                                        });
                                                final int i4 = 1;
                                                builder.setNegativeButton(
                                                        R.string.cancel,
                                                        new DialogInterface
                                                                .OnClickListener() { // from class:
                                                            // com.samsung.android.biometrics.app.setting.prompt.knox.SecureFolderSysUiClientHelper.5
                                                            @Override // android.content.DialogInterface.OnClickListener
                                                            public final void onClick(
                                                                    DialogInterface dialogInterface,
                                                                    int i42) {
                                                                boolean isAdminActive;
                                                                switch (i4) {
                                                                    case 0:
                                                                        secureFolderSysUiClientHelper
                                                                                .mKnoxEventList.add(
                                                                                KnoxSamsungAnalyticsLogger
                                                                                        .addEvent(
                                                                                                100,
                                                                                                1003,
                                                                                                null));
                                                                        if (!((ActivityManager)
                                                                                        secureFolderSysUiClientHelper
                                                                                                .mContext
                                                                                                .getSystemService(
                                                                                                        "activity"))
                                                                                .isInLockTaskMode()) {
                                                                            ((BiometricPromptClient)
                                                                                            secureFolderSysUiClientHelper
                                                                                                    .mPromptConfig
                                                                                                    .mCallback)
                                                                                    .onUserCancel(
                                                                                            3);
                                                                            secureFolderSysUiClientHelper
                                                                                    .showSFLockedView(
                                                                                            true,
                                                                                            false);
                                                                            dialogInterface
                                                                                    .dismiss();
                                                                            break;
                                                                        } else {
                                                                            Toast.makeText(
                                                                                            secureFolderSysUiClientHelper
                                                                                                    .mContext,
                                                                                            R.string
                                                                                                    .lock_to_app_toast,
                                                                                            1)
                                                                                    .show();
                                                                            break;
                                                                        }
                                                                    case 1:
                                                                        secureFolderSysUiClientHelper
                                                                                .mKnoxEventList.add(
                                                                                KnoxSamsungAnalyticsLogger
                                                                                        .addEvent(
                                                                                                100,
                                                                                                1002,
                                                                                                null));
                                                                        dialogInterface.dismiss();
                                                                        break;
                                                                    case 2:
                                                                        secureFolderSysUiClientHelper
                                                                                .mKnoxEventList.add(
                                                                                KnoxSamsungAnalyticsLogger
                                                                                        .addEvent(
                                                                                                183,
                                                                                                1833,
                                                                                                null));
                                                                        secureFolderSysUiClientHelper
                                                                                .mAlertDialog
                                                                                .getButton(-1)
                                                                                .setEnabled(false);
                                                                        ((BiometricPromptClient)
                                                                                        secureFolderSysUiClientHelper
                                                                                                .mPromptConfig
                                                                                                .mCallback)
                                                                                .onUserCancel(3);
                                                                        SecureFolderSysUiClientHelper
                                                                                secureFolderSysUiClientHelper2 =
                                                                                        secureFolderSysUiClientHelper;
                                                                        Context context =
                                                                                secureFolderSysUiClientHelper2
                                                                                        .mContext;
                                                                        secureFolderSysUiClientHelper2
                                                                                .getClass();
                                                                        Log.d(
                                                                                "KKG::SecureFolderSysUiClientHelper",
                                                                                "removeSecureFolder()");
                                                                        PromptConfig promptConfig =
                                                                                secureFolderSysUiClientHelper2
                                                                                        .mPromptConfig;
                                                                        int i5 =
                                                                                promptConfig
                                                                                        .mUserId;
                                                                        if (i5 <= 0) {
                                                                            Log.d(
                                                                                    "KKG::SecureFolderSysUiClientHelper",
                                                                                    "removeSecureFolder()."
                                                                                        + " Incorrect"
                                                                                        + " User ID"
                                                                                        + " : "
                                                                                            + i5);
                                                                            break;
                                                                        } else {
                                                                            Log.d(
                                                                                    "KKG::SecureFolderSysUiClientHelper",
                                                                                    "setActiveAdminIfNeeded");
                                                                            ComponentName
                                                                                    componentName =
                                                                                            new ComponentName(
                                                                                                    "com.samsung.knox.securefolder",
                                                                                                    "com.samsung.knox.securefolder.containeragent.detector.KnoxDeviceAdminReceiver");
                                                                            DevicePolicyManager
                                                                                    devicePolicyManager =
                                                                                            (DevicePolicyManager)
                                                                                                    context
                                                                                                            .getSystemService(
                                                                                                                    "device_policy");
                                                                            if (devicePolicyManager
                                                                                    == null) {
                                                                                Log.i(
                                                                                        "KKG::SecureFolderSysUiClientHelper",
                                                                                        "DevicePolicyManager"
                                                                                            + " is null");
                                                                                isAdminActive =
                                                                                        false;
                                                                            } else {
                                                                                isAdminActive =
                                                                                        devicePolicyManager
                                                                                                .isAdminActive(
                                                                                                        componentName);
                                                                            }
                                                                            if (!isAdminActive) {
                                                                                Log.d(
                                                                                        "KKG::SecureFolderSysUiClientHelper",
                                                                                        "setActiveAdminIfNeeded():"
                                                                                            + " Setting"
                                                                                            + " active"
                                                                                            + " admin");
                                                                                DevicePolicyManager
                                                                                        devicePolicyManager2 =
                                                                                                (DevicePolicyManager)
                                                                                                        context
                                                                                                                .getSystemService(
                                                                                                                        "device_policy");
                                                                                if (devicePolicyManager2
                                                                                        == null) {
                                                                                    Log.i(
                                                                                            "KKG::SecureFolderSysUiClientHelper",
                                                                                            "DevicePolicyManager"
                                                                                                + " is null");
                                                                                } else {
                                                                                    try {
                                                                                        devicePolicyManager2
                                                                                                .setActiveAdmin(
                                                                                                        componentName,
                                                                                                        false,
                                                                                                        promptConfig
                                                                                                                .mUserId);
                                                                                    } catch (
                                                                                            Exception
                                                                                                    e2) {
                                                                                        Log.d(
                                                                                                "KKG::SecureFolderSysUiClientHelper",
                                                                                                "Error"
                                                                                                    + " setting"
                                                                                                    + " active"
                                                                                                    + " admin"
                                                                                                    + " : "
                                                                                                        + e2
                                                                                                                .getMessage());
                                                                                    }
                                                                                }
                                                                            }
                                                                            Settings.Secure
                                                                                    .putIntForUser(
                                                                                            context
                                                                                                    .getContentResolver(),
                                                                                            "DelFlag",
                                                                                            1,
                                                                                            0);
                                                                            Settings.Secure
                                                                                    .putStringForUser(
                                                                                            context
                                                                                                    .getContentResolver(),
                                                                                            "secure_folder_image_name",
                                                                                            "sf_app_icon_00",
                                                                                            0);
                                                                            Settings.Secure
                                                                                    .putStringForUser(
                                                                                            context
                                                                                                    .getContentResolver(),
                                                                                            "secure_folder_name",
                                                                                            null,
                                                                                            0);
                                                                            try {
                                                                                ((UserManager)
                                                                                                context.createPackageContextAsUser(
                                                                                                                "com.samsung.knox.securefolder",
                                                                                                                0,
                                                                                                                UserHandle
                                                                                                                        .of(
                                                                                                                                i5))
                                                                                                        .getSystemService(
                                                                                                                "user"))
                                                                                        .removeUser(
                                                                                                i5);
                                                                                break;
                                                                            } catch (Exception e3) {
                                                                                Log.d(
                                                                                        "KKG::SecureFolderSysUiClientHelper",
                                                                                        "removeSecureFolderUser()."
                                                                                            + " Exception"
                                                                                            + " : "
                                                                                                + e3
                                                                                                        .getMessage());
                                                                                return;
                                                                            }
                                                                        }
                                                                    default:
                                                                        secureFolderSysUiClientHelper
                                                                                .mKnoxEventList.add(
                                                                                KnoxSamsungAnalyticsLogger
                                                                                        .addEvent(
                                                                                                183,
                                                                                                1832,
                                                                                                null));
                                                                        dialogInterface.dismiss();
                                                                        break;
                                                                }
                                                            }
                                                        });
                                                AlertDialog create = builder.create();
                                                secureFolderSysUiClientHelper.mAlertDialog = create;
                                                Window window = create.getWindow();
                                                window.setType(2017);
                                                window.setGravity(80);
                                                secureFolderSysUiClientHelper.mAlertDialog.show();
                                            } catch (Exception e2) {
                                                Log.e(
                                                        "KKG::SecureFolderSysUiClientHelper",
                                                        "Exception : " + e2.getMessage());
                                            }
                                            SecureFolderSysUiClientHelper
                                                    secureFolderSysUiClientHelper2 = this.this$0;
                                            secureFolderSysUiClientHelper2.mKnoxEventList.add(
                                                    KnoxSamsungAnalyticsLogger.addEvent(
                                                            100,
                                                            1001,
                                                            secureFolderSysUiClientHelper2
                                                                    .getCurrentLockType()));
                                            break;
                                        }
                                        break;
                                    default:
                                        final SecureFolderSysUiClientHelper
                                                secureFolderSysUiClientHelper3 = this.this$0;
                                        AlertDialog.Builder builder2 =
                                                new AlertDialog.Builder(
                                                        (secureFolderSysUiClientHelper3
                                                                                        .mContext
                                                                                        .getResources()
                                                                                        .getConfiguration()
                                                                                        .uiMode
                                                                                & 48)
                                                                        == 32
                                                                ? new ContextThemeWrapper(
                                                                        secureFolderSysUiClientHelper3
                                                                                .mContext
                                                                                .getApplicationContext(),
                                                                        android.R.style
                                                                                .Theme
                                                                                .DeviceDefault)
                                                                : new ContextThemeWrapper(
                                                                        secureFolderSysUiClientHelper3
                                                                                .mContext
                                                                                .getApplicationContext(),
                                                                        android.R.style
                                                                                .Theme
                                                                                .DeviceDefault
                                                                                .Light));
                                        builder2.setTitle(R.string.uninstall_secure_folder);
                                        builder2.setMessage(R.string.keyguard_uninstall_popup_msg);
                                        builder2.setCancelable(true);
                                        final int i5 = 2;
                                        builder2.setPositiveButton(
                                                R.string.knox_uninstall_dialog_title,
                                                new DialogInterface
                                                        .OnClickListener() { // from class:
                                                    // com.samsung.android.biometrics.app.setting.prompt.knox.SecureFolderSysUiClientHelper.5
                                                    @Override // android.content.DialogInterface.OnClickListener
                                                    public final void onClick(
                                                            DialogInterface dialogInterface,
                                                            int i42) {
                                                        boolean isAdminActive;
                                                        switch (i5) {
                                                            case 0:
                                                                secureFolderSysUiClientHelper3
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        100, 1003,
                                                                                        null));
                                                                if (!((ActivityManager)
                                                                                secureFolderSysUiClientHelper3
                                                                                        .mContext
                                                                                        .getSystemService(
                                                                                                "activity"))
                                                                        .isInLockTaskMode()) {
                                                                    ((BiometricPromptClient)
                                                                                    secureFolderSysUiClientHelper3
                                                                                            .mPromptConfig
                                                                                            .mCallback)
                                                                            .onUserCancel(3);
                                                                    secureFolderSysUiClientHelper3
                                                                            .showSFLockedView(
                                                                                    true, false);
                                                                    dialogInterface.dismiss();
                                                                    break;
                                                                } else {
                                                                    Toast.makeText(
                                                                                    secureFolderSysUiClientHelper3
                                                                                            .mContext,
                                                                                    R.string
                                                                                            .lock_to_app_toast,
                                                                                    1)
                                                                            .show();
                                                                    break;
                                                                }
                                                            case 1:
                                                                secureFolderSysUiClientHelper3
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        100, 1002,
                                                                                        null));
                                                                dialogInterface.dismiss();
                                                                break;
                                                            case 2:
                                                                secureFolderSysUiClientHelper3
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        183, 1833,
                                                                                        null));
                                                                secureFolderSysUiClientHelper3
                                                                        .mAlertDialog
                                                                        .getButton(-1)
                                                                        .setEnabled(false);
                                                                ((BiometricPromptClient)
                                                                                secureFolderSysUiClientHelper3
                                                                                        .mPromptConfig
                                                                                        .mCallback)
                                                                        .onUserCancel(3);
                                                                SecureFolderSysUiClientHelper
                                                                        secureFolderSysUiClientHelper22 =
                                                                                secureFolderSysUiClientHelper3;
                                                                Context context =
                                                                        secureFolderSysUiClientHelper22
                                                                                .mContext;
                                                                secureFolderSysUiClientHelper22
                                                                        .getClass();
                                                                Log.d(
                                                                        "KKG::SecureFolderSysUiClientHelper",
                                                                        "removeSecureFolder()");
                                                                PromptConfig promptConfig =
                                                                        secureFolderSysUiClientHelper22
                                                                                .mPromptConfig;
                                                                int i52 = promptConfig.mUserId;
                                                                if (i52 <= 0) {
                                                                    Log.d(
                                                                            "KKG::SecureFolderSysUiClientHelper",
                                                                            "removeSecureFolder()."
                                                                                + " Incorrect User"
                                                                                + " ID : "
                                                                                    + i52);
                                                                    break;
                                                                } else {
                                                                    Log.d(
                                                                            "KKG::SecureFolderSysUiClientHelper",
                                                                            "setActiveAdminIfNeeded");
                                                                    ComponentName componentName =
                                                                            new ComponentName(
                                                                                    "com.samsung.knox.securefolder",
                                                                                    "com.samsung.knox.securefolder.containeragent.detector.KnoxDeviceAdminReceiver");
                                                                    DevicePolicyManager
                                                                            devicePolicyManager =
                                                                                    (DevicePolicyManager)
                                                                                            context
                                                                                                    .getSystemService(
                                                                                                            "device_policy");
                                                                    if (devicePolicyManager
                                                                            == null) {
                                                                        Log.i(
                                                                                "KKG::SecureFolderSysUiClientHelper",
                                                                                "DevicePolicyManager"
                                                                                    + " is null");
                                                                        isAdminActive = false;
                                                                    } else {
                                                                        isAdminActive =
                                                                                devicePolicyManager
                                                                                        .isAdminActive(
                                                                                                componentName);
                                                                    }
                                                                    if (!isAdminActive) {
                                                                        Log.d(
                                                                                "KKG::SecureFolderSysUiClientHelper",
                                                                                "setActiveAdminIfNeeded():"
                                                                                    + " Setting"
                                                                                    + " active"
                                                                                    + " admin");
                                                                        DevicePolicyManager
                                                                                devicePolicyManager2 =
                                                                                        (DevicePolicyManager)
                                                                                                context
                                                                                                        .getSystemService(
                                                                                                                "device_policy");
                                                                        if (devicePolicyManager2
                                                                                == null) {
                                                                            Log.i(
                                                                                    "KKG::SecureFolderSysUiClientHelper",
                                                                                    "DevicePolicyManager"
                                                                                        + " is null");
                                                                        } else {
                                                                            try {
                                                                                devicePolicyManager2
                                                                                        .setActiveAdmin(
                                                                                                componentName,
                                                                                                false,
                                                                                                promptConfig
                                                                                                        .mUserId);
                                                                            } catch (
                                                                                    Exception e22) {
                                                                                Log.d(
                                                                                        "KKG::SecureFolderSysUiClientHelper",
                                                                                        "Error"
                                                                                            + " setting"
                                                                                            + " active"
                                                                                            + " admin"
                                                                                            + " : "
                                                                                                + e22
                                                                                                        .getMessage());
                                                                            }
                                                                        }
                                                                    }
                                                                    Settings.Secure.putIntForUser(
                                                                            context
                                                                                    .getContentResolver(),
                                                                            "DelFlag",
                                                                            1,
                                                                            0);
                                                                    Settings.Secure
                                                                            .putStringForUser(
                                                                                    context
                                                                                            .getContentResolver(),
                                                                                    "secure_folder_image_name",
                                                                                    "sf_app_icon_00",
                                                                                    0);
                                                                    Settings.Secure
                                                                            .putStringForUser(
                                                                                    context
                                                                                            .getContentResolver(),
                                                                                    "secure_folder_name",
                                                                                    null,
                                                                                    0);
                                                                    try {
                                                                        ((UserManager)
                                                                                        context.createPackageContextAsUser(
                                                                                                        "com.samsung.knox.securefolder",
                                                                                                        0,
                                                                                                        UserHandle
                                                                                                                .of(
                                                                                                                        i52))
                                                                                                .getSystemService(
                                                                                                        "user"))
                                                                                .removeUser(i52);
                                                                        break;
                                                                    } catch (Exception e3) {
                                                                        Log.d(
                                                                                "KKG::SecureFolderSysUiClientHelper",
                                                                                "removeSecureFolderUser()."
                                                                                    + " Exception :"
                                                                                    + " "
                                                                                        + e3
                                                                                                .getMessage());
                                                                        return;
                                                                    }
                                                                }
                                                            default:
                                                                secureFolderSysUiClientHelper3
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        183, 1832,
                                                                                        null));
                                                                dialogInterface.dismiss();
                                                                break;
                                                        }
                                                    }
                                                });
                                        final int i6 = 3;
                                        builder2.setNegativeButton(
                                                R.string.cancel,
                                                new DialogInterface
                                                        .OnClickListener() { // from class:
                                                    // com.samsung.android.biometrics.app.setting.prompt.knox.SecureFolderSysUiClientHelper.5
                                                    @Override // android.content.DialogInterface.OnClickListener
                                                    public final void onClick(
                                                            DialogInterface dialogInterface,
                                                            int i42) {
                                                        boolean isAdminActive;
                                                        switch (i6) {
                                                            case 0:
                                                                secureFolderSysUiClientHelper3
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        100, 1003,
                                                                                        null));
                                                                if (!((ActivityManager)
                                                                                secureFolderSysUiClientHelper3
                                                                                        .mContext
                                                                                        .getSystemService(
                                                                                                "activity"))
                                                                        .isInLockTaskMode()) {
                                                                    ((BiometricPromptClient)
                                                                                    secureFolderSysUiClientHelper3
                                                                                            .mPromptConfig
                                                                                            .mCallback)
                                                                            .onUserCancel(3);
                                                                    secureFolderSysUiClientHelper3
                                                                            .showSFLockedView(
                                                                                    true, false);
                                                                    dialogInterface.dismiss();
                                                                    break;
                                                                } else {
                                                                    Toast.makeText(
                                                                                    secureFolderSysUiClientHelper3
                                                                                            .mContext,
                                                                                    R.string
                                                                                            .lock_to_app_toast,
                                                                                    1)
                                                                            .show();
                                                                    break;
                                                                }
                                                            case 1:
                                                                secureFolderSysUiClientHelper3
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        100, 1002,
                                                                                        null));
                                                                dialogInterface.dismiss();
                                                                break;
                                                            case 2:
                                                                secureFolderSysUiClientHelper3
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        183, 1833,
                                                                                        null));
                                                                secureFolderSysUiClientHelper3
                                                                        .mAlertDialog
                                                                        .getButton(-1)
                                                                        .setEnabled(false);
                                                                ((BiometricPromptClient)
                                                                                secureFolderSysUiClientHelper3
                                                                                        .mPromptConfig
                                                                                        .mCallback)
                                                                        .onUserCancel(3);
                                                                SecureFolderSysUiClientHelper
                                                                        secureFolderSysUiClientHelper22 =
                                                                                secureFolderSysUiClientHelper3;
                                                                Context context =
                                                                        secureFolderSysUiClientHelper22
                                                                                .mContext;
                                                                secureFolderSysUiClientHelper22
                                                                        .getClass();
                                                                Log.d(
                                                                        "KKG::SecureFolderSysUiClientHelper",
                                                                        "removeSecureFolder()");
                                                                PromptConfig promptConfig =
                                                                        secureFolderSysUiClientHelper22
                                                                                .mPromptConfig;
                                                                int i52 = promptConfig.mUserId;
                                                                if (i52 <= 0) {
                                                                    Log.d(
                                                                            "KKG::SecureFolderSysUiClientHelper",
                                                                            "removeSecureFolder()."
                                                                                + " Incorrect User"
                                                                                + " ID : "
                                                                                    + i52);
                                                                    break;
                                                                } else {
                                                                    Log.d(
                                                                            "KKG::SecureFolderSysUiClientHelper",
                                                                            "setActiveAdminIfNeeded");
                                                                    ComponentName componentName =
                                                                            new ComponentName(
                                                                                    "com.samsung.knox.securefolder",
                                                                                    "com.samsung.knox.securefolder.containeragent.detector.KnoxDeviceAdminReceiver");
                                                                    DevicePolicyManager
                                                                            devicePolicyManager =
                                                                                    (DevicePolicyManager)
                                                                                            context
                                                                                                    .getSystemService(
                                                                                                            "device_policy");
                                                                    if (devicePolicyManager
                                                                            == null) {
                                                                        Log.i(
                                                                                "KKG::SecureFolderSysUiClientHelper",
                                                                                "DevicePolicyManager"
                                                                                    + " is null");
                                                                        isAdminActive = false;
                                                                    } else {
                                                                        isAdminActive =
                                                                                devicePolicyManager
                                                                                        .isAdminActive(
                                                                                                componentName);
                                                                    }
                                                                    if (!isAdminActive) {
                                                                        Log.d(
                                                                                "KKG::SecureFolderSysUiClientHelper",
                                                                                "setActiveAdminIfNeeded():"
                                                                                    + " Setting"
                                                                                    + " active"
                                                                                    + " admin");
                                                                        DevicePolicyManager
                                                                                devicePolicyManager2 =
                                                                                        (DevicePolicyManager)
                                                                                                context
                                                                                                        .getSystemService(
                                                                                                                "device_policy");
                                                                        if (devicePolicyManager2
                                                                                == null) {
                                                                            Log.i(
                                                                                    "KKG::SecureFolderSysUiClientHelper",
                                                                                    "DevicePolicyManager"
                                                                                        + " is null");
                                                                        } else {
                                                                            try {
                                                                                devicePolicyManager2
                                                                                        .setActiveAdmin(
                                                                                                componentName,
                                                                                                false,
                                                                                                promptConfig
                                                                                                        .mUserId);
                                                                            } catch (
                                                                                    Exception e22) {
                                                                                Log.d(
                                                                                        "KKG::SecureFolderSysUiClientHelper",
                                                                                        "Error"
                                                                                            + " setting"
                                                                                            + " active"
                                                                                            + " admin"
                                                                                            + " : "
                                                                                                + e22
                                                                                                        .getMessage());
                                                                            }
                                                                        }
                                                                    }
                                                                    Settings.Secure.putIntForUser(
                                                                            context
                                                                                    .getContentResolver(),
                                                                            "DelFlag",
                                                                            1,
                                                                            0);
                                                                    Settings.Secure
                                                                            .putStringForUser(
                                                                                    context
                                                                                            .getContentResolver(),
                                                                                    "secure_folder_image_name",
                                                                                    "sf_app_icon_00",
                                                                                    0);
                                                                    Settings.Secure
                                                                            .putStringForUser(
                                                                                    context
                                                                                            .getContentResolver(),
                                                                                    "secure_folder_name",
                                                                                    null,
                                                                                    0);
                                                                    try {
                                                                        ((UserManager)
                                                                                        context.createPackageContextAsUser(
                                                                                                        "com.samsung.knox.securefolder",
                                                                                                        0,
                                                                                                        UserHandle
                                                                                                                .of(
                                                                                                                        i52))
                                                                                                .getSystemService(
                                                                                                        "user"))
                                                                                .removeUser(i52);
                                                                        break;
                                                                    } catch (Exception e3) {
                                                                        Log.d(
                                                                                "KKG::SecureFolderSysUiClientHelper",
                                                                                "removeSecureFolderUser()."
                                                                                    + " Exception :"
                                                                                    + " "
                                                                                        + e3
                                                                                                .getMessage());
                                                                        return;
                                                                    }
                                                                }
                                                            default:
                                                                secureFolderSysUiClientHelper3
                                                                        .mKnoxEventList.add(
                                                                        KnoxSamsungAnalyticsLogger
                                                                                .addEvent(
                                                                                        183, 1832,
                                                                                        null));
                                                                dialogInterface.dismiss();
                                                                break;
                                                        }
                                                    }
                                                });
                                        AlertDialog create2 = builder2.create();
                                        secureFolderSysUiClientHelper3.mAlertDialog = create2;
                                        Window window2 = create2.getWindow();
                                        window2.setType(2017);
                                        window2.setGravity(80);
                                        secureFolderSysUiClientHelper3.mAlertDialog
                                                .setCanceledOnTouchOutside(true);
                                        secureFolderSysUiClientHelper3.mAlertDialog.show();
                                        break;
                                }
                            }
                        });
                return;
            }
            return;
        }
        int dimensionPixelSize =
                this.mContext.getResources().getDimensionPixelSize(R.dimen.uninstall_btn_height);
        int dimensionPixelSize2 =
                this.mContext.getResources().getDimensionPixelSize(R.dimen.uninstall_btn_width);
        int dimensionPixelSize3 =
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.uninstall_btn_padding_left_right);
        if (knoxAuthCredentialView.getOrientation() == 1) {
            TextView textView5 =
                    (TextView) knoxAuthCredentialView.findViewById(R.id.btn_forgot_txt_port);
            this.mUninstallBtn = textView5;
            if (textView5 != null) {
                LinearLayout.LayoutParams layoutParams =
                        new LinearLayout.LayoutParams(dimensionPixelSize2, dimensionPixelSize);
                layoutParams.topMargin =
                        this.mContext
                                .getResources()
                                .getDimensionPixelSize(R.dimen.uninstall_btn_margin_top_port);
                layoutParams.gravity = 1;
                this.mUninstallBtn.setLayoutParams(layoutParams);
            }
        } else {
            TextView textView6 =
                    (TextView) knoxAuthCredentialView.findViewById(R.id.btn_forgot_txt_land);
            this.mUninstallBtn = textView6;
            if (textView6 != null) {
                RelativeLayout.LayoutParams layoutParams2 =
                        new RelativeLayout.LayoutParams(dimensionPixelSize2, dimensionPixelSize);
                layoutParams2.topMargin =
                        this.mContext
                                .getResources()
                                .getDimensionPixelSize(R.dimen.uninstall_btn_margin_top_land);
                layoutParams2.addRule(3, R.id.detailsText);
                layoutParams2.addRule(14);
                this.mUninstallBtn.setLayoutParams(layoutParams2);
            }
        }
        TextView textView7 = this.mUninstallBtn;
        if (textView7 != null) {
            textView7.setVisibility(visibility);
            this.mUninstallBtn.setGravity(17);
            TextView textView8 = this.mUninstallBtn;
            textView8.setPadding(
                    dimensionPixelSize3,
                    textView8.getPaddingTop(),
                    dimensionPixelSize3,
                    this.mUninstallBtn.getPaddingBottom());
            this.mUninstallBtn.setText(R.string.knox_uninstall_dialog_title);
            this.mUninstallBtn.setBackgroundResource(
                    R.drawable.sec_knox_ripple_effect_transparent_button_drawable);
            final int i3 = 1;
            this.mUninstallBtn.setOnClickListener(
                    new View.OnClickListener(this) { // from class:
                        // com.samsung.android.biometrics.app.setting.prompt.knox.SecureFolderSysUiClientHelper.3
                        public final /* synthetic */ SecureFolderSysUiClientHelper this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            String string;
                            switch (i3) {
                                case 0:
                                    String str = this.this$0.mPackageName;
                                    boolean z = false;
                                    try {
                                        List tasks =
                                                ActivityTaskManager.getInstance()
                                                        .getTasks(3, false);
                                        if (tasks != null && !tasks.isEmpty()) {
                                            Iterator it = tasks.iterator();
                                            while (true) {
                                                if (it.hasNext()) {
                                                    ActivityManager.RunningTaskInfo
                                                            runningTaskInfo =
                                                                    (ActivityManager
                                                                                    .RunningTaskInfo)
                                                                            it.next();
                                                    if (str.equals(
                                                            runningTaskInfo.topActivity
                                                                    .getPackageName())) {
                                                        z =
                                                                WindowConfiguration
                                                                        .inMultiWindowMode(
                                                                                runningTaskInfo
                                                                                        .configuration
                                                                                        .windowConfiguration
                                                                                        .getWindowingMode());
                                                    }
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        FocusableWindow$$ExternalSyntheticOutline0.m(
                                                e,
                                                new StringBuilder("isMultiWindowMode: "),
                                                "KKG::KnoxUtils");
                                    }
                                    Log.d(
                                            "KKG::SecureFolderSysUiClientHelper",
                                            "isInMultiWindowMode : "
                                                    + z
                                                    + ", package : "
                                                    + this.this$0.mPackageName);
                                    if (!Utils.isDesktopMode(this.this$0.mContext) && z) {
                                        Toast.makeText(
                                                        this.this$0.mContext,
                                                        R.string
                                                                .lock_screen_doesnt_support_multi_window_text,
                                                        1)
                                                .show();
                                        break;
                                    } else {
                                        final SecureFolderSysUiClientHelper
                                                secureFolderSysUiClientHelper = this.this$0;
                                        secureFolderSysUiClientHelper.getClass();
                                        try {
                                            AlertDialog.Builder builder =
                                                    new AlertDialog.Builder(
                                                            (secureFolderSysUiClientHelper
                                                                                            .mContext
                                                                                            .getResources()
                                                                                            .getConfiguration()
                                                                                            .uiMode
                                                                                    & 48)
                                                                            == 32
                                                                    ? new ContextThemeWrapper(
                                                                            secureFolderSysUiClientHelper
                                                                                    .mContext
                                                                                    .getApplicationContext(),
                                                                            android.R.style
                                                                                    .Theme
                                                                                    .DeviceDefault)
                                                                    : new ContextThemeWrapper(
                                                                            secureFolderSysUiClientHelper
                                                                                    .mContext
                                                                                    .getApplicationContext(),
                                                                            android.R.style
                                                                                    .Theme
                                                                                    .DeviceDefault
                                                                                    .Light));
                                            int keyguardStoredPasswordQuality =
                                                    secureFolderSysUiClientHelper.mLockPatternUtils
                                                            .getKeyguardStoredPasswordQuality(
                                                                    secureFolderSysUiClientHelper
                                                                            .mPromptConfig
                                                                            .mUserId);
                                            if (keyguardStoredPasswordQuality != 65536) {
                                                if (keyguardStoredPasswordQuality != 131072
                                                        && keyguardStoredPasswordQuality
                                                                != 196608) {
                                                    if (keyguardStoredPasswordQuality != 262144
                                                            && keyguardStoredPasswordQuality
                                                                    != 327680
                                                            && keyguardStoredPasswordQuality
                                                                    != 393216) {
                                                        if (keyguardStoredPasswordQuality
                                                                != 458752) {
                                                            if (keyguardStoredPasswordQuality
                                                                    != 524288) {
                                                                Log.e(
                                                                        "KKG::SecureFolderSysUiClientHelper",
                                                                        "invalid quality error");
                                                                string =
                                                                        secureFolderSysUiClientHelper
                                                                                .mContext.getString(
                                                                                R.string
                                                                                        .forgot_pattern_header);
                                                            }
                                                        }
                                                    }
                                                    string =
                                                            secureFolderSysUiClientHelper.mContext
                                                                    .getString(
                                                                            R.string
                                                                                    .forgot_password_header);
                                                }
                                                string =
                                                        secureFolderSysUiClientHelper.mContext
                                                                .getString(
                                                                        R.string.forgot_pin_header);
                                            } else {
                                                string =
                                                        secureFolderSysUiClientHelper.mContext
                                                                .getString(
                                                                        R.string
                                                                                .forgot_pattern_header);
                                            }
                                            builder.setTitle(string);
                                            String string2 =
                                                    SemCscFeature.getInstance()
                                                            .getString(
                                                                    "CscFeature_Common_ReplaceSecBrandAsGalaxy");
                                            if (string2 == null || !string2.equals("TRUE")) {
                                                builder.setMessage(
                                                        secureFolderSysUiClientHelper.mContext
                                                                .getString(
                                                                        R.string
                                                                                .forgot_ppp_dialog));
                                            } else {
                                                builder.setMessage(
                                                        secureFolderSysUiClientHelper.mContext
                                                                .getString(
                                                                        R.string
                                                                                .forgot_ppp_dialog_galaxy));
                                            }
                                            final int i32 = 0;
                                            builder.setPositiveButton(
                                                    R.string.reset,
                                                    new DialogInterface
                                                            .OnClickListener() { // from class:
                                                        // com.samsung.android.biometrics.app.setting.prompt.knox.SecureFolderSysUiClientHelper.5
                                                        @Override // android.content.DialogInterface.OnClickListener
                                                        public final void onClick(
                                                                DialogInterface dialogInterface,
                                                                int i42) {
                                                            boolean isAdminActive;
                                                            switch (i32) {
                                                                case 0:
                                                                    secureFolderSysUiClientHelper
                                                                            .mKnoxEventList.add(
                                                                            KnoxSamsungAnalyticsLogger
                                                                                    .addEvent(
                                                                                            100,
                                                                                            1003,
                                                                                            null));
                                                                    if (!((ActivityManager)
                                                                                    secureFolderSysUiClientHelper
                                                                                            .mContext
                                                                                            .getSystemService(
                                                                                                    "activity"))
                                                                            .isInLockTaskMode()) {
                                                                        ((BiometricPromptClient)
                                                                                        secureFolderSysUiClientHelper
                                                                                                .mPromptConfig
                                                                                                .mCallback)
                                                                                .onUserCancel(3);
                                                                        secureFolderSysUiClientHelper
                                                                                .showSFLockedView(
                                                                                        true,
                                                                                        false);
                                                                        dialogInterface.dismiss();
                                                                        break;
                                                                    } else {
                                                                        Toast.makeText(
                                                                                        secureFolderSysUiClientHelper
                                                                                                .mContext,
                                                                                        R.string
                                                                                                .lock_to_app_toast,
                                                                                        1)
                                                                                .show();
                                                                        break;
                                                                    }
                                                                case 1:
                                                                    secureFolderSysUiClientHelper
                                                                            .mKnoxEventList.add(
                                                                            KnoxSamsungAnalyticsLogger
                                                                                    .addEvent(
                                                                                            100,
                                                                                            1002,
                                                                                            null));
                                                                    dialogInterface.dismiss();
                                                                    break;
                                                                case 2:
                                                                    secureFolderSysUiClientHelper
                                                                            .mKnoxEventList.add(
                                                                            KnoxSamsungAnalyticsLogger
                                                                                    .addEvent(
                                                                                            183,
                                                                                            1833,
                                                                                            null));
                                                                    secureFolderSysUiClientHelper
                                                                            .mAlertDialog
                                                                            .getButton(-1)
                                                                            .setEnabled(false);
                                                                    ((BiometricPromptClient)
                                                                                    secureFolderSysUiClientHelper
                                                                                            .mPromptConfig
                                                                                            .mCallback)
                                                                            .onUserCancel(3);
                                                                    SecureFolderSysUiClientHelper
                                                                            secureFolderSysUiClientHelper22 =
                                                                                    secureFolderSysUiClientHelper;
                                                                    Context context =
                                                                            secureFolderSysUiClientHelper22
                                                                                    .mContext;
                                                                    secureFolderSysUiClientHelper22
                                                                            .getClass();
                                                                    Log.d(
                                                                            "KKG::SecureFolderSysUiClientHelper",
                                                                            "removeSecureFolder()");
                                                                    PromptConfig promptConfig =
                                                                            secureFolderSysUiClientHelper22
                                                                                    .mPromptConfig;
                                                                    int i52 = promptConfig.mUserId;
                                                                    if (i52 <= 0) {
                                                                        Log.d(
                                                                                "KKG::SecureFolderSysUiClientHelper",
                                                                                "removeSecureFolder()."
                                                                                    + " Incorrect"
                                                                                    + " User ID : "
                                                                                        + i52);
                                                                        break;
                                                                    } else {
                                                                        Log.d(
                                                                                "KKG::SecureFolderSysUiClientHelper",
                                                                                "setActiveAdminIfNeeded");
                                                                        ComponentName
                                                                                componentName =
                                                                                        new ComponentName(
                                                                                                "com.samsung.knox.securefolder",
                                                                                                "com.samsung.knox.securefolder.containeragent.detector.KnoxDeviceAdminReceiver");
                                                                        DevicePolicyManager
                                                                                devicePolicyManager =
                                                                                        (DevicePolicyManager)
                                                                                                context
                                                                                                        .getSystemService(
                                                                                                                "device_policy");
                                                                        if (devicePolicyManager
                                                                                == null) {
                                                                            Log.i(
                                                                                    "KKG::SecureFolderSysUiClientHelper",
                                                                                    "DevicePolicyManager"
                                                                                        + " is null");
                                                                            isAdminActive = false;
                                                                        } else {
                                                                            isAdminActive =
                                                                                    devicePolicyManager
                                                                                            .isAdminActive(
                                                                                                    componentName);
                                                                        }
                                                                        if (!isAdminActive) {
                                                                            Log.d(
                                                                                    "KKG::SecureFolderSysUiClientHelper",
                                                                                    "setActiveAdminIfNeeded():"
                                                                                        + " Setting"
                                                                                        + " active"
                                                                                        + " admin");
                                                                            DevicePolicyManager
                                                                                    devicePolicyManager2 =
                                                                                            (DevicePolicyManager)
                                                                                                    context
                                                                                                            .getSystemService(
                                                                                                                    "device_policy");
                                                                            if (devicePolicyManager2
                                                                                    == null) {
                                                                                Log.i(
                                                                                        "KKG::SecureFolderSysUiClientHelper",
                                                                                        "DevicePolicyManager"
                                                                                            + " is null");
                                                                            } else {
                                                                                try {
                                                                                    devicePolicyManager2
                                                                                            .setActiveAdmin(
                                                                                                    componentName,
                                                                                                    false,
                                                                                                    promptConfig
                                                                                                            .mUserId);
                                                                                } catch (
                                                                                        Exception
                                                                                                e22) {
                                                                                    Log.d(
                                                                                            "KKG::SecureFolderSysUiClientHelper",
                                                                                            "Error"
                                                                                                + " setting"
                                                                                                + " active"
                                                                                                + " admin"
                                                                                                + " : "
                                                                                                    + e22
                                                                                                            .getMessage());
                                                                                }
                                                                            }
                                                                        }
                                                                        Settings.Secure
                                                                                .putIntForUser(
                                                                                        context
                                                                                                .getContentResolver(),
                                                                                        "DelFlag",
                                                                                        1,
                                                                                        0);
                                                                        Settings.Secure
                                                                                .putStringForUser(
                                                                                        context
                                                                                                .getContentResolver(),
                                                                                        "secure_folder_image_name",
                                                                                        "sf_app_icon_00",
                                                                                        0);
                                                                        Settings.Secure
                                                                                .putStringForUser(
                                                                                        context
                                                                                                .getContentResolver(),
                                                                                        "secure_folder_name",
                                                                                        null,
                                                                                        0);
                                                                        try {
                                                                            ((UserManager)
                                                                                            context.createPackageContextAsUser(
                                                                                                            "com.samsung.knox.securefolder",
                                                                                                            0,
                                                                                                            UserHandle
                                                                                                                    .of(
                                                                                                                            i52))
                                                                                                    .getSystemService(
                                                                                                            "user"))
                                                                                    .removeUser(
                                                                                            i52);
                                                                            break;
                                                                        } catch (Exception e3) {
                                                                            Log.d(
                                                                                    "KKG::SecureFolderSysUiClientHelper",
                                                                                    "removeSecureFolderUser()."
                                                                                        + " Exception"
                                                                                        + " : "
                                                                                            + e3
                                                                                                    .getMessage());
                                                                            return;
                                                                        }
                                                                    }
                                                                default:
                                                                    secureFolderSysUiClientHelper
                                                                            .mKnoxEventList.add(
                                                                            KnoxSamsungAnalyticsLogger
                                                                                    .addEvent(
                                                                                            183,
                                                                                            1832,
                                                                                            null));
                                                                    dialogInterface.dismiss();
                                                                    break;
                                                            }
                                                        }
                                                    });
                                            final int i4 = 1;
                                            builder.setNegativeButton(
                                                    R.string.cancel,
                                                    new DialogInterface
                                                            .OnClickListener() { // from class:
                                                        // com.samsung.android.biometrics.app.setting.prompt.knox.SecureFolderSysUiClientHelper.5
                                                        @Override // android.content.DialogInterface.OnClickListener
                                                        public final void onClick(
                                                                DialogInterface dialogInterface,
                                                                int i42) {
                                                            boolean isAdminActive;
                                                            switch (i4) {
                                                                case 0:
                                                                    secureFolderSysUiClientHelper
                                                                            .mKnoxEventList.add(
                                                                            KnoxSamsungAnalyticsLogger
                                                                                    .addEvent(
                                                                                            100,
                                                                                            1003,
                                                                                            null));
                                                                    if (!((ActivityManager)
                                                                                    secureFolderSysUiClientHelper
                                                                                            .mContext
                                                                                            .getSystemService(
                                                                                                    "activity"))
                                                                            .isInLockTaskMode()) {
                                                                        ((BiometricPromptClient)
                                                                                        secureFolderSysUiClientHelper
                                                                                                .mPromptConfig
                                                                                                .mCallback)
                                                                                .onUserCancel(3);
                                                                        secureFolderSysUiClientHelper
                                                                                .showSFLockedView(
                                                                                        true,
                                                                                        false);
                                                                        dialogInterface.dismiss();
                                                                        break;
                                                                    } else {
                                                                        Toast.makeText(
                                                                                        secureFolderSysUiClientHelper
                                                                                                .mContext,
                                                                                        R.string
                                                                                                .lock_to_app_toast,
                                                                                        1)
                                                                                .show();
                                                                        break;
                                                                    }
                                                                case 1:
                                                                    secureFolderSysUiClientHelper
                                                                            .mKnoxEventList.add(
                                                                            KnoxSamsungAnalyticsLogger
                                                                                    .addEvent(
                                                                                            100,
                                                                                            1002,
                                                                                            null));
                                                                    dialogInterface.dismiss();
                                                                    break;
                                                                case 2:
                                                                    secureFolderSysUiClientHelper
                                                                            .mKnoxEventList.add(
                                                                            KnoxSamsungAnalyticsLogger
                                                                                    .addEvent(
                                                                                            183,
                                                                                            1833,
                                                                                            null));
                                                                    secureFolderSysUiClientHelper
                                                                            .mAlertDialog
                                                                            .getButton(-1)
                                                                            .setEnabled(false);
                                                                    ((BiometricPromptClient)
                                                                                    secureFolderSysUiClientHelper
                                                                                            .mPromptConfig
                                                                                            .mCallback)
                                                                            .onUserCancel(3);
                                                                    SecureFolderSysUiClientHelper
                                                                            secureFolderSysUiClientHelper22 =
                                                                                    secureFolderSysUiClientHelper;
                                                                    Context context =
                                                                            secureFolderSysUiClientHelper22
                                                                                    .mContext;
                                                                    secureFolderSysUiClientHelper22
                                                                            .getClass();
                                                                    Log.d(
                                                                            "KKG::SecureFolderSysUiClientHelper",
                                                                            "removeSecureFolder()");
                                                                    PromptConfig promptConfig =
                                                                            secureFolderSysUiClientHelper22
                                                                                    .mPromptConfig;
                                                                    int i52 = promptConfig.mUserId;
                                                                    if (i52 <= 0) {
                                                                        Log.d(
                                                                                "KKG::SecureFolderSysUiClientHelper",
                                                                                "removeSecureFolder()."
                                                                                    + " Incorrect"
                                                                                    + " User ID : "
                                                                                        + i52);
                                                                        break;
                                                                    } else {
                                                                        Log.d(
                                                                                "KKG::SecureFolderSysUiClientHelper",
                                                                                "setActiveAdminIfNeeded");
                                                                        ComponentName
                                                                                componentName =
                                                                                        new ComponentName(
                                                                                                "com.samsung.knox.securefolder",
                                                                                                "com.samsung.knox.securefolder.containeragent.detector.KnoxDeviceAdminReceiver");
                                                                        DevicePolicyManager
                                                                                devicePolicyManager =
                                                                                        (DevicePolicyManager)
                                                                                                context
                                                                                                        .getSystemService(
                                                                                                                "device_policy");
                                                                        if (devicePolicyManager
                                                                                == null) {
                                                                            Log.i(
                                                                                    "KKG::SecureFolderSysUiClientHelper",
                                                                                    "DevicePolicyManager"
                                                                                        + " is null");
                                                                            isAdminActive = false;
                                                                        } else {
                                                                            isAdminActive =
                                                                                    devicePolicyManager
                                                                                            .isAdminActive(
                                                                                                    componentName);
                                                                        }
                                                                        if (!isAdminActive) {
                                                                            Log.d(
                                                                                    "KKG::SecureFolderSysUiClientHelper",
                                                                                    "setActiveAdminIfNeeded():"
                                                                                        + " Setting"
                                                                                        + " active"
                                                                                        + " admin");
                                                                            DevicePolicyManager
                                                                                    devicePolicyManager2 =
                                                                                            (DevicePolicyManager)
                                                                                                    context
                                                                                                            .getSystemService(
                                                                                                                    "device_policy");
                                                                            if (devicePolicyManager2
                                                                                    == null) {
                                                                                Log.i(
                                                                                        "KKG::SecureFolderSysUiClientHelper",
                                                                                        "DevicePolicyManager"
                                                                                            + " is null");
                                                                            } else {
                                                                                try {
                                                                                    devicePolicyManager2
                                                                                            .setActiveAdmin(
                                                                                                    componentName,
                                                                                                    false,
                                                                                                    promptConfig
                                                                                                            .mUserId);
                                                                                } catch (
                                                                                        Exception
                                                                                                e22) {
                                                                                    Log.d(
                                                                                            "KKG::SecureFolderSysUiClientHelper",
                                                                                            "Error"
                                                                                                + " setting"
                                                                                                + " active"
                                                                                                + " admin"
                                                                                                + " : "
                                                                                                    + e22
                                                                                                            .getMessage());
                                                                                }
                                                                            }
                                                                        }
                                                                        Settings.Secure
                                                                                .putIntForUser(
                                                                                        context
                                                                                                .getContentResolver(),
                                                                                        "DelFlag",
                                                                                        1,
                                                                                        0);
                                                                        Settings.Secure
                                                                                .putStringForUser(
                                                                                        context
                                                                                                .getContentResolver(),
                                                                                        "secure_folder_image_name",
                                                                                        "sf_app_icon_00",
                                                                                        0);
                                                                        Settings.Secure
                                                                                .putStringForUser(
                                                                                        context
                                                                                                .getContentResolver(),
                                                                                        "secure_folder_name",
                                                                                        null,
                                                                                        0);
                                                                        try {
                                                                            ((UserManager)
                                                                                            context.createPackageContextAsUser(
                                                                                                            "com.samsung.knox.securefolder",
                                                                                                            0,
                                                                                                            UserHandle
                                                                                                                    .of(
                                                                                                                            i52))
                                                                                                    .getSystemService(
                                                                                                            "user"))
                                                                                    .removeUser(
                                                                                            i52);
                                                                            break;
                                                                        } catch (Exception e3) {
                                                                            Log.d(
                                                                                    "KKG::SecureFolderSysUiClientHelper",
                                                                                    "removeSecureFolderUser()."
                                                                                        + " Exception"
                                                                                        + " : "
                                                                                            + e3
                                                                                                    .getMessage());
                                                                            return;
                                                                        }
                                                                    }
                                                                default:
                                                                    secureFolderSysUiClientHelper
                                                                            .mKnoxEventList.add(
                                                                            KnoxSamsungAnalyticsLogger
                                                                                    .addEvent(
                                                                                            183,
                                                                                            1832,
                                                                                            null));
                                                                    dialogInterface.dismiss();
                                                                    break;
                                                            }
                                                        }
                                                    });
                                            AlertDialog create = builder.create();
                                            secureFolderSysUiClientHelper.mAlertDialog = create;
                                            Window window = create.getWindow();
                                            window.setType(2017);
                                            window.setGravity(80);
                                            secureFolderSysUiClientHelper.mAlertDialog.show();
                                        } catch (Exception e2) {
                                            Log.e(
                                                    "KKG::SecureFolderSysUiClientHelper",
                                                    "Exception : " + e2.getMessage());
                                        }
                                        SecureFolderSysUiClientHelper
                                                secureFolderSysUiClientHelper2 = this.this$0;
                                        secureFolderSysUiClientHelper2.mKnoxEventList.add(
                                                KnoxSamsungAnalyticsLogger.addEvent(
                                                        100,
                                                        1001,
                                                        secureFolderSysUiClientHelper2
                                                                .getCurrentLockType()));
                                        break;
                                    }
                                    break;
                                default:
                                    final SecureFolderSysUiClientHelper
                                            secureFolderSysUiClientHelper3 = this.this$0;
                                    AlertDialog.Builder builder2 =
                                            new AlertDialog.Builder(
                                                    (secureFolderSysUiClientHelper3
                                                                                    .mContext
                                                                                    .getResources()
                                                                                    .getConfiguration()
                                                                                    .uiMode
                                                                            & 48)
                                                                    == 32
                                                            ? new ContextThemeWrapper(
                                                                    secureFolderSysUiClientHelper3
                                                                            .mContext
                                                                            .getApplicationContext(),
                                                                    android.R.style
                                                                            .Theme
                                                                            .DeviceDefault)
                                                            : new ContextThemeWrapper(
                                                                    secureFolderSysUiClientHelper3
                                                                            .mContext
                                                                            .getApplicationContext(),
                                                                    android.R.style
                                                                            .Theme
                                                                            .DeviceDefault
                                                                            .Light));
                                    builder2.setTitle(R.string.uninstall_secure_folder);
                                    builder2.setMessage(R.string.keyguard_uninstall_popup_msg);
                                    builder2.setCancelable(true);
                                    final int i5 = 2;
                                    builder2.setPositiveButton(
                                            R.string.knox_uninstall_dialog_title,
                                            new DialogInterface.OnClickListener() { // from class:
                                                // com.samsung.android.biometrics.app.setting.prompt.knox.SecureFolderSysUiClientHelper.5
                                                @Override // android.content.DialogInterface.OnClickListener
                                                public final void onClick(
                                                        DialogInterface dialogInterface, int i42) {
                                                    boolean isAdminActive;
                                                    switch (i5) {
                                                        case 0:
                                                            secureFolderSysUiClientHelper3
                                                                    .mKnoxEventList.add(
                                                                    KnoxSamsungAnalyticsLogger
                                                                            .addEvent(
                                                                                    100, 1003,
                                                                                    null));
                                                            if (!((ActivityManager)
                                                                            secureFolderSysUiClientHelper3
                                                                                    .mContext
                                                                                    .getSystemService(
                                                                                            "activity"))
                                                                    .isInLockTaskMode()) {
                                                                ((BiometricPromptClient)
                                                                                secureFolderSysUiClientHelper3
                                                                                        .mPromptConfig
                                                                                        .mCallback)
                                                                        .onUserCancel(3);
                                                                secureFolderSysUiClientHelper3
                                                                        .showSFLockedView(
                                                                                true, false);
                                                                dialogInterface.dismiss();
                                                                break;
                                                            } else {
                                                                Toast.makeText(
                                                                                secureFolderSysUiClientHelper3
                                                                                        .mContext,
                                                                                R.string
                                                                                        .lock_to_app_toast,
                                                                                1)
                                                                        .show();
                                                                break;
                                                            }
                                                        case 1:
                                                            secureFolderSysUiClientHelper3
                                                                    .mKnoxEventList.add(
                                                                    KnoxSamsungAnalyticsLogger
                                                                            .addEvent(
                                                                                    100, 1002,
                                                                                    null));
                                                            dialogInterface.dismiss();
                                                            break;
                                                        case 2:
                                                            secureFolderSysUiClientHelper3
                                                                    .mKnoxEventList.add(
                                                                    KnoxSamsungAnalyticsLogger
                                                                            .addEvent(
                                                                                    183, 1833,
                                                                                    null));
                                                            secureFolderSysUiClientHelper3
                                                                    .mAlertDialog
                                                                    .getButton(-1)
                                                                    .setEnabled(false);
                                                            ((BiometricPromptClient)
                                                                            secureFolderSysUiClientHelper3
                                                                                    .mPromptConfig
                                                                                    .mCallback)
                                                                    .onUserCancel(3);
                                                            SecureFolderSysUiClientHelper
                                                                    secureFolderSysUiClientHelper22 =
                                                                            secureFolderSysUiClientHelper3;
                                                            Context context =
                                                                    secureFolderSysUiClientHelper22
                                                                            .mContext;
                                                            secureFolderSysUiClientHelper22
                                                                    .getClass();
                                                            Log.d(
                                                                    "KKG::SecureFolderSysUiClientHelper",
                                                                    "removeSecureFolder()");
                                                            PromptConfig promptConfig =
                                                                    secureFolderSysUiClientHelper22
                                                                            .mPromptConfig;
                                                            int i52 = promptConfig.mUserId;
                                                            if (i52 <= 0) {
                                                                Log.d(
                                                                        "KKG::SecureFolderSysUiClientHelper",
                                                                        "removeSecureFolder()."
                                                                            + " Incorrect User ID :"
                                                                            + " "
                                                                                + i52);
                                                                break;
                                                            } else {
                                                                Log.d(
                                                                        "KKG::SecureFolderSysUiClientHelper",
                                                                        "setActiveAdminIfNeeded");
                                                                ComponentName componentName =
                                                                        new ComponentName(
                                                                                "com.samsung.knox.securefolder",
                                                                                "com.samsung.knox.securefolder.containeragent.detector.KnoxDeviceAdminReceiver");
                                                                DevicePolicyManager
                                                                        devicePolicyManager =
                                                                                (DevicePolicyManager)
                                                                                        context
                                                                                                .getSystemService(
                                                                                                        "device_policy");
                                                                if (devicePolicyManager == null) {
                                                                    Log.i(
                                                                            "KKG::SecureFolderSysUiClientHelper",
                                                                            "DevicePolicyManager is"
                                                                                + " null");
                                                                    isAdminActive = false;
                                                                } else {
                                                                    isAdminActive =
                                                                            devicePolicyManager
                                                                                    .isAdminActive(
                                                                                            componentName);
                                                                }
                                                                if (!isAdminActive) {
                                                                    Log.d(
                                                                            "KKG::SecureFolderSysUiClientHelper",
                                                                            "setActiveAdminIfNeeded():"
                                                                                + " Setting active"
                                                                                + " admin");
                                                                    DevicePolicyManager
                                                                            devicePolicyManager2 =
                                                                                    (DevicePolicyManager)
                                                                                            context
                                                                                                    .getSystemService(
                                                                                                            "device_policy");
                                                                    if (devicePolicyManager2
                                                                            == null) {
                                                                        Log.i(
                                                                                "KKG::SecureFolderSysUiClientHelper",
                                                                                "DevicePolicyManager"
                                                                                    + " is null");
                                                                    } else {
                                                                        try {
                                                                            devicePolicyManager2
                                                                                    .setActiveAdmin(
                                                                                            componentName,
                                                                                            false,
                                                                                            promptConfig
                                                                                                    .mUserId);
                                                                        } catch (Exception e22) {
                                                                            Log.d(
                                                                                    "KKG::SecureFolderSysUiClientHelper",
                                                                                    "Error setting"
                                                                                        + " active"
                                                                                        + " admin :"
                                                                                        + " "
                                                                                            + e22
                                                                                                    .getMessage());
                                                                        }
                                                                    }
                                                                }
                                                                Settings.Secure.putIntForUser(
                                                                        context
                                                                                .getContentResolver(),
                                                                        "DelFlag",
                                                                        1,
                                                                        0);
                                                                Settings.Secure.putStringForUser(
                                                                        context
                                                                                .getContentResolver(),
                                                                        "secure_folder_image_name",
                                                                        "sf_app_icon_00",
                                                                        0);
                                                                Settings.Secure.putStringForUser(
                                                                        context
                                                                                .getContentResolver(),
                                                                        "secure_folder_name",
                                                                        null,
                                                                        0);
                                                                try {
                                                                    ((UserManager)
                                                                                    context.createPackageContextAsUser(
                                                                                                    "com.samsung.knox.securefolder",
                                                                                                    0,
                                                                                                    UserHandle
                                                                                                            .of(
                                                                                                                    i52))
                                                                                            .getSystemService(
                                                                                                    "user"))
                                                                            .removeUser(i52);
                                                                    break;
                                                                } catch (Exception e3) {
                                                                    Log.d(
                                                                            "KKG::SecureFolderSysUiClientHelper",
                                                                            "removeSecureFolderUser()."
                                                                                + " Exception : "
                                                                                    + e3
                                                                                            .getMessage());
                                                                    return;
                                                                }
                                                            }
                                                        default:
                                                            secureFolderSysUiClientHelper3
                                                                    .mKnoxEventList.add(
                                                                    KnoxSamsungAnalyticsLogger
                                                                            .addEvent(
                                                                                    183, 1832,
                                                                                    null));
                                                            dialogInterface.dismiss();
                                                            break;
                                                    }
                                                }
                                            });
                                    final int i6 = 3;
                                    builder2.setNegativeButton(
                                            R.string.cancel,
                                            new DialogInterface.OnClickListener() { // from class:
                                                // com.samsung.android.biometrics.app.setting.prompt.knox.SecureFolderSysUiClientHelper.5
                                                @Override // android.content.DialogInterface.OnClickListener
                                                public final void onClick(
                                                        DialogInterface dialogInterface, int i42) {
                                                    boolean isAdminActive;
                                                    switch (i6) {
                                                        case 0:
                                                            secureFolderSysUiClientHelper3
                                                                    .mKnoxEventList.add(
                                                                    KnoxSamsungAnalyticsLogger
                                                                            .addEvent(
                                                                                    100, 1003,
                                                                                    null));
                                                            if (!((ActivityManager)
                                                                            secureFolderSysUiClientHelper3
                                                                                    .mContext
                                                                                    .getSystemService(
                                                                                            "activity"))
                                                                    .isInLockTaskMode()) {
                                                                ((BiometricPromptClient)
                                                                                secureFolderSysUiClientHelper3
                                                                                        .mPromptConfig
                                                                                        .mCallback)
                                                                        .onUserCancel(3);
                                                                secureFolderSysUiClientHelper3
                                                                        .showSFLockedView(
                                                                                true, false);
                                                                dialogInterface.dismiss();
                                                                break;
                                                            } else {
                                                                Toast.makeText(
                                                                                secureFolderSysUiClientHelper3
                                                                                        .mContext,
                                                                                R.string
                                                                                        .lock_to_app_toast,
                                                                                1)
                                                                        .show();
                                                                break;
                                                            }
                                                        case 1:
                                                            secureFolderSysUiClientHelper3
                                                                    .mKnoxEventList.add(
                                                                    KnoxSamsungAnalyticsLogger
                                                                            .addEvent(
                                                                                    100, 1002,
                                                                                    null));
                                                            dialogInterface.dismiss();
                                                            break;
                                                        case 2:
                                                            secureFolderSysUiClientHelper3
                                                                    .mKnoxEventList.add(
                                                                    KnoxSamsungAnalyticsLogger
                                                                            .addEvent(
                                                                                    183, 1833,
                                                                                    null));
                                                            secureFolderSysUiClientHelper3
                                                                    .mAlertDialog
                                                                    .getButton(-1)
                                                                    .setEnabled(false);
                                                            ((BiometricPromptClient)
                                                                            secureFolderSysUiClientHelper3
                                                                                    .mPromptConfig
                                                                                    .mCallback)
                                                                    .onUserCancel(3);
                                                            SecureFolderSysUiClientHelper
                                                                    secureFolderSysUiClientHelper22 =
                                                                            secureFolderSysUiClientHelper3;
                                                            Context context =
                                                                    secureFolderSysUiClientHelper22
                                                                            .mContext;
                                                            secureFolderSysUiClientHelper22
                                                                    .getClass();
                                                            Log.d(
                                                                    "KKG::SecureFolderSysUiClientHelper",
                                                                    "removeSecureFolder()");
                                                            PromptConfig promptConfig =
                                                                    secureFolderSysUiClientHelper22
                                                                            .mPromptConfig;
                                                            int i52 = promptConfig.mUserId;
                                                            if (i52 <= 0) {
                                                                Log.d(
                                                                        "KKG::SecureFolderSysUiClientHelper",
                                                                        "removeSecureFolder()."
                                                                            + " Incorrect User ID :"
                                                                            + " "
                                                                                + i52);
                                                                break;
                                                            } else {
                                                                Log.d(
                                                                        "KKG::SecureFolderSysUiClientHelper",
                                                                        "setActiveAdminIfNeeded");
                                                                ComponentName componentName =
                                                                        new ComponentName(
                                                                                "com.samsung.knox.securefolder",
                                                                                "com.samsung.knox.securefolder.containeragent.detector.KnoxDeviceAdminReceiver");
                                                                DevicePolicyManager
                                                                        devicePolicyManager =
                                                                                (DevicePolicyManager)
                                                                                        context
                                                                                                .getSystemService(
                                                                                                        "device_policy");
                                                                if (devicePolicyManager == null) {
                                                                    Log.i(
                                                                            "KKG::SecureFolderSysUiClientHelper",
                                                                            "DevicePolicyManager is"
                                                                                + " null");
                                                                    isAdminActive = false;
                                                                } else {
                                                                    isAdminActive =
                                                                            devicePolicyManager
                                                                                    .isAdminActive(
                                                                                            componentName);
                                                                }
                                                                if (!isAdminActive) {
                                                                    Log.d(
                                                                            "KKG::SecureFolderSysUiClientHelper",
                                                                            "setActiveAdminIfNeeded():"
                                                                                + " Setting active"
                                                                                + " admin");
                                                                    DevicePolicyManager
                                                                            devicePolicyManager2 =
                                                                                    (DevicePolicyManager)
                                                                                            context
                                                                                                    .getSystemService(
                                                                                                            "device_policy");
                                                                    if (devicePolicyManager2
                                                                            == null) {
                                                                        Log.i(
                                                                                "KKG::SecureFolderSysUiClientHelper",
                                                                                "DevicePolicyManager"
                                                                                    + " is null");
                                                                    } else {
                                                                        try {
                                                                            devicePolicyManager2
                                                                                    .setActiveAdmin(
                                                                                            componentName,
                                                                                            false,
                                                                                            promptConfig
                                                                                                    .mUserId);
                                                                        } catch (Exception e22) {
                                                                            Log.d(
                                                                                    "KKG::SecureFolderSysUiClientHelper",
                                                                                    "Error setting"
                                                                                        + " active"
                                                                                        + " admin :"
                                                                                        + " "
                                                                                            + e22
                                                                                                    .getMessage());
                                                                        }
                                                                    }
                                                                }
                                                                Settings.Secure.putIntForUser(
                                                                        context
                                                                                .getContentResolver(),
                                                                        "DelFlag",
                                                                        1,
                                                                        0);
                                                                Settings.Secure.putStringForUser(
                                                                        context
                                                                                .getContentResolver(),
                                                                        "secure_folder_image_name",
                                                                        "sf_app_icon_00",
                                                                        0);
                                                                Settings.Secure.putStringForUser(
                                                                        context
                                                                                .getContentResolver(),
                                                                        "secure_folder_name",
                                                                        null,
                                                                        0);
                                                                try {
                                                                    ((UserManager)
                                                                                    context.createPackageContextAsUser(
                                                                                                    "com.samsung.knox.securefolder",
                                                                                                    0,
                                                                                                    UserHandle
                                                                                                            .of(
                                                                                                                    i52))
                                                                                            .getSystemService(
                                                                                                    "user"))
                                                                            .removeUser(i52);
                                                                    break;
                                                                } catch (Exception e3) {
                                                                    Log.d(
                                                                            "KKG::SecureFolderSysUiClientHelper",
                                                                            "removeSecureFolderUser()."
                                                                                + " Exception : "
                                                                                    + e3
                                                                                            .getMessage());
                                                                    return;
                                                                }
                                                            }
                                                        default:
                                                            secureFolderSysUiClientHelper3
                                                                    .mKnoxEventList.add(
                                                                    KnoxSamsungAnalyticsLogger
                                                                            .addEvent(
                                                                                    183, 1832,
                                                                                    null));
                                                            dialogInterface.dismiss();
                                                            break;
                                                    }
                                                }
                                            });
                                    AlertDialog create2 = builder2.create();
                                    secureFolderSysUiClientHelper3.mAlertDialog = create2;
                                    Window window2 = create2.getWindow();
                                    window2.setType(2017);
                                    window2.setGravity(80);
                                    secureFolderSysUiClientHelper3.mAlertDialog
                                            .setCanceledOnTouchOutside(true);
                                    secureFolderSysUiClientHelper3.mAlertDialog.show();
                                    break;
                            }
                        }
                    });
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final void setBiometricAttemptDeadline(int i) {
        this.mLockPatternUtils.setBiometricAttemptDeadline(this.mPromptConfig.mUserId, i);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final void setDetailText(TextView textView) {
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        PromptConfig promptConfig = this.mPromptConfig;
        int currentFailedPasswordAttempts =
                devicePolicyManager.getCurrentFailedPasswordAttempts(promptConfig.mUserId);
        int keyguardStoredPasswordQuality =
                this.mLockPatternUtils.getKeyguardStoredPasswordQuality(promptConfig.mUserId);
        int i = 15 - currentFailedPasswordAttempts;
        int[][] iArr = DETAIL_TEXTS;
        String string =
                keyguardStoredPasswordQuality != 65536
                        ? (keyguardStoredPasswordQuality == 131072
                                        || keyguardStoredPasswordQuality == 196608)
                                ? (!isResetWithSamsungAccountEnable()
                                                || currentFailedPasswordAttempts < 10)
                                        ? this.mContext.getResources().getString(iArr[1][0])
                                        : this.mContext
                                                .getResources()
                                                .getQuantityString(
                                                        R.plurals.sec_incorrect_pin_attempts_left,
                                                        i,
                                                        Integer.valueOf(i))
                                : (keyguardStoredPasswordQuality == 262144
                                                || keyguardStoredPasswordQuality == 327680
                                                || keyguardStoredPasswordQuality == 393216)
                                        ? (!isResetWithSamsungAccountEnable()
                                                        || currentFailedPasswordAttempts < 10)
                                                ? this.mContext.getResources().getString(iArr[2][0])
                                                : this.mContext
                                                        .getResources()
                                                        .getQuantityString(
                                                                R.plurals
                                                                        .sec_incorrect_password_attempts_left,
                                                                i,
                                                                Integer.valueOf(i))
                                        : ""
                        : (!isResetWithSamsungAccountEnable() || currentFailedPasswordAttempts < 10)
                                ? this.mContext.getResources().getString(iArr[0][0])
                                : this.mContext
                                        .getResources()
                                        .getQuantityString(
                                                R.plurals.sec_incorrect_pattern_attempts_left,
                                                i,
                                                Integer.valueOf(i));
        if (textView != null) {
            textView.setText(string);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final void setErrorTimerText(TextView textView, long j) {
        int round = (Math.round(j / 1000) % 60) + 1;
        int floor = (((int) Math.floor(j / 60000)) % 60) + 1;
        if (round <= 0) {
            return;
        }
        if (floor > 1) {
            textView.setText(
                    this.mContext
                            .getResources()
                            .getQuantityString(
                                    R.plurals
                                            .sec_lockpattern_too_many_failed_confirmation_attempts_footer_min,
                                    floor,
                                    Integer.valueOf(floor)));
        } else if (round == 60) {
            textView.setText(
                    this.mContext
                            .getResources()
                            .getQuantityString(
                                    R.plurals
                                            .sec_lockpattern_too_many_failed_confirmation_attempts_footer_min,
                                    floor,
                                    Integer.valueOf(floor)));
        } else {
            textView.setText(
                    this.mContext
                            .getResources()
                            .getQuantityString(
                                    R.plurals
                                            .sec_lockpattern_too_many_failed_confirmation_attempts_footer_sec,
                                    round,
                                    Integer.valueOf(round)));
        }
    }

    public final void setTextOrHide(
            TextView textView,
            CharSequence charSequence,
            KnoxAuthCredentialView knoxAuthCredentialView) {
        if (TextUtils.isEmpty(charSequence)) {
            textView.setVisibility(8);
        } else {
            textView.setText(charSequence);
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, knoxAuthCredentialView);
    }

    public final void showSFLockedView(boolean z, boolean z2) {
        Intent intent = new Intent();
        intent.setClassName(
                "com.android.settings", "com.samsung.android.settings.knox.SecureFolderLockedView");
        intent.addFlags(268468224);
        intent.putExtra("fromResetBtn", z);
        intent.putExtra("wasLastAttempt", z2);
        intent.putExtra("userId", this.mPromptConfig.mUserId);
        try {
            this.mContext.startActivity(intent);
        } catch (Exception e) {
            Log.e(
                    "KKG::SecureFolderSysUiClientHelper",
                    "Exception while launching secure folder locked view : " + e.getMessage());
        }
    }
}
