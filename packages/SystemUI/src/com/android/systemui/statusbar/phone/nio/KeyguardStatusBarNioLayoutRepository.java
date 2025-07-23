package com.android.systemui.statusbar.phone.nio;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.R;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorGardenModel;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SettingsHelper;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class KeyguardStatusBarNioLayoutRepository implements BootAnimationFinishedCache.BootAnimationFinishedListener, ConfigurationController.ConfigurationListener, IndicatorGardenPresenter.GardenListener, View.OnLayoutChangeListener, SettingsHelper.OnChangedCallback, OngoingCallListener {
    public final BootAnimationFinishedCache bootAnimationFinishedCache;
    public final ConfigurationController configController;
    public final Context context;
    public final IndicatorGardenPresenter gardenPresenter;
    public final IndicatorScaleGardener gardenScale;
    public final List listeners = new ArrayList();
    public final Handler mainHandler;
    public View nioContainerView;
    public final KeyguardStatusBarNioLayoutModel nioLayoutModel;
    public final OngoingCallController ongoingCallController;
    private final SettingsHelper settingsHelper;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            KeyguardStatusBarNioLayoutRepository.this.notifyNioUpdated();
        }
    }

    public KeyguardStatusBarNioLayoutRepository(Context context, Handler handler, BootAnimationFinishedCache bootAnimationFinishedCache, ConfigurationController configurationController, KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel, IndicatorScaleGardener indicatorScaleGardener, IndicatorGardenPresenter indicatorGardenPresenter, SettingsHelper settingsHelper, OngoingCallController ongoingCallController) {
        this.context = context;
        this.mainHandler = handler;
        this.bootAnimationFinishedCache = bootAnimationFinishedCache;
        this.configController = configurationController;
        this.nioLayoutModel = keyguardStatusBarNioLayoutModel;
        this.gardenScale = indicatorScaleGardener;
        this.gardenPresenter = indicatorGardenPresenter;
        this.settingsHelper = settingsHelper;
        this.ongoingCallController = ongoingCallController;
        keyguardStatusBarNioLayoutModel.updateRing = new AnonymousClass1();
        ((BootAnimationFinishedCacheImpl) bootAnimationFinishedCache).addListener(this);
    }

    public final void notifyNioUpdated() {
        if (this.nioContainerView != null && updateNioLayoutMargin()) {
            printLog(" SKIP notifyNioUpdated() - onLayoutChanged will be called or nioContainerView is null? " + (this.nioContainerView == null) + " Next Ring will be coming.");
            this.nioLayoutModel.isUpdatedModel = true;
            return;
        }
        if (!((BootAnimationFinishedCacheImpl) this.bootAnimationFinishedCache).bootAnimationFinished.get()) {
            printLog(" SKIP notifyNioUpdated() - isBootAnimationFinished not yet ");
            return;
        }
        printLog(" * notifyNioUpdated() !!!  size[" + this.listeners.size() + "]  ");
        for (PluginFaceWidgetManager$$ExternalSyntheticLambda0 pluginFaceWidgetManager$$ExternalSyntheticLambda0 : this.listeners) {
            if (this.listeners.contains(pluginFaceWidgetManager$$ExternalSyntheticLambda0)) {
                pluginFaceWidgetManager$$ExternalSyntheticLambda0.onNioLayoutUpdated(this.nioLayoutModel);
            }
        }
    }

    @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
    public final void onBootAnimationFinished() {
        printLog("onBootAnimationFinished() isBootAnimationFinished:" + ((BootAnimationFinishedCacheImpl) this.bootAnimationFinishedCache).bootAnimationFinished.get() + " ");
        ((ConfigurationControllerImpl) this.configController).addCallback(this);
        this.gardenPresenter.addCallback((IndicatorGardenPresenter.GardenListener) this);
        this.settingsHelper.registerCallback(this, Settings.System.getUriFor(SettingsHelper.INDEX_LOCKSCREEN_MINIMIZING_NOTIFICATION), Settings.Secure.getUriFor(SettingsHelper.INDEX_LOCK_SCREEN_SHOW_NOTIFICATIONS));
        this.ongoingCallController.addCallback((OngoingCallListener) this);
        notifyNioUpdated();
    }

    @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
    public final void onChanged(Uri uri) {
        final boolean isShowNotificationOnKeyguard = this.settingsHelper.isShowNotificationOnKeyguard();
        boolean isNotificationAsDot = this.settingsHelper.isNotificationAsDot();
        final boolean isNotificationIconsOnlyOn = this.settingsHelper.isNotificationIconsOnlyOn();
        boolean isNotificationAsCard = this.settingsHelper.isNotificationAsCard();
        StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("SettingsHelper.onChanged() showOnKeyguard:", ", dot?", ", icon?", isShowNotificationOnKeyguard, isNotificationAsDot);
        m.append(isNotificationIconsOnlyOn);
        m.append(", card?");
        m.append(isNotificationAsCard);
        printLog(m.toString());
        final KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel = this.nioLayoutModel;
        keyguardStatusBarNioLayoutModel.updateValues(new Runnable() { // from class: com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository$onChanged$1$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel2 = KeyguardStatusBarNioLayoutModel.this;
                boolean z = isShowNotificationOnKeyguard;
                if (keyguardStatusBarNioLayoutModel2.isShowNotificationOnKeyguard != z) {
                    keyguardStatusBarNioLayoutModel2.isShowNotificationOnKeyguard = z;
                    keyguardStatusBarNioLayoutModel2.isUpdatedModel = true;
                }
                boolean z2 = isNotificationIconsOnlyOn;
                if (keyguardStatusBarNioLayoutModel2.isNotificationIconsOnlyOn != z2) {
                    keyguardStatusBarNioLayoutModel2.isNotificationIconsOnlyOn = z2;
                    keyguardStatusBarNioLayoutModel2.isUpdatedModel = true;
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDensityOrFontScaleChanged() {
        this.mainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository$onDensityOrFontScaleChanged$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardStatusBarNioLayoutRepository.this.printLog("onDensityOrFontScaleChanged()");
                final KeyguardStatusBarNioLayoutRepository keyguardStatusBarNioLayoutRepository = KeyguardStatusBarNioLayoutRepository.this;
                final KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel = keyguardStatusBarNioLayoutRepository.nioLayoutModel;
                keyguardStatusBarNioLayoutModel.updateValues(new Runnable() { // from class: com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository$onDensityOrFontScaleChanged$1$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel2 = KeyguardStatusBarNioLayoutModel.this;
                        KeyguardStatusBarNioLayoutRepository keyguardStatusBarNioLayoutRepository2 = keyguardStatusBarNioLayoutRepository;
                        int i = keyguardStatusBarNioLayoutRepository2.gardenScale.getLatestScaleModel(keyguardStatusBarNioLayoutRepository2.context).iconSize;
                        if (keyguardStatusBarNioLayoutModel2.iconSize != i) {
                            keyguardStatusBarNioLayoutModel2.iconSize = i;
                            keyguardStatusBarNioLayoutModel2.isUpdatedModel = true;
                        }
                        KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel3 = KeyguardStatusBarNioLayoutModel.this;
                        KeyguardStatusBarNioLayoutRepository keyguardStatusBarNioLayoutRepository3 = keyguardStatusBarNioLayoutRepository;
                        float f = keyguardStatusBarNioLayoutRepository3.gardenScale.getLatestScaleModel(keyguardStatusBarNioLayoutRepository3.context).ratio;
                        if (keyguardStatusBarNioLayoutModel3.iconScaleRatio == f) {
                            return;
                        }
                        keyguardStatusBarNioLayoutModel3.iconScaleRatio = f;
                        keyguardStatusBarNioLayoutModel3.isUpdatedModel = true;
                    }
                });
            }
        });
    }

    @Override // com.android.systemui.statusbar.phone.IndicatorGardenPresenter.GardenListener
    public final void onGardenChanged(final IndicatorGardenModel indicatorGardenModel) {
        printLog("onGardenChanged()");
        final KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel = this.nioLayoutModel;
        keyguardStatusBarNioLayoutModel.updateValues(new Runnable() { // from class: com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository$onGardenChanged$1$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel2 = KeyguardStatusBarNioLayoutModel.this;
                IndicatorGardenModel indicatorGardenModel2 = indicatorGardenModel;
                int i = indicatorGardenModel2.paddingLeft;
                if (keyguardStatusBarNioLayoutModel2.paddingLeft != i) {
                    keyguardStatusBarNioLayoutModel2.paddingLeft = i;
                    keyguardStatusBarNioLayoutModel2.isUpdatedModel = true;
                }
                int i2 = indicatorGardenModel2.paddingRight;
                if (keyguardStatusBarNioLayoutModel2.paddingRight != i2) {
                    keyguardStatusBarNioLayoutModel2.paddingRight = i2;
                    keyguardStatusBarNioLayoutModel2.isUpdatedModel = true;
                }
                int i3 = indicatorGardenModel2.totalHeight;
                if (keyguardStatusBarNioLayoutModel2.totalHeight != i3) {
                    keyguardStatusBarNioLayoutModel2.totalHeight = i3;
                    keyguardStatusBarNioLayoutModel2.isUpdatedModel = true;
                }
                int i4 = indicatorGardenModel2.cameraTopMargin;
                if (keyguardStatusBarNioLayoutModel2.topMargin != i4) {
                    keyguardStatusBarNioLayoutModel2.topMargin = i4;
                    keyguardStatusBarNioLayoutModel2.isUpdatedModel = true;
                }
                int i5 = indicatorGardenModel2.cameraBottomMargin;
                if (keyguardStatusBarNioLayoutModel2.bottomMargin != i5) {
                    keyguardStatusBarNioLayoutModel2.bottomMargin = i5;
                    keyguardStatusBarNioLayoutModel2.isUpdatedModel = true;
                }
                int i6 = indicatorGardenModel2.maxWidthLeftContainer;
                if (keyguardStatusBarNioLayoutModel2.containerEndX != i6) {
                    keyguardStatusBarNioLayoutModel2.containerEndX = i6;
                    keyguardStatusBarNioLayoutModel2.isUpdatedModel = true;
                }
            }
        });
    }

    @Override // android.view.View.OnLayoutChangeListener
    public final void onLayoutChange(View view, final int i, int i2, final int i3, int i4, int i5, int i6, int i7, int i8) {
        final KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel = this.nioLayoutModel;
        keyguardStatusBarNioLayoutModel.updateValues(new Runnable() { // from class: com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository$onLayoutChange$1$1
            @Override // java.lang.Runnable
            public final void run() {
                int m;
                KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel2 = KeyguardStatusBarNioLayoutModel.this;
                if (keyguardStatusBarNioLayoutModel2.isRtl) {
                    m = StrongAuthPopup$$ExternalSyntheticOutline0.m(this.context, R.dimen.keyguard_carrier_text_nio_default_margin, i3) - i;
                } else {
                    m = StrongAuthPopup$$ExternalSyntheticOutline0.m(this.context, R.dimen.keyguard_carrier_text_nio_default_margin, i3);
                }
                if (keyguardStatusBarNioLayoutModel2.containerStartX != m) {
                    keyguardStatusBarNioLayoutModel2.containerStartX = m;
                    keyguardStatusBarNioLayoutModel2.isUpdatedModel = true;
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onLayoutDirectionChanged(final boolean z) {
        printLog("onLayoutDirectionChanged(rtl:" + z + ")");
        final KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel = this.nioLayoutModel;
        keyguardStatusBarNioLayoutModel.updateValues(new Runnable() { // from class: com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository$onLayoutDirectionChanged$1$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel2 = KeyguardStatusBarNioLayoutModel.this;
                boolean z2 = z;
                if (keyguardStatusBarNioLayoutModel2.isRtl != z2) {
                    keyguardStatusBarNioLayoutModel2.isRtl = z2;
                    keyguardStatusBarNioLayoutModel2.isUpdatedModel = true;
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener
    public final void onOngoingCallStateChanged() {
        final boolean hasOngoingCall = this.ongoingCallController.hasOngoingCall();
        printLog("onOngoingCallStateChanged(" + hasOngoingCall + ")");
        final KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel = this.nioLayoutModel;
        keyguardStatusBarNioLayoutModel.updateValues(new Runnable() { // from class: com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository$onOngoingCallStateChanged$1$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel2 = KeyguardStatusBarNioLayoutModel.this;
                boolean z = hasOngoingCall;
                if (keyguardStatusBarNioLayoutModel2.visibleCallChip != z) {
                    keyguardStatusBarNioLayoutModel2.visibleCallChip = z;
                    keyguardStatusBarNioLayoutModel2.isUpdatedModel = true;
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onSmallestScreenWidthChanged() {
        this.mainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository$onSmallestScreenWidthChanged$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardStatusBarNioLayoutRepository.this.printLog("onSmallestScreenWidthChanged()");
                final KeyguardStatusBarNioLayoutRepository keyguardStatusBarNioLayoutRepository = KeyguardStatusBarNioLayoutRepository.this;
                final KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel = keyguardStatusBarNioLayoutRepository.nioLayoutModel;
                keyguardStatusBarNioLayoutModel.updateValues(new Runnable() { // from class: com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository$onSmallestScreenWidthChanged$1$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel2 = KeyguardStatusBarNioLayoutModel.this;
                        KeyguardStatusBarNioLayoutRepository keyguardStatusBarNioLayoutRepository2 = keyguardStatusBarNioLayoutRepository;
                        int i = keyguardStatusBarNioLayoutRepository2.gardenScale.getLatestScaleModel(keyguardStatusBarNioLayoutRepository2.context).iconSize;
                        if (keyguardStatusBarNioLayoutModel2.iconSize != i) {
                            keyguardStatusBarNioLayoutModel2.iconSize = i;
                            keyguardStatusBarNioLayoutModel2.isUpdatedModel = true;
                        }
                        KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel3 = KeyguardStatusBarNioLayoutModel.this;
                        KeyguardStatusBarNioLayoutRepository keyguardStatusBarNioLayoutRepository3 = keyguardStatusBarNioLayoutRepository;
                        float f = keyguardStatusBarNioLayoutRepository3.gardenScale.getLatestScaleModel(keyguardStatusBarNioLayoutRepository3.context).ratio;
                        if (keyguardStatusBarNioLayoutModel3.iconScaleRatio == f) {
                            return;
                        }
                        keyguardStatusBarNioLayoutModel3.iconScaleRatio = f;
                        keyguardStatusBarNioLayoutModel3.isUpdatedModel = true;
                    }
                });
            }
        });
    }

    public final void printLog(String str) {
        KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel = this.nioLayoutModel;
        keyguardStatusBarNioLayoutModel.getClass();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        int i = keyguardStatusBarNioLayoutModel.paddingLeft;
        int i2 = keyguardStatusBarNioLayoutModel.paddingRight;
        int i3 = keyguardStatusBarNioLayoutModel.totalHeight;
        int i4 = keyguardStatusBarNioLayoutModel.topMargin;
        int i5 = keyguardStatusBarNioLayoutModel.bottomMargin;
        int endMargin = keyguardStatusBarNioLayoutModel.getEndMargin();
        boolean z = keyguardStatusBarNioLayoutModel.isRtl;
        int i6 = keyguardStatusBarNioLayoutModel.containerEndX;
        int i7 = keyguardStatusBarNioLayoutModel.containerStartX;
        int i8 = keyguardStatusBarNioLayoutModel.iconSize;
        String format = decimalFormat.format(Float.valueOf(keyguardStatusBarNioLayoutModel.iconScaleRatio));
        boolean z2 = keyguardStatusBarNioLayoutModel.visibleCallChip;
        boolean z3 = keyguardStatusBarNioLayoutModel.isShowNotificationOnKeyguard;
        boolean z4 = keyguardStatusBarNioLayoutModel.isNotificationIconsOnlyOn;
        int i9 = keyguardStatusBarNioLayoutModel.numberOfNio;
        String format2 = decimalFormat.format(Float.valueOf(keyguardStatusBarNioLayoutModel.keyguardStatusBarViewAlpha));
        int i10 = keyguardStatusBarNioLayoutModel.keyguardStatusBarViewVisibility;
        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "[pL:", ", pR:", ", H:");
        ViewPager$$ExternalSyntheticOutline0.m(m, i3, ", mT:", i4, ", mB:");
        ViewPager$$ExternalSyntheticOutline0.m(m, i5, ", mR:", endMargin, ", rtl?");
        m.append(z);
        m.append(", (x2:");
        m.append(i6);
        m.append(" - x1:");
        ViewPager$$ExternalSyntheticOutline0.m(m, i7, " = avW:", i6 - i7, "), iconSize:");
        m.append(i8);
        m.append(", ratio:");
        m.append(format);
        m.append(", callChip?");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, z2, ", settingsShow?", z3, ", settingNio?");
        m.append(z4);
        m.append(", numberOfNio?");
        m.append(i9);
        m.append(", KSBV(a:");
        m.append(format2);
        m.append("-v:");
        m.append(i10);
        m.append(")] ");
        MediaSessions$H$$ExternalSyntheticOutline0.m(" ", str, "     ", m.toString(), "KeyguardStatusBarNioLayoutRepository");
    }

    public final boolean updateNioLayoutMargin() {
        View view = this.nioContainerView;
        if (view == null) {
            return false;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (marginLayoutParams.getMarginEnd() == this.nioLayoutModel.getEndMargin()) {
            return false;
        }
        printLog("updateNioLayoutMargin() " + marginLayoutParams.getMarginEnd() + " >> " + this.nioLayoutModel.getEndMargin());
        marginLayoutParams.setMarginEnd(this.nioLayoutModel.getEndMargin());
        view.setLayoutParams(marginLayoutParams);
        return true;
    }
}
