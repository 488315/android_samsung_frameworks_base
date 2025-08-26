package com.android.systemui.keyboard.shortcut.ui.viewmodel;

import android.app.role.RoleManager;
import android.content.Context;
import com.android.systemui.keyboard.shortcut.domain.interactor.ShortcutHelperCategoriesInteractor;
import com.android.systemui.keyboard.shortcut.domain.interactor.ShortcutHelperCustomizationModeInteractor;
import com.android.systemui.keyboard.shortcut.domain.interactor.ShortcutHelperStateInteractor;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategory;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutsUiState;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
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
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow("");
        this.searchQuery = stateFlowImplMutableStateFlow;
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        this.userContext = userTrackerImpl.createCurrentUserContext(userTrackerImpl.getUserContext());
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = shortcutHelperCategoriesInteractor.shortcutCategories;
        this.shouldShow = FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(!((List) obj).isEmpty());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), coroutineDispatcher);
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine = FlowKt.combine(stateFlowImplMutableStateFlow, shortcutHelperCategoriesInteractor.shortcutCategories, shortcutHelperCustomizationModeInteractor.customizationMode, new ShortcutHelperViewModel$shortcutsUiState$1(this, null));
        SharingStarted.Companion.getClass();
        this.shortcutsUiState = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine, coroutineScope, SharingStarted.Companion.Lazily, ShortcutsUiState.Inactive.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004d A[PHI: r8 r9 r10
      0x004d: PHI (r8v2 com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel) = 
      (r8v0 com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel)
      (r8v4 com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel)
     binds: [B:14:0x0040, B:28:0x0096] A[DONT_GENERATE, DONT_INLINE]
      0x004d: PHI (r9v2 java.util.Iterator) = (r9v1 java.util.Iterator), (r9v3 java.util.Iterator) binds: [B:14:0x0040, B:28:0x0096] A[DONT_GENERATE, DONT_INLINE]
      0x004d: PHI (r10v8 java.util.List) = (r10v5 java.util.List), (r10v10 java.util.List) binds: [B:14:0x0040, B:28:0x0096] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a4 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0080 -> B:23:0x0085). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$excludeLauncherApp(ShortcutHelperViewModel shortcutHelperViewModel, List list, ContinuationImpl continuationImpl) throws Throwable {
        ShortcutHelperViewModel$excludeLauncherApp$1 shortcutHelperViewModel$excludeLauncherApp$1;
        List list2;
        Iterator it;
        ShortcutCategory shortcutCategory;
        ?? next;
        boolean z;
        shortcutHelperViewModel.getClass();
        if (continuationImpl instanceof ShortcutHelperViewModel$excludeLauncherApp$1) {
            shortcutHelperViewModel$excludeLauncherApp$1 = (ShortcutHelperViewModel$excludeLauncherApp$1) continuationImpl;
            int i = shortcutHelperViewModel$excludeLauncherApp$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                shortcutHelperViewModel$excludeLauncherApp$1.label = i - Integer.MIN_VALUE;
            } else {
                shortcutHelperViewModel$excludeLauncherApp$1 = new ShortcutHelperViewModel$excludeLauncherApp$1(shortcutHelperViewModel, continuationImpl);
            }
        }
        Object obj = shortcutHelperViewModel$excludeLauncherApp$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = shortcutHelperViewModel$excludeLauncherApp$1.label;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Object obj2 = shortcutHelperViewModel$excludeLauncherApp$1.L$3;
            it = (Iterator) shortcutHelperViewModel$excludeLauncherApp$1.L$2;
            List list3 = (List) shortcutHelperViewModel$excludeLauncherApp$1.L$1;
            ShortcutHelperViewModel shortcutHelperViewModel2 = (ShortcutHelperViewModel) shortcutHelperViewModel$excludeLauncherApp$1.L$0;
            ResultKt.throwOnFailure(obj);
            if (((Boolean) obj).booleanValue()) {
                list2 = list3;
                next = obj2;
                shortcutHelperViewModel = shortcutHelperViewModel2;
                z = false;
            } else {
                list2 = list3;
                next = obj2;
                shortcutHelperViewModel = shortcutHelperViewModel2;
                z = true;
            }
            if (z) {
                shortcutCategory = next;
            } else {
                shortcutCategory = null;
                if (it.hasNext()) {
                    next = it.next();
                    ShortcutCategoryType shortcutCategoryType = ((ShortcutCategory) next).type;
                    if (shortcutCategoryType instanceof ShortcutCategoryType.CurrentApp) {
                        String str = ((ShortcutCategoryType.CurrentApp) shortcutCategoryType).packageName;
                        shortcutHelperViewModel$excludeLauncherApp$1.L$0 = shortcutHelperViewModel;
                        shortcutHelperViewModel$excludeLauncherApp$1.L$1 = list2;
                        shortcutHelperViewModel$excludeLauncherApp$1.L$2 = it;
                        shortcutHelperViewModel$excludeLauncherApp$1.L$3 = next;
                        shortcutHelperViewModel$excludeLauncherApp$1.label = 1;
                        shortcutHelperViewModel.getClass();
                        Object objWithContext = BuildersKt.withContext(shortcutHelperViewModel.backgroundDispatcher, new ShortcutHelperViewModel$isLauncherApp$2(shortcutHelperViewModel, str, null), shortcutHelperViewModel$excludeLauncherApp$1);
                        if (objWithContext == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        shortcutHelperViewModel2 = shortcutHelperViewModel;
                        obj2 = next;
                        list3 = list2;
                        obj = objWithContext;
                        if (((Boolean) obj).booleanValue()) {
                        }
                        if (z) {
                        }
                    } else {
                        z = false;
                        if (z) {
                        }
                    }
                }
            }
            ShortcutCategory shortcutCategory2 = shortcutCategory;
            return shortcutCategory2 == null ? CollectionsKt___CollectionsKt.minus(list2, shortcutCategory2) : list2;
        }
        ResultKt.throwOnFailure(obj);
        list2 = list;
        it = list.iterator();
        shortcutCategory = null;
        if (it.hasNext()) {
        }
        ShortcutCategory shortcutCategory22 = shortcutCategory;
        if (shortcutCategory22 == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x004e A[PHI: r8 r9 r10
      0x004e: PHI (r8v5 com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel) = 
      (r8v0 com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel)
      (r8v7 com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel)
     binds: [B:14:0x0041, B:28:0x0096] A[DONT_GENERATE, DONT_INLINE]
      0x004e: PHI (r9v2 java.util.Iterator) = (r9v1 java.util.Iterator), (r9v3 java.util.Iterator) binds: [B:14:0x0041, B:28:0x0096] A[DONT_GENERATE, DONT_INLINE]
      0x004e: PHI (r10v7 java.util.List) = (r10v5 java.util.List), (r10v9 java.util.List) binds: [B:14:0x0041, B:28:0x0096] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:22:0x0080 -> B:23:0x0085). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$getDefaultSelectedCategory(ShortcutHelperViewModel shortcutHelperViewModel, List list, ContinuationImpl continuationImpl) throws Throwable {
        ShortcutHelperViewModel$getDefaultSelectedCategory$1 shortcutHelperViewModel$getDefaultSelectedCategory$1;
        List list2;
        Iterator it;
        Object next;
        ShortcutCategoryType shortcutCategoryType;
        boolean z;
        shortcutHelperViewModel.getClass();
        if (continuationImpl instanceof ShortcutHelperViewModel$getDefaultSelectedCategory$1) {
            shortcutHelperViewModel$getDefaultSelectedCategory$1 = (ShortcutHelperViewModel$getDefaultSelectedCategory$1) continuationImpl;
            int i = shortcutHelperViewModel$getDefaultSelectedCategory$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                shortcutHelperViewModel$getDefaultSelectedCategory$1.label = i - Integer.MIN_VALUE;
            } else {
                shortcutHelperViewModel$getDefaultSelectedCategory$1 = new ShortcutHelperViewModel$getDefaultSelectedCategory$1(shortcutHelperViewModel, continuationImpl);
            }
        }
        Object obj = shortcutHelperViewModel$getDefaultSelectedCategory$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = shortcutHelperViewModel$getDefaultSelectedCategory$1.label;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Object obj2 = shortcutHelperViewModel$getDefaultSelectedCategory$1.L$3;
            it = (Iterator) shortcutHelperViewModel$getDefaultSelectedCategory$1.L$2;
            List list3 = (List) shortcutHelperViewModel$getDefaultSelectedCategory$1.L$1;
            ShortcutHelperViewModel shortcutHelperViewModel2 = (ShortcutHelperViewModel) shortcutHelperViewModel$getDefaultSelectedCategory$1.L$0;
            ResultKt.throwOnFailure(obj);
            if (((Boolean) obj).booleanValue()) {
                list2 = list3;
                next = obj2;
                shortcutHelperViewModel = shortcutHelperViewModel2;
                z = true;
            } else {
                list2 = list3;
                next = obj2;
                shortcutHelperViewModel = shortcutHelperViewModel2;
                z = false;
            }
            if (!z) {
                if (it.hasNext()) {
                    next = null;
                } else {
                    next = it.next();
                    ShortcutCategoryType shortcutCategoryType2 = ((ShortcutCategory) next).type;
                    if (shortcutCategoryType2 instanceof ShortcutCategoryType.CurrentApp) {
                        String str = ((ShortcutCategoryType.CurrentApp) shortcutCategoryType2).packageName;
                        shortcutHelperViewModel$getDefaultSelectedCategory$1.L$0 = shortcutHelperViewModel;
                        shortcutHelperViewModel$getDefaultSelectedCategory$1.L$1 = list2;
                        shortcutHelperViewModel$getDefaultSelectedCategory$1.L$2 = it;
                        shortcutHelperViewModel$getDefaultSelectedCategory$1.L$3 = next;
                        shortcutHelperViewModel$getDefaultSelectedCategory$1.label = 1;
                        shortcutHelperViewModel.getClass();
                        Object objWithContext = BuildersKt.withContext(shortcutHelperViewModel.backgroundDispatcher, new ShortcutHelperViewModel$isLauncherApp$2(shortcutHelperViewModel, str, null), shortcutHelperViewModel$getDefaultSelectedCategory$1);
                        if (objWithContext == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        shortcutHelperViewModel2 = shortcutHelperViewModel;
                        obj2 = next;
                        list3 = list2;
                        obj = objWithContext;
                        if (((Boolean) obj).booleanValue()) {
                        }
                        if (!z) {
                        }
                    } else {
                        z = false;
                        if (!z) {
                        }
                    }
                }
            }
            ShortcutCategory shortcutCategory = (ShortcutCategory) next;
            if (shortcutCategory != null && (shortcutCategoryType = shortcutCategory.type) != null) {
                return shortcutCategoryType;
            }
            ShortcutCategory shortcutCategory2 = (ShortcutCategory) CollectionsKt___CollectionsKt.firstOrNull(list2);
            if (shortcutCategory2 != null) {
                return shortcutCategory2.type;
            }
            return null;
        }
        ResultKt.throwOnFailure(obj);
        list2 = list;
        it = list.iterator();
        if (it.hasNext()) {
        }
    }
}
