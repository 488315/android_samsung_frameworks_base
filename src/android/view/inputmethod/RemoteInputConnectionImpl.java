package android.view.inputmethod;

import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.app.UriGrantsManager;
import android.content.ContentProvider;
import android.graphics.RectF;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.CancellationSignalBeamer;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ResultReceiver;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.inputmethod.RemoteInputConnectionImpl;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;
import com.android.internal.inputmethod.IRemoteInputConnection;
import com.android.internal.inputmethod.ImeTracing;
import com.android.internal.inputmethod.InputConnectionCommandHeader;
import com.android.internal.inputmethod.InputConnectionProtoDumper;
import com.samsung.android.rune.ViewRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
final class RemoteInputConnectionImpl extends IRemoteInputConnection.Stub {
    private static final boolean DEBUG = false;
    private static final int MAX_END_BATCH_EDIT_RETRY = 16;
    private static final String TAG = "RemoteInputConnectionImpl";
    private CancellationSignalBeamer.Receiver mBeamer;
    private final Handler mH;
    private final AtomicReference<InputConnection> mInputConnectionRef;
    private final Looper mLooper;
    private final InputMethodManager mParentInputMethodManager;
    private final WeakReference<View> mServedView;
    private boolean mWritingToolkitMode = false;
    private final AtomicBoolean mDeactivateRequested = new AtomicBoolean(false);
    private final AtomicInteger mCurrentSessionId = new AtomicInteger(0);
    private final AtomicBoolean mHasPendingInvalidation = new AtomicBoolean();
    private final AtomicBoolean mIsCursorAnchorInfoMonitoring = new AtomicBoolean(false);
    private final AtomicBoolean mHasPendingImmediateCursorAnchorInfoUpdate = new AtomicBoolean(false);
    private final IRemoteAccessibilityInputConnection mAccessibilityInputConnection = new AnonymousClass1();

    @Retention(RetentionPolicy.SOURCE)
    private @interface Dispatching {
        boolean cancellable();
    }

    private static final class KnownAlwaysTrueEndBatchEditCache {
        private static volatile Class<?>[] sArray;
        private static volatile Class<?> sElement;

        private KnownAlwaysTrueEndBatchEditCache() {
        }

        static boolean contains(Class<? extends InputConnection> cls) {
            if (cls == sElement) {
                return true;
            }
            Class<?>[] clsArr = sArray;
            if (clsArr == null) {
                return false;
            }
            for (Class<?> cls2 : clsArr) {
                if (cls2 == cls) {
                    return true;
                }
            }
            return false;
        }

        static void add(Class<? extends InputConnection> cls) {
            if (sElement == null) {
                sElement = cls;
                return;
            }
            Class<?>[] clsArr = sArray;
            int length = clsArr != null ? clsArr.length : 0;
            Class<?>[] clsArr2 = new Class[length + 1];
            for (int i = 0; i < length; i++) {
                clsArr2[i] = clsArr[i];
            }
            clsArr2[length] = cls;
            sArray = clsArr2;
        }
    }

    RemoteInputConnectionImpl(Looper looper, InputConnection inputConnection, InputMethodManager inputMethodManager, View view) {
        this.mInputConnectionRef = new AtomicReference<>(inputConnection);
        this.mLooper = looper;
        this.mH = new Handler(looper);
        this.mParentInputMethodManager = inputMethodManager;
        this.mServedView = new WeakReference<>(view);
    }

    public InputConnection getInputConnection() {
        return this.mInputConnectionRef.get();
    }

    public boolean hasPendingInvalidation() {
        return this.mHasPendingInvalidation.get();
    }

    private boolean isFinished() {
        return this.mInputConnectionRef.get() == null;
    }

    private View getServedView() {
        return this.mServedView.get();
    }

    public boolean isAssociatedWith(View view) {
        if (view == null) {
            return false;
        }
        return this.mServedView.refersTo(view);
    }

    public boolean resetHasPendingImmediateCursorAnchorInfoUpdate() {
        return this.mHasPendingImmediateCursorAnchorInfoUpdate.getAndSet(false);
    }

    public boolean isCursorAnchorInfoMonitoring() {
        return this.mIsCursorAnchorInfoMonitoring.get();
    }

    public void scheduleInvalidateInput() {
        scheduleInvalidateInput(false);
    }

