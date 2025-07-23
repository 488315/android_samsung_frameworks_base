package android.app;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.R;

/* loaded from: classes.dex */
public class BootProgressDialog extends ProgressDialog {
    public BootProgressDialog(Context context) {
        super(context, 16973834);
    }

    @Override // android.app.ProgressDialog, android.app.AlertDialog, android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        View decorView = getWindow().getDecorView();
        View findViewById = decorView.findViewById(R.id.progress_animation);
        if (findViewById instanceof ImageView) {
            findViewById.setBackgroundResource(R.drawable.boot_progress_animation);
            ((AnimationDrawable) findViewById.getBackground()).start();
        }
        TextView textView = (TextView) decorView.findViewById(R.id.text_optimizing_apps);
        if (textView != null) {
            textView.lambda$setTextAsync$0(this.mContext.getString(R.string.boot_progress_dialog_optimizing_apps_ing));
        }
        View findViewById2 = decorView.findViewById(R.id.body);
        if (findViewById2 != null) {
            clearMarginAndSetMatchParentAncestor(findViewById2);
        }
        WindowInsetsController windowInsetsController = decorView.getWindowInsetsController();
        if (windowInsetsController != null) {
            windowInsetsController.hide(WindowInsets.Type.systemBars());
            windowInsetsController.setSystemBarsBehavior(2);
        }
    }

    private void clearMarginAndSetMatchParentAncestor(View view) {
        clearMarginAndSetMatchParent(view);
        if (view.getParent() instanceof View) {
            clearMarginAndSetMatchParentAncestor((View) view.getParent());
        }
    }

    private void clearMarginAndSetMatchParent(View view) {
        if (view == null) {
            return;
        }
        view.setPadding(0, 0, 0, 0);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = -1;
            layoutParams.height = -1;
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(0, 0, 0, 0);
            }
            view.setLayoutParams(layoutParams);
        }
    }
}
