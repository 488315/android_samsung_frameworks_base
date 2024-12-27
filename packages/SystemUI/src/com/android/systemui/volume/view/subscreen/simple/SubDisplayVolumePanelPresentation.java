package com.android.systemui.volume.view.subscreen.simple;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Presentation;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Display;
import android.view.LayoutInflater;
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

public final class SubDisplayVolumePanelPresentation extends Presentation implements VolumeObserver {
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SubDisplayVolumePanelPresentation(com.android.systemui.volume.VolumeDependencyBase r5) {
        /*
            r4 = this;
            java.lang.Class<android.content.Context> r0 = android.content.Context.class
            com.android.systemui.volume.VolumeDependency r5 = (com.android.systemui.volume.VolumeDependency) r5
            java.lang.Object r0 = r5.get(r0)
            android.content.Context r0 = (android.content.Context) r0
            java.lang.Class<com.android.systemui.volume.util.DisplayManagerWrapper> r1 = com.android.systemui.volume.util.DisplayManagerWrapper.class
            java.lang.Object r1 = r5.get(r1)
            com.android.systemui.volume.util.DisplayManagerWrapper r1 = (com.android.systemui.volume.util.DisplayManagerWrapper) r1
            android.view.Display r1 = r1.getFrontSubDisplay()
            r2 = 2132018709(0x7f140615, float:1.9675732E38)
            r4.<init>(r0, r1, r2)
            com.android.systemui.volume.store.StoreInteractor r0 = new com.android.systemui.volume.store.StoreInteractor
            r1 = 0
            r0.<init>(r4, r1)
            r4.mStoreInteractor = r0
            java.lang.Class<com.android.systemui.volume.util.HandlerWrapper> r1 = com.android.systemui.volume.util.HandlerWrapper.class
            java.lang.Object r1 = r5.get(r1)
            com.android.systemui.volume.util.HandlerWrapper r1 = (com.android.systemui.volume.util.HandlerWrapper) r1
            r4.mHandlerWrapper = r1
            java.lang.Class<com.android.systemui.volume.store.VolumePanelStore> r1 = com.android.systemui.volume.store.VolumePanelStore.class
            java.lang.Object r1 = r5.get(r1)
            com.android.systemui.volume.store.VolumePanelStore r1 = (com.android.systemui.volume.store.VolumePanelStore) r1
            r4.mStore = r1
            java.lang.Class<com.android.systemui.volume.view.VolumePanelMotion> r2 = com.android.systemui.volume.view.VolumePanelMotion.class
            java.lang.Object r2 = r5.get(r2)
            com.android.systemui.volume.view.VolumePanelMotion r2 = (com.android.systemui.volume.view.VolumePanelMotion) r2
            r4.mVolumePanelMotion = r2
            com.android.systemui.volume.util.BlurEffect r2 = new com.android.systemui.volume.util.BlurEffect
            android.content.Context r3 = r4.getContext()
            r2.<init>(r3, r5)
            r4.mBlurEffect = r2
            java.lang.Class<com.android.systemui.volume.util.VibratorWrapper> r2 = com.android.systemui.volume.util.VibratorWrapper.class
            java.lang.Object r5 = r5.get(r2)
            com.android.systemui.volume.util.VibratorWrapper r5 = (com.android.systemui.volume.util.VibratorWrapper) r5
            r4.mVibratorWrapper = r5
            r0.store = r1
            boolean r5 = com.android.systemui.BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG
            if (r5 == 0) goto L61
            r5 = 2131559772(0x7f0d055c, float:1.8744897E38)
            goto L64
        L61:
            r5 = 2131559766(0x7f0d0556, float:1.8744885E38)
        L64:
            r4.setContentView(r5)
            r5 = 2131364877(0x7f0a0c0d, float:1.8349603E38)
            android.view.View r5 = r4.findViewById(r5)
            android.widget.ImageButton r5 = (android.widget.ImageButton) r5
            r4.mArrowLeft = r5
            r5 = 2131364878(0x7f0a0c0e, float:1.8349605E38)
            android.view.View r5 = r4.findViewById(r5)
            android.widget.ImageButton r5 = (android.widget.ImageButton) r5
            r4.mArrowRight = r5
            r5 = 2131364880(0x7f0a0c10, float:1.834961E38)
            android.view.View r5 = r4.findViewById(r5)
            android.view.ViewGroup r5 = (android.view.ViewGroup) r5
            r4.mRowContainer = r5
            r5 = 2131364882(0x7f0a0c12, float:1.8349614E38)
            android.view.View r5 = r4.findViewById(r5)
            android.widget.TextView r5 = (android.widget.TextView) r5
            r4.mWarningToastPopup = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumePanelPresentation.<init>(com.android.systemui.volume.VolumeDependencyBase):void");
    }

    public final void addRow(final VolumePanelState volumePanelState) {
        boolean isMultiSoundBt = volumePanelState.isMultiSoundBt();
        final boolean z = (!isMultiSoundBt && volumePanelState.getActiveStream() == 3) || (isMultiSoundBt && volumePanelState.getActiveStream() == 21);
        volumePanelState.getVolumeRowList().stream().filter(new Predicate() { // from class: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                SubDisplayVolumePanelPresentation subDisplayVolumePanelPresentation = SubDisplayVolumePanelPresentation.this;
                boolean z2 = z;
                VolumePanelState volumePanelState2 = volumePanelState;
                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                subDisplayVolumePanelPresentation.getClass();
                return (z2 && volumePanelRow.getStreamType() == 22) ? subDisplayVolumePanelPresentation.mIsDualAudio : volumePanelRow.getStreamType() == volumePanelState2.getActiveStream();
            }
        }).forEach(new Consumer() { // from class: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SubDisplayVolumePanelPresentation subDisplayVolumePanelPresentation = SubDisplayVolumePanelPresentation.this;
                VolumePanelState volumePanelState2 = volumePanelState;
                boolean z2 = z;
                VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
                subDisplayVolumePanelPresentation.getClass();
                int i = 0;
                if (!BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG) {
                    SubDisplayVolumeRowView subDisplayVolumeRowView = (SubDisplayVolumeRowView) LayoutInflater.from(subDisplayVolumePanelPresentation.getContext()).inflate(R.layout.volume_sub_display_row_view, (ViewGroup) null);
                    VolumePanelStore volumePanelStore = subDisplayVolumePanelPresentation.mStore;
                    HandlerWrapper handlerWrapper = subDisplayVolumePanelPresentation.mHandlerWrapper;
                    boolean z3 = subDisplayVolumePanelPresentation.mIsDualAudio && z2;
                    StoreInteractor storeInteractor = subDisplayVolumeRowView.mStoreInteractor;
                    storeInteractor.store = volumePanelStore;
                    storeInteractor.observeStore();
                    subDisplayVolumeRowView.mHandlerWrapper = handlerWrapper;
                    boolean isHasVibrator = volumePanelState2.isHasVibrator();
                    subDisplayVolumeRowView.mStream = volumePanelRow.getStreamType();
                    volumePanelRow.getRealLevel();
                    volumePanelRow.getLevelMin();
                    volumePanelRow.getLevelMax();
                    boolean isSliderEnabled = volumePanelRow.isSliderEnabled();
                    boolean isIconEnabled = volumePanelRow.isIconEnabled();
                    if (z3) {
                        ((ImageButton) subDisplayVolumeRowView.findViewById(R.id.volume_row_icon_with_label)).setClickable(false);
                    } else {
                        SubVolumeIcon subVolumeIcon = (SubVolumeIcon) subDisplayVolumeRowView.findViewById(R.id.volume_row_icon);
                        subDisplayVolumeRowView.mIcon = subVolumeIcon;
                        subVolumeIcon.initialize(volumePanelStore, volumePanelState2, volumePanelRow);
                        subDisplayVolumeRowView.mIcon.setClickable(false);
                        if (isSliderEnabled) {
                            subDisplayVolumeRowView.mIcon.setEnabled(true);
                            subDisplayVolumeRowView.mIcon.setAlpha(1.0f);
                        } else {
                            subDisplayVolumeRowView.mIcon.setEnabled(isIconEnabled);
                            subDisplayVolumeRowView.mIcon.setAlpha(isIconEnabled ? 1.0f : 0.4f);
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
                    subDisplayVolumeRowView.mSeekBar.setOnSeekBarChangeListener(new SubDisplayVolumeRowView.VolumeSeekBarChangeListener(subDisplayVolumeRowView, i));
                    subDisplayVolumeRowView.mSeekBar.setEnabled(isSliderEnabled);
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
                    subDisplayVolumeRowView.updateContentDescription(volumePanelRow, isHasVibrator);
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
                boolean isHasVibrator2 = volumePanelState2.isHasVibrator();
                subLargeDisplayVolumeRowView.mStream = volumePanelRow.getStreamType();
                boolean isSliderEnabled2 = volumePanelRow.isSliderEnabled();
                boolean isIconEnabled2 = volumePanelRow.isIconEnabled();
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
                                SubLargeDisplayVolumeRowView subLargeDisplayVolumeRowView2 = SubLargeDisplayVolumeRowView.this;
                                subLargeDisplayVolumeRowView2.mStoreInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_ICON_CLICKED).stream(subLargeDisplayVolumeRowView2.mStream).isFromOutside(true).build(), false);
                            }
                        });
                        SubVolumeIcon subVolumeIcon3 = subLargeDisplayVolumeRowView.mIcon;
                        subVolumeIcon3.setClickable(subVolumeIcon3.isEnabled() && subLargeDisplayVolumeRowView.mIconClickable);
                    }
                }
                if (isSliderEnabled2) {
                    subLargeDisplayVolumeRowView.mIcon.setEnabled(true);
                    subLargeDisplayVolumeRowView.mIcon.setAlpha(1.0f);
                } else {
                    subLargeDisplayVolumeRowView.mIcon.setEnabled(isIconEnabled2);
                    subLargeDisplayVolumeRowView.mIcon.setAlpha(isIconEnabled2 ? 1.0f : 0.4f);
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
                subLargeDisplayVolumeRowView.mSeekBar.setEnabled(isSliderEnabled2);
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
                int roundToInt = MathKt__MathJVMKt.roundToInt(r10.getResources().getDisplayMetrics().widthPixels * (z4 ? subLargeDisplayVolumeRowView.getContext().getResources().getFloat(R.dimen.volume_sub_large_display_dual_seek_bar_margin_ratio) : 0.0f));
                layoutParams.setMarginStart(roundToInt);
                layoutParams.setMarginEnd(roundToInt);
                viewGroup3.setLayoutParams(layoutParams);
                subLargeDisplayVolumeRowView.updateContentDescription$1(volumePanelRow, isHasVibrator2);
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

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0076, code lost:
    
        if (r4 < (r0.getHeight() + r1)) goto L28;
     */
    @Override // android.app.Dialog, android.view.Window.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean dispatchTouchEvent(android.view.MotionEvent r9) {
        /*
            r8 = this;
            boolean r0 = com.android.systemui.BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG
            if (r0 == 0) goto L88
            com.android.systemui.volume.store.StoreInteractor r0 = r8.mStoreInteractor
            com.samsung.systemui.splugins.volume.VolumePanelAction$Builder r1 = new com.samsung.systemui.splugins.volume.VolumePanelAction$Builder
            com.samsung.systemui.splugins.volume.VolumePanelAction$ActionType r2 = com.samsung.systemui.splugins.volume.VolumePanelAction.ActionType.ACTION_TOUCH_PANEL
            r1.<init>(r2)
            r2 = 1
            com.samsung.systemui.splugins.volume.VolumePanelAction$Builder r1 = r1.isFromOutside(r2)
            com.samsung.systemui.splugins.volume.VolumePanelAction r1 = r1.build()
            r3 = 0
            r0.sendAction(r1, r3)
            int r0 = r9.getAction()
            if (r0 == r2) goto L33
            r1 = 4
            if (r0 == r1) goto L24
            goto L88
        L24:
            com.android.systemui.volume.store.StoreInteractor r9 = r8.mStoreInteractor
            com.samsung.systemui.splugins.volume.VolumePanelAction$Builder r0 = new com.samsung.systemui.splugins.volume.VolumePanelAction$Builder
            com.samsung.systemui.splugins.volume.VolumePanelAction$ActionType r1 = com.samsung.systemui.splugins.volume.VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE
            r0.<init>(r1)
            com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(r0, r2, r9, r3)
            r8.mStartProgress = r3
            return r2
        L33:
            boolean r0 = r8.mIsSeekBarTouching
            if (r0 != 0) goto L86
            boolean r0 = r8.mStartProgress
            if (r0 != 0) goto L86
            r0 = 2131364899(0x7f0a0c23, float:1.8349648E38)
            android.view.View r0 = r8.findViewById(r0)
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            boolean r1 = r8.mIsDualAudio
            if (r1 == 0) goto L79
            float r1 = r9.getRawX()
            float r4 = r9.getRawY()
            r5 = 2
            int[] r5 = new int[r5]
            r0.getLocationOnScreen(r5)
            r6 = r5[r3]
            float r7 = (float) r6
            int r7 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
            if (r7 <= 0) goto L79
            int r7 = r0.getWidth()
            int r7 = r7 + r6
            float r6 = (float) r7
            int r1 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r1 >= 0) goto L79
            r1 = r5[r2]
            float r5 = (float) r1
            int r5 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r5 <= 0) goto L79
            int r0 = r0.getHeight()
            int r0 = r0 + r1
            float r0 = (float) r0
            int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r0 >= 0) goto L79
            goto L86
        L79:
            com.android.systemui.volume.store.StoreInteractor r8 = r8.mStoreInteractor
            com.samsung.systemui.splugins.volume.VolumePanelAction$Builder r9 = new com.samsung.systemui.splugins.volume.VolumePanelAction$Builder
            com.samsung.systemui.splugins.volume.VolumePanelAction$ActionType r0 = com.samsung.systemui.splugins.volume.VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE
            r9.<init>(r0)
            com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(r9, r2, r8, r3)
            return r2
        L86:
            r8.mStartProgress = r3
        L88:
            boolean r8 = super.dispatchTouchEvent(r9)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumePanelPresentation.dispatchTouchEvent(android.view.MotionEvent):boolean");
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
                    SubDisplayVolumePanelPresentation subDisplayVolumePanelPresentation = SubDisplayVolumePanelPresentation.this;
                    subDisplayVolumePanelPresentation.getClass();
                    final Runnable subDisplayVolumePanelPresentation$$ExternalSyntheticLambda3 = new SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda3();
                    boolean z = BasicRune.VOLUME_PARTIAL_BLUR;
                    if (z) {
                        if (subDisplayVolumePanelPresentation.mIsDualAudio) {
                            subDisplayVolumePanelPresentation.mBlurView = subDisplayVolumePanelPresentation.findViewById(R.id.sub_volume_panel_dual_view_blur);
                            subDisplayVolumePanelPresentation$$ExternalSyntheticLambda3 = new SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda0(subDisplayVolumePanelPresentation, 2);
                        } else {
                            View findViewById = subDisplayVolumePanelPresentation.mRowContainer.findViewById(R.id.sub_volume_panel_captured_blur);
                            subDisplayVolumePanelPresentation.mBlurView = findViewById;
                            if (findViewById != null) {
                                findViewById.setForeground(subDisplayVolumePanelPresentation.getContext().getDrawable(R.drawable.sub_volume_seekbar_fg));
                                subDisplayVolumePanelPresentation$$ExternalSyntheticLambda3 = new SubDisplayVolumePanelPresentation$$ExternalSyntheticLambda0(subDisplayVolumePanelPresentation, 3);
                            }
                        }
                    }
                    VolumePanelMotion volumePanelMotion = subDisplayVolumePanelPresentation.mVolumePanelMotion;
                    final View decorView = subDisplayVolumePanelPresentation.getWindow().getDecorView();
                    volumePanelMotion.getClass();
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(decorView, "alpha", decorView.getAlpha(), 1.0f);
                    ofFloat.setDuration(200L);
                    ofFloat.setInterpolator(new LinearInterpolator());
                    if (z) {
                        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.VolumePanelMotion$startSubVolumePanelShowAnimation$1$1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                if (decorView.getAlpha() > 0.1f) {
                                    subDisplayVolumePanelPresentation$$ExternalSyntheticLambda3.run();
                                }
                            }
                        });
                        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.VolumePanelMotion$startSubVolumePanelShowAnimation$1$2
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                subDisplayVolumePanelPresentation$$ExternalSyntheticLambda3.run();
                            }
                        });
                    }
                    ofFloat.start();
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
                boolean isMultiSoundBt = volumePanelState.isMultiSoundBt();
                boolean z = (!isMultiSoundBt && volumePanelState.getActiveStream() == 3) || (isMultiSoundBt && volumePanelState.getActiveStream() == 21);
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
                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(decorView, "alpha", decorView.getAlpha(), 0.0f);
                        ofFloat.setDuration(200L);
                        ofFloat.setInterpolator(new LinearInterpolator());
                        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.VolumePanelMotion$startSubVolumePanelHideAnimation$1$1
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                subDisplayVolumePanelPresentation$$ExternalSyntheticLambda0.run();
                                subDisplayVolumePanelPresentation$$ExternalSyntheticLambda02.run();
                            }
                        });
                        ofFloat.start();
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
