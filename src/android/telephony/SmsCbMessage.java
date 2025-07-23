package android.telephony;

import android.annotation.SystemApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Telephony;
import android.telephony.CbGeoUtils;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

@SystemApi
/* loaded from: classes4.dex */
public final class SmsCbMessage implements Parcelable {
    public static final Parcelable.Creator<SmsCbMessage> CREATOR = new Parcelable.Creator<SmsCbMessage>() { // from class: android.telephony.SmsCbMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmsCbMessage createFromParcel(Parcel parcel) {
            return new SmsCbMessage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SmsCbMessage[] newArray(int i) {
            return new SmsCbMessage[i];
        }
    };
    public static final int GEOGRAPHICAL_SCOPE_CELL_WIDE = 3;
    public static final int GEOGRAPHICAL_SCOPE_CELL_WIDE_IMMEDIATE = 0;
    public static final int GEOGRAPHICAL_SCOPE_LOCATION_AREA_WIDE = 2;
    public static final int GEOGRAPHICAL_SCOPE_PLMN_WIDE = 1;
    public static final String LOG_TAG = "SMSCB";
    public static final int MAXIMUM_WAIT_TIME_NOT_SET = 255;
    public static final int MESSAGE_FORMAT_3GPP = 1;
    public static final int MESSAGE_FORMAT_3GPP2 = 2;
    public static final int MESSAGE_PRIORITY_EMERGENCY = 3;
    public static final int MESSAGE_PRIORITY_INTERACTIVE = 1;
    public static final int MESSAGE_PRIORITY_NORMAL = 0;
    public static final int MESSAGE_PRIORITY_URGENT = 2;
    private final String mBody;
    private final SmsCbCmasInfo mCmasWarningInfo;
    private final int mDataCodingScheme;
    private final SmsCbEtwsInfo mEtwsWarningInfo;
    private final int mGeographicalScope;
    private final List<CbGeoUtils.Geometry> mGeometries;
    private final String mLanguage;
    private final SmsCbLocation mLocation;
    private final int mMaximumWaitTimeSec;
    private final int mMessageFormat;
    private final int mPriority;
    private final long mReceivedTimeMillis;
    private final int mSerialNumber;
    private final int mServiceCategory;
    private final int mSlotIndex;
    private final int mSubId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface GeographicalScope {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MessageFormat {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MessagePriority {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SmsCbMessage(int i, int i2, int i3, SmsCbLocation smsCbLocation, int i4, String str, String str2, int i5, SmsCbEtwsInfo smsCbEtwsInfo, SmsCbCmasInfo smsCbCmasInfo, int i6, int i7) {
        this(i, i2, i3, smsCbLocation, i4, str, 0, str2, i5, smsCbEtwsInfo, smsCbCmasInfo, 0, null, System.currentTimeMillis(), i6, i7);
    }

    public SmsCbMessage(int i, int i2, int i3, SmsCbLocation smsCbLocation, int i4, String str, int i5, String str2, int i6, SmsCbEtwsInfo smsCbEtwsInfo, SmsCbCmasInfo smsCbCmasInfo, int i7, List<CbGeoUtils.Geometry> list, long j, int i8, int i9) {
        this.mMessageFormat = i;
        this.mGeographicalScope = i2;
        this.mSerialNumber = i3;
        this.mLocation = smsCbLocation;
        this.mServiceCategory = i4;
        this.mLanguage = str;
        this.mDataCodingScheme = i5;
        this.mBody = str2;
        this.mPriority = i6;
        this.mEtwsWarningInfo = smsCbEtwsInfo;
        this.mCmasWarningInfo = smsCbCmasInfo;
        this.mReceivedTimeMillis = j;
        this.mGeometries = list;
        this.mMaximumWaitTimeSec = i7;
        this.mSlotIndex = i8;
        this.mSubId = i9;
    }

    public SmsCbMessage(Parcel parcel) {
        this.mMessageFormat = parcel.readInt();
        this.mGeographicalScope = parcel.readInt();
        this.mSerialNumber = parcel.readInt();
        this.mLocation = new SmsCbLocation(parcel);
        this.mServiceCategory = parcel.readInt();
        this.mLanguage = parcel.readString();
        this.mDataCodingScheme = parcel.readInt();
        this.mBody = parcel.readString();
        this.mPriority = parcel.readInt();
        int readInt = parcel.readInt();
        if (readInt == 67) {
            this.mEtwsWarningInfo = null;
            this.mCmasWarningInfo = new SmsCbCmasInfo(parcel);
        } else if (readInt == 69) {
            this.mEtwsWarningInfo = new SmsCbEtwsInfo(parcel);
            this.mCmasWarningInfo = null;
        } else {
            this.mEtwsWarningInfo = null;
            this.mCmasWarningInfo = null;
        }
        this.mReceivedTimeMillis = parcel.readLong();
        String readString = parcel.readString();
        this.mGeometries = readString != null ? CbGeoUtils.parseGeometriesFromString(readString) : null;
        this.mMaximumWaitTimeSec = parcel.readInt();
        this.mSlotIndex = parcel.readInt();
        this.mSubId = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mMessageFormat);
        parcel.writeInt(this.mGeographicalScope);
        parcel.writeInt(this.mSerialNumber);
        this.mLocation.writeToParcel(parcel, i);
        parcel.writeInt(this.mServiceCategory);
        parcel.writeString(this.mLanguage);
        parcel.writeInt(this.mDataCodingScheme);
        parcel.writeString(this.mBody);
        parcel.writeInt(this.mPriority);
        if (this.mEtwsWarningInfo != null) {
            parcel.writeInt(69);
            this.mEtwsWarningInfo.writeToParcel(parcel, i);
        } else if (this.mCmasWarningInfo != null) {
            parcel.writeInt(67);
            this.mCmasWarningInfo.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(48);
        }
        parcel.writeLong(this.mReceivedTimeMillis);
        List<CbGeoUtils.Geometry> list = this.mGeometries;
        parcel.writeString(list != null ? CbGeoUtils.encodeGeometriesToString(list) : null);
        parcel.writeInt(this.mMaximumWaitTimeSec);
        parcel.writeInt(this.mSlotIndex);
        parcel.writeInt(this.mSubId);
    }

    public int getGeographicalScope() {
        return this.mGeographicalScope;
    }

    public int getSerialNumber() {
        return this.mSerialNumber;
    }

    public SmsCbLocation getLocation() {
        return this.mLocation;
    }

    public int getServiceCategory() {
        return this.mServiceCategory;
    }

    public String getLanguageCode() {
        return this.mLanguage;
    }

    public int getDataCodingScheme() {
        return this.mDataCodingScheme;
    }

    public String getMessageBody() {
        return this.mBody;
    }

    @SystemApi
    public List<CbGeoUtils.Geometry> getGeometries() {
        List<CbGeoUtils.Geometry> list = this.mGeometries;
        return list == null ? new ArrayList() : list;
    }

    public int getMaximumWaitingDuration() {
        return this.mMaximumWaitTimeSec;
    }

    public long getReceivedTime() {
        return this.mReceivedTimeMillis;
    }

    public int getSlotIndex() {
        return this.mSlotIndex;
    }

    public int getSubscriptionId() {
        return this.mSubId;
    }

    public int getMessageFormat() {
        return this.mMessageFormat;
    }

    public int getMessagePriority() {
        return this.mPriority;
    }

    public SmsCbEtwsInfo getEtwsWarningInfo() {
        return this.mEtwsWarningInfo;
    }

    public SmsCbCmasInfo getCmasWarningInfo() {
        return this.mCmasWarningInfo;
    }

    public boolean isEmergencyMessage() {
        return this.mPriority == 3;
    }

    public boolean isEtwsMessage() {
        return this.mEtwsWarningInfo != null;
    }

    public boolean isCmasMessage() {
        return this.mCmasWarningInfo != null;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder("SmsCbMessage{geographicalScope=");
        sb.append(this.mGeographicalScope);
        sb.append(", serialNumber=");
        sb.append(this.mSerialNumber);
        sb.append(", location=");
        sb.append(this.mLocation);
        sb.append(", serviceCategory=");
        sb.append(this.mServiceCategory);
        sb.append(", language=");
        sb.append(this.mLanguage);
        sb.append(", body=");
        sb.append(this.mBody);
        sb.append(", priority=");
        sb.append(this.mPriority);
        String str2 = "";
        if (this.mEtwsWarningInfo != null) {
            str = ", " + this.mEtwsWarningInfo.toString();
        } else {
            str = "";
        }
        sb.append(str);
        if (this.mCmasWarningInfo != null) {
            str2 = ", " + this.mCmasWarningInfo.toString();
        }
        sb.append(str2);
        sb.append(", maximumWaitingTime=");
        sb.append(this.mMaximumWaitTimeSec);
        sb.append(", received time=");
        sb.append(this.mReceivedTimeMillis);
        sb.append(", slotIndex = ");
        sb.append(this.mSlotIndex);
        sb.append(", geo=");
        List<CbGeoUtils.Geometry> list = this.mGeometries;
        sb.append(list != null ? CbGeoUtils.encodeGeometriesToString(list) : PerfettoProtoLogImpl.NULL_STRING);
        sb.append('}');
        return sb.toString();
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues(16);
        contentValues.put("slot_index", Integer.valueOf(this.mSlotIndex));
        contentValues.put("sub_id", Integer.valueOf(this.mSubId));
        contentValues.put(Telephony.CellBroadcasts.GEOGRAPHICAL_SCOPE, Integer.valueOf(this.mGeographicalScope));
        if (this.mLocation.getPlmn() != null) {
            contentValues.put("plmn", this.mLocation.getPlmn());
        }
        if (this.mLocation.getLac() != -1) {
            contentValues.put(Telephony.CellBroadcasts.LAC, Integer.valueOf(this.mLocation.getLac()));
        }
        if (this.mLocation.getCid() != -1) {
            contentValues.put("cid", Integer.valueOf(this.mLocation.getCid()));
        }
        contentValues.put("serial_number", Integer.valueOf(getSerialNumber()));
        contentValues.put(Telephony.CellBroadcasts.SERVICE_CATEGORY, Integer.valueOf(getServiceCategory()));
        contentValues.put("language", getLanguageCode());
        contentValues.put(Telephony.CellBroadcasts.DATA_CODING_SCHEME, Integer.valueOf(getDataCodingScheme()));
        contentValues.put("body", getMessageBody());
        contentValues.put(Telephony.CellBroadcasts.MESSAGE_FORMAT, Integer.valueOf(getMessageFormat()));
        contentValues.put("priority", Integer.valueOf(getMessagePriority()));
        SmsCbEtwsInfo etwsWarningInfo = getEtwsWarningInfo();
        if (etwsWarningInfo != null) {
            contentValues.put(Telephony.CellBroadcasts.ETWS_WARNING_TYPE, Integer.valueOf(etwsWarningInfo.getWarningType()));
            contentValues.put(Telephony.CellBroadcasts.ETWS_IS_PRIMARY, Boolean.valueOf(etwsWarningInfo.isPrimary()));
        }
        SmsCbCmasInfo cmasWarningInfo = getCmasWarningInfo();
        if (cmasWarningInfo != null) {
            contentValues.put(Telephony.CellBroadcasts.CMAS_MESSAGE_CLASS, Integer.valueOf(cmasWarningInfo.getMessageClass()));
            contentValues.put(Telephony.CellBroadcasts.CMAS_CATEGORY, Integer.valueOf(cmasWarningInfo.getCategory()));
            contentValues.put(Telephony.CellBroadcasts.CMAS_RESPONSE_TYPE, Integer.valueOf(cmasWarningInfo.getResponseType()));
            contentValues.put(Telephony.CellBroadcasts.CMAS_SEVERITY, Integer.valueOf(cmasWarningInfo.getSeverity()));
            contentValues.put(Telephony.CellBroadcasts.CMAS_URGENCY, Integer.valueOf(cmasWarningInfo.getUrgency()));
            contentValues.put(Telephony.CellBroadcasts.CMAS_CERTAINTY, Integer.valueOf(cmasWarningInfo.getCertainty()));
        }
        contentValues.put(Telephony.CellBroadcasts.RECEIVED_TIME, Long.valueOf(this.mReceivedTimeMillis));
        List<CbGeoUtils.Geometry> list = this.mGeometries;
        if (list != null) {
            contentValues.put(Telephony.CellBroadcasts.GEOMETRIES, CbGeoUtils.encodeGeometriesToString(list));
        } else {
            contentValues.put(Telephony.CellBroadcasts.GEOMETRIES, (String) null);
        }
        contentValues.put(Telephony.CellBroadcasts.MAXIMUM_WAIT_TIME, Integer.valueOf(this.mMaximumWaitTimeSec));
        return contentValues;
    }

    public static SmsCbMessage createFromCursor(Cursor cursor) {
        SmsCbEtwsInfo smsCbEtwsInfo;
        SmsCbCmasInfo smsCbCmasInfo;
        int i = cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.CellBroadcasts.GEOGRAPHICAL_SCOPE));
        int i2 = cursor.getInt(cursor.getColumnIndexOrThrow("serial_number"));
        int i3 = cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.CellBroadcasts.SERVICE_CATEGORY));
        String string = cursor.getString(cursor.getColumnIndexOrThrow("language"));
        String string2 = cursor.getString(cursor.getColumnIndexOrThrow("body"));
        int i4 = cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.CellBroadcasts.MESSAGE_FORMAT));
        int i5 = cursor.getInt(cursor.getColumnIndexOrThrow("priority"));
        int i6 = cursor.getInt(cursor.getColumnIndexOrThrow("slot_index"));
        int i7 = cursor.getInt(cursor.getColumnIndexOrThrow("sub_id"));
        int columnIndex = cursor.getColumnIndex("plmn");
        int i8 = -1;
        String string3 = (columnIndex == -1 || cursor.isNull(columnIndex)) ? null : cursor.getString(columnIndex);
        int columnIndex2 = cursor.getColumnIndex(Telephony.CellBroadcasts.LAC);
        int i9 = (columnIndex2 == -1 || cursor.isNull(columnIndex2)) ? -1 : cursor.getInt(columnIndex2);
        int columnIndex3 = cursor.getColumnIndex("cid");
        SmsCbLocation smsCbLocation = new SmsCbLocation(string3, i9, (columnIndex3 == -1 || cursor.isNull(columnIndex3)) ? -1 : cursor.getInt(columnIndex3));
        int columnIndex4 = cursor.getColumnIndex(Telephony.CellBroadcasts.ETWS_WARNING_TYPE);
        int columnIndex5 = cursor.getColumnIndex(Telephony.CellBroadcasts.ETWS_IS_PRIMARY);
        if (columnIndex4 == -1 || cursor.isNull(columnIndex4) || columnIndex5 == -1 || cursor.isNull(columnIndex5)) {
            smsCbEtwsInfo = null;
        } else {
            smsCbEtwsInfo = new SmsCbEtwsInfo(cursor.getInt(columnIndex4), false, false, cursor.getInt(columnIndex5) != 0, null);
        }
        int columnIndex6 = cursor.getColumnIndex(Telephony.CellBroadcasts.CMAS_MESSAGE_CLASS);
        if (columnIndex6 == -1 || cursor.isNull(columnIndex6)) {
            smsCbCmasInfo = null;
        } else {
            int i10 = cursor.getInt(columnIndex6);
            int columnIndex7 = cursor.getColumnIndex(Telephony.CellBroadcasts.CMAS_CATEGORY);
            int i11 = (columnIndex7 == -1 || cursor.isNull(columnIndex7)) ? -1 : cursor.getInt(columnIndex7);
            int columnIndex8 = cursor.getColumnIndex(Telephony.CellBroadcasts.CMAS_RESPONSE_TYPE);
            int i12 = (columnIndex8 == -1 || cursor.isNull(columnIndex8)) ? -1 : cursor.getInt(columnIndex8);
            int columnIndex9 = cursor.getColumnIndex(Telephony.CellBroadcasts.CMAS_SEVERITY);
            int i13 = (columnIndex9 == -1 || cursor.isNull(columnIndex9)) ? -1 : cursor.getInt(columnIndex9);
            int columnIndex10 = cursor.getColumnIndex(Telephony.CellBroadcasts.CMAS_URGENCY);
            int i14 = (columnIndex10 == -1 || cursor.isNull(columnIndex10)) ? -1 : cursor.getInt(columnIndex10);
            int columnIndex11 = cursor.getColumnIndex(Telephony.CellBroadcasts.CMAS_CERTAINTY);
            if (columnIndex11 != -1 && !cursor.isNull(columnIndex11)) {
                i8 = cursor.getInt(columnIndex11);
            }
            smsCbCmasInfo = new SmsCbCmasInfo(i10, i11, i12, i13, i14, i8);
        }
        String string4 = cursor.getString(cursor.getColumnIndex(Telephony.CellBroadcasts.GEOMETRIES));
        return new SmsCbMessage(i4, i, i2, smsCbLocation, i3, string, 0, string2, i5, smsCbEtwsInfo, smsCbCmasInfo, cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.CellBroadcasts.MAXIMUM_WAIT_TIME)), string4 != null ? CbGeoUtils.parseGeometriesFromString(string4) : null, cursor.getLong(cursor.getColumnIndexOrThrow(Telephony.CellBroadcasts.RECEIVED_TIME)), i6, i7);
    }

    public boolean needGeoFencingCheck() {
        List<CbGeoUtils.Geometry> list;
        return (this.mMaximumWaitTimeSec <= 0 || (list = this.mGeometries) == null || list.isEmpty()) ? false : true;
    }

    public int getCmasCategory() {
        try {
            return this.mCmasWarningInfo.getCategory();
        } catch (NullPointerException unused) {
            com.android.telephony.Rlog.e(LOG_TAG, "Null pointer exception in getCmasCategory");
            return 0;
        }
    }

    public int getCmasResponseType() {
        try {
            return this.mCmasWarningInfo.getResponseType();
        } catch (NullPointerException unused) {
            com.android.telephony.Rlog.e(LOG_TAG, "Null pointer exception in getCmasResponseType");
            return 0;
        }
    }

    public int getCmasSeverity() {
        try {
            return this.mCmasWarningInfo.getSeverity();
        } catch (NullPointerException unused) {
            com.android.telephony.Rlog.e(LOG_TAG, "Null pointer exception in getCmasSeverity");
            return 0;
        }
    }

    public int getCmasUrgency() {
        try {
            return this.mCmasWarningInfo.getUrgency();
        } catch (NullPointerException unused) {
            com.android.telephony.Rlog.e(LOG_TAG, "Null pointer exception in getCmasUrgency");
            return 0;
        }
    }

    public int getCmasCertainty() {
        try {
            return this.mCmasWarningInfo.getCertainty();
        } catch (NullPointerException unused) {
            com.android.telephony.Rlog.e(LOG_TAG, "Null pointer exception in getCmasCertainty");
            return 0;
        }
    }

    public int getCmasMessageId() {
        try {
            return this.mCmasWarningInfo.getMessageID();
        } catch (NullPointerException unused) {
            com.android.telephony.Rlog.e(LOG_TAG, "Null pointer exception in getCmasMessageID");
            return 0;
        }
    }

    public int getCmasAlertHandling() {
        try {
            return this.mCmasWarningInfo.getAlertHandling();
        } catch (NullPointerException unused) {
            com.android.telephony.Rlog.e(LOG_TAG, "Null pointer exception in getCmasAlertHandling");
            return 0;
        }
    }

    public long getCmasMsgExpires() {
        try {
            return this.mCmasWarningInfo.getMsgExpires();
        } catch (NullPointerException unused) {
            com.android.telephony.Rlog.e(LOG_TAG, "Null pointer exception in getCmasMsgExpires");
            return 0L;
        }
    }

    public boolean getCmasRecordTypeFirstExists() {
        try {
            return this.mCmasWarningInfo.getCMASRecordTypeFirstExists();
        } catch (NullPointerException unused) {
            com.android.telephony.Rlog.e(LOG_TAG, "Null pointer exception in getCmasRecordTypeFirstExists");
            return false;
        }
    }

    public boolean getCmasRecordTypeSecondExists() {
        try {
            return this.mCmasWarningInfo.getCMASRecordTypeSecondExists();
        } catch (NullPointerException unused) {
            com.android.telephony.Rlog.e(LOG_TAG, "Null pointer exception in getCmasRecordTypeSecondExists");
            return false;
        }
    }
}
