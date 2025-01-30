package androidx.picker.helper;

import androidx.picker.loader.AppIconFlow;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class FlowHelperKt {
    public static final void loadIconSync(AppIconFlow appIconFlow) {
        BuildersKt.runBlocking(EmptyCoroutineContext.INSTANCE, new FlowHelperKt$loadIconSync$1(appIconFlow, null));
    }
}
