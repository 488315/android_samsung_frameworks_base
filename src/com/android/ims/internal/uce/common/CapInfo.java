package com.android.ims.internal.uce.common;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class CapInfo implements Parcelable {
    public static final String CALLCOMPOSER = "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gppservice.ims.icsi.gsma.callcomposer\"";
    public static final String CAPDISC_VIA_PRESENCE = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcse.dp\"";
    public static final String CHATBOT = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gppapplication.ims.iari.rcs.chatbot\"";
    public static final String CHATBOTROLE = "+g.gsma.rcs.isbot";
    public static final Parcelable.Creator<CapInfo> CREATOR = new Parcelable.Creator<CapInfo>() { // from class: com.android.ims.internal.uce.common.CapInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CapInfo createFromParcel(Parcel parcel) {
            return new CapInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CapInfo[] newArray(int i) {
            return new CapInfo[i];
        }
    };
    public static final String FILE_TRANSFER = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcse.ft\"";
    public static final String FILE_TRANSFER_HTTP = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.fthttp\"";
    public static final String FILE_TRANSFER_SNF = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.ftstandfw\"";
    public static final String FILE_TRANSFER_THUMBNAIL = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.ftthumb\"";
    public static final String FULL_SNF_GROUPCHAT = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.fullsfgroupchat\"";
    public static final String GEOPULL = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.geopull\"";
    public static final String GEOPULL_FT = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.geopullft\"";
    public static final String GEOPUSH = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.geopush\"";
    public static final String GEOSMS = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gppapplication.ims.iari.rcs.geosms\"";
    public static final String IMAGE_SHARE = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.gsma-is\"";
    public static final String INSTANT_MSG = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcse.im\"";
    public static final String IP_VIDEO = "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.mmtel\";video";
    public static final String IP_VOICE = "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.mmtel\"";
    public static final String MMTEL_CALLCOMPOSER = "+g.gsma.callcomposer";
    public static final String POSTCALL = "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gppservice.ims.icsi.gsma.callunanswered\"";
    public static final String RCS_IP_VIDEO_CALL = "+g.gsma.rcs.ipvideocall";
    public static final String RCS_IP_VIDEO_ONLY_CALL = "+g.gsma.rcs.ipvideoonlycall";
    public static final String RCS_IP_VOICE_CALL = "+g.gsma.rcs.ipcall";
    public static final String SHAREDMAP = "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gppservice.ims.icsi.gsma.sharedmap\"";
    public static final String SHAREDSKETCH = "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gppservice.ims.icsi.gsma.sharedsketch\"";
    public static final String SOCIAL_PRESENCE = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcse.sp\"";
    public static final String STANDALONE_CHATBOT = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.rcs.chatbot.sa\"";
    public static final String STANDALONE_MSG = "+g.3gpp.icsi-ref=\"urn%3Aurn-7%3A3gpp-service.ims.icsi.oma.cpm.msg;urn%3Aurn-7%3A3gpp-service.ims.icsi.oma.cpm.largemsg\"";
    public static final String VIDEO_SHARE = "+g.3gpp.iari-ref=\"urn%3Aurn-7%3A3gpp-application.ims.iari.gsma-vs\"";
    public static final String VIDEO_SHARE_DURING_CS = "+g.3gpp.cs-voice";
    private boolean mCallComposerSupported;
    private Map<String, String> mCapInfoMap;
    private long mCapTimestamp;
    private boolean mCdViaPresenceSupported;
    private boolean mChatbotRoleSupported;
    private boolean mChatbotSupported;
    private String[] mExts;
    private boolean mFtHttpSupported;
    private boolean mFtSnFSupported;
    private boolean mFtSupported;
    private boolean mFtThumbSupported;
    private boolean mFullSnFGroupChatSupported;
    private boolean mGeoPullFtSupported;
    private boolean mGeoPullSupported;
    private boolean mGeoPushSupported;
    private boolean mGeoSmsSupported;
    private boolean mImSupported;
    private boolean mIpVideoSupported;
    private boolean mIpVoiceSupported;
    private boolean mIsSupported;
    private boolean mMmtelCallComposerSupported;
    private boolean mPostCallSupported;
    private boolean mRcsIpVideoCallSupported;
    private boolean mRcsIpVideoOnlyCallSupported;
    private boolean mRcsIpVoiceCallSupported;
    private boolean mSharedMapSupported;
    private boolean mSharedSketchSupported;
    private boolean mSmChatbotSupported;
    private boolean mSmSupported;
    private boolean mSpSupported;
    private boolean mVsDuringCSSupported;
    private boolean mVsSupported;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CapInfo() {
        this.mImSupported = false;
        this.mFtSupported = false;
        this.mFtThumbSupported = false;
        this.mFtSnFSupported = false;
        this.mFtHttpSupported = false;
        this.mIsSupported = false;
        this.mVsDuringCSSupported = false;
        this.mVsSupported = false;
        this.mSpSupported = false;
        this.mCdViaPresenceSupported = false;
        this.mIpVoiceSupported = false;
        this.mIpVideoSupported = false;
        this.mGeoPullFtSupported = false;
        this.mGeoPullSupported = false;
        this.mGeoPushSupported = false;
        this.mSmSupported = false;
        this.mFullSnFGroupChatSupported = false;
        this.mRcsIpVoiceCallSupported = false;
        this.mRcsIpVideoCallSupported = false;
        this.mRcsIpVideoOnlyCallSupported = false;
        this.mGeoSmsSupported = false;
        this.mCallComposerSupported = false;
        this.mPostCallSupported = false;
        this.mSharedMapSupported = false;
        this.mSharedSketchSupported = false;
        this.mChatbotSupported = false;
        this.mChatbotRoleSupported = false;
        this.mSmChatbotSupported = false;
        this.mMmtelCallComposerSupported = false;
        this.mExts = new String[10];
        this.mCapTimestamp = 0L;
        this.mCapInfoMap = new HashMap();
    }

    public boolean isImSupported() {
        return this.mImSupported;
    }

    public void setImSupported(boolean z) {
        this.mImSupported = z;
    }

    public boolean isFtThumbSupported() {
        return this.mFtThumbSupported;
    }

    public void setFtThumbSupported(boolean z) {
        this.mFtThumbSupported = z;
    }

    public boolean isFtSnFSupported() {
        return this.mFtSnFSupported;
    }

    public void setFtSnFSupported(boolean z) {
        this.mFtSnFSupported = z;
    }

    public boolean isFtHttpSupported() {
        return this.mFtHttpSupported;
    }

    public void setFtHttpSupported(boolean z) {
        this.mFtHttpSupported = z;
    }

    public boolean isFtSupported() {
        return this.mFtSupported;
    }

    public void setFtSupported(boolean z) {
        this.mFtSupported = z;
    }

    public boolean isIsSupported() {
        return this.mIsSupported;
    }

    public void setIsSupported(boolean z) {
        this.mIsSupported = z;
    }

    public boolean isVsDuringCSSupported() {
        return this.mVsDuringCSSupported;
    }

    public void setVsDuringCSSupported(boolean z) {
        this.mVsDuringCSSupported = z;
    }

    public boolean isVsSupported() {
        return this.mVsSupported;
    }

    public void setVsSupported(boolean z) {
        this.mVsSupported = z;
    }

    public boolean isSpSupported() {
        return this.mSpSupported;
    }

    public void setSpSupported(boolean z) {
        this.mSpSupported = z;
    }

    public boolean isCdViaPresenceSupported() {
        return this.mCdViaPresenceSupported;
    }

    public void setCdViaPresenceSupported(boolean z) {
        this.mCdViaPresenceSupported = z;
    }

    public boolean isIpVoiceSupported() {
        return this.mIpVoiceSupported;
    }

    public void setIpVoiceSupported(boolean z) {
        this.mIpVoiceSupported = z;
    }

    public boolean isIpVideoSupported() {
        return this.mIpVideoSupported;
    }

    public void setIpVideoSupported(boolean z) {
        this.mIpVideoSupported = z;
    }

    public boolean isGeoPullFtSupported() {
        return this.mGeoPullFtSupported;
    }

    public void setGeoPullFtSupported(boolean z) {
        this.mGeoPullFtSupported = z;
    }

    public boolean isGeoPullSupported() {
        return this.mGeoPullSupported;
    }

    public void setGeoPullSupported(boolean z) {
        this.mGeoPullSupported = z;
    }

    public boolean isGeoPushSupported() {
        return this.mGeoPushSupported;
    }

    public void setGeoPushSupported(boolean z) {
        this.mGeoPushSupported = z;
    }

    public boolean isSmSupported() {
        return this.mSmSupported;
    }

    public void setSmSupported(boolean z) {
        this.mSmSupported = z;
    }

    public boolean isFullSnFGroupChatSupported() {
        return this.mFullSnFGroupChatSupported;
    }

    public boolean isRcsIpVoiceCallSupported() {
        return this.mRcsIpVoiceCallSupported;
    }

    public boolean isRcsIpVideoCallSupported() {
        return this.mRcsIpVideoCallSupported;
    }

    public boolean isRcsIpVideoOnlyCallSupported() {
        return this.mRcsIpVideoOnlyCallSupported;
    }

    public void setFullSnFGroupChatSupported(boolean z) {
        this.mFullSnFGroupChatSupported = z;
    }

    public void setRcsIpVoiceCallSupported(boolean z) {
        this.mRcsIpVoiceCallSupported = z;
    }

    public void setRcsIpVideoCallSupported(boolean z) {
        this.mRcsIpVideoCallSupported = z;
    }

    public void setRcsIpVideoOnlyCallSupported(boolean z) {
        this.mRcsIpVideoOnlyCallSupported = z;
    }

    public boolean isGeoSmsSupported() {
        return this.mGeoSmsSupported;
    }

    public void setGeoSmsSupported(boolean z) {
        this.mGeoSmsSupported = z;
    }

    public boolean isCallComposerSupported() {
        return this.mCallComposerSupported;
    }

    public void setCallComposerSupported(boolean z) {
        this.mCallComposerSupported = z;
    }

    public boolean isPostCallSupported() {
        return this.mPostCallSupported;
    }

    public void setPostCallSupported(boolean z) {
        this.mPostCallSupported = z;
    }

    public boolean isSharedMapSupported() {
        return this.mSharedMapSupported;
    }

    public void setSharedMapSupported(boolean z) {
        this.mSharedMapSupported = z;
    }

    public boolean isSharedSketchSupported() {
        return this.mSharedSketchSupported;
    }

    public void setSharedSketchSupported(boolean z) {
        this.mSharedSketchSupported = z;
    }

    public boolean isChatbotSupported() {
        return this.mChatbotSupported;
    }

    public void setChatbotSupported(boolean z) {
        this.mChatbotSupported = z;
    }

    public boolean isChatbotRoleSupported() {
        return this.mChatbotRoleSupported;
    }

    public void setChatbotRoleSupported(boolean z) {
        this.mChatbotRoleSupported = z;
    }

    public boolean isSmChatbotSupported() {
        return this.mSmChatbotSupported;
    }

    public void setSmChatbotSupported(boolean z) {
        this.mSmChatbotSupported = z;
    }

    public boolean isMmtelCallComposerSupported() {
        return this.mMmtelCallComposerSupported;
    }

    public void setMmtelCallComposerSupported(boolean z) {
        this.mMmtelCallComposerSupported = z;
    }

    public String[] getExts() {
        return this.mExts;
    }

    public void setExts(String[] strArr) {
        this.mExts = strArr;
    }

    public long getCapTimestamp() {
        return this.mCapTimestamp;
    }

    public void setCapTimestamp(long j) {
        this.mCapTimestamp = j;
    }

    public void addCapability(String str, String str2) {
        this.mCapInfoMap.put(str, str2);
    }

    public String getCapabilityVersions(String str) {
        return this.mCapInfoMap.get(str);
    }

    public void removeCapability(String str) {
        this.mCapInfoMap.remove(str);
    }

    public void setCapInfoMap(Map<String, String> map) {
        this.mCapInfoMap = map;
    }

    public Map<String, String> getCapInfoMap() {
        return this.mCapInfoMap;
    }

    public boolean isCapabilitySupported(String str) {
        return this.mCapInfoMap.containsKey(str);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mImSupported ? 1 : 0);
        parcel.writeInt(this.mFtSupported ? 1 : 0);
        parcel.writeInt(this.mFtThumbSupported ? 1 : 0);
        parcel.writeInt(this.mFtSnFSupported ? 1 : 0);
        parcel.writeInt(this.mFtHttpSupported ? 1 : 0);
        parcel.writeInt(this.mIsSupported ? 1 : 0);
        parcel.writeInt(this.mVsDuringCSSupported ? 1 : 0);
        parcel.writeInt(this.mVsSupported ? 1 : 0);
        parcel.writeInt(this.mSpSupported ? 1 : 0);
        parcel.writeInt(this.mCdViaPresenceSupported ? 1 : 0);
        parcel.writeInt(this.mIpVoiceSupported ? 1 : 0);
        parcel.writeInt(this.mIpVideoSupported ? 1 : 0);
        parcel.writeInt(this.mGeoPullFtSupported ? 1 : 0);
        parcel.writeInt(this.mGeoPullSupported ? 1 : 0);
        parcel.writeInt(this.mGeoPushSupported ? 1 : 0);
        parcel.writeInt(this.mSmSupported ? 1 : 0);
        parcel.writeInt(this.mFullSnFGroupChatSupported ? 1 : 0);
        parcel.writeInt(this.mGeoSmsSupported ? 1 : 0);
        parcel.writeInt(this.mCallComposerSupported ? 1 : 0);
        parcel.writeInt(this.mPostCallSupported ? 1 : 0);
        parcel.writeInt(this.mSharedMapSupported ? 1 : 0);
        parcel.writeInt(this.mSharedSketchSupported ? 1 : 0);
        parcel.writeInt(this.mChatbotSupported ? 1 : 0);
        parcel.writeInt(this.mChatbotRoleSupported ? 1 : 0);
        parcel.writeInt(this.mSmChatbotSupported ? 1 : 0);
        parcel.writeInt(this.mMmtelCallComposerSupported ? 1 : 0);
        parcel.writeInt(this.mRcsIpVoiceCallSupported ? 1 : 0);
        parcel.writeInt(this.mRcsIpVideoCallSupported ? 1 : 0);
        parcel.writeInt(this.mRcsIpVideoOnlyCallSupported ? 1 : 0);
        parcel.writeStringArray(this.mExts);
        parcel.writeLong(this.mCapTimestamp);
        Bundle bundle = new Bundle();
        for (Map.Entry<String, String> entry : this.mCapInfoMap.entrySet()) {
            bundle.putString(entry.getKey(), entry.getValue());
        }
        parcel.writeBundle(bundle);
    }

    private CapInfo(Parcel parcel) {
        this.mImSupported = false;
        this.mFtSupported = false;
        this.mFtThumbSupported = false;
        this.mFtSnFSupported = false;
        this.mFtHttpSupported = false;
        this.mIsSupported = false;
        this.mVsDuringCSSupported = false;
        this.mVsSupported = false;
        this.mSpSupported = false;
        this.mCdViaPresenceSupported = false;
        this.mIpVoiceSupported = false;
        this.mIpVideoSupported = false;
        this.mGeoPullFtSupported = false;
        this.mGeoPullSupported = false;
        this.mGeoPushSupported = false;
        this.mSmSupported = false;
        this.mFullSnFGroupChatSupported = false;
        this.mRcsIpVoiceCallSupported = false;
        this.mRcsIpVideoCallSupported = false;
        this.mRcsIpVideoOnlyCallSupported = false;
        this.mGeoSmsSupported = false;
        this.mCallComposerSupported = false;
        this.mPostCallSupported = false;
        this.mSharedMapSupported = false;
        this.mSharedSketchSupported = false;
        this.mChatbotSupported = false;
        this.mChatbotRoleSupported = false;
        this.mSmChatbotSupported = false;
        this.mMmtelCallComposerSupported = false;
        this.mExts = new String[10];
        this.mCapTimestamp = 0L;
        this.mCapInfoMap = new HashMap();
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.mImSupported = parcel.readInt() != 0;
        this.mFtSupported = parcel.readInt() != 0;
        this.mFtThumbSupported = parcel.readInt() != 0;
        this.mFtSnFSupported = parcel.readInt() != 0;
        this.mFtHttpSupported = parcel.readInt() != 0;
        this.mIsSupported = parcel.readInt() != 0;
        this.mVsDuringCSSupported = parcel.readInt() != 0;
        this.mVsSupported = parcel.readInt() != 0;
        this.mSpSupported = parcel.readInt() != 0;
        this.mCdViaPresenceSupported = parcel.readInt() != 0;
        this.mIpVoiceSupported = parcel.readInt() != 0;
        this.mIpVideoSupported = parcel.readInt() != 0;
        this.mGeoPullFtSupported = parcel.readInt() != 0;
        this.mGeoPullSupported = parcel.readInt() != 0;
        this.mGeoPushSupported = parcel.readInt() != 0;
        this.mSmSupported = parcel.readInt() != 0;
        this.mFullSnFGroupChatSupported = parcel.readInt() != 0;
        this.mGeoSmsSupported = parcel.readInt() != 0;
        this.mCallComposerSupported = parcel.readInt() != 0;
        this.mPostCallSupported = parcel.readInt() != 0;
        this.mSharedMapSupported = parcel.readInt() != 0;
        this.mSharedSketchSupported = parcel.readInt() != 0;
        this.mChatbotSupported = parcel.readInt() != 0;
        this.mChatbotRoleSupported = parcel.readInt() != 0;
        this.mSmChatbotSupported = parcel.readInt() != 0;
        this.mMmtelCallComposerSupported = parcel.readInt() != 0;
        this.mRcsIpVoiceCallSupported = parcel.readInt() != 0;
        this.mRcsIpVideoCallSupported = parcel.readInt() != 0;
        this.mRcsIpVideoOnlyCallSupported = parcel.readInt() != 0;
        this.mExts = parcel.createStringArray();
        this.mCapTimestamp = parcel.readLong();
        Bundle readBundle = parcel.readBundle();
        for (String str : readBundle.keySet()) {
            this.mCapInfoMap.put(str, readBundle.getString(str));
        }
    }
}
