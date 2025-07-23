package com.android.systemui.media.mediaoutput.controller.device;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DeviceInfo {
    public final Map details;
    public final int icon;
    public final String key;
    public final String name;
    public final int type;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DeviceInfo(String str, String str2, int i, int i2, Map<String, String> map) {
        this.name = str;
        this.key = str2;
        this.type = i;
        this.icon = i2;
        this.details = map;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeviceInfo)) {
            return false;
        }
        DeviceInfo deviceInfo = (DeviceInfo) obj;
        return Intrinsics.areEqual(this.name, deviceInfo.name) && Intrinsics.areEqual(this.key, deviceInfo.key) && this.type == deviceInfo.type && this.icon == deviceInfo.icon && Intrinsics.areEqual(this.details, deviceInfo.details);
    }

    public final int hashCode() {
        int m = ReorderTile$$ExternalSyntheticOutline0.m(this.icon, ReorderTile$$ExternalSyntheticOutline0.m(this.type, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.name.hashCode() * 31, 31, this.key), 31), 31);
        Map map = this.details;
        return m + (map == null ? 0 : map.hashCode());
    }

    public final String toString() {
        return "DeviceInfo(name=" + this.name + ", key=" + this.key + ", type=" + this.type + ", icon=" + this.icon + ", details=" + this.details + ")";
    }
}
