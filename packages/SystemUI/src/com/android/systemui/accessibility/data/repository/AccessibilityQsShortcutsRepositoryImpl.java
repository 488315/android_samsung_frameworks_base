package com.android.systemui.accessibility.data.repository;

import android.content.ComponentName;
import android.content.Context;
import android.util.SparseArray;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.systemui.accessibility.data.repository.UserA11yQsShortcutsRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* loaded from: classes.dex */
public final class AccessibilityQsShortcutsRepositoryImpl implements AccessibilityQsShortcutsRepository {
    public static final Map TILE_SPEC_TO_COMPONENT_MAPPING;
    public final CoroutineDispatcher backgroundDispatcher;
    public final AccessibilityManager manager;
    public final SparseArray userA11yQsShortcutsRepositories = new SparseArray();
    public final UserA11yQsShortcutsRepository.Factory userA11yQsShortcutsRepositoryFactory;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepositoryImpl$notifyAccessibilityManagerTilesChanged$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AccessibilityQsShortcutsRepositoryImpl.this.notifyAccessibilityManagerTilesChanged(null, null, this);
        }
    }

    /* renamed from: com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepositoryImpl$notifyAccessibilityManagerTilesChanged$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ List<ComponentName> $newTiles;
        final /* synthetic */ Context $userContext;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Context context, List<ComponentName> list, Continuation continuation) {
            super(2, continuation);
            this.$userContext = context;
            this.$newTiles = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AccessibilityQsShortcutsRepositoryImpl.this.new AnonymousClass3(this.$userContext, this.$newTiles, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            AccessibilityQsShortcutsRepositoryImpl.this.manager.notifyQuickSettingsTilesChanged(this.$userContext.getUserId(), this.$newTiles);
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
        TILE_SPEC_TO_COMPONENT_MAPPING = MapsKt__MapsKt.mapOf(new Pair("ColorCorrection", AccessibilityShortcutController.DALTONIZER_TILE_COMPONENT_NAME), new Pair("ColorInversion", AccessibilityShortcutController.COLOR_INVERSION_TILE_COMPONENT_NAME), new Pair("onehanded", AccessibilityShortcutController.ONE_HANDED_TILE_COMPONENT_NAME), new Pair("ReduceBrightColors", AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_TILE_SERVICE_COMPONENT_NAME), new Pair("font_scaling", AccessibilityShortcutController.FONT_SIZE_TILE_COMPONENT_NAME), new Pair("hearing_devices", AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_TILE_COMPONENT_NAME), new Pair("HighContrastFont", AccessibilityShortcutController.HIGH_CONTRAST_FONTS_TILE_COMPONENT_NAME), new Pair("ColorLens", AccessibilityShortcutController.COLOR_LENS_TILE_COMPONENT_NAME));
    }

    public AccessibilityQsShortcutsRepositoryImpl(AccessibilityManager accessibilityManager, UserA11yQsShortcutsRepository.Factory factory, CoroutineDispatcher coroutineDispatcher) {
        this.manager = accessibilityManager;
        this.userA11yQsShortcutsRepositoryFactory = factory;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00dd, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r11, r2, r0) == r1) goto L41;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r9v6, types: [java.util.List] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object notifyAccessibilityManagerTilesChanged(Context context, List list, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        ArrayList arrayList;
        Object objCoroutineScope;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            arrayList = new ArrayList();
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = context;
            anonymousClass1.L$2 = list;
            anonymousClass1.L$3 = arrayList;
            anonymousClass1.label = 1;
            objCoroutineScope = CoroutineScopeKt.coroutineScope(new AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2(this, context, null), anonymousClass1);
            if (objCoroutineScope != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ?? r9 = (List) anonymousClass1.L$3;
        list = (List) anonymousClass1.L$2;
        context = (Context) anonymousClass1.L$1;
        AccessibilityQsShortcutsRepositoryImpl accessibilityQsShortcutsRepositoryImpl = (AccessibilityQsShortcutsRepositoryImpl) anonymousClass1.L$0;
        ResultKt.throwOnFailure(obj);
        arrayList = r9;
        this = accessibilityQsShortcutsRepositoryImpl;
        objCoroutineScope = obj;
        Set set = (Set) objCoroutineScope;
        for (TileSpec tileSpec : list) {
            if (tileSpec instanceof TileSpec.CustomTileSpec) {
                TileSpec.CustomTileSpec customTileSpec = (TileSpec.CustomTileSpec) tileSpec;
                if (set.contains(customTileSpec.componentName)) {
                    arrayList.add(customTileSpec.componentName);
                }
            } else if (tileSpec instanceof TileSpec.PlatformTileSpec) {
                Map map = TILE_SPEC_TO_COMPONENT_MAPPING;
                TileSpec.PlatformTileSpec platformTileSpec = (TileSpec.PlatformTileSpec) tileSpec;
                if (map.containsKey(platformTileSpec.spec)) {
                    Object obj2 = map.get(platformTileSpec.spec);
                    obj2.getClass();
                    arrayList.add(obj2);
                }
            } else if (!Intrinsics.areEqual(tileSpec, TileSpec.Invalid.INSTANCE) && !Intrinsics.areEqual(tileSpec, TileSpec.Empty.INSTANCE)) {
                throw new NoWhenBranchMatchedException();
            }
        }
        CoroutineDispatcher coroutineDispatcher = this.backgroundDispatcher;
        AnonymousClass3 anonymousClass3 = this.new AnonymousClass3(context, arrayList, null);
        anonymousClass1.L$0 = null;
        anonymousClass1.L$1 = null;
        anonymousClass1.L$2 = null;
        anonymousClass1.L$3 = null;
        anonymousClass1.label = 2;
    }
}
