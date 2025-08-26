package com.android.wm.shell.compatui.letterbox;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final class LetterboxUtils$Maps {
    public static final LetterboxUtils$Maps INSTANCE = new LetterboxUtils$Maps();

    private LetterboxUtils$Maps() {
    }

    public static void runOnItem$default(LetterboxUtils$Maps letterboxUtils$Maps, Map map, LetterboxKey letterboxKey, Function1 function1, Function2 function2, int i) {
        if ((i & 2) != 0) {
            function1 = new LetterboxUtils$Maps$$ExternalSyntheticLambda0();
        }
        if ((i & 4) != 0) {
            function2 = new LetterboxUtils$Maps$$ExternalSyntheticLambda1();
        }
        letterboxUtils$Maps.getClass();
        LinkedHashMap linkedHashMap = (LinkedHashMap) map;
        Object obj = linkedHashMap.get(letterboxKey);
        if (obj != null) {
            function1.mo781invoke(obj);
        } else {
            function2.invoke(letterboxKey, linkedHashMap);
        }
    }
}
