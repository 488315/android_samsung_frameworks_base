package com.android.systemui.controls.ui.util;

import android.graphics.Insets;
import android.util.Log;
import android.util.Size;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SpanManager {
    public final LayoutUtil layoutUtil;
    public final Map spanInfos = new LinkedHashMap();
    public int maxSpan = 1;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SpanManager(LayoutUtil layoutUtil) {
        this.layoutUtil = layoutUtil;
    }

    public final void updateSpanInfos(int i) {
        int i2;
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "updateSpanInfos layoutWidth = ", "SpanManager");
        Map map = this.spanInfos;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : ((LinkedHashMap) map).entrySet()) {
            if (((SpanInfo) entry.getValue()).width > 0) {
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
                Log.d("LayoutUtil", "getAvailableSpanCount context.config = " + layoutUtil.context.getResources().getConfiguration());
                WindowMetrics currentWindowMetrics = ((WindowManager) layoutUtil.context.getSystemService("window")).getCurrentWindowMetrics();
                Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout());
                Size size = new Size(currentWindowMetrics.getBounds().width() - (insetsIgnoringVisibility.right + insetsIgnoringVisibility.left), currentWindowMetrics.getBounds().height() - (insetsIgnoringVisibility.top + insetsIgnoringVisibility.bottom));
                spanInfo.numberPerLine = layoutUtil.getAvailableSpanCount((int) (size.getWidth() * layoutUtil.getWidthPercentBasic(layoutUtil.context.getResources().getFloat(R.integer.control_basic_width_percentage))), i3);
            }
        }
        Collection values = ((LinkedHashMap) this.spanInfos).values();
        ArrayList arrayList = new ArrayList();
        for (Object obj : values) {
            if (((SpanInfo) obj).numberPerLine > 0) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        int i4 = 1;
        while (it.hasNext()) {
            int i5 = ((SpanInfo) it.next()).numberPerLine;
            int i6 = i4 * i5;
            while (true) {
                int i7 = i5;
                i2 = i4;
                i4 = i7;
                if (i4 == 0) {
                    break;
                } else {
                    i5 = i2 % i4;
                }
            }
            i4 = i6 / i2;
        }
        this.maxSpan = i4;
        for (Map.Entry entry3 : ((LinkedHashMap) this.spanInfos).entrySet()) {
            ((SpanInfo) entry3.getValue()).span = this.maxSpan / ((SpanInfo) entry3.getValue()).numberPerLine;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(this.maxSpan, "updateSpanInfos maxSpan = ", "SpanManager");
        for (Map.Entry entry4 : ((LinkedHashMap) this.spanInfos).entrySet()) {
            Object key = entry4.getKey();
            int i8 = ((SpanInfo) entry4.getValue()).span;
            int i9 = ((SpanInfo) entry4.getValue()).numberPerLine;
            StringBuilder sb = new StringBuilder("updateSpanInfos [");
            sb.append(key);
            sb.append("] span = ");
            sb.append(i8);
            sb.append(", ");
            RecyclerView$$ExternalSyntheticOutline0.m(i9, "SpanManager", sb);
        }
    }
}
