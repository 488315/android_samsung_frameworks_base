package com.android.systemui.unfold;

import android.content.Context;
import com.android.systemui.unfold.updates.RotationChangeProvider;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UnfoldTransitionModule$provideNaturalRotationProgressProvider$1 implements Function {
    public final /* synthetic */ Context $context;
    public final /* synthetic */ RotationChangeProvider $rotationChangeProvider;

    public UnfoldTransitionModule$provideNaturalRotationProgressProvider$1(Context context, RotationChangeProvider rotationChangeProvider) {
        this.$context = context;
        this.$rotationChangeProvider = rotationChangeProvider;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return new NaturalRotationUnfoldProgressProvider(this.$context, this.$rotationChangeProvider, (UnfoldTransitionProgressProvider) obj);
    }
}
