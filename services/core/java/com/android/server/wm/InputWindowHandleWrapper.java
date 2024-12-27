package com.android.server.wm;

import android.os.IBinder;
import android.view.InputWindowHandle;

import java.util.Objects;

public final class InputWindowHandleWrapper {
    public boolean mChanged = true;
    public final InputWindowHandle mHandle;

    public InputWindowHandleWrapper(InputWindowHandle inputWindowHandle) {
        this.mHandle = inputWindowHandle;
    }

    public final void setFocusable(boolean z) {
        InputWindowHandle inputWindowHandle = this.mHandle;
        if (((inputWindowHandle.inputConfig & 4) == 0) == z) {
            return;
        }
        inputWindowHandle.setInputConfig(4, !z);
        this.mChanged = true;
    }

    public final void setInputConfigMasked(int i, int i2) {
        int i3 = i & i2;
        InputWindowHandle inputWindowHandle = this.mHandle;
        int i4 = inputWindowHandle.inputConfig;
        if (i3 == (i4 & i2)) {
            return;
        }
        inputWindowHandle.inputConfig = i3 | ((~i2) & i4);
        this.mChanged = true;
    }

    public final void setName(String str) {
        if (Objects.equals(this.mHandle.name, str)) {
            return;
        }
        this.mHandle.name = str;
        this.mChanged = true;
    }

    public final void setOneHandSpecs(float f, float f2, float f3) {
        InputWindowHandle inputWindowHandle = this.mHandle;
        if (inputWindowHandle.oneHandOffsetX == f
                && inputWindowHandle.oneHandOffsetY == f2
                && inputWindowHandle.oneHandScale == f3) {
            return;
        }
        inputWindowHandle.oneHandOffsetX = f;
        inputWindowHandle.oneHandOffsetY = f2;
        inputWindowHandle.oneHandScale = f3;
        this.mChanged = true;
    }

    public final void setToken(IBinder iBinder) {
        InputWindowHandle inputWindowHandle = this.mHandle;
        if (inputWindowHandle.token == iBinder) {
            return;
        }
        inputWindowHandle.token = iBinder;
        this.mChanged = true;
    }

    public final String toString() {
        return this.mHandle + ", changed=" + this.mChanged;
    }
}
