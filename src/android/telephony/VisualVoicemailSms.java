package android.telephony;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.PhoneAccountHandle;

/* loaded from: classes4.dex */
public final class VisualVoicemailSms implements Parcelable {
    public static final Parcelable.Creator<VisualVoicemailSms> CREATOR = new Parcelable.Creator<VisualVoicemailSms>() { // from class: android.telephony.VisualVoicemailSms.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisualVoicemailSms createFromParcel(Parcel parcel) {
            return new Builder().setPhoneAccountHandle((PhoneAccountHandle) parcel.readParcelable(null, PhoneAccountHandle.class)).setPrefix(parcel.readString()).setFields(parcel.readBundle()).setMessageBody(parcel.readString()).build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisualVoicemailSms[] newArray(int i) {
            return new VisualVoicemailSms[i];
        }
    };
    private final Bundle mFields;
    private final String mMessageBody;
    private final PhoneAccountHandle mPhoneAccountHandle;
    private final String mPrefix;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    VisualVoicemailSms(Builder builder) {
        this.mPhoneAccountHandle = builder.mPhoneAccountHandle;
        this.mPrefix = builder.mPrefix;
        this.mFields = builder.mFields;
        this.mMessageBody = builder.mMessageBody;
    }

    public PhoneAccountHandle getPhoneAccountHandle() {
        return this.mPhoneAccountHandle;
    }

    public String getPrefix() {
        return this.mPrefix;
    }

    public Bundle getFields() {
        return this.mFields;
    }

    public String getMessageBody() {
        return this.mMessageBody;
    }

    public static class Builder {
        private Bundle mFields;
        private String mMessageBody;
        private PhoneAccountHandle mPhoneAccountHandle;
        private String mPrefix;

        public VisualVoicemailSms build() {
            return new VisualVoicemailSms(this);
        }

        public Builder setPhoneAccountHandle(PhoneAccountHandle phoneAccountHandle) {
            this.mPhoneAccountHandle = phoneAccountHandle;
            return this;
        }

        public Builder setPrefix(String str) {
            this.mPrefix = str;
            return this;
        }

        public Builder setFields(Bundle bundle) {
            this.mFields = bundle;
            return this;
        }

        public Builder setMessageBody(String str) {
            this.mMessageBody = str;
            return this;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(getPhoneAccountHandle(), i);
        parcel.writeString(getPrefix());
        parcel.writeBundle(getFields());
        parcel.writeString(getMessageBody());
    }
}
