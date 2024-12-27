package com.android.server.locksettings;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.weaver.IWeaver;
import android.hardware.weaver.V1_0.IWeaver;
import android.hardware.weaver.WeaverConfig;
import android.hardware.weaver.WeaverReadResponse;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IBinder;
import android.os.ServiceSpecificException;
import android.util.Slog;

import com.android.server.NandswapManager$$ExternalSyntheticOutline0;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WeaverHidlAdapter implements IWeaver {
    public final android.hardware.weaver.V1_0.IWeaver mImpl;

    public WeaverHidlAdapter(android.hardware.weaver.V1_0.IWeaver iWeaver) {
        this.mImpl = iWeaver;
    }

    public static ArrayList toByteArrayList(byte[] bArr) {
        ArrayList arrayList = new ArrayList(bArr.length);
        for (byte b : bArr) {
            arrayList.add(Byte.valueOf(b));
        }
        return arrayList;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        throw new UnsupportedOperationException("WeaverHidlAdapter does not support asBinder");
    }

    @Override // android.hardware.weaver.IWeaver
    public final WeaverConfig getConfig() {
        WeaverConfig[] weaverConfigArr = new WeaverConfig[1];
        IWeaver.Proxy proxy = (IWeaver.Proxy) this.mImpl;
        proxy.getClass();
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken("android.hardware.weaver@1.0::IWeaver");
        HwParcel hwParcel2 = new HwParcel();
        try {
            proxy.mRemote.transact(1, hwParcel, hwParcel2, 0);
            hwParcel2.verifySuccess();
            hwParcel.releaseTemporaryStorage();
            int readInt32 = hwParcel2.readInt32();
            HwBlob readBuffer = hwParcel2.readBuffer(12L);
            int int32 = readBuffer.getInt32(0L);
            int int322 = readBuffer.getInt32(4L);
            int int323 = readBuffer.getInt32(8L);
            if (readInt32 == 0) {
                WeaverConfig weaverConfig = new WeaverConfig();
                weaverConfig.slots = int32;
                weaverConfig.keySize = int322;
                weaverConfig.valueSize = int323;
                weaverConfigArr[0] = weaverConfig;
            } else {
                Slog.e(
                        "WeaverHidlAdapter",
                        "Failed to get HIDL weaver config. status: "
                                + readInt32
                                + ", slots: "
                                + int32);
            }
            hwParcel2.release();
            return weaverConfigArr[0];
        } catch (Throwable th) {
            hwParcel2.release();
            throw th;
        }
    }

    @Override // android.hardware.weaver.IWeaver
    public final int getInterfaceVersion() {
        return 2;
    }

    @Override // android.hardware.weaver.IWeaver
    public final WeaverReadResponse read(int i, byte[] bArr) {
        final WeaverReadResponse[] weaverReadResponseArr = new WeaverReadResponse[1];
        ((IWeaver.Proxy) this.mImpl)
                .read(
                        i,
                        toByteArrayList(bArr),
                        new IWeaver
                                .readCallback() { // from class:
                                                  // com.android.server.locksettings.WeaverHidlAdapter$$ExternalSyntheticLambda0
                            @Override // android.hardware.weaver.V1_0.IWeaver.readCallback
                            public final void onValues(
                                    int i2,
                                    android.hardware.weaver.V1_0.WeaverReadResponse
                                            weaverReadResponse) {
                                WeaverReadResponse weaverReadResponse2 = new WeaverReadResponse();
                                if (i2 == 0) {
                                    weaverReadResponse2.status = 0;
                                } else if (i2 == 1) {
                                    weaverReadResponse2.status = 1;
                                } else if (i2 == 2) {
                                    weaverReadResponse2.status = 2;
                                } else if (i2 != 3) {
                                    NandswapManager$$ExternalSyntheticOutline0.m(
                                            i2, "Unexpected status in read: ", "WeaverHidlAdapter");
                                    weaverReadResponse2.status = 1;
                                } else {
                                    weaverReadResponse2.status = 3;
                                }
                                weaverReadResponse2.timeout = weaverReadResponse.timeout;
                                ArrayList arrayList = weaverReadResponse.value;
                                byte[] bArr2 = new byte[arrayList.size()];
                                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                                    bArr2[i3] = ((Byte) arrayList.get(i3)).byteValue();
                                }
                                weaverReadResponse2.value = bArr2;
                                weaverReadResponseArr[0] = weaverReadResponse2;
                            }
                        });
        return weaverReadResponseArr[0];
    }

    @Override // android.hardware.weaver.IWeaver
    public final void write(int i, byte[] bArr, byte[] bArr2) {
        android.hardware.weaver.V1_0.IWeaver iWeaver = this.mImpl;
        ArrayList byteArrayList = toByteArrayList(bArr);
        ArrayList byteArrayList2 = toByteArrayList(bArr2);
        IWeaver.Proxy proxy = (IWeaver.Proxy) iWeaver;
        proxy.getClass();
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken("android.hardware.weaver@1.0::IWeaver");
        hwParcel.writeInt32(i);
        hwParcel.writeInt8Vector(byteArrayList);
        hwParcel.writeInt8Vector(byteArrayList2);
        HwParcel hwParcel2 = new HwParcel();
        try {
            proxy.mRemote.transact(2, hwParcel, hwParcel2, 0);
            hwParcel2.verifySuccess();
            hwParcel.releaseTemporaryStorage();
            int readInt32 = hwParcel2.readInt32();
            if (readInt32 != 0) {
                throw new ServiceSpecificException(
                        1,
                        VibrationParam$1$$ExternalSyntheticOutline0.m(
                                readInt32, "Failed IWeaver.write call, status: "));
            }
        } finally {
            hwParcel2.release();
        }
    }
}
