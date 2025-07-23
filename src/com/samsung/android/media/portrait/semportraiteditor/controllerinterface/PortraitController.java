package com.samsung.android.media.portrait.semportraiteditor.controllerinterface;

/* loaded from: classes6.dex */
public class PortraitController {
    public static native int addPortraitPreprocess();

    public static native int create(int i);

    public static native int deInitialize();

    public static native int destroy();

    public static native int getParam(int i, PortraitData portraitData);

    public static native String getVersion();

    public static native int initialize();

    public static native int process(int i);

    public static native int setParam(int i, PortraitData portraitData);

    static {
        try {
            System.loadLibrary("portrait_controller_interface_jni.samsung");
        } catch (Exception | UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
        try {
            System.loadLibrary("portrait_controller_engine.samsung");
        } catch (Exception unused) {
        } catch (UnsatisfiedLinkError e2) {
            e2.printStackTrace();
        }
        try {
            System.loadLibrary("mpbase");
        } catch (Exception | UnsatisfiedLinkError e3) {
            e3.printStackTrace();
        }
        try {
            System.loadLibrary("arcsoft_photoeditor");
        } catch (Exception unused2) {
        } catch (UnsatisfiedLinkError e4) {
            e4.printStackTrace();
        }
    }
}
