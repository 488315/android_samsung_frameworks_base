package com.android.systemui.bouncer.ui.viewmodel;

import com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.user.ui.viewmodel.UserActionViewModel;
import com.android.systemui.user.ui.viewmodel.UserViewModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class BouncerViewModel$userSwitcherDropdown$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public BouncerViewModel$userSwitcherDropdown$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BouncerViewModel$userSwitcherDropdown$1 bouncerViewModel$userSwitcherDropdown$1 = new BouncerViewModel$userSwitcherDropdown$1((Continuation) obj3);
        bouncerViewModel$userSwitcherDropdown$1.L$0 = (List) obj;
        bouncerViewModel$userSwitcherDropdown$1.L$1 = (List) obj2;
        return bouncerViewModel$userSwitcherDropdown$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        List list2 = (List) this.L$1;
        List<UserViewModel> list3 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
        for (UserViewModel userViewModel : list3) {
            Icon.Loaded loaded = new Icon.Loaded(userViewModel.image, null);
            Function0 function0 = userViewModel.onClicked;
            if (function0 == null) {
                function0 = new Function0() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerViewModel$userSwitcherDropdown$1$1$1
                    @Override // kotlin.jvm.functions.Function0
                    public final /* bridge */ /* synthetic */ Object invoke() {
                        return Unit.INSTANCE;
                    }
                };
            }
            arrayList.add(new BouncerViewModel.UserSwitcherDropdownItemViewModel(loaded, userViewModel.name, function0));
        }
        List<UserActionViewModel> list4 = list2;
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
        for (UserActionViewModel userActionViewModel : list4) {
            arrayList2.add(new BouncerViewModel.UserSwitcherDropdownItemViewModel(new Icon.Resource(userActionViewModel.iconResourceId, null), new Text.Resource(userActionViewModel.textResourceId), userActionViewModel.onClicked));
        }
        return CollectionsKt___CollectionsKt.plus((Iterable) arrayList2, (Collection) arrayList);
    }
}
