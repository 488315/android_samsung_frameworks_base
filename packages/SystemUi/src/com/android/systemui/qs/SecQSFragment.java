package com.android.systemui.qs;

import android.view.View;
import com.android.systemui.qs.cinema.QSCinemaCompany;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shade.SecPanelFoldHelper;
import com.android.systemui.shade.ShadeHeaderController;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQSFragment {
    public BooleanSupplier expandImmediate;
    public QSCinemaCompany qsCinemaFragmentAdapter;
    public final BooleanSupplier qsExpandedSupplier;
    public final QuickAnimation quickAnimation;
    public final QuickBar quickBar;
    public final QuickPanel quickPanel;
    public final QuickTile quickTile;
    public final ShadeHeaderController shadeHeaderController;
    public boolean stackScrollerOverscrolling;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SecQSFragment(FalsingManager falsingManager, BooleanSupplier booleanSupplier, SecQSDetailDisplayer secQSDetailDisplayer, Supplier<View> supplier, ShadeHeaderController shadeHeaderController) {
        this.qsExpandedSupplier = booleanSupplier;
        this.shadeHeaderController = shadeHeaderController;
        this.quickAnimation = new QuickAnimation(booleanSupplier, supplier);
        this.quickBar = new QuickBar(booleanSupplier);
        this.quickPanel = new QuickPanel(booleanSupplier);
        this.quickTile = new QuickTile(falsingManager);
        new SecPanelFoldHelper(booleanSupplier);
    }
}
