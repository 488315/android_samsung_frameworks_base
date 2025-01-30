package com.samsung.android.animation;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

/* loaded from: classes5.dex */
abstract class SemAbsSweepAnimationFilter {
  protected boolean mIsAnimationBack = false;

  abstract ValueAnimator createActionUpAnimator(View view, float f, int i, float f2, boolean z);

  abstract void doMoveAction(View view, float f, int i);

  abstract void doRefresh();

  abstract void doUpActionWhenAnimationUpdate(int i, float f);

  abstract void draw(Canvas canvas);

  abstract Rect getBitmapDrawableBound();

  abstract float getEndXOfActionUpAnimator();

  abstract void initAnimationFilter(
      View view,
      float f,
      int i,
      SemSweepListAnimator.OnSweepListener onSweepListener,
      SemSweepListAnimator.SweepConfiguration sweepConfiguration);

  abstract void setForegroundView(View view);

  SemAbsSweepAnimationFilter() {}

  public boolean isAnimationBack() {
    return this.mIsAnimationBack;
  }
}
