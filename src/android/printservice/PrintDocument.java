package android.printservice;

import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.print.PrintDocumentInfo;
import android.print.PrintJobId;
import android.util.Log;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class PrintDocument {
    private static final String LOG_TAG = "PrintDocument";
    private final PrintDocumentInfo mInfo;
    private final PrintJobId mPrintJobId;
    private final IPrintServiceClient mPrintServiceClient;

    PrintDocument(PrintJobId printJobId, IPrintServiceClient iPrintServiceClient, PrintDocumentInfo printDocumentInfo) {
        this.mPrintJobId = printJobId;
        this.mPrintServiceClient = iPrintServiceClient;
        this.mInfo = printDocumentInfo;
    }

    public PrintDocumentInfo getInfo() {
        PrintService.throwIfNotCalledOnMainThread();
        return this.mInfo;
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x0020: MOVE (r2 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:9:0x0020 */
    /* JADX WARN: Removed duplicated region for block: B:34:0x003e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x002f A[EXC_TOP_SPLITTER, PHI: r3
      0x002f: PHI (r3v4 android.os.ParcelFileDescriptor) = (r3v3 android.os.ParcelFileDescriptor), (r3v5 android.os.ParcelFileDescriptor) binds: [B:25:0x0038, B:19:0x002d] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ParcelFileDescriptor getData() throws Throwable {
        ParcelFileDescriptor parcelFileDescriptor;
        AutoCloseable autoCloseable;
        PrintService.throwIfNotCalledOnMainThread();
        AutoCloseable autoCloseable2 = null;
        try {
            try {
                ParcelFileDescriptor[] parcelFileDescriptorArrCreatePipe = ParcelFileDescriptor.createPipe();
                ParcelFileDescriptor parcelFileDescriptor2 = parcelFileDescriptorArrCreatePipe[0];
                parcelFileDescriptor = parcelFileDescriptorArrCreatePipe[1];
                try {
                    this.mPrintServiceClient.writePrintJobData(parcelFileDescriptor, this.mPrintJobId);
                    if (parcelFileDescriptor != null) {
                        try {
                            parcelFileDescriptor.close();
                        } catch (IOException unused) {
                        }
                    }
                    return parcelFileDescriptor2;
                } catch (RemoteException e) {
                    e = e;
                    Log.e(LOG_TAG, "Error calling getting print job data!", e);
                    if (parcelFileDescriptor != null) {
                        try {
                            parcelFileDescriptor.close();
                        } catch (IOException unused2) {
                        }
                    }
                    return null;
                } catch (IOException e2) {
                    e = e2;
                    Log.e(LOG_TAG, "Error calling getting print job data!", e);
                    if (parcelFileDescriptor != null) {
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                autoCloseable2 = autoCloseable;
                if (autoCloseable2 != null) {
                    try {
                        autoCloseable2.close();
                    } catch (IOException unused3) {
                    }
                }
                throw th;
            }
        } catch (RemoteException e3) {
            e = e3;
            parcelFileDescriptor = null;
        } catch (IOException e4) {
            e = e4;
            parcelFileDescriptor = null;
        } catch (Throwable th2) {
            th = th2;
            if (autoCloseable2 != null) {
            }
            throw th;
        }
    }
}
