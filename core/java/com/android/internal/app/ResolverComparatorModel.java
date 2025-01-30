package com.android.internal.app;

import android.content.p002pm.ResolveInfo;
import com.android.internal.app.chooser.TargetInfo;
import java.util.Comparator;

/* loaded from: classes4.dex */
interface ResolverComparatorModel {
    Comparator<ResolveInfo> getComparator();

    float getScore(TargetInfo targetInfo);

    void notifyOnTargetSelected(TargetInfo targetInfo);
}
