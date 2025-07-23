package android.printservice;

import android.content.Context;
import android.os.RemoteException;
import android.print.PrintJobId;
import android.print.PrintJobInfo;
import android.util.Log;

/* loaded from: classes3.dex */
public final class PrintJob {
    private static final String LOG_TAG = "PrintJob";
    private PrintJobInfo mCachedInfo;
    private final Context mContext;
    private final PrintDocument mDocument;
    private final IPrintServiceClient mPrintServiceClient;

    PrintJob(Context context, PrintJobInfo printJobInfo, IPrintServiceClient iPrintServiceClient) {
        this.mContext = context;
        this.mCachedInfo = printJobInfo;
        this.mPrintServiceClient = iPrintServiceClient;
        this.mDocument = new PrintDocument(this.mCachedInfo.getId(), iPrintServiceClient, printJobInfo.getDocumentInfo());
    }

    public PrintJobId getId() {
        PrintService.throwIfNotCalledOnMainThread();
        return this.mCachedInfo.getId();
    }

    public PrintJobInfo getInfo() {
        PrintJobInfo printJobInfo;
        PrintService.throwIfNotCalledOnMainThread();
        if (isInImmutableState()) {
            return this.mCachedInfo;
        }
        try {
            printJobInfo = this.mPrintServiceClient.getPrintJobInfo(this.mCachedInfo.getId());
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Couldn't get info for job: " + this.mCachedInfo.getId(), e);
            printJobInfo = null;
        }
        if (printJobInfo != null) {
            this.mCachedInfo = printJobInfo;
        }
        return this.mCachedInfo;
    }

    public PrintDocument getDocument() {
        PrintService.throwIfNotCalledOnMainThread();
        return this.mDocument;
    }

    public boolean isQueued() {
        PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getState() == 2;
    }

    public boolean isStarted() {
        PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getState() == 3;
    }

    public boolean isBlocked() {
        PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getState() == 4;
    }

    public boolean isCompleted() {
        PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getState() == 5;
    }

    public boolean isFailed() {
        PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getState() == 6;
    }

    public boolean isCancelled() {
        PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getState() == 7;
    }

    public boolean start() {
        PrintService.throwIfNotCalledOnMainThread();
        int state = getInfo().getState();
        if (state == 2 || state == 4) {
            return setState(3, null);
        }
        return false;
    }

    public boolean block(String str) {
        PrintService.throwIfNotCalledOnMainThread();
        int state = getInfo().getState();
        if (state == 3 || state == 4) {
            return setState(4, str);
        }
        return false;
    }

    public boolean complete() {
        PrintService.throwIfNotCalledOnMainThread();
        if (isStarted()) {
            return setState(5, null);
        }
        return false;
    }

    public boolean fail(String str) {
        PrintService.throwIfNotCalledOnMainThread();
        if (isInImmutableState()) {
            return false;
        }
        return setState(6, str);
    }

    public boolean cancel() {
        PrintService.throwIfNotCalledOnMainThread();
        if (isInImmutableState()) {
            return false;
        }
        return setState(7, null);
    }

    public void setProgress(float f) {
        PrintService.throwIfNotCalledOnMainThread();
        try {
            this.mPrintServiceClient.setProgress(this.mCachedInfo.getId(), f);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error setting progress for job: " + this.mCachedInfo.getId(), e);
        }
    }

    public void setStatus(CharSequence charSequence) {
        PrintService.throwIfNotCalledOnMainThread();
        try {
            this.mPrintServiceClient.setStatus(this.mCachedInfo.getId(), charSequence);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error setting status for job: " + this.mCachedInfo.getId(), e);
        }
    }

    public void setStatus(int i) {
        PrintService.throwIfNotCalledOnMainThread();
        try {
            this.mPrintServiceClient.setStatusRes(this.mCachedInfo.getId(), i, this.mContext.getPackageName());
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error setting status for job: " + this.mCachedInfo.getId(), e);
        }
    }

    public boolean setTag(String str) {
        PrintService.throwIfNotCalledOnMainThread();
        if (isInImmutableState()) {
            return false;
        }
        try {
            return this.mPrintServiceClient.setPrintJobTag(this.mCachedInfo.getId(), str);
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error setting tag for job: " + this.mCachedInfo.getId(), e);
            return false;
        }
    }

    public String getTag() {
        PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getTag();
    }

    public String getAdvancedStringOption(String str) {
        PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getAdvancedStringOption(str);
    }

    public boolean hasAdvancedOption(String str) {
        PrintService.throwIfNotCalledOnMainThread();
        return getInfo().hasAdvancedOption(str);
    }

    public int getAdvancedIntOption(String str) {
        PrintService.throwIfNotCalledOnMainThread();
        return getInfo().getAdvancedIntOption(str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return this.mCachedInfo.getId().equals(((PrintJob) obj).mCachedInfo.getId());
        }
        return false;
    }

    public int hashCode() {
        return this.mCachedInfo.getId().hashCode();
    }

    private boolean isInImmutableState() {
        int state = this.mCachedInfo.getState();
        return state == 5 || state == 7 || state == 6;
    }

    private boolean setState(int i, String str) {
        try {
            if (!this.mPrintServiceClient.setPrintJobState(this.mCachedInfo.getId(), i, str)) {
                return false;
            }
            this.mCachedInfo.setState(i);
            this.mCachedInfo.setStatus(str);
            return true;
        } catch (RemoteException e) {
            Log.e(LOG_TAG, "Error setting the state of job: " + this.mCachedInfo.getId(), e);
            return false;
        }
    }
}
