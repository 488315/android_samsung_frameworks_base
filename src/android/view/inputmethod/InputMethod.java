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

    default void canStartStylusHandwriting(int i, IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback, CursorAnchorInfo cursorAnchorInfo, boolean z) {
    }

    void changeInputMethodSubtype(InputMethodSubtype inputMethodSubtype);

    default void commitHandwritingDelegationTextIfAvailable() {
    }

    void createSession(SessionCallback sessionCallback);

    default void discardHandwritingDelegationText() {
    }

    default void finishStylusHandwriting() {
    }

    void hideSoftInput(int i, ResultReceiver resultReceiver);

    default void initInkWindow() {
    }

    default void minimizeSoftInput(int i) {
    }

    default void onNavButtonFlagsChanged(int i) {
    }

    default void removeStylusHandwritingWindow() {
    }

    void restartInput(InputConnection inputConnection, EditorInfo editorInfo);

    void revokeSession(InputMethodSession inputMethodSession);

    void setSessionEnabled(InputMethodSession inputMethodSession, boolean z);

    default void setStylusWindowIdleTimeoutForTest(long j) {
    }

    void showSoftInput(int i, ResultReceiver resultReceiver);

    void startInput(InputConnection inputConnection, EditorInfo editorInfo);

    default void startStylusHandwriting(int i, InputChannel inputChannel, List<MotionEvent> list) {
    }

    default void unMinimizeSoftInput() {
    }

    void unbindInput();

    default void updateEditorToolType(int i) {
    }

    default void initializeInternal(IInputMethod.InitParams initParams) {
        attachToken(initParams.token);
    }

    default void onCreateInlineSuggestionsRequest(InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) {
        try {
            iInlineSuggestionsRequestCallback.onInlineSuggestionsUnsupported();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to call onInlineSuggestionsUnsupported.", e);
        }
    }

    default void dispatchStartInput(InputConnection inputConnection, IInputMethod.StartInputParams startInputParams) {
        if (startInputParams.restarting) {
            restartInput(inputConnection, startInputParams.editorInfo);
        } else {
            startInput(inputConnection, startInputParams.editorInfo);
        }
    }

    default void showSoftInputWithToken(int i, ResultReceiver resultReceiver, IBinder iBinder, ImeTracker.Token token) {
        showSoftInput(i, resultReceiver);
    }

    default void hideSoftInputWithToken(int i, ResultReceiver resultReceiver, IBinder iBinder, ImeTracker.Token token) {
        hideSoftInput(i, resultReceiver);
    }
}
