package com.android.internal.globalactions;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ListView;
import com.android.internal.app.AlertController;

/* loaded from: classes5.dex */
public final class ActionsDialog extends Dialog implements DialogInterface {
    private final ActionsAdapter mAdapter;
    private final AlertController mAlert;
    private final Context mContext;

    public ActionsDialog(Context context, AlertController.AlertParams alertParams) {
        super(context, getDialogTheme(context));
        Context context2 = getContext();
        this.mContext = context2;
        AlertController create = AlertController.create(context2, this, getWindow());
        this.mAlert = create;
        this.mAdapter = (ActionsAdapter) alertParams.mAdapter;
        alertParams.apply(create);
    }

    private static int getDialogTheme(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843529, typedValue, true);
        return typedValue.resourceId;
    }

    @Override // android.app.Dialog
    protected void onStart() {
        super.setCanceledOnTouchOutside(true);
        super.onStart();
    }

    public ListView getListView() {
        return this.mAlert.getListView();
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mAlert.installContent();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 32) {
            for (int i = 0; i < this.mAdapter.getCount(); i++) {
                CharSequence labelForAccessibility = this.mAdapter.getItem(i).getLabelForAccessibility(getContext());
                if (labelForAccessibility != null) {
                    accessibilityEvent.getText().add(labelForAccessibility);
                }
            }
        }
        return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this.mAlert.onKeyDown(i, keyEvent)) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (this.mAlert.onKeyUp(i, keyEvent)) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }
}
