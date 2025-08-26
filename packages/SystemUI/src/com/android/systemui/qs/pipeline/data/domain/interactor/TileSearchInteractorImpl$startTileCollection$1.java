package com.android.systemui.qs.pipeline.data.domain.interactor;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.indexsearch.SystemUIIndexMediator;
import com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter;
import com.android.systemui.qs.pipeline.domain.model.TileModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
final class TileSearchInteractorImpl$startTileCollection$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TileSearchInteractorImpl this$0;

    /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.TileSearchInteractorImpl$startTileCollection$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ TileSearchInteractorImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(TileSearchInteractorImpl tileSearchInteractorImpl, Continuation continuation) {
            super(2, continuation);
            this.this$0 = tileSearchInteractorImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
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
                final TileSearchInteractorImpl tileSearchInteractorImpl = this.this$0;
                UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = ((UserRepositoryImpl) tileSearchInteractorImpl.userRepository).selectedUserInfo;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.pipeline.data.domain.interactor.TileSearchInteractorImpl.startTileCollection.1.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        UserInfo userInfo = (UserInfo) obj2;
                        TileSearchInteractorImpl tileSearchInteractorImpl2 = tileSearchInteractorImpl;
                        int i2 = tileSearchInteractorImpl2.currentUser;
                        int i3 = userInfo.id;
                        if (i2 != i3) {
                            tileSearchInteractorImpl2.currentUser = i3;
                            Context userContext = ((UserTrackerImpl) tileSearchInteractorImpl2.userTracker).getUserContext();
                            boolean z = userInfo.id == 0;
                            tileSearchInteractorImpl2.systemUIIndexMediator.getClass();
                            userContext.getPackageManager().setComponentEnabledSetting(new ComponentName("com.android.systemui", "com.android.systemui.indexsearch.SystemUIIndexProvider"), z ? 1 : 2, 1);
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (userRepositoryImpl$special$$inlined$map$2.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.TileSearchInteractorImpl$startTileCollection$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ TileSearchInteractorImpl this$0;

        /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.TileSearchInteractorImpl$startTileCollection$1$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ TileSearchInteractorImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(TileSearchInteractorImpl tileSearchInteractorImpl, Continuation continuation) {
                super(2, continuation);
                this.this$0 = tileSearchInteractorImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Resources.NotFoundException {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                List list = (List) this.L$0;
                ArrayList arrayList = new ArrayList();
                TileSearchInteractorImpl tileSearchInteractorImpl = this.this$0;
                ArrayList arrayList2 = new ArrayList();
                for (Object obj2 : list) {
                    TileSpec tileSpec = ((TileModel) obj2).spec;
                    String string = tileSearchInteractorImpl.context.getResources().getString(R.string.quick_settings_search_allow_list);
                    TilesSettingConverter.INSTANCE.getClass();
                    if (((ArrayList) TilesSettingConverter.toTilesList(string)).contains(tileSpec)) {
                        arrayList2.add(obj2);
                    }
                }
                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    Object obj3 = arrayList2.get(i);
                    i++;
                    TileModel tileModel = (TileModel) obj3;
                    arrayList.add((QSTileImpl) tileModel.tile);
                    arrayList3.add(new Integer(Log.d("TileSearchInteractor", "searchableTile " + tileModel.spec)));
                }
                TileModel barTileBySpecString = this.this$0.currentTileInteractor.getBarTileBySpecString();
                if (barTileBySpecString != null) {
                    arrayList.add((QSTileImpl) barTileBySpecString.tile);
                    Log.d("TileSearchInteractor", "searchableTile, added " + barTileBySpecString.spec);
                }
                SystemUIIndexMediator systemUIIndexMediator = this.this$0.systemUIIndexMediator;
                synchronized (systemUIIndexMediator.mTileSearchables) {
                    systemUIIndexMediator.mTileSearchables.clear();
                    systemUIIndexMediator.mTileSearchables.addAll(arrayList);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(TileSearchInteractorImpl tileSearchInteractorImpl, Continuation continuation) {
            super(2, continuation);
            this.this$0 = tileSearchInteractorImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                TileSearchInteractorImpl tileSearchInteractorImpl = this.this$0;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = tileSearchInteractorImpl.refreshSearchableTiles;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(tileSearchInteractorImpl, null);
                this.label = 1;
                if (FlowKt.collectLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, anonymousClass1, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.TileSearchInteractorImpl$startTileCollection$1$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ TileSearchInteractorImpl this$0;

        /* renamed from: com.android.systemui.qs.pipeline.data.domain.interactor.TileSearchInteractorImpl$startTileCollection$1$3$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ TileSearchInteractorImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(TileSearchInteractorImpl tileSearchInteractorImpl, Continuation continuation) {
                super(2, continuation);
                this.this$0 = tileSearchInteractorImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((Configuration) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                ArrayList arrayList;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Configuration configuration = (Configuration) this.L$0;
                TileSearchInteractorImpl tileSearchInteractorImpl = this.this$0;
                int i = tileSearchInteractorImpl.currentUiMode;
                int i2 = configuration.uiMode;
                if (i != i2) {
                    tileSearchInteractorImpl.currentUiMode = i2;
                    SystemUIIndexMediator systemUIIndexMediator = tileSearchInteractorImpl.systemUIIndexMediator;
                    synchronized (systemUIIndexMediator.mTileSearchables) {
                        arrayList = systemUIIndexMediator.mTileSearchables;
                    }
                    int size = arrayList.size();
                    int i3 = 0;
                    while (i3 < size) {
                        Object obj2 = arrayList.get(i3);
                        i3++;
                        ((QSTileImpl) obj2).saveTileIconAsImage();
                    }
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(TileSearchInteractorImpl tileSearchInteractorImpl, Continuation continuation) {
            super(2, continuation);
            this.this$0 = tileSearchInteractorImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                TileSearchInteractorImpl tileSearchInteractorImpl = this.this$0;
                Flow flow = ((ConfigurationInteractorImpl) tileSearchInteractorImpl.configurationInteractor).configurationValues;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(tileSearchInteractorImpl, null);
                this.label = 1;
                if (FlowKt.collectLatest(flow, anonymousClass1, this) == coroutineSingletons) {
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TileSearchInteractorImpl$startTileCollection$1(TileSearchInteractorImpl tileSearchInteractorImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = tileSearchInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TileSearchInteractorImpl$startTileCollection$1 tileSearchInteractorImpl$startTileCollection$1 = new TileSearchInteractorImpl$startTileCollection$1(this.this$0, continuation);
        tileSearchInteractorImpl$startTileCollection$1.L$0 = obj;
        return tileSearchInteractorImpl$startTileCollection$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TileSearchInteractorImpl$startTileCollection$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, null), 3);
        TileSearchInteractorImpl tileSearchInteractorImpl = this.this$0;
        BuildersKt.launch$default(coroutineScope, tileSearchInteractorImpl.backgroundDispatcher, null, new AnonymousClass2(tileSearchInteractorImpl, null), 2);
        TileSearchInteractorImpl tileSearchInteractorImpl2 = this.this$0;
        BuildersKt.launch$default(coroutineScope, tileSearchInteractorImpl2.backgroundDispatcher, null, new AnonymousClass3(tileSearchInteractorImpl2, null), 2);
        return Unit.INSTANCE;
    }
}