    void scheduleInvalidateInput(final boolean z) {
        if (this.mHasPendingInvalidation.compareAndSet(false, true)) {
            final int incrementAndGet = z ? -1 : this.mCurrentSessionId.incrementAndGet();
            this.mH.post(new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteInputConnectionImpl.this.lambda$scheduleInvalidateInput$0(z, incrementAndGet);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleInvalidateInput$0(boolean z, int i) {
        InputConnection inputConnection;
        View servedView;
        TextSnapshot takeSnapshot;
        try {
            if (!isFinished() && (inputConnection = getInputConnection()) != null && (servedView = getServedView()) != null) {
                Class<?> cls = inputConnection.getClass();
                boolean contains = KnownAlwaysTrueEndBatchEditCache.contains(cls);
                if (!contains) {
                    boolean beginBatchEdit = inputConnection.beginBatchEdit();
                    inputConnection.finishComposingText();
                    if (beginBatchEdit) {
                        int i2 = 0;
                        while (true) {
                            if (!inputConnection.endBatchEdit()) {
                                break;
                            }
                            i2++;
                            if (i2 > 16) {
                                Log.e(TAG, cls.getTypeName() + "#endBatchEdit() still returns true even after retrying 16 times.  Falling back to InputMethodManager#restartInput(View)");
                                KnownAlwaysTrueEndBatchEditCache.add(cls);
                                contains = true;
                                break;
                            }
                        }
                    }
                }
                if (z) {
                    this.mParentInputMethodManager.restartInput(servedView);
                } else if (contains || (takeSnapshot = inputConnection.takeSnapshot()) == null || !this.mParentInputMethodManager.doInvalidateInput(this, takeSnapshot, i)) {
                    this.mParentInputMethodManager.restartInput(servedView);
                }
            }
        } finally {
            this.mHasPendingInvalidation.set(false);
        }
    }

    public void deactivate() {
        if (this.mWritingToolkitMode || this.mDeactivateRequested.getAndSet(true)) {
            return;
        }
        dispatch(new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$deactivate$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deactivate$1() {
        Trace.traceBegin(4L, "InputConnection#closeConnection");
        try {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null) {
                return;
            }
            try {
                inputConnection.closeConnection();
            } catch (AbstractMethodError unused) {
            }
            this.mInputConnectionRef.set(null);
            Trace.traceEnd(4L);
            final View view = this.mServedView.get();
            if (view != null) {
                Handler handler = view.getHandler();
                if (handler != null) {
                    if (handler.getLooper().isCurrentThread()) {
                        view.onInputConnectionClosedInternal();
                    } else {
                        Objects.requireNonNull(view);
                        handler.post(new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda37
                            @Override // java.lang.Runnable
                            public final void run() {
                                View.this.onInputConnectionClosedInternal();
                            }
                        });
                    }
                }
                ViewRootImpl viewRootImpl = view.getViewRootImpl();
                if (viewRootImpl != null) {
                    viewRootImpl.getHandwritingInitiator().onInputConnectionClosed(view);
                }
            }
        } finally {
            this.mInputConnectionRef.set(null);
            Trace.traceEnd(4L);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void cancelCancellationSignal(final IBinder iBinder) {
        if (this.mBeamer == null) {
            return;
        }
        dispatch(new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda45
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$cancelCancellationSignal$2(iBinder);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelCancellationSignal$2(IBinder iBinder) {
        this.mBeamer.cancel(iBinder);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void forgetCancellationSignal(final IBinder iBinder) {
        if (this.mBeamer == null) {
            return;
        }
        dispatch(new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda38
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$forgetCancellationSignal$3(iBinder);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$forgetCancellationSignal$3(IBinder iBinder) {
        this.mBeamer.forget(iBinder);
    }

    public String toString() {
        return "RemoteInputConnectionImpl{connection=" + getInputConnection() + " mDeactivateRequested=" + this.mDeactivateRequested.get() + " mServedView=" + this.mServedView.get() + "}";
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        InputConnection inputConnection = this.mInputConnectionRef.get();
        if ((inputConnection instanceof DumpableInputConnection) && this.mLooper.isCurrentThread()) {
            ((DumpableInputConnection) inputConnection).dumpDebug(protoOutputStream, j);
        }
    }

    public void dispatchReportFullscreenMode(final boolean z) {
        dispatch(new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda50
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$dispatchReportFullscreenMode$4(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchReportFullscreenMode$4(boolean z) {
        InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            return;
        }
        inputConnection.reportFullscreenMode(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkSessionId(InputConnectionCommandHeader inputConnectionCommandHeader) {
        if (inputConnectionCommandHeader.mSessionId == this.mCurrentSessionId.get()) {
            return true;
        }
        Log.w(TAG, "Session id mismatch header.sessionId: " + inputConnectionCommandHeader.mSessionId + " currentSessionId: " + this.mCurrentSessionId.get() + " while calling " + Debug.getCaller());
        return false;
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getTextAfterCursor(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2, AndroidFuture androidFuture) {
        dispatchWithTracing("getTextAfterCursor", androidFuture, new Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda24
            @Override // java.util.function.Supplier
            public final Object get() {
                CharSequence lambda$getTextAfterCursor$5;
                lambda$getTextAfterCursor$5 = RemoteInputConnectionImpl.this.lambda$getTextAfterCursor$5(inputConnectionCommandHeader, i, i2);
                return lambda$getTextAfterCursor$5;
            }
        }, useImeTracing() ? new Function() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda25
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                byte[] buildGetTextAfterCursorProto;
                buildGetTextAfterCursorProto = InputConnectionProtoDumper.buildGetTextAfterCursorProto(i, i2, (CharSequence) obj);
                return buildGetTextAfterCursorProto;
            }
        } : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CharSequence lambda$getTextAfterCursor$5(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
        if (!checkSessionId(inputConnectionCommandHeader)) {
            return null;
        }
        InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "getTextAfterCursor on inactive InputConnection");
            return null;
        }
        if (i < 0) {
            Log.i(TAG, "Returning null to getTextAfterCursor due to an invalid length=" + i);
            return null;
        }
        return inputConnection.getTextAfterCursor(i, i2);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getTextBeforeCursor(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2, AndroidFuture androidFuture) {
        dispatchWithTracing("getTextBeforeCursor", androidFuture, new Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda34
            @Override // java.util.function.Supplier
            public final Object get() {
                CharSequence lambda$getTextBeforeCursor$7;
                lambda$getTextBeforeCursor$7 = RemoteInputConnectionImpl.this.lambda$getTextBeforeCursor$7(inputConnectionCommandHeader, i, i2);
                return lambda$getTextBeforeCursor$7;
            }
        }, useImeTracing() ? new Function() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda35
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                byte[] buildGetTextBeforeCursorProto;
                buildGetTextBeforeCursorProto = InputConnectionProtoDumper.buildGetTextBeforeCursorProto(i, i2, (CharSequence) obj);
                return buildGetTextBeforeCursorProto;
            }
        } : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CharSequence lambda$getTextBeforeCursor$7(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
        if (!checkSessionId(inputConnectionCommandHeader)) {
            return null;
        }
        InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "getTextBeforeCursor on inactive InputConnection");
            return null;
        }
        if (i < 0) {
            Log.i(TAG, "Returning null to getTextBeforeCursor due to an invalid length=" + i);
            return null;
        }
        return inputConnection.getTextBeforeCursor(i, i2);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getSelectedText(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i, AndroidFuture androidFuture) {
        dispatchWithTracing("getSelectedText", androidFuture, new Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda31
            @Override // java.util.function.Supplier
            public final Object get() {
                CharSequence lambda$getSelectedText$9;
                lambda$getSelectedText$9 = RemoteInputConnectionImpl.this.lambda$getSelectedText$9(inputConnectionCommandHeader, i);
                return lambda$getSelectedText$9;
            }
        }, useImeTracing() ? new Function() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda32
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                byte[] buildGetSelectedTextProto;
                buildGetSelectedTextProto = InputConnectionProtoDumper.buildGetSelectedTextProto(i, (CharSequence) obj);
                return buildGetSelectedTextProto;
            }
        } : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CharSequence lambda$getSelectedText$9(InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
        if (!checkSessionId(inputConnectionCommandHeader)) {
            return null;
        }
        InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "getSelectedText on inactive InputConnection");
            return null;
        }
        try {
            return inputConnection.getSelectedText(i);
        } catch (AbstractMethodError unused) {
            return null;
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getSurroundingText(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2, final int i3, AndroidFuture androidFuture) {
        dispatchWithTracing("getSurroundingText", androidFuture, new Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                SurroundingText lambda$getSurroundingText$11;
                lambda$getSurroundingText$11 = RemoteInputConnectionImpl.this.lambda$getSurroundingText$11(inputConnectionCommandHeader, i, i2, i3);
                return lambda$getSurroundingText$11;
            }
        }, useImeTracing() ? new Function() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                byte[] buildGetSurroundingTextProto;
                buildGetSurroundingTextProto = InputConnectionProtoDumper.buildGetSurroundingTextProto(i, i2, i3, (SurroundingText) obj);
                return buildGetSurroundingTextProto;
            }
        } : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ SurroundingText lambda$getSurroundingText$11(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3) {
        if (!checkSessionId(inputConnectionCommandHeader)) {
            return null;
        }
        InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "getSurroundingText on inactive InputConnection");
            return null;
        }
        if (i < 0) {
            Log.i(TAG, "Returning null to getSurroundingText due to an invalid beforeLength=" + i);
            return null;
        }
        if (i2 < 0) {
            Log.i(TAG, "Returning null to getSurroundingText due to an invalid afterLength=" + i2);
            return null;
        }
        return inputConnection.getSurroundingText(i, i2, i3);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getCursorCapsMode(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i, AndroidFuture androidFuture) {
        dispatchWithTracing("getCursorCapsMode", androidFuture, new Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                Integer lambda$getCursorCapsMode$13;
                lambda$getCursorCapsMode$13 = RemoteInputConnectionImpl.this.lambda$getCursorCapsMode$13(inputConnectionCommandHeader, i);
                return lambda$getCursorCapsMode$13;
            }
        }, useImeTracing() ? new Function() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                byte[] buildGetCursorCapsModeProto;
                buildGetCursorCapsModeProto = InputConnectionProtoDumper.buildGetCursorCapsModeProto(i, ((Integer) obj).intValue());
                return buildGetCursorCapsModeProto;
            }
        } : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getCursorCapsMode$13(InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
        if (!checkSessionId(inputConnectionCommandHeader)) {
            return 0;
        }
        InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "getCursorCapsMode on inactive InputConnection");
            return 0;
        }
        return Integer.valueOf(inputConnection.getCursorCapsMode(i));
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getExtractedText(final InputConnectionCommandHeader inputConnectionCommandHeader, final ExtractedTextRequest extractedTextRequest, final int i, AndroidFuture androidFuture) {
        dispatchWithTracing("getExtractedText", androidFuture, new Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda22
            @Override // java.util.function.Supplier
            public final Object get() {
                ExtractedText lambda$getExtractedText$15;
                lambda$getExtractedText$15 = RemoteInputConnectionImpl.this.lambda$getExtractedText$15(inputConnectionCommandHeader, extractedTextRequest, i);
                return lambda$getExtractedText$15;
            }
        }, useImeTracing() ? new Function() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda23
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                byte[] buildGetExtractedTextProto;
                buildGetExtractedTextProto = InputConnectionProtoDumper.buildGetExtractedTextProto(ExtractedTextRequest.this, i, (ExtractedText) obj);
                return buildGetExtractedTextProto;
            }
        } : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ExtractedText lambda$getExtractedText$15(InputConnectionCommandHeader inputConnectionCommandHeader, ExtractedTextRequest extractedTextRequest, int i) {
        if (!checkSessionId(inputConnectionCommandHeader)) {
            return null;
        }
        InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "getExtractedText on inactive InputConnection");
            return null;
        }
        return inputConnection.getExtractedText(extractedTextRequest, i);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void commitText(final InputConnectionCommandHeader inputConnectionCommandHeader, final CharSequence charSequence, final int i) {
        dispatchWithTracing("commitText", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda46
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$commitText$17(inputConnectionCommandHeader, charSequence, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commitText$17(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i) {
        if (isInjectionFromKcap(inputConnectionCommandHeader.mSessionId) || checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "commitText on inactive InputConnection");
            } else {
                inputConnection.commitText(charSequence, i);
            }
        }
    }

    private boolean isInjectionFromKcap(int i) {
        return i == 9999 && SystemProperties.getInt("sys.datawedge.prop", 0) == 1;
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void commitTextWithTextAttribute(final InputConnectionCommandHeader inputConnectionCommandHeader, final CharSequence charSequence, final int i, final TextAttribute textAttribute) {
        dispatchWithTracing("commitTextWithTextAttribute", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$commitTextWithTextAttribute$18(inputConnectionCommandHeader, charSequence, i, textAttribute);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commitTextWithTextAttribute$18(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i, TextAttribute textAttribute) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "commitText on inactive InputConnection");
            } else {
                inputConnection.commitText(charSequence, i, textAttribute);
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void commitCompletion(final InputConnectionCommandHeader inputConnectionCommandHeader, final CompletionInfo completionInfo) {
        dispatchWithTracing("commitCompletion", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$commitCompletion$19(inputConnectionCommandHeader, completionInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commitCompletion$19(InputConnectionCommandHeader inputConnectionCommandHeader, CompletionInfo completionInfo) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "commitCompletion on inactive InputConnection");
            } else {
                inputConnection.commitCompletion(completionInfo);
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void commitCorrection(final InputConnectionCommandHeader inputConnectionCommandHeader, final CorrectionInfo correctionInfo) {
        dispatchWithTracing("commitCorrection", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$commitCorrection$20(inputConnectionCommandHeader, correctionInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commitCorrection$20(InputConnectionCommandHeader inputConnectionCommandHeader, CorrectionInfo correctionInfo) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "commitCorrection on inactive InputConnection");
            } else {
                try {
                    inputConnection.commitCorrection(correctionInfo);
                } catch (AbstractMethodError unused) {
                }
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setSelection(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2) {
        dispatchWithTracing("setSelection", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$setSelection$21(inputConnectionCommandHeader, i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSelection$21(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "setSelection on inactive InputConnection");
            } else {
                inputConnection.setSelection(i, i2);
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void performEditorAction(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i) {
        dispatchWithTracing("performEditorAction", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda33
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$performEditorAction$22(inputConnectionCommandHeader, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performEditorAction$22(InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "performEditorAction on inactive InputConnection");
            } else {
                inputConnection.performEditorAction(i);
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void performContextMenuAction(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i) {
        dispatchWithTracing("performContextMenuAction", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$performContextMenuAction$23(inputConnectionCommandHeader, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performContextMenuAction$23(InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "performContextMenuAction on inactive InputConnection");
            } else {
                inputConnection.performContextMenuAction(i);
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setComposingRegion(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2) {
        dispatchWithTracing("setComposingRegion", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$setComposingRegion$24(inputConnectionCommandHeader, i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setComposingRegion$24(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "setComposingRegion on inactive InputConnection");
            } else {
                try {
                    inputConnection.setComposingRegion(i, i2);
                } catch (AbstractMethodError unused) {
                }
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setComposingRegionWithTextAttribute(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2, final TextAttribute textAttribute) {
        dispatchWithTracing("setComposingRegionWithTextAttribute", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$setComposingRegionWithTextAttribute$25(inputConnectionCommandHeader, i, i2, textAttribute);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setComposingRegionWithTextAttribute$25(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, TextAttribute textAttribute) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "setComposingRegion on inactive InputConnection");
            } else {
                inputConnection.setComposingRegion(i, i2, textAttribute);
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setComposingText(final InputConnectionCommandHeader inputConnectionCommandHeader, final CharSequence charSequence, final int i) {
        dispatchWithTracing("setComposingText", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$setComposingText$26(inputConnectionCommandHeader, charSequence, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setComposingText$26(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "setComposingText on inactive InputConnection");
            } else {
                inputConnection.setComposingText(charSequence, i);
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setComposingTextWithTextAttribute(final InputConnectionCommandHeader inputConnectionCommandHeader, final CharSequence charSequence, final int i, final TextAttribute textAttribute) {
        dispatchWithTracing("setComposingTextWithTextAttribute", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda48
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$setComposingTextWithTextAttribute$27(inputConnectionCommandHeader, charSequence, i, textAttribute);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setComposingTextWithTextAttribute$27(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i, TextAttribute textAttribute) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "setComposingText on inactive InputConnection");
            } else {
                inputConnection.setComposingText(charSequence, i, textAttribute);
            }
        }
    }

    public void finishComposingTextFromImm() {
        final int i = this.mCurrentSessionId.get();
        dispatchWithTracing("finishComposingTextFromImm", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$finishComposingTextFromImm$28(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishComposingTextFromImm$28(int i) {
        if (!isFinished() && i == this.mCurrentSessionId.get()) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "finishComposingTextFromImm on inactive InputConnection");
            } else {
                inputConnection.finishComposingText();
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void finishComposingText(final InputConnectionCommandHeader inputConnectionCommandHeader) {
        dispatchWithTracing("finishComposingText", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$finishComposingText$29(inputConnectionCommandHeader);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishComposingText$29(InputConnectionCommandHeader inputConnectionCommandHeader) {
        if (!isFinished() && checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null && this.mDeactivateRequested.get()) {
                Log.w(TAG, "finishComposingText on inactive InputConnection");
            } else {
                inputConnection.finishComposingText();
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void sendKeyEvent(final InputConnectionCommandHeader inputConnectionCommandHeader, final KeyEvent keyEvent) {
        dispatchWithTracing("sendKeyEvent", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda41
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$sendKeyEvent$30(inputConnectionCommandHeader, keyEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendKeyEvent$30(InputConnectionCommandHeader inputConnectionCommandHeader, KeyEvent keyEvent) {
        if (isInjectionFromKcap(inputConnectionCommandHeader.mSessionId) || checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "sendKeyEvent on inactive InputConnection");
            } else {
                inputConnection.sendKeyEvent(keyEvent);
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void clearMetaKeyStates(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i) {
        dispatchWithTracing("clearMetaKeyStates", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$clearMetaKeyStates$31(inputConnectionCommandHeader, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearMetaKeyStates$31(InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "clearMetaKeyStates on inactive InputConnection");
            } else {
                inputConnection.clearMetaKeyStates(i);
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void deleteSurroundingText(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2) {
        dispatchWithTracing("deleteSurroundingText", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda44
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$deleteSurroundingText$32(inputConnectionCommandHeader, i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteSurroundingText$32(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "deleteSurroundingText on inactive InputConnection");
            } else {
                inputConnection.deleteSurroundingText(i, i2);
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void deleteSurroundingTextInCodePoints(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2) {
        dispatchWithTracing("deleteSurroundingTextInCodePoints", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$deleteSurroundingTextInCodePoints$33(inputConnectionCommandHeader, i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteSurroundingTextInCodePoints$33(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "deleteSurroundingTextInCodePoints on inactive InputConnection");
            } else {
                try {
                    inputConnection.deleteSurroundingTextInCodePoints(i, i2);
                } catch (AbstractMethodError unused) {
                }
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void beginBatchEdit(final InputConnectionCommandHeader inputConnectionCommandHeader) {
        dispatchWithTracing("beginBatchEdit", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$beginBatchEdit$34(inputConnectionCommandHeader);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$beginBatchEdit$34(InputConnectionCommandHeader inputConnectionCommandHeader) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "beginBatchEdit on inactive InputConnection");
            } else {
                inputConnection.beginBatchEdit();
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void endBatchEdit(final InputConnectionCommandHeader inputConnectionCommandHeader) {
        dispatchWithTracing("endBatchEdit", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$endBatchEdit$35(inputConnectionCommandHeader);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$endBatchEdit$35(InputConnectionCommandHeader inputConnectionCommandHeader) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "endBatchEdit on inactive InputConnection");
            } else {
                inputConnection.endBatchEdit();
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void performSpellCheck(final InputConnectionCommandHeader inputConnectionCommandHeader) {
        dispatchWithTracing("performSpellCheck", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda40
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$performSpellCheck$36(inputConnectionCommandHeader);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performSpellCheck$36(InputConnectionCommandHeader inputConnectionCommandHeader) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "performSpellCheck on inactive InputConnection");
            } else {
                inputConnection.performSpellCheck();
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void performPrivateCommand(final InputConnectionCommandHeader inputConnectionCommandHeader, final String str, final Bundle bundle) {
        dispatchWithTracing("performPrivateCommand", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda39
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$performPrivateCommand$37(str, inputConnectionCommandHeader, bundle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performPrivateCommand$37(String str, InputConnectionCommandHeader inputConnectionCommandHeader, Bundle bundle) {
        if (ViewRune.SUPPORT_WRITING_TOOLKIT && SemInputMethodManagerUtils.ACTION_DEACTIVATE.equals(str)) {
            setWritingToolkitMode(false);
            deactivate();
        } else if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "performPrivateCommand on inactive InputConnection");
            } else {
                inputConnection.performPrivateCommand(str, bundle);
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void performHandwritingGesture(final InputConnectionCommandHeader inputConnectionCommandHeader, ParcelableHandwritingGesture parcelableHandwritingGesture, final ResultReceiver resultReceiver) {
        final HandwritingGesture handwritingGesture = parcelableHandwritingGesture.get();
        if (handwritingGesture instanceof CancellableHandwritingGesture) {
            CancellableHandwritingGesture cancellableHandwritingGesture = (CancellableHandwritingGesture) handwritingGesture;
            cancellableHandwritingGesture.unbeamCancellationSignal(getCancellationSignalBeamer());
            if (cancellableHandwritingGesture.getCancellationSignal() != null && cancellableHandwritingGesture.getCancellationSignal().isCanceled()) {
                if (resultReceiver != null) {
                    resultReceiver.send(4, null);
                    return;
                }
                return;
            }
        }
        dispatchWithTracing("performHandwritingGesture", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$performHandwritingGesture$39(inputConnectionCommandHeader, resultReceiver, handwritingGesture);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performHandwritingGesture$39(InputConnectionCommandHeader inputConnectionCommandHeader, final ResultReceiver resultReceiver, HandwritingGesture handwritingGesture) {
        if (!checkSessionId(inputConnectionCommandHeader)) {
            if (resultReceiver != null) {
                resultReceiver.send(4, null);
                return;
            }
            return;
        }
        InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "performHandwritingGesture on inactive InputConnection");
            if (resultReceiver != null) {
                resultReceiver.send(4, null);
                return;
            }
            return;
        }
        inputConnection.performHandwritingGesture(handwritingGesture, resultReceiver != null ? new PendingIntent$$ExternalSyntheticLambda0() : null, resultReceiver != null ? new IntConsumer() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda27
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                ResultReceiver.this.send(i, null);
            }
        } : null);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void previewHandwritingGesture(final InputConnectionCommandHeader inputConnectionCommandHeader, ParcelableHandwritingGesture parcelableHandwritingGesture, IBinder iBinder) {
        final CancellationSignal unbeam = iBinder != null ? getCancellationSignalBeamer().unbeam(iBinder) : null;
        final PreviewableHandwritingGesture previewableHandwritingGesture = (PreviewableHandwritingGesture) parcelableHandwritingGesture.get();
        dispatchWithTracing("previewHandwritingGesture", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$previewHandwritingGesture$40(inputConnectionCommandHeader, unbeam, previewableHandwritingGesture);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$previewHandwritingGesture$40(InputConnectionCommandHeader inputConnectionCommandHeader, CancellationSignal cancellationSignal, PreviewableHandwritingGesture previewableHandwritingGesture) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            if (cancellationSignal == null || !cancellationSignal.isCanceled()) {
                InputConnection inputConnection = getInputConnection();
                if (inputConnection == null || this.mDeactivateRequested.get()) {
                    Log.w(TAG, "previewHandwritingGesture on inactive InputConnection");
                } else {
                    inputConnection.previewHandwritingGesture(previewableHandwritingGesture, cancellationSignal);
                }
            }
        }
    }

    private CancellationSignalBeamer.Receiver getCancellationSignalBeamer() {
        CancellationSignalBeamer.Receiver receiver = this.mBeamer;
        if (receiver != null) {
            return receiver;
        }
        CancellationSignalBeamer.Receiver receiver2 = new CancellationSignalBeamer.Receiver(true);
        this.mBeamer = receiver2;
        return receiver2;
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void requestCursorUpdates(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2, AndroidFuture androidFuture) {
        dispatchWithTracing("requestCursorUpdates", androidFuture, new Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda47
            @Override // java.util.function.Supplier
            public final Object get() {
                Boolean lambda$requestCursorUpdates$41;
                lambda$requestCursorUpdates$41 = RemoteInputConnectionImpl.this.lambda$requestCursorUpdates$41(inputConnectionCommandHeader, i, i2);
                return lambda$requestCursorUpdates$41;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$requestCursorUpdates$41(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
        if (!checkSessionId(inputConnectionCommandHeader)) {
            return false;
        }
        return Boolean.valueOf(requestCursorUpdatesInternal(i, 0, i2));
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void requestCursorUpdatesWithFilter(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2, final int i3, AndroidFuture androidFuture) {
        dispatchWithTracing("requestCursorUpdates", androidFuture, new Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final Object get() {
                Boolean lambda$requestCursorUpdatesWithFilter$42;
                lambda$requestCursorUpdatesWithFilter$42 = RemoteInputConnectionImpl.this.lambda$requestCursorUpdatesWithFilter$42(inputConnectionCommandHeader, i, i2, i3);
                return lambda$requestCursorUpdatesWithFilter$42;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$requestCursorUpdatesWithFilter$42(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3) {
        if (!checkSessionId(inputConnectionCommandHeader)) {
            return false;
        }
        return Boolean.valueOf(requestCursorUpdatesInternal(i, i2, i3));
    }

    private boolean requestCursorUpdatesInternal(int i, int i2, int i3) {
        InputConnection inputConnection = getInputConnection();
        boolean z = false;
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "requestCursorUpdates on inactive InputConnection");
            return false;
        }
        if (this.mParentInputMethodManager.mRequestCursorUpdateDisplayIdCheck.get() && this.mParentInputMethodManager.getDisplayId() != i3) {
            return false;
        }
        boolean z2 = (i & 1) != 0;
        boolean z3 = (i & 2) != 0;
        try {
            boolean requestCursorUpdates = inputConnection.requestCursorUpdates(i, i2);
            this.mHasPendingImmediateCursorAnchorInfoUpdate.set(requestCursorUpdates && z2);
            AtomicBoolean atomicBoolean = this.mIsCursorAnchorInfoMonitoring;
            if (requestCursorUpdates && z3) {
                z = true;
            }
            atomicBoolean.set(z);
            return requestCursorUpdates;
        } catch (AbstractMethodError unused) {
            this.mHasPendingImmediateCursorAnchorInfoUpdate.set(false);
            this.mIsCursorAnchorInfoMonitoring.set(false);
            return false;
        } catch (Throwable th) {
            this.mHasPendingImmediateCursorAnchorInfoUpdate.set(false);
            this.mIsCursorAnchorInfoMonitoring.set(false);
            throw th;
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void requestTextBoundsInfo(final InputConnectionCommandHeader inputConnectionCommandHeader, final RectF rectF, final ResultReceiver resultReceiver) {
        dispatchWithTracing("requestTextBoundsInfo", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda43
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$requestTextBoundsInfo$44(inputConnectionCommandHeader, resultReceiver, rectF);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestTextBoundsInfo$44(InputConnectionCommandHeader inputConnectionCommandHeader, final ResultReceiver resultReceiver, RectF rectF) {
        if (!checkSessionId(inputConnectionCommandHeader)) {
            resultReceiver.send(3, null);
            return;
        }
        InputConnection inputConnection = getInputConnection();
        if (inputConnection == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "requestTextBoundsInfo on inactive InputConnection");
            resultReceiver.send(3, null);
        } else {
            inputConnection.requestTextBoundsInfo(rectF, new PendingIntent$$ExternalSyntheticLambda0(), new Consumer() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda42
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RemoteInputConnectionImpl.lambda$requestTextBoundsInfo$43(ResultReceiver.this, (TextBoundsInfoResult) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$requestTextBoundsInfo$43(ResultReceiver resultReceiver, TextBoundsInfoResult textBoundsInfoResult) {
        int resultCode = textBoundsInfoResult.getResultCode();
        TextBoundsInfo textBoundsInfo = textBoundsInfoResult.getTextBoundsInfo();
        resultReceiver.send(resultCode, textBoundsInfo == null ? null : textBoundsInfo.toBundle());
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void commitContent(final InputConnectionCommandHeader inputConnectionCommandHeader, final InputContentInfo inputContentInfo, final int i, final Bundle bundle, AndroidFuture androidFuture) {
        final int callingUid = Binder.getCallingUid();
        dispatchWithTracing("commitContent", androidFuture, new Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                Boolean lambda$commitContent$45;
                lambda$commitContent$45 = RemoteInputConnectionImpl.this.lambda$commitContent$45(inputContentInfo, callingUid, inputConnectionCommandHeader, i, bundle);
                return lambda$commitContent$45;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$commitContent$45(InputContentInfo inputContentInfo, int i, InputConnectionCommandHeader inputConnectionCommandHeader, int i2, Bundle bundle) {
        try {
            UriGrantsManager.getService().checkGrantUriPermission_ignoreNonSystem(i, null, ContentProvider.getUriWithoutUserId(inputContentInfo.getContentUri()), 1, ContentProvider.getUserIdFromUri(inputContentInfo.getContentUri(), UserHandle.getUserId(i)));
            if (!checkSessionId(inputConnectionCommandHeader)) {
                return false;
            }
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "commitContent on inactive InputConnection");
                return false;
            }
            if (inputContentInfo == null || !inputContentInfo.validate()) {
                Log.w(TAG, "commitContent with invalid inputContentInfo=" + inputContentInfo);
                return false;
            }
            try {
                return Boolean.valueOf(inputConnection.commitContent(inputContentInfo, i2, bundle));
            } catch (AbstractMethodError unused) {
                return false;
            }
        } catch (Exception e) {
            Log.w(TAG, "commitContent with invalid Uri permission from IME:", e);
            return false;
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setImeConsumesInput(final InputConnectionCommandHeader inputConnectionCommandHeader, final boolean z) {
        dispatchWithTracing("setImeConsumesInput", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda30
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$setImeConsumesInput$46(inputConnectionCommandHeader, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setImeConsumesInput$46(InputConnectionCommandHeader inputConnectionCommandHeader, boolean z) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "setImeConsumesInput on inactive InputConnection");
            } else {
                inputConnection.setImeConsumesInput(z);
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void replaceText(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2, final CharSequence charSequence, final int i3, final TextAttribute textAttribute) {
        dispatchWithTracing("replaceText", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda49
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$replaceText$47(inputConnectionCommandHeader, i, i2, charSequence, i3, textAttribute);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$replaceText$47(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, CharSequence charSequence, int i3, TextAttribute textAttribute) {
        if (checkSessionId(inputConnectionCommandHeader)) {
            InputConnection inputConnection = getInputConnection();
            if (inputConnection == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "replaceText on inactive InputConnection");
            } else {
                inputConnection.replaceText(i, i2, charSequence, i3, textAttribute);
            }
        }
    }

    /* renamed from: android.view.inputmethod.RemoteInputConnectionImpl$1, reason: invalid class name */
    class AnonymousClass1 extends IRemoteAccessibilityInputConnection.Stub {
        AnonymousClass1() {
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void commitText(final InputConnectionCommandHeader inputConnectionCommandHeader, final CharSequence charSequence, final int i, final TextAttribute textAttribute) {
            RemoteInputConnectionImpl.this.dispatchWithTracing("commitTextFromA11yIme", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteInputConnectionImpl.AnonymousClass1.this.lambda$commitText$0(inputConnectionCommandHeader, charSequence, i, textAttribute);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$commitText$0(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i, TextAttribute textAttribute) {
            if (RemoteInputConnectionImpl.this.checkSessionId(inputConnectionCommandHeader)) {
                InputConnection inputConnection = RemoteInputConnectionImpl.this.getInputConnection();
                if (inputConnection == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                    Log.w(RemoteInputConnectionImpl.TAG, "commitText on inactive InputConnection");
                    return;
                }
                inputConnection.beginBatchEdit();
                inputConnection.finishComposingText();
                inputConnection.commitText(charSequence, i, textAttribute);
                inputConnection.endBatchEdit();
            }
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void setSelection(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2) {
            RemoteInputConnectionImpl.this.dispatchWithTracing("setSelectionFromA11yIme", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteInputConnectionImpl.AnonymousClass1.this.lambda$setSelection$1(inputConnectionCommandHeader, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setSelection$1(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
            if (RemoteInputConnectionImpl.this.checkSessionId(inputConnectionCommandHeader)) {
                InputConnection inputConnection = RemoteInputConnectionImpl.this.getInputConnection();
                if (inputConnection == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                    Log.w(RemoteInputConnectionImpl.TAG, "setSelection on inactive InputConnection");
                } else {
                    inputConnection.setSelection(i, i2);
                }
            }
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void getSurroundingText(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2, final int i3, AndroidFuture androidFuture) {
            RemoteInputConnectionImpl.this.dispatchWithTracing("getSurroundingTextFromA11yIme", androidFuture, new Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    SurroundingText lambda$getSurroundingText$2;
                    lambda$getSurroundingText$2 = RemoteInputConnectionImpl.AnonymousClass1.this.lambda$getSurroundingText$2(inputConnectionCommandHeader, i, i2, i3);
                    return lambda$getSurroundingText$2;
                }
            }, RemoteInputConnectionImpl.useImeTracing() ? new Function() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    byte[] buildGetSurroundingTextProto;
                    buildGetSurroundingTextProto = InputConnectionProtoDumper.buildGetSurroundingTextProto(i, i2, i3, (SurroundingText) obj);
                    return buildGetSurroundingTextProto;
                }
            } : null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ SurroundingText lambda$getSurroundingText$2(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3) {
            if (!RemoteInputConnectionImpl.this.checkSessionId(inputConnectionCommandHeader)) {
                return null;
            }
            InputConnection inputConnection = RemoteInputConnectionImpl.this.getInputConnection();
            if (inputConnection == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                Log.w(RemoteInputConnectionImpl.TAG, "getSurroundingText on inactive InputConnection");
                return null;
            }
            if (i < 0) {
                Log.i(RemoteInputConnectionImpl.TAG, "Returning null to getSurroundingText due to an invalid beforeLength=" + i);
                return null;
            }
            if (i2 < 0) {
                Log.i(RemoteInputConnectionImpl.TAG, "Returning null to getSurroundingText due to an invalid afterLength=" + i2);
                return null;
            }
            return inputConnection.getSurroundingText(i, i2, i3);
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void deleteSurroundingText(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i, final int i2) {
            RemoteInputConnectionImpl.this.dispatchWithTracing("deleteSurroundingTextFromA11yIme", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteInputConnectionImpl.AnonymousClass1.this.lambda$deleteSurroundingText$4(inputConnectionCommandHeader, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$deleteSurroundingText$4(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) {
            if (RemoteInputConnectionImpl.this.checkSessionId(inputConnectionCommandHeader)) {
                InputConnection inputConnection = RemoteInputConnectionImpl.this.getInputConnection();
                if (inputConnection == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                    Log.w(RemoteInputConnectionImpl.TAG, "deleteSurroundingText on inactive InputConnection");
                } else {
                    inputConnection.deleteSurroundingText(i, i2);
                }
            }
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void sendKeyEvent(final InputConnectionCommandHeader inputConnectionCommandHeader, final KeyEvent keyEvent) {
            RemoteInputConnectionImpl.this.dispatchWithTracing("sendKeyEventFromA11yIme", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteInputConnectionImpl.AnonymousClass1.this.lambda$sendKeyEvent$5(inputConnectionCommandHeader, keyEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendKeyEvent$5(InputConnectionCommandHeader inputConnectionCommandHeader, KeyEvent keyEvent) {
            if (RemoteInputConnectionImpl.this.checkSessionId(inputConnectionCommandHeader)) {
                InputConnection inputConnection = RemoteInputConnectionImpl.this.getInputConnection();
                if (inputConnection == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                    Log.w(RemoteInputConnectionImpl.TAG, "sendKeyEvent on inactive InputConnection");
                } else {
                    inputConnection.sendKeyEvent(keyEvent);
                }
            }
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void performEditorAction(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i) {
            RemoteInputConnectionImpl.this.dispatchWithTracing("performEditorActionFromA11yIme", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteInputConnectionImpl.AnonymousClass1.this.lambda$performEditorAction$6(inputConnectionCommandHeader, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$performEditorAction$6(InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
            if (RemoteInputConnectionImpl.this.checkSessionId(inputConnectionCommandHeader)) {
                InputConnection inputConnection = RemoteInputConnectionImpl.this.getInputConnection();
                if (inputConnection == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                    Log.w(RemoteInputConnectionImpl.TAG, "performEditorAction on inactive InputConnection");
                } else {
                    inputConnection.performEditorAction(i);
                }
            }
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void performContextMenuAction(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i) {
            RemoteInputConnectionImpl.this.dispatchWithTracing("performContextMenuActionFromA11yIme", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteInputConnectionImpl.AnonymousClass1.this.lambda$performContextMenuAction$7(inputConnectionCommandHeader, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$performContextMenuAction$7(InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
            if (RemoteInputConnectionImpl.this.checkSessionId(inputConnectionCommandHeader)) {
                InputConnection inputConnection = RemoteInputConnectionImpl.this.getInputConnection();
                if (inputConnection == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                    Log.w(RemoteInputConnectionImpl.TAG, "performContextMenuAction on inactive InputConnection");
                } else {
                    inputConnection.performContextMenuAction(i);
                }
            }
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void getCursorCapsMode(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i, AndroidFuture androidFuture) {
            RemoteInputConnectionImpl.this.dispatchWithTracing("getCursorCapsModeFromA11yIme", androidFuture, new Supplier() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda7
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$getCursorCapsMode$8;
                    lambda$getCursorCapsMode$8 = RemoteInputConnectionImpl.AnonymousClass1.this.lambda$getCursorCapsMode$8(inputConnectionCommandHeader, i);
                    return lambda$getCursorCapsMode$8;
                }
            }, RemoteInputConnectionImpl.useImeTracing() ? new Function() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda8
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    byte[] buildGetCursorCapsModeProto;
                    buildGetCursorCapsModeProto = InputConnectionProtoDumper.buildGetCursorCapsModeProto(i, ((Integer) obj).intValue());
                    return buildGetCursorCapsModeProto;
                }
            } : null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$getCursorCapsMode$8(InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
            if (!RemoteInputConnectionImpl.this.checkSessionId(inputConnectionCommandHeader)) {
                return 0;
            }
            InputConnection inputConnection = RemoteInputConnectionImpl.this.getInputConnection();
            if (inputConnection == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                Log.w(RemoteInputConnectionImpl.TAG, "getCursorCapsMode on inactive InputConnection");
                return 0;
            }
            return Integer.valueOf(inputConnection.getCursorCapsMode(i));
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void clearMetaKeyStates(final InputConnectionCommandHeader inputConnectionCommandHeader, final int i) {
            RemoteInputConnectionImpl.this.dispatchWithTracing("clearMetaKeyStatesFromA11yIme", new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteInputConnectionImpl.AnonymousClass1.this.lambda$clearMetaKeyStates$10(inputConnectionCommandHeader, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$clearMetaKeyStates$10(InputConnectionCommandHeader inputConnectionCommandHeader, int i) {
            if (RemoteInputConnectionImpl.this.checkSessionId(inputConnectionCommandHeader)) {
                InputConnection inputConnection = RemoteInputConnectionImpl.this.getInputConnection();
                if (inputConnection == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                    Log.w(RemoteInputConnectionImpl.TAG, "clearMetaKeyStates on inactive InputConnection");
                } else {
                    inputConnection.clearMetaKeyStates(i);
                }
            }
        }
    }

    public IRemoteAccessibilityInputConnection asIRemoteAccessibilityInputConnection() {
        return this.mAccessibilityInputConnection;
    }

    private void dispatch(Runnable runnable) {
        if (this.mLooper.isCurrentThread()) {
            runnable.run();
        } else {
            this.mH.post(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchWithTracing(final String str, final Runnable runnable) {
        if (Trace.isTagEnabled(4L)) {
            runnable = new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteInputConnectionImpl.lambda$dispatchWithTracing$48(str, runnable);
                }
            };
        }
        dispatch(runnable);
    }

    static /* synthetic */ void lambda$dispatchWithTracing$48(String str, Runnable runnable) {
        Trace.traceBegin(4L, "InputConnection#" + str);
        try {
            runnable.run();
        } finally {
            Trace.traceEnd(4L);
        }
    }

    private <T> void dispatchWithTracing(String str, AndroidFuture androidFuture, Supplier<T> supplier) {
        dispatchWithTracing(str, androidFuture, supplier, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> void dispatchWithTracing(final String str, final AndroidFuture androidFuture, final Supplier<T> supplier, final Function<T, byte[]> function) {
        dispatchWithTracing(str, new Runnable() { // from class: android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda36
            @Override // java.lang.Runnable
            public final void run() {
                RemoteInputConnectionImpl.this.lambda$dispatchWithTracing$49(supplier, androidFuture, function, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchWithTracing$49(Supplier supplier, AndroidFuture androidFuture, Function function, String str) {
        try {
            Object obj = supplier.get();
            androidFuture.complete(obj);
            if (function != null) {
                byte[] bArr = (byte[]) function.apply(obj);
                ImeTracing.getInstance().triggerClientDump("RemoteInputConnectionImpl#" + str, this.mParentInputMethodManager, bArr);
            }
        } catch (Throwable th) {
            androidFuture.completeExceptionally(th);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean useImeTracing() {
        return ImeTracing.getInstance().isEnabled();
    }

    public void setWritingToolkitMode(boolean z) {
        this.mWritingToolkitMode = z;
    }

    public boolean getWritingToolkitMode() {
        return this.mWritingToolkitMode;
    }
}
