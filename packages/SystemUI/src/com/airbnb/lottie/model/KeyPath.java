package com.airbnb.lottie.model;

import androidx.compose.animation.BoundsAnimationElement$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceGroupAdapter$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    /* JADX WARN: Removed duplicated region for block: B:36:0x008e A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean fullyResolvesTo(int i, String str) {
        if (i < this.keys.size()) {
            boolean z = i == this.keys.size() - 1;
            String str2 = (String) this.keys.get(i);
            if (!str2.equals("**")) {
                boolean z2 = str2.equals(str) || str2.equals("*");
                if ((z || (i == this.keys.size() - 2 && ((String) PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, this.keys)).equals("**"))) && z2) {
                    return true;
                }
            } else {
                if (z || !((String) this.keys.get(i + 1)).equals(str)) {
                    if (!z) {
                        int i2 = i + 1;
                        if (i2 >= this.keys.size() - 1) {
                            return ((String) this.keys.get(i2)).equals(str);
                        }
                    }
                    return true;
                }
                if (i == this.keys.size() - 2 || (i == this.keys.size() - 3 && ((String) PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, this.keys)).equals("**"))) {
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = this.keys.hashCode() * 31;
        KeyPathElement keyPathElement = this.resolvedElement;
        return iHashCode + (keyPathElement != null ? keyPathElement.hashCode() : 0);
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
