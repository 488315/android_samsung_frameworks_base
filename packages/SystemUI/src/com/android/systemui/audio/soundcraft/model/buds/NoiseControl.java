package com.android.systemui.audio.soundcraft.model.buds;

import com.google.gson.annotations.SerializedName;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class NoiseControl {

    @SerializedName("name")
    private final String name;

    @SerializedName("state")
    private boolean state;

    public NoiseControl(String str, boolean z) {
        this.name = str;
        this.state = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NoiseControl)) {
            return false;
        }
        NoiseControl noiseControl = (NoiseControl) obj;
        return Intrinsics.areEqual(this.name, noiseControl.name) && this.state == noiseControl.state;
    }

    public final String getName() {
        return this.name;
    }

    public final boolean getState() {
        return this.state;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.state) + (this.name.hashCode() * 31);
    }

    public final String toString() {
        return "NoiseControl(name=" + this.name + ", state=" + this.state + ")";
    }
}
