package com.android.systemui.navigationbar.util;

import android.util.Log;
import com.android.systemui.navigationbar.bandaid.Band;
import com.android.systemui.navigationbar.model.NavBarStates;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
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

/* loaded from: classes2.dex */
public final class NavBarReflectUtil {
    public static final NavBarReflectUtil INSTANCE = new NavBarReflectUtil();

    private NavBarReflectUtil() {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void assign(Field field, Object obj, String str) throws IllegalAccessException, IllegalArgumentException {
        Object objValueOf;
        field.setAccessible(true);
        String name = field.getType().getName();
        Locale locale = Locale.ROOT;
        String lowerCase = name.toLowerCase(locale);
        switch (lowerCase.hashCode()) {
            case -1325958191:
                if (!lowerCase.equals("double")) {
                    objValueOf = null;
                    break;
                } else {
                    try {
                        if (ScreenFloatValueRegEx.value.matches(str)) {
                            objValueOf = Double.valueOf(Double.parseDouble(str));
                            break;
                        }
                    } catch (NumberFormatException unused) {
                    }
                }
            case -891985903:
                objValueOf = str;
                if (!lowerCase.equals("string")) {
                }
                break;
            case 104431:
                if (lowerCase.equals("int")) {
                    objValueOf = Integer.valueOf(Integer.parseInt(str));
                    break;
                }
                break;
            case 3327612:
                if (lowerCase.equals("long")) {
                    objValueOf = Long.valueOf(Long.parseLong(str));
                    break;
                }
                break;
            case 64711720:
                if (lowerCase.equals("boolean")) {
                    objValueOf = Boolean.valueOf(Boolean.parseBoolean(str.toLowerCase(locale)));
                    break;
                }
                break;
            case 97526364:
                if (lowerCase.equals("float")) {
                    objValueOf = StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(str);
                    break;
                }
                break;
        }
        field.set(obj, objValueOf);
    }

    public static final EventTypeFactory.EventType createFakeHandleEvent(String str, String str2) {
        try {
            return (EventTypeFactory.EventType) new Gson().fromJson(str2, (Class) Class.forName("com.android.systemui.navigationbar.store.EventTypeFactory$EventType$" + str));
        } catch (Exception e) {
            Log.e("NavBarReflectUtil", "Failed to create fake handle event : " + e);
            e.printStackTrace();
            return null;
        }
    }

    public static final void runFakeStoreAction(NavBarStore navBarStore, String str, String str2, int i) {
        try {
            NavBarStoreAction.Action action = new NavBarStoreAction.Action(null, null, null, null, false, 0.0f, null, false, 0.0f, 0, false, false, 0, 0, null, null, false, false, null, 0.0f, 0.0f, 0, 0, 8388607, null);
            Iterator it = StringsKt__StringsKt.split$default(str2, new String[]{","}, 0, 6).iterator();
            while (it.hasNext()) {
                List listSplit$default = StringsKt__StringsKt.split$default((String) it.next(), new String[]{"="}, 0, 6);
                if (listSplit$default.size() == 2) {
                    Field declaredField = NavBarStoreAction.Action.class.getDeclaredField((String) listSplit$default.get(0));
                    declaredField.setAccessible(true);
                    NavBarReflectUtil navBarReflectUtil = INSTANCE;
                    String str3 = (String) listSplit$default.get(1);
                    navBarReflectUtil.getClass();
                    assign(declaredField, action, str3);
                }
            }
            NavBarStoreAction navBarStoreAction = (NavBarStoreAction) Class.forName("com.android.systemui.navigationbar.store.NavBarStoreAction$" + str).getDeclaredConstructor(NavBarStoreAction.Action.class).newInstance(action);
            NavBarStoreImpl navBarStoreImpl = (NavBarStoreImpl) navBarStore;
            NavBarStateManager navStateManager = navBarStoreImpl.getNavStateManager(i);
            navBarStoreImpl.apply(new Band.Kit(new EventTypeFactory.EventType.OnFakeNavBarEventOccurred(false, 1, null), navStateManager, ((NavBarStateManagerImpl) navStateManager).states, i), navBarStoreAction);
        } catch (Exception e) {
            Log.e("NavBarReflectUtil", "Failed to create fake store action : " + e);
            e.printStackTrace();
        }
    }

    public static final void updateFakeStatus(NavBarStore navBarStore, int i, List list) {
        NavBarStates navBarStates = ((NavBarStateManagerImpl) ((NavBarStoreImpl) navBarStore).getNavStateManager(i)).states;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            List listSplit$default = StringsKt__StringsKt.split$default((String) it.next(), new String[]{"="}, 0, 6);
            if (listSplit$default.size() == 2) {
                Field declaredField = NavBarStates.class.getDeclaredField((String) listSplit$default.get(0));
                declaredField.setAccessible(true);
                String str = (String) listSplit$default.get(1);
                INSTANCE.getClass();
                assign(declaredField, navBarStates, str);
            }
        }
    }
}
