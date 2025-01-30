package com.sec.ims;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class IMSParameter implements Parcelable {
    public static final Parcelable.Creator<IMSParameter> CREATOR = new Parcelable.Creator<IMSParameter>() { // from class: com.sec.ims.IMSParameter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IMSParameter createFromParcel(Parcel parcel) {
            return new IMSParameter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IMSParameter[] newArray(int i) {
            return new IMSParameter[i];
        }
    };
    private IMSAPCSInfo mAPCSInfo;
    private Bundle mPrimitiveMap;
    private IMSProfileParams[] mProfileParams;
    private IMSRegistrationInfo mRegistrationInfo;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class CALL {
        public static final String ACTION = "action";
        public static final String ADDED_SESSION_ID = "addedsessionid";
        public static final String ALERT_INFO = "alertinfo";
        public static final String AUDIO_CODEC = "audiocodec";
        public static final String BEARER_STATE = "bearerstate";
        public static final String BEARER_TYPE = "bearertype";
        public static final String CALL_ID = "callid";
        public static final String CALL_TYPE = "calltype";
        public static final String CAUSE = "cause";
        public static final String CODEC_BIT_RATE = "codecbitrate";
        public static final String CONFERENCE_SUPPORTED = "conferencesupported";
        public static final String DATA = "data";
        public static final String DCS = "dcs";
        public static final String DIRECTION = "direction";
        public static final String DURING_VIDEOCALL = "duringvideocall";
        public static final String EPDG_UNAVAILABLE_REASON = "epdgunavailablereason";
        public static final String EPDG_W2L_HO_PENDING = "epdgw2lhandoverpending";
        public static final String ERROR_CODE = "errorcode";
        public static final String ERROR_MESSAGE = "errormessage";
        public static final String EVENT = "event";
        public static final String HD_ICON = "hdicon";
        public static final String HISTORY = "history";
        public static final String HOLD_REQUEST = "holdrequest";
        public static final String IS_MO_CALL = "ismocall";
        public static final String LOCAL_HOLD_TONE = "localholdtone";
        public static final String MAXCONFERENCECALLUSERS = "maxconferencecallusers";
        public static final String MODIFY_HEADER = "modifyheader";
        public static final String MT_CONFERENCE = "mtconference";
        public static final String NUMBER_PLUS = "numberplus";
        public static final String PARTICIPANT_LIST = "participantlist";
        public static final String PEER_URI = "peeruri";
        public static final String PHOTO_RING_AVAILABLE = "photoringavailable";
        public static final String PLETTERING = "plettering";
        public static final String READ_SIP_MESSAGE = "readsipmessage";
        public static final String REASON = "reason";
        public static final String REMOVED_SESSION_ID = "removedsessionid";
        public static final String RESULT = "result";
        public static final String RESUME_REQUEST = "resumerequest";
        public static final String RETRY_AFTER_TIME = "retryaftertime";
        public static final String SERVICETYPE = "servicetype";
        public static final String SESSION_ID = "sessionid";
        public static final String STATUS = "status";
        public static final String TTY_TYPE = "ttytype";
        public static final String URI_LIST = "urilist";
        public static final String URN = "urn";
        public static final String VIDEO_ORIENTAION = "videoorientation";
        public static final String VMS_CALL = "vmscall";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class CALL_DIRECTION {
        public static final int CALL_DIRECTION_MO = 0;
        public static final int CALL_DIRECTION_MT = 1;
        public static final int CALL_DIRECTION_PULLED_MO = 2;
        public static final int CALL_DIRECTION_PULLED_MT = 3;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class GENERAL {
        public static final String APCS_INFO = "apcsinfo";
        public static final String CONNECTION_STATE = "connectionState";
        public static final String DATA_ENABLED = "dataEnabled";
        public static final String EPDG_ERROR_CODE = "epdgerrorcode";
        public static final String EPDG_HO_DIRECTION = "epdghandoverdirection";
        public static final String EXPIRY_TIME = "expirytime";
        public static final String NETWORK_INFO = "networkInfo";
        public static final String NETWORK_TYPE = "networktype";
        public static final String NEW_NETWORK_TYPE = "newnetworktype";
        public static final String OLD_NETWORK_TYPE = "oldnetworktype";
        public static final String REASON = "reason";
        public static final String REGISTRATION_INFO = "registrationinfo";
        public static final String SETTINGS_VALUE = "settingsvalue";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class MEDIA {
        public static final String CAMERA_ID = "cameraid";
        public static final String FILENAME = "filename";
        public static final String IS_NEAR_END = "isnearend";
        public static final String SESSION_ID = "media_sessionid";
        public static final String SUCCESS = "success";
        public static final String VIDEO_ORIENTATION = "videoorientation";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class PRESENCE {
        public static final String ERROR_CODE = "errorcode";
        public static final String ERROR_PHRASE = "reasonphrase";
        public static final String PIDF = "pidf";
        public static final String PROFILE_PARAMS = "profileparams";
        public static final String SUBSCRIBE_STATE = "subscribeState";
        public static final String TOKEN = "token";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class SMS {
        public static final String CONTENT_TYPE = "contenttype";
        public static final String DATA = "data";
        public static final String MESSAGE_ID = "messageid";
        public static final String REASON_CODE = "reasoncode";
        public static final String RETRY_AFTER = "retry_after";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class SSCONFIG {
        public static final String CB_TYPE = "cbtype";
        public static final String CF_TYPE = "cftype";
        public static final String CF_URI = "cfuri";
        public static final String CF_URI_LENGTH = "cfurilength";
        public static final String CF_URI_TYPE = "cfuritype";
        public static final String DATA = "data";
        public static final String END_TIME = "end_time";
        public static final String ERROR_MESSAGE = "errormessage";
        public static final String ERROR_TYPE = "errorcode";
        public static final String EVENT_TYPE = "eventtype";
        public static final String NO_REPLAY_TIME = "noreplaytime";
        public static final String NUMBER_DATE = "numberNdate";
        public static final String NUMBER_OF_CLASSES = "numberofclasses";
        public static final String RESULT = "result";
        public static final String SESSION_ID = "sessionid";
        public static final String SS_CLASS = "ssclass";
        public static final String SS_STATUS = "ssstatus";
        public static final String START_TIME = "start_time";
        public static final String SUB_EVENT_TYPE = "subeventtype";
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class TYPE_HOLDTONE {
        public static final int TYPE_HOLDTONE_START = 0;
        public static final int TYPE_HOLDTONE_STOP = 1;
        public static final int TYPE_HOLDTONE_STOP_WITHOUT_NOTIFICATION = 2;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class VZW_API_SUPPORT {
        public static final String DIALOG_ID = "dialogId";
        public static final String MESSAGE = "sipMessage";
        public static final String TOKEN = "token";
    }

    public IMSParameter() {
        this.mPrimitiveMap = new Bundle();
        this.mAPCSInfo = null;
        this.mRegistrationInfo = null;
        this.mProfileParams = null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean getBoolean(String str) {
        return getBoolean(str, false);
    }

    public Bundle getBundle(String str) {
        return this.mPrimitiveMap.getBundle(str);
    }

    public byte[] getByteArray(String str) {
        Object obj = this.mPrimitiveMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (byte[]) obj;
        } catch (ClassCastException unused) {
            return null;
        }
    }

    public int getInt(String str) {
        return getInt(str, 0);
    }

    public int[] getIntArray(String str) {
        Object obj = this.mPrimitiveMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (int[]) obj;
        } catch (ClassCastException unused) {
            return null;
        }
    }

    public ArrayList<Integer> getIntegerArrayList(String str) {
        Bundle bundle = (Bundle) getParcelable(str);
        if (bundle == null) {
            return null;
        }
        return bundle.getIntegerArrayList(str);
    }

    public long getLong(String str) {
        return getLong(str, 0L);
    }

    public Object getParcelable(String str) {
        if (str.equals(GENERAL.APCS_INFO)) {
            return this.mAPCSInfo;
        }
        if (str.equals(GENERAL.REGISTRATION_INFO)) {
            return this.mRegistrationInfo;
        }
        Object obj = this.mPrimitiveMap.get(str);
        if (obj == null) {
            return null;
        }
        return obj;
    }

    public Object[] getParcelableArray(String str) {
        if (str.equals(PRESENCE.PROFILE_PARAMS)) {
            return this.mProfileParams;
        }
        Object obj = this.mPrimitiveMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (Object[]) obj;
        } catch (ClassCastException unused) {
            return null;
        }
    }

    public SparseArray<String> getSparseStringArray(String str) {
        Bundle bundle = (Bundle) getParcelable(str);
        if (bundle == null) {
            return null;
        }
        SparseArray<String> sparseArray = new SparseArray<>();
        for (String str2 : bundle.keySet()) {
            try {
                sparseArray.put(Integer.parseInt(str2), bundle.getString(str2));
            } catch (NumberFormatException unused) {
                return null;
            }
        }
        return sparseArray;
    }

    public String getString(String str) {
        Object obj = this.mPrimitiveMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (String) obj;
        } catch (ClassCastException unused) {
            return null;
        }
    }

    public String[] getStringArray(String str) {
        Object obj = this.mPrimitiveMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (String[]) obj;
        } catch (ClassCastException unused) {
            return null;
        }
    }

    public ArrayList<String> getStringArrayList(String str) {
        Bundle bundle = (Bundle) getParcelable(str);
        if (bundle == null) {
            return null;
        }
        return bundle.getStringArrayList(str);
    }

    public void putBoolean(String str, boolean z) {
        this.mPrimitiveMap.putBoolean(str, z);
    }

    public void putBundle(String str, Bundle bundle) {
        this.mPrimitiveMap.putBundle(str, bundle);
    }

    public void putByteArray(String str, byte[] bArr) {
        this.mPrimitiveMap.putByteArray(str, bArr);
    }

    public void putInt(String str, int i) {
        this.mPrimitiveMap.putInt(str, i);
    }

    public void putIntArray(String str, int[] iArr) {
        this.mPrimitiveMap.putIntArray(str, iArr);
    }

    public void putIntegerArrayList(String str, ArrayList<Integer> arrayList) {
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(str, arrayList);
        putParcelable(str, bundle);
    }

    public void putLong(String str, long j) {
        this.mPrimitiveMap.putLong(str, j);
    }

    public void putParcelable(String str, Parcelable parcelable) {
        if (str.equals(GENERAL.APCS_INFO) && (parcelable instanceof IMSAPCSInfo)) {
            try {
                this.mAPCSInfo = ((IMSAPCSInfo) parcelable).m2823clone();
                return;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return;
            }
        }
        if (!str.equals(GENERAL.REGISTRATION_INFO) || !(parcelable instanceof IMSRegistrationInfo)) {
            this.mPrimitiveMap.putParcelable(str, parcelable);
            return;
        }
        try {
            this.mRegistrationInfo = ((IMSRegistrationInfo) parcelable).m2825clone();
        } catch (CloneNotSupportedException e2) {
            e2.printStackTrace();
        }
    }

    public void putParcelableArray(String str, Parcelable[] parcelableArr) {
        if (!str.equals(PRESENCE.PROFILE_PARAMS) || !(parcelableArr instanceof IMSProfileParams[])) {
            this.mPrimitiveMap.putParcelableArray(str, parcelableArr);
            return;
        }
        try {
            this.mProfileParams = new IMSProfileParams[parcelableArr.length];
            for (int i = 0; i < parcelableArr.length; i++) {
                this.mProfileParams[i] = ((IMSProfileParams) parcelableArr[i]).m2824clone();
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void putSparseStringArray(String str, SparseArray<String> sparseArray) {
        Bundle bundle = new Bundle();
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            bundle.putString(String.valueOf(sparseArray.keyAt(i)), sparseArray.valueAt(i));
        }
        putParcelable(str, bundle);
    }

    public void putString(String str, String str2) {
        this.mPrimitiveMap.putString(str, str2);
    }

    public void putStringArray(String str, String[] strArr) {
        this.mPrimitiveMap.putStringArray(str, strArr);
    }

    public void putStringArrayList(String str, ArrayList<String> arrayList) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(str, arrayList);
        putParcelable(str, bundle);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mPrimitiveMap);
        if (this.mAPCSInfo == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeParcelable(this.mAPCSInfo, i);
        }
        if (this.mRegistrationInfo == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeParcelable(this.mRegistrationInfo, i);
        }
        IMSProfileParams[] iMSProfileParamsArr = this.mProfileParams;
        if (iMSProfileParamsArr == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(iMSProfileParamsArr.length);
        for (IMSProfileParams iMSProfileParams : this.mProfileParams) {
            parcel.writeParcelable(iMSProfileParams, i);
        }
    }

    public boolean getBoolean(String str, boolean z) {
        Object obj = this.mPrimitiveMap.get(str);
        if (obj == null) {
            return z;
        }
        try {
            return ((Boolean) obj).booleanValue();
        } catch (ClassCastException unused) {
            return z;
        }
    }

    public int getInt(String str, int i) {
        Object obj = this.mPrimitiveMap.get(str);
        if (obj == null) {
            return i;
        }
        try {
            return ((Integer) obj).intValue();
        } catch (ClassCastException unused) {
            return i;
        }
    }

    public long getLong(String str, long j) {
        Object obj = this.mPrimitiveMap.get(str);
        if (obj == null) {
            return j;
        }
        try {
            return ((Long) obj).longValue();
        } catch (ClassCastException unused) {
            return j;
        }
    }

    public String getString(String str, String str2) {
        Object obj = this.mPrimitiveMap.get(str);
        if (obj == null) {
            return str2;
        }
        try {
            return (String) obj;
        } catch (ClassCastException unused) {
            return str2;
        }
    }

    public IMSParameter(Parcel parcel) {
        this.mPrimitiveMap = new Bundle();
        this.mAPCSInfo = null;
        this.mRegistrationInfo = null;
        this.mProfileParams = null;
        this.mPrimitiveMap = parcel.readBundle();
        if (parcel.readInt() != 0) {
            this.mAPCSInfo = (IMSAPCSInfo) parcel.readParcelable(IMSAPCSInfo.class.getClassLoader());
        }
        if (parcel.readInt() != 0) {
            this.mRegistrationInfo = (IMSRegistrationInfo) parcel.readParcelable(IMSRegistrationInfo.class.getClassLoader());
        }
        int readInt = parcel.readInt();
        if (readInt > 0) {
            this.mProfileParams = new IMSProfileParams[readInt];
            for (int i = 0; i < readInt; i++) {
                this.mProfileParams[i] = (IMSProfileParams) parcel.readParcelable(IMSProfileParams.class.getClassLoader());
            }
        }
    }
}
