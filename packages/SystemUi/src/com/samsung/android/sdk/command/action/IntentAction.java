package com.samsung.android.sdk.command.action;

import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class IntentAction extends CommandAction {
    public final String mIntentAction;
    public final Bundle mIntentExtras;
    public final String mTargetClass;
    public final String mTargetPackage;

    public IntentAction(String str, String str2, String str3, Bundle bundle) {
        super("intent");
        this.mTargetPackage = str;
        this.mTargetClass = str2;
        this.mIntentAction = str3;
        this.mIntentExtras = bundle;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final int getActionType() {
        return 3;
    }

    @Override // com.samsung.android.sdk.command.action.CommandAction
    public final Bundle getDataBundle() {
        Bundle dataBundle = super.getDataBundle();
        dataBundle.putString("key_target_package", this.mTargetPackage);
        dataBundle.putString("key_target_class", this.mTargetClass);
        dataBundle.putString("key_intent_action", this.mIntentAction);
        dataBundle.putBundle("key_intent_extras", this.mIntentExtras);
        return dataBundle;
    }

    public IntentAction(Bundle bundle) {
        super(bundle);
        this.mTargetPackage = bundle.getString("key_target_package");
        this.mTargetClass = bundle.getString("key_target_class");
        this.mIntentAction = bundle.getString("key_intent_action");
        this.mIntentExtras = bundle.getBundle("key_intent_extras");
    }
}
