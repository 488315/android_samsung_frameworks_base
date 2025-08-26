package com.android.wm.shell.freeform;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.GraphicBuffer;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.HardwareBuffer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.window.TaskSnapshot;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import com.android.internal.widget.GridLayoutManager;
import com.android.internal.widget.LinearLayoutManager;
import com.android.internal.widget.RecyclerView;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.freeform.FreeformContainerFolderView;
import com.android.wm.shell.freeform.FreeformContainerManager;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;
import com.samsung.android.rune.CoreRune;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
class FreeformContainerFolderView extends RecyclerView implements FreeformContainerCallback {
    public static final float[] TAIL_ICON_ALPHA_ARRAY = {1.0f, 0.5f, 0.1f};
    public static final float[] TAIL_ICON_SCALE_ARRAY = {1.0f, 0.9f, 0.81f};
    public final FolderViewAdapter mAdapter;
    public int mAirViewMargin;
    public boolean mAnimatingSpringPreviewX;
    public boolean mAnimatingSpringPreviewY;
    public boolean mAnimatingSpringX;
    public boolean mAnimatingSpringY;
    public boolean mBlockDataUpdate;
    public final ArrayMap mCachedBitmaps;
    public final Context mContext;
    public final Point mDisplaySize;
    public final int[] mDraggingIconReturnLocation;
    public Spring mDraggingIconSpringX;
    public Spring mDraggingIconSpringY;
    public ImageView mDraggingIconView;
    public ImageView mDraggingPreview;
    public final int[] mDraggingPreviewReturnLocation;
    public Spring mDraggingPreviewSpringX;
    public Spring mDraggingPreviewSpringY;
    public Drawable mEmptySlotIcon;
    public int mFolderMaxWidth;
    public FreeformThumbnailView mFreeformThumbnailView;
    public FreeformContainerManager.H mH;
    public boolean mHasIconMoved;
    public boolean mHasPreviewMoved;
    public int mHeight;
    public boolean mIsAppIconMoving;
    public boolean mIsCollapseAnimating;
    public boolean mIsCollapsePreview;
    public boolean mIsExpandAnimating;
    public boolean mIsExpanded;
    public boolean mItemAddedWhileAnimating;
    public final FolderItemDecoration mItemDecoration;
    public int mItemSize;
    public float mLastPositionX;
    public float mLastPositionY;
    public int mLastScrollState;
    public final LayoutInflater mLayoutInflater;
    public final AnonymousClass2 mLayoutManager;
    public int mMaxWidth;
    public MultiInstancePreviewAdapter mMultiInstancePreviewAdapter;
    public final AnonymousClass1 mOpenFolderRunnable;
    public int mOrientation;
    public int mPaddingLeft;
    public int mPaddingRight;
    public int mPointerSettleDownGap;
    public MultiInstancePreviewPopupWindow mPopupWindow;
    public int mPreviewHeight;
    public RecyclerView mPreviewRecycler;
    public int mPreviewWidth;
    public final SettingsObserver mSettingsObserver;
    public final SpringSystem mSpringSystem;
    public final Rect mStableInsets;
    public ImageView mTargetIconView;
    public FreeformContainerItem mTargetItem;
    public ImageView mTargetPreview;
    public FreeformContainerItem mTargetPreviewItem;
    public int mThresholdToMove;
    public int mThumbnailMargin;
    public final Rect mTmpBounds;
    public FreeformContainerFolderTrayView mTrayView;
    public int mVerticalPreviewMargin;
    public FreeformContainerViewController mViewController;
    public int mVisibleIconCount;
    public int mVisibleIconMaxCount;
    public int mWidth;
    public final WindowManager mWindowManager;

    /* renamed from: com.android.wm.shell.freeform.FreeformContainerFolderView$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public final void run() throws Resources.NotFoundException {
            FreeformContainerFolderView.this.setHorizontalScrollBarEnabled(true);
            Log.i("FreeformContainer", "[FolderView] mOpenFolderRunnable Run()");
            for (int itemCount = FreeformContainerFolderView.this.mAdapter.getItemCount() - 1; itemCount >= 0; itemCount--) {
                final RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = FreeformContainerFolderView.this.findViewHolderForAdapterPosition(itemCount);
                if (viewHolderFindViewHolderForAdapterPosition != null) {
                    if (itemCount == 0) {
                        FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                        freeformContainerFolderView.mBlockDataUpdate = true;
                        freeformContainerFolderView.mH.postDelayed(new FreeformContainerFolderView$1$$ExternalSyntheticLambda0(0, this, viewHolderFindViewHolderForAdapterPosition), 213L);
                    } else {
                        Animation animationLoadAnimation = AnimationUtils.loadAnimation(FreeformContainerFolderView.this.mContext, R.anim.freeform_container_icon_appearing_in_folder);
                        animationLoadAnimation.setAnimationListener(new Animation.AnimationListener(this) { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.1.1
                            @Override // android.view.animation.Animation.AnimationListener
                            public final void onAnimationStart(Animation animation) {
                                viewHolderFindViewHolderForAdapterPosition.itemView.setVisibility(0);
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public final void onAnimationEnd(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public final void onAnimationRepeat(Animation animation) {
                            }
                        });
                        viewHolderFindViewHolderForAdapterPosition.itemView.startAnimation(animationLoadAnimation);
                    }
                }
            }
            FreeformContainerFolderView.this.mIsExpandAnimating = false;
        }
    }

    public class FolderItemDecoration extends RecyclerView.ItemDecoration {
        public final Rect mItemMargin;
        public int mItemSpace;

        public /* synthetic */ FolderItemDecoration(FreeformContainerFolderView freeformContainerFolderView, int i) {
            this();
        }

        public final void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
            int itemCount = getItemCount() - 1;
            rect.top = this.mItemMargin.top;
            if (recyclerView.getAdapter().getItemViewType(childAdapterPosition) == 0) {
                rect.top -= FreeformContainerFolderView.this.mContext.getResources().getDimensionPixelSize(R.dimen.freeform_container_multi_instance_item_top_margin_diff);
            }
            if (!CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW) {
                rect.bottom = this.mItemMargin.bottom;
            }
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

    public class FolderViewAdapter extends RecyclerView.Adapter {
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

        public final int getItemViewType(int i) {
            FreeformContainerItem freeformContainerItem = (FreeformContainerItem) FreeformContainerFolderView.this.mViewController.mItemController.mItemList.get(i);
            freeformContainerItem.getClass();
            return !(freeformContainerItem instanceof MultiInstanceItem) ? 1 : 0;
        }

        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            if (i == 1) {
                FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                LayoutInflater layoutInflater = freeformContainerFolderView.mLayoutInflater;
                Objects.requireNonNull(layoutInflater);
                return freeformContainerFolderView.new SingleInstanceItemViewHolder(layoutInflater.inflate(R.layout.freeform_container_item, (ViewGroup) null));
            }
            FreeformContainerFolderView freeformContainerFolderView2 = FreeformContainerFolderView.this;
            LayoutInflater layoutInflater2 = freeformContainerFolderView2.mLayoutInflater;
            Objects.requireNonNull(layoutInflater2);
            return freeformContainerFolderView2.new MultiInstanceItemViewHolder(layoutInflater2.inflate(R.layout.freeform_container_view_group_item, (ViewGroup) null));
        }

