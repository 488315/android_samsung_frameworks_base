package com.android.systemui.media.mediaoutput.entity;

import android.media.AudioDeviceInfo;
import androidx.compose.ui.graphics.painter.Painter;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import com.android.systemui.media.mediaoutput.ext.AudioDeviceInfoExt;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class BuiltInDevice implements AudioDevice {
    public static final Companion Companion = new Companion(null);
    public AudioDeviceInfo audioDeviceInfo;
    public final Painter badge;
    public final ControllerType controllerType;
    public final CharSequence description;
    public final boolean force;
    public final Painter icon;
    public final String id;
    public ResourceString multiSoundInfo;
    public final CharSequence name;
    public RouteDevice routeDevice;
    public ControllerType routingControllerType;
    public final State state;
    public final int volume;
    public final int volumeMax;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public BuiltInDevice(String str, CharSequence charSequence, CharSequence charSequence2, Painter painter, Painter painter2, int i, int i2, State state, boolean z) {
        this.id = str;
        this.name = charSequence;
        this.description = charSequence2;
        this.icon = painter;
        this.badge = painter2;
        this.volume = i;
        this.volumeMax = i2;
        this.state = state;
        this.force = z;
        this.controllerType = ControllerType.BuiltIn;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v5, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r12v2, types: [androidx.compose.ui.graphics.painter.Painter] */
    public static BuiltInDevice copy$default(BuiltInDevice builtInDevice, ResourceString resourceString, ImageVectorConverterPainter imageVectorConverterPainter, int i, State state, int i2) {
        String str = builtInDevice.id;
        CharSequence charSequence = builtInDevice.name;
        ResourceString resourceString2 = resourceString;
        if ((i2 & 4) != 0) {
            resourceString2 = builtInDevice.description;
        }
        ResourceString resourceString3 = resourceString2;
        Painter painter = builtInDevice.icon;
        ImageVectorConverterPainter imageVectorConverterPainter2 = imageVectorConverterPainter;
        if ((i2 & 16) != 0) {
            imageVectorConverterPainter2 = builtInDevice.badge;
        }
        ImageVectorConverterPainter imageVectorConverterPainter3 = imageVectorConverterPainter2;
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
        return new BuiltInDevice(str, charSequence, resourceString3, painter, imageVectorConverterPainter3, i3, i4, state, z);
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final AudioDevice clone() {
        BuiltInDevice builtInDeviceCopy$default = copy$default(this, null, null, 0, null, 511);
        builtInDeviceCopy$default.deepCopy(this);
        return builtInDeviceCopy$default;
    }

    public final void deepCopy(BuiltInDevice builtInDevice) {
        if (builtInDevice == null) {
            builtInDevice = null;
        }
        if (builtInDevice != null) {
            AudioDeviceInfo audioDeviceInfo = builtInDevice.audioDeviceInfo;
            this.audioDeviceInfo = audioDeviceInfo != null ? audioDeviceInfo : null;
            this.routeDevice = builtInDevice.routeDevice;
            this.routingControllerType = builtInDevice.routingControllerType;
            this.multiSoundInfo = builtInDevice.multiSoundInfo;
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
        return CollectionsKt___CollectionsKt.plus((Iterable) Arrays.asList(new Pair("routeDevice", routeDevice != null ? routeDevice.getName() : null), new Pair("routingControllerType", this.routingControllerType)), (Collection) attributes);
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final Painter getBadge() {
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
        int iM = ControlInfo$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name);
        CharSequence charSequence = this.description;
        int iM2 = AudioMirroringDevice$$ExternalSyntheticOutline0.m(this.icon, (iM + (charSequence == null ? 0 : charSequence.hashCode())) * 31, 31);
        Painter painter = this.badge;
        return Boolean.hashCode(this.force) + ((this.state.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.volumeMax, ReorderTile$$ExternalSyntheticOutline0.m(this.volume, (iM2 + (painter != null ? painter.hashCode() : 0)) * 31, 31), 31)) * 31);
    }

    public final String toString() {
        return toLogText();
    }

    public /* synthetic */ BuiltInDevice(String str, CharSequence charSequence, CharSequence charSequence2, Painter painter, Painter painter2, int i, int i2, State state, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, charSequence, (i3 & 4) != 0 ? null : charSequence2, painter, (i3 & 16) != 0 ? null : painter2, (i3 & 32) != 0 ? 0 : i, (i3 & 64) != 0 ? 150 : i2, (i3 & 128) != 0 ? State.CONNECTED : state, (i3 & 256) != 0 ? false : z);
    }
}
