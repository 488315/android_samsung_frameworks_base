package com.android.p038wm.shell.windowdecor;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.android.systemui.R;
import com.samsung.android.multiwindow.MultiWindowManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DexCompatRestartActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mTargetTaskId;

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        this.mTargetTaskId = getIntent().getIntExtra("compat_task_id", -1);
        String string = getResources().getString(R.string.decor_caption_compatibility_dialog_title);
        String string2 = getResources().getString(R.string.decor_caption_compatibility_dialog_button_pos);
        String string3 = getResources().getString(android.R.string.cancel);
        View inflate = LayoutInflater.from(this).inflate(R.layout.sem_decor_caption_dialog_layout, (ViewGroup) null);
        ((TextView) inflate.findViewById(R.id.message)).setText(getResources().getString(R.string.decor_caption_compatibility_dialog_message));
        final CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.option_show_restart_popup);
        checkBox.setText(getResources().getString(R.string.decor_caption_compatibility_dialog_option));
        Button button = (Button) inflate.findViewById(R.id.customButton1);
        button.setText(string2);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.DexCompatRestartActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DexCompatRestartActivity dexCompatRestartActivity = DexCompatRestartActivity.this;
                CheckBox checkBox2 = checkBox;
                int i = DexCompatRestartActivity.$r8$clinit;
                dexCompatRestartActivity.getClass();
                if (checkBox2.isChecked()) {
                    Settings.System.putInt(dexCompatRestartActivity.getContentResolver(), "disable_dexcompat_restart_dialog", 1);
                }
                MultiWindowManager.getInstance().toggleFreeformForDexCompatApp(dexCompatRestartActivity.mTargetTaskId);
            }
        });
        Button button2 = (Button) inflate.findViewById(R.id.customButton2);
        button2.setText(string3);
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.DexCompatRestartActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DexCompatRestartActivity dexCompatRestartActivity = DexCompatRestartActivity.this;
                int i = DexCompatRestartActivity.$r8$clinit;
                dexCompatRestartActivity.finish();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(string).setView(inflate);
        AlertDialog create = builder.create();
        create.create();
        getWindow().setGravity(80);
        create.show();
    }
}
