package androidx.activity;

import android.view.inputmethod.InputMethodManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.lang.reflect.Field;

/* loaded from: classes.dex */
final class ImmLeaksCleaner implements LifecycleEventObserver {
    private static Field sHField;
    private static Field sNextServedViewField;
    private static int sReflectedFieldsInitialized;
    private static Field sServedViewField;

    ImmLeaksCleaner() {
        throw null;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        if (event != Lifecycle.Event.ON_DESTROY) {
            return;
        }
        if (sReflectedFieldsInitialized == 0) {
            try {
                sReflectedFieldsInitialized = 2;
                Field declaredField = InputMethodManager.class.getDeclaredField("mServedView");
                sServedViewField = declaredField;
                declaredField.setAccessible(true);
                Field declaredField2 = InputMethodManager.class.getDeclaredField("mNextServedView");
                sNextServedViewField = declaredField2;
                declaredField2.setAccessible(true);
                Field declaredField3 = InputMethodManager.class.getDeclaredField("mH");
                sHField = declaredField3;
                declaredField3.setAccessible(true);
                sReflectedFieldsInitialized = 1;
            } catch (NoSuchFieldException unused) {
            }
        }
        if (sReflectedFieldsInitialized == 1) {
            throw null;
        }
    }
}
