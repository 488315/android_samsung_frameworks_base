package com.android.systemui.media.mediaoutput.controller.device;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.os.UserHandle;
import android.util.Log;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.datastore.core.DataStore;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.AudioMirroringDevice;
import com.android.systemui.media.mediaoutput.entity.RouteDevice;
import com.android.systemui.media.mediaoutput.entity.State;
import com.android.systemui.media.mediaoutput.ext.AudioMirroringExtKt;
import com.android.systemui.media.mediaoutput.ext.MediaRoute2InfoExtKt;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes2.dex */
public final class AudioMirroringDeviceController extends RouteDeviceController {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final ControllerType controllerType;
    public volatile String mediaPackageName;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final void access$setAudioMirroringSpeakerMode(Companion companion, Context context, String str) {
            Object failure;
            companion.getClass();
            try {
                int i = Result.$r8$clinit;
                Log.i("AudioMirroringDeviceController", "setAudioMirroringSpeakerMode() : ".concat(str));
                ContentResolver contentResolver = context.getContentResolver();
                Uri uri = Uri.parse("content://com.samsung.android.audiomirroring");
                Bundle bundle = new Bundle();
                bundle.putString("method", str);
                Unit unit = Unit.INSTANCE;
                failure = contentResolver.call(uri, "set_speaker_mode", (String) null, bundle);
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
            if (thM3441exceptionOrNullimpl != null) {
                thM3441exceptionOrNullimpl.printStackTrace();
            }
        }

        private Companion() {
        }
    }

