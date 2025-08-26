package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.Objects;
import java.util.regex.Pattern;

@SystemApi
/* loaded from: classes4.dex */
public final class PhoneNumberRange implements Parcelable {
    public static final Parcelable.Creator<PhoneNumberRange> CREATOR = new Parcelable.Creator<PhoneNumberRange>() { // from class: android.telephony.PhoneNumberRange.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhoneNumberRange createFromParcel(Parcel parcel) {
            return new PhoneNumberRange(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhoneNumberRange[] newArray(int i) {
            return new PhoneNumberRange[i];
        }
    };
    private final String mCountryCode;
    private final String mLowerBound;
    private final String mPrefix;
    private final String mUpperBound;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PhoneNumberRange(String str, String str2, String str3, String str4) {
        validateLowerAndUpperBounds(str3, str4);
        if (!Pattern.matches("[0-9]*", str)) {
            throw new IllegalArgumentException("Country code must be all numeric");
        }
        if (!Pattern.matches("[0-9]*", str2)) {
            throw new IllegalArgumentException("Prefix must be all numeric");
        }
        this.mCountryCode = str;
        this.mPrefix = str2;
        this.mLowerBound = str3;
        this.mUpperBound = str4;
    }

    private PhoneNumberRange(Parcel parcel) {
        this.mCountryCode = parcel.readString();
        this.mPrefix = parcel.readString();
        this.mLowerBound = parcel.readString();
        this.mUpperBound = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mCountryCode);
        parcel.writeString(this.mPrefix);
        parcel.writeString(this.mLowerBound);
        parcel.writeString(this.mUpperBound);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PhoneNumberRange phoneNumberRange = (PhoneNumberRange) obj;
            if (Objects.equals(this.mCountryCode, phoneNumberRange.mCountryCode) && Objects.equals(this.mPrefix, phoneNumberRange.mPrefix) && Objects.equals(this.mLowerBound, phoneNumberRange.mLowerBound) && Objects.equals(this.mUpperBound, phoneNumberRange.mUpperBound)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mCountryCode, this.mPrefix, this.mLowerBound, this.mUpperBound);
    }

    public String toString() {
        return "PhoneNumberRange{mCountryCode='" + this.mCountryCode + "', mPrefix='" + this.mPrefix + "', mLowerBound='" + this.mLowerBound + "', mUpperBound='" + this.mUpperBound + "'}";
    }

    private void validateLowerAndUpperBounds(String str, String str2) {
        if (str.length() != str2.length()) {
            throw new IllegalArgumentException("Lower and upper bounds must have the same length");
        }
        if (!Pattern.matches("[0-9]*", str)) {
            throw new IllegalArgumentException("Lower bound must be all numeric");
        }
        if (!Pattern.matches("[0-9]*", str2)) {
            throw new IllegalArgumentException("Upper bound must be all numeric");
        }
        if (Integer.parseInt(str) > Integer.parseInt(str2)) {
            throw new IllegalArgumentException("Lower bound must be lower than upper bound");
        }
    }

    public boolean matches(String str) throws NumberFormatException {
        String strSubstring;
        String strReplaceAll = str.replaceAll("[^0-9]", "");
        String str2 = this.mCountryCode + this.mPrefix;
        if (strReplaceAll.startsWith(str2)) {
            strSubstring = strReplaceAll.substring(str2.length());
        } else {
            if (strReplaceAll.startsWith(this.mPrefix)) {
                strSubstring = strReplaceAll.substring(this.mPrefix.length());
            }
            return false;
        }
        try {
            int i = Integer.parseInt(this.mLowerBound);
            int i2 = Integer.parseInt(this.mUpperBound);
            int i3 = Integer.parseInt(strSubstring);
            return i3 <= i2 && i3 >= i;
        } catch (NumberFormatException e) {
            Log.e("PhoneNumberRange", "Invalid bounds or number.", e);
        }
    }
}
