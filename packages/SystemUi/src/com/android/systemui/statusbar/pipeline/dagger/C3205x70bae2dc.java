package com.android.systemui.statusbar.pipeline.dagger;

import com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconsViewModel;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.pipeline.dagger.StatusBarPipelineModule$Companion$provideFirstMobileSubShowingNetworkTypeIconProvider$1 */
/* loaded from: classes2.dex */
public final class C3205x70bae2dc implements Supplier {
    public final /* synthetic */ MobileIconsViewModel $mobileIconsViewModel;

    public C3205x70bae2dc(MobileIconsViewModel mobileIconsViewModel) {
        this.$mobileIconsViewModel = mobileIconsViewModel;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return this.$mobileIconsViewModel.firstMobileSubShowingNetworkTypeIcon;
    }
}
