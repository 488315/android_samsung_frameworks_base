package com.android.systemui.logging;

import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotiCinemaLogger {
    public final ArrayList mTmpLog = new ArrayList();

    public static String getViewLogLine(View view, int i, int i2) {
        if (view == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder("");
        for (int i3 = 0; i3 <= i2; i3++) {
            sb.append("  | ");
        }
        sb.append(" idx=" + i);
        sb.append(":::" + view.toString());
        sb.append(", w:" + view.getWidth());
        sb.append(", mw:" + view.getMeasuredWidth());
        sb.append(", h:" + view.getHeight());
        sb.append(", mh:" + view.getMeasuredHeight());
        sb.append(", x:" + view.getX());
        sb.append(", tx:" + view.getTranslationX());
        sb.append(", y:" + view.getY());
        sb.append(", ty:" + view.getTranslationY());
        sb.append(", lr:" + view.isLayoutRequested());
        sb.append(", clickable:" + view.isClickable());
        sb.append(", focusable:" + view.isFocusable());
        sb.append(", a:" + view.getAlpha());
        sb.append(", v:" + view.getVisibility());
        return sb.toString();
    }

    public final void visitLayoutTreeToAssembleLogLine(int i, ViewGroup viewGroup) {
        if (viewGroup == null) {
            return;
        }
        if (i == 0) {
            this.mTmpLog.add(getViewLogLine(viewGroup, -1, -1));
        }
        for (int i2 = 0; i2 < viewGroup.getChildCount() && i2 <= 50 && i <= 20; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (childAt != null) {
                this.mTmpLog.add(getViewLogLine(childAt, i2, i));
                if (childAt instanceof ViewGroup) {
                    visitLayoutTreeToAssembleLogLine(i + 1, (ViewGroup) childAt);
                }
            }
        }
    }
}
