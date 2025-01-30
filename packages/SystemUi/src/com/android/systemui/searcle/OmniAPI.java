package com.android.systemui.searcle;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.android.internal.app.IVoiceInteractionManagerService;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.omni.AssistStateManager;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class OmniAPI {
    public static AssistStateManager mAssistStateManager;
    public static IVoiceInteractionManagerService mVoiceInteractionManagerService;
    public static final Intent INTENT_ACTION_ASSIST = new Intent("android.intent.action.ASSIST");
    public static final SettingsHelper mSettingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum UnexecutableType {
        AVAILABLE,
        SERVICE_UNAVAILABLE,
        GOOGLE_APP_DISABLED,
        GOOGLE_IS_NOT_DIGITAL_ASSISTANT
    }

    public static boolean isGoogleDefaultAssistant(Context context) {
        SettingsHelper settingsHelper = mSettingsHelper;
        settingsHelper.getClass();
        boolean z = BasicRune.SEARCLE;
        String stringValue = z ? settingsHelper.mItemLists.get("assistant").getStringValue() : null;
        if (!TextUtils.isEmpty(stringValue)) {
            return AssistStateManager.OMNI_PACKAGE.equals(ComponentName.unflattenFromString(stringValue).getPackageName());
        }
        settingsHelper.getClass();
        String stringValue2 = z ? settingsHelper.mItemLists.get("voice_interaction_service").getStringValue() : null;
        if (!TextUtils.isEmpty(stringValue2)) {
            return AssistStateManager.OMNI_PACKAGE.equals(ComponentName.unflattenFromString(stringValue2).getPackageName());
        }
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(INTENT_ACTION_ASSIST, 65536);
        if (resolveActivity != null) {
            return AssistStateManager.OMNI_PACKAGE.equals(resolveActivity.resolvePackageName);
        }
        return false;
    }
}
