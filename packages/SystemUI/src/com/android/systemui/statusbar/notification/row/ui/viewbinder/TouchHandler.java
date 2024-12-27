package com.android.systemui.statusbar.notification.row.ui.viewbinder;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TouchHandler implements Gefingerpoken, View.OnTouchListener {
    public final FalsingManager falsingManager;
    public boolean isTouchEnabled;
    public final ActivatableNotificationView view;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public TouchHandler(ActivatableNotificationView activatableNotificationView, FalsingManager falsingManager) {
        this.view = activatableNotificationView;
        this.falsingManager = falsingManager;
    }

    @Override // com.android.systemui.Gefingerpoken
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            this.view.mLastActionUpTime = motionEvent.getEventTime();
        }
        if (!this.isTouchEnabled || motionEvent.getAction() != 1) {
            return false;
        }
        boolean isFalseTap = this.falsingManager.isFalseTap(1);
        if (isFalseTap) {
            String simpleName = Reflection.getOrCreateKotlinClass(view.getClass()).getSimpleName();
            if (simpleName == null) {
                simpleName = "ActivatableNotificationViewBinder";
            }
            Log.d(simpleName, "capturing false tap");
        }
        return isFalseTap;
    }

    @Override // com.android.systemui.Gefingerpoken
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }
}
