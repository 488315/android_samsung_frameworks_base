package com.android.internal.telephony;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Bundle;
import com.android.internal.telephony.ISms;
import java.util.List;

/* loaded from: classes4.dex */
public class ISmsImplBase extends ISms.Stub {
    @Override // com.android.internal.telephony.ISms
    public List<SmsRawData> getAllMessagesFromIccEfForSubscriber(int i, String str) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean updateMessageOnIccEfForSubscriber(int i, String str, int i2, int i3, byte[] bArr) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean copyMessageToIccEfForSubscriber(int i, String str, int i2, byte[] bArr, byte[] bArr2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendDataForSubscriber(int i, String str, String str2, String str3, String str4, int i2, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendTextForSubscriber(int i, String str, String str2, String str3, String str4, String str5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, long j) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendTextForSubscriberWithOptions(int i, String str, String str2, String str3, String str4, String str5, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i2, boolean z2, int i3) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void injectSmsPduForSubscriber(int i, byte[] bArr, String str, PendingIntent pendingIntent) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendMultipartTextForSubscriber(int i, String str, String str2, String str3, String str4, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean z, long j) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendMultipartTextForSubscriberWithOptions(int i, String str, String str2, String str3, String str4, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean z, int i2, boolean z2, int i3) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean enableCellBroadcastForSubscriber(int i, int i2, int i3) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean disableCellBroadcastForSubscriber(int i, int i2, int i3) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean enableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean disableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public int getPremiumSmsPermission(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public int getPremiumSmsPermissionForSubscriber(int i, String str) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void setPremiumSmsPermission(String str, int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void setPremiumSmsPermissionForSubscriber(int i, String str, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean isImsSmsSupportedForSubscriber(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean isSmsSimPickActivityNeeded(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public int getPreferredSmsSubscription() {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public String getImsSmsFormatForSubscriber(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean isSMSPromptEnabled() {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendStoredText(int i, String str, String str2, Uri uri, String str3, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendStoredMultipartText(int i, String str, String str2, Uri uri, String str3, List<PendingIntent> list, List<PendingIntent> list2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public Bundle getCarrierConfigValuesForSubscriber(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public String createAppSpecificSmsToken(int i, String str, PendingIntent pendingIntent) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public String createAppSpecificSmsTokenWithPackageInfo(int i, String str, String str2, PendingIntent pendingIntent) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void setStorageMonitorMemoryStatusOverride(int i, boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void clearStorageMonitorMemoryStatusOverride(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public int checkSmsShortCodeDestination(int i, String str, String str2, String str3, String str4) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public String getSmscAddressFromIccEfForSubscriber(int i, String str) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean setSmscAddressOnIccEfForSubscriber(String str, int i, String str2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public int getSmsCapacityOnIccForSubscriber(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean resetAllCellBroadcastRanges(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public long getWapMessageSize(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendTextwithCBPForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str5, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendTextwithOptionsForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i2, int i3, int i4) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendTextwithOptionsReadconfirmForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i2, int i3, int i4, int i5) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendMultipartTextwithCBPForSubscriber(int i, String str, String str2, String str3, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, String str4, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendMultipartTextwithOptionsForSubscriber(int i, String str, String str2, String str3, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean z, int i2, int i3, int i4) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean getSMSPAvailableForSubscriber(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public byte[] getCbSettingsForSubscriber(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public String getMnoNameForSubscriber(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean getSmsSettingForSubscriber(int i, String str) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendDatawithOrigPortForSubscriber(int i, String str, String str2, String str3, int i2, int i3, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendOTADomesticForSubscriber(int i, String str, String str2, String str3, String str4) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendTextNSRIForSubscriber(int i, String str, String str2, String str3, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2, int i2, int i3) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void sendTextAutoLoginForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public void resetSimFullStatusForSubscriber(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.telephony.ISms
    public boolean isMsgBlockedForOneNumberServiceForSubscriber(int i) {
        throw new UnsupportedOperationException();
    }
}
