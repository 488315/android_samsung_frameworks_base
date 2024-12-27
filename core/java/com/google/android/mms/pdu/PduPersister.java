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

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.Predicate;

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
    private static final String[] PDU_PROJECTION = {
        "_id",
        Telephony.BaseMmsColumns.MESSAGE_BOX,
        "thread_id",
        Telephony.BaseMmsColumns.RETRIEVE_TEXT,
        Telephony.BaseMmsColumns.SUBJECT,
        Telephony.BaseMmsColumns.CONTENT_LOCATION,
        Telephony.BaseMmsColumns.CONTENT_TYPE,
        Telephony.BaseMmsColumns.MESSAGE_CLASS,
        Telephony.BaseMmsColumns.MESSAGE_ID,
        Telephony.BaseMmsColumns.RESPONSE_TEXT,
        Telephony.BaseMmsColumns.TRANSACTION_ID,
        Telephony.BaseMmsColumns.CONTENT_CLASS,
        Telephony.BaseMmsColumns.DELIVERY_REPORT,
        Telephony.BaseMmsColumns.MESSAGE_TYPE,
        "v",
        Telephony.BaseMmsColumns.PRIORITY,
        Telephony.BaseMmsColumns.READ_REPORT,
        Telephony.BaseMmsColumns.READ_STATUS,
        Telephony.BaseMmsColumns.REPORT_ALLOWED,
        Telephony.BaseMmsColumns.RETRIEVE_STATUS,
        Telephony.BaseMmsColumns.STATUS,
        "date",
        Telephony.BaseMmsColumns.DELIVERY_TIME,
        Telephony.BaseMmsColumns.EXPIRY,
        Telephony.BaseMmsColumns.MESSAGE_SIZE,
        Telephony.BaseMmsColumns.SUBJECT_CHARSET,
        Telephony.BaseMmsColumns.RETRIEVE_TEXT_CHARSET
    };
    private static final String[] PART_PROJECTION = {
        "_id",
        Telephony.Mms.Part.CHARSET,
        Telephony.Mms.Part.CONTENT_DISPOSITION,
        "cid",
        Telephony.Mms.Part.CONTENT_LOCATION,
        "ct",
        Telephony.Mms.Part.FILENAME,
        "name",
        "text"
    };
    private static final HashMap<Uri, Integer> MESSAGE_BOX_MAP = new HashMap<>();

    static {
        MESSAGE_BOX_MAP.put(Telephony.Mms.Inbox.CONTENT_URI, 1);
        MESSAGE_BOX_MAP.put(Telephony.Mms.Sent.CONTENT_URI, 2);
        MESSAGE_BOX_MAP.put(Telephony.Mms.Draft.CONTENT_URI, 3);
        MESSAGE_BOX_MAP.put(Telephony.Mms.Outbox.CONTENT_URI, 4);
        MESSAGE_BOX_MAP.put(Uri.parse("content://spammms/inbox"), 1);
        CHARSET_COLUMN_INDEX_MAP = new HashMap<>();
        CHARSET_COLUMN_INDEX_MAP.put(150, 25);
        CHARSET_COLUMN_INDEX_MAP.put(154, 26);
        CHARSET_COLUMN_NAME_MAP = new HashMap<>();
        CHARSET_COLUMN_NAME_MAP.put(150, Telephony.BaseMmsColumns.SUBJECT_CHARSET);
        CHARSET_COLUMN_NAME_MAP.put(154, Telephony.BaseMmsColumns.RETRIEVE_TEXT_CHARSET);
        ENCODED_STRING_COLUMN_INDEX_MAP = new HashMap<>();
        ENCODED_STRING_COLUMN_INDEX_MAP.put(154, 3);
        ENCODED_STRING_COLUMN_INDEX_MAP.put(150, 4);
        ENCODED_STRING_COLUMN_NAME_MAP = new HashMap<>();
        ENCODED_STRING_COLUMN_NAME_MAP.put(154, Telephony.BaseMmsColumns.RETRIEVE_TEXT);
        ENCODED_STRING_COLUMN_NAME_MAP.put(150, Telephony.BaseMmsColumns.SUBJECT);
        TEXT_STRING_COLUMN_INDEX_MAP = new HashMap<>();
        TEXT_STRING_COLUMN_INDEX_MAP.put(131, 5);
        TEXT_STRING_COLUMN_INDEX_MAP.put(132, 6);
        TEXT_STRING_COLUMN_INDEX_MAP.put(138, 7);
        TEXT_STRING_COLUMN_INDEX_MAP.put(139, 8);
        TEXT_STRING_COLUMN_INDEX_MAP.put(147, 9);
        TEXT_STRING_COLUMN_INDEX_MAP.put(152, 10);
        TEXT_STRING_COLUMN_NAME_MAP = new HashMap<>();
        TEXT_STRING_COLUMN_NAME_MAP.put(131, Telephony.BaseMmsColumns.CONTENT_LOCATION);
        TEXT_STRING_COLUMN_NAME_MAP.put(132, Telephony.BaseMmsColumns.CONTENT_TYPE);
        TEXT_STRING_COLUMN_NAME_MAP.put(138, Telephony.BaseMmsColumns.MESSAGE_CLASS);
        TEXT_STRING_COLUMN_NAME_MAP.put(139, Telephony.BaseMmsColumns.MESSAGE_ID);
        TEXT_STRING_COLUMN_NAME_MAP.put(147, Telephony.BaseMmsColumns.RESPONSE_TEXT);
        TEXT_STRING_COLUMN_NAME_MAP.put(152, Telephony.BaseMmsColumns.TRANSACTION_ID);
        OCTET_COLUMN_INDEX_MAP = new HashMap<>();
        OCTET_COLUMN_INDEX_MAP.put(186, 11);
        OCTET_COLUMN_INDEX_MAP.put(134, 12);
        OCTET_COLUMN_INDEX_MAP.put(140, 13);
        OCTET_COLUMN_INDEX_MAP.put(141, 14);
        OCTET_COLUMN_INDEX_MAP.put(143, 15);
        OCTET_COLUMN_INDEX_MAP.put(144, 16);
        OCTET_COLUMN_INDEX_MAP.put(155, 17);
        OCTET_COLUMN_INDEX_MAP.put(145, 18);
        OCTET_COLUMN_INDEX_MAP.put(153, 19);
        OCTET_COLUMN_INDEX_MAP.put(149, 20);
        OCTET_COLUMN_NAME_MAP = new HashMap<>();
        OCTET_COLUMN_NAME_MAP.put(186, Telephony.BaseMmsColumns.CONTENT_CLASS);
        OCTET_COLUMN_NAME_MAP.put(134, Telephony.BaseMmsColumns.DELIVERY_REPORT);
        OCTET_COLUMN_NAME_MAP.put(140, Telephony.BaseMmsColumns.MESSAGE_TYPE);
        OCTET_COLUMN_NAME_MAP.put(141, "v");
        OCTET_COLUMN_NAME_MAP.put(143, Telephony.BaseMmsColumns.PRIORITY);
        OCTET_COLUMN_NAME_MAP.put(144, Telephony.BaseMmsColumns.READ_REPORT);
        OCTET_COLUMN_NAME_MAP.put(155, Telephony.BaseMmsColumns.READ_STATUS);
        OCTET_COLUMN_NAME_MAP.put(145, Telephony.BaseMmsColumns.REPORT_ALLOWED);
        OCTET_COLUMN_NAME_MAP.put(153, Telephony.BaseMmsColumns.RETRIEVE_STATUS);
        OCTET_COLUMN_NAME_MAP.put(149, Telephony.BaseMmsColumns.STATUS);
        LONG_COLUMN_INDEX_MAP = new HashMap<>();
        LONG_COLUMN_INDEX_MAP.put(133, 21);
        LONG_COLUMN_INDEX_MAP.put(135, 22);
        LONG_COLUMN_INDEX_MAP.put(136, 23);
        LONG_COLUMN_INDEX_MAP.put(142, 24);
        LONG_COLUMN_NAME_MAP = new HashMap<>();
        LONG_COLUMN_NAME_MAP.put(133, "date");
        LONG_COLUMN_NAME_MAP.put(135, Telephony.BaseMmsColumns.DELIVERY_TIME);
        LONG_COLUMN_NAME_MAP.put(136, Telephony.BaseMmsColumns.EXPIRY);
        LONG_COLUMN_NAME_MAP.put(142, Telephony.BaseMmsColumns.MESSAGE_SIZE);
        LONG_COLUMN_NAME_MAP.put(192, "reserved");
        PDU_CACHE_INSTANCE = PduCache.getInstance();
    }

    private PduPersister(Context context) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
    }

    public static PduPersister getPduPersister(Context context) {
        if (sPersister == null) {
            Log.e(TAG, "sPersister is null");
            sPersister = new PduPersister(context);
        } else if (!context.equals(sPersister.mContext)) {
            Log.e(TAG, "context on pdupersist is not same");
            sPersister.release();
            sPersister = new PduPersister(context);
        }
        return sPersister;
    }

    private void setEncodedStringValueToHeaders(
            Cursor c, int columnIndex, PduHeaders headers, int mapColumn) {
        String s = c.getString(columnIndex);
        if (s != null && s.length() > 0) {
            int charsetColumnIndex =
                    CHARSET_COLUMN_INDEX_MAP.get(Integer.valueOf(mapColumn)).intValue();
            int charset = c.getInt(charsetColumnIndex);
            EncodedStringValue value = new EncodedStringValue(charset, getBytes(s));
            headers.setEncodedStringValue(value, mapColumn);
        }
    }

    private void setTextStringToHeaders(
            Cursor c, int columnIndex, PduHeaders headers, int mapColumn) {
        String s = c.getString(columnIndex);
        if (s != null) {
            headers.setTextString(getBytes(s), mapColumn);
        }
    }

    private void setOctetToHeaders(Cursor c, int columnIndex, PduHeaders headers, int mapColumn)
            throws InvalidHeaderValueException {
        if (!c.isNull(columnIndex)) {
            int b = c.getInt(columnIndex);
            headers.setOctet(b, mapColumn);
        }
    }

    private void setLongToHeaders(Cursor c, int columnIndex, PduHeaders headers, int mapColumn) {
        if (!c.isNull(columnIndex)) {
            long l = c.getLong(columnIndex);
            headers.setLongInteger(l, mapColumn);
        }
    }

    private Integer getIntegerFromPartColumn(Cursor c, int columnIndex) {
        if (!c.isNull(columnIndex)) {
            return Integer.valueOf(c.getInt(columnIndex));
        }
        return null;
    }

    private byte[] getByteArrayFromPartColumn(Cursor c, int columnIndex) {
        if (!c.isNull(columnIndex)) {
            return getBytes(c.getString(columnIndex));
        }
        return null;
    }

    private PduPart[] loadParts(long msgId) throws MmsException {
        return loadParts(msgId, false);
    }

    private void loadAddress(long msgId, PduHeaders headers) {
        loadAddress(msgId, headers, false);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.android.mms.pdu.GenericPdu load(android.net.Uri r21)
            throws com.google.android.mms.MmsException {
        /*
            Method dump skipped, instructions count: 722
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.mms.pdu.PduPersister.load(android.net.Uri):com.google.android.mms.pdu.GenericPdu");
    }

    private void persistAddress(long msgId, int type, EncodedStringValue[] array) {
        persistAddress(msgId, type, array, false);
    }

    private static String getPartContentType(PduPart part) {
        if (part.getContentType() == null) {
            return null;
        }
        return toIsoString(part.getContentType());
    }

    public Uri persistPart(PduPart part, long msgId, HashMap<Uri, InputStream> preOpenedFiles)
            throws MmsException {
        return persistPart(part, msgId, preOpenedFiles, 0, false, false);
    }

    private void persistData(
            PduPart part, Uri uri, String contentType, HashMap<Uri, InputStream> preOpenedFiles)
            throws MmsException {
        persistData(part, uri, contentType, preOpenedFiles, false, false);
    }

    private void updateAddress(long msgId, int type, EncodedStringValue[] array) {
        SqliteWrapper.delete(
                this.mContext,
                this.mContentResolver,
                Uri.parse("content://mms/" + msgId + "/addr"),
                "type=" + type,
                null);
        if (array != null) {
            persistAddress(msgId, type, array);
        }
    }

    public void updateHeaders(Uri uri, SendReq sendReq) {
        updateHeaders(uri, sendReq, 0);
    }

    private void updatePart(Uri uri, PduPart part, HashMap<Uri, InputStream> preOpenedFiles)
            throws MmsException {
        ContentValues values = new ContentValues(7);
        int charset = part.getCharset();
        if (charset != 0) {
            values.put(Telephony.Mms.Part.CHARSET, Integer.valueOf(charset));
        }
        if (part.getContentType() != null) {
            String contentType = toIsoString(part.getContentType());
            values.put("ct", contentType);
            if (part.getFilename() != null) {
                String fileName = new String(part.getFilename());
                values.put(Telephony.Mms.Part.FILENAME, fileName);
            }
            if (part.getName() != null) {
                String name = new String(part.getName());
                values.put("name", name);
            }
            Object value = null;
            if (part.getContentDisposition() != null) {
                value = toIsoString(part.getContentDisposition());
                values.put(Telephony.Mms.Part.CONTENT_DISPOSITION, (String) value);
            }
            if (part.getContentId() != null) {
                value = toIsoString(part.getContentId());
                values.put("cid", (String) value);
            }
            if (part.getContentLocation() != null) {
                Object value2 = toIsoString(part.getContentLocation());
                values.put(Telephony.Mms.Part.CONTENT_LOCATION, (String) value2);
            }
            SqliteWrapper.update(this.mContext, this.mContentResolver, uri, values, null, null);
            if (part.getData() != null || !uri.equals(part.getDataUri())) {
                persistData(part, uri, contentType, preOpenedFiles);
                return;
            }
            return;
        }
        throw new MmsException("MIME type of the part must be set.");
    }

    public void updateParts(Uri uri, PduBody body, HashMap<Uri, InputStream> preOpenedFiles)
            throws MmsException {
        try {
            synchronized (PDU_CACHE_INSTANCE) {
                if (PDU_CACHE_INSTANCE.isUpdating(uri)) {
                    try {
                        PDU_CACHE_INSTANCE.wait();
                    } catch (InterruptedException e) {
                        Log.e(TAG, "updateParts: ", e);
                    }
                    PduCacheEntry cacheEntry = PDU_CACHE_INSTANCE.get(uri);
                    if (cacheEntry != null) {
                        ((MultimediaMessagePdu) cacheEntry.getPdu()).setBody(body);
                    }
                }
                PDU_CACHE_INSTANCE.setUpdating(uri, true);
            }
            ArrayList<PduPart> toBeCreated = new ArrayList<>();
            HashMap<Uri, PduPart> toBeUpdated = new HashMap<>();
            int partsNum = body.getPartsNum();
            StringBuilder filter = new StringBuilder().append('(');
            for (int i = 0; i < partsNum; i++) {
                PduPart part = body.getPart(i);
                Uri partUri = part.getDataUri();
                if (partUri != null
                        && !TextUtils.isEmpty(partUri.getAuthority())
                        && partUri.getAuthority().startsWith("mms")) {
                    toBeUpdated.put(partUri, part);
                    if (filter.length() > 1) {
                        filter.append(" AND ");
                    }
                    filter.append("_id");
                    filter.append("!=");
                    DatabaseUtils.appendEscapedSQLString(filter, partUri.getLastPathSegment());
                }
                toBeCreated.add(part);
            }
            filter.append(')');
            long msgId = ContentUris.parseId(uri);
            SqliteWrapper.delete(
                    this.mContext,
                    this.mContentResolver,
                    Uri.parse(Telephony.Mms.CONTENT_URI + "/" + msgId + "/part"),
                    filter.length() > 2 ? filter.toString() : null,
                    null);
            Iterator<PduPart> it = toBeCreated.iterator();
            while (it.hasNext()) {
                persistPart(it.next(), msgId, preOpenedFiles);
            }
            for (Map.Entry<Uri, PduPart> e2 : toBeUpdated.entrySet()) {
                updatePart(e2.getKey(), e2.getValue(), preOpenedFiles);
            }
            synchronized (PDU_CACHE_INSTANCE) {
                PDU_CACHE_INSTANCE.setUpdating(uri, false);
                PDU_CACHE_INSTANCE.notifyAll();
            }
        } catch (Throwable th) {
            synchronized (PDU_CACHE_INSTANCE) {
                PDU_CACHE_INSTANCE.setUpdating(uri, false);
                PDU_CACHE_INSTANCE.notifyAll();
                throw th;
            }
        }
    }

    public Uri persist(
            GenericPdu pdu,
            Uri uri,
            boolean createThreadId,
            boolean groupMmsEnabled,
            HashMap<Uri, InputStream> preOpenedFiles)
            throws MmsException {
        return persist(pdu, 0, uri, createThreadId, groupMmsEnabled, preOpenedFiles, false, false);
    }

    private void loadRecipients(
            int addressType,
            HashSet<String> recipients,
            HashMap<Integer, EncodedStringValue[]> addressMap,
            boolean excludeMyNumber) {
        EncodedStringValue[] array = addressMap.get(Integer.valueOf(addressType));
        if (array == null) {
            return;
        }
        SubscriptionManager subscriptionManager = SubscriptionManager.from(this.mContext);
        Set<String> myPhoneNumbers = new HashSet<>();
        if (excludeMyNumber) {
            for (SubscriptionInfo subInfo : subscriptionManager.getActiveSubscriptionInfoList()) {
                String myNumber =
                        ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class))
                                .createForSubscriptionId(subInfo.getSubscriptionId())
                                .getLine1Number();
                if (myNumber != null) {
                    myPhoneNumbers.add(myNumber);
                }
            }
        }
        for (EncodedStringValue v : array) {
            if (v != null) {
                String number = v.getString();
                if (excludeMyNumber) {
                    Iterator<String> it = myPhoneNumbers.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        if (!PhoneNumberUtils.compare(number, it.next())
                                && !recipients.contains(number)) {
                            recipients.add(number);
                            break;
                        }
                    }
                } else if (!recipients.contains(number)) {
                    recipients.add(number);
                }
            }
        }
    }

    public Uri move(Uri from, Uri to) throws MmsException {
        long msgId = ContentUris.parseId(from);
        if (msgId == -1) {
            throw new MmsException("Error! ID of the message: -1.");
        }
        Integer msgBox = MESSAGE_BOX_MAP.get(to);
        if (msgBox == null) {
            throw new MmsException(
                    "Bad destination, must be one of content://mms/inbox, content://mms/sent,"
                        + " content://mms/drafts, content://mms/outbox, content://mms/temp.");
        }
        ContentValues values = new ContentValues(1);
        values.put(Telephony.BaseMmsColumns.MESSAGE_BOX, msgBox);
        SqliteWrapper.update(this.mContext, this.mContentResolver, from, values, null, null);
        return ContentUris.withAppendedId(to, msgId);
    }

    public static String toIsoString(byte[] bytes) {
        try {
            return new String(bytes, CharacterSets.MIMENAME_ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "ISO_8859_1 must be supported!", e);
            return "";
        }
    }

    public static byte[] getBytes(String data) {
        try {
            return data.getBytes(CharacterSets.MIMENAME_ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "ISO_8859_1 must be supported!", e);
            return new byte[0];
        }
    }

    public void release() {
        Log.d(TAG, "pdupersist release");
        Uri uri = Uri.parse(TEMPORARY_DRM_OBJECT_URI);
        SqliteWrapper.delete(this.mContext, this.mContentResolver, uri, null, null);
    }

    public Cursor getPendingMessages(long dueTime) {
        Uri.Builder uriBuilder = Telephony.MmsSms.PendingMessages.CONTENT_URI.buildUpon();
        uriBuilder.appendQueryParameter("protocol", "mms");
        String[] selectionArgs = {String.valueOf(10), String.valueOf(dueTime)};
        return SqliteWrapper.query(
                this.mContext,
                this.mContentResolver,
                uriBuilder.build(),
                null,
                "err_type < ? AND due_time <= ?",
                selectionArgs,
                Telephony.MmsSms.PendingMessages.DUE_TIME);
    }

    public void updateHeaders(Uri uri, SendReq sendReq, int simSlot) {
        updateHeaders(uri, sendReq, simSlot, 0);
    }

    public void updateHeaders(Uri uri, SendReq sendReq, int simSlot, int twoPhoneServiceUid) {
        ContentValues values;
        long threadId;
        int[] iArr;
        EncodedStringValue[] array;
        PduHeaders headers;
        EncodedStringValue[] array2;
        synchronized (PDU_CACHE_INSTANCE) {
            if (PDU_CACHE_INSTANCE.isUpdating(uri)) {
                try {
                    PDU_CACHE_INSTANCE.wait();
                } catch (InterruptedException e) {
                    Log.e(TAG, "updateHeaders: ", e);
                }
            }
        }
        PDU_CACHE_INSTANCE.purge(uri);
        if (this.mTelephonyManager.getPhoneCount() > 1) {
            values = new ContentValues(12);
        } else {
            values = new ContentValues(10);
        }
        byte[] contentType = sendReq.getContentType();
        if (contentType != null) {
            values.put(Telephony.BaseMmsColumns.CONTENT_TYPE, toIsoString(contentType));
        }
        long date = sendReq.getDate();
        if (date != -1) {
            values.put("date", Long.valueOf(date));
        }
        int deliveryReport = sendReq.getDeliveryReport();
        if (deliveryReport != 0) {
            values.put(Telephony.BaseMmsColumns.DELIVERY_REPORT, Integer.valueOf(deliveryReport));
        }
        long deliveryTime = sendReq.getDeliveryTime();
        if (deliveryTime != -1) {
            values.put(Telephony.BaseMmsColumns.DELIVERY_TIME, Long.valueOf(deliveryTime));
        }
        long expiry = sendReq.getExpiry();
        if (expiry != -1) {
            values.put(Telephony.BaseMmsColumns.EXPIRY, Long.valueOf(expiry));
        }
        byte[] msgClass = sendReq.getMessageClass();
        if (msgClass != null) {
            values.put(Telephony.BaseMmsColumns.MESSAGE_CLASS, toIsoString(msgClass));
        }
        int priority = sendReq.getPriority();
        if (priority != 0) {
            values.put(Telephony.BaseMmsColumns.PRIORITY, Integer.valueOf(priority));
        }
        int readReport = sendReq.getReadReport();
        if (readReport != 0) {
            values.put(Telephony.BaseMmsColumns.READ_REPORT, Integer.valueOf(readReport));
        }
        byte[] transId = sendReq.getTransactionId();
        if (transId != null) {
            values.put(Telephony.BaseMmsColumns.TRANSACTION_ID, toIsoString(transId));
        }
        EncodedStringValue subject = sendReq.getSubject();
        if (subject != null) {
            values.put(Telephony.BaseMmsColumns.SUBJECT, toIsoString(subject.getTextString()));
            values.put(
                    Telephony.BaseMmsColumns.SUBJECT_CHARSET,
                    Integer.valueOf(subject.getCharacterSet()));
        } else {
            values.put(Telephony.BaseMmsColumns.SUBJECT, "");
        }
        long messageSize = sendReq.getMessageSize();
        if (messageSize > 0) {
            values.put(Telephony.BaseMmsColumns.MESSAGE_SIZE, Long.valueOf(messageSize));
        }
        PduHeaders headers2 = sendReq.getPduHeaders();
        HashSet<String> recipients = new HashSet<>();
        int[] iArr2 = ADDRESS_FIELDS;
        int length = iArr2.length;
        int i = 0;
        while (i < length) {
            int addrType = iArr2[i];
            EncodedStringValue[] array3 = null;
            int i2 = length;
            if (addrType == 137) {
                EncodedStringValue v = headers2.getEncodedStringValue(addrType);
                if (v == null) {
                    iArr = iArr2;
                } else {
                    iArr = iArr2;
                    EncodedStringValue[] array4 = {v};
                    array3 = array4;
                }
                array = array3;
            } else {
                iArr = iArr2;
                EncodedStringValue[] array5 = headers2.getEncodedStringValues(addrType);
                array = array5;
            }
            if (array != null) {
                headers = headers2;
                long msgId = ContentUris.parseId(uri);
                updateAddress(msgId, addrType, array);
                if (addrType == 151) {
                    int length2 = array.length;
                    int addrType2 = 0;
                    while (addrType2 < length2) {
                        EncodedStringValue v2 = array[addrType2];
                        if (v2 == null) {
                            array2 = array;
                        } else {
                            array2 = array;
                            recipients.add(v2.getString());
                        }
                        addrType2++;
                        array = array2;
                    }
                }
            } else {
                headers = headers2;
            }
            i++;
            headers2 = headers;
            length = i2;
            iArr2 = iArr;
        }
        if (!recipients.isEmpty()) {
            if (this.mCscFeature.getBoolean("CscFeature_Common_SupportTwoPhoneService", false)) {
                if (this.mTelephonyManager.getPhoneCount() > 1) {
                    if (twoPhoneServiceUid > 0) {
                        threadId =
                                Telephony.Threads.semGetOrCreateThreadId(
                                        this.mContext,
                                        recipients,
                                        true,
                                        simSlot,
                                        twoPhoneServiceUid);
                    } else {
                        threadId =
                                Telephony.Threads.getOrCreateThreadId(
                                        this.mContext, recipients, simSlot);
                    }
                } else if (twoPhoneServiceUid > 0) {
                    threadId =
                            Telephony.Threads.semGetOrCreateThreadId(
                                    this.mContext, recipients, true, 0, twoPhoneServiceUid);
                } else {
                    threadId = Telephony.Threads.getOrCreateThreadId(this.mContext, recipients);
                }
            } else if (this.mTelephonyManager.getPhoneCount() > 1) {
                threadId =
                        Telephony.Threads.getOrCreateThreadId(this.mContext, recipients, simSlot);
            } else {
                threadId = Telephony.Threads.getOrCreateThreadId(this.mContext, recipients);
            }
            values.put("thread_id", Long.valueOf(threadId));
        }
        if (this.mCscFeature.getBoolean("CscFeature_Common_SupportTwoPhoneService", false)
                && twoPhoneServiceUid > 0) {
            values.put("using_mode", Integer.valueOf(twoPhoneServiceUid));
        }
        long reserved = sendReq.getReserved();
        if (reserved != -1) {
            values.put("reserved", Long.valueOf(reserved));
        }
        SqliteWrapper.update(this.mContext, this.mContentResolver, uri, values, null, null);
    }

    /*  JADX ERROR: Types fix failed
    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:183)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:242)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:221)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
    */
    /* JADX WARN: Failed to apply debug info
    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnoreUnknown(TypeUpdate.java:74)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:137)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:133)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.searchAndApplyVarDebugInfo(DebugInfoApplyVisitor.java:75)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.lambda$applyDebugInfo$0(DebugInfoApplyVisitor.java:68)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:68)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.visit(DebugInfoApplyVisitor.java:55)
     */
    private void persistData(
            com.google.android.mms.pdu.PduPart r25,
            android.net.Uri r26,
            java.lang.String r27,
            java.util.HashMap<android.net.Uri, java.io.InputStream> r28,
            boolean r29,
            boolean r30)
            throws com.google.android.mms.MmsException {
        /*
            Method dump skipped, instructions count: 1226
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.mms.pdu.PduPersister.persistData(com.google.android.mms.pdu.PduPart,"
                    + " android.net.Uri, java.lang.String, java.util.HashMap, boolean,"
                    + " boolean):void");
    }

    public Uri persistPart(
            PduPart part,
            long msgId,
            HashMap<Uri, InputStream> preOpenedFiles,
            int simSlot,
            boolean bSpam,
            boolean hasVendorDrmEngine)
            throws MmsException {
        Uri uri;
        String contentType;
        if (bSpam) {
            uri = Uri.parse("content://spammms/" + msgId + "/spampart");
        } else {
            uri = Uri.parse("content://mms/" + msgId + "/part");
        }
        ContentValues values = new ContentValues(8);
        int charset = part.getCharset();
        if (charset != 0) {
            values.put(Telephony.Mms.Part.CHARSET, Integer.valueOf(charset));
        }
        String contentType2 = getPartContentType(part);
        if (contentType2 == null) {
            throw new MmsException("MIME type of the part must be set.");
        }
        if (!ContentType.IMAGE_JPG.equals(contentType2)) {
            contentType = contentType2;
        } else {
            contentType = ContentType.IMAGE_JPEG;
        }
        values.put("ct", contentType);
        if (ContentType.APP_SMIL.equals(contentType)) {
            values.put("seq", (Integer) (-1));
        }
        if (part.getFilename() != null) {
            if (isSupportOMA13NameEncoding(simSlot)) {
                values.put(Telephony.Mms.Part.FILENAME, toIsoString(part.getFilename()));
            } else {
                String fileName = new String(part.getFilename());
                if (!isOma13Encoding(fileName)) {
                    StringTokenizer st = new StringTokenizer(fileName, "\\/:*?\"<>|");
                    fileName = "";
                    while (st.hasMoreTokens()) {
                        fileName = fileName + st.nextToken();
                    }
                }
                values.put(Telephony.Mms.Part.FILENAME, fileName);
            }
        }
        if (part.getName() != null) {
            if (isSupportOMA13NameEncoding(simSlot)) {
                values.put("name", toIsoString(part.getName()));
            } else {
                String name = new String(part.getName());
                if (!isOma13Encoding(name)) {
                    StringTokenizer st2 = new StringTokenizer(name, "\\/:*?\"<>|");
                    name = "";
                    while (st2.hasMoreTokens()) {
                        name = name + st2.nextToken();
                    }
                }
                values.put("name", toIsoString(name.getBytes()));
            }
        }
        Object value = null;
        if (part.getContentDisposition() != null) {
            value = toIsoString(part.getContentDisposition());
            values.put(Telephony.Mms.Part.CONTENT_DISPOSITION, (String) value);
        }
        if (part.getContentId() != null) {
            value = toIsoString(part.getContentId());
            values.put("cid", (String) value);
        }
        if (part.getContentLocation() != null) {
            Object value2 = toIsoString(part.getContentLocation());
            values.put(Telephony.Mms.Part.CONTENT_LOCATION, (String) value2);
        }
        Uri res = SqliteWrapper.insert(this.mContext, this.mContentResolver, uri, values);
        if (res == null) {
            throw new MmsException("Failed to persist part, return null.");
        }
        persistData(part, res, contentType, preOpenedFiles, bSpam, hasVendorDrmEngine);
        part.setDataUri(res);
        return res;
    }

    private void persistAddress(long msgId, int type, EncodedStringValue[] array, boolean bSpam) {
        Uri uri;
        ContentValues values = new ContentValues(3);
        for (EncodedStringValue addr : array) {
            values.clear();
            values.put("address", toIsoString(addr.getTextString()));
            values.put(Telephony.Mms.Addr.CHARSET, Integer.valueOf(addr.getCharacterSet()));
            values.put("type", Integer.valueOf(type));
            if (bSpam) {
                uri = Uri.parse("content://spammms/" + msgId + "/spamaddr");
            } else {
                uri = Uri.parse("content://mms/" + msgId + "/addr");
            }
            SqliteWrapper.insert(this.mContext, this.mContentResolver, uri, values);
        }
    }

    private void loadAddress(long msgId, PduHeaders headers, boolean bSpam) {
        Cursor c;
        if (bSpam) {
            Cursor c2 =
                    SqliteWrapper.query(
                            this.mContext,
                            this.mContentResolver,
                            Uri.parse("content://spammms/" + msgId + "/spamaddr"),
                            new String[] {"address", Telephony.Mms.Addr.CHARSET, "type"},
                            null,
                            null,
                            null);
            c = c2;
        } else {
            Cursor c3 =
                    SqliteWrapper.query(
                            this.mContext,
                            this.mContentResolver,
                            Uri.parse("content://mms/" + msgId + "/addr"),
                            new String[] {"address", Telephony.Mms.Addr.CHARSET, "type"},
                            null,
                            null,
                            null);
            c = c3;
        }
        if (c != null) {
            while (c.moveToNext()) {
                try {
                    String addr = c.getString(0);
                    if (!TextUtils.isEmpty(addr)) {
                        int addrType = c.getInt(2);
                        switch (addrType) {
                            case 129:
                            case 130:
                            case 151:
                                headers.appendEncodedStringValue(
                                        new EncodedStringValue(c.getInt(1), getBytes(addr)),
                                        addrType);
                                break;
                            case 137:
                                headers.setEncodedStringValue(
                                        new EncodedStringValue(c.getInt(1), getBytes(addr)),
                                        addrType);
                                break;
                            default:
                                Log.e(TAG, "Unknown address type: " + addrType);
                                break;
                        }
                    }
                } finally {
                    c.close();
                }
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.google.android.mms.pdu.PduPart[] loadParts(long r28, boolean r30)
            throws com.google.android.mms.MmsException {
        /*
            Method dump skipped, instructions count: 631
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.google.android.mms.pdu.PduPersister.loadParts(long,"
                    + " boolean):com.google.android.mms.pdu.PduPart[]");
    }

    private static boolean isOma13Encoding(String filename) {
        boolean result = false;
        if (filename == null) {
            return false;
        }
        if (filename.length() >= 5
                && filename.startsWith(ENCODING_PREFIX)
                && filename.endsWith(ENCODING_SUFFIX)) {
            result = true;
        }
        Log.d(TAG, "pdupersister isOma13Encoding:" + result);
        return result;
    }

    public Cursor getPendingMessages(int simSlot, long dueTime) {
        Uri.Builder uriBuilder = Telephony.MmsSms.PendingMessages.CONTENT_URI.buildUpon();
        uriBuilder.appendQueryParameter("protocol", "mms");
        String[] selectionArgs = {
            String.valueOf(10), String.valueOf(dueTime), String.valueOf(simSlot)
        };
        return SqliteWrapper.query(
                this.mContext,
                this.mContentResolver,
                uriBuilder.build(),
                null,
                "err_type < ? AND due_time <= ? AND sim_slot2 = ?",
                selectionArgs,
                Telephony.MmsSms.PendingMessages.DUE_TIME);
    }

    public Uri persist(GenericPdu pdu, Uri uri, int reqAppId, int reqMsgId) throws MmsException {
        return persist(pdu, 0, uri, reqAppId, reqMsgId, (HashMap<Uri, InputStream>) null);
    }

    public Uri persist(GenericPdu pdu, int simSlot, Uri uri, int reqAppId, int reqMsgId)
            throws MmsException {
        return persist(pdu, simSlot, uri, reqAppId, reqMsgId, (HashMap<Uri, InputStream>) null);
    }

    public Uri persist(
            GenericPdu pdu,
            Uri uri,
            int reqAppId,
            int reqMsgId,
            HashMap<Uri, InputStream> preOpenedFiles)
            throws MmsException {
        return persist(pdu, 0, uri, reqAppId, reqMsgId, preOpenedFiles);
    }

    public Uri persist(
            GenericPdu pdu,
            int simSlot,
            Uri uri,
            int reqAppId,
            int reqMsgId,
            HashMap<Uri, InputStream> preOpenedFiles)
            throws MmsException {
        return persist(pdu, simSlot, uri, reqAppId, reqMsgId, preOpenedFiles, 0);
    }

    public Uri persist(
            GenericPdu pdu,
            int simSlot,
            Uri uri,
            int reqAppId,
            int reqMsgId,
            HashMap<Uri, InputStream> preOpenedFiles,
            int twoPhoneServiceUid)
            throws MmsException {
        PduBody body;
        int i;
        PduBody body2;
        if (uri == null) {
            throw new MmsException("Uri may not be null.");
        }
        Integer msgBox = MESSAGE_BOX_MAP.get(uri);
        if (msgBox == null) {
            throw new MmsException(
                    "Bad destination, must be one of content://mms/inbox, content://mms/sent,"
                        + " content://mms/drafts, content://mms/outbox, content://mms/temp.");
        }
        PDU_CACHE_INSTANCE.purge(uri);
        PduHeaders header = pdu.getPduHeaders();
        ContentValues values = new ContentValues();
        Set<Map.Entry<Integer, String>> set = ENCODED_STRING_COLUMN_NAME_MAP.entrySet();
        for (Map.Entry<Integer, String> e : set) {
            int field = e.getKey().intValue();
            EncodedStringValue encodedString = header.getEncodedStringValue(field);
            if (encodedString != null) {
                String charsetColumn = CHARSET_COLUMN_NAME_MAP.get(Integer.valueOf(field));
                values.put(e.getValue(), toIsoString(encodedString.getTextString()));
                values.put(charsetColumn, Integer.valueOf(encodedString.getCharacterSet()));
            }
        }
        Set<Map.Entry<Integer, String>> set2 = TEXT_STRING_COLUMN_NAME_MAP.entrySet();
        for (Map.Entry<Integer, String> e2 : set2) {
            byte[] text = header.getTextString(e2.getKey().intValue());
            if (text != null) {
                values.put(e2.getValue(), toIsoString(text));
            }
        }
        Set<Map.Entry<Integer, String>> set3 = OCTET_COLUMN_NAME_MAP.entrySet();
        for (Map.Entry<Integer, String> e3 : set3) {
            int b = header.getOctet(e3.getKey().intValue());
            if (b != 0) {
                values.put(e3.getValue(), Integer.valueOf(b));
            }
        }
        Set<Map.Entry<Integer, String>> set4 = LONG_COLUMN_NAME_MAP.entrySet();
        for (Map.Entry<Integer, String> e4 : set4) {
            long l = header.getLongInteger(e4.getKey().intValue());
            if (l != -1) {
                values.put(e4.getValue(), Long.valueOf(l));
            }
        }
        HashMap<Integer, EncodedStringValue[]> addressMap = new HashMap<>(ADDRESS_FIELDS.length);
        for (int addrType : ADDRESS_FIELDS) {
            EncodedStringValue[] array = null;
            if (addrType == 137) {
                EncodedStringValue v = header.getEncodedStringValue(addrType);
                if (v != null) {
                    EncodedStringValue[] array2 = {v};
                    array = array2;
                }
            } else {
                array = header.getEncodedStringValues(addrType);
            }
            addressMap.put(Integer.valueOf(addrType), array);
        }
        HashSet<String> recipients = new HashSet<>();
        long threadId = Long.MAX_VALUE;
        int msgType = pdu.getMessageType();
        this.mTelephonyManager.getLine1Number();
        if (msgType != 130 && msgType != 132 && msgType != 128) {
            body = null;
        } else {
            EncodedStringValue[] array3 = null;
            switch (msgType) {
                case 128:
                    EncodedStringValue[] array4 = addressMap.get(151);
                    array3 = array4;
                    break;
                case 130:
                case 132:
                    EncodedStringValue[] array5 = addressMap.get(137);
                    array3 = array5;
                    break;
            }
            if (array3 == null) {
                body = null;
            } else {
                int length = array3.length;
                body = null;
                int i2 = 0;
                while (i2 < length) {
                    EncodedStringValue v2 = array3[i2];
                    if (v2 == null) {
                        i = length;
                    } else {
                        i = length;
                        recipients.add(v2.getString());
                    }
                    i2++;
                    length = i;
                }
            }
            if (!this.mCscFeature.getBoolean("CscFeature_Common_SupportTwoPhoneService", false)) {
                threadId = Telephony.Threads.getOrCreateThreadId(this.mContext, recipients);
            } else {
                threadId =
                        twoPhoneServiceUid > 0
                                ? Telephony.Threads.semGetOrCreateThreadId(
                                        this.mContext, recipients, true, 0, twoPhoneServiceUid)
                                : Telephony.Threads.getOrCreateThreadId(this.mContext, recipients);
            }
        }
        values.put("thread_id", Long.valueOf(threadId));
        if (this.mCscFeature.getBoolean("CscFeature_Common_SupportTwoPhoneService", false)
                && twoPhoneServiceUid > 0) {
            values.put("using_mode", Integer.valueOf(twoPhoneServiceUid));
        }
        long dummyId = System.nanoTime();
        if (pdu instanceof MultimediaMessagePdu) {
            PduBody body3 = ((MultimediaMessagePdu) pdu).getBody();
            if (body3 != null) {
                int partsNum = body3.getPartsNum();
                for (int i3 = 0; i3 < partsNum; i3++) {
                    PduPart part = body3.getPart(i3);
                    persistPart(part, dummyId, preOpenedFiles);
                }
            }
            body2 = body3;
        } else {
            body2 = body;
        }
        if (reqAppId > 0) {
            values.put("app_id", Integer.valueOf(reqAppId));
            values.put("msg_id", Integer.valueOf(reqMsgId));
        }
        Uri res = SqliteWrapper.insert(this.mContext, this.mContentResolver, uri, values);
        if (res == null) {
            throw new MmsException("persist() failed: return null.");
        }
        long msgId = ContentUris.parseId(res);
        ContentValues values2 = new ContentValues(1);
        values2.put(Telephony.Mms.Part.MSG_ID, Long.valueOf(msgId));
        SqliteWrapper.update(
                this.mContext,
                this.mContentResolver,
                Uri.parse("content://mms/" + dummyId + "/part"),
                values2,
                null,
                null);
        Uri res2 = Uri.parse(uri + "/" + msgId);
        int[] iArr = ADDRESS_FIELDS;
        int length2 = iArr.length;
        int i4 = 0;
        while (i4 < length2) {
            int addrType2 = iArr[i4];
            ContentValues values3 = values2;
            EncodedStringValue[] array6 = addressMap.get(Integer.valueOf(addrType2));
            if (array6 != null) {
                persistAddress(msgId, addrType2, array6);
            }
            i4++;
            values2 = values3;
        }
        return res2;
    }

    public Uri persist(GenericPdu pdu, Uri uri) throws MmsException {
        return persist(pdu, 0, uri, true, false, null, false, false);
    }

    public Uri persist(GenericPdu pdu, Uri uri, boolean bSpam) throws MmsException {
        return persist(pdu, 0, uri, true, false, null, bSpam, true);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.net.Uri persist(
            com.google.android.mms.pdu.GenericPdu r41,
            int r42,
            android.net.Uri r43,
            boolean r44,
            boolean r45,
            java.util.HashMap<android.net.Uri, java.io.InputStream> r46,
            boolean r47,
            boolean r48,
            int r49)
            throws com.google.android.mms.MmsException {
        /*
            Method dump skipped, instructions count: 1056
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.mms.pdu.PduPersister.persist(com.google.android.mms.pdu.GenericPdu,"
                    + " int, android.net.Uri, boolean, boolean, java.util.HashMap, boolean,"
                    + " boolean, int):android.net.Uri");
    }

    public Uri persist(
            GenericPdu pdu,
            Uri uri,
            boolean createThreadId,
            boolean groupMmsEnabled,
            HashMap<Uri, InputStream> preOpenedFiles,
            boolean bSpam)
            throws MmsException {
        return persist(pdu, 0, uri, createThreadId, groupMmsEnabled, preOpenedFiles, bSpam, true);
    }

    public Uri persist(
            GenericPdu pdu,
            int simSlot,
            Uri uri,
            boolean createThreadId,
            boolean groupMmsEnabled,
            HashMap<Uri, InputStream> preOpenedFiles)
            throws MmsException {
        return persist(
                pdu, simSlot, uri, createThreadId, groupMmsEnabled, preOpenedFiles, false, false);
    }

    public Uri persist(GenericPdu pdu, int simSlot, Uri uri) throws MmsException {
        return persist(pdu, simSlot, uri, true, false, null, false, true);
    }

    public Uri persist(GenericPdu pdu, int simSlot, Uri uri, boolean bSpam) throws MmsException {
        return persist(pdu, simSlot, uri, true, false, null, bSpam, true);
    }

    public Uri persist(
            GenericPdu pdu,
            Uri uri,
            boolean createThreadId,
            boolean groupMmsEnabled,
            HashMap<Uri, InputStream> preOpenedFiles,
            boolean bSpam,
            boolean hasVendorDrmEngine)
            throws MmsException {
        return persist(
                pdu,
                0,
                uri,
                createThreadId,
                groupMmsEnabled,
                preOpenedFiles,
                bSpam,
                hasVendorDrmEngine);
    }

    public Uri persist(
            GenericPdu pdu,
            int simSlot,
            Uri uri,
            boolean createThreadId,
            boolean groupMmsEnabled,
            HashMap<Uri, InputStream> preOpenedFiles,
            boolean bSpam,
            boolean hasVendorDrmEngine)
            throws MmsException {
        return persist(
                pdu,
                simSlot,
                uri,
                createThreadId,
                groupMmsEnabled,
                preOpenedFiles,
                bSpam,
                hasVendorDrmEngine,
                0);
    }

    private boolean isSupportOMA13NameEncoding(int simSlot) {
        final String matchedCode;
        if (simSlot == 0) {
            matchedCode =
                    SystemProperties.get(
                            "mdc.matched_code", SystemProperties.get("ro.csc.sales_code", ""));
        } else {
            matchedCode =
                    SystemProperties.get(
                            "mdc.matched_code2", SystemProperties.get("ro.csc.sales_code", ""));
        }
        if (TextUtils.isEmpty(matchedCode)) {
            return false;
        }
        String[] supportCode = {"CHC", "CHM", "CHN", "KTC", "LUC", "SKC", "KOO", "K06", "K01"};
        return Arrays.stream(supportCode)
                .anyMatch(
                        new Predicate() { // from class:
                                          // com.google.android.mms.pdu.PduPersister$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean equals;
                                equals = ((String) obj).equals(matchedCode);
                                return equals;
                            }
                        });
    }
}
