package com.sec.ims.presence;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class PresenceInfo implements Parcelable {
    public static final Parcelable.Creator<PresenceInfo> CREATOR = new Parcelable.Creator<PresenceInfo>() { // from class: com.sec.ims.presence.PresenceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PresenceInfo createFromParcel(Parcel parcel) {
            return new PresenceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PresenceInfo[] newArray(int i) {
            return new PresenceInfo[i];
        }
    };
    private boolean bPublishGzip;
    private String contactId;
    private String eTag;
    private long expire_time;

    /* renamed from: id */
    private long f639id;
    private boolean isFetchSuccess;
    private List<DeviceTuple> mDeviceTuples;
    private List<PersonTuple> mPersonTuples;
    private List<ServiceTuple> mServiceTuples;
    private long min_expires;
    private int phoneId;
    private String pidf;
    private String subscriptionId;
    private String tel_uri;
    private long timestamp;
    private String uri;
    private boolean useExtendedTimer;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Builder {
        long expire_time;
        int phoneId;
        String pidf;
        String tel_uri;
        long timestamp;
        String uri;

        public PresenceInfo build() {
            return new PresenceInfo(this);
        }

        public Builder expire_time(long j) {
            this.expire_time = j;
            return this;
        }

        public Builder phoneId(int i) {
            this.phoneId = i;
            return this;
        }

        public Builder pidf(String str) {
            this.pidf = str;
            return this;
        }

        public Builder tel_uri(String str) {
            this.tel_uri = str;
            return this;
        }

        public Builder timestamp(long j) {
            this.timestamp = j;
            return this;
        }

        public Builder uri(String str) {
            this.uri = str;
            return this;
        }
    }

    public PresenceInfo() {
        this.f639id = -1L;
        this.min_expires = 0L;
        this.pidf = null;
        this.mPersonTuples = new ArrayList();
        this.mServiceTuples = new ArrayList();
        this.mDeviceTuples = new ArrayList();
        this.contactId = null;
        this.subscriptionId = null;
        this.phoneId = 0;
        this.tel_uri = null;
        this.uri = null;
        this.useExtendedTimer = false;
        this.isFetchSuccess = true;
    }

    public void addDevice(DeviceTuple deviceTuple) {
        if (this.mDeviceTuples.contains(deviceTuple)) {
            return;
        }
        this.mDeviceTuples.add(deviceTuple);
    }

    public void addPerson(PersonTuple personTuple) {
        if (this.mPersonTuples.contains(personTuple)) {
            return;
        }
        this.mPersonTuples.add(personTuple);
    }

    public void addService(ServiceTuple serviceTuple) {
        if (this.mServiceTuples.contains(serviceTuple)) {
            return;
        }
        this.mServiceTuples.add(serviceTuple);
    }

    public void clearDevice() {
        this.mDeviceTuples.clear();
    }

    public void clearPerson() {
        this.mPersonTuples.clear();
    }

    public void clearService() {
        this.mServiceTuples.clear();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getContactId() {
        return this.contactId;
    }

    public List<DeviceTuple> getDeviceList() {
        return this.mDeviceTuples;
    }

    public String getEtag() {
        return this.eTag;
    }

    public long getExpireTime() {
        return this.expire_time;
    }

    public boolean getExtendedTimerFlag() {
        return this.useExtendedTimer;
    }

    public long getId() {
        return this.f639id;
    }

    public long getMinExpires() {
        return this.min_expires;
    }

    public List<PersonTuple> getPersonList() {
        return this.mPersonTuples;
    }

    public int getPhoneId() {
        return this.phoneId;
    }

    public String getPidf() {
        return this.pidf;
    }

    public boolean getPublishGzipEnabled() {
        return this.bPublishGzip;
    }

    public List<ServiceTuple> getServiceList() {
        return this.mServiceTuples;
    }

    public ServiceTuple getServiceTuple(String str) {
        for (ServiceTuple serviceTuple : this.mServiceTuples) {
            if (serviceTuple.serviceId.equalsIgnoreCase(str)) {
                return serviceTuple;
            }
        }
        return null;
    }

    public String getSubscriptionId() {
        return this.subscriptionId;
    }

    public String getTelUri() {
        return this.tel_uri;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getUri() {
        return this.uri;
    }

    public boolean isFetchSuccess() {
        return this.isFetchSuccess;
    }

    public void removeService(ServiceTuple serviceTuple) {
        Iterator<ServiceTuple> it = this.mServiceTuples.iterator();
        while (it.hasNext()) {
            if (serviceTuple.serviceId.equalsIgnoreCase(it.next().serviceId)) {
                it.remove();
            }
        }
    }

    public void setContactId(String str) {
        this.contactId = str;
    }

    public void setEtag(String str) {
        this.eTag = str;
    }

    public void setExpireTime(long j) {
        this.expire_time = j;
    }

    public void setExtendedTimerFlag(boolean z) {
        this.useExtendedTimer = z;
    }

    public void setFetchState(boolean z) {
        this.isFetchSuccess = z;
    }

    public void setId(long j) {
        this.f639id = j;
    }

    public void setMinExpires(long j) {
        this.min_expires = j;
    }

    public void setPhoneId(int i) {
        this.phoneId = i;
    }

    public void setPidf(String str) {
        this.pidf = str;
    }

    public void setPublishGzipEnabled(boolean z) {
        this.bPublishGzip = z;
    }

    public void setSubscriptionId(String str) {
        this.subscriptionId = str;
    }

    public void setTelUri(String str) {
        this.tel_uri = str;
    }

    public void setTimestamp(long j) {
        this.timestamp = j;
    }

    public void setUri(String str) {
        this.uri = str;
    }

    public String toString() {
        return "PresenceInfo: id=" + this.f639id + ", tel_uri=" + this.tel_uri + ", uri=" + this.uri + ", contactId=" + this.contactId + ", subscriptionId=" + this.subscriptionId + ", isFetchSuccess=" + this.isFetchSuccess + ", expire_time=" + this.expire_time + ", min_expires=" + this.min_expires + ", phoneId=" + this.phoneId + ", bPublishGzip=" + this.bPublishGzip + ", timestamp=" + this.timestamp;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.f639id);
        parcel.writeInt(this.phoneId);
        String str = this.contactId;
        if (str == null) {
            parcel.writeString("");
        } else {
            parcel.writeString(str);
        }
        String str2 = this.subscriptionId;
        if (str2 == null) {
            parcel.writeString("");
        } else {
            parcel.writeString(str2);
        }
        parcel.writeString(this.tel_uri);
        parcel.writeString(this.uri);
        parcel.writeLong(this.timestamp);
        parcel.writeLong(this.expire_time);
        if (this.pidf == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeString(this.pidf);
        }
    }

    public void addDevice(List<DeviceTuple> list) {
        this.mDeviceTuples.addAll(list);
    }

    public void addPerson(List<PersonTuple> list) {
        this.mPersonTuples.addAll(list);
    }

    public void addService(List<ServiceTuple> list) {
        this.mServiceTuples.addAll(list);
    }

    public PresenceInfo(String str, int i) {
        this.f639id = -1L;
        this.min_expires = 0L;
        this.pidf = null;
        this.mPersonTuples = new ArrayList();
        this.mServiceTuples = new ArrayList();
        this.mDeviceTuples = new ArrayList();
        this.contactId = null;
        this.tel_uri = null;
        this.uri = null;
        this.isFetchSuccess = true;
        this.subscriptionId = str;
        this.phoneId = i;
    }

    public PresenceInfo(int i) {
        this.f639id = -1L;
        this.min_expires = 0L;
        this.pidf = null;
        this.mPersonTuples = new ArrayList();
        this.mServiceTuples = new ArrayList();
        this.mDeviceTuples = new ArrayList();
        this.contactId = null;
        this.subscriptionId = null;
        this.tel_uri = null;
        this.uri = null;
        this.useExtendedTimer = false;
        this.isFetchSuccess = true;
        this.phoneId = i;
    }

    public PresenceInfo(Builder builder) {
        this.f639id = -1L;
        this.min_expires = 0L;
        this.pidf = null;
        this.tel_uri = builder.tel_uri;
        this.phoneId = builder.phoneId;
        this.uri = builder.uri;
        this.timestamp = builder.timestamp;
        this.expire_time = builder.expire_time;
        this.pidf = builder.pidf;
        this.mPersonTuples = new ArrayList();
        this.mServiceTuples = new ArrayList();
        this.mDeviceTuples = new ArrayList();
    }

    public PresenceInfo(Parcel parcel) {
        this.f639id = -1L;
        this.min_expires = 0L;
        this.pidf = null;
        this.f639id = parcel.readLong();
        this.phoneId = parcel.readInt();
        this.contactId = parcel.readString();
        this.subscriptionId = parcel.readString();
        this.tel_uri = parcel.readString();
        this.uri = parcel.readString();
        this.timestamp = parcel.readLong();
        this.expire_time = parcel.readLong();
        if (parcel.readInt() == 1) {
            this.pidf = parcel.readString();
        }
    }
}
