package com.google.android.mms.pdu;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.SystemProperties;
import android.provider.Telephony;
import android.telephony.PhoneNumberUtils;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.mms.ContentType;
import com.google.android.mms.InvalidHeaderValueException;
import com.google.android.mms.MmsException;
import com.google.android.mms.util.DownloadDrmHelper;
import com.google.android.mms.util.PduCache;
import com.google.android.mms.util.PduCacheEntry;
import com.google.android.mms.util.SqliteWrapper;
import com.samsung.android.feature.SemCscFeature;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Predicate;

/* loaded from: classes6.dex */
public class PduPersister {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final HashMap<Integer, Integer> CHARSET_COLUMN_INDEX_MAP;
    private static final HashMap<Integer, String> CHARSET_COLUMN_NAME_MAP;
    private static final boolean DEBUG = false;
    private static final HashMap<Integer, Integer> ENCODED_STRING_COLUMN_INDEX_MAP;
    private static final HashMap<Integer, String> ENCODED_STRING_COLUMN_NAME_MAP;
    private static final String ENCODING_PREFIX = "=?";
    private static final String ENCODING_SUFFIX = "?=";
    private static final boolean LOCAL_LOGV = false;
    private static final HashMap<Integer, Integer> LONG_COLUMN_INDEX_MAP;
    private static final HashMap<Integer, String> LONG_COLUMN_NAME_MAP;
    private static final HashMap<Uri, Integer> MESSAGE_BOX_MAP;
    private static final HashMap<Integer, Integer> OCTET_COLUMN_INDEX_MAP;
    private static final HashMap<Integer, String> OCTET_COLUMN_NAME_MAP;
    private static final int PART_COLUMN_CHARSET = 1;
    private static final int PART_COLUMN_CONTENT_DISPOSITION = 2;
    private static final int PART_COLUMN_CONTENT_ID = 3;
    private static final int PART_COLUMN_CONTENT_LOCATION = 4;
    private static final int PART_COLUMN_CONTENT_TYPE = 5;
    private static final int PART_COLUMN_FILENAME = 6;
    private static final int PART_COLUMN_ID = 0;
    private static final int PART_COLUMN_NAME = 7;
    private static final int PART_COLUMN_TEXT = 8;
    private static final PduCache PDU_CACHE_INSTANCE;
    private static final int PDU_COLUMN_CONTENT_CLASS = 11;
    private static final int PDU_COLUMN_CONTENT_LOCATION = 5;
    private static final int PDU_COLUMN_CONTENT_TYPE = 6;
    private static final int PDU_COLUMN_DATE = 21;
    private static final int PDU_COLUMN_DELIVERY_REPORT = 12;
    private static final int PDU_COLUMN_DELIVERY_TIME = 22;
    private static final int PDU_COLUMN_EXPIRY = 23;
    private static final int PDU_COLUMN_ID = 0;
    private static final int PDU_COLUMN_MESSAGE_BOX = 1;
    private static final int PDU_COLUMN_MESSAGE_CLASS = 7;
    private static final int PDU_COLUMN_MESSAGE_ID = 8;
    private static final int PDU_COLUMN_MESSAGE_SIZE = 24;
    private static final int PDU_COLUMN_MESSAGE_TYPE = 13;
    private static final int PDU_COLUMN_MMS_VERSION = 14;
    private static final int PDU_COLUMN_PRIORITY = 15;
    private static final int PDU_COLUMN_READ_REPORT = 16;
    private static final int PDU_COLUMN_READ_STATUS = 17;
    private static final int PDU_COLUMN_REPORT_ALLOWED = 18;
    private static final int PDU_COLUMN_RESPONSE_TEXT = 9;
    private static final int PDU_COLUMN_RETRIEVE_STATUS = 19;
    private static final int PDU_COLUMN_RETRIEVE_TEXT = 3;
    private static final int PDU_COLUMN_RETRIEVE_TEXT_CHARSET = 26;
    private static final int PDU_COLUMN_STATUS = 20;
    private static final int PDU_COLUMN_SUBJECT = 4;
    private static final int PDU_COLUMN_SUBJECT_CHARSET = 25;
    private static final int PDU_COLUMN_THREAD_ID = 2;
    private static final int PDU_COLUMN_TRANSACTION_ID = 10;
    private static final int PHONE_ID1 = 0;
    private static final long PLACEHOLDER_THREAD_ID = Long.MAX_VALUE;
    public static final int PROC_STATUS_COMPLETED = 3;
    public static final int PROC_STATUS_PERMANENTLY_FAILURE = 2;
    public static final int PROC_STATUS_TRANSIENT_FAILURE = 1;
    private static final String TAG = "PduPersister";
    public static final String TEMPORARY_DRM_OBJECT_URI = "content://mms/9223372036854775807/part";
    private static final HashMap<Integer, Integer> TEXT_STRING_COLUMN_INDEX_MAP;
    private static final HashMap<Integer, String> TEXT_STRING_COLUMN_NAME_MAP;
    private static PduPersister sPersister;
    private final ContentResolver mContentResolver;
    private final Context mContext;
    private final SemCscFeature mCscFeature = SemCscFeature.getInstance();
    private final TelephonyManager mTelephonyManager;
    private static final int[] ADDRESS_FIELDS = {129, 130, 137, 151};
    private static final String[] PDU_PROJECTION = {"_id", Telephony.BaseMmsColumns.MESSAGE_BOX, "thread_id", Telephony.BaseMmsColumns.RETRIEVE_TEXT, Telephony.BaseMmsColumns.SUBJECT, Telephony.BaseMmsColumns.CONTENT_LOCATION, Telephony.BaseMmsColumns.CONTENT_TYPE, Telephony.BaseMmsColumns.MESSAGE_CLASS, Telephony.BaseMmsColumns.MESSAGE_ID, Telephony.BaseMmsColumns.RESPONSE_TEXT, Telephony.BaseMmsColumns.TRANSACTION_ID, Telephony.BaseMmsColumns.CONTENT_CLASS, Telephony.BaseMmsColumns.DELIVERY_REPORT, Telephony.BaseMmsColumns.MESSAGE_TYPE, "v", Telephony.BaseMmsColumns.PRIORITY, Telephony.BaseMmsColumns.READ_REPORT, Telephony.BaseMmsColumns.READ_STATUS, Telephony.BaseMmsColumns.REPORT_ALLOWED, Telephony.BaseMmsColumns.RETRIEVE_STATUS, Telephony.BaseMmsColumns.STATUS, "date", Telephony.BaseMmsColumns.DELIVERY_TIME, Telephony.BaseMmsColumns.EXPIRY, Telephony.BaseMmsColumns.MESSAGE_SIZE, Telephony.BaseMmsColumns.SUBJECT_CHARSET, Telephony.BaseMmsColumns.RETRIEVE_TEXT_CHARSET};
    private static final String[] PART_PROJECTION = {"_id", Telephony.Mms.Part.CHARSET, Telephony.Mms.Part.CONTENT_DISPOSITION, "cid", Telephony.Mms.Part.CONTENT_LOCATION, "ct", Telephony.Mms.Part.FILENAME, "name", "text"};

