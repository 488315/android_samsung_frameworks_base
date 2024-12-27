package com.android.systemui.shared.clocks;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockMessageBuffers;
import com.android.systemui.plugins.clocks.ClockMetadata;
import com.android.systemui.plugins.clocks.ClockProvider;
import com.android.systemui.plugins.clocks.ClockSettings;
import java.util.Collections;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DefaultClockProvider implements ClockProvider {
    public final Context ctx;
    public final boolean hasStepClockAnimation;
    public final LayoutInflater layoutInflater;
    public ClockMessageBuffers messageBuffers;
    public final boolean migratedClocks;
    public final Resources resources;

    public DefaultClockProvider(Context context, LayoutInflater layoutInflater, Resources resources, boolean z, boolean z2) {
        this.ctx = context;
        this.layoutInflater = layoutInflater;
        this.resources = resources;
        this.hasStepClockAnimation = z;
        this.migratedClocks = z2;
    }

    @Override // com.android.systemui.plugins.clocks.ClockProvider
    public final ClockController createClock(ClockSettings clockSettings) {
        if (!Intrinsics.areEqual(clockSettings.getClockId(), "DEFAULT")) {
            throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(clockSettings.getClockId(), " is unsupported by ", DefaultClockProviderKt.TAG));
        }
        return new DefaultClockController(this.ctx, this.layoutInflater, this.resources, clockSettings, this.hasStepClockAnimation, this.migratedClocks, this.messageBuffers);
    }

    @Override // com.android.systemui.plugins.clocks.ClockProvider
    public final Drawable getClockThumbnail(String str) {
        if (str.equals("DEFAULT")) {
            return this.resources.getDrawable(R.drawable.clock_default_thumbnail, null);
        }
        throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, " is unsupported by ", DefaultClockProviderKt.TAG));
    }

    @Override // com.android.systemui.plugins.clocks.ClockProvider
    public final List getClocks() {
        return Collections.singletonList(new ClockMetadata("DEFAULT"));
    }

    @Override // com.android.systemui.plugins.clocks.ClockProvider
    public final void initialize(ClockMessageBuffers clockMessageBuffers) {
        this.messageBuffers = clockMessageBuffers;
    }

    public /* synthetic */ DefaultClockProvider(Context context, LayoutInflater layoutInflater, Resources resources, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, layoutInflater, resources, (i & 8) != 0 ? false : z, (i & 16) != 0 ? false : z2);
    }
}
