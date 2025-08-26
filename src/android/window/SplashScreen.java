package android.window;

import android.app.Activity;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.Singleton;
import android.util.Slog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public interface SplashScreen {
    public static final int SPLASH_SCREEN_STYLE_ICON = 1;
    public static final int SPLASH_SCREEN_STYLE_SOLID_COLOR = 0;
    public static final int SPLASH_SCREEN_STYLE_UNDEFINED = -1;

    public interface OnExitAnimationListener {
        void onSplashScreenExit(SplashScreenView splashScreenView);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SplashScreenStyle {
    }

    void clearOnExitAnimationListener();

    void setOnExitAnimationListener(OnExitAnimationListener onExitAnimationListener);

    void setSplashScreenTheme(int i);

    public static class SplashScreenImpl implements SplashScreen {
        private static final String TAG = "SplashScreenImpl";
        private final IBinder mActivityToken;
        private OnExitAnimationListener mExitAnimationListener;
        private final SplashScreenManagerGlobal mGlobal = SplashScreenManagerGlobal.getInstance();

        public SplashScreenImpl(Context context) {
            this.mActivityToken = context.getActivityToken();
        }

        @Override // android.window.SplashScreen
        public void setOnExitAnimationListener(OnExitAnimationListener onExitAnimationListener) {
            if (this.mActivityToken == null) {
                return;
            }
            synchronized (this.mGlobal.mGlobalLock) {
                if (onExitAnimationListener != null) {
                    this.mExitAnimationListener = onExitAnimationListener;
                    this.mGlobal.addImpl(this);
                }
            }
        }

        @Override // android.window.SplashScreen
        public void clearOnExitAnimationListener() {
            if (this.mActivityToken == null) {
                return;
            }
            synchronized (this.mGlobal.mGlobalLock) {
                this.mExitAnimationListener = null;
                this.mGlobal.removeImpl(this);
            }
        }

        @Override // android.window.SplashScreen
        public void setSplashScreenTheme(int i) {
            if (this.mActivityToken == null) {
                Log.w(TAG, "Couldn't persist the starting theme. This instance is not an Activity");
                return;
            }
            Activity activity = ActivityThread.currentActivityThread().getActivity(this.mActivityToken);
            if (activity == null) {
                return;
            }
            try {
                AppGlobals.getPackageManager().setSplashScreenTheme(activity.getComponentName().getPackageName(), i != 0 ? activity.getResources().getResourceName(i) : null, activity.getUserId());
            } catch (RemoteException e) {
                Log.w(TAG, "Couldn't persist the starting theme", e);
            }
        }
    }

    public static class SplashScreenManagerGlobal {
        private static final String TAG = "SplashScreen";
        private static final Singleton<SplashScreenManagerGlobal> sInstance = new Singleton<SplashScreenManagerGlobal>() { // from class: android.window.SplashScreen.SplashScreenManagerGlobal.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.util.Singleton
            public SplashScreenManagerGlobal create() {
                return new SplashScreenManagerGlobal();
            }
        };
        private final Object mGlobalLock;
        private final ArrayList<SplashScreenImpl> mImpls;

        private SplashScreenManagerGlobal() {
            this.mGlobalLock = new Object();
            this.mImpls = new ArrayList<>();
            ActivityThread.currentActivityThread().registerSplashScreenManager(this);
        }

        public static SplashScreenManagerGlobal getInstance() {
            return sInstance.get();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addImpl(SplashScreenImpl splashScreenImpl) {
            synchronized (this.mGlobalLock) {
                this.mImpls.add(splashScreenImpl);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeImpl(SplashScreenImpl splashScreenImpl) {
            synchronized (this.mGlobalLock) {
                this.mImpls.remove(splashScreenImpl);
            }
        }

        private SplashScreenImpl findImpl(IBinder iBinder) {
            synchronized (this.mGlobalLock) {
                Iterator<SplashScreenImpl> it = this.mImpls.iterator();
                while (it.hasNext()) {
                    SplashScreenImpl next = it.next();
                    if (next.mActivityToken == iBinder) {
                        return next;
                    }
                }
                return null;
            }
        }

        public void tokenDestroyed(IBinder iBinder) {
            synchronized (this.mGlobalLock) {
                SplashScreenImpl splashScreenImplFindImpl = findImpl(iBinder);
                if (splashScreenImplFindImpl != null) {
                    removeImpl(splashScreenImplFindImpl);
                }
            }
        }

        public void handOverSplashScreenView(IBinder iBinder, SplashScreenView splashScreenView) {
            dispatchOnExitAnimation(iBinder, splashScreenView);
        }

        private void dispatchOnExitAnimation(IBinder iBinder, SplashScreenView splashScreenView) {
            synchronized (this.mGlobalLock) {
                SplashScreenImpl splashScreenImplFindImpl = findImpl(iBinder);
                if (splashScreenImplFindImpl == null) {
                    return;
                }
                if (splashScreenImplFindImpl.mExitAnimationListener == null) {
                    Slog.e(TAG, "cannot dispatch onExitAnimation to listener " + iBinder);
                    return;
                }
                splashScreenImplFindImpl.mExitAnimationListener.onSplashScreenExit(splashScreenView);
            }
        }

        public boolean containsExitListener(IBinder iBinder) {
            boolean z;
            synchronized (this.mGlobalLock) {
                SplashScreenImpl splashScreenImplFindImpl = findImpl(iBinder);
                z = (splashScreenImplFindImpl == null || splashScreenImplFindImpl.mExitAnimationListener == null) ? false : true;
            }
            return z;
        }
    }
}
