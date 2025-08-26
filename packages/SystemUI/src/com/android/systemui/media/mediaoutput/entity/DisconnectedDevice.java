package com.android.systemui.media.mediaoutput.entity;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.painter.Painter;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class DisconnectedDevice implements AudioDevice {
    public static final Companion Companion = new Companion(null);
    public final Painter badge;
    public CachedBluetoothDevice cachedBluetoothDevice;
    public final ControllerType controllerType;
    public final String description;
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

    public DisconnectedDevice(String str, String str2, String str3, Painter painter, Painter painter2, int i, int i2, State state) {
        this.id = str;
        this.name = str2;
        this.description = str3;
        this.icon = painter;
        this.badge = painter2;
        this.volume = i;
        this.volumeMax = i2;
        this.state = state;
        this.controllerType = ControllerType.Disconnected;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final AudioDevice clone() {
        DisconnectedDevice disconnectedDevice = new DisconnectedDevice(this.id, this.name, this.description, this.icon, this.badge, this.volume, this.volumeMax, this.state);
        CachedBluetoothDevice cachedBluetoothDevice = this.cachedBluetoothDevice;
        if (cachedBluetoothDevice == null) {
            cachedBluetoothDevice = null;
        }
        disconnectedDevice.cachedBluetoothDevice = cachedBluetoothDevice;
        return disconnectedDevice;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DisconnectedDevice)) {
            return false;
        }
        DisconnectedDevice disconnectedDevice = (DisconnectedDevice) obj;
        return Intrinsics.areEqual(this.id, disconnectedDevice.id) && Intrinsics.areEqual(this.name, disconnectedDevice.name) && Intrinsics.areEqual(this.description, disconnectedDevice.description) && Intrinsics.areEqual(this.icon, disconnectedDevice.icon) && Intrinsics.areEqual(this.badge, disconnectedDevice.badge) && this.volume == disconnectedDevice.volume && this.volumeMax == disconnectedDevice.volumeMax && this.state == disconnectedDevice.state;
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
        int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name);
        String str = this.description;
        int iM2 = AudioMirroringDevice$$ExternalSyntheticOutline0.m(this.icon, (iM + (str == null ? 0 : str.hashCode())) * 31, 31);
        Painter painter = this.badge;
        return this.state.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.volumeMax, ReorderTile$$ExternalSyntheticOutline0.m(this.volume, (iM2 + (painter != null ? painter.hashCode() : 0)) * 31, 31), 31);
    }

    public final String toString() {
        return toLogText();
    }

    public /* synthetic */ DisconnectedDevice(String str, String str2, String str3, Painter painter, Painter painter2, int i, int i2, State state, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i3 & 4) != 0 ? null : str3, painter, (i3 & 16) != 0 ? null : painter2, (i3 & 32) != 0 ? 0 : i, (i3 & 64) != 0 ? 0 : i2, (i3 & 128) != 0 ? State.DISCONNECTED : state);
    }
}
