package com.android.systemui.p016qs.customize.setting;

import android.content.SharedPreferences;
import com.sec.ims.configuration.DATA;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final /* synthetic */ class SecQSSettingEditResources$init$1 extends FunctionReferenceImpl implements Function2 {
    public SecQSSettingEditResources$init$1(Object obj) {
        super(2, obj, SecQSSettingEditResources.class, "updateSALog", "updateSALog(Ljava/lang/String;Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        String str = (String) obj;
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        SecQSSettingEditResources secQSSettingEditResources = (SecQSSettingEditResources) this.receiver;
        secQSSettingEditResources.getClass();
        String str2 = booleanValue ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
        SharedPreferences.Editor editor = secQSSettingEditResources.editor;
        editor.putString(str, str2);
        editor.apply();
        return Unit.INSTANCE;
    }
}
