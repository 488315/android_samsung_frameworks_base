package com.android.internal.view;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.os.ResultReceiver;
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

import java.util.List;

public interface IInputMethodManager extends IInterface {
    boolean acceptStylusHandwritingDelegation(
            IInputMethodClient iInputMethodClient, int i, String str, String str2, int i2)
            throws RemoteException;

    void acceptStylusHandwritingDelegationAsync(
            IInputMethodClient iInputMethodClient,
            int i,
            String str,
            String str2,
            int i2,
            IBooleanListener iBooleanListener)
            throws RemoteException;

    void addClient(
            IInputMethodClient iInputMethodClient,
            IRemoteInputConnection iRemoteInputConnection,
            int i)
            throws RemoteException;

    void addVirtualStylusIdForTestSession(IInputMethodClient iInputMethodClient)
            throws RemoteException;

    void dismissAndShowAgainInputMethodPicker() throws RemoteException;

    int getCurTokenDisplayId() throws RemoteException;

    int getCurrentFocusDisplayID() throws RemoteException;

    InputMethodInfo getCurrentInputMethodInfoAsUser(int i) throws RemoteException;

    InputMethodSubtype getCurrentInputMethodSubtype(int i) throws RemoteException;

    boolean getDexSettingsValue(String str, String str2) throws RemoteException;

    InputMethodInfoSafeList getEnabledInputMethodList(int i) throws RemoteException;

    List<InputMethodInfo> getEnabledInputMethodListLegacy(int i) throws RemoteException;

    List<InputMethodSubtype> getEnabledInputMethodSubtypeList(String str, boolean z, int i)
            throws RemoteException;

    IImeTracker getImeTrackerService() throws RemoteException;

    InputMethodInfoSafeList getInputMethodList(int i, int i2) throws RemoteException;

    List<InputMethodInfo> getInputMethodListLegacy(int i, int i2) throws RemoteException;

    int getInputMethodWindowVisibleHeight(IInputMethodClient iInputMethodClient)
            throws RemoteException;

    InputMethodSubtype getLastInputMethodSubtype(int i) throws RemoteException;

    boolean getWACOMPen() throws RemoteException;

    void handleVoiceHWKey() throws RemoteException;

    boolean hideSoftInput(
            IInputMethodClient iInputMethodClient,
            IBinder iBinder,
            ImeTracker.Token token,
            int i,
            ResultReceiver resultReceiver,
            int i2)
            throws RemoteException;

    void hideSoftInputFromServerForTest() throws RemoteException;

    int isAccessoryKeyboard() throws RemoteException;

    boolean isCurrentInputMethodAsSamsungKeyboard() throws RemoteException;

    boolean isImeTraceEnabled() throws RemoteException;

    boolean isInputMethodPickerShownForTest() throws RemoteException;

    boolean isInputMethodShown() throws RemoteException;

    boolean isStylusHandwritingAvailableAsUser(int i, boolean z) throws RemoteException;

    boolean minimizeSoftInput(IInputMethodClient iInputMethodClient, int i) throws RemoteException;

    void prepareStylusHandwritingDelegation(
            IInputMethodClient iInputMethodClient, int i, String str, String str2)
            throws RemoteException;

    void removeImeSurface() throws RemoteException;

    void removeImeSurfaceFromWindowAsync(IBinder iBinder) throws RemoteException;

    void reportPerceptibleAsync(IBinder iBinder, boolean z) throws RemoteException;

    void setAdditionalInputMethodSubtypes(
            String str, InputMethodSubtype[] inputMethodSubtypeArr, int i) throws RemoteException;

    void setExplicitlyEnabledInputMethodSubtypes(String str, int[] iArr, int i)
            throws RemoteException;

    void setInputMethodSwitchDisable(IInputMethodClient iInputMethodClient, boolean z)
            throws RemoteException;

    void setStylusWindowIdleTimeoutForTest(IInputMethodClient iInputMethodClient, long j)
            throws RemoteException;

    void showInputMethodPickerFromClient(IInputMethodClient iInputMethodClient, int i)
            throws RemoteException;

    void showInputMethodPickerFromSystem(int i, int i2) throws RemoteException;

    boolean showSoftInput(
            IInputMethodClient iInputMethodClient,
            IBinder iBinder,
            ImeTracker.Token token,
            int i,
            int i2,
            ResultReceiver resultReceiver,
            int i3)
            throws RemoteException;

    void startConnectionlessStylusHandwriting(
            IInputMethodClient iInputMethodClient,
            int i,
            CursorAnchorInfo cursorAnchorInfo,
            String str,
            String str2,
            IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback)
            throws RemoteException;

