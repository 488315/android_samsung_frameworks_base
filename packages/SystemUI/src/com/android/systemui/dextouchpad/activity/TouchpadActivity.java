package com.android.systemui.dextouchpad.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.android.systemui.R;
import com.android.systemui.dextouchpad.util.Features;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class TouchpadActivity extends AppCompatActivity {
    public static WeakReference mActivity;
    public DisplayManager mDisplayManager;
    public final AnonymousClass1 mFinishActivityReceiver = new BroadcastReceiver() { // from class: com.android.systemui.dextouchpad.activity.TouchpadActivity.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("com.samsung.android.desktopmode.action.FINISH_DEX_TOUCHPAD_ACTIVITY".equals(intent.getAction())) {
                Log.d("DexTouchpadActivity", "onReceive finish touchpad");
                TouchpadActivity touchpadActivity = TouchpadActivity.this;
                touchpadActivity.getClass();
                if (Features.DEBUG) {
                    Log.d("DexTouchpadActivity", "finishActivity()");
                }
                touchpadActivity.getApplicationContext().unregisterReceiver(touchpadActivity.mFinishActivityReceiver);
                TouchpadActivity.mActivity = null;
                touchpadActivity.finish();
            }
        }
    };

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
        getApplicationContext().registerReceiver(this.mFinishActivityReceiver, new IntentFilter("com.samsung.android.desktopmode.action.FINISH_DEX_TOUCHPAD_ACTIVITY"), null, null, 4);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        Fragment fragmentFindFragmentById = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragmentFindFragmentById instanceof TouchpadFragment) {
            TouchpadFragment touchpadFragment = (TouchpadFragment) fragmentFindFragmentById;
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