    static {
        HashMap<Uri, Integer> map = new HashMap<>();
        MESSAGE_BOX_MAP = map;
        map.put(Telephony.Mms.Inbox.CONTENT_URI, 1);
        map.put(Telephony.Mms.Sent.CONTENT_URI, 2);
        map.put(Telephony.Mms.Draft.CONTENT_URI, 3);
        map.put(Telephony.Mms.Outbox.CONTENT_URI, 4);
        map.put(Uri.parse("content://spammms/inbox"), 1);
        HashMap<Integer, Integer> map2 = new HashMap<>();
        CHARSET_COLUMN_INDEX_MAP = map2;
        map2.put(150, 25);
        map2.put(154, 26);
        HashMap<Integer, String> map3 = new HashMap<>();
        CHARSET_COLUMN_NAME_MAP = map3;
        map3.put(150, Telephony.BaseMmsColumns.SUBJECT_CHARSET);
        map3.put(154, Telephony.BaseMmsColumns.RETRIEVE_TEXT_CHARSET);
        HashMap<Integer, Integer> map4 = new HashMap<>();
        ENCODED_STRING_COLUMN_INDEX_MAP = map4;
        map4.put(154, 3);
        map4.put(150, 4);
        HashMap<Integer, String> map5 = new HashMap<>();
        ENCODED_STRING_COLUMN_NAME_MAP = map5;
        map5.put(154, Telephony.BaseMmsColumns.RETRIEVE_TEXT);
        map5.put(150, Telephony.BaseMmsColumns.SUBJECT);
        HashMap<Integer, Integer> map6 = new HashMap<>();
        TEXT_STRING_COLUMN_INDEX_MAP = map6;
        map6.put(131, 5);
        map6.put(132, 6);
        map6.put(138, 7);
        map6.put(139, 8);
        map6.put(147, 9);
        map6.put(152, 10);
        HashMap<Integer, String> map7 = new HashMap<>();
        TEXT_STRING_COLUMN_NAME_MAP = map7;
        map7.put(131, Telephony.BaseMmsColumns.CONTENT_LOCATION);
        map7.put(132, Telephony.BaseMmsColumns.CONTENT_TYPE);
        map7.put(138, Telephony.BaseMmsColumns.MESSAGE_CLASS);
        map7.put(139, Telephony.BaseMmsColumns.MESSAGE_ID);
        map7.put(147, Telephony.BaseMmsColumns.RESPONSE_TEXT);
        map7.put(152, Telephony.BaseMmsColumns.TRANSACTION_ID);
        HashMap<Integer, Integer> map8 = new HashMap<>();
        OCTET_COLUMN_INDEX_MAP = map8;
        map8.put(186, 11);
        map8.put(134, 12);
        map8.put(140, 13);
        map8.put(141, 14);
        map8.put(143, 15);
        map8.put(144, 16);
        map8.put(155, 17);
        map8.put(145, 18);
        map8.put(153, 19);
        map8.put(149, 20);
        HashMap<Integer, String> map9 = new HashMap<>();
        OCTET_COLUMN_NAME_MAP = map9;
        map9.put(186, Telephony.BaseMmsColumns.CONTENT_CLASS);
        map9.put(134, Telephony.BaseMmsColumns.DELIVERY_REPORT);
        map9.put(140, Telephony.BaseMmsColumns.MESSAGE_TYPE);
        map9.put(141, "v");
        map9.put(143, Telephony.BaseMmsColumns.PRIORITY);
        map9.put(144, Telephony.BaseMmsColumns.READ_REPORT);
        map9.put(155, Telephony.BaseMmsColumns.READ_STATUS);
        map9.put(145, Telephony.BaseMmsColumns.REPORT_ALLOWED);
        map9.put(153, Telephony.BaseMmsColumns.RETRIEVE_STATUS);
        map9.put(149, Telephony.BaseMmsColumns.STATUS);
        HashMap<Integer, Integer> map10 = new HashMap<>();
        LONG_COLUMN_INDEX_MAP = map10;
        map10.put(133, 21);
        map10.put(135, 22);
        map10.put(136, 23);
        map10.put(142, 24);
        HashMap<Integer, String> map11 = new HashMap<>();
        LONG_COLUMN_NAME_MAP = map11;
        map11.put(133, "date");
        map11.put(135, Telephony.BaseMmsColumns.DELIVERY_TIME);
        map11.put(136, Telephony.BaseMmsColumns.EXPIRY);
        map11.put(142, Telephony.BaseMmsColumns.MESSAGE_SIZE);
        map11.put(192, "reserved");
        PDU_CACHE_INSTANCE = PduCache.getInstance();
    }

