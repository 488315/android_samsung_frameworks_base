package android.inputmethodservice;

import android.graphics.RectF;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.CancellationSignalBeamer;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.HandwritingGesture;
import android.view.inputmethod.InputContentInfo;
import android.view.inputmethod.ParcelableHandwritingGesture;
import android.view.inputmethod.SurroundingText;
import android.view.inputmethod.TextAttribute;
import android.view.inputmethod.TextBoundsInfo;
import android.view.inputmethod.TextBoundsInfoResult;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.inputmethod.IRemoteInputConnection;
import com.android.internal.inputmethod.InputConnectionCommandHeader;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* loaded from: classes2.dex */
final class IRemoteInputConnectionInvoker {
    private CancellationSignalBeamer.Sender mBeamer;
    private final IRemoteInputConnection mConnection;
    private final int mSessionId;

    private IRemoteInputConnectionInvoker(IRemoteInputConnection iRemoteInputConnection, int i) {
        this.mConnection = iRemoteInputConnection;
        this.mSessionId = i;
    }

    private static abstract class OnceResultReceiver<C> extends ResultReceiver {
        private C mConsumer;
        private Executor mExecutor;

        protected abstract void dispatch(Executor executor, C c, int i, Bundle bundle);

        protected OnceResultReceiver(Executor executor, C c) {
            super((Handler) null);
            Objects.requireNonNull(executor);
            Objects.requireNonNull(c);
            this.mExecutor = executor;
            this.mConsumer = c;
        }

