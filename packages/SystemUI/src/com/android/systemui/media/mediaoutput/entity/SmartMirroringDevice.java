package com.android.systemui.media.mediaoutput.entity;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.systemui.R;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import com.android.systemui.media.mediaoutput.controller.device.DeviceInfo;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import com.android.systemui.media.mediaoutput.icons.Icons;
import com.android.systemui.media.mediaoutput.icons.badge.SmartViewKt;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class SmartMirroringDevice implements AudioDevice {
    public static final Companion Companion = new Companion(null);
    public final Painter badge;
    public final boolean cancelable;
    public final ControllerType controllerType;
    public final CharSequence description;
    public DeviceInfo deviceInfo;
    public final Painter icon;
    public final String id;
    public final String name;
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

    public SmartMirroringDevice(String str, String str2, CharSequence charSequence, Painter painter, Painter painter2, int i, int i2, State state, boolean z) {
        this.id = str;
        this.name = str2;
        this.description = charSequence;
        this.icon = painter;
        this.badge = painter2;
        this.volume = i;
        this.volumeMax = i2;
        this.state = state;
        this.cancelable = z;
        this.controllerType = ControllerType.SmartMirroring;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final AudioDevice clone() {
        SmartMirroringDevice smartMirroringDevice = new SmartMirroringDevice(this.id, this.name, this.description, this.icon, this.badge, this.volume, this.volumeMax, this.state, this.cancelable);
        DeviceInfo deviceInfo = this.deviceInfo;
        if (deviceInfo == null) {
            deviceInfo = null;
        }
        smartMirroringDevice.deviceInfo = deviceInfo;
        return smartMirroringDevice;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SmartMirroringDevice)) {
            return false;
        }
        SmartMirroringDevice smartMirroringDevice = (SmartMirroringDevice) obj;
        return Intrinsics.areEqual(this.id, smartMirroringDevice.id) && Intrinsics.areEqual(this.name, smartMirroringDevice.name) && Intrinsics.areEqual(this.description, smartMirroringDevice.description) && Intrinsics.areEqual(this.icon, smartMirroringDevice.icon) && Intrinsics.areEqual(this.badge, smartMirroringDevice.badge) && this.volume == smartMirroringDevice.volume && this.volumeMax == smartMirroringDevice.volumeMax && this.state == smartMirroringDevice.state && this.cancelable == smartMirroringDevice.cancelable;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final Painter getBadge() {
        return this.badge;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final boolean getCancelable() {
        return this.cancelable;
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
        int iM = AudioMirroringDevice$$ExternalSyntheticOutline0.m(this.icon, ControlInfo$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name), 31, this.description), 31);
        Painter painter = this.badge;
        return Boolean.hashCode(this.cancelable) + ((this.state.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.volumeMax, ReorderTile$$ExternalSyntheticOutline0.m(this.volume, (iM + (painter == null ? 0 : painter.hashCode())) * 31, 31), 31)) * 31);
    }

    public final String toString() {
        return toLogText();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public SmartMirroringDevice(String str, String str2, CharSequence charSequence, Painter painter, Painter painter2, int i, int i2, State state, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        Painter converter;
        CharSequence resourceString = (i3 & 4) != 0 ? new ResourceString(R.string.mirror_screen, null, 2, null) : charSequence;
        if ((i3 & 16) != 0) {
            ImageVectorConverterPainter.Companion companion = ImageVectorConverterPainter.Companion;
            Icons.Badge badge = Icons.Badge.INSTANCE;
            ImageVector imageVector = (ImageVector) SmartViewKt.SmartView$delegate.getValue();
            companion.getClass();
            converter = ImageVectorConverterPainter.Companion.toConverter(imageVector);
        } else {
            converter = painter2;
        }
        this(str, str2, resourceString, painter, converter, (i3 & 32) != 0 ? 0 : i, (i3 & 64) != 0 ? 0 : i2, (i3 & 128) != 0 ? State.DISCONNECTED : state, (i3 & 256) != 0 ? false : z);
    }
}
