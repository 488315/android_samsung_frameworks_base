package com.android.systemui.biometrics;

import android.R;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.view.Display;
import android.view.DisplayInfo;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieCompositionFactory;
import com.android.settingslib.widget.LottieColorUtils;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AuthBiometricFingerprintIconController extends AuthIconController {
    public Pair iconLayoutParamSize;
    public final LottieAnimationView iconViewOverlay;
    public final boolean isReverseDefaultRotation;
    public final boolean isSideFps;

    public AuthBiometricFingerprintIconController(Context context, LottieAnimationView lottieAnimationView, LottieAnimationView lottieAnimationView2) {
        super(context, lottieAnimationView);
        this.iconViewOverlay = lottieAnimationView2;
        this.isReverseDefaultRotation = context.getResources().getBoolean(R.bool.config_notificationHeaderClickableForExpand);
        boolean z = true;
        this.iconLayoutParamSize = new Pair(1, 1);
        setIconLayoutParamSize(new Pair(Integer.valueOf(context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.biometric_dialog_fingerprint_icon_width)), Integer.valueOf(context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.biometric_dialog_fingerprint_icon_height))));
        FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService("fingerprint");
        int i = 0;
        if (fingerprintManager != null) {
            List sensorPropertiesInternal = fingerprintManager.getSensorPropertiesInternal();
            if (!sensorPropertiesInternal.isEmpty()) {
                Iterator it = sensorPropertiesInternal.iterator();
                while (it.hasNext()) {
                    if (((FingerprintSensorPropertiesInternal) it.next()).isAnySidefpsType()) {
                        break;
                    }
                }
            }
        }
        z = false;
        this.isSideFps = z;
        if (z) {
            int[] iArr = {com.android.systemui.R.raw.biometricprompt_fingerprint_to_error_landscape, com.android.systemui.R.raw.biometricprompt_folded_base_bottomright, com.android.systemui.R.raw.biometricprompt_folded_base_default, com.android.systemui.R.raw.biometricprompt_folded_base_topleft, com.android.systemui.R.raw.biometricprompt_landscape_base, com.android.systemui.R.raw.biometricprompt_portrait_base_bottomright, com.android.systemui.R.raw.biometricprompt_portrait_base_topleft, com.android.systemui.R.raw.biometricprompt_symbol_error_to_fingerprint_landscape, com.android.systemui.R.raw.biometricprompt_symbol_error_to_fingerprint_portrait_bottomright, com.android.systemui.R.raw.biometricprompt_symbol_error_to_fingerprint_portrait_topleft, com.android.systemui.R.raw.biometricprompt_symbol_error_to_success_landscape, com.android.systemui.R.raw.biometricprompt_symbol_error_to_success_portrait_bottomright, com.android.systemui.R.raw.biometricprompt_symbol_error_to_success_portrait_topleft, com.android.systemui.R.raw.biometricprompt_symbol_fingerprint_to_error_portrait_bottomright, com.android.systemui.R.raw.biometricprompt_symbol_fingerprint_to_error_portrait_topleft, com.android.systemui.R.raw.biometricprompt_symbol_fingerprint_to_success_landscape, com.android.systemui.R.raw.f767x28a2dbae, com.android.systemui.R.raw.biometricprompt_symbol_fingerprint_to_success_portrait_topleft};
            while (i < 18) {
                int i2 = iArr[i];
                LottieCompositionFactory.fromRawRes(context, LottieCompositionFactory.rawResCacheKey(i2, context), i2);
                i++;
            }
        } else {
            int[] iArr2 = {com.android.systemui.R.raw.fingerprint_dialogue_error_to_fingerprint_lottie, com.android.systemui.R.raw.fingerprint_dialogue_error_to_success_lottie, com.android.systemui.R.raw.fingerprint_dialogue_fingerprint_to_error_lottie, com.android.systemui.R.raw.fingerprint_dialogue_fingerprint_to_success_lottie};
            while (i < 4) {
                int i3 = iArr2[i];
                LottieCompositionFactory.fromRawRes(context, LottieCompositionFactory.rawResCacheKey(i3, context), i3);
                i++;
            }
        }
        DisplayInfo displayInfo = new DisplayInfo();
        Display display = context.getDisplay();
        if (display != null) {
            display.getDisplayInfo(displayInfo);
        }
        if (z) {
            int i4 = displayInfo.rotation;
            if ((this.isReverseDefaultRotation ? (i4 + 1) % 4 : i4) == 2) {
                lottieAnimationView.setRotation(180.0f);
            }
        }
    }

    public Integer getAnimationForTransition(int i, int i2) {
        int i3 = com.android.systemui.R.raw.fingerprint_dialogue_fingerprint_to_error_lottie;
        if (i2 == 1 || i2 == 2) {
            if (i == 3 || i == 4) {
                i3 = com.android.systemui.R.raw.fingerprint_dialogue_error_to_fingerprint_lottie;
            }
        } else if (i2 != 3 && i2 != 4) {
            if (i2 != 6) {
                return null;
            }
            i3 = (i == 3 || i == 4) ? com.android.systemui.R.raw.fingerprint_dialogue_error_to_success_lottie : com.android.systemui.R.raw.fingerprint_dialogue_fingerprint_to_success_lottie;
        }
        return Integer.valueOf(i3);
    }

    public final CharSequence getIconContentDescription(int i) {
        Integer valueOf;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 5:
            case 6:
                valueOf = Integer.valueOf(this.isSideFps ? com.android.systemui.R.string.security_settings_sfps_enroll_find_sensor_message : com.android.systemui.R.string.fingerprint_dialog_touch_sensor);
                break;
            case 3:
            case 4:
                valueOf = Integer.valueOf(com.android.systemui.R.string.biometric_dialog_try_again);
                break;
            default:
                valueOf = null;
                break;
        }
        if (valueOf != null) {
            return this.context.getString(valueOf.intValue());
        }
        return null;
    }

    public final void setIconLayoutParamSize(Pair pair) {
        if (Intrinsics.areEqual(this.iconLayoutParamSize, pair)) {
            return;
        }
        this.iconViewOverlay.getLayoutParams().width = ((Number) pair.getFirst()).intValue();
        this.iconViewOverlay.getLayoutParams().height = ((Number) pair.getSecond()).intValue();
        this.iconView.getLayoutParams().width = ((Number) pair.getFirst()).intValue();
        this.iconView.getLayoutParams().height = ((Number) pair.getSecond()).intValue();
        this.iconLayoutParamSize = pair;
    }

    public boolean shouldAnimateIconViewForTransition(int i, int i2) {
        if (i2 == 1 || i2 == 2) {
            if (i != 4 && i != 3) {
                return false;
            }
        } else if (i2 != 3 && i2 != 4 && i2 != 6) {
            return false;
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:71:0x0071, code lost:
    
        if (r0 == 3) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0086, code lost:
    
        if (r0 == 3) goto L61;
     */
    @Override // com.android.systemui.biometrics.AuthIconController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateIcon(int i, int i2) {
        Integer valueOf;
        int i3;
        boolean z = false;
        if (!this.isSideFps) {
            this.iconViewOverlay.setVisibility(8);
            Integer animationForTransition = getAnimationForTransition(i, i2);
            if (animationForTransition != null) {
                int intValue = animationForTransition.intValue();
                if (i != 1 || i2 != 2) {
                    this.iconView.setAnimation(intValue);
                }
                CharSequence iconContentDescription = getIconContentDescription(i2);
                if (iconContentDescription != null) {
                    this.iconView.setContentDescription(iconContentDescription);
                }
                this.iconView.lottieDrawable.setFrame(0);
                if (shouldAnimateIconViewForTransition(i, i2)) {
                    this.iconView.playAnimation();
                }
                if (this.isSideFps) {
                    LottieColorUtils.applyDynamicColors(this.context, this.iconView);
                    return;
                }
                return;
            }
            return;
        }
        DisplayInfo displayInfo = new DisplayInfo();
        Display display = this.context.getDisplay();
        if (display != null) {
            display.getDisplayInfo(displayInfo);
        }
        int i4 = displayInfo.rotation;
        if (this.isReverseDefaultRotation) {
            i4 = (i4 + 1) % 4;
        }
        int i5 = com.android.systemui.R.raw.biometricprompt_symbol_fingerprint_to_error_portrait_bottomright;
        if (i2 == 1 || i2 == 2) {
            if (i == 3 || i == 4) {
                if (i4 != 0) {
                    if (i4 == 1) {
                        i5 = com.android.systemui.R.raw.biometricprompt_symbol_error_to_fingerprint_portrait_topleft;
                    } else if (i4 != 2 && i4 == 3) {
                        i5 = com.android.systemui.R.raw.biometricprompt_symbol_error_to_fingerprint_portrait_bottomright;
                    }
                }
                i5 = com.android.systemui.R.raw.biometricprompt_symbol_error_to_fingerprint_landscape;
            } else {
                if (i4 != 0) {
                    if (i4 == 1) {
                        i5 = com.android.systemui.R.raw.biometricprompt_symbol_fingerprint_to_error_portrait_topleft;
                    } else if (i4 != 2) {
                    }
                }
                i5 = com.android.systemui.R.raw.biometricprompt_fingerprint_to_error_landscape;
            }
            valueOf = Integer.valueOf(i5);
        } else if (i2 == 3 || i2 == 4) {
            if (i4 != 0) {
                if (i4 == 1) {
                    i5 = com.android.systemui.R.raw.biometricprompt_symbol_fingerprint_to_error_portrait_topleft;
                } else if (i4 != 2) {
                }
                valueOf = Integer.valueOf(i5);
            }
            i5 = com.android.systemui.R.raw.biometricprompt_fingerprint_to_error_landscape;
            valueOf = Integer.valueOf(i5);
        } else if (i2 != 6) {
            valueOf = null;
        } else {
            if (i == 3 || i == 4) {
                if (i4 != 0) {
                    if (i4 == 1) {
                        i3 = com.android.systemui.R.raw.biometricprompt_symbol_error_to_success_portrait_topleft;
                    } else if (i4 != 2 && i4 == 3) {
                        i3 = com.android.systemui.R.raw.biometricprompt_symbol_error_to_success_portrait_bottomright;
                    }
                }
                i3 = com.android.systemui.R.raw.biometricprompt_symbol_error_to_success_landscape;
            } else {
                if (i4 != 0) {
                    if (i4 == 1) {
                        i3 = com.android.systemui.R.raw.biometricprompt_symbol_fingerprint_to_success_portrait_topleft;
                    } else if (i4 != 2 && i4 == 3) {
                        i3 = com.android.systemui.R.raw.f767x28a2dbae;
                    }
                }
                i3 = com.android.systemui.R.raw.biometricprompt_symbol_fingerprint_to_success_landscape;
            }
            valueOf = Integer.valueOf(i3);
        }
        if (valueOf != null) {
            int intValue2 = valueOf.intValue();
            if (i != 1 || i2 != 2) {
                this.iconViewOverlay.setAnimation(intValue2);
            }
            CharSequence iconContentDescription2 = getIconContentDescription(i2);
            if (iconContentDescription2 != null) {
                this.iconView.setContentDescription(iconContentDescription2);
            }
            this.iconView.lottieDrawable.setFrame(0);
            this.iconViewOverlay.lottieDrawable.setFrame(0);
            if (i2 == 1 || i2 == 2 ? i == 4 || i == 3 || i == 0 : i2 == 3 || i2 == 4 || i2 == 6) {
                this.iconView.playAnimation();
            }
            if (i2 == 1 || i2 == 2 ? i == 4 || i == 3 : i2 == 3 || i2 == 4 || i2 == 6) {
                z = true;
            }
            if (z) {
                this.iconViewOverlay.playAnimation();
            }
            LottieColorUtils.applyDynamicColors(this.context, this.iconView);
            LottieColorUtils.applyDynamicColors(this.context, this.iconViewOverlay);
        }
    }
}
