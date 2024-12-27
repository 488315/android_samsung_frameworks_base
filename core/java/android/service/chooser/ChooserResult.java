package android.service.chooser;

import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

public final class ChooserResult implements Parcelable {
    public static final int CHOOSER_RESULT_COPY = 1;
    public static final int CHOOSER_RESULT_EDIT = 2;
    public static final int CHOOSER_RESULT_SELECTED_COMPONENT = 0;
    public static final int CHOOSER_RESULT_UNKNOWN = -1;
    public static final Parcelable.Creator<ChooserResult> CREATOR =
            new Parcelable.Creator<
                    ChooserResult>() { // from class: android.service.chooser.ChooserResult.1
                @Override // android.os.Parcelable.Creator
                public ChooserResult createFromParcel(Parcel source) {
                    return new ChooserResult(source);
                }

                @Override // android.os.Parcelable.Creator
                public ChooserResult[] newArray(int size) {
                    return new ChooserResult[0];
                }
            };
    public static final long SEND_CHOOSER_RESULT = 263474465;
    private final boolean mIsShortcut;
    private final ComponentName mSelectedComponent;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResultType {}

    private ChooserResult(Parcel source) {
        this.mType = source.readInt();
        this.mSelectedComponent = ComponentName.readFromParcel(source);
        this.mIsShortcut = source.readBoolean();
    }

    public ChooserResult(int type, ComponentName componentName, boolean isShortcut) {
        this.mType = type;
        this.mSelectedComponent = componentName;
        this.mIsShortcut = isShortcut;
    }

    public int getType() {
        return this.mType;
    }

    public ComponentName getSelectedComponent() {
        return this.mSelectedComponent;
    }

    public boolean isShortcut() {
        return this.mIsShortcut;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mType);
        ComponentName.writeToParcel(this.mSelectedComponent, dest);
        dest.writeBoolean(this.mIsShortcut);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChooserResult that = (ChooserResult) o;
        if (this.mType == that.mType
                && this.mIsShortcut == that.mIsShortcut
                && Objects.equals(this.mSelectedComponent, that.mSelectedComponent)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mType),
                this.mSelectedComponent,
                Boolean.valueOf(this.mIsShortcut));
    }
}
