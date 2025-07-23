package android.media;

import android.security.keystore.KeyProperties;
import com.samsung.android.wallpaperbackup.BnRConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public class AudioPort {
    public static final int ROLE_NONE = 0;
    public static final int ROLE_SINK = 2;
    public static final int ROLE_SOURCE = 1;
    private static final String TAG = "AudioPort";
    public static final int TYPE_DEVICE = 1;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_SESSION = 3;
    public static final int TYPE_SUBMIX = 2;
    private AudioPortConfig mActiveConfig;
    private final int[] mChannelIndexMasks;
    private final int[] mChannelMasks;
    private final List<AudioDescriptor> mDescriptors;
    private final int[] mFormats;
    private final AudioGain[] mGains;
    AudioHandle mHandle;
    private final String mName;
    private final List<AudioProfile> mProfiles;
    protected final int mRole;
    private final int[] mSamplingRates;

    AudioPort(AudioHandle audioHandle, int i, String str, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, AudioGain[] audioGainArr) {
        this.mHandle = audioHandle;
        this.mRole = i;
        this.mName = str;
        this.mSamplingRates = iArr;
        this.mChannelMasks = iArr2;
        this.mChannelIndexMasks = iArr3;
        this.mFormats = iArr4;
        this.mGains = audioGainArr;
        this.mProfiles = new ArrayList();
        if (iArr4 != null) {
            for (int i2 : iArr4) {
                this.mProfiles.add(new AudioProfile(i2, iArr, iArr2, iArr3, 0));
            }
        }
        this.mDescriptors = new ArrayList();
    }

    AudioPort(AudioHandle audioHandle, int i, String str, List<AudioProfile> list, AudioGain[] audioGainArr, List<AudioDescriptor> list2) {
        this.mHandle = audioHandle;
        this.mRole = i;
        this.mName = str;
        this.mProfiles = list;
        this.mDescriptors = list2;
        this.mGains = audioGainArr;
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        HashSet hashSet3 = new HashSet();
        HashSet hashSet4 = new HashSet();
        for (AudioProfile audioProfile : list) {
            hashSet.add(Integer.valueOf(audioProfile.getFormat()));
            hashSet2.addAll((Collection) Arrays.stream(audioProfile.getSampleRates()).boxed().collect(Collectors.toList()));
            hashSet3.addAll((Collection) Arrays.stream(audioProfile.getChannelMasks()).boxed().collect(Collectors.toList()));
            hashSet4.addAll((Collection) Arrays.stream(audioProfile.getChannelIndexMasks()).boxed().collect(Collectors.toList()));
        }
        this.mSamplingRates = hashSet2.stream().mapToInt(new AudioPort$$ExternalSyntheticLambda0()).toArray();
        this.mChannelMasks = hashSet3.stream().mapToInt(new AudioPort$$ExternalSyntheticLambda0()).toArray();
        this.mChannelIndexMasks = hashSet4.stream().mapToInt(new AudioPort$$ExternalSyntheticLambda0()).toArray();
        this.mFormats = hashSet.stream().mapToInt(new AudioPort$$ExternalSyntheticLambda0()).toArray();
    }

    AudioHandle handle() {
        return this.mHandle;
    }

    public int id() {
        return this.mHandle.id();
    }

    public int role() {
        return this.mRole;
    }

    public String name() {
        return this.mName;
    }

    public int[] samplingRates() {
        return this.mSamplingRates;
    }

    public int[] channelMasks() {
        return this.mChannelMasks;
    }

    public int[] channelIndexMasks() {
        return this.mChannelIndexMasks;
    }

    public int[] formats() {
        return this.mFormats;
    }

    public List<AudioProfile> profiles() {
        return this.mProfiles;
    }

    public List<AudioDescriptor> audioDescriptors() {
        return this.mDescriptors;
    }

    public AudioGain[] gains() {
        return this.mGains;
    }

    AudioGain gain(int i) {
        if (i < 0) {
            return null;
        }
        AudioGain[] audioGainArr = this.mGains;
        if (i >= audioGainArr.length) {
            return null;
        }
        return audioGainArr[i];
    }

    public AudioPortConfig buildConfig(int i, int i2, int i3, AudioGainConfig audioGainConfig) {
        return new AudioPortConfig(this, i, i2, i3, audioGainConfig);
    }

    public AudioPortConfig activeConfig() {
        return this.mActiveConfig;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof AudioPort)) {
            return false;
        }
        return this.mHandle.equals(((AudioPort) obj).handle());
    }

    public int hashCode() {
        return this.mHandle.hashCode();
    }

    public String toString() {
        String num = Integer.toString(this.mRole);
        int i = this.mRole;
        if (i == 0) {
            num = KeyProperties.DIGEST_NONE;
        } else if (i == 1) {
            num = BnRConstants.SOURCE_KEY;
        } else if (i == 2) {
            num = "SINK";
        }
        return "{mHandle: " + this.mHandle + ", mRole: " + num + "}";
    }
}
