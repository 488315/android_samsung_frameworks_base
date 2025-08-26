package androidx.compose.ui.draganddrop;

import android.content.ClipDescription;
import androidx.compose.ui.geometry.Offset;
import java.util.Set;
import kotlin.collections.EmptySet;
import kotlin.collections.builders.SetBuilder;

/* loaded from: classes.dex */
public abstract class DragAndDrop_androidKt {
    public static final long getPositionInRoot(DragAndDropEvent dragAndDropEvent) {
        float x = dragAndDropEvent.dragEvent.getX();
        float y = dragAndDropEvent.dragEvent.getY();
        long jFloatToRawIntBits = (Float.floatToRawIntBits(x) << 32) | (Float.floatToRawIntBits(y) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        return jFloatToRawIntBits;
    }

    public static final Set mimeTypes(DragAndDropEvent dragAndDropEvent) {
        ClipDescription clipDescription = dragAndDropEvent.dragEvent.getClipDescription();
        if (clipDescription == null) {
            return EmptySet.INSTANCE;
        }
        SetBuilder setBuilder = new SetBuilder(clipDescription.getMimeTypeCount());
        int mimeTypeCount = clipDescription.getMimeTypeCount();
        for (int i = 0; i < mimeTypeCount; i++) {
            setBuilder.add(clipDescription.getMimeType(i));
        }
        return setBuilder.build();
    }
}
