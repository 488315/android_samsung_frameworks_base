package android.telephony.ims;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SystemApi
/* loaded from: classes4.dex */
public final class RcsContactUceCapability implements Parcelable {
    public static final int CAPABILITY_MECHANISM_OPTIONS = 2;
    public static final int CAPABILITY_MECHANISM_PRESENCE = 1;
    public static final Parcelable.Creator<RcsContactUceCapability> CREATOR = new Parcelable.Creator<RcsContactUceCapability>() { // from class: android.telephony.ims.RcsContactUceCapability.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RcsContactUceCapability createFromParcel(Parcel parcel) {
            return new RcsContactUceCapability(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RcsContactUceCapability[] newArray(int i) {
            return new RcsContactUceCapability[i];
        }
    };
    public static final int REQUEST_RESULT_FOUND = 3;
    public static final int REQUEST_RESULT_NOT_FOUND = 2;
    public static final int REQUEST_RESULT_NOT_ONLINE = 1;
    public static final int REQUEST_RESULT_UNKNOWN = 0;
    public static final int SOURCE_TYPE_CACHED = 1;
    public static final int SOURCE_TYPE_NETWORK = 0;
    private int mCapabilityMechanism;
    private final Uri mContactUri;
    private Uri mEntityUri;
    private final Set<String> mFeatureTags;
    private final List<RcsContactPresenceTuple> mPresenceTuples;
    private int mRequestResult;
    private int mSourceType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CapabilityMechanism {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SourceType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class OptionsBuilder {
        private final RcsContactUceCapability mCapabilities;

        public OptionsBuilder(Uri uri) {
            this.mCapabilities = new RcsContactUceCapability(uri, 2, 0);
        }

        public OptionsBuilder(Uri uri, int i) {
            this.mCapabilities = new RcsContactUceCapability(uri, 2, i);
        }

        public OptionsBuilder setRequestResult(int i) {
            this.mCapabilities.mRequestResult = i;
            return this;
        }

        public OptionsBuilder addFeatureTag(String str) {
            this.mCapabilities.mFeatureTags.add(str);
            return this;
        }

        public OptionsBuilder addFeatureTags(Set<String> set) {
            this.mCapabilities.mFeatureTags.addAll(set);
            return this;
        }

        public RcsContactUceCapability build() {
            return this.mCapabilities;
        }
    }

    public static final class PresenceBuilder {
        private final RcsContactUceCapability mCapabilities;

        public PresenceBuilder(Uri uri, int i, int i2) {
            RcsContactUceCapability rcsContactUceCapability = new RcsContactUceCapability(uri, 1, i);
            this.mCapabilities = rcsContactUceCapability;
            rcsContactUceCapability.mRequestResult = i2;
        }

        public PresenceBuilder addCapabilityTuple(RcsContactPresenceTuple rcsContactPresenceTuple) {
            this.mCapabilities.mPresenceTuples.add(rcsContactPresenceTuple);
            return this;
        }

        public PresenceBuilder addCapabilityTuples(List<RcsContactPresenceTuple> list) {
            this.mCapabilities.mPresenceTuples.addAll(list);
            return this;
        }

        public PresenceBuilder setEntityUri(Uri uri) {
            this.mCapabilities.mEntityUri = uri;
            return this;
        }

        public RcsContactUceCapability build() {
            return this.mCapabilities;
        }
    }

    private RcsContactUceCapability(Uri uri, int i, int i2) {
        this.mFeatureTags = new HashSet();
        this.mPresenceTuples = new ArrayList();
        this.mContactUri = uri;
        this.mCapabilityMechanism = i;
        this.mSourceType = i2;
    }

    private RcsContactUceCapability(Parcel parcel) {
        HashSet hashSet = new HashSet();
        this.mFeatureTags = hashSet;
        ArrayList arrayList = new ArrayList();
        this.mPresenceTuples = arrayList;
        this.mContactUri = (Uri) parcel.readParcelable(Uri.class.getClassLoader(), Uri.class);
        this.mCapabilityMechanism = parcel.readInt();
        this.mSourceType = parcel.readInt();
        this.mRequestResult = parcel.readInt();
        this.mEntityUri = (Uri) parcel.readParcelable(Uri.class.getClassLoader(), Uri.class);
        ArrayList arrayList2 = new ArrayList();
        parcel.readStringList(arrayList2);
        hashSet.addAll(arrayList2);
        parcel.readParcelableList(arrayList, RcsContactPresenceTuple.class.getClassLoader(), RcsContactPresenceTuple.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mContactUri, i);
        parcel.writeInt(this.mCapabilityMechanism);
        parcel.writeInt(this.mSourceType);
        parcel.writeInt(this.mRequestResult);
        parcel.writeParcelable(this.mEntityUri, i);
        parcel.writeStringList(new ArrayList(this.mFeatureTags));
        parcel.writeParcelableList(this.mPresenceTuples, i);
    }

    public int getCapabilityMechanism() {
        return this.mCapabilityMechanism;
    }

    public Set<String> getFeatureTags() {
        if (this.mCapabilityMechanism != 2) {
            return Collections.EMPTY_SET;
        }
        return Collections.unmodifiableSet(this.mFeatureTags);
    }

    public List<RcsContactPresenceTuple> getCapabilityTuples() {
        if (this.mCapabilityMechanism != 1) {
            return Collections.EMPTY_LIST;
        }
        return Collections.unmodifiableList(this.mPresenceTuples);
    }

    public RcsContactPresenceTuple getCapabilityTuple(String str) {
        if (this.mCapabilityMechanism != 1) {
            return null;
        }
        for (RcsContactPresenceTuple rcsContactPresenceTuple : this.mPresenceTuples) {
            if (rcsContactPresenceTuple.getServiceId() != null && rcsContactPresenceTuple.getServiceId().equals(str)) {
                return rcsContactPresenceTuple;
            }
        }
        return null;
    }

    public int getSourceType() {
        return this.mSourceType;
    }

    public int getRequestResult() {
        return this.mRequestResult;
    }

    public Uri getContactUri() {
        return this.mContactUri;
    }

    public Uri getEntityUri() {
        return this.mEntityUri;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("RcsContactUceCapability");
        int i = this.mCapabilityMechanism;
        if (i == 1) {
            sb.append("(presence) {");
        } else if (i == 2) {
            sb.append("(options) {");
        } else {
            sb.append("(?) {");
        }
        boolean z = Build.IS_ENG;
        Object obj = PerfettoProtoLogImpl.NULL_STRING;
        if (z) {
            sb.append("uri=");
            sb.append(this.mContactUri);
        } else {
            sb.append("uri (isNull)=");
            sb.append(this.mContactUri != null ? "XXX" : PerfettoProtoLogImpl.NULL_STRING);
        }
        sb.append(", sourceType=");
        sb.append(this.mSourceType);
        sb.append(", requestResult=");
        sb.append(this.mRequestResult);
        if (Build.IS_ENG) {
            sb.append("entity uri=");
            Uri uri = this.mEntityUri;
            if (uri != null) {
                obj = uri;
            }
            sb.append(obj);
        } else {
            sb.append("entity uri (isNull)=");
            sb.append(this.mEntityUri == null ? PerfettoProtoLogImpl.NULL_STRING : "XXX");
        }
        int i2 = this.mCapabilityMechanism;
        if (i2 == 1) {
            sb.append(", presenceTuples={");
            sb.append(this.mPresenceTuples);
            sb.append("}");
        } else if (i2 == 2) {
            sb.append(", featureTags={");
            sb.append(this.mFeatureTags);
            sb.append("}");
        }
        return sb.toString();
    }
}
