package com.android.wm.shell.desktopmode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityTaskManager;
import android.app.TaskInfo;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.hardware.HardwareBuffer;
import android.os.RemoteException;
import android.view.SemBlurInfo;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.window.TaskSnapshot;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.internal.dynamicanimation.animation.SpringForce;
import com.android.systemui.R;
import com.android.systemui.util.DimensionKt;
import com.android.wm.shell.bubbles.BubblePositioner;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.desktopmode.DesktopModeVisualIndicator;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.VisualIndicatorViewContainer;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.shared.GroupedTaskInfo;
import com.android.wm.shell.shared.bubbles.BubbleDropTargetBoundsProvider;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.WindowDecoration;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingDecorViewModel;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration;
import com.android.wm.shell.windowdecor.tiling.SnapEventHandler;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes3.dex */
public final class VisualIndicatorViewContainer {
    public final BubbleDropTargetBoundsProvider bubbleBoundsProvider;
    public final ShellExecutor desktopExecutor;
    public final SurfaceControl.Builder indicatorBuilder;
    public SurfaceControl indicatorLeash;
    public View indicatorView;
    public SurfaceControlViewHost indicatorViewHost;
    public boolean isReleased;
    public final ShellExecutor mainExecutor;
    public RecentTasksController recentsTasksController;
    public final SnapEventHandler snapEventHandler;
    public final WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory;
    public final SyncTransactionQueue syncQueue;
    public SurfaceControl taskSnapshotLeash;
    public View taskSnapshotView;
    public SurfaceControlViewHost taskSnapshotViewHost;
    public final RectF tmpRect;

    public VisualIndicatorViewContainer(ShellExecutor shellExecutor, ShellExecutor shellExecutor2, SurfaceControl.Builder builder, SyncTransactionQueue syncTransactionQueue, BubbleDropTargetBoundsProvider bubbleDropTargetBoundsProvider, SnapEventHandler snapEventHandler) {
        this(shellExecutor, shellExecutor2, builder, syncTransactionQueue, null, bubbleDropTargetBoundsProvider, snapEventHandler, 16, null);
    }

