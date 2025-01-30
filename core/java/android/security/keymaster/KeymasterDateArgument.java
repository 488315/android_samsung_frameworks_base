package android.security.keymaster;

import android.p009os.Parcel;
import java.util.Date;

/* loaded from: classes3.dex */
class KeymasterDateArgument extends KeymasterArgument {
    public final Date date;

    public KeymasterDateArgument(int tag, Date date) {
        super(tag);
        switch (KeymasterDefs.getTagType(tag)) {
            case 1610612736:
                this.date = date;
                return;
            default:
                throw new IllegalArgumentException("Bad date tag " + tag);
        }
    }

    public KeymasterDateArgument(int tag, Parcel in) {
        super(tag);
        this.date = new Date(in.readLong());
    }

    @Override // android.security.keymaster.KeymasterArgument
    public void writeValue(Parcel out) {
        out.writeLong(this.date.getTime());
    }
}
