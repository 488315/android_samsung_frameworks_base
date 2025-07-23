package android.hardware.input;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class KeyboardLayoutSelectionResult implements Parcelable {
    public static final int LAYOUT_SELECTION_CRITERIA_DEFAULT = 4;
    public static final int LAYOUT_SELECTION_CRITERIA_DEVICE = 2;
    public static final int LAYOUT_SELECTION_CRITERIA_UNSPECIFIED = 0;
    public static final int LAYOUT_SELECTION_CRITERIA_USER = 1;
    public static final int LAYOUT_SELECTION_CRITERIA_VIRTUAL_KEYBOARD = 3;
    private final String mLayoutDescriptor;
    private final int mSelectionCriteria;
    public static final KeyboardLayoutSelectionResult FAILED = new KeyboardLayoutSelectionResult(null, 0);
    public static final Parcelable.Creator<KeyboardLayoutSelectionResult> CREATOR = new Parcelable.Creator<KeyboardLayoutSelectionResult>() { // from class: android.hardware.input.KeyboardLayoutSelectionResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyboardLayoutSelectionResult[] newArray(int i) {
            return new KeyboardLayoutSelectionResult[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyboardLayoutSelectionResult createFromParcel(Parcel parcel) {
            return new KeyboardLayoutSelectionResult(parcel);
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface LayoutSelectionCriteria {
    }

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String layoutSelectionCriteriaToString(int i) {
        if (i == 0) {
            return "LAYOUT_SELECTION_CRITERIA_UNSPECIFIED";
        }
        if (i == 1) {
            return "LAYOUT_SELECTION_CRITERIA_USER";
        }
        if (i == 2) {
            return "LAYOUT_SELECTION_CRITERIA_DEVICE";
        }
        if (i == 3) {
            return "LAYOUT_SELECTION_CRITERIA_VIRTUAL_KEYBOARD";
        }
        if (i == 4) {
            return "LAYOUT_SELECTION_CRITERIA_DEFAULT";
        }
        return Integer.toHexString(i);
    }

    public KeyboardLayoutSelectionResult(String str, int i) {
        this.mLayoutDescriptor = str;
        this.mSelectionCriteria = i;
        if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4) {
            return;
        }
        throw new IllegalArgumentException("selectionCriteria was " + i + " but must be one of: LAYOUT_SELECTION_CRITERIA_UNSPECIFIED(0), LAYOUT_SELECTION_CRITERIA_USER(1), LAYOUT_SELECTION_CRITERIA_DEVICE(2), LAYOUT_SELECTION_CRITERIA_VIRTUAL_KEYBOARD(3), LAYOUT_SELECTION_CRITERIA_DEFAULT(4)");
    }

    public String getLayoutDescriptor() {
        return this.mLayoutDescriptor;
    }

    public int getSelectionCriteria() {
        return this.mSelectionCriteria;
    }

    public String toString() {
        return "KeyboardLayoutSelectionResult { layoutDescriptor = " + this.mLayoutDescriptor + ", selectionCriteria = " + layoutSelectionCriteriaToString(this.mSelectionCriteria) + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            KeyboardLayoutSelectionResult keyboardLayoutSelectionResult = (KeyboardLayoutSelectionResult) obj;
            if (Objects.equals(this.mLayoutDescriptor, keyboardLayoutSelectionResult.mLayoutDescriptor) && this.mSelectionCriteria == keyboardLayoutSelectionResult.mSelectionCriteria) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((Objects.hashCode(this.mLayoutDescriptor) + 31) * 31) + this.mSelectionCriteria;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mLayoutDescriptor != null ? (byte) 1 : (byte) 0);
        String str = this.mLayoutDescriptor;
        if (str != null) {
            parcel.writeString(str);
        }
        parcel.writeInt(this.mSelectionCriteria);
    }

    KeyboardLayoutSelectionResult(Parcel parcel) {
        String readString = (parcel.readByte() & 1) == 0 ? null : parcel.readString();
        int readInt = parcel.readInt();
        this.mLayoutDescriptor = readString;
        this.mSelectionCriteria = readInt;
        if (readInt == 0 || readInt == 1 || readInt == 2 || readInt == 3 || readInt == 4) {
            return;
        }
        throw new IllegalArgumentException("selectionCriteria was " + readInt + " but must be one of: LAYOUT_SELECTION_CRITERIA_UNSPECIFIED(0), LAYOUT_SELECTION_CRITERIA_USER(1), LAYOUT_SELECTION_CRITERIA_DEVICE(2), LAYOUT_SELECTION_CRITERIA_VIRTUAL_KEYBOARD(3), LAYOUT_SELECTION_CRITERIA_DEFAULT(4)");
    }
}
