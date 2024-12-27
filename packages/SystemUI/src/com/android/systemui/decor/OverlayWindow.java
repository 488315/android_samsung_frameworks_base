package com.android.systemui.decor;

import android.content.Context;
import android.view.View;
import com.android.systemui.RegionInterceptingFrameLayout;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Pair;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class OverlayWindow {
    public final Context context;
    public final RegionInterceptingFrameLayout rootView;
    public final Map viewProviderMap = new LinkedHashMap();

    public OverlayWindow(Context context) {
        this.context = context;
        this.rootView = new RegionInterceptingFrameLayout(context);
    }

    public final View getView(int i) {
        Pair pair = (Pair) ((LinkedHashMap) this.viewProviderMap).get(Integer.valueOf(i));
        if (pair != null) {
            return (View) pair.getFirst();
        }
        return null;
    }
}
