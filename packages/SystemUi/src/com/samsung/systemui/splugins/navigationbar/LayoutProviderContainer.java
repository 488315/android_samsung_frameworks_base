package com.samsung.systemui.splugins.navigationbar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface LayoutProviderContainer {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class DefaultImpls {
        public static LayoutProvider updateLayoutProvider(LayoutProviderContainer layoutProviderContainer, boolean z, boolean z2) {
            return null;
        }

        public static /* synthetic */ LayoutProvider updateLayoutProvider$default(LayoutProviderContainer layoutProviderContainer, boolean z, boolean z2, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: updateLayoutProvider");
            }
            if ((i & 1) != 0) {
                z = false;
            }
            return layoutProviderContainer.updateLayoutProvider(z, z2);
        }
    }

    LayoutProvider updateLayoutProvider(boolean z, boolean z2);
}
