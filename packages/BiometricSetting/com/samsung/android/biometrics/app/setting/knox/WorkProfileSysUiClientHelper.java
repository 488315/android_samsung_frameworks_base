package com.samsung.android.biometrics.app.setting.knox;

import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.UserInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.view.menu.SubMenuBuilder$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockPatternView;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.ResourceManager;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;
import com.samsung.android.knox.ContainerProxy;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;
import java.io.ByteArrayOutputStream;

/* loaded from: classes.dex */
public final class WorkProfileSysUiClientHelper implements KnoxSysUiClientHelper {
    private static final int[][] DETAIL_TEXTS = {new int[]{R.string.sec_draw_pattern_WC}, new int[]{R.string.sec_enter_pin_WC}, new int[]{R.string.sec_enter_password_WC}};
    private LayoutInflater layoutInflater;
    private final AccessibilityManager mAccessibilityManager;
    private AlertDialog mAlertDialog;
    private Context mContext;
    private DevicePolicyManager mDevicePolicyManager;
    private boolean mIsPasswordShown;
    private LockPatternUtils mLockPatternUtils;
    private final PromptConfig mPromptConfig;
    private UserManager mUserManager;

    /* renamed from: com.samsung.android.biometrics.app.setting.knox.WorkProfileSysUiClientHelper$1 */
    final class DialogInterfaceOnClickListenerC02881 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    /* renamed from: -$$Nest$misInLandscapeMode, reason: not valid java name */
    static boolean m239$$Nest$misInLandscapeMode(WorkProfileSysUiClientHelper workProfileSysUiClientHelper) {
        int displayRotation = KnoxUtils.getDisplayRotation(workProfileSysUiClientHelper.mContext);
        return displayRotation == 1 || displayRotation == 3;
    }

    public WorkProfileSysUiClientHelper(Context context, PromptConfig promptConfig) {
        this.mContext = context;
        this.mPromptConfig = promptConfig;
        this.layoutInflater = LayoutInflater.from(context);
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        this.mUserManager = (UserManager) context.getSystemService("user");
    }

