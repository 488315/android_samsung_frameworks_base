package com.samsung.sesl.compose.phone.ui.hapticfeedback;

import android.graphics.Rect;
import android.view.View;
import com.samsung.sesl.sep.compat.view.SepHapticFeedbackConstantsCompat;
import com.samsung.sesl.sep.reflect.SepBaseReflector;
import com.samsung.sesl.sep.reflect.view.SepViewReflector;
import java.lang.reflect.Method;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes4.dex */
final /* synthetic */ class SeslPhoneHapticFeedbackConstantsKt$SeslPhoneHapticFeedbackConstants$1 extends FunctionReferenceImpl implements Function1 {
    public SeslPhoneHapticFeedbackConstantsKt$SeslPhoneHapticFeedbackConstants$1(Object obj) {
        super(1, obj, SepHapticFeedbackConstantsCompat.class, "canHapticFeedback", "canHapticFeedback(Landroid/view/View;)Z", 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0046  */
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo781invoke(Object obj) throws NoSuchMethodException, SecurityException {
        View view = (View) obj;
        ((SepHapticFeedbackConstantsCompat) this.receiver).getClass();
        boolean z = false;
        if (SepHapticFeedbackConstantsCompat.SUPPORT_TOUCH_FEEDBACK && view.hasWindowFocus()) {
            SepViewReflector.INSTANCE.getClass();
            SepBaseReflector.INSTANCE.getClass();
            Method declaredMethod = SepBaseReflector.getDeclaredMethod(SepViewReflector.mClass, "isVisibleToUser", Rect.class);
            if (declaredMethod != null) {
                Object objInvoke = SepBaseReflector.invoke(view, declaredMethod, null);
                Boolean bool = objInvoke instanceof Boolean ? (Boolean) objInvoke : null;
                boolean zBooleanValue = bool != null ? bool.booleanValue() : false;
                if (zBooleanValue && !view.isTemporarilyDetached()) {
                    z = true;
                }
            }
        }
        return Boolean.valueOf(z);
    }
}
