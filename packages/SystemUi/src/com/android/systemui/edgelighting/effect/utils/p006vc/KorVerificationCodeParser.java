package com.android.systemui.edgelighting.effect.utils.p006vc;

import android.content.Context;
import android.util.Slog;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KorVerificationCodeParser extends VerificationCodeParserBase {
    public final Context mContext;

    public KorVerificationCodeParser(Context context) {
        this.mContext = context;
    }

    @Override // com.android.systemui.edgelighting.effect.utils.p006vc.VerificationCodeParser
    public final String getVerificationCode(String str) {
        Slog.d("ORC/KorVerificationCodeParser", "getVerificationCode");
        Context context = this.mContext;
        return VerificationCodeParserBase.getVerificationCodeWithoutDefault(str, context.getResources().getStringArray(R.array.verification_code_strong_kor), context.getResources().getStringArray(R.array.verification_code_weak_kor));
    }
}
