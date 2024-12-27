package com.android.systemui.plank.monitor;

import android.content.Context;
import android.graphics.Point;
import com.android.systemui.plank.monitor.TestInputMonitor;
import com.samsung.android.view.SemWindowManager;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TestInputHandler implements TestInputMonitor.EventHandler {
    public final Point displaySize;
    public final Context mContext;
    public final List mEventHistory;
    public long mLastEventTime;
    public long mStartEventTime;
    public int mStartX;
    public int mStartY;

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

    public TestInputHandler(Context context) {
        this.mContext = context;
        Point point = new Point();
        this.displaySize = point;
        this.mEventHistory = new ArrayList();
        SemWindowManager.getInstance().getInitialDisplaySize(point);
    }

    public final void addEvent(EventData eventData) {
        synchronized (this.mEventHistory) {
            ((ArrayList) this.mEventHistory).add(eventData);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0059, code lost:
    
        if (r18.mStartY <= r2.y) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0069, code lost:
    
        if (r18.mStartY <= r2.x) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onEventHandler(android.view.MotionEvent r19) {
        /*
            Method dump skipped, instructions count: 278
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.plank.monitor.TestInputHandler.onEventHandler(android.view.MotionEvent):void");
    }
}