    public static Bitmap createFallbackBitmap(TaskSnapshot taskSnapshot, Rect rect) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(rect != null ? rect.width() : taskSnapshot.getTaskSize().x, rect != null ? rect.height() : taskSnapshot.getTaskSize().y, Bitmap.Config.ARGB_8888);
        bitmapCreateBitmap.eraseColor(-16777216);
        return bitmapCreateBitmap;
    }

    public final void addDeskLabel(Integer num) {
        if (num != null) {
            View view = this.indicatorView;
            TextView textView = view != null ? (TextView) view.findViewById(R.id.labelView) : null;
            if (textView != null) {
                Context context = textView.getContext();
                textView.setText(context != null ? context.getString(R.string.desktop_label, num) : null);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void fadeInIndicatorInternal(DisplayLayout displayLayout, DesktopModeVisualIndicator.IndicatorType indicatorType, int i, SnapEventHandler snapEventHandler) {
        List list;
        List listReversed;
        TaskSnapshot taskSnapshot;
        int i2;
        View view;
        boolean z;
        ArrayList arrayList;
        Rect rect;
        Bitmap bitmapWrapHardwareBuffer;
        if (indicatorType == DesktopModeVisualIndicator.IndicatorType.TO_DESKTOP_MAXIMIZED_WINDOW || indicatorType == DesktopModeVisualIndicator.IndicatorType.NO_INDICATOR) {
            return;
        }
        if (CoreRune.DW_TASK_SNAPSHOT_BLUR && indicatorType == DesktopModeVisualIndicator.IndicatorType.TO_DESKTOP_INDICATOR) {
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            final Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
            this.mainExecutor.executeBlocking(new Runnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer.fadeInIndicatorInternal.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.lang.Runnable
                public final void run() {
                    GroupedTaskInfo deskForSnapshot;
                    RecentTasksController recentTasksController;
                    Ref$ObjectRef ref$ObjectRef3 = ref$ObjectRef;
                    RecentTasksController recentTasksController2 = this.recentsTasksController;
                    T deskLabel = 0;
                    deskLabel = 0;
                    deskLabel = 0;
                    ref$ObjectRef3.element = recentTasksController2 != null ? recentTasksController2.getDeskForSnapshot() : 0;
                    Ref$ObjectRef ref$ObjectRef4 = ref$ObjectRef2;
                    RecentTasksController recentTasksController3 = this.recentsTasksController;
                    if (recentTasksController3 != null && (deskForSnapshot = recentTasksController3.getDeskForSnapshot()) != null && (recentTasksController = this.recentsTasksController) != null) {
                        if (deskForSnapshot.mType == 4) {
                            throw new IllegalStateException("No desk ID for a mixed task");
                        }
                        deskLabel = recentTasksController.getDeskLabel(deskForSnapshot.mDeskId);
                    }
                    ref$ObjectRef4.element = deskLabel;
                }
            });
            GroupedTaskInfo groupedTaskInfo = (GroupedTaskInfo) ref$ObjectRef.element;
            View view2 = this.taskSnapshotView;
            if (view2 != null) {
                FrameLayout frameLayout = (FrameLayout) view2.findViewById(R.id.desktop_drop_view);
                float fDpToPx = DimensionKt.dpToPx((Number) 14, view2.getContext());
                if (groupedTaskInfo != null && (list = groupedTaskInfo.mTasks) != null && (listReversed = CollectionsKt___CollectionsKt.reversed(list)) != null) {
                    ArrayList arrayList2 = new ArrayList();
                    for (Object obj : listReversed) {
                        TaskInfo taskInfo = (TaskInfo) obj;
                        if (taskInfo.isRunning) {
                            if (groupedTaskInfo.mType == 4) {
                                throw new IllegalStateException("No minimized task ids for a mixed task");
                            }
                            int[] iArr = groupedTaskInfo.mMinimizedTaskIds;
                            if (iArr == null || ArraysKt___ArraysKt.indexOf(taskInfo.taskId, iArr) < 0) {
                                arrayList2.add(obj);
                            }
                        }
                    }
                    int size = arrayList2.size();
                    boolean z2 = false;
                    int i3 = 0;
                    while (i3 < size) {
                        Object obj2 = arrayList2.get(i3);
                        i3++;
                        TaskInfo taskInfo2 = (TaskInfo) obj2;
                        int i4 = taskInfo2.taskId;
                        if (i4 <= 0) {
                            taskSnapshot = null;
                        } else {
                            try {
                                taskSnapshot = ActivityTaskManager.getService().getTaskSnapshot(i4, z2);
                            } catch (RemoteException unused) {
                            }
                        }
                        if (taskSnapshot == null) {
                            i2 = size;
                            view = view2;
                            z = z2;
                            arrayList = arrayList2;
                        } else {
                            int rotation = taskSnapshot.getRotation();
                            int i5 = displayLayout.mRotation;
                            i2 = size;
                            view = view2;
                            z = false;
                            Rect rect2 = new Rect(0, 0, displayLayout.mWidth, displayLayout.mHeight);
                            arrayList = arrayList2;
                            Rect rect3 = new Rect(0, 0, displayLayout.mHeight, displayLayout.mWidth);
                            if (rotation != i5) {
                                rect = new Rect();
                                MultiWindowUtils.adjustBoundsForScreenRatio(rect3, rect2, taskInfo2.configuration.windowConfiguration.getBounds(), rect);
                            } else {
                                rect = null;
                            }
                            HardwareBuffer hardwareBuffer = taskSnapshot.getHardwareBuffer();
                            if (hardwareBuffer == null || (bitmapWrapHardwareBuffer = Bitmap.wrapHardwareBuffer(hardwareBuffer, taskSnapshot.getColorSpace())) == null) {
                                bitmapWrapHardwareBuffer = createFallbackBitmap(taskSnapshot, rect);
                            } else if (rect != null && (bitmapWrapHardwareBuffer.getWidth() != rect.width() || bitmapWrapHardwareBuffer.getHeight() != rect.height())) {
                                bitmapWrapHardwareBuffer = Bitmap.createScaledBitmap(bitmapWrapHardwareBuffer, rect.width(), rect.height(), true);
                                bitmapWrapHardwareBuffer.getClass();
                            }
                            int width = bitmapWrapHardwareBuffer.getWidth();
                            int height = bitmapWrapHardwareBuffer.getHeight();
                            Bitmap.Config config = Bitmap.Config.ARGB_8888;
                            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, config);
                            Canvas canvas = new Canvas(bitmapCreateBitmap);
                            Paint paint = new Paint();
                            paint.setAntiAlias(true);
                            Bitmap bitmapCopy = bitmapWrapHardwareBuffer.copy(config, true);
                            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
                            paint.setShader(new BitmapShader(bitmapCopy, tileMode, tileMode));
                            this.tmpRect.set(0.0f, 0.0f, bitmapWrapHardwareBuffer.getWidth(), bitmapWrapHardwareBuffer.getHeight());
                            canvas.drawRoundRect(this.tmpRect, fDpToPx, fDpToPx, paint);
                            ImageView imageView = new ImageView(view.getContext());
                            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
                            layoutParams.setMargins(taskInfo2.configuration.windowConfiguration.getBounds().left, taskInfo2.configuration.windowConfiguration.getBounds().top, -displayLayout.mWidth, -displayLayout.mHeight);
                            imageView.setLayoutParams(layoutParams);
                            imageView.setImageBitmap(bitmapCreateBitmap);
                            if (frameLayout != null) {
                                frameLayout.addView(imageView);
                            }
                        }
                        z2 = z;
                        arrayList2 = arrayList;
                        size = i2;
                        view2 = view;
                    }
                }
            }
            addDeskLabel((Integer) ref$ObjectRef2.element);
        }
        ((HandlerExecutor) this.desktopExecutor).assertCurrentThread();
        View view3 = this.indicatorView;
        if (view3 != null) {
            VisualIndicatorAnimator.Companion.getClass();
            Rect indicatorBounds = VisualIndicatorAnimator.Companion.getIndicatorBounds(displayLayout, indicatorType, this.bubbleBoundsProvider, i, snapEventHandler);
            VisualIndicatorAnimator visualIndicatorAnimator = new VisualIndicatorAnimator(view3, VisualIndicatorAnimator.Companion.getMinBounds(indicatorBounds), indicatorBounds, displayLayout, i);
            visualIndicatorAnimator.setInterpolator(new DecelerateInterpolator());
            VisualIndicatorAnimator.Companion.setupIndicatorAnimation(visualIndicatorAnimator, VisualIndicatorAnimator.AlphaAnimType.ALPHA_FADE_IN_ANIM, indicatorType);
            if (indicatorType == DesktopModeVisualIndicator.IndicatorType.TO_DESKTOP_INDICATOR && this.taskSnapshotView != null) {
                SpringAnimation springAnimation = new SpringAnimation(this.taskSnapshotView, DynamicAnimation.TRANSLATION_X, 0.0f);
                SpringForce springForce = new SpringForce(0.0f);
                springForce.setDampingRatio(1.0f);
                springForce.setStiffness(361.0f);
                springAnimation.setSpring(springForce);
                springAnimation.start();
            }
            visualIndicatorAnimator.start();
        }
    }

    public final Rect getIndicatorBounds() {
        Drawable background;
        Rect bounds;
        View view = this.indicatorView;
        return (view == null || (background = view.getBackground()) == null || (bounds = background.getBounds()) == null) ? new Rect() : bounds;
    }

    public final void releaseVisualIndicator() {
        if (this.isReleased) {
            return;
        }
        this.desktopExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer.releaseVisualIndicator.1
            @Override // java.lang.Runnable
            public final void run() {
                SurfaceControlViewHost surfaceControlViewHost = VisualIndicatorViewContainer.this.indicatorViewHost;
                if (surfaceControlViewHost != null) {
                    surfaceControlViewHost.release();
                }
                VisualIndicatorViewContainer visualIndicatorViewContainer = VisualIndicatorViewContainer.this;
                visualIndicatorViewContainer.indicatorViewHost = null;
                if (CoreRune.DW_TASK_SNAPSHOT_BLUR) {
                    SurfaceControlViewHost surfaceControlViewHost2 = visualIndicatorViewContainer.taskSnapshotViewHost;
                    if (surfaceControlViewHost2 != null) {
                        surfaceControlViewHost2.release();
                    }
                    VisualIndicatorViewContainer.this.taskSnapshotViewHost = null;
                }
            }
        });
        SurfaceControl surfaceControl = this.indicatorLeash;
        if (surfaceControl != null) {
            final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            transaction.remove(surfaceControl);
            this.indicatorLeash = null;
            if (CoreRune.DW_TASK_SNAPSHOT_BLUR) {
                SurfaceControl surfaceControl2 = this.taskSnapshotLeash;
                if (surfaceControl2 != null) {
                    transaction.remove(surfaceControl2);
                }
                this.taskSnapshotLeash = null;
            }
            this.syncQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$releaseVisualIndicator$2$2
                @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
                public final void runWithTransaction(SurfaceControl.Transaction transaction2) {
                    transaction2.merge(transaction);
                    transaction.close();
                }
            });
        }
        this.isReleased = true;
    }

    public VisualIndicatorViewContainer(ShellExecutor shellExecutor, ShellExecutor shellExecutor2, SurfaceControl.Builder builder, SyncTransactionQueue syncTransactionQueue, WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory, BubbleDropTargetBoundsProvider bubbleDropTargetBoundsProvider, SnapEventHandler snapEventHandler) {
        this.desktopExecutor = shellExecutor;
        this.mainExecutor = shellExecutor2;
        this.indicatorBuilder = builder;
        this.syncQueue = syncTransactionQueue;
        this.surfaceControlViewHostFactory = surfaceControlViewHostFactory;
        this.bubbleBoundsProvider = bubbleDropTargetBoundsProvider;
        this.snapEventHandler = snapEventHandler;
        this.tmpRect = new RectF();
    }

    public /* synthetic */ VisualIndicatorViewContainer(ShellExecutor shellExecutor, ShellExecutor shellExecutor2, SurfaceControl.Builder builder, SyncTransactionQueue syncTransactionQueue, WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory, BubbleDropTargetBoundsProvider bubbleDropTargetBoundsProvider, SnapEventHandler snapEventHandler, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(shellExecutor, shellExecutor2, builder, syncTransactionQueue, (i & 16) != 0 ? new WindowDecoration.SurfaceControlViewHostFactory() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer.1
        } : surfaceControlViewHostFactory, bubbleDropTargetBoundsProvider, snapEventHandler);
    }

    public final class VisualIndicatorAnimator extends ValueAnimator {
        public static final Companion Companion = new Companion(null);
        public final View blurView;
        public final View deskLabelView;
        public final int displayId;
        public final Rect indicatorEndBounds;
        public final Rect indicatorStartBounds;
        public final View indicatorView;
        public final RectEvaluator mRectEvaluator;
        public final View targetView;

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        final class AlphaAnimType {
            public static final /* synthetic */ AlphaAnimType[] $VALUES;
            public static final AlphaAnimType ALPHA_FADE_IN_ANIM;
            public static final AlphaAnimType ALPHA_FADE_OUT_ANIM;
            public static final AlphaAnimType ALPHA_NO_CHANGE_ANIM;

            static {
                AlphaAnimType alphaAnimType = new AlphaAnimType("ALPHA_FADE_IN_ANIM", 0);
                ALPHA_FADE_IN_ANIM = alphaAnimType;
                AlphaAnimType alphaAnimType2 = new AlphaAnimType("ALPHA_FADE_OUT_ANIM", 1);
                ALPHA_FADE_OUT_ANIM = alphaAnimType2;
                AlphaAnimType alphaAnimType3 = new AlphaAnimType("ALPHA_NO_CHANGE_ANIM", 2);
                ALPHA_NO_CHANGE_ANIM = alphaAnimType3;
                AlphaAnimType[] alphaAnimTypeArr = {alphaAnimType, alphaAnimType2, alphaAnimType3};
                $VALUES = alphaAnimTypeArr;
                EnumEntriesKt.enumEntries(alphaAnimTypeArr);
            }

            private AlphaAnimType(String str, int i) {
            }

            public static AlphaAnimType valueOf(String str) {
                return (AlphaAnimType) Enum.valueOf(AlphaAnimType.class, str);
            }

            public static AlphaAnimType[] values() {
                return (AlphaAnimType[]) $VALUES.clone();
            }
        }

        public final class Companion {

            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[DesktopModeVisualIndicator.IndicatorType.values().length];
                    try {
                        iArr[DesktopModeVisualIndicator.IndicatorType.TO_FULLSCREEN_INDICATOR.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[DesktopModeVisualIndicator.IndicatorType.TO_DESKTOP_MAXIMIZED_WINDOW.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[DesktopModeVisualIndicator.IndicatorType.TO_DESKTOP_INDICATOR.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    try {
                        iArr[DesktopModeVisualIndicator.IndicatorType.TO_SPLIT_LEFT_INDICATOR.ordinal()] = 4;
                    } catch (NoSuchFieldError unused4) {
                    }
                    try {
                        iArr[DesktopModeVisualIndicator.IndicatorType.TO_SPLIT_RIGHT_INDICATOR.ordinal()] = 5;
                    } catch (NoSuchFieldError unused5) {
                    }
                    try {
                        iArr[DesktopModeVisualIndicator.IndicatorType.TO_BUBBLE_LEFT_INDICATOR.ordinal()] = 6;
                    } catch (NoSuchFieldError unused6) {
                    }
                    try {
                        iArr[DesktopModeVisualIndicator.IndicatorType.TO_BUBBLE_RIGHT_INDICATOR.ordinal()] = 7;
                    } catch (NoSuchFieldError unused7) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public static Rect getIndicatorBounds(DisplayLayout displayLayout, DesktopModeVisualIndicator.IndicatorType indicatorType, BubbleDropTargetBoundsProvider bubbleDropTargetBoundsProvider, int i, SnapEventHandler snapEventHandler) {
                Rect snapBounds;
                Rect rect = new Rect();
                displayLayout.getStableBounds(rect, false);
                int i2 = rect.top;
                switch (WhenMappings.$EnumSwitchMapping$0[indicatorType.ordinal()]) {
                    case 1:
                        DesktopStateImpl.Companion.getClass();
                        if (DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                            rect.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
                            return rect;
                        }
                        rect.top += i2;
                        rect.bottom -= i2;
                        rect.left += i2;
                        rect.right -= i2;
                        return rect;
                    case 2:
                        rect.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
                        rect.top += i2;
                        return rect;
                    case 3:
                        if (CoreRune.DW_TASK_SNAPSHOT_BLUR) {
                            rect.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
                            rect.top += i2;
                            return rect;
                        }
                        float f = 1.0f - DesktopTasksController.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                        float f2 = 2;
                        return new Rect((int) ((rect.width() * f) / f2), (int) ((rect.height() * f) / f2), (int) (rect.width() - ((rect.width() * f) / f2)), (int) (rect.height() - ((f * rect.height()) / f2)));
                    case 4:
                        DesktopTilingDecorViewModel desktopTilingDecorViewModel = ((DesktopModeWindowDecorViewModel) snapEventHandler).mDesktopTilingDecorViewModel;
                        DesktopTilingWindowDecoration desktopTilingWindowDecoration = (DesktopTilingWindowDecoration) desktopTilingDecorViewModel.tilingTransitionHandlerByDisplayId.get(i);
                        snapBounds = desktopTilingWindowDecoration != null ? desktopTilingWindowDecoration.getSnapBounds(DesktopTasksController.SnapPosition.LEFT) : null;
                        if (snapBounds != null) {
                            return snapBounds;
                        }
                        DisplayController displayController = desktopTilingDecorViewModel.displayController;
                        DisplayLayout displayLayout2 = displayController.getDisplayLayout(i);
                        Rect rect2 = new Rect();
                        if (displayLayout2 != null) {
                            displayLayout2.getStableBounds(rect2, false);
                        }
                        Context displayContext = displayController.getDisplayContext(i);
                        if (displayContext == null) {
                            displayContext = desktopTilingDecorViewModel.context;
                        }
                        int i3 = rect2.left;
                        return new Rect(i3, rect2.top, ((rect2.width() / 2) + i3) - (displayContext.getResources().getDimensionPixelSize(R.dimen.split_divider_bar_width) / 2), rect2.bottom);
                    case 5:
                        DesktopTilingDecorViewModel desktopTilingDecorViewModel2 = ((DesktopModeWindowDecorViewModel) snapEventHandler).mDesktopTilingDecorViewModel;
                        DesktopTilingWindowDecoration desktopTilingWindowDecoration2 = (DesktopTilingWindowDecoration) desktopTilingDecorViewModel2.tilingTransitionHandlerByDisplayId.get(i);
                        snapBounds = desktopTilingWindowDecoration2 != null ? desktopTilingWindowDecoration2.getSnapBounds(DesktopTasksController.SnapPosition.RIGHT) : null;
                        if (snapBounds != null) {
                            return snapBounds;
                        }
                        DisplayController displayController2 = desktopTilingDecorViewModel2.displayController;
                        DisplayLayout displayLayout3 = displayController2.getDisplayLayout(i);
                        Rect rect3 = new Rect();
                        if (displayLayout3 != null) {
                            displayLayout3.getStableBounds(rect3, false);
                        }
                        Context displayContext2 = displayController2.getDisplayContext(i);
                        if (displayContext2 == null) {
                            displayContext2 = desktopTilingDecorViewModel2.context;
                        }
                        return new Rect((displayContext2.getResources().getDimensionPixelSize(R.dimen.split_divider_bar_width) / 2) + (rect3.width() / 2) + rect3.left, rect3.top, rect3.right, rect3.bottom);
                    case 6:
                        return bubbleDropTargetBoundsProvider != null ? ((BubblePositioner) bubbleDropTargetBoundsProvider).getBubbleBarExpandedViewDropTargetBounds(true) : new Rect();
                    case 7:
                        return bubbleDropTargetBoundsProvider != null ? ((BubblePositioner) bubbleDropTargetBoundsProvider).getBubbleBarExpandedViewDropTargetBounds(false) : new Rect();
                    default:
                        throw new IllegalArgumentException("Invalid indicator type provided.");
                }
            }

            public static Rect getMinBounds(Rect rect) {
                return new Rect((int) ((rect.width() * 0.015f) + rect.left), (int) ((rect.height() * 0.015f) + rect.top), (int) (rect.right - (rect.width() * 0.015f)), (int) (rect.bottom - (rect.height() * 0.015f)));
            }

            public static void setupIndicatorAnimation(final VisualIndicatorAnimator visualIndicatorAnimator, final AlphaAnimType alphaAnimType, final DesktopModeVisualIndicator.IndicatorType indicatorType) {
                DesktopStateImpl.Companion companion = DesktopStateImpl.Companion;
                int i = visualIndicatorAnimator.displayId;
                companion.getClass();
                DesktopStateImpl.Companion.inDesktopWindowing(i);
                visualIndicatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$VisualIndicatorAnimator$Companion$setupIndicatorAnimation$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        Drawable background;
                        Drawable background2;
                        Drawable background3;
                        VisualIndicatorViewContainer.VisualIndicatorAnimator visualIndicatorAnimator2 = visualIndicatorAnimator;
                        float animatedFraction = valueAnimator.getAnimatedFraction();
                        View view = visualIndicatorAnimator.indicatorView;
                        if (!Intrinsics.areEqual(visualIndicatorAnimator2.indicatorStartBounds, visualIndicatorAnimator2.indicatorEndBounds)) {
                            Rect rectEvaluate = visualIndicatorAnimator2.mRectEvaluator.evaluate(animatedFraction, visualIndicatorAnimator2.indicatorStartBounds, visualIndicatorAnimator2.indicatorEndBounds);
                            DesktopStateImpl.Companion companion2 = DesktopStateImpl.Companion;
                            int i2 = visualIndicatorAnimator2.displayId;
                            companion2.getClass();
                            if (DesktopStateImpl.Companion.inDesktopWindowing(i2)) {
                                View view2 = visualIndicatorAnimator2.targetView;
                                if (view2 != null && (background3 = view2.getBackground()) != null) {
                                    background3.setBounds(rectEvaluate);
                                }
                                View view3 = visualIndicatorAnimator2.blurView;
                                if (view3 != null && (background2 = view3.getBackground()) != null) {
                                    background2.setBounds(rectEvaluate);
                                }
                            } else if (view != null && (background = view.getBackground()) != null) {
                                background.setBounds(rectEvaluate);
                            }
                        }
                        VisualIndicatorViewContainer.VisualIndicatorAnimator.AlphaAnimType alphaAnimType2 = alphaAnimType;
                        if (alphaAnimType2 == VisualIndicatorViewContainer.VisualIndicatorAnimator.AlphaAnimType.ALPHA_FADE_IN_ANIM) {
                            VisualIndicatorViewContainer.VisualIndicatorAnimator.access$updateIndicatorAlpha(visualIndicatorAnimator, valueAnimator.getAnimatedFraction(), visualIndicatorAnimator.indicatorView, indicatorType);
                        } else if (alphaAnimType2 == VisualIndicatorViewContainer.VisualIndicatorAnimator.AlphaAnimType.ALPHA_FADE_OUT_ANIM) {
                            VisualIndicatorViewContainer.VisualIndicatorAnimator.access$updateIndicatorAlpha(visualIndicatorAnimator, 1 - valueAnimator.getAnimatedFraction(), visualIndicatorAnimator.indicatorView, indicatorType);
                        }
                    }
                });
                visualIndicatorAnimator.addListener(new AnimatorListenerAdapter(visualIndicatorAnimator) { // from class: com.android.wm.shell.desktopmode.VisualIndicatorViewContainer$VisualIndicatorAnimator$Companion$setupIndicatorAnimation$2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                    }
                });
                if (indicatorType != DesktopModeVisualIndicator.IndicatorType.TO_DESKTOP_INDICATOR) {
                    visualIndicatorAnimator.setDuration(200L);
                } else if (alphaAnimType == AlphaAnimType.ALPHA_FADE_IN_ANIM) {
                    visualIndicatorAnimator.setDuration(100L);
                } else if (alphaAnimType == AlphaAnimType.ALPHA_FADE_OUT_ANIM) {
                    visualIndicatorAnimator.setDuration(300L);
                }
            }

            private Companion() {
            }
        }

        public VisualIndicatorAnimator(View view, Rect rect, Rect rect2, DisplayLayout displayLayout, int i) {
            Drawable background;
            this.indicatorView = view;
            Rect rect3 = new Rect(rect);
            this.indicatorStartBounds = rect3;
            this.indicatorEndBounds = rect2;
            this.displayId = i;
            setFloatValues(0.0f, 1.0f);
            this.mRectEvaluator = new RectEvaluator(new Rect());
            Resources resources = view.getContext().getResources();
            View viewFindViewById = view.findViewById(R.id.targetView);
            this.targetView = viewFindViewById;
            if (viewFindViewById != null && (background = viewFindViewById.getBackground()) != null) {
                background.setBounds(rect3);
            }
            SemBlurInfo semBlurInfoBuild = new SemBlurInfo.Builder(0).setRadius(103).setBackgroundCornerRadius(resources.getDimension(R.dimen.desktop_dnd_drop_corner_radius_size)).build();
            View viewFindViewById2 = view.findViewById(R.id.blurView);
            this.blurView = viewFindViewById2;
            if (viewFindViewById2 != null) {
                viewFindViewById2.semSetBlurInfo(semBlurInfoBuild);
            }
            this.deskLabelView = view.findViewById(R.id.labelContainer);
        }

        public static final void access$updateIndicatorAlpha(VisualIndicatorAnimator visualIndicatorAnimator, float f, View view, DesktopModeVisualIndicator.IndicatorType indicatorType) {
            Drawable background;
            Drawable background2;
            visualIndicatorAnimator.getClass();
            if (CoreRune.DW_TASK_SNAPSHOT_BLUR && indicatorType == DesktopModeVisualIndicator.IndicatorType.TO_DESKTOP_INDICATOR) {
                View view2 = visualIndicatorAnimator.blurView;
                if (view2 != null && (background2 = view2.getBackground()) != null) {
                    background2.setAlpha((int) (255 * f));
                }
                View view3 = visualIndicatorAnimator.deskLabelView;
                if (view3 != null) {
                    view3.setAlpha(255 * f);
                    return;
                }
                return;
            }
            DesktopStateImpl.Companion companion = DesktopStateImpl.Companion;
            int i = visualIndicatorAnimator.displayId;
            companion.getClass();
            if (!DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                if ((view != null ? view.getBackground() : null) == null && view != null) {
                    view.setBackgroundResource(R.drawable.desktop_windowing_transition_background);
                }
                LayerDrawable layerDrawable = (LayerDrawable) (view != null ? view.getBackground() : null);
                float f2 = 255 * f;
                layerDrawable.findDrawableByLayerId(R.id.indicator_stroke).setAlpha((int) f2);
                layerDrawable.findDrawableByLayerId(R.id.indicator_solid).setAlpha((int) (f2 * 0.35f));
                return;
            }
            View view4 = visualIndicatorAnimator.targetView;
            if (view4 != null) {
                view4.setAlpha(255 * f);
            }
            View view5 = visualIndicatorAnimator.blurView;
            if (view5 == null || (background = view5.getBackground()) == null) {
                return;
            }
            background.setAlpha((int) (255 * f));
        }

        public static /* synthetic */ void getIndicatorEndBounds$annotations() {
        }

        public static /* synthetic */ void getIndicatorStartBounds$annotations() {
        }
    }

    public static /* synthetic */ void getIndicatorView$annotations() {
    }
}
