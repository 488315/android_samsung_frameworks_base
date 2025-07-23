package android.hardware.radio;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public final class RadioAlert implements Parcelable {
    public static final int CATEGORY_CBRNE = 10;
    public static final int CATEGORY_ENV = 7;
    public static final int CATEGORY_FIRE = 5;
    public static final int CATEGORY_GEO = 0;
    public static final int CATEGORY_HEALTH = 6;
    public static final int CATEGORY_INFRA = 9;
    public static final int CATEGORY_MET = 1;
    public static final int CATEGORY_OTHER = 11;
    public static final int CATEGORY_RESCUE = 4;
    public static final int CATEGORY_SAFETY = 2;
    public static final int CATEGORY_SECURITY = 3;
    public static final int CATEGORY_TRANSPORT = 8;
    public static final int CERTAINTY_LIKELY = 1;
    public static final int CERTAINTY_OBSERVED = 0;
    public static final int CERTAINTY_POSSIBLE = 2;
    public static final int CERTAINTY_UNKNOWN = 4;
    public static final int CERTAINTY_UNLIKELY = 3;
    public static final Parcelable.Creator<RadioAlert> CREATOR = new Parcelable.Creator<RadioAlert>() { // from class: android.hardware.radio.RadioAlert.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioAlert createFromParcel(Parcel parcel) {
            return new RadioAlert(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioAlert[] newArray(int i) {
            return new RadioAlert[i];
        }
    };
    public static final int MESSAGE_TYPE_ALERT = 0;
    public static final int MESSAGE_TYPE_CANCEL = 2;
    public static final int MESSAGE_TYPE_UPDATE = 1;
    public static final int SEVERITY_EXTREME = 0;
    public static final int SEVERITY_MINOR = 3;
    public static final int SEVERITY_MODERATE = 2;
    public static final int SEVERITY_SEVERE = 1;
    public static final int SEVERITY_UNKNOWN = 4;
    public static final int STATUS_ACTUAL = 0;
    public static final int STATUS_EXERCISE = 1;
    public static final int STATUS_TEST = 2;
    public static final int URGENCY_EXPECTED = 1;
    public static final int URGENCY_FUTURE = 2;
    public static final int URGENCY_IMMEDIATE = 0;
    public static final int URGENCY_PAST = 3;
    public static final int URGENCY_UNKNOWN = 4;
    private final List<AlertInfo> mInfoList;
    private final int mMessageType;
    private final int mStatus;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AlertCategory {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AlertCertainty {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AlertMessageType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AlertSeverity {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AlertStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AlertUrgency {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Geocode implements Parcelable {
        public static final Parcelable.Creator<Geocode> CREATOR = new Parcelable.Creator<Geocode>() { // from class: android.hardware.radio.RadioAlert.Geocode.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Geocode createFromParcel(Parcel parcel) {
                return new Geocode(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Geocode[] newArray(int i) {
                return new Geocode[i];
            }
        };
        private final String mValue;
        private final String mValueName;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Geocode(String str, String str2) {
            this.mValueName = (String) Objects.requireNonNull(str, "Geocode value name can not be null");
            this.mValue = (String) Objects.requireNonNull(str2, "Geocode value can not be null");
        }

        private Geocode(Parcel parcel) {
            this.mValueName = parcel.readString8();
            this.mValue = parcel.readString8();
        }

        public String getValueName() {
            return this.mValueName;
        }

        public String getValue() {
            return this.mValue;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString8(this.mValueName);
            parcel.writeString8(this.mValue);
        }

        public String toString() {
            return "Gecode [valueName=" + this.mValueName + ", value=" + this.mValue + NavigationBarInflaterView.SIZE_MOD_END;
        }

        public int hashCode() {
            return Objects.hash(this.mValueName, this.mValue);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Geocode) {
                Geocode geocode = (Geocode) obj;
                if (Objects.equals(this.mValueName, geocode.mValueName) && Objects.equals(this.mValue, geocode.mValue)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static final class Coordinate implements Parcelable {
        public static final Parcelable.Creator<Coordinate> CREATOR = new Parcelable.Creator<Coordinate>() { // from class: android.hardware.radio.RadioAlert.Coordinate.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Coordinate createFromParcel(Parcel parcel) {
                return new Coordinate(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Coordinate[] newArray(int i) {
                return new Coordinate[i];
            }
        };
        private final double mLatitude;
        private final double mLongitude;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Coordinate(double d, double d2) {
            if (d < -90.0d || d > 90.0d) {
                throw new IllegalArgumentException("Latitude value should be between -90 and 90");
            }
            if (d2 < -180.0d || d2 > 180.0d) {
                throw new IllegalArgumentException("Longitude value should be between -180 and 180");
            }
            this.mLatitude = d;
            this.mLongitude = d2;
        }

        private Coordinate(Parcel parcel) {
            this.mLatitude = parcel.readDouble();
            this.mLongitude = parcel.readDouble();
        }

        public double getLatitude() {
            return this.mLatitude;
        }

        public double getLongitude() {
            return this.mLongitude;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeDouble(this.mLatitude);
            parcel.writeDouble(this.mLongitude);
        }

        public String toString() {
            return "Coordinate [latitude=" + this.mLatitude + ", longitude=" + this.mLongitude + NavigationBarInflaterView.SIZE_MOD_END;
        }

        public int hashCode() {
            return Objects.hash(Double.valueOf(this.mLatitude), Double.valueOf(this.mLongitude));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Coordinate) {
                Coordinate coordinate = (Coordinate) obj;
                if (this.mLatitude == coordinate.mLatitude && this.mLongitude == coordinate.mLongitude) {
                    return true;
                }
            }
            return false;
        }
    }

    public static final class Polygon implements Parcelable {
        public static final Parcelable.Creator<Polygon> CREATOR = new Parcelable.Creator<Polygon>() { // from class: android.hardware.radio.RadioAlert.Polygon.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Polygon createFromParcel(Parcel parcel) {
                return new Polygon(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Polygon[] newArray(int i) {
                return new Polygon[i];
            }
        };
        private final List<Coordinate> mCoordinates;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Polygon(List<Coordinate> list) {
            Objects.requireNonNull(list, "Coordinates can not be null");
            if (list.size() < 4) {
                throw new IllegalArgumentException("Number of coordinates must be at least 4");
            }
            if (!list.get(0).equals(list.get(list.size() - 1))) {
                throw new IllegalArgumentException("The last and first coordinates must be the same");
            }
            this.mCoordinates = list;
        }

        private Polygon(Parcel parcel) {
            ArrayList arrayList = new ArrayList();
            this.mCoordinates = arrayList;
            parcel.readTypedList(arrayList, Coordinate.CREATOR);
        }

        public List<Coordinate> getCoordinates() {
            return this.mCoordinates;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedList(this.mCoordinates);
        }

        public String toString() {
            return "Polygon [coordinates=" + this.mCoordinates + NavigationBarInflaterView.SIZE_MOD_END;
        }

        public int hashCode() {
            return Objects.hash(this.mCoordinates);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Polygon) {
                return this.mCoordinates.equals(((Polygon) obj).mCoordinates);
            }
            return false;
        }
    }

    public static final class AlertArea implements Parcelable {
        public static final Parcelable.Creator<AlertArea> CREATOR = new Parcelable.Creator<AlertArea>() { // from class: android.hardware.radio.RadioAlert.AlertArea.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AlertArea createFromParcel(Parcel parcel) {
                return new AlertArea(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AlertArea[] newArray(int i) {
                return new AlertArea[i];
            }
        };
        private final List<Geocode> mGeocodes;
        private final List<Polygon> mPolygons;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public AlertArea(List<Polygon> list, List<Geocode> list2) {
            this.mPolygons = (List) Objects.requireNonNull(list, "Polygons can not be null");
            this.mGeocodes = (List) Objects.requireNonNull(list2, "Geocodes can not be null");
        }

        private AlertArea(Parcel parcel) {
            ArrayList arrayList = new ArrayList();
            this.mPolygons = arrayList;
            ArrayList arrayList2 = new ArrayList();
            this.mGeocodes = arrayList2;
            parcel.readTypedList(arrayList, Polygon.CREATOR);
            parcel.readTypedList(arrayList2, Geocode.CREATOR);
        }

        public List<Polygon> getPolygons() {
            return this.mPolygons;
        }

        public List<Geocode> getGeocodes() {
            return this.mGeocodes;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedList(this.mPolygons);
            parcel.writeTypedList(this.mGeocodes);
        }

        public String toString() {
            return "AlertArea [polygons=" + this.mPolygons + ", geocodes=" + this.mGeocodes + NavigationBarInflaterView.SIZE_MOD_END;
        }

        public int hashCode() {
            return Objects.hash(this.mPolygons, this.mGeocodes);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof AlertArea) {
                AlertArea alertArea = (AlertArea) obj;
                if (this.mPolygons.equals(alertArea.mPolygons) && this.mGeocodes.equals(alertArea.mGeocodes)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static final class AlertInfo implements Parcelable {
        public static final Parcelable.Creator<AlertInfo> CREATOR = new Parcelable.Creator<AlertInfo>() { // from class: android.hardware.radio.RadioAlert.AlertInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AlertInfo createFromParcel(Parcel parcel) {
                return new AlertInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AlertInfo[] newArray(int i) {
                return new AlertInfo[i];
            }
        };
        private final List<AlertArea> mAreaList;
        private final int[] mCategories;
        private final int mCertainty;
        private final String mLanguage;
        private final int mSeverity;
        private final String mTextualMessage;
        private final int mUrgency;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public AlertInfo(int[] iArr, int i, int i2, int i3, String str, List<AlertArea> list, String str2) {
            Objects.requireNonNull(iArr, "Categories can not be null");
            Arrays.sort(iArr);
            this.mCategories = iArr;
            this.mUrgency = i;
            this.mSeverity = i2;
            this.mCertainty = i3;
            this.mTextualMessage = str;
            this.mAreaList = (List) Objects.requireNonNull(list, "Area list can not be null");
            this.mLanguage = str2;
        }

        private AlertInfo(Parcel parcel) {
            int[] iArr = new int[parcel.readInt()];
            this.mCategories = iArr;
            parcel.readIntArray(iArr);
            this.mUrgency = parcel.readInt();
            this.mSeverity = parcel.readInt();
            this.mCertainty = parcel.readInt();
            this.mTextualMessage = parcel.readString8();
            ArrayList arrayList = new ArrayList();
            this.mAreaList = arrayList;
            parcel.readTypedList(arrayList, AlertArea.CREATOR);
            if (parcel.readBoolean()) {
                this.mLanguage = parcel.readString8();
            } else {
                this.mLanguage = null;
            }
        }

        public int[] getCategories() {
            return this.mCategories;
        }

        public int getUrgency() {
            return this.mUrgency;
        }

        public int getSeverity() {
            return this.mSeverity;
        }

        public int getCertainty() {
            return this.mCertainty;
        }

        public String getDescription() {
            return this.mTextualMessage;
        }

        public List<AlertArea> getAreas() {
            return this.mAreaList;
        }

        public String getLanguage() {
            return this.mLanguage;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mCategories.length);
            parcel.writeIntArray(this.mCategories);
            parcel.writeInt(this.mUrgency);
            parcel.writeInt(this.mSeverity);
            parcel.writeInt(this.mCertainty);
            parcel.writeString8(this.mTextualMessage);
            parcel.writeTypedList(this.mAreaList);
            if (this.mLanguage == null) {
                parcel.writeBoolean(false);
            } else {
                parcel.writeBoolean(true);
                parcel.writeString8(this.mLanguage);
            }
        }

        public String toString() {
            return "AlertInfo [categories=" + Arrays.toString(this.mCategories) + ", urgency=" + this.mUrgency + ", severity=" + this.mSeverity + ", certainty=" + this.mCertainty + ", textualMessage=" + this.mTextualMessage + ", areaList=" + this.mAreaList + ", language=" + this.mLanguage + NavigationBarInflaterView.SIZE_MOD_END;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(Arrays.hashCode(this.mCategories)), Integer.valueOf(this.mUrgency), Integer.valueOf(this.mSeverity), Integer.valueOf(this.mCertainty), this.mTextualMessage, this.mAreaList, this.mLanguage);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof AlertInfo) {
                AlertInfo alertInfo = (AlertInfo) obj;
                if (Arrays.equals(this.mCategories, alertInfo.mCategories) && this.mUrgency == alertInfo.mUrgency && this.mSeverity == alertInfo.mSeverity && this.mCertainty == alertInfo.mCertainty && this.mTextualMessage.equals(alertInfo.mTextualMessage) && this.mAreaList.equals(alertInfo.mAreaList) && Objects.equals(this.mLanguage, alertInfo.mLanguage)) {
                    return true;
                }
            }
            return false;
        }
    }

    public RadioAlert(int i, int i2, List<AlertInfo> list) {
        this.mStatus = i;
        this.mMessageType = i2;
        this.mInfoList = (List) Objects.requireNonNull(list, "Alert info list can not be null");
    }

    private RadioAlert(Parcel parcel) {
        this.mStatus = parcel.readInt();
        this.mMessageType = parcel.readInt();
        this.mInfoList = parcel.readParcelableList(new ArrayList(), AlertInfo.class.getClassLoader(), AlertInfo.class);
    }

    public int getStatus() {
        return this.mStatus;
    }

    public int getMessageType() {
        return this.mMessageType;
    }

    public List<AlertInfo> getInfoList() {
        return this.mInfoList;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mStatus);
        parcel.writeInt(this.mMessageType);
        parcel.writeParcelableList(this.mInfoList, 0);
    }

    public String toString() {
        return "RadioAlert [status=" + this.mStatus + ", messageType=" + this.mMessageType + ", infoList= " + this.mInfoList + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mStatus), Integer.valueOf(this.mMessageType), this.mInfoList);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof RadioAlert) {
            RadioAlert radioAlert = (RadioAlert) obj;
            if (this.mStatus == radioAlert.mStatus && this.mMessageType == radioAlert.mMessageType && this.mInfoList.equals(radioAlert.mInfoList)) {
                return true;
            }
        }
        return false;
    }
}
