package com.samsung.android.nexus.base.context;

import android.content.Context;
import com.samsung.android.nexus.base.animator.AnimatorCore;
import com.samsung.android.nexus.base.utils.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class NexusContext {
    public final AnimatorCore mAnimatorCore;
    public final Context mContext;
    public int mHeight;
    public int mWidth;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ModeData {
    }

    public NexusContext(Context context) {
        new ModeData();
        this.mWidth = 0;
        this.mHeight = 0;
        Log.m262i("NexusContext", "NexusContext() : create NexusContext");
        this.mContext = context;
        this.mAnimatorCore = new AnimatorCore();
    }
}
