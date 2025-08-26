package com.android.systemui.dextouchpad.activity;

import android.graphics.Point;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.dextouchpad.util.Features;
import java.lang.ref.WeakReference;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class TouchpadViewMover {
    public static final long MOVE_POSITION_INTERVAL;
    public static final long MOVE_POSITION_TIMEOUT;
    public final Handler mHandler;
    public WeakReference mView;
    public boolean mIsMoveStarted = false;
    public boolean mIsSpenEnabled = false;
    public final TouchpadViewMover$$ExternalSyntheticLambda0 mUpdatePositionRunnable = new Runnable() { // from class: com.android.systemui.dextouchpad.activity.TouchpadViewMover$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            TouchpadViewMover touchpadViewMover = this.f$0;
            touchpadViewMover.mIsMoveStarted = true;
            touchpadViewMover.updatePosition(true);
        }
    };
    public final Point mPosition = new Point(0, 0);

    static {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        MOVE_POSITION_INTERVAL = timeUnit.convert(10L, TimeUnit.SECONDS);
        MOVE_POSITION_TIMEOUT = timeUnit.convert(1L, TimeUnit.MINUTES);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.dextouchpad.activity.TouchpadViewMover$$ExternalSyntheticLambda0] */
    public TouchpadViewMover(Handler handler) {
        this.mHandler = handler;
    }

    public final void executeAnimator() {
        WeakReference weakReference = this.mView;
        View view = weakReference != null ? (View) weakReference.get() : null;
        if (view != null) {
            view.animate().translationX(this.mPosition.x * 2).translationY(this.mPosition.y * 2).withLayer();
        }
    }

    public final void setSpenEnabled(boolean z) {
        if (Features.DEBUG) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("setSpenEnabled(), enabled=", "DexTouchpadViewMover", z);
        }
        this.mIsSpenEnabled = z;
        if (!z) {
            updatePosition(false);
        } else {
            this.mPosition.set(0, 0);
            executeAnimator();
        }
    }

    public final void updatePosition(boolean z) {
        int i;
        boolean z2 = Features.DEBUG;
        if (z2) {
            StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("updatePosition(), isUpdated=", ", mIsMoveStarted=", z);
            sbM.append(this.mIsMoveStarted);
            sbM.append(", mIsSpenEnabled=");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sbM, this.mIsSpenEnabled, "DexTouchpadViewMover");
        }
        if (!this.mIsMoveStarted || this.mIsSpenEnabled) {
            return;
        }
        TouchpadViewMover$$ExternalSyntheticLambda0 touchpadViewMover$$ExternalSyntheticLambda0 = this.mUpdatePositionRunnable;
        Handler handler = this.mHandler;
        handler.removeCallbacks(touchpadViewMover$$ExternalSyntheticLambda0);
        if (z) {
            if (z2) {
                Log.d("DexTouchpadViewMover", "updateNextMovePosition()");
            }
            Random random = new Random();
            Point point = new Point();
            while (true) {
                Point point2 = this.mPosition;
                point.set(point2.x, point2.y);
                int iNextInt = random.nextInt(4);
                if (iNextInt == 0) {
                    point.offset(1, 1);
                } else if (iNextInt == 1) {
                    point.offset(-1, 1);
                } else if (iNextInt == 2) {
                    point.offset(-1, -1);
                } else if (iNextInt == 3) {
                    point.offset(1, -1);
                }
                int i2 = point.x;
                if (i2 <= 8 && i2 >= -8 && (i = point.y) <= 8 && i >= -8) {
                    break;
                }
            }
            if (Features.DEBUG) {
                StringBuilder sb = new StringBuilder("nextPosition.x=");
                sb.append(point.x);
                sb.append(", nextPosition.y=");
                RecyclerView$$ExternalSyntheticOutline0.m(point.y, "DexTouchpadViewMover", sb);
            }
            this.mPosition.set(point.x, point.y);
            executeAnimator();
        }
        handler.postDelayed(touchpadViewMover$$ExternalSyntheticLambda0, MOVE_POSITION_INTERVAL);
    }
}
