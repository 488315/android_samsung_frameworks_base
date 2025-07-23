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
        /* JADX WARN: Removed duplicated region for block: B:110:0x01fc  */
        /* JADX WARN: Removed duplicated region for block: B:120:0x020b A[ORIG_RETURN, RETURN] */
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
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void handleGsmCellBroadcastSms(int r18, byte[] r19) {
            /*
                Method dump skipped, instructions count: 530
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.telephony.CellBroadcastService.ICellBroadcastServiceWrapper.handleGsmCellBroadcastSms(int, byte[]):void");
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
                    RemoteCallback.this.sendResult((Bundle) obj);
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
