package com.android.wm.shell.bubbles.storage;

import android.content.Context;
import android.util.AtomicFile;
import java.io.File;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BubblePersistentRepository {
    public final AtomicFile bubbleFile;

    public BubblePersistentRepository(Context context) {
        this.bubbleFile = new AtomicFile(new File(context.getFilesDir(), "overflow_bubbles.xml"), "overflow-bubbles");
    }
}
