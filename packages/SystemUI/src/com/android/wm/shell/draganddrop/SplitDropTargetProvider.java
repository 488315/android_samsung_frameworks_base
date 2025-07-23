package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.graphics.Rect;
import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class SplitDropTargetProvider {
    public final Context mContext;
    public final SplitDragPolicy mPolicy;
    public final SplitScreenController mSplitScreen;

    public SplitDropTargetProvider(SplitDragPolicy splitDragPolicy, Context context) {
        this.mPolicy = splitDragPolicy;
        this.mSplitScreen = splitDragPolicy.mSplitScreen;
        this.mContext = context;
    }

    public abstract void addSplitTargets(Rect rect, boolean z, boolean z2, float f, ArrayList arrayList);
}
