package android.service.controls.templates;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public abstract class ControlTemplate {
    private static final ControlTemplate ERROR_TEMPLATE;
    private static final String KEY_TEMPLATE_ID = "key_template_id";
    private static final String KEY_TEMPLATE_TYPE = "key_template_type";
    public static final ControlTemplate NO_TEMPLATE;
    private static final String TAG = "ControlTemplate";
    public static final int TYPE_ERROR = -1;
    public static final int TYPE_NO_TEMPLATE = 0;
    public static final int TYPE_RANGE = 2;
    public static final int TYPE_STATELESS = 8;
    public static final int TYPE_TEMPERATURE = 7;
    public static final int TYPE_THUMBNAIL = 3;
    public static final int TYPE_TOGGLE = 1;
    public static final int TYPE_TOGGLE_RANGE = 6;
    private final String mTemplateId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface TemplateType {
    }

    public abstract int getTemplateType();

    public void prepareTemplateForBinder(Context context) {
    }

    static {
        String str = "";
        NO_TEMPLATE = new ControlTemplate(str) { // from class: android.service.controls.templates.ControlTemplate.1
            @Override // android.service.controls.templates.ControlTemplate
            public int getTemplateType() {
                return 0;
            }
        };
        ERROR_TEMPLATE = new ControlTemplate(str) { // from class: android.service.controls.templates.ControlTemplate.2
            @Override // android.service.controls.templates.ControlTemplate
            public int getTemplateType() {
                return -1;
            }
        };
    }

    public String getTemplateId() {
        return this.mTemplateId;
    }

    Bundle getDataBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TEMPLATE_TYPE, getTemplateType());
        bundle.putString(KEY_TEMPLATE_ID, this.mTemplateId);
        return bundle;
    }

    private ControlTemplate() {
        this.mTemplateId = "";
    }

    ControlTemplate(Bundle bundle) {
        this.mTemplateId = bundle.getString(KEY_TEMPLATE_ID);
    }

    ControlTemplate(String str) {
        Preconditions.checkNotNull(str);
        this.mTemplateId = str;
    }

    static ControlTemplate createTemplateFromBundle(Bundle bundle) {
        if (bundle == null) {
            Log.e(TAG, "Null bundle");
            return ERROR_TEMPLATE;
        }
        int i = bundle.getInt(KEY_TEMPLATE_TYPE, -1);
        try {
            if (i == 0) {
                return NO_TEMPLATE;
            }
            if (i == 1) {
                return new ToggleTemplate(bundle);
            }
            if (i == 2) {
                return new RangeTemplate(bundle);
            }
            if (i == 3) {
                return new ThumbnailTemplate(bundle);
            }
            if (i == 6) {
                return new ToggleRangeTemplate(bundle);
            }
            if (i == 7) {
                return new TemperatureControlTemplate(bundle);
            }
            if (i == 8) {
                return new StatelessTemplate(bundle);
            }
            return ERROR_TEMPLATE;
        } catch (Exception e) {
            Log.e(TAG, "Error creating template", e);
            return ERROR_TEMPLATE;
        }
    }

    public static ControlTemplate getErrorTemplate() {
        return ERROR_TEMPLATE;
    }

    public static ControlTemplate getNoTemplateObject() {
        return NO_TEMPLATE;
    }
}
