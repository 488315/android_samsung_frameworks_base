package com.android.wm.shell.windowdecor.tiling;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.Size;
import android.view.Display;
import android.view.RoundedCorner;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.window.InputTransferToken;
import com.android.systemui.R;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class DesktopTilingDividerWindowManager extends WindowlessWindowManager implements View.OnLayoutChangeListener {
    public final Context displayContext;
    public final Rect dividerBounds;
    public boolean dividerShown;
    public final Size handleRegionSize;
    public final boolean isDarkMode;
    public final SurfaceControl leash;
    public final int maxRoundedCornerRadius;
    public boolean setTouchRegion;
    public TilingDividerView tilingDividerView;
    public final Supplier transactionSupplier;
    public final DesktopTilingWindowDecoration transitionHandler;
    public SurfaceControlViewHost viewHost;
    public final String windowName;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DesktopTilingDividerWindowManager(Configuration configuration, String str, SurfaceControl surfaceControl, SyncTransactionQueue syncTransactionQueue, DesktopTilingWindowDecoration desktopTilingWindowDecoration, Supplier<SurfaceControl.Transaction> supplier, Rect rect, Context context, boolean z) {
        super(configuration, surfaceControl, (InputTransferToken) null);
        this.windowName = str;
        this.leash = surfaceControl;
        this.transitionHandler = desktopTilingWindowDecoration;
        this.transactionSupplier = supplier;
        this.dividerBounds = rect;
        this.displayContext = context;
        this.isDarkMode = z;
        this.handleRegionSize = new Size(context.getResources().getDimensionPixelSize(R.dimen.split_divider_handle_region_height), context.getResources().getDimensionPixelSize(R.dimen.split_divider_handle_region_width));
        this.setTouchRegion = true;
        Display display = context.getDisplay();
        Iterator it = Arrays.asList(0, 1, 2, 3).iterator();
        if (!it.hasNext()) {
            throw new NoSuchElementException();
        }
        RoundedCorner roundedCorner = display.getRoundedCorner(((Number) it.next()).intValue());
        int radius = roundedCorner != null ? roundedCorner.getRadius() : 0;
        while (it.hasNext()) {
            RoundedCorner roundedCorner2 = display.getRoundedCorner(((Number) it.next()).intValue());
            int radius2 = roundedCorner2 != null ? roundedCorner2.getRadius() : 0;
            if (radius < radius2) {
                radius = radius2;
            }
        }
        this.maxRoundedCornerRadius = radius;
    }

    @Override // android.view.View.OnLayoutChangeListener
    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (this.setTouchRegion) {
            updateTouchRegion();
            this.setTouchRegion = false;
        }
    }

    public final void setSlippery(boolean z) {
        TilingDividerView tilingDividerView = this.tilingDividerView;
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) (tilingDividerView != null ? tilingDividerView.getLayoutParams() : null);
        int i = layoutParams.flags;
        if (((i & VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) != 0) == z) {
            return;
        }
        if (z) {
            layoutParams.flags = i | VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
        } else {
            layoutParams.flags = (-536870913) & i;
        }
        SurfaceControlViewHost surfaceControlViewHost = this.viewHost;
        (surfaceControlViewHost != null ? surfaceControlViewHost : null).relayout(layoutParams);
    }

    public final void updateTouchRegion() {
        int i = (-this.handleRegionSize.getWidth()) / 2;
        Rect rect = new Rect(i, 0, this.handleRegionSize.getWidth() + i, this.dividerBounds.height());
        Rect rect2 = this.dividerBounds;
        float f = this.maxRoundedCornerRadius;
        Path path = new Path();
        path.setFillType(Path.FillType.WINDING);
        float fHeight = rect2.height() / 2.0f;
        float fWidth = ((rect2.width() / 2.0f) + f) - (rect.width() / 2.0f);
        float fWidth2 = fWidth + rect.width();
        float fWidth3 = fHeight - (rect2.width() / 2.0f);
        float fWidth4 = fWidth3 + rect2.width();
        float fHeight2 = fHeight - (rect.height() / 2.0f);
        float fHeight3 = rect2.height() - f;
        Path.Direction direction = Path.Direction.CCW;
        path.addRect(fWidth, fHeight2, fWidth2, fHeight2 + rect.height(), direction);
        path.addRect(fWidth3, f, fWidth4, fHeight3, direction);
        Rect rect3 = new Rect((int) fWidth, (int) f, (int) fWidth2, (int) fHeight3);
        Region region = new Region();
        region.setPath(path, new Region(rect3));
        SurfaceControlViewHost surfaceControlViewHost = this.viewHost;
        if (surfaceControlViewHost == null) {
            surfaceControlViewHost = null;
        }
        setTouchRegion(surfaceControlViewHost.getWindowToken().asBinder(), region);
    }
}
