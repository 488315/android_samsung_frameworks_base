package com.android.systemui.audio.soundcraft.model.phone;

import kotlin.jvm.internal.Intrinsics;

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
