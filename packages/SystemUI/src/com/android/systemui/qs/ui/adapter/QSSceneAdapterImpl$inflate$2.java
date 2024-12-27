package com.android.systemui.qs.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.dagger.QSSceneComponent;
import com.android.systemui.qs.ui.adapter.QSSceneAdapter;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class QSSceneAdapterImpl$inflate$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    Object L$0;
    int label;
    final /* synthetic */ QSSceneAdapterImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSSceneAdapterImpl$inflate$2(QSSceneAdapterImpl qSSceneAdapterImpl, Context context, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qSSceneAdapterImpl;
        this.$context = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new QSSceneAdapterImpl$inflate$2(this.this$0, this.$context, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSSceneAdapterImpl$inflate$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AsyncLayoutInflater asyncLayoutInflater = (AsyncLayoutInflater) this.this$0.asyncLayoutInflaterFactory.invoke(this.$context);
            this.L$0 = asyncLayoutInflater;
            this.label = 1;
            final SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this));
            asyncLayoutInflater.inflateInternal(R.layout.qs_panel, null, new AsyncLayoutInflater.OnInflateFinishedListener() { // from class: com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl$inflate$2$view$1$1
                @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
                public final void onInflateFinished(View view, ViewGroup viewGroup) {
                    int i2 = Result.$r8$clinit;
                    Continuation.this.resumeWith(view);
                }
            }, asyncLayoutInflater.mInflater, null);
            obj = safeContinuation.getOrThrow();
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        View view = (View) obj;
        Bundle bundle = new Bundle();
        QSImpl qSImpl = (QSImpl) this.this$0._qsImpl.getValue();
        if (qSImpl != null) {
            qSImpl.onSaveInstanceState(bundle);
        }
        QSImpl qSImpl2 = (QSImpl) this.this$0._qsImpl.getValue();
        if (qSImpl2 != null) {
            qSImpl2.onDestroy();
        }
        QSSceneComponent create = this.this$0.qsSceneComponentFactory.create(view);
        QSImpl qSImpl3 = (QSImpl) this.this$0.qsImplProvider.get();
        DumpManager dumpManager = qSImpl3.mDumpManager;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "QSImpl", qSImpl3);
        qSImpl3.onComponentCreated(create, bundle);
        this.this$0._qsImpl.updateState(null, qSImpl3);
        qSImpl3.mRootView.setPadding(0, 0, 0, 0);
        qSImpl3.setContainerController(this.this$0);
        QSSceneAdapterImpl qSSceneAdapterImpl = this.this$0;
        QSSceneAdapterImpl.access$applyState(qSSceneAdapterImpl, qSImpl3, (QSSceneAdapter.State) qSSceneAdapterImpl.state.getValue());
        QSSceneAdapterImpl qSSceneAdapterImpl2 = this.this$0;
        QSImpl qSImpl4 = (QSImpl) qSSceneAdapterImpl2._qsImpl.getValue();
        QSSceneAdapter.State state = (QSSceneAdapter.State) qSSceneAdapterImpl2.state.getValue();
        if (qSImpl4 != null) {
            qSImpl4.setQsExpansion(state.getExpansion(), 1.0f, 0.0f, ((Number) state.getSquishiness().invoke()).floatValue());
        }
        return Unit.INSTANCE;
    }
}
