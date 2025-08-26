package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Dependency;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public class SubscreenRecyclerView extends RecyclerView {
    public float mItemTouchDownX;
    public float mItemTouchDownY;
    public final float mThresHold;

    public SubscreenRecyclerView(Context context) {
        super(context);
        this.mThresHold = 50.0f;
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean zDispatchKeyEvent = ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel.dispatchKeyEvent(keyEvent);
        return !zDispatchKeyEvent ? super.dispatchKeyEvent(keyEvent) : zDispatchKeyEvent;
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean zDispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mItemTouchDownX = motionEvent.getX();
            this.mItemTouchDownY = motionEvent.getY();
            return zDispatchTouchEvent;
        }
        if (actionMasked == 2) {
            if (Math.abs(this.mItemTouchDownY - motionEvent.getY()) + this.mThresHold > Math.abs(this.mItemTouchDownX - motionEvent.getX())) {
                getParent().requestDisallowInterceptTouchEvent(true);
                return true;
            }
        }
        return zDispatchTouchEvent;
    }

    public SubscreenRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.recyclerViewStyle);
        this.mThresHold = 50.0f;
    }

    public SubscreenRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mThresHold = 50.0f;
    }
}
