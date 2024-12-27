package com.android.systemui.statusbar;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.android.keyguard.AlphaOptimizedLinearLayout;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class HeadsUpStatusBarView extends AlphaOptimizedLinearLayout {
    public final Rect mIconDrawingRect;
    public View mIconPlaceholder;
    public final Rect mLayoutedIconRect;
    public Runnable mOnDrawingRectChangedListener;
    public final HeadsUpStatusBarView$$ExternalSyntheticLambda0 mOnSensitivityChangedListener;
    public NotificationEntry mShowingEntry;
    public TextView mTextView;

    public HeadsUpStatusBarView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mIconPlaceholder = findViewById(R.id.icon_placeholder);
        this.mTextView = (TextView) findViewById(R.id.text);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Runnable runnable;
        super.onLayout(z, i, i2, i3, i4);
        this.mLayoutedIconRect.set(0, 0, 0, 0);
        Rect rect = this.mIconDrawingRect;
        float f = rect.left;
        rect.set(this.mLayoutedIconRect);
        if (f == this.mIconDrawingRect.left || (runnable = this.mOnDrawingRectChangedListener) == null) {
            return;
        }
        runnable.run();
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Bundle)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Bundle bundle = (Bundle) parcelable;
        super.onRestoreInstanceState(bundle.getParcelable("heads_up_status_bar_view_super_parcelable"));
        if (bundle.containsKey("visibility")) {
            setVisibility(bundle.getInt("visibility"));
        }
        if (bundle.containsKey("alpha")) {
            setAlpha(bundle.getFloat("alpha"));
        }
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("heads_up_status_bar_view_super_parcelable", super.onSaveInstanceState());
        bundle.putInt("visibility", getVisibility());
        bundle.putFloat("alpha", getAlpha());
        return bundle;
    }

    public final void setEntry(NotificationEntry notificationEntry) {
        NotificationEntry notificationEntry2 = this.mShowingEntry;
        if (notificationEntry2 != null) {
            notificationEntry2.mOnSensitivityChangedListeners.remove(this.mOnSensitivityChangedListener);
        }
        this.mShowingEntry = notificationEntry;
        if (notificationEntry != null) {
            CharSequence charSequence = (CharSequence) notificationEntry.mHeadsUpStatusBarText.getValue();
            if (((Boolean) notificationEntry.mSensitive.getValue()).booleanValue()) {
                charSequence = (CharSequence) notificationEntry.mHeadsUpStatusBarTextPublic.getValue();
            }
            this.mTextView.setText(charSequence);
            NotificationEntry notificationEntry3 = this.mShowingEntry;
            notificationEntry3.mOnSensitivityChangedListeners.addIfAbsent(this.mOnSensitivityChangedListener);
        }
    }

    public HeadsUpStatusBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HeadsUpStatusBarView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.HeadsUpStatusBarView$$ExternalSyntheticLambda0] */
    public HeadsUpStatusBarView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mLayoutedIconRect = new Rect();
        this.mIconDrawingRect = new Rect();
        this.mOnSensitivityChangedListener = new NotificationEntry.OnSensitivityChangedListener() { // from class: com.android.systemui.statusbar.HeadsUpStatusBarView$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.notification.collection.NotificationEntry.OnSensitivityChangedListener
            public final void onSensitivityChanged(NotificationEntry notificationEntry) {
                HeadsUpStatusBarView headsUpStatusBarView = HeadsUpStatusBarView.this;
                if (notificationEntry == headsUpStatusBarView.mShowingEntry) {
                    headsUpStatusBarView.setEntry(notificationEntry);
                    return;
                }
                throw new IllegalStateException("Got a sensitivity change for " + notificationEntry + " but mShowingEntry is " + headsUpStatusBarView.mShowingEntry);
            }
        };
    }

    public HeadsUpStatusBarView(Context context, View view, TextView textView) {
        this(context);
        this.mIconPlaceholder = view;
        this.mTextView = textView;
    }
}
