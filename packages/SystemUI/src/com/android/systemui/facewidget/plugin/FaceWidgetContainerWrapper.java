package com.android.systemui.facewidget.plugin;

import android.content.Context;
import android.view.View;
import android.view.ViewPropertyAnimator;
import com.android.app.animation.Interpolators;
import com.android.keyguard.KeyguardSecVisibilityHelper;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda12;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer;
import com.android.systemui.statusbar.phone.ScreenOffAnimation;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.io.PrintWriter;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class FaceWidgetContainerWrapper implements Dumpable {
    public View mClockContainer;
    public List mContentsContainerList;
    public final Context mContext;
    public View mFaceWidgetContainer;
    public NotificationPanelViewController$$ExternalSyntheticLambda12 mIsDLSViewEnabledSupplier = null;
    public KeyguardSecVisibilityHelper mKeyguardSecVisibilityHelper;
    public KeyguardStateController mKeyguardStateController;
    public final KeyguardStatusViewAlphaChangeControllerWrapper mKeyguardStatusViewAlphaChangeControllerWrapper;
    public KeyguardLogger mLogger;
    public DcmMascotViewContainer mMascotViewContainer;
    public PluginKeyguardStatusView mPluginKeyguardStatusView;
    public ScreenOffAnimationController mScreenOffAnimationController;

    public FaceWidgetContainerWrapper(Context context, KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper) {
        this.mContext = context;
        this.mKeyguardStatusViewAlphaChangeControllerWrapper = keyguardStatusViewAlphaChangeControllerWrapper;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
        if (pluginFaceWidgetManager != null) {
            PluginKeyguardStatusView pluginKeyguardStatusView = pluginFaceWidgetManager.mFaceWidgetPlugin;
            if (pluginKeyguardStatusView != null) {
                pluginKeyguardStatusView.dump(null, printWriter, strArr);
            }
            FaceWidgetWallpaperUtilsWrapper faceWidgetWallpaperUtilsWrapper = pluginFaceWidgetManager.mWallpaperUtilsWrapper;
            if (faceWidgetWallpaperUtilsWrapper != null) {
                faceWidgetWallpaperUtilsWrapper.dump(printWriter, strArr);
            }
        }
    }

    public final void initPlugin(PluginKeyguardStatusView pluginKeyguardStatusView, View view, List list) {
        this.mPluginKeyguardStatusView = pluginKeyguardStatusView;
        this.mFaceWidgetContainer = view;
        if (view != null) {
            this.mKeyguardSecVisibilityHelper = new KeyguardSecVisibilityHelper(this.mFaceWidgetContainer, this.mKeyguardStateController, this.mScreenOffAnimationController, true, this.mLogger.buffer);
        }
        this.mContentsContainerList = list;
        if (list == null || list.size() <= 0) {
            this.mClockContainer = null;
        } else {
            this.mClockContainer = (View) this.mContentsContainerList.get(0);
        }
    }

    public final void setKeyguardStatusViewVisibility(int i, int i2, boolean z, boolean z2) {
        Object obj;
        View view = this.mFaceWidgetContainer;
        if (view == null || view.animate() == null) {
            return;
        }
        View view2 = this.mFaceWidgetContainer;
        if (view2 == null) {
            view2 = new View(this.mContext);
        }
        view2.setTranslationY(0.0f);
        final KeyguardSecVisibilityHelper keyguardSecVisibilityHelper = this.mKeyguardSecVisibilityHelper;
        if (keyguardSecVisibilityHelper != null) {
            final IntConsumer intConsumer = new IntConsumer() { // from class: com.android.systemui.facewidget.plugin.FaceWidgetContainerWrapper$$ExternalSyntheticLambda0
                @Override // java.util.function.IntConsumer
                public final void accept(int i3) {
                    FaceWidgetContainerWrapper faceWidgetContainerWrapper = FaceWidgetContainerWrapper.this;
                    faceWidgetContainerWrapper.getClass();
                    try {
                        PluginKeyguardStatusView pluginKeyguardStatusView = faceWidgetContainerWrapper.mPluginKeyguardStatusView;
                        if (pluginKeyguardStatusView != null) {
                            pluginKeyguardStatusView.onKeyguardVisibilityHelperChanged(i3);
                        }
                    } catch (UndeclaredThrowableException e) {
                        e.printStackTrace();
                    }
                }
            };
            DcmMascotViewContainer dcmMascotViewContainer = this.mMascotViewContainer;
            final View view3 = this.mFaceWidgetContainer;
            if (view3 == null) {
                view3 = new View(this.mContext);
            }
            NotificationPanelViewController$$ExternalSyntheticLambda12 notificationPanelViewController$$ExternalSyntheticLambda12 = this.mIsDLSViewEnabledSupplier;
            boolean z3 = notificationPanelViewController$$ExternalSyntheticLambda12 != null && ((Boolean) notificationPanelViewController$$ExternalSyntheticLambda12.get()).booleanValue();
            keyguardSecVisibilityHelper.mMascotViewContainer = dcmMascotViewContainer;
            view3.animate().cancel();
            KeyguardStateController keyguardStateController = keyguardSecVisibilityHelper.mKeyguardStateController;
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController;
            boolean z4 = keyguardStateControllerImpl.mOccluded;
            keyguardSecVisibilityHelper.isVisibilityAnimating = false;
            if (z3 && i2 == 2 && i == 1) {
                view3.setVisibility(8);
                intConsumer.accept(8);
            } else if ((!z && i2 == 1 && i != 1) || z2) {
                keyguardSecVisibilityHelper.isVisibilityAnimating = true;
                view3.animate().alpha(0.0f).setStartDelay(0L).setDuration(160L).setInterpolator(Interpolators.ALPHA_OUT).withEndAction(new Runnable() { // from class: com.android.keyguard.KeyguardSecVisibilityHelper$setViewVisibility$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DcmMascotViewContainer dcmMascotViewContainer2;
                        KeyguardSecVisibilityHelper.this.isVisibilityAnimating = false;
                        view3.setVisibility(8);
                        IntConsumer intConsumer2 = intConsumer;
                        if (intConsumer2 != null) {
                            intConsumer2.accept(8);
                        }
                        if (!CscRune.KEYGUARD_DCM_LIVE_UX || (dcmMascotViewContainer2 = KeyguardSecVisibilityHelper.this.mMascotViewContainer) == null) {
                            return;
                        }
                        dcmMascotViewContainer2.getClass();
                        dcmMascotViewContainer2.setMascotViewVisible(8);
                    }
                });
                if (z) {
                    ViewPropertyAnimator startDelay = view3.animate().setStartDelay(keyguardStateControllerImpl.mKeyguardFadingAwayDelay);
                    keyguardStateController.getClass();
                    startDelay.setDuration(((KeyguardStateControllerImpl) keyguardStateController).mKeyguardFadingAwayDuration / 2).start();
                }
            } else if (i2 == 2 && i == 1) {
                view3.setVisibility(0);
                intConsumer.accept(0);
            } else if (i != 1) {
                view3.setVisibility(8);
                view3.setAlpha(1.0f);
                intConsumer.accept(8);
            } else if (z) {
                keyguardSecVisibilityHelper.isVisibilityAnimating = true;
                ViewPropertyAnimator withEndAction = view3.animate().alpha(0.0f).translationYBy((-view3.getHeight()) * 0.05f).setInterpolator(Interpolators.FAST_OUT_LINEAR_IN).withEndAction(new Runnable() { // from class: com.android.keyguard.KeyguardSecVisibilityHelper$setViewVisibility$animator$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DcmMascotViewContainer dcmMascotViewContainer2;
                        KeyguardSecVisibilityHelper.this.isVisibilityAnimating = false;
                        view3.setVisibility(4);
                        view3.setTranslationY(0.0f);
                        IntConsumer intConsumer2 = intConsumer;
                        if (intConsumer2 != null) {
                            intConsumer2.accept(4);
                        }
                        if (!CscRune.KEYGUARD_DCM_LIVE_UX || (dcmMascotViewContainer2 = KeyguardSecVisibilityHelper.this.mMascotViewContainer) == null) {
                            return;
                        }
                        dcmMascotViewContainer2.getClass();
                        dcmMascotViewContainer2.setMascotViewVisible(8);
                    }
                });
                if (keyguardSecVisibilityHelper.mAnimateYPos) {
                    float y = view3.getY() - (view3.getHeight() * 0.05f);
                    long j = 125;
                    AnimationProperties animationProperties = keyguardSecVisibilityHelper.mAnimationProperties;
                    animationProperties.duration = j;
                    long j2 = 0;
                    animationProperties.delay = j2;
                    AnimatableProperty.AnonymousClass7 anonymousClass7 = AnimatableProperty.Y;
                    PropertyAnimator.cancelAnimation(view3, anonymousClass7);
                    PropertyAnimator.setProperty(view3, anonymousClass7, y, animationProperties, true);
                    withEndAction.setDuration(j).setStartDelay(j2);
                }
                withEndAction.start();
            } else if (!keyguardSecVisibilityHelper.mLastOccludedState || z4) {
                ScreenOffAnimationController screenOffAnimationController = keyguardSecVisibilityHelper.mScreenOffAnimationController;
                List list = screenOffAnimationController.animations;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        if (((ScreenOffAnimation) it.next()).shouldAnimateInKeyguard()) {
                            keyguardSecVisibilityHelper.isVisibilityAnimating = true;
                            View view4 = keyguardSecVisibilityHelper.mView;
                            Iterator it2 = screenOffAnimationController.animations.iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    obj = null;
                                    break;
                                }
                                obj = it2.next();
                                ScreenOffAnimation screenOffAnimation = (ScreenOffAnimation) obj;
                                if (screenOffAnimation.shouldAnimateInKeyguard()) {
                                    screenOffAnimation.animateInKeyguard(view4, keyguardSecVisibilityHelper.mSetVisibleEndRunnable);
                                    break;
                                }
                            }
                        }
                    }
                }
                view3.setVisibility(0);
                view3.setAlpha(1.0f);
                intConsumer.accept(0);
            } else if (z3) {
                view3.setVisibility(8);
                intConsumer.accept(8);
            } else {
                view3.setVisibility(0);
                intConsumer.accept(0);
            }
            keyguardSecVisibilityHelper.mLastOccludedState = z4;
        }
        if (CscRune.KEYGUARD_DCM_LIVE_UX) {
            if (z || i == 0) {
                this.mMascotViewContainer.setMascotViewVisible(4);
                return;
            }
            DcmMascotViewContainer dcmMascotViewContainer2 = this.mMascotViewContainer;
            View view5 = this.mFaceWidgetContainer;
            dcmMascotViewContainer2.setMascotViewVisible(view5 != null ? view5.getVisibility() : 8);
        }
    }
}
