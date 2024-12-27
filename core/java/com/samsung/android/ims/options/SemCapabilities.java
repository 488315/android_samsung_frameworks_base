package com.samsung.android.ims.options;

import android.hardware.tv.tuner.FrontendInnerFec;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SemSystemProperties;
import android.util.Log;

import com.samsung.android.ims.settings.SemImsProfile;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SemCapabilities implements Parcelable, Cloneable {
    public static final Parcelable.Creator<SemCapabilities> CREATOR;
    public static final long FEATURE_ALL = 1152921504606846975L;
    public static final String FEATURE_TAG_CANCEL_MESSAGE =
            "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.cancelmessage\"";
    public static final String FEATURE_TAG_IPCALL = "+g.gsma.rcs.ipcall";
    public static final String FEATURE_TAG_IPCALL_VIDEO = "+g.gsma.rcs.ipcall;video";
    public static final String FEATURE_TAG_IPCALL_VIDEO_ONLY = "+g.gsma.rcs.ipvideocallonly";
    public static final String FEATURE_TAG_MMTEL =
            "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.mmtel\"";
    public static final String FEATURE_TAG_MMTEL_VIDEO =
            "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.mmtel\";video";
    private static final String LOG_TAG = "SemCapexInfo";
    private long mAvailableFeatures;
    private String mBotServiceId;
    private List<String> mExtFeatures;
    private long mFeatures;
    private boolean mIsAvailable;
    private boolean mIsExpired;
    private boolean mIsLegacyLatching;
    private Date mTimestamp;
    private static final boolean SHIP_BUILD =
            "true".equals(SemSystemProperties.get("ro.product_ship", "false"));
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

    @Deprecated public static long FEATURE_CHATBOT_COMMUNICATION = 1099511627776L;
    public static long FEATURE_CHATBOT_ROLE = 2199023255552L;
    public static long FEATURE_CHATBOT_STANDALONE_MSG = FrontendInnerFec.FEC_20_30;
    public static long FEATURE_MMTEL_CALL_COMPOSER = FrontendInnerFec.FEC_96_180;
    public static long FEATURE_CANCEL_MESSAGE = 281474976710656L;
    private static Map<Long, String> sFeatures = new HashMap();

    public static final class FeatureFetchType {
        public static final int FETCH_TYPE_OTHER = 0;
        public static final int FETCH_TYPE_POLL = 1;
    }

    static {
        sFeatures.put(Long.valueOf(FEATURE_CHAT_CPM), "im");
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
        sFeatures.put(Long.valueOf(FEATURE_NON_RCS_USER), "null");
        sFeatures.put(Long.valueOf(FEATURE_NOT_UPDATED), "not_updated");
        sFeatures.put(Long.valueOf(FEATURE_STICKER), "sticker");
        sFeatures.put(Long.valueOf(FEATURE_FT_VIA_SMS), "ftsms");
        sFeatures.put(Long.valueOf(FEATURE_GEO_VIA_SMS), "geosms");
        sFeatures.put(Long.valueOf(FEATURE_ENRICHED_CALL_COMPOSER), "callcomposer");
        sFeatures.put(Long.valueOf(FEATURE_CANCEL_MESSAGE), "cancelmessage");
        sFeatures.put(Long.valueOf(FEATURE_CHATBOT_STANDALONE_MSG), "chatbot_standalone_msg");
        sFeatures.put(Long.valueOf(FEATURE_CHATBOT_ROLE), "isbot");
        CREATOR =
                new Parcelable.Creator<
                        SemCapabilities>() { // from class:
                                             // com.samsung.android.ims.options.SemCapabilities.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public SemCapabilities createFromParcel(Parcel in) {
                        return new SemCapabilities(in);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public SemCapabilities[] newArray(int size) {
                        return new SemCapabilities[size];
                    }
                };
    }

    @Deprecated
    public class FetchType {
        public static final int FETCH_TYPE_OTHER = 0;
        public static final int FETCH_TYPE_POLL = 1;

        public FetchType() {}
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

    public boolean isFeatureAvailable(int feature) {
        return isFeatureAvailable(Long.valueOf(feature).longValue());
    }

    public boolean isFeatureAvailable(long feature) {
        if (feature == Long.valueOf(FEATURE_OFFLINE_RCS_USER).longValue()) {
            return true;
        }
        boolean isFeatureAvailable = (this.mAvailableFeatures & feature) != 0;
        Log.d(LOG_TAG, "isFeatureAvailable: " + isFeatureAvailable);
        return isFeatureAvailable;
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

    private static String dumpServices(long features) {
        List<String> list = new ArrayList<>();
        for (Map.Entry<Long, String> e : sFeatures.entrySet()) {
            long feature = e.getKey().longValue();
            if ((feature & features) != 0) {
                list.add(e.getValue());
            }
        }
        return list.toString();
    }

    public boolean hasFeature(int feature) {
        return (this.mFeatures & ((long) feature)) == ((long) feature);
    }

    public boolean hasFeature(long feature) {
        return (this.mFeatures & feature) == feature;
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mIsAvailable ? 1 : 0);
        parcel.writeLong(this.mFeatures);
        parcel.writeLong(this.mAvailableFeatures);
        parcel.writeInt(this.mIsExpired ? 1 : 0);
        parcel.writeInt(this.mIsLegacyLatching ? 1 : 0);
        parcel.writeLong(this.mTimestamp.getTime());
        parcel.writeString(this.mBotServiceId != null ? this.mBotServiceId : "");
        parcel.writeStringList(this.mExtFeatures);
    }

    private SemCapabilities(Parcel in) {
        this.mIsAvailable = false;
        this.mFeatures = 0L;
        this.mAvailableFeatures = 0L;
        this.mIsExpired = false;
        this.mIsLegacyLatching = false;
        this.mBotServiceId = null;
        this.mIsAvailable = in.readInt() == 1;
        this.mFeatures = in.readLong();
        this.mAvailableFeatures = in.readLong();
        this.mIsExpired = in.readInt() == 1;
        this.mIsLegacyLatching = in.readInt() == 1;
        this.mTimestamp = new Date(in.readLong());
        this.mBotServiceId = in.readString();
        this.mExtFeatures = new ArrayList();
        in.readStringList(this.mExtFeatures);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SemCapabilities m8844clone() throws CloneNotSupportedException {
        return (SemCapabilities) super.clone();
    }

    public String toString() {
        return "Capabilities [mIsAvailable="
                + this.mIsAvailable
                + ", mFeatures="
                + Long.toHexString(this.mFeatures)
                + ", mIsLegacyLatching="
                + this.mIsLegacyLatching
                + ", mBotServiceId="
                + (SHIP_BUILD ? "xxxxx" : this.mBotServiceId)
                + ", mTimestamp="
                + this.mTimestamp
                + ", mAvailableFeatures="
                + dumpServices(this.mAvailableFeatures)
                + ", mFeatures="
                + dumpServices(this.mFeatures)
                + NavigationBarInflaterView.SIZE_MOD_END;
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

        public Builder setIsAvailable(boolean isAvailable) {
            this.mIsAvailable = isAvailable;
            return this;
        }

        public Builder setFeature(long features) {
            this.mFeatures = features;
            return this;
        }

        public Builder setAvailableFeatures(long availableFeatures) {
            this.mAvailableFeatures = availableFeatures;
            return this;
        }

        public Builder setIsExpired(boolean isExpired) {
            this.mIsExpired = isExpired;
            return this;
        }

        public Builder setLegacyLatching(boolean isLegacyLatching) {
            this.mIsLegacyLatching = isLegacyLatching;
            return this;
        }

        public Builder setTimestamp(Date timestamp) {
            this.mTimestamp = timestamp;
            return this;
        }

        public Builder setBotServiceId(String botServiceId) {
            this.mBotServiceId = botServiceId;
            return this;
        }

        public Builder setExtFeature(List<String> extFeatures) {
            this.mExtFeatures.addAll(extFeatures);
            return this;
        }
    }
}
