package android.graphics.rendererpolicy;

import java.util.List;

/* loaded from: classes.dex */
public class Blocklist {
    private final List<BlockItem> items;

    public Blocklist(List<BlockItem> items) {
        this.items = items;
    }

    public List<BlockItem> getItems() {
        return this.items;
    }

    public String toString() {
        return this.items != null ? this.items.toString() : "";
    }
}
