package android.service.controls.templates;

import android.os.Bundle;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class TemperatureControlTemplate extends ControlTemplate {
    private static final int ALL_FLAGS = 62;
    public static final int FLAG_MODE_COOL = 8;
    public static final int FLAG_MODE_ECO = 32;
    public static final int FLAG_MODE_HEAT = 4;
    public static final int FLAG_MODE_HEAT_COOL = 16;
    public static final int FLAG_MODE_OFF = 2;
    private static final String KEY_CURRENT_ACTIVE_MODE = "key_current_active_mode";
    private static final String KEY_CURRENT_MODE = "key_current_mode";
    private static final String KEY_MODES = "key_modes";
    private static final String KEY_TEMPLATE = "key_template";
    public static final int MODE_COOL = 3;
    public static final int MODE_ECO = 5;
    public static final int MODE_HEAT = 2;
    public static final int MODE_HEAT_COOL = 4;
    public static final int MODE_OFF = 1;
    public static final int MODE_UNKNOWN = 0;
    private static final int NUM_MODES = 6;
    private static final String TAG = "ThermostatTemplate";
    private static final int TYPE = 7;
    private static final int[] modeToFlag = {0, 2, 4, 8, 16, 32};
    private final int mCurrentActiveMode;
    private final int mCurrentMode;
    private final int mModes;
    private final ControlTemplate mTemplate;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModeFlag {
    }

    @Override // android.service.controls.templates.ControlTemplate
    public int getTemplateType() {
        return 7;
    }

    public TemperatureControlTemplate(String str, ControlTemplate controlTemplate, int i, int i2, int i3) {
        super(str);
        Preconditions.checkNotNull(controlTemplate);
        this.mTemplate = controlTemplate;
        if (i < 0 || i >= 6) {
            Log.e(TAG, "Invalid current mode:" + i);
            this.mCurrentMode = 0;
        } else {
            this.mCurrentMode = i;
        }
        if (i2 < 0 || i2 >= 6) {
            Log.e(TAG, "Invalid current active mode:" + i2);
            this.mCurrentActiveMode = 0;
        } else {
            this.mCurrentActiveMode = i2;
        }
        int i4 = i3 & 62;
        this.mModes = i4;
        int i5 = this.mCurrentMode;
        if (i5 != 0 && (modeToFlag[i5] & i4) == 0) {
            throw new IllegalArgumentException("Mode " + this.mCurrentMode + " not supported in flag.");
        }
        int i6 = this.mCurrentActiveMode;
        if (i6 == 0 || (modeToFlag[i6] & i4) != 0) {
            return;
        }
        throw new IllegalArgumentException("Mode " + i2 + " not supported in flag.");
    }

    TemperatureControlTemplate(Bundle bundle) {
        super(bundle);
        this.mTemplate = ControlTemplate.createTemplateFromBundle(bundle.getBundle(KEY_TEMPLATE));
        this.mCurrentMode = bundle.getInt(KEY_CURRENT_MODE);
        this.mCurrentActiveMode = bundle.getInt(KEY_CURRENT_ACTIVE_MODE);
        this.mModes = bundle.getInt(KEY_MODES);
    }

    @Override // android.service.controls.templates.ControlTemplate
    Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putBundle(KEY_TEMPLATE, this.mTemplate.getDataBundle());
        dataBundle.putInt(KEY_CURRENT_MODE, this.mCurrentMode);
        dataBundle.putInt(KEY_CURRENT_ACTIVE_MODE, this.mCurrentActiveMode);
        dataBundle.putInt(KEY_MODES, this.mModes);
        return dataBundle;
    }

    public ControlTemplate getTemplate() {
        return this.mTemplate;
    }

    public int getCurrentMode() {
        return this.mCurrentMode;
    }

    public int getCurrentActiveMode() {
        return this.mCurrentActiveMode;
    }

    public int getModes() {
        return this.mModes;
    }
}
