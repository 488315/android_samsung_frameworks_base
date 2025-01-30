package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DefaultItemAnimator extends SimpleItemAnimator {
    public static final Interpolator ITEM_MOVE_INTERPOLATOR = new PathInterpolator(0.4f, 0.6f, 0.0f, 1.0f);
    public static TimeInterpolator sDefaultInterpolator;
    public final ArrayList mPendingRemovals = new ArrayList();
    public final ArrayList mPendingAdditions = new ArrayList();
    public final ArrayList mPendingMoves = new ArrayList();
    public final ArrayList mPendingChanges = new ArrayList();
    public final ArrayList mAdditionsList = new ArrayList();
    public final ArrayList mMovesList = new ArrayList();
    public final ArrayList mChangesList = new ArrayList();
    public final ArrayList mAddAnimations = new ArrayList();
    public final ArrayList mMoveAnimations = new ArrayList();
    public final ArrayList mRemoveAnimations = new ArrayList();
    public final ArrayList mChangeAnimations = new ArrayList();
    public int mPendingAnimFlag = 0;
    public int mLastItemBottom = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MoveInfo {
        public final int fromX;
        public final int fromY;
        public final RecyclerView.ViewHolder holder;
        public final int toX;
        public final int toY;

        public MoveInfo(RecyclerView.ViewHolder viewHolder, int i, int i2, int i3, int i4) {
            this.holder = viewHolder;
            this.fromX = i;
            this.fromY = i2;
            this.toX = i3;
            this.toY = i4;
        }
    }

    public static void cancelAll(List list) {
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            } else {
                ((RecyclerView.ViewHolder) arrayList.get(size)).itemView.animate().cancel();
            }
        }
    }

    @Override // androidx.recyclerview.widget.SimpleItemAnimator
    public final void animateAdd(RecyclerView.ViewHolder viewHolder) {
        resetAnimation(viewHolder);
        viewHolder.itemView.setAlpha(0.0f);
        this.mPendingAdditions.add(viewHolder);
        int i = this.mPendingAnimFlag;
        if ((i & 8) == 0) {
            this.mPendingAnimFlag = i | 8;
        }
    }

    @Override // androidx.recyclerview.widget.SimpleItemAnimator
    public final boolean animateChange(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2, int i, int i2, int i3, int i4) {
        if (viewHolder == viewHolder2) {
            return animateMove(viewHolder, i, i2, i3, i4);
        }
        View view = viewHolder.itemView;
        float translationX = view.getTranslationX();
        float translationY = view.getTranslationY();
        float alpha = view.getAlpha();
        resetAnimation(viewHolder);
        view.setTranslationX(translationX);
        view.setTranslationY(translationY);
        view.setAlpha(alpha);
        resetAnimation(viewHolder2);
        float f = -((int) ((i3 - i) - translationX));
        View view2 = viewHolder2.itemView;
        view2.setTranslationX(f);
        view2.setTranslationY(-((int) ((i4 - i2) - translationY)));
        view2.setAlpha(0.0f);
        this.mPendingChanges.add(new ChangeInfo(viewHolder, viewHolder2, i, i2, i3, i4));
        int i5 = this.mPendingAnimFlag;
        if ((i5 & 4) != 0) {
            return true;
        }
        this.mPendingAnimFlag = i5 | 4;
        return true;
    }

    @Override // androidx.recyclerview.widget.SimpleItemAnimator
    public final boolean animateMove(RecyclerView.ViewHolder viewHolder, int i, int i2, int i3, int i4) {
        View view = viewHolder.itemView;
        int translationX = i + ((int) view.getTranslationX());
        int translationY = i2 + ((int) viewHolder.itemView.getTranslationY());
        resetAnimation(viewHolder);
        int i5 = i3 - translationX;
        int i6 = i4 - translationY;
        if (i5 == 0 && i6 == 0) {
            dispatchAnimationFinished(viewHolder);
            return false;
        }
        if (i5 != 0) {
            view.setTranslationX(-i5);
        }
        if (i6 != 0) {
            view.setTranslationY(-i6);
        }
        this.mPendingMoves.add(new MoveInfo(viewHolder, translationX, translationY, i3, i4));
        int i7 = this.mPendingAnimFlag;
        if ((i7 & 2) != 0) {
            return true;
        }
        this.mPendingAnimFlag = i7 | 2;
        return true;
    }

    @Override // androidx.recyclerview.widget.SimpleItemAnimator
    public final void animateRemove(RecyclerView.ViewHolder viewHolder) {
        resetAnimation(viewHolder);
        this.mPendingRemovals.add(viewHolder);
        View view = viewHolder.itemView;
        if (view.getBottom() > this.mLastItemBottom) {
            this.mLastItemBottom = view.getBottom();
        }
        int i = this.mPendingAnimFlag;
        if ((i & 1) == 0) {
            this.mPendingAnimFlag = i | 1;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public final boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder, List list) {
        return !list.isEmpty() || canReuseUpdatedViewHolder(viewHolder);
    }

    public final void dispatchFinishedWhenDone() {
        if (isRunning()) {
            return;
        }
        ArrayList arrayList = this.mFinishedListeners;
        if (arrayList.size() <= 0) {
            arrayList.clear();
        } else {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(arrayList.get(0));
            throw null;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public final void endAnimation(RecyclerView.ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        view.animate().cancel();
        ArrayList arrayList = this.mPendingMoves;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            if (((MoveInfo) arrayList.get(size)).holder == viewHolder) {
                view.setTranslationY(0.0f);
                view.setTranslationX(0.0f);
                dispatchAnimationFinished(viewHolder);
                arrayList.remove(size);
            }
        }
        endChangeAnimation(viewHolder, this.mPendingChanges);
        if (this.mPendingRemovals.remove(viewHolder)) {
            view.setAlpha(1.0f);
            dispatchAnimationFinished(viewHolder);
        }
        if (this.mPendingAdditions.remove(viewHolder)) {
            view.setAlpha(1.0f);
            dispatchAnimationFinished(viewHolder);
        }
        ArrayList arrayList2 = this.mChangesList;
        for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
            ArrayList arrayList3 = (ArrayList) arrayList2.get(size2);
            endChangeAnimation(viewHolder, arrayList3);
            if (arrayList3.isEmpty()) {
                arrayList2.remove(size2);
            }
        }
        ArrayList arrayList4 = this.mMovesList;
        for (int size3 = arrayList4.size() - 1; size3 >= 0; size3--) {
            ArrayList arrayList5 = (ArrayList) arrayList4.get(size3);
            int size4 = arrayList5.size() - 1;
            while (true) {
                if (size4 < 0) {
                    break;
                }
                if (((MoveInfo) arrayList5.get(size4)).holder == viewHolder) {
                    view.setTranslationY(0.0f);
                    view.setTranslationX(0.0f);
                    dispatchAnimationFinished(viewHolder);
                    arrayList5.remove(size4);
                    if (arrayList5.isEmpty()) {
                        arrayList4.remove(size3);
                    }
                } else {
                    size4--;
                }
            }
        }
        ArrayList arrayList6 = this.mAdditionsList;
        for (int size5 = arrayList6.size() - 1; size5 >= 0; size5--) {
            ArrayList arrayList7 = (ArrayList) arrayList6.get(size5);
            if (arrayList7.remove(viewHolder)) {
                view.setAlpha(1.0f);
                dispatchAnimationFinished(viewHolder);
                if (arrayList7.isEmpty()) {
                    arrayList6.remove(size5);
                }
            }
        }
        this.mRemoveAnimations.remove(viewHolder);
        this.mAddAnimations.remove(viewHolder);
        this.mChangeAnimations.remove(viewHolder);
        this.mMoveAnimations.remove(viewHolder);
        dispatchFinishedWhenDone();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public final void endAnimations() {
        ArrayList arrayList = this.mPendingMoves;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            MoveInfo moveInfo = (MoveInfo) arrayList.get(size);
            View view = moveInfo.holder.itemView;
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            dispatchAnimationFinished(moveInfo.holder);
            arrayList.remove(size);
        }
        ArrayList arrayList2 = this.mPendingRemovals;
        int size2 = arrayList2.size();
        while (true) {
            size2--;
            if (size2 < 0) {
                break;
            }
            dispatchAnimationFinished((RecyclerView.ViewHolder) arrayList2.get(size2));
            arrayList2.remove(size2);
        }
        ArrayList arrayList3 = this.mPendingAdditions;
        int size3 = arrayList3.size();
        while (true) {
            size3--;
            if (size3 < 0) {
                break;
            }
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) arrayList3.get(size3);
            viewHolder.itemView.setAlpha(1.0f);
            dispatchAnimationFinished(viewHolder);
            arrayList3.remove(size3);
        }
        ArrayList arrayList4 = this.mPendingChanges;
        int size4 = arrayList4.size();
        while (true) {
            size4--;
            if (size4 < 0) {
                break;
            }
            ChangeInfo changeInfo = (ChangeInfo) arrayList4.get(size4);
            RecyclerView.ViewHolder viewHolder2 = changeInfo.oldHolder;
            if (viewHolder2 != null) {
                endChangeAnimationIfNecessary(changeInfo, viewHolder2);
            }
            RecyclerView.ViewHolder viewHolder3 = changeInfo.newHolder;
            if (viewHolder3 != null) {
                endChangeAnimationIfNecessary(changeInfo, viewHolder3);
            }
        }
        arrayList4.clear();
        if (isRunning()) {
            ArrayList arrayList5 = this.mMovesList;
            int size5 = arrayList5.size();
            while (true) {
                size5--;
                if (size5 < 0) {
                    break;
                }
                ArrayList arrayList6 = (ArrayList) arrayList5.get(size5);
                int size6 = arrayList6.size();
                while (true) {
                    size6--;
                    if (size6 >= 0) {
                        MoveInfo moveInfo2 = (MoveInfo) arrayList6.get(size6);
                        View view2 = moveInfo2.holder.itemView;
                        view2.setTranslationY(0.0f);
                        view2.setTranslationX(0.0f);
                        dispatchAnimationFinished(moveInfo2.holder);
                        arrayList6.remove(size6);
                        if (arrayList6.isEmpty()) {
                            arrayList5.remove(arrayList6);
                        }
                    }
                }
            }
            ArrayList arrayList7 = this.mAdditionsList;
            int size7 = arrayList7.size();
            while (true) {
                size7--;
                if (size7 < 0) {
                    break;
                }
                ArrayList arrayList8 = (ArrayList) arrayList7.get(size7);
                int size8 = arrayList8.size();
                while (true) {
                    size8--;
                    if (size8 >= 0) {
                        RecyclerView.ViewHolder viewHolder4 = (RecyclerView.ViewHolder) arrayList8.get(size8);
                        viewHolder4.itemView.setAlpha(1.0f);
                        dispatchAnimationFinished(viewHolder4);
                        arrayList8.remove(size8);
                        if (arrayList8.isEmpty()) {
                            arrayList7.remove(arrayList8);
                        }
                    }
                }
            }
            ArrayList arrayList9 = this.mChangesList;
            int size9 = arrayList9.size();
            while (true) {
                size9--;
                if (size9 < 0) {
                    break;
                }
                ArrayList arrayList10 = (ArrayList) arrayList9.get(size9);
                int size10 = arrayList10.size();
                while (true) {
                    size10--;
                    if (size10 >= 0) {
                        ChangeInfo changeInfo2 = (ChangeInfo) arrayList10.get(size10);
                        RecyclerView.ViewHolder viewHolder5 = changeInfo2.oldHolder;
                        if (viewHolder5 != null) {
                            endChangeAnimationIfNecessary(changeInfo2, viewHolder5);
                        }
                        RecyclerView.ViewHolder viewHolder6 = changeInfo2.newHolder;
                        if (viewHolder6 != null) {
                            endChangeAnimationIfNecessary(changeInfo2, viewHolder6);
                        }
                        if (arrayList10.isEmpty()) {
                            arrayList9.remove(arrayList10);
                        }
                    }
                }
            }
            cancelAll(this.mRemoveAnimations);
            cancelAll(this.mMoveAnimations);
            cancelAll(this.mAddAnimations);
            cancelAll(this.mChangeAnimations);
            ArrayList arrayList11 = this.mFinishedListeners;
            if (arrayList11.size() <= 0) {
                arrayList11.clear();
            } else {
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(arrayList11.get(0));
                throw null;
            }
        }
    }

    public final void endChangeAnimation(RecyclerView.ViewHolder viewHolder, List list) {
        int size = list.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            ChangeInfo changeInfo = (ChangeInfo) list.get(size);
            if (endChangeAnimationIfNecessary(changeInfo, viewHolder) && changeInfo.oldHolder == null && changeInfo.newHolder == null) {
                list.remove(changeInfo);
            }
        }
    }

    public final boolean endChangeAnimationIfNecessary(ChangeInfo changeInfo, RecyclerView.ViewHolder viewHolder) {
        if (changeInfo.newHolder == viewHolder) {
            changeInfo.newHolder = null;
        } else {
            if (changeInfo.oldHolder != viewHolder) {
                return false;
            }
            changeInfo.oldHolder = null;
        }
        viewHolder.itemView.setAlpha(1.0f);
        View view = viewHolder.itemView;
        view.setTranslationX(0.0f);
        view.setTranslationY(0.0f);
        dispatchAnimationFinished(viewHolder);
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public final long getMoveDuration() {
        return 400L;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public final long getRemoveDuration() {
        return 100L;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public final boolean isRunning() {
        return (this.mPendingAdditions.isEmpty() && this.mPendingChanges.isEmpty() && this.mPendingMoves.isEmpty() && this.mPendingRemovals.isEmpty() && this.mMoveAnimations.isEmpty() && this.mRemoveAnimations.isEmpty() && this.mAddAnimations.isEmpty() && this.mChangeAnimations.isEmpty() && this.mMovesList.isEmpty() && this.mAdditionsList.isEmpty() && this.mChangesList.isEmpty()) ? false : true;
    }

    public final void resetAnimation(RecyclerView.ViewHolder viewHolder) {
        if (sDefaultInterpolator == null) {
            sDefaultInterpolator = new ValueAnimator().getInterpolator();
        }
        viewHolder.itemView.animate().setInterpolator(sDefaultInterpolator);
        endAnimation(viewHolder);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public final void runPendingAnimations() {
        ArrayList arrayList = this.mPendingRemovals;
        boolean z = !arrayList.isEmpty();
        ArrayList arrayList2 = this.mPendingMoves;
        boolean z2 = !arrayList2.isEmpty();
        ArrayList arrayList3 = this.mPendingChanges;
        boolean z3 = !arrayList3.isEmpty();
        ArrayList arrayList4 = this.mPendingAdditions;
        boolean z4 = !arrayList4.isEmpty();
        if (z || z2 || z4 || z3) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                final RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) it.next();
                final View view = viewHolder.itemView;
                final ViewPropertyAnimator animate = view.animate();
                long j = (view.getTag() == null || !view.getTag().equals("preferencecategory")) ? 100L : 0L;
                this.mRemoveAnimations.add(viewHolder);
                animate.setDuration(j).alpha(0.0f).setListener(new AnimatorListenerAdapter() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.4
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        animate.setListener(null);
                        view.setAlpha(1.0f);
                        DefaultItemAnimator.this.dispatchAnimationFinished(viewHolder);
                        DefaultItemAnimator.this.mRemoveAnimations.remove(viewHolder);
                        DefaultItemAnimator.this.dispatchFinishedWhenDone();
                        DefaultItemAnimator defaultItemAnimator = DefaultItemAnimator.this;
                        int i = defaultItemAnimator.mPendingAnimFlag;
                        if ((i & 1) != 0) {
                            defaultItemAnimator.mPendingAnimFlag = i & (-2);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        DefaultItemAnimator.this.getClass();
                    }
                }).start();
            }
            arrayList.clear();
            if (z2) {
                final ArrayList arrayList5 = new ArrayList();
                arrayList5.addAll(arrayList2);
                this.mMovesList.add(arrayList5);
                arrayList2.clear();
                new Runnable() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Iterator it2 = arrayList5.iterator();
                        while (it2.hasNext()) {
                            MoveInfo moveInfo = (MoveInfo) it2.next();
                            final DefaultItemAnimator defaultItemAnimator = DefaultItemAnimator.this;
                            final RecyclerView.ViewHolder viewHolder2 = moveInfo.holder;
                            defaultItemAnimator.getClass();
                            final View view2 = viewHolder2.itemView;
                            final int i = moveInfo.toX - moveInfo.fromX;
                            final int i2 = moveInfo.toY - moveInfo.fromY;
                            if (i != 0) {
                                view2.animate().translationX(0.0f);
                            }
                            if (i2 != 0) {
                                view2.animate().translationY(0.0f);
                            }
                            final ViewPropertyAnimator animate2 = view2.animate();
                            animate2.setInterpolator(DefaultItemAnimator.ITEM_MOVE_INTERPOLATOR);
                            defaultItemAnimator.mMoveAnimations.add(viewHolder2);
                            View view3 = defaultItemAnimator.mHostView;
                            if (view3 instanceof RecyclerView) {
                                RecyclerView recyclerView = (RecyclerView) view3;
                                if (recyclerView.mBlackTop != -1 && viewHolder2.getLayoutPosition() == recyclerView.mChildHelper.getChildCount() - 1) {
                                    animate2.setUpdateListener(new ValueAnimator.AnimatorUpdateListener(defaultItemAnimator, recyclerView) { // from class: androidx.recyclerview.widget.DefaultItemAnimator.6
                                        public final /* synthetic */ RecyclerView val$recyclerView;

                                        {
                                            this.val$recyclerView = recyclerView;
                                        }

                                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                            this.val$recyclerView.invalidate();
                                        }
                                    });
                                }
                            }
                            animate2.setDuration(400L).setListener(new AnimatorListenerAdapter() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.7
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationCancel(Animator animator) {
                                    if (i != 0) {
                                        view2.setTranslationX(0.0f);
                                    }
                                    if (i2 != 0) {
                                        view2.setTranslationY(0.0f);
                                    }
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    animate2.setListener(null);
                                    DefaultItemAnimator.this.dispatchAnimationFinished(viewHolder2);
                                    DefaultItemAnimator.this.mMoveAnimations.remove(viewHolder2);
                                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                                    DefaultItemAnimator defaultItemAnimator2 = DefaultItemAnimator.this;
                                    int i3 = defaultItemAnimator2.mPendingAnimFlag;
                                    if ((i3 & 2) != 0) {
                                        defaultItemAnimator2.mPendingAnimFlag = i3 & (-3);
                                    }
                                    int i4 = defaultItemAnimator2.mPendingAnimFlag;
                                    if ((i4 & 8) != 0) {
                                        defaultItemAnimator2.mPendingAnimFlag = i4 | 16;
                                    }
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationStart(Animator animator) {
                                    DefaultItemAnimator.this.getClass();
                                }
                            }).start();
                        }
                        arrayList5.clear();
                        DefaultItemAnimator.this.mMovesList.remove(arrayList5);
                    }
                }.run();
            }
            if (z3) {
                final ArrayList arrayList6 = new ArrayList();
                arrayList6.addAll(arrayList3);
                this.mChangesList.add(arrayList6);
                arrayList3.clear();
                new Runnable() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Iterator it2 = arrayList6.iterator();
                        while (it2.hasNext()) {
                            final ChangeInfo changeInfo = (ChangeInfo) it2.next();
                            final DefaultItemAnimator defaultItemAnimator = DefaultItemAnimator.this;
                            defaultItemAnimator.getClass();
                            RecyclerView.ViewHolder viewHolder2 = changeInfo.oldHolder;
                            final View view2 = viewHolder2 == null ? null : viewHolder2.itemView;
                            RecyclerView.ViewHolder viewHolder3 = changeInfo.newHolder;
                            final View view3 = viewHolder3 != null ? viewHolder3.itemView : null;
                            Interpolator interpolator = DefaultItemAnimator.ITEM_MOVE_INTERPOLATOR;
                            ArrayList arrayList7 = defaultItemAnimator.mChangeAnimations;
                            if (view2 != null) {
                                final ViewPropertyAnimator duration = view2.animate().setDuration(400L);
                                arrayList7.add(changeInfo.oldHolder);
                                duration.translationX(changeInfo.toX - changeInfo.fromX);
                                duration.translationY(changeInfo.toY - changeInfo.fromY);
                                duration.alpha(0.0f).setDuration(400L).setInterpolator(interpolator).setListener(new AnimatorListenerAdapter() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.8
                                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                    public final void onAnimationEnd(Animator animator) {
                                        duration.setListener(null);
                                        view2.setAlpha(1.0f);
                                        view2.setTranslationX(0.0f);
                                        view2.setTranslationY(0.0f);
                                        DefaultItemAnimator.this.dispatchAnimationFinished(changeInfo.oldHolder);
                                        DefaultItemAnimator.this.mChangeAnimations.remove(changeInfo.oldHolder);
                                        DefaultItemAnimator.this.dispatchFinishedWhenDone();
                                        DefaultItemAnimator defaultItemAnimator2 = DefaultItemAnimator.this;
                                        int i = defaultItemAnimator2.mPendingAnimFlag;
                                        if ((i & 4) != 0) {
                                            defaultItemAnimator2.mPendingAnimFlag = i & (-5);
                                        }
                                    }

                                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                    public final void onAnimationStart(Animator animator) {
                                        DefaultItemAnimator defaultItemAnimator2 = DefaultItemAnimator.this;
                                        RecyclerView.ViewHolder viewHolder4 = changeInfo.oldHolder;
                                        defaultItemAnimator2.getClass();
                                    }
                                }).start();
                            }
                            if (view3 != null) {
                                final ViewPropertyAnimator animate2 = view3.animate();
                                arrayList7.add(changeInfo.newHolder);
                                animate2.translationX(0.0f).translationY(0.0f).setDuration(400L).alpha(1.0f).setInterpolator(interpolator).setListener(new AnimatorListenerAdapter() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.9
                                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                    public final void onAnimationEnd(Animator animator) {
                                        animate2.setListener(null);
                                        view3.setAlpha(1.0f);
                                        view3.setTranslationX(0.0f);
                                        view3.setTranslationY(0.0f);
                                        DefaultItemAnimator.this.dispatchAnimationFinished(changeInfo.newHolder);
                                        DefaultItemAnimator.this.mChangeAnimations.remove(changeInfo.newHolder);
                                        DefaultItemAnimator.this.dispatchFinishedWhenDone();
                                    }

                                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                    public final void onAnimationStart(Animator animator) {
                                        DefaultItemAnimator defaultItemAnimator2 = DefaultItemAnimator.this;
                                        RecyclerView.ViewHolder viewHolder4 = changeInfo.newHolder;
                                        defaultItemAnimator2.getClass();
                                    }
                                }).start();
                            }
                        }
                        arrayList6.clear();
                        DefaultItemAnimator.this.mChangesList.remove(arrayList6);
                    }
                }.run();
            }
            if (z4) {
                final ArrayList arrayList7 = new ArrayList();
                arrayList7.addAll(arrayList4);
                this.mAdditionsList.add(arrayList7);
                arrayList4.clear();
                Runnable runnable = new Runnable() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        Iterator it2 = arrayList7.iterator();
                        while (it2.hasNext()) {
                            final RecyclerView.ViewHolder viewHolder2 = (RecyclerView.ViewHolder) it2.next();
                            final DefaultItemAnimator defaultItemAnimator = DefaultItemAnimator.this;
                            defaultItemAnimator.getClass();
                            final View view2 = viewHolder2.itemView;
                            final ViewPropertyAnimator animate2 = view2.animate();
                            long j2 = (view2.getTag() == null || !view2.getTag().equals("preferencecategory")) ? 200L : 0L;
                            defaultItemAnimator.mAddAnimations.add(viewHolder2);
                            animate2.alpha(1.0f).setDuration(j2).setListener(new AnimatorListenerAdapter() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.5
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationCancel(Animator animator) {
                                    view2.setAlpha(1.0f);
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    animate2.setListener(null);
                                    DefaultItemAnimator.this.dispatchAnimationFinished(viewHolder2);
                                    DefaultItemAnimator.this.mAddAnimations.remove(viewHolder2);
                                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                                    DefaultItemAnimator defaultItemAnimator2 = DefaultItemAnimator.this;
                                    int i = defaultItemAnimator2.mPendingAnimFlag;
                                    if ((i & 8) != 0) {
                                        defaultItemAnimator2.mPendingAnimFlag = i & (-9);
                                    }
                                    int i2 = defaultItemAnimator2.mPendingAnimFlag;
                                    if ((i2 & 16) != 0) {
                                        defaultItemAnimator2.mPendingAnimFlag = i2 & (-17);
                                    }
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationStart(Animator animator) {
                                    DefaultItemAnimator.this.getClass();
                                }
                            }).start();
                        }
                        arrayList7.clear();
                        DefaultItemAnimator.this.mAdditionsList.remove(arrayList7);
                    }
                };
                if (!z && !z2 && !z3) {
                    runnable.run();
                    return;
                }
                View view2 = ((RecyclerView.ViewHolder) arrayList7.get(0)).itemView;
                if (view2.getTag() != null && view2.getTag().equals("preferencecategory")) {
                    runnable.run();
                } else {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api16Impl.postOnAnimationDelayed(view2, runnable, 100L);
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ChangeInfo {
        public final int fromX;
        public final int fromY;
        public RecyclerView.ViewHolder newHolder;
        public RecyclerView.ViewHolder oldHolder;
        public final int toX;
        public final int toY;

        private ChangeInfo(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
            this.oldHolder = viewHolder;
            this.newHolder = viewHolder2;
        }

        public final String toString() {
            return "ChangeInfo{oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" + this.toY + '}';
        }

        public ChangeInfo(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2, int i, int i2, int i3, int i4) {
            this(viewHolder, viewHolder2);
            this.fromX = i;
            this.fromY = i2;
            this.toX = i3;
            this.toY = i4;
        }
    }
}
