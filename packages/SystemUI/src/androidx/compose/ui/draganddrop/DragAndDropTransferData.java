package androidx.compose.ui.draganddrop;

import android.content.ClipData;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class DragAndDropTransferData {
    public final ClipData clipData;
    public final int flags;
    public final Object localState;

    public DragAndDropTransferData(ClipData clipData, Object obj, int i) {
        this.clipData = clipData;
        this.localState = obj;
        this.flags = i;
    }

    public /* synthetic */ DragAndDropTransferData(ClipData clipData, Object obj, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(clipData, (i2 & 2) != 0 ? null : obj, (i2 & 4) != 0 ? 0 : i);
    }
}
