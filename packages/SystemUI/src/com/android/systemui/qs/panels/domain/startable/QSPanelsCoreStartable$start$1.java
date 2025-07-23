package com.android.systemui.qs.panels.domain.startable;

import android.content.pm.UserInfo;
import com.android.systemui.qs.panels.data.repository.QSPreferencesRepository;
import com.android.systemui.qs.panels.domain.interactor.QSPreferencesInteractor;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class QSPanelsCoreStartable$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ QSPanelsCoreStartable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSPanelsCoreStartable$start$1(QSPanelsCoreStartable qSPanelsCoreStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qSPanelsCoreStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new QSPanelsCoreStartable$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSPanelsCoreStartable$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            QSPreferencesInteractor qSPreferencesInteractor = this.this$0.preferenceInteractor;
            this.label = 1;
            final QSPreferencesRepository qSPreferencesRepository = qSPreferencesInteractor.repo;
            Object collect = ((UserRepositoryImpl) qSPreferencesRepository.userRepository).selectedUserInfo.collect(new FlowCollector() { // from class: com.android.systemui.qs.panels.data.repository.QSPreferencesRepository$deleteLargeTileDataJob$2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    int i2 = ((UserInfo) obj2).id;
                    int i3 = QSPreferencesRepository.$r8$clinit;
                    ((UserFileManagerImpl) QSPreferencesRepository.this.userFileManager).getSharedPreferences$1(i2, "quick_settings_prefs").edit().remove("large_tiles_specs").remove("large_tiles_default").apply();
                    return Unit.INSTANCE;
                }
            }, this);
            if (collect != coroutineSingletons) {
                collect = Unit.INSTANCE;
            }
            if (collect != coroutineSingletons) {
                collect = Unit.INSTANCE;
            }
            if (collect == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
