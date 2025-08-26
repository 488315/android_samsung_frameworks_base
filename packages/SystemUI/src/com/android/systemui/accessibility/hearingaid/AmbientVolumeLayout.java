package com.android.systemui.accessibility.hearingaid;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.settingslib.bluetooth.AmbientVolumeUi;
import com.android.settingslib.bluetooth.AmbientVolumeUiController;
import com.android.settingslib.bluetooth.AmbientVolumeUiController$$ExternalSyntheticLambda4;
import com.android.systemui.R;
import com.google.android.material.slider.Slider;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashBiMap.View.AnonymousClass1;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes.dex */
public class AmbientVolumeLayout extends LinearLayout implements AmbientVolumeUi {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ImageView mExpandIcon;
    public boolean mExpandable;
    public boolean mExpanded;
    public int mLaunchSourceId;
    public AmbientVolumeUiController mListener;
    public boolean mMutable;
    public boolean mMuted;
    public final HashBiMap mSideToSliderMap;
    public final AmbientVolumeLayout$$ExternalSyntheticLambda0 mSliderOnChangeListener;
    public HearingDevicesUiEventLogger mUiEventLogger;
    public final ImageView mVolumeIcon;
    public int mVolumeLevel;

    public AmbientVolumeLayout(Context context) {
        this(context, null);
    }

    public final void createSlider(int i) {
        if (this.mSideToSliderMap.containsKey(Integer.valueOf(i))) {
            return;
        }
        AmbientVolumeSlider ambientVolumeSlider = new AmbientVolumeSlider(((LinearLayout) this).mContext);
        AmbientVolumeLayout$$ExternalSyntheticLambda0 ambientVolumeLayout$$ExternalSyntheticLambda0 = this.mSliderOnChangeListener;
        if (ambientVolumeLayout$$ExternalSyntheticLambda0 != null) {
            ((ArrayList) ambientVolumeSlider.mChangeListeners).add(ambientVolumeLayout$$ExternalSyntheticLambda0);
        }
        if (i == 0) {
            String string = ((LinearLayout) this).mContext.getString(R.string.hearing_devices_ambient_control_left);
            ambientVolumeSlider.mTitle.setText(string);
            ambientVolumeSlider.mTitle.setVisibility(TextUtils.isEmpty(string) ? 8 : 0);
            ambientVolumeSlider.setContentDescription(((LinearLayout) this).mContext.getString(R.string.hearing_devices_ambient_control_left));
            String string2 = ((LinearLayout) this).mContext.getString(R.string.hearing_devices_ambient_control_left_description);
            Slider slider = ambientVolumeSlider.mSlider;
            if (slider != null) {
                slider.setContentDescription(string2);
            }
        } else if (i == 1) {
            String string3 = ((LinearLayout) this).mContext.getString(R.string.hearing_devices_ambient_control_right);
            ambientVolumeSlider.mTitle.setText(string3);
            ambientVolumeSlider.mTitle.setVisibility(TextUtils.isEmpty(string3) ? 8 : 0);
            ambientVolumeSlider.setContentDescription(((LinearLayout) this).mContext.getString(R.string.hearing_devices_ambient_control_right));
            String string4 = ((LinearLayout) this).mContext.getString(R.string.hearing_devices_ambient_control_right_description);
            Slider slider2 = ambientVolumeSlider.mSlider;
            if (slider2 != null) {
                slider2.setContentDescription(string4);
            }
        } else {
            String string5 = ((LinearLayout) this).mContext.getString(R.string.hearing_devices_ambient_control_description);
            Slider slider3 = ambientVolumeSlider.mSlider;
            if (slider3 != null) {
                slider3.setContentDescription(string5);
            }
        }
        this.mSideToSliderMap.put(Integer.valueOf(i), ambientVolumeSlider);
    }

    public ImageView getExpandIcon() {
        return this.mExpandIcon;
    }

    public Map<Integer, AmbientVolumeSlider> getSliders() {
        return this.mSideToSliderMap;
    }

    public ImageView getVolumeIcon() {
        return this.mVolumeIcon;
    }

    public final int getVolumeLevel(int i) {
        AmbientVolumeSlider ambientVolumeSlider = (AmbientVolumeSlider) this.mSideToSliderMap.get(Integer.valueOf(i));
        if (ambientVolumeSlider == null || !ambientVolumeSlider.mSlider.isEnabled() || !ambientVolumeSlider.mSlider.isEnabled()) {
            return 0;
        }
        double d = ambientVolumeSlider.mSlider.valueFrom;
        return (int) Math.ceil((r6.getValue() - d) / ((r6.valueTo - d) / 4.0d));
    }

    public final void setExpanded(boolean z) {
        if (this.mExpandable || !z) {
            this.mExpanded = z;
            updateExpandIcon();
            updateLayout();
        }
    }

