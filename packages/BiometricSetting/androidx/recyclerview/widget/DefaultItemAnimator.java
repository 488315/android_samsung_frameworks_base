package androidx.recyclerview.widget;

import android.annotation.SuppressLint;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class DefaultItemAnimator extends SimpleItemAnimator {
    private ArrayList<RecyclerView.ViewHolder> mPendingRemovals = new ArrayList<>();
    private ArrayList<RecyclerView.ViewHolder> mPendingAdditions = new ArrayList<>();
    private ArrayList<MoveInfo> mPendingMoves = new ArrayList<>();
    private ArrayList<ChangeInfo> mPendingChanges = new ArrayList<>();
    ArrayList<ArrayList<RecyclerView.ViewHolder>> mAdditionsList = new ArrayList<>();
    ArrayList<ArrayList<MoveInfo>> mMovesList = new ArrayList<>();
    ArrayList<ArrayList<ChangeInfo>> mChangesList = new ArrayList<>();
    ArrayList<RecyclerView.ViewHolder> mAddAnimations = new ArrayList<>();
    ArrayList<RecyclerView.ViewHolder> mMoveAnimations = new ArrayList<>();
    ArrayList<RecyclerView.ViewHolder> mRemoveAnimations = new ArrayList<>();
    ArrayList<RecyclerView.ViewHolder> mChangeAnimations = new ArrayList<>();

    private static class ChangeInfo {
        public RecyclerView.ViewHolder newHolder;
        public RecyclerView.ViewHolder oldHolder;

        ChangeInfo() {
            throw null;
        }

        @SuppressLint({"UnknownNullness"})
        public final String toString() {
            return "ChangeInfo{oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder + ", fromX=0, fromY=0, toX=0, toY=0}";
        }
    }

    private static class MoveInfo {
        MoveInfo() {
            throw null;
        }
    }

    static void cancelAll(List list) {
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size() - 1;
        if (size < 0) {
            return;
        }
        ((RecyclerView.ViewHolder) arrayList.get(size)).getClass();
        throw null;
    }

    private static void endChangeAnimationIfNecessary(ChangeInfo changeInfo, RecyclerView.ViewHolder viewHolder) {
        if (changeInfo.newHolder == viewHolder) {
            changeInfo.newHolder = null;
        } else if (changeInfo.oldHolder != viewHolder) {
            return;
        } else {
            changeInfo.oldHolder = null;
        }
        viewHolder.getClass();
        throw null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public final void endAnimations() {
        ArrayList<RecyclerView.ViewHolder> arrayList;
        int size;
        ArrayList<MoveInfo> arrayList2;
        int size2;
        int size3 = this.mPendingMoves.size() - 1;
        if (size3 >= 0) {
            this.mPendingMoves.get(size3).getClass();
            throw null;
        }
        int size4 = this.mPendingRemovals.size();
        while (true) {
            size4--;
            if (size4 < 0) {
                break;
            }
            dispatchAnimationFinished(this.mPendingRemovals.get(size4));
            this.mPendingRemovals.remove(size4);
        }
        int size5 = this.mPendingAdditions.size() - 1;
        if (size5 >= 0) {
            this.mPendingAdditions.get(size5).getClass();
            throw null;
        }
        int size6 = this.mPendingChanges.size();
        while (true) {
            size6--;
            if (size6 < 0) {
                break;
            }
            ChangeInfo changeInfo = this.mPendingChanges.get(size6);
            RecyclerView.ViewHolder viewHolder = changeInfo.oldHolder;
            if (viewHolder != null) {
                endChangeAnimationIfNecessary(changeInfo, viewHolder);
            }
            RecyclerView.ViewHolder viewHolder2 = changeInfo.newHolder;
            if (viewHolder2 != null) {
                endChangeAnimationIfNecessary(changeInfo, viewHolder2);
            }
        }
        this.mPendingChanges.clear();
        if (isRunning()) {
            int size7 = this.mMovesList.size();
            do {
                size7--;
                if (size7 < 0) {
                    int size8 = this.mAdditionsList.size();
                    do {
                        size8--;
                        if (size8 >= 0) {
                            arrayList = this.mAdditionsList.get(size8);
                            size = arrayList.size() - 1;
                        } else {
                            int size9 = this.mChangesList.size();
                            while (true) {
                                size9--;
                                if (size9 < 0) {
                                    cancelAll(this.mRemoveAnimations);
                                    cancelAll(this.mMoveAnimations);
                                    cancelAll(this.mAddAnimations);
                                    cancelAll(this.mChangeAnimations);
                                    dispatchAnimationsFinished();
                                    return;
                                }
                                ArrayList<ChangeInfo> arrayList3 = this.mChangesList.get(size9);
                                int size10 = arrayList3.size();
                                while (true) {
                                    size10--;
                                    if (size10 >= 0) {
                                        ChangeInfo changeInfo2 = arrayList3.get(size10);
                                        RecyclerView.ViewHolder viewHolder3 = changeInfo2.oldHolder;
                                        if (viewHolder3 != null) {
                                            endChangeAnimationIfNecessary(changeInfo2, viewHolder3);
                                        }
                                        RecyclerView.ViewHolder viewHolder4 = changeInfo2.newHolder;
                                        if (viewHolder4 != null) {
                                            endChangeAnimationIfNecessary(changeInfo2, viewHolder4);
                                        }
                                        if (arrayList3.isEmpty()) {
                                            this.mChangesList.remove(arrayList3);
                                        }
                                    }
                                }
                            }
                        }
                    } while (size < 0);
                    arrayList.get(size).getClass();
                    throw null;
                }
                arrayList2 = this.mMovesList.get(size7);
                size2 = arrayList2.size() - 1;
            } while (size2 < 0);
            arrayList2.get(size2).getClass();
            throw null;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public final boolean isRunning() {
        return (this.mPendingAdditions.isEmpty() && this.mPendingChanges.isEmpty() && this.mPendingMoves.isEmpty() && this.mPendingRemovals.isEmpty() && this.mMoveAnimations.isEmpty() && this.mRemoveAnimations.isEmpty() && this.mAddAnimations.isEmpty() && this.mChangeAnimations.isEmpty() && this.mMovesList.isEmpty() && this.mAdditionsList.isEmpty() && this.mChangesList.isEmpty()) ? false : true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemAnimator
    public final void runPendingAnimations() {
        boolean z = !this.mPendingRemovals.isEmpty();
        boolean z2 = !this.mPendingMoves.isEmpty();
        boolean z3 = !this.mPendingChanges.isEmpty();
        boolean z4 = !this.mPendingAdditions.isEmpty();
        if (z || z2 || z4 || z3) {
            Iterator<RecyclerView.ViewHolder> it = this.mPendingRemovals.iterator();
            if (it.hasNext()) {
                it.next().getClass();
                throw null;
            }
            this.mPendingRemovals.clear();
            if (z2) {
                ArrayList<MoveInfo> arrayList = new ArrayList<>();
                arrayList.addAll(this.mPendingMoves);
                this.mMovesList.add(arrayList);
                this.mPendingMoves.clear();
                if (z) {
                    arrayList.get(0).getClass();
                    throw null;
                }
                Iterator<MoveInfo> it2 = arrayList.iterator();
                if (it2.hasNext()) {
                    it2.next().getClass();
                    throw null;
                }
                arrayList.clear();
                this.mMovesList.remove(arrayList);
            }
            if (z3) {
                final ArrayList<ChangeInfo> arrayList2 = new ArrayList<>();
                arrayList2.addAll(this.mPendingChanges);
                this.mChangesList.add(arrayList2);
                this.mPendingChanges.clear();
                Runnable runnable = new Runnable() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Iterator it3 = arrayList2.iterator();
                        while (it3.hasNext()) {
                            ChangeInfo changeInfo = (ChangeInfo) it3.next();
                            DefaultItemAnimator.this.getClass();
                            RecyclerView.ViewHolder viewHolder = changeInfo.oldHolder;
                        }
                        arrayList2.clear();
                        DefaultItemAnimator.this.mChangesList.remove(arrayList2);
                    }
                };
                if (z) {
                    arrayList2.get(0).oldHolder.getClass();
                    ViewCompat.postOnAnimationDelayed(null, runnable, getRemoveDuration());
                } else {
                    runnable.run();
                }
            }
            if (z4) {
                final ArrayList<RecyclerView.ViewHolder> arrayList3 = new ArrayList<>();
                arrayList3.addAll(this.mPendingAdditions);
                this.mAdditionsList.add(arrayList3);
                this.mPendingAdditions.clear();
                Runnable runnable2 = new Runnable() { // from class: androidx.recyclerview.widget.DefaultItemAnimator.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        Iterator it3 = arrayList3.iterator();
                        if (!it3.hasNext()) {
                            arrayList3.clear();
                            DefaultItemAnimator.this.mAdditionsList.remove(arrayList3);
                        } else {
                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) it3.next();
                            DefaultItemAnimator.this.getClass();
                            viewHolder.getClass();
                            throw null;
                        }
                    }
                };
                if (!z && !z2 && !z3) {
                    runnable2.run();
                    return;
                }
                long max = Math.max(z2 ? getMoveDuration() : 0L, z3 ? getChangeDuration() : 0L) + (z ? getRemoveDuration() : 0L);
                arrayList3.get(0).getClass();
                ViewCompat.postOnAnimationDelayed(null, runnable2, max);
            }
        }
    }
}
