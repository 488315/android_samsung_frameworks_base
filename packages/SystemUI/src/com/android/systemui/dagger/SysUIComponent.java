package com.android.systemui.dagger;

import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.BootAnimationFinishedTrigger;
import com.android.systemui.BootCompleteCacheImpl;
import com.android.systemui.Dependency;
import com.android.systemui.InitController;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.doze.AODIntentService;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.people.PeopleProvider;
import com.android.systemui.plank.protocol.TestProtocolProvider;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface SysUIComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Builder {
    }

    Dependency createDependency();

    DumpManager createDumpManager();

    ConfigurationController getConfigurationController();

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
