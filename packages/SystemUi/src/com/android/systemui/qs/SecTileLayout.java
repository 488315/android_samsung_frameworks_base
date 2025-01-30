package com.android.systemui.qs;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecTileLayout {
    public final IntSupplier cellHeightSupplier;
    public final IntSupplier cellMarginHorizontalSupplier;
    public final IntSupplier cellWidthSupplier;
    public final IntConsumer columnsConsumer;
    public final IntSupplier columnsSupplier;
    public final Supplier contextSupplier;
    public final IntSupplier getLayoutDirectionSupplier;
    public final IntFunction getRowTopFunction;
    public final IntSupplier lastCellWidthSupplier;
    public final ArrayList records;
    public final IntSupplier rowsSupplier;
    public final IntSupplier sidePaddingSupplier;
    public int tileVerticalMargin;
    public final BiFunction updateMaxRowsBiFunction;
    public final Lazy resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecTileLayout$resourcePicker$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        }
    });
    public final Lazy settingsHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecTileLayout$settingsHelper$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SettingsHelper) Dependency.get(SettingsHelper.class);
        }
    });
    public int height = 1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Counter {
        public int column;
        public final int columns;
        public int index;
        public final int indices;
        public int row;

        public Counter(int i, int i2) {
            this.indices = i;
            this.columns = i2;
        }
    }

    public SecTileLayout(IntSupplier intSupplier, IntSupplier intSupplier2, IntSupplier intSupplier3, IntConsumer intConsumer, IntSupplier intSupplier4, Supplier<Context> supplier, IntSupplier intSupplier5, IntFunction<Integer> intFunction, ArrayList<SecQSPanelControllerBase.TileRecord> arrayList, IntSupplier intSupplier6, IntSupplier intSupplier7, BiFunction<Integer, Integer, Boolean> biFunction, IntSupplier intSupplier8) {
        this.cellHeightSupplier = intSupplier;
        this.cellMarginHorizontalSupplier = intSupplier2;
        this.cellWidthSupplier = intSupplier3;
        this.columnsConsumer = intConsumer;
        this.columnsSupplier = intSupplier4;
        this.contextSupplier = supplier;
        this.getLayoutDirectionSupplier = intSupplier5;
        this.getRowTopFunction = intFunction;
        this.records = arrayList;
        this.rowsSupplier = intSupplier6;
        this.sidePaddingSupplier = intSupplier7;
        this.updateMaxRowsBiFunction = biFunction;
        this.lastCellWidthSupplier = intSupplier8;
    }

    public final SecQSPanelResourcePicker getResourcePicker() {
        return (SecQSPanelResourcePicker) this.resourcePicker$delegate.getValue();
    }

    public final void update(TileLayout$$ExternalSyntheticLambda0 tileLayout$$ExternalSyntheticLambda0, Function2 function2) {
        tileLayout$$ExternalSyntheticLambda0.accept(((Number) function2.invoke(getResourcePicker(), this.contextSupplier.get())).intValue());
    }
}
