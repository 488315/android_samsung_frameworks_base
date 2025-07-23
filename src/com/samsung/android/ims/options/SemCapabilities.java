package com.samsung.android.ims.options;

import android.hardware.tv.tuner.FrontendInnerFec;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SemSystemProperties;
import android.util.Log;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.ims.settings.SemImsProfile;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public class SemCapabilities implements Parcelable, Cloneable {
    public static final Parcelable.Creator<SemCapabilities> CREATOR;
    public static final long FEATURE_ALL = 1152921504606846975L;
    public static final String FEATURE_TAG_CANCEL_MESSAGE = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.cancelmessage\"";
    public static final String FEATURE_TAG_IPCALL = "+g.gsma.rcs.ipcall";
    public static final String FEATURE_TAG_IPCALL_VIDEO = "+g.gsma.rcs.ipcall;video";
    public static final String FEATURE_TAG_IPCALL_VIDEO_ONLY = "+g.gsma.rcs.ipvideocallonly";
    public static final String FEATURE_TAG_MMTEL = "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.mmtel\"";
    public static final String FEATURE_TAG_MMTEL_VIDEO = "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.mmtel\";video";
    private static final String LOG_TAG = "SemCapexInfo";
    private static Map<Long, String> sFeatures;
    private long mAvailableFeatures;
    private String mBotServiceId;
    private List<String> mExtFeatures;
    private long mFeatures;
    private boolean mIsAvailable;
    private boolean mIsExpired;
    private boolean mIsLegacyLatching;
    private Date mTimestamp;
    private static final boolean SHIP_BUILD = "true".equals(SemSystemProperties.get("ro.product_ship", "false"));
    public static int FEATURE_NON_RCS_USER = 16777216;
    public static int FEATURE_NOT_UPDATED = 33554432;
    public static int FEATURE_OFFLINE_RCS_USER = 0;
    public static int FEATURE_CHAT_CPM = 4;
    public static int FEATURE_FT = 16;
    public static int FEATURE_FT_HTTP = 128;
    public static int FEATURE_STANDALONE_MSG = 256;
    public static int FEATURE_MMTEL = 4096;
    public static int FEATURE_MMTEL_VIDEO = 8192;
    public static int FEATURE_IPCALL = 16384;
    public static int FEATURE_IPCALL_VIDEO = 32768;
    public static int FEATURE_IPCALL_VIDEO_ONLY = 65536;
    public static int FEATURE_GEOLOCATION_PUSH = 524288;
    public static int FEATURE_CHAT_SIMPLE_IM = 2097152;
    public static int FEATURE_FT_VIA_SMS = 4194304;
    public static int FEATURE_GEO_VIA_SMS = 8388608;
    public static int FEATURE_STICKER = 134217728;
    public static long FEATURE_ENRICHED_CALL_COMPOSER = 68719476736L;

    @Deprecated
    public static long FEATURE_CHATBOT_COMMUNICATION = 1099511627776L;
    public static long FEATURE_CHATBOT_ROLE = 2199023255552L;
    public static long FEATURE_CHATBOT_STANDALONE_MSG = FrontendInnerFec.FEC_20_30;
    public static long FEATURE_MMTEL_CALL_COMPOSER = FrontendInnerFec.FEC_96_180;
    public static long FEATURE_CANCEL_MESSAGE = 281474976710656L;

    public static final class FeatureFetchType {
        public static final int FETCH_TYPE_OTHER = 0;
        public static final int FETCH_TYPE_POLL = 1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        HashMap hashMap = new HashMap();
        sFeatures = hashMap;
        hashMap.put(Long.valueOf(FEATURE_CHAT_CPM), "im");
        sFeatures.put(Long.valueOf(FEATURE_FT), SemImsProfile.ImsFeature.FT);
        sFeatures.put(Long.valueOf(FEATURE_FT_HTTP), "fthttp");
        sFeatures.put(Long.valueOf(FEATURE_STANDALONE_MSG), "standalone_msg");
        sFeatures.put(Long.valueOf(FEATURE_MMTEL), "mmtel");
        sFeatures.put(Long.valueOf(FEATURE_MMTEL_VIDEO), "mmtel_video");
        sFeatures.put(Long.valueOf(FEATURE_MMTEL_CALL_COMPOSER), "mmtel_call_composer");
        sFeatures.put(Long.valueOf(FEATURE_IPCALL), "ipcall");
        sFeatures.put(Long.valueOf(FEATURE_IPCALL_VIDEO), "ipcall_video");
        sFeatures.put(Long.valueOf(FEATURE_IPCALL_VIDEO_ONLY), "ipcall_video_only");
        sFeatures.put(Long.valueOf(FEATURE_GEOLOCATION_PUSH), "geopush");
        sFeatures.put(Long.valueOf(FEATURE_CHAT_SIMPLE_IM), "session_mode_msg");
        sFeatures.put(Long.valueOf(FEATURE_NON_RCS_USER), PerfettoProtoLogImpl.NULL_STRING);
        sFeatures.put(Long.valueOf(FEATURE_NOT_UPDATED), "not_updated");
        sFeatures.put(Long.valueOf(FEATURE_STICKER), "sticker");
        sFeatures.put(Long.valueOf(FEATURE_FT_VIA_SMS), "ftsms");
        sFeatures.put(Long.valueOf(FEATURE_GEO_VIA_SMS), "geosms");
        sFeatures.put(Long.valueOf(FEATURE_ENRICHED_CALL_COMPOSER), "callcomposer");
        sFeatures.put(Long.valueOf(FEATURE_CANCEL_MESSAGE), "cancelmessage");
        sFeatures.put(Long.valueOf(FEATURE_CHATBOT_STANDALONE_MSG), "chatbot_standalone_msg");
        sFeatures.put(Long.valueOf(FEATURE_CHATBOT_ROLE), "isbot");
        CREATOR = new Parcelable.Creator<SemCapabilities>() { // from class: com.samsung.android.ims.options.SemCapabilities.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SemCapabilities createFromParcel(Parcel parcel) {
                return new SemCapabilities(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SemCapabilities[] newArray(int i) {
                return new SemCapabilities[i];
            }
        };
    }

    @Deprecated
    public class FetchType {
        public static final int FETCH_TYPE_OTHER = 0;
        public static final int FETCH_TYPE_POLL = 1;

        public FetchType(SemCapabilities semCapabilities) {
        }
    }

    public SemCapabilities(Builder builder) {
        this.mIsAvailable = false;
        this.mFeatures = 0L;
        this.mAvailableFeatures = 0L;
        this.mIsExpired = false;
        this.mIsLegacyLatching = false;
        this.mBotServiceId = null;
        this.mIsAvailable = builder.mIsAvailable;
        this.mFeatures = builder.mFeatures;
        this.mAvailableFeatures = builder.mAvailableFeatures;
        this.mIsExpired = builder.mIsExpired;
        this.mIsLegacyLatching = builder.mIsLegacyLatching;
        this.mTimestamp = builder.mTimestamp;
        this.mBotServiceId = builder.mBotServiceId;
        this.mExtFeatures = builder.mExtFeatures;
    }

    public boolean isFeatureAvailable(int i) {
        long j = i;
        Long.valueOf(j).getClass();
        return isFeatureAvailable(j);
    }

    public boolean isFeatureAvailable(long j) {
        long j2 = FEATURE_OFFLINE_RCS_USER;
        Long.valueOf(j2).getClass();
        if (j == j2) {
            return true;
        }
        boolean z = (this.mAvailableFeatures & j) != 0;
        Log.d(LOG_TAG, "isFeatureAvailable: " + z);
        return z;
    }

    public boolean isAvailable() {
        return this.mIsAvailable;
    }

    public long getFeature() {
        return this.mFeatures;
    }

    public long getAvailableFeatures() {
        return this.mAvailableFeatures;
    }

    private static String dumpServices(long j) {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<Long, String> entry : sFeatures.entrySet()) {
            if ((entry.getKey().longValue() & j) != 0) {
                arrayList.add(entry.getValue());
            }
        }
        return arrayList.toString();
    }

    public boolean hasFeature(int i) {
        long j = this.mFeatures;
        long j2 = i;
        return (j & j2) == j2;
    }

    public boolean hasFeature(long j) {
        return (this.mFeatures & j) == j;
    }

    public Date getTimestamp() {
        return this.mTimestamp;
    }

    public boolean getExpired() {
        return this.mIsExpired;
    }

    public boolean getLegacyLatching() {
        return this.mIsLegacyLatching;
    }

    public String getBotServiceId() {
        return this.mBotServiceId;
    }

    public List<String> getExtFeature() {
        return this.mExtFeatures;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mIsAvailable ? 1 : 0);
        parcel.writeLong(this.mFeatures);
        parcel.writeLong(this.mAvailableFeatures);
        parcel.writeInt(this.mIsExpired ? 1 : 0);
        parcel.writeInt(this.mIsLegacyLatching ? 1 : 0);
        parcel.writeLong(this.mTimestamp.getTime());
        String str = this.mBotServiceId;
        if (str == null) {
            str = "";
        }
        parcel.writeString(str);
        parcel.writeStringList(this.mExtFeatures);
    }

    private SemCapabilities(Parcel parcel) {
        this.mIsAvailable = false;
        this.mFeatures = 0L;
        this.mAvailableFeatures = 0L;
        this.mIsExpired = false;
        this.mIsLegacyLatching = false;
        this.mBotServiceId = null;
        this.mIsAvailable = parcel.readInt() == 1;
        this.mFeatures = parcel.readLong();
        this.mAvailableFeatures = parcel.readLong();
        this.mIsExpired = parcel.readInt() == 1;
        this.mIsLegacyLatching = parcel.readInt() == 1;
        this.mTimestamp = new Date(parcel.readLong());
        this.mBotServiceId = parcel.readString();
        ArrayList arrayList = new ArrayList();
        this.mExtFeatures = arrayList;
        parcel.readStringList(arrayList);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SemCapabilities m9252clone() throws CloneNotSupportedException {
        return (SemCapabilities) super.clone();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Capabilities [mIsAvailable=");
        sb.append(this.mIsAvailable);
        sb.append(", mFeatures=");
        sb.append(Long.toHexString(this.mFeatures));
        sb.append(", mIsLegacyLatching=");
        sb.append(this.mIsLegacyLatching);
        sb.append(", mBotServiceId=");
        sb.append(SHIP_BUILD ? "xxxxx" : this.mBotServiceId);
        sb.append(", mTimestamp=");
        sb.append(this.mTimestamp);
        sb.append(", mAvailableFeatures=");
        sb.append(dumpServices(this.mAvailableFeatures));
        sb.append(", mFeatures=");
        sb.append(dumpServices(this.mFeatures));
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        protected Date mTimestamp;
        protected boolean mIsAvailable = false;
        protected long mFeatures = 0;
        protected long mAvailableFeatures = 0;
        protected boolean mIsExpired = false;
        protected boolean mIsLegacyLatching = false;
        protected String mBotServiceId = null;
        protected List<String> mExtFeatures = new ArrayList();

        public SemCapabilities build() {
            return new SemCapabilities(this);
        }

        public Builder setIsAvailable(boolean z) {
            this.mIsAvailable = z;
            return this;
        }

        public Builder setFeature(long j) {
            this.mFeatures = j;
            return this;
        }

        public Builder setAvailableFeatures(long j) {
            this.mAvailableFeatures = j;
            return this;
        }

        public Builder setIsExpired(boolean z) {
            this.mIsExpired = z;
            return this;
        }

        public Builder setLegacyLatching(boolean z) {
            this.mIsLegacyLatching = z;
            return this;
        }

        public Builder setTimestamp(Date date) {
            this.mTimestamp = date;
            return this;
        }

        public Builder setBotServiceId(String str) {
            this.mBotServiceId = str;
            return this;
        }

        public Builder setExtFeature(List<String> list) {
            this.mExtFeatures.addAll(list);
            return this;
        }
    }
}
