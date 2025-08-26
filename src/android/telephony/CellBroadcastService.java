package android.telephony;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteCallback;
import android.telecom.ParcelableCallAnalytics;
import android.telephony.ICellBroadcastService;
import android.telephony.cdma.CdmaSmsCbProgramData;
import com.android.internal.telephony.gsm.SmsCbHeader;
import com.android.internal.util.FastPrintWriter;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes4.dex */
public abstract class CellBroadcastService extends Service {
    public static final String CELL_BROADCAST_SERVICE_INTERFACE = "android.telephony.CellBroadcastService";
    private static final int GSM_HEADER_LENGTH = 6;
    private static final int ONEPAGE_DATA_LENGTH = 83;
    private static final String TAG = "CellBroadcastService";
    private static final int UMTS_HEADER_LENGTH = 7;
    private final HashMap<SmsCbConcatInfo, byte[][]> mSmsCbPageMap = new HashMap<>(4);
    private final ICellBroadcastService.Stub mStubWrapper = new ICellBroadcastServiceWrapper();

    public abstract CharSequence getCellBroadcastAreaInfo(int i);

    @Deprecated
    public void onCdmaCellBroadcastSms(int i, byte[] bArr, int i2) {
    }

    @Deprecated
    public void onCdmaScpMessage(int i, List<CdmaSmsCbProgramData> list, String str, Consumer<Bundle> consumer) {
    }

