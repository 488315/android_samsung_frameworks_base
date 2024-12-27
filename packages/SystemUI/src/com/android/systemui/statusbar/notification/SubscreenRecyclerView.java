package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Dependency;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
        boolean dispatchKeyEvent = ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel.dispatchKeyEvent(keyEvent);
        return !dispatchKeyEvent ? super.dispatchKeyEvent(keyEvent) : dispatchKeyEvent;
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mItemTouchDownX = motionEvent.getX();
            this.mItemTouchDownY = motionEvent.getY();
            return dispatchTouchEvent;
        }
        if (actionMasked != 2) {
            return dispatchTouchEvent;
        }
        if (Math.abs(this.mItemTouchDownY - motionEvent.getY()) + this.mThresHold <= Math.abs(this.mItemTouchDownX - motionEvent.getX())) {
            return dispatchTouchEvent;
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        return true;
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
