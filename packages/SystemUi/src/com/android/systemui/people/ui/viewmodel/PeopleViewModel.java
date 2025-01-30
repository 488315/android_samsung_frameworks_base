package com.android.systemui.people.ui.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.util.Log;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.android.systemui.R;
import com.android.systemui.people.PeopleTileViewHelper;
import com.android.systemui.people.data.model.PeopleTileModel;
import com.android.systemui.people.data.repository.PeopleTileRepository;
import com.android.systemui.people.data.repository.PeopleTileRepositoryImpl;
import com.android.systemui.people.data.repository.PeopleWidgetRepository;
import com.android.systemui.people.widget.PeopleTileKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PeopleViewModel extends ViewModel {
    public final StateFlowImpl _appWidgetId;
    public final StateFlowImpl _priorityTiles;
    public final StateFlowImpl _recentTiles;
    public final StateFlowImpl _result;
    public final ReadonlyStateFlow appWidgetId;
    public final Context context;
    public final ReadonlyStateFlow priorityTiles;
    public final ReadonlyStateFlow recentTiles;
    public final ReadonlyStateFlow result;
    public final PeopleTileRepository tileRepository;
    public final PeopleWidgetRepository widgetRepository;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            if (!Intrinsics.areEqual(cls, PeopleViewModel.class)) {
                throw new IllegalStateException("Check failed.".toString());
            }
            return new PeopleViewModel(this.context, this.tileRepository, this.widgetRepository);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Result {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Cancelled extends Result {
            public static final Cancelled INSTANCE = new Cancelled();

            private Cancelled() {
                super(null);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    static {
        new Companion(null);
    }

    public PeopleViewModel(Context context, PeopleTileRepository peopleTileRepository, PeopleWidgetRepository peopleWidgetRepository) {
        this.context = context;
        this.tileRepository = peopleTileRepository;
        this.widgetRepository = peopleWidgetRepository;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(priorityTiles());
        this._priorityTiles = MutableStateFlow;
        this.priorityTiles = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(recentTiles());
        this._recentTiles = MutableStateFlow2;
        this.recentTiles = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(0);
        this._appWidgetId = MutableStateFlow3;
        this.appWidgetId = FlowKt.asStateFlow(MutableStateFlow3);
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(null);
        this._result = MutableStateFlow4;
        this.result = FlowKt.asStateFlow(MutableStateFlow4);
    }

    public final List priorityTiles() {
        try {
            List priorityTiles = ((PeopleTileRepositoryImpl) this.tileRepository).priorityTiles();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(priorityTiles, 10));
            Iterator it = priorityTiles.iterator();
            while (it.hasNext()) {
                arrayList.add(toViewModel((PeopleTileModel) it.next()));
            }
            return arrayList;
        } catch (Exception e) {
            Log.e("PeopleViewModel", "Couldn't retrieve priority conversations", e);
            return EmptyList.INSTANCE;
        }
    }

    public final List recentTiles() {
        try {
            List recentTiles = ((PeopleTileRepositoryImpl) this.tileRepository).recentTiles();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(recentTiles, 10));
            Iterator it = recentTiles.iterator();
            while (it.hasNext()) {
                arrayList.add(toViewModel((PeopleTileModel) it.next()));
            }
            return arrayList;
        } catch (Exception e) {
            Log.e("PeopleViewModel", "Couldn't retrieve recent conversations", e);
            return EmptyList.INSTANCE;
        }
    }

    public final PeopleTileViewModel toViewModel(PeopleTileModel peopleTileModel) {
        Context context = this.context;
        float f = context.getResources().getDisplayMetrics().density;
        Pattern pattern = PeopleTileViewHelper.DOUBLE_EXCLAMATION_PATTERN;
        int dimension = (int) (context.getResources().getDimension(R.dimen.avatar_size_for_medium) / f);
        boolean z = peopleTileModel.hasNewStory;
        Icon icon = peopleTileModel.userIcon;
        PeopleTileKey peopleTileKey = peopleTileModel.key;
        return new PeopleTileViewModel(peopleTileKey, PeopleTileViewHelper.getPersonIconBitmap(context, dimension, z, icon, peopleTileKey.mPackageName, peopleTileKey.mUserId, peopleTileModel.isImportant, peopleTileModel.isDndBlocking), peopleTileModel.username);
    }
}