        @Override // android.os.ResultReceiver
        protected final void onReceiveResult(int i, Bundle bundle) {
            Executor executor;
            C c;
            synchronized (this) {
                executor = this.mExecutor;
                c = this.mConsumer;
                this.mExecutor = null;
                this.mConsumer = null;
            }
            if (executor == null || c == null) {
                return;
            }
            dispatch(executor, c, i, bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class IntResultReceiver extends OnceResultReceiver<IntConsumer> {
        IntResultReceiver(Executor executor, IntConsumer intConsumer) {
            super(executor, intConsumer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.inputmethodservice.IRemoteInputConnectionInvoker.OnceResultReceiver
        public void dispatch(Executor executor, final IntConsumer intConsumer, final int i, Bundle bundle) {
            executor.execute(new Runnable() { // from class: android.inputmethodservice.IRemoteInputConnectionInvoker$IntResultReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    intConsumer.accept(i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class TextBoundsInfoResultReceiver extends OnceResultReceiver<Consumer<TextBoundsInfoResult>> {
        TextBoundsInfoResultReceiver(Executor executor, Consumer<TextBoundsInfoResult> consumer) {
            super(executor, consumer);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.inputmethodservice.IRemoteInputConnectionInvoker.OnceResultReceiver
        public void dispatch(Executor executor, final Consumer<TextBoundsInfoResult> consumer, int i, Bundle bundle) {
            final TextBoundsInfoResult textBoundsInfoResult = new TextBoundsInfoResult(i, TextBoundsInfo.createFromBundle(bundle));
            executor.execute(new Runnable() { // from class: android.inputmethodservice.IRemoteInputConnectionInvoker$TextBoundsInfoResultReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(textBoundsInfoResult);
                }
            });
        }
    }

    public static IRemoteInputConnectionInvoker create(IRemoteInputConnection iRemoteInputConnection) {
        Objects.requireNonNull(iRemoteInputConnection);
        return new IRemoteInputConnectionInvoker(iRemoteInputConnection, 0);
    }

    public IRemoteInputConnectionInvoker cloneWithSessionId(int i) {
        return new IRemoteInputConnectionInvoker(this.mConnection, i);
    }

    public boolean isSameConnection(IRemoteInputConnection iRemoteInputConnection) {
        return iRemoteInputConnection != null && this.mConnection.asBinder() == iRemoteInputConnection.asBinder();
    }

    InputConnectionCommandHeader createHeader() {
        return new InputConnectionCommandHeader(this.mSessionId);
    }

    public AndroidFuture<CharSequence> getTextAfterCursor(int i, int i2) {
        AndroidFuture<CharSequence> androidFuture = new AndroidFuture<>();
        try {
            this.mConnection.getTextAfterCursor(createHeader(), i, i2, androidFuture);
            return androidFuture;
        } catch (RemoteException e) {
            androidFuture.completeExceptionally(e);
            return androidFuture;
        }
    }

    public AndroidFuture<CharSequence> getTextBeforeCursor(int i, int i2) {
        AndroidFuture<CharSequence> androidFuture = new AndroidFuture<>();
        try {
            this.mConnection.getTextBeforeCursor(createHeader(), i, i2, androidFuture);
            return androidFuture;
        } catch (RemoteException e) {
            androidFuture.completeExceptionally(e);
            return androidFuture;
        }
    }

    public AndroidFuture<CharSequence> getSelectedText(int i) {
        AndroidFuture<CharSequence> androidFuture = new AndroidFuture<>();
        try {
            this.mConnection.getSelectedText(createHeader(), i, androidFuture);
            return androidFuture;
        } catch (RemoteException e) {
            androidFuture.completeExceptionally(e);
            return androidFuture;
        }
    }

    public AndroidFuture<SurroundingText> getSurroundingText(int i, int i2, int i3) {
        AndroidFuture<SurroundingText> androidFuture = new AndroidFuture<>();
        try {
            this.mConnection.getSurroundingText(createHeader(), i, i2, i3, androidFuture);
            return androidFuture;
        } catch (RemoteException e) {
            androidFuture.completeExceptionally(e);
            return androidFuture;
        }
    }

    public AndroidFuture<Integer> getCursorCapsMode(int i) {
        AndroidFuture<Integer> androidFuture = new AndroidFuture<>();
        try {
            this.mConnection.getCursorCapsMode(createHeader(), i, androidFuture);
            return androidFuture;
        } catch (RemoteException e) {
            androidFuture.completeExceptionally(e);
            return androidFuture;
        }
    }

    public AndroidFuture<ExtractedText> getExtractedText(ExtractedTextRequest extractedTextRequest, int i) {
        AndroidFuture<ExtractedText> androidFuture = new AndroidFuture<>();
        try {
            this.mConnection.getExtractedText(createHeader(), extractedTextRequest, i, androidFuture);
            return androidFuture;
        } catch (RemoteException e) {
            androidFuture.completeExceptionally(e);
            return androidFuture;
        }
    }

    public boolean commitText(CharSequence charSequence, int i) {
        try {
            this.mConnection.commitText(createHeader(), charSequence, i);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean commitText(CharSequence charSequence, int i, TextAttribute textAttribute) {
        try {
            this.mConnection.commitTextWithTextAttribute(createHeader(), charSequence, i, textAttribute);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean commitCompletion(CompletionInfo completionInfo) {
        try {
            this.mConnection.commitCompletion(createHeader(), completionInfo);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean commitCorrection(CorrectionInfo correctionInfo) {
        try {
            this.mConnection.commitCorrection(createHeader(), correctionInfo);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setSelection(int i, int i2) {
        try {
            this.mConnection.setSelection(createHeader(), i, i2);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean performEditorAction(int i) {
        try {
            this.mConnection.performEditorAction(createHeader(), i);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean performContextMenuAction(int i) {
        try {
            this.mConnection.performContextMenuAction(createHeader(), i);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setComposingRegion(int i, int i2) {
        try {
            this.mConnection.setComposingRegion(createHeader(), i, i2);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setComposingRegion(int i, int i2, TextAttribute textAttribute) {
        try {
            this.mConnection.setComposingRegionWithTextAttribute(createHeader(), i, i2, textAttribute);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setComposingText(CharSequence charSequence, int i) {
        try {
            this.mConnection.setComposingText(createHeader(), charSequence, i);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean setComposingText(CharSequence charSequence, int i, TextAttribute textAttribute) {
        try {
            this.mConnection.setComposingTextWithTextAttribute(createHeader(), charSequence, i, textAttribute);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean finishComposingText() {
        try {
            this.mConnection.finishComposingText(createHeader());
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean beginBatchEdit() {
        try {
            this.mConnection.beginBatchEdit(createHeader());
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean endBatchEdit() {
        try {
            this.mConnection.endBatchEdit(createHeader());
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean sendKeyEvent(KeyEvent keyEvent) {
        try {
            this.mConnection.sendKeyEvent(createHeader(), keyEvent);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean clearMetaKeyStates(int i) {
        try {
            this.mConnection.clearMetaKeyStates(createHeader(), i);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean deleteSurroundingText(int i, int i2) {
        try {
            this.mConnection.deleteSurroundingText(createHeader(), i, i2);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean deleteSurroundingTextInCodePoints(int i, int i2) {
        try {
            this.mConnection.deleteSurroundingTextInCodePoints(createHeader(), i, i2);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean performSpellCheck() {
        try {
            this.mConnection.performSpellCheck(createHeader());
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean performPrivateCommand(String str, Bundle bundle) {
        try {
            this.mConnection.performPrivateCommand(createHeader(), str, bundle);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void performHandwritingGesture(HandwritingGesture handwritingGesture, Executor executor, final IntConsumer intConsumer) {
        IntResultReceiver intResultReceiver;
        if (intConsumer != null) {
            Objects.requireNonNull(executor);
            intResultReceiver = new IntResultReceiver(executor, intConsumer);
        } else {
            intResultReceiver = null;
        }
        try {
            CancellationSignalBeamer.Sender.MustClose mustCloseBeamScopeIfNeeded = getCancellationSignalBeamer().beamScopeIfNeeded(handwritingGesture);
            try {
                this.mConnection.performHandwritingGesture(createHeader(), ParcelableHandwritingGesture.of(handwritingGesture), intResultReceiver);
                if (mustCloseBeamScopeIfNeeded != null) {
                    mustCloseBeamScopeIfNeeded.close();
                }
            } finally {
            }
        } catch (RemoteException unused) {
            if (intConsumer == null || executor == null) {
                return;
            }
            executor.execute(new Runnable() { // from class: android.inputmethodservice.IRemoteInputConnectionInvoker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    intConsumer.accept(4);
                }
            });
        }
    }

    public boolean previewHandwritingGesture(HandwritingGesture handwritingGesture, CancellationSignal cancellationSignal) {
        try {
            CancellationSignalBeamer.Sender.CloseableToken closeableTokenBeam = beam(cancellationSignal);
            try {
                this.mConnection.previewHandwritingGesture(createHeader(), ParcelableHandwritingGesture.of(handwritingGesture), closeableTokenBeam);
                if (closeableTokenBeam == null) {
                    return true;
                }
                closeableTokenBeam.close();
                return true;
            } finally {
            }
        } catch (RemoteException unused) {
            return false;
        }
    }

    CancellationSignalBeamer.Sender.CloseableToken beam(CancellationSignal cancellationSignal) {
        if (cancellationSignal == null) {
            return null;
        }
        return getCancellationSignalBeamer().beam(cancellationSignal);
    }

    private CancellationSignalBeamer.Sender getCancellationSignalBeamer() {
        CancellationSignalBeamer.Sender sender = this.mBeamer;
        if (sender != null) {
            return sender;
        }
        CancellationSignalBeamer.Sender sender2 = new CancellationSignalBeamer.Sender() { // from class: android.inputmethodservice.IRemoteInputConnectionInvoker.1
            @Override // android.os.CancellationSignalBeamer.Sender
            public void onCancel(IBinder iBinder) {
                try {
                    IRemoteInputConnectionInvoker.this.mConnection.cancelCancellationSignal(iBinder);
                } catch (RemoteException unused) {
                }
            }

            @Override // android.os.CancellationSignalBeamer.Sender
            public void onForget(IBinder iBinder) {
                try {
                    IRemoteInputConnectionInvoker.this.mConnection.forgetCancellationSignal(iBinder);
                } catch (RemoteException unused) {
                }
            }
        };
        this.mBeamer = sender2;
        return sender2;
    }

    public AndroidFuture<Boolean> requestCursorUpdates(int i, int i2) {
        AndroidFuture<Boolean> androidFuture = new AndroidFuture<>();
        try {
            this.mConnection.requestCursorUpdates(createHeader(), i, i2, androidFuture);
            return androidFuture;
        } catch (RemoteException e) {
            androidFuture.completeExceptionally(e);
            return androidFuture;
        }
    }

    public AndroidFuture<Boolean> requestCursorUpdates(int i, int i2, int i3) {
        AndroidFuture<Boolean> androidFuture = new AndroidFuture<>();
        try {
            this.mConnection.requestCursorUpdatesWithFilter(createHeader(), i, i2, i3, androidFuture);
            return androidFuture;
        } catch (RemoteException e) {
            androidFuture.completeExceptionally(e);
            return androidFuture;
        }
    }

    public void requestTextBoundsInfo(RectF rectF, Executor executor, final Consumer<TextBoundsInfoResult> consumer) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        try {
            this.mConnection.requestTextBoundsInfo(createHeader(), rectF, new TextBoundsInfoResultReceiver(executor, consumer));
        } catch (RemoteException unused) {
            executor.execute(new Runnable() { // from class: android.inputmethodservice.IRemoteInputConnectionInvoker$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(new TextBoundsInfoResult(3));
                }
            });
        }
    }

    public AndroidFuture<Boolean> commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
        AndroidFuture<Boolean> androidFuture = new AndroidFuture<>();
        try {
            this.mConnection.commitContent(createHeader(), inputContentInfo, i, bundle, androidFuture);
            return androidFuture;
        } catch (RemoteException e) {
            androidFuture.completeExceptionally(e);
            return androidFuture;
        }
    }

    public boolean setImeConsumesInput(boolean z) {
        try {
            this.mConnection.setImeConsumesInput(createHeader(), z);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean replaceText(int i, int i2, CharSequence charSequence, int i3, TextAttribute textAttribute) {
        try {
            this.mConnection.replaceText(createHeader(), i, i2, charSequence, i3, textAttribute);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }
}
