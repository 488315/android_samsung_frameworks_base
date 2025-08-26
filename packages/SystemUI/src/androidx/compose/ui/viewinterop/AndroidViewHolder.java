package androidx.compose.ui.viewinterop;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.compose.runtime.ComposeNodeLifecycleCallback;
import androidx.compose.runtime.CompositionContext;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.focus.FocusOwnerImpl;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidCanvas_androidKt;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher;
import androidx.compose.ui.input.nestedscroll.NestedScrollModifierKt;
import androidx.compose.ui.input.pointer.PointerInteropFilter_androidKt;
import androidx.compose.ui.input.pointer.RequestDisallowInterceptTouchEvent;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.IntrinsicMeasureScope;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.OnGloballyPositionedModifierKt;
import androidx.compose.ui.node.InnerNodeCoordinator;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.Owner;
import androidx.compose.ui.node.OwnerScope;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.platform.NestedScrollInteropConnectionKt;
import androidx.compose.ui.platform.WindowRecomposer_androidKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntOffsetKt;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.Velocity;
import androidx.compose.ui.unit.VelocityKt;
import androidx.compose.ui.viewinterop.AndroidViewHolder;
import androidx.core.graphics.Insets;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsAnimationCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.savedstate.SavedStateRegistryOwner;
import com.android.systemui.R;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public class AndroidViewHolder extends ViewGroup implements NestedScrollingParent3, ComposeNodeLifecycleCallback, OwnerScope, OnApplyWindowInsetsListener {
    public static final Function1 OnCommitAffectingUpdate;
    public Density density;
    public final NestedScrollDispatcher dispatcher;
    public boolean hasUpdateBlock;
    public WindowInsetsCompat insets;
    public boolean isDrawing;
    public int lastHeightMeasureSpec;
    public int lastWidthMeasureSpec;
    public final LayoutNode layoutNode;
    public LifecycleOwner lifecycleOwner;
    public final int[] location;
    public Modifier modifier;
    public final NestedScrollingParentHelper nestedScrollingParentHelper;
    public final Function1 onDensityChanged;
    public final Function1 onModifierChanged;
    public RequestDisallowInterceptTouchEvent onRequestDisallowInterceptTouchEvent;
    public final Owner owner;
    public final int[] position;
    public Lambda release;
    public Lambda reset;
    public final Function0 runInvalidate;
    public final Function0 runUpdate;
    public SavedStateRegistryOwner savedStateRegistryOwner;
    public long size;
    public Lambda update;
    public final View view;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: androidx.compose.ui.viewinterop.AndroidViewHolder$onNestedFling$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $consumed;
        final /* synthetic */ long $viewVelocity;
        int label;
        final /* synthetic */ AndroidViewHolder this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(boolean z, AndroidViewHolder androidViewHolder, long j, Continuation continuation) {
            super(2, continuation);
            this.$consumed = z;
            this.this$0 = androidViewHolder;
            this.$viewVelocity = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$consumed, this.this$0, this.$viewVelocity, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0034, code lost:
        
            if (r4.m582dispatchPostFlingRZ2iAVY(0, r7, r10) == r0) goto L17;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x004c, code lost:
        
            if (r1.m582dispatchPostFlingRZ2iAVY(r2, 0, r10) == r0) goto L17;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x004e, code lost:
        
            return r0;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (this.$consumed) {
                    NestedScrollDispatcher nestedScrollDispatcher = this.this$0.dispatcher;
                    long j = this.$viewVelocity;
                    Velocity.Companion.getClass();
                    this.label = 2;
                } else {
                    NestedScrollDispatcher nestedScrollDispatcher2 = this.this$0.dispatcher;
                    Velocity.Companion.getClass();
                    long j2 = this.$viewVelocity;
                    this.label = 1;
                }
            } else {
                if (i != 1 && i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: androidx.compose.ui.viewinterop.AndroidViewHolder$onNestedPreFling$1, reason: invalid class name and case insensitive filesystem */
    final class C07581 extends SuspendLambda implements Function2 {
        final /* synthetic */ long $toBeConsumed;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07581(long j, Continuation continuation) {
            super(2, continuation);
            this.$toBeConsumed = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AndroidViewHolder.this.new C07581(this.$toBeConsumed, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07581) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                NestedScrollDispatcher nestedScrollDispatcher = AndroidViewHolder.this.dispatcher;
                long j = this.$toBeConsumed;
                this.label = 1;
                if (nestedScrollDispatcher.m584dispatchPreFlingQWom1Mo(j, this) == coroutineSingletons) {
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

    static {
        new Companion(null);
        OnCommitAffectingUpdate = new Function1() { // from class: androidx.compose.ui.viewinterop.AndroidViewHolder$Companion$OnCommitAffectingUpdate$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                AndroidViewHolder androidViewHolder = (AndroidViewHolder) obj;
                androidViewHolder.getHandler().post(new AndroidViewHolder$$ExternalSyntheticLambda0(1, androidViewHolder.runUpdate));
                return Unit.INSTANCE;
            }
        };
    }

    public AndroidViewHolder(Context context, CompositionContext compositionContext, int i, NestedScrollDispatcher nestedScrollDispatcher, View view, Owner owner) {
        super(context);
        this.dispatcher = nestedScrollDispatcher;
        this.view = view;
        this.owner = owner;
        if (compositionContext != null) {
            Map map = WindowRecomposer_androidKt.animationScale;
            setTag(R.id.androidx_compose_ui_view_composition_context, compositionContext);
        }
        setSaveFromParentEnabled(false);
        addView(view);
        ViewCompat.setWindowInsetsAnimationCallback(this, new WindowInsetsAnimationCompat.Callback() { // from class: androidx.compose.ui.viewinterop.AndroidViewHolder.2
            @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
            public final WindowInsetsCompat onProgress(WindowInsetsCompat windowInsetsCompat, List list) {
                Function1 function1 = AndroidViewHolder.OnCommitAffectingUpdate;
                return AndroidViewHolder.this.insetToLayoutPosition(windowInsetsCompat);
            }

            @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
            public final WindowInsetsAnimationCompat.BoundsCompat onStart(WindowInsetsAnimationCompat windowInsetsAnimationCompat, WindowInsetsAnimationCompat.BoundsCompat boundsCompat) {
                InnerNodeCoordinator innerNodeCoordinator = AndroidViewHolder.this.layoutNode.nodes.innerCoordinator;
                if (innerNodeCoordinator.tail.isAttached) {
                    long jM856roundk4lQ0M = IntOffsetKt.m856roundk4lQ0M(LayoutCoordinatesKt.positionInRoot(innerNodeCoordinator));
                    IntOffset.Companion companion = IntOffset.Companion;
                    int i2 = (int) (jM856roundk4lQ0M >> 32);
                    if (i2 < 0) {
                        i2 = 0;
                    }
                    int i3 = (int) (jM856roundk4lQ0M & 4294967295L);
                    if (i3 < 0) {
                        i3 = 0;
                    }
                    long jMo612getSizeYbymL2g = LayoutCoordinatesKt.findRootCoordinates(innerNodeCoordinator).mo612getSizeYbymL2g();
                    int i4 = (int) (jMo612getSizeYbymL2g >> 32);
                    int i5 = (int) (jMo612getSizeYbymL2g & 4294967295L);
                    long j = innerNodeCoordinator.measuredSize;
                    long jM856roundk4lQ0M2 = IntOffsetKt.m856roundk4lQ0M(innerNodeCoordinator.mo615localToRootMKHz9U((Float.floatToRawIntBits((int) (j >> 32)) << 32) | (Float.floatToRawIntBits((int) (j & 4294967295L)) & 4294967295L)));
                    int i6 = i4 - ((int) (jM856roundk4lQ0M2 >> 32));
                    if (i6 < 0) {
                        i6 = 0;
                    }
                    int i7 = i5 - ((int) (jM856roundk4lQ0M2 & 4294967295L));
                    int i8 = i7 >= 0 ? i7 : 0;
                    if (i2 != 0 || i3 != 0 || i6 != 0 || i8 != 0) {
                        return new WindowInsetsAnimationCompat.BoundsCompat(AndroidViewHolder.inset(boundsCompat.mLowerBound, i2, i3, i6, i8), AndroidViewHolder.inset(boundsCompat.mUpperBound, i2, i3, i6, i8));
                    }
                }
                return boundsCompat;
            }
        });
        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(this, this);
        this.update = new Function0() { // from class: androidx.compose.ui.viewinterop.AndroidViewHolder$update$1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        };
        this.reset = new Function0() { // from class: androidx.compose.ui.viewinterop.AndroidViewHolder$reset$1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        };
        this.release = new Function0() { // from class: androidx.compose.ui.viewinterop.AndroidViewHolder$release$1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        };
        Modifier.Companion companion = Modifier.Companion;
        this.modifier = companion;
        this.density = DensityKt.Density$default(1.0f);
        this.position = new int[2];
        IntSize.Companion.getClass();
        this.size = 0L;
        this.runUpdate = new AndroidViewHolder$runUpdate$1(this);
        this.runInvalidate = new Function0() { // from class: androidx.compose.ui.viewinterop.AndroidViewHolder$runInvalidate$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                this.this$0.layoutNode.invalidateLayer$ui_release();
                return Unit.INSTANCE;
            }
        };
        this.location = new int[2];
        this.lastWidthMeasureSpec = Integer.MIN_VALUE;
        this.lastHeightMeasureSpec = Integer.MIN_VALUE;
        this.nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        final LayoutNode layoutNode = new LayoutNode(false, 0, 3, null);
        layoutNode.forceUseOldLayers = true;
        layoutNode.interopViewFactoryHolder = this;
        Modifier modifierOnGloballyPositioned = OnGloballyPositionedModifierKt.onGloballyPositioned(DrawModifierKt.drawBehind(GraphicsLayerModifierKt.m479graphicsLayer_6ThJ44$default(PointerInteropFilter_androidKt.pointerInteropFilter(SemanticsModifierKt.semantics(NestedScrollModifierKt.nestedScroll(companion, AndroidViewHolder_androidKt.NoOpScrollConnection, nestedScrollDispatcher), true, new Function1() { // from class: androidx.compose.ui.viewinterop.AndroidViewHolder$layoutNode$1$coreModifier$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                return Unit.INSTANCE;
            }
        }), this), 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, null, false, 0, 524287), new Function1() { // from class: androidx.compose.ui.viewinterop.AndroidViewHolder$layoutNode$1$coreModifier$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                AndroidViewHolder androidViewHolder = this.$this_run;
                LayoutNode layoutNode2 = layoutNode;
                AndroidViewHolder androidViewHolder2 = this;
                Canvas canvas = ((DrawScope) obj).getDrawContext().getCanvas();
                if (androidViewHolder.view.getVisibility() != 8) {
                    androidViewHolder.isDrawing = true;
                    AndroidComposeView androidComposeView = layoutNode2.owner;
                    if (androidComposeView == null) {
                        androidComposeView = null;
                    }
                    if (androidComposeView != null) {
                        android.graphics.Canvas canvas2 = AndroidCanvas_androidKt.EmptyCanvas;
                        android.graphics.Canvas canvas3 = ((AndroidCanvas) canvas).internalCanvas;
                        androidComposeView.getAndroidViewsHandler$ui_release();
                        androidViewHolder2.draw(canvas3);
                    }
                    androidViewHolder.isDrawing = false;
                }
                return Unit.INSTANCE;
            }
        }), new Function1() { // from class: androidx.compose.ui.viewinterop.AndroidViewHolder$layoutNode$1$coreModifier$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                WindowInsets windowInsets;
                AndroidViewHolder_androidKt.access$layoutAccordingTo(this.$this_run, layoutNode);
                AndroidViewHolder androidViewHolder = this.$this_run;
                ((AndroidComposeView) androidViewHolder.owner).isPendingInteropViewLayoutChangeDispatch = true;
                int[] iArr = androidViewHolder.position;
                int i2 = iArr[0];
                int i3 = iArr[1];
                androidViewHolder.view.getLocationOnScreen(iArr);
                AndroidViewHolder androidViewHolder2 = this.$this_run;
                long j = androidViewHolder2.size;
                androidViewHolder2.size = ((LayoutCoordinates) obj).mo612getSizeYbymL2g();
                AndroidViewHolder androidViewHolder3 = this.$this_run;
                WindowInsetsCompat windowInsetsCompat = androidViewHolder3.insets;
                if (windowInsetsCompat != null) {
                    int[] iArr2 = androidViewHolder3.position;
                    if ((i2 != iArr2[0] || i3 != iArr2[1] || !IntSize.m863equalsimpl0(j, androidViewHolder3.size)) && (windowInsets = this.$this_run.insetToLayoutPosition(windowInsetsCompat).toWindowInsets()) != null) {
                        this.$this_run.view.dispatchApplyWindowInsets(windowInsets);
                    }
                }
                return Unit.INSTANCE;
            }
        });
        layoutNode.setModifier(this.modifier.then(modifierOnGloballyPositioned));
        this.onModifierChanged = new AndroidViewHolder$layoutNode$1$1(layoutNode, modifierOnGloballyPositioned);
        layoutNode.setDensity$1(this.density);
        this.onDensityChanged = new AndroidViewHolder$layoutNode$1$2(layoutNode);
        layoutNode.onAttach = new Function1() { // from class: androidx.compose.ui.viewinterop.AndroidViewHolder$layoutNode$1$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Owner owner2 = (Owner) obj;
                final AndroidComposeView androidComposeView = owner2 instanceof AndroidComposeView ? (AndroidComposeView) owner2 : null;
                if (androidComposeView != null) {
                    AndroidViewHolder androidViewHolder = this.$this_run;
                    final LayoutNode layoutNode2 = layoutNode;
                    androidComposeView.getAndroidViewsHandler$ui_release().holderToLayoutNode.put(androidViewHolder, layoutNode2);
                    androidComposeView.getAndroidViewsHandler$ui_release().addView(androidViewHolder);
                    androidComposeView.getAndroidViewsHandler$ui_release().layoutNodeToHolder.put(layoutNode2, androidViewHolder);
                    androidViewHolder.setImportantForAccessibility(1);
                    ViewCompat.setAccessibilityDelegate(androidViewHolder, new AccessibilityDelegateCompat() { // from class: androidx.compose.ui.platform.AndroidComposeView$addAndroidView$1
                        /* JADX WARN: Removed duplicated region for block: B:19:0x004a  */
                        @Override // androidx.core.view.AccessibilityDelegateCompat
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat.mInfo);
                            AndroidComposeView androidComposeView2 = androidComposeView;
                            if (androidComposeView2.composeAccessibilityDelegate.isEnabled$ui_release()) {
                                accessibilityNodeInfoCompat.mInfo.setVisibleToUser(false);
                            }
                            LayoutNode layoutNode3 = layoutNode2;
                            LayoutNode parent$ui_release = layoutNode3.getParent$ui_release();
                            while (true) {
                                if (parent$ui_release == null) {
                                    parent$ui_release = null;
                                    break;
                                } else if (parent$ui_release.nodes.m665hasH91voCI$ui_release(8)) {
                                    break;
                                } else {
                                    parent$ui_release = parent$ui_release.getParent$ui_release();
                                }
                            }
                            Integer numValueOf = parent$ui_release != null ? Integer.valueOf(parent$ui_release.semanticsId) : null;
                            if (numValueOf != null) {
                                if (numValueOf.intValue() == androidComposeView2.semanticsOwner.getUnmergedRootSemanticsNode().id) {
                                    numValueOf = -1;
                                }
                            }
                            int iIntValue = numValueOf.intValue();
                            accessibilityNodeInfoCompat.mParentVirtualDescendantId = iIntValue;
                            AccessibilityNodeInfo accessibilityNodeInfo = accessibilityNodeInfoCompat.mInfo;
                            AndroidComposeView androidComposeView3 = androidComposeView;
                            accessibilityNodeInfo.setParent(androidComposeView3, iIntValue);
                            int i2 = layoutNode3.semanticsId;
                            int orDefault = androidComposeView2.composeAccessibilityDelegate.idToBeforeMap.getOrDefault(i2);
                            if (orDefault != -1) {
                                AndroidViewHolder androidViewHolderSemanticsIdToView = SemanticsUtils_androidKt.semanticsIdToView(androidComposeView2.getAndroidViewsHandler$ui_release(), orDefault);
                                if (androidViewHolderSemanticsIdToView != null) {
                                    accessibilityNodeInfoCompat.mInfo.setTraversalBefore(androidViewHolderSemanticsIdToView);
                                } else {
                                    accessibilityNodeInfoCompat.mInfo.setTraversalBefore(androidComposeView3, orDefault);
                                }
                                AndroidComposeView.access$addExtraDataToAccessibilityNodeInfoHelper(androidComposeView2, i2, accessibilityNodeInfoCompat.mInfo, androidComposeView2.composeAccessibilityDelegate.ExtraDataTestTraversalBeforeVal);
                            }
                            int orDefault2 = androidComposeView2.composeAccessibilityDelegate.idToAfterMap.getOrDefault(i2);
                            if (orDefault2 != -1) {
                                AndroidViewHolder androidViewHolderSemanticsIdToView2 = SemanticsUtils_androidKt.semanticsIdToView(androidComposeView2.getAndroidViewsHandler$ui_release(), orDefault2);
                                if (androidViewHolderSemanticsIdToView2 != null) {
                                    accessibilityNodeInfoCompat.mInfo.setTraversalAfter(androidViewHolderSemanticsIdToView2);
                                } else {
                                    accessibilityNodeInfoCompat.mInfo.setTraversalAfter(androidComposeView3, orDefault2);
                                }
                                AndroidComposeView.access$addExtraDataToAccessibilityNodeInfoHelper(androidComposeView2, i2, accessibilityNodeInfoCompat.mInfo, androidComposeView2.composeAccessibilityDelegate.ExtraDataTestTraversalAfterVal);
                            }
                        }
                    });
                }
                ViewParent parent = this.$this_run.view.getParent();
                AndroidViewHolder androidViewHolder2 = this.$this_run;
                if (parent != androidViewHolder2) {
                    androidViewHolder2.addView(androidViewHolder2.view);
                }
                return Unit.INSTANCE;
            }
        };
        layoutNode.onDetach = new Function1() { // from class: androidx.compose.ui.viewinterop.AndroidViewHolder$layoutNode$1$4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Owner owner2 = (Owner) obj;
                if (ComposeUiFlags.isViewFocusFixEnabled && this.$this_run.hasFocus()) {
                    FocusOwnerImpl focusOwnerImpl = ((AndroidComposeView) owner2).focusOwner;
                    focusOwnerImpl.getClass();
                    FocusDirection.Companion.getClass();
                    focusOwnerImpl.m372clearFocusI7lrPNg(FocusDirection.Exit, true, true);
                }
                AndroidComposeView androidComposeView = owner2 instanceof AndroidComposeView ? (AndroidComposeView) owner2 : null;
                if (androidComposeView != null) {
                    AndroidViewHolder androidViewHolder = this.$this_run;
                    androidComposeView.getAndroidViewsHandler$ui_release().removeViewInLayout(androidViewHolder);
                    androidComposeView.getAndroidViewsHandler$ui_release().layoutNodeToHolder.remove(androidComposeView.getAndroidViewsHandler$ui_release().holderToLayoutNode.remove(androidViewHolder));
                    androidViewHolder.setImportantForAccessibility(0);
                }
                this.$this_run.removeAllViewsInLayout();
                return Unit.INSTANCE;
            }
        };
        layoutNode.setMeasurePolicy(new MeasurePolicy() { // from class: androidx.compose.ui.viewinterop.AndroidViewHolder$layoutNode$1$5
            @Override // androidx.compose.ui.layout.MeasurePolicy
            public final int maxIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i2) {
                AndroidViewHolder androidViewHolder = this.$this_run;
                androidViewHolder.measure(AndroidViewHolder.access$obtainMeasureSpec(androidViewHolder, 0, i2, androidViewHolder.getLayoutParams().width), View.MeasureSpec.makeMeasureSpec(0, 0));
                return androidViewHolder.getMeasuredHeight();
            }

            @Override // androidx.compose.ui.layout.MeasurePolicy
            public final int maxIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i2) {
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                AndroidViewHolder androidViewHolder = this.$this_run;
                androidViewHolder.measure(iMakeMeasureSpec, AndroidViewHolder.access$obtainMeasureSpec(androidViewHolder, 0, i2, androidViewHolder.getLayoutParams().height));
                return androidViewHolder.getMeasuredWidth();
            }

            @Override // androidx.compose.ui.layout.MeasurePolicy
            /* renamed from: measure-3p2s80s */
            public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
                final AndroidViewHolder androidViewHolder = this.$this_run;
                if (androidViewHolder.getChildCount() == 0) {
                    return measureScope.layout$1(Constraints.m825getMinWidthimpl(j), Constraints.m824getMinHeightimpl(j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.viewinterop.AndroidViewHolder$layoutNode$1$5$measure$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                            return Unit.INSTANCE;
                        }
                    });
                }
                if (Constraints.m825getMinWidthimpl(j) != 0) {
                    androidViewHolder.getChildAt(0).setMinimumWidth(Constraints.m825getMinWidthimpl(j));
                }
                if (Constraints.m824getMinHeightimpl(j) != 0) {
                    androidViewHolder.getChildAt(0).setMinimumHeight(Constraints.m824getMinHeightimpl(j));
                }
                androidViewHolder.measure(AndroidViewHolder.access$obtainMeasureSpec(androidViewHolder, Constraints.m825getMinWidthimpl(j), Constraints.m823getMaxWidthimpl(j), androidViewHolder.getLayoutParams().width), AndroidViewHolder.access$obtainMeasureSpec(androidViewHolder, Constraints.m824getMinHeightimpl(j), Constraints.m822getMaxHeightimpl(j), androidViewHolder.getLayoutParams().height));
                int measuredWidth = androidViewHolder.getMeasuredWidth();
                int measuredHeight = androidViewHolder.getMeasuredHeight();
                final LayoutNode layoutNode2 = layoutNode;
                return measureScope.layout$1(measuredWidth, measuredHeight, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.ui.viewinterop.AndroidViewHolder$layoutNode$1$5$measure$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        AndroidViewHolder_androidKt.access$layoutAccordingTo(androidViewHolder, layoutNode2);
                        return Unit.INSTANCE;
                    }
                });
            }

            @Override // androidx.compose.ui.layout.MeasurePolicy
            public final int minIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i2) {
                AndroidViewHolder androidViewHolder = this.$this_run;
                androidViewHolder.measure(AndroidViewHolder.access$obtainMeasureSpec(androidViewHolder, 0, i2, androidViewHolder.getLayoutParams().width), View.MeasureSpec.makeMeasureSpec(0, 0));
                return androidViewHolder.getMeasuredHeight();
            }

            @Override // androidx.compose.ui.layout.MeasurePolicy
            public final int minIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i2) {
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                AndroidViewHolder androidViewHolder = this.$this_run;
                androidViewHolder.measure(iMakeMeasureSpec, AndroidViewHolder.access$obtainMeasureSpec(androidViewHolder, 0, i2, androidViewHolder.getLayoutParams().height));
                return androidViewHolder.getMeasuredWidth();
            }
        });
        this.layoutNode = layoutNode;
    }

    public static final int access$obtainMeasureSpec(AndroidViewHolder androidViewHolder, int i, int i2, int i3) {
        androidViewHolder.getClass();
        return (i3 >= 0 || i == i2) ? View.MeasureSpec.makeMeasureSpec(RangesKt___RangesKt.coerceIn(i3, i, i2), 1073741824) : (i3 != -2 || i2 == Integer.MAX_VALUE) ? (i3 != -1 || i2 == Integer.MAX_VALUE) ? View.MeasureSpec.makeMeasureSpec(0, 0) : View.MeasureSpec.makeMeasureSpec(i2, 1073741824) : View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE);
    }

    public static Insets inset(Insets insets, int i, int i2, int i3, int i4) {
        int i5 = insets.left - i;
        if (i5 < 0) {
            i5 = 0;
        }
        int i6 = insets.top - i2;
        if (i6 < 0) {
            i6 = 0;
        }
        int i7 = insets.right - i3;
        if (i7 < 0) {
            i7 = 0;
        }
        int i8 = insets.bottom - i4;
        return Insets.of(i5, i6, i7, i8 >= 0 ? i8 : 0);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean gatherTransparentRegion(Region region) {
        if (region == null) {
            return true;
        }
        getLocationInWindow(this.location);
        int[] iArr = this.location;
        int i = iArr[0];
        region.op(i, iArr[1], getWidth() + i, getHeight() + this.location[1], Region.Op.DIFFERENCE);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return getClass().getName();
    }

    @Override // android.view.View
    public final ViewGroup.LayoutParams getLayoutParams() {
        ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
        return layoutParams == null ? new ViewGroup.LayoutParams(-1, -1) : layoutParams;
    }

    @Override // android.view.ViewGroup
    public final int getNestedScrollAxes() {
        NestedScrollingParentHelper nestedScrollingParentHelper = this.nestedScrollingParentHelper;
        return nestedScrollingParentHelper.mNestedScrollAxesNonTouch | nestedScrollingParentHelper.mNestedScrollAxesTouch;
    }

    public final WindowInsetsCompat insetToLayoutPosition(WindowInsetsCompat windowInsetsCompat) {
        WindowInsetsCompat.Impl impl = windowInsetsCompat.mImpl;
        Insets insets = impl.getInsets(-1);
        Insets insets2 = Insets.NONE;
        if (!insets.equals(insets2) || !impl.getInsetsIgnoringVisibility(-9).equals(insets2) || impl.getDisplayCutout() != null) {
            InnerNodeCoordinator innerNodeCoordinator = this.layoutNode.nodes.innerCoordinator;
            if (innerNodeCoordinator.tail.isAttached) {
                long jM856roundk4lQ0M = IntOffsetKt.m856roundk4lQ0M(LayoutCoordinatesKt.positionInRoot(innerNodeCoordinator));
                IntOffset.Companion companion = IntOffset.Companion;
                int i = (int) (jM856roundk4lQ0M >> 32);
                if (i < 0) {
                    i = 0;
                }
                int i2 = (int) (jM856roundk4lQ0M & 4294967295L);
                if (i2 < 0) {
                    i2 = 0;
                }
                long jMo612getSizeYbymL2g = LayoutCoordinatesKt.findRootCoordinates(innerNodeCoordinator).mo612getSizeYbymL2g();
                int i3 = (int) (jMo612getSizeYbymL2g >> 32);
                int i4 = (int) (jMo612getSizeYbymL2g & 4294967295L);
                long j = innerNodeCoordinator.measuredSize;
                long jM856roundk4lQ0M2 = IntOffsetKt.m856roundk4lQ0M(innerNodeCoordinator.mo615localToRootMKHz9U((Float.floatToRawIntBits((int) (j >> 32)) << 32) | (Float.floatToRawIntBits((int) (j & 4294967295L)) & 4294967295L)));
                int i5 = i3 - ((int) (jM856roundk4lQ0M2 >> 32));
                if (i5 < 0) {
                    i5 = 0;
                }
                int i6 = i4 - ((int) (4294967295L & jM856roundk4lQ0M2));
                int i7 = i6 >= 0 ? i6 : 0;
                if (i != 0 || i2 != 0 || i5 != 0 || i7 != 0) {
                    return windowInsetsCompat.mImpl.inset(i, i2, i5, i7);
                }
            }
        }
        return windowInsetsCompat;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final ViewParent invalidateChildInParent(int[] iArr, Rect rect) {
        super.invalidateChildInParent(iArr, rect);
        if (this.isDrawing) {
            this.view.postOnAnimation(new AndroidViewHolder$$ExternalSyntheticLambda0(0, this.runInvalidate));
            return null;
        }
        this.layoutNode.invalidateLayer$ui_release();
        return null;
    }

    @Override // android.view.View
    public final boolean isNestedScrollingEnabled() {
        return this.view.isNestedScrollingEnabled();
    }

    @Override // androidx.compose.ui.node.OwnerScope
    public final boolean isValidOwnerScope() {
        return isAttachedToWindow();
    }

    @Override // androidx.core.view.OnApplyWindowInsetsListener
    public final WindowInsetsCompat onApplyWindowInsets(WindowInsetsCompat windowInsetsCompat, View view) {
        this.insets = new WindowInsetsCompat(windowInsetsCompat);
        return insetToLayoutPosition(windowInsetsCompat);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((AndroidViewHolder$runUpdate$1) this.runUpdate).invoke();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
    @Override // androidx.compose.runtime.ComposeNodeLifecycleCallback
    public final void onDeactivate() {
        this.reset.invoke();
        if (!hasFocus()) {
            removeAllViewsInLayout();
            return;
        }
        View viewFindFocus = findFocus();
        if (viewFindFocus == null) {
            removeAllViewsInLayout();
            return;
        }
        final View view = new View(getContext());
        Rect rect = new Rect(0, 0, viewFindFocus.getWidth(), viewFindFocus.getHeight());
        offsetDescendantRectToMyCoords(viewFindFocus, rect);
        addView(view);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setNextFocusUpId(viewFindFocus.getNextFocusUpId());
        view.setNextFocusDownId(viewFindFocus.getNextFocusDownId());
        view.setNextFocusLeftId(viewFindFocus.getNextFocusLeftId());
        view.setNextFocusRightId(viewFindFocus.getNextFocusRightId());
        view.setNextFocusForwardId(viewFindFocus.getNextFocusForwardId());
        view.layout(rect.left, rect.top, rect.right, rect.bottom);
        view.requestFocus();
        int childCount = getChildCount() - 1;
        for (int i = 0; i < childCount; i++) {
            removeViewAt(0);
        }
        ((AndroidComposeView) this.owner).registerOnEndApplyChangesListener(new Function0() { // from class: androidx.compose.ui.viewinterop.AndroidViewHolder.onDeactivate.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AndroidViewHolder.this.removeView(view);
                return Unit.INSTANCE;
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void onDescendantInvalidated(View view, View view2) {
        super.onDescendantInvalidated(view, view2);
        if (this.isDrawing) {
            this.view.postOnAnimation(new AndroidViewHolder$$ExternalSyntheticLambda0(0, this.runInvalidate));
        } else {
            this.layoutNode.invalidateLayer$ui_release();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!isAttachedToWindow()) {
            InlineClassHelperKt.throwIllegalStateException("Expected AndroidViewHolder to be attached when observing reads.");
        }
        ((AndroidComposeView) this.owner).snapshotObserver.observer.clear(this);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.view.layout(0, 0, i3 - i, i4 - i2);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        if (this.view.getParent() != this) {
            setMeasuredDimension(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize(i2));
            return;
        }
        if (this.view.getVisibility() == 8) {
            setMeasuredDimension(0, 0);
            return;
        }
        this.view.measure(i, i2);
        setMeasuredDimension(this.view.getMeasuredWidth(), this.view.getMeasuredHeight());
        this.lastWidthMeasureSpec = i;
        this.lastHeightMeasureSpec = i2;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedFling(View view, float f, float f2, boolean z) {
        if (!this.view.isNestedScrollingEnabled()) {
            return false;
        }
        AndroidViewHolder_androidKt$NoOpScrollConnection$1 androidViewHolder_androidKt$NoOpScrollConnection$1 = AndroidViewHolder_androidKt.NoOpScrollConnection;
        BuildersKt.launch$default(this.dispatcher.getCoroutineScope(), null, null, new AnonymousClass1(z, this, VelocityKt.Velocity(f * (-1.0f), f2 * (-1.0f)), null), 3);
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final boolean onNestedPreFling(View view, float f, float f2) {
        if (!this.view.isNestedScrollingEnabled()) {
            return false;
        }
        AndroidViewHolder_androidKt$NoOpScrollConnection$1 androidViewHolder_androidKt$NoOpScrollConnection$1 = AndroidViewHolder_androidKt.NoOpScrollConnection;
        BuildersKt.launch$default(this.dispatcher.getCoroutineScope(), null, null, new C07581(VelocityKt.Velocity(f * (-1.0f), f2 * (-1.0f)), null), 3);
        return false;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedPreScroll(View view, int i, int i2, int[] iArr, int i3) {
        if (this.view.isNestedScrollingEnabled()) {
            NestedScrollDispatcher nestedScrollDispatcher = this.dispatcher;
            AndroidViewHolder_androidKt$NoOpScrollConnection$1 androidViewHolder_androidKt$NoOpScrollConnection$1 = AndroidViewHolder_androidKt.NoOpScrollConnection;
            float f = i;
            float f2 = -1;
            Offset.Companion companion = Offset.Companion;
            long jM585dispatchPreScrollOzD1aCk = nestedScrollDispatcher.m585dispatchPreScrollOzD1aCk(AndroidViewHolder_androidKt.access$toNestedScrollSource(i3), (Float.floatToRawIntBits(f * f2) << 32) | (Float.floatToRawIntBits(i2 * f2) & 4294967295L));
            iArr[0] = NestedScrollInteropConnectionKt.composeToViewOffset(Float.intBitsToFloat((int) (jM585dispatchPreScrollOzD1aCk >> 32)));
            iArr[1] = NestedScrollInteropConnectionKt.composeToViewOffset(Float.intBitsToFloat((int) (jM585dispatchPreScrollOzD1aCk & 4294967295L)));
        }
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5) {
        if (this.view.isNestedScrollingEnabled()) {
            NestedScrollDispatcher nestedScrollDispatcher = this.dispatcher;
            AndroidViewHolder_androidKt$NoOpScrollConnection$1 androidViewHolder_androidKt$NoOpScrollConnection$1 = AndroidViewHolder_androidKt.NoOpScrollConnection;
            float f = -1;
            long jFloatToRawIntBits = (Float.floatToRawIntBits(i2 * f) & 4294967295L) | (Float.floatToRawIntBits(i * f) << 32);
            Offset.Companion companion = Offset.Companion;
            nestedScrollDispatcher.m583dispatchPostScrollDzOQY0M(AndroidViewHolder_androidKt.access$toNestedScrollSource(i5), jFloatToRawIntBits, (Float.floatToRawIntBits(i3 * f) << 32) | (Float.floatToRawIntBits(i4 * f) & 4294967295L));
        }
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onNestedScrollAccepted(View view, View view2, int i, int i2) {
        NestedScrollingParentHelper nestedScrollingParentHelper = this.nestedScrollingParentHelper;
        if (i2 == 1) {
            nestedScrollingParentHelper.mNestedScrollAxesNonTouch = i;
        } else {
            nestedScrollingParentHelper.mNestedScrollAxesTouch = i;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
    @Override // androidx.compose.runtime.ComposeNodeLifecycleCallback
    public final void onRelease() {
        this.release.invoke();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
    @Override // androidx.compose.runtime.ComposeNodeLifecycleCallback
    public final void onReuse() {
        if (this.view.getParent() != this) {
            addView(this.view);
        } else {
            this.reset.invoke();
        }
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        return ((i & 2) == 0 && (i & 1) == 0) ? false : true;
    }

    @Override // androidx.core.view.NestedScrollingParent2
    public final void onStopNestedScroll(View view, int i) {
        NestedScrollingParentHelper nestedScrollingParentHelper = this.nestedScrollingParentHelper;
        if (i == 1) {
            nestedScrollingParentHelper.mNestedScrollAxesNonTouch = 0;
        } else {
            nestedScrollingParentHelper.mNestedScrollAxesTouch = 0;
        }
    }

    @Override // android.view.View
    public final void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z) {
        RequestDisallowInterceptTouchEvent requestDisallowInterceptTouchEvent = this.onRequestDisallowInterceptTouchEvent;
        if (requestDisallowInterceptTouchEvent != null) {
            requestDisallowInterceptTouchEvent.mo781invoke(Boolean.valueOf(z));
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return true;
    }

    @Override // androidx.core.view.NestedScrollingParent3
    public final void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        if (this.view.isNestedScrollingEnabled()) {
            NestedScrollDispatcher nestedScrollDispatcher = this.dispatcher;
            AndroidViewHolder_androidKt$NoOpScrollConnection$1 androidViewHolder_androidKt$NoOpScrollConnection$1 = AndroidViewHolder_androidKt.NoOpScrollConnection;
            long jFloatToRawIntBits = (Float.floatToRawIntBits(i * r9) << 32) | (Float.floatToRawIntBits(i2 * r9) & 4294967295L);
            Offset.Companion companion = Offset.Companion;
            float f = i4 * (-1);
            long jM583dispatchPostScrollDzOQY0M = nestedScrollDispatcher.m583dispatchPostScrollDzOQY0M(AndroidViewHolder_androidKt.access$toNestedScrollSource(i5), jFloatToRawIntBits, (Float.floatToRawIntBits(i3 * r9) << 32) | (Float.floatToRawIntBits(f) & 4294967295L));
            iArr[0] = NestedScrollInteropConnectionKt.composeToViewOffset(Float.intBitsToFloat((int) (jM583dispatchPostScrollDzOQY0M >> 32)));
            iArr[1] = NestedScrollInteropConnectionKt.composeToViewOffset(Float.intBitsToFloat((int) (jM583dispatchPostScrollDzOQY0M & 4294967295L)));
        }
    }
}
