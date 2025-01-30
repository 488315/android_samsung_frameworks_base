package android.media;

/* loaded from: classes2.dex */
class AudioHandle {
    private final int mId;

    AudioHandle(int id) {
        this.mId = id;
    }

    /* renamed from: id */
    int m48id() {
        return this.mId;
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof AudioHandle)) {
            return false;
        }
        AudioHandle ah = (AudioHandle) o;
        return this.mId == ah.m48id();
    }

    public int hashCode() {
        return this.mId;
    }

    public String toString() {
        return Integer.toString(this.mId);
    }
}
