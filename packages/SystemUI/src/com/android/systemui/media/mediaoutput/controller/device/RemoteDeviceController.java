package com.android.systemui.media.mediaoutput.controller.device;

import android.content.Context;
import android.media.MediaRouter2Manager;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.util.Log;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class RemoteDeviceController extends DeviceController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public MediaController currentMediaController;
    public final MediaSessionManager mediaSessionManager;
    public final Lazy router2Manager$delegate;
    public String packageName = "";
    public final RemoteDeviceController$callback$1 callback = new MediaController.Callback() { // from class: com.android.systemui.media.mediaoutput.controller.device.RemoteDeviceController$callback$1
        @Override // android.media.session.MediaController.Callback
        public final void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            Log.d("RemoteDeviceController", "onAudioInfoChanged() - " + playbackInfo);
            CoroutineScope controllerScope = RemoteDeviceController.this.getControllerScope();
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            BuildersKt.launch$default(controllerScope, MainDispatcherLoader.dispatcher, null, new RemoteDeviceController$callback$1$onAudioInfoChanged$1(RemoteDeviceController.this, null), 2);
        }

        @Override // android.media.session.MediaController.Callback
        public final void onSessionDestroyed() {
            Log.d("RemoteDeviceController", "onSessionDestroyed()");
            CoroutineScope controllerScope = RemoteDeviceController.this.getControllerScope();
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            BuildersKt.launch$default(controllerScope, MainDispatcherLoader.dispatcher, null, new RemoteDeviceController$callback$1$onSessionDestroyed$1(RemoteDeviceController.this, null), 2);
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.media.mediaoutput.controller.device.RemoteDeviceController$callback$1] */
    public RemoteDeviceController(final Context context, MediaSessionManager mediaSessionManager) {
        this.mediaSessionManager = mediaSessionManager;
        this.router2Manager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.RemoteDeviceController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Context context2 = context;
                int i = RemoteDeviceController.$r8$clinit;
                return MediaRouter2Manager.getInstance(context2);
            }
        });
        Log.d("RemoteDeviceController", "init()");
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x0165, code lost:
    
        if (r14.emit(r13, r0) == r1) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x017c, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x017a, code lost:
    
        if (r14.emit(r13, r0) == r1) goto L71;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$updateMediaController(com.android.systemui.media.mediaoutput.controller.device.RemoteDeviceController r13, kotlin.coroutines.jvm.internal.ContinuationImpl r14) {
        /*
            Method dump skipped, instructions count: 384
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.RemoteDeviceController.access$updateMediaController(com.android.systemui.media.mediaoutput.controller.device.RemoteDeviceController, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit adjustVolume(AudioDevice audioDevice, int i) {
        Log.d("RemoteDeviceController", "adjustVolume() - " + audioDevice + " - " + i);
        MediaController mediaController = this.currentMediaController;
        if (mediaController != null) {
            mediaController.setVolumeTo(i, 0);
        }
        return Unit.INSTANCE;
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
        MediaSessions$H$$ExternalSyntheticOutline0.m("packageName changed : ", this.packageName, " -> ", str, "RemoteDeviceController");
        this.packageName = str;
        CoroutineScope controllerScope = getControllerScope();
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        BuildersKt.launch$default(controllerScope, MainDispatcherLoader.dispatcher, null, new RemoteDeviceController$packageName$1(this, null), 2);
    }
}
