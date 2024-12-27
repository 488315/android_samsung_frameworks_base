package android.view;

import android.graphics.Rect;
import android.graphics.Region;
import android.window.InputTransferToken;
import android.window.SurfaceSyncGroup;

public interface AttachedSurfaceControl {

    public interface OnBufferTransformHintChangedListener {
        void onBufferTransformHintChanged(int i);
    }

    boolean applyTransactionOnDraw(SurfaceControl.Transaction transaction);

    SurfaceControl.Transaction buildReparentTransaction(SurfaceControl surfaceControl);

    default int getBufferTransformHint() {
        return 0;
    }

    default void addOnBufferTransformHintChangedListener(
            OnBufferTransformHintChangedListener listener) {}

    default void removeOnBufferTransformHintChangedListener(
            OnBufferTransformHintChangedListener listener) {}

    default void setTouchableRegion(Region r) {}

    default SurfaceSyncGroup getOrCreateSurfaceSyncGroup() {
        return null;
    }

    default void setChildBoundingInsets(Rect insets) {}

    default InputTransferToken getInputTransferToken() {
        throw new UnsupportedOperationException(
                "The getInputTransferToken needs to be implemented before making this call.");
    }
}
