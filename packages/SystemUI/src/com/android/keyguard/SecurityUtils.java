package com.android.keyguard;

import android.R;
import android.content.Context;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.telephony.SubscriptionInfo;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class SecurityUtils {
    public static int sPINContainerBottomMargin;
    public static final int[] sImeHeight = new int[2];
    public static final HashMap sTypefaceMap = new HashMap();
    public static int sViewFlipperWidth = 0;
    public static int sPasswordViewFlipperWidth = 0;
    public static int sMainDisplayWidth = 0;
    public static int sMainDisplayHeight = 0;
    public static int sSubDisplayWidth = 0;

    /* renamed from: com.android.keyguard.SecurityUtils$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode = iArr;
            try {
                iArr[KeyguardSecurityModel.SecurityMode.FMM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.RMM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.KNOXGUARD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.AdminLock.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SKTCarrierLock.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SKTCarrierPassword.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static int calculateLandscapeViewWidth(int i, Context context) throws Resources.NotFoundException {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.secondary_waterfall_display_right_edge_size);
        return ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isInDisplayFingerprintMarginAccepted() ? ((i - dimensionPixelSize) - DeviceState.getInDisplayFingerprintHeight()) / 2 : (i - (dimensionPixelSize * 2)) / 2;
    }

    public static boolean checkFullscreenBouncer(KeyguardSecurityModel.SecurityMode securityMode) {
        boolean z = securityMode == KeyguardSecurityModel.SecurityMode.SimPin || securityMode == KeyguardSecurityModel.SecurityMode.SimPuk || securityMode == KeyguardSecurityModel.SecurityMode.FMM || securityMode == KeyguardSecurityModel.SecurityMode.RMM || securityMode == KeyguardSecurityModel.SecurityMode.KNOXGUARD || securityMode == KeyguardSecurityModel.SecurityMode.SKTCarrierLock || securityMode == KeyguardSecurityModel.SecurityMode.SKTCarrierPassword || securityMode == KeyguardSecurityModel.SecurityMode.AdminLock || securityMode == KeyguardSecurityModel.SecurityMode.Permanent;
        if (LsRune.SECURITY_SIM_PERSO_LOCK) {
            z = z || securityMode == KeyguardSecurityModel.SecurityMode.SimPerso;
        }
        return SafeUIState.isSysUiSafeModeEnabled() ? z || securityMode != KeyguardSecurityModel.SecurityMode.None : z;
    }

    public static int getCurrentRotation(Context context) {
        if (DeviceState.shouldEnableKeyguardScreenRotation(context)) {
            return DeviceState.getRotation(context.getResources().getConfiguration().windowConfiguration.getRotation());
        }
        return 0;
    }

    public static int getFoldPINContainerHeight(Context context) {
        Resources resources = context.getResources();
        float fHeight = resources.getConfiguration().windowConfiguration.getBounds().height();
        return (int) ((resources.getFloat(com.android.systemui.R.dimen.fold_num_pad_key_bottom_margin_ratio) * fHeight * 3.0f) + (resources.getFloat(com.android.systemui.R.dimen.fold_num_pad_key_size_ratio) * fHeight * 4.0f));
    }

    public static int getLockIconTopMargin(Context context) {
        int iHeight = context.getResources().getConfiguration().windowConfiguration.getBounds().height();
        int rotation = DeviceState.getRotation(context.getResources().getConfiguration().windowConfiguration.getRotation());
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && DeviceType.isTablet() && rotation == 2) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
            if (keyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted() && !keyguardUpdateMonitor.isNowBarExpandMode()) {
                return DeviceState.getInDisplayFingerprintHeight();
            }
        }
        float f = iHeight;
        float f2 = context.getResources().getFloat(com.android.systemui.R.dimen.kg_lock_icon_top_margin_phone_portrait_ratio);
        boolean z = true;
        if (rotation != 1 && rotation != 3) {
            z = false;
        }
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            f2 = (!z || context.getResources().getConfiguration().semDisplayDeviceType == 0) ? context.getResources().getFloat(com.android.systemui.R.dimen.kg_lock_icon_top_margin_fold_main_ratio) : context.getResources().getFloat(com.android.systemui.R.dimen.kg_lock_icon_top_margin_fold_sub_landscape_ratio);
        } else if (LsRune.SECURITY_SUB_DISPLAY_COVER) {
            f2 = z ? context.getResources().getFloat(com.android.systemui.R.dimen.kg_lock_icon_top_margin_fold_sub_landscape_ratio) : context.getResources().getFloat(com.android.systemui.R.dimen.kg_lock_icon_top_margin_fold_main_ratio);
        } else if (DeviceState.isTablet()) {
            f2 = z ? context.getResources().getFloat(com.android.systemui.R.dimen.kg_lock_icon_top_margin_fold_main_ratio) : context.getResources().getFloat(com.android.systemui.R.dimen.kg_lock_icon_top_margin_tablet_ratio);
        } else if (z) {
            f2 = context.getResources().getFloat(com.android.systemui.R.dimen.kg_lock_icon_top_margin_phone_landscape_ratio);
        }
        return (int) (f * f2);
    }

    public static int getMainSecurityViewFlipperSize(Context context, boolean z) {
        if (sViewFlipperWidth == 0) {
            initMainDisplaySize(context);
        }
        return z ? sPasswordViewFlipperWidth : sViewFlipperWidth;
    }

    public static int getPINContainerHeight(Context context) {
        Resources resources = context.getResources();
        Rect bounds = resources.getConfiguration().windowConfiguration.getBounds();
        int iMin = Math.min(bounds.width(), bounds.height());
        return (int) ((resources.getFloat(com.android.systemui.R.dimen.num_pad_key_bottom_margin_ratio) * Math.max(bounds.width(), bounds.height()) * 3.0f) + (resources.getFloat(com.android.systemui.R.dimen.num_pad_key_size_ratio) * iMin * 4.0f));
    }

    public static int getSimSlotNum(int i) {
        SubscriptionInfo subscriptionInfoForSubId = ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).getSubscriptionInfoForSubId(i);
        if (subscriptionInfoForSubId != null) {
            return subscriptionInfoForSubId.getSimSlotIndex();
        }
        return -1;
    }

    public static SpannableStringBuilder getStrongAuthPopupString(Context context, final KeyguardSecurityModel.SecurityMode securityMode, final EditText editText, int i) {
        final KeyguardTextBuilder keyguardTextBuilder = KeyguardTextBuilder.getInstance(context);
        String promptSecurityMessage = keyguardTextBuilder.getPromptSecurityMessage(securityMode, i);
        if (!TextUtils.isEmpty(promptSecurityMessage) && !KeyguardTextBuilder.getInstance(context).getStrongAuthTimeOutMessage(securityMode).isEmpty() && (i == 2 || i == 7 || i == 17)) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            int iIndexOf = promptSecurityMessage.indexOf("%1$s");
            int iIndexOf2 = promptSecurityMessage.indexOf("%2$s") - 4;
            if (iIndexOf >= 0 && iIndexOf2 >= 0) {
                spannableStringBuilder.append((CharSequence) String.format(promptSecurityMessage, "", ""));
                if (!SignalSeverity.NONE.equals(keyguardTextBuilder.mBiometricType)) {
                    spannableStringBuilder.setSpan(new ClickableSpan() { // from class: com.android.keyguard.KeyguardTextBuilder.1
                        public final /* synthetic */ EditText val$passwordEntry;
                        public final /* synthetic */ KeyguardSecurityModel.SecurityMode val$securityMode;

                        public AnonymousClass1(final KeyguardSecurityModel.SecurityMode securityMode2, final EditText editText2) {
                            securityMode = securityMode2;
                            editText = editText2;
                        }

                        @Override // android.text.style.ClickableSpan
                        public final void onClick(View view) {
                            StrongAuthPopup strongAuthPopup = KeyguardTextBuilder.this.mStrongAuthPopup;
                            if (strongAuthPopup != null) {
                                strongAuthPopup.dismiss();
                                KeyguardTextBuilder.this.mStrongAuthPopup = null;
                            }
                            KeyguardTextBuilder.this.mStrongAuthPopup = new StrongAuthPopup(KeyguardTextBuilder.this.mContext, securityMode, editText);
                            KeyguardTextBuilder.this.mStrongAuthPopup.updatePopup();
                            StrongAuthPopup strongAuthPopup2 = KeyguardTextBuilder.this.mStrongAuthPopup;
                            strongAuthPopup2.mHandler.postDelayed(new StrongAuthPopup$$ExternalSyntheticLambda1(strongAuthPopup2, 1), 100L);
                            SystemUIAnalytics.sendEventLog("102", SystemUIAnalytics.EID_OPEN_SECURITY_HELP);
                        }

                        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                        public final void updateDrawState(TextPaint textPaint) {
                            textPaint.setUnderlineText(true);
                            textPaint.setFakeBoldText(true);
                        }
                    }, iIndexOf, iIndexOf2, 33);
                }
                return spannableStringBuilder;
            }
            Log.d("KeyguardTextBuilder", "Unnecessary to update this message : promptReasonString = ".concat(promptSecurityMessage));
        }
        return null;
    }

    public static int getStrongAuthPrompt(int i) {
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
        if (keyguardUpdateMonitor.is2StepVerification()) {
            return 0;
        }
        KeyguardUpdateMonitor.StrongAuthTracker strongAuthTracker = keyguardUpdateMonitor.mStrongAuthTracker;
        int strongAuthForUser = strongAuthTracker.getStrongAuthForUser(i);
        boolean zIsNonStrongBiometricAllowedAfterIdleTimeout = strongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(i);
        if ((strongAuthForUser & 1) != 0) {
            return 1;
        }
        if ((strongAuthForUser & 2) != 0) {
            return 3;
        }
        if ((strongAuthForUser & 16) != 0) {
            return 2;
        }
        if ((strongAuthForUser & 128) != 0) {
            return 7;
        }
        return !zIsNonStrongBiometricAllowedAfterIdleTimeout ? 17 : 0;
    }

    public static int getTabletPINContainerHeight(Context context) {
        Resources resources = context.getResources();
        Rect bounds = resources.getConfiguration().windowConfiguration.getBounds();
        int iMin = Math.min(bounds.width(), bounds.height());
        return (int) ((resources.getFloat(com.android.systemui.R.dimen.tablet_num_pad_key_bottom_margin_ratio) * Math.max(bounds.width(), bounds.height()) * 3.0f) + (resources.getFloat(com.android.systemui.R.dimen.tablet_num_pad_key_size_ratio) * iMin * 4.0f));
    }

    public static void initMainDisplaySize(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        Rect bounds = configuration.windowConfiguration.getBounds();
        int iWidth = bounds.width();
        int iHeight = bounds.height();
        if (configuration.semDisplayDeviceType != 0) {
            if (sSubDisplayWidth == 0) {
                sSubDisplayWidth = Math.min(iWidth, iHeight);
            }
        } else if (sMainDisplayWidth == 0 && sMainDisplayHeight == 0) {
            sMainDisplayWidth = Math.min(iWidth, iHeight);
            sMainDisplayHeight = Math.max(iWidth, iHeight);
            sViewFlipperWidth = (int) SecurityUtils$$ExternalSyntheticOutline0.m(context, com.android.systemui.R.dimen.kg_message_area_width_dual_display_ratio, sMainDisplayWidth);
            sPasswordViewFlipperWidth = (int) SecurityUtils$$ExternalSyntheticOutline0.m(context, com.android.systemui.R.dimen.kg_password_message_area_width_dual_display_ratio, sMainDisplayWidth);
        }
    }

    public static boolean isArrowViewSupported(KeyguardSecurityModel.SecurityMode securityMode) {
        if (((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).isDualDarInnerAuthShowing()) {
            return false;
        }
        return securityMode == KeyguardSecurityModel.SecurityMode.PIN || securityMode == KeyguardSecurityModel.SecurityMode.Pattern || securityMode == KeyguardSecurityModel.SecurityMode.SimPin || securityMode == KeyguardSecurityModel.SecurityMode.SimPuk;
    }

    public static boolean matchSignature(Signature signature) throws NoSuchAlgorithmException {
        MessageDigest messageDigest;
        byte[] byteArray = signature.toByteArray();
        try {
            messageDigest = MessageDigest.getInstance("SHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            messageDigest = null;
        }
        messageDigest.update(byteArray);
        byte[] bArrDigest = messageDigest.digest();
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] cArr2 = new char[bArrDigest.length * 2];
        for (int i = 0; i < bArrDigest.length; i++) {
            byte b = bArrDigest[i];
            int i2 = i * 2;
            cArr2[i2] = cArr[(b & 255) >>> 4];
            cArr2[i2 + 1] = cArr[b & 15];
        }
        return "0848EDB80D10A557AA0D885AB3B669C915DCD6BCA8D78715568A06876AACD7CD".equals(new String(cArr2));
    }
}
