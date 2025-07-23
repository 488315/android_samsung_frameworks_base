package android.view;

import android.graphics.Rect;
import android.graphics.Region;
import android.view.SurfaceControl;
import android.window.InputTransferToken;
import android.window.SurfaceSyncGroup;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public interface AttachedSurfaceControl {

    public interface OnBufferTransformHintChangedListener {
        void onBufferTransformHintChanged(int i);
    }

    default void addOnBufferTransformHintChangedListener(OnBufferTransformHintChangedListener onBufferTransformHintChangedListener) {
    }

    boolean applyTransactionOnDraw(SurfaceControl.Transaction transaction);

    SurfaceControl.Transaction buildReparentTransaction(SurfaceControl surfaceControl);

    default int getBufferTransformHint() {
        return 0;
    }

    default SurfaceSyncGroup getOrCreateSurfaceSyncGroup() {
        return null;
    }

    default void removeOnBufferTransformHintChangedListener(OnBufferTransformHintChangedListener onBufferTransformHintChangedListener) {
    }

    default void setChildBoundingInsets(Rect rect) {
    }

    default void setTouchableRegion(Region region) {
    }

    default InputTransferToken getInputTransferToken() {
        throw new UnsupportedOperationException("The getInputTransferToken needs to be implemented before making this call.");
    }

    default SurfaceControl.OnJankDataListenerRegistration registerOnJankDataListener(Executor executor, SurfaceControl.OnJankDataListener onJankDataListener) {
        return SurfaceControl.OnJankDataListenerRegistration.NONE;
    }
}
