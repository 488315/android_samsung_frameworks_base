package android.telephony;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteCallback;
import android.telecom.ParcelableCallAnalytics;
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
/* loaded from: classes3.dex */
public abstract class CellBroadcastService extends Service {
  public static final String CELL_BROADCAST_SERVICE_INTERFACE =
      "android.telephony.CellBroadcastService";
  private static final int GSM_HEADER_LENGTH = 6;
  private static final int ONEPAGE_DATA_LENGTH = 83;
  private static final String TAG = "CellBroadcastService";
  private static final int UMTS_HEADER_LENGTH = 7;
  private final HashMap<SmsCbConcatInfo, byte[][]> mSmsCbPageMap = new HashMap<>(4);
  private final ICellBroadcastService.Stub mStubWrapper = new ICellBroadcastServiceWrapper();

  public abstract CharSequence getCellBroadcastAreaInfo(int i);

  public abstract void onCdmaCellBroadcastSms(int i, byte[] bArr, int i2);

  public abstract void onCdmaScpMessage(
      int i, List<CdmaSmsCbProgramData> list, String str, Consumer<Bundle> consumer);

  public abstract void onGsmCellBroadcastSms(int i, byte[] bArr);

  @Override // android.app.Service
  public IBinder onBind(Intent intent) {
    return this.mStubWrapper;
  }

  public class ICellBroadcastServiceWrapper extends ICellBroadcastService.Stub {
    public ICellBroadcastServiceWrapper() {}

