package androidx.compose.foundation.gestures;

import androidx.compose.foundation.gestures.BringIntoViewSpec;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.relocation.BringIntoViewResponder;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.LayoutAwareModifierNode;
import androidx.compose.ui.node.NodeCoordinator;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.IntSizeKt;
import java.util.concurrent.CancellationException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.text.CharsKt__CharJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineName;
import kotlinx.coroutines.CoroutineStart;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ContentInViewNode extends Modifier.Node implements BringIntoViewResponder, LayoutAwareModifierNode, CompositionLocalConsumerModifierNode {
    public final BringIntoViewRequestPriorityQueue bringIntoViewRequests = new BringIntoViewRequestPriorityQueue();
    public BringIntoViewSpec bringIntoViewSpec;
    public boolean childWasMaxVisibleBeforeViewportShrunk;
    public LayoutCoordinates focusedChild;
    public boolean isAnimationRunning;
    public Orientation orientation;
    public boolean reverseDirection;
    public final ScrollingLogic scrollingLogic;
    public boolean trackingFocusedChild;
    public long viewportSize;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Request {
        public final CancellableContinuation continuation;
        public final Function0 currentBounds;

        public Request(Function0 function0, CancellableContinuation cancellableContinuation) {
            this.currentBounds = function0;
            this.continuation = cancellableContinuation;
        }

        public final String toString() {
            String str;
            CancellableContinuation cancellableContinuation = this.continuation;
            CoroutineName coroutineName = (CoroutineName) cancellableContinuation.getContext().get(CoroutineName.Key);
            String str2 = coroutineName != null ? coroutineName.name : null;
            StringBuilder sb = new StringBuilder("Request@");
            int hashCode = hashCode();
            CharsKt__CharJVMKt.checkRadix(16);
            sb.append(Integer.toString(hashCode, 16));
            if (str2 == null || (str = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("[", str2, "](")) == null) {
                str = "(";
            }
            sb.append(str);
            sb.append("currentBounds()=");
            sb.append(this.currentBounds.invoke());
            sb.append(", continuation=");
            sb.append(cancellableContinuation);
            sb.append(')');
            return sb.toString();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Orientation.values().length];
            try {
                iArr[Orientation.Vertical.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Orientation.Horizontal.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ContentInViewNode(Orientation orientation, ScrollingLogic scrollingLogic, boolean z, BringIntoViewSpec bringIntoViewSpec) {
        this.orientation = orientation;
        this.scrollingLogic = scrollingLogic;
        this.reverseDirection = z;
        this.bringIntoViewSpec = bringIntoViewSpec;
        IntSize.Companion.getClass();
        this.viewportSize = 0L;
    }

    public static final float access$calculateScrollDelta(ContentInViewNode contentInViewNode, BringIntoViewSpec bringIntoViewSpec) {
        long j;
        Rect rect;
        int compare;
        long j2 = contentInViewNode.viewportSize;
        IntSize.Companion.getClass();
        if (IntSize.m861equalsimpl0(j2, 0L)) {
            return 0.0f;
        }
        MutableVector mutableVector = contentInViewNode.bringIntoViewRequests.requests;
        int i = mutableVector.size - 1;
        Object[] objArr = mutableVector.content;
        if (i < objArr.length) {
            rect = null;
            while (true) {
                if (i < 0) {
                    j = 4294967295L;
                    break;
                }
                Rect rect2 = (Rect) ((Request) objArr[i]).currentBounds.invoke();
                if (rect2 != null) {
                    long m408getSizeNHjbRc = rect2.m408getSizeNHjbRc();
                    long m864toSizeozmzZPI = IntSizeKt.m864toSizeozmzZPI(contentInViewNode.viewportSize);
                    j = 4294967295L;
                    int i2 = WhenMappings.$EnumSwitchMapping$0[contentInViewNode.orientation.ordinal()];
                    if (i2 == 1) {
                        compare = Float.compare(Float.intBitsToFloat((int) (m408getSizeNHjbRc & 4294967295L)), Float.intBitsToFloat((int) (m864toSizeozmzZPI & 4294967295L)));
                    } else {
                        if (i2 != 2) {
                            throw new NoWhenBranchMatchedException();
                        }
                        compare = Float.compare(Float.intBitsToFloat((int) (m408getSizeNHjbRc >> 32)), Float.intBitsToFloat((int) (m864toSizeozmzZPI >> 32)));
                    }
                    if (compare <= 0) {
                        rect = rect2;
                    } else if (rect == null) {
                        rect = rect2;
                    }
                }
                i--;
            }
        } else {
            j = 4294967295L;
            rect = null;
        }
        if (rect == null) {
            Rect focusedChildBounds = contentInViewNode.trackingFocusedChild ? contentInViewNode.getFocusedChildBounds() : null;
            if (focusedChildBounds == null) {
                return 0.0f;
            }
            rect = focusedChildBounds;
        }
        long m864toSizeozmzZPI2 = IntSizeKt.m864toSizeozmzZPI(contentInViewNode.viewportSize);
        int i3 = WhenMappings.$EnumSwitchMapping$0[contentInViewNode.orientation.ordinal()];
        if (i3 == 1) {
            float f = rect.bottom;
            float f2 = rect.top;
            return bringIntoViewSpec.calculateScrollDistance(f2, f - f2, Float.intBitsToFloat((int) (m864toSizeozmzZPI2 & j)));
        }
        if (i3 != 2) {
            throw new NoWhenBranchMatchedException();
        }
        float f3 = rect.right;
        float f4 = rect.left;
        return bringIntoViewSpec.calculateScrollDistance(f4, f3 - f4, Float.intBitsToFloat((int) (m864toSizeozmzZPI2 >> 32)));
    }

    public final Object bringChildIntoView(Function0 function0, Continuation continuation) {
        Rect rect = (Rect) function0.invoke();
        if (rect == null || m64isMaxVisibleO0kMr_c(rect, this.viewportSize)) {
            return Unit.INSTANCE;
        }
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final Request request = new Request(function0, cancellableContinuationImpl);
        final BringIntoViewRequestPriorityQueue bringIntoViewRequestPriorityQueue = this.bringIntoViewRequests;
        bringIntoViewRequestPriorityQueue.getClass();
        Rect rect2 = (Rect) request.currentBounds.invoke();
        CancellableContinuation cancellableContinuation = request.continuation;
        if (rect2 == null) {
            int i = Result.$r8$clinit;
            cancellableContinuation.resumeWith(Unit.INSTANCE);
        } else {
            cancellableContinuation.invokeOnCancellation(new Function1() { // from class: androidx.compose.foundation.gestures.BringIntoViewRequestPriorityQueue$enqueue$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    BringIntoViewRequestPriorityQueue.this.requests.remove(request);
                    return Unit.INSTANCE;
                }
            });
            MutableVector mutableVector = bringIntoViewRequestPriorityQueue.requests;
            IntRange until = RangesKt___RangesKt.until(0, mutableVector.size);
            int i2 = until.first;
            int i3 = until.last;
            if (i2 <= i3) {
                while (true) {
                    Rect rect3 = (Rect) ((Request) mutableVector.content[i3]).currentBounds.invoke();
                    if (rect3 != null) {
                        Rect intersect = rect2.intersect(rect3);
                        if (intersect.equals(rect2)) {
                            mutableVector.add(i3 + 1, request);
                            break;
                        }
                        if (!intersect.equals(rect3)) {
                            CancellationException cancellationException = new CancellationException("bringIntoView call interrupted by a newer, non-overlapping call");
                            int i4 = mutableVector.size - 1;
                            if (i4 <= i3) {
                                while (true) {
                                    ((Request) mutableVector.content[i3]).continuation.cancel(cancellationException);
                                    if (i4 == i3) {
                                        break;
                                    }
                                    i4++;
                                }
                            }
                        }
                    }
                    if (i3 == i2) {
                        break;
                    }
                    i3--;
                }
            }
            mutableVector.add(0, request);
            if (!this.isAnimationRunning) {
                launchAnimation();
            }
        }
        Object result = cancellableContinuationImpl.getResult();
        return result == CoroutineSingletons.COROUTINE_SUSPENDED ? result : Unit.INSTANCE;
    }

    public final Rect getFocusedChildBounds() {
        if (this.isAttached) {
            NodeCoordinator requireLayoutCoordinates = DelegatableNodeKt.requireLayoutCoordinates(this);
            LayoutCoordinates layoutCoordinates = this.focusedChild;
            if (layoutCoordinates != null) {
                if (!layoutCoordinates.isAttached()) {
                    layoutCoordinates = null;
                }
                if (layoutCoordinates != null) {
                    return requireLayoutCoordinates.localBoundingBoxOf(layoutCoordinates, false);
                }
            }
        }
        return null;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final boolean getShouldAutoInvalidate() {
        return false;
    }

    /* renamed from: isMaxVisible-O0kMr_c, reason: not valid java name */
    public final boolean m64isMaxVisibleO0kMr_c(Rect rect, long j) {
        long m65relocationOffsetBMxPBkI = m65relocationOffsetBMxPBkI(rect, j);
        return Math.abs(Float.intBitsToFloat((int) (m65relocationOffsetBMxPBkI >> 32))) <= 0.5f && Math.abs(Float.intBitsToFloat((int) (m65relocationOffsetBMxPBkI & 4294967295L))) <= 0.5f;
    }

    public final void launchAnimation() {
        BringIntoViewSpec bringIntoViewSpec = this.bringIntoViewSpec;
        if (bringIntoViewSpec == null) {
            bringIntoViewSpec = (BringIntoViewSpec) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, BringIntoViewSpec_androidKt.LocalBringIntoViewSpec);
        }
        if (this.isAnimationRunning) {
            InlineClassHelperKt.throwIllegalStateException("launchAnimation called when previous animation was running");
        }
        BringIntoViewSpec.Companion.getClass();
        BuildersKt.launch$default(getCoroutineScope(), null, CoroutineStart.UNDISPATCHED, new ContentInViewNode$launchAnimation$2(this, new UpdatableAnimationState(BringIntoViewSpec.Companion.DefaultScrollAnimationSpec), bringIntoViewSpec, null), 1);
    }

    @Override // androidx.compose.ui.node.LayoutAwareModifierNode
    /* renamed from: onRemeasured-ozmzZPI */
    public final void mo49onRemeasuredozmzZPI(long j) {
        int compare;
        Rect focusedChildBounds;
        long j2 = this.viewportSize;
        this.viewportSize = j;
        int i = WhenMappings.$EnumSwitchMapping$0[this.orientation.ordinal()];
        if (i == 1) {
            compare = Intrinsics.compare((int) (j & 4294967295L), (int) (4294967295L & j2));
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            compare = Intrinsics.compare((int) (j >> 32), (int) (j2 >> 32));
        }
        if (compare >= 0 || this.isAnimationRunning || this.trackingFocusedChild || (focusedChildBounds = getFocusedChildBounds()) == null || !m64isMaxVisibleO0kMr_c(focusedChildBounds, j2)) {
            return;
        }
        this.childWasMaxVisibleBeforeViewportShrunk = true;
    }

    /* renamed from: relocationOffset-BMxPBkI, reason: not valid java name */
    public final long m65relocationOffsetBMxPBkI(Rect rect, long j) {
        long m864toSizeozmzZPI = IntSizeKt.m864toSizeozmzZPI(j);
        int i = WhenMappings.$EnumSwitchMapping$0[this.orientation.ordinal()];
        if (i != 1) {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            BringIntoViewSpec bringIntoViewSpec = this.bringIntoViewSpec;
            if (bringIntoViewSpec == null) {
                bringIntoViewSpec = (BringIntoViewSpec) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, BringIntoViewSpec_androidKt.LocalBringIntoViewSpec);
            }
            float f = rect.right;
            float f2 = rect.left;
            long floatToRawIntBits = (Float.floatToRawIntBits(bringIntoViewSpec.calculateScrollDistance(f2, f - f2, Float.intBitsToFloat((int) (m864toSizeozmzZPI >> 32)))) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
            Offset.Companion companion = Offset.Companion;
            return floatToRawIntBits;
        }
        BringIntoViewSpec bringIntoViewSpec2 = this.bringIntoViewSpec;
        if (bringIntoViewSpec2 == null) {
            bringIntoViewSpec2 = (BringIntoViewSpec) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, BringIntoViewSpec_androidKt.LocalBringIntoViewSpec);
        }
        float f3 = rect.bottom;
        float f4 = rect.top;
        float calculateScrollDistance = bringIntoViewSpec2.calculateScrollDistance(f4, f3 - f4, Float.intBitsToFloat((int) (m864toSizeozmzZPI & 4294967295L)));
        long floatToRawIntBits2 = (Float.floatToRawIntBits(0.0f) << 32) | (Float.floatToRawIntBits(calculateScrollDistance) & 4294967295L);
        Offset.Companion companion2 = Offset.Companion;
        return floatToRawIntBits2;
    }
}
