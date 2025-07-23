package android.print;

import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.print.IPrinterDiscoveryObserver;
import android.util.ArrayMap;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/* loaded from: classes3.dex */
public final class PrinterDiscoverySession {
    private static final String LOG_TAG = "PrinterDiscoverySession";
    private static final int MSG_PRINTERS_ADDED = 1;
    private static final int MSG_PRINTERS_REMOVED = 2;
    private final Handler mHandler;
    private boolean mIsPrinterDiscoveryStarted;
    private OnPrintersChangeListener mListener;
    private IPrinterDiscoveryObserver mObserver;
    private final IPrintManager mPrintManager;
    private final LinkedHashMap<PrinterId, PrinterInfo> mPrinters = new LinkedHashMap<>();
    private final int mUserId;

    public interface OnPrintersChangeListener {
        void onPrintersChanged();
    }

    PrinterDiscoverySession(IPrintManager iPrintManager, Context context, int i) {
        this.mPrintManager = iPrintManager;
        this.mUserId = i;
        this.mHandler = new SessionHandler(context.getMainLooper());
        PrinterDiscoveryObserver printerDiscoveryObserver = new PrinterDiscoveryObserver(this);
        this.mObserver = printerDiscoveryObserver;
        try {
            iPrintManager.createPrinterDiscoverySession(printerDiscoveryObserver, i);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error creating printer discovery session", e);
        }
    }

    public final void startPrinterDiscovery(List<PrinterId> list) {
        if (isDestroyed()) {
            Log.w(LOG_TAG, "Ignoring start printers discovery - session destroyed");
            return;
        }
        if (this.mIsPrinterDiscoveryStarted) {
            return;
        }
        this.mIsPrinterDiscoveryStarted = true;
        try {
            this.mPrintManager.startPrinterDiscovery(this.mObserver, list, this.mUserId);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error starting printer discovery", e);
        }
    }

    public final void stopPrinterDiscovery() {
        if (isDestroyed()) {
            Log.w(LOG_TAG, "Ignoring stop printers discovery - session destroyed");
            return;
        }
        if (this.mIsPrinterDiscoveryStarted) {
            this.mIsPrinterDiscoveryStarted = false;
            try {
                this.mPrintManager.stopPrinterDiscovery(this.mObserver, this.mUserId);
            } catch (RemoteException e) {
                Log.e(LOG_TAG, "Error stopping printer discovery", e);
            }
        }
    }

    public final void startPrinterStateTracking(PrinterId printerId) {
        if (isDestroyed()) {
            Log.w(LOG_TAG, "Ignoring start printer state tracking - session destroyed");
            return;
        }
        try {
            this.mPrintManager.startPrinterStateTracking(printerId, this.mUserId);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error starting printer state tracking", e);
        }
    }

    public final void stopPrinterStateTracking(PrinterId printerId) {
        if (isDestroyed()) {
            Log.w(LOG_TAG, "Ignoring stop printer state tracking - session destroyed");
            return;
        }
        try {
            this.mPrintManager.stopPrinterStateTracking(printerId, this.mUserId);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error stopping printer state tracking", e);
        }
    }

    public final void validatePrinters(List<PrinterId> list) {
        if (isDestroyed()) {
            Log.w(LOG_TAG, "Ignoring validate printers - session destroyed");
            return;
        }
        try {
            this.mPrintManager.validatePrinters(list, this.mUserId);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error validating printers", e);
        }
    }

    public final void destroy() {
        if (isDestroyed()) {
            Log.w(LOG_TAG, "Ignoring destroy - session destroyed");
        }
        destroyNoCheck();
    }

    public final List<PrinterInfo> getPrinters() {
        if (isDestroyed()) {
            Log.w(LOG_TAG, "Ignoring get printers - session destroyed");
            return Collections.EMPTY_LIST;
        }
        return new ArrayList(this.mPrinters.values());
    }

    public final boolean isDestroyed() {
        throwIfNotCalledOnMainThread();
        return isDestroyedNoCheck();
    }

    public final boolean isPrinterDiscoveryStarted() {
        throwIfNotCalledOnMainThread();
        return this.mIsPrinterDiscoveryStarted;
    }

