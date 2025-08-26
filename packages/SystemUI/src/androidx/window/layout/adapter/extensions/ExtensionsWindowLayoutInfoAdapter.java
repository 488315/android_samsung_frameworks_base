package androidx.window.layout.adapter.extensions;

import android.graphics.Rect;
import androidx.window.core.Bounds;
import androidx.window.layout.FoldingFeature;
import androidx.window.layout.HardwareFoldingFeature;
import androidx.window.layout.WindowLayoutInfo;
import androidx.window.layout.WindowMetrics;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class ExtensionsWindowLayoutInfoAdapter {
    public static final ExtensionsWindowLayoutInfoAdapter INSTANCE = new ExtensionsWindowLayoutInfoAdapter();

    private ExtensionsWindowLayoutInfoAdapter() {
    }

    public static WindowLayoutInfo translate$window_release(WindowMetrics windowMetrics, androidx.window.extensions.layout.WindowLayoutInfo windowLayoutInfo) {
        HardwareFoldingFeature.Type type;
        FoldingFeature.State state;
        List<androidx.window.extensions.layout.FoldingFeature> displayFeatures = windowLayoutInfo.getDisplayFeatures();
        ArrayList arrayList = new ArrayList();
        for (androidx.window.extensions.layout.FoldingFeature foldingFeature : displayFeatures) {
            HardwareFoldingFeature hardwareFoldingFeature = null;
            if (foldingFeature instanceof androidx.window.extensions.layout.FoldingFeature) {
                androidx.window.extensions.layout.FoldingFeature foldingFeature2 = foldingFeature;
                INSTANCE.getClass();
                int type2 = foldingFeature2.getType();
                if (type2 == 1) {
                    HardwareFoldingFeature.Type.Companion.getClass();
                    type = HardwareFoldingFeature.Type.FOLD;
                } else if (type2 == 2) {
                    HardwareFoldingFeature.Type.Companion.getClass();
                    type = HardwareFoldingFeature.Type.HINGE;
                }
                int state2 = foldingFeature2.getState();
                if (state2 == 1) {
                    state = FoldingFeature.State.FLAT;
                } else if (state2 == 2) {
                    state = FoldingFeature.State.HALF_OPENED;
                }
                Bounds bounds = new Bounds(foldingFeature2.getBounds());
                Rect rect = windowMetrics._bounds.toRect();
                if ((bounds.getHeight() != 0 || bounds.getWidth() != 0) && ((bounds.getWidth() == rect.width() || bounds.getHeight() == rect.height()) && ((bounds.getWidth() >= rect.width() || bounds.getHeight() >= rect.height()) && (bounds.getWidth() != rect.width() || bounds.getHeight() != rect.height())))) {
                    hardwareFoldingFeature = new HardwareFoldingFeature(new Bounds(foldingFeature2.getBounds()), type, state);
                }
            }
            if (hardwareFoldingFeature != null) {
                arrayList.add(hardwareFoldingFeature);
            }
        }
        return new WindowLayoutInfo(arrayList);
    }
}
