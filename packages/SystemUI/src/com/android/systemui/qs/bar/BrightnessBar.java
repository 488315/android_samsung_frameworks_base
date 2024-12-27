package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieAnimationView;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qs.QSBackupRestoreManager;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.settings.brightness.BrightnessAnimationIcon;
import com.android.systemui.settings.brightness.BrightnessController;
import com.android.systemui.settings.brightness.BrightnessController.AnonymousClass10;
import com.android.systemui.settings.brightness.BrightnessDetail;
import com.android.systemui.settings.brightness.BrightnessDetailAdapter;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.settings.brightness.BrightnessSliderView;
import com.android.systemui.settings.brightness.SecBrightnessController;
import com.android.systemui.settings.brightness.SecBrightnessSliderController;
import com.android.systemui.settings.brightness.SecBrightnessSliderView;
import com.android.systemui.settings.brightness.ToggleSeekBar;
import com.android.systemui.settings.brightness.ToggleSlider;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.SecBrightnessMirrorControllerProvider;
import com.android.systemui.statusbar.policy.BrightnessMirrorController;
import com.android.systemui.statusbar.policy.QuickTileBrightnessMirrorDummyView;
import com.android.systemui.statusbar.policy.SecBrightnessMirrorController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.RecoilEffectUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Objects;

