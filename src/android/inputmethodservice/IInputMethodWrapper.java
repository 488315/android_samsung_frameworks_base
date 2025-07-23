package android.inputmethodservice;

import android.Manifest;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.InputChannel;
import android.view.MotionEvent;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputBinding;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodSession;
import android.view.inputmethod.InputMethodSubtype;
import com.android.internal.inputmethod.CancellationGroup;
import com.android.internal.inputmethod.IConnectionlessHandwritingCallback;
import com.android.internal.inputmethod.IInlineSuggestionsRequestCallback;
import com.android.internal.inputmethod.IInputMethod;
import com.android.internal.inputmethod.IInputMethodSession;
import com.android.internal.inputmethod.IInputMethodSessionCallback;
import com.android.internal.inputmethod.IRemoteInputConnection;
import com.android.internal.inputmethod.InlineSuggestionsRequestInfo;
import com.android.internal.os.HandlerCaller;
import com.android.internal.os.SomeArgs;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
class IInputMethodWrapper extends IInputMethod.Stub implements HandlerCaller.Callback {
    private static final int DO_CAN_START_STYLUS_HANDWRITING = 100;
    private static final int DO_CHANGE_INPUTMETHOD_SUBTYPE = 80;
    private static final int DO_COMMIT_HANDWRITING_DELEGATION_TEXT_IF_AVAILABLE = 170;
    private static final int DO_CREATE_INLINE_SUGGESTIONS_REQUEST = 90;
    private static final int DO_CREATE_SESSION = 40;
    private static final int DO_DISCARD_HANDWRITING_DELEGATION_TEXT = 180;
    private static final int DO_DUMP = 1;
    private static final int DO_FINISH_STYLUS_HANDWRITING = 130;
    private static final int DO_HIDE_SOFT_INPUT = 70;
    private static final int DO_INITIALIZE_INTERNAL = 10;
    private static final int DO_INIT_INK_WINDOW = 120;
    private static final int DO_MINIMIZE_SOFT_INPUT = 200;
    private static final int DO_ON_NAV_BUTTON_FLAGS_CHANGED = 35;
    private static final int DO_REMOVE_STYLUS_HANDWRITING_WINDOW = 150;
    private static final int DO_SET_INPUT_CONTEXT = 20;
    private static final int DO_SET_SESSION_ENABLED = 45;
    private static final int DO_SET_STYLUS_WINDOW_IDLE_TIMEOUT = 160;
    private static final int DO_SHOW_SOFT_INPUT = 60;
    private static final int DO_START_INPUT = 32;
    private static final int DO_START_STYLUS_HANDWRITING = 110;
    private static final int DO_UNSET_INPUT_CONTEXT = 30;
    private static final int DO_UPDATE_TOOL_TYPE = 140;
    private static final String TAG = "InputMethodWrapper";
    private static final int UNDO_MINIMIZE_SOFT_INPUT = 210;
    final HandlerCaller mCaller;
    CancellationGroup mCancellationGroup = null;
    final Context mContext;
    final WeakReference<InputMethod> mInputMethod;
    final WeakReference<InputMethodServiceInternal> mTarget;
    final int mTargetSdkVersion;

    static final class InputMethodSessionCallbackWrapper implements InputMethod.SessionCallback {
        final IInputMethodSessionCallback mCb;
        final InputChannel mChannel;
        final Context mContext;

        InputMethodSessionCallbackWrapper(Context context, InputChannel inputChannel, IInputMethodSessionCallback iInputMethodSessionCallback) {
            this.mContext = context;
            this.mChannel = inputChannel;
            this.mCb = iInputMethodSessionCallback;
        }

        @Override // android.view.inputmethod.InputMethod.SessionCallback
        public void sessionCreated(InputMethodSession inputMethodSession) {
            try {
                if (inputMethodSession != null) {
                    this.mCb.sessionCreated(new IInputMethodSessionWrapper(this.mContext, inputMethodSession, this.mChannel));
                } else {
                    InputChannel inputChannel = this.mChannel;
                    if (inputChannel != null) {
                        inputChannel.dispose();
                    }
                    this.mCb.sessionCreated(null);
                }
            } catch (RemoteException unused) {
            }
        }
    }

