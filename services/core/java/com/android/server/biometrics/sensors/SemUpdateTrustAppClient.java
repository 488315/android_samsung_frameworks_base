package com.android.server.biometrics.sensors;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.Slog;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.log.BiometricContext;
import com.android.server.biometrics.log.BiometricLogger;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes.dex */
public abstract class SemUpdateTrustAppClient extends BaseClientMonitor {
    public int mDataTransmissionUnit;
    public int mErrorValue;
    public final String mPath;
    public int mSuccessValue;

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public int getProtoEnum() {
        return 0;
    }

    public abstract int sehInstallTAEnd(byte[] bArr);

    public abstract int sehInstallTAStart();

    public abstract int sehInstallTAWrite(byte[] bArr);

    public SemUpdateTrustAppClient(Context context, IBinder iBinder, ClientMonitorCallbackConverter clientMonitorCallbackConverter, String str, String str2, int i, int i2, BiometricContext biometricContext) {
        super(context, iBinder, clientMonitorCallbackConverter, 0, str, 0, i, new BiometricLogger(context, i2, 0, 0), biometricContext);
        this.mDataTransmissionUnit = 921600;
        this.mPath = str2;
        if (i2 == 1) {
            this.mErrorValue = -100;
            this.mSuccessValue = 100;
        } else if (i2 == 4) {
            this.mErrorValue = -100;
            this.mSuccessValue = 0;
            this.mDataTransmissionUnit = 3145728;
        }
        if (Utils.DEBUG) {
            Slog.d("Biometrics/SemUpdateTrustAppClient", "SemUpdateTrustAppClient: path = " + str2 + ", " + this.mDataTransmissionUnit);
        }
    }

    @Override // com.android.server.biometrics.sensors.BaseClientMonitor
    public void start(ClientMonitorCallback clientMonitorCallback) {
        super.start(clientMonitorCallback);
        startUpdate();
    }

    public final void startUpdate() {
        int i;
        Slog.d("Biometrics/SemUpdateTrustAppClient", "startUpdate: start");
        FileInputStream fileInputStream = null;
        try {
            File file = new File(this.mPath);
            if (!file.exists()) {
                Slog.w("Biometrics/SemUpdateTrustAppClient", "startUpdate: No file exist");
                handleUpdate(this.mErrorValue);
                return;
            }
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                ByteBuffer allocate = ByteBuffer.allocate(fileInputStream2.available());
                Slog.i("Biometrics/SemUpdateTrustAppClient", "startUpdate: size = " + fileInputStream2.available());
                byte[] bArr = new byte[102400];
                while (true) {
                    int read = fileInputStream2.read(bArr);
                    if (read == -1) {
                        break;
                    } else {
                        allocate.put(bArr, 0, read);
                    }
                }
                fileInputStream2.close();
                Slog.i("Biometrics/SemUpdateTrustAppClient", "startUpdate: done reading file");
                if (sehInstallTAStart() != 0) {
                    handleUpdate(this.mErrorValue);
                    return;
                }
                if (this.mDataTransmissionUnit == -1) {
                    if (sehInstallTAWrite(allocate.array()) != 0) {
                        Slog.w("Biometrics/SemUpdateTrustAppClient", "startUpdate: OPERATION_WRITE error");
                        handleUpdate(this.mErrorValue);
                        return;
                    }
                } else {
                    int ceil = (int) Math.ceil(allocate.position() / this.mDataTransmissionUnit);
                    if (Utils.DEBUG) {
                        Slog.i("Biometrics/SemUpdateTrustAppClient", "startUpdate: loopCnt:" + ceil);
                    }
                    allocate.position(0);
                    for (int i2 = 0; i2 < ceil; i2++) {
                        int min = Math.min(allocate.remaining(), this.mDataTransmissionUnit);
                        byte[] bArr2 = new byte[min];
                        boolean z = Utils.DEBUG;
                        if (z) {
                            Slog.i("Biometrics/SemUpdateTrustAppClient", "startUpdate: read length:" + min + ", byteBuffer.position()" + allocate.position() + ", byteBuffer.remaining():" + allocate.remaining());
                        }
                        allocate.get(bArr2, 0, min);
                        if (z) {
                            Slog.i("Biometrics/SemUpdateTrustAppClient", "startUpdate: byteBuffer.position() after get()" + allocate.position() + ", byteBuffer.remaining():" + allocate.remaining());
                        }
                        if (sehInstallTAWrite(bArr2) != 0) {
                            Slog.w("Biometrics/SemUpdateTrustAppClient", "startUpdate: OPERATION_WRITE error");
                            handleUpdate(this.mErrorValue);
                            return;
                        }
                    }
                }
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                    messageDigest.update(allocate.array());
                    byte[] digest = messageDigest.digest();
                    StringBuilder sb = new StringBuilder();
                    for (byte b : digest) {
                        sb.append(String.format("%02x", Byte.valueOf(b)));
                    }
                    if (Utils.DEBUG) {
                        Slog.w("Biometrics/SemUpdateTrustAppClient", "startUpdate: digest:" + sb.toString().trim());
                    }
                    if (sehInstallTAEnd(digest) != 0) {
                        Slog.w("Biometrics/SemUpdateTrustAppClient", "startUpdate: OPERATION_END error");
                        handleUpdate(this.mErrorValue);
                    } else {
                        handleUpdate(this.mSuccessValue);
                    }
                } catch (NoSuchAlgorithmException e) {
                    Slog.w("Biometrics/SemUpdateTrustAppClient", "startUpdate: failure to get MessageDigest instance", e);
                    handleUpdate(this.mErrorValue);
                }
            } catch (Exception e2) {
                e = e2;
                fileInputStream = fileInputStream2;
                Slog.i("Biometrics/SemUpdateTrustAppClient", "startUpdate: failure to read file");
                handleUpdate(this.mErrorValue);
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception unused) {
                        Slog.e("Biometrics/SemUpdateTrustAppClient", "startUpdate: failed to close file", e);
                    }
                }
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public final void handleUpdate(int i) {
        try {
            getListener().onSemTrustAppUpdateEvent(i);
        } catch (RemoteException e) {
            Log.w("Biometrics/SemUpdateTrustAppClient", "handleUpdate: " + e.getMessage());
        }
        this.mCallback.onClientFinished(this, i == this.mSuccessValue);
    }
}
