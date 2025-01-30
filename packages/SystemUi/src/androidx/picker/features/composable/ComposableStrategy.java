package androidx.picker.features.composable;

import androidx.picker.model.viewdata.ViewData;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface ComposableStrategy {
    List<ComposableFrame> getIconFrameList();

    List<ComposableFrame> getLeftFrameList();

    List<ComposableFrame> getTitleFrameList();

    List<ComposableFrame> getWidgetFrameList();

    ComposableType selectComposableType(ViewData viewData);
}
