package com.android.internal.telecom;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import android.telecom.CallAttributes;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomAnalytics;
import com.android.internal.telecom.ICallEventCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ITelecomService extends IInterface {

    public static class Default implements ITelecomService {
        @Override // com.android.internal.telecom.ITelecomService
        public void acceptHandover(Uri uri, int i, PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void acceptRingingCall(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void acceptRingingCallWithVideoState(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void addCall(CallAttributes callAttributes, ICallEventCallback iCallEventCallback, String str, String str2) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void addNewIncomingCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle, String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void addNewIncomingConference(PhoneAccountHandle phoneAccountHandle, Bundle bundle, String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void addNewUnknownCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void addOrRemoveTestCallCompanionApp(String str, boolean z) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void cancelMissedCallsNotification(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public int cleanupOrphanPhoneAccounts() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void cleanupStuckCalls() throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void clearAccounts(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public Intent createLaunchEmergencyDialerIntent(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public Intent createManageBlockedNumbersIntent(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public TelecomAnalytics dumpCallAnalytics() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean enablePhoneAccount(PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean endCall(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public Uri getAdnUriForPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public ParceledListSlice<PhoneAccountHandle> getAllPhoneAccountHandles() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public ParceledListSlice<PhoneAccount> getAllPhoneAccounts() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public int getAllPhoneAccountsCount() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public ParceledListSlice<PhoneAccountHandle> getCallCapablePhoneAccounts(boolean z, String str, String str2, boolean z2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public int getCallState() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public int getCallStateUsingPackage(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public int getCurrentTtyMode(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public String getDefaultDialerPackage(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public String getDefaultDialerPackageForUser(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public PhoneAccountHandle getDefaultOutgoingPhoneAccount(String str, String str2, String str3) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public ComponentName getDefaultPhoneApp() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public String getLine1Number(PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public ParceledListSlice<PhoneAccountHandle> getOwnSelfManagedPhoneAccounts(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public PhoneAccount getPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public ParceledListSlice<PhoneAccountHandle> getPhoneAccountsForPackage(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public ParceledListSlice<PhoneAccountHandle> getPhoneAccountsSupportingScheme(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public ParceledListSlice<PhoneAccount> getRegisteredPhoneAccounts(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public ParceledListSlice<PhoneAccountHandle> getSelfManagedPhoneAccounts(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public PhoneAccountHandle getSimCallManager(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public PhoneAccountHandle getSimCallManagerForUser(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public String getSystemDialerPackage(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public PhoneAccountHandle getUserSelectedOutgoingPhoneAccount(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public String getVoiceMailNumber(PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void handleCallIntent(Intent intent, String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean handlePinMmi(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean handlePinMmiForPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean hasForegroundServiceDelegation(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean hasManageOngoingCallsPermission(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isInCall(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isInEmergencyCall() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isInManagedCall(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isInSelfManagedCall(String str, UserHandle userHandle, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isIncomingCallPermitted(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isNonUiInCallServiceBound(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isOutgoingCallPermitted(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isRinging(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isTtySupported(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isVoiceMailNumber(PhoneAccountHandle phoneAccountHandle, String str, String str2, String str3) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void placeCall(Uri uri, Bundle bundle, String str, String str2) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void registerPhoneAccount(PhoneAccount phoneAccount, String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void requestLogMark(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void resetCarMode() throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean setDefaultDialer(String str) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setMetricsTestMode(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setSystemDialer(ComponentName componentName) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestCallDiagnosticService(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestDefaultCallRedirectionApp(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestDefaultCallScreeningApp(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestDefaultDialer(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestEmergencyPhoneAccountPackageNameFilter(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestPhoneAcctSuggestionComponent(String str, UserHandle userHandle) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setUserSelectedOutgoingPhoneAccount(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void showInCallScreen(boolean z, String str, String str2) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void silenceRinger(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void startConference(List<Uri> list, Bundle bundle, String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void stopBlockSuppression() throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void unregisterPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void waitForAudioToUpdate(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void waitOnHandlers() throws RemoteException {
        }
    }

    void acceptHandover(Uri uri, int i, PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException;

    void acceptRingingCall(String str) throws RemoteException;

    void acceptRingingCallWithVideoState(String str, int i) throws RemoteException;

    void addCall(CallAttributes callAttributes, ICallEventCallback iCallEventCallback, String str, String str2) throws RemoteException;

    void addNewIncomingCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle, String str) throws RemoteException;

    void addNewIncomingConference(PhoneAccountHandle phoneAccountHandle, Bundle bundle, String str) throws RemoteException;

    void addNewUnknownCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle) throws RemoteException;

    void addOrRemoveTestCallCompanionApp(String str, boolean z) throws RemoteException;

    void cancelMissedCallsNotification(String str) throws RemoteException;

    int cleanupOrphanPhoneAccounts() throws RemoteException;

    void cleanupStuckCalls() throws RemoteException;

    void clearAccounts(String str) throws RemoteException;

    Intent createLaunchEmergencyDialerIntent(String str) throws RemoteException;

    Intent createManageBlockedNumbersIntent(String str) throws RemoteException;

    TelecomAnalytics dumpCallAnalytics() throws RemoteException;

    boolean enablePhoneAccount(PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException;

    boolean endCall(String str) throws RemoteException;

    Uri getAdnUriForPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException;

    ParceledListSlice<PhoneAccountHandle> getAllPhoneAccountHandles() throws RemoteException;

    ParceledListSlice<PhoneAccount> getAllPhoneAccounts() throws RemoteException;

    int getAllPhoneAccountsCount() throws RemoteException;

    ParceledListSlice<PhoneAccountHandle> getCallCapablePhoneAccounts(boolean z, String str, String str2, boolean z2) throws RemoteException;

    int getCallState() throws RemoteException;

    int getCallStateUsingPackage(String str, String str2) throws RemoteException;

    int getCurrentTtyMode(String str, String str2) throws RemoteException;

    String getDefaultDialerPackage(String str) throws RemoteException;

    String getDefaultDialerPackageForUser(int i) throws RemoteException;

    PhoneAccountHandle getDefaultOutgoingPhoneAccount(String str, String str2, String str3) throws RemoteException;

    ComponentName getDefaultPhoneApp() throws RemoteException;

    String getLine1Number(PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException;

    ParceledListSlice<PhoneAccountHandle> getOwnSelfManagedPhoneAccounts(String str, String str2) throws RemoteException;

    PhoneAccount getPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException;

    ParceledListSlice<PhoneAccountHandle> getPhoneAccountsForPackage(String str) throws RemoteException;

    ParceledListSlice<PhoneAccountHandle> getPhoneAccountsSupportingScheme(String str, String str2) throws RemoteException;

    ParceledListSlice<PhoneAccount> getRegisteredPhoneAccounts(String str, String str2) throws RemoteException;

    ParceledListSlice<PhoneAccountHandle> getSelfManagedPhoneAccounts(String str, String str2) throws RemoteException;

    PhoneAccountHandle getSimCallManager(int i, String str) throws RemoteException;

    PhoneAccountHandle getSimCallManagerForUser(int i, String str) throws RemoteException;

    String getSystemDialerPackage(String str) throws RemoteException;

    PhoneAccountHandle getUserSelectedOutgoingPhoneAccount(String str) throws RemoteException;

    String getVoiceMailNumber(PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException;

    void handleCallIntent(Intent intent, String str) throws RemoteException;

    boolean handlePinMmi(String str, String str2) throws RemoteException;

    boolean handlePinMmiForPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException;

    boolean hasForegroundServiceDelegation(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException;

    boolean hasManageOngoingCallsPermission(String str) throws RemoteException;

    boolean isInCall(String str, String str2) throws RemoteException;

    boolean isInEmergencyCall() throws RemoteException;

    boolean isInManagedCall(String str, String str2) throws RemoteException;

    boolean isInSelfManagedCall(String str, UserHandle userHandle, String str2) throws RemoteException;

    boolean isIncomingCallPermitted(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException;

    boolean isNonUiInCallServiceBound(String str) throws RemoteException;

    boolean isOutgoingCallPermitted(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException;

    boolean isRinging(String str) throws RemoteException;

    boolean isTtySupported(String str, String str2) throws RemoteException;

    boolean isVoiceMailNumber(PhoneAccountHandle phoneAccountHandle, String str, String str2, String str3) throws RemoteException;

    void placeCall(Uri uri, Bundle bundle, String str, String str2) throws RemoteException;

    void registerPhoneAccount(PhoneAccount phoneAccount, String str) throws RemoteException;

    void requestLogMark(String str) throws RemoteException;

    void resetCarMode() throws RemoteException;

    boolean setDefaultDialer(String str) throws RemoteException;

    void setMetricsTestMode(boolean z) throws RemoteException;

    void setSystemDialer(ComponentName componentName) throws RemoteException;

    void setTestCallDiagnosticService(String str) throws RemoteException;

    void setTestDefaultCallRedirectionApp(String str) throws RemoteException;

    void setTestDefaultCallScreeningApp(String str) throws RemoteException;

    void setTestDefaultDialer(String str) throws RemoteException;

    void setTestEmergencyPhoneAccountPackageNameFilter(String str) throws RemoteException;

    void setTestPhoneAcctSuggestionComponent(String str, UserHandle userHandle) throws RemoteException;

    void setUserSelectedOutgoingPhoneAccount(PhoneAccountHandle phoneAccountHandle) throws RemoteException;

    void showInCallScreen(boolean z, String str, String str2) throws RemoteException;

    void silenceRinger(String str) throws RemoteException;

    void startConference(List<Uri> list, Bundle bundle, String str) throws RemoteException;

    void stopBlockSuppression() throws RemoteException;

    void unregisterPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException;

    void waitForAudioToUpdate(boolean z) throws RemoteException;

    void waitOnHandlers() throws RemoteException;

    public static abstract class Stub extends Binder implements ITelecomService {
        public static final String DESCRIPTOR = "com.android.internal.telecom.ITelecomService";
        static final int TRANSACTION_acceptHandover = 57;
        static final int TRANSACTION_acceptRingingCall = 36;
        static final int TRANSACTION_acceptRingingCallWithVideoState = 37;
        static final int TRANSACTION_addCall = 74;
        static final int TRANSACTION_addNewIncomingCall = 44;
        static final int TRANSACTION_addNewIncomingConference = 45;
        static final int TRANSACTION_addNewUnknownCall = 46;
        static final int TRANSACTION_addOrRemoveTestCallCompanionApp = 69;
        static final int TRANSACTION_cancelMissedCallsNotification = 38;
        static final int TRANSACTION_cleanupOrphanPhoneAccounts = 62;
        static final int TRANSACTION_cleanupStuckCalls = 61;
        static final int TRANSACTION_clearAccounts = 19;
        static final int TRANSACTION_createLaunchEmergencyDialerIntent = 53;
        static final int TRANSACTION_createManageBlockedNumbersIntent = 52;
        static final int TRANSACTION_dumpCallAnalytics = 27;
        static final int TRANSACTION_enablePhoneAccount = 49;
        static final int TRANSACTION_endCall = 35;
        static final int TRANSACTION_getAdnUriForPhoneAccount = 41;
        static final int TRANSACTION_getAllPhoneAccountHandles = 14;
        static final int TRANSACTION_getAllPhoneAccounts = 13;
        static final int TRANSACTION_getAllPhoneAccountsCount = 12;
        static final int TRANSACTION_getCallCapablePhoneAccounts = 5;
        static final int TRANSACTION_getCallState = 33;
        static final int TRANSACTION_getCallStateUsingPackage = 34;
        static final int TRANSACTION_getCurrentTtyMode = 43;
        static final int TRANSACTION_getDefaultDialerPackage = 24;
        static final int TRANSACTION_getDefaultDialerPackageForUser = 25;
        static final int TRANSACTION_getDefaultOutgoingPhoneAccount = 2;
        static final int TRANSACTION_getDefaultPhoneApp = 23;
        static final int TRANSACTION_getLine1Number = 22;
        static final int TRANSACTION_getOwnSelfManagedPhoneAccounts = 7;
        static final int TRANSACTION_getPhoneAccount = 10;
        static final int TRANSACTION_getPhoneAccountsForPackage = 9;
        static final int TRANSACTION_getPhoneAccountsSupportingScheme = 8;
        static final int TRANSACTION_getRegisteredPhoneAccounts = 11;
        static final int TRANSACTION_getSelfManagedPhoneAccounts = 6;
        static final int TRANSACTION_getSimCallManager = 15;
        static final int TRANSACTION_getSimCallManagerForUser = 16;
        static final int TRANSACTION_getSystemDialerPackage = 26;
        static final int TRANSACTION_getUserSelectedOutgoingPhoneAccount = 3;
        static final int TRANSACTION_getVoiceMailNumber = 21;
        static final int TRANSACTION_handleCallIntent = 60;
        static final int TRANSACTION_handlePinMmi = 39;
        static final int TRANSACTION_handlePinMmiForPhoneAccount = 40;
        static final int TRANSACTION_hasForegroundServiceDelegation = 75;
        static final int TRANSACTION_hasManageOngoingCallsPermission = 30;
        static final int TRANSACTION_isInCall = 29;
        static final int TRANSACTION_isInEmergencyCall = 59;
        static final int TRANSACTION_isInManagedCall = 31;
        static final int TRANSACTION_isInSelfManagedCall = 73;
        static final int TRANSACTION_isIncomingCallPermitted = 54;
        static final int TRANSACTION_isNonUiInCallServiceBound = 63;
        static final int TRANSACTION_isOutgoingCallPermitted = 55;
        static final int TRANSACTION_isRinging = 32;
        static final int TRANSACTION_isTtySupported = 42;
        static final int TRANSACTION_isVoiceMailNumber = 20;
        static final int TRANSACTION_placeCall = 48;
        static final int TRANSACTION_registerPhoneAccount = 17;
        static final int TRANSACTION_requestLogMark = 66;
        static final int TRANSACTION_resetCarMode = 64;
        static final int TRANSACTION_setDefaultDialer = 50;
        static final int TRANSACTION_setMetricsTestMode = 76;
        static final int TRANSACTION_setSystemDialer = 70;
        static final int TRANSACTION_setTestCallDiagnosticService = 72;
        static final int TRANSACTION_setTestDefaultCallRedirectionApp = 65;
        static final int TRANSACTION_setTestDefaultCallScreeningApp = 68;
        static final int TRANSACTION_setTestDefaultDialer = 71;
        static final int TRANSACTION_setTestEmergencyPhoneAccountPackageNameFilter = 58;
        static final int TRANSACTION_setTestPhoneAcctSuggestionComponent = 67;
        static final int TRANSACTION_setUserSelectedOutgoingPhoneAccount = 4;
        static final int TRANSACTION_showInCallScreen = 1;
        static final int TRANSACTION_silenceRinger = 28;
        static final int TRANSACTION_startConference = 47;
        static final int TRANSACTION_stopBlockSuppression = 51;
        static final int TRANSACTION_unregisterPhoneAccount = 18;
        static final int TRANSACTION_waitForAudioToUpdate = 77;
        static final int TRANSACTION_waitOnHandlers = 56;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 76;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITelecomService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITelecomService)) {
                return (ITelecomService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "showInCallScreen";
                case 2:
                    return "getDefaultOutgoingPhoneAccount";
                case 3:
                    return "getUserSelectedOutgoingPhoneAccount";
                case 4:
                    return "setUserSelectedOutgoingPhoneAccount";
                case 5:
                    return "getCallCapablePhoneAccounts";
                case 6:
                    return "getSelfManagedPhoneAccounts";
                case 7:
                    return "getOwnSelfManagedPhoneAccounts";
                case 8:
                    return "getPhoneAccountsSupportingScheme";
                case 9:
                    return "getPhoneAccountsForPackage";
                case 10:
                    return "getPhoneAccount";
                case 11:
                    return "getRegisteredPhoneAccounts";
                case 12:
                    return "getAllPhoneAccountsCount";
                case 13:
                    return "getAllPhoneAccounts";
                case 14:
                    return "getAllPhoneAccountHandles";
                case 15:
                    return "getSimCallManager";
                case 16:
                    return "getSimCallManagerForUser";
                case 17:
                    return "registerPhoneAccount";
                case 18:
                    return "unregisterPhoneAccount";
                case 19:
                    return "clearAccounts";
                case 20:
                    return "isVoiceMailNumber";
                case 21:
                    return "getVoiceMailNumber";
                case 22:
                    return "getLine1Number";
                case 23:
                    return "getDefaultPhoneApp";
                case 24:
                    return "getDefaultDialerPackage";
                case 25:
                    return "getDefaultDialerPackageForUser";
                case 26:
                    return "getSystemDialerPackage";
                case 27:
                    return "dumpCallAnalytics";
                case 28:
                    return "silenceRinger";
                case 29:
                    return "isInCall";
                case 30:
                    return "hasManageOngoingCallsPermission";
                case 31:
                    return "isInManagedCall";
                case 32:
                    return "isRinging";
                case 33:
                    return "getCallState";
                case 34:
                    return "getCallStateUsingPackage";
                case 35:
                    return "endCall";
                case 36:
                    return "acceptRingingCall";
                case 37:
                    return "acceptRingingCallWithVideoState";
                case 38:
                    return "cancelMissedCallsNotification";
                case 39:
                    return "handlePinMmi";
                case 40:
                    return "handlePinMmiForPhoneAccount";
                case 41:
                    return "getAdnUriForPhoneAccount";
                case 42:
                    return "isTtySupported";
                case 43:
                    return "getCurrentTtyMode";
                case 44:
                    return "addNewIncomingCall";
                case 45:
                    return "addNewIncomingConference";
                case 46:
                    return "addNewUnknownCall";
                case 47:
                    return "startConference";
                case 48:
                    return "placeCall";
                case 49:
                    return "enablePhoneAccount";
                case 50:
                    return "setDefaultDialer";
                case 51:
                    return "stopBlockSuppression";
                case 52:
                    return "createManageBlockedNumbersIntent";
                case 53:
                    return "createLaunchEmergencyDialerIntent";
                case 54:
                    return "isIncomingCallPermitted";
                case 55:
                    return "isOutgoingCallPermitted";
                case 56:
                    return "waitOnHandlers";
                case 57:
                    return "acceptHandover";
                case 58:
                    return "setTestEmergencyPhoneAccountPackageNameFilter";
                case 59:
                    return "isInEmergencyCall";
                case 60:
                    return "handleCallIntent";
                case 61:
                    return "cleanupStuckCalls";
                case 62:
                    return "cleanupOrphanPhoneAccounts";
                case 63:
                    return "isNonUiInCallServiceBound";
                case 64:
                    return "resetCarMode";
                case 65:
                    return "setTestDefaultCallRedirectionApp";
                case 66:
                    return "requestLogMark";
                case 67:
                    return "setTestPhoneAcctSuggestionComponent";
                case 68:
                    return "setTestDefaultCallScreeningApp";
                case 69:
                    return "addOrRemoveTestCallCompanionApp";
                case 70:
                    return "setSystemDialer";
                case 71:
                    return "setTestDefaultDialer";
                case 72:
                    return "setTestCallDiagnosticService";
                case 73:
                    return "isInSelfManagedCall";
                case 74:
                    return "addCall";
                case 75:
                    return "hasForegroundServiceDelegation";
                case 76:
                    return "setMetricsTestMode";
                case 77:
                    return "waitForAudioToUpdate";
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
                    boolean z = parcel.readBoolean();
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showInCallScreen(z, string, string2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    PhoneAccountHandle defaultOutgoingPhoneAccount = getDefaultOutgoingPhoneAccount(string3, string4, string5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultOutgoingPhoneAccount, 1);
                    return true;
                case 3:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    PhoneAccountHandle userSelectedOutgoingPhoneAccount = getUserSelectedOutgoingPhoneAccount(string6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userSelectedOutgoingPhoneAccount, 1);
                    return true;
                case 4:
                    PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserSelectedOutgoingPhoneAccount(phoneAccountHandle);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean z2 = parcel.readBoolean();
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccountHandle> callCapablePhoneAccounts = getCallCapablePhoneAccounts(z2, string7, string8, z3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(callCapablePhoneAccounts, 1);
                    return true;
                case 6:
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccountHandle> selfManagedPhoneAccounts = getSelfManagedPhoneAccounts(string9, string10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(selfManagedPhoneAccounts, 1);
                    return true;
                case 7:
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccountHandle> ownSelfManagedPhoneAccounts = getOwnSelfManagedPhoneAccounts(string11, string12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ownSelfManagedPhoneAccounts, 1);
                    return true;
                case 8:
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccountHandle> phoneAccountsSupportingScheme = getPhoneAccountsSupportingScheme(string13, string14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(phoneAccountsSupportingScheme, 1);
                    return true;
                case 9:
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccountHandle> phoneAccountsForPackage = getPhoneAccountsForPackage(string15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(phoneAccountsForPackage, 1);
                    return true;
                case 10:
                    PhoneAccountHandle phoneAccountHandle2 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    PhoneAccount phoneAccount = getPhoneAccount(phoneAccountHandle2, string16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(phoneAccount, 1);
                    return true;
                case 11:
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccount> registeredPhoneAccounts = getRegisteredPhoneAccounts(string17, string18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registeredPhoneAccounts, 1);
                    return true;
                case 12:
                    int allPhoneAccountsCount = getAllPhoneAccountsCount();
                    parcel2.writeNoException();
                    parcel2.writeInt(allPhoneAccountsCount);
                    return true;
                case 13:
                    ParceledListSlice<PhoneAccount> allPhoneAccounts = getAllPhoneAccounts();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allPhoneAccounts, 1);
                    return true;
                case 14:
                    ParceledListSlice<PhoneAccountHandle> allPhoneAccountHandles = getAllPhoneAccountHandles();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allPhoneAccountHandles, 1);
                    return true;
                case 15:
                    int i3 = parcel.readInt();
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    PhoneAccountHandle simCallManager = getSimCallManager(i3, string19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(simCallManager, 1);
                    return true;
                case 16:
                    int i4 = parcel.readInt();
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    PhoneAccountHandle simCallManagerForUser = getSimCallManagerForUser(i4, string20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(simCallManagerForUser, 1);
                    return true;
                case 17:
                    PhoneAccount phoneAccount2 = (PhoneAccount) parcel.readTypedObject(PhoneAccount.CREATOR);
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerPhoneAccount(phoneAccount2, string21);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    PhoneAccountHandle phoneAccountHandle3 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterPhoneAccount(phoneAccountHandle3, string22);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearAccounts(string23);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    PhoneAccountHandle phoneAccountHandle4 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String string24 = parcel.readString();
                    String string25 = parcel.readString();
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsVoiceMailNumber = isVoiceMailNumber(phoneAccountHandle4, string24, string25, string26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsVoiceMailNumber);
                    return true;
                case 21:
                    PhoneAccountHandle phoneAccountHandle5 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String string27 = parcel.readString();
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String voiceMailNumber = getVoiceMailNumber(phoneAccountHandle5, string27, string28);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceMailNumber);
                    return true;
                case 22:
                    PhoneAccountHandle phoneAccountHandle6 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String string29 = parcel.readString();
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String line1Number = getLine1Number(phoneAccountHandle6, string29, string30);
                    parcel2.writeNoException();
                    parcel2.writeString(line1Number);
                    return true;
                case 23:
                    ComponentName defaultPhoneApp = getDefaultPhoneApp();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultPhoneApp, 1);
                    return true;
                case 24:
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String defaultDialerPackage = getDefaultDialerPackage(string31);
                    parcel2.writeNoException();
                    parcel2.writeString(defaultDialerPackage);
                    return true;
                case 25:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String defaultDialerPackageForUser = getDefaultDialerPackageForUser(i5);
                    parcel2.writeNoException();
                    parcel2.writeString(defaultDialerPackageForUser);
                    return true;
                case 26:
                    String string32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String systemDialerPackage = getSystemDialerPackage(string32);
                    parcel2.writeNoException();
                    parcel2.writeString(systemDialerPackage);
                    return true;
                case 27:
                    TelecomAnalytics telecomAnalyticsDumpCallAnalytics = dumpCallAnalytics();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(telecomAnalyticsDumpCallAnalytics, 1);
                    return true;
                case 28:
                    String string33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    silenceRinger(string33);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    String string34 = parcel.readString();
                    String string35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsInCall = isInCall(string34, string35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInCall);
                    return true;
                case 30:
                    String string36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasManageOngoingCallsPermission = hasManageOngoingCallsPermission(string36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasManageOngoingCallsPermission);
                    return true;
                case 31:
                    String string37 = parcel.readString();
                    String string38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsInManagedCall = isInManagedCall(string37, string38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInManagedCall);
                    return true;
                case 32:
                    String string39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsRinging = isRinging(string39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRinging);
                    return true;
                case 33:
                    int callState = getCallState();
                    parcel2.writeNoException();
                    parcel2.writeInt(callState);
                    return true;
                case 34:
                    String string40 = parcel.readString();
                    String string41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int callStateUsingPackage = getCallStateUsingPackage(string40, string41);
                    parcel2.writeNoException();
                    parcel2.writeInt(callStateUsingPackage);
                    return true;
                case 35:
                    String string42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zEndCall = endCall(string42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEndCall);
                    return true;
                case 36:
                    String string43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acceptRingingCall(string43);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    String string44 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acceptRingingCallWithVideoState(string44, i6);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    String string45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelMissedCallsNotification(string45);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    String string46 = parcel.readString();
                    String string47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHandlePinMmi = handlePinMmi(string46, string47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHandlePinMmi);
                    return true;
                case 40:
                    PhoneAccountHandle phoneAccountHandle7 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String string48 = parcel.readString();
                    String string49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHandlePinMmiForPhoneAccount = handlePinMmiForPhoneAccount(phoneAccountHandle7, string48, string49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHandlePinMmiForPhoneAccount);
                    return true;
                case 41:
                    PhoneAccountHandle phoneAccountHandle8 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String string50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Uri adnUriForPhoneAccount = getAdnUriForPhoneAccount(phoneAccountHandle8, string50);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(adnUriForPhoneAccount, 1);
                    return true;
                case 42:
                    String string51 = parcel.readString();
                    String string52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsTtySupported = isTtySupported(string51, string52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTtySupported);
                    return true;
                case 43:
                    String string53 = parcel.readString();
                    String string54 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int currentTtyMode = getCurrentTtyMode(string53, string54);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentTtyMode);
                    return true;
                case 44:
                    PhoneAccountHandle phoneAccountHandle9 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String string55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addNewIncomingCall(phoneAccountHandle9, bundle, string55);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    PhoneAccountHandle phoneAccountHandle10 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String string56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addNewIncomingConference(phoneAccountHandle10, bundle2, string56);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    PhoneAccountHandle phoneAccountHandle11 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addNewUnknownCall(phoneAccountHandle11, bundle3);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(Uri.CREATOR);
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String string57 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startConference(arrayListCreateTypedArrayList, bundle4, string57);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String string58 = parcel.readString();
                    String string59 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    placeCall(uri, bundle5, string58, string59);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    PhoneAccountHandle phoneAccountHandle12 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnablePhoneAccount = enablePhoneAccount(phoneAccountHandle12, z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnablePhoneAccount);
                    return true;
                case 50:
                    String string60 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean defaultDialer = setDefaultDialer(string60);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(defaultDialer);
                    return true;
                case 51:
                    stopBlockSuppression();
                    parcel2.writeNoException();
                    return true;
                case 52:
                    String string61 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Intent intentCreateManageBlockedNumbersIntent = createManageBlockedNumbersIntent(string61);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(intentCreateManageBlockedNumbersIntent, 1);
                    return true;
                case 53:
                    String string62 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Intent intentCreateLaunchEmergencyDialerIntent = createLaunchEmergencyDialerIntent(string62);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(intentCreateLaunchEmergencyDialerIntent, 1);
                    return true;
                case 54:
                    PhoneAccountHandle phoneAccountHandle13 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String string63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsIncomingCallPermitted = isIncomingCallPermitted(phoneAccountHandle13, string63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsIncomingCallPermitted);
                    return true;
                case 55:
                    PhoneAccountHandle phoneAccountHandle14 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String string64 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsOutgoingCallPermitted = isOutgoingCallPermitted(phoneAccountHandle14, string64);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOutgoingCallPermitted);
                    return true;
                case 56:
                    waitOnHandlers();
                    parcel2.writeNoException();
                    return true;
                case 57:
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i7 = parcel.readInt();
                    PhoneAccountHandle phoneAccountHandle15 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String string65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acceptHandover(uri2, i7, phoneAccountHandle15, string65);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    String string66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTestEmergencyPhoneAccountPackageNameFilter(string66);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    boolean zIsInEmergencyCall = isInEmergencyCall();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInEmergencyCall);
                    return true;
                case 60:
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String string67 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    handleCallIntent(intent, string67);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    cleanupStuckCalls();
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int iCleanupOrphanPhoneAccounts = cleanupOrphanPhoneAccounts();
                    parcel2.writeNoException();
                    parcel2.writeInt(iCleanupOrphanPhoneAccounts);
                    return true;
                case 63:
                    String string68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsNonUiInCallServiceBound = isNonUiInCallServiceBound(string68);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNonUiInCallServiceBound);
                    return true;
                case 64:
                    resetCarMode();
                    parcel2.writeNoException();
                    return true;
                case 65:
                    String string69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTestDefaultCallRedirectionApp(string69);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    String string70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestLogMark(string70);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    String string71 = parcel.readString();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTestPhoneAcctSuggestionComponent(string71, userHandle);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    String string72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTestDefaultCallScreeningApp(string72);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    String string73 = parcel.readString();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    addOrRemoveTestCallCompanionApp(string73, z5);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSystemDialer(componentName);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    String string74 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTestDefaultDialer(string74);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    String string75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTestCallDiagnosticService(string75);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    String string76 = parcel.readString();
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String string77 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsInSelfManagedCall = isInSelfManagedCall(string76, userHandle2, string77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInSelfManagedCall);
                    return true;
                case 74:
                    CallAttributes callAttributes = (CallAttributes) parcel.readTypedObject(CallAttributes.CREATOR);
                    ICallEventCallback iCallEventCallbackAsInterface = ICallEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string78 = parcel.readString();
                    String string79 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addCall(callAttributes, iCallEventCallbackAsInterface, string78, string79);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    PhoneAccountHandle phoneAccountHandle16 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String string80 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasForegroundServiceDelegation = hasForegroundServiceDelegation(phoneAccountHandle16, string80);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasForegroundServiceDelegation);
                    return true;
                case 76:
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMetricsTestMode(z6);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    waitForAudioToUpdate(z7);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITelecomService {
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

            @Override // com.android.internal.telecom.ITelecomService
            public void showInCallScreen(boolean z, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public PhoneAccountHandle getDefaultOutgoingPhoneAccount(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PhoneAccountHandle) parcelObtain2.readTypedObject(PhoneAccountHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public PhoneAccountHandle getUserSelectedOutgoingPhoneAccount(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PhoneAccountHandle) parcelObtain2.readTypedObject(PhoneAccountHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setUserSelectedOutgoingPhoneAccount(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getCallCapablePhoneAccounts(boolean z, String str, String str2, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getSelfManagedPhoneAccounts(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getOwnSelfManagedPhoneAccounts(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getPhoneAccountsSupportingScheme(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getPhoneAccountsForPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public PhoneAccount getPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PhoneAccount) parcelObtain2.readTypedObject(PhoneAccount.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccount> getRegisteredPhoneAccounts(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int getAllPhoneAccountsCount() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccount> getAllPhoneAccounts() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getAllPhoneAccountHandles() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public PhoneAccountHandle getSimCallManager(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PhoneAccountHandle) parcelObtain2.readTypedObject(PhoneAccountHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public PhoneAccountHandle getSimCallManagerForUser(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PhoneAccountHandle) parcelObtain2.readTypedObject(PhoneAccountHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void registerPhoneAccount(PhoneAccount phoneAccount, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccount, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void unregisterPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void clearAccounts(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isVoiceMailNumber(PhoneAccountHandle phoneAccountHandle, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public String getVoiceMailNumber(PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public String getLine1Number(PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ComponentName getDefaultPhoneApp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public String getDefaultDialerPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public String getDefaultDialerPackageForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public String getSystemDialerPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public TelecomAnalytics dumpCallAnalytics() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (TelecomAnalytics) parcelObtain2.readTypedObject(TelecomAnalytics.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void silenceRinger(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isInCall(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean hasManageOngoingCallsPermission(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isInManagedCall(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isRinging(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int getCallState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int getCallStateUsingPackage(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean endCall(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void acceptRingingCall(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void acceptRingingCallWithVideoState(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void cancelMissedCallsNotification(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean handlePinMmi(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean handlePinMmiForPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public Uri getAdnUriForPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Uri) parcelObtain2.readTypedObject(Uri.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isTtySupported(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int getCurrentTtyMode(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addNewIncomingCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addNewIncomingConference(PhoneAccountHandle phoneAccountHandle, Bundle bundle, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addNewUnknownCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void startConference(List<Uri> list, Bundle bundle, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void placeCall(Uri uri, Bundle bundle, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean enablePhoneAccount(PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean setDefaultDialer(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void stopBlockSuppression() throws RemoteException {
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

            @Override // com.android.internal.telecom.ITelecomService
            public Intent createManageBlockedNumbersIntent(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Intent) parcelObtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public Intent createLaunchEmergencyDialerIntent(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Intent) parcelObtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isIncomingCallPermitted(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isOutgoingCallPermitted(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void waitOnHandlers() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void acceptHandover(Uri uri, int i, PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestEmergencyPhoneAccountPackageNameFilter(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isInEmergencyCall() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void handleCallIntent(Intent intent, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void cleanupStuckCalls() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int cleanupOrphanPhoneAccounts() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isNonUiInCallServiceBound(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void resetCarMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestDefaultCallRedirectionApp(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void requestLogMark(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestPhoneAcctSuggestionComponent(String str, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestDefaultCallScreeningApp(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addOrRemoveTestCallCompanionApp(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setSystemDialer(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestDefaultDialer(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestCallDiagnosticService(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isInSelfManagedCall(String str, UserHandle userHandle, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addCall(CallAttributes callAttributes, ICallEventCallback iCallEventCallback, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(callAttributes, 0);
                    parcelObtain.writeStrongInterface(iCallEventCallback);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean hasForegroundServiceDelegation(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setMetricsTestMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void waitForAudioToUpdate(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
