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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.preselectionId);
        parcel.writeTypedArray(this.labels, i);
        parcel.writeString(this.language);
        parcel.writeInt(this.renderingIndication);
        parcel.writeBoolean(this.hasAudioDescription);
        parcel.writeBoolean(this.hasSpokenSubtitles);
        parcel.writeBoolean(this.hasDialogueEnhancement);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.preselectionId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.labels = (AudioPreselectionLabel[]) parcel.createTypedArray(AudioPreselectionLabel.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.language = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.renderingIndication = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.hasAudioDescription = parcel.readBoolean();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.hasSpokenSubtitles = parcel.readBoolean();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.hasDialogueEnhancement = parcel.readBoolean();
                                        if (iDataPosition > Integer.MAX_VALUE - i) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
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
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
