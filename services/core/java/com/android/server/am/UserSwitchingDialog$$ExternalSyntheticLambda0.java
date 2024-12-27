package com.android.server.am;

import android.R;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Trace;
import android.util.Slog;
import android.widget.ImageView;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class UserSwitchingDialog$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ UserSwitchingDialog$$ExternalSyntheticLambda0(UserSwitchingDialog userSwitchingDialog, UserController$$ExternalSyntheticLambda3 userController$$ExternalSyntheticLambda3) {
        this.$r8$classId = 1;
        this.f$0 = userSwitchingDialog;
        this.f$1 = userController$$ExternalSyntheticLambda3;
    }

    public /* synthetic */ UserSwitchingDialog$$ExternalSyntheticLambda0(UserSwitchingDialog userSwitchingDialog, UserSwitchingDialog$$ExternalSyntheticLambda0 userSwitchingDialog$$ExternalSyntheticLambda0) {
        this.$r8$classId = 3;
        this.f$0 = userSwitchingDialog;
        this.f$1 = userSwitchingDialog$$ExternalSyntheticLambda0;
    }

    public /* synthetic */ UserSwitchingDialog$$ExternalSyntheticLambda0(UserSwitchingDialog userSwitchingDialog, UserSwitchingDialog$$ExternalSyntheticLambda0 userSwitchingDialog$$ExternalSyntheticLambda0, int i) {
        this.$r8$classId = i;
        this.f$0 = userSwitchingDialog;
        this.f$1 = userSwitchingDialog$$ExternalSyntheticLambda0;
    }

    public /* synthetic */ UserSwitchingDialog$$ExternalSyntheticLambda0(UserSwitchingDialog userSwitchingDialog, Runnable runnable) {
        this.$r8$classId = 0;
        this.f$0 = userSwitchingDialog;
        this.f$1 = runnable;
    }

    public /* synthetic */ UserSwitchingDialog$$ExternalSyntheticLambda0(String str, UserSwitchingDialog$$ExternalSyntheticLambda4 userSwitchingDialog$$ExternalSyntheticLambda4) {
        this.$r8$classId = 5;
        this.f$0 = str;
        this.f$1 = userSwitchingDialog$$ExternalSyntheticLambda4;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AnimatedVectorDrawable animatedVectorDrawable;
        switch (this.$r8$classId) {
            case 0:
                UserSwitchingDialog userSwitchingDialog = (UserSwitchingDialog) this.f$0;
                Runnable runnable = this.f$1;
                userSwitchingDialog.dismiss();
                runnable.run();
                break;
            case 1:
                UserSwitchingDialog userSwitchingDialog2 = (UserSwitchingDialog) this.f$0;
                Runnable runnable2 = this.f$1;
                if (userSwitchingDialog2.mNeedToFreezeScreen) {
                    Slog.d("UserSwitchingDialog", "traceBegin-".concat("startFreezingScreen"));
                    Trace.traceBegin(64L, "startFreezingScreen");
                    userSwitchingDialog2.mWindowManager.startFreezingScreen(0, 0);
                    Trace.traceEnd(64L);
                    Slog.d("UserSwitchingDialog", "traceEnd-".concat("startFreezingScreen"));
                }
                runnable2.run();
                break;
            case 2:
                UserSwitchingDialog userSwitchingDialog3 = (UserSwitchingDialog) this.f$0;
                Runnable runnable3 = this.f$1;
                userSwitchingDialog3.asyncTraceEnd(1, "showAnimation");
                userSwitchingDialog3.asyncTraceBegin(2, "spinnerAnimation");
                UserSwitchingDialog$$ExternalSyntheticLambda0 userSwitchingDialog$$ExternalSyntheticLambda0 = new UserSwitchingDialog$$ExternalSyntheticLambda0(userSwitchingDialog3, (UserSwitchingDialog$$ExternalSyntheticLambda0) runnable3, 4);
                ImageView imageView = (ImageView) userSwitchingDialog3.findViewById(R.id.sensitive);
                if (imageView != null) {
                    Drawable drawable = imageView.getDrawable();
                    if (drawable instanceof AnimatedVectorDrawable) {
                        animatedVectorDrawable = (AnimatedVectorDrawable) drawable;
                        if (userSwitchingDialog3.mDisableAnimations && animatedVectorDrawable != null) {
                            final UserSwitchingDialog$$ExternalSyntheticLambda4 userSwitchingDialog$$ExternalSyntheticLambda4 = new UserSwitchingDialog$$ExternalSyntheticLambda4(userSwitchingDialog3, new AtomicBoolean(true), userSwitchingDialog$$ExternalSyntheticLambda0);
                            userSwitchingDialog3.mHandler.postDelayed(new UserSwitchingDialog$$ExternalSyntheticLambda0("spinner", userSwitchingDialog$$ExternalSyntheticLambda4), 1000L);
                            animatedVectorDrawable.registerAnimationCallback(new Animatable2.AnimationCallback() { // from class: com.android.server.am.UserSwitchingDialog.1
                                @Override // android.graphics.drawable.Animatable2.AnimationCallback
                                public final void onAnimationEnd(Drawable drawable2) {
                                    userSwitchingDialog$$ExternalSyntheticLambda4.run();
                                }
                            });
                            animatedVectorDrawable.start();
                            break;
                        } else {
                            userSwitchingDialog$$ExternalSyntheticLambda0.run();
                            break;
                        }
                    }
                }
                animatedVectorDrawable = null;
                if (userSwitchingDialog3.mDisableAnimations) {
                }
                userSwitchingDialog$$ExternalSyntheticLambda0.run();
            case 3:
                UserSwitchingDialog userSwitchingDialog4 = (UserSwitchingDialog) this.f$0;
                Runnable runnable4 = this.f$1;
                userSwitchingDialog4.asyncTraceEnd(3, "dismissAnimation");
                runnable4.run();
                break;
            case 4:
                UserSwitchingDialog userSwitchingDialog5 = (UserSwitchingDialog) this.f$0;
                Runnable runnable5 = this.f$1;
                userSwitchingDialog5.asyncTraceEnd(2, "spinnerAnimation");
                runnable5.run();
                break;
            default:
                String str = (String) this.f$0;
                Runnable runnable6 = this.f$1;
                Slog.w("UserSwitchingDialog", str + " animation not completed in 1000 ms");
                runnable6.run();
                break;
        }
    }
}
