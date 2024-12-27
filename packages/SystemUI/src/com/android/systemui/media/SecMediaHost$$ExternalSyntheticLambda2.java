package com.android.systemui.media;

import java.util.function.IntSupplier;

public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda2 implements IntSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecMediaHost f$0;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda2(SecMediaHost secMediaHost, int i) {
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
