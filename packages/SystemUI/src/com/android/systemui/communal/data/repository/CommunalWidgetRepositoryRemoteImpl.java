package com.android.systemui.communal.data.repository;

import android.content.ComponentName;
import android.os.UserHandle;
import com.android.systemui.communal.shared.model.GlanceableHubMultiUserHelper;
import com.android.systemui.communal.widgets.GlanceableHubWidgetManager;
import com.android.systemui.communal.widgets.GlanceableHubWidgetManager$$ExternalSyntheticLambda1;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class CommunalWidgetRepositoryRemoteImpl implements CommunalWidgetRepository {
    public final CoroutineScope bgScope;
    public final Flow communalWidgets;
    public final GlanceableHubWidgetManager glanceableHubWidgetManager;

    /* renamed from: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryRemoteImpl$addWidget$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ WidgetConfigurator $configurator;
        final /* synthetic */ ComponentName $provider;
        final /* synthetic */ Integer $rank;
        final /* synthetic */ UserHandle $user;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ComponentName componentName, UserHandle userHandle, Integer num, WidgetConfigurator widgetConfigurator, Continuation continuation) {
            super(2, continuation);
            this.$provider = componentName;
            this.$user = userHandle;
            this.$rank = num;
            this.$configurator = widgetConfigurator;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalWidgetRepositoryRemoteImpl.this.new AnonymousClass1(this.$provider, this.$user, this.$rank, this.$configurator, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            final GlanceableHubWidgetManager glanceableHubWidgetManager = CommunalWidgetRepositoryRemoteImpl.this.glanceableHubWidgetManager;
            final ComponentName componentName = this.$provider;
            final UserHandle userHandle = this.$user;
            final Integer num = this.$rank;
            final WidgetConfigurator widgetConfigurator = this.$configurator;
            glanceableHubWidgetManager.getClass();
            glanceableHubWidgetManager.runOnService(new Function1() { // from class: com.android.systemui.communal.widgets.GlanceableHubWidgetManager$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    ComponentName componentName2 = componentName;
                    UserHandle userHandle2 = userHandle;
                    IGlanceableHubWidgetManagerService iGlanceableHubWidgetManagerService = (IGlanceableHubWidgetManagerService) obj2;
                    int i = GlanceableHubWidgetManager.$r8$clinit;
                    Integer num2 = num;
                    int iIntValue = num2 != null ? num2.intValue() : -1;
                    GlanceableHubWidgetManager glanceableHubWidgetManager2 = glanceableHubWidgetManager;
                    glanceableHubWidgetManager2.getClass();
                    WidgetConfigurator widgetConfigurator2 = widgetConfigurator;
                    iGlanceableHubWidgetManagerService.addWidget(componentName2, userHandle2, iIntValue, widgetConfigurator2 != null ? new GlanceableHubWidgetManager$createIConfigureWidgetCallback$1$1(glanceableHubWidgetManager2, widgetConfigurator2) : null);
                    return Unit.INSTANCE;
                }
            });
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryRemoteImpl$deleteWidget$1, reason: invalid class name and case insensitive filesystem */
    final class C08301 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $widgetId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08301(int i, Continuation continuation) {
            super(2, continuation);
            this.$widgetId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalWidgetRepositoryRemoteImpl.this.new C08301(this.$widgetId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08301) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            GlanceableHubWidgetManager glanceableHubWidgetManager = CommunalWidgetRepositoryRemoteImpl.this.glanceableHubWidgetManager;
            final int i = this.$widgetId;
            glanceableHubWidgetManager.getClass();
            glanceableHubWidgetManager.runOnService(new Function1() { // from class: com.android.systemui.communal.widgets.GlanceableHubWidgetManager$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    int i2 = GlanceableHubWidgetManager.$r8$clinit;
                    ((IGlanceableHubWidgetManagerService) obj2).deleteWidget(i);
                    return Unit.INSTANCE;
                }
            });
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryRemoteImpl$resizeWidget$1, reason: invalid class name and case insensitive filesystem */
    final class C08311 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $appWidgetId;
        final /* synthetic */ int $spanY;
        final /* synthetic */ Map<Integer, Integer> $widgetIdToRankMap;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08311(int i, int i2, Map<Integer, Integer> map, Continuation continuation) {
            super(2, continuation);
            this.$appWidgetId = i;
            this.$spanY = i2;
            this.$widgetIdToRankMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalWidgetRepositoryRemoteImpl.this.new C08311(this.$appWidgetId, this.$spanY, this.$widgetIdToRankMap, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08311) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            GlanceableHubWidgetManager glanceableHubWidgetManager = CommunalWidgetRepositoryRemoteImpl.this.glanceableHubWidgetManager;
            final int i = this.$appWidgetId;
            final int i2 = this.$spanY;
            final Map<Integer, Integer> map = this.$widgetIdToRankMap;
            glanceableHubWidgetManager.getClass();
            glanceableHubWidgetManager.runOnService(new Function1() { // from class: com.android.systemui.communal.widgets.GlanceableHubWidgetManager$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    Map map2 = map;
                    int i3 = GlanceableHubWidgetManager.$r8$clinit;
                    ((IGlanceableHubWidgetManagerService) obj2).resizeWidget(i, i2, CollectionsKt___CollectionsKt.toIntArray(map2.keySet()), CollectionsKt___CollectionsKt.toIntArray(map2.values()));
                    return Unit.INSTANCE;
                }
            });
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryRemoteImpl$updateWidgetOrder$1, reason: invalid class name and case insensitive filesystem */
    final class C08321 extends SuspendLambda implements Function2 {
        final /* synthetic */ Map<Integer, Integer> $widgetIdToRankMap;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08321(Map<Integer, Integer> map, Continuation continuation) {
            super(2, continuation);
            this.$widgetIdToRankMap = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalWidgetRepositoryRemoteImpl.this.new C08321(this.$widgetIdToRankMap, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08321) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            GlanceableHubWidgetManager glanceableHubWidgetManager = CommunalWidgetRepositoryRemoteImpl.this.glanceableHubWidgetManager;
            Map<Integer, Integer> map = this.$widgetIdToRankMap;
            glanceableHubWidgetManager.getClass();
            glanceableHubWidgetManager.runOnService(new GlanceableHubWidgetManager$$ExternalSyntheticLambda1(map, 0));
            return Unit.INSTANCE;
        }
    }

    public CommunalWidgetRepositoryRemoteImpl(CoroutineScope coroutineScope, GlanceableHubWidgetManager glanceableHubWidgetManager, GlanceableHubMultiUserHelper glanceableHubMultiUserHelper) {
        this.bgScope = coroutineScope;
        this.glanceableHubWidgetManager = glanceableHubWidgetManager;
        glanceableHubMultiUserHelper.getClass();
        this.communalWidgets = glanceableHubWidgetManager.widgets;
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void abortRestoreWidgets() {
        throw new IllegalStateException("Restore widgets should be performed on a foreground user");
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void addWidget(ComponentName componentName, UserHandle userHandle, Integer num, WidgetConfigurator widgetConfigurator) {
        BuildersKt.launch$default(this.bgScope, null, null, new AnonymousClass1(componentName, userHandle, num, widgetConfigurator, null), 3);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void deleteWidget(int i) {
        BuildersKt.launch$default(this.bgScope, null, null, new C08301(i, null), 3);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final Flow getCommunalWidgets() {
        return this.communalWidgets;
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void resizeWidget(Map map, int i, int i2) {
        BuildersKt.launch$default(this.bgScope, null, null, new C08311(i, i2, map, null), 3);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void restoreWidgets(Map map) {
        throw new IllegalStateException("Restore widgets should be performed on a foreground user");
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void updateWidgetOrder(Map map) {
        BuildersKt.launch$default(this.bgScope, null, null, new C08321(map, null), 3);
    }
}
