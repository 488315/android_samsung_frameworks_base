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

/* loaded from: classes4.dex */
public interface IInputMethodManager extends IInterface {

    public static class Default implements IInputMethodManager {
        @Override // com.android.internal.view.IInputMethodManager
        public boolean acceptStylusHandwritingDelegation(IInputMethodClient iInputMethodClient, int i, String str, String str2, int i2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void acceptStylusHandwritingDelegationAsync(IInputMethodClient iInputMethodClient, int i, String str, String str2, int i2, IBooleanListener iBooleanListener) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void addClient(IInputMethodClient iInputMethodClient, IRemoteInputConnection iRemoteInputConnection, int i) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void addVirtualStylusIdForTestSession(IInputMethodClient iInputMethodClient) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void dismissAndShowAgainInputMethodPicker() throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void forceHideSoftInput() throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public int getCurTokenDisplayId() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public int getCurrentFocusDisplayID() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public InputMethodInfo getCurrentInputMethodInfoAsUser(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public InputMethodSubtype getCurrentInputMethodSubtype(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean getDexSettingsValue(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public InputMethodInfoSafeList getEnabledInputMethodList(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public List<InputMethodInfo> getEnabledInputMethodListLegacy(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public List<InputMethodSubtype> getEnabledInputMethodSubtypeList(String str, boolean z, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public IImeTracker getImeTrackerService() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public InputMethodInfoSafeList getInputMethodList(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public List<InputMethodInfo> getInputMethodListLegacy(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public int getInputMethodWindowVisibleHeight(IInputMethodClient iInputMethodClient) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public InputMethodSubtype getLastInputMethodSubtype(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean getWACOMPen() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void handleVoiceHWKey() throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean hideSoftInput(IInputMethodClient iInputMethodClient, IBinder iBinder, ImeTracker.Token token, int i, ResultReceiver resultReceiver, int i2, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void hideSoftInputFromServerForTest() throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public int isAccessoryKeyboard() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean isCurrentInputMethodAsSamsungKeyboard() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean isImeTraceEnabled() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean isInputMethodPickerShownForTest() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean isInputMethodShown() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean isStylusHandwritingAvailableAsUser(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean minimizeSoftInput(IInputMethodClient iInputMethodClient, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void onImeSwitchButtonClickFromSystem(int i) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void prepareStylusHandwritingDelegation(IInputMethodClient iInputMethodClient, int i, String str, String str2) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void removeImeSurface(int i) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void removeImeSurfaceFromWindowAsync(IBinder iBinder) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void reportPerceptibleAsync(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void setAdditionalInputMethodSubtypes(String str, InputMethodSubtype[] inputMethodSubtypeArr, int i) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void setExplicitlyEnabledInputMethodSubtypes(String str, int[] iArr, int i) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void setInputMethodSwitchDisable(IInputMethodClient iInputMethodClient, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void setStylusWindowIdleTimeoutForTest(IInputMethodClient iInputMethodClient, long j) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean shouldShowImeSwitcherButtonForTest() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void showInputMethodPickerFromClient(IInputMethodClient iInputMethodClient, int i) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void showInputMethodPickerFromSystem(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void showInputMethodPickerFromSystemWithUserId(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public boolean showSoftInput(IInputMethodClient iInputMethodClient, IBinder iBinder, ImeTracker.Token token, int i, int i2, ResultReceiver resultReceiver, int i3, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void startConnectionlessStylusHandwriting(IInputMethodClient iInputMethodClient, int i, CursorAnchorInfo cursorAnchorInfo, String str, String str2, IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void startImeTrace() throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public InputBindResult startInputOrWindowGainedFocus(int i, IInputMethodClient iInputMethodClient, IBinder iBinder, int i2, int i3, int i4, EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void startInputOrWindowGainedFocusAsync(int i, IInputMethodClient iInputMethodClient, IBinder iBinder, int i2, int i3, int i4, EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, boolean z, int i7, boolean z2) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void startProtoDump(byte[] bArr, int i, String str) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void startStylusHandwriting(IInputMethodClient iInputMethodClient) throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void stopImeTrace() throws RemoteException {
        }

        @Override // com.android.internal.view.IInputMethodManager
        public void undoMinimizeSoftInput() throws RemoteException {
        }
    }

    boolean acceptStylusHandwritingDelegation(IInputMethodClient iInputMethodClient, int i, String str, String str2, int i2) throws RemoteException;

    void acceptStylusHandwritingDelegationAsync(IInputMethodClient iInputMethodClient, int i, String str, String str2, int i2, IBooleanListener iBooleanListener) throws RemoteException;

    void addClient(IInputMethodClient iInputMethodClient, IRemoteInputConnection iRemoteInputConnection, int i) throws RemoteException;

    void addVirtualStylusIdForTestSession(IInputMethodClient iInputMethodClient) throws RemoteException;

    void dismissAndShowAgainInputMethodPicker() throws RemoteException;

    void forceHideSoftInput() throws RemoteException;

    int getCurTokenDisplayId() throws RemoteException;

    int getCurrentFocusDisplayID() throws RemoteException;

    InputMethodInfo getCurrentInputMethodInfoAsUser(int i) throws RemoteException;

    InputMethodSubtype getCurrentInputMethodSubtype(int i) throws RemoteException;

    boolean getDexSettingsValue(String str, String str2) throws RemoteException;

    InputMethodInfoSafeList getEnabledInputMethodList(int i) throws RemoteException;

    List<InputMethodInfo> getEnabledInputMethodListLegacy(int i) throws RemoteException;

    List<InputMethodSubtype> getEnabledInputMethodSubtypeList(String str, boolean z, int i) throws RemoteException;

    IImeTracker getImeTrackerService() throws RemoteException;

    InputMethodInfoSafeList getInputMethodList(int i, int i2) throws RemoteException;

    List<InputMethodInfo> getInputMethodListLegacy(int i, int i2) throws RemoteException;

    int getInputMethodWindowVisibleHeight(IInputMethodClient iInputMethodClient) throws RemoteException;

    InputMethodSubtype getLastInputMethodSubtype(int i) throws RemoteException;

    boolean getWACOMPen() throws RemoteException;

    void handleVoiceHWKey() throws RemoteException;

    boolean hideSoftInput(IInputMethodClient iInputMethodClient, IBinder iBinder, ImeTracker.Token token, int i, ResultReceiver resultReceiver, int i2, boolean z) throws RemoteException;

    void hideSoftInputFromServerForTest() throws RemoteException;

    int isAccessoryKeyboard() throws RemoteException;

    boolean isCurrentInputMethodAsSamsungKeyboard() throws RemoteException;

    boolean isImeTraceEnabled() throws RemoteException;

    boolean isInputMethodPickerShownForTest() throws RemoteException;

    boolean isInputMethodShown() throws RemoteException;

    boolean isStylusHandwritingAvailableAsUser(int i, boolean z) throws RemoteException;

    boolean minimizeSoftInput(IInputMethodClient iInputMethodClient, int i) throws RemoteException;

    void onImeSwitchButtonClickFromSystem(int i) throws RemoteException;

    void prepareStylusHandwritingDelegation(IInputMethodClient iInputMethodClient, int i, String str, String str2) throws RemoteException;

    void removeImeSurface(int i) throws RemoteException;

    void removeImeSurfaceFromWindowAsync(IBinder iBinder) throws RemoteException;

    void reportPerceptibleAsync(IBinder iBinder, boolean z) throws RemoteException;

    void setAdditionalInputMethodSubtypes(String str, InputMethodSubtype[] inputMethodSubtypeArr, int i) throws RemoteException;

    void setExplicitlyEnabledInputMethodSubtypes(String str, int[] iArr, int i) throws RemoteException;

    void setInputMethodSwitchDisable(IInputMethodClient iInputMethodClient, boolean z) throws RemoteException;

    void setStylusWindowIdleTimeoutForTest(IInputMethodClient iInputMethodClient, long j) throws RemoteException;

    boolean shouldShowImeSwitcherButtonForTest() throws RemoteException;

    void showInputMethodPickerFromClient(IInputMethodClient iInputMethodClient, int i) throws RemoteException;

    void showInputMethodPickerFromSystem(int i, int i2) throws RemoteException;

    void showInputMethodPickerFromSystemWithUserId(int i, int i2, int i3) throws RemoteException;

    boolean showSoftInput(IInputMethodClient iInputMethodClient, IBinder iBinder, ImeTracker.Token token, int i, int i2, ResultReceiver resultReceiver, int i3, boolean z) throws RemoteException;

    void startConnectionlessStylusHandwriting(IInputMethodClient iInputMethodClient, int i, CursorAnchorInfo cursorAnchorInfo, String str, String str2, IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback) throws RemoteException;

    void startImeTrace() throws RemoteException;

    InputBindResult startInputOrWindowGainedFocus(int i, IInputMethodClient iInputMethodClient, IBinder iBinder, int i2, int i3, int i4, EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, boolean z) throws RemoteException;

    void startInputOrWindowGainedFocusAsync(int i, IInputMethodClient iInputMethodClient, IBinder iBinder, int i2, int i3, int i4, EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, boolean z, int i7, boolean z2) throws RemoteException;

    void startProtoDump(byte[] bArr, int i, String str) throws RemoteException;

    void startStylusHandwriting(IInputMethodClient iInputMethodClient) throws RemoteException;

    void stopImeTrace() throws RemoteException;

    void undoMinimizeSoftInput() throws RemoteException;

    public static abstract class Stub extends Binder implements IInputMethodManager {
        public static final String DESCRIPTOR = "com.android.internal.view.IInputMethodManager";
        static final int TRANSACTION_acceptStylusHandwritingDelegation = 33;
        static final int TRANSACTION_acceptStylusHandwritingDelegationAsync = 34;
        static final int TRANSACTION_addClient = 1;
        static final int TRANSACTION_addVirtualStylusIdForTestSession = 36;
        static final int TRANSACTION_dismissAndShowAgainInputMethodPicker = 48;
        static final int TRANSACTION_forceHideSoftInput = 41;
        static final int TRANSACTION_getCurTokenDisplayId = 50;
        static final int TRANSACTION_getCurrentFocusDisplayID = 49;
        static final int TRANSACTION_getCurrentInputMethodInfoAsUser = 2;
        static final int TRANSACTION_getCurrentInputMethodSubtype = 19;
        static final int TRANSACTION_getDexSettingsValue = 46;
        static final int TRANSACTION_getEnabledInputMethodList = 4;
        static final int TRANSACTION_getEnabledInputMethodListLegacy = 6;
        static final int TRANSACTION_getEnabledInputMethodSubtypeList = 7;
        static final int TRANSACTION_getImeTrackerService = 38;
        static final int TRANSACTION_getInputMethodList = 3;
        static final int TRANSACTION_getInputMethodListLegacy = 5;
        static final int TRANSACTION_getInputMethodWindowVisibleHeight = 22;
        static final int TRANSACTION_getLastInputMethodSubtype = 8;
        static final int TRANSACTION_getWACOMPen = 43;
        static final int TRANSACTION_handleVoiceHWKey = 51;
        static final int TRANSACTION_hideSoftInput = 10;
        static final int TRANSACTION_hideSoftInputFromServerForTest = 11;
        static final int TRANSACTION_isAccessoryKeyboard = 42;
        static final int TRANSACTION_isCurrentInputMethodAsSamsungKeyboard = 45;
        static final int TRANSACTION_isImeTraceEnabled = 27;
        static final int TRANSACTION_isInputMethodPickerShownForTest = 16;
        static final int TRANSACTION_isInputMethodShown = 44;
        static final int TRANSACTION_isStylusHandwritingAvailableAsUser = 35;
        static final int TRANSACTION_minimizeSoftInput = 39;
        static final int TRANSACTION_onImeSwitchButtonClickFromSystem = 17;
        static final int TRANSACTION_prepareStylusHandwritingDelegation = 32;
        static final int TRANSACTION_removeImeSurface = 24;
        static final int TRANSACTION_removeImeSurfaceFromWindowAsync = 25;
        static final int TRANSACTION_reportPerceptibleAsync = 23;
        static final int TRANSACTION_setAdditionalInputMethodSubtypes = 20;
        static final int TRANSACTION_setExplicitlyEnabledInputMethodSubtypes = 21;
        static final int TRANSACTION_setInputMethodSwitchDisable = 47;
        static final int TRANSACTION_setStylusWindowIdleTimeoutForTest = 37;
        static final int TRANSACTION_shouldShowImeSwitcherButtonForTest = 18;
        static final int TRANSACTION_showInputMethodPickerFromClient = 14;
        static final int TRANSACTION_showInputMethodPickerFromSystem = 15;
        static final int TRANSACTION_showInputMethodPickerFromSystemWithUserId = 52;
        static final int TRANSACTION_showSoftInput = 9;
        static final int TRANSACTION_startConnectionlessStylusHandwriting = 31;
        static final int TRANSACTION_startImeTrace = 28;
        static final int TRANSACTION_startInputOrWindowGainedFocus = 12;
        static final int TRANSACTION_startInputOrWindowGainedFocusAsync = 13;
        static final int TRANSACTION_startProtoDump = 26;
        static final int TRANSACTION_startStylusHandwriting = 30;
        static final int TRANSACTION_stopImeTrace = 29;
        static final int TRANSACTION_undoMinimizeSoftInput = 40;
        private final PermissionEnforcer mEnforcer;
        static final String[] PERMISSIONS_showInputMethodPickerFromSystem = {Manifest.permission.WRITE_SECURE_SETTINGS, Manifest.permission.INTERACT_ACROSS_USERS_FULL};
        static final String[] PERMISSIONS_onImeSwitchButtonClickFromSystem = {Manifest.permission.WRITE_SECURE_SETTINGS, Manifest.permission.INTERACT_ACROSS_USERS_FULL};
        static final String[] PERMISSIONS_removeImeSurface = {Manifest.permission.INTERNAL_SYSTEM_WINDOW, Manifest.permission.INTERACT_ACROSS_USERS_FULL};
        static final String[] PERMISSIONS_showInputMethodPickerFromSystemWithUserId = {Manifest.permission.WRITE_SECURE_SETTINGS, Manifest.permission.INTERACT_ACROSS_USERS_FULL};

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 51;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IInputMethodManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInputMethodManager)) {
                return (IInputMethodManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "onImeSwitchButtonClickFromSystem";
                case 18:
                    return "shouldShowImeSwitcherButtonForTest";
                case 19:
                    return "getCurrentInputMethodSubtype";
                case 20:
                    return "setAdditionalInputMethodSubtypes";
                case 21:
                    return "setExplicitlyEnabledInputMethodSubtypes";
                case 22:
                    return "getInputMethodWindowVisibleHeight";
                case 23:
                    return "reportPerceptibleAsync";
                case 24:
                    return "removeImeSurface";
                case 25:
                    return "removeImeSurfaceFromWindowAsync";
                case 26:
                    return "startProtoDump";
                case 27:
                    return "isImeTraceEnabled";
                case 28:
                    return "startImeTrace";
                case 29:
                    return "stopImeTrace";
                case 30:
                    return "startStylusHandwriting";
                case 31:
                    return "startConnectionlessStylusHandwriting";
                case 32:
                    return "prepareStylusHandwritingDelegation";
                case 33:
                    return "acceptStylusHandwritingDelegation";
                case 34:
                    return "acceptStylusHandwritingDelegationAsync";
                case 35:
                    return "isStylusHandwritingAvailableAsUser";
                case 36:
                    return "addVirtualStylusIdForTestSession";
                case 37:
                    return "setStylusWindowIdleTimeoutForTest";
                case 38:
                    return "getImeTrackerService";
                case 39:
                    return "minimizeSoftInput";
                case 40:
                    return "undoMinimizeSoftInput";
                case 41:
                    return "forceHideSoftInput";
                case 42:
                    return "isAccessoryKeyboard";
                case 43:
                    return "getWACOMPen";
                case 44:
                    return "isInputMethodShown";
                case 45:
                    return "isCurrentInputMethodAsSamsungKeyboard";
                case 46:
                    return "getDexSettingsValue";
                case 47:
                    return "setInputMethodSwitchDisable";
                case 48:
                    return "dismissAndShowAgainInputMethodPicker";
                case 49:
                    return "getCurrentFocusDisplayID";
                case 50:
                    return "getCurTokenDisplayId";
                case 51:
                    return "handleVoiceHWKey";
                case 52:
                    return "showInputMethodPickerFromSystemWithUserId";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IInputMethodClient asInterface = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    IRemoteInputConnection asInterface2 = IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addClient(asInterface, asInterface2, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputMethodInfo currentInputMethodInfoAsUser = getCurrentInputMethodInfoAsUser(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentInputMethodInfoAsUser, 1);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputMethodInfoSafeList inputMethodList = getInputMethodList(readInt3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputMethodList, 1);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputMethodInfoSafeList enabledInputMethodList = getEnabledInputMethodList(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enabledInputMethodList, 1);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<InputMethodInfo> inputMethodListLegacy = getInputMethodListLegacy(readInt6, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(inputMethodListLegacy, 1);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<InputMethodInfo> enabledInputMethodListLegacy = getEnabledInputMethodListLegacy(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enabledInputMethodListLegacy, 1);
                    return true;
                case 7:
                    String readString = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<InputMethodSubtype> enabledInputMethodSubtypeList = getEnabledInputMethodSubtypeList(readString, readBoolean, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enabledInputMethodSubtypeList, 1);
                    return true;
                case 8:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputMethodSubtype lastInputMethodSubtype = getLastInputMethodSubtype(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lastInputMethodSubtype, 1);
                    return true;
                case 9:
                    IInputMethodClient asInterface3 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    ImeTracker.Token token = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    int readInt13 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean showSoftInput = showSoftInput(asInterface3, readStrongBinder, token, readInt11, readInt12, resultReceiver, readInt13, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(showSoftInput);
                    return true;
                case 10:
                    IInputMethodClient asInterface4 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    ImeTracker.Token token2 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    int readInt14 = parcel.readInt();
                    ResultReceiver resultReceiver2 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    int readInt15 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean hideSoftInput = hideSoftInput(asInterface4, readStrongBinder2, token2, readInt14, resultReceiver2, readInt15, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hideSoftInput);
                    return true;
                case 11:
                    hideSoftInputFromServerForTest();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt16 = parcel.readInt();
                    IInputMethodClient asInterface5 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    EditorInfo editorInfo = (EditorInfo) parcel.readTypedObject(EditorInfo.CREATOR);
                    IRemoteInputConnection asInterface6 = IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    IRemoteAccessibilityInputConnection asInterface7 = IRemoteAccessibilityInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher = (ImeOnBackInvokedDispatcher) parcel.readTypedObject(ImeOnBackInvokedDispatcher.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    InputBindResult startInputOrWindowGainedFocus = startInputOrWindowGainedFocus(readInt16, asInterface5, readStrongBinder3, readInt17, readInt18, readInt19, editorInfo, asInterface6, asInterface7, readInt20, readInt21, imeOnBackInvokedDispatcher, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startInputOrWindowGainedFocus, 1);
                    return true;
                case 13:
                    int readInt22 = parcel.readInt();
                    IInputMethodClient asInterface8 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    EditorInfo editorInfo2 = (EditorInfo) parcel.readTypedObject(EditorInfo.CREATOR);
                    IRemoteInputConnection asInterface9 = IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    IRemoteAccessibilityInputConnection asInterface10 = IRemoteAccessibilityInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher2 = (ImeOnBackInvokedDispatcher) parcel.readTypedObject(ImeOnBackInvokedDispatcher.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt28 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startInputOrWindowGainedFocusAsync(readInt22, asInterface8, readStrongBinder4, readInt23, readInt24, readInt25, editorInfo2, asInterface9, asInterface10, readInt26, readInt27, imeOnBackInvokedDispatcher2, readBoolean5, readInt28, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IInputMethodClient asInterface11 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showInputMethodPickerFromClient(asInterface11, readInt29);
                    parcel2.writeNoException();
                    break;
                case 15:
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showInputMethodPickerFromSystem(readInt30, readInt31);
                    parcel2.writeNoException();
                    break;
                case 16:
                    boolean isInputMethodPickerShownForTest = isInputMethodPickerShownForTest();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInputMethodPickerShownForTest);
                    break;
                case 17:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onImeSwitchButtonClickFromSystem(readInt32);
                    break;
                case 18:
                    boolean shouldShowImeSwitcherButtonForTest = shouldShowImeSwitcherButtonForTest();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldShowImeSwitcherButtonForTest);
                    break;
                case 19:
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputMethodSubtype currentInputMethodSubtype = getCurrentInputMethodSubtype(readInt33);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentInputMethodSubtype, 1);
                    break;
                case 20:
                    String readString2 = parcel.readString();
                    InputMethodSubtype[] inputMethodSubtypeArr = (InputMethodSubtype[]) parcel.createTypedArray(InputMethodSubtype.CREATOR);
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAdditionalInputMethodSubtypes(readString2, inputMethodSubtypeArr, readInt34);
                    parcel2.writeNoException();
                    break;
                case 21:
                    String readString3 = parcel.readString();
                    int[] createIntArray = parcel.createIntArray();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setExplicitlyEnabledInputMethodSubtypes(readString3, createIntArray, readInt35);
                    parcel2.writeNoException();
                    break;
                case 22:
                    IInputMethodClient asInterface12 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int inputMethodWindowVisibleHeight = getInputMethodWindowVisibleHeight(asInterface12);
                    parcel2.writeNoException();
                    parcel2.writeInt(inputMethodWindowVisibleHeight);
                    break;
                case 23:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportPerceptibleAsync(readStrongBinder5, readBoolean7);
                    break;
                case 24:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeImeSurface(readInt36);
                    parcel2.writeNoException();
                    break;
                case 25:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    removeImeSurfaceFromWindowAsync(readStrongBinder6);
                    break;
                case 26:
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt37 = parcel.readInt();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startProtoDump(createByteArray, readInt37, readString4);
                    parcel2.writeNoException();
                    break;
                case 27:
                    boolean isImeTraceEnabled = isImeTraceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isImeTraceEnabled);
                    break;
                case 28:
                    startImeTrace();
                    parcel2.writeNoException();
                    break;
                case 29:
                    stopImeTrace();
                    parcel2.writeNoException();
                    break;
                case 30:
                    IInputMethodClient asInterface13 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startStylusHandwriting(asInterface13);
                    parcel2.writeNoException();
                    break;
                case 31:
                    IInputMethodClient asInterface14 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt38 = parcel.readInt();
                    CursorAnchorInfo cursorAnchorInfo = (CursorAnchorInfo) parcel.readTypedObject(CursorAnchorInfo.CREATOR);
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    IConnectionlessHandwritingCallback asInterface15 = IConnectionlessHandwritingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startConnectionlessStylusHandwriting(asInterface14, readInt38, cursorAnchorInfo, readString5, readString6, asInterface15);
                    break;
                case 32:
                    IInputMethodClient asInterface16 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt39 = parcel.readInt();
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    prepareStylusHandwritingDelegation(asInterface16, readInt39, readString7, readString8);
                    parcel2.writeNoException();
                    break;
                case 33:
                    IInputMethodClient asInterface17 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt40 = parcel.readInt();
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean acceptStylusHandwritingDelegation = acceptStylusHandwritingDelegation(asInterface17, readInt40, readString9, readString10, readInt41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(acceptStylusHandwritingDelegation);
                    break;
                case 34:
                    IInputMethodClient asInterface18 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt42 = parcel.readInt();
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    int readInt43 = parcel.readInt();
                    IBooleanListener asInterface19 = IBooleanListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    acceptStylusHandwritingDelegationAsync(asInterface18, readInt42, readString11, readString12, readInt43, asInterface19);
                    break;
                case 35:
                    int readInt44 = parcel.readInt();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isStylusHandwritingAvailableAsUser = isStylusHandwritingAvailableAsUser(readInt44, readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStylusHandwritingAvailableAsUser);
                    break;
                case 36:
                    IInputMethodClient asInterface20 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addVirtualStylusIdForTestSession(asInterface20);
                    parcel2.writeNoException();
                    break;
                case 37:
                    IInputMethodClient asInterface21 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setStylusWindowIdleTimeoutForTest(asInterface21, readLong);
                    parcel2.writeNoException();
                    break;
                case 38:
                    IImeTracker imeTrackerService = getImeTrackerService();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(imeTrackerService);
                    break;
                case 39:
                    IInputMethodClient asInterface22 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean minimizeSoftInput = minimizeSoftInput(asInterface22, readInt45);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(minimizeSoftInput);
                    break;
                case 40:
                    undoMinimizeSoftInput();
                    parcel2.writeNoException();
                    break;
                case 41:
                    forceHideSoftInput();
                    parcel2.writeNoException();
                    break;
                case 42:
                    int isAccessoryKeyboard = isAccessoryKeyboard();
                    parcel2.writeNoException();
                    parcel2.writeInt(isAccessoryKeyboard);
                    break;
                case 43:
                    boolean wACOMPen = getWACOMPen();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wACOMPen);
                    break;
                case 44:
                    boolean isInputMethodShown = isInputMethodShown();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInputMethodShown);
                    break;
                case 45:
                    boolean isCurrentInputMethodAsSamsungKeyboard = isCurrentInputMethodAsSamsungKeyboard();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCurrentInputMethodAsSamsungKeyboard);
                    break;
                case 46:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean dexSettingsValue = getDexSettingsValue(readString13, readString14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dexSettingsValue);
                    break;
                case 47:
                    IInputMethodClient asInterface23 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInputMethodSwitchDisable(asInterface23, readBoolean9);
                    parcel2.writeNoException();
                    break;
                case 48:
                    dismissAndShowAgainInputMethodPicker();
                    parcel2.writeNoException();
                    break;
                case 49:
                    int currentFocusDisplayID = getCurrentFocusDisplayID();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentFocusDisplayID);
                    break;
                case 50:
                    int curTokenDisplayId = getCurTokenDisplayId();
                    parcel2.writeNoException();
                    parcel2.writeInt(curTokenDisplayId);
                    break;
                case 51:
                    handleVoiceHWKey();
                    parcel2.writeNoException();
                    break;
                case 52:
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showInputMethodPickerFromSystemWithUserId(readInt46, readInt47, readInt48);
                    parcel2.writeNoException();
                    break;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IInputMethodManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void addClient(IInputMethodClient iInputMethodClient, IRemoteInputConnection iRemoteInputConnection, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeStrongInterface(iRemoteInputConnection);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputMethodInfo getCurrentInputMethodInfoAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (InputMethodInfo) obtain2.readTypedObject(InputMethodInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputMethodInfoSafeList getInputMethodList(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (InputMethodInfoSafeList) obtain2.readTypedObject(InputMethodInfoSafeList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputMethodInfoSafeList getEnabledInputMethodList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (InputMethodInfoSafeList) obtain2.readTypedObject(InputMethodInfoSafeList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public List<InputMethodInfo> getInputMethodListLegacy(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(InputMethodInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public List<InputMethodInfo> getEnabledInputMethodListLegacy(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(InputMethodInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public List<InputMethodSubtype> getEnabledInputMethodSubtypeList(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(InputMethodSubtype.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputMethodSubtype getLastInputMethodSubtype(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (InputMethodSubtype) obtain2.readTypedObject(InputMethodSubtype.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean showSoftInput(IInputMethodClient iInputMethodClient, IBinder iBinder, ImeTracker.Token token, int i, int i2, ResultReceiver resultReceiver, int i3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(token, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(resultReceiver, 0);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean hideSoftInput(IInputMethodClient iInputMethodClient, IBinder iBinder, ImeTracker.Token token, int i, ResultReceiver resultReceiver, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(token, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resultReceiver, 0);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void hideSoftInputFromServerForTest() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputBindResult startInputOrWindowGainedFocus(int i, IInputMethodClient iInputMethodClient, IBinder iBinder, int i2, int i3, int i4, EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(editorInfo, 0);
                    obtain.writeStrongInterface(iRemoteInputConnection);
                    obtain.writeStrongInterface(iRemoteAccessibilityInputConnection);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    obtain.writeTypedObject(imeOnBackInvokedDispatcher, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (InputBindResult) obtain2.readTypedObject(InputBindResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startInputOrWindowGainedFocusAsync(int i, IInputMethodClient iInputMethodClient, IBinder iBinder, int i2, int i3, int i4, EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, boolean z, int i7, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(editorInfo, 0);
                    obtain.writeStrongInterface(iRemoteInputConnection);
                    obtain.writeStrongInterface(iRemoteAccessibilityInputConnection);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    obtain.writeTypedObject(imeOnBackInvokedDispatcher, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i7);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void showInputMethodPickerFromClient(IInputMethodClient iInputMethodClient, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void showInputMethodPickerFromSystem(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isInputMethodPickerShownForTest() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void onImeSwitchButtonClickFromSystem(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean shouldShowImeSwitcherButtonForTest() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputMethodSubtype getCurrentInputMethodSubtype(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return (InputMethodSubtype) obtain2.readTypedObject(InputMethodSubtype.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void setAdditionalInputMethodSubtypes(String str, InputMethodSubtype[] inputMethodSubtypeArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedArray(inputMethodSubtypeArr, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void setExplicitlyEnabledInputMethodSubtypes(String str, int[] iArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public int getInputMethodWindowVisibleHeight(IInputMethodClient iInputMethodClient) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void reportPerceptibleAsync(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void removeImeSurface(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void removeImeSurfaceFromWindowAsync(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startProtoDump(byte[] bArr, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isImeTraceEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startImeTrace() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void stopImeTrace() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startStylusHandwriting(IInputMethodClient iInputMethodClient) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startConnectionlessStylusHandwriting(IInputMethodClient iInputMethodClient, int i, CursorAnchorInfo cursorAnchorInfo, String str, String str2, IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cursorAnchorInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iConnectionlessHandwritingCallback);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void prepareStylusHandwritingDelegation(IInputMethodClient iInputMethodClient, int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean acceptStylusHandwritingDelegation(IInputMethodClient iInputMethodClient, int i, String str, String str2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void acceptStylusHandwritingDelegationAsync(IInputMethodClient iInputMethodClient, int i, String str, String str2, int i2, IBooleanListener iBooleanListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iBooleanListener);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isStylusHandwritingAvailableAsUser(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void addVirtualStylusIdForTestSession(IInputMethodClient iInputMethodClient) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void setStylusWindowIdleTimeoutForTest(IInputMethodClient iInputMethodClient, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeLong(j);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public IImeTracker getImeTrackerService() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImeTracker.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean minimizeSoftInput(IInputMethodClient iInputMethodClient, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void undoMinimizeSoftInput() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void forceHideSoftInput() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public int isAccessoryKeyboard() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean getWACOMPen() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isInputMethodShown() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isCurrentInputMethodAsSamsungKeyboard() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean getDexSettingsValue(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void setInputMethodSwitchDisable(IInputMethodClient iInputMethodClient, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodClient);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void dismissAndShowAgainInputMethodPicker() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public int getCurrentFocusDisplayID() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public int getCurTokenDisplayId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void handleVoiceHWKey() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void showInputMethodPickerFromSystemWithUserId(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void hideSoftInputFromServerForTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }

        protected void showInputMethodPickerFromSystem_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_showInputMethodPickerFromSystem, getCallingPid(), getCallingUid());
        }

        protected void isInputMethodPickerShownForTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }

        protected void onImeSwitchButtonClickFromSystem_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_onImeSwitchButtonClickFromSystem, getCallingPid(), getCallingUid());
        }

        protected void shouldShowImeSwitcherButtonForTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }

        protected void removeImeSurface_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_removeImeSurface, getCallingPid(), getCallingUid());
        }

        protected void startImeTrace_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_UI_TRACING, getCallingPid(), getCallingUid());
        }

        protected void stopImeTrace_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CONTROL_UI_TRACING, getCallingPid(), getCallingUid());
        }

        protected void addVirtualStylusIdForTestSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }

        protected void setStylusWindowIdleTimeoutForTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_INPUT_METHOD, getCallingPid(), getCallingUid());
        }

        protected void showInputMethodPickerFromSystemWithUserId_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_showInputMethodPickerFromSystemWithUserId, getCallingPid(), getCallingUid());
        }
    }
}
