package androidx.compose.foundation;

import android.view.View;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatableNode_androidKt;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.GlobalPositionAwareModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.node.ObserverModifierNode;
import androidx.compose.ui.node.ObserverModifierNodeKt;
import androidx.compose.ui.node.SemanticsModifierNode;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpSize;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ChannelResult;

/* loaded from: classes.dex */
public final class MagnifierNode extends Modifier.Node implements GlobalPositionAwareModifierNode, DrawModifierNode, SemanticsModifierNode, ObserverModifierNode {
    public State anchorPositionInRootState;
    public boolean clippingEnabled;
    public float cornerRadius;
    public Density density;
    public BufferedChannel drawSignalChannel;
    public float elevation;
    public final MutableState layoutCoordinates$delegate;
    public PlatformMagnifier magnifier;
    public Function1 magnifierCenter;
    public Function1 onSizeChanged;
    public PlatformMagnifierFactory platformMagnifierFactory;
    public IntSize previousSize;
    public long size;
    public Function1 sourceCenter;
    public long sourceCenterInRoot;
    public boolean useTextDefault;
    public View view;
    public float zoom;

    /* renamed from: androidx.compose.foundation.MagnifierNode$onAttach$1, reason: invalid class name and case insensitive filesystem */
    final class C06821 extends SuspendLambda implements Function2 {
        int label;

