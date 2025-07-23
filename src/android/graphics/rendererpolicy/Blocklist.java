package android.graphics.rendererpolicy;

import java.util.List;

/* loaded from: classes.dex */
public class Blocklist {
    private final List<BlockItem> items;

    public Blocklist(List<BlockItem> list) {
        this.items = list;
    }

    public List<BlockItem> getItems() {
        return this.items;
    }

    public String toString() {
        List<BlockItem> list = this.items;
        return list != null ? list.toString() : "";
    }
}
