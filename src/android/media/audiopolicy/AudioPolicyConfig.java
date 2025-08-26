package android.media.audiopolicy;

import android.media.audiopolicy.AudioMixingRule;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: classes3.dex */
public class AudioPolicyConfig implements Parcelable {
    public static final Parcelable.Creator<AudioPolicyConfig> CREATOR = new Parcelable.Creator<AudioPolicyConfig>() { // from class: android.media.audiopolicy.AudioPolicyConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPolicyConfig createFromParcel(Parcel parcel) {
            return new AudioPolicyConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPolicyConfig[] newArray(int i) {
            return new AudioPolicyConfig[i];
        }
    };
    private static final String TAG = "AudioPolicyConfig";
    protected int mDuckingPolicy;
    private int mMixCounter;
    protected final ArrayList<AudioMix> mMixes;
    private String mRegistrationId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected AudioPolicyConfig(AudioPolicyConfig audioPolicyConfig) {
        this.mDuckingPolicy = 0;
        this.mRegistrationId = null;
        this.mMixCounter = 0;
        this.mMixes = audioPolicyConfig.mMixes;
    }

    public AudioPolicyConfig(ArrayList<AudioMix> arrayList) {
        this.mDuckingPolicy = 0;
        this.mRegistrationId = null;
        this.mMixCounter = 0;
        this.mMixes = arrayList;
    }

    public void addMix(AudioMix audioMix) throws IllegalArgumentException {
        if (audioMix == null) {
            throw new IllegalArgumentException("Illegal null AudioMix argument");
        }
        this.mMixes.add(audioMix);
    }

    public ArrayList<AudioMix> getMixes() {
        return this.mMixes;
    }

