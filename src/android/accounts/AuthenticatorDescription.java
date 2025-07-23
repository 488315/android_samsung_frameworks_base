package android.accounts;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class AuthenticatorDescription implements Parcelable {
    public static final Parcelable.Creator<AuthenticatorDescription> CREATOR = new Parcelable.Creator<AuthenticatorDescription>() { // from class: android.accounts.AuthenticatorDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticatorDescription createFromParcel(Parcel parcel) {
            return new AuthenticatorDescription(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthenticatorDescription[] newArray(int i) {
            return new AuthenticatorDescription[i];
        }
    };
    public final int accountPreferencesId;
    public final boolean customTokens;
    public final int iconId;
    public final int labelId;
    public final String packageName;
    public final int smallIconId;
    public final String type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AuthenticatorDescription(String str, String str2, int i, int i2, int i3, int i4, boolean z) {
        if (str == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
        if (str2 == null) {
            throw new IllegalArgumentException("packageName cannot be null");
        }
        this.type = str;
        this.packageName = str2;
        this.labelId = i;
        this.iconId = i2;
        this.smallIconId = i3;
        this.accountPreferencesId = i4;
        this.customTokens = z;
    }

    public AuthenticatorDescription(String str, String str2, int i, int i2, int i3, int i4) {
        this(str, str2, i, i2, i3, i4, false);
    }

    public static AuthenticatorDescription newKey(String str) {
        if (str == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
        return new AuthenticatorDescription(str);
    }

    private AuthenticatorDescription(String str) {
        this.type = str;
        this.packageName = null;
        this.labelId = 0;
        this.iconId = 0;
        this.smallIconId = 0;
        this.accountPreferencesId = 0;
        this.customTokens = false;
    }

    private AuthenticatorDescription(Parcel parcel) {
        this.type = parcel.readString();
        this.packageName = parcel.readString();
        this.labelId = parcel.readInt();
        this.iconId = parcel.readInt();
        this.smallIconId = parcel.readInt();
        this.accountPreferencesId = parcel.readInt();
        this.customTokens = parcel.readByte() == 1;
    }

    public int hashCode() {
        return this.type.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AuthenticatorDescription) {
            return this.type.equals(((AuthenticatorDescription) obj).type);
        }
        return false;
    }

    public String toString() {
        return "AuthenticatorDescription {type=" + this.type + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.type);
        parcel.writeString(this.packageName);
        parcel.writeInt(this.labelId);
        parcel.writeInt(this.iconId);
        parcel.writeInt(this.smallIconId);
        parcel.writeInt(this.accountPreferencesId);
        parcel.writeByte(this.customTokens ? (byte) 1 : (byte) 0);
    }
}
