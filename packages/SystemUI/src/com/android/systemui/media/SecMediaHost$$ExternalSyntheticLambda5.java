package com.android.systemui.media;

import java.util.function.IntSupplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda5 implements IntSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecMediaHost f$0;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda5(SecMediaHost secMediaHost, int i) {
        this.$r8$classId = i;
        this.f$0 = secMediaHost;
    }

    @Override // java.util.function.IntSupplier
    public final int getAsInt() {
        int i = this.$r8$classId;
        SecMediaHost secMediaHost = this.f$0;
        switch (i) {
        }
        return secMediaHost.mIsRTL;
    }
}
