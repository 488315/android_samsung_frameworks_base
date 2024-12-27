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
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecTileLayout {
    public final IntSupplier cellHeightSupplier;
    public final IntSupplier cellMarginHorizontalSupplier;
    public final IntSupplier cellWidthSupplier;
    public final IntSupplier columnsSupplier;
    public final IntSupplier getLayoutDirectionSupplier;
    public final IntFunction getRowTopFunction;
    public final ArrayList records;
    public final IntSupplier rowsSupplier;
    public final IntSupplier sidePaddingSupplier;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public SecTileLayout(IntSupplier intSupplier, IntSupplier intSupplier2, IntSupplier intSupplier3, IntConsumer intConsumer, IntSupplier intSupplier4, Supplier<Context> supplier, IntSupplier intSupplier5, IntFunction<Integer> intFunction, ArrayList<SecQSPanelControllerBase.TileRecord> arrayList, IntSupplier intSupplier6, IntSupplier intSupplier7, BiFunction<Integer, Integer, Boolean> biFunction) {
        this.cellHeightSupplier = intSupplier;
        this.cellMarginHorizontalSupplier = intSupplier2;
        this.cellWidthSupplier = intSupplier3;
        this.columnsSupplier = intSupplier4;
        this.getLayoutDirectionSupplier = intSupplier5;
        this.getRowTopFunction = intFunction;
        this.records = arrayList;
        this.rowsSupplier = intSupplier6;
        this.sidePaddingSupplier = intSupplier7;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecTileLayout$resourcePicker$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecTileLayout$settingsHelper$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
            }
        });
    }
}
