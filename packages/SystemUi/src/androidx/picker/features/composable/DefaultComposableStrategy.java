package androidx.picker.features.composable;

import androidx.picker.features.composable.icon.IconFrame;
import androidx.picker.features.composable.left.LeftFrame;
import androidx.picker.features.composable.title.TitleFrame;
import androidx.picker.features.composable.widget.WidgetFrame;
import androidx.picker.model.viewdata.AllAppsViewData;
import androidx.picker.model.viewdata.AppInfoViewData;
import androidx.picker.model.viewdata.CategoryViewData;
import androidx.picker.model.viewdata.ViewData;
import java.util.List;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class DefaultComposableStrategy implements ComposableStrategy {
    private final List<ComposableFrame> leftFrameList = ArraysKt___ArraysKt.toList(LeftFrame.values());
    private final List<ComposableFrame> iconFrameList = ArraysKt___ArraysKt.toList(IconFrame.values());
    private final List<ComposableFrame> titleFrameList = ArraysKt___ArraysKt.toList(TitleFrame.values());
    private final List<ComposableFrame> widgetFrameList = ArraysKt___ArraysKt.toList(WidgetFrame.values());

    @Override // androidx.picker.features.composable.ComposableStrategy
    public List<ComposableFrame> getIconFrameList() {
        return this.iconFrameList;
    }

    @Override // androidx.picker.features.composable.ComposableStrategy
    public List<ComposableFrame> getLeftFrameList() {
        return this.leftFrameList;
    }

    @Override // androidx.picker.features.composable.ComposableStrategy
    public List<ComposableFrame> getTitleFrameList() {
        return this.titleFrameList;
    }

    @Override // androidx.picker.features.composable.ComposableStrategy
    public List getWidgetFrameList() {
        return this.widgetFrameList;
    }

    @Override // androidx.picker.features.composable.ComposableStrategy
    public ComposableType selectComposableType(ViewData viewData) {
        if (viewData instanceof AllAppsViewData) {
            return ComposableTypeSet.AllSwitch;
        }
        if (viewData instanceof CategoryViewData) {
            return ComposableTypeSet.CheckBox_Expander;
        }
        if (!(viewData instanceof AppInfoViewData)) {
            return null;
        }
        AppInfoViewData appInfoViewData = (AppInfoViewData) viewData;
        int itemType = appInfoViewData.getItemType();
        return itemType != 2 ? itemType != 4 ? itemType != 5 ? ComposableTypeSet.TextOnly : ComposableTypeSet.Switch : appInfoViewData.getActionIcon() != null ? ComposableTypeSet.Radio_Action : ComposableTypeSet.Radio : appInfoViewData.getActionIcon() != null ? ComposableTypeSet.CheckBox_Action : ComposableTypeSet.CheckBox;
    }
}
