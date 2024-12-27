package com.android.server.notification;

import android.R;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.UserHandle;
import android.telecom.TelecomManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LocalLog;
import android.util.Slog;

import com.android.internal.util.NotificationMessagingUtil;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ZenModeFiltering {
    public static final boolean DEBUG = ZenModeHelper.DEBUG;
    public static final RepeatCallers REPEAT_CALLERS = new RepeatCallers();
    public final Context mContext;
    public ComponentName mDefaultPhoneApp;
    public final NotificationMessagingUtil mMessagingUtil;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RepeatCallers {
        public int mThresholdMinutes;
        public final ArrayMap mTelCalls = new ArrayMap();
        public final ArrayMap mOtherCalls = new ArrayMap();

        /* renamed from: -$$Nest$misRepeat, reason: not valid java name */
        public static boolean m734$$Nest$misRepeat(
                RepeatCallers repeatCallers, Context context, Bundle bundle, ArraySet arraySet) {
            boolean z;
            synchronized (repeatCallers) {
                if (repeatCallers.mThresholdMinutes <= 0) {
                    repeatCallers.mThresholdMinutes =
                            context.getResources()
                                    .getInteger(
                                            R.integer
                                                    .leanback_setup_alpha_backward_in_content_delay);
                }
                z = false;
                if (repeatCallers.mThresholdMinutes > 0 && bundle != null) {
                    String[] extraPeople = ValidateNotificationPeople.getExtraPeople(bundle);
                    if (extraPeople != null && extraPeople.length != 0) {
                        long currentTimeMillis = System.currentTimeMillis();
                        repeatCallers.cleanUp(repeatCallers.mTelCalls, currentTimeMillis);
                        repeatCallers.cleanUp(repeatCallers.mOtherCalls, currentTimeMillis);
                        z = repeatCallers.checkCallers(context, extraPeople, arraySet);
                    }
                }
            }
            return z;
        }

        public final synchronized boolean checkCallers(
                Context context, String[] strArr, ArraySet arraySet) {
            boolean z;
            try {
                String networkCountryIso =
                        ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                                .getNetworkCountryIso();
                z = false;
                boolean z2 = false;
                boolean z3 = false;
                for (String str : strArr) {
                    if (str != null) {
                        Uri parse = Uri.parse(str);
                        if ("tel".equals(parse.getScheme())) {
                            if (checkForNumber(parse.getSchemeSpecificPart(), networkCountryIso)) {
                                z = true;
                            }
                            z2 = true;
                        } else if (this.mOtherCalls.containsKey(str)) {
                            z = true;
                            z3 = true;
                        } else {
                            z3 = true;
                        }
                    }
                }
                if (arraySet != null) {
                    Iterator it = arraySet.iterator();
                    while (it.hasNext()) {
                        if (checkForNumber((String) it.next(), networkCountryIso)) {
                            z = true;
                        }
                        z2 = true;
                    }
                }
                LocalLog localLog = ZenLog.STATE_CHANGES;
                ZenLog.append(20, "res=" + z + ", given phone number=" + z2 + ", given uri=" + z3);
            } catch (Throwable th) {
                throw th;
            }
            return z;
        }

        public final synchronized boolean checkForNumber(String str, String str2) {
            if (this.mTelCalls.containsKey(str)) {
                return true;
            }
            String decode = Uri.decode(str);
            if (decode != null) {
                Iterator it = this.mTelCalls.keySet().iterator();
                while (it.hasNext()) {
                    if (PhoneNumberUtils.areSamePhoneNumber(decode, (String) it.next(), str2)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public final synchronized void cleanUp(ArrayMap arrayMap, long j) {
            try {
                for (int size = arrayMap.size() - 1; size >= 0; size--) {
                    long longValue = ((Long) arrayMap.valueAt(size)).longValue();
                    if (longValue <= j && j - longValue <= this.mThresholdMinutes * 60000) {}
                    arrayMap.removeAt(size);
                }
            } catch (Throwable th) {
                throw th;
            }
        }

        public final synchronized void recordCallers(String[] strArr, ArraySet arraySet, long j) {
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            for (String str : strArr) {
                try {
                    if (str != null) {
                        Uri parse = Uri.parse(str);
                        if ("tel".equals(parse.getScheme())) {
                            String decode = Uri.decode(parse.getSchemeSpecificPart());
                            if (decode != null) {
                                this.mTelCalls.put(decode, Long.valueOf(j));
                                z = true;
                                z2 = true;
                            }
                        } else {
                            this.mOtherCalls.put(str, Long.valueOf(j));
                            z = true;
                            z3 = true;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (arraySet != null) {
                Iterator it = arraySet.iterator();
                while (it.hasNext()) {
                    String str2 = (String) it.next();
                    if (str2 != null) {
                        this.mTelCalls.put(str2, Long.valueOf(j));
                        z = true;
                        z2 = true;
                    }
                }
            }
            if (z) {
                LocalLog localLog = ZenLog.STATE_CHANGES;
                ZenLog.append(19, "has phone number=" + z2 + ", has uri=" + z3);
            }
        }
    }

    public ZenModeFiltering(Context context) {
        this.mContext = context;
        this.mMessagingUtil = new NotificationMessagingUtil(context, (Object) null);
    }

    public static boolean audienceMatches(float f, int i) {
        if (i == 0) {
            return true;
        }
        if (i == 1) {
            return f >= 0.5f;
        }
        if (i == 2) {
            return f >= 1.0f;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                i, "Encountered unknown source: ", "ZenModeHelper");
        return true;
    }

    public static boolean matchesCallFilter(
            Context context,
            int i,
            NotificationManager.Policy policy,
            UserHandle userHandle,
            Bundle bundle,
            ValidateNotificationPeople validateNotificationPeople,
            int i2) {
        if (i == 2) {
            ZenLog.traceMatchesCallFilter(i2, "no interruptions", false);
            return false;
        }
        if (i == 3) {
            ZenLog.traceMatchesCallFilter(i2, "alarms only", false);
            return false;
        }
        if (i == 1) {
            List exceptionContacts = policy.getExceptionContacts();
            if (exceptionContacts != null && validateNotificationPeople != null) {
                if (policy.exceptionContactsFlag == 0
                        && exceptionContacts.size() > 0
                        && validateNotificationPeople.isInExceptionContacts(
                                userHandle, bundle, exceptionContacts)) {
                    ZenLog.traceMatchesCallFilter(i2, "contact matched (allowed)", true);
                    return true;
                }
                if (policy.exceptionContactsFlag == 1
                        && (exceptionContacts.isEmpty()
                                || (exceptionContacts.size() > 0
                                        && !validateNotificationPeople.isInExceptionContacts(
                                                userHandle, bundle, exceptionContacts)))) {
                    ZenLog.traceMatchesCallFilter(i2, "contact matched (disallowed)", true);
                    return true;
                }
            }
            if (policy.allowRepeatCallers()
                    && RepeatCallers.m734$$Nest$misRepeat(REPEAT_CALLERS, context, bundle, null)) {
                ZenLog.traceMatchesCallFilter(i2, "repeat caller", true);
                return true;
            }
            if (!policy.allowCalls()) {
                ZenLog.traceMatchesCallFilter(i2, "calls not allowed", false);
                return false;
            }
            if (validateNotificationPeople != null) {
                if (ValidateNotificationPeople.DEBUG) {
                    Slog.d("ValidateNoPeople", "checking affinity for " + userHandle);
                }
                float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
                if (bundle != null) {
                    String l = Long.toString(System.nanoTime());
                    float[] fArr = new float[1];
                    ArraySet arraySet = new ArraySet();
                    Context contextAsUser = validateNotificationPeople.getContextAsUser(userHandle);
                    if (contextAsUser != null) {
                        final ValidateNotificationPeople.PeopleRankingReconsideration
                                validatePeople =
                                        validateNotificationPeople.validatePeople(
                                                contextAsUser, l, bundle, null, fArr, arraySet);
                        f = fArr[0];
                        if (validatePeople != null) {
                            final Semaphore semaphore = new Semaphore(0);
                            AsyncTask.THREAD_POOL_EXECUTOR.execute(
                                    new Runnable() { // from class:
                                                     // com.android.server.notification.ValidateNotificationPeople.2
                                        public final /* synthetic */ Semaphore val$s;

                                        public AnonymousClass2(final Semaphore semaphore2) {
                                            r2 = semaphore2;
                                        }

                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            PeopleRankingReconsideration.this.work();
                                            r2.release();
                                        }
                                    });
                            try {
                                if (semaphore2.tryAcquire(3000, TimeUnit.MILLISECONDS)) {
                                    f = Math.max(validatePeople.mContactAffinity, f);
                                } else {
                                    Slog.w(
                                            "ValidateNoPeople",
                                            "Timeout while waiting for affinity: "
                                                    + l
                                                    + ". Returning timeoutAffinity=1.0");
                                    f = 1.0f;
                                }
                            } catch (InterruptedException e) {
                                Slog.w(
                                        "ValidateNoPeople",
                                        "InterruptedException while waiting for affinity: "
                                                + l
                                                + ". Returning affinity="
                                                + f,
                                        e);
                            }
                        }
                    }
                }
                Float valueOf = Float.valueOf(f);
                if (bundle == null) {
                    ZenLog.append(22, "extra is null");
                } else {
                    LocalLog localLog = ZenLog.STATE_CHANGES;
                    ZenLog.append(22, bundle.toString() + ", contactAffinity: " + valueOf);
                }
                boolean audienceMatches = audienceMatches(f, policy.allowCallsFrom());
                ZenLog.traceMatchesCallFilter(i2, "contact affinity " + f, audienceMatches);
                return audienceMatches;
            }
        }
        ZenLog.traceMatchesCallFilter(i2, "no restrictions", true);
        return true;
    }

    public static void maybeLogInterceptDecision(
            NotificationRecord notificationRecord, boolean z, String str) {
        boolean z2 = notificationRecord.mIntercept;
        boolean z3 = notificationRecord.mInterceptSet;
        if (z3 && z2 == z) {
            return;
        }
        if (!z3) {
            str = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("new:", str);
        } else if (z2 != z) {
            str = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("updated:", str);
        }
        if (!z) {
            ZenLog.traceNotIntercepted(notificationRecord, str);
            return;
        }
        LocalLog localLog = ZenLog.STATE_CHANGES;
        ZenLog.append(1, notificationRecord.sbn.getKey() + "," + str);
    }

    public static boolean shouldInterceptAudience(NotificationRecord notificationRecord, int i) {
        float f = notificationRecord.mContactAffinity;
        if (audienceMatches(f, i)) {
            maybeLogInterceptDecision(notificationRecord, false, "affinity=" + f);
            return false;
        }
        maybeLogInterceptDecision(notificationRecord, true, "!audienceMatches,affinity=" + f);
        return true;
    }

    public final boolean isCall(NotificationRecord notificationRecord) {
        ComponentName componentName;
        if (notificationRecord != null) {
            String packageName = notificationRecord.sbn.getPackageName();
            if (this.mDefaultPhoneApp == null) {
                TelecomManager telecomManager =
                        (TelecomManager) this.mContext.getSystemService("telecom");
                this.mDefaultPhoneApp =
                        telecomManager != null ? telecomManager.getDefaultPhoneApp() : null;
                if (DEBUG) {
                    Slog.d("ZenModeHelper", "Default phone app: " + this.mDefaultPhoneApp);
                }
            }
            if ((packageName != null
                            && (componentName = this.mDefaultPhoneApp) != null
                            && packageName.equals(componentName.getPackageName()))
                    || notificationRecord.isCategory("call")) {
                return true;
            }
        }
        return false;
    }

    public final boolean matchesExceptionContacts(
            NotificationManager.Policy policy, NotificationRecord notificationRecord) {
        String country;
        String[] split;
        String stringBuffer;
        if (notificationRecord.sbn != null) {
            TelephonyManager telephonyManager =
                    (TelephonyManager) this.mContext.getSystemService("phone");
            if (telephonyManager != null) {
                country = telephonyManager.getNetworkCountryIso();
                if (country == null && (country = telephonyManager.getSimCountryIso()) == null) {
                    country = Locale.getDefault().getCountry();
                }
            } else {
                Slog.e("ZenModeHelper", "cannot get a TelephonyManager.");
                country = Locale.getDefault().getCountry();
            }
            List<String> exceptionContacts = policy.getExceptionContacts();
            if (exceptionContacts == null) {
                return false;
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (String str : exceptionContacts) {
                if (str.length() != 0) {
                    String[] split2 = str.split(";");
                    for (int i = 0; i < split2.length; i++) {
                        if (i == 0) {
                            arrayList.add(split2[0]);
                        }
                        if (i == 1) {
                            arrayList2.add(split2[1]);
                        }
                    }
                }
            }
            ArraySet arraySet = notificationRecord.mPhoneNumbers;
            if (arraySet != null) {
                Iterator it = arraySet.iterator();
                while (it.hasNext()) {
                    String str2 = (String) it.next();
                    if (str2 != null) {
                        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                            if (arrayList2.get(i2) != null
                                    && PhoneNumberUtils.areSamePhoneNumber(
                                            str2, (String) arrayList2.get(i2), country)) {
                                return true;
                            }
                        }
                    }
                }
            }
            String[] extraPeople =
                    ValidateNotificationPeople.getExtraPeople(
                            notificationRecord.sbn.getNotification().extras);
            if (extraPeople != null) {
                for (String str3 : extraPeople) {
                    if (str3 != null && (split = str3.split(":")) != null) {
                        BootReceiver$$ExternalSyntheticOutline0.m(
                                new StringBuilder("received contact type="),
                                split[0],
                                "ZenModeHelper");
                        if (split.length < 2) {
                            Slog.e("ZenModeHelper", "The contact info doesn't have a number.");
                        } else {
                            String str4 = null;
                            if (split[0].equals("tel")) {
                                try {
                                    str4 = URLDecoder.decode(split[1], "UTF-8");
                                } catch (Exception e) {
                                    Slog.d("ZenModeHelper", "URLDecoder.decode error");
                                    e.printStackTrace();
                                }
                                StringBuilder sb = new StringBuilder("received contact number=");
                                StringBuffer stringBuffer2 = new StringBuffer();
                                if (str4 == null) {
                                    stringBuffer2.append("null");
                                    stringBuffer = stringBuffer2.toString();
                                } else {
                                    for (int i3 = 0; i3 < str4.length(); i3++) {
                                        char charAt = str4.charAt(i3);
                                        if (charAt != '-') {
                                            switch (charAt) {
                                                case '0':
                                                    stringBuffer2.append('o');
                                                    break;
                                                case '1':
                                                    stringBuffer2.append('a');
                                                    break;
                                                case '2':
                                                    stringBuffer2.append('b');
                                                    break;
                                                case '3':
                                                    stringBuffer2.append('c');
                                                    break;
                                                case '4':
                                                    stringBuffer2.append('d');
                                                    break;
                                                case '5':
                                                    stringBuffer2.append('e');
                                                    break;
                                                case '6':
                                                    stringBuffer2.append('f');
                                                    break;
                                                case '7':
                                                    stringBuffer2.append('g');
                                                    break;
                                                case '8':
                                                    stringBuffer2.append('h');
                                                    break;
                                                case '9':
                                                    stringBuffer2.append('i');
                                                    break;
                                            }
                                        } else {
                                            stringBuffer2.append('+');
                                        }
                                    }
                                    stringBuffer = stringBuffer2.toString();
                                }
                                BootReceiver$$ExternalSyntheticOutline0.m(
                                        sb, stringBuffer, "ZenModeHelper");
                                for (int i4 = 0; i4 < arrayList2.size(); i4++) {
                                    if (PhoneNumberUtils.areSamePhoneNumber(
                                            str4, (String) arrayList2.get(i4), country)) {
                                        return true;
                                    }
                                }
                            } else if (!split[0].equals("contactId")) {
                                BootReceiver$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("received contact specificUri="),
                                        split[1],
                                        "ZenModeHelper");
                            } else if (arrayList.contains(null)) {
                                if (DEBUG) {
                                    Slog.d("ZenModeHelper", "contactId");
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x00c0, code lost:

       if (r7 == false) goto L71;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldIntercept(
            int r7,
            android.app.NotificationManager.Policy r8,
            com.android.server.notification.NotificationRecord r9) {
        /*
            Method dump skipped, instructions count: 561
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.ZenModeFiltering.shouldIntercept(int,"
                    + " android.app.NotificationManager$Policy,"
                    + " com.android.server.notification.NotificationRecord):boolean");
    }
}
