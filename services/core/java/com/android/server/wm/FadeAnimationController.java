package com.android.server.wm;

import android.R;
import android.content.Context;
import android.os.Debug;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public abstract class FadeAnimationController {
  public final Context mContext;
  public final DisplayContent mDisplayContent;

  public FadeAnimationController(DisplayContent displayContent) {
    this.mDisplayContent = displayContent;
    this.mContext = displayContent.mWmService.mContext;
  }

  public Animation getFadeInAnimation() {
    return AnimationUtils.loadAnimation(this.mContext, R.anim.fade_in);
  }

  public Animation getFadeOutAnimation() {
    return AnimationUtils.loadAnimation(this.mContext, R.anim.fade_out);
  }

  public void fadeWindowToken(boolean z, WindowToken windowToken, int i) {
    fadeWindowToken(z, windowToken, i, null);
  }

  public void fadeWindowToken(
      boolean z,
      WindowToken windowToken,
      int i,
      SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
    if (windowToken == null || windowToken.getParent() == null) {
      return;
    }
    Animation fadeInAnimation = z ? getFadeInAnimation() : getFadeOutAnimation();
    FadeAnimationAdapter createAdapter =
        fadeInAnimation != null
            ? createAdapter(createAnimationSpec(fadeInAnimation), z, windowToken)
            : null;
    if (createAdapter == null) {
      return;
    }
    Slog.d(
        StartingSurfaceController.TAG,
        "fadeWindowToken, show="
            + z
            + ", animationType="
            + SurfaceAnimator.animationTypeToString(i)
            + ", topWindow="
            + windowToken.getTopChild()
            + ", token="
            + windowToken
            + ", caller="
            + Debug.getCallers(5));
    windowToken.startAnimation(
        windowToken.getPendingTransaction(), createAdapter, z, i, onAnimationFinishedCallback);
  }

  public FadeAnimationAdapter createAdapter(
      LocalAnimationAdapter.AnimationSpec animationSpec, boolean z, WindowToken windowToken) {
    return new FadeAnimationAdapter(
        animationSpec, windowToken.getSurfaceAnimationRunner(), z, windowToken);
  }

  public LocalAnimationAdapter.AnimationSpec createAnimationSpec(final Animation animation) {
    return new LocalAnimationAdapter
        .AnimationSpec() { // from class: com.android.server.wm.FadeAnimationController.1
      public final Transformation mTransformation = new Transformation();

      @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
      public boolean getShowWallpaper() {
        return true;
      }

      @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
      public long getDuration() {
        return animation.getDuration();
      }

      @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
      public void apply(
          SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, long j) {
        this.mTransformation.clear();
        animation.getTransformation(j, this.mTransformation);
        transaction.setAlpha(surfaceControl, this.mTransformation.getAlpha());
      }

      @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
      public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.println(animation);
      }

      @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
      public void dumpDebugInner(ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1138166333441L, animation.toString());
        protoOutputStream.end(start);
      }
    };
  }

  public class FadeAnimationAdapter extends LocalAnimationAdapter {
    public final boolean mShow;
    public final WindowToken mToken;

    public FadeAnimationAdapter(
        LocalAnimationAdapter.AnimationSpec animationSpec,
        SurfaceAnimationRunner surfaceAnimationRunner,
        boolean z,
        WindowToken windowToken) {
      super(animationSpec, surfaceAnimationRunner);
      this.mShow = z;
      this.mToken = windowToken;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public boolean shouldDeferAnimationFinish(Runnable runnable) {
      return !this.mShow;
    }
  }
}
