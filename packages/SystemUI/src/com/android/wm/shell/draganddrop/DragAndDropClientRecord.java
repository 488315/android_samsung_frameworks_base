package com.android.wm.shell.draganddrop;

import android.content.ClipData;
import android.content.Intent;
import android.os.IBinder;
import android.util.Slog;
import com.samsung.android.multiwindow.IDragAndDropClient;

/* loaded from: classes3.dex */
public class DragAndDropClientRecord {
    public final IDragAndDropClient mClient;
    public final int mDisplayId;

    private DragAndDropClientRecord(IDragAndDropClient iDragAndDropClient, int i) {
        this.mClient = iDragAndDropClient;
        this.mDisplayId = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static DragAndDropClientRecord from(ClipData clipData, int i) {
        IDragAndDropClient iDragAndDropClientAsInterface;
        Intent intent;
        IBinder iBinderExtra;
        if (clipData == null || clipData.getItemCount() == 0 || (intent = clipData.getItemAt(0).getIntent()) == null) {
            iDragAndDropClientAsInterface = null;
        } else {
            try {
                iBinderExtra = intent.getIBinderExtra("com.samsung.android.intent.extra.DRAG_AND_DROP_CLIENT");
            } catch (Exception unused) {
                Slog.d("DragAndDropClient", "Failed to getIBinderExtra. It's not drag from Edge");
                iBinderExtra = null;
            }
            if (iBinderExtra != null) {
                iDragAndDropClientAsInterface = IDragAndDropClient.Stub.asInterface(iBinderExtra);
            }
        }
        if (iDragAndDropClientAsInterface == null) {
            return null;
        }
        return new DragAndDropClientRecord(iDragAndDropClientAsInterface, i);
    }
}
