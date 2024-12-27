package com.android.systemui.media.mediaoutput.entity;

import android.media.MediaRoute2Info;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AudioMirroringDevice implements RouteDevice {
    public final ImageVector badge;
    public final boolean cancelable;
    public final ControllerType controllerType;
    public final String description;
    public final boolean deselectable;
    public final Painter icon;
    public final String id;
    public MediaRoute2Info mediaRoute2Info;
    public final CharSequence name;
    public final boolean selectable;
    public final State state;
    public final boolean transferable;
    public final int volume;
    public final int volumeMax;

    public AudioMirroringDevice(String str, CharSequence charSequence, String str2, Painter painter, ImageVector imageVector, int i, int i2, State state, boolean z, boolean z2, boolean z3, boolean z4) {
        this.id = str;
        this.name = charSequence;
        this.description = str2;
        this.icon = painter;
        this.badge = imageVector;
        this.volume = i;
        this.volumeMax = i2;
        this.state = state;
        this.selectable = z;
        this.deselectable = z2;
        this.transferable = z3;
        this.cancelable = z4;
        this.controllerType = ControllerType.AudioMirroring;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final AudioDevice clone() {
        AudioMirroringDevice audioMirroringDevice = new AudioMirroringDevice(this.id, this.name, this.description, this.icon, this.badge, this.volume, this.volumeMax, this.state, this.selectable, this.deselectable, this.transferable, this.cancelable);
        MediaRoute2Info mediaRoute2Info = this.mediaRoute2Info;
        if (mediaRoute2Info == null) {
            mediaRoute2Info = null;
        }
        audioMirroringDevice.mediaRoute2Info = mediaRoute2Info;
        return audioMirroringDevice;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AudioMirroringDevice)) {
            return false;
        }
        AudioMirroringDevice audioMirroringDevice = (AudioMirroringDevice) obj;
        return Intrinsics.areEqual(this.id, audioMirroringDevice.id) && Intrinsics.areEqual(this.name, audioMirroringDevice.name) && Intrinsics.areEqual(this.description, audioMirroringDevice.description) && Intrinsics.areEqual(this.icon, audioMirroringDevice.icon) && Intrinsics.areEqual(this.badge, audioMirroringDevice.badge) && this.volume == audioMirroringDevice.volume && this.volumeMax == audioMirroringDevice.volumeMax && this.state == audioMirroringDevice.state && this.selectable == audioMirroringDevice.selectable && this.deselectable == audioMirroringDevice.deselectable && this.transferable == audioMirroringDevice.transferable && this.cancelable == audioMirroringDevice.cancelable;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice, com.android.systemui.media.mediaoutput.entity.EntityString
    public final List getAttributes() {
        return CollectionsKt___CollectionsKt.plus((Iterable) CollectionsKt__CollectionsKt.listOf(new Pair("volumeMax", Integer.valueOf(this.volumeMax)), new Pair("isSelectable", Boolean.valueOf(this.selectable)), new Pair("isDeselectable", Boolean.valueOf(this.deselectable)), new Pair("isTransferable", Boolean.valueOf(this.transferable))), (Collection) super.getAttributes());
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
    public final boolean getDeselectable() {
        return this.deselectable;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final Painter getIcon() {
        return this.icon;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final String getId() {
        return this.id;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.RouteDevice
    public final MediaRoute2Info getMediaRoute2Info() {
        MediaRoute2Info mediaRoute2Info = this.mediaRoute2Info;
        if (mediaRoute2Info != null) {
            return mediaRoute2Info;
        }
        return null;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final CharSequence getName() {
        return this.name;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final boolean getSelectable() {
        return this.selectable;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final State getState() {
        return this.state;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final boolean getTransferable() {
        return this.transferable;
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
        return Boolean.hashCode(this.cancelable) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((this.state.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.volumeMax, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.volume, (hashCode + (imageVector != null ? imageVector.hashCode() : 0)) * 31, 31), 31)) * 31, 31, this.selectable), 31, this.deselectable), 31, this.transferable);
    }

    public final String toString() {
        return toLogText();
    }

    public /* synthetic */ AudioMirroringDevice(String str, CharSequence charSequence, String str2, Painter painter, ImageVector imageVector, int i, int i2, State state, boolean z, boolean z2, boolean z3, boolean z4, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, charSequence, (i3 & 4) != 0 ? null : str2, painter, (i3 & 16) != 0 ? null : imageVector, i, i2, state, (i3 & 256) != 0 ? false : z, (i3 & 512) != 0 ? false : z2, (i3 & 1024) != 0 ? true : z3, (i3 & 2048) != 0 ? false : z4);
    }
}
