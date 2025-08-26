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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInputMethodManager)) {
                return (IInputMethodManager) iInterfaceQueryLocalInterface;
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
                    IInputMethodClient iInputMethodClientAsInterface = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    IRemoteInputConnection iRemoteInputConnectionAsInterface = IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addClient(iInputMethodClientAsInterface, iRemoteInputConnectionAsInterface, i3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputMethodInfo currentInputMethodInfoAsUser = getCurrentInputMethodInfoAsUser(i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentInputMethodInfoAsUser, 1);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputMethodInfoSafeList inputMethodList = getInputMethodList(i5, i6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputMethodList, 1);
                    return true;
                case 4:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputMethodInfoSafeList enabledInputMethodList = getEnabledInputMethodList(i7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enabledInputMethodList, 1);
                    return true;
                case 5:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<InputMethodInfo> inputMethodListLegacy = getInputMethodListLegacy(i8, i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(inputMethodListLegacy, 1);
                    return true;
                case 6:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<InputMethodInfo> enabledInputMethodListLegacy = getEnabledInputMethodListLegacy(i10);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enabledInputMethodListLegacy, 1);
                    return true;
                case 7:
                    String string = parcel.readString();
                    boolean z = parcel.readBoolean();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<InputMethodSubtype> enabledInputMethodSubtypeList = getEnabledInputMethodSubtypeList(string, z, i11);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enabledInputMethodSubtypeList, 1);
                    return true;
                case 8:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputMethodSubtype lastInputMethodSubtype = getLastInputMethodSubtype(i12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(lastInputMethodSubtype, 1);
                    return true;
                case 9:
                    IInputMethodClient iInputMethodClientAsInterface2 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    IBinder strongBinder = parcel.readStrongBinder();
                    ImeTracker.Token token = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    int i15 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zShowSoftInput = showSoftInput(iInputMethodClientAsInterface2, strongBinder, token, i13, i14, resultReceiver, i15, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShowSoftInput);
                    return true;
                case 10:
                    IInputMethodClient iInputMethodClientAsInterface3 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    ImeTracker.Token token2 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    int i16 = parcel.readInt();
                    ResultReceiver resultReceiver2 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    int i17 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zHideSoftInput = hideSoftInput(iInputMethodClientAsInterface3, strongBinder2, token2, i16, resultReceiver2, i17, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHideSoftInput);
                    return true;
                case 11:
                    hideSoftInputFromServerForTest();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i18 = parcel.readInt();
                    IInputMethodClient iInputMethodClientAsInterface4 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    EditorInfo editorInfo = (EditorInfo) parcel.readTypedObject(EditorInfo.CREATOR);
                    IRemoteInputConnection iRemoteInputConnectionAsInterface2 = IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnectionAsInterface = IRemoteAccessibilityInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher = (ImeOnBackInvokedDispatcher) parcel.readTypedObject(ImeOnBackInvokedDispatcher.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    InputBindResult inputBindResultStartInputOrWindowGainedFocus = startInputOrWindowGainedFocus(i18, iInputMethodClientAsInterface4, strongBinder3, i19, i20, i21, editorInfo, iRemoteInputConnectionAsInterface2, iRemoteAccessibilityInputConnectionAsInterface, i22, i23, imeOnBackInvokedDispatcher, z4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputBindResultStartInputOrWindowGainedFocus, 1);
                    return true;
                case 13:
                    int i24 = parcel.readInt();
                    IInputMethodClient iInputMethodClientAsInterface5 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    int i27 = parcel.readInt();
                    EditorInfo editorInfo2 = (EditorInfo) parcel.readTypedObject(EditorInfo.CREATOR);
                    IRemoteInputConnection iRemoteInputConnectionAsInterface3 = IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnectionAsInterface2 = IRemoteAccessibilityInputConnection.Stub.asInterface(parcel.readStrongBinder());
                    int i28 = parcel.readInt();
                    int i29 = parcel.readInt();
                    ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher2 = (ImeOnBackInvokedDispatcher) parcel.readTypedObject(ImeOnBackInvokedDispatcher.CREATOR);
                    boolean z5 = parcel.readBoolean();
                    int i30 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startInputOrWindowGainedFocusAsync(i24, iInputMethodClientAsInterface5, strongBinder4, i25, i26, i27, editorInfo2, iRemoteInputConnectionAsInterface3, iRemoteAccessibilityInputConnectionAsInterface2, i28, i29, imeOnBackInvokedDispatcher2, z5, i30, z6);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IInputMethodClient iInputMethodClientAsInterface6 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showInputMethodPickerFromClient(iInputMethodClientAsInterface6, i31);
                    parcel2.writeNoException();
                    break;
                case 15:
                    int i32 = parcel.readInt();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showInputMethodPickerFromSystem(i32, i33);
                    parcel2.writeNoException();
                    break;
                case 16:
                    boolean zIsInputMethodPickerShownForTest = isInputMethodPickerShownForTest();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInputMethodPickerShownForTest);
                    break;
                case 17:
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onImeSwitchButtonClickFromSystem(i34);
                    break;
                case 18:
                    boolean zShouldShowImeSwitcherButtonForTest = shouldShowImeSwitcherButtonForTest();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldShowImeSwitcherButtonForTest);
                    break;
                case 19:
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    InputMethodSubtype currentInputMethodSubtype = getCurrentInputMethodSubtype(i35);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentInputMethodSubtype, 1);
                    break;
                case 20:
                    String string2 = parcel.readString();
                    InputMethodSubtype[] inputMethodSubtypeArr = (InputMethodSubtype[]) parcel.createTypedArray(InputMethodSubtype.CREATOR);
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAdditionalInputMethodSubtypes(string2, inputMethodSubtypeArr, i36);
                    parcel2.writeNoException();
                    break;
                case 21:
                    String string3 = parcel.readString();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setExplicitlyEnabledInputMethodSubtypes(string3, iArrCreateIntArray, i37);
                    parcel2.writeNoException();
                    break;
                case 22:
                    IInputMethodClient iInputMethodClientAsInterface7 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int inputMethodWindowVisibleHeight = getInputMethodWindowVisibleHeight(iInputMethodClientAsInterface7);
                    parcel2.writeNoException();
                    parcel2.writeInt(inputMethodWindowVisibleHeight);
                    break;
                case 23:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportPerceptibleAsync(strongBinder5, z7);
                    break;
                case 24:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeImeSurface(i38);
                    parcel2.writeNoException();
                    break;
                case 25:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    removeImeSurfaceFromWindowAsync(strongBinder6);
                    break;
                case 26:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    int i39 = parcel.readInt();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startProtoDump(bArrCreateByteArray, i39, string4);
                    parcel2.writeNoException();
                    break;
                case 27:
                    boolean zIsImeTraceEnabled = isImeTraceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsImeTraceEnabled);
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
                    IInputMethodClient iInputMethodClientAsInterface8 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startStylusHandwriting(iInputMethodClientAsInterface8);
                    parcel2.writeNoException();
                    break;
                case 31:
                    IInputMethodClient iInputMethodClientAsInterface9 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    int i40 = parcel.readInt();
                    CursorAnchorInfo cursorAnchorInfo = (CursorAnchorInfo) parcel.readTypedObject(CursorAnchorInfo.CREATOR);
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    IConnectionlessHandwritingCallback iConnectionlessHandwritingCallbackAsInterface = IConnectionlessHandwritingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startConnectionlessStylusHandwriting(iInputMethodClientAsInterface9, i40, cursorAnchorInfo, string5, string6, iConnectionlessHandwritingCallbackAsInterface);
                    break;
                case 32:
                    IInputMethodClient iInputMethodClientAsInterface10 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    int i41 = parcel.readInt();
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    prepareStylusHandwritingDelegation(iInputMethodClientAsInterface10, i41, string7, string8);
                    parcel2.writeNoException();
                    break;
                case 33:
                    IInputMethodClient iInputMethodClientAsInterface11 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    int i42 = parcel.readInt();
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zAcceptStylusHandwritingDelegation = acceptStylusHandwritingDelegation(iInputMethodClientAsInterface11, i42, string9, string10, i43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAcceptStylusHandwritingDelegation);
                    break;
                case 34:
                    IInputMethodClient iInputMethodClientAsInterface12 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    int i44 = parcel.readInt();
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    int i45 = parcel.readInt();
                    IBooleanListener iBooleanListenerAsInterface = IBooleanListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    acceptStylusHandwritingDelegationAsync(iInputMethodClientAsInterface12, i44, string11, string12, i45, iBooleanListenerAsInterface);
                    break;
                case 35:
                    int i46 = parcel.readInt();
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsStylusHandwritingAvailableAsUser = isStylusHandwritingAvailableAsUser(i46, z8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsStylusHandwritingAvailableAsUser);
                    break;
                case 36:
                    IInputMethodClient iInputMethodClientAsInterface13 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addVirtualStylusIdForTestSession(iInputMethodClientAsInterface13);
                    parcel2.writeNoException();
                    break;
                case 37:
                    IInputMethodClient iInputMethodClientAsInterface14 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setStylusWindowIdleTimeoutForTest(iInputMethodClientAsInterface14, j);
                    parcel2.writeNoException();
                    break;
                case 38:
                    IImeTracker imeTrackerService = getImeTrackerService();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(imeTrackerService);
                    break;
                case 39:
                    IInputMethodClient iInputMethodClientAsInterface15 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zMinimizeSoftInput = minimizeSoftInput(iInputMethodClientAsInterface15, i47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMinimizeSoftInput);
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
                    int iIsAccessoryKeyboard = isAccessoryKeyboard();
                    parcel2.writeNoException();
                    parcel2.writeInt(iIsAccessoryKeyboard);
                    break;
                case 43:
                    boolean wACOMPen = getWACOMPen();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wACOMPen);
                    break;
                case 44:
                    boolean zIsInputMethodShown = isInputMethodShown();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInputMethodShown);
                    break;
                case 45:
                    boolean zIsCurrentInputMethodAsSamsungKeyboard = isCurrentInputMethodAsSamsungKeyboard();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCurrentInputMethodAsSamsungKeyboard);
                    break;
                case 46:
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean dexSettingsValue = getDexSettingsValue(string13, string14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dexSettingsValue);
                    break;
                case 47:
                    IInputMethodClient iInputMethodClientAsInterface16 = IInputMethodClient.Stub.asInterface(parcel.readStrongBinder());
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInputMethodSwitchDisable(iInputMethodClientAsInterface16, z9);
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
                    int i48 = parcel.readInt();
                    int i49 = parcel.readInt();
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showInputMethodPickerFromSystemWithUserId(i48, i49, i50);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputMethodClient);
                    parcelObtain.writeStrongInterface(iRemoteInputConnection);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputMethodInfo getCurrentInputMethodInfoAsUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (InputMethodInfo) parcelObtain2.readTypedObject(InputMethodInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputMethodInfoSafeList getInputMethodList(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (InputMethodInfoSafeList) parcelObtain2.readTypedObject(InputMethodInfoSafeList.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputMethodInfoSafeList getEnabledInputMethodList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (InputMethodInfoSafeList) parcelObtain2.readTypedObject(InputMethodInfoSafeList.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public List<InputMethodInfo> getInputMethodListLegacy(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(InputMethodInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public List<InputMethodInfo> getEnabledInputMethodListLegacy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(InputMethodInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public List<InputMethodSubtype> getEnabledInputMethodSubtypeList(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(InputMethodSubtype.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputMethodSubtype getLastInputMethodSubtype(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (InputMethodSubtype) parcelObtain2.readTypedObject(InputMethodSubtype.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean showSoftInput(IInputMethodClient iInputMethodClient, IBinder iBinder, ImeTracker.Token token, int i, int i2, ResultReceiver resultReceiver, int i3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputMethodClient);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(token, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean hideSoftInput(IInputMethodClient iInputMethodClient, IBinder iBinder, ImeTracker.Token token, int i, ResultReceiver resultReceiver, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputMethodClient);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(token, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void hideSoftInputFromServerForTest() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputBindResult startInputOrWindowGainedFocus(int i, IInputMethodClient iInputMethodClient, IBinder iBinder, int i2, int i3, int i4, EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iInputMethodClient);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeTypedObject(editorInfo, 0);
                    parcelObtain.writeStrongInterface(iRemoteInputConnection);
                    parcelObtain.writeStrongInterface(iRemoteAccessibilityInputConnection);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeInt(i6);
                    parcelObtain.writeTypedObject(imeOnBackInvokedDispatcher, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (InputBindResult) parcelObtain2.readTypedObject(InputBindResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startInputOrWindowGainedFocusAsync(int i, IInputMethodClient iInputMethodClient, IBinder iBinder, int i2, int i3, int i4, EditorInfo editorInfo, IRemoteInputConnection iRemoteInputConnection, IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i5, int i6, ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher, boolean z, int i7, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iInputMethodClient);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeTypedObject(editorInfo, 0);
                    parcelObtain.writeStrongInterface(iRemoteInputConnection);
                    parcelObtain.writeStrongInterface(iRemoteAccessibilityInputConnection);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeInt(i6);
                    parcelObtain.writeTypedObject(imeOnBackInvokedDispatcher, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i7);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void showInputMethodPickerFromClient(IInputMethodClient iInputMethodClient, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputMethodClient);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void showInputMethodPickerFromSystem(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isInputMethodPickerShownForTest() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void onImeSwitchButtonClickFromSystem(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean shouldShowImeSwitcherButtonForTest() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public InputMethodSubtype getCurrentInputMethodSubtype(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (InputMethodSubtype) parcelObtain2.readTypedObject(InputMethodSubtype.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void setAdditionalInputMethodSubtypes(String str, InputMethodSubtype[] inputMethodSubtypeArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedArray(inputMethodSubtypeArr, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void setExplicitlyEnabledInputMethodSubtypes(String str, int[] iArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public int getInputMethodWindowVisibleHeight(IInputMethodClient iInputMethodClient) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputMethodClient);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void reportPerceptibleAsync(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(23, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void removeImeSurface(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void removeImeSurfaceFromWindowAsync(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(25, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startProtoDump(byte[] bArr, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isImeTraceEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startImeTrace() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void stopImeTrace() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startStylusHandwriting(IInputMethodClient iInputMethodClient) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputMethodClient);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void startConnectionlessStylusHandwriting(IInputMethodClient iInputMethodClient, int i, CursorAnchorInfo cursorAnchorInfo, String str, String str2, IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputMethodClient);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(cursorAnchorInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iConnectionlessHandwritingCallback);
                    this.mRemote.transact(31, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void prepareStylusHandwritingDelegation(IInputMethodClient iInputMethodClient, int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputMethodClient);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean acceptStylusHandwritingDelegation(IInputMethodClient iInputMethodClient, int i, String str, String str2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputMethodClient);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void acceptStylusHandwritingDelegationAsync(IInputMethodClient iInputMethodClient, int i, String str, String str2, int i2, IBooleanListener iBooleanListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputMethodClient);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iBooleanListener);
                    this.mRemote.transact(34, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isStylusHandwritingAvailableAsUser(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void addVirtualStylusIdForTestSession(IInputMethodClient iInputMethodClient) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputMethodClient);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void setStylusWindowIdleTimeoutForTest(IInputMethodClient iInputMethodClient, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputMethodClient);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public IImeTracker getImeTrackerService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImeTracker.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean minimizeSoftInput(IInputMethodClient iInputMethodClient, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputMethodClient);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void undoMinimizeSoftInput() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void forceHideSoftInput() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public int isAccessoryKeyboard() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean getWACOMPen() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isInputMethodShown() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean isCurrentInputMethodAsSamsungKeyboard() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public boolean getDexSettingsValue(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void setInputMethodSwitchDisable(IInputMethodClient iInputMethodClient, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputMethodClient);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void dismissAndShowAgainInputMethodPicker() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public int getCurrentFocusDisplayID() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public int getCurTokenDisplayId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void handleVoiceHWKey() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.view.IInputMethodManager
            public void showInputMethodPickerFromSystemWithUserId(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
