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
    public static final ImeJankTracker JANK_TRACKER;
    public static final ImeLatencyTracker LATENCY_TRACKER;
    public static final int ORIGIN_CLIENT = 5;
    public static final int ORIGIN_IME = 7;
    public static final int ORIGIN_SERVER = 6;
    public static final int ORIGIN_WM_SHELL = 8;
    public static final int PHASE_CLIENT_ALREADY_HIDDEN = 65;
    public static final int PHASE_CLIENT_ANIMATION_CANCEL = 40;
    public static final int PHASE_CLIENT_ANIMATION_FINISHED_HIDE = 42;
    public static final int PHASE_CLIENT_ANIMATION_FINISHED_SHOW = 41;
    public static final int PHASE_CLIENT_ANIMATION_RUNNING = 39;
    public static final int PHASE_CLIENT_APPLY_ANIMATION = 32;
    public static final int PHASE_CLIENT_COLLECT_SOURCE_CONTROLS = 35;
    public static final int PHASE_CLIENT_CONTROL_ANIMATION = 33;
    public static final int PHASE_CLIENT_HANDLE_DISPATCH_IME_VISIBILITY_CHANGED = 51;
    public static final int PHASE_CLIENT_HANDLE_HIDE_INSETS = 31;
    public static final int PHASE_CLIENT_HANDLE_SET_IME_VISIBILITY = 58;
    public static final int PHASE_CLIENT_HANDLE_SHOW_INSETS = 30;
    public static final int PHASE_CLIENT_HIDE_INSETS = 29;
    public static final int PHASE_CLIENT_INSETS_CONSUMER_NOTIFY_HIDDEN = 38;
    public static final int PHASE_CLIENT_INSETS_CONSUMER_REQUEST_SHOW = 36;
    public static final int PHASE_CLIENT_NOTIFY_IME_VISIBILITY_CHANGED = 52;
    public static final int PHASE_CLIENT_NO_ONGOING_USER_ANIMATION = 61;
    public static final int PHASE_CLIENT_ON_CONTROLS_CHANGED = 76;
    public static final int PHASE_CLIENT_REPORT_REQUESTED_VISIBLE_TYPES = 48;
    public static final int PHASE_CLIENT_REQUEST_IME_SHOW = 37;
    public static final int PHASE_CLIENT_SET_IME_VISIBILITY = 59;
    public static final int PHASE_CLIENT_SHOW_INSETS = 28;
    public static final int PHASE_CLIENT_UPDATE_ANIMATING_TYPES = 71;
    public static final int PHASE_CLIENT_UPDATE_REQUESTED_VISIBLE_TYPES = 53;
    public static final int PHASE_CLIENT_VIEW_HANDLER_AVAILABLE = 66;
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
    public static final int PHASE_SERVER_CLIENT_INVOKER = 78;
    public static final int PHASE_SERVER_CLIENT_KNOWN = 2;
    public static final int PHASE_SERVER_CURRENT_ACTIVE_IME = 47;
    public static final int PHASE_SERVER_HAS_IME = 9;
    public static final int PHASE_SERVER_HIDE_IMPLICIT = 6;
    public static final int PHASE_SERVER_HIDE_NOT_ALWAYS = 7;
    public static final int PHASE_SERVER_IME_INVOKER = 77;
    public static final int PHASE_SERVER_SET_VISIBILITY_ON_FOCUSED_WINDOW = 57;
    public static final int PHASE_SERVER_SHOULD_HIDE = 10;
    public static final int PHASE_SERVER_SYSTEM_READY = 5;
    public static final int PHASE_SERVER_UPDATE_CLIENT_VISIBILITY = 67;
    public static final int PHASE_SERVER_WAIT_IME = 8;
    public static final int PHASE_WM_ABORT_SHOW_IME_POST_LAYOUT = 43;
    public static final int PHASE_WM_ANIMATION_CREATE = 26;
    public static final int PHASE_WM_ANIMATION_RUNNING = 27;
    public static final int PHASE_WM_DISPATCH_IME_REQUESTED_CHANGED = 60;
    public static final int PHASE_WM_DISPLAY_IME_CONTROLLER_SET_IME_REQUESTED_VISIBLE = 68;
    public static final int PHASE_WM_GET_CONTROL_WITH_LEASH = 55;
    public static final int PHASE_WM_HAS_IME_INSETS_CONTROL_TARGET = 20;
    public static final int PHASE_WM_INVOKING_IME_REQUESTED_LISTENER = 64;
    public static final int PHASE_WM_NOTIFY_HIDE_ANIMATION_FINISHED = 74;
    public static final int PHASE_WM_NOTIFY_IME_VISIBILITY_CHANGED_FROM_CLIENT = 62;
    public static final int PHASE_WM_POSTING_CHANGED_IME_VISIBILITY = 63;
    public static final int PHASE_WM_POST_LAYOUT_NOTIFY_CONTROLS_CHANGED = 50;
    public static final int PHASE_WM_REMOTE_INSETS_CONTROLLER = 25;
    public static final int PHASE_WM_REMOTE_INSETS_CONTROL_TARGET_HIDE_INSETS = 24;
    public static final int PHASE_WM_REMOTE_INSETS_CONTROL_TARGET_SET_REQUESTED_VISIBILITY = 54;
    public static final int PHASE_WM_REMOTE_INSETS_CONTROL_TARGET_SHOW_INSETS = 23;
    public static final int PHASE_WM_REQUESTED_VISIBLE_TYPES_NOT_CHANGED = 70;
    public static final int PHASE_WM_SET_REMOTE_TARGET_IME_VISIBILITY = 49;
    public static final int PHASE_WM_SHOW_IME_READY = 19;
    public static final int PHASE_WM_SHOW_IME_RUNNER = 18;
    public static final int PHASE_WM_UPDATE_ANIMATING_TYPES = 72;
    public static final int PHASE_WM_UPDATE_DISPLAY_WINDOW_ANIMATING_TYPES = 75;
    public static final int PHASE_WM_UPDATE_DISPLAY_WINDOW_REQUESTED_VISIBLE_TYPES = 69;
    public static final int PHASE_WM_UPDATE_REQUESTED_VISIBLE_TYPES = 56;
    public static final int PHASE_WM_WINDOW_ANIMATING_TYPES_CHANGED = 73;
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

    static {
        JANK_TRACKER = new ImeJankTracker();
        LATENCY_TRACKER = new ImeLatencyTracker();
    }

    default Token onStart(int i, int i2, int i3, boolean z) {
        return onStart(Process.myProcessName(), Process.myUid(), i, i2, i3, z);
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
                    this.f$0.reloadSystemProperties();
                }
            });
        }

        @Override // android.view.inputmethod.ImeTracker
        public Token onStart(String str, int i, int i2, int i3, int i4, boolean z) {
            Token tokenOnStart = IInputMethodManagerGlobalInvoker.onStart(Token.createTag(str), i, i2, i3, i4, z);
            Log.i(ImeTracker.TAG, tokenOnStart.mTag + ": " + getOnStartPrefix(i2) + " at " + Debug.originToString(i3) + " reason " + InputMethodDebug.softInputDisplayReasonToString(i4) + " fromUser " + z, this.mLogStackTrace ? new Throwable() : null);
            return tokenOnStart;
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onProgress(Token token, int i) {
            if (token == null) {
                return;
            }
            IInputMethodManagerGlobalInvoker.onProgress(token.mBinder, i);
            if (this.mLogProgress) {
                Log.i(ImeTracker.TAG, token.mTag + ": onProgress at " + Debug.phaseToString(i));
            }
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onFailed(Token token, int i) {
            if (token == null) {
                return;
            }
            IInputMethodManagerGlobalInvoker.onFailed(token, i);
            Log.i(ImeTracker.TAG, token.mTag + ": onFailed at " + Debug.phaseToString(i));
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onTodo(Token token, int i) {
            if (token == null) {
                return;
            }
            Log.i(ImeTracker.TAG, token.mTag + ": onTodo at " + Debug.phaseToString(i));
        }

        @Override // android.view.inputmethod.ImeTracker
        public void onCancelled(Token token, int i) {
            if (token == null) {
                return;
            }
            IInputMethodManagerGlobalInvoker.onCancelled(token, i);
            Log.i(ImeTracker.TAG, token.mTag + ": onCancelled at " + Debug.phaseToString(i));
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
        public void onUserFinished(Token token, boolean z) {
            if (token == null) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(token.mTag);
            sb.append(": onUserFinished ");
            sb.append(z ? "shown" : "hidden");
            Log.i(ImeTracker.TAG, sb.toString());
        }

        private static String getOnStartPrefix(int i) {
            if (i == 1) {
                return "onRequestShow";
            }
            if (i == 2) {
                return "onRequestHide";
            }
            if (i == 3) {
                return "onRequestUser";
            }
            return "onRequestUnknown";
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
            public Token createFromParcel(Parcel parcel) {
                return new Token(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Token[] newArray(int i) {
                return new Token[i];
            }
        };
        private static IBinder sEmptyBinder;
        private final IBinder mBinder;
        private final String mTag;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Token(IBinder iBinder, String str) {
            this.mBinder = iBinder;
            this.mTag = str;
        }

        private Token(Parcel parcel) {
            this.mBinder = parcel.readStrongBinder();
            this.mTag = parcel.readString8();
        }

        public IBinder getBinder() {
            return this.mBinder;
        }

        public String getTag() {
            return this.mTag;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String createTag(String str) {
            return str + ":" + Integer.toHexString(ThreadLocalRandom.current().nextInt());
        }

        public static Token empty() {
            return empty(createTag(Process.myProcessName()));
        }

        static Token empty(String str) {
            return new Token(getEmptyBinder(), str);
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
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeStrongBinder(this.mBinder);
            parcel.writeString8(this.mTag);
        }
    }

    public static final class Debug {
        private static final Map<Integer, String> sTypes = getFieldMapping(ImeTracker.class, "TYPE_");
        private static final Map<Integer, String> sStatus = getFieldMapping(ImeTracker.class, "STATUS_");
        private static final Map<Integer, String> sOrigins = getFieldMapping(ImeTracker.class, "ORIGIN_");
        private static final Map<Integer, String> sPhases = getFieldMapping(ImeTracker.class, "PHASE_");

        public static String typeToString(int i) {
            return sTypes.getOrDefault(Integer.valueOf(i), "TYPE_" + i);
        }

        public static String statusToString(int i) {
            return sStatus.getOrDefault(Integer.valueOf(i), "STATUS_" + i);
        }

        public static String originToString(int i) {
            return sOrigins.getOrDefault(Integer.valueOf(i), "ORIGIN_" + i);
        }

        public static String phaseToString(int i) {
            return sPhases.getOrDefault(Integer.valueOf(i), "PHASE_" + i);
        }

        private static Map<Integer, String> getFieldMapping(Class<?> cls, final String str) {
            return (Map) Arrays.stream(cls.getDeclaredFields()).filter(new Predicate() { // from class: android.view.inputmethod.ImeTracker$Debug$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((Field) obj).getName().startsWith(str);
                }
            }).collect(Collectors.toMap(new Function() { // from class: android.view.inputmethod.ImeTracker$Debug$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(ImeTracker.Debug.getFieldValue((Field) obj));
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
        private static int getImeInsetsCujFromAnimation(int i) {
            if (i != 0) {
                return i != 1 ? -1 : 81;
            }
            return 80;
        }

        private ImeJankTracker() {
        }

        public void onRequestAnimation(InputMethodJankContext inputMethodJankContext, int i, boolean z) {
            int imeInsetsCujFromAnimation = getImeInsetsCujFromAnimation(i);
            if (inputMethodJankContext.getDisplayContext() == null || inputMethodJankContext.getTargetSurfaceControl() == null || imeInsetsCujFromAnimation == -1) {
                return;
            }
            InteractionJankMonitor.getInstance().begin(InteractionJankMonitor.Configuration.Builder.withSurface(imeInsetsCujFromAnimation, inputMethodJankContext.getDisplayContext(), inputMethodJankContext.getTargetSurfaceControl(), inputMethodJankContext.getDisplayContext().getMainThreadHandler()).setTag(String.format(Locale.US, "%d@%d@%s", Integer.valueOf(i), Integer.valueOf(!z ? 1 : 0), inputMethodJankContext.getHostPackageName())));
        }

        public void onCancelAnimation(int i) {
            int imeInsetsCujFromAnimation = getImeInsetsCujFromAnimation(i);
            if (imeInsetsCujFromAnimation != -1) {
                InteractionJankMonitor.getInstance().cancel(imeInsetsCujFromAnimation);
            }
        }

        public void onFinishAnimation(int i) {
            int imeInsetsCujFromAnimation = getImeInsetsCujFromAnimation(i);
            if (imeInsetsCujFromAnimation != -1) {
                InteractionJankMonitor.getInstance().end(imeInsetsCujFromAnimation);
            }
        }
    }

    public static final class ImeLatencyTracker {
        private boolean shouldMonitorLatency(int i) {
            return i == 1 || i == 4 || i == 39 || i == 26 || i == 28 || i == 3 || i == 5;
        }

        private ImeLatencyTracker() {
        }

        public void onRequestShow(Token token, int i, int i2, InputMethodLatencyContext inputMethodLatencyContext) {
            if (shouldMonitorLatency(i2)) {
                LatencyTracker.getInstance(inputMethodLatencyContext.getAppContext()).onActionStart(20, InputMethodDebug.softInputDisplayReasonToString(i2));
            }
        }

        public void onRequestHide(Token token, int i, int i2, InputMethodLatencyContext inputMethodLatencyContext) {
            if (shouldMonitorLatency(i2)) {
                LatencyTracker.getInstance(inputMethodLatencyContext.getAppContext()).onActionStart(21, InputMethodDebug.softInputDisplayReasonToString(i2));
            }
        }

        public void onShowFailed(Token token, int i, InputMethodLatencyContext inputMethodLatencyContext) {
            onShowCancelled(token, i, inputMethodLatencyContext);
        }

        public void onHideFailed(Token token, int i, InputMethodLatencyContext inputMethodLatencyContext) {
            onHideCancelled(token, i, inputMethodLatencyContext);
        }

        public void onShowCancelled(Token token, int i, InputMethodLatencyContext inputMethodLatencyContext) {
            LatencyTracker.getInstance(inputMethodLatencyContext.getAppContext()).lambda$onActionStart$1(20);
        }

        public void onHideCancelled(Token token, int i, InputMethodLatencyContext inputMethodLatencyContext) {
            LatencyTracker.getInstance(inputMethodLatencyContext.getAppContext()).lambda$onActionStart$1(21);
        }

        public void onShown(Token token, InputMethodLatencyContext inputMethodLatencyContext) {
            LatencyTracker.getInstance(inputMethodLatencyContext.getAppContext()).onActionEnd(20);
        }

        public void onHidden(Token token, InputMethodLatencyContext inputMethodLatencyContext) {
            LatencyTracker.getInstance(inputMethodLatencyContext.getAppContext()).onActionEnd(21);
        }
    }
}
