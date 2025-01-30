package com.samsung.android.authnrservice.service;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/* loaded from: classes2.dex */
public final class FingerprintOperation {
    public static FingerprintOperation sFingerprintOperation;
    public FingerprintManager mFingerprintManager;
    public final int mUserId;

    public static synchronized FingerprintOperation getInstance(Context context) {
        FingerprintOperation fingerprintOperation;
        synchronized (FingerprintOperation.class) {
            if (sFingerprintOperation == null) {
                sFingerprintOperation = new FingerprintOperation(context);
            }
            fingerprintOperation = sFingerprintOperation;
        }
        return fingerprintOperation;
    }

    public FingerprintOperation(Context context) {
        this.mFingerprintManager = null;
        this.mFingerprintManager = (FingerprintManager) context.getSystemService(FingerprintManager.class);
        this.mUserId = context.getUserId();
    }

    public final synchronized byte[] sendRequest(byte[] bArr) {
        AuthnrLog.m123v("FPO", "sendRequest");
        if (this.mFingerprintManager == null) {
            AuthnrLog.m121e("FPO", "Fingerprint Service not found");
            return new byte[0];
        }
        if (bArr != null && bArr.length != 0) {
            byte[] bArr2 = new byte[bArr.length + 14];
            ByteBuffer wrap = ByteBuffer.wrap(bArr2);
            wrap.order(ByteOrder.LITTLE_ENDIAN);
            wrap.putShort(0, (short) 21249);
            wrap.putShort(2, (short) (10 + bArr.length));
            wrap.putShort(4, (short) 21250);
            wrap.putShort(6, (short) 6);
            System.arraycopy("authnr".getBytes(StandardCharsets.UTF_8), 0, bArr2, 8, 6);
            System.arraycopy(bArr, 0, bArr2, 14, bArr.length);
            byte[] bArr3 = new byte[10240];
            int semProcessFido = this.mFingerprintManager.semProcessFido(this.mUserId, bArr2, bArr3);
            if (semProcessFido == 0) {
                AuthnrLog.m121e("FPO", "request failed");
                return null;
            }
            return Arrays.copyOf(bArr3, semProcessFido);
        }
        AuthnrLog.m121e("FPO", "invalid input");
        return null;
    }

    public synchronized boolean setChallenge(byte[] bArr) {
        AuthnrLog.m123v("FPO", "set challenge");
        ByteBuffer allocate = ByteBuffer.allocate(bArr.length + 2 + 2);
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        allocate.order(byteOrder);
        allocate.putShort((short) 1);
        allocate.putShort((short) bArr.length);
        allocate.put(bArr);
        byte[] sendRequest = sendRequest(Arrays.copyOfRange(allocate.array(), 0, allocate.position()));
        if (sendRequest != null && sendRequest.length != 0) {
            ByteBuffer wrap = ByteBuffer.wrap(sendRequest);
            wrap.order(byteOrder);
            if (wrap.getShort() == 0) {
                return true;
            }
            AuthnrLog.m121e("FPO", "setChallenge failed");
            return false;
        }
        AuthnrLog.m121e("FPO", "sendRequest failed");
        return false;
    }

    public synchronized byte[] getWrappedObject(byte[] bArr) {
        AuthnrLog.m123v("FPO", "getWrappedObject");
        ByteBuffer allocate = ByteBuffer.allocate(bArr.length + 2 + 2);
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        allocate.order(byteOrder);
        allocate.putShort((short) 2);
        allocate.putShort((short) bArr.length);
        allocate.put(bArr);
        byte[] sendRequest = sendRequest(Arrays.copyOfRange(allocate.array(), 0, allocate.position()));
        if (sendRequest != null && sendRequest.length != 0) {
            ByteBuffer wrap = ByteBuffer.wrap(sendRequest);
            wrap.order(byteOrder);
            if (wrap.getShort() == 0) {
                return sendRequest;
            }
            AuthnrLog.m121e("FPO", "getWrappedObject failed");
            return new byte[0];
        }
        AuthnrLog.m121e("FPO", "sendRequest failed");
        return new byte[0];
    }
}
