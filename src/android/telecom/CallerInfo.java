package android.telecom;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.internal.R;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.util.Arrays;
import java.util.Locale;

/* loaded from: classes4.dex */
public class CallerInfo {
    private static final String TAG = "CallerInfo";
    public static final long USER_TYPE_CURRENT = 0;
    public static final long USER_TYPE_WORK = 1;
    private static final boolean VDBG;
    public Drawable cachedPhoto;
    public Bitmap cachedPhotoIcon;
    public String cnapName;
    private Uri contactDisplayPhotoUri;
    public boolean contactExists;
    private long contactIdOrZero;
    public Uri contactRefUri;
    public Uri contactRingtoneUri;
    public Uri customVibrationUri;
    public String geoDescription;
    public boolean isCachedPhotoCurrent;
    public String lookupKey;
    private String name;
    public int namePresentation;
    public boolean needUpdate;
    public String normalizedNumber;
    public String numberLabel;
    public int numberPresentation;
    public int numberType;
    public String phoneLabel;
    private String phoneNumber;
    public int photoResource;
    public ComponentName preferredPhoneAccountComponent;
    public String preferredPhoneAccountId;
    public long rawContactId;
    public String secCallBackground;
    public String secProfileCardDataId;
    public boolean shouldSendToVoicemail;
    private boolean mIsEmergency = false;
    private boolean mIsVoiceMail = false;
    public long userType = 0;

    static {
        VDBG = !Log.SHIP_BUILD && Log.VERBOSE;
    }

