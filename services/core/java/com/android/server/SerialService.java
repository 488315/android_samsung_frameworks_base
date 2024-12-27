package com.android.server;

import android.content.Context;
import android.hardware.ISerialManager;
import android.hardware.SerialManagerInternal;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import com.android.internal.util.Preconditions;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class SerialService extends ISerialManager.Stub {
    public final AnonymousClass1 mInternal;
    public final LinkedHashMap mSerialPorts;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public SerialService mService;

        public Lifecycle(Context context) {
            super(context);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.SerialService] */
        @Override // com.android.server.SystemService
        public final void onStart() {
            ?? serialService = new SerialService(getContext());
            this.mService = serialService;
            publishBinderService("serial", serialService);
            publishLocalService(SerialManagerInternal.class, this.mService.mInternal);
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.SerialService$1] */
    public SerialService(Context context) {
        super(PermissionEnforcer.fromContext(context));
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.mSerialPorts = linkedHashMap;
        this.mInternal = new SerialManagerInternal() { // from class: com.android.server.SerialService.1
            public final void addVirtualSerialPortForTest(String str, Supplier supplier) {
                synchronized (SerialService.this.mSerialPorts) {
                    Preconditions.checkState(!SerialService.this.mSerialPorts.containsKey(str), "Port " + str + " already defined");
                    Preconditions.checkArgument(str.startsWith("virtual:"), "Port " + str + " must be under virtual:");
                    SerialService.this.mSerialPorts.put(str, supplier);
                }
            }

            public final void removeVirtualSerialPortForTest(String str) {
                synchronized (SerialService.this.mSerialPorts) {
                    Preconditions.checkState(SerialService.this.mSerialPorts.containsKey(str), "Port " + str + " not yet defined");
                    Preconditions.checkArgument(str.startsWith("virtual:"), "Port " + str + " must be under virtual:");
                    SerialService.this.mSerialPorts.remove(str);
                }
            }
        };
        synchronized (linkedHashMap) {
            try {
                for (final String str : context.getResources().getStringArray(17236314)) {
                    this.mSerialPorts.put(str, new Supplier() { // from class: com.android.server.SerialService$$ExternalSyntheticLambda0
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            ParcelFileDescriptor native_open;
                            native_open = SerialService.this.native_open(str);
                            return native_open;
                        }
                    });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native ParcelFileDescriptor native_open(String str);

    public final String[] getSerialPorts() {
        String[] strArr;
        super.getSerialPorts_enforcePermission();
        synchronized (this.mSerialPorts) {
            try {
                ArrayList arrayList = new ArrayList();
                for (String str : this.mSerialPorts.keySet()) {
                    if (!str.startsWith("virtual:") && !new File(str).exists()) {
                    }
                    arrayList.add(str);
                }
                strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
            } catch (Throwable th) {
                throw th;
            }
        }
        return strArr;
    }

    public final ParcelFileDescriptor openSerialPort(String str) {
        ParcelFileDescriptor parcelFileDescriptor;
        super.openSerialPort_enforcePermission();
        synchronized (this.mSerialPorts) {
            try {
                Supplier supplier = (Supplier) this.mSerialPorts.get(str);
                if (supplier == null) {
                    throw new IllegalArgumentException("Invalid serial port " + str);
                }
                parcelFileDescriptor = (ParcelFileDescriptor) supplier.get();
            } catch (Throwable th) {
                throw th;
            }
        }
        return parcelFileDescriptor;
    }
}
