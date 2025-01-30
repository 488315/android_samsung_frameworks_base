package com.android.systemui.keyguard.bouncer.data.repository;

import com.android.systemui.keyguard.shared.model.AuthenticationFlags;
import kotlin.Triple;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public /* synthetic */ class BouncerMessageRepositoryImpl$authFlagsBasedPromptReason$2 extends AdaptedFunctionReference implements Function4 {
    public static final BouncerMessageRepositoryImpl$authFlagsBasedPromptReason$2 INSTANCE = new BouncerMessageRepositoryImpl$authFlagsBasedPromptReason$2();

    public BouncerMessageRepositoryImpl$authFlagsBasedPromptReason$2() {
        super(4, Triple.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        int i = BouncerMessageRepositoryImpl.$r8$clinit;
        return new Triple((AuthenticationFlags) obj, Boolean.valueOf(booleanValue), Boolean.valueOf(booleanValue2));
    }
}
