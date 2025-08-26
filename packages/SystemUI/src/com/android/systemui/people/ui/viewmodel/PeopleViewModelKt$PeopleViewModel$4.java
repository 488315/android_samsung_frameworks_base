package com.android.systemui.people.ui.viewmodel;

import android.content.Intent;
import com.android.systemui.people.data.repository.PeopleWidgetRepository;
import com.android.systemui.people.data.repository.PeopleWidgetRepositoryImpl;
import com.android.systemui.people.ui.viewmodel.PeopleViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.MutableStateFlow;

/* loaded from: classes2.dex */
final /* synthetic */ class PeopleViewModelKt$PeopleViewModel$4 extends FunctionReferenceImpl implements Function1 {
    final /* synthetic */ MutableStateFlow $appWidgetId;
    final /* synthetic */ MutableStateFlow $result;
    final /* synthetic */ PeopleWidgetRepository $widgetRepository;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PeopleViewModelKt$PeopleViewModel$4(MutableStateFlow mutableStateFlow, PeopleWidgetRepository peopleWidgetRepository, MutableStateFlow mutableStateFlow2) {
        super(1, Intrinsics.Kotlin.class, "onTileClicked", "PeopleViewModel$onTileClicked(Lkotlinx/coroutines/flow/MutableStateFlow;Lcom/android/systemui/people/data/repository/PeopleWidgetRepository;Lkotlinx/coroutines/flow/MutableStateFlow;Lcom/android/systemui/people/ui/viewmodel/PeopleTileViewModel;)V", 0);
        this.$appWidgetId = mutableStateFlow;
        this.$widgetRepository = peopleWidgetRepository;
        this.$result = mutableStateFlow2;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        MutableStateFlow mutableStateFlow = this.$appWidgetId;
        PeopleWidgetRepository peopleWidgetRepository = this.$widgetRepository;
        MutableStateFlow mutableStateFlow2 = this.$result;
        ((PeopleWidgetRepositoryImpl) peopleWidgetRepository).peopleSpaceWidgetManager.addNewWidget(((Number) mutableStateFlow.getValue()).intValue(), ((PeopleTileViewModel) obj).key);
        Intent intent = new Intent();
        intent.putExtra("appWidgetId", ((Number) mutableStateFlow.getValue()).intValue());
        mutableStateFlow2.setValue(new PeopleViewModel.Result.Success(intent));
        return Unit.INSTANCE;
    }
}
