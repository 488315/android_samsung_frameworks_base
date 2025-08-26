package androidx.navigation.compose;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.runtime.saveable.SaverKt;
import androidx.compose.runtime.saveable.SaverKt$Saver$1;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavBackStackEntryState;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigator;
import androidx.navigation.NavigatorProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.ArrayIterator;

/* loaded from: classes.dex */
public abstract class NavHostControllerKt {
    public static final NavHostController access$createNavController(Context context) {
        NavHostController navHostController = new NavHostController(context);
        NavigatorProvider navigatorProvider = navHostController._navigatorProvider;
        navigatorProvider.addNavigator(new ComposeNavGraphNavigator(navigatorProvider));
        navHostController._navigatorProvider.addNavigator(new ComposeNavigator());
        navHostController._navigatorProvider.addNavigator(new DialogNavigator());
        return navHostController;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final NavHostController rememberNavController(Navigator[] navigatorArr, ComposerImpl composerImpl) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.navigation.compose.rememberNavController (NavHostController.kt:57)");
        }
        final Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        Object[] objArrCopyOf = Arrays.copyOf(navigatorArr, navigatorArr.length);
        NavHostControllerKt$NavControllerSaver$1 navHostControllerKt$NavControllerSaver$1 = new Function2() { // from class: androidx.navigation.compose.NavHostControllerKt$NavControllerSaver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Bundle bundle;
                NavHostController navHostController = (NavHostController) obj2;
                navHostController.getClass();
                ArrayList<String> arrayList = new ArrayList<>();
                Bundle bundle2 = new Bundle();
                for (Map.Entry entry : MapsKt__MapsKt.toMap(navHostController._navigatorProvider._navigators).entrySet()) {
                    ((Navigator) entry.getValue()).getClass();
                }
                if (arrayList.isEmpty()) {
                    bundle = null;
                } else {
                    bundle = new Bundle();
                    bundle2.putStringArrayList("android-support-nav:controller:navigatorState:names", arrayList);
                    bundle.putBundle("android-support-nav:controller:navigatorState", bundle2);
                }
                ArrayDeque arrayDeque = navHostController.backQueue;
                if (!arrayDeque.isEmpty()) {
                    if (bundle == null) {
                        bundle = new Bundle();
                    }
                    Parcelable[] parcelableArr = new Parcelable[arrayDeque.getSize()];
                    Iterator it = arrayDeque.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        parcelableArr[i] = new NavBackStackEntryState((NavBackStackEntry) it.next());
                        i++;
                    }
                    bundle.putParcelableArray("android-support-nav:controller:backStack", parcelableArr);
                }
                if (!navHostController.backStackMap.isEmpty()) {
                    if (bundle == null) {
                        bundle = new Bundle();
                    }
                    int[] iArr = new int[navHostController.backStackMap.size()];
                    ArrayList<String> arrayList2 = new ArrayList<>();
                    int i2 = 0;
                    for (Map.Entry entry2 : ((LinkedHashMap) navHostController.backStackMap).entrySet()) {
                        int iIntValue = ((Number) entry2.getKey()).intValue();
                        String str = (String) entry2.getValue();
                        iArr[i2] = iIntValue;
                        arrayList2.add(str);
                        i2++;
                    }
                    bundle.putIntArray("android-support-nav:controller:backStackDestIds", iArr);
                    bundle.putStringArrayList("android-support-nav:controller:backStackIds", arrayList2);
                }
                if (!navHostController.backStackStates.isEmpty()) {
                    if (bundle == null) {
                        bundle = new Bundle();
                    }
                    ArrayList<String> arrayList3 = new ArrayList<>();
                    for (Map.Entry entry3 : ((LinkedHashMap) navHostController.backStackStates).entrySet()) {
                        String str2 = (String) entry3.getKey();
                        ArrayDeque arrayDeque2 = (ArrayDeque) entry3.getValue();
                        arrayList3.add(str2);
                        Parcelable[] parcelableArr2 = new Parcelable[arrayDeque2.getSize()];
                        Iterator it2 = arrayDeque2.iterator();
                        int i3 = 0;
                        while (it2.hasNext()) {
                            Object next = it2.next();
                            int i4 = i3 + 1;
                            if (i3 < 0) {
                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                                throw null;
                            }
                            parcelableArr2[i3] = (NavBackStackEntryState) next;
                            i3 = i4;
                        }
                        bundle.putParcelableArray(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("android-support-nav:controller:backStackStates:", str2), parcelableArr2);
                    }
                    bundle.putStringArrayList("android-support-nav:controller:backStackStates", arrayList3);
                }
                if (navHostController.deepLinkHandled) {
                    if (bundle == null) {
                        bundle = new Bundle();
                    }
                    bundle.putBoolean("android-support-nav:controller:deepLinkHandled", navHostController.deepLinkHandled);
                }
                return bundle;
            }
        };
        Function1 function1 = new Function1() { // from class: androidx.navigation.compose.NavHostControllerKt$NavControllerSaver$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Bundle bundle = (Bundle) obj;
                NavHostController navHostControllerAccess$createNavController = NavHostControllerKt.access$createNavController(context);
                if (bundle == null) {
                    return navHostControllerAccess$createNavController;
                }
                bundle.setClassLoader(navHostControllerAccess$createNavController.context.getClassLoader());
                navHostControllerAccess$createNavController.navigatorStateToRestore = bundle.getBundle("android-support-nav:controller:navigatorState");
                navHostControllerAccess$createNavController.backStackToRestore = bundle.getParcelableArray("android-support-nav:controller:backStack");
                ((LinkedHashMap) navHostControllerAccess$createNavController.backStackStates).clear();
                int[] intArray = bundle.getIntArray("android-support-nav:controller:backStackDestIds");
                ArrayList<String> stringArrayList = bundle.getStringArrayList("android-support-nav:controller:backStackIds");
                int i = 0;
                if (intArray != null && stringArrayList != null) {
                    int length = intArray.length;
                    int i2 = 0;
                    int i3 = 0;
                    while (i2 < length) {
                        navHostControllerAccess$createNavController.backStackMap.put(Integer.valueOf(intArray[i2]), stringArrayList.get(i3));
                        i2++;
                        i3++;
                    }
                }
                ArrayList<String> stringArrayList2 = bundle.getStringArrayList("android-support-nav:controller:backStackStates");
                if (stringArrayList2 != null) {
                    int size = stringArrayList2.size();
                    while (i < size) {
                        String str = stringArrayList2.get(i);
                        i++;
                        String str2 = str;
                        Parcelable[] parcelableArray = bundle.getParcelableArray("android-support-nav:controller:backStackStates:" + str2);
                        if (parcelableArray != null) {
                            Map map = navHostControllerAccess$createNavController.backStackStates;
                            ArrayDeque arrayDeque = new ArrayDeque(parcelableArray.length);
                            ArrayIterator arrayIterator = new ArrayIterator(parcelableArray);
                            while (arrayIterator.hasNext()) {
                                arrayDeque.addLast((NavBackStackEntryState) ((Parcelable) arrayIterator.next()));
                            }
                            map.put(str2, arrayDeque);
                        }
                    }
                }
                navHostControllerAccess$createNavController.deepLinkHandled = bundle.getBoolean("android-support-nav:controller:deepLinkHandled");
                return navHostControllerAccess$createNavController;
            }
        };
        SaverKt$Saver$1 saverKt$Saver$1 = SaverKt.AutoSaver;
        SaverKt$Saver$1 saverKt$Saver$12 = new SaverKt$Saver$1(navHostControllerKt$NavControllerSaver$1, function1);
        boolean zChangedInstance = composerImpl.changedInstance(context);
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChangedInstance) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new Function0() { // from class: androidx.navigation.compose.NavHostControllerKt$rememberNavController$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return NavHostControllerKt.access$createNavController(context);
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        NavHostController navHostController = (NavHostController) RememberSaveableKt.rememberSaveable(objArrCopyOf, saverKt$Saver$12, null, (Function0) objRememberedValue, composerImpl, 0, 4);
        for (Navigator navigator : navigatorArr) {
            navHostController._navigatorProvider.addNavigator(navigator);
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return navHostController;
    }
}
