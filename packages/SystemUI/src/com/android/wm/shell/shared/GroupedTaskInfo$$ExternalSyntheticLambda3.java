package com.android.wm.shell.shared;

import android.os.Parcelable;
import java.util.function.ToIntFunction;

/* loaded from: classes3.dex */
public final /* synthetic */ class GroupedTaskInfo$$ExternalSyntheticLambda3 implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        Parcelable.Creator<GroupedTaskInfo> creator = GroupedTaskInfo.CREATOR;
        return ((Integer) obj).intValue();
    }
}
