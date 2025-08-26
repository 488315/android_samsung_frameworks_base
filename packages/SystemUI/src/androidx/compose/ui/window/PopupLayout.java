package androidx.compose.ui.window;

import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.runtime.snapshots.Snapshot$Companion$$ExternalSyntheticLambda0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.platform.AbstractComposeView;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntRect;
import androidx.compose.ui.unit.IntRectKt;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;
import com.android.systemui.R;
import java.util.UUID;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$LongRef;

/* loaded from: classes.dex */
public final class PopupLayout extends AbstractComposeView {
    public static final Function1 onCommitAffectingPopupPosition;
    public Api33Impl$$ExternalSyntheticLambda0 backCallback;
    public final State canCalculatePosition$delegate;
    public final View composeView;
    public final MutableState content$delegate;
    public final int[] locationOnScreen;
    public Function0 onDismissRequest;
    public final WindowManager.LayoutParams params;
    public IntRect parentBounds;
    public final MutableState parentLayoutCoordinates$delegate;
    public LayoutDirection parentLayoutDirection;
    public final MutableState popupContentSize$delegate;
    public final PopupLayoutHelper popupLayoutHelper;
    public PopupPositionProvider positionProvider;
    public final Rect previousWindowVisibleFrame;
    public PopupProperties properties;
    public boolean shouldCreateCompositionOnAttachedToWindow;
    public final SnapshotStateObserver snapshotStateObserver;
    public final WindowManager windowManager;

