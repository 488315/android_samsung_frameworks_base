package android.media.soundtrigger;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class Phrase implements Parcelable {
    public static final Parcelable.Creator<Phrase> CREATOR = new Parcelable.Creator<Phrase>() { // from class: android.media.soundtrigger.Phrase.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Phrase createFromParcel(Parcel parcel) {
            Phrase phrase = new Phrase();
            phrase.readFromParcel(parcel);
            return phrase;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Phrase[] newArray(int i) {
            return new Phrase[i];
        }
    };
    public String locale;
    public String text;
    public int[] users;
    public int id = 0;
    public int recognitionModes = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.id);
        parcel.writeInt(this.recognitionModes);
        parcel.writeIntArray(this.users);
        parcel.writeString(this.locale);
        parcel.writeString(this.text);
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
                this.id = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.recognitionModes = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.users = parcel.createIntArray();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.locale = parcel.readString();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.text = parcel.readString();
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
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("id: " + this.id);
        stringJoiner.add("recognitionModes: " + this.recognitionModes);
        stringJoiner.add("users: " + Arrays.toString(this.users));
        stringJoiner.add("locale: " + Objects.toString(this.locale));
        stringJoiner.add("text: " + Objects.toString(this.text));
        return "Phrase" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Phrase)) {
            return false;
        }
        Phrase phrase = (Phrase) obj;
        return Objects.deepEquals(Integer.valueOf(this.id), Integer.valueOf(phrase.id)) && Objects.deepEquals(Integer.valueOf(this.recognitionModes), Integer.valueOf(phrase.recognitionModes)) && Objects.deepEquals(this.users, phrase.users) && Objects.deepEquals(this.locale, phrase.locale) && Objects.deepEquals(this.text, phrase.text);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.id), Integer.valueOf(this.recognitionModes), this.users, this.locale, this.text).toArray());
    }
}
