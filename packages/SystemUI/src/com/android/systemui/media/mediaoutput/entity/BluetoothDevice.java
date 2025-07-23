package com.android.systemui.media.mediaoutput.entity;

import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.painter.Painter;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import com.samsung.android.knox.custom.CustomDeviceManager;
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
public final class BluetoothDevice implements AudioDevice, BluetoothRoute {
    public static final Companion Companion = new Companion(null);
    public List activeDevices;
    public AudioDeviceInfo audioDeviceInfo;
    public final Painter badge;
    public CachedBluetoothDevice cachedBluetoothDevice;
    public final boolean cancelable;
    public final ControllerType controllerType;
    public final CharSequence description;
    public final boolean force;
    public final Painter icon;
    public final String id;
    public final String name;
    public RouteDevice routeDevice;
    public ControllerType routingControllerType;
    public final boolean selectable;
    public final State state;
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

    public BluetoothDevice(String str, String str2, CharSequence charSequence, Painter painter, Painter painter2, int i, int i2, State state, boolean z, boolean z2, boolean z3) {
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
        this.force = z3;
        this.controllerType = ControllerType.Bluetooth;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v2, types: [androidx.compose.ui.graphics.painter.Painter] */
    public static BluetoothDevice copy$default(BluetoothDevice bluetoothDevice, CharSequence charSequence, ImageVectorConverterPainter imageVectorConverterPainter, int i, State state, boolean z, int i2) {
        String str = bluetoothDevice.id;
        String str2 = bluetoothDevice.name;
        if ((i2 & 4) != 0) {
            charSequence = bluetoothDevice.description;
        }
        CharSequence charSequence2 = charSequence;
        Painter painter = bluetoothDevice.icon;
        ImageVectorConverterPainter imageVectorConverterPainter2 = imageVectorConverterPainter;
        if ((i2 & 16) != 0) {
            imageVectorConverterPainter2 = bluetoothDevice.badge;
        }
        ImageVectorConverterPainter imageVectorConverterPainter3 = imageVectorConverterPainter2;
        int i3 = (i2 & 32) != 0 ? bluetoothDevice.volume : i;
        int i4 = bluetoothDevice.volumeMax;
        State state2 = (i2 & 128) != 0 ? bluetoothDevice.state : state;
        boolean z2 = (i2 & 256) != 0 ? bluetoothDevice.selectable : z;
        boolean z3 = bluetoothDevice.cancelable;
        boolean z4 = bluetoothDevice.force;
        bluetoothDevice.getClass();
        return new BluetoothDevice(str, str2, charSequence2, painter, imageVectorConverterPainter3, i3, i4, state2, z2, z3, z4);
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final AudioDevice clone() {
        BluetoothDevice copy$default = copy$default(this, null, null, 0, null, false, CustomDeviceManager.SETTINGS_ALL_PREVIOUS);
        copy$default.deepCopy(this);
        return copy$default;
    }

    public final void deepCopy(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            bluetoothDevice = null;
        }
        if (bluetoothDevice != null) {
            CachedBluetoothDevice cachedBluetoothDevice = bluetoothDevice.cachedBluetoothDevice;
            this.cachedBluetoothDevice = cachedBluetoothDevice != null ? cachedBluetoothDevice : null;
            this.audioDeviceInfo = bluetoothDevice.audioDeviceInfo;
            this.activeDevices = bluetoothDevice.activeDevices;
            this.routeDevice = bluetoothDevice.routeDevice;
            this.routingControllerType = bluetoothDevice.routingControllerType;
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BluetoothDevice)) {
            return false;
        }
        BluetoothDevice bluetoothDevice = (BluetoothDevice) obj;
        return Intrinsics.areEqual(this.id, bluetoothDevice.id) && Intrinsics.areEqual(this.name, bluetoothDevice.name) && Intrinsics.areEqual(this.description, bluetoothDevice.description) && Intrinsics.areEqual(this.icon, bluetoothDevice.icon) && Intrinsics.areEqual(this.badge, bluetoothDevice.badge) && this.volume == bluetoothDevice.volume && this.volumeMax == bluetoothDevice.volumeMax && this.state == bluetoothDevice.state && this.selectable == bluetoothDevice.selectable && this.cancelable == bluetoothDevice.cancelable && this.force == bluetoothDevice.force;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice, com.android.systemui.media.mediaoutput.entity.EntityString
    public final List getAttributes() {
        List attributes = super.getAttributes();
        Pair pair = new Pair("audioDeviceInfo", this.audioDeviceInfo);
        Pair pair2 = new Pair("description", this.description);
        CachedBluetoothDevice cachedBluetoothDevice = this.cachedBluetoothDevice;
        if (cachedBluetoothDevice == null) {
            cachedBluetoothDevice = null;
        }
        Pair pair3 = new Pair("isLeConnected", Boolean.valueOf(cachedBluetoothDevice.isConnectedLeAudioDevice()));
        Pair pair4 = new Pair("isNeedEarProtect", Boolean.TRUE);
        Pair pair5 = new Pair("earShockValue", Float.valueOf((AudioManager.semGetEarProtectLimit() - 1) * 10.0f));
        Pair pair6 = new Pair("isSelectable", Boolean.valueOf(this.selectable));
        RouteDevice routeDevice = this.routeDevice;
        return CollectionsKt___CollectionsKt.plus((Iterable) Arrays.asList(pair, pair2, pair3, pair4, pair5, pair6, new Pair("routeDevice", routeDevice != null ? routeDevice.getName() : null), new Pair("routingControllerType", this.routingControllerType)), (Collection) attributes);
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
    public final boolean getForce() {
        return this.force;
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
        return Boolean.hashCode(this.force) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((this.state.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.volumeMax, ReorderTile$$ExternalSyntheticOutline0.m(this.volume, (hashCode + (painter != null ? painter.hashCode() : 0)) * 31, 31), 31)) * 31, 31, this.selectable), 31, this.cancelable);
    }

    public final String toString() {
        return toLogText();
    }

    public /* synthetic */ BluetoothDevice(String str, String str2, CharSequence charSequence, Painter painter, Painter painter2, int i, int i2, State state, boolean z, boolean z2, boolean z3, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i3 & 4) != 0 ? null : charSequence, painter, (i3 & 16) != 0 ? null : painter2, (i3 & 32) != 0 ? 0 : i, (i3 & 64) != 0 ? 150 : i2, (i3 & 128) != 0 ? State.CONNECTED : state, (i3 & 256) != 0 ? false : z, (i3 & 512) != 0 ? false : z2, (i3 & 1024) != 0 ? false : z3);
    }
}
