package com.android.server.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.util.Slog;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import android.window.ImeOnBackInvokedDispatcher;

import com.android.internal.inputmethod.IBooleanListener;
import com.android.internal.inputmethod.IConnectionlessHandwritingCallback;
import com.android.internal.inputmethod.IInputMethodClient;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;
import com.android.internal.inputmethod.IRemoteInputConnection;
import com.android.internal.inputmethod.InputBindResult;
import com.android.internal.inputmethod.InputMethodInfoSafeList;
import com.android.internal.util.FunctionalUtils;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticLambda1;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ZeroJankProxy implements IInputMethodManagerImpl.Callback {
    public final Executor mExecutor;
    public final Callback mInner;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Callback extends IInputMethodManagerImpl.Callback {}

    public ZeroJankProxy(
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticLambda1
                    extendedEthernetServiceImpl$1$$ExternalSyntheticLambda1,
            Callback callback) {
        this.mInner = callback;
        this.mExecutor = extendedEthernetServiceImpl$1$$ExternalSyntheticLambda1;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean acceptStylusHandwritingDelegation(
            final IInputMethodClient iInputMethodClient,
            final int i,
            final String str,
            final String str2,
            final int i2) {
        try {
            return ((Boolean)
                            CompletableFuture.supplyAsync(
                                            new Supplier() { // from class:
                                                             // com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda8
                                                @Override // java.util.function.Supplier
                                                public final Object get() {
                                                    ZeroJankProxy zeroJankProxy =
                                                            ZeroJankProxy.this;
                                                    return Boolean.valueOf(
                                                            ((InputMethodManagerService)
                                                                            zeroJankProxy.mInner)
                                                                    .acceptStylusHandwritingDelegation(
                                                                            iInputMethodClient,
                                                                            i,
                                                                            str,
                                                                            str2,
                                                                            i2));
                                                }
                                            },
                                            new Executor() { // from class:
                                                             // com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda9
                                                @Override // java.util.concurrent.Executor
                                                public final void execute(Runnable runnable) {
                                                    ZeroJankProxy.this.offloadInner(runnable);
                                                }
                                            })
                                    .get())
                    .booleanValue();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void acceptStylusHandwritingDelegationAsync(
            IInputMethodClient iInputMethodClient,
            int i,
            String str,
            String str2,
            int i2,
            IBooleanListener iBooleanListener) {
        offloadInner(
                new ZeroJankProxy$$ExternalSyntheticLambda2(
                        this, iInputMethodClient, i, str, str2, i2, iBooleanListener));
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void addClient(
            final IInputMethodClient iInputMethodClient,
            final IRemoteInputConnection iRemoteInputConnection,
            final int i) {
        offloadInner(
                new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda10
                    public final void runOrThrow() {
                        ZeroJankProxy zeroJankProxy = ZeroJankProxy.this;
                        ((InputMethodManagerService) zeroJankProxy.mInner)
                                .addClient(iInputMethodClient, iRemoteInputConnection, i);
                    }
                });
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void addVirtualStylusIdForTestSession(IInputMethodClient iInputMethodClient) {
        ((InputMethodManagerService) this.mInner)
                .addVirtualStylusIdForTestSession(iInputMethodClient);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void dismissAndShowAgainInputMethodPicker() {
        ((InputMethodManagerService) this.mInner).dismissAndShowAgainInputMethodPicker();
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        ((InputMethodManagerService) this.mInner).dump(fileDescriptor, printWriter, strArr);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final int getCurTokenDisplayId() {
        return ((InputMethodManagerService) this.mInner).getCurTokenDisplayId();
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final int getCurrentFocusDisplayID() {
        return ((InputMethodManagerService) this.mInner).getCurrentFocusDisplayID();
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final InputMethodInfo getCurrentInputMethodInfoAsUser(int i) {
        return ((InputMethodManagerService) this.mInner).getCurrentInputMethodInfoAsUser(i);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final InputMethodSubtype getCurrentInputMethodSubtype(int i) {
        return ((InputMethodManagerService) this.mInner).getCurrentInputMethodSubtype(i);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean getDexSettingsValue(String str, String str2) {
        return ((InputMethodManagerService) this.mInner).getDexSettingsValue(str, str2);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final InputMethodInfoSafeList getEnabledInputMethodList(int i) {
        return ((InputMethodManagerService) this.mInner).getEnabledInputMethodList(i);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final List getEnabledInputMethodListLegacy(int i) {
        return ((InputMethodManagerService) this.mInner).getEnabledInputMethodListLegacy(i);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final List getEnabledInputMethodSubtypeList(String str, boolean z, int i) {
        return ((InputMethodManagerService) this.mInner)
                .getEnabledInputMethodSubtypeList(str, z, i);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final ImeTrackerService getImeTrackerService() {
        return ((InputMethodManagerService) this.mInner).mImeTrackerService;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final InputMethodInfoSafeList getInputMethodList(int i, int i2) {
        return ((InputMethodManagerService) this.mInner).getInputMethodList(i, i2);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final List getInputMethodListLegacy(int i, int i2) {
        return ((InputMethodManagerService) this.mInner).getInputMethodListLegacy(i, i2);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final int getInputMethodWindowVisibleHeight(IInputMethodClient iInputMethodClient) {
        return ((InputMethodManagerService) this.mInner)
                .getInputMethodWindowVisibleHeight(iInputMethodClient);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final InputMethodSubtype getLastInputMethodSubtype(int i) {
        return ((InputMethodManagerService) this.mInner).getLastInputMethodSubtype(i);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean getWACOMPen() {
        return ((InputMethodManagerService) this.mInner).mSemImmsUtil.mSpenLastUsed;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void handleVoiceHWKey() {
        ((InputMethodManagerService) this.mInner).handleVoiceHWKey();
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean hideSoftInput(
            IInputMethodClient iInputMethodClient,
            IBinder iBinder,
            ImeTracker.Token token,
            int i,
            ResultReceiver resultReceiver,
            int i2) {
        offloadInner(
                new ZeroJankProxy$$ExternalSyntheticLambda2(
                        this, iInputMethodClient, iBinder, token, i, resultReceiver, i2));
        return true;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void hideSoftInputFromServerForTest() {
        ((InputMethodManagerService) this.mInner).hideSoftInputFromServerForTest();
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final int isAccessoryKeyboard() {
        return ((InputMethodManagerService) this.mInner).isAccessoryKeyboard();
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean isCurrentInputMethodAsSamsungKeyboard() {
        return ((InputMethodManagerService) this.mInner).isCurrentInputMethodAsSamsungKeyboard();
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean isImeTraceEnabled() {
        return ((InputMethodManagerService) this.mInner).isImeTraceEnabled();
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean isInputMethodPickerShownForTest() {
        return ((InputMethodManagerService) this.mInner).isInputMethodPickerShownForTest();
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean isInputMethodShown() {
        return ((InputMethodManagerService) this.mInner).isInputMethodShown();
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean isStylusHandwritingAvailableAsUser(int i, boolean z) {
        return ((InputMethodManagerService) this.mInner).isStylusHandwritingAvailableAsUser(i, z);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean minimizeSoftInput(IInputMethodClient iInputMethodClient, int i) {
        return ((InputMethodManagerService) this.mInner).minimizeSoftInput(iInputMethodClient, i);
    }

    public final void offloadInner(final Runnable runnable) {
        final long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda11
                        @Override // java.lang.Runnable
                        public final void run() {
                            long j = clearCallingIdentity;
                            Runnable runnable2 = runnable;
                            long clearCallingIdentity2 = Binder.clearCallingIdentity();
                            Binder.restoreCallingIdentity(j);
                            try {
                                try {
                                    runnable2.run();
                                } catch (Exception e) {
                                    Slog.e(
                                            "InputMethodManagerService",
                                            "Error in async IMMS call",
                                            e);
                                }
                            } finally {
                                Binder.restoreCallingIdentity(clearCallingIdentity2);
                            }
                        }
                    });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void onShellCommand(
            FileDescriptor fileDescriptor,
            FileDescriptor fileDescriptor2,
            FileDescriptor fileDescriptor3,
            String[] strArr,
            ShellCallback shellCallback,
            ResultReceiver resultReceiver,
            Binder binder) {
        ((InputMethodManagerService) this.mInner)
                .onShellCommand(
                        fileDescriptor,
                        fileDescriptor2,
                        fileDescriptor3,
                        strArr,
                        shellCallback,
                        resultReceiver,
                        binder);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void prepareStylusHandwritingDelegation(
            final IInputMethodClient iInputMethodClient,
            final int i,
            final String str,
            final String str2) {
        offloadInner(
                new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda3
                    public final void runOrThrow() {
                        ZeroJankProxy zeroJankProxy = ZeroJankProxy.this;
                        ((InputMethodManagerService) zeroJankProxy.mInner)
                                .prepareStylusHandwritingDelegation(
                                        iInputMethodClient, i, str, str2);
                    }
                });
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void removeImeSurface() {
        ((InputMethodManagerService) this.mInner).removeImeSurface();
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void removeImeSurfaceFromWindowAsync(IBinder iBinder) {
        ((InputMethodManagerService) this.mInner).removeImeSurfaceFromWindowAsync(iBinder);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void reportPerceptibleAsync(IBinder iBinder, boolean z) {
        ((InputMethodManagerService) this.mInner).reportPerceptibleAsync(iBinder, z);
    }

    public final void sendResultReceiverFailure(ResultReceiver resultReceiver) {
        boolean z;
        if (resultReceiver == null) {
            return;
        }
        synchronized (ImfLock.class) {
            z = ((InputMethodManagerService) this.mInner).mVisibilityStateComputer.mInputShown;
        }
        resultReceiver.send(!z ? 1 : 0, null);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void setAdditionalInputMethodSubtypes(
            String str, InputMethodSubtype[] inputMethodSubtypeArr, int i) {
        ((InputMethodManagerService) this.mInner)
                .setAdditionalInputMethodSubtypes(str, inputMethodSubtypeArr, i);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void setExplicitlyEnabledInputMethodSubtypes(String str, int[] iArr, int i) {
        ((InputMethodManagerService) this.mInner)
                .setExplicitlyEnabledInputMethodSubtypes(str, iArr, i);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void setInputMethodSwitchDisable(
            IInputMethodClient iInputMethodClient, boolean z) {
        ((InputMethodManagerService) this.mInner)
                .setInputMethodSwitchDisable(iInputMethodClient, z);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void setStylusWindowIdleTimeoutForTest(
            IInputMethodClient iInputMethodClient, long j) {
        ((InputMethodManagerService) this.mInner)
                .setStylusWindowIdleTimeoutForTest(iInputMethodClient, j);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void showInputMethodPickerFromClient(
            final IInputMethodClient iInputMethodClient, final int i) {
        offloadInner(
                new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda5
                    public final void runOrThrow() {
                        ZeroJankProxy zeroJankProxy = ZeroJankProxy.this;
                        ((InputMethodManagerService) zeroJankProxy.mInner)
                                .showInputMethodPickerFromClient(iInputMethodClient, i);
                    }
                });
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void showInputMethodPickerFromSystem(int i, int i2) {
        ((InputMethodManagerService) this.mInner).showInputMethodPickerFromSystem(i, i2);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final boolean showSoftInput(
            final IInputMethodClient iInputMethodClient,
            final IBinder iBinder,
            final ImeTracker.Token token,
            final int i,
            final int i2,
            final ResultReceiver resultReceiver,
            final int i3) {
        offloadInner(
                new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda1
                    public final void runOrThrow() {
                        ZeroJankProxy zeroJankProxy = ZeroJankProxy.this;
                        IInputMethodClient iInputMethodClient2 = iInputMethodClient;
                        IBinder iBinder2 = iBinder;
                        ImeTracker.Token token2 = token;
                        int i4 = i;
                        int i5 = i2;
                        ResultReceiver resultReceiver2 = resultReceiver;
                        if (((InputMethodManagerService) zeroJankProxy.mInner)
                                .showSoftInput(
                                        iInputMethodClient2,
                                        iBinder2,
                                        token2,
                                        i4,
                                        i5,
                                        resultReceiver2,
                                        i3)) {
                            return;
                        }
                        zeroJankProxy.sendResultReceiverFailure(resultReceiver2);
                    }
                });
        return true;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void startConnectionlessStylusHandwriting(
            final IInputMethodClient iInputMethodClient,
            final int i,
            final CursorAnchorInfo cursorAnchorInfo,
            final String str,
            final String str2,
            final IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback) {
        offloadInner(
                new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda0
                    public final void runOrThrow() {
                        ZeroJankProxy zeroJankProxy = ZeroJankProxy.this;
                        ((InputMethodManagerService) zeroJankProxy.mInner)
                                .startConnectionlessStylusHandwriting(
                                        iInputMethodClient,
                                        i,
                                        cursorAnchorInfo,
                                        str,
                                        str2,
                                        iConnectionlessHandwritingCallback);
                    }
                });
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void startImeTrace() {
        ((InputMethodManagerService) this.mInner).startImeTrace();
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final InputBindResult startInputOrWindowGainedFocus(
            int i,
            IInputMethodClient iInputMethodClient,
            IBinder iBinder,
            int i2,
            int i3,
            int i4,
            EditorInfo editorInfo,
            IRemoteInputConnection iRemoteInputConnection,
            IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection,
            int i5,
            int i6,
            ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher) {
        return null;
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void startInputOrWindowGainedFocusAsync(
            final int i,
            final IInputMethodClient iInputMethodClient,
            final IBinder iBinder,
            final int i2,
            final int i3,
            final int i4,
            final EditorInfo editorInfo,
            final IRemoteInputConnection iRemoteInputConnection,
            final IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection,
            final int i5,
            final int i6,
            final ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher,
            final int i7) {
        offloadInner(
                new FunctionalUtils.ThrowingRunnable() { // from class:
                    // com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda7
                    public final void runOrThrow() {
                        IInputMethodClientInvoker iInputMethodClientInvoker;
                        ZeroJankProxy zeroJankProxy = ZeroJankProxy.this;
                        int i8 = i;
                        IInputMethodClient iInputMethodClient2 = iInputMethodClient;
                        IBinder iBinder2 = iBinder;
                        int i9 = i2;
                        int i10 = i3;
                        int i11 = i4;
                        EditorInfo editorInfo2 = editorInfo;
                        IRemoteInputConnection iRemoteInputConnection2 = iRemoteInputConnection;
                        IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection2 =
                                iRemoteAccessibilityInputConnection;
                        int i12 = i5;
                        int i13 = i6;
                        ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher2 =
                                imeOnBackInvokedDispatcher;
                        int i14 = i7;
                        InputBindResult startInputOrWindowGainedFocus =
                                ((InputMethodManagerService) zeroJankProxy.mInner)
                                        .startInputOrWindowGainedFocus(
                                                i8,
                                                iInputMethodClient2,
                                                iBinder2,
                                                i9,
                                                i10,
                                                i11,
                                                editorInfo2,
                                                iRemoteInputConnection2,
                                                iRemoteAccessibilityInputConnection2,
                                                i12,
                                                i13,
                                                imeOnBackInvokedDispatcher2);
                        synchronized (ImfLock.class) {
                            try {
                                ClientState client =
                                        ((InputMethodManagerService) zeroJankProxy.mInner)
                                                .mClientController.getClient(
                                                        iInputMethodClient2.asBinder());
                                if (client == null
                                        || (iInputMethodClientInvoker = client.mClient) == null) {
                                    Slog.i(
                                            "InputMethodManagerService",
                                            "Client that requested startInputOrWindowGainedFocus is"
                                                + " no longer bound. InputBindResult: "
                                                    + startInputOrWindowGainedFocus
                                                    + " for startInputSeq: "
                                                    + i14);
                                } else if (iInputMethodClientInvoker.mIsProxy) {
                                    iInputMethodClientInvoker.onStartInputResultInternal(
                                            startInputOrWindowGainedFocus, i14);
                                } else {
                                    iInputMethodClientInvoker.mHandler.post(
                                            new IInputMethodClientInvoker$$ExternalSyntheticLambda5(
                                                    iInputMethodClientInvoker,
                                                    startInputOrWindowGainedFocus,
                                                    i14,
                                                    0));
                                }
                            } finally {
                            }
                        }
                        if (startInputOrWindowGainedFocus.result == 1) {
                            InputMethodManagerService inputMethodManagerService =
                                    (InputMethodManagerService) zeroJankProxy.mInner;
                            synchronized (ImfLock.class) {
                                try {
                                    ClientState client2 =
                                            inputMethodManagerService.mClientController.getClient(
                                                    iInputMethodClient2.asBinder());
                                    if (client2 != null) {
                                        inputMethodManagerService.requestClientSessionLocked(
                                                client2);
                                        InputMethodManagerService
                                                .requestClientSessionForAccessibilityLocked(
                                                        client2);
                                    }
                                } finally {
                                }
                            }
                        }
                    }
                });
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void startProtoDump(byte[] bArr, int i, String str) {
        ((InputMethodManagerService) this.mInner).startProtoDump(bArr, i, str);
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void startStylusHandwriting(final IInputMethodClient iInputMethodClient) {
        offloadInner(
                new FunctionalUtils
                        .ThrowingRunnable() { // from class:
                                              // com.android.server.inputmethod.ZeroJankProxy$$ExternalSyntheticLambda6
                    public final void runOrThrow() {
                        ZeroJankProxy zeroJankProxy = ZeroJankProxy.this;
                        ((InputMethodManagerService) zeroJankProxy.mInner)
                                .startStylusHandwriting(
                                        iInputMethodClient, false, null, null, false);
                    }
                });
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void stopImeTrace() {
        ((InputMethodManagerService) this.mInner).stopImeTrace();
    }

    @Override // com.android.server.inputmethod.IInputMethodManagerImpl.Callback
    public final void undoMinimizeSoftInput() {
        ((InputMethodManagerService) this.mInner).undoMinimizeSoftInput();
    }
}
