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
import java.util.IllegalFormatException;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
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
    private static volatile SessionManager sSessionManager;
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
        if (!SystemProperties.getBoolean("ro.product_ship", true) && !SystemProperties.getBoolean("persist.ril.override.product_ship", false)) {
            z = false;
        }
        SHIP_BUILD = z;
        sSingletonSync = new Object();
        sLock = null;
        sIsUserExtendedLoggingEnabled = false;
        sIsUnitTestingEnabled = false;
        sUserExtendedLoggingStopTime = 0L;
    }

    private Log() {
    }

    public static void d(String str, String str2, Object... objArr) {
        if (sIsUserExtendedLoggingEnabled) {
            maybeDisableLogging();
            Slog.i(TAG, buildMessage(str, str2, objArr));
        } else if (DEBUG) {
            Slog.d(TAG, buildMessage(str, str2, objArr));
        }
    }

    public static void d(Object obj, String str, Object... objArr) {
        if (sIsUserExtendedLoggingEnabled) {
            maybeDisableLogging();
            Slog.i(TAG, buildMessage(getPrefixFromObject(obj), str, objArr));
        } else if (DEBUG) {
            Slog.d(TAG, buildMessage(getPrefixFromObject(obj), str, objArr));
        }
    }

    public static void i(String str, String str2, Object... objArr) {
        if (INFO) {
            Slog.i(TAG, buildMessage(str, str2, objArr));
        }
    }

    public static void i(Object obj, String str, Object... objArr) {
        if (INFO) {
            Slog.i(TAG, buildMessage(getPrefixFromObject(obj), str, objArr));
        }
    }

    public static void v(String str, String str2, Object... objArr) {
        if (sIsUserExtendedLoggingEnabled) {
            maybeDisableLogging();
            Slog.i(TAG, buildMessage(str, str2, objArr));
        } else if (VERBOSE) {
            Slog.v(TAG, buildMessage(str, str2, objArr));
        }
    }

    public static void v(Object obj, String str, Object... objArr) {
        if (sIsUserExtendedLoggingEnabled) {
            maybeDisableLogging();
            Slog.i(TAG, buildMessage(getPrefixFromObject(obj), str, objArr));
        } else if (VERBOSE) {
            Slog.v(TAG, buildMessage(getPrefixFromObject(obj), str, objArr));
        }
    }

    public static void w(String str, String str2, Object... objArr) {
        if (WARN) {
            Slog.w(TAG, buildMessage(str, str2, objArr));
        }
    }

    public static void w(Object obj, String str, Object... objArr) {
        if (WARN) {
            Slog.w(TAG, buildMessage(getPrefixFromObject(obj), str, objArr));
        }
    }

    public static void e(String str, Throwable th, String str2, Object... objArr) {
        if (ERROR) {
            Slog.e(TAG, buildMessage(str, str2, objArr), th);
        }
    }

    public static void e(Object obj, Throwable th, String str, Object... objArr) {
        if (ERROR) {
            Slog.e(TAG, buildMessage(getPrefixFromObject(obj), str, objArr), th);
        }
    }

    public static void wtf(String str, Throwable th, String str2, Object... objArr) {
        Slog.wtf(TAG, buildMessage(str, str2, objArr), th);
    }

    public static void wtf(Object obj, Throwable th, String str, Object... objArr) {
        Slog.wtf(TAG, buildMessage(getPrefixFromObject(obj), str, objArr), th);
    }

    public static void wtf(String str, String str2, Object... objArr) {
        String strBuildMessage = buildMessage(str, str2, objArr);
        Slog.wtf(TAG, strBuildMessage, new IllegalStateException(strBuildMessage));
    }

    public static void wtf(Object obj, String str, Object... objArr) {
        String strBuildMessage = buildMessage(getPrefixFromObject(obj), str, objArr);
        Slog.wtf(TAG, strBuildMessage, new IllegalStateException(strBuildMessage));
    }

    public static void setSessionContext(Context context) {
        getSessionManager().setContext(context);
    }

    public static void startSession(String str) {
        getSessionManager().startSession(str, null);
    }

    public static void startSession(Session.Info info, String str) {
        getSessionManager().startSession(info, str, null);
    }

    public static void startSession(String str, String str2) {
        getSessionManager().startSession(str, str2);
    }

    public static void startSession(Session.Info info, String str, String str2) {
        getSessionManager().startSession(info, str, str2);
    }

    public static Session createSubsession() {
        return getSessionManager().createSubsession();
    }

    public static Session.Info getExternalSession() {
        return getSessionManager().getExternalSession();
    }

    public static Session.Info getExternalSession(String str) {
        return getSessionManager().getExternalSession(str);
    }

    public static void cancelSubsession(Session session) {
        getSessionManager().cancelSubsession(session);
    }

    public static void continueSession(Session session, String str) {
        getSessionManager().continueSession(session, str);
    }

    public static void endSession() {
        getSessionManager().endSession();
    }

    public static void registerSessionListener(SessionManager.ISessionListener iSessionListener) {
        getSessionManager().registerSessionListener(iSessionListener);
    }

    public static String getSessionId() {
        synchronized (sSingletonSync) {
            if (sSessionManager != null) {
                return getSessionManager().getSessionId();
            }
            return "";
        }
    }

    public static void addEvent(EventManager.Loggable loggable, String str) {
        getEventManager().event(loggable, str, null);
    }

    public static void addEvent(EventManager.Loggable loggable, String str, Object obj) {
        getEventManager().event(loggable, str, obj);
    }

    public static void addEvent(EventManager.Loggable loggable, String str, String str2, Object... objArr) {
        getEventManager().event(loggable, str, str2, objArr);
    }

    public static void registerEventListener(EventManager.EventListener eventListener) {
        getEventManager().registerEventListener(eventListener);
    }

    public static void addRequestResponsePair(EventManager.TimedEventPair timedEventPair) {
        getEventManager().addRequestResponsePair(timedEventPair);
    }

    public static void dumpEvents(IndentingPrintWriter indentingPrintWriter) {
        synchronized (sSingletonSync) {
            if (sEventManager != null) {
                getEventManager().dumpEvents(indentingPrintWriter);
            } else {
                indentingPrintWriter.println("No Historical Events Logged.");
            }
        }
    }

    public static void dumpEventsTimeline(IndentingPrintWriter indentingPrintWriter) {
        synchronized (sSingletonSync) {
            if (sEventManager != null) {
                getEventManager().dumpEventsTimeline(indentingPrintWriter);
            } else {
                indentingPrintWriter.println("No Historical Events Logged.");
            }
        }
    }

    public static void setIsExtendedLoggingEnabled(boolean z) {
        if (sIsUserExtendedLoggingEnabled == z) {
            return;
        }
        EventManager eventManager = sEventManager;
        if (eventManager != null) {
            eventManager.changeEventCacheSize(z ? 20 : 10);
        }
        sIsUserExtendedLoggingEnabled = z;
        if (z) {
            sUserExtendedLoggingStopTime = System.currentTimeMillis() + 1800000;
        } else {
            sUserExtendedLoggingStopTime = 0L;
        }
    }

    public static void setUnitTestingEnabled(boolean z) {
        sIsUnitTestingEnabled = z;
    }

    public static boolean isUnitTestingEnabled() {
        return sIsUnitTestingEnabled;
    }

    private static EventManager getEventManager() {
        if (sEventManager == null) {
            synchronized (sSingletonSync) {
                if (sEventManager == null) {
                    EventManager eventManager = new EventManager(new SessionManager.ISessionIdQueryHandler() { // from class: android.telecom.Log$$ExternalSyntheticLambda1
                        @Override // android.telecom.Logging.SessionManager.ISessionIdQueryHandler
                        public final String getSessionId() {
                            return Log.getSessionId();
                        }
                    });
                    sEventManager = eventManager;
                    return eventManager;
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

    public static SessionManager setSessionManager(Context context, Runnable runnable) {
        if (sSessionManager == null) {
            synchronized (sSingletonSync) {
                if (sSessionManager == null) {
                    sSessionManager = new SessionManager(runnable);
                    sSessionManager.setContext(context);
                    return sSessionManager;
                }
            }
        }
        return sSessionManager;
    }

    public static void setTag(String str) {
        TAG = str;
        DEBUG = isLoggable(3);
        INFO = isLoggable(4);
        VERBOSE = isLoggable(2);
        WARN = isLoggable(5);
        ERROR = isLoggable(6);
    }

    public static void setLock(Object obj) {
        if (Build.IS_USER) {
            return;
        }
        sLock = obj;
    }

    private static void maybeDisableLogging() {
        if (sIsUserExtendedLoggingEnabled && sUserExtendedLoggingStopTime < System.currentTimeMillis()) {
            sUserExtendedLoggingStopTime = 0L;
            sIsUserExtendedLoggingEnabled = false;
        }
    }

    public static boolean isLoggable(int i) {
        return android.util.Log.isLoggable(TAG, i);
    }

    public static String piiHandle(Object obj) {
        if (obj == null || (!SHIP_BUILD && VERBOSE)) {
            return String.valueOf(obj);
        }
        StringBuilder sb = new StringBuilder();
        if (obj instanceof Uri) {
            Uri uri = (Uri) obj;
            String scheme = uri.getScheme();
            if (!TextUtils.isEmpty(scheme)) {
                sb.append(scheme);
                sb.append(":");
            }
            String schemeSpecificPart = uri.getSchemeSpecificPart();
            if (PhoneAccount.SCHEME_TEL.equals(scheme)) {
                obfuscatePhoneNumber(sb, schemeSpecificPart);
            } else if ("sip".equals(scheme)) {
                for (int i = 0; i < schemeSpecificPart.length(); i++) {
                    char cCharAt = schemeSpecificPart.charAt(i);
                    if (cCharAt != '@' && cCharAt != '.') {
                        cCharAt = '*';
                    }
                    sb.append(cCharAt);
                }
            } else {
                sb.append(pii(obj));
            }
        } else if (obj instanceof String) {
            obfuscatePhoneNumber(sb, (String) obj);
        }
        return sb.toString();
    }

    private static void obfuscatePhoneNumber(StringBuilder sb, String str) {
        int dialableCount = getDialableCount(str) - NUM_DIALABLE_DIGITS_TO_LOG;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            boolean zIsDialable = PhoneNumberUtils.isDialable(cCharAt);
            if (zIsDialable) {
                dialableCount--;
            }
            sb.append((!zIsDialable || dialableCount < 0) ? Character.valueOf(cCharAt) : "*");
        }
    }

    private static int getDialableCount(String str) {
        int i = 0;
        for (char c : str.toCharArray()) {
            if (PhoneNumberUtils.isDialable(c)) {
                i++;
            }
        }
        return i;
    }

    public static String pii(Object obj) {
        if (obj == null || (!SHIP_BUILD && VERBOSE)) {
            return String.valueOf(obj);
        }
        return "***";
    }

    private static String getPrefixFromObject(Object obj) {
        return obj == null ? "<null>" : obj.getClass().getSimpleName();
    }

    private static String buildMessage(String str, String str2, Object... objArr) {
        String str3;
        String sessionId = getSessionId();
        if (TextUtils.isEmpty(sessionId)) {
            str3 = "";
        } else {
            str3 = ": " + sessionId;
        }
        if (objArr != null) {
            try {
                if (objArr.length != 0) {
                    str2 = String.format(Locale.US, str2, objArr);
                }
            } catch (IllegalFormatException e) {
                e(TAG, (Throwable) e, "Log: IllegalFormatException: formatString='%s' numArgs=%d", str2, Integer.valueOf(objArr.length));
                str2 = str2 + " (An error occurred while formatting the message.)";
            }
        }
        Object obj = sLock;
        return String.format(Locale.US, "%s: %s%s%s", str, str2, str3, obj != null ? Thread.holdsLock(obj) ? "🔒" : "❗" : "");
    }

    public static String getPackageAbbreviation(ComponentName componentName) {
        if (componentName == null) {
            return "";
        }
        return getPackageAbbreviation(componentName.getPackageName());
    }

    public static String getPackageAbbreviation(String str) {
        if (str == null) {
            return "";
        }
        return (String) Arrays.stream(str.split("\\.")).map(new Function() { // from class: android.telecom.Log$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Log.lambda$getPackageAbbreviation$0((String) obj);
            }
        }).collect(Collectors.joining(""));
    }

    static /* synthetic */ String lambda$getPackageAbbreviation$0(String str) {
        return str.length() == 0 ? "" : str.substring(0, 1);
    }

    public static Object maskPii(Object obj) {
        return SHIP_BUILD ? "<MASKED>" : obj;
    }
}
