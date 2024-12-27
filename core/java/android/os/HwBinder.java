package android.os;

import android.annotation.SystemApi;
import android.util.Log;

import libcore.util.NativeAllocationRegistry;

import java.util.NoSuchElementException;

@SystemApi
/* loaded from: classes3.dex */
public abstract class HwBinder implements IHwBinder {
    private static final String TAG = "HwBinder";
    private static final NativeAllocationRegistry sNativeRegistry;
    private long mNativeContext;

    public static final native void configureRpcThreadpool(long j, boolean z);

    public static final native IHwBinder getService(String str, String str2, boolean z)
            throws RemoteException, NoSuchElementException;

    public static final native void joinRpcThreadpool();

    private static final native long native_init();

    private static native void native_report_sysprop_change();

    private final native void native_setup();

    public static final native void setTrebleTestingOverride(boolean z);

    public abstract void onTransact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2)
            throws RemoteException;

    public final native void registerService(String str) throws RemoteException;

    @Override // android.os.IHwBinder
    public final native void transact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2)
            throws RemoteException;

    public HwBinder() {
        native_setup();
        sNativeRegistry.registerNativeAllocation(this, this.mNativeContext);
    }

    public static final IHwBinder getService(String iface, String serviceName)
            throws RemoteException, NoSuchElementException {
        if (!HidlSupport.isHidlSupported()
                && (iface.equals(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName)
                        || iface.equals(
                                android.internal.hidl.manager.V1_1.IServiceManager.kInterfaceName)
                        || iface.equals(
                                android.internal.hidl.manager.V1_2.IServiceManager
                                        .kInterfaceName))) {
            Log.i(
                    TAG,
                    "Replacing Java hwservicemanager with a fake HwNoService because HIDL is not"
                        + " supported on this device.");
            return new HwNoService();
        }
        return getService(iface, serviceName, false);
    }

    static {
        long freeFunction = native_init();
        sNativeRegistry =
                new NativeAllocationRegistry(HwBinder.class.getClassLoader(), freeFunction, 128L);
    }

    public static void enableInstrumentation() {
        native_report_sysprop_change();
    }

    public static void reportSyspropChanged() {
        native_report_sysprop_change();
    }
}
