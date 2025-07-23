package com.android.systemui.shared.clocks;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.view.LayoutInflater;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.customization.R$drawable;
import com.android.systemui.customization.R$string;
import com.android.systemui.plugins.clocks.AxisPresetConfig;
import com.android.systemui.plugins.clocks.ClockAxisStyle;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockFontAxis;
import com.android.systemui.plugins.clocks.ClockLogger;
import com.android.systemui.plugins.clocks.ClockMessageBuffers;
import com.android.systemui.plugins.clocks.ClockMetadata;
import com.android.systemui.plugins.clocks.ClockPickerConfig;
import com.android.systemui.plugins.clocks.ClockProvider;
import com.android.systemui.plugins.clocks.ClockSettings;
import com.android.systemui.shared.clocks.FlexClockController;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DefaultClockProvider implements ClockProvider {
    public static final Companion Companion = new Companion(null);
    public static final Lazy FLEX_TYPEFACE$delegate = LazyKt__LazyJVMKt.lazy(new DefaultClockProvider$$ExternalSyntheticLambda0());
    public final Context ctx;
    public final boolean isClockReactiveVariantsEnabled;
    public final LayoutInflater layoutInflater;
    public ClockMessageBuffers messageBuffers;
    public final Resources resources;
    public final Vibrator vibrator;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public DefaultClockProvider(Context context, LayoutInflater layoutInflater, Resources resources, boolean z, Vibrator vibrator) {
        this.ctx = context;
        this.layoutInflater = layoutInflater;
        this.resources = resources;
        this.isClockReactiveVariantsEnabled = z;
        this.vibrator = vibrator;
    }

    @Override // com.android.systemui.plugins.clocks.ClockProvider
    public final ClockController createClock(ClockSettings clockSettings) {
        List clocks = getClocks();
        if (!(clocks instanceof Collection) || !clocks.isEmpty()) {
            Iterator it = clocks.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(((ClockMetadata) it.next()).getClockId(), clockSettings.getClockId())) {
                    if (!this.isClockReactiveVariantsEnabled) {
                        return new DefaultClockController(this.ctx, this.layoutInflater, this.resources, clockSettings, this.messageBuffers);
                    }
                    ClockMessageBuffers clockMessageBuffers = this.messageBuffers;
                    if (clockMessageBuffers == null) {
                        clockMessageBuffers = new ClockMessageBuffers(ClockLogger.Companion.getDEFAULT_MESSAGE_BUFFER());
                    }
                    ClockMessageBuffers clockMessageBuffers2 = clockMessageBuffers;
                    ClockFontAxis.Companion companion = ClockFontAxis.Companion;
                    FlexClockController.Companion.getClass();
                    return new FlexClockController(new ClockContext(this.ctx, this.resources, ClockSettings.copy$default(clockSettings, null, null, new ClockAxisStyle(companion.merge(FlexClockController.Companion.getDefaultAxes(clockSettings), clockSettings.getAxes())), 3, null), new TypefaceCache(clockMessageBuffers2.getInfraMessageBuffer(), 30, new DefaultClockProvider$$ExternalSyntheticLambda1()), clockMessageBuffers2, clockMessageBuffers2.getInfraMessageBuffer(), this.vibrator));
                }
            }
        }
        throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(clockSettings.getClockId(), " is unsupported by ", DefaultClockProviderKt.TAG));
    }

    @Override // com.android.systemui.plugins.clocks.ClockProvider
    public final ClockPickerConfig getClockPickerConfig(ClockSettings clockSettings) {
        List clocks = getClocks();
        if (!(clocks instanceof Collection) || !clocks.isEmpty()) {
            Iterator it = clocks.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(((ClockMetadata) it.next()).getClockId(), clockSettings.getClockId())) {
                    if (!this.isClockReactiveVariantsEnabled) {
                        String clockId = clockSettings.getClockId();
                        return new ClockPickerConfig(clockId == null ? "DEFAULT" : clockId, this.resources.getString(R$string.clock_default_name), this.resources.getString(R$string.clock_default_description), this.resources.getDrawable(R$drawable.clock_default_thumbnail, null), true, EmptyList.INSTANCE, null);
                    }
                    ClockFontAxis.Companion companion = ClockFontAxis.Companion;
                    FlexClockController.Companion.getClass();
                    List<ClockFontAxis> merge = companion.merge(FlexClockController.Companion.getDefaultAxes(clockSettings), clockSettings.getAxes());
                    String clockId2 = clockSettings.getClockId();
                    String str = clockId2 == null ? "DEFAULT" : clockId2;
                    String string = this.resources.getString(R$string.clock_default_name);
                    String string2 = this.resources.getString(R$string.clock_default_description);
                    Drawable drawable = this.resources.getDrawable(R$drawable.clock_default_thumbnail, null);
                    AxisPresetConfig axisPresetConfig = new AxisPresetConfig(Arrays.asList(FlexClockController.Companion.buildPresetGroup(this.resources, true), FlexClockController.Companion.buildPresetGroup(this.resources, false)), null, 2, null);
                    return new ClockPickerConfig(str, string, string2, drawable, true, merge, AxisPresetConfig.copy$default(axisPresetConfig, null, axisPresetConfig.findStyle(new ClockAxisStyle(merge)), 1, null));
                }
            }
        }
        throw new IllegalArgumentException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(clockSettings.getClockId(), " is unsupported by ", DefaultClockProviderKt.TAG));
    }

    @Override // com.android.systemui.plugins.clocks.ClockProvider
    public final List getClocks() {
        List singletonList = Collections.singletonList(new ClockMetadata("DEFAULT", false, null, 6, null));
        return this.isClockReactiveVariantsEnabled ? CollectionsKt___CollectionsKt.plus(singletonList, new ClockMetadata("DIGITAL_CLOCK_FLEX", true, "DEFAULT")) : singletonList;
    }

    @Override // com.android.systemui.plugins.clocks.ClockProvider
    public final void initialize(ClockMessageBuffers clockMessageBuffers) {
        this.messageBuffers = clockMessageBuffers;
    }

    public /* synthetic */ DefaultClockProvider(Context context, LayoutInflater layoutInflater, Resources resources, boolean z, Vibrator vibrator, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, layoutInflater, resources, (i & 8) != 0 ? false : z, vibrator);
    }
}
