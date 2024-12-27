package com.android.systemui.media.mediaoutput.entity;

import android.media.AudioDeviceInfo;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import com.android.systemui.media.mediaoutput.ext.AudioDeviceInfoExt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class SmartViewDevice implements AudioDevice {
    public static final Companion Companion = new Companion(null);
    public AudioDeviceInfo audioDeviceInfo;
    public final ImageVector badge;
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
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SmartViewDevice(String str, String str2, CharSequence charSequence, Painter painter, ImageVector imageVector, int i, int i2, State state, boolean z, boolean z2) {
        this.id = str;
        this.name = str2;
        this.description = charSequence;
        this.icon = painter;
        this.badge = imageVector;
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
        int hashCode = (this.icon.hashCode() + ControlInfo$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name), 31, this.description)) * 31;
        ImageVector imageVector = this.badge;
        return Boolean.hashCode(this.cancelable) + TransitionData$$ExternalSyntheticOutline0.m((this.state.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.volumeMax, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.volume, (hashCode + (imageVector == null ? 0 : imageVector.hashCode())) * 31, 31), 31)) * 31, 31, this.selectable);
    }

    public final String toString() {
        return toLogText();
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SmartViewDevice(java.lang.String r17, java.lang.String r18, java.lang.CharSequence r19, androidx.compose.ui.graphics.painter.Painter r20, androidx.compose.ui.graphics.vector.ImageVector r21, int r22, int r23, com.android.systemui.media.mediaoutput.entity.State r24, boolean r25, boolean r26, int r27, kotlin.jvm.internal.DefaultConstructorMarker r28) {
        /*
            r16 = this;
            r0 = r27
            r1 = r0 & 4
            if (r1 == 0) goto L12
            com.android.systemui.media.mediaoutput.ext.ResourceString r1 = new com.android.systemui.media.mediaoutput.ext.ResourceString
            r2 = 2
            r3 = 0
            r4 = 2131954693(0x7f130c05, float:1.9545892E38)
            r1.<init>(r4, r3, r2, r3)
            r8 = r1
            goto L14
        L12:
            r8 = r19
        L14:
            r1 = r0 & 16
            if (r1 == 0) goto L24
            com.android.systemui.media.mediaoutput.icons.Icons$Badge r1 = com.android.systemui.media.mediaoutput.icons.Icons$Badge.INSTANCE
            kotlin.Lazy r1 = com.android.systemui.media.mediaoutput.icons.badge.SmartViewKt.SmartView$delegate
            java.lang.Object r1 = r1.getValue()
            androidx.compose.ui.graphics.vector.ImageVector r1 = (androidx.compose.ui.graphics.vector.ImageVector) r1
            r10 = r1
            goto L26
        L24:
            r10 = r21
        L26:
            r1 = r0 & 32
            r2 = 0
            if (r1 == 0) goto L2d
            r11 = r2
            goto L2f
        L2d:
            r11 = r22
        L2f:
            r1 = r0 & 64
            if (r1 == 0) goto L37
            r1 = 150(0x96, float:2.1E-43)
            r12 = r1
            goto L39
        L37:
            r12 = r23
        L39:
            r1 = r0 & 128(0x80, float:1.794E-43)
            if (r1 == 0) goto L41
            com.android.systemui.media.mediaoutput.entity.State r1 = com.android.systemui.media.mediaoutput.entity.State.CONNECTED
            r13 = r1
            goto L43
        L41:
            r13 = r24
        L43:
            r1 = r0 & 256(0x100, float:3.59E-43)
            if (r1 == 0) goto L49
            r14 = r2
            goto L4b
        L49:
            r14 = r25
        L4b:
            r0 = r0 & 512(0x200, float:7.175E-43)
            if (r0 == 0) goto L52
            r0 = 1
            r15 = r0
            goto L54
        L52:
            r15 = r26
        L54:
            r5 = r16
            r6 = r17
            r7 = r18
            r9 = r20
            r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.entity.SmartViewDevice.<init>(java.lang.String, java.lang.String, java.lang.CharSequence, androidx.compose.ui.graphics.painter.Painter, androidx.compose.ui.graphics.vector.ImageVector, int, int, com.android.systemui.media.mediaoutput.entity.State, boolean, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
