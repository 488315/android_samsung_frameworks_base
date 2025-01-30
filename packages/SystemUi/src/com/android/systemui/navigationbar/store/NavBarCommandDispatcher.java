package com.android.systemui.navigationbar.store;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.util.NavBarReflectUtil;
import com.android.systemui.plank.command.PlankCommandDispatcher;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavBarCommandDispatcher implements PlankCommandDispatcher {
    public final boolean enabled;
    public final NavBarStore navBarStore;
    public final HashMap originNavState = new HashMap();
    public final Handler handler = new Handler(Looper.getMainLooper());

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        HashMap hashMap = this.originNavState;
        if (hashMap.get(Integer.valueOf(i)) == null) {
            Log.d("NaBarCommandDispatcher", "copyPrevStates()");
            hashMap.put(Integer.valueOf(i), NavBarStateManager.States.copy$default(((NavBarStoreImpl) this.navBarStore).getNavStateManager(i).states));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00b5, code lost:
    
        android.util.Log.d("NaBarCommandDispatcher", "Failed to get eventName: " + r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x012e, code lost:
    
        android.util.Log.d("NaBarCommandDispatcher", "Failed to get eventName: " + r10);
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0066 A[Catch: Exception -> 0x0172, TryCatch #0 {Exception -> 0x0172, blocks: (B:3:0x0012, B:8:0x003d, B:11:0x004e, B:15:0x005a, B:20:0x0066, B:22:0x0076, B:24:0x009d, B:28:0x00ab, B:33:0x00b5, B:35:0x00c5, B:36:0x00df, B:39:0x00ea, B:41:0x00f8, B:44:0x0167, B:47:0x0101, B:48:0x0117, B:52:0x0124, B:57:0x012e, B:59:0x013e, B:61:0x0147), top: B:2:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0076 A[Catch: Exception -> 0x0172, TryCatch #0 {Exception -> 0x0172, blocks: (B:3:0x0012, B:8:0x003d, B:11:0x004e, B:15:0x005a, B:20:0x0066, B:22:0x0076, B:24:0x009d, B:28:0x00ab, B:33:0x00b5, B:35:0x00c5, B:36:0x00df, B:39:0x00ea, B:41:0x00f8, B:44:0x0167, B:47:0x0101, B:48:0x0117, B:52:0x0124, B:57:0x012e, B:59:0x013e, B:61:0x0147), top: B:2:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0167 A[Catch: Exception -> 0x0172, TRY_LEAVE, TryCatch #0 {Exception -> 0x0172, blocks: (B:3:0x0012, B:8:0x003d, B:11:0x004e, B:15:0x005a, B:20:0x0066, B:22:0x0076, B:24:0x009d, B:28:0x00ab, B:33:0x00b5, B:35:0x00c5, B:36:0x00df, B:39:0x00ea, B:41:0x00f8, B:44:0x0167, B:47:0x0101, B:48:0x0117, B:52:0x0124, B:57:0x012e, B:59:0x013e, B:61:0x0147), top: B:2:0x0012 }] */
    @Override // com.android.systemui.plank.command.PlankCommandDispatcher
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Bundle dispatch(String str, Bundle bundle) {
        String string;
        String string2;
        final int i;
        String string3;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        try {
            Intrinsics.checkNotNull(bundle);
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
            int hashCode = string.hashCode();
            NavBarStore navBarStore = this.navBarStore;
            z2 = z;
            switch (hashCode) {
                case -1833374131:
                    boolean z5 = true;
                    if (!string.equals("FAKE_HANDLE_EVENT")) {
                        break;
                    } else {
                        if (string2 != null && string2.length() != 0) {
                            z5 = false;
                            break;
                        }
                        copyPrevStatesIfNeeded(i);
                        EventTypeFactory.EventType createFakeHandleEvent = NavBarReflectUtil.createFakeHandleEvent(string2, string3);
                        if (createFakeHandleEvent != null) {
                            Log.d("NaBarCommandDispatcher", "Execute fake handle event " + createFakeHandleEvent + "..");
                            ((NavBarStoreImpl) navBarStore).handleEvent(NavBarReflectUtil.class, createFakeHandleEvent, i);
                            break;
                        }
                    }
                    break;
                case 77866287:
                    if (!string.equals("RESET")) {
                        break;
                    } else {
                        NavBarStateManager.States states = (NavBarStateManager.States) this.originNavState.get(Integer.valueOf(i));
                        if (states != null) {
                            ((NavBarStoreImpl) navBarStore).getNavStateManager(i).states = states;
                        } else {
                            Log.d("NaBarCommandDispatcher", "Failed to restore originNavState: " + i + " has null states.");
                        }
                        z3 = true;
                        if (z3) {
                            this.handler.post(new Runnable() { // from class: com.android.systemui.navigationbar.store.NavBarCommandDispatcher$dispatch$3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    NavigationBar navigationBar = (NavigationBar) ((NavBarStoreImpl) NavBarCommandDispatcher.this.navBarStore).getModule(NavigationBar.class, i);
                                    navigationBar.repositionNavigationBar(navigationBar.mNavBarStateManager.states.rotation);
                                    ((NavigationBarView) ((NavBarStoreImpl) NavBarCommandDispatcher.this.navBarStore).getModule(NavigationBarView.class, i)).updateIconsAndHints();
                                    ((NavBarStoreImpl) NavBarCommandDispatcher.this.navBarStore).handleEvent(NavBarReflectUtil.class, new EventTypeFactory.EventType.OnNavBarStyleChanged(false, 1, null));
                                }
                            });
                        }
                        Intrinsics.checkNotNull(bundle);
                        break;
                    }
                case 1497837598:
                    boolean z6 = true;
                    if (!string.equals("FAKE_STORE_ACTION")) {
                        break;
                    } else {
                        if (string2 != null && string2.length() != 0) {
                            z6 = false;
                            break;
                        }
                        copyPrevStatesIfNeeded(i);
                        Log.d("NaBarCommandDispatcher", "Running fake store action " + string2 + "..");
                        NavBarReflectUtil.runFakeStoreAction(navBarStore, string2, string3, i);
                        break;
                    }
                case 1622997020:
                    if (!string.equals("FAKE_STATUS")) {
                        break;
                    } else {
                        if (string3 != null && string3.length() != 0) {
                            z4 = false;
                            if (!z4) {
                                Log.d("NaBarCommandDispatcher", "Failed to get params: " + string3);
                                break;
                            } else {
                                copyPrevStatesIfNeeded(i);
                                Log.d("NaBarCommandDispatcher", "Update fakeParams " + string3 + "..");
                                NavBarReflectUtil.updateFakeStatus(navBarStore, i, StringsKt__StringsKt.split$default(string3, new String[]{","}, 0, 6));
                                break;
                            }
                        }
                        z4 = true;
                        if (!z4) {
                        }
                    }
                    break;
            }
            return bundle;
        }
        z2 = z;
        z3 = z2;
        if (z3) {
        }
        Intrinsics.checkNotNull(bundle);
        return bundle;
    }
}
