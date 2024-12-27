package com.android.systemui.media.mediaoutput.controller.device;

import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.util.Log;
import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RemoteDeviceController extends DeviceController {
    public MediaController currentMediaController;
    public final MediaSessionManager mediaSessionManager;
    public String packageName = "";
    public final RemoteDeviceController$callback$1 callback = new MediaController.Callback() { // from class: com.android.systemui.media.mediaoutput.controller.device.RemoteDeviceController$callback$1
        @Override // android.media.session.MediaController.Callback
        public final void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            Log.d("RemoteDeviceController", "onAudioInfoChanged() - " + playbackInfo);
            BuildersKt.launch$default(RemoteDeviceController.this.getControllerScope(), null, null, new RemoteDeviceController$callback$1$onAudioInfoChanged$1(RemoteDeviceController.this, null), 3);
        }

        @Override // android.media.session.MediaController.Callback
        public final void onSessionDestroyed() {
            Log.d("RemoteDeviceController", "onSessionDestroyed()");
            BuildersKt.launch$default(RemoteDeviceController.this.getControllerScope(), null, null, new RemoteDeviceController$callback$1$onSessionDestroyed$1(RemoteDeviceController.this, null), 3);
        }
    };

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

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.media.mediaoutput.controller.device.RemoteDeviceController$callback$1] */
    public RemoteDeviceController(MediaSessionManager mediaSessionManager) {
        this.mediaSessionManager = mediaSessionManager;
        Log.d("RemoteDeviceController", "init()");
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$updateMediaController(com.android.systemui.media.mediaoutput.controller.device.RemoteDeviceController r11, kotlin.coroutines.Continuation r12) {
        /*
            Method dump skipped, instructions count: 329
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.RemoteDeviceController.access$updateMediaController(com.android.systemui.media.mediaoutput.controller.device.RemoteDeviceController, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void adjustVolume(AudioDevice audioDevice, int i) {
        Log.d("RemoteDeviceController", "adjustVolume() - " + audioDevice + " - " + i);
        MediaController mediaController = this.currentMediaController;
        if (mediaController != null) {
            mediaController.setVolumeTo(i, 0);
        }
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("RemoteDeviceController", "close()");
        MediaController mediaController = this.currentMediaController;
        if (mediaController != null) {
            mediaController.unregisterCallback(this.callback);
        }
    }

    public final void setPackageName(String str) {
        if (Intrinsics.areEqual(this.packageName, str)) {
            return;
        }
        MWBixbyController$$ExternalSyntheticOutline0.m("packageName changed : ", this.packageName, " -> ", str, "RemoteDeviceController");
        this.packageName = str;
        BuildersKt.launch$default(getControllerScope(), null, null, new RemoteDeviceController$packageName$1(this, null), 3);
    }
}