    public static CallerInfo getCallerInfo(Context context, Uri uri, Cursor cursor) {
        int columnIndex;
        CallerInfo callerInfo = new CallerInfo();
        callerInfo.photoResource = 0;
        callerInfo.phoneLabel = null;
        callerInfo.numberType = 0;
        callerInfo.numberLabel = null;
        callerInfo.cachedPhoto = null;
        callerInfo.isCachedPhotoCurrent = false;
        callerInfo.contactExists = false;
        callerInfo.userType = 0L;
        boolean z = VDBG;
        if (z) {
            Log.v(TAG, "getCallerInfo() based on cursor...", new Object[0]);
        }
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int columnIndex2 = cursor.getColumnIndex("display_name");
                    if (columnIndex2 != -1) {
                        callerInfo.name = cursor.getString(columnIndex2);
                    }
                    int columnIndex3 = cursor.getColumnIndex("number");
                    if (columnIndex3 != -1) {
                        callerInfo.phoneNumber = cursor.getString(columnIndex3);
                    }
                    int columnIndex4 = cursor.getColumnIndex("normalized_number");
                    if (columnIndex4 != -1) {
                        callerInfo.normalizedNumber = cursor.getString(columnIndex4);
                    }
                    int columnIndex5 = cursor.getColumnIndex("label");
                    if (columnIndex5 != -1 && (columnIndex = cursor.getColumnIndex("type")) != -1) {
                        callerInfo.numberType = cursor.getInt(columnIndex);
                        String string = cursor.getString(columnIndex5);
                        callerInfo.numberLabel = string;
                        callerInfo.phoneLabel = ContactsContract.CommonDataKinds.Phone.getDisplayLabel(context, callerInfo.numberType, string).toString();
                    }
                    int columnIndexForPersonId = getColumnIndexForPersonId(uri, cursor);
                    if (columnIndexForPersonId != -1) {
                        long j = cursor.getLong(columnIndexForPersonId);
                        if (j != 0 && !ContactsContract.Contacts.isEnterpriseContactId(j)) {
                            callerInfo.contactIdOrZero = j;
                            if (z) {
                                Log.v(TAG, "==> got info.contactIdOrZero: " + callerInfo.contactIdOrZero, new Object[0]);
                            }
                        }
                        if (ContactsContract.Contacts.isEnterpriseContactId(j)) {
                            callerInfo.userType = 1L;
                        }
                    } else {
                        Log.w(TAG, "Couldn't find contact_id column for " + uri, new Object[0]);
                    }
                    int columnIndex6 = cursor.getColumnIndex("lookup");
                    if (columnIndex6 != -1) {
                        callerInfo.lookupKey = cursor.getString(columnIndex6);
                    }
                    int columnIndex7 = cursor.getColumnIndex("photo_uri");
                    if (columnIndex7 != -1 && cursor.getString(columnIndex7) != null) {
                        callerInfo.contactDisplayPhotoUri = Uri.parse(cursor.getString(columnIndex7));
                    } else {
                        callerInfo.contactDisplayPhotoUri = null;
                    }
                    int columnIndex8 = cursor.getColumnIndex(ContactsContract.DataColumns.PREFERRED_PHONE_ACCOUNT_COMPONENT_NAME);
                    if (columnIndex8 != -1 && cursor.getString(columnIndex8) != null) {
                        callerInfo.preferredPhoneAccountComponent = ComponentName.unflattenFromString(cursor.getString(columnIndex8));
                    }
                    int columnIndex9 = cursor.getColumnIndex(ContactsContract.DataColumns.PREFERRED_PHONE_ACCOUNT_ID);
                    if (columnIndex9 != -1 && cursor.getString(columnIndex9) != null) {
                        callerInfo.preferredPhoneAccountId = cursor.getString(columnIndex9);
                    }
                    int columnIndex10 = cursor.getColumnIndex("custom_ringtone");
                    if (columnIndex10 != -1 && cursor.getString(columnIndex10) != null) {
                        if (TextUtils.isEmpty(cursor.getString(columnIndex10))) {
                            callerInfo.contactRingtoneUri = Uri.EMPTY;
                        } else {
                            callerInfo.contactRingtoneUri = Uri.parse(cursor.getString(columnIndex10));
                        }
                    } else {
                        callerInfo.contactRingtoneUri = null;
                    }
                    int columnIndex11 = cursor.getColumnIndex("send_to_voicemail");
                    callerInfo.shouldSendToVoicemail = columnIndex11 != -1 && cursor.getInt(columnIndex11) == 1;
                    int columnIndex12 = cursor.getColumnIndex("_id");
                    if (columnIndex12 != -1) {
                        callerInfo.rawContactId = cursor.getLong(columnIndex12);
                    }
                    int columnIndex13 = cursor.getColumnIndex("sec_custom_vibration");
                    if (columnIndex13 != -1 && cursor.getString(columnIndex13) != null) {
                        callerInfo.customVibrationUri = Uri.parse(cursor.getString(columnIndex13));
                    } else {
                        callerInfo.customVibrationUri = null;
                    }
                    int columnIndex14 = cursor.getColumnIndex("sec_call_background");
                    if (columnIndex14 != -1 && cursor.getString(columnIndex14) != null) {
                        callerInfo.secCallBackground = cursor.getString(columnIndex14);
                    } else {
                        callerInfo.secCallBackground = null;
                    }
                    int columnIndex15 = cursor.getColumnIndex("sec_profile_card_data_id");
                    if (columnIndex15 != -1 && cursor.getString(columnIndex15) != null) {
                        callerInfo.secProfileCardDataId = cursor.getString(columnIndex15);
                    } else {
                        callerInfo.secProfileCardDataId = null;
                    }
                    callerInfo.contactExists = true;
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, (Throwable) e, "getCallerInfo is fail. " + e + ", Column names: " + Arrays.toString(cursor.getColumnNames()) + ", length: " + cursor.getColumnCount(), new Object[0]);
                cursor.moveToPosition(-1);
                if (cursor.moveToNext()) {
                    try {
                        cursor.getString(cursor.getColumnCount() - 1);
                        Log.d(TAG, "getCallerInfo - Cursor last index has no problem", new Object[0]);
                    } catch (Exception e2) {
                        Log.e(TAG, (Throwable) e2, "getCallerInfo - Cursor index is invalid. " + e2, new Object[0]);
                    }
                }
            }
        }
        callerInfo.needUpdate = false;
        callerInfo.name = normalize(callerInfo.name);
        callerInfo.contactRefUri = uri;
        return callerInfo;
    }

    public static CallerInfo getCallerInfo(Context context, Uri uri) {
        ContentResolver currentProfileContentResolver = CallerInfoAsyncQuery.getCurrentProfileContentResolver(context);
        if (currentProfileContentResolver == null) {
            return null;
        }
        try {
            return getCallerInfo(context, uri, currentProfileContentResolver.query(uri, null, null, null, null));
        } catch (RuntimeException e) {
            Log.e(TAG, (Throwable) e, "Error getting caller info.", new Object[0]);
            return null;
        }
    }

    public static CallerInfo getCallerInfo(Context context, String str) {
        if (VDBG) {
            Log.v(TAG, "getCallerInfo() based on number...", new Object[0]);
        }
        return getCallerInfo(context, str, SubscriptionManager.getDefaultSubscriptionId());
    }

    public static CallerInfo getCallerInfo(Context context, String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (((TelephonyManager) context.getSystemService(TelephonyManager.class)).isEmergencyNumber(str)) {
            return new CallerInfo().markAsEmergency(context);
        }
        if (PhoneNumberUtils.isVoiceMailNumber(null, i, str)) {
            return new CallerInfo().markAsVoiceMail(context, i);
        }
        CallerInfo doSecondaryLookupIfNecessary = doSecondaryLookupIfNecessary(context, str, getCallerInfo(context, Uri.withAppendedPath(ContactsContract.PhoneLookup.ENTERPRISE_CONTENT_FILTER_URI, Uri.encode(str))));
        if (doSecondaryLookupIfNecessary == null) {
            return null;
        }
        if (TextUtils.isEmpty(doSecondaryLookupIfNecessary.phoneNumber)) {
            doSecondaryLookupIfNecessary.phoneNumber = str;
        }
        return doSecondaryLookupIfNecessary;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    public long getContactId() {
        return this.contactIdOrZero;
    }

    public Uri getContactDisplayPhotoUri() {
        return this.contactDisplayPhotoUri;
    }

    public void SetContactDisplayPhotoUri(Uri uri) {
        this.contactDisplayPhotoUri = uri;
    }

    static CallerInfo doSecondaryLookupIfNecessary(Context context, String str, CallerInfo callerInfo) {
        if (callerInfo == null) {
            return null;
        }
        if (!callerInfo.contactExists && PhoneNumberUtils.isUriNumber(str)) {
            String usernameFromUriNumber = PhoneNumberUtils.getUsernameFromUriNumber(str);
            if (PhoneNumberUtils.isGlobalPhoneNumber(usernameFromUriNumber)) {
                return getCallerInfo(context, Uri.withAppendedPath(ContactsContract.PhoneLookup.ENTERPRISE_CONTENT_FILTER_URI, Uri.encode(usernameFromUriNumber)));
            }
        }
        return callerInfo;
    }

    public boolean isEmergencyNumber() {
        return this.mIsEmergency;
    }

    public boolean isVoiceMailNumber() {
        return this.mIsVoiceMail;
    }

    CallerInfo markAsEmergency(Context context) {
        this.phoneNumber = context.getString(R.string.emergency_call_dialog_number_for_display);
        this.photoResource = R.drawable.picture_emergency;
        this.mIsEmergency = true;
        return this;
    }

    CallerInfo markAsVoiceMail(Context context, int i) {
        this.mIsVoiceMail = true;
        try {
            this.phoneNumber = ((TelephonyManager) context.getSystemService(TelephonyManager.class)).createForSubscriptionId(i).getVoiceMailAlphaTag();
            return this;
        } catch (SecurityException e) {
            Log.e(TAG, (Throwable) e, "Cannot access VoiceMail.", new Object[0]);
            return this;
        }
    }

    private static String normalize(String str) {
        if (str == null || str.length() > 0) {
            return str;
        }
        return null;
    }

    private static int getColumnIndexForPersonId(Uri uri, Cursor cursor) {
        boolean z = VDBG;
        if (z) {
            Log.v(TAG, "- getColumnIndexForPersonId: contactRef URI = '" + uri + "'...", new Object[0]);
        }
        String uri2 = uri.toString();
        String str = "contact_id";
        if (uri2.startsWith("content://com.android.contacts/data/phones")) {
            if (z) {
                Log.v(TAG, "'data/phones' URI; using RawContacts.CONTACT_ID", new Object[0]);
            }
        } else if (uri2.startsWith("content://com.android.contacts/data")) {
            if (z) {
                Log.v(TAG, "'data' URI; using Data.CONTACT_ID", new Object[0]);
            }
        } else if (uri2.startsWith("content://com.android.contacts/phone_lookup")) {
            if (z) {
                Log.v(TAG, "'phone_lookup' URI; using PhoneLookup._ID", new Object[0]);
            }
            str = "_id";
        } else {
            Log.w(TAG, "Unexpected prefix for contactRef '" + uri2 + "'", new Object[0]);
            str = null;
        }
        int columnIndex = str != null ? cursor.getColumnIndex(str) : -1;
        if (z) {
            Log.v(TAG, "==> Using column '" + str + "' (columnIndex = " + columnIndex + ") for person_id lookup...", new Object[0]);
        }
        return columnIndex;
    }

    public void updateGeoDescription(Context context, String str) {
        if (!TextUtils.isEmpty(this.phoneNumber)) {
            str = this.phoneNumber;
        }
        this.geoDescription = getGeoDescription(context, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00b8 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getGeoDescription(android.content.Context r11, java.lang.String r12) {
        /*
            java.lang.String r0 = "- parsed number: "
            java.lang.String r1 = "parsing '"
            boolean r2 = android.telecom.CallerInfo.VDBG
            r3 = 0
            java.lang.String r4 = "CallerInfo"
            if (r2 == 0) goto L24
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "getGeoDescription('"
            r5.<init>(r6)
            r5.append(r12)
            java.lang.String r6 = "')..."
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            java.lang.Object[] r6 = new java.lang.Object[r3]
            android.telecom.Log.v(r4, r5, r6)
        L24:
            boolean r5 = android.text.TextUtils.isEmpty(r12)
            r6 = 0
            if (r5 == 0) goto L2c
            return r6
        L2c:
            com.android.i18n.phonenumbers.PhoneNumberUtil r5 = com.android.i18n.phonenumbers.PhoneNumberUtil.getInstance()
            com.android.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder r7 = com.android.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder.getInstance()
            android.content.res.Resources r8 = r11.getResources()
            android.content.res.Configuration r8 = r8.getConfiguration()
            java.util.Locale r8 = r8.locale
            java.lang.String r11 = getCurrentCountryIso(r11, r8)
            java.lang.String r9 = "'"
            if (r2 == 0) goto L64
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: com.android.i18n.phonenumbers.NumberParseException -> L7c
            r10.<init>(r1)     // Catch: com.android.i18n.phonenumbers.NumberParseException -> L7c
            r10.append(r12)     // Catch: com.android.i18n.phonenumbers.NumberParseException -> L7c
            java.lang.String r1 = "' for countryIso '"
            r10.append(r1)     // Catch: com.android.i18n.phonenumbers.NumberParseException -> L7c
            r10.append(r11)     // Catch: com.android.i18n.phonenumbers.NumberParseException -> L7c
            java.lang.String r1 = "'..."
            r10.append(r1)     // Catch: com.android.i18n.phonenumbers.NumberParseException -> L7c
            java.lang.String r1 = r10.toString()     // Catch: com.android.i18n.phonenumbers.NumberParseException -> L7c
            java.lang.Object[] r10 = new java.lang.Object[r3]     // Catch: com.android.i18n.phonenumbers.NumberParseException -> L7c
            android.telecom.Log.v(r4, r1, r10)     // Catch: com.android.i18n.phonenumbers.NumberParseException -> L7c
        L64:
            com.android.i18n.phonenumbers.Phonenumber$PhoneNumber r11 = r5.parse(r12, r11)     // Catch: com.android.i18n.phonenumbers.NumberParseException -> L7c
            if (r2 == 0) goto L97
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: com.android.i18n.phonenumbers.NumberParseException -> L7d
            r1.<init>(r0)     // Catch: com.android.i18n.phonenumbers.NumberParseException -> L7d
            r1.append(r11)     // Catch: com.android.i18n.phonenumbers.NumberParseException -> L7d
            java.lang.String r0 = r1.toString()     // Catch: com.android.i18n.phonenumbers.NumberParseException -> L7d
            java.lang.Object[] r1 = new java.lang.Object[r3]     // Catch: com.android.i18n.phonenumbers.NumberParseException -> L7d
            android.telecom.Log.v(r4, r0, r1)     // Catch: com.android.i18n.phonenumbers.NumberParseException -> L7d
            goto L97
        L7c:
            r11 = r6
        L7d:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "getGeoDescription: NumberParseException for incoming number '"
            r0.<init>(r1)
            java.lang.String r12 = android.telecom.Log.pii(r12)
            r0.append(r12)
            r0.append(r9)
            java.lang.String r12 = r0.toString()
            java.lang.Object[] r0 = new java.lang.Object[r3]
            android.telecom.Log.w(r4, r12, r0)
        L97:
            if (r11 == 0) goto Lb8
            java.lang.String r11 = r7.getDescriptionForNumber(r11, r8)
            boolean r12 = android.telecom.CallerInfo.VDBG
            if (r12 == 0) goto Lb7
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r0 = "- got description: '"
            r12.<init>(r0)
            r12.append(r11)
            r12.append(r9)
            java.lang.String r12 = r12.toString()
            java.lang.Object[] r0 = new java.lang.Object[r3]
            android.telecom.Log.v(r4, r12, r0)
        Lb7:
            return r11
        Lb8:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telecom.CallerInfo.getGeoDescription(android.content.Context, java.lang.String):java.lang.String");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String getCurrentCountryIso(android.content.Context r4, java.util.Locale r5) {
        /*
            java.lang.String r0 = "country_detector"
            java.lang.Object r4 = r4.getSystemService(r0)
            android.location.CountryDetector r4 = (android.location.CountryDetector) r4
            r0 = 0
            java.lang.String r1 = "CallerInfo"
            if (r4 == 0) goto L24
            android.location.Country r4 = r4.detectCountry()
            if (r4 == 0) goto L18
            java.lang.String r4 = r4.getCountryIso()
            goto L25
        L18:
            java.lang.Exception r4 = new java.lang.Exception
            r4.<init>()
            java.lang.String r2 = "CountryDetector.detectCountry() returned null."
            java.lang.Object[] r3 = new java.lang.Object[r0]
            android.telecom.Log.e(r1, r4, r2, r3)
        L24:
            r4 = 0
        L25:
            if (r4 != 0) goto L3e
            java.lang.String r4 = r5.getCountry()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r2 = "No CountryDetector; falling back to countryIso based on locale: "
            r5.<init>(r2)
            r5.append(r4)
            java.lang.String r5 = r5.toString()
            java.lang.Object[] r0 = new java.lang.Object[r0]
            android.telecom.Log.w(r1, r5, r0)
        L3e:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telecom.CallerInfo.getCurrentCountryIso(android.content.Context, java.util.Locale):java.lang.String");
    }

    protected static String getCurrentCountryIso(Context context) {
        return getCurrentCountryIso(context, Locale.getDefault());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(super.toString() + " { ");
        String str = this.name;
        String str2 = PerfettoProtoLogImpl.NULL_STRING;
        sb.append("name ".concat(str == null ? PerfettoProtoLogImpl.NULL_STRING : "non-null"));
        if (this.phoneNumber != null) {
            str2 = "non-null";
        }
        sb.append(", phoneNumber ".concat(str2));
        sb.append(" }");
        return sb.toString();
    }
}
