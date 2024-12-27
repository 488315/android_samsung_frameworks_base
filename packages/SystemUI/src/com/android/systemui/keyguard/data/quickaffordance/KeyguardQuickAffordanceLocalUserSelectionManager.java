package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.settings.SystemSettings;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardQuickAffordanceLocalUserSelectionManager implements KeyguardQuickAffordanceSelectionManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final Lazy defaults$delegate;
    public final ChannelFlowTransformLatest selections;
    public SharedPreferences sharedPrefs;
    public final SystemSettings systemSettings;
    public final UserFileManager userFileManager;
    public final UserTracker userTracker;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public KeyguardQuickAffordanceLocalUserSelectionManager(Context context, UserFileManager userFileManager, UserTracker userTracker, SystemSettings systemSettings, BroadcastDispatcher broadcastDispatcher) {
        this.context = context;
        this.userFileManager = userFileManager;
        this.userTracker = userTracker;
        this.systemSettings = systemSettings;
        this.sharedPrefs = ((UserFileManagerImpl) userFileManager).getSharedPreferences$1(((UserTrackerImpl) userTracker).getUserId(), "quick_affordance_selections");
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        KeyguardQuickAffordanceLocalUserSelectionManager$userId$1 keyguardQuickAffordanceLocalUserSelectionManager$userId$1 = new KeyguardQuickAffordanceLocalUserSelectionManager$userId$1(this, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(keyguardQuickAffordanceLocalUserSelectionManager$userId$1);
        this.defaults$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager$defaults$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Flags.FEATURE_FLAGS.getClass();
                String[] stringArray = KeyguardQuickAffordanceLocalUserSelectionManager.this.context.getResources().getStringArray(R.array.config_keyguardQuickAffordanceDefaults);
                int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(stringArray.length);
                if (mapCapacity < 16) {
                    mapCapacity = 16;
                }
                LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
                for (String str : stringArray) {
                    Intrinsics.checkNotNull(str);
                    List split$default = StringsKt__StringsKt.split$default(str, new String[]{":"}, 0, 6);
                    if (split$default.size() != 2) {
                        throw new IllegalStateException("Check failed.".toString());
                    }
                    Pair pair = new Pair((String) split$default.get(0), StringsKt__StringsKt.split$default((CharSequence) split$default.get(1), new String[]{","}, 0, 6));
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
                return linkedHashMap;
            }
        });
        this.selections = FlowKt.transformLatest(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(conflatedCallbackFlow2, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardQuickAffordanceLocalUserSelectionManager$selections$1(null), BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.android.systemui.backup.RESTORE_FINISHED"), null, 4, "com.android.systemui.permission.SELF", 2)), new KeyguardQuickAffordanceLocalUserSelectionManager$selections$2(null)), new KeyguardQuickAffordanceLocalUserSelectionManager$special$$inlined$flatMapLatest$1(null, this));
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    /* renamed from: getSelections, reason: collision with other method in class */
    public final Flow mo1960getSelections() {
        return this.selections;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    public final void setSelections(String str, List list) {
        this.sharedPrefs.edit().putString("slot_".concat(str), CollectionsKt___CollectionsKt.joinToString$default(list, ",", null, null, null, 62)).apply();
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    public final Map getSelections() {
        boolean z = this.context.getResources().getBoolean(R.bool.custom_lockscreen_shortcuts_enabled);
        Lazy lazy = this.defaults$delegate;
        if (!z) {
            return (Map) lazy.getValue();
        }
        Set<String> keySet = this.sharedPrefs.getAll().keySet();
        ArrayList arrayList = new ArrayList();
        for (Object obj : keySet) {
            String str = (String) obj;
            Intrinsics.checkNotNull(str);
            if (str.startsWith("slot_")) {
                arrayList.add(obj);
            }
        }
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        if (mapCapacity < 16) {
            mapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            Intrinsics.checkNotNull(str2);
            String substring = str2.substring(5);
            String string = this.sharedPrefs.getString(str2, null);
            Pair pair = new Pair(substring, (string == null || string.length() == 0) ? EmptyList.INSTANCE : StringsKt__StringsKt.split$default(string, new String[]{","}, 0, 6));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(linkedHashMap);
        for (Map.Entry entry : ((Map) lazy.getValue()).entrySet()) {
            String str3 = (String) entry.getKey();
            List list = (List) entry.getValue();
            if (!linkedHashMap2.containsKey(str3)) {
                linkedHashMap2.put(str3, list);
            }
        }
        return linkedHashMap2;
    }
}
