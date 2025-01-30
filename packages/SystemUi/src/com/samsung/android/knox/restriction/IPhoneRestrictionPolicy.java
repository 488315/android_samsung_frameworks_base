package com.samsung.android.knox.restriction;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IPhoneRestrictionPolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.restriction.IPhoneRestrictionPolicy";

    boolean addIncomingCallExceptionPattern(ContextInfo contextInfo, String str);

    boolean addIncomingCallRestriction(ContextInfo contextInfo, String str);

    boolean addIncomingSmsExceptionPattern(ContextInfo contextInfo, String str);

    boolean addIncomingSmsRestriction(ContextInfo contextInfo, String str);

    boolean addNumberOfIncomingCalls();

    boolean addNumberOfIncomingSms();

    boolean addNumberOfOutgoingCalls();

    boolean addNumberOfOutgoingSms();

    boolean addOutgoingCallExceptionPattern(ContextInfo contextInfo, String str);

    boolean addOutgoingCallRestriction(ContextInfo contextInfo, String str);

    boolean addOutgoingSmsExceptionPattern(ContextInfo contextInfo, String str);

    boolean addOutgoingSmsRestriction(ContextInfo contextInfo, String str);

    boolean allowCallerIDDisplay(ContextInfo contextInfo, boolean z);

    boolean allowCopyContactToSim(ContextInfo contextInfo, boolean z);

    int allowDataNetworkFromSimSlot(ContextInfo contextInfo, int i, boolean z);

    int allowIncomingCallFromSimSlot(ContextInfo contextInfo, int i, boolean z);

    boolean allowIncomingMms(ContextInfo contextInfo, boolean z);

    boolean allowIncomingSms(ContextInfo contextInfo, boolean z);

    int allowIncomingSmsFromSimSlot(ContextInfo contextInfo, int i, boolean z);

    int allowMmsFromSimSlot(ContextInfo contextInfo, int i, boolean z);

    int allowOutgoingCallFromSimSlot(ContextInfo contextInfo, int i, boolean z);

    boolean allowOutgoingMms(ContextInfo contextInfo, boolean z);

    boolean allowOutgoingSms(ContextInfo contextInfo, boolean z);

    int allowOutgoingSmsFromSimSlot(ContextInfo contextInfo, int i, boolean z);

    boolean allowWapPush(ContextInfo contextInfo, boolean z);

    boolean blockMmsWithStorage(ContextInfo contextInfo, boolean z);

    boolean blockSmsWithStorage(ContextInfo contextInfo, boolean z);

    boolean canIncomingCall(String str);

    boolean canIncomingSms(String str);

    boolean canOutgoingCall(String str);

    boolean canOutgoingSms(String str);

    int changeSimPinCode(ContextInfo contextInfo, String str, String str2, String str3);

    boolean checkDataCallLimit();

    boolean checkEnableUseOfPacketData(boolean z);

    boolean clearStoredBlockedMms(ContextInfo contextInfo);

    boolean clearStoredBlockedSms(ContextInfo contextInfo);

    boolean decreaseNumberOfOutgoingSms();

    boolean enableLimitNumberOfCalls(ContextInfo contextInfo, boolean z);

    boolean enableLimitNumberOfSms(ContextInfo contextInfo, boolean z);

    boolean getDataCallLimitEnabled(ContextInfo contextInfo);

    String getDisclaimerText(ContextInfo contextInfo);

    boolean getEmergencyCallOnly(ContextInfo contextInfo, boolean z);

    String getIncomingCallExceptionPatterns(ContextInfo contextInfo);

    String getIncomingCallRestriction(ContextInfo contextInfo, boolean z);

    String getIncomingSmsExceptionPatterns(ContextInfo contextInfo);

    String getIncomingSmsRestriction(ContextInfo contextInfo, boolean z);

    long getLimitOfDataCalls(ContextInfo contextInfo, int i);

    int getLimitOfIncomingCalls(ContextInfo contextInfo, int i);

    int getLimitOfIncomingSms(ContextInfo contextInfo, int i);

    int getLimitOfOutgoingCalls(ContextInfo contextInfo, int i);

    int getLimitOfOutgoingSms(ContextInfo contextInfo, int i);

    String getOutgoingCallExceptionPatterns(ContextInfo contextInfo);

    String getOutgoingCallRestriction(ContextInfo contextInfo, boolean z);

    String getOutgoingSmsExceptionPatterns(ContextInfo contextInfo);

    String getOutgoingSmsRestriction(ContextInfo contextInfo, boolean z);

    String getPinCode(String str);

    Bundle getRCSMessage(ContextInfo contextInfo, long j);

    boolean isBlockMmsWithStorageEnabled(ContextInfo contextInfo);

    boolean isBlockSmsWithStorageEnabled(ContextInfo contextInfo);

    boolean isCallerIDDisplayAllowed(ContextInfo contextInfo);

    boolean isCopyContactToSimAllowed(ContextInfo contextInfo);

    boolean isDataAllowedFromSimSlot(int i);

    boolean isIncomingCallAllowedFromSimSlot(int i);

    boolean isIncomingMmsAllowed(ContextInfo contextInfo);

    boolean isIncomingSmsAllowed(ContextInfo contextInfo);

    boolean isIncomingSmsAllowedFromSimSlot(int i);

    boolean isLimitNumberOfCallsEnabled(ContextInfo contextInfo);

    boolean isLimitNumberOfSmsEnabled(ContextInfo contextInfo);

    boolean isMmsAllowedFromSimSlot(int i);

    boolean isOutgoingCallAllowedFromSimSlot(int i);

    boolean isOutgoingMmsAllowed(ContextInfo contextInfo);

    boolean isOutgoingSmsAllowed(ContextInfo contextInfo);

    boolean isOutgoingSmsAllowedFromSimSlot(int i);

    boolean isRCSEnabled(ContextInfo contextInfo, int i, boolean z);

    boolean isRCSEnabledBySimSlot(ContextInfo contextInfo, int i, boolean z, int i2);

    boolean isSimLockedByAdmin(String str);

    boolean isSubIdLockedByAdmin(int i);

    boolean isWapPushAllowed(ContextInfo contextInfo);

    int lockUnlockCorporateSimCard(ContextInfo contextInfo, String str, String str2, boolean z);

    boolean removeIncomingCallExceptionPattern(ContextInfo contextInfo);

    boolean removeIncomingCallRestriction(ContextInfo contextInfo);

    boolean removeIncomingSmsExceptionPattern(ContextInfo contextInfo);

    boolean removeIncomingSmsRestriction(ContextInfo contextInfo);

    boolean removeOutgoingCallExceptionPattern(ContextInfo contextInfo);

    boolean removeOutgoingCallRestriction(ContextInfo contextInfo);

    boolean removeOutgoingSmsExceptionPattern(ContextInfo contextInfo);

    boolean removeOutgoingSmsRestriction(ContextInfo contextInfo);

    boolean resetCallsCount(ContextInfo contextInfo);

    boolean resetDataCallLimitCounter(ContextInfo contextInfo);

    boolean resetSmsCount(ContextInfo contextInfo);

    boolean setDataCallLimitEnabled(ContextInfo contextInfo, boolean z);

    boolean setDisclaimerText(ContextInfo contextInfo, String str);

    boolean setEmergencyCallOnly(ContextInfo contextInfo, boolean z);

    boolean setIncomingCallExceptionPattern(ContextInfo contextInfo, String str);

    boolean setIncomingCallRestriction(ContextInfo contextInfo, String str);

    boolean setIncomingSmsExceptionPattern(ContextInfo contextInfo, String str);

    boolean setIncomingSmsRestriction(ContextInfo contextInfo, String str);

    boolean setLimitOfDataCalls(ContextInfo contextInfo, long j, long j2, long j3);

    boolean setLimitOfIncomingCalls(ContextInfo contextInfo, int i, int i2, int i3);

    boolean setLimitOfIncomingSms(ContextInfo contextInfo, int i, int i2, int i3);

    boolean setLimitOfOutgoingCalls(ContextInfo contextInfo, int i, int i2, int i3);

    boolean setLimitOfOutgoingSms(ContextInfo contextInfo, int i, int i2, int i3);

    boolean setOutgoingCallExceptionPattern(ContextInfo contextInfo, String str);

    boolean setOutgoingCallRestriction(ContextInfo contextInfo, String str);

    boolean setOutgoingSmsExceptionPattern(ContextInfo contextInfo, String str);

    boolean setOutgoingSmsRestriction(ContextInfo contextInfo, String str);

    int setRCSEnabled(ContextInfo contextInfo, int i, boolean z);

    int setRCSEnabledBySimSlot(ContextInfo contextInfo, int i, boolean z, int i2);

    void updateDataLimitState();

    void updateDateAndDataCallCounters(long j);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IPhoneRestrictionPolicy {
        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean addIncomingCallExceptionPattern(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean addIncomingCallRestriction(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean addIncomingSmsExceptionPattern(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean addIncomingSmsRestriction(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean addNumberOfIncomingCalls() {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean addNumberOfIncomingSms() {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean addNumberOfOutgoingCalls() {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean addNumberOfOutgoingSms() {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean addOutgoingCallExceptionPattern(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean addOutgoingCallRestriction(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean addOutgoingSmsExceptionPattern(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean addOutgoingSmsRestriction(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean allowCallerIDDisplay(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean allowCopyContactToSim(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final int allowDataNetworkFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final int allowIncomingCallFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean allowIncomingMms(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean allowIncomingSms(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final int allowIncomingSmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final int allowMmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final int allowOutgoingCallFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean allowOutgoingMms(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean allowOutgoingSms(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final int allowOutgoingSmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean allowWapPush(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean blockMmsWithStorage(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean blockSmsWithStorage(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean canIncomingCall(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean canIncomingSms(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean canOutgoingCall(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean canOutgoingSms(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final int changeSimPinCode(ContextInfo contextInfo, String str, String str2, String str3) {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean checkDataCallLimit() {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean checkEnableUseOfPacketData(boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean clearStoredBlockedMms(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean clearStoredBlockedSms(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean decreaseNumberOfOutgoingSms() {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean enableLimitNumberOfCalls(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean enableLimitNumberOfSms(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean getDataCallLimitEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final String getDisclaimerText(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean getEmergencyCallOnly(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final String getIncomingCallExceptionPatterns(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final String getIncomingCallRestriction(ContextInfo contextInfo, boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final String getIncomingSmsExceptionPatterns(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final String getIncomingSmsRestriction(ContextInfo contextInfo, boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final long getLimitOfDataCalls(ContextInfo contextInfo, int i) {
            return 0L;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final int getLimitOfIncomingCalls(ContextInfo contextInfo, int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final int getLimitOfIncomingSms(ContextInfo contextInfo, int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final int getLimitOfOutgoingCalls(ContextInfo contextInfo, int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final int getLimitOfOutgoingSms(ContextInfo contextInfo, int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final String getOutgoingCallExceptionPatterns(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final String getOutgoingCallRestriction(ContextInfo contextInfo, boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final String getOutgoingSmsExceptionPatterns(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final String getOutgoingSmsRestriction(ContextInfo contextInfo, boolean z) {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final String getPinCode(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final Bundle getRCSMessage(ContextInfo contextInfo, long j) {
            return null;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isBlockMmsWithStorageEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isBlockSmsWithStorageEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isCallerIDDisplayAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isCopyContactToSimAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isDataAllowedFromSimSlot(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isIncomingCallAllowedFromSimSlot(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isIncomingMmsAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isIncomingSmsAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isIncomingSmsAllowedFromSimSlot(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isLimitNumberOfCallsEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isLimitNumberOfSmsEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isMmsAllowedFromSimSlot(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isOutgoingCallAllowedFromSimSlot(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isOutgoingMmsAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isOutgoingSmsAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isOutgoingSmsAllowedFromSimSlot(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isRCSEnabled(ContextInfo contextInfo, int i, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isRCSEnabledBySimSlot(ContextInfo contextInfo, int i, boolean z, int i2) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isSimLockedByAdmin(String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isSubIdLockedByAdmin(int i) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean isWapPushAllowed(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final int lockUnlockCorporateSimCard(ContextInfo contextInfo, String str, String str2, boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean removeIncomingCallExceptionPattern(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean removeIncomingCallRestriction(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean removeIncomingSmsExceptionPattern(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean removeIncomingSmsRestriction(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean removeOutgoingCallExceptionPattern(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean removeOutgoingCallRestriction(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean removeOutgoingSmsExceptionPattern(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean removeOutgoingSmsRestriction(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean resetCallsCount(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean resetDataCallLimitCounter(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean resetSmsCount(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean setDataCallLimitEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean setDisclaimerText(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean setEmergencyCallOnly(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean setIncomingCallExceptionPattern(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean setIncomingCallRestriction(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean setIncomingSmsExceptionPattern(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean setIncomingSmsRestriction(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean setLimitOfDataCalls(ContextInfo contextInfo, long j, long j2, long j3) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean setLimitOfIncomingCalls(ContextInfo contextInfo, int i, int i2, int i3) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean setLimitOfIncomingSms(ContextInfo contextInfo, int i, int i2, int i3) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean setLimitOfOutgoingCalls(ContextInfo contextInfo, int i, int i2, int i3) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean setLimitOfOutgoingSms(ContextInfo contextInfo, int i, int i2, int i3) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean setOutgoingCallExceptionPattern(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean setOutgoingCallRestriction(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean setOutgoingSmsExceptionPattern(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final boolean setOutgoingSmsRestriction(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final int setRCSEnabled(ContextInfo contextInfo, int i, boolean z) {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final int setRCSEnabledBySimSlot(ContextInfo contextInfo, int i, boolean z, int i2) {
            return 0;
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final void updateDataLimitState() {
        }

        @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
        public final void updateDateAndDataCallCounters(long j) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IPhoneRestrictionPolicy {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IPhoneRestrictionPolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean addIncomingCallExceptionPattern(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean addIncomingCallRestriction(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean addIncomingSmsExceptionPattern(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean addIncomingSmsRestriction(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean addNumberOfIncomingCalls() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean addNumberOfIncomingSms() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean addNumberOfOutgoingCalls() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean addNumberOfOutgoingSms() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean addOutgoingCallExceptionPattern(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean addOutgoingCallRestriction(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean addOutgoingSmsExceptionPattern(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean addOutgoingSmsRestriction(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean allowCallerIDDisplay(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean allowCopyContactToSim(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final int allowDataNetworkFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final int allowIncomingCallFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean allowIncomingMms(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean allowIncomingSms(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final int allowIncomingSmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final int allowMmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final int allowOutgoingCallFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean allowOutgoingMms(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean allowOutgoingSms(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final int allowOutgoingSmsFromSimSlot(ContextInfo contextInfo, int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean allowWapPush(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean blockMmsWithStorage(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean blockSmsWithStorage(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean canIncomingCall(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean canIncomingSms(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean canOutgoingCall(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean canOutgoingSms(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final int changeSimPinCode(ContextInfo contextInfo, String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean checkDataCallLimit() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean checkEnableUseOfPacketData(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean clearStoredBlockedMms(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean clearStoredBlockedSms(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean decreaseNumberOfOutgoingSms() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean enableLimitNumberOfCalls(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean enableLimitNumberOfSms(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean getDataCallLimitEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final String getDisclaimerText(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean getEmergencyCallOnly(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final String getIncomingCallExceptionPatterns(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final String getIncomingCallRestriction(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final String getIncomingSmsExceptionPatterns(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final String getIncomingSmsRestriction(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IPhoneRestrictionPolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final long getLimitOfDataCalls(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final int getLimitOfIncomingCalls(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final int getLimitOfIncomingSms(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final int getLimitOfOutgoingCalls(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final int getLimitOfOutgoingSms(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final String getOutgoingCallExceptionPatterns(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final String getOutgoingCallRestriction(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final String getOutgoingSmsExceptionPatterns(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final String getOutgoingSmsRestriction(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final String getPinCode(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final Bundle getRCSMessage(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isBlockMmsWithStorageEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isBlockSmsWithStorageEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isCallerIDDisplayAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isCopyContactToSimAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isDataAllowedFromSimSlot(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isIncomingCallAllowedFromSimSlot(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isIncomingMmsAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isIncomingSmsAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isIncomingSmsAllowedFromSimSlot(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isLimitNumberOfCallsEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isLimitNumberOfSmsEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isMmsAllowedFromSimSlot(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isOutgoingCallAllowedFromSimSlot(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isOutgoingMmsAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isOutgoingSmsAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isOutgoingSmsAllowedFromSimSlot(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isRCSEnabled(ContextInfo contextInfo, int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isRCSEnabledBySimSlot(ContextInfo contextInfo, int i, boolean z, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isSimLockedByAdmin(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isSubIdLockedByAdmin(int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean isWapPushAllowed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final int lockUnlockCorporateSimCard(ContextInfo contextInfo, String str, String str2, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean removeIncomingCallExceptionPattern(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean removeIncomingCallRestriction(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean removeIncomingSmsExceptionPattern(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean removeIncomingSmsRestriction(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean removeOutgoingCallExceptionPattern(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean removeOutgoingCallRestriction(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean removeOutgoingSmsExceptionPattern(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean removeOutgoingSmsRestriction(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean resetCallsCount(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean resetDataCallLimitCounter(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean resetSmsCount(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean setDataCallLimitEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean setDisclaimerText(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean setEmergencyCallOnly(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean setIncomingCallExceptionPattern(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean setIncomingCallRestriction(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean setIncomingSmsExceptionPattern(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean setIncomingSmsRestriction(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean setLimitOfDataCalls(ContextInfo contextInfo, long j, long j2, long j3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeLong(j3);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean setLimitOfIncomingCalls(ContextInfo contextInfo, int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean setLimitOfIncomingSms(ContextInfo contextInfo, int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean setLimitOfOutgoingCalls(ContextInfo contextInfo, int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean setLimitOfOutgoingSms(ContextInfo contextInfo, int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean setOutgoingCallExceptionPattern(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean setOutgoingCallRestriction(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean setOutgoingSmsExceptionPattern(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final boolean setOutgoingSmsRestriction(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final int setRCSEnabled(ContextInfo contextInfo, int i, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final int setRCSEnabledBySimSlot(ContextInfo contextInfo, int i, boolean z, int i2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final void updateDataLimitState() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.restriction.IPhoneRestrictionPolicy
            public final void updateDateAndDataCallCounters(long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPhoneRestrictionPolicy.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPhoneRestrictionPolicy.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IPhoneRestrictionPolicy)) ? new Proxy(iBinder) : (IPhoneRestrictionPolicy) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String outgoingCallRestriction = getOutgoingCallRestriction(contextInfo, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeString(outgoingCallRestriction);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String incomingCallRestriction = getIncomingCallRestriction(contextInfo2, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeString(incomingCallRestriction);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean removeOutgoingCallRestriction = removeOutgoingCallRestriction(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeOutgoingCallRestriction);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean removeIncomingCallRestriction = removeIncomingCallRestriction(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeIncomingCallRestriction);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addOutgoingCallRestriction = addOutgoingCallRestriction(contextInfo5, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addOutgoingCallRestriction);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addIncomingCallRestriction = addIncomingCallRestriction(contextInfo6, readString2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addIncomingCallRestriction);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean outgoingCallRestriction2 = setOutgoingCallRestriction(contextInfo7, readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outgoingCallRestriction2);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean incomingCallRestriction2 = setIncomingCallRestriction(contextInfo8, readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(incomingCallRestriction2);
                    return true;
                case 9:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canOutgoingCall = canOutgoingCall(readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canOutgoingCall);
                    return true;
                case 10:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canIncomingCall = canIncomingCall(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canIncomingCall);
                    return true;
                case 11:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean emergencyCallOnly = setEmergencyCallOnly(contextInfo9, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(emergencyCallOnly);
                    return true;
                case 12:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean emergencyCallOnly2 = getEmergencyCallOnly(contextInfo10, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(emergencyCallOnly2);
                    return true;
                case 13:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableLimitNumberOfCalls = enableLimitNumberOfCalls(contextInfo11, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableLimitNumberOfCalls);
                    return true;
                case 14:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isLimitNumberOfCallsEnabled = isLimitNumberOfCallsEnabled(contextInfo12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLimitNumberOfCallsEnabled);
                    return true;
                case 15:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean limitOfIncomingCalls = setLimitOfIncomingCalls(contextInfo13, readInt, readInt2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(limitOfIncomingCalls);
                    return true;
                case 16:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int limitOfIncomingCalls2 = getLimitOfIncomingCalls(contextInfo14, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeInt(limitOfIncomingCalls2);
                    return true;
                case 17:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean limitOfOutgoingCalls = setLimitOfOutgoingCalls(contextInfo15, readInt5, readInt6, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(limitOfOutgoingCalls);
                    return true;
                case 18:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int limitOfOutgoingCalls2 = getLimitOfOutgoingCalls(contextInfo16, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(limitOfOutgoingCalls2);
                    return true;
                case 19:
                    boolean addNumberOfIncomingCalls = addNumberOfIncomingCalls();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addNumberOfIncomingCalls);
                    return true;
                case 20:
                    boolean addNumberOfOutgoingCalls = addNumberOfOutgoingCalls();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addNumberOfOutgoingCalls);
                    return true;
                case 21:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean resetCallsCount = resetCallsCount(contextInfo17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetCallsCount);
                    return true;
                case 22:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String outgoingSmsRestriction = getOutgoingSmsRestriction(contextInfo18, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeString(outgoingSmsRestriction);
                    return true;
                case 23:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String incomingSmsRestriction = getIncomingSmsRestriction(contextInfo19, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeString(incomingSmsRestriction);
                    return true;
                case 24:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean removeOutgoingSmsRestriction = removeOutgoingSmsRestriction(contextInfo20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeOutgoingSmsRestriction);
                    return true;
                case 25:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean removeIncomingSmsRestriction = removeIncomingSmsRestriction(contextInfo21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeIncomingSmsRestriction);
                    return true;
                case 26:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addOutgoingSmsRestriction = addOutgoingSmsRestriction(contextInfo22, readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addOutgoingSmsRestriction);
                    return true;
                case 27:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addIncomingSmsRestriction = addIncomingSmsRestriction(contextInfo23, readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addIncomingSmsRestriction);
                    return true;
                case 28:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean outgoingSmsRestriction2 = setOutgoingSmsRestriction(contextInfo24, readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outgoingSmsRestriction2);
                    return true;
                case 29:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean incomingSmsRestriction2 = setIncomingSmsRestriction(contextInfo25, readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(incomingSmsRestriction2);
                    return true;
                case 30:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canOutgoingSms = canOutgoingSms(readString11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canOutgoingSms);
                    return true;
                case 31:
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canIncomingSms = canIncomingSms(readString12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canIncomingSms);
                    return true;
                case 32:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableLimitNumberOfSms = enableLimitNumberOfSms(contextInfo26, readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableLimitNumberOfSms);
                    return true;
                case 33:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isLimitNumberOfSmsEnabled = isLimitNumberOfSmsEnabled(contextInfo27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLimitNumberOfSmsEnabled);
                    return true;
                case 34:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean resetSmsCount = resetSmsCount(contextInfo28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetSmsCount);
                    return true;
                case 35:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean limitOfIncomingSms = setLimitOfIncomingSms(contextInfo29, readInt9, readInt10, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(limitOfIncomingSms);
                    return true;
                case 36:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int limitOfIncomingSms2 = getLimitOfIncomingSms(contextInfo30, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeInt(limitOfIncomingSms2);
                    return true;
                case 37:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean limitOfOutgoingSms = setLimitOfOutgoingSms(contextInfo31, readInt13, readInt14, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(limitOfOutgoingSms);
                    return true;
                case 38:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int limitOfOutgoingSms2 = getLimitOfOutgoingSms(contextInfo32, readInt16);
                    parcel2.writeNoException();
                    parcel2.writeInt(limitOfOutgoingSms2);
                    return true;
                case 39:
                    boolean addNumberOfIncomingSms = addNumberOfIncomingSms();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addNumberOfIncomingSms);
                    return true;
                case 40:
                    boolean addNumberOfOutgoingSms = addNumberOfOutgoingSms();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addNumberOfOutgoingSms);
                    return true;
                case 41:
                    boolean decreaseNumberOfOutgoingSms = decreaseNumberOfOutgoingSms();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(decreaseNumberOfOutgoingSms);
                    return true;
                case 42:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dataCallLimitEnabled = setDataCallLimitEnabled(contextInfo33, readBoolean9);
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
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean limitOfDataCalls = setLimitOfDataCalls(contextInfo35, readLong, readLong2, readLong3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(limitOfDataCalls);
                    return true;
                case 45:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long limitOfDataCalls2 = getLimitOfDataCalls(contextInfo36, readInt17);
                    parcel2.writeNoException();
                    parcel2.writeLong(limitOfDataCalls2);
                    return true;
                case 46:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean resetDataCallLimitCounter = resetDataCallLimitCounter(contextInfo37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetDataCallLimitCounter);
                    return true;
                case 47:
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean checkEnableUseOfPacketData = checkEnableUseOfPacketData(readBoolean10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkEnableUseOfPacketData);
                    return true;
                case 48:
                    boolean checkDataCallLimit = checkDataCallLimit();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkDataCallLimit);
                    return true;
                case 49:
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    updateDateAndDataCallCounters(readLong4);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    updateDataLimitState();
                    parcel2.writeNoException();
                    return true;
                case 51:
                    ContextInfo contextInfo38 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowIncomingSms = allowIncomingSms(contextInfo38, readBoolean11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowIncomingSms);
                    return true;
                case 52:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowOutgoingSms = allowOutgoingSms(contextInfo39, readBoolean12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowOutgoingSms);
                    return true;
                case 53:
                    ContextInfo contextInfo40 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isIncomingSmsAllowed = isIncomingSmsAllowed(contextInfo40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIncomingSmsAllowed);
                    return true;
                case 54:
                    ContextInfo contextInfo41 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isOutgoingSmsAllowed = isOutgoingSmsAllowed(contextInfo41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOutgoingSmsAllowed);
                    return true;
                case 55:
                    ContextInfo contextInfo42 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowIncomingMms = allowIncomingMms(contextInfo42, readBoolean13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowIncomingMms);
                    return true;
                case 56:
                    ContextInfo contextInfo43 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowOutgoingMms = allowOutgoingMms(contextInfo43, readBoolean14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowOutgoingMms);
                    return true;
                case 57:
                    ContextInfo contextInfo44 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isIncomingMmsAllowed = isIncomingMmsAllowed(contextInfo44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIncomingMmsAllowed);
                    return true;
                case 58:
                    ContextInfo contextInfo45 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isOutgoingMmsAllowed = isOutgoingMmsAllowed(contextInfo45);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOutgoingMmsAllowed);
                    return true;
                case 59:
                    ContextInfo contextInfo46 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean blockSmsWithStorage = blockSmsWithStorage(contextInfo46, readBoolean15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(blockSmsWithStorage);
                    return true;
                case 60:
                    ContextInfo contextInfo47 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isBlockSmsWithStorageEnabled = isBlockSmsWithStorageEnabled(contextInfo47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBlockSmsWithStorageEnabled);
                    return true;
                case 61:
                    ContextInfo contextInfo48 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean blockMmsWithStorage = blockMmsWithStorage(contextInfo48, readBoolean16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(blockMmsWithStorage);
                    return true;
                case 62:
                    ContextInfo contextInfo49 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isBlockMmsWithStorageEnabled = isBlockMmsWithStorageEnabled(contextInfo49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBlockMmsWithStorageEnabled);
                    return true;
                case 63:
                    ContextInfo contextInfo50 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearStoredBlockedSms = clearStoredBlockedSms(contextInfo50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearStoredBlockedSms);
                    return true;
                case 64:
                    ContextInfo contextInfo51 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearStoredBlockedMms = clearStoredBlockedMms(contextInfo51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearStoredBlockedMms);
                    return true;
                case 65:
                    ContextInfo contextInfo52 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowWapPush = allowWapPush(contextInfo52, readBoolean17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowWapPush);
                    return true;
                case 66:
                    ContextInfo contextInfo53 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isWapPushAllowed = isWapPushAllowed(contextInfo53);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWapPushAllowed);
                    return true;
                case 67:
                    ContextInfo contextInfo54 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowCallerIDDisplay = allowCallerIDDisplay(contextInfo54, readBoolean18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowCallerIDDisplay);
                    return true;
                case 68:
                    ContextInfo contextInfo55 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCallerIDDisplayAllowed = isCallerIDDisplayAllowed(contextInfo55);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallerIDDisplayAllowed);
                    return true;
                case 69:
                    ContextInfo contextInfo56 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    boolean readBoolean19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int lockUnlockCorporateSimCard = lockUnlockCorporateSimCard(contextInfo56, readString13, readString14, readBoolean19);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockUnlockCorporateSimCard);
                    return true;
                case 70:
                    ContextInfo contextInfo57 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int changeSimPinCode = changeSimPinCode(contextInfo57, readString15, readString16, readString17);
                    parcel2.writeNoException();
                    parcel2.writeInt(changeSimPinCode);
                    return true;
                case 71:
                    String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSimLockedByAdmin = isSimLockedByAdmin(readString18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSimLockedByAdmin);
                    return true;
                case 72:
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String pinCode = getPinCode(readString19);
                    parcel2.writeNoException();
                    parcel2.writeString(pinCode);
                    return true;
                case 73:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSubIdLockedByAdmin = isSubIdLockedByAdmin(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSubIdLockedByAdmin);
                    return true;
                case 74:
                    ContextInfo contextInfo58 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowCopyContactToSim = allowCopyContactToSim(contextInfo58, readBoolean20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowCopyContactToSim);
                    return true;
                case 75:
                    ContextInfo contextInfo59 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCopyContactToSimAllowed = isCopyContactToSimAllowed(contextInfo59);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCopyContactToSimAllowed);
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
                    boolean removeOutgoingCallExceptionPattern = removeOutgoingCallExceptionPattern(contextInfo62);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeOutgoingCallExceptionPattern);
                    return true;
                case 79:
                    ContextInfo contextInfo63 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean removeIncomingCallExceptionPattern = removeIncomingCallExceptionPattern(contextInfo63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeIncomingCallExceptionPattern);
                    return true;
                case 80:
                    ContextInfo contextInfo64 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addOutgoingCallExceptionPattern = addOutgoingCallExceptionPattern(contextInfo64, readString20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addOutgoingCallExceptionPattern);
                    return true;
                case 81:
                    ContextInfo contextInfo65 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addIncomingCallExceptionPattern = addIncomingCallExceptionPattern(contextInfo65, readString21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addIncomingCallExceptionPattern);
                    return true;
                case 82:
                    ContextInfo contextInfo66 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean outgoingCallExceptionPattern = setOutgoingCallExceptionPattern(contextInfo66, readString22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outgoingCallExceptionPattern);
                    return true;
                case 83:
                    ContextInfo contextInfo67 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean incomingCallExceptionPattern = setIncomingCallExceptionPattern(contextInfo67, readString23);
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
                    boolean removeOutgoingSmsExceptionPattern = removeOutgoingSmsExceptionPattern(contextInfo70);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeOutgoingSmsExceptionPattern);
                    return true;
                case 87:
                    ContextInfo contextInfo71 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean removeIncomingSmsExceptionPattern = removeIncomingSmsExceptionPattern(contextInfo71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeIncomingSmsExceptionPattern);
                    return true;
                case 88:
                    ContextInfo contextInfo72 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addOutgoingSmsExceptionPattern = addOutgoingSmsExceptionPattern(contextInfo72, readString24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addOutgoingSmsExceptionPattern);
                    return true;
                case 89:
                    ContextInfo contextInfo73 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addIncomingSmsExceptionPattern = addIncomingSmsExceptionPattern(contextInfo73, readString25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addIncomingSmsExceptionPattern);
                    return true;
                case 90:
                    ContextInfo contextInfo74 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean outgoingSmsExceptionPattern = setOutgoingSmsExceptionPattern(contextInfo74, readString26);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(outgoingSmsExceptionPattern);
                    return true;
                case 91:
                    ContextInfo contextInfo75 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean incomingSmsExceptionPattern = setIncomingSmsExceptionPattern(contextInfo75, readString27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(incomingSmsExceptionPattern);
                    return true;
                case 92:
                    ContextInfo contextInfo76 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean disclaimerText = setDisclaimerText(contextInfo76, readString28);
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
                    int readInt19 = parcel.readInt();
                    boolean readBoolean21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int rCSEnabled = setRCSEnabled(contextInfo78, readInt19, readBoolean21);
                    parcel2.writeNoException();
                    parcel2.writeInt(rCSEnabled);
                    return true;
                case 95:
                    ContextInfo contextInfo79 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt20 = parcel.readInt();
                    boolean readBoolean22 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isRCSEnabled = isRCSEnabled(contextInfo79, readInt20, readBoolean22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRCSEnabled);
                    return true;
                case 96:
                    ContextInfo contextInfo80 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    Bundle rCSMessage = getRCSMessage(contextInfo80, readLong5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rCSMessage, 1);
                    return true;
                case 97:
                    ContextInfo contextInfo81 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt21 = parcel.readInt();
                    boolean readBoolean23 = parcel.readBoolean();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int rCSEnabledBySimSlot = setRCSEnabledBySimSlot(contextInfo81, readInt21, readBoolean23, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeInt(rCSEnabledBySimSlot);
                    return true;
                case 98:
                    ContextInfo contextInfo82 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt23 = parcel.readInt();
                    boolean readBoolean24 = parcel.readBoolean();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isRCSEnabledBySimSlot = isRCSEnabledBySimSlot(contextInfo82, readInt23, readBoolean24, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRCSEnabledBySimSlot);
                    return true;
                case 99:
                    ContextInfo contextInfo83 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt25 = parcel.readInt();
                    boolean readBoolean25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int allowDataNetworkFromSimSlot = allowDataNetworkFromSimSlot(contextInfo83, readInt25, readBoolean25);
                    parcel2.writeNoException();
                    parcel2.writeInt(allowDataNetworkFromSimSlot);
                    return true;
                case 100:
                    ContextInfo contextInfo84 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt26 = parcel.readInt();
                    boolean readBoolean26 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int allowIncomingCallFromSimSlot = allowIncomingCallFromSimSlot(contextInfo84, readInt26, readBoolean26);
                    parcel2.writeNoException();
                    parcel2.writeInt(allowIncomingCallFromSimSlot);
                    return true;
                case 101:
                    ContextInfo contextInfo85 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt27 = parcel.readInt();
                    boolean readBoolean27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int allowOutgoingCallFromSimSlot = allowOutgoingCallFromSimSlot(contextInfo85, readInt27, readBoolean27);
                    parcel2.writeNoException();
                    parcel2.writeInt(allowOutgoingCallFromSimSlot);
                    return true;
                case 102:
                    ContextInfo contextInfo86 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt28 = parcel.readInt();
                    boolean readBoolean28 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int allowIncomingSmsFromSimSlot = allowIncomingSmsFromSimSlot(contextInfo86, readInt28, readBoolean28);
                    parcel2.writeNoException();
                    parcel2.writeInt(allowIncomingSmsFromSimSlot);
                    return true;
                case 103:
                    ContextInfo contextInfo87 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt29 = parcel.readInt();
                    boolean readBoolean29 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int allowOutgoingSmsFromSimSlot = allowOutgoingSmsFromSimSlot(contextInfo87, readInt29, readBoolean29);
                    parcel2.writeNoException();
                    parcel2.writeInt(allowOutgoingSmsFromSimSlot);
                    return true;
                case 104:
                    ContextInfo contextInfo88 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt30 = parcel.readInt();
                    boolean readBoolean30 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int allowMmsFromSimSlot = allowMmsFromSimSlot(contextInfo88, readInt30, readBoolean30);
                    parcel2.writeNoException();
                    parcel2.writeInt(allowMmsFromSimSlot);
                    return true;
                case 105:
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDataAllowedFromSimSlot = isDataAllowedFromSimSlot(readInt31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDataAllowedFromSimSlot);
                    return true;
                case 106:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isIncomingCallAllowedFromSimSlot = isIncomingCallAllowedFromSimSlot(readInt32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIncomingCallAllowedFromSimSlot);
                    return true;
                case 107:
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isOutgoingCallAllowedFromSimSlot = isOutgoingCallAllowedFromSimSlot(readInt33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOutgoingCallAllowedFromSimSlot);
                    return true;
                case 108:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isIncomingSmsAllowedFromSimSlot = isIncomingSmsAllowedFromSimSlot(readInt34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isIncomingSmsAllowedFromSimSlot);
                    return true;
                case 109:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isOutgoingSmsAllowedFromSimSlot = isOutgoingSmsAllowedFromSimSlot(readInt35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOutgoingSmsAllowedFromSimSlot);
                    return true;
                case 110:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isMmsAllowedFromSimSlot = isMmsAllowedFromSimSlot(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMmsAllowedFromSimSlot);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
