package android.content.pm;

import android.content.res.ResourceId;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.Preconditions;

public final class SemSuspendDialogInfo implements Parcelable {
    public static final Parcelable.Creator<SemSuspendDialogInfo> CREATOR =
            new Parcelable.Creator<SemSuspendDialogInfo>() { // from class:
                // android.content.pm.SemSuspendDialogInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemSuspendDialogInfo createFromParcel(Parcel source) {
                    return new SemSuspendDialogInfo(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemSuspendDialogInfo[] newArray(int size) {
                    return new SemSuspendDialogInfo[size];
                }
            };
    static final int ID_NULL = 0;
    private final String mDialogMessage;
    private final int mDialogMessageResId;
    private final int mNeutralButtonTextResId;
    private final int mTitleResId;

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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int parcelableFlags) {
        dest.writeInt(this.mTitleResId);
        dest.writeInt(this.mDialogMessageResId);
        dest.writeString(this.mDialogMessage);
        dest.writeInt(this.mNeutralButtonTextResId);
    }

    private SemSuspendDialogInfo(Parcel source) {
        this.mTitleResId = source.readInt();
        this.mDialogMessageResId = source.readInt();
        this.mDialogMessage = source.readString();
        this.mNeutralButtonTextResId = source.readInt();
    }

    SemSuspendDialogInfo(Builder b) {
        this.mTitleResId = b.mTitleResId;
        this.mDialogMessageResId = b.mDialogMessageResId;
        this.mDialogMessage = this.mDialogMessageResId == 0 ? b.mDialogMessage : null;
        this.mNeutralButtonTextResId = b.mNeutralButtonTextResId;
    }

    public static final class Builder {
        private int mTitleResId = 0;
        private int mDialogMessageResId = 0;
        private String mDialogMessage = null;
        private int mNeutralButtonTextResId = 0;

        public Builder setTitle(int resId) {
            Preconditions.checkArgument(ResourceId.isValid(resId), "Invalid resource id provided");
            this.mTitleResId = resId;
            return this;
        }

        public Builder setMessage(int resId) {
            Preconditions.checkArgument(ResourceId.isValid(resId), "Invalid resource id provided");
            this.mDialogMessageResId = resId;
            return this;
        }

        public Builder setMessage(String message) {
            Preconditions.checkStringNotEmpty(message, "Message cannot be null or empty");
            this.mDialogMessage = message;
            return this;
        }

        public Builder setNeutralButtonText(int resId) {
            Preconditions.checkArgument(ResourceId.isValid(resId), "Invalid resource id provided");
            this.mNeutralButtonTextResId = resId;
            return this;
        }

        public SemSuspendDialogInfo build() {
            return new SemSuspendDialogInfo(this);
        }
    }
}
