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
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DpSize;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ChannelResult;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public /* synthetic */ MagnifierNode(Function1 function1, Function1 function12, Function1 function13, float f, boolean z, long j, float f2, float f3, boolean z2, PlatformMagnifierFactory platformMagnifierFactory, DefaultConstructorMarker defaultConstructorMarker) {
        this(function1, function12, function13, f, z, j, f2, f3, z2, platformMagnifierFactory);
    }

    @Override // androidx.compose.ui.node.SemanticsModifierNode
    public final void applySemantics(SemanticsPropertyReceiver semanticsPropertyReceiver) {
        ((SemanticsConfiguration) semanticsPropertyReceiver).set(Magnifier_androidKt.MagnifierPositionInRoot, new Function0() { // from class: androidx.compose.foundation.MagnifierNode$applySemantics$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Offset.m393boximpl(MagnifierNode.this.sourceCenterInRoot);
            }
        });
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(LayoutNodeDrawScope layoutNodeDrawScope) {
        layoutNodeDrawScope.drawContent();
        BufferedChannel bufferedChannel = this.drawSignalChannel;
        if (bufferedChannel != null) {
            ChannelResult.m3457boximpl(bufferedChannel.mo3456trySendJP2dKIU(Unit.INSTANCE));
        }
    }

    /* renamed from: getAnchorPositionInRoot-F1C5BW0, reason: not valid java name */
    public final long m42getAnchorPositionInRootF1C5BW0() {
        if (this.anchorPositionInRootState == null) {
            this.anchorPositionInRootState = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.foundation.MagnifierNode$anchorPositionInRoot$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    long j;
                    LayoutCoordinates layoutCoordinates = (LayoutCoordinates) ((SnapshotMutableStateImpl) MagnifierNode.this.layoutCoordinates$delegate).getValue();
                    if (layoutCoordinates != null) {
                        j = LayoutCoordinatesKt.positionInRoot(layoutCoordinates);
                    } else {
                        Offset.Companion.getClass();
                        j = Offset.Unspecified;
                    }
                    return Offset.m393boximpl(j);
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
        BuildersKt.launch$default(getCoroutineScope(), null, CoroutineStart.UNDISPATCHED, new MagnifierNode$onAttach$1(this, null), 1);
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
        ObserverModifierNodeKt.observeReads(this, new Function0() { // from class: androidx.compose.foundation.MagnifierNode$onObservedReadsChanged$1
            {
                super(0);
            }

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
        View view = this.view;
        if (view == null) {
            view = DelegatableNode_androidKt.requireView(this);
        }
        View view2 = view;
        this.view = view2;
        Density density = this.density;
        if (density == null) {
            density = DelegatableNodeKt.requireLayoutNode(this).density;
        }
        Density density2 = density;
        this.density = density2;
        this.magnifier = this.platformMagnifierFactory.mo46createnHHXs2Y(view2, this.useTextDefault, this.size, this.cornerRadius, this.elevation, this.clippingEnabled, density2, this.zoom);
        updateSizeIfNecessary();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0073  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateMagnifier() {
        /*
            r9 = this;
            androidx.compose.ui.unit.Density r0 = r9.density
            if (r0 != 0) goto Lc
            androidx.compose.ui.node.LayoutNode r0 = androidx.compose.ui.node.DelegatableNodeKt.requireLayoutNode(r9)
            androidx.compose.ui.unit.Density r0 = r0.density
            r9.density = r0
        Lc:
            kotlin.jvm.functions.Function1 r1 = r9.sourceCenter
            java.lang.Object r1 = r1.mo779invoke(r0)
            androidx.compose.ui.geometry.Offset r1 = (androidx.compose.ui.geometry.Offset) r1
            long r1 = r1.packedValue
            r3 = 9223372034707292159(0x7fffffff7fffffff, double:NaN)
            long r5 = r1 & r3
            r7 = 9205357640488583168(0x7fc000007fc00000, double:2.247117487993712E307)
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 == 0) goto L7e
            long r5 = r9.m42getAnchorPositionInRootF1C5BW0()
            long r5 = r5 & r3
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 == 0) goto L7e
            long r5 = r9.m42getAnchorPositionInRootF1C5BW0()
            long r1 = androidx.compose.ui.geometry.Offset.m401plusMKHz9U(r5, r1)
            r9.sourceCenterInRoot = r1
            kotlin.jvm.functions.Function1 r1 = r9.magnifierCenter
            if (r1 == 0) goto L60
            java.lang.Object r0 = r1.mo779invoke(r0)
            androidx.compose.ui.geometry.Offset r0 = (androidx.compose.ui.geometry.Offset) r0
            long r0 = r0.packedValue
            androidx.compose.ui.geometry.Offset r0 = androidx.compose.ui.geometry.Offset.m393boximpl(r0)
            long r1 = r0.packedValue
            long r1 = r1 & r3
            int r1 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
            if (r1 == 0) goto L51
            goto L52
        L51:
            r0 = 0
        L52:
            if (r0 == 0) goto L60
            long r1 = r9.m42getAnchorPositionInRootF1C5BW0()
            long r3 = r0.packedValue
            long r0 = androidx.compose.ui.geometry.Offset.m401plusMKHz9U(r1, r3)
        L5e:
            r5 = r0
            goto L68
        L60:
            androidx.compose.ui.geometry.Offset$Companion r0 = androidx.compose.ui.geometry.Offset.Companion
            r0.getClass()
            long r0 = androidx.compose.ui.geometry.Offset.Unspecified
            goto L5e
        L68:
            androidx.compose.foundation.PlatformMagnifier r0 = r9.magnifier
            if (r0 != 0) goto L6f
            r9.recreateMagnifier()
        L6f:
            androidx.compose.foundation.PlatformMagnifier r2 = r9.magnifier
            if (r2 == 0) goto L7a
            long r3 = r9.sourceCenterInRoot
            float r7 = r9.zoom
            r2.mo45updateWko1d7g(r3, r5, r7)
        L7a:
            r9.updateSizeIfNecessary()
            return
        L7e:
            androidx.compose.ui.geometry.Offset$Companion r0 = androidx.compose.ui.geometry.Offset.Companion
            r0.getClass()
            long r0 = androidx.compose.ui.geometry.Offset.Unspecified
            r9.sourceCenterInRoot = r0
            androidx.compose.foundation.PlatformMagnifier r9 = r9.magnifier
            if (r9 == 0) goto L92
            androidx.compose.foundation.PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl r9 = (androidx.compose.foundation.PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl) r9
            android.widget.Magnifier r9 = r9.magnifier
            r9.dismiss()
        L92:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.MagnifierNode.updateMagnifier():void");
    }

    public final void updateSizeIfNecessary() {
        Density density;
        PlatformMagnifier platformMagnifier = this.magnifier;
        if (platformMagnifier == null || (density = this.density) == null) {
            return;
        }
        PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl platformMagnifierFactoryApi28Impl$PlatformMagnifierImpl = (PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl) platformMagnifier;
        if (IntSize.m860equalsimpl(platformMagnifierFactoryApi28Impl$PlatformMagnifierImpl.m47getSizeYbymL2g(), this.previousSize)) {
            return;
        }
        Function1 function1 = this.onSizeChanged;
        if (function1 != null) {
            function1.mo779invoke(DpSize.m842boximpl(density.mo55toDpSizekrfVVM(IntSizeKt.m864toSizeozmzZPI(platformMagnifierFactoryApi28Impl$PlatformMagnifierImpl.m47getSizeYbymL2g()))));
        }
        this.previousSize = IntSize.m859boximpl(platformMagnifierFactoryApi28Impl$PlatformMagnifierImpl.m47getSizeYbymL2g());
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MagnifierNode(kotlin.jvm.functions.Function1 r2, kotlin.jvm.functions.Function1 r3, kotlin.jvm.functions.Function1 r4, float r5, boolean r6, long r7, float r9, float r10, boolean r11, androidx.compose.foundation.PlatformMagnifierFactory r12, int r13, kotlin.jvm.internal.DefaultConstructorMarker r14) {
        /*
            r1 = this;
            r14 = r13 & 2
            r0 = 0
            if (r14 == 0) goto L6
            r3 = r0
        L6:
            r14 = r13 & 4
            if (r14 == 0) goto Lb
            r4 = r0
        Lb:
            r14 = r13 & 8
            if (r14 == 0) goto L11
            r5 = 2143289344(0x7fc00000, float:NaN)
        L11:
            r14 = r13 & 16
            if (r14 == 0) goto L16
            r6 = 0
        L16:
            r14 = r13 & 32
            if (r14 == 0) goto L21
            androidx.compose.ui.unit.DpSize$Companion r7 = androidx.compose.ui.unit.DpSize.Companion
            r7.getClass()
            long r7 = androidx.compose.ui.unit.DpSize.Unspecified
        L21:
            r14 = r13 & 64
            if (r14 == 0) goto L2c
            androidx.compose.ui.unit.Dp$Companion r9 = androidx.compose.ui.unit.Dp.Companion
            r9.getClass()
            float r9 = androidx.compose.ui.unit.Dp.Unspecified
        L2c:
            r14 = r13 & 128(0x80, float:1.8E-43)
            if (r14 == 0) goto L37
            androidx.compose.ui.unit.Dp$Companion r10 = androidx.compose.ui.unit.Dp.Companion
            r10.getClass()
            float r10 = androidx.compose.ui.unit.Dp.Unspecified
        L37:
            r14 = r13 & 256(0x100, float:3.59E-43)
            if (r14 == 0) goto L3c
            r11 = 1
        L3c:
            r13 = r13 & 512(0x200, float:7.17E-43)
            if (r13 == 0) goto L49
            androidx.compose.foundation.PlatformMagnifierFactory$Companion r12 = androidx.compose.foundation.PlatformMagnifierFactory.Companion
            r12.getClass()
            androidx.compose.ui.semantics.SemanticsPropertyKey r12 = androidx.compose.foundation.Magnifier_androidKt.MagnifierPositionInRoot
            androidx.compose.foundation.PlatformMagnifierFactoryApi29Impl r12 = androidx.compose.foundation.PlatformMagnifierFactoryApi29Impl.INSTANCE
        L49:
            r13 = 0
            r1.<init>(r2, r3, r4, r5, r6, r7, r9, r10, r11, r12, r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.MagnifierNode.<init>(kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function1, float, boolean, long, float, float, boolean, androidx.compose.foundation.PlatformMagnifierFactory, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
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
