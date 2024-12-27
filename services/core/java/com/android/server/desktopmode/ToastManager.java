package com.android.server.desktopmode;

import android.content.Context;
import android.util.IndentingPrintWriter;
import android.widget.Toast;
import com.android.server.UiThread;
import com.samsung.android.cover.CoverManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class ToastManager {
    public static volatile CoverManager sCoverManager;
    public static final List sToasts = new ArrayList();

    public static void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Current ToastManager state:");
        indentingPrintWriter.increaseIndent();
        List list = sToasts;
        synchronized (list) {
            try {
                indentingPrintWriter.println("sToasts (" + ((ArrayList) list).size() + "):");
                indentingPrintWriter.increaseIndent();
                Iterator it = ((ArrayList) list).iterator();
                while (it.hasNext()) {
                    indentingPrintWriter.println((Toast) it.next());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    public static void showToast(Context context, String str, int i) {
        if (str == null || str.isEmpty()) {
            return;
        }
        UiThread.getHandler().post(new ToastManager$$ExternalSyntheticLambda0(i, context, str, false));
    }
}
