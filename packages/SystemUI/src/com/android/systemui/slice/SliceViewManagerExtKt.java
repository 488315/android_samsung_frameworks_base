package com.android.systemui.slice;

import android.net.Uri;
import androidx.slice.SliceViewManager;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class SliceViewManagerExtKt {
    public static final Flow sliceForUri(SliceViewManager sliceViewManager, Uri uri) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        SliceViewManagerExtKt$sliceForUri$1 sliceViewManagerExtKt$sliceForUri$1 = new SliceViewManagerExtKt$sliceForUri$1(sliceViewManager, uri, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(sliceViewManagerExtKt$sliceForUri$1);
    }
}
