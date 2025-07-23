package android.telephony.ims;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SystemApi
/* loaded from: classes4.dex */
public final class RcsContactPresenceTuple implements Parcelable {
    public static final Parcelable.Creator<RcsContactPresenceTuple> CREATOR = new Parcelable.Creator<RcsContactPresenceTuple>() { // from class: android.telephony.ims.RcsContactPresenceTuple.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RcsContactPresenceTuple createFromParcel(Parcel parcel) {
            return new RcsContactPresenceTuple(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RcsContactPresenceTuple[] newArray(int i) {
            return new RcsContactPresenceTuple[i];
        }
    };
    private static final String LOG_TAG = "RcsContactPresenceTuple";
    public static final String SERVICE_ID_CALL_COMPOSER = "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.gsma.callcomposer";
    public static final String SERVICE_ID_CHATBOT = "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.chatbot";
    public static final String SERVICE_ID_CHATBOT_ROLE = "org.gsma.rcs.isbot";
    public static final String SERVICE_ID_CHATBOT_STANDALONE = " org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.chatbot.sa";
    public static final String SERVICE_ID_CHAT_V1 = "org.openmobilealliance:IM-session";
    public static final String SERVICE_ID_CHAT_V2 = "org.openmobilealliance:ChatSession";
    public static final String SERVICE_ID_FT = "org.openmobilealliance:File-Transfer-HTTP";
    public static final String SERVICE_ID_FT_OVER_SMS = "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.ftsms";
    public static final String SERVICE_ID_GEO_PUSH = "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.geopush";
    public static final String SERVICE_ID_GEO_PUSH_VIA_SMS = "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.geosms";
    public static final String SERVICE_ID_MMTEL = "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.mmtel";
    public static final String SERVICE_ID_POST_CALL = "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.gsma.callunanswered";
    public static final String SERVICE_ID_PRESENCE = "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcse.dp";
    public static final String SERVICE_ID_SHARED_MAP = "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.gsma.sharedmap";
    public static final String SERVICE_ID_SHARED_SKETCH = "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.gsma.sharedsketch";
    public static final String SERVICE_ID_SLM = "org.openmobilealliance:StandaloneMsg";
    public static final String TUPLE_BASIC_STATUS_CLOSED = "closed";
    public static final String TUPLE_BASIC_STATUS_OPEN = "open";
    private Uri mContactUri;
    private ServiceCapabilities mServiceCapabilities;
    private String mServiceDescription;
    private String mServiceId;
    private String mServiceVersion;
    private String mStatus;
    private Instant mTimestamp;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BasicStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ServiceId {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class ServiceCapabilities implements Parcelable {
        public static final Parcelable.Creator<ServiceCapabilities> CREATOR = new Parcelable.Creator<ServiceCapabilities>() { // from class: android.telephony.ims.RcsContactPresenceTuple.ServiceCapabilities.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ServiceCapabilities createFromParcel(Parcel parcel) {
                return new ServiceCapabilities(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ServiceCapabilities[] newArray(int i) {
                return new ServiceCapabilities[i];
            }
        };
        public static final String DUPLEX_MODE_FULL = "full";
        public static final String DUPLEX_MODE_HALF = "half";
        public static final String DUPLEX_MODE_RECEIVE_ONLY = "receive-only";
        public static final String DUPLEX_MODE_SEND_ONLY = "send-only";
        private final boolean mIsAudioCapable;
        private final boolean mIsVideoCapable;
        private final List<String> mSupportedDuplexModeList;
        private final List<String> mUnsupportedDuplexModeList;

        @Retention(RetentionPolicy.SOURCE)
        public @interface DuplexMode {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public static final class Builder {
            private ServiceCapabilities mCapabilities;

            public Builder(boolean z, boolean z2) {
                this.mCapabilities = new ServiceCapabilities(z, z2);
            }

            public Builder addSupportedDuplexMode(String str) {
                this.mCapabilities.mSupportedDuplexModeList.add(str);
                return this;
            }

            public Builder addUnsupportedDuplexMode(String str) {
                this.mCapabilities.mUnsupportedDuplexModeList.add(str);
                return this;
            }

            public ServiceCapabilities build() {
                return this.mCapabilities;
            }
        }

        ServiceCapabilities(boolean z, boolean z2) {
            this.mSupportedDuplexModeList = new ArrayList();
            this.mUnsupportedDuplexModeList = new ArrayList();
            this.mIsAudioCapable = z;
            this.mIsVideoCapable = z2;
        }

        private ServiceCapabilities(Parcel parcel) {
            ArrayList arrayList = new ArrayList();
            this.mSupportedDuplexModeList = arrayList;
            ArrayList arrayList2 = new ArrayList();
            this.mUnsupportedDuplexModeList = arrayList2;
            this.mIsAudioCapable = parcel.readBoolean();
            this.mIsVideoCapable = parcel.readBoolean();
            parcel.readStringList(arrayList);
            parcel.readStringList(arrayList2);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeBoolean(this.mIsAudioCapable);
            parcel.writeBoolean(this.mIsVideoCapable);
            parcel.writeStringList(this.mSupportedDuplexModeList);
            parcel.writeStringList(this.mUnsupportedDuplexModeList);
        }

        public boolean isAudioCapable() {
            return this.mIsAudioCapable;
        }

        public boolean isVideoCapable() {
            return this.mIsVideoCapable;
        }

        public List<String> getSupportedDuplexModes() {
            return Collections.unmodifiableList(this.mSupportedDuplexModeList);
        }

        public List<String> getUnsupportedDuplexModes() {
            return Collections.unmodifiableList(this.mUnsupportedDuplexModeList);
        }

        public String toString() {
            return "servCaps{a=" + this.mIsAudioCapable + ", v=" + this.mIsVideoCapable + ", supported=" + this.mSupportedDuplexModeList + ", unsupported=" + this.mUnsupportedDuplexModeList + '}';
        }
    }

    public static final class Builder {
        private final RcsContactPresenceTuple mPresenceTuple;

        public Builder(String str, String str2, String str3) {
            this.mPresenceTuple = new RcsContactPresenceTuple(str, str2, str3);
        }

        public Builder setContactUri(Uri uri) {
            this.mPresenceTuple.mContactUri = uri;
            return this;
        }

        public Builder setTime(Instant instant) {
            this.mPresenceTuple.mTimestamp = instant;
            return this;
        }

        public Builder setServiceDescription(String str) {
            this.mPresenceTuple.mServiceDescription = str;
            return this;
        }

        public Builder setServiceCapabilities(ServiceCapabilities serviceCapabilities) {
            this.mPresenceTuple.mServiceCapabilities = serviceCapabilities;
            return this;
        }

        public RcsContactPresenceTuple build() {
            return this.mPresenceTuple;
        }
    }

    private RcsContactPresenceTuple(String str, String str2, String str3) {
        this.mStatus = str;
        this.mServiceId = str2;
        this.mServiceVersion = str3;
    }

    private RcsContactPresenceTuple(Parcel parcel) {
        this.mContactUri = (Uri) parcel.readParcelable(Uri.class.getClassLoader(), Uri.class);
        this.mTimestamp = convertStringFormatTimeToInstant(parcel.readString());
        this.mStatus = parcel.readString();
        this.mServiceId = parcel.readString();
        this.mServiceVersion = parcel.readString();
        this.mServiceDescription = parcel.readString();
        this.mServiceCapabilities = (ServiceCapabilities) parcel.readParcelable(ServiceCapabilities.class.getClassLoader(), ServiceCapabilities.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mContactUri, i);
        parcel.writeString(convertInstantToStringFormat(this.mTimestamp));
        parcel.writeString(this.mStatus);
        parcel.writeString(this.mServiceId);
        parcel.writeString(this.mServiceVersion);
        parcel.writeString(this.mServiceDescription);
        parcel.writeParcelable(this.mServiceCapabilities, i);
    }

    private String convertInstantToStringFormat(Instant instant) {
        if (instant == null) {
            return "";
        }
        return instant.toString();
    }

    private Instant convertStringFormatTimeToInstant(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return (Instant) DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(str, new TemporalQuery() { // from class: android.telephony.ims.RcsContactPresenceTuple$$ExternalSyntheticLambda0
                @Override // java.time.temporal.TemporalQuery
                public final Object queryFrom(TemporalAccessor temporalAccessor) {
                    return Instant.from(temporalAccessor);
                }
            });
        } catch (DateTimeParseException unused) {
            return null;
        }
    }

    public String getStatus() {
        return this.mStatus;
    }

    public String getServiceId() {
        return this.mServiceId;
    }

    public String getServiceVersion() {
        return this.mServiceVersion;
    }

    public Uri getContactUri() {
        return this.mContactUri;
    }

    public Instant getTime() {
        return this.mTimestamp;
    }

    public String getServiceDescription() {
        return this.mServiceDescription;
    }

    public ServiceCapabilities getServiceCapabilities() {
        return this.mServiceCapabilities;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (Build.IS_ENG) {
            sb.append("u=");
            sb.append(this.mContactUri);
        } else {
            sb.append("u=");
            sb.append(this.mContactUri != null ? "XXX" : PerfettoProtoLogImpl.NULL_STRING);
        }
        sb.append(", id=");
        sb.append(this.mServiceId);
        sb.append(", v=");
        sb.append(this.mServiceVersion);
        sb.append(", s=");
        sb.append(this.mStatus);
        if (this.mTimestamp != null) {
            sb.append(", timestamp=");
            sb.append(this.mTimestamp);
        }
        if (this.mServiceDescription != null) {
            sb.append(", servDesc=");
            sb.append(this.mServiceDescription);
        }
        if (this.mServiceCapabilities != null) {
            sb.append(", servCaps=");
            sb.append(this.mServiceCapabilities);
        }
        sb.append("}");
        return sb.toString();
    }
}
