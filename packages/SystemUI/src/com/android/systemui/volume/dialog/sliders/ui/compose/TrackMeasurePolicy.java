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
        Contents mirrored = Contents.Active.TrackStartIcon.INSTANCE;
        if (this.shouldMirrorIcons) {
            mirrored = mirrored.getMirrored();
        }
        return ((Boolean) ((MutableState) MapsKt__MapsKt.getValue(mirrored, map)).getValue()).booleanValue();
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo3measure3p2s80s(final MeasureScope measureScope, List list, long j) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Measurable measurable = (Measurable) list.get(i);
            Object layoutId = LayoutIdKt.getLayoutId(measurable);
            Contents.Track track = Contents.Track.INSTANCE;
            if (Intrinsics.areEqual(layoutId, track)) {
                final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(j);
                int iMin = Math.min(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height);
                long jM816copyZbe2FdA$default = Constraints.m816copyZbe2FdA$default(j, 0, iMin, 0, iMin, 5);
                MapBuilder mapBuilder = new MapBuilder();
                mapBuilder.put(track, placeableMo610measureBRTryo0);
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    Measurable measurable2 = (Measurable) it.next();
                    if (!Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable2), Contents.Track.INSTANCE)) {
                        Contents mirrored = (Contents) LayoutIdKt.getLayoutId(measurable2);
                        if (this.shouldMirrorIcons) {
                            mirrored = mirrored.getMirrored();
                        }
                        mapBuilder.put(mirrored, measurable2.mo610measureBRTryo0(jM816copyZbe2FdA$default));
                    }
                }
                final MapBuilder mapBuilderBuild = mapBuilder.build();
                return measureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.volume.dialog.sliders.ui.compose.TrackMeasurePolicy$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                        TrackMeasurePolicy trackMeasurePolicy = this;
                        int iMo52roundToPx0680j_4 = measureScope.mo52roundToPx0680j_4(trackMeasurePolicy.gapSize);
                        boolean z = trackMeasurePolicy.shouldMirrorIcons;
                        SliderState sliderState = trackMeasurePolicy.sliderState;
                        float coercedValueAsFraction = z ? 1 - sliderState.getCoercedValueAsFraction() : sliderState.getCoercedValueAsFraction();
                        MapBuilder mapBuilder2 = mapBuilderBuild;
                        Iterator it2 = ((MapBuilderKeys) mapBuilder2.keySet()).iterator();
                        while (it2.hasNext()) {
                            Contents contents = (Contents) it2.next();
                            Placeable placeable = (Placeable) MapsKt__MapsKt.getValue(contents, mapBuilder2);
                            Placeable placeable2 = placeableMo610measureBRTryo0;
                            boolean z2 = trackMeasurePolicy.isVertical;
                            if (z2) {
                                placementScope.place(placeable, 0, contents.calculatePosition(coercedValueAsFraction, placeable.height, placeable2.height, iMo52roundToPx0680j_4), 0.0f);
                            } else {
                                placementScope.place(placeable, contents.calculatePosition(coercedValueAsFraction, placeable.width, placeable2.width, iMo52roundToPx0680j_4), 0, 0.0f);
                            }
                            if (!Intrinsics.areEqual(contents, Contents.Track.INSTANCE)) {
                                MutableState mutableState = (MutableState) MapsKt__MapsKt.getValue(contents, trackMeasurePolicy.isVisible);
                                boolean zIsVisible = contents.isVisible(coercedValueAsFraction, z2 ? placeable.height : placeable.width, z2 ? placeable2.height : placeable2.width, iMo52roundToPx0680j_4);
                                if (((Boolean) mutableState.getValue()).booleanValue() != zIsVisible) {
                                    mutableState.setValue(Boolean.valueOf(zIsVisible));
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                });
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
