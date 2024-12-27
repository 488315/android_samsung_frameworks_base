package com.android.systemui.media.mediaoutput.entity;

import android.media.RoutingSessionInfo;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
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
public final class GroupDevice implements AudioDevice {
    public final ImageVector badge;
    public ControllerType controllerType;
    public final String description;
    public final Painter icon;
    public final String id;
    public final String name;
    public RoutingSessionInfo routingSessionInfo;
    public final List selectedDevices;
    public final State state;
    public final int volume;
    public final int volumeMax;

    public GroupDevice(String str, String str2, String str3, Painter painter, ImageVector imageVector, int i, int i2, State state, List<? extends AudioDevice> list) {
        this.id = str;
        this.name = str2;
        this.description = str3;
        this.icon = painter;
        this.badge = imageVector;
        this.volume = i;
        this.volumeMax = i2;
        this.state = state;
        this.selectedDevices = list;
        this.controllerType = ControllerType.None;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final AudioDevice clone() {
        GroupDevice groupDevice = new GroupDevice(this.id, this.name, this.description, this.icon, this.badge, this.volume, this.volumeMax, this.state, this.selectedDevices);
        RoutingSessionInfo routingSessionInfo = this.routingSessionInfo;
        if (routingSessionInfo == null) {
            routingSessionInfo = null;
        }
        groupDevice.routingSessionInfo = routingSessionInfo;
        return groupDevice;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GroupDevice)) {
            return false;
        }
        GroupDevice groupDevice = (GroupDevice) obj;
        return Intrinsics.areEqual(this.id, groupDevice.id) && Intrinsics.areEqual(this.name, groupDevice.name) && Intrinsics.areEqual(this.description, groupDevice.description) && Intrinsics.areEqual(this.icon, groupDevice.icon) && Intrinsics.areEqual(this.badge, groupDevice.badge) && this.volume == groupDevice.volume && this.volumeMax == groupDevice.volumeMax && this.state == groupDevice.state && Intrinsics.areEqual(this.selectedDevices, groupDevice.selectedDevices);
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice, com.android.systemui.media.mediaoutput.entity.EntityString
    public final List getAttributes() {
        return CollectionsKt___CollectionsKt.plus((Iterable) CollectionsKt__CollectionsKt.listOf(new Pair("selectedDevices", this.selectedDevices), new Pair("volumeMax", Integer.valueOf(this.volumeMax))), (Collection) super.getAttributes());
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
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.id.hashCode() * 31, 31, this.name);
        String str = this.description;
        int hashCode = (this.icon.hashCode() + ((m + (str == null ? 0 : str.hashCode())) * 31)) * 31;
        ImageVector imageVector = this.badge;
        return this.selectedDevices.hashCode() + ((this.state.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.volumeMax, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.volume, (hashCode + (imageVector != null ? imageVector.hashCode() : 0)) * 31, 31), 31)) * 31);
    }

    public final String toString() {
        return toLogText();
    }

    public /* synthetic */ GroupDevice(String str, String str2, String str3, Painter painter, ImageVector imageVector, int i, int i2, State state, List list, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i3 & 4) != 0 ? null : str3, painter, (i3 & 16) != 0 ? null : imageVector, i, i2, (i3 & 128) != 0 ? State.SELECTED : state, list);
    }
}
