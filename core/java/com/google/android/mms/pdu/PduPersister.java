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
import com.samsung.android.share.SemShareConstants;
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
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
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
            Log.m96e(TAG, "sPersister is null");
            sPersister = new PduPersister(context);
        } else if (!context.equals(pduPersister.mContext)) {
            Log.m96e(TAG, "context on pdupersist is not same");
            sPersister.release();
            sPersister = new PduPersister(context);
        }
        return sPersister;
    }

    private void setEncodedStringValueToHeaders(Cursor c, int columnIndex, PduHeaders headers, int mapColumn) {
        String s = c.getString(columnIndex);
        if (s != null && s.length() > 0) {
            int charsetColumnIndex = CHARSET_COLUMN_INDEX_MAP.get(Integer.valueOf(mapColumn)).intValue();
            int charset = c.getInt(charsetColumnIndex);
            EncodedStringValue value = new EncodedStringValue(charset, getBytes(s));
            headers.setEncodedStringValue(value, mapColumn);
        }
    }

    private void setTextStringToHeaders(Cursor c, int columnIndex, PduHeaders headers, int mapColumn) {
        String s = c.getString(columnIndex);
        if (s != null) {
            headers.setTextString(getBytes(s), mapColumn);
        }
    }

    private void setOctetToHeaders(Cursor c, int columnIndex, PduHeaders headers, int mapColumn) throws InvalidHeaderValueException {
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

    /* JADX WARN: Removed duplicated region for block: B:23:0x025d A[Catch: all -> 0x0262, TRY_ENTER, TryCatch #1 {all -> 0x0262, blocks: (B:13:0x0054, B:56:0x014f, B:59:0x0158, B:61:0x015f, B:64:0x0169, B:70:0x019e, B:71:0x01a1, B:72:0x0214, B:73:0x022e, B:74:0x01a5, B:85:0x01ac, B:86:0x01b3, B:87:0x01ba, B:88:0x01c1, B:89:0x01c8, B:90:0x01cf, B:91:0x01d6, B:92:0x01f2, B:93:0x01f3, B:95:0x0185, B:97:0x018b, B:99:0x0194, B:102:0x022f, B:103:0x023a, B:23:0x025d, B:24:0x0261), top: B:12:0x0054 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01a1 A[Catch: all -> 0x0262, TryCatch #1 {all -> 0x0262, blocks: (B:13:0x0054, B:56:0x014f, B:59:0x0158, B:61:0x015f, B:64:0x0169, B:70:0x019e, B:71:0x01a1, B:72:0x0214, B:73:0x022e, B:74:0x01a5, B:85:0x01ac, B:86:0x01b3, B:87:0x01ba, B:88:0x01c1, B:89:0x01c8, B:90:0x01cf, B:91:0x01d6, B:92:0x01f2, B:93:0x01f3, B:95:0x0185, B:97:0x018b, B:99:0x0194, B:102:0x022f, B:103:0x023a, B:23:0x025d, B:24:0x0261), top: B:12:0x0054 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01a5 A[Catch: all -> 0x0262, TryCatch #1 {all -> 0x0262, blocks: (B:13:0x0054, B:56:0x014f, B:59:0x0158, B:61:0x015f, B:64:0x0169, B:70:0x019e, B:71:0x01a1, B:72:0x0214, B:73:0x022e, B:74:0x01a5, B:85:0x01ac, B:86:0x01b3, B:87:0x01ba, B:88:0x01c1, B:89:0x01c8, B:90:0x01cf, B:91:0x01d6, B:92:0x01f2, B:93:0x01f3, B:95:0x0185, B:97:0x018b, B:99:0x0194, B:102:0x022f, B:103:0x023a, B:23:0x025d, B:24:0x0261), top: B:12:0x0054 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01fe A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01ac A[Catch: all -> 0x0262, TryCatch #1 {all -> 0x0262, blocks: (B:13:0x0054, B:56:0x014f, B:59:0x0158, B:61:0x015f, B:64:0x0169, B:70:0x019e, B:71:0x01a1, B:72:0x0214, B:73:0x022e, B:74:0x01a5, B:85:0x01ac, B:86:0x01b3, B:87:0x01ba, B:88:0x01c1, B:89:0x01c8, B:90:0x01cf, B:91:0x01d6, B:92:0x01f2, B:93:0x01f3, B:95:0x0185, B:97:0x018b, B:99:0x0194, B:102:0x022f, B:103:0x023a, B:23:0x025d, B:24:0x0261), top: B:12:0x0054 }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01b3 A[Catch: all -> 0x0262, TryCatch #1 {all -> 0x0262, blocks: (B:13:0x0054, B:56:0x014f, B:59:0x0158, B:61:0x015f, B:64:0x0169, B:70:0x019e, B:71:0x01a1, B:72:0x0214, B:73:0x022e, B:74:0x01a5, B:85:0x01ac, B:86:0x01b3, B:87:0x01ba, B:88:0x01c1, B:89:0x01c8, B:90:0x01cf, B:91:0x01d6, B:92:0x01f2, B:93:0x01f3, B:95:0x0185, B:97:0x018b, B:99:0x0194, B:102:0x022f, B:103:0x023a, B:23:0x025d, B:24:0x0261), top: B:12:0x0054 }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01ba A[Catch: all -> 0x0262, TryCatch #1 {all -> 0x0262, blocks: (B:13:0x0054, B:56:0x014f, B:59:0x0158, B:61:0x015f, B:64:0x0169, B:70:0x019e, B:71:0x01a1, B:72:0x0214, B:73:0x022e, B:74:0x01a5, B:85:0x01ac, B:86:0x01b3, B:87:0x01ba, B:88:0x01c1, B:89:0x01c8, B:90:0x01cf, B:91:0x01d6, B:92:0x01f2, B:93:0x01f3, B:95:0x0185, B:97:0x018b, B:99:0x0194, B:102:0x022f, B:103:0x023a, B:23:0x025d, B:24:0x0261), top: B:12:0x0054 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01c1 A[Catch: all -> 0x0262, TryCatch #1 {all -> 0x0262, blocks: (B:13:0x0054, B:56:0x014f, B:59:0x0158, B:61:0x015f, B:64:0x0169, B:70:0x019e, B:71:0x01a1, B:72:0x0214, B:73:0x022e, B:74:0x01a5, B:85:0x01ac, B:86:0x01b3, B:87:0x01ba, B:88:0x01c1, B:89:0x01c8, B:90:0x01cf, B:91:0x01d6, B:92:0x01f2, B:93:0x01f3, B:95:0x0185, B:97:0x018b, B:99:0x0194, B:102:0x022f, B:103:0x023a, B:23:0x025d, B:24:0x0261), top: B:12:0x0054 }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01c8 A[Catch: all -> 0x0262, TryCatch #1 {all -> 0x0262, blocks: (B:13:0x0054, B:56:0x014f, B:59:0x0158, B:61:0x015f, B:64:0x0169, B:70:0x019e, B:71:0x01a1, B:72:0x0214, B:73:0x022e, B:74:0x01a5, B:85:0x01ac, B:86:0x01b3, B:87:0x01ba, B:88:0x01c1, B:89:0x01c8, B:90:0x01cf, B:91:0x01d6, B:92:0x01f2, B:93:0x01f3, B:95:0x0185, B:97:0x018b, B:99:0x0194, B:102:0x022f, B:103:0x023a, B:23:0x025d, B:24:0x0261), top: B:12:0x0054 }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01cf A[Catch: all -> 0x0262, TryCatch #1 {all -> 0x0262, blocks: (B:13:0x0054, B:56:0x014f, B:59:0x0158, B:61:0x015f, B:64:0x0169, B:70:0x019e, B:71:0x01a1, B:72:0x0214, B:73:0x022e, B:74:0x01a5, B:85:0x01ac, B:86:0x01b3, B:87:0x01ba, B:88:0x01c1, B:89:0x01c8, B:90:0x01cf, B:91:0x01d6, B:92:0x01f2, B:93:0x01f3, B:95:0x0185, B:97:0x018b, B:99:0x0194, B:102:0x022f, B:103:0x023a, B:23:0x025d, B:24:0x0261), top: B:12:0x0054 }] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01d6 A[Catch: all -> 0x0262, TryCatch #1 {all -> 0x0262, blocks: (B:13:0x0054, B:56:0x014f, B:59:0x0158, B:61:0x015f, B:64:0x0169, B:70:0x019e, B:71:0x01a1, B:72:0x0214, B:73:0x022e, B:74:0x01a5, B:85:0x01ac, B:86:0x01b3, B:87:0x01ba, B:88:0x01c1, B:89:0x01c8, B:90:0x01cf, B:91:0x01d6, B:92:0x01f2, B:93:0x01f3, B:95:0x0185, B:97:0x018b, B:99:0x0194, B:102:0x022f, B:103:0x023a, B:23:0x025d, B:24:0x0261), top: B:12:0x0054 }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01f3 A[Catch: all -> 0x0262, TRY_LEAVE, TryCatch #1 {all -> 0x0262, blocks: (B:13:0x0054, B:56:0x014f, B:59:0x0158, B:61:0x015f, B:64:0x0169, B:70:0x019e, B:71:0x01a1, B:72:0x0214, B:73:0x022e, B:74:0x01a5, B:85:0x01ac, B:86:0x01b3, B:87:0x01ba, B:88:0x01c1, B:89:0x01c8, B:90:0x01cf, B:91:0x01d6, B:92:0x01f2, B:93:0x01f3, B:95:0x0185, B:97:0x018b, B:99:0x0194, B:102:0x022f, B:103:0x023a, B:23:0x025d, B:24:0x0261), top: B:12:0x0054 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public GenericPdu load(Uri uri) throws MmsException {
        PduCacheEntry cacheEntry;
        GenericPdu pdu;
        PduCache pduCache;
        try {
            PduCache pduCache2 = PDU_CACHE_INSTANCE;
            synchronized (pduCache2) {
                try {
                    if (pduCache2.isUpdating(uri)) {
                        try {
                            pduCache2.wait();
                        } catch (InterruptedException e) {
                            Log.m97e(TAG, "load: ", e);
                        }
                        PduCache pduCache3 = PDU_CACHE_INSTANCE;
                        PduCacheEntry cacheEntry2 = pduCache3.get(uri);
                        if (cacheEntry2 != null) {
                            GenericPdu pdu2 = cacheEntry2.getPdu();
                            synchronized (pduCache3) {
                                if (0 != 0) {
                                    pduCache3.put(uri, new PduCacheEntry(null, 0, -1L));
                                }
                                pduCache3.setUpdating(uri, false);
                                pduCache3.notifyAll();
                            }
                            return pdu2;
                        }
                        cacheEntry = cacheEntry2;
                    } else {
                        cacheEntry = null;
                    }
                    try {
                        PDU_CACHE_INSTANCE.setUpdating(uri, true);
                        try {
                            Cursor c = SqliteWrapper.query(this.mContext, this.mContentResolver, uri, PDU_PROJECTION, null, null, null);
                            PduHeaders headers = new PduHeaders();
                            long msgId = ContentUris.parseId(uri);
                            if (c != null) {
                                try {
                                    if (c.getCount() == 1 && c.moveToFirst()) {
                                        int msgBox = c.getInt(1);
                                        long threadId = c.getLong(2);
                                        Set<Map.Entry<Integer, Integer>> set = ENCODED_STRING_COLUMN_INDEX_MAP.entrySet();
                                        for (Map.Entry<Integer, Integer> e2 : set) {
                                            try {
                                                setEncodedStringValueToHeaders(c, e2.getValue().intValue(), headers, e2.getKey().intValue());
                                            } catch (Throwable th) {
                                                th = th;
                                                if (c != null) {
                                                    c.close();
                                                }
                                                throw th;
                                            }
                                        }
                                        Set<Map.Entry<Integer, Integer>> set2 = TEXT_STRING_COLUMN_INDEX_MAP.entrySet();
                                        for (Map.Entry<Integer, Integer> e3 : set2) {
                                            setTextStringToHeaders(c, e3.getValue().intValue(), headers, e3.getKey().intValue());
                                        }
                                        Set<Map.Entry<Integer, Integer>> set3 = OCTET_COLUMN_INDEX_MAP.entrySet();
                                        for (Map.Entry<Integer, Integer> e4 : set3) {
                                            setOctetToHeaders(c, e4.getValue().intValue(), headers, e4.getKey().intValue());
                                        }
                                        Set<Map.Entry<Integer, Integer>> set4 = LONG_COLUMN_INDEX_MAP.entrySet();
                                        for (Map.Entry<Integer, Integer> e5 : set4) {
                                            setLongToHeaders(c, e5.getValue().intValue(), headers, e5.getKey().intValue());
                                        }
                                        if (c != null) {
                                            c.close();
                                        }
                                        if (msgId == -1) {
                                            throw new MmsException("Error! ID of the message: -1.");
                                        }
                                        String mUriAuthority = uri.getAuthority();
                                        boolean mIsSpam = false;
                                        if (mUriAuthority != null && mUriAuthority.equals("spammms")) {
                                            mIsSpam = true;
                                        }
                                        loadAddress(msgId, headers, mIsSpam);
                                        int msgType = headers.getOctet(140);
                                        PduBody body = new PduBody();
                                        if (msgType != 132 && msgType != 128) {
                                            switch (msgType) {
                                                case 128:
                                                    GenericPdu pdu3 = new SendReq(headers, body);
                                                    pdu = pdu3;
                                                    break;
                                                case 129:
                                                case 137:
                                                case 138:
                                                case 139:
                                                case 140:
                                                case 141:
                                                case 142:
                                                case 143:
                                                case 144:
                                                case 145:
                                                case 146:
                                                case 147:
                                                case 148:
                                                case 149:
                                                case 150:
                                                case 151:
                                                    throw new MmsException("Unsupported PDU type: " + Integer.toHexString(msgType));
                                                case 130:
                                                    GenericPdu pdu4 = new NotificationInd(headers);
                                                    pdu = pdu4;
                                                    break;
                                                case 131:
                                                    GenericPdu pdu5 = new NotifyRespInd(headers);
                                                    pdu = pdu5;
                                                    break;
                                                case 132:
                                                    GenericPdu pdu6 = new RetrieveConf(headers, body);
                                                    pdu = pdu6;
                                                    break;
                                                case 133:
                                                    GenericPdu pdu7 = new AcknowledgeInd(headers);
                                                    pdu = pdu7;
                                                    break;
                                                case 134:
                                                    GenericPdu pdu8 = new DeliveryInd(headers);
                                                    pdu = pdu8;
                                                    break;
                                                case 135:
                                                    GenericPdu pdu9 = new ReadRecInd(headers);
                                                    pdu = pdu9;
                                                    break;
                                                case 136:
                                                    GenericPdu pdu10 = new ReadOrigInd(headers);
                                                    pdu = pdu10;
                                                    break;
                                                default:
                                                    throw new MmsException("Unrecognized PDU type: " + Integer.toHexString(msgType));
                                            }
                                            pduCache = PDU_CACHE_INSTANCE;
                                            synchronized (pduCache) {
                                                pduCache.put(uri, new PduCacheEntry(pdu, msgBox, threadId));
                                                pduCache.setUpdating(uri, false);
                                                pduCache.notifyAll();
                                            }
                                            return pdu;
                                        }
                                        PduPart[] parts = loadParts(msgId, mIsSpam);
                                        if (parts != null) {
                                            for (PduPart pduPart : parts) {
                                                body.addPart(pduPart);
                                            }
                                        }
                                        switch (msgType) {
                                        }
                                        pduCache = PDU_CACHE_INSTANCE;
                                        synchronized (pduCache) {
                                        }
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            }
                            try {
                                throw new MmsException("Bad uri: " + uri);
                            } catch (Throwable th3) {
                                th = th3;
                                if (c != null) {
                                }
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            PduCache pduCache4 = PDU_CACHE_INSTANCE;
                            synchronized (pduCache4) {
                                if (0 != 0) {
                                    pduCache4.put(uri, new PduCacheEntry(null, 0, -1L));
                                }
                                pduCache4.setUpdating(uri, false);
                                pduCache4.notifyAll();
                            }
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                }
            }
        } catch (Throwable th7) {
            th = th7;
        }
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

    public Uri persistPart(PduPart part, long msgId, HashMap<Uri, InputStream> preOpenedFiles) throws MmsException {
        return persistPart(part, msgId, preOpenedFiles, 0, false, false);
    }

    private void persistData(PduPart part, Uri uri, String contentType, HashMap<Uri, InputStream> preOpenedFiles) throws MmsException {
        persistData(part, uri, contentType, preOpenedFiles, false, false);
    }

    private void updateAddress(long msgId, int type, EncodedStringValue[] array) {
        SqliteWrapper.delete(this.mContext, this.mContentResolver, Uri.parse("content://mms/" + msgId + "/addr"), "type=" + type, null);
        if (array != null) {
            persistAddress(msgId, type, array);
        }
    }

    public void updateHeaders(Uri uri, SendReq sendReq) {
        updateHeaders(uri, sendReq, 0);
    }

    private void updatePart(Uri uri, PduPart part, HashMap<Uri, InputStream> preOpenedFiles) throws MmsException {
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

    public void updateParts(Uri uri, PduBody body, HashMap<Uri, InputStream> preOpenedFiles) throws MmsException {
        try {
            PduCache pduCache = PDU_CACHE_INSTANCE;
            synchronized (pduCache) {
                if (pduCache.isUpdating(uri)) {
                    try {
                        pduCache.wait();
                    } catch (InterruptedException e) {
                        Log.m97e(TAG, "updateParts: ", e);
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
                if (partUri != null && !TextUtils.isEmpty(partUri.getAuthority()) && partUri.getAuthority().startsWith("mms")) {
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
            SqliteWrapper.delete(this.mContext, this.mContentResolver, Uri.parse(Telephony.Mms.CONTENT_URI + "/" + msgId + "/part"), filter.length() > 2 ? filter.toString() : null, null);
            Iterator<PduPart> it = toBeCreated.iterator();
            while (it.hasNext()) {
                persistPart(it.next(), msgId, preOpenedFiles);
            }
            for (Map.Entry<Uri, PduPart> e2 : toBeUpdated.entrySet()) {
                updatePart(e2.getKey(), e2.getValue(), preOpenedFiles);
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

    public Uri persist(GenericPdu pdu, Uri uri, boolean createThreadId, boolean groupMmsEnabled, HashMap<Uri, InputStream> preOpenedFiles) throws MmsException {
        return persist(pdu, 0, uri, createThreadId, groupMmsEnabled, preOpenedFiles, false, false);
    }

    private void loadRecipients(int addressType, HashSet<String> recipients, HashMap<Integer, EncodedStringValue[]> addressMap, boolean excludeMyNumber) {
        EncodedStringValue[] array = addressMap.get(Integer.valueOf(addressType));
        if (array == null) {
            return;
        }
        SubscriptionManager subscriptionManager = SubscriptionManager.from(this.mContext);
        Set<String> myPhoneNumbers = new HashSet<>();
        if (excludeMyNumber) {
            for (SubscriptionInfo subInfo : subscriptionManager.getActiveSubscriptionInfoList()) {
                String myNumber = ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class)).createForSubscriptionId(subInfo.getSubscriptionId()).getLine1Number();
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
                        if (!PhoneNumberUtils.compare(number, it.next()) && !recipients.contains(number)) {
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
            throw new MmsException("Bad destination, must be one of content://mms/inbox, content://mms/sent, content://mms/drafts, content://mms/outbox, content://mms/temp.");
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
            Log.m97e(TAG, "ISO_8859_1 must be supported!", e);
            return "";
        }
    }

    public static byte[] getBytes(String data) {
        try {
            return data.getBytes(CharacterSets.MIMENAME_ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            Log.m97e(TAG, "ISO_8859_1 must be supported!", e);
            return new byte[0];
        }
    }

    public void release() {
        Log.m94d(TAG, "pdupersist release");
        Uri uri = Uri.parse(TEMPORARY_DRM_OBJECT_URI);
        SqliteWrapper.delete(this.mContext, this.mContentResolver, uri, null, null);
    }

    public Cursor getPendingMessages(long dueTime) {
        Uri.Builder uriBuilder = Telephony.MmsSms.PendingMessages.CONTENT_URI.buildUpon();
        uriBuilder.appendQueryParameter("protocol", "mms");
        String[] selectionArgs = {String.valueOf(10), String.valueOf(dueTime)};
        return SqliteWrapper.query(this.mContext, this.mContentResolver, uriBuilder.build(), null, "err_type < ? AND due_time <= ?", selectionArgs, Telephony.MmsSms.PendingMessages.DUE_TIME);
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
        PduCache pduCache = PDU_CACHE_INSTANCE;
        synchronized (pduCache) {
            if (pduCache.isUpdating(uri)) {
                try {
                    pduCache.wait();
                } catch (InterruptedException e) {
                    Log.m97e(TAG, "updateHeaders: ", e);
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
            values.put(Telephony.BaseMmsColumns.SUBJECT_CHARSET, Integer.valueOf(subject.getCharacterSet()));
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
                        threadId = Telephony.Threads.semGetOrCreateThreadId(this.mContext, recipients, true, simSlot, twoPhoneServiceUid);
                    } else {
                        threadId = Telephony.Threads.getOrCreateThreadId(this.mContext, recipients, simSlot);
                    }
                } else if (twoPhoneServiceUid > 0) {
                    threadId = Telephony.Threads.semGetOrCreateThreadId(this.mContext, recipients, true, 0, twoPhoneServiceUid);
                } else {
                    threadId = Telephony.Threads.getOrCreateThreadId(this.mContext, recipients);
                }
            } else if (this.mTelephonyManager.getPhoneCount() > 1) {
                threadId = Telephony.Threads.getOrCreateThreadId(this.mContext, recipients, simSlot);
            } else {
                threadId = Telephony.Threads.getOrCreateThreadId(this.mContext, recipients);
            }
            values.put("thread_id", Long.valueOf(threadId));
        }
        if (this.mCscFeature.getBoolean("CscFeature_Common_SupportTwoPhoneService", false) && twoPhoneServiceUid > 0) {
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
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.applyDebugInfo(DebugInfoApplyVisitor.java:68)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoApplyVisitor.visit(DebugInfoApplyVisitor.java:55)
     */
    /* JADX WARN: Not initialized variable reg: 16, insn: 0x0417: MOVE (r11 I:??[OBJECT, ARRAY]) = (r16 I:??[OBJECT, ARRAY] A[D('dataUri' android.net.Uri)]), block:B:272:0x0416 */
    /* JADX WARN: Not initialized variable reg: 16, insn: 0x041d: MOVE (r11 I:??[OBJECT, ARRAY]) = (r16 I:??[OBJECT, ARRAY] A[D('dataUri' android.net.Uri)]), block:B:270:0x041d */
    /* JADX WARN: Not initialized variable reg: 16, insn: 0x0423: MOVE (r11 I:??[OBJECT, ARRAY]) = (r16 I:??[OBJECT, ARRAY] A[D('dataUri' android.net.Uri)]), block:B:268:0x0423 */
    /* JADX WARN: Not initialized variable reg: 17, insn: 0x0419: MOVE (r7 I:??[OBJECT, ARRAY]) = (r17 I:??[OBJECT, ARRAY] A[D('os' java.io.OutputStream)]), block:B:272:0x0416 */
    /* JADX WARN: Not initialized variable reg: 17, insn: 0x041f: MOVE (r7 I:??[OBJECT, ARRAY]) = (r17 I:??[OBJECT, ARRAY] A[D('os' java.io.OutputStream)]), block:B:270:0x041d */
    /* JADX WARN: Not initialized variable reg: 17, insn: 0x0425: MOVE (r7 I:??[OBJECT, ARRAY]) = (r17 I:??[OBJECT, ARRAY] A[D('os' java.io.OutputStream)]), block:B:268:0x0423 */
    private void persistData(com.google.android.mms.pdu.PduPart r25, android.net.Uri r26, java.lang.String r27, java.util.HashMap<android.net.Uri, java.io.InputStream> r28, boolean r29, boolean r30) throws com.google.android.mms.MmsException {
        /*
            Method dump skipped, instructions count: 1226
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.mms.pdu.PduPersister.persistData(com.google.android.mms.pdu.PduPart, android.net.Uri, java.lang.String, java.util.HashMap, boolean, boolean):void");
    }

    public Uri persistPart(PduPart part, long msgId, HashMap<Uri, InputStream> preOpenedFiles, int simSlot, boolean bSpam, boolean hasVendorDrmEngine) throws MmsException {
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
            Cursor c2 = SqliteWrapper.query(this.mContext, this.mContentResolver, Uri.parse("content://spammms/" + msgId + "/spamaddr"), new String[]{"address", Telephony.Mms.Addr.CHARSET, "type"}, null, null, null);
            c = c2;
        } else {
            Cursor c3 = SqliteWrapper.query(this.mContext, this.mContentResolver, Uri.parse("content://mms/" + msgId + "/addr"), new String[]{"address", Telephony.Mms.Addr.CHARSET, "type"}, null, null, null);
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
                                headers.appendEncodedStringValue(new EncodedStringValue(c.getInt(1), getBytes(addr)), addrType);
                                break;
                            case 137:
                                headers.setEncodedStringValue(new EncodedStringValue(c.getInt(1), getBytes(addr)), addrType);
                                break;
                            default:
                                Log.m96e(TAG, "Unknown address type: " + addrType);
                                break;
                        }
                    }
                } finally {
                    c.close();
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:84:0x01ff A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:92:? A[Catch: all -> 0x0268, SYNTHETIC, TryCatch #6 {all -> 0x0268, blocks: (B:12:0x0068, B:15:0x0070, B:16:0x007a, B:18:0x0080, B:20:0x008d, B:21:0x0094, B:23:0x009c, B:24:0x009f, B:26:0x00a7, B:27:0x00aa, B:29:0x00b5, B:30:0x00b8, B:32:0x00c3, B:34:0x00ce, B:35:0x00d1, B:37:0x00de, B:38:0x00e1, B:40:0x00ed, B:41:0x0126, B:43:0x0135, B:45:0x013b, B:47:0x0141, B:49:0x0157, B:51:0x015f, B:54:0x0211, B:57:0x021f, B:58:0x022c, B:60:0x0248, B:85:0x01ff, B:91:0x020a, B:90:0x0206, B:100:0x01a2, B:104:0x01a9, B:127:0x010a, B:129:0x0256, B:130:0x0261), top: B:11:0x0068, inners: #3, #5 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private PduPart[] loadParts(long msgId, boolean bSpam) throws MmsException {
        Cursor c;
        long partId;
        Uri partURI;
        ByteArrayOutputStream baos;
        IOException iOException;
        PduPersister pduPersister = this;
        boolean z = bSpam;
        int i = 1;
        if (z) {
            Cursor c2 = SqliteWrapper.query(pduPersister.mContext, pduPersister.mContentResolver, Uri.parse("content://spammms/" + msgId + "/spampart"), PART_PROJECTION, null, null, null);
            c = c2;
        } else {
            Cursor c3 = SqliteWrapper.query(pduPersister.mContext, pduPersister.mContentResolver, Uri.parse("content://mms/" + msgId + "/part"), PART_PROJECTION, null, null, null);
            c = c3;
        }
        if (c != null) {
            try {
                if (c.getCount() != 0) {
                    int partCount = c.getCount();
                    PduPart[] parts = new PduPart[partCount];
                    int partIdx = 0;
                    while (c.moveToNext()) {
                        PduPart part = new PduPart();
                        Integer charset = pduPersister.getIntegerFromPartColumn(c, i);
                        if (charset != null) {
                            part.setCharset(charset.intValue());
                        }
                        byte[] contentDisposition = pduPersister.getByteArrayFromPartColumn(c, 2);
                        if (contentDisposition != null) {
                            part.setContentDisposition(contentDisposition);
                        }
                        byte[] contentId = pduPersister.getByteArrayFromPartColumn(c, 3);
                        if (contentId != null) {
                            part.setContentId(contentId);
                        }
                        byte[] contentLocation = pduPersister.getByteArrayFromPartColumn(c, 4);
                        if (contentLocation != null) {
                            part.setContentLocation(contentLocation);
                        }
                        byte[] contentType = pduPersister.getByteArrayFromPartColumn(c, 5);
                        if (contentType == null) {
                            throw new MmsException("Content-Type must be set.");
                        }
                        part.setContentType(contentType);
                        byte[] fileName = pduPersister.getByteArrayFromPartColumn(c, 6);
                        if (fileName != null) {
                            part.setFilename(fileName);
                        }
                        byte[] name = pduPersister.getByteArrayFromPartColumn(c, 7);
                        if (name != null) {
                            part.setName(name);
                        }
                        long partId2 = c.getLong(0);
                        if (z) {
                            partId = partId2;
                            partURI = Uri.parse("content://spammms/spampart/" + partId);
                        } else {
                            partId = partId2;
                            partURI = Uri.parse("content://mms/part/" + partId);
                        }
                        part.setDataUri(partURI);
                        String type = toIsoString(contentType);
                        if (!ContentType.isImageType(type) && !ContentType.isAudioType(type) && !ContentType.isVideoType(type)) {
                            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                            InputStream is = null;
                            if ("text/plain".equals(type) || ContentType.APP_SMIL.equals(type)) {
                                baos = baos2;
                            } else if ("text/html".equals(type)) {
                                baos = baos2;
                            } else {
                                try {
                                    InputStream is2 = pduPersister.mContentResolver.openInputStream(partURI);
                                    if (is2 == null) {
                                        throw new MmsException("Failed to load part data, return null.");
                                    }
                                    try {
                                        byte[] buffer = new byte[256];
                                        int len = is2.read(buffer);
                                        while (len >= 0) {
                                            long partId3 = partId;
                                            ByteArrayOutputStream baos3 = baos2;
                                            try {
                                                baos3.write(buffer, 0, len);
                                                len = is2.read(buffer);
                                                baos2 = baos3;
                                                partId = partId3;
                                            } catch (IOException e) {
                                                e = e;
                                                is = is2;
                                                try {
                                                    Log.m97e(TAG, "Failed to load part data", e);
                                                    c.close();
                                                    throw new MmsException(e);
                                                } catch (Throwable e2) {
                                                    iOException = e2;
                                                    if (is == null) {
                                                        throw iOException;
                                                    }
                                                    try {
                                                        is.close();
                                                        throw iOException;
                                                    } catch (IOException e3) {
                                                        Log.m97e(TAG, "Failed to close stream", e3);
                                                        throw iOException;
                                                    }
                                                }
                                            } catch (Throwable th) {
                                                is = is2;
                                                iOException = th;
                                                if (is == null) {
                                                }
                                            }
                                        }
                                        baos = baos2;
                                        if (is2 != null) {
                                            try {
                                                is2.close();
                                            } catch (IOException e4) {
                                                Log.m97e(TAG, "Failed to close stream", e4);
                                            }
                                        }
                                        part.setData(baos.toByteArray());
                                    } catch (IOException e5) {
                                        e = e5;
                                        is = is2;
                                        Log.m97e(TAG, "Failed to load part data", e);
                                        c.close();
                                        throw new MmsException(e);
                                    } catch (Throwable th2) {
                                        is = is2;
                                        iOException = th2;
                                        if (is == null) {
                                        }
                                    }
                                } catch (IOException e6) {
                                    e = e6;
                                } catch (Throwable th3) {
                                    iOException = th3;
                                }
                            }
                            String text = c.getString(8);
                            byte[] blob = new EncodedStringValue(text != null ? text : "").getTextString();
                            baos.write(blob, 0, blob.length);
                            part.setData(baos.toByteArray());
                        }
                        parts[partIdx] = part;
                        pduPersister = this;
                        z = bSpam;
                        partIdx++;
                        i = 1;
                    }
                    if (c != null) {
                        c.close();
                    }
                    return parts;
                }
            } finally {
                if (c != null) {
                    c.close();
                }
            }
        }
    }

    private static boolean isOma13Encoding(String filename) {
        boolean result = false;
        if (filename == null) {
            return false;
        }
        if (filename.length() >= 5 && filename.startsWith(ENCODING_PREFIX) && filename.endsWith(ENCODING_SUFFIX)) {
            result = true;
        }
        Log.m94d(TAG, "pdupersister isOma13Encoding:" + result);
        return result;
    }

    public Cursor getPendingMessages(int simSlot, long dueTime) {
        Uri.Builder uriBuilder = Telephony.MmsSms.PendingMessages.CONTENT_URI.buildUpon();
        uriBuilder.appendQueryParameter("protocol", "mms");
        String[] selectionArgs = {String.valueOf(10), String.valueOf(dueTime), String.valueOf(simSlot)};
        return SqliteWrapper.query(this.mContext, this.mContentResolver, uriBuilder.build(), null, "err_type < ? AND due_time <= ? AND sim_slot2 = ?", selectionArgs, Telephony.MmsSms.PendingMessages.DUE_TIME);
    }

    public Uri persist(GenericPdu pdu, Uri uri, int reqAppId, int reqMsgId) throws MmsException {
        return persist(pdu, 0, uri, reqAppId, reqMsgId, (HashMap<Uri, InputStream>) null);
    }

    public Uri persist(GenericPdu pdu, int simSlot, Uri uri, int reqAppId, int reqMsgId) throws MmsException {
        return persist(pdu, simSlot, uri, reqAppId, reqMsgId, (HashMap<Uri, InputStream>) null);
    }

    public Uri persist(GenericPdu pdu, Uri uri, int reqAppId, int reqMsgId, HashMap<Uri, InputStream> preOpenedFiles) throws MmsException {
        return persist(pdu, 0, uri, reqAppId, reqMsgId, preOpenedFiles);
    }

    public Uri persist(GenericPdu pdu, int simSlot, Uri uri, int reqAppId, int reqMsgId, HashMap<Uri, InputStream> preOpenedFiles) throws MmsException {
        return persist(pdu, simSlot, uri, reqAppId, reqMsgId, preOpenedFiles, 0);
    }

    public Uri persist(GenericPdu pdu, int simSlot, Uri uri, int reqAppId, int reqMsgId, HashMap<Uri, InputStream> preOpenedFiles, int twoPhoneServiceUid) throws MmsException {
        PduBody body;
        int i;
        PduBody body2;
        if (uri == null) {
            throw new MmsException("Uri may not be null.");
        }
        Integer msgBox = MESSAGE_BOX_MAP.get(uri);
        if (msgBox == null) {
            throw new MmsException("Bad destination, must be one of content://mms/inbox, content://mms/sent, content://mms/drafts, content://mms/outbox, content://mms/temp.");
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
        int[] iArr = ADDRESS_FIELDS;
        HashMap<Integer, EncodedStringValue[]> addressMap = new HashMap<>(iArr.length);
        for (int addrType : iArr) {
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
                threadId = twoPhoneServiceUid > 0 ? Telephony.Threads.semGetOrCreateThreadId(this.mContext, recipients, true, 0, twoPhoneServiceUid) : Telephony.Threads.getOrCreateThreadId(this.mContext, recipients);
            }
        }
        values.put("thread_id", Long.valueOf(threadId));
        if (this.mCscFeature.getBoolean("CscFeature_Common_SupportTwoPhoneService", false) && twoPhoneServiceUid > 0) {
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
            values.put(SemShareConstants.SURVEY_CONTENT_APPID, Integer.valueOf(reqAppId));
            values.put("msg_id", Integer.valueOf(reqMsgId));
        }
        Uri res = SqliteWrapper.insert(this.mContext, this.mContentResolver, uri, values);
        if (res == null) {
            throw new MmsException("persist() failed: return null.");
        }
        long msgId = ContentUris.parseId(res);
        ContentValues values2 = new ContentValues(1);
        values2.put(Telephony.Mms.Part.MSG_ID, Long.valueOf(msgId));
        SqliteWrapper.update(this.mContext, this.mContentResolver, Uri.parse("content://mms/" + dummyId + "/part"), values2, null, null);
        Uri res2 = Uri.parse(uri + "/" + msgId);
        int[] iArr2 = ADDRESS_FIELDS;
        int length2 = iArr2.length;
        int i4 = 0;
        while (i4 < length2) {
            int addrType2 = iArr2[i4];
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02fa  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0307  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0339  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x039c  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x03c1  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x03b9  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x036a  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0315  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x02d9  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0219  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0246  */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v42 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Uri persist(GenericPdu genericPdu, int i, Uri uri, boolean z, boolean z2, HashMap<Uri, InputStream> hashMap, boolean z3, boolean z4, int i2) throws MmsException {
        long j;
        long j2;
        ?? r1;
        long orCreateThreadId;
        long j3;
        int i3;
        ContentValues contentValues;
        HashMap<Integer, EncodedStringValue[]> hashMap2;
        int i4;
        Uri insert;
        Uri uri2;
        int length;
        int i5;
        HashMap<Integer, EncodedStringValue[]> hashMap3;
        int i6;
        int i7;
        int[] iArr;
        int i8;
        int[] iArr2;
        if (uri == null) {
            throw new MmsException("Uri may not be null.");
        }
        try {
            j = ContentUris.parseId(uri);
        } catch (NumberFormatException e) {
            j = -1;
        }
        boolean z5 = j != -1;
        if (!z5 && MESSAGE_BOX_MAP.get(uri) == null) {
            throw new MmsException("Bad destination, must be one of content://mms/inbox, content://mms/sent, content://mms/drafts, content://mms/outbox, content://mms/temp.");
        }
        PduCache pduCache = PDU_CACHE_INSTANCE;
        synchronized (pduCache) {
            if (pduCache.isUpdating(uri)) {
                try {
                    pduCache.wait();
                } catch (InterruptedException e2) {
                    Log.m97e(TAG, "persist1: ", e2);
                }
            }
        }
        PDU_CACHE_INSTANCE.purge(uri);
        PduHeaders pduHeaders = genericPdu.getPduHeaders();
        ContentValues contentValues2 = new ContentValues();
        for (Map.Entry<Integer, String> entry : ENCODED_STRING_COLUMN_NAME_MAP.entrySet()) {
            int intValue = entry.getKey().intValue();
            EncodedStringValue encodedStringValue = pduHeaders.getEncodedStringValue(intValue);
            if (encodedStringValue != null) {
                String str = CHARSET_COLUMN_NAME_MAP.get(Integer.valueOf(intValue));
                contentValues2.put(entry.getValue(), toIsoString(encodedStringValue.getTextString()));
                contentValues2.put(str, Integer.valueOf(encodedStringValue.getCharacterSet()));
            }
        }
        for (Map.Entry<Integer, String> entry2 : TEXT_STRING_COLUMN_NAME_MAP.entrySet()) {
            byte[] textString = pduHeaders.getTextString(entry2.getKey().intValue());
            if (textString != null) {
                contentValues2.put(entry2.getValue(), toIsoString(textString));
            }
        }
        for (Map.Entry<Integer, String> entry3 : OCTET_COLUMN_NAME_MAP.entrySet()) {
            int octet = pduHeaders.getOctet(entry3.getKey().intValue());
            if (octet != 0) {
                contentValues2.put(entry3.getValue(), Integer.valueOf(octet));
            }
        }
        for (Map.Entry<Integer, String> entry4 : LONG_COLUMN_NAME_MAP.entrySet()) {
            long longInteger = pduHeaders.getLongInteger(entry4.getKey().intValue());
            if (longInteger != -1) {
                contentValues2.put(entry4.getValue(), Long.valueOf(longInteger));
            }
        }
        int[] iArr3 = ADDRESS_FIELDS;
        HashMap<Integer, EncodedStringValue[]> hashMap4 = new HashMap<>(iArr3.length);
        int length2 = iArr3.length;
        int i9 = 0;
        while (i9 < length2) {
            int i10 = iArr3[i9];
            EncodedStringValue[] encodedStringValueArr = null;
            if (i10 == 137) {
                EncodedStringValue encodedStringValue2 = pduHeaders.getEncodedStringValue(i10);
                if (encodedStringValue2 == null) {
                    i8 = length2;
                    iArr2 = iArr3;
                } else {
                    i8 = length2;
                    iArr2 = iArr3;
                    encodedStringValueArr = new EncodedStringValue[]{encodedStringValue2};
                }
            } else {
                i8 = length2;
                iArr2 = iArr3;
                encodedStringValueArr = pduHeaders.getEncodedStringValues(i10);
            }
            hashMap4.put(Integer.valueOf(i10), encodedStringValueArr);
            i9++;
            length2 = i8;
            iArr3 = iArr2;
        }
        HashSet<String> hashSet = new HashSet<>();
        int messageType = genericPdu.getMessageType();
        this.mTelephonyManager.getLine1Number();
        if (messageType != 130 && messageType != 132 && messageType != 128) {
            j2 = Long.MAX_VALUE;
        } else {
            switch (messageType) {
                case 128:
                    j2 = Long.MAX_VALUE;
                    r1 = 0;
                    loadRecipients(151, hashSet, hashMap4, false);
                    break;
                case 129:
                case 131:
                default:
                    j2 = Long.MAX_VALUE;
                    r1 = 0;
                    break;
                case 130:
                case 132:
                    j2 = Long.MAX_VALUE;
                    r1 = 0;
                    r1 = 0;
                    loadRecipients(137, hashSet, hashMap4, false);
                    if (z2) {
                        loadRecipients(151, hashSet, hashMap4, true);
                        loadRecipients(130, hashSet, hashMap4, true);
                        break;
                    }
                    break;
            }
            if (z && !z3) {
                if (!this.mCscFeature.getBoolean("CscFeature_Common_SupportTwoPhoneService", (boolean) r1)) {
                    orCreateThreadId = Telephony.Threads.getOrCreateThreadId(this.mContext, hashSet);
                } else {
                    orCreateThreadId = i2 > 0 ? Telephony.Threads.semGetOrCreateThreadId(this.mContext, hashSet, true, r1, i2) : Telephony.Threads.getOrCreateThreadId(this.mContext, hashSet);
                }
                if (!z3) {
                    contentValues2.put("thread_id", Long.valueOf(orCreateThreadId));
                }
                if (this.mCscFeature.getBoolean("CscFeature_Common_SupportTwoPhoneService", false) && i2 > 0) {
                    contentValues2.put("using_mode", Integer.valueOf(i2));
                }
                long currentTimeMillis = System.currentTimeMillis();
                int i11 = 1;
                int i12 = 0;
                if (genericPdu instanceof MultimediaMessagePdu) {
                    j3 = currentTimeMillis;
                    i3 = 0;
                    contentValues = contentValues2;
                    hashMap2 = hashMap4;
                    i4 = 1;
                } else {
                    PduBody body = ((MultimediaMessagePdu) genericPdu).getBody();
                    if (body == null) {
                        j3 = currentTimeMillis;
                        i3 = 0;
                        contentValues = contentValues2;
                        hashMap2 = hashMap4;
                        i4 = 1;
                    } else {
                        int partsNum = body.getPartsNum();
                        if (partsNum > 2) {
                            i11 = 0;
                        }
                        int i13 = 0;
                        while (i13 < partsNum) {
                            PduPart part = body.getPart(i13);
                            i12 += part.getDataLength();
                            long j4 = currentTimeMillis;
                            int i14 = partsNum;
                            PduBody pduBody = body;
                            int i15 = messageType;
                            int i16 = i13;
                            ContentValues contentValues3 = contentValues2;
                            HashSet<String> hashSet2 = hashSet;
                            HashMap<Integer, EncodedStringValue[]> hashMap5 = hashMap4;
                            persistPart(part, j4, hashMap, i, z3, z4);
                            String partContentType = getPartContentType(part);
                            if (partContentType != null && !ContentType.APP_SMIL.equals(partContentType) && !"text/plain".equals(partContentType)) {
                                i11 = 0;
                            }
                            i13 = i16 + 1;
                            contentValues2 = contentValues3;
                            hashSet = hashSet2;
                            currentTimeMillis = j4;
                            messageType = i15;
                            body = pduBody;
                            partsNum = i14;
                            hashMap4 = hashMap5;
                        }
                        j3 = currentTimeMillis;
                        contentValues = contentValues2;
                        hashMap2 = hashMap4;
                        i4 = 1;
                        i3 = 0;
                    }
                }
                contentValues.put(Telephony.BaseMmsColumns.TEXT_ONLY, Integer.valueOf(i11));
                if (contentValues.getAsInteger(Telephony.BaseMmsColumns.MESSAGE_SIZE) == null) {
                    contentValues.put(Telephony.BaseMmsColumns.MESSAGE_SIZE, Integer.valueOf(i12));
                }
                if (z5) {
                    insert = SqliteWrapper.insert(this.mContext, this.mContentResolver, uri, contentValues);
                    if (insert == null) {
                        throw new MmsException("persist() failed: return null.");
                    }
                    ContentUris.parseId(insert);
                } else {
                    SqliteWrapper.update(this.mContext, this.mContentResolver, uri, contentValues, null, null);
                    insert = uri;
                }
                long parseId = ContentUris.parseId(insert);
                ContentValues contentValues4 = new ContentValues(i4);
                contentValues4.put(Telephony.Mms.Part.MSG_ID, Long.valueOf(parseId));
                if (z3 != i4) {
                    SqliteWrapper.update(this.mContext, this.mContentResolver, Uri.parse("content://spammms/" + j3 + "/spampart"), contentValues4, null, null);
                } else {
                    SqliteWrapper.update(this.mContext, this.mContentResolver, Uri.parse("content://mms/" + j3 + "/part"), contentValues4, null, null);
                }
                if (!z5) {
                    uri2 = insert;
                } else {
                    uri2 = Uri.parse(uri + "/" + parseId);
                }
                int[] iArr4 = ADDRESS_FIELDS;
                length = iArr4.length;
                i5 = i3;
                while (i5 < length) {
                    int i17 = iArr4[i5];
                    HashMap<Integer, EncodedStringValue[]> hashMap6 = hashMap2;
                    EncodedStringValue[] encodedStringValueArr2 = hashMap6.get(Integer.valueOf(i17));
                    if (encodedStringValueArr2 == null) {
                        hashMap3 = hashMap6;
                        i6 = i5;
                        i7 = length;
                        iArr = iArr4;
                    } else {
                        hashMap3 = hashMap6;
                        i6 = i5;
                        i7 = length;
                        iArr = iArr4;
                        persistAddress(parseId, i17, encodedStringValueArr2, z3);
                    }
                    i5 = i6 + 1;
                    hashMap2 = hashMap3;
                    length = i7;
                    iArr4 = iArr;
                }
                return uri2;
            }
        }
        orCreateThreadId = j2;
        if (!z3) {
        }
        if (this.mCscFeature.getBoolean("CscFeature_Common_SupportTwoPhoneService", false)) {
            contentValues2.put("using_mode", Integer.valueOf(i2));
        }
        long currentTimeMillis2 = System.currentTimeMillis();
        int i112 = 1;
        int i122 = 0;
        if (genericPdu instanceof MultimediaMessagePdu) {
        }
        contentValues.put(Telephony.BaseMmsColumns.TEXT_ONLY, Integer.valueOf(i112));
        if (contentValues.getAsInteger(Telephony.BaseMmsColumns.MESSAGE_SIZE) == null) {
        }
        if (z5) {
        }
        long parseId2 = ContentUris.parseId(insert);
        ContentValues contentValues42 = new ContentValues(i4);
        contentValues42.put(Telephony.Mms.Part.MSG_ID, Long.valueOf(parseId2));
        if (z3 != i4) {
        }
        if (!z5) {
        }
        int[] iArr42 = ADDRESS_FIELDS;
        length = iArr42.length;
        i5 = i3;
        while (i5 < length) {
        }
        return uri2;
    }

    public Uri persist(GenericPdu pdu, Uri uri, boolean createThreadId, boolean groupMmsEnabled, HashMap<Uri, InputStream> preOpenedFiles, boolean bSpam) throws MmsException {
        return persist(pdu, 0, uri, createThreadId, groupMmsEnabled, preOpenedFiles, bSpam, true);
    }

    public Uri persist(GenericPdu pdu, int simSlot, Uri uri, boolean createThreadId, boolean groupMmsEnabled, HashMap<Uri, InputStream> preOpenedFiles) throws MmsException {
        return persist(pdu, simSlot, uri, createThreadId, groupMmsEnabled, preOpenedFiles, false, false);
    }

    public Uri persist(GenericPdu pdu, int simSlot, Uri uri) throws MmsException {
        return persist(pdu, simSlot, uri, true, false, null, false, true);
    }

    public Uri persist(GenericPdu pdu, int simSlot, Uri uri, boolean bSpam) throws MmsException {
        return persist(pdu, simSlot, uri, true, false, null, bSpam, true);
    }

    public Uri persist(GenericPdu pdu, Uri uri, boolean createThreadId, boolean groupMmsEnabled, HashMap<Uri, InputStream> preOpenedFiles, boolean bSpam, boolean hasVendorDrmEngine) throws MmsException {
        return persist(pdu, 0, uri, createThreadId, groupMmsEnabled, preOpenedFiles, bSpam, hasVendorDrmEngine);
    }

    public Uri persist(GenericPdu pdu, int simSlot, Uri uri, boolean createThreadId, boolean groupMmsEnabled, HashMap<Uri, InputStream> preOpenedFiles, boolean bSpam, boolean hasVendorDrmEngine) throws MmsException {
        return persist(pdu, simSlot, uri, createThreadId, groupMmsEnabled, preOpenedFiles, bSpam, hasVendorDrmEngine, 0);
    }

    private boolean isSupportOMA13NameEncoding(int simSlot) {
        final String matchedCode;
        if (simSlot == 0) {
            matchedCode = SystemProperties.get("mdc.matched_code", SystemProperties.get("ro.csc.sales_code", ""));
        } else {
            matchedCode = SystemProperties.get("mdc.matched_code2", SystemProperties.get("ro.csc.sales_code", ""));
        }
        if (TextUtils.isEmpty(matchedCode)) {
            return false;
        }
        String[] supportCode = {"CHC", "CHM", "CHN", "KTC", "LUC", "SKC", "KOO", "K06", "K01"};
        return Arrays.stream(supportCode).anyMatch(new Predicate() { // from class: com.google.android.mms.pdu.PduPersister$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean equals;
                equals = ((String) obj).equals(matchedCode);
                return equals;
            }
        });
    }
}
