package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardQuickAffordanceLocalUserSelectionManager implements KeyguardQuickAffordanceSelectionManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final ChannelFlowTransformLatest selections;
    public SharedPreferences sharedPrefs;
    public final UserFileManager userFileManager;
    public final UserTracker userTracker;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public KeyguardQuickAffordanceLocalUserSelectionManager(Context context, UserFileManager userFileManager, UserTracker userTracker, BroadcastDispatcher broadcastDispatcher) {
        this.context = context;
        this.userFileManager = userFileManager;
        this.userTracker = userTracker;
        this.sharedPrefs = ((UserFileManagerImpl) userFileManager).getSharedPreferences$1(((UserTrackerImpl) userTracker).getUserId(), "quick_affordance_selections");
        Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new KeyguardQuickAffordanceLocalUserSelectionManager$userId$1(this, null));
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                String[] stringArray = KeyguardQuickAffordanceLocalUserSelectionManager.this.context.getResources().getStringArray(R.array.config_keyguardQuickAffordanceDefaults);
                int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(stringArray.length);
                if (mapCapacity < 16) {
                    mapCapacity = 16;
                }
                LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
                for (String str : stringArray) {
                    str.getClass();
                    List split$default = StringsKt__StringsKt.split$default(str, new String[]{":"}, 0, 6);
                    if (split$default.size() != 2) {
                        throw new IllegalStateException("Check failed.");
                    }
                    Pair pair = new Pair((String) split$default.get(0), StringsKt__StringsKt.split$default((CharSequence) split$default.get(1), new String[]{","}, 0, 6));
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
                return linkedHashMap;
            }
        });
        this.selections = FlowKt.transformLatest(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(conflatedCallbackFlow, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardQuickAffordanceLocalUserSelectionManager$selections$1(null), BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, new IntentFilter("com.android.systemui.backup.RESTORE_FINISHED"), null, 2)), new KeyguardQuickAffordanceLocalUserSelectionManager$selections$2(null)), new KeyguardQuickAffordanceLocalUserSelectionManager$special$$inlined$flatMapLatest$1(null, this));
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    /* renamed from: getSelections, reason: collision with other method in class */
    public final Flow mo2597getSelections() {
        return this.selections;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    public final void setSelections(String str, List list) {
        this.sharedPrefs.edit().putString(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("slot_", str), CollectionsKt___CollectionsKt.joinToString$default(list, ",", null, null, null, 62)).apply();
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager
    public final Map getSelections() {
        Set<String> keySet = this.sharedPrefs.getAll().keySet();
        ArrayList arrayList = new ArrayList();
        for (Object obj : keySet) {
            String str = (String) obj;
            str.getClass();
            if (str.startsWith("slot_")) {
                arrayList.add(obj);
            }
        }
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        if (mapCapacity < 16) {
            mapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList.get(i);
            i++;
            String str2 = (String) obj2;
            str2.getClass();
            String substring = str2.substring(5);
            String string = this.sharedPrefs.getString(str2, null);
            Pair pair = new Pair(substring, (string == null || string.length() == 0) ? EmptyList.INSTANCE : StringsKt__StringsKt.split$default(string, new String[]{","}, 0, 6));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return new LinkedHashMap(linkedHashMap);
    }
}
