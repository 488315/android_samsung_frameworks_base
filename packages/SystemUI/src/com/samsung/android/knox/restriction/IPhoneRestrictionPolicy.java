package com.samsung.android.knox.restriction;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;

/* loaded from: classes4.dex */
public interface IPhoneRestrictionPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.restriction.IPhoneRestrictionPolicy";

    boolean addIncomingCallExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException;

    boolean addIncomingCallRestriction(ContextInfo contextInfo, String str) throws RemoteException;

    boolean addIncomingSmsExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException;

    boolean addIncomingSmsRestriction(ContextInfo contextInfo, String str) throws RemoteException;

    boolean addNumberOfIncomingCalls() throws RemoteException;

    boolean addNumberOfIncomingSms() throws RemoteException;

    boolean addNumberOfOutgoingCalls() throws RemoteException;

    boolean addNumberOfOutgoingSms() throws RemoteException;

    boolean addOutgoingCallExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException;

    boolean addOutgoingCallRestriction(ContextInfo contextInfo, String str) throws RemoteException;

    boolean addOutgoingSmsExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException;

    boolean addOutgoingSmsRestriction(ContextInfo contextInfo, String str) throws RemoteException;

    boolean allowCallerIDDisplay(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowCopyContactToSim(ContextInfo contextInfo, boolean z) throws RemoteException;

    int allowDataNetworkFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException;

    int allowIncomingCallFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException;

    boolean allowIncomingMms(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowIncomingSms(ContextInfo contextInfo, boolean z) throws RemoteException;

    int allowIncomingSmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException;

    int allowMmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException;

    int allowOutgoingCallFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException;

    boolean allowOutgoingMms(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean allowOutgoingSms(ContextInfo contextInfo, boolean z) throws RemoteException;

    int allowOutgoingSmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException;

    boolean allowWapPush(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean blockMmsWithStorage(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean blockSmsWithStorage(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean canIncomingCall(String str) throws RemoteException;

    boolean canIncomingSms(String str) throws RemoteException;

    boolean canOutgoingCall(String str) throws RemoteException;

    boolean canOutgoingSms(String str) throws RemoteException;

    int changeSimPinCode(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException;

    boolean checkDataCallLimit() throws RemoteException;

    boolean checkEnableUseOfPacketData(boolean z) throws RemoteException;

    boolean clearStoredBlockedMms(ContextInfo contextInfo) throws RemoteException;

    boolean clearStoredBlockedSms(ContextInfo contextInfo) throws RemoteException;

    boolean decreaseNumberOfOutgoingSms() throws RemoteException;

    boolean enableLimitNumberOfCalls(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean enableLimitNumberOfSms(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean getDataCallLimitEnabled(ContextInfo contextInfo) throws RemoteException;

    String getDisclaimerText(ContextInfo contextInfo) throws RemoteException;

    boolean getEmergencyCallOnly(ContextInfo contextInfo, boolean z) throws RemoteException;

    String getIncomingCallExceptionPatterns(ContextInfo contextInfo) throws RemoteException;

    String getIncomingCallRestriction(ContextInfo contextInfo, boolean z) throws RemoteException;

    String getIncomingSmsExceptionPatterns(ContextInfo contextInfo) throws RemoteException;

    String getIncomingSmsRestriction(ContextInfo contextInfo, boolean z) throws RemoteException;

    long getLimitOfDataCalls(ContextInfo contextInfo, int i) throws RemoteException;

    int getLimitOfIncomingCalls(ContextInfo contextInfo, int i) throws RemoteException;

    int getLimitOfIncomingSms(ContextInfo contextInfo, int i) throws RemoteException;

    int getLimitOfOutgoingCalls(ContextInfo contextInfo, int i) throws RemoteException;

    int getLimitOfOutgoingSms(ContextInfo contextInfo, int i) throws RemoteException;

    String getOutgoingCallExceptionPatterns(ContextInfo contextInfo) throws RemoteException;

    String getOutgoingCallRestriction(ContextInfo contextInfo, boolean z) throws RemoteException;

    String getOutgoingSmsExceptionPatterns(ContextInfo contextInfo) throws RemoteException;

    String getOutgoingSmsRestriction(ContextInfo contextInfo, boolean z) throws RemoteException;

    String getPinCode(String str) throws RemoteException;

    Bundle getRCSMessage(ContextInfo contextInfo, long j) throws RemoteException;

    boolean isBlockMmsWithStorageEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isBlockSmsWithStorageEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isCallerIDDisplayAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isCopyContactToSimAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isDataAllowedFromSimSlot(int i) throws RemoteException;

    boolean isIncomingCallAllowedFromSimSlot(int i) throws RemoteException;

    boolean isIncomingMmsAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isIncomingSmsAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isIncomingSmsAllowedFromSimSlot(int i) throws RemoteException;

    boolean isLimitNumberOfCallsEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isLimitNumberOfSmsEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isMmsAllowedFromSimSlot(int i) throws RemoteException;

    boolean isOutgoingCallAllowedFromSimSlot(int i) throws RemoteException;

    boolean isOutgoingMmsAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isOutgoingSmsAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isOutgoingSmsAllowedFromSimSlot(int i) throws RemoteException;

    boolean isRCSEnabled(ContextInfo contextInfo, int i, boolean z) throws RemoteException;

    boolean isRCSEnabledBySimSlot(ContextInfo contextInfo, int i, boolean z, int i2) throws RemoteException;

    boolean isSimLockedByAdmin(String str) throws RemoteException;

    boolean isSubIdLockedByAdmin(int i) throws RemoteException;

    boolean isWapPushAllowed(ContextInfo contextInfo) throws RemoteException;

    int lockUnlockCorporateSimCard(ContextInfo contextInfo, String str, String str2, boolean z) throws RemoteException;

    boolean removeIncomingCallExceptionPattern(ContextInfo contextInfo) throws RemoteException;

    boolean removeIncomingCallRestriction(ContextInfo contextInfo) throws RemoteException;

    boolean removeIncomingSmsExceptionPattern(ContextInfo contextInfo) throws RemoteException;

    boolean removeIncomingSmsRestriction(ContextInfo contextInfo) throws RemoteException;

    boolean removeOutgoingCallExceptionPattern(ContextInfo contextInfo) throws RemoteException;

    boolean removeOutgoingCallRestriction(ContextInfo contextInfo) throws RemoteException;

    boolean removeOutgoingSmsExceptionPattern(ContextInfo contextInfo) throws RemoteException;

    boolean removeOutgoingSmsRestriction(ContextInfo contextInfo) throws RemoteException;

    boolean resetCallsCount(ContextInfo contextInfo) throws RemoteException;

    boolean resetDataCallLimitCounter(ContextInfo contextInfo) throws RemoteException;

    boolean resetSmsCount(ContextInfo contextInfo) throws RemoteException;

    boolean setDataCallLimitEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setDisclaimerText(ContextInfo contextInfo, String str) throws RemoteException;

    boolean setEmergencyCallOnly(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setIncomingCallExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException;

    boolean setIncomingCallRestriction(ContextInfo contextInfo, String str) throws RemoteException;

    boolean setIncomingSmsExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException;

    boolean setIncomingSmsRestriction(ContextInfo contextInfo, String str) throws RemoteException;

    boolean setLimitOfDataCalls(ContextInfo contextInfo, long j, long j2, long j3) throws RemoteException;

    boolean setLimitOfIncomingCalls(ContextInfo contextInfo, int i, int i2, int i3) throws RemoteException;

    boolean setLimitOfIncomingSms(ContextInfo contextInfo, int i, int i2, int i3) throws RemoteException;

    boolean setLimitOfOutgoingCalls(ContextInfo contextInfo, int i, int i2, int i3) throws RemoteException;

    boolean setLimitOfOutgoingSms(ContextInfo contextInfo, int i, int i2, int i3) throws RemoteException;

    boolean setOutgoingCallExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException;

    boolean setOutgoingCallRestriction(ContextInfo contextInfo, String str) throws RemoteException;

    boolean setOutgoingSmsExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException;

    boolean setOutgoingSmsRestriction(ContextInfo contextInfo, String str) throws RemoteException;

    int setRCSEnabled(ContextInfo contextInfo, int i, boolean z) throws RemoteException;

    int setRCSEnabledBySimSlot(ContextInfo contextInfo, int i, boolean z, int i2) throws RemoteException;

    void updateDataLimitState() throws RemoteException;

    void updateDateAndDataCallCounters(long j) throws RemoteException;

    public class Default implements IPhoneRestrictionPolicy {
        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean addIncomingCallExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean addIncomingCallRestriction(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean addIncomingSmsExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean addIncomingSmsRestriction(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean addNumberOfIncomingCalls() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean addNumberOfIncomingSms() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean addNumberOfOutgoingCalls() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean addNumberOfOutgoingSms() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean addOutgoingCallExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean addOutgoingCallRestriction(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean addOutgoingSmsExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean addOutgoingSmsRestriction(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean allowCallerIDDisplay(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean allowCopyContactToSim(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public int allowDataNetworkFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public int allowIncomingCallFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean allowIncomingMms(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean allowIncomingSms(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public int allowIncomingSmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public int allowMmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public int allowOutgoingCallFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean allowOutgoingMms(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean allowOutgoingSms(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public int allowOutgoingSmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean allowWapPush(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean blockMmsWithStorage(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean blockSmsWithStorage(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean canIncomingCall(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean canIncomingSms(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean canOutgoingCall(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean canOutgoingSms(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public int changeSimPinCode(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean checkDataCallLimit() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean checkEnableUseOfPacketData(boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean clearStoredBlockedMms(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean clearStoredBlockedSms(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean decreaseNumberOfOutgoingSms() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean enableLimitNumberOfCalls(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean enableLimitNumberOfSms(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean getDataCallLimitEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public String getDisclaimerText(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean getEmergencyCallOnly(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public String getIncomingCallExceptionPatterns(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public String getIncomingCallRestriction(ContextInfo contextInfo, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public String getIncomingSmsExceptionPatterns(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public String getIncomingSmsRestriction(ContextInfo contextInfo, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public long getLimitOfDataCalls(ContextInfo contextInfo, int i) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public int getLimitOfIncomingCalls(ContextInfo contextInfo, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public int getLimitOfIncomingSms(ContextInfo contextInfo, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public int getLimitOfOutgoingCalls(ContextInfo contextInfo, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public int getLimitOfOutgoingSms(ContextInfo contextInfo, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public String getOutgoingCallExceptionPatterns(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public String getOutgoingCallRestriction(ContextInfo contextInfo, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public String getOutgoingSmsExceptionPatterns(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public String getOutgoingSmsRestriction(ContextInfo contextInfo, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public String getPinCode(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public Bundle getRCSMessage(ContextInfo contextInfo, long j) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isBlockMmsWithStorageEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isBlockSmsWithStorageEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isCallerIDDisplayAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isCopyContactToSimAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isDataAllowedFromSimSlot(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isIncomingCallAllowedFromSimSlot(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isIncomingMmsAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isIncomingSmsAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isIncomingSmsAllowedFromSimSlot(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isLimitNumberOfCallsEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isLimitNumberOfSmsEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isMmsAllowedFromSimSlot(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isOutgoingCallAllowedFromSimSlot(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isOutgoingMmsAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isOutgoingSmsAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isOutgoingSmsAllowedFromSimSlot(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isRCSEnabled(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isRCSEnabledBySimSlot(ContextInfo contextInfo, int i, boolean z, int i2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isSimLockedByAdmin(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isSubIdLockedByAdmin(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean isWapPushAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public int lockUnlockCorporateSimCard(ContextInfo contextInfo, String str, String str2, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean removeIncomingCallExceptionPattern(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean removeIncomingCallRestriction(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean removeIncomingSmsExceptionPattern(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean removeIncomingSmsRestriction(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean removeOutgoingCallExceptionPattern(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean removeOutgoingCallRestriction(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean removeOutgoingSmsExceptionPattern(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean removeOutgoingSmsRestriction(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean resetCallsCount(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean resetDataCallLimitCounter(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean resetSmsCount(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean setDataCallLimitEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean setDisclaimerText(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean setEmergencyCallOnly(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean setIncomingCallExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean setIncomingCallRestriction(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean setIncomingSmsExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean setIncomingSmsRestriction(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean setLimitOfDataCalls(ContextInfo contextInfo, long j, long j2, long j3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean setLimitOfIncomingCalls(ContextInfo contextInfo, int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean setLimitOfIncomingSms(ContextInfo contextInfo, int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean setLimitOfOutgoingCalls(ContextInfo contextInfo, int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean setLimitOfOutgoingSms(ContextInfo contextInfo, int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean setOutgoingCallExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean setOutgoingCallRestriction(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean setOutgoingSmsExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public boolean setOutgoingSmsRestriction(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public int setRCSEnabled(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public int setRCSEnabledBySimSlot(ContextInfo contextInfo, int i, boolean z, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public void updateDataLimitState() throws RemoteException {
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public void updateDateAndDataCallCounters(long j) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements IPhoneRestrictionPolicy {
        public static final int TRANSACTION_addIncomingCallExceptionPattern = 81;
        public static final int TRANSACTION_addIncomingCallRestriction = 6;
        public static final int TRANSACTION_addIncomingSmsExceptionPattern = 89;
        public static final int TRANSACTION_addIncomingSmsRestriction = 27;
        public static final int TRANSACTION_addNumberOfIncomingCalls = 19;
        public static final int TRANSACTION_addNumberOfIncomingSms = 39;
        public static final int TRANSACTION_addNumberOfOutgoingCalls = 20;
        public static final int TRANSACTION_addNumberOfOutgoingSms = 40;
        public static final int TRANSACTION_addOutgoingCallExceptionPattern = 80;
        public static final int TRANSACTION_addOutgoingCallRestriction = 5;
        public static final int TRANSACTION_addOutgoingSmsExceptionPattern = 88;
        public static final int TRANSACTION_addOutgoingSmsRestriction = 26;
        public static final int TRANSACTION_allowCallerIDDisplay = 67;
        public static final int TRANSACTION_allowCopyContactToSim = 74;
        public static final int TRANSACTION_allowDataNetworkFromSimSlot = 99;
        public static final int TRANSACTION_allowIncomingCallFromSimSlot = 100;
        public static final int TRANSACTION_allowIncomingMms = 55;
        public static final int TRANSACTION_allowIncomingSms = 51;
        public static final int TRANSACTION_allowIncomingSmsFromSimSlot = 102;
        public static final int TRANSACTION_allowMmsFromSimSlot = 104;
        public static final int TRANSACTION_allowOutgoingCallFromSimSlot = 101;
        public static final int TRANSACTION_allowOutgoingMms = 56;
        public static final int TRANSACTION_allowOutgoingSms = 52;
        public static final int TRANSACTION_allowOutgoingSmsFromSimSlot = 103;
        public static final int TRANSACTION_allowWapPush = 65;
        public static final int TRANSACTION_blockMmsWithStorage = 61;
        public static final int TRANSACTION_blockSmsWithStorage = 59;
        public static final int TRANSACTION_canIncomingCall = 10;
        public static final int TRANSACTION_canIncomingSms = 31;
        public static final int TRANSACTION_canOutgoingCall = 9;
        public static final int TRANSACTION_canOutgoingSms = 30;
        public static final int TRANSACTION_changeSimPinCode = 70;
        public static final int TRANSACTION_checkDataCallLimit = 48;
        public static final int TRANSACTION_checkEnableUseOfPacketData = 47;
        public static final int TRANSACTION_clearStoredBlockedMms = 64;
        public static final int TRANSACTION_clearStoredBlockedSms = 63;
        public static final int TRANSACTION_decreaseNumberOfOutgoingSms = 41;
        public static final int TRANSACTION_enableLimitNumberOfCalls = 13;
        public static final int TRANSACTION_enableLimitNumberOfSms = 32;
        public static final int TRANSACTION_getDataCallLimitEnabled = 43;
        public static final int TRANSACTION_getDisclaimerText = 93;
        public static final int TRANSACTION_getEmergencyCallOnly = 12;
        public static final int TRANSACTION_getIncomingCallExceptionPatterns = 77;
        public static final int TRANSACTION_getIncomingCallRestriction = 2;
        public static final int TRANSACTION_getIncomingSmsExceptionPatterns = 85;
        public static final int TRANSACTION_getIncomingSmsRestriction = 23;
        public static final int TRANSACTION_getLimitOfDataCalls = 45;
        public static final int TRANSACTION_getLimitOfIncomingCalls = 16;
        public static final int TRANSACTION_getLimitOfIncomingSms = 36;
        public static final int TRANSACTION_getLimitOfOutgoingCalls = 18;
        public static final int TRANSACTION_getLimitOfOutgoingSms = 38;
        public static final int TRANSACTION_getOutgoingCallExceptionPatterns = 76;
        public static final int TRANSACTION_getOutgoingCallRestriction = 1;
        public static final int TRANSACTION_getOutgoingSmsExceptionPatterns = 84;
        public static final int TRANSACTION_getOutgoingSmsRestriction = 22;
        public static final int TRANSACTION_getPinCode = 72;
        public static final int TRANSACTION_getRCSMessage = 96;
        public static final int TRANSACTION_isBlockMmsWithStorageEnabled = 62;
        public static final int TRANSACTION_isBlockSmsWithStorageEnabled = 60;
        public static final int TRANSACTION_isCallerIDDisplayAllowed = 68;
        public static final int TRANSACTION_isCopyContactToSimAllowed = 75;
        public static final int TRANSACTION_isDataAllowedFromSimSlot = 105;
        public static final int TRANSACTION_isIncomingCallAllowedFromSimSlot = 106;
        public static final int TRANSACTION_isIncomingMmsAllowed = 57;
        public static final int TRANSACTION_isIncomingSmsAllowed = 53;
        public static final int TRANSACTION_isIncomingSmsAllowedFromSimSlot = 108;
        public static final int TRANSACTION_isLimitNumberOfCallsEnabled = 14;
        public static final int TRANSACTION_isLimitNumberOfSmsEnabled = 33;
        public static final int TRANSACTION_isMmsAllowedFromSimSlot = 110;
        public static final int TRANSACTION_isOutgoingCallAllowedFromSimSlot = 107;
        public static final int TRANSACTION_isOutgoingMmsAllowed = 58;
        public static final int TRANSACTION_isOutgoingSmsAllowed = 54;
        public static final int TRANSACTION_isOutgoingSmsAllowedFromSimSlot = 109;
        public static final int TRANSACTION_isRCSEnabled = 95;
        public static final int TRANSACTION_isRCSEnabledBySimSlot = 98;
        public static final int TRANSACTION_isSimLockedByAdmin = 71;
        public static final int TRANSACTION_isSubIdLockedByAdmin = 73;
        public static final int TRANSACTION_isWapPushAllowed = 66;
        public static final int TRANSACTION_lockUnlockCorporateSimCard = 69;
        public static final int TRANSACTION_removeIncomingCallExceptionPattern = 79;
        public static final int TRANSACTION_removeIncomingCallRestriction = 4;
        public static final int TRANSACTION_removeIncomingSmsExceptionPattern = 87;
        public static final int TRANSACTION_removeIncomingSmsRestriction = 25;
        public static final int TRANSACTION_removeOutgoingCallExceptionPattern = 78;
        public static final int TRANSACTION_removeOutgoingCallRestriction = 3;
        public static final int TRANSACTION_removeOutgoingSmsExceptionPattern = 86;
        public static final int TRANSACTION_removeOutgoingSmsRestriction = 24;
        public static final int TRANSACTION_resetCallsCount = 21;
        public static final int TRANSACTION_resetDataCallLimitCounter = 46;
        public static final int TRANSACTION_resetSmsCount = 34;
        public static final int TRANSACTION_setDataCallLimitEnabled = 42;
        public static final int TRANSACTION_setDisclaimerText = 92;
        public static final int TRANSACTION_setEmergencyCallOnly = 11;
        public static final int TRANSACTION_setIncomingCallExceptionPattern = 83;
        public static final int TRANSACTION_setIncomingCallRestriction = 8;
        public static final int TRANSACTION_setIncomingSmsExceptionPattern = 91;
        public static final int TRANSACTION_setIncomingSmsRestriction = 29;
        public static final int TRANSACTION_setLimitOfDataCalls = 44;
        public static final int TRANSACTION_setLimitOfIncomingCalls = 15;
        public static final int TRANSACTION_setLimitOfIncomingSms = 35;
        public static final int TRANSACTION_setLimitOfOutgoingCalls = 17;
        public static final int TRANSACTION_setLimitOfOutgoingSms = 37;
        public static final int TRANSACTION_setOutgoingCallExceptionPattern = 82;
        public static final int TRANSACTION_setOutgoingCallRestriction = 7;
        public static final int TRANSACTION_setOutgoingSmsExceptionPattern = 90;
        public static final int TRANSACTION_setOutgoingSmsRestriction = 28;
        public static final int TRANSACTION_setRCSEnabled = 94;
        public static final int TRANSACTION_setRCSEnabledBySimSlot = 97;
        public static final int TRANSACTION_updateDataLimitState = 50;
        public static final int TRANSACTION_updateDateAndDataCallCounters = 49;

        class Proxy implements IPhoneRestrictionPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean addIncomingCallExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean addIncomingCallRestriction(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean addIncomingSmsExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean addIncomingSmsRestriction(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean addNumberOfIncomingCalls() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean addNumberOfIncomingSms() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean addNumberOfOutgoingCalls() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean addNumberOfOutgoingSms() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean addOutgoingCallExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean addOutgoingCallRestriction(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean addOutgoingSmsExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean addOutgoingSmsRestriction(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean allowCallerIDDisplay(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean allowCopyContactToSim(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public int allowDataNetworkFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public int allowIncomingCallFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean allowIncomingMms(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean allowIncomingSms(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public int allowIncomingSmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public int allowMmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public int allowOutgoingCallFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean allowOutgoingMms(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean allowOutgoingSms(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public int allowOutgoingSmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean allowWapPush(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean blockMmsWithStorage(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean blockSmsWithStorage(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean canIncomingCall(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean canIncomingSms(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean canOutgoingCall(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean canOutgoingSms(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public int changeSimPinCode(ContextInfo contextInfo, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean checkDataCallLimit() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean checkEnableUseOfPacketData(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean clearStoredBlockedMms(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean clearStoredBlockedSms(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean decreaseNumberOfOutgoingSms() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean enableLimitNumberOfCalls(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean enableLimitNumberOfSms(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean getDataCallLimitEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public String getDisclaimerText(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean getEmergencyCallOnly(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public String getIncomingCallExceptionPatterns(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public String getIncomingCallRestriction(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public String getIncomingSmsExceptionPatterns(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public String getIncomingSmsRestriction(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IPhoneRestrictionPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public long getLimitOfDataCalls(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public int getLimitOfIncomingCalls(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public int getLimitOfIncomingSms(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public int getLimitOfOutgoingCalls(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public int getLimitOfOutgoingSms(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public String getOutgoingCallExceptionPatterns(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public String getOutgoingCallRestriction(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public String getOutgoingSmsExceptionPatterns(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public String getOutgoingSmsRestriction(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public String getPinCode(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public Bundle getRCSMessage(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isBlockMmsWithStorageEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isBlockSmsWithStorageEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isCallerIDDisplayAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isCopyContactToSimAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isDataAllowedFromSimSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isIncomingCallAllowedFromSimSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isIncomingMmsAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isIncomingSmsAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isIncomingSmsAllowedFromSimSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isLimitNumberOfCallsEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isLimitNumberOfSmsEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isMmsAllowedFromSimSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(110, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isOutgoingCallAllowedFromSimSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isOutgoingMmsAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isOutgoingSmsAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isOutgoingSmsAllowedFromSimSlot(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isRCSEnabled(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isRCSEnabledBySimSlot(ContextInfo contextInfo, int i, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isSimLockedByAdmin(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isSubIdLockedByAdmin(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean isWapPushAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public int lockUnlockCorporateSimCard(ContextInfo contextInfo, String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean removeIncomingCallExceptionPattern(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean removeIncomingCallRestriction(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean removeIncomingSmsExceptionPattern(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean removeIncomingSmsRestriction(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean removeOutgoingCallExceptionPattern(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean removeOutgoingCallRestriction(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean removeOutgoingSmsExceptionPattern(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean removeOutgoingSmsRestriction(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean resetCallsCount(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean resetDataCallLimitCounter(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean resetSmsCount(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean setDataCallLimitEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean setDisclaimerText(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean setEmergencyCallOnly(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean setIncomingCallExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean setIncomingCallRestriction(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean setIncomingSmsExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean setIncomingSmsRestriction(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean setLimitOfDataCalls(ContextInfo contextInfo, long j, long j2, long j3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeLong(j3);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean setLimitOfIncomingCalls(ContextInfo contextInfo, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean setLimitOfIncomingSms(ContextInfo contextInfo, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean setLimitOfOutgoingCalls(ContextInfo contextInfo, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean setLimitOfOutgoingSms(ContextInfo contextInfo, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean setOutgoingCallExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean setOutgoingCallRestriction(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean setOutgoingSmsExceptionPattern(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public boolean setOutgoingSmsRestriction(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public int setRCSEnabled(ContextInfo contextInfo, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public int setRCSEnabledBySimSlot(ContextInfo contextInfo, int i, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public void updateDataLimitState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public void updateDateAndDataCallCounters(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IPhoneRestrictionPolicy.DESCRIPTOR);
        }

        public static IPhoneRestrictionPolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPhoneRestrictionPolicy.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IPhoneRestrictionPolicy)) ? new Proxy(iBinder) : (IPhoneRestrictionPolicy) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPhoneRestrictionPolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPhoneRestrictionPolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String outgoingCallRestriction = getOutgoingCallRestriction(contextInfo, z);
                    parcel2.writeNoException();
                    parcel2.writeString(outgoingCallRestriction);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String incomingCallRestriction = getIncomingCallRestriction(contextInfo2, z2);
                    parcel2.writeNoException();
                    parcel2.writeString(incomingCallRestriction);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRemoveOutgoingCallRestriction = removeOutgoingCallRestriction(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveOutgoingCallRestriction);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRemoveIncomingCallRestriction = removeIncomingCallRestriction(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveIncomingCallRestriction);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddOutgoingCallRestriction = addOutgoingCallRestriction(contextInfo5, string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddOutgoingCallRestriction);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddIncomingCallRestriction = addIncomingCallRestriction(contextInfo6, string2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddIncomingCallRestriction);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean outgoingCallRestriction2 = setOutgoingCallRestriction(contextInfo7, string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outgoingCallRestriction2);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean incomingCallRestriction2 = setIncomingCallRestriction(contextInfo8, string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(incomingCallRestriction2);
                    return true;
                case 9:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCanOutgoingCall = canOutgoingCall(string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanOutgoingCall);
                    return true;
                case 10:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCanIncomingCall = canIncomingCall(string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanIncomingCall);
                    return true;
                case 11:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean emergencyCallOnly = setEmergencyCallOnly(contextInfo9, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(emergencyCallOnly);
                    return true;
                case 12:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean emergencyCallOnly2 = getEmergencyCallOnly(contextInfo10, z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(emergencyCallOnly2);
                    return true;
                case 13:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnableLimitNumberOfCalls = enableLimitNumberOfCalls(contextInfo11, z5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableLimitNumberOfCalls);
                    return true;
                case 14:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsLimitNumberOfCallsEnabled = isLimitNumberOfCallsEnabled(contextInfo12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLimitNumberOfCallsEnabled);
                    return true;
                case 15:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean limitOfIncomingCalls = setLimitOfIncomingCalls(contextInfo13, i3, i4, i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(limitOfIncomingCalls);
                    return true;
                case 16:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int limitOfIncomingCalls2 = getLimitOfIncomingCalls(contextInfo14, i6);
                    parcel2.writeNoException();
                    parcel2.writeInt(limitOfIncomingCalls2);
                    return true;
                case 17:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean limitOfOutgoingCalls = setLimitOfOutgoingCalls(contextInfo15, i7, i8, i9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(limitOfOutgoingCalls);
                    return true;
                case 18:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int limitOfOutgoingCalls2 = getLimitOfOutgoingCalls(contextInfo16, i10);
                    parcel2.writeNoException();
                    parcel2.writeInt(limitOfOutgoingCalls2);
                    return true;
                case 19:
                    boolean zAddNumberOfIncomingCalls = addNumberOfIncomingCalls();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddNumberOfIncomingCalls);
                    return true;
                case 20:
                    boolean zAddNumberOfOutgoingCalls = addNumberOfOutgoingCalls();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddNumberOfOutgoingCalls);
                    return true;
                case 21:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zResetCallsCount = resetCallsCount(contextInfo17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zResetCallsCount);
                    return true;
                case 22:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String outgoingSmsRestriction = getOutgoingSmsRestriction(contextInfo18, z6);
                    parcel2.writeNoException();
                    parcel2.writeString(outgoingSmsRestriction);
                    return true;
                case 23:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String incomingSmsRestriction = getIncomingSmsRestriction(contextInfo19, z7);
                    parcel2.writeNoException();
                    parcel2.writeString(incomingSmsRestriction);
                    return true;
                case 24:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRemoveOutgoingSmsRestriction = removeOutgoingSmsRestriction(contextInfo20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveOutgoingSmsRestriction);
                    return true;
                case 25:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRemoveIncomingSmsRestriction = removeIncomingSmsRestriction(contextInfo21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveIncomingSmsRestriction);
                    return true;
                case 26:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddOutgoingSmsRestriction = addOutgoingSmsRestriction(contextInfo22, string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddOutgoingSmsRestriction);
                    return true;
                case 27:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddIncomingSmsRestriction = addIncomingSmsRestriction(contextInfo23, string8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddIncomingSmsRestriction);
                    return true;
                case 28:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean outgoingSmsRestriction2 = setOutgoingSmsRestriction(contextInfo24, string9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outgoingSmsRestriction2);
                    return true;
                case 29:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean incomingSmsRestriction2 = setIncomingSmsRestriction(contextInfo25, string10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(incomingSmsRestriction2);
                    return true;
                case 30:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCanOutgoingSms = canOutgoingSms(string11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanOutgoingSms);
                    return true;
                case 31:
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCanIncomingSms = canIncomingSms(string12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanIncomingSms);
                    return true;
                case 32:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnableLimitNumberOfSms = enableLimitNumberOfSms(contextInfo26, z8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableLimitNumberOfSms);
                    return true;
                case 33:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsLimitNumberOfSmsEnabled = isLimitNumberOfSmsEnabled(contextInfo27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsLimitNumberOfSmsEnabled);
                    return true;
                case 34:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zResetSmsCount = resetSmsCount(contextInfo28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zResetSmsCount);
                    return true;
                case 35:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean limitOfIncomingSms = setLimitOfIncomingSms(contextInfo29, i11, i12, i13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(limitOfIncomingSms);
                    return true;
                case 36:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int limitOfIncomingSms2 = getLimitOfIncomingSms(contextInfo30, i14);
                    parcel2.writeNoException();
                    parcel2.writeInt(limitOfIncomingSms2);
                    return true;
                case 37:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean limitOfOutgoingSms = setLimitOfOutgoingSms(contextInfo31, i15, i16, i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(limitOfOutgoingSms);
                    return true;
                case 38:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int limitOfOutgoingSms2 = getLimitOfOutgoingSms(contextInfo32, i18);
                    parcel2.writeNoException();
                    parcel2.writeInt(limitOfOutgoingSms2);
                    return true;
                case 39:
                    boolean zAddNumberOfIncomingSms = addNumberOfIncomingSms();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddNumberOfIncomingSms);
                    return true;
                case 40:
                    boolean zAddNumberOfOutgoingSms = addNumberOfOutgoingSms();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddNumberOfOutgoingSms);
                    return true;
                case 41:
                    boolean zDecreaseNumberOfOutgoingSms = decreaseNumberOfOutgoingSms();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDecreaseNumberOfOutgoingSms);
                    return true;
                case 42:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dataCallLimitEnabled = setDataCallLimitEnabled(contextInfo33, z9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dataCallLimitEnabled);
                    return true;
                case 43:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean dataCallLimitEnabled2 = getDataCallLimitEnabled(contextInfo34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dataCallLimitEnabled2);
                    return true;
                case 44:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean limitOfDataCalls = setLimitOfDataCalls(contextInfo35, j, j2, j3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(limitOfDataCalls);
                    return true;
                case 45:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long limitOfDataCalls2 = getLimitOfDataCalls(contextInfo36, i19);
                    parcel2.writeNoException();
                    parcel2.writeLong(limitOfDataCalls2);
                    return true;
                case 46:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zResetDataCallLimitCounter = resetDataCallLimitCounter(contextInfo37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zResetDataCallLimitCounter);
                    return true;
                case 47:
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zCheckEnableUseOfPacketData = checkEnableUseOfPacketData(z10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckEnableUseOfPacketData);
                    return true;
                case 48:
                    boolean zCheckDataCallLimit = checkDataCallLimit();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckDataCallLimit);
                    return true;
                case 49:
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateDateAndDataCallCounters(j4);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    updateDataLimitState();
                    parcel2.writeNoException();
                    return true;
                case 51:
                    ContextInfo contextInfo38 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowIncomingSms = allowIncomingSms(contextInfo38, z11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowIncomingSms);
                    return true;
                case 52:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowOutgoingSms = allowOutgoingSms(contextInfo39, z12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowOutgoingSms);
                    return true;
                case 53:
                    ContextInfo contextInfo40 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsIncomingSmsAllowed = isIncomingSmsAllowed(contextInfo40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsIncomingSmsAllowed);
                    return true;
                case 54:
                    ContextInfo contextInfo41 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsOutgoingSmsAllowed = isOutgoingSmsAllowed(contextInfo41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOutgoingSmsAllowed);
                    return true;
                case 55:
                    ContextInfo contextInfo42 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowIncomingMms = allowIncomingMms(contextInfo42, z13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowIncomingMms);
                    return true;
                case 56:
                    ContextInfo contextInfo43 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowOutgoingMms = allowOutgoingMms(contextInfo43, z14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowOutgoingMms);
                    return true;
                case 57:
                    ContextInfo contextInfo44 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsIncomingMmsAllowed = isIncomingMmsAllowed(contextInfo44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsIncomingMmsAllowed);
                    return true;
                case 58:
                    ContextInfo contextInfo45 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsOutgoingMmsAllowed = isOutgoingMmsAllowed(contextInfo45);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOutgoingMmsAllowed);
                    return true;
                case 59:
                    ContextInfo contextInfo46 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zBlockSmsWithStorage = blockSmsWithStorage(contextInfo46, z15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zBlockSmsWithStorage);
                    return true;
                case 60:
                    ContextInfo contextInfo47 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsBlockSmsWithStorageEnabled = isBlockSmsWithStorageEnabled(contextInfo47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBlockSmsWithStorageEnabled);
                    return true;
                case 61:
                    ContextInfo contextInfo48 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zBlockMmsWithStorage = blockMmsWithStorage(contextInfo48, z16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zBlockMmsWithStorage);
                    return true;
                case 62:
                    ContextInfo contextInfo49 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsBlockMmsWithStorageEnabled = isBlockMmsWithStorageEnabled(contextInfo49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBlockMmsWithStorageEnabled);
                    return true;
                case 63:
                    ContextInfo contextInfo50 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearStoredBlockedSms = clearStoredBlockedSms(contextInfo50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearStoredBlockedSms);
                    return true;
                case 64:
                    ContextInfo contextInfo51 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearStoredBlockedMms = clearStoredBlockedMms(contextInfo51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearStoredBlockedMms);
                    return true;
                case 65:
                    ContextInfo contextInfo52 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowWapPush = allowWapPush(contextInfo52, z17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowWapPush);
                    return true;
                case 66:
                    ContextInfo contextInfo53 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsWapPushAllowed = isWapPushAllowed(contextInfo53);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsWapPushAllowed);
                    return true;
                case 67:
                    ContextInfo contextInfo54 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowCallerIDDisplay = allowCallerIDDisplay(contextInfo54, z18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowCallerIDDisplay);
                    return true;
                case 68:
                    ContextInfo contextInfo55 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsCallerIDDisplayAllowed = isCallerIDDisplayAllowed(contextInfo55);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCallerIDDisplayAllowed);
                    return true;
                case 69:
                    ContextInfo contextInfo56 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    boolean z19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iLockUnlockCorporateSimCard = lockUnlockCorporateSimCard(contextInfo56, string13, string14, z19);
                    parcel2.writeNoException();
                    parcel2.writeInt(iLockUnlockCorporateSimCard);
                    return true;
                case 70:
                    ContextInfo contextInfo57 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iChangeSimPinCode = changeSimPinCode(contextInfo57, string15, string16, string17);
                    parcel2.writeNoException();
                    parcel2.writeInt(iChangeSimPinCode);
                    return true;
                case 71:
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsSimLockedByAdmin = isSimLockedByAdmin(string18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSimLockedByAdmin);
                    return true;
                case 72:
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String pinCode = getPinCode(string19);
                    parcel2.writeNoException();
                    parcel2.writeString(pinCode);
                    return true;
                case 73:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSubIdLockedByAdmin = isSubIdLockedByAdmin(i20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSubIdLockedByAdmin);
                    return true;
                case 74:
                    ContextInfo contextInfo58 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowCopyContactToSim = allowCopyContactToSim(contextInfo58, z20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowCopyContactToSim);
                    return true;
                case 75:
                    ContextInfo contextInfo59 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsCopyContactToSimAllowed = isCopyContactToSimAllowed(contextInfo59);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCopyContactToSimAllowed);
                    return true;
                case 76:
                    ContextInfo contextInfo60 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String outgoingCallExceptionPatterns = getOutgoingCallExceptionPatterns(contextInfo60);
                    parcel2.writeNoException();
                    parcel2.writeString(outgoingCallExceptionPatterns);
                    return true;
                case 77:
                    ContextInfo contextInfo61 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String incomingCallExceptionPatterns = getIncomingCallExceptionPatterns(contextInfo61);
                    parcel2.writeNoException();
                    parcel2.writeString(incomingCallExceptionPatterns);
                    return true;
                case 78:
                    ContextInfo contextInfo62 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRemoveOutgoingCallExceptionPattern = removeOutgoingCallExceptionPattern(contextInfo62);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveOutgoingCallExceptionPattern);
                    return true;
                case 79:
                    ContextInfo contextInfo63 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRemoveIncomingCallExceptionPattern = removeIncomingCallExceptionPattern(contextInfo63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveIncomingCallExceptionPattern);
                    return true;
                case 80:
                    ContextInfo contextInfo64 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddOutgoingCallExceptionPattern = addOutgoingCallExceptionPattern(contextInfo64, string20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddOutgoingCallExceptionPattern);
                    return true;
                case 81:
                    ContextInfo contextInfo65 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddIncomingCallExceptionPattern = addIncomingCallExceptionPattern(contextInfo65, string21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddIncomingCallExceptionPattern);
                    return true;
                case 82:
                    ContextInfo contextInfo66 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean outgoingCallExceptionPattern = setOutgoingCallExceptionPattern(contextInfo66, string22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outgoingCallExceptionPattern);
                    return true;
                case 83:
                    ContextInfo contextInfo67 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean incomingCallExceptionPattern = setIncomingCallExceptionPattern(contextInfo67, string23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(incomingCallExceptionPattern);
                    return true;
                case 84:
                    ContextInfo contextInfo68 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String outgoingSmsExceptionPatterns = getOutgoingSmsExceptionPatterns(contextInfo68);
                    parcel2.writeNoException();
                    parcel2.writeString(outgoingSmsExceptionPatterns);
                    return true;
                case 85:
                    ContextInfo contextInfo69 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String incomingSmsExceptionPatterns = getIncomingSmsExceptionPatterns(contextInfo69);
                    parcel2.writeNoException();
                    parcel2.writeString(incomingSmsExceptionPatterns);
                    return true;
                case 86:
                    ContextInfo contextInfo70 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRemoveOutgoingSmsExceptionPattern = removeOutgoingSmsExceptionPattern(contextInfo70);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveOutgoingSmsExceptionPattern);
                    return true;
                case 87:
                    ContextInfo contextInfo71 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRemoveIncomingSmsExceptionPattern = removeIncomingSmsExceptionPattern(contextInfo71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveIncomingSmsExceptionPattern);
                    return true;
                case 88:
                    ContextInfo contextInfo72 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddOutgoingSmsExceptionPattern = addOutgoingSmsExceptionPattern(contextInfo72, string24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddOutgoingSmsExceptionPattern);
                    return true;
                case 89:
                    ContextInfo contextInfo73 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddIncomingSmsExceptionPattern = addIncomingSmsExceptionPattern(contextInfo73, string25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddIncomingSmsExceptionPattern);
                    return true;
                case 90:
                    ContextInfo contextInfo74 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean outgoingSmsExceptionPattern = setOutgoingSmsExceptionPattern(contextInfo74, string26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outgoingSmsExceptionPattern);
                    return true;
                case 91:
                    ContextInfo contextInfo75 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean incomingSmsExceptionPattern = setIncomingSmsExceptionPattern(contextInfo75, string27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(incomingSmsExceptionPattern);
                    return true;
                case 92:
                    ContextInfo contextInfo76 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean disclaimerText = setDisclaimerText(contextInfo76, string28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disclaimerText);
                    return true;
                case 93:
                    ContextInfo contextInfo77 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String disclaimerText2 = getDisclaimerText(contextInfo77);
                    parcel2.writeNoException();
                    parcel2.writeString(disclaimerText2);
                    return true;
                case 94:
                    ContextInfo contextInfo78 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i21 = parcel.readInt();
                    boolean z21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int rCSEnabled = setRCSEnabled(contextInfo78, i21, z21);
                    parcel2.writeNoException();
                    parcel2.writeInt(rCSEnabled);
                    return true;
                case 95:
                    ContextInfo contextInfo79 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i22 = parcel.readInt();
                    boolean z22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zIsRCSEnabled = isRCSEnabled(contextInfo79, i22, z22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRCSEnabled);
                    return true;
                case 96:
                    ContextInfo contextInfo80 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    Bundle rCSMessage = getRCSMessage(contextInfo80, j5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rCSMessage, 1);
                    return true;
                case 97:
                    ContextInfo contextInfo81 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i23 = parcel.readInt();
                    boolean z23 = parcel.readBoolean();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int rCSEnabledBySimSlot = setRCSEnabledBySimSlot(contextInfo81, i23, z23, i24);
                    parcel2.writeNoException();
                    parcel2.writeInt(rCSEnabledBySimSlot);
                    return true;
                case 98:
                    ContextInfo contextInfo82 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i25 = parcel.readInt();
                    boolean z24 = parcel.readBoolean();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsRCSEnabledBySimSlot = isRCSEnabledBySimSlot(contextInfo82, i25, z24, i26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRCSEnabledBySimSlot);
                    return true;
                case 99:
                    ContextInfo contextInfo83 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i27 = parcel.readInt();
                    boolean z25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iAllowDataNetworkFromSimSlot = allowDataNetworkFromSimSlot(contextInfo83, i27, z25);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAllowDataNetworkFromSimSlot);
                    return true;
                case 100:
                    ContextInfo contextInfo84 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i28 = parcel.readInt();
                    boolean z26 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iAllowIncomingCallFromSimSlot = allowIncomingCallFromSimSlot(contextInfo84, i28, z26);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAllowIncomingCallFromSimSlot);
                    return true;
                case 101:
                    ContextInfo contextInfo85 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i29 = parcel.readInt();
                    boolean z27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iAllowOutgoingCallFromSimSlot = allowOutgoingCallFromSimSlot(contextInfo85, i29, z27);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAllowOutgoingCallFromSimSlot);
                    return true;
                case 102:
                    ContextInfo contextInfo86 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i30 = parcel.readInt();
                    boolean z28 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iAllowIncomingSmsFromSimSlot = allowIncomingSmsFromSimSlot(contextInfo86, i30, z28);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAllowIncomingSmsFromSimSlot);
                    return true;
                case 103:
                    ContextInfo contextInfo87 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i31 = parcel.readInt();
                    boolean z29 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iAllowOutgoingSmsFromSimSlot = allowOutgoingSmsFromSimSlot(contextInfo87, i31, z29);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAllowOutgoingSmsFromSimSlot);
                    return true;
                case 104:
                    ContextInfo contextInfo88 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i32 = parcel.readInt();
                    boolean z30 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iAllowMmsFromSimSlot = allowMmsFromSimSlot(contextInfo88, i32, z30);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAllowMmsFromSimSlot);
                    return true;
                case 105:
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDataAllowedFromSimSlot = isDataAllowedFromSimSlot(i33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDataAllowedFromSimSlot);
                    return true;
                case 106:
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsIncomingCallAllowedFromSimSlot = isIncomingCallAllowedFromSimSlot(i34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsIncomingCallAllowedFromSimSlot);
                    return true;
                case 107:
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsOutgoingCallAllowedFromSimSlot = isOutgoingCallAllowedFromSimSlot(i35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOutgoingCallAllowedFromSimSlot);
                    return true;
                case 108:
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsIncomingSmsAllowedFromSimSlot = isIncomingSmsAllowedFromSimSlot(i36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsIncomingSmsAllowedFromSimSlot);
                    return true;
                case 109:
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsOutgoingSmsAllowedFromSimSlot = isOutgoingSmsAllowedFromSimSlot(i37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOutgoingSmsAllowedFromSimSlot);
                    return true;
                case 110:
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsMmsAllowedFromSimSlot = isMmsAllowedFromSimSlot(i38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMmsAllowedFromSimSlot);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
