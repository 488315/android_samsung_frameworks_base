package com.android.systemui.media.mediaoutput.controller.device;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DeviceInfo {
    public final Map details;
    public final int icon;
    public final String key;
    public final String name;
    public final int type;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        int m = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.icon, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.type, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.name.hashCode() * 31, 31, this.key), 31), 31);
        Map map = this.details;
        return m + (map == null ? 0 : map.hashCode());
    }

    public final String toString() {
        return "DeviceInfo(name=" + this.name + ", key=" + this.key + ", type=" + this.type + ", icon=" + this.icon + ", details=" + this.details + ")";
    }
}
