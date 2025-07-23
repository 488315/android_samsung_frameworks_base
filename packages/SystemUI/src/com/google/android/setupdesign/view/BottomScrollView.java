package com.google.android.setupdesign.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class BottomScrollView extends ScrollView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 checkScrollRunnable;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
