package com.android.server.notification;

import android.app.Person;
import android.content.ContentProvider;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.LruCache;
import android.util.Slog;

import com.android.internal.os.BackgroundThread;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.clipboard.ClipboardService;

import libcore.util.EmptyArray;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class ValidateNotificationPeople implements NotificationSignalExtractor {
    public Context mBaseContext;
    public boolean mEnabled;
    public int mEvictionCount;
    public Handler mHandler;
    public AnonymousClass1 mObserver;
    public LruCache mPeopleCache;
    public NotificationUsageStats mUsageStats;
    public Map mUserToContextMap;
    public ZenModeHelper mZenModeHelper;
    public static final boolean VERBOSE = Log.isLoggable("ValidateNoPeople", 2);
    public static final boolean DEBUG = Log.isLoggable("ValidateNoPeople", 3);
    public static final String[] LOOKUP_PROJECTION = {
        KnoxCustomManagerService.ID, "lookup", "starred", "has_phone_number"
    };
    static final String[] PHONE_LOOKUP_PROJECTION = {"data4", "data1"};

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class LookupResult {
        public float mAffinity = FullScreenMagnificationGestureHandler.MAX_SCALE;
        public boolean mHasPhone = false;
        public String mPhoneLookupKey = null;
        public final ArraySet mPhoneNumbers = new ArraySet();
        public final long mExpireMillis =
                System.currentTimeMillis() + ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;

        public final float getAffinity() {
            return (this.mAffinity == FullScreenMagnificationGestureHandler.MAX_SCALE
                            || isExpired())
                    ? FullScreenMagnificationGestureHandler.MAX_SCALE
                    : this.mAffinity;
        }

        public boolean isExpired() {
            return this.mExpireMillis < System.currentTimeMillis();
        }

        public final void mergeContact(Cursor cursor) {
            this.mAffinity = Math.max(this.mAffinity, 0.5f);
            int columnIndex = cursor.getColumnIndex(KnoxCustomManagerService.ID);
            if (columnIndex >= 0) {
                int i = cursor.getInt(columnIndex);
                if (ValidateNotificationPeople.DEBUG) {
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(
                            i, "contact _ID is: ", "ValidateNoPeople");
                }
            } else {
                Slog.i("ValidateNoPeople", "invalid cursor: no _ID");
            }
            int columnIndex2 = cursor.getColumnIndex("lookup");
            if (columnIndex2 >= 0) {
                this.mPhoneLookupKey = cursor.getString(columnIndex2);
                if (ValidateNotificationPeople.DEBUG) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            new StringBuilder("contact LOOKUP_KEY is: "),
                            this.mPhoneLookupKey,
                            "ValidateNoPeople");
                }
            } else if (ValidateNotificationPeople.DEBUG) {
                Slog.d("ValidateNoPeople", "invalid cursor: no LOOKUP_KEY");
            }
            int columnIndex3 = cursor.getColumnIndex("starred");
            if (columnIndex3 >= 0) {
                boolean z = cursor.getInt(columnIndex3) != 0;
                if (z) {
                    this.mAffinity = Math.max(this.mAffinity, 1.0f);
                }
                if (ValidateNotificationPeople.DEBUG) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            "contact STARRED is: ", "ValidateNoPeople", z);
                }
            } else if (ValidateNotificationPeople.DEBUG) {
                Slog.d("ValidateNoPeople", "invalid cursor: no STARRED");
            }
            int columnIndex4 = cursor.getColumnIndex("has_phone_number");
            if (columnIndex4 < 0) {
                if (ValidateNotificationPeople.DEBUG) {
                    Slog.d("ValidateNoPeople", "invalid cursor: no HAS_PHONE_NUMBER");
                }
            } else {
                this.mHasPhone = cursor.getInt(columnIndex4) != 0;
                if (ValidateNotificationPeople.DEBUG) {
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(
                            "ValidateNoPeople",
                            new StringBuilder("contact HAS_PHONE_NUMBER is: "),
                            this.mHasPhone);
                }
            }
        }

        public final void mergePhoneNumber(Cursor cursor) {
            int columnIndex = cursor.getColumnIndex("data4");
            if (columnIndex >= 0) {
                this.mPhoneNumbers.add(cursor.getString(columnIndex));
            } else if (ValidateNotificationPeople.DEBUG) {
                Slog.d("ValidateNoPeople", "cursor data not found: no NORMALIZED_NUMBER");
            }
            int columnIndex2 = cursor.getColumnIndex("data1");
            if (columnIndex2 >= 0) {
                this.mPhoneNumbers.add(cursor.getString(columnIndex2));
            } else if (ValidateNotificationPeople.DEBUG) {
                Slog.d("ValidateNoPeople", "cursor data not found: no NUMBER");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class PeopleRankingReconsideration extends RankingReconsideration {
        public float mContactAffinity;
        public final Context mContext;
        public final LinkedList mPendingLookups;
        public ArraySet mPhoneNumbers;
        public NotificationRecord mRecord;

        public PeopleRankingReconsideration(Context context, String str, LinkedList linkedList) {
            super(0L, str);
            this.mContactAffinity = FullScreenMagnificationGestureHandler.MAX_SCALE;
            this.mPhoneNumbers = null;
            this.mContext = context;
            this.mPendingLookups = linkedList;
        }

        public static void addContacts(LookupResult lookupResult, Context context, Uri uri) {
            try {
                Cursor query =
                        context.getContentResolver()
                                .query(
                                        uri,
                                        ValidateNotificationPeople.LOOKUP_PROJECTION,
                                        null,
                                        null,
                                        null);
                try {
                    if (query != null) {
                        while (query.moveToNext()) {
                            lookupResult.mergeContact(query);
                        }
                        query.close();
                    } else {
                        Slog.w("ValidateNoPeople", "Null cursor from contacts query.");
                        if (query != null) {
                            query.close();
                        }
                    }
                } finally {
                }
            } catch (Throwable th) {
                Slog.w(
                        "ValidateNoPeople",
                        "Problem getting content resolver or performing contacts query.",
                        th);
            }
        }

        public static LookupResult searchContacts(Context context, Uri uri) {
            int i;
            LookupResult lookupResult = new LookupResult();
            Uri createCorpLookupUriFromEnterpriseLookupUri =
                    ContactsContract.Contacts.createCorpLookupUriFromEnterpriseLookupUri(uri);
            if (createCorpLookupUriFromEnterpriseLookupUri == null) {
                addContacts(lookupResult, context, uri);
            } else {
                UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
                int[] profileIds = userManager.getProfileIds(context.getUserId(), true);
                int length = profileIds.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        i = -1;
                        break;
                    }
                    i = profileIds[i2];
                    if (userManager.isManagedProfile(i)) {
                        break;
                    }
                    i2++;
                }
                if (i == -1) {
                    Slog.w(
                            "ValidateNoPeople",
                            "Work profile user ID not found for work contact: "
                                    + createCorpLookupUriFromEnterpriseLookupUri);
                } else {
                    addContacts(
                            lookupResult,
                            context,
                            ContentProvider.maybeAddUserId(
                                    createCorpLookupUriFromEnterpriseLookupUri, i));
                }
            }
            return lookupResult;
        }

        public static LookupResult searchContactsAndLookupNumbers(Context context, Uri uri) {
            LookupResult searchContacts = searchContacts(context, uri);
            String str = !searchContacts.mHasPhone ? null : searchContacts.mPhoneLookupKey;
            if (str != null) {
                try {
                    Cursor query =
                            context.getContentResolver()
                                    .query(
                                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                            ValidateNotificationPeople.PHONE_LOOKUP_PROJECTION,
                                            "lookup = ?",
                                            new String[] {str},
                                            null);
                    try {
                        if (query == null) {
                            Slog.w(
                                    "ValidateNoPeople",
                                    "Cursor is null when querying contact phone number.");
                            if (query != null) {
                                query.close();
                            }
                            return searchContacts;
                        }
                        while (query.moveToNext()) {
                            searchContacts.mergePhoneNumber(query);
                        }
                        query.close();
                    } finally {
                    }
                } catch (Throwable th) {
                    Slog.w(
                            "ValidateNoPeople",
                            "Problem getting content resolver or querying phone numbers.",
                            th);
                }
            }
            return searchContacts;
        }

        @Override // com.android.server.notification.RankingReconsideration
        public final void applyChangesLocked(NotificationRecord notificationRecord) {
            notificationRecord.mContactAffinity =
                    Math.max(this.mContactAffinity, notificationRecord.mContactAffinity);
            if (ValidateNotificationPeople.VERBOSE) {
                Slog.i(
                        "ValidateNoPeople",
                        "final affinity: " + notificationRecord.mContactAffinity);
            }
            ArraySet arraySet = this.mPhoneNumbers;
            if (arraySet == null || arraySet.size() == 0) {
                return;
            }
            if (notificationRecord.mPhoneNumbers == null) {
                notificationRecord.mPhoneNumbers = new ArraySet();
            }
            notificationRecord.mPhoneNumbers.addAll(arraySet);
        }

        @Override // com.android.server.notification.RankingReconsideration
        public final void work() {
            LookupResult lookupResult;
            if (ValidateNotificationPeople.VERBOSE) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Executing: validation for: "),
                        this.mKey,
                        "ValidateNoPeople");
            }
            long currentTimeMillis = System.currentTimeMillis();
            Iterator it = this.mPendingLookups.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String str = (String) it.next();
                String cacheKey =
                        ValidateNotificationPeople.getCacheKey(this.mContext.getUserId(), str);
                synchronized (ValidateNotificationPeople.this.mPeopleCache) {
                    try {
                        lookupResult =
                                (LookupResult)
                                        ValidateNotificationPeople.this.mPeopleCache.get(cacheKey);
                        if (lookupResult == null || lookupResult.isExpired()) {
                            r4 = false;
                        }
                    } finally {
                    }
                }
                if (!r4) {
                    Uri parse = Uri.parse(str);
                    if ("tel".equals(parse.getScheme())) {
                        if (ValidateNotificationPeople.DEBUG) {
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    "checking telephone URI: ", str, "ValidateNoPeople");
                        }
                        lookupResult =
                                searchContacts(
                                        this.mContext,
                                        Uri.withAppendedPath(
                                                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                                                Uri.encode(parse.getSchemeSpecificPart())));
                    } else if ("mailto".equals(parse.getScheme())) {
                        if (ValidateNotificationPeople.DEBUG) {
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    "checking mailto URI: ", str, "ValidateNoPeople");
                        }
                        lookupResult =
                                searchContacts(
                                        this.mContext,
                                        Uri.withAppendedPath(
                                                ContactsContract.CommonDataKinds.Email
                                                        .CONTENT_LOOKUP_URI,
                                                Uri.encode(parse.getSchemeSpecificPart())));
                    } else if (str.startsWith(
                            ContactsContract.Contacts.CONTENT_LOOKUP_URI.toString())) {
                        if (ValidateNotificationPeople.DEBUG) {
                            Slog.d("ValidateNoPeople", "checking lookup URI: ".concat(str));
                        }
                        lookupResult = searchContactsAndLookupNumbers(this.mContext, parse);
                    } else {
                        lookupResult = new LookupResult();
                        if (!"name".equals(parse.getScheme())) {
                            Slog.w("ValidateNoPeople", "unsupported URI ".concat(str));
                        }
                    }
                }
                if (lookupResult != null) {
                    if (!r4) {
                        synchronized (ValidateNotificationPeople.this.mPeopleCache) {
                            ValidateNotificationPeople.this.mPeopleCache.put(
                                    cacheKey, lookupResult);
                        }
                    }
                    if (ValidateNotificationPeople.DEBUG) {
                        Slog.d(
                                "ValidateNoPeople",
                                "lookup contactAffinity is " + lookupResult.getAffinity());
                    }
                    this.mContactAffinity =
                            Math.max(this.mContactAffinity, lookupResult.getAffinity());
                    if (lookupResult.mPhoneNumbers != null) {
                        if (this.mPhoneNumbers == null) {
                            this.mPhoneNumbers = new ArraySet();
                        }
                        this.mPhoneNumbers.addAll(lookupResult.mPhoneNumbers);
                    }
                } else if (ValidateNotificationPeople.DEBUG) {
                    Slog.d("ValidateNoPeople", "lookupResult is null");
                }
            }
            if (ValidateNotificationPeople.DEBUG) {
                Slog.d(
                        "ValidateNoPeople",
                        "Validation finished in "
                                + (System.currentTimeMillis() - currentTimeMillis)
                                + "ms");
            }
            NotificationRecord notificationRecord = this.mRecord;
            if (notificationRecord != null) {
                NotificationUsageStats notificationUsageStats =
                        ValidateNotificationPeople.this.mUsageStats;
                float f = this.mContactAffinity;
                notificationUsageStats.registerPeopleAffinity(
                        notificationRecord,
                        f > FullScreenMagnificationGestureHandler.MAX_SCALE,
                        f == 1.0f,
                        false);
            }
        }
    }

    public static String getCacheKey(int i, String str) {
        return Integer.toString(i) + ":" + str;
    }

    public static String[] getExtraPeople(Bundle bundle) {
        String[] extraPeopleForKey = getExtraPeopleForKey(bundle, "android.people.list");
        String[] extraPeopleForKey2 = getExtraPeopleForKey(bundle, "android.people");
        if (extraPeopleForKey2 == null) {
            return extraPeopleForKey;
        }
        if (extraPeopleForKey == null) {
            return extraPeopleForKey2;
        }
        ArraySet arraySet = new ArraySet(extraPeopleForKey2.length + extraPeopleForKey.length);
        for (String str : extraPeopleForKey2) {
            arraySet.add(str);
        }
        for (String str2 : extraPeopleForKey) {
            arraySet.add(str2);
        }
        return (String[]) arraySet.toArray(EmptyArray.STRING);
    }

    public static String[] getExtraPeopleForKey(Bundle bundle, String str) {
        Object obj = bundle.get(str);
        if (obj instanceof String[]) {
            return (String[]) obj;
        }
        String[] strArr = null;
        int i = 0;
        if (!(obj instanceof ArrayList)) {
            if (obj instanceof String) {
                return new String[] {(String) obj};
            }
            if (obj instanceof char[]) {
                return new String[] {new String((char[]) obj)};
            }
            if (obj instanceof CharSequence) {
                return new String[] {((CharSequence) obj).toString()};
            }
            if (obj instanceof CharSequence[]) {
                CharSequence[] charSequenceArr = (CharSequence[]) obj;
                int length = charSequenceArr.length;
                strArr = new String[length];
                while (i < length) {
                    strArr[i] = charSequenceArr[i].toString();
                    i++;
                }
            }
            return strArr;
        }
        ArrayList arrayList = (ArrayList) obj;
        if (arrayList.isEmpty()) {
            return null;
        }
        if (arrayList.get(0) instanceof String) {
            return (String[]) arrayList.toArray(new String[arrayList.size()]);
        }
        if (arrayList.get(0) instanceof CharSequence) {
            int size = arrayList.size();
            String[] strArr2 = new String[size];
            while (i < size) {
                strArr2[i] = ((CharSequence) arrayList.get(i)).toString();
                i++;
            }
            return strArr2;
        }
        if (arrayList.get(0) instanceof Person) {
            int size2 = arrayList.size();
            strArr = new String[size2];
            while (i < size2) {
                strArr[i] = ((Person) arrayList.get(i)).resolveToLegacyUri();
                i++;
            }
        }
        return strArr;
    }

    public final Context getContextAsUser(UserHandle userHandle) {
        Context context =
                (Context) this.mUserToContextMap.get(Integer.valueOf(userHandle.getIdentifier()));
        if (context != null) {
            return context;
        }
        try {
            context = this.mBaseContext.createPackageContextAsUser("android", 0, userHandle);
            this.mUserToContextMap.put(Integer.valueOf(userHandle.getIdentifier()), context);
            return context;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("ValidateNoPeople", "failed to create package context for lookups", e);
            return context;
        }
    }

    public void initForTests(
            Context context, NotificationUsageStats notificationUsageStats, LruCache lruCache) {
        this.mUserToContextMap = new ArrayMap();
        this.mBaseContext = context;
        this.mUsageStats = notificationUsageStats;
        this.mPeopleCache = lruCache;
        this.mEnabled = true;
    }

    /* JADX WARN: Type inference failed for: r4v8, types: [com.android.server.notification.ValidateNotificationPeople$1] */
    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void initialize(
            final Context context, NotificationUsageStats notificationUsageStats) {
        if (DEBUG) {
            Slog.d("ValidateNoPeople", "Initializing  " + getClass().getSimpleName() + ".");
        }
        this.mUserToContextMap = new ArrayMap();
        this.mBaseContext = context;
        this.mUsageStats = notificationUsageStats;
        this.mPeopleCache = new LruCache(200);
        boolean z =
                1
                        == Settings.Global.getInt(
                                this.mBaseContext.getContentResolver(),
                                "validate_notification_people_enabled",
                                1);
        this.mEnabled = z;
        if (z) {
            this.mHandler = new Handler();
            this.mObserver =
                    new ContentObserver(this.mHandler) { // from class:
                        // com.android.server.notification.ValidateNotificationPeople.1
                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z2, Uri uri, final int i) {
                            super.onChange(z2, uri, i);
                            if ((ValidateNotificationPeople.DEBUG
                                            || ValidateNotificationPeople.this.mEvictionCount % 100
                                                    == 0)
                                    && ValidateNotificationPeople.VERBOSE) {
                                SystemServiceManager$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("mEvictionCount: "),
                                        ValidateNotificationPeople.this.mEvictionCount,
                                        "ValidateNoPeople");
                            }
                            ValidateNotificationPeople.this.mPeopleCache.evictAll();
                            final ValidateNotificationPeople validateNotificationPeople =
                                    ValidateNotificationPeople.this;
                            validateNotificationPeople.mEvictionCount++;
                            final Context context2 = context;
                            final int callingUid = Binder.getCallingUid();
                            validateNotificationPeople.getClass();
                            BackgroundThread.getHandler()
                                    .post(
                                            new Runnable() { // from class:
                                                // com.android.server.notification.ValidateNotificationPeople$$ExternalSyntheticLambda1
                                                /* JADX WARN: Removed duplicated region for block: B:27:0x00cf A[SYNTHETIC] */
                                                /* JADX WARN: Removed duplicated region for block: B:31:0x0057 A[SYNTHETIC] */
                                                @Override // java.lang.Runnable
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                                */
                                                public final void run() {
                                                    /*
                                                        Method dump skipped, instructions count: 272
                                                        To view this dump change 'Code comments level' option to 'DEBUG'
                                                    */
                                                    throw new UnsupportedOperationException(
                                                            "Method not decompiled:"
                                                                + " com.android.server.notification.ValidateNotificationPeople$$ExternalSyntheticLambda1.run():void");
                                                }
                                            });
                        }
                    };
            this.mBaseContext
                    .getContentResolver()
                    .registerContentObserver(
                            ContactsContract.Contacts.CONTENT_URI, true, this.mObserver, -1);
        }
    }

    public final boolean isInExceptionContacts(UserHandle userHandle, Bundle bundle, List list) {
        String country;
        String[] split;
        String str;
        String[] extraPeople = getExtraPeople(bundle);
        if (extraPeople != null) {
            TelephonyManager telephonyManager =
                    (TelephonyManager) this.mBaseContext.getSystemService("phone");
            if (telephonyManager != null) {
                country = telephonyManager.getNetworkCountryIso();
                if (country == null && (country = telephonyManager.getSimCountryIso()) == null) {
                    country = Locale.getDefault().getCountry();
                }
            } else {
                country = Locale.getDefault().getCountry();
            }
            String str2 = country;
            for (String str3 : extraPeople) {
                if (str3 != null && (split = str3.split(":")) != null && split.length >= 2) {
                    try {
                        str = URLDecoder.decode(split[1], "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        str = null;
                    }
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        String[] split2 = ((String) it.next()).split(";");
                        if (split2.length > 1
                                && PhoneNumberUtils.areSamePhoneNumber(str, split2[1], str2)) {
                            return true;
                        }
                    }
                }
            }
            String l = Long.toString(System.nanoTime());
            float[] fArr = new float[1];
            ArraySet arraySet = new ArraySet();
            Context contextAsUser = getContextAsUser(userHandle);
            if (contextAsUser != null) {
                validatePeople(contextAsUser, l, bundle, null, fArr, arraySet);
            }
            Iterator it2 = arraySet.iterator();
            while (it2.hasNext()) {
                String str4 = (String) it2.next();
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        "isInExceptionContacts phone = ", str4, "ValidateNoPeople");
                if (str4 != null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) != null
                                && PhoneNumberUtils.areSamePhoneNumber(
                                        str4, (String) list.get(i), str2)) {
                            Slog.d("ValidateNoPeople", "isInExceptionContacts return true");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final RankingReconsideration process(NotificationRecord notificationRecord) {
        boolean z = this.mEnabled;
        boolean z2 = VERBOSE;
        if (!z) {
            if (z2) {
                Slog.i("ValidateNoPeople", "disabled");
            }
            return null;
        }
        if (notificationRecord.sbn.getNotification() == null) {
            if (z2) {
                Slog.i("ValidateNoPeople", "skipping empty notification");
            }
            return null;
        }
        if (notificationRecord.sbn.getUserId() == -1) {
            if (z2) {
                Slog.i("ValidateNoPeople", "skipping global notification");
            }
            return null;
        }
        Context contextAsUser = getContextAsUser(notificationRecord.sbn.getUser());
        if (contextAsUser != null) {
            return validatePeople(contextAsUser, notificationRecord);
        }
        if (z2) {
            Slog.i("ValidateNoPeople", "skipping notification that lacks a context");
        }
        return null;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setConfig(RankingConfig rankingConfig) {}

    @Override // com.android.server.notification.NotificationSignalExtractor
    public final void setZenHelper(ZenModeHelper zenModeHelper) {
        this.mZenModeHelper = zenModeHelper;
    }

    public RankingReconsideration validatePeople(
            Context context, NotificationRecord notificationRecord) {
        String key = notificationRecord.sbn.getKey();
        Bundle bundle = notificationRecord.sbn.getNotification().extras;
        float[] fArr = new float[1];
        ArraySet arraySet = new ArraySet();
        PeopleRankingReconsideration validatePeople =
                validatePeople(
                        context, key, bundle, notificationRecord.mPeopleOverride, fArr, arraySet);
        float f = fArr[0];
        notificationRecord.mContactAffinity = f;
        if (arraySet.size() > 0 && arraySet.size() != 0) {
            if (notificationRecord.mPhoneNumbers == null) {
                notificationRecord.mPhoneNumbers = new ArraySet();
            }
            notificationRecord.mPhoneNumbers.addAll(arraySet);
        }
        if (validatePeople == null) {
            this.mUsageStats.registerPeopleAffinity(
                    notificationRecord,
                    f > FullScreenMagnificationGestureHandler.MAX_SCALE,
                    f == 1.0f,
                    true);
        } else {
            validatePeople.mRecord = notificationRecord;
        }
        return validatePeople;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0081 A[Catch: all -> 0x007a, TryCatch #0 {all -> 0x007a, blocks: (B:20:0x0054, B:22:0x0066, B:25:0x006d, B:27:0x0071, B:29:0x0081, B:31:0x008d, B:33:0x0093, B:34:0x0096, B:35:0x0097, B:52:0x007c), top: B:19:0x0054 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.notification.ValidateNotificationPeople
                    .PeopleRankingReconsideration
            validatePeople(
                    android.content.Context r9,
                    java.lang.String r10,
                    android.os.Bundle r11,
                    java.util.List r12,
                    float[] r13,
                    android.util.ArraySet r14) {
        /*
            r8 = this;
            r0 = 0
            if (r11 != 0) goto L4
            return r0
        L4:
            android.util.ArraySet r1 = new android.util.ArraySet
            r1.<init>(r12)
            java.lang.String[] r11 = getExtraPeople(r11)
            if (r11 == 0) goto L16
            java.util.List r11 = java.util.Arrays.asList(r11)
            r1.addAll(r11)
        L16:
            boolean r11 = com.android.server.notification.ValidateNotificationPeople.VERBOSE
            if (r11 == 0) goto L32
            java.lang.String r11 = "ValidateNoPeople"
            java.lang.String r12 = "Validating: "
            java.lang.String r2 = " for "
            java.lang.StringBuilder r12 = com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0.m(r12, r10, r2)
            int r2 = r9.getUserId()
            r12.append(r2)
            java.lang.String r12 = r12.toString()
            android.util.Slog.i(r11, r12)
        L32:
            java.util.LinkedList r11 = new java.util.LinkedList
            r11.<init>()
            java.util.Iterator r12 = r1.iterator()
            r1 = 0
            r2 = 0
            r3 = r1
        L3e:
            boolean r4 = r12.hasNext()
            if (r4 == 0) goto La0
            java.lang.Object r4 = r12.next()
            java.lang.String r4 = (java.lang.String) r4
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 == 0) goto L51
            goto L3e
        L51:
            android.util.LruCache r5 = r8.mPeopleCache
            monitor-enter(r5)
            int r6 = r9.getUserId()     // Catch: java.lang.Throwable -> L7a
            java.lang.String r6 = getCacheKey(r6, r4)     // Catch: java.lang.Throwable -> L7a
            android.util.LruCache r7 = r8.mPeopleCache     // Catch: java.lang.Throwable -> L7a
            java.lang.Object r6 = r7.get(r6)     // Catch: java.lang.Throwable -> L7a
            com.android.server.notification.ValidateNotificationPeople$LookupResult r6 = (com.android.server.notification.ValidateNotificationPeople.LookupResult) r6     // Catch: java.lang.Throwable -> L7a
            if (r6 == 0) goto L7c
            boolean r7 = r6.isExpired()     // Catch: java.lang.Throwable -> L7a
            if (r7 == 0) goto L6d
            goto L7c
        L6d:
            boolean r4 = com.android.server.notification.ValidateNotificationPeople.DEBUG     // Catch: java.lang.Throwable -> L7a
            if (r4 == 0) goto L7f
            java.lang.String r4 = "ValidateNoPeople"
            java.lang.String r7 = "using cached lookupResult"
            android.util.Slog.d(r4, r7)     // Catch: java.lang.Throwable -> L7a
            goto L7f
        L7a:
            r8 = move-exception
            goto L9e
        L7c:
            r11.add(r4)     // Catch: java.lang.Throwable -> L7a
        L7f:
            if (r6 == 0) goto L96
            float r4 = r6.getAffinity()     // Catch: java.lang.Throwable -> L7a
            float r2 = java.lang.Math.max(r2, r4)     // Catch: java.lang.Throwable -> L7a
            android.util.ArraySet r4 = r6.mPhoneNumbers     // Catch: java.lang.Throwable -> L7a
            if (r4 == 0) goto L96
            int r6 = r4.size()     // Catch: java.lang.Throwable -> L7a
            if (r6 <= 0) goto L96
            r14.addAll(r4)     // Catch: java.lang.Throwable -> L7a
        L96:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L7a
            int r3 = r3 + 1
            r4 = 10
            if (r3 != r4) goto L3e
            goto La0
        L9e:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L7a
            throw r8
        La0:
            r13[r1] = r2
            boolean r12 = r11.isEmpty()
            if (r12 == 0) goto Lc0
            boolean r8 = com.android.server.notification.ValidateNotificationPeople.VERBOSE
            if (r8 == 0) goto Lbf
            java.lang.String r8 = "ValidateNoPeople"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "final affinity: "
            r9.<init>(r10)
            r9.append(r2)
            java.lang.String r9 = r9.toString()
            android.util.Slog.i(r8, r9)
        Lbf:
            return r0
        Lc0:
            boolean r12 = com.android.server.notification.ValidateNotificationPeople.DEBUG
            if (r12 == 0) goto Lcb
            java.lang.String r12 = "ValidateNoPeople"
            java.lang.String r13 = "Pending: future work scheduled for: "
            com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0.m(r13, r10, r12)
        Lcb:
            com.android.server.notification.ValidateNotificationPeople$PeopleRankingReconsideration r12 = new com.android.server.notification.ValidateNotificationPeople$PeopleRankingReconsideration
            r12.<init>(r9, r10, r11)
            return r12
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.ValidateNotificationPeople.validatePeople(android.content.Context,"
                    + " java.lang.String, android.os.Bundle, java.util.List, float[],"
                    + " android.util.ArraySet):com.android.server.notification.ValidateNotificationPeople$PeopleRankingReconsideration");
    }
}
