package com.android.wm.shell.draganddrop;

import android.content.ClipData;
import android.content.Intent;
import android.os.IBinder;
import android.util.Slog;
import com.samsung.android.multiwindow.IDragAndDropClient;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DragAndDropClientRecord {
    public final IDragAndDropClient mClient;
    public final int mDisplayId;

    private DragAndDropClientRecord(IDragAndDropClient iDragAndDropClient, int i) {
        this.mClient = iDragAndDropClient;
        this.mDisplayId = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0030 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static DragAndDropClientRecord from(ClipData clipData, int i) {
        IDragAndDropClient iDragAndDropClient;
        Intent intent;
        IBinder iBinder;
        if (clipData != null && clipData.getItemCount() != 0 && (intent = clipData.getItemAt(0).getIntent()) != null) {
            try {
                iBinder = intent.getIBinderExtra("com.samsung.android.intent.extra.DRAG_AND_DROP_CLIENT");
            } catch (Exception unused) {
                Slog.d("DragAndDropClient", "Failed to getIBinderExtra. It's not drag from Edge");
                iBinder = null;
            }
            if (iBinder != null) {
                iDragAndDropClient = IDragAndDropClient.Stub.asInterface(iBinder);
                if (iDragAndDropClient != null) {
                    return null;
                }
                return new DragAndDropClientRecord(iDragAndDropClient, i);
            }
        }
        iDragAndDropClient = null;
        if (iDragAndDropClient != null) {
        }
    }
}
