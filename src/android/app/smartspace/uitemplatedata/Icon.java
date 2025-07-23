package android.app.smartspace.uitemplatedata;

import android.annotation.SystemApi;
import android.app.smartspace.SmartspaceUtils;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class Icon implements Parcelable {
    public static final Parcelable.Creator<Icon> CREATOR = new Parcelable.Creator<Icon>() { // from class: android.app.smartspace.uitemplatedata.Icon.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Icon createFromParcel(Parcel parcel) {
            return new Icon(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Icon[] newArray(int i) {
            return new Icon[i];
        }
    };
    private final CharSequence mContentDescription;
    private final android.graphics.drawable.Icon mIcon;
    private final boolean mShouldTint;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    Icon(Parcel parcel) {
        this.mIcon = (android.graphics.drawable.Icon) parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
        this.mContentDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mShouldTint = parcel.readBoolean();
    }

    private Icon(android.graphics.drawable.Icon icon, CharSequence charSequence, boolean z) {
        this.mIcon = icon;
        this.mContentDescription = charSequence;
        this.mShouldTint = z;
    }

    public android.graphics.drawable.Icon getIcon() {
        return this.mIcon;
    }

    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public boolean shouldTint() {
        return this.mShouldTint;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Icon)) {
            return false;
        }
        Icon icon = (Icon) obj;
        return this.mIcon.toString().equals(icon.mIcon.toString()) && SmartspaceUtils.isEqual(this.mContentDescription, icon.mContentDescription) && this.mShouldTint == icon.mShouldTint;
    }

    public int hashCode() {
        return Objects.hash(this.mIcon.toString(), this.mContentDescription, Boolean.valueOf(this.mShouldTint));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mIcon, i);
        TextUtils.writeToParcel(this.mContentDescription, parcel, i);
        parcel.writeBoolean(this.mShouldTint);
    }

    public String toString() {
        return "SmartspaceIcon{mIcon=" + this.mIcon + ", mContentDescription=" + ((Object) this.mContentDescription) + ", mShouldTint=" + this.mShouldTint + '}';
    }

    @SystemApi
    public static final class Builder {
        private CharSequence mContentDescription;
        private android.graphics.drawable.Icon mIcon;
        private boolean mShouldTint = true;

        public Builder(android.graphics.drawable.Icon icon) {
            this.mIcon = (android.graphics.drawable.Icon) Objects.requireNonNull(icon);
        }

        public Builder setContentDescription(CharSequence charSequence) {
            this.mContentDescription = charSequence;
            return this;
        }

        public Builder setShouldTint(boolean z) {
            this.mShouldTint = z;
            return this;
        }

        public Icon build() {
            this.mIcon.convertToAshmem();
            return new Icon(this.mIcon, this.mContentDescription, this.mShouldTint);
        }
    }
}