public final class BrightnessBar extends BarItemImpl implements TunerService.Tunable, TileHostable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mBarBottomMargin;
    public LinearLayout mBrightnessBarContainer;
    public BrightnessController mBrightnessController;
    public final BrightnessController.Factory mBrightnessControllerFactory;
    public ImageView mBrightnessDetailIcon;
    public BrightnessMirrorController mBrightnessMirrorController;
    public final AnonymousClass2 mBrightnessMirrorListener;
    public BrightnessSliderController mBrightnessSliderController;
    public final BrightnessSliderController.Factory mBrightnessSliderControllerFactory;
    public BrightnessAnimationIcon mCloneBrightnessIcon;
    public final Context mContext;
    public boolean mIsAllowedOnTop;
    public final QSBackupRestoreManager mQSBackupRestoreManager;
    public final SecQSDetailController mQSDetailController;
    public final SecQSPanelResourcePicker mResourcePicker;
    public SecBrightnessMirrorController mSecBrightnessMirrorController;
    public final Lazy mSecBrightnessMirrorControllerProviderLazy;
    public SecBrightnessSliderController mSecBrightnessSliderController;
    private final SettingsHelper mSettingsHelper;
    public RelativeLayout mSliderContainer;
    public LinearLayout mTileLayout;
    public final ArrayList mTiles;
    public ToggleSeekBar mToggleSeekBar;
    public final TunerService mTunerService;

    /* renamed from: com.android.systemui.qs.bar.BrightnessBar$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

    static {
        Settings.System.getUriFor(SettingsHelper.INDEX_EMERGENCY_MODE);
    }

    public BrightnessBar(Context context, BrightnessController.Factory factory, BrightnessSliderController.Factory factory2, Lazy lazy, TunerService tunerService, SettingsHelper settingsHelper, QSBackupRestoreManager qSBackupRestoreManager, SecQSDetailController secQSDetailController) {
        super(context);
        this.mBarBottomMargin = 0;
        this.mTiles = new ArrayList();
        this.mBrightnessMirrorListener = new AnonymousClass2();
        this.mContext = context;
        this.mBrightnessControllerFactory = factory;
        this.mBrightnessSliderControllerFactory = factory2;
        this.mSecBrightnessMirrorControllerProviderLazy = lazy;
        this.mTunerService = tunerService;
        this.mSettingsHelper = settingsHelper;
        this.mQSBackupRestoreManager = qSBackupRestoreManager;
        this.mQSDetailController = secQSDetailController;
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        tunerService.addTunable(this, "brightness_on_top");
        this.mIsAllowedOnTop = tunerService.getValue(1, "brightness_on_top") != 0;
        qSBackupRestoreManager.addCallback("BrightnessOnTop", new QSBackupRestoreManager.Callback() { // from class: com.android.systemui.qs.bar.BrightnessBar.1
            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final boolean isValidDB() {
                boolean z = true;
                int value = BrightnessBar.this.mTunerService.getValue(1, "brightness_on_top");
                if (value != 0 && value != 1) {
                    z = false;
                }
                KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("brightnessOnTop : ", value, " valid : ", z, "BrightnessOnTop");
                return z;
            }

            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final String onBackup(boolean z) {
                StringBuilder sb = new StringBuilder("TAG::brightness_on_top::");
                BrightnessBar brightnessBar = BrightnessBar.this;
                if (z) {
                    String value = brightnessBar.mTunerService.getValue("brightness_on_top");
                    sb.append(value != null ? value : "0");
                } else {
                    brightnessBar.getClass();
                    sb.append("0");
                }
                Log.d("BrightnessOnTop", "builder : " + ((Object) sb));
                return sb.toString();
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v1 */
            /* JADX WARN: Type inference failed for: r0v2, types: [boolean, int] */
            /* JADX WARN: Type inference failed for: r0v3 */
            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final void onRestore(String str) {
                BrightnessBar brightnessBar = BrightnessBar.this;
                brightnessBar.getClass();
                String[] split = str.split("::");
                if (split[0].equals("brightness_on_top")) {
                    ?? r0 = Integer.parseInt(split[1]) == 1 ? 1 : 0;
                    StringBuilder m = RowView$$ExternalSyntheticOutline0.m("isAllowedOnTop : ", "   Integer.parseInt(sp[1]) : ", r0);
                    m.append(Integer.parseInt(split[1]));
                    Log.d("BrightnessOnTop", m.toString());
                    if (brightnessBar.mIsAllowedOnTop != r0) {
                        brightnessBar.mIsAllowedOnTop = r0;
                        brightnessBar.mTunerService.setValue((int) r0, "brightness_on_top");
                    }
                }
            }
        });
    }

    @Override // com.android.systemui.qs.bar.TileHostable
    public final void addTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        this.mTiles.add(tileRecord);
        LinearLayout linearLayout = this.mTileLayout;
        if (linearLayout != null) {
            linearLayout.addView(tileRecord.tileView);
            updateTileLayoutSizeMargins(this.mTileLayout);
        }
        SecBrightnessMirrorController secBrightnessMirrorController = this.mSecBrightnessMirrorController;
        if (secBrightnessMirrorController != null) {
            ArrayList arrayList = this.mTiles;
            QuickTileBrightnessMirrorDummyView quickTileBrightnessMirrorDummyView = secBrightnessMirrorController.quickTileBrightnessMirrorDummyView;
            if (quickTileBrightnessMirrorDummyView != null) {
                ArrayList arrayList2 = quickTileBrightnessMirrorDummyView.tiles;
                arrayList2.clear();
                arrayList2.addAll(arrayList);
            }
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        this.mCallback = null;
        this.mTunerService.removeTunable(this);
        BrightnessMirrorController brightnessMirrorController = this.mBrightnessMirrorController;
        if (brightnessMirrorController != null) {
            brightnessMirrorController.mBrightnessMirrorListeners.remove(this.mBrightnessMirrorListener);
        }
        BrightnessController brightnessController = this.mBrightnessController;
        if (brightnessController != null) {
            Handler handler = brightnessController.mBackgroundHandler;
            BrightnessController.AnonymousClass3 anonymousClass3 = brightnessController.mStopListeningRunnable;
            handler.removeCallbacks(anonymousClass3);
            handler.post(anonymousClass3);
            brightnessController.mControlValueInitialized = false;
        }
        this.mQSBackupRestoreManager.removeCallback("BrightnessOnTop");
        removeAllTiles();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarHeight() {
        if (!this.mShowing) {
            return 0;
        }
        boolean z = this.mIsOnCollapsedState;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        return z ? secQSPanelResourcePicker.getBrightnessBarHeight(this.mContext) + this.mBarBottomMargin : secQSPanelResourcePicker.getBrightnessBarContainerHeight(this.mContext);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return 0;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        FrameLayout frameLayout;
        BrightnessSliderController create = this.mBrightnessSliderControllerFactory.create(this.mContext, viewGroup);
        this.mBrightnessSliderController = create;
        View rootView = create.getRootView();
        this.mBarRootView = rootView;
        this.mBrightnessBarContainer = (LinearLayout) rootView.findViewById(R.id.brightness_bar_container);
        this.mTileLayout = (LinearLayout) this.mBarRootView.findViewById(R.id.brightness_tile_layout);
        this.mSliderContainer = (RelativeLayout) this.mBarRootView.findViewById(R.id.slider_container);
        this.mToggleSeekBar = (ToggleSeekBar) this.mBarRootView.findViewById(R.id.slider);
        this.mBrightnessSliderController.init();
        ImageView imageView = (ImageView) this.mBarRootView.findViewById(R.id.brightness_detail);
        this.mBrightnessDetailIcon = imageView;
        imageView.setStateListAnimator(RecoilEffectUtil.getSecRecoilSmallAnimator(this.mContext));
        BrightnessSliderController brightnessSliderController = this.mBrightnessSliderController;
        this.mSecBrightnessSliderController = brightnessSliderController.mSecBrightnessSliderController;
        BrightnessController.Factory factory = this.mBrightnessControllerFactory;
        BrightnessController create2 = factory.create(brightnessSliderController);
        this.mBrightnessController = create2;
        if (create2 != null) {
            Handler handler = create2.mBackgroundHandler;
            BrightnessController.AnonymousClass2 anonymousClass2 = create2.mStartListeningRunnable;
            handler.removeCallbacks(anonymousClass2);
            handler.post(anonymousClass2);
            if (this.mListening) {
                BrightnessController brightnessController = this.mBrightnessController;
                brightnessController.getClass();
                brightnessController.mBackgroundHandler.post(brightnessController.new AnonymousClass10());
            }
        }
        Lazy lazy = this.mSecBrightnessMirrorControllerProviderLazy;
        if (lazy != null) {
            BrightnessMirrorController brightnessMirrorController = ((CentralSurfacesImpl) ((SecBrightnessMirrorControllerProvider) lazy.get())).mBrightnessMirrorController;
            this.mBrightnessMirrorController = brightnessMirrorController;
            if (brightnessMirrorController != null) {
                ArraySet arraySet = brightnessMirrorController.mBrightnessMirrorListeners;
                AnonymousClass2 anonymousClass22 = this.mBrightnessMirrorListener;
                arraySet.remove(anonymousClass22);
                BrightnessMirrorController brightnessMirrorController2 = this.mBrightnessMirrorController;
                brightnessMirrorController2.getClass();
                Objects.requireNonNull(anonymousClass22);
                brightnessMirrorController2.mBrightnessMirrorListeners.add(anonymousClass22);
                SecBrightnessMirrorController secBrightnessMirrorController = this.mBrightnessMirrorController.mSecBrightnessMirrorController;
                this.mSecBrightnessMirrorController = secBrightnessMirrorController;
                this.mSecBrightnessSliderController.secBrightnessMirrorController = secBrightnessMirrorController;
            }
            updateBrightnessMirror();
        }
        if (this.mIsOnCollapsedState) {
            RelativeLayout relativeLayout = this.mSliderContainer;
            relativeLayout.setPadding(0, relativeLayout.getPaddingTop(), 0, this.mSliderContainer.getPaddingBottom());
            this.mToggleSeekBar.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.bar.BrightnessBar$$ExternalSyntheticLambda0
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    BrightnessBar brightnessBar = BrightnessBar.this;
                    brightnessBar.getClass();
                    if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isBrightnessBlocked()) {
                        return true;
                    }
                    SecBrightnessSliderController secBrightnessSliderController = brightnessBar.mSecBrightnessSliderController;
                    if (secBrightnessSliderController != null && !secBrightnessSliderController.sliderEnabled) {
                        if (motionEvent.getAction() != 0) {
                            return true;
                        }
                        brightnessBar.mSecBrightnessSliderController.showSliderDisabledToast();
                        return true;
                    }
                    if (secBrightnessSliderController != null && secBrightnessSliderController.view.mSecBrightnessSliderView.highBrightnessModeEnter && secBrightnessSliderController.isAutoChecked() && motionEvent.getAction() == 0) {
                        brightnessBar.mSecBrightnessSliderController.showHighBrightnessModeToast();
                    }
                    return false;
                }
            });
            this.mBrightnessDetailIcon.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.bar.BrightnessBar$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    BrightnessBar brightnessBar = BrightnessBar.this;
                    brightnessBar.getClass();
                    view.setClickable(true);
                    view.setEnabled(true);
                    BrightnessDetail brightnessDetail = new BrightnessDetail(brightnessBar.mContext, brightnessBar.mQSDetailController, brightnessBar.mBrightnessControllerFactory);
                    brightnessDetail.qsDetailController.showTargetDetail((BrightnessDetailAdapter) brightnessDetail.adapter$delegate.getValue());
                    SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_BRIGHTNESS_EXPAND, SystemUIAnalytics.RUNESTONE_LABEL_QP_LAYOUT);
                    SystemUIAnalytics.sendScreenViewLog(SystemUIAnalytics.SID_BRIGHTNESS_DETAIL);
                }
            });
            this.mBrightnessDetailIcon.setClickable(true);
            boolean isEmergencyMode = this.mSettingsHelper.isEmergencyMode();
            Log.d(this.TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("BrightnessDetail disabled = ", isEmergencyMode));
            ImageView imageView2 = this.mBrightnessDetailIcon;
            if (imageView2 != null) {
                imageView2.setVisibility(isEmergencyMode ? 8 : 0);
            }
            SecBrightnessMirrorController secBrightnessMirrorController2 = this.mSecBrightnessMirrorController;
            if (secBrightnessMirrorController2 != null && (frameLayout = secBrightnessMirrorController2.brightnessMirror) != null) {
                frameLayout.findViewById(R.id.brightness_detail_container).setVisibility(isEmergencyMode ? 8 : 0);
            }
        } else {
            this.mTileLayout.setVisibility(0);
            this.mBrightnessDetailIcon.setVisibility(8);
            this.mBrightnessBarContainer.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.bar.BrightnessBar$$ExternalSyntheticLambda4
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    BrightnessBar brightnessBar = BrightnessBar.this;
                    brightnessBar.getClass();
                    BrightnessDetail brightnessDetail = new BrightnessDetail(brightnessBar.mContext, brightnessBar.mQSDetailController, brightnessBar.mBrightnessControllerFactory);
                    brightnessDetail.qsDetailController.showTargetDetail((BrightnessDetailAdapter) brightnessDetail.adapter$delegate.getValue());
                    return true;
                }
            });
            this.mTiles.forEach(new BrightnessBar$$ExternalSyntheticLambda2(this.mTileLayout, 2));
            SecBrightnessMirrorController secBrightnessMirrorController3 = this.mSecBrightnessMirrorController;
            if (secBrightnessMirrorController3 != null) {
                ArrayList arrayList = this.mTiles;
                QuickTileBrightnessMirrorDummyView quickTileBrightnessMirrorDummyView = secBrightnessMirrorController3.quickTileBrightnessMirrorDummyView;
                if (quickTileBrightnessMirrorDummyView != null) {
                    ArrayList arrayList2 = quickTileBrightnessMirrorDummyView.tiles;
                    arrayList2.clear();
                    arrayList2.addAll(arrayList);
                }
            }
            final SecBrightnessSliderController secBrightnessSliderController = this.mSecBrightnessSliderController;
            Context context = this.mContext;
            secBrightnessSliderController.getClass();
            final BrightnessDetail brightnessDetail = new BrightnessDetail(context, this.mQSDetailController, factory);
            final Handler handler2 = new Handler();
            SecBrightnessSliderView secBrightnessSliderView = secBrightnessSliderController.view.mSecBrightnessSliderView;
            final ToggleSeekBar slider = secBrightnessSliderView != null ? secBrightnessSliderView.getSlider() : null;
            if (slider != null) {
                slider.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.settings.brightness.SecBrightnessSliderController$setLongPressHandler$1
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(final View view, MotionEvent motionEvent) {
                        SecBrightnessSliderController secBrightnessSliderController2 = SecBrightnessSliderController.this;
                        if (secBrightnessSliderController2.isSliderDisabled) {
                            Log.d(SecBrightnessSliderController.TAG, "Dark mode and brightness bar sliding happened simulteaneosuly: ");
                            return true;
                        }
                        if (!secBrightnessSliderController2.sliderEnabled && !secBrightnessSliderController2.outdoormode) {
                            if (motionEvent.getAction() == 0) {
                                SecBrightnessSliderController.this.showSliderDisabledToast();
                            }
                            return true;
                        }
                        SecBrightnessSliderView secBrightnessSliderView2 = secBrightnessSliderController2.view.mSecBrightnessSliderView;
                        if (secBrightnessSliderView2 != null && secBrightnessSliderView2.highBrightnessModeEnter && secBrightnessSliderController2.isAutoChecked() && motionEvent.getAction() == 0) {
                            SecBrightnessSliderController.this.showHighBrightnessModeToast();
                        }
                        if (!SecBrightnessSliderController.this.sliderEnabled || ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isBrightnessBlocked()) {
                            return true;
                        }
                        if (motionEvent.getAction() == 0) {
                            SecBrightnessSliderController secBrightnessSliderController3 = SecBrightnessSliderController.this;
                            secBrightnessSliderController3.isLongPressed = false;
                            secBrightnessSliderController3.getClass();
                            SecBrightnessSliderController.this.downPoint.set(motionEvent.getX(), motionEvent.getY());
                            Handler handler3 = handler2;
                            final ToggleSeekBar toggleSeekBar = slider;
                            final SecBrightnessSliderController secBrightnessSliderController4 = SecBrightnessSliderController.this;
                            final BrightnessDetail brightnessDetail2 = brightnessDetail;
                            handler3.postDelayed(new Runnable() { // from class: com.android.systemui.settings.brightness.SecBrightnessSliderController$setLongPressHandler$1.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ToggleSeekBar.this.performHapticFeedback(0);
                                    view.setPressed(false);
                                    secBrightnessSliderController4.isLongPressed = true;
                                    BrightnessDetail brightnessDetail3 = brightnessDetail2;
                                    brightnessDetail3.qsDetailController.showTargetDetail((BrightnessDetailAdapter) brightnessDetail3.adapter$delegate.getValue());
                                    SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_BRIGHTNESS_EXPAND, SystemUIAnalytics.RUNESTONE_LABEL_QP_LAYOUT);
                                    SystemUIAnalytics.sendScreenViewLog(SystemUIAnalytics.SID_BRIGHTNESS_DETAIL);
                                }
                            }, 500L);
                            return false;
                        }
                        if (motionEvent.getAction() != 2 || SecBrightnessSliderController.this.isLongPressed) {
                            if (!SecBrightnessSliderController.this.isLongPressed) {
                                handler2.removeCallbacksAndMessages(null);
                            }
                            return SecBrightnessSliderController.this.isLongPressed;
                        }
                        float hypot = (float) Math.hypot(motionEvent.getX() - SecBrightnessSliderController.this.downPoint.x, motionEvent.getY() - SecBrightnessSliderController.this.downPoint.y);
                        SecBrightnessSliderController secBrightnessSliderController5 = SecBrightnessSliderController.this;
                        if (hypot > secBrightnessSliderController5.touchSlop && !secBrightnessSliderController5.isLongPressed) {
                            handler2.removeCallbacksAndMessages(null);
                            SecBrightnessSliderController.this.getClass();
                        }
                        return false;
                    }
                });
            }
        }
        this.mToggleSeekBar.setOnHoverListener(new BrightnessBar$$ExternalSyntheticLambda5());
        updateVisibility$7();
        updateHeightMargins();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void makeCloneBar() {
        LayoutInflater from = LayoutInflater.from(this.mContext);
        SecBrightnessSliderController.Companion.getClass();
        View inflate = from.inflate(R.layout.sec_quick_settings_brightness_dialog, (ViewGroup) null);
        this.mClonedBarView = inflate;
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.brightness_bar_container);
        LinearLayout linearLayout2 = (LinearLayout) this.mClonedBarView.findViewById(R.id.brightness_tile_layout);
        RelativeLayout relativeLayout = (RelativeLayout) this.mClonedBarView.findViewById(R.id.slider_container);
        linearLayout2.setVisibility(0);
        this.mClonedBarView.requireViewById(R.id.brightness_detail_container).setVisibility(8);
        createTilesViewAndDistribute(linearLayout2, this.mTiles, this.mResourcePicker, Boolean.FALSE, null);
        updateLayout(this.mClonedBarView, linearLayout, linearLayout2, relativeLayout);
        BrightnessSliderView brightnessSliderView = (BrightnessSliderView) this.mClonedBarView;
        ToggleSeekBar slider = brightnessSliderView.mSecBrightnessSliderView.getSlider();
        slider.setMaxHeight(this.mResourcePicker.getBrightnessBarExpandedHeight(this.mContext));
        slider.setPaddingRelative(0, 0, 0, 0);
        slider.setImportantForAccessibility(2);
        BrightnessAnimationIcon brightnessAnimationIcon = new BrightnessAnimationIcon((LottieAnimationView) this.mClonedBarView.findViewById(R.id.brightness_icon));
        this.mCloneBrightnessIcon = brightnessAnimationIcon;
        brightnessAnimationIcon.init(this.mContext);
        this.mCloneBrightnessIcon.play(brightnessSliderView.mSlider.getProgress(), brightnessSliderView.mSlider.getMax());
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onConfigChanged(Configuration configuration) {
        updateBrightnessMirror();
        updateHeightMargins();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onKnoxPolicyChanged() {
        updateVisibility$7();
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        String m = FontProvider$$ExternalSyntheticOutline0.m("onTuningChanged() : key = ", str, ", newValue = ", str2);
        String str3 = this.TAG;
        Log.d(str3, m);
        if ("brightness_on_top".equals(str)) {
            if (str2 == null) {
                this.mIsAllowedOnTop = true;
            } else {
                this.mIsAllowedOnTop = Integer.parseInt(str2) != 0;
            }
            updateVisibility$7();
            ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("onTuningChanged() : BRIGHTNESS_ON_TOP = "), this.mIsAllowedOnTop, str3);
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onUiModeChanged() {
        SecBrightnessSliderController secBrightnessSliderController;
        SecBrightnessSliderController secBrightnessSliderController2;
        if (this.mBarRootView == null) {
            return;
        }
        this.mSecBrightnessSliderController.isSliderDisabled = true;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.android.systemui.qs.bar.BrightnessBar$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                BrightnessBar.this.mSecBrightnessSliderController.isSliderDisabled = false;
            }
        }, 200L);
        ImageView imageView = this.mBrightnessDetailIcon;
        if (imageView != null) {
            imageView.setBackground(this.mContext.getDrawable(R.drawable.ripple_drawable_20dp));
            this.mBrightnessDetailIcon.getBackground().setState(new int[]{android.R.attr.state_enabled});
            this.mBrightnessDetailIcon.setColorFilter(this.mContext.getColor(R.color.tw_check_box_tint));
        }
        BrightnessController brightnessController = this.mBrightnessController;
        if (brightnessController != null) {
            SecBrightnessController secBrightnessController = brightnessController.mSecBrightnessController;
            secBrightnessController.handler.obtainMessage(9).sendToTarget();
            ToggleSlider toggleSlider = secBrightnessController.control;
            BrightnessSliderController brightnessSliderController = toggleSlider instanceof BrightnessSliderController ? (BrightnessSliderController) toggleSlider : null;
            if (brightnessSliderController != null && (secBrightnessSliderController2 = brightnessSliderController.mSecBrightnessSliderController) != null) {
                secBrightnessSliderController2.updateSliderDrawable();
            }
            BrightnessSliderController brightnessSliderController2 = this.mSecBrightnessMirrorController.toggleSliderController;
            if (brightnessSliderController2 != null && (secBrightnessSliderController = brightnessSliderController2.mSecBrightnessSliderController) != null) {
                secBrightnessSliderController.updateSliderDrawable();
            }
        }
        updateIconSize();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int orignBottomMargin() {
        return 0;
    }

    @Override // com.android.systemui.qs.bar.TileHostable
    public final void removeAllTiles() {
        QuickTileBrightnessMirrorDummyView quickTileBrightnessMirrorDummyView;
        SecBrightnessMirrorController secBrightnessMirrorController = this.mSecBrightnessMirrorController;
        if (secBrightnessMirrorController != null && (quickTileBrightnessMirrorDummyView = secBrightnessMirrorController.quickTileBrightnessMirrorDummyView) != null) {
            quickTileBrightnessMirrorDummyView.tiles.clear();
        }
        if (this.mTileLayout != null) {
            this.mTiles.forEach(new BrightnessBar$$ExternalSyntheticLambda2(this, 0));
        }
        this.mTiles.clear();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setExpanded(boolean z) {
        QuickTileBrightnessMirrorDummyView quickTileBrightnessMirrorDummyView;
        this.mQsExpanded = z;
        BrightnessController brightnessController = this.mBrightnessController;
        if (brightnessController != null) {
            brightnessController.mBackgroundHandler.post(brightnessController.new AnonymousClass10());
        }
        SecBrightnessMirrorController secBrightnessMirrorController = this.mSecBrightnessMirrorController;
        if (secBrightnessMirrorController == null || (quickTileBrightnessMirrorDummyView = secBrightnessMirrorController.quickTileBrightnessMirrorDummyView) == null) {
            return;
        }
        quickTileBrightnessMirrorDummyView.expanded = z;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setListening(boolean z) {
        BrightnessController brightnessController;
        this.mListening = z;
        if (z && (brightnessController = this.mBrightnessController) != null) {
            brightnessController.mBackgroundHandler.post(brightnessController.new AnonymousClass10());
        }
        this.mTiles.forEach(new BrightnessBar$$ExternalSyntheticLambda2(this, 1));
    }

    public final void updateBrightnessMirror() {
        BrightnessMirrorController brightnessMirrorController = this.mBrightnessMirrorController;
        if (brightnessMirrorController != null) {
            BrightnessSliderController brightnessSliderController = this.mBrightnessSliderController;
            brightnessSliderController.mMirrorController = brightnessMirrorController;
            brightnessSliderController.setMirror(brightnessMirrorController.mToggleSliderController);
        }
        if (this.mSecBrightnessSliderController != null) {
            this.mSecBrightnessSliderController.updateSliderHeight(this.mResourcePicker.getBrightnessBarExpandedHeight(this.mContext));
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateClonedBar() {
        ToggleSeekBar toggleSeekBar = (ToggleSeekBar) this.mClonedBarView.findViewById(R.id.slider);
        toggleSeekBar.setMax(this.mBrightnessSliderController.getMax());
        toggleSeekBar.setProgress(this.mBrightnessSliderController.getValue());
        this.mCloneBrightnessIcon.play(this.mBrightnessSliderController.getValue(), this.mBrightnessSliderController.getMax());
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        updateLayout(this.mBarRootView, this.mBrightnessBarContainer, this.mTileLayout, this.mSliderContainer);
        updateIconSize();
    }

    public final void updateIconSize() {
        Context context = this.mContext;
        this.mResourcePicker.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        int dp = SecQSPanelResourceCommon.Companion.dp(R.dimen.brightness_slider_icon_size, context);
        RelativeLayout relativeLayout = (RelativeLayout) this.mBarRootView.findViewById(R.id.brightness_detail_container);
        ImageView imageView = this.mBrightnessDetailIcon;
        if (imageView != null) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.width = dp;
            layoutParams.height = dp;
            this.mBrightnessDetailIcon.setLayoutParams(layoutParams);
        }
        if (relativeLayout != null) {
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
            layoutParams2.width = dp;
            layoutParams2.height = dp;
            relativeLayout.setLayoutParams(layoutParams2);
        }
        LottieAnimationView lottieAnimationView = (LottieAnimationView) this.mBarRootView.findViewById(R.id.brightness_icon);
        if (lottieAnimationView != null) {
            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) lottieAnimationView.getLayoutParams();
            layoutParams3.width = dp;
            layoutParams3.height = dp;
            lottieAnimationView.setLayoutParams(layoutParams3);
        }
        SecBrightnessMirrorController secBrightnessMirrorController = this.mSecBrightnessMirrorController;
        if (secBrightnessMirrorController != null) {
            FrameLayout frameLayout = secBrightnessMirrorController.brightnessMirror;
            ImageView imageView2 = frameLayout != null ? (ImageView) frameLayout.findViewById(R.id.brightness_detail) : null;
            if (!(imageView2 instanceof ImageView)) {
                imageView2 = null;
            }
            if (imageView2 != null) {
                ViewGroup.LayoutParams layoutParams4 = imageView2.getLayoutParams();
                layoutParams4.width = dp;
                layoutParams4.height = dp;
                imageView2.setLayoutParams(layoutParams4);
            }
            FrameLayout frameLayout2 = this.mSecBrightnessMirrorController.brightnessMirror;
            RelativeLayout relativeLayout2 = frameLayout2 != null ? (RelativeLayout) frameLayout2.findViewById(R.id.brightness_detail_container) : null;
            RelativeLayout relativeLayout3 = relativeLayout2 instanceof RelativeLayout ? relativeLayout2 : null;
            if (relativeLayout3 != null) {
                ViewGroup.LayoutParams layoutParams5 = relativeLayout3.getLayoutParams();
                layoutParams5.width = dp;
                layoutParams5.height = dp;
                relativeLayout3.setLayoutParams(layoutParams5);
            }
        }
    }

    public final void updateLayout(View view, LinearLayout linearLayout, LinearLayout linearLayout2, RelativeLayout relativeLayout) {
        if (this.mBarRootView == null) {
            return;
        }
        Context context = this.mContext;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, secQSPanelResourcePicker.getBrightnessBarHeight(context));
        if (this.mIsOnCollapsedState) {
            int quickQSCommonBottomMargin = secQSPanelResourcePicker.getQuickQSCommonBottomMargin(this.mContext);
            this.mBarBottomMargin = quickQSCommonBottomMargin;
            layoutParams.bottomMargin = quickQSCommonBottomMargin;
        } else {
            layoutParams.height = secQSPanelResourcePicker.getBrightnessBarContainerHeight(this.mContext);
            linearLayout.setOrientation(0);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
            relativeLayout.setPadding(0, 0, 0, 0);
            int brightnessBarExpandedHeight = secQSPanelResourcePicker.getBrightnessBarExpandedHeight(this.mContext);
            layoutParams2.height = brightnessBarExpandedHeight;
            secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getClass();
            layoutParams2.width = -1;
            layoutParams2.weight = 1.0f;
            relativeLayout.setLayoutParams(layoutParams2);
            this.mBrightnessSliderController.mSecBrightnessSliderController.updateSliderHeight(brightnessBarExpandedHeight);
            updateTileLayoutSizeMargins(linearLayout2);
        }
        view.setLayoutParams(layoutParams);
    }

    public final void updateTileLayoutSizeMargins(LinearLayout linearLayout) {
        if (linearLayout == null) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams.weight = 0.0f;
        layoutParams.setMarginStart(this.mContext.getResources().getDimensionPixelSize(R.dimen.brightness_slider_end_margin));
        Context context = this.mContext;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        layoutParams.width = secQSPanelResourcePicker.getBrightnessBarExpandedHeight(context);
        layoutParams.height = secQSPanelResourcePicker.getBrightnessBarExpandedHeight(this.mContext);
        linearLayout.setLayoutParams(layoutParams);
        int brightnessTileLayoutBetweenMargin = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getBrightnessTileLayoutBetweenMargin(this.mContext);
        linearLayout.setGravity(17);
        int size = this.mTiles.size();
        for (int i = 0; i < size - 1; i++) {
            View childAt = linearLayout.getChildAt(i);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) childAt.getLayoutParams();
            layoutParams2.setMarginEnd(brightnessTileLayoutBetweenMargin);
            childAt.setLayoutParams(layoutParams2);
        }
    }

    public final void updateVisibility$7() {
        CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mCustomSdkMonitor;
        boolean z = customSdkMonitor != null && (customSdkMonitor.mKnoxCustomQuickPanelButtons & 4) == 4 && (!this.mIsOnCollapsedState || this.mIsAllowedOnTop);
        if (this.mShowing != z) {
            showBar(z);
        }
    }
}
