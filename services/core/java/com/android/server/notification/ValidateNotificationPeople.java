package com.android.server.notification;

import android.app.NotificationManager;
import android.app.Person;
import android.content.ContentProvider;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.LruCache;
import android.util.Slog;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.clipboard.ClipboardService;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import libcore.util.EmptyArray;

/* loaded from: classes2.dex */
public class ValidateNotificationPeople implements NotificationSignalExtractor {
    public Context mBaseContext;
    public boolean mEnabled;
    public int mEvictionCount;
    public Handler mHandler;
    public ContentObserver mObserver;
    public LruCache mPeopleCache;
    public NotificationUsageStats mUsageStats;
    public Map mUserToContextMap;
    public ZenModeHelper mZenModeHelper;
    public static final boolean VERBOSE = Log.isLoggable("ValidateNoPeople", 2);
    public static final boolean DEBUG = Log.isLoggable("ValidateNoPeople", 3);
    public static final String[] LOOKUP_PROJECTION = {KnoxCustomManagerService.f1773ID, "lookup", "starred", "has_phone_number"};
    static final String[] PHONE_LOOKUP_PROJECTION = {"data4", "data1"};

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void setConfig(RankingConfig rankingConfig) {
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void initialize(final Context context, NotificationUsageStats notificationUsageStats) {
        if (DEBUG) {
            Slog.d("ValidateNoPeople", "Initializing  " + getClass().getSimpleName() + ".");
        }
        this.mUserToContextMap = new ArrayMap();
        this.mBaseContext = context;
        this.mUsageStats = notificationUsageStats;
        this.mPeopleCache = new LruCache(200);
        boolean z = 1 == Settings.Global.getInt(this.mBaseContext.getContentResolver(), "validate_notification_people_enabled", 1);
        this.mEnabled = z;
        if (z) {
            this.mHandler = new Handler();
            this.mObserver = new ContentObserver(this.mHandler) { // from class: com.android.server.notification.ValidateNotificationPeople.1
                @Override // android.database.ContentObserver
                public void onChange(boolean z2, Uri uri, int i) {
                    super.onChange(z2, uri, i);
                    if ((ValidateNotificationPeople.DEBUG || ValidateNotificationPeople.this.mEvictionCount % 100 == 0) && ValidateNotificationPeople.VERBOSE) {
                        Slog.i("ValidateNoPeople", "mEvictionCount: " + ValidateNotificationPeople.this.mEvictionCount);
                    }
                    ValidateNotificationPeople.this.mPeopleCache.evictAll();
                    ValidateNotificationPeople.this.mEvictionCount++;
                    ValidateNotificationPeople.this.updateExceptionContacts(context, uri, i, Binder.getCallingUid(), true);
                }
            };
            this.mBaseContext.getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true, this.mObserver, -1);
        }
    }

