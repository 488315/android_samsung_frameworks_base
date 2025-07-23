package com.android.systemui.notifications.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.foundation.ScrollState;
import androidx.compose.runtime.MutableState;
import androidx.compose.ui.geometry.Rect;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.shared.model.AccessibilityScrollEvent;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationScrollView;
import java.util.function.Consumer;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NotificationsKt$NotificationScrollingStack$6$1$1 implements Consumer {
    public final /* synthetic */ CoroutineScope $coroutineScope;
    public final /* synthetic */ Function0 $minScrimOffset;
    public final /* synthetic */ Animatable $scrimOffset;
    public final /* synthetic */ ScrollState $scrollState;
    public final /* synthetic */ MutableState $stackBoundsOnScreen;
    public final /* synthetic */ NotificationScrollView $stackScrollView;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.notifications.ui.composable.NotificationsKt$NotificationScrollingStack$6$1$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function0 $minScrimOffset;
        final /* synthetic */ Animatable<Float, AnimationVector1D> $scrimOffset;
        final /* synthetic */ float $scrollPosition;
        final /* synthetic */ ScrollState $scrollState;
        final /* synthetic */ float $targetScroll;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(float f, float f2, Animatable<Float, AnimationVector1D> animatable, Function0 function0, ScrollState scrollState, Continuation continuation) {
            super(2, continuation);
            this.$targetScroll = f;
            this.$scrollPosition = f2;
            this.$scrimOffset = animatable;
            this.$minScrimOffset = function0;
            this.$scrollState = scrollState;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$targetScroll, this.$scrollPosition, this.$scrimOffset, this.$minScrimOffset, this.$scrollState, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                float f = this.$targetScroll - this.$scrollPosition;
                Animatable<Float, AnimationVector1D> animatable = this.$scrimOffset;
                Function0 function0 = this.$minScrimOffset;
                ScrollState scrollState = this.$scrollState;
                this.label = 1;
                if (NotificationsKt.access$scrollNotificationStack(f, false, animatable, function0, scrollState, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AccessibilityScrollEvent.values().length];
            try {
                iArr[AccessibilityScrollEvent.SCROLL_UP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[AccessibilityScrollEvent.SCROLL_DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public NotificationsKt$NotificationScrollingStack$6$1$1(MutableState<Rect> mutableState, NotificationScrollView notificationScrollView, ScrollState scrollState, CoroutineScope coroutineScope, Animatable<Float, AnimationVector1D> animatable, Function0 function0) {
        this.$stackBoundsOnScreen = mutableState;
        this.$stackScrollView = notificationScrollView;
        this.$scrollState = scrollState;
        this.$coroutineScope = coroutineScope;
        this.$scrimOffset = animatable;
        this.$minScrimOffset = function0;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = WhenMappings.$EnumSwitchMapping$0[((AccessibilityScrollEvent) obj).ordinal()];
        int i2 = 1;
        if (i == 1) {
            i2 = -1;
        } else if (i != 2) {
            throw new NoWhenBranchMatchedException();
        }
        Rect rect = (Rect) this.$stackBoundsOnScreen.getValue();
        float f = rect.bottom - rect.top;
        NotificationStackScrollLayout notificationStackScrollLayout = (NotificationStackScrollLayout) this.$stackScrollView;
        float max = Math.max(0.0f, f - (notificationStackScrollLayout.mShelf.getHeight() + notificationStackScrollLayout.mPaddingBetweenElements));
        float value = this.$scrollState.getValue();
        BuildersKt.launch$default(this.$coroutineScope, null, null, new AnonymousClass1(RangesKt___RangesKt.coerceIn((i2 * max) + value, 0.0f, this.$scrollState.getMaxValue()), value, this.$scrimOffset, this.$minScrimOffset, this.$scrollState, null), 3);
    }
}
