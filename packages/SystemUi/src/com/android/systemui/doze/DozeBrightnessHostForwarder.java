package com.android.systemui.doze;

import com.android.systemui.doze.DozeMachine;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        dozeServiceHost.mDozeLog.traceDozeScreenBrightness(i);
        ((NotificationShadeWindowControllerImpl) dozeServiceHost.mNotificationShadeWindowController).mScreenBrightnessDoze = i / 255.0f;
    }
}
