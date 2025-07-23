package com.android.systemui.edgelighting.effect.utils.vc;

import android.content.Context;
import android.util.Slog;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class MalVerificationCodeParser extends VerificationCodeParserBase {
    public final Context mContext;

    public MalVerificationCodeParser(Context context) {
        this.mContext = context;
    }

    @Override // com.android.systemui.edgelighting.effect.utils.vc.VerificationCodeParser
    public final String getVerificationCode(String str) {
        Slog.d("ORC/MalVerificationCodeParser", "getVerificationCode");
        return VerificationCodeParserBase.getVerificationCode(this.mContext, str, this.mContext.getResources().getStringArray(R.array.verification_code_strong_mal), this.mContext.getResources().getStringArray(R.array.verification_code_weak_mal));
    }
}
