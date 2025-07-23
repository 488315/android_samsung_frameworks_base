package com.android.systemui.touchpad.tutorial.ui.composable;

import androidx.compose.ui.focus.FocusRequester;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.Screen;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class TutorialSelectionScreenKt$TwoByTwoTutorialButtons$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ FocusRequester $backFocusRequester;
    final /* synthetic */ FocusRequester $homeFocusRequester;
    final /* synthetic */ Screen $lastSelectedScreen;
    final /* synthetic */ FocusRequester $recentAppsFocusRequester;
    final /* synthetic */ FocusRequester $switchAppsFocusRequester;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Screen.values().length];
            try {
                iArr[Screen.HOME_GESTURE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Screen.BACK_GESTURE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Screen.RECENT_APPS_GESTURE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Screen.SWITCH_APPS_GESTURE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TutorialSelectionScreenKt$TwoByTwoTutorialButtons$1$1(Screen screen, FocusRequester focusRequester, FocusRequester focusRequester2, FocusRequester focusRequester3, FocusRequester focusRequester4, Continuation continuation) {
        super(2, continuation);
        this.$lastSelectedScreen = screen;
        this.$homeFocusRequester = focusRequester;
        this.$backFocusRequester = focusRequester2;
        this.$recentAppsFocusRequester = focusRequester3;
        this.$switchAppsFocusRequester = focusRequester4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TutorialSelectionScreenKt$TwoByTwoTutorialButtons$1$1(this.$lastSelectedScreen, this.$homeFocusRequester, this.$backFocusRequester, this.$recentAppsFocusRequester, this.$switchAppsFocusRequester, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TutorialSelectionScreenKt$TwoByTwoTutorialButtons$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = WhenMappings.$EnumSwitchMapping$0[this.$lastSelectedScreen.ordinal()];
        if (i == 1) {
            FocusRequester.m376requestFocus3ESFkO8$default(this.$homeFocusRequester);
        } else if (i == 2) {
            FocusRequester.m376requestFocus3ESFkO8$default(this.$backFocusRequester);
        } else if (i == 3) {
            FocusRequester.m376requestFocus3ESFkO8$default(this.$recentAppsFocusRequester);
        } else if (i != 4) {
            Unit unit = Unit.INSTANCE;
        } else {
            FocusRequester.m376requestFocus3ESFkO8$default(this.$switchAppsFocusRequester);
        }
        return Unit.INSTANCE;
    }
}
