package androidx.compose.animation.graphics.vector;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.KeyframesSpec;
import androidx.compose.animation.core.Transition;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes.dex */
final class PropertyValues$createAnimationSpec$1 extends Lambda implements Function3 {
    final /* synthetic */ int $overallDuration;
    final /* synthetic */ PropertyValues<Object> this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PropertyValues$createAnimationSpec$1(PropertyValues<Object> propertyValues, int i) {
        super(3);
        this.this$0 = propertyValues;
        this.$overallDuration = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v1, types: [androidx.compose.animation.core.DurationBasedAnimationSpec] */
    /* JADX WARN: Type inference failed for: r12v3, types: [androidx.compose.animation.core.RepeatableSpec] */
    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyframesSpec keyframesSpecKeyframes;
        Transition.Segment segment = (Transition.Segment) obj;
        ((Number) obj3).intValue();
        ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
        composerImpl.startReplaceGroup(-361329948);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.graphics.vector.PropertyValues.createAnimationSpec.<anonymous> (Animator.kt:154)");
        }
        ArrayList arrayList = (ArrayList) this.this$0.timestamps;
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Timestamp timestamp = (Timestamp) arrayList.get(i);
            Integer numValueOf = Integer.valueOf(timestamp.timeMillis);
            PropertyValuesHolder propertyValuesHolder = timestamp.holder;
            boolean z = propertyValuesHolder instanceof PropertyValuesHolderFloat;
            final int i2 = timestamp.durationMillis;
            if (z) {
                final PropertyValuesHolderFloat propertyValuesHolderFloat = (PropertyValuesHolderFloat) propertyValuesHolder;
                propertyValuesHolderFloat.getClass();
                keyframesSpecKeyframes = AnimationSpecKt.keyframes(new Function1() { // from class: androidx.compose.animation.graphics.vector.PropertyValuesHolderFloat$asKeyframeSpec$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj4) {
                        KeyframesSpec.KeyframesSpecConfig keyframesSpecConfig = (KeyframesSpec.KeyframesSpecConfig) obj4;
                        int i3 = i2;
                        keyframesSpecConfig.durationMillis = i3;
                        List list = propertyValuesHolderFloat.animatorKeyframes;
                        int size2 = list.size();
                        for (int i4 = 0; i4 < size2; i4++) {
                            Keyframe keyframe = (Keyframe) list.get(i4);
                            keyframesSpecConfig.at((int) (i3 * keyframe.fraction), keyframe.value).easing = keyframe.interpolator;
                        }
                        return Unit.INSTANCE;
                    }
                });
            } else {
                if (!(propertyValuesHolder instanceof PropertyValuesHolderColor)) {
                    throw new RuntimeException("Unexpected value type: " + propertyValuesHolder);
                }
                final PropertyValuesHolderColor propertyValuesHolderColor = (PropertyValuesHolderColor) propertyValuesHolder;
                propertyValuesHolderColor.getClass();
                keyframesSpecKeyframes = AnimationSpecKt.keyframes(new Function1() { // from class: androidx.compose.animation.graphics.vector.PropertyValuesHolderColor$asKeyframeSpec$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj4) {
                        KeyframesSpec.KeyframesSpecConfig keyframesSpecConfig = (KeyframesSpec.KeyframesSpecConfig) obj4;
                        int i3 = i2;
                        keyframesSpecConfig.durationMillis = i3;
                        List list = propertyValuesHolderColor.animatorKeyframes;
                        int size2 = list.size();
                        for (int i4 = 0; i4 < size2; i4++) {
                            Keyframe keyframe = (Keyframe) list.get(i4);
                            keyframesSpecConfig.at((int) (i3 * keyframe.fraction), keyframe.value).easing = keyframe.interpolator;
                        }
                        return Unit.INSTANCE;
                    }
                });
            }
            Object objM10repeatable91I0pcU$default = keyframesSpecKeyframes;
            int i3 = timestamp.repeatCount;
            if (i3 != 0) {
                objM10repeatable91I0pcU$default = AnimationSpecKt.m10repeatable91I0pcU$default(i3 == -1 ? Integer.MAX_VALUE : i3 + 1, objM10repeatable91I0pcU$default, timestamp.repeatMode, 0L, 8);
            }
            arrayList2.add(new Pair(numValueOf, objM10repeatable91I0pcU$default));
        }
        FiniteAnimationSpec combinedSpec = new CombinedSpec(arrayList2);
        if (!((Boolean) segment.getTargetState()).booleanValue()) {
            combinedSpec = new ReversedSpec(combinedSpec, this.$overallDuration);
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return combinedSpec;
    }
}
