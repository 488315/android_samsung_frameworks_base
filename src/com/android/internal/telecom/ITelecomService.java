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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITelecomService)) {
                return (ITelecomService) queryLocalInterface;
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
                    boolean readBoolean = parcel.readBoolean();
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showInCallScreen(readBoolean, readString, readString2);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    PhoneAccountHandle defaultOutgoingPhoneAccount = getDefaultOutgoingPhoneAccount(readString3, readString4, readString5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultOutgoingPhoneAccount, 1);
                    return true;
                case 3:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    PhoneAccountHandle userSelectedOutgoingPhoneAccount = getUserSelectedOutgoingPhoneAccount(readString6);
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
                    boolean readBoolean2 = parcel.readBoolean();
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccountHandle> callCapablePhoneAccounts = getCallCapablePhoneAccounts(readBoolean2, readString7, readString8, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(callCapablePhoneAccounts, 1);
                    return true;
                case 6:
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccountHandle> selfManagedPhoneAccounts = getSelfManagedPhoneAccounts(readString9, readString10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(selfManagedPhoneAccounts, 1);
                    return true;
                case 7:
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccountHandle> ownSelfManagedPhoneAccounts = getOwnSelfManagedPhoneAccounts(readString11, readString12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ownSelfManagedPhoneAccounts, 1);
                    return true;
                case 8:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccountHandle> phoneAccountsSupportingScheme = getPhoneAccountsSupportingScheme(readString13, readString14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(phoneAccountsSupportingScheme, 1);
                    return true;
                case 9:
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccountHandle> phoneAccountsForPackage = getPhoneAccountsForPackage(readString15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(phoneAccountsForPackage, 1);
                    return true;
                case 10:
                    PhoneAccountHandle phoneAccountHandle2 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    PhoneAccount phoneAccount = getPhoneAccount(phoneAccountHandle2, readString16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(phoneAccount, 1);
                    return true;
                case 11:
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<PhoneAccount> registeredPhoneAccounts = getRegisteredPhoneAccounts(readString17, readString18);
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
                    int readInt = parcel.readInt();
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    PhoneAccountHandle simCallManager = getSimCallManager(readInt, readString19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(simCallManager, 1);
                    return true;
                case 16:
                    int readInt2 = parcel.readInt();
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    PhoneAccountHandle simCallManagerForUser = getSimCallManagerForUser(readInt2, readString20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(simCallManagerForUser, 1);
                    return true;
                case 17:
                    PhoneAccount phoneAccount2 = (PhoneAccount) parcel.readTypedObject(PhoneAccount.CREATOR);
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerPhoneAccount(phoneAccount2, readString21);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    PhoneAccountHandle phoneAccountHandle3 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterPhoneAccount(phoneAccountHandle3, readString22);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearAccounts(readString23);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    PhoneAccountHandle phoneAccountHandle4 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String readString24 = parcel.readString();
                    String readString25 = parcel.readString();
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isVoiceMailNumber = isVoiceMailNumber(phoneAccountHandle4, readString24, readString25, readString26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVoiceMailNumber);
                    return true;
                case 21:
                    PhoneAccountHandle phoneAccountHandle5 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String readString27 = parcel.readString();
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String voiceMailNumber = getVoiceMailNumber(phoneAccountHandle5, readString27, readString28);
                    parcel2.writeNoException();
                    parcel2.writeString(voiceMailNumber);
                    return true;
                case 22:
                    PhoneAccountHandle phoneAccountHandle6 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String readString29 = parcel.readString();
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String line1Number = getLine1Number(phoneAccountHandle6, readString29, readString30);
                    parcel2.writeNoException();
                    parcel2.writeString(line1Number);
                    return true;
                case 23:
                    ComponentName defaultPhoneApp = getDefaultPhoneApp();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultPhoneApp, 1);
                    return true;
                case 24:
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String defaultDialerPackage = getDefaultDialerPackage(readString31);
                    parcel2.writeNoException();
                    parcel2.writeString(defaultDialerPackage);
                    return true;
                case 25:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String defaultDialerPackageForUser = getDefaultDialerPackageForUser(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeString(defaultDialerPackageForUser);
                    return true;
                case 26:
                    String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String systemDialerPackage = getSystemDialerPackage(readString32);
                    parcel2.writeNoException();
                    parcel2.writeString(systemDialerPackage);
                    return true;
                case 27:
                    TelecomAnalytics dumpCallAnalytics = dumpCallAnalytics();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dumpCallAnalytics, 1);
                    return true;
                case 28:
                    String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    silenceRinger(readString33);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    String readString34 = parcel.readString();
                    String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isInCall = isInCall(readString34, readString35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInCall);
                    return true;
                case 30:
                    String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasManageOngoingCallsPermission = hasManageOngoingCallsPermission(readString36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasManageOngoingCallsPermission);
                    return true;
                case 31:
                    String readString37 = parcel.readString();
                    String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isInManagedCall = isInManagedCall(readString37, readString38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInManagedCall);
                    return true;
                case 32:
                    String readString39 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isRinging = isRinging(readString39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRinging);
                    return true;
                case 33:
                    int callState = getCallState();
                    parcel2.writeNoException();
                    parcel2.writeInt(callState);
                    return true;
                case 34:
                    String readString40 = parcel.readString();
                    String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int callStateUsingPackage = getCallStateUsingPackage(readString40, readString41);
                    parcel2.writeNoException();
                    parcel2.writeInt(callStateUsingPackage);
                    return true;
                case 35:
                    String readString42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean endCall = endCall(readString42);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(endCall);
                    return true;
                case 36:
                    String readString43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acceptRingingCall(readString43);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    String readString44 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acceptRingingCallWithVideoState(readString44, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelMissedCallsNotification(readString45);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    String readString46 = parcel.readString();
                    String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean handlePinMmi = handlePinMmi(readString46, readString47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(handlePinMmi);
                    return true;
                case 40:
                    PhoneAccountHandle phoneAccountHandle7 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String readString48 = parcel.readString();
                    String readString49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean handlePinMmiForPhoneAccount = handlePinMmiForPhoneAccount(phoneAccountHandle7, readString48, readString49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(handlePinMmiForPhoneAccount);
                    return true;
                case 41:
                    PhoneAccountHandle phoneAccountHandle8 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Uri adnUriForPhoneAccount = getAdnUriForPhoneAccount(phoneAccountHandle8, readString50);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(adnUriForPhoneAccount, 1);
                    return true;
                case 42:
                    String readString51 = parcel.readString();
                    String readString52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isTtySupported = isTtySupported(readString51, readString52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTtySupported);
                    return true;
                case 43:
                    String readString53 = parcel.readString();
                    String readString54 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int currentTtyMode = getCurrentTtyMode(readString53, readString54);
                    parcel2.writeNoException();
                    parcel2.writeInt(currentTtyMode);
                    return true;
                case 44:
                    PhoneAccountHandle phoneAccountHandle9 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString55 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addNewIncomingCall(phoneAccountHandle9, bundle, readString55);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    PhoneAccountHandle phoneAccountHandle10 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addNewIncomingConference(phoneAccountHandle10, bundle2, readString56);
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
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(Uri.CREATOR);
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString57 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startConference(createTypedArrayList, bundle4, readString57);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Bundle bundle5 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    String readString58 = parcel.readString();
                    String readString59 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    placeCall(uri, bundle5, readString58, readString59);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    PhoneAccountHandle phoneAccountHandle12 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enablePhoneAccount = enablePhoneAccount(phoneAccountHandle12, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enablePhoneAccount);
                    return true;
                case 50:
                    String readString60 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean defaultDialer = setDefaultDialer(readString60);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(defaultDialer);
                    return true;
                case 51:
                    stopBlockSuppression();
                    parcel2.writeNoException();
                    return true;
                case 52:
                    String readString61 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Intent createManageBlockedNumbersIntent = createManageBlockedNumbersIntent(readString61);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createManageBlockedNumbersIntent, 1);
                    return true;
                case 53:
                    String readString62 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Intent createLaunchEmergencyDialerIntent = createLaunchEmergencyDialerIntent(readString62);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createLaunchEmergencyDialerIntent, 1);
                    return true;
                case 54:
                    PhoneAccountHandle phoneAccountHandle13 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String readString63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isIncomingCallPermitted = isIncomingCallPermitted(phoneAccountHandle13, readString63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIncomingCallPermitted);
                    return true;
                case 55:
                    PhoneAccountHandle phoneAccountHandle14 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String readString64 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isOutgoingCallPermitted = isOutgoingCallPermitted(phoneAccountHandle14, readString64);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOutgoingCallPermitted);
                    return true;
                case 56:
                    waitOnHandlers();
                    parcel2.writeNoException();
                    return true;
                case 57:
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt5 = parcel.readInt();
                    PhoneAccountHandle phoneAccountHandle15 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String readString65 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acceptHandover(uri2, readInt5, phoneAccountHandle15, readString65);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    String readString66 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTestEmergencyPhoneAccountPackageNameFilter(readString66);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    boolean isInEmergencyCall = isInEmergencyCall();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInEmergencyCall);
                    return true;
                case 60:
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    String readString67 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    handleCallIntent(intent, readString67);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    cleanupStuckCalls();
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int cleanupOrphanPhoneAccounts = cleanupOrphanPhoneAccounts();
                    parcel2.writeNoException();
                    parcel2.writeInt(cleanupOrphanPhoneAccounts);
                    return true;
                case 63:
                    String readString68 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isNonUiInCallServiceBound = isNonUiInCallServiceBound(readString68);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNonUiInCallServiceBound);
                    return true;
                case 64:
                    resetCarMode();
                    parcel2.writeNoException();
                    return true;
                case 65:
                    String readString69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTestDefaultCallRedirectionApp(readString69);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    String readString70 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestLogMark(readString70);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    String readString71 = parcel.readString();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTestPhoneAcctSuggestionComponent(readString71, userHandle);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    String readString72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTestDefaultCallScreeningApp(readString72);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    String readString73 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    addOrRemoveTestCallCompanionApp(readString73, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSystemDialer(componentName);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    String readString74 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTestDefaultDialer(readString74);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    String readString75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setTestCallDiagnosticService(readString75);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    String readString76 = parcel.readString();
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String readString77 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isInSelfManagedCall = isInSelfManagedCall(readString76, userHandle2, readString77);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInSelfManagedCall);
                    return true;
                case 74:
                    CallAttributes callAttributes = (CallAttributes) parcel.readTypedObject(CallAttributes.CREATOR);
                    ICallEventCallback asInterface = ICallEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString78 = parcel.readString();
                    String readString79 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addCall(callAttributes, asInterface, readString78, readString79);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    PhoneAccountHandle phoneAccountHandle16 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String readString80 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasForegroundServiceDelegation = hasForegroundServiceDelegation(phoneAccountHandle16, readString80);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasForegroundServiceDelegation);
                    return true;
                case 76:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMetricsTestMode(readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    waitForAudioToUpdate(readBoolean7);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public PhoneAccountHandle getDefaultOutgoingPhoneAccount(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PhoneAccountHandle) obtain2.readTypedObject(PhoneAccountHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public PhoneAccountHandle getUserSelectedOutgoingPhoneAccount(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PhoneAccountHandle) obtain2.readTypedObject(PhoneAccountHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setUserSelectedOutgoingPhoneAccount(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getCallCapablePhoneAccounts(boolean z, String str, String str2, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getSelfManagedPhoneAccounts(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getOwnSelfManagedPhoneAccounts(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getPhoneAccountsSupportingScheme(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getPhoneAccountsForPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public PhoneAccount getPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PhoneAccount) obtain2.readTypedObject(PhoneAccount.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccount> getRegisteredPhoneAccounts(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int getAllPhoneAccountsCount() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccount> getAllPhoneAccounts() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ParceledListSlice<PhoneAccountHandle> getAllPhoneAccountHandles() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public PhoneAccountHandle getSimCallManager(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PhoneAccountHandle) obtain2.readTypedObject(PhoneAccountHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public PhoneAccountHandle getSimCallManagerForUser(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PhoneAccountHandle) obtain2.readTypedObject(PhoneAccountHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void registerPhoneAccount(PhoneAccount phoneAccount, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccount, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void unregisterPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void clearAccounts(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isVoiceMailNumber(PhoneAccountHandle phoneAccountHandle, String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public String getVoiceMailNumber(PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public String getLine1Number(PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public ComponentName getDefaultPhoneApp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public String getDefaultDialerPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public String getDefaultDialerPackageForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public String getSystemDialerPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public TelecomAnalytics dumpCallAnalytics() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return (TelecomAnalytics) obtain2.readTypedObject(TelecomAnalytics.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void silenceRinger(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isInCall(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean hasManageOngoingCallsPermission(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isInManagedCall(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isRinging(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int getCallState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int getCallStateUsingPackage(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean endCall(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void acceptRingingCall(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void acceptRingingCallWithVideoState(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void cancelMissedCallsNotification(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean handlePinMmi(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean handlePinMmiForPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public Uri getAdnUriForPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Uri) obtain2.readTypedObject(Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isTtySupported(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int getCurrentTtyMode(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addNewIncomingCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addNewIncomingConference(PhoneAccountHandle phoneAccountHandle, Bundle bundle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addNewUnknownCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void startConference(List<Uri> list, Bundle bundle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void placeCall(Uri uri, Bundle bundle, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean enablePhoneAccount(PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean setDefaultDialer(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void stopBlockSuppression() throws RemoteException {
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

            @Override // com.android.internal.telecom.ITelecomService
            public Intent createManageBlockedNumbersIntent(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Intent) obtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public Intent createLaunchEmergencyDialerIntent(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Intent) obtain2.readTypedObject(Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isIncomingCallPermitted(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isOutgoingCallPermitted(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void waitOnHandlers() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void acceptHandover(Uri uri, int i, PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestEmergencyPhoneAccountPackageNameFilter(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isInEmergencyCall() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void handleCallIntent(Intent intent, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void cleanupStuckCalls() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public int cleanupOrphanPhoneAccounts() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isNonUiInCallServiceBound(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void resetCarMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestDefaultCallRedirectionApp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void requestLogMark(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestPhoneAcctSuggestionComponent(String str, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestDefaultCallScreeningApp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addOrRemoveTestCallCompanionApp(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setSystemDialer(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestDefaultDialer(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setTestCallDiagnosticService(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean isInSelfManagedCall(String str, UserHandle userHandle, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void addCall(CallAttributes callAttributes, ICallEventCallback iCallEventCallback, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(callAttributes, 0);
                    obtain.writeStrongInterface(iCallEventCallback);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public boolean hasForegroundServiceDelegation(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void setMetricsTestMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ITelecomService
            public void waitForAudioToUpdate(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
