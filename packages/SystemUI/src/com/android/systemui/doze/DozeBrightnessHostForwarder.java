package com.android.systemui.doze;

import com.android.systemui.doze.DozeMachine;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.statusbar.phone.DozeServiceHost;

/* loaded from: classes2.dex */
public class DozeBrightnessHostForwarder extends DozeMachine.Service.Delegate {
    public final DozeHost mHost;

    public DozeBrightnessHostForwarder(DozeMachine.Service service, DozeHost dozeHost) {
        super(service);
        this.mHost = dozeHost;
    }

    @Override // com.android.systemui.doze.DozeMachine.Service.Delegate, com.android.systemui.doze.DozeMachine.Service
    public final void setDozeScreenBrightness(int i) {
        this.mDelegate.setDozeScreenBrightness(i);
        ((NotificationShadeWindowControllerImpl) ((DozeServiceHost) this.mHost).mNotificationShadeWindowController).mScreenBrightnessDoze = i / 255.0f;
    }

    @Override // com.android.systemui.doze.DozeMachine.Service.Delegate, com.android.systemui.doze.DozeMachine.Service
    public final void setDozeScreenBrightnessFloat(float f) {
        this.mDelegate.setDozeScreenBrightnessFloat(f);
        ((NotificationShadeWindowControllerImpl) ((DozeServiceHost) this.mHost).mNotificationShadeWindowController).mScreenBrightnessDoze = f;
    }
}
