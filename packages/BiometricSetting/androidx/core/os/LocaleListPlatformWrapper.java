package androidx.core.os;

import android.os.LocaleList;

/* loaded from: classes.dex */
final class LocaleListPlatformWrapper implements LocaleListInterface {
    private final LocaleList mLocaleList;

    LocaleListPlatformWrapper(Object obj) {
        this.mLocaleList = (LocaleList) obj;
    }

    public final boolean equals(Object obj) {
        return this.mLocaleList.equals(((LocaleListInterface) obj).getLocaleList());
    }

    @Override // androidx.core.os.LocaleListInterface
    public final Object getLocaleList() {
        return this.mLocaleList;
    }

    public final int hashCode() {
        return this.mLocaleList.hashCode();
    }

    @Override // androidx.core.os.LocaleListInterface
    public final boolean isEmpty() {
        return this.mLocaleList.isEmpty();
    }

    public final String toString() {
        return this.mLocaleList.toString();
    }
}
