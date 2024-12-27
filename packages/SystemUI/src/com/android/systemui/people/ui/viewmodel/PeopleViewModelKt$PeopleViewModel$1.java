package com.android.systemui.people.ui.viewmodel;

import android.content.Context;
import com.android.systemui.people.data.repository.PeopleTileRepository;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

final /* synthetic */ class PeopleViewModelKt$PeopleViewModel$1 extends FunctionReferenceImpl implements Function0 {
    final /* synthetic */ Context $context;
    final /* synthetic */ MutableStateFlow $priorityTiles;
    final /* synthetic */ MutableStateFlow $recentTiles;
    final /* synthetic */ PeopleTileRepository $tileRepository;

    public PeopleViewModelKt$PeopleViewModel$1(MutableStateFlow mutableStateFlow, MutableStateFlow mutableStateFlow2, PeopleTileRepository peopleTileRepository, Context context) {
        super(0, Intrinsics.Kotlin.class, "onTileRefreshRequested", "PeopleViewModel$onTileRefreshRequested(Lkotlinx/coroutines/flow/MutableStateFlow;Lkotlinx/coroutines/flow/MutableStateFlow;Lcom/android/systemui/people/data/repository/PeopleTileRepository;Landroid/content/Context;)V", 0);
        this.$priorityTiles = mutableStateFlow;
        this.$recentTiles = mutableStateFlow2;
        this.$tileRepository = peopleTileRepository;
        this.$context = context;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        MutableStateFlow mutableStateFlow = this.$priorityTiles;
        MutableStateFlow mutableStateFlow2 = this.$recentTiles;
        PeopleTileRepository peopleTileRepository = this.$tileRepository;
        Context context = this.$context;
        ((StateFlowImpl) mutableStateFlow).setValue(PeopleViewModelKt.PeopleViewModel$priorityTiles(peopleTileRepository, context));
        ((StateFlowImpl) mutableStateFlow2).setValue(PeopleViewModelKt.PeopleViewModel$recentTiles(peopleTileRepository, context));
        return Unit.INSTANCE;
    }
}
