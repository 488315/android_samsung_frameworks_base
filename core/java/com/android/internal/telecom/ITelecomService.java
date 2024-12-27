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

import java.util.List;

/* loaded from: classes5.dex */
public interface ITelecomService extends IInterface {
    void acceptHandover(Uri uri, int i, PhoneAccountHandle phoneAccountHandle, String str)
            throws RemoteException;

    void acceptRingingCall(String str) throws RemoteException;

    void acceptRingingCallWithVideoState(String str, int i) throws RemoteException;

    void addCall(
            CallAttributes callAttributes,
            ICallEventCallback iCallEventCallback,
            String str,
            String str2)
            throws RemoteException;

    void addNewIncomingCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle, String str)
            throws RemoteException;

    void addNewIncomingConference(PhoneAccountHandle phoneAccountHandle, Bundle bundle, String str)
            throws RemoteException;

    void addNewUnknownCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle)
            throws RemoteException;

    void addOrRemoveTestCallCompanionApp(String str, boolean z) throws RemoteException;

    void cancelMissedCallsNotification(String str) throws RemoteException;

    int cleanupOrphanPhoneAccounts() throws RemoteException;

    void cleanupStuckCalls() throws RemoteException;

    void clearAccounts(String str) throws RemoteException;

    Intent createLaunchEmergencyDialerIntent(String str) throws RemoteException;

    Intent createManageBlockedNumbersIntent(String str) throws RemoteException;

    TelecomAnalytics dumpCallAnalytics() throws RemoteException;

    boolean enablePhoneAccount(PhoneAccountHandle phoneAccountHandle, boolean z)
            throws RemoteException;

    boolean endCall(String str) throws RemoteException;

    Uri getAdnUriForPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str)
            throws RemoteException;

    ParceledListSlice<PhoneAccountHandle> getAllPhoneAccountHandles() throws RemoteException;

    ParceledListSlice<PhoneAccount> getAllPhoneAccounts() throws RemoteException;

    int getAllPhoneAccountsCount() throws RemoteException;

    ParceledListSlice<PhoneAccountHandle> getCallCapablePhoneAccounts(
            boolean z, String str, String str2, boolean z2) throws RemoteException;

    int getCallState() throws RemoteException;

    int getCallStateUsingPackage(String str, String str2) throws RemoteException;

    int getCurrentTtyMode(String str, String str2) throws RemoteException;

    String getDefaultDialerPackage(String str) throws RemoteException;

    String getDefaultDialerPackageForUser(int i) throws RemoteException;

    PhoneAccountHandle getDefaultOutgoingPhoneAccount(String str, String str2, String str3)
            throws RemoteException;

    ComponentName getDefaultPhoneApp() throws RemoteException;

    String getLine1Number(PhoneAccountHandle phoneAccountHandle, String str, String str2)
            throws RemoteException;

    ParceledListSlice<PhoneAccountHandle> getOwnSelfManagedPhoneAccounts(String str, String str2)
            throws RemoteException;

    PhoneAccount getPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str)
            throws RemoteException;

    ParceledListSlice<PhoneAccountHandle> getPhoneAccountsForPackage(String str)
            throws RemoteException;

    ParceledListSlice<PhoneAccountHandle> getPhoneAccountsSupportingScheme(String str, String str2)
            throws RemoteException;

    ParceledListSlice<PhoneAccount> getRegisteredPhoneAccounts(String str, String str2)
            throws RemoteException;

    ParceledListSlice<PhoneAccountHandle> getSelfManagedPhoneAccounts(String str, String str2)
            throws RemoteException;

    PhoneAccountHandle getSimCallManager(int i, String str) throws RemoteException;

    PhoneAccountHandle getSimCallManagerForUser(int i, String str) throws RemoteException;

    String getSystemDialerPackage(String str) throws RemoteException;

    PhoneAccountHandle getUserSelectedOutgoingPhoneAccount(String str) throws RemoteException;

    String getVoiceMailNumber(PhoneAccountHandle phoneAccountHandle, String str, String str2)
            throws RemoteException;

    void handleCallIntent(Intent intent, String str) throws RemoteException;

    boolean handlePinMmi(String str, String str2) throws RemoteException;

    boolean handlePinMmiForPhoneAccount(
            PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException;

    boolean hasManageOngoingCallsPermission(String str) throws RemoteException;

    boolean isInCall(String str, String str2) throws RemoteException;

    boolean isInEmergencyCall() throws RemoteException;

    boolean isInManagedCall(String str, String str2) throws RemoteException;

    boolean isInSelfManagedCall(String str, UserHandle userHandle, String str2)
            throws RemoteException;

    boolean isIncomingCallPermitted(PhoneAccountHandle phoneAccountHandle, String str)
            throws RemoteException;

    boolean isNonUiInCallServiceBound(String str) throws RemoteException;

    boolean isOutgoingCallPermitted(PhoneAccountHandle phoneAccountHandle, String str)
            throws RemoteException;

    boolean isRinging(String str) throws RemoteException;

    boolean isTtySupported(String str, String str2) throws RemoteException;

    boolean isVoiceMailNumber(
            PhoneAccountHandle phoneAccountHandle, String str, String str2, String str3)
            throws RemoteException;

    void placeCall(Uri uri, Bundle bundle, String str, String str2) throws RemoteException;

    void registerPhoneAccount(PhoneAccount phoneAccount, String str) throws RemoteException;

    void requestLogMark(String str) throws RemoteException;

    void resetCarMode() throws RemoteException;

    boolean setDefaultDialer(String str) throws RemoteException;

    void setSystemDialer(ComponentName componentName) throws RemoteException;

    void setTestCallDiagnosticService(String str) throws RemoteException;

    void setTestDefaultCallRedirectionApp(String str) throws RemoteException;

    void setTestDefaultCallScreeningApp(String str) throws RemoteException;

    void setTestDefaultDialer(String str) throws RemoteException;

    void setTestEmergencyPhoneAccountPackageNameFilter(String str) throws RemoteException;

    void setTestPhoneAcctSuggestionComponent(String str) throws RemoteException;

    void setUserSelectedOutgoingPhoneAccount(PhoneAccountHandle phoneAccountHandle)
            throws RemoteException;

    void showInCallScreen(boolean z, String str, String str2) throws RemoteException;

    void silenceRinger(String str) throws RemoteException;

    void startConference(List<Uri> list, Bundle bundle, String str) throws RemoteException;

    void stopBlockSuppression() throws RemoteException;

    void unregisterPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str)
            throws RemoteException;

    void waitOnHandlers() throws RemoteException;

    public static class Default implements ITelecomService {
        @Override // com.android.internal.telecom.ITelecomService
        public void showInCallScreen(
                boolean showDialpad, String callingPackage, String callingFeatureId)
                throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public PhoneAccountHandle getDefaultOutgoingPhoneAccount(
                String uriScheme, String callingPackage, String callingFeatureId)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public PhoneAccountHandle getUserSelectedOutgoingPhoneAccount(String callingPackage)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void setUserSelectedOutgoingPhoneAccount(PhoneAccountHandle account)
                throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public ParceledListSlice<PhoneAccountHandle> getCallCapablePhoneAccounts(
                boolean includeDisabledAccounts,
                String callingPackage,
                String callingFeatureId,
                boolean acrossProfiles)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public ParceledListSlice<PhoneAccountHandle> getSelfManagedPhoneAccounts(
                String callingPackage, String callingFeatureId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public ParceledListSlice<PhoneAccountHandle> getOwnSelfManagedPhoneAccounts(
                String callingPackage, String callingFeatureId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public ParceledListSlice<PhoneAccountHandle> getPhoneAccountsSupportingScheme(
                String uriScheme, String callingPackage) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public ParceledListSlice<PhoneAccountHandle> getPhoneAccountsForPackage(String packageName)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public PhoneAccount getPhoneAccount(PhoneAccountHandle account, String callingPackage)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public ParceledListSlice<PhoneAccount> getRegisteredPhoneAccounts(
                String callingPackage, String callingFeatureId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public int getAllPhoneAccountsCount() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public ParceledListSlice<PhoneAccount> getAllPhoneAccounts() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public ParceledListSlice<PhoneAccountHandle> getAllPhoneAccountHandles()
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public PhoneAccountHandle getSimCallManager(int subId, String callingPackage)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public PhoneAccountHandle getSimCallManagerForUser(int userId, String callingPackage)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void registerPhoneAccount(PhoneAccount metadata, String callingPackage)
                throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void unregisterPhoneAccount(PhoneAccountHandle account, String callingPackage)
                throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void clearAccounts(String packageName) throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isVoiceMailNumber(
                PhoneAccountHandle accountHandle,
                String number,
                String callingPackage,
                String callingFeatureId)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public String getVoiceMailNumber(
                PhoneAccountHandle accountHandle, String callingPackage, String callingFeatureId)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public String getLine1Number(
                PhoneAccountHandle accountHandle, String callingPackage, String callingFeatureId)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public ComponentName getDefaultPhoneApp() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public String getDefaultDialerPackage(String callingPackage) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public String getDefaultDialerPackageForUser(int userId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public String getSystemDialerPackage(String callingPackage) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public TelecomAnalytics dumpCallAnalytics() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void silenceRinger(String callingPackage) throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isInCall(String callingPackage, String callingFeatureId)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean hasManageOngoingCallsPermission(String callingPackage)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isInManagedCall(String callingPackage, String callingFeatureId)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isRinging(String callingPackage) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public int getCallState() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public int getCallStateUsingPackage(String callingPackage, String callingFeatureId)
                throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean endCall(String callingPackage) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void acceptRingingCall(String callingPackage) throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void acceptRingingCallWithVideoState(String callingPackage, int videoState)
                throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void cancelMissedCallsNotification(String callingPackage) throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public boolean handlePinMmi(String dialString, String callingPackage)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean handlePinMmiForPhoneAccount(
                PhoneAccountHandle accountHandle, String dialString, String callingPackage)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public Uri getAdnUriForPhoneAccount(PhoneAccountHandle accountHandle, String callingPackage)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isTtySupported(String callingPackage, String callingFeatureId)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public int getCurrentTtyMode(String callingPackage, String callingFeatureId)
                throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void addNewIncomingCall(
                PhoneAccountHandle phoneAccount, Bundle extras, String callingPackage)
                throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void addNewIncomingConference(
                PhoneAccountHandle phoneAccount, Bundle extras, String callingPackage)
                throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void addNewUnknownCall(PhoneAccountHandle phoneAccount, Bundle extras)
                throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void startConference(List<Uri> participants, Bundle extras, String callingPackage)
                throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void placeCall(
                Uri handle, Bundle extras, String callingPackage, String callingFeatureId)
                throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public boolean enablePhoneAccount(PhoneAccountHandle accountHandle, boolean isEnabled)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean setDefaultDialer(String packageName) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void stopBlockSuppression() throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public Intent createManageBlockedNumbersIntent(String callingPackage)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public Intent createLaunchEmergencyDialerIntent(String number) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isIncomingCallPermitted(
                PhoneAccountHandle phoneAccountHandle, String callingPackage)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isOutgoingCallPermitted(
                PhoneAccountHandle phoneAccountHandle, String callingPackage)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void waitOnHandlers() throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void acceptHandover(
                Uri srcAddr, int videoState, PhoneAccountHandle destAcct, String callingPackage)
                throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestEmergencyPhoneAccountPackageNameFilter(String packageName)
                throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isInEmergencyCall() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void handleCallIntent(Intent intent, String callingPackageProxy)
                throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void cleanupStuckCalls() throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public int cleanupOrphanPhoneAccounts() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isNonUiInCallServiceBound(String packageName) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void resetCarMode() throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestDefaultCallRedirectionApp(String packageName) throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void requestLogMark(String message) throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestPhoneAcctSuggestionComponent(String flattenedComponentName)
                throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestDefaultCallScreeningApp(String packageName) throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void addOrRemoveTestCallCompanionApp(String packageName, boolean isAdded)
                throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void setSystemDialer(ComponentName testComponentName) throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestDefaultDialer(String packageName) throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public void setTestCallDiagnosticService(String packageName) throws RemoteException {}

        @Override // com.android.internal.telecom.ITelecomService
        public boolean isInSelfManagedCall(
                String packageName, UserHandle userHandle, String callingPackage)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.telecom.ITelecomService
        public void addCall(
                CallAttributes callAttributes,
                ICallEventCallback callback,
                String callId,
                String callingPackage)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ITelecomService {
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
        static final int TRANSACTION_waitOnHandlers = 56;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITelecomService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ITelecomService)) {
                return (ITelecomService) iin;
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
                    boolean _arg0 = data.readBoolean();
                    String _arg1 = data.readString();
                    String _arg2 = data.readString();
                    data.enforceNoDataAvail();
                    showInCallScreen(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    String _arg12 = data.readString();
                    String _arg22 = data.readString();
                    data.enforceNoDataAvail();
                    PhoneAccountHandle _result =
                            getDefaultOutgoingPhoneAccount(_arg02, _arg12, _arg22);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    data.enforceNoDataAvail();
                    PhoneAccountHandle _result2 = getUserSelectedOutgoingPhoneAccount(_arg03);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 4:
                    PhoneAccountHandle _arg04 =
                            (PhoneAccountHandle) data.readTypedObject(PhoneAccountHandle.CREATOR);
                    data.enforceNoDataAvail();
                    setUserSelectedOutgoingPhoneAccount(_arg04);
                    reply.writeNoException();
                    return true;
                case 5:
                    boolean _arg05 = data.readBoolean();
                    String _arg13 = data.readString();
                    String _arg23 = data.readString();
                    boolean _arg3 = data.readBoolean();
                    data.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccountHandle> _result3 =
                            getCallCapablePhoneAccounts(_arg05, _arg13, _arg23, _arg3);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 6:
                    String _arg06 = data.readString();
                    String _arg14 = data.readString();
                    data.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccountHandle> _result4 =
                            getSelfManagedPhoneAccounts(_arg06, _arg14);
                    reply.writeNoException();
                    reply.writeTypedObject(_result4, 1);
                    return true;
                case 7:
                    String _arg07 = data.readString();
                    String _arg15 = data.readString();
                    data.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccountHandle> _result5 =
                            getOwnSelfManagedPhoneAccounts(_arg07, _arg15);
                    reply.writeNoException();
                    reply.writeTypedObject(_result5, 1);
                    return true;
                case 8:
                    String _arg08 = data.readString();
                    String _arg16 = data.readString();
                    data.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccountHandle> _result6 =
                            getPhoneAccountsSupportingScheme(_arg08, _arg16);
                    reply.writeNoException();
                    reply.writeTypedObject(_result6, 1);
                    return true;
                case 9:
                    String _arg09 = data.readString();
                    data.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccountHandle> _result7 =
                            getPhoneAccountsForPackage(_arg09);
                    reply.writeNoException();
                    reply.writeTypedObject(_result7, 1);
                    return true;
                case 10:
                    PhoneAccountHandle _arg010 =
                            (PhoneAccountHandle) data.readTypedObject(PhoneAccountHandle.CREATOR);
                    String _arg17 = data.readString();
                    data.enforceNoDataAvail();
                    PhoneAccount _result8 = getPhoneAccount(_arg010, _arg17);
                    reply.writeNoException();
                    reply.writeTypedObject(_result8, 1);
                    return true;
                case 11:
                    String _arg011 = data.readString();
                    String _arg18 = data.readString();
                    data.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccount> _result9 =
                            getRegisteredPhoneAccounts(_arg011, _arg18);
                    reply.writeNoException();
                    reply.writeTypedObject(_result9, 1);
                    return true;
                case 12:
                    int _result10 = getAllPhoneAccountsCount();
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 13:
                    ParceledListSlice<PhoneAccount> _result11 = getAllPhoneAccounts();
                    reply.writeNoException();
                    reply.writeTypedObject(_result11, 1);
                    return true;
                case 14:
                    ParceledListSlice<PhoneAccountHandle> _result12 = getAllPhoneAccountHandles();
                    reply.writeNoException();
                    reply.writeTypedObject(_result12, 1);
                    return true;
                case 15:
                    int _arg012 = data.readInt();
                    String _arg19 = data.readString();
                    data.enforceNoDataAvail();
                    PhoneAccountHandle _result13 = getSimCallManager(_arg012, _arg19);
                    reply.writeNoException();
                    reply.writeTypedObject(_result13, 1);
                    return true;
                case 16:
                    int _arg013 = data.readInt();
                    String _arg110 = data.readString();
                    data.enforceNoDataAvail();
                    PhoneAccountHandle _result14 = getSimCallManagerForUser(_arg013, _arg110);
                    reply.writeNoException();
                    reply.writeTypedObject(_result14, 1);
                    return true;
                case 17:
                    PhoneAccount _arg014 =
                            (PhoneAccount) data.readTypedObject(PhoneAccount.CREATOR);
                    String _arg111 = data.readString();
                    data.enforceNoDataAvail();
                    registerPhoneAccount(_arg014, _arg111);
                    reply.writeNoException();
                    return true;
                case 18:
                    PhoneAccountHandle _arg015 =
                            (PhoneAccountHandle) data.readTypedObject(PhoneAccountHandle.CREATOR);
                    String _arg112 = data.readString();
                    data.enforceNoDataAvail();
                    unregisterPhoneAccount(_arg015, _arg112);
                    reply.writeNoException();
                    return true;
                case 19:
                    String _arg016 = data.readString();
                    data.enforceNoDataAvail();
                    clearAccounts(_arg016);
                    reply.writeNoException();
                    return true;
                case 20:
                    PhoneAccountHandle _arg017 =
                            (PhoneAccountHandle) data.readTypedObject(PhoneAccountHandle.CREATOR);
                    String _arg113 = data.readString();
                    String _arg24 = data.readString();
                    String _arg32 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result15 = isVoiceMailNumber(_arg017, _arg113, _arg24, _arg32);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 21:
                    PhoneAccountHandle _arg018 =
                            (PhoneAccountHandle) data.readTypedObject(PhoneAccountHandle.CREATOR);
                    String _arg114 = data.readString();
                    String _arg25 = data.readString();
                    data.enforceNoDataAvail();
                    String _result16 = getVoiceMailNumber(_arg018, _arg114, _arg25);
                    reply.writeNoException();
                    reply.writeString(_result16);
                    return true;
                case 22:
                    PhoneAccountHandle _arg019 =
                            (PhoneAccountHandle) data.readTypedObject(PhoneAccountHandle.CREATOR);
                    String _arg115 = data.readString();
                    String _arg26 = data.readString();
                    data.enforceNoDataAvail();
                    String _result17 = getLine1Number(_arg019, _arg115, _arg26);
                    reply.writeNoException();
                    reply.writeString(_result17);
                    return true;
                case 23:
                    ComponentName _result18 = getDefaultPhoneApp();
                    reply.writeNoException();
                    reply.writeTypedObject(_result18, 1);
                    return true;
                case 24:
                    String _arg020 = data.readString();
                    data.enforceNoDataAvail();
                    String _result19 = getDefaultDialerPackage(_arg020);
                    reply.writeNoException();
                    reply.writeString(_result19);
                    return true;
                case 25:
                    int _arg021 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result20 = getDefaultDialerPackageForUser(_arg021);
                    reply.writeNoException();
                    reply.writeString(_result20);
                    return true;
                case 26:
                    String _arg022 = data.readString();
                    data.enforceNoDataAvail();
                    String _result21 = getSystemDialerPackage(_arg022);
                    reply.writeNoException();
                    reply.writeString(_result21);
                    return true;
                case 27:
                    TelecomAnalytics _result22 = dumpCallAnalytics();
                    reply.writeNoException();
                    reply.writeTypedObject(_result22, 1);
                    return true;
                case 28:
                    String _arg023 = data.readString();
                    data.enforceNoDataAvail();
                    silenceRinger(_arg023);
                    reply.writeNoException();
                    return true;
                case 29:
                    String _arg024 = data.readString();
                    String _arg116 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result23 = isInCall(_arg024, _arg116);
                    reply.writeNoException();
                    reply.writeBoolean(_result23);
                    return true;
                case 30:
                    String _arg025 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result24 = hasManageOngoingCallsPermission(_arg025);
                    reply.writeNoException();
                    reply.writeBoolean(_result24);
                    return true;
                case 31:
                    String _arg026 = data.readString();
                    String _arg117 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result25 = isInManagedCall(_arg026, _arg117);
                    reply.writeNoException();
                    reply.writeBoolean(_result25);
                    return true;
                case 32:
                    String _arg027 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result26 = isRinging(_arg027);
                    reply.writeNoException();
                    reply.writeBoolean(_result26);
                    return true;
                case 33:
                    int _result27 = getCallState();
                    reply.writeNoException();
                    reply.writeInt(_result27);
                    return true;
                case 34:
                    String _arg028 = data.readString();
                    String _arg118 = data.readString();
                    data.enforceNoDataAvail();
                    int _result28 = getCallStateUsingPackage(_arg028, _arg118);
                    reply.writeNoException();
                    reply.writeInt(_result28);
                    return true;
                case 35:
                    String _arg029 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result29 = endCall(_arg029);
                    reply.writeNoException();
                    reply.writeBoolean(_result29);
                    return true;
                case 36:
                    String _arg030 = data.readString();
                    data.enforceNoDataAvail();
                    acceptRingingCall(_arg030);
                    reply.writeNoException();
                    return true;
                case 37:
                    String _arg031 = data.readString();
                    int _arg119 = data.readInt();
                    data.enforceNoDataAvail();
                    acceptRingingCallWithVideoState(_arg031, _arg119);
                    reply.writeNoException();
                    return true;
                case 38:
                    String _arg032 = data.readString();
                    data.enforceNoDataAvail();
                    cancelMissedCallsNotification(_arg032);
                    reply.writeNoException();
                    return true;
                case 39:
                    String _arg033 = data.readString();
                    String _arg120 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result30 = handlePinMmi(_arg033, _arg120);
                    reply.writeNoException();
                    reply.writeBoolean(_result30);
                    return true;
                case 40:
                    PhoneAccountHandle _arg034 =
                            (PhoneAccountHandle) data.readTypedObject(PhoneAccountHandle.CREATOR);
                    String _arg121 = data.readString();
                    String _arg27 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result31 = handlePinMmiForPhoneAccount(_arg034, _arg121, _arg27);
                    reply.writeNoException();
                    reply.writeBoolean(_result31);
                    return true;
                case 41:
                    PhoneAccountHandle _arg035 =
                            (PhoneAccountHandle) data.readTypedObject(PhoneAccountHandle.CREATOR);
                    String _arg122 = data.readString();
                    data.enforceNoDataAvail();
                    Uri _result32 = getAdnUriForPhoneAccount(_arg035, _arg122);
                    reply.writeNoException();
                    reply.writeTypedObject(_result32, 1);
                    return true;
                case 42:
                    String _arg036 = data.readString();
                    String _arg123 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result33 = isTtySupported(_arg036, _arg123);
                    reply.writeNoException();
                    reply.writeBoolean(_result33);
                    return true;
                case 43:
                    String _arg037 = data.readString();
                    String _arg124 = data.readString();
                    data.enforceNoDataAvail();
                    int _result34 = getCurrentTtyMode(_arg037, _arg124);
                    reply.writeNoException();
                    reply.writeInt(_result34);
                    return true;
                case 44:
                    PhoneAccountHandle _arg038 =
                            (PhoneAccountHandle) data.readTypedObject(PhoneAccountHandle.CREATOR);
                    Bundle _arg125 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    String _arg28 = data.readString();
                    data.enforceNoDataAvail();
                    addNewIncomingCall(_arg038, _arg125, _arg28);
                    reply.writeNoException();
                    return true;
                case 45:
                    PhoneAccountHandle _arg039 =
                            (PhoneAccountHandle) data.readTypedObject(PhoneAccountHandle.CREATOR);
                    Bundle _arg126 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    String _arg29 = data.readString();
                    data.enforceNoDataAvail();
                    addNewIncomingConference(_arg039, _arg126, _arg29);
                    reply.writeNoException();
                    return true;
                case 46:
                    PhoneAccountHandle _arg040 =
                            (PhoneAccountHandle) data.readTypedObject(PhoneAccountHandle.CREATOR);
                    Bundle _arg127 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    addNewUnknownCall(_arg040, _arg127);
                    reply.writeNoException();
                    return true;
                case 47:
                    List<Uri> _arg041 = data.createTypedArrayList(Uri.CREATOR);
                    Bundle _arg128 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    String _arg210 = data.readString();
                    data.enforceNoDataAvail();
                    startConference(_arg041, _arg128, _arg210);
                    reply.writeNoException();
                    return true;
                case 48:
                    Uri _arg042 = (Uri) data.readTypedObject(Uri.CREATOR);
                    Bundle _arg129 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    String _arg211 = data.readString();
                    String _arg33 = data.readString();
                    data.enforceNoDataAvail();
                    placeCall(_arg042, _arg129, _arg211, _arg33);
                    reply.writeNoException();
                    return true;
                case 49:
                    PhoneAccountHandle _arg043 =
                            (PhoneAccountHandle) data.readTypedObject(PhoneAccountHandle.CREATOR);
                    boolean _arg130 = data.readBoolean();
                    data.enforceNoDataAvail();
                    boolean _result35 = enablePhoneAccount(_arg043, _arg130);
                    reply.writeNoException();
                    reply.writeBoolean(_result35);
                    return true;
                case 50:
                    String _arg044 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result36 = setDefaultDialer(_arg044);
                    reply.writeNoException();
                    reply.writeBoolean(_result36);
                    return true;
                case 51:
                    stopBlockSuppression();
                    reply.writeNoException();
                    return true;
                case 52:
                    String _arg045 = data.readString();
                    data.enforceNoDataAvail();
                    Intent _result37 = createManageBlockedNumbersIntent(_arg045);
                    reply.writeNoException();
                    reply.writeTypedObject(_result37, 1);
                    return true;
                case 53:
                    String _arg046 = data.readString();
                    data.enforceNoDataAvail();
                    Intent _result38 = createLaunchEmergencyDialerIntent(_arg046);
                    reply.writeNoException();
                    reply.writeTypedObject(_result38, 1);
                    return true;
                case 54:
                    PhoneAccountHandle _arg047 =
                            (PhoneAccountHandle) data.readTypedObject(PhoneAccountHandle.CREATOR);
                    String _arg131 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result39 = isIncomingCallPermitted(_arg047, _arg131);
                    reply.writeNoException();
                    reply.writeBoolean(_result39);
                    return true;
                case 55:
                    PhoneAccountHandle _arg048 =
                            (PhoneAccountHandle) data.readTypedObject(PhoneAccountHandle.CREATOR);
                    String _arg132 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result40 = isOutgoingCallPermitted(_arg048, _arg132);
                    reply.writeNoException();
                    reply.writeBoolean(_result40);
                    return true;
                case 56:
                    waitOnHandlers();
                    reply.writeNoException();
                    return true;
                case 57:
                    Uri _arg049 = (Uri) data.readTypedObject(Uri.CREATOR);
                    int _arg133 = data.readInt();
                    PhoneAccountHandle _arg212 =
                            (PhoneAccountHandle) data.readTypedObject(PhoneAccountHandle.CREATOR);
                    String _arg34 = data.readString();
                    data.enforceNoDataAvail();
                    acceptHandover(_arg049, _arg133, _arg212, _arg34);
                    reply.writeNoException();
                    return true;
                case 58:
                    String _arg050 = data.readString();
                    data.enforceNoDataAvail();
                    setTestEmergencyPhoneAccountPackageNameFilter(_arg050);
                    reply.writeNoException();
                    return true;
                case 59:
                    boolean _result41 = isInEmergencyCall();
                    reply.writeNoException();
                    reply.writeBoolean(_result41);
                    return true;
                case 60:
                    Intent _arg051 = (Intent) data.readTypedObject(Intent.CREATOR);
                    String _arg134 = data.readString();
                    data.enforceNoDataAvail();
                    handleCallIntent(_arg051, _arg134);
                    reply.writeNoException();
                    return true;
                case 61:
                    cleanupStuckCalls();
                    reply.writeNoException();
                    return true;
                case 62:
                    int _result42 = cleanupOrphanPhoneAccounts();
                    reply.writeNoException();
                    reply.writeInt(_result42);
                    return true;
                case 63:
                    String _arg052 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result43 = isNonUiInCallServiceBound(_arg052);
                    reply.writeNoException();
                    reply.writeBoolean(_result43);
                    return true;
                case 64:
                    resetCarMode();
                    reply.writeNoException();
                    return true;
                case 65:
                    String _arg053 = data.readString();
                    data.enforceNoDataAvail();
                    setTestDefaultCallRedirectionApp(_arg053);
                    reply.writeNoException();
                    return true;
                case 66:
                    String _arg054 = data.readString();
                    data.enforceNoDataAvail();
                    requestLogMark(_arg054);
                    reply.writeNoException();
                    return true;
                case 67:
                    String _arg055 = data.readString();
                    data.enforceNoDataAvail();
                    setTestPhoneAcctSuggestionComponent(_arg055);
                    reply.writeNoException();
                    return true;
                case 68:
                    String _arg056 = data.readString();
                    data.enforceNoDataAvail();
                    setTestDefaultCallScreeningApp(_arg056);
                    reply.writeNoException();
                    return true;
                case 69:
                    String _arg057 = data.readString();
                    boolean _arg135 = data.readBoolean();
                    data.enforceNoDataAvail();
                    addOrRemoveTestCallCompanionApp(_arg057, _arg135);
                    reply.writeNoException();
                    return true;
                case 70:
                    ComponentName _arg058 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    setSystemDialer(_arg058);
                    reply.writeNoException();
                    return true;
                case 71:
                    String _arg059 = data.readString();
                    data.enforceNoDataAvail();
                    setTestDefaultDialer(_arg059);
                    reply.writeNoException();
                    return true;
                case 72:
                    String _arg060 = data.readString();
                    data.enforceNoDataAvail();
                    setTestCallDiagnosticService(_arg060);
                    reply.writeNoException();
                    return true;
                case 73:
                    String _arg061 = data.readString();
                    UserHandle _arg136 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    String _arg213 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result44 = isInSelfManagedCall(_arg061, _arg136, _arg213);
                    reply.writeNoException();
                    reply.writeBoolean(_result44);
                    return true;
                case 74:
                    CallAttributes _arg062 =
                            (CallAttributes) data.readTypedObject(CallAttributes.CREATOR);
                    ICallEventCallback _arg137 =
                            ICallEventCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg214 = data.readString();
                    String _arg35 = data.readString();
                    data.enforceNoDataAvail();
                    addCall(_arg062, _arg137, _arg214, _arg35);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITelecomService {
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

            @Override // com.android.internal.telecom.ITelecomService
            public void showInCallScreen(
                    boolean showDialpad, String callingPackage, String callingFeatureId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(showDialpad);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public PhoneAccountHandle getDefaultOutgoingPhoneAccount(
                    String uriScheme, String callingPackage, String callingFeatureId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uriScheme);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    PhoneAccountHandle _result =
                            (PhoneAccountHandle) _reply.readTypedObject(PhoneAccountHandle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public PhoneAccountHandle getUserSelectedOutgoingPhoneAccount(String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    PhoneAccountHandle _result =
                            (PhoneAccountHandle) _reply.readTypedObject(PhoneAccountHandle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setUserSelectedOutgoingPhoneAccount(PhoneAccountHandle account)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(account, 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getCallCapablePhoneAccounts(
                    boolean includeDisabledAccounts,
                    String callingPackage,
                    String callingFeatureId,
                    boolean acrossProfiles)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(includeDisabledAccounts);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    _data.writeBoolean(acrossProfiles);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice<PhoneAccountHandle> _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getSelfManagedPhoneAccounts(
                    String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice<PhoneAccountHandle> _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getOwnSelfManagedPhoneAccounts(
                    String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice<PhoneAccountHandle> _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getPhoneAccountsSupportingScheme(
                    String uriScheme, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uriScheme);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice<PhoneAccountHandle> _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getPhoneAccountsForPackage(
                    String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice<PhoneAccountHandle> _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public PhoneAccount getPhoneAccount(PhoneAccountHandle account, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(account, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    PhoneAccount _result =
                            (PhoneAccount) _reply.readTypedObject(PhoneAccount.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccount> getRegisteredPhoneAccounts(
                    String callingPackage, String callingFeatureId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice<PhoneAccount> _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int getAllPhoneAccountsCount() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccount> getAllPhoneAccounts() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice<PhoneAccount> _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getAllPhoneAccountHandles()
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice<PhoneAccountHandle> _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public PhoneAccountHandle getSimCallManager(int subId, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    PhoneAccountHandle _result =
                            (PhoneAccountHandle) _reply.readTypedObject(PhoneAccountHandle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public PhoneAccountHandle getSimCallManagerForUser(int userId, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    PhoneAccountHandle _result =
                            (PhoneAccountHandle) _reply.readTypedObject(PhoneAccountHandle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void registerPhoneAccount(PhoneAccount metadata, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(metadata, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void unregisterPhoneAccount(PhoneAccountHandle account, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(account, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void clearAccounts(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isVoiceMailNumber(
                    PhoneAccountHandle accountHandle,
                    String number,
                    String callingPackage,
                    String callingFeatureId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(accountHandle, 0);
                    _data.writeString(number);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public String getVoiceMailNumber(
                    PhoneAccountHandle accountHandle,
                    String callingPackage,
                    String callingFeatureId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(accountHandle, 0);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public String getLine1Number(
                    PhoneAccountHandle accountHandle,
                    String callingPackage,
                    String callingFeatureId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(accountHandle, 0);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ComponentName getDefaultPhoneApp() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    ComponentName _result =
                            (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public String getDefaultDialerPackage(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public String getDefaultDialerPackageForUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public String getSystemDialerPackage(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public TelecomAnalytics dumpCallAnalytics() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    TelecomAnalytics _result =
                            (TelecomAnalytics) _reply.readTypedObject(TelecomAnalytics.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void silenceRinger(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isInCall(String callingPackage, String callingFeatureId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean hasManageOngoingCallsPermission(String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isInManagedCall(String callingPackage, String callingFeatureId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isRinging(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int getCallState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int getCallStateUsingPackage(String callingPackage, String callingFeatureId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean endCall(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void acceptRingingCall(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void acceptRingingCallWithVideoState(String callingPackage, int videoState)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(videoState);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void cancelMissedCallsNotification(String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean handlePinMmi(String dialString, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(dialString);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean handlePinMmiForPhoneAccount(
                    PhoneAccountHandle accountHandle, String dialString, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(accountHandle, 0);
                    _data.writeString(dialString);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public Uri getAdnUriForPhoneAccount(
                    PhoneAccountHandle accountHandle, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(accountHandle, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                    Uri _result = (Uri) _reply.readTypedObject(Uri.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isTtySupported(String callingPackage, String callingFeatureId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int getCurrentTtyMode(String callingPackage, String callingFeatureId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addNewIncomingCall(
                    PhoneAccountHandle phoneAccount, Bundle extras, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(phoneAccount, 0);
                    _data.writeTypedObject(extras, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addNewIncomingConference(
                    PhoneAccountHandle phoneAccount, Bundle extras, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(phoneAccount, 0);
                    _data.writeTypedObject(extras, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addNewUnknownCall(PhoneAccountHandle phoneAccount, Bundle extras)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(phoneAccount, 0);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void startConference(
                    List<Uri> participants, Bundle extras, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(participants, 0);
                    _data.writeTypedObject(extras, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void placeCall(
                    Uri handle, Bundle extras, String callingPackage, String callingFeatureId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(handle, 0);
                    _data.writeTypedObject(extras, 0);
                    _data.writeString(callingPackage);
                    _data.writeString(callingFeatureId);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean enablePhoneAccount(PhoneAccountHandle accountHandle, boolean isEnabled)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(accountHandle, 0);
                    _data.writeBoolean(isEnabled);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean setDefaultDialer(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void stopBlockSuppression() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public Intent createManageBlockedNumbersIntent(String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                    Intent _result = (Intent) _reply.readTypedObject(Intent.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public Intent createLaunchEmergencyDialerIntent(String number) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(number);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    Intent _result = (Intent) _reply.readTypedObject(Intent.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isIncomingCallPermitted(
                    PhoneAccountHandle phoneAccountHandle, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(phoneAccountHandle, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isOutgoingCallPermitted(
                    PhoneAccountHandle phoneAccountHandle, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(phoneAccountHandle, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void waitOnHandlers() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void acceptHandover(
                    Uri srcAddr, int videoState, PhoneAccountHandle destAcct, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(srcAddr, 0);
                    _data.writeInt(videoState);
                    _data.writeTypedObject(destAcct, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestEmergencyPhoneAccountPackageNameFilter(String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isInEmergencyCall() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void handleCallIntent(Intent intent, String callingPackageProxy)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intent, 0);
                    _data.writeString(callingPackageProxy);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void cleanupStuckCalls() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int cleanupOrphanPhoneAccounts() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isNonUiInCallServiceBound(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void resetCarMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestDefaultCallRedirectionApp(String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void requestLogMark(String message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(message);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestPhoneAcctSuggestionComponent(String flattenedComponentName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(flattenedComponentName);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestDefaultCallScreeningApp(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addOrRemoveTestCallCompanionApp(String packageName, boolean isAdded)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(isAdded);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setSystemDialer(ComponentName testComponentName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(testComponentName, 0);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestDefaultDialer(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestCallDiagnosticService(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isInSelfManagedCall(
                    String packageName, UserHandle userHandle, String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(userHandle, 0);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addCall(
                    CallAttributes callAttributes,
                    ICallEventCallback callback,
                    String callId,
                    String callingPackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(callAttributes, 0);
                    _data.writeStrongInterface(callback);
                    _data.writeString(callId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 73;
        }
    }
}
