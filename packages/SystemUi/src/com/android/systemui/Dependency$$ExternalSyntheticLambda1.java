package com.android.systemui;

import com.android.systemui.Dependency;
import dagger.Lazy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class Dependency$$ExternalSyntheticLambda1 implements Dependency.LazyDependencyCreator {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Lazy f$0;

    public /* synthetic */ Dependency$$ExternalSyntheticLambda1(Lazy lazy, int i) {
        this.$r8$classId = i;
        this.f$0 = lazy;
    }

    @Override // com.android.systemui.Dependency.LazyDependencyCreator
    public final Object createDependency() {
        int i = this.$r8$classId;
        Lazy lazy = this.f$0;
        switch (i) {
        }
        return lazy.get();
    }
}
