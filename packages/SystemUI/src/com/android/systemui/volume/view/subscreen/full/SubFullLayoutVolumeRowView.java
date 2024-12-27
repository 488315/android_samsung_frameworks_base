package com.android.systemui.volume.view.subscreen.full;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.util.BluetoothIconUtil;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.util.ViewUtil;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.android.systemui.volume.view.SecRoundedCornerSeekBarDrawable;
import com.android.systemui.volume.view.ViewLevelConverter;
import com.samsung.systemui.splugins.extensions.VolumePanelStateExt;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

public final class SubFullLayoutVolumeRowView extends FrameLayout implements VolumeObserver<VolumePanelState> {
    public ImageView bluetoothDeviceIcon;
    public Runnable buttonAnimatorRunnable;
    public int earProtectLevel;
    public HandlerWrapper handlerWrapper;
    public SubFullLayoutVolumeIcon icon;
    public boolean iconClickable;
    public boolean isAODEnabled;
    public boolean isDualViewEnabled;
    public String label;
    public final Lazy progressBarSpring$delegate;
    public final SubFullLayoutVolumeRowView$recheckCallback$1 recheckCallback;
    public SubFullLayoutVolumeSeekBar seekBar;
    public ViewGroup seekBarBackground;
    public boolean startProgress;
    public final StoreInteractor storeInteractor;
    public int stream;
    public SpringAnimation touchDownAnimation;
    public boolean touchDownIcon;
    public SpringAnimation touchUpAnimation;
    public SubFullLayoutVolumePanelMotion volumePanelMotion;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[VolumePanelState.StateType.values().length];
            try {
                iArr[VolumePanelState.StateType.STATE_UPDATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SET_STREAM_VOLUME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_UPDATE_PROGRESS_BAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_UPDATE_PROGRESS_BAR_LATER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_STOP_SLIDER_TRACKING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL_COMPLETED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SEEKBAR_START_PROGRESS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SEEKBAR_TOUCH_DOWN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SEEKBAR_TOUCH_UP.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SMART_VIEW_SEEKBAR_TOUCHED.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public SubFullLayoutVolumeRowView(Context context) {
        super(context);
        this.storeInteractor = new StoreInteractor(this, null);
        this.recheckCallback = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$recheckCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                StoreInteractor storeInteractor = SubFullLayoutVolumeRowView.this.storeInteractor;
                VolumePanelAction.Builder stream = new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_CHECK_IF_NEED_TO_SET_PROGRESS).stream(SubFullLayoutVolumeRowView.this.stream);
                SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = SubFullLayoutVolumeRowView.this.seekBar;
                if (subFullLayoutVolumeSeekBar == null) {
                    subFullLayoutVolumeSeekBar = null;
                }
                storeInteractor.sendAction(stream.progress(subFullLayoutVolumeSeekBar.getProgress()).build(), false);
            }
        };
        this.buttonAnimatorRunnable = SubFullLayoutVolumeRowView$buttonAnimatorRunnable$1.INSTANCE;
        this.progressBarSpring$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$progressBarSpring$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder());
                final SubFullLayoutVolumeRowView subFullLayoutVolumeRowView = SubFullLayoutVolumeRowView.this;
                SpringForce springForce = new SpringForce();
                springForce.setDampingRatio(1.0f);
                springForce.setStiffness(450.0f);
                springAnimation.mSpring = springForce;
                springAnimation.mVelocity = 0.0f;
                SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = subFullLayoutVolumeRowView.seekBar;
                if (subFullLayoutVolumeSeekBar == null) {
                    subFullLayoutVolumeSeekBar = null;
                }
                springAnimation.setStartValue(subFullLayoutVolumeSeekBar.getProgress());
                springAnimation.setMinimumVisibleChange(1.0f);
                springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$progressBarSpring$2$1$2
                    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
                    public final void onAnimationUpdate(float f, float f2) {
                        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar2 = SubFullLayoutVolumeRowView.this.seekBar;
                        if (subFullLayoutVolumeSeekBar2 == null) {
                            subFullLayoutVolumeSeekBar2 = null;
                        }
                        subFullLayoutVolumeSeekBar2.setProgress((int) f);
                    }
                });
                return springAnimation;
            }
        });
    }

    public final void animateSeekBarButton$1(VolumePanelState volumePanelState, final boolean z) {
        if (volumePanelState.getStream() == this.stream) {
            SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = this.seekBar;
            if (subFullLayoutVolumeSeekBar == null) {
                subFullLayoutVolumeSeekBar = null;
            }
            final SecRoundedCornerSeekBarDrawable secRoundedCornerSeekBarDrawable = (SecRoundedCornerSeekBarDrawable) ((LayerDrawable) subFullLayoutVolumeSeekBar.getProgressDrawable()).findDrawableByLayerId(R.id.progress);
            HandlerWrapper handlerWrapper = this.handlerWrapper;
            if (handlerWrapper == null) {
                handlerWrapper = null;
            }
            handlerWrapper.remove(this.buttonAnimatorRunnable);
            Runnable runnable = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$animateSeekBarButton$1
                @Override // java.lang.Runnable
                public final void run() {
                    SecRoundedCornerSeekBarDrawable.this.animateButton(z);
                }
            };
            this.buttonAnimatorRunnable = runnable;
            if (z) {
                HandlerWrapper handlerWrapper2 = this.handlerWrapper;
                (handlerWrapper2 != null ? handlerWrapper2 : null).post(runnable);
            } else {
                HandlerWrapper handlerWrapper3 = this.handlerWrapper;
                (handlerWrapper3 != null ? handlerWrapper3 : null).postDelayed(runnable, 500L);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            ViewUtil viewUtil = ViewUtil.INSTANCE;
            SubFullLayoutVolumeIcon subFullLayoutVolumeIcon = this.icon;
            if (subFullLayoutVolumeIcon == null) {
                subFullLayoutVolumeIcon = null;
            }
            float rawX = motionEvent.getRawX();
            float rawY = motionEvent.getRawY();
            viewUtil.getClass();
            if (ViewUtil.isTouched(subFullLayoutVolumeIcon, rawX, rawY)) {
                this.touchDownIcon = true;
                ViewGroup viewGroup = this.seekBarBackground;
                if (!ViewUtil.isTouched(viewGroup != null ? viewGroup : null, motionEvent.getRawX(), motionEvent.getRawY())) {
                    return true;
                }
            } else if (VolumePanelValues.isSmartView(this.stream)) {
                this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SMART_VIEW_SEEKBAR_TOUCHED).isFromOutside(true).stream(this.stream).build(), false);
            }
        } else if (action == 1) {
            if (isIconClicked(motionEvent.getRawX(), motionEvent.getRawY())) {
                this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_ICON_CLICKED).stream(this.stream).isFromOutside(true).build(), false);
            }
            this.touchDownIcon = false;
            this.startProgress = false;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initialize(com.android.systemui.volume.store.VolumePanelStore r6, com.android.systemui.volume.util.HandlerWrapper r7, com.samsung.systemui.splugins.volume.VolumePanelRow r8, com.samsung.systemui.splugins.volume.VolumePanelState r9, com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion r10) {
        /*
            Method dump skipped, instructions count: 1097
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView.initialize(com.android.systemui.volume.store.VolumePanelStore, com.android.systemui.volume.util.HandlerWrapper, com.samsung.systemui.splugins.volume.VolumePanelRow, com.samsung.systemui.splugins.volume.VolumePanelState, com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion):void");
    }

    public final boolean isIconClicked(float f, float f2) {
        if (!this.startProgress && this.touchDownIcon) {
            SubFullLayoutVolumeIcon subFullLayoutVolumeIcon = this.icon;
            if (subFullLayoutVolumeIcon == null) {
                subFullLayoutVolumeIcon = null;
            }
            if (subFullLayoutVolumeIcon.isEnabled() && this.iconClickable) {
                ViewUtil viewUtil = ViewUtil.INSTANCE;
                SubFullLayoutVolumeIcon subFullLayoutVolumeIcon2 = this.icon;
                SubFullLayoutVolumeIcon subFullLayoutVolumeIcon3 = subFullLayoutVolumeIcon2 != null ? subFullLayoutVolumeIcon2 : null;
                viewUtil.getClass();
                if (ViewUtil.isTouched(subFullLayoutVolumeIcon3, f, f2)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(VolumePanelState volumePanelState) {
        int earProtectLevel;
        VolumePanelState volumePanelState2 = volumePanelState;
        r1 = null;
        SpringAnimation springAnimation = null;
        switch (WhenMappings.$EnumSwitchMapping$0[volumePanelState2.getStateType().ordinal()]) {
            case 1:
                VolumePanelStateExt volumePanelStateExt = VolumePanelStateExt.INSTANCE;
                if (volumePanelStateExt.isRowVisible(volumePanelState2, this.stream)) {
                    VolumePanelRow findRow = volumePanelStateExt.findRow(volumePanelState2, this.stream);
                    if (findRow != null && (earProtectLevel = findRow.getEarProtectLevel()) != this.earProtectLevel) {
                        this.earProtectLevel = earProtectLevel;
                        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = this.seekBar;
                        if (subFullLayoutVolumeSeekBar == null) {
                            subFullLayoutVolumeSeekBar = null;
                        }
                        subFullLayoutVolumeSeekBar.getClass();
                    }
                    updateProgressDrawable$1(volumePanelState2);
                    VolumePanelRow findRow2 = volumePanelStateExt.findRow(volumePanelState2, this.stream);
                    if (findRow2 != null) {
                        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar2 = this.seekBar;
                        if (subFullLayoutVolumeSeekBar2 == null) {
                            subFullLayoutVolumeSeekBar2 = null;
                        }
                        subFullLayoutVolumeSeekBar2.setEnabled(findRow2.isSliderEnabled());
                    }
                    VolumePanelRow findRow3 = volumePanelStateExt.findRow(volumePanelState2, this.stream);
                    if (findRow3 != null) {
                        updateContentDescription$1(volumePanelState2, findRow3);
                    }
                    VolumePanelRow findRow4 = volumePanelStateExt.findRow(volumePanelState2, this.stream);
                    if (findRow4 != null) {
                        updateBluetoothDeviceIcon$1(findRow4);
                    }
                    StoreInteractor storeInteractor = this.storeInteractor;
                    VolumePanelAction.Builder stream = new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_CHECK_IF_NEED_TO_SET_PROGRESS).stream(this.stream);
                    SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar3 = this.seekBar;
                    storeInteractor.sendAction(stream.progress((subFullLayoutVolumeSeekBar3 != null ? subFullLayoutVolumeSeekBar3 : null).getProgress()).build(), true);
                    break;
                }
                break;
            case 2:
                if (this.isAODEnabled && this.stream == volumePanelState2.getStream()) {
                    updateProgress$1(volumePanelState2);
                    break;
                }
                break;
            case 3:
                if (this.stream == volumePanelState2.getStream()) {
                    updateProgress$1(volumePanelState2);
                    break;
                }
                break;
            case 4:
                if (this.stream == volumePanelState2.getStream()) {
                    HandlerWrapper handlerWrapper = this.handlerWrapper;
                    if (handlerWrapper == null) {
                        handlerWrapper = null;
                    }
                    handlerWrapper.remove(this.recheckCallback);
                    HandlerWrapper handlerWrapper2 = this.handlerWrapper;
                    (handlerWrapper2 != null ? handlerWrapper2 : null).postDelayed(this.recheckCallback, 1000L);
                    break;
                }
                break;
            case 5:
                if (this.stream == volumePanelState2.getStream()) {
                    StoreInteractor storeInteractor2 = this.storeInteractor;
                    VolumePanelAction.Builder stream2 = new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_UPDATE_PROGRESS_BAR).stream(this.stream);
                    SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar4 = this.seekBar;
                    if (subFullLayoutVolumeSeekBar4 == null) {
                        subFullLayoutVolumeSeekBar4 = null;
                    }
                    storeInteractor2.sendAction(stream2.progress(subFullLayoutVolumeSeekBar4.getProgress()).build(), true);
                    HandlerWrapper handlerWrapper3 = this.handlerWrapper;
                    if (handlerWrapper3 == null) {
                        handlerWrapper3 = null;
                    }
                    handlerWrapper3.remove(this.recheckCallback);
                    HandlerWrapper handlerWrapper4 = this.handlerWrapper;
                    (handlerWrapper4 != null ? handlerWrapper4 : null).postDelayed(this.recheckCallback, 1000L);
                    break;
                }
                break;
            case 6:
                this.storeInteractor.dispose();
                SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar5 = this.seekBar;
                (subFullLayoutVolumeSeekBar5 != null ? subFullLayoutVolumeSeekBar5 : null).storeInteractor.dispose();
                break;
            case 7:
                if (volumePanelState2.getStream() == this.stream) {
                    this.startProgress = true;
                    break;
                }
                break;
            case 8:
                if ((this.isAODEnabled || this.isDualViewEnabled || volumePanelState2.isExpanded()) && volumePanelState2.getStream() == this.stream) {
                    SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = this.volumePanelMotion;
                    if (subFullLayoutVolumePanelMotion == null) {
                        subFullLayoutVolumePanelMotion = null;
                    }
                    SpringAnimation springAnimation2 = this.touchDownAnimation;
                    if (springAnimation2 == null) {
                        springAnimation2 = null;
                    }
                    SpringAnimation springAnimation3 = this.touchUpAnimation;
                    SpringAnimation springAnimation4 = springAnimation3 != null ? springAnimation3 : null;
                    subFullLayoutVolumePanelMotion.getClass();
                    SubFullLayoutVolumePanelMotion.startSeekBarTouchDownAnimation(springAnimation2, springAnimation4, true);
                }
                animateSeekBarButton$1(volumePanelState2, true);
                break;
            case 9:
                if ((this.isAODEnabled || this.isDualViewEnabled || volumePanelState2.isExpanded()) && volumePanelState2.getStream() == this.stream) {
                    SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = this.volumePanelMotion;
                    if (subFullLayoutVolumePanelMotion2 == null) {
                        subFullLayoutVolumePanelMotion2 = null;
                    }
                    SpringAnimation springAnimation5 = this.touchUpAnimation;
                    if (springAnimation5 == null) {
                        springAnimation5 = null;
                    }
                    SpringAnimation springAnimation6 = this.touchDownAnimation;
                    if (springAnimation6 == null) {
                        springAnimation6 = null;
                    }
                    subFullLayoutVolumePanelMotion2.getClass();
                    if (springAnimation6 != null) {
                        if (springAnimation6.mRunning && springAnimation6.canSkipToEnd()) {
                            springAnimation = springAnimation6;
                        }
                        if (springAnimation != null) {
                            springAnimation.skipToEnd();
                        }
                    }
                    springAnimation5.animateToFinalPosition(1.0f);
                }
                animateSeekBarButton$1(volumePanelState2, false);
                break;
            case 10:
                if (this.stream == volumePanelState2.getStream()) {
                    SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar6 = this.seekBar;
                    if (subFullLayoutVolumeSeekBar6 == null) {
                        subFullLayoutVolumeSeekBar6 = null;
                    }
                    subFullLayoutVolumeSeekBar6.setFocusable(false);
                    SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar7 = this.seekBar;
                    if (subFullLayoutVolumeSeekBar7 == null) {
                        subFullLayoutVolumeSeekBar7 = null;
                    }
                    subFullLayoutVolumeSeekBar7.setFocusableInTouchMode(false);
                    SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar8 = this.seekBar;
                    if (subFullLayoutVolumeSeekBar8 == null) {
                        subFullLayoutVolumeSeekBar8 = null;
                    }
                    subFullLayoutVolumeSeekBar8.clearFocus();
                    SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar9 = this.seekBar;
                    if (subFullLayoutVolumeSeekBar9 == null) {
                        subFullLayoutVolumeSeekBar9 = null;
                    }
                    subFullLayoutVolumeSeekBar9.setBackground(null);
                    List<VolumePanelRow> volumeRowList = volumePanelState2.getVolumeRowList();
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : volumeRowList) {
                        if (VolumePanelValues.isSmartView(((VolumePanelRow) obj).getStreamType())) {
                            arrayList.add(obj);
                        }
                    }
                    ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        arrayList2.add(((VolumePanelRow) it.next()).getSmartViewLabel());
                    }
                    String str = (String) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList2);
                    if (str == null) {
                        str = "";
                    }
                    Toast.makeText(getContext(), getContext().getString(com.android.systemui.R.string.volume_use_your_phone_volume_smart_view, str), 0).show();
                    break;
                }
                break;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.storeInteractor.dispose();
        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = this.seekBar;
        if (subFullLayoutVolumeSeekBar == null) {
            subFullLayoutVolumeSeekBar = null;
        }
        subFullLayoutVolumeSeekBar.storeInteractor.dispose();
    }

    public final void updateBluetoothDeviceIcon$1(VolumePanelRow volumePanelRow) {
        ImageView imageView;
        if (volumePanelRow.getIconType() != 2 && volumePanelRow.getIconType() != 10 && volumePanelRow.getIconType() != 13 && volumePanelRow.getIconType() != 12) {
            ViewVisibilityUtil viewVisibilityUtil = ViewVisibilityUtil.INSTANCE;
            ImageView imageView2 = this.bluetoothDeviceIcon;
            imageView = imageView2 != null ? imageView2 : null;
            viewVisibilityUtil.getClass();
            ViewVisibilityUtil.setGone(imageView);
            return;
        }
        String dualBtDeviceAddress = volumePanelRow.getDualBtDeviceAddress();
        if (!this.isDualViewEnabled || TextUtils.isEmpty(dualBtDeviceAddress)) {
            return;
        }
        BluetoothIconUtil bluetoothIconUtil = BluetoothIconUtil.INSTANCE;
        Context context = getContext();
        bluetoothIconUtil.getClass();
        Drawable iconDrawable = BluetoothIconUtil.getIconDrawable(context, dualBtDeviceAddress);
        ImageView imageView3 = this.bluetoothDeviceIcon;
        if (imageView3 == null) {
            imageView3 = null;
        }
        imageView3.setImageDrawable(iconDrawable);
        ViewVisibilityUtil viewVisibilityUtil2 = ViewVisibilityUtil.INSTANCE;
        ImageView imageView4 = this.bluetoothDeviceIcon;
        imageView = imageView4 != null ? imageView4 : null;
        viewVisibilityUtil2.getClass();
        imageView.setVisibility(0);
    }

    public final void updateContentDescription$1(VolumePanelState volumePanelState, VolumePanelRow volumePanelRow) {
        int iconType = volumePanelRow.getIconType();
        String string = this.stream == 2 ? iconType == 0 ? getContext().getString(com.android.systemui.R.string.volume_icon_content_description_ringtone_to_sound) : volumePanelState.isHasVibrator() ? getContext().getString(com.android.systemui.R.string.volume_icon_content_description_ringtone_to_vib) : getContext().getString(com.android.systemui.R.string.volume_icon_content_description_ringtone_to_mute) : (iconType == 1 || volumePanelRow.isMuted() || volumePanelRow.getRealLevel() == 0) ? getContext().getString(com.android.systemui.R.string.volume_icon_content_description_to_unmute, this.label) : getContext().getString(com.android.systemui.R.string.volume_icon_content_description_to_mute, this.label);
        Intrinsics.checkNotNull(string);
        SubFullLayoutVolumeIcon subFullLayoutVolumeIcon = this.icon;
        if (subFullLayoutVolumeIcon == null) {
            subFullLayoutVolumeIcon = null;
        }
        subFullLayoutVolumeIcon.setContentDescription(string);
    }

    public final void updateProgress$1(VolumePanelState volumePanelState) {
        VolumePanelRow findRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, this.stream);
        if (findRow != null) {
            int viewRealLevel = ViewLevelConverter.viewRealLevel(findRow);
            if (!findRow.isVisible()) {
                ((SpringAnimation) this.progressBarSpring$delegate.getValue()).cancel();
                SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = this.seekBar;
                (subFullLayoutVolumeSeekBar != null ? subFullLayoutVolumeSeekBar : null).setProgress(viewRealLevel);
            } else {
                ((SpringAnimation) this.progressBarSpring$delegate.getValue()).setStartValue((this.seekBar != null ? r2 : null).getProgress());
                ((SpringAnimation) this.progressBarSpring$delegate.getValue()).animateToFinalPosition(viewRealLevel);
            }
        }
    }

    public final void updateProgressDrawable$1(VolumePanelState volumePanelState) {
        VolumePanelRow findRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, this.stream);
        if (findRow != null) {
            SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = this.seekBar;
            if (subFullLayoutVolumeSeekBar == null) {
                subFullLayoutVolumeSeekBar = null;
            }
            SecRoundedCornerSeekBarDrawable secRoundedCornerSeekBarDrawable = (SecRoundedCornerSeekBarDrawable) ((LayerDrawable) subFullLayoutVolumeSeekBar.getProgressDrawable()).findDrawableByLayerId(R.id.progress);
            secRoundedCornerSeekBarDrawable.setContext(getContext());
            boolean z = volumePanelState.isLeBroadcasting() && !findRow.isRoutedToBluetooth();
            if (volumePanelState.isSafeMediaDeviceOn() || volumePanelState.isSafeMediaPinDeviceOn()) {
                int earProtectLevel = findRow.getEarProtectLevel();
                int realLevel = findRow.getRealLevel();
                if (VolumePanelValues.isAudioSharing(findRow.getStreamType())) {
                    realLevel *= 100;
                }
                if (1 <= earProtectLevel && earProtectLevel < realLevel && !z) {
                    secRoundedCornerSeekBarDrawable.setShockColor(true);
                    return;
                }
            }
            secRoundedCornerSeekBarDrawable.setShockColor(false);
        }
    }

    public SubFullLayoutVolumeRowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.storeInteractor = new StoreInteractor(this, null);
        this.recheckCallback = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$recheckCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                StoreInteractor storeInteractor = SubFullLayoutVolumeRowView.this.storeInteractor;
                VolumePanelAction.Builder stream = new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_CHECK_IF_NEED_TO_SET_PROGRESS).stream(SubFullLayoutVolumeRowView.this.stream);
                SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = SubFullLayoutVolumeRowView.this.seekBar;
                if (subFullLayoutVolumeSeekBar == null) {
                    subFullLayoutVolumeSeekBar = null;
                }
                storeInteractor.sendAction(stream.progress(subFullLayoutVolumeSeekBar.getProgress()).build(), false);
            }
        };
        this.buttonAnimatorRunnable = SubFullLayoutVolumeRowView$buttonAnimatorRunnable$1.INSTANCE;
        this.progressBarSpring$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$progressBarSpring$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SpringAnimation springAnimation = new SpringAnimation(new FloatValueHolder());
                final SubFullLayoutVolumeRowView subFullLayoutVolumeRowView = SubFullLayoutVolumeRowView.this;
                SpringForce springForce = new SpringForce();
                springForce.setDampingRatio(1.0f);
                springForce.setStiffness(450.0f);
                springAnimation.mSpring = springForce;
                springAnimation.mVelocity = 0.0f;
                SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar = subFullLayoutVolumeRowView.seekBar;
                if (subFullLayoutVolumeSeekBar == null) {
                    subFullLayoutVolumeSeekBar = null;
                }
                springAnimation.setStartValue(subFullLayoutVolumeSeekBar.getProgress());
                springAnimation.setMinimumVisibleChange(1.0f);
                springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumeRowView$progressBarSpring$2$1$2
                    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
                    public final void onAnimationUpdate(float f, float f2) {
                        SubFullLayoutVolumeSeekBar subFullLayoutVolumeSeekBar2 = SubFullLayoutVolumeRowView.this.seekBar;
                        if (subFullLayoutVolumeSeekBar2 == null) {
                            subFullLayoutVolumeSeekBar2 = null;
                        }
                        subFullLayoutVolumeSeekBar2.setProgress((int) f);
                    }
                });
                return springAnimation;
            }
        });
    }
}
