package android.telecom;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.SystemProperties;
import android.telecom.Logging.EventManager;
import android.telecom.Logging.Session;
import android.telecom.Logging.SessionManager;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Slog;

import com.android.internal.util.IndentingPrintWriter;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Log {
    public static boolean DEBUG = false;
    public static boolean ERROR = false;
    private static final int EVENTS_TO_CACHE = 10;
    private static final int EVENTS_TO_CACHE_DEBUG = 20;
    private static final long EXTENDED_LOGGING_DURATION_MILLIS = 1800000;
    private static final boolean FORCE_LOGGING = false;
    public static boolean INFO;
    private static final int NUM_DIALABLE_DIGITS_TO_LOG;
    public static final boolean SHIP_BUILD;
    public static String TAG;
    private static final boolean USER_BUILD;
    public static boolean VERBOSE;
    public static boolean WARN;
    private static EventManager sEventManager;
    private static boolean sIsUnitTestingEnabled;
    private static boolean sIsUserExtendedLoggingEnabled;
    private static Object sLock;
    private static SessionManager sSessionManager;
    private static final Object sSingletonSync;
    private static long sUserExtendedLoggingStopTime;

    static {
        NUM_DIALABLE_DIGITS_TO_LOG = Build.IS_USER ? 0 : 2;
        TAG = "TelecomFramework";
        DEBUG = isLoggable(3);
        INFO = isLoggable(4);
        VERBOSE = isLoggable(2);
        WARN = isLoggable(5);
        ERROR = isLoggable(6);
        USER_BUILD = Build.IS_USER;
        boolean z = true;
        if (!SystemProperties.getBoolean("ro.product_ship", true)
                && !SystemProperties.getBoolean("persist.ril.override.product_ship", false)) {
            z = false;
        }
        SHIP_BUILD = z;
        sSingletonSync = new Object();
        sLock = null;
        sIsUserExtendedLoggingEnabled = false;
        sIsUnitTestingEnabled = false;
        sUserExtendedLoggingStopTime = 0L;
    }

    private Log() {}

    public static void d(String prefix, String format, Object... args) {
        if (sIsUserExtendedLoggingEnabled) {
            maybeDisableLogging();
            Slog.i(TAG, buildMessage(prefix, format, args));
        } else if (DEBUG) {
            Slog.d(TAG, buildMessage(prefix, format, args));
        }
    }

    public static void d(Object objectPrefix, String format, Object... args) {
        if (sIsUserExtendedLoggingEnabled) {
            maybeDisableLogging();
            Slog.i(TAG, buildMessage(getPrefixFromObject(objectPrefix), format, args));
        } else if (DEBUG) {
            Slog.d(TAG, buildMessage(getPrefixFromObject(objectPrefix), format, args));
        }
    }

    public static void i(String prefix, String format, Object... args) {
        if (INFO) {
            Slog.i(TAG, buildMessage(prefix, format, args));
        }
    }

    public static void i(Object objectPrefix, String format, Object... args) {
        if (INFO) {
            Slog.i(TAG, buildMessage(getPrefixFromObject(objectPrefix), format, args));
        }
    }

    public static void v(String prefix, String format, Object... args) {
        if (sIsUserExtendedLoggingEnabled) {
            maybeDisableLogging();
            Slog.i(TAG, buildMessage(prefix, format, args));
        } else if (VERBOSE) {
            Slog.v(TAG, buildMessage(prefix, format, args));
        }
    }

    public static void v(Object objectPrefix, String format, Object... args) {
        if (sIsUserExtendedLoggingEnabled) {
            maybeDisableLogging();
            Slog.i(TAG, buildMessage(getPrefixFromObject(objectPrefix), format, args));
        } else if (VERBOSE) {
            Slog.v(TAG, buildMessage(getPrefixFromObject(objectPrefix), format, args));
        }
    }

    public static void w(String prefix, String format, Object... args) {
        if (WARN) {
            Slog.w(TAG, buildMessage(prefix, format, args));
        }
    }

    public static void w(Object objectPrefix, String format, Object... args) {
        if (WARN) {
            Slog.w(TAG, buildMessage(getPrefixFromObject(objectPrefix), format, args));
        }
    }

    public static void e(String prefix, Throwable tr, String format, Object... args) {
        if (ERROR) {
            Slog.e(TAG, buildMessage(prefix, format, args), tr);
        }
    }

    public static void e(Object objectPrefix, Throwable tr, String format, Object... args) {
        if (ERROR) {
            Slog.e(TAG, buildMessage(getPrefixFromObject(objectPrefix), format, args), tr);
        }
    }

    public static void wtf(String prefix, Throwable tr, String format, Object... args) {
        Slog.wtf(TAG, buildMessage(prefix, format, args), tr);
    }

    public static void wtf(Object objectPrefix, Throwable tr, String format, Object... args) {
        Slog.wtf(TAG, buildMessage(getPrefixFromObject(objectPrefix), format, args), tr);
    }

    public static void wtf(String prefix, String format, Object... args) {
        String msg = buildMessage(prefix, format, args);
        Slog.wtf(TAG, msg, new IllegalStateException(msg));
    }

    public static void wtf(Object objectPrefix, String format, Object... args) {
        String msg = buildMessage(getPrefixFromObject(objectPrefix), format, args);
        Slog.wtf(TAG, msg, new IllegalStateException(msg));
    }

    public static void setSessionContext(Context context) {
        getSessionManager().setContext(context);
    }

    public static void startSession(String shortMethodName) {
        getSessionManager().startSession(shortMethodName, null);
    }

    public static void startSession(Session.Info info, String shortMethodName) {
        getSessionManager().startSession(info, shortMethodName, null);
    }

    public static void startSession(String shortMethodName, String callerIdentification) {
        getSessionManager().startSession(shortMethodName, callerIdentification);
    }

    public static void startSession(
            Session.Info info, String shortMethodName, String callerIdentification) {
        getSessionManager().startSession(info, shortMethodName, callerIdentification);
    }

    public static Session createSubsession() {
        return getSessionManager().createSubsession();
    }

    public static Session.Info getExternalSession() {
        return getSessionManager().getExternalSession();
    }

    public static Session.Info getExternalSession(String ownerInfo) {
        return getSessionManager().getExternalSession(ownerInfo);
    }

    public static void cancelSubsession(Session subsession) {
        getSessionManager().cancelSubsession(subsession);
    }

    public static void continueSession(Session subsession, String shortMethodName) {
        getSessionManager().continueSession(subsession, shortMethodName);
    }

    public static void endSession() {
        getSessionManager().endSession();
    }

    public static void registerSessionListener(SessionManager.ISessionListener l) {
        getSessionManager().registerSessionListener(l);
    }

    public static String getSessionId() {
        synchronized (sSingletonSync) {
            if (sSessionManager != null) {
                return getSessionManager().getSessionId();
            }
            return "";
        }
    }

    public static void addEvent(EventManager.Loggable recordEntry, String event) {
        getEventManager().event(recordEntry, event, null);
    }

    public static void addEvent(EventManager.Loggable recordEntry, String event, Object data) {
        getEventManager().event(recordEntry, event, data);
    }

    public static void addEvent(
            EventManager.Loggable recordEntry, String event, String format, Object... args) {
        getEventManager().event(recordEntry, event, format, args);
    }

    public static void registerEventListener(EventManager.EventListener e) {
        getEventManager().registerEventListener(e);
    }

    public static void addRequestResponsePair(EventManager.TimedEventPair p) {
        getEventManager().addRequestResponsePair(p);
    }

    public static void dumpEvents(IndentingPrintWriter pw) {
        synchronized (sSingletonSync) {
            if (sEventManager != null) {
                getEventManager().dumpEvents(pw);
            } else {
                pw.println("No Historical Events Logged.");
            }
        }
    }

    public static void dumpEventsTimeline(IndentingPrintWriter pw) {
        synchronized (sSingletonSync) {
            if (sEventManager != null) {
                getEventManager().dumpEventsTimeline(pw);
            } else {
                pw.println("No Historical Events Logged.");
            }
        }
    }

    public static void setIsExtendedLoggingEnabled(boolean isExtendedLoggingEnabled) {
        if (sIsUserExtendedLoggingEnabled == isExtendedLoggingEnabled) {
            return;
        }
        if (sEventManager != null) {
            sEventManager.changeEventCacheSize(isExtendedLoggingEnabled ? 20 : 10);
        }
        sIsUserExtendedLoggingEnabled = isExtendedLoggingEnabled;
        if (sIsUserExtendedLoggingEnabled) {
            sUserExtendedLoggingStopTime = System.currentTimeMillis() + 1800000;
        } else {
            sUserExtendedLoggingStopTime = 0L;
        }
    }

    public static void setUnitTestingEnabled(boolean isEnabled) {
        sIsUnitTestingEnabled = isEnabled;
    }

    public static boolean isUnitTestingEnabled() {
        return sIsUnitTestingEnabled;
    }

    private static EventManager getEventManager() {
        if (sEventManager == null) {
            synchronized (sSingletonSync) {
                if (sEventManager == null) {
                    sEventManager =
                            new EventManager(
                                    new SessionManager
                                            .ISessionIdQueryHandler() { // from class:
                                                                        // android.telecom.Log$$ExternalSyntheticLambda1
                                        @Override // android.telecom.Logging.SessionManager.ISessionIdQueryHandler
                                        public final String getSessionId() {
                                            return Log.getSessionId();
                                        }
                                    });
                    return sEventManager;
                }
            }
        }
        return sEventManager;
    }

    public static SessionManager getSessionManager() {
        if (sSessionManager == null) {
            synchronized (sSingletonSync) {
                if (sSessionManager == null) {
                    sSessionManager = new SessionManager();
                    return sSessionManager;
                }
            }
        }
        return sSessionManager;
    }

    public static void setTag(String tag) {
        TAG = tag;
        DEBUG = isLoggable(3);
        INFO = isLoggable(4);
        VERBOSE = isLoggable(2);
        WARN = isLoggable(5);
        ERROR = isLoggable(6);
    }

    public static void setLock(Object lock) {
        if (!Build.IS_USER) {
            sLock = lock;
        }
    }

    private static void maybeDisableLogging() {
        if (sIsUserExtendedLoggingEnabled
                && sUserExtendedLoggingStopTime < System.currentTimeMillis()) {
            sUserExtendedLoggingStopTime = 0L;
            sIsUserExtendedLoggingEnabled = false;
        }
    }

    public static boolean isLoggable(int level) {
        return android.util.Log.isLoggable(TAG, level);
    }

    public static String piiHandle(Object pii) {
        if (pii == null || (!SHIP_BUILD && VERBOSE)) {
            return String.valueOf(pii);
        }
        StringBuilder sb = new StringBuilder();
        if (pii instanceof Uri) {
            Uri uri = (Uri) pii;
            String scheme = uri.getScheme();
            if (!TextUtils.isEmpty(scheme)) {
                sb.append(scheme).append(":");
            }
            String textToObfuscate = uri.getSchemeSpecificPart();
            if (PhoneAccount.SCHEME_TEL.equals(scheme)) {
                obfuscatePhoneNumber(sb, textToObfuscate);
            } else if ("sip".equals(scheme)) {
                for (int i = 0; i < textToObfuscate.length(); i++) {
                    char c = textToObfuscate.charAt(i);
                    if (c != '@' && c != '.') {
                        c = '*';
                    }
                    sb.append(c);
                }
            } else {
                sb.append(pii(pii));
            }
        } else if (pii instanceof String) {
            String number = (String) pii;
            obfuscatePhoneNumber(sb, number);
        }
        return sb.toString();
    }

    private static void obfuscatePhoneNumber(StringBuilder sb, String phoneNumber) {
        int numDigitsToObfuscate = getDialableCount(phoneNumber) - NUM_DIALABLE_DIGITS_TO_LOG;
        for (int i = 0; i < phoneNumber.length(); i++) {
            char c = phoneNumber.charAt(i);
            boolean isDialable = PhoneNumberUtils.isDialable(c);
            if (isDialable) {
                numDigitsToObfuscate--;
            }
            sb.append((!isDialable || numDigitsToObfuscate < 0) ? Character.valueOf(c) : "*");
        }
    }

    private static int getDialableCount(String toCount) {
        int numDialable = 0;
        for (char c : toCount.toCharArray()) {
            if (PhoneNumberUtils.isDialable(c)) {
                numDialable++;
            }
        }
        return numDialable;
    }

    public static String pii(Object pii) {
        if (pii == null || (!SHIP_BUILD && VERBOSE)) {
            return String.valueOf(pii);
        }
        return "***";
    }

    private static String getPrefixFromObject(Object obj) {
        return obj == null ? "<null>" : obj.getClass().getSimpleName();
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String buildMessage(
            java.lang.String r7, java.lang.String r8, java.lang.Object... r9) {
        /*
            java.lang.String r0 = getSessionId()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            java.lang.String r2 = ""
            if (r1 == 0) goto Le
            r1 = r2
            goto L21
        Le:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = ": "
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.StringBuilder r1 = r1.append(r0)
            java.lang.String r1 = r1.toString()
        L21:
            if (r9 == 0) goto L53
            int r3 = r9.length     // Catch: java.util.IllegalFormatException -> L2e
            if (r3 != 0) goto L27
            goto L53
        L27:
            java.util.Locale r3 = java.util.Locale.US     // Catch: java.util.IllegalFormatException -> L2e
            java.lang.String r3 = java.lang.String.format(r3, r8, r9)     // Catch: java.util.IllegalFormatException -> L2e
            goto L54
        L2e:
            r3 = move-exception
            java.lang.String r4 = android.telecom.Log.TAG
            int r5 = r9.length
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            java.lang.Object[] r5 = new java.lang.Object[]{r8, r5}
            java.lang.String r6 = "Log: IllegalFormatException: formatString='%s' numArgs=%d"
            e(r4, r3, r6, r5)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.StringBuilder r4 = r4.append(r8)
            java.lang.String r5 = " (An error occurred while formatting the message.)"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            goto L56
        L53:
            r3 = r8
        L54:
            r4 = r3
        L56:
            java.lang.Object r3 = android.telecom.Log.sLock
            if (r3 == 0) goto L69
            java.lang.Object r2 = android.telecom.Log.sLock
            boolean r2 = java.lang.Thread.holdsLock(r2)
            if (r2 == 0) goto L66
            java.lang.String r2 = "🔒"
            goto L69
        L66:
            java.lang.String r2 = "❗"
        L69:
            java.util.Locale r3 = java.util.Locale.US
            java.lang.String r5 = "%s: %s%s%s"
            java.lang.Object[] r6 = new java.lang.Object[]{r7, r4, r1, r2}
            java.lang.String r3 = java.lang.String.format(r3, r5, r6)
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.telecom.Log.buildMessage(java.lang.String,"
                    + " java.lang.String, java.lang.Object[]):java.lang.String");
    }

    public static String getPackageAbbreviation(ComponentName componentName) {
        if (componentName == null) {
            return "";
        }
        return getPackageAbbreviation(componentName.getPackageName());
    }

    public static String getPackageAbbreviation(String packageName) {
        if (packageName == null) {
            return "";
        }
        return (String)
                Arrays.stream(packageName.split("\\."))
                        .map(
                                new Function() { // from class:
                                                 // android.telecom.Log$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return Log.lambda$getPackageAbbreviation$0((String) obj);
                                    }
                                })
                        .collect(Collectors.joining(""));
    }

    static /* synthetic */ String lambda$getPackageAbbreviation$0(String s) {
        return s.length() == 0 ? "" : s.substring(0, 1);
    }

    public static Object maskPii(Object pii) {
        return SHIP_BUILD ? "<MASKED>" : pii;
    }
}
