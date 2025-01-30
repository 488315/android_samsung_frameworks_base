package com.airbnb.lottie.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyPath {
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
        if (obj == null || KeyPath.class != obj.getClass()) {
            return false;
        }
        KeyPath keyPath = (KeyPath) obj;
        if (!this.keys.equals(keyPath.keys)) {
            return false;
        }
        KeyPathElement keyPathElement = this.resolvedElement;
        return keyPathElement != null ? keyPathElement.equals(keyPath.resolvedElement) : keyPath.resolvedElement == null;
    }

    public final boolean fullyResolvesTo(int i, String str) {
        List list = this.keys;
        if (i >= list.size()) {
            return false;
        }
        boolean z = i == list.size() - 1;
        String str2 = (String) list.get(i);
        if (!str2.equals("**")) {
            return (z || (i == list.size() + (-2) && ((String) list.get(list.size() + (-1))).equals("**"))) && (str2.equals(str) || str2.equals("*"));
        }
        if (!z && ((String) list.get(i + 1)).equals(str)) {
            return i == list.size() + (-2) || (i == list.size() + (-3) && ((String) list.get(list.size() + (-1))).equals("**"));
        }
        if (z) {
            return true;
        }
        int i2 = i + 1;
        if (i2 < list.size() - 1) {
            return false;
        }
        return ((String) list.get(i2)).equals(str);
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
        List list = this.keys;
        if (((String) list.get(i)).equals("**")) {
            return (i != list.size() - 1 && ((String) list.get(i + 1)).equals(str)) ? 2 : 0;
        }
        return 1;
    }

    public final boolean matches(int i, String str) {
        if ("__container".equals(str)) {
            return true;
        }
        List list = this.keys;
        if (i >= list.size()) {
            return false;
        }
        return ((String) list.get(i)).equals(str) || ((String) list.get(i)).equals("**") || ((String) list.get(i)).equals("*");
    }

    public final boolean propagateToChildren(int i, String str) {
        if ("__container".equals(str)) {
            return true;
        }
        List list = this.keys;
        return i < list.size() - 1 || ((String) list.get(i)).equals("**");
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
        sb.append(this.resolvedElement != null);
        sb.append('}');
        return sb.toString();
    }

    private KeyPath(KeyPath keyPath) {
        this.keys = new ArrayList(keyPath.keys);
        this.resolvedElement = keyPath.resolvedElement;
    }
}
