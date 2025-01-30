package com.android.systemui.navigationbar.util;

import android.util.Log;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.google.gson.Gson;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.text.ScreenFloatValueRegEx;
import kotlin.text.StringsKt__StringNumberConversionsJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavBarReflectUtil {
    public static final NavBarReflectUtil INSTANCE = new NavBarReflectUtil();

    private NavBarReflectUtil() {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x006a, code lost:
    
        if (r0.equals("string") == false) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void assign(Field field, Object obj, String str) {
        Object obj2;
        field.setAccessible(true);
        String name = field.getType().getName();
        Locale locale = Locale.ROOT;
        String lowerCase = name.toLowerCase(locale);
        switch (lowerCase.hashCode()) {
            case -1325958191:
                if (lowerCase.equals("double")) {
                    try {
                        if (ScreenFloatValueRegEx.value.matches(str)) {
                            obj2 = Double.valueOf(Double.parseDouble(str));
                            break;
                        }
                    } catch (NumberFormatException unused) {
                    }
                }
                obj2 = null;
                break;
            case -891985903:
                obj2 = str;
                break;
            case 104431:
                if (lowerCase.equals("int")) {
                    obj2 = Integer.valueOf(Integer.parseInt(str));
                    break;
                }
                obj2 = null;
                break;
            case 3327612:
                if (lowerCase.equals("long")) {
                    obj2 = Long.valueOf(Long.parseLong(str));
                    break;
                }
                obj2 = null;
                break;
            case 64711720:
                if (lowerCase.equals("boolean")) {
                    obj2 = Boolean.valueOf(Boolean.parseBoolean(str.toLowerCase(locale)));
                    break;
                }
                obj2 = null;
                break;
            case 97526364:
                if (lowerCase.equals("float")) {
                    obj2 = StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(str);
                    break;
                }
                obj2 = null;
                break;
            default:
                obj2 = null;
                break;
        }
        field.set(obj, obj2);
    }

    public static final EventTypeFactory.EventType createFakeHandleEvent(String str, String str2) {
        try {
            return (EventTypeFactory.EventType) new Gson().fromJson(Class.forName("com.android.systemui.navigationbar.store.EventTypeFactory$EventType$".concat(str)), str2);
        } catch (Exception e) {
            Log.e("NavBarReflectUtil", "Failed to create fake handle event : " + e);
            e.printStackTrace();
            return null;
        }
    }

    public static final void runFakeStoreAction(NavBarStore navBarStore, String str, String str2, int i) {
        try {
            NavBarStoreAction.Action action = r1;
            NavBarStoreAction.Action action2 = new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, false, null, null, false, false, null, 0.0f, 0.0f, 0, 4194303, null);
            Iterator it = StringsKt__StringsKt.split$default(str2, new String[]{","}, 0, 6).iterator();
            while (it.hasNext()) {
                List split$default = StringsKt__StringsKt.split$default((String) it.next(), new String[]{"="}, 0, 6);
                if (split$default.size() == 2) {
                    Field declaredField = NavBarStoreAction.Action.class.getDeclaredField((String) split$default.get(0));
                    declaredField.setAccessible(true);
                    NavBarReflectUtil navBarReflectUtil = INSTANCE;
                    String str3 = (String) split$default.get(1);
                    navBarReflectUtil.getClass();
                    NavBarStoreAction.Action action3 = action;
                    assign(declaredField, action3, str3);
                    action = action3;
                }
            }
            NavBarStoreAction navBarStoreAction = (NavBarStoreAction) Class.forName("com.android.systemui.navigationbar.store.NavBarStoreAction$" + str).getDeclaredConstructor(NavBarStoreAction.Action.class).newInstance(action);
            NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore;
            NavBarStateManager navStateManager = navBarStoreImpl.getNavStateManager(i);
            navBarStoreImpl.apply(new Band.Kit(new EventTypeFactory.EventType.OnFakeNavBarEventOccurred(false, 1, null), navStateManager, navStateManager.states, i), navBarStoreAction);
        } catch (Exception e) {
            Log.e("NavBarReflectUtil", "Failed to create fake store action : " + e);
            e.printStackTrace();
        }
    }

    public static final void updateFakeStatus(NavBarStore navBarStore, int i, List list) {
        NavBarStateManager.States states = ((NavBarStoreImpl) navBarStore).getNavStateManager(i).states;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            List split$default = StringsKt__StringsKt.split$default((String) it.next(), new String[]{"="}, 0, 6);
            if (split$default.size() == 2) {
                Field declaredField = NavBarStateManager.States.class.getDeclaredField((String) split$default.get(0));
                declaredField.setAccessible(true);
                String str = (String) split$default.get(1);
                INSTANCE.getClass();
                assign(declaredField, states, str);
            }
        }
    }
}
