package com.android.systemui.volume.view.subscreen.simple;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Presentation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.BlurEffect;
import com.android.systemui.volume.util.DisplayManagerWrapper;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.util.SettingHelper;
import com.android.systemui.volume.util.VibratorWrapper;
import com.android.systemui.volume.view.ViewLevelConverter;
import com.android.systemui.volume.view.VolumePanelMotion;
import com.android.systemui.volume.view.VolumeSeekBar;
import com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0;
import com.android.systemui.volume.view.icon.SubVolumeIcon;
import com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumeRowView;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes3.dex */
public class SubDisplayVolumePanelPresentation extends Presentation implements VolumeObserver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mActiveStream;
    public final ImageButton mArrowLeft;
    public final ImageButton mArrowRight;
    public final BlurEffect mBlurEffect;
    public View mBlurView;
    public final HandlerWrapper mHandlerWrapper;
    public boolean mIsDualAudio;
    public boolean mIsSeekBarTouching;
    public final ViewGroup mRowContainer;
    public boolean mStartProgress;
    public final VolumePanelStore mStore;
    public final StoreInteractor mStoreInteractor;
    public final VibratorWrapper mVibratorWrapper;
    public final VolumePanelMotion mVolumePanelMotion;
    public final TextView mWarningToastPopup;

    /* renamed from: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumePanelPresentation$2, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType;

        static {
            int[] iArr = new int[VolumePanelState.StateType.values().length];
            $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType = iArr;
            try {
                iArr[VolumePanelState.StateType.STATE_SHOW_SUB_DISPLAY_VOLUME_PANEL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_FOLDER_STATE_CHANGED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_ORIENTATION_CHANGED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_COVER_STATE_CHANGED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_DISMISS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_ARROW_RIGHT_CLICKED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_ARROW_LEFT_CLICKED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_SHOW_VOLUME_LIMITER_DIALOG.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_SHOW_VOLUME_SAFETY_WARNING_DIALOG.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_VOLUME_LIMITER_DIALOG_VOLUME_DOWN.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_VOLUME_SAFETY_WARNING_DIALOG_FLAG_DISMISS.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_SEEKBAR_START_PROGRESS.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_SEEKBAR_TOUCH_DOWN.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_SEEKBAR_TOUCH_UP.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[VolumePanelState.StateType.STATE_UPDATE.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public SubDisplayVolumePanelPresentation(VolumeDependencyBase volumeDependencyBase) {
        VolumeDependency volumeDependency = (VolumeDependency) volumeDependencyBase;
        super((Context) volumeDependency.get(Context.class), ((DisplayManagerWrapper) volumeDependency.get(DisplayManagerWrapper.class)).getFrontSubDisplay(), R.style.Theme_SystemUI_VolumePanelPresentation);
        StoreInteractor storeInteractor = new StoreInteractor(this, null);
        this.mStoreInteractor = storeInteractor;
        this.mHandlerWrapper = (HandlerWrapper) volumeDependency.get(HandlerWrapper.class);
        VolumePanelStore volumePanelStore = (VolumePanelStore) volumeDependency.get(VolumePanelStore.class);
        this.mStore = volumePanelStore;
        this.mVolumePanelMotion = (VolumePanelMotion) volumeDependency.get(VolumePanelMotion.class);
        this.mBlurEffect = new BlurEffect(getContext(), volumeDependency);
        this.mVibratorWrapper = (VibratorWrapper) volumeDependency.get(VibratorWrapper.class);
        storeInteractor.store = volumePanelStore;
        setContentView(BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG ? R.layout.volume_sub_large_display_view : R.layout.volume_sub_display_view);
        this.mArrowLeft = (ImageButton) findViewById(R.id.sub_display_dual_volume_arrow_left);
        this.mArrowRight = (ImageButton) findViewById(R.id.sub_display_dual_volume_arrow_right);
        this.mRowContainer = (ViewGroup) findViewById(R.id.sub_display_volume_row_container);
        this.mWarningToastPopup = (TextView) findViewById(R.id.sub_display_volume_warning_toast_view);
    }

    public final void addRow(final VolumePanelState volumePanelState) {
        boolean zIsMultiSoundBt = volumePanelState.isMultiSoundBt();
        final boolean z = (!zIsMultiSoundBt && volumePanelState.getActiveStream() == 3) || (zIsMultiSoundBt && volumePanelState.getActiveStream() == 21);
        volumePanelState.getVolumeRowList().stream().filter(new Predicate() { // from class: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                SubDisplayVolumePanelPresentation subDisplayVolumePanelPresentation = this.f$0;
                boolean z2 = z;
                VolumePanelState volumePanelState2 = volumePanelState;
                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                int i = SubDisplayVolumePanelPresentation.$r8$clinit;
                subDisplayVolumePanelPresentation.getClass();
                return (z2 && volumePanelRow.getStreamType() == 22) ? subDisplayVolumePanelPresentation.mIsDualAudio : volumePanelRow.getStreamType() == volumePanelState2.getActiveStream();
            }
        }).forEach(new Consumer() { // from class: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws Resources.NotFoundException {
                SubDisplayVolumePanelPresentation subDisplayVolumePanelPresentation = this.f$0;
                VolumePanelState volumePanelState2 = volumePanelState;
                boolean z2 = z;
                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                int i = SubDisplayVolumePanelPresentation.$r8$clinit;
                subDisplayVolumePanelPresentation.getClass();
                int i2 = 0;
                if (!BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG) {
                    SubDisplayVolumeRowView subDisplayVolumeRowView = (SubDisplayVolumeRowView) LayoutInflater.from(subDisplayVolumePanelPresentation.getContext()).inflate(R.layout.volume_sub_display_row_view, (ViewGroup) null);
                    VolumePanelStore volumePanelStore = subDisplayVolumePanelPresentation.mStore;
                    HandlerWrapper handlerWrapper = subDisplayVolumePanelPresentation.mHandlerWrapper;
                    boolean z3 = subDisplayVolumePanelPresentation.mIsDualAudio && z2;
                    StoreInteractor storeInteractor = subDisplayVolumeRowView.mStoreInteractor;
                    storeInteractor.store = volumePanelStore;
                    storeInteractor.observeStore();
                    subDisplayVolumeRowView.mHandlerWrapper = handlerWrapper;
                    boolean zIsHasVibrator = volumePanelState2.isHasVibrator();
                    subDisplayVolumeRowView.mStream = volumePanelRow.getStreamType();
                    volumePanelRow.getRealLevel();
                    volumePanelRow.getLevelMin();
                    volumePanelRow.getLevelMax();
                    boolean zIsSliderEnabled = volumePanelRow.isSliderEnabled();
                    boolean zIsIconEnabled = volumePanelRow.isIconEnabled();
                    if (z3) {
                        ((ImageButton) subDisplayVolumeRowView.findViewById(R.id.volume_row_icon_with_label)).setClickable(false);
                    } else {
                        SubVolumeIcon subVolumeIcon = (SubVolumeIcon) subDisplayVolumeRowView.findViewById(R.id.volume_row_icon);
                        subDisplayVolumeRowView.mIcon = subVolumeIcon;
                        subVolumeIcon.initialize(volumePanelStore, volumePanelState2, volumePanelRow);
                        subDisplayVolumeRowView.mIcon.setClickable(false);
                        if (zIsSliderEnabled) {
                            subDisplayVolumeRowView.mIcon.setEnabled(true);
                            subDisplayVolumeRowView.mIcon.setAlpha(1.0f);
                        } else {
                            subDisplayVolumeRowView.mIcon.setEnabled(zIsIconEnabled);
                            subDisplayVolumeRowView.mIcon.setAlpha(zIsIconEnabled ? 1.0f : 0.4f);
                        }
                    }
                    SeekBar seekBar = (SeekBar) subDisplayVolumeRowView.findViewById(R.id.volume_row_slider);
                    subDisplayVolumeRowView.mSeekBar = seekBar;
                    seekBar.setProgressTintList(subDisplayVolumeRowView.mIconActiveColor);
                    subDisplayVolumeRowView.mSeekBar.setProgressBackgroundTintList(subDisplayVolumeRowView.mIconActiveBgColor);
                    subDisplayVolumeRowView.mSeekBar.setThumbTintList(subDisplayVolumeRowView.mIconActiveColor);
                    subDisplayVolumeRowView.mSeekBar.setDualModeOverlapColor(subDisplayVolumeRowView.getContext().getResources().getColor(R.color.sub_display_volume_progress_earshock_bg_color, null), subDisplayVolumeRowView.getContext().getResources().getColor(R.color.sub_display_volume_progress_earshock_color, null));
                    if (subDisplayVolumeRowView.mStream == 20) {
                        subDisplayVolumeRowView.mSeekBar.setTouchDisabled(true);
                    }
                    subDisplayVolumeRowView.mSeekBar.setOnSeekBarChangeListener(new SubDisplayVolumeRowView.VolumeSeekBarChangeListener(subDisplayVolumeRowView, i2));
                    subDisplayVolumeRowView.mSeekBar.setEnabled(zIsSliderEnabled);
                    subDisplayVolumeRowView.mSeekBar.semSetMin(ViewLevelConverter.viewMinLevel(volumePanelRow));
                    subDisplayVolumeRowView.mSeekBar.setMax(ViewLevelConverter.viewMaxLevel(volumePanelRow));
                    subDisplayVolumeRowView.mSeekBar.setProgress(ViewLevelConverter.viewRealLevel(volumePanelRow));
                    int earProtectLevel = volumePanelRow.getEarProtectLevel();
                    if (earProtectLevel != subDisplayVolumeRowView.mEarProtectLevel) {
                        subDisplayVolumeRowView.mEarProtectLevel = earProtectLevel;
                        subDisplayVolumeRowView.mSeekBar.semSetOverlapPointForDualColor(earProtectLevel);
                    }
                    TextView textView = (TextView) subDisplayVolumeRowView.findViewById(R.id.volume_row_header);
                    subDisplayVolumeRowView.mLabelTextView = textView;
                    textView.setText(subDisplayVolumeRowView.getStreamLabel$2(volumePanelState2, volumePanelRow));
                    subDisplayVolumeRowView.mLabelTextView.setSelected(true);
                    if (volumePanelRow.getStreamType() != 22) {
                        subDisplayVolumeRowView.setVisibility(volumePanelRow.isVisible() ? 0 : 8);
                    }
                    ViewGroup viewGroup = (ViewGroup) subDisplayVolumeRowView.findViewById(R.id.sub_display_dual_volume_icon_label);
                    if (z3) {
                        ((SubVolumeIcon) subDisplayVolumeRowView.findViewById(R.id.volume_row_icon)).setVisibility(8);
                        viewGroup.setVisibility(0);
                    } else {
                        viewGroup.setVisibility(8);
                    }
                    subDisplayVolumeRowView.updateContentDescription(volumePanelRow, zIsHasVibrator);
                    subDisplayVolumeRowView.mSeekBar.setContentDescription(subDisplayVolumeRowView.mLabelTextView.getText());
                    subDisplayVolumeRowView.setLayoutDirection(subDisplayVolumeRowView.getContext().getResources().getConfiguration().getLayoutDirection());
                    if (volumePanelRow.getStreamType() == 22) {
                        subDisplayVolumeRowView.setVisibility(8);
                    }
                    subDisplayVolumePanelPresentation.mRowContainer.addView(subDisplayVolumeRowView);
                    return;
                }
                final SubLargeDisplayVolumeRowView subLargeDisplayVolumeRowView = (SubLargeDisplayVolumeRowView) LayoutInflater.from(subDisplayVolumePanelPresentation.getContext()).inflate(R.layout.volume_sub_large_display_row_view, (ViewGroup) null);
                VolumePanelStore volumePanelStore2 = subDisplayVolumePanelPresentation.mStore;
                HandlerWrapper handlerWrapper2 = subDisplayVolumePanelPresentation.mHandlerWrapper;
                VolumePanelMotion volumePanelMotion = subDisplayVolumePanelPresentation.mVolumePanelMotion;
                boolean z4 = subDisplayVolumePanelPresentation.mIsDualAudio && z2;
                VibratorWrapper vibratorWrapper = subDisplayVolumePanelPresentation.mVibratorWrapper;
                StoreInteractor storeInteractor2 = subLargeDisplayVolumeRowView.mStoreInteractor;
                storeInteractor2.store = volumePanelStore2;
                storeInteractor2.observeStore();
                subLargeDisplayVolumeRowView.mVolumePanelMotion = volumePanelMotion;
                subLargeDisplayVolumeRowView.mHandlerWrapper = handlerWrapper2;
                subLargeDisplayVolumeRowView.mVibratorWrapper = vibratorWrapper;
                boolean zIsHasVibrator2 = volumePanelState2.isHasVibrator();
                subLargeDisplayVolumeRowView.mStream = volumePanelRow.getStreamType();
                boolean zIsSliderEnabled2 = volumePanelRow.isSliderEnabled();
                boolean zIsIconEnabled2 = volumePanelRow.isIconEnabled();
                subLargeDisplayVolumeRowView.mIconClickable = volumePanelRow.isIconClickable();
                subLargeDisplayVolumeRowView.mIsDualViewEnabled = z4;
                SubVolumeIcon subVolumeIcon2 = (SubVolumeIcon) subLargeDisplayVolumeRowView.findViewById(R.id.volume_row_icon);
                subLargeDisplayVolumeRowView.mIcon = subVolumeIcon2;
                subVolumeIcon2.initialize(volumePanelStore2, volumePanelState2, volumePanelRow);
                if (volumePanelState2.isShowA11yStream()) {
                    if (subLargeDisplayVolumeRowView.mStream == 10) {
                        subLargeDisplayVolumeRowView.mIcon.setImportantForAccessibility(2);
                    } else {
                        subLargeDisplayVolumeRowView.mIcon.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.subscreen.simple.SubLargeDisplayVolumeRowView$$ExternalSyntheticLambda11
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                SubLargeDisplayVolumeRowView subLargeDisplayVolumeRowView2 = subLargeDisplayVolumeRowView;
                                subLargeDisplayVolumeRowView2.mStoreInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_ICON_CLICKED).stream(subLargeDisplayVolumeRowView2.mStream).isFromOutside(true).build(), false);
                            }
                        });
                        SubVolumeIcon subVolumeIcon3 = subLargeDisplayVolumeRowView.mIcon;
                        subVolumeIcon3.setClickable(subVolumeIcon3.isEnabled() && subLargeDisplayVolumeRowView.mIconClickable);
                    }
                }
                if (zIsSliderEnabled2) {
                    subLargeDisplayVolumeRowView.mIcon.setEnabled(true);
                    subLargeDisplayVolumeRowView.mIcon.setAlpha(1.0f);
                } else {
                    subLargeDisplayVolumeRowView.mIcon.setEnabled(zIsIconEnabled2);
                    subLargeDisplayVolumeRowView.mIcon.setAlpha(zIsIconEnabled2 ? 1.0f : 0.4f);
                }
                VolumeSeekBar volumeSeekBar = (VolumeSeekBar) subLargeDisplayVolumeRowView.findViewById(R.id.volume_row_slider);
                subLargeDisplayVolumeRowView.mSeekBar = volumeSeekBar;
                volumeSeekBar.stream = subLargeDisplayVolumeRowView.mStream;
                volumeSeekBar.isTracking = false;
                StoreInteractor storeInteractor3 = volumeSeekBar.storeInteractor;
                storeInteractor3.store = volumePanelStore2;
                storeInteractor3.observeStore();
                if (subLargeDisplayVolumeRowView.mStream == 20) {
                    subLargeDisplayVolumeRowView.mSeekBar.setTouchDisabled(true);
                }
                subLargeDisplayVolumeRowView.mSeekBar.semSetMin(ViewLevelConverter.viewMinLevel(volumePanelRow));
                subLargeDisplayVolumeRowView.mSeekBar.setMax(ViewLevelConverter.viewMaxLevel(volumePanelRow));
                subLargeDisplayVolumeRowView.mSeekBar.setProgress(ViewLevelConverter.viewRealLevel(volumePanelRow));
                subLargeDisplayVolumeRowView.mSeekBar.setEnabled(zIsSliderEnabled2);
                TextView textView2 = (TextView) subLargeDisplayVolumeRowView.findViewById(R.id.volume_row_header);
                subLargeDisplayVolumeRowView.mLabelTextView = textView2;
                textView2.setText(subLargeDisplayVolumeRowView.getStreamLabel$3(volumePanelState2, volumePanelRow));
                subLargeDisplayVolumeRowView.mLabelTextView.setSelected(true);
                if (volumePanelRow.getStreamType() != 22) {
                    subLargeDisplayVolumeRowView.setVisibility(volumePanelRow.isVisible() ? 0 : 8);
                }
                ((ViewGroup) subLargeDisplayVolumeRowView.findViewById(R.id.sub_display_dual_volume_icon_label)).setVisibility(z4 ? 0 : 8);
                ViewGroup viewGroup2 = (ViewGroup) subLargeDisplayVolumeRowView.findViewById(R.id.volume_seekbar_background);
                if (subLargeDisplayVolumeRowView.mIsDualViewEnabled) {
                    viewGroup2.setBackground(null);
                    if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled()) {
                        subLargeDisplayVolumeRowView.mSeekBar.setProgressDrawable(subLargeDisplayVolumeRowView.getContext().getDrawable(R.drawable.sub_volume_seekbar_drawable_expand_reduce_transparency));
                    } else {
                        subLargeDisplayVolumeRowView.mSeekBar.setProgressDrawable(subLargeDisplayVolumeRowView.getContext().getDrawable(R.drawable.sub_display_volume_seekbar_drawable_expand_blur));
                    }
                } else if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled()) {
                    subLargeDisplayVolumeRowView.mSeekBar.setProgressDrawable(subLargeDisplayVolumeRowView.getContext().getDrawable(R.drawable.sub_volume_seekbar_drawable_reduce_transparency));
                    viewGroup2.setBackground(subLargeDisplayVolumeRowView.getContext().getDrawable(R.drawable.sub_volume_seekbar_bg_reduce_transparency));
                } else {
                    subLargeDisplayVolumeRowView.mSeekBar.setProgressDrawable(subLargeDisplayVolumeRowView.getContext().getDrawable(R.drawable.sub_display_volume_seekbar_drawable));
                    viewGroup2.setBackground(subLargeDisplayVolumeRowView.getContext().getDrawable(R.drawable.sub_volume_seekbar_bg_blur));
                }
                ViewGroup viewGroup3 = (ViewGroup) subLargeDisplayVolumeRowView.findViewById(R.id.volume_seekbar_container);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewGroup3.getLayoutParams();
                Context context = subLargeDisplayVolumeRowView.getContext();
                CoverDisplayLayoutHelper coverDisplayLayoutHelper = CoverDisplayLayoutHelper.INSTANCE;
                float f = context.getResources().getDisplayMetrics().widthPixels;
                CoverDisplayLayoutHelper.INSTANCE.getClass();
                float f2 = context.getResources().getFloat(z4 ? R.dimen.volume_sub_large_display_dual_seek_bar_width_ratio : R.dimen.volume_sub_large_display_seek_bar_width_ratio);
                SettingHelper.INSTANCE.getClass();
                if (Settings.Secure.getInt(context.getContentResolver(), SettingsHelper.INDEX_SHOW_NAVIGATION_FOR_SUBSCREEN, 0) == 1) {
                    VolumeDependency.Companion.getClass();
                    VolumeDependency volumeDependency = VolumeDependency.sInstance;
                    if (volumeDependency == null) {
                        volumeDependency = null;
                    }
                    Display frontSubDisplay = ((DisplayManagerWrapper) volumeDependency.get(DisplayManagerWrapper.class)).getFrontSubDisplay();
                    if (frontSubDisplay != null) {
                        Point point = new Point();
                        frontSubDisplay.getRealSize(point);
                        if (point.x < point.y && frontSubDisplay.getRotation() != 0) {
                            f2 = context.getResources().getFloat(z4 ? R.dimen.volume_sub_large_display_dual_seek_bar_width_sero_ratio : R.dimen.volume_sub_large_display_seek_bar_width_sero_ratio);
                        }
                    }
                }
                layoutParams.width = MathKt__MathJVMKt.roundToInt(f * f2);
                int iRoundToInt = MathKt__MathJVMKt.roundToInt(r10.getResources().getDisplayMetrics().widthPixels * (z4 ? subLargeDisplayVolumeRowView.getContext().getResources().getFloat(R.dimen.volume_sub_large_display_dual_seek_bar_margin_ratio) : 0.0f));
                layoutParams.setMarginStart(iRoundToInt);
                layoutParams.setMarginEnd(iRoundToInt);
                viewGroup3.setLayoutParams(layoutParams);
                subLargeDisplayVolumeRowView.updateContentDescription$1(volumePanelRow, zIsHasVibrator2);
                subLargeDisplayVolumeRowView.mSeekBar.setContentDescription(subLargeDisplayVolumeRowView.mLabelTextView.getText());
                subLargeDisplayVolumeRowView.setLayoutDirection(subLargeDisplayVolumeRowView.getContext().getResources().getConfiguration().getLayoutDirection());
                if (volumePanelRow.getStreamType() == 22) {
                    subLargeDisplayVolumeRowView.setVisibility(8);
                }
                View view = (ViewGroup) subLargeDisplayVolumeRowView.findViewById(R.id.volume_seekbar_container);
                VolumePanelMotion volumePanelMotion2 = subLargeDisplayVolumeRowView.mVolumePanelMotion;
                View view2 = z4 ? view : subLargeDisplayVolumeRowView;
                volumePanelMotion2.getClass();
                subLargeDisplayVolumeRowView.mTouchDownAnimation = VolumePanelMotion.getSeekBarTouchDownAnimation(view2);
                VolumePanelMotion volumePanelMotion3 = subLargeDisplayVolumeRowView.mVolumePanelMotion;
                if (!z4) {
                    view = subLargeDisplayVolumeRowView;
                }
                volumePanelMotion3.getClass();
                subLargeDisplayVolumeRowView.mTouchUpAnimation = VolumePanelMotion.getSeekBarTouchUpAnimation(view);
                subLargeDisplayVolumeRowView.mVolumePanelMotion.getClass();
                subLargeDisplayVolumeRowView.mKeyDownAnimation = VolumePanelMotion.getSeekBarKeyDownAnimation(subLargeDisplayVolumeRowView);
                subLargeDisplayVolumeRowView.mVolumePanelMotion.getClass();
                subLargeDisplayVolumeRowView.mKeyUpAnimation = VolumePanelMotion.getSeekBarKeyUpAnimation(subLargeDisplayVolumeRowView);
                subDisplayVolumePanelPresentation.mRowContainer.addView(subLargeDisplayVolumeRowView);
                if (volumePanelRow.getStreamType() == volumePanelState2.getActiveStream()) {
                    subDisplayVolumePanelPresentation.mActiveStream = volumePanelRow.getStreamType();
                }
                if (subDisplayVolumePanelPresentation.mIsDualAudio) {
                    return;
                }
                subDisplayVolumePanelPresentation.findViewById(R.id.volume_seekbar_outline_stroke).setVisibility(0);
            }
        });
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        super.dismiss();
        onStop();
        this.mStoreInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_DISMISS_SUB_DISPLAY_VOLUME_PANEL).build(), true);
        this.mRowContainer.removeAllViews();
        this.mWarningToastPopup.setVisibility(8);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0076, code lost:
    
        if (r4 < (r0.getHeight() + r1)) goto L28;
     */
    @Override // android.app.Dialog, android.view.Window.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG) {
            this.mStoreInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_PANEL).isFromOutside(true).build(), false);
            int action = motionEvent.getAction();
            if (action == 1) {
                if (!this.mIsSeekBarTouching && !this.mStartProgress) {
                    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.sub_volume_panel_view_background);
                    if (this.mIsDualAudio) {
                        float rawX = motionEvent.getRawX();
                        float rawY = motionEvent.getRawY();
                        int[] iArr = new int[2];
                        viewGroup.getLocationOnScreen(iArr);
                        if (rawX > iArr[0] && rawX < viewGroup.getWidth() + r6) {
                            if (rawY > iArr[1]) {
                            }
                        }
                    }
                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, this.mStoreInteractor, false);
                    return true;
                }
                this.mStartProgress = false;
            } else if (action == 4) {
                VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, this.mStoreInteractor, false);
                this.mStartProgress = false;
                return true;
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public final void initDialog$1() {
        Window window = getWindow();
        window.addFlags(8);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.setTitle("SubScreenVolumeBar");
        if (BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG) {
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.clearFlags(2);
            window.addFlags(17563936);
            setCanceledOnTouchOutside(true);
            attributes.type = 2037;
            attributes.format = -3;
            attributes.windowAnimations = -1;
            attributes.accessibilityTitle = getContext().getString(R.string.volume_panel_view_title);
            setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    SubDisplayVolumePanelPresentation subDisplayVolumePanelPresentation = this.f$0;
                    int i = SubDisplayVolumePanelPresentation.$r8$clinit;
                    subDisplayVolumePanelPresentation.getClass();
                    final Runnable subDisplayVolumePanelPresentation$$ExternalSyntheticLambda3 = new SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda3();
                    boolean z = BasicRune.VOLUME_PARTIAL_BLUR;
                    if (z) {
                        if (subDisplayVolumePanelPresentation.mIsDualAudio) {
                            subDisplayVolumePanelPresentation.mBlurView = subDisplayVolumePanelPresentation.findViewById(R.id.sub_volume_panel_dual_view_blur);
                            subDisplayVolumePanelPresentation$$ExternalSyntheticLambda3 = new SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda0(subDisplayVolumePanelPresentation, 2);
                        } else {
                            View viewFindViewById = subDisplayVolumePanelPresentation.mRowContainer.findViewById(R.id.sub_volume_panel_captured_blur);
                            subDisplayVolumePanelPresentation.mBlurView = viewFindViewById;
                            if (viewFindViewById != null) {
                                viewFindViewById.setForeground(subDisplayVolumePanelPresentation.getContext().getDrawable(R.drawable.sub_volume_seekbar_fg));
                                subDisplayVolumePanelPresentation$$ExternalSyntheticLambda3 = new SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda0(subDisplayVolumePanelPresentation, 3);
                            }
                        }
                    }
                    VolumePanelMotion volumePanelMotion = subDisplayVolumePanelPresentation.mVolumePanelMotion;
                    final View decorView = subDisplayVolumePanelPresentation.getWindow().getDecorView();
                    volumePanelMotion.getClass();
                    ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(decorView, "alpha", decorView.getAlpha(), 1.0f);
                    objectAnimatorOfFloat.setDuration(200L);
                    objectAnimatorOfFloat.setInterpolator(new LinearInterpolator());
                    if (z) {
                        objectAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.VolumePanelMotion$startSubVolumePanelShowAnimation$1$1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                if (decorView.getAlpha() > 0.1f) {
                                    subDisplayVolumePanelPresentation$$ExternalSyntheticLambda3.run();
                                }
                            }
                        });
                        objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.VolumePanelMotion$startSubVolumePanelShowAnimation$1$2
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                subDisplayVolumePanelPresentation$$ExternalSyntheticLambda3.run();
                            }
                        });
                    }
                    objectAnimatorOfFloat.start();
                }
            });
        }
        window.setAttributes(attributes);
        getWindow().getDecorView().setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumePanelPresentation.1
            @Override // android.view.View.AccessibilityDelegate
            public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
                VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SEND_ACCESSIBILITY_EVENT), true, SubDisplayVolumePanelPresentation.this.mStoreInteractor, true);
                return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            }
        });
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(Object obj) {
        final int i = 0;
        final int i2 = 1;
        VolumePanelState volumePanelState = (VolumePanelState) obj;
        switch (AnonymousClass2.$SwitchMap$com$samsung$systemui$splugins$volume$VolumePanelState$StateType[volumePanelState.getStateType().ordinal()]) {
            case 1:
                this.mIsDualAudio = volumePanelState.isDualAudio();
                boolean zIsMultiSoundBt = volumePanelState.isMultiSoundBt();
                boolean z = (!zIsMultiSoundBt && volumePanelState.getActiveStream() == 3) || (zIsMultiSoundBt && volumePanelState.getActiveStream() == 21);
                ViewGroup viewGroup = (ViewGroup) findViewById(R.id.sub_volume_panel_view_background);
                ViewGroup viewGroup2 = (ViewGroup) findViewById(R.id.sub_volume_panel_expand_view_background_stroke);
                this.mArrowLeft.setVisibility(8);
                if (this.mIsDualAudio && z) {
                    this.mArrowRight.setVisibility(0);
                    if (BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG && viewGroup != null && viewGroup2 != null) {
                        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled()) {
                            viewGroup.setBackground(getContext().getDrawable(R.drawable.sub_volume_panel_expand_bg_reduce_transparency));
                            viewGroup2.setVisibility(8);
                        } else {
                            viewGroup.setBackground(getContext().getDrawable(R.drawable.sub_volume_panel_expand_bg_blur));
                            viewGroup2.setVisibility(0);
                        }
                    }
                    this.mArrowLeft.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda8
                        public final /* synthetic */ SubDisplayVolumePanelPresentation f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i3 = i;
                            SubDisplayVolumePanelPresentation subDisplayVolumePanelPresentation = this.f$0;
                            switch (i3) {
                                case 0:
                                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ARROW_LEFT_CLICKED), true, subDisplayVolumePanelPresentation.mStoreInteractor, true);
                                    break;
                                default:
                                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ARROW_RIGHT_CLICKED), true, subDisplayVolumePanelPresentation.mStoreInteractor, true);
                                    break;
                            }
                        }
                    });
                    this.mArrowRight.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda8
                        public final /* synthetic */ SubDisplayVolumePanelPresentation f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            int i3 = i2;
                            SubDisplayVolumePanelPresentation subDisplayVolumePanelPresentation = this.f$0;
                            switch (i3) {
                                case 0:
                                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ARROW_LEFT_CLICKED), true, subDisplayVolumePanelPresentation.mStoreInteractor, true);
                                    break;
                                default:
                                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ARROW_RIGHT_CLICKED), true, subDisplayVolumePanelPresentation.mStoreInteractor, true);
                                    break;
                            }
                        }
                    });
                } else {
                    this.mArrowRight.setVisibility(8);
                    if (BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG && viewGroup != null && viewGroup2 != null) {
                        viewGroup.setBackground(null);
                        viewGroup2.setVisibility(8);
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewGroup.getLayoutParams();
                        marginLayoutParams.leftMargin = 0;
                        marginLayoutParams.rightMargin = 0;
                    }
                }
                addRow(volumePanelState);
                if (BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG) {
                    initDialog$1();
                    getWindow().getDecorView().setAlpha(0.0f);
                }
                if (volumePanelState.isShowingVolumeSafetyWarningDialog() || volumePanelState.isShowingVolumeLimiterDialog()) {
                    this.mWarningToastPopup.setVisibility(0);
                }
                show();
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                if (volumePanelState.isShowingSubDisplayVolumePanel()) {
                    if (!BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG) {
                        dismiss();
                        break;
                    } else {
                        VolumePanelMotion volumePanelMotion = this.mVolumePanelMotion;
                        View decorView = getWindow().getDecorView();
                        final SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda0 subDisplayVolumePanelPresentation$$ExternalSyntheticLambda0 = new SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda0(this, 0);
                        final SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda0 subDisplayVolumePanelPresentation$$ExternalSyntheticLambda02 = new SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda0(this, 1);
                        volumePanelMotion.getClass();
                        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(decorView, "alpha", decorView.getAlpha(), 0.0f);
                        objectAnimatorOfFloat.setDuration(200L);
                        objectAnimatorOfFloat.setInterpolator(new LinearInterpolator());
                        objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.VolumePanelMotion$startSubVolumePanelHideAnimation$1$1
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                subDisplayVolumePanelPresentation$$ExternalSyntheticLambda0.run();
                                subDisplayVolumePanelPresentation$$ExternalSyntheticLambda02.run();
                            }
                        });
                        objectAnimatorOfFloat.start();
                        break;
                    }
                }
                break;
            case 7:
                this.mArrowLeft.setVisibility(0);
                this.mArrowRight.setVisibility(8);
                break;
            case 8:
                this.mArrowLeft.setVisibility(8);
                this.mArrowRight.setVisibility(0);
                break;
            case 9:
            case 10:
                if (this.mWarningToastPopup.getVisibility() == 8) {
                    this.mWarningToastPopup.setVisibility(0);
                    break;
                }
                break;
            case 11:
            case 12:
                if (this.mWarningToastPopup.getVisibility() == 0) {
                    this.mWarningToastPopup.setVisibility(8);
                    break;
                }
                break;
            case 13:
                this.mStartProgress = true;
                break;
            case 14:
                this.mIsSeekBarTouching = true;
                break;
            case 15:
                this.mIsSeekBarTouching = false;
                break;
            case 16:
                if (isShowing() && !this.mIsDualAudio && this.mActiveStream != volumePanelState.getActiveStream()) {
                    this.mRowContainer.removeAllViews();
                    addRow(volumePanelState);
                    break;
                }
                break;
        }
    }

    @Override // android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initDialog$1();
    }
}