    private void setTextOrHide(TextView textView, CharSequence charSequence, KnoxAuthCredentialView knoxAuthCredentialView) {
        if (TextUtils.isEmpty(charSequence)) {
            textView.setVisibility(8);
        } else {
            textView.setText(charSequence);
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, knoxAuthCredentialView);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x038c  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x03bf  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x03b2  */
    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final KnoxAuthCredentialView changeCredentialViewIfNeeded(View view) {
        int i;
        KnoxAuthCredentialView knoxAuthCredentialView;
        TextView textView;
        int i2;
        int i3;
        View findViewById;
        PromptConfig promptConfig = this.mPromptConfig;
        int credentialType = promptConfig.getCredentialType();
        if (credentialType != 1) {
            if (credentialType == 2) {
                knoxAuthCredentialView = (KnoxAuthCredentialView) this.layoutInflater.inflate(R.layout.sec_knox_biometric_prompt_credential_pattern, (ViewGroup) null, false);
                Configuration configuration = this.mContext.getResources().getConfiguration();
                if (configuration.orientation == 2) {
                    knoxAuthCredentialView.setOrientation(0);
                } else {
                    knoxAuthCredentialView.setOrientation(1);
                }
                RelativeLayout relativeLayout = (RelativeLayout) knoxAuthCredentialView.findViewById(R.id.parent_icon_layout);
                RelativeLayout relativeLayout2 = (RelativeLayout) knoxAuthCredentialView.findViewById(R.id.pwdLayoutScrollView);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) relativeLayout2.getLayoutParams();
                ImageView imageView = (ImageView) knoxAuthCredentialView.findViewById(R.id.icon);
                LinearLayout linearLayout = (LinearLayout) knoxAuthCredentialView.findViewById(R.id.pwdLayout);
                LinearLayout linearLayout2 = (LinearLayout) knoxAuthCredentialView.findViewById(R.id.detailsTextLayout);
                RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) linearLayout2.getLayoutParams();
                LinearLayout linearLayout3 = (LinearLayout) knoxAuthCredentialView.findViewById(R.id.secure_by_knox_logo_portrait);
                LinearLayout linearLayout4 = (LinearLayout) knoxAuthCredentialView.findViewById(R.id.secure_by_knox_logo_landscape);
                if (configuration.orientation == 2) {
                    layoutParams3.topMargin = 0;
                    layoutParams3.addRule(13);
                    linearLayout3.setVisibility(8);
                    linearLayout4.setVisibility(0);
                    linearLayout.setGravity(17);
                    layoutParams4.addRule(3, R.id.knoxTitleText);
                    layoutParams4.addRule(13);
                    layoutParams4.removeRule(12);
                    layoutParams.weight = 0.5f;
                    layoutParams2.weight = 0.5f;
                    knoxAuthCredentialView.setPadding(0, 0, KnoxUtils.getNavigationBarSize(this.mContext), (KnoxUtils.isFoldableProduct() && configuration.semDisplayDeviceType == 0) ? KnoxUtils.getNavigationBarSize(this.mContext) : 0);
                } else {
                    layoutParams3.topMargin = (int) this.mContext.getResources().getDimension(R.dimen.knox_logo_confirm_credential_top_margin);
                    linearLayout4.setVisibility(8);
                    linearLayout3.setVisibility(0);
                    layoutParams3.addRule(14);
                    layoutParams3.addRule(10);
                    layoutParams3.removeRule(13);
                    layoutParams4.removeRule(3);
                    layoutParams4.addRule(12);
                    layoutParams4.removeRule(13);
                    linearLayout.setGravity(80);
                    float adjustBiometricViewWeightsForPatternPortrait = KnoxUtils.adjustBiometricViewWeightsForPatternPortrait();
                    layoutParams.weight = adjustBiometricViewWeightsForPatternPortrait;
                    layoutParams2.weight = 1.0f - adjustBiometricViewWeightsForPatternPortrait;
                    knoxAuthCredentialView.setPadding(0, 0, 0, KnoxUtils.getNavigationBarSize(this.mContext));
                }
                i = 2;
                if (promptConfig.isKnoxManagedProfile() && (findViewById = view.findViewById(R.id.panel)) != null) {
                    findViewById.setBackground(this.mContext.getDrawable(R.drawable.sec_knox_credential_bg));
                }
                textView = (TextView) knoxAuthCredentialView.findViewById(R.id.detailsText);
                if (textView == null) {
                    int credentialType2 = promptConfig.getCredentialType();
                    int[][] iArr = DETAIL_TEXTS;
                    if (credentialType2 == 1) {
                        i2 = 0;
                        i3 = iArr[1][0];
                    } else if (credentialType2 == i) {
                        i2 = 0;
                        i3 = iArr[0][0];
                    } else if (credentialType2 != 3) {
                        i3 = 0;
                        i2 = 0;
                    } else {
                        i2 = 0;
                        i3 = iArr[i][0];
                    }
                    textView.setText(i3);
                } else {
                    i2 = 0;
                }
                if (KnoxUtils.isMultifactorEnabledForWork(this.mContext, promptConfig.getUserId())) {
                    ((ImageView) knoxAuthCredentialView.findViewById(R.id.knox_two_step_image)).setVisibility(i2);
                }
                return knoxAuthCredentialView;
            }
            if (credentialType != 3) {
                if (credentialType != 6) {
                    throw new IllegalStateException("Unknown credential type: " + promptConfig.getCredentialType());
                }
                Log.i("KKG::WorkProfileSysUiClientHelper", "WorkProfileSysUiClientHelper: UCMKeyguardEnabled");
            }
        }
        final KnoxAuthCredentialView knoxAuthCredentialView2 = (KnoxAuthCredentialView) this.layoutInflater.inflate(R.layout.sec_knox_biometric_prompt_credential_password, (ViewGroup) null, false);
        final EditText editText = (EditText) knoxAuthCredentialView2.findViewById(R.id.lockPassword);
        final ImageButton imageButton = (ImageButton) knoxAuthCredentialView2.findViewById(R.id.password_show_btn);
        if (editText != null && imageButton != null) {
            editText.setPrivateImeOptions(editText.getPrivateImeOptions() + ";disableToolbar=true;lockScreenPasswordField=true");
            if (this.mUserManager.isUserUnlocked(UserHandle.of(promptConfig.getUserId()))) {
                editText.setTextOperationUser(UserHandle.of(promptConfig.getUserId()));
            }
            if (promptConfig.getCredentialType() == 3) {
                PasswordPolicy passwordPolicy = KnoxUtils.getPasswordPolicy(this.mContext, promptConfig.getUserId());
                if (passwordPolicy == null || passwordPolicy.isPasswordVisibilityEnabled()) {
                    imageButton.setVisibility(0);
                } else {
                    imageButton.setVisibility(8);
                }
                imageButton.setForeground(this.mContext.getResources().getDrawable(R.drawable.lock_password_btn_password_hide_mtrl, null));
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imageButton.setContentDescription(this.mContext.getString(R.string.sec_lockpassword_show_button));
                imageButton.setImageTintList(ColorStateList.valueOf(this.mContext.getResources().getColor(R.color.body_text_color_light, null)));
                imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.knox.WorkProfileSysUiClientHelper.3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        int selectionEnd = editText.getSelectionEnd();
                        if (WorkProfileSysUiClientHelper.this.mIsPasswordShown) {
                            imageButton.setForeground(WorkProfileSysUiClientHelper.this.mContext.getResources().getDrawable(R.drawable.lock_password_btn_password_hide_mtrl, null));
                            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            imageButton.setContentDescription(WorkProfileSysUiClientHelper.this.mContext.getString(R.string.sec_lockpassword_show_button));
                            WorkProfileSysUiClientHelper.this.mIsPasswordShown = false;
                        } else {
                            imageButton.setForeground(WorkProfileSysUiClientHelper.this.mContext.getResources().getDrawable(R.drawable.lock_password_btn_password_show_mtrl, null));
                            editText.setTransformationMethod(null);
                            imageButton.setContentDescription(WorkProfileSysUiClientHelper.this.mContext.getString(R.string.sec_lockpassword_hide_button));
                            WorkProfileSysUiClientHelper.this.mIsPasswordShown = true;
                        }
                        try {
                            editText.setSelection(selectionEnd);
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                imageButton.setVisibility(8);
            }
        }
        Configuration configuration2 = this.mContext.getResources().getConfiguration();
        if (configuration2.orientation == 2) {
            knoxAuthCredentialView2.setOrientation(0);
        } else {
            knoxAuthCredentialView2.setOrientation(1);
        }
        RelativeLayout relativeLayout3 = (RelativeLayout) knoxAuthCredentialView2.findViewById(R.id.parent_icon_layout);
        ImageView imageView2 = (ImageView) knoxAuthCredentialView2.findViewById(R.id.icon);
        LinearLayout linearLayout5 = (LinearLayout) knoxAuthCredentialView2.findViewById(R.id.pwdLayout);
        ScrollView scrollView = (ScrollView) knoxAuthCredentialView2.findViewById(R.id.pwdLayoutScrollView);
        LinearLayout linearLayout6 = (LinearLayout) knoxAuthCredentialView2.findViewById(R.id.detailsTextLayout);
        ViewGroup.LayoutParams layoutParams5 = ((RelativeLayout) knoxAuthCredentialView2.findViewById(R.id.password_view)).getLayoutParams();
        LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) relativeLayout3.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams7 = (RelativeLayout.LayoutParams) imageView2.getLayoutParams();
        LinearLayout.LayoutParams layoutParams8 = (LinearLayout.LayoutParams) scrollView.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams9 = (RelativeLayout.LayoutParams) linearLayout6.getLayoutParams();
        if (configuration2.orientation == 2) {
            layoutParams6.height = -1;
            layoutParams7.topMargin = 0;
            layoutParams6.weight = 0.5f;
            layoutParams8.weight = 0.5f;
            layoutParams7.addRule(13);
            layoutParams7.removeRule(10);
            linearLayout5.setGravity(17);
            layoutParams9.addRule(13);
            layoutParams9.removeRule(12);
            knoxAuthCredentialView2.setPadding(0, 0, KnoxUtils.getNavigationBarSize(this.mContext), (KnoxUtils.isFoldableProduct() && configuration2.semDisplayDeviceType == 0) ? KnoxUtils.getNavigationBarSize(this.mContext) : 0);
            layoutParams5.width = -1;
        } else {
            layoutParams6.height = -1;
            layoutParams7.topMargin = (int) this.mContext.getResources().getDimension(R.dimen.knox_logo_confirm_credential_top_margin);
            linearLayout5.setGravity(80);
            layoutParams7.addRule(10);
            layoutParams7.removeRule(13);
            layoutParams6.weight = 0.25f;
            layoutParams8.weight = 0.75f;
            layoutParams9.addRule(12);
            layoutParams9.removeRule(13);
            knoxAuthCredentialView2.setPadding(0, 0, 0, KnoxUtils.getNavigationBarSize(this.mContext));
            if (KnoxUtils.isFoldableProduct() && configuration2.semDisplayDeviceType == 0) {
                layoutParams5.width = (int) (Utils.getDisplayMetrics(this.mContext).widthPixels * 0.7d);
            } else if (KnoxUtils.isTablet()) {
                layoutParams5.width = (int) (Utils.getDisplayMetrics(this.mContext).widthPixels * 0.4d);
            } else {
                layoutParams5.width = -1;
            }
        }
        final ScrollView scrollView2 = (ScrollView) knoxAuthCredentialView2.findViewById(R.id.pwdLayoutScrollView);
        final ImageView imageView3 = (ImageView) knoxAuthCredentialView2.findViewById(R.id.icon);
        final LinearLayout linearLayout7 = (LinearLayout) knoxAuthCredentialView2.findViewById(R.id.pwdLayout);
        final RelativeLayout relativeLayout4 = (RelativeLayout) knoxAuthCredentialView2.findViewById(R.id.parent_icon_layout);
        final TextView textView2 = (TextView) knoxAuthCredentialView2.findViewById(R.id.knoxTitleText);
        final LinearLayout.LayoutParams layoutParams10 = (LinearLayout.LayoutParams) relativeLayout4.getLayoutParams();
        final LinearLayout.LayoutParams layoutParams11 = (LinearLayout.LayoutParams) scrollView2.getLayoutParams();
        final LinearLayout linearLayout8 = (LinearLayout) knoxAuthCredentialView2.findViewById(R.id.detailsTextLayout);
        ViewTreeObserver viewTreeObserver = knoxAuthCredentialView2.getViewTreeObserver();
        i = 2;
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.samsung.android.biometrics.app.setting.knox.WorkProfileSysUiClientHelper.4
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                if (knoxAuthCredentialView2 != null) {
                    Rect rect = new Rect();
                    scrollView2.getWindowVisibleDisplayFrame(rect);
                    int navigationBarSize = KnoxUtils.getNavigationBarSize(WorkProfileSysUiClientHelper.this.mContext);
                    int height = navigationBarSize > 0 ? (scrollView2.getRootView().getHeight() - rect.bottom) - navigationBarSize : scrollView2.getRootView().getHeight() - rect.bottom;
                    Log.d("KKG::WorkProfileSysUiClientHelper", "Keyboard height = " + height);
                    if (height <= 0) {
                        if (WorkProfileSysUiClientHelper.m239$$Nest$misInLandscapeMode(WorkProfileSysUiClientHelper.this)) {
                            navigationBarSize = 0;
                        }
                        linearLayout7.setPadding(0, 0, 0, navigationBarSize);
                        relativeLayout4.setPadding(0, 0, 0, 0);
                        imageView3.setVisibility(0);
                        textView2.setVisibility(0);
                        linearLayout8.setPadding(0, WorkProfileSysUiClientHelper.this.mContext.getResources().getDimensionPixelSize(R.dimen.knox_password_view_margin_end), 0, 0);
                        if (WorkProfileSysUiClientHelper.m239$$Nest$misInLandscapeMode(WorkProfileSysUiClientHelper.this)) {
                            layoutParams10.weight = 0.5f;
                            layoutParams11.weight = 0.5f;
                            return;
                        } else {
                            layoutParams10.weight = 0.2f;
                            layoutParams11.weight = 0.8f;
                            return;
                        }
                    }
                    if (!WorkProfileSysUiClientHelper.m239$$Nest$misInLandscapeMode(WorkProfileSysUiClientHelper.this)) {
                        layoutParams10.weight = 0.5f;
                        layoutParams11.weight = 0.5f;
                        linearLayout7.setPadding(0, 0, 0, height + 0);
                        relativeLayout4.setPadding(0, 0, 0, 0);
                        imageView3.setVisibility(0);
                        textView2.setVisibility(0);
                        linearLayout8.setPadding(0, 0, 0, 0);
                        return;
                    }
                    layoutParams10.weight = 0.5f;
                    layoutParams11.weight = 0.5f;
                    int i4 = height + 0;
                    linearLayout7.setPadding(0, 0, 0, i4);
                    relativeLayout4.setPadding(0, 0, 0, i4);
                    imageView3.setVisibility(8);
                    textView2.setVisibility(8);
                    linearLayout8.setPadding(0, 0, 0, 0);
                }
            }
        });
        knoxAuthCredentialView = knoxAuthCredentialView2;
        if (promptConfig.isKnoxManagedProfile()) {
            findViewById.setBackground(this.mContext.getDrawable(R.drawable.sec_knox_credential_bg));
        }
        textView = (TextView) knoxAuthCredentialView.findViewById(R.id.detailsText);
        if (textView == null) {
        }
        if (KnoxUtils.isMultifactorEnabledForWork(this.mContext, promptConfig.getUserId())) {
        }
        return knoxAuthCredentialView;
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final TextView getDetailsTextView(KnoxAuthCredentialView knoxAuthCredentialView) {
        return (TextView) knoxAuthCredentialView.findViewById(R.id.detailsText);
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final String getErrorMessage() {
        int keyguardStoredPasswordQuality = this.mLockPatternUtils.getKeyguardStoredPasswordQuality(this.mPromptConfig.getUserId());
        if (keyguardStoredPasswordQuality == 65536) {
            return this.mContext.getString(R.string.cryptkeeper_wrong_pattern);
        }
        if (keyguardStoredPasswordQuality != 131072 && keyguardStoredPasswordQuality != 196608) {
            if (keyguardStoredPasswordQuality != 262144 && keyguardStoredPasswordQuality != 327680 && keyguardStoredPasswordQuality != 393216) {
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

    public final void handleAuthenticationFailed() {
        Log.d("KKG::WorkProfileSysUiClientHelper", "onAuthenticationFailed");
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        PromptConfig promptConfig = this.mPromptConfig;
        devicePolicyManager.reportFailedBiometricAttempt(promptConfig.getUserId());
        int currentFailedBiometricAttempts = this.mDevicePolicyManager.getCurrentFailedBiometricAttempts(promptConfig.getUserId());
        if (currentFailedBiometricAttempts < 50) {
            if (currentFailedBiometricAttempts == 0 || currentFailedBiometricAttempts % 5 != 0) {
                return;
            }
            this.mLockPatternUtils.setBiometricAttemptDeadline(promptConfig.getUserId(), 30000);
            if (KnoxUtils.isMultifactorEnabledForWork(this.mContext, promptConfig.getUserId())) {
                promptConfig.getCallback().onDismissed(5, null);
                return;
            } else if (Utils.isDesktopMode(this.mContext)) {
                promptConfig.getCallback().onDismissed(5, null);
                return;
            } else {
                promptConfig.getCallback().onDeviceCredentialPressed();
                return;
            }
        }
        Log.d("KKG::WorkProfileSysUiClientHelper", "isBiometricDeadlineForWorkProfile ( too many failed. )");
        this.mLockPatternUtils.clearBiometricAttemptDeadline(promptConfig.getUserId());
        if (KnoxUtils.isMultifactorEnabledForWork(this.mContext, promptConfig.getUserId())) {
            int userId = promptConfig.getUserId();
            Bundle bundle = new Bundle();
            bundle.putInt("android.intent.extra.user_handle", userId);
            ContainerProxy.sendPolicyUpdate("knox.container.proxy.POLICY_ADMIN_LOCK", bundle);
            promptConfig.getCallback().onDismissed(5, null);
            return;
        }
        this.mLockPatternUtils.requireStrongAuth(4096, promptConfig.getUserId());
        if (Utils.isDesktopMode(this.mContext)) {
            promptConfig.getCallback().onDismissed(5, null);
        } else {
            promptConfig.getCallback().onDeviceCredentialPressed();
        }
    }

    public final void handleAuthenticationSucceeded() {
        Log.d("KKG::WorkProfileSysUiClientHelper", "onAuthenticationSucceeded");
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        PromptConfig promptConfig = this.mPromptConfig;
        lockPatternUtils.clearBiometricAttemptDeadline(promptConfig.getUserId());
        this.mDevicePolicyManager.reportSuccessfulBiometricAttempt(promptConfig.getUserId());
    }

    public final boolean interceptHandleAuthenticationSucceededIfNeeded() {
        Log.d("KKG::WorkProfileSysUiClientHelper", "interceptHandleAuthenticationSucceededIfNeeded");
        Context context = this.mContext;
        PromptConfig promptConfig = this.mPromptConfig;
        if (!KnoxUtils.isMultifactorEnabledForWork(context, promptConfig.getUserId())) {
            return false;
        }
        if (promptConfig.isKnoxOnlyConfirmBiometric()) {
            Log.d("KKG::WorkProfileSysUiClientHelper", "Only confirm biometric case when two-factor.");
            return false;
        }
        if (SemPersonaManager.appliedPasswordPolicy(promptConfig.getUserId())) {
            Log.d("KKG::WorkProfileSysUiClientHelper", "Applied password policy with multificator enabled.");
            return false;
        }
        promptConfig.getCallback().onDeviceCredentialPressed();
        return true;
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final boolean isForgotbtnDialogShowing() {
        AlertDialog alertDialog = this.mAlertDialog;
        return alertDialog != null && alertDialog.isShowing();
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final void modifyLayoutParamsIfNeeded(WindowManager.LayoutParams layoutParams) {
        layoutParams.setFitInsetsTypes(layoutParams.getFitInsetsTypes() & (~WindowInsets.Type.navigationBars()));
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.privateFlags |= 134217728;
        layoutParams.insetsFlags.behavior = 2;
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final void onAttachedPatternViewToWindow(LockPatternView lockPatternView) {
        lockPatternView.setColors(-1, -1, -65536);
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final void onAttachedToWindow(KnoxAuthCredentialView knoxAuthCredentialView, TextView textView, TextView textView2, TextView textView3, ImageView imageView) {
        Bitmap bitmap;
        byte[] bArr;
        TextView textView4 = (TextView) knoxAuthCredentialView.findViewById(R.id.knoxTitleText);
        if (textView4 != null) {
            textView4.setText(this.mContext.getString(R.string.biometric_prompt_default_title_work_profile));
        }
        PromptConfig promptConfig = this.mPromptConfig;
        BitmapDrawable bitmapDrawable = null;
        if (promptConfig.getPromptInfo().isUseDefaultTitle() && this.mContext.getString(R.string.biometric_prompt_default_title).equals(promptConfig.getPromptInfo().getTitle())) {
            setTextOrHide(textView, null, knoxAuthCredentialView);
        } else {
            setTextOrHide(textView, promptConfig.getPromptInfo().getTitle(), knoxAuthCredentialView);
        }
        setTextOrHide(textView2, promptConfig.getPromptInfo().getSubtitle(), knoxAuthCredentialView);
        setTextOrHide(textView3, promptConfig.getPromptInfo().getDescription(), knoxAuthCredentialView);
        Log.d("KKG::WorkProfileSysUiClientHelper", "Applying default work icon");
        Drawable drawable = new ResourceManager(this.mContext, "com.android.settings").getDrawable("knox_basic");
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = !drawable.getBounds().isEmpty() ? drawable.getBounds().width() : drawable.getIntrinsicWidth();
            int height = !drawable.getBounds().isEmpty() ? drawable.getBounds().height() : drawable.getIntrinsicHeight();
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }
            Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            bitmap = createBitmap;
        }
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            bArr = byteArrayOutputStream.toByteArray();
        } else {
            bArr = null;
        }
        if (bArr != null) {
            bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), BitmapFactory.decodeByteArray(bArr, 0, bArr.length));
            bitmapDrawable.setTint(-1);
        }
        imageView.setImageDrawable(bitmapDrawable);
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final void onCredentialVerified(int i, boolean z, View view, int i2, TextView textView) {
        int i3;
        int i4;
        boolean isPremiumContainer;
        SemPersonaManager semPersonaManager;
        PromptConfig promptConfig = this.mPromptConfig;
        if (z) {
            this.mLockPatternUtils.clearBiometricAttemptDeadline(promptConfig.getUserId());
            return;
        }
        if (i2 > 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        throw new IllegalStateException(SubMenuBuilder$$ExternalSyntheticOutline0.m0m("Unknown credential type: ", i));
                    }
                } else if (view instanceof LockPatternView) {
                    ((LockPatternView) view).setVisibility(4);
                }
            }
            if (view instanceof EditText) {
                EditText editText = (EditText) view;
                editText.setInputType(0);
                editText.setClickable(false);
                editText.setCursorVisible(false);
                editText.setFocusableInTouchMode(false);
                ((InputMethodManager) this.mContext.getSystemService(InputMethodManager.class)).hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        }
        Log.d("KKG::WorkProfileSysUiClientHelper", "reportFailedAttempt");
        int currentFailedPasswordAttempts = this.mLockPatternUtils.getCurrentFailedPasswordAttempts(promptConfig.getUserId());
        int maximumFailedPasswordsForWipe = this.mLockPatternUtils.getMaximumFailedPasswordsForWipe(promptConfig.getUserId());
        PasswordPolicy passwordPolicy = KnoxUtils.getPasswordPolicy(this.mContext, promptConfig.getUserId());
        if (passwordPolicy != null) {
            i3 = passwordPolicy.getMaximumFailedPasswordsForDeviceDisable();
        } else {
            Cursor query = this.mContext.getContentResolver().query(Uri.parse("content://com.sec.knox.provider/PasswordPolicy2"), null, "getMaximumFailedPasswordsForDisable", new String[]{Integer.toString(KnoxUtils.isChangeRequested(this.mContext, promptConfig.getUserId()) > 0 ? promptConfig.getUserId() * 100000 : promptConfig.getUserId() == 0 ? 0 : Process.myUid())}, null);
            if (query != null) {
                try {
                    query.moveToFirst();
                    i3 = query.getInt(query.getColumnIndex("getMaximumFailedPasswordsForDisable"));
                } catch (Exception unused) {
                    i3 = 0;
                } catch (Throwable th) {
                    query.close();
                    throw th;
                }
                query.close();
            } else {
                i3 = 0;
            }
        }
        int min = (maximumFailedPasswordsForWipe <= 0 || i3 <= 0) ? maximumFailedPasswordsForWipe > 0 ? maximumFailedPasswordsForWipe : i3 > 0 ? i3 : 0 : Math.min(maximumFailedPasswordsForWipe, i3);
        if (min <= 0 || currentFailedPasswordAttempts <= 0 || (i4 = min - currentFailedPasswordAttempts) > 10) {
            return;
        }
        textView.setText(String.format(i4 == 1 ? this.mContext.getString(R.string.keyguard_password_attempt_count_pin_code) : this.mContext.getString(R.string.keyguard_password_attempts_count_pin_code), Integer.valueOf(i4)));
        if (i4 > 5) {
            return;
        }
        Context context = this.mContext;
        int userId = promptConfig.getUserId();
        String string = (context == null || (semPersonaManager = (SemPersonaManager) context.getSystemService("persona")) == null) ? "Knox" : SemPersonaManager.isSecureFolderId(userId) ? context.getString(R.string.secure_folder_title) : semPersonaManager.getContainerName(userId, context);
        Context context2 = this.mContext;
        int userId2 = promptConfig.getUserId();
        UserInfo userInfo = UserManager.get(context2).getUserInfo(userId2);
        if (userInfo == null) {
            Log.e("KKG::KnoxUtils", userId2 + " not found...");
            isPremiumContainer = false;
        } else {
            isPremiumContainer = userInfo.isPremiumContainer();
        }
        Log.d("KKG::WorkProfileSysUiClientHelper", "Under than 5 attempts left : " + string + " : " + isPremiumContainer + " Wipe : " + maximumFailedPasswordsForWipe + " Disable : " + i3);
        String format = min == maximumFailedPasswordsForWipe ? currentFailedPasswordAttempts == 1 ? i4 == 1 ? isPremiumContainer ? String.format(this.mContext.getString(R.string.incorrect_dialog_wipe_1), string, string) : this.mContext.getString(R.string.incorrect_dialog_wipe_base_1) : isPremiumContainer ? String.format(this.mContext.getString(R.string.incorrect_dialog_wipe_2), string, Integer.valueOf(i4), string) : String.format(this.mContext.getString(R.string.incorrect_dialog_wipe_base_2), Integer.valueOf(i4)) : i4 == 1 ? isPremiumContainer ? String.format(this.mContext.getString(R.string.incorrect_dialog_wipe_4), Integer.valueOf(currentFailedPasswordAttempts), string, string) : String.format(this.mContext.getString(R.string.incorrect_dialog_wipe_base_4), Integer.valueOf(currentFailedPasswordAttempts)) : isPremiumContainer ? String.format(this.mContext.getString(R.string.incorrect_dialog_wipe_3), Integer.valueOf(currentFailedPasswordAttempts), string, Integer.valueOf(i4), string) : String.format(this.mContext.getString(R.string.incorrect_dialog_wipe_base_3), Integer.valueOf(currentFailedPasswordAttempts), Integer.valueOf(i4)) : currentFailedPasswordAttempts == 1 ? min == 1 ? String.format(this.mContext.getString(R.string.incorrect_dialog_1), string, string) : String.format(this.mContext.getString(R.string.incorrect_dialog_2), string, Integer.valueOf(i4), string) : i4 == 1 ? String.format(this.mContext.getString(R.string.incorrect_dialog_4), Integer.valueOf(currentFailedPasswordAttempts), string, string) : String.format(this.mContext.getString(R.string.incorrect_dialog_3), Integer.valueOf(currentFailedPasswordAttempts), string, Integer.valueOf(i4), string);
        if (i4 < 1) {
            if (min != maximumFailedPasswordsForWipe) {
                String str = SystemProperties.get("ro.organization_owned");
                if (!(str != null && str.equals("true")) || passwordPolicy == null) {
                    int userId3 = promptConfig.getUserId();
                    Bundle bundle = new Bundle();
                    bundle.putInt("android.intent.extra.user_handle", userId3);
                    ContainerProxy.sendPolicyUpdate("knox.container.proxy.POLICY_ADMIN_LOCK", bundle);
                } else {
                    passwordPolicy.lock();
                }
            }
            promptConfig.getCallback().onUserCancel(3);
            return;
        }
        final boolean z2 = i4 < 1;
        AlertDialog.Builder builder = new AlertDialog.Builder((this.mContext.getResources().getConfiguration().uiMode & 48) == 32 ? new ContextThemeWrapper(this.mContext.getApplicationContext(), android.R.style.Theme.DeviceDefault) : new ContextThemeWrapper(this.mContext.getApplicationContext(), android.R.style.Theme.DeviceDefault.Light));
        builder.setTitle((CharSequence) null);
        builder.setMessage(format);
        builder.setCancelable(true);
        builder.setPositiveButton(android.R.string.ok, new DialogInterfaceOnClickListenerC02881());
        AlertDialog create = builder.create();
        this.mAlertDialog = create;
        Window window = create.getWindow();
        window.setType(2017);
        window.setGravity(80);
        this.mAlertDialog.setCanceledOnTouchOutside(false);
        this.mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.samsung.android.biometrics.app.setting.knox.WorkProfileSysUiClientHelper.2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                if (z2) {
                    WorkProfileSysUiClientHelper.this.mPromptConfig.getCallback().onUserCancel(3);
                }
            }
        });
        this.mAlertDialog.show();
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final void onDetachedFromWindow() {
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mAlertDialog.dismiss();
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final void onErrorTimeoutFinish(KnoxAuthCredentialView knoxAuthCredentialView, int i, View view) {
        final int i2 = 0;
        final int i3 = 1;
        if (i == 1) {
            if (view instanceof EditText) {
                final EditText editText = (EditText) view;
                editText.setClickable(true);
                editText.setInputType(18);
                editText.setCursorVisible(true);
                editText.setFocusableInTouchMode(true);
                final InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
                knoxAuthCredentialView.postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.knox.WorkProfileSysUiClientHelper$$ExternalSyntheticLambda0
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
                }, 100L);
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
                throw new IllegalStateException(SubMenuBuilder$$ExternalSyntheticOutline0.m0m("Unknown credential type: ", i));
            }
            if (view instanceof EditText) {
                final EditText editText2 = (EditText) view;
                editText2.setClickable(true);
                editText2.setInputType(129);
                editText2.setCursorVisible(true);
                editText2.setFocusableInTouchMode(true);
                final InputMethodManager inputMethodManager2 = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
                knoxAuthCredentialView.postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.knox.WorkProfileSysUiClientHelper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i3) {
                            case 0:
                                EditText editText22 = editText2;
                                InputMethodManager inputMethodManager22 = inputMethodManager2;
                                editText22.requestFocus();
                                inputMethodManager22.showSoftInput(editText22, 1);
                                break;
                            default:
                                EditText editText3 = editText2;
                                InputMethodManager inputMethodManager3 = inputMethodManager2;
                                editText3.requestFocus();
                                inputMethodManager3.showSoftInput(editText3, 1);
                                break;
                        }
                    }
                }, 100L);
            }
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final void setBiometricAttemptDeadline(int i) {
        this.mLockPatternUtils.setBiometricAttemptDeadline(this.mPromptConfig.getUserId(), i);
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final void setDetailText(TextView textView) {
        int keyguardStoredPasswordQuality = this.mLockPatternUtils.getKeyguardStoredPasswordQuality(this.mPromptConfig.getUserId());
        int[][] iArr = DETAIL_TEXTS;
        String string = keyguardStoredPasswordQuality != 65536 ? (keyguardStoredPasswordQuality == 131072 || keyguardStoredPasswordQuality == 196608) ? this.mContext.getResources().getString(iArr[1][0]) : (keyguardStoredPasswordQuality == 262144 || keyguardStoredPasswordQuality == 327680 || keyguardStoredPasswordQuality == 393216) ? this.mContext.getResources().getString(iArr[2][0]) : "" : this.mContext.getResources().getString(iArr[0][0]);
        if (textView != null) {
            textView.setText(string);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final void setErrorTimerText(TextView textView, long j) {
        int round = (Math.round(j / 1000) % 60) + 1;
        int floor = (((int) Math.floor(j / 60000)) % 60) + 1;
        if (round <= 0) {
            return;
        }
        if (floor > 1) {
            textView.setText(this.mContext.getResources().getQuantityString(R.plurals.sec_lockpattern_too_many_failed_confirmation_attempts_footer_min, floor, Integer.valueOf(floor)));
        } else if (round == 60) {
            textView.setText(this.mContext.getResources().getQuantityString(R.plurals.sec_lockpattern_too_many_failed_confirmation_attempts_footer_min, floor, Integer.valueOf(floor)));
        } else {
            textView.setText(this.mContext.getResources().getQuantityString(R.plurals.sec_lockpattern_too_many_failed_confirmation_attempts_footer_sec, round, Integer.valueOf(round)));
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final void onConfigurationChanged() {
    }
}
