package com.samsung.android.allshare.file;

import android.net.Uri;
import com.samsung.android.allshare.Device;
import com.samsung.android.allshare.ERROR;
import java.io.File;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public abstract class FileReceiver extends Device {

    public interface IFileReceiverProgressUpdateEventListener {
        void onCompleted(FileReceiver fileReceiver, String str, File file, Uri uri, ERROR error);

        void onFailed(FileReceiver fileReceiver, String str, File file, Uri uri, ERROR error);

        void onProgressUpdated(FileReceiver fileReceiver, String str, long j, long j2, File file, Uri uri, ERROR error);
    }

    public interface IFileReceiverReceiveResponseListener {
        void onCancelResponseReceived(FileReceiver fileReceiver, String str, ERROR error);

        void onReceiveResponseReceived(FileReceiver fileReceiver, String str, ArrayList<Uri> arrayList, String str2, ERROR error);
    }

    public abstract void cancel(String str);

    public abstract void receive(ArrayList<File> arrayList, ArrayList<Uri> arrayList2, String str, Boolean bool, String str2, IFileReceiverReceiveResponseListener iFileReceiverReceiveResponseListener, IFileReceiverProgressUpdateEventListener iFileReceiverProgressUpdateEventListener);

    protected FileReceiver() {
    }
}
