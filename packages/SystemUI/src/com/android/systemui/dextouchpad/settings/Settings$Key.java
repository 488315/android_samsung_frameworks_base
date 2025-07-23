package com.android.systemui.dextouchpad.settings;

import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class Settings$Key implements Comparable {
    public final String mDefValue;
    public final String mName;
    public final Class mType;

    public Settings$Key(Class<Object> cls, String str, String str2) {
        this.mType = cls;
        this.mName = str;
        this.mDefValue = str2;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return this.mName.compareTo(((Settings$Key) obj).mName);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Settings$Key settings$Key = (Settings$Key) obj;
            String str = this.mDefValue;
            boolean equals = str != null ? str.equals(settings$Key.mDefValue) : true;
            if (this.mName.equals(settings$Key.mName) && this.mType.equals(settings$Key.mType) && equals) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(this.mName, this.mType, this.mDefValue);
    }

    public final String toString() {
        return this.mName;
    }
}
