package com.android.systemui.logging;

import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NotiCinemaLogger {
    public final ArrayList mTmpLog = new ArrayList();

    public final void visitLayoutTreeToAssembleLogLine(ViewGroup viewGroup, int i) {
        if (viewGroup == null) {
            return;
        }
        for (int i2 = 0; i2 < viewGroup.getChildCount() && i2 <= 50 && i <= 20; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (childAt != null) {
                ArrayList arrayList = this.mTmpLog;
                StringBuilder sb = new StringBuilder("");
                for (int i3 = 0; i3 < i; i3++) {
                    sb.append("  | ");
                }
                sb.append(" idx=" + i2);
                sb.append(":::" + childAt.toString());
                sb.append(", w:" + childAt.getWidth());
                sb.append(", mw:" + childAt.getMeasuredWidth());
                sb.append(", h:" + childAt.getHeight());
                sb.append(", mh:" + childAt.getMeasuredHeight());
                sb.append(", x:" + childAt.getX());
                sb.append(", tx:" + childAt.getTranslationX());
                sb.append(", y:" + childAt.getY());
                sb.append(", ty:" + childAt.getTranslationY());
                sb.append(", lr:" + childAt.isLayoutRequested());
                sb.append(", clickable:" + childAt.isClickable());
                sb.append(", focusable:" + childAt.isFocusable());
                sb.append(", a:" + childAt.getAlpha());
                sb.append(", v:" + childAt.getVisibility());
                arrayList.add(sb.toString());
                if (childAt instanceof ViewGroup) {
                    visitLayoutTreeToAssembleLogLine((ViewGroup) childAt, i + 1);
                }
            }
        }
    }
}
