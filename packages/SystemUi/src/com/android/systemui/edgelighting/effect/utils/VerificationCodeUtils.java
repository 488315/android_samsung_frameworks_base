package com.android.systemui.edgelighting.effect.utils;

import android.content.Context;
import android.os.Debug;
import android.text.TextUtils;
import android.util.Slog;
import com.android.systemui.edgelighting.effect.utils.vc.ChnVerificationCodeParser;
import com.android.systemui.edgelighting.effect.utils.vc.IndVerificationCodeParser;
import com.android.systemui.edgelighting.effect.utils.vc.KorVerificationCodeParser;
import com.android.systemui.edgelighting.effect.utils.vc.MalVerificationCodeParser;
import com.android.systemui.edgelighting.effect.utils.vc.MymVerificationCodeParser;
import com.android.systemui.edgelighting.effect.utils.vc.NormalVerificationCodeParser;
import com.android.systemui.edgelighting.effect.utils.vc.PhiVerificationCodeParser;
import com.android.systemui.edgelighting.effect.utils.vc.SinVerificationCodeParser;
import com.android.systemui.edgelighting.effect.utils.vc.ThlVerificationCodeParser;
import com.android.systemui.edgelighting.effect.utils.vc.VnVerificationCodeParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class VerificationCodeUtils {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static String Verify_Code = null;
    public static int code_startIndex = -1;
    public static int code_endIndex = -1;

    public static String getVerifyCode() {
        if (DEBUG) {
            Slog.i("ORC/VerificationCodeUtils", "code is " + Verify_Code);
        }
        String str = Verify_Code;
        return str == null ? "" : str.contains("-") ? Verify_Code.replaceAll("[^\\d]", "") : Verify_Code;
    }

    public static boolean isVerificationCode(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            Slog.i("ORC/VerificationCodeUtils", "isVerificationCode() is false");
            return false;
        }
        Verify_Code = null;
        String verificationCode = (SalesCode.isVn ? new VnVerificationCodeParser(context) : SalesCode.isPHI ? new PhiVerificationCodeParser(context) : SalesCode.isMAL ? new MalVerificationCodeParser(context) : SalesCode.isTHL ? new ThlVerificationCodeParser(context) : SalesCode.isIND ? new IndVerificationCodeParser(context) : SalesCode.isMYM ? new MymVerificationCodeParser(context) : SalesCode.isSIN ? new SinVerificationCodeParser(context) : SalesCode.isKor ? new KorVerificationCodeParser(context) : SalesCode.isChn ? new ChnVerificationCodeParser(context) : new NormalVerificationCodeParser(context)).getVerificationCode(str);
        Verify_Code = verificationCode;
        if (verificationCode == null) {
            return false;
        }
        int indexOf = str.indexOf(verificationCode);
        code_startIndex = indexOf;
        code_endIndex = Verify_Code.length() + indexOf;
        return true;
    }
}
