package com.android.systemui.statusbar.notification.row.ui.viewbinder;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class TouchHandler implements Gefingerpoken, View.OnTouchListener {
    public final FalsingManager falsingManager;
    public boolean isTouchEnabled;
    public final ActivatableNotificationView view;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
}
