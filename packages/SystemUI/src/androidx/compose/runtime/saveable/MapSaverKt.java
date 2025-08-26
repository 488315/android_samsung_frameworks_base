package androidx.compose.runtime.saveable;

import com.android.systemui.inputdevice.tutorial.ui.composable.TutorialActionState$Companion$$ExternalSyntheticLambda0;
import com.android.systemui.inputdevice.tutorial.ui.composable.TutorialActionState$Companion$$ExternalSyntheticLambda1;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class MapSaverKt {
    public static final SaverKt$Saver$1 mapSaver(final TutorialActionState$Companion$$ExternalSyntheticLambda0 tutorialActionState$Companion$$ExternalSyntheticLambda0, final TutorialActionState$Companion$$ExternalSyntheticLambda1 tutorialActionState$Companion$$ExternalSyntheticLambda1) {
        return ListSaverKt.listSaver(new Function2() { // from class: androidx.compose.runtime.saveable.MapSaverKt.mapSaver.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                ArrayList arrayList = new ArrayList();
                for (Map.Entry entry : ((Map) tutorialActionState$Companion$$ExternalSyntheticLambda0.invoke((SaverScope) obj, obj2)).entrySet()) {
                    arrayList.add(entry.getKey());
                    arrayList.add(entry.getValue());
                }
                return arrayList;
            }
        }, new Function1() { // from class: androidx.compose.runtime.saveable.MapSaverKt.mapSaver.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                List list = (List) obj;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                if (list.size() % 2 != 0) {
                    throw new IllegalStateException("non-zero remainder");
                }
                for (int i = 0; i < list.size(); i += 2) {
                    linkedHashMap.put((String) list.get(i), list.get(i + 1));
                }
                return tutorialActionState$Companion$$ExternalSyntheticLambda1.mo781invoke(linkedHashMap);
            }
        });
    }
}
