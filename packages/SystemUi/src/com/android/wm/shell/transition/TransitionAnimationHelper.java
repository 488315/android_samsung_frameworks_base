package com.android.wm.shell.transition;

import android.R;
import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.window.ScreenCapture;
import android.window.TransitionInfo;
import com.android.internal.policy.TransitionAnimation;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.util.TransitionUtil;
import com.android.systemui.keyguard.KeyguardService$$ExternalSyntheticOutline0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TransitionAnimationHelper {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00b5, code lost:
    
        if (r9.equals("Right Edge Extension") == false) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void createExtensionSurface(SurfaceControl surfaceControl, Rect rect, Rect rect2, int i, int i2, String str, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        int i3;
        SurfaceControl build = new SurfaceControl.Builder().setName(str).setParent(surfaceControl).setHidden(true).setCallsite("TransitionAnimationHelper#createExtensionSurface").setOpaque(true).setBufferSize(rect2.width(), rect2.height()).build();
        ScreenCapture.ScreenshotHardwareBuffer captureLayers = ScreenCapture.captureLayers(new ScreenCapture.LayerCaptureArgs.Builder(surfaceControl).setSourceCrop(rect).setFrameScale(1.0f).setPixelFormat(1).setChildrenOnly(true).setAllowProtected(true).setCaptureSecureLayers(true).build());
        char c = 0;
        if (captureLayers == null) {
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                ShellProtoLogImpl.m230e(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1686649611, 0, "Failed to capture edge of window.", null);
                return;
            }
            return;
        }
        Bitmap asBitmap = captureLayers.asBitmap();
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        BitmapShader bitmapShader = new BitmapShader(asBitmap, tileMode, tileMode);
        Paint paint = new Paint();
        if (CoreRune.FW_EDGE_EXTENSION_ANIM_DEBUG) {
            switch (str.hashCode()) {
                case -1732636608:
                    break;
                case -266415435:
                    if (str.equals("Left Edge Extension")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -253668335:
                    if (str.equals("Bottom Edge Extension")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 596320231:
                    if (str.equals("Top Edge Extension")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    i3 = -16776961;
                    break;
                case 1:
                    i3 = -65536;
                    break;
                case 2:
                    i3 = -16711681;
                    break;
                case 3:
                    i3 = -16711936;
                    break;
                default:
                    i3 = -65281;
                    break;
            }
            paint.setColor(i3);
        } else {
            paint.setShader(bitmapShader);
        }
        Surface surface = new Surface(build);
        Canvas lockHardwareCanvas = surface.lockHardwareCanvas();
        lockHardwareCanvas.drawRect(rect2, paint);
        surface.unlockCanvasAndPost(lockHardwareCanvas);
        surface.release();
        transaction.setLayer(build, VideoPlayer.MEDIA_ERROR_SYSTEM);
        transaction.setPosition(build, i, i2);
        transaction.setVisibility(build, true);
        transaction2.remove(build);
    }

    public static void edgeExtendWindow(TransitionInfo.Change change, Animation animation, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        if ((change.getFlags() & 8) == 0 && !change.hasFlags(QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING)) {
            Transformation transformation = new Transformation();
            animation.getTransformationAt(0.0f, transformation);
            Transformation transformation2 = new Transformation();
            animation.getTransformationAt(1.0f, transformation2);
            Insets min = Insets.min(transformation.getInsets(), transformation2.getInsets());
            int max = Math.max(change.getStartAbsBounds().height(), change.getEndAbsBounds().height());
            int max2 = Math.max(change.getStartAbsBounds().width(), change.getEndAbsBounds().width());
            if (min.left < 0) {
                createExtensionSurface(change.getLeash(), new Rect(0, 0, 1, max), new Rect(0, 0, -min.left, max), min.left, 0, "Left Edge Extension", transaction, transaction2);
            }
            if (min.top < 0) {
                createExtensionSurface(change.getLeash(), new Rect(0, 0, max2, 1), new Rect(0, 0, max2, -min.top), 0, min.top, "Top Edge Extension", transaction, transaction2);
            }
            if (min.right < 0) {
                createExtensionSurface(change.getLeash(), new Rect(max2 - 1, 0, max2, max), new Rect(0, 0, -min.right, max), max2, 0, "Right Edge Extension", transaction, transaction2);
            }
            if (min.bottom < 0) {
                createExtensionSurface(change.getLeash(), new Rect(0, max - 1, max2, max), new Rect(0, 0, max2, -min.bottom), min.left, max, "Bottom Edge Extension", transaction, transaction2);
            }
        }
    }

    public static int getTransitionBackgroundColorIfSet(TransitionInfo transitionInfo, TransitionInfo.Change change, Animation animation, int i) {
        return !animation.getShowBackdrop() ? i : (transitionInfo.getAnimationOptions() == null || transitionInfo.getAnimationOptions().getBackgroundColor() == 0) ? animation.getBackdropColor() != 0 ? animation.getBackdropColor() : change.getBackgroundColor() != 0 ? change.getBackgroundColor() : i : transitionInfo.getAnimationOptions().getBackgroundColor();
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x019c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Animation loadAttributeAnimation(TransitionInfo transitionInfo, TransitionInfo.Change change, int i, TransitionAnimation transitionAnimation, boolean z) {
        int i2;
        boolean z2;
        Animation loadAnimationAttr;
        int type = transitionInfo.getType();
        int mode = change.getMode();
        int flags = change.getFlags();
        boolean isOpeningType = TransitionUtil.isOpeningType(mode);
        boolean z3 = true;
        boolean z4 = change.getTaskInfo() != null;
        TransitionInfo.AnimationOptions animationOptions = transitionInfo.getAnimationOptions();
        int type2 = animationOptions != null ? animationOptions.getType() : 0;
        if (z) {
            if (type == 1) {
                i2 = isOpeningType ? 28 : 29;
            } else {
                if (type == 2 && !isOpeningType) {
                    i2 = 27;
                }
                i2 = 0;
            }
            z2 = false;
        } else {
            if (i == 3) {
                i2 = isOpeningType ? 20 : 21;
            } else if (i == 4) {
                i2 = isOpeningType ? 22 : 23;
            } else if (i == 1) {
                i2 = isOpeningType ? 16 : 17;
            } else if (i == 2) {
                i2 = isOpeningType ? 18 : 19;
            } else {
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
                    if (type == 1 || type == 2 || type == 3 || type == 4) {
                        int i3 = 0;
                        int i4 = 0;
                        for (int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m >= 0; m136m--) {
                            TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
                            ActivityManager.RunningTaskInfo taskInfo = change2.getTaskInfo();
                            if (change2.getMode() != 6 && ((taskInfo != null || change2.hasFlags(QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED)) && (taskInfo == null || !WindowConfiguration.inMultiWindowMode(taskInfo.getWindowingMode())))) {
                                i3++;
                                if (change2.hasFlags(4)) {
                                    i4++;
                                }
                            }
                        }
                        if ((i3 == i4 && i3 > 0) && !isOpeningType) {
                            z2 = true;
                            i2 = 7;
                        }
                    }
                }
                i2 = R.anim.voice_activity_open_enter;
                if (type == 1) {
                    z2 = (flags & 4) != 0;
                    if (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_RESUMED_AFFORDANCE || !z4 || !change.getResumedAffordance()) {
                        if (!z4 || !z2 || isOpeningType) {
                            i2 = (!z4 || z2) ? isOpeningType ? 4 : 5 : isOpeningType ? 8 : 9;
                        }
                        i2 = 7;
                    }
                } else if (type == 3) {
                    if (!CoreRune.MT_NEW_DEX_RESUMED_AFFORDANCE_ANIMATION || !z4 || !change.getConfiguration().isNewDexMode() || !change.getResumedAffordance()) {
                        i2 = isOpeningType ? 12 : 13;
                    }
                } else if (type == 2) {
                    z2 = ((flags & 4) == 0 || isOpeningType) ? false : true;
                    if (!z4 || z2) {
                        if (isOpeningType) {
                            i2 = 6;
                        }
                        i2 = 7;
                    } else {
                        i2 = isOpeningType ? 10 : 11;
                    }
                } else {
                    if (type == 4) {
                        i2 = isOpeningType ? 14 : 15;
                    }
                    i2 = 0;
                }
            }
            z2 = false;
        }
        Animation animation = null;
        if (i2 != 0) {
            if (type2 == 14 && !z4) {
                if (i2 != 4 && i2 != 5) {
                    if (i2 == 6 || i2 == 7) {
                        z3 = false;
                    }
                    if (animation == null) {
                        loadAnimationAttr = transitionAnimation.loadAppTransitionAnimation(animationOptions.getPackageName(), isOpeningType ? animation.getCustomEnterResId() : animation.getCustomExitResId());
                        if (loadAnimationAttr != null && animation.getCustomBackgroundColor() != 0) {
                            loadAnimationAttr.setBackdropColor(animation.getCustomBackgroundColor());
                        }
                    } else {
                        loadAnimationAttr = transitionAnimation.loadAnimationAttr(animationOptions.getPackageName(), animationOptions.getAnimations(), i2, z2);
                    }
                    animation = loadAnimationAttr;
                }
                animation = animationOptions.getCustomActivityTransition(z3);
                if (animation == null) {
                }
                animation = loadAnimationAttr;
            } else if (!z2 || z4 || (flags & 65794) != 0 || (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && !isOpeningType)) {
                animation = (CoreRune.FW_CUSTOM_SHELL_TRANSITION_RESUMED_AFFORDANCE && change.getResumedAffordance()) ? transitionAnimation.loadDefaultAnimationRes(i2) : transitionAnimation.loadDefaultAnimationAttr(i2, z2);
            }
        }
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 120456366, 196, "loadAnimation: anim=%s animAttr=0x%x type=%s isEntrance=%b", String.valueOf(animation), Long.valueOf(i2), String.valueOf(WindowManager.transitTypeToString(type)), Boolean.valueOf(isOpeningType));
        }
        return animation;
    }
}
