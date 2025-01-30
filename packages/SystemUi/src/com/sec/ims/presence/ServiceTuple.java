package com.sec.ims.presence;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import com.sec.ims.options.Capabilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ServiceTuple {
    public static final String BASIC_STATUS_CLOSED = "closed";
    public static final String BASIC_STATUS_OPEN = "open";
    public static final String MEDIA_CAP_AUDIO = "audio";
    public static final String MEDIA_CAP_FULL_DUPLEX = "duplex";
    public static final String MEDIA_CAP_VIDEO = "video";
    private static Map<Long, String> sServiceDescriptionMap;
    private static final Map<Long, ServiceTuple> sServiceTuples;
    public String basicStatus;
    public String description;
    public String displayText;
    public long feature;
    public List<String> mediaCapabilities;
    public String serviceId;
    public String tupleId;
    public String version;

    static {
        HashMap hashMap = new HashMap();
        sServiceDescriptionMap = hashMap;
        hashMap.put(Long.valueOf(Capabilities.FEATURE_STANDALONE_MSG), "StandaloneMsg");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_STANDALONE_MSG_V1), "StandaloneMsg");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_CHAT_SIMPLE_IM), "Session Mode Messaging");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_CHAT_CPM), "Session Mode Messaging");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_SF_GROUP_CHAT), "FullStoreGrpChat");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_FT), "File Transfer");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_FT_THUMBNAIL), "FtThumbnail");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_FT_STORE), "FtStoreForward");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_FT_HTTP), "FileTransferHTTP");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_FT_HTTP_EXTRA), "FileTransferHTTPExtra");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_ISH), "ImageShare");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_VSH), "VideoShare");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_VSH_OUTSIDE_CALL), "VideoShareOutsideCall");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_SOCIAL_PRESENCE), "SocialPresence");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_PRESENCE_DISCOVERY), "DiscoveryPresence");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_MMTEL), "IPVoiceCall");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_MMTEL_VIDEO), "IPVideoCall");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_IPCALL), "RcsIPVoiceCall");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_IPCALL_VIDEO), "RcsIPVideoCall");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_IPCALL_VIDEO_ONLY), "RcsIPVideoCallOnly");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_GEOLOCATION_PULL), "GeolocPull");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_GEOLOCATION_PULL_FT), "GeolocPullFT");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_GEOLOCATION_PUSH), "GeolocPush");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_STICKER), PluginLockStar.STICKER_TYPE);
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_FT_VIA_SMS), "FileTransferSMS");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_GEO_VIA_SMS), "GeolocSMS");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_CHATBOT_CHAT_SESSION), "ChatbotChatSession");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_CHATBOT_STANDALONE_MSG), "ChatbotStandaloneMsg");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_CHATBOT_EXTENDED_MSG), "ExtendedBotMsg");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_CHATBOT_ROLE), "ChatbotRole");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_PLUG_IN), "PlugIn");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_ENRICHED_CALL_COMPOSER), "CallComposer");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_ENRICHED_POST_CALL), "PostCall");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_MMTEL_CALL_COMPOSER), "MmtelCallComposer");
        sServiceDescriptionMap.put(Long.valueOf(Capabilities.FEATURE_CANCEL_MESSAGE), "CancelMessage");
        HashMap hashMap2 = new HashMap();
        sServiceTuples = hashMap2;
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_STANDALONE_MSG_V1), new ServiceTuple(Capabilities.FEATURE_STANDALONE_MSG_V1, "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.sm", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_STANDALONE_MSG), new ServiceTuple(Capabilities.FEATURE_STANDALONE_MSG, "org.openmobilealliance:StandaloneMsg", KnoxVpnPolicyConstants.NEW_FW));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_CHAT_SIMPLE_IM), new ServiceTuple(Capabilities.FEATURE_CHAT_SIMPLE_IM, "org.openmobilealliance:IM-session", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_CHAT_CPM), new ServiceTuple(Capabilities.FEATURE_CHAT_CPM, "org.openmobilealliance:ChatSession", KnoxVpnPolicyConstants.NEW_FW));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_SF_GROUP_CHAT), new ServiceTuple(Capabilities.FEATURE_SF_GROUP_CHAT, "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.fullsfgroupchat", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_FT), new ServiceTuple(Capabilities.FEATURE_FT, "org.openmobilealliance:File-Transfer", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_FT_THUMBNAIL_V1), new ServiceTuple(Capabilities.FEATURE_FT_THUMBNAIL_V1, "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.ftthumb", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_FT_THUMBNAIL), new ServiceTuple(Capabilities.FEATURE_FT_THUMBNAIL, "org.openmobilealliance:File-Transfer-thumb", KnoxVpnPolicyConstants.NEW_FW));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_FT_STORE), new ServiceTuple(Capabilities.FEATURE_FT_STORE, "org.openmobilealliance:File-Transfer", KnoxVpnPolicyConstants.NEW_FW));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_FT_HTTP), new ServiceTuple(Capabilities.FEATURE_FT_HTTP, "org.openmobilealliance:File-Transfer-HTTP", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_FT_HTTP_EXTRA), new ServiceTuple(Capabilities.FEATURE_FT_HTTP_EXTRA, "org.openmobilealliance:File-Transfer-HTTP-EXTRA", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_ISH), new ServiceTuple(Capabilities.FEATURE_ISH, "org.gsma.imageshare", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_VSH), new ServiceTuple(Capabilities.FEATURE_VSH, "org.gsma.videoshare", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_VSH_OUTSIDE_CALL), new ServiceTuple(Capabilities.FEATURE_VSH_OUTSIDE_CALL, "org.gsma.videoshare", KnoxVpnPolicyConstants.NEW_FW));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_SOCIAL_PRESENCE), new ServiceTuple(Capabilities.FEATURE_SOCIAL_PRESENCE, "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcse.sp", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_PRESENCE_DISCOVERY), new ServiceTuple(Capabilities.FEATURE_PRESENCE_DISCOVERY, "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcse.dp", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_MMTEL), new ServiceTuple(Capabilities.FEATURE_MMTEL, "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.mmtel", "1.0", MEDIA_CAP_AUDIO, MEDIA_CAP_FULL_DUPLEX));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_MMTEL_VIDEO), new ServiceTuple(Capabilities.FEATURE_MMTEL_VIDEO, "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.mmtel", "1.0", MEDIA_CAP_AUDIO, MEDIA_CAP_VIDEO, MEDIA_CAP_FULL_DUPLEX));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_IPCALL), new ServiceTuple(Capabilities.FEATURE_IPCALL, "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.mmtel.gsma.ipcall", "1.0", MEDIA_CAP_AUDIO, MEDIA_CAP_FULL_DUPLEX));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_IPCALL_VIDEO), new ServiceTuple(Capabilities.FEATURE_IPCALL_VIDEO, "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.mmtel.gsma.ipcall", "1.0", MEDIA_CAP_AUDIO, MEDIA_CAP_VIDEO, MEDIA_CAP_FULL_DUPLEX));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_IPCALL_VIDEO_ONLY), new ServiceTuple(Capabilities.FEATURE_IPCALL_VIDEO_ONLY, "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.mmtel.gsma.ipcall.ipvideocallonly", "1.0", MEDIA_CAP_AUDIO, MEDIA_CAP_VIDEO, MEDIA_CAP_FULL_DUPLEX));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_GEOLOCATION_PULL), new ServiceTuple(Capabilities.FEATURE_GEOLOCATION_PULL, "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.geopull", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_GEOLOCATION_PULL_FT), new ServiceTuple(Capabilities.FEATURE_GEOLOCATION_PULL_FT, "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.geopullft", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_GEOLOCATION_PUSH), new ServiceTuple(Capabilities.FEATURE_GEOLOCATION_PUSH, "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.geopush", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_STICKER), new ServiceTuple(Capabilities.FEATURE_STICKER, "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.sticker", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_FT_VIA_SMS), new ServiceTuple(Capabilities.FEATURE_FT_VIA_SMS, "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.ftsms", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_GEO_VIA_SMS), new ServiceTuple(Capabilities.FEATURE_GEO_VIA_SMS, "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.geosms", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_CHATBOT_CHAT_SESSION), new ServiceTuple(Capabilities.FEATURE_CHATBOT_CHAT_SESSION, "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.chatbot", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_CHATBOT_STANDALONE_MSG), new ServiceTuple(Capabilities.FEATURE_CHATBOT_STANDALONE_MSG, "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.chatbot.sa", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_CHATBOT_EXTENDED_MSG), new ServiceTuple(Capabilities.FEATURE_CHATBOT_EXTENDED_MSG, "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.chatbot.xbotmessage", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_CHATBOT_ROLE), new ServiceTuple(Capabilities.FEATURE_CHATBOT_ROLE, "org.gsma.rcs.isbot", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_PLUG_IN), new ServiceTuple(Capabilities.FEATURE_PLUG_IN, "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.plugin", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_ENRICHED_CALL_COMPOSER), new ServiceTuple(Capabilities.FEATURE_ENRICHED_CALL_COMPOSER, "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.gsma.callcomposer", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_ENRICHED_POST_CALL), new ServiceTuple(Capabilities.FEATURE_ENRICHED_POST_CALL, "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.gsma.callunanswered", "1.0"));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_MMTEL_CALL_COMPOSER), new ServiceTuple(Capabilities.FEATURE_MMTEL_CALL_COMPOSER, "org.3gpp.urn:urn-7:3gpp-service.ims.icsi.gsma.callcomposer", KnoxVpnPolicyConstants.NEW_FW));
        hashMap2.put(Long.valueOf(Capabilities.FEATURE_CANCEL_MESSAGE), new ServiceTuple(Capabilities.FEATURE_CANCEL_MESSAGE, "org.3gpp.urn:urn-7:3gpp-application.ims.iari.rcs.cancelmessage", "1.0"));
    }

    public ServiceTuple(long j, String str, String str2) {
        this.feature = j;
        this.serviceId = str;
        this.version = str2;
        this.description = sServiceDescriptionMap.get(Long.valueOf(j));
        this.basicStatus = BASIC_STATUS_OPEN;
        this.mediaCapabilities = null;
        this.tupleId = null;
        this.displayText = null;
    }

    public static long getFeatures(List<ServiceTuple> list) {
        Iterator<ServiceTuple> it = list.iterator();
        long j = 0;
        while (it.hasNext()) {
            j |= it.next().feature;
        }
        return j;
    }

    public static ServiceTuple getServiceTuple(long j) {
        return sServiceTuples.get(Long.valueOf(j));
    }

    public static List<ServiceTuple> getServiceTupleList(long j) {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<Long, ServiceTuple> entry : sServiceTuples.entrySet()) {
            if ((entry.getKey().longValue() & j) > 0) {
                arrayList.add(entry.getValue());
            }
        }
        Map<Long, ServiceTuple> map = sServiceTuples;
        if (arrayList.contains(map.get(Long.valueOf(Capabilities.FEATURE_MMTEL_VIDEO)))) {
            arrayList.remove(map.get(Long.valueOf(Capabilities.FEATURE_MMTEL)));
        }
        return arrayList;
    }

    public static void setDisplayText(long j, String str) {
        getServiceTuple(j).displayText = str;
    }

    public static void setServiceDescription(long j, String str) {
        sServiceDescriptionMap.put(Long.valueOf(j), str);
        getServiceTuple(j).description = sServiceDescriptionMap.get(Long.valueOf(j));
    }

    public static void setServiceVersion(long j, String str) {
        getServiceTuple(j).version = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ServiceTuple serviceTuple = (ServiceTuple) obj;
        String str = this.serviceId;
        if (str == null) {
            if (serviceTuple.serviceId != null) {
                return false;
            }
        } else if (!str.equals(serviceTuple.serviceId)) {
            return false;
        }
        String str2 = this.version;
        if (str2 == null) {
            if (serviceTuple.version != null) {
                return false;
            }
        } else if (!str2.equals(serviceTuple.version)) {
            return false;
        }
        List<String> list = this.mediaCapabilities;
        if (list == null) {
            if (serviceTuple.mediaCapabilities != null) {
                return false;
            }
        } else if (!list.equals(serviceTuple.mediaCapabilities)) {
            return false;
        }
        String str3 = this.displayText;
        if (str3 == null) {
            if (serviceTuple.displayText != null) {
                return false;
            }
        } else if (!str3.equals(serviceTuple.displayText)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        String str = this.serviceId;
        int hashCode = ((str == null ? 0 : str.hashCode()) + 31) * 31;
        String str2 = this.version;
        return hashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public String toString() {
        return "ServiceTuple [feature=" + this.feature + ", tupleId=" + this.tupleId + ", serviceId=" + this.serviceId + ", version=" + this.version + ", description=" + this.description + ", basicStatus=" + this.basicStatus + ", mediaCapabilities=" + this.mediaCapabilities + "]";
    }

    public static ServiceTuple getServiceTuple(String str, String str2, String... strArr) {
        ArrayList arrayList = new ArrayList();
        for (ServiceTuple serviceTuple : sServiceTuples.values()) {
            if (serviceTuple.serviceId.equalsIgnoreCase(str) && (str2 == null || serviceTuple.version.equals(str2))) {
                arrayList.add(serviceTuple);
            }
        }
        if (strArr != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ServiceTuple serviceTuple2 = (ServiceTuple) it.next();
                for (String str3 : strArr) {
                    List<String> list = serviceTuple2.mediaCapabilities;
                    if (list != null && !list.contains(str3)) {
                        it.remove();
                    }
                }
            }
        }
        if (arrayList.size() > 0) {
            return (ServiceTuple) arrayList.get(0);
        }
        return null;
    }

    public ServiceTuple(long j, String str, String str2, String... strArr) {
        this(j, str, str2);
        this.mediaCapabilities = Arrays.asList(strArr);
        this.displayText = null;
    }
}
