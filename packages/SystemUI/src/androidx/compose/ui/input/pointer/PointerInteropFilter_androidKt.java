package androidx.compose.ui.input.pointer;

import android.view.MotionEvent;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.viewinterop.AndroidViewHolder;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes.dex */
public abstract class PointerInteropFilter_androidKt {
    public static final Modifier motionEventSpy(Modifier modifier, final Function1 function1) {
        return SuspendingPointerInputFilterKt.pointerInput(modifier, function1, new PointerInputEventHandler() { // from class: androidx.compose.ui.input.pointer.PointerInteropFilter_androidKt.motionEventSpy.1

            /* renamed from: androidx.compose.ui.input.pointer.PointerInteropFilter_androidKt$motionEventSpy$1$1, reason: invalid class name and collision with other inner class name */
            final class C00381 extends RestrictedSuspendLambda implements Function2 {
                final /* synthetic */ Function1 $watcher;
                private /* synthetic */ Object L$0;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00381(Function1 function1, Continuation continuation) {
                    super(2, continuation);
                    this.$watcher = function1;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C00381 c00381 = new C00381(this.$watcher, continuation);
                    c00381.L$0 = obj;
                    return c00381;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C00381) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Removed duplicated region for block: B:11:0x002e A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:12:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:15:0x003a  */
                /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:12:0x002f -> B:13:0x0032). Please report as a decompilation issue!!! */
                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invokeSuspend(Object obj) {
                    AwaitPointerEventScope awaitPointerEventScope;
                    Object objAwaitPointerEvent;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                        PointerEventPass pointerEventPass = PointerEventPass.Initial;
                        this.L$0 = awaitPointerEventScope;
                        this.label = 1;
                        SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine pointerEventHandlerCoroutine = (SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope;
                        objAwaitPointerEvent = pointerEventHandlerCoroutine.awaitPointerEvent(pointerEventPass, this);
                        if (objAwaitPointerEvent == coroutineSingletons) {
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        AwaitPointerEventScope awaitPointerEventScope2 = (AwaitPointerEventScope) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        MotionEvent motionEvent$ui_release = ((PointerEvent) obj).getMotionEvent$ui_release();
                        if (motionEvent$ui_release != null) {
                            this.$watcher.mo781invoke(motionEvent$ui_release);
                        }
                        awaitPointerEventScope = awaitPointerEventScope2;
                        PointerEventPass pointerEventPass2 = PointerEventPass.Initial;
                        this.L$0 = awaitPointerEventScope;
                        this.label = 1;
                        SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine pointerEventHandlerCoroutine2 = (SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope;
                        objAwaitPointerEvent = pointerEventHandlerCoroutine2.awaitPointerEvent(pointerEventPass2, this);
                        if (objAwaitPointerEvent == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        awaitPointerEventScope2 = pointerEventHandlerCoroutine2;
                        obj = objAwaitPointerEvent;
                        MotionEvent motionEvent$ui_release2 = ((PointerEvent) obj).getMotionEvent$ui_release();
                        if (motionEvent$ui_release2 != null) {
                        }
                        awaitPointerEventScope = awaitPointerEventScope2;
                        PointerEventPass pointerEventPass22 = PointerEventPass.Initial;
                        this.L$0 = awaitPointerEventScope;
                        this.label = 1;
                        SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine pointerEventHandlerCoroutine22 = (SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope;
                        objAwaitPointerEvent = pointerEventHandlerCoroutine22.awaitPointerEvent(pointerEventPass22, this);
                        if (objAwaitPointerEvent == coroutineSingletons) {
                        }
                    }
                }
            }

            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                pointerInputScope.setInterceptOutOfBoundsChildEvents();
                Object objAwaitPointerEventScope = pointerInputScope.awaitPointerEventScope(new C00381(function1, null), continuation);
                return objAwaitPointerEventScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objAwaitPointerEventScope : Unit.INSTANCE;
            }
        });
    }

    public static final Modifier pointerInteropFilter(Modifier modifier, final AndroidViewHolder androidViewHolder) {
        PointerInteropFilter pointerInteropFilter = new PointerInteropFilter();
        pointerInteropFilter.onTouchEvent = new Function1() { // from class: androidx.compose.ui.input.pointer.PointerInteropFilter_androidKt.pointerInteropFilter.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                boolean zDispatchTouchEvent;
                MotionEvent motionEvent = (MotionEvent) obj;
                switch (motionEvent.getActionMasked()) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        zDispatchTouchEvent = androidViewHolder.dispatchTouchEvent(motionEvent);
                        break;
                    default:
                        zDispatchTouchEvent = androidViewHolder.dispatchGenericMotionEvent(motionEvent);
                        break;
                }
                return Boolean.valueOf(zDispatchTouchEvent);
            }
        };
        RequestDisallowInterceptTouchEvent requestDisallowInterceptTouchEvent = new RequestDisallowInterceptTouchEvent();
        RequestDisallowInterceptTouchEvent requestDisallowInterceptTouchEvent2 = pointerInteropFilter.requestDisallowInterceptTouchEvent;
        if (requestDisallowInterceptTouchEvent2 != null) {
            requestDisallowInterceptTouchEvent2.pointerInteropFilter = null;
        }
        pointerInteropFilter.requestDisallowInterceptTouchEvent = requestDisallowInterceptTouchEvent;
        requestDisallowInterceptTouchEvent.pointerInteropFilter = pointerInteropFilter;
        androidViewHolder.onRequestDisallowInterceptTouchEvent = requestDisallowInterceptTouchEvent;
        return modifier.then(pointerInteropFilter);
    }

    public static Modifier pointerInteropFilter$default(Modifier modifier, final Function1 function1) {
        final RequestDisallowInterceptTouchEvent requestDisallowInterceptTouchEvent = null;
        return ComposedModifierKt.composed(modifier, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.ui.input.pointer.PointerInteropFilter_androidKt.pointerInteropFilter.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                ((Number) obj3).intValue();
                ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
                composerImpl.startReplaceGroup(374375707);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.ui.input.pointer.pointerInteropFilter.<anonymous> (PointerInteropFilter.android.kt:77)");
                }
                Object objRememberedValue = composerImpl.rememberedValue();
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new PointerInteropFilter();
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                PointerInteropFilter pointerInteropFilter = (PointerInteropFilter) objRememberedValue;
                pointerInteropFilter.onTouchEvent = function1;
                RequestDisallowInterceptTouchEvent requestDisallowInterceptTouchEvent2 = requestDisallowInterceptTouchEvent;
                RequestDisallowInterceptTouchEvent requestDisallowInterceptTouchEvent3 = pointerInteropFilter.requestDisallowInterceptTouchEvent;
                if (requestDisallowInterceptTouchEvent3 != null) {
                    requestDisallowInterceptTouchEvent3.pointerInteropFilter = null;
                }
                pointerInteropFilter.requestDisallowInterceptTouchEvent = requestDisallowInterceptTouchEvent2;
                if (requestDisallowInterceptTouchEvent2 != null) {
                    requestDisallowInterceptTouchEvent2.pointerInteropFilter = pointerInteropFilter;
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                return pointerInteropFilter;
            }
        });
    }
}
