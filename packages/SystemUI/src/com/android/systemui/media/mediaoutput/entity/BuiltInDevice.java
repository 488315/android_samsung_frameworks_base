package com.android.systemui.media.mediaoutput.entity;

import android.media.AudioDeviceInfo;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import com.android.systemui.media.mediaoutput.ext.AudioDeviceInfoExt;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class BuiltInDevice implements AudioDevice {
    public static final Companion Companion = new Companion(null);
    public AudioDeviceInfo audioDeviceInfo;
    public final ImageVector badge;
    public final ControllerType controllerType;
    public final String description;
    public final boolean force;
    public final Painter icon;
    public final String id;
    public final CharSequence name;
    public RouteDevice routeDevice;
    public ControllerType routingControllerType;
    public final State state;
    public final int volume;
    public final int volumeMax;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BuiltInDevice(String str, CharSequence charSequence, String str2, Painter painter, ImageVector imageVector, int i, int i2, State state, boolean z) {
        this.id = str;
        this.name = charSequence;
        this.description = str2;
        this.icon = painter;
        this.badge = imageVector;
        this.volume = i;
        this.volumeMax = i2;
        this.state = state;
        this.force = z;
        this.controllerType = ControllerType.BuiltIn;
    }

    public static BuiltInDevice copy$default(BuiltInDevice builtInDevice, ImageVector imageVector, int i, State state, int i2) {
        String str = builtInDevice.id;
        CharSequence charSequence = builtInDevice.name;
        String str2 = builtInDevice.description;
        Painter painter = builtInDevice.icon;
        if ((i2 & 16) != 0) {
            imageVector = builtInDevice.badge;
        }
        ImageVector imageVector2 = imageVector;
        if ((i2 & 32) != 0) {
            i = builtInDevice.volume;
        }
        int i3 = i;
        int i4 = builtInDevice.volumeMax;
        if ((i2 & 128) != 0) {
            state = builtInDevice.state;
        }
        boolean z = builtInDevice.force;
        builtInDevice.getClass();
        return new BuiltInDevice(str, charSequence, str2, painter, imageVector2, i3, i4, state, z);
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final AudioDevice clone() {
        BuiltInDevice copy$default = copy$default(this, null, 0, null, 511);
        copy$default.deepCopy$1(this);
        return copy$default;
    }

    public final void deepCopy$1(AudioDevice audioDevice) {
        BuiltInDevice builtInDevice = audioDevice instanceof BuiltInDevice ? (BuiltInDevice) audioDevice : null;
        if (builtInDevice != null) {
            AudioDeviceInfo audioDeviceInfo = builtInDevice.audioDeviceInfo;
            this.audioDeviceInfo = audioDeviceInfo != null ? audioDeviceInfo : null;
            this.routeDevice = builtInDevice.routeDevice;
            this.routingControllerType = builtInDevice.routingControllerType;
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BuiltInDevice)) {
            return false;
        }
        BuiltInDevice builtInDevice = (BuiltInDevice) obj;
        return Intrinsics.areEqual(this.id, builtInDevice.id) && Intrinsics.areEqual(this.name, builtInDevice.name) && Intrinsics.areEqual(this.description, builtInDevice.description) && Intrinsics.areEqual(this.icon, builtInDevice.icon) && Intrinsics.areEqual(this.badge, builtInDevice.badge) && this.volume == builtInDevice.volume && this.volumeMax == builtInDevice.volumeMax && this.state == builtInDevice.state && this.force == builtInDevice.force;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice, com.android.systemui.media.mediaoutput.entity.EntityString
    public final List getAttributes() {
        List attributes = super.getAttributes();
        RouteDevice routeDevice = this.routeDevice;
        return CollectionsKt___CollectionsKt.plus((Iterable) CollectionsKt__CollectionsKt.listOf(new Pair("routeDevice", routeDevice != null ? routeDevice.getName() : null), new Pair("routingControllerType", this.routingControllerType)), (Collection) attributes);
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final ImageVector getBadge() {
        return this.badge;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final ControllerType getControllerType() {
        return this.controllerType;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final CharSequence getDescription() {
        return this.description;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final ControllerType getFinalControllerType() {
        ControllerType controllerType;
        RouteDevice routeDevice = this.routeDevice;
        if (routeDevice != null && (controllerType = routeDevice.getControllerType()) != null) {
            return controllerType;
        }
        ControllerType controllerType2 = this.routingControllerType;
        return controllerType2 == null ? this.controllerType : controllerType2;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final boolean getForce() {
        return this.force;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final Painter getIcon() {
        return this.icon;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final String getId() {
        return this.id;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final CharSequence getName() {
        return this.name;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final boolean getNeedEarProtect() {
        AudioDeviceInfoExt audioDeviceInfoExt = AudioDeviceInfoExt.INSTANCE;
        AudioDeviceInfo audioDeviceInfo = this.audioDeviceInfo;
        if (audioDeviceInfo == null) {
            audioDeviceInfo = null;
        }
        audioDeviceInfoExt.getClass();
        return AudioDeviceInfoExt.getNeedEarProtect(audioDeviceInfo);
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final State getState() {
        return this.state;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final int getVolume() {
        return this.volume;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final int getVolumeMax() {
        return this.volumeMax;
    }

    public final int hashCode() {
        int m = ControlInfo$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name);
        String str = this.description;
        int hashCode = (this.icon.hashCode() + ((m + (str == null ? 0 : str.hashCode())) * 31)) * 31;
        ImageVector imageVector = this.badge;
        return Boolean.hashCode(this.force) + ((this.state.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.volumeMax, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.volume, (hashCode + (imageVector != null ? imageVector.hashCode() : 0)) * 31, 31), 31)) * 31);
    }

    public final String toString() {
        return toLogText();
    }

    public /* synthetic */ BuiltInDevice(String str, CharSequence charSequence, String str2, Painter painter, ImageVector imageVector, int i, int i2, State state, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, charSequence, (i3 & 4) != 0 ? null : str2, painter, (i3 & 16) != 0 ? null : imageVector, (i3 & 32) != 0 ? 0 : i, (i3 & 64) != 0 ? 150 : i2, (i3 & 128) != 0 ? State.CONNECTED : state, (i3 & 256) != 0 ? false : z);
    }
}
