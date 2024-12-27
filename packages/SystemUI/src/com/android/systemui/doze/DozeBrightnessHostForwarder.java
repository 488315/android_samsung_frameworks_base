package com.android.systemui.doze;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DozeBrightnessHostForwarder extends DozeMachine.Service.Delegate {
    public final DozeHost mHost;

    public DozeBrightnessHostForwarder(DozeMachine.Service service, DozeHost dozeHost, Executor executor) {
        super(service, executor);
        this.mHost = dozeHost;
    }

    @Override // com.android.systemui.doze.DozeMachine.Service.Delegate, com.android.systemui.doze.DozeMachine.Service
    public final void setDozeScreenBrightness(int i) {
        super.setDozeScreenBrightness(i);
        DozeServiceHost dozeServiceHost = (DozeServiceHost) this.mHost;
        DozeLogger dozeLogger = dozeServiceHost.mDozeLog.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$logDozeScreenBrightness$2 dozeLogger$logDozeScreenBrightness$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logDozeScreenBrightness$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "Doze screen brightness set, brightness=");
            }
        };
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logDozeScreenBrightness$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
        ((NotificationShadeWindowControllerImpl) dozeServiceHost.mNotificationShadeWindowController).mScreenBrightnessDoze = i / 255.0f;
    }
}
