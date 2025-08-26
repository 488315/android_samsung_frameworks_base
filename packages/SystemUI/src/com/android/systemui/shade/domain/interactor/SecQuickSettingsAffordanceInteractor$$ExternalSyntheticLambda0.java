package com.android.systemui.shade.domain.interactor;

import com.android.systemui.Dependency;
import com.android.systemui.statusbar.CommandQueue;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class SecQuickSettingsAffordanceInteractor$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        int i = SecQuickSettingsAffordanceInteractor.$r8$clinit;
        return (CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class);
    }
}