        public final void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
            int i;
            int i2;
            int i3;
            char c;
            float f;
            final int i4 = 2;
            final int i5 = 0;
            final int i6 = 1;
            super.onViewAttachedToWindow(viewHolder);
            int adapterPosition = viewHolder.getAdapterPosition();
            char c2 = 65535;
            if (adapterPosition == -1) {
                return;
            }
            FolderViewItemViewHolder folderViewItemViewHolder = (FolderViewItemViewHolder) viewHolder;
            FreeformContainerItem freeformContainerItem = (FreeformContainerItem) FreeformContainerFolderView.this.mViewController.mItemController.mItemList.get(adapterPosition);
            folderViewItemViewHolder.mItem = freeformContainerItem;
            if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW && (viewHolder instanceof MultiInstanceItemViewHolder)) {
                MultiInstanceItemViewHolder multiInstanceItemViewHolder = (MultiInstanceItemViewHolder) viewHolder;
                for (int itemCount = freeformContainerItem.getItemCount() - 1; itemCount >= 0; itemCount--) {
                    LayoutInflater layoutInflater = FreeformContainerFolderView.this.mLayoutInflater;
                    Objects.requireNonNull(layoutInflater);
                    ImageView imageView = (ImageView) layoutInflater.inflate(R.layout.freeform_container_item, (ViewGroup) null).findViewById(R.id.freeform_container_item_image);
                    imageView.setImageDrawable(multiInstanceItemViewHolder.mItem.mShowingIcon.getConstantState().newDrawable());
                    ((ViewGroup) imageView.getParent()).removeView(imageView);
                    multiInstanceItemViewHolder.mMultiInstancePointerGroupView.addView(imageView);
                }
                final FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                View view = viewHolder.itemView;
                freeformContainerFolderView.getClass();
                int itemCount2 = freeformContainerItem.getItemCount();
                int i7 = itemCount2 - 1;
                int i8 = 0;
                while (i7 >= 0) {
                    final View childAt = ((ViewGroup) view).getChildAt(i7);
                    if (itemCount2 <= i6) {
                        i2 = i5;
                        i3 = i6;
                        c = c2;
                        f = 1.0f;
                    } else {
                        int i9 = itemCount2 - 2;
                        int iMax = Math.max(itemCount2 - 3, i5);
                        if (i9 < i7 || i7 < iMax) {
                            i2 = i5;
                            i3 = i6;
                            c = c2;
                            f = 1.0f;
                        } else {
                            i8 += i6;
                            childAt.setVisibility(i5);
                            float y = childAt.getY();
                            c = c2;
                            float y2 = childAt.getY() + (freeformContainerFolderView.mPointerSettleDownGap * i8);
                            ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView$$ExternalSyntheticLambda0
                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    switch (i5) {
                                        case 0:
                                            FreeformContainerFolderView freeformContainerFolderView2 = freeformContainerFolderView;
                                            View view2 = childAt;
                                            if (!freeformContainerFolderView2.mIsAppIconMoving) {
                                                view2.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                                                break;
                                            }
                                            break;
                                        case 1:
                                            FreeformContainerFolderView freeformContainerFolderView3 = freeformContainerFolderView;
                                            View view3 = childAt;
                                            if (!freeformContainerFolderView3.mIsAppIconMoving) {
                                                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                                view3.setScaleX(fFloatValue);
                                                view3.setScaleY(fFloatValue);
                                                break;
                                            }
                                            break;
                                        default:
                                            FreeformContainerFolderView freeformContainerFolderView4 = freeformContainerFolderView;
                                            View view4 = childAt;
                                            if (!freeformContainerFolderView4.mIsAppIconMoving) {
                                                view4.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                                                break;
                                            }
                                            break;
                                    }
                                }
                            };
                            i2 = i5;
                            ValueAnimator.AnimatorUpdateListener animatorUpdateListener2 = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView$$ExternalSyntheticLambda0
                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    switch (i6) {
                                        case 0:
                                            FreeformContainerFolderView freeformContainerFolderView2 = freeformContainerFolderView;
                                            View view2 = childAt;
                                            if (!freeformContainerFolderView2.mIsAppIconMoving) {
                                                view2.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                                                break;
                                            }
                                            break;
                                        case 1:
                                            FreeformContainerFolderView freeformContainerFolderView3 = freeformContainerFolderView;
                                            View view3 = childAt;
                                            if (!freeformContainerFolderView3.mIsAppIconMoving) {
                                                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                                view3.setScaleX(fFloatValue);
                                                view3.setScaleY(fFloatValue);
                                                break;
                                            }
                                            break;
                                        default:
                                            FreeformContainerFolderView freeformContainerFolderView4 = freeformContainerFolderView;
                                            View view4 = childAt;
                                            if (!freeformContainerFolderView4.mIsAppIconMoving) {
                                                view4.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                                                break;
                                            }
                                            break;
                                    }
                                }
                            };
                            i3 = i6;
                            ValueAnimator.AnimatorUpdateListener animatorUpdateListener3 = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView$$ExternalSyntheticLambda0
                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    switch (i4) {
                                        case 0:
                                            FreeformContainerFolderView freeformContainerFolderView2 = freeformContainerFolderView;
                                            View view2 = childAt;
                                            if (!freeformContainerFolderView2.mIsAppIconMoving) {
                                                view2.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                                                break;
                                            }
                                            break;
                                        case 1:
                                            FreeformContainerFolderView freeformContainerFolderView3 = freeformContainerFolderView;
                                            View view3 = childAt;
                                            if (!freeformContainerFolderView3.mIsAppIconMoving) {
                                                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                                view3.setScaleX(fFloatValue);
                                                view3.setScaleY(fFloatValue);
                                                break;
                                            }
                                            break;
                                        default:
                                            FreeformContainerFolderView freeformContainerFolderView4 = freeformContainerFolderView;
                                            View view4 = childAt;
                                            if (!freeformContainerFolderView4.mIsAppIconMoving) {
                                                view4.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                                                break;
                                            }
                                            break;
                                    }
                                }
                            };
                            int i10 = (itemCount2 - i7) - 1;
                            float f2 = FreeformContainerFolderView.TAIL_ICON_ALPHA_ARRAY[i10];
                            float f3 = FreeformContainerFolderView.TAIL_ICON_SCALE_ARRAY[i10];
                            float[] fArr = new float[2];
                            fArr[i2] = 1.0f;
                            fArr[i3] = f2;
                            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(fArr);
                            valueAnimatorOfFloat.addUpdateListener(animatorUpdateListener);
                            float[] fArr2 = new float[2];
                            fArr2[i2] = 1.0f;
                            fArr2[i3] = f3;
                            ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(fArr2);
                            valueAnimatorOfFloat2.addUpdateListener(animatorUpdateListener2);
                            float[] fArr3 = new float[2];
                            fArr3[i2] = y;
                            fArr3[i3] = y2;
                            ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(fArr3);
                            valueAnimatorOfFloat3.addUpdateListener(animatorUpdateListener3);
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(valueAnimatorOfFloat);
                            arrayList.add(valueAnimatorOfFloat2);
                            arrayList.add(valueAnimatorOfFloat3);
                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.setDuration(200L);
                            animatorSet.playTogether(arrayList);
                            animatorSet.start();
                            i7--;
                            c2 = c;
                            i5 = i2;
                            i6 = i3;
                        }
                    }
                    childAt.setAlpha(f);
                    childAt.setScaleX(f);
                    childAt.setScaleY(f);
                    i7--;
                    c2 = c;
                    i5 = i2;
                    i6 = i3;
                }
                i = i5;
            } else {
                i = 0;
                if (viewHolder instanceof SingleInstanceItemViewHolder) {
                    SingleInstanceItemViewHolder singleInstanceItemViewHolder = (SingleInstanceItemViewHolder) viewHolder;
                    ImageView imageView2 = singleInstanceItemViewHolder.mIconView;
                    Objects.requireNonNull(imageView2);
                    imageView2.setImageDrawable(singleInstanceItemViewHolder.mItem.mShowingIcon);
                    if (!CoreRune.MW_FREEFORM_MINIMIZED_PREVIEW) {
                        viewHolder.itemView.setTooltip(singleInstanceItemViewHolder.mItem.mDescription);
                    }
                }
            }
            viewHolder.itemView.setOnTouchListener(folderViewItemViewHolder);
            viewHolder.itemView.setContentDescription(folderViewItemViewHolder.mItem.mDescription);
            viewHolder.itemView.setVisibility(FreeformContainerFolderView.this.mIsExpandAnimating ? 4 : i);
        }

        public final void onViewDetachedFromWindow(RecyclerView.ViewHolder viewHolder) {
            super.onViewDetachedFromWindow(viewHolder);
            if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW && (viewHolder instanceof MultiInstanceItemViewHolder)) {
                ((MultiInstanceItemViewHolder) viewHolder).mMultiInstancePointerGroupView.removeAllViews();
            } else if (viewHolder instanceof SingleInstanceItemViewHolder) {
                ImageView imageView = ((SingleInstanceItemViewHolder) viewHolder).mIconView;
                Objects.requireNonNull(imageView);
                imageView.setImageDrawable(null);
            }
            viewHolder.itemView.setOnTouchListener(null);
        }

        private FolderViewAdapter() {
        }

        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        }
    }

    public abstract class FolderViewItemViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {
        public FreeformContainerItem mItem;

        public FolderViewItemViewHolder(FreeformContainerFolderView freeformContainerFolderView, View view) {
            super(view);
        }
    }

    public class MultiInstanceItemViewHolder extends FolderViewItemViewHolder {
        public final FreeformContainerFolderView$MultiInstanceItemViewHolder$$ExternalSyntheticLambda1 mButtonHoverListener;
        public final FreeformContainerFolderView$1$$ExternalSyntheticLambda1 mDismissMultiInstancePreviewByHoverExit;
        public final MultiInstanceEventHandler mHandler;
        public final ViewGroup mMultiInstancePointerGroupView;

        public final class MultiInstanceEventHandler extends Handler {
            public MultiInstanceEventHandler() {
            }

            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                MultiInstanceItemViewHolder multiInstanceItemViewHolder = MultiInstanceItemViewHolder.this;
                if (i == 1) {
                    if (hasMessages(2)) {
                        removeMessages(1);
                        return;
                    } else {
                        MultiInstanceItemViewHolder.m3266$$Nest$mcreateMultiInstancePreview(multiInstanceItemViewHolder, true);
                        removeMessages(1);
                        return;
                    }
                }
                if (i == 2) {
                    if (hasMessages(1)) {
                        removeMessages(1);
                    }
                    MultiInstanceItemViewHolder.m3266$$Nest$mcreateMultiInstancePreview(multiInstanceItemViewHolder, false);
                    removeMessages(2);
                }
            }
        }

        /* renamed from: -$$Nest$mcreateMultiInstancePreview, reason: not valid java name */
        public static void m3266$$Nest$mcreateMultiInstancePreview(final MultiInstanceItemViewHolder multiInstanceItemViewHolder, boolean z) {
            int height;
            FreeformContainerItem freeformContainerItem;
            MultiInstanceItem multiInstanceItemAsMultiInstanceItem = multiInstanceItemViewHolder.mItem.asMultiInstanceItem();
            if (multiInstanceItemAsMultiInstanceItem == null || multiInstanceItemAsMultiInstanceItem.mChildItemList.size() <= 1) {
                Log.w("FreeformContainer", "createMultiInstancePreview: invalid item, " + multiInstanceItemAsMultiInstanceItem);
                return;
            }
            int itemCount = multiInstanceItemViewHolder.mItem.getItemCount() - 1;
            View childAt = multiInstanceItemViewHolder.mMultiInstancePointerGroupView.getChildAt(itemCount);
            if (childAt == null) {
                Log.w("FreeformContainer", "createMultiInstancePreview: cannot find child view, " + multiInstanceItemAsMultiInstanceItem);
                return;
            }
            ArrayList arrayList = new ArrayList();
            while (itemCount >= 0) {
                MultiInstanceItem multiInstanceItem = (MultiInstanceItem) multiInstanceItemViewHolder.mItem.getItemList().get(itemCount);
                Bitmap bitmapCreateBitmap = multiInstanceItem.mSnapshotBitmap;
                if (bitmapCreateBitmap == null) {
                    Log.w("FreeformContainer", "Use dummy bitmap, " + multiInstanceItem);
                    FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                    bitmapCreateBitmap = Bitmap.createBitmap(freeformContainerFolderView.mPreviewWidth, freeformContainerFolderView.mPreviewHeight, Bitmap.Config.ARGB_8888);
                    new Canvas(bitmapCreateBitmap).drawColor(-1);
                }
                FreeformContainerFolderView freeformContainerFolderView2 = FreeformContainerFolderView.this;
                arrayList.add(Bitmap.createScaledBitmap(Bitmap.createBitmap(bitmapCreateBitmap, 0, 0, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight()), freeformContainerFolderView2.mPreviewWidth, freeformContainerFolderView2.mPreviewHeight, true));
                itemCount--;
            }
            Rect rect = FreeformContainerFolderView.this.mItemDecoration.mItemMargin;
            FreeformContainerItem freeformContainerItem2 = multiInstanceItemViewHolder.mItem;
            Log.d("FreeformContainer", "createMultiInstancePreviewPopup: " + freeformContainerItem2);
            FreeformContainerFolderView.this.dismissMultiInstancePreviewPopup("create_new_popup");
            LayoutInflater layoutInflater = FreeformContainerFolderView.this.mLayoutInflater;
            Objects.requireNonNull(layoutInflater);
            View viewInflate = layoutInflater.inflate(R.layout.preview_list_container, (ViewGroup) null);
            FreeformContainerFolderView.this.mPopupWindow = new MultiInstancePreviewPopupWindow(viewInflate, -2, -2, true, z, freeformContainerItem2);
            FreeformContainerFolderView.this.mPopupWindow.setTouchable(true);
            MultiInstancePreviewPopupWindow multiInstancePreviewPopupWindow = FreeformContainerFolderView.this.mPopupWindow;
            if (!multiInstancePreviewPopupWindow.mOpenedByHoverAction && (freeformContainerItem = multiInstancePreviewPopupWindow.mItem) != null && freeformContainerItem == freeformContainerItem2) {
                multiInstancePreviewPopupWindow.setOutsideTouchable(true);
            }
            FreeformContainerFolderView.this.mPopupWindow.setFocusable(false);
            FreeformContainerFolderView.this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
            FreeformContainerFolderView.this.mPreviewRecycler = viewInflate.findViewById(R.id.previewRecycler);
            FreeformContainerFolderView.this.mMultiInstancePreviewAdapter = FreeformContainerFolderView.this.new MultiInstancePreviewAdapter(arrayList, freeformContainerItem2);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FreeformContainerFolderView.this.mContext);
            linearLayoutManager.setOrientation(0);
            FreeformContainerFolderView.this.mPreviewRecycler.setLayoutManager(linearLayoutManager);
            FreeformContainerFolderView freeformContainerFolderView3 = FreeformContainerFolderView.this;
            freeformContainerFolderView3.mPreviewRecycler.setAdapter(freeformContainerFolderView3.mMultiInstancePreviewAdapter);
            FreeformContainerFolderView.this.mPreviewRecycler.setScrollBarStyle(UcmAgentService.ERROR_SCP_UNKNOWN);
            FreeformContainerFolderView.this.mMultiInstancePreviewAdapter.notifyDataSetChanged();
            FreeformContainerFolderView.this.mPreviewRecycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.MultiInstanceItemViewHolder.1
                public final boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                    return FreeformContainerFolderView.this.mViewController.isDismissButtonShowing();
                }

                public final void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                    if (FreeformContainerFolderView.this.mViewController.isDismissButtonShowing()) {
                        float rawX = motionEvent.getRawX();
                        float rawY = motionEvent.getRawY();
                        int action = motionEvent.getAction();
                        if (action != 1) {
                            if (action == 2) {
                                ImageView imageView = FreeformContainerFolderView.this.mDraggingPreview;
                                imageView.setX((rawX - FreeformContainerFolderView.this.mLastPositionX) + imageView.getX());
                                ImageView imageView2 = FreeformContainerFolderView.this.mDraggingPreview;
                                imageView2.setY((rawY - FreeformContainerFolderView.this.mLastPositionY) + imageView2.getY());
                                FreeformContainerFolderView freeformContainerFolderView4 = FreeformContainerFolderView.this;
                                freeformContainerFolderView4.mLastPositionX = rawX;
                                freeformContainerFolderView4.mLastPositionY = rawY;
                                FreeformContainerFolderView.getDraggingViewBounds(freeformContainerFolderView4.mTmpBounds, freeformContainerFolderView4.mDraggingPreview);
                                FreeformContainerFolderView freeformContainerFolderView5 = FreeformContainerFolderView.this;
                                FreeformContainerViewController freeformContainerViewController = freeformContainerFolderView5.mViewController;
                                Rect rect2 = freeformContainerFolderView5.mTmpBounds;
                                FreeformContainerDismissButtonView freeformContainerDismissButtonView = freeformContainerViewController.mDismissButtonView;
                                if (freeformContainerDismissButtonView != null) {
                                    freeformContainerDismissButtonView.mDismissViewManager.mView.updateView(rect2);
                                }
                                FreeformContainerFolderView.this.mHasPreviewMoved = true;
                                return;
                            }
                            if (action != 3) {
                                return;
                            }
                        }
                        FreeformContainerFolderView freeformContainerFolderView6 = FreeformContainerFolderView.this;
                        FreeformContainerFolderView.getDraggingViewBounds(freeformContainerFolderView6.mTmpBounds, freeformContainerFolderView6.mDraggingPreview);
                        FreeformContainerFolderView freeformContainerFolderView7 = FreeformContainerFolderView.this;
                        freeformContainerFolderView7.mViewController.hideDismissButtonAndDismissIcon(freeformContainerFolderView7.mTargetPreviewItem, freeformContainerFolderView7.mDraggingPreview, freeformContainerFolderView7.mTmpBounds);
                        MultiInstanceItemViewHolder multiInstanceItemViewHolder2 = MultiInstanceItemViewHolder.this;
                        if (!FreeformContainerFolderView.this.mHasPreviewMoved) {
                            MultiInstanceItemViewHolder.m3267$$Nest$mrestorePreview(multiInstanceItemViewHolder2);
                        }
                        if (FreeformContainerFolderView.this.mViewController.isEnterDismissButton()) {
                            return;
                        }
                        final MultiInstanceItemViewHolder multiInstanceItemViewHolder3 = MultiInstanceItemViewHolder.this;
                        float translationX = FreeformContainerFolderView.this.mDraggingPreview.getTranslationX();
                        float translationY = FreeformContainerFolderView.this.mDraggingPreview.getTranslationY();
                        FreeformContainerFolderView freeformContainerFolderView8 = FreeformContainerFolderView.this;
                        int[] iArr = freeformContainerFolderView8.mDraggingPreviewReturnLocation;
                        float f = iArr[0];
                        float f2 = iArr[1];
                        freeformContainerFolderView8.mAnimatingSpringPreviewX = Math.abs(translationX - f) > 2.0f;
                        FreeformContainerFolderView.this.mAnimatingSpringPreviewY = Math.abs(translationY - f2) > 2.0f;
                        if (!FreeformContainerFolderView.this.isSpringPreviewAnimating()) {
                            StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("[FolderView] animateToReturnDraggingAppIconView: spring failed, from=[", translationX, ",", translationY, "], to=[");
                            sbM.append(f);
                            sbM.append(",");
                            sbM.append(f2);
                            sbM.append("], call finishDraggingAppIcon()");
                            Log.i("FreeformContainer", sbM.toString());
                            FreeformContainerFolderView.this.finishDraggingAppPreview();
                            return;
                        }
                        FreeformContainerFolderView freeformContainerFolderView9 = FreeformContainerFolderView.this;
                        freeformContainerFolderView9.mDraggingPreviewSpringX = freeformContainerFolderView9.mSpringSystem.createSpring();
                        FreeformContainerFolderView.this.mDraggingPreviewSpringX.mSpringConfig = new SpringConfig(180.0d, 18.0d);
                        Spring spring = FreeformContainerFolderView.this.mDraggingPreviewSpringX;
                        spring.mRestSpeedThreshold = 0.30000001192092896d;
                        spring.mDisplacementFromRestThreshold = 0.30000001192092896d;
                        spring.addListener(new SimpleSpringListener() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.MultiInstanceItemViewHolder.2
                            @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
                            public final void onSpringAtRest(Spring spring2) {
                                MultiInstanceItemViewHolder multiInstanceItemViewHolder4 = MultiInstanceItemViewHolder.this;
                                FreeformContainerFolderView freeformContainerFolderView10 = FreeformContainerFolderView.this;
                                freeformContainerFolderView10.mAnimatingSpringPreviewX = false;
                                if (freeformContainerFolderView10.isSpringPreviewAnimating()) {
                                    return;
                                }
                                Log.i("FreeformContainer", "[FolderView] onSpringAtRest of springX, releaseDraggingState");
                                MultiInstanceItemViewHolder.m3267$$Nest$mrestorePreview(multiInstanceItemViewHolder4);
                                FreeformContainerFolderView.this.finishDraggingAppPreview();
                            }

                            @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
                            public final void onSpringUpdate(Spring spring2) {
                                FreeformContainerFolderView.this.mDraggingPreview.setX((float) spring2.mCurrentState.position);
                            }
                        });
                        FreeformContainerFolderView.this.mDraggingPreviewSpringX.setCurrentValue(translationX);
                        FreeformContainerFolderView.this.mDraggingPreviewSpringX.setEndValue(f);
                        FreeformContainerFolderView freeformContainerFolderView10 = FreeformContainerFolderView.this;
                        freeformContainerFolderView10.mDraggingPreviewSpringY = freeformContainerFolderView10.mSpringSystem.createSpring();
                        FreeformContainerFolderView.this.mDraggingPreviewSpringY.mSpringConfig = new SpringConfig(180.0d, 18.0d);
                        Spring spring2 = FreeformContainerFolderView.this.mDraggingPreviewSpringY;
                        spring2.mRestSpeedThreshold = 0.30000001192092896d;
                        spring2.mDisplacementFromRestThreshold = 0.30000001192092896d;
                        spring2.addListener(new SimpleSpringListener() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.MultiInstanceItemViewHolder.3
                            @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
                            public final void onSpringAtRest(Spring spring3) {
                                MultiInstanceItemViewHolder multiInstanceItemViewHolder4 = MultiInstanceItemViewHolder.this;
                                FreeformContainerFolderView freeformContainerFolderView11 = FreeformContainerFolderView.this;
                                freeformContainerFolderView11.mAnimatingSpringPreviewY = false;
                                if (freeformContainerFolderView11.isSpringPreviewAnimating()) {
                                    return;
                                }
                                Log.i("FreeformContainer", "[FolderView] onSpringAtRest of springY, releaseDraggingState");
                                MultiInstanceItemViewHolder.m3267$$Nest$mrestorePreview(multiInstanceItemViewHolder4);
                                FreeformContainerFolderView.this.finishDraggingAppPreview();
                            }

                            @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
                            public final void onSpringUpdate(Spring spring3) {
                                FreeformContainerFolderView.this.mDraggingPreview.setY((float) spring3.mCurrentState.position);
                            }
                        });
                        FreeformContainerFolderView.this.mDraggingPreviewSpringY.setCurrentValue(translationY);
                        FreeformContainerFolderView.this.mDraggingPreviewSpringY.setEndValue(f2);
                    }
                }

                public final void onRequestDisallowInterceptTouchEvent(boolean z2) {
                }
            });
            int itemCount2 = freeformContainerItem2.getItemCount();
            int[] locationOnScreen = childAt.getLocationOnScreen();
            int width = (childAt.getWidth() / 2) + locationOnScreen[0];
            int i = locationOnScreen[1];
            FreeformContainerFolderView freeformContainerFolderView4 = FreeformContainerFolderView.this;
            int i2 = freeformContainerFolderView4.mPreviewHeight;
            int i3 = rect.top + i2;
            int i4 = freeformContainerFolderView4.mVerticalPreviewMargin * 2;
            Rect rect2 = freeformContainerFolderView4.mStableInsets;
            if ((i - (i4 + i3)) - rect2.top > 0) {
                height = i - ((i3 + freeformContainerFolderView4.mThumbnailMargin) + i4);
            } else {
                int i5 = rect.bottom + i2 + i;
                int i6 = freeformContainerFolderView4.mDisplaySize.y - rect2.bottom;
                height = i5 > i6 ? i6 - i2 : FreeformContainerFolderView.this.mThumbnailMargin + childAt.getHeight() + i + rect.bottom;
            }
            FreeformContainerFolderView freeformContainerFolderView5 = FreeformContainerFolderView.this;
            int i7 = itemCount2 * freeformContainerFolderView5.mPreviewWidth;
            int i8 = i7 / 2;
            int i9 = width - i8;
            Rect rect3 = freeformContainerFolderView5.mStableInsets;
            int i10 = rect3.left;
            if (i9 - i10 < 0) {
                i9 = i10;
            } else {
                int i11 = width + i8;
                int i12 = rect3.right;
                int i13 = i11 + i12;
                int i14 = freeformContainerFolderView5.mDisplaySize.x;
                if (i13 > i14 - i12) {
                    i9 = (i14 - i7) - i12;
                }
            }
            FreeformContainerFolderView.this.mViewController.mContainerView.post(new FreeformContainerFolderView$1$$ExternalSyntheticLambda0(1, multiInstanceItemViewHolder, new Point(i9, height)));
            FreeformContainerFolderView.this.mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView$MultiInstanceItemViewHolder$$ExternalSyntheticLambda3
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() {
                    RecyclerView recyclerView = FreeformContainerFolderView.this.mPreviewRecycler;
                    if (recyclerView != null) {
                        recyclerView.setAdapter((RecyclerView.Adapter) null);
                    }
                }
            });
            if (CoreRune.MW_FREEFORM_MINIMIZE_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("2204");
            }
        }

        /* renamed from: -$$Nest$mrestorePreview, reason: not valid java name */
        public static void m3267$$Nest$mrestorePreview(MultiInstanceItemViewHolder multiInstanceItemViewHolder) {
            int size = FreeformContainerFolderView.this.mMultiInstancePreviewAdapter.mSnapshotBitmapList.size();
            for (int i = 0; i < size; i++) {
                MultiInstancePreviewAdapter.PreviewItemViewHolder previewItemViewHolder = (MultiInstancePreviewAdapter.PreviewItemViewHolder) FreeformContainerFolderView.this.mPreviewRecycler.findViewHolderForAdapterPosition(i);
                if (previewItemViewHolder != null && previewItemViewHolder.mItem.equals(FreeformContainerFolderView.this.mTargetPreviewItem)) {
                    previewItemViewHolder.mPreview.setImageDrawable(FreeformContainerFolderView.getCloneDrawableFromImageView(FreeformContainerFolderView.this.mDraggingPreview));
                }
            }
        }

        public MultiInstanceItemViewHolder(View view) {
            super(FreeformContainerFolderView.this, view);
            this.mDismissMultiInstancePreviewByHoverExit = new FreeformContainerFolderView$1$$ExternalSyntheticLambda1(this, 1);
            FreeformContainerFolderView$MultiInstanceItemViewHolder$$ExternalSyntheticLambda1 freeformContainerFolderView$MultiInstanceItemViewHolder$$ExternalSyntheticLambda1 = new FreeformContainerFolderView$MultiInstanceItemViewHolder$$ExternalSyntheticLambda1(this, 0);
            this.mButtonHoverListener = freeformContainerFolderView$MultiInstanceItemViewHolder$$ExternalSyntheticLambda1;
            this.mHandler = new MultiInstanceEventHandler();
            ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.multi_instance_item_group_view);
            this.mMultiInstancePointerGroupView = viewGroup;
            viewGroup.setOnHoverListener(freeformContainerFolderView$MultiInstanceItemViewHolder$$ExternalSyntheticLambda1);
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            FreeformContainerItem freeformContainerItem;
            float rawX = motionEvent.getRawX();
            float rawY = motionEvent.getRawY();
            int action = motionEvent.getAction();
            if (action == 0) {
                FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                freeformContainerFolderView.mIsAppIconMoving = false;
                freeformContainerFolderView.mLastPositionX = rawX;
                freeformContainerFolderView.mLastPositionY = rawY;
                freeformContainerFolderView.mHasIconMoved = false;
                Log.i("FreeformContainer", "[FolderView] onTouch(" + MotionEvent.actionToString(action) + ")");
                return true;
            }
            if (action == 1) {
                Log.i("FreeformContainer", "[FolderView] onTouch(" + MotionEvent.actionToString(action) + ")");
                FreeformContainerItem freeformContainerItem2 = this.mItem;
                if (freeformContainerItem2 == null) {
                    Log.w("FreeformContainer", "[FolderView] cannot find item");
                    FreeformContainerFolderView.this.mViewController.updateContainerState(0, true, true);
                    return false;
                }
                if (!CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW || !(freeformContainerItem2 instanceof MultiInstanceItem)) {
                    FreeformContainerFolderView.this.mH.sendMessage(30, freeformContainerItem2);
                    FreeformContainerFolderView.this.mViewController.updateContainerState(0, true, true);
                    return false;
                }
                if (freeformContainerItem2.asMultiInstanceItem().mChildItemList.size() == 1) {
                    FreeformContainerFolderView.this.mH.sendMessage(30, this.mItem.getItemList().getFirst());
                    FreeformContainerFolderView.this.mViewController.updateContainerState(0, true, true);
                    return false;
                }
                FreeformContainerFolderView freeformContainerFolderView2 = FreeformContainerFolderView.this;
                MultiInstancePreviewPopupWindow multiInstancePreviewPopupWindow = freeformContainerFolderView2.mPopupWindow;
                if (multiInstancePreviewPopupWindow != null) {
                    FreeformContainerItem freeformContainerItem3 = this.mItem;
                    if (!multiInstancePreviewPopupWindow.mOpenedByHoverAction && (freeformContainerItem = multiInstancePreviewPopupWindow.mItem) != null && freeformContainerItem == freeformContainerItem3) {
                        freeformContainerFolderView2.dismissMultiInstancePreviewPopup("action_up");
                        return false;
                    }
                }
                this.mHandler.removeMessages(1);
                this.mHandler.sendMessage(this.mHandler.obtainMessage(2));
                return false;
            }
            if (action == 2) {
                int itemCount = FreeformContainerFolderView.this.mAdapter.getItemCount();
                FreeformContainerFolderView freeformContainerFolderView3 = FreeformContainerFolderView.this;
                float f = itemCount < freeformContainerFolderView3.mVisibleIconMaxCount ? rawX - freeformContainerFolderView3.mLastPositionX : 0.0f;
                float f2 = rawY - freeformContainerFolderView3.mLastPositionY;
                FreeformContainerFolderView.getDraggingViewBounds(freeformContainerFolderView3.mTmpBounds, freeformContainerFolderView3.mDraggingIconView);
                float fHypot = (float) Math.hypot(f, f2);
                FreeformContainerFolderView freeformContainerFolderView4 = FreeformContainerFolderView.this;
                if (fHypot >= freeformContainerFolderView4.mThresholdToMove && !freeformContainerFolderView4.mViewController.isDismissButtonShowing() && !FreeformContainerFolderView.this.isSpringAnimating()) {
                    FreeformContainerFolderView freeformContainerFolderView5 = FreeformContainerFolderView.this;
                    if (!freeformContainerFolderView5.mIsAppIconMoving) {
                        freeformContainerFolderView5.mIsAppIconMoving = true;
                    }
                    FreeformContainerViewController freeformContainerViewController = freeformContainerFolderView5.mViewController;
                    Rect rect = freeformContainerFolderView5.mTmpBounds;
                    freeformContainerViewController.createOrUpdateDismissButton();
                    freeformContainerViewController.mDismissButtonView.show(rect);
                    FreeformContainerFolderView.this.finishDraggingAppIcon();
                    FreeformContainerFolderView freeformContainerFolderView6 = FreeformContainerFolderView.this;
                    ViewGroup viewGroup = this.mMultiInstancePointerGroupView;
                    freeformContainerFolderView6.mTargetIconView = (ImageView) viewGroup.getChildAt(viewGroup.getChildCount() - 1);
                    FreeformContainerFolderView freeformContainerFolderView7 = FreeformContainerFolderView.this;
                    freeformContainerFolderView7.mTargetItem = this.mItem;
                    FreeformContainerFolderView.m3265$$Nest$mstartDraggingAppIcon(freeformContainerFolderView7);
                    for (int i = 0; i < this.mMultiInstancePointerGroupView.getChildCount(); i++) {
                        View childAt = this.mMultiInstancePointerGroupView.getChildAt(i);
                        if (childAt instanceof ImageView) {
                            ((ImageView) childAt).setImageDrawable(FreeformContainerFolderView.this.mEmptySlotIcon);
                        }
                    }
                    Log.i("FreeformContainer", "[FolderView] onTouch(" + MotionEvent.actionToString(action) + "): Ready to move");
                    return false;
                }
            }
            return true;
        }
    }

    public class MultiInstancePreviewAdapter extends RecyclerView.Adapter {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final FreeformContainerItem mItem;
        public final List mSnapshotBitmapList;

        public class PreviewItemViewHolder extends RecyclerView.ViewHolder {
            public FreeformContainerItem mItem;
            public final ImageView mPreview;

            public PreviewItemViewHolder(MultiInstancePreviewAdapter multiInstancePreviewAdapter, View view) {
                super(view);
                this.mPreview = (ImageView) view.findViewById(R.id.freeform_container_item_preview);
            }
        }

        public MultiInstancePreviewAdapter(List<Bitmap> list, FreeformContainerItem freeformContainerItem) {
            this.mSnapshotBitmapList = list;
            this.mItem = freeformContainerItem;
        }

        public final int getItemCount() {
            return this.mSnapshotBitmapList.size();
        }

        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            final PreviewItemViewHolder previewItemViewHolder = (PreviewItemViewHolder) viewHolder;
            previewItemViewHolder.mPreview.setImageBitmap((Bitmap) this.mSnapshotBitmapList.get(i));
            ((RecyclerView.ViewHolder) previewItemViewHolder).itemView.setOnClickListener(new FreeformContainerFolderView$$ExternalSyntheticLambda3(this, i));
            ((RecyclerView.ViewHolder) previewItemViewHolder).itemView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView$MultiInstancePreviewAdapter$$ExternalSyntheticLambda1
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    FreeformContainerFolderView.MultiInstancePreviewAdapter multiInstancePreviewAdapter = this.f$0;
                    FreeformContainerFolderView.MultiInstancePreviewAdapter.PreviewItemViewHolder previewItemViewHolder2 = previewItemViewHolder;
                    int i2 = FreeformContainerFolderView.MultiInstancePreviewAdapter.$r8$clinit;
                    float rawX = motionEvent.getRawX();
                    float rawY = motionEvent.getRawY();
                    int action = motionEvent.getAction();
                    if (action == 0) {
                        FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                        freeformContainerFolderView.mLastPositionX = rawX;
                        freeformContainerFolderView.mLastPositionY = rawY;
                        freeformContainerFolderView.mHasPreviewMoved = false;
                        Log.i("FreeformContainer", "[FolderView] onTouch(" + MotionEvent.actionToString(action) + ")");
                        return true;
                    }
                    if (action == 1) {
                        FreeformContainerItem freeformContainerItem = previewItemViewHolder2.mItem;
                        if (freeformContainerItem != null) {
                            FreeformContainerFolderView.this.mH.sendMessage(30, freeformContainerItem);
                            FreeformContainerFolderView.this.mIsCollapsePreview = true;
                        }
                        return false;
                    }
                    if (action == 2) {
                        int size = FreeformContainerFolderView.this.mMultiInstancePreviewAdapter.mSnapshotBitmapList.size();
                        FreeformContainerFolderView freeformContainerFolderView2 = FreeformContainerFolderView.this;
                        float f = size < freeformContainerFolderView2.mVisibleIconMaxCount ? rawX - freeformContainerFolderView2.mLastPositionX : 0.0f;
                        float f2 = rawY - freeformContainerFolderView2.mLastPositionY;
                        FreeformContainerFolderView.getDraggingViewBounds(freeformContainerFolderView2.mTmpBounds, freeformContainerFolderView2.mDraggingPreview);
                        float fHypot = (float) Math.hypot(f, f2);
                        FreeformContainerFolderView freeformContainerFolderView3 = FreeformContainerFolderView.this;
                        if (fHypot >= freeformContainerFolderView3.mThresholdToMove && !freeformContainerFolderView3.mViewController.isDismissButtonShowing() && !FreeformContainerFolderView.this.isSpringPreviewAnimating()) {
                            FreeformContainerFolderView freeformContainerFolderView4 = FreeformContainerFolderView.this;
                            FreeformContainerViewController freeformContainerViewController = freeformContainerFolderView4.mViewController;
                            Rect rect = freeformContainerFolderView4.mTmpBounds;
                            freeformContainerViewController.createOrUpdateDismissButton();
                            freeformContainerViewController.mDismissButtonView.show(rect);
                            FreeformContainerFolderView.this.finishDraggingAppPreview();
                            FreeformContainerFolderView freeformContainerFolderView5 = FreeformContainerFolderView.this;
                            ImageView imageView = previewItemViewHolder2.mPreview;
                            freeformContainerFolderView5.mTargetPreview = imageView;
                            freeformContainerFolderView5.mTargetPreviewItem = previewItemViewHolder2.mItem;
                            if (imageView == null) {
                                Log.e("FreeformContainer", "[FolderView] mTargetPreview is null");
                            } else {
                                imageView.getLocationOnScreen(freeformContainerFolderView5.mDraggingPreviewReturnLocation);
                                freeformContainerFolderView5.mDraggingPreview.setX(freeformContainerFolderView5.mDraggingPreviewReturnLocation[0]);
                                freeformContainerFolderView5.mDraggingPreview.setY(freeformContainerFolderView5.mDraggingPreviewReturnLocation[1]);
                                Drawable cloneDrawableFromImageView = FreeformContainerFolderView.getCloneDrawableFromImageView(freeformContainerFolderView5.mTargetPreview);
                                if (cloneDrawableFromImageView != null) {
                                    freeformContainerFolderView5.mDraggingPreview.setImageDrawable(cloneDrawableFromImageView);
                                    freeformContainerFolderView5.mDraggingPreview.setVisibility(0);
                                }
                            }
                            previewItemViewHolder2.mPreview.setImageDrawable(null);
                            Log.i("FreeformContainer", "[FolderView] onTouch(" + MotionEvent.actionToString(action) + "): Ready to move");
                            return false;
                        }
                    }
                    return true;
                }
            });
            ((RecyclerView.ViewHolder) previewItemViewHolder).itemView.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.MultiInstancePreviewAdapter.1
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                }

                @Override // android.view.View.AccessibilityDelegate
                public final boolean performAccessibilityAction(View view, int i2, Bundle bundle) {
                    FreeformContainerItem freeformContainerItem;
                    if (i2 == 16 && (freeformContainerItem = previewItemViewHolder.mItem) != null) {
                        FreeformContainerFolderView.this.mH.sendMessage(30, freeformContainerItem);
                        FreeformContainerFolderView.this.mIsCollapsePreview = true;
                    }
                    return super.performAccessibilityAction(view, i2, bundle);
                }
            });
        }

        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new PreviewItemViewHolder(this, KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, R.layout.preview_container_list_item, viewGroup, false));
        }

        public final void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
            PreviewItemViewHolder previewItemViewHolder = (PreviewItemViewHolder) viewHolder;
            super.onViewAttachedToWindow(previewItemViewHolder);
            int itemCount = (this.mItem.getItemCount() - previewItemViewHolder.getAdapterPosition()) - 1;
            if (itemCount >= 0) {
                previewItemViewHolder.mItem = (FreeformContainerItem) this.mItem.getItemList().get(itemCount);
                ((RecyclerView.ViewHolder) previewItemViewHolder).itemView.setContentDescription(this.mItem.mDescription);
            } else {
                Log.e("FreeformContainer", "[FolderView] onViewAttachedToWindow: invalid, " + this.mItem);
            }
        }
    }

    public class MultiInstancePreviewPopupWindow extends PopupWindow {
        public final FreeformContainerItem mItem;
        public final boolean mOpenedByHoverAction;

        public MultiInstancePreviewPopupWindow(View view, int i, int i2, boolean z, boolean z2, FreeformContainerItem freeformContainerItem) {
            super(view, i, i2, z);
            this.mItem = freeformContainerItem;
            this.mOpenedByHoverAction = z2;
            Log.d("FreeformContainer", "[FolderView] " + this + " is constructed");
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("MultiInstancePreview{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" item=");
            sb.append(this.mItem);
            sb.append(", hover=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.mOpenedByHoverAction, "}");
        }
    }

    public final class SettingsObserver extends ContentObserver {
        public final Uri mEasyModeSwitchUri;

        public SettingsObserver() {
            super(null);
            Uri uriFor = Settings.System.getUriFor(SettingsHelper.INDEX_EASY_MODE_SWITCH);
            this.mEasyModeSwitchUri = uriFor;
            FreeformContainerFolderView.this.mContext.getContentResolver().registerContentObserver(uriFor, false, this, -1);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (uri != null && this.mEasyModeSwitchUri.equals(uri)) {
                FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                float[] fArr = FreeformContainerFolderView.TAIL_ICON_ALPHA_ARRAY;
                freeformContainerFolderView.calculateVisibleIconMaxCount();
            }
        }
    }

    public class SingleInstanceItemViewHolder extends FolderViewItemViewHolder {
        public final FreeformContainerFolderView$MultiInstanceItemViewHolder$$ExternalSyntheticLambda1 mButtonHoverListener;
        public final AnonymousClass2 mDismissPreview;
        public final ImageView mIconView;
        public boolean mIsVisiblePreview;
        public final AnonymousClass1 mShowPreview;

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.freeform.FreeformContainerFolderView$SingleInstanceItemViewHolder$1] */
        /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.freeform.FreeformContainerFolderView$SingleInstanceItemViewHolder$2] */
        public SingleInstanceItemViewHolder(View view) {
            super(FreeformContainerFolderView.this, view);
            this.mIsVisiblePreview = false;
            this.mShowPreview = new Runnable() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.SingleInstanceItemViewHolder.1
                /* JADX WARN: Removed duplicated region for block: B:23:0x0076 A[PHI: r3 r4
                  0x0076: PHI (r3v6 float) = (r3v2 float), (r3v10 float), (r3v2 float) binds: [B:18:0x0063, B:16:0x005a, B:11:0x0048] A[DONT_GENERATE, DONT_INLINE]
                  0x0076: PHI (r4v6 float) = (r4v3 float), (r4v11 float), (r4v3 float) binds: [B:18:0x0063, B:16:0x005a, B:11:0x0048] A[DONT_GENERATE, DONT_INLINE]] */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() {
                    Bitmap taskSnapshot;
                    float f;
                    float f2;
                    int height;
                    SingleInstanceItemViewHolder singleInstanceItemViewHolder = SingleInstanceItemViewHolder.this;
                    FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                    if (freeformContainerFolderView.mFreeformThumbnailView == null || (taskSnapshot = freeformContainerFolderView.getTaskSnapshot(singleInstanceItemViewHolder.mItem.getTaskId())) == null) {
                        return;
                    }
                    SingleInstanceItemViewHolder singleInstanceItemViewHolder2 = SingleInstanceItemViewHolder.this;
                    singleInstanceItemViewHolder2.mIsVisiblePreview = true;
                    FreeformThumbnailView freeformThumbnailView = FreeformContainerFolderView.this.mFreeformThumbnailView;
                    freeformThumbnailView.mBitmap = taskSnapshot;
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(taskSnapshot, 0, 0, taskSnapshot.getWidth(), freeformThumbnailView.mBitmap.getHeight());
                    float width = bitmapCreateBitmap.getWidth();
                    float height2 = bitmapCreateBitmap.getHeight();
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
                                int i = (int) width;
                                freeformThumbnailView.mWidth = i;
                                int i2 = (int) height2;
                                freeformThumbnailView.mHeight = i2;
                                freeformThumbnailView.mBitmap = Bitmap.createScaledBitmap(bitmapCreateBitmap, i, i2, true);
                            }
                        } else {
                            int i3 = (int) width;
                            freeformThumbnailView.mWidth = i3;
                            int i22 = (int) height2;
                            freeformThumbnailView.mHeight = i22;
                            freeformThumbnailView.mBitmap = Bitmap.createScaledBitmap(bitmapCreateBitmap, i3, i22, true);
                        }
                    } else if (height2 > freeformThumbnailView.mMaxSize) {
                        Log.d("FreeformThumbnailView", "Height recompute");
                        float f5 = height2 / width;
                        if (f5 != 0.0f) {
                            f = freeformThumbnailView.mMaxSize;
                            f2 = f / f5;
                            float f42 = f2;
                            height2 = f;
                            width = f42;
                            int i32 = (int) width;
                            freeformThumbnailView.mWidth = i32;
                            int i222 = (int) height2;
                            freeformThumbnailView.mHeight = i222;
                            freeformThumbnailView.mBitmap = Bitmap.createScaledBitmap(bitmapCreateBitmap, i32, i222, true);
                        }
                    }
                    freeformThumbnailView.mImageView.setImageBitmap(freeformThumbnailView.mBitmap);
                    SingleInstanceItemViewHolder singleInstanceItemViewHolder3 = SingleInstanceItemViewHolder.this;
                    FreeformContainerFolderView freeformContainerFolderView2 = FreeformContainerFolderView.this;
                    FreeformThumbnailView freeformThumbnailView2 = freeformContainerFolderView2.mFreeformThumbnailView;
                    ImageView imageView = singleInstanceItemViewHolder3.mIconView;
                    Rect rect = freeformContainerFolderView2.mItemDecoration.mItemMargin;
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
                    if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW) {
                        FreeformContainerFolderView.this.dismissMultiInstancePreviewPopup("other_preview_by_hover");
                    }
                    if (CoreRune.MW_FREEFORM_MINIMIZE_SA_LOGGING) {
                        CoreSaLogger.logForAdvanced("2204");
                    }
                }
            };
            this.mDismissPreview = new Runnable() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.SingleInstanceItemViewHolder.2
                @Override // java.lang.Runnable
                public final void run() {
                    SingleInstanceItemViewHolder singleInstanceItemViewHolder = SingleInstanceItemViewHolder.this;
                    FreeformThumbnailView freeformThumbnailView = FreeformContainerFolderView.this.mFreeformThumbnailView;
                    if (freeformThumbnailView == null || !singleInstanceItemViewHolder.mIsVisiblePreview) {
                        return;
                    }
                    freeformThumbnailView.scheduleAnimation(false);
                    SingleInstanceItemViewHolder.this.mIsVisiblePreview = false;
                }
            };
            FreeformContainerFolderView$MultiInstanceItemViewHolder$$ExternalSyntheticLambda1 freeformContainerFolderView$MultiInstanceItemViewHolder$$ExternalSyntheticLambda1 = new FreeformContainerFolderView$MultiInstanceItemViewHolder$$ExternalSyntheticLambda1(this, 1);
            this.mButtonHoverListener = freeformContainerFolderView$MultiInstanceItemViewHolder$$ExternalSyntheticLambda1;
            ImageView imageView = (ImageView) view.findViewById(R.id.freeform_container_item_image);
            this.mIconView = imageView;
            imageView.setHapticFeedbackEnabled(false);
            if (CoreRune.MW_FREEFORM_MINIMIZED_PREVIEW) {
                imageView.setOnHoverListener(freeformContainerFolderView$MultiInstanceItemViewHolder$$ExternalSyntheticLambda1);
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
                freeformContainerFolderView.mHasIconMoved = false;
                Log.i("FreeformContainer", "[FolderView] onTouch(" + MotionEvent.actionToString(action) + ")");
                return true;
            }
            if (action == 1) {
                Log.i("FreeformContainer", "[FolderView] onTouch(" + MotionEvent.actionToString(action) + ")");
                FreeformContainerItem freeformContainerItem = this.mItem;
                if (freeformContainerItem != null) {
                    FreeformContainerFolderView.this.mH.sendMessage(30, freeformContainerItem);
                }
                FreeformContainerFolderView.this.mViewController.updateContainerState(0, true, true);
                return false;
            }
            if (action == 2) {
                int itemCount = FreeformContainerFolderView.this.mAdapter.getItemCount();
                FreeformContainerFolderView freeformContainerFolderView2 = FreeformContainerFolderView.this;
                float f = itemCount < freeformContainerFolderView2.mVisibleIconMaxCount ? rawX - freeformContainerFolderView2.mLastPositionX : 0.0f;
                float f2 = rawY - freeformContainerFolderView2.mLastPositionY;
                FreeformContainerFolderView.getDraggingViewBounds(freeformContainerFolderView2.mTmpBounds, freeformContainerFolderView2.mDraggingIconView);
                float fHypot = (float) Math.hypot(f, f2);
                FreeformContainerFolderView freeformContainerFolderView3 = FreeformContainerFolderView.this;
                if (fHypot >= freeformContainerFolderView3.mThresholdToMove && !freeformContainerFolderView3.mViewController.isDismissButtonShowing() && !FreeformContainerFolderView.this.isSpringAnimating()) {
                    FreeformContainerFolderView freeformContainerFolderView4 = FreeformContainerFolderView.this;
                    FreeformContainerViewController freeformContainerViewController = freeformContainerFolderView4.mViewController;
                    Rect rect = freeformContainerFolderView4.mTmpBounds;
                    freeformContainerViewController.createOrUpdateDismissButton();
                    freeformContainerViewController.mDismissButtonView.show(rect);
                    FreeformContainerFolderView.this.finishDraggingAppIcon();
                    FreeformContainerFolderView freeformContainerFolderView5 = FreeformContainerFolderView.this;
                    freeformContainerFolderView5.mTargetIconView = this.mIconView;
                    freeformContainerFolderView5.mTargetItem = this.mItem;
                    FreeformContainerFolderView.m3265$$Nest$mstartDraggingAppIcon(freeformContainerFolderView5);
                    this.mIconView.setImageDrawable(FreeformContainerFolderView.this.mEmptySlotIcon);
                    Log.i("FreeformContainer", "[FolderView] onTouch(" + MotionEvent.actionToString(action) + "): Ready to move");
                    return false;
                }
            }
            return true;
        }
    }

    /* renamed from: -$$Nest$mstartDraggingAppIcon, reason: not valid java name */
    public static void m3265$$Nest$mstartDraggingAppIcon(FreeformContainerFolderView freeformContainerFolderView) {
        ImageView imageView = freeformContainerFolderView.mTargetIconView;
        if (imageView == null) {
            Log.e("FreeformContainer", "[FolderView] mTargetIconView is null");
            return;
        }
        imageView.getLocationOnScreen(freeformContainerFolderView.mDraggingIconReturnLocation);
        freeformContainerFolderView.mDraggingIconView.setX(freeformContainerFolderView.mDraggingIconReturnLocation[0]);
        freeformContainerFolderView.mDraggingIconView.setY(freeformContainerFolderView.mDraggingIconReturnLocation[1]);
        Drawable cloneDrawableFromImageView = getCloneDrawableFromImageView(freeformContainerFolderView.mTargetIconView);
        if (cloneDrawableFromImageView != null) {
            freeformContainerFolderView.mDraggingIconView.setImageDrawable(cloneDrawableFromImageView);
            freeformContainerFolderView.mDraggingIconView.setVisibility(0);
        }
    }

    public FreeformContainerFolderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSpringSystem = SpringSystem.create();
        this.mTargetItem = null;
        this.mTargetPreviewItem = null;
        this.mTmpBounds = new Rect();
        this.mDisplaySize = new Point();
        int i = 0;
        this.mIsExpanded = false;
        this.mIsExpandAnimating = false;
        this.mIsCollapseAnimating = false;
        this.mIsCollapsePreview = false;
        this.mBlockDataUpdate = false;
        this.mHasIconMoved = false;
        this.mItemAddedWhileAnimating = false;
        this.mLastScrollState = 0;
        this.mDraggingIconReturnLocation = new int[2];
        this.mDraggingPreviewReturnLocation = new int[2];
        this.mAnimatingSpringX = false;
        this.mAnimatingSpringY = false;
        this.mAnimatingSpringPreviewX = false;
        this.mAnimatingSpringPreviewY = false;
        this.mCachedBitmaps = new ArrayMap();
        this.mHasPreviewMoved = false;
        this.mIsAppIconMoving = false;
        this.mOrientation = 0;
        this.mStableInsets = new Rect();
        this.mOpenFolderRunnable = new AnonymousClass1();
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mLayoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        FolderViewAdapter folderViewAdapter = new FolderViewAdapter(this, i);
        this.mAdapter = folderViewAdapter;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 1, 0, isLayoutRtl()) { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.2
            public final boolean canScrollHorizontally() {
                if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW) {
                    FreeformContainerFolderView freeformContainerFolderView = FreeformContainerFolderView.this;
                    float[] fArr = FreeformContainerFolderView.TAIL_ICON_ALPHA_ARRAY;
                    return !freeformContainerFolderView.isSpringAnimating() && FreeformContainerFolderView.this.mAdapter.getItemCount() > 1;
                }
                FreeformContainerFolderView freeformContainerFolderView2 = FreeformContainerFolderView.this;
                float[] fArr2 = FreeformContainerFolderView.TAIL_ICON_ALPHA_ARRAY;
                return !freeformContainerFolderView2.isSpringAnimating();
            }
        };
        this.mLayoutManager = layoutManager;
        FolderItemDecoration folderItemDecoration = new FolderItemDecoration(this, i);
        this.mItemDecoration = folderItemDecoration;
        this.mSettingsObserver = new SettingsObserver();
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

    public static Drawable getCloneDrawableFromImageView(ImageView imageView) {
        Drawable drawableNewDrawable = (imageView.getDrawable() == null || imageView.getDrawable().getConstantState() == null) ? null : imageView.getDrawable().getConstantState().newDrawable();
        if (drawableNewDrawable == null) {
            Log.e("FreeformContainer", "[FolderView] " + imageView + " failed to newDrawable()");
        }
        return drawableNewDrawable;
    }

    public static void getDraggingViewBounds(Rect rect, View view) {
        int x = (int) view.getX();
        int y = (int) view.getY();
        rect.set(x, y, view.getWidth() + x, view.getHeight() + y);
    }

    public final void calculateFolderSize() {
        int iMin = Math.min((this.mViewController.mNonDecorDisplayFrame.width() - this.mPaddingLeft) - this.mPaddingRight, this.mFolderMaxWidth);
        FolderItemDecoration folderItemDecoration = this.mItemDecoration;
        Rect rect = folderItemDecoration.mItemMargin;
        FreeformContainerFolderTrayView freeformContainerFolderTrayView = this.mTrayView;
        int i = freeformContainerFolderTrayView.mCloseButtonSize + freeformContainerFolderTrayView.mOpenAllAppsButtonSize;
        int i2 = rect.left;
        int i3 = this.mItemSize + folderItemDecoration.mItemSpace;
        int i4 = this.mVisibleIconMaxCount;
        int i5 = (i3 * i4) + i2;
        this.mMaxWidth = i5;
        this.mVisibleIconCount = i4;
        int i6 = rect.right;
        if (iMin < i5 + i6 + i) {
            this.mMaxWidth = iMin - (i6 + i);
        }
        if (this.mAdapter.getItemCount() > 20) {
            return;
        }
        int itemCount = this.mAdapter.getItemCount();
        int i7 = this.mWidth;
        int i8 = this.mHeight;
        FolderItemDecoration folderItemDecoration2 = this.mItemDecoration;
        int i9 = ((this.mItemSize + folderItemDecoration2.mItemSpace) * itemCount) + folderItemDecoration2.mItemMargin.left;
        this.mWidth = i9;
        int iMin2 = Math.min(i9, this.mMaxWidth);
        this.mWidth = iMin2;
        Rect rect2 = this.mItemDecoration.mItemMargin;
        int i10 = rect2.top + this.mItemSize + rect2.bottom;
        this.mHeight = i10;
        if (i7 == iMin2 && i8 == i10) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = this.mWidth;
        layoutParams.height = this.mHeight;
        setLayoutParams(layoutParams);
        FreeformContainerFolderTrayView freeformContainerFolderTrayView2 = this.mTrayView;
        int i11 = this.mWidth;
        int i12 = this.mHeight;
        freeformContainerFolderTrayView2.mWidth = i11 + freeformContainerFolderTrayView2.mItemMargin.right + freeformContainerFolderTrayView2.mCloseButtonSize + freeformContainerFolderTrayView2.mOpenAllAppsButtonSize;
        freeformContainerFolderTrayView2.mHeight = i12;
        ViewGroup.LayoutParams layoutParams2 = freeformContainerFolderTrayView2.getLayoutParams();
        layoutParams2.width = freeformContainerFolderTrayView2.mWidth;
        layoutParams2.height = freeformContainerFolderTrayView2.mHeight;
        freeformContainerFolderTrayView2.setLayoutParams(layoutParams2);
        Log.i("FreeformContainer", "[FolderView] updateFolderSize: itemCount=" + itemCount + ", size=(" + this.mWidth + "x" + this.mHeight + ")");
    }

    public final void calculateVisibleIconMaxCount() {
        if (Settings.System.getInt(FreeformContainerFolderView.this.mContext.getContentResolver(), SettingsHelper.INDEX_EASY_MODE_SWITCH, 1) == 0) {
            this.mVisibleIconMaxCount = 4;
        } else {
            this.mVisibleIconMaxCount = CoreRune.IS_TABLET_DEVICE ? 8 : 4;
        }
    }

    public final void collapse(boolean z) throws Resources.NotFoundException {
        FreeformThumbnailView freeformThumbnailView;
        if (this.mIsExpanded) {
            this.mIsExpanded = false;
            this.mBlockDataUpdate = false;
            this.mItemAddedWhileAnimating = false;
            scrollToPosition(0);
            finishDraggingAppIcon();
            this.mH.removeCallbacks(this.mOpenFolderRunnable);
            int itemCount = this.mAdapter.getItemCount();
            for (int i = 0; i < itemCount; i++) {
                RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = findViewHolderForAdapterPosition(i);
                if (viewHolderFindViewHolderForAdapterPosition != null) {
                    viewHolderFindViewHolderForAdapterPosition.itemView.clearAnimation();
                }
            }
            this.mAdapter.notifyDataSetChanged();
            setAdapter((RecyclerView.Adapter) null);
            this.mTrayView.clearAnimation();
            if (z) {
                Log.i("FreeformContainer", "[FolderView] animateCollapse");
                this.mIsCollapseAnimating = true;
                Animator animatorLoadAnimator = AnimatorInflater.loadAnimator(this.mContext, R.anim.freeform_container_folder_collapse);
                animatorLoadAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.freeform.FreeformContainerFolderView.4
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        FreeformContainerFolderView.this.setVisibility(8);
                        FreeformContainerFolderView.this.setAlpha(1.0f);
                        FreeformContainerFolderView.this.mViewController.closeFullscreenMode("fullscreen_mode_request_folder");
                        FreeformContainerFolderView.this.mIsCollapseAnimating = false;
                    }
                });
                animatorLoadAnimator.setTarget(this);
                animatorLoadAnimator.start();
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

    public final void computeInset() {
        FreeformContainerManager freeformContainerManager = FreeformContainerManager.getInstance(this.mContext);
        Rect rect = this.mStableInsets;
        freeformContainerManager.getClass();
        FreeformContainerManager.getOverrideStableInsets(rect);
        if (this.mOrientation != 1) {
            Rect rect2 = this.mStableInsets;
            int i = rect2.bottom;
            int i2 = this.mAirViewMargin;
            if (i < i2) {
                rect2.bottom = i2;
                return;
            }
            return;
        }
        Rect rect3 = this.mStableInsets;
        int i3 = rect3.left;
        int i4 = this.mAirViewMargin;
        if (i3 < i4) {
            rect3.left = i4;
        }
        if (rect3.right < i4) {
            rect3.right = i4;
        }
    }

    public final void dismissMultiInstancePreviewPopup(String str) {
        if (this.mPopupWindow == null) {
            return;
        }
        Log.d("FreeformContainer", "[FolderView] dismissPopupWindow: " + this.mPopupWindow + ", reason=" + str);
        this.mPopupWindow.dismiss();
        this.mPopupWindow = null;
    }

    public final void finishDraggingAppIcon() {
        if (isSpringAnimating()) {
            Spring spring = this.mDraggingIconSpringX;
            if (spring != null) {
                spring.removeAllListeners();
            }
            Spring spring2 = this.mDraggingIconSpringY;
            if (spring2 != null) {
                spring2.removeAllListeners();
            }
            this.mAnimatingSpringX = false;
            this.mAnimatingSpringY = false;
        }
        this.mTargetIconView = null;
        this.mTargetItem = null;
        this.mDraggingIconView.setImageDrawable(null);
        this.mDraggingIconView.setVisibility(8);
    }

    public final void finishDraggingAppPreview() {
        if (isSpringPreviewAnimating()) {
            Spring spring = this.mDraggingPreviewSpringX;
            if (spring != null) {
                spring.removeAllListeners();
            }
            Spring spring2 = this.mDraggingPreviewSpringY;
            if (spring2 != null) {
                spring2.removeAllListeners();
            }
            this.mAnimatingSpringPreviewX = false;
            this.mAnimatingSpringPreviewY = false;
        }
        this.mTargetPreview = null;
        this.mTargetPreviewItem = null;
        this.mDraggingPreview.setImageDrawable(null);
        this.mDraggingPreview.setVisibility(8);
    }

    public final Bitmap getTaskSnapshot(int i) {
        GraphicBuffer snapshot;
        Bitmap bitmap = (Bitmap) this.mCachedBitmaps.get(Integer.valueOf(i));
        if (bitmap != null) {
            return bitmap;
        }
        try {
            TaskSnapshot taskSnapshot = ActivityTaskManager.getService().getTaskSnapshot(i, true);
            if (taskSnapshot != null && (snapshot = taskSnapshot.getSnapshot()) != null) {
                Bitmap bitmapWrapHardwareBuffer = Bitmap.wrapHardwareBuffer(HardwareBuffer.createFromGraphicBuffer(snapshot), null);
                this.mCachedBitmaps.put(Integer.valueOf(i), bitmapWrapHardwareBuffer);
                return bitmapWrapHardwareBuffer;
            }
        } catch (RemoteException e) {
            Log.e("FreeformContainer", "Failed to get task snapshot, taskId=" + e);
        }
        return null;
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

    public final boolean isSpringPreviewAnimating() {
        return this.mAnimatingSpringPreviewX || this.mAnimatingSpringPreviewY;
    }

    public final void onConfigurationChanged(Configuration configuration) {
        int i;
        super.onConfigurationChanged(configuration);
        if (!CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW || (i = configuration.orientation) == this.mOrientation) {
            return;
        }
        this.mOrientation = i;
        getDisplay().getRealSize(this.mDisplaySize);
        computeInset();
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mViewController.isDismissButtonShowing()) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerCallback
    public final void onItemAdded(FreeformContainerItem freeformContainerItem) {
        if (!this.mBlockDataUpdate) {
            this.mAdapter.notifyDataSetChanged();
        } else {
            this.mItemAddedWhileAnimating = true;
            Log.i("FreeformContainer", "[FolderView] onItemAdded: item is added while opening folder");
        }
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerCallback
    public final void onItemRemoved(FreeformContainerItem freeformContainerItem) {
        RecyclerView recyclerView;
        if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW && (recyclerView = this.mPreviewRecycler) != null && recyclerView.getAdapter() != null && this.mMultiInstancePreviewAdapter != null) {
            freeformContainerItem.getClass();
            if (freeformContainerItem instanceof MultiInstanceItem) {
                int size = this.mMultiInstancePreviewAdapter.mSnapshotBitmapList.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    MultiInstancePreviewAdapter.PreviewItemViewHolder previewItemViewHolder = (MultiInstancePreviewAdapter.PreviewItemViewHolder) this.mPreviewRecycler.findViewHolderForAdapterPosition(i);
                    if (previewItemViewHolder != null && previewItemViewHolder.mItem.equals(freeformContainerItem)) {
                        MultiInstancePreviewAdapter multiInstancePreviewAdapter = this.mMultiInstancePreviewAdapter;
                        multiInstancePreviewAdapter.mSnapshotBitmapList.remove(i);
                        multiInstancePreviewAdapter.notifyItemRemoved(i);
                        multiInstancePreviewAdapter.notifyItemRangeChanged(i, multiInstancePreviewAdapter.mSnapshotBitmapList.size());
                        this.mMultiInstancePreviewAdapter.notifyDataSetChanged();
                        break;
                    }
                    i++;
                }
                if (this.mIsCollapsePreview || this.mMultiInstancePreviewAdapter.mSnapshotBitmapList.size() <= 1) {
                    dismissMultiInstancePreviewPopup("item_removed");
                    if (this.mIsCollapsePreview) {
                        this.mViewController.updateContainerState(0, true, true);
                    } else {
                        FolderViewAdapter folderViewAdapter = this.mAdapter;
                        FreeformContainerFolderView.this.mAdapter.notifyItemChanged(FreeformContainerFolderView.this.mViewController.mItemController.mItemList.indexOf(FreeformContainerFolderView.this.mViewController.mItemController.getItemByName(freeformContainerItem.mPackageName)));
                        if (this.mAdapter.getItemCount() == 1) {
                            this.mViewController.updateContainerState(0, true, true);
                        }
                    }
                    this.mIsCollapsePreview = false;
                } else {
                    this.mPopupWindow.update();
                }
                finishDraggingAppPreview();
                return;
            }
        }
        this.mAdapter.notifyDataSetChanged();
        if (this.mAdapter.getItemCount() == 1) {
            this.mViewController.updateContainerState(0, true, true);
        }
        if (!this.mIsCollapseAnimating) {
            calculateFolderSize();
        }
        finishDraggingAppIcon();
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x001b, code lost:
    
        if (r2 != 3) goto L38;
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
                    getDraggingViewBounds(this.mTmpBounds, this.mDraggingIconView);
                    FreeformContainerViewController freeformContainerViewController = this.mViewController;
                    Rect rect = this.mTmpBounds;
                    FreeformContainerDismissButtonView freeformContainerDismissButtonView = freeformContainerViewController.mDismissButtonView;
                    if (freeformContainerDismissButtonView != null) {
                        freeformContainerDismissButtonView.mDismissViewManager.mView.updateView(rect);
                    }
                    this.mHasIconMoved = true;
                    return true;
                }
            }
            if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER_MULTIINSTANCE_PREVIEW) {
                this.mIsAppIconMoving = false;
            }
            getDraggingViewBounds(this.mTmpBounds, this.mDraggingIconView);
            this.mViewController.hideDismissButtonAndDismissIcon(this.mTargetItem, this.mDraggingIconView, this.mTmpBounds);
            if (!this.mHasIconMoved) {
                restoreAppIcon();
            }
            if (!this.mViewController.isEnterDismissButton()) {
                float translationX = this.mDraggingIconView.getTranslationX();
                float translationY = this.mDraggingIconView.getTranslationY();
                int[] iArr = this.mDraggingIconReturnLocation;
                float f = iArr[0];
                float f2 = iArr[1];
                this.mAnimatingSpringX = Math.abs(translationX - f) > 2.0f;
                this.mAnimatingSpringY = Math.abs(translationY - f2) > 2.0f;
                if (!isSpringAnimating()) {
                    StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("[FolderView] animateToReturnDraggingAppIconView: spring failed, from=[", translationX, ",", translationY, "], to=[");
                    sbM.append(f);
                    sbM.append(",");
                    sbM.append(f2);
                    sbM.append("], call finishDraggingAppIcon()");
                    Log.i("FreeformContainer", sbM.toString());
                    finishDraggingAppIcon();
                    return true;
                }
                Spring springCreateSpring = this.mSpringSystem.createSpring();
                this.mDraggingIconSpringX = springCreateSpring;
                springCreateSpring.mSpringConfig = new SpringConfig(180.0d, 18.0d);
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
                        freeformContainerFolderView.restoreAppIcon();
                        freeformContainerFolderView.finishDraggingAppIcon();
                    }

                    @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
                    public final void onSpringUpdate(Spring spring2) {
                        FreeformContainerFolderView.this.mDraggingIconView.setX((float) spring2.mCurrentState.position);
                    }
                });
                this.mDraggingIconSpringX.setCurrentValue(translationX);
                this.mDraggingIconSpringX.setEndValue(f);
                Spring springCreateSpring2 = this.mSpringSystem.createSpring();
                this.mDraggingIconSpringY = springCreateSpring2;
                springCreateSpring2.mSpringConfig = new SpringConfig(180.0d, 18.0d);
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
                        freeformContainerFolderView.restoreAppIcon();
                        freeformContainerFolderView.finishDraggingAppIcon();
                    }

                    @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
                    public final void onSpringUpdate(Spring spring3) {
                        FreeformContainerFolderView.this.mDraggingIconView.setY((float) spring3.mCurrentState.position);
                    }
                });
                this.mDraggingIconSpringY.setCurrentValue(translationY);
                this.mDraggingIconSpringY.setEndValue(f2);
            }
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerCallback
    public final void onViewDestroyed() {
        SettingsObserver settingsObserver = this.mSettingsObserver;
        FreeformContainerFolderView.this.mContext.getContentResolver().unregisterContentObserver(settingsObserver);
    }

    public final void restoreAppIcon() {
        Drawable cloneDrawableFromImageView;
        int itemCount = this.mAdapter.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            FolderViewItemViewHolder folderViewItemViewHolder = (FolderViewItemViewHolder) findViewHolderForAdapterPosition(i);
            if (folderViewItemViewHolder != null && folderViewItemViewHolder.mItem.equals(this.mTargetItem) && (cloneDrawableFromImageView = getCloneDrawableFromImageView(this.mDraggingIconView)) != null) {
                if (this.mAdapter.getItemViewType(i) == 0) {
                    MultiInstanceItemViewHolder multiInstanceItemViewHolder = (MultiInstanceItemViewHolder) folderViewItemViewHolder;
                    int childCount = multiInstanceItemViewHolder.mMultiInstancePointerGroupView.getChildCount();
                    for (int i2 = 0; i2 < childCount; i2++) {
                        ((ImageView) multiInstanceItemViewHolder.mMultiInstancePointerGroupView.getChildAt(i2)).setImageDrawable(cloneDrawableFromImageView);
                    }
                } else {
                    ((SingleInstanceItemViewHolder) folderViewItemViewHolder).mIconView.setImageDrawable(cloneDrawableFromImageView);
                }
            }
        }
    }

    @Override // com.android.wm.shell.freeform.FreeformContainerCallback
    public final void onRotationChanged(int i, int i2, Rect rect) {
    }
}
