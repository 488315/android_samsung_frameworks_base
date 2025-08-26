package com.android.systemui.media.mediaoutput.controller.device;

import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaRouter2Manager;
import android.media.RoutingSessionInfo;
import android.util.Log;
import com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.BuiltInDevice;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class RouteDeviceController$$ExternalSyntheticLambda3 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ RouteDeviceController$$ExternalSyntheticLambda3(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Object obj = this.f$1;
        Object obj2 = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                RouteDeviceController.Companion companion = RouteDeviceController.Companion;
                AudioDeviceInfo audioDeviceInfo = ((BuiltInDevice) ((AudioDevice) obj2)).audioDeviceInfo;
                if (audioDeviceInfo == null) {
                    audioDeviceInfo = null;
                }
                RouteDeviceController routeDeviceController = (RouteDeviceController) obj;
                AudioManagerExtKt.setDeviceForced(routeDeviceController.audioManager, audioDeviceInfo);
                Iterator it = routeDeviceController.getRouter2Manager$1().getRoutingSessions(routeDeviceController.getPackageName()).iterator();
                while (it.hasNext()) {
                    routeDeviceController.getRouter2Manager$1().releaseSession((RoutingSessionInfo) it.next());
                }
                break;
            case 1:
                AudioDeviceInfo audioDeviceInfo2 = (AudioDeviceInfo) obj2;
                RouteDeviceController routeDeviceController2 = (RouteDeviceController) obj;
                AudioManager audioManager = routeDeviceController2.audioManager;
                if (audioDeviceInfo2 != null) {
                    AudioManagerExtKt.setDeviceForced(audioManager, audioDeviceInfo2);
                } else {
                    AudioManagerExtKt.removePreferredDeviceForStrategy(audioManager);
                }
                Iterator it2 = routeDeviceController2.getRouter2Manager$1().getRoutingSessions(routeDeviceController2.getPackageName()).iterator();
                while (it2.hasNext()) {
                    routeDeviceController2.getRouter2Manager$1().releaseSession((RoutingSessionInfo) it2.next());
                }
                break;
            default:
                MediaRouter2Manager mediaRouter2Manager = (MediaRouter2Manager) obj2;
                Log.d("RouteDeviceController", "unregisterScanRequest()");
                mediaRouter2Manager.unregisterScanRequest();
                mediaRouter2Manager.unregisterCallback((RouteDeviceController$Companion$routeDeviceChanges$1$callback$1) obj);
                break;
        }
        return Unit.INSTANCE;
    }
}
