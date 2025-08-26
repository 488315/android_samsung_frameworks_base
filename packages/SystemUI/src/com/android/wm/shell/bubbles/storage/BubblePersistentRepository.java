package com.android.wm.shell.bubbles.storage;

import android.content.Context;
import android.util.AtomicFile;
import java.io.File;

/* loaded from: classes3.dex */
public final class BubblePersistentRepository {
    public final AtomicFile bubbleFile;

    public BubblePersistentRepository(Context context) {
        this.bubbleFile = new AtomicFile(new File(context.getFilesDir(), "overflow_bubbles.xml"), "overflow-bubbles");
    }
}
