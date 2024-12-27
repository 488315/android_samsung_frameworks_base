package com.android.server.am;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
