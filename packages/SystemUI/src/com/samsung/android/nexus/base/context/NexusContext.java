package com.samsung.android.nexus.base.context;

import android.content.Context;
import com.samsung.android.nexus.base.animator.AnimatorCore;
import com.samsung.android.nexus.base.utils.Log;

/* loaded from: classes4.dex */
public class NexusContext {
    public final AnimatorCore mAnimatorCore;
    public final Context mContext;
    public int mHeight;
    public int mWidth;

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
