package com.android.systemui.media;

import java.util.function.Supplier;

public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda3 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecMediaHost f$0;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda3(SecMediaHost secMediaHost, int i) {
        this.$r8$classId = i;
        this.f$0 = secMediaHost;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        SecMediaHost secMediaHost = this.f$0;
        switch (i) {
            case 0:
                return secMediaHost.mMediaFrames;
            default:
                return secMediaHost.mContext;
        }
    }
}
