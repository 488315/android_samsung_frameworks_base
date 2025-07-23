package androidx.compose.ui.platform;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.node.WeakReference;
import androidx.compose.ui.text.input.NullableInputConnectionWrapper;
import androidx.compose.ui.text.input.PlatformTextInputService;
import androidx.compose.ui.text.input.TextInputService;
import androidx.compose.ui.text.input.TextInputSession;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AndroidPlatformTextInputSession$startInputMethod$3 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ AndroidPlatformTextInputSession this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AndroidPlatformTextInputSession$startInputMethod$3(AndroidPlatformTextInputSession androidPlatformTextInputSession, Continuation continuation) {
        super(2, continuation);
        this.this$0 = androidPlatformTextInputSession;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AndroidPlatformTextInputSession$startInputMethod$3 androidPlatformTextInputSession$startInputMethod$3 = new AndroidPlatformTextInputSession$startInputMethod$3(this.this$0, continuation);
        androidPlatformTextInputSession$startInputMethod$3.L$0 = obj;
        return androidPlatformTextInputSession$startInputMethod$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AndroidPlatformTextInputSession$startInputMethod$3) create((InputMethodSession) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final InputMethodSession inputMethodSession = (InputMethodSession) this.L$0;
            final AndroidPlatformTextInputSession androidPlatformTextInputSession = this.this$0;
            this.L$0 = inputMethodSession;
            this.L$1 = androidPlatformTextInputSession;
            this.label = 1;
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
            cancellableContinuationImpl.initCancellability();
            TextInputService textInputService = androidPlatformTextInputSession.textInputService;
            PlatformTextInputService platformTextInputService = textInputService.platformTextInputService;
            platformTextInputService.startInput();
            textInputService._currentInputSession.set(new TextInputSession(textInputService, platformTextInputService));
            cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: androidx.compose.ui.platform.AndroidPlatformTextInputSession$startInputMethod$3$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    InputMethodSession inputMethodSession2 = InputMethodSession.this;
                    synchronized (inputMethodSession2.lock) {
                        try {
                            inputMethodSession2.disposed = true;
                            MutableVector mutableVector = inputMethodSession2.connections;
                            Object[] objArr = mutableVector.content;
                            int i2 = mutableVector.size;
                            for (int i3 = 0; i3 < i2; i3++) {
                                NullableInputConnectionWrapper nullableInputConnectionWrapper = (NullableInputConnectionWrapper) ((WeakReference) objArr[i3]).get();
                                if (nullableInputConnectionWrapper != null) {
                                    nullableInputConnectionWrapper.disposeDelegate();
                                }
                            }
                            inputMethodSession2.connections.clear();
                            Unit unit = Unit.INSTANCE;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    TextInputService textInputService2 = androidPlatformTextInputSession.textInputService;
                    textInputService2._currentInputSession.set(null);
                    textInputService2.platformTextInputService.stopInput();
                    return Unit.INSTANCE;
                }
            });
            if (cancellableContinuationImpl.getResult() == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