    void startImeTrace() throws RemoteException;

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
            ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher)
            throws RemoteException;

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
            int i7)
            throws RemoteException;

    void startProtoDump(byte[] bArr, int i, String str) throws RemoteException;

    void startStylusHandwriting(IInputMethodClient iInputMethodClient) throws RemoteException;

    void stopImeTrace() throws RemoteException;

    void undoMinimizeSoftInput() throws RemoteException;

    public static class Default implements IInputMethodManager {
        @Override // com.android.internal.view.IInputMethodManager
        public void addClient(
                IInputMethodClient client,
                IRemoteInputConnection inputmethod,
                int untrustedDisplayId)
                throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public InputMethodInfo getCurrentInputMethodInfoAsUser(int userId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public InputMethodInfoSafeList getInputMethodList(int userId, int directBootAwareness)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public InputMethodInfoSafeList getEnabledInputMethodList(int userId)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public List<InputMethodInfo> getInputMethodListLegacy(int userId, int directBootAwareness)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public List<InputMethodInfo> getEnabledInputMethodListLegacy(int userId)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public List<InputMethodSubtype> getEnabledInputMethodSubtypeList(
                String imiId, boolean allowsImplicitlyEnabledSubtypes, int userId)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public InputMethodSubtype getLastInputMethodSubtype(int userId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean showSoftInput(
                IInputMethodClient client,
                IBinder windowToken,
                ImeTracker.Token statsToken,
                int flags,
                int lastClickToolType,
                ResultReceiver resultReceiver,
                int reason)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean hideSoftInput(
                IInputMethodClient client,
                IBinder windowToken,
                ImeTracker.Token statsToken,
                int flags,
                ResultReceiver resultReceiver,
                int reason)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void hideSoftInputFromServerForTest() throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public InputBindResult startInputOrWindowGainedFocus(
                int startInputReason,
                IInputMethodClient client,
                IBinder windowToken,
                int startInputFlags,
                int softInputMode,
                int windowFlags,
                EditorInfo editorInfo,
                IRemoteInputConnection inputConnection,
                IRemoteAccessibilityInputConnection remoteAccessibilityInputConnection,
                int unverifiedTargetSdkVersion,
                int userId,
                ImeOnBackInvokedDispatcher imeDispatcher)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void startInputOrWindowGainedFocusAsync(
                int startInputReason,
                IInputMethodClient client,
                IBinder windowToken,
                int startInputFlags,
                int softInputMode,
                int windowFlags,
                EditorInfo editorInfo,
                IRemoteInputConnection inputConnection,
                IRemoteAccessibilityInputConnection remoteAccessibilityInputConnection,
                int unverifiedTargetSdkVersion,
                int userId,
                ImeOnBackInvokedDispatcher imeDispatcher,
                int startInputSeq)
                throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public void showInputMethodPickerFromClient(
                IInputMethodClient client, int auxiliarySubtypeMode) throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public void showInputMethodPickerFromSystem(int auxiliarySubtypeMode, int displayId)
                throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public boolean isInputMethodPickerShownForTest() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public InputMethodSubtype getCurrentInputMethodSubtype(int userId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void setAdditionalInputMethodSubtypes(
                String id, InputMethodSubtype[] subtypes, int userId) throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public void setExplicitlyEnabledInputMethodSubtypes(
                String imeId, int[] subtypeHashCodes, int userId) throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public int getInputMethodWindowVisibleHeight(IInputMethodClient client)
                throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void reportPerceptibleAsync(IBinder windowToken, boolean perceptible)
                throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public void removeImeSurface() throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public void removeImeSurfaceFromWindowAsync(IBinder windowToken) throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public void startProtoDump(byte[] protoDump, int source, String where)
                throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public boolean isImeTraceEnabled() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void startImeTrace() throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public void stopImeTrace() throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public void startStylusHandwriting(IInputMethodClient client) throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public void startConnectionlessStylusHandwriting(
                IInputMethodClient client,
                int userId,
                CursorAnchorInfo cursorAnchorInfo,
                String delegatePackageName,
                String delegatorPackageName,
                IConnectionlessHandwritingCallback callback)
                throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public void prepareStylusHandwritingDelegation(
                IInputMethodClient client,
                int userId,
                String delegatePackageName,
                String delegatorPackageName)
                throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public boolean acceptStylusHandwritingDelegation(
                IInputMethodClient client,
                int userId,
                String delegatePackageName,
                String delegatorPackageName,
                int flags)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void acceptStylusHandwritingDelegationAsync(
                IInputMethodClient client,
                int userId,
                String delegatePackageName,
                String delegatorPackageName,
                int flags,
                IBooleanListener callback)
                throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public boolean isStylusHandwritingAvailableAsUser(int userId, boolean connectionless)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void addVirtualStylusIdForTestSession(IInputMethodClient client)
                throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public void setStylusWindowIdleTimeoutForTest(IInputMethodClient client, long timeout)
                throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public IImeTracker getImeTrackerService() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean minimizeSoftInput(IInputMethodClient client, int minimize)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void undoMinimizeSoftInput() throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public int isAccessoryKeyboard() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean getWACOMPen() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean isInputMethodShown() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean isCurrentInputMethodAsSamsungKeyboard() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean getDexSettingsValue(String key, String defaultKey) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void setInputMethodSwitchDisable(IInputMethodClient client, boolean disable)
                throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public void dismissAndShowAgainInputMethodPicker() throws RemoteException {}

        @Override // com.android.internal.view.IInputMethodManager
        public int getCurrentFocusDisplayID() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public int getCurTokenDisplayId() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void handleVoiceHWKey() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IInputMethodManager {
        public static final String DESCRIPTOR = "com.android.internal.view.IInputMethodManager";
        static final int TRANSACTION_acceptStylusHandwritingDelegation = 31;
        static final int TRANSACTION_acceptStylusHandwritingDelegationAsync = 32;
        static final int TRANSACTION_addClient = 1;
        static final int TRANSACTION_addVirtualStylusIdForTestSession = 34;
        static final int TRANSACTION_dismissAndShowAgainInputMethodPicker = 45;
        static final int TRANSACTION_getCurTokenDisplayId = 47;
        static final int TRANSACTION_getCurrentFocusDisplayID = 46;
        static final int TRANSACTION_getCurrentInputMethodInfoAsUser = 2;
        static final int TRANSACTION_getCurrentInputMethodSubtype = 17;
        static final int TRANSACTION_getDexSettingsValue = 43;
        static final int TRANSACTION_getEnabledInputMethodList = 4;
        static final int TRANSACTION_getEnabledInputMethodListLegacy = 6;
        static final int TRANSACTION_getEnabledInputMethodSubtypeList = 7;
        static final int TRANSACTION_getImeTrackerService = 36;
        static final int TRANSACTION_getInputMethodList = 3;
        static final int TRANSACTION_getInputMethodListLegacy = 5;
        static final int TRANSACTION_getInputMethodWindowVisibleHeight = 20;
        static final int TRANSACTION_getLastInputMethodSubtype = 8;
        static final int TRANSACTION_getWACOMPen = 40;
        static final int TRANSACTION_handleVoiceHWKey = 48;
        static final int TRANSACTION_hideSoftInput = 10;
        static final int TRANSACTION_hideSoftInputFromServerForTest = 11;
        static final int TRANSACTION_isAccessoryKeyboard = 39;
        static final int TRANSACTION_isCurrentInputMethodAsSamsungKeyboard = 42;
        static final int TRANSACTION_isImeTraceEnabled = 25;
        static final int TRANSACTION_isInputMethodPickerShownForTest = 16;
        static final int TRANSACTION_isInputMethodShown = 41;
        static final int TRANSACTION_isStylusHandwritingAvailableAsUser = 33;
        static final int TRANSACTION_minimizeSoftInput = 37;
        static final int TRANSACTION_prepareStylusHandwritingDelegation = 30;
        static final int TRANSACTION_removeImeSurface = 22;
        static final int TRANSACTION_removeImeSurfaceFromWindowAsync = 23;
        static final int TRANSACTION_reportPerceptibleAsync = 21;
        static final int TRANSACTION_setAdditionalInputMethodSubtypes = 18;
        static final int TRANSACTION_setExplicitlyEnabledInputMethodSubtypes = 19;
        static final int TRANSACTION_setInputMethodSwitchDisable = 44;
        static final int TRANSACTION_setStylusWindowIdleTimeoutForTest = 35;
        static final int TRANSACTION_showInputMethodPickerFromClient = 14;
        static final int TRANSACTION_showInputMethodPickerFromSystem = 15;
        static final int TRANSACTION_showSoftInput = 9;
        static final int TRANSACTION_startConnectionlessStylusHandwriting = 29;
        static final int TRANSACTION_startImeTrace = 26;
        static final int TRANSACTION_startInputOrWindowGainedFocus = 12;
        static final int TRANSACTION_startInputOrWindowGainedFocusAsync = 13;
        static final int TRANSACTION_startProtoDump = 24;
        static final int TRANSACTION_startStylusHandwriting = 28;
        static final int TRANSACTION_stopImeTrace = 27;
        static final int TRANSACTION_undoMinimizeSoftInput = 38;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(
                    PermissionEnforcer.fromContext(
                            ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IInputMethodManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IInputMethodManager)) {
                return (IInputMethodManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "addClient";
                case 2:
                    return "getCurrentInputMethodInfoAsUser";
                case 3:
                    return "getInputMethodList";
                case 4:
                    return "getEnabledInputMethodList";
                case 5:
                    return "getInputMethodListLegacy";
                case 6:
                    return "getEnabledInputMethodListLegacy";
                case 7:
                    return "getEnabledInputMethodSubtypeList";
                case 8:
                    return "getLastInputMethodSubtype";
                case 9:
                    return "showSoftInput";
                case 10:
                    return "hideSoftInput";
                case 11:
                    return "hideSoftInputFromServerForTest";
                case 12:
                    return "startInputOrWindowGainedFocus";
                case 13:
                    return "startInputOrWindowGainedFocusAsync";
                case 14:
                    return "showInputMethodPickerFromClient";
                case 15:
                    return "showInputMethodPickerFromSystem";
                case 16:
                    return "isInputMethodPickerShownForTest";
                case 17:
                    return "getCurrentInputMethodSubtype";
                case 18:
                    return "setAdditionalInputMethodSubtypes";
                case 19:
                    return "setExplicitlyEnabledInputMethodSubtypes";
                case 20:
                    return "getInputMethodWindowVisibleHeight";
                case 21:
                    return "reportPerceptibleAsync";
                case 22:
                    return "removeImeSurface";
                case 23:
                    return "removeImeSurfaceFromWindowAsync";
                case 24:
                    return "startProtoDump";
                case 25:
                    return "isImeTraceEnabled";
                case 26:
                    return "startImeTrace";
                case 27:
                    return "stopImeTrace";
                case 28:
                    return "startStylusHandwriting";
                case 29:
                    return "startConnectionlessStylusHandwriting";
                case 30:
                    return "prepareStylusHandwritingDelegation";
                case 31:
                    return "acceptStylusHandwritingDelegation";
                case 32:
                    return "acceptStylusHandwritingDelegationAsync";
                case 33:
                    return "isStylusHandwritingAvailableAsUser";
                case 34:
                    return "addVirtualStylusIdForTestSession";
                case 35:
                    return "setStylusWindowIdleTimeoutForTest";
                case 36:
                    return "getImeTrackerService";
                case 37:
                    return "minimizeSoftInput";
                case 38:
                    return "undoMinimizeSoftInput";
                case 39:
                    return "isAccessoryKeyboard";
                case 40:
                    return "getWACOMPen";
                case 41:
                    return "isInputMethodShown";
                case 42:
                    return "isCurrentInputMethodAsSamsungKeyboard";
                case 43:
                    return "getDexSettingsValue";
                case 44:
                    return "setInputMethodSwitchDisable";
                case 45:
                    return "dismissAndShowAgainInputMethodPicker";
                case 46:
                    return "getCurrentFocusDisplayID";
                case 47:
                    return "getCurTokenDisplayId";
                case 48:
                    return "handleVoiceHWKey";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IInputMethodClient _arg0 =
                            IInputMethodClient.Stub.asInterface(data.readStrongBinder());
                    IRemoteInputConnection _arg1 =
                            IRemoteInputConnection.Stub.asInterface(data.readStrongBinder());
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    addClient(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    InputMethodInfo _result = getCurrentInputMethodInfoAsUser(_arg02);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    InputMethodInfoSafeList _result2 = getInputMethodList(_arg03, _arg12);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    InputMethodInfoSafeList _result3 = getEnabledInputMethodList(_arg04);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    List<InputMethodInfo> _result4 = getInputMethodListLegacy(_arg05, _arg13);
                    reply.writeNoException();
                    reply.writeTypedList(_result4, 1);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    List<InputMethodInfo> _result5 = getEnabledInputMethodListLegacy(_arg06);
                    reply.writeNoException();
                    reply.writeTypedList(_result5, 1);
                    return true;
                case 7:
                    String _arg07 = data.readString();
                    boolean _arg14 = data.readBoolean();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    List<InputMethodSubtype> _result6 =
                            getEnabledInputMethodSubtypeList(_arg07, _arg14, _arg22);
                    reply.writeNoException();
                    reply.writeTypedList(_result6, 1);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    data.enforceNoDataAvail();
                    InputMethodSubtype _result7 = getLastInputMethodSubtype(_arg08);
                    reply.writeNoException();
                    reply.writeTypedObject(_result7, 1);
                    return true;
                case 9:
                    IInputMethodClient _arg09 =
                            IInputMethodClient.Stub.asInterface(data.readStrongBinder());
                    IBinder _arg15 = data.readStrongBinder();
                    ImeTracker.Token _arg23 =
                            (ImeTracker.Token) data.readTypedObject(ImeTracker.Token.CREATOR);
                    int _arg3 = data.readInt();
                    int _arg4 = data.readInt();
                    ResultReceiver _arg5 =
                            (ResultReceiver) data.readTypedObject(ResultReceiver.CREATOR);
                    int _arg6 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result8 =
                            showSoftInput(_arg09, _arg15, _arg23, _arg3, _arg4, _arg5, _arg6);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 10:
                    IInputMethodClient _arg010 =
                            IInputMethodClient.Stub.asInterface(data.readStrongBinder());
                    IBinder _arg16 = data.readStrongBinder();
                    ImeTracker.Token _arg24 =
                            (ImeTracker.Token) data.readTypedObject(ImeTracker.Token.CREATOR);
                    int _arg32 = data.readInt();
                    ResultReceiver _arg42 =
                            (ResultReceiver) data.readTypedObject(ResultReceiver.CREATOR);
                    int _arg52 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result9 =
                            hideSoftInput(_arg010, _arg16, _arg24, _arg32, _arg42, _arg52);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 11:
                    hideSoftInputFromServerForTest();
                    reply.writeNoException();
                    return true;
                case 12:
                    int _arg011 = data.readInt();
                    IInputMethodClient _arg17 =
                            IInputMethodClient.Stub.asInterface(data.readStrongBinder());
                    IBinder _arg25 = data.readStrongBinder();
                    int _arg33 = data.readInt();
                    int _arg43 = data.readInt();
                    int _arg53 = data.readInt();
                    EditorInfo _arg62 = (EditorInfo) data.readTypedObject(EditorInfo.CREATOR);
                    IRemoteInputConnection _arg7 =
                            IRemoteInputConnection.Stub.asInterface(data.readStrongBinder());
                    IRemoteAccessibilityInputConnection _arg8 =
                            IRemoteAccessibilityInputConnection.Stub.asInterface(
                                    data.readStrongBinder());
                    int _arg9 = data.readInt();
                    int _arg10 = data.readInt();
                    ImeOnBackInvokedDispatcher _arg11 =
                            (ImeOnBackInvokedDispatcher)
                                    data.readTypedObject(ImeOnBackInvokedDispatcher.CREATOR);
                    data.enforceNoDataAvail();
                    InputBindResult _result10 =
                            startInputOrWindowGainedFocus(
                                    _arg011, _arg17, _arg25, _arg33, _arg43, _arg53, _arg62, _arg7,
                                    _arg8, _arg9, _arg10, _arg11);
                    reply.writeNoException();
                    reply.writeTypedObject(_result10, 1);
                    return true;
                case 13:
                    int _arg012 = data.readInt();
                    IInputMethodClient _arg18 =
                            IInputMethodClient.Stub.asInterface(data.readStrongBinder());
                    IBinder _arg26 = data.readStrongBinder();
                    int _arg34 = data.readInt();
                    int _arg44 = data.readInt();
                    int _arg54 = data.readInt();
                    EditorInfo _arg63 = (EditorInfo) data.readTypedObject(EditorInfo.CREATOR);
                    IRemoteInputConnection _arg72 =
                            IRemoteInputConnection.Stub.asInterface(data.readStrongBinder());
                    IRemoteAccessibilityInputConnection _arg82 =
                            IRemoteAccessibilityInputConnection.Stub.asInterface(
                                    data.readStrongBinder());
                    int _arg92 = data.readInt();
                    int _arg102 = data.readInt();
                    ImeOnBackInvokedDispatcher _arg112 =
                            (ImeOnBackInvokedDispatcher)
                                    data.readTypedObject(ImeOnBackInvokedDispatcher.CREATOR);
                    int _arg122 = data.readInt();
                    data.enforceNoDataAvail();
                    startInputOrWindowGainedFocusAsync(
                            _arg012, _arg18, _arg26, _arg34, _arg44, _arg54, _arg63, _arg72, _arg82,
                            _arg92, _arg102, _arg112, _arg122);
                    reply.writeNoException();
                    return true;
                case 14:
                    IInputMethodClient _arg013 =
                            IInputMethodClient.Stub.asInterface(data.readStrongBinder());
                    int _arg19 = data.readInt();
                    data.enforceNoDataAvail();
                    showInputMethodPickerFromClient(_arg013, _arg19);
                    reply.writeNoException();
                    return true;
                case 15:
                    int _arg014 = data.readInt();
                    int _arg110 = data.readInt();
                    data.enforceNoDataAvail();
                    showInputMethodPickerFromSystem(_arg014, _arg110);
                    reply.writeNoException();
                    return true;
                case 16:
                    boolean _result11 = isInputMethodPickerShownForTest();
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 17:
                    int _arg015 = data.readInt();
                    data.enforceNoDataAvail();
                    InputMethodSubtype _result12 = getCurrentInputMethodSubtype(_arg015);
                    reply.writeNoException();
                    reply.writeTypedObject(_result12, 1);
                    return true;
                case 18:
                    String _arg016 = data.readString();
                    InputMethodSubtype[] _arg111 =
                            (InputMethodSubtype[])
                                    data.createTypedArray(InputMethodSubtype.CREATOR);
                    int _arg27 = data.readInt();
                    data.enforceNoDataAvail();
                    setAdditionalInputMethodSubtypes(_arg016, _arg111, _arg27);
                    reply.writeNoException();
                    return true;
                case 19:
                    String _arg017 = data.readString();
                    int[] _arg113 = data.createIntArray();
                    int _arg28 = data.readInt();
                    data.enforceNoDataAvail();
                    setExplicitlyEnabledInputMethodSubtypes(_arg017, _arg113, _arg28);
                    reply.writeNoException();
                    return true;
                case 20:
                    IBinder _arg018 = data.readStrongBinder();
                    IInputMethodClient _arg019 = IInputMethodClient.Stub.asInterface(_arg018);
                    data.enforceNoDataAvail();
                    int _result13 = getInputMethodWindowVisibleHeight(_arg019);
                    reply.writeNoException();
                    reply.writeInt(_result13);
                    return true;
                case 21:
                    IBinder _arg020 = data.readStrongBinder();
                    boolean _arg114 = data.readBoolean();
                    data.enforceNoDataAvail();
                    reportPerceptibleAsync(_arg020, _arg114);
                    return true;
                case 22:
                    removeImeSurface();
                    reply.writeNoException();
                    return true;
                case 23:
                    IBinder _arg021 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    removeImeSurfaceFromWindowAsync(_arg021);
                    return true;
                case 24:
                    byte[] _arg022 = data.createByteArray();
                    int _arg115 = data.readInt();
                    String _arg29 = data.readString();
                    data.enforceNoDataAvail();
                    startProtoDump(_arg022, _arg115, _arg29);
                    reply.writeNoException();
                    return true;
                case 25:
                    boolean _result14 = isImeTraceEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 26:
                    startImeTrace();
                    reply.writeNoException();
                    return true;
                case 27:
                    stopImeTrace();
                    reply.writeNoException();
                    return true;
                case 28:
                    IInputMethodClient _arg023 =
                            IInputMethodClient.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    startStylusHandwriting(_arg023);
                    reply.writeNoException();
                    return true;
                case 29:
                    IInputMethodClient _arg024 =
                            IInputMethodClient.Stub.asInterface(data.readStrongBinder());
                    int _arg116 = data.readInt();
                    CursorAnchorInfo _arg210 =
                            (CursorAnchorInfo) data.readTypedObject(CursorAnchorInfo.CREATOR);
                    String _arg35 = data.readString();
                    String _arg45 = data.readString();
                    IConnectionlessHandwritingCallback _arg55 =
                            IConnectionlessHandwritingCallback.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    startConnectionlessStylusHandwriting(
                            _arg024, _arg116, _arg210, _arg35, _arg45, _arg55);
                    return true;
                case 30:
                    IInputMethodClient _arg025 =
                            IInputMethodClient.Stub.asInterface(data.readStrongBinder());
                    int _arg117 = data.readInt();
                    String _arg211 = data.readString();
                    String _arg36 = data.readString();
                    data.enforceNoDataAvail();
                    prepareStylusHandwritingDelegation(_arg025, _arg117, _arg211, _arg36);
                    reply.writeNoException();
                    return true;
                case 31:
                    IInputMethodClient _arg026 =
                            IInputMethodClient.Stub.asInterface(data.readStrongBinder());
                    int _arg118 = data.readInt();
                    String _arg212 = data.readString();
                    String _arg37 = data.readString();
                    int _arg46 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result15 =
                            acceptStylusHandwritingDelegation(
                                    _arg026, _arg118, _arg212, _arg37, _arg46);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 32:
                    IInputMethodClient _arg027 =
                            IInputMethodClient.Stub.asInterface(data.readStrongBinder());
                    int _arg119 = data.readInt();
                    String _arg213 = data.readString();
                    String _arg38 = data.readString();
                    int _arg47 = data.readInt();
                    IBooleanListener _arg56 =
                            IBooleanListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    acceptStylusHandwritingDelegationAsync(
                            _arg027, _arg119, _arg213, _arg38, _arg47, _arg56);
                    return true;
                case 33:
                    int _arg028 = data.readInt();
                    boolean _arg120 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result16 = isStylusHandwritingAvailableAsUser(_arg028, _arg120);
                    reply.writeNoException();
                    reply.writeBoolean(_result16);
                    return true;
                case 34:
                    IInputMethodClient _arg029 =
                            IInputMethodClient.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addVirtualStylusIdForTestSession(_arg029);
                    reply.writeNoException();
                    return true;
                case 35:
                    IInputMethodClient _arg030 =
                            IInputMethodClient.Stub.asInterface(data.readStrongBinder());
                    long _arg121 = data.readLong();
                    data.enforceNoDataAvail();
                    setStylusWindowIdleTimeoutForTest(_arg030, _arg121);
                    reply.writeNoException();
                    return true;
                case 36:
                    IImeTracker _result17 = getImeTrackerService();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result17);
                    return true;
                case 37:
                    IInputMethodClient _arg031 =
                            IInputMethodClient.Stub.asInterface(data.readStrongBinder());
                    int _arg123 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result18 = minimizeSoftInput(_arg031, _arg123);
                    reply.writeNoException();
                    reply.writeBoolean(_result18);
                    return true;
                case 38:
                    undoMinimizeSoftInput();
                    reply.writeNoException();
                    return true;
                case 39:
                    int _result19 = isAccessoryKeyboard();
                    reply.writeNoException();
                    reply.writeInt(_result19);
                    return true;
                case 40:
                    boolean _result20 = getWACOMPen();
                    reply.writeNoException();
                    reply.writeBoolean(_result20);
                    return true;
                case 41:
                    boolean _result21 = isInputMethodShown();
                    reply.writeNoException();
                    reply.writeBoolean(_result21);
                    return true;
                case 42:
                    boolean _result22 = isCurrentInputMethodAsSamsungKeyboard();
                    reply.writeNoException();
                    reply.writeBoolean(_result22);
                    return true;
                case 43:
                    String _arg032 = data.readString();
                    String _arg124 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result23 = getDexSettingsValue(_arg032, _arg124);
                    reply.writeNoException();
                    reply.writeBoolean(_result23);
                    return true;
                case 44:
                    IInputMethodClient _arg033 =
                            IInputMethodClient.Stub.asInterface(data.readStrongBinder());
                    boolean _arg125 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setInputMethodSwitchDisable(_arg033, _arg125);
                    reply.writeNoException();
                    return true;
                case 45:
                    dismissAndShowAgainInputMethodPicker();
                    reply.writeNoException();
                    return true;
                case 46:
                    int _result24 = getCurrentFocusDisplayID();
                    reply.writeNoException();
                    reply.writeInt(_result24);
                    return true;
                case 47:
                    int _result25 = getCurTokenDisplayId();
                    reply.writeNoException();
                    reply.writeInt(_result25);
                    return true;
                case 48:
                    handleVoiceHWKey();
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IInputMethodManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void addClient(
                    IInputMethodClient client,
                    IRemoteInputConnection inputmethod,
                    int untrustedDisplayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeStrongInterface(inputmethod);
                    _data.writeInt(untrustedDisplayId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputMethodInfo getCurrentInputMethodInfoAsUser(int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    InputMethodInfo _result =
                            (InputMethodInfo) _reply.readTypedObject(InputMethodInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputMethodInfoSafeList getInputMethodList(int userId, int directBootAwareness)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(directBootAwareness);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    InputMethodInfoSafeList _result =
                            (InputMethodInfoSafeList)
                                    _reply.readTypedObject(InputMethodInfoSafeList.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputMethodInfoSafeList getEnabledInputMethodList(int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    InputMethodInfoSafeList _result =
                            (InputMethodInfoSafeList)
                                    _reply.readTypedObject(InputMethodInfoSafeList.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public List<InputMethodInfo> getInputMethodListLegacy(
                    int userId, int directBootAwareness) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(directBootAwareness);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    List<InputMethodInfo> _result =
                            _reply.createTypedArrayList(InputMethodInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public List<InputMethodInfo> getEnabledInputMethodListLegacy(int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    List<InputMethodInfo> _result =
                            _reply.createTypedArrayList(InputMethodInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public List<InputMethodSubtype> getEnabledInputMethodSubtypeList(
                    String imiId, boolean allowsImplicitlyEnabledSubtypes, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(imiId);
                    _data.writeBoolean(allowsImplicitlyEnabledSubtypes);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    List<InputMethodSubtype> _result =
                            _reply.createTypedArrayList(InputMethodSubtype.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputMethodSubtype getLastInputMethodSubtype(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    InputMethodSubtype _result =
                            (InputMethodSubtype) _reply.readTypedObject(InputMethodSubtype.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean showSoftInput(
                    IInputMethodClient client,
                    IBinder windowToken,
                    ImeTracker.Token statsToken,
                    int flags,
                    int lastClickToolType,
                    ResultReceiver resultReceiver,
                    int reason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeStrongBinder(windowToken);
                    _data.writeTypedObject(statsToken, 0);
                    _data.writeInt(flags);
                    _data.writeInt(lastClickToolType);
                    _data.writeTypedObject(resultReceiver, 0);
                    _data.writeInt(reason);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean hideSoftInput(
                    IInputMethodClient client,
                    IBinder windowToken,
                    ImeTracker.Token statsToken,
                    int flags,
                    ResultReceiver resultReceiver,
                    int reason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeStrongBinder(windowToken);
                    _data.writeTypedObject(statsToken, 0);
                    _data.writeInt(flags);
                    _data.writeTypedObject(resultReceiver, 0);
                    _data.writeInt(reason);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void hideSoftInputFromServerForTest() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputBindResult startInputOrWindowGainedFocus(
                    int startInputReason,
                    IInputMethodClient client,
                    IBinder windowToken,
                    int startInputFlags,
                    int softInputMode,
                    int windowFlags,
                    EditorInfo editorInfo,
                    IRemoteInputConnection inputConnection,
                    IRemoteAccessibilityInputConnection remoteAccessibilityInputConnection,
                    int unverifiedTargetSdkVersion,
                    int userId,
                    ImeOnBackInvokedDispatcher imeDispatcher)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(startInputReason);
                    _data.writeStrongInterface(client);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeStrongBinder(windowToken);
                    try {
                        _data.writeInt(startInputFlags);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(softInputMode);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(windowFlags);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeTypedObject(editorInfo, 0);
                    try {
                        _data.writeStrongInterface(inputConnection);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStrongInterface(remoteAccessibilityInputConnection);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(unverifiedTargetSdkVersion);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th9) {
                    th = th9;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(userId);
                    try {
                        _data.writeTypedObject(imeDispatcher, 0);
                    } catch (Throwable th10) {
                        th = th10;
                    }
                    try {
                        this.mRemote.transact(12, _data, _reply, 0);
                        _reply.readException();
                        InputBindResult _result =
                                (InputBindResult) _reply.readTypedObject(InputBindResult.CREATOR);
                        _reply.recycle();
                        _data.recycle();
                        return _result;
                    } catch (Throwable th11) {
                        th = th11;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startInputOrWindowGainedFocusAsync(
                    int startInputReason,
                    IInputMethodClient client,
                    IBinder windowToken,
                    int startInputFlags,
                    int softInputMode,
                    int windowFlags,
                    EditorInfo editorInfo,
                    IRemoteInputConnection inputConnection,
                    IRemoteAccessibilityInputConnection remoteAccessibilityInputConnection,
                    int unverifiedTargetSdkVersion,
                    int userId,
                    ImeOnBackInvokedDispatcher imeDispatcher,
                    int startInputSeq)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(startInputReason);
                    _data.writeStrongInterface(client);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeStrongBinder(windowToken);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(startInputFlags);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(softInputMode);
                } catch (Throwable th4) {
                    th = th4;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(windowFlags);
                } catch (Throwable th5) {
                    th = th5;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeTypedObject(editorInfo, 0);
                } catch (Throwable th6) {
                    th = th6;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeStrongInterface(inputConnection);
                } catch (Throwable th7) {
                    th = th7;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeStrongInterface(remoteAccessibilityInputConnection);
                    try {
                        _data.writeInt(unverifiedTargetSdkVersion);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(userId);
                    } catch (Throwable th9) {
                        th = th9;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedObject(imeDispatcher, 0);
                    } catch (Throwable th10) {
                        th = th10;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(startInputSeq);
                        this.mRemote.transact(13, _data, _reply, 0);
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th11) {
                        th = th11;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void showInputMethodPickerFromClient(
                    IInputMethodClient client, int auxiliarySubtypeMode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeInt(auxiliarySubtypeMode);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void showInputMethodPickerFromSystem(int auxiliarySubtypeMode, int displayId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(auxiliarySubtypeMode);
                    _data.writeInt(displayId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isInputMethodPickerShownForTest() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputMethodSubtype getCurrentInputMethodSubtype(int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    InputMethodSubtype _result =
                            (InputMethodSubtype) _reply.readTypedObject(InputMethodSubtype.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void setAdditionalInputMethodSubtypes(
                    String id, InputMethodSubtype[] subtypes, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(id);
                    _data.writeTypedArray(subtypes, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void setExplicitlyEnabledInputMethodSubtypes(
                    String imeId, int[] subtypeHashCodes, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(imeId);
                    _data.writeIntArray(subtypeHashCodes);
                    _data.writeInt(userId);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public int getInputMethodWindowVisibleHeight(IInputMethodClient client)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void reportPerceptibleAsync(IBinder windowToken, boolean perceptible)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(windowToken);
                    _data.writeBoolean(perceptible);
                    this.mRemote.transact(21, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void removeImeSurface() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void removeImeSurfaceFromWindowAsync(IBinder windowToken)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(windowToken);
                    this.mRemote.transact(23, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startProtoDump(byte[] protoDump, int source, String where)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(protoDump);
                    _data.writeInt(source);
                    _data.writeString(where);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isImeTraceEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startImeTrace() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void stopImeTrace() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startStylusHandwriting(IInputMethodClient client) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startConnectionlessStylusHandwriting(
                    IInputMethodClient client,
                    int userId,
                    CursorAnchorInfo cursorAnchorInfo,
                    String delegatePackageName,
                    String delegatorPackageName,
                    IConnectionlessHandwritingCallback callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeInt(userId);
                    _data.writeTypedObject(cursorAnchorInfo, 0);
                    _data.writeString(delegatePackageName);
                    _data.writeString(delegatorPackageName);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(29, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void prepareStylusHandwritingDelegation(
                    IInputMethodClient client,
                    int userId,
                    String delegatePackageName,
                    String delegatorPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeInt(userId);
                    _data.writeString(delegatePackageName);
                    _data.writeString(delegatorPackageName);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean acceptStylusHandwritingDelegation(
                    IInputMethodClient client,
                    int userId,
                    String delegatePackageName,
                    String delegatorPackageName,
                    int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeInt(userId);
                    _data.writeString(delegatePackageName);
                    _data.writeString(delegatorPackageName);
                    _data.writeInt(flags);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void acceptStylusHandwritingDelegationAsync(
                    IInputMethodClient client,
                    int userId,
                    String delegatePackageName,
                    String delegatorPackageName,
                    int flags,
                    IBooleanListener callback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeInt(userId);
                    _data.writeString(delegatePackageName);
                    _data.writeString(delegatorPackageName);
                    _data.writeInt(flags);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(32, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isStylusHandwritingAvailableAsUser(int userId, boolean connectionless)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeBoolean(connectionless);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void addVirtualStylusIdForTestSession(IInputMethodClient client)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void setStylusWindowIdleTimeoutForTest(IInputMethodClient client, long timeout)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeLong(timeout);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public IImeTracker getImeTrackerService() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    IImeTracker _result = IImeTracker.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean minimizeSoftInput(IInputMethodClient client, int minimize)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeInt(minimize);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void undoMinimizeSoftInput() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public int isAccessoryKeyboard() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean getWACOMPen() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isInputMethodShown() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isCurrentInputMethodAsSamsungKeyboard() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean getDexSettingsValue(String key, String defaultKey)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeString(defaultKey);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void setInputMethodSwitchDisable(IInputMethodClient client, boolean disable)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeBoolean(disable);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void dismissAndShowAgainInputMethodPicker() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public int getCurrentFocusDisplayID() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public int getCurTokenDisplayId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void handleVoiceHWKey() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void hideSoftInputFromServerForTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }

        protected void showInputMethodPickerFromSystem_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void isInputMethodPickerShownForTest_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }

        protected void removeImeSurface_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.INTERNAL_SYSTEM_WINDOW, getCallingPid(), getCallingUid());
        }

        protected void startImeTrace_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.CONTROL_UI_TRACING, getCallingPid(), getCallingUid());
        }

        protected void stopImeTrace_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.CONTROL_UI_TRACING, getCallingPid(), getCallingUid());
        }

        protected void addVirtualStylusIdForTestSession_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }

        protected void setStylusWindowIdleTimeoutForTest_enforcePermission()
                throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 47;
        }
    }
}
