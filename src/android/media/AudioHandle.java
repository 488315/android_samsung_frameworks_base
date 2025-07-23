package android.media;

/* loaded from: classes2.dex */
class AudioHandle {
    private final int mId;

    AudioHandle(int i) {
        this.mId = i;
    }

    int id() {
        return this.mId;
    }

    public boolean equals(Object obj) {
        return obj != null && (obj instanceof AudioHandle) && this.mId == ((AudioHandle) obj).id();
    }

    public int hashCode() {
        return this.mId;
    }

    public String toString() {
        return Integer.toString(this.mId);
    }
}
