package androidx.compose.ui.platform;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.Trace;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStructure;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.collection.IntObjectMapKt;
import androidx.collection.MutableIntObjectMap;
import androidx.collection.MutableObjectList;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.runtime.snapshots.Snapshot$Companion$$ExternalSyntheticLambda0;
import androidx.compose.runtime.snapshots.SnapshotKt;
import androidx.compose.runtime.snapshots.SnapshotStateObserver;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.SessionMutex;
import androidx.compose.ui.autofill.AndroidAutofill;
import androidx.compose.ui.autofill.AndroidAutofill$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.AndroidAutofillManager;
import androidx.compose.ui.autofill.AndroidAutofill_androidKt;
import androidx.compose.ui.autofill.AutofillApi26Helper;
import androidx.compose.ui.autofill.AutofillCallback;
import androidx.compose.ui.autofill.AutofillNode;
import androidx.compose.ui.autofill.AutofillTree;
import androidx.compose.ui.autofill.PlatformAutofillManagerImpl;
import androidx.compose.ui.contentcapture.AndroidContentCaptureManager;
import androidx.compose.ui.draganddrop.AndroidDragAndDropManager;
import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.focus.FocusInteropUtils_androidKt;
import androidx.compose.ui.focus.FocusOwnerImpl;
import androidx.compose.ui.focus.FocusOwnerImplKt;
import androidx.compose.ui.focus.FocusTargetNode;
import androidx.compose.ui.focus.FocusTransactionManager;
import androidx.compose.ui.focus.FocusTransactionsKt;
import androidx.compose.ui.focus.FocusTraversalKt;
import androidx.compose.ui.focus.TwoDimensionalFocusSearchKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidGraphicsContext_androidKt;
import androidx.compose.ui.graphics.CanvasHolder;
import androidx.compose.ui.graphics.GraphicsContext;
import androidx.compose.ui.graphics.Matrix;
import androidx.compose.ui.graphics.RectHelper_androidKt;
import androidx.compose.ui.hapticfeedback.PlatformHapticFeedback;
import androidx.compose.ui.input.InputMode;
import androidx.compose.ui.input.InputModeManagerImpl;
import androidx.compose.ui.input.key.Key;
import androidx.compose.ui.input.key.KeyEventType;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import androidx.compose.ui.input.key.KeyInputModifierKt;
import androidx.compose.ui.input.key.SoftKeyboardInterceptionModifierNode;
import androidx.compose.ui.input.pointer.AndroidPointerIcon;
import androidx.compose.ui.input.pointer.AndroidPointerIconType;
import androidx.compose.ui.input.pointer.MatrixPositionCalculator;
import androidx.compose.ui.input.pointer.MotionEventAdapter;
import androidx.compose.ui.input.pointer.PointerInputEvent;
import androidx.compose.ui.input.pointer.PointerInputEventData;
import androidx.compose.ui.input.pointer.PointerInputEventProcessor;
import androidx.compose.ui.input.pointer.PointerKeyboardModifiers;
import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.input.rotary.RotaryInputModifierKt;
import androidx.compose.ui.input.rotary.RotaryInputModifierNode;
import androidx.compose.ui.input.rotary.RotaryScrollEvent;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.RootMeasurePolicy;
import androidx.compose.ui.modifier.ModifierLocalManager;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.node.DepthSortedSetsForDifferentPasses;
import androidx.compose.ui.node.HitTestResult;
import androidx.compose.ui.node.LayoutNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.node.LayoutNodeLayoutDelegate;
import androidx.compose.ui.node.LookaheadAlignmentLines;
import androidx.compose.ui.node.LookaheadPassDelegate;
import androidx.compose.ui.node.MeasureAndLayoutDelegate;
import androidx.compose.ui.node.MeasurePassDelegate;
import androidx.compose.ui.node.ModifierNodeElement;
import androidx.compose.ui.node.NodeChain;
import androidx.compose.ui.node.OwnedLayer;
import androidx.compose.ui.node.Owner;
import androidx.compose.ui.node.OwnerSnapshotObserver;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.platform.WrappedComposition;
import androidx.compose.ui.scrollcapture.ScrollCapture;
import androidx.compose.ui.semantics.EmptySemanticsElement;
import androidx.compose.ui.semantics.EmptySemanticsModifier;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsNode;
import androidx.compose.ui.semantics.SemanticsNodeKt;
import androidx.compose.ui.semantics.SemanticsOwner;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.spatial.RectManager;
import androidx.compose.ui.spatial.RectManagerKt;
import androidx.compose.ui.spatial.ThrottledCallbacks;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.font.FontFamilyResolver_androidKt;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.ImeOptions;
import androidx.compose.ui.text.input.KeyboardCapitalization;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.input.NullableInputConnectionWrapper;
import androidx.compose.ui.text.input.NullableInputConnectionWrapper_androidKt;
import androidx.compose.ui.text.input.PlatformImeOptions;
import androidx.compose.ui.text.input.RecordingInputConnection;
import androidx.compose.ui.text.input.TextFieldValue;
import androidx.compose.ui.text.input.TextInputService;
import androidx.compose.ui.text.input.TextInputServiceAndroid;
import androidx.compose.ui.text.input.TextInputServiceAndroid$createInputConnection$1;
import androidx.compose.ui.unit.AndroidDensity_androidKt;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntOffsetKt;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.viewinterop.AndroidViewHolder;
import androidx.core.view.ViewCompat;
import androidx.emoji2.text.EmojiCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.savedstate.SavedStateRegistryOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.ResultKt;
import kotlin.ULong;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference0Impl;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public final class AndroidComposeView extends ViewGroup implements Owner, ViewRootForTest, MatrixPositionCalculator, DefaultLifecycleObserver {
    public static final Companion Companion = new Companion(null);
    public static Method getBooleanMethod;
    public static Class systemPropertiesClass;
    public AndroidViewsHandler _androidViewsHandler;
    public final AndroidAutofill _autofill;
    public final AndroidAutofillManager _autofillManager;
    public final InputModeManagerImpl _inputModeManager;
    public final MutableState _viewTreeOwners$delegate;
    public final LazyWindowInfo _windowInfo;
    public final AndroidAccessibilityManager accessibilityManager;
    public final AutofillTree autofillTree;
    public final CanvasHolder canvasHolder;
    public final AndroidClipboard clipboard;
    public final AndroidClipboardManager clipboardManager;
    public final AndroidComposeViewAccessibilityDelegateCompat composeAccessibilityDelegate;
    public Function1 configurationChangeObserver;
    public final AndroidContentCaptureManager contentCaptureManager;
    public CoroutineContext coroutineContext;
    public int currentFontWeightAdjustment;
    public final MutableState density$delegate;
    public final List dirtyLayers;
    public final AndroidDragAndDropManager dragAndDropManager;
    public final MutableObjectList endApplyChangesListeners;
    public final FocusOwnerImpl focusOwner;
    public final MutableState fontFamilyResolver$delegate;
    public final AndroidFontResourceLoader fontLoader;
    public boolean forceUseMatrixCache;
    public final AndroidComposeView$$ExternalSyntheticLambda0 globalLayoutListener;
    public long globalPosition;
    public final GraphicsContext graphicsContext;
    public final PlatformHapticFeedback hapticFeedBack;
    public boolean hoverExitReceived;
    public boolean isDrawingContent;
    public boolean isPendingInteropViewLayoutChangeDispatch;
    public boolean isRenderNodeCompatible;
    public boolean keyboardModifiersRequireUpdate;
    public long lastDownPointerPosition;
    public long lastMatrixRecalculationAnimationTime;
    public final WeakCache layerCache;
    public final MutableState layoutDirection$delegate;
    public final MutableIntObjectMap layoutNodes;
    public final TextInputServiceAndroid legacyTextInputServiceAndroid;
    public final CalculateMatrixToWindowApi29 matrixToWindow;
    public final MeasureAndLayoutDelegate measureAndLayoutDelegate;
    public final ModifierLocalManager modifierLocalManager;
    public final MotionEventAdapter motionEventAdapter;
    public boolean observationClearRequested;
    public Constraints onMeasureConstraints;
    public Function1 onViewTreeOwnersAvailable;
    public final AndroidComposeView$pointerIconService$1 pointerIconService;
    public final PointerInputEventProcessor pointerInputEventProcessor;
    public List postponedDirtyLayers;
    public MotionEvent previousMotionEvent;
    public boolean processingRequestFocusForNextNonChildView;
    public final RectManager rectManager;
    public long relayoutTime;
    public final Function0 resendMotionEventOnLayout;
    public final AndroidComposeView$resendMotionEventRunnable$1 resendMotionEventRunnable;
    public final LayoutNode root;
    public final ScrollCapture scrollCapture;
    public final AndroidComposeView$$ExternalSyntheticLambda1 scrollChangedListener;
    public final SemanticsOwner semanticsOwner;
    public final AndroidComposeView$$ExternalSyntheticLambda3 sendHoverExitEvent;
    public final LayoutNodeDrawScope sharedDrawScope;
    public boolean showLayoutBounds;
    public final OwnerSnapshotObserver snapshotObserver;
    public final DelegatingSoftwareKeyboardController softwareKeyboardController;
    public final boolean superclassInitComplete;
    public final TextInputService textInputService;
    public final AtomicReference textInputSessionMutex;
    public final AndroidTextToolbar textToolbar;
    public final float[] tmpMatrix;
    public final int[] tmpPositionArray;
    public final AndroidComposeView$$ExternalSyntheticLambda2 touchModeChangeListener;
    public final AndroidViewConfiguration viewConfiguration;
    public DrawChildContainer viewLayersContainer;
    public final float[] viewToWindowMatrix;
    public final State viewTreeOwners$delegate;
    public boolean wasMeasuredWithMultipleConstraints;
    public long windowPosition;
    public final float[] windowToViewMatrix;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final boolean access$getIsShowingLayoutBounds(Companion companion) throws ClassNotFoundException {
            companion.getClass();
            try {
                if (AndroidComposeView.systemPropertiesClass == null) {
                    Class<?> cls = Class.forName("android.os.SystemProperties");
                    AndroidComposeView.systemPropertiesClass = cls;
                    AndroidComposeView.getBooleanMethod = cls.getDeclaredMethod("getBoolean", String.class, Boolean.TYPE);
                }
                Method method = AndroidComposeView.getBooleanMethod;
                Object objInvoke = method != null ? method.invoke(null, "debug.layout", Boolean.FALSE) : null;
                Boolean bool = objInvoke instanceof Boolean ? (Boolean) objInvoke : null;
                if (bool != null) {
                    return bool.booleanValue();
                }
                return false;
            } catch (Exception unused) {
                return false;
            }
        }

        private Companion() {
        }
    }

    public final class ViewTreeOwners {
        public final LifecycleOwner lifecycleOwner;
        public final SavedStateRegistryOwner savedStateRegistryOwner;

        public ViewTreeOwners(LifecycleOwner lifecycleOwner, SavedStateRegistryOwner savedStateRegistryOwner) {
            this.lifecycleOwner = lifecycleOwner;
            this.savedStateRegistryOwner = savedStateRegistryOwner;
        }
    }

    /* renamed from: androidx.compose.ui.platform.AndroidComposeView$textInputSession$1, reason: invalid class name and case insensitive filesystem */
    final class C07531 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C07531(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AndroidComposeView.this.textInputSession(null, this);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v40, types: [androidx.compose.ui.platform.AndroidComposeView$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v41, types: [androidx.compose.ui.platform.AndroidComposeView$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r0v42, types: [androidx.compose.ui.platform.AndroidComposeView$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r0v63, types: [androidx.compose.ui.platform.AndroidComposeView$resendMotionEventRunnable$1] */
    public AndroidComposeView(Context context, CoroutineContext coroutineContext) {
        int i;
        super(context);
        int i2 = 0;
        Offset.Companion.getClass();
        this.lastDownPointerPosition = Offset.Unspecified;
        int i3 = 1;
        this.superclassInitComplete = true;
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        this.sharedDrawScope = new LayoutNodeDrawScope(null, i3, 0 == true ? 1 : 0);
        MutableState mutableStateMutableStateOf = SnapshotStateKt.mutableStateOf(AndroidDensity_androidKt.Density(context), SnapshotStateKt.referentialEqualityPolicy());
        this.density$delegate = mutableStateMutableStateOf;
        EmptySemanticsModifier emptySemanticsModifier = new EmptySemanticsModifier();
        EmptySemanticsElement emptySemanticsElement = new EmptySemanticsElement(emptySemanticsModifier);
        ModifierNodeElement<BringIntoViewOnScreenResponderNode> modifierNodeElement = new ModifierNodeElement<BringIntoViewOnScreenResponderNode>() { // from class: androidx.compose.ui.platform.AndroidComposeView$bringIntoViewNode$1
            @Override // androidx.compose.ui.node.ModifierNodeElement
            public final Modifier.Node create() {
                return new BringIntoViewOnScreenResponderNode(this.this$0);
            }

            public final boolean equals(Object obj) {
                return obj == this;
            }

            public final int hashCode() {
                return this.this$0.hashCode();
            }

            @Override // androidx.compose.ui.node.ModifierNodeElement
            public final void update(Modifier.Node node) {
                ((BringIntoViewOnScreenResponderNode) node).view = this.this$0;
            }
        };
        FocusOwnerImpl focusOwnerImpl = new FocusOwnerImpl(new AndroidComposeView$focusOwner$1(this), new AndroidComposeView$focusOwner$2(this), new AndroidComposeView$focusOwner$3(this), new AndroidComposeView$focusOwner$4(this), new AndroidComposeView$focusOwner$5(this), new MutablePropertyReference0Impl(this) { // from class: androidx.compose.ui.platform.AndroidComposeView$focusOwner$6
            @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KProperty0
            public final Object get() {
                return (LayoutDirection) ((SnapshotMutableStateImpl) ((AndroidComposeView) this.receiver).layoutDirection$delegate).getValue();
            }

            @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KMutableProperty0
            public final void set(Object obj) {
                AndroidComposeView androidComposeView = (AndroidComposeView) this.receiver;
                AndroidComposeView.Companion companion = AndroidComposeView.Companion;
                ((SnapshotMutableStateImpl) androidComposeView.layoutDirection$delegate).setValue((LayoutDirection) obj);
            }
        });
        this.focusOwner = focusOwnerImpl;
        this.coroutineContext = coroutineContext;
        AndroidDragAndDropManager androidDragAndDropManager = new AndroidDragAndDropManager(new AndroidComposeView$dragAndDropManager$1(this));
        this.dragAndDropManager = androidDragAndDropManager;
        this._windowInfo = new LazyWindowInfo();
        Modifier.Companion companion = Modifier.Companion;
        Modifier modifierOnKeyEvent = KeyInputModifierKt.onKeyEvent(companion, new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeView$keyInputModifier$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                final FocusDirection focusDirectionM368boximpl;
                int i4;
                KeyEvent keyEvent = ((androidx.compose.ui.input.key.KeyEvent) obj).nativeKeyEvent;
                this.this$0.getClass();
                long jM580getKeyZmokQxo = KeyEvent_androidKt.m580getKeyZmokQxo(keyEvent);
                Key.Companion.getClass();
                if (Key.m578equalsimpl0(jM580getKeyZmokQxo, Key.NavigatePrevious)) {
                    FocusDirection.Companion.getClass();
                    focusDirectionM368boximpl = FocusDirection.m368boximpl(FocusDirection.Previous);
                } else if (Key.m578equalsimpl0(jM580getKeyZmokQxo, Key.NavigateNext)) {
                    FocusDirection.Companion.getClass();
                    focusDirectionM368boximpl = FocusDirection.m368boximpl(FocusDirection.Next);
                } else if (Key.m578equalsimpl0(jM580getKeyZmokQxo, Key.Tab)) {
                    if (keyEvent.isShiftPressed()) {
                        FocusDirection.Companion.getClass();
                        i4 = FocusDirection.Previous;
                    } else {
                        FocusDirection.Companion.getClass();
                        i4 = FocusDirection.Next;
                    }
                    focusDirectionM368boximpl = FocusDirection.m368boximpl(i4);
                } else if (Key.m578equalsimpl0(jM580getKeyZmokQxo, Key.DirectionRight)) {
                    FocusDirection.Companion.getClass();
                    focusDirectionM368boximpl = FocusDirection.m368boximpl(FocusDirection.Right);
                } else if (Key.m578equalsimpl0(jM580getKeyZmokQxo, Key.DirectionLeft)) {
                    FocusDirection.Companion.getClass();
                    focusDirectionM368boximpl = FocusDirection.m368boximpl(FocusDirection.Left);
                } else {
                    if (Key.m578equalsimpl0(jM580getKeyZmokQxo, Key.DirectionUp) ? true : Key.m578equalsimpl0(jM580getKeyZmokQxo, Key.PageUp)) {
                        FocusDirection.Companion.getClass();
                        focusDirectionM368boximpl = FocusDirection.m368boximpl(FocusDirection.Up);
                    } else {
                        if (Key.m578equalsimpl0(jM580getKeyZmokQxo, Key.DirectionDown) ? true : Key.m578equalsimpl0(jM580getKeyZmokQxo, Key.PageDown)) {
                            FocusDirection.Companion.getClass();
                            focusDirectionM368boximpl = FocusDirection.m368boximpl(FocusDirection.Down);
                        } else {
                            if (Key.m578equalsimpl0(jM580getKeyZmokQxo, Key.DirectionCenter) ? true : Key.m578equalsimpl0(jM580getKeyZmokQxo, Key.Enter) ? true : Key.m578equalsimpl0(jM580getKeyZmokQxo, Key.NumPadEnter)) {
                                FocusDirection.Companion.getClass();
                                focusDirectionM368boximpl = FocusDirection.m368boximpl(FocusDirection.Enter);
                            } else {
                                if (Key.m578equalsimpl0(jM580getKeyZmokQxo, Key.Back) ? true : Key.m578equalsimpl0(jM580getKeyZmokQxo, Key.Escape)) {
                                    FocusDirection.Companion.getClass();
                                    focusDirectionM368boximpl = FocusDirection.m368boximpl(FocusDirection.Exit);
                                } else {
                                    focusDirectionM368boximpl = null;
                                }
                            }
                        }
                    }
                }
                if (focusDirectionM368boximpl != null) {
                    int iM581getTypeZmokQxo = KeyEvent_androidKt.m581getTypeZmokQxo(keyEvent);
                    KeyEventType.Companion.getClass();
                    if (iM581getTypeZmokQxo == KeyEventType.KeyDown) {
                        int i5 = focusDirectionM368boximpl.value;
                        Integer numM370toAndroidFocusDirection3ESFkO8 = FocusInteropUtils_androidKt.m370toAndroidFocusDirection3ESFkO8(i5);
                        if (ComposeUiFlags.isViewFocusFixEnabled && this.this$0.hasFocus() && numM370toAndroidFocusDirection3ESFkO8 != null && this.this$0.m697onMoveFocusInChildren3ESFkO8(i5)) {
                            return Boolean.TRUE;
                        }
                        Rect rectOnFetchFocusRect = this.this$0.onFetchFocusRect();
                        Boolean boolM374focusSearchULY8qGw = this.this$0.focusOwner.m374focusSearchULY8qGw(i5, rectOnFetchFocusRect, new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeView$keyInputModifier$1$focusWasMovedOrCancelled$1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                return Boolean.valueOf(((FocusTargetNode) obj2).m380requestFocus3ESFkO8(focusDirectionM368boximpl.value));
                            }
                        });
                        if (boolM374focusSearchULY8qGw != null ? boolM374focusSearchULY8qGw.booleanValue() : true) {
                            return Boolean.TRUE;
                        }
                        if (!FocusOwnerImplKt.m377is1dFocusSearch3ESFkO8(i5)) {
                            return Boolean.FALSE;
                        }
                        if (numM370toAndroidFocusDirection3ESFkO8 != null) {
                            View viewFindNextNonChildView = this.this$0.findNextNonChildView(numM370toAndroidFocusDirection3ESFkO8.intValue());
                            if (Intrinsics.areEqual(viewFindNextNonChildView, this.this$0)) {
                                viewFindNextNonChildView = null;
                            }
                            if (viewFindNextNonChildView != null) {
                                android.graphics.Rect androidRect = rectOnFetchFocusRect != null ? RectHelper_androidKt.toAndroidRect(rectOnFetchFocusRect) : null;
                                if (androidRect == null) {
                                    throw new IllegalStateException("Invalid rect");
                                }
                                ViewGroup viewGroup = (ViewGroup) this.this$0.getRootView();
                                viewGroup.offsetDescendantRectToMyCoords(this.this$0, androidRect);
                                viewGroup.offsetRectIntoDescendantCoords(viewFindNextNonChildView, androidRect);
                                if (FocusInteropUtils_androidKt.requestInteropFocus(viewFindNextNonChildView, numM370toAndroidFocusDirection3ESFkO8, androidRect)) {
                                    return Boolean.TRUE;
                                }
                            }
                        }
                        if (!this.this$0.focusOwner.m372clearFocusI7lrPNg(i5, false, false)) {
                            return Boolean.TRUE;
                        }
                        Boolean boolM374focusSearchULY8qGw2 = this.this$0.focusOwner.m374focusSearchULY8qGw(i5, null, new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeView$keyInputModifier$1.1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                return Boolean.valueOf(((FocusTargetNode) obj2).m380requestFocus3ESFkO8(focusDirectionM368boximpl.value));
                            }
                        });
                        return Boolean.valueOf(boolM374focusSearchULY8qGw2 != null ? boolM374focusSearchULY8qGw2.booleanValue() : true);
                    }
                }
                return Boolean.FALSE;
            }
        });
        Modifier modifierOnRotaryScrollEvent = RotaryInputModifierKt.onRotaryScrollEvent(companion, new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeView$rotaryInputModifier$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                return Boolean.FALSE;
            }
        });
        this.canvasHolder = new CanvasHolder();
        AndroidViewConfiguration androidViewConfiguration = new AndroidViewConfiguration(android.view.ViewConfiguration.get(context));
        this.viewConfiguration = androidViewConfiguration;
        LayoutNode layoutNode = new LayoutNode(false, 0, 3, null);
        layoutNode.setMeasurePolicy(RootMeasurePolicy.INSTANCE);
        layoutNode.setDensity$1((Density) ((SnapshotMutableStateImpl) mutableStateMutableStateOf).getValue());
        layoutNode.setViewConfiguration(androidViewConfiguration);
        layoutNode.setModifier(emptySemanticsElement.then(modifierOnRotaryScrollEvent).then(modifierOnKeyEvent).then(focusOwnerImpl.modifier).then(androidDragAndDropManager.modifier).then(modifierNodeElement));
        this.root = layoutNode;
        MutableIntObjectMap mutableIntObjectMapMutableIntObjectMapOf = IntObjectMapKt.mutableIntObjectMapOf();
        this.layoutNodes = mutableIntObjectMapMutableIntObjectMapOf;
        RectManager rectManager = new RectManager(mutableIntObjectMapMutableIntObjectMapOf);
        this.rectManager = rectManager;
        SemanticsOwner semanticsOwner = new SemanticsOwner(layoutNode, emptySemanticsModifier, mutableIntObjectMapMutableIntObjectMapOf);
        this.semanticsOwner = semanticsOwner;
        AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = new AndroidComposeViewAccessibilityDelegateCompat(this);
        this.composeAccessibilityDelegate = androidComposeViewAccessibilityDelegateCompat;
        AndroidContentCaptureManager androidContentCaptureManager = new AndroidContentCaptureManager(this, new AndroidComposeView$contentCaptureManager$1(this));
        this.contentCaptureManager = androidContentCaptureManager;
        this.accessibilityManager = new AndroidAccessibilityManager(context);
        this.graphicsContext = AndroidGraphicsContext_androidKt.GraphicsContext(this);
        AutofillTree autofillTree = new AutofillTree();
        this.autofillTree = autofillTree;
        this.dirtyLayers = new ArrayList();
        this.motionEventAdapter = new MotionEventAdapter();
        this.pointerInputEventProcessor = new PointerInputEventProcessor(layoutNode);
        this.configurationChangeObserver = new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeView$configurationChangeObserver$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                return Unit.INSTANCE;
            }
        };
        this._autofill = new AndroidAutofill(this, autofillTree);
        AutofillManager autofillManager = (AutofillManager) context.getSystemService(AutofillManager.class);
        if (autofillManager == null) {
            throw AndroidAutofill$$ExternalSyntheticOutline0.m("Autofill service could not be located.");
        }
        this._autofillManager = new AndroidAutofillManager(new PlatformAutofillManagerImpl(autofillManager), semanticsOwner, this, rectManager, context.getPackageName());
        AndroidClipboardManager androidClipboardManager = new AndroidClipboardManager(context);
        this.clipboardManager = androidClipboardManager;
        this.clipboard = new AndroidClipboard(androidClipboardManager);
        this.snapshotObserver = new OwnerSnapshotObserver(new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeView$snapshotObserver$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Function0 function0 = (Function0) obj;
                Handler handler = this.this$0.getHandler();
                if ((handler != null ? handler.getLooper() : null) == Looper.myLooper()) {
                    function0.invoke();
                } else {
                    Handler handler2 = this.this$0.getHandler();
                    if (handler2 != null) {
                        handler2.post(new AndroidComposeView$$ExternalSyntheticLambda3(function0, 1));
                    }
                }
                return Unit.INSTANCE;
            }
        });
        this.measureAndLayoutDelegate = new MeasureAndLayoutDelegate(layoutNode);
        long j = Integer.MAX_VALUE;
        IntOffset.Companion companion2 = IntOffset.Companion;
        this.globalPosition = (j & 4294967295L) | (j << 32);
        this.tmpPositionArray = new int[]{0, 0};
        this.tmpMatrix = Matrix.m483constructorimpl$default();
        this.viewToWindowMatrix = Matrix.m483constructorimpl$default();
        this.windowToViewMatrix = Matrix.m483constructorimpl$default();
        this.lastMatrixRecalculationAnimationTime = -1L;
        this.windowPosition = Offset.Infinite;
        this.isRenderNodeCompatible = true;
        this._viewTreeOwners$delegate = SnapshotStateKt.mutableStateOf$default(null);
        this.viewTreeOwners$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.ui.platform.AndroidComposeView$viewTreeOwners$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (AndroidComposeView.ViewTreeOwners) ((SnapshotMutableStateImpl) this.this$0._viewTreeOwners$delegate).getValue();
            }
        });
        this.globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.compose.ui.platform.AndroidComposeView$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                AndroidComposeView androidComposeView = this.f$0;
                AndroidComposeView.Companion companion3 = AndroidComposeView.Companion;
                androidComposeView.updatePositionCacheAndDispatch();
            }
        };
        this.scrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: androidx.compose.ui.platform.AndroidComposeView$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                AndroidComposeView androidComposeView = this.f$0;
                AndroidComposeView.Companion companion3 = AndroidComposeView.Companion;
                androidComposeView.updatePositionCacheAndDispatch();
            }
        };
        this.touchModeChangeListener = new ViewTreeObserver.OnTouchModeChangeListener() { // from class: androidx.compose.ui.platform.AndroidComposeView$$ExternalSyntheticLambda2
            @Override // android.view.ViewTreeObserver.OnTouchModeChangeListener
            public final void onTouchModeChanged(boolean z) {
                int i4;
                InputModeManagerImpl inputModeManagerImpl = this.f$0._inputModeManager;
                if (z) {
                    InputMode.Companion.getClass();
                    i4 = InputMode.Touch;
                } else {
                    InputMode.Companion.getClass();
                    i4 = InputMode.Keyboard;
                }
                ((SnapshotMutableStateImpl) inputModeManagerImpl.inputMode$delegate).setValue(InputMode.m574boximpl(i4));
            }
        };
        TextInputServiceAndroid textInputServiceAndroid = new TextInputServiceAndroid(this, this);
        this.legacyTextInputServiceAndroid = textInputServiceAndroid;
        ((AndroidComposeView_androidKt$platformTextInputServiceInterceptor$1) AndroidComposeView_androidKt.platformTextInputServiceInterceptor).getClass();
        TextInputService textInputService = new TextInputService(textInputServiceAndroid);
        this.textInputService = textInputService;
        this.textInputSessionMutex = new AtomicReference(null);
        this.softwareKeyboardController = new DelegatingSoftwareKeyboardController(textInputService);
        this.fontLoader = new AndroidFontResourceLoader(context);
        this.fontFamilyResolver$delegate = SnapshotStateKt.mutableStateOf(FontFamilyResolver_androidKt.createFontFamilyResolver(context), SnapshotStateKt.referentialEqualityPolicy());
        this.currentFontWeightAdjustment = context.getResources().getConfiguration().fontWeightAdjustment;
        int iM = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(context);
        LayoutDirection layoutDirection = iM != 0 ? iM != 1 ? null : LayoutDirection.Rtl : LayoutDirection.Ltr;
        this.layoutDirection$delegate = SnapshotStateKt.mutableStateOf$default(layoutDirection == null ? LayoutDirection.Ltr : layoutDirection);
        this.hapticFeedBack = new PlatformHapticFeedback(this);
        if (isInTouchMode()) {
            InputMode.Companion.getClass();
            i = InputMode.Touch;
        } else {
            InputMode.Companion.getClass();
            i = InputMode.Keyboard;
        }
        this._inputModeManager = new InputModeManagerImpl(i, new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeView$_inputModeManager$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                int i4 = ((InputMode) obj).value;
                InputMode.Companion.getClass();
                return Boolean.valueOf(i4 == InputMode.Touch ? this.this$0.isInTouchMode() : i4 == InputMode.Keyboard ? this.this$0.isInTouchMode() ? this.this$0.requestFocusFromTouch() : true : false);
            }
        }, objArr2 == true ? 1 : 0);
        this.modifierLocalManager = new ModifierLocalManager(this);
        this.textToolbar = new AndroidTextToolbar(this);
        this.layerCache = new WeakCache();
        this.endApplyChangesListeners = new MutableObjectList(i2, i3, objArr == true ? 1 : 0);
        this.resendMotionEventRunnable = new Runnable() { // from class: androidx.compose.ui.platform.AndroidComposeView$resendMotionEventRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                this.this$0.removeCallbacks(this);
                MotionEvent motionEvent = this.this$0.previousMotionEvent;
                if (motionEvent != null) {
                    boolean z = motionEvent.getToolType(0) == 3;
                    int actionMasked = motionEvent.getActionMasked();
                    if (z) {
                        if (actionMasked == 10 || actionMasked == 1) {
                            return;
                        }
                    } else if (actionMasked == 1) {
                        return;
                    }
                    int i4 = 7;
                    if (actionMasked != 7 && actionMasked != 9) {
                        i4 = 2;
                    }
                    AndroidComposeView androidComposeView = this.this$0;
                    androidComposeView.sendSimulatedEvent(motionEvent, i4, androidComposeView.relayoutTime, false);
                }
            }
        };
        this.sendHoverExitEvent = new AndroidComposeView$$ExternalSyntheticLambda3(this, 0);
        this.resendMotionEventOnLayout = new Function0() { // from class: androidx.compose.ui.platform.AndroidComposeView$resendMotionEventOnLayout$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int actionMasked;
                MotionEvent motionEvent = this.this$0.previousMotionEvent;
                if (motionEvent != null && ((actionMasked = motionEvent.getActionMasked()) == 7 || actionMasked == 9)) {
                    this.this$0.relayoutTime = SystemClock.uptimeMillis();
                    AndroidComposeView androidComposeView = this.this$0;
                    androidComposeView.post(androidComposeView.resendMotionEventRunnable);
                }
                return Unit.INSTANCE;
            }
        };
        this.matrixToWindow = new CalculateMatrixToWindowApi29();
        addOnAttachStateChangeListener(androidContentCaptureManager);
        setWillNotDraw(false);
        setFocusable(true);
        AndroidComposeViewVerificationHelperMethodsO.INSTANCE.focusable(this, 1, false);
        setFocusableInTouchMode(true);
        setClipChildren(false);
        ViewCompat.setAccessibilityDelegate(this, androidComposeViewAccessibilityDelegateCompat);
        ViewRootForTest.Companion.getClass();
        setOnDragListener(androidDragAndDropManager);
        layoutNode.attach$ui_release(this);
        AndroidComposeViewForceDarkModeQ.INSTANCE.disallowForceDark(this);
        this.scrollCapture = new ScrollCapture();
        this.pointerIconService = new AndroidComposeView$pointerIconService$1(this);
    }

    public static final void access$addExtraDataToAccessibilityNodeInfoHelper(AndroidComposeView androidComposeView, int i, AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        int orDefault;
        if (Intrinsics.areEqual(str, androidComposeView.composeAccessibilityDelegate.ExtraDataTestTraversalBeforeVal)) {
            int orDefault2 = androidComposeView.composeAccessibilityDelegate.idToBeforeMap.getOrDefault(i);
            if (orDefault2 != -1) {
                accessibilityNodeInfo.getExtras().putInt(str, orDefault2);
                return;
            }
            return;
        }
        if (!Intrinsics.areEqual(str, androidComposeView.composeAccessibilityDelegate.ExtraDataTestTraversalAfterVal) || (orDefault = androidComposeView.composeAccessibilityDelegate.idToAfterMap.getOrDefault(i)) == -1) {
            return;
        }
        accessibilityNodeInfo.getExtras().putInt(str, orDefault);
    }

    /* renamed from: access$onRequestFocusForOwner-7o62pno, reason: not valid java name */
    public static final boolean m691access$onRequestFocusForOwner7o62pno(AndroidComposeView androidComposeView, FocusDirection focusDirection, Rect rect) {
        Integer numM370toAndroidFocusDirection3ESFkO8;
        if (androidComposeView.isFocused() || androidComposeView.hasFocus()) {
            return true;
        }
        return super.requestFocus((focusDirection == null || (numM370toAndroidFocusDirection3ESFkO8 = FocusInteropUtils_androidKt.m370toAndroidFocusDirection3ESFkO8(focusDirection.value)) == null) ? 130 : numM370toAndroidFocusDirection3ESFkO8.intValue(), rect != null ? RectHelper_androidKt.toAndroidRect(rect) : null);
    }

    public static void clearChildInvalidObservations(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof AndroidComposeView) {
                ((AndroidComposeView) childAt).onEndApplyChanges();
            } else if (childAt instanceof ViewGroup) {
                clearChildInvalidObservations((ViewGroup) childAt);
            }
        }
    }

    /* renamed from: convertMeasureSpec-I7RO_PI, reason: not valid java name */
    public static long m692convertMeasureSpecI7RO_PI(int i) {
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        if (mode == Integer.MIN_VALUE) {
            int i2 = ULong.$r8$clinit;
            return (0 << 32) | size;
        }
        if (mode == 0) {
            int i3 = ULong.$r8$clinit;
            return (0 << 32) | Integer.MAX_VALUE;
        }
        if (mode != 1073741824) {
            throw new IllegalStateException();
        }
        long j = size;
        int i4 = ULong.$r8$clinit;
        return (j << 32) | j;
    }

    public static void invalidateLayers(LayoutNode layoutNode) {
        layoutNode.invalidateLayers$ui_release();
        MutableVector mutableVector = layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            invalidateLayers((LayoutNode) objArr[i2]);
        }
    }

    public static boolean isBadMotionEvent(MotionEvent motionEvent) {
        boolean z = (Float.floatToRawIntBits(motionEvent.getX()) & Integer.MAX_VALUE) >= 2139095040 || (Float.floatToRawIntBits(motionEvent.getY()) & Integer.MAX_VALUE) >= 2139095040 || (Float.floatToRawIntBits(motionEvent.getRawX()) & Integer.MAX_VALUE) >= 2139095040 || (Float.floatToRawIntBits(motionEvent.getRawY()) & Integer.MAX_VALUE) >= 2139095040;
        if (!z) {
            int pointerCount = motionEvent.getPointerCount();
            for (int i = 1; i < pointerCount; i++) {
                z = (Float.floatToRawIntBits(motionEvent.getX(i)) & Integer.MAX_VALUE) >= 2139095040 || (Float.floatToRawIntBits(motionEvent.getY(i)) & Integer.MAX_VALUE) >= 2139095040 || !MotionEventVerifierApi29.INSTANCE.isValidMotionEvent(motionEvent, i);
                if (z) {
                    break;
                }
            }
        }
        return z;
    }

    @Override // android.view.ViewGroup
    public final void addView(View view) {
        addView(view, -1);
    }

    @Override // android.view.View
    public final void autofill(SparseArray sparseArray) {
        Function1 function1;
        boolean z = ComposeUiFlags.isRectTrackingEnabled;
        AndroidAutofill androidAutofill = this._autofill;
        if (androidAutofill != null) {
            AutofillTree autofillTree = androidAutofill.autofillTree;
            if (autofillTree.children.isEmpty()) {
                return;
            }
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                int iKeyAt = sparseArray.keyAt(i);
                AutofillValue autofillValue = (AutofillValue) sparseArray.get(iKeyAt);
                AutofillApi26Helper.INSTANCE.getClass();
                if (autofillValue.isText()) {
                    String string = autofillValue.getTextValue().toString();
                    AutofillNode autofillNode = (AutofillNode) ((LinkedHashMap) autofillTree.children).get(Integer.valueOf(iKeyAt));
                    if (autofillNode != null && (function1 = autofillNode.onFill) != null) {
                        function1.mo781invoke(string);
                        Unit unit = Unit.INSTANCE;
                    }
                } else {
                    if (autofillValue.isDate()) {
                        throw new NotImplementedError("An operation is not implemented: b/138604541: Add onFill() callback for date");
                    }
                    if (autofillValue.isList()) {
                        throw new NotImplementedError("An operation is not implemented: b/138604541: Add onFill() callback for list");
                    }
                    if (autofillValue.isToggle()) {
                        throw new NotImplementedError("An operation is not implemented: b/138604541:  Add onFill() callback for toggle");
                    }
                }
            }
        }
    }

    @Override // android.view.View
    public final boolean canScrollHorizontally(int i) {
        return this.composeAccessibilityDelegate.m701canScroll0AR0LA0$ui_release(this.lastDownPointerPosition, i, false);
    }

    @Override // android.view.View
    public final boolean canScrollVertically(int i) {
        return this.composeAccessibilityDelegate.m701canScroll0AR0LA0$ui_release(this.lastDownPointerPosition, i, true);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        if (!isAttachedToWindow()) {
            invalidateLayers(this.root);
        }
        measureAndLayout(true);
        Snapshot.Companion.getClass();
        SnapshotKt.currentSnapshot().notifyObjectsInitialized$runtime_release();
        this.isDrawingContent = true;
        CanvasHolder canvasHolder = this.canvasHolder;
        AndroidCanvas androidCanvas = canvasHolder.androidCanvas;
        Canvas canvas2 = androidCanvas.internalCanvas;
        androidCanvas.internalCanvas = canvas;
        this.root.draw$ui_release(androidCanvas, null);
        canvasHolder.androidCanvas.internalCanvas = canvas2;
        if (!((ArrayList) this.dirtyLayers).isEmpty()) {
            int size = ((ArrayList) this.dirtyLayers).size();
            for (int i = 0; i < size; i++) {
                ((OwnedLayer) ((ArrayList) this.dirtyLayers).get(i)).updateDisplayList();
            }
        }
        ViewLayer.Companion.getClass();
        if (ViewLayer.shouldUseDispatchDraw) {
            int iSave = canvas.save();
            canvas.clipRect(0.0f, 0.0f, 0.0f, 0.0f);
            super.dispatchDraw(canvas);
            canvas.restoreToCount(iSave);
        }
        ((ArrayList) this.dirtyLayers).clear();
        this.isDrawingContent = false;
        List list = this.postponedDirtyLayers;
        if (list != null) {
            ((ArrayList) this.dirtyLayers).addAll(list);
            ((ArrayList) list).clear();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v11, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r13v12, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r13v16 */
    /* JADX WARN: Type inference failed for: r13v17, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r13v18, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r13v19 */
    /* JADX WARN: Type inference failed for: r13v20 */
    /* JADX WARN: Type inference failed for: r13v21 */
    /* JADX WARN: Type inference failed for: r13v22 */
    /* JADX WARN: Type inference failed for: r13v26 */
    /* JADX WARN: Type inference failed for: r13v27 */
    /* JADX WARN: Type inference failed for: r14v10 */
    /* JADX WARN: Type inference failed for: r14v11 */
    /* JADX WARN: Type inference failed for: r14v15 */
    /* JADX WARN: Type inference failed for: r14v16, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r14v17 */
    /* JADX WARN: Type inference failed for: r14v18 */
    /* JADX WARN: Type inference failed for: r14v19, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r14v23 */
    /* JADX WARN: Type inference failed for: r14v24, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r14v25, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r14v26 */
    /* JADX WARN: Type inference failed for: r14v27 */
    /* JADX WARN: Type inference failed for: r14v28 */
    /* JADX WARN: Type inference failed for: r14v29 */
    /* JADX WARN: Type inference failed for: r14v44 */
    /* JADX WARN: Type inference failed for: r14v45 */
    /* JADX WARN: Type inference failed for: r14v46 */
    /* JADX WARN: Type inference failed for: r14v47 */
    /* JADX WARN: Type inference failed for: r14v48 */
    /* JADX WARN: Type inference failed for: r14v49 */
    /* JADX WARN: Type inference failed for: r14v5, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r14v6, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r6v29 */
    /* JADX WARN: Type inference failed for: r6v30 */
    /* JADX WARN: Type inference failed for: r6v31 */
    /* JADX WARN: Type inference failed for: r6v32 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v22 */
    /* JADX WARN: Type inference failed for: r7v23, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r7v24 */
    /* JADX WARN: Type inference failed for: r7v25, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r7v26, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v27 */
    /* JADX WARN: Type inference failed for: r7v28 */
    /* JADX WARN: Type inference failed for: r7v29 */
    /* JADX WARN: Type inference failed for: r7v30 */
    /* JADX WARN: Type inference failed for: r7v31 */
    /* JADX WARN: Type inference failed for: r7v32 */
    /* JADX WARN: Type inference failed for: r8v27 */
    /* JADX WARN: Type inference failed for: r8v28 */
    /* JADX WARN: Type inference failed for: r8v29 */
    /* JADX WARN: Type inference failed for: r8v30, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r8v31 */
    /* JADX WARN: Type inference failed for: r8v32 */
    /* JADX WARN: Type inference failed for: r8v33, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r8v35 */
    /* JADX WARN: Type inference failed for: r8v36 */
    /* JADX WARN: Type inference failed for: r8v37 */
    /* JADX WARN: Type inference failed for: r8v38 */
    @Override // android.view.View
    public final boolean dispatchGenericMotionEvent(final MotionEvent motionEvent) {
        RotaryInputModifierNode rotaryInputModifierNode;
        int size;
        NodeChain nodeChain;
        DelegatingNode delegatingNodeAccess$pop;
        NodeChain nodeChain2;
        if (this.hoverExitReceived) {
            removeCallbacks(this.sendHoverExitEvent);
            if (motionEvent.getActionMasked() == 8) {
                this.hoverExitReceived = false;
            } else {
                this.sendHoverExitEvent.run();
            }
        }
        if (motionEvent.getActionMasked() != 8) {
            return super.dispatchGenericMotionEvent(motionEvent);
        }
        if (isBadMotionEvent(motionEvent) || !isAttachedToWindow()) {
            return super.dispatchGenericMotionEvent(motionEvent);
        }
        if (!motionEvent.isFromSource(4194304)) {
            return (m693handleMotionEvent8iAsVTc(motionEvent) & 1) != 0;
        }
        android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(getContext());
        float f = -motionEvent.getAxisValue(26);
        getContext();
        float scaledVerticalScrollFactor = viewConfiguration.getScaledVerticalScrollFactor() * f;
        getContext();
        RotaryScrollEvent rotaryScrollEvent = new RotaryScrollEvent(scaledVerticalScrollFactor, viewConfiguration.getScaledHorizontalScrollFactor() * f, motionEvent.getEventTime(), motionEvent.getDeviceId());
        FocusOwnerImpl focusOwnerImpl = this.focusOwner;
        Function0 function0 = new Function0() { // from class: androidx.compose.ui.platform.AndroidComposeView$handleRotaryEvent$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(super/*android.view.ViewGroup*/.dispatchGenericMotionEvent(motionEvent));
            }
        };
        if (focusOwnerImpl.focusInvalidationManager.hasPendingInvalidation()) {
            System.out.println((Object) "FocusRelatedWarning: Dispatching rotary event while the focus system is invalidated.");
            return false;
        }
        FocusTargetNode focusTargetNodeFindActiveFocusNode = FocusTraversalKt.findActiveFocusNode(focusOwnerImpl.rootFocusNode);
        if (focusTargetNodeFindActiveFocusNode != null) {
            if (!focusTargetNodeFindActiveFocusNode.node.isAttached) {
                InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
            }
            Modifier.Node node = focusTargetNodeFindActiveFocusNode.node;
            LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNodeFindActiveFocusNode);
            loop0: while (true) {
                if (layoutNodeRequireLayoutNode == null) {
                    delegatingNodeAccess$pop = 0;
                    break;
                }
                if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
                    while (node != null) {
                        if ((node.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
                            ?? mutableVector = 0;
                            delegatingNodeAccess$pop = node;
                            while (delegatingNodeAccess$pop != 0) {
                                if (delegatingNodeAccess$pop instanceof RotaryInputModifierNode) {
                                    break loop0;
                                }
                                if ((delegatingNodeAccess$pop.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                                    Modifier.Node node2 = delegatingNodeAccess$pop.delegate;
                                    int i = 0;
                                    delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                    mutableVector = mutableVector;
                                    while (node2 != null) {
                                        if ((node2.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
                                            i++;
                                            mutableVector = mutableVector;
                                            if (i == 1) {
                                                delegatingNodeAccess$pop = node2;
                                            } else {
                                                if (mutableVector == 0) {
                                                    mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                if (delegatingNodeAccess$pop != 0) {
                                                    mutableVector.add(delegatingNodeAccess$pop);
                                                    delegatingNodeAccess$pop = 0;
                                                }
                                                mutableVector.add(node2);
                                            }
                                        }
                                        node2 = node2.child;
                                        delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                        mutableVector = mutableVector;
                                    }
                                    if (i == 1) {
                                    }
                                }
                                delegatingNodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector);
                            }
                        }
                        node = node.parent;
                    }
                }
                layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
                node = (layoutNodeRequireLayoutNode == null || (nodeChain2 = layoutNodeRequireLayoutNode.nodes) == null) ? null : nodeChain2.tail;
            }
            rotaryInputModifierNode = (RotaryInputModifierNode) delegatingNodeAccess$pop;
        } else {
            rotaryInputModifierNode = null;
        }
        if (rotaryInputModifierNode != null) {
            Modifier.Node node3 = (Modifier.Node) rotaryInputModifierNode;
            if (!node3.node.isAttached) {
                InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
            }
            Modifier.Node node4 = node3.node.parent;
            LayoutNode layoutNodeRequireLayoutNode2 = DelegatableNodeKt.requireLayoutNode(rotaryInputModifierNode);
            ArrayList arrayList = null;
            while (layoutNodeRequireLayoutNode2 != null) {
                if ((layoutNodeRequireLayoutNode2.nodes.head.aggregateChildKindSet & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
                    while (node4 != null) {
                        if ((node4.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
                            Modifier.Node nodeAccess$pop = node4;
                            MutableVector mutableVector2 = null;
                            while (nodeAccess$pop != null) {
                                if (nodeAccess$pop instanceof RotaryInputModifierNode) {
                                    if (arrayList == null) {
                                        arrayList = new ArrayList();
                                    }
                                    arrayList.add(nodeAccess$pop);
                                } else if ((nodeAccess$pop.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                    int i2 = 0;
                                    for (Modifier.Node node5 = ((DelegatingNode) nodeAccess$pop).delegate; node5 != null; node5 = node5.child) {
                                        if ((node5.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
                                            i2++;
                                            if (i2 == 1) {
                                                nodeAccess$pop = node5;
                                            } else {
                                                if (mutableVector2 == null) {
                                                    mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                if (nodeAccess$pop != null) {
                                                    mutableVector2.add(nodeAccess$pop);
                                                    nodeAccess$pop = null;
                                                }
                                                mutableVector2.add(node5);
                                            }
                                        }
                                    }
                                    if (i2 == 1) {
                                    }
                                }
                                nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector2);
                            }
                        }
                        node4 = node4.parent;
                    }
                }
                layoutNodeRequireLayoutNode2 = layoutNodeRequireLayoutNode2.getParent$ui_release();
                node4 = (layoutNodeRequireLayoutNode2 == null || (nodeChain = layoutNodeRequireLayoutNode2.nodes) == null) ? null : nodeChain.tail;
            }
            if (arrayList != null && arrayList.size() - 1 >= 0) {
                while (true) {
                    int i3 = size - 1;
                    if (((RotaryInputModifierNode) arrayList.get(size)).onPreRotaryScrollEvent(rotaryScrollEvent)) {
                        break;
                    }
                    if (i3 < 0) {
                        break;
                    }
                    size = i3;
                }
            }
            DelegatingNode delegatingNodeAccess$pop2 = node3.node;
            ?? mutableVector3 = 0;
            while (true) {
                if (delegatingNodeAccess$pop2 != 0) {
                    if (delegatingNodeAccess$pop2 instanceof RotaryInputModifierNode) {
                        if (((RotaryInputModifierNode) delegatingNodeAccess$pop2).onPreRotaryScrollEvent(rotaryScrollEvent)) {
                            break;
                        }
                    } else if ((delegatingNodeAccess$pop2.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 && (delegatingNodeAccess$pop2 instanceof DelegatingNode)) {
                        Modifier.Node node6 = delegatingNodeAccess$pop2.delegate;
                        int i4 = 0;
                        mutableVector3 = mutableVector3;
                        delegatingNodeAccess$pop2 = delegatingNodeAccess$pop2;
                        while (node6 != null) {
                            if ((node6.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
                                i4++;
                                mutableVector3 = mutableVector3;
                                if (i4 == 1) {
                                    delegatingNodeAccess$pop2 = node6;
                                } else {
                                    if (mutableVector3 == 0) {
                                        mutableVector3 = new MutableVector(new Modifier.Node[16], 0);
                                    }
                                    if (delegatingNodeAccess$pop2 != 0) {
                                        mutableVector3.add(delegatingNodeAccess$pop2);
                                        delegatingNodeAccess$pop2 = 0;
                                    }
                                    mutableVector3.add(node6);
                                }
                            }
                            node6 = node6.child;
                            mutableVector3 = mutableVector3;
                            delegatingNodeAccess$pop2 = delegatingNodeAccess$pop2;
                        }
                        if (i4 == 1) {
                        }
                    }
                    delegatingNodeAccess$pop2 = DelegatableNodeKt.access$pop(mutableVector3);
                } else if (!((Boolean) function0.invoke()).booleanValue()) {
                    DelegatingNode delegatingNodeAccess$pop3 = node3.node;
                    ?? mutableVector4 = 0;
                    while (true) {
                        if (delegatingNodeAccess$pop3 != 0) {
                            if (delegatingNodeAccess$pop3 instanceof RotaryInputModifierNode) {
                                if (((RotaryInputModifierNode) delegatingNodeAccess$pop3).onRotaryScrollEvent(rotaryScrollEvent)) {
                                    break;
                                }
                            } else if ((delegatingNodeAccess$pop3.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 && (delegatingNodeAccess$pop3 instanceof DelegatingNode)) {
                                Modifier.Node node7 = delegatingNodeAccess$pop3.delegate;
                                int i5 = 0;
                                delegatingNodeAccess$pop3 = delegatingNodeAccess$pop3;
                                mutableVector4 = mutableVector4;
                                while (node7 != null) {
                                    if ((node7.kindSet & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
                                        i5++;
                                        mutableVector4 = mutableVector4;
                                        if (i5 == 1) {
                                            delegatingNodeAccess$pop3 = node7;
                                        } else {
                                            if (mutableVector4 == 0) {
                                                mutableVector4 = new MutableVector(new Modifier.Node[16], 0);
                                            }
                                            if (delegatingNodeAccess$pop3 != 0) {
                                                mutableVector4.add(delegatingNodeAccess$pop3);
                                                delegatingNodeAccess$pop3 = 0;
                                            }
                                            mutableVector4.add(node7);
                                        }
                                    }
                                    node7 = node7.child;
                                    delegatingNodeAccess$pop3 = delegatingNodeAccess$pop3;
                                    mutableVector4 = mutableVector4;
                                }
                                if (i5 == 1) {
                                }
                            }
                            delegatingNodeAccess$pop3 = DelegatableNodeKt.access$pop(mutableVector4);
                        } else if (arrayList != null) {
                            int size2 = arrayList.size();
                            for (int i6 = 0; i6 < size2; i6++) {
                                if (!((RotaryInputModifierNode) arrayList.get(i6)).onRotaryScrollEvent(rotaryScrollEvent)) {
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x0153  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        int i;
        if (this.hoverExitReceived) {
            removeCallbacks(this.sendHoverExitEvent);
            this.sendHoverExitEvent.run();
        }
        if (!isBadMotionEvent(motionEvent) && isAttachedToWindow()) {
            AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = this.composeAccessibilityDelegate;
            if (androidComposeViewAccessibilityDelegateCompat.accessibilityManager.isEnabled() && androidComposeViewAccessibilityDelegateCompat.accessibilityManager.isTouchExplorationEnabled()) {
                int action = motionEvent.getAction();
                AndroidComposeView androidComposeView = androidComposeViewAccessibilityDelegateCompat.view;
                if (action == 7 || action == 9) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    androidComposeView.measureAndLayout(true);
                    HitTestResult hitTestResult = new HitTestResult();
                    LayoutNode layoutNode = androidComposeView.root;
                    long jFloatToRawIntBits = (Float.floatToRawIntBits(x) << 32) | (Float.floatToRawIntBits(y) & 4294967295L);
                    Offset.Companion companion = Offset.Companion;
                    LayoutNode.Companion companion2 = LayoutNode.Companion;
                    PointerType.Companion.getClass();
                    layoutNode.m643hitTestSemantics6fMxITs$ui_release(jFloatToRawIntBits, hitTestResult, true);
                    for (int i2 = hitTestResult.values._size - 1; -1 < i2; i2--) {
                        LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode((Modifier.Node) hitTestResult.values.get(i2));
                        if (((AndroidViewHolder) androidComposeView.getAndroidViewsHandler$ui_release().layoutNodeToHolder.get(layoutNodeRequireLayoutNode)) != null) {
                            break;
                        }
                        if (layoutNodeRequireLayoutNode.nodes.m665hasH91voCI$ui_release(8)) {
                            int iSemanticsNodeIdToAccessibilityVirtualNodeId = androidComposeViewAccessibilityDelegateCompat.semanticsNodeIdToAccessibilityVirtualNodeId(layoutNodeRequireLayoutNode.semanticsId);
                            SemanticsNode SemanticsNode = SemanticsNodeKt.SemanticsNode(layoutNodeRequireLayoutNode, false);
                            if (SemanticsUtils_androidKt.isImportantForAccessibility(SemanticsNode)) {
                                SemanticsConfiguration config = SemanticsNode.getConfig();
                                SemanticsProperties.INSTANCE.getClass();
                                if (!config.props.containsKey(SemanticsProperties.LinkTestMarker)) {
                                    i = iSemanticsNodeIdToAccessibilityVirtualNodeId;
                                    break;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    i = Integer.MIN_VALUE;
                    androidComposeView.getAndroidViewsHandler$ui_release().dispatchGenericMotionEvent(motionEvent);
                    int i3 = androidComposeViewAccessibilityDelegateCompat.hoveredVirtualViewId;
                    if (i3 != i) {
                        androidComposeViewAccessibilityDelegateCompat.hoveredVirtualViewId = i;
                        AndroidComposeViewAccessibilityDelegateCompat.sendEventForVirtualView$default(androidComposeViewAccessibilityDelegateCompat, i, 128, null, 12);
                        AndroidComposeViewAccessibilityDelegateCompat.sendEventForVirtualView$default(androidComposeViewAccessibilityDelegateCompat, i3, 256, null, 12);
                    }
                } else if (action == 10) {
                    int i4 = androidComposeViewAccessibilityDelegateCompat.hoveredVirtualViewId;
                    if (i4 == Integer.MIN_VALUE) {
                        androidComposeView.getAndroidViewsHandler$ui_release().dispatchGenericMotionEvent(motionEvent);
                    } else if (i4 != Integer.MIN_VALUE) {
                        androidComposeViewAccessibilityDelegateCompat.hoveredVirtualViewId = Integer.MIN_VALUE;
                        AndroidComposeViewAccessibilityDelegateCompat.sendEventForVirtualView$default(androidComposeViewAccessibilityDelegateCompat, Integer.MIN_VALUE, 128, null, 12);
                        AndroidComposeViewAccessibilityDelegateCompat.sendEventForVirtualView$default(androidComposeViewAccessibilityDelegateCompat, i4, 256, null, 12);
                    }
                }
            }
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 7) {
                if (actionMasked == 10 && isInBounds(motionEvent)) {
                    if (motionEvent.getToolType(0) != 3 || motionEvent.getButtonState() == 0) {
                        MotionEvent motionEvent2 = this.previousMotionEvent;
                        if (motionEvent2 != null) {
                            motionEvent2.recycle();
                        }
                        this.previousMotionEvent = MotionEvent.obtainNoHistory(motionEvent);
                        this.hoverExitReceived = true;
                        postDelayed(this.sendHoverExitEvent, 8L);
                        return false;
                    }
                } else if ((m693handleMotionEvent8iAsVTc(motionEvent) & 1) != 0) {
                    return true;
                }
            } else if (isPositionChanged(motionEvent)) {
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(final KeyEvent keyEvent) {
        if (!isFocused()) {
            return this.focusOwner.m373dispatchKeyEventYhN2O0w(keyEvent, new Function0() { // from class: androidx.compose.ui.platform.AndroidComposeView.dispatchKeyEvent.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Boolean.valueOf(AndroidComposeView.super.dispatchKeyEvent(keyEvent));
                }
            });
        }
        LazyWindowInfo lazyWindowInfo = this._windowInfo;
        int metaState = keyEvent.getMetaState();
        lazyWindowInfo.getClass();
        WindowInfoImpl.Companion.getClass();
        ((SnapshotMutableStateImpl) WindowInfoImpl.GlobalKeyboardModifiers).setValue(PointerKeyboardModifiers.m598boximpl(metaState));
        return this.focusOwner.m373dispatchKeyEventYhN2O0w(keyEvent, new Function0() { // from class: androidx.compose.ui.focus.FocusOwner$dispatchKeyEvent$1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Boolean.FALSE;
            }
        }) || super.dispatchKeyEvent(keyEvent);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0202 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:217:0x0197 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r0v13, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v6, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r0v7, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r3v22, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r3v23, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v24 */
    /* JADX WARN: Type inference failed for: r3v25 */
    /* JADX WARN: Type inference failed for: r3v26 */
    /* JADX WARN: Type inference failed for: r3v27 */
    /* JADX WARN: Type inference failed for: r3v42 */
    /* JADX WARN: Type inference failed for: r3v43 */
    /* JADX WARN: Type inference failed for: r3v44 */
    /* JADX WARN: Type inference failed for: r3v45 */
    /* JADX WARN: Type inference failed for: r3v46 */
    /* JADX WARN: Type inference failed for: r3v47 */
    /* JADX WARN: Type inference failed for: r3v6, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r3v7, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v16, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v18 */
    /* JADX WARN: Type inference failed for: r7v19, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r7v33 */
    /* JADX WARN: Type inference failed for: r7v34 */
    /* JADX WARN: Type inference failed for: r7v35 */
    /* JADX WARN: Type inference failed for: r7v36 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v21 */
    /* JADX WARN: Type inference failed for: r8v22, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r8v23 */
    /* JADX WARN: Type inference failed for: r8v24, types: [androidx.compose.ui.Modifier$Node] */
    /* JADX WARN: Type inference failed for: r8v25, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v26 */
    /* JADX WARN: Type inference failed for: r8v27 */
    /* JADX WARN: Type inference failed for: r8v28 */
    /* JADX WARN: Type inference failed for: r8v29 */
    /* JADX WARN: Type inference failed for: r8v30 */
    /* JADX WARN: Type inference failed for: r8v31 */
    /* JADX WARN: Type inference failed for: r9v28 */
    /* JADX WARN: Type inference failed for: r9v29 */
    /* JADX WARN: Type inference failed for: r9v30 */
    /* JADX WARN: Type inference failed for: r9v31, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r9v32 */
    /* JADX WARN: Type inference failed for: r9v33 */
    /* JADX WARN: Type inference failed for: r9v34, types: [androidx.compose.runtime.collection.MutableVector] */
    /* JADX WARN: Type inference failed for: r9v36 */
    /* JADX WARN: Type inference failed for: r9v37 */
    /* JADX WARN: Type inference failed for: r9v38 */
    /* JADX WARN: Type inference failed for: r9v39 */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        SoftKeyboardInterceptionModifierNode softKeyboardInterceptionModifierNode;
        DelegatingNode delegatingNodeAccess$pop;
        int size;
        NodeChain nodeChain;
        DelegatingNode delegatingNodeAccess$pop2;
        NodeChain nodeChain2;
        if (isFocused()) {
            FocusOwnerImpl focusOwnerImpl = this.focusOwner;
            if (focusOwnerImpl.focusInvalidationManager.hasPendingInvalidation()) {
                System.out.println((Object) "FocusRelatedWarning: Dispatching intercepted soft keyboard event while the focus system is invalidated.");
            } else {
                FocusTargetNode focusTargetNodeFindActiveFocusNode = FocusTraversalKt.findActiveFocusNode(focusOwnerImpl.rootFocusNode);
                if (focusTargetNodeFindActiveFocusNode != null) {
                    if (!focusTargetNodeFindActiveFocusNode.node.isAttached) {
                        InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
                    }
                    Modifier.Node node = focusTargetNodeFindActiveFocusNode.node;
                    LayoutNode layoutNodeRequireLayoutNode = DelegatableNodeKt.requireLayoutNode(focusTargetNodeFindActiveFocusNode);
                    loop0: while (true) {
                        if (layoutNodeRequireLayoutNode == null) {
                            delegatingNodeAccess$pop2 = 0;
                            break;
                        }
                        if ((layoutNodeRequireLayoutNode.nodes.head.aggregateChildKindSet & 131072) != 0) {
                            while (node != null) {
                                if ((node.kindSet & 131072) != 0) {
                                    ?? mutableVector = 0;
                                    delegatingNodeAccess$pop2 = node;
                                    while (delegatingNodeAccess$pop2 != 0) {
                                        if (delegatingNodeAccess$pop2 instanceof SoftKeyboardInterceptionModifierNode) {
                                            break loop0;
                                        }
                                        if ((delegatingNodeAccess$pop2.kindSet & 131072) != 0 && (delegatingNodeAccess$pop2 instanceof DelegatingNode)) {
                                            Modifier.Node node2 = delegatingNodeAccess$pop2.delegate;
                                            int i = 0;
                                            delegatingNodeAccess$pop2 = delegatingNodeAccess$pop2;
                                            mutableVector = mutableVector;
                                            while (node2 != null) {
                                                if ((node2.kindSet & 131072) != 0) {
                                                    i++;
                                                    mutableVector = mutableVector;
                                                    if (i == 1) {
                                                        delegatingNodeAccess$pop2 = node2;
                                                    } else {
                                                        if (mutableVector == 0) {
                                                            mutableVector = new MutableVector(new Modifier.Node[16], 0);
                                                        }
                                                        if (delegatingNodeAccess$pop2 != 0) {
                                                            mutableVector.add(delegatingNodeAccess$pop2);
                                                            delegatingNodeAccess$pop2 = 0;
                                                        }
                                                        mutableVector.add(node2);
                                                    }
                                                }
                                                node2 = node2.child;
                                                delegatingNodeAccess$pop2 = delegatingNodeAccess$pop2;
                                                mutableVector = mutableVector;
                                            }
                                            if (i == 1) {
                                            }
                                        }
                                        delegatingNodeAccess$pop2 = DelegatableNodeKt.access$pop(mutableVector);
                                    }
                                }
                                node = node.parent;
                            }
                        }
                        layoutNodeRequireLayoutNode = layoutNodeRequireLayoutNode.getParent$ui_release();
                        node = (layoutNodeRequireLayoutNode == null || (nodeChain2 = layoutNodeRequireLayoutNode.nodes) == null) ? null : nodeChain2.tail;
                    }
                    softKeyboardInterceptionModifierNode = (SoftKeyboardInterceptionModifierNode) delegatingNodeAccess$pop2;
                } else {
                    softKeyboardInterceptionModifierNode = null;
                }
                if (softKeyboardInterceptionModifierNode != null) {
                    Modifier.Node node3 = (Modifier.Node) softKeyboardInterceptionModifierNode;
                    if (!node3.node.isAttached) {
                        InlineClassHelperKt.throwIllegalStateException("visitAncestors called on an unattached node");
                    }
                    Modifier.Node node4 = node3.node.parent;
                    LayoutNode layoutNodeRequireLayoutNode2 = DelegatableNodeKt.requireLayoutNode(softKeyboardInterceptionModifierNode);
                    ArrayList arrayList = null;
                    while (layoutNodeRequireLayoutNode2 != null) {
                        if ((layoutNodeRequireLayoutNode2.nodes.head.aggregateChildKindSet & 131072) != 0) {
                            while (node4 != null) {
                                if ((node4.kindSet & 131072) != 0) {
                                    Modifier.Node nodeAccess$pop = node4;
                                    MutableVector mutableVector2 = null;
                                    while (nodeAccess$pop != null) {
                                        if (nodeAccess$pop instanceof SoftKeyboardInterceptionModifierNode) {
                                            if (arrayList == null) {
                                                arrayList = new ArrayList();
                                            }
                                            arrayList.add(nodeAccess$pop);
                                        } else if ((nodeAccess$pop.kindSet & 131072) != 0 && (nodeAccess$pop instanceof DelegatingNode)) {
                                            int i2 = 0;
                                            for (Modifier.Node node5 = ((DelegatingNode) nodeAccess$pop).delegate; node5 != null; node5 = node5.child) {
                                                if ((node5.kindSet & 131072) != 0) {
                                                    i2++;
                                                    if (i2 == 1) {
                                                        nodeAccess$pop = node5;
                                                    } else {
                                                        if (mutableVector2 == null) {
                                                            mutableVector2 = new MutableVector(new Modifier.Node[16], 0);
                                                        }
                                                        if (nodeAccess$pop != null) {
                                                            mutableVector2.add(nodeAccess$pop);
                                                            nodeAccess$pop = null;
                                                        }
                                                        mutableVector2.add(node5);
                                                    }
                                                }
                                            }
                                            if (i2 == 1) {
                                            }
                                        }
                                        nodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector2);
                                    }
                                }
                                node4 = node4.parent;
                            }
                        }
                        layoutNodeRequireLayoutNode2 = layoutNodeRequireLayoutNode2.getParent$ui_release();
                        node4 = (layoutNodeRequireLayoutNode2 == null || (nodeChain = layoutNodeRequireLayoutNode2.nodes) == null) ? null : nodeChain.tail;
                    }
                    if (arrayList == null || arrayList.size() - 1 < 0) {
                        delegatingNodeAccess$pop = node3.node;
                        ?? mutableVector3 = 0;
                        while (true) {
                            if (delegatingNodeAccess$pop == 0) {
                                if (delegatingNodeAccess$pop instanceof SoftKeyboardInterceptionModifierNode) {
                                    if (((SoftKeyboardInterceptionModifierNode) delegatingNodeAccess$pop).mo576onPreInterceptKeyBeforeSoftKeyboardZmokQxo(keyEvent)) {
                                        break;
                                    }
                                } else if ((delegatingNodeAccess$pop.kindSet & 131072) != 0 && (delegatingNodeAccess$pop instanceof DelegatingNode)) {
                                    Modifier.Node node6 = delegatingNodeAccess$pop.delegate;
                                    int i3 = 0;
                                    delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                    mutableVector3 = mutableVector3;
                                    while (node6 != null) {
                                        if ((node6.kindSet & 131072) != 0) {
                                            i3++;
                                            mutableVector3 = mutableVector3;
                                            if (i3 == 1) {
                                                delegatingNodeAccess$pop = node6;
                                            } else {
                                                if (mutableVector3 == 0) {
                                                    mutableVector3 = new MutableVector(new Modifier.Node[16], 0);
                                                }
                                                if (delegatingNodeAccess$pop != 0) {
                                                    mutableVector3.add(delegatingNodeAccess$pop);
                                                    delegatingNodeAccess$pop = 0;
                                                }
                                                mutableVector3.add(node6);
                                            }
                                        }
                                        node6 = node6.child;
                                        delegatingNodeAccess$pop = delegatingNodeAccess$pop;
                                        mutableVector3 = mutableVector3;
                                    }
                                    if (i3 == 1) {
                                    }
                                }
                                delegatingNodeAccess$pop = DelegatableNodeKt.access$pop(mutableVector3);
                            } else {
                                DelegatingNode delegatingNodeAccess$pop3 = node3.node;
                                ?? mutableVector4 = 0;
                                while (true) {
                                    if (delegatingNodeAccess$pop3 != 0) {
                                        if (delegatingNodeAccess$pop3 instanceof SoftKeyboardInterceptionModifierNode) {
                                            if (((SoftKeyboardInterceptionModifierNode) delegatingNodeAccess$pop3).mo575onInterceptKeyBeforeSoftKeyboardZmokQxo(keyEvent)) {
                                                break;
                                            }
                                        } else if ((delegatingNodeAccess$pop3.kindSet & 131072) != 0 && (delegatingNodeAccess$pop3 instanceof DelegatingNode)) {
                                            Modifier.Node node7 = delegatingNodeAccess$pop3.delegate;
                                            int i4 = 0;
                                            delegatingNodeAccess$pop3 = delegatingNodeAccess$pop3;
                                            mutableVector4 = mutableVector4;
                                            while (node7 != null) {
                                                if ((node7.kindSet & 131072) != 0) {
                                                    i4++;
                                                    mutableVector4 = mutableVector4;
                                                    if (i4 == 1) {
                                                        delegatingNodeAccess$pop3 = node7;
                                                    } else {
                                                        if (mutableVector4 == 0) {
                                                            mutableVector4 = new MutableVector(new Modifier.Node[16], 0);
                                                        }
                                                        if (delegatingNodeAccess$pop3 != 0) {
                                                            mutableVector4.add(delegatingNodeAccess$pop3);
                                                            delegatingNodeAccess$pop3 = 0;
                                                        }
                                                        mutableVector4.add(node7);
                                                    }
                                                }
                                                node7 = node7.child;
                                                delegatingNodeAccess$pop3 = delegatingNodeAccess$pop3;
                                                mutableVector4 = mutableVector4;
                                            }
                                            if (i4 == 1) {
                                            }
                                        }
                                        delegatingNodeAccess$pop3 = DelegatableNodeKt.access$pop(mutableVector4);
                                    } else if (arrayList != null) {
                                        int size2 = arrayList.size();
                                        for (int i5 = 0; i5 < size2; i5++) {
                                            if (((SoftKeyboardInterceptionModifierNode) arrayList.get(i5)).mo575onInterceptKeyBeforeSoftKeyboardZmokQxo(keyEvent)) {
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        while (true) {
                            int i6 = size - 1;
                            if (((SoftKeyboardInterceptionModifierNode) arrayList.get(size)).mo576onPreInterceptKeyBeforeSoftKeyboardZmokQxo(keyEvent)) {
                                break;
                            }
                            if (i6 < 0) {
                                break;
                            }
                            size = i6;
                        }
                        delegatingNodeAccess$pop = node3.node;
                        ?? mutableVector32 = 0;
                        while (true) {
                            if (delegatingNodeAccess$pop == 0) {
                            }
                        }
                    }
                }
            }
            if (!super.dispatchKeyEventPreIme(keyEvent)) {
                break;
            }
            return false;
        }
        if (!super.dispatchKeyEventPreIme(keyEvent)) {
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.hoverExitReceived) {
            removeCallbacks(this.sendHoverExitEvent);
            MotionEvent motionEvent2 = this.previousMotionEvent;
            motionEvent2.getClass();
            if (motionEvent.getActionMasked() == 0 && motionEvent2.getSource() == motionEvent.getSource() && motionEvent2.getToolType(0) == motionEvent.getToolType(0)) {
                this.hoverExitReceived = false;
            } else {
                this.sendHoverExitEvent.run();
            }
        }
        if (!isBadMotionEvent(motionEvent) && isAttachedToWindow() && (motionEvent.getActionMasked() != 2 || isPositionChanged(motionEvent))) {
            int iM693handleMotionEvent8iAsVTc = m693handleMotionEvent8iAsVTc(motionEvent);
            if ((iM693handleMotionEvent8iAsVTc & 2) != 0) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            if ((iM693handleMotionEvent8iAsVTc & 1) != 0) {
                return true;
            }
        }
        return false;
    }

    public final View findNextNonChildView(int i) {
        FocusFinderCompat.Companion.getClass();
        FocusFinderCompat focusFinderCompat = FocusFinderCompat.FocusFinderThreadLocal.get();
        focusFinderCompat.getClass();
        FocusFinderCompat focusFinderCompat2 = focusFinderCompat;
        View viewFindNextFocus = this;
        while (viewFindNextFocus != null) {
            viewFindNextFocus = focusFinderCompat2.findNextFocus(i, viewFindNextFocus, (ViewGroup) getRootView());
            if (viewFindNextFocus != null) {
                Function1 function1 = AndroidComposeView_androidKt.platformTextInputServiceInterceptor;
                if (!viewFindNextFocus.equals(this)) {
                    for (ViewParent parent = viewFindNextFocus.getParent(); parent != null; parent = parent.getParent()) {
                        if (parent == this) {
                            break;
                        }
                    }
                }
                return viewFindNextFocus;
            }
        }
        return null;
    }

    public final View findViewByAccessibilityIdTraversal(int i) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            Method declaredMethod = Class.forName("android.view.View").getDeclaredMethod("findViewByAccessibilityIdTraversal", Integer.TYPE);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(this, Integer.valueOf(i));
            if (objInvoke instanceof View) {
                return (View) objInvoke;
            }
        } catch (NoSuchMethodException unused) {
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ViewGroup, android.view.ViewParent
    public final View focusSearch(View view, int i) {
        Rect rectCalculateBoundingRectRelativeTo;
        int i2;
        if (view == null || this.measureAndLayoutDelegate.duringMeasureLayout) {
            return super.focusSearch(view, i);
        }
        FocusFinderCompat.Companion.getClass();
        FocusFinderCompat focusFinderCompat = FocusFinderCompat.FocusFinderThreadLocal.get();
        focusFinderCompat.getClass();
        View viewFindNextFocus = focusFinderCompat.findNextFocus(i, view, this);
        if (view == this) {
            FocusTargetNode focusTargetNodeFindActiveFocusNode = FocusTraversalKt.findActiveFocusNode(this.focusOwner.rootFocusNode);
            rectCalculateBoundingRectRelativeTo = focusTargetNodeFindActiveFocusNode != null ? FocusTraversalKt.focusRect(focusTargetNodeFindActiveFocusNode) : null;
            if (rectCalculateBoundingRectRelativeTo == null) {
                rectCalculateBoundingRectRelativeTo = FocusInteropUtils_androidKt.calculateBoundingRectRelativeTo(view, this);
            }
        } else {
            rectCalculateBoundingRectRelativeTo = FocusInteropUtils_androidKt.calculateBoundingRectRelativeTo(view, this);
        }
        FocusDirection focusDirection = FocusInteropUtils_androidKt.toFocusDirection(i);
        if (focusDirection != null) {
            i2 = focusDirection.value;
        } else {
            FocusDirection.Companion.getClass();
            i2 = FocusDirection.Down;
        }
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        if (this.focusOwner.m374focusSearchULY8qGw(i2, rectCalculateBoundingRectRelativeTo, new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeView$focusSearch$searchResult$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Type inference failed for: r1v1, types: [T, androidx.compose.ui.focus.FocusTargetNode] */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ref$ObjectRef.element = (FocusTargetNode) obj;
                return Boolean.TRUE;
            }
        }) != null) {
            if (ref$ObjectRef.element != 0) {
                if (viewFindNextFocus != null) {
                    if (FocusOwnerImplKt.m377is1dFocusSearch3ESFkO8(i2)) {
                        return super.focusSearch(view, i);
                    }
                    T t = ref$ObjectRef.element;
                    t.getClass();
                    if (TwoDimensionalFocusSearchKt.m390isBetterCandidateI7lrPNg(FocusTraversalKt.focusRect((FocusTargetNode) t), FocusInteropUtils_androidKt.calculateBoundingRectRelativeTo(viewFindNextFocus, this), rectCalculateBoundingRectRelativeTo, i2)) {
                    }
                }
                return this;
            }
            if (viewFindNextFocus == null) {
            }
            return viewFindNextFocus;
        }
        return view;
    }

    public final void forceMeasureTheSubtree(LayoutNode layoutNode, boolean z) {
        this.measureAndLayoutDelegate.forceMeasureTheSubtree(layoutNode, z);
    }

    public final AndroidViewsHandler getAndroidViewsHandler$ui_release() {
        if (this._androidViewsHandler == null) {
            AndroidViewsHandler androidViewsHandler = new AndroidViewsHandler(getContext());
            this._androidViewsHandler = androidViewsHandler;
            addView(androidViewsHandler, -1);
            requestLayout();
        }
        AndroidViewsHandler androidViewsHandler2 = this._androidViewsHandler;
        androidViewsHandler2.getClass();
        return androidViewsHandler2;
    }

    @Override // android.view.View
    public final void getFocusedRect(android.graphics.Rect rect) {
        Unit unit;
        Rect rectOnFetchFocusRect = onFetchFocusRect();
        if (rectOnFetchFocusRect != null) {
            rect.left = Math.round(rectOnFetchFocusRect.left);
            rect.top = Math.round(rectOnFetchFocusRect.top);
            rect.right = Math.round(rectOnFetchFocusRect.right);
            rect.bottom = Math.round(rectOnFetchFocusRect.bottom);
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            super.getFocusedRect(rect);
        }
    }

    @Override // android.view.View
    public final int getImportantForAutofill() {
        return 1;
    }

    public final ViewTreeOwners getViewTreeOwners() {
        return (ViewTreeOwners) this.viewTreeOwners$delegate.getValue();
    }

    /* renamed from: handleMotionEvent-8iAsVTc, reason: not valid java name */
    public final int m693handleMotionEvent8iAsVTc(MotionEvent motionEvent) {
        int actionMasked;
        removeCallbacks(this.resendMotionEventRunnable);
        try {
            recalculateWindowPosition(motionEvent);
            boolean z = true;
            this.forceUseMatrixCache = true;
            measureAndLayout(false);
            Trace.beginSection("AndroidOwner:onTouch");
            try {
                int actionMasked2 = motionEvent.getActionMasked();
                MotionEvent motionEvent2 = this.previousMotionEvent;
                boolean z2 = motionEvent2 != null && motionEvent2.getToolType(0) == 3;
                if (motionEvent2 != null) {
                    if ((motionEvent2.getSource() == motionEvent.getSource() && motionEvent2.getToolType(0) == motionEvent.getToolType(0)) ? false : true) {
                        if (motionEvent2.getButtonState() != 0 || (actionMasked = motionEvent2.getActionMasked()) == 0 || actionMasked == 2 || actionMasked == 6) {
                            this.pointerInputEventProcessor.processCancel();
                        } else if (motionEvent2.getActionMasked() != 10 && z2) {
                            sendSimulatedEvent(motionEvent2, 10, motionEvent2.getEventTime(), true);
                        }
                    }
                }
                boolean z3 = motionEvent.getToolType(0) == 3;
                if (!z2 && z3 && actionMasked2 != 3 && actionMasked2 != 9 && isInBounds(motionEvent)) {
                    sendSimulatedEvent(motionEvent, 9, motionEvent.getEventTime(), true);
                }
                if (motionEvent2 != null) {
                    motionEvent2.recycle();
                }
                MotionEvent motionEvent3 = this.previousMotionEvent;
                if (motionEvent3 != null && motionEvent3.getAction() == 10) {
                    MotionEvent motionEvent4 = this.previousMotionEvent;
                    int pointerId = motionEvent4 != null ? motionEvent4.getPointerId(0) : -1;
                    if (motionEvent.getAction() == 9 && motionEvent.getHistorySize() == 0) {
                        if (pointerId >= 0) {
                            MotionEventAdapter motionEventAdapter = this.motionEventAdapter;
                            motionEventAdapter.activeHoverIds.delete(pointerId);
                            motionEventAdapter.motionEventToComposePointerIdMap.delete(pointerId);
                        }
                    } else if (motionEvent.getAction() == 0 && motionEvent.getHistorySize() == 0) {
                        MotionEvent motionEvent5 = this.previousMotionEvent;
                        float x = motionEvent5 != null ? motionEvent5.getX() : Float.NaN;
                        MotionEvent motionEvent6 = this.previousMotionEvent;
                        boolean z4 = (x == motionEvent.getX() && (motionEvent6 != null ? motionEvent6.getY() : Float.NaN) == motionEvent.getY()) ? false : true;
                        MotionEvent motionEvent7 = this.previousMotionEvent;
                        if ((motionEvent7 != null ? motionEvent7.getEventTime() : -1L) == motionEvent.getEventTime()) {
                            z = false;
                        }
                        if (z4 || z) {
                            if (pointerId >= 0) {
                                MotionEventAdapter motionEventAdapter2 = this.motionEventAdapter;
                                motionEventAdapter2.activeHoverIds.delete(pointerId);
                                motionEventAdapter2.motionEventToComposePointerIdMap.delete(pointerId);
                            }
                            this.pointerInputEventProcessor.hitPathTracker.root.children.clear();
                        }
                    }
                }
                this.previousMotionEvent = MotionEvent.obtainNoHistory(motionEvent);
                int iM699sendMotionEvent8iAsVTc = m699sendMotionEvent8iAsVTc(motionEvent);
                Trace.endSection();
                return iM699sendMotionEvent8iAsVTc;
            } catch (Throwable th) {
                Trace.endSection();
                throw th;
            }
        } finally {
            this.forceUseMatrixCache = false;
        }
    }

    public final void invalidateLayoutNodeMeasurement(LayoutNode layoutNode) {
        this.measureAndLayoutDelegate.requestRemeasure(layoutNode, false);
        MutableVector mutableVector = layoutNode.get_children$ui_release();
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            invalidateLayoutNodeMeasurement((LayoutNode) objArr[i2]);
        }
    }

    public final boolean isInBounds(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        return 0.0f <= x && x <= ((float) getWidth()) && 0.0f <= y && y <= ((float) getHeight());
    }

    public final boolean isPositionChanged(MotionEvent motionEvent) {
        MotionEvent motionEvent2;
        return (motionEvent.getPointerCount() == 1 && (motionEvent2 = this.previousMotionEvent) != null && motionEvent2.getPointerCount() == motionEvent.getPointerCount() && motionEvent.getRawX() == motionEvent2.getRawX() && motionEvent.getRawY() == motionEvent2.getRawY()) ? false : true;
    }

    /* renamed from: localToScreen-58bKbWc, reason: not valid java name */
    public final void m694localToScreen58bKbWc(float[] fArr) {
        recalculateWindowPosition();
        Matrix.m489timesAssign58bKbWc(fArr, this.viewToWindowMatrix);
        float fIntBitsToFloat = Float.intBitsToFloat((int) (this.windowPosition >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (this.windowPosition & 4294967295L));
        float[] fArr2 = this.tmpMatrix;
        Function1 function1 = AndroidComposeView_androidKt.platformTextInputServiceInterceptor;
        Matrix.m486resetimpl(fArr2);
        Matrix.m490translateimpl(fIntBitsToFloat, fIntBitsToFloat2, fArr2);
        float fM702dotp89u6pk = AndroidComposeView_androidKt.m702dotp89u6pk(fArr2, 0, fArr, 0);
        float fM702dotp89u6pk2 = AndroidComposeView_androidKt.m702dotp89u6pk(fArr2, 0, fArr, 1);
        float fM702dotp89u6pk3 = AndroidComposeView_androidKt.m702dotp89u6pk(fArr2, 0, fArr, 2);
        float fM702dotp89u6pk4 = AndroidComposeView_androidKt.m702dotp89u6pk(fArr2, 0, fArr, 3);
        float fM702dotp89u6pk5 = AndroidComposeView_androidKt.m702dotp89u6pk(fArr2, 1, fArr, 0);
        float fM702dotp89u6pk6 = AndroidComposeView_androidKt.m702dotp89u6pk(fArr2, 1, fArr, 1);
        float fM702dotp89u6pk7 = AndroidComposeView_androidKt.m702dotp89u6pk(fArr2, 1, fArr, 2);
        float fM702dotp89u6pk8 = AndroidComposeView_androidKt.m702dotp89u6pk(fArr2, 1, fArr, 3);
        float fM702dotp89u6pk9 = AndroidComposeView_androidKt.m702dotp89u6pk(fArr2, 2, fArr, 0);
        float fM702dotp89u6pk10 = AndroidComposeView_androidKt.m702dotp89u6pk(fArr2, 2, fArr, 1);
        float fM702dotp89u6pk11 = AndroidComposeView_androidKt.m702dotp89u6pk(fArr2, 2, fArr, 2);
        float fM702dotp89u6pk12 = AndroidComposeView_androidKt.m702dotp89u6pk(fArr2, 2, fArr, 3);
        float fM702dotp89u6pk13 = AndroidComposeView_androidKt.m702dotp89u6pk(fArr2, 3, fArr, 0);
        float fM702dotp89u6pk14 = AndroidComposeView_androidKt.m702dotp89u6pk(fArr2, 3, fArr, 1);
        float fM702dotp89u6pk15 = AndroidComposeView_androidKt.m702dotp89u6pk(fArr2, 3, fArr, 2);
        float fM702dotp89u6pk16 = AndroidComposeView_androidKt.m702dotp89u6pk(fArr2, 3, fArr, 3);
        fArr[0] = fM702dotp89u6pk;
        fArr[1] = fM702dotp89u6pk2;
        fArr[2] = fM702dotp89u6pk3;
        fArr[3] = fM702dotp89u6pk4;
        fArr[4] = fM702dotp89u6pk5;
        fArr[5] = fM702dotp89u6pk6;
        fArr[6] = fM702dotp89u6pk7;
        fArr[7] = fM702dotp89u6pk8;
        fArr[8] = fM702dotp89u6pk9;
        fArr[9] = fM702dotp89u6pk10;
        fArr[10] = fM702dotp89u6pk11;
        fArr[11] = fM702dotp89u6pk12;
        fArr[12] = fM702dotp89u6pk13;
        fArr[13] = fM702dotp89u6pk14;
        fArr[14] = fM702dotp89u6pk15;
        fArr[15] = fM702dotp89u6pk16;
    }

    /* renamed from: localToScreen-MK-Hz9U, reason: not valid java name */
    public final long m695localToScreenMKHz9U(long j) {
        recalculateWindowPosition();
        long jM484mapMKHz9U = Matrix.m484mapMKHz9U(j, this.viewToWindowMatrix);
        float fIntBitsToFloat = Float.intBitsToFloat((int) (this.windowPosition >> 32)) + Float.intBitsToFloat((int) (jM484mapMKHz9U >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (this.windowPosition & 4294967295L)) + Float.intBitsToFloat((int) (jM484mapMKHz9U & 4294967295L));
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L);
        Offset.Companion companion = Offset.Companion;
        return jFloatToRawIntBits;
    }

    public final void measureAndLayout(boolean z) {
        Function0 function0;
        if (this.measureAndLayoutDelegate.relayoutNodes.isNotEmpty() || this.measureAndLayoutDelegate.onPositionedDispatcher.layoutNodes.size != 0) {
            Trace.beginSection("AndroidOwner:measureAndLayout");
            if (z) {
                try {
                    function0 = this.resendMotionEventOnLayout;
                } finally {
                    Trace.endSection();
                }
            } else {
                function0 = null;
            }
            if (this.measureAndLayoutDelegate.measureAndLayout(function0)) {
                requestLayout();
            }
            this.measureAndLayoutDelegate.dispatchOnPositionedCallbacks(false);
            if (this.isPendingInteropViewLayoutChangeDispatch) {
                getViewTreeObserver().dispatchOnGlobalLayout();
                this.isPendingInteropViewLayoutChangeDispatch = false;
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    /* renamed from: measureAndLayout-0kLqBqw, reason: not valid java name */
    public final void m696measureAndLayout0kLqBqw(LayoutNode layoutNode, long j) {
        Trace.beginSection("AndroidOwner:measureAndLayout");
        try {
            this.measureAndLayoutDelegate.m659measureAndLayout0kLqBqw(layoutNode, j);
            if (!this.measureAndLayoutDelegate.relayoutNodes.isNotEmpty()) {
                this.measureAndLayoutDelegate.dispatchOnPositionedCallbacks(false);
                if (this.isPendingInteropViewLayoutChangeDispatch) {
                    getViewTreeObserver().dispatchOnGlobalLayout();
                    this.isPendingInteropViewLayoutChangeDispatch = false;
                }
            }
            if (ComposeUiFlags.isRectTrackingEnabled) {
                this.rectManager.dispatchCallbacks();
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.endSection();
        }
    }

    public final void notifyLayerIsDirty$ui_release(OwnedLayer ownedLayer, boolean z) {
        if (!z) {
            if (this.isDrawingContent) {
                return;
            }
            ((ArrayList) this.dirtyLayers).remove(ownedLayer);
            List list = this.postponedDirtyLayers;
            if (list != null) {
                ((ArrayList) list).remove(ownedLayer);
                return;
            }
            return;
        }
        if (!this.isDrawingContent) {
            this.dirtyLayers.add(ownedLayer);
            return;
        }
        List arrayList = this.postponedDirtyLayers;
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.postponedDirtyLayers = arrayList;
        }
        arrayList.add(ownedLayer);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        LifecycleOwner lifecycleOwner;
        Lifecycle lifecycle;
        int i;
        LifecycleOwner lifecycleOwner2;
        LifecycleOwner lifecycleOwner3;
        super.onAttachedToWindow();
        ((SnapshotMutableStateImpl) this._windowInfo.isWindowFocused$delegate).setValue(Boolean.valueOf(hasWindowFocus()));
        LazyWindowInfo lazyWindowInfo = this._windowInfo;
        new Function0() { // from class: androidx.compose.ui.platform.AndroidComposeView.onAttachedToWindow.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Activity activity;
                long jRound;
                Context context = AndroidComposeView.this.getContext();
                Context baseContext = context;
                while (true) {
                    if (!(baseContext instanceof Activity)) {
                        if (!(baseContext instanceof ContextWrapper)) {
                            activity = null;
                            break;
                        }
                        baseContext = ((ContextWrapper) baseContext).getBaseContext();
                    } else {
                        activity = (Activity) baseContext;
                        break;
                    }
                }
                if (activity != null) {
                    BoundsHelper.Companion.getClass();
                    BoundsHelperApi30Impl.INSTANCE.getClass();
                    android.graphics.Rect bounds = ((WindowManager) activity.getSystemService(WindowManager.class)).getCurrentWindowMetrics().getBounds();
                    jRound = (bounds.height() & 4294967295L) | (bounds.width() << 32);
                    IntSize.Companion companion = IntSize.Companion;
                } else {
                    Configuration configuration = context.getResources().getConfiguration();
                    jRound = (Math.round(configuration.screenHeightDp * r7) & 4294967295L) | (Math.round(configuration.screenWidthDp * context.getResources().getDisplayMetrics().density) << 32);
                    IntSize.Companion companion2 = IntSize.Companion;
                }
                return IntSize.m861boximpl(jRound);
            }
        };
        lazyWindowInfo.getClass();
        this._windowInfo.getClass();
        invalidateLayoutNodeMeasurement(this.root);
        invalidateLayers(this.root);
        SnapshotStateObserver snapshotStateObserver = this.snapshotObserver.observer;
        snapshotStateObserver.getClass();
        Snapshot.Companion companion = Snapshot.Companion;
        Function2 function2 = snapshotStateObserver.applyObserver;
        companion.getClass();
        snapshotStateObserver.applyUnsubscribe = Snapshot.Companion.registerApplyObserver(function2);
        AndroidAutofill androidAutofill = this._autofill;
        if (androidAutofill != null) {
            AutofillCallback autofillCallback = AutofillCallback.INSTANCE;
            autofillCallback.getClass();
            androidAutofill.autofillManager.registerCallback(autofillCallback);
        }
        LifecycleOwner lifecycleOwner4 = ViewTreeLifecycleOwner.get(this);
        SavedStateRegistryOwner savedStateRegistryOwner = ViewTreeSavedStateRegistryOwner.get(this);
        ViewTreeOwners viewTreeOwners = getViewTreeOwners();
        Lifecycle lifecycle2 = null;
        if (viewTreeOwners == null || (lifecycleOwner4 != null && savedStateRegistryOwner != null && (lifecycleOwner4 != (lifecycleOwner3 = viewTreeOwners.lifecycleOwner) || savedStateRegistryOwner != lifecycleOwner3))) {
            if (lifecycleOwner4 == null) {
                throw new IllegalStateException("Composed into the View which doesn't propagate ViewTreeLifecycleOwner!");
            }
            if (savedStateRegistryOwner == null) {
                throw new IllegalStateException("Composed into the View which doesn't propagateViewTreeSavedStateRegistryOwner!");
            }
            if (viewTreeOwners != null && (lifecycleOwner = viewTreeOwners.lifecycleOwner) != null && (lifecycle = lifecycleOwner.getLifecycle()) != null) {
                lifecycle.removeObserver(this);
            }
            lifecycleOwner4.getLifecycle().addObserver(this);
            ViewTreeOwners viewTreeOwners2 = new ViewTreeOwners(lifecycleOwner4, savedStateRegistryOwner);
            ((SnapshotMutableStateImpl) this._viewTreeOwners$delegate).setValue(viewTreeOwners2);
            Function1 function1 = this.onViewTreeOwnersAvailable;
            if (function1 != null) {
                ((WrappedComposition.AnonymousClass1) function1).mo781invoke(viewTreeOwners2);
            }
            this.onViewTreeOwnersAvailable = null;
        }
        InputModeManagerImpl inputModeManagerImpl = this._inputModeManager;
        if (isInTouchMode()) {
            InputMode.Companion.getClass();
            i = InputMode.Touch;
        } else {
            InputMode.Companion.getClass();
            i = InputMode.Keyboard;
        }
        ((SnapshotMutableStateImpl) inputModeManagerImpl.inputMode$delegate).setValue(InputMode.m574boximpl(i));
        ViewTreeOwners viewTreeOwners3 = getViewTreeOwners();
        if (viewTreeOwners3 != null && (lifecycleOwner2 = viewTreeOwners3.lifecycleOwner) != null) {
            lifecycle2 = lifecycleOwner2.getLifecycle();
        }
        if (lifecycle2 == null) {
            throw AndroidAutofill$$ExternalSyntheticOutline0.m("No lifecycle owner exists");
        }
        lifecycle2.addObserver(this);
        lifecycle2.addObserver(this.contentCaptureManager);
        getViewTreeObserver().addOnGlobalLayoutListener(this.globalLayoutListener);
        getViewTreeObserver().addOnScrollChangedListener(this.scrollChangedListener);
        getViewTreeObserver().addOnTouchModeChangeListener(this.touchModeChangeListener);
        AndroidComposeViewTranslationCallbackS.INSTANCE.setViewTranslationCallback(this);
        AndroidAutofillManager androidAutofillManager = this._autofillManager;
        if (androidAutofillManager != null) {
            this.focusOwner.listeners.add(androidAutofillManager);
            this.semanticsOwner.listeners.add(androidAutofillManager);
        }
    }

    @Override // android.view.View
    public final boolean onCheckIsTextEditor() {
        AndroidPlatformTextInputSession androidPlatformTextInputSession = (AndroidPlatformTextInputSession) SessionMutex.m354getCurrentSessionimpl(this.textInputSessionMutex);
        if (androidPlatformTextInputSession == null) {
            return this.legacyTextInputServiceAndroid.editorHasFocus;
        }
        InputMethodSession inputMethodSession = (InputMethodSession) SessionMutex.m354getCurrentSessionimpl(androidPlatformTextInputSession.methodSessionMutex);
        return inputMethodSession != null && (inputMethodSession.disposed ^ true);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ((SnapshotMutableStateImpl) this.density$delegate).setValue(AndroidDensity_androidKt.Density(getContext()));
        this._windowInfo.getClass();
        int i = configuration.fontWeightAdjustment;
        if (i != this.currentFontWeightAdjustment) {
            this.currentFontWeightAdjustment = i;
            ((SnapshotMutableStateImpl) this.fontFamilyResolver$delegate).setValue(FontFamilyResolver_androidKt.createFontFamilyResolver(getContext()));
        }
        this.configurationChangeObserver.mo781invoke(configuration);
    }

    @Override // android.view.View
    public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        String str;
        AndroidPlatformTextInputSession androidPlatformTextInputSession = (AndroidPlatformTextInputSession) SessionMutex.m354getCurrentSessionimpl(this.textInputSessionMutex);
        if (androidPlatformTextInputSession == null) {
            TextInputServiceAndroid textInputServiceAndroid = this.legacyTextInputServiceAndroid;
            if (textInputServiceAndroid.editorHasFocus) {
                ImeOptions imeOptions = textInputServiceAndroid.imeOptions;
                TextFieldValue textFieldValue = textInputServiceAndroid.state;
                int i = imeOptions.imeAction;
                ImeAction.Companion.getClass();
                int i2 = ImeAction.Default;
                boolean z = imeOptions.singleLine;
                int i3 = 6;
                if (i == i2) {
                    if (!z) {
                        i3 = 0;
                    }
                } else if (i == 0) {
                    i3 = 1;
                } else if (i == ImeAction.Go) {
                    i3 = 2;
                } else if (i == ImeAction.Next) {
                    i3 = 5;
                } else if (i == ImeAction.Previous) {
                    i3 = 7;
                } else if (i == ImeAction.Search) {
                    i3 = 3;
                } else if (i == ImeAction.Send) {
                    i3 = 4;
                } else if (i != ImeAction.Done) {
                    throw new IllegalStateException("invalid ImeAction");
                }
                editorInfo.imeOptions = i3;
                PlatformImeOptions platformImeOptions = imeOptions.platformImeOptions;
                if (platformImeOptions != null && (str = platformImeOptions.privateImeOptions) != null) {
                    editorInfo.privateImeOptions = str;
                }
                KeyboardType.Companion.getClass();
                int i4 = KeyboardType.Text;
                int i5 = imeOptions.keyboardType;
                if (i5 == i4) {
                    editorInfo.inputType = 1;
                } else if (i5 == KeyboardType.Ascii) {
                    editorInfo.inputType = 1;
                    editorInfo.imeOptions |= Integer.MIN_VALUE;
                } else if (i5 == KeyboardType.Number) {
                    editorInfo.inputType = 2;
                } else if (i5 == KeyboardType.Phone) {
                    editorInfo.inputType = 3;
                } else if (i5 == KeyboardType.Uri) {
                    editorInfo.inputType = 17;
                } else if (i5 == KeyboardType.Email) {
                    editorInfo.inputType = 33;
                } else if (i5 == KeyboardType.Password) {
                    editorInfo.inputType = 129;
                } else if (i5 == KeyboardType.NumberPassword) {
                    editorInfo.inputType = 18;
                } else {
                    if (i5 != KeyboardType.Decimal) {
                        throw new IllegalStateException("Invalid Keyboard Type");
                    }
                    editorInfo.inputType = 8194;
                }
                if (!z) {
                    int i6 = editorInfo.inputType;
                    if ((i6 & 1) == 1) {
                        editorInfo.inputType = i6 | 131072;
                        if (imeOptions.imeAction == i2) {
                            editorInfo.imeOptions |= 1073741824;
                        }
                    }
                }
                if ((editorInfo.inputType & 1) == 1) {
                    KeyboardCapitalization.Companion.getClass();
                    int i7 = KeyboardCapitalization.Characters;
                    int i8 = imeOptions.capitalization;
                    if (i8 == i7) {
                        editorInfo.inputType |= 4096;
                    } else if (i8 == KeyboardCapitalization.Words) {
                        editorInfo.inputType |= 8192;
                    } else if (i8 == KeyboardCapitalization.Sentences) {
                        editorInfo.inputType |= NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                    }
                    if (imeOptions.autoCorrect) {
                        editorInfo.inputType |= NetworkAnalyticsConstants.DataPoints.FLAG_UID;
                    }
                }
                long j = textFieldValue.selection;
                TextRange.Companion companion = TextRange.Companion;
                editorInfo.initialSelStart = (int) (j >> 32);
                editorInfo.initialSelEnd = (int) (j & 4294967295L);
                editorInfo.setInitialSurroundingSubText(textFieldValue.annotatedString.text, 0);
                editorInfo.imeOptions |= 33554432;
                if (EmojiCompat.isConfigured()) {
                    EmojiCompat.get().updateEditorInfo(editorInfo);
                }
                RecordingInputConnection recordingInputConnection = new RecordingInputConnection(textInputServiceAndroid.state, new TextInputServiceAndroid$createInputConnection$1(textInputServiceAndroid), textInputServiceAndroid.imeOptions.autoCorrect);
                ((ArrayList) textInputServiceAndroid.ics).add(new WeakReference(recordingInputConnection));
                return recordingInputConnection;
            }
        } else {
            final InputMethodSession inputMethodSession = (InputMethodSession) SessionMutex.m354getCurrentSessionimpl(androidPlatformTextInputSession.methodSessionMutex);
            if (inputMethodSession != null) {
                synchronized (inputMethodSession.lock) {
                    if (inputMethodSession.disposed) {
                        return null;
                    }
                    NullableInputConnectionWrapper NullableInputConnectionWrapper = NullableInputConnectionWrapper_androidKt.NullableInputConnectionWrapper(inputMethodSession.request.createInputConnection(editorInfo), new Function1() { // from class: androidx.compose.ui.platform.InputMethodSession$createInputConnection$1$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            NullableInputConnectionWrapper nullableInputConnectionWrapper = (NullableInputConnectionWrapper) obj;
                            nullableInputConnectionWrapper.disposeDelegate();
                            MutableVector mutableVector = inputMethodSession.connections;
                            Object[] objArr = mutableVector.content;
                            int i9 = mutableVector.size;
                            int i10 = 0;
                            while (true) {
                                if (i10 >= i9) {
                                    i10 = -1;
                                    break;
                                }
                                if (Intrinsics.areEqual((androidx.compose.ui.node.WeakReference) objArr[i10], nullableInputConnectionWrapper)) {
                                    break;
                                }
                                i10++;
                            }
                            if (i10 >= 0) {
                                inputMethodSession.connections.removeAt(i10);
                            }
                            InputMethodSession inputMethodSession2 = inputMethodSession;
                            if (inputMethodSession2.connections.size == 0) {
                                inputMethodSession2.onAllConnectionsClosed.invoke();
                            }
                            return Unit.INSTANCE;
                        }
                    });
                    inputMethodSession.connections.add(new androidx.compose.ui.node.WeakReference(NullableInputConnectionWrapper));
                    return NullableInputConnectionWrapper;
                }
            }
        }
        return null;
    }

    @Override // android.view.View
    public final void onCreateVirtualViewTranslationRequests(long[] jArr, int[] iArr, Consumer consumer) {
        this.contentCaptureManager.onCreateVirtualViewTranslationRequests$ui_release(jArr, consumer);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        LifecycleOwner lifecycleOwner;
        super.onDetachedFromWindow();
        SnapshotStateObserver snapshotStateObserver = this.snapshotObserver.observer;
        Snapshot$Companion$$ExternalSyntheticLambda0 snapshot$Companion$$ExternalSyntheticLambda0 = snapshotStateObserver.applyUnsubscribe;
        if (snapshot$Companion$$ExternalSyntheticLambda0 != null) {
            snapshot$Companion$$ExternalSyntheticLambda0.dispose();
        }
        snapshotStateObserver.clear();
        this._windowInfo.getClass();
        ViewTreeOwners viewTreeOwners = getViewTreeOwners();
        Lifecycle lifecycle = (viewTreeOwners == null || (lifecycleOwner = viewTreeOwners.lifecycleOwner) == null) ? null : lifecycleOwner.getLifecycle();
        if (lifecycle == null) {
            throw AndroidAutofill$$ExternalSyntheticOutline0.m("No lifecycle owner exists");
        }
        lifecycle.removeObserver(this.contentCaptureManager);
        lifecycle.removeObserver(this);
        AndroidAutofill androidAutofill = this._autofill;
        if (androidAutofill != null) {
            AutofillCallback autofillCallback = AutofillCallback.INSTANCE;
            autofillCallback.getClass();
            androidAutofill.autofillManager.unregisterCallback(autofillCallback);
        }
        getViewTreeObserver().removeOnGlobalLayoutListener(this.globalLayoutListener);
        getViewTreeObserver().removeOnScrollChangedListener(this.scrollChangedListener);
        getViewTreeObserver().removeOnTouchModeChangeListener(this.touchModeChangeListener);
        AndroidComposeViewTranslationCallbackS.INSTANCE.clearViewTranslationCallback(this);
        AndroidAutofillManager androidAutofillManager = this._autofillManager;
        if (androidAutofillManager != null) {
            this.semanticsOwner.listeners.remove(androidAutofillManager);
            this.focusOwner.listeners.remove(androidAutofillManager);
        }
    }

    public final void onEndApplyChanges() {
        if (this.observationClearRequested) {
            this.snapshotObserver.clearInvalidObservations$ui_release();
            this.observationClearRequested = false;
        }
        AndroidViewsHandler androidViewsHandler = this._androidViewsHandler;
        if (androidViewsHandler != null) {
            clearChildInvalidObservations(androidViewsHandler);
        }
        boolean z = ComposeUiFlags.isRectTrackingEnabled;
        while (this.endApplyChangesListeners.isNotEmpty() && this.endApplyChangesListeners.get(0) != null) {
            int i = this.endApplyChangesListeners._size;
            for (int i2 = 0; i2 < i; i2++) {
                Function0 function0 = (Function0) this.endApplyChangesListeners.get(i2);
                MutableObjectList mutableObjectList = this.endApplyChangesListeners;
                if (i2 < 0 || i2 >= mutableObjectList._size) {
                    mutableObjectList.throwIndexOutOfBoundsExclusiveException$collection(i2);
                    throw null;
                }
                Object[] objArr = mutableObjectList.content;
                Object obj = objArr[i2];
                objArr[i2] = null;
                if (function0 != null) {
                    function0.invoke();
                }
            }
            this.endApplyChangesListeners.removeRange(0, i);
        }
    }

    public final Rect onFetchFocusRect() {
        if (isFocused()) {
            FocusTargetNode focusTargetNodeFindActiveFocusNode = FocusTraversalKt.findActiveFocusNode(this.focusOwner.rootFocusNode);
            if (focusTargetNodeFindActiveFocusNode != null) {
                return FocusTraversalKt.focusRect(focusTargetNodeFindActiveFocusNode);
            }
            return null;
        }
        View viewFindFocus = findFocus();
        if (viewFindFocus != null) {
            return FocusInteropUtils_androidKt.calculateBoundingRectRelativeTo(viewFindFocus, this);
        }
        return null;
    }

    @Override // android.view.View
    public final void onFocusChanged(boolean z, int i, android.graphics.Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (z || hasFocus()) {
            return;
        }
        FocusOwnerImpl focusOwnerImpl = this.focusOwner;
        focusOwnerImpl.getClass();
        boolean z2 = ComposeUiFlags.isTrackFocusEnabled;
        FocusTargetNode focusTargetNode = focusOwnerImpl.rootFocusNode;
        if (z2) {
            FocusTransactionsKt.clearFocus(focusTargetNode, true);
            return;
        }
        FocusTransactionManager focusTransactionManager = focusOwnerImpl.focusTransactionManager;
        if (focusTransactionManager.ongoingTransaction) {
            FocusTransactionsKt.clearFocus(focusTargetNode, true);
            return;
        }
        try {
            focusTransactionManager.ongoingTransaction = true;
            FocusTransactionsKt.clearFocus(focusTargetNode, true);
        } finally {
            FocusTransactionManager.access$commitTransaction(focusTransactionManager);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.lastMatrixRecalculationAnimationTime = 0L;
        this.measureAndLayoutDelegate.measureAndLayout(this.resendMotionEventOnLayout);
        this.onMeasureConstraints = null;
        updatePositionCacheAndDispatch();
        if (this._androidViewsHandler != null) {
            getAndroidViewsHandler$ui_release().layout(0, 0, i3 - i, i4 - i2);
        }
    }

    public final void onLayoutChange(LayoutNode layoutNode) {
        AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = this.composeAccessibilityDelegate;
        androidComposeViewAccessibilityDelegateCompat.currentSemanticsNodesInvalidated = true;
        if (androidComposeViewAccessibilityDelegateCompat.isEnabled$ui_release()) {
            androidComposeViewAccessibilityDelegateCompat.notifySubtreeAccessibilityStateChangedIfNeeded(layoutNode);
        }
        AndroidContentCaptureManager androidContentCaptureManager = this.contentCaptureManager;
        androidContentCaptureManager.currentSemanticsNodesInvalidated = true;
        if (androidContentCaptureManager.isEnabled$ui_release()) {
            androidContentCaptureManager.boundsUpdateChannel.mo3476trySendJP2dKIU(Unit.INSTANCE);
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        Trace.beginSection("AndroidOwner:onMeasure");
        try {
            if (!isAttachedToWindow()) {
                invalidateLayoutNodeMeasurement(this.root);
            }
            long jM692convertMeasureSpecI7RO_PI = m692convertMeasureSpecI7RO_PI(i);
            int i3 = ULong.$r8$clinit;
            long jM692convertMeasureSpecI7RO_PI2 = m692convertMeasureSpecI7RO_PI(i2);
            Constraints.Companion.getClass();
            long jM827fitPrioritizingHeightZbe2FdA = Constraints.Companion.m827fitPrioritizingHeightZbe2FdA((int) (jM692convertMeasureSpecI7RO_PI >>> 32), (int) (jM692convertMeasureSpecI7RO_PI & 4294967295L), (int) (jM692convertMeasureSpecI7RO_PI2 >>> 32), (int) (4294967295L & jM692convertMeasureSpecI7RO_PI2));
            Constraints constraints = this.onMeasureConstraints;
            if (constraints == null) {
                this.onMeasureConstraints = Constraints.m815boximpl(jM827fitPrioritizingHeightZbe2FdA);
                this.wasMeasuredWithMultipleConstraints = false;
            } else if (!Constraints.m817equalsimpl0(constraints.value, jM827fitPrioritizingHeightZbe2FdA)) {
                this.wasMeasuredWithMultipleConstraints = true;
            }
            this.measureAndLayoutDelegate.m660updateRootConstraintsBRTryo0(jM827fitPrioritizingHeightZbe2FdA);
            this.measureAndLayoutDelegate.measureOnly();
            MeasurePassDelegate measurePassDelegate = this.root.layoutDelegate.measurePassDelegate;
            setMeasuredDimension(measurePassDelegate.width, measurePassDelegate.height);
            if (this._androidViewsHandler != null) {
                getAndroidViewsHandler$ui_release().measure(View.MeasureSpec.makeMeasureSpec(this.root.layoutDelegate.measurePassDelegate.width, 1073741824), View.MeasureSpec.makeMeasureSpec(this.root.layoutDelegate.measurePassDelegate.height, 1073741824));
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            Trace.endSection();
        }
    }

    /* renamed from: onMoveFocusInChildren-3ESFkO8, reason: not valid java name */
    public final boolean m697onMoveFocusInChildren3ESFkO8(int i) {
        AndroidViewsHandler androidViewsHandler;
        View viewFindNextFocus;
        android.graphics.Rect androidRect = null;
        if (!ComposeUiFlags.isViewFocusFixEnabled) {
            FocusDirection.Companion.getClass();
            if (i == FocusDirection.Enter || i == FocusDirection.Exit) {
                return false;
            }
            Integer numM370toAndroidFocusDirection3ESFkO8 = FocusInteropUtils_androidKt.m370toAndroidFocusDirection3ESFkO8(i);
            if (numM370toAndroidFocusDirection3ESFkO8 == null) {
                throw new IllegalStateException("Invalid focus direction");
            }
            int iIntValue = numM370toAndroidFocusDirection3ESFkO8.intValue();
            Rect rectOnFetchFocusRect = onFetchFocusRect();
            android.graphics.Rect androidRect2 = rectOnFetchFocusRect != null ? RectHelper_androidKt.toAndroidRect(rectOnFetchFocusRect) : null;
            FocusFinderCompat.Companion.getClass();
            FocusFinderCompat focusFinderCompat = FocusFinderCompat.FocusFinderThreadLocal.get();
            focusFinderCompat.getClass();
            FocusFinderCompat focusFinderCompat2 = focusFinderCompat;
            if (androidRect2 == null) {
                viewFindNextFocus = focusFinderCompat2.findNextFocus(iIntValue, findFocus(), this);
            } else {
                focusFinderCompat2.cachedFocusedRect.set(androidRect2);
                android.graphics.Rect rect = focusFinderCompat2.cachedFocusedRect;
                ArrayList<View> arrayList = focusFinderCompat2.tmpList;
                try {
                    arrayList.clear();
                    addFocusables(arrayList, iIntValue, isInTouchMode() ? 1 : 0);
                    View viewFindNextFocus2 = arrayList.isEmpty() ? null : focusFinderCompat2.findNextFocus(iIntValue, rect, null, this, arrayList);
                    arrayList.clear();
                    viewFindNextFocus = viewFindNextFocus2;
                } catch (Throwable th) {
                    arrayList.clear();
                    throw th;
                }
            }
            if (viewFindNextFocus != null) {
                return FocusInteropUtils_androidKt.requestInteropFocus(viewFindNextFocus, Integer.valueOf(iIntValue), androidRect2);
            }
            return false;
        }
        FocusDirection.Companion.getClass();
        if (i == FocusDirection.Enter || i == FocusDirection.Exit || !hasFocus() || (androidViewsHandler = this._androidViewsHandler) == null) {
            return false;
        }
        Integer numM370toAndroidFocusDirection3ESFkO82 = FocusInteropUtils_androidKt.m370toAndroidFocusDirection3ESFkO8(i);
        if (numM370toAndroidFocusDirection3ESFkO82 == null) {
            throw new IllegalStateException("Invalid focus direction");
        }
        int iIntValue2 = numM370toAndroidFocusDirection3ESFkO82.intValue();
        ViewGroup viewGroup = (ViewGroup) getRootView();
        View viewFindFocus = viewGroup.findFocus();
        if (viewFindFocus == null) {
            throw new IllegalStateException("view hasFocus but root can't find it");
        }
        FocusFinderCompat.Companion.getClass();
        FocusFinderCompat focusFinderCompat3 = FocusFinderCompat.FocusFinderThreadLocal.get();
        focusFinderCompat3.getClass();
        View viewFindNextFocus3 = focusFinderCompat3.findNextFocus(iIntValue2, viewFindFocus, viewGroup);
        if (!FocusOwnerImplKt.m377is1dFocusSearch3ESFkO8(i) || !androidViewsHandler.hasFocus()) {
            Rect rectOnFetchFocusRect2 = onFetchFocusRect();
            androidRect = rectOnFetchFocusRect2 != null ? RectHelper_androidKt.toAndroidRect(rectOnFetchFocusRect2) : null;
            if (viewFindNextFocus3 != null && androidRect != null) {
                viewGroup.offsetDescendantRectToMyCoords(this, androidRect);
                viewGroup.offsetRectIntoDescendantCoords(viewFindNextFocus3, androidRect);
            }
        }
        if (viewFindNextFocus3 == null || viewFindNextFocus3 == viewFindFocus) {
            return false;
        }
        View focusedChild = androidViewsHandler.getFocusedChild();
        ViewParent parent = viewFindNextFocus3.getParent();
        while (parent != null && parent != focusedChild) {
            parent = parent.getParent();
        }
        if (parent == null) {
            return false;
        }
        return FocusInteropUtils_androidKt.requestInteropFocus(viewFindNextFocus3, Integer.valueOf(iIntValue2), androidRect);
    }

    @Override // android.view.View
    public final void onProvideAutofillVirtualStructure(ViewStructure viewStructure, int i) {
        if (viewStructure != null) {
            boolean z = ComposeUiFlags.isRectTrackingEnabled;
            AndroidAutofill androidAutofill = this._autofill;
            if (androidAutofill != null) {
                AndroidAutofill_androidKt.populateViewStructure(androidAutofill, viewStructure);
            }
        }
    }

    public final void onRequestMeasure(LayoutNode layoutNode, boolean z, boolean z2, boolean z3) {
        LayoutNode parent$ui_release;
        LayoutNode parent$ui_release2;
        LookaheadPassDelegate lookaheadPassDelegate;
        LookaheadAlignmentLines lookaheadAlignmentLines;
        if (!z) {
            if (this.measureAndLayoutDelegate.requestRemeasure(layoutNode, z2) && z3) {
                scheduleMeasureAndLayout(layoutNode);
                return;
            }
            return;
        }
        MeasureAndLayoutDelegate measureAndLayoutDelegate = this.measureAndLayoutDelegate;
        measureAndLayoutDelegate.getClass();
        if (layoutNode.lookaheadRoot == null) {
            InlineClassHelperKt.throwIllegalStateException("Error: requestLookaheadRemeasure cannot be called on a node outside LookaheadScope");
        }
        LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = layoutNode.layoutDelegate;
        int i = MeasureAndLayoutDelegate.WhenMappings.$EnumSwitchMapping$0[layoutNodeLayoutDelegate.layoutState.ordinal()];
        if (i != 1) {
            if (i == 2 || i == 3 || i == 4) {
                measureAndLayoutDelegate.postponedMeasureRequests.add(new MeasureAndLayoutDelegate.PostponedRequest(layoutNode, true, z2));
                return;
            }
            if (i != 5) {
                throw new NoWhenBranchMatchedException();
            }
            if (!layoutNodeLayoutDelegate.lookaheadMeasurePending || z2) {
                layoutNodeLayoutDelegate.lookaheadMeasurePending = true;
                layoutNodeLayoutDelegate.measurePassDelegate.measurePending = true;
                if (layoutNode.isDeactivated) {
                    return;
                }
                boolean zAreEqual = Intrinsics.areEqual(layoutNode.isPlacedInLookahead(), Boolean.TRUE);
                DepthSortedSetsForDifferentPasses depthSortedSetsForDifferentPasses = measureAndLayoutDelegate.relayoutNodes;
                if ((zAreEqual || (layoutNodeLayoutDelegate.lookaheadMeasurePending && (layoutNode.getMeasuredByParentInLookahead$ui_release() == LayoutNode.UsageByParent.InMeasureBlock || !((lookaheadPassDelegate = layoutNodeLayoutDelegate.lookaheadPassDelegate) == null || (lookaheadAlignmentLines = lookaheadPassDelegate.alignmentLines) == null || !lookaheadAlignmentLines.getRequired$ui_release())))) && ((parent$ui_release = layoutNode.getParent$ui_release()) == null || !parent$ui_release.layoutDelegate.lookaheadMeasurePending)) {
                    depthSortedSetsForDifferentPasses.add(layoutNode, true);
                } else if ((layoutNode.isPlaced() || (layoutNode.getMeasurePending$ui_release() && MeasureAndLayoutDelegate.getMeasureAffectsParent(layoutNode))) && ((parent$ui_release2 = layoutNode.getParent$ui_release()) == null || !parent$ui_release2.getMeasurePending$ui_release())) {
                    depthSortedSetsForDifferentPasses.add(layoutNode, false);
                }
                if (measureAndLayoutDelegate.duringFullMeasureLayoutPass || !z3) {
                    return;
                }
                scheduleMeasureAndLayout(layoutNode);
            }
        }
    }

    public final void onRequestRelayout(LayoutNode layoutNode, boolean z, boolean z2) {
        if (!z) {
            MeasureAndLayoutDelegate measureAndLayoutDelegate = this.measureAndLayoutDelegate;
            measureAndLayoutDelegate.getClass();
            int i = MeasureAndLayoutDelegate.WhenMappings.$EnumSwitchMapping$0[layoutNode.layoutDelegate.layoutState.ordinal()];
            if (i == 1 || i == 2 || i == 3 || i == 4) {
                return;
            }
            if (i != 5) {
                throw new NoWhenBranchMatchedException();
            }
            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate = layoutNode.layoutDelegate;
            if (!z2 && layoutNode.isPlaced() == layoutNodeLayoutDelegate.measurePassDelegate.isPlacedByParent && (layoutNode.getMeasurePending$ui_release() || layoutNode.getLayoutPending$ui_release())) {
                return;
            }
            MeasurePassDelegate measurePassDelegate = layoutNodeLayoutDelegate.measurePassDelegate;
            measurePassDelegate.layoutPending = true;
            measurePassDelegate.layoutPendingForAlignment = true;
            if (!layoutNode.isDeactivated && measurePassDelegate.isPlacedByParent) {
                LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
                if ((parent$ui_release == null || !parent$ui_release.getLayoutPending$ui_release()) && (parent$ui_release == null || !parent$ui_release.getMeasurePending$ui_release())) {
                    measureAndLayoutDelegate.relayoutNodes.add(layoutNode, false);
                }
                if (measureAndLayoutDelegate.duringFullMeasureLayoutPass) {
                    return;
                }
                scheduleMeasureAndLayout(null);
                return;
            }
            return;
        }
        MeasureAndLayoutDelegate measureAndLayoutDelegate2 = this.measureAndLayoutDelegate;
        measureAndLayoutDelegate2.getClass();
        int i2 = MeasureAndLayoutDelegate.WhenMappings.$EnumSwitchMapping$0[layoutNode.layoutDelegate.layoutState.ordinal()];
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 == 3) {
                    return;
                }
                if (i2 != 4 && i2 != 5) {
                    throw new NoWhenBranchMatchedException();
                }
            }
            LayoutNodeLayoutDelegate layoutNodeLayoutDelegate2 = layoutNode.layoutDelegate;
            if ((layoutNodeLayoutDelegate2.lookaheadMeasurePending || layoutNodeLayoutDelegate2.lookaheadLayoutPending) && !z2) {
                return;
            }
            layoutNodeLayoutDelegate2.lookaheadLayoutPending = true;
            layoutNodeLayoutDelegate2.lookaheadLayoutPendingForAlignment = true;
            MeasurePassDelegate measurePassDelegate2 = layoutNodeLayoutDelegate2.measurePassDelegate;
            measurePassDelegate2.layoutPending = true;
            measurePassDelegate2.layoutPendingForAlignment = true;
            if (layoutNode.isDeactivated) {
                return;
            }
            LayoutNode parent$ui_release2 = layoutNode.getParent$ui_release();
            boolean zAreEqual = Intrinsics.areEqual(layoutNode.isPlacedInLookahead(), Boolean.TRUE);
            DepthSortedSetsForDifferentPasses depthSortedSetsForDifferentPasses = measureAndLayoutDelegate2.relayoutNodes;
            if (zAreEqual && ((parent$ui_release2 == null || !parent$ui_release2.layoutDelegate.lookaheadMeasurePending) && (parent$ui_release2 == null || !parent$ui_release2.layoutDelegate.lookaheadLayoutPending))) {
                depthSortedSetsForDifferentPasses.add(layoutNode, true);
            } else if (layoutNode.isPlaced() && ((parent$ui_release2 == null || !parent$ui_release2.getLayoutPending$ui_release()) && (parent$ui_release2 == null || !parent$ui_release2.getMeasurePending$ui_release()))) {
                depthSortedSetsForDifferentPasses.add(layoutNode, false);
            }
            if (measureAndLayoutDelegate2.duringFullMeasureLayoutPass) {
                return;
            }
            scheduleMeasureAndLayout(null);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        androidx.compose.ui.input.pointer.PointerIcon pointerIcon;
        int toolType = motionEvent.getToolType(i);
        if (motionEvent.isFromSource(8194) || !motionEvent.isFromSource(16386) || (!(toolType == 2 || toolType == 4) || (pointerIcon = this.pointerIconService.currentStylusHoverIcon) == null)) {
            return super.onResolvePointerIcon(motionEvent, i);
        }
        AndroidComposeViewVerificationHelperMethodsN androidComposeViewVerificationHelperMethodsN = AndroidComposeViewVerificationHelperMethodsN.INSTANCE;
        Context context = getContext();
        androidComposeViewVerificationHelperMethodsN.getClass();
        return pointerIcon instanceof AndroidPointerIcon ? ((AndroidPointerIcon) pointerIcon).pointerIcon : pointerIcon instanceof AndroidPointerIconType ? PointerIcon.getSystemIcon(context, ((AndroidPointerIconType) pointerIcon).type) : PointerIcon.getSystemIcon(context, 1000);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public final void onResume$1() {
        this.showLayoutBounds = Companion.access$getIsShowingLayoutBounds(Companion);
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        if (this.superclassInitComplete) {
            LayoutDirection layoutDirection = i != 0 ? i != 1 ? null : LayoutDirection.Rtl : LayoutDirection.Ltr;
            if (layoutDirection == null) {
                layoutDirection = LayoutDirection.Ltr;
            }
            ((SnapshotMutableStateImpl) this.layoutDirection$delegate).setValue(layoutDirection);
        }
    }

    @Override // android.view.View
    public final void onScrollCaptureSearch(android.graphics.Rect rect, Point point, Consumer consumer) {
        ScrollCapture scrollCapture = this.scrollCapture;
        if (scrollCapture != null) {
            scrollCapture.onScrollCaptureSearch(this, this.semanticsOwner, this.coroutineContext, consumer);
        }
    }

    public final void onSemanticsChange() {
        AndroidComposeViewAccessibilityDelegateCompat androidComposeViewAccessibilityDelegateCompat = this.composeAccessibilityDelegate;
        androidComposeViewAccessibilityDelegateCompat.currentSemanticsNodesInvalidated = true;
        if (androidComposeViewAccessibilityDelegateCompat.isEnabled$ui_release() && !androidComposeViewAccessibilityDelegateCompat.checkingForSemanticsChanges) {
            androidComposeViewAccessibilityDelegateCompat.checkingForSemanticsChanges = true;
            androidComposeViewAccessibilityDelegateCompat.handler.post(androidComposeViewAccessibilityDelegateCompat.semanticsChangeChecker);
        }
        AndroidContentCaptureManager androidContentCaptureManager = this.contentCaptureManager;
        androidContentCaptureManager.currentSemanticsNodesInvalidated = true;
        if (!androidContentCaptureManager.isEnabled$ui_release() || androidContentCaptureManager.checkingForSemanticsChanges) {
            return;
        }
        androidContentCaptureManager.checkingForSemanticsChanges = true;
        androidContentCaptureManager.handler.post(androidContentCaptureManager.contentCaptureChangeChecker);
    }

    @Override // android.view.View
    public final void onVirtualViewTranslationResponses(LongSparseArray longSparseArray) {
        AndroidContentCaptureManager androidContentCaptureManager = this.contentCaptureManager;
        androidContentCaptureManager.getClass();
        AndroidContentCaptureManager.onVirtualViewTranslationResponses$ui_release(androidContentCaptureManager, longSparseArray);
    }

    @Override // android.view.View
    public final void onWindowFocusChanged(boolean z) {
        boolean zAccess$getIsShowingLayoutBounds;
        ((SnapshotMutableStateImpl) this._windowInfo.isWindowFocused$delegate).setValue(Boolean.valueOf(z));
        this.keyboardModifiersRequireUpdate = true;
        super.onWindowFocusChanged(z);
        if (!z || this.showLayoutBounds == (zAccess$getIsShowingLayoutBounds = Companion.access$getIsShowingLayoutBounds(Companion))) {
            return;
        }
        this.showLayoutBounds = zAccess$getIsShowingLayoutBounds;
        invalidateLayers(this.root);
    }

    public final void recalculateWindowPosition() {
        if (this.forceUseMatrixCache) {
            return;
        }
        long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        if (jCurrentAnimationTimeMillis != this.lastMatrixRecalculationAnimationTime) {
            this.lastMatrixRecalculationAnimationTime = jCurrentAnimationTimeMillis;
            this.matrixToWindow.mo703calculateMatrixToWindowEL8BTi8(this, this.viewToWindowMatrix);
            InvertMatrixKt.m706invertToJiSxe2E(this.viewToWindowMatrix, this.windowToViewMatrix);
            ViewParent parent = getParent();
            View view = this;
            while (parent instanceof ViewGroup) {
                view = (View) parent;
                parent = ((ViewGroup) view).getParent();
            }
            view.getLocationOnScreen(this.tmpPositionArray);
            int[] iArr = this.tmpPositionArray;
            float f = iArr[0];
            float f2 = iArr[1];
            view.getLocationInWindow(iArr);
            float f3 = this.tmpPositionArray[0];
            float f4 = f2 - r0[1];
            Offset.Companion companion = Offset.Companion;
            this.windowPosition = (Float.floatToRawIntBits(f - f3) << 32) | (Float.floatToRawIntBits(f4) & 4294967295L);
        }
    }

    public final void recycle$ui_release(OwnedLayer ownedLayer) {
        Reference referencePoll;
        MutableVector mutableVector;
        if (this.viewLayersContainer != null) {
            ViewLayer.Companion.getClass();
        }
        WeakCache weakCache = this.layerCache;
        do {
            referencePoll = weakCache.referenceQueue.poll();
            mutableVector = weakCache.values;
            if (referencePoll != null) {
                mutableVector.remove(referencePoll);
            }
        } while (referencePoll != null);
        mutableVector.add(new WeakReference(ownedLayer, weakCache.referenceQueue));
        this.dirtyLayers.remove(ownedLayer);
    }

    public final void registerOnEndApplyChangesListener(Function0 function0) {
        if (this.endApplyChangesListeners.indexOf(function0) >= 0) {
            return;
        }
        this.endApplyChangesListeners.add(function0);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean requestFocus(int i, android.graphics.Rect rect) {
        final int i2;
        View viewFindNextNonChildView;
        final int i3;
        if (ComposeUiFlags.isViewFocusFixEnabled) {
            if (!isFocused()) {
                if (!this.processingRequestFocusForNextNonChildView && !this.focusOwner.focusTransactionManager.ongoingTransaction) {
                    FocusDirection focusDirection = FocusInteropUtils_androidKt.toFocusDirection(i);
                    if (focusDirection != null) {
                        i2 = focusDirection.value;
                    } else {
                        FocusDirection.Companion.getClass();
                        i2 = FocusDirection.Enter;
                    }
                    if (!hasFocus() || !m697onMoveFocusInChildren3ESFkO8(i2)) {
                        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                        Boolean boolM374focusSearchULY8qGw = this.focusOwner.m374focusSearchULY8qGw(i2, rect != null ? RectHelper_androidKt.toComposeRect(rect) : null, new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeView$requestFocus$focusSearchResult$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                ref$BooleanRef.element = true;
                                return Boolean.valueOf(((FocusTargetNode) obj).m380requestFocus3ESFkO8(i2));
                            }
                        });
                        if (boolM374focusSearchULY8qGw != null) {
                            if (!boolM374focusSearchULY8qGw.booleanValue()) {
                                if (!ref$BooleanRef.element) {
                                    if ((rect == null || hasFocus() || !Intrinsics.areEqual(this.focusOwner.m374focusSearchULY8qGw(i2, null, new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeView$requestFocus$altFocus$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(1);
                                        }

                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj) {
                                            return Boolean.valueOf(((FocusTargetNode) obj).m380requestFocus3ESFkO8(i2));
                                        }
                                    }), Boolean.TRUE)) && (viewFindNextNonChildView = findNextNonChildView(i)) != null && viewFindNextNonChildView != this) {
                                        this.processingRequestFocusForNextNonChildView = true;
                                        boolean zRequestFocus = viewFindNextNonChildView.requestFocus(i);
                                        this.processingRequestFocusForNextNonChildView = false;
                                        return zRequestFocus;
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }
        } else if (!isFocused()) {
            if (this.focusOwner.rootFocusNode.getFocusState().getHasFocus()) {
                return super.requestFocus(i, rect);
            }
            FocusDirection focusDirection2 = FocusInteropUtils_androidKt.toFocusDirection(i);
            if (focusDirection2 != null) {
                i3 = focusDirection2.value;
            } else {
                FocusDirection.Companion.getClass();
                i3 = FocusDirection.Enter;
            }
            return Intrinsics.areEqual(this.focusOwner.m374focusSearchULY8qGw(i3, rect != null ? RectHelper_androidKt.toComposeRect(rect) : null, new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeView.requestFocus.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    return Boolean.valueOf(((FocusTargetNode) obj).m380requestFocus3ESFkO8(i3));
                }
            }), Boolean.TRUE);
        }
        return true;
    }

    public final void scheduleMeasureAndLayout(LayoutNode layoutNode) {
        if (isLayoutRequested() || !isAttachedToWindow()) {
            return;
        }
        if (layoutNode != null) {
            while (layoutNode != null && layoutNode.getMeasuredByParent$ui_release() == LayoutNode.UsageByParent.InMeasureBlock) {
                if (!this.wasMeasuredWithMultipleConstraints) {
                    LayoutNode parent$ui_release = layoutNode.getParent$ui_release();
                    if (parent$ui_release == null) {
                        break;
                    }
                    long j = parent$ui_release.nodes.innerCoordinator.measurementConstraints;
                    if (Constraints.m821getHasFixedWidthimpl(j) && Constraints.m820getHasFixedHeightimpl(j)) {
                        break;
                    }
                }
                layoutNode = layoutNode.getParent$ui_release();
            }
            if (layoutNode == this.root) {
                requestLayout();
                return;
            }
        }
        if (getWidth() == 0 || getHeight() == 0) {
            requestLayout();
        } else {
            invalidate();
        }
    }

    /* renamed from: screenToLocal-MK-Hz9U, reason: not valid java name */
    public final long m698screenToLocalMKHz9U(long j) {
        recalculateWindowPosition();
        float fIntBitsToFloat = Float.intBitsToFloat((int) (j >> 32)) - Float.intBitsToFloat((int) (this.windowPosition >> 32));
        float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) - Float.intBitsToFloat((int) (this.windowPosition & 4294967295L));
        float[] fArr = this.windowToViewMatrix;
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fIntBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(fIntBitsToFloat) << 32);
        Offset.Companion companion = Offset.Companion;
        return Matrix.m484mapMKHz9U(jFloatToRawIntBits, fArr);
    }

    /* renamed from: sendMotionEvent-8iAsVTc, reason: not valid java name */
    public final int m699sendMotionEvent8iAsVTc(MotionEvent motionEvent) {
        Object obj;
        if (this.keyboardModifiersRequireUpdate) {
            this.keyboardModifiersRequireUpdate = false;
            LazyWindowInfo lazyWindowInfo = this._windowInfo;
            int metaState = motionEvent.getMetaState();
            lazyWindowInfo.getClass();
            WindowInfoImpl.Companion.getClass();
            ((SnapshotMutableStateImpl) WindowInfoImpl.GlobalKeyboardModifiers).setValue(PointerKeyboardModifiers.m598boximpl(metaState));
        }
        PointerInputEvent pointerInputEventConvertToPointerInputEvent$ui_release = this.motionEventAdapter.convertToPointerInputEvent$ui_release(this, motionEvent);
        if (pointerInputEventConvertToPointerInputEvent$ui_release == null) {
            this.pointerInputEventProcessor.processCancel();
            return 0;
        }
        List list = pointerInputEventConvertToPointerInputEvent$ui_release.pointers;
        int size = list.size() - 1;
        if (size >= 0) {
            while (true) {
                int i = size - 1;
                obj = list.get(size);
                if (((PointerInputEventData) obj).down) {
                    break;
                }
                if (i < 0) {
                    break;
                }
                size = i;
            }
            obj = null;
        } else {
            obj = null;
        }
        PointerInputEventData pointerInputEventData = (PointerInputEventData) obj;
        if (pointerInputEventData != null) {
            this.lastDownPointerPosition = pointerInputEventData.position;
        }
        int iM595processBIzXfog = this.pointerInputEventProcessor.m595processBIzXfog(pointerInputEventConvertToPointerInputEvent$ui_release, this, isInBounds(motionEvent));
        int actionMasked = motionEvent.getActionMasked();
        if ((actionMasked != 0 && actionMasked != 5) || (iM595processBIzXfog & 1) != 0) {
            return iM595processBIzXfog;
        }
        MotionEventAdapter motionEventAdapter = this.motionEventAdapter;
        int pointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
        motionEventAdapter.activeHoverIds.delete(pointerId);
        motionEventAdapter.motionEventToComposePointerIdMap.delete(pointerId);
        return iM595processBIzXfog;
    }

    public final void sendSimulatedEvent(MotionEvent motionEvent, int i, long j, boolean z) {
        int i2 = 1;
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = -1;
        if (actionMasked != 1) {
            if (actionMasked == 6) {
                actionIndex = motionEvent.getActionIndex();
            }
        } else if (i != 9 && i != 10) {
            actionIndex = 0;
        }
        int pointerCount = motionEvent.getPointerCount() - (actionIndex >= 0 ? 1 : 0);
        if (pointerCount == 0) {
            return;
        }
        MotionEvent.PointerProperties[] pointerPropertiesArr = new MotionEvent.PointerProperties[pointerCount];
        for (int i3 = 0; i3 < pointerCount; i3++) {
            pointerPropertiesArr[i3] = new MotionEvent.PointerProperties();
        }
        MotionEvent.PointerCoords[] pointerCoordsArr = new MotionEvent.PointerCoords[pointerCount];
        for (int i4 = 0; i4 < pointerCount; i4++) {
            pointerCoordsArr[i4] = new MotionEvent.PointerCoords();
        }
        int i5 = 0;
        while (i5 < pointerCount) {
            int i6 = ((actionIndex < 0 || i5 < actionIndex) ? 0 : i2) + i5;
            motionEvent.getPointerProperties(i6, pointerPropertiesArr[i5]);
            MotionEvent.PointerCoords pointerCoords = pointerCoordsArr[i5];
            motionEvent.getPointerCoords(i6, pointerCoords);
            float f = pointerCoords.x;
            float f2 = pointerCoords.y;
            long jFloatToRawIntBits = Float.floatToRawIntBits(f);
            int iFloatToRawIntBits = Float.floatToRawIntBits(f2);
            int i7 = i2;
            long j2 = (iFloatToRawIntBits & 4294967295L) | (jFloatToRawIntBits << 32);
            Offset.Companion companion = Offset.Companion;
            long jM695localToScreenMKHz9U = m695localToScreenMKHz9U(j2);
            pointerCoords.x = Float.intBitsToFloat((int) (jM695localToScreenMKHz9U >> 32));
            pointerCoords.y = Float.intBitsToFloat((int) (jM695localToScreenMKHz9U & 4294967295L));
            i5 += i7;
            i2 = i7;
            pointerCount = pointerCount;
        }
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent.getDownTime() == motionEvent.getEventTime() ? j : motionEvent.getDownTime(), j, i, pointerCount, pointerPropertiesArr, pointerCoordsArr, motionEvent.getMetaState(), z ? 0 : motionEvent.getButtonState(), motionEvent.getXPrecision(), motionEvent.getYPrecision(), motionEvent.getDeviceId(), motionEvent.getEdgeFlags(), motionEvent.getSource(), motionEvent.getFlags());
        PointerInputEvent pointerInputEventConvertToPointerInputEvent$ui_release = this.motionEventAdapter.convertToPointerInputEvent$ui_release(this, motionEventObtain);
        pointerInputEventConvertToPointerInputEvent$ui_release.getClass();
        this.pointerInputEventProcessor.m595processBIzXfog(pointerInputEventConvertToPointerInputEvent$ui_release, this, true);
        motionEventObtain.recycle();
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final CoroutineSingletons textInputSession(Function2 function2, ContinuationImpl continuationImpl) {
        C07531 c07531;
        if (continuationImpl instanceof C07531) {
            c07531 = (C07531) continuationImpl;
            int i = c07531.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c07531.label = i - Integer.MIN_VALUE;
            } else {
                c07531 = new C07531(continuationImpl);
            }
        }
        Object obj = c07531.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c07531.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            AtomicReference atomicReference = this.textInputSessionMutex;
            Function1 function1 = new Function1() { // from class: androidx.compose.ui.platform.AndroidComposeView.textInputSession.2
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    AndroidComposeView androidComposeView = AndroidComposeView.this;
                    return new AndroidPlatformTextInputSession(androidComposeView, androidComposeView.textInputService, (CoroutineScope) obj2);
                }
            };
            c07531.label = 1;
            if (SessionMutex.m355withSessionCancellingPreviousimpl(atomicReference, function1, function2, c07531) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updatePositionCacheAndDispatch() {
        boolean z;
        boolean z2;
        getLocationOnScreen(this.tmpPositionArray);
        long j = this.globalPosition;
        IntOffset.Companion companion = IntOffset.Companion;
        int i = (int) (j >> 32);
        int i2 = (int) (j & 4294967295L);
        int[] iArr = this.tmpPositionArray;
        int i3 = iArr[0];
        if (i != i3 || i2 != iArr[1] || this.lastMatrixRecalculationAnimationTime < 0) {
            this.globalPosition = (iArr[1] & 4294967295L) | (i3 << 32);
            if (i == Integer.MAX_VALUE || i2 == Integer.MAX_VALUE) {
                z = false;
            } else {
                this.root.layoutDelegate.measurePassDelegate.notifyChildrenUsingCoordinatesWhilePlacing();
                z = true;
            }
        }
        recalculateWindowPosition();
        RectManager rectManager = this.rectManager;
        long j2 = this.globalPosition;
        long jM856roundk4lQ0M = IntOffsetKt.m856roundk4lQ0M(this.windowPosition);
        float[] fArr = this.viewToWindowMatrix;
        rectManager.getClass();
        if ((RectManagerKt.m723access$analyzeComponents58bKbWc(fArr) & 2) != 0) {
            fArr = null;
        }
        ThrottledCallbacks throttledCallbacks = rectManager.throttledCallbacks;
        if (IntOffset.m851equalsimpl0(jM856roundk4lQ0M, throttledCallbacks.windowOffset)) {
            z2 = false;
        } else {
            throttledCallbacks.windowOffset = jM856roundk4lQ0M;
            z2 = true;
        }
        if (!IntOffset.m851equalsimpl0(j2, throttledCallbacks.screenOffset)) {
            throttledCallbacks.screenOffset = j2;
            z2 = true;
        }
        if (fArr != null) {
            throttledCallbacks.viewToWindowMatrix = fArr;
            z2 = true;
        }
        rectManager.isScreenOrWindowDirty = z2 || rectManager.isScreenOrWindowDirty;
        this.measureAndLayoutDelegate.dispatchOnPositionedCallbacks(z);
        if (ComposeUiFlags.isRectTrackingEnabled) {
            this.rectManager.dispatchCallbacks();
        }
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i) {
        view.getClass();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = generateDefaultLayoutParams();
        }
        addViewInLayout(view, i, layoutParams, true);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, int i2) {
        ViewGroup.LayoutParams layoutParamsGenerateDefaultLayoutParams = generateDefaultLayoutParams();
        layoutParamsGenerateDefaultLayoutParams.width = i;
        layoutParamsGenerateDefaultLayoutParams.height = i2;
        Unit unit = Unit.INSTANCE;
        addViewInLayout(view, -1, layoutParamsGenerateDefaultLayoutParams, true);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        addViewInLayout(view, i, layoutParams, true);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final void addView(View view, ViewGroup.LayoutParams layoutParams) {
        addViewInLayout(view, -1, layoutParams, true);
    }

    public final void recalculateWindowPosition(MotionEvent motionEvent) {
        this.lastMatrixRecalculationAnimationTime = AnimationUtils.currentAnimationTimeMillis();
        this.matrixToWindow.mo703calculateMatrixToWindowEL8BTi8(this, this.viewToWindowMatrix);
        InvertMatrixKt.m706invertToJiSxe2E(this.viewToWindowMatrix, this.windowToViewMatrix);
        float[] fArr = this.viewToWindowMatrix;
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        long jFloatToRawIntBits = (Float.floatToRawIntBits(y) & 4294967295L) | (Float.floatToRawIntBits(x) << 32);
        Offset.Companion companion = Offset.Companion;
        long jM484mapMKHz9U = Matrix.m484mapMKHz9U(jFloatToRawIntBits, fArr);
        float rawX = motionEvent.getRawX() - Float.intBitsToFloat((int) (jM484mapMKHz9U >> 32));
        float rawY = motionEvent.getRawY() - Float.intBitsToFloat((int) (jM484mapMKHz9U & 4294967295L));
        this.windowPosition = (Float.floatToRawIntBits(rawX) << 32) | (Float.floatToRawIntBits(rawY) & 4294967295L);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
    }
}
