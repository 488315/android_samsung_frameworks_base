package com.android.systemui.media.mediaoutput.controller.device;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.RoutingSessionInfo;
import android.os.Process;
import android.util.Log;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.datastore.core.DataStore;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.ChromeCastDevice;
import com.android.systemui.media.mediaoutput.entity.GroupDevice;
import com.android.systemui.media.mediaoutput.entity.State;
import com.android.systemui.media.mediaoutput.ext.MediaRoute2InfoExtKt;
import com.android.systemui.media.mediaoutput.icons.Icons$Badge;
import com.android.systemui.media.mediaoutput.icons.Icons$Device;
import com.android.systemui.media.mediaoutput.icons.badge.ChromecastKt;
import com.android.systemui.media.mediaoutput.icons.device.GroupSpeakerKt;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class ChromeCastDeviceController extends RouteDeviceController {

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

    public ChromeCastDeviceController(Context context, AudioManager audioManager, DataStore dataStore) {
        super(context, audioManager, dataStore);
        Log.d("ChromeCastDeviceController", "init()");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController
    public final GroupDevice createGroupDevice(RoutingSessionInfo routingSessionInfo, List list) {
        String id = routingSessionInfo.getId();
        String valueOf = String.valueOf(routingSessionInfo.getName());
        ImageVectorConverterPainter.Companion companion = ImageVectorConverterPainter.Companion;
        Icons$Device icons$Device = Icons$Device.INSTANCE;
        ImageVector imageVector = (ImageVector) GroupSpeakerKt.GroupSpeaker$delegate.getValue();
        companion.getClass();
        ImageVectorConverterPainter converter = ImageVectorConverterPainter.Companion.toConverter(imageVector);
        Icons$Badge icons$Badge = Icons$Badge.INSTANCE;
        GroupDevice groupDevice = new GroupDevice(id, valueOf, null, converter, (ImageVector) ChromecastKt.Chromecast$delegate.getValue(), routingSessionInfo.getVolume(), routingSessionInfo.getVolumeMax(), null, list, 132, null);
        groupDevice.controllerType = ControllerType.ChromeCastGroup;
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
        ChromeCastDevice chromeCastDevice = new ChromeCastDevice(id, displayName, null, converter, null, volume, volumeMax, state, z, z2, z3, z4, 20, null);
        chromeCastDevice.mediaRoute2Info = mediaRoute2Info;
        chromeCastDevice.isInAppCasting = z4;
        return chromeCastDevice;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController, com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void transfer(AudioDevice audioDevice) {
        MediaRoute2Info mediaRoute2Info;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "ChromeCastDeviceController");
        MediaRoute2Info mediaRoute2Info2 = null;
        if ((audioDevice instanceof ChromeCastDevice) && (mediaRoute2Info = ((ChromeCastDevice) audioDevice).mediaRoute2Info) != null) {
            mediaRoute2Info2 = mediaRoute2Info;
        }
        if (mediaRoute2Info2 != null) {
            getRouter2Manager().transfer(this.packageName, mediaRoute2Info2, Process.myUserHandle());
        } else {
            super.transfer(audioDevice);
        }
    }
}
