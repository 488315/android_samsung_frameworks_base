package androidx.compose.foundation.lazy.layout;

import androidx.collection.IntObjectMapKt;
import androidx.collection.MutableIntObjectMap;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.SubcomposeMeasureScope;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyLayoutMeasureScopeImpl implements LazyLayoutMeasureScope, MeasureScope {
    public final LazyLayoutItemContentFactory itemContentFactory;
    public final LazyLayoutItemProvider itemProvider;
    public final MutableIntObjectMap placeablesCache = IntObjectMapKt.mutableIntObjectMapOf();
    public final SubcomposeMeasureScope subcomposeMeasureScope;

    public LazyLayoutMeasureScopeImpl(LazyLayoutItemContentFactory lazyLayoutItemContentFactory, SubcomposeMeasureScope subcomposeMeasureScope) {
        this.itemContentFactory = lazyLayoutItemContentFactory;
        this.subcomposeMeasureScope = subcomposeMeasureScope;
        this.itemProvider = (LazyLayoutItemProvider) lazyLayoutItemContentFactory.itemProvider.invoke();
    }

    @Override // androidx.compose.ui.unit.Density
    public final float getDensity() {
        return this.subcomposeMeasureScope.getDensity();
    }

    @Override // androidx.compose.ui.unit.FontScaling
    public final float getFontScale() {
        return this.subcomposeMeasureScope.getFontScale();
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasureScope
    public final LayoutDirection getLayoutDirection() {
        return this.subcomposeMeasureScope.getLayoutDirection();
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasureScope
    public final boolean isLookingAhead() {
        return this.subcomposeMeasureScope.isLookingAhead();
    }

    @Override // androidx.compose.ui.layout.MeasureScope
    public final MeasureResult layout(int i, int i2, Map map, Function1 function1) {
        return this.subcomposeMeasureScope.layout(i, i2, map, function1);
    }

    @Override // androidx.compose.ui.layout.MeasureScope
    public final MeasureResult layout$1(int i, int i2, Map map, Function1 function1) {
        return this.subcomposeMeasureScope.layout$1(i, i2, map, function1);
    }

    /* renamed from: measure-0kLqBqw, reason: not valid java name */
    public final List m169measure0kLqBqw(int i, long j) {
        MutableIntObjectMap mutableIntObjectMap = this.placeablesCache;
        List list = (List) mutableIntObjectMap.get(i);
        if (list != null) {
            return list;
        }
        LazyLayoutItemProvider lazyLayoutItemProvider = this.itemProvider;
        Object key = lazyLayoutItemProvider.getKey(i);
        List subcompose = this.subcomposeMeasureScope.subcompose(key, this.itemContentFactory.getContent(i, key, lazyLayoutItemProvider.getContentType(i)));
        int size = subcompose.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            arrayList.add(((Measurable) subcompose.get(i2)).mo608measureBRTryo0(j));
        }
        mutableIntObjectMap.set(i, arrayList);
        return arrayList;
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: roundToPx-0680j_4 */
    public final int mo51roundToPx0680j_4(float f) {
        return this.subcomposeMeasureScope.mo51roundToPx0680j_4(f);
    }

    @Override // androidx.compose.ui.unit.FontScaling
    /* renamed from: toDp-GaN1DYA */
    public final float mo52toDpGaN1DYA(long j) {
        return this.subcomposeMeasureScope.mo52toDpGaN1DYA(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toDp-u2uoSUM */
    public final float mo54toDpu2uoSUM(int i) {
        return this.subcomposeMeasureScope.mo54toDpu2uoSUM(i);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toDpSize-k-rfVVM */
    public final long mo55toDpSizekrfVVM(long j) {
        return this.subcomposeMeasureScope.mo55toDpSizekrfVVM(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toPx--R2X_6o */
    public final float mo56toPxR2X_6o(long j) {
        return this.subcomposeMeasureScope.mo56toPxR2X_6o(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toPx-0680j_4 */
    public final float mo57toPx0680j_4(float f) {
        return this.subcomposeMeasureScope.mo57toPx0680j_4(f);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toSize-XkaWNTQ */
    public final long mo58toSizeXkaWNTQ(long j) {
        return this.subcomposeMeasureScope.mo58toSizeXkaWNTQ(j);
    }

    @Override // androidx.compose.ui.unit.FontScaling
    /* renamed from: toSp-0xMU5do */
    public final long mo59toSp0xMU5do(float f) {
        return this.subcomposeMeasureScope.mo59toSp0xMU5do(f);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toSp-kPz2Gy4 */
    public final long mo60toSpkPz2Gy4(float f) {
        return this.subcomposeMeasureScope.mo60toSpkPz2Gy4(f);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toDp-u2uoSUM */
    public final float mo53toDpu2uoSUM(float f) {
        return this.subcomposeMeasureScope.mo53toDpu2uoSUM(f);
    }
}
