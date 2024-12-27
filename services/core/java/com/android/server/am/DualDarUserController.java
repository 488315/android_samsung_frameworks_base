package com.android.server.am;


public final class DualDarUserController {
    public static volatile DualDarUserController mInstance;
    public final UserController.Injector mInjector;

    public DualDarUserController(UserController.Injector injector) {
        this.mInjector = injector;
    }

    public static DualDarUserController getInstance(UserController.Injector injector) {
        if (mInstance == null) {
            synchronized (DualDarUserController.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new DualDarUserController(injector);
                    }
                } finally {
                }
            }
        }
        return mInstance;
    }
}
