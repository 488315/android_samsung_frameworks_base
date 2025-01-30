package com.android.p038wm.shell.freeform;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.GraphicBuffer;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.HardwareBuffer;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.window.TaskSnapshot;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.internal.widget.GridLayoutManager;
import com.android.internal.widget.RecyclerView;
import com.android.keyguard.punchhole.VIDirector$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.freeform.FreeformContainerFolderView;
import com.android.p038wm.shell.freeform.FreeformContainerManager;
import com.android.systemui.R;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
class FreeformContainerFolderView extends RecyclerView implements FreeformContainerCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final FolderViewAdapter mAdapter;
    public boolean mAnimatingSpringX;
    public boolean mAnimatingSpringY;
    public boolean mBlockDataUpdate;
    public final ArrayMap mCachedBitmaps;
    public ColorStateList mColorTintList;
    public final Context mContext;
    public int[] mDraggingIconReturnLocation;
    public Spring mDraggingIconSpringX;
    public Spring mDraggingIconSpringY;
    public ImageView mDraggingIconView;
    public Drawable mEmptySlotIcon;
    public FreeformThumbnailView mFreeformThumbnailView;

    /* renamed from: mH */
    public FreeformContainerManager.HandlerC3983H f445mH;
    public int mHeight;
    public boolean mIsCollapseAnimating;
    public boolean mIsExpandAnimating;
    public boolean mIsExpanded;
    public boolean mItemAddedWhileAnimating;
    public final FolderItemDecoration mItemDecoration;
    public int mItemSize;
    public float mLastPositionX;
    public float mLastPositionY;
    public int mLastScrollState;
    public final LayoutInflater mLayoutInflater;
    public final C39732 mLayoutManager;
    public int mMaxWidth;
    public final RunnableC39721 mOpenFolderRunnable;
    public int mPaddingLeft;
    public int mPaddingRight;
    public final SpringSystem mSpringSystem;
    public ImageView mTargetIconView;
    public FreeformContainerItem mTargetItem;
    public int mThresholdToMove;
    public final Rect mTmpBounds;
    public FreeformContainerFolderTrayView mTrayView;
    public FreeformContainerViewController mViewController;
    public int mVisibleIconCount;
    public int mVisibleIconMaxCount;
    public int mWidth;
    public final WindowManager mWindowManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.freeform.FreeformContainerFolderView$1 */
    public final class RunnableC39721 implements Runnable {
        public RunnableC39721() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            FreeformContainerFolderView.this.setHorizontalScrollBarEnabled(true);
            Log.i("FreeformContainer", "[FolderView] mOpenFolderRunnable Run()");
            for (int itemCount = FreeformContainerFolderView.this.mAdapter.getItemCount() - 1; itemCount >= 0; itemCount--) {
                FolderItemViewHolder folderItemViewHolder = (FolderItemViewHolder) FreeformContainerFolderView.this.findViewHolderForAdapterPosition(itemCount);
                if (folderItemViewHolder != null) {
                    final View view = ((RecyclerView.ViewHolder) folderItemViewHolder).itemView;
                    SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("[FolderView] openAnim, itemView=", itemCount, "FreeformContainer");
                    if (itemCount == 0) {
                        FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                        freeformContainerFolderView.mBlockDataUpdate = true;
                        freeformContainerFolderView.f445mH.postDelayed(new Runnable() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                final FreeformContainerFolderView.RunnableC39721 runnableC39721 = FreeformContainerFolderView.RunnableC39721.this;
                                View view2 = view;
                                runnableC39721.getClass();
                                view2.setVisibility(0);
                                FreeformContainerFolderView freeformContainerFolderView2 = FreeformContainerFolderView.this;
                                if (freeformContainerFolderView2.mBlockDataUpdate) {
                                    freeformContainerFolderView2.mBlockDataUpdate = false;
                                    if (freeformContainerFolderView2.mItemAddedWhileAnimating) {
                                        freeformContainerFolderView2.f445mH.postDelayed(new Runnable() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView$1$$ExternalSyntheticLambda1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                FreeformContainerFolderView freeformContainerFolderView3 = FreeformContainerFolderView.this;
                                                freeformContainerFolderView3.mItemAddedWhileAnimating = false;
                                                freeformContainerFolderView3.mAdapter.notifyDataSetChanged();
                                            }
                                        }, 100L);
                                    }
                                }
                            }
                        }, 213L);
                    } else {
                        Animation loadAnimation = AnimationUtils.loadAnimation(FreeformContainerFolderView.this.mContext, R.anim.freeform_container_icon_appearing_in_folder);
                        loadAnimation.setAnimationListener(new Animation.AnimationListener(this) { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.1.1
                            @Override // android.view.animation.Animation.AnimationListener
                            public final void onAnimationStart(Animation animation) {
                                view.setVisibility(0);
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public final void onAnimationEnd(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public final void onAnimationRepeat(Animation animation) {
                            }
                        });
                        view.startAnimation(loadAnimation);
                    }
                }
            }
            FreeformContainerFolderView.this.mIsExpandAnimating = false;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FolderItemDecoration extends RecyclerView.ItemDecoration {
        public final Rect mItemMargin;
        public int mItemSpace;

        public /* synthetic */ FolderItemDecoration(FreeformContainerFolderView freeformContainerFolderView, int i) {
            this();
        }

        public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
            int itemCount = getItemCount() - 1;
            Rect rect2 = this.mItemMargin;
            rect.top = rect2.top;
            rect.bottom = rect2.bottom;
            if (FreeformContainerFolderView.this.isLayoutRtl()) {
                if (childAdapterPosition == 0) {
                    rect.right = this.mItemMargin.right;
                }
                if (childAdapterPosition == itemCount) {
                    rect.left = this.mItemMargin.left;
                    return;
                } else {
                    rect.left = this.mItemSpace;
                    return;
                }
            }
            if (childAdapterPosition == 0) {
                rect.left = this.mItemMargin.left;
            }
            if (childAdapterPosition == itemCount) {
                rect.right = this.mItemMargin.right;
            } else {
                rect.right = this.mItemSpace;
            }
        }

        private FolderItemDecoration() {
            this.mItemMargin = new Rect();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FolderItemViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {
        public final ViewOnHoverListenerC3971xdcc3db21 mButtonHoverListener;
        public final RunnableC39802 mDismissPreview;
        public final ImageView mIconView;
        public boolean mIsVisiblePreview;
        public FreeformContainerItem mItem;
        public final RunnableC39791 mShowPreview;

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.freeform.FreeformContainerFolderView$FolderItemViewHolder$1] */
        /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.freeform.FreeformContainerFolderView$FolderItemViewHolder$2] */
        /* JADX WARN: Type inference failed for: r0v2, types: [android.view.View$OnHoverListener, com.android.wm.shell.freeform.FreeformContainerFolderView$FolderItemViewHolder$$ExternalSyntheticLambda0] */
        public FolderItemViewHolder(View view) {
            super(view);
            this.mIsVisiblePreview = false;
            this.mShowPreview = new Runnable() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.FolderItemViewHolder.1
                @Override // java.lang.Runnable
                public final void run() {
                    float f;
                    float f2;
                    int height;
                    GraphicBuffer snapshot;
                    FolderItemViewHolder folderItemViewHolder = FolderItemViewHolder.this;
                    if (FreeformContainerFolderView.this.mFreeformThumbnailView == null) {
                        return;
                    }
                    int taskId = folderItemViewHolder.mItem.getTaskId();
                    Bitmap bitmap = (Bitmap) FreeformContainerFolderView.this.mCachedBitmaps.get(Integer.valueOf(taskId));
                    if (bitmap == null) {
                        bitmap = null;
                        try {
                            TaskSnapshot taskSnapshot = ActivityTaskManager.getService().getTaskSnapshot(taskId, true, false);
                            if (taskSnapshot != null && (snapshot = taskSnapshot.getSnapshot()) != null) {
                                Bitmap wrapHardwareBuffer = Bitmap.wrapHardwareBuffer(HardwareBuffer.createFromGraphicBuffer(snapshot), null);
                                FreeformContainerFolderView.this.mCachedBitmaps.put(Integer.valueOf(taskId), wrapHardwareBuffer);
                                bitmap = wrapHardwareBuffer;
                            }
                        } catch (RemoteException e) {
                            Log.e("FreeformContainer", "Failed to get task snapshot, taskId=" + e);
                        }
                    }
                    if (bitmap == null) {
                        return;
                    }
                    FolderItemViewHolder folderItemViewHolder2 = FolderItemViewHolder.this;
                    folderItemViewHolder2.mIsVisiblePreview = true;
                    FreeformThumbnailView freeformThumbnailView = FreeformContainerFolderView.this.mFreeformThumbnailView;
                    freeformThumbnailView.mBitmap = bitmap;
                    int i = Settings.Global.getInt(freeformThumbnailView.mContext.getContentResolver(), "freeform_caption_type", 0);
                    Context context = freeformThumbnailView.mContext;
                    boolean z = i == 1;
                    Resources resources = context.getResources();
                    int dimensionPixelSize = z ? resources.getDimensionPixelSize(android.R.dimen.timepicker_time_label_size) : resources.getDimensionPixelSize(android.R.dimen.tooltip_margin);
                    Bitmap bitmap2 = freeformThumbnailView.mBitmap;
                    Bitmap createBitmap = Bitmap.createBitmap(bitmap2, 0, dimensionPixelSize, bitmap2.getWidth(), freeformThumbnailView.mBitmap.getHeight() - dimensionPixelSize);
                    float width = createBitmap.getWidth();
                    float height2 = createBitmap.getHeight();
                    if (width > height2) {
                        if (width > freeformThumbnailView.mMaxSize) {
                            Log.d("FreeformThumbnailView", "Width recompute");
                            float f3 = width / height2;
                            if (f3 != 0.0f) {
                                f2 = freeformThumbnailView.mMaxSize;
                                f = f2 / f3;
                                float f4 = f2;
                                height2 = f;
                                width = f4;
                            }
                        }
                        int i2 = (int) width;
                        freeformThumbnailView.mWidth = i2;
                        int i3 = (int) height2;
                        freeformThumbnailView.mHeight = i3;
                        freeformThumbnailView.mBitmap = Bitmap.createScaledBitmap(createBitmap, i2, i3, true);
                    } else {
                        if (height2 > freeformThumbnailView.mMaxSize) {
                            Log.d("FreeformThumbnailView", "Height recompute");
                            float f5 = height2 / width;
                            if (f5 != 0.0f) {
                                f = freeformThumbnailView.mMaxSize;
                                f2 = f / f5;
                                float f42 = f2;
                                height2 = f;
                                width = f42;
                            }
                        }
                        int i22 = (int) width;
                        freeformThumbnailView.mWidth = i22;
                        int i32 = (int) height2;
                        freeformThumbnailView.mHeight = i32;
                        freeformThumbnailView.mBitmap = Bitmap.createScaledBitmap(createBitmap, i22, i32, true);
                    }
                    freeformThumbnailView.mImageView.setImageBitmap(freeformThumbnailView.mBitmap);
                    FolderItemViewHolder folderItemViewHolder3 = FolderItemViewHolder.this;
                    FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                    FreeformThumbnailView freeformThumbnailView2 = freeformContainerFolderView.mFreeformThumbnailView;
                    ImageView imageView = folderItemViewHolder3.mIconView;
                    Rect rect = freeformContainerFolderView.mItemDecoration.mItemMargin;
                    ViewGroup.LayoutParams layoutParams = freeformThumbnailView2.mImageView.getLayoutParams();
                    int[] locationOnScreen = imageView.getLocationOnScreen();
                    int width2 = (imageView.getWidth() / 2) + locationOnScreen[0];
                    int i4 = locationOnScreen[1];
                    freeformThumbnailView2.mPivot.set(width2, i4);
                    int i5 = freeformThumbnailView2.mHeight;
                    int i6 = rect.top + i5;
                    Rect rect2 = freeformThumbnailView2.mStableInsets;
                    if ((i4 - i6) - rect2.top > 0) {
                        height = i4 - (i6 + freeformThumbnailView2.mMargin);
                    } else {
                        int i7 = rect.bottom + i5 + i4;
                        int i8 = freeformThumbnailView2.mDisplaySize.y - rect2.bottom;
                        height = i7 > i8 ? i8 - i5 : imageView.getHeight() + i4 + rect.bottom + freeformThumbnailView2.mMargin;
                    }
                    int i9 = freeformThumbnailView2.mWidth;
                    int i10 = i9 / 2;
                    int i11 = width2 - i10;
                    Rect rect3 = freeformThumbnailView2.mStableInsets;
                    int i12 = rect3.left;
                    if (i11 - i12 < 0) {
                        i11 = i12;
                    } else {
                        int i13 = i10 + width2;
                        int i14 = rect3.right;
                        int i15 = i13 + i14;
                        int i16 = freeformThumbnailView2.mDisplaySize.x;
                        if (i15 > i16 - i14) {
                            i11 = (i16 - i9) - i14;
                        }
                    }
                    freeformThumbnailView2.mView.setX(i11);
                    freeformThumbnailView2.mView.setY(height);
                    layoutParams.height = freeformThumbnailView2.mHeight;
                    layoutParams.width = freeformThumbnailView2.mWidth;
                    freeformThumbnailView2.mImageView.setLayoutParams(layoutParams);
                    FreeformContainerFolderView.this.mFreeformThumbnailView.scheduleAnimation(true);
                    if (CoreRune.MW_SA_LOGGING) {
                        CoreSaLogger.logForAdvanced("2204");
                    }
                }
            };
            this.mDismissPreview = new Runnable() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.FolderItemViewHolder.2
                @Override // java.lang.Runnable
                public final void run() {
                    FolderItemViewHolder folderItemViewHolder = FolderItemViewHolder.this;
                    FreeformThumbnailView freeformThumbnailView = FreeformContainerFolderView.this.mFreeformThumbnailView;
                    if (freeformThumbnailView == null || !folderItemViewHolder.mIsVisiblePreview) {
                        return;
                    }
                    freeformThumbnailView.scheduleAnimation(false);
                    FolderItemViewHolder.this.mIsVisiblePreview = false;
                }
            };
            ?? r0 = new View.OnHoverListener() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView$FolderItemViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view2, MotionEvent motionEvent) {
                    int action;
                    FreeformThumbnailView freeformThumbnailView;
                    FreeformContainerFolderView.FolderItemViewHolder folderItemViewHolder = FreeformContainerFolderView.FolderItemViewHolder.this;
                    if (FreeformContainerFolderView.this.mFreeformThumbnailView != null && (action = motionEvent.getAction()) != 7) {
                        FreeformContainerFolderView.this.f445mH.removeCallbacks(folderItemViewHolder.mDismissPreview);
                        FreeformContainerFolderView.this.f445mH.removeCallbacks(folderItemViewHolder.mShowPreview);
                        if (action == 9) {
                            FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                            freeformContainerFolderView.getClass();
                            if (CoreRune.MW_FREEFORM_MINIMIZED_PREVIEW && (freeformThumbnailView = freeformContainerFolderView.mFreeformThumbnailView) != null && !freeformThumbnailView.isAttachedToWindow()) {
                                WindowManager windowManager = freeformContainerFolderView.mWindowManager;
                                FreeformThumbnailView freeformThumbnailView2 = freeformContainerFolderView.mFreeformThumbnailView;
                                freeformThumbnailView2.getClass();
                                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2604, 16778040, -3);
                                layoutParams.setTitle("freeform-thumbnail");
                                layoutParams.privateFlags |= 536870992;
                                layoutParams.setFitInsetsTypes(0);
                                layoutParams.layoutInDisplayCutoutMode = 1;
                                layoutParams.gravity = 8388659;
                                layoutParams.x = 0;
                                layoutParams.y = 0;
                                windowManager.addView(freeformThumbnailView2, layoutParams);
                            }
                            FreeformContainerFolderView.this.f445mH.postDelayed(folderItemViewHolder.mShowPreview, 400L);
                        } else {
                            FreeformContainerFolderView.this.f445mH.post(folderItemViewHolder.mDismissPreview);
                        }
                    }
                    return false;
                }
            };
            this.mButtonHoverListener = r0;
            ImageView imageView = (ImageView) view.findViewById(R.id.freeform_container_item_image);
            this.mIconView = imageView;
            imageView.setHapticFeedbackEnabled(false);
            if (CoreRune.MW_FREEFORM_MINIMIZED_PREVIEW) {
                imageView.setOnHoverListener(r0);
            }
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            float rawX = motionEvent.getRawX();
            float rawY = motionEvent.getRawY();
            int action = motionEvent.getAction();
            if (action == 0) {
                FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                freeformContainerFolderView.mLastPositionX = rawX;
                freeformContainerFolderView.mLastPositionY = rawY;
                Log.i("FreeformContainer", "[FolderView] onTouch(" + MotionEvent.actionToString(action) + ")");
            } else {
                if (action == 1) {
                    Log.i("FreeformContainer", "[FolderView] onTouch(" + MotionEvent.actionToString(action) + ")");
                    FreeformContainerItem freeformContainerItem = this.mItem;
                    if (freeformContainerItem != null) {
                        FreeformContainerFolderView.this.f445mH.sendMessage(30, freeformContainerItem);
                    }
                    FreeformContainerFolderView.this.mViewController.updateContainerState(1, true, true);
                    return false;
                }
                if (action == 2) {
                    int itemCount = FreeformContainerFolderView.this.mAdapter.getItemCount();
                    FreeformContainerFolderView freeformContainerFolderView2 = FreeformContainerFolderView.this;
                    float f = itemCount < freeformContainerFolderView2.mVisibleIconMaxCount ? rawX - freeformContainerFolderView2.mLastPositionX : 0.0f;
                    float f2 = rawY - freeformContainerFolderView2.mLastPositionY;
                    freeformContainerFolderView2.getDraggingAppIconBounds(freeformContainerFolderView2.mTmpBounds);
                    float hypot = (float) Math.hypot(f, f2);
                    FreeformContainerFolderView freeformContainerFolderView3 = FreeformContainerFolderView.this;
                    if ((hypot < ((float) freeformContainerFolderView3.mThresholdToMove) || freeformContainerFolderView3.mViewController.isDismissButtonShowing() || FreeformContainerFolderView.this.isSpringAnimating()) ? false : true) {
                        FreeformContainerFolderView freeformContainerFolderView4 = FreeformContainerFolderView.this;
                        FreeformContainerViewController freeformContainerViewController = freeformContainerFolderView4.mViewController;
                        Rect rect = freeformContainerFolderView4.mTmpBounds;
                        freeformContainerViewController.createOrUpdateDismissButton();
                        freeformContainerViewController.mDismissButtonView.show(rect);
                        FreeformContainerFolderView.this.finishDraggingAppIcon();
                        FreeformContainerFolderView freeformContainerFolderView5 = FreeformContainerFolderView.this;
                        ImageView imageView = this.mIconView;
                        freeformContainerFolderView5.mTargetIconView = imageView;
                        freeformContainerFolderView5.mTargetItem = this.mItem;
                        if (imageView != null) {
                            freeformContainerFolderView5.mDraggingIconReturnLocation = imageView.getLocationOnScreen();
                            freeformContainerFolderView5.mDraggingIconView.setX(r0[0]);
                            freeformContainerFolderView5.mDraggingIconView.setY(freeformContainerFolderView5.mDraggingIconReturnLocation[1]);
                            if (freeformContainerFolderView5.mTargetIconView.getDrawable() == null || freeformContainerFolderView5.mTargetIconView.getDrawable().getConstantState() == null) {
                                Log.e("FreeformContainer", "[FolderView] startDraggingAppIcon: failed to newDrawable()");
                            } else {
                                freeformContainerFolderView5.mDraggingIconView.setImageDrawable(freeformContainerFolderView5.mTargetIconView.getDrawable().getConstantState().newDrawable());
                                freeformContainerFolderView5.mDraggingIconView.setVisibility(0);
                            }
                        } else {
                            Log.e("FreeformContainer", "[FolderView] mTargetIconView is null");
                        }
                        this.mIconView.setImageDrawable(FreeformContainerFolderView.this.mEmptySlotIcon);
                        Log.i("FreeformContainer", "[FolderView] onTouch(" + MotionEvent.actionToString(action) + "): Ready to move");
                        return false;
                    }
                }
            }
            return true;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FolderViewAdapter extends RecyclerView.Adapter {
        public /* synthetic */ FolderViewAdapter(FreeformContainerFolderView freeformContainerFolderView, int i) {
            this();
        }

        public final int getItemCount() {
            FreeformContainerView freeformContainerView;
            FreeformContainerViewController freeformContainerViewController = FreeformContainerFolderView.this.mViewController;
            if (freeformContainerViewController == null || (freeformContainerView = freeformContainerViewController.mContainerView) == null) {
                return 0;
            }
            return freeformContainerView.getIconViewListCount();
        }

        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("[FolderView] onBindViewHolder: position=", i, "FreeformContainer");
        }

        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
            LayoutInflater layoutInflater = freeformContainerFolderView.mLayoutInflater;
            Objects.requireNonNull(layoutInflater);
            return freeformContainerFolderView.new FolderItemViewHolder(layoutInflater.inflate(R.layout.freeform_container_item, (ViewGroup) null));
        }

        public final void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
            FolderItemViewHolder folderItemViewHolder = (FolderItemViewHolder) viewHolder;
            super.onViewAttachedToWindow(folderItemViewHolder);
            int adapterPosition = folderItemViewHolder.getAdapterPosition();
            if (adapterPosition == -1) {
                return;
            }
            folderItemViewHolder.mItem = (FreeformContainerItem) FreeformContainerFolderView.this.mViewController.mItemController.mItemList.get(adapterPosition);
            ImageView imageView = folderItemViewHolder.mIconView;
            Objects.requireNonNull(imageView);
            imageView.setImageDrawable(folderItemViewHolder.mItem.mShowingIcon);
            ((RecyclerView.ViewHolder) folderItemViewHolder).itemView.setOnTouchListener(folderItemViewHolder);
            ((RecyclerView.ViewHolder) folderItemViewHolder).itemView.setContentDescription(folderItemViewHolder.mItem.mDescription);
            ((RecyclerView.ViewHolder) folderItemViewHolder).itemView.setVisibility(FreeformContainerFolderView.this.mIsExpandAnimating ? 4 : 0);
            if (CoreRune.MW_FREEFORM_MINIMIZED_PREVIEW) {
                return;
            }
            ((RecyclerView.ViewHolder) folderItemViewHolder).itemView.setTooltip(folderItemViewHolder.mItem.mDescription);
        }

        public final void onViewDetachedFromWindow(RecyclerView.ViewHolder viewHolder) {
            FolderItemViewHolder folderItemViewHolder = (FolderItemViewHolder) viewHolder;
            super.onViewDetachedFromWindow(folderItemViewHolder);
            ImageView imageView = folderItemViewHolder.mIconView;
            Objects.requireNonNull(imageView);
            imageView.setImageDrawable(null);
            ((RecyclerView.ViewHolder) folderItemViewHolder).itemView.setOnTouchListener(null);
        }

        private FolderViewAdapter() {
        }
    }

    /* renamed from: -$$Nest$mrestoreAppIcon, reason: not valid java name */
    public static void m2748$$Nest$mrestoreAppIcon(FreeformContainerFolderView freeformContainerFolderView) {
        ImageView imageView;
        int itemCount = freeformContainerFolderView.mAdapter.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            FolderItemViewHolder folderItemViewHolder = (FolderItemViewHolder) freeformContainerFolderView.findViewHolderForAdapterPosition(i);
            if (folderItemViewHolder != null && folderItemViewHolder.mItem.equals(freeformContainerFolderView.mTargetItem) && (imageView = freeformContainerFolderView.mDraggingIconView) != null && imageView.getDrawable() != null && freeformContainerFolderView.mDraggingIconView.getDrawable().getConstantState() != null) {
                folderItemViewHolder.mIconView.setImageDrawable(freeformContainerFolderView.mDraggingIconView.getDrawable().getConstantState().newDrawable());
            }
        }
    }

    public FreeformContainerFolderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSpringSystem = SpringSystem.create();
        this.mTargetItem = null;
        this.mTmpBounds = new Rect();
        int i = 0;
        this.mIsExpanded = false;
        this.mIsExpandAnimating = false;
        this.mIsCollapseAnimating = false;
        this.mBlockDataUpdate = false;
        this.mItemAddedWhileAnimating = false;
        this.mLastScrollState = 0;
        this.mDraggingIconReturnLocation = new int[2];
        this.mAnimatingSpringX = false;
        this.mAnimatingSpringY = false;
        this.mCachedBitmaps = new ArrayMap();
        this.mOpenFolderRunnable = new RunnableC39721();
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mLayoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 1, 0, isLayoutRtl()) { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.2
            public final boolean canScrollHorizontally() {
                FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                int i2 = FreeformContainerFolderView.$r8$clinit;
                return !freeformContainerFolderView.isSpringAnimating();
            }
        };
        this.mLayoutManager = layoutManager;
        FolderViewAdapter folderViewAdapter = new FolderViewAdapter(this, i);
        this.mAdapter = folderViewAdapter;
        FolderItemDecoration folderItemDecoration = new FolderItemDecoration(this, i);
        this.mItemDecoration = folderItemDecoration;
        setLayoutManager(layoutManager);
        setAdapter(folderViewAdapter);
        addItemDecoration(folderItemDecoration);
        setClipToOutline(true);
        addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.3
            public final void onScrollStateChanged(RecyclerView recyclerView, int i2) {
                super.onScrollStateChanged(recyclerView, i2);
                FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                if (freeformContainerFolderView.mLastScrollState == 0 && i2 == 1) {
                    freeformContainerFolderView.finishDraggingAppIcon();
                }
                FreeformContainerFolderView.this.mLastScrollState = i2;
            }
        });
    }

    public final void calculateFolderSize() {
        int width = (this.mViewController.mNonDecorDisplayFrame.width() - this.mPaddingLeft) - this.mPaddingRight;
        FolderItemDecoration folderItemDecoration = this.mItemDecoration;
        Rect rect = folderItemDecoration.mItemMargin;
        FreeformContainerFolderTrayView freeformContainerFolderTrayView = this.mTrayView;
        int i = freeformContainerFolderTrayView.mCloseButtonSize + freeformContainerFolderTrayView.mOpenAllAppsButtonSize;
        int i2 = rect.left;
        int i3 = this.mItemSize + folderItemDecoration.mItemSpace;
        int i4 = this.mVisibleIconMaxCount;
        this.mMaxWidth = (i3 * i4) + i2;
        this.mVisibleIconCount = i4;
        while (true) {
            int i5 = this.mMaxWidth;
            if (width >= rect.right + i5 + i) {
                break;
            }
            this.mMaxWidth = i5 - (this.mItemSize + this.mItemDecoration.mItemSpace);
            this.mVisibleIconCount--;
        }
        if (this.mAdapter.getItemCount() > 20) {
            return;
        }
        int itemCount = this.mAdapter.getItemCount();
        int i6 = this.mWidth;
        int i7 = this.mHeight;
        FolderItemDecoration folderItemDecoration2 = this.mItemDecoration;
        int i8 = ((this.mItemSize + folderItemDecoration2.mItemSpace) * itemCount) + folderItemDecoration2.mItemMargin.left;
        this.mWidth = i8;
        int min = Math.min(i8, this.mMaxWidth);
        this.mWidth = min;
        Rect rect2 = this.mItemDecoration.mItemMargin;
        int i9 = rect2.top + this.mItemSize + rect2.bottom;
        this.mHeight = i9;
        if (i6 == min && i7 == i9) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = this.mWidth;
        layoutParams.height = this.mHeight;
        setLayoutParams(layoutParams);
        FreeformContainerFolderTrayView freeformContainerFolderTrayView2 = this.mTrayView;
        int i10 = this.mWidth;
        int i11 = this.mHeight;
        freeformContainerFolderTrayView2.mWidth = i10 + freeformContainerFolderTrayView2.mItemMargin.right + freeformContainerFolderTrayView2.mCloseButtonSize + freeformContainerFolderTrayView2.mOpenAllAppsButtonSize;
        freeformContainerFolderTrayView2.mHeight = i11;
        ViewGroup.LayoutParams layoutParams2 = freeformContainerFolderTrayView2.getLayoutParams();
        layoutParams2.width = freeformContainerFolderTrayView2.mWidth;
        layoutParams2.height = freeformContainerFolderTrayView2.mHeight;
        freeformContainerFolderTrayView2.setLayoutParams(layoutParams2);
        Log.i("FreeformContainer", "[FolderView] updateFolderSize: itemCount=" + itemCount + ", size=(" + this.mWidth + "x" + this.mHeight + ")");
    }

    public final void collapse(boolean z) {
        FreeformThumbnailView freeformThumbnailView;
        if (this.mIsExpanded) {
            this.mIsExpanded = false;
            this.mBlockDataUpdate = false;
            this.mItemAddedWhileAnimating = false;
            scrollToPosition(0);
            finishDraggingAppIcon();
            this.f445mH.removeCallbacks(this.mOpenFolderRunnable);
            int itemCount = this.mAdapter.getItemCount();
            for (int i = 0; i < itemCount; i++) {
                FolderItemViewHolder folderItemViewHolder = (FolderItemViewHolder) findViewHolderForAdapterPosition(i);
                if (folderItemViewHolder != null) {
                    ((RecyclerView.ViewHolder) folderItemViewHolder).itemView.clearAnimation();
                }
            }
            this.mAdapter.notifyDataSetChanged();
            setAdapter((RecyclerView.Adapter) null);
            this.mTrayView.clearAnimation();
            if (z) {
                Log.i("FreeformContainer", "[FolderView] animateCollapse");
                this.mIsCollapseAnimating = true;
                Animator loadAnimator = AnimatorInflater.loadAnimator(this.mContext, R.anim.freeform_container_folder_collapse);
                loadAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.4
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        FreeformContainerFolderView.this.setVisibility(8);
                        FreeformContainerFolderView.this.setAlpha(1.0f);
                        FreeformContainerFolderView.this.mViewController.closeFullscreenMode("fullscreen_mode_request_folder");
                        FreeformContainerFolderView.this.mIsCollapseAnimating = false;
                    }
                });
                loadAnimator.setTarget(this);
                loadAnimator.start();
                this.mTrayView.setVisibility(8);
            } else {
                setVisibility(8);
                this.mViewController.closeFullscreenMode("fullscreen_mode_request_folder");
                this.mTrayView.setVisibility(8);
            }
            boolean z2 = CoreRune.MW_FREEFORM_MINIMIZED_PREVIEW;
            if (z2) {
                if (z2 && (freeformThumbnailView = this.mFreeformThumbnailView) != null) {
                    if (freeformThumbnailView.isAttachedToWindow()) {
                        this.mWindowManager.removeViewImmediate(this.mFreeformThumbnailView);
                    }
                    this.mFreeformThumbnailView = null;
                }
                this.mCachedBitmaps.clear();
            }
        }
    }

    public final void finishDraggingAppIcon() {
        if (isSpringAnimating()) {
            Spring spring = this.mDraggingIconSpringX;
            if (spring != null) {
                spring.mListeners.clear();
            }
            Spring spring2 = this.mDraggingIconSpringY;
            if (spring2 != null) {
                spring2.mListeners.clear();
            }
            this.mAnimatingSpringX = false;
            this.mAnimatingSpringY = false;
        }
        this.mTargetIconView = null;
        this.mTargetItem = null;
        this.mDraggingIconView.setImageDrawable(null);
        this.mDraggingIconView.setVisibility(8);
    }

    public final void getDraggingAppIconBounds(Rect rect) {
        int x = (int) this.mDraggingIconView.getX();
        int y = (int) this.mDraggingIconView.getY();
        rect.set(x, y, this.mDraggingIconView.getWidth() + x, this.mDraggingIconView.getHeight() + y);
    }

    public final void getTrayBounds(Rect rect) {
        FreeformContainerFolderTrayView freeformContainerFolderTrayView = this.mTrayView;
        int x = (int) freeformContainerFolderTrayView.getX();
        int y = (int) freeformContainerFolderTrayView.getY();
        freeformContainerFolderTrayView.mTmpBounds.set(x, y, freeformContainerFolderTrayView.mWidth + x, freeformContainerFolderTrayView.mHeight + y);
        rect.set(freeformContainerFolderTrayView.mTmpBounds);
    }

    public final boolean isSpringAnimating() {
        return this.mAnimatingSpringX || this.mAnimatingSpringY;
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mViewController.isDismissButtonShowing()) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // com.android.p038wm.shell.freeform.FreeformContainerCallback
    public final void onItemAdded(FreeformContainerItem freeformContainerItem) {
        if (!this.mBlockDataUpdate) {
            this.mAdapter.notifyDataSetChanged();
        } else {
            this.mItemAddedWhileAnimating = true;
            Log.i("FreeformContainer", "[FolderView] onItemAdded: item is added while opening folder");
        }
    }

    @Override // com.android.p038wm.shell.freeform.FreeformContainerCallback
    public final void onItemRemoved(FreeformContainerItem freeformContainerItem) {
        this.mAdapter.notifyDataSetChanged();
        if (this.mAdapter.getItemCount() == 1) {
            this.mViewController.updateContainerState(1, true, true);
        }
        if (!this.mIsCollapseAnimating) {
            calculateFolderSize();
        }
        finishDraggingAppIcon();
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001b, code lost:
    
        if (r2 != 3) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mViewController.isDismissButtonShowing()) {
            float rawX = motionEvent.getRawX();
            float rawY = motionEvent.getRawY();
            int action = motionEvent.getAction();
            if (action != 1) {
                if (action == 2) {
                    ImageView imageView = this.mDraggingIconView;
                    imageView.setX((rawX - this.mLastPositionX) + imageView.getX());
                    ImageView imageView2 = this.mDraggingIconView;
                    imageView2.setY((rawY - this.mLastPositionY) + imageView2.getY());
                    this.mLastPositionX = rawX;
                    this.mLastPositionY = rawY;
                    getDraggingAppIconBounds(this.mTmpBounds);
                    FreeformContainerViewController freeformContainerViewController = this.mViewController;
                    Rect rect = this.mTmpBounds;
                    FreeformContainerDismissButtonView freeformContainerDismissButtonView = freeformContainerViewController.mDismissButtonView;
                    if (freeformContainerDismissButtonView != null) {
                        freeformContainerDismissButtonView.mDismissButtonManager.mView.updateView(rect);
                    }
                    return true;
                }
            }
            getDraggingAppIconBounds(this.mTmpBounds);
            this.mViewController.hideDismissButtonAndDismissIcon(this.mTargetItem, this.mDraggingIconView, this.mTmpBounds);
            FreeformContainerDismissButtonView freeformContainerDismissButtonView2 = this.mViewController.mDismissButtonView;
            if (!(freeformContainerDismissButtonView2 == null ? false : freeformContainerDismissButtonView2.mDismissButtonManager.mView.mIsEnterDismissButton)) {
                float translationX = this.mDraggingIconView.getTranslationX();
                float translationY = this.mDraggingIconView.getTranslationY();
                int[] iArr = this.mDraggingIconReturnLocation;
                float f = iArr[0];
                float f2 = iArr[1];
                this.mAnimatingSpringX = Math.abs(translationX - f) > 2.0f;
                this.mAnimatingSpringY = Math.abs(translationY - f2) > 2.0f;
                if (isSpringAnimating()) {
                    Spring createSpring = this.mSpringSystem.createSpring();
                    this.mDraggingIconSpringX = createSpring;
                    createSpring.setSpringConfig(new SpringConfig(180.0d, 18.0d));
                    Spring spring = this.mDraggingIconSpringX;
                    spring.mRestSpeedThreshold = 0.30000001192092896d;
                    spring.mDisplacementFromRestThreshold = 0.30000001192092896d;
                    spring.addListener(new SimpleSpringListener() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.6
                        @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
                        public final void onSpringAtRest(Spring spring2) {
                            FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                            freeformContainerFolderView.mAnimatingSpringX = false;
                            if (freeformContainerFolderView.isSpringAnimating()) {
                                return;
                            }
                            Log.i("FreeformContainer", "[FolderView] onSpringAtRest of springX, releaseDraggingState");
                            FreeformContainerFolderView.m2748$$Nest$mrestoreAppIcon(freeformContainerFolderView);
                            freeformContainerFolderView.finishDraggingAppIcon();
                        }

                        @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
                        public final void onSpringUpdate(Spring spring2) {
                            FreeformContainerFolderView.this.mDraggingIconView.setX((float) spring2.mCurrentState.position);
                        }
                    });
                    this.mDraggingIconSpringX.setCurrentValue(translationX);
                    this.mDraggingIconSpringX.setEndValue(f);
                    Spring createSpring2 = this.mSpringSystem.createSpring();
                    this.mDraggingIconSpringY = createSpring2;
                    createSpring2.setSpringConfig(new SpringConfig(180.0d, 18.0d));
                    Spring spring2 = this.mDraggingIconSpringY;
                    spring2.mRestSpeedThreshold = 0.30000001192092896d;
                    spring2.mDisplacementFromRestThreshold = 0.30000001192092896d;
                    spring2.addListener(new SimpleSpringListener() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.7
                        @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
                        public final void onSpringAtRest(Spring spring3) {
                            FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                            freeformContainerFolderView.mAnimatingSpringY = false;
                            if (freeformContainerFolderView.isSpringAnimating()) {
                                return;
                            }
                            Log.i("FreeformContainer", "[FolderView] onSpringAtRest of springY, releaseDraggingState");
                            FreeformContainerFolderView.m2748$$Nest$mrestoreAppIcon(freeformContainerFolderView);
                            freeformContainerFolderView.finishDraggingAppIcon();
                        }

                        @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
                        public final void onSpringUpdate(Spring spring3) {
                            FreeformContainerFolderView.this.mDraggingIconView.setY((float) spring3.mCurrentState.position);
                        }
                    });
                    this.mDraggingIconSpringY.setCurrentValue(translationY);
                    this.mDraggingIconSpringY.setEndValue(f2);
                } else {
                    StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("[FolderView] animateToReturnDraggingAppIconView: spring failed, from=[", translationX, ",", translationY, "], to=[");
                    m88m.append(f);
                    m88m.append(",");
                    m88m.append(f2);
                    m88m.append("], call finishDraggingAppIcon()");
                    Log.i("FreeformContainer", m88m.toString());
                    finishDraggingAppIcon();
                }
            }
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // com.android.p038wm.shell.freeform.FreeformContainerCallback
    public final void onViewDestroyed() {
    }

    @Override // com.android.p038wm.shell.freeform.FreeformContainerCallback
    public final void onRotationChanged(int i, int i2, Rect rect) {
    }
}
