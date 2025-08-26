package com.android.systemui.inputdevice.tutorial.ui.composable;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.State;
import androidx.compose.ui.node.Ref;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.compose.LottieAnimatable;
import com.airbnb.lottie.compose.LottieAnimatableImpl;
import com.airbnb.lottie.compose.LottieCompositionResultImpl;
import com.airbnb.lottie.model.Marker;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class TutorialAnimationKt$$ExternalSyntheticLambda9 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ State f$1;

    public /* synthetic */ TutorialAnimationKt$$ExternalSyntheticLambda9(Object obj, State state, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = state;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Marker marker;
        Marker marker2;
        switch (this.$r8$classId) {
            case 0:
                LottieComposition lottieComposition = (LottieComposition) ((LottieCompositionResultImpl) this.f$1).getValue();
                Progress progress = ((Ref) this.f$0).value;
                String startMarker = progress != null ? progress.getStartMarker() : null;
                float f = 0.0f;
                if (startMarker != null) {
                    float f2 = (lottieComposition == null || (marker = lottieComposition.getMarker(startMarker)) == null) ? 0.0f : marker.startFrame;
                    if (lottieComposition != null) {
                        float f3 = lottieComposition.startFrame;
                        f = (f2 - f3) / (lottieComposition.endFrame - f3);
                    }
                }
                return Float.valueOf(f);
            case 1:
                LottieComposition lottieComposition2 = (LottieComposition) ((LottieCompositionResultImpl) this.f$1).getValue();
                Progress progress2 = ((Ref) this.f$0).value;
                String endMarker = progress2 != null ? progress2.getEndMarker() : null;
                float f4 = 0.0f;
                if (endMarker != null) {
                    float f5 = (lottieComposition2 == null || (marker2 = lottieComposition2.getMarker(endMarker)) == null) ? 0.0f : marker2.startFrame;
                    if (lottieComposition2 != null) {
                        float f6 = lottieComposition2.startFrame;
                        f4 = (f5 - f6) / (lottieComposition2.endFrame - f6);
                    }
                }
                return Float.valueOf(f4);
            default:
                return Float.valueOf(((Boolean) ((MutableState) this.f$0).getValue()).booleanValue() ? 1.0f : ((Number) ((LottieAnimatableImpl) ((LottieAnimatable) this.f$1)).getValue()).floatValue());
        }
    }
}
