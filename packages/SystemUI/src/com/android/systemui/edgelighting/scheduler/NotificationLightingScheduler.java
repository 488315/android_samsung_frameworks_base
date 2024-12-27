package com.android.systemui.edgelighting.scheduler;

import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.util.Slog;
import com.android.systemui.edgelighting.scheduler.EdgeLightingScheduler;
import com.samsung.android.edge.SemEdgeLightingInfo;
import java.util.HashMap;

public final class NotificationLightingScheduler {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public LightingScheduleInfo mCurrentLightingScheduleInfo;
    public EdgeLightingScheduler.AnonymousClass4 mListener;
    public final EdgeLightingDataKeeper mEdgeLightingDataKeeper = new EdgeLightingDataKeeper(0);
    public final AnonymousClass1 mNotificationScheduleHandler = new Handler() { // from class: com.android.systemui.edgelighting.scheduler.NotificationLightingScheduler.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 0) {
                return;
            }
            String str = (String) message.obj;
            boolean z = NotificationLightingScheduler.DEBUG;
            NotificationLightingScheduler.this.expireNotiLighting(str);
        }
    };

    public final class EdgeLightingDataKeeper {
        public final HashMap mNotificationMap;

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
                AnonymousClass1 anonymousClass1 = this.mNotificationScheduleHandler;
                anonymousClass1.removeMessages(0);
                anonymousClass1.sendMessageDelayed(anonymousClass1.obtainMessage(0, this.mCurrentLightingScheduleInfo.getNotificationKey()), i);
            }
        }
    }

    public final void flushNotiNow() {
        if (this.mCurrentLightingScheduleInfo != null) {
            AnonymousClass1 anonymousClass1 = this.mNotificationScheduleHandler;
            anonymousClass1.removeMessages(0);
            anonymousClass1.sendMessage(anonymousClass1.obtainMessage(0, this.mCurrentLightingScheduleInfo.getNotificationKey()));
        }
    }
}
