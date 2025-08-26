package com.android.settingslib.notification.modes;

import android.graphics.drawable.Drawable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ZenIcon extends Record {
    public final Drawable drawable;
    public final Key key;

    public final class Key extends Record {
        public final int resId;
        public final String resPackage;

        public Key(String str, int i) {
            if (!(i != 0)) {
                throw new IllegalArgumentException("Resource id must be valid");
            }
            this.resPackage = str;
            this.resId = i;
        }

        public static Key forSystemResource(int i) {
            return new Key(null, i);
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            if (!(obj instanceof Key)) {
                return false;
            }
            Key key = (Key) obj;
            return this.resId == key.resId && Objects.equals(this.resPackage, key.resPackage);
        }

        @Override // java.lang.Record
        public final int hashCode() {
            int i = this.resId;
            return Objects.hashCode(this.resPackage) + (i * 31);
        }

        @Override // java.lang.Record
        public final String toString() {
            Object[] objArr = {this.resPackage, Integer.valueOf(this.resId)};
            String[] strArrSplit = "resPackage;resId".length() == 0 ? new String[0] : "resPackage;resId".split(";");
            StringBuilder sb = new StringBuilder();
            sb.append(Key.class.getSimpleName());
            sb.append("[");
            for (int i = 0; i < strArrSplit.length; i++) {
                sb.append(strArrSplit[i]);
                sb.append("=");
                sb.append(objArr[i]);
                if (i != strArrSplit.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    public ZenIcon(Key key, Drawable drawable) {
        this.key = key;
        this.drawable = drawable;
    }

    @Override // java.lang.Record
    public final boolean equals(Object obj) {
        if (!(obj instanceof ZenIcon)) {
            return false;
        }
        ZenIcon zenIcon = (ZenIcon) obj;
        return Objects.equals(this.key, zenIcon.key) && Objects.equals(this.drawable, zenIcon.drawable);
    }

    @Override // java.lang.Record
    public final int hashCode() {
        Key key = this.key;
        Drawable drawable = this.drawable;
        return Objects.hashCode(drawable) + (Objects.hashCode(key) * 31);
    }

    @Override // java.lang.Record
    public final String toString() {
        Object[] objArr = {this.key, this.drawable};
        String[] strArrSplit = "key;drawable".length() == 0 ? new String[0] : "key;drawable".split(";");
        StringBuilder sb = new StringBuilder("ZenIcon[");
        for (int i = 0; i < strArrSplit.length; i++) {
            sb.append(strArrSplit[i]);
            sb.append("=");
            sb.append(objArr[i]);
            if (i != strArrSplit.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
