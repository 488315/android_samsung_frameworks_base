package com.android.systemui.volume.dialog.sliders.ui.compose;

import androidx.compose.material3.SliderState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.util.ListUtilsKt;
import com.android.systemui.volume.dialog.sliders.ui.compose.Contents;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.KotlinNothingValueException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.builders.MapBuilder;
import kotlin.collections.builders.MapBuilderKeys;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class TrackMeasurePolicy implements MeasurePolicy, SliderIconsState {
    public final float gapSize;
    public final boolean isVertical;
    public final Map isVisible;
    public final boolean shouldMirrorIcons;
    public final SliderState sliderState;

    public /* synthetic */ TrackMeasurePolicy(SliderState sliderState, boolean z, float f, boolean z2, DefaultConstructorMarker defaultConstructorMarker) {
        this(sliderState, z, f, z2);
    }

    public final boolean isActiveTrackStartIconVisible() {
        Map map = this.isVisible;
        Contents contents = Contents.Active.TrackStartIcon.INSTANCE;
        if (this.shouldMirrorIcons) {
            contents = contents.getMirrored();
        }
        return ((Boolean) ((MutableState) MapsKt__MapsKt.getValue(contents, map)).getValue()).booleanValue();
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo3measure3p2s80s(final MeasureScope measureScope, List list, long j) {
        MeasureResult layout$1;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Measurable measurable = (Measurable) list.get(i);
            Object layoutId = LayoutIdKt.getLayoutId(measurable);
            Contents.Track track = Contents.Track.INSTANCE;
            if (Intrinsics.areEqual(layoutId, track)) {
                final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(j);
                int min = Math.min(mo608measureBRTryo0.width, mo608measureBRTryo0.height);
                long m814copyZbe2FdA$default = Constraints.m814copyZbe2FdA$default(j, 0, min, 0, min, 5);
                MapBuilder mapBuilder = new MapBuilder();
                mapBuilder.put(track, mo608measureBRTryo0);
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    Measurable measurable2 = (Measurable) it.next();
                    if (!Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable2), Contents.Track.INSTANCE)) {
                        Contents contents = (Contents) LayoutIdKt.getLayoutId(measurable2);
                        if (this.shouldMirrorIcons) {
                            contents = contents.getMirrored();
                        }
                        mapBuilder.put(contents, measurable2.mo608measureBRTryo0(m814copyZbe2FdA$default));
                    }
                }
                final MapBuilder build = mapBuilder.build();
                layout$1 = measureScope.layout$1(mo608measureBRTryo0.width, mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.volume.dialog.sliders.ui.compose.TrackMeasurePolicy$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                        TrackMeasurePolicy trackMeasurePolicy = this;
                        int mo51roundToPx0680j_4 = MeasureScope.this.mo51roundToPx0680j_4(trackMeasurePolicy.gapSize);
                        boolean z = trackMeasurePolicy.shouldMirrorIcons;
                        SliderState sliderState = trackMeasurePolicy.sliderState;
                        float coercedValueAsFraction = z ? 1 - sliderState.getCoercedValueAsFraction() : sliderState.getCoercedValueAsFraction();
                        MapBuilder mapBuilder2 = build;
                        Iterator it2 = ((MapBuilderKeys) mapBuilder2.keySet()).iterator();
                        while (it2.hasNext()) {
                            Contents contents2 = (Contents) it2.next();
                            Placeable placeable = (Placeable) MapsKt__MapsKt.getValue(contents2, mapBuilder2);
                            Placeable placeable2 = mo608measureBRTryo0;
                            boolean z2 = trackMeasurePolicy.isVertical;
                            if (z2) {
                                placementScope.place(placeable, 0, contents2.calculatePosition(coercedValueAsFraction, placeable.height, placeable2.height, mo51roundToPx0680j_4), 0.0f);
                            } else {
                                placementScope.place(placeable, contents2.calculatePosition(coercedValueAsFraction, placeable.width, placeable2.width, mo51roundToPx0680j_4), 0, 0.0f);
                            }
                            if (!Intrinsics.areEqual(contents2, Contents.Track.INSTANCE)) {
                                MutableState mutableState = (MutableState) MapsKt__MapsKt.getValue(contents2, trackMeasurePolicy.isVisible);
                                boolean isVisible = contents2.isVisible(coercedValueAsFraction, z2 ? placeable.height : placeable.width, z2 ? placeable2.height : placeable2.width, mo51roundToPx0680j_4);
                                if (((Boolean) mutableState.getValue()).booleanValue() != isVisible) {
                                    mutableState.setValue(Boolean.valueOf(isVisible));
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                });
                return layout$1;
            }
        }
        ListUtilsKt.throwNoSuchElementException("Collection contains no element matching the predicate.");
        throw new KotlinNothingValueException();
    }

    private TrackMeasurePolicy(SliderState sliderState, boolean z, float f, boolean z2) {
        this.sliderState = sliderState;
        this.shouldMirrorIcons = z;
        this.gapSize = f;
        this.isVertical = z2;
        Contents.Active.TrackStartIcon trackStartIcon = Contents.Active.TrackStartIcon.INSTANCE;
        Boolean bool = Boolean.FALSE;
        this.isVisible = MapsKt__MapsKt.mutableMapOf(new Pair(trackStartIcon, SnapshotStateKt.mutableStateOf$default(bool)), new Pair(Contents.Active.TrackEndIcon.INSTANCE, SnapshotStateKt.mutableStateOf$default(bool)), new Pair(Contents.Inactive.TrackStartIcon.INSTANCE, SnapshotStateKt.mutableStateOf$default(bool)), new Pair(Contents.Inactive.TrackEndIcon.INSTANCE, SnapshotStateKt.mutableStateOf$default(bool)));
    }
}
