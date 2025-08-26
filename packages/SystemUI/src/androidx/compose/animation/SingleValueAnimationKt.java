package androidx.compose.animation;

import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.TwoWayConverter;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.graphics.Color;

/* loaded from: classes.dex */
public abstract class SingleValueAnimationKt {
    public static final SpringSpec colorDefaultSpring = AnimationSpecKt.spring$default(0.0f, 0.0f, null, 7);

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* renamed from: animateColorAsState-euL9pac, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final State m7animateColorAsStateeuL9pac(long j, FiniteAnimationSpec finiteAnimationSpec, String str, Composer composer, int i, int i2) {
        if ((i2 & 2) != 0) {
            finiteAnimationSpec = colorDefaultSpring;
        }
        FiniteAnimationSpec finiteAnimationSpec2 = finiteAnimationSpec;
        if ((i2 & 4) != 0) {
            str = "ColorAnimation";
        }
        String str2 = str;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.animateColorAsState (SingleValueAnimation.kt:60)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        boolean zChanged = composerImpl.changed(Color.m461getColorSpaceimpl(j));
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = (TwoWayConverter) ((ColorVectorConverterKt$ColorToVector$1) ColorVectorConverterKt.ColorToVector).mo781invoke(Color.m461getColorSpaceimpl(j));
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        int i3 = i << 6;
        State stateAnimateValueAsState = AnimateAsStateKt.animateValueAsState(Color.m456boximpl(j), (TwoWayConverter) objRememberedValue, finiteAnimationSpec2, null, str2, null, composerImpl, (i & 14) | ((i << 3) & 896) | (57344 & i3) | (i3 & 458752), 8);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return stateAnimateValueAsState;
    }
}
