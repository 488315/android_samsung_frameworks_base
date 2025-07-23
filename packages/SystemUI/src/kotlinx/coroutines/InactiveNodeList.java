package kotlinx.coroutines;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class InactiveNodeList implements Incomplete {
    public final NodeList list;

    public InactiveNodeList(NodeList nodeList) {
        this.list = nodeList;
    }

    @Override // kotlinx.coroutines.Incomplete
    public final NodeList getList() {
        return this.list;
    }

    @Override // kotlinx.coroutines.Incomplete
    public final boolean isActive() {
        return false;
    }
}
