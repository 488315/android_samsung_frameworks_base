package com.android.server.am;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Slog;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.util.ObjectUtils;
import com.android.internal.util.UserIcons;
import com.android.server.display.DisplayPowerController2;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class UserSwitchingDialog extends Dialog {
    public final Context mContext;
    public final boolean mDisableAnimations;
    public final Handler mHandler;
    public final boolean mNeedToFreezeScreen;
    public final UserInfo mNewUser;
    public final UserInfo mOldUser;
    public final String mSwitchingFromSystemUserMessage;
    public final String mSwitchingToSystemUserMessage;
    public final int mTraceCookie;
    public final WindowManagerService mWindowManager;

    public UserSwitchingDialog(Context context, UserInfo userInfo, UserInfo userInfo2, String str, String str2, WindowManagerService windowManagerService) {
        super(context, R.style.Theme.Material.NoActionBar.Fullscreen);
        this.mHandler = new Handler(Looper.myLooper());
        this.mContext = context;
        this.mOldUser = userInfo;
        this.mNewUser = userInfo2;
        this.mSwitchingFromSystemUserMessage = str;
        this.mSwitchingToSystemUserMessage = str2;
        boolean z = false;
        boolean z2 = SystemProperties.getBoolean("debug.usercontroller.disable_user_switching_dialog_animations", false);
        this.mDisableAnimations = z2;
        this.mWindowManager = windowManagerService;
        if (!z2 && !isUserSetupComplete(userInfo2)) {
            z = true;
        }
        this.mNeedToFreezeScreen = z;
        this.mTraceCookie = (userInfo.id * 21474) + userInfo2.id;
        inflateContent();
        configureWindow();
    }

    public final void configureWindow() {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.privateFlags = 272;
        attributes.layoutInDisplayCutoutMode = 3;
        window.setAttributes(attributes);
        window.setBackgroundDrawableResource(R.color.transparent);
        window.setType(2010);
        window.setDecorFitsSystemWindows(false);
    }

    public void inflateContent() {
        setCancelable(false);
        setContentView(17367508);
        TextView textView = (TextView) findViewById(R.id.message);
        if (textView != null) {
            String textMessage = getTextMessage();
            textView.setAccessibilityPaneTitle(textMessage);
            textView.setText(textMessage);
        }
        ImageView imageView = (ImageView) findViewById(R.id.icon);
        if (imageView != null) {
            imageView.setImageBitmap(getUserIconRounded());
        }
        ImageView imageView2 = (ImageView) findViewById(R.id.searchbox);
        if (imageView2 != null) {
            if (this.mDisableAnimations) {
                imageView2.setVisibility(8);
                return;
            }
            TypedValue typedValue = new TypedValue();
            getContext().getTheme().resolveAttribute(R.^attr-private.colorAccentPrimary, typedValue, true);
            imageView2.setColorFilter(typedValue.data);
        }
    }

    public final Bitmap getUserIconRounded() {
        Bitmap bitmap = (Bitmap) ObjectUtils.getOrElse(BitmapFactory.decodeFile(this.mNewUser.iconPath), defaultUserIcon(this.mNewUser.id));
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        Paint paint = new Paint(1);
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        paint.setShader(new BitmapShader(bitmap, tileMode, tileMode));
        float f = width;
        float f2 = height;
        new Canvas(createBitmap).drawRoundRect(new RectF(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, f, f2), f / 2.0f, f2 / 2.0f, paint);
        return createBitmap;
    }

    public final Bitmap defaultUserIcon(int i) {
        Resources resources = getContext().getResources();
        return UserIcons.convertToBitmapAtUserIconSize(resources, UserIcons.getDefaultUserIcon(resources, i, false));
    }

    public final String getTextMessage() {
        String str;
        Resources resources = getContext().getResources();
        if (UserManager.isDeviceInDemoMode(this.mContext)) {
            return resources.getString(this.mOldUser.isDemo() ? R.string.font_family_button_material : R.string.font_family_caption_material);
        }
        if (this.mOldUser.id == 0) {
            str = this.mSwitchingFromSystemUserMessage;
        } else {
            str = this.mNewUser.id == 0 ? this.mSwitchingToSystemUserMessage : null;
        }
        return str != null ? str : resources.getString(17042577, this.mNewUser.name);
    }

    public final boolean isUserSetupComplete(UserInfo userInfo) {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "user_setup_complete", 0, userInfo.id) == 1;
    }

    @Override // android.app.Dialog
    public void show() {
        asyncTraceBegin("dialog", 0);
        super.show();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
        stopFreezingScreen();
        asyncTraceEnd("dialog", 0);
    }

    public void show(final Runnable runnable) {
        Slog.d("UserSwitchingDialog", "show called");
        show();
        startShowAnimation(new Runnable() { // from class: com.android.server.am.UserSwitchingDialog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                UserSwitchingDialog.this.lambda$show$0(runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$0(Runnable runnable) {
        startFreezingScreen();
        runnable.run();
    }

    public void dismiss(final Runnable runnable) {
        Slog.d("UserSwitchingDialog", "dismiss called");
        if (runnable == null) {
            dismiss();
        } else {
            startDismissAnimation(new Runnable() { // from class: com.android.server.am.UserSwitchingDialog$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    UserSwitchingDialog.this.lambda$dismiss$1(runnable);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dismiss$1(Runnable runnable) {
        dismiss();
        runnable.run();
    }

    public final void startFreezingScreen() {
        if (this.mNeedToFreezeScreen) {
            traceBegin("startFreezingScreen");
            this.mWindowManager.startFreezingScreen(0, 0);
            traceEnd("startFreezingScreen");
        }
    }

    public final void stopFreezingScreen() {
        if (this.mNeedToFreezeScreen) {
            traceBegin("stopFreezingScreen");
            this.mWindowManager.stopFreezingScreen();
            traceEnd("stopFreezingScreen");
        }
    }

    public final void startShowAnimation(final Runnable runnable) {
        if (this.mDisableAnimations) {
            runnable.run();
        } else {
            asyncTraceBegin("showAnimation", 1);
            startDialogAnimation(KnoxCustomManagerService.SHOW, new AlphaAnimation(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f), new Runnable() { // from class: com.android.server.am.UserSwitchingDialog$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    UserSwitchingDialog.this.lambda$startShowAnimation$3(runnable);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startShowAnimation$3(final Runnable runnable) {
        asyncTraceEnd("showAnimation", 1);
        asyncTraceBegin("spinnerAnimation", 2);
        startProgressAnimation(new Runnable() { // from class: com.android.server.am.UserSwitchingDialog$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                UserSwitchingDialog.this.lambda$startShowAnimation$2(runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startShowAnimation$2(Runnable runnable) {
        asyncTraceEnd("spinnerAnimation", 2);
        runnable.run();
    }

    public final void startDismissAnimation(final Runnable runnable) {
        if (this.mDisableAnimations || this.mNeedToFreezeScreen) {
            runnable.run();
        } else {
            asyncTraceBegin("dismissAnimation", 3);
            startDialogAnimation("dismiss", new AlphaAnimation(1.0f, DisplayPowerController2.RATE_FROM_DOZE_TO_ON), new Runnable() { // from class: com.android.server.am.UserSwitchingDialog$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    UserSwitchingDialog.this.lambda$startDismissAnimation$4(runnable);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDismissAnimation$4(Runnable runnable) {
        asyncTraceEnd("dismissAnimation", 3);
        runnable.run();
    }

    public final void startProgressAnimation(Runnable runnable) {
        AnimatedVectorDrawable spinnerAVD = getSpinnerAVD();
        if (this.mDisableAnimations || spinnerAVD == null) {
            runnable.run();
            return;
        }
        final Runnable animationWithTimeout = animationWithTimeout("spinner", runnable);
        spinnerAVD.registerAnimationCallback(new Animatable2.AnimationCallback() { // from class: com.android.server.am.UserSwitchingDialog.1
            @Override // android.graphics.drawable.Animatable2.AnimationCallback
            public void onAnimationEnd(Drawable drawable) {
                animationWithTimeout.run();
            }
        });
        spinnerAVD.start();
    }

    public final AnimatedVectorDrawable getSpinnerAVD() {
        ImageView imageView = (ImageView) findViewById(R.id.searchbox);
        if (imageView == null) {
            return null;
        }
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof AnimatedVectorDrawable) {
            return (AnimatedVectorDrawable) drawable;
        }
        return null;
    }

    public final void startDialogAnimation(String str, Animation animation, Runnable runnable) {
        View findViewById = findViewById(R.id.content);
        if (this.mDisableAnimations || findViewById == null) {
            runnable.run();
            return;
        }
        final Runnable animationWithTimeout = animationWithTimeout(str, runnable);
        animation.setDuration(300L);
        animation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.server.am.UserSwitchingDialog.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation2) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation2) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation2) {
                animationWithTimeout.run();
            }
        });
        findViewById.startAnimation(animation);
    }

    public final Runnable animationWithTimeout(final String str, final Runnable runnable) {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        final Runnable runnable2 = new Runnable() { // from class: com.android.server.am.UserSwitchingDialog$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                UserSwitchingDialog.this.lambda$animationWithTimeout$5(atomicBoolean, runnable);
            }
        };
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.am.UserSwitchingDialog$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                UserSwitchingDialog.lambda$animationWithTimeout$6(str, runnable2);
            }
        }, 1000L);
        return runnable2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$animationWithTimeout$5(AtomicBoolean atomicBoolean, Runnable runnable) {
        if (atomicBoolean.getAndSet(false)) {
            this.mHandler.removeCallbacksAndMessages(null);
            runnable.run();
        }
    }

    public static /* synthetic */ void lambda$animationWithTimeout$6(String str, Runnable runnable) {
        Slog.w("UserSwitchingDialog", str + " animation not completed in 1000 ms");
        runnable.run();
    }

    public final void asyncTraceBegin(String str, int i) {
        Slog.d("UserSwitchingDialog", "asyncTraceBegin-" + str);
        Trace.asyncTraceBegin(64L, "UserSwitchingDialog" + str, this.mTraceCookie + i);
    }

    public final void asyncTraceEnd(String str, int i) {
        Trace.asyncTraceEnd(64L, "UserSwitchingDialog" + str, this.mTraceCookie + i);
        Slog.d("UserSwitchingDialog", "asyncTraceEnd-" + str);
    }

    public final void traceBegin(String str) {
        Slog.d("UserSwitchingDialog", "traceBegin-" + str);
        Trace.traceBegin(64L, str);
    }

    public final void traceEnd(String str) {
        Trace.traceEnd(64L);
        Slog.d("UserSwitchingDialog", "traceEnd-" + str);
    }
}