        public C06821(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MagnifierNode.this.new C06821(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C06821) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x0049, code lost:
        
            if (androidx.compose.runtime.MonotonicFrameClockKt.getMonotonicFrameClock(getContext()).withFrameNanos(new androidx.compose.runtime.MonotonicFrameClockKt$withFrameMillis$2(r6), r5) == r0) goto L20;
         */
        /* JADX WARN: Path cross not found for [B:13:0x0025, B:16:0x002e], limit reached: 23 */
        /* JADX WARN: Path cross not found for [B:16:0x002e, B:13:0x0025], limit reached: 23 */
        /* JADX WARN: Removed duplicated region for block: B:11:0x001f  */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0025  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0034  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0032 -> B:11:0x001f). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0049 -> B:21:0x004c). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            BufferedChannel bufferedChannel;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                bufferedChannel = MagnifierNode.this.drawSignalChannel;
                if (bufferedChannel != null) {
                }
                if (MagnifierNode.this.magnifier == null) {
                }
                return coroutineSingletons;
            }
            if (i == 1) {
                ResultKt.throwOnFailure(obj);
                if (MagnifierNode.this.magnifier == null) {
                }
                return coroutineSingletons;
            }
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            PlatformMagnifier platformMagnifier = MagnifierNode.this.magnifier;
            if (platformMagnifier != null) {
                ((PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl) platformMagnifier).magnifier.update();
            }
            bufferedChannel = MagnifierNode.this.drawSignalChannel;
            if (bufferedChannel != null) {
                this.label = 1;
                if (bufferedChannel.receive(this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (MagnifierNode.this.magnifier == null) {
                C00031 c00031 = new Function1() { // from class: androidx.compose.foundation.MagnifierNode.onAttach.1.1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj2) {
                        ((Number) obj2).longValue();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 2;
            } else {
                bufferedChannel = MagnifierNode.this.drawSignalChannel;
                if (bufferedChannel != null) {
                }
                if (MagnifierNode.this.magnifier == null) {
                }
            }
            return coroutineSingletons;
        }
    }

    public /* synthetic */ MagnifierNode(Function1 function1, Function1 function12, Function1 function13, float f, boolean z, long j, float f2, float f3, boolean z2, PlatformMagnifierFactory platformMagnifierFactory, DefaultConstructorMarker defaultConstructorMarker) {
        this(function1, function12, function13, f, z, j, f2, f3, z2, platformMagnifierFactory);
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final void applySemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        ((SemanticsConfiguration) semanticsPropertyReceiver).set(Magnifier_androidKt.MagnifierPositionInRoot, new Function0() { // from class: androidx.compose.foundation.MagnifierNode.applySemantics.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Offset.m395boximpl(MagnifierNode.this.sourceCenterInRoot);
            }
        });
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        layoutNodeDrawScope.drawContent();
        BufferedChannel bufferedChannel = this.drawSignalChannel;
        if (bufferedChannel != null) {
            ChannelResult.m3477boximpl(bufferedChannel.mo3476trySendJP2dKIU(Unit.INSTANCE));
        }
    }

    /* renamed from: getAnchorPositionInRoot-F1C5BW0, reason: not valid java name */
    public final long m43getAnchorPositionInRootF1C5BW0() {
        if (this.anchorPositionInRootState == null) {
            this.anchorPositionInRootState = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.foundation.MagnifierNode$anchorPositionInRoot$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    long jPositionInRoot;
                    LayoutCoordinates layoutCoordinates = (LayoutCoordinates) ((SnapshotMutableStateImpl) this.this$0.layoutCoordinates$delegate).getValue();
                    if (layoutCoordinates != null) {
                        jPositionInRoot = LayoutCoordinatesKt.positionInRoot(layoutCoordinates);
                    } else {
                        Offset.Companion.getClass();
                        jPositionInRoot = Offset.Unspecified;
                    }
                    return Offset.m395boximpl(jPositionInRoot);
                }
            });
        }
        State state = this.anchorPositionInRootState;
        if (state != null) {
            return ((Offset) state.getValue()).packedValue;
        }
        Offset.Companion.getClass();
        return Offset.Unspecified;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        onObservedReadsChanged();
        this.drawSignalChannel = ChannelKt.Channel$default(0, null, null, 7);
        BuildersKt.launch$default(getCoroutineScope(), null, CoroutineStart.UNDISPATCHED, new C06821(null), 1);
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        PlatformMagnifier platformMagnifier = this.magnifier;
        if (platformMagnifier != null) {
            ((PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl) platformMagnifier).magnifier.dismiss();
        }
        this.magnifier = null;
    }

    @Override // androidx.compose.ui.node.GlobalPositionAwareModifierNode
    public final void onGloballyPositioned(NodeCoordinator nodeCoordinator) {
        ((SnapshotMutableStateImpl) this.layoutCoordinates$delegate).setValue(nodeCoordinator);
    }

    @Override // androidx.compose.ui.node.ObserverModifierNode
    public final void onObservedReadsChanged() {
        ObserverModifierNodeKt.observeReads(this, new Function0() { // from class: androidx.compose.foundation.MagnifierNode.onObservedReadsChanged.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                MagnifierNode.this.updateMagnifier();
                return Unit.INSTANCE;
            }
        });
    }

    public final void recreateMagnifier() {
        PlatformMagnifier platformMagnifier = this.magnifier;
        if (platformMagnifier != null) {
            ((PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl) platformMagnifier).magnifier.dismiss();
        }
        View viewRequireView = this.view;
        if (viewRequireView == null) {
            viewRequireView = DelegatableNode_androidKt.requireView(this);
        }
        View view = viewRequireView;
        this.view = view;
        Density density = this.density;
        if (density == null) {
            density = DelegatableNodeKt.requireLayoutNode(this).density;
        }
        Density density2 = density;
        this.density = density2;
        this.magnifier = this.platformMagnifierFactory.mo47createnHHXs2Y(view, this.useTextDefault, this.size, this.cornerRadius, this.elevation, this.clippingEnabled, density2, this.zoom);
        updateSizeIfNecessary();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateMagnifier() {
        long jM403plusMKHz9U;
        Density density = this.density;
        if (density == null) {
            density = DelegatableNodeKt.requireLayoutNode(this).density;
            this.density = density;
        }
        long j = ((Offset) this.sourceCenter.mo781invoke(density)).packedValue;
        if ((j & 9223372034707292159L) == 9205357640488583168L || (m43getAnchorPositionInRootF1C5BW0() & 9223372034707292159L) == 9205357640488583168L) {
            Offset.Companion.getClass();
            this.sourceCenterInRoot = Offset.Unspecified;
            PlatformMagnifier platformMagnifier = this.magnifier;
            if (platformMagnifier != null) {
                ((PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl) platformMagnifier).magnifier.dismiss();
                return;
            }
            return;
        }
        this.sourceCenterInRoot = Offset.m403plusMKHz9U(m43getAnchorPositionInRootF1C5BW0(), j);
        Function1 function1 = this.magnifierCenter;
        if (function1 != null) {
            Offset offsetM395boximpl = Offset.m395boximpl(((Offset) function1.mo781invoke(density)).packedValue);
            if ((offsetM395boximpl.packedValue & 9223372034707292159L) == 9205357640488583168L) {
                offsetM395boximpl = null;
            }
            if (offsetM395boximpl != null) {
                jM403plusMKHz9U = Offset.m403plusMKHz9U(m43getAnchorPositionInRootF1C5BW0(), offsetM395boximpl.packedValue);
            } else {
                Offset.Companion.getClass();
                jM403plusMKHz9U = Offset.Unspecified;
            }
        }
        long j2 = jM403plusMKHz9U;
        if (this.magnifier == null) {
            recreateMagnifier();
        }
        PlatformMagnifier platformMagnifier2 = this.magnifier;
        if (platformMagnifier2 != null) {
            platformMagnifier2.mo46updateWko1d7g(this.sourceCenterInRoot, j2, this.zoom);
        }
        updateSizeIfNecessary();
    }

    public final void updateSizeIfNecessary() {
        Density density;
        PlatformMagnifier platformMagnifier = this.magnifier;
        if (platformMagnifier == null || (density = this.density) == null) {
            return;
        }
        PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl platformMagnifierFactoryApi28Impl$PlatformMagnifierImpl = (PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl) platformMagnifier;
        if (IntSize.m862equalsimpl(platformMagnifierFactoryApi28Impl$PlatformMagnifierImpl.m48getSizeYbymL2g(), this.previousSize)) {
            return;
        }
        Function1 function1 = this.onSizeChanged;
        if (function1 != null) {
            function1.mo781invoke(DpSize.m844boximpl(density.mo56toDpSizekrfVVM(IntSizeKt.m866toSizeozmzZPI(platformMagnifierFactoryApi28Impl$PlatformMagnifierImpl.m48getSizeYbymL2g()))));
        }
        this.previousSize = IntSize.m861boximpl(platformMagnifierFactoryApi28Impl$PlatformMagnifierImpl.m48getSizeYbymL2g());
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public MagnifierNode(Function1 function1, Function1 function12, Function1 function13, float f, boolean z, long j, float f2, float f3, boolean z2, PlatformMagnifierFactory platformMagnifierFactory, int i, DefaultConstructorMarker defaultConstructorMarker) {
        function12 = (i & 2) != 0 ? null : function12;
        function13 = (i & 4) != 0 ? null : function13;
        f = (i & 8) != 0 ? Float.NaN : f;
        z = (i & 16) != 0 ? false : z;
        if ((i & 32) != 0) {
            DpSize.Companion.getClass();
            j = DpSize.Unspecified;
        }
        if ((i & 64) != 0) {
            Dp.Companion.getClass();
            f2 = Dp.Unspecified;
        }
        if ((i & 128) != 0) {
            Dp.Companion.getClass();
            f3 = Dp.Unspecified;
        }
        z2 = (i & 256) != 0 ? true : z2;
        if ((i & 512) != 0) {
            PlatformMagnifierFactory.Companion.getClass();
            SemanticsPropertyKey semanticsPropertyKey = Magnifier_androidKt.MagnifierPositionInRoot;
            platformMagnifierFactory = PlatformMagnifierFactoryApi29Impl.INSTANCE;
        }
        this(function1, function12, function13, f, z, j, f2, f3, z2, platformMagnifierFactory, null);
    }

    private MagnifierNode(Function1 function1, Function1 function12, Function1 function13, float f, boolean z, long j, float f2, float f3, boolean z2, PlatformMagnifierFactory platformMagnifierFactory) {
        this.sourceCenter = function1;
        this.magnifierCenter = function12;
        this.onSizeChanged = function13;
        this.zoom = f;
        this.useTextDefault = z;
        this.size = j;
        this.cornerRadius = f2;
        this.elevation = f3;
        this.clippingEnabled = z2;
        this.platformMagnifierFactory = platformMagnifierFactory;
        this.layoutCoordinates$delegate = SnapshotStateKt.mutableStateOf(null, SnapshotStateKt.neverEqualPolicy());
        Offset.Companion.getClass();
        this.sourceCenterInRoot = Offset.Unspecified;
    }
}
