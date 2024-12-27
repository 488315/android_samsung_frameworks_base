package com.android.systemui.media.mediaoutput.entity;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DisconnectedDevice implements AudioDevice {
    public static final Companion Companion = new Companion(null);
    public final ImageVector badge;
    public CachedBluetoothDevice cachedBluetoothDevice;
    public final ControllerType controllerType;
    public final String description;
    public final Painter icon;
    public final String id;
    public final String name;
    public final State state;
    public final int volume;
    public final int volumeMax;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public DisconnectedDevice(String str, String str2, String str3, Painter painter, ImageVector imageVector, int i, int i2, State state) {
        this.id = str;
        this.name = str2;
        this.description = str3;
        this.icon = painter;
        this.badge = imageVector;
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
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name);
        String str = this.description;
        int hashCode = (this.icon.hashCode() + ((m + (str == null ? 0 : str.hashCode())) * 31)) * 31;
        ImageVector imageVector = this.badge;
        return this.state.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.volumeMax, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.volume, (hashCode + (imageVector != null ? imageVector.hashCode() : 0)) * 31, 31), 31);
    }

    public final String toString() {
        return toLogText();
    }

    public /* synthetic */ DisconnectedDevice(String str, String str2, String str3, Painter painter, ImageVector imageVector, int i, int i2, State state, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i3 & 4) != 0 ? null : str3, painter, (i3 & 16) != 0 ? null : imageVector, (i3 & 32) != 0 ? 0 : i, (i3 & 64) != 0 ? 0 : i2, (i3 & 128) != 0 ? State.DISCONNECTED : state);
    }
}
