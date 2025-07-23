package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.SecTileLayout;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SecHeaderTileLayout extends TileLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Rect mClippingBounds;
    public final Context mContext;
    public int mOrientation;
    public final SecTileLayout mSecTileLayout;

    public SecHeaderTileLayout(final Context context) {
        super(context);
        this.mClippingBounds = new Rect();
        this.mContext = context;
        setClipChildren(false);
        setClipToPadding(false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 1;
        layoutParams.bottomMargin = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getQuickQSCommonBottomMargin(context);
        setLayoutParams(layoutParams);
        this.mOrientation = getResources().getConfiguration().orientation;
        updateResources();
        setTag("open_anim");
        final int i = 0;
        IntSupplier intSupplier = new IntSupplier(this) { // from class: com.android.systemui.qs.SecHeaderTileLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ SecHeaderTileLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                int i2 = i;
                SecHeaderTileLayout secHeaderTileLayout = this.f$0;
                switch (i2) {
                    case 0:
                        int i3 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellHeight;
                    case 1:
                        int i4 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mSidePadding;
                    case 2:
                        int i5 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellMarginHorizontal;
                    case 3:
                        int i6 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellWidth;
                    case 4:
                        int i7 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mColumns;
                    case 5:
                        return secHeaderTileLayout.getLayoutDirection();
                    default:
                        int i8 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mRows;
                }
            }
        };
        final int i2 = 2;
        IntSupplier intSupplier2 = new IntSupplier(this) { // from class: com.android.systemui.qs.SecHeaderTileLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ SecHeaderTileLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                int i22 = i2;
                SecHeaderTileLayout secHeaderTileLayout = this.f$0;
                switch (i22) {
                    case 0:
                        int i3 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellHeight;
                    case 1:
                        int i4 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mSidePadding;
                    case 2:
                        int i5 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellMarginHorizontal;
                    case 3:
                        int i6 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellWidth;
                    case 4:
                        int i7 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mColumns;
                    case 5:
                        return secHeaderTileLayout.getLayoutDirection();
                    default:
                        int i8 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mRows;
                }
            }
        };
        final int i3 = 3;
        IntSupplier intSupplier3 = new IntSupplier(this) { // from class: com.android.systemui.qs.SecHeaderTileLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ SecHeaderTileLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                int i22 = i3;
                SecHeaderTileLayout secHeaderTileLayout = this.f$0;
                switch (i22) {
                    case 0:
                        int i32 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellHeight;
                    case 1:
                        int i4 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mSidePadding;
                    case 2:
                        int i5 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellMarginHorizontal;
                    case 3:
                        int i6 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellWidth;
                    case 4:
                        int i7 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mColumns;
                    case 5:
                        return secHeaderTileLayout.getLayoutDirection();
                    default:
                        int i8 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mRows;
                }
            }
        };
        IntConsumer intConsumer = new IntConsumer() { // from class: com.android.systemui.qs.SecHeaderTileLayout$$ExternalSyntheticLambda4
            @Override // java.util.function.IntConsumer
            public final void accept(int i4) {
                SecHeaderTileLayout secHeaderTileLayout = SecHeaderTileLayout.this;
                int i5 = SecHeaderTileLayout.$r8$clinit;
                secHeaderTileLayout.mColumns = i4;
            }
        };
        final int i4 = 4;
        IntSupplier intSupplier4 = new IntSupplier(this) { // from class: com.android.systemui.qs.SecHeaderTileLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ SecHeaderTileLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                int i22 = i4;
                SecHeaderTileLayout secHeaderTileLayout = this.f$0;
                switch (i22) {
                    case 0:
                        int i32 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellHeight;
                    case 1:
                        int i42 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mSidePadding;
                    case 2:
                        int i5 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellMarginHorizontal;
                    case 3:
                        int i6 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellWidth;
                    case 4:
                        int i7 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mColumns;
                    case 5:
                        return secHeaderTileLayout.getLayoutDirection();
                    default:
                        int i8 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mRows;
                }
            }
        };
        Supplier supplier = new Supplier() { // from class: com.android.systemui.qs.SecHeaderTileLayout$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final Object get() {
                Context context2 = context;
                int i5 = SecHeaderTileLayout.$r8$clinit;
                return context2;
            }
        };
        final int i5 = 5;
        IntSupplier intSupplier5 = new IntSupplier(this) { // from class: com.android.systemui.qs.SecHeaderTileLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ SecHeaderTileLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                int i22 = i5;
                SecHeaderTileLayout secHeaderTileLayout = this.f$0;
                switch (i22) {
                    case 0:
                        int i32 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellHeight;
                    case 1:
                        int i42 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mSidePadding;
                    case 2:
                        int i52 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellMarginHorizontal;
                    case 3:
                        int i6 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellWidth;
                    case 4:
                        int i7 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mColumns;
                    case 5:
                        return secHeaderTileLayout.getLayoutDirection();
                    default:
                        int i8 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mRows;
                }
            }
        };
        IntFunction intFunction = new IntFunction() { // from class: com.android.systemui.qs.SecHeaderTileLayout$$ExternalSyntheticLambda8
            @Override // java.util.function.IntFunction
            public final Object apply(int i6) {
                return Integer.valueOf((int) (((r2.mCellHeight * ((SecHeaderTileLayout.this.mSquishinessFraction * 0.9f) + 0.1f)) + r2.mCellMarginVertical) * i6));
            }
        };
        ArrayList arrayList = this.mRecords;
        final int i6 = 6;
        IntSupplier intSupplier6 = new IntSupplier(this) { // from class: com.android.systemui.qs.SecHeaderTileLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ SecHeaderTileLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                int i22 = i6;
                SecHeaderTileLayout secHeaderTileLayout = this.f$0;
                switch (i22) {
                    case 0:
                        int i32 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellHeight;
                    case 1:
                        int i42 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mSidePadding;
                    case 2:
                        int i52 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellMarginHorizontal;
                    case 3:
                        int i62 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellWidth;
                    case 4:
                        int i7 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mColumns;
                    case 5:
                        return secHeaderTileLayout.getLayoutDirection();
                    default:
                        int i8 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mRows;
                }
            }
        };
        final int i7 = 1;
        this.mSecTileLayout = new SecTileLayout(intSupplier, intSupplier2, intSupplier3, intConsumer, intSupplier4, supplier, intSupplier5, intFunction, arrayList, intSupplier6, new IntSupplier(this) { // from class: com.android.systemui.qs.SecHeaderTileLayout$$ExternalSyntheticLambda0
            public final /* synthetic */ SecHeaderTileLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                int i22 = i7;
                SecHeaderTileLayout secHeaderTileLayout = this.f$0;
                switch (i22) {
                    case 0:
                        int i32 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellHeight;
                    case 1:
                        int i42 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mSidePadding;
                    case 2:
                        int i52 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellMarginHorizontal;
                    case 3:
                        int i62 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mCellWidth;
                    case 4:
                        int i72 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mColumns;
                    case 5:
                        return secHeaderTileLayout.getLayoutDirection();
                    default:
                        int i8 = SecHeaderTileLayout.$r8$clinit;
                        return secHeaderTileLayout.mRows;
                }
            }
        }, new BiFunction() { // from class: com.android.systemui.qs.SecHeaderTileLayout$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Boolean.valueOf(SecHeaderTileLayout.this.updateMaxRows(((Integer) obj).intValue(), ((Integer) obj2).intValue()));
            }
        });
    }

    @Override // com.android.systemui.qs.TileLayout
    public final void addTileView$1(SecQSPanelControllerBase.TileRecord tileRecord) {
        addView(tileRecord.tileView, getChildCount(), new ViewGroup.LayoutParams(this.mCellWidth, this.mCellHeight));
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mOrientation = getResources().getConfiguration().orientation;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mOrientation = configuration.orientation;
        updateResources();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mOrientation = getResources().getConfiguration().orientation;
        updateResources();
    }

    @Override // com.android.systemui.qs.TileLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mClippingBounds.set(0, 0, i3 - i, 10000);
        setClipBounds(this.mClippingBounds);
        int size = this.mRecords.size();
        if (size == 0) {
            this.mColumns = 0;
        } else {
            int measuredWidth = getMeasuredWidth() - (this.mSidePadding * 2);
            int i5 = this.mCellWidth;
            int i6 = measuredWidth - (size * i5);
            if (i6 > 0) {
                this.mCellMarginHorizontal = i6 / Math.max(1, size - 1);
                this.mColumns = size;
            } else {
                int min = i5 == 0 ? 1 : Math.min(size, measuredWidth / i5);
                this.mColumns = min;
                if (min != 0) {
                    if (min == 1) {
                        this.mCellMarginHorizontal = (measuredWidth - this.mCellWidth) / 2;
                    } else {
                        this.mCellMarginHorizontal = (measuredWidth - (this.mCellWidth * min)) / (min - 1);
                    }
                }
            }
        }
        int i7 = 0;
        while (true) {
            int i8 = 8;
            if (i7 >= this.mRecords.size()) {
                break;
            }
            QSTileView qSTileView = ((SecQSPanelControllerBase.TileRecord) this.mRecords.get(i7)).tileView;
            if (i7 < this.mColumns) {
                i8 = 0;
            }
            qSTileView.setVisibility(i8);
            i7++;
        }
        ArrayList arrayList = this.mRecords;
        if (arrayList != null && arrayList.size() > 0) {
            ArrayList arrayList2 = this.mRecords;
            int size2 = arrayList2.size();
            View view = this;
            int i9 = 0;
            while (i9 < size2) {
                Object obj = arrayList2.get(i9);
                i9++;
                SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) obj;
                if (tileRecord.tileView.getVisibility() != 8) {
                    view = tileRecord.tileView.updateAccessibilityOrder(view);
                }
            }
        }
        SecTileLayout secTileLayout = this.mSecTileLayout;
        if (secTileLayout != null) {
            int i10 = this.mColumns;
            int asInt = secTileLayout.columnsSupplier.getAsInt();
            int asInt2 = secTileLayout.rowsSupplier.getAsInt() * asInt;
            if (i10 > asInt2) {
                i10 = asInt2;
            }
            SecTileLayout.Counter counter = new SecTileLayout.Counter(i10, asInt);
            boolean z2 = secTileLayout.getLayoutDirectionSupplier.getAsInt() == 1;
            int asInt3 = secTileLayout.cellWidthSupplier.getAsInt();
            int asInt4 = secTileLayout.cellHeightSupplier.getAsInt();
            while (counter.index < counter.indices) {
                int asInt5 = secTileLayout.sidePaddingSupplier.getAsInt() + ((secTileLayout.cellMarginHorizontalSupplier.getAsInt() + secTileLayout.cellWidthSupplier.getAsInt()) * (z2 ? (asInt - counter.column) - 1 : counter.column));
                Integer num = (Integer) secTileLayout.getRowTopFunction.apply(counter.row);
                QSTileView qSTileView2 = ((SecQSPanelControllerBase.TileRecord) secTileLayout.records.get(counter.index)).tileView;
                num.getClass();
                qSTileView2.layout(asInt5, num.intValue(), asInt5 + asInt3, num.intValue() + asInt4);
                counter.index++;
                int i11 = counter.column + 1;
                counter.column = i11;
                if (i11 == counter.columns) {
                    counter.column = 0;
                    counter.row++;
                }
            }
        }
    }

    @Override // com.android.systemui.qs.TileLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        ArrayList arrayList = this.mRecords;
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            SecQSPanelControllerBase.TileRecord tileRecord = (SecQSPanelControllerBase.TileRecord) obj;
            if (tileRecord.tileView.getVisibility() != 8) {
                tileRecord.tileView.measure(View.MeasureSpec.makeMeasureSpec(this.mCellWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mCellHeight, 1073741824));
            }
        }
        int i4 = this.mCellHeight;
        setMeasuredDimension(View.MeasureSpec.getSize(i), i4 >= 0 ? i4 : 0);
    }

    @Override // com.android.systemui.qs.TileLayout, com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final void setListening(boolean z, UiEventLogger uiEventLogger) {
        boolean z2 = !this.mListening && z;
        super.setListening(z, uiEventLogger);
        if (z2) {
            for (int i = 0; i < Math.min(this.mRecords.size(), this.mColumns); i++) {
                QSTile qSTile = ((SecQSPanelControllerBase.TileRecord) this.mRecords.get(i)).tile;
                uiEventLogger.logWithInstanceId(QSEvent.QQS_TILE_VISIBLE, 0, qSTile.getMetricsSpec(), qSTile.getInstanceId());
            }
        }
    }

    @Override // com.android.systemui.qs.TileLayout, com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final boolean updateResources() {
        if (this.mContext != null) {
            this.mCellWidth = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getTileImageSize(this.mContext);
            this.mCellHeight = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getTileImageSize(this.mContext);
            this.mCellMarginHorizontal = 0;
            SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
            Context context = this.mContext;
            this.mSidePadding = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getQQSPanelStartEndPadding(this.mOrientation, context);
        }
        return false;
    }
}
