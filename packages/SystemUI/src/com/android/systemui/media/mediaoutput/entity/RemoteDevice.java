package com.android.systemui.media.mediaoutput.entity;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import com.android.systemui.media.mediaoutput.icons.Icons$Device;
import com.android.systemui.media.mediaoutput.icons.device.TvKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class RemoteDevice implements AudioDevice {
    public static final Companion Companion = new Companion(null);
    public final ControllerType controllerType = ControllerType.Remote;
    public final ImageVectorConverterPainter icon;
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

    public RemoteDevice(String str, CharSequence charSequence, int i, int i2) {
        this.id = str;
        this.name = charSequence;
        this.volume = i;
        this.volumeMax = i2;
        ImageVectorConverterPainter.Companion companion = ImageVectorConverterPainter.Companion;
        Icons$Device icons$Device = Icons$Device.INSTANCE;
        ImageVector tv = TvKt.getTv();
        companion.getClass();
        this.icon = ImageVectorConverterPainter.Companion.toConverter(tv);
        this.state = State.SELECTED;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RemoteDevice)) {
            return false;
        }
        RemoteDevice remoteDevice = (RemoteDevice) obj;
        return Intrinsics.areEqual(this.id, remoteDevice.id) && Intrinsics.areEqual(this.name, remoteDevice.name) && this.volume == remoteDevice.volume && this.volumeMax == remoteDevice.volumeMax;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final ImageVector getBadge() {
        return null;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final ControllerType getControllerType() {
        return this.controllerType;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final CharSequence getDescription() {
        return null;
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
        return Integer.hashCode(this.volumeMax) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.volume, ControlInfo$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name), 31);
    }

    public final String toString() {
        return toLogText();
    }
}
