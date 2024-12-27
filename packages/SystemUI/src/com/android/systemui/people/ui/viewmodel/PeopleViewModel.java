package com.android.systemui.people.ui.viewmodel;

import android.content.Context;
import android.content.Intent;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.android.systemui.people.data.repository.PeopleTileRepository;
import com.android.systemui.people.data.repository.PeopleWidgetRepository;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PeopleViewModel extends ViewModel {
    public final Function0 clearResult;
    public final Function1 onTileClicked;
    public final Function0 onTileRefreshRequested;
    public final Function0 onUserJourneyCancelled;
    public final Function1 onWidgetIdChanged;
    public final StateFlow priorityTiles;
    public final StateFlow recentTiles;
    public final StateFlow result;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Factory implements ViewModelProvider.Factory {
        public final Context context;
        public final PeopleTileRepository tileRepository;
        public final PeopleWidgetRepository widgetRepository;

        public Factory(Context context, PeopleTileRepository peopleTileRepository, PeopleWidgetRepository peopleWidgetRepository) {
            this.context = context;
            this.tileRepository = peopleTileRepository;
            this.widgetRepository = peopleWidgetRepository;
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public final ViewModel create(Class cls) {
            if (!cls.equals(PeopleViewModel.class)) {
                throw new IllegalStateException("Check failed.".toString());
            }
            Context context = this.context;
            PeopleTileRepository peopleTileRepository = this.tileRepository;
            StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(PeopleViewModelKt.PeopleViewModel$priorityTiles(peopleTileRepository, context));
            StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(PeopleViewModelKt.PeopleViewModel$recentTiles(peopleTileRepository, context));
            StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(0);
            StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(null);
            return new PeopleViewModel(FlowKt.asStateFlow(MutableStateFlow), FlowKt.asStateFlow(MutableStateFlow2), FlowKt.asStateFlow(MutableStateFlow3), FlowKt.asStateFlow(MutableStateFlow4), new PeopleViewModelKt$PeopleViewModel$1(MutableStateFlow, MutableStateFlow2, peopleTileRepository, context), new PeopleViewModelKt$PeopleViewModel$2(MutableStateFlow3), new PeopleViewModelKt$PeopleViewModel$3(MutableStateFlow4), new PeopleViewModelKt$PeopleViewModel$4(MutableStateFlow3, this.widgetRepository, MutableStateFlow4), new PeopleViewModelKt$PeopleViewModel$5(MutableStateFlow4));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class Result {

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Cancelled extends Result {
            public static final Cancelled INSTANCE = new Cancelled();

            private Cancelled() {
                super(null);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Success extends Result {
            public final Intent data;

            public Success(Intent intent) {
                super(null);
                this.data = intent;
            }
        }

        private Result() {
        }

        public /* synthetic */ Result(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public PeopleViewModel(StateFlow stateFlow, StateFlow stateFlow2, StateFlow stateFlow3, StateFlow stateFlow4, Function0 function0, Function1 function1, Function0 function02, Function1 function12, Function0 function03) {
        this.priorityTiles = stateFlow;
        this.recentTiles = stateFlow2;
        this.result = stateFlow4;
        this.onTileRefreshRequested = function0;
        this.onWidgetIdChanged = function1;
        this.clearResult = function02;
        this.onTileClicked = function12;
        this.onUserJourneyCancelled = function03;
    }
}
