package com.android.systemui.audio.soundcraft.model.phone;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class Dolby {
    public final String name;
    public boolean state;

    public Dolby(String str, boolean z) {
        this.name = str;
        this.state = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Dolby)) {
            return false;
        }
        Dolby dolby = (Dolby) obj;
        return Intrinsics.areEqual(this.name, dolby.name) && this.state == dolby.state;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.state) + (this.name.hashCode() * 31);
    }

    public final String toString() {
        return "Dolby(name=" + this.name + ", state=" + this.state + ")";
    }
}
