package com.android.systemui.dagger;

import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.dagger.SysUIComponent;
import com.android.systemui.doze.AODIntentService;
import com.android.systemui.keyguard.CustomizationProvider;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.people.PeopleProvider;
import com.android.systemui.plank.protocol.TestProtocolProvider;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface ReferenceSysUIComponent extends SysUIComponent {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
