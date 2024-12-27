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
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class UserSwitchingDialog extends Dialog {
    public final boolean mDisableAnimations;
    public final Handler mHandler;
    public final boolean mNeedToFreezeScreen;
    public final int mTraceCookie;
    public final WindowManagerService mWindowManager;

    public UserSwitchingDialog(Context context, UserInfo userInfo, UserInfo userInfo2, String str, String str2, WindowManagerService windowManagerService) {
        super(context, R.style.Theme.Material.NoActionBar.Fullscreen);
        String string;
        this.mHandler = new Handler(Looper.myLooper());
        boolean z = SystemProperties.getBoolean("debug.usercontroller.disable_user_switching_dialog_animations", false);
        this.mDisableAnimations = z;
        this.mWindowManager = windowManagerService;
        this.mNeedToFreezeScreen = (z || Settings.Secure.getIntForUser(context.getContentResolver(), "user_setup_complete", 0, userInfo2.id) == 1) ? false : true;
        this.mTraceCookie = (userInfo.id * 21473) + userInfo2.id;
        setCancelable(false);
        setContentView(17367516);
        TextView textView = (TextView) findViewById(R.id.message);
        if (textView != null) {
            Resources resources = getContext().getResources();
            if (UserManager.isDeviceInDemoMode(context)) {
                string = resources.getString(userInfo.isDemo() ? R.string.gnss_service : R.string.gnss_time_update_service);
            } else {
                str = userInfo.id != 0 ? userInfo2.id == 0 ? str2 : null : str;
                string = str != null ? str : resources.getString(17042776, userInfo2.name);
            }
            textView.setAccessibilityPaneTitle(string);
            textView.setText(string);
        }
        ImageView imageView = (ImageView) findViewById(R.id.icon);
        if (imageView != null) {
            Bitmap decodeFile = BitmapFactory.decodeFile(userInfo2.iconPath);
            int i = userInfo2.id;
            Resources resources2 = getContext().getResources();
            Bitmap bitmap = (Bitmap) ObjectUtils.getOrElse(decodeFile, UserIcons.convertToBitmapAtUserIconSize(resources2, UserIcons.getDefaultUserIcon(resources2, i, false)));
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Bitmap createBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
            Paint paint = new Paint(1);
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            paint.setShader(new BitmapShader(bitmap, tileMode, tileMode));
            float f = width;
            float f2 = height;
            new Canvas(createBitmap).drawRoundRect(new RectF(FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, f, f2), f / 2.0f, f2 / 2.0f, paint);
            imageView.setImageBitmap(createBitmap);
        }
        ImageView imageView2 = (ImageView) findViewById(R.id.sensitive);
        if (imageView2 != null) {
            if (z) {
                imageView2.setVisibility(8);
            } else {
                TypedValue typedValue = new TypedValue();
                getContext().getTheme().resolveAttribute(R.^attr-private.colorAccentPrimary, typedValue, true);
                imageView2.setColorFilter(typedValue.data);
            }
        }
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.privateFlags = 272;
        attributes.layoutInDisplayCutoutMode = 3;
        window.setAttributes(attributes);
        window.setBackgroundDrawableResource(R.color.transparent);
        window.setType(2010);
        window.setDecorFitsSystemWindows(false);
    }

    public final void asyncTraceBegin(int i, String str) {
        Slog.d("UserSwitchingDialog", "asyncTraceBegin-".concat(str));
        Trace.asyncTraceBegin(64L, "UserSwitchingDialog".concat(str), this.mTraceCookie + i);
    }

    public final void asyncTraceEnd(int i, String str) {
        Trace.asyncTraceEnd(64L, "UserSwitchingDialog".concat(str), this.mTraceCookie + i);
        Slog.d("UserSwitchingDialog", "asyncTraceEnd-".concat(str));
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        super.dismiss();
        if (this.mNeedToFreezeScreen) {
            Slog.d("UserSwitchingDialog", "traceBegin-".concat("stopFreezingScreen"));
            Trace.traceBegin(64L, "stopFreezingScreen");
            this.mWindowManager.stopFreezingScreen();
            Trace.traceEnd(64L);
            Slog.d("UserSwitchingDialog", "traceEnd-".concat("stopFreezingScreen"));
        }
        asyncTraceEnd(0, "dialog");
    }

    public final void dismiss(Runnable runnable) {
        Slog.d("UserSwitchingDialog", "dismiss called");
        if (runnable == null) {
            dismiss();
            return;
        }
        UserSwitchingDialog$$ExternalSyntheticLambda0 userSwitchingDialog$$ExternalSyntheticLambda0 = new UserSwitchingDialog$$ExternalSyntheticLambda0(this, runnable);
        if (this.mDisableAnimations || this.mNeedToFreezeScreen) {
            userSwitchingDialog$$ExternalSyntheticLambda0.run();
        } else {
            asyncTraceBegin(3, "dismissAnimation");
            startDialogAnimation("dismiss", new AlphaAnimation(1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE), new UserSwitchingDialog$$ExternalSyntheticLambda0(this, userSwitchingDialog$$ExternalSyntheticLambda0));
        }
    }

    @Override // android.app.Dialog
    public final void show() {
        asyncTraceBegin(0, "dialog");
        super.show();
    }

    public final void show(UserController$$ExternalSyntheticLambda3 userController$$ExternalSyntheticLambda3) {
        Slog.d("UserSwitchingDialog", "show called");
        show();
        UserSwitchingDialog$$ExternalSyntheticLambda0 userSwitchingDialog$$ExternalSyntheticLambda0 = new UserSwitchingDialog$$ExternalSyntheticLambda0(this, userController$$ExternalSyntheticLambda3);
        if (this.mDisableAnimations) {
            userSwitchingDialog$$ExternalSyntheticLambda0.run();
        } else {
            asyncTraceBegin(1, "showAnimation");
            startDialogAnimation(KnoxCustomManagerService.SHOW, new AlphaAnimation(FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f), new UserSwitchingDialog$$ExternalSyntheticLambda0(this, userSwitchingDialog$$ExternalSyntheticLambda0, 2));
        }
    }

    public final void startDialogAnimation(String str, Animation animation, Runnable runnable) {
        View findViewById = findViewById(R.id.content);
        if (this.mDisableAnimations || findViewById == null) {
            runnable.run();
            return;
        }
        final UserSwitchingDialog$$ExternalSyntheticLambda4 userSwitchingDialog$$ExternalSyntheticLambda4 = new UserSwitchingDialog$$ExternalSyntheticLambda4(this, new AtomicBoolean(true), runnable);
        this.mHandler.postDelayed(new UserSwitchingDialog$$ExternalSyntheticLambda0(str, userSwitchingDialog$$ExternalSyntheticLambda4), 1000L);
        animation.setDuration(300L);
        animation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.server.am.UserSwitchingDialog.2
            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationEnd(Animation animation2) {
                userSwitchingDialog$$ExternalSyntheticLambda4.run();
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationRepeat(Animation animation2) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationStart(Animation animation2) {
            }
        });
        findViewById.startAnimation(animation);
    }
}
