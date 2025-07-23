package com.android.systemui.fragments;

import android.content.res.Configuration;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.inject.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class FragmentService implements Dumpable {
    public final AnonymousClass1 mConfigurationListener;
    public final FragmentHostManager.Factory mFragmentHostManagerFactory;
    public final ArrayMap mHosts = new ArrayMap();
    public final ArrayMap mInjectionMap = new ArrayMap();
    public final Handler mHandler = new Handler();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class FragmentHostState {
        public final FragmentHostManager mFragmentHostManager;
        public final View mView;

        public FragmentHostState(View view) {
            this.mView = view;
            this.mFragmentHostManager = FragmentService.this.mFragmentHostManagerFactory.create(view);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.fragments.FragmentService$1, java.lang.Object] */
    public FragmentService(FragmentHostManager.Factory factory, ConfigurationController configurationController, DumpManager dumpManager) {
        ?? r0 = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.fragments.FragmentService.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(final Configuration configuration) {
                for (final FragmentHostState fragmentHostState : FragmentService.this.mHosts.values()) {
                    FragmentService.this.mHandler.post(new Runnable() { // from class: com.android.systemui.fragments.FragmentService$FragmentHostState$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            FragmentService.FragmentHostState fragmentHostState2 = FragmentService.FragmentHostState.this;
                            Configuration configuration2 = configuration;
                            FragmentHostManager fragmentHostManager = fragmentHostState2.mFragmentHostManager;
                            if (fragmentHostManager.mConfigChanges.applyNewConfig(fragmentHostManager.mContext.getResources())) {
                                fragmentHostManager.reloadFragments();
                            } else {
                                fragmentHostManager.mFragments.dispatchConfigurationChanged(configuration2);
                            }
                        }
                    });
                }
            }
        };
        this.mConfigurationListener = r0;
        this.mFragmentHostManagerFactory = factory;
        ((ConfigurationControllerImpl) configurationController).addCallback(r0);
        dumpManager.registerNormalDumpable(this);
    }

    public final void addFragmentInstantiationProvider(Class cls, Provider provider) {
        String name = cls.getName();
        if (!this.mInjectionMap.containsKey(name)) {
            this.mInjectionMap.put(name, provider);
            return;
        }
        Log.w("FragmentService", "Fragment " + name + " is already provided by different Dagger component; Not adding method");
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("Dumping fragments:");
        Iterator it = this.mHosts.values().iterator();
        while (it.hasNext()) {
            ((FragmentHostState) it.next()).mFragmentHostManager.mFragments.getFragmentManager().dump("  ", null, printWriter, strArr);
        }
    }

    public final FragmentHostManager getFragmentHostManager(View view) {
        View rootView = view.getRootView();
        FragmentHostState fragmentHostState = (FragmentHostState) this.mHosts.get(rootView);
        if (fragmentHostState == null) {
            fragmentHostState = new FragmentHostState(rootView);
            this.mHosts.put(rootView, fragmentHostState);
        }
        return fragmentHostState.mFragmentHostManager;
    }
}
