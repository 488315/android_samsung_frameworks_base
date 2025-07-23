package com.android.systemui.statusbar.phone;

import android.view.View;
import android.view.ViewGroup;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.StatusBarIconView;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class IndicatorGardenViewTreeLogHelper {
    public static void printChildWidthRecursive(PrintWriter printWriter, ViewGroup viewGroup, int i) {
        if (viewGroup == null) {
            return;
        }
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (childAt != null) {
                printDumpLog(printWriter, childAt, i2, i);
                if (childAt instanceof ViewGroup) {
                    printChildWidthRecursive(printWriter, (ViewGroup) childAt, i + 1);
                }
            }
        }
    }

    public static void printDumpLog(PrintWriter printWriter, View view, int i, int i2) {
        String str = " ";
        if (i2 >= 0) {
            int i3 = 0;
            while (true) {
                str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  |  ");
                if (i3 == i2) {
                    break;
                } else {
                    i3++;
                }
            }
        }
        printWriter.print(str + " idx=" + i);
        StringBuilder sb = new StringBuilder(":::");
        sb.append(view);
        printWriter.print(sb.toString());
        printWriter.print(", w:" + view.getWidth());
        printWriter.print(", mw:" + view.getMeasuredWidth());
        printWriter.print(", x:" + view.getX());
        printWriter.print(", px:" + view.getPivotX());
        printWriter.print(", tx:" + view.getTranslationX());
        printWriter.print(", lr:" + view.isLayoutRequested());
        if (view instanceof StatusBarIconView) {
            printWriter.print(", visibleState:" + ((StatusBarIconView) view).mVisibleState);
        }
        printWriter.println(", a:" + view.getAlpha());
    }
}
