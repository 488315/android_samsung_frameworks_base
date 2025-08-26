package com.android.systemui.shade;

import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class CombinedShadeHeadersConstraintManagerImpl$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        ConstraintSet constraintSet = (ConstraintSet) obj;
        CombinedShadeHeadersConstraintManagerImpl combinedShadeHeadersConstraintManagerImpl = CombinedShadeHeadersConstraintManagerImpl.INSTANCE;
        constraintSet.connect(R.id.date, 7, R.id.barrier, 6);
        constraintSet.createBarrier(R.id.barrier, 6, 0, R.id.shade_header_system_icons, R.id.privacy_container);
        constraintSet.connect(R.id.shade_header_system_icons, 6, R.id.date, 7);
        constraintSet.connect(R.id.privacy_container, 6, R.id.date, 7);
        constraintSet.constrainWidth(R.id.shade_header_system_icons, -2);
        constraintSet.constrainedWidth(R.id.date, true);
        constraintSet.constrainedWidth(R.id.shade_header_system_icons, true);
        return Unit.INSTANCE;
    }
}
