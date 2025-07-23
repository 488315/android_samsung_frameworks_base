package com.android.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import java.util.function.Consumer;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class ObservableTextView extends TextView {
    private Consumer<Integer> mOnVisibilityChangedListener;

    public ObservableTextView(Context context) {
        super(context);
    }

    public ObservableTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ObservableTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public ObservableTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onVisibilityChanged(View view, int i) {
        Consumer<Integer> consumer;
        super.onVisibilityChanged(view, i);
        if (view != this || (consumer = this.mOnVisibilityChangedListener) == null) {
            return;
        }
        consumer.accept(Integer.valueOf(i));
    }

    public void setOnVisibilityChangedListener(Consumer<Integer> consumer) {
        this.mOnVisibilityChangedListener = consumer;
    }
}