    public final void setMuted(boolean z) {
        boolean z2 = this.mMutable;
        if (z2 || !z) {
            this.mMuted = z;
            if (z2 && z) {
                HashBiMap.View.AnonymousClass1 anonymousClass1 = ((HashBiMap.View) this.mSideToSliderMap.values()).new AnonymousClass1();
                while (anonymousClass1.hasNext()) {
                    Slider slider = ((AmbientVolumeSlider) anonymousClass1.next()).mSlider;
                    slider.setValues(Float.valueOf(slider.valueFrom));
                }
            }
            updateVolumeIcon();
        }
    }

    public final void setSliderEnabled(int i, boolean z) {
        AmbientVolumeSlider ambientVolumeSlider = (AmbientVolumeSlider) this.mSideToSliderMap.get(Integer.valueOf(i));
        if (ambientVolumeSlider == null || ambientVolumeSlider.mSlider.isEnabled() == z) {
            return;
        }
        ambientVolumeSlider.setEnabled(z);
        updateLayout();
    }

    public final void updateExpandIcon() {
        this.mExpandIcon.setVisibility(this.mExpandable ? 0 : 8);
        this.mExpandIcon.setRotation(this.mExpanded ? 180.0f : 0.0f);
        if (this.mExpandable) {
            this.mExpandIcon.setContentDescription(((LinearLayout) this).mContext.getString(this.mExpanded ? R.string.hearing_devices_ambient_collapse_controls : R.string.hearing_devices_ambient_expand_controls));
        } else {
            this.mExpandIcon.setContentDescription(null);
        }
    }

    public final void updateLayout() {
        this.mSideToSliderMap.forEach(new AmbientVolumeLayout$$ExternalSyntheticLambda3(this, 0));
        updateVolumeLevel();
    }

    public final void updateVolumeIcon() {
        this.mVolumeIcon.setImageLevel(this.mMuted ? 0 : this.mVolumeLevel);
        if (this.mMutable) {
            this.mVolumeIcon.setContentDescription(((LinearLayout) this).mContext.getString(this.mMuted ? R.string.hearing_devices_ambient_unmute : R.string.hearing_devices_ambient_mute));
            this.mVolumeIcon.setImportantForAccessibility(1);
        } else {
            this.mVolumeIcon.setContentDescription(null);
            this.mVolumeIcon.setImportantForAccessibility(2);
        }
    }

    public final void updateVolumeLevel() {
        int volumeLevel;
        int volumeLevel2;
        if (this.mExpanded) {
            volumeLevel = getVolumeLevel(0);
            volumeLevel2 = getVolumeLevel(1);
        } else {
            volumeLevel = getVolumeLevel(999);
            volumeLevel2 = volumeLevel;
        }
        this.mVolumeLevel = Math.min(Math.max((volumeLevel * 5) + volumeLevel2, 0), 24);
        updateVolumeIcon();
    }

