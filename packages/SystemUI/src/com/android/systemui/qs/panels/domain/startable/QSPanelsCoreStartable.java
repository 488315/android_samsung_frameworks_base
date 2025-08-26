package com.android.systemui.qs.panels.domain.startable;

import android.content.pm.UserInfo;
import com.android.systemui.CoreStartable;
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
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public final class QSPanelsCoreStartable implements CoreStartable {
    public final CoroutineScope backgroundApplicationScope;
    public final QSPreferencesInteractor preferenceInteractor;

    /* renamed from: com.android.systemui.qs.panels.domain.startable.QSPanelsCoreStartable$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return QSPanelsCoreStartable.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                QSPreferencesInteractor qSPreferencesInteractor = QSPanelsCoreStartable.this.preferenceInteractor;
                this.label = 1;
                final QSPreferencesRepository qSPreferencesRepository = qSPreferencesInteractor.repo;
                Object objCollect = ((UserRepositoryImpl) qSPreferencesRepository.userRepository).selectedUserInfo.collect(new FlowCollector() { // from class: com.android.systemui.qs.panels.data.repository.QSPreferencesRepository$deleteLargeTileDataJob$2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        int i2 = ((UserInfo) obj2).id;
                        int i3 = QSPreferencesRepository.$r8$clinit;
                        ((UserFileManagerImpl) qSPreferencesRepository.userFileManager).getSharedPreferences$1(i2, "quick_settings_prefs").edit().remove("large_tiles_specs").remove("large_tiles_default").apply();
                        return Unit.INSTANCE;
                    }
                }, this);
                if (objCollect != coroutineSingletons) {
                    objCollect = Unit.INSTANCE;
                }
                if (objCollect != coroutineSingletons) {
                    objCollect = Unit.INSTANCE;
                }
                if (objCollect == coroutineSingletons) {
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

    public QSPanelsCoreStartable(QSPreferencesInteractor qSPreferencesInteractor, CoroutineScope coroutineScope) {
        this.preferenceInteractor = qSPreferencesInteractor;
        this.backgroundApplicationScope = coroutineScope;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BuildersKt.launch$default(this.backgroundApplicationScope, null, null, new AnonymousClass1(null), 3);
    }
}
