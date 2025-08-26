package com.android.systemui.media.mediaoutput.controller.device;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.RouteListingPreference;
import android.util.Log;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.datastore.core.DataStore;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.entity.ChromeCastDevice;
import com.android.systemui.media.mediaoutput.entity.RouteDevice;
import com.android.systemui.media.mediaoutput.entity.State;
import com.android.systemui.media.mediaoutput.ext.MediaRoute2InfoExtKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public class ChromeCastDeviceController extends RouteDeviceController {
    public final ControllerType controllerType;

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

    public ChromeCastDeviceController(Context context, AudioManager audioManager, DataStore dataStore) {
        super(context, audioManager, dataStore);
        this.controllerType = ControllerType.ChromeCast;
        Log.d("ChromeCastDeviceController", "init()");
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
        RouteListingPreference routeListingPreference = getRouter2Manager$1().getRouteListingPreference(this.packageName);
        ChromeCastDevice chromeCastDevice = new ChromeCastDevice(id, displayName, converter, state, volume, volumeMax, z, z2, z3, z4, mediaRoute2Info, item, routeListingPreference != null ? routeListingPreference.getLinkedItemComponentName() : null);
        chromeCastDevice.isInAppCasting = z4;
        return chromeCastDevice;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController
    public final ControllerType getControllerType() {
        return this.controllerType;
    }
}
