package com.android.systemui.controls.ui;

import android.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
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
import com.android.systemui.BasicRune;
import com.google.android.material.textfield.TextInputLayout;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ChallengeDialogs {
    public static final ChallengeDialogs INSTANCE = new ChallengeDialogs();
    public static final int STYLE;

    static {
        STYLE = BasicRune.CONTROLS_SAMSUNG_STYLE ? 2132018373 : R.style.Theme.DeviceDefault.Dialog.Alert;
    }

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

    public static final void access$setInputType(ChallengeDialogs challengeDialogs, EditText editText, boolean z) {
        challengeDialogs.getClass();
        if (z) {
            editText.setInputType(129);
        } else {
            editText.setInputType(18);
        }
    }

    /* JADX WARN: Type inference failed for: r10v5, types: [android.app.AlertDialog, android.app.Dialog, com.android.systemui.controls.ui.ChallengeDialogs$createPinDialog$1] */
    /* JADX WARN: Type inference failed for: r11v4, types: [android.app.AlertDialog, android.app.Dialog, com.android.systemui.controls.ui.ChallengeDialogs$createCustomPinDialog$1] */
    public static Dialog createPinDialog(final ControlViewHolder controlViewHolder, final boolean z, final boolean z2, final Function0 function0) {
        boolean z3 = BasicRune.CONTROLS_SAMSUNG_STYLE;
        final int i = STYLE;
        TextView textView = controlViewHolder.title;
        final Context context = controlViewHolder.context;
        if (z3) {
            final ControlAction controlAction = controlViewHolder.lastAction;
            if (controlAction == null) {
                Log.e("ControlsUiController", "PIN Dialog attempted but no last action is set. Will not show");
                return null;
            }
            Resources resources = context.getResources();
            Pair pair = z2 ? new Pair(resources.getString(com.android.systemui.R.string.controls_custom_pin_verify, textView.getText()), Integer.valueOf(com.android.systemui.R.string.controls_custom_pin_instructions)) : new Pair(resources.getString(com.android.systemui.R.string.controls_custom_pin_verify, textView.getText()), Integer.valueOf(com.android.systemui.R.string.controls_custom_pin_instructions));
            String str = (String) pair.component1();
            final int intValue = ((Number) pair.component2()).intValue();
            final ?? r11 = new AlertDialog(context, i) { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createCustomPinDialog$1
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
            r11.setTitle(str);
            r11.setView(LayoutInflater.from(r11.getContext()).inflate(com.android.systemui.R.layout.controls_custom_dialog_pin, (ViewGroup) null));
            r11.setButton(-1, r11.getContext().getText(com.android.systemui.R.string.controls_custom_dialog_ok), new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createCustomPinDialog$2$1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    if (dialogInterface instanceof Dialog) {
                        EditText editText = ((TextInputLayout) ((Dialog) dialogInterface).requireViewById(com.android.systemui.R.id.controls_pin_input_layout)).editText;
                        Intrinsics.checkNotNull(editText);
                        ControlViewHolder.this.action(ChallengeDialogs.access$addChallengeValue(ChallengeDialogs.INSTANCE, controlAction, editText.getText().toString()));
                        dialogInterface.dismiss();
                    }
                }
            });
            r11.setButton(-2, r11.getContext().getText(com.android.systemui.R.string.controls_custom_dialog_cancel), new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createCustomPinDialog$2$2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    Function0.this.invoke();
                    dialogInterface.cancel();
                }
            });
            Window window = r11.getWindow();
            window.setType(2020);
            window.setSoftInputMode(4);
            r11.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createCustomPinDialog$2$4
                @Override // android.content.DialogInterface.OnShowListener
                public final void onShow(DialogInterface dialogInterface) {
                    TextInputLayout textInputLayout = (TextInputLayout) requireViewById(com.android.systemui.R.id.controls_pin_input_layout);
                    final EditText editText = textInputLayout.editText;
                    Intrinsics.checkNotNull(editText);
                    editText.setHint(intValue);
                    if (z2) {
                        textInputLayout.setError(getContext().getString(com.android.systemui.R.string.controls_pin_error_message));
                    }
                    ((TextView) requireViewById(com.android.systemui.R.id.controls_pin_use_alpha_text)).setText(com.android.systemui.R.string.controls_custom_pin_use_alphanumeric);
                    int color = getContext().getResources().getColor(com.android.systemui.R.color.basic_interaction_dialog_button, getContext().getTheme());
                    ChallengeDialogs$createCustomPinDialog$1 challengeDialogs$createCustomPinDialog$1 = ChallengeDialogs$createCustomPinDialog$1.this;
                    challengeDialogs$createCustomPinDialog$1.getButton(-1).setTextColor(color);
                    challengeDialogs$createCustomPinDialog$1.getButton(-2).setTextColor(color);
                    final CheckBox checkBox = (CheckBox) requireViewById(com.android.systemui.R.id.controls_pin_use_alpha);
                    checkBox.setChecked(z);
                    ChallengeDialogs.access$setInputType(ChallengeDialogs.INSTANCE, editText, checkBox.isChecked());
                    ((CheckBox) requireViewById(com.android.systemui.R.id.controls_pin_use_alpha)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createCustomPinDialog$2$4.2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            ChallengeDialogs.access$setInputType(ChallengeDialogs.INSTANCE, editText, checkBox.isChecked());
                        }
                    });
                    editText.requestFocus();
                }
            });
            return r11;
        }
        final ControlAction controlAction2 = controlViewHolder.lastAction;
        if (controlAction2 == null) {
            Log.e("ControlsUiController", "PIN Dialog attempted but no last action is set. Will not show");
            return null;
        }
        Resources resources2 = context.getResources();
        Pair pair2 = z2 ? new Pair(resources2.getString(com.android.systemui.R.string.controls_pin_wrong), Integer.valueOf(com.android.systemui.R.string.controls_pin_instructions_retry)) : new Pair(resources2.getString(com.android.systemui.R.string.controls_pin_verify, textView.getText()), Integer.valueOf(com.android.systemui.R.string.controls_pin_instructions));
        String str2 = (String) pair2.component1();
        final int intValue2 = ((Number) pair2.component2()).intValue();
        final ?? r10 = new AlertDialog(context, i) { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createPinDialog$1
            @Override // android.app.Dialog, android.content.DialogInterface
            public final void dismiss() {
                View decorView;
                InputMethodManager inputMethodManager;
                Window window2 = getWindow();
                if (window2 != null && (decorView = window2.getDecorView()) != null && (inputMethodManager = (InputMethodManager) decorView.getContext().getSystemService(InputMethodManager.class)) != null) {
                    inputMethodManager.hideSoftInputFromWindow(decorView.getWindowToken(), 0);
                }
                super.dismiss();
            }
        };
        r10.setTitle(str2);
        r10.setView(LayoutInflater.from(r10.getContext()).inflate(com.android.systemui.R.layout.controls_dialog_pin, (ViewGroup) null));
        r10.setButton(-1, r10.getContext().getText(R.string.ok), new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createPinDialog$2$1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                if (dialogInterface instanceof Dialog) {
                    Dialog dialog = (Dialog) dialogInterface;
                    dialog.requireViewById(com.android.systemui.R.id.controls_pin_input);
                    ControlViewHolder.this.action(ChallengeDialogs.access$addChallengeValue(ChallengeDialogs.INSTANCE, controlAction2, ((EditText) dialog.requireViewById(com.android.systemui.R.id.controls_pin_input)).getText().toString()));
                    dialogInterface.dismiss();
                }
            }
        });
        r10.setButton(-2, r10.getContext().getText(R.string.cancel), new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createPinDialog$2$2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                Function0.this.invoke();
                dialogInterface.cancel();
            }
        });
        Window window2 = r10.getWindow();
        window2.setType(2020);
        window2.setSoftInputMode(4);
        r10.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createPinDialog$2$4
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                final EditText editText = (EditText) requireViewById(com.android.systemui.R.id.controls_pin_input);
                editText.setHint(intValue2);
                final CheckBox checkBox = (CheckBox) requireViewById(com.android.systemui.R.id.controls_pin_use_alpha);
                checkBox.setChecked(z);
                ChallengeDialogs.access$setInputType(ChallengeDialogs.INSTANCE, editText, checkBox.isChecked());
                ((CheckBox) requireViewById(com.android.systemui.R.id.controls_pin_use_alpha)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.ChallengeDialogs$createPinDialog$2$4.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ChallengeDialogs.access$setInputType(ChallengeDialogs.INSTANCE, editText, checkBox.isChecked());
                    }
                });
                editText.requestFocus();
            }
        });
        return r10;
    }
}
