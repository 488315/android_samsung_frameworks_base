package android.media;

/* loaded from: classes2.dex */
public class AudioPatch {
    private final AudioHandle mHandle;
    private final AudioPortConfig[] mSinks;
    private final AudioPortConfig[] mSources;

    AudioPatch(AudioHandle audioHandle, AudioPortConfig[] audioPortConfigArr, AudioPortConfig[] audioPortConfigArr2) {
        this.mHandle = audioHandle;
        this.mSources = audioPortConfigArr;
        this.mSinks = audioPortConfigArr2;
    }

    public AudioPortConfig[] sources() {
        return this.mSources;
    }

    public AudioPortConfig[] sinks() {
        return this.mSinks;
    }

    public int id() {
        return this.mHandle.id();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("mHandle: ");
        sb.append(this.mHandle.toString());
        sb.append(" mSources: {");
        for (AudioPortConfig audioPortConfig : this.mSources) {
            sb.append(audioPortConfig.toString());
            sb.append(", ");
        }
        sb.append("} mSinks: {");
        for (AudioPortConfig audioPortConfig2 : this.mSinks) {
            sb.append(audioPortConfig2.toString());
            sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }
}
