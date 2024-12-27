package com.android.systemui.dagger;

import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.dagger.SysUIComponent;
import com.android.systemui.doze.AODIntentService;
import com.android.systemui.keyguard.CustomizationProvider;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.people.PeopleProvider;
import com.android.systemui.plank.protocol.TestProtocolProvider;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface ReferenceSysUIComponent extends SysUIComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Builder extends SysUIComponent.Builder {
    }

    @Override // com.android.systemui.dagger.SysUIComponent
    /* synthetic */ void inject(SystemUIAppComponentFactoryBase systemUIAppComponentFactoryBase);

    @Override // com.android.systemui.dagger.SysUIComponent
    /* synthetic */ void inject(AODIntentService aODIntentService);

    void inject(CustomizationProvider customizationProvider);

    @Override // com.android.systemui.dagger.SysUIComponent
    /* synthetic */ void inject(KeyguardSliceProvider keyguardSliceProvider);

    @Override // com.android.systemui.dagger.SysUIComponent
    /* synthetic */ void inject(PeopleProvider peopleProvider);

    @Override // com.android.systemui.dagger.SysUIComponent
    /* synthetic */ void inject(TestProtocolProvider testProtocolProvider);

    @Override // com.android.systemui.dagger.SysUIComponent
    /* synthetic */ void inject(KeyguardSecAffordanceView keyguardSecAffordanceView);
}
