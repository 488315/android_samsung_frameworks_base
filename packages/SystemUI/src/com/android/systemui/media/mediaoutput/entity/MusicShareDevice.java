package com.android.systemui.media.mediaoutput.entity;

import android.media.AudioDeviceInfo;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.painter.Painter;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDevice;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MusicShareDevice implements AudioDevice, BluetoothRoute {
    public static final Companion Companion = new Companion(null);
    public AudioDeviceInfo audioDeviceInfo;
    public final Painter badge;
    public CachedBluetoothCastDevice cachedBluetoothCastDevice;
    public CachedBluetoothDevice cachedBluetoothDevice;
    public final boolean cancelable;
    public ControllerType controllerType;
    public final CharSequence description;
    public final Painter icon;
    public final String id;
    public final String name;
    public RouteDevice routeDevice;
    public ControllerType routingControllerType;
    public final boolean selectable;
    public final State state;
    public final boolean transferable;
    public final int volume;
    public final int volumeMax;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public MusicShareDevice(String str, String str2, CharSequence charSequence, Painter painter, Painter painter2, int i, int i2, State state, boolean z, boolean z2, boolean z3) {
        this.id = str;
        this.name = str2;
        this.description = charSequence;
        this.icon = painter;
        this.badge = painter2;
        this.volume = i;
        this.volumeMax = i2;
        this.state = state;
        this.selectable = z;
        this.transferable = z2;
        this.cancelable = z3;
        this.controllerType = ControllerType.MusicShare;
    }

    public static MusicShareDevice copy$default(MusicShareDevice musicShareDevice, CharSequence charSequence, int i, State state, boolean z, int i2) {
        String str = musicShareDevice.id;
        String str2 = musicShareDevice.name;
        if ((i2 & 4) != 0) {
            charSequence = musicShareDevice.description;
        }
        CharSequence charSequence2 = charSequence;
        Painter painter = musicShareDevice.icon;
        Painter painter2 = musicShareDevice.badge;
        if ((i2 & 32) != 0) {
            i = musicShareDevice.volume;
        }
        int i3 = i;
        int i4 = musicShareDevice.volumeMax;
        State state2 = (i2 & 128) != 0 ? musicShareDevice.state : state;
        boolean z2 = (i2 & 256) != 0 ? musicShareDevice.selectable : z;
        boolean z3 = musicShareDevice.transferable;
        boolean z4 = musicShareDevice.cancelable;
        musicShareDevice.getClass();
        return new MusicShareDevice(str, str2, charSequence2, painter, painter2, i3, i4, state2, z2, z3, z4);
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final AudioDevice clone() {
        MusicShareDevice copy$default = copy$default(this, null, 0, null, false, CustomDeviceManager.SETTINGS_ALL_PREVIOUS);
        copy$default.deepCopy(this);
        return copy$default;
    }

    public final void deepCopy(MusicShareDevice musicShareDevice) {
        if (musicShareDevice == null) {
            musicShareDevice = null;
        }
        if (musicShareDevice != null) {
            this.controllerType = musicShareDevice.controllerType;
            this.cachedBluetoothCastDevice = musicShareDevice.cachedBluetoothCastDevice;
            this.cachedBluetoothDevice = musicShareDevice.cachedBluetoothDevice;
            this.audioDeviceInfo = musicShareDevice.audioDeviceInfo;
            this.routeDevice = musicShareDevice.routeDevice;
            this.routingControllerType = musicShareDevice.routingControllerType;
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MusicShareDevice)) {
            return false;
        }
        MusicShareDevice musicShareDevice = (MusicShareDevice) obj;
        return Intrinsics.areEqual(this.id, musicShareDevice.id) && Intrinsics.areEqual(this.name, musicShareDevice.name) && Intrinsics.areEqual(this.description, musicShareDevice.description) && Intrinsics.areEqual(this.icon, musicShareDevice.icon) && Intrinsics.areEqual(this.badge, musicShareDevice.badge) && this.volume == musicShareDevice.volume && this.volumeMax == musicShareDevice.volumeMax && this.state == musicShareDevice.state && this.selectable == musicShareDevice.selectable && this.transferable == musicShareDevice.transferable && this.cancelable == musicShareDevice.cancelable;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice, com.android.systemui.media.mediaoutput.entity.EntityString
    public final List getAttributes() {
        List attributes = super.getAttributes();
        Pair pair = new Pair("isTransferable", Boolean.valueOf(this.transferable));
        Pair pair2 = new Pair("isCancelable", Boolean.valueOf(this.cancelable));
        RouteDevice routeDevice = this.routeDevice;
        return CollectionsKt___CollectionsKt.plus((Iterable) Arrays.asList(pair, pair2, new Pair("routeDevice", routeDevice != null ? routeDevice.getName() : null), new Pair("routingControllerType", this.routingControllerType)), (Collection) attributes);
    }

    @Override // com.android.systemui.media.mediaoutput.entity.BluetoothRoute
    public final AudioDeviceInfo getAudioDeviceInfo() {
        return this.audioDeviceInfo;
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
    public final ControllerType getFinalControllerType() {
        ControllerType controllerType;
        RouteDevice routeDevice = this.routeDevice;
        if (routeDevice != null && (controllerType = routeDevice.getControllerType()) != null) {
            return controllerType;
        }
        ControllerType controllerType2 = this.routingControllerType;
        return controllerType2 == null ? this.controllerType : controllerType2;
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
        return true;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.BluetoothRoute
    public final RouteDevice getRouteDevice() {
        return this.routeDevice;
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
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name);
        CharSequence charSequence = this.description;
        int hashCode = (this.icon.hashCode() + ((m + (charSequence == null ? 0 : charSequence.hashCode())) * 31)) * 31;
        Painter painter = this.badge;
        return Boolean.hashCode(this.cancelable) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((this.state.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.volumeMax, ReorderTile$$ExternalSyntheticOutline0.m(this.volume, (hashCode + (painter != null ? painter.hashCode() : 0)) * 31, 31), 31)) * 31, 31, this.selectable), 31, this.transferable);
    }

    public final String toString() {
        return toLogText();
    }

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException
        */
    public MusicShareDevice(java.lang.String r15, java.lang.String r16, java.lang.CharSequence r17, androidx.compose.ui.graphics.painter.Painter r18, androidx.compose.ui.graphics.painter.Painter r19, int r20, int r21, com.android.systemui.media.mediaoutput.entity.State r22, boolean r23, boolean r24, boolean r25, int r26, kotlin.jvm.internal.DefaultConstructorMarker r27) {
        /*
            r14 = this;
            r0 = r26
            r1 = r0 & 4
            if (r1 == 0) goto L9
            r1 = 0
            r5 = r1
            goto Lb
        L9:
            r5 = r17
        Lb:
            r1 = r0 & 16
            if (r1 == 0) goto L24
            com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter$Companion r1 = com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter.Companion
            com.android.systemui.media.mediaoutput.icons.Icons$Badge r2 = com.android.systemui.media.mediaoutput.icons.Icons.Badge.INSTANCE
            kotlin.Lazy r2 = com.android.systemui.media.mediaoutput.icons.badge.MusicShareKt.MusicShare$delegate
            java.lang.Object r2 = r2.getValue()
            androidx.compose.ui.graphics.vector.ImageVector r2 = (androidx.compose.ui.graphics.vector.ImageVector) r2
            r1.getClass()
            com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter r1 = com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter.Companion.toConverter(r2)
            r7 = r1
            goto L26
        L24:
            r7 = r19
        L26:
            r1 = r0 & 32
            r2 = 0
            if (r1 == 0) goto L2d
            r8 = r2
            goto L2f
        L2d:
            r8 = r20
        L2f:
            r1 = r0 & 64
            if (r1 == 0) goto L37
            r1 = 150(0x96, float:2.1E-43)
            r9 = r1
            goto L39
        L37:
            r9 = r21
        L39:
            r1 = r0 & 256(0x100, float:3.59E-43)
            if (r1 == 0) goto L3f
            r11 = r2
            goto L41
        L3f:
            r11 = r23
        L41:
            r1 = r0 & 512(0x200, float:7.17E-43)
            r2 = 1
            if (r1 == 0) goto L48
            r12 = r2
            goto L4a
        L48:
            r12 = r24
        L4a:
            r0 = r0 & 1024(0x400, float:1.435E-42)
            if (r0 == 0) goto L58
            r13 = r2
            r3 = r15
            r4 = r16
            r6 = r18
            r10 = r22
            r2 = r14
            goto L62
        L58:
            r13 = r25
            r2 = r14
            r3 = r15
            r4 = r16
            r6 = r18
            r10 = r22
        L62:
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.entity.MusicShareDevice.<init>(java.lang.String, java.lang.String, java.lang.CharSequence, androidx.compose.ui.graphics.painter.Painter, androidx.compose.ui.graphics.painter.Painter, int, int, com.android.systemui.media.mediaoutput.entity.State, boolean, boolean, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
