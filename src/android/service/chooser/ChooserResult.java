package android.service.chooser;

import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class ChooserResult implements Parcelable {
    public static final int CHOOSER_RESULT_COPY = 1;
    public static final int CHOOSER_RESULT_EDIT = 2;
    public static final int CHOOSER_RESULT_SELECTED_COMPONENT = 0;
    public static final int CHOOSER_RESULT_UNKNOWN = -1;
    public static final Parcelable.Creator<ChooserResult> CREATOR = new Parcelable.Creator<ChooserResult>() { // from class: android.service.chooser.ChooserResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ChooserResult createFromParcel(Parcel parcel) {
            return new ChooserResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ChooserResult[] newArray(int i) {
            return new ChooserResult[0];
        }
    };
    public static final long SEND_CHOOSER_RESULT = 263474465;
    private final boolean mIsShortcut;
    private final ComponentName mSelectedComponent;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResultType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ChooserResult(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mSelectedComponent = ComponentName.readFromParcel(parcel);
        this.mIsShortcut = parcel.readBoolean();
    }

    public ChooserResult(int i, ComponentName componentName, boolean z) {
        this.mType = i;
        this.mSelectedComponent = componentName;
        this.mIsShortcut = z;
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        ComponentName.writeToParcel(this.mSelectedComponent, parcel);
        parcel.writeBoolean(this.mIsShortcut);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ChooserResult chooserResult = (ChooserResult) obj;
            if (this.mType == chooserResult.mType && this.mIsShortcut == chooserResult.mIsShortcut && Objects.equals(this.mSelectedComponent, chooserResult.mSelectedComponent)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mType), this.mSelectedComponent, Boolean.valueOf(this.mIsShortcut));
    }
}
