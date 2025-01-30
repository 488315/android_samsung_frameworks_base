package com.android.wm.shell.bubbles.storage;

import android.content.Context;
import android.util.AtomicFile;
import java.io.File;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubblePersistentRepository {
    public final AtomicFile bubbleFile;

    public BubblePersistentRepository(Context context) {
        this.bubbleFile = new AtomicFile(new File(context.getFilesDir(), "overflow_bubbles.xml"), "overflow-bubbles");
    }
}
