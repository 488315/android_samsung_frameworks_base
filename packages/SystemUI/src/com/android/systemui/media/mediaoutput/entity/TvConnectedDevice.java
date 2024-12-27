package com.android.systemui.media.mediaoutput.entity;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class TvConnectedDevice implements AudioDevice {
    public static final Companion Companion = new Companion(null);
    public final ImageVector badge;
    public final ControllerType controllerType;
    public final String description;
    public final Painter icon;
    public final String id;
    public final CharSequence name;
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

    public TvConnectedDevice(String str, CharSequence charSequence, String str2, Painter painter, ImageVector imageVector, State state, int i, int i2) {
        this.id = str;
        this.name = charSequence;
        this.description = str2;
        this.icon = painter;
        this.badge = imageVector;
        this.state = state;
        this.volume = i;
        this.volumeMax = i2;
        this.controllerType = ControllerType.None;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TvConnectedDevice)) {
            return false;
        }
        TvConnectedDevice tvConnectedDevice = (TvConnectedDevice) obj;
        return Intrinsics.areEqual(this.id, tvConnectedDevice.id) && Intrinsics.areEqual(this.name, tvConnectedDevice.name) && Intrinsics.areEqual(this.description, tvConnectedDevice.description) && Intrinsics.areEqual(this.icon, tvConnectedDevice.icon) && Intrinsics.areEqual(this.badge, tvConnectedDevice.badge) && this.state == tvConnectedDevice.state && this.volume == tvConnectedDevice.volume && this.volumeMax == tvConnectedDevice.volumeMax;
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
        int m = ControlInfo$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name);
        String str = this.description;
        int hashCode = (this.icon.hashCode() + ((m + (str == null ? 0 : str.hashCode())) * 31)) * 31;
        ImageVector imageVector = this.badge;
        return Integer.hashCode(this.volumeMax) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.volume, (this.state.hashCode() + ((hashCode + (imageVector != null ? imageVector.hashCode() : 0)) * 31)) * 31, 31);
    }

    public final String toString() {
        return toLogText();
    }

    public /* synthetic */ TvConnectedDevice(String str, CharSequence charSequence, String str2, Painter painter, ImageVector imageVector, State state, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, charSequence, (i3 & 4) != 0 ? null : str2, painter, (i3 & 16) != 0 ? null : imageVector, (i3 & 32) != 0 ? State.CONNECTED : state, (i3 & 64) != 0 ? 0 : i, (i3 & 128) != 0 ? 0 : i2);
    }
}
