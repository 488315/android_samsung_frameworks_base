package android.hardware.broadcastradio;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;

public final class Metadata implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public int _tag;
    public Object _value;

    /* renamed from: android.hardware.broadcastradio.Metadata$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            Metadata metadata = new Metadata();
            int readInt = parcel.readInt();
            switch (readInt) {
                case 0:
                    String readString = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString;
                    return metadata;
                case 1:
                    Integer valueOf = Integer.valueOf(parcel.readInt());
                    metadata._tag = readInt;
                    metadata._value = valueOf;
                    return metadata;
                case 2:
                    Integer valueOf2 = Integer.valueOf(parcel.readInt());
                    metadata._tag = readInt;
                    metadata._value = valueOf2;
                    return metadata;
                case 3:
                    String readString2 = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString2;
                    return metadata;
                case 4:
                    String readString3 = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString3;
                    return metadata;
                case 5:
                    String readString4 = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString4;
                    return metadata;
                case 6:
                    String readString5 = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString5;
                    return metadata;
                case 7:
                    Integer valueOf3 = Integer.valueOf(parcel.readInt());
                    metadata._tag = readInt;
                    metadata._value = valueOf3;
                    return metadata;
                case 8:
                    Integer valueOf4 = Integer.valueOf(parcel.readInt());
                    metadata._tag = readInt;
                    metadata._value = valueOf4;
                    return metadata;
                case 9:
                    String readString6 = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString6;
                    return metadata;
                case 10:
                    String readString7 = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString7;
                    return metadata;
                case 11:
                    String readString8 = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString8;
                    return metadata;
                case 12:
                    String readString9 = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString9;
                    return metadata;
                case 13:
                    String readString10 = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString10;
                    return metadata;
                case 14:
                    String readString11 = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString11;
                    return metadata;
                case 15:
                    String readString12 = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString12;
                    return metadata;
                case 16:
                    String readString13 = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString13;
                    return metadata;
                case 17:
                    String readString14 = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString14;
                    return metadata;
                case 18:
                    String readString15 = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString15;
                    return metadata;
                case 19:
                    String readString16 = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString16;
                    return metadata;
                case 20:
                    String[] createStringArray = parcel.createStringArray();
                    metadata._tag = readInt;
                    metadata._value = createStringArray;
                    return metadata;
                case 21:
                    String readString17 = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString17;
                    return metadata;
                case 22:
                    String readString18 = parcel.readString();
                    metadata._tag = readInt;
                    metadata._value = readString18;
                    return metadata;
                case 23:
                    Integer valueOf5 = Integer.valueOf(parcel.readInt());
                    metadata._tag = readInt;
                    metadata._value = valueOf5;
                    return metadata;
                default:
                    throw new IllegalArgumentException(
                            VibrationParam$1$$ExternalSyntheticOutline0.m(
                                    readInt, "union: unknown tag: "));
            }
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Metadata[i];
        }
    }

    public static String _tagString(int i) {
        switch (i) {
            case 0:
                return "rdsPs";
            case 1:
                return "rdsPty";
            case 2:
                return "rbdsPty";
            case 3:
                return "rdsRt";
            case 4:
                return "songTitle";
            case 5:
                return "songArtist";
            case 6:
                return "songAlbum";
            case 7:
                return "stationIcon";
            case 8:
                return "albumArt";
            case 9:
                return "programName";
            case 10:
                return "dabEnsembleName";
            case 11:
                return "dabEnsembleNameShort";
            case 12:
                return "dabServiceName";
            case 13:
                return "dabServiceNameShort";
            case 14:
                return "dabComponentName";
            case 15:
                return "dabComponentNameShort";
            case 16:
                return "genre";
            case 17:
                return "commentShortDescription";
            case 18:
                return "commentActualText";
            case 19:
                return "commercial";
            case 20:
                return "ufids";
            case 21:
                return "hdStationNameShort";
            case 22:
                return "hdStationNameLong";
            case 23:
                return "hdSubChannelsAvailable";
            default:
                throw new IllegalStateException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(i, "unknown field: "));
        }
    }

    public final void _assertTag(int i) {
        if (this._tag == i) {
            return;
        }
        throw new IllegalStateException(
                "bad access: " + _tagString(i) + ", " + _tagString(this._tag) + " is available.");
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Metadata)) {
            return false;
        }
        Metadata metadata = (Metadata) obj;
        return this._tag == metadata._tag && Objects.deepEquals(this._value, metadata._value);
    }

    public final int getStability() {
        return 1;
    }

    public final int hashCode() {
        return Arrays.deepHashCode(
                Arrays.asList(Integer.valueOf(this._tag), this._value).toArray());
    }

    public final String toString() {
        switch (this._tag) {
            case 0:
                StringBuilder m = Metadata$$ExternalSyntheticOutline0.m("Metadata.rdsPs(", this, 0);
                m.append(Objects.toString((String) this._value));
                m.append(")");
                return m.toString();
            case 1:
                StringBuilder m2 =
                        Metadata$$ExternalSyntheticOutline0.m("Metadata.rdsPty(", this, 1);
                m2.append(((Integer) this._value).intValue());
                m2.append(")");
                return m2.toString();
            case 2:
                StringBuilder m3 =
                        Metadata$$ExternalSyntheticOutline0.m("Metadata.rbdsPty(", this, 2);
                m3.append(((Integer) this._value).intValue());
                m3.append(")");
                return m3.toString();
            case 3:
                StringBuilder m4 =
                        Metadata$$ExternalSyntheticOutline0.m("Metadata.rdsRt(", this, 3);
                m4.append(Objects.toString((String) this._value));
                m4.append(")");
                return m4.toString();
            case 4:
                StringBuilder m5 =
                        Metadata$$ExternalSyntheticOutline0.m("Metadata.songTitle(", this, 4);
                m5.append(Objects.toString((String) this._value));
                m5.append(")");
                return m5.toString();
            case 5:
                StringBuilder m6 =
                        Metadata$$ExternalSyntheticOutline0.m("Metadata.songArtist(", this, 5);
                m6.append(Objects.toString((String) this._value));
                m6.append(")");
                return m6.toString();
            case 6:
                StringBuilder m7 =
                        Metadata$$ExternalSyntheticOutline0.m("Metadata.songAlbum(", this, 6);
                m7.append(Objects.toString((String) this._value));
                m7.append(")");
                return m7.toString();
            case 7:
                StringBuilder m8 =
                        Metadata$$ExternalSyntheticOutline0.m("Metadata.stationIcon(", this, 7);
                m8.append(((Integer) this._value).intValue());
                m8.append(")");
                return m8.toString();
            case 8:
                StringBuilder m9 =
                        Metadata$$ExternalSyntheticOutline0.m("Metadata.albumArt(", this, 8);
                m9.append(((Integer) this._value).intValue());
                m9.append(")");
                return m9.toString();
            case 9:
                StringBuilder m10 =
                        Metadata$$ExternalSyntheticOutline0.m("Metadata.programName(", this, 9);
                m10.append(Objects.toString((String) this._value));
                m10.append(")");
                return m10.toString();
            case 10:
                StringBuilder m11 =
                        Metadata$$ExternalSyntheticOutline0.m(
                                "Metadata.dabEnsembleName(", this, 10);
                m11.append(Objects.toString((String) this._value));
                m11.append(")");
                return m11.toString();
            case 11:
                StringBuilder m12 =
                        Metadata$$ExternalSyntheticOutline0.m(
                                "Metadata.dabEnsembleNameShort(", this, 11);
                m12.append(Objects.toString((String) this._value));
                m12.append(")");
                return m12.toString();
            case 12:
                StringBuilder m13 =
                        Metadata$$ExternalSyntheticOutline0.m("Metadata.dabServiceName(", this, 12);
                m13.append(Objects.toString((String) this._value));
                m13.append(")");
                return m13.toString();
            case 13:
                StringBuilder m14 =
                        Metadata$$ExternalSyntheticOutline0.m(
                                "Metadata.dabServiceNameShort(", this, 13);
                m14.append(Objects.toString((String) this._value));
                m14.append(")");
                return m14.toString();
            case 14:
                StringBuilder m15 =
                        Metadata$$ExternalSyntheticOutline0.m(
                                "Metadata.dabComponentName(", this, 14);
                m15.append(Objects.toString((String) this._value));
                m15.append(")");
                return m15.toString();
            case 15:
                StringBuilder m16 =
                        Metadata$$ExternalSyntheticOutline0.m(
                                "Metadata.dabComponentNameShort(", this, 15);
                m16.append(Objects.toString((String) this._value));
                m16.append(")");
                return m16.toString();
            case 16:
                StringBuilder m17 =
                        Metadata$$ExternalSyntheticOutline0.m("Metadata.genre(", this, 16);
                m17.append(Objects.toString((String) this._value));
                m17.append(")");
                return m17.toString();
            case 17:
                StringBuilder m18 =
                        Metadata$$ExternalSyntheticOutline0.m(
                                "Metadata.commentShortDescription(", this, 17);
                m18.append(Objects.toString((String) this._value));
                m18.append(")");
                return m18.toString();
            case 18:
                StringBuilder m19 =
                        Metadata$$ExternalSyntheticOutline0.m(
                                "Metadata.commentActualText(", this, 18);
                m19.append(Objects.toString((String) this._value));
                m19.append(")");
                return m19.toString();
            case 19:
                StringBuilder m20 =
                        Metadata$$ExternalSyntheticOutline0.m("Metadata.commercial(", this, 19);
                m20.append(Objects.toString((String) this._value));
                m20.append(")");
                return m20.toString();
            case 20:
                return AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                        Metadata$$ExternalSyntheticOutline0.m("Metadata.ufids(", this, 20),
                        Arrays.toString((String[]) this._value),
                        ")");
            case 21:
                StringBuilder m21 =
                        Metadata$$ExternalSyntheticOutline0.m(
                                "Metadata.hdStationNameShort(", this, 21);
                m21.append(Objects.toString((String) this._value));
                m21.append(")");
                return m21.toString();
            case 22:
                StringBuilder m22 =
                        Metadata$$ExternalSyntheticOutline0.m(
                                "Metadata.hdStationNameLong(", this, 22);
                m22.append(Objects.toString((String) this._value));
                m22.append(")");
                return m22.toString();
            case 23:
                StringBuilder m23 =
                        Metadata$$ExternalSyntheticOutline0.m(
                                "Metadata.hdSubChannelsAvailable(", this, 23);
                m23.append(((Integer) this._value).intValue());
                m23.append(")");
                return m23.toString();
            default:
                throw new IllegalStateException("unknown field: " + this._tag);
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                _assertTag(0);
                parcel.writeString((String) this._value);
                break;
            case 1:
                _assertTag(1);
                parcel.writeInt(((Integer) this._value).intValue());
                break;
            case 2:
                _assertTag(2);
                parcel.writeInt(((Integer) this._value).intValue());
                break;
            case 3:
                _assertTag(3);
                parcel.writeString((String) this._value);
                break;
            case 4:
                _assertTag(4);
                parcel.writeString((String) this._value);
                break;
            case 5:
                _assertTag(5);
                parcel.writeString((String) this._value);
                break;
            case 6:
                _assertTag(6);
                parcel.writeString((String) this._value);
                break;
            case 7:
                _assertTag(7);
                parcel.writeInt(((Integer) this._value).intValue());
                break;
            case 8:
                _assertTag(8);
                parcel.writeInt(((Integer) this._value).intValue());
                break;
            case 9:
                _assertTag(9);
                parcel.writeString((String) this._value);
                break;
            case 10:
                _assertTag(10);
                parcel.writeString((String) this._value);
                break;
            case 11:
                _assertTag(11);
                parcel.writeString((String) this._value);
                break;
            case 12:
                _assertTag(12);
                parcel.writeString((String) this._value);
                break;
            case 13:
                _assertTag(13);
                parcel.writeString((String) this._value);
                break;
            case 14:
                _assertTag(14);
                parcel.writeString((String) this._value);
                break;
            case 15:
                _assertTag(15);
                parcel.writeString((String) this._value);
                break;
            case 16:
                _assertTag(16);
                parcel.writeString((String) this._value);
                break;
            case 17:
                _assertTag(17);
                parcel.writeString((String) this._value);
                break;
            case 18:
                _assertTag(18);
                parcel.writeString((String) this._value);
                break;
            case 19:
                _assertTag(19);
                parcel.writeString((String) this._value);
                break;
            case 20:
                _assertTag(20);
                parcel.writeStringArray((String[]) this._value);
                break;
            case 21:
                _assertTag(21);
                parcel.writeString((String) this._value);
                break;
            case 22:
                _assertTag(22);
                parcel.writeString((String) this._value);
                break;
            case 23:
                _assertTag(23);
                parcel.writeInt(((Integer) this._value).intValue());
                break;
        }
    }
}
