package com.android.systemui.decor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class DecorProvider {
    public abstract List getAlignedBounds();

    public abstract int getViewId();

    public abstract View inflateView(Context context, ViewGroup viewGroup, int i, int i2);

    public abstract void onReloadResAndMeasure(View view, int i, int i2, int i3, String str);

    public final String toString() {
        return getClass().getSimpleName() + "{alignedBounds=" + getAlignedBounds() + "}";
    }
}
