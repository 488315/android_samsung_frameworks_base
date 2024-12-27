package com.android.systemui.biometrics.ui.viewmodel;

import android.content.res.TypedArray;
import android.hardware.biometrics.PromptContentView;
import android.text.TextPaint;
import com.android.systemui.R;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

final class PromptViewModel$hasOnlyOneLineTitle$1 extends SuspendLambda implements Function5 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    /* synthetic */ Object L$3;
    int label;
    final /* synthetic */ PromptViewModel this$0;

    public PromptViewModel$hasOnlyOneLineTitle$1(PromptViewModel promptViewModel, Continuation continuation) {
        super(5, continuation);
        this.this$0 = promptViewModel;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        PromptViewModel$hasOnlyOneLineTitle$1 promptViewModel$hasOnlyOneLineTitle$1 = new PromptViewModel$hasOnlyOneLineTitle$1(this.this$0, (Continuation) obj5);
        promptViewModel$hasOnlyOneLineTitle$1.L$0 = (String) obj;
        promptViewModel$hasOnlyOneLineTitle$1.L$1 = (String) obj2;
        promptViewModel$hasOnlyOneLineTitle$1.L$2 = (PromptContentView) obj3;
        promptViewModel$hasOnlyOneLineTitle$1.L$3 = (String) obj4;
        return promptViewModel$hasOnlyOneLineTitle$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String str = (String) this.L$0;
        String str2 = (String) this.L$1;
        PromptContentView promptContentView = (PromptContentView) this.L$2;
        String str3 = (String) this.L$3;
        boolean z = false;
        if (str2.length() <= 0 && promptContentView == null && str3.length() <= 0) {
            int dimensionPixelSize = this.this$0.context.getResources().getDimensionPixelSize(R.dimen.biometric_prompt_two_pane_udfps_shorter_content_width);
            TypedArray obtainStyledAttributes = this.this$0.context.obtainStyledAttributes(R.style.TextAppearance_AuthCredential_Title, new int[]{android.R.attr.textSize});
            TextPaint textPaint = new TextPaint();
            textPaint.setTextSize(obtainStyledAttributes.getDimensionPixelSize(0, 0));
            float measureText = textPaint.measureText(str);
            obtainStyledAttributes.recycle();
            if (measureText / dimensionPixelSize <= 1.0f) {
                z = true;
            }
        }
        return Boolean.valueOf(z);
    }
}
