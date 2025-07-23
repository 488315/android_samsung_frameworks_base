package com.android.systemui.media;

import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecMediaHost f$0;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda0(SecMediaHost secMediaHost, int i) {
        this.$r8$classId = i;
        this.f$0 = secMediaHost;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        SecMediaHost secMediaHost = this.f$0;
        switch (i) {
            case 0:
                return secMediaHost.mContext;
            default:
                return secMediaHost.mMediaFrames;
        }
    }
}
