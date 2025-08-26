package android.view.inputmethod;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.util.ExceptionUtils;
import android.view.inputmethod.ImeTracker;
import android.window.ImeOnBackInvokedDispatcher;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.inputmethod.IBooleanListener;
import com.android.internal.inputmethod.IConnectionlessHandwritingCallback;
import com.android.internal.inputmethod.IImeTracker;
import com.android.internal.inputmethod.IInputMethodClient;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;
import com.android.internal.inputmethod.IRemoteInputConnection;
import com.android.internal.inputmethod.InputBindResult;
import com.android.internal.inputmethod.InputMethodInfoSafeList;
import com.android.internal.view.IInputMethodManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
final class IInputMethodManagerGlobalInvoker {
    private static final long TIMEOUT_MS = 10000;
    private static int sCurStartInputSeq;
    private static volatile IInputMethodManager sServiceCache;
    private static volatile IImeTracker sTrackerServiceCache;

    IInputMethodManagerGlobalInvoker() {
    }

    static boolean isAvailable() {
        return getService() != null;
    }

    static IInputMethodManager getService() {
        IInputMethodManager iInputMethodManagerAsInterface = sServiceCache;
        if (iInputMethodManagerAsInterface == null) {
            if (InputMethodManager.isInEditModeInternal() || (iInputMethodManagerAsInterface = IInputMethodManager.Stub.asInterface(ServiceManager.getService(Context.INPUT_METHOD_SERVICE))) == null) {
                return null;
            }
            sServiceCache = iInputMethodManagerAsInterface;
        }
        return iInputMethodManagerAsInterface;
    }

    private static void handleRemoteExceptionOrRethrow(RemoteException remoteException, Consumer<RemoteException> consumer) {
        if (consumer != null) {
            consumer.accept(remoteException);
            return;
        }
        throw remoteException.rethrowFromSystemServer();
    }

