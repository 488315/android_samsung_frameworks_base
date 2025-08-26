package com.android.systemui.media.mediaoutput.controller.device;

import android.content.Context;
import android.media.MediaRouter2Manager;
import android.media.RoutingSessionInfo;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.util.Log;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.RemoteDevice;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes2.dex */
public final class RemoteDeviceController extends DeviceController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public MediaController currentMediaController;
    public final MediaSessionManager mediaSessionManager;
    public final Lazy router2Manager$delegate;
    public final UserTracker userTracker;
    public String packageName = "";
    public final RemoteDeviceController$callback$1 callback = new MediaController.Callback() { // from class: com.android.systemui.media.mediaoutput.controller.device.RemoteDeviceController$callback$1
        @Override // android.media.session.MediaController.Callback
        public final void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
            Log.d("RemoteDeviceController", "onAudioInfoChanged() - " + playbackInfo);
            CoroutineScope controllerScope = this.this$0.getControllerScope();
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            BuildersKt.launch$default(controllerScope, MainDispatcherLoader.dispatcher, null, new RemoteDeviceController$callback$1$onAudioInfoChanged$1(this.this$0, null), 2);
        }

        @Override // android.media.session.MediaController.Callback
        public final void onSessionDestroyed() {
            Log.d("RemoteDeviceController", "onSessionDestroyed()");
            CoroutineScope controllerScope = this.this$0.getControllerScope();
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            BuildersKt.launch$default(controllerScope, MainDispatcherLoader.dispatcher, null, new RemoteDeviceController$callback$1$onSessionDestroyed$1(this.this$0, null), 2);
        }
    };

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
    public RemoteDeviceController(final Context context, UserTracker userTracker, MediaSessionManager mediaSessionManager) {
        this.userTracker = userTracker;
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

    /* JADX WARN: Code restructure failed: missing block: B:62:0x016d, code lost:
    
        if (r14.emit(r13, r0) == r1) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0182, code lost:
    
        if (r14.emit(r13, r0) == r1) goto L71;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0184, code lost:
    
        return r1;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$updateMediaController(RemoteDeviceController remoteDeviceController, ContinuationImpl continuationImpl) {
        RemoteDeviceController$updateMediaController$1 remoteDeviceController$updateMediaController$1;
        Object obj;
        MediaController.PlaybackInfo playbackInfo;
        remoteDeviceController.getClass();
        if (continuationImpl instanceof RemoteDeviceController$updateMediaController$1) {
            remoteDeviceController$updateMediaController$1 = (RemoteDeviceController$updateMediaController$1) continuationImpl;
            int i = remoteDeviceController$updateMediaController$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                remoteDeviceController$updateMediaController$1.label = i - Integer.MIN_VALUE;
            } else {
                remoteDeviceController$updateMediaController$1 = new RemoteDeviceController$updateMediaController$1(remoteDeviceController, continuationImpl);
            }
        }
        Object obj2 = remoteDeviceController$updateMediaController$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = remoteDeviceController$updateMediaController$1.label;
        if (i2 != 0) {
            if (i2 == 1) {
                ResultKt.throwOnFailure(obj2);
                return Unit.INSTANCE;
            }
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj2);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj2);
        List listSingletonList = null;
        List activeSessionsForUser = remoteDeviceController.mediaSessionManager.getActiveSessionsForUser(null, ((UserTrackerImpl) remoteDeviceController.userTracker).getUserHandle());
        ArrayList arrayList = new ArrayList();
        for (Object obj3 : activeSessionsForUser) {
            if (Intrinsics.areEqual(((MediaController) obj3).getPackageName(), remoteDeviceController.packageName)) {
                arrayList.add(obj3);
            }
        }
        int i3 = 0;
        if (arrayList.size() > 1) {
            SecNotificationBlockManager$$ExternalSyntheticOutline0.m(arrayList.size(), "MediaController(", remoteDeviceController.packageName, ") size = ", "RemoteDeviceController");
            int size = arrayList.size();
            int i4 = 0;
            while (i4 < size) {
                Object obj4 = arrayList.get(i4);
                i4++;
                MediaController mediaController = (MediaController) obj4;
                Log.d("RemoteDeviceController", "\t" + mediaController.getPlaybackInfo() + ", " + mediaController.getPlaybackState());
            }
        }
        int size2 = arrayList.size();
        while (true) {
            if (i3 >= size2) {
                obj = null;
                break;
            }
            obj = arrayList.get(i3);
            i3++;
            if (((MediaController) obj).getPlaybackInfo().getPlaybackType() == 2) {
                break;
            }
        }
        MediaController mediaController2 = (MediaController) obj;
        SharedFlowImpl sharedFlowImpl = remoteDeviceController.devicesFlow;
        RemoteDeviceController$callback$1 remoteDeviceController$callback$1 = remoteDeviceController.callback;
        if (mediaController2 != null) {
            List remoteSessions = ((MediaRouter2Manager) remoteDeviceController.router2Manager$delegate.getValue()).getRemoteSessions();
            if (!(remoteSessions instanceof Collection) || !remoteSessions.isEmpty()) {
                Iterator it = remoteSessions.iterator();
                while (it.hasNext()) {
                    if (Intrinsics.areEqual(((RoutingSessionInfo) it.next()).getClientPackageName(), remoteDeviceController.packageName)) {
                    }
                }
            }
            MediaSession.Token sessionToken = mediaController2.getSessionToken();
            MediaController mediaController3 = remoteDeviceController.currentMediaController;
            if (!Intrinsics.areEqual(sessionToken, mediaController3 != null ? mediaController3.getSessionToken() : null)) {
                remoteDeviceController.currentMediaController = mediaController2;
                mediaController2.registerCallback(remoteDeviceController$callback$1);
            }
            MediaController mediaController4 = remoteDeviceController.currentMediaController;
            if (mediaController4 != null && (playbackInfo = mediaController4.getPlaybackInfo()) != null) {
                RemoteDevice.Companion companion = RemoteDevice.Companion;
                String str = remoteDeviceController.packageName;
                companion.getClass();
                listSingletonList = Collections.singletonList(new RemoteDevice(str, new ResourceString(R.string.current_casting_device, null, 2, null), playbackInfo.getCurrentVolume(), playbackInfo.getMaxVolume()));
            }
            List list = listSingletonList == null ? EmptyList.INSTANCE : listSingletonList;
            remoteDeviceController$updateMediaController$1.L$0 = listSingletonList;
            remoteDeviceController$updateMediaController$1.label = 2;
        }
        MediaController mediaController5 = remoteDeviceController.currentMediaController;
        if (mediaController5 != null) {
            mediaController5.unregisterCallback(remoteDeviceController$callback$1);
        }
        EmptyList emptyList = EmptyList.INSTANCE;
        remoteDeviceController$updateMediaController$1.label = 1;
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
