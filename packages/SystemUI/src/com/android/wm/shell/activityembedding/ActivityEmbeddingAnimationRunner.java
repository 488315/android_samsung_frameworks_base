package com.android.wm.shell.activityembedding;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.window.TransitionInfo;
import com.android.wm.shell.activityembedding.ActivityEmbeddingAnimationAdapter;
import com.android.wm.shell.common.ScreenshotUtils;
import com.android.wm.shell.shared.TransitionUtil;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ActivityEmbeddingAnimationRunner {
    public Animator mActiveAnimator;
    final ActivityEmbeddingAnimationSpec mAnimationSpec;
    public final ActivityEmbeddingController mController;

    public ActivityEmbeddingAnimationRunner(Context context, ActivityEmbeddingController activityEmbeddingController) {
        this.mController = activityEmbeddingController;
        this.mAnimationSpec = new ActivityEmbeddingAnimationSpec(context);
    }

    public static void calculateParentBounds(TransitionInfo.Change change, Rect rect) {
        Point endParentSize = change.getEndParentSize();
        if (endParentSize.equals(0, 0)) {
            return;
        }
        Point endRelOffset = change.getEndRelOffset();
        Point point = new Point(change.getEndAbsBounds().left, change.getEndAbsBounds().top);
        Point point2 = new Point(point.x - endRelOffset.x, point.y - endRelOffset.y);
        int i = point2.x;
        int i2 = point2.y;
        rect.set(i, i2, endParentSize.x + i, endParentSize.y + i2);
    }

    public static List createOpenCloseAnimationAdapters(TransitionInfo transitionInfo, boolean z, ActivityEmbeddingAnimationRunner$$ExternalSyntheticLambda1 activityEmbeddingAnimationRunner$$ExternalSyntheticLambda1, SurfaceControl.Transaction transaction) {
        SurfaceControl orCreateScreenshot;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        for (TransitionInfo.Change change : transitionInfo.getChanges()) {
            if (TransitionUtil.isOpeningType(change.getMode())) {
                if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION) {
                    arrayList.add(0, change);
                } else {
                    arrayList.add(change);
                }
                rect.union(change.getEndAbsBounds());
            } else {
                if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION) {
                    arrayList2.add(0, change);
                } else {
                    arrayList2.add(change);
                }
                rect2.union(change.getStartAbsBounds());
                rect2.union(change.getEndAbsBounds());
            }
        }
        ArrayList arrayList3 = new ArrayList();
        int size = arrayList.size();
        int i = 1000;
        int i2 = 0;
        while (i2 < size) {
            int i3 = i2 + 1;
            TransitionInfo.Change change2 = (TransitionInfo.Change) arrayList.get(i2);
            Animation animation = activityEmbeddingAnimationRunner$$ExternalSyntheticLambda1.get(transitionInfo, change2, rect);
            if (shouldUseJumpCutForAnimation(animation)) {
                return new ArrayList();
            }
            ActivityEmbeddingAnimationAdapter activityEmbeddingAnimationAdapter = new ActivityEmbeddingAnimationAdapter(animation, change2, change2.getLeash(), rect, transitionInfo.getRoot(TransitionUtil.rootIndexFor(change2, transitionInfo)));
            if (z) {
                activityEmbeddingAnimationAdapter.mOverrideLayer = i;
                i++;
            }
            arrayList3.add(activityEmbeddingAnimationAdapter);
            i2 = i3;
        }
        int size2 = arrayList2.size();
        int i4 = 0;
        while (i4 < size2) {
            Object obj = arrayList2.get(i4);
            i4++;
            TransitionInfo.Change change3 = (TransitionInfo.Change) obj;
            if ((!TransitionUtil.isClosingType(change3.getMode()) ? false : !change3.getStartAbsBounds().equals(change3.getEndAbsBounds())) && (orCreateScreenshot = getOrCreateScreenshot(change3, change3, transaction)) != null) {
                ActivityEmbeddingAnimationAdapter.SnapshotAdapter snapshotAdapter = new ActivityEmbeddingAnimationAdapter.SnapshotAdapter(new AlphaAnimation(1.0f, 1.0f), change3, orCreateScreenshot, transitionInfo.getRoot(TransitionUtil.rootIndexFor(change3, transitionInfo)));
                if (!z) {
                    snapshotAdapter.mOverrideLayer = i;
                    i++;
                }
                arrayList3.add(snapshotAdapter);
            }
            ArrayList arrayList4 = arrayList3;
            int i5 = i;
            Animation animation2 = activityEmbeddingAnimationRunner$$ExternalSyntheticLambda1.get(transitionInfo, change3, rect2);
            if (shouldUseJumpCutForAnimation(animation2)) {
                return new ArrayList();
            }
            ActivityEmbeddingAnimationAdapter activityEmbeddingAnimationAdapter2 = new ActivityEmbeddingAnimationAdapter(animation2, change3, change3.getLeash(), rect2, transitionInfo.getRoot(TransitionUtil.rootIndexFor(change3, transitionInfo)));
            if (z) {
                i = i5;
            } else {
                activityEmbeddingAnimationAdapter2.mOverrideLayer = i5;
                i = i5 + 1;
            }
            arrayList4.add(activityEmbeddingAnimationAdapter2);
            arrayList3 = arrayList4;
        }
        return arrayList3;
    }

    public static SurfaceControl getOrCreateScreenshot(TransitionInfo.Change change, TransitionInfo.Change change2, SurfaceControl.Transaction transaction) {
        SurfaceControl snapshot = change.getSnapshot();
        if (snapshot != null) {
            transaction.reparent(snapshot, change2.getLeash());
            return snapshot;
        }
        Rect rect = new Rect(change.getStartAbsBounds());
        rect.offsetTo(0, 0);
        return ScreenshotUtils.takeScreenshot(transaction, change.getLeash(), change2.getLeash(), rect, Integer.MAX_VALUE);
    }

    public static boolean shouldUseJumpCutForAnimation(Animation animation) {
        return animation.getDuration() == 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:154:0x014c, code lost:
    
        if (r6.getEndAbsBounds().equals(r10.getStartAbsBounds()) != false) goto L72;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0618 A[LOOP:1: B:28:0x058f->B:36:0x0618, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x05bf A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r10v10, types: [int] */
    /* JADX WARN: Type inference failed for: r10v24 */
    /* JADX WARN: Type inference failed for: r10v27, types: [int] */
    /* JADX WARN: Type inference failed for: r10v73 */
    /* JADX WARN: Type inference failed for: r10v76 */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r11v10 */
    /* JADX WARN: Type inference failed for: r11v11, types: [int] */
    /* JADX WARN: Type inference failed for: r11v15 */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r11v4, types: [int] */
    /* JADX WARN: Type inference failed for: r11v8 */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.animation.Animator createAnimator(android.window.TransitionInfo r24, android.view.SurfaceControl.Transaction r25, android.view.SurfaceControl.Transaction r26, final java.lang.Runnable r27, java.util.List<java.util.function.Consumer<android.view.SurfaceControl.Transaction>> r28) {
        /*
            Method dump skipped, instructions count: 1680
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.activityembedding.ActivityEmbeddingAnimationRunner.createAnimator(android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.view.SurfaceControl$Transaction, java.lang.Runnable, java.util.List):android.animation.Animator");
    }
}
