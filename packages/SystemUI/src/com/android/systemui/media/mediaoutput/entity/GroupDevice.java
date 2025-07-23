package com.android.systemui.media.mediaoutput.entity;

import android.content.Intent;
import android.media.RoutingSessionInfo;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.painter.Painter;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GroupDevice implements AudioDevice, DeviceAction {
    public final Painter badge;
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

    public GroupDevice(String str, String str2, String str3, Painter painter, Painter painter2, int i, int i2, State state, List<? extends AudioDevice> list) {
        this.id = str;
        this.name = str2;
        this.description = str3;
        this.icon = painter;
        this.badge = painter2;
        this.volume = i;
        this.volumeMax = i2;
        this.state = state;
        this.selectedDevices = list;
        this.controllerType = ControllerType.None;
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final AudioDevice clone() {
        GroupDevice groupDevice = new GroupDevice(this.id, this.name, this.description, this.icon, this.badge, this.volume, this.volumeMax, this.state, this.selectedDevices);
        groupDevice.controllerType = this.controllerType;
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
        return CollectionsKt___CollectionsKt.plus((Iterable) Arrays.asList(new Pair("selectedDevices", this.selectedDevices), new Pair("volumeMax", Integer.valueOf(this.volumeMax))), (Collection) super.getAttributes());
    }

    @Override // com.android.systemui.media.mediaoutput.entity.AudioDevice
    public final Painter getBadge() {
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

    @Override // com.android.systemui.media.mediaoutput.entity.DeviceAction
    public final Intent getTargetIntent() {
        List list = this.selectedDevices;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof DeviceAction) {
                arrayList.add(obj);
            }
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList.get(i);
            i++;
            Intent targetIntent = ((DeviceAction) obj2).getTargetIntent();
            if (targetIntent != null) {
                return targetIntent;
            }
        }
        return null;
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
        Painter painter = this.badge;
        return this.selectedDevices.hashCode() + ((this.state.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.volumeMax, ReorderTile$$ExternalSyntheticOutline0.m(this.volume, (hashCode + (painter != null ? painter.hashCode() : 0)) * 31, 31), 31)) * 31);
    }

    public final String toString() {
        return toLogText();
    }

    public /* synthetic */ GroupDevice(String str, String str2, String str3, Painter painter, Painter painter2, int i, int i2, State state, List list, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i3 & 4) != 0 ? null : str3, painter, (i3 & 16) != 0 ? null : painter2, i, i2, (i3 & 128) != 0 ? State.SELECTED : state, list);
    }
}