    final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LayoutDirection.values().length];
            try {
                iArr[LayoutDirection.Ltr.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LayoutDirection.Rtl.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
        onCommitAffectingPopupPosition = new Function1() { // from class: androidx.compose.ui.window.PopupLayout$Companion$onCommitAffectingPopupPosition$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                PopupLayout popupLayout = (PopupLayout) obj;
                if (popupLayout.isAttachedToWindow()) {
                    popupLayout.updatePosition();
                }
                return Unit.INSTANCE;
            }
        };
    }

    public /* synthetic */ PopupLayout(Function0 function0, PopupProperties popupProperties, String str, View view, Density density, PopupPositionProvider popupPositionProvider, UUID uuid, PopupLayoutHelper popupLayoutHelper, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(function0, popupProperties, str, view, density, popupPositionProvider, uuid, (i & 128) != 0 ? new PopupLayoutHelperImpl29() : popupLayoutHelper);
    }

    @Override // androidx.compose.ui.platform.AbstractComposeView
    public final void Content(ComposerImpl composerImpl) {
        composerImpl.startReplaceGroup(-857613600);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.ui.window.PopupLayout.Content (AndroidPopup.android.kt:572)");
        }
        ((Function2) ((SnapshotMutableStateImpl) this.content$delegate).getValue()).invoke(composerImpl, 0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!this.properties.dismissOnBackPress) {
            return super.dispatchKeyEvent(keyEvent);
        }
        if (keyEvent.getKeyCode() == 4 || keyEvent.getKeyCode() == 111) {
            KeyEvent.DispatcherState keyDispatcherState = getKeyDispatcherState();
            if (keyDispatcherState == null) {
                return super.dispatchKeyEvent(keyEvent);
            }
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                keyDispatcherState.startTracking(keyEvent, this);
                return true;
            }
            if (keyEvent.getAction() == 1 && keyDispatcherState.isTracking(keyEvent) && !keyEvent.isCanceled()) {
                Function0 function0 = this.onDismissRequest;
                if (function0 != null) {
                    function0.invoke();
                }
                return true;
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // androidx.compose.ui.platform.AbstractComposeView
    public final boolean getShouldCreateCompositionOnAttachedToWindow() {
        return this.shouldCreateCompositionOnAttachedToWindow;
    }

    public final IntRect getVisibleDisplayBounds() {
        Rect rect = this.previousWindowVisibleFrame;
        PopupLayoutHelper popupLayoutHelper = this.popupLayoutHelper;
        View view = this.composeView;
        ((PopupLayoutHelperImpl) popupLayoutHelper).getClass();
        view.getWindowVisibleDisplayFrame(rect);
        DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = AndroidPopup_androidKt.LocalPopupTestTag;
        return new IntRect(rect.left, rect.top, rect.right, rect.bottom);
    }

    @Override // androidx.compose.ui.platform.AbstractComposeView
    public final void internalOnLayout$ui_release(boolean z, int i, int i2, int i3, int i4) {
        View childAt;
        super.internalOnLayout$ui_release(z, i, i2, i3, i4);
        if (this.properties.usePlatformDefaultWidth || (childAt = getChildAt(0)) == null) {
            return;
        }
        this.params.width = childAt.getMeasuredWidth();
        this.params.height = childAt.getMeasuredHeight();
        PopupLayoutHelper popupLayoutHelper = this.popupLayoutHelper;
        WindowManager windowManager = this.windowManager;
        WindowManager.LayoutParams layoutParams = this.params;
        ((PopupLayoutHelperImpl) popupLayoutHelper).getClass();
        windowManager.updateViewLayout(this, layoutParams);
    }

    @Override // androidx.compose.ui.platform.AbstractComposeView
    public final void internalOnMeasure$ui_release(int i, int i2) {
        if (this.properties.usePlatformDefaultWidth) {
            super.internalOnMeasure$ui_release(i, i2);
        } else {
            IntRect visibleDisplayBounds = getVisibleDisplayBounds();
            super.internalOnMeasure$ui_release(View.MeasureSpec.makeMeasureSpec(visibleDisplayBounds.getWidth(), Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(visibleDisplayBounds.getHeight(), Integer.MIN_VALUE));
        }
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [androidx.compose.ui.window.Api33Impl$$ExternalSyntheticLambda0] */
    @Override // androidx.compose.ui.platform.AbstractComposeView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        OnBackInvokedDispatcher onBackInvokedDispatcherFindOnBackInvokedDispatcher;
        super.onAttachedToWindow();
        SnapshotStateObserver snapshotStateObserver = this.snapshotStateObserver;
        snapshotStateObserver.getClass();
        Snapshot.Companion companion = Snapshot.Companion;
        Function2 function2 = snapshotStateObserver.applyObserver;
        companion.getClass();
        snapshotStateObserver.applyUnsubscribe = Snapshot.Companion.registerApplyObserver(function2);
        if (this.properties.dismissOnBackPress) {
            if (this.backCallback == null) {
                final Function0 function0 = this.onDismissRequest;
                int i = Api33Impl.$r8$clinit;
                this.backCallback = new OnBackInvokedCallback() { // from class: androidx.compose.ui.window.Api33Impl$$ExternalSyntheticLambda0
                    @Override // android.window.OnBackInvokedCallback
                    public final void onBackInvoked() {
                        Function0 function02 = function0;
                        int i2 = Api33Impl.$r8$clinit;
                        if (function02 != null) {
                            function02.invoke();
                        }
                    }
                };
            }
            Api33Impl$$ExternalSyntheticLambda0 api33Impl$$ExternalSyntheticLambda0 = this.backCallback;
            int i2 = Api33Impl.$r8$clinit;
            if (api33Impl$$ExternalSyntheticLambda0 == null || (onBackInvokedDispatcherFindOnBackInvokedDispatcher = findOnBackInvokedDispatcher()) == null) {
                return;
            }
            onBackInvokedDispatcherFindOnBackInvokedDispatcher.registerOnBackInvokedCallback(1000000, api33Impl$$ExternalSyntheticLambda0);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        OnBackInvokedDispatcher onBackInvokedDispatcherFindOnBackInvokedDispatcher;
        super.onDetachedFromWindow();
        Snapshot$Companion$$ExternalSyntheticLambda0 snapshot$Companion$$ExternalSyntheticLambda0 = this.snapshotStateObserver.applyUnsubscribe;
        if (snapshot$Companion$$ExternalSyntheticLambda0 != null) {
            snapshot$Companion$$ExternalSyntheticLambda0.dispose();
        }
        this.snapshotStateObserver.clear();
        Api33Impl$$ExternalSyntheticLambda0 api33Impl$$ExternalSyntheticLambda0 = this.backCallback;
        int i = Api33Impl.$r8$clinit;
        if (api33Impl$$ExternalSyntheticLambda0 != null && (onBackInvokedDispatcherFindOnBackInvokedDispatcher = findOnBackInvokedDispatcher()) != null) {
            onBackInvokedDispatcherFindOnBackInvokedDispatcher.unregisterOnBackInvokedCallback(api33Impl$$ExternalSyntheticLambda0);
        }
        this.backCallback = null;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.properties.dismissOnClickOutside) {
            return super.onTouchEvent(motionEvent);
        }
        if (motionEvent != null && motionEvent.getAction() == 0 && (motionEvent.getX() < 0.0f || motionEvent.getX() >= getWidth() || motionEvent.getY() < 0.0f || motionEvent.getY() >= getHeight())) {
            Function0 function0 = this.onDismissRequest;
            if (function0 != null) {
                function0.invoke();
            }
            return true;
        }
        if (motionEvent == null || motionEvent.getAction() != 4) {
            return super.onTouchEvent(motionEvent);
        }
        Function0 function02 = this.onDismissRequest;
        if (function02 != null) {
            function02.invoke();
        }
        return true;
    }

    public final void updateParameters(Function0 function0, PopupProperties popupProperties, LayoutDirection layoutDirection) {
        this.onDismissRequest = function0;
        if (!Intrinsics.areEqual(this.properties, popupProperties)) {
            if (popupProperties.usePlatformDefaultWidth && !this.properties.usePlatformDefaultWidth) {
                WindowManager.LayoutParams layoutParams = this.params;
                layoutParams.width = -2;
                layoutParams.height = -2;
            }
            this.properties = popupProperties;
            WindowManager.LayoutParams layoutParams2 = this.params;
            boolean zIsFlagSecureEnabled = AndroidPopup_androidKt.isFlagSecureEnabled(this.composeView);
            boolean z = popupProperties.inheritSecurePolicy;
            int i = popupProperties.flags;
            if (z && zIsFlagSecureEnabled) {
                i |= 8192;
            } else if (z && !zIsFlagSecureEnabled) {
                i &= -8193;
            }
            layoutParams2.flags = i;
            PopupLayoutHelper popupLayoutHelper = this.popupLayoutHelper;
            WindowManager windowManager = this.windowManager;
            WindowManager.LayoutParams layoutParams3 = this.params;
            ((PopupLayoutHelperImpl) popupLayoutHelper).getClass();
            windowManager.updateViewLayout(this, layoutParams3);
        }
        int i2 = WhenMappings.$EnumSwitchMapping$0[layoutDirection.ordinal()];
        int i3 = 1;
        if (i2 == 1) {
            i3 = 0;
        } else if (i2 != 2) {
            throw new NoWhenBranchMatchedException();
        }
        super.setLayoutDirection(i3);
    }

    public final void updateParentBounds$ui_release() {
        LayoutCoordinates layoutCoordinates = (LayoutCoordinates) ((SnapshotMutableStateImpl) this.parentLayoutCoordinates$delegate).getValue();
        if (layoutCoordinates != null) {
            if (!layoutCoordinates.isAttached()) {
                layoutCoordinates = null;
            }
            if (layoutCoordinates == null) {
                return;
            }
            long jMo612getSizeYbymL2g = layoutCoordinates.mo612getSizeYbymL2g();
            long jPositionInWindow = LayoutCoordinatesKt.positionInWindow(layoutCoordinates);
            IntOffset.Companion companion = IntOffset.Companion;
            IntRect intRectM860IntRectVbeCjmY = IntRectKt.m860IntRectVbeCjmY((Math.round(Float.intBitsToFloat((int) (jPositionInWindow >> 32))) << 32) | (4294967295L & Math.round(Float.intBitsToFloat((int) (jPositionInWindow & 4294967295L)))), jMo612getSizeYbymL2g);
            if (intRectM860IntRectVbeCjmY.equals(this.parentBounds)) {
                return;
            }
            this.parentBounds = intRectM860IntRectVbeCjmY;
            updatePosition();
        }
    }

    public final void updatePosition() {
        IntSize intSize;
        final IntRect intRect = this.parentBounds;
        if (intRect == null || (intSize = (IntSize) ((SnapshotMutableStateImpl) this.popupContentSize$delegate).getValue()) == null) {
            return;
        }
        IntRect visibleDisplayBounds = getVisibleDisplayBounds();
        final long width = (visibleDisplayBounds.getWidth() << 32) | (visibleDisplayBounds.getHeight() & 4294967295L);
        final Ref$LongRef ref$LongRef = new Ref$LongRef();
        IntOffset.Companion.getClass();
        ref$LongRef.element = 0L;
        SnapshotStateObserver snapshotStateObserver = this.snapshotStateObserver;
        Function1 function1 = onCommitAffectingPopupPosition;
        final long j = intSize.packedValue;
        snapshotStateObserver.observeReads(this, function1, new Function0() { // from class: androidx.compose.ui.window.PopupLayout.updatePosition.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Ref$LongRef ref$LongRef2 = ref$LongRef;
                PopupLayout popupLayout = this;
                ref$LongRef2.element = popupLayout.positionProvider.mo49calculatePositionllwVHH4(intRect, width, popupLayout.parentLayoutDirection, j);
                return Unit.INSTANCE;
            }
        });
        WindowManager.LayoutParams layoutParams = this.params;
        long j2 = ref$LongRef.element;
        layoutParams.x = (int) (j2 >> 32);
        layoutParams.y = (int) (j2 & 4294967295L);
        if (this.properties.excludeFromSystemGesture) {
            this.popupLayoutHelper.setGestureExclusionRects(this, (int) (width >> 32), (int) (width & 4294967295L));
        }
        PopupLayoutHelper popupLayoutHelper = this.popupLayoutHelper;
        WindowManager windowManager = this.windowManager;
        WindowManager.LayoutParams layoutParams2 = this.params;
        ((PopupLayoutHelperImpl) popupLayoutHelper).getClass();
        windowManager.updateViewLayout(this, layoutParams2);
    }

    public PopupLayout(Function0 function0, PopupProperties popupProperties, String str, View view, Density density, PopupPositionProvider popupPositionProvider, UUID uuid, PopupLayoutHelper popupLayoutHelper) {
        super(view.getContext(), null, 0, 6, null);
        this.onDismissRequest = function0;
        this.properties = popupProperties;
        this.composeView = view;
        this.popupLayoutHelper = popupLayoutHelper;
        this.windowManager = (WindowManager) view.getContext().getSystemService("window");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = 8388659;
        PopupProperties popupProperties2 = this.properties;
        boolean zIsFlagSecureEnabled = AndroidPopup_androidKt.isFlagSecureEnabled(view);
        boolean z = popupProperties2.inheritSecurePolicy;
        int i = popupProperties2.flags;
        if (z && zIsFlagSecureEnabled) {
            i |= 8192;
        } else if (z && !zIsFlagSecureEnabled) {
            i &= -8193;
        }
        layoutParams.flags = i;
        layoutParams.type = 1002;
        layoutParams.token = view.getApplicationWindowToken();
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.format = -3;
        layoutParams.setTitle(view.getContext().getResources().getString(R.string.default_popup_window_title));
        this.params = layoutParams;
        this.positionProvider = popupPositionProvider;
        this.parentLayoutDirection = LayoutDirection.Ltr;
        this.popupContentSize$delegate = SnapshotStateKt.mutableStateOf$default(null);
        this.parentLayoutCoordinates$delegate = SnapshotStateKt.mutableStateOf$default(null);
        this.canCalculatePosition$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: androidx.compose.ui.window.PopupLayout$canCalculatePosition$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                PopupLayout popupLayout = this.this$0;
                Function1 function1 = PopupLayout.onCommitAffectingPopupPosition;
                LayoutCoordinates layoutCoordinates = (LayoutCoordinates) ((SnapshotMutableStateImpl) popupLayout.parentLayoutCoordinates$delegate).getValue();
                if (layoutCoordinates == null || !layoutCoordinates.isAttached()) {
                    layoutCoordinates = null;
                }
                return Boolean.valueOf((layoutCoordinates == null || ((IntSize) ((SnapshotMutableStateImpl) this.this$0.popupContentSize$delegate).getValue()) == null) ? false : true);
            }
        });
        Dp.Companion companion = Dp.Companion;
        this.previousWindowVisibleFrame = new Rect();
        this.snapshotStateObserver = new SnapshotStateObserver(new Function1() { // from class: androidx.compose.ui.window.PopupLayout$snapshotStateObserver$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                final Function0 function02 = (Function0) obj;
                Handler handler = this.this$0.getHandler();
                if ((handler != null ? handler.getLooper() : null) == Looper.myLooper()) {
                    function02.invoke();
                } else {
                    Handler handler2 = this.this$0.getHandler();
                    if (handler2 != null) {
                        handler2.post(new Runnable() { // from class: androidx.compose.ui.window.PopupLayout$snapshotStateObserver$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                function02.invoke();
                            }
                        });
                    }
                }
                return Unit.INSTANCE;
            }
        });
        setId(android.R.id.content);
        setTag(R.id.view_tree_lifecycle_owner, ViewTreeLifecycleOwner.get(view));
        setTag(R.id.view_tree_view_model_store_owner, ViewTreeViewModelStoreOwner.get(view));
        setTag(R.id.view_tree_saved_state_registry_owner, ViewTreeSavedStateRegistryOwner.get(view));
        setTag(R.id.compose_view_saveable_id_tag, "Popup:" + uuid);
        setClipChildren(false);
        setElevation(density.mo58toPx0680j_4((float) 8));
        setOutlineProvider(new ViewOutlineProvider() { // from class: androidx.compose.ui.window.PopupLayout.2
            @Override // android.view.ViewOutlineProvider
            public final void getOutline(View view2, Outline outline) {
                outline.setRect(0, 0, view2.getWidth(), view2.getHeight());
                outline.setAlpha(0.0f);
            }
        });
        ComposableSingletons$AndroidPopup_androidKt.INSTANCE.getClass();
        this.content$delegate = SnapshotStateKt.mutableStateOf$default(ComposableSingletons$AndroidPopup_androidKt.f22lambda1);
        this.locationOnScreen = new int[2];
    }

    @Override // android.view.View
    public final void setLayoutDirection(int i) {
    }
}
