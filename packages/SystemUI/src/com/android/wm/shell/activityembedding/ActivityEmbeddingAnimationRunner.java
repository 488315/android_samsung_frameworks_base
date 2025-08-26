package com.android.wm.shell.activityembedding;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.ArraySet;
import android.util.Log;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.wm.shell.activityembedding.ActivityEmbeddingAnimationAdapter;
import com.android.wm.shell.common.ScreenshotUtils;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.transition.TransitionAnimationHelper;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0517  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0597  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x05c8  */
    /* JADX WARN: Type inference failed for: r18v1 */
    /* JADX WARN: Type inference failed for: r18v2 */
    /* JADX WARN: Type inference failed for: r18v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Animator createAnimator(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, final Runnable runnable, List<Consumer<SurfaceControl.Transaction>> list) {
        ActivityEmbeddingAnimationRunner activityEmbeddingAnimationRunner;
        int i;
        int i2;
        final List<ActivityEmbeddingAnimationAdapter> listCreateOpenCloseAnimationAdapters;
        int i3;
        Rect rect;
        Animation animationLoadCustomAnimation;
        int iHeight;
        int iWidth;
        TranslateAnimation translateAnimation;
        int iHeight2;
        int iWidth2;
        Rect rect2;
        TransitionInfo.Change change;
        Iterator it;
        Boolean bool;
        Animation[] animationArr;
        ?? r18;
        ArrayList arrayList;
        Animation animationLoadCustomAnimation2;
        TransitionInfo.Change change2;
        SurfaceControl rootLeash;
        ActivityEmbeddingAnimationRunner activityEmbeddingAnimationRunner2 = this;
        TransitionInfo transitionInfo2 = transitionInfo;
        char c = 0;
        int i4 = 2;
        boolean z = true;
        int i5 = 6;
        if (transitionInfo2.getType() != 1017) {
            boolean z2 = false;
            for (TransitionInfo.Change change3 : transitionInfo2.getChanges()) {
                if (change3.hasFlags(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT)) {
                    listCreateOpenCloseAnimationAdapters = new ArrayList();
                } else if (!z2 && change3.getMode() == 6 && !change3.getStartAbsBounds().equals(change3.getEndAbsBounds())) {
                    z2 = true;
                }
            }
            if (z2) {
                ArrayList arrayList2 = new ArrayList();
                for (TransitionInfo.Change change4 : transitionInfo2.getChanges()) {
                    if (change4.getMode() == 6 && !change4.getStartAbsBounds().equals(change4.getEndAbsBounds())) {
                        arrayList2.add(change4);
                        WindowContainerToken parent = change4.getParent();
                        if (parent != null && (change2 = transitionInfo2.getChange(parent)) != null && TransitionUtil.isOpeningType(change2.getMode())) {
                            arrayList2.add(change2);
                        }
                    }
                }
                if (!arrayList2.isEmpty()) {
                    ArrayList arrayList3 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList();
                    for (TransitionInfo.Change change5 : transitionInfo2.getChanges()) {
                        if (!arrayList2.contains(change5) && (change5.getParent() == null || !arrayList2.contains(transitionInfo2.getChange(change5.getParent())))) {
                            if (TransitionUtil.isOpeningType(change5.getMode())) {
                                arrayList3.add(change5);
                            } else if (TransitionUtil.isClosingType(change5.getMode())) {
                                arrayList4.add(change5);
                            }
                        }
                    }
                    i = -1;
                    if (!arrayList3.isEmpty() && !arrayList4.isEmpty()) {
                        if (arrayList2.size() == 1 && arrayList3.size() == 1 && arrayList4.size() == 1) {
                            TransitionInfo.Change change6 = (TransitionInfo.Change) arrayList2.get(0);
                            TransitionInfo.Change change7 = (TransitionInfo.Change) arrayList3.get(0);
                            TransitionInfo.Change change8 = (TransitionInfo.Change) arrayList4.get(0);
                            if (!change6.getStartAbsBounds().equals(change7.getEndAbsBounds()) || !change6.getEndAbsBounds().equals(change8.getStartAbsBounds())) {
                            }
                            float[] fArr = new float[i3];
                            // fill-array-data instruction
                            fArr[0] = 0.0f;
                            fArr[1] = 1.0f;
                            final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(fArr);
                            long jMax = 0;
                            if (listCreateOpenCloseAnimationAdapters.isEmpty()) {
                                for (TransitionInfo.Change change9 : transitionInfo2.getChanges()) {
                                    SurfaceControl leash = change9.getLeash();
                                    if (change9.getParent() != null) {
                                        transaction.setPosition(leash, change9.getEndRelOffset().x, change9.getEndRelOffset().y);
                                    } else {
                                        TransitionInfo.Root root = transitionInfo2.getRoot(TransitionUtil.rootIndexFor(change9, transitionInfo2));
                                        transaction.setPosition(leash, change9.getEndAbsBounds().left - root.getOffset().x, change9.getEndAbsBounds().top - root.getOffset().y);
                                    }
                                    transaction.setWindowCrop(leash, change9.getEndAbsBounds().width(), change9.getEndAbsBounds().height());
                                    if (change9.getMode() == 2) {
                                        transaction.hide(leash);
                                    } else {
                                        transaction.show(leash);
                                        transaction.setAlpha(leash, 1.0f);
                                    }
                                }
                            } else {
                                Iterator it2 = listCreateOpenCloseAnimationAdapters.iterator();
                                while (true) {
                                    if (!it2.hasNext()) {
                                        break;
                                    }
                                    ActivityEmbeddingAnimationAdapter activityEmbeddingAnimationAdapter = (ActivityEmbeddingAnimationAdapter) it2.next();
                                    TransitionInfo.Change change10 = activityEmbeddingAnimationAdapter.mChange;
                                    Animation animation = activityEmbeddingAnimationAdapter.mAnimation;
                                    if (animation.getShowBackdrop()) {
                                        int backdropColor = animation.getBackdropColor() != 0 ? animation.getBackdropColor() : change10.getBackgroundColor() != 0 ? change10.getBackgroundColor() : i2;
                                        if (backdropColor != 0) {
                                            if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION) {
                                                int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo2, 1);
                                                SurfaceControl leash2 = null;
                                                while (true) {
                                                    if (iM < 0) {
                                                        rootLeash = leash2;
                                                        break;
                                                    }
                                                    TransitionInfo.Change change11 = (TransitionInfo.Change) transitionInfo2.getChanges().get(iM);
                                                    if (change11.getMode() == 6) {
                                                        rootLeash = null;
                                                        break;
                                                    }
                                                    if (change11.hasFlags(4)) {
                                                        break;
                                                    }
                                                    if ((TransitionUtil.isOpeningType(transitionInfo2.getType()) && TransitionUtil.isClosingType(change11.getMode())) || (TransitionUtil.isClosingType(transitionInfo2.getType()) && TransitionUtil.isOpeningType(change11.getMode()))) {
                                                        leash2 = change11.getLeash();
                                                    }
                                                    iM--;
                                                }
                                                if (rootLeash == null) {
                                                    rootLeash = transitionInfo2.getRootLeash();
                                                }
                                                TransitionAnimationHelper.addBackgroundToTransition(rootLeash, backdropColor, transaction, transaction2);
                                            } else {
                                                TransitionAnimationHelper.addBackgroundToTransition(transitionInfo2.getRootLeash(), backdropColor, transaction, transaction2);
                                            }
                                        }
                                    }
                                }
                                Iterator it3 = listCreateOpenCloseAnimationAdapters.iterator();
                                while (it3.hasNext()) {
                                    jMax = Math.max(jMax, ((ActivityEmbeddingAnimationAdapter) it3.next()).mAnimation.computeDurationHint());
                                }
                                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.activityembedding.ActivityEmbeddingAnimationRunner$$ExternalSyntheticLambda0
                                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        List<ActivityEmbeddingAnimationAdapter> list2 = listCreateOpenCloseAnimationAdapters;
                                        ValueAnimator valueAnimator2 = valueAnimatorOfFloat;
                                        SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
                                        transaction3.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
                                        for (ActivityEmbeddingAnimationAdapter activityEmbeddingAnimationAdapter2 : list2) {
                                            long currentPlayTime = valueAnimator2.getCurrentPlayTime();
                                            activityEmbeddingAnimationAdapter2.mTransformation.clear();
                                            Animation animation2 = activityEmbeddingAnimationAdapter2.mAnimation;
                                            animation2.getTransformation(Math.min(currentPlayTime, animation2.getDuration()), activityEmbeddingAnimationAdapter2.mTransformation);
                                            activityEmbeddingAnimationAdapter2.onAnimationUpdateInner(transaction3);
                                        }
                                        transaction3.apply();
                                    }
                                });
                                transaction.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
                                for (ActivityEmbeddingAnimationAdapter activityEmbeddingAnimationAdapter2 : listCreateOpenCloseAnimationAdapters) {
                                    transaction.show(activityEmbeddingAnimationAdapter2.mLeash);
                                    int i6 = activityEmbeddingAnimationAdapter2.mOverrideLayer;
                                    int i7 = i;
                                    if (i6 != i7) {
                                        transaction.setLayer(activityEmbeddingAnimationAdapter2.mLeash, i6);
                                    }
                                    activityEmbeddingAnimationAdapter2.mAnimation.getTransformationAt(0.0f, activityEmbeddingAnimationAdapter2.mTransformation);
                                    activityEmbeddingAnimationAdapter2.onAnimationUpdateInner(transaction);
                                    i = i7;
                                }
                            }
                            valueAnimatorOfFloat.setDuration(jMax);
                            valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.activityembedding.ActivityEmbeddingAnimationRunner.1
                                @Override // android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
                                    Iterator it4 = listCreateOpenCloseAnimationAdapters.iterator();
                                    while (it4.hasNext()) {
                                        ((ActivityEmbeddingAnimationAdapter) it4.next()).onAnimationEnd(transaction3);
                                    }
                                    transaction3.apply();
                                    ActivityEmbeddingAnimationRunner.this.mActiveAnimator = null;
                                    runnable.run();
                                }

                                @Override // android.animation.Animator.AnimatorListener
                                public final void onAnimationCancel(Animator animator) {
                                }

                                @Override // android.animation.Animator.AnimatorListener
                                public final void onAnimationRepeat(Animator animator) {
                                }

                                @Override // android.animation.Animator.AnimatorListener
                                public final void onAnimationStart(Animator animator) {
                                }
                            });
                            return valueAnimatorOfFloat;
                        }
                    }
                    ArrayList arrayList5 = new ArrayList();
                    ArraySet arraySet = new ArraySet();
                    Rect rect3 = new Rect();
                    Iterator it4 = transitionInfo2.getChanges().iterator();
                    Animation animation2 = null;
                    Boolean boolValueOf = null;
                    loop8: while (true) {
                        boolean z3 = z;
                        ArrayList arrayList6 = arrayList5;
                        if (it4.hasNext()) {
                            TransitionInfo.Change change12 = (TransitionInfo.Change) it4.next();
                            if (change12.getMode() != i5 || change12.getStartAbsBounds().equals(change12.getEndAbsBounds())) {
                                rect2 = rect3;
                                arrayList5 = arrayList6;
                                it4 = it4;
                            } else {
                                arraySet.add(change12);
                                WindowContainerToken parent2 = change12.getParent();
                                if (parent2 == null || (change = transitionInfo2.getChange(parent2)) == null || !TransitionUtil.isOpeningType(change.getMode())) {
                                    change = change12;
                                } else {
                                    arraySet.add(change);
                                }
                                TransitionInfo.AnimationOptions animationOptions = change.getAnimationOptions();
                                if (animationOptions != null && (animationLoadCustomAnimation2 = activityEmbeddingAnimationRunner2.mAnimationSpec.loadCustomAnimation(animationOptions, i5)) != null) {
                                    boolValueOf = Boolean.valueOf(animationLoadCustomAnimation2.getShowBackdrop());
                                }
                                calculateParentBounds(change12, rect3);
                                ActivityEmbeddingAnimationSpec activityEmbeddingAnimationSpec = activityEmbeddingAnimationRunner2.mAnimationSpec;
                                activityEmbeddingAnimationSpec.getClass();
                                float f = TransitionUtil.isClosingType(change12.getMode()) ? 0.0f : 1.0f;
                                AlphaAnimation alphaAnimation = new AlphaAnimation(f, f);
                                Animation animationLoadCustomAnimation3 = activityEmbeddingAnimationSpec.loadCustomAnimation(change12.getAnimationOptions(), i5);
                                if (animationLoadCustomAnimation3 != null) {
                                    animationArr = new Animation[i4];
                                    animationArr[c] = alphaAnimation;
                                    animationArr[z3 ? 1 : 0] = animationLoadCustomAnimation3;
                                    rect2 = rect3;
                                    it = it4;
                                    bool = boolValueOf;
                                    r18 = z3;
                                } else {
                                    Rect startAbsBounds = change12.getStartAbsBounds();
                                    Rect endAbsBounds = change12.getEndAbsBounds();
                                    float fWidth = startAbsBounds.width() / endAbsBounds.width();
                                    float fHeight = startAbsBounds.height() / endAbsBounds.height();
                                    float f2 = 1.0f / fWidth;
                                    it = it4;
                                    float f3 = 1.0f / fHeight;
                                    bool = boolValueOf;
                                    AnimationSet animationSet = new AnimationSet(false);
                                    rect2 = rect3;
                                    AlphaAnimation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
                                    alphaAnimation2.setInterpolator(activityEmbeddingAnimationSpec.mLinearInterpolator);
                                    alphaAnimation2.setDuration(80L);
                                    alphaAnimation2.setStartOffset(30L);
                                    animationSet.addAnimation(alphaAnimation2);
                                    ScaleAnimation scaleAnimation = new ScaleAnimation(f2, f2, f3, f3);
                                    scaleAnimation.setInterpolator(activityEmbeddingAnimationSpec.mFastOutExtraSlowInInterpolator);
                                    scaleAnimation.setDuration(517L);
                                    animationSet.addAnimation(scaleAnimation);
                                    animationSet.initialize(startAbsBounds.width(), startAbsBounds.height(), endAbsBounds.width(), endAbsBounds.height());
                                    animationSet.scaleCurrentDuration(activityEmbeddingAnimationSpec.mTransitionAnimationScaleSetting);
                                    AnimationSet animationSet2 = new AnimationSet(z3);
                                    animationSet2.setInterpolator(activityEmbeddingAnimationSpec.mFastOutExtraSlowInInterpolator);
                                    ScaleAnimation scaleAnimation2 = new ScaleAnimation(fWidth, 1.0f, fHeight, 1.0f);
                                    scaleAnimation2.setDuration(517L);
                                    animationSet2.addAnimation(scaleAnimation2);
                                    TranslateAnimation translateAnimation2 = new TranslateAnimation(startAbsBounds.left - endAbsBounds.left, 0.0f, startAbsBounds.top - endAbsBounds.top, 0.0f);
                                    translateAnimation2.setDuration(517L);
                                    animationSet2.addAnimation(translateAnimation2);
                                    animationSet2.initialize(startAbsBounds.width(), startAbsBounds.height(), rect2.width(), rect2.height());
                                    animationSet2.scaleCurrentDuration(activityEmbeddingAnimationSpec.mTransitionAnimationScaleSetting);
                                    r18 = 1;
                                    animationArr = new Animation[]{animationSet, animationSet2};
                                }
                                for (Animation animation3 : animationArr) {
                                    if (shouldUseJumpCutForAnimation(animation3)) {
                                        listCreateOpenCloseAnimationAdapters = new ArrayList();
                                        i2 = 0;
                                        i3 = 2;
                                        activityEmbeddingAnimationRunner = this;
                                        transitionInfo2 = transitionInfo;
                                        break loop8;
                                    }
                                }
                                animation2 = animationArr[r18];
                                SurfaceControl orCreateScreenshot = getOrCreateScreenshot(change12, change, transaction);
                                transitionInfo2 = transitionInfo;
                                TransitionInfo.Root root2 = transitionInfo2.getRoot(TransitionUtil.rootIndexFor(change12, transitionInfo2));
                                if (orCreateScreenshot != null) {
                                    ActivityEmbeddingAnimationAdapter.SnapshotAdapter snapshotAdapter = new ActivityEmbeddingAnimationAdapter.SnapshotAdapter(animationArr[0], change12, orCreateScreenshot, root2);
                                    arrayList = arrayList6;
                                    arrayList.add(snapshotAdapter);
                                } else {
                                    arrayList = arrayList6;
                                    Log.e("ActivityEmbeddingAnimR", "Failed to take screenshot for change=" + change12);
                                }
                                arrayList.add(new ActivityEmbeddingAnimationAdapter.BoundsChangeAdapter(animationArr[1], change, root2));
                                arrayList5 = arrayList;
                                it4 = it;
                                boolValueOf = bool;
                            }
                            rect3 = rect2;
                            c = 0;
                            i4 = 2;
                            z = true;
                            i5 = 6;
                            activityEmbeddingAnimationRunner2 = this;
                        } else {
                            Rect rect4 = rect3;
                            if (rect4.isEmpty()) {
                                throw new IllegalStateException("There should be at least one changing window to play the change animation");
                            }
                            Iterator it5 = transitionInfo2.getChanges().iterator();
                            boolean zBooleanValue = true;
                            while (true) {
                                if (it5.hasNext()) {
                                    TransitionInfo.Change change13 = (TransitionInfo.Change) it5.next();
                                    if (!arraySet.contains(change13)) {
                                        if ((change13.getParent() != null && arraySet.contains(transitionInfo2.getChange(change13.getParent()))) || change13.getMode() == 6) {
                                            float f4 = TransitionUtil.isClosingType(change13.getMode()) ? 0.0f : 1.0f;
                                            animationLoadCustomAnimation = new AlphaAnimation(f4, f4);
                                            activityEmbeddingAnimationRunner = this;
                                            rect = rect4;
                                        } else if (TransitionUtil.isClosingType(change13.getMode())) {
                                            activityEmbeddingAnimationRunner = this;
                                            ActivityEmbeddingAnimationSpec activityEmbeddingAnimationSpec2 = activityEmbeddingAnimationRunner.mAnimationSpec;
                                            activityEmbeddingAnimationSpec2.getClass();
                                            animationLoadCustomAnimation = activityEmbeddingAnimationSpec2.loadCustomAnimation(change13.getAnimationOptions(), 6);
                                            if (animationLoadCustomAnimation != null) {
                                                rect = rect4;
                                                zBooleanValue = false;
                                            } else {
                                                Rect startAbsBounds2 = change13.getStartAbsBounds();
                                                rect = rect4;
                                                int i8 = rect.top;
                                                int i9 = startAbsBounds2.top;
                                                if (i8 == i9 && rect.bottom == startAbsBounds2.bottom) {
                                                    iWidth2 = rect.left == startAbsBounds2.left ? -startAbsBounds2.width() : startAbsBounds2.width();
                                                    iHeight2 = 0;
                                                } else {
                                                    iHeight2 = i8 == i9 ? -startAbsBounds2.height() : startAbsBounds2.height();
                                                    iWidth2 = 0;
                                                }
                                                translateAnimation = new TranslateAnimation(0.0f, iWidth2, 0.0f, iHeight2);
                                                translateAnimation.setInterpolator(activityEmbeddingAnimationSpec2.mFastOutExtraSlowInInterpolator);
                                                translateAnimation.setDuration(517L);
                                                translateAnimation.initialize(startAbsBounds2.width(), startAbsBounds2.height(), startAbsBounds2.width(), startAbsBounds2.height());
                                                translateAnimation.scaleCurrentDuration(activityEmbeddingAnimationSpec2.mTransitionAnimationScaleSetting);
                                                animationLoadCustomAnimation = translateAnimation;
                                                zBooleanValue = false;
                                            }
                                        } else {
                                            activityEmbeddingAnimationRunner = this;
                                            rect = rect4;
                                            ActivityEmbeddingAnimationSpec activityEmbeddingAnimationSpec3 = activityEmbeddingAnimationRunner.mAnimationSpec;
                                            activityEmbeddingAnimationSpec3.getClass();
                                            animationLoadCustomAnimation = activityEmbeddingAnimationSpec3.loadCustomAnimation(change13.getAnimationOptions(), 6);
                                            if (animationLoadCustomAnimation == null) {
                                                Rect endAbsBounds2 = change13.getEndAbsBounds();
                                                int i10 = rect.top;
                                                int i11 = endAbsBounds2.top;
                                                if (i10 == i11 && rect.bottom == endAbsBounds2.bottom) {
                                                    iWidth = rect.left == endAbsBounds2.left ? -endAbsBounds2.width() : endAbsBounds2.width();
                                                    iHeight = 0;
                                                } else {
                                                    iHeight = i10 == i11 ? -endAbsBounds2.height() : endAbsBounds2.height();
                                                    iWidth = 0;
                                                }
                                                translateAnimation = new TranslateAnimation(iWidth, 0.0f, iHeight, 0.0f);
                                                translateAnimation.setInterpolator(activityEmbeddingAnimationSpec3.mFastOutExtraSlowInInterpolator);
                                                translateAnimation.setDuration(517L);
                                                translateAnimation.initialize(endAbsBounds2.width(), endAbsBounds2.height(), endAbsBounds2.width(), endAbsBounds2.height());
                                                translateAnimation.scaleCurrentDuration(activityEmbeddingAnimationSpec3.mTransitionAnimationScaleSetting);
                                                animationLoadCustomAnimation = translateAnimation;
                                            }
                                            zBooleanValue = false;
                                        }
                                        if (shouldUseJumpCutForAnimation(animationLoadCustomAnimation)) {
                                            listCreateOpenCloseAnimationAdapters = new ArrayList();
                                            break;
                                        }
                                        arrayList6.add(new ActivityEmbeddingAnimationAdapter(animationLoadCustomAnimation, change13, transitionInfo2.getRoot(TransitionUtil.rootIndexFor(change13, transitionInfo2))));
                                        rect4 = rect;
                                    }
                                } else {
                                    activityEmbeddingAnimationRunner = this;
                                    if (boolValueOf != null) {
                                        zBooleanValue = boolValueOf.booleanValue();
                                    }
                                    if (zBooleanValue && animation2 != null) {
                                        animation2.setShowBackdrop(true);
                                    }
                                    listCreateOpenCloseAnimationAdapters = arrayList6;
                                }
                            }
                            i2 = 0;
                        }
                    }
                    float[] fArr2 = new float[i3];
                    // fill-array-data instruction
                    fArr2[0] = 0.0f;
                    fArr2[1] = 1.0f;
                    final ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(fArr2);
                    long jMax2 = 0;
                    if (listCreateOpenCloseAnimationAdapters.isEmpty()) {
                    }
                    valueAnimatorOfFloat2.setDuration(jMax2);
                    valueAnimatorOfFloat2.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.activityembedding.ActivityEmbeddingAnimationRunner.1
                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
                            Iterator it42 = listCreateOpenCloseAnimationAdapters.iterator();
                            while (it42.hasNext()) {
                                ((ActivityEmbeddingAnimationAdapter) it42.next()).onAnimationEnd(transaction3);
                            }
                            transaction3.apply();
                            ActivityEmbeddingAnimationRunner.this.mActiveAnimator = null;
                            runnable.run();
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationCancel(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationRepeat(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                        }
                    });
                    return valueAnimatorOfFloat2;
                }
                i = -1;
                listCreateOpenCloseAnimationAdapters = new ArrayList();
                activityEmbeddingAnimationRunner = activityEmbeddingAnimationRunner2;
                i2 = 0;
                i3 = 2;
                float[] fArr22 = new float[i3];
                // fill-array-data instruction
                fArr22[0] = 0.0f;
                fArr22[1] = 1.0f;
                final ValueAnimator valueAnimatorOfFloat22 = ValueAnimator.ofFloat(fArr22);
                long jMax22 = 0;
                if (listCreateOpenCloseAnimationAdapters.isEmpty()) {
                }
                valueAnimatorOfFloat22.setDuration(jMax22);
                valueAnimatorOfFloat22.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.activityembedding.ActivityEmbeddingAnimationRunner.1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
                        Iterator it42 = listCreateOpenCloseAnimationAdapters.iterator();
                        while (it42.hasNext()) {
                            ((ActivityEmbeddingAnimationAdapter) it42.next()).onAnimationEnd(transaction3);
                        }
                        transaction3.apply();
                        ActivityEmbeddingAnimationRunner.this.mActiveAnimator = null;
                        runnable.run();
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                    }
                });
                return valueAnimatorOfFloat22;
            }
            activityEmbeddingAnimationRunner = activityEmbeddingAnimationRunner2;
            i = -1;
            if (TransitionUtil.isClosingType(transitionInfo2.getType())) {
                ActivityEmbeddingAnimationSpec activityEmbeddingAnimationSpec4 = activityEmbeddingAnimationRunner.mAnimationSpec;
                Objects.requireNonNull(activityEmbeddingAnimationSpec4);
                ActivityEmbeddingAnimationRunner$$ExternalSyntheticLambda1 activityEmbeddingAnimationRunner$$ExternalSyntheticLambda1 = new ActivityEmbeddingAnimationRunner$$ExternalSyntheticLambda1(activityEmbeddingAnimationSpec4, 1);
                i2 = 0;
                listCreateOpenCloseAnimationAdapters = createOpenCloseAnimationAdapters(transitionInfo2, false, activityEmbeddingAnimationRunner$$ExternalSyntheticLambda1, transaction);
            } else {
                i2 = 0;
                ActivityEmbeddingAnimationSpec activityEmbeddingAnimationSpec5 = activityEmbeddingAnimationRunner.mAnimationSpec;
                Objects.requireNonNull(activityEmbeddingAnimationSpec5);
                listCreateOpenCloseAnimationAdapters = createOpenCloseAnimationAdapters(transitionInfo2, true, new ActivityEmbeddingAnimationRunner$$ExternalSyntheticLambda1(activityEmbeddingAnimationSpec5, 0), transaction);
            }
            i3 = 2;
            float[] fArr222 = new float[i3];
            // fill-array-data instruction
            fArr222[0] = 0.0f;
            fArr222[1] = 1.0f;
            final ValueAnimator valueAnimatorOfFloat222 = ValueAnimator.ofFloat(fArr222);
            long jMax222 = 0;
            if (listCreateOpenCloseAnimationAdapters.isEmpty()) {
            }
            valueAnimatorOfFloat222.setDuration(jMax222);
            valueAnimatorOfFloat222.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.activityembedding.ActivityEmbeddingAnimationRunner.1
                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
                    Iterator it42 = listCreateOpenCloseAnimationAdapters.iterator();
                    while (it42.hasNext()) {
                        ((ActivityEmbeddingAnimationAdapter) it42.next()).onAnimationEnd(transaction3);
                    }
                    transaction3.apply();
                    ActivityEmbeddingAnimationRunner.this.mActiveAnimator = null;
                    runnable.run();
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                }
            });
            return valueAnimatorOfFloat222;
        }
        listCreateOpenCloseAnimationAdapters = new ArrayList();
        activityEmbeddingAnimationRunner = activityEmbeddingAnimationRunner2;
        i2 = 0;
        i3 = 2;
        i = -1;
        float[] fArr2222 = new float[i3];
        // fill-array-data instruction
        fArr2222[0] = 0.0f;
        fArr2222[1] = 1.0f;
        final ValueAnimator valueAnimatorOfFloat2222 = ValueAnimator.ofFloat(fArr2222);
        long jMax2222 = 0;
        if (listCreateOpenCloseAnimationAdapters.isEmpty()) {
        }
        valueAnimatorOfFloat2222.setDuration(jMax2222);
        valueAnimatorOfFloat2222.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.activityembedding.ActivityEmbeddingAnimationRunner.1
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
                Iterator it42 = listCreateOpenCloseAnimationAdapters.iterator();
                while (it42.hasNext()) {
                    ((ActivityEmbeddingAnimationAdapter) it42.next()).onAnimationEnd(transaction3);
                }
                transaction3.apply();
                ActivityEmbeddingAnimationRunner.this.mActiveAnimator = null;
                runnable.run();
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        return valueAnimatorOfFloat2222;
    }
}
