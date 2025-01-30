package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.viewmodel.KeyguardPreviewSmartspaceViewModel;
import com.android.systemui.keyguard.shared.model.SettingsClockSize;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public /* synthetic */ class KeyguardPreviewSmartspaceViewModel$shouldHideSmartspace$2 extends AdaptedFunctionReference implements Function3 {
    public static final KeyguardPreviewSmartspaceViewModel$shouldHideSmartspace$2 INSTANCE = new KeyguardPreviewSmartspaceViewModel$shouldHideSmartspace$2();

    public KeyguardPreviewSmartspaceViewModel$shouldHideSmartspace$2() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardPreviewSmartspaceViewModel.Companion companion = KeyguardPreviewSmartspaceViewModel.Companion;
        return new Pair((SettingsClockSize) obj, (String) obj2);
    }
}
