package com.android.systemui.statusbar.data.repository;

import android.graphics.Rect;
import android.view.InsetsFlags;
import android.view.ViewDebug;
import android.view.WindowInsets;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.view.AppearanceRegion;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.core.StatusBarInitializer;
import com.android.systemui.statusbar.data.model.StatusBarMode;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.phone.BoundsPair;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.LetterboxAppearanceCalculator;
import com.android.systemui.statusbar.phone.StatusBarBoundsProvider;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentComponent;
import com.android.systemui.statusbar.phone.ongoingcall.data.repository.OngoingCallRepository;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import java.io.PrintWriter;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StatusBarModePerDisplayRepositoryImpl implements StatusBarInitializer.OnStatusBarViewInitializedListener, Dumpable {
    public final StateFlowImpl _isTransientShown;
    public final StateFlowImpl _originalStatusBarAttributes;
    public final StateFlowImpl _statusBarBounds;
    public final CommandQueue commandQueue;
    public final StatusBarModePerDisplayRepositoryImpl$commandQueueCallback$1 commandQueueCallback;
    public final ReadonlyStateFlow isInFullscreenMode;
    public final ReadonlyStateFlow isTransientShown;
    public final LetterboxAppearanceCalculator letterboxAppearanceCalculator;
    public final ReadonlyStateFlow modifiedStatusBarAttributes;
    public final ReadonlyStateFlow statusBarAppearance;
    public final ReadonlyStateFlow statusBarMode;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ModifiedStatusBarAttributes {
        public final int appearance;
        public final List appearanceRegions;
        public final boolean navbarColorManagedByIme;
        public final BoundsPair statusBarBounds;

        public ModifiedStatusBarAttributes(int i, List<? extends AppearanceRegion> list, boolean z, BoundsPair boundsPair) {
            this.appearance = i;
            this.appearanceRegions = list;
            this.navbarColorManagedByIme = z;
            this.statusBarBounds = boundsPair;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ModifiedStatusBarAttributes)) {
                return false;
            }
            ModifiedStatusBarAttributes modifiedStatusBarAttributes = (ModifiedStatusBarAttributes) obj;
            return this.appearance == modifiedStatusBarAttributes.appearance && Intrinsics.areEqual(this.appearanceRegions, modifiedStatusBarAttributes.appearanceRegions) && this.navbarColorManagedByIme == modifiedStatusBarAttributes.navbarColorManagedByIme && Intrinsics.areEqual(this.statusBarBounds, modifiedStatusBarAttributes.statusBarBounds);
        }

        public final int hashCode() {
            return this.statusBarBounds.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.appearanceRegions, Integer.hashCode(this.appearance) * 31, 31), 31, this.navbarColorManagedByIme);
        }

        public final String toString() {
            int i = this.appearance;
            return StringsKt__IndentKt.trimIndent("\n                ModifiedStatusBarAttributes(\n                    appearance=" + (i == 0 ? PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE : ViewDebug.flagsToString(InsetsFlags.class, "appearance", i)) + ",\n                    appearanceRegions=" + this.appearanceRegions + ",\n                    navbarColorManagedByIme=" + this.navbarColorManagedByIme + ",\n                    statusBarBounds=" + this.statusBarBounds + "\n                    )\n                    ");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class StatusBarAttributes {
        public final int appearance;
        public final List appearanceRegions;
        public final List letterboxDetails;
        public final boolean navbarColorManagedByIme;
        public final int requestedVisibleTypes;

        public StatusBarAttributes(int i, List<? extends AppearanceRegion> list, boolean z, int i2, List<? extends LetterboxDetails> list2) {
            this.appearance = i;
            this.appearanceRegions = list;
            this.navbarColorManagedByIme = z;
            this.requestedVisibleTypes = i2;
            this.letterboxDetails = list2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StatusBarAttributes)) {
                return false;
            }
            StatusBarAttributes statusBarAttributes = (StatusBarAttributes) obj;
            return this.appearance == statusBarAttributes.appearance && Intrinsics.areEqual(this.appearanceRegions, statusBarAttributes.appearanceRegions) && this.navbarColorManagedByIme == statusBarAttributes.navbarColorManagedByIme && this.requestedVisibleTypes == statusBarAttributes.requestedVisibleTypes && Intrinsics.areEqual(this.letterboxDetails, statusBarAttributes.letterboxDetails);
        }

        public final int hashCode() {
            return this.letterboxDetails.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.requestedVisibleTypes, TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.appearanceRegions, Integer.hashCode(this.appearance) * 31, 31), 31, this.navbarColorManagedByIme), 31);
        }

        public final String toString() {
            int i = this.appearance;
            return StringsKt__IndentKt.trimIndent("\n                StatusBarAttributes(\n                    appearance=" + (i == 0 ? PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE : ViewDebug.flagsToString(InsetsFlags.class, "appearance", i)) + ",\n                    appearanceRegions=" + this.appearanceRegions + ",\n                    navbarColorManagedByIme=" + this.navbarColorManagedByIme + ",\n                    requestedVisibleTypes=" + ContentInViewNode$Request$$ExternalSyntheticOutline0.m("[", StringsKt__StringsJVMKt.replace$default(WindowInsets.Type.toString(this.requestedVisibleTypes), " ", ", "), "]") + ",\n                    letterboxDetails=" + this.letterboxDetails + "\n                    )\n                    ");
        }
    }

    /* JADX WARN: Type inference failed for: r8v1, types: [com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$commandQueueCallback$1] */
    public StatusBarModePerDisplayRepositoryImpl(CoroutineScope coroutineScope, final int i, CommandQueue commandQueue, LetterboxAppearanceCalculator letterboxAppearanceCalculator, OngoingCallRepository ongoingCallRepository) {
        this.commandQueue = commandQueue;
        this.letterboxAppearanceCalculator = letterboxAppearanceCalculator;
        this.commandQueueCallback = new CommandQueue.Callbacks() { // from class: com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$commandQueueCallback$1
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void abortTransient(int i2, int i3) {
                if (i2 != i || (WindowInsets.Type.statusBars() & i3) == 0) {
                    return;
                }
                StatusBarModePerDisplayRepositoryImpl.this._isTransientShown.updateState(null, Boolean.FALSE);
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void onSystemBarAttributesChanged(int i2, int i3, AppearanceRegion[] appearanceRegionArr, boolean z, int i4, int i5, String str, LetterboxDetails[] letterboxDetailsArr) {
                if (i2 != i) {
                    return;
                }
                StatusBarModePerDisplayRepositoryImpl.this._originalStatusBarAttributes.updateState(null, new StatusBarModePerDisplayRepositoryImpl.StatusBarAttributes(i3, ArraysKt___ArraysKt.toList(appearanceRegionArr), z, i5, ArraysKt___ArraysKt.toList(letterboxDetailsArr)));
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void showTransient(int i2, int i3, boolean z) {
                if (i2 != i || (WindowInsets.Type.statusBars() & i3) == 0) {
                    return;
                }
                CentralSurfaces centralSurfaces = (CentralSurfaces) Dependency.sDependency.getDependencyInner(CentralSurfaces.class);
                if (centralSurfaces != null) {
                    ((CentralSurfacesImpl) centralSurfaces).mNoAnimationOnNextBarModeChange = true;
                }
                StatusBarModePerDisplayRepositoryImpl.this._isTransientShown.updateState(null, Boolean.TRUE);
            }
        };
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isTransientShown = MutableStateFlow;
        ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(MutableStateFlow);
        this.isTransientShown = asStateFlow;
        final StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._originalStatusBarAttributes = MutableStateFlow2;
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(new BoundsPair(new Rect(), new Rect()));
        this._statusBarBounds = MutableStateFlow3;
        Flow flow = new Flow() { // from class: com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L54
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$StatusBarAttributes r5 = (com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl.StatusBarAttributes) r5
                        if (r5 == 0) goto L47
                        int r6 = android.view.WindowInsets.Type.statusBars()
                        int r5 = r5.requestedVisibleTypes
                        r5 = r5 & r6
                        if (r5 != 0) goto L41
                        r5 = r3
                        goto L42
                    L41:
                        r5 = 0
                    L42:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        goto L49
                    L47:
                        java.lang.Boolean r5 = java.lang.Boolean.FALSE
                    L49:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L54
                        return r1
                    L54:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flow, coroutineScope, startedEagerly, bool);
        this.isInFullscreenMode = stateIn;
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(MutableStateFlow2, MutableStateFlow3, new StatusBarModePerDisplayRepositoryImpl$modifiedStatusBarAttributes$1(this, null)), coroutineScope, startedEagerly, null);
        this.modifiedStatusBarAttributes = stateIn2;
        final ReadonlyStateFlow stateIn3 = FlowKt.stateIn(FlowKt.combine(stateIn2, asStateFlow, stateIn, ongoingCallRepository.ongoingCallState, new StatusBarModePerDisplayRepositoryImpl$statusBarAppearance$1(this, null)), coroutineScope, startedEagerly, null);
        this.statusBarAppearance = stateIn3;
        this.statusBarMode = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L47
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.statusbar.data.model.StatusBarAppearance r5 = (com.android.systemui.statusbar.data.model.StatusBarAppearance) r5
                        if (r5 == 0) goto L3a
                        com.android.systemui.statusbar.data.model.StatusBarMode r5 = r5.mode
                        if (r5 != 0) goto L3c
                    L3a:
                        com.android.systemui.statusbar.data.model.StatusBarMode r5 = com.android.systemui.statusbar.data.model.StatusBarMode.TRANSPARENT
                    L3c:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L47
                        return r1
                    L47:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, StatusBarMode.TRANSPARENT);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(String.valueOf(this._originalStatusBarAttributes.getValue()));
        printWriter.println(String.valueOf(this.modifiedStatusBarAttributes.$$delegate_0.getValue()));
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("statusBarMode: ", this.statusBarMode.$$delegate_0.getValue(), printWriter);
    }

    @Override // com.android.systemui.statusbar.core.StatusBarInitializer.OnStatusBarViewInitializedListener
    public final void onStatusBarViewInitialized(StatusBarFragmentComponent statusBarFragmentComponent) {
        StatusBarBoundsProvider boundsProvider = statusBarFragmentComponent.getBoundsProvider();
        StatusBarModePerDisplayRepositoryImpl$onStatusBarViewInitialized$listener$1 statusBarModePerDisplayRepositoryImpl$onStatusBarViewInitialized$listener$1 = new StatusBarModePerDisplayRepositoryImpl$onStatusBarViewInitialized$listener$1(this);
        boundsProvider.changeListeners.addIfAbsent(statusBarModePerDisplayRepositoryImpl$onStatusBarViewInitialized$listener$1);
        statusBarModePerDisplayRepositoryImpl$onStatusBarViewInitialized$listener$1.this$0._statusBarBounds.updateState(null, boundsProvider.previousBounds);
    }
}
