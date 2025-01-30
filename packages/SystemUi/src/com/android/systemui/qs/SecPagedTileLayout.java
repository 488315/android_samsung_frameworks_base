package com.android.systemui.qs;

import com.android.systemui.Dependency;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.qs.SecQSPanelControllerBase;
import java.util.ArrayList;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecPagedTileLayout {
    public int cellWidth;
    public final Consumer distributeTilesConsumer;
    public final Runnable distributeTilesRunnable;
    public final BooleanSupplier distributeTilesSupplier;
    public final IntSupplier getColumnCountSupplier;
    public final SecPagedTileLayout$knoxStateCallback$1 knoxStateCallback;
    public final IntConsumer lastMaxHeightConsumer;
    public final IntSupplier lastMaxHeightSupplier;
    public int layoutDirection;
    public final Supplier pagesSupplier;
    public final Supplier tilesSupplier;
    public final SecQSPanelResourcePicker resourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
    public final KnoxStateMonitor knoxStateMonitor = (KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class);
    public int lastMaxWidth = -1;
    public int pageHeight = -1;

    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.qs.SecPagedTileLayout$knoxStateCallback$1] */
    public SecPagedTileLayout(int i, Consumer<Boolean> consumer, BooleanSupplier booleanSupplier, Runnable runnable, IntSupplier intSupplier, IntConsumer intConsumer, IntSupplier intSupplier2, Supplier<ArrayList<TileLayout>> supplier, Runnable runnable2, Consumer<Boolean> consumer2, Consumer<Boolean> consumer3, Supplier<ArrayList<SecQSPanelControllerBase.TileRecord>> supplier2) {
        this.layoutDirection = i;
        this.distributeTilesConsumer = consumer;
        this.distributeTilesSupplier = booleanSupplier;
        this.distributeTilesRunnable = runnable;
        this.getColumnCountSupplier = intSupplier;
        this.lastMaxHeightConsumer = intConsumer;
        this.lastMaxHeightSupplier = intSupplier2;
        this.pagesSupplier = supplier;
        this.tilesSupplier = supplier2;
        Boolean bool = Boolean.FALSE;
        consumer2.accept(bool);
        consumer3.accept(bool);
        this.knoxStateCallback = new KnoxStateMonitorCallback() { // from class: com.android.systemui.qs.SecPagedTileLayout$knoxStateCallback$1
            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onUpdateQuickPanelEdit() {
                SecPagedTileLayout.this.distributeTilesRunnable.run();
            }
        };
    }
}
