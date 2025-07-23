package android.content.om;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class OverlayConstraint implements Parcelable {
    public static final Parcelable.Creator<OverlayConstraint> CREATOR = new Parcelable.Creator<OverlayConstraint>() { // from class: android.content.om.OverlayConstraint.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverlayConstraint createFromParcel(Parcel parcel) {
            return new OverlayConstraint(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverlayConstraint[] newArray(int i) {
            return new OverlayConstraint[i];
        }
    };
    public static final int TYPE_DEVICE_ID = 1;
    public static final int TYPE_DISPLAY_ID = 0;
    private final int mType;
    private final int mValue;

    @Retention(RetentionPolicy.SOURCE)
    @interface ConstraintType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public OverlayConstraint(int i, int i2) {
        if (i != 1 && i != 0) {
            throw new IllegalArgumentException("Type must be either TYPE_DISPLAY_ID or TYPE_DEVICE_ID");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("Value must be greater than 0");
        }
        this.mType = i;
        this.mValue = i2;
    }

    private OverlayConstraint(Parcel parcel) {
        this(parcel.readInt(), parcel.readInt());
    }

    public int getType() {
        return this.mType;
    }

    public int getValue() {
        return this.mValue;
    }

    public String toString() {
        return "{type: " + typeToString(this.mType) + ", value: " + this.mValue + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OverlayConstraint) {
            OverlayConstraint overlayConstraint = (OverlayConstraint) obj;
            if (this.mType == overlayConstraint.mType && this.mValue == overlayConstraint.mValue) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mType), Integer.valueOf(this.mValue));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mValue);
    }

    public static String constraintsToString(List<OverlayConstraint> list) {
        if (list == null || list.isEmpty()) {
            return "None";
        }
        return NavigationBarInflaterView.SIZE_MOD_START + TextUtils.join(",", list) + NavigationBarInflaterView.SIZE_MOD_END;
    }

    private static String typeToString(int i) {
        return i == 1 ? "DEVICE_ID" : "DISPLAY_ID";
    }
}
