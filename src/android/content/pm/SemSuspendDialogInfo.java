package android.content.pm;

import android.content.res.ResourceId;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

/* loaded from: classes.dex */
public final class SemSuspendDialogInfo implements Parcelable {
    public static final Parcelable.Creator<SemSuspendDialogInfo> CREATOR = new Parcelable.Creator<SemSuspendDialogInfo>() { // from class: android.content.pm.SemSuspendDialogInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSuspendDialogInfo createFromParcel(Parcel parcel) {
            return new SemSuspendDialogInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSuspendDialogInfo[] newArray(int i) {
            return new SemSuspendDialogInfo[i];
        }
    };
    static final int ID_NULL = 0;
    private final String mDialogMessage;
    private final int mDialogMessageResId;
    private final int mNeutralButtonTextResId;
    private final int mTitleResId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    int getTitleResId() {
        return this.mTitleResId;
    }

    int getDialogMessageResId() {
        return this.mDialogMessageResId;
    }

    String getDialogMessage() {
        return this.mDialogMessage;
    }

    int getNeutralButtonTextResId() {
        return this.mNeutralButtonTextResId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mTitleResId);
        parcel.writeInt(this.mDialogMessageResId);
        parcel.writeString(this.mDialogMessage);
        parcel.writeInt(this.mNeutralButtonTextResId);
    }

    private SemSuspendDialogInfo(Parcel parcel) {
        this.mTitleResId = parcel.readInt();
        this.mDialogMessageResId = parcel.readInt();
        this.mDialogMessage = parcel.readString();
        this.mNeutralButtonTextResId = parcel.readInt();
    }

    SemSuspendDialogInfo(Builder builder) {
        this.mTitleResId = builder.mTitleResId;
        int i = builder.mDialogMessageResId;
        this.mDialogMessageResId = i;
        this.mDialogMessage = i == 0 ? builder.mDialogMessage : null;
        this.mNeutralButtonTextResId = builder.mNeutralButtonTextResId;
    }

    public static final class Builder {
        private int mTitleResId = 0;
        private int mDialogMessageResId = 0;
        private String mDialogMessage = null;
        private int mNeutralButtonTextResId = 0;

        public Builder setTitle(int i) {
            Preconditions.checkArgument(ResourceId.isValid(i), "Invalid resource id provided");
            this.mTitleResId = i;
            return this;
        }

        public Builder setMessage(int i) {
            Preconditions.checkArgument(ResourceId.isValid(i), "Invalid resource id provided");
            this.mDialogMessageResId = i;
            return this;
        }

        public Builder setMessage(String str) {
            Preconditions.checkStringNotEmpty(str, "Message cannot be null or empty");
            this.mDialogMessage = str;
            return this;
        }

        public Builder setNeutralButtonText(int i) {
            Preconditions.checkArgument(ResourceId.isValid(i), "Invalid resource id provided");
            this.mNeutralButtonTextResId = i;
            return this;
        }

        public SemSuspendDialogInfo build() {
            return new SemSuspendDialogInfo(this);
        }
    }
}
