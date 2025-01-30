package com.android.systemui.media;

import java.util.function.BiFunction;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda1 implements BiFunction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecMediaHost f$0;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda1(SecMediaHost secMediaHost, int i) {
        this.$r8$classId = i;
        this.f$0 = secMediaHost;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        switch (this.$r8$classId) {
        }
        return Integer.valueOf(this.f$0.getMediaPlayerNum(((Boolean) obj).booleanValue(), (MediaType) obj2));
    }
}
