package com.android.systemui.plank.monitor;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import com.android.systemui.plank.monitor.EventFactory;
import com.android.systemui.plank.monitor.TestInputMonitor;
import com.samsung.android.view.SemWindowManager;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class TestInputHandler implements TestInputMonitor.EventHandler {
    public final Point displaySize;
    public final Context mContext;
    public final List mEventHistory;
    public long mLastEventTime;
    public long mStartEventTime;
    public int mStartX;
    public int mStartY;

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

    /* JADX WARN: Removed duplicated region for block: B:19:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onEventHandler(MotionEvent motionEvent) {
        int eventTime;
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.mStartX = (int) motionEvent.getRawX();
            this.mStartY = (int) motionEvent.getRawY();
            if (this.mLastEventTime > 0 && (eventTime = (int) (motionEvent.getEventTime() - this.mLastEventTime)) >= 10) {
                EventFactory.Companion.getClass();
                addEvent(new EventData(EventType.SLEEP, 0, 0, 0, 0, 0, eventTime));
            }
            this.mStartEventTime = motionEvent.getEventTime();
            return;
        }
        if (action != 1) {
            return;
        }
        int rawX = (int) motionEvent.getRawX();
        int rawY = (int) motionEvent.getRawY();
        int iAbs = Math.abs(this.mStartX - rawX);
        int iAbs2 = Math.abs(rawY - this.mStartY);
        int eventTime2 = (int) ((motionEvent.getEventTime() - this.mStartEventTime) / 25);
        if (iAbs > 10 || iAbs2 > 10) {
            EventFactory.Companion companion = EventFactory.Companion;
            int i = this.mStartX;
            int i2 = this.mStartY;
            companion.getClass();
            addEvent(new EventData(EventType.SWIPE, i, i2, rawX, rawY, eventTime2, 0));
        } else {
            boolean z = this.mContext.getResources().getConfiguration().orientation == 2;
            if (!z) {
                int i3 = this.mStartX;
                Point point = this.displaySize;
                if (i3 > point.x || this.mStartY > point.y) {
                    EventFactory.Companion companion2 = EventFactory.Companion;
                    int i4 = this.mStartX;
                    int i5 = this.mStartY;
                    companion2.getClass();
                    addEvent(new EventData(EventType.SWIPE, i4, i5, i4, i5, 1, 0));
                } else {
                    if (z) {
                        int i6 = this.mStartX;
                        Point point2 = this.displaySize;
                        if (i6 > point2.y || this.mStartY > point2.x) {
                        }
                    }
                    if (eventTime2 >= 5) {
                        EventFactory.Companion companion3 = EventFactory.Companion;
                        int i7 = this.mStartX;
                        int i8 = this.mStartY;
                        companion3.getClass();
                        addEvent(new EventData(EventType.LONGCLICK, i7, i8, i7, i8, eventTime2, 0));
                    } else {
                        EventFactory.Companion companion4 = EventFactory.Companion;
                        int i9 = this.mStartX;
                        int i10 = this.mStartY;
                        companion4.getClass();
                        addEvent(new EventData(EventType.CLICK, i9, i10, 0, 0, 0, 0));
                    }
                }
            }
        }
        this.mLastEventTime = motionEvent.getEventTime();
    }
}