    public final void setOnPrintersChangeListener(OnPrintersChangeListener onPrintersChangeListener) {
        throwIfNotCalledOnMainThread();
        this.mListener = onPrintersChangeListener;
    }

    protected final void finalize() throws Throwable {
        if (!isDestroyedNoCheck()) {
            Log.e(LOG_TAG, "Destroying leaked printer discovery session");
            destroyNoCheck();
        }
        super.finalize();
    }

    private boolean isDestroyedNoCheck() {
        return this.mObserver == null;
    }

    private void destroyNoCheck() {
        stopPrinterDiscovery();
        try {
            this.mPrintManager.destroyPrinterDiscoverySession(this.mObserver, this.mUserId);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error destroying printer discovery session", e);
        } finally {
            this.mObserver = null;
            this.mPrinters.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePrintersAdded(List<PrinterInfo> list) {
        if (isDestroyed()) {
            return;
        }
        int i = 0;
        if (this.mPrinters.isEmpty()) {
            int size = list.size();
            while (i < size) {
                PrinterInfo printerInfo = list.get(i);
                this.mPrinters.put(printerInfo.getId(), printerInfo);
                i++;
            }
            notifyOnPrintersChanged();
            return;
        }
        ArrayMap arrayMap = new ArrayMap();
        int size2 = list.size();
        while (i < size2) {
            PrinterInfo printerInfo2 = list.get(i);
            arrayMap.put(printerInfo2.getId(), printerInfo2);
            i++;
        }
        for (PrinterId printerId : this.mPrinters.keySet()) {
            PrinterInfo printerInfo3 = (PrinterInfo) arrayMap.remove(printerId);
            if (printerInfo3 != null) {
                this.mPrinters.put(printerId, printerInfo3);
            }
        }
        this.mPrinters.putAll(arrayMap);
        notifyOnPrintersChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePrintersRemoved(List<PrinterId> list) {
        if (isDestroyed()) {
            return;
        }
        int size = list.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            if (this.mPrinters.remove(list.get(i)) != null) {
                z = true;
            }
        }
        if (z) {
            notifyOnPrintersChanged();
        }
    }

    private void notifyOnPrintersChanged() {
        OnPrintersChangeListener onPrintersChangeListener = this.mListener;
        if (onPrintersChangeListener != null) {
            onPrintersChangeListener.onPrintersChanged();
        }
    }

    private static void throwIfNotCalledOnMainThread() {
        if (!Looper.getMainLooper().isCurrentThread()) {
            throw new IllegalAccessError("must be called from the main thread");
        }
    }

    private final class SessionHandler extends Handler {
        public SessionHandler(Looper looper) {
            super(looper, null, false);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                PrinterDiscoverySession.this.handlePrintersAdded((List) message.obj);
            } else {
                if (i != 2) {
                    return;
                }
                PrinterDiscoverySession.this.handlePrintersRemoved((List) message.obj);
            }
        }
    }

    public static final class PrinterDiscoveryObserver extends IPrinterDiscoveryObserver.Stub {
        private final WeakReference<PrinterDiscoverySession> mWeakSession;

        public PrinterDiscoveryObserver(PrinterDiscoverySession printerDiscoverySession) {
            this.mWeakSession = new WeakReference<>(printerDiscoverySession);
        }

        @Override // android.print.IPrinterDiscoveryObserver
        public void onPrintersAdded(ParceledListSlice parceledListSlice) {
            PrinterDiscoverySession printerDiscoverySession = this.mWeakSession.get();
            if (printerDiscoverySession != null) {
                printerDiscoverySession.mHandler.obtainMessage(1, parceledListSlice.getList()).sendToTarget();
            }
        }

        @Override // android.print.IPrinterDiscoveryObserver
        public void onPrintersRemoved(ParceledListSlice parceledListSlice) {
            PrinterDiscoverySession printerDiscoverySession = this.mWeakSession.get();
            if (printerDiscoverySession != null) {
                printerDiscoverySession.mHandler.obtainMessage(2, parceledListSlice.getList()).sendToTarget();
            }
        }
    }
}
