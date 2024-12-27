package com.android.server.autofill.ui;

import android.text.TextUtils;
import android.widget.Toast;

import com.android.server.autofill.ui.FillUi.ItemsAdapter.AnonymousClass1;

import java.util.Objects;

public final /* synthetic */ class AutoFillUI$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ AutoFillUI f$0;
    public final /* synthetic */ AutoFillUI.AutoFillUiCallback f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ AutoFillUI$$ExternalSyntheticLambda8(
            AutoFillUI autoFillUI,
            AutoFillUI.AutoFillUiCallback autoFillUiCallback,
            CharSequence charSequence) {
        this.f$0 = autoFillUI;
        this.f$1 = autoFillUiCallback;
        this.f$2 = charSequence;
    }

    public /* synthetic */ AutoFillUI$$ExternalSyntheticLambda8(
            AutoFillUI autoFillUI, AutoFillUI.AutoFillUiCallback autoFillUiCallback, String str) {
        this.f$0 = autoFillUI;
        this.f$1 = autoFillUiCallback;
        this.f$2 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FillUi fillUi;
        switch (this.$r8$classId) {
            case 0:
                AutoFillUI autoFillUI = this.f$0;
                AutoFillUI.AutoFillUiCallback autoFillUiCallback = this.f$1;
                String str = (String) this.f$2;
                if (autoFillUiCallback == autoFillUI.mCallback
                        && (fillUi = autoFillUI.mFillUi) != null) {
                    if (fillUi.mDestroyed) {
                        throw new IllegalStateException(
                                "cannot interact with a destroyed instance");
                    }
                    FillUi.ItemsAdapter itemsAdapter = fillUi.mAdapter;
                    if (itemsAdapter == null) {
                        if (TextUtils.isEmpty(str)) {
                            fillUi.requestShowFillUi();
                            return;
                        } else {
                            fillUi.mCallback.requestHideFillUi();
                            return;
                        }
                    }
                    String lowerCase = str == null ? null : str.toLowerCase();
                    if (Objects.equals(fillUi.mFilterText, lowerCase)) {
                        return;
                    }
                    fillUi.mFilterText = lowerCase;
                    itemsAdapter.new AnonymousClass1()
                            .filter(
                                    fillUi.mFilterText,
                                    new FillUi$$ExternalSyntheticLambda0(
                                            fillUi, itemsAdapter.getCount()));
                    return;
                }
                return;
            default:
                AutoFillUI autoFillUI2 = this.f$0;
                AutoFillUI.AutoFillUiCallback autoFillUiCallback2 = this.f$1;
                CharSequence charSequence = (CharSequence) this.f$2;
                if (autoFillUI2.mCallback != autoFillUiCallback2) {
                    return;
                }
                autoFillUI2.hideAllUiThread(autoFillUiCallback2);
                if (TextUtils.isEmpty(charSequence)) {
                    return;
                }
                Toast.makeText(autoFillUI2.mContext, charSequence, 1).show();
                return;
        }
    }
}
