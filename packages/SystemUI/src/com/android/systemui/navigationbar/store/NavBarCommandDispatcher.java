package com.android.systemui.navigationbar.store;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.android.systemui.navigationbar.model.NavBarStates;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.util.NavBarReflectUtil;
import com.android.systemui.navigationbar.views.NavigationBar;
import com.android.systemui.navigationbar.views.NavigationBarView;
import com.android.systemui.plank.command.PlankCommandDispatcher;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public final class NavBarCommandDispatcher implements PlankCommandDispatcher {
    public final boolean enabled;
    public final NavBarStore navBarStore;
    public final HashMap originNavState = new HashMap();
    public final Handler handler = new Handler(Looper.getMainLooper());

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public NavBarCommandDispatcher(NavBarStore navBarStore) {
        this.navBarStore = navBarStore;
        if (Build.TYPE.equals("user")) {
            return;
        }
        Log.d("NaBarCommandDispatcher", "init()");
        this.enabled = true;
    }

    public final void copyPrevStatesIfNeeded(int i) {
        if (this.originNavState.get(Integer.valueOf(i)) == null) {
            Log.d("NaBarCommandDispatcher", "copyPrevStates()");
            this.originNavState.put(Integer.valueOf(i), NavBarStates.copy$default(((NavBarStateManagerImpl) ((NavBarStoreImpl) this.navBarStore).getNavStateManager(i)).states));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.android.systemui.plank.command.PlankCommandDispatcher
    public final Bundle dispatch(Bundle bundle, String str) {
        String string;
        String string2;
        final int i;
        String string3;
        boolean z;
        try {
            bundle.getClass();
            string = bundle.getString("cmdType", "");
            string2 = bundle.getString("eventName", "");
            i = bundle.getInt("displayId", 0);
            string3 = bundle.getString("params", "");
            z = bundle.getBoolean("update", false);
        } catch (Exception e) {
            Log.d("NaBarCommandDispatcher", "An exception occurred while processing dispatch().");
            e.printStackTrace();
        }
        if (!this.enabled) {
            return bundle;
        }
        if (string != null) {
            int iHashCode = string.hashCode();
            NavBarStore navBarStore = this.navBarStore;
            switch (iHashCode) {
                case -1833374131:
                    if (!string.equals("FAKE_HANDLE_EVENT")) {
                        break;
                    } else {
                        if (string2 != null && string2.length() != 0) {
                            copyPrevStatesIfNeeded(i);
                            string3.getClass();
                            EventTypeFactory.EventType eventTypeCreateFakeHandleEvent = NavBarReflectUtil.createFakeHandleEvent(string2, string3);
                            if (eventTypeCreateFakeHandleEvent != null) {
                                Log.d("NaBarCommandDispatcher", "Execute fake handle event " + eventTypeCreateFakeHandleEvent + "..");
                                navBarStore.handleEvent(NavBarReflectUtil.class, eventTypeCreateFakeHandleEvent, i);
                                break;
                            }
                        }
                        Log.d("NaBarCommandDispatcher", "Failed to get eventName: " + string2);
                        break;
                    }
                    break;
                case 77866287:
                    if (!string.equals("RESET")) {
                        break;
                    } else {
                        NavBarStates navBarStates = (NavBarStates) this.originNavState.get(Integer.valueOf(i));
                        if (navBarStates != null) {
                            ((NavBarStateManagerImpl) ((NavBarStoreImpl) navBarStore).getNavStateManager(i)).states = navBarStates;
                        } else {
                            Log.d("NaBarCommandDispatcher", "Failed to restore originNavState: " + i + " has null states.");
                        }
                        z = true;
                        break;
                    }
                case 1497837598:
                    if (!string.equals("FAKE_STORE_ACTION")) {
                        break;
                    } else {
                        if (string2 != null && string2.length() != 0) {
                            copyPrevStatesIfNeeded(i);
                            Log.d("NaBarCommandDispatcher", "Running fake store action " + string2 + "..");
                            string3.getClass();
                            NavBarReflectUtil.runFakeStoreAction(navBarStore, string2, string3, i);
                            break;
                        }
                        Log.d("NaBarCommandDispatcher", "Failed to get eventName: " + string2);
                        break;
                    }
                    break;
                case 1622997020:
                    if (!string.equals("FAKE_STATUS")) {
                        break;
                    } else {
                        if (string3 != null && string3.length() != 0) {
                            copyPrevStatesIfNeeded(i);
                            Log.d("NaBarCommandDispatcher", "Update fakeParams " + string3 + "..");
                            NavBarReflectUtil.updateFakeStatus(navBarStore, i, StringsKt__StringsKt.split$default(string3, new String[]{","}, 0, 6));
                            break;
                        }
                        Log.d("NaBarCommandDispatcher", "Failed to get params: " + string3);
                        break;
                    }
            }
            return bundle;
        }
        if (z) {
            this.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarCommandDispatcher.dispatch.3
                @Override // java.lang.Runnable
                public final void run() {
                    NavigationBar navigationBar = (NavigationBar) ((NavBarStoreImpl) NavBarCommandDispatcher.this.navBarStore).getModule(NavigationBar.class, i);
                    navigationBar.repositionNavigationBar(((NavBarStateManagerImpl) navigationBar.mNavBarStateManager).states.rotation);
                    ((NavigationBarView) ((NavBarStoreImpl) NavBarCommandDispatcher.this.navBarStore).getModule(NavigationBarView.class, i)).updateIconsAndHints();
                    ((NavBarStoreImpl) NavBarCommandDispatcher.this.navBarStore).handleEvent(NavBarReflectUtil.class, new EventTypeFactory.EventType.OnNavBarStyleChanged(false, 1, null));
                }
            });
        }
        bundle.getClass();
        return bundle;
    }
}
