package com.android.systemui.shared.clocks;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.plugins.ClockController;
import com.android.systemui.plugins.ClockMetadata;
import com.android.systemui.plugins.ClockProvider;
import com.android.systemui.plugins.ClockSettings;
import java.util.Collections;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DefaultClockProvider implements ClockProvider {
    public final Context ctx;
    public final boolean hasStepClockAnimation;
    public final LayoutInflater layoutInflater;
    public final Resources resources;

    public DefaultClockProvider(Context context, LayoutInflater layoutInflater, Resources resources, boolean z) {
        this.ctx = context;
        this.layoutInflater = layoutInflater;
        this.resources = resources;
        this.hasStepClockAnimation = z;
    }

    @Override // com.android.systemui.plugins.ClockProvider
    public final ClockController createClock(String str) {
        return ClockProvider.DefaultImpls.createClock(this, str);
    }

    @Override // com.android.systemui.plugins.ClockProvider
    public final Drawable getClockThumbnail(String str) {
        if (Intrinsics.areEqual(str, "DEFAULT")) {
            return this.resources.getDrawable(R.drawable.clock_default_thumbnail, null);
        }
        throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str, " is unsupported by ", DefaultClockProviderKt.TAG));
    }

    @Override // com.android.systemui.plugins.ClockProvider
    public final List getClocks() {
        return Collections.singletonList(new ClockMetadata("DEFAULT", "Default Clock"));
    }

    @Override // com.android.systemui.plugins.ClockProvider
    public final ClockController createClock(ClockSettings clockSettings) {
        if (Intrinsics.areEqual(clockSettings.getClockId(), "DEFAULT")) {
            return new DefaultClockController(this.ctx, this.layoutInflater, this.resources, clockSettings, this.hasStepClockAnimation);
        }
        throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(clockSettings.getClockId(), " is unsupported by ", DefaultClockProviderKt.TAG));
    }

    public /* synthetic */ DefaultClockProvider(Context context, LayoutInflater layoutInflater, Resources resources, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, layoutInflater, resources, (i & 8) != 0 ? false : z);
    }
}
