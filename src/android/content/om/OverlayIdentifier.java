package android.content.om;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class OverlayIdentifier implements Parcelable {
    public static final Parcelable.Creator<OverlayIdentifier> CREATOR = new Parcelable.Creator<OverlayIdentifier>() { // from class: android.content.om.OverlayIdentifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverlayIdentifier[] newArray(int i) {
            return new OverlayIdentifier[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverlayIdentifier createFromParcel(Parcel parcel) {
            return new OverlayIdentifier(parcel);
        }
    };
    private final String mOverlayName;
    private final String mPackageName;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public OverlayIdentifier(String str, String str2) {
        this.mPackageName = str;
        this.mOverlayName = str2;
    }

    public OverlayIdentifier(String str) {
        this.mPackageName = str;
        this.mOverlayName = null;
    }

    public String toString() {
        if (this.mOverlayName == null) {
            return this.mPackageName;
        }
        return this.mPackageName + ":" + this.mOverlayName;
    }

    public static OverlayIdentifier fromString(String str) {
        String[] strArrSplit = str.split(":", 2);
        if (strArrSplit.length == 2) {
            return new OverlayIdentifier(strArrSplit[0], strArrSplit[1]);
        }
        return new OverlayIdentifier(strArrSplit[0]);
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getOverlayName() {
        return this.mOverlayName;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            OverlayIdentifier overlayIdentifier = (OverlayIdentifier) obj;
            if (Objects.equals(this.mPackageName, overlayIdentifier.mPackageName) && Objects.equals(this.mOverlayName, overlayIdentifier.mOverlayName)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((Objects.hashCode(this.mPackageName) + 31) * 31) + Objects.hashCode(this.mOverlayName);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte b = this.mPackageName != null ? (byte) 1 : (byte) 0;
        if (this.mOverlayName != null) {
            b = (byte) (b | 2);
        }
        parcel.writeByte(b);
        String str = this.mPackageName;
        if (str != null) {
            parcel.writeString(str);
        }
        String str2 = this.mOverlayName;
        if (str2 != null) {
            parcel.writeString(str2);
        }
    }

    OverlayIdentifier(Parcel parcel) {
        byte b = parcel.readByte();
        String string = (b & 1) == 0 ? null : parcel.readString();
        String string2 = (b & 2) != 0 ? parcel.readString() : null;
        this.mPackageName = string;
        this.mOverlayName = string2;
    }
}
