package com.android.systemui.decor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public abstract class DecorProvider {
    public abstract List getAlignedBounds();

    public abstract int getViewId();

    public abstract View inflateView(Context context, ViewGroup viewGroup, int i, int i2);

    public abstract void onReloadResAndMeasure(View view, int i, int i2, int i3, String str);

    public final String toString() {
        return getClass().getSimpleName() + "{alignedBounds=" + getAlignedBounds() + "}";
    }
}
