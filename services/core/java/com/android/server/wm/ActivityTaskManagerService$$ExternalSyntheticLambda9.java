package com.android.server.wm;

import android.app.ActivityManagerInternal;
import java.util.function.BiConsumer;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class ActivityTaskManagerService$$ExternalSyntheticLambda9 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        ((ActivityManagerInternal) obj).updateOomLevelsForDisplay(((Integer) obj2).intValue());
    }
}
