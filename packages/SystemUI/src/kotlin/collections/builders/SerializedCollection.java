package kotlin.collections.builders;

import java.io.Externalizable;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;
import java.util.Iterator;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SerializedCollection implements Externalizable {
    private static final long serialVersionUID = 0;
    private Collection<?> collection;
    private final int tag;

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

    public SerializedCollection() {
        this(EmptyList.INSTANCE, 0);
    }

    private final Object readResolve() {
        return this.collection;
    }

    @Override // java.io.Externalizable
    public final void readExternal(ObjectInput objectInput) {
        Collection<?> build;
        byte readByte = objectInput.readByte();
        int i = readByte & 1;
        if ((readByte & (-2)) != 0) {
            throw new InvalidObjectException("Unsupported flags value: " + ((int) readByte) + '.');
        }
        int readInt = objectInput.readInt();
        if (readInt < 0) {
            throw new InvalidObjectException("Illegal size value: " + readInt + '.');
        }
        int i2 = 0;
        if (i == 0) {
            ListBuilder listBuilder = new ListBuilder(readInt);
            while (i2 < readInt) {
                listBuilder.add(objectInput.readObject());
                i2++;
            }
            build = listBuilder.build();
        } else {
            if (i != 1) {
                throw new InvalidObjectException("Unsupported collection type tag: " + i + '.');
            }
            SetBuilder setBuilder = new SetBuilder(readInt);
            while (i2 < readInt) {
                setBuilder.add(objectInput.readObject());
                i2++;
            }
            build = setBuilder.build();
        }
        this.collection = build;
    }

    @Override // java.io.Externalizable
    public final void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeByte(this.tag);
        objectOutput.writeInt(this.collection.size());
        Iterator<?> it = this.collection.iterator();
        while (it.hasNext()) {
            objectOutput.writeObject(it.next());
        }
    }

    public SerializedCollection(Collection<?> collection, int i) {
        this.collection = collection;
        this.tag = i;
    }
}
