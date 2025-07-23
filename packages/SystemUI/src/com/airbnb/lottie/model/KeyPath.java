package com.airbnb.lottie.model;

import androidx.compose.animation.BoundsAnimationElement$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class KeyPath {
    public static final KeyPath COMPOSITION = new KeyPath("COMPOSITION");
    public final List keys;
    public KeyPathElement resolvedElement;

    public KeyPath(String... strArr) {
        this.keys = Arrays.asList(strArr);
    }

    public final KeyPath addKey(String str) {
        KeyPath keyPath = new KeyPath(this);
        keyPath.keys.add(str);
        return keyPath;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            KeyPath keyPath = (KeyPath) obj;
            if (!this.keys.equals(keyPath.keys)) {
                return false;
            }
            KeyPathElement keyPathElement = this.resolvedElement;
            if (keyPathElement != null) {
                return keyPathElement.equals(keyPath.resolvedElement);
            }
            if (keyPath.resolvedElement == null) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x008e A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean fullyResolvesTo(int r7, java.lang.String r8) {
        /*
            r6 = this;
            java.util.List r0 = r6.keys
            int r0 = r0.size()
            r1 = 0
            if (r7 < r0) goto Lb
            goto L99
        Lb:
            java.util.List r0 = r6.keys
            int r0 = r0.size()
            r2 = 1
            int r0 = r0 - r2
            if (r7 != r0) goto L17
            r0 = r2
            goto L18
        L17:
            r0 = r1
        L18:
            java.util.List r3 = r6.keys
            java.lang.Object r3 = r3.get(r7)
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r4 = "**"
            boolean r5 = r3.equals(r4)
            if (r5 != 0) goto L57
            boolean r8 = r3.equals(r8)
            if (r8 != 0) goto L39
            java.lang.String r8 = "*"
            boolean r8 = r3.equals(r8)
            if (r8 == 0) goto L37
            goto L39
        L37:
            r8 = r1
            goto L3a
        L39:
            r8 = r2
        L3a:
            if (r0 != 0) goto L54
            java.util.List r0 = r6.keys
            int r0 = r0.size()
            int r0 = r0 + (-2)
            if (r7 != r0) goto L99
            java.util.List r6 = r6.keys
            java.lang.Object r6 = androidx.preference.PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(r2, r6)
            java.lang.String r6 = (java.lang.String) r6
            boolean r6 = r6.equals(r4)
            if (r6 == 0) goto L99
        L54:
            if (r8 == 0) goto L99
            goto L8e
        L57:
            if (r0 != 0) goto L8c
            java.util.List r3 = r6.keys
            int r5 = r7 + 1
            java.lang.Object r3 = r3.get(r5)
            java.lang.String r3 = (java.lang.String) r3
            boolean r3 = r3.equals(r8)
            if (r3 == 0) goto L8c
            java.util.List r8 = r6.keys
            int r8 = r8.size()
            int r8 = r8 + (-2)
            if (r7 == r8) goto L8e
            java.util.List r8 = r6.keys
            int r8 = r8.size()
            int r8 = r8 + (-3)
            if (r7 != r8) goto L99
            java.util.List r6 = r6.keys
            java.lang.Object r6 = androidx.preference.PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(r2, r6)
            java.lang.String r6 = (java.lang.String) r6
            boolean r6 = r6.equals(r4)
            if (r6 == 0) goto L99
            goto L8e
        L8c:
            if (r0 == 0) goto L8f
        L8e:
            return r2
        L8f:
            int r7 = r7 + r2
            java.util.List r0 = r6.keys
            int r0 = r0.size()
            int r0 = r0 - r2
            if (r7 >= r0) goto L9a
        L99:
            return r1
        L9a:
            java.util.List r6 = r6.keys
            java.lang.Object r6 = r6.get(r7)
            java.lang.String r6 = (java.lang.String) r6
            boolean r6 = r6.equals(r8)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.model.KeyPath.fullyResolvesTo(int, java.lang.String):boolean");
    }

    public final int hashCode() {
        int hashCode = this.keys.hashCode() * 31;
        KeyPathElement keyPathElement = this.resolvedElement;
        return hashCode + (keyPathElement != null ? keyPathElement.hashCode() : 0);
    }

    public final int incrementDepthBy(int i, String str) {
        if ("__container".equals(str)) {
            return 0;
        }
        if (((String) this.keys.get(i)).equals("**")) {
            return (i != this.keys.size() - 1 && ((String) this.keys.get(i + 1)).equals(str)) ? 2 : 0;
        }
        return 1;
    }

    public final boolean matches(int i, String str) {
        if ("__container".equals(str)) {
            return true;
        }
        if (i >= this.keys.size()) {
            return false;
        }
        return ((String) this.keys.get(i)).equals(str) || ((String) this.keys.get(i)).equals("**") || ((String) this.keys.get(i)).equals("*");
    }

    public final boolean propagateToChildren(int i, String str) {
        return "__container".equals(str) || i < this.keys.size() - 1 || ((String) this.keys.get(i)).equals("**");
    }

    public final KeyPath resolve(KeyPathElement keyPathElement) {
        KeyPath keyPath = new KeyPath(this);
        keyPath.resolvedElement = keyPathElement;
        return keyPath;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("KeyPath{keys=");
        sb.append(this.keys);
        sb.append(",resolved=");
        return BoundsAnimationElement$$ExternalSyntheticOutline0.m(sb, this.resolvedElement != null, '}');
    }

    private KeyPath(KeyPath keyPath) {
        this.keys = new ArrayList(keyPath.keys);
        this.resolvedElement = keyPath.resolvedElement;
    }
}
