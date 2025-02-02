package com.airbnb.lottie.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public final class KeyPath {
    public static final KeyPath COMPOSITION = new KeyPath("COMPOSITION");
    private final List<String> keys;
    private KeyPathElement resolvedElement;

    public KeyPath(String... strArr) {
        this.keys = Arrays.asList(strArr);
    }

    public final KeyPath addKey(String str) {
        KeyPath keyPath = new KeyPath(this);
        keyPath.keys.add(str);
        return keyPath;
    }

    public final boolean fullyResolvesTo(int i, String str) {
        List<String> list = this.keys;
        if (i >= list.size()) {
            return false;
        }
        boolean z = i == list.size() - 1;
        String str2 = list.get(i);
        if (!str2.equals("**")) {
            return (z || (i == list.size() + (-2) && list.get(list.size() + (-1)).equals("**"))) && (str2.equals(str) || str2.equals("*"));
        }
        if (!z && list.get(i + 1).equals(str)) {
            return i == list.size() + (-2) || (i == list.size() + (-3) && list.get(list.size() + (-1)).equals("**"));
        }
        if (z) {
            return true;
        }
        int i2 = i + 1;
        if (i2 < list.size() - 1) {
            return false;
        }
        return list.get(i2).equals(str);
    }

    public final KeyPathElement getResolvedElement() {
        return this.resolvedElement;
    }

    public final int incrementDepthBy(int i, String str) {
        if ("__container".equals(str)) {
            return 0;
        }
        List<String> list = this.keys;
        if (list.get(i).equals("**")) {
            return (i != list.size() - 1 && list.get(i + 1).equals(str)) ? 2 : 0;
        }
        return 1;
    }

    public final boolean matches(int i, String str) {
        if ("__container".equals(str)) {
            return true;
        }
        List<String> list = this.keys;
        if (i >= list.size()) {
            return false;
        }
        return list.get(i).equals(str) || list.get(i).equals("**") || list.get(i).equals("*");
    }

    public final boolean propagateToChildren(int i, String str) {
        if ("__container".equals(str)) {
            return true;
        }
        List<String> list = this.keys;
        return i < list.size() - 1 || list.get(i).equals("**");
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
