package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.PhoneNumberUtils;
import android.text.format.DateFormat;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Random;

/* loaded from: classes.dex */
public class VerifierDeviceIdentity implements Parcelable {
    private static final int GROUP_SIZE = 4;
    private static final int LONG_SIZE = 13;
    private static final char SEPARATOR = '-';
    private final long mIdentity;
    private final String mIdentityString;
    private static final char[] ENCODE = {DateFormat.CAPITAL_AM_PM, 'B', 'C', 'D', DateFormat.DAY, 'F', 'G', 'H', 'I', 'J', 'K', DateFormat.STANDALONE_MONTH, DateFormat.MONTH, PhoneNumberUtils.WILD, 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7'};
    public static final Parcelable.Creator<VerifierDeviceIdentity> CREATOR = new Parcelable.Creator<VerifierDeviceIdentity>() { // from class: android.content.pm.VerifierDeviceIdentity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VerifierDeviceIdentity createFromParcel(Parcel parcel) {
            return new VerifierDeviceIdentity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VerifierDeviceIdentity[] newArray(int i) {
            return new VerifierDeviceIdentity[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VerifierDeviceIdentity(long j) {
        this.mIdentity = j;
        this.mIdentityString = encodeBase32(j);
    }

    private VerifierDeviceIdentity(Parcel parcel) {
        long readLong = parcel.readLong();
        this.mIdentity = readLong;
        this.mIdentityString = encodeBase32(readLong);
    }

    public static VerifierDeviceIdentity generate() {
        return generate(new SecureRandom());
    }

    public static VerifierDeviceIdentity generate(Random random) {
        return new VerifierDeviceIdentity(random.nextLong());
    }

    private static final String encodeBase32(long j) {
        char[] cArr = ENCODE;
        int i = 16;
        char[] cArr2 = new char[16];
        for (int i2 = 0; i2 < 13; i2++) {
            if (i2 > 0 && i2 % 4 == 1) {
                i--;
                cArr2[i] = SEPARATOR;
            }
            int i3 = (int) (31 & j);
            j >>>= 5;
            i--;
            cArr2[i] = cArr[i3];
        }
        return String.valueOf(cArr2);
    }

    private static final long decodeBase32(byte[] bArr) throws IllegalArgumentException {
        int i;
        long j = 0;
        int i2 = 0;
        for (byte b : bArr) {
            if (65 <= b && b <= 90) {
                i = b - 65;
            } else if (50 <= b && b <= 55) {
                i = b - 24;
            } else if (b == 45) {
                continue;
            } else if (97 <= b && b <= 122) {
                i = b - 97;
            } else if (b == 48) {
                i = 14;
            } else {
                if (b != 49) {
                    throw new IllegalArgumentException("base base-32 character: " + ((int) b));
                }
                i = 8;
            }
            j = (j << 5) | i;
            i2++;
            if (i2 == 1) {
                if ((i & 15) != i) {
                    throw new IllegalArgumentException("illegal start character; will overflow");
                }
            } else if (i2 > 13) {
                throw new IllegalArgumentException("too long; should have 13 characters");
            }
        }
        if (i2 == 13) {
            return j;
        }
        throw new IllegalArgumentException("too short; should have 13 characters");
    }

    public int hashCode() {
        return (int) this.mIdentity;
    }

    public boolean equals(Object obj) {
        return (obj instanceof VerifierDeviceIdentity) && this.mIdentity == ((VerifierDeviceIdentity) obj).mIdentity;
    }

    public String toString() {
        return this.mIdentityString;
    }

    public static VerifierDeviceIdentity parse(String str) throws IllegalArgumentException {
        try {
            return new VerifierDeviceIdentity(decodeBase32(str.getBytes("US-ASCII")));
        } catch (UnsupportedEncodingException unused) {
            throw new IllegalArgumentException("bad base-32 characters in input");
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mIdentity);
    }
}
