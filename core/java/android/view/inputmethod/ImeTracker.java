package android.view.inputmethod;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.SystemProperties;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.inputmethod.ImeTracker;
import com.android.internal.inputmethod.InputMethodDebug;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.util.LatencyTracker;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public interface ImeTracker {
    public static final int ORIGIN_CLIENT = 5;
    public static final int ORIGIN_IME = 7;
    public static final int ORIGIN_SERVER = 6;
    public static final int ORIGIN_WM_SHELL = 8;
    public static final int PHASE_CLIENT_ANIMATION_CANCEL = 40;
    public static final int PHASE_CLIENT_ANIMATION_FINISHED_HIDE = 42;
    public static final int PHASE_CLIENT_ANIMATION_FINISHED_SHOW = 41;
    public static final int PHASE_CLIENT_ANIMATION_RUNNING = 39;
    public static final int PHASE_CLIENT_APPLY_ANIMATION = 32;
    public static final int PHASE_CLIENT_COLLECT_SOURCE_CONTROLS = 35;
    public static final int PHASE_CLIENT_CONTROL_ANIMATION = 33;
    public static final int PHASE_CLIENT_HANDLE_HIDE_INSETS = 31;
    public static final int PHASE_CLIENT_HANDLE_SHOW_INSETS = 30;
    public static final int PHASE_CLIENT_HIDE_INSETS = 29;
    public static final int PHASE_CLIENT_INSETS_CONSUMER_NOTIFY_HIDDEN = 38;
    public static final int PHASE_CLIENT_INSETS_CONSUMER_REQUEST_SHOW = 36;
    public static final int PHASE_CLIENT_REQUEST_IME_SHOW = 37;
    public static final int PHASE_CLIENT_SHOW_INSETS = 28;
    public static final int PHASE_CLIENT_VIEW_SERVED = 1;
    public static final int PHASE_IME_HIDE_SOFT_INPUT = 14;
    public static final int PHASE_IME_HIDE_WINDOW = 45;
    public static final int PHASE_IME_ON_SHOW_SOFT_INPUT_TRUE = 15;
    public static final int PHASE_IME_PRIVILEGED_OPERATIONS = 46;
    public static final int PHASE_IME_SHOW_SOFT_INPUT = 13;
    public static final int PHASE_IME_SHOW_WINDOW = 44;
    public static final int PHASE_IME_WRAPPER = 11;
    public static final int PHASE_IME_WRAPPER_DISPATCH = 12;
    public static final int PHASE_NOT_SET = 0;
    public static final int PHASE_SERVER_ACCESSIBILITY = 4;
    public static final int PHASE_SERVER_APPLY_IME_VISIBILITY = 17;
    public static final int PHASE_SERVER_CLIENT_FOCUSED = 3;
    public static final int PHASE_SERVER_CLIENT_KNOWN = 2;
    public static final int PHASE_SERVER_CURRENT_ACTIVE_IME = 47;
    public static final int PHASE_SERVER_HAS_IME = 9;
    public static final int PHASE_SERVER_HIDE_IMPLICIT = 6;
    public static final int PHASE_SERVER_HIDE_NOT_ALWAYS = 7;
    public static final int PHASE_SERVER_SHOULD_HIDE = 10;
    public static final int PHASE_SERVER_SYSTEM_READY = 5;
    public static final int PHASE_SERVER_WAIT_IME = 8;
    public static final int PHASE_WM_ABORT_SHOW_IME_POST_LAYOUT = 43;
    public static final int PHASE_WM_ANIMATION_CREATE = 26;
    public static final int PHASE_WM_ANIMATION_RUNNING = 27;
    public static final int PHASE_WM_HAS_IME_INSETS_CONTROL_TARGET = 20;
    public static final int PHASE_WM_REMOTE_INSETS_CONTROLLER = 25;
    public static final int PHASE_WM_REMOTE_INSETS_CONTROL_TARGET_HIDE_INSETS = 24;
    public static final int PHASE_WM_REMOTE_INSETS_CONTROL_TARGET_SHOW_INSETS = 23;
    public static final int PHASE_WM_SHOW_IME_READY = 19;
    public static final int PHASE_WM_SHOW_IME_RUNNER = 18;
    public static final int PHASE_WM_WINDOW_INSETS_CONTROL_TARGET_HIDE_INSETS = 22;
    public static final int PHASE_WM_WINDOW_INSETS_CONTROL_TARGET_SHOW_INSETS = 21;
    public static final int STATUS_CANCEL = 2;
    public static final int STATUS_FAIL = 3;
    public static final int STATUS_RUN = 1;
    public static final int STATUS_SUCCESS = 4;
    public static final int STATUS_TIMEOUT = 5;
    public static final String TAG = "ImeTracker";
    public static final String TOKEN_NONE = "TOKEN_NONE";
    public static final int TYPE_HIDE = 2;
    public static final int TYPE_SHOW = 1;
    public static final int TYPE_USER = 3;
    public static final boolean DEBUG_IME_VISIBILITY = SystemProperties.getBoolean("persist.debug.imf_event", false);
    public static final ImeTracker LOGGER = new AnonymousClass1();
    public static final ImeJankTracker JANK_TRACKER = new ImeJankTracker();
    public static final ImeLatencyTracker LATENCY_TRACKER = new ImeLatencyTracker();

    public interface InputMethodJankContext {
        Context getDisplayContext();

        String getHostPackageName();

        SurfaceControl getTargetSurfaceControl();
    }

    public interface InputMethodLatencyContext {
        Context getAppContext();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Origin {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Phase {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    void onCancelled(Token token, int i);

    void onDispatched(Token token);

    void onFailed(Token token, int i);

    void onHidden(Token token);

    void onProgress(Token token, int i);

    void onShown(Token token);

    Token onStart(String str, int i, int i2, int i3, int i4, boolean z);

    void onTodo(Token token, int i);

    void onUserFinished(Token token, boolean z);

    default Token onStart(int type, int origin, int reason, boolean fromUser) {
        return onStart(Process.myProcessName(), Process.myUid(), type, origin, reason, fromUser);
    }

    static boolean isFromUser(View view) {
        Handler handler;
        ViewRootImpl viewRootImpl;
        return (view == null || (handler = view.getHandler()) == null || handler.getLooper() == null || !handler.getLooper().isCurrentThread() || (viewRootImpl = view.getViewRootImpl()) == null || !viewRootImpl.isHandlingPointerEvent()) ? false : true;
    }

    static ImeTracker forLogging() {
        return LOGGER;
    }

    static ImeJankTracker forJank() {
        return JANK_TRACKER;
    }

    static ImeLatencyTracker forLatency() {
        return LATENCY_TRACKER;
    }

    /* renamed from: android.view.inputmethod.ImeTracker$1, reason: invalid class name */
    class AnonymousClass1 implements ImeTracker {
        private boolean mLogProgress;
        private boolean mLogStackTrace;

        AnonymousClass1() {
            reloadSystemProperties();
            SystemProperties.addChangeCallback(new Runnable() { // from class: android.view.inputmethod.ImeTracker$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ImeTracker.AnonymousClass1.this.reloadSystemProperties();
                }
            });
        }

        @Override // android.view.inputmethod.ImeTracker
        public Token onStart(String component, int uid, int type, int origin, int reason, boolean fromUser) {
            String tag = Token.createTag(component);
            Token token = IInputMethodManagerGlobalInvoker.onStart(tag, uid, type, origin, reason, fromUser);
            Log.i(ImeTracker.TAG, token.mTag + ": " + getOnStartPrefix(type) + " at " + Debug.originToString(origin) + " reason " + InputMethodDebug.softInputDisplayReasonToString(reason) + " fromUser " + fromUser, this.mLogStackTrace ? new Throwable() : null);
            return token;
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onProgress(Token token, int phase) {
            if (token == null) {
                return;
            }
            IInputMethodManagerGlobalInvoker.onProgress(token.mBinder, phase);
            if (this.mLogProgress) {
                Log.i(ImeTracker.TAG, token.mTag + ": onProgress at " + Debug.phaseToString(phase));
            }
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onFailed(Token token, int phase) {
            if (token == null) {
                return;
            }
            IInputMethodManagerGlobalInvoker.onFailed(token, phase);
            Log.i(ImeTracker.TAG, token.mTag + ": onFailed at " + Debug.phaseToString(phase));
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onTodo(Token token, int phase) {
            if (token == null) {
                return;
            }
            Log.i(ImeTracker.TAG, token.mTag + ": onTodo at " + Debug.phaseToString(phase));
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onCancelled(Token token, int phase) {
            if (token == null) {
                return;
            }
            IInputMethodManagerGlobalInvoker.onCancelled(token, phase);
            Log.i(ImeTracker.TAG, token.mTag + ": onCancelled at " + Debug.phaseToString(phase));
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onShown(Token token) {
            if (token == null) {
                return;
            }
            IInputMethodManagerGlobalInvoker.onShown(token);
            Log.i(ImeTracker.TAG, token.mTag + ": onShown");
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onHidden(Token token) {
            if (token == null) {
                return;
            }
            IInputMethodManagerGlobalInvoker.onHidden(token);
            Log.i(ImeTracker.TAG, token.mTag + ": onHidden");
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onDispatched(Token token) {
            if (token == null) {
                return;
            }
            IInputMethodManagerGlobalInvoker.onDispatched(token);
            Log.i(ImeTracker.TAG, token.mTag + ": onDispatched");
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onUserFinished(Token token, boolean shown) {
            if (token == null) {
                return;
            }
            Log.i(ImeTracker.TAG, token.mTag + ": onUserFinished " + (shown ? "shown" : "hidden"));
        }

        private static String getOnStartPrefix(int type) {
            switch (type) {
                case 1:
                    return "onRequestShow";
                case 2:
                    return "onRequestHide";
                case 3:
                    return "onRequestUser";
                default:
                    return "onRequestUnknown";
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reloadSystemProperties() {
            this.mLogProgress = SystemProperties.getBoolean("persist.debug.imetracker", false);
            this.mLogStackTrace = SystemProperties.getBoolean("persist.debug.imerequest.logstacktrace", false);
        }
    }

    public static final class Token implements Parcelable {
        public static final Parcelable.Creator<Token> CREATOR = new Parcelable.Creator<Token>() { // from class: android.view.inputmethod.ImeTracker.Token.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Token createFromParcel(Parcel in) {
                return new Token(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Token[] newArray(int size) {
                return new Token[size];
            }
        };
        private static IBinder sEmptyBinder;
        private final IBinder mBinder;
        private final String mTag;

        public Token(IBinder binder, String tag) {
            this.mBinder = binder;
            this.mTag = tag;
        }

        private Token(Parcel in) {
            this.mBinder = in.readStrongBinder();
            this.mTag = in.readString8();
        }

        public IBinder getBinder() {
            return this.mBinder;
        }

        public String getTag() {
            return this.mTag;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String createTag(String component) {
            return component + ":" + Integer.toHexString(ThreadLocalRandom.current().nextInt());
        }

        public static Token empty() {
            String tag = createTag(Process.myProcessName());
            return empty(tag);
        }

        static Token empty(String tag) {
            return new Token(getEmptyBinder(), tag);
        }

        private static IBinder getEmptyBinder() {
            if (sEmptyBinder == null) {
                sEmptyBinder = new Binder();
            }
            return sEmptyBinder;
        }

        public String toString() {
            return super.toString() + "(tag: " + this.mTag + NavigationBarInflaterView.KEY_CODE_END;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeStrongBinder(this.mBinder);
            dest.writeString8(this.mTag);
        }
    }

    public static final class Debug {
        private static final Map<Integer, String> sTypes = getFieldMapping(ImeTracker.class, "TYPE_");
        private static final Map<Integer, String> sStatus = getFieldMapping(ImeTracker.class, "STATUS_");
        private static final Map<Integer, String> sOrigins = getFieldMapping(ImeTracker.class, "ORIGIN_");
        private static final Map<Integer, String> sPhases = getFieldMapping(ImeTracker.class, "PHASE_");

        public static String typeToString(int type) {
            return sTypes.getOrDefault(Integer.valueOf(type), "TYPE_" + type);
        }

        public static String statusToString(int status) {
            return sStatus.getOrDefault(Integer.valueOf(status), "STATUS_" + status);
        }

        public static String originToString(int origin) {
            return sOrigins.getOrDefault(Integer.valueOf(origin), "ORIGIN_" + origin);
        }

        public static String phaseToString(int phase) {
            return sPhases.getOrDefault(Integer.valueOf(phase), "PHASE_" + phase);
        }

        private static Map<Integer, String> getFieldMapping(Class<?> cls, final String fieldPrefix) {
            return (Map) Arrays.stream(cls.getDeclaredFields()).filter(new Predicate() { // from class: android.view.inputmethod.ImeTracker$Debug$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean startsWith;
                    startsWith = ((Field) obj).getName().startsWith(fieldPrefix);
                    return startsWith;
                }
            }).collect(Collectors.toMap(new Function() { // from class: android.view.inputmethod.ImeTracker$Debug$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    int fieldValue;
                    fieldValue = ImeTracker.Debug.getFieldValue((Field) obj);
                    return Integer.valueOf(fieldValue);
                }
            }, new Function() { // from class: android.view.inputmethod.ImeTracker$Debug$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((Field) obj).getName();
                }
            }));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int getFieldValue(Field field) {
            try {
                return field.getInt(null);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static final class ImeJankTracker {
        private ImeJankTracker() {
        }

        public void onRequestAnimation(InputMethodJankContext inputMethodJankContext, int i, boolean z) {
            int imeInsetsCujFromAnimation = getImeInsetsCujFromAnimation(i);
            if (inputMethodJankContext.getDisplayContext() == null || inputMethodJankContext.getTargetSurfaceControl() == null || imeInsetsCujFromAnimation == -1) {
                return;
            }
            InteractionJankMonitor.getInstance().begin(InteractionJankMonitor.Configuration.Builder.withSurface(imeInsetsCujFromAnimation, inputMethodJankContext.getDisplayContext(), inputMethodJankContext.getTargetSurfaceControl()).setTag(String.format(Locale.US, "%d@%d@%s", Integer.valueOf(i), Integer.valueOf(!z ? 1 : 0), inputMethodJankContext.getHostPackageName())));
        }

        public void onCancelAnimation(int animType) {
            int cujType = getImeInsetsCujFromAnimation(animType);
            if (cujType != -1) {
                InteractionJankMonitor.getInstance().cancel(cujType);
            }
        }

        public void onFinishAnimation(int animType) {
            int cujType = getImeInsetsCujFromAnimation(animType);
            if (cujType != -1) {
                InteractionJankMonitor.getInstance().end(cujType);
            }
        }

        private static int getImeInsetsCujFromAnimation(int animType) {
            switch (animType) {
                case 0:
                    return 80;
                case 1:
                    return 81;
                default:
                    return -1;
            }
        }
    }

    public static final class ImeLatencyTracker {
        private ImeLatencyTracker() {
        }

        private boolean shouldMonitorLatency(int reason) {
            return reason == 1 || reason == 4 || reason == 39 || reason == 26 || reason == 28 || reason == 3 || reason == 5;
        }

        public void onRequestShow(Token token, int origin, int reason, InputMethodLatencyContext latencyContext) {
            if (shouldMonitorLatency(reason)) {
                LatencyTracker.getInstance(latencyContext.getAppContext()).onActionStart(20, InputMethodDebug.softInputDisplayReasonToString(reason));
            }
        }

        public void onRequestHide(Token token, int origin, int reason, InputMethodLatencyContext latencyContext) {
            if (shouldMonitorLatency(reason)) {
                LatencyTracker.getInstance(latencyContext.getAppContext()).onActionStart(21, InputMethodDebug.softInputDisplayReasonToString(reason));
            }
        }

        public void onShowFailed(Token token, int phase, InputMethodLatencyContext latencyContext) {
            onShowCancelled(token, phase, latencyContext);
        }

        public void onHideFailed(Token token, int phase, InputMethodLatencyContext latencyContext) {
            onHideCancelled(token, phase, latencyContext);
        }

        public void onShowCancelled(Token token, int phase, InputMethodLatencyContext latencyContext) {
            LatencyTracker.getInstance(latencyContext.getAppContext()).lambda$onActionStart$1(20);
        }

        public void onHideCancelled(Token token, int phase, InputMethodLatencyContext latencyContext) {
            LatencyTracker.getInstance(latencyContext.getAppContext()).lambda$onActionStart$1(21);
        }

        public void onShown(Token token, InputMethodLatencyContext latencyContext) {
            LatencyTracker.getInstance(latencyContext.getAppContext()).onActionEnd(20);
        }

        public void onHidden(Token token, InputMethodLatencyContext latencyContext) {
            LatencyTracker.getInstance(latencyContext.getAppContext()).onActionEnd(21);
        }
    }
}
