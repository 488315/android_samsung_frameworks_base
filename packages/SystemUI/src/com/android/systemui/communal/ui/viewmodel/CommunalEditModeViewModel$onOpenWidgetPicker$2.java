package com.android.systemui.communal.ui.viewmodel;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Parcelable;
import android.util.Log;
import androidx.activity.result.ActivityResultLauncher;
import com.android.systemui.Flags;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;

final class CommunalEditModeViewModel$onOpenWidgetPicker$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ ActivityResultLauncher $activityLauncher;
    final /* synthetic */ PackageManager $packageManager;
    final /* synthetic */ Resources $resources;
    int label;
    final /* synthetic */ CommunalEditModeViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalEditModeViewModel$onOpenWidgetPicker$2(CommunalEditModeViewModel communalEditModeViewModel, Resources resources, PackageManager packageManager, ActivityResultLauncher activityResultLauncher, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalEditModeViewModel;
        this.$resources = resources;
        this.$packageManager = packageManager;
        this.$activityLauncher = activityResultLauncher;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalEditModeViewModel$onOpenWidgetPicker$2(this.this$0, this.$resources, this.$packageManager, this.$activityLauncher, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalEditModeViewModel$onOpenWidgetPicker$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ActivityInfo activityInfo;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 = this.this$0.communalInteractor.widgetContent;
            this.label = 1;
            obj = FlowKt.first(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : (List) obj) {
            if (obj2 instanceof CommunalContentModel.WidgetContent.Widget) {
                arrayList.add(obj2);
            }
        }
        ArrayList<? extends Parcelable> arrayList2 = new ArrayList<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(((CommunalContentModel.WidgetContent.Widget) it.next()).providerInfo);
        }
        CommunalEditModeViewModel communalEditModeViewModel = this.this$0;
        PackageManager packageManager = this.$packageManager;
        int i2 = CommunalEditModeViewModel.$r8$clinit;
        communalEditModeViewModel.getClass();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 65536);
        Intent intent2 = null;
        String str = (resolveActivity == null || (activityInfo = resolveActivity.activityInfo) == null) ? null : activityInfo.packageName;
        if (str == null) {
            Log.e("CommunalEditModeViewModel", "Couldn't resolve launcher package name");
        } else {
            intent2 = new Intent("android.intent.action.PICK");
            intent2.setPackage(str);
            Flags.FEATURE_FLAGS.getClass();
            intent2.putExtra("categoryFilter", ((Number) communalEditModeViewModel.communalSettingsInteractor.communalWidgetCategories.$$delegate_0.getValue()).intValue());
            intent2.putExtra("ui_surface", "widgets_hub");
            intent2.putParcelableArrayListExtra("added_app_widgets", arrayList2);
        }
        if (intent2 != null) {
            try {
                this.$activityLauncher.launch(intent2);
                return Boolean.TRUE;
            } catch (Exception e) {
                new Integer(Log.e("CommunalEditModeViewModel", "Failed to launch widget picker activity", e));
            }
        }
        return Boolean.FALSE;
    }
}