    IInputMethodWrapper(InputMethodServiceInternal inputMethodServiceInternal, InputMethod inputMethod) {
        this.mTarget = new WeakReference<>(inputMethodServiceInternal);
        Context applicationContext = inputMethodServiceInternal.getContext().getApplicationContext();
        this.mContext = applicationContext;
        this.mCaller = new HandlerCaller(applicationContext, null, this, true);
        this.mInputMethod = new WeakReference<>(inputMethod);
        this.mTargetSdkVersion = inputMethodServiceInternal.getContext().getApplicationInfo().targetSdkVersion;
    }

    @Override // com.android.internal.os.HandlerCaller.Callback
    public void executeMessage(Message message) {
        InputMethod inputMethod = this.mInputMethod.get();
        InputMethodServiceInternal inputMethodServiceInternal = this.mTarget.get();
        switch (message.what) {
            case 1:
                SomeArgs someArgs = (SomeArgs) message.obj;
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_DUMP")) {
                    FileDescriptor fileDescriptor = (FileDescriptor) someArgs.arg1;
                    PrintWriter printWriter = (PrintWriter) someArgs.arg2;
                    String[] strArr = (String[]) someArgs.arg3;
                    CountDownLatch countDownLatch = (CountDownLatch) someArgs.arg4;
                    try {
                        try {
                            inputMethodServiceInternal.dump(fileDescriptor, printWriter, strArr);
                        } catch (RuntimeException e) {
                            printWriter.println("Exception: " + e);
                        }
                    } finally {
                        countDownLatch.countDown();
                    }
                }
                someArgs.recycle();
                return;
            case 10:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_INITIALIZE_INTERNAL")) {
                    inputMethod.initializeInternal((IInputMethod.InitParams) message.obj);
                    return;
                }
                return;
            case 20:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_SET_INPUT_CONTEXT")) {
                    inputMethod.bindInput((InputBinding) message.obj);
                    return;
                }
                return;
            case 30:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_UNSET_INPUT_CONTEXT")) {
                    inputMethod.unbindInput();
                    return;
                }
                return;
            case 32:
                SomeArgs someArgs2 = (SomeArgs) message.obj;
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_START_INPUT")) {
                    inputMethod.dispatchStartInput((InputConnection) someArgs2.arg1, (IInputMethod.StartInputParams) someArgs2.arg2);
                }
                someArgs2.recycle();
                return;
            case 35:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_ON_NAV_BUTTON_FLAGS_CHANGED")) {
                    inputMethod.onNavButtonFlagsChanged(message.arg1);
                    return;
                }
                return;
            case 40:
                SomeArgs someArgs3 = (SomeArgs) message.obj;
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_CREATE_SESSION")) {
                    inputMethod.createSession(new InputMethodSessionCallbackWrapper(this.mContext, (InputChannel) someArgs3.arg1, (IInputMethodSessionCallback) someArgs3.arg2));
                }
                someArgs3.recycle();
                return;
            case 45:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_SET_SESSION_ENABLED")) {
                    inputMethod.setSessionEnabled((InputMethodSession) message.obj, message.arg1 != 0);
                    return;
                }
                return;
            case 60:
                SomeArgs someArgs4 = (SomeArgs) message.obj;
                ImeTracker.Token token = (ImeTracker.Token) someArgs4.arg3;
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_SHOW_SOFT_INPUT")) {
                    ImeTracker.forLogging().onProgress(token, 12);
                    inputMethod.showSoftInputWithToken(message.arg1, (ResultReceiver) someArgs4.arg2, (IBinder) someArgs4.arg1, token);
                } else {
                    ImeTracker.forLogging().onFailed(token, 12);
                }
                someArgs4.recycle();
                return;
            case 70:
                SomeArgs someArgs5 = (SomeArgs) message.obj;
                ImeTracker.Token token2 = (ImeTracker.Token) someArgs5.arg3;
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_HIDE_SOFT_INPUT")) {
                    ImeTracker.forLogging().onProgress(token2, 12);
                    inputMethod.hideSoftInputWithToken(message.arg1, (ResultReceiver) someArgs5.arg2, (IBinder) someArgs5.arg1, token2);
                } else {
                    ImeTracker.forLogging().onFailed(token2, 12);
                }
                someArgs5.recycle();
                return;
            case 80:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_CHANGE_INPUTMETHOD_SUBTYPE")) {
                    inputMethod.changeInputMethodSubtype((InputMethodSubtype) message.obj);
                    return;
                }
                return;
            case 90:
                SomeArgs someArgs6 = (SomeArgs) message.obj;
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_CREATE_INLINE_SUGGESTIONS_REQUEST")) {
                    inputMethod.onCreateInlineSuggestionsRequest((InlineSuggestionsRequestInfo) someArgs6.arg1, (IInlineSuggestionsRequestCallback) someArgs6.arg2);
                }
                someArgs6.recycle();
                return;
            case 100:
                SomeArgs someArgs7 = (SomeArgs) message.obj;
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_CAN_START_STYLUS_HANDWRITING")) {
                    inputMethod.canStartStylusHandwriting(message.arg1, (IConnectionlessHandwritingCallback) someArgs7.arg1, (CursorAnchorInfo) someArgs7.arg2, message.arg2 != 0);
                }
                someArgs7.recycle();
                return;
            case 110:
                SomeArgs someArgs8 = (SomeArgs) message.obj;
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_START_STYLUS_HANDWRITING")) {
                    inputMethod.startStylusHandwriting(message.arg1, (InputChannel) someArgs8.arg1, (List) someArgs8.arg2);
                }
                someArgs8.recycle();
                return;
            case 120:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_INIT_INK_WINDOW")) {
                    inputMethod.initInkWindow();
                    return;
                }
                return;
            case 130:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_FINISH_STYLUS_HANDWRITING")) {
                    inputMethod.finishStylusHandwriting();
                    return;
                }
                return;
            case 140:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_UPDATE_TOOL_TYPE")) {
                    inputMethod.updateEditorToolType(message.arg1);
                    return;
                }
                return;
            case 150:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_REMOVE_STYLUS_HANDWRITING_WINDOW")) {
                    inputMethod.removeStylusHandwritingWindow();
                    return;
                }
                return;
            case 160:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_SET_STYLUS_WINDOW_IDLE_TIMEOUT")) {
                    inputMethod.setStylusWindowIdleTimeoutForTest(((Long) message.obj).longValue());
                    return;
                }
                return;
            case 170:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_COMMIT_HANDWRITING_DELEGATION_TEXT_IF_AVAILABLE")) {
                    inputMethod.commitHandwritingDelegationTextIfAvailable();
                    return;
                }
                return;
            case 180:
                if (isValid(inputMethod, inputMethodServiceInternal, "DO_DISCARD_HANDWRITING_DELEGATION_TEXT")) {
                    inputMethod.discardHandwritingDelegationText();
                    return;
                }
                return;
            case 200:
                inputMethod.minimizeSoftInput(message.arg1);
                return;
            case 210:
                inputMethod.unMinimizeSoftInput();
                return;
            default:
                Log.w(TAG, "Unhandled message code: " + message.what);
                return;
        }
    }

    @Override // android.os.Binder
    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        InputMethodServiceInternal inputMethodServiceInternal = this.mTarget.get();
        if (inputMethodServiceInternal == null) {
            return;
        }
        if (inputMethodServiceInternal.getContext().checkCallingOrSelfPermission(Manifest.permission.DUMP) != 0) {
            printWriter.println("Permission Denial: can't dump InputMethodManager from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        this.mCaller.getHandler().sendMessageAtFrontOfQueue(this.mCaller.obtainMessageOOOO(1, fileDescriptor, printWriter, strArr, countDownLatch));
        try {
            if (countDownLatch.await(5L, TimeUnit.SECONDS)) {
                return;
            }
            printWriter.println("Timeout waiting for dump");
        } catch (InterruptedException unused) {
            printWriter.println("Interrupted waiting for dump");
        }
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void initializeInternal(IInputMethod.InitParams initParams) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(10, initParams));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void onCreateInlineSuggestionsRequest(InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(90, inlineSuggestionsRequestInfo, iInlineSuggestionsRequestCallback));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void bindInput(InputBinding inputBinding) {
        if (this.mCancellationGroup != null) {
            Log.e(TAG, "bindInput must be paired with unbindInput.");
        }
        this.mCancellationGroup = new CancellationGroup();
        InputBinding inputBinding2 = new InputBinding(new RemoteInputConnection(this.mTarget, IRemoteInputConnection.Stub.asInterface(inputBinding.getConnectionToken()), this.mCancellationGroup), inputBinding);
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(20, inputBinding2));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void unbindInput() {
        CancellationGroup cancellationGroup = this.mCancellationGroup;
        if (cancellationGroup != null) {
            cancellationGroup.cancelAll();
            this.mCancellationGroup = null;
        } else {
            Log.e(TAG, "unbindInput must be paired with bindInput.");
        }
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(30));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void startInput(IInputMethod.StartInputParams startInputParams) {
        if (this.mCancellationGroup == null) {
            Log.e(TAG, "startInput must be called after bindInput.");
            this.mCancellationGroup = new CancellationGroup();
        }
        startInputParams.editorInfo.makeCompatible(this.mTargetSdkVersion);
        RemoteInputConnection remoteInputConnection = startInputParams.remoteInputConnection == null ? null : new RemoteInputConnection(this.mTarget, startInputParams.remoteInputConnection, this.mCancellationGroup);
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(32, remoteInputConnection, startInputParams));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void onNavButtonFlagsChanged(int i) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageI(35, i));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void createSession(InputChannel inputChannel, IInputMethodSessionCallback iInputMethodSessionCallback) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageOO(40, inputChannel, iInputMethodSessionCallback));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void setSessionEnabled(IInputMethodSession iInputMethodSession, boolean z) {
        try {
            InputMethodSession internalInputMethodSession = ((IInputMethodSessionWrapper) iInputMethodSession).getInternalInputMethodSession();
            if (internalInputMethodSession != null) {
                HandlerCaller handlerCaller = this.mCaller;
                handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageIO(45, z ? 1 : 0, internalInputMethodSession));
            } else {
                Log.w(TAG, "Session is already finished: " + iInputMethodSession);
            }
        } catch (ClassCastException e) {
            Log.w(TAG, "Incoming session not of correct type: " + iInputMethodSession, e);
        }
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void showSoftInput(IBinder iBinder, ImeTracker.Token token, int i, ResultReceiver resultReceiver) {
        ImeTracker.forLogging().onProgress(token, 11);
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageIOOO(60, i, iBinder, resultReceiver, token));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void hideSoftInput(IBinder iBinder, ImeTracker.Token token, int i, ResultReceiver resultReceiver) {
        ImeTracker.forLogging().onProgress(token, 11);
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageIOOO(70, i, iBinder, resultReceiver, token));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void changeInputMethodSubtype(InputMethodSubtype inputMethodSubtype) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(80, inputMethodSubtype));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void canStartStylusHandwriting(int i, IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback, CursorAnchorInfo cursorAnchorInfo, boolean z) throws RemoteException {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageIIOO(100, i, z ? 1 : 0, iConnectionlessHandwritingCallback, cursorAnchorInfo));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void updateEditorToolType(int i) throws RemoteException {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageI(140, i));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void startStylusHandwriting(int i, InputChannel inputChannel, List<MotionEvent> list) throws RemoteException {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageIOO(110, i, inputChannel, list));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void commitHandwritingDelegationTextIfAvailable() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(170));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void discardHandwritingDelegationText() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(180));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void initInkWindow() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(120));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void finishStylusHandwriting() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(130));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void removeStylusHandwritingWindow() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessage(150));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void setStylusWindowIdleTimeoutForTest(long j) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageO(160, Long.valueOf(j)));
    }

    private static boolean isValid(InputMethod inputMethod, InputMethodServiceInternal inputMethodServiceInternal, String str) {
        if (inputMethod != null && inputMethodServiceInternal != null && !inputMethodServiceInternal.isServiceDestroyed()) {
            return true;
        }
        Log.w(TAG, "Ignoring " + str + ", InputMethod:" + inputMethod + ", InputMethodServiceInternal:" + inputMethodServiceInternal);
        return false;
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void minimizeSoftInput(int i) {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageI(200, i));
    }

    @Override // com.android.internal.inputmethod.IInputMethod
    public void undoMinimizeSoftInput() {
        HandlerCaller handlerCaller = this.mCaller;
        handlerCaller.executeOrSendMessage(handlerCaller.obtainMessageI(210, 0));
    }
}
