package com.android.systemui.media;

import android.view.View;
import kotlin.Pair;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class ViewPagerHelper$createPageAdapter$1$$ExternalSyntheticLambda0 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Integer num = (Integer) obj;
        num.getClass();
        View view = ((SecMediaControlPanel) obj2).mViewHolder.playerView;
        if (view == null) {
            view = null;
        }
        return new Pair(num, view);
    }
}
