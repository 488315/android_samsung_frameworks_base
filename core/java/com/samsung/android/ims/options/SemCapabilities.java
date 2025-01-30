package com.samsung.android.ims.options;

import android.app.backup.FullBackup;
import android.hardware.p006tv.tuner.FrontendInnerFec;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.p009os.SemSystemProperties;
import android.util.Log;
import com.samsung.android.ims.settings.SemImsProfile;
import com.samsung.android.sepunion.UnionConstants;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public class SemCapabilities implements Parcelable, Cloneable {
  public static final Parcelable.Creator<SemCapabilities> CREATOR;
  public static final long FEATURE_ALL = 1152921504606846975L;
  public static final String FEATURE_TAG_CANCEL_MESSAGE =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.cancelmessage\"";
  public static final String FEATURE_TAG_CHAT =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcse.im\"";
  public static final String FEATURE_TAG_CHATBOT_CHAT_SESSION =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.chatbot\"";
  public static final String FEATURE_TAG_CHATBOT_ROLE = "+g.gsma.rcs.isbot";
  public static final String FEATURE_TAG_CHATBOT_STANDALONE_MSG =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.chatbot.sa\"";
  public static final String FEATURE_TAG_ENRICHED_CALL_COMPOSER =
      "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.gsma.callcomposer\"";
  public static final String FEATURE_TAG_ENRICHED_POST_CALL =
      "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.gsma.callunanswered\"";
  public static final String FEATURE_TAG_ENRICHED_SHARED_MAP =
      "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.gsma.sharedmap\"";
  public static final String FEATURE_TAG_ENRICHED_SHARED_SKETCH =
      "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.gsma.sharedsketch\"";
  public static final String FEATURE_TAG_EXTENDED_BOT_MSG =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.xbotmessage\"";
  public static final String FEATURE_TAG_FT =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcse.ft\"";
  public static final String FEATURE_TAG_FT_HTTP =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.fthttp\"";
  public static final String FEATURE_TAG_FT_HTTP_EXTRA =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.fthttpextra\"";
  public static final String FEATURE_TAG_FT_STORE =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.ftstandfw\"";
  public static final String FEATURE_TAG_FT_THUMBNAIL =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.ftthumb\"";
  public static final String FEATURE_TAG_FT_VIA_SMS =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.ftsms\"";
  public static final String FEATURE_TAG_GEOLOCATION_PULL =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.geopull\"";
  public static final String FEATURE_TAG_GEOLOCATION_PULL_FT =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.geopullft\"";
  public static final String FEATURE_TAG_GEOLOCATION_PUSH =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.geopush\"";
  public static final String FEATURE_TAG_GEO_VIA_SMS =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.geosms\"";
  public static final String FEATURE_TAG_INTEGRATED_MSG =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.joyn.intmsg\"";
  public static final String FEATURE_TAG_IPCALL = "+g.gsma.rcs.ipcall";
  public static final String FEATURE_TAG_IPCALL_VIDEO = "+g.gsma.rcs.ipcall;video";
  public static final String FEATURE_TAG_IPCALL_VIDEO_ONLY = "+g.gsma.rcs.ipvideocallonly";
  public static final String FEATURE_TAG_ISH =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.gsma-is\"";
  public static final String FEATURE_TAG_LAST_SEEN_ACTIVE =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.lastseenactive\"";
  public static final String FEATURE_TAG_MMTEL =
      "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.mmtel\"";
  public static final String FEATURE_TAG_MMTEL_CALL_COMPOSER = "+g.gsma.callcomposer";
  public static final String FEATURE_TAG_MMTEL_VIDEO =
      "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.mmtel\";video";
  public static final String FEATURE_TAG_NOT_UPDATED = "not_updated";
  public static final String FEATURE_TAG_NULL = "null";
  public static final String FEATURE_TAG_PLUG_IN =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.plugin\"";
  public static final String FEATURE_TAG_PRESENCE_DISCOVERY =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcse.dp\"";
  public static final String FEATURE_TAG_PUBLIC_MSG =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs .mnc000.mcc460.publicmsg\"";
  public static final String FEATURE_TAG_SF_GROUP_CHAT =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.fullsfgroupchat\"";
  public static final String FEATURE_TAG_SOCIAL_PRESENCE =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcse.sp\"";
  public static final String FEATURE_TAG_STANDALONE_MSG =
      "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.oma.cpm.msg,urn%3Aurn-7%3A3gpp-service.ims.icsi.oma.cpm.largemsg\"";
  public static final String FEATURE_TAG_STICKER =
      "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.sticker\"";
  public static final String FEATURE_TAG_VSH = "+g.3gpp.cs-voice";
  public static final String FEATURE_TAG_VSH_OUTSIDE_CALL =
      "+g.3gpp.iari-ref=\"urn:urn-7:3gpp-application.ims.iari.gsma-vs\"";
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
  private static final boolean SHIP_BUILD =
      "true".equals(SemSystemProperties.get("ro.product_ship", "false"));
  public static int FEATURE_NON_RCS_USER = 16777216;
  public static int FEATURE_NOT_UPDATED = 33554432;
  public static int FEATURE_OFFLINE_RCS_USER = 0;
  public static int FEATURE_ISH = 1;
  public static int FEATURE_VSH = 2;
  public static int FEATURE_CHAT_CPM = 4;
  public static int FEATURE_SF_GROUP_CHAT = 8;
  public static int FEATURE_FT = 16;
  public static int FEATURE_FT_THUMBNAIL = 32;
  public static int FEATURE_FT_STORE = 64;
  public static int FEATURE_FT_HTTP = 128;
  public static int FEATURE_STANDALONE_MSG = 256;
  public static int FEATURE_VSH_OUTSIDE_CALL = 512;
  public static int FEATURE_SOCIAL_PRESENCE = 1024;
  public static int FEATURE_PRESENCE_DISCOVERY = 2048;
  public static int FEATURE_MMTEL = 4096;
  public static int FEATURE_MMTEL_VIDEO = 8192;
  public static int FEATURE_IPCALL = 16384;
  public static int FEATURE_IPCALL_VIDEO = 32768;
  public static int FEATURE_IPCALL_VIDEO_ONLY = 65536;
  public static int FEATURE_GEOLOCATION_PULL = 131072;
  public static int FEATURE_GEOLOCATION_PULL_FT = 262144;
  public static int FEATURE_GEOLOCATION_PUSH = 524288;
  public static int FEATURE_INTEGRATED_MSG = 1048576;
  public static int FEATURE_CHAT_SIMPLE_IM = 2097152;
  public static int FEATURE_FT_VIA_SMS = 4194304;
  public static int FEATURE_GEO_VIA_SMS = 8388608;
  public static int FEATURE_STICKER = 134217728;
  public static int FEATURE_FT_HTTP_EXTRA = 268435456;
  public static int FEATURE_STANDALONE_MSG_V1 = 536870912;
  public static int FEATURE_FT_THUMBNAIL_V1 = 1073741824;

  @Deprecated public static long FEATURE_VEMOTICON = 4294967296L;

  @Deprecated public static long FEATURE_CARD_MSG = 8589934592L;

  @Deprecated public static long FEATURE_BURN_MSG = 17179869184L;

  @Deprecated public static long FEATURE_CLOUD_FILE = 34359738368L;
  public static long FEATURE_ENRICHED_CALL_COMPOSER = 68719476736L;
  public static long FEATURE_ENRICHED_SHARED_MAP = 137438953472L;
  public static long FEATURE_ENRICHED_SHARED_SKETCH = 274877906944L;
  public static long FEATURE_ENRICHED_POST_CALL = 549755813888L;

  @Deprecated public static long FEATURE_CHATBOT_COMMUNICATION = 1099511627776L;
  public static long FEATURE_CHATBOT_CHAT_SESSION = 1099511627776L;
  public static long FEATURE_CHATBOT_ROLE = 2199023255552L;
  public static long FEATURE_PLUG_IN = 4398046511104L;
  public static long FEATURE_PUBLIC_MSG = 8796093022208L;
  public static long FEATURE_LAST_SEEN_ACTIVE = FrontendInnerFec.FEC_18_30;
  public static long FEATURE_CHATBOT_STANDALONE_MSG = FrontendInnerFec.FEC_20_30;
  public static long FEATURE_CHATBOT_EXTENDED_MSG = FrontendInnerFec.FEC_90_180;
  public static long FEATURE_MMTEL_CALL_COMPOSER = FrontendInnerFec.FEC_96_180;
  public static long FEATURE_CANCEL_MESSAGE = 281474976710656L;
  public static final long FEATURE_IM_SERVICE = 1099511627776L | (4 | 2097152);
  public static final int FEATURE_FT_SERVICE = 16 | 64;
  public static final long FEATURE_CALL_SERVICE = (1 | 2) | 274877906944L;

  public static final class FeatureFetchType {
    public static final int FETCH_TYPE_OTHER = 0;
    public static final int FETCH_TYPE_POLL = 1;
  }

  static {
    HashMap hashMap = new HashMap();
    sFeatures = hashMap;
    hashMap.put(Long.valueOf(FEATURE_ISH), "ish");
    sFeatures.put(Long.valueOf(FEATURE_VSH), "vsh");
    sFeatures.put(Long.valueOf(FEATURE_CHAT_CPM), "im");
    sFeatures.put(Long.valueOf(FEATURE_SF_GROUP_CHAT), "fullsf_groupchat");
    sFeatures.put(Long.valueOf(FEATURE_FT), SemImsProfile.ImsFeature.f3006FT);
    sFeatures.put(Long.valueOf(FEATURE_FT_THUMBNAIL), "ftthumb");
    sFeatures.put(Long.valueOf(FEATURE_FT_STORE), "ftstandfw");
    sFeatures.put(Long.valueOf(FEATURE_FT_HTTP), "fthttp");
    sFeatures.put(Long.valueOf(FEATURE_STANDALONE_MSG), "standalone_msg");
    sFeatures.put(Long.valueOf(FEATURE_STANDALONE_MSG_V1), "standalone_msg_v1");
    sFeatures.put(Long.valueOf(FEATURE_VSH_OUTSIDE_CALL), "vsh_outside_call");
    sFeatures.put(Long.valueOf(FEATURE_SOCIAL_PRESENCE), FullBackup.SHAREDPREFS_TREE_TOKEN);
    sFeatures.put(Long.valueOf(FEATURE_PRESENCE_DISCOVERY), "dp");
    sFeatures.put(Long.valueOf(FEATURE_MMTEL), "mmtel");
    sFeatures.put(Long.valueOf(FEATURE_MMTEL_VIDEO), "mmtel_video");
    sFeatures.put(Long.valueOf(FEATURE_MMTEL_CALL_COMPOSER), "mmtel_call_composer");
    sFeatures.put(Long.valueOf(FEATURE_IPCALL), "ipcall");
    sFeatures.put(Long.valueOf(FEATURE_IPCALL_VIDEO), "ipcall_video");
    sFeatures.put(Long.valueOf(FEATURE_IPCALL_VIDEO_ONLY), "ipcall_video_only");
    sFeatures.put(Long.valueOf(FEATURE_GEOLOCATION_PULL), "geopush");
    sFeatures.put(Long.valueOf(FEATURE_GEOLOCATION_PULL_FT), "geopullft");
    sFeatures.put(Long.valueOf(FEATURE_GEOLOCATION_PUSH), "geopush");
    sFeatures.put(Long.valueOf(FEATURE_INTEGRATED_MSG), "intergrated_msg");
    sFeatures.put(Long.valueOf(FEATURE_CHAT_SIMPLE_IM), "session_mode_msg");
    sFeatures.put(Long.valueOf(FEATURE_NON_RCS_USER), FEATURE_TAG_NULL);
    sFeatures.put(Long.valueOf(FEATURE_NOT_UPDATED), FEATURE_TAG_NOT_UPDATED);
    sFeatures.put(Long.valueOf(FEATURE_STICKER), "sticker");
    sFeatures.put(Long.valueOf(FEATURE_FT_THUMBNAIL_V1), "ftthumb_v1");
    sFeatures.put(Long.valueOf(FEATURE_FT_HTTP_EXTRA), "fthttp_extra");
    sFeatures.put(Long.valueOf(FEATURE_FT_VIA_SMS), "ftsms");
    sFeatures.put(Long.valueOf(FEATURE_GEO_VIA_SMS), "geosms");
    sFeatures.put(Long.valueOf(FEATURE_ENRICHED_CALL_COMPOSER), "callcomposer");
    sFeatures.put(Long.valueOf(FEATURE_ENRICHED_SHARED_MAP), "sharedmap");
    sFeatures.put(Long.valueOf(FEATURE_ENRICHED_SHARED_SKETCH), "sharedsketch");
    sFeatures.put(Long.valueOf(FEATURE_ENRICHED_POST_CALL), "callunanswered");
    sFeatures.put(Long.valueOf(FEATURE_LAST_SEEN_ACTIVE), "lastseenactive");
    sFeatures.put(Long.valueOf(FEATURE_CHATBOT_CHAT_SESSION), "chatbot");
    sFeatures.put(Long.valueOf(FEATURE_CANCEL_MESSAGE), "cancelmessage");
    sFeatures.put(Long.valueOf(FEATURE_CHATBOT_STANDALONE_MSG), "chatbot_standalone_msg");
    sFeatures.put(Long.valueOf(FEATURE_CHATBOT_EXTENDED_MSG), "extended_bot_msg");
    sFeatures.put(Long.valueOf(FEATURE_CHATBOT_ROLE), "isbot");
    sFeatures.put(Long.valueOf(FEATURE_PLUG_IN), UnionConstants.SERVICE_PLUGIN);
    sFeatures.put(Long.valueOf(FEATURE_PUBLIC_MSG), "publicmsg");
    CREATOR =
        new Parcelable.Creator<
            SemCapabilities>() { // from class: com.samsung.android.ims.options.SemCapabilities.1
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
    Log.m94d(LOG_TAG, "isFeatureAvailable: " + isFeatureAvailable);
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
    Iterator<Long> it = sFeatures.keySet().iterator();
    while (it.hasNext()) {
      long feature = it.next().longValue();
      if ((feature & features) != 0) {
        list.add(sFeatures.get(Long.valueOf(feature)));
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

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
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
    ArrayList arrayList = new ArrayList();
    this.mExtFeatures = arrayList;
    in.readStringList(arrayList);
  }

  /* renamed from: clone, reason: merged with bridge method [inline-methods] */
  public SemCapabilities m8938clone() throws CloneNotSupportedException {
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