    private PduPersister(Context context) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
    }

    public static PduPersister getPduPersister(Context context) {
        PduPersister pduPersister = sPersister;
        if (pduPersister == null) {
            Log.e(TAG, "sPersister is null");
            sPersister = new PduPersister(context);
        } else if (!context.equals(pduPersister.mContext)) {
            Log.e(TAG, "context on pdupersist is not same");
            sPersister.release();
            sPersister = new PduPersister(context);
        }
        return sPersister;
    }

    private void setEncodedStringValueToHeaders(Cursor cursor, int i, PduHeaders pduHeaders, int i2) {
        String string = cursor.getString(i);
        if (string == null || string.length() <= 0) {
            return;
        }
        pduHeaders.setEncodedStringValue(new EncodedStringValue(cursor.getInt(CHARSET_COLUMN_INDEX_MAP.get(Integer.valueOf(i2)).intValue()), getBytes(string)), i2);
    }

    private void setTextStringToHeaders(Cursor cursor, int i, PduHeaders pduHeaders, int i2) {
        String string = cursor.getString(i);
        if (string != null) {
            pduHeaders.setTextString(getBytes(string), i2);
        }
    }

    private void setOctetToHeaders(Cursor cursor, int i, PduHeaders pduHeaders, int i2) throws InvalidHeaderValueException {
        if (cursor.isNull(i)) {
            return;
        }
        pduHeaders.setOctet(cursor.getInt(i), i2);
    }

    private void setLongToHeaders(Cursor cursor, int i, PduHeaders pduHeaders, int i2) {
        if (cursor.isNull(i)) {
            return;
        }
        pduHeaders.setLongInteger(cursor.getLong(i), i2);
    }

    private Integer getIntegerFromPartColumn(Cursor cursor, int i) {
        if (cursor.isNull(i)) {
            return null;
        }
        return Integer.valueOf(cursor.getInt(i));
    }

    private byte[] getByteArrayFromPartColumn(Cursor cursor, int i) {
        if (cursor.isNull(i)) {
            return null;
        }
        return getBytes(cursor.getString(i));
    }

    private PduPart[] loadParts(long j) throws MmsException {
        return loadParts(j, false);
    }

    private void loadAddress(long j, PduHeaders pduHeaders) {
        loadAddress(j, pduHeaders, false);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:110:0x0213
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1178)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public com.google.android.mms.pdu.GenericPdu load(android.net.Uri r13) throws com.google.android.mms.MmsException {
        /*
            Method dump skipped, instructions count: 604
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.mms.pdu.PduPersister.load(android.net.Uri):com.google.android.mms.pdu.GenericPdu");
    }

    private void persistAddress(long j, int i, EncodedStringValue[] encodedStringValueArr) {
        persistAddress(j, i, encodedStringValueArr, false);
    }

    private static String getPartContentType(PduPart pduPart) {
        if (pduPart.getContentType() == null) {
            return null;
        }
        return toIsoString(pduPart.getContentType());
    }

    public Uri persistPart(PduPart pduPart, long j, HashMap<Uri, InputStream> map) throws MmsException {
        return persistPart(pduPart, j, map, 0, false, false);
    }

    private void persistData(PduPart pduPart, Uri uri, String str, HashMap<Uri, InputStream> map) throws Throwable {
        persistData(pduPart, uri, str, map, false, false);
    }

    private void updateAddress(long j, int i, EncodedStringValue[] encodedStringValueArr) {
        Context context = this.mContext;
        ContentResolver contentResolver = this.mContentResolver;
        Uri uri = Uri.parse("content://mms/" + j + "/addr");
        StringBuilder sb = new StringBuilder("type=");
        sb.append(i);
        SqliteWrapper.delete(context, contentResolver, uri, sb.toString(), null);
        if (encodedStringValueArr != null) {
            persistAddress(j, i, encodedStringValueArr);
        }
    }

    public void updateHeaders(Uri uri, SendReq sendReq) {
        updateHeaders(uri, sendReq, 0);
    }

    private void updatePart(Uri uri, PduPart pduPart, HashMap<Uri, InputStream> map) throws Throwable {
        ContentValues contentValues = new ContentValues(7);
        int charset = pduPart.getCharset();
        if (charset != 0) {
            contentValues.put(Telephony.Mms.Part.CHARSET, Integer.valueOf(charset));
        }
        if (pduPart.getContentType() != null) {
            String isoString = toIsoString(pduPart.getContentType());
            contentValues.put("ct", isoString);
            if (pduPart.getFilename() != null) {
                contentValues.put(Telephony.Mms.Part.FILENAME, new String(pduPart.getFilename()));
            }
            if (pduPart.getName() != null) {
                contentValues.put("name", new String(pduPart.getName()));
            }
            if (pduPart.getContentDisposition() != null) {
                contentValues.put(Telephony.Mms.Part.CONTENT_DISPOSITION, toIsoString(pduPart.getContentDisposition()));
            }
            if (pduPart.getContentId() != null) {
                contentValues.put("cid", toIsoString(pduPart.getContentId()));
            }
            if (pduPart.getContentLocation() != null) {
                contentValues.put(Telephony.Mms.Part.CONTENT_LOCATION, toIsoString(pduPart.getContentLocation()));
            }
            SqliteWrapper.update(this.mContext, this.mContentResolver, uri, contentValues, null, null);
            if (pduPart.getData() == null && uri.equals(pduPart.getDataUri())) {
                return;
            }
            persistData(pduPart, uri, isoString, map);
            return;
        }
        throw new MmsException("MIME type of the part must be set.");
    }

    public void updateParts(Uri uri, PduBody pduBody, HashMap<Uri, InputStream> map) throws MmsException {
        try {
            PduCache pduCache = PDU_CACHE_INSTANCE;
            synchronized (pduCache) {
                if (pduCache.isUpdating(uri)) {
                    try {
                        pduCache.wait();
                    } catch (InterruptedException e) {
                        Log.e(TAG, "updateParts: ", e);
                    }
                    PduCacheEntry pduCacheEntry = PDU_CACHE_INSTANCE.get(uri);
                    if (pduCacheEntry != null) {
                        ((MultimediaMessagePdu) pduCacheEntry.getPdu()).setBody(pduBody);
                    }
                    PDU_CACHE_INSTANCE.setUpdating(uri, true);
                } else {
                    PDU_CACHE_INSTANCE.setUpdating(uri, true);
                }
            }
            ArrayList arrayList = new ArrayList();
            HashMap map2 = new HashMap();
            int partsNum = pduBody.getPartsNum();
            StringBuilder sb = new StringBuilder();
            sb.append('(');
            for (int i = 0; i < partsNum; i++) {
                PduPart part = pduBody.getPart(i);
                Uri dataUri = part.getDataUri();
                if (dataUri == null || TextUtils.isEmpty(dataUri.getAuthority()) || !dataUri.getAuthority().startsWith("mms")) {
                    arrayList.add(part);
                } else {
                    map2.put(dataUri, part);
                    if (sb.length() > 1) {
                        sb.append(" AND ");
                    }
                    sb.append("_id");
                    sb.append("!=");
                    DatabaseUtils.appendEscapedSQLString(sb, dataUri.getLastPathSegment());
                }
            }
            sb.append(')');
            long id = ContentUris.parseId(uri);
            SqliteWrapper.delete(this.mContext, this.mContentResolver, Uri.parse(Telephony.Mms.CONTENT_URI + "/" + id + "/part"), sb.length() > 2 ? sb.toString() : null, null);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                persistPart((PduPart) it.next(), id, map);
            }
            for (Map.Entry entry : map2.entrySet()) {
                updatePart((Uri) entry.getKey(), (PduPart) entry.getValue(), map);
            }
            PduCache pduCache2 = PDU_CACHE_INSTANCE;
            synchronized (pduCache2) {
                pduCache2.setUpdating(uri, false);
                pduCache2.notifyAll();
            }
        } catch (Throwable th) {
            PduCache pduCache3 = PDU_CACHE_INSTANCE;
            synchronized (pduCache3) {
                pduCache3.setUpdating(uri, false);
                pduCache3.notifyAll();
                throw th;
            }
        }
    }

    public Uri persist(GenericPdu genericPdu, Uri uri, boolean z, boolean z2, HashMap<Uri, InputStream> map) throws MmsException {
        return persist(genericPdu, 0, uri, z, z2, map, false, false);
    }

    private void loadRecipients(int i, HashSet<String> hashSet, HashMap<Integer, EncodedStringValue[]> map, boolean z) {
        EncodedStringValue[] encodedStringValueArr = map.get(Integer.valueOf(i));
        if (encodedStringValueArr == null) {
            return;
        }
        SubscriptionManager subscriptionManagerFrom = SubscriptionManager.from(this.mContext);
        HashSet hashSet2 = new HashSet();
        if (z) {
            Iterator<SubscriptionInfo> it = subscriptionManagerFrom.getActiveSubscriptionInfoList().iterator();
            while (it.hasNext()) {
                String line1Number = ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class)).createForSubscriptionId(it.next().getSubscriptionId()).getLine1Number();
                if (line1Number != null) {
                    hashSet2.add(line1Number);
                }
            }
        }
        for (EncodedStringValue encodedStringValue : encodedStringValueArr) {
            if (encodedStringValue != null) {
                String string = encodedStringValue.getString();
                if (z) {
                    Iterator it2 = hashSet2.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        if (!PhoneNumberUtils.compare(string, (String) it2.next()) && !hashSet.contains(string)) {
                            hashSet.add(string);
                            break;
                        }
                    }
                } else if (!hashSet.contains(string)) {
                    hashSet.add(string);
                }
            }
        }
    }

    public Uri move(Uri uri, Uri uri2) throws MmsException {
        long id = ContentUris.parseId(uri);
        if (id == -1) {
            throw new MmsException("Error! ID of the message: -1.");
        }
        Integer num = MESSAGE_BOX_MAP.get(uri2);
        if (num == null) {
            throw new MmsException("Bad destination, must be one of content://mms/inbox, content://mms/sent, content://mms/drafts, content://mms/outbox, content://mms/temp.");
        }
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(Telephony.BaseMmsColumns.MESSAGE_BOX, num);
        SqliteWrapper.update(this.mContext, this.mContentResolver, uri, contentValues, null, null);
        return ContentUris.withAppendedId(uri2, id);
    }

    public static String toIsoString(byte[] bArr) {
        try {
            return new String(bArr, CharacterSets.MIMENAME_ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "ISO_8859_1 must be supported!", e);
            return "";
        }
    }

    public static byte[] getBytes(String str) {
        try {
            return str.getBytes(CharacterSets.MIMENAME_ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "ISO_8859_1 must be supported!", e);
            return new byte[0];
        }
    }

    public void release() {
        Log.d(TAG, "pdupersist release");
        SqliteWrapper.delete(this.mContext, this.mContentResolver, Uri.parse(TEMPORARY_DRM_OBJECT_URI), null, null);
    }

    public Cursor getPendingMessages(long j) {
        Uri.Builder builderBuildUpon = Telephony.MmsSms.PendingMessages.CONTENT_URI.buildUpon();
        builderBuildUpon.appendQueryParameter("protocol", "mms");
        return SqliteWrapper.query(this.mContext, this.mContentResolver, builderBuildUpon.build(), null, "err_type < ? AND due_time <= ?", new String[]{String.valueOf(10), String.valueOf(j)}, Telephony.MmsSms.PendingMessages.DUE_TIME);
    }

    public void updateHeaders(Uri uri, SendReq sendReq, int i) {
        updateHeaders(uri, sendReq, i, 0);
    }

    public void updateHeaders(Uri uri, SendReq sendReq, int i, int i2) {
        ContentValues contentValues;
        long orCreateThreadId;
        long j;
        EncodedStringValue[] encodedStringValues;
        PduCache pduCache = PDU_CACHE_INSTANCE;
        synchronized (pduCache) {
            if (pduCache.isUpdating(uri)) {
                try {
                    pduCache.wait();
                } catch (InterruptedException e) {
                    Log.e(TAG, "updateHeaders: ", e);
                }
            }
        }
        PDU_CACHE_INSTANCE.purge(uri);
        int i3 = 1;
        if (this.mTelephonyManager.getPhoneCount() > 1) {
            contentValues = new ContentValues(12);
        } else {
            contentValues = new ContentValues(10);
        }
        byte[] contentType = sendReq.getContentType();
        if (contentType != null) {
            contentValues.put(Telephony.BaseMmsColumns.CONTENT_TYPE, toIsoString(contentType));
        }
        long date = sendReq.getDate();
        long j2 = -1;
        if (date != -1) {
            contentValues.put("date", Long.valueOf(date));
        }
        int deliveryReport = sendReq.getDeliveryReport();
        if (deliveryReport != 0) {
            contentValues.put(Telephony.BaseMmsColumns.DELIVERY_REPORT, Integer.valueOf(deliveryReport));
        }
        long deliveryTime = sendReq.getDeliveryTime();
        if (deliveryTime != -1) {
            contentValues.put(Telephony.BaseMmsColumns.DELIVERY_TIME, Long.valueOf(deliveryTime));
        }
        long expiry = sendReq.getExpiry();
        if (expiry != -1) {
            contentValues.put(Telephony.BaseMmsColumns.EXPIRY, Long.valueOf(expiry));
        }
        byte[] messageClass = sendReq.getMessageClass();
        if (messageClass != null) {
            contentValues.put(Telephony.BaseMmsColumns.MESSAGE_CLASS, toIsoString(messageClass));
        }
        int priority = sendReq.getPriority();
        if (priority != 0) {
            contentValues.put(Telephony.BaseMmsColumns.PRIORITY, Integer.valueOf(priority));
        }
        int readReport = sendReq.getReadReport();
        if (readReport != 0) {
            contentValues.put(Telephony.BaseMmsColumns.READ_REPORT, Integer.valueOf(readReport));
        }
        byte[] transactionId = sendReq.getTransactionId();
        if (transactionId != null) {
            contentValues.put(Telephony.BaseMmsColumns.TRANSACTION_ID, toIsoString(transactionId));
        }
        EncodedStringValue subject = sendReq.getSubject();
        if (subject != null) {
            contentValues.put(Telephony.BaseMmsColumns.SUBJECT, toIsoString(subject.getTextString()));
            contentValues.put(Telephony.BaseMmsColumns.SUBJECT_CHARSET, Integer.valueOf(subject.getCharacterSet()));
        } else {
            contentValues.put(Telephony.BaseMmsColumns.SUBJECT, "");
        }
        long messageSize = sendReq.getMessageSize();
        if (messageSize > 0) {
            contentValues.put(Telephony.BaseMmsColumns.MESSAGE_SIZE, Long.valueOf(messageSize));
        }
        PduHeaders pduHeaders = sendReq.getPduHeaders();
        HashSet hashSet = new HashSet();
        int[] iArr = ADDRESS_FIELDS;
        int length = iArr.length;
        int i4 = 0;
        while (i4 < length) {
            int i5 = iArr[i4];
            if (i5 == 137) {
                EncodedStringValue encodedStringValue = pduHeaders.getEncodedStringValue(i5);
                j = j2;
                if (encodedStringValue != null) {
                    encodedStringValues = new EncodedStringValue[i3];
                    encodedStringValues[0] = encodedStringValue;
                } else {
                    encodedStringValues = null;
                }
            } else {
                j = j2;
                encodedStringValues = pduHeaders.getEncodedStringValues(i5);
            }
            PduHeaders pduHeaders2 = pduHeaders;
            if (encodedStringValues != null) {
                updateAddress(ContentUris.parseId(uri), i5, encodedStringValues);
                if (i5 == 151) {
                    for (EncodedStringValue encodedStringValue2 : encodedStringValues) {
                        if (encodedStringValue2 != null) {
                            hashSet.add(encodedStringValue2.getString());
                        }
                    }
                }
            }
            i4++;
            pduHeaders = pduHeaders2;
            j2 = j;
            i3 = 1;
        }
        long j3 = j2;
        if (!hashSet.isEmpty()) {
            if (this.mCscFeature.getBoolean("CscFeature_Common_SupportTwoPhoneService", false)) {
                if (this.mTelephonyManager.getPhoneCount() > 1) {
                    if (i2 > 0) {
                        orCreateThreadId = Telephony.Threads.semGetOrCreateThreadId(this.mContext, hashSet, true, i, i2);
                    } else {
                        orCreateThreadId = Telephony.Threads.getOrCreateThreadId(this.mContext, hashSet, i);
                    }
                } else if (i2 > 0) {
                    orCreateThreadId = Telephony.Threads.semGetOrCreateThreadId(this.mContext, hashSet, true, 0, i2);
                } else {
                    orCreateThreadId = Telephony.Threads.getOrCreateThreadId(this.mContext, hashSet);
                }
            } else if (this.mTelephonyManager.getPhoneCount() > 1) {
                orCreateThreadId = Telephony.Threads.getOrCreateThreadId(this.mContext, hashSet, i);
            } else {
                orCreateThreadId = Telephony.Threads.getOrCreateThreadId(this.mContext, hashSet);
            }
            contentValues.put("thread_id", Long.valueOf(orCreateThreadId));
        }
        if (this.mCscFeature.getBoolean("CscFeature_Common_SupportTwoPhoneService", false) && i2 > 0) {
            contentValues.put("using_mode", Integer.valueOf(i2));
        }
        long reserved = sendReq.getReserved();
        if (reserved != j3) {
            contentValues.put("reserved", Long.valueOf(reserved));
        }
        SqliteWrapper.update(this.mContext, this.mContentResolver, uri, contentValues, null, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.google.android.mms.pdu.PduPart] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v4, types: [java.io.InputStream, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v3, types: [java.lang.StringBuilder] */
    private void persistData(PduPart pduPart, Uri uri, String str, HashMap<Uri, InputStream> map, boolean z, boolean z2) throws Throwable {
        InputStream inputStream;
        if (map == null) {
            Log.v(TAG, "preOpenedFiles is null");
        }
        OutputStream outputStream = null;
        inputStreamOpenInputStream = null;
        inputStreamOpenInputStream = null;
        InputStream inputStreamOpenInputStream = null;
        OutputStream outputStream2 = null;
        outputStream = null;
        try {
            try {
                byte[] data = pduPart.getData();
                if ("text/plain".equals(str) || ContentType.APP_SMIL.equals(str) || "text/html".equals(str)) {
                    ContentValues contentValues = new ContentValues();
                    if (data == null) {
                        contentValues.put("text", "");
                    } else if (pduPart.getCharset() == 38) {
                        contentValues.put("text", new EncodedStringValue(pduPart.getCharset(), data).getString());
                        contentValues.put(Telephony.Mms.Part.CHARSET, (Integer) 106);
                    } else {
                        contentValues.put("text", new EncodedStringValue(data).getString());
                    }
                    if (this.mContentResolver.update(uri, contentValues, null, null) != 1) {
                        throw new MmsException("unable to update " + uri.toString());
                    }
                    inputStream = null;
                } else {
                    DownloadDrmHelper.isDrmConvertNeeded(str);
                    OutputStream outputStreamOpenOutputStream = this.mContentResolver.openOutputStream(uri);
                    try {
                        if (outputStreamOpenOutputStream == null) {
                            throw new MmsException("unable to open output stream " + uri.toString());
                        }
                        if (data == null) {
                            Uri dataUri = pduPart.getDataUri();
                            if (dataUri != null && !dataUri.equals(uri)) {
                                if (map != null && map.containsKey(dataUri)) {
                                    inputStreamOpenInputStream = map.get(dataUri);
                                }
                                if (inputStreamOpenInputStream == null) {
                                    inputStreamOpenInputStream = this.mContentResolver.openInputStream(dataUri);
                                }
                                byte[] bArr = new byte[8192];
                                while (true) {
                                    int i = inputStreamOpenInputStream.read(bArr);
                                    if (i == -1) {
                                        break;
                                    } else {
                                        outputStreamOpenOutputStream.write(bArr, 0, i);
                                    }
                                }
                            }
                            Log.w(TAG, "Can't find data for this part.");
                            if (outputStreamOpenOutputStream != null) {
                                try {
                                    outputStreamOpenOutputStream.close();
                                    return;
                                } catch (IOException e) {
                                    Log.e(TAG, "IOException while closing: " + outputStreamOpenOutputStream, e);
                                    return;
                                }
                            }
                            return;
                        }
                        outputStreamOpenOutputStream.write(data);
                        inputStream = inputStreamOpenInputStream;
                        outputStream2 = outputStreamOpenOutputStream;
                    } catch (FileNotFoundException e2) {
                        e = e2;
                        Log.e(TAG, "Failed to open Input/Output stream.", e);
                        throw new MmsException(e);
                    } catch (IOException e3) {
                        e = e3;
                        Log.e(TAG, "Failed to read/write data.", e);
                        throw new MmsException(e);
                    } catch (Throwable th) {
                        th = th;
                        pduPart = 0;
                        outputStream = outputStreamOpenOutputStream;
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e4) {
                                Log.e(TAG, "IOException while closing: " + outputStream, e4);
                            }
                        }
                        if (pduPart == 0) {
                            throw th;
                        }
                        try {
                            pduPart.close();
                            throw th;
                        } catch (IOException e5) {
                            Log.e(TAG, "IOException while closing: " + pduPart, e5);
                            throw th;
                        }
                    }
                }
                if (outputStream2 != null) {
                    try {
                        outputStream2.close();
                    } catch (IOException e6) {
                        Log.e(TAG, "IOException while closing: " + outputStream2, e6);
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e7) {
                        Log.e(TAG, "IOException while closing: " + inputStream, e7);
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (FileNotFoundException e8) {
            e = e8;
        } catch (IOException e9) {
            e = e9;
        } catch (Throwable th3) {
            th = th3;
            pduPart = 0;
        }
    }

    public Uri persistPart(PduPart pduPart, long j, HashMap<Uri, InputStream> map, int i, boolean z, boolean z2) throws Throwable {
        Uri uri;
        if (z) {
            uri = Uri.parse("content://spammms/" + j + "/spampart");
        } else {
            uri = Uri.parse("content://mms/" + j + "/part");
        }
        ContentValues contentValues = new ContentValues(8);
        int charset = pduPart.getCharset();
        if (charset != 0) {
            contentValues.put(Telephony.Mms.Part.CHARSET, Integer.valueOf(charset));
        }
        String partContentType = getPartContentType(pduPart);
        if (partContentType != null) {
            if (ContentType.IMAGE_JPG.equals(partContentType)) {
                partContentType = ContentType.IMAGE_JPEG;
            }
            String str = partContentType;
            contentValues.put("ct", str);
            if (ContentType.APP_SMIL.equals(str)) {
                contentValues.put("seq", (Integer) (-1));
            }
            String str2 = "";
            if (pduPart.getFilename() != null) {
                if (isSupportOMA13NameEncoding(i)) {
                    contentValues.put(Telephony.Mms.Part.FILENAME, toIsoString(pduPart.getFilename()));
                } else {
                    String str3 = new String(pduPart.getFilename());
                    if (!isOma13Encoding(str3)) {
                        StringTokenizer stringTokenizer = new StringTokenizer(str3, "\\/:*?\"<>|");
                        str3 = "";
                        while (stringTokenizer.hasMoreTokens()) {
                            str3 = str3 + stringTokenizer.nextToken();
                        }
                    }
                    contentValues.put(Telephony.Mms.Part.FILENAME, str3);
                }
            }
            if (pduPart.getName() != null) {
                if (isSupportOMA13NameEncoding(i)) {
                    contentValues.put("name", toIsoString(pduPart.getName()));
                } else {
                    String str4 = new String(pduPart.getName());
                    if (!isOma13Encoding(str4)) {
                        StringTokenizer stringTokenizer2 = new StringTokenizer(str4, "\\/:*?\"<>|");
                        while (stringTokenizer2.hasMoreTokens()) {
                            str2 = str2 + stringTokenizer2.nextToken();
                        }
                        str4 = str2;
                    }
                    contentValues.put("name", toIsoString(str4.getBytes()));
                }
            }
            if (pduPart.getContentDisposition() != null) {
                contentValues.put(Telephony.Mms.Part.CONTENT_DISPOSITION, toIsoString(pduPart.getContentDisposition()));
            }
            if (pduPart.getContentId() != null) {
                contentValues.put("cid", toIsoString(pduPart.getContentId()));
            }
            if (pduPart.getContentLocation() != null) {
                contentValues.put(Telephony.Mms.Part.CONTENT_LOCATION, toIsoString(pduPart.getContentLocation()));
            }
            Uri uriInsert = SqliteWrapper.insert(this.mContext, this.mContentResolver, uri, contentValues);
            if (uriInsert == null) {
                throw new MmsException("Failed to persist part, return null.");
            }
            persistData(pduPart, uriInsert, str, map, z, z2);
            pduPart.setDataUri(uriInsert);
            return uriInsert;
        }
        throw new MmsException("MIME type of the part must be set.");
    }

    private void persistAddress(long j, int i, EncodedStringValue[] encodedStringValueArr, boolean z) {
        ContentValues contentValues = new ContentValues(3);
        for (EncodedStringValue encodedStringValue : encodedStringValueArr) {
            contentValues.clear();
            contentValues.put("address", toIsoString(encodedStringValue.getTextString()));
            contentValues.put(Telephony.Mms.Addr.CHARSET, Integer.valueOf(encodedStringValue.getCharacterSet()));
            contentValues.put("type", Integer.valueOf(i));
            SqliteWrapper.insert(this.mContext, this.mContentResolver, z ? Uri.parse("content://spammms/" + j + "/spamaddr") : Uri.parse("content://mms/" + j + "/addr"), contentValues);
        }
    }

    private void loadAddress(long j, PduHeaders pduHeaders, boolean z) {
        Cursor cursorQuery;
        if (z) {
            cursorQuery = SqliteWrapper.query(this.mContext, this.mContentResolver, Uri.parse("content://spammms/" + j + "/spamaddr"), new String[]{"address", Telephony.Mms.Addr.CHARSET, "type"}, null, null, null);
        } else {
            cursorQuery = SqliteWrapper.query(this.mContext, this.mContentResolver, Uri.parse("content://mms/" + j + "/addr"), new String[]{"address", Telephony.Mms.Addr.CHARSET, "type"}, null, null, null);
        }
        if (cursorQuery != null) {
            while (cursorQuery.moveToNext()) {
                try {
                    String string = cursorQuery.getString(0);
                    if (!TextUtils.isEmpty(string)) {
                        int i = cursorQuery.getInt(2);
                        if (i != 129 && i != 130) {
                            if (i == 137) {
                                pduHeaders.setEncodedStringValue(new EncodedStringValue(cursorQuery.getInt(1), getBytes(string)), i);
                            } else if (i != 151) {
                                Log.e(TAG, "Unknown address type: " + i);
                            }
                        }
                        pduHeaders.appendEncodedStringValue(new EncodedStringValue(cursorQuery.getInt(1), getBytes(string)), i);
                    }
                } finally {
                    cursorQuery.close();
                }
            }
        }
    }

    private PduPart[] loadParts(long j, boolean z) throws MmsException {
        Cursor cursorQuery;
        Throwable th;
        IOException iOException;
        if (z) {
            cursorQuery = SqliteWrapper.query(this.mContext, this.mContentResolver, Uri.parse("content://spammms/" + j + "/spampart"), PART_PROJECTION, null, null, null);
        } else {
            cursorQuery = SqliteWrapper.query(this.mContext, this.mContentResolver, Uri.parse("content://mms/" + j + "/part"), PART_PROJECTION, null, null, null);
        }
        InputStream inputStream = null;
        if (cursorQuery != null) {
            try {
                if (cursorQuery.getCount() != 0) {
                    PduPart[] pduPartArr = new PduPart[cursorQuery.getCount()];
                    int i = 0;
                    while (cursorQuery.moveToNext()) {
                        PduPart pduPart = new PduPart();
                        Integer integerFromPartColumn = getIntegerFromPartColumn(cursorQuery, 1);
                        if (integerFromPartColumn != null) {
                            pduPart.setCharset(integerFromPartColumn.intValue());
                        }
                        byte[] byteArrayFromPartColumn = getByteArrayFromPartColumn(cursorQuery, 2);
                        if (byteArrayFromPartColumn != null) {
                            pduPart.setContentDisposition(byteArrayFromPartColumn);
                        }
                        byte[] byteArrayFromPartColumn2 = getByteArrayFromPartColumn(cursorQuery, 3);
                        if (byteArrayFromPartColumn2 != null) {
                            pduPart.setContentId(byteArrayFromPartColumn2);
                        }
                        byte[] byteArrayFromPartColumn3 = getByteArrayFromPartColumn(cursorQuery, 4);
                        if (byteArrayFromPartColumn3 != null) {
                            pduPart.setContentLocation(byteArrayFromPartColumn3);
                        }
                        byte[] byteArrayFromPartColumn4 = getByteArrayFromPartColumn(cursorQuery, 5);
                        if (byteArrayFromPartColumn4 != null) {
                            pduPart.setContentType(byteArrayFromPartColumn4);
                            byte[] byteArrayFromPartColumn5 = getByteArrayFromPartColumn(cursorQuery, 6);
                            if (byteArrayFromPartColumn5 != null) {
                                pduPart.setFilename(byteArrayFromPartColumn5);
                            }
                            byte[] byteArrayFromPartColumn6 = getByteArrayFromPartColumn(cursorQuery, 7);
                            if (byteArrayFromPartColumn6 != null) {
                                pduPart.setName(byteArrayFromPartColumn6);
                            }
                            long j2 = cursorQuery.getLong(0);
                            Uri uri = z ? Uri.parse("content://spammms/spampart/" + j2) : Uri.parse("content://mms/part/" + j2);
                            pduPart.setDataUri(uri);
                            String isoString = toIsoString(byteArrayFromPartColumn4);
                            if (!ContentType.isImageType(isoString) && !ContentType.isAudioType(isoString) && !ContentType.isVideoType(isoString)) {
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                if ("text/plain".equals(isoString) || ContentType.APP_SMIL.equals(isoString) || "text/html".equals(isoString)) {
                                    String string = cursorQuery.getString(8);
                                    if (string == null) {
                                        string = "";
                                    }
                                    byte[] textString = new EncodedStringValue(string).getTextString();
                                    byteArrayOutputStream.write(textString, 0, textString.length);
                                } else {
                                    try {
                                        try {
                                            InputStream inputStreamOpenInputStream = this.mContentResolver.openInputStream(uri);
                                            if (inputStreamOpenInputStream == null) {
                                                throw new MmsException("Failed to load part data, return null.");
                                            }
                                            try {
                                                byte[] bArr = new byte[256];
                                                for (int i2 = inputStreamOpenInputStream.read(bArr); i2 >= 0; i2 = inputStreamOpenInputStream.read(bArr)) {
                                                    byteArrayOutputStream.write(bArr, 0, i2);
                                                }
                                                if (inputStreamOpenInputStream != null) {
                                                    try {
                                                        inputStreamOpenInputStream.close();
                                                    } catch (IOException e) {
                                                        Log.e(TAG, "Failed to close stream", e);
                                                    }
                                                }
                                            } catch (IOException e2) {
                                                iOException = e2;
                                                inputStream = inputStreamOpenInputStream;
                                                Log.e(TAG, "Failed to load part data", iOException);
                                                cursorQuery.close();
                                                throw new MmsException(iOException);
                                            } catch (Throwable th2) {
                                                th = th2;
                                                inputStream = inputStreamOpenInputStream;
                                                if (inputStream != null) {
                                                    try {
                                                        inputStream.close();
                                                        throw th;
                                                    } catch (IOException e3) {
                                                        Log.e(TAG, "Failed to close stream", e3);
                                                        throw th;
                                                    }
                                                }
                                                throw th;
                                            }
                                        } catch (IOException e4) {
                                            iOException = e4;
                                        }
                                    } catch (Throwable th3) {
                                        th = th3;
                                    }
                                }
                                pduPart.setData(byteArrayOutputStream.toByteArray());
                            }
                            pduPartArr[i] = pduPart;
                            i++;
                        } else {
                            throw new MmsException("Content-Type must be set.");
                        }
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return pduPartArr;
                }
            } finally {
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return null;
    }

    private static boolean isOma13Encoding(String str) {
        boolean z = false;
        if (str == null) {
            return false;
        }
        if (str.length() >= 5 && str.startsWith(ENCODING_PREFIX) && str.endsWith(ENCODING_SUFFIX)) {
            z = true;
        }
        Log.d(TAG, "pdupersister isOma13Encoding:" + z);
        return z;
    }

    public Cursor getPendingMessages(int i, long j) {
        Uri.Builder builderBuildUpon = Telephony.MmsSms.PendingMessages.CONTENT_URI.buildUpon();
        builderBuildUpon.appendQueryParameter("protocol", "mms");
        return SqliteWrapper.query(this.mContext, this.mContentResolver, builderBuildUpon.build(), null, "err_type < ? AND due_time <= ? AND sim_slot2 = ?", new String[]{String.valueOf(10), String.valueOf(j), String.valueOf(i)}, Telephony.MmsSms.PendingMessages.DUE_TIME);
    }

    public Uri persist(GenericPdu genericPdu, Uri uri, int i, int i2) throws MmsException {
        return persist(genericPdu, 0, uri, i, i2, (HashMap<Uri, InputStream>) null);
    }

    public Uri persist(GenericPdu genericPdu, int i, Uri uri, int i2, int i3) throws MmsException {
        return persist(genericPdu, i, uri, i2, i3, (HashMap<Uri, InputStream>) null);
    }

    public Uri persist(GenericPdu genericPdu, Uri uri, int i, int i2, HashMap<Uri, InputStream> map) throws MmsException {
        return persist(genericPdu, 0, uri, i, i2, map);
    }

    public Uri persist(GenericPdu genericPdu, int i, Uri uri, int i2, int i3, HashMap<Uri, InputStream> map) throws MmsException {
        return persist(genericPdu, i, uri, i2, i3, map, 0);
    }

    public Uri persist(GenericPdu genericPdu, int i, Uri uri, int i2, int i3, HashMap<Uri, InputStream> map, int i4) throws MmsException {
        EncodedStringValue[] encodedStringValues;
        long orCreateThreadId;
        PduBody body;
        if (uri == null) {
            throw new MmsException("Uri may not be null.");
        }
        if (MESSAGE_BOX_MAP.get(uri) == null) {
            throw new MmsException("Bad destination, must be one of content://mms/inbox, content://mms/sent, content://mms/drafts, content://mms/outbox, content://mms/temp.");
        }
        PDU_CACHE_INSTANCE.purge(uri);
        PduHeaders pduHeaders = genericPdu.getPduHeaders();
        ContentValues contentValues = new ContentValues();
        for (Map.Entry<Integer, String> entry : ENCODED_STRING_COLUMN_NAME_MAP.entrySet()) {
            Integer key = entry.getKey();
            EncodedStringValue encodedStringValue = pduHeaders.getEncodedStringValue(key.intValue());
            if (encodedStringValue != null) {
                String str = CHARSET_COLUMN_NAME_MAP.get(key);
                contentValues.put(entry.getValue(), toIsoString(encodedStringValue.getTextString()));
                contentValues.put(str, Integer.valueOf(encodedStringValue.getCharacterSet()));
            }
        }
        for (Map.Entry<Integer, String> entry2 : TEXT_STRING_COLUMN_NAME_MAP.entrySet()) {
            byte[] textString = pduHeaders.getTextString(entry2.getKey().intValue());
            if (textString != null) {
                contentValues.put(entry2.getValue(), toIsoString(textString));
            }
        }
        for (Map.Entry<Integer, String> entry3 : OCTET_COLUMN_NAME_MAP.entrySet()) {
            int octet = pduHeaders.getOctet(entry3.getKey().intValue());
            if (octet != 0) {
                contentValues.put(entry3.getValue(), Integer.valueOf(octet));
            }
        }
        for (Map.Entry<Integer, String> entry4 : LONG_COLUMN_NAME_MAP.entrySet()) {
            long longInteger = pduHeaders.getLongInteger(entry4.getKey().intValue());
            if (longInteger != -1) {
                contentValues.put(entry4.getValue(), Long.valueOf(longInteger));
            }
        }
        int[] iArr = ADDRESS_FIELDS;
        HashMap map2 = new HashMap(iArr.length);
        int length = iArr.length;
        int i5 = 0;
        while (true) {
            encodedStringValues = null;
            if (i5 >= length) {
                break;
            }
            int i6 = iArr[i5];
            if (i6 == 137) {
                EncodedStringValue encodedStringValue2 = pduHeaders.getEncodedStringValue(i6);
                if (encodedStringValue2 != null) {
                    encodedStringValues = new EncodedStringValue[]{encodedStringValue2};
                }
            } else {
                encodedStringValues = pduHeaders.getEncodedStringValues(i6);
            }
            map2.put(Integer.valueOf(i6), encodedStringValues);
            i5++;
        }
        HashSet hashSet = new HashSet();
        int messageType = genericPdu.getMessageType();
        this.mTelephonyManager.getLine1Number();
        if (messageType == 130 || messageType == 132 || messageType == 128) {
            if (messageType == 128) {
                encodedStringValues = (EncodedStringValue[]) map2.get(151);
            } else if (messageType == 130 || messageType == 132) {
                encodedStringValues = (EncodedStringValue[]) map2.get(137);
            }
            if (encodedStringValues != null) {
                for (EncodedStringValue encodedStringValue3 : encodedStringValues) {
                    if (encodedStringValue3 != null) {
                        hashSet.add(encodedStringValue3.getString());
                    }
                }
            }
            if (this.mCscFeature.getBoolean("CscFeature_Common_SupportTwoPhoneService", false) && i4 > 0) {
                orCreateThreadId = Telephony.Threads.semGetOrCreateThreadId(this.mContext, hashSet, true, 0, i4);
            } else {
                orCreateThreadId = Telephony.Threads.getOrCreateThreadId(this.mContext, hashSet);
            }
        } else {
            orCreateThreadId = Long.MAX_VALUE;
        }
        contentValues.put("thread_id", Long.valueOf(orCreateThreadId));
        if (this.mCscFeature.getBoolean("CscFeature_Common_SupportTwoPhoneService", false) && i4 > 0) {
            contentValues.put("using_mode", Integer.valueOf(i4));
        }
        long jNanoTime = System.nanoTime();
        if ((genericPdu instanceof MultimediaMessagePdu) && (body = ((MultimediaMessagePdu) genericPdu).getBody()) != null) {
            int partsNum = body.getPartsNum();
            for (int i7 = 0; i7 < partsNum; i7++) {
                persistPart(body.getPart(i7), jNanoTime, map);
            }
        }
        if (i2 > 0) {
            contentValues.put("app_id", Integer.valueOf(i2));
            contentValues.put("msg_id", Integer.valueOf(i3));
        }
        Uri uriInsert = SqliteWrapper.insert(this.mContext, this.mContentResolver, uri, contentValues);
        if (uriInsert == null) {
            throw new MmsException("persist() failed: return null.");
        }
        long id = ContentUris.parseId(uriInsert);
        ContentValues contentValues2 = new ContentValues(1);
        contentValues2.put(Telephony.Mms.Part.MSG_ID, Long.valueOf(id));
        SqliteWrapper.update(this.mContext, this.mContentResolver, Uri.parse("content://mms/" + jNanoTime + "/part"), contentValues2, null, null);
        Uri uri2 = Uri.parse(uri + "/" + id);
        for (int i8 : ADDRESS_FIELDS) {
            EncodedStringValue[] encodedStringValueArr = (EncodedStringValue[]) map2.get(Integer.valueOf(i8));
            if (encodedStringValueArr != null) {
                persistAddress(id, i8, encodedStringValueArr);
            }
        }
        return uri2;
    }

    public Uri persist(GenericPdu genericPdu, Uri uri) throws MmsException {
        return persist(genericPdu, 0, uri, true, false, null, false, false);
    }

    public Uri persist(GenericPdu genericPdu, Uri uri, boolean z) throws MmsException {
        return persist(genericPdu, 0, uri, true, false, null, z, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:83:0x01c0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Uri persist(GenericPdu genericPdu, int i, Uri uri, boolean z, boolean z2, HashMap<Uri, InputStream> map, boolean z3, boolean z4, int i2) throws Throwable {
        long id;
        long orCreateThreadId;
        boolean z5;
        PduPersister pduPersister;
        int i3;
        int i4;
        Uri uri2;
        long j;
        Uri uriInsert;
        PduBody body;
        EncodedStringValue[] encodedStringValues;
        PduPersister pduPersister2 = this;
        if (uri == null) {
            throw new MmsException("Uri may not be null.");
        }
        try {
            id = ContentUris.parseId(uri);
        } catch (NumberFormatException unused) {
            id = -1;
        }
        int i5 = 0;
        boolean z6 = id != -1;
        if (!z6 && MESSAGE_BOX_MAP.get(uri) == null) {
            throw new MmsException("Bad destination, must be one of content://mms/inbox, content://mms/sent, content://mms/drafts, content://mms/outbox, content://mms/temp.");
        }
        PduCache pduCache = PDU_CACHE_INSTANCE;
        synchronized (pduCache) {
            if (pduCache.isUpdating(uri)) {
                try {
                    pduCache.wait();
                } catch (InterruptedException e) {
                    Log.e(TAG, "persist1: ", e);
                }
            }
        }
        PDU_CACHE_INSTANCE.purge(uri);
        PduHeaders pduHeaders = genericPdu.getPduHeaders();
        ContentValues contentValues = new ContentValues();
        for (Map.Entry<Integer, String> entry : ENCODED_STRING_COLUMN_NAME_MAP.entrySet()) {
            Integer key = entry.getKey();
            EncodedStringValue encodedStringValue = pduHeaders.getEncodedStringValue(key.intValue());
            if (encodedStringValue != null) {
                String str = CHARSET_COLUMN_NAME_MAP.get(key);
                contentValues.put(entry.getValue(), toIsoString(encodedStringValue.getTextString()));
                contentValues.put(str, Integer.valueOf(encodedStringValue.getCharacterSet()));
            }
        }
        for (Map.Entry<Integer, String> entry2 : TEXT_STRING_COLUMN_NAME_MAP.entrySet()) {
            byte[] textString = pduHeaders.getTextString(entry2.getKey().intValue());
            if (textString != null) {
                contentValues.put(entry2.getValue(), toIsoString(textString));
            }
        }
        for (Map.Entry<Integer, String> entry3 : OCTET_COLUMN_NAME_MAP.entrySet()) {
            int octet = pduHeaders.getOctet(entry3.getKey().intValue());
            if (octet != 0) {
                contentValues.put(entry3.getValue(), Integer.valueOf(octet));
            }
        }
        for (Map.Entry<Integer, String> entry4 : LONG_COLUMN_NAME_MAP.entrySet()) {
            long longInteger = pduHeaders.getLongInteger(entry4.getKey().intValue());
            if (longInteger != -1) {
                contentValues.put(entry4.getValue(), Long.valueOf(longInteger));
            }
        }
        int[] iArr = ADDRESS_FIELDS;
        HashMap<Integer, EncodedStringValue[]> map2 = new HashMap<>(iArr.length);
        for (int i6 : iArr) {
            if (i6 == 137) {
                EncodedStringValue encodedStringValue2 = pduHeaders.getEncodedStringValue(i6);
                encodedStringValues = encodedStringValue2 != null ? new EncodedStringValue[]{encodedStringValue2} : null;
            } else {
                encodedStringValues = pduHeaders.getEncodedStringValues(i6);
            }
            map2.put(Integer.valueOf(i6), encodedStringValues);
        }
        HashSet<String> hashSet = new HashSet<>();
        int messageType = genericPdu.getMessageType();
        pduPersister2.mTelephonyManager.getLine1Number();
        if (messageType == 130 || messageType == 132 || messageType == 128) {
            if (messageType == 128) {
                pduPersister2.loadRecipients(151, hashSet, map2, false);
            } else if (messageType == 130 || messageType == 132) {
                pduPersister2.loadRecipients(137, hashSet, map2, false);
                if (z2) {
                    pduPersister2.loadRecipients(151, hashSet, map2, true);
                    pduPersister2.loadRecipients(130, hashSet, map2, true);
                }
            }
            if (!z || z3) {
                orCreateThreadId = Long.MAX_VALUE;
            } else if (pduPersister2.mCscFeature.getBoolean("CscFeature_Common_SupportTwoPhoneService", false) && i2 > 0) {
                orCreateThreadId = Telephony.Threads.semGetOrCreateThreadId(pduPersister2.mContext, hashSet, true, 0, i2);
            } else {
                orCreateThreadId = Telephony.Threads.getOrCreateThreadId(pduPersister2.mContext, hashSet);
            }
        }
        if (!z3) {
            contentValues.put("thread_id", Long.valueOf(orCreateThreadId));
        }
        if (pduPersister2.mCscFeature.getBoolean("CscFeature_Common_SupportTwoPhoneService", false) && i2 > 0) {
            contentValues.put("using_mode", Integer.valueOf(i2));
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (!(genericPdu instanceof MultimediaMessagePdu) || (body = ((MultimediaMessagePdu) genericPdu).getBody()) == null) {
            z5 = z3;
            pduPersister = pduPersister2;
            i3 = 1;
            i4 = 0;
        } else {
            int partsNum = body.getPartsNum();
            i3 = partsNum > 2 ? 0 : 1;
            int i7 = 0;
            i4 = 0;
            while (i7 < partsNum) {
                int i8 = i7;
                PduPart part = body.getPart(i8);
                int dataLength = i4 + part.getDataLength();
                pduPersister2.persistPart(part, jCurrentTimeMillis, map, i, z3, z4);
                PduPersister pduPersister3 = pduPersister2;
                String partContentType = getPartContentType(part);
                if (partContentType != null && !ContentType.APP_SMIL.equals(partContentType) && !"text/plain".equals(partContentType)) {
                    i3 = 0;
                }
                i7 = i8 + 1;
                pduPersister2 = pduPersister3;
                i4 = dataLength;
            }
            z5 = z3;
            pduPersister = pduPersister2;
        }
        contentValues.put(Telephony.BaseMmsColumns.TEXT_ONLY, Integer.valueOf(i3));
        if (contentValues.getAsInteger(Telephony.BaseMmsColumns.MESSAGE_SIZE) == null) {
            contentValues.put(Telephony.BaseMmsColumns.MESSAGE_SIZE, Integer.valueOf(i4));
        }
        if (z6) {
            uri2 = uri;
            j = jCurrentTimeMillis;
            SqliteWrapper.update(pduPersister.mContext, pduPersister.mContentResolver, uri2, contentValues, null, null);
            uriInsert = uri2;
        } else {
            uri2 = uri;
            j = jCurrentTimeMillis;
            uriInsert = SqliteWrapper.insert(pduPersister.mContext, pduPersister.mContentResolver, uri2, contentValues);
            if (uriInsert == null) {
                throw new MmsException("persist() failed: return null.");
            }
            ContentUris.parseId(uriInsert);
        }
        long id2 = ContentUris.parseId(uriInsert);
        ContentValues contentValues2 = new ContentValues(1);
        contentValues2.put(Telephony.Mms.Part.MSG_ID, Long.valueOf(id2));
        if (z5) {
            SqliteWrapper.update(pduPersister.mContext, pduPersister.mContentResolver, Uri.parse("content://spammms/" + j + "/spampart"), contentValues2, null, null);
        } else {
            SqliteWrapper.update(pduPersister.mContext, pduPersister.mContentResolver, Uri.parse("content://mms/" + j + "/part"), contentValues2, null, null);
        }
        if (!z6) {
            uriInsert = Uri.parse(uri2 + "/" + id2);
        }
        int[] iArr2 = ADDRESS_FIELDS;
        int length = iArr2.length;
        while (i5 < length) {
            int i9 = iArr2[i5];
            EncodedStringValue[] encodedStringValueArr = map2.get(Integer.valueOf(i9));
            if (encodedStringValueArr != null) {
                pduPersister.persistAddress(id2, i9, encodedStringValueArr, z5);
            }
            i5++;
            pduPersister = this;
            z5 = z3;
        }
        return uriInsert;
    }

    public Uri persist(GenericPdu genericPdu, Uri uri, boolean z, boolean z2, HashMap<Uri, InputStream> map, boolean z3) throws MmsException {
        return persist(genericPdu, 0, uri, z, z2, map, z3, true);
    }

    public Uri persist(GenericPdu genericPdu, int i, Uri uri, boolean z, boolean z2, HashMap<Uri, InputStream> map) throws MmsException {
        return persist(genericPdu, i, uri, z, z2, map, false, false);
    }

    public Uri persist(GenericPdu genericPdu, int i, Uri uri) throws MmsException {
        return persist(genericPdu, i, uri, true, false, null, false, true);
    }

    public Uri persist(GenericPdu genericPdu, int i, Uri uri, boolean z) throws MmsException {
        return persist(genericPdu, i, uri, true, false, null, z, true);
    }

    public Uri persist(GenericPdu genericPdu, Uri uri, boolean z, boolean z2, HashMap<Uri, InputStream> map, boolean z3, boolean z4) throws MmsException {
        return persist(genericPdu, 0, uri, z, z2, map, z3, z4);
    }

    public Uri persist(GenericPdu genericPdu, int i, Uri uri, boolean z, boolean z2, HashMap<Uri, InputStream> map, boolean z3, boolean z4) throws MmsException {
        return persist(genericPdu, i, uri, z, z2, map, z3, z4, 0);
    }

    private boolean isSupportOMA13NameEncoding(int i) {
        final String str;
        if (i == 0) {
            str = SystemProperties.get("mdc.matched_code", SystemProperties.get("ro.csc.sales_code", ""));
        } else {
            str = SystemProperties.get("mdc.matched_code2", SystemProperties.get("ro.csc.sales_code", ""));
        }
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Arrays.stream(new String[]{"CHC", "CHM", "CHN", "KTC", "LUC", "SKC", "KOO", "K06", "K01"}).anyMatch(new Predicate() { // from class: com.google.android.mms.pdu.PduPersister$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((String) obj).equals(str);
            }
        });
    }
}