    @Override // android.telephony.ICellBroadcastService
    public void handleGsmCellBroadcastSms(int slotIndex, byte[] message) {
      byte[][] pdus;
      int i;
      byte[][] pdus2 = null;
      int pduLength = ((message[1] & 255) << 8) | (message[0] & 255);
      byte[] pdu = new byte[pduLength];
      System.arraycopy(message, 4, pdu, 0, pduLength);
      SmsCbHeader header = CellBroadcastService.this.createSmsCbHeader(pdu);
      com.android.telephony.Rlog.m238d(CellBroadcastService.TAG, "header=" + header);
      if (header == null) {
        return;
      }
      int pageCount = header.getNumberOfPages();
      if (pageCount == 1) {
        if (!header.isUmtsFormat()) {
          try {
            com.android.telephony.Rlog.m242i(
                CellBroadcastService.TAG, "Single page. Not UMTS format");
            int wacLength = ((message[3] & 255) << 8) | (message[2] & 255);
            if (wacLength > 0) {
              byte[] wacPdu = new byte[wacLength + 2];
              wacPdu[0] = message[2];
              wacPdu[1] = message[3];
              System.arraycopy(message, pduLength + 4, wacPdu, 2, wacLength);
              CellBroadcastService.this.convertGsmToUmts(
                  pdu, wacPdu, pduLength, wacLength + 2, slotIndex);
              return;
            }
            if (header.getServiceCategory() == 4400) {
              CellBroadcastService.this.convertGsmToUmts(pdu, null, pduLength, 0, slotIndex);
              return;
            }
            if (header.getEtwsInfo() != null && pdu.length > 56) {
              com.android.telephony.Rlog.m242i(
                  CellBroadcastService.TAG, "Remove padding bit and convert GSM to UMTS.");
              int i2 = pdu.length - 1;
              while (i2 >= 0 && pdu[i2] == 0) {
                i2--;
                pduLength--;
              }
              CellBroadcastService.this.convertGsmToUmts(pdu, null, pduLength, 0, slotIndex);
              return;
            }
          } catch (IndexOutOfBoundsException e) {
            com.android.telephony.Rlog.m240e(
                CellBroadcastService.TAG, "Error in decoding SMS CB pdu " + e.toString());
            CellBroadcastService.this.onGsmCellBroadcastSms(slotIndex, pdu);
            return;
          }
        }
        com.android.telephony.Rlog.m238d(CellBroadcastService.TAG, "Converting is not needed");
        CellBroadcastService.this.onGsmCellBroadcastSms(slotIndex, pdu);
        return;
      }
      if (pageCount <= 1) {
        CellBroadcastService.this.onGsmCellBroadcastSms(slotIndex, pdu);
        return;
      }
      SmsCbConcatInfo concatInfo = new SmsCbConcatInfo(header, System.currentTimeMillis());
      byte[][] pdus3 = (byte[][]) CellBroadcastService.this.mSmsCbPageMap.get(concatInfo);
      if (pdus3 == null) {
        byte[][] pdus4 = new byte[pageCount][];
        CellBroadcastService.this.mSmsCbPageMap.put(concatInfo, pdus4);
        pdus = pdus4;
      } else {
        pdus = pdus3;
      }
      com.android.telephony.Rlog.m238d(CellBroadcastService.TAG, "pdus size=" + pdus.length);
      pdus[header.getPageIndex() - 1] = pdu;
      for (byte[] bArr : pdus) {
        if (bArr == null) {
          com.android.telephony.Rlog.m238d(CellBroadcastService.TAG, "still missing pdu");
          return;
        }
      }
      try {
        CellBroadcastService.this.mSmsCbPageMap.remove(concatInfo);
        Iterator<SmsCbConcatInfo> iter =
            CellBroadcastService.this.mSmsCbPageMap.keySet().iterator();
        while (iter.hasNext()) {
          try {
            SmsCbConcatInfo info = iter.next();
            if (info.overTime()) {
              com.android.telephony.Rlog.m238d(
                  CellBroadcastService.TAG, "Remove saved message over 5min");
              iter.remove();
            }
          } catch (RuntimeException e2) {
            e = e2;
            i = 0;
            pdus2 = pdus;
          }
        }
        int wacLength2 = (message[2] & 255) | ((message[3] & 255) << 8);
        try {
          if (wacLength2 > 0) {
            try {
              com.android.telephony.Rlog.m242i(
                  CellBroadcastService.TAG, "WAC included in GSM format multipage");
              byte[] wacPdu2 = new byte[wacLength2 + 2];
              wacPdu2[0] = message[2];
              wacPdu2[1] = message[3];
              System.arraycopy(message, pduLength + 4, wacPdu2, 2, wacLength2);
              CellBroadcastService.this.convertGsmToUmtsForMultiPage(
                  pageCount, pdus, wacPdu2, wacLength2 + 2, slotIndex);
              return;
            } catch (RuntimeException e3) {
              e = e3;
              pdus2 = pdus;
              i = 0;
            }
          } else {
            pdus2 = pdus;
            try {
              if (header.getEtwsInfo() == null) {
                com.android.telephony.Rlog.m242i(
                    CellBroadcastService.TAG, "No WAC. Deliver CB without converting.");
                for (byte[] bArr2 : pdus2) {
                  CellBroadcastService.this.onGsmCellBroadcastSms(slotIndex, bArr2);
                }
                return;
              }
              com.android.telephony.Rlog.m242i(
                  CellBroadcastService.TAG,
                  "Remove padding bit and convert GSM to UMTS for multipage.");
              byte[] p = pdus2[pageCount - 1];
              int pLength = p.length;
              int i3 = p.length - 1;
              int pLength2 = pLength;
              while (i3 >= 0 && p[i3] == 0) {
                i3--;
                pLength2--;
              }
              byte[] noPaddingPdu = new byte[pLength2];
              try {
                System.arraycopy(p, 0, noPaddingPdu, 0, pLength2);
                pdus2[pageCount - 1] = noPaddingPdu;
                CellBroadcastService.this.convertGsmToUmtsForMultiPage(
                    pageCount, pdus2, null, 0, slotIndex);
                return;
              } catch (RuntimeException e4) {
                e = e4;
                i = 0;
              }
            } catch (RuntimeException e5) {
              e = e5;
            }
          }
        } catch (RuntimeException e6) {
          e = e6;
          i = 0;
        }
      } catch (RuntimeException e7) {
        e = e7;
        i = 0;
        pdus2 = pdus;
      }
      String errorMessage = "Error in decoding SMS CB pdu" + e.toString();
      com.android.telephony.Rlog.m240e(CellBroadcastService.TAG, errorMessage);
      if (pdus2 != null) {
        int length = pdus2.length;
        for (int i4 = i; i4 < length; i4++) {
          byte[] p2 = pdus2[i4];
          if (p2 != null) {
            CellBroadcastService.this.onGsmCellBroadcastSms(slotIndex, p2);
          }
        }
      }
    }

    @Override // android.telephony.ICellBroadcastService
    public void handleCdmaCellBroadcastSms(int slotIndex, byte[] bearerData, int serviceCategory) {
      CellBroadcastService.this.onCdmaCellBroadcastSms(slotIndex, bearerData, serviceCategory);
    }

    @Override // android.telephony.ICellBroadcastService
    public void handleCdmaScpMessage(
        int slotIndex,
        List<CdmaSmsCbProgramData> smsCbProgramData,
        String originatingAddress,
        final RemoteCallback callback) {
      Consumer<Bundle> consumer =
          new Consumer() { // from class:
                           // android.telephony.CellBroadcastService$ICellBroadcastServiceWrapper$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              RemoteCallback.this.sendResult((Bundle) obj);
            }
          };
      CellBroadcastService.this.onCdmaScpMessage(
          slotIndex, smsCbProgramData, originatingAddress, consumer);
    }

    @Override // android.telephony.ICellBroadcastService
    public CharSequence getCellBroadcastAreaInfo(int slotIndex) {
      return CellBroadcastService.this.getCellBroadcastAreaInfo(slotIndex);
    }

