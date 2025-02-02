package com.samsung.android.biometrics.app.setting.knox;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
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
import android.widget.Toast;
import androidx.appcompat.view.menu.SubMenuBuilder$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockPatternView;
import com.samsung.android.biometrics.app.setting.DisplayStateManager$Injector$$ExternalSyntheticOutline0;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.Utils;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.SemPersonaManager;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class SecureFolderSysUiClientHelper implements KnoxSysUiClientHelper {
    private static final int[][] DETAIL_TEXTS = {new int[]{R.string.draw_unlock_pattern}, new int[]{R.string.sec_enter_pin_WC}, new int[]{R.string.sec_enter_password_WC}};
    private final LayoutInflater layoutInflater;
    private final AccessibilityManager mAccessibilityManager;
    private AlertDialog mAlertDialog;
    private TextView mBtnForgot;
    private Context mContext;
    private DevicePolicyManager mDevicePolicyManager;
    private boolean mIsPasswordShown;
    protected ArrayList<HashMap<String, Object>> mKnoxEventList;
    private LockPatternUtils mLockPatternUtils;
    private final String mPackageName;
    private final PromptConfig mPromptConfig;
    private TextView mUninstallBtn;

    /* renamed from: -$$Nest$misInLandscapeMode, reason: not valid java name */
    static boolean m221$$Nest$misInLandscapeMode(SecureFolderSysUiClientHelper secureFolderSysUiClientHelper) {
        int displayRotation = KnoxUtils.getDisplayRotation(secureFolderSysUiClientHelper.mContext);
        return displayRotation == 1 || displayRotation == 3;
    }

    /* renamed from: -$$Nest$mremoveSecureFolder, reason: not valid java name */
    static void m222$$Nest$mremoveSecureFolder(SecureFolderSysUiClientHelper secureFolderSysUiClientHelper, Context context) {
        boolean isAdminActive;
        secureFolderSysUiClientHelper.getClass();
        Log.d("KKG::SecureFolderSysUiClientHelper", "removeSecureFolder()");
        PromptConfig promptConfig = secureFolderSysUiClientHelper.mPromptConfig;
        int userId = promptConfig.getUserId();
        if (userId <= 0) {
            Log.d("KKG::SecureFolderSysUiClientHelper", "removeSecureFolder(). Incorrect User ID : " + userId);
            return;
        }
        Log.d("KKG::SecureFolderSysUiClientHelper", "setActiveAdminIfNeeded");
        ComponentName componentName = new ComponentName("com.samsung.knox.securefolder", "com.samsung.knox.securefolder.containeragent.detector.KnoxDeviceAdminReceiver");
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        if (devicePolicyManager == null) {
            Log.i("KKG::SecureFolderSysUiClientHelper", "DevicePolicyManager is null");
            isAdminActive = false;
        } else {
            isAdminActive = devicePolicyManager.isAdminActive(componentName);
        }
        if (!isAdminActive) {
            Log.d("KKG::SecureFolderSysUiClientHelper", "setActiveAdminIfNeeded(): Setting active admin");
            DevicePolicyManager devicePolicyManager2 = (DevicePolicyManager) context.getSystemService("device_policy");
            if (devicePolicyManager2 == null) {
                Log.i("KKG::SecureFolderSysUiClientHelper", "DevicePolicyManager is null");
            } else {
                try {
                    devicePolicyManager2.setActiveAdmin(componentName, false, promptConfig.getUserId());
                } catch (Exception e) {
                    Log.d("KKG::SecureFolderSysUiClientHelper", "Error setting active admin : " + e.getMessage());
                }
            }
        }
        Settings.Secure.putIntForUser(context.getContentResolver(), "DelFlag", 1, 0);
        Settings.Secure.putStringForUser(context.getContentResolver(), "secure_folder_image_name", "sf_app_icon_00", 0);
        Settings.Secure.putStringForUser(context.getContentResolver(), "secure_folder_name", null, 0);
        try {
            ((UserManager) context.createPackageContextAsUser("com.samsung.knox.securefolder", 0, UserHandle.of(userId)).getSystemService("user")).removeUser(userId);
        } catch (Exception e2) {
            Log.d("KKG::SecureFolderSysUiClientHelper", "removeSecureFolderUser(). Exception : " + e2.getMessage());
        }
    }

    /* renamed from: -$$Nest$mshowForgotPasswordDialog, reason: not valid java name */
    static void m223$$Nest$mshowForgotPasswordDialog(SecureFolderSysUiClientHelper secureFolderSysUiClientHelper) {
        String string;
        secureFolderSysUiClientHelper.getClass();
        try {
            boolean z = true;
            AlertDialog.Builder builder = new AlertDialog.Builder((secureFolderSysUiClientHelper.mContext.getResources().getConfiguration().uiMode & 48) == 32 ? new ContextThemeWrapper(secureFolderSysUiClientHelper.mContext.getApplicationContext(), android.R.style.Theme.DeviceDefault) : new ContextThemeWrapper(secureFolderSysUiClientHelper.mContext.getApplicationContext(), android.R.style.Theme.DeviceDefault.Light));
            int keyguardStoredPasswordQuality = secureFolderSysUiClientHelper.mLockPatternUtils.getKeyguardStoredPasswordQuality(secureFolderSysUiClientHelper.mPromptConfig.getUserId());
            if (keyguardStoredPasswordQuality != 65536) {
                if (keyguardStoredPasswordQuality != 131072 && keyguardStoredPasswordQuality != 196608) {
                    if (keyguardStoredPasswordQuality != 262144 && keyguardStoredPasswordQuality != 327680 && keyguardStoredPasswordQuality != 393216) {
                        if (keyguardStoredPasswordQuality != 458752) {
                            if (keyguardStoredPasswordQuality != 524288) {
                                Log.e("KKG::SecureFolderSysUiClientHelper", "invalid quality error");
                                string = secureFolderSysUiClientHelper.mContext.getString(R.string.forgot_pattern_header);
                            }
                        }
                    }
                    string = secureFolderSysUiClientHelper.mContext.getString(R.string.forgot_password_header);
                }
                string = secureFolderSysUiClientHelper.mContext.getString(R.string.forgot_pin_header);
            } else {
                string = secureFolderSysUiClientHelper.mContext.getString(R.string.forgot_pattern_header);
            }
            builder.setTitle(string);
            String string2 = SemCscFeature.getInstance().getString("CscFeature_Common_ReplaceSecBrandAsGalaxy");
            if (string2 == null || !string2.equals("TRUE")) {
                z = false;
            }
            if (z) {
                builder.setMessage(secureFolderSysUiClientHelper.mContext.getString(R.string.forgot_ppp_dialog_galaxy));
            } else {
                builder.setMessage(secureFolderSysUiClientHelper.mContext.getString(R.string.forgot_ppp_dialog));
            }
            builder.setPositiveButton(R.string.reset, new DialogInterface.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.knox.SecureFolderSysUiClientHelper.5
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    SecureFolderSysUiClientHelper.this.mKnoxEventList.add(KnoxSamsungAnalyticsLogger.addEvent(100, 1003, null));
                    if (((ActivityManager) SecureFolderSysUiClientHelper.this.mContext.getSystemService("activity")).isInLockTaskMode()) {
                        Toast.makeText(SecureFolderSysUiClientHelper.this.mContext, R.string.lock_to_app_toast, 1).show();
                        return;
                    }
                    SecureFolderSysUiClientHelper.this.mPromptConfig.getCallback().onUserCancel(3);
                    SecureFolderSysUiClientHelper.this.showSFLockedView(true, false);
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.knox.SecureFolderSysUiClientHelper.6
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    SecureFolderSysUiClientHelper.this.mKnoxEventList.add(KnoxSamsungAnalyticsLogger.addEvent(100, 1002, null));
                    dialogInterface.dismiss();
                }
            });
            AlertDialog create = builder.create();
            secureFolderSysUiClientHelper.mAlertDialog = create;
            Window window = create.getWindow();
            window.setType(2017);
            window.setGravity(80);
            secureFolderSysUiClientHelper.mAlertDialog.show();
        } catch (Exception e) {
            Log.e("KKG::SecureFolderSysUiClientHelper", "Exception : " + e.getMessage());
        }
    }

    /* renamed from: -$$Nest$mshowUninstallDialog, reason: not valid java name */
    static void m225$$Nest$mshowUninstallDialog(SecureFolderSysUiClientHelper secureFolderSysUiClientHelper) {
        AlertDialog.Builder builder = new AlertDialog.Builder((secureFolderSysUiClientHelper.mContext.getResources().getConfiguration().uiMode & 48) == 32 ? new ContextThemeWrapper(secureFolderSysUiClientHelper.mContext.getApplicationContext(), android.R.style.Theme.DeviceDefault) : new ContextThemeWrapper(secureFolderSysUiClientHelper.mContext.getApplicationContext(), android.R.style.Theme.DeviceDefault.Light));
        builder.setTitle(R.string.uninstall_secure_folder);
        builder.setMessage(R.string.keyguard_uninstall_popup_msg);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.knox_uninstall_dialog_title, new DialogInterface.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.knox.SecureFolderSysUiClientHelper.7
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SecureFolderSysUiClientHelper.this.mKnoxEventList.add(KnoxSamsungAnalyticsLogger.addEvent(183, 1833, null));
                SecureFolderSysUiClientHelper.this.mAlertDialog.getButton(-1).setEnabled(false);
                SecureFolderSysUiClientHelper.this.mPromptConfig.getCallback().onUserCancel(3);
                SecureFolderSysUiClientHelper secureFolderSysUiClientHelper2 = SecureFolderSysUiClientHelper.this;
                SecureFolderSysUiClientHelper.m222$$Nest$mremoveSecureFolder(secureFolderSysUiClientHelper2, secureFolderSysUiClientHelper2.mContext);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.knox.SecureFolderSysUiClientHelper.8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SecureFolderSysUiClientHelper.this.mKnoxEventList.add(KnoxSamsungAnalyticsLogger.addEvent(183, 1832, null));
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        secureFolderSysUiClientHelper.mAlertDialog = create;
        Window window = create.getWindow();
        window.setType(2017);
        window.setGravity(80);
        secureFolderSysUiClientHelper.mAlertDialog.setCanceledOnTouchOutside(true);
        secureFolderSysUiClientHelper.mAlertDialog.show();
    }

    public SecureFolderSysUiClientHelper(Context context, PromptConfig promptConfig, String str) {
        this.mContext = context;
        this.mPromptConfig = promptConfig;
        this.mPackageName = str;
        this.layoutInflater = LayoutInflater.from(context);
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
        this.mKnoxEventList = arrayList;
        String concat = getCurrentLockType().concat(" + fingerprint");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("statusID", 100);
        hashMap.put("detail", concat);
        hashMap.put("type", "status");
        arrayList.add(hashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getCurrentLockType() {
        int credentialType = this.mPromptConfig.getCredentialType();
        return credentialType != 1 ? credentialType != 2 ? credentialType != 3 ? "" : "password" : "pattern" : "pin";
    }

    private boolean isResetWithSamsungAccountEnable() {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), "sf_reset_with_samsung_account", 1, this.mPromptConfig.getUserId()) == 1;
    }

    private void setAuthenticationViewOrientation(Configuration configuration, KnoxAuthCredentialView knoxAuthCredentialView) {
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
            TextView textView3 = knoxAuthCredentialView.getOrientation() == 1 ? (TextView) knoxAuthCredentialView.findViewById(R.id.btn_forgot_txt_port) : (TextView) knoxAuthCredentialView.findViewById(R.id.btn_forgot_txt_land);
            this.mBtnForgot = textView3;
            if (textView3 != null) {
                textView3.setVisibility(visibility);
                TextView textView4 = this.mBtnForgot;
                textView4.setPaintFlags(textView4.getPaintFlags() | 8);
                int credentialType = this.mPromptConfig.getCredentialType();
                if (credentialType == 1) {
                    this.mBtnForgot.setText(this.mContext.getResources().getString(R.string.forgot_pin));
                } else if (credentialType == 2) {
                    this.mBtnForgot.setText(this.mContext.getResources().getString(R.string.forgot_pattern));
                } else if (credentialType == 3) {
                    this.mBtnForgot.setText(this.mContext.getResources().getString(R.string.forgot_password));
                }
                this.mBtnForgot.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.knox.SecureFolderSysUiClientHelper.3
                    /* JADX WARN: Code restructure failed: missing block: B:13:0x0034, code lost:
                    
                        r0 = android.app.WindowConfiguration.inMultiWindowMode(r2.configuration.windowConfiguration.getWindowingMode());
                     */
                    @Override // android.view.View.OnClickListener
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void onClick(View view) {
                        String str = SecureFolderSysUiClientHelper.this.mPackageName;
                        boolean z = false;
                        try {
                            List tasks = ActivityTaskManager.getInstance().getTasks(3, false);
                            if (tasks != null && !tasks.isEmpty()) {
                                Iterator it = tasks.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) it.next();
                                    if (str.equals(runningTaskInfo.topActivity.getPackageName())) {
                                        break;
                                    }
                                }
                            }
                        } catch (Exception e) {
                            DisplayStateManager$Injector$$ExternalSyntheticOutline0.m9m(e, new StringBuilder("isMultiWindowMode: "), "KKG::KnoxUtils");
                        }
                        Log.d("KKG::SecureFolderSysUiClientHelper", "isInMultiWindowMode : " + z + ", package : " + SecureFolderSysUiClientHelper.this.mPackageName);
                        if (!Utils.isDesktopMode(SecureFolderSysUiClientHelper.this.mContext) && z) {
                            Toast.makeText(SecureFolderSysUiClientHelper.this.mContext, R.string.lock_screen_doesnt_support_multi_window_text, 1).show();
                            return;
                        }
                        SecureFolderSysUiClientHelper.m223$$Nest$mshowForgotPasswordDialog(SecureFolderSysUiClientHelper.this);
                        SecureFolderSysUiClientHelper secureFolderSysUiClientHelper = SecureFolderSysUiClientHelper.this;
                        secureFolderSysUiClientHelper.mKnoxEventList.add(KnoxSamsungAnalyticsLogger.addEvent(100, 1001, secureFolderSysUiClientHelper.getCurrentLockType()));
                    }
                });
                return;
            }
            return;
        }
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.uninstall_btn_height);
        int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.uninstall_btn_width);
        int dimensionPixelSize3 = this.mContext.getResources().getDimensionPixelSize(R.dimen.uninstall_btn_padding_left_right);
        if (knoxAuthCredentialView.getOrientation() == 1) {
            TextView textView5 = (TextView) knoxAuthCredentialView.findViewById(R.id.btn_forgot_txt_port);
            this.mUninstallBtn = textView5;
            if (textView5 != null) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dimensionPixelSize2, dimensionPixelSize);
                layoutParams.topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.uninstall_btn_margin_top_port);
                layoutParams.gravity = 1;
                this.mUninstallBtn.setLayoutParams(layoutParams);
            }
        } else {
            TextView textView6 = (TextView) knoxAuthCredentialView.findViewById(R.id.btn_forgot_txt_land);
            this.mUninstallBtn = textView6;
            if (textView6 != null) {
                RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(dimensionPixelSize2, dimensionPixelSize);
                layoutParams2.topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.uninstall_btn_margin_top_land);
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
            textView8.setPadding(dimensionPixelSize3, textView8.getPaddingTop(), dimensionPixelSize3, this.mUninstallBtn.getPaddingBottom());
            this.mUninstallBtn.setText(R.string.knox_uninstall_dialog_title);
            this.mUninstallBtn.setBackgroundResource(R.drawable.sec_knox_ripple_effect_transparent_button_drawable);
            this.mUninstallBtn.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.knox.SecureFolderSysUiClientHelper.4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SecureFolderSysUiClientHelper.m225$$Nest$mshowUninstallDialog(SecureFolderSysUiClientHelper.this);
                }
            });
        }
    }

    private void setTextOrHide(TextView textView, CharSequence charSequence, KnoxAuthCredentialView knoxAuthCredentialView) {
        if (TextUtils.isEmpty(charSequence)) {
            textView.setVisibility(8);
        } else {
            textView.setText(charSequence);
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, knoxAuthCredentialView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSFLockedView(boolean z, boolean z2) {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.samsung.android.settings.knox.SecureFolderLockedView");
        intent.addFlags(268468224);
        intent.putExtra("fromResetBtn", z);
        intent.putExtra("wasLastAttempt", z2);
        intent.putExtra("userId", this.mPromptConfig.getUserId());
        try {
            this.mContext.startActivity(intent);
        } catch (Exception e) {
            Log.e("KKG::SecureFolderSysUiClientHelper", "Exception while launching secure folder locked view : " + e.getMessage());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0349  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0360  */
    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final KnoxAuthCredentialView changeCredentialViewIfNeeded(View view) {
        PromptConfig promptConfig;
        int i;
        int i2;
        KnoxAuthCredentialView knoxAuthCredentialView;
        View findViewById;
        TextView textView;
        PromptConfig promptConfig2 = this.mPromptConfig;
        int credentialType = promptConfig2.getCredentialType();
        LayoutInflater layoutInflater = this.layoutInflater;
        if (credentialType != 1) {
            if (credentialType == 2) {
                knoxAuthCredentialView = (KnoxAuthCredentialView) layoutInflater.inflate(R.layout.sec_knox_biometric_prompt_credential_pattern, (ViewGroup) null, false);
                Configuration configuration = this.mContext.getResources().getConfiguration();
                setAuthenticationViewOrientation(configuration, knoxAuthCredentialView);
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
                    knoxAuthCredentialView.setPadding(0, 0, KnoxUtils.getNavigationBarSize(this.mContext), (KnoxUtils.isTablet() || (KnoxUtils.isFoldableProduct() && configuration.semDisplayDeviceType == 0)) ? KnoxUtils.getNavigationBarSize(this.mContext) : 0);
                    layoutParams.weight = 0.5f;
                    layoutParams2.weight = 0.5f;
                } else {
                    knoxAuthCredentialView.setPadding(0, 0, 0, KnoxUtils.getNavigationBarSize(this.mContext));
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
                    if (KnoxUtils.isFoldableProduct()) {
                        layoutParams.weight = 0.6f;
                        layoutParams2.weight = 0.4f;
                    } else {
                        float adjustBiometricViewWeightsForPatternPortrait = KnoxUtils.adjustBiometricViewWeightsForPatternPortrait();
                        layoutParams.weight = adjustBiometricViewWeightsForPatternPortrait;
                        layoutParams2.weight = 1.0f - adjustBiometricViewWeightsForPatternPortrait;
                    }
                }
                i = 0;
                promptConfig = promptConfig2;
                i2 = 3;
                findViewById = view.findViewById(R.id.panel);
                if (findViewById != null) {
                    findViewById.setBackground(this.mContext.getDrawable(R.drawable.sec_knox_credential_bg));
                }
                textView = (TextView) knoxAuthCredentialView.findViewById(R.id.detailsText);
                if (textView != null) {
                    int credentialType2 = promptConfig.getCredentialType();
                    int[][] iArr = DETAIL_TEXTS;
                    textView.setText(credentialType2 != 1 ? credentialType2 != 2 ? credentialType2 != i2 ? i : iArr[2][i] : iArr[i][i] : iArr[1][i]);
                }
                return knoxAuthCredentialView;
            }
            if (credentialType != 3) {
                throw new IllegalStateException("Unknown credential type: " + promptConfig2.getCredentialType());
            }
        }
        final KnoxAuthCredentialView knoxAuthCredentialView2 = (KnoxAuthCredentialView) layoutInflater.inflate(R.layout.sec_knox_biometric_prompt_credential_password, (ViewGroup) null, false);
        final EditText editText = (EditText) knoxAuthCredentialView2.findViewById(R.id.lockPassword);
        final ImageButton imageButton = (ImageButton) knoxAuthCredentialView2.findViewById(R.id.password_show_btn);
        if (editText != null && imageButton != null) {
            editText.setPrivateImeOptions(editText.getPrivateImeOptions() + ";disableToolbar=true;lockScreenPasswordField=true");
            if (promptConfig2.getCredentialType() == 3) {
                imageButton.setVisibility(0);
                imageButton.setForeground(this.mContext.getResources().getDrawable(R.drawable.lock_password_btn_password_hide_mtrl, null));
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imageButton.setContentDescription(this.mContext.getString(R.string.sec_lockpassword_show_button));
                imageButton.setImageTintList(ColorStateList.valueOf(this.mContext.getResources().getColor(R.color.body_text_color_light, null)));
                imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.biometrics.app.setting.knox.SecureFolderSysUiClientHelper.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        int selectionEnd = editText.getSelectionEnd();
                        if (SecureFolderSysUiClientHelper.this.mIsPasswordShown) {
                            imageButton.setForeground(SecureFolderSysUiClientHelper.this.mContext.getResources().getDrawable(R.drawable.lock_password_btn_password_hide_mtrl, null));
                            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            imageButton.setContentDescription(SecureFolderSysUiClientHelper.this.mContext.getString(R.string.sec_lockpassword_show_button));
                            SecureFolderSysUiClientHelper.this.mIsPasswordShown = false;
                        } else {
                            imageButton.setForeground(SecureFolderSysUiClientHelper.this.mContext.getResources().getDrawable(R.drawable.lock_password_btn_password_show_mtrl, null));
                            editText.setTransformationMethod(null);
                            imageButton.setContentDescription(SecureFolderSysUiClientHelper.this.mContext.getString(R.string.sec_lockpassword_hide_button));
                            SecureFolderSysUiClientHelper.this.mIsPasswordShown = true;
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
        setAuthenticationViewOrientation(configuration2, knoxAuthCredentialView2);
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
        promptConfig = promptConfig2;
        i = 0;
        i2 = 3;
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.samsung.android.biometrics.app.setting.knox.SecureFolderSysUiClientHelper.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                if (knoxAuthCredentialView2 != null) {
                    Rect rect = new Rect();
                    scrollView2.getWindowVisibleDisplayFrame(rect);
                    int navigationBarSize = KnoxUtils.getNavigationBarSize(SecureFolderSysUiClientHelper.this.mContext);
                    int height = navigationBarSize > 0 ? (scrollView2.getRootView().getHeight() - rect.bottom) - navigationBarSize : scrollView2.getRootView().getHeight() - rect.bottom;
                    Log.d("KKG::SecureFolderSysUiClientHelper", "Keyboard height = " + height);
                    if (height <= 0) {
                        if (SecureFolderSysUiClientHelper.m221$$Nest$misInLandscapeMode(SecureFolderSysUiClientHelper.this)) {
                            navigationBarSize = 0;
                        }
                        linearLayout7.setPadding(0, 0, 0, navigationBarSize);
                        relativeLayout4.setPadding(0, 0, 0, 0);
                        imageView3.setVisibility(0);
                        textView2.setVisibility(0);
                        linearLayout8.setPadding(0, SecureFolderSysUiClientHelper.this.mContext.getResources().getDimensionPixelSize(R.dimen.knox_password_view_margin_end), 0, 0);
                        if (SecureFolderSysUiClientHelper.m221$$Nest$misInLandscapeMode(SecureFolderSysUiClientHelper.this)) {
                            layoutParams10.weight = 0.5f;
                            layoutParams11.weight = 0.5f;
                            return;
                        } else {
                            layoutParams10.weight = 0.2f;
                            layoutParams11.weight = 0.8f;
                            return;
                        }
                    }
                    if (!SecureFolderSysUiClientHelper.m221$$Nest$misInLandscapeMode(SecureFolderSysUiClientHelper.this)) {
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
                    int i3 = height + 0;
                    linearLayout7.setPadding(0, 0, 0, i3);
                    relativeLayout4.setPadding(0, 0, 0, i3);
                    linearLayout8.setPadding(0, 0, 0, 0);
                    imageView3.setVisibility(8);
                    textView2.setVisibility(8);
                }
            }
        });
        knoxAuthCredentialView = knoxAuthCredentialView2;
        findViewById = view.findViewById(R.id.panel);
        if (findViewById != null) {
        }
        textView = (TextView) knoxAuthCredentialView.findViewById(R.id.detailsText);
        if (textView != null) {
        }
        return knoxAuthCredentialView;
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final TextView getDetailsTextView(KnoxAuthCredentialView knoxAuthCredentialView) {
        return (TextView) knoxAuthCredentialView.findViewById(R.id.detailsText);
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final String getErrorMessage() {
        TextView textView;
        if (isResetWithSamsungAccountEnable() && (textView = this.mBtnForgot) != null) {
            textView.setVisibility(0);
        }
        int keyguardStoredPasswordQuality = this.mLockPatternUtils.getKeyguardStoredPasswordQuality(this.mPromptConfig.getUserId());
        if (keyguardStoredPasswordQuality == 65536) {
            return this.mContext.getResources().getString(R.string.incorrect_pattern_entered_secure_folder);
        }
        if (keyguardStoredPasswordQuality == 131072 || keyguardStoredPasswordQuality == 196608 || keyguardStoredPasswordQuality == 262144 || keyguardStoredPasswordQuality == 327680 || keyguardStoredPasswordQuality == 393216) {
            return this.mContext.getResources().getString(R.string.sec_lockpassword_need_to_unlock_wrong);
        }
        return null;
    }

    public final void handleAuthenticationFailed() {
        Log.d("KKG::SecureFolderSysUiClientHelper", "onAuthenticationFailed");
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        PromptConfig promptConfig = this.mPromptConfig;
        devicePolicyManager.reportFailedBiometricAttempt(promptConfig.getUserId());
        int currentFailedBiometricAttempts = this.mDevicePolicyManager.getCurrentFailedBiometricAttempts(promptConfig.getUserId());
        if (currentFailedBiometricAttempts >= 50) {
            Log.d("KKG::SecureFolderSysUiClientHelper", "mFailedBiometricUnlockAttemptsForSecureFolder ( too many failed. )");
            this.mLockPatternUtils.clearBiometricAttemptDeadline(promptConfig.getUserId());
            this.mLockPatternUtils.requireStrongAuth(4096, promptConfig.getUserId());
            if (Utils.isDesktopMode(this.mContext)) {
                promptConfig.getCallback().onDismissed(5, null);
                return;
            } else {
                promptConfig.getCallback().onDeviceCredentialPressed();
                return;
            }
        }
        if (currentFailedBiometricAttempts == 0 || currentFailedBiometricAttempts % 5 != 0) {
            return;
        }
        this.mLockPatternUtils.setBiometricAttemptDeadline(promptConfig.getUserId(), 30000);
        if (Utils.isDesktopMode(this.mContext)) {
            promptConfig.getCallback().onDismissed(5, null);
        } else {
            promptConfig.getCallback().onDeviceCredentialPressed();
        }
    }

    public final void handleAuthenticationSucceeded() {
        Log.d("KKG::SecureFolderSysUiClientHelper", "onAuthenticationSucceeded");
        this.mKnoxEventList.add(KnoxSamsungAnalyticsLogger.addEvent(100, 1000, getCurrentLockType()));
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        PromptConfig promptConfig = this.mPromptConfig;
        lockPatternUtils.clearBiometricAttemptDeadline(promptConfig.getUserId());
        this.mDevicePolicyManager.reportSuccessfulBiometricAttempt(promptConfig.getUserId());
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

    /* JADX WARN: Removed duplicated region for block: B:17:0x009a  */
    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onAttachedToWindow(KnoxAuthCredentialView knoxAuthCredentialView, TextView textView, TextView textView2, TextView textView3, ImageView imageView) {
        Exception e;
        byte[] bArr;
        Bitmap bitmap;
        TextView textView4 = (TextView) knoxAuthCredentialView.findViewById(R.id.knoxTitleText);
        PromptConfig promptConfig = this.mPromptConfig;
        if (textView4 != null) {
            textView4.setText(promptConfig.getExtraInfo().getString("SECURE_FOLDER_NAME"));
        }
        BitmapDrawable bitmapDrawable = null;
        if (promptConfig.getPromptInfo().isUseDefaultTitle() && this.mContext.getString(R.string.biometric_prompt_default_title).equals(promptConfig.getPromptInfo().getTitle())) {
            setTextOrHide(textView, null, knoxAuthCredentialView);
        } else {
            setTextOrHide(textView, promptConfig.getPromptInfo().getTitle(), knoxAuthCredentialView);
        }
        setTextOrHide(textView2, promptConfig.getPromptInfo().getSubtitle(), knoxAuthCredentialView);
        setTextOrHide(textView3, promptConfig.getPromptInfo().getDescription(), knoxAuthCredentialView);
        try {
            bArr = SemPersonaManager.getKnoxIcon(promptConfig.getUserId());
        } catch (Exception e2) {
            e = e2;
            bArr = null;
        }
        try {
            bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), BitmapFactory.decodeByteArray(bArr, 0, bArr.length));
        } catch (Exception e3) {
            e = e3;
            Log.d("KKG::SecureFolderSysUiClientHelper", "Unable to fetch sf icon : " + e.getMessage());
            if (bArr == null) {
            }
            imageView.setImageDrawable(bitmapDrawable);
        }
        if (bArr == null) {
            Log.d("KKG::SecureFolderSysUiClientHelper", "Applying default sf icon");
            Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.sf_keyguard_default_logo);
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
            }
            if (bArr != null) {
                bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), BitmapFactory.decodeByteArray(bArr, 0, bArr.length));
            }
        }
        imageView.setImageDrawable(bitmapDrawable);
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final void onConfigurationChanged() {
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mAlertDialog.dismiss();
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final void onCredentialVerified(int i, boolean z, View view, int i2, TextView textView) {
        PromptConfig promptConfig = this.mPromptConfig;
        if (z) {
            this.mLockPatternUtils.clearBiometricAttemptDeadline(promptConfig.getUserId());
            return;
        }
        if (i2 > 0) {
            int currentFailedPasswordAttempts = this.mDevicePolicyManager.getCurrentFailedPasswordAttempts(promptConfig.getUserId());
            Log.d("KKG::SecureFolderSysUiClientHelper", "mFailedUnlockAttemptsForSecureFolder " + currentFailedPasswordAttempts);
            if (currentFailedPasswordAttempts >= 15) {
                if (isResetWithSamsungAccountEnable()) {
                    Log.d("KKG::SecureFolderSysUiClientHelper", "mFailedUnlockAttemptsForSecureFolder ( show Locked View. )");
                    promptConfig.getCallback().onUserCancel(3);
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
                    throw new IllegalStateException(SubMenuBuilder$$ExternalSyntheticOutline0.m0m("Unknown credential type: ", i));
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
    }

    @Override // com.samsung.android.biometrics.app.setting.knox.KnoxSysUiClientHelper
    public final void onDetachedFromWindow() {
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mAlertDialog.dismiss();
        }
        Context context = this.mContext;
        ArrayList<HashMap<String, Object>> arrayList = this.mKnoxEventList;
        int userId = this.mPromptConfig.getUserId();
        if (SemPersonaManager.isSecureFolderId(userId)) {
            Log.d("KKG::KnoxSamsungAnalyticsLogger", "Sending SA logging event");
            Intent intent = new Intent("com.samsung.knox.securefolder.salogging");
            intent.setPackage("com.samsung.knox.securefolder");
            intent.setComponent(new ComponentName("com.samsung.knox.securefolder", "com.samsung.knox.securefolder.common.util.logging.LoggingReceiver"));
            intent.putExtra("knoxEventList", arrayList);
            context.sendBroadcastAsUser(intent, new UserHandle(userId), "com.samsung.android.knox.permission.KNOX_CONTAINER");
        }
        this.mKnoxEventList.clear();
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
                knoxAuthCredentialView.postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.knox.SecureFolderSysUiClientHelper$$ExternalSyntheticLambda0
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
                knoxAuthCredentialView.postDelayed(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.knox.SecureFolderSysUiClientHelper$$ExternalSyntheticLambda0
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
        String quantityString;
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        PromptConfig promptConfig = this.mPromptConfig;
        int currentFailedPasswordAttempts = devicePolicyManager.getCurrentFailedPasswordAttempts(promptConfig.getUserId());
        int keyguardStoredPasswordQuality = this.mLockPatternUtils.getKeyguardStoredPasswordQuality(promptConfig.getUserId());
        int i = 15 - currentFailedPasswordAttempts;
        if (keyguardStoredPasswordQuality != 65536) {
            int[][] iArr = DETAIL_TEXTS;
            if (keyguardStoredPasswordQuality == 131072 || keyguardStoredPasswordQuality == 196608) {
                quantityString = isResetWithSamsungAccountEnable() && currentFailedPasswordAttempts >= 10 ? this.mContext.getResources().getQuantityString(R.plurals.sec_incorrect_pin_attempts_left, i, Integer.valueOf(i)) : this.mContext.getResources().getString(iArr[1][0]);
            } else if (keyguardStoredPasswordQuality == 262144 || keyguardStoredPasswordQuality == 327680 || keyguardStoredPasswordQuality == 393216) {
                quantityString = isResetWithSamsungAccountEnable() && currentFailedPasswordAttempts >= 10 ? this.mContext.getResources().getQuantityString(R.plurals.sec_incorrect_password_attempts_left, i, Integer.valueOf(i)) : this.mContext.getResources().getString(iArr[2][0]);
            } else {
                quantityString = "";
            }
        } else {
            quantityString = isResetWithSamsungAccountEnable() && currentFailedPasswordAttempts >= 10 ? this.mContext.getResources().getQuantityString(R.plurals.sec_incorrect_pattern_attempts_left, i, Integer.valueOf(i)) : this.mContext.getResources().getString(R.string.incorrect_pattern_entered_secure_folder);
        }
        if (textView != null) {
            textView.setText(quantityString);
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
}
