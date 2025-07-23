package android.printservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.print.PrintJobInfo;
import android.print.PrinterId;
import android.printservice.IPrintService;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class PrintService extends Service {
    private static final boolean DEBUG = false;
    public static final String EXTRA_CAN_SELECT_PRINTER = "android.printservice.extra.CAN_SELECT_PRINTER";
    public static final String EXTRA_PRINTER_INFO = "android.intent.extra.print.EXTRA_PRINTER_INFO";
    public static final String EXTRA_PRINT_DOCUMENT_INFO = "android.printservice.extra.PRINT_DOCUMENT_INFO";
    public static final String EXTRA_PRINT_JOB_INFO = "android.intent.extra.print.PRINT_JOB_INFO";
    public static final String EXTRA_SELECT_PRINTER = "android.printservice.extra.SELECT_PRINTER";
    private static final String LOG_TAG = "PrintService";
    public static final String SERVICE_INTERFACE = "android.printservice.PrintService";
    public static final String SERVICE_META_DATA = "android.printservice";
    private IPrintServiceClient mClient;
    private PrinterDiscoverySession mDiscoverySession;
    private Handler mHandler;
    private int mLastSessionId = -1;

    protected void onConnected() {
    }

    protected abstract PrinterDiscoverySession onCreatePrinterDiscoverySession();

    protected void onDisconnected() {
    }

    protected abstract void onPrintJobQueued(PrintJob printJob);

    protected abstract void onRequestCancelPrintJob(PrintJob printJob);

    @Override // android.app.Service, android.content.ContextWrapper
    protected final void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        this.mHandler = new ServiceHandler(context.getMainLooper());
    }

    public final List<PrintJob> getActivePrintJobs() {
        ArrayList arrayList;
        throwIfNotCalledOnMainThread();
        IPrintServiceClient iPrintServiceClient = this.mClient;
        if (iPrintServiceClient == null) {
            return Collections.EMPTY_LIST;
        }
        try {
            List<PrintJobInfo> printJobInfos = iPrintServiceClient.getPrintJobInfos();
            if (printJobInfos != null) {
                int size = printJobInfos.size();
                arrayList = new ArrayList(size);
                for (int i = 0; i < size; i++) {
                    arrayList.add(new PrintJob(this, printJobInfos.get(i), this.mClient));
                }
            } else {
                arrayList = null;
            }
            if (arrayList != null) {
                return arrayList;
            }
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error calling getPrintJobs()", e);
        }
        return Collections.EMPTY_LIST;
    }

    public final PrinterId generatePrinterId(String str) {
        throwIfNotCalledOnMainThread();
        return new PrinterId(new ComponentName(getPackageName(), getClass().getName()), (String) Preconditions.checkNotNull(str, "localId cannot be null"));
    }

    static void throwIfNotCalledOnMainThread() {
        if (!Looper.getMainLooper().isCurrentThread()) {
            throw new IllegalAccessError("must be called from the main thread");
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return new IPrintService.Stub() { // from class: android.printservice.PrintService.1
            @Override // android.printservice.IPrintService
            public void createPrinterDiscoverySession() {
                PrintService.this.mHandler.sendEmptyMessage(1);
            }

            @Override // android.printservice.IPrintService
            public void destroyPrinterDiscoverySession() {
                PrintService.this.mHandler.sendEmptyMessage(2);
            }

            @Override // android.printservice.IPrintService
            public void startPrinterDiscovery(List<PrinterId> list) {
                PrintService.this.mHandler.obtainMessage(3, list).sendToTarget();
            }

            @Override // android.printservice.IPrintService
            public void stopPrinterDiscovery() {
                PrintService.this.mHandler.sendEmptyMessage(4);
            }

            @Override // android.printservice.IPrintService
            public void validatePrinters(List<PrinterId> list) {
                PrintService.this.mHandler.obtainMessage(5, list).sendToTarget();
            }

            @Override // android.printservice.IPrintService
            public void startPrinterStateTracking(PrinterId printerId) {
                PrintService.this.mHandler.obtainMessage(6, printerId).sendToTarget();
            }

            @Override // android.printservice.IPrintService
            public void requestCustomPrinterIcon(PrinterId printerId) {
                PrintService.this.mHandler.obtainMessage(7, printerId).sendToTarget();
            }

            @Override // android.printservice.IPrintService
            public void stopPrinterStateTracking(PrinterId printerId) {
                PrintService.this.mHandler.obtainMessage(8, printerId).sendToTarget();
            }

            @Override // android.printservice.IPrintService
            public void setClient(IPrintServiceClient iPrintServiceClient) {
                PrintService.this.mHandler.obtainMessage(11, iPrintServiceClient).sendToTarget();
            }

            @Override // android.printservice.IPrintService
            public void requestCancelPrintJob(PrintJobInfo printJobInfo) {
                PrintService.this.mHandler.obtainMessage(10, printJobInfo).sendToTarget();
            }

            @Override // android.printservice.IPrintService
            public void onPrintJobQueued(PrintJobInfo printJobInfo) {
                PrintService.this.mHandler.obtainMessage(9, printJobInfo).sendToTarget();
            }
        };
    }

    private final class ServiceHandler extends Handler {
        public static final int MSG_CREATE_PRINTER_DISCOVERY_SESSION = 1;
        public static final int MSG_DESTROY_PRINTER_DISCOVERY_SESSION = 2;
        public static final int MSG_ON_PRINTJOB_QUEUED = 9;
        public static final int MSG_ON_REQUEST_CANCEL_PRINTJOB = 10;
        public static final int MSG_REQUEST_CUSTOM_PRINTER_ICON = 7;
        public static final int MSG_SET_CLIENT = 11;
        public static final int MSG_START_PRINTER_DISCOVERY = 3;
        public static final int MSG_START_PRINTER_STATE_TRACKING = 6;
        public static final int MSG_STOP_PRINTER_DISCOVERY = 4;
        public static final int MSG_STOP_PRINTER_STATE_TRACKING = 8;
        public static final int MSG_VALIDATE_PRINTERS = 5;

        public ServiceHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            switch (i) {
                case 1:
                    PrinterDiscoverySession onCreatePrinterDiscoverySession = PrintService.this.onCreatePrinterDiscoverySession();
                    if (onCreatePrinterDiscoverySession == null) {
                        throw new NullPointerException("session cannot be null");
                    }
                    if (onCreatePrinterDiscoverySession.getId() == PrintService.this.mLastSessionId) {
                        throw new IllegalStateException("cannot reuse session instances");
                    }
                    PrintService.this.mDiscoverySession = onCreatePrinterDiscoverySession;
                    PrintService.this.mLastSessionId = onCreatePrinterDiscoverySession.getId();
                    onCreatePrinterDiscoverySession.setObserver(PrintService.this.mClient);
                    return;
                case 2:
                    if (PrintService.this.mDiscoverySession != null) {
                        PrintService.this.mDiscoverySession.destroy();
                        PrintService.this.mDiscoverySession = null;
                        return;
                    }
                    return;
                case 3:
                    if (PrintService.this.mDiscoverySession != null) {
                        PrintService.this.mDiscoverySession.startPrinterDiscovery((ArrayList) message.obj);
                        return;
                    }
                    return;
                case 4:
                    if (PrintService.this.mDiscoverySession != null) {
                        PrintService.this.mDiscoverySession.stopPrinterDiscovery();
                        return;
                    }
                    return;
                case 5:
                    if (PrintService.this.mDiscoverySession != null) {
                        PrintService.this.mDiscoverySession.validatePrinters((List) message.obj);
                        return;
                    }
                    return;
                case 6:
                    if (PrintService.this.mDiscoverySession != null) {
                        PrintService.this.mDiscoverySession.startPrinterStateTracking((PrinterId) message.obj);
                        return;
                    }
                    return;
                case 7:
                    if (PrintService.this.mDiscoverySession != null) {
                        PrintService.this.mDiscoverySession.requestCustomPrinterIcon((PrinterId) message.obj);
                        return;
                    }
                    return;
                case 8:
                    if (PrintService.this.mDiscoverySession != null) {
                        PrintService.this.mDiscoverySession.stopPrinterStateTracking((PrinterId) message.obj);
                        return;
                    }
                    return;
                case 9:
                    PrintJobInfo printJobInfo = (PrintJobInfo) message.obj;
                    PrintService printService = PrintService.this;
                    PrintService printService2 = PrintService.this;
                    printService.onPrintJobQueued(new PrintJob(printService2, printJobInfo, printService2.mClient));
                    return;
                case 10:
                    PrintJobInfo printJobInfo2 = (PrintJobInfo) message.obj;
                    PrintService printService3 = PrintService.this;
                    PrintService printService4 = PrintService.this;
                    printService3.onRequestCancelPrintJob(new PrintJob(printService4, printJobInfo2, printService4.mClient));
                    return;
                case 11:
                    PrintService.this.mClient = (IPrintServiceClient) message.obj;
                    if (PrintService.this.mClient != null) {
                        PrintService.this.onConnected();
                        return;
                    } else {
                        PrintService.this.onDisconnected();
                        return;
                    }
                default:
                    throw new IllegalArgumentException("Unknown message: " + i);
            }
        }
    }
}
