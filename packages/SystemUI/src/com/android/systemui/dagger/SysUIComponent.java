package com.android.systemui.dagger;

import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.BootAnimationFinishedTrigger;
import com.android.systemui.BootCompleteCacheImpl;
import com.android.systemui.InitController;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.doze.AODIntentService;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.people.PeopleProvider;
import com.android.systemui.plank.protocol.TestProtocolProvider;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.phone.ConfigurationForwarder;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface SysUIComponent {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Builder {
    }

    DumpManager createDumpManager();

    ConfigurationForwarder getConfigurationForwarder();

    InitController getInitController();

    Map getPerUserStartables();

    Map getPostStartables();

    Map getPreStartables();

    Map getSafeUIStartables();

    Map getStartableDependencies();

    Map getStartables();

    void inject(SystemUIAppComponentFactoryBase systemUIAppComponentFactoryBase);

    void inject(AODIntentService aODIntentService);

    void inject(KeyguardSliceProvider keyguardSliceProvider);

    void inject(PeopleProvider peopleProvider);

    void inject(TestProtocolProvider testProtocolProvider);

    void inject(KeyguardSecAffordanceView keyguardSecAffordanceView);

    BootAnimationFinishedCacheImpl provideBootAnimationFinishedImpl();

    BootAnimationFinishedTrigger provideBootAnimationFinishedTrigger();

    BootCompleteCacheImpl provideBootCacheImpl();
}
