package android.telephony;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class VisualVoicemailSmsFilterSettings implements Parcelable {
    public static final String DEFAULT_CLIENT_PREFIX = "//VVM";
    public static final int DEFAULT_DESTINATION_PORT = -1;
    public static final int DESTINATION_PORT_ANY = -1;
    public static final int DESTINATION_PORT_DATA_SMS = -2;
    public static final int MAX_LIST_SIZE = 100;
    public static final int MAX_STRING_LENGTH = 256;
    public final String clientPrefix;
    public final int destinationPort;
    public final List<String> originatingNumbers;
    public final String packageName;
    public static final List<String> DEFAULT_ORIGINATING_NUMBERS = Collections.EMPTY_LIST;
    public static final Parcelable.Creator<VisualVoicemailSmsFilterSettings> CREATOR = new Parcelable.Creator<VisualVoicemailSmsFilterSettings>() { // from class: android.telephony.VisualVoicemailSmsFilterSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisualVoicemailSmsFilterSettings createFromParcel(Parcel parcel) {
            Builder builder = new Builder();
            builder.setClientPrefix(parcel.readString());
            builder.setOriginatingNumbers(parcel.createStringArrayList());
            builder.setDestinationPort(parcel.readInt());
            builder.setPackageName(parcel.readString());
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisualVoicemailSmsFilterSettings[] newArray(int i) {
            return new VisualVoicemailSmsFilterSettings[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class Builder {
        private String mPackageName;
        private String mClientPrefix = VisualVoicemailSmsFilterSettings.DEFAULT_CLIENT_PREFIX;
        private List<String> mOriginatingNumbers = VisualVoicemailSmsFilterSettings.DEFAULT_ORIGINATING_NUMBERS;
        private int mDestinationPort = -1;

        public VisualVoicemailSmsFilterSettings build() {
            return new VisualVoicemailSmsFilterSettings(this);
        }

        public Builder setClientPrefix(String str) {
            if (str == null) {
                throw new IllegalArgumentException("Client prefix cannot be null");
            }
            if (str.length() > 256) {
                throw new IllegalArgumentException("Client prefix cannot be greater than 256 characters");
            }
            this.mClientPrefix = str;
            return this;
        }

        public Builder setOriginatingNumbers(List<String> list) {
            if (list == null) {
                throw new IllegalArgumentException("Originating numbers cannot be null");
            }
            if (list.size() > 100) {
                throw new IllegalArgumentException("The originatingNumbers list size cannot be greater than 256 elements");
            }
            for (String str : list) {
                if (str != null && str.length() > 256) {
                    throw new IllegalArgumentException("Numbers within the originatingNumbers list cannot be greater than256 characters");
                }
            }
            this.mOriginatingNumbers = list;
            return this;
        }

        public Builder setDestinationPort(int i) {
            this.mDestinationPort = i;
            return this;
        }

        public Builder setPackageName(String str) {
            this.mPackageName = str;
            return this;
        }
    }

    private VisualVoicemailSmsFilterSettings(Builder builder) {
        this.clientPrefix = builder.mClientPrefix;
        this.originatingNumbers = builder.mOriginatingNumbers;
        this.destinationPort = builder.mDestinationPort;
        this.packageName = builder.mPackageName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.clientPrefix);
        parcel.writeStringList(this.originatingNumbers);
        parcel.writeInt(this.destinationPort);
        parcel.writeString(this.packageName);
    }

    public String toString() {
        return "[VisualVoicemailSmsFilterSettings clientPrefix=" + this.clientPrefix + ", originatingNumbers=" + this.originatingNumbers + ", destinationPort=" + this.destinationPort + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
