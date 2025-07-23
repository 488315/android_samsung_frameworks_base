package android.hardware.contexthub;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public final class HubEndpointInfo implements Parcelable {
    public static final Parcelable.Creator<HubEndpointInfo> CREATOR = new Parcelable.Creator<HubEndpointInfo>() { // from class: android.hardware.contexthub.HubEndpointInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HubEndpointInfo createFromParcel(Parcel parcel) {
            return new HubEndpointInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HubEndpointInfo[] newArray(int i) {
            return new HubEndpointInfo[i];
        }
    };
    public static final int TYPE_APP = 2;
    public static final int TYPE_FRAMEWORK = 1;
    public static final int TYPE_HUB_ENDPOINT = 5;
    public static final int TYPE_NANOAPP = 4;
    public static final int TYPE_NATIVE = 3;
    private final List<HubServiceInfo> mHubServiceInfos;
    private final HubEndpointIdentifier mId;
    private final String mName;
    private final List<String> mRequiredPermissions;
    private final String mTag;
    private final int mType;
    private final int mVersion;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EndpointType {
    }

    public static class HubEndpointIdentifier {
        private final long mEndpointId;
        private final long mHubId;

        public HubEndpointIdentifier(long j, long j2) {
            this.mEndpointId = j2;
            this.mHubId = j;
        }

        public HubEndpointIdentifier(EndpointId endpointId) {
            this.mEndpointId = endpointId.id;
            this.mHubId = endpointId.hubId;
        }

        public long getEndpoint() {
            return this.mEndpointId;
        }

        public long getHub() {
            return this.mHubId;
        }

        public static HubEndpointIdentifier invalid() {
            return new HubEndpointIdentifier(0L, 0L);
        }

        public int hashCode() {
            return Objects.hash(Long.valueOf(this.mEndpointId), Long.valueOf(this.mHubId));
        }

        public boolean equals(Object obj) {
            if (obj instanceof HubEndpointIdentifier) {
                HubEndpointIdentifier hubEndpointIdentifier = (HubEndpointIdentifier) obj;
                if (hubEndpointIdentifier.mHubId == this.mHubId && hubEndpointIdentifier.mEndpointId == this.mEndpointId) {
                    return true;
                }
            }
            return false;
        }
    }

    public HubEndpointInfo(EndpointInfo endpointInfo) {
        this.mId = new HubEndpointIdentifier(endpointInfo.id.hubId, endpointInfo.id.id);
        this.mType = endpointInfo.type;
        this.mName = endpointInfo.name;
        this.mVersion = endpointInfo.version;
        this.mTag = endpointInfo.tag;
        this.mRequiredPermissions = Arrays.asList(endpointInfo.requiredPermissions);
        this.mHubServiceInfos = new ArrayList(endpointInfo.services.length);
        for (int i = 0; i < endpointInfo.services.length; i++) {
            this.mHubServiceInfos.add(new HubServiceInfo(endpointInfo.services[i]));
        }
    }

    public HubEndpointInfo(String str, int i, String str2, List<HubServiceInfo> list) {
        this.mId = HubEndpointIdentifier.invalid();
        this.mType = 2;
        this.mName = str;
        this.mVersion = i;
        this.mTag = str2;
        this.mRequiredPermissions = Collections.EMPTY_LIST;
        this.mHubServiceInfos = list;
    }

    private HubEndpointInfo(Parcel parcel) {
        this.mId = new HubEndpointIdentifier(parcel.readLong(), parcel.readLong());
        this.mType = parcel.readInt();
        this.mName = parcel.readString();
        this.mVersion = parcel.readInt();
        this.mTag = parcel.readString();
        ArrayList arrayList = new ArrayList();
        this.mRequiredPermissions = arrayList;
        parcel.readStringList(arrayList);
        ArrayList arrayList2 = new ArrayList();
        this.mHubServiceInfos = arrayList2;
        parcel.readTypedList(arrayList2, HubServiceInfo.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        Iterator<HubServiceInfo> it = this.mHubServiceInfos.iterator();
        int i = 0;
        while (it.hasNext()) {
            i |= it.next().describeContents();
        }
        return i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mId.getHub());
        parcel.writeLong(this.mId.getEndpoint());
        parcel.writeInt(this.mType);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mVersion);
        parcel.writeString(this.mTag);
        parcel.writeStringList(this.mRequiredPermissions);
        parcel.writeTypedList(this.mHubServiceInfos, i);
    }

    public HubEndpointIdentifier getIdentifier() {
        return this.mId;
    }

    public int getType() {
        return this.mType;
    }

    public String getName() {
        return this.mName;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public String getTag() {
        return this.mTag;
    }

    public Collection<String> getRequiredPermissions() {
        return Collections.unmodifiableList(this.mRequiredPermissions);
    }

    public Collection<HubServiceInfo> getServiceInfoCollection() {
        return Collections.unmodifiableList(this.mHubServiceInfos);
    }

    public String toString() {
        return "Endpoint [0x" + Long.toHexString(this.mId.getEndpoint()) + "@ Hub 0x" + Long.toHexString(this.mId.getHub()) + "] Name=" + this.mName + ", Tag=" + this.mTag;
    }
}
