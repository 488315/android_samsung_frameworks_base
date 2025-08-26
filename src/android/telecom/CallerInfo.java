package android.telecom;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Country;
import android.location.CountryDetector;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.i18n.phonenumbers.NumberParseException;
import com.android.i18n.phonenumbers.PhoneNumberUtil;
import com.android.i18n.phonenumbers.Phonenumber;
import com.android.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;
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
        CallerInfo callerInfoDoSecondaryLookupIfNecessary = doSecondaryLookupIfNecessary(context, str, getCallerInfo(context, Uri.withAppendedPath(ContactsContract.PhoneLookup.ENTERPRISE_CONTENT_FILTER_URI, Uri.encode(str))));
        if (callerInfoDoSecondaryLookupIfNecessary == null) {
            return null;
        }
        if (TextUtils.isEmpty(callerInfoDoSecondaryLookupIfNecessary.phoneNumber)) {
            callerInfoDoSecondaryLookupIfNecessary.phoneNumber = str;
        }
        return callerInfoDoSecondaryLookupIfNecessary;
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
        String string = uri.toString();
        String str = "contact_id";
        if (string.startsWith("content://com.android.contacts/data/phones")) {
            if (z) {
                Log.v(TAG, "'data/phones' URI; using RawContacts.CONTACT_ID", new Object[0]);
            }
        } else if (string.startsWith("content://com.android.contacts/data")) {
            if (z) {
                Log.v(TAG, "'data' URI; using Data.CONTACT_ID", new Object[0]);
            }
        } else if (string.startsWith("content://com.android.contacts/phone_lookup")) {
            if (z) {
                Log.v(TAG, "'phone_lookup' URI; using PhoneLookup._ID", new Object[0]);
            }
            str = "_id";
        } else {
            Log.w(TAG, "Unexpected prefix for contactRef '" + string + "'", new Object[0]);
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

    /* JADX WARN: Removed duplicated region for block: B:18:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00b8 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getGeoDescription(Context context, String str) {
        Phonenumber.PhoneNumber phoneNumber;
        boolean z = VDBG;
        if (z) {
            Log.v(TAG, "getGeoDescription('" + str + "')...", new Object[0]);
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        PhoneNumberOfflineGeocoder phoneNumberOfflineGeocoder = PhoneNumberOfflineGeocoder.getInstance();
        Locale locale = context.getResources().getConfiguration().locale;
        String currentCountryIso = getCurrentCountryIso(context, locale);
        if (z) {
            try {
                Log.v(TAG, "parsing '" + str + "' for countryIso '" + currentCountryIso + "'...", new Object[0]);
            } catch (NumberParseException unused) {
                phoneNumber = null;
                Log.w(TAG, "getGeoDescription: NumberParseException for incoming number '" + Log.pii(str) + "'", new Object[0]);
                if (phoneNumber == null) {
                }
            }
        }
        phoneNumber = phoneNumberUtil.parse(str, currentCountryIso);
        if (z) {
            try {
                Log.v(TAG, "- parsed number: " + phoneNumber, new Object[0]);
            } catch (NumberParseException unused2) {
                Log.w(TAG, "getGeoDescription: NumberParseException for incoming number '" + Log.pii(str) + "'", new Object[0]);
                if (phoneNumber == null) {
                }
            }
        }
        if (phoneNumber == null) {
            return null;
        }
        String descriptionForNumber = phoneNumberOfflineGeocoder.getDescriptionForNumber(phoneNumber, locale);
        if (VDBG) {
            Log.v(TAG, "- got description: '" + descriptionForNumber + "'", new Object[0]);
        }
        return descriptionForNumber;
    }

    private static String getCurrentCountryIso(Context context, Locale locale) {
        String countryIso;
        CountryDetector countryDetector = (CountryDetector) context.getSystemService(Context.COUNTRY_DETECTOR);
        if (countryDetector == null) {
            countryIso = null;
        } else {
            Country countryDetectCountry = countryDetector.detectCountry();
            if (countryDetectCountry == null) {
                Log.e(TAG, (Throwable) new Exception(), "CountryDetector.detectCountry() returned null.", new Object[0]);
                countryIso = null;
            } else {
                countryIso = countryDetectCountry.getCountryIso();
            }
        }
        if (countryIso != null) {
            return countryIso;
        }
        String country = locale.getCountry();
        Log.w(TAG, "No CountryDetector; falling back to countryIso based on locale: " + country, new Object[0]);
        return country;
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