    @Override // android.os.Binder
    protected void dump(FileDescriptor fd, PrintWriter fout, String[] args) {
      CellBroadcastService.this.dump(fd, fout, args);
    }

    @Override // android.os.Binder, android.os.IBinder
    public void dump(FileDescriptor fd, String[] args) {
      PrintWriter pw = new FastPrintWriter(new FileOutputStream(fd));
      CellBroadcastService.this.dump(fd, pw, args);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public SmsCbHeader createSmsCbHeader(byte[] bytes) {
    try {
      return new SmsCbHeader(bytes);
    } catch (Exception ex) {
      com.android.telephony.Rlog.m240e(TAG, "Can't create SmsCbHeader, ex = " + ex.toString());
      return null;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void convertGsmToUmts(
      byte[] pdu, byte[] wac, int pduLength, int wacLength, int slotIndex) {
    byte[] umtsPdu = new byte[wacLength + 90];
    int offset = 0 + 1;
    try {
      umtsPdu[0] = 1;
      int offset2 = offset + 1;
      umtsPdu[offset] = pdu[2];
      int offset3 = offset2 + 1;
      umtsPdu[offset2] = pdu[3];
      int offset4 = offset3 + 1;
      umtsPdu[offset3] = pdu[0];
      int offset5 = offset4 + 1;
      umtsPdu[offset4] = pdu[1];
      int offset6 = offset5 + 1;
      umtsPdu[offset5] = pdu[4];
      umtsPdu[offset6] = 1;
      System.arraycopy(pdu, 6, umtsPdu, offset6 + 1, pduLength - 6);
      umtsPdu[89] = (byte) (pduLength - 6);
      if (wacLength > 0) {
        System.arraycopy(wac, 0, umtsPdu, 90, wacLength);
      }
      onGsmCellBroadcastSms(slotIndex, umtsPdu);
    } catch (IndexOutOfBoundsException e) {
      com.android.telephony.Rlog.m240e(TAG, "Error in convertGsmToUmts: " + e.toString());
      onGsmCellBroadcastSms(slotIndex, pdu);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void convertGsmToUmtsForMultiPage(
      int pageCount, byte[][] pdus, byte[] wac, int wacLength, int slotIndex) {
    byte[] umtsPdu = new byte[(pageCount * 83) + 7 + wacLength];
    int offset = 0 + 1;
    try {
      umtsPdu[0] = 1;
      int offset2 = offset + 1;
      umtsPdu[offset] = pdus[0][2];
      int offset3 = offset2 + 1;
      umtsPdu[offset2] = pdus[0][3];
      int offset4 = offset3 + 1;
      umtsPdu[offset3] = pdus[0][0];
      int offset5 = offset4 + 1;
      umtsPdu[offset4] = pdus[0][1];
      int offset6 = offset5 + 1;
      umtsPdu[offset5] = pdus[0][4];
      int i = offset6 + 1;
      umtsPdu[offset6] = (byte) pageCount;
      int page = 0;
      for (byte[] pdu : pdus) {
        System.arraycopy(pdu, 6, umtsPdu, (page * 83) + 7, pdu.length - 6);
        page++;
        umtsPdu[((page * 83) + 7) - 1] = (byte) (pdu.length - 6);
      }
      if (wacLength != 0) {
        try {
          System.arraycopy(wac, 0, umtsPdu, (pageCount * 83) + 7, wacLength);
        } catch (IndexOutOfBoundsException e) {
          e = e;
          com.android.telephony.Rlog.m240e(
              TAG, "Error in convertGsmToUmtsForMultiPage: " + e.toString());
          for (byte[] bArr : pdus) {
            onGsmCellBroadcastSms(slotIndex, bArr);
          }
          return;
        }
      }
      onGsmCellBroadcastSms(slotIndex, umtsPdu);
    } catch (IndexOutOfBoundsException e2) {
      e = e2;
    }
  }

  private static final class SmsCbConcatInfo {
    private final SmsCbHeader mHeader;
    private final long mReceivedTime;

    SmsCbConcatInfo(SmsCbHeader header, long receivedTime) {
      this.mHeader = header;
      this.mReceivedTime = receivedTime;
    }

    public int hashCode() {
      return this.mHeader.getSerialNumber() * 31;
    }

    public boolean equals(Object obj) {
      if (!(obj instanceof SmsCbConcatInfo)) {
        return false;
      }
      SmsCbConcatInfo other = (SmsCbConcatInfo) obj;
      return this.mHeader.getSerialNumber() == other.mHeader.getSerialNumber()
          && this.mReceivedTime < other.mReceivedTime + ParcelableCallAnalytics.MILLIS_IN_5_MINUTES;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean overTime() {
      return this.mReceivedTime
          < System.currentTimeMillis() - ParcelableCallAnalytics.MILLIS_IN_5_MINUTES;
    }
  }
}
