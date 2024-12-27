package com.android.server.power.shutdown;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.util.Slog;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.power.LibQmg;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.view.SemWindowManager;

import java.util.ArrayList;
import java.util.Locale;

public final class ShutdownDialog extends Dialog {
    public final AnimationPlayer animationPlayer;
    public Handler logHandler;
    public final Context mContext;
    public ShutdownAnimatedImageView mImageView;
    public TextView mLogView;
    public final SoundPlayer soundPlayer;

    public ShutdownDialog(Context context) {
        super(context, R.style.Theme.NoTitleBar.Fullscreen);
        this.mContext = context;
        this.soundPlayer = new SoundPlayer(context);
        SemWindowManager.FoldStateListener webpPlayer = new WebpPlayer(context, ".webp");
        Locale locale = Locale.ENGLISH;
        ResourceManager resourceManager = webpPlayer.resourceManager;
        Slog.v(
                "Shutdown-WebpPlayer",
                String.format(
                        locale,
                        "isAvailable %s, %s, [%s,%s]",
                        resourceManager.mainImages,
                        resourceManager.subImages,
                        resourceManager.mainLoopImage,
                        resourceManager.subLoopImage));
        if (!(!((ArrayList) resourceManager.mainImages).isEmpty())) {
            webpPlayer = new QmgPlayer(context);
            if (LibQmg.qmgCheckSupportQmg() != 1 || webpPlayer.mainImages.isEmpty()) {
                webpPlayer = new PngPlayer(context, ".png");
                webpPlayer.mainBitmap = null;
                webpPlayer.subBitmap = null;
                ResourceManager resourceManager2 = webpPlayer.resourceManager;
                Slog.v(
                        "Shutdown-PngPlayer",
                        String.format(
                                locale,
                                "isAvailable %s, %s, [%s,%s]",
                                resourceManager2.mainImages,
                                resourceManager2.subImages,
                                resourceManager2.mainLoopImage,
                                resourceManager2.subLoopImage));
                if (((ArrayList) resourceManager2.mainImages).isEmpty()
                        && ((ArrayList) resourceManager2.subImages).isEmpty()
                        && resourceManager2.mainLoopImage == null
                        && resourceManager2.subLoopImage == null) {
                    Slog.w(
                            "Shutdown-AnimationPlayer",
                            "createPlayer can not make available player");
                    webpPlayer = null;
                }
            }
        }
        this.animationPlayer = webpPlayer;
        if (webpPlayer == null) {
            Slog.w("Shutdown-ShutdownDialog", "can't find available animation player");
        }
        setCancelable(false);
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.setTitle("ShutdownDialog");
            attributes.flags |= -2140535424;
            attributes.privateFlags |= 16;
            attributes.systemUiVisibility |= 2;
            attributes.rotationAnimation |= 2;
            window.clearFlags(EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT);
            attributes.type = 2021;
            attributes.layoutInDisplayCutoutMode = 1;
            window.setAttributes(attributes);
            window.setWindowAnimations(0);
        }
    }

    @Override // android.app.Dialog
    public final void onCreate(Bundle bundle) {
        Slog.d("Shutdown-ShutdownDialog", "onCreate " + this.animationPlayer);
        super.onCreate(bundle);
        Window window = getWindow();
        if (window != null) {
            window.setStatusBarColor(0);
            window.setDecorFitsSystemWindows(false);
        }
        FrameLayout frameLayout = new FrameLayout(this.mContext);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1, 17));
        frameLayout.setBackgroundColor(-16777216);
        ShutdownAnimatedImageView shutdownAnimatedImageView =
                new ShutdownAnimatedImageView(this.mContext);
        shutdownAnimatedImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        shutdownAnimatedImageView.setAdjustViewBounds(true);
        this.mImageView = shutdownAnimatedImageView;
        shutdownAnimatedImageView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1, 17));
        this.logHandler = new Handler(Looper.getMainLooper());
        TextView textView = new TextView(this.mContext);
        this.mLogView = textView;
        textView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.mLogView.setTextColor(-16711681);
        frameLayout.addView(this.mImageView);
        frameLayout.addView(this.mLogView);
        setContentView(frameLayout);
        AnimationPlayer animationPlayer = this.animationPlayer;
        if (animationPlayer != null) {
            animationPlayer.setView(this.mImageView);
        }
    }

    @Override // android.app.Dialog
    public final void onStart() {
        AnimationPlayer animationPlayer;
        Slog.d("Shutdown-ShutdownDialog", "onStart");
        super.onStart();
        Window window = getWindow();
        if (window != null && (animationPlayer = this.animationPlayer) != null) {
            Pair mainAnimationWidthHeight = animationPlayer.getMainAnimationWidthHeight();
            if (mainAnimationWidthHeight == null) {
                Slog.d("Shutdown-ShutdownDialog", "disableWindowRotation imageSize is null!");
            } else {
                Integer num = (Integer) mainAnimationWidthHeight.first;
                int intValue = num.intValue();
                Integer num2 = (Integer) mainAnimationWidthHeight.second;
                int intValue2 = num2.intValue();
                if (intValue == intValue2) {
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(
                            intValue,
                            "disableWindowRotation width and height are same as ",
                            "Shutdown-ShutdownDialog");
                } else {
                    WindowManager.LayoutParams attributes = window.getAttributes();
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(attributes);
                    if (intValue > intValue2) {
                        layoutParams.screenOrientation = 0;
                    } else {
                        layoutParams.screenOrientation = 1;
                    }
                    window.setAttributes(layoutParams);
                    Locale locale = Locale.ENGLISH;
                    String str = layoutParams.screenOrientation == 0 ? "LANDSCAPE" : "PORTRAIT";
                    StringBuilder sb = new StringBuilder("disableWindowRotation width[");
                    sb.append(num);
                    sb.append("] height[");
                    sb.append(num2);
                    sb.append("] orientation[");
                    Slog.d(
                            "Shutdown-ShutdownDialog",
                            String.format(
                                    locale,
                                    AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, str, "]"),
                                    new Object[0]));
                }
            }
        }
        AnimationPlayer animationPlayer2 = this.animationPlayer;
        if (animationPlayer2 != null) {
            animationPlayer2.start();
        }
        this.soundPlayer.start();
    }

    @Override // android.app.Dialog
    public final void onStop() {
        super.onStop();
        Slog.d("Shutdown-ShutdownDialog", "onStop");
    }
}
