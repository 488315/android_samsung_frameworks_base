package androidx.compose.material.ripple;

import android.content.Context;
import android.view.ViewGroup;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class RippleContainer extends ViewGroup {
    public final int MaxRippleHosts;
    public int nextHostIndex;
    public final RippleHostMap rippleHostMap;
    public final List rippleHosts;
    public final List unusedRippleHosts;

    public RippleContainer(Context context) {
        super(context);
        this.MaxRippleHosts = 5;
        ArrayList arrayList = new ArrayList();
        this.rippleHosts = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.unusedRippleHosts = arrayList2;
        this.rippleHostMap = new RippleHostMap();
        setClipChildren(false);
        RippleHostView rippleHostView = new RippleHostView(context);
        addView(rippleHostView);
        arrayList.add(rippleHostView);
        arrayList2.add(rippleHostView);
        this.nextHostIndex = 1;
        setTag(R.id.hide_in_inspector_tag, Boolean.TRUE);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        setMeasuredDimension(0, 0);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }
}
