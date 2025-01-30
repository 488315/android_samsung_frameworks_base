package com.android.systemui.edgelighting.scheduler;

import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.util.Slog;
import com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler;
import com.samsung.android.edge.SemEdgeLightingInfo;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NotificationLightingScheduler {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public LightingScheduleInfo mCurrentLightingScheduleInfo;
    public EdgeLightingScheduler.C13564 mListener;
    public final EdgeLightingDataKeeper mEdgeLightingDataKeeper = new EdgeLightingDataKeeper(0);
    public final HandlerC13601 mNotificationScheduleHandler = new Handler() { // from class: com.android.systemui.edgelighting.scheduler.NotificationLightingScheduler.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 0) {
                return;
            }
            NotificationLightingScheduler notificationLightingScheduler = NotificationLightingScheduler.this;
            String str = (String) message.obj;
            boolean z = NotificationLightingScheduler.DEBUG;
            notificationLightingScheduler.expireNotiLighting(str);
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EdgeLightingDataKeeper {
        public final HashMap mNotificationMap;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class SemEdgeLightingInfoData {
            public final SemEdgeLightingInfo mEdgeLightingInfo;

            public SemEdgeLightingInfoData(SemEdgeLightingInfo semEdgeLightingInfo, long j) {
                this.mEdgeLightingInfo = semEdgeLightingInfo;
            }
        }

        public /* synthetic */ EdgeLightingDataKeeper(int i) {
            this();
        }

        private EdgeLightingDataKeeper() {
            this.mNotificationMap = new HashMap();
            new HashMap();
        }
    }

    public final void expireNotiLighting(String str) {
        LightingScheduleInfo lightingScheduleInfo = this.mCurrentLightingScheduleInfo;
        String notificationKey = lightingScheduleInfo != null ? lightingScheduleInfo.getNotificationKey() : null;
        if (str.equals("turnToHeadsUp")) {
            this.mCurrentLightingScheduleInfo = null;
            Slog.d("NotificationLightingScheduler", "expiredNotiLighting: remove=" + str + " turn to heads up");
            this.mListener.stopNotification(true);
            return;
        }
        if (str.equals(notificationKey)) {
            this.mCurrentLightingScheduleInfo = null;
            Slog.d("NotificationLightingScheduler", "expiredNotiLighting: remove=".concat(str));
            this.mListener.stopNotification(false);
        } else {
            Slog.d("NotificationLightingScheduler", "expiredNotiLighting: invalid expire=" + str + " cur=" + notificationKey);
            this.mListener.stopNotification(false);
        }
    }

    public final void extendLightingDuration(int i, boolean z) {
        LightingScheduleInfo lightingScheduleInfo = this.mCurrentLightingScheduleInfo;
        if (lightingScheduleInfo != null) {
            if (z || lightingScheduleInfo.getDuration() < i) {
                Slog.d("NotificationLightingScheduler", "extendLightingDuration for verification");
                this.mCurrentLightingScheduleInfo.setDuration(i);
                HandlerC13601 handlerC13601 = this.mNotificationScheduleHandler;
                handlerC13601.removeMessages(0);
                handlerC13601.sendMessageDelayed(handlerC13601.obtainMessage(0, this.mCurrentLightingScheduleInfo.getNotificationKey()), i);
            }
        }
    }

    public final void flushNotiNow() {
        if (this.mCurrentLightingScheduleInfo != null) {
            HandlerC13601 handlerC13601 = this.mNotificationScheduleHandler;
            handlerC13601.removeMessages(0);
            handlerC13601.sendMessage(handlerC13601.obtainMessage(0, this.mCurrentLightingScheduleInfo.getNotificationKey()));
        }
    }
}
