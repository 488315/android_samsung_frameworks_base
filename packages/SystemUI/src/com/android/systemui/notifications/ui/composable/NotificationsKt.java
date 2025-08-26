package com.android.systemui.notifications.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.foundation.ScrollState;
import androidx.compose.ui.graphics.ColorKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.CoroutineScopeKt;

/* loaded from: classes2.dex */
public abstract class NotificationsKt {
    static {
        ColorKt.Color$default(1.0f, 0.0f, 0.0f, 16);
        ColorKt.Color$default(0.0f, 0.0f, 1.0f, 16);
        ColorKt.Color$default(0.0f, 1.0f, 0.0f, 16);
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00e0, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r1, r2, null, null, null, r6, 14) == r0) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00f5, code lost:
    
        if (r1.snapTo(r10, r6) == r0) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x010b, code lost:
    
        if (androidx.compose.foundation.gestures.ScrollExtensionsKt.animateScrollBy(r13, r9, androidx.compose.animation.core.AnimationSpecKt.spring$default(0.0f, 0.0f, null, 7), r6) == r0) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0118, code lost:
    
        if (androidx.compose.foundation.gestures.ScrollExtensionsKt.scrollBy(r13, r9, r6) == r0) goto L55;
     */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$scrollNotificationStack(float f, boolean z, Animatable animatable, Function0 function0, ScrollState scrollState, Continuation continuation) {
        NotificationsKt$scrollNotificationStack$1 notificationsKt$scrollNotificationStack$1;
        float fFloatValue;
        float fFloatValue2;
        if (continuation instanceof NotificationsKt$scrollNotificationStack$1) {
            notificationsKt$scrollNotificationStack$1 = (NotificationsKt$scrollNotificationStack$1) continuation;
            int i = notificationsKt$scrollNotificationStack$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                notificationsKt$scrollNotificationStack$1.label = i - Integer.MIN_VALUE;
            } else {
                notificationsKt$scrollNotificationStack$1 = new NotificationsKt$scrollNotificationStack$1(continuation);
            }
        }
        NotificationsKt$scrollNotificationStack$1 notificationsKt$scrollNotificationStack$12 = notificationsKt$scrollNotificationStack$1;
        Object obj = notificationsKt$scrollNotificationStack$12.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        switch (notificationsKt$scrollNotificationStack$12.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                fFloatValue = ((Number) function0.invoke()).floatValue();
                if (((Number) animatable.internalState.getValue()).floatValue() <= fFloatValue) {
                    if (!z) {
                        notificationsKt$scrollNotificationStack$12.label = 6;
                        break;
                    } else {
                        notificationsKt$scrollNotificationStack$12.label = 5;
                        break;
                    }
                } else {
                    float fFloatValue3 = fFloatValue - (((Number) animatable.internalState.getValue()).floatValue() - f);
                    if (fFloatValue3 < 0.0f) {
                        fFloatValue3 = 0.0f;
                    }
                    int iRoundToInt = MathKt__MathJVMKt.roundToInt(fFloatValue3);
                    if (iRoundToInt > 0) {
                        if (z) {
                            NotificationsKt$scrollNotificationStack$2 notificationsKt$scrollNotificationStack$2 = new NotificationsKt$scrollNotificationStack$2(scrollState, iRoundToInt, null);
                            notificationsKt$scrollNotificationStack$12.L$0 = animatable;
                            notificationsKt$scrollNotificationStack$12.F$0 = f;
                            notificationsKt$scrollNotificationStack$12.Z$0 = z;
                            notificationsKt$scrollNotificationStack$12.F$1 = fFloatValue;
                            notificationsKt$scrollNotificationStack$12.label = 1;
                            if (CoroutineScopeKt.coroutineScope(notificationsKt$scrollNotificationStack$2, notificationsKt$scrollNotificationStack$12) != obj2) {
                            }
                        } else {
                            notificationsKt$scrollNotificationStack$12.L$0 = animatable;
                            notificationsKt$scrollNotificationStack$12.F$0 = f;
                            notificationsKt$scrollNotificationStack$12.Z$0 = z;
                            notificationsKt$scrollNotificationStack$12.F$1 = fFloatValue;
                            notificationsKt$scrollNotificationStack$12.label = 2;
                            if (scrollState.scrollTo(iRoundToInt, notificationsKt$scrollNotificationStack$12) != obj2) {
                            }
                        }
                    }
                    Animatable animatable2 = animatable;
                    fFloatValue2 = ((Number) animatable2.internalState.getValue()).floatValue() - f;
                    if (fFloatValue2 >= fFloatValue) {
                        fFloatValue = fFloatValue2;
                    }
                    if (!z) {
                        Float f2 = new Float(fFloatValue);
                        notificationsKt$scrollNotificationStack$12.L$0 = null;
                        notificationsKt$scrollNotificationStack$12.label = 3;
                        break;
                    } else {
                        Float f3 = new Float(fFloatValue);
                        notificationsKt$scrollNotificationStack$12.L$0 = null;
                        notificationsKt$scrollNotificationStack$12.label = 4;
                        break;
                    }
                }
                return obj2;
            case 1:
            case 2:
                float f4 = notificationsKt$scrollNotificationStack$12.F$1;
                z = notificationsKt$scrollNotificationStack$12.Z$0;
                float f5 = notificationsKt$scrollNotificationStack$12.F$0;
                Animatable animatable3 = (Animatable) notificationsKt$scrollNotificationStack$12.L$0;
                ResultKt.throwOnFailure(obj);
                fFloatValue = f4;
                f = f5;
                animatable = animatable3;
                Animatable animatable22 = animatable;
                fFloatValue2 = ((Number) animatable22.internalState.getValue()).floatValue() - f;
                if (fFloatValue2 >= fFloatValue) {
                }
                if (!z) {
                }
                return obj2;
            case 3:
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            case 4:
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            case 5:
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            case 6:
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
