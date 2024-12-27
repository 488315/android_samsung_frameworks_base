package vendor.samsung.frameworks.codecsolution;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IBinder;
import android.os.ServiceManager;
import android.util.Log;
import dalvik.system.PathClassLoader;
import java.lang.reflect.Method;

/* loaded from: classes6.dex */
public class SemCodecSolutionService {
    private static final String TAG = "SemCodecSolutionService";
    private Class mClass;
    private Object mService;

    public SemCodecSolutionService() {
        this.mClass = null;
        this.mService = null;
        try {
            PathClassLoader loader = new PathClassLoader("/system/framework/vendor.samsung.frameworks.codecsolution-service.jar", getClass().getClassLoader());
            Class classICS = loader.loadClass("vendor.samsung.frameworks.codecsolution.ISehCodecSolution");
            Class classStub = loader.loadClass("vendor.samsung.frameworks.codecsolution.ISehCodecSolution$Stub");
            Method asInterface = classStub.getDeclaredMethod("asInterface", IBinder.class);
            IBinder service = ServiceManager.getService("vendor.samsung.frameworks.codecsolution.ISehCodecSolution/default");
            Object proxy = asInterface.invoke(classStub, service);
            this.mClass = classICS;
            this.mService = proxy;
        } catch (Exception e) {
            Log.e(TAG, "Can't load ISehCodecSolution class.");
            e.printStackTrace();
        }
    }

    public void setSmartFittingMode(int mode) {
        Log.d(TAG, "setSmartFittingMode(" + mode + NavigationBarInflaterView.KEY_CODE_END);
        if (this.mClass == null || this.mService == null) {
            return;
        }
        try {
            Method m = this.mClass.getDeclaredMethod("setSmartFittingMode", Integer.TYPE);
            m.invoke(this.mService, Integer.valueOf(mode));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAutoFitMode(boolean use) {
        Log.d(TAG, "setAutoFitMode(" + use + NavigationBarInflaterView.KEY_CODE_END);
        if (this.mClass == null || this.mService == null) {
            return;
        }
        try {
            Method m = this.mClass.getDeclaredMethod("setAutoFitMode", Boolean.TYPE);
            m.invoke(this.mService, Boolean.valueOf(use));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSecVideoUseSmartFitting(int use) {
        Log.d(TAG, "setSecVideoUseSmartFitting(" + use + NavigationBarInflaterView.KEY_CODE_END);
        if (use != 0) {
            setAutoFitMode(true);
        } else {
            setAutoFitMode(false);
        }
    }
}
