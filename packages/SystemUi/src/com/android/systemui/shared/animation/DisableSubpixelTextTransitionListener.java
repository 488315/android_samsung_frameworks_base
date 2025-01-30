package com.android.systemui.shared.animation;

import android.os.Trace;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DisableSubpixelTextTransitionListener implements UnfoldTransitionProgressProvider.TransitionProgressListener {
    public final List childrenTextViews = new ArrayList();
    public boolean isTransitionInProgress;
    public final ViewGroup rootView;

    public DisableSubpixelTextTransitionListener(ViewGroup viewGroup) {
        this.rootView = viewGroup;
    }

    public static void getAllChildTextView(ViewGroup viewGroup, List list) {
        if (viewGroup != null) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt instanceof ViewGroup) {
                    getAllChildTextView((ViewGroup) childAt, list);
                } else if ((childAt instanceof TextView) && (((TextView) childAt).getPaintFlags() & 128) <= 0) {
                    ((ArrayList) list).add(new WeakReference(childAt));
                }
            }
        }
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionFinished() {
        if (this.isTransitionInProgress) {
            this.isTransitionInProgress = false;
            boolean isTagEnabled = Trace.isTagEnabled(4096L);
            List list = this.childrenTextViews;
            if (!isTagEnabled) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    TextView textView = (TextView) ((WeakReference) it.next()).get();
                    if (textView != null) {
                        textView.setPaintFlags(textView.getPaintFlags() & (-129));
                    }
                }
                ((ArrayList) list).clear();
                return;
            }
            Trace.traceBegin(4096L, "subpixelFlagEnableForTextView");
            try {
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    TextView textView2 = (TextView) ((WeakReference) it2.next()).get();
                    if (textView2 != null) {
                        textView2.setPaintFlags(textView2.getPaintFlags() & (-129));
                    }
                }
                ((ArrayList) list).clear();
                Unit unit = Unit.INSTANCE;
            } finally {
                Trace.traceEnd(4096L);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x009a, code lost:
    
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x009e, code lost:
    
        throw r6;
     */
    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onTransitionStarted() {
        this.isTransitionInProgress = true;
        boolean isTagEnabled = Trace.isTagEnabled(4096L);
        List list = this.childrenTextViews;
        ViewGroup viewGroup = this.rootView;
        if (!isTagEnabled) {
            if (Trace.isTagEnabled(4096L)) {
                Trace.traceBegin(4096L, "subpixelFlagTraverseHierarchy");
                try {
                    getAllChildTextView(viewGroup, list);
                    Unit unit = Unit.INSTANCE;
                } finally {
                }
            } else {
                getAllChildTextView(viewGroup, list);
            }
            if (!Trace.isTagEnabled(4096L)) {
                Iterator it = ((ArrayList) list).iterator();
                while (it.hasNext()) {
                    TextView textView = (TextView) ((WeakReference) it.next()).get();
                    if (textView != null) {
                        textView.setPaintFlags(textView.getPaintFlags() | 128);
                    }
                }
                return;
            }
            Trace.traceBegin(4096L, "subpixelFlagDisableForTextView");
            try {
                Iterator it2 = ((ArrayList) list).iterator();
                while (it2.hasNext()) {
                    TextView textView2 = (TextView) ((WeakReference) it2.next()).get();
                    if (textView2 != null) {
                        textView2.setPaintFlags(textView2.getPaintFlags() | 128);
                    }
                }
                Unit unit2 = Unit.INSTANCE;
                return;
            } finally {
            }
        }
        Trace.traceBegin(4096L, "subpixelFlagSetForTextView");
        try {
            if (Trace.isTagEnabled(4096L)) {
                Trace.traceBegin(4096L, "subpixelFlagTraverseHierarchy");
                getAllChildTextView(viewGroup, list);
                Unit unit3 = Unit.INSTANCE;
                Trace.traceEnd(4096L);
            } else {
                getAllChildTextView(viewGroup, list);
            }
            if (Trace.isTagEnabled(4096L)) {
                Trace.traceBegin(4096L, "subpixelFlagDisableForTextView");
                Iterator it3 = ((ArrayList) list).iterator();
                while (it3.hasNext()) {
                    TextView textView3 = (TextView) ((WeakReference) it3.next()).get();
                    if (textView3 != null) {
                        textView3.setPaintFlags(textView3.getPaintFlags() | 128);
                    }
                }
                Unit unit4 = Unit.INSTANCE;
                Trace.traceEnd(4096L);
            } else {
                Iterator it4 = ((ArrayList) list).iterator();
                while (it4.hasNext()) {
                    TextView textView4 = (TextView) ((WeakReference) it4.next()).get();
                    if (textView4 != null) {
                        textView4.setPaintFlags(textView4.getPaintFlags() | 128);
                    }
                }
            }
            Unit unit5 = Unit.INSTANCE;
        } catch (Throwable th) {
            throw th;
        } finally {
        }
    }
}
