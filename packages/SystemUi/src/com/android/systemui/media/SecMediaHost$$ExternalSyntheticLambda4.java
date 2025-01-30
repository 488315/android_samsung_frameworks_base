package com.android.systemui.media;

import java.util.HashMap;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda4 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HashMap f$0;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda4(int i, HashMap hashMap) {
        this.$r8$classId = i;
        this.f$0 = hashMap;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
        }
        return (SecMediaPlayerData) this.f$0.get((MediaType) obj);
    }
}
