package android.media.audiopolicy;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AudioPolicyConfig implements Parcelable {
    public static final Parcelable.Creator<AudioPolicyConfig> CREATOR =
            new Parcelable.Creator<AudioPolicyConfig>() { // from class:
                // android.media.audiopolicy.AudioPolicyConfig.1
                @Override // android.os.Parcelable.Creator
                public AudioPolicyConfig createFromParcel(Parcel p) {
                    return new AudioPolicyConfig(p);
                }

                @Override // android.os.Parcelable.Creator
                public AudioPolicyConfig[] newArray(int size) {
                    return new AudioPolicyConfig[size];
                }
            };
    private static final String TAG = "AudioPolicyConfig";
    protected int mDuckingPolicy;
    private int mMixCounter;
    protected final ArrayList<AudioMix> mMixes;
    private String mRegistrationId;

    protected AudioPolicyConfig(AudioPolicyConfig conf) {
        this.mDuckingPolicy = 0;
        this.mRegistrationId = null;
        this.mMixCounter = 0;
        this.mMixes = conf.mMixes;
    }

    public AudioPolicyConfig(ArrayList<AudioMix> mixes) {
        this.mDuckingPolicy = 0;
        this.mRegistrationId = null;
        this.mMixCounter = 0;
        this.mMixes = mixes;
    }

    public void addMix(AudioMix mix) throws IllegalArgumentException {
        if (mix == null) {
            throw new IllegalArgumentException("Illegal null AudioMix argument");
        }
        this.mMixes.add(mix);
    }

    public ArrayList<AudioMix> getMixes() {
        return this.mMixes;
    }

    public int hashCode() {
        return Objects.hash(this.mMixes);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mMixes.size());
        Iterator<AudioMix> it = this.mMixes.iterator();
        while (it.hasNext()) {
            AudioMix mix = it.next();
            mix.writeToParcel(dest, flags);
        }
    }

    private AudioPolicyConfig(Parcel in) {
        this.mDuckingPolicy = 0;
        this.mRegistrationId = null;
        this.mMixCounter = 0;
        int nbMixes = in.readInt();
        this.mMixes = new ArrayList<>(nbMixes);
        for (int i = 0; i < nbMixes; i++) {
            this.mMixes.add(AudioMix.CREATOR.createFromParcel(in));
        }
    }

    public String toLogFriendlyString() {
        String textDump;
        String textDump2 = new String("android.media.audiopolicy.AudioPolicyConfig:\n");
        String textDump3 =
                textDump2 + this.mMixes.size() + " AudioMix, reg:" + this.mRegistrationId + "\n";
        Iterator<AudioMix> it = this.mMixes.iterator();
        while (it.hasNext()) {
            AudioMix mix = it.next();
            textDump3 =
                    (((((((textDump3
                                                                                    + "* route"
                                                                                    + " flags=0x"
                                                                                    + Integer
                                                                                            .toHexString(
                                                                                                    mix
                                                                                                            .getRouteFlags())
                                                                                    + "\n")
                                                                            + "  rate="
                                                                            + mix.getFormat()
                                                                                    .getSampleRate()
                                                                            + "Hz\n")
                                                                    + "  encoding="
                                                                    + mix.getFormat().getEncoding()
                                                                    + "\n")
                                                            + "  channels=0x")
                                                    + Integer.toHexString(
                                                                    mix.getFormat()
                                                                            .getChannelMask())
                                                            .toUpperCase()
                                                    + "\n")
                                            + "  ignore playback capture opt out="
                                            + mix.getRule().allowPrivilegedMediaPlaybackCapture()
                                            + "\n")
                                    + "  allow voice communication capture="
                                    + mix.getRule().voiceCommunicationCaptureAllowed()
                                    + "\n")
                            + "  specified mix type="
                            + mix.getRule().getTargetMixRole()
                            + "\n";
            ArrayList<AudioMixingRule.AudioMixMatchCriterion> criteria =
                    mix.getRule().getCriteria();
            Iterator<AudioMixingRule.AudioMixMatchCriterion> it2 = criteria.iterator();
            while (it2.hasNext()) {
                AudioMixingRule.AudioMixMatchCriterion criterion = it2.next();
                switch (criterion.mRule) {
                    case 1:
                        textDump = (textDump3 + "  match usage ") + criterion.mAttr.usageToString();
                        break;
                    case 2:
                        textDump =
                                (textDump3 + "  match capture preset ")
                                        + criterion.mAttr.getCapturePreset();
                        break;
                    case 4:
                        textDump = (textDump3 + "  match UID ") + criterion.mIntProp;
                        break;
                    case 8:
                        textDump = (textDump3 + "  match userId ") + criterion.mIntProp;
                        break;
                    case 16:
                        textDump = (textDump3 + " match audio session id") + criterion.mIntProp;
                        break;
                    case 32769:
                        textDump =
                                (textDump3 + "  exclude usage ") + criterion.mAttr.usageToString();
                        break;
                    case 32770:
                        textDump =
                                (textDump3 + "  exclude capture preset ")
                                        + criterion.mAttr.getCapturePreset();
                        break;
                    case 32772:
                        textDump = (textDump3 + "  exclude UID ") + criterion.mIntProp;
                        break;
                    case 32776:
                        textDump = (textDump3 + "  exclude userId ") + criterion.mIntProp;
                        break;
                    case AudioMixingRule.RULE_EXCLUDE_AUDIO_SESSION_ID /* 32784 */:
                        textDump = (textDump3 + " exclude audio session id ") + criterion.mIntProp;
                        break;
                    default:
                        textDump = textDump3 + "invalid rule!";
                        break;
                }
                textDump3 = textDump + "\n";
            }
        }
        return textDump3;
    }

    public String toCompactLogString() {
        String compactDump = "reg:" + this.mRegistrationId;
        int mixNum = 0;
        Iterator<AudioMix> it = this.mMixes.iterator();
        while (it.hasNext()) {
            AudioMix mix = it.next();
            compactDump =
                    compactDump
                            + " Mix:"
                            + mixNum
                            + "-Typ:"
                            + mixTypePrefix(mix.getMixType())
                            + "-Rul:"
                            + mix.getRule().getCriteria().size();
            mixNum++;
        }
        return compactDump;
    }

    private static String mixTypePrefix(int mixType) {
        switch (mixType) {
            case 0:
                return "p";
            case 1:
                return "r";
            default:
                return "#";
        }
    }

    protected void reset() {
        this.mMixCounter = 0;
    }

    protected void setRegistration(String regId) {
        boolean currentRegNull = this.mRegistrationId == null || this.mRegistrationId.isEmpty();
        boolean newRegNull = regId == null || regId.isEmpty();
        if (!currentRegNull && !newRegNull && !this.mRegistrationId.equals(regId)) {
            Log.e(
                    TAG,
                    "Invalid registration transition from "
                            + this.mRegistrationId
                            + " to "
                            + regId);
            return;
        }
        this.mRegistrationId = regId == null ? "" : regId;
        Iterator<AudioMix> it = this.mMixes.iterator();
        while (it.hasNext()) {
            AudioMix mix = it.next();
            setMixRegistration(mix);
        }
    }

    protected void setMixRegistration(AudioMix mix) {
        if (!this.mRegistrationId.isEmpty()) {
            if ((mix.getRouteFlags() & 2) == 2) {
                StringBuilder append =
                        new StringBuilder()
                                .append(this.mRegistrationId)
                                .append("mix")
                                .append(mixTypeId(mix.getMixType()))
                                .append(":");
                int i = this.mMixCounter;
                this.mMixCounter = i + 1;
                mix.setRegistration(append.append(i).toString());
                return;
            }
            if ((mix.getRouteFlags() & 1) == 1) {
                mix.setRegistration(mix.mDeviceAddress);
                return;
            }
            return;
        }
        mix.setRegistration("");
    }

    protected void add(ArrayList<AudioMix> mixes) {
        Iterator<AudioMix> it = mixes.iterator();
        while (it.hasNext()) {
            AudioMix mix = it.next();
            if (mix.getRegistration() == null || mix.getRegistration().isEmpty()) {
                setMixRegistration(mix);
            }
            this.mMixes.add(mix);
        }
    }

    protected void remove(ArrayList<AudioMix> mixes) {
        Iterator<AudioMix> it = mixes.iterator();
        while (it.hasNext()) {
            AudioMix mix = it.next();
            this.mMixes.remove(mix);
        }
    }

    public void updateMixingRules(List<Pair<AudioMix, AudioMixingRule>> audioMixingRuleUpdates) {
        ((List) Objects.requireNonNull(audioMixingRuleUpdates))
                .forEach(
                        new Consumer() { // from class:
                            // android.media.audiopolicy.AudioPolicyConfig$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                AudioPolicyConfig.this.lambda$updateMixingRules$0((Pair) obj);
                            }
                        });
    }

    public /* synthetic */ void lambda$updateMixingRules$0(Pair update) {
        updateMixingRule((AudioMix) update.first, (AudioMixingRule) update.second);
    }

    private void updateMixingRule(
            final AudioMix audioMixToUpdate, final AudioMixingRule audioMixingRule) {
        Stream stream = this.mMixes.stream();
        Objects.requireNonNull(audioMixToUpdate);
        stream.filter(
                        new Predicate() { // from class:
                            // android.media.audiopolicy.AudioPolicyConfig$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return AudioMix.this.equals((AudioMix) obj);
                            }
                        })
                .findAny()
                .ifPresent(
                        new Consumer() { // from class:
                            // android.media.audiopolicy.AudioPolicyConfig$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((AudioMix) obj).setAudioMixingRule(AudioMixingRule.this);
                            }
                        });
    }

    private static String mixTypeId(int type) {
        return type == 0 ? "p" : type == 1 ? "r" : "i";
    }

    protected String getRegistration() {
        return this.mRegistrationId;
    }
}
