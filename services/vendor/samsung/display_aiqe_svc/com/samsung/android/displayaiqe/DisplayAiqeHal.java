package com.samsung.android.displayaiqe;

public interface DisplayAiqeHal {
    String getContentMode();

    int getCoprValue();

    boolean getDisplayService();

    boolean setBlueLightFilterMode(boolean z, int i);

    boolean setByPassMode(boolean z);

    boolean setContentMode(int i);

    boolean setEnvironmentAdaptiveDisplayLevel(int i);

    boolean setEnvironmentAdaptiveDisplayMode(int i);

    boolean setExtraDimMode(int i);

    boolean setHighBrightnessMode(int i);

    boolean setHighDynamicRangeMode(boolean z);

    boolean setNaturalMode(String str);

    boolean setScreenMode(int i);

    boolean setVividnessMode(int i);

    boolean setWhiteBalanceMode(int i, int i2, int i3, int i4, int i5, int i6);
}
