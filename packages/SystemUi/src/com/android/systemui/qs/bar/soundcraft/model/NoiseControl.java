package com.android.systemui.qs.bar.soundcraft.model;

import com.google.gson.annotations.SerializedName;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = this.name.hashCode() * 31;
        boolean z = this.state;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode + i;
    }

    public final String toString() {
        return "NoiseControl(name=" + this.name + ", state=" + this.state + ")";
    }
}
