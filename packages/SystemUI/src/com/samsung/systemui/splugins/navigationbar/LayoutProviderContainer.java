package com.samsung.systemui.splugins.navigationbar;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface LayoutProviderContainer {
    static /* synthetic */ LayoutProvider updateLayoutProvider$default(LayoutProviderContainer layoutProviderContainer, boolean z, boolean z2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: updateLayoutProvider");
        }
        if ((i & 1) != 0) {
            z = false;
        }
        return layoutProviderContainer.updateLayoutProvider(z, z2);
    }

    default LayoutProvider updateLayoutProvider(boolean z, boolean z2) {
        return null;
    }
}
