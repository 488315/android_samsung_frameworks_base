package com.android.systemui.qs.panels.ui.compose;

import com.android.systemui.qs.panels.shared.model.SizedTile;
import com.android.systemui.qs.panels.ui.viewmodel.BounceableTileViewModel;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class BounceableInfoKt {
    public static final BounceableInfo bounceableInfo(List list, SizedTile sizedTile, int i, int i2, int i3, boolean z, boolean z2) {
        boolean z3 = i2 == i3 - sizedTile.getWidth();
        BounceableTileViewModel bounceableTileViewModel = (BounceableTileViewModel) CollectionsKt___CollectionsKt.getOrNull(i - 1, list);
        BounceableTileViewModel bounceableTileViewModel2 = null;
        if (bounceableTileViewModel == null || z) {
            bounceableTileViewModel = null;
        }
        BounceableTileViewModel bounceableTileViewModel3 = (BounceableTileViewModel) CollectionsKt___CollectionsKt.getOrNull(i + 1, list);
        if (bounceableTileViewModel3 != null && !z2) {
            bounceableTileViewModel2 = bounceableTileViewModel3;
        }
        return new BounceableInfo((BounceableTileViewModel) list.get(i), bounceableTileViewModel, bounceableTileViewModel2, true ^ z3);
    }
}
