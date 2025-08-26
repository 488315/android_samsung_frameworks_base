package com.android.systemui.edgelighting.effect.utils.vc;

import android.content.Context;
import android.content.res.Resources;
import android.util.Slog;
import com.android.systemui.R;

/* loaded from: classes2.dex */
public class ThlVerificationCodeParser extends VerificationCodeParserBase {
    public final Context mContext;

    public ThlVerificationCodeParser(Context context) {
        this.mContext = context;
    }

    @Override // com.android.systemui.edgelighting.effect.utils.vc.VerificationCodeParser
    public final String getVerificationCode(String str) throws Resources.NotFoundException {
        Slog.d("ORC/ThlVerificationCodeParser", "getVerificationCode");
        return VerificationCodeParserBase.getVerificationCode(this.mContext, str, this.mContext.getResources().getStringArray(R.array.verification_code_strong_thl), this.mContext.getResources().getStringArray(R.array.verification_code_weak_thl));
    }
}
