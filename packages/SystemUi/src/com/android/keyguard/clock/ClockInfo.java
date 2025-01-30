package com.android.keyguard.clock;

import android.graphics.Bitmap;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ClockInfo {
    public final String mId;
    public final String mName;
    public final Supplier mPreview;
    public final Supplier mThumbnail;
    public final Supplier mTitle;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
    }

    public /* synthetic */ ClockInfo(String str, Supplier supplier, String str2, Supplier supplier2, Supplier supplier3, int i) {
        this(str, supplier, str2, supplier2, supplier3);
    }

    private ClockInfo(String str, Supplier<String> supplier, String str2, Supplier<Bitmap> supplier2, Supplier<Bitmap> supplier3) {
        this.mName = str;
        this.mTitle = supplier;
        this.mId = str2;
        this.mThumbnail = supplier2;
        this.mPreview = supplier3;
    }
}
