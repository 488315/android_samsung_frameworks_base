package androidx.compose.runtime;

/* loaded from: classes.dex */
public final class KeyInfo {
    public final int key;
    public final int location;
    public final int nodes;
    public final Object objectKey;

    public KeyInfo(int i, Object obj, int i2, int i3, int i4) {
        this.key = i;
        this.objectKey = obj;
        this.location = i2;
        this.nodes = i3;
    }
}
