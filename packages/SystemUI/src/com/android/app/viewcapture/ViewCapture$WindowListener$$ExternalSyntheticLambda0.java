package com.android.app.viewcapture;

import android.os.Trace;
import android.view.View;
import com.android.app.viewcapture.ViewCapture;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final /* synthetic */ class ViewCapture$WindowListener$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ ViewCapture.WindowListener f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ViewCapture.ViewPropertyRef viewPropertyRef;
        ViewCapture.ViewPropertyRef viewPropertyRef2;
        ViewCapture.ViewPropertyRef viewPropertyRef3;
        final ViewCapture.WindowListener windowListener = this.f$0;
        final ViewCapture.ViewPropertyRef viewPropertyRef4 = (ViewCapture.ViewPropertyRef) obj;
        Trace.beginSection("vc#copyCleanViewsFromLastFrameBg");
        long j = viewPropertyRef4.elapsedRealtimeNanos;
        int i = windowListener.mFrameIndexBg + 1;
        windowListener.mFrameIndexBg = i;
        if (i >= windowListener.this$0.mMemorySize) {
            windowListener.mFrameIndexBg = 0;
        }
        long[] jArr = windowListener.mFrameTimesNanosBg;
        int i2 = windowListener.mFrameIndexBg;
        jArr[i2] = j;
        ViewCapture.ViewPropertyRef viewPropertyRef5 = windowListener.mNodesBg[i2];
        final ViewCapture.ViewPropertyRef viewPropertyRef6 = viewPropertyRef4;
        ViewCapture.ViewPropertyRef viewPropertyRef7 = null;
        ViewCapture.ViewPropertyRef viewPropertyRef8 = null;
        while (true) {
            viewPropertyRef6.clazz = viewPropertyRef6.view.getClass();
            viewPropertyRef6.hashCode = viewPropertyRef6.view.hashCode();
            viewPropertyRef6.id = viewPropertyRef6.view.getId();
            viewPropertyRef6.view = null;
            if (viewPropertyRef5 == null) {
                viewPropertyRef = viewPropertyRef5;
                viewPropertyRef5 = new ViewCapture.ViewPropertyRef();
            } else {
                viewPropertyRef = viewPropertyRef5.next;
                viewPropertyRef5.next = null;
            }
            if (viewPropertyRef6.childCount < 0) {
                int i3 = viewPropertyRef6.hashCode;
                int i4 = windowListener.mFrameIndexBg;
                if (i4 == 0) {
                    i4 = windowListener.this$0.mMemorySize;
                }
                viewPropertyRef2 = windowListener.mNodesBg[i4 - 1];
                while (viewPropertyRef2 != null && viewPropertyRef2.hashCode != i3) {
                    viewPropertyRef2 = viewPropertyRef2.next;
                }
                if (viewPropertyRef2 != null) {
                    viewPropertyRef2.transferTo(viewPropertyRef6);
                } else {
                    viewPropertyRef6.childCount = 0;
                }
            } else {
                viewPropertyRef2 = null;
            }
            viewPropertyRef6.transferTo(viewPropertyRef5);
            if (viewPropertyRef7 == null) {
                viewPropertyRef7 = viewPropertyRef5;
            } else {
                viewPropertyRef8.next = viewPropertyRef5;
            }
            if (viewPropertyRef2 != null) {
                int i5 = viewPropertyRef2.childCount;
                while (i5 > 0) {
                    viewPropertyRef2 = viewPropertyRef2.next;
                    i5 = (i5 - 1) + viewPropertyRef2.childCount;
                    if (viewPropertyRef == null) {
                        viewPropertyRef3 = viewPropertyRef;
                        viewPropertyRef = new ViewCapture.ViewPropertyRef();
                    } else {
                        viewPropertyRef3 = viewPropertyRef.next;
                        viewPropertyRef.next = null;
                    }
                    viewPropertyRef2.transferTo(viewPropertyRef);
                    viewPropertyRef5.next = viewPropertyRef;
                    viewPropertyRef5 = viewPropertyRef;
                    viewPropertyRef = viewPropertyRef3;
                }
            }
            viewPropertyRef8 = viewPropertyRef5;
            viewPropertyRef5 = viewPropertyRef;
            ViewCapture.ViewPropertyRef viewPropertyRef9 = viewPropertyRef6.next;
            if (viewPropertyRef9 == null) {
                ViewCapture viewCapture = windowListener.this$0;
                Runnable runnable = new Runnable() { // from class: com.android.app.viewcapture.ViewCapture$WindowListener$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        ViewCapture.WindowListener windowListener2 = windowListener;
                        ViewCapture.ViewPropertyRef viewPropertyRef10 = viewPropertyRef4;
                        viewPropertyRef6.next = windowListener2.mPool;
                        windowListener2.mPool = viewPropertyRef10;
                    }
                };
                View view = windowListener.mRoot;
                viewCapture.getClass();
                ViewCapture.runOnUiThread(view, runnable);
                windowListener.mNodesBg[windowListener.mFrameIndexBg] = viewPropertyRef7;
                ViewCapture viewCapture2 = windowListener.this$0;
                String str = windowListener.name;
                viewCapture2.getClass();
                Trace.endSection();
                return;
            }
            viewPropertyRef6 = viewPropertyRef9;
        }
    }
}
