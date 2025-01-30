package kotlin.collections.builders;

import android.support.v4.media.AbstractC0000x2c234b15;
import java.io.Externalizable;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
final class SerializedMap implements Externalizable {
    private static final long serialVersionUID = 0;
    private Map<?, ?> map;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public SerializedMap(Map<?, ?> map) {
        this.map = map;
    }

    private final Object readResolve() {
        return this.map;
    }

    @Override // java.io.Externalizable
    public final void readExternal(ObjectInput objectInput) {
        byte readByte = objectInput.readByte();
        if (readByte != 0) {
            throw new InvalidObjectException(AbstractC0000x2c234b15.m0m("Unsupported flags value: ", readByte));
        }
        int readInt = objectInput.readInt();
        if (readInt < 0) {
            throw new InvalidObjectException("Illegal size value: " + readInt + '.');
        }
        MapBuilder mapBuilder = new MapBuilder(readInt);
        for (int i = 0; i < readInt; i++) {
            mapBuilder.put(objectInput.readObject(), objectInput.readObject());
        }
        mapBuilder.build$1();
        this.map = mapBuilder;
    }

    @Override // java.io.Externalizable
    public final void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeByte(0);
        objectOutput.writeInt(this.map.size());
        for (Map.Entry<?, ?> entry : this.map.entrySet()) {
            objectOutput.writeObject(entry.getKey());
            objectOutput.writeObject(entry.getValue());
        }
    }

    public SerializedMap() {
        this(MapsKt__MapsKt.emptyMap());
    }
}
