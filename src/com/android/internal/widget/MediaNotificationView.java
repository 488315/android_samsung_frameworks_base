package com.android.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import java.util.ArrayList;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class MediaNotificationView extends FrameLayout {
    private ArrayList<VisibilityChangeListener> mListeners;

    public interface VisibilityChangeListener {
        void onAggregatedVisibilityChanged(boolean z);
    }

    public MediaNotificationView(Context context) {
        this(context, null);
    }

    public MediaNotificationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MediaNotificationView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MediaNotificationView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        if (this.mListeners != null) {
            for (int i = 0; i < this.mListeners.size(); i++) {
                this.mListeners.get(i).onAggregatedVisibilityChanged(z);
            }
        }
    }

    public void addVisibilityListener(VisibilityChangeListener visibilityChangeListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        if (this.mListeners.contains(visibilityChangeListener)) {
            return;
        }
        this.mListeners.add(visibilityChangeListener);
    }

    public void removeVisibilityListener(VisibilityChangeListener visibilityChangeListener) {
        ArrayList<VisibilityChangeListener> arrayList = this.mListeners;
        if (arrayList != null) {
            arrayList.remove(visibilityChangeListener);
        }
    }
}
