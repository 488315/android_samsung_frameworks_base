package android.audio.policy.configuration.V7_0;

import android.app.slice.Slice;

/* loaded from: classes.dex */
public enum Role {
    sink("sink"),
    source(Slice.SUBTYPE_SOURCE);

    private final String rawName;

    Role(String str) {
        this.rawName = str;
    }

    public String getRawName() {
        return this.rawName;
    }

    static Role fromString(String str) {
        for (Role role : values()) {
            if (role.getRawName().equals(str)) {
                return role;
            }
        }
        throw new IllegalArgumentException(str);
    }
}
