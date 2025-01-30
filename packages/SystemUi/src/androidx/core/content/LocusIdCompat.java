package androidx.core.content;

import android.content.LocusId;
import android.text.TextUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LocusIdCompat {
    public final String mId;
    public final LocusId mWrapped;

    public LocusIdCompat(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("id cannot be empty");
        }
        this.mId = str;
        this.mWrapped = new LocusId(str);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || LocusIdCompat.class != obj.getClass()) {
            return false;
        }
        String str = ((LocusIdCompat) obj).mId;
        String str2 = this.mId;
        return str2 == null ? str == null : str2.equals(str);
    }

    public final int hashCode() {
        String str = this.mId;
        return 31 + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("LocusIdCompat[");
        sb.append(this.mId.length() + "_chars");
        sb.append("]");
        return sb.toString();
    }
}
