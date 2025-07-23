package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class AudioPreselection implements Parcelable {
    public static final Parcelable.Creator<AudioPreselection> CREATOR = new Parcelable.Creator<AudioPreselection>() { // from class: android.hardware.tv.tuner.AudioPreselection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPreselection createFromParcel(Parcel parcel) {
            AudioPreselection audioPreselection = new AudioPreselection();
            audioPreselection.readFromParcel(parcel);
            return audioPreselection;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioPreselection[] newArray(int i) {
            return new AudioPreselection[i];
        }
    };
    public AudioPreselectionLabel[] labels;
    public String language;
    public int renderingIndication;
    public int preselectionId = 0;
    public boolean hasAudioDescription = false;
    public boolean hasSpokenSubtitles = false;
    public boolean hasDialogueEnhancement = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.preselectionId);
        parcel.writeTypedArray(this.labels, i);
        parcel.writeString(this.language);
        parcel.writeInt(this.renderingIndication);
        parcel.writeBoolean(this.hasAudioDescription);
        parcel.writeBoolean(this.hasSpokenSubtitles);
        parcel.writeBoolean(this.hasDialogueEnhancement);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.preselectionId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.labels = (AudioPreselectionLabel[]) parcel.createTypedArray(AudioPreselectionLabel.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.language = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.renderingIndication = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.hasAudioDescription = parcel.readBoolean();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.hasSpokenSubtitles = parcel.readBoolean();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.hasDialogueEnhancement = parcel.readBoolean();
                                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.labels);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