    public void initForTests(Context context, NotificationUsageStats notificationUsageStats, LruCache lruCache) {
        this.mUserToContextMap = new ArrayMap();
        this.mBaseContext = context;
        this.mUsageStats = notificationUsageStats;
        this.mPeopleCache = lruCache;
        this.mEnabled = true;
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public RankingReconsideration process(NotificationRecord notificationRecord) {
        if (!this.mEnabled) {
            if (VERBOSE) {
                Slog.i("ValidateNoPeople", "disabled");
            }
            return null;
        }
        if (notificationRecord == null || notificationRecord.getNotification() == null) {
            if (VERBOSE) {
                Slog.i("ValidateNoPeople", "skipping empty notification");
            }
            return null;
        }
        if (notificationRecord.getUserId() == -1) {
            if (VERBOSE) {
                Slog.i("ValidateNoPeople", "skipping global notification");
            }
            return null;
        }
        Context contextAsUser = getContextAsUser(notificationRecord.getUser());
        if (contextAsUser == null) {
            if (VERBOSE) {
                Slog.i("ValidateNoPeople", "skipping notification that lacks a context");
            }
            return null;
        }
        return validatePeople(contextAsUser, notificationRecord);
    }

    @Override // com.android.server.notification.NotificationSignalExtractor
    public void setZenHelper(ZenModeHelper zenModeHelper) {
        this.mZenModeHelper = zenModeHelper;
    }

    public float getContactAffinity(UserHandle userHandle, Bundle bundle, int i, float f) {
        if (DEBUG) {
            Slog.d("ValidateNoPeople", "checking affinity for " + userHandle);
        }
        if (bundle == null) {
            return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        }
        String l = Long.toString(System.nanoTime());
        float[] fArr = new float[1];
        ArraySet arraySet = new ArraySet();
        Context contextAsUser = getContextAsUser(userHandle);
        if (contextAsUser == null) {
            return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        }
        final PeopleRankingReconsideration validatePeople = validatePeople(contextAsUser, l, bundle, null, fArr, arraySet);
        float f2 = fArr[0];
        if (validatePeople == null) {
            return f2;
        }
        final Semaphore semaphore = new Semaphore(0);
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: com.android.server.notification.ValidateNotificationPeople.2
            @Override // java.lang.Runnable
            public void run() {
                validatePeople.work();
                semaphore.release();
            }
        });
        try {
            if (!semaphore.tryAcquire(i, TimeUnit.MILLISECONDS)) {
                Slog.w("ValidateNoPeople", "Timeout while waiting for affinity: " + l + ". Returning timeoutAffinity=" + f);
                return f;
            }
            return Math.max(validatePeople.getContactAffinity(), f2);
        } catch (InterruptedException e) {
            Slog.w("ValidateNoPeople", "InterruptedException while waiting for affinity: " + l + ". Returning affinity=" + f2, e);
            return f2;
        }
    }

    public boolean isInExceptionContacts(UserHandle userHandle, Bundle bundle, List list) {
        String country;
        String[] split;
        String str;
        String[] extraPeople = getExtraPeople(bundle);
        if (extraPeople != null) {
            TelephonyManager telephonyManager = (TelephonyManager) this.mBaseContext.getSystemService("phone");
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
                if (str3 != null && (split = str3.split(XmlUtils.STRING_ARRAY_SEPARATOR)) != null && split.length >= 2) {
                    try {
                        str = URLDecoder.decode(split[1], "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        str = null;
                    }
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        String[] split2 = ((String) it.next()).split(KnoxVpnFirewallHelper.DELIMITER);
                        if (split2.length > 1 && PhoneNumberUtils.areSamePhoneNumber(str, split2[1], str2)) {
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
                Slog.d("ValidateNoPeople", "isInExceptionContacts phone = " + str4);
                if (str4 != null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) != null && PhoneNumberUtils.areSamePhoneNumber(str4, (String) list.get(i), str2)) {
                            Slog.d("ValidateNoPeople", "isInExceptionContacts return true");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public final void updateExceptionContacts(final Context context, Uri uri, final int i, final int i2, final boolean z) {
        BackgroundThread.getHandler().post(new Runnable() { // from class: com.android.server.notification.ValidateNotificationPeople$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ValidateNotificationPeople.this.lambda$updateExceptionContacts$0(i, context, i2, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateExceptionContacts$0(int i, Context context, int i2, boolean z) {
        ZenModeHelper zenModeHelper = this.mZenModeHelper;
        if (zenModeHelper == null) {
            Slog.d("ValidateNoPeople", "skipping - no zen info available");
            return;
        }
        NotificationManager.Policy notificationPolicy = zenModeHelper.getNotificationPolicy();
        if (notificationPolicy == null) {
            return;
        }
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Uri.Builder buildUpon = uri.buildUpon();
        buildUpon.encodedAuthority("" + i + "@" + uri.getEncodedAuthority());
        this.mZenModeHelper.setNotificationPolicy(new NotificationManager.Policy(notificationPolicy.priorityCategories, notificationPolicy.priorityCallSenders, notificationPolicy.priorityMessageSenders, notificationPolicy.suppressedVisualEffects, notificationPolicy.state, notificationPolicy.priorityConversationSenders, getUpdatedExceptionContacts(context, notificationPolicy.getExceptionContacts(), buildUpon.build())), i2, z);
        Slog.d("ValidateNoPeople", "The contacts of policy has been updated.");
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x008b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0015 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List getUpdatedExceptionContacts(Context context, List list, Uri uri) {
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String[] split = ((String) it.next()).split(KnoxVpnFirewallHelper.DELIMITER);
                if (split.length > 1) {
                    try {
                        Cursor query = context.getContentResolver().query(uri, null, "contact_id = " + split[0], null, null);
                        if (query != null) {
                            try {
                                if (query.moveToFirst()) {
                                    String string = query.getString(query.getColumnIndex("data1"));
                                    Slog.d("ValidateNoPeople", "There is a contact has been updated.");
                                    StringBuffer stringBuffer = new StringBuffer();
                                    stringBuffer.append(split[0]);
                                    stringBuffer.append(KnoxVpnFirewallHelper.DELIMITER);
                                    stringBuffer.append(string);
                                    arrayList.add(stringBuffer.toString());
                                    if (query == null) {
                                        query.close();
                                    }
                                }
                            } catch (Throwable th) {
                                if (query != null) {
                                    try {
                                        query.close();
                                    } catch (Throwable th2) {
                                        th.addSuppressed(th2);
                                    }
                                }
                                throw th;
                            }
                        }
                        if (query == null) {
                            Slog.d("ValidateNoPeople", "Failed to query the contact.");
                        } else {
                            Slog.d("ValidateNoPeople", "There is a contact has been deleted.");
                        }
                        if (query == null) {
                        }
                    } catch (Throwable th3) {
                        Slog.w("ValidateNoPeople", "Problem getting content resolver or performing contacts query.", th3);
                    }
                }
            }
        }
        return arrayList;
    }

    public void fixExceptionContacts(final Context context, final List list) {
        BackgroundThread.getHandler().post(new Runnable() { // from class: com.android.server.notification.ValidateNotificationPeople$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ValidateNotificationPeople.this.lambda$fixExceptionContacts$1(context, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v18 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public /* synthetic */ void lambda$fixExceptionContacts$1(Context context, List list) {
        String str;
        Cursor query;
        Throwable th;
        ArrayList arrayList = new ArrayList();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Uri.Builder buildUpon = uri.buildUpon();
        buildUpon.encodedAuthority("" + context.getUserId() + "@" + uri.getEncodedAuthority());
        Uri build = buildUpon.build();
        String[] strArr = {"contact_id", "display_name", "data1"};
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            try {
                str = build;
                try {
                    query = context.getContentResolver().query(str, strArr, null, null, null);
                    try {
                    } catch (Throwable th2) {
                        th = th2;
                        Slog.w(str, "Problem getting content resolver or performing contacts query.", th);
                    }
                } catch (Throwable th3) {
                    th = th3;
                    str = "ValidateNoPeople";
                }
            } catch (Throwable th4) {
                th = th4;
                str = "ValidateNoPeople";
            }
            if (query == null) {
                String str3 = "ValidateNoPeople";
                try {
                    Log.d(str3, "phoneCursor is NULL");
                    if (query != null) {
                        query.close();
                        return;
                    }
                    return;
                } catch (Throwable th5) {
                    th = th5;
                    str = str3;
                }
            } else {
                while (true) {
                    try {
                        if (!query.moveToNext()) {
                            break;
                        }
                        String string = query.getString(query.getColumnIndex("contact_id"));
                        String string2 = query.getString(query.getColumnIndex("data1"));
                        Object[] split = str2.split(KnoxVpnFirewallHelper.DELIMITER);
                        if (string2 != null && string2.equals(split[1])) {
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append(string);
                            stringBuffer.append(KnoxVpnFirewallHelper.DELIMITER);
                            stringBuffer.append(string2);
                            if (!arrayList.contains(stringBuffer.toString())) {
                                arrayList.add(stringBuffer.toString());
                            }
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        str = "ValidateNoPeople";
                    }
                }
                query.close();
            }
            if (query == null) {
                throw th;
            }
            try {
                query.close();
                throw th;
            } catch (Throwable th7) {
                th.addSuppressed(th7);
                throw th;
            }
        }
        ZenModeHelper zenModeHelper = this.mZenModeHelper;
        if (zenModeHelper == null) {
            Slog.d("ValidateNoPeople", "skipping - no zen info available");
            return;
        }
        NotificationManager.Policy notificationPolicy = zenModeHelper.getNotificationPolicy();
        if (notificationPolicy == null) {
            return;
        }
        this.mZenModeHelper.setNotificationPolicy(new NotificationManager.Policy(notificationPolicy.priorityCategories, notificationPolicy.priorityCallSenders, notificationPolicy.priorityMessageSenders, notificationPolicy.suppressedVisualEffects, notificationPolicy.state, notificationPolicy.priorityConversationSenders, arrayList, notificationPolicy.getAppBypassDndList()), Binder.getCallingUid(), true);
    }

    public final Context getContextAsUser(UserHandle userHandle) {
        Context context = (Context) this.mUserToContextMap.get(Integer.valueOf(userHandle.getIdentifier()));
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

    public RankingReconsideration validatePeople(Context context, NotificationRecord notificationRecord) {
        String key = notificationRecord.getKey();
        Bundle bundle = notificationRecord.getNotification().extras;
        float[] fArr = new float[1];
        ArraySet arraySet = new ArraySet();
        PeopleRankingReconsideration validatePeople = validatePeople(context, key, bundle, notificationRecord.getPeopleOverride(), fArr, arraySet);
        float f = fArr[0];
        notificationRecord.setContactAffinity(f);
        if (arraySet.size() > 0) {
            notificationRecord.mergePhoneNumbers(arraySet);
        }
        if (validatePeople == null) {
            this.mUsageStats.registerPeopleAffinity(notificationRecord, f > DisplayPowerController2.RATE_FROM_DOZE_TO_ON, f == 1.0f, true);
        } else {
            validatePeople.setRecord(notificationRecord);
        }
        return validatePeople;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0089 A[Catch: all -> 0x00aa, TryCatch #0 {, blocks: (B:20:0x005e, B:22:0x0070, B:25:0x0077, B:27:0x007b, B:29:0x0089, B:31:0x0093, B:33:0x0099, B:35:0x009f, B:36:0x00a2, B:37:0x00a3, B:54:0x0084), top: B:19:0x005e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final PeopleRankingReconsideration validatePeople(Context context, String str, Bundle bundle, List list, float[] fArr, ArraySet arraySet) {
        ArraySet phoneNumbers;
        if (bundle == null) {
            return null;
        }
        ArraySet<String> arraySet2 = new ArraySet(list);
        String[] extraPeople = getExtraPeople(bundle);
        if (extraPeople != null) {
            arraySet2.addAll(Arrays.asList(extraPeople));
        }
        if (VERBOSE) {
            Slog.i("ValidateNoPeople", "Validating: " + str + " for " + context.getUserId());
        }
        LinkedList linkedList = new LinkedList();
        float f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        int i = 0;
        for (String str2 : arraySet2) {
            if (!TextUtils.isEmpty(str2)) {
                synchronized (this.mPeopleCache) {
                    LookupResult lookupResult = (LookupResult) this.mPeopleCache.get(getCacheKey(context.getUserId(), str2));
                    if (lookupResult != null && !lookupResult.isExpired()) {
                        if (DEBUG) {
                            Slog.d("ValidateNoPeople", "using cached lookupResult");
                        }
                        if (lookupResult != null) {
                            f = Math.max(f, lookupResult.getAffinity());
                            if (arraySet != null && (phoneNumbers = lookupResult.getPhoneNumbers()) != null && phoneNumbers.size() > 0) {
                                arraySet.addAll(phoneNumbers);
                            }
                        }
                    }
                    linkedList.add(str2);
                    if (lookupResult != null) {
                    }
                }
                i++;
                if (i == 10) {
                    break;
                }
            }
        }
        fArr[0] = f;
        if (linkedList.isEmpty()) {
            if (VERBOSE) {
                Slog.i("ValidateNoPeople", "final affinity: " + f);
            }
            return null;
        }
        if (DEBUG) {
            Slog.d("ValidateNoPeople", "Pending: future work scheduled for: " + str);
        }
        return new PeopleRankingReconsideration(context, str, linkedList);
    }

    public static String getCacheKey(int i, String str) {
        return Integer.toString(i) + XmlUtils.STRING_ARRAY_SEPARATOR + str;
    }

    public static String[] getExtraPeople(Bundle bundle) {
        return combineLists(getExtraPeopleForKey(bundle, "android.people"), getExtraPeopleForKey(bundle, "android.people.list"));
    }

    public static String[] combineLists(String[] strArr, String[] strArr2) {
        if (strArr == null) {
            return strArr2;
        }
        if (strArr2 == null) {
            return strArr;
        }
        ArraySet arraySet = new ArraySet(strArr.length + strArr2.length);
        for (String str : strArr) {
            arraySet.add(str);
        }
        for (String str2 : strArr2) {
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
        if (obj instanceof ArrayList) {
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
        if (obj instanceof String) {
            return new String[]{(String) obj};
        }
        if (obj instanceof char[]) {
            return new String[]{new String((char[]) obj)};
        }
        if (obj instanceof CharSequence) {
            return new String[]{((CharSequence) obj).toString()};
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

    public class LookupResult {
        public float mAffinity = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        public boolean mHasPhone = false;
        public String mPhoneLookupKey = null;
        public ArraySet mPhoneNumbers = new ArraySet();
        public final long mExpireMillis = System.currentTimeMillis() + ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;

        public void mergeContact(Cursor cursor) {
            this.mAffinity = Math.max(this.mAffinity, 0.5f);
            int columnIndex = cursor.getColumnIndex(KnoxCustomManagerService.f1773ID);
            if (columnIndex >= 0) {
                int i = cursor.getInt(columnIndex);
                if (ValidateNotificationPeople.DEBUG) {
                    Slog.d("ValidateNoPeople", "contact _ID is: " + i);
                }
            } else {
                Slog.i("ValidateNoPeople", "invalid cursor: no _ID");
            }
            int columnIndex2 = cursor.getColumnIndex("lookup");
            if (columnIndex2 >= 0) {
                this.mPhoneLookupKey = cursor.getString(columnIndex2);
                if (ValidateNotificationPeople.DEBUG) {
                    Slog.d("ValidateNoPeople", "contact LOOKUP_KEY is: " + this.mPhoneLookupKey);
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
                    Slog.d("ValidateNoPeople", "contact STARRED is: " + z);
                }
            } else if (ValidateNotificationPeople.DEBUG) {
                Slog.d("ValidateNoPeople", "invalid cursor: no STARRED");
            }
            int columnIndex4 = cursor.getColumnIndex("has_phone_number");
            if (columnIndex4 >= 0) {
                this.mHasPhone = cursor.getInt(columnIndex4) != 0;
                if (ValidateNotificationPeople.DEBUG) {
                    Slog.d("ValidateNoPeople", "contact HAS_PHONE_NUMBER is: " + this.mHasPhone);
                    return;
                }
                return;
            }
            if (ValidateNotificationPeople.DEBUG) {
                Slog.d("ValidateNoPeople", "invalid cursor: no HAS_PHONE_NUMBER");
            }
        }

        public String getPhoneLookupKey() {
            if (this.mHasPhone) {
                return this.mPhoneLookupKey;
            }
            return null;
        }

        public void mergePhoneNumber(Cursor cursor) {
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

        public ArraySet getPhoneNumbers() {
            return this.mPhoneNumbers;
        }

        public boolean isExpired() {
            return this.mExpireMillis < System.currentTimeMillis();
        }

        public final boolean isInvalid() {
            return this.mAffinity == DisplayPowerController2.RATE_FROM_DOZE_TO_ON || isExpired();
        }

        public float getAffinity() {
            return isInvalid() ? DisplayPowerController2.RATE_FROM_DOZE_TO_ON : this.mAffinity;
        }
    }

    class PeopleRankingReconsideration extends RankingReconsideration {
        public float mContactAffinity;
        public final Context mContext;
        public final LinkedList mPendingLookups;
        public ArraySet mPhoneNumbers;
        public NotificationRecord mRecord;

        public PeopleRankingReconsideration(Context context, String str, LinkedList linkedList) {
            super(str);
            this.mContactAffinity = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
            this.mPhoneNumbers = null;
            this.mContext = context;
            this.mPendingLookups = linkedList;
        }

        @Override // com.android.server.notification.RankingReconsideration
        public void work() {
            LookupResult lookupResult;
            if (ValidateNotificationPeople.VERBOSE) {
                Slog.i("ValidateNoPeople", "Executing: validation for: " + this.mKey);
            }
            long currentTimeMillis = System.currentTimeMillis();
            Iterator it = this.mPendingLookups.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String str = (String) it.next();
                String cacheKey = ValidateNotificationPeople.getCacheKey(this.mContext.getUserId(), str);
                synchronized (ValidateNotificationPeople.this.mPeopleCache) {
                    lookupResult = (LookupResult) ValidateNotificationPeople.this.mPeopleCache.get(cacheKey);
                    if (lookupResult == null || lookupResult.isExpired()) {
                        r4 = false;
                    }
                }
                if (!r4) {
                    Uri parse = Uri.parse(str);
                    if ("tel".equals(parse.getScheme())) {
                        if (ValidateNotificationPeople.DEBUG) {
                            Slog.d("ValidateNoPeople", "checking telephone URI: " + str);
                        }
                        lookupResult = resolvePhoneContact(this.mContext, parse.getSchemeSpecificPart());
                    } else if ("mailto".equals(parse.getScheme())) {
                        if (ValidateNotificationPeople.DEBUG) {
                            Slog.d("ValidateNoPeople", "checking mailto URI: " + str);
                        }
                        lookupResult = resolveEmailContact(this.mContext, parse.getSchemeSpecificPart());
                    } else if (str.startsWith(ContactsContract.Contacts.CONTENT_LOOKUP_URI.toString())) {
                        if (ValidateNotificationPeople.DEBUG) {
                            Slog.d("ValidateNoPeople", "checking lookup URI: " + str);
                        }
                        lookupResult = searchContactsAndLookupNumbers(this.mContext, parse);
                    } else {
                        lookupResult = new LookupResult();
                        if (!"name".equals(parse.getScheme())) {
                            Slog.w("ValidateNoPeople", "unsupported URI " + str);
                        }
                    }
                }
                if (lookupResult != null) {
                    if (!r4) {
                        synchronized (ValidateNotificationPeople.this.mPeopleCache) {
                            ValidateNotificationPeople.this.mPeopleCache.put(cacheKey, lookupResult);
                        }
                    }
                    if (ValidateNotificationPeople.DEBUG) {
                        Slog.d("ValidateNoPeople", "lookup contactAffinity is " + lookupResult.getAffinity());
                    }
                    this.mContactAffinity = Math.max(this.mContactAffinity, lookupResult.getAffinity());
                    if (lookupResult.getPhoneNumbers() != null) {
                        if (this.mPhoneNumbers == null) {
                            this.mPhoneNumbers = new ArraySet();
                        }
                        this.mPhoneNumbers.addAll(lookupResult.getPhoneNumbers());
                    }
                } else if (ValidateNotificationPeople.DEBUG) {
                    Slog.d("ValidateNoPeople", "lookupResult is null");
                }
            }
            if (ValidateNotificationPeople.DEBUG) {
                Slog.d("ValidateNoPeople", "Validation finished in " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
            }
            if (this.mRecord != null) {
                NotificationUsageStats notificationUsageStats = ValidateNotificationPeople.this.mUsageStats;
                NotificationRecord notificationRecord = this.mRecord;
                float f = this.mContactAffinity;
                notificationUsageStats.registerPeopleAffinity(notificationRecord, f > DisplayPowerController2.RATE_FROM_DOZE_TO_ON, f == 1.0f, false);
            }
        }

        public static LookupResult resolvePhoneContact(Context context, String str) {
            return searchContacts(context, Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(str)));
        }

        public static LookupResult resolveEmailContact(Context context, String str) {
            return searchContacts(context, Uri.withAppendedPath(ContactsContract.CommonDataKinds.Email.CONTENT_LOOKUP_URI, Uri.encode(str)));
        }

        public static LookupResult searchContacts(Context context, Uri uri) {
            LookupResult lookupResult = new LookupResult();
            Uri createCorpLookupUriFromEnterpriseLookupUri = ContactsContract.Contacts.createCorpLookupUriFromEnterpriseLookupUri(uri);
            if (createCorpLookupUriFromEnterpriseLookupUri == null) {
                addContacts(lookupResult, context, uri);
            } else {
                addWorkContacts(lookupResult, context, createCorpLookupUriFromEnterpriseLookupUri);
            }
            return lookupResult;
        }

        public static LookupResult searchContactsAndLookupNumbers(Context context, Uri uri) {
            LookupResult searchContacts = searchContacts(context, uri);
            String phoneLookupKey = searchContacts.getPhoneLookupKey();
            if (phoneLookupKey != null) {
                try {
                    Cursor query = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, ValidateNotificationPeople.PHONE_LOOKUP_PROJECTION, "lookup = ?", new String[]{phoneLookupKey}, null);
                    try {
                        if (query == null) {
                            Slog.w("ValidateNoPeople", "Cursor is null when querying contact phone number.");
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
                    Slog.w("ValidateNoPeople", "Problem getting content resolver or querying phone numbers.", th);
                }
            }
            return searchContacts;
        }

        public static void addWorkContacts(LookupResult lookupResult, Context context, Uri uri) {
            int findWorkUserId = findWorkUserId(context);
            if (findWorkUserId == -1) {
                Slog.w("ValidateNoPeople", "Work profile user ID not found for work contact: " + uri);
                return;
            }
            addContacts(lookupResult, context, ContentProvider.maybeAddUserId(uri, findWorkUserId));
        }

        public static int findWorkUserId(Context context) {
            UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
            for (int i : userManager.getProfileIds(context.getUserId(), true)) {
                if (userManager.isManagedProfile(i)) {
                    return i;
                }
            }
            return -1;
        }

        public static void addContacts(LookupResult lookupResult, Context context, Uri uri) {
            try {
                Cursor query = context.getContentResolver().query(uri, ValidateNotificationPeople.LOOKUP_PROJECTION, null, null, null);
                try {
                    if (query == null) {
                        Slog.w("ValidateNoPeople", "Null cursor from contacts query.");
                        if (query != null) {
                            query.close();
                            return;
                        }
                        return;
                    }
                    while (query.moveToNext()) {
                        lookupResult.mergeContact(query);
                    }
                    query.close();
                } finally {
                }
            } catch (Throwable th) {
                Slog.w("ValidateNoPeople", "Problem getting content resolver or performing contacts query.", th);
            }
        }

        @Override // com.android.server.notification.RankingReconsideration
        public void applyChangesLocked(NotificationRecord notificationRecord) {
            notificationRecord.setContactAffinity(Math.max(this.mContactAffinity, notificationRecord.getContactAffinity()));
            if (ValidateNotificationPeople.VERBOSE) {
                Slog.i("ValidateNoPeople", "final affinity: " + notificationRecord.getContactAffinity());
            }
            notificationRecord.mergePhoneNumbers(this.mPhoneNumbers);
        }

        public float getContactAffinity() {
            return this.mContactAffinity;
        }

        public void setRecord(NotificationRecord notificationRecord) {
            this.mRecord = notificationRecord;
        }
    }
}
