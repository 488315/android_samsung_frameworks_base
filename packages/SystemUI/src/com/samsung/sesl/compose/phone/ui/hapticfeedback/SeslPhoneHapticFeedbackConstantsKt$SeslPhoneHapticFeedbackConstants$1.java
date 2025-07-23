package com.samsung.sesl.compose.phone.ui.hapticfeedback;

import android.graphics.Rect;
import android.view.View;
import com.samsung.sesl.sep.compat.view.SepHapticFeedbackConstantsCompat;
import com.samsung.sesl.sep.reflect.SepBaseReflector;
import com.samsung.sesl.sep.reflect.view.SepViewReflector;
import java.lang.reflect.Method;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final /* synthetic */ class SeslPhoneHapticFeedbackConstantsKt$SeslPhoneHapticFeedbackConstants$1 extends FunctionReferenceImpl implements Function1 {
    public SeslPhoneHapticFeedbackConstantsKt$SeslPhoneHapticFeedbackConstants$1(Object obj) {
        super(1, obj, SepHapticFeedbackConstantsCompat.class, "canHapticFeedback", "canHapticFeedback(Landroid/view/View;)Z", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        boolean z;
        View view = (View) obj;
        ((SepHapticFeedbackConstantsCompat) this.receiver).getClass();
        boolean z2 = false;
        if (SepHapticFeedbackConstantsCompat.SUPPORT_TOUCH_FEEDBACK && view.hasWindowFocus()) {
            SepViewReflector.INSTANCE.getClass();
            SepBaseReflector.INSTANCE.getClass();
            Method declaredMethod = SepBaseReflector.getDeclaredMethod(SepViewReflector.mClass, "isVisibleToUser", Rect.class);
            if (declaredMethod != null) {
                Object invoke = SepBaseReflector.invoke(view, declaredMethod, null);
                Boolean bool = invoke instanceof Boolean ? (Boolean) invoke : null;
                if (bool != null) {
                    z = bool.booleanValue();
                    if (z && !view.isTemporarilyDetached()) {
                        z2 = true;
                    }
                }
            }
            z = false;
            if (z) {
                z2 = true;
            }
        }
        return Boolean.valueOf(z2);
    }
}
