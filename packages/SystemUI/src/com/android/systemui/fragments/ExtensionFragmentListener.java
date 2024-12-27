package com.android.systemui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.View;
import com.android.systemui.R;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.plugins.FragmentBase;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ExtensionFragmentListener implements Consumer {
    public final ExtensionController.Extension mExtension;
    public final FragmentHostManager mFragmentHostManager;
    public final int mId;
    public String mOldClass;
    public final String mTag;

    private ExtensionFragmentListener(FragmentService fragmentService, View view, String str, int i, ExtensionController.Extension extension) {
        this.mTag = str;
        FragmentHostManager fragmentHostManager = fragmentService.getFragmentHostManager(view);
        this.mFragmentHostManager = fragmentHostManager;
        this.mExtension = extension;
        this.mId = i;
        fragmentHostManager.mFragments.getFragmentManager().beginTransaction().replace(i, (Fragment) ((ExtensionControllerImpl.ExtensionImpl) extension).mItem, str).commit();
        ((ExtensionControllerImpl.ExtensionImpl) extension).mItem = null;
    }

    public static void attachExtensonToFragment(FragmentService fragmentService, View view, ExtensionControllerImpl.ExtensionImpl extensionImpl) {
        extensionImpl.mCallbacks.add(new ExtensionFragmentListener(fragmentService, view, QS.TAG, R.id.qs_frame, extensionImpl));
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        FragmentBase fragmentBase = (FragmentBase) obj;
        if (Fragment.class.isInstance(fragmentBase)) {
            FragmentHostManager.ExtensionFragmentManager extensionFragmentManager = this.mFragmentHostManager.mPlugins;
            int i = this.mId;
            String str = this.mTag;
            String str2 = this.mOldClass;
            String name = fragmentBase.getClass().getName();
            ExtensionControllerImpl.ExtensionImpl extensionImpl = (ExtensionControllerImpl.ExtensionImpl) this.mExtension;
            Context context = extensionImpl.mPluginContext;
            if (context == null) {
                context = ExtensionControllerImpl.this.mDefaultContext;
            }
            if (str2 != null) {
                extensionFragmentManager.mExtensionLookup.remove(str2);
            }
            extensionFragmentManager.mExtensionLookup.put(name, context);
            FragmentHostManager fragmentHostManager = FragmentHostManager.this;
            fragmentHostManager.mFragments.getFragmentManager().beginTransaction().replace(i, extensionFragmentManager.instantiate(context, name, null), str).commit();
            fragmentHostManager.reloadFragments();
            this.mOldClass = fragmentBase.getClass().getName();
        } else {
            Log.e("ExtensionFragmentListener", fragmentBase.getClass().getName().concat(" must be a Fragment"));
        }
        ExtensionControllerImpl.ExtensionImpl extensionImpl2 = (ExtensionControllerImpl.ExtensionImpl) this.mExtension;
        Object obj2 = extensionImpl2.mItem;
        if (obj2 != null) {
            ExtensionControllerImpl.this.mLeakDetector.trackGarbage(obj2);
        }
        extensionImpl2.mItem = null;
    }
}
