package com.android.wm.shell.transition;

import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.window.TransitionInfo;
import com.android.internal.policy.TransitionAnimation;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransitionUtil;
import com.samsung.android.rune.CoreRune;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class TransitionAnimationHelper {

    public class RoundedContentPerDisplay implements DisplayInsetsController.OnInsetsChangedListener {
        public final Rect mBounds = new Rect();

        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        public final void insetsChanged(InsetsState insetsState) {
            Insets insetsMax = Insets.NONE;
            for (int iSourceSize = insetsState.sourceSize() - 1; iSourceSize >= 0; iSourceSize--) {
                InsetsSource insetsSourceSourceAt = insetsState.sourceAt(iSourceSize);
                if (insetsSourceSourceAt.hasFlags(2)) {
                    insetsMax = Insets.max(insetsSourceSourceAt.calculateInsets(insetsState.getDisplayFrame(), false), insetsMax);
                }
            }
            this.mBounds.set(insetsState.getDisplayFrame());
            this.mBounds.inset(insetsMax);
        }
    }

    public class RoundedContentTracker implements DisplayController.OnDisplaysChangedListener {
        public final DisplayController mDisplayController;
        public final DisplayInsetsController mDisplayInsetsController;
        public final SparseArray mPerDisplay = new SparseArray();

        public RoundedContentTracker(DisplayController displayController, DisplayInsetsController displayInsetsController) {
            this.mDisplayController = displayController;
            this.mDisplayInsetsController = displayInsetsController;
        }

        @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
        public final void onDisplayAdded(int i) {
            RoundedContentPerDisplay roundedContentPerDisplay = new RoundedContentPerDisplay();
            this.mDisplayInsetsController.addInsetsChangedListener(i, roundedContentPerDisplay);
            this.mPerDisplay.put(i, roundedContentPerDisplay);
            DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(i);
            roundedContentPerDisplay.mBounds.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
        }

        @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
        public final void onDisplayRemoved(int i) {
            RoundedContentPerDisplay roundedContentPerDisplay = (RoundedContentPerDisplay) this.mPerDisplay.removeReturnOld(i);
            if (roundedContentPerDisplay != null) {
                this.mDisplayInsetsController.removeInsetsChangedListener(i, roundedContentPerDisplay);
            }
        }
    }

    public static void addBackgroundToTransition(SurfaceControl surfaceControl, int i, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        if (i == 0) {
            return;
        }
        Color colorValueOf = Color.valueOf(i);
        float[] fArr = {colorValueOf.red(), colorValueOf.green(), colorValueOf.blue()};
        SurfaceControl surfaceControlBuild = new SurfaceControl.Builder().setName("Animation Background").setParent(surfaceControl).setColorLayer().setOpaque(true).setCallsite("TransitionAnimationHelper.addBackgroundToTransition").build();
        transaction.setLayer(surfaceControlBuild, Integer.MIN_VALUE).setColor(surfaceControlBuild, fArr).show(surfaceControlBuild);
        transaction2.remove(surfaceControlBuild);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x005a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int getTransitionTypeFromInfo(TransitionInfo transitionInfo) {
        int type = transitionInfo.getType();
        if ((type == 13 || type == 14) && !transitionInfo.getChanges().isEmpty()) {
            return TransitionUtil.isOpeningMode(((TransitionInfo.Change) transitionInfo.getChanges().get(0)).getMode()) ? 1 : 2;
        }
        if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX) {
            for (TransitionInfo.Change change : transitionInfo.getChanges()) {
                if (change.getAnimationOptions() != null && change.getAnimationOptions().getType() == 1) {
                    break;
                }
            }
            if (type == 1) {
                for (TransitionInfo.Change change2 : transitionInfo.getChanges()) {
                    if ((change2.getTaskInfo() == null && !change2.hasFlags(32)) || TransitionUtil.isOrderOnly(change2)) {
                        if (change2.getTaskInfo() == null || !change2.hasFlags(65826)) {
                            if (change2.getMode() == 1) {
                                return type;
                            }
                        }
                    }
                }
                return 2;
            }
        } else if (type == 1) {
        }
        return type;
    }

    public static boolean isCoveredByOpaqueFullscreenChange(TransitionInfo.Change change, TransitionInfo transitionInfo) {
        boolean z;
        Iterator it = transitionInfo.getChanges().iterator();
        while (true) {
            z = false;
            if (!it.hasNext()) {
                break;
            }
            TransitionInfo.Change change2 = (TransitionInfo.Change) it.next();
            if (change2 == change) {
                return false;
            }
            if ((change2.getFlags() & 4) == 0 && change2.getTaskInfo() != null) {
                z = true;
                if (change2.getTaskInfo().getWindowingMode() == 1) {
                    break;
                }
            }
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:137:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x009b A[PHI: r6
      0x009b: PHI (r6v19 boolean) = (r6v4 boolean), (r6v11 boolean), (r6v18 boolean), (r6v21 boolean) binds: [B:113:0x0114, B:81:0x00d0, B:98:0x00f2, B:54:0x009a] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Animation loadAttributeAnimation(int i, TransitionInfo transitionInfo, TransitionInfo.Change change, int i2, TransitionAnimation transitionAnimation, boolean z) {
        int i3;
        int i4;
        boolean z2;
        boolean z3;
        Animation animationLoadAnimationAttr;
        int mode = change.getMode();
        int flags = change.getFlags();
        boolean zIsOpeningType = TransitionUtil.isOpeningType(mode);
        boolean z4 = change.getTaskInfo() != null;
        boolean z5 = z4 && change.getTaskInfo().isFreeform();
        boolean zIsCoveredByOpaqueFullscreenChange = isCoveredByOpaqueFullscreenChange(change, transitionInfo);
        TransitionInfo.AnimationOptions animationOptions = change.getAnimationOptions();
        int type = animationOptions != null ? animationOptions.getType() : 0;
        if (z) {
            if (i == 1) {
                i4 = zIsOpeningType ? 28 : 29;
            } else {
                if (i == 2) {
                    i4 = zIsOpeningType ? 0 : 27;
                }
                z2 = false;
                i4 = 0;
            }
            z2 = false;
        } else {
            if (i2 == 4) {
                i4 = zIsOpeningType ? 20 : 21;
            } else if (i2 == 5) {
                i4 = zIsOpeningType ? 22 : 23;
            } else if (i2 == 2) {
                i4 = zIsOpeningType ? 16 : 17;
            } else {
                if (i2 == 3) {
                    i3 = zIsOpeningType ? 18 : 19;
                } else if (!zIsCoveredByOpaqueFullscreenChange && z5 && TransitionUtil.isOpeningMode(i) && change.getMode() == 4) {
                    z2 = (flags & 4) != 0;
                    i4 = 7;
                } else {
                    if (!zIsCoveredByOpaqueFullscreenChange && z5 && i == 3 && change.getMode() == 3) {
                        z2 = (flags & 4) != 0;
                    } else if (CoreRune.FW_SHELL_TRANSITION_EXTENSION && i2 == 6) {
                        i4 = zIsOpeningType ? 4 : 0;
                        z2 = true;
                    } else if (i == 1) {
                        z2 = (flags & 4) != 0;
                        if (z4 && z2 && !zIsOpeningType) {
                            i4 = 7;
                        } else if (z4 && !z2) {
                            i4 = zIsOpeningType ? 8 : 9;
                        } else if (!zIsOpeningType) {
                            i4 = 5;
                        }
                    } else if (i == 3) {
                        if (!CoreRune.FW_SHELL_TRANSITION_BUG_FIX || !z4 || (flags & 4) == 0 || zIsOpeningType) {
                            if (zIsOpeningType) {
                                i4 = 12;
                            } else {
                                i3 = 13;
                            }
                        }
                        i4 = 7;
                    } else if (i == 2) {
                        z2 = ((flags & 4) == 0 || zIsOpeningType) ? false : true;
                        if (z4 && !z2) {
                            i4 = zIsOpeningType ? 10 : 11;
                        } else if (zIsOpeningType) {
                            i4 = 6;
                        }
                    } else {
                        if (i == 4) {
                            if (zIsOpeningType) {
                                i4 = 14;
                            } else {
                                i3 = 15;
                            }
                        }
                        z2 = false;
                        i4 = 0;
                    }
                    i4 = 4;
                }
                i4 = i3;
            }
            z2 = false;
        }
        Animation animationLoadDefaultAnimationAttr = null;
        if (i4 != 0) {
            if (type == 14 && !z4) {
                if (i4 == 4 || i4 == 5) {
                    z3 = true;
                } else {
                    if (i4 == 6 || i4 == 7) {
                        z3 = false;
                    }
                    if (animationLoadDefaultAnimationAttr == null) {
                        animationLoadAnimationAttr = transitionAnimation.loadAppTransitionAnimation(animationOptions.getPackageName(), zIsOpeningType ? animationLoadDefaultAnimationAttr.getCustomEnterResId() : animationLoadDefaultAnimationAttr.getCustomExitResId());
                        if (animationLoadAnimationAttr != null && animationLoadDefaultAnimationAttr.getCustomBackgroundColor() != 0) {
                            animationLoadAnimationAttr.setBackdropColor(animationLoadDefaultAnimationAttr.getCustomBackgroundColor());
                        }
                    } else {
                        animationLoadAnimationAttr = transitionAnimation.loadAnimationAttr(animationOptions.getPackageName(), animationOptions.getAnimations(), i4, z2);
                    }
                    animationLoadDefaultAnimationAttr = animationLoadAnimationAttr;
                }
                animationLoadDefaultAnimationAttr = animationOptions.getCustomActivityTransition(z3);
                if (animationLoadDefaultAnimationAttr == null) {
                }
                animationLoadDefaultAnimationAttr = animationLoadAnimationAttr;
            } else if (!z2 || z4 || (flags & 65794) != 0) {
                animationLoadDefaultAnimationAttr = transitionAnimation.loadDefaultAnimationAttr(i4, z2);
            }
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -5172949229302105239L, 196, String.valueOf(animationLoadDefaultAnimationAttr), Long.valueOf(i4), String.valueOf(Transitions.transitTypeToString(i)), Boolean.valueOf(zIsOpeningType));
        }
        if (CoreRune.FW_SHELL_TRANSITION_LOG) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -6527218358856370542L, 12, String.valueOf(TransitionAnimation.wallpaperTransitTypeToString(i2)), Boolean.valueOf(z2));
            }
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 4344526971070525671L, 0, String.valueOf(change));
            }
        }
        return animationLoadDefaultAnimationAttr;
    }
}
