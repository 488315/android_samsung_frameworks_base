package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class SubscreenNotificationTemplate {
    public final Context mContext;
    public final FrameLayout mLayout;
    public TextView mMarqueeText;
    public final int mTouchSlop;
    public final AnonymousClass1 mOnTouchListener = new View.OnTouchListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationTemplate.1
        public float mInitX;
        public float mInitY;
        public boolean mIsClicked;

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            if (view != SubscreenNotificationTemplate.this.mLayout) {
                return false;
            }
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                this.mInitX = motionEvent.getX();
                this.mInitY = motionEvent.getY();
                this.mIsClicked = true;
            } else if (actionMasked != 1) {
                if (actionMasked == 2 && ((this.mIsClicked && Math.abs(this.mInitX - motionEvent.getX()) > SubscreenNotificationTemplate.this.mTouchSlop) || Math.abs(this.mInitY - motionEvent.getY()) > SubscreenNotificationTemplate.this.mTouchSlop)) {
                    Log.d("S.S.N.", "OUT OF TOUCH SLOP ");
                    float abs = Math.abs(this.mInitY - motionEvent.getY());
                    SubscreenNotificationTemplate subscreenNotificationTemplate = SubscreenNotificationTemplate.this;
                    if (abs > subscreenNotificationTemplate.mTouchSlop) {
                        subscreenNotificationTemplate.mDeviceModel.dimissTopPopupNotification();
                    }
                    this.mIsClicked = false;
                }
            } else if (this.mIsClicked) {
                ((PowerManager) SubscreenNotificationTemplate.this.mContext.getSystemService("power")).userActivity(SystemClock.uptimeMillis(), true);
                SubscreenNotificationTemplate.this.performClick();
                this.mIsClicked = false;
                this.mInitY = 0.0f;
                this.mInitX = 0.0f;
            }
            return true;
        }
    };
    public final SubscreenDeviceModelParent mDeviceModel = ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.statusbar.notification.SubscreenNotificationTemplate$1] */
    public SubscreenNotificationTemplate(Context context) {
        this.mContext = context;
        FrameLayout frameLayout = new FrameLayout(context);
        this.mLayout = frameLayout;
        frameLayout.setClickable(true);
        this.mLayout.setFocusable(true);
        this.mLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationTemplate$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscreenNotificationTemplate subscreenNotificationTemplate = SubscreenNotificationTemplate.this;
                subscreenNotificationTemplate.getClass();
                if (view.isAccessibilityFocused()) {
                    subscreenNotificationTemplate.performClick();
                }
            }
        });
        SubscreenDeviceModelParent subscreenDeviceModelParent = this.mDeviceModel;
        if (subscreenDeviceModelParent != null) {
            subscreenDeviceModelParent.setFullPopupWindowKeyEventListener(this.mLayout);
        }
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        context.getColor(R.color.subscreen_notification_primary_default);
    }

    public abstract void makeView(NotificationEntry notificationEntry, boolean z);

    public abstract void performClick();
}
