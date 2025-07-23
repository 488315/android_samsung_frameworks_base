package android.hardware;

import android.os.ParcelFileDescriptor;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public abstract class SerialManagerInternal {
    public abstract void addVirtualSerialPortForTest(String str, Supplier<ParcelFileDescriptor> supplier);

    public abstract void removeVirtualSerialPortForTest(String str);
}
