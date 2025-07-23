package android.telecom;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class Voicemail implements Parcelable {
    public static final Parcelable.Creator<Voicemail> CREATOR = new Parcelable.Creator<Voicemail>() { // from class: android.telecom.Voicemail.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Voicemail createFromParcel(Parcel parcel) {
            return new Voicemail(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Voicemail[] newArray(int i) {
            return new Voicemail[i];
        }
    };
    private final Long mDuration;
    private final Boolean mHasContent;
    private final Long mId;
    private final Boolean mIsRead;
    private final String mNumber;
    private final PhoneAccountHandle mPhoneAccount;
    private final String mProviderData;
    private final String mSource;
    private final Long mTimestamp;
    private final String mTranscription;
    private final Uri mUri;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private Voicemail(Long l, String str, PhoneAccountHandle phoneAccountHandle, Long l2, Long l3, String str2, String str3, Uri uri, Boolean bool, Boolean bool2, String str4) {
        this.mTimestamp = l;
        this.mNumber = str;
        this.mPhoneAccount = phoneAccountHandle;
        this.mId = l2;
        this.mDuration = l3;
        this.mSource = str2;
        this.mProviderData = str3;
        this.mUri = uri;
        this.mIsRead = bool;
        this.mHasContent = bool2;
        this.mTranscription = str4;
    }

    public static Builder createForInsertion(long j, String str) {
        return new Builder().setNumber(str).setTimestamp(j);
    }

    public static Builder createForUpdate(long j, String str) {
        return new Builder().setId(j).setSourceData(str);
    }

    public static class Builder {
        private Long mBuilderDuration;
        private boolean mBuilderHasContent;
        private Long mBuilderId;
        private Boolean mBuilderIsRead;
        private String mBuilderNumber;
        private PhoneAccountHandle mBuilderPhoneAccount;
        private String mBuilderSourceData;
        private String mBuilderSourcePackage;
        private Long mBuilderTimestamp;
        private String mBuilderTranscription;
        private Uri mBuilderUri;

        private Builder() {
        }

        public Builder setNumber(String str) {
            this.mBuilderNumber = str;
            return this;
        }

        public Builder setTimestamp(long j) {
            this.mBuilderTimestamp = Long.valueOf(j);
            return this;
        }

        public Builder setPhoneAccount(PhoneAccountHandle phoneAccountHandle) {
            this.mBuilderPhoneAccount = phoneAccountHandle;
            return this;
        }

        public Builder setId(long j) {
            this.mBuilderId = Long.valueOf(j);
            return this;
        }

        public Builder setDuration(long j) {
            this.mBuilderDuration = Long.valueOf(j);
            return this;
        }

        public Builder setSourcePackage(String str) {
            this.mBuilderSourcePackage = str;
            return this;
        }

        public Builder setSourceData(String str) {
            this.mBuilderSourceData = str;
            return this;
        }

        public Builder setUri(Uri uri) {
            this.mBuilderUri = uri;
            return this;
        }

        public Builder setIsRead(boolean z) {
            this.mBuilderIsRead = Boolean.valueOf(z);
            return this;
        }

        public Builder setHasContent(boolean z) {
            this.mBuilderHasContent = z;
            return this;
        }

        public Builder setTranscription(String str) {
            this.mBuilderTranscription = str;
            return this;
        }

        public Voicemail build() {
            Long l = this.mBuilderId;
            this.mBuilderId = Long.valueOf(l == null ? -1L : l.longValue());
            Long l2 = this.mBuilderTimestamp;
            this.mBuilderTimestamp = Long.valueOf(l2 == null ? 0L : l2.longValue());
            Long l3 = this.mBuilderDuration;
            this.mBuilderDuration = Long.valueOf(l3 != null ? l3.longValue() : 0L);
            Boolean bool = this.mBuilderIsRead;
            this.mBuilderIsRead = Boolean.valueOf(bool == null ? false : bool.booleanValue());
            return new Voicemail(this.mBuilderTimestamp, this.mBuilderNumber, this.mBuilderPhoneAccount, this.mBuilderId, this.mBuilderDuration, this.mBuilderSourcePackage, this.mBuilderSourceData, this.mBuilderUri, this.mBuilderIsRead, Boolean.valueOf(this.mBuilderHasContent), this.mBuilderTranscription);
        }
    }

    public long getId() {
        return this.mId.longValue();
    }

    public String getNumber() {
        return this.mNumber;
    }

    public PhoneAccountHandle getPhoneAccount() {
        return this.mPhoneAccount;
    }

    public long getTimestampMillis() {
        return this.mTimestamp.longValue();
    }

    public long getDuration() {
        return this.mDuration.longValue();
    }

    public String getSourcePackage() {
        return this.mSource;
    }

    public String getSourceData() {
        return this.mProviderData;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public boolean isRead() {
        return this.mIsRead.booleanValue();
    }

    public boolean hasContent() {
        return this.mHasContent.booleanValue();
    }

    public String getTranscription() {
        return this.mTranscription;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mTimestamp.longValue());
        parcel.writeCharSequence(this.mNumber);
        if (this.mPhoneAccount == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mPhoneAccount.writeToParcel(parcel, i);
        }
        parcel.writeLong(this.mId.longValue());
        parcel.writeLong(this.mDuration.longValue());
        parcel.writeCharSequence(this.mSource);
        parcel.writeCharSequence(this.mProviderData);
        if (this.mUri == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mUri.writeToParcel(parcel, i);
        }
        if (this.mIsRead.booleanValue()) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
        }
        if (this.mHasContent.booleanValue()) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeCharSequence(this.mTranscription);
    }

    private Voicemail(Parcel parcel) {
        this.mTimestamp = Long.valueOf(parcel.readLong());
        this.mNumber = (String) parcel.readCharSequence();
        if (parcel.readInt() > 0) {
            this.mPhoneAccount = PhoneAccountHandle.CREATOR.createFromParcel(parcel);
        } else {
            this.mPhoneAccount = null;
        }
        this.mId = Long.valueOf(parcel.readLong());
        this.mDuration = Long.valueOf(parcel.readLong());
        this.mSource = (String) parcel.readCharSequence();
        this.mProviderData = (String) parcel.readCharSequence();
        if (parcel.readInt() > 0) {
            this.mUri = Uri.CREATOR.createFromParcel(parcel);
        } else {
            this.mUri = null;
        }
        this.mIsRead = Boolean.valueOf(parcel.readInt() > 0);
        this.mHasContent = Boolean.valueOf(parcel.readInt() > 0);
        this.mTranscription = (String) parcel.readCharSequence();
    }
}
