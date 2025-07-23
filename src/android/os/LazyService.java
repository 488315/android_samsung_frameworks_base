package android.os;

import android.content.Context;
import android.os.ILazyService;
import android.util.ArrayMap;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class LazyService extends ILazyService.Stub {
    private static final String TAG = "LazyService";
    protected static List<String> historyServices = Collections.synchronizedList(new ArrayList());
    private Context mContext;
    private Map<String, IServiceCreator> serviceCreators = new ArrayMap();
    private List<String> services = new ArrayList();

    public LazyService(Context context) {
        this.mContext = context;
    }

    @Override // android.os.ILazyService
    public IBinder getService(String str) {
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (this) {
            IServiceCreator iServiceCreator = this.serviceCreators.get(str);
            IBinder iBinder = null;
            if (iServiceCreator == null) {
                return null;
            }
            this.serviceCreators.remove(str);
            Log.d(TAG, str + " getService");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            IBinder createService = iServiceCreator.createService(this.mContext);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            try {
                ServiceManager.addService(str, createService);
                iBinder = createService;
            } catch (Throwable th) {
                Log.e(TAG, "Failure adding " + str + " Service", th);
                historyServices.add("Failure adding " + str + " Service, Exception : " + th.toString());
            }
            Log.d(TAG, (System.currentTimeMillis() - currentTimeMillis) + "ms");
            historyServices.add("GetService " + str + " Service : " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
            return iBinder;
        }
    }

    public synchronized void addService(String str, IServiceCreator iServiceCreator) {
        if (this.serviceCreators.get(str) != null) {
            Log.e(TAG, str + " is already in lazy service manager");
            throw new IllegalArgumentException("DuplicatedName");
        }
        this.services.add(str);
        this.serviceCreators.put(str, iServiceCreator);
    }

    public void addService(String str, Class cls) {
        try {
            addService(str, new DefaultServiceCreator(cls));
            Log.d(TAG, str + " addService");
        } catch (NoSuchMethodException e) {
            Log.e(TAG, str + " error in addService", e);
            historyServices.add(str + " error in addService : " + e.toString());
        }
    }

    @Override // android.os.Binder
    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print("# Service(lazy) List : ");
        printWriter.println();
        Iterator<String> it = this.services.iterator();
        while (it.hasNext()) {
            printWriter.print(it.next() + " ");
            printWriter.println();
        }
        printWriter.println();
        printWriter.print("# Uninitialized services : ");
        printWriter.println();
        synchronized (this) {
            Iterator<String> it2 = this.serviceCreators.keySet().iterator();
            while (it2.hasNext()) {
                printWriter.print(it2.next() + " ");
                printWriter.println();
            }
        }
        printWriter.println();
        printWriter.print("# Logs :");
        printWriter.println();
        Iterator<String> it3 = historyServices.iterator();
        while (it3.hasNext()) {
            printWriter.print(it3.next());
            printWriter.println();
        }
    }

    public static class DefaultServiceCreator implements IServiceCreator {
        private Constructor mConstructor;

        public DefaultServiceCreator(Class cls) throws NoSuchMethodException {
            this.mConstructor = cls.getConstructor(Context.class);
        }

        @Override // android.os.IServiceCreator
        public IBinder createService(Context context) {
            try {
                return (IBinder) this.mConstructor.newInstance(context);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                Log.e(LazyService.TAG, "Error in createService (DefaultServiceCreator)", e);
                LazyService.historyServices.add("Failure creating Exception : " + e.toString());
                return null;
            }
        }
    }
}
