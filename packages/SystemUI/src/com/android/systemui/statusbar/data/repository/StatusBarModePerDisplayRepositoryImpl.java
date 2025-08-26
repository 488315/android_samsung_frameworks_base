package com.android.systemui.statusbar.data.repository;

import android.graphics.Rect;
import android.view.InsetsFlags;
import android.view.ViewDebug;
import android.view.WindowInsets;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.view.AppearanceRegion;
import com.android.systemui.Dependency;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.data.model.StatusBarAppearance;
import com.android.systemui.statusbar.data.model.StatusBarMode;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.layout.BoundsPair;
import com.android.systemui.statusbar.layout.LetterboxAppearanceCalculator;
import com.android.systemui.statusbar.layout.StatusBarBoundsProvider;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.fragment.dagger.HomeStatusBarComponent;
import com.android.systemui.statusbar.phone.ongoingcall.data.repository.OngoingCallRepository;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.List;
import kotlin.ResultKt;
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

/* loaded from: classes3.dex */
public final class StatusBarModePerDisplayRepositoryImpl implements StatusBarModePerDisplayRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _isTransientShown;
    public final StateFlowImpl _ongoingProcessRequiresStatusBarVisible;
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
            return this.letterboxDetails.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.requestedVisibleTypes, TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.appearanceRegions, Integer.hashCode(this.appearance) * 31, 31), 31, this.navbarColorManagedByIme), 31);
        }

        public final String toString() {
            int i = this.appearance;
            return StringsKt__IndentKt.trimIndent("\n                StatusBarAttributes(\n                    appearance=" + (i == 0 ? PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE : ViewDebug.flagsToString(InsetsFlags.class, "appearance", i)) + ",\n                    appearanceRegions=" + this.appearanceRegions + ",\n                    navbarColorManagedByIme=" + this.navbarColorManagedByIme + ",\n                    requestedVisibleTypes=" + ContentInViewNode$Request$$ExternalSyntheticOutline0.m("[", StringsKt__StringsJVMKt.replace$default(WindowInsets.Type.toString(this.requestedVisibleTypes), " ", ", "), "]") + ",\n                    letterboxDetails=" + this.letterboxDetails + "\n                    )\n                    ");
        }
    }

    /* JADX WARN: Type inference failed for: r10v1, types: [com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$commandQueueCallback$1] */
    public StatusBarModePerDisplayRepositoryImpl(CoroutineScope coroutineScope, final int i, CommandQueue commandQueue, LetterboxAppearanceCalculator letterboxAppearanceCalculator, OngoingCallRepository ongoingCallRepository) {
        this.commandQueue = commandQueue;
        this.letterboxAppearanceCalculator = letterboxAppearanceCalculator;
        this.commandQueueCallback = new CommandQueue.Callbacks() { // from class: com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$commandQueueCallback$1
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void abortTransient(int i2, int i3) {
                if (i2 != i || (WindowInsets.Type.statusBars() & i3) == 0) {
                    return;
                }
                this.this$0._isTransientShown.updateState(null, Boolean.FALSE);
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void onSystemBarAttributesChanged(int i2, int i3, AppearanceRegion[] appearanceRegionArr, boolean z, int i4, int i5, String str, LetterboxDetails[] letterboxDetailsArr) {
                if (i2 != i) {
                    return;
                }
                this.this$0._originalStatusBarAttributes.updateState(null, new StatusBarModePerDisplayRepositoryImpl.StatusBarAttributes(i3, ArraysKt___ArraysKt.toList(appearanceRegionArr), z, i5, ArraysKt___ArraysKt.toList(letterboxDetailsArr)));
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
                this.this$0._isTransientShown.updateState(null, Boolean.TRUE);
            }
        };
        Boolean bool = Boolean.FALSE;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._isTransientShown = stateFlowImplMutableStateFlow;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.isTransientShown = readonlyStateFlowAsStateFlow;
        final StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._originalStatusBarAttributes = stateFlowImplMutableStateFlow2;
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(new BoundsPair(new Rect(), new Rect()));
        this._statusBarBounds = stateFlowImplMutableStateFlow3;
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(bool);
        this._ongoingProcessRequiresStatusBarVisible = stateFlowImplMutableStateFlow4;
        FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
        Flow flow = new Flow() { // from class: com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Boolean boolValueOf;
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
                        StatusBarModePerDisplayRepositoryImpl.StatusBarAttributes statusBarAttributes = (StatusBarModePerDisplayRepositoryImpl.StatusBarAttributes) obj;
                        if (statusBarAttributes != null) {
                            boolValueOf = Boolean.valueOf((statusBarAttributes.requestedVisibleTypes & WindowInsets.Type.statusBars()) == 0);
                        } else {
                            boolValueOf = Boolean.FALSE;
                        }
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
                Object objCollect = stateFlowImplMutableStateFlow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flow, coroutineScope, startedEagerly, bool);
        this.isInFullscreenMode = readonlyStateFlowStateIn;
        ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlowImplMutableStateFlow2, stateFlowImplMutableStateFlow3, new StatusBarModePerDisplayRepositoryImpl$modifiedStatusBarAttributes$1(this, null)), coroutineScope, startedEagerly, null);
        this.modifiedStatusBarAttributes = readonlyStateFlowStateIn2;
        final ReadonlyStateFlow readonlyStateFlowStateIn3 = FlowKt.stateIn(FlowKt.combine(readonlyStateFlowStateIn2, readonlyStateFlowAsStateFlow, readonlyStateFlowStateIn, ongoingCallRepository.ongoingCallState, stateFlowImplMutableStateFlow4, new StatusBarModePerDisplayRepositoryImpl$statusBarAppearance$1(this, null)), coroutineScope, startedEagerly, null);
        this.statusBarAppearance = readonlyStateFlowStateIn3;
        this.statusBarMode = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$2

            /* renamed from: com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    StatusBarMode statusBarMode;
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
                        StatusBarAppearance statusBarAppearance = (StatusBarAppearance) obj;
                        if (statusBarAppearance == null || (statusBarMode = statusBarAppearance.mode) == null) {
                            statusBarMode = StatusBarMode.TRANSPARENT;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(statusBarMode, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlowStateIn3.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, StatusBarMode.TRANSPARENT);
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(String.valueOf(this._originalStatusBarAttributes.getValue()));
        printWriter.println(String.valueOf(this.modifiedStatusBarAttributes.$$delegate_0.getValue()));
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("statusBarMode: ", this.statusBarMode.$$delegate_0.getValue(), printWriter);
    }

    @Override // com.android.systemui.statusbar.core.StatusBarInitializer.OnStatusBarViewInitializedListener
    public final void onStatusBarViewInitialized(HomeStatusBarComponent homeStatusBarComponent) {
        StatusBarBoundsProvider boundsProvider = homeStatusBarComponent.getBoundsProvider();
        StatusBarModePerDisplayRepositoryImpl$onStatusBarViewInitialized$listener$1 statusBarModePerDisplayRepositoryImpl$onStatusBarViewInitialized$listener$1 = new StatusBarModePerDisplayRepositoryImpl$onStatusBarViewInitialized$listener$1(this);
        boundsProvider.changeListeners.addIfAbsent(statusBarModePerDisplayRepositoryImpl$onStatusBarViewInitialized$listener$1);
        statusBarModePerDisplayRepositoryImpl$onStatusBarViewInitialized$listener$1.this$0._statusBarBounds.setValue(boundsProvider.previousBounds);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.commandQueue.addCallback((CommandQueue.Callbacks) this.commandQueueCallback);
    }
}