    public AmbientVolumeLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AmbientVolumeLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AmbientVolumeLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mExpandable = true;
        this.mExpanded = false;
        this.mMutable = false;
        this.mMuted = false;
        this.mSideToSliderMap = HashBiMap.create();
        this.mVolumeLevel = 24;
        this.mSliderOnChangeListener = new AmbientVolumeLayout$$ExternalSyntheticLambda0(this);
        LinearLayout.inflate(context, R.layout.hearing_device_ambient_volume_layout, this);
        ImageView imageView = (ImageView) requireViewById(R.id.ambient_volume_icon);
        this.mVolumeIcon = imageView;
        imageView.setImageResource(R.drawable.ic_ambient_volume);
        final int i3 = 0;
        this.mVolumeIcon.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.accessibility.hearingaid.AmbientVolumeLayout$$ExternalSyntheticLambda1
            public final /* synthetic */ AmbientVolumeLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i4 = i3;
                AmbientVolumeLayout ambientVolumeLayout = this.f$0;
                switch (i4) {
                    case 0:
                        if (ambientVolumeLayout.mMutable) {
                            ambientVolumeLayout.setMuted(!ambientVolumeLayout.mMuted);
                            HearingDevicesUiEventLogger hearingDevicesUiEventLogger = ambientVolumeLayout.mUiEventLogger;
                            if (hearingDevicesUiEventLogger != null) {
                                hearingDevicesUiEventLogger.log(ambientVolumeLayout.mMuted ? HearingDevicesUiEvent.HEARING_DEVICES_AMBIENT_MUTE : HearingDevicesUiEvent.HEARING_DEVICES_AMBIENT_UNMUTE, ambientVolumeLayout.mLaunchSourceId, null);
                            }
                            AmbientVolumeUiController ambientVolumeUiController = ambientVolumeLayout.mListener;
                            if (ambientVolumeUiController != null) {
                                AmbientVolumeLayout ambientVolumeLayout2 = (AmbientVolumeLayout) ambientVolumeUiController.mAmbientLayout;
                                boolean z = ambientVolumeLayout2.mMuted;
                                HashBiMap hashBiMap = ambientVolumeUiController.mSideToDeviceMap;
                                if (!z) {
                                    hashBiMap.forEach(new AmbientVolumeUiController$$ExternalSyntheticLambda4(ambientVolumeUiController, 2));
                                }
                                HashBiMap.View.AnonymousClass1 anonymousClass1 = ((HashBiMap.View) hashBiMap.values()).new AnonymousClass1();
                                while (anonymousClass1.hasNext()) {
                                    ambientVolumeUiController.mVolumeController.setMuted((BluetoothDevice) anonymousClass1.next(), ambientVolumeLayout2.mMuted);
                                }
                                break;
                            }
                        }
                        break;
                    default:
                        ambientVolumeLayout.setExpanded(!ambientVolumeLayout.mExpanded);
                        HearingDevicesUiEventLogger hearingDevicesUiEventLogger2 = ambientVolumeLayout.mUiEventLogger;
                        if (hearingDevicesUiEventLogger2 != null) {
                            hearingDevicesUiEventLogger2.log(ambientVolumeLayout.mExpanded ? HearingDevicesUiEvent.HEARING_DEVICES_AMBIENT_EXPAND_CONTROLS : HearingDevicesUiEvent.HEARING_DEVICES_AMBIENT_COLLAPSE_CONTROLS, ambientVolumeLayout.mLaunchSourceId, null);
                        }
                        AmbientVolumeUiController ambientVolumeUiController2 = ambientVolumeLayout.mListener;
                        if (ambientVolumeUiController2 != null) {
                            ambientVolumeUiController2.mSideToDeviceMap.forEach(new AmbientVolumeUiController$$ExternalSyntheticLambda4(ambientVolumeUiController2, 0));
                            ambientVolumeUiController2.mLocalDataManager.flush();
                            break;
                        }
                        break;
                }
            }
        });
        updateVolumeIcon();
        ImageView imageView2 = (ImageView) requireViewById(R.id.ambient_expand_icon);
        this.mExpandIcon = imageView2;
        final int i4 = 1;
        imageView2.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.accessibility.hearingaid.AmbientVolumeLayout$$ExternalSyntheticLambda1
            public final /* synthetic */ AmbientVolumeLayout f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i42 = i4;
                AmbientVolumeLayout ambientVolumeLayout = this.f$0;
                switch (i42) {
                    case 0:
                        if (ambientVolumeLayout.mMutable) {
                            ambientVolumeLayout.setMuted(!ambientVolumeLayout.mMuted);
                            HearingDevicesUiEventLogger hearingDevicesUiEventLogger = ambientVolumeLayout.mUiEventLogger;
                            if (hearingDevicesUiEventLogger != null) {
                                hearingDevicesUiEventLogger.log(ambientVolumeLayout.mMuted ? HearingDevicesUiEvent.HEARING_DEVICES_AMBIENT_MUTE : HearingDevicesUiEvent.HEARING_DEVICES_AMBIENT_UNMUTE, ambientVolumeLayout.mLaunchSourceId, null);
                            }
                            AmbientVolumeUiController ambientVolumeUiController = ambientVolumeLayout.mListener;
                            if (ambientVolumeUiController != null) {
                                AmbientVolumeLayout ambientVolumeLayout2 = (AmbientVolumeLayout) ambientVolumeUiController.mAmbientLayout;
                                boolean z = ambientVolumeLayout2.mMuted;
                                HashBiMap hashBiMap = ambientVolumeUiController.mSideToDeviceMap;
                                if (!z) {
                                    hashBiMap.forEach(new AmbientVolumeUiController$$ExternalSyntheticLambda4(ambientVolumeUiController, 2));
                                }
                                HashBiMap.View.AnonymousClass1 anonymousClass1 = ((HashBiMap.View) hashBiMap.values()).new AnonymousClass1();
                                while (anonymousClass1.hasNext()) {
                                    ambientVolumeUiController.mVolumeController.setMuted((BluetoothDevice) anonymousClass1.next(), ambientVolumeLayout2.mMuted);
                                }
                                break;
                            }
                        }
                        break;
                    default:
                        ambientVolumeLayout.setExpanded(!ambientVolumeLayout.mExpanded);
                        HearingDevicesUiEventLogger hearingDevicesUiEventLogger2 = ambientVolumeLayout.mUiEventLogger;
                        if (hearingDevicesUiEventLogger2 != null) {
                            hearingDevicesUiEventLogger2.log(ambientVolumeLayout.mExpanded ? HearingDevicesUiEvent.HEARING_DEVICES_AMBIENT_EXPAND_CONTROLS : HearingDevicesUiEvent.HEARING_DEVICES_AMBIENT_COLLAPSE_CONTROLS, ambientVolumeLayout.mLaunchSourceId, null);
                        }
                        AmbientVolumeUiController ambientVolumeUiController2 = ambientVolumeLayout.mListener;
                        if (ambientVolumeUiController2 != null) {
                            ambientVolumeUiController2.mSideToDeviceMap.forEach(new AmbientVolumeUiController$$ExternalSyntheticLambda4(ambientVolumeUiController2, 0));
                            ambientVolumeUiController2.mLocalDataManager.flush();
                            break;
                        }
                        break;
                }
            }
        });
        updateExpandIcon();
    }
}
