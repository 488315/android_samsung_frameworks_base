package android.view.inputmethod;

import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.app.UriGrantsManager;
import android.content.ContentProvider;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.CancellationSignalBeamer;
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
    private final AtomicBoolean mHasPendingImmediateCursorAnchorInfoUpdate =
            new AtomicBoolean(false);
    private final IRemoteAccessibilityInputConnection mAccessibilityInputConnection =
            new AnonymousClass1();

    @Retention(RetentionPolicy.SOURCE)
    private @interface Dispatching {
        boolean cancellable();
    }

    private static final class KnownAlwaysTrueEndBatchEditCache {
        private static volatile Class<?>[] sArray;
        private static volatile Class<?> sElement;

        private KnownAlwaysTrueEndBatchEditCache() {}

        static boolean contains(Class<? extends InputConnection> klass) {
            if (klass == sElement) {
                return true;
            }
            Class<?>[] array = sArray;
            if (array == null) {
                return false;
            }
            for (Class<?> item : array) {
                if (item == klass) {
                    return true;
                }
            }
            return false;
        }

        static void add(Class<? extends InputConnection> klass) {
            if (sElement == null) {
                sElement = klass;
                return;
            }
            Class<?>[] array = sArray;
            int arraySize = array != null ? array.length : 0;
            Class<?>[] newArray = new Class[arraySize + 1];
            for (int i = 0; i < arraySize; i++) {
                newArray[i] = array[i];
            }
            newArray[arraySize] = klass;
            sArray = newArray;
        }
    }

    RemoteInputConnectionImpl(
            Looper looper,
            InputConnection inputConnection,
            InputMethodManager inputMethodManager,
            View servedView) {
        this.mInputConnectionRef = new AtomicReference<>(inputConnection);
        this.mLooper = looper;
        this.mH = new Handler(this.mLooper);
        this.mParentInputMethodManager = inputMethodManager;
        this.mServedView = new WeakReference<>(servedView);
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
        if (this.mHasPendingInvalidation.compareAndSet(false, true)) {
            final int nextSessionId = this.mCurrentSessionId.incrementAndGet();
            this.mH.post(
                    new Runnable() { // from class:
                                     // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda36
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteInputConnectionImpl.this.lambda$scheduleInvalidateInput$0(
                                    nextSessionId);
                        }
                    });
        }
    }

    public /* synthetic */ void lambda$scheduleInvalidateInput$0(int nextSessionId) {
        TextSnapshot textSnapshot;
        try {
            if (isFinished()) {
                return;
            }
            InputConnection ic = getInputConnection();
            if (ic == null) {
                return;
            }
            View view = getServedView();
            if (view == null) {
                return;
            }
            Class<?> cls = ic.getClass();
            boolean alwaysTrueEndBatchEditDetected = KnownAlwaysTrueEndBatchEditCache.contains(cls);
            if (!alwaysTrueEndBatchEditDetected) {
                boolean supportsBatchEdit = ic.beginBatchEdit();
                ic.finishComposingText();
                if (supportsBatchEdit) {
                    int retryCount = 0;
                    while (true) {
                        if (!ic.endBatchEdit()) {
                            break;
                        }
                        retryCount++;
                        if (retryCount > 16) {
                            Log.e(
                                    TAG,
                                    cls.getTypeName()
                                            + "#endBatchEdit() still returns true even after"
                                            + " retrying 16 times.  Falling back to"
                                            + " InputMethodManager#restartInput(View)");
                            alwaysTrueEndBatchEditDetected = true;
                            KnownAlwaysTrueEndBatchEditCache.add(cls);
                            break;
                        }
                    }
                }
            }
            if (alwaysTrueEndBatchEditDetected
                    || (textSnapshot = ic.takeSnapshot()) == null
                    || !this.mParentInputMethodManager.doInvalidateInput(
                            this, textSnapshot, nextSessionId)) {
                this.mParentInputMethodManager.restartInput(view);
            }
        } finally {
            this.mHasPendingInvalidation.set(false);
        }
    }

    public void deactivate() {
        if (this.mWritingToolkitMode || this.mDeactivateRequested.getAndSet(true)) {
            return;
        }
        dispatch(
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda16
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$deactivate$1();
                    }
                });
    }

    public /* synthetic */ void lambda$deactivate$1() {
        Trace.traceBegin(4L, "InputConnection#closeConnection");
        try {
            InputConnection ic = getInputConnection();
            if (ic == null) {
                return;
            }
            try {
                ic.closeConnection();
            } catch (AbstractMethodError e) {
            }
            this.mInputConnectionRef.set(null);
            Trace.traceEnd(4L);
            final View servedView = this.mServedView.get();
            if (servedView != null) {
                Handler handler = servedView.getHandler();
                if (handler != null) {
                    if (handler.getLooper().isCurrentThread()) {
                        servedView.onInputConnectionClosedInternal();
                    } else {
                        Objects.requireNonNull(servedView);
                        handler.post(
                                new Runnable() { // from class:
                                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda37
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        View.this.onInputConnectionClosedInternal();
                                    }
                                });
                    }
                }
                ViewRootImpl viewRoot = servedView.getViewRootImpl();
                if (viewRoot != null) {
                    viewRoot.getHandwritingInitiator().onInputConnectionClosed(servedView);
                }
            }
        } finally {
            this.mInputConnectionRef.set(null);
            Trace.traceEnd(4L);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void cancelCancellationSignal(final IBinder token) {
        if (this.mBeamer == null) {
            return;
        }
        dispatch(
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda45
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$cancelCancellationSignal$2(token);
                    }
                });
    }

    public /* synthetic */ void lambda$cancelCancellationSignal$2(IBinder token) {
        this.mBeamer.cancel(token);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void forgetCancellationSignal(final IBinder token) {
        if (this.mBeamer == null) {
            return;
        }
        dispatch(
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda38
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$forgetCancellationSignal$3(token);
                    }
                });
    }

    public /* synthetic */ void lambda$forgetCancellationSignal$3(IBinder token) {
        this.mBeamer.forget(token);
    }

    public String toString() {
        return "RemoteInputConnectionImpl{connection="
                + getInputConnection()
                + " mDeactivateRequested="
                + this.mDeactivateRequested.get()
                + " mServedView="
                + this.mServedView.get()
                + "}";
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        InputConnection ic = this.mInputConnectionRef.get();
        if ((ic instanceof DumpableInputConnection) && this.mLooper.isCurrentThread()) {
            ((DumpableInputConnection) ic).dumpDebug(proto, fieldId);
        }
    }

    public void dispatchReportFullscreenMode(final boolean enabled) {
        dispatch(
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda50
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$dispatchReportFullscreenMode$4(
                                enabled);
                    }
                });
    }

    public /* synthetic */ void lambda$dispatchReportFullscreenMode$4(boolean enabled) {
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            return;
        }
        ic.reportFullscreenMode(enabled);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getTextAfterCursor(
            final InputConnectionCommandHeader header,
            final int length,
            final int flags,
            AndroidFuture future) {
        dispatchWithTracing(
                "getTextAfterCursor",
                future,
                new Supplier() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda23
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        CharSequence lambda$getTextAfterCursor$5;
                        lambda$getTextAfterCursor$5 =
                                RemoteInputConnectionImpl.this.lambda$getTextAfterCursor$5(
                                        header, length, flags);
                        return lambda$getTextAfterCursor$5;
                    }
                },
                useImeTracing()
                        ? new Function() { // from class:
                                           // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda24
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                byte[] buildGetTextAfterCursorProto;
                                buildGetTextAfterCursorProto =
                                        InputConnectionProtoDumper.buildGetTextAfterCursorProto(
                                                length, flags, (CharSequence) obj);
                                return buildGetTextAfterCursorProto;
                            }
                        }
                        : null);
    }

    public /* synthetic */ CharSequence lambda$getTextAfterCursor$5(
            InputConnectionCommandHeader header, int length, int flags) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return null;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "getTextAfterCursor on inactive InputConnection");
            return null;
        }
        if (length < 0) {
            Log.i(TAG, "Returning null to getTextAfterCursor due to an invalid length=" + length);
            return null;
        }
        return ic.getTextAfterCursor(length, flags);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getTextBeforeCursor(
            final InputConnectionCommandHeader header,
            final int length,
            final int flags,
            AndroidFuture future) {
        dispatchWithTracing(
                "getTextBeforeCursor",
                future,
                new Supplier() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda33
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        CharSequence lambda$getTextBeforeCursor$7;
                        lambda$getTextBeforeCursor$7 =
                                RemoteInputConnectionImpl.this.lambda$getTextBeforeCursor$7(
                                        header, length, flags);
                        return lambda$getTextBeforeCursor$7;
                    }
                },
                useImeTracing()
                        ? new Function() { // from class:
                                           // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda34
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                byte[] buildGetTextBeforeCursorProto;
                                buildGetTextBeforeCursorProto =
                                        InputConnectionProtoDumper.buildGetTextBeforeCursorProto(
                                                length, flags, (CharSequence) obj);
                                return buildGetTextBeforeCursorProto;
                            }
                        }
                        : null);
    }

    public /* synthetic */ CharSequence lambda$getTextBeforeCursor$7(
            InputConnectionCommandHeader header, int length, int flags) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return null;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "getTextBeforeCursor on inactive InputConnection");
            return null;
        }
        if (length < 0) {
            Log.i(TAG, "Returning null to getTextBeforeCursor due to an invalid length=" + length);
            return null;
        }
        return ic.getTextBeforeCursor(length, flags);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getSelectedText(
            final InputConnectionCommandHeader header, final int flags, AndroidFuture future) {
        dispatchWithTracing(
                "getSelectedText",
                future,
                new Supplier() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda30
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        CharSequence lambda$getSelectedText$9;
                        lambda$getSelectedText$9 =
                                RemoteInputConnectionImpl.this.lambda$getSelectedText$9(
                                        header, flags);
                        return lambda$getSelectedText$9;
                    }
                },
                useImeTracing()
                        ? new Function() { // from class:
                                           // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda31
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                byte[] buildGetSelectedTextProto;
                                buildGetSelectedTextProto =
                                        InputConnectionProtoDumper.buildGetSelectedTextProto(
                                                flags, (CharSequence) obj);
                                return buildGetSelectedTextProto;
                            }
                        }
                        : null);
    }

    public /* synthetic */ CharSequence lambda$getSelectedText$9(
            InputConnectionCommandHeader header, int flags) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return null;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "getSelectedText on inactive InputConnection");
            return null;
        }
        try {
            return ic.getSelectedText(flags);
        } catch (AbstractMethodError e) {
            return null;
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getSurroundingText(
            final InputConnectionCommandHeader header,
            final int beforeLength,
            final int afterLength,
            final int flags,
            AndroidFuture future) {
        dispatchWithTracing(
                "getSurroundingText",
                future,
                new Supplier() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda7
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        SurroundingText lambda$getSurroundingText$11;
                        lambda$getSurroundingText$11 =
                                RemoteInputConnectionImpl.this.lambda$getSurroundingText$11(
                                        header, beforeLength, afterLength, flags);
                        return lambda$getSurroundingText$11;
                    }
                },
                useImeTracing()
                        ? new Function() { // from class:
                                           // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda8
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                byte[] buildGetSurroundingTextProto;
                                buildGetSurroundingTextProto =
                                        InputConnectionProtoDumper.buildGetSurroundingTextProto(
                                                beforeLength,
                                                afterLength,
                                                flags,
                                                (SurroundingText) obj);
                                return buildGetSurroundingTextProto;
                            }
                        }
                        : null);
    }

    public /* synthetic */ SurroundingText lambda$getSurroundingText$11(
            InputConnectionCommandHeader header, int beforeLength, int afterLength, int flags) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return null;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "getSurroundingText on inactive InputConnection");
            return null;
        }
        if (beforeLength < 0) {
            Log.i(
                    TAG,
                    "Returning null to getSurroundingText due to an invalid beforeLength="
                            + beforeLength);
            return null;
        }
        if (afterLength < 0) {
            Log.i(
                    TAG,
                    "Returning null to getSurroundingText due to an invalid afterLength="
                            + afterLength);
            return null;
        }
        return ic.getSurroundingText(beforeLength, afterLength, flags);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getCursorCapsMode(
            final InputConnectionCommandHeader header, final int reqModes, AndroidFuture future) {
        dispatchWithTracing(
                "getCursorCapsMode",
                future,
                new Supplier() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda2
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Integer lambda$getCursorCapsMode$13;
                        lambda$getCursorCapsMode$13 =
                                RemoteInputConnectionImpl.this.lambda$getCursorCapsMode$13(
                                        header, reqModes);
                        return lambda$getCursorCapsMode$13;
                    }
                },
                useImeTracing()
                        ? new Function() { // from class:
                                           // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda3
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                byte[] buildGetCursorCapsModeProto;
                                buildGetCursorCapsModeProto =
                                        InputConnectionProtoDumper.buildGetCursorCapsModeProto(
                                                reqModes, ((Integer) obj).intValue());
                                return buildGetCursorCapsModeProto;
                            }
                        }
                        : null);
    }

    public /* synthetic */ Integer lambda$getCursorCapsMode$13(
            InputConnectionCommandHeader header, int reqModes) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return 0;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "getCursorCapsMode on inactive InputConnection");
            return 0;
        }
        return Integer.valueOf(ic.getCursorCapsMode(reqModes));
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void getExtractedText(
            final InputConnectionCommandHeader header,
            final ExtractedTextRequest request,
            final int flags,
            AndroidFuture future) {
        dispatchWithTracing(
                "getExtractedText",
                future,
                new Supplier() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda21
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        ExtractedText lambda$getExtractedText$15;
                        lambda$getExtractedText$15 =
                                RemoteInputConnectionImpl.this.lambda$getExtractedText$15(
                                        header, request, flags);
                        return lambda$getExtractedText$15;
                    }
                },
                useImeTracing()
                        ? new Function() { // from class:
                                           // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda22
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                byte[] buildGetExtractedTextProto;
                                buildGetExtractedTextProto =
                                        InputConnectionProtoDumper.buildGetExtractedTextProto(
                                                ExtractedTextRequest.this,
                                                flags,
                                                (ExtractedText) obj);
                                return buildGetExtractedTextProto;
                            }
                        }
                        : null);
    }

    public /* synthetic */ ExtractedText lambda$getExtractedText$15(
            InputConnectionCommandHeader header, ExtractedTextRequest request, int flags) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return null;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "getExtractedText on inactive InputConnection");
            return null;
        }
        return ic.getExtractedText(request, flags);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void commitText(
            final InputConnectionCommandHeader header,
            final CharSequence text,
            final int newCursorPosition) {
        dispatchWithTracing(
                "commitText",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda46
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$commitText$17(
                                header, text, newCursorPosition);
                    }
                });
    }

    public /* synthetic */ void lambda$commitText$17(
            InputConnectionCommandHeader header, CharSequence text, int newCursorPosition) {
        if (!isInjectionFromKcap(header.mSessionId)
                && header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "commitText on inactive InputConnection");
        } else {
            ic.commitText(text, newCursorPosition);
        }
    }

    private boolean isInjectionFromKcap(int sessionId) {
        return sessionId == 9999 && SystemProperties.getInt("sys.datawedge.prop", 0) == 1;
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void commitTextWithTextAttribute(
            final InputConnectionCommandHeader header,
            final CharSequence text,
            final int newCursorPosition,
            final TextAttribute textAttribute) {
        dispatchWithTracing(
                "commitTextWithTextAttribute",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda25
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$commitTextWithTextAttribute$18(
                                header, text, newCursorPosition, textAttribute);
                    }
                });
    }

    public /* synthetic */ void lambda$commitTextWithTextAttribute$18(
            InputConnectionCommandHeader header,
            CharSequence text,
            int newCursorPosition,
            TextAttribute textAttribute) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "commitText on inactive InputConnection");
        } else {
            ic.commitText(text, newCursorPosition, textAttribute);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void commitCompletion(
            final InputConnectionCommandHeader header, final CompletionInfo text) {
        dispatchWithTracing(
                "commitCompletion",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$commitCompletion$19(header, text);
                    }
                });
    }

    public /* synthetic */ void lambda$commitCompletion$19(
            InputConnectionCommandHeader header, CompletionInfo text) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "commitCompletion on inactive InputConnection");
        } else {
            ic.commitCompletion(text);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void commitCorrection(
            final InputConnectionCommandHeader header, final CorrectionInfo info) {
        dispatchWithTracing(
                "commitCorrection",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$commitCorrection$20(header, info);
                    }
                });
    }

    public /* synthetic */ void lambda$commitCorrection$20(
            InputConnectionCommandHeader header, CorrectionInfo info) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "commitCorrection on inactive InputConnection");
        } else {
            try {
                ic.commitCorrection(info);
            } catch (AbstractMethodError e) {
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setSelection(
            final InputConnectionCommandHeader header, final int start, final int end) {
        dispatchWithTracing(
                "setSelection",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$setSelection$21(header, start, end);
                    }
                });
    }

    public /* synthetic */ void lambda$setSelection$21(
            InputConnectionCommandHeader header, int start, int end) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "setSelection on inactive InputConnection");
        } else {
            ic.setSelection(start, end);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void performEditorAction(final InputConnectionCommandHeader header, final int id) {
        dispatchWithTracing(
                "performEditorAction",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda32
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$performEditorAction$22(header, id);
                    }
                });
    }

    public /* synthetic */ void lambda$performEditorAction$22(
            InputConnectionCommandHeader header, int id) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "performEditorAction on inactive InputConnection");
        } else {
            ic.performEditorAction(id);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void performContextMenuAction(final InputConnectionCommandHeader header, final int id) {
        dispatchWithTracing(
                "performContextMenuAction",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$performContextMenuAction$23(
                                header, id);
                    }
                });
    }

    public /* synthetic */ void lambda$performContextMenuAction$23(
            InputConnectionCommandHeader header, int id) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "performContextMenuAction on inactive InputConnection");
        } else {
            ic.performContextMenuAction(id);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setComposingRegion(
            final InputConnectionCommandHeader header, final int start, final int end) {
        dispatchWithTracing(
                "setComposingRegion",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$setComposingRegion$24(
                                header, start, end);
                    }
                });
    }

    public /* synthetic */ void lambda$setComposingRegion$24(
            InputConnectionCommandHeader header, int start, int end) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "setComposingRegion on inactive InputConnection");
        } else {
            try {
                ic.setComposingRegion(start, end);
            } catch (AbstractMethodError e) {
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setComposingRegionWithTextAttribute(
            final InputConnectionCommandHeader header,
            final int start,
            final int end,
            final TextAttribute textAttribute) {
        dispatchWithTracing(
                "setComposingRegionWithTextAttribute",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this
                                .lambda$setComposingRegionWithTextAttribute$25(
                                        header, start, end, textAttribute);
                    }
                });
    }

    public /* synthetic */ void lambda$setComposingRegionWithTextAttribute$25(
            InputConnectionCommandHeader header, int start, int end, TextAttribute textAttribute) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "setComposingRegion on inactive InputConnection");
        } else {
            ic.setComposingRegion(start, end, textAttribute);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setComposingText(
            final InputConnectionCommandHeader header,
            final CharSequence text,
            final int newCursorPosition) {
        dispatchWithTracing(
                "setComposingText",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda27
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$setComposingText$26(
                                header, text, newCursorPosition);
                    }
                });
    }

    public /* synthetic */ void lambda$setComposingText$26(
            InputConnectionCommandHeader header, CharSequence text, int newCursorPosition) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "setComposingText on inactive InputConnection");
        } else {
            ic.setComposingText(text, newCursorPosition);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setComposingTextWithTextAttribute(
            final InputConnectionCommandHeader header,
            final CharSequence text,
            final int newCursorPosition,
            final TextAttribute textAttribute) {
        dispatchWithTracing(
                "setComposingTextWithTextAttribute",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda48
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$setComposingTextWithTextAttribute$27(
                                header, text, newCursorPosition, textAttribute);
                    }
                });
    }

    public /* synthetic */ void lambda$setComposingTextWithTextAttribute$27(
            InputConnectionCommandHeader header,
            CharSequence text,
            int newCursorPosition,
            TextAttribute textAttribute) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "setComposingText on inactive InputConnection");
        } else {
            ic.setComposingText(text, newCursorPosition, textAttribute);
        }
    }

    public void finishComposingTextFromImm() {
        final int currentSessionId = this.mCurrentSessionId.get();
        dispatchWithTracing(
                "finishComposingTextFromImm",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$finishComposingTextFromImm$28(
                                currentSessionId);
                    }
                });
    }

    public /* synthetic */ void lambda$finishComposingTextFromImm$28(int currentSessionId) {
        if (isFinished() || currentSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "finishComposingTextFromImm on inactive InputConnection");
        } else {
            ic.finishComposingText();
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void finishComposingText(final InputConnectionCommandHeader header) {
        dispatchWithTracing(
                "finishComposingText",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda19
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$finishComposingText$29(header);
                    }
                });
    }

    public /* synthetic */ void lambda$finishComposingText$29(InputConnectionCommandHeader header) {
        if (isFinished() || header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null && this.mDeactivateRequested.get()) {
            Log.w(TAG, "finishComposingText on inactive InputConnection");
        } else {
            ic.finishComposingText();
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void sendKeyEvent(final InputConnectionCommandHeader header, final KeyEvent event) {
        dispatchWithTracing(
                "sendKeyEvent",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda41
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$sendKeyEvent$30(header, event);
                    }
                });
    }

    public /* synthetic */ void lambda$sendKeyEvent$30(
            InputConnectionCommandHeader header, KeyEvent event) {
        if (!isInjectionFromKcap(header.mSessionId)
                && header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "sendKeyEvent on inactive InputConnection");
        } else {
            ic.sendKeyEvent(event);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void clearMetaKeyStates(final InputConnectionCommandHeader header, final int states) {
        dispatchWithTracing(
                "clearMetaKeyStates",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$clearMetaKeyStates$31(header, states);
                    }
                });
    }

    public /* synthetic */ void lambda$clearMetaKeyStates$31(
            InputConnectionCommandHeader header, int states) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "clearMetaKeyStates on inactive InputConnection");
        } else {
            ic.clearMetaKeyStates(states);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void deleteSurroundingText(
            final InputConnectionCommandHeader header,
            final int beforeLength,
            final int afterLength) {
        dispatchWithTracing(
                "deleteSurroundingText",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda44
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$deleteSurroundingText$32(
                                header, beforeLength, afterLength);
                    }
                });
    }

    public /* synthetic */ void lambda$deleteSurroundingText$32(
            InputConnectionCommandHeader header, int beforeLength, int afterLength) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "deleteSurroundingText on inactive InputConnection");
        } else {
            ic.deleteSurroundingText(beforeLength, afterLength);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void deleteSurroundingTextInCodePoints(
            final InputConnectionCommandHeader header,
            final int beforeLength,
            final int afterLength) {
        dispatchWithTracing(
                "deleteSurroundingTextInCodePoints",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda20
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$deleteSurroundingTextInCodePoints$33(
                                header, beforeLength, afterLength);
                    }
                });
    }

    public /* synthetic */ void lambda$deleteSurroundingTextInCodePoints$33(
            InputConnectionCommandHeader header, int beforeLength, int afterLength) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "deleteSurroundingTextInCodePoints on inactive InputConnection");
        } else {
            try {
                ic.deleteSurroundingTextInCodePoints(beforeLength, afterLength);
            } catch (AbstractMethodError e) {
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void beginBatchEdit(final InputConnectionCommandHeader header) {
        dispatchWithTracing(
                "beginBatchEdit",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$beginBatchEdit$34(header);
                    }
                });
    }

    public /* synthetic */ void lambda$beginBatchEdit$34(InputConnectionCommandHeader header) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "beginBatchEdit on inactive InputConnection");
        } else {
            ic.beginBatchEdit();
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void endBatchEdit(final InputConnectionCommandHeader header) {
        dispatchWithTracing(
                "endBatchEdit",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$endBatchEdit$35(header);
                    }
                });
    }

    public /* synthetic */ void lambda$endBatchEdit$35(InputConnectionCommandHeader header) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "endBatchEdit on inactive InputConnection");
        } else {
            ic.endBatchEdit();
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void performSpellCheck(final InputConnectionCommandHeader header) {
        dispatchWithTracing(
                "performSpellCheck",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda40
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$performSpellCheck$36(header);
                    }
                });
    }

    public /* synthetic */ void lambda$performSpellCheck$36(InputConnectionCommandHeader header) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "performSpellCheck on inactive InputConnection");
        } else {
            ic.performSpellCheck();
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void performPrivateCommand(
            final InputConnectionCommandHeader header, final String action, final Bundle data) {
        dispatchWithTracing(
                "performPrivateCommand",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda39
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$performPrivateCommand$37(
                                action, header, data);
                    }
                });
    }

    public /* synthetic */ void lambda$performPrivateCommand$37(
            String action, InputConnectionCommandHeader header, Bundle data) {
        if (ViewRune.SUPPORT_WRITING_TOOLKIT
                && SemInputMethodManagerUtils.ACTION_DEACTIVATE.equals(action)) {
            setWritingToolkitMode(false);
            deactivate();
        } else {
            if (header.mSessionId != this.mCurrentSessionId.get()) {
                return;
            }
            InputConnection ic = getInputConnection();
            if (ic == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "performPrivateCommand on inactive InputConnection");
            } else {
                ic.performPrivateCommand(action, data);
            }
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void performHandwritingGesture(
            final InputConnectionCommandHeader header,
            ParcelableHandwritingGesture gestureContainer,
            final ResultReceiver resultReceiver) {
        final HandwritingGesture gesture = gestureContainer.get();
        if (gesture instanceof CancellableHandwritingGesture) {
            CancellableHandwritingGesture cancellableGesture =
                    (CancellableHandwritingGesture) gesture;
            cancellableGesture.unbeamCancellationSignal(getCancellationSignalBeamer());
            if (cancellableGesture.getCancellationSignal() != null
                    && cancellableGesture.getCancellationSignal().isCanceled()) {
                if (resultReceiver != null) {
                    resultReceiver.send(4, null);
                    return;
                }
                return;
            }
        }
        dispatchWithTracing(
                "performHandwritingGesture",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda28
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$performHandwritingGesture$39(
                                header, resultReceiver, gesture);
                    }
                });
    }

    public /* synthetic */ void lambda$performHandwritingGesture$39(
            InputConnectionCommandHeader header,
            final ResultReceiver resultReceiver,
            HandwritingGesture gesture) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            if (resultReceiver != null) {
                resultReceiver.send(4, null);
                return;
            }
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "performHandwritingGesture on inactive InputConnection");
            if (resultReceiver != null) {
                resultReceiver.send(4, null);
                return;
            }
            return;
        }
        ic.performHandwritingGesture(
                gesture,
                resultReceiver != null ? new PendingIntent$$ExternalSyntheticLambda0() : null,
                resultReceiver != null
                        ? new IntConsumer() { // from class:
                                              // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda26
                            @Override // java.util.function.IntConsumer
                            public final void accept(int i) {
                                ResultReceiver.this.send(i, null);
                            }
                        }
                        : null);
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void previewHandwritingGesture(
            final InputConnectionCommandHeader header,
            ParcelableHandwritingGesture gestureContainer,
            IBinder cancellationSignalToken) {
        final CancellationSignal cancellationSignal =
                cancellationSignalToken != null
                        ? getCancellationSignalBeamer().unbeam(cancellationSignalToken)
                        : null;
        final PreviewableHandwritingGesture gesture =
                (PreviewableHandwritingGesture) gestureContainer.get();
        dispatchWithTracing(
                "previewHandwritingGesture",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$previewHandwritingGesture$40(
                                header, cancellationSignal, gesture);
                    }
                });
    }

    public /* synthetic */ void lambda$previewHandwritingGesture$40(
            InputConnectionCommandHeader header,
            CancellationSignal cancellationSignal,
            PreviewableHandwritingGesture gesture) {
        if (header.mSessionId == this.mCurrentSessionId.get()) {
            if (cancellationSignal != null && cancellationSignal.isCanceled()) {
                return;
            }
            InputConnection ic = getInputConnection();
            if (ic == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "previewHandwritingGesture on inactive InputConnection");
            } else {
                ic.previewHandwritingGesture(gesture, cancellationSignal);
            }
        }
    }

    private CancellationSignalBeamer.Receiver getCancellationSignalBeamer() {
        if (this.mBeamer != null) {
            return this.mBeamer;
        }
        this.mBeamer = new CancellationSignalBeamer.Receiver(true);
        return this.mBeamer;
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void requestCursorUpdates(
            final InputConnectionCommandHeader header,
            final int cursorUpdateMode,
            final int imeDisplayId,
            AndroidFuture future) {
        dispatchWithTracing(
                "requestCursorUpdates",
                future,
                new Supplier() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda47
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Boolean lambda$requestCursorUpdates$41;
                        lambda$requestCursorUpdates$41 =
                                RemoteInputConnectionImpl.this.lambda$requestCursorUpdates$41(
                                        header, cursorUpdateMode, imeDisplayId);
                        return lambda$requestCursorUpdates$41;
                    }
                });
    }

    public /* synthetic */ Boolean lambda$requestCursorUpdates$41(
            InputConnectionCommandHeader header, int cursorUpdateMode, int imeDisplayId) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return false;
        }
        return Boolean.valueOf(requestCursorUpdatesInternal(cursorUpdateMode, 0, imeDisplayId));
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void requestCursorUpdatesWithFilter(
            final InputConnectionCommandHeader header,
            final int cursorUpdateMode,
            final int cursorUpdateFilter,
            final int imeDisplayId,
            AndroidFuture future) {
        dispatchWithTracing(
                "requestCursorUpdates",
                future,
                new Supplier() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda11
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Boolean lambda$requestCursorUpdatesWithFilter$42;
                        lambda$requestCursorUpdatesWithFilter$42 =
                                RemoteInputConnectionImpl.this
                                        .lambda$requestCursorUpdatesWithFilter$42(
                                                header,
                                                cursorUpdateMode,
                                                cursorUpdateFilter,
                                                imeDisplayId);
                        return lambda$requestCursorUpdatesWithFilter$42;
                    }
                });
    }

    public /* synthetic */ Boolean lambda$requestCursorUpdatesWithFilter$42(
            InputConnectionCommandHeader header,
            int cursorUpdateMode,
            int cursorUpdateFilter,
            int imeDisplayId) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return false;
        }
        return Boolean.valueOf(
                requestCursorUpdatesInternal(cursorUpdateMode, cursorUpdateFilter, imeDisplayId));
    }

    private boolean requestCursorUpdatesInternal(
            int cursorUpdateMode, int cursorUpdateFilter, int imeDisplayId) {
        InputConnection ic = getInputConnection();
        boolean z = false;
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "requestCursorUpdates on inactive InputConnection");
            return false;
        }
        if (this.mParentInputMethodManager.mRequestCursorUpdateDisplayIdCheck.get()
                && this.mParentInputMethodManager.getDisplayId() != imeDisplayId) {
            return false;
        }
        boolean hasImmediate = (cursorUpdateMode & 1) != 0;
        boolean hasMonitoring = (cursorUpdateMode & 2) != 0;
        try {
            boolean result = ic.requestCursorUpdates(cursorUpdateMode, cursorUpdateFilter);
            this.mHasPendingImmediateCursorAnchorInfoUpdate.set(result && hasImmediate);
            AtomicBoolean atomicBoolean = this.mIsCursorAnchorInfoMonitoring;
            if (result && hasMonitoring) {
                z = true;
            }
            atomicBoolean.set(z);
            return result;
        } catch (AbstractMethodError e) {
            this.mHasPendingImmediateCursorAnchorInfoUpdate.set(0 != 0 && hasImmediate);
            this.mIsCursorAnchorInfoMonitoring.set(0 != 0 && hasMonitoring);
            return false;
        } catch (Throwable th) {
            this.mHasPendingImmediateCursorAnchorInfoUpdate.set(0 != 0 && hasImmediate);
            AtomicBoolean atomicBoolean2 = this.mIsCursorAnchorInfoMonitoring;
            if (0 != 0 && hasMonitoring) {
                z = true;
            }
            atomicBoolean2.set(z);
            throw th;
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void requestTextBoundsInfo(
            final InputConnectionCommandHeader header,
            final RectF bounds,
            final ResultReceiver resultReceiver) {
        dispatchWithTracing(
                "requestTextBoundsInfo",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda43
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$requestTextBoundsInfo$44(
                                header, resultReceiver, bounds);
                    }
                });
    }

    public /* synthetic */ void lambda$requestTextBoundsInfo$44(
            InputConnectionCommandHeader header,
            final ResultReceiver resultReceiver,
            RectF bounds) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            resultReceiver.send(3, null);
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "requestTextBoundsInfo on inactive InputConnection");
            resultReceiver.send(3, null);
        } else {
            ic.requestTextBoundsInfo(
                    bounds,
                    new PendingIntent$$ExternalSyntheticLambda0(),
                    new Consumer() { // from class:
                                     // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda42
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            RemoteInputConnectionImpl.lambda$requestTextBoundsInfo$43(
                                    ResultReceiver.this, (TextBoundsInfoResult) obj);
                        }
                    });
        }
    }

    static /* synthetic */ void lambda$requestTextBoundsInfo$43(
            ResultReceiver resultReceiver, TextBoundsInfoResult textBoundsInfoResult) {
        int resultCode = textBoundsInfoResult.getResultCode();
        TextBoundsInfo textBoundsInfo = textBoundsInfoResult.getTextBoundsInfo();
        resultReceiver.send(resultCode, textBoundsInfo == null ? null : textBoundsInfo.toBundle());
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void commitContent(
            final InputConnectionCommandHeader header,
            final InputContentInfo inputContentInfo,
            final int flags,
            final Bundle opts,
            AndroidFuture future) {
        final int imeUid = Binder.getCallingUid();
        dispatchWithTracing(
                "commitContent",
                future,
                new Supplier() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda4
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Boolean lambda$commitContent$45;
                        lambda$commitContent$45 =
                                RemoteInputConnectionImpl.this.lambda$commitContent$45(
                                        inputContentInfo, imeUid, header, flags, opts);
                        return lambda$commitContent$45;
                    }
                });
    }

    public /* synthetic */ Boolean lambda$commitContent$45(
            InputContentInfo inputContentInfo,
            int imeUid,
            InputConnectionCommandHeader header,
            int flags,
            Bundle opts) {
        try {
            int contentUriOwnerUserId =
                    ContentProvider.getUserIdFromUri(
                            inputContentInfo.getContentUri(), UserHandle.getUserId(imeUid));
            Uri contentUriWithoutUserId =
                    ContentProvider.getUriWithoutUserId(inputContentInfo.getContentUri());
            UriGrantsManager.getService()
                    .checkGrantUriPermission_ignoreNonSystem(
                            imeUid, null, contentUriWithoutUserId, 1, contentUriOwnerUserId);
            if (header.mSessionId != this.mCurrentSessionId.get()) {
                return false;
            }
            InputConnection ic = getInputConnection();
            if (ic == null || this.mDeactivateRequested.get()) {
                Log.w(TAG, "commitContent on inactive InputConnection");
                return false;
            }
            if (inputContentInfo == null || !inputContentInfo.validate()) {
                Log.w(TAG, "commitContent with invalid inputContentInfo=" + inputContentInfo);
                return false;
            }
            try {
                return Boolean.valueOf(ic.commitContent(inputContentInfo, flags, opts));
            } catch (AbstractMethodError e) {
                return false;
            }
        } catch (Exception e2) {
            Log.w(TAG, "commitContent with invalid Uri permission from IME:", e2);
            return false;
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void setImeConsumesInput(
            final InputConnectionCommandHeader header, final boolean imeConsumesInput) {
        dispatchWithTracing(
                "setImeConsumesInput",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda29
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$setImeConsumesInput$46(
                                header, imeConsumesInput);
                    }
                });
    }

    public /* synthetic */ void lambda$setImeConsumesInput$46(
            InputConnectionCommandHeader header, boolean imeConsumesInput) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "setImeConsumesInput on inactive InputConnection");
        } else {
            ic.setImeConsumesInput(imeConsumesInput);
        }
    }

    @Override // com.android.internal.inputmethod.IRemoteInputConnection
    public void replaceText(
            final InputConnectionCommandHeader header,
            final int start,
            final int end,
            final CharSequence text,
            final int newCursorPosition,
            final TextAttribute textAttribute) {
        dispatchWithTracing(
                "replaceText",
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda49
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$replaceText$47(
                                header, start, end, text, newCursorPosition, textAttribute);
                    }
                });
    }

    public /* synthetic */ void lambda$replaceText$47(
            InputConnectionCommandHeader header,
            int start,
            int end,
            CharSequence text,
            int newCursorPosition,
            TextAttribute textAttribute) {
        if (header.mSessionId != this.mCurrentSessionId.get()) {
            return;
        }
        InputConnection ic = getInputConnection();
        if (ic == null || this.mDeactivateRequested.get()) {
            Log.w(TAG, "replaceText on inactive InputConnection");
        } else {
            ic.replaceText(start, end, text, newCursorPosition, textAttribute);
        }
    }

    /* renamed from: android.view.inputmethod.RemoteInputConnectionImpl$1, reason: invalid class name */
    class AnonymousClass1 extends IRemoteAccessibilityInputConnection.Stub {
        AnonymousClass1() {}

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void commitText(
                final InputConnectionCommandHeader header,
                final CharSequence text,
                final int newCursorPosition,
                final TextAttribute textAttribute) {
            RemoteInputConnectionImpl.this.dispatchWithTracing(
                    "commitTextFromA11yIme",
                    new Runnable() { // from class:
                                     // android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteInputConnectionImpl.AnonymousClass1.this.lambda$commitText$0(
                                    header, text, newCursorPosition, textAttribute);
                        }
                    });
        }

        public /* synthetic */ void lambda$commitText$0(
                InputConnectionCommandHeader header,
                CharSequence text,
                int newCursorPosition,
                TextAttribute textAttribute) {
            if (header.mSessionId != RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return;
            }
            InputConnection ic = RemoteInputConnectionImpl.this.getInputConnection();
            if (ic == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                Log.w(RemoteInputConnectionImpl.TAG, "commitText on inactive InputConnection");
                return;
            }
            ic.beginBatchEdit();
            ic.finishComposingText();
            ic.commitText(text, newCursorPosition, textAttribute);
            ic.endBatchEdit();
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void setSelection(
                final InputConnectionCommandHeader header, final int start, final int end) {
            RemoteInputConnectionImpl.this.dispatchWithTracing(
                    "setSelectionFromA11yIme",
                    new Runnable() { // from class:
                                     // android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteInputConnectionImpl.AnonymousClass1.this.lambda$setSelection$1(
                                    header, start, end);
                        }
                    });
        }

        public /* synthetic */ void lambda$setSelection$1(
                InputConnectionCommandHeader header, int start, int end) {
            if (header.mSessionId != RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return;
            }
            InputConnection ic = RemoteInputConnectionImpl.this.getInputConnection();
            if (ic == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                Log.w(RemoteInputConnectionImpl.TAG, "setSelection on inactive InputConnection");
            } else {
                ic.setSelection(start, end);
            }
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void getSurroundingText(
                final InputConnectionCommandHeader header,
                final int beforeLength,
                final int afterLength,
                final int flags,
                AndroidFuture future) {
            RemoteInputConnectionImpl.this.dispatchWithTracing(
                    "getSurroundingTextFromA11yIme",
                    future,
                    new Supplier() { // from class:
                                     // android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda3
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            SurroundingText lambda$getSurroundingText$2;
                            lambda$getSurroundingText$2 =
                                    RemoteInputConnectionImpl.AnonymousClass1.this
                                            .lambda$getSurroundingText$2(
                                                    header, beforeLength, afterLength, flags);
                            return lambda$getSurroundingText$2;
                        }
                    },
                    RemoteInputConnectionImpl.useImeTracing()
                            ? new Function() { // from class:
                                               // android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda4
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    byte[] buildGetSurroundingTextProto;
                                    buildGetSurroundingTextProto =
                                            InputConnectionProtoDumper.buildGetSurroundingTextProto(
                                                    beforeLength,
                                                    afterLength,
                                                    flags,
                                                    (SurroundingText) obj);
                                    return buildGetSurroundingTextProto;
                                }
                            }
                            : null);
        }

        public /* synthetic */ SurroundingText lambda$getSurroundingText$2(
                InputConnectionCommandHeader header, int beforeLength, int afterLength, int flags) {
            if (header.mSessionId != RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return null;
            }
            InputConnection ic = RemoteInputConnectionImpl.this.getInputConnection();
            if (ic == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                Log.w(
                        RemoteInputConnectionImpl.TAG,
                        "getSurroundingText on inactive InputConnection");
                return null;
            }
            if (beforeLength < 0) {
                Log.i(
                        RemoteInputConnectionImpl.TAG,
                        "Returning null to getSurroundingText due to an invalid beforeLength="
                                + beforeLength);
                return null;
            }
            if (afterLength < 0) {
                Log.i(
                        RemoteInputConnectionImpl.TAG,
                        "Returning null to getSurroundingText due to an invalid afterLength="
                                + afterLength);
                return null;
            }
            return ic.getSurroundingText(beforeLength, afterLength, flags);
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void deleteSurroundingText(
                final InputConnectionCommandHeader header,
                final int beforeLength,
                final int afterLength) {
            RemoteInputConnectionImpl.this.dispatchWithTracing(
                    "deleteSurroundingTextFromA11yIme",
                    new Runnable() { // from class:
                                     // android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteInputConnectionImpl.AnonymousClass1.this
                                    .lambda$deleteSurroundingText$4(
                                            header, beforeLength, afterLength);
                        }
                    });
        }

        public /* synthetic */ void lambda$deleteSurroundingText$4(
                InputConnectionCommandHeader header, int beforeLength, int afterLength) {
            if (header.mSessionId != RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return;
            }
            InputConnection ic = RemoteInputConnectionImpl.this.getInputConnection();
            if (ic == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                Log.w(
                        RemoteInputConnectionImpl.TAG,
                        "deleteSurroundingText on inactive InputConnection");
            } else {
                ic.deleteSurroundingText(beforeLength, afterLength);
            }
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void sendKeyEvent(final InputConnectionCommandHeader header, final KeyEvent event) {
            RemoteInputConnectionImpl.this.dispatchWithTracing(
                    "sendKeyEventFromA11yIme",
                    new Runnable() { // from class:
                                     // android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteInputConnectionImpl.AnonymousClass1.this.lambda$sendKeyEvent$5(
                                    header, event);
                        }
                    });
        }

        public /* synthetic */ void lambda$sendKeyEvent$5(
                InputConnectionCommandHeader header, KeyEvent event) {
            if (header.mSessionId != RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return;
            }
            InputConnection ic = RemoteInputConnectionImpl.this.getInputConnection();
            if (ic == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                Log.w(RemoteInputConnectionImpl.TAG, "sendKeyEvent on inactive InputConnection");
            } else {
                ic.sendKeyEvent(event);
            }
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void performEditorAction(final InputConnectionCommandHeader header, final int id) {
            RemoteInputConnectionImpl.this.dispatchWithTracing(
                    "performEditorActionFromA11yIme",
                    new Runnable() { // from class:
                                     // android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteInputConnectionImpl.AnonymousClass1.this
                                    .lambda$performEditorAction$6(header, id);
                        }
                    });
        }

        public /* synthetic */ void lambda$performEditorAction$6(
                InputConnectionCommandHeader header, int id) {
            if (header.mSessionId != RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return;
            }
            InputConnection ic = RemoteInputConnectionImpl.this.getInputConnection();
            if (ic == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                Log.w(
                        RemoteInputConnectionImpl.TAG,
                        "performEditorAction on inactive InputConnection");
            } else {
                ic.performEditorAction(id);
            }
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void performContextMenuAction(
                final InputConnectionCommandHeader header, final int id) {
            RemoteInputConnectionImpl.this.dispatchWithTracing(
                    "performContextMenuActionFromA11yIme",
                    new Runnable() { // from class:
                                     // android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteInputConnectionImpl.AnonymousClass1.this
                                    .lambda$performContextMenuAction$7(header, id);
                        }
                    });
        }

        public /* synthetic */ void lambda$performContextMenuAction$7(
                InputConnectionCommandHeader header, int id) {
            if (header.mSessionId != RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return;
            }
            InputConnection ic = RemoteInputConnectionImpl.this.getInputConnection();
            if (ic == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                Log.w(
                        RemoteInputConnectionImpl.TAG,
                        "performContextMenuAction on inactive InputConnection");
            } else {
                ic.performContextMenuAction(id);
            }
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void getCursorCapsMode(
                final InputConnectionCommandHeader header,
                final int reqModes,
                AndroidFuture future) {
            RemoteInputConnectionImpl.this.dispatchWithTracing(
                    "getCursorCapsModeFromA11yIme",
                    future,
                    new Supplier() { // from class:
                                     // android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda7
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            Integer lambda$getCursorCapsMode$8;
                            lambda$getCursorCapsMode$8 =
                                    RemoteInputConnectionImpl.AnonymousClass1.this
                                            .lambda$getCursorCapsMode$8(header, reqModes);
                            return lambda$getCursorCapsMode$8;
                        }
                    },
                    RemoteInputConnectionImpl.useImeTracing()
                            ? new Function() { // from class:
                                               // android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda8
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    byte[] buildGetCursorCapsModeProto;
                                    buildGetCursorCapsModeProto =
                                            InputConnectionProtoDumper.buildGetCursorCapsModeProto(
                                                    reqModes, ((Integer) obj).intValue());
                                    return buildGetCursorCapsModeProto;
                                }
                            }
                            : null);
        }

        public /* synthetic */ Integer lambda$getCursorCapsMode$8(
                InputConnectionCommandHeader header, int reqModes) {
            if (header.mSessionId != RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return 0;
            }
            InputConnection ic = RemoteInputConnectionImpl.this.getInputConnection();
            if (ic == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                Log.w(
                        RemoteInputConnectionImpl.TAG,
                        "getCursorCapsMode on inactive InputConnection");
                return 0;
            }
            return Integer.valueOf(ic.getCursorCapsMode(reqModes));
        }

        @Override // com.android.internal.inputmethod.IRemoteAccessibilityInputConnection
        public void clearMetaKeyStates(
                final InputConnectionCommandHeader header, final int states) {
            RemoteInputConnectionImpl.this.dispatchWithTracing(
                    "clearMetaKeyStatesFromA11yIme",
                    new Runnable() { // from class:
                                     // android.view.inputmethod.RemoteInputConnectionImpl$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteInputConnectionImpl.AnonymousClass1.this
                                    .lambda$clearMetaKeyStates$10(header, states);
                        }
                    });
        }

        public /* synthetic */ void lambda$clearMetaKeyStates$10(
                InputConnectionCommandHeader header, int states) {
            if (header.mSessionId != RemoteInputConnectionImpl.this.mCurrentSessionId.get()) {
                return;
            }
            InputConnection ic = RemoteInputConnectionImpl.this.getInputConnection();
            if (ic == null || RemoteInputConnectionImpl.this.mDeactivateRequested.get()) {
                Log.w(
                        RemoteInputConnectionImpl.TAG,
                        "clearMetaKeyStates on inactive InputConnection");
            } else {
                ic.clearMetaKeyStates(states);
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

    public void dispatchWithTracing(final String methodName, final Runnable runnable) {
        Runnable actualRunnable;
        if (Trace.isTagEnabled(4L)) {
            actualRunnable =
                    new Runnable() { // from class:
                                     // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteInputConnectionImpl.lambda$dispatchWithTracing$48(
                                    methodName, runnable);
                        }
                    };
        } else {
            actualRunnable = runnable;
        }
        dispatch(actualRunnable);
    }

    static /* synthetic */ void lambda$dispatchWithTracing$48(
            String methodName, Runnable runnable) {
        Trace.traceBegin(4L, "InputConnection#" + methodName);
        try {
            runnable.run();
        } finally {
            Trace.traceEnd(4L);
        }
    }

    private <T> void dispatchWithTracing(
            String methodName, AndroidFuture untypedFuture, Supplier<T> supplier) {
        dispatchWithTracing(methodName, untypedFuture, supplier, null);
    }

    public <T> void dispatchWithTracing(
            final String methodName,
            final AndroidFuture untypedFuture,
            final Supplier<T> supplier,
            final Function<T, byte[]> dumpProtoProvider) {
        dispatchWithTracing(
                methodName,
                new Runnable() { // from class:
                                 // android.view.inputmethod.RemoteInputConnectionImpl$$ExternalSyntheticLambda35
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemoteInputConnectionImpl.this.lambda$dispatchWithTracing$49(
                                supplier, untypedFuture, dumpProtoProvider, methodName);
                    }
                });
    }

    public /* synthetic */ void lambda$dispatchWithTracing$49(
            Supplier supplier,
            AndroidFuture future,
            Function dumpProtoProvider,
            String methodName) {
        try {
            Object obj = supplier.get();
            future.complete(obj);
            if (dumpProtoProvider != null) {
                byte[] icProto = (byte[]) dumpProtoProvider.apply(obj);
                ImeTracing.getInstance()
                        .triggerClientDump(
                                "RemoteInputConnectionImpl#" + methodName,
                                this.mParentInputMethodManager,
                                icProto);
            }
        } catch (Throwable throwable) {
            future.completeExceptionally(throwable);
            throw throwable;
        }
    }

    public static boolean useImeTracing() {
        return ImeTracing.getInstance().isEnabled();
    }

    public void setWritingToolkitMode(boolean mode) {
        this.mWritingToolkitMode = mode;
    }

    public boolean getWritingToolkitMode() {
        return this.mWritingToolkitMode;
    }
}
