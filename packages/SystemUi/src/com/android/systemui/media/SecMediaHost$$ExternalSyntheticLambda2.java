package com.android.systemui.media;

import java.util.function.IntSupplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda2 implements IntSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecMediaHost f$0;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda2(SecMediaHost secMediaHost, int i) {
        this.$r8$classId = i;
        this.f$0 = secMediaHost;
    }

    @Override // java.util.function.IntSupplier
    public final int getAsInt() {
        switch (this.$r8$classId) {
        }
        return this.f$0.mIsRTL;
    }
}