    public int hashCode() {
        return Objects.hash(this.mMixes);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mMixes.size());
        Iterator<AudioMix> it = this.mMixes.iterator();
        while (it.hasNext()) {
            it.next().writeToParcel(parcel, i);
        }
    }

    private AudioPolicyConfig(Parcel parcel) {
        this.mDuckingPolicy = 0;
        this.mRegistrationId = null;
        this.mMixCounter = 0;
        int i = parcel.readInt();
        this.mMixes = new ArrayList<>(i);
        for (int i2 = 0; i2 < i; i2++) {
            this.mMixes.add(AudioMix.CREATOR.createFromParcel(parcel));
        }
    }

    public String toLogFriendlyString() {
        String str;
        String str2 = new String("android.media.audiopolicy.AudioPolicyConfig:\n") + this.mMixes.size() + " AudioMix, reg:" + this.mRegistrationId + ShaderAssembler.NEWLINE;
        Iterator<AudioMix> it = this.mMixes.iterator();
        while (it.hasNext()) {
            AudioMix next = it.next();
            str2 = (((((((str2 + "* route flags=0x" + Integer.toHexString(next.getRouteFlags()) + ShaderAssembler.NEWLINE) + "  rate=" + next.getFormat().getSampleRate() + "Hz\n") + "  encoding=" + next.getFormat().getEncoding() + ShaderAssembler.NEWLINE) + "  channels=0x") + Integer.toHexString(next.getFormat().getChannelMask()).toUpperCase() + ShaderAssembler.NEWLINE) + "  ignore playback capture opt out=" + next.getRule().allowPrivilegedMediaPlaybackCapture() + ShaderAssembler.NEWLINE) + "  allow voice communication capture=" + next.getRule().voiceCommunicationCaptureAllowed() + ShaderAssembler.NEWLINE) + "  specified mix type=" + next.getRule().getTargetMixRole() + ShaderAssembler.NEWLINE;
            Iterator<AudioMixingRule.AudioMixMatchCriterion> it2 = next.getRule().getCriteria().iterator();
            while (it2.hasNext()) {
                AudioMixingRule.AudioMixMatchCriterion next2 = it2.next();
                int i = next2.mRule;
                if (i == 1) {
                    str = (str2 + "  match usage ") + next2.mAttr.usageToString();
                } else if (i == 2) {
                    str = (str2 + "  match capture preset ") + next2.mAttr.getCapturePreset();
                } else if (i == 4) {
                    str = (str2 + "  match UID ") + next2.mIntProp;
                } else if (i == 8) {
                    str = (str2 + "  match userId ") + next2.mIntProp;
                } else if (i == 16) {
                    str = (str2 + " match audio session id") + next2.mIntProp;
                } else if (i == 32772) {
                    str = (str2 + "  exclude UID ") + next2.mIntProp;
                } else if (i == 32776) {
                    str = (str2 + "  exclude userId ") + next2.mIntProp;
                } else if (i != 32784) {
                    switch (i) {
                        case 32769:
                            str = (str2 + "  exclude usage ") + next2.mAttr.usageToString();
                            break;
                        case 32770:
                            str = (str2 + "  exclude capture preset ") + next2.mAttr.getCapturePreset();
                            break;
                        default:
                            str = str2 + "invalid rule!";
                            break;
                    }
                } else {
                    str = (str2 + " exclude audio session id ") + next2.mIntProp;
                }
                str2 = str + ShaderAssembler.NEWLINE;
            }
        }
        return str2;
    }

    public String toCompactLogString() {
        String str = "reg:" + this.mRegistrationId;
        Iterator<AudioMix> it = this.mMixes.iterator();
        int i = 0;
        while (it.hasNext()) {
            AudioMix next = it.next();
            str = str + " Mix:" + i + "-Typ:" + mixTypePrefix(next.getMixType()) + "-Rul:" + next.getRule().getCriteria().size();
            i++;
        }
        return str;
    }

    private static String mixTypePrefix(int i) {
        if (i == 0) {
            return "p";
        }
        if (i == 1) {
            return "r";
        }
        return "#";
    }

    protected void reset() {
        this.mMixCounter = 0;
    }

    protected void setRegistration(String str) {
        String str2 = this.mRegistrationId;
        boolean z = str2 == null || str2.isEmpty();
        boolean z2 = str == null || str.isEmpty();
        if (!z && !z2 && !this.mRegistrationId.equals(str)) {
            Log.e(TAG, "Invalid registration transition from " + this.mRegistrationId + " to " + str);
            return;
        }
        if (str == null) {
            str = "";
        }
        this.mRegistrationId = str;
        Iterator<AudioMix> it = this.mMixes.iterator();
        while (it.hasNext()) {
            setMixRegistration(it.next());
        }
    }

    protected void setMixRegistration(AudioMix audioMix) {
        if (!this.mRegistrationId.isEmpty()) {
            if ((audioMix.getRouteFlags() & 2) == 2) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.mRegistrationId);
                sb.append("mix");
                sb.append(mixTypeId(audioMix.getMixType()));
                sb.append(":");
                int i = this.mMixCounter;
                this.mMixCounter = i + 1;
                sb.append(i);
                audioMix.setRegistration(sb.toString());
                return;
            }
            if ((audioMix.getRouteFlags() & 1) == 1) {
                audioMix.setRegistration(audioMix.mDeviceAddress);
                return;
            }
            return;
        }
        audioMix.setRegistration("");
    }

    protected void add(ArrayList<AudioMix> arrayList) {
        Iterator<AudioMix> it = arrayList.iterator();
        while (it.hasNext()) {
            AudioMix next = it.next();
            if (next.getRegistration() == null || next.getRegistration().isEmpty()) {
                setMixRegistration(next);
            }
            this.mMixes.add(next);
        }
    }

    protected void remove(ArrayList<AudioMix> arrayList) {
        Iterator<AudioMix> it = arrayList.iterator();
        while (it.hasNext()) {
            this.mMixes.remove(it.next());
        }
    }

    public void updateMixingRules(List<Pair<AudioMix, AudioMixingRule>> list) {
        ((List) Objects.requireNonNull(list)).forEach(new Consumer() { // from class: android.media.audiopolicy.AudioPolicyConfig$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$updateMixingRules$0((Pair) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$updateMixingRules$0(Pair pair) {
        updateMixingRule((AudioMix) pair.first, (AudioMixingRule) pair.second);
    }

    private void updateMixingRule(final AudioMix audioMix, final AudioMixingRule audioMixingRule) {
        Stream stream = this.mMixes.stream();
        Objects.requireNonNull(audioMix);
        stream.filter(new Predicate() { // from class: android.media.audiopolicy.AudioPolicyConfig$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return audioMix.equals((AudioMix) obj);
            }
        }).findAny().ifPresent(new Consumer() { // from class: android.media.audiopolicy.AudioPolicyConfig$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AudioMix) obj).setAudioMixingRule(audioMixingRule);
            }
        });
    }

    private static String mixTypeId(int i) {
        if (i == 0) {
            return "p";
        }
        if (i == 1) {
            return "r";
        }
        return "i";
    }

    protected String getRegistration() {
        return this.mRegistrationId;
    }
}
