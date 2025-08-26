package com.android.systemui.qs.panels.ui.viewmodel;

import android.content.Context;
import androidx.compose.ui.text.AnnotatedString;
import com.android.systemui.common.shared.model.Text;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
final class EditModeViewModel$tiles$1$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ EditModeViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EditModeViewModel$tiles$1$2(EditModeViewModel editModeViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = editModeViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        EditModeViewModel$tiles$1$2 editModeViewModel$tiles$1$2 = new EditModeViewModel$tiles$1$2(this.this$0, (Continuation) obj3);
        editModeViewModel$tiles$1$2.L$0 = (List) obj;
        return editModeViewModel$tiles$1$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        EditModeViewModel editModeViewModel = this.this$0;
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            UnloadedEditTileViewModel unloadedEditTileViewModel = (UnloadedEditTileViewModel) list.get(i);
            Context context = editModeViewModel.context;
            Text text = unloadedEditTileViewModel.label;
            Text.Companion companion = Text.Companion;
            companion.getClass();
            String strLoadText = Text.Companion.loadText(text, context);
            AnnotatedString annotatedString = null;
            AnnotatedString annotatedString2 = strLoadText != null ? new AnnotatedString(strLoadText, null, 2, null) : null;
            if (annotatedString2 == null) {
                annotatedString2 = new AnnotatedString(unloadedEditTileViewModel.tileSpec.getSpec(), null, 2, null);
            }
            Text text2 = unloadedEditTileViewModel.appName;
            if (text2 != null) {
                companion.getClass();
                String strLoadText2 = Text.Companion.loadText(text2, context);
                if (strLoadText2 != null) {
                    annotatedString = new AnnotatedString(strLoadText2, null, 2, null);
                }
            }
            AnnotatedString annotatedString3 = annotatedString2;
            arrayList.add(new EditTileViewModel(unloadedEditTileViewModel.tileSpec, unloadedEditTileViewModel.icon, annotatedString3, annotatedString, unloadedEditTileViewModel.isCurrent, unloadedEditTileViewModel.availableEditActions, unloadedEditTileViewModel.category));
        }
        return arrayList;
    }
}
