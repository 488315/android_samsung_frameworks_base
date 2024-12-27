package com.android.systemui.media.mediaoutput.entity;

import android.media.AudioDeviceInfo;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import com.android.systemui.media.mediaoutput.ext.AudioDeviceInfoExt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DexDevice implements AudioDevice {
    public static final Companion Companion = new Companion(null);
    public AudioDeviceInfo audioDeviceInfo;
    public final ImageVector badge;
    public final boolean cancelable;
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

    public DexDevice(String str, String str2, String str3, Painter painter, ImageVector imageVector, int i, int i2, State state, boolean z) {
        this.id = str;
        this.name = str2;
        this.description = str3;
        this.icon = painter;
        this.badge = imageVector;
        this.volume = i;
        this.volumeMax = i2;
        this.state = state;
        this.cancelable = z;
        this.controllerType = ControllerType.Dex;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final AudioDevice clone() {
        DexDevice dexDevice = new DexDevice(this.id, this.name, this.description, this.icon, this.badge, this.volume, this.volumeMax, this.state, this.cancelable);
        AudioDeviceInfo audioDeviceInfo = this.audioDeviceInfo;
        if (audioDeviceInfo == null) {
            audioDeviceInfo = null;
        }
        dexDevice.audioDeviceInfo = audioDeviceInfo;
        return dexDevice;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DexDevice)) {
            return false;
        }
        DexDevice dexDevice = (DexDevice) obj;
        return Intrinsics.areEqual(this.id, dexDevice.id) && Intrinsics.areEqual(this.name, dexDevice.name) && Intrinsics.areEqual(this.description, dexDevice.description) && Intrinsics.areEqual(this.icon, dexDevice.icon) && Intrinsics.areEqual(this.badge, dexDevice.badge) && this.volume == dexDevice.volume && this.volumeMax == dexDevice.volumeMax && this.state == dexDevice.state && this.cancelable == dexDevice.cancelable;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final ImageVector getBadge() {
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
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name);
        String str = this.description;
        int hashCode = (this.icon.hashCode() + ((m + (str == null ? 0 : str.hashCode())) * 31)) * 31;
        ImageVector imageVector = this.badge;
        return Boolean.hashCode(this.cancelable) + ((this.state.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.volumeMax, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.volume, (hashCode + (imageVector != null ? imageVector.hashCode() : 0)) * 31, 31), 31)) * 31);
    }

    public final String toString() {
        return toLogText();
    }

    public /* synthetic */ DexDevice(String str, String str2, String str3, Painter painter, ImageVector imageVector, int i, int i2, State state, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i3 & 4) != 0 ? null : str3, painter, (i3 & 16) != 0 ? null : imageVector, (i3 & 32) != 0 ? 0 : i, (i3 & 64) != 0 ? 150 : i2, (i3 & 128) != 0 ? State.CONNECTED : state, (i3 & 256) != 0 ? true : z);
    }
}
