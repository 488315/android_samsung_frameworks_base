package com.android.p038wm.shell.draganddrop;

import android.content.Context;
import android.graphics.Rect;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class SplitDropTargetProvider {
    public final Context mContext;
    public final DragAndDropPolicy mPolicy;
    public final SplitScreenController mSplitScreen;

    public SplitDropTargetProvider(DragAndDropPolicy dragAndDropPolicy, Context context) {
        this.mPolicy = dragAndDropPolicy;
        this.mSplitScreen = dragAndDropPolicy.mSplitScreen;
        this.mContext = context;
    }

    public abstract void addSplitTargets(Rect rect, boolean z, boolean z2, float f, ArrayList arrayList);
}