    static void startProtoDump(byte[] bArr, int i, String str, Consumer<RemoteException> consumer) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.startProtoDump(bArr, i, str);
        } catch (RemoteException e) {
            handleRemoteExceptionOrRethrow(e, consumer);
        }
    }

    static void startImeTrace(Consumer<RemoteException> consumer) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.startImeTrace();
        } catch (RemoteException e) {
            handleRemoteExceptionOrRethrow(e, consumer);
        }
    }

    static void stopImeTrace(Consumer<RemoteException> consumer) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.stopImeTrace();
        } catch (RemoteException e) {
            handleRemoteExceptionOrRethrow(e, consumer);
        }
    }

    static boolean isImeTraceEnabled() {
        IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.isImeTraceEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void removeImeSurface(int i, Consumer<RemoteException> consumer) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.removeImeSurface(i);
        } catch (RemoteException e) {
            handleRemoteExceptionOrRethrow(e, consumer);
        }
    }

    static void addClient(IInputMethodClient iInputMethodClient, IRemoteInputConnection iRemoteInputConnection, int i) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.addClient(iInputMethodClient, iRemoteInputConnection, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static InputMethodInfo getCurrentInputMethodInfoAsUser(int i) {
        IInputMethodManager service = getService();
        if (service == null) {
            return null;
        }
        try {
            return service.getCurrentInputMethodInfoAsUser(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static List<InputMethodInfo> getInputMethodList(int i, int i2) {
        IInputMethodManager service = getService();
        if (service == null) {
            return new ArrayList();
        }
        try {
            if (Flags.useInputMethodInfoSafeList()) {
                return InputMethodInfoSafeList.extractFrom(service.getInputMethodList(i, i2));
            }
            return service.getInputMethodListLegacy(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static List<InputMethodInfo> getEnabledInputMethodList(int i) {
        IInputMethodManager service = getService();
        if (service == null) {
            return new ArrayList();
        }
        try {
            if (Flags.useInputMethodInfoSafeList()) {
                return InputMethodInfoSafeList.extractFrom(service.getEnabledInputMethodList(i));
            }
            return service.getEnabledInputMethodListLegacy(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static List<InputMethodSubtype> getEnabledInputMethodSubtypeList(String str, boolean z, int i) {
        IInputMethodManager service = getService();
        if (service == null) {
            return new ArrayList();
        }
        try {
            return service.getEnabledInputMethodSubtypeList(str, z, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static InputMethodSubtype getLastInputMethodSubtype(int i) {
        IInputMethodManager service = getService();
        if (service == null) {
            return null;
        }
        try {
            return service.getLastInputMethodSubtype(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean showSoftInput(IInputMethodClient iInputMethodClient, IBinder iBinder, ImeTracker.Token token, int i, int i2, ResultReceiver resultReceiver, int i3, boolean z) {
        IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.showSoftInput(iInputMethodClient, iBinder, token, i, i2, resultReceiver, i3, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean hideSoftInput(IInputMethodClient iInputMethodClient, IBinder iBinder, ImeTracker.Token token, int i, ResultReceiver resultReceiver, int i2, boolean z) {
        IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.hideSoftInput(iInputMethodClient, iBinder, token, i, resultReceiver, i2, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void hideSoftInputFromServerForTest() {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.hideSoftInputFromServerForTest();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static InputBindResult startInputOrWindowGainedFocus(int i, IInputMethodClient iInputMethodClient, IBinder iBinder, int i2, int i3, int i4, EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, boolean z) {
        IInputMethodManager service = getService();
        if (service == null) {
            return InputBindResult.NULL;
        }
        try {
            return service.startInputOrWindowGainedFocus(i, iInputMethodClient, iBinder, i2, i3, i4, editorInfo, iRemoteInputConnection, iRemoteAccessibilityInputConnection, i5, i6, imeOnBackInvokedDispatcher, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static int startInputOrWindowGainedFocusAsync(int i, IInputMethodClient iInputMethodClient, IBinder iBinder, int i2, int i3, int i4, EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, boolean z, boolean z2) {
        IInputMethodManager service = getService();
        if (service == null) {
            return -1;
        }
        try {
            service.startInputOrWindowGainedFocusAsync(i, iInputMethodClient, iBinder, i2, i3, i4, editorInfo, iRemoteInputConnection, iRemoteAccessibilityInputConnection, i5, i6, imeOnBackInvokedDispatcher, z, advanceAngGetStartInputSequenceNumber(), z2);
            return sCurStartInputSeq;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static int advanceAngGetStartInputSequenceNumber() {
        int i = sCurStartInputSeq + 1;
        sCurStartInputSeq = i;
        return i;
    }

    static void showInputMethodPickerFromClient(IInputMethodClient iInputMethodClient, int i) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.showInputMethodPickerFromClient(iInputMethodClient, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void showInputMethodPickerFromSystem(int i, int i2) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.showInputMethodPickerFromSystem(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean isInputMethodPickerShownForTest() {
        IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.isInputMethodPickerShownForTest();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void onImeSwitchButtonClickFromSystem(int i) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.onImeSwitchButtonClickFromSystem(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean shouldShowImeSwitcherButtonForTest() {
        IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.shouldShowImeSwitcherButtonForTest();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static InputMethodSubtype getCurrentInputMethodSubtype(int i) {
        IInputMethodManager service = getService();
        if (service == null) {
            return null;
        }
        try {
            return service.getCurrentInputMethodSubtype(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void setAdditionalInputMethodSubtypes(String str, InputMethodSubtype[] inputMethodSubtypeArr, int i) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.setAdditionalInputMethodSubtypes(str, inputMethodSubtypeArr, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void setExplicitlyEnabledInputMethodSubtypes(String str, int[] iArr, int i) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.setExplicitlyEnabledInputMethodSubtypes(str, iArr, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static int getInputMethodWindowVisibleHeight(IInputMethodClient iInputMethodClient) {
        IInputMethodManager service = getService();
        if (service == null) {
            return 0;
        }
        try {
            return service.getInputMethodWindowVisibleHeight(iInputMethodClient);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void reportPerceptibleAsync(IBinder iBinder, boolean z) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.reportPerceptibleAsync(iBinder, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void removeImeSurfaceFromWindowAsync(IBinder iBinder) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.removeImeSurfaceFromWindowAsync(iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void startStylusHandwriting(IInputMethodClient iInputMethodClient) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.startStylusHandwriting(iInputMethodClient);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean startConnectionlessStylusHandwriting(IInputMethodClient iInputMethodClient, int i, CursorAnchorInfo cursorAnchorInfo, String str, String str2, IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback) {
        IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.startConnectionlessStylusHandwriting(iInputMethodClient, i, cursorAnchorInfo, str, str2, iConnectionlessHandwritingCallback);
            return true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void prepareStylusHandwritingDelegation(IInputMethodClient iInputMethodClient, int i, String str, String str2) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.prepareStylusHandwritingDelegation(iInputMethodClient, i, str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean acceptStylusHandwritingDelegation(IInputMethodClient iInputMethodClient, int i, String str, String str2, int i2) {
        IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.acceptStylusHandwritingDelegation(iInputMethodClient, i, str, str2, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean acceptStylusHandwritingDelegationAsync(IInputMethodClient iInputMethodClient, int i, String str, String str2, int i2, IBooleanListener iBooleanListener) {
        IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            service.acceptStylusHandwritingDelegationAsync(iInputMethodClient, i, str, str2, i2, iBooleanListener);
            return true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean isStylusHandwritingAvailableAsUser(int i, boolean z) {
        IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.isStylusHandwritingAvailableAsUser(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void addVirtualStylusIdForTestSession(IInputMethodClient iInputMethodClient) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.addVirtualStylusIdForTestSession(iInputMethodClient);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void setStylusWindowIdleTimeoutForTest(IInputMethodClient iInputMethodClient, long j) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.setStylusWindowIdleTimeoutForTest(iInputMethodClient, j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static ImeTracker.Token onStart(String str, int i, int i2, int i3, int i4, boolean z) {
        IImeTracker imeTrackerService = getImeTrackerService();
        if (imeTrackerService == null) {
            return ImeTracker.Token.empty(str);
        }
        try {
            return imeTrackerService.onStart(str, i, i2, i3, i4, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void onProgress(IBinder iBinder, int i) {
        IImeTracker imeTrackerService = getImeTrackerService();
        if (imeTrackerService == null) {
            return;
        }
        try {
            imeTrackerService.onProgress(iBinder, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void onFailed(ImeTracker.Token token, int i) {
        IImeTracker imeTrackerService = getImeTrackerService();
        if (imeTrackerService == null) {
            return;
        }
        try {
            imeTrackerService.onFailed(token, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void onCancelled(ImeTracker.Token token, int i) {
        IImeTracker imeTrackerService = getImeTrackerService();
        if (imeTrackerService == null) {
            return;
        }
        try {
            imeTrackerService.onCancelled(token, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void onShown(ImeTracker.Token token) {
        IImeTracker imeTrackerService = getImeTrackerService();
        if (imeTrackerService == null) {
            return;
        }
        try {
            imeTrackerService.onShown(token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void onHidden(ImeTracker.Token token) {
        IImeTracker imeTrackerService = getImeTrackerService();
        if (imeTrackerService == null) {
            return;
        }
        try {
            imeTrackerService.onHidden(token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void onDispatched(ImeTracker.Token token) {
        IImeTracker imeTrackerService = getImeTrackerService();
        if (imeTrackerService == null) {
            return;
        }
        try {
            imeTrackerService.onDispatched(token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean hasPendingImeVisibilityRequests() {
        IImeTracker imeTrackerService = getImeTrackerService();
        if (imeTrackerService == null) {
            return true;
        }
        try {
            return imeTrackerService.hasPendingImeVisibilityRequests();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void finishTrackingPendingImeVisibilityRequests() {
        IImeTracker imeTrackerService = getImeTrackerService();
        if (imeTrackerService == null) {
            return;
        }
        try {
            AndroidFuture androidFuture = new AndroidFuture();
            imeTrackerService.finishTrackingPendingImeVisibilityRequests(androidFuture);
            androidFuture.get(10000L, TimeUnit.MILLISECONDS);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (Exception e2) {
            throw ExceptionUtils.propagate(e2);
        }
    }

    private static IImeTracker getImeTrackerService() {
        IImeTracker iImeTracker = sTrackerServiceCache;
        if (iImeTracker != null) {
            return iImeTracker;
        }
        IInputMethodManager service = getService();
        if (service == null) {
            return null;
        }
        try {
            IImeTracker imeTrackerService = service.getImeTrackerService();
            if (imeTrackerService == null) {
                return null;
            }
            sTrackerServiceCache = imeTrackerService;
            return imeTrackerService;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean minimizeSoftInput(IInputMethodClient iInputMethodClient, int i) {
        IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.minimizeSoftInput(iInputMethodClient, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void undoMinimizeSoftInput() {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.undoMinimizeSoftInput();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void forceHideSoftInput() {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.forceHideSoftInput();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static int isAccessoryKeyboard() {
        IInputMethodManager service = getService();
        if (service == null) {
            return 0;
        }
        try {
            return service.isAccessoryKeyboard();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean getWACOMPen() {
        IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.getWACOMPen();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean isInputMethodShown() {
        IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.isInputMethodShown();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean isCurrentInputMethodAsSamsungKeyboard() {
        IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.isCurrentInputMethodAsSamsungKeyboard();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static boolean getDexSettingsValue(String str, String str2) {
        IInputMethodManager service = getService();
        if (service == null) {
            return false;
        }
        try {
            return service.getDexSettingsValue(str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void setInputMethodSwitchDisable(IInputMethodClient iInputMethodClient, boolean z) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.setInputMethodSwitchDisable(iInputMethodClient, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void dismissAndShowAgainInputMethodPicker() {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.dismissAndShowAgainInputMethodPicker();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static int getCurrentFocusDisplayID() {
        IInputMethodManager service = getService();
        if (service == null) {
            return 0;
        }
        try {
            return service.getCurrentFocusDisplayID();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static int getCurTokenDisplayId() {
        IInputMethodManager service = getService();
        if (service == null) {
            return 0;
        }
        try {
            return service.getCurTokenDisplayId();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void handleVoiceHWKey() {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.handleVoiceHWKey();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static void showInputMethodPickerFromSystemWithUserId(int i, int i2, int i3) {
        IInputMethodManager service = getService();
        if (service == null) {
            return;
        }
        try {
            service.showInputMethodPickerFromSystemWithUserId(i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
