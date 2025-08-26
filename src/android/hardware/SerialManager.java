package android.hardware;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import java.io.IOException;

/* loaded from: classes2.dex */
public class SerialManager {
    private static final String TAG = "SerialManager";
    private final Context mContext;
    private final ISerialManager mService;

    public SerialManager(Context context, ISerialManager iSerialManager) {
        this.mContext = context;
        this.mService = iSerialManager;
    }

    public String[] getSerialPorts() {
        try {
            return this.mService.getSerialPorts();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public SerialPort openSerialPort(String str, int i) throws IOException {
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenSerialPort = this.mService.openSerialPort(str);
            if (parcelFileDescriptorOpenSerialPort != null) {
                SerialPort serialPort = new SerialPort(str);
                serialPort.open(parcelFileDescriptorOpenSerialPort, i);
                return serialPort;
            }
            throw new IOException("Could not open serial port " + str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
