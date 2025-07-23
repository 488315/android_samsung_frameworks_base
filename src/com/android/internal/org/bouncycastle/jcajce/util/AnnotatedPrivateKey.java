package com.android.internal.org.bouncycastle.jcajce.util;

import java.security.PrivateKey;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class AnnotatedPrivateKey implements PrivateKey {
    public static final String LABEL = "label";
    private final Map<String, Object> annotations;
    private final PrivateKey key;

    AnnotatedPrivateKey(PrivateKey privateKey, String str) {
        this.key = privateKey;
        this.annotations = Collections.singletonMap("label", str);
    }

    AnnotatedPrivateKey(PrivateKey privateKey, Map<String, Object> map) {
        this.key = privateKey;
        this.annotations = map;
    }

    public PrivateKey getKey() {
        return this.key;
    }

    public Map<String, Object> getAnnotations() {
        return this.annotations;
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return this.key.getAlgorithm();
    }

    public Object getAnnotation(String str) {
        return this.annotations.get(str);
    }

    public AnnotatedPrivateKey addAnnotation(String str, Object obj) {
        HashMap hashMap = new HashMap(this.annotations);
        hashMap.put(str, obj);
        return new AnnotatedPrivateKey(this.key, (Map<String, Object>) Collections.unmodifiableMap(hashMap));
    }

    public AnnotatedPrivateKey removeAnnotation(String str) {
        HashMap hashMap = new HashMap(this.annotations);
        hashMap.remove(str);
        return new AnnotatedPrivateKey(this.key, (Map<String, Object>) Collections.unmodifiableMap(hashMap));
    }

    @Override // java.security.Key
    public String getFormat() {
        return this.key.getFormat();
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        return this.key.getEncoded();
    }

    public int hashCode() {
        return this.key.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof AnnotatedPrivateKey) {
            return this.key.equals(((AnnotatedPrivateKey) obj).key);
        }
        return this.key.equals(obj);
    }

    public String toString() {
        if (this.annotations.containsKey("label")) {
            return this.annotations.get("label").toString();
        }
        return this.key.toString();
    }
}
