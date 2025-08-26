package com.android.systemui.qs.tileimpl;

import android.content.Context;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class SecQSIconViewImpl {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final boolean isNoBgLargeTile;
    public final QuickCustomTileIconResize quickCustomTileIconResize;
    public final Lazy resourcePicker$delegate;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public SecQSIconViewImpl(Context context, boolean z) {
        this.context = context;
        this.isNoBgLargeTile = z;
        this.resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new SecQSIconViewImpl$$ExternalSyntheticLambda0());
        this.quickCustomTileIconResize = new QuickCustomTileIconResize(context);
    }

    public /* synthetic */ SecQSIconViewImpl(Context context, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? false : z);
    }
}
