package platform.test.motion.compose.values;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public abstract class MotionTestValueKt {
    public static final Modifier motionTestValues(Modifier modifier, Function1 function1) {
        return modifier.then(new MotionTestValuesElement(function1));
    }
}
