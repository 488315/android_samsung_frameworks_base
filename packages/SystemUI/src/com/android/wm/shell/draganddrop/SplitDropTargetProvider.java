package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.graphics.Rect;
import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.ArrayList;

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
