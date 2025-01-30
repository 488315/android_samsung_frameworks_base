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
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.configuration.DATA;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SecurityUtils {
    public static final int[] sImeHeight = new int[2];
    public static int sMainDisplayHeight;
    public static int sMainDisplayWidth;
    public static int sPINContainerBottomMargin;
    public static int sPasswordViewFlipperWidth;
    public static int sSubDisplayWidth;
    public static int sViewFlipperWidth;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.SecurityUtils$1 */
    public abstract /* synthetic */ class AbstractC08451 {

        /* renamed from: $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode */
        public static final /* synthetic */ int[] f215xdc0e830a;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            f215xdc0e830a = iArr;
            try {
                iArr[KeyguardSecurityModel.SecurityMode.FMM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f215xdc0e830a[KeyguardSecurityModel.SecurityMode.RMM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f215xdc0e830a[KeyguardSecurityModel.SecurityMode.KNOXGUARD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f215xdc0e830a[KeyguardSecurityModel.SecurityMode.AdminLock.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f215xdc0e830a[KeyguardSecurityModel.SecurityMode.SKTCarrierLock.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f215xdc0e830a[KeyguardSecurityModel.SecurityMode.SKTCarrierPassword.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static int calculateLandscapeViewWidth(int i, Context context) {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.notification_content_margin_end);
        return ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).isInDisplayFingerprintMarginAccepted() ? ((i - dimensionPixelSize) - DeviceState.sInDisplayFingerprintHeight) / 2 : (i - (dimensionPixelSize * 2)) / 2;
    }

    public static boolean checkFullscreenBouncer(KeyguardSecurityModel.SecurityMode securityMode) {
        boolean z = (((securityMode == KeyguardSecurityModel.SecurityMode.SimPin || securityMode == KeyguardSecurityModel.SecurityMode.SimPuk) || securityMode == KeyguardSecurityModel.SecurityMode.FMM || securityMode == KeyguardSecurityModel.SecurityMode.RMM || securityMode == KeyguardSecurityModel.SecurityMode.KNOXGUARD || securityMode == KeyguardSecurityModel.SecurityMode.SKTCarrierLock || securityMode == KeyguardSecurityModel.SecurityMode.SKTCarrierPassword) || securityMode == KeyguardSecurityModel.SecurityMode.AdminLock) || securityMode == KeyguardSecurityModel.SecurityMode.Permanent;
        if (LsRune.SECURITY_SIM_PERSO_LOCK) {
            z = z || securityMode == KeyguardSecurityModel.SecurityMode.SimPerso;
        }
        if (SafeUIState.isSysUiSafeModeEnabled()) {
            return z || securityMode != KeyguardSecurityModel.SecurityMode.None;
        }
        return z;
    }

    public static int getCurrentRotation(Context context) {
        if (DeviceState.shouldEnableKeyguardScreenRotation(context)) {
            return DeviceState.getRotation(context.getResources().getConfiguration().windowConfiguration.getRotation());
        }
        return 0;
    }

    public static int getFoldPINContainerHeight(Context context) {
        Resources resources = context.getResources();
        float height = resources.getConfiguration().windowConfiguration.getBounds().height();
        return (int) ((resources.getFloat(com.android.systemui.R.dimen.fold_num_pad_key_bottom_margin_ratio) * height * 3.0f) + (resources.getFloat(com.android.systemui.R.dimen.fold_num_pad_key_size_ratio) * height * 4.0f));
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
        int min = Math.min(bounds.width(), bounds.height());
        return (int) ((resources.getFloat(com.android.systemui.R.dimen.num_pad_key_bottom_margin_ratio) * Math.max(bounds.width(), bounds.height()) * 3.0f) + (resources.getFloat(com.android.systemui.R.dimen.num_pad_key_size_ratio) * min * 4.0f));
    }

    public static int getSimSlotNum(int i) {
        SubscriptionInfo subscriptionInfoForSubId = ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).getSubscriptionInfoForSubId(i);
        if (subscriptionInfoForSubId != null) {
            return subscriptionInfoForSubId.getSimSlotIndex();
        }
        return -1;
    }

    public static SpannableStringBuilder getStrongAuthPopupString(Context context, final KeyguardSecurityModel.SecurityMode securityMode, final EditText editText, int i) {
        final KeyguardTextBuilder keyguardTextBuilder = KeyguardTextBuilder.getInstance(context);
        String promptSecurityMessage = keyguardTextBuilder.getPromptSecurityMessage(securityMode, i);
        if (TextUtils.isEmpty(promptSecurityMessage) || isEmptyStrongAuthPopupMessage(context, securityMode)) {
            return null;
        }
        if (i != 2 && i != 7 && i != 17) {
            return null;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int indexOf = promptSecurityMessage.indexOf("%1$s");
        int indexOf2 = promptSecurityMessage.indexOf("%2$s") - 4;
        if (indexOf < 0 || indexOf2 < 0) {
            Log.d("KeyguardTextBuilder", "Unnecessary to update this message : promptReasonString = ".concat(promptSecurityMessage));
            return null;
        }
        spannableStringBuilder.append((CharSequence) String.format(promptSecurityMessage, "", ""));
        if (!"none".equals(keyguardTextBuilder.mBiometricType)) {
            spannableStringBuilder.setSpan(new ClickableSpan() { // from class: com.android.keyguard.KeyguardTextBuilder.1
                public final /* synthetic */ EditText val$passwordEntry;
                public final /* synthetic */ KeyguardSecurityModel.SecurityMode val$securityMode;

                public C07881(final KeyguardSecurityModel.SecurityMode securityMode2, final EditText editText2) {
                    r2 = securityMode2;
                    r3 = editText2;
                }

                @Override // android.text.style.ClickableSpan
                public final void onClick(View view) {
                    StrongAuthPopup strongAuthPopup = KeyguardTextBuilder.this.mStrongAuthPopup;
                    if (strongAuthPopup != null) {
                        strongAuthPopup.dismiss();
                        KeyguardTextBuilder.this.mStrongAuthPopup = null;
                    }
                    KeyguardTextBuilder.this.mStrongAuthPopup = new StrongAuthPopup(KeyguardTextBuilder.this.mContext, r2, r3);
                    KeyguardTextBuilder.this.mStrongAuthPopup.updatePopup();
                    StrongAuthPopup strongAuthPopup2 = KeyguardTextBuilder.this.mStrongAuthPopup;
                    strongAuthPopup2.mHandler.postDelayed(new StrongAuthPopup$$ExternalSyntheticLambda0(strongAuthPopup2, 0), 100L);
                    SystemUIAnalytics.sendEventLog(DATA.DM_FIELD_INDEX.VOLTE_DOMAIN_UI_SHOW, "1073");
                }

                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public final void updateDrawState(TextPaint textPaint) {
                    textPaint.setUnderlineText(true);
                    textPaint.setFakeBoldText(true);
                }
            }, indexOf, indexOf2, 33);
        }
        return spannableStringBuilder;
    }

    public static int getStrongAuthPrompt() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
        if (keyguardUpdateMonitor.is2StepVerification()) {
            return 0;
        }
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        KeyguardUpdateMonitor.StrongAuthTracker strongAuthTracker = keyguardUpdateMonitor.mStrongAuthTracker;
        int strongAuthForUser = strongAuthTracker.getStrongAuthForUser(currentUser);
        boolean isNonStrongBiometricAllowedAfterIdleTimeout = strongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(currentUser);
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
        return !isNonStrongBiometricAllowedAfterIdleTimeout ? 17 : 0;
    }

    public static int getTabletPINContainerHeight(Context context) {
        Resources resources = context.getResources();
        Rect bounds = resources.getConfiguration().windowConfiguration.getBounds();
        int min = Math.min(bounds.width(), bounds.height());
        return (int) ((resources.getFloat(com.android.systemui.R.dimen.tablet_num_pad_key_bottom_margin_ratio) * Math.max(bounds.width(), bounds.height()) * 3.0f) + (resources.getFloat(com.android.systemui.R.dimen.tablet_num_pad_key_size_ratio) * min * 4.0f));
    }

    public static void initMainDisplaySize(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        Rect bounds = configuration.windowConfiguration.getBounds();
        int width = bounds.width();
        int height = bounds.height();
        if (configuration.semDisplayDeviceType != 0) {
            if (sSubDisplayWidth == 0) {
                sSubDisplayWidth = Math.min(width, height);
            }
        } else if (sMainDisplayWidth == 0 && sMainDisplayHeight == 0) {
            sMainDisplayWidth = Math.min(width, height);
            sMainDisplayHeight = Math.max(width, height);
            sViewFlipperWidth = (int) (context.getResources().getFloat(com.android.systemui.R.dimen.kg_message_area_width_dual_display_ratio) * sMainDisplayWidth);
            sPasswordViewFlipperWidth = (int) (context.getResources().getFloat(com.android.systemui.R.dimen.kg_password_message_area_width_dual_display_ratio) * sMainDisplayWidth);
        }
    }

    public static boolean isArrowViewSupported(KeyguardSecurityModel.SecurityMode securityMode) {
        if (((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).isDualDarInnerAuthShowing()) {
            return false;
        }
        return securityMode == KeyguardSecurityModel.SecurityMode.PIN || securityMode == KeyguardSecurityModel.SecurityMode.Pattern || securityMode == KeyguardSecurityModel.SecurityMode.SimPin || securityMode == KeyguardSecurityModel.SecurityMode.SimPuk;
    }

    public static boolean isEmptyStrongAuthPopupMessage(Context context, KeyguardSecurityModel.SecurityMode securityMode) {
        return KeyguardTextBuilder.getInstance(context).getStrongAuthTimeOutMessage(securityMode).isEmpty();
    }

    public static boolean matchSignature(Signature signature) {
        MessageDigest messageDigest;
        byte[] byteArray = signature.toByteArray();
        try {
            messageDigest = MessageDigest.getInstance("SHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            messageDigest = null;
        }
        messageDigest.update(byteArray);
        byte[] digest = messageDigest.digest();
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] cArr2 = new char[digest.length * 2];
        for (int i = 0; i < digest.length; i++) {
            int i2 = digest[i] & 255;
            int i3 = i * 2;
            cArr2[i3] = cArr[i2 >>> 4];
            cArr2[i3 + 1] = cArr[i2 & 15];
        }
        return "0848EDB80D10A557AA0D885AB3B669C915DCD6BCA8D78715568A06876AACD7CD".equals(new String(cArr2));
    }
}
