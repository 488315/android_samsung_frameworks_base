package com.samsung.android.media.portrait.semportraiteditor;

import com.samsung.android.media.portrait.semportraiteditor.controllerinterface.PortraitController;
import com.samsung.android.media.portrait.semportraiteditor.controllerinterface.PortraitData;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes6.dex */
public class SemPortraitEditor {
    public static final int PORTRAIT_CONTROLLER_ADD_PORTRAIT_PARAM = 3000;
    public static final int PORTRAIT_CONTROLLER_DUALBOKEH = 2;
    public static final int PORTRAIT_CONTROLLER_DUAL_ART_BOKEH_MASK = 2205;
    public static final int PORTRAIT_CONTROLLER_DUAL_BACKDROP_REGION_COLOR_PARAM = 2206;
    public static final int PORTRAIT_CONTROLLER_DUAL_BOKEH_PARAM = 2001;
    public static final int PORTRAIT_CONTROLLER_DUAL_DISPARITY_DATA_PARAM = 2202;
    public static final int PORTRAIT_CONTROLLER_DUAL_FOCUS_POINT = 2004;
    public static final int PORTRAIT_CONTROLLER_DUAL_GLASS_BUFFER_PARAM = 2201;
    public static final int PORTRAIT_CONTROLLER_DUAL_INIT_PARAM = 2000;
    public static final int PORTRAIT_CONTROLLER_DUAL_META_DATA = 2003;
    public static final int PORTRAIT_CONTROLLER_DUAL_OUTPUT_IMG_PARAM = 2302;
    public static final int PORTRAIT_CONTROLLER_DUAL_RELIGHTING_EXTRA_INFO = 2204;
    public static final int PORTRAIT_CONTROLLER_DUAL_RELIGHTING_MASK_BUFFER_PARAM = 2203;
    public static final int PORTRAIT_CONTROLLER_DUAL_SAVE_OUTPUT_IMG_PARAM = 2304;
    public static final int PORTRAIT_CONTROLLER_DUAL_SAVE_SRC_IMG_PARAM = 2303;
    public static final int PORTRAIT_CONTROLLER_DUAL_SRC_IMG_PARAM = 2301;
    public static final int PORTRAIT_CONTROLLER_EFFECT_LEVEL = 11;
    public static final int PORTRAIT_CONTROLLER_EFFECT_TYPE = 10;
    public static final int PORTRAIT_CONTROLLER_ERROR_CODE_ERROR = 1;
    public static final int PORTRAIT_CONTROLLER_ERROR_CODE_INVALID = 2;
    public static final int PORTRAIT_CONTROLLER_ERROR_CODE_INVALID_SIZE = 3;
    public static final int PORTRAIT_CONTROLLER_ERROR_CODE_NONE = 0;
    public static final int PORTRAIT_CONTROLLER_ERROR_CODE_NO_FACE = 11;
    public static final int PORTRAIT_CONTROLLER_ERROR_CODE_TOO_CLOSE = 12;
    public static final int PORTRAIT_CONTROLLER_ERROR_CODE_TOO_FAR = 13;
    public static final int PORTRAIT_CONTROLLER_LOAD_ARCSOFT_LIB = 12;
    public static final int PORTRAIT_CONTROLLER_PARAM_COMMON = 0;
    public static final int PORTRAIT_CONTROLLER_PARAM_PREVIEW = 1;
    public static final int PORTRAIT_CONTROLLER_PARAM_SAVE = 2;
    public static final int PORTRAIT_CONTROLLER_PROCESS_PREVIEW = 1;
    public static final int PORTRAIT_CONTROLLER_PROCESS_SAVE = 2;
    public static final int PORTRAIT_CONTROLLER_RELIGHTING_PARAM = 2002;
    public static final int PORTRAIT_CONTROLLER_SINGLEBOKEH = 1;
    public static final int PORTRAIT_CONTROLLER_SINGLE_INIT_PARAM = 1000;
    public static final int PORTRAIT_CONTROLLER_SINGLE_OUTPUT_IMAGE_ADDRESS = 1002;
    public static final int PORTRAIT_CONTROLLER_SINGLE_PREVIEW_IMAGE_ADDRESS = 1001;
    public static final int PORTRAIT_CONTROLLER_SINGLE_RELIGHTING_MASK = 1003;
    public static final int PORTRAIT_PROPERTY_EFFECT_TYPE_BIG_BOKEH = 10;
    public static final int PORTRAIT_PROPERTY_EFFECT_TYPE_BOKEH_LENS = 0;
    public static final int PORTRAIT_PROPERTY_EFFECT_TYPE_BOKEH_SPIN = 1;
    public static final int PORTRAIT_PROPERTY_EFFECT_TYPE_BOKEH_ZOOM = 2;
    public static final int PORTRAIT_PROPERTY_EFFECT_TYPE_COLOR_PICKER = 20;
    public static final int PORTRAIT_PROPERTY_EFFECT_TYPE_COLOR_POP = 6;
    public static final int PORTRAIT_PROPERTY_EFFECT_TYPE_HIGHLOW_KEY = 21;
    public static final int PORTRAIT_PROPERTY_EFFECT_TYPE_HIGH_KEY = 22;
    public static final int PORTRAIT_PROPERTY_EFFECT_TYPE_LOW_KEY = 23;
    public static final int PORTRAIT_PROPERTY_EFFECT_TYPE_MASK = 100;
    public static final int PORTRAIT_PROPERTY_EFFECT_TYPE_MONO_TONE = 5;
    public static final int PORTRAIT_PROPERTY_EFFECT_TYPE_STAGE_LIGHT = 4;
    public static final int PORTRAIT_PROPERTY_EFFECT_TYPE_STUDIO_LIGHT = 11;
    public static final int PORTRAIT_PROPERTY_EFFECT_TYPE_VINTAGE_LIGHT = 3;

    @Retention(RetentionPolicy.SOURCE)
    public @interface getPortraitDataParameterIndex {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface returnCode {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface setPortraitDataParameterIndex {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface setPortraitType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface setProcessMode {}

    public static int create(int type) {
        return PortraitController.create(type);
    }

    public static int initialize() {
        return PortraitController.initialize();
    }

    public static int setParam(int index, PortraitData portraitData) {
        return PortraitController.setParam(index, portraitData);
    }

    public static int getParam(int index, PortraitData portraitData) {
        return PortraitController.getParam(index, portraitData);
    }

    public static int process(int mode) {
        return PortraitController.process(mode);
    }

    public static int deinitialize() {
        return PortraitController.deInitialize();
    }

    public static int destroy() {
        return PortraitController.destroy();
    }

    public static int addPortraitPreprocess() {
        return PortraitController.addPortraitPreprocess();
    }

    public static String getVersion() {
        return PortraitController.getVersion();
    }
}
