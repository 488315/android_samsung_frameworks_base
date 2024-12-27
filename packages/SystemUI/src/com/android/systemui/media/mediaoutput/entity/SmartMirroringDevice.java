package com.android.systemui.media.mediaoutput.entity;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import com.android.systemui.media.mediaoutput.controller.device.DeviceInfo;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SmartMirroringDevice implements AudioDevice {
    public static final Companion Companion = new Companion(null);
    public final ImageVector badge;
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SmartMirroringDevice(String str, String str2, CharSequence charSequence, Painter painter, ImageVector imageVector, int i, int i2, State state, boolean z) {
        this.id = str;
        this.name = str2;
        this.description = charSequence;
        this.icon = painter;
        this.badge = imageVector;
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
        return Boolean.hashCode(this.cancelable) + ((this.state.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.volumeMax, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.volume, (hashCode + (imageVector == null ? 0 : imageVector.hashCode())) * 31, 31), 31)) * 31);
    }

    public final String toString() {
        return toLogText();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SmartMirroringDevice(java.lang.String r16, java.lang.String r17, java.lang.CharSequence r18, androidx.compose.ui.graphics.painter.Painter r19, androidx.compose.ui.graphics.vector.ImageVector r20, int r21, int r22, com.android.systemui.media.mediaoutput.entity.State r23, boolean r24, int r25, kotlin.jvm.internal.DefaultConstructorMarker r26) {
        /*
            r15 = this;
            r0 = r25
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
            r8 = r18
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
            r10 = r20
        L26:
            r1 = r0 & 32
            r2 = 0
            if (r1 == 0) goto L2d
            r11 = r2
            goto L2f
        L2d:
            r11 = r21
        L2f:
            r1 = r0 & 64
            if (r1 == 0) goto L35
            r12 = r2
            goto L37
        L35:
            r12 = r22
        L37:
            r1 = r0 & 128(0x80, float:1.794E-43)
            if (r1 == 0) goto L3f
            com.android.systemui.media.mediaoutput.entity.State r1 = com.android.systemui.media.mediaoutput.entity.State.DISCONNECTED
            r13 = r1
            goto L41
        L3f:
            r13 = r23
        L41:
            r0 = r0 & 256(0x100, float:3.59E-43)
            if (r0 == 0) goto L47
            r14 = r2
            goto L49
        L47:
            r14 = r24
        L49:
            r5 = r15
            r6 = r16
            r7 = r17
            r9 = r19
            r5.<init>(r6, r7, r8, r9, r10, r11, r12, r13, r14)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.entity.SmartMirroringDevice.<init>(java.lang.String, java.lang.String, java.lang.CharSequence, androidx.compose.ui.graphics.painter.Painter, androidx.compose.ui.graphics.vector.ImageVector, int, int, com.android.systemui.media.mediaoutput.entity.State, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
