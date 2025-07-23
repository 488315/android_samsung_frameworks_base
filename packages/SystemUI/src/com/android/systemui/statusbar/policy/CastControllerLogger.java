package com.android.systemui.statusbar.policy;

import android.media.MediaRouter;
import com.android.systemui.log.LogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CastControllerLogger {
    public static final Companion Companion = new Companion(null);
    public final LogBuffer logger;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static String toLogString(MediaRouter.RouteInfo routeInfo) {
            if (routeInfo == null) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(routeInfo.getName());
            sb.append('/');
            sb.append(routeInfo.getDescription());
            sb.append('@');
            sb.append(routeInfo.getDeviceAddress());
            sb.append(",status=");
            sb.append(routeInfo.getStatus());
            if (routeInfo.isDefault()) {
                sb.append(",default");
            }
            if (routeInfo.isEnabled()) {
                sb.append(",enabled");
            }
            if (routeInfo.isConnecting()) {
                sb.append(",connecting");
            }
            if (routeInfo.isSelected()) {
                sb.append(",selected");
            }
            sb.append(",id=");
            sb.append(routeInfo.getTag());
            return sb.toString();
        }

        private Companion() {
        }
    }

    public CastControllerLogger(LogBuffer logBuffer) {
        this.logger = logBuffer;
    }
}
