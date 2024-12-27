package com.android.systemui.flags;

import android.util.IndentingPrintWriter;
import com.android.systemui.CoreStartable;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.collections.EmptyList;

public abstract class FlagDependenciesBase implements CoreStartable {
    public final EmptyList allDependencies;
    public final Handler handler;
    public final EmptyList unmetDependencies;

    public interface Handler {
    }

    public FlagDependenciesBase(FeatureFlagsClassic featureFlagsClassic, Handler handler) {
        this.handler = handler;
        new ArrayList();
        EmptyList emptyList = EmptyList.INSTANCE;
        this.allDependencies = emptyList;
        this.unmetDependencies = emptyList;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        EmptyList emptyList = this.allDependencies;
        asIndenting.append("allDependencies").append((CharSequence) ": ").println(emptyList.size());
        asIndenting.increaseIndent();
        try {
            Iterator<E> it = emptyList.iterator();
            while (it.hasNext()) {
                asIndenting.println(it.next());
            }
            asIndenting.decreaseIndent();
            EmptyList emptyList2 = this.unmetDependencies;
            asIndenting.append("unmetDependencies").append((CharSequence) ": ").println(emptyList2.size());
            asIndenting.increaseIndent();
            try {
                Iterator<E> it2 = emptyList2.iterator();
                while (it2.hasNext()) {
                    asIndenting.println(it2.next());
                }
            } finally {
            }
        } finally {
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.handler.getClass();
    }
}
