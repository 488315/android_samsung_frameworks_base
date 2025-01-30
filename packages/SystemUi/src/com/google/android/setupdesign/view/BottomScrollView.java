package com.google.android.setupdesign.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class BottomScrollView extends ScrollView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final RunnableC44241 checkScrollRunnable;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface BottomScrollListener {
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.google.android.setupdesign.view.BottomScrollView$1] */
    public BottomScrollView(Context context) {
        super(context);
        this.checkScrollRunnable = new Runnable() { // from class: com.google.android.setupdesign.view.BottomScrollView.1
            @Override // java.lang.Runnable
            public final void run() {
                BottomScrollView bottomScrollView = BottomScrollView.this;
                int i = BottomScrollView.$r8$clinit;
                bottomScrollView.getClass();
            }
        };
    }

    public BottomScrollListener getBottomScrollListener() {
        return null;
    }

    @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        View childAt = getChildAt(0);
        if (childAt != null) {
            Math.max(0, ((childAt.getMeasuredHeight() - i4) + i2) - getPaddingBottom());
        }
        if (i4 - i2 > 0) {
            post(this.checkScrollRunnable);
        }
    }

    @Override // android.view.View
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.google.android.setupdesign.view.BottomScrollView$1] */
    public BottomScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.checkScrollRunnable = new Runnable() { // from class: com.google.android.setupdesign.view.BottomScrollView.1
            @Override // java.lang.Runnable
            public final void run() {
                BottomScrollView bottomScrollView = BottomScrollView.this;
                int i = BottomScrollView.$r8$clinit;
                bottomScrollView.getClass();
            }
        };
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.google.android.setupdesign.view.BottomScrollView$1] */
    public BottomScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.checkScrollRunnable = new Runnable() { // from class: com.google.android.setupdesign.view.BottomScrollView.1
            @Override // java.lang.Runnable
            public final void run() {
                BottomScrollView bottomScrollView = BottomScrollView.this;
                int i2 = BottomScrollView.$r8$clinit;
                bottomScrollView.getClass();
            }
        };
    }
}
