package android.view;

import android.os.IBinder;
import android.util.ArrayMap;
import java.util.Iterator;
import java.util.Objects;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes4.dex */
public abstract class SurfaceControlHdrLayerInfoListener {
    private static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(SurfaceControlHdrLayerInfoListener.class.getClassLoader(), nGetDestructor());
    private ArrayMap<IBinder, Runnable> mRegisteredListeners = new ArrayMap<>();

    private static native long nGetDestructor();

    private native long nRegister(IBinder iBinder);

    public abstract void onHdrInfoChanged(IBinder iBinder, int i, int i2, int i3, int i4, float f);

    public void register(IBinder iBinder) {
        Objects.requireNonNull(iBinder);
        synchronized (this) {
            if (this.mRegisteredListeners.containsKey(iBinder)) {
                return;
            }
            this.mRegisteredListeners.put(iBinder, sRegistry.registerNativeAllocation(this, nRegister(iBinder)));
        }
    }

    public void unregister(IBinder iBinder) {
        Runnable runnableRemove;
        Objects.requireNonNull(iBinder);
        synchronized (this) {
            runnableRemove = this.mRegisteredListeners.remove(iBinder);
        }
        if (runnableRemove != null) {
            runnableRemove.run();
        }
    }

    public void unregisterAll() {
        ArrayMap<IBinder, Runnable> arrayMap;
        synchronized (this) {
            arrayMap = this.mRegisteredListeners;
            this.mRegisteredListeners = new ArrayMap<>();
        }
        Iterator<Runnable> it = arrayMap.values().iterator();
        while (it.hasNext()) {
            it.next().run();
        }
    }
}
