package androidx.compose.ui.draganddrop;

import android.content.ClipData;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
