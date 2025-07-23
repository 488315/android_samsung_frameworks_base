package com.android.wm.shell.draganddrop;

import com.samsung.android.multiwindow.IDragAndDropClient;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DragAndDropClientRecord {
    public final IDragAndDropClient mClient;
    public final int mDisplayId;

    private DragAndDropClientRecord(IDragAndDropClient iDragAndDropClient, int i) {
        this.mClient = iDragAndDropClient;
        this.mDisplayId = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.wm.shell.draganddrop.DragAndDropClientRecord from(android.content.ClipData r2, int r3) {
        /*
            r0 = 0
            if (r2 == 0) goto L15
            int r1 = r2.getItemCount()
            if (r1 != 0) goto La
            goto L15
        La:
            r1 = 0
            android.content.ClipData$Item r2 = r2.getItemAt(r1)
            android.content.Intent r2 = r2.getIntent()
            if (r2 != 0) goto L17
        L15:
            r2 = r0
            goto L2d
        L17:
            java.lang.String r1 = "com.samsung.android.intent.extra.DRAG_AND_DROP_CLIENT"
            android.os.IBinder r2 = r2.getIBinderExtra(r1)     // Catch: java.lang.Exception -> L1e
            goto L26
        L1e:
            java.lang.String r2 = "DragAndDropClient"
            java.lang.String r1 = "Failed to getIBinderExtra. It's not drag from Edge"
            android.util.Slog.d(r2, r1)
            r2 = r0
        L26:
            if (r2 != 0) goto L29
            goto L15
        L29:
            com.samsung.android.multiwindow.IDragAndDropClient r2 = com.samsung.android.multiwindow.IDragAndDropClient.Stub.asInterface(r2)
        L2d:
            if (r2 != 0) goto L30
            return r0
        L30:
            com.android.wm.shell.draganddrop.DragAndDropClientRecord r0 = new com.android.wm.shell.draganddrop.DragAndDropClientRecord
            r0.<init>(r2, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.draganddrop.DragAndDropClientRecord.from(android.content.ClipData, int):com.android.wm.shell.draganddrop.DragAndDropClientRecord");
    }
}
