package com.samsung.android.biometrics.app.setting.prompt.knox;

import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.UserManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.view.menu.SubMenuBuilder$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockPatternView;

import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.ResourceManager;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

public final class WorkProfileSysUiClientHelper implements KnoxSysUiClientHelper {
    public static final int[][] DETAIL_TEXTS = {
        new int[] {R.string.sec_draw_pattern_WC},
        new int[] {R.string.sec_enter_pin_WC},
        new int[] {R.string.sec_enter_password_WC}
    };
    public final LayoutInflater layoutInflater;
    public final AccessibilityManager mAccessibilityManager;
    public AlertDialog mAlertDialog;
    public final Context mContext;
    public final DevicePolicyManager mDevicePolicyManager;
    public boolean mIsPasswordShown;
    public final LockPatternUtils mLockPatternUtils;
    public final PromptConfig mPromptConfig;
    public final UserManager mUserManager;

    /* renamed from: com.samsung.android.biometrics.app.setting.prompt.knox.WorkProfileSysUiClientHelper$1, reason: invalid class name */
    public final class AnonymousClass1 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    public WorkProfileSysUiClientHelper(Context context, PromptConfig promptConfig) {
        this.mContext = context;
        this.mPromptConfig = promptConfig;
        this.layoutInflater = LayoutInflater.from(context);
        this.mAccessibilityManager =
                (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        this.mUserManager = (UserManager) context.getSystemService("user");
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView
            changeCredentialViewIfNeeded(android.view.View r21) {
        /*
            Method dump skipped, instructions count: 978
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.biometrics.app.setting.prompt.knox.WorkProfileSysUiClientHelper.changeCredentialViewIfNeeded(android.view.View):com.samsung.android.biometrics.app.setting.prompt.knox.KnoxAuthCredentialView");
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final TextView getDetailsTextView(KnoxAuthCredentialView knoxAuthCredentialView) {
        return (TextView) knoxAuthCredentialView.findViewById(R.id.detailsText);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final String getErrorMessage() {
        int keyguardStoredPasswordQuality =
                this.mLockPatternUtils.getKeyguardStoredPasswordQuality(this.mPromptConfig.mUserId);
        if (keyguardStoredPasswordQuality == 65536) {
            return this.mContext.getString(R.string.cryptkeeper_wrong_pattern);
        }
        if (keyguardStoredPasswordQuality != 131072 && keyguardStoredPasswordQuality != 196608) {
            if (keyguardStoredPasswordQuality != 262144
                    && keyguardStoredPasswordQuality != 327680
                    && keyguardStoredPasswordQuality != 393216) {
                if (keyguardStoredPasswordQuality != 458752) {
                    if (keyguardStoredPasswordQuality != 524288) {
                        return "";
                    }
                }
            }
            return this.mContext.getString(R.string.cryptkeeper_wrong_password);
        }
        return this.mContext.getString(R.string.cryptkeeper_wrong_pin);
    }

    public final String getWPCMessage(int i, int i2) {
        return i == 1
                ? i2 == 1
                        ? KnoxUtils.isTablet()
                                ? String.format(
                                        String.format(
                                                this.mContext.getString(
                                                        R.string.wpc_tablet_incorrect_dialog_1),
                                                new Object[0]),
                                        new Object[0])
                                : String.format(
                                        this.mContext.getString(
                                                R.string.wpc_phone_incorrect_dialog_1),
                                        new Object[0])
                        : KnoxUtils.isTablet()
                                ? this.mContext
                                        .getResources()
                                        .getQuantityString(
                                                R.plurals.wpc_tablet_incorrect_dialog_2,
                                                i,
                                                Integer.valueOf(i2))
                                : this.mContext
                                        .getResources()
                                        .getQuantityString(
                                                R.plurals.wpc_phone_incorrect_dialog_2,
                                                i,
                                                Integer.valueOf(i2))
                : i2 == 1
                        ? KnoxUtils.isTablet()
                                ? this.mContext
                                        .getResources()
                                        .getQuantityString(
                                                R.plurals.wpc_tablet_incorrect_dialog_4,
                                                i,
                                                Integer.valueOf(i))
                                : this.mContext
                                        .getResources()
                                        .getQuantityString(
                                                R.plurals.wpc_phone_incorrect_dialog_4,
                                                i,
                                                Integer.valueOf(i))
                        : KnoxUtils.isTablet()
                                ? String.format(
                                        this.mContext.getString(
                                                R.string.wpc_tablet_incorrect_dialog_3),
                                        Integer.valueOf(i),
                                        Integer.valueOf(i2))
                                : String.format(
                                        this.mContext.getString(
                                                R.string.wpc_phone_incorrect_dialog_3),
                                        Integer.valueOf(i),
                                        Integer.valueOf(i2));
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final boolean isForgotbtnDialogShowing() {
        AlertDialog alertDialog = this.mAlertDialog;
        return alertDialog != null && alertDialog.isShowing();
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
    public final void onAttachedToWindow(
            KnoxAuthCredentialView knoxAuthCredentialView,
            TextView textView,
            TextView textView2,
            TextView textView3,
            ImageView imageView) {
        Drawable drawable;
        TextView textView4 = (TextView) knoxAuthCredentialView.findViewById(R.id.knoxTitleText);
        if (textView4 != null) {
            textView4.setText(
                    this.mContext.getString(R.string.biometric_prompt_default_title_work_profile));
        }
        PromptConfig promptConfig = this.mPromptConfig;
        if (promptConfig.mPromptInfo.isUseDefaultTitle()
                && this.mContext
                        .getString(R.string.biometric_prompt_default_title)
                        .equals(promptConfig.mPromptInfo.getTitle())) {
            setTextOrHide$1(textView, null, knoxAuthCredentialView);
        } else {
            setTextOrHide$1(textView, promptConfig.mPromptInfo.getTitle(), knoxAuthCredentialView);
        }
        setTextOrHide$1(textView2, promptConfig.mPromptInfo.getSubtitle(), knoxAuthCredentialView);
        setTextOrHide$1(
                textView3, promptConfig.mPromptInfo.getDescription(), knoxAuthCredentialView);
        if (imageView == null
                || (drawable =
                                new ResourceManager(this.mContext, "com.android.settings")
                                        .getDrawable("knox_basic"))
                        == null) {
            return;
        }
        drawable.setTint(-1);
        imageView.setImageDrawable(drawable);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCredentialVerified(
            int r20, boolean r21, android.view.View r22, int r23, android.widget.TextView r24) {
        /*
            Method dump skipped, instructions count: 792
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.biometrics.app.setting.prompt.knox.WorkProfileSysUiClientHelper.onCredentialVerified(int,"
                    + " boolean, android.view.View, int, android.widget.TextView):void");
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final void onDetachedFromWindow() {
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mAlertDialog.dismiss();
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
                                         // com.samsung.android.biometrics.app.setting.prompt.knox.WorkProfileSysUiClientHelper$$ExternalSyntheticLambda0
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
                                         // com.samsung.android.biometrics.app.setting.prompt.knox.WorkProfileSysUiClientHelper$$ExternalSyntheticLambda0
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

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final void setBiometricAttemptDeadline(int i) {
        this.mLockPatternUtils.setBiometricAttemptDeadline(this.mPromptConfig.mUserId, i);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final void setDetailText(TextView textView) {
        int keyguardStoredPasswordQuality =
                this.mLockPatternUtils.getKeyguardStoredPasswordQuality(this.mPromptConfig.mUserId);
        int[][] iArr = DETAIL_TEXTS;
        String string =
                keyguardStoredPasswordQuality != 65536
                        ? (keyguardStoredPasswordQuality == 131072
                                        || keyguardStoredPasswordQuality == 196608)
                                ? this.mContext.getResources().getString(iArr[1][0])
                                : (keyguardStoredPasswordQuality == 262144
                                                || keyguardStoredPasswordQuality == 327680
                                                || keyguardStoredPasswordQuality == 393216)
                                        ? this.mContext.getResources().getString(iArr[2][0])
                                        : ""
                        : this.mContext.getResources().getString(iArr[0][0]);
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

    public final void setTextOrHide$1(
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

    @Override // com.samsung.android.biometrics.app.setting.prompt.knox.KnoxSysUiClientHelper
    public final void onConfigurationChanged() {}
}
