package com.google.android.material.sidesheet;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.activity.BackEventCompat;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.motion.MaterialBackHandler;
import com.google.android.material.motion.MaterialSideContainerBackHelper;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.sidesheet.SideSheetBehavior;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.WeakHashMap;

/* loaded from: classes4.dex */
public class SideSheetBehavior<V extends View> extends CoordinatorLayout.Behavior implements MaterialBackHandler {
    public final ColorStateList backgroundTint;
    public final Set callbacks;
    public int childWidth;
    public final int coplanarSiblingViewId;
    public WeakReference coplanarSiblingViewRef;
    public final AnonymousClass1 dragCallback;
    public final boolean draggable;
    public final float elevation;
    public final float hideFriction;
    public boolean ignoreEvents;
    public int initialX;
    public int innerMargin;
    public final MaterialShapeDrawable materialShapeDrawable;
    public int parentInnerEdge;
    public int parentWidth;
    public final ShapeAppearanceModel shapeAppearanceModel;
    public SheetDelegate sheetDelegate;
    public MaterialSideContainerBackHelper sideContainerBackHelper;
    public int state;
    public final StateSettlingTracker stateSettlingTracker;
    public VelocityTracker velocityTracker;
    public ViewDragHelper viewDragHelper;
    public WeakReference viewRef;

