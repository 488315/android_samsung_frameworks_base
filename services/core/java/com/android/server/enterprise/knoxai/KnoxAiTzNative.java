package com.android.server.enterprise.knoxai;

import com.samsung.android.knox.knoxai.TaLoaderOptions;
import com.samsung.android.knox.knoxai.TaProcessBuffer;
import java.io.FileDescriptor;

/* loaded from: classes2.dex */
public class KnoxAiTzNative {
  public static final String TAG = "KnoxAiTzNative";

  public native byte[] tzKnoxAiGetCert(byte[] bArr, int i);

  public native int tzKnoxAiInitializeFd(
      String str, int i, String str2, int i2, FileDescriptor fileDescriptor, int i3, int i4);

  public native byte[] tzKnoxAiReturnPlainFactoryModelKey(byte[] bArr, int i, byte[] bArr2, int i2);

  public native byte[] tzKnoxAiSetProv(byte[] bArr, int i, byte[] bArr2, int i2);

  public native int tzKnoxAiTerminate();

  public int initializeTaSession(TaLoaderOptions taLoaderOptions) {
    return tzKnoxAiInitializeFd(
        taLoaderOptions.getRoot(),
        taLoaderOptions.getRootLength(),
        taLoaderOptions.getProcess(),
        taLoaderOptions.getProcessLength(),
        taLoaderOptions.getFd(),
        taLoaderOptions.getTaOffset(),
        taLoaderOptions.getTaSize());
  }

  public int terminateTaSession() {
    return tzKnoxAiTerminate();
  }

  public void processTaCommand(
      int i, TaProcessBuffer[] taProcessBufferArr, TaProcessBuffer[] taProcessBufferArr2) {
    byte[] bArr;
    if (taProcessBufferArr2 == null || taProcessBufferArr2.length == 0) {
      return;
    }
    TaProcessBuffer taProcessBuffer = new TaProcessBuffer();
    taProcessBufferArr2[0] = taProcessBuffer;
    taProcessBuffer.setProcessBufferType(TaProcessBuffer.ProcessBufferType.OUTPUT_RESULT);
    if (taProcessBufferArr != null && taProcessBufferArr.length > 0) {
      if (i == TaProcessBuffer.ProcessType.GET_CERT.getValue()) {
        bArr =
            tzKnoxAiGetCert(
                taProcessBufferArr[0].getBuffer(), taProcessBufferArr[0].getBufferLength());
      } else if (i == TaProcessBuffer.ProcessType.SET_PROV.getValue()) {
        bArr =
            tzKnoxAiSetProv(
                taProcessBufferArr[0].getBuffer(),
                taProcessBufferArr[0].getBufferLength(),
                taProcessBufferArr[1].getBuffer(),
                taProcessBufferArr[1].getBufferLength());
      } else if (i == TaProcessBuffer.ProcessType.RETURN_PLAIN_FACTORY_MODEL_KEY.getValue()) {
        bArr =
            tzKnoxAiReturnPlainFactoryModelKey(
                taProcessBufferArr[0].getBuffer(),
                taProcessBufferArr[0].getBufferLength(),
                taProcessBufferArr[1].getBuffer(),
                taProcessBufferArr[1].getBufferLength());
      }
      taProcessBufferArr2[0].setBuffer(bArr);
    }
    bArr = null;
    taProcessBufferArr2[0].setBuffer(bArr);
  }
}
