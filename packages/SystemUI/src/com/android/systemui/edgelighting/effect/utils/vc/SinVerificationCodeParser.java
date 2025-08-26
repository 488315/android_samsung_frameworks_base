package com.android.systemui.edgelighting.effect.utils.vc;

import android.content.Context;
import android.content.res.Resources;
import android.util.Slog;
import com.android.systemui.R;

/* loaded from: classes2.dex */
public class SinVerificationCodeParser extends VerificationCodeParserBase {
    public final Context mContext;

    public SinVerificationCodeParser(Context context) {
        this.mContext = context;
    }

    @Override // com.android.systemui.edgelighting.effect.utils.vc.VerificationCodeParser
    public final String getVerificationCode(String str) throws Resources.NotFoundException {
        Slog.d("ORC/SinVerificationCodeParser", "getVerificationCode");
        return VerificationCodeParserBase.getVerificationCode(this.mContext, str, this.mContext.getResources().getStringArray(R.array.verification_code_strong_sin), this.mContext.getResources().getStringArray(R.array.verification_code_weak_sin));
    }
}
