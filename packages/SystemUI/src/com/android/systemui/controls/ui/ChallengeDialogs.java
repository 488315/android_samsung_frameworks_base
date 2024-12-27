package com.android.systemui.controls.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.service.controls.actions.BooleanAction;
import android.service.controls.actions.CommandAction;
import android.service.controls.actions.ControlAction;
import android.service.controls.actions.FloatAction;
import android.service.controls.actions.ModeAction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.android.systemui.R;
import com.google.android.material.textfield.TextInputLayout;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

public final class ChallengeDialogs {
    public static final ChallengeDialogs INSTANCE = new ChallengeDialogs();
    public static final int STYLE = 2132018525;

    private ChallengeDialogs() {
    }

    public static final ControlAction access$addChallengeValue(ChallengeDialogs challengeDialogs, ControlAction controlAction, String str) {
        challengeDialogs.getClass();
        String templateId = controlAction.getTemplateId();
        if (controlAction instanceof BooleanAction) {
            return new BooleanAction(templateId, ((BooleanAction) controlAction).getNewState(), str);
        }
        if (controlAction instanceof FloatAction) {
            return new FloatAction(templateId, ((FloatAction) controlAction).getNewValue(), str);
        }
        if (controlAction instanceof CommandAction) {
            return new CommandAction(templateId, str);
        }
        if (controlAction instanceof ModeAction) {
            return new ModeAction(templateId, ((ModeAction) controlAction).getNewMode(), str);
        }
        throw new IllegalStateException("'action' is not a known type: " + controlAction);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [android.app.AlertDialog, com.android.systemui.controls.ui.ChallengeDialogs$createSecPinDialog$1] */
    public static ChallengeDialogs$createSecPinDialog$1 createPinDialog(final ControlViewHolder controlViewHolder, final boolean z, final boolean z2, final Function0 function0) {
        final ControlAction controlAction = controlViewHolder.lastAction;
        if (controlAction == null) {
            Log.e("ControlsUiController", "PIN Dialog attempted but no last action is set. Will not show");
            return null;
        }
        final Context context = controlViewHolder.context;
        final int i = STYLE;
        final ?? r3 = new AlertDialog(context, i) { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createSecPinDialog$1
            @Override // android.app.Dialog, android.content.DialogInterface
            public final void dismiss() {
                View decorView;
                InputMethodManager inputMethodManager;
                Window window = getWindow();
                if (window != null && (decorView = window.getDecorView()) != null && (inputMethodManager = (InputMethodManager) decorView.getContext().getSystemService(InputMethodManager.class)) != null) {
                    inputMethodManager.hideSoftInputFromWindow(decorView.getWindowToken(), 0);
                }
                super.dismiss();
            }
        };
        r3.setTitle(controlViewHolder.context.getResources().getString(R.string.sec_controls_pin_verify, controlViewHolder.title.getText()));
        r3.setView(LayoutInflater.from(r3.getContext()).inflate(R.layout.sec_controls_dialog_pin, (ViewGroup) null));
        r3.setButton(-1, r3.getContext().getText(R.string.sec_controls_dialog_ok), new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createSecPinDialog$2$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                if (dialogInterface instanceof Dialog) {
                    EditText editText = ((TextInputLayout) ((Dialog) dialogInterface).requireViewById(R.id.controls_pin_input_layout)).editText;
                    Intrinsics.checkNotNull(editText);
                    ControlViewHolder.this.action(ChallengeDialogs.access$addChallengeValue(ChallengeDialogs.INSTANCE, controlAction, editText.getText().toString()));
                    dialogInterface.dismiss();
                }
            }
        });
        r3.setButton(-2, r3.getContext().getText(R.string.controls_dialog_cancel), new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createSecPinDialog$2$2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                Function0.this.invoke();
                dialogInterface.cancel();
            }
        });
        Window window = r3.getWindow();
        if (window != null) {
            window.setType(2020);
        }
        Window window2 = r3.getWindow();
        if (window2 != null) {
            window2.setSoftInputMode(4);
        }
        r3.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createSecPinDialog$2$3
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                TextInputLayout textInputLayout = (TextInputLayout) requireViewById(R.id.controls_pin_input_layout);
                final EditText editText = textInputLayout.editText;
                Intrinsics.checkNotNull(editText);
                editText.setHint(R.string.sec_controls_pin_instructions);
                if (z2) {
                    textInputLayout.setError(getContext().getString(R.string.controls_pin_error_message));
                }
                ((TextView) requireViewById(R.id.controls_pin_use_alpha_text)).setText(R.string.sec_controls_pin_use_alphanumeric);
                int color = getContext().getResources().getColor(R.color.basic_interaction_dialog_button, getContext().getTheme());
                ChallengeDialogs$createSecPinDialog$1 challengeDialogs$createSecPinDialog$1 = ChallengeDialogs$createSecPinDialog$1.this;
                challengeDialogs$createSecPinDialog$1.getButton(-1).setTextColor(color);
                challengeDialogs$createSecPinDialog$1.getButton(-2).setTextColor(color);
                final CheckBox checkBox = (CheckBox) requireViewById(R.id.controls_pin_use_alpha);
                checkBox.setChecked(z);
                ChallengeDialogs challengeDialogs = ChallengeDialogs.INSTANCE;
                boolean isChecked = checkBox.isChecked();
                challengeDialogs.getClass();
                if (isChecked) {
                    editText.setInputType(129);
                } else {
                    editText.setInputType(18);
                }
                ((CheckBox) requireViewById(R.id.controls_pin_use_alpha)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createSecPinDialog$2$3.2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ChallengeDialogs challengeDialogs2 = ChallengeDialogs.INSTANCE;
                        EditText editText2 = editText;
                        boolean isChecked2 = checkBox.isChecked();
                        challengeDialogs2.getClass();
                        if (isChecked2) {
                            editText2.setInputType(129);
                        } else {
                            editText2.setInputType(18);
                        }
                    }
                });
                editText.requestFocus();
            }
        });
        return r3;
    }
}