    public abstract void onGsmCellBroadcastSms(int i, byte[] bArr);

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mStubWrapper;
    }

    public class ICellBroadcastServiceWrapper extends ICellBroadcastService.Stub {
        public ICellBroadcastServiceWrapper() {
        }

        /* JADX WARN: Incorrect type for immutable var: ssa=byte, code=byte[], for r3v11, types: [byte] */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:101:0x01fc  */
        /* JADX WARN: Removed duplicated region for block: B:107:0x020b A[ORIG_RETURN, RETURN] */
        /* JADX WARN: Type inference failed for: r2v28 */
        /* JADX WARN: Type inference failed for: r2v8, types: [byte[]] */
        /* JADX WARN: Type inference failed for: r3v12 */
        /* JADX WARN: Type inference failed for: r3v13 */
        /* JADX WARN: Type inference failed for: r3v15 */
        /* JADX WARN: Type inference failed for: r3v6, types: [android.telephony.CellBroadcastService] */
        /* JADX WARN: Type inference failed for: r3v7, types: [byte[]] */
        /* JADX WARN: Type inference failed for: r4v2 */
        /* JADX WARN: Type inference failed for: r4v3 */
        /* JADX WARN: Type inference failed for: r4v4 */
        /* JADX WARN: Type inference failed for: r4v5 */
        /* JADX WARN: Type inference failed for: r4v6 */
        /* JADX WARN: Type inference failed for: r4v9, types: [byte[][]] */
        /* JADX WARN: Type inference failed for: r5v2, types: [android.telephony.CellBroadcastService] */
        /* JADX WARN: Type inference failed for: r7v8 */
        @Override // android.telephony.ICellBroadcastService
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void handleGsmCellBroadcastSms(int i, byte[] bArr) {
            byte[] bArr2;
            int i2;
            int i3;
            byte[] bArr3;
            int i4 = ((bArr[1] & 255) << 8) | (bArr[0] & 255);
            byte[] bArr4 = new byte[i4];
            ?? r4 = 4;
            System.arraycopy(bArr, 4, bArr4, 0, i4);
            SmsCbHeader smsCbHeaderCreateSmsCbHeader = CellBroadcastService.this.createSmsCbHeader(bArr4);
            com.android.telephony.Rlog.d(CellBroadcastService.TAG, "header=" + smsCbHeaderCreateSmsCbHeader);
            if (smsCbHeaderCreateSmsCbHeader == null) {
                return;
            }
            int numberOfPages = smsCbHeaderCreateSmsCbHeader.getNumberOfPages();
            if (numberOfPages == 1) {
                if (smsCbHeaderCreateSmsCbHeader.isUmtsFormat()) {
                    bArr3 = bArr4;
                    i3 = i;
                } else {
                    try {
                        com.android.telephony.Rlog.i(CellBroadcastService.TAG, "Single page. Not UMTS format");
                        byte[] bArr5 = bArr[3];
                        int i5 = (bArr5 & 255) << 8;
                        byte b = bArr[2];
                        int i6 = i5 | (b & 255);
                        try {
                            if (i6 > 0) {
                                int i7 = i6 + 2;
                                try {
                                    byte[] bArr6 = new byte[i7];
                                    bArr6[0] = b;
                                    bArr6[1] = bArr5;
                                    System.arraycopy(bArr, i4 + 4, bArr6, 2, i6);
                                    CellBroadcastService.this.convertGsmToUmts(bArr4, bArr6, i4, i7, i);
                                    return;
                                } catch (IndexOutOfBoundsException e) {
                                    e = e;
                                    bArr5 = bArr4;
                                    i2 = i;
                                    bArr2 = bArr5;
                                    com.android.telephony.Rlog.e(CellBroadcastService.TAG, "Error in decoding SMS CB pdu " + e.toString());
                                    CellBroadcastService.this.onGsmCellBroadcastSms(i2, bArr2);
                                    return;
                                }
                            }
                            bArr5 = bArr4;
                            if (smsCbHeaderCreateSmsCbHeader.getServiceCategory() == 4400) {
                                CellBroadcastService.this.convertGsmToUmts(bArr5, null, i4, 0, i);
                                return;
                            }
                            if (smsCbHeaderCreateSmsCbHeader.getEtwsInfo() != null && i4 > 56) {
                                com.android.telephony.Rlog.i(CellBroadcastService.TAG, "Remove padding bit and convert GSM to UMTS.");
                                int i8 = i4 - 1;
                                while (i8 >= 0 && bArr5[i8] == 0) {
                                    i8--;
                                    i4--;
                                }
                                i2 = i;
                                try {
                                    CellBroadcastService.this.convertGsmToUmts(bArr5, null, i4, 0, i2);
                                    return;
                                } catch (IndexOutOfBoundsException e2) {
                                    e = e2;
                                    bArr2 = bArr5;
                                    com.android.telephony.Rlog.e(CellBroadcastService.TAG, "Error in decoding SMS CB pdu " + e.toString());
                                    CellBroadcastService.this.onGsmCellBroadcastSms(i2, bArr2);
                                    return;
                                }
                            }
                            i3 = i;
                            bArr3 = bArr5;
                        } catch (IndexOutOfBoundsException e3) {
                            e = e3;
                        }
                    } catch (IndexOutOfBoundsException e4) {
                        e = e4;
                        bArr2 = bArr4;
                        i2 = i;
                    }
                }
                com.android.telephony.Rlog.d(CellBroadcastService.TAG, "Converting is not needed");
                CellBroadcastService.this.onGsmCellBroadcastSms(i3, bArr3);
                return;
            }
            int i9 = i;
            if (numberOfPages > 1) {
                SmsCbConcatInfo smsCbConcatInfo = new SmsCbConcatInfo(smsCbHeaderCreateSmsCbHeader, System.currentTimeMillis(), i9);
                byte[][] bArr7 = (byte[][]) CellBroadcastService.this.mSmsCbPageMap.get(smsCbConcatInfo);
                if (bArr7 == null) {
                    bArr7 = new byte[numberOfPages][];
                    CellBroadcastService.this.mSmsCbPageMap.put(smsCbConcatInfo, bArr7);
                }
                com.android.telephony.Rlog.d(CellBroadcastService.TAG, "pdus size=" + bArr7.length);
                bArr7[smsCbHeaderCreateSmsCbHeader.getPageIndex() + (-1)] = bArr4;
                for (byte[] bArr8 : bArr7) {
                    if (bArr8 == null) {
                        com.android.telephony.Rlog.d(CellBroadcastService.TAG, "still missing pdu");
                        return;
                    }
                }
                try {
                    CellBroadcastService.this.mSmsCbPageMap.remove(smsCbConcatInfo);
                    Iterator it = CellBroadcastService.this.mSmsCbPageMap.keySet().iterator();
                    while (it.hasNext()) {
                        if (((SmsCbConcatInfo) it.next()).overTime()) {
                            com.android.telephony.Rlog.d(CellBroadcastService.TAG, "Remove saved message over 5min");
                            it.remove();
                        }
                    }
                    int i10 = ((bArr[3] & 255) << 8) | (bArr[2] & 255);
                    try {
                        if (i10 > 0) {
                            try {
                                com.android.telephony.Rlog.i(CellBroadcastService.TAG, "WAC included in GSM format multipage");
                                int i11 = i10 + 2;
                                byte[] bArr9 = new byte[i11];
                                bArr9[0] = bArr[2];
                                bArr9[1] = bArr[3];
                                System.arraycopy(bArr, 4 + i4, bArr9, 2, i10);
                                CellBroadcastService.this.convertGsmToUmtsForMultiPage(numberOfPages, bArr7, bArr9, i11, i9);
                                return;
                            } catch (RuntimeException e5) {
                                e = e5;
                                r4 = bArr7;
                                i9 = i;
                                com.android.telephony.Rlog.e(CellBroadcastService.TAG, "Error in decoding SMS CB pdu" + e.toString());
                                if (r4 == 0) {
                                    for (?? r2 : r4) {
                                        if (r2 != 0) {
                                            CellBroadcastService.this.onGsmCellBroadcastSms(i9, r2);
                                        }
                                    }
                                    return;
                                }
                                return;
                            }
                        }
                        r4 = bArr7;
                        try {
                            if (smsCbHeaderCreateSmsCbHeader.getEtwsInfo() != null) {
                                com.android.telephony.Rlog.i(CellBroadcastService.TAG, "Remove padding bit and convert GSM to UMTS for multipage.");
                                int i12 = numberOfPages - 1;
                                byte[] bArr10 = r4[i12];
                                int length = bArr10.length;
                                int length2 = bArr10.length - 1;
                                while (length2 >= 0 && bArr10[length2] == 0) {
                                    length2--;
                                    length--;
                                }
                                byte[] bArr11 = new byte[length];
                                System.arraycopy(bArr10, 0, bArr11, 0, length);
                                r4[i12] = bArr11;
                                CellBroadcastService.this.convertGsmToUmtsForMultiPage(numberOfPages, r4, null, 0, i);
                                return;
                            }
                            com.android.telephony.Rlog.i(CellBroadcastService.TAG, "No WAC. Deliver CB without converting.");
                            for (?? r0 : r4) {
                                CellBroadcastService.this.onGsmCellBroadcastSms(i, r0);
                            }
                        } catch (RuntimeException e6) {
                            e = e6;
                            com.android.telephony.Rlog.e(CellBroadcastService.TAG, "Error in decoding SMS CB pdu" + e.toString());
                            if (r4 == 0) {
                            }
                        }
                    } catch (RuntimeException e7) {
                        e = e7;
                    }
                } catch (RuntimeException e8) {
                    e = e8;
                    r4 = bArr7;
                }
            } else {
                CellBroadcastService.this.onGsmCellBroadcastSms(i9, bArr4);
            }
        }

        @Override // android.telephony.ICellBroadcastService
        public void handleCdmaCellBroadcastSms(int i, byte[] bArr, int i2) {
            CellBroadcastService.this.onCdmaCellBroadcastSms(i, bArr, i2);
        }

        @Override // android.telephony.ICellBroadcastService
        public void handleCdmaScpMessage(int i, List<CdmaSmsCbProgramData> list, String str, final RemoteCallback remoteCallback) {
            CellBroadcastService.this.onCdmaScpMessage(i, list, str, new Consumer() { // from class: android.telephony.CellBroadcastService$ICellBroadcastServiceWrapper$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    remoteCallback.sendResult((Bundle) obj);
                }
            });
        }

        @Override // android.telephony.ICellBroadcastService
        public CharSequence getCellBroadcastAreaInfo(int i) {
            return CellBroadcastService.this.getCellBroadcastAreaInfo(i);
        }

        @Override // android.os.Binder
        protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            CellBroadcastService.this.dump(fileDescriptor, printWriter, strArr);
        }

        @Override // android.os.Binder, android.os.IBinder
        public void dump(FileDescriptor fileDescriptor, String[] strArr) {
            CellBroadcastService.this.dump(fileDescriptor, new FastPrintWriter(new FileOutputStream(fileDescriptor)), strArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SmsCbHeader createSmsCbHeader(byte[] bArr) {
        try {
            return new SmsCbHeader(bArr);
        } catch (Exception e) {
            com.android.telephony.Rlog.e(TAG, "Can't create SmsCbHeader, ex = " + e.toString());
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void convertGsmToUmts(byte[] bArr, byte[] bArr2, int i, int i2, int i3) {
        byte[] bArr3 = new byte[i2 + 90];
        try {
            bArr3[0] = 1;
            bArr3[1] = bArr[2];
            bArr3[2] = bArr[3];
            bArr3[3] = bArr[0];
            bArr3[4] = bArr[1];
            bArr3[5] = bArr[4];
            bArr3[6] = 1;
            int i4 = i - 6;
            System.arraycopy(bArr, 6, bArr3, 7, i4);
            bArr3[89] = (byte) i4;
            if (i2 > 0) {
                System.arraycopy(bArr2, 0, bArr3, 90, i2);
            }
            onGsmCellBroadcastSms(i3, bArr3);
        } catch (IndexOutOfBoundsException e) {
            com.android.telephony.Rlog.e(TAG, "Error in convertGsmToUmts: " + e.toString());
            onGsmCellBroadcastSms(i3, bArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void convertGsmToUmtsForMultiPage(int i, byte[][] bArr, byte[] bArr2, int i2, int i3) {
        int i4 = (i * 83) + 7;
        byte[] bArr3 = new byte[i4 + i2];
        try {
            bArr3[0] = 1;
            byte[] bArr4 = bArr[0];
            bArr3[1] = bArr4[2];
            bArr3[2] = bArr4[3];
            bArr3[3] = bArr4[0];
            bArr3[4] = bArr4[1];
            bArr3[5] = bArr4[4];
            bArr3[6] = (byte) i;
            int i5 = 0;
            for (byte[] bArr5 : bArr) {
                System.arraycopy(bArr5, 6, bArr3, (i5 * 83) + 7, bArr5.length - 6);
                i5++;
                bArr3[(i5 * 83) + 6] = (byte) (bArr5.length - 6);
            }
            if (i2 != 0) {
                System.arraycopy(bArr2, 0, bArr3, i4, i2);
            }
            onGsmCellBroadcastSms(i3, bArr3);
        } catch (IndexOutOfBoundsException e) {
            com.android.telephony.Rlog.e(TAG, "Error in convertGsmToUmtsForMultiPage: " + e.toString());
            for (byte[] bArr6 : bArr) {
                onGsmCellBroadcastSms(i3, bArr6);
            }
        }
    }

    private static final class SmsCbConcatInfo {
        private final SmsCbHeader mHeader;
        private final long mReceivedTime;
        private final int mSlotIndex;

        SmsCbConcatInfo(SmsCbHeader smsCbHeader, long j, int i) {
            this.mHeader = smsCbHeader;
            this.mReceivedTime = j;
            this.mSlotIndex = i;
        }

        public int hashCode() {
            return this.mHeader.getSerialNumber() * 31;
        }

        public boolean equals(Object obj) {
            if (obj instanceof SmsCbConcatInfo) {
                SmsCbConcatInfo smsCbConcatInfo = (SmsCbConcatInfo) obj;
                if (this.mHeader.getSerialNumber() == smsCbConcatInfo.mHeader.getSerialNumber() && this.mReceivedTime < smsCbConcatInfo.mReceivedTime + ParcelableCallAnalytics.MILLIS_IN_5_MINUTES && this.mSlotIndex == smsCbConcatInfo.mSlotIndex) {
                    return true;
                }
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean overTime() {
            return this.mReceivedTime < System.currentTimeMillis() - ParcelableCallAnalytics.MILLIS_IN_5_MINUTES;
        }
    }
}
