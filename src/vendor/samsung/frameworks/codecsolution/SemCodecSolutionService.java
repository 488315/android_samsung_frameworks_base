package vendor.samsung.frameworks.codecsolution;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IBinder;
import android.os.ServiceManager;
import android.util.Log;
import dalvik.system.PathClassLoader;

/* loaded from: classes6.dex */
public class SemCodecSolutionService {
    private static final String TAG = "SemCodecSolutionService";
    private Class mClass;
    private Object mService;

    public SemCodecSolutionService() {
        this.mClass = null;
        this.mService = null;
        try {
            PathClassLoader pathClassLoader = new PathClassLoader("/system/framework/vendor.samsung.frameworks.codecsolution-service.jar", getClass().getClassLoader());
            Class loadClass = pathClassLoader.loadClass("vendor.samsung.frameworks.codecsolution.ISehCodecSolution");
            Class loadClass2 = pathClassLoader.loadClass("vendor.samsung.frameworks.codecsolution.ISehCodecSolution$Stub");
            Object invoke = loadClass2.getDeclaredMethod("asInterface", IBinder.class).invoke(loadClass2, ServiceManager.getService("vendor.samsung.frameworks.codecsolution.ISehCodecSolution/default"));
            this.mClass = loadClass;
            this.mService = invoke;
        } catch (Exception e) {
            Log.e(TAG, "Can't load ISehCodecSolution class.");
            e.printStackTrace();
        }
    }

    public void setSmartFittingMode(int i) {
        Log.d(TAG, "setSmartFittingMode(" + i + NavigationBarInflaterView.KEY_CODE_END);
        Class cls = this.mClass;
        if (cls == null || this.mService == null) {
            return;
        }
        try {
            cls.getDeclaredMethod("setSmartFittingMode", Integer.TYPE).invoke(this.mService, Integer.valueOf(i));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAutoFitMode(boolean z) {
        Log.d(TAG, "setAutoFitMode(" + z + NavigationBarInflaterView.KEY_CODE_END);
        Class cls = this.mClass;
        if (cls == null || this.mService == null) {
            return;
        }
        try {
            cls.getDeclaredMethod("setAutoFitMode", Boolean.TYPE).invoke(this.mService, Boolean.valueOf(z));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSecVideoUseSmartFitting(int i) {
        Log.d(TAG, "setSecVideoUseSmartFitting(" + i + NavigationBarInflaterView.KEY_CODE_END);
        if (i != 0) {
            setAutoFitMode(true);
        } else {
            setAutoFitMode(false);
        }
    }
}
