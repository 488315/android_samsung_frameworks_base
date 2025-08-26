package android.media;

import android.icu.util.ULocale;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class AudioPresentation implements Parcelable {
    public static final int CONTENT_COMMENTARY = 5;
    public static final int CONTENT_DIALOG = 4;
    public static final int CONTENT_EMERGENCY = 6;
    public static final int CONTENT_HEARING_IMPAIRED = 3;
    public static final int CONTENT_MAIN = 0;
    public static final int CONTENT_MUSIC_AND_EFFECTS = 1;
    public static final int CONTENT_UNKNOWN = -1;
    public static final int CONTENT_VISUALLY_IMPAIRED = 2;
    public static final int CONTENT_VOICEOVER = 7;
    public static final Parcelable.Creator<AudioPresentation> CREATOR = new Parcelable.Creator<AudioPresentation>() { // from class: android.media.AudioPresentation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPresentation createFromParcel(Parcel parcel) {
            return new AudioPresentation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPresentation[] newArray(int i) {
            return new AudioPresentation[i];
        }
    };
    public static final int MASTERED_FOR_3D = 3;
    public static final int MASTERED_FOR_HEADPHONE = 4;
    public static final int MASTERED_FOR_STEREO = 1;
    public static final int MASTERED_FOR_SURROUND = 2;
    public static final int MASTERING_NOT_INDICATED = 0;
    public static final int PRESENTATION_ID_UNKNOWN = -1;
    public static final int PROGRAM_ID_UNKNOWN = -1;
    private final boolean mAudioDescriptionAvailable;
    private final boolean mDialogueEnhancementAvailable;
    private final HashMap<ULocale, String> mLabels;
    private final ULocale mLanguage;
    private final int mMasteringIndication;
    private final int mPresentationId;
    private final int mProgramId;
    private final boolean mSpokenSubtitlesAvailable;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ContentClassifier {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MasteringIndicationType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AudioPresentation(int i, int i2, ULocale uLocale, int i3, boolean z, boolean z2, boolean z3, Map<ULocale, String> map) {
        this.mPresentationId = i;
        this.mProgramId = i2;
        this.mLanguage = uLocale;
        this.mMasteringIndication = i3;
        this.mAudioDescriptionAvailable = z;
        this.mSpokenSubtitlesAvailable = z2;
        this.mDialogueEnhancementAvailable = z3;
        this.mLabels = new HashMap<>(map);
    }

    private AudioPresentation(Parcel parcel) {
        this.mPresentationId = parcel.readInt();
        this.mProgramId = parcel.readInt();
        this.mLanguage = (ULocale) parcel.readSerializable(ULocale.class.getClassLoader(), ULocale.class);
        this.mMasteringIndication = parcel.readInt();
        this.mAudioDescriptionAvailable = parcel.readBoolean();
        this.mSpokenSubtitlesAvailable = parcel.readBoolean();
        this.mDialogueEnhancementAvailable = parcel.readBoolean();
        this.mLabels = (HashMap) parcel.readSerializable(HashMap.class.getClassLoader(), HashMap.class);
    }

    public int getPresentationId() {
        return this.mPresentationId;
    }

    public int getProgramId() {
        return this.mProgramId;
    }

    public Map<Locale, String> getLabels() {
        HashMap map = new HashMap(this.mLabels.size());
        for (Map.Entry<ULocale, String> entry : this.mLabels.entrySet()) {
            map.put(entry.getKey().toLocale(), entry.getValue());
        }
        return map;
    }

    private Map<ULocale, String> getULabels() {
        return this.mLabels;
    }

    public Locale getLocale() {
        return this.mLanguage.toLocale();
    }

    private ULocale getULocale() {
        return this.mLanguage;
    }

    public int getMasteringIndication() {
        return this.mMasteringIndication;
    }

    public boolean hasAudioDescription() {
        return this.mAudioDescriptionAvailable;
    }

    public boolean hasSpokenSubtitles() {
        return this.mSpokenSubtitlesAvailable;
    }

    public boolean hasDialogueEnhancement() {
        return this.mDialogueEnhancementAvailable;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AudioPresentation)) {
            return false;
        }
        AudioPresentation audioPresentation = (AudioPresentation) obj;
        return this.mPresentationId == audioPresentation.getPresentationId() && this.mProgramId == audioPresentation.getProgramId() && this.mLanguage.equals(audioPresentation.getULocale()) && this.mMasteringIndication == audioPresentation.getMasteringIndication() && this.mAudioDescriptionAvailable == audioPresentation.hasAudioDescription() && this.mSpokenSubtitlesAvailable == audioPresentation.hasSpokenSubtitles() && this.mDialogueEnhancementAvailable == audioPresentation.hasDialogueEnhancement() && this.mLabels.equals(audioPresentation.getULabels());
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mPresentationId), Integer.valueOf(this.mProgramId), Integer.valueOf(this.mLanguage.hashCode()), Integer.valueOf(this.mMasteringIndication), Boolean.valueOf(this.mAudioDescriptionAvailable), Boolean.valueOf(this.mSpokenSubtitlesAvailable), Boolean.valueOf(this.mDialogueEnhancementAvailable), Integer.valueOf(this.mLabels.hashCode()));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName() + " ");
        StringBuilder sb2 = new StringBuilder("{ presentation id=");
        sb2.append(this.mPresentationId);
        sb.append(sb2.toString());
        sb.append(", program id=" + this.mProgramId);
        sb.append(", language=" + this.mLanguage);
        sb.append(", labels=" + this.mLabels);
        sb.append(", mastering indication=" + this.mMasteringIndication);
        sb.append(", audio description=" + this.mAudioDescriptionAvailable);
        sb.append(", spoken subtitles=" + this.mSpokenSubtitlesAvailable);
        sb.append(", dialogue enhancement=" + this.mDialogueEnhancementAvailable);
        sb.append(" }");
        return sb.toString();
    }

    public static final class Builder {
        private final int mPresentationId;
        private int mProgramId = -1;
        private ULocale mLanguage = new ULocale("");
        private int mMasteringIndication = 0;
        private boolean mAudioDescriptionAvailable = false;
        private boolean mSpokenSubtitlesAvailable = false;
        private boolean mDialogueEnhancementAvailable = false;
        private HashMap<ULocale, String> mLabels = new HashMap<>();

        public Builder(int i) {
            this.mPresentationId = i;
        }

        public Builder setProgramId(int i) {
            this.mProgramId = i;
            return this;
        }

        public Builder setLocale(ULocale uLocale) {
            this.mLanguage = uLocale;
            return this;
        }

        public Builder setMasteringIndication(int i) {
            if (i != 0 && i != 1 && i != 2 && i != 3 && i != 4) {
                throw new IllegalArgumentException("Unknown mastering indication: " + i);
            }
            this.mMasteringIndication = i;
            return this;
        }

        public Builder setLabels(Map<ULocale, CharSequence> map) {
            this.mLabels.clear();
            for (Map.Entry<ULocale, CharSequence> entry : map.entrySet()) {
                this.mLabels.put(entry.getKey(), entry.getValue().toString());
            }
            return this;
        }

        public Builder setHasAudioDescription(boolean z) {
            this.mAudioDescriptionAvailable = z;
            return this;
        }

        public Builder setHasSpokenSubtitles(boolean z) {
            this.mSpokenSubtitlesAvailable = z;
            return this;
        }

        public Builder setHasDialogueEnhancement(boolean z) {
            this.mDialogueEnhancementAvailable = z;
            return this;
        }

        public AudioPresentation build() {
            return new AudioPresentation(this.mPresentationId, this.mProgramId, this.mLanguage, this.mMasteringIndication, this.mAudioDescriptionAvailable, this.mSpokenSubtitlesAvailable, this.mDialogueEnhancementAvailable, this.mLabels);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
        parcel.writeInt(getPresentationId());
        parcel.writeInt(getProgramId());
        parcel.writeSerializable(getULocale());
        parcel.writeInt(getMasteringIndication());
        parcel.writeBoolean(hasAudioDescription());
        parcel.writeBoolean(hasSpokenSubtitles());
        parcel.writeBoolean(hasDialogueEnhancement());
        parcel.writeSerializable(this.mLabels);
    }
}
