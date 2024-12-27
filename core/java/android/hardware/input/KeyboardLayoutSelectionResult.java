package android.hardware.input;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
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
    public static final KeyboardLayoutSelectionResult FAILED =
            new KeyboardLayoutSelectionResult(null, 0);
    public static final Parcelable.Creator<KeyboardLayoutSelectionResult> CREATOR =
            new Parcelable.Creator<KeyboardLayoutSelectionResult>() { // from class:
                // android.hardware.input.KeyboardLayoutSelectionResult.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public KeyboardLayoutSelectionResult[] newArray(int size) {
                    return new KeyboardLayoutSelectionResult[size];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public KeyboardLayoutSelectionResult createFromParcel(Parcel in) {
                    return new KeyboardLayoutSelectionResult(in);
                }
            };

    @Retention(RetentionPolicy.SOURCE)
    public @interface LayoutSelectionCriteria {}

    public static String layoutSelectionCriteriaToString(int value) {
        switch (value) {
            case 0:
                return "LAYOUT_SELECTION_CRITERIA_UNSPECIFIED";
            case 1:
                return "LAYOUT_SELECTION_CRITERIA_USER";
            case 2:
                return "LAYOUT_SELECTION_CRITERIA_DEVICE";
            case 3:
                return "LAYOUT_SELECTION_CRITERIA_VIRTUAL_KEYBOARD";
            case 4:
                return "LAYOUT_SELECTION_CRITERIA_DEFAULT";
            default:
                return Integer.toHexString(value);
        }
    }

    public KeyboardLayoutSelectionResult(String layoutDescriptor, int selectionCriteria) {
        this.mLayoutDescriptor = layoutDescriptor;
        this.mSelectionCriteria = selectionCriteria;
        if (this.mSelectionCriteria != 0
                && this.mSelectionCriteria != 1
                && this.mSelectionCriteria != 2
                && this.mSelectionCriteria != 3
                && this.mSelectionCriteria != 4) {
            throw new IllegalArgumentException(
                    "selectionCriteria was "
                            + this.mSelectionCriteria
                            + " but must be one of: LAYOUT_SELECTION_CRITERIA_UNSPECIFIED(0),"
                            + " LAYOUT_SELECTION_CRITERIA_USER(1),"
                            + " LAYOUT_SELECTION_CRITERIA_DEVICE(2),"
                            + " LAYOUT_SELECTION_CRITERIA_VIRTUAL_KEYBOARD(3),"
                            + " LAYOUT_SELECTION_CRITERIA_DEFAULT(4"
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public String getLayoutDescriptor() {
        return this.mLayoutDescriptor;
    }

    public int getSelectionCriteria() {
        return this.mSelectionCriteria;
    }

    public String toString() {
        return "KeyboardLayoutSelectionResult { layoutDescriptor = "
                + this.mLayoutDescriptor
                + ", selectionCriteria = "
                + layoutSelectionCriteriaToString(this.mSelectionCriteria)
                + " }";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeyboardLayoutSelectionResult that = (KeyboardLayoutSelectionResult) o;
        if (Objects.equals(this.mLayoutDescriptor, that.mLayoutDescriptor)
                && this.mSelectionCriteria == that.mSelectionCriteria) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + Objects.hashCode(this.mLayoutDescriptor);
        return (_hash * 31) + this.mSelectionCriteria;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        byte flg = this.mLayoutDescriptor != null ? (byte) (0 | 1) : (byte) 0;
        dest.writeByte(flg);
        if (this.mLayoutDescriptor != null) {
            dest.writeString(this.mLayoutDescriptor);
        }
        dest.writeInt(this.mSelectionCriteria);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    KeyboardLayoutSelectionResult(Parcel in) {
        byte flg = in.readByte();
        String layoutDescriptor = (flg & 1) == 0 ? null : in.readString();
        int selectionCriteria = in.readInt();
        this.mLayoutDescriptor = layoutDescriptor;
        this.mSelectionCriteria = selectionCriteria;
        if (this.mSelectionCriteria != 0
                && this.mSelectionCriteria != 1
                && this.mSelectionCriteria != 2
                && this.mSelectionCriteria != 3
                && this.mSelectionCriteria != 4) {
            throw new IllegalArgumentException(
                    "selectionCriteria was "
                            + this.mSelectionCriteria
                            + " but must be one of: LAYOUT_SELECTION_CRITERIA_UNSPECIFIED(0),"
                            + " LAYOUT_SELECTION_CRITERIA_USER(1),"
                            + " LAYOUT_SELECTION_CRITERIA_DEVICE(2),"
                            + " LAYOUT_SELECTION_CRITERIA_VIRTUAL_KEYBOARD(3),"
                            + " LAYOUT_SELECTION_CRITERIA_DEFAULT(4"
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    @Deprecated
    private void __metadata() {}
}
