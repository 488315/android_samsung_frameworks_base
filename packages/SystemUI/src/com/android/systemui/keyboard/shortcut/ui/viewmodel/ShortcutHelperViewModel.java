package com.android.systemui.keyboard.shortcut.ui.viewmodel;

import android.app.role.RoleManager;
import android.content.Context;
import com.android.systemui.keyboard.shortcut.domain.interactor.ShortcutHelperCategoriesInteractor;
import com.android.systemui.keyboard.shortcut.domain.interactor.ShortcutHelperCustomizationModeInteractor;
import com.android.systemui.keyboard.shortcut.domain.interactor.ShortcutHelperStateInteractor;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutsUiState;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ShortcutHelperViewModel {
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;
    public final ShortcutHelperCustomizationModeInteractor customizationModeInteractor;
    public final RoleManager roleManager;
    public final StateFlowImpl searchQuery;
    public final ReadonlyStateFlow shortcutsUiState;
    public final Flow shouldShow;
    public final ShortcutHelperStateInteractor stateInteractor;
    public final Context userContext;
    public final UserTracker userTracker;

    public ShortcutHelperViewModel(Context context, RoleManager roleManager, UserTracker userTracker, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, ShortcutHelperStateInteractor shortcutHelperStateInteractor, ShortcutHelperCategoriesInteractor shortcutHelperCategoriesInteractor, ShortcutHelperCustomizationModeInteractor shortcutHelperCustomizationModeInteractor) {
        this.context = context;
        this.roleManager = roleManager;
        this.userTracker = userTracker;
        this.backgroundDispatcher = coroutineDispatcher;
        this.stateInteractor = shortcutHelperStateInteractor;
        this.customizationModeInteractor = shortcutHelperCustomizationModeInteractor;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow("");
        this.searchQuery = MutableStateFlow;
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        this.userContext = userTrackerImpl.createCurrentUserContext(userTrackerImpl.getUserContext());
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = shortcutHelperCategoriesInteractor.shortcutCategories;
        this.shouldShow = FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.List r5 = (java.util.List) r5
                        java.util.Collection r5 = (java.util.Collection) r5
                        boolean r5 = r5.isEmpty()
                        r5 = r5 ^ r3
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineDispatcher);
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 combine = FlowKt.combine(MutableStateFlow, shortcutHelperCategoriesInteractor.shortcutCategories, shortcutHelperCustomizationModeInteractor.customizationMode, new ShortcutHelperViewModel$shortcutsUiState$1(this, null));
        SharingStarted.Companion.getClass();
        this.shortcutsUiState = FlowKt.stateIn(combine, coroutineScope, SharingStarted.Companion.Lazily, ShortcutsUiState.Inactive.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a4 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0080 -> B:10:0x0085). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$excludeLauncherApp(com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel r8, java.util.List r9, kotlin.coroutines.jvm.internal.ContinuationImpl r10) {
        /*
            r8.getClass()
            boolean r0 = r10 instanceof com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$excludeLauncherApp$1
            if (r0 == 0) goto L16
            r0 = r10
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$excludeLauncherApp$1 r0 = (com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$excludeLauncherApp$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$excludeLauncherApp$1 r0 = new com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$excludeLauncherApp$1
            r0.<init>(r8, r10)
        L1b:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L40
            if (r2 != r3) goto L38
            java.lang.Object r8 = r0.L$3
            java.lang.Object r9 = r0.L$2
            java.util.Iterator r9 = (java.util.Iterator) r9
            java.lang.Object r2 = r0.L$1
            java.util.List r2 = (java.util.List) r2
            java.lang.Object r4 = r0.L$0
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel r4 = (com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel) r4
            kotlin.ResultKt.throwOnFailure(r10)
            goto L85
        L38:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L40:
            kotlin.ResultKt.throwOnFailure(r10)
            r10 = r9
            java.lang.Iterable r10 = (java.lang.Iterable) r10
            java.util.Iterator r10 = r10.iterator()
            r7 = r10
            r10 = r9
            r9 = r7
        L4d:
            boolean r2 = r9.hasNext()
            r4 = 0
            if (r2 == 0) goto L99
            java.lang.Object r2 = r9.next()
            r5 = r2
            com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategory r5 = (com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategory) r5
            com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType r5 = r5.type
            boolean r6 = r5 instanceof com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType.CurrentApp
            if (r6 == 0) goto L95
            com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType$CurrentApp r5 = (com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType.CurrentApp) r5
            java.lang.String r5 = r5.packageName
            r0.L$0 = r8
            r0.L$1 = r10
            r0.L$2 = r9
            r0.L$3 = r2
            r0.label = r3
            r8.getClass()
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$isLauncherApp$2 r6 = new com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$isLauncherApp$2
            r6.<init>(r8, r5, r4)
            kotlinx.coroutines.CoroutineDispatcher r4 = r8.backgroundDispatcher
            java.lang.Object r4 = kotlinx.coroutines.BuildersKt.withContext(r4, r6, r0)
            if (r4 != r1) goto L80
            return r1
        L80:
            r7 = r4
            r4 = r8
            r8 = r2
            r2 = r10
            r10 = r7
        L85:
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            if (r10 == 0) goto L92
            r10 = r2
            r2 = r8
            r8 = r4
            r4 = r3
            goto L96
        L92:
            r10 = r2
            r2 = r8
            r8 = r4
        L95:
            r4 = 0
        L96:
            if (r4 == 0) goto L4d
            r4 = r2
        L99:
            com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategory r4 = (com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategory) r4
            if (r4 == 0) goto La4
            java.lang.Iterable r10 = (java.lang.Iterable) r10
            java.util.List r8 = kotlin.collections.CollectionsKt___CollectionsKt.minus(r10, r4)
            return r8
        La4:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel.access$excludeLauncherApp(com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel, java.util.List, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0096, code lost:
    
        if (r5 != false) goto L31;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0080 -> B:10:0x0085). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$getDefaultSelectedCategory(com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel r8, java.util.List r9, kotlin.coroutines.jvm.internal.ContinuationImpl r10) {
        /*
            r8.getClass()
            boolean r0 = r10 instanceof com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$getDefaultSelectedCategory$1
            if (r0 == 0) goto L16
            r0 = r10
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$getDefaultSelectedCategory$1 r0 = (com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$getDefaultSelectedCategory$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$getDefaultSelectedCategory$1 r0 = new com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$getDefaultSelectedCategory$1
            r0.<init>(r8, r10)
        L1b:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L41
            if (r2 != r4) goto L39
            java.lang.Object r8 = r0.L$3
            java.lang.Object r9 = r0.L$2
            java.util.Iterator r9 = (java.util.Iterator) r9
            java.lang.Object r2 = r0.L$1
            java.util.List r2 = (java.util.List) r2
            java.lang.Object r5 = r0.L$0
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel r5 = (com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel) r5
            kotlin.ResultKt.throwOnFailure(r10)
            goto L85
        L39:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L41:
            kotlin.ResultKt.throwOnFailure(r10)
            r10 = r9
            java.lang.Iterable r10 = (java.lang.Iterable) r10
            java.util.Iterator r10 = r10.iterator()
            r7 = r10
            r10 = r9
            r9 = r7
        L4e:
            boolean r2 = r9.hasNext()
            if (r2 == 0) goto L99
            java.lang.Object r2 = r9.next()
            r5 = r2
            com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategory r5 = (com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategory) r5
            com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType r5 = r5.type
            boolean r6 = r5 instanceof com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType.CurrentApp
            if (r6 == 0) goto L95
            com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType$CurrentApp r5 = (com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType.CurrentApp) r5
            java.lang.String r5 = r5.packageName
            r0.L$0 = r8
            r0.L$1 = r10
            r0.L$2 = r9
            r0.L$3 = r2
            r0.label = r4
            r8.getClass()
            com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$isLauncherApp$2 r6 = new com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$isLauncherApp$2
            r6.<init>(r8, r5, r3)
            kotlinx.coroutines.CoroutineDispatcher r5 = r8.backgroundDispatcher
            java.lang.Object r5 = kotlinx.coroutines.BuildersKt.withContext(r5, r6, r0)
            if (r5 != r1) goto L80
            return r1
        L80:
            r7 = r5
            r5 = r8
            r8 = r2
            r2 = r10
            r10 = r7
        L85:
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r10 = r10.booleanValue()
            if (r10 != 0) goto L92
            r10 = r2
            r2 = r8
            r8 = r5
            r5 = r4
            goto L96
        L92:
            r10 = r2
            r2 = r8
            r8 = r5
        L95:
            r5 = 0
        L96:
            if (r5 == 0) goto L4e
            goto L9a
        L99:
            r2 = r3
        L9a:
            com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategory r2 = (com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategory) r2
            if (r2 == 0) goto La4
            com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType r8 = r2.type
            if (r8 != 0) goto La3
            goto La4
        La3:
            return r8
        La4:
            java.lang.Object r8 = kotlin.collections.CollectionsKt___CollectionsKt.firstOrNull(r10)
            com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategory r8 = (com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategory) r8
            if (r8 == 0) goto Laf
            com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType r8 = r8.type
            return r8
        Laf:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel.access$getDefaultSelectedCategory(com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel, java.util.List, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
