package com.samsung.android.nexus.base.context;

import android.content.Context;
import com.samsung.android.nexus.base.animator.AnimatorCore;
import com.samsung.android.nexus.base.utils.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class NexusContext {
    public final AnimatorCore mAnimatorCore;
    public final Context mContext;
    public int mHeight;
    public int mWidth;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ModeData {
    }

    public NexusContext(Context context) {
        new ModeData();
        this.mWidth = 0;
        this.mHeight = 0;
        Log.i("NexusContext", "NexusContext() : create NexusContext");
        this.mContext = context;
        this.mAnimatorCore = new AnimatorCore();
    }
}