    public class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: com.google.android.material.sidesheet.SideSheetBehavior.SavedState.1
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }
        };
        public final int state;

        public SavedState(Parcel parcel) {
            this(parcel, (ClassLoader) null);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.state);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.state = parcel.readInt();
        }

        public SavedState(Parcelable parcelable, SideSheetBehavior<?> sideSheetBehavior) {
            super(parcelable);
            this.state = sideSheetBehavior.state;
        }
    }

    public class StateSettlingTracker {
        public final SideSheetBehavior$StateSettlingTracker$$ExternalSyntheticLambda0 continueSettlingRunnable = new Runnable() { // from class: com.google.android.material.sidesheet.SideSheetBehavior$StateSettlingTracker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SideSheetBehavior.StateSettlingTracker stateSettlingTracker = this.f$0;
                stateSettlingTracker.isContinueSettlingRunnablePosted = false;
                SideSheetBehavior sideSheetBehavior = SideSheetBehavior.this;
                ViewDragHelper viewDragHelper = sideSheetBehavior.viewDragHelper;
                if (viewDragHelper != null && viewDragHelper.continueSettling()) {
                    stateSettlingTracker.continueSettlingToState(stateSettlingTracker.targetState);
                } else if (sideSheetBehavior.state == 2) {
                    sideSheetBehavior.setStateInternal(stateSettlingTracker.targetState);
                }
            }
        };
        public boolean isContinueSettlingRunnablePosted;
        public int targetState;

        /* JADX WARN: Type inference failed for: r1v1, types: [com.google.android.material.sidesheet.SideSheetBehavior$StateSettlingTracker$$ExternalSyntheticLambda0] */
        public StateSettlingTracker() {
        }

        public final void continueSettlingToState(int i) {
            SideSheetBehavior sideSheetBehavior = SideSheetBehavior.this;
            WeakReference weakReference = sideSheetBehavior.viewRef;
            if (weakReference == null || weakReference.get() == null) {
                return;
            }
            this.targetState = i;
            if (this.isContinueSettlingRunnablePosted) {
                return;
            }
            View view = (View) sideSheetBehavior.viewRef.get();
            SideSheetBehavior$StateSettlingTracker$$ExternalSyntheticLambda0 sideSheetBehavior$StateSettlingTracker$$ExternalSyntheticLambda0 = this.continueSettlingRunnable;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            view.postOnAnimation(sideSheetBehavior$StateSettlingTracker$$ExternalSyntheticLambda0);
            this.isContinueSettlingRunnablePosted = true;
        }
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [com.google.android.material.sidesheet.SideSheetBehavior$1] */
    public SideSheetBehavior() {
        this.stateSettlingTracker = new StateSettlingTracker();
        this.draggable = true;
        this.state = 5;
        this.hideFriction = 0.1f;
        this.coplanarSiblingViewId = -1;
        this.callbacks = new LinkedHashSet();
        this.dragCallback = new ViewDragHelper.Callback() { // from class: com.google.android.material.sidesheet.SideSheetBehavior.1
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final int clampViewPositionHorizontal(View view, int i) {
                SideSheetBehavior sideSheetBehavior = SideSheetBehavior.this;
                return MathUtils.clamp(i, sideSheetBehavior.sheetDelegate.getMinViewPositionHorizontal(), sideSheetBehavior.sheetDelegate.getMaxViewPositionHorizontal());
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final int clampViewPositionVertical(View view, int i) {
                return view.getTop();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final int getViewHorizontalDragRange(View view) {
                SideSheetBehavior sideSheetBehavior = SideSheetBehavior.this;
                return sideSheetBehavior.childWidth + sideSheetBehavior.innerMargin;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final void onViewDragStateChanged(int i) {
                if (i == 1) {
                    SideSheetBehavior sideSheetBehavior = SideSheetBehavior.this;
                    if (sideSheetBehavior.draggable) {
                        sideSheetBehavior.setStateInternal(1);
                    }
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final void onViewPositionChanged(View view, int i, int i2) {
                ViewGroup.MarginLayoutParams marginLayoutParams;
                SideSheetBehavior sideSheetBehavior = SideSheetBehavior.this;
                WeakReference weakReference = sideSheetBehavior.coplanarSiblingViewRef;
                View view2 = weakReference != null ? (View) weakReference.get() : null;
                if (view2 != null && (marginLayoutParams = (ViewGroup.MarginLayoutParams) view2.getLayoutParams()) != null) {
                    sideSheetBehavior.sheetDelegate.updateCoplanarSiblingLayoutParams(marginLayoutParams, view.getLeft(), view.getRight());
                    view2.setLayoutParams(marginLayoutParams);
                }
                if (sideSheetBehavior.callbacks.isEmpty()) {
                    return;
                }
                sideSheetBehavior.sheetDelegate.calculateSlideOffset(i);
                Iterator it = sideSheetBehavior.callbacks.iterator();
                while (it.hasNext()) {
                    ((SideSheetCallback) it.next()).onSlide();
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:19:0x0053  */
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onViewReleased(View view, float f, float f2) {
                int i;
                SideSheetBehavior sideSheetBehavior = SideSheetBehavior.this;
                if (!sideSheetBehavior.sheetDelegate.isExpandingOutwards(f)) {
                    if (sideSheetBehavior.sheetDelegate.shouldHide(f, view)) {
                        i = (sideSheetBehavior.sheetDelegate.isSwipeSignificant(f, f2) || sideSheetBehavior.sheetDelegate.isReleasedCloseToInnerEdge(view)) ? 5 : 3;
                    } else {
                        if (f == 0.0f || Math.abs(f) <= Math.abs(f2)) {
                            int left = view.getLeft();
                            if (Math.abs(left - sideSheetBehavior.sheetDelegate.getExpandedOffset()) < Math.abs(left - sideSheetBehavior.sheetDelegate.getHiddenOffset())) {
                            }
                        }
                    }
                }
                sideSheetBehavior.startSettling$1(view, i, true);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final boolean tryCaptureView(View view, int i) {
                WeakReference weakReference;
                SideSheetBehavior sideSheetBehavior = SideSheetBehavior.this;
                return (sideSheetBehavior.state == 1 || (weakReference = sideSheetBehavior.viewRef) == null || weakReference.get() != view) ? false : true;
            }
        };
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public final void cancelBackProgress() {
        MaterialSideContainerBackHelper materialSideContainerBackHelper = this.sideContainerBackHelper;
        if (materialSideContainerBackHelper == null || materialSideContainerBackHelper.onCancelBackProgress() == null) {
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(materialSideContainerBackHelper.view, (Property<View, Float>) View.SCALE_X, 1.0f), ObjectAnimator.ofFloat(materialSideContainerBackHelper.view, (Property<View, Float>) View.SCALE_Y, 1.0f));
        View view = materialSideContainerBackHelper.view;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                animatorSet.playTogether(ObjectAnimator.ofFloat(viewGroup.getChildAt(i), (Property<View, Float>) View.SCALE_Y, 1.0f));
            }
        }
        animatorSet.setDuration(materialSideContainerBackHelper.cancelDuration);
        animatorSet.start();
    }

    public MaterialSideContainerBackHelper getBackHelper() {
        return this.sideContainerBackHelper;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ProcessVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: com.google.android.material.motion.MaterialSideContainerBackHelper.1.<init>(com.google.android.material.motion.MaterialSideContainerBackHelper, boolean, int):void, class status: GENERATED_AND_UNLOADED
        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:291)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isArgUnused(ProcessVariables.java:146)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.lambda$isVarUnused$0(ProcessVariables.java:131)
        	at jadx.core.utils.ListUtils.allMatch(ListUtils.java:194)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.isVarUnused(ProcessVariables.java:131)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables$1.processBlock(ProcessVariables.java:82)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:64)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:19)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.removeUnusedResults(ProcessVariables.java:73)
        	at jadx.core.dex.visitors.regions.variables.ProcessVariables.visit(ProcessVariables.java:48)
        */
    @Override // com.google.android.material.motion.MaterialBackHandler
    public final void handleBackInvoked() {
        /*
            r11 = this;
            r0 = 0
            r1 = 1
            com.google.android.material.motion.MaterialSideContainerBackHelper r2 = r11.sideContainerBackHelper
            if (r2 != 0) goto L7
            return
        L7:
            androidx.activity.BackEventCompat r3 = r2.backEvent
            r4 = 0
            r2.backEvent = r4
            r5 = 5
            if (r3 == 0) goto Lbc
            com.google.android.material.sidesheet.SheetDelegate r6 = r11.sheetDelegate
            r7 = 3
            if (r6 == 0) goto L1c
            int r6 = r6.getSheetEdge()
            if (r6 != 0) goto L1b
            goto L1c
        L1b:
            r5 = r7
        L1c:
            com.google.android.material.sidesheet.SideSheetBehavior$2 r6 = new com.google.android.material.sidesheet.SideSheetBehavior$2
            r6.<init>()
            java.lang.ref.WeakReference r8 = r11.coplanarSiblingViewRef
            if (r8 == 0) goto L2c
            java.lang.Object r8 = r8.get()
            android.view.View r8 = (android.view.View) r8
            goto L2d
        L2c:
            r8 = r4
        L2d:
            if (r8 != 0) goto L30
            goto L45
        L30:
            android.view.ViewGroup$LayoutParams r9 = r8.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r9 = (android.view.ViewGroup.MarginLayoutParams) r9
            if (r9 != 0) goto L39
            goto L45
        L39:
            com.google.android.material.sidesheet.SheetDelegate r4 = r11.sheetDelegate
            int r4 = r4.getCoplanarSiblingAdjacentMargin(r9)
            com.google.android.material.sidesheet.SideSheetBehavior$$ExternalSyntheticLambda2 r10 = new com.google.android.material.sidesheet.SideSheetBehavior$$ExternalSyntheticLambda2
            r10.<init>()
            r4 = r10
        L45:
            int r11 = r3.swipeEdge
            if (r11 != 0) goto L4b
            r11 = r1
            goto L4c
        L4b:
            r11 = r0
        L4c:
            android.view.View r8 = r2.view
            java.util.WeakHashMap r9 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            int r8 = r8.getLayoutDirection()
            int r8 = android.view.Gravity.getAbsoluteGravity(r5, r8)
            r8 = r8 & r7
            if (r8 != r7) goto L5d
            r7 = r1
            goto L5e
        L5d:
            r7 = r0
        L5e:
            android.view.View r8 = r2.view
            int r8 = r8.getWidth()
            float r8 = (float) r8
            android.view.View r9 = r2.view
            float r9 = r9.getScaleX()
            float r9 = r9 * r8
            android.view.View r8 = r2.view
            android.view.ViewGroup$LayoutParams r8 = r8.getLayoutParams()
            boolean r10 = r8 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r10 == 0) goto L80
            android.view.ViewGroup$MarginLayoutParams r8 = (android.view.ViewGroup.MarginLayoutParams) r8
            if (r7 == 0) goto L7d
            int r8 = r8.leftMargin
            goto L81
        L7d:
            int r8 = r8.rightMargin
            goto L81
        L80:
            r8 = r0
        L81:
            float r8 = (float) r8
            float r9 = r9 + r8
            android.view.View r8 = r2.view
            android.util.Property r10 = android.view.View.TRANSLATION_X
            if (r7 == 0) goto L8a
            float r9 = -r9
        L8a:
            float[] r1 = new float[r1]
            r1[r0] = r9
            android.animation.ObjectAnimator r0 = android.animation.ObjectAnimator.ofFloat(r8, r10, r1)
            if (r4 == 0) goto L97
            r0.addUpdateListener(r4)
        L97:
            androidx.interpolator.view.animation.FastOutSlowInInterpolator r1 = new androidx.interpolator.view.animation.FastOutSlowInInterpolator
            r1.<init>()
            r0.setInterpolator(r1)
            float r1 = r3.progress
            int r3 = r2.hideDurationMax
            int r4 = r2.hideDurationMin
            int r1 = com.google.android.material.animation.AnimationUtils.lerp(r1, r3, r4)
            long r3 = (long) r1
            r0.setDuration(r3)
            com.google.android.material.motion.MaterialSideContainerBackHelper$1 r1 = new com.google.android.material.motion.MaterialSideContainerBackHelper$1
            r1.<init>()
            r0.addListener(r1)
            r0.addListener(r6)
            r0.start()
            return
        Lbc:
            r11.setState$1(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.sidesheet.SideSheetBehavior.handleBackInvoked():void");
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
        this.viewRef = null;
        this.viewDragHelper = null;
        this.sideContainerBackHelper = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onDetachedFromLayoutParams() {
        this.viewRef = null;
        this.viewDragHelper = null;
        this.sideContainerBackHelper = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        ViewDragHelper viewDragHelper;
        VelocityTracker velocityTracker;
        if ((!view.isShown() && ViewCompat.getAccessibilityPaneTitle(view) == null) || !this.draggable) {
            this.ignoreEvents = true;
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0 && (velocityTracker = this.velocityTracker) != null) {
            velocityTracker.recycle();
            this.velocityTracker = null;
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (actionMasked == 0) {
            this.initialX = (int) motionEvent.getX();
        } else if ((actionMasked == 1 || actionMasked == 3) && this.ignoreEvents) {
            this.ignoreEvents = false;
            return false;
        }
        return (this.ignoreEvents || (viewDragHelper = this.viewDragHelper) == null || !viewDragHelper.shouldInterceptTouchEvent(motionEvent)) ? false : true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        View view2;
        View view3;
        int i2;
        View viewFindViewById;
        MaterialShapeDrawable materialShapeDrawable = this.materialShapeDrawable;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (coordinatorLayout.getFitsSystemWindows() && !view.getFitsSystemWindows()) {
            view.setFitsSystemWindows(true);
        }
        int outerEdge = 0;
        if (this.viewRef == null) {
            this.viewRef = new WeakReference(view);
            this.sideContainerBackHelper = new MaterialSideContainerBackHelper(view);
            if (materialShapeDrawable != null) {
                view.setBackground(materialShapeDrawable);
                float elevation = this.elevation;
                if (elevation == -1.0f) {
                    elevation = ViewCompat.Api21Impl.getElevation(view);
                }
                materialShapeDrawable.setElevation(elevation);
            } else {
                ColorStateList colorStateList = this.backgroundTint;
                if (colorStateList != null) {
                    ViewCompat.Api21Impl.setBackgroundTintList(view, colorStateList);
                }
            }
            int i3 = this.state == 5 ? 4 : 0;
            if (view.getVisibility() != i3) {
                view.setVisibility(i3);
            }
            updateAccessibilityActions$1();
            if (view.getImportantForAccessibility() == 0) {
                view.setImportantForAccessibility(1);
            }
            if (ViewCompat.getAccessibilityPaneTitle(view) == null) {
                ViewCompat.setAccessibilityPaneTitle(view, view.getResources().getString(R.string.side_sheet_accessibility_pane_title));
            }
        }
        int i4 = Gravity.getAbsoluteGravity(((CoordinatorLayout.LayoutParams) view.getLayoutParams()).gravity, i) == 3 ? 1 : 0;
        SheetDelegate sheetDelegate = this.sheetDelegate;
        if (sheetDelegate == null || sheetDelegate.getSheetEdge() != i4) {
            ShapeAppearanceModel shapeAppearanceModel = this.shapeAppearanceModel;
            CoordinatorLayout.LayoutParams layoutParams = null;
            if (i4 == 0) {
                this.sheetDelegate = new RightSheetDelegate(this);
                if (shapeAppearanceModel != null) {
                    WeakReference weakReference = this.viewRef;
                    if (weakReference != null && (view3 = (View) weakReference.get()) != null && (view3.getLayoutParams() instanceof CoordinatorLayout.LayoutParams)) {
                        layoutParams = (CoordinatorLayout.LayoutParams) view3.getLayoutParams();
                    }
                    if (layoutParams == null || ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin <= 0) {
                        ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder(shapeAppearanceModel);
                        builder.setTopRightCornerSize(0.0f);
                        builder.setBottomRightCornerSize(0.0f);
                        ShapeAppearanceModel shapeAppearanceModelBuild = builder.build();
                        if (materialShapeDrawable != null) {
                            materialShapeDrawable.setShapeAppearanceModel(shapeAppearanceModelBuild);
                        }
                    }
                }
            } else {
                if (i4 != 1) {
                    throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i4, "Invalid sheet edge position value: ", ". Must be 0 or 1."));
                }
                this.sheetDelegate = new LeftSheetDelegate(this);
                if (shapeAppearanceModel != null) {
                    WeakReference weakReference2 = this.viewRef;
                    if (weakReference2 != null && (view2 = (View) weakReference2.get()) != null && (view2.getLayoutParams() instanceof CoordinatorLayout.LayoutParams)) {
                        layoutParams = (CoordinatorLayout.LayoutParams) view2.getLayoutParams();
                    }
                    if (layoutParams == null || ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin <= 0) {
                        ShapeAppearanceModel.Builder builder2 = new ShapeAppearanceModel.Builder(shapeAppearanceModel);
                        builder2.setTopLeftCornerSize(0.0f);
                        builder2.setBottomLeftCornerSize(0.0f);
                        ShapeAppearanceModel shapeAppearanceModelBuild2 = builder2.build();
                        if (materialShapeDrawable != null) {
                            materialShapeDrawable.setShapeAppearanceModel(shapeAppearanceModelBuild2);
                        }
                    }
                }
            }
        }
        if (this.viewDragHelper == null) {
            this.viewDragHelper = ViewDragHelper.create(coordinatorLayout, this.dragCallback);
        }
        int outerEdge2 = this.sheetDelegate.getOuterEdge(view);
        coordinatorLayout.onLayoutChild(view, i);
        this.parentWidth = coordinatorLayout.getWidth();
        this.parentInnerEdge = this.sheetDelegate.getParentInnerEdge(coordinatorLayout);
        this.childWidth = view.getWidth();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        this.innerMargin = marginLayoutParams != null ? this.sheetDelegate.calculateInnerMargin(marginLayoutParams) : 0;
        int i5 = this.state;
        if (i5 == 1 || i5 == 2) {
            outerEdge = outerEdge2 - this.sheetDelegate.getOuterEdge(view);
        } else if (i5 != 3) {
            if (i5 != 5) {
                throw new IllegalStateException("Unexpected value: " + this.state);
            }
            outerEdge = this.sheetDelegate.getHiddenOffset();
        }
        view.offsetLeftAndRight(outerEdge);
        if (this.coplanarSiblingViewRef == null && (i2 = this.coplanarSiblingViewId) != -1 && (viewFindViewById = coordinatorLayout.findViewById(i2)) != null) {
            this.coplanarSiblingViewRef = new WeakReference(viewFindViewById);
        }
        for (SideSheetCallback sideSheetCallback : this.callbacks) {
        }
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(ViewGroup.getChildMeasureSpec(i, coordinatorLayout.getPaddingRight() + coordinatorLayout.getPaddingLeft() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width), ViewGroup.getChildMeasureSpec(i3, coordinatorLayout.getPaddingBottom() + coordinatorLayout.getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height));
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onRestoreInstanceState(View view, Parcelable parcelable) {
        int i = ((SavedState) parcelable).state;
        if (i == 1 || i == 2) {
            i = 5;
        }
        this.state = i;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final Parcelable onSaveInstanceState(View view) {
        return new SavedState((Parcelable) View.BaseSavedState.EMPTY_STATE, (SideSheetBehavior<?>) this);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        VelocityTracker velocityTracker;
        if (!view.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (this.state == 1 && actionMasked == 0) {
            return true;
        }
        if (shouldHandleDraggingWithHelper$1()) {
            this.viewDragHelper.processTouchEvent(motionEvent);
        }
        if (actionMasked == 0 && (velocityTracker = this.velocityTracker) != null) {
            velocityTracker.recycle();
            this.velocityTracker = null;
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (shouldHandleDraggingWithHelper$1() && actionMasked == 2 && !this.ignoreEvents && shouldHandleDraggingWithHelper$1()) {
            float fAbs = Math.abs(this.initialX - motionEvent.getX());
            ViewDragHelper viewDragHelper = this.viewDragHelper;
            if (fAbs > viewDragHelper.mTouchSlop) {
                viewDragHelper.captureChildView(view, motionEvent.getPointerId(motionEvent.getActionIndex()));
            }
        }
        return !this.ignoreEvents;
    }

    public final void setState$1(final int i) {
        if (i == 1 || i == 2) {
            throw new IllegalArgumentException(TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("STATE_"), i == 1 ? "DRAGGING" : "SETTLING", " should not be set externally."));
        }
        WeakReference weakReference = this.viewRef;
        if (weakReference == null || weakReference.get() == null) {
            setStateInternal(i);
            return;
        }
        View view = (View) this.viewRef.get();
        Runnable runnable = new Runnable() { // from class: com.google.android.material.sidesheet.SideSheetBehavior$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SideSheetBehavior sideSheetBehavior = this.f$0;
                int i2 = i;
                View view2 = (View) sideSheetBehavior.viewRef.get();
                if (view2 != null) {
                    sideSheetBehavior.startSettling$1(view2, i2, false);
                }
            }
        };
        ViewParent parent = view.getParent();
        if (parent != null && parent.isLayoutRequested()) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (view.isAttachedToWindow()) {
                view.post(runnable);
                return;
            }
        }
        runnable.run();
    }

    public final void setStateInternal(int i) {
        View view;
        if (this.state == i) {
            return;
        }
        this.state = i;
        WeakReference weakReference = this.viewRef;
        if (weakReference == null || (view = (View) weakReference.get()) == null) {
            return;
        }
        int i2 = this.state == 5 ? 4 : 0;
        if (view.getVisibility() != i2) {
            view.setVisibility(i2);
        }
        Iterator it = this.callbacks.iterator();
        while (it.hasNext()) {
            ((SideSheetCallback) it.next()).onStateChanged();
        }
        updateAccessibilityActions$1();
    }

    public final boolean shouldHandleDraggingWithHelper$1() {
        if (this.viewDragHelper != null) {
            return this.draggable || this.state == 1;
        }
        return false;
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public final void startBackProgress(BackEventCompat backEventCompat) {
        MaterialSideContainerBackHelper materialSideContainerBackHelper = this.sideContainerBackHelper;
        if (materialSideContainerBackHelper == null) {
            return;
        }
        materialSideContainerBackHelper.backEvent = backEventCompat;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002d, code lost:
    
        if (r1.settleCapturedViewAt(r0, r3.getTop()) != false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x004b, code lost:
    
        if (r3 != false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x004d, code lost:
    
        setStateInternal(2);
        r2.stateSettlingTracker.continueSettlingToState(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0056, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startSettling$1(View view, int i, boolean z) {
        int expandedOffset;
        if (i == 3) {
            expandedOffset = this.sheetDelegate.getExpandedOffset();
        } else {
            if (i != 5) {
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid state to get outer edge offset: "));
            }
            expandedOffset = this.sheetDelegate.getHiddenOffset();
        }
        ViewDragHelper viewDragHelper = this.viewDragHelper;
        if (viewDragHelper != null) {
            if (!z) {
                int top = view.getTop();
                viewDragHelper.mCapturedView = view;
                viewDragHelper.mActivePointerId = -1;
                boolean zForceSettleCapturedViewAt = viewDragHelper.forceSettleCapturedViewAt(expandedOffset, top, 0, 0);
                if (!zForceSettleCapturedViewAt && viewDragHelper.mDragState == 0 && viewDragHelper.mCapturedView != null) {
                    viewDragHelper.mCapturedView = null;
                }
            }
        }
        setStateInternal(i);
    }

    public final void updateAccessibilityActions$1() {
        View view;
        WeakReference weakReference = this.viewRef;
        if (weakReference == null || (view = (View) weakReference.get()) == null) {
            return;
        }
        ViewCompat.removeActionWithId(view, 262144);
        ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
        ViewCompat.removeActionWithId(view, 1048576);
        ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
        final int i = 5;
        if (this.state != 5) {
            ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, null, new AccessibilityViewCommand() { // from class: com.google.android.material.sidesheet.SideSheetBehavior$$ExternalSyntheticLambda1
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public final boolean perform(View view2) {
                    this.f$0.setState$1(i);
                    return true;
                }
            });
        }
        final int i2 = 3;
        if (this.state != 3) {
            ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, null, new AccessibilityViewCommand() { // from class: com.google.android.material.sidesheet.SideSheetBehavior$$ExternalSyntheticLambda1
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public final boolean perform(View view2) {
                    this.f$0.setState$1(i2);
                    return true;
                }
            });
        }
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public final void updateBackProgress(BackEventCompat backEventCompat) {
        ViewGroup.MarginLayoutParams marginLayoutParams;
        MaterialSideContainerBackHelper materialSideContainerBackHelper = this.sideContainerBackHelper;
        if (materialSideContainerBackHelper == null) {
            return;
        }
        SheetDelegate sheetDelegate = this.sheetDelegate;
        int i = (sheetDelegate == null || sheetDelegate.getSheetEdge() == 0) ? 5 : 3;
        if (materialSideContainerBackHelper.backEvent == null) {
            Log.w("MaterialBackHelper", "Must call startBackProgress() before updateBackProgress()");
        }
        BackEventCompat backEventCompat2 = materialSideContainerBackHelper.backEvent;
        materialSideContainerBackHelper.backEvent = backEventCompat;
        if (backEventCompat2 != null) {
            materialSideContainerBackHelper.updateBackProgress(backEventCompat.progress, backEventCompat.swipeEdge == 0, i);
        }
        WeakReference weakReference = this.viewRef;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        View view = (View) this.viewRef.get();
        WeakReference weakReference2 = this.coplanarSiblingViewRef;
        View view2 = weakReference2 != null ? (View) weakReference2.get() : null;
        if (view2 == null || (marginLayoutParams = (ViewGroup.MarginLayoutParams) view2.getLayoutParams()) == null) {
            return;
        }
        this.sheetDelegate.updateCoplanarSiblingAdjacentMargin(marginLayoutParams, (int) ((view.getScaleX() * this.childWidth) + this.innerMargin));
        view2.requestLayout();
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.google.android.material.sidesheet.SideSheetBehavior$1] */
    public SideSheetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.stateSettlingTracker = new StateSettlingTracker();
        this.draggable = true;
        this.state = 5;
        this.hideFriction = 0.1f;
        this.coplanarSiblingViewId = -1;
        this.callbacks = new LinkedHashSet();
        this.dragCallback = new ViewDragHelper.Callback() { // from class: com.google.android.material.sidesheet.SideSheetBehavior.1
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final int clampViewPositionHorizontal(View view, int i) {
                SideSheetBehavior sideSheetBehavior = SideSheetBehavior.this;
                return MathUtils.clamp(i, sideSheetBehavior.sheetDelegate.getMinViewPositionHorizontal(), sideSheetBehavior.sheetDelegate.getMaxViewPositionHorizontal());
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final int clampViewPositionVertical(View view, int i) {
                return view.getTop();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final int getViewHorizontalDragRange(View view) {
                SideSheetBehavior sideSheetBehavior = SideSheetBehavior.this;
                return sideSheetBehavior.childWidth + sideSheetBehavior.innerMargin;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final void onViewDragStateChanged(int i) {
                if (i == 1) {
                    SideSheetBehavior sideSheetBehavior = SideSheetBehavior.this;
                    if (sideSheetBehavior.draggable) {
                        sideSheetBehavior.setStateInternal(1);
                    }
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final void onViewPositionChanged(View view, int i, int i2) {
                ViewGroup.MarginLayoutParams marginLayoutParams;
                SideSheetBehavior sideSheetBehavior = SideSheetBehavior.this;
                WeakReference weakReference = sideSheetBehavior.coplanarSiblingViewRef;
                View view2 = weakReference != null ? (View) weakReference.get() : null;
                if (view2 != null && (marginLayoutParams = (ViewGroup.MarginLayoutParams) view2.getLayoutParams()) != null) {
                    sideSheetBehavior.sheetDelegate.updateCoplanarSiblingLayoutParams(marginLayoutParams, view.getLeft(), view.getRight());
                    view2.setLayoutParams(marginLayoutParams);
                }
                if (sideSheetBehavior.callbacks.isEmpty()) {
                    return;
                }
                sideSheetBehavior.sheetDelegate.calculateSlideOffset(i);
                Iterator it = sideSheetBehavior.callbacks.iterator();
                while (it.hasNext()) {
                    ((SideSheetCallback) it.next()).onSlide();
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:19:0x0053  */
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onViewReleased(View view, float f, float f2) {
                int i;
                SideSheetBehavior sideSheetBehavior = SideSheetBehavior.this;
                if (!sideSheetBehavior.sheetDelegate.isExpandingOutwards(f)) {
                    if (sideSheetBehavior.sheetDelegate.shouldHide(f, view)) {
                        i = (sideSheetBehavior.sheetDelegate.isSwipeSignificant(f, f2) || sideSheetBehavior.sheetDelegate.isReleasedCloseToInnerEdge(view)) ? 5 : 3;
                    } else {
                        if (f == 0.0f || Math.abs(f) <= Math.abs(f2)) {
                            int left = view.getLeft();
                            if (Math.abs(left - sideSheetBehavior.sheetDelegate.getExpandedOffset()) < Math.abs(left - sideSheetBehavior.sheetDelegate.getHiddenOffset())) {
                            }
                        }
                    }
                }
                sideSheetBehavior.startSettling$1(view, i, true);
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public final boolean tryCaptureView(View view, int i) {
                WeakReference weakReference;
                SideSheetBehavior sideSheetBehavior = SideSheetBehavior.this;
                return (sideSheetBehavior.state == 1 || (weakReference = sideSheetBehavior.viewRef) == null || weakReference.get() != view) ? false : true;
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SideSheetBehavior_Layout);
        if (typedArrayObtainStyledAttributes.hasValue(3)) {
            this.backgroundTint = MaterialResources.getColorStateList(context, typedArrayObtainStyledAttributes, 3);
        }
        if (typedArrayObtainStyledAttributes.hasValue(6)) {
            this.shapeAppearanceModel = ShapeAppearanceModel.builder(context, attributeSet, 0, R.style.Widget_Material3_SideSheet).build();
        }
        if (typedArrayObtainStyledAttributes.hasValue(5)) {
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(5, -1);
            this.coplanarSiblingViewId = resourceId;
            WeakReference weakReference = this.coplanarSiblingViewRef;
            if (weakReference != null) {
                weakReference.clear();
            }
            this.coplanarSiblingViewRef = null;
            WeakReference weakReference2 = this.viewRef;
            if (weakReference2 != null) {
                View view = (View) weakReference2.get();
                if (resourceId != -1) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    if (view.isLaidOut()) {
                        view.requestLayout();
                    }
                }
            }
        }
        ShapeAppearanceModel shapeAppearanceModel = this.shapeAppearanceModel;
        if (shapeAppearanceModel != null) {
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(shapeAppearanceModel);
            this.materialShapeDrawable = materialShapeDrawable;
            materialShapeDrawable.initializeElevationOverlay(context);
            ColorStateList colorStateList = this.backgroundTint;
            if (colorStateList != null) {
                this.materialShapeDrawable.setFillColor(colorStateList);
            } else {
                TypedValue typedValue = new TypedValue();
                context.getTheme().resolveAttribute(android.R.attr.colorBackground, typedValue, true);
                this.materialShapeDrawable.setTint(typedValue.data);
            }
        }
        this.elevation = typedArrayObtainStyledAttributes.getDimension(2, -1.0f);
        this.draggable = typedArrayObtainStyledAttributes.getBoolean(4, true);
        typedArrayObtainStyledAttributes.recycle();
        ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }
}
