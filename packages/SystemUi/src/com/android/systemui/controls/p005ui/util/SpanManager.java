package com.android.systemui.controls.p005ui.util;

import android.content.Context;
import android.graphics.Insets;
import android.util.Log;
import android.util.Size;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SpanManager {
    public final LayoutUtil layoutUtil;
    public final Map spanInfos = new LinkedHashMap();
    public int maxSpan = 1;

    public SpanManager(LayoutUtil layoutUtil) {
        this.layoutUtil = layoutUtil;
    }

    public final void updateSpanInfos(int i) {
        int i2;
        Map map = this.spanInfos;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = (LinkedHashMap) map;
        Iterator it = linkedHashMap2.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Map.Entry entry = (Map.Entry) it.next();
            if ((((SpanInfo) entry.getValue()).width <= 0 ? 0 : 1) != 0) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            int i3 = ((SpanInfo) entry2.getValue()).width;
            LayoutUtil layoutUtil = this.layoutUtil;
            if (i > 0) {
                ((SpanInfo) entry2.getValue()).numberPerLine = layoutUtil.getAvailableSpanCount(i, i3);
            } else {
                SpanInfo spanInfo = (SpanInfo) entry2.getValue();
                Context context = layoutUtil.context;
                WindowMetrics currentWindowMetrics = ((WindowManager) context.getSystemService("window")).getCurrentWindowMetrics();
                Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout());
                Size size = new Size(currentWindowMetrics.getBounds().width() - (insetsIgnoringVisibility.right + insetsIgnoringVisibility.left), currentWindowMetrics.getBounds().height() - (insetsIgnoringVisibility.top + insetsIgnoringVisibility.bottom));
                float f = context.getResources().getFloat(R.integer.controls_basic_width_percentage);
                if (layoutUtil.context.getResources().getConfiguration().screenHeightDp <= 411) {
                    f = 1.0f;
                }
                spanInfo.numberPerLine = layoutUtil.getAvailableSpanCount((int) (size.getWidth() * f), i3);
            }
        }
        Collection values = linkedHashMap2.values();
        ArrayList arrayList = new ArrayList();
        for (Object obj : values) {
            if (((SpanInfo) obj).numberPerLine > 0) {
                arrayList.add(obj);
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            int i4 = ((SpanInfo) it2.next()).numberPerLine;
            int i5 = i2 * i4;
            while (i4 != 0) {
                int i6 = i2 % i4;
                i2 = i4;
                i4 = i6;
            }
            i2 = i5 / i2;
        }
        this.maxSpan = i2;
        for (Map.Entry entry3 : linkedHashMap2.entrySet()) {
            ((SpanInfo) entry3.getValue()).span = this.maxSpan / ((SpanInfo) entry3.getValue()).numberPerLine;
        }
        Log.d("ControlsSpanManager", "SpanManager maxSpan=" + this.maxSpan);
        for (Map.Entry entry4 : linkedHashMap2.entrySet()) {
            Object key = entry4.getKey();
            int i7 = ((SpanInfo) entry4.getValue()).span;
            int i8 = ((SpanInfo) entry4.getValue()).numberPerLine;
            StringBuilder sb = new StringBuilder("SpanManager [");
            sb.append(key);
            sb.append("] span=");
            sb.append(i7);
            sb.append(", ");
            RecyclerView$$ExternalSyntheticOutline0.m46m(sb, i8, "ControlsSpanManager");
        }
    }
}
