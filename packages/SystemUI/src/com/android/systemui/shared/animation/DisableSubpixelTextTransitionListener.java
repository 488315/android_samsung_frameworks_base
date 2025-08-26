package com.android.systemui.shared.animation;

import android.os.Trace;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;

/* loaded from: classes3.dex */
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
            int i = 0;
            this.isTransitionInProgress = false;
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice("subpixelFlagEnableForTextView");
            }
            try {
                ArrayList arrayList = (ArrayList) this.childrenTextViews;
                int size = arrayList.size();
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    TextView textView = (TextView) ((WeakReference) obj).get();
                    if (textView != null) {
                        textView.setPaintFlags(textView.getPaintFlags() & (-129));
                    }
                }
                ((ArrayList) this.childrenTextViews).clear();
                Unit unit = Unit.INSTANCE;
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
            } catch (Throwable th) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
                throw th;
            }
        }
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionStarted() {
        this.isTransitionInProgress = true;
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("subpixelFlagSetForTextView");
        }
        try {
            zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice("subpixelFlagTraverseHierarchy");
            }
            try {
                getAllChildTextView(this.rootView, this.childrenTextViews);
                Unit unit = Unit.INSTANCE;
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
                boolean zIsEnabled2 = Trace.isEnabled();
                if (zIsEnabled2) {
                    TraceUtilsKt.beginSlice("subpixelFlagDisableForTextView");
                }
                try {
                    ArrayList arrayList = (ArrayList) this.childrenTextViews;
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        TextView textView = (TextView) ((WeakReference) obj).get();
                        if (textView != null) {
                            textView.setPaintFlags(textView.getPaintFlags() | 128);
                        }
                    }
                    Unit unit2 = Unit.INSTANCE;
                    if (zIsEnabled2) {
                        TraceUtilsKt.endSlice();
                    }
                } catch (Throwable th) {
                    if (zIsEnabled2) {
                        TraceUtilsKt.endSlice();
                    }
                    throw th;
                }
            } finally {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
            }
        } catch (Throwable th2) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th2;
        }
    }
}
