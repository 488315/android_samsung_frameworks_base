package com.android.systemui.unfold.util;

import android.content.Context;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.updates.RotationChangeProvider;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NaturalRotationUnfoldProgressProvider implements UnfoldTransitionProgressProvider {
    public final Context context;
    public boolean isNaturalRotation;
    public final RotationChangeProvider rotationChangeProvider;
    public final NaturalRotationUnfoldProgressProvider$rotationListener$1 rotationListener = new NaturalRotationUnfoldProgressProvider$rotationListener$1(this);
    public final ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider;

    public NaturalRotationUnfoldProgressProvider(Context context, RotationChangeProvider rotationChangeProvider, UnfoldTransitionProgressProvider unfoldTransitionProgressProvider) {
        this.context = context;
        this.rotationChangeProvider = rotationChangeProvider;
        this.scopedUnfoldTransitionProgressProvider = new ScopedUnfoldTransitionProgressProvider(unfoldTransitionProgressProvider);
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void addCallback(Object obj) {
        ((ArrayList) this.scopedUnfoldTransitionProgressProvider.listeners).add((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void removeCallback(Object obj) {
        ((ArrayList) this.scopedUnfoldTransitionProgressProvider.listeners).remove((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }
}
