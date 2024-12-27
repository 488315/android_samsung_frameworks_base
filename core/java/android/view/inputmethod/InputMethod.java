package android.view.inputmethod;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.InputChannel;
import android.view.MotionEvent;
import android.view.inputmethod.ImeTracker;
import com.android.internal.inputmethod.IConnectionlessHandwritingCallback;
import com.android.internal.inputmethod.IInlineSuggestionsRequestCallback;
import com.android.internal.inputmethod.IInputMethod;
import com.android.internal.inputmethod.InlineSuggestionsRequestInfo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/* loaded from: classes4.dex */
public interface InputMethod {
    public static final String SERVICE_INTERFACE = "android.view.InputMethod";
    public static final String SERVICE_META_DATA = "android.view.im";
    public static final int SHOW_EXPLICIT = 1;

    @Deprecated
    public static final int SHOW_FORCED = 2;
    public static final String TAG = "InputMethod";

    public interface SessionCallback {
        void sessionCreated(InputMethodSession inputMethodSession);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowFlags {
    }

    void attachToken(IBinder iBinder);

    void bindInput(InputBinding inputBinding);

    void changeInputMethodSubtype(InputMethodSubtype inputMethodSubtype);

    void createSession(SessionCallback sessionCallback);

    void hideSoftInput(int i, ResultReceiver resultReceiver);

    void restartInput(InputConnection inputConnection, EditorInfo editorInfo);

    void revokeSession(InputMethodSession inputMethodSession);

    void setSessionEnabled(InputMethodSession inputMethodSession, boolean z);

    void showSoftInput(int i, ResultReceiver resultReceiver);

    void startInput(InputConnection inputConnection, EditorInfo editorInfo);

    void unbindInput();

    default void initializeInternal(IInputMethod.InitParams params) {
        attachToken(params.token);
    }

    default void onCreateInlineSuggestionsRequest(InlineSuggestionsRequestInfo requestInfo, IInlineSuggestionsRequestCallback cb) {
        try {
            cb.onInlineSuggestionsUnsupported();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to call onInlineSuggestionsUnsupported.", e);
        }
    }

    default void dispatchStartInput(InputConnection inputConnection, IInputMethod.StartInputParams params) {
        if (params.restarting) {
            restartInput(inputConnection, params.editorInfo);
        } else {
            startInput(inputConnection, params.editorInfo);
        }
    }

    default void onNavButtonFlagsChanged(int navButtonFlags) {
    }

    default void showSoftInputWithToken(int flags, ResultReceiver resultReceiver, IBinder showInputToken, ImeTracker.Token statsToken) {
        showSoftInput(flags, resultReceiver);
    }

    default void hideSoftInputWithToken(int flags, ResultReceiver resultReceiver, IBinder hideInputToken, ImeTracker.Token statsToken) {
        hideSoftInput(flags, resultReceiver);
    }

    default void canStartStylusHandwriting(int requestId, IConnectionlessHandwritingCallback connectionlessCallback, CursorAnchorInfo cursorAnchorInfo, boolean isConnectionlessForDelegation) {
    }

    default void updateEditorToolType(int toolType) {
    }

    default void startStylusHandwriting(int requestId, InputChannel channel, List<MotionEvent> events) {
    }

    default void commitHandwritingDelegationTextIfAvailable() {
    }

    default void discardHandwritingDelegationText() {
    }

    default void initInkWindow() {
    }

    default void finishStylusHandwriting() {
    }

    default void removeStylusHandwritingWindow() {
    }

    default void setStylusWindowIdleTimeoutForTest(long timeout) {
    }

    default void minimizeSoftInput(int height) {
    }

    default void unMinimizeSoftInput() {
    }
}
