package com.android.systemui.dextouchpad.activity;

import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.android.systemui.R;
import java.lang.ref.WeakReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TouchpadActivity extends AppCompatActivity {
    public static WeakReference mActivity;
    public DisplayManager mDisplayManager;

    public static TouchpadActivity getActivity() {
        WeakReference weakReference = mActivity;
        if (weakReference != null) {
            return (TouchpadActivity) weakReference.get();
        }
        return null;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_touchpad);
        mActivity = new WeakReference(this);
        this.mDisplayManager = (DisplayManager) getSystemService("display");
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        Fragment findFragmentById = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (findFragmentById instanceof TouchpadFragment) {
            TouchpadFragment touchpadFragment = (TouchpadFragment) findFragmentById;
            if (!z) {
                touchpadFragment.getClass();
                return;
            }
            Window window = touchpadFragment.mActivity.getWindow();
            if (window == null) {
                return;
            }
            window.setDecorFitsSystemWindows(false);
            WindowInsetsController insetsController = window.getInsetsController();
            if (insetsController == null) {
                return;
            }
            insetsController.hide(WindowInsets.Type.systemBars());
            insetsController.setSystemBarsBehavior(2);
        }
    }
}
