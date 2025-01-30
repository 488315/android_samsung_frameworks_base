package com.android.systemui.edgelighting.effect.utils.vc;

import android.content.Context;
import android.util.Slog;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NormalVerificationCodeParser extends VerificationCodeParserBase {
    public final Context mContext;

    public NormalVerificationCodeParser(Context context) {
        this.mContext = context;
    }

    @Override // com.android.systemui.edgelighting.effect.utils.vc.VerificationCodeParser
    public final String getVerificationCode(String str) {
        Slog.d("Normal/VerificationCodeParser", "getVerificationCode");
        return VerificationCodeParserBase.getVerificationCode(this.mContext, str, new String[0], new String[0]);
    }
}
