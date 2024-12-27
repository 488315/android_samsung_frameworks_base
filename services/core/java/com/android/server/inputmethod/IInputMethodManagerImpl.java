package com.android.server.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;
import android.window.ImeOnBackInvokedDispatcher;

import com.android.internal.inputmethod.IBooleanListener;
import com.android.internal.inputmethod.IConnectionlessHandwritingCallback;
import com.android.internal.inputmethod.IImeTracker;
import com.android.internal.inputmethod.IInputMethodClient;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;
import com.android.internal.inputmethod.IRemoteInputConnection;
import com.android.internal.inputmethod.InputBindResult;
import com.android.internal.inputmethod.InputMethodInfoSafeList;
import com.android.internal.view.IInputMethodManager;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public final class IInputMethodManagerImpl extends IInputMethodManager.Stub {
    public final Callback mCallback;

    public interface Callback {
        boolean acceptStylusHandwritingDelegation(
                IInputMethodClient iInputMethodClient, int i, String str, String str2, int i2);

        void acceptStylusHandwritingDelegationAsync(
                IInputMethodClient iInputMethodClient,
                int i,
                String str,
                String str2,
                int i2,
                IBooleanListener iBooleanListener);

        void addClient(
                IInputMethodClient iInputMethodClient,
                IRemoteInputConnection iRemoteInputConnection,
                int i);

        void addVirtualStylusIdForTestSession(IInputMethodClient iInputMethodClient);

        void dismissAndShowAgainInputMethodPicker();

        void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

        int getCurTokenDisplayId();

        int getCurrentFocusDisplayID();

        InputMethodInfo getCurrentInputMethodInfoAsUser(int i);

        InputMethodSubtype getCurrentInputMethodSubtype(int i);

        boolean getDexSettingsValue(String str, String str2);

        InputMethodInfoSafeList getEnabledInputMethodList(int i);

        List getEnabledInputMethodListLegacy(int i);

        List getEnabledInputMethodSubtypeList(String str, boolean z, int i);

        ImeTrackerService getImeTrackerService();

        InputMethodInfoSafeList getInputMethodList(int i, int i2);

        List getInputMethodListLegacy(int i, int i2);

        int getInputMethodWindowVisibleHeight(IInputMethodClient iInputMethodClient);

        InputMethodSubtype getLastInputMethodSubtype(int i);

        boolean getWACOMPen();

        void handleVoiceHWKey();

        boolean hideSoftInput(
                IInputMethodClient iInputMethodClient,
                IBinder iBinder,
                ImeTracker.Token token,
                int i,
                ResultReceiver resultReceiver,
                int i2);

        void hideSoftInputFromServerForTest();

        int isAccessoryKeyboard();

        boolean isCurrentInputMethodAsSamsungKeyboard();

        boolean isImeTraceEnabled();

        boolean isInputMethodPickerShownForTest();

        boolean isInputMethodShown();

        boolean isStylusHandwritingAvailableAsUser(int i, boolean z);

        boolean minimizeSoftInput(IInputMethodClient iInputMethodClient, int i);

        void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver,
                Binder binder);

        void prepareStylusHandwritingDelegation(
                IInputMethodClient iInputMethodClient, int i, String str, String str2);

        void removeImeSurface();

        void removeImeSurfaceFromWindowAsync(IBinder iBinder);

        void reportPerceptibleAsync(IBinder iBinder, boolean z);

        void setAdditionalInputMethodSubtypes(
                String str, InputMethodSubtype[] inputMethodSubtypeArr, int i);

        void setExplicitlyEnabledInputMethodSubtypes(String str, int[] iArr, int i);

        void setInputMethodSwitchDisable(IInputMethodClient iInputMethodClient, boolean z);

        void setStylusWindowIdleTimeoutForTest(IInputMethodClient iInputMethodClient, long j);

        void showInputMethodPickerFromClient(IInputMethodClient iInputMethodClient, int i);

        void showInputMethodPickerFromSystem(int i, int i2);

        boolean showSoftInput(
                IInputMethodClient iInputMethodClient,
                IBinder iBinder,
                ImeTracker.Token token,
                int i,
                int i2,
                ResultReceiver resultReceiver,
                int i3);

        void startConnectionlessStylusHandwriting(
                IInputMethodClient iInputMethodClient,
                int i,
                CursorAnchorInfo cursorAnchorInfo,
                String str,
                String str2,
                IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback);

        void startImeTrace();

        InputBindResult startInputOrWindowGainedFocus(
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
                ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher);

        void startInputOrWindowGainedFocusAsync(
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
                ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher,
                int i7);

        void startProtoDump(byte[] bArr, int i, String str);

        void startStylusHandwriting(IInputMethodClient iInputMethodClient);

        void stopImeTrace();

        void undoMinimizeSoftInput();
    }

    public IInputMethodManagerImpl(Callback callback) {
        this.mCallback = callback;
    }

    public final boolean acceptStylusHandwritingDelegation(
            IInputMethodClient iInputMethodClient, int i, String str, String str2, int i2) {
        return this.mCallback.acceptStylusHandwritingDelegation(
                iInputMethodClient, i, str, str2, i2);
    }

    public final void acceptStylusHandwritingDelegationAsync(
            IInputMethodClient iInputMethodClient,
            int i,
            String str,
            String str2,
            int i2,
            IBooleanListener iBooleanListener) {
        this.mCallback.acceptStylusHandwritingDelegationAsync(
                iInputMethodClient, i, str, str2, i2, iBooleanListener);
    }

    public final void addClient(
            IInputMethodClient iInputMethodClient,
            IRemoteInputConnection iRemoteInputConnection,
            int i) {
        this.mCallback.addClient(iInputMethodClient, iRemoteInputConnection, i);
    }

    public final void addVirtualStylusIdForTestSession(IInputMethodClient iInputMethodClient) {
        addVirtualStylusIdForTestSession_enforcePermission();
        this.mCallback.addVirtualStylusIdForTestSession(iInputMethodClient);
    }

    public final void dismissAndShowAgainInputMethodPicker() {
        this.mCallback.dismissAndShowAgainInputMethodPicker();
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.mCallback.dump(fileDescriptor, printWriter, strArr);
    }

    public final int getCurTokenDisplayId() {
        return this.mCallback.getCurTokenDisplayId();
    }

    public final int getCurrentFocusDisplayID() {
        return this.mCallback.getCurrentFocusDisplayID();
    }

    public final InputMethodInfo getCurrentInputMethodInfoAsUser(int i) {
        return this.mCallback.getCurrentInputMethodInfoAsUser(i);
    }

    public final InputMethodSubtype getCurrentInputMethodSubtype(int i) {
        return this.mCallback.getCurrentInputMethodSubtype(i);
    }

    public final boolean getDexSettingsValue(String str, String str2) {
        return this.mCallback.getDexSettingsValue(str, str2);
    }

    public final InputMethodInfoSafeList getEnabledInputMethodList(int i) {
        return this.mCallback.getEnabledInputMethodList(i);
    }

    public final List getEnabledInputMethodListLegacy(int i) {
        return this.mCallback.getEnabledInputMethodListLegacy(i);
    }

    public final List getEnabledInputMethodSubtypeList(String str, boolean z, int i) {
        return this.mCallback.getEnabledInputMethodSubtypeList(str, z, i);
    }

    public final IImeTracker getImeTrackerService() {
        return this.mCallback.getImeTrackerService();
    }

    public final InputMethodInfoSafeList getInputMethodList(int i, int i2) {
        return this.mCallback.getInputMethodList(i, i2);
    }

    public final List getInputMethodListLegacy(int i, int i2) {
        return this.mCallback.getInputMethodListLegacy(i, i2);
    }

    public final int getInputMethodWindowVisibleHeight(IInputMethodClient iInputMethodClient) {
        return this.mCallback.getInputMethodWindowVisibleHeight(iInputMethodClient);
    }

    public final InputMethodSubtype getLastInputMethodSubtype(int i) {
        return this.mCallback.getLastInputMethodSubtype(i);
    }

    public final boolean getWACOMPen() {
        return this.mCallback.getWACOMPen();
    }

    public final void handleVoiceHWKey() {
        this.mCallback.handleVoiceHWKey();
    }

    public final boolean hideSoftInput(
            IInputMethodClient iInputMethodClient,
            IBinder iBinder,
            ImeTracker.Token token,
            int i,
            ResultReceiver resultReceiver,
            int i2) {
        return this.mCallback.hideSoftInput(
                iInputMethodClient, iBinder, token, i, resultReceiver, i2);
    }

    public final void hideSoftInputFromServerForTest() {
        hideSoftInputFromServerForTest_enforcePermission();
        this.mCallback.hideSoftInputFromServerForTest();
    }

    public final int isAccessoryKeyboard() {
        return this.mCallback.isAccessoryKeyboard();
    }

    public final boolean isCurrentInputMethodAsSamsungKeyboard() {
        return this.mCallback.isCurrentInputMethodAsSamsungKeyboard();
    }

    public final boolean isImeTraceEnabled() {
        return this.mCallback.isImeTraceEnabled();
    }

    public final boolean isInputMethodPickerShownForTest() {
        isInputMethodPickerShownForTest_enforcePermission();
        return this.mCallback.isInputMethodPickerShownForTest();
    }

    public final boolean isInputMethodShown() {
        return this.mCallback.isInputMethodShown();
    }

    public final boolean isStylusHandwritingAvailableAsUser(int i, boolean z) {
        return this.mCallback.isStylusHandwritingAvailableAsUser(i, z);
    }

    public final boolean minimizeSoftInput(IInputMethodClient iInputMethodClient, int i) {
        return this.mCallback.minimizeSoftInput(iInputMethodClient, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(
            FileDescriptor fileDescriptor,
            FileDescriptor fileDescriptor2,
            FileDescriptor fileDescriptor3,
            String[] strArr,
            ShellCallback shellCallback,
            ResultReceiver resultReceiver) {
        this.mCallback.onShellCommand(
                fileDescriptor,
                fileDescriptor2,
                fileDescriptor3,
                strArr,
                shellCallback,
                resultReceiver,
                this);
    }

    public final void prepareStylusHandwritingDelegation(
            IInputMethodClient iInputMethodClient, int i, String str, String str2) {
        this.mCallback.prepareStylusHandwritingDelegation(iInputMethodClient, i, str, str2);
    }

    public final void removeImeSurface() {
        removeImeSurface_enforcePermission();
        this.mCallback.removeImeSurface();
    }

    public final void removeImeSurfaceFromWindowAsync(IBinder iBinder) {
        this.mCallback.removeImeSurfaceFromWindowAsync(iBinder);
    }

    public final void reportPerceptibleAsync(IBinder iBinder, boolean z) {
        this.mCallback.reportPerceptibleAsync(iBinder, z);
    }

    public final void setAdditionalInputMethodSubtypes(
            String str, InputMethodSubtype[] inputMethodSubtypeArr, int i) {
        this.mCallback.setAdditionalInputMethodSubtypes(str, inputMethodSubtypeArr, i);
    }

    public final void setExplicitlyEnabledInputMethodSubtypes(String str, int[] iArr, int i) {
        this.mCallback.setExplicitlyEnabledInputMethodSubtypes(str, iArr, i);
    }

    public final void setInputMethodSwitchDisable(
            IInputMethodClient iInputMethodClient, boolean z) {
        this.mCallback.setInputMethodSwitchDisable(iInputMethodClient, z);
    }

    public final void setStylusWindowIdleTimeoutForTest(
            IInputMethodClient iInputMethodClient, long j) {
        setStylusWindowIdleTimeoutForTest_enforcePermission();
        this.mCallback.setStylusWindowIdleTimeoutForTest(iInputMethodClient, j);
    }

    public final void showInputMethodPickerFromClient(
            IInputMethodClient iInputMethodClient, int i) {
        this.mCallback.showInputMethodPickerFromClient(iInputMethodClient, i);
    }

    public final void showInputMethodPickerFromSystem(int i, int i2) {
        showInputMethodPickerFromSystem_enforcePermission();
        this.mCallback.showInputMethodPickerFromSystem(i, i2);
    }

    public final boolean showSoftInput(
            IInputMethodClient iInputMethodClient,
            IBinder iBinder,
            ImeTracker.Token token,
            int i,
            int i2,
            ResultReceiver resultReceiver,
            int i3) {
        return this.mCallback.showSoftInput(
                iInputMethodClient, iBinder, token, i, i2, resultReceiver, i3);
    }

    public final void startConnectionlessStylusHandwriting(
            IInputMethodClient iInputMethodClient,
            int i,
            CursorAnchorInfo cursorAnchorInfo,
            String str,
            String str2,
            IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback) {
        this.mCallback.startConnectionlessStylusHandwriting(
                iInputMethodClient,
                i,
                cursorAnchorInfo,
                str,
                str2,
                iConnectionlessHandwritingCallback);
    }

    public final void startImeTrace() {
        startImeTrace_enforcePermission();
        this.mCallback.startImeTrace();
    }

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
        return this.mCallback.startInputOrWindowGainedFocus(
                i,
                iInputMethodClient,
                iBinder,
                i2,
                i3,
                i4,
                editorInfo,
                iRemoteInputConnection,
                iRemoteAccessibilityInputConnection,
                i5,
                i6,
                imeOnBackInvokedDispatcher);
    }

    public final void startInputOrWindowGainedFocusAsync(
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
            ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher,
            int i7) {
        this.mCallback.startInputOrWindowGainedFocusAsync(
                i,
                iInputMethodClient,
                iBinder,
                i2,
                i3,
                i4,
                editorInfo,
                iRemoteInputConnection,
                iRemoteAccessibilityInputConnection,
                i5,
                i6,
                imeOnBackInvokedDispatcher,
                i7);
    }

    public final void startProtoDump(byte[] bArr, int i, String str) {
        this.mCallback.startProtoDump(bArr, i, str);
    }

    public final void startStylusHandwriting(IInputMethodClient iInputMethodClient) {
        this.mCallback.startStylusHandwriting(iInputMethodClient);
    }

    public final void stopImeTrace() {
        stopImeTrace_enforcePermission();
        this.mCallback.stopImeTrace();
    }

    public final void undoMinimizeSoftInput() {
        this.mCallback.undoMinimizeSoftInput();
    }
}
