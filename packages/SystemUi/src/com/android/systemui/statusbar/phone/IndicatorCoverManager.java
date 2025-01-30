package com.android.systemui.statusbar.phone;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class IndicatorCoverManager {
    public final CentralSurfaces centralSurfaces;
    public final Context context;
    public final IndicatorGardenPresenter indicatorGardenPresenter;
    public boolean needToApplyForCoverPaddings;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public IndicatorCoverManager(Context context, CentralSurfaces centralSurfaces, IndicatorGardenPresenter indicatorGardenPresenter) {
        this.context = context;
        this.centralSurfaces = centralSurfaces;
        this.indicatorGardenPresenter = indicatorGardenPresenter;
    }

    public final void updateCoverMargin(int i, boolean z) {
        boolean z2 = this.needToApplyForCoverPaddings;
        if (i != 8) {
            z = false;
        }
        this.needToApplyForCoverPaddings = z;
        int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R.dimen.clear_cover_status_bar_margin);
        boolean z3 = this.needToApplyForCoverPaddings;
        int i2 = z3 ? dimensionPixelSize : 0;
        if (z3 != z2) {
            RecyclerView$$ExternalSyntheticOutline0.m46m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("updateCoverMargin() prvCovered: ", z2, " >>> mNeedToApplyForCoverPaddings: ", z3, " ,getDefaultSidePadding(): "), i2, "IndicatorCoverManager");
            this.indicatorGardenPresenter.updateGardenWithNewModel(((CentralSurfacesImpl) this.centralSurfaces).mPhoneStatusBarViewController);
        }
    }
}
