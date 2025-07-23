package com.android.systemui.decor;

import android.content.Context;
import android.view.View;
import com.android.systemui.RegionInterceptingFrameLayout;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class DecorProvider {
    public abstract List getAlignedBounds();

    public abstract int getViewId();

    public abstract View inflateView(Context context, RegionInterceptingFrameLayout regionInterceptingFrameLayout, int i, int i2);

    public abstract void onReloadResAndMeasure(View view, int i, int i2, int i3, String str);

    public final String toString() {
        return getClass().getSimpleName() + "{alignedBounds=" + getAlignedBounds() + "}";
    }
}
