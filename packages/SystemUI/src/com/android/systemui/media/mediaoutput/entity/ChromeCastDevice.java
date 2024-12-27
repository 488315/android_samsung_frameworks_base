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
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ChromeCastDevice implements RouteDevice {
    public final ImageVector badge;
    public final boolean cancelable;
    public final ControllerType controllerType;
    public final String description;
    public final boolean deselectable;
    public final Painter icon;
    public final String id;
    public boolean isInAppCasting;
    public MediaRoute2Info mediaRoute2Info;
    public final CharSequence name;
    public final boolean selectable;
    public final State state;
    public final boolean transferable;
    public final int volume;
    public final int volumeMax;

    public ChromeCastDevice(String str, CharSequence charSequence, String str2, Painter painter, ImageVector imageVector, int i, int i2, State state, boolean z, boolean z2, boolean z3, boolean z4) {
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
        this.controllerType = ControllerType.ChromeCast;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final AudioDevice clone() {
        ChromeCastDevice chromeCastDevice = new ChromeCastDevice(this.id, this.name, this.description, this.icon, this.badge, this.volume, this.volumeMax, this.state, this.selectable, this.deselectable, this.transferable, this.cancelable);
        MediaRoute2Info mediaRoute2Info = this.mediaRoute2Info;
        if (mediaRoute2Info == null) {
            mediaRoute2Info = null;
        }
        chromeCastDevice.mediaRoute2Info = mediaRoute2Info;
        chromeCastDevice.isInAppCasting = this.isInAppCasting;
        return chromeCastDevice;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ChromeCastDevice)) {
            return false;
        }
        ChromeCastDevice chromeCastDevice = (ChromeCastDevice) obj;
        return Intrinsics.areEqual(this.id, chromeCastDevice.id) && Intrinsics.areEqual(this.name, chromeCastDevice.name) && Intrinsics.areEqual(this.description, chromeCastDevice.description) && Intrinsics.areEqual(this.icon, chromeCastDevice.icon) && Intrinsics.areEqual(this.badge, chromeCastDevice.badge) && this.volume == chromeCastDevice.volume && this.volumeMax == chromeCastDevice.volumeMax && this.state == chromeCastDevice.state && this.selectable == chromeCastDevice.selectable && this.deselectable == chromeCastDevice.deselectable && this.transferable == chromeCastDevice.transferable && this.cancelable == chromeCastDevice.cancelable;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice, com.android.systemui.media.mediaoutput.entity.EntityString
    public final List getAttributes() {
        return CollectionsKt___CollectionsKt.plus((Iterable) CollectionsKt__CollectionsKt.listOf(new Pair("isInAppCasting", Boolean.valueOf(this.isInAppCasting)), new Pair("volumeMax", Integer.valueOf(this.volumeMax)), new Pair("isSelectable", Boolean.valueOf(this.selectable)), new Pair("isDeselectable", Boolean.valueOf(this.deselectable)), new Pair("isTransferable", Boolean.valueOf(this.transferable)), new Pair("isCancelable", Boolean.valueOf(this.cancelable))), (Collection) super.getAttributes());
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

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ChromeCastDevice(java.lang.String r16, java.lang.CharSequence r17, java.lang.String r18, androidx.compose.ui.graphics.painter.Painter r19, androidx.compose.ui.graphics.vector.ImageVector r20, int r21, int r22, com.android.systemui.media.mediaoutput.entity.State r23, boolean r24, boolean r25, boolean r26, boolean r27, int r28, kotlin.jvm.internal.DefaultConstructorMarker r29) {
        /*
            r15 = this;
            r0 = r28
            r1 = r0 & 4
            if (r1 == 0) goto L9
            r1 = 0
            r5 = r1
            goto Lb
        L9:
            r5 = r18
        Lb:
            r1 = r0 & 16
            if (r1 == 0) goto L1b
            com.android.systemui.media.mediaoutput.icons.Icons$Badge r1 = com.android.systemui.media.mediaoutput.icons.Icons$Badge.INSTANCE
            kotlin.Lazy r1 = com.android.systemui.media.mediaoutput.icons.badge.ChromecastKt.Chromecast$delegate
            java.lang.Object r1 = r1.getValue()
            androidx.compose.ui.graphics.vector.ImageVector r1 = (androidx.compose.ui.graphics.vector.ImageVector) r1
            r7 = r1
            goto L1d
        L1b:
            r7 = r20
        L1d:
            r1 = r0 & 256(0x100, float:3.59E-43)
            r2 = 0
            if (r1 == 0) goto L24
            r11 = r2
            goto L26
        L24:
            r11 = r24
        L26:
            r1 = r0 & 512(0x200, float:7.175E-43)
            if (r1 == 0) goto L2c
            r12 = r2
            goto L2e
        L2c:
            r12 = r25
        L2e:
            r1 = r0 & 1024(0x400, float:1.435E-42)
            if (r1 == 0) goto L35
            r1 = 1
            r13 = r1
            goto L37
        L35:
            r13 = r26
        L37:
            r0 = r0 & 2048(0x800, float:2.87E-42)
            if (r0 == 0) goto L3d
            r14 = r2
            goto L3f
        L3d:
            r14 = r27
        L3f:
            r2 = r15
            r3 = r16
            r4 = r17
            r6 = r19
            r8 = r21
            r9 = r22
            r10 = r23
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.entity.ChromeCastDevice.<init>(java.lang.String, java.lang.CharSequence, java.lang.String, androidx.compose.ui.graphics.painter.Painter, androidx.compose.ui.graphics.vector.ImageVector, int, int, com.android.systemui.media.mediaoutput.entity.State, boolean, boolean, boolean, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
