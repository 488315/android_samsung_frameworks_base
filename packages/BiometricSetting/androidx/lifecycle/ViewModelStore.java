package androidx.lifecycle;

import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class ViewModelStore {
    private final HashMap<String, ViewModel> mMap = new HashMap<>();

    public final void clear() {
        HashMap<String, ViewModel> hashMap = this.mMap;
        Iterator<ViewModel> it = hashMap.values().iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
        hashMap.clear();
    }

    final ViewModel get(String str) {
        return this.mMap.get(str);
    }

    final void put(String str, ViewModel viewModel) {
        ViewModel put = this.mMap.put(str, viewModel);
        if (put != null) {
            put.onCleared();
        }
    }
}
