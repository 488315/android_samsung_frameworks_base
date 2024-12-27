package com.android.systemui.qs.tileimpl;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SecQSIconViewImpl {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final boolean isNoBgLargeTile;
    public final QuickCustomTileIconResize quickCustomTileIconResize;
    public final Lazy resourcePicker$delegate;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SecQSIconViewImpl(Context context, boolean z) {
        this.context = context;
        this.isNoBgLargeTile = z;
        this.resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.tileimpl.SecQSIconViewImpl$resourcePicker$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
            }
        });
        this.quickCustomTileIconResize = new QuickCustomTileIconResize(context);
    }

    public final int getIconSize() {
        boolean z = this.isNoBgLargeTile;
        Lazy lazy = this.resourcePicker$delegate;
        if (z) {
            return ((SecQSPanelResourcePicker) lazy.getValue()).getNoBGTileIconSize(this.context);
        }
        SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) lazy.getValue();
        Context context = this.context;
        secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        return SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_style_qs_tile_image_size, context);
    }

    public /* synthetic */ SecQSIconViewImpl(Context context, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? false : z);
    }
}
