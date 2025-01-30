package com.android.server.wm;


/* compiled from: R8$$SyntheticClass */
/* renamed from: com.android.server.wm.ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda0 */
/* loaded from: classes3.dex */
public final /* synthetic */ class C2967x5a652b8e
    implements SurfaceAnimator.OnAnimationFinishedCallback {
  public final /* synthetic */ ScreenRotationAnimation.SurfaceRotationAnimationController f$0;

  public /* synthetic */ C2967x5a652b8e(
      ScreenRotationAnimation.SurfaceRotationAnimationController
          surfaceRotationAnimationController) {
    this.f$0 = surfaceRotationAnimationController;
  }

  @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
  public final void onAnimationFinished(int i, AnimationAdapter animationAdapter) {
    this.f$0.onAnimationEnd(i, animationAdapter);
  }
}
