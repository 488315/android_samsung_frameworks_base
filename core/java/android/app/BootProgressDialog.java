package android.app;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.p009os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.C4337R;

/* loaded from: classes.dex */
public class BootProgressDialog extends ProgressDialog {
    public BootProgressDialog(Context context) {
        super(context, 16973834);
    }

    @Override // android.app.ProgressDialog, android.app.AlertDialog, android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decor = getWindow().getDecorView();
        View v = decor.findViewById(C4337R.id.progress_animation);
        if (v instanceof ImageView) {
            v.setBackgroundResource(C4337R.drawable.boot_progress_animation);
            AnimationDrawable bootProgressAnimation = (AnimationDrawable) v.getBackground();
            bootProgressAnimation.start();
        }
        TextView t = (TextView) decor.findViewById(C4337R.id.text_optimizing_apps);
        if (t != null) {
            t.setText(this.mContext.getString(C4337R.string.boot_progress_dialog_optimizing_apps_ing));
        }
        View v2 = decor.findViewById(C4337R.id.body);
        if (v2 != null) {
            clearMarginAndSetMatchParentAncestor(v2);
        }
        WindowInsetsController insetsController = decor.getWindowInsetsController();
        if (insetsController != null) {
            insetsController.hide(WindowInsets.Type.systemBars());
            insetsController.setSystemBarsBehavior(2);
        }
    }

    private void clearMarginAndSetMatchParentAncestor(View v) {
        clearMarginAndSetMatchParent(v);
        if (v.getParent() instanceof View) {
            clearMarginAndSetMatchParentAncestor((View) v.getParent());
        }
    }

    private void clearMarginAndSetMatchParent(View v) {
        if (v == null) {
            return;
        }
        v.setPadding(0, 0, 0, 0);
        ViewGroup.LayoutParams params = v.getLayoutParams();
        if (params != null) {
            params.width = -1;
            params.height = -1;
            if (params instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) params).setMargins(0, 0, 0, 0);
            }
            v.setLayoutParams(params);
        }
    }
}
