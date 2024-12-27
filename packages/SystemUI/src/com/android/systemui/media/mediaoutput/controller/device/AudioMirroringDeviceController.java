package com.android.systemui.media.mediaoutput.controller.device;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
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
import com.android.systemui.media.mediaoutput.entity.GroupDevice;
import com.android.systemui.media.mediaoutput.entity.State;
import com.android.systemui.media.mediaoutput.ext.MediaRoute2InfoExtKt;
import com.android.systemui.media.mediaoutput.icons.Icons$Device;
import com.android.systemui.media.mediaoutput.icons.device.GroupSpeakerKt;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AudioMirroringDeviceController extends RouteDeviceController {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public String mediaPackageName;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static final void access$setAudioMirroringSpeakerMode(Companion companion, Context context, String str) {
            Object failure;
            companion.getClass();
            try {
                int i = Result.$r8$clinit;
                Log.i("AudioMirroringDeviceController", "setAudioMirroringSpeakerMode() : ".concat(str));
                ContentResolver contentResolver = context.getContentResolver();
                Uri parse = Uri.parse("content://com.samsung.android.audiomirroring");
                Bundle bundle = new Bundle();
                bundle.putString("method", str);
                Unit unit = Unit.INSTANCE;
                failure = contentResolver.call(parse, "set_speaker_mode", (String) null, bundle);
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
            if (m2527exceptionOrNullimpl != null) {
                m2527exceptionOrNullimpl.printStackTrace();
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public AudioMirroringDeviceController(Context context, AudioManager audioManager, DataStore dataStore) {
        super(context, audioManager, dataStore);
        this.context = context;
        this.mediaPackageName = "";
        Log.d("AudioMirroringDeviceController", "init()");
        Intent intent = new Intent("com.samsung.android.audiomirroring.service.START_AUDIO_CAST");
        intent.setPackage("com.samsung.android.audiomirroring");
        Log.i("AudioMirroringDeviceController", "startAudioMirroringService() - intent: " + intent + " / extra: " + intent.getExtras());
        context.startServiceAsUser(intent, UserHandle.CURRENT);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController
    public final GroupDevice createGroupDevice(RoutingSessionInfo routingSessionInfo, List list) {
        String id = routingSessionInfo.getId();
        String valueOf = String.valueOf(routingSessionInfo.getName());
        ImageVectorConverterPainter.Companion companion = ImageVectorConverterPainter.Companion;
        Icons$Device icons$Device = Icons$Device.INSTANCE;
        ImageVector imageVector = (ImageVector) GroupSpeakerKt.GroupSpeaker$delegate.getValue();
        companion.getClass();
        GroupDevice groupDevice = new GroupDevice(id, valueOf, null, ImageVectorConverterPainter.Companion.toConverter(imageVector), null, routingSessionInfo.getVolume(), routingSessionInfo.getVolumeMax(), null, list, 148, null);
        groupDevice.controllerType = ControllerType.AudioMirroringGroup;
        groupDevice.routingSessionInfo = routingSessionInfo;
        return groupDevice;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController
    public final AudioDevice createRouteDevice(MediaRoute2Info mediaRoute2Info, State state, boolean z, boolean z2, boolean z3, boolean z4) {
        String id = mediaRoute2Info.getId();
        CharSequence displayName = MediaRoute2InfoExtKt.getDisplayName(mediaRoute2Info);
        ImageVectorConverterPainter.Companion companion = ImageVectorConverterPainter.Companion;
        ImageVector simpleIcon = MediaRoute2InfoExtKt.getSimpleIcon(mediaRoute2Info);
        companion.getClass();
        ImageVectorConverterPainter converter = ImageVectorConverterPainter.Companion.toConverter(simpleIcon);
        int volume = mediaRoute2Info.getVolume();
        int volumeMax = mediaRoute2Info.getVolumeMax();
        Intrinsics.checkNotNull(id);
        AudioMirroringDevice audioMirroringDevice = new AudioMirroringDevice(id, displayName, null, converter, null, volume, volumeMax, state, z, z2, z3, false, 2068, null);
        audioMirroringDevice.mediaRoute2Info = mediaRoute2Info;
        return audioMirroringDevice;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController
    public final RoutingSessionInfo getAvailableSession(List list) {
        Object failure;
        String string;
        Context context = this.context;
        Companion.getClass();
        try {
            int i = Result.$r8$clinit;
            failure = context.getContentResolver().call(Uri.parse("content://com.samsung.android.audiomirroring"), "request_package_name", (String) null, (Bundle) null);
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
        if (m2527exceptionOrNullimpl != null) {
            m2527exceptionOrNullimpl.printStackTrace();
        }
        if (failure instanceof Result.Failure) {
            failure = null;
        }
        Bundle bundle = (Bundle) failure;
        String str = "";
        if (bundle != null && (string = bundle.getString("mediaPackageName", "")) != null) {
            Log.i("AudioMirroringDeviceController", "getAudioMirroringPackageName() - ".concat(string));
            str = string;
        }
        if (!(!StringsKt__StringsJVMKt.isBlank(str))) {
            str = null;
        }
        if (str == null) {
            return null;
        }
        if (!str.equals(this.mediaPackageName)) {
            str = null;
        }
        if (str == null) {
            return null;
        }
        Iterator it = CollectionsKt___CollectionsKt.dropLast(list).iterator();
        while (it.hasNext()) {
            getRouter2Manager().releaseSession((RoutingSessionInfo) it.next());
        }
        RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) CollectionsKt___CollectionsKt.lastOrNull(list);
        if (routingSessionInfo != null) {
            return routingSessionInfo;
        }
        return null;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController
    public final String getPackageName() {
        String str = this.mediaPackageName;
        if (!StringsKt__StringsKt.contains(str, ".", false)) {
            str = null;
        }
        return str != null ? "com.samsung.android.audiomirroring" : "";
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController, com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void select(AudioDevice audioDevice) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("select() - ", audioDevice, "AudioMirroringDeviceController");
        if (audioDevice instanceof AudioMirroringDevice) {
            Companion.access$setAudioMirroringSpeakerMode(Companion, this.context, "selectRoute");
        }
        super.select(audioDevice);
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
    public final void transfer(AudioDevice audioDevice) {
        Object failure;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "AudioMirroringDeviceController");
        MediaRoute2Info mediaRoute2Info = null;
        if (audioDevice instanceof AudioMirroringDevice) {
            Context context = this.context;
            String str = this.mediaPackageName;
            Companion companion = Companion;
            companion.getClass();
            try {
                int i = Result.$r8$clinit;
                Log.i("AudioMirroringDeviceController", "setAudioMirroringPackageName() : " + str);
                ContentResolver contentResolver = context.getContentResolver();
                Uri parse = Uri.parse("content://com.samsung.android.audiomirroring");
                Bundle bundle = new Bundle();
                bundle.putString("mediaPackageName", str);
                Unit unit = Unit.INSTANCE;
                failure = contentResolver.call(parse, "set_package_name", (String) null, bundle);
            } catch (Throwable th) {
                int i2 = Result.$r8$clinit;
                failure = new Result.Failure(th);
            }
            Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
            if (m2527exceptionOrNullimpl != null) {
                m2527exceptionOrNullimpl.printStackTrace();
            }
            Companion.access$setAudioMirroringSpeakerMode(companion, this.context, "transfer");
            MediaRoute2Info mediaRoute2Info2 = ((AudioMirroringDevice) audioDevice).mediaRoute2Info;
            if (mediaRoute2Info2 != null) {
                mediaRoute2Info = mediaRoute2Info2;
            }
        }
        if (mediaRoute2Info != null) {
            getRouter2Manager().transfer(getPackageName(), mediaRoute2Info, Process.myUserHandle());
        } else {
            super.transfer(audioDevice);
        }
    }
}
