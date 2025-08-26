package androidx.compose.ui.window;

import androidx.compose.runtime.MonotonicFrameClockKt;
import androidx.compose.ui.platform.InfiniteAnimationPolicy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* loaded from: classes.dex */
final class AndroidPopup_androidKt$Popup$5$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ PopupLayout $popupLayout;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AndroidPopup_androidKt$Popup$5$1(PopupLayout popupLayout, Continuation continuation) {
        super(2, continuation);
        this.$popupLayout = popupLayout;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AndroidPopup_androidKt$Popup$5$1 androidPopup_androidKt$Popup$5$1 = new AndroidPopup_androidKt$Popup$5$1(this.$popupLayout, continuation);
        androidPopup_androidKt$Popup$5$1.L$0 = obj;
        return androidPopup_androidKt$Popup$5$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AndroidPopup_androidKt$Popup$5$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Path cross not found for [B:18:0x005c, B:20:0x0060], limit reached: 25 */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x006a  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:14:0x0045 -> B:16:0x0048). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineScope coroutineScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            coroutineScope = (CoroutineScope) this.L$0;
            if (CoroutineScopeKt.isActive(coroutineScope)) {
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            coroutineScope = (CoroutineScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            PopupLayout popupLayout = this.$popupLayout;
            int[] iArr = popupLayout.locationOnScreen;
            int i2 = iArr[0];
            int i3 = iArr[1];
            popupLayout.composeView.getLocationOnScreen(iArr);
            int[] iArr2 = popupLayout.locationOnScreen;
            if (i2 == iArr2[0] || i3 != iArr2[1]) {
                popupLayout.updateParentBounds$ui_release();
            }
            if (CoroutineScopeKt.isActive(coroutineScope)) {
                AnonymousClass1 anonymousClass1 = new Function1() { // from class: androidx.compose.ui.window.AndroidPopup_androidKt$Popup$5$1.1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj2) {
                        ((Number) obj2).longValue();
                        return Unit.INSTANCE;
                    }
                };
                this.L$0 = coroutineScope;
                this.label = 1;
                if (getContext().get(InfiniteAnimationPolicy.Key) != null) {
                    throw new ClassCastException();
                }
                if (MonotonicFrameClockKt.getMonotonicFrameClock(getContext()).withFrameNanos(anonymousClass1, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                PopupLayout popupLayout2 = this.$popupLayout;
                int[] iArr3 = popupLayout2.locationOnScreen;
                int i22 = iArr3[0];
                int i32 = iArr3[1];
                popupLayout2.composeView.getLocationOnScreen(iArr3);
                int[] iArr22 = popupLayout2.locationOnScreen;
                if (i22 == iArr22[0]) {
                }
                popupLayout2.updateParentBounds$ui_release();
                if (CoroutineScopeKt.isActive(coroutineScope)) {
                    return Unit.INSTANCE;
                }
            }
        }
    }
}
