package com.android.systemui.media.mediaoutput.entity;

import android.media.AudioDeviceInfo;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.systemui.R;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import com.android.systemui.media.mediaoutput.ext.AudioDeviceInfoExt;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import com.android.systemui.media.mediaoutput.icons.Icons;
import com.android.systemui.media.mediaoutput.icons.badge.SmartViewKt;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class SmartViewDevice implements AudioDevice {
    public static final Companion Companion = new Companion(null);
    public AudioDeviceInfo audioDeviceInfo;
    public final Painter badge;
    public final boolean cancelable;
    public final ControllerType controllerType;
    public final CharSequence description;
    public final Painter icon;
    public final String id;
    public final String name;
    public final boolean selectable;
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

    public SmartViewDevice(String str, String str2, CharSequence charSequence, Painter painter, Painter painter2, int i, int i2, State state, boolean z, boolean z2) {
        this.id = str;
        this.name = str2;
        this.description = charSequence;
        this.icon = painter;
        this.badge = painter2;
        this.volume = i;
        this.volumeMax = i2;
        this.state = state;
        this.selectable = z;
        this.cancelable = z2;
        this.controllerType = ControllerType.SmartView;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final AudioDevice clone() {
        SmartViewDevice smartViewDevice = new SmartViewDevice(this.id, this.name, this.description, this.icon, this.badge, this.volume, this.volumeMax, this.state, this.selectable, this.cancelable);
        AudioDeviceInfo audioDeviceInfo = this.audioDeviceInfo;
        if (audioDeviceInfo == null) {
            audioDeviceInfo = null;
        }
        smartViewDevice.audioDeviceInfo = audioDeviceInfo;
        return smartViewDevice;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SmartViewDevice)) {
            return false;
        }
        SmartViewDevice smartViewDevice = (SmartViewDevice) obj;
        return Intrinsics.areEqual(this.id, smartViewDevice.id) && Intrinsics.areEqual(this.name, smartViewDevice.name) && Intrinsics.areEqual(this.description, smartViewDevice.description) && Intrinsics.areEqual(this.icon, smartViewDevice.icon) && Intrinsics.areEqual(this.badge, smartViewDevice.badge) && this.volume == smartViewDevice.volume && this.volumeMax == smartViewDevice.volumeMax && this.state == smartViewDevice.state && this.selectable == smartViewDevice.selectable && this.cancelable == smartViewDevice.cancelable;
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
    public final boolean getSelectable() {
        return this.selectable;
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
        return Boolean.hashCode(this.cancelable) + TransitionData$$ExternalSyntheticOutline0.m((this.state.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.volumeMax, ReorderTile$$ExternalSyntheticOutline0.m(this.volume, (iM + (painter == null ? 0 : painter.hashCode())) * 31, 31), 31)) * 31, 31, this.selectable);
    }

    public final String toString() {
        return toLogText();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public SmartViewDevice(String str, String str2, CharSequence charSequence, Painter painter, Painter painter2, int i, int i2, State state, boolean z, boolean z2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
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
        this(str, str2, resourceString, painter, converter, (i3 & 32) != 0 ? 0 : i, (i3 & 64) != 0 ? 150 : i2, (i3 & 128) != 0 ? State.CONNECTED : state, (i3 & 256) != 0 ? false : z, (i3 & 512) != 0 ? true : z2);
    }
}
