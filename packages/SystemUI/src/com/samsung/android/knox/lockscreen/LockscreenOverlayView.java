package com.samsung.android.knox.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LockscreenOverlayView extends FrameLayout {
    public static final String TAG = "LSO_LockscreenOverlayView";
    public Handler handler;
    public final LSOInterface lso;
    public final Context mContext;
    public final BroadcastReceiver mNotifier;
    public final Point mParentDimension;
    public final Point mViewDimension;
    public boolean registered;

    public LockscreenOverlayView(Context context) {
        super(context);
        this.handler = new Handler() { // from class: com.samsung.android.knox.lockscreen.LockscreenOverlayView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                LockscreenOverlayView lockscreenOverlayView = LockscreenOverlayView.this;
                String str = LockscreenOverlayView.TAG;
                lockscreenOverlayView.setLayout();
            }
        };
        this.mNotifier = new BroadcastReceiver() { // from class: com.samsung.android.knox.lockscreen.LockscreenOverlayView.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                MotionLayout$$ExternalSyntheticOutline0.m("Received intent: ", action, LockscreenOverlayView.TAG);
                if (action == null) {
                    return;
                }
                if (action.equals("android.intent.action.MEDIA_MOUNTED") || action.equals(LSOConstants.ACTION_LSO_CONFIG_CHANGED_INTERNAL)) {
                    LockscreenOverlayView.this.handler.sendEmptyMessage(0);
                }
            }
        };
        this.mContext = context;
        Point point = new Point();
        this.mParentDimension = point;
        calculateDeviceDimension();
        this.mViewDimension = new Point(point);
        LSOInterface lSOInterface = LSOInterface.getInstance(new ContextInfo(Process.myUid()), context);
        this.lso = lSOInterface;
        if (lSOInterface == null) {
            setVisibility(8);
            return;
        }
        setVisibility(4);
        setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.registered = false;
        setLayout();
    }

    public final boolean allowToBeVisible() {
        double d = this.mViewDimension.x;
        Point point = this.mParentDimension;
        if (d >= point.x * 0.8d && r0.y >= point.y * 0.8d) {
            return true;
        }
        StringBuilder sb = new StringBuilder("Screen Size(");
        sb.append(this.mParentDimension.x);
        sb.append(",");
        sb.append(this.mParentDimension.y);
        sb.append(")  : View Size(");
        sb.append(this.mViewDimension.x);
        sb.append(",");
        EmergencyButtonController$$ExternalSyntheticOutline0.m(sb, this.mViewDimension.y, ")", TAG);
        Log.d(TAG, "LSOInterface View cannot be displayed as view size is not enough.");
        return false;
    }

    public final void calculateDeviceDimension() {
        ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getSize(this.mParentDimension);
    }

    public final void finalize() throws Throwable {
        super.finalize();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        registerLSONotification();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        unregisterLSONotification();
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        calculateDeviceDimension();
        StringBuilder sb = new StringBuilder("Size Changed(");
        sb.append(this.mParentDimension.y);
        sb.append(",");
        ViewPager$$ExternalSyntheticOutline0.m(sb, this.mParentDimension.y, ")  : From(", i3, ",");
        ViewPager$$ExternalSyntheticOutline0.m(sb, i4, ")  To(", i, ",");
        EmergencyButtonController$$ExternalSyntheticOutline0.m(sb, i2, ")", TAG);
        Point point = this.mViewDimension;
        point.x = i;
        point.y = i2;
        if (getVisibility() == 4 && allowToBeVisible()) {
            setVisibility(0);
        } else {
            if (getVisibility() != 0 || allowToBeVisible()) {
                return;
            }
            setVisibility(4);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        setVisibility(8);
        return super.onTouchEvent(motionEvent);
    }

    public final synchronized void registerLSONotification() {
        if (this.registered) {
            return;
        }
        setLayout();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
        intentFilter.addAction(LSOConstants.ACTION_LSO_CONFIG_CHANGED_INTERNAL);
        this.mContext.registerReceiver(this.mNotifier, intentFilter);
        this.registered = true;
        Log.d(TAG, "Registered for Intent: android.intent.action.MEDIA_MOUNTED , com.samsung.android.knox.intent.action.LSO_CONFIG_CHANGED_INTERNAL");
    }

    public final boolean setLayout() {
        removeAllViews();
        LSOAttributeSet preferences = this.lso.getPreferences();
        float floatValue = (preferences == null || !preferences.containsKey(LSOAttrConst.ATTR_ALPHA)) ? 1.0f : preferences.getAsFloat(LSOAttrConst.ATTR_ALPHA) != null ? preferences.getAsFloat(LSOAttrConst.ATTR_ALPHA).floatValue() : 0.0f;
        boolean z = true;
        for (int i = 1; i <= 3; i++) {
            LSOItemData data = this.lso.getData(i);
            if (data != null) {
                View layout = setLayout(data);
                if (layout != null && i != 3) {
                    layout.setAlpha(floatValue);
                }
                z = false;
            }
        }
        if (z) {
            Log.d(TAG, "No Lockscreen Overlay data found.");
        }
        return false;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        int visibility = getVisibility();
        if (i == 0 && !allowToBeVisible()) {
            i = 4;
        } else if (i == 4 && allowToBeVisible()) {
            i = 0;
        }
        if (visibility == i) {
            return;
        }
        super.setVisibility(i);
    }

    public final synchronized void unregisterLSONotification() {
        if (this.registered) {
            this.mContext.unregisterReceiver(this.mNotifier);
            this.registered = false;
            Log.d(TAG, "Unregistered for Intent: android.intent.action.MEDIA_MOUNTED , com.samsung.android.knox.intent.action.LSO_CONFIG_CHANGED_INTERNAL");
        }
    }

    public final View setLayout(LSOItemData lSOItemData) {
        try {
            LSOContainerView lSOContainerView = new LSOContainerView(this.mContext, (LSOItemContainer) lSOItemData);
            addView(lSOContainerView, new FrameLayout.LayoutParams(-1, -1));
            return lSOContainerView;
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("setLayout() Error while creating views: ", e, TAG);
            return null;
        }
    }
}
