package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper;
import com.android.systemui.qs.tileimpl.LabelTileView;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.IndexingSequence;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence$iterator$1;

public final class SecTileChunkLayout extends ViewGroup implements SecQSPanel.QSTileLayout {
    public final Cell cell;
    public final CellMargin cellMargin;
    public int columns;
    public final Context context;
    public float fraction;
    public boolean oldListening;
    public final ArrayList records;
    public final SecQSPanelResourcePicker resourcePicker;
    public int rowSpace;
    public int rows;
    public int sidePadding;

    public final class Cell {
        public int height;
        public int heightCollapsed;
        public int heightExpanded;
        public int width;

        public Cell(int i, int i2, int i3, int i4) {
            this.width = i;
            this.height = i2;
            this.heightCollapsed = i3;
            this.heightExpanded = i4;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Cell)) {
                return false;
            }
            Cell cell = (Cell) obj;
            return this.width == cell.width && this.height == cell.height && this.heightCollapsed == cell.heightCollapsed && this.heightExpanded == cell.heightExpanded;
        }

        public final int hashCode() {
            return Integer.hashCode(this.heightExpanded) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.heightCollapsed, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.height, Integer.hashCode(this.width) * 31, 31), 31);
        }

        public final String toString() {
            int i = this.width;
            int i2 = this.height;
            int i3 = this.heightCollapsed;
            int i4 = this.heightExpanded;
            StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "Cell(width=", ", height=", ", heightCollapsed=");
            m.append(i3);
            m.append(", heightExpanded=");
            m.append(i4);
            m.append(")");
            return m.toString();
        }
    }

    public final class CellMargin {
        public int horizontal;
        public int verticalCollapsed;
        public int verticalExpanded;

        public CellMargin(int i, int i2, int i3) {
            this.horizontal = i;
            this.verticalCollapsed = i2;
            this.verticalExpanded = i3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CellMargin)) {
                return false;
            }
            CellMargin cellMargin = (CellMargin) obj;
            return this.horizontal == cellMargin.horizontal && this.verticalCollapsed == cellMargin.verticalCollapsed && this.verticalExpanded == cellMargin.verticalExpanded;
        }

        public final int hashCode() {
            return Integer.hashCode(this.verticalExpanded) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.verticalCollapsed, Integer.hashCode(this.horizontal) * 31, 31);
        }

        public final String toString() {
            return Anchor$$ExternalSyntheticOutline0.m(this.verticalExpanded, ")", RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(this.horizontal, this.verticalCollapsed, "CellMargin(horizontal=", ", verticalCollapsed=", ", verticalExpanded="));
        }
    }

    public SecTileChunkLayout(Context context) {
        super(context);
        this.context = context;
        this.resourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        this.records = new ArrayList();
        this.cell = new Cell(1, 0, 0, 0);
        this.cellMargin = new CellMargin(0, 0, 0);
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void addTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        this.records.add(tileRecord);
        boolean z = this.oldListening;
        QSTile qSTile = tileRecord.tile;
        qSTile.setListening(this, z);
        addView(tileRecord.tileView);
        QSTileImpl qSTileImpl = qSTile instanceof QSTileImpl ? (QSTileImpl) qSTile : null;
        if (qSTileImpl != null) {
            qSTileImpl.saveTileIconAsImage();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int size = this.records.size();
        boolean z2 = getLayoutDirection() == 1;
        int i5 = this.rows * this.columns;
        if (size > i5) {
            size = i5;
        }
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i6 < size) {
            if (i7 == this.columns) {
                i8++;
                i7 = 0;
            }
            SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) this.records.get(i6);
            int i9 = this.rowSpace * i8;
            int i10 = z2 ? (this.columns - i7) - 1 : i7;
            int i11 = this.cell.width;
            int i12 = ((this.cellMargin.horizontal + i11) * i10) + this.sidePadding;
            QSTileView qSTileView = tileRecord.tileView;
            qSTileView.layout(i12, i9, i11 + i12, qSTileView.getMeasuredHeight() + i9);
            qSTileView.setPosition(i6);
            i6++;
            i7++;
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int size = this.records.size();
        int size2 = View.MeasureSpec.getSize(i);
        int i3 = size2 - (this.sidePadding * 2);
        Cell cell = this.cell;
        int i4 = cell.width;
        int i5 = i3 / i4;
        if (1 >= i5) {
            i5 = 1;
        }
        this.columns = i5;
        int i6 = size / i5;
        this.rows = i6;
        if (size % i5 != 0) {
            this.rows = i6 + 1;
        }
        this.cellMargin.horizontal = i5 == 1 ? 0 : (i3 - (i4 * i5)) / (i5 - 1);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(cell.height, 1073741824);
        TransformingSequence$iterator$1 transformingSequence$iterator$1 = new TransformingSequence$iterator$1(new TransformingSequence(SequencesKt___SequencesKt.filterNot(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(this.records), new Function1() { // from class: com.android.systemui.qs.SecTileChunkLayout$onMeasure$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((SecQSPanelControllerBase.TileRecord) obj).tileView.getVisibility() == 8);
            }
        }), new Function1() { // from class: com.android.systemui.qs.SecTileChunkLayout$onMeasure$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ((SecQSPanelControllerBase.TileRecord) obj).tileView;
            }
        }));
        View view = this;
        int i7 = 0;
        while (transformingSequence$iterator$1.iterator.hasNext()) {
            QSTileView qSTileView = (QSTileView) transformingSequence$iterator$1.next();
            if (i7 == this.columns) {
                i7 = 0;
            }
            qSTileView.measure(View.MeasureSpec.makeMeasureSpec(this.cell.width, 1073741824), makeMeasureSpec);
            view = qSTileView.updateAccessibilityOrder(view);
            this.cell.height = qSTileView.getMeasuredHeight();
            i7++;
        }
        int i8 = this.rowSpace * this.rows;
        setMeasuredDimension(size2, i8 >= 0 ? i8 : 0);
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void removeTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        this.records.remove(tileRecord);
        tileRecord.tile.setListening(this, false);
        removeView(tileRecord.tileView);
    }

    public final void setFraction(float f) {
        this.fraction = f;
        updateExpandableVariables(f);
        float f2 = this.fraction;
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.mapNotNull(SequencesKt___SequencesKt.filterNot(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(this.records), new Function1() { // from class: com.android.systemui.qs.SecTileChunkLayout$setLabelAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((SecQSPanelControllerBase.TileRecord) obj).tileView.getVisibility() == 8);
            }
        }), new Function1() { // from class: com.android.systemui.qs.SecTileChunkLayout$setLabelAlpha$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                QSTileView qSTileView = ((SecQSPanelControllerBase.TileRecord) obj).tileView;
                if (qSTileView instanceof LabelTileView) {
                    return (LabelTileView) qSTileView;
                }
                return null;
            }
        }));
        while (filteringSequence$iterator$1.hasNext()) {
            LinearLayout linearLayout = ((LabelTileView) filteringSequence$iterator$1.next()).labelContainer;
            if (linearLayout != null) {
                linearLayout.setAlpha(f2);
            }
        }
    }

    public final void setLabelVisibility(boolean z) {
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.mapNotNull(SequencesKt___SequencesKt.filterNot(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(this.records), new Function1() { // from class: com.android.systemui.qs.SecTileChunkLayout$setLabelVisibility$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((SecQSPanelControllerBase.TileRecord) obj).tileView.getVisibility() == 8);
            }
        }), new Function1() { // from class: com.android.systemui.qs.SecTileChunkLayout$setLabelVisibility$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                QSTileView qSTileView = ((SecQSPanelControllerBase.TileRecord) obj).tileView;
                if (qSTileView instanceof LabelTileView) {
                    return (LabelTileView) qSTileView;
                }
                return null;
            }
        }));
        while (filteringSequence$iterator$1.hasNext()) {
            LabelTileView labelTileView = (LabelTileView) filteringSequence$iterator$1.next();
            if (z) {
                labelTileView.setBackground(labelTileView.getContext().getDrawable(R.drawable.sec_tile_view_ripple_background));
                labelTileView.iconView.setBackground(null);
            } else {
                labelTileView.setBackground(null);
                labelTileView.iconView.setBackground(labelTileView.commonTileView.tileBackground);
            }
        }
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void setListening(boolean z, UiEventLogger uiEventLogger) {
        if (this.oldListening == z) {
            return;
        }
        this.oldListening = z;
        Iterator it = this.records.iterator();
        while (it.hasNext()) {
            ((SecQSPanelControllerBase.TileRecord) it.next()).tile.setListening(this, this.oldListening);
        }
    }

    public final void updateExpandableVariables(float f) {
        Cell cell = this.cell;
        int i = cell.heightCollapsed;
        int i2 = cell.heightExpanded;
        cell.height = ((int) ((i2 - i) * f)) + i;
        this.rowSpace = i + this.cellMargin.verticalCollapsed + ((int) (((i2 + r0.verticalExpanded) - r1) * f));
    }

    public final void updateFocusForTiles(int i, boolean z) {
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(new IndexingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(this.records)), new Function1() { // from class: com.android.systemui.qs.SecTileChunkLayout$updateFocusForTiles$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((SecQSPanelControllerBase.TileRecord) ((IndexedValue) obj).value).tileView.getVisibility() != 8);
            }
        }));
        while (filteringSequence$iterator$1.hasNext()) {
            IndexedValue indexedValue = (IndexedValue) filteringSequence$iterator$1.next();
            int i2 = indexedValue.index;
            QSTileView qSTileView = ((SecQSPanelControllerBase.TileRecord) indexedValue.value).tileView;
            LabelTileView labelTileView = qSTileView instanceof LabelTileView ? (LabelTileView) qSTileView : null;
            if (labelTileView != null) {
                labelTileView.setFocusable((i2 < this.columns * i) | z);
            }
        }
    }

    @Override // com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final boolean updateResources() {
        updateExpandableVariables(this.fraction);
        setLabelVisibility(false);
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.resourcePicker;
        this.sidePadding = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getTileExpandedSidePadding(this.context);
        Cell cell = this.cell;
        Context context = this.context;
        SecQSPanelResourcePickHelper secQSPanelResourcePickHelper = secQSPanelResourcePicker.resourcePickHelper;
        cell.width = secQSPanelResourcePickHelper.getTargetPicker().getTileExpandedWidth(context);
        cell.heightCollapsed = secQSPanelResourcePicker.getTouchIconSize(this.context);
        cell.heightExpanded = secQSPanelResourcePickHelper.getTargetPicker().getTileExpandedHeight(this.context);
        Resources resources = getResources();
        CellMargin cellMargin = this.cellMargin;
        cellMargin.verticalCollapsed = resources.getDimensionPixelSize(R.dimen.tile_chunk_layout_vertical_between_margin);
        cellMargin.verticalExpanded = resources.getDimensionPixelSize(R.dimen.tile_chunk_layout_vertical_between_margin_expanded);
        int i = this.columns;
        int panelWidth = (this.resourcePicker.getPanelWidth(this.context) - ((this.resourcePicker.resourcePickHelper.getTargetPicker().getPanelSidePadding(this.context) + this.sidePadding) * 2)) / this.cell.width;
        if (1 >= panelWidth) {
            panelWidth = 1;
        }
        this.columns = panelWidth;
        int size = this.records.size();
        int i2 = this.columns;
        int i3 = size / i2;
        this.rows = i3;
        if (size % i2 != 0) {
            this.rows = i3 + 1;
        }
        if (i2 == i) {
            return false;
        }
        requestLayout();
        return true;
    }

    public final void updateTileWidth(float f) {
        SecQSPanelResourceCommon secQSPanelResourceCommon = this.resourcePicker.resourcePickHelper.getTargetPicker().common;
        if (secQSPanelResourceCommon != null) {
            secQSPanelResourceCommon.tileExpandedWidthRatio = f;
        }
        this.cell.width = this.resourcePicker.resourcePickHelper.getTargetPicker().getTileExpandedWidth(this.context);
        Log.d("SecTileChunkLayout", "updateTileWidth(ratio:" + f + ") -> mCellWidth:" + this.cell.width);
        updateResources();
    }
}