    public AudioMirroringDeviceController(Context context, AudioManager audioManager, DataStore dataStore) {
        super(context, audioManager, dataStore);
        this.context = context;
        this.mediaPackageName = "";
        this.controllerType = ControllerType.AudioMirroring;
        Log.d("AudioMirroringDeviceController", "init()");
        Intent intent = new Intent("com.samsung.android.audiomirroring.service.START_AUDIO_CAST");
        intent.setPackage("com.samsung.android.audiomirroring");
        Log.i("AudioMirroringDeviceController", "startAudioMirroringService() - intent: " + intent + " / extra: " + intent.getExtras());
        context.startServiceAsUser(intent, UserHandle.CURRENT);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController
    public final RouteDevice createRouteDevice(MediaRoute2Info mediaRoute2Info, State state, boolean z, boolean z2, boolean z3, boolean z4, RouteListingPreference.Item item) {
        String id = mediaRoute2Info.getId();
        CharSequence displayName = MediaRoute2InfoExtKt.getDisplayName(mediaRoute2Info);
        ImageVectorConverterPainter.Companion companion = ImageVectorConverterPainter.Companion;
        ImageVector simpleIcon = MediaRoute2InfoExtKt.getSimpleIcon(mediaRoute2Info);
        companion.getClass();
        ImageVectorConverterPainter converter = ImageVectorConverterPainter.Companion.toConverter(simpleIcon);
        int volume = mediaRoute2Info.getVolume();
        int volumeMax = mediaRoute2Info.getVolumeHandling() == 1 ? mediaRoute2Info.getVolumeMax() : 0;
        RouteListingPreference routeListingPreference = getRouter2Manager$1().getRouteListingPreference(getPackageName());
        return new AudioMirroringDevice(id, displayName, converter, state, volume, volumeMax, z, z2, z3, false, mediaRoute2Info, item, routeListingPreference != null ? routeListingPreference.getLinkedItemComponentName() : null, 512, null);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController
    public final RoutingSessionInfo getAvailableSession(List list) {
        String audioMirroringPackageName = AudioMirroringExtKt.getAudioMirroringPackageName(this.context);
        if (StringsKt__StringsKt.isBlank(audioMirroringPackageName)) {
            audioMirroringPackageName = null;
        }
        if (audioMirroringPackageName != null) {
            if (!audioMirroringPackageName.equals(this.mediaPackageName)) {
                audioMirroringPackageName = null;
            }
            if (audioMirroringPackageName != null) {
                int size = list.size() - 1;
                if (size < 0) {
                    size = 0;
                }
                Iterator it = CollectionsKt___CollectionsKt.take(list, size).iterator();
                while (it.hasNext()) {
                    getRouter2Manager$1().releaseSession((RoutingSessionInfo) it.next());
                }
                RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) CollectionsKt___CollectionsKt.lastOrNull(list);
                if (routingSessionInfo != null) {
                    return routingSessionInfo;
                }
            }
        }
        return null;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController
    public final ControllerType getControllerType() {
        return this.controllerType;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController
    public final String getPackageName() {
        String str = this.mediaPackageName;
        if (str == null) {
            return "";
        }
        if (!StringsKt__StringsKt.contains(str, ".", false)) {
            str = null;
        }
        return str != null ? "com.samsung.android.audiomirroring" : "";
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController
    public final List getTransferableRoutes() {
        EmptyList emptyList;
        String str = this.mediaPackageName;
        if (str != null) {
            if (!str.startsWith("com.spotify.music")) {
                str = null;
            }
            if (str != null) {
                if ((this.isSpotifyCastingPriority ? str : null) != null && (emptyList = EmptyList.INSTANCE) != null) {
                    return emptyList;
                }
            }
        }
        return getRouter2Manager$1().getTransferableRoutes(getPackageName());
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController, com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit select(AudioDevice audioDevice, ContinuationImpl continuationImpl) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("select() - ", audioDevice, "AudioMirroringDeviceController");
        if (audioDevice instanceof AudioMirroringDevice) {
            Companion.access$setAudioMirroringSpeakerMode(Companion, this.context, "selectRoute");
        }
        Unit unitSelect = super.select(audioDevice, continuationImpl);
        return unitSelect == CoroutineSingletons.COROUTINE_SUSPENDED ? unitSelect : Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController
    public final void setPackageName(String str) {
        if (!StringsKt__StringsKt.contains(str, ".", false)) {
            str = null;
        }
        if (str == null) {
            str = "";
        }
        this.mediaPackageName = str;
        BuildersKt.launch$default(getControllerScope(), null, null, new AudioMirroringDeviceController$packageName$4(this, null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController, com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Object transfer(AudioDevice audioDevice, Continuation continuation) {
        Object failure;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "AudioMirroringDeviceController");
        MediaRoute2Info mediaRoute2Info = null;
        if (audioDevice instanceof AudioMirroringDevice) {
            Companion companion = Companion;
            Context context = this.context;
            String str = this.mediaPackageName;
            if (str == null) {
                str = "";
            }
            companion.getClass();
            try {
                int i = Result.$r8$clinit;
                Log.i("AudioMirroringDeviceController", "setAudioMirroringPackageName() : ".concat(str));
                ContentResolver contentResolver = context.getContentResolver();
                Uri uri = Uri.parse("content://com.samsung.android.audiomirroring");
                Bundle bundle = new Bundle();
                bundle.putString("mediaPackageName", str);
                Unit unit = Unit.INSTANCE;
                failure = contentResolver.call(uri, "set_package_name", (String) null, bundle);
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
            if (thM3441exceptionOrNullimpl != null) {
                thM3441exceptionOrNullimpl.printStackTrace();
            }
            Companion.access$setAudioMirroringSpeakerMode(Companion, this.context, "transfer");
            mediaRoute2Info = ((AudioMirroringDevice) audioDevice).mediaRoute2Info;
        }
        if (mediaRoute2Info != null) {
            getRouter2Manager$1().transfer(getPackageName(), mediaRoute2Info, Process.myUserHandle());
            return Unit.INSTANCE;
        }
        Object objTransfer$suspendImpl = RouteDeviceController.transfer$suspendImpl(this, audioDevice, (ContinuationImpl) continuation);
        return objTransfer$suspendImpl == CoroutineSingletons.COROUTINE_SUSPENDED ? objTransfer$suspendImpl : Unit.INSTANCE;
    }
}
