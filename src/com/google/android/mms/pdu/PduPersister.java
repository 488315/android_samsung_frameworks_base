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
import com.google.android.mms.util.PduCache;
import com.google.android.mms.util.PduCacheEntry;
import com.google.android.mms.util.SqliteWrapper;
import com.samsung.android.feature.SemCscFeature;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        HashMap<Uri, Integer> hashMap = new HashMap<>();
        MESSAGE_BOX_MAP = hashMap;
        hashMap.put(Telephony.Mms.Inbox.CONTENT_URI, 1);
        hashMap.put(Telephony.Mms.Sent.CONTENT_URI, 2);
        hashMap.put(Telephony.Mms.Draft.CONTENT_URI, 3);
        hashMap.put(Telephony.Mms.Outbox.CONTENT_URI, 4);
        hashMap.put(Uri.parse("content://spammms/inbox"), 1);
        HashMap<Integer, Integer> hashMap2 = new HashMap<>();
        CHARSET_COLUMN_INDEX_MAP = hashMap2;
        hashMap2.put(150, 25);
        hashMap2.put(154, 26);
        HashMap<Integer, String> hashMap3 = new HashMap<>();
        CHARSET_COLUMN_NAME_MAP = hashMap3;
        hashMap3.put(150, Telephony.BaseMmsColumns.SUBJECT_CHARSET);
        hashMap3.put(154, Telephony.BaseMmsColumns.RETRIEVE_TEXT_CHARSET);
        HashMap<Integer, Integer> hashMap4 = new HashMap<>();
        ENCODED_STRING_COLUMN_INDEX_MAP = hashMap4;
        hashMap4.put(154, 3);
        hashMap4.put(150, 4);
        HashMap<Integer, String> hashMap5 = new HashMap<>();
        ENCODED_STRING_COLUMN_NAME_MAP = hashMap5;
        hashMap5.put(154, Telephony.BaseMmsColumns.RETRIEVE_TEXT);
        hashMap5.put(150, Telephony.BaseMmsColumns.SUBJECT);
        HashMap<Integer, Integer> hashMap6 = new HashMap<>();
        TEXT_STRING_COLUMN_INDEX_MAP = hashMap6;
        hashMap6.put(131, 5);
        hashMap6.put(132, 6);
        hashMap6.put(138, 7);
        hashMap6.put(139, 8);
        hashMap6.put(147, 9);
        hashMap6.put(152, 10);
        HashMap<Integer, String> hashMap7 = new HashMap<>();
        TEXT_STRING_COLUMN_NAME_MAP = hashMap7;
        hashMap7.put(131, Telephony.BaseMmsColumns.CONTENT_LOCATION);
        hashMap7.put(132, Telephony.BaseMmsColumns.CONTENT_TYPE);
        hashMap7.put(138, Telephony.BaseMmsColumns.MESSAGE_CLASS);
        hashMap7.put(139, Telephony.BaseMmsColumns.MESSAGE_ID);
        hashMap7.put(147, Telephony.BaseMmsColumns.RESPONSE_TEXT);
        hashMap7.put(152, Telephony.BaseMmsColumns.TRANSACTION_ID);
        HashMap<Integer, Integer> hashMap8 = new HashMap<>();
        OCTET_COLUMN_INDEX_MAP = hashMap8;
        hashMap8.put(186, 11);
        hashMap8.put(134, 12);
        hashMap8.put(140, 13);
        hashMap8.put(141, 14);
        hashMap8.put(143, 15);
        hashMap8.put(144, 16);
        hashMap8.put(155, 17);
        hashMap8.put(145, 18);
        hashMap8.put(153, 19);
        hashMap8.put(149, 20);
        HashMap<Integer, String> hashMap9 = new HashMap<>();
        OCTET_COLUMN_NAME_MAP = hashMap9;
        hashMap9.put(186, Telephony.BaseMmsColumns.CONTENT_CLASS);
        hashMap9.put(134, Telephony.BaseMmsColumns.DELIVERY_REPORT);
        hashMap9.put(140, Telephony.BaseMmsColumns.MESSAGE_TYPE);
        hashMap9.put(141, "v");
        hashMap9.put(143, Telephony.BaseMmsColumns.PRIORITY);
        hashMap9.put(144, Telephony.BaseMmsColumns.READ_REPORT);
        hashMap9.put(155, Telephony.BaseMmsColumns.READ_STATUS);
        hashMap9.put(145, Telephony.BaseMmsColumns.REPORT_ALLOWED);
        hashMap9.put(153, Telephony.BaseMmsColumns.RETRIEVE_STATUS);
        hashMap9.put(149, Telephony.BaseMmsColumns.STATUS);
        HashMap<Integer, Integer> hashMap10 = new HashMap<>();
        LONG_COLUMN_INDEX_MAP = hashMap10;
        hashMap10.put(133, 21);
        hashMap10.put(135, 22);
        hashMap10.put(136, 23);
        hashMap10.put(142, 24);
        HashMap<Integer, String> hashMap11 = new HashMap<>();
        LONG_COLUMN_NAME_MAP = hashMap11;
        hashMap11.put(133, "date");
        hashMap11.put(135, Telephony.BaseMmsColumns.DELIVERY_TIME);
        hashMap11.put(136, Telephony.BaseMmsColumns.EXPIRY);
        hashMap11.put(142, Telephony.BaseMmsColumns.MESSAGE_SIZE);
        hashMap11.put(192, "reserved");
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
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:125:0x0213
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public com.google.android.mms.pdu.GenericPdu load(android.net.Uri r13) throws com.google.android.mms.MmsException {
        /*
            Method dump skipped, instructions count: 604
            To view this dump change 'Code comments level' option to 'DEBUG'
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

    public Uri persistPart(PduPart pduPart, long j, HashMap<Uri, InputStream> hashMap) throws MmsException {
        return persistPart(pduPart, j, hashMap, 0, false, false);
    }

    private void persistData(PduPart pduPart, Uri uri, String str, HashMap<Uri, InputStream> hashMap) throws MmsException {
        persistData(pduPart, uri, str, hashMap, false, false);
    }

    private void updateAddress(long j, int i, EncodedStringValue[] encodedStringValueArr) {
        Context context = this.mContext;
        ContentResolver contentResolver = this.mContentResolver;
        Uri parse = Uri.parse("content://mms/" + j + "/addr");
        StringBuilder sb = new StringBuilder("type=");
        sb.append(i);
        SqliteWrapper.delete(context, contentResolver, parse, sb.toString(), null);
        if (encodedStringValueArr != null) {
            persistAddress(j, i, encodedStringValueArr);
        }
    }

    public void updateHeaders(Uri uri, SendReq sendReq) {
        updateHeaders(uri, sendReq, 0);
    }

    private void updatePart(Uri uri, PduPart pduPart, HashMap<Uri, InputStream> hashMap) throws MmsException {
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
            persistData(pduPart, uri, isoString, hashMap);
            return;
        }
        throw new MmsException("MIME type of the part must be set.");
    }

    public void updateParts(Uri uri, PduBody pduBody, HashMap<Uri, InputStream> hashMap) throws MmsException {
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
                }
                PDU_CACHE_INSTANCE.setUpdating(uri, true);
            }
            ArrayList arrayList = new ArrayList();
            HashMap hashMap2 = new HashMap();
            int partsNum = pduBody.getPartsNum();
            StringBuilder sb = new StringBuilder();
            sb.append('(');
            for (int i = 0; i < partsNum; i++) {
                PduPart part = pduBody.getPart(i);
                Uri dataUri = part.getDataUri();
                if (dataUri != null && !TextUtils.isEmpty(dataUri.getAuthority()) && dataUri.getAuthority().startsWith("mms")) {
                    hashMap2.put(dataUri, part);
                    if (sb.length() > 1) {
                        sb.append(" AND ");
                    }
                    sb.append("_id");
                    sb.append("!=");
                    DatabaseUtils.appendEscapedSQLString(sb, dataUri.getLastPathSegment());
                }
                arrayList.add(part);
            }
            sb.append(')');
            long parseId = ContentUris.parseId(uri);
            SqliteWrapper.delete(this.mContext, this.mContentResolver, Uri.parse(Telephony.Mms.CONTENT_URI + "/" + parseId + "/part"), sb.length() > 2 ? sb.toString() : null, null);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                persistPart((PduPart) it.next(), parseId, hashMap);
            }
            for (Map.Entry entry : hashMap2.entrySet()) {
                updatePart((Uri) entry.getKey(), (PduPart) entry.getValue(), hashMap);
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

    public Uri persist(GenericPdu genericPdu, Uri uri, boolean z, boolean z2, HashMap<Uri, InputStream> hashMap) throws MmsException {
        return persist(genericPdu, 0, uri, z, z2, hashMap, false, false);
    }

    private void loadRecipients(int i, HashSet<String> hashSet, HashMap<Integer, EncodedStringValue[]> hashMap, boolean z) {
        EncodedStringValue[] encodedStringValueArr = hashMap.get(Integer.valueOf(i));
        if (encodedStringValueArr == null) {
            return;
        }
        SubscriptionManager from = SubscriptionManager.from(this.mContext);
        HashSet hashSet2 = new HashSet();
        if (z) {
            Iterator<SubscriptionInfo> it = from.getActiveSubscriptionInfoList().iterator();
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
        long parseId = ContentUris.parseId(uri);
        if (parseId == -1) {
            throw new MmsException("Error! ID of the message: -1.");
        }
        Integer num = MESSAGE_BOX_MAP.get(uri2);
        if (num == null) {
            throw new MmsException("Bad destination, must be one of content://mms/inbox, content://mms/sent, content://mms/drafts, content://mms/outbox, content://mms/temp.");
        }
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(Telephony.BaseMmsColumns.MESSAGE_BOX, num);
        SqliteWrapper.update(this.mContext, this.mContentResolver, uri, contentValues, null, null);
        return ContentUris.withAppendedId(uri2, parseId);
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
        Uri.Builder buildUpon = Telephony.MmsSms.PendingMessages.CONTENT_URI.buildUpon();
        buildUpon.appendQueryParameter("protocol", "mms");
        return SqliteWrapper.query(this.mContext, this.mContentResolver, buildUpon.build(), null, "err_type < ? AND due_time <= ?", new String[]{String.valueOf(10), String.valueOf(j)}, Telephony.MmsSms.PendingMessages.DUE_TIME);
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
    /* JADX WARN: Removed duplicated region for block: B:36:0x0121 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x010b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.google.android.mms.pdu.PduPart] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v4, types: [java.io.InputStream, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v3, types: [java.lang.StringBuilder] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void persistData(com.google.android.mms.pdu.PduPart r6, android.net.Uri r7, java.lang.String r8, java.util.HashMap<android.net.Uri, java.io.InputStream> r9, boolean r10, boolean r11) throws com.google.android.mms.MmsException {
        /*
            Method dump skipped, instructions count: 407
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.mms.pdu.PduPersister.persistData(com.google.android.mms.pdu.PduPart, android.net.Uri, java.lang.String, java.util.HashMap, boolean, boolean):void");
    }

    public Uri persistPart(PduPart pduPart, long j, HashMap<Uri, InputStream> hashMap, int i, boolean z, boolean z2) throws MmsException {
        Uri parse;
        if (z) {
            parse = Uri.parse("content://spammms/" + j + "/spampart");
        } else {
            parse = Uri.parse("content://mms/" + j + "/part");
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
            Uri insert = SqliteWrapper.insert(this.mContext, this.mContentResolver, parse, contentValues);
            if (insert == null) {
                throw new MmsException("Failed to persist part, return null.");
            }
            persistData(pduPart, insert, str, hashMap, z, z2);
            pduPart.setDataUri(insert);
            return insert;
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
        Cursor query;
        if (z) {
            query = SqliteWrapper.query(this.mContext, this.mContentResolver, Uri.parse("content://spammms/" + j + "/spamaddr"), new String[]{"address", Telephony.Mms.Addr.CHARSET, "type"}, null, null, null);
        } else {
            query = SqliteWrapper.query(this.mContext, this.mContentResolver, Uri.parse("content://mms/" + j + "/addr"), new String[]{"address", Telephony.Mms.Addr.CHARSET, "type"}, null, null, null);
        }
        if (query != null) {
            while (query.moveToNext()) {
                try {
                    String string = query.getString(0);
                    if (!TextUtils.isEmpty(string)) {
                        int i = query.getInt(2);
                        if (i != 129 && i != 130) {
                            if (i == 137) {
                                pduHeaders.setEncodedStringValue(new EncodedStringValue(query.getInt(1), getBytes(string)), i);
                            } else if (i != 151) {
                                Log.e(TAG, "Unknown address type: " + i);
                            }
                        }
                        pduHeaders.appendEncodedStringValue(new EncodedStringValue(query.getInt(1), getBytes(string)), i);
                    }
                } finally {
                    query.close();
                }
            }
        }
    }

    private PduPart[] loadParts(long j, boolean z) throws MmsException {
        Cursor query;
        Throwable th;
        IOException iOException;
        if (z) {
            query = SqliteWrapper.query(this.mContext, this.mContentResolver, Uri.parse("content://spammms/" + j + "/spampart"), PART_PROJECTION, null, null, null);
        } else {
            query = SqliteWrapper.query(this.mContext, this.mContentResolver, Uri.parse("content://mms/" + j + "/part"), PART_PROJECTION, null, null, null);
        }
        InputStream inputStream = null;
        if (query != null) {
            try {
                if (query.getCount() != 0) {
                    PduPart[] pduPartArr = new PduPart[query.getCount()];
                    int i = 0;
                    while (query.moveToNext()) {
                        PduPart pduPart = new PduPart();
                        Integer integerFromPartColumn = getIntegerFromPartColumn(query, 1);
                        if (integerFromPartColumn != null) {
                            pduPart.setCharset(integerFromPartColumn.intValue());
                        }
                        byte[] byteArrayFromPartColumn = getByteArrayFromPartColumn(query, 2);
                        if (byteArrayFromPartColumn != null) {
                            pduPart.setContentDisposition(byteArrayFromPartColumn);
                        }
                        byte[] byteArrayFromPartColumn2 = getByteArrayFromPartColumn(query, 3);
                        if (byteArrayFromPartColumn2 != null) {
                            pduPart.setContentId(byteArrayFromPartColumn2);
                        }
                        byte[] byteArrayFromPartColumn3 = getByteArrayFromPartColumn(query, 4);
                        if (byteArrayFromPartColumn3 != null) {
                            pduPart.setContentLocation(byteArrayFromPartColumn3);
                        }
                        byte[] byteArrayFromPartColumn4 = getByteArrayFromPartColumn(query, 5);
                        if (byteArrayFromPartColumn4 != null) {
                            pduPart.setContentType(byteArrayFromPartColumn4);
                            byte[] byteArrayFromPartColumn5 = getByteArrayFromPartColumn(query, 6);
                            if (byteArrayFromPartColumn5 != null) {
                                pduPart.setFilename(byteArrayFromPartColumn5);
                            }
                            byte[] byteArrayFromPartColumn6 = getByteArrayFromPartColumn(query, 7);
                            if (byteArrayFromPartColumn6 != null) {
                                pduPart.setName(byteArrayFromPartColumn6);
                            }
                            long j2 = query.getLong(0);
                            Uri parse = z ? Uri.parse("content://spammms/spampart/" + j2) : Uri.parse("content://mms/part/" + j2);
                            pduPart.setDataUri(parse);
                            String isoString = toIsoString(byteArrayFromPartColumn4);
                            if (!ContentType.isImageType(isoString) && !ContentType.isAudioType(isoString) && !ContentType.isVideoType(isoString)) {
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                if ("text/plain".equals(isoString) || ContentType.APP_SMIL.equals(isoString) || "text/html".equals(isoString)) {
                                    String string = query.getString(8);
                                    if (string == null) {
                                        string = "";
                                    }
                                    byte[] textString = new EncodedStringValue(string).getTextString();
                                    byteArrayOutputStream.write(textString, 0, textString.length);
                                } else {
                                    try {
                                        try {
                                            InputStream openInputStream = this.mContentResolver.openInputStream(parse);
                                            if (openInputStream == null) {
                                                throw new MmsException("Failed to load part data, return null.");
                                            }
                                            try {
                                                byte[] bArr = new byte[256];
                                                for (int read = openInputStream.read(bArr); read >= 0; read = openInputStream.read(bArr)) {
                                                    byteArrayOutputStream.write(bArr, 0, read);
                                                }
                                                if (openInputStream != null) {
                                                    try {
                                                        openInputStream.close();
                                                    } catch (IOException e) {
                                                        Log.e(TAG, "Failed to close stream", e);
                                                    }
                                                }
                                            } catch (IOException e2) {
                                                iOException = e2;
                                                inputStream = openInputStream;
                                                Log.e(TAG, "Failed to load part data", iOException);
                                                query.close();
                                                throw new MmsException(iOException);
                                            } catch (Throwable th2) {
                                                th = th2;
                                                inputStream = openInputStream;
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
                                        } catch (Throwable th3) {
                                            th = th3;
                                        }
                                    } catch (IOException e4) {
                                        iOException = e4;
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
                    if (query != null) {
                        query.close();
                    }
                    return pduPartArr;
                }
            } finally {
            }
        }
        if (query != null) {
            query.close();
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
        Uri.Builder buildUpon = Telephony.MmsSms.PendingMessages.CONTENT_URI.buildUpon();
        buildUpon.appendQueryParameter("protocol", "mms");
        return SqliteWrapper.query(this.mContext, this.mContentResolver, buildUpon.build(), null, "err_type < ? AND due_time <= ? AND sim_slot2 = ?", new String[]{String.valueOf(10), String.valueOf(j), String.valueOf(i)}, Telephony.MmsSms.PendingMessages.DUE_TIME);
    }

    public Uri persist(GenericPdu genericPdu, Uri uri, int i, int i2) throws MmsException {
        return persist(genericPdu, 0, uri, i, i2, (HashMap<Uri, InputStream>) null);
    }

    public Uri persist(GenericPdu genericPdu, int i, Uri uri, int i2, int i3) throws MmsException {
        return persist(genericPdu, i, uri, i2, i3, (HashMap<Uri, InputStream>) null);
    }

    public Uri persist(GenericPdu genericPdu, Uri uri, int i, int i2, HashMap<Uri, InputStream> hashMap) throws MmsException {
        return persist(genericPdu, 0, uri, i, i2, hashMap);
    }

    public Uri persist(GenericPdu genericPdu, int i, Uri uri, int i2, int i3, HashMap<Uri, InputStream> hashMap) throws MmsException {
        return persist(genericPdu, i, uri, i2, i3, hashMap, 0);
    }

    public Uri persist(GenericPdu genericPdu, int i, Uri uri, int i2, int i3, HashMap<Uri, InputStream> hashMap, int i4) throws MmsException {
        EncodedStringValue[] encodedStringValueArr;
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
        HashMap hashMap2 = new HashMap(iArr.length);
        int length = iArr.length;
        int i5 = 0;
        while (true) {
            encodedStringValueArr = null;
            if (i5 >= length) {
                break;
            }
            int i6 = iArr[i5];
            if (i6 == 137) {
                EncodedStringValue encodedStringValue2 = pduHeaders.getEncodedStringValue(i6);
                if (encodedStringValue2 != null) {
                    encodedStringValueArr = new EncodedStringValue[]{encodedStringValue2};
                }
            } else {
                encodedStringValueArr = pduHeaders.getEncodedStringValues(i6);
            }
            hashMap2.put(Integer.valueOf(i6), encodedStringValueArr);
            i5++;
        }
        HashSet hashSet = new HashSet();
        int messageType = genericPdu.getMessageType();
        this.mTelephonyManager.getLine1Number();
        if (messageType == 130 || messageType == 132 || messageType == 128) {
            if (messageType == 128) {
                encodedStringValueArr = (EncodedStringValue[]) hashMap2.get(151);
            } else if (messageType == 130 || messageType == 132) {
                encodedStringValueArr = (EncodedStringValue[]) hashMap2.get(137);
            }
            if (encodedStringValueArr != null) {
                for (EncodedStringValue encodedStringValue3 : encodedStringValueArr) {
                    if (encodedStringValue3 != null) {
                        hashSet.add(encodedStringValue3.getString());
                    }
                }
            }
            if (!this.mCscFeature.getBoolean("CscFeature_Common_SupportTwoPhoneService", false)) {
                orCreateThreadId = Telephony.Threads.getOrCreateThreadId(this.mContext, hashSet);
            } else if (i4 > 0) {
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
        long nanoTime = System.nanoTime();
        if ((genericPdu instanceof MultimediaMessagePdu) && (body = ((MultimediaMessagePdu) genericPdu).getBody()) != null) {
            int partsNum = body.getPartsNum();
            for (int i7 = 0; i7 < partsNum; i7++) {
                persistPart(body.getPart(i7), nanoTime, hashMap);
            }
        }
        if (i2 > 0) {
            contentValues.put("app_id", Integer.valueOf(i2));
            contentValues.put("msg_id", Integer.valueOf(i3));
        }
        Uri insert = SqliteWrapper.insert(this.mContext, this.mContentResolver, uri, contentValues);
        if (insert == null) {
            throw new MmsException("persist() failed: return null.");
        }
        long parseId = ContentUris.parseId(insert);
        ContentValues contentValues2 = new ContentValues(1);
        contentValues2.put(Telephony.Mms.Part.MSG_ID, Long.valueOf(parseId));
        SqliteWrapper.update(this.mContext, this.mContentResolver, Uri.parse("content://mms/" + nanoTime + "/part"), contentValues2, null, null);
        Uri parse = Uri.parse(uri + "/" + parseId);
        for (int i8 : ADDRESS_FIELDS) {
            EncodedStringValue[] encodedStringValueArr2 = (EncodedStringValue[]) hashMap2.get(Integer.valueOf(i8));
            if (encodedStringValueArr2 != null) {
                persistAddress(parseId, i8, encodedStringValueArr2);
            }
        }
        return parse;
    }

    public Uri persist(GenericPdu genericPdu, Uri uri) throws MmsException {
        return persist(genericPdu, 0, uri, true, false, null, false, false);
    }

    public Uri persist(GenericPdu genericPdu, Uri uri, boolean z) throws MmsException {
        return persist(genericPdu, 0, uri, true, false, null, z, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:116:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0269  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x02a0  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x02ef  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x030c  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02c7  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x027b  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01c7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.net.Uri persist(com.google.android.mms.pdu.GenericPdu r24, int r25, android.net.Uri r26, boolean r27, boolean r28, java.util.HashMap<android.net.Uri, java.io.InputStream> r29, boolean r30, boolean r31, int r32) throws com.google.android.mms.MmsException {
        /*
            Method dump skipped, instructions count: 835
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.mms.pdu.PduPersister.persist(com.google.android.mms.pdu.GenericPdu, int, android.net.Uri, boolean, boolean, java.util.HashMap, boolean, boolean, int):android.net.Uri");
    }

    public Uri persist(GenericPdu genericPdu, Uri uri, boolean z, boolean z2, HashMap<Uri, InputStream> hashMap, boolean z3) throws MmsException {
        return persist(genericPdu, 0, uri, z, z2, hashMap, z3, true);
    }

    public Uri persist(GenericPdu genericPdu, int i, Uri uri, boolean z, boolean z2, HashMap<Uri, InputStream> hashMap) throws MmsException {
        return persist(genericPdu, i, uri, z, z2, hashMap, false, false);
    }

    public Uri persist(GenericPdu genericPdu, int i, Uri uri) throws MmsException {
        return persist(genericPdu, i, uri, true, false, null, false, true);
    }

    public Uri persist(GenericPdu genericPdu, int i, Uri uri, boolean z) throws MmsException {
        return persist(genericPdu, i, uri, true, false, null, z, true);
    }

    public Uri persist(GenericPdu genericPdu, Uri uri, boolean z, boolean z2, HashMap<Uri, InputStream> hashMap, boolean z3, boolean z4) throws MmsException {
        return persist(genericPdu, 0, uri, z, z2, hashMap, z3, z4);
    }

    public Uri persist(GenericPdu genericPdu, int i, Uri uri, boolean z, boolean z2, HashMap<Uri, InputStream> hashMap, boolean z3, boolean z4) throws MmsException {
        return persist(genericPdu, i, uri, z, z2, hashMap, z3, z4, 0);
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
                boolean equals;
                equals = ((String) obj).equals(str);
                return equals;
            }
        });
    }
}
