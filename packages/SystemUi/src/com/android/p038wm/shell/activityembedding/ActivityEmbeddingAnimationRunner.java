package com.android.p038wm.shell.activityembedding;

import android.R;
import android.animation.Animator;
import android.content.Context;
import android.graphics.Rect;
import android.util.ArraySet;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ClipRectAnimation;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import com.android.internal.policy.TransitionAnimation;
import com.android.p038wm.shell.activityembedding.ActivityEmbeddingAnimationAdapter;
import com.android.p038wm.shell.common.ScreenshotUtils;
import com.android.p038wm.shell.transition.TransitionAnimationHelper;
import com.android.p038wm.shell.util.TransitionUtil;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ActivityEmbeddingAnimationRunner {
    public Animator mActiveAnimator;
    final ActivityEmbeddingAnimationSpec mAnimationSpec;
    public final ActivityEmbeddingController mController;

    public ActivityEmbeddingAnimationRunner(Context context, ActivityEmbeddingController activityEmbeddingController) {
        this.mController = activityEmbeddingController;
        this.mAnimationSpec = new ActivityEmbeddingAnimationSpec(context);
    }

    public static ActivityEmbeddingAnimationAdapter createOpenCloseAnimationAdapter(TransitionInfo transitionInfo, TransitionInfo.Change change, ActivityEmbeddingAnimationRunner$$ExternalSyntheticLambda1 activityEmbeddingAnimationRunner$$ExternalSyntheticLambda1, Rect rect) {
        Animation loadDefaultAnimationRes;
        int i = activityEmbeddingAnimationRunner$$ExternalSyntheticLambda1.$r8$classId;
        ActivityEmbeddingAnimationSpec activityEmbeddingAnimationSpec = activityEmbeddingAnimationRunner$$ExternalSyntheticLambda1.f$0;
        switch (i) {
            case 0:
                activityEmbeddingAnimationSpec.getClass();
                boolean isOpeningType = TransitionUtil.isOpeningType(change.getMode());
                TransitionAnimation transitionAnimation = activityEmbeddingAnimationSpec.mTransitionAnimation;
                Animation loadAttributeAnimation = TransitionAnimationHelper.loadAttributeAnimation(transitionInfo, change, 0, transitionAnimation, false);
                if (loadAttributeAnimation != null && loadAttributeAnimation.getShowBackdrop()) {
                    loadDefaultAnimationRes = transitionAnimation.loadDefaultAnimationRes(isOpeningType ? 17432935 : 17432936);
                } else if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION) {
                    loadDefaultAnimationRes = transitionAnimation.loadDefaultAnimationRes(isOpeningType ? R.anim.ft_avd_toarrow_rectangle_path_1_animation : R.anim.ft_avd_toarrow_rectangle_path_2_animation);
                } else {
                    loadDefaultAnimationRes = transitionAnimation.loadDefaultAnimationRes(isOpeningType ? R.anim.activity_close_enter : R.anim.activity_close_exit);
                }
                loadDefaultAnimationRes.initialize(rect.width(), rect.height(), rect.width(), rect.height());
                loadDefaultAnimationRes.scaleCurrentDuration(activityEmbeddingAnimationSpec.mTransitionAnimationScaleSetting);
                break;
            default:
                activityEmbeddingAnimationSpec.getClass();
                boolean isOpeningType2 = TransitionUtil.isOpeningType(change.getMode());
                TransitionAnimation transitionAnimation2 = activityEmbeddingAnimationSpec.mTransitionAnimation;
                Animation loadAttributeAnimation2 = TransitionAnimationHelper.loadAttributeAnimation(transitionInfo, change, 0, transitionAnimation2, false);
                if (loadAttributeAnimation2 != null && loadAttributeAnimation2.getShowBackdrop()) {
                    loadDefaultAnimationRes = transitionAnimation2.loadDefaultAnimationRes(isOpeningType2 ? 17432937 : 17432938);
                } else if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION) {
                    loadDefaultAnimationRes = transitionAnimation2.loadDefaultAnimationRes(isOpeningType2 ? R.anim.ft_avd_toarrow_rectangle_path_3_animation : R.anim.ft_avd_toarrow_rectangle_path_4_animation);
                } else {
                    loadDefaultAnimationRes = transitionAnimation2.loadDefaultAnimationRes(isOpeningType2 ? R.anim.activity_open_enter : R.anim.activity_open_exit);
                }
                loadDefaultAnimationRes.initialize(rect.width(), rect.height(), rect.width(), rect.height());
                loadDefaultAnimationRes.scaleCurrentDuration(activityEmbeddingAnimationSpec.mTransitionAnimationScaleSetting);
                break;
        }
        return new ActivityEmbeddingAnimationAdapter(loadDefaultAnimationRes, change, change.getLeash(), rect, transitionInfo.getRoot(TransitionUtil.rootIndexFor(change, transitionInfo)));
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
        Iterator it = arrayList.iterator();
        int i = 1000;
        while (it.hasNext()) {
            ActivityEmbeddingAnimationAdapter createOpenCloseAnimationAdapter = createOpenCloseAnimationAdapter(transitionInfo, (TransitionInfo.Change) it.next(), activityEmbeddingAnimationRunner$$ExternalSyntheticLambda1, rect);
            if (z) {
                createOpenCloseAnimationAdapter.mOverrideLayer = i;
                i++;
            }
            arrayList3.add(createOpenCloseAnimationAdapter);
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            TransitionInfo.Change change2 = (TransitionInfo.Change) it2.next();
            if ((!TransitionUtil.isClosingType(change2.getMode()) ? false : !change2.getStartAbsBounds().equals(change2.getEndAbsBounds())) && (orCreateScreenshot = getOrCreateScreenshot(change2, change2, transaction)) != null) {
                ActivityEmbeddingAnimationAdapter.SnapshotAdapter snapshotAdapter = new ActivityEmbeddingAnimationAdapter.SnapshotAdapter(new AlphaAnimation(1.0f, 1.0f), change2, orCreateScreenshot, transitionInfo.getRoot(TransitionUtil.rootIndexFor(change2, transitionInfo)));
                if (!z) {
                    snapshotAdapter.mOverrideLayer = i;
                    i++;
                }
                arrayList3.add(snapshotAdapter);
            }
            ActivityEmbeddingAnimationAdapter createOpenCloseAnimationAdapter2 = createOpenCloseAnimationAdapter(transitionInfo, change2, activityEmbeddingAnimationRunner$$ExternalSyntheticLambda1, rect2);
            if (!z) {
                createOpenCloseAnimationAdapter2.mOverrideLayer = i;
                i++;
            }
            arrayList3.add(createOpenCloseAnimationAdapter2);
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

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0440, code lost:
    
        r5 = android.animation.ValueAnimator.ofFloat(0.0f, 1.0f);
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0450, code lost:
    
        if (r2.isEmpty() == false) goto L156;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0452, code lost:
    
        r4 = r21.getChanges().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x045e, code lost:
    
        if (r4.hasNext() == false) goto L199;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0460, code lost:
    
        r6 = (android.window.TransitionInfo.Change) r4.next();
        r9 = r6.getLeash();
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x046e, code lost:
    
        if (r6.getParent() == null) goto L151;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0470, code lost:
    
        r1.setPosition(r9, r6.getEndRelOffset().x, r6.getEndRelOffset().y);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x04a9, code lost:
    
        r1.setWindowCrop(r9, r6.getEndAbsBounds().width(), r6.getEndAbsBounds().height());
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x04c0, code lost:
    
        if (r6.getMode() != 2) goto L198;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x04c6, code lost:
    
        r1.show(r9);
        r1.setAlpha(r9, 1.0f);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x04c2, code lost:
    
        r1.hide(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0482, code lost:
    
        r10 = r14.getRoot(com.android.p038wm.shell.util.TransitionUtil.rootIndexFor(r6, r14));
        r1.setPosition(r9, r6.getEndAbsBounds().left - r10.getOffset().x, r6.getEndAbsBounds().top - r10.getOffset().y);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x05c9, code lost:
    
        r5.setDuration(r7);
        r5.addListener(new com.android.p038wm.shell.activityembedding.ActivityEmbeddingAnimationRunner.C37691(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x05d6, code lost:
    
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x04cf, code lost:
    
        r6 = r2.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x04d7, code lost:
    
        if (r6.hasNext() == false) goto L205;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x04d9, code lost:
    
        r9 = (com.android.p038wm.shell.activityembedding.ActivityEmbeddingAnimationAdapter) r6.next();
        r10 = r9.mAnimation;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x04e5, code lost:
    
        if (r10.hasExtension() != false) goto L204;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x04e8, code lost:
    
        r9 = r9.mChange;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x04f2, code lost:
    
        if (com.android.p038wm.shell.util.TransitionUtil.isOpeningType(r9.getMode()) == false) goto L206;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0501, code lost:
    
        com.android.p038wm.shell.transition.TransitionAnimationHelper.edgeExtendWindow(r9, r10, r1, r23);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x04f4, code lost:
    
        r25.add(new com.android.p038wm.shell.activityembedding.ActivityEmbeddingAnimationRunner$$ExternalSyntheticLambda2(r9, r10, r23));
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0509, code lost:
    
        r6 = r2.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0513, code lost:
    
        if (r6.hasNext() == false) goto L212;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0515, code lost:
    
        r9 = (com.android.p038wm.shell.activityembedding.ActivityEmbeddingAnimationAdapter) r6.next();
        r9 = com.android.p038wm.shell.transition.TransitionAnimationHelper.getTransitionBackgroundColorIfSet(r14, r9.mChange, r9.mAnimation, 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0524, code lost:
    
        if (r9 == 0) goto L214;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0526, code lost:
    
        r4 = r21.getRootLeash();
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x052a, code lost:
    
        if (r9 != 0) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x052d, code lost:
    
        r6 = android.graphics.Color.valueOf(r9);
        r9 = new float[]{r6.red(), r6.green(), r6.blue()};
        r3 = new android.view.SurfaceControl.Builder().setName("Animation Background").setParent(r4).setColorLayer().setOpaque(true).build();
        r1.setLayer(r3, com.samsung.android.nexus.video.VideoPlayer.MEDIA_ERROR_SYSTEM).setColor(r3, r9).show(r3);
        r23.remove(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0572, code lost:
    
        r3 = r2.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x057a, code lost:
    
        if (r3.hasNext() == false) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x057c, code lost:
    
        r7 = java.lang.Math.max(r7, ((com.android.p038wm.shell.activityembedding.ActivityEmbeddingAnimationAdapter) r3.next()).mAnimation.computeDurationHint());
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x058d, code lost:
    
        r5.addUpdateListener(new com.android.p038wm.shell.activityembedding.ActivityEmbeddingAnimationRunner$$ExternalSyntheticLambda0(r2, r5));
        r1.setFrameTimelineVsync(android.view.Choreographer.getInstance().getVsyncId());
        r3 = r2.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x05a8, code lost:
    
        if (r3.hasNext() == false) goto L216;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x05aa, code lost:
    
        r4 = (com.android.p038wm.shell.activityembedding.ActivityEmbeddingAnimationAdapter) r3.next();
        r6 = r4.mLeash;
        r1.show(r6);
        r9 = r4.mOverrideLayer;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x05b8, code lost:
    
        if (r9 == (-1)) goto L218;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x05ba, code lost:
    
        r1.setLayer(r6, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x05bd, code lost:
    
        r4.mAnimation.getTransformationAt(0.0f, r4.mTransformation);
        r4.onAnimationUpdateInner(r1);
     */
    /* JADX WARN: Removed duplicated region for block: B:140:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x00f5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Animator createAnimator(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, final SurfaceControl.Transaction transaction2, final Runnable runnable, List<Consumer<SurfaceControl.Transaction>> list) {
        SurfaceControl.Transaction transaction3;
        final List createOpenCloseAnimationAdapters;
        TransitionInfo transitionInfo2;
        boolean z;
        ArrayList arrayList;
        Iterator it;
        int i;
        int i2;
        Rect rect;
        Animation animation;
        int i3;
        int i4;
        ArrayList arrayList2;
        ArraySet arraySet;
        Iterator it2;
        TransitionInfo transitionInfo3;
        SurfaceControl.Transaction transaction4;
        TransitionInfo.Change change;
        TransitionInfo.Change change2;
        ActivityEmbeddingAnimationRunner activityEmbeddingAnimationRunner = this;
        TransitionInfo transitionInfo4 = transitionInfo;
        SurfaceControl.Transaction transaction5 = transaction;
        Iterator it3 = transitionInfo.getChanges().iterator();
        boolean z2 = false;
        while (true) {
            int i5 = 6;
            if (it3.hasNext()) {
                TransitionInfo.Change change3 = (TransitionInfo.Change) it3.next();
                if (change3.hasFlags(16384)) {
                    transitionInfo2 = transitionInfo4;
                    transaction3 = transaction5;
                    createOpenCloseAnimationAdapters = new ArrayList();
                    break;
                }
                if (!z2 && change3.getMode() == 6 && !change3.getStartAbsBounds().equals(change3.getEndAbsBounds())) {
                    z2 = true;
                }
            } else if (z2) {
                ArrayList arrayList3 = new ArrayList();
                for (TransitionInfo.Change change4 : transitionInfo.getChanges()) {
                    if (change4.getMode() == 6 && !change4.getStartAbsBounds().equals(change4.getEndAbsBounds())) {
                        arrayList3.add(change4);
                        WindowContainerToken parent = change4.getParent();
                        if (parent != null && (change2 = transitionInfo4.getChange(parent)) != null && TransitionUtil.isOpeningType(change2.getMode())) {
                            arrayList3.add(change2);
                        }
                    }
                }
                if (!arrayList3.isEmpty()) {
                    boolean z3 = false;
                    boolean z4 = false;
                    for (TransitionInfo.Change change5 : transitionInfo.getChanges()) {
                        if (!arrayList3.contains(change5) && (change5.getParent() == null || !arrayList3.contains(transitionInfo4.getChange(change5.getParent())))) {
                            z3 |= TransitionUtil.isOpeningType(change5.getMode());
                            z4 |= TransitionUtil.isClosingType(change5.getMode());
                        }
                    }
                    if (!z3 || !z4) {
                        z = false;
                        if (z) {
                            ArrayList arrayList4 = new ArrayList();
                            ArraySet arraySet2 = new ArraySet();
                            Rect rect2 = new Rect();
                            Iterator it4 = transitionInfo.getChanges().iterator();
                            Animation animation2 = null;
                            ActivityEmbeddingAnimationRunner activityEmbeddingAnimationRunner2 = activityEmbeddingAnimationRunner;
                            transitionInfo2 = transitionInfo4;
                            TransitionInfo transitionInfo5 = transitionInfo2;
                            while (it4.hasNext()) {
                                TransitionInfo.Change change6 = (TransitionInfo.Change) it4.next();
                                if (change6.getMode() != i5 || change6.getStartAbsBounds().equals(change6.getEndAbsBounds())) {
                                    arrayList2 = arrayList4;
                                    arraySet = arraySet2;
                                    it2 = it4;
                                    transitionInfo3 = transitionInfo4;
                                    transaction4 = transaction5;
                                } else {
                                    arraySet2.add(change6);
                                    WindowContainerToken parent2 = change6.getParent();
                                    if (parent2 == null || (change = transitionInfo5.getChange(parent2)) == null || !TransitionUtil.isOpeningType(change.getMode())) {
                                        change = change6;
                                    } else {
                                        arraySet2.add(change);
                                    }
                                    rect2.union(change.getStartAbsBounds());
                                    rect2.union(change.getEndAbsBounds());
                                    if (change != change6) {
                                        rect2.union(change6.getStartAbsBounds());
                                    }
                                    ActivityEmbeddingAnimationSpec activityEmbeddingAnimationSpec = activityEmbeddingAnimationRunner2.mAnimationSpec;
                                    activityEmbeddingAnimationSpec.getClass();
                                    Rect startAbsBounds = change6.getStartAbsBounds();
                                    Rect endAbsBounds = change6.getEndAbsBounds();
                                    float width = startAbsBounds.width() / endAbsBounds.width();
                                    float height = startAbsBounds.height() / endAbsBounds.height();
                                    it2 = it4;
                                    float f = 1.0f / width;
                                    float f2 = 1.0f / height;
                                    AnimationSet animationSet = new AnimationSet(false);
                                    ArrayList arrayList5 = arrayList4;
                                    arraySet = arraySet2;
                                    AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                                    alphaAnimation.setInterpolator(activityEmbeddingAnimationSpec.mLinearInterpolator);
                                    alphaAnimation.setDuration(80L);
                                    alphaAnimation.setStartOffset(30L);
                                    animationSet.addAnimation(alphaAnimation);
                                    ScaleAnimation scaleAnimation = new ScaleAnimation(f, f, f2, f2);
                                    Interpolator interpolator = activityEmbeddingAnimationSpec.mFastOutExtraSlowInInterpolator;
                                    scaleAnimation.setInterpolator(interpolator);
                                    scaleAnimation.setDuration(517L);
                                    animationSet.addAnimation(scaleAnimation);
                                    animationSet.initialize(startAbsBounds.width(), startAbsBounds.height(), endAbsBounds.width(), endAbsBounds.height());
                                    animationSet.scaleCurrentDuration(activityEmbeddingAnimationSpec.mTransitionAnimationScaleSetting);
                                    AnimationSet animationSet2 = new AnimationSet(true);
                                    animationSet2.setInterpolator(interpolator);
                                    ScaleAnimation scaleAnimation2 = new ScaleAnimation(width, 1.0f, height, 1.0f);
                                    scaleAnimation2.setDuration(517L);
                                    animationSet2.addAnimation(scaleAnimation2);
                                    TranslateAnimation translateAnimation = new TranslateAnimation(startAbsBounds.left - endAbsBounds.left, 0.0f, startAbsBounds.top - endAbsBounds.top, 0.0f);
                                    translateAnimation.setDuration(517L);
                                    animationSet2.addAnimation(translateAnimation);
                                    Rect rect3 = new Rect(startAbsBounds);
                                    Rect rect4 = new Rect(endAbsBounds);
                                    rect3.offsetTo(0, 0);
                                    rect4.offsetTo(0, 0);
                                    ClipRectAnimation clipRectAnimation = new ClipRectAnimation(rect3, rect4);
                                    clipRectAnimation.setDuration(517L);
                                    animationSet2.addAnimation(clipRectAnimation);
                                    animationSet2.initialize(startAbsBounds.width(), startAbsBounds.height(), rect2.width(), rect2.height());
                                    animationSet2.scaleCurrentDuration(activityEmbeddingAnimationSpec.mTransitionAnimationScaleSetting);
                                    Animation[] animationArr = {animationSet, animationSet2};
                                    Animation animation3 = animationArr[1];
                                    transaction4 = transaction;
                                    SurfaceControl orCreateScreenshot = getOrCreateScreenshot(change6, change, transaction4);
                                    transitionInfo3 = transitionInfo;
                                    TransitionInfo.Root root = transitionInfo3.getRoot(TransitionUtil.rootIndexFor(change6, transitionInfo3));
                                    if (orCreateScreenshot != null) {
                                        ActivityEmbeddingAnimationAdapter.SnapshotAdapter snapshotAdapter = new ActivityEmbeddingAnimationAdapter.SnapshotAdapter(animationArr[0], change6, orCreateScreenshot, root);
                                        arrayList2 = arrayList5;
                                        arrayList2.add(snapshotAdapter);
                                    } else {
                                        arrayList2 = arrayList5;
                                        Log.e("ActivityEmbeddingAnimR", "Failed to take screenshot for change=" + change6);
                                    }
                                    arrayList2.add(new ActivityEmbeddingAnimationAdapter.BoundsChangeAdapter(animationArr[1], change, root));
                                    activityEmbeddingAnimationRunner2 = this;
                                    animation2 = animation3;
                                    transitionInfo2 = transitionInfo3;
                                    transitionInfo5 = transitionInfo2;
                                }
                                transaction5 = transaction4;
                                transitionInfo4 = transitionInfo3;
                                it4 = it2;
                                arraySet2 = arraySet;
                                i5 = 6;
                                arrayList4 = arrayList2;
                            }
                            arrayList = arrayList4;
                            ArraySet arraySet3 = arraySet2;
                            if (rect2.isEmpty()) {
                                throw new IllegalStateException("There should be at least one changing window to play the change animation");
                            }
                            Iterator it5 = transitionInfo.getChanges().iterator();
                            boolean z5 = true;
                            while (it5.hasNext()) {
                                TransitionInfo.Change change7 = (TransitionInfo.Change) it5.next();
                                ArraySet arraySet4 = arraySet3;
                                if (arraySet4.contains(change7)) {
                                    arraySet3 = arraySet4;
                                } else {
                                    if ((change7.getParent() == null || !arraySet4.contains(transitionInfo5.getChange(change7.getParent()))) && change7.getMode() != 6) {
                                        if (TransitionUtil.isClosingType(change7.getMode())) {
                                            ActivityEmbeddingAnimationSpec activityEmbeddingAnimationSpec2 = activityEmbeddingAnimationRunner2.mAnimationSpec;
                                            activityEmbeddingAnimationSpec2.getClass();
                                            Rect startAbsBounds2 = change7.getStartAbsBounds();
                                            int i6 = rect2.top;
                                            int i7 = startAbsBounds2.top;
                                            if (i6 == i7) {
                                                it = it5;
                                                if (rect2.bottom == startAbsBounds2.bottom) {
                                                    i4 = rect2.left == startAbsBounds2.left ? -startAbsBounds2.width() : startAbsBounds2.width();
                                                    i3 = 0;
                                                    animation = new TranslateAnimation(0.0f, i4, 0.0f, i3);
                                                    animation.setInterpolator(activityEmbeddingAnimationSpec2.mFastOutExtraSlowInInterpolator);
                                                    animation.setDuration(517L);
                                                    animation.initialize(startAbsBounds2.width(), startAbsBounds2.height(), startAbsBounds2.width(), startAbsBounds2.height());
                                                    animation.scaleCurrentDuration(activityEmbeddingAnimationSpec2.mTransitionAnimationScaleSetting);
                                                    arraySet3 = arraySet4;
                                                    rect = rect2;
                                                }
                                            } else {
                                                it = it5;
                                            }
                                            int height2 = startAbsBounds2.height();
                                            if (i6 == i7) {
                                                height2 = -height2;
                                            }
                                            i3 = height2;
                                            i4 = 0;
                                            animation = new TranslateAnimation(0.0f, i4, 0.0f, i3);
                                            animation.setInterpolator(activityEmbeddingAnimationSpec2.mFastOutExtraSlowInInterpolator);
                                            animation.setDuration(517L);
                                            animation.initialize(startAbsBounds2.width(), startAbsBounds2.height(), startAbsBounds2.width(), startAbsBounds2.height());
                                            animation.scaleCurrentDuration(activityEmbeddingAnimationSpec2.mTransitionAnimationScaleSetting);
                                            arraySet3 = arraySet4;
                                            rect = rect2;
                                        } else {
                                            it = it5;
                                            ActivityEmbeddingAnimationSpec activityEmbeddingAnimationSpec3 = activityEmbeddingAnimationRunner2.mAnimationSpec;
                                            activityEmbeddingAnimationSpec3.getClass();
                                            Rect endAbsBounds2 = change7.getEndAbsBounds();
                                            int i8 = rect2.top;
                                            int i9 = endAbsBounds2.top;
                                            if (i8 == i9 && rect2.bottom == endAbsBounds2.bottom) {
                                                i2 = rect2.left == endAbsBounds2.left ? -endAbsBounds2.width() : endAbsBounds2.width();
                                                i = 0;
                                            } else {
                                                int height3 = endAbsBounds2.height();
                                                i = i8 == i9 ? -height3 : height3;
                                                i2 = 0;
                                            }
                                            TranslateAnimation translateAnimation2 = new TranslateAnimation(i2, 0.0f, i, 0.0f);
                                            translateAnimation2.setInterpolator(activityEmbeddingAnimationSpec3.mFastOutExtraSlowInInterpolator);
                                            arraySet3 = arraySet4;
                                            rect = rect2;
                                            translateAnimation2.setDuration(517L);
                                            translateAnimation2.initialize(endAbsBounds2.width(), endAbsBounds2.height(), endAbsBounds2.width(), endAbsBounds2.height());
                                            translateAnimation2.scaleCurrentDuration(activityEmbeddingAnimationSpec3.mTransitionAnimationScaleSetting);
                                            animation = translateAnimation2;
                                        }
                                        z5 = false;
                                        arrayList.add(new ActivityEmbeddingAnimationAdapter(animation, change7, transitionInfo5.getRoot(TransitionUtil.rootIndexFor(change7, transitionInfo5))));
                                        rect2 = rect;
                                        it5 = it;
                                    }
                                    float f3 = TransitionUtil.isClosingType(change7.getMode()) ? 0.0f : 1.0f;
                                    animation = new AlphaAnimation(f3, f3);
                                    it = it5;
                                    arraySet3 = arraySet4;
                                    rect = rect2;
                                    arrayList.add(new ActivityEmbeddingAnimationAdapter(animation, change7, transitionInfo5.getRoot(TransitionUtil.rootIndexFor(change7, transitionInfo5))));
                                    rect2 = rect;
                                    it5 = it;
                                }
                            }
                            if (z5 && animation2 != null) {
                                animation2.setShowBackdrop(true);
                            }
                        } else {
                            transitionInfo2 = transitionInfo4;
                            arrayList = new ArrayList();
                        }
                        activityEmbeddingAnimationRunner = this;
                        transaction3 = transaction;
                        createOpenCloseAnimationAdapters = arrayList;
                    }
                }
                z = true;
                if (z) {
                }
                activityEmbeddingAnimationRunner = this;
                transaction3 = transaction;
                createOpenCloseAnimationAdapters = arrayList;
            } else {
                if (TransitionUtil.isClosingType(transitionInfo.getType())) {
                    activityEmbeddingAnimationRunner = this;
                    ActivityEmbeddingAnimationSpec activityEmbeddingAnimationSpec4 = activityEmbeddingAnimationRunner.mAnimationSpec;
                    Objects.requireNonNull(activityEmbeddingAnimationSpec4);
                    ActivityEmbeddingAnimationRunner$$ExternalSyntheticLambda1 activityEmbeddingAnimationRunner$$ExternalSyntheticLambda1 = new ActivityEmbeddingAnimationRunner$$ExternalSyntheticLambda1(activityEmbeddingAnimationSpec4, 0);
                    transaction3 = transaction;
                    createOpenCloseAnimationAdapters = createOpenCloseAnimationAdapters(transitionInfo4, false, activityEmbeddingAnimationRunner$$ExternalSyntheticLambda1, transaction3);
                } else {
                    activityEmbeddingAnimationRunner = this;
                    transaction3 = transaction;
                    ActivityEmbeddingAnimationSpec activityEmbeddingAnimationSpec5 = activityEmbeddingAnimationRunner.mAnimationSpec;
                    Objects.requireNonNull(activityEmbeddingAnimationSpec5);
                    createOpenCloseAnimationAdapters = createOpenCloseAnimationAdapters(transitionInfo4, true, new ActivityEmbeddingAnimationRunner$$ExternalSyntheticLambda1(activityEmbeddingAnimationSpec5, 1), transaction3);
                }
                transitionInfo2 = transitionInfo4;
            }
        }
    }
}
