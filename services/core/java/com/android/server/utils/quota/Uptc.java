package com.android.server.utils.quota;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class Uptc {
    public final int mHash;
    public final String packageName;
    public final String tag;
    public final int userId;

    public Uptc(int i, String str, String str2) {
        this.userId = i;
        this.packageName = str;
        this.tag = str2;
        StringBuilder sb = new StringBuilder();
        sb.append((str.hashCode() * 31) + (i * 31));
        sb.append(str2);
        this.mHash = sb.toString() == null ? 0 : str2.hashCode() * 31;
    }

    public static String string(int i, String str, String str2) {
        StringBuilder sb = new StringBuilder("<");
        sb.append(i);
        sb.append(">");
        sb.append(str);
        sb.append(str2 == null ? "" : "::".concat(str2));
        return sb.toString();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Uptc)) {
            return false;
        }
        Uptc uptc = (Uptc) obj;
        return this.userId == uptc.userId
                && Objects.equals(this.packageName, uptc.packageName)
                && Objects.equals(this.tag, uptc.tag);
    }

    public final int hashCode() {
        return this.mHash;
    }

    public final String toString() {
        return string(this.userId, this.packageName, this.tag);
    }
}
