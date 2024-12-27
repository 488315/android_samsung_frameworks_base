package com.android.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.util.function.Consumer;

@RemoteViews.RemoteView
public class ObservableTextView extends TextView {
    private Consumer<Integer> mOnVisibilityChangedListener;

    public ObservableTextView(Context context) {
        super(context);
    }

    public ObservableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ObservableTextView(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (changedView == this && this.mOnVisibilityChangedListener != null) {
            this.mOnVisibilityChangedListener.accept(Integer.valueOf(visibility));
        }
    }

    public void setOnVisibilityChangedListener(Consumer<Integer> listener) {
        this.mOnVisibilityChangedListener = listener;
    }
}
