package android.content.pm;

import android.os.Parcel;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes.dex */
public class PackageParserCacheHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "PackageParserCacheHelper";

    private PackageParserCacheHelper() {
    }

    public static class ReadHelper extends Parcel.ReadWriteHelper {
        private final Parcel mParcel;
        private final ArrayList<String> mStrings = new ArrayList<>();

        public ReadHelper(Parcel parcel) {
            this.mParcel = parcel;
        }

        public void startAndInstall() {
            this.mStrings.clear();
            int readInt = this.mParcel.readInt();
            if (readInt < 0) {
                throw new IllegalStateException("Invalid string pool position: " + readInt);
            }
            int dataPosition = this.mParcel.dataPosition();
            this.mParcel.setDataPosition(readInt);
            this.mParcel.readStringList(this.mStrings);
            this.mParcel.setDataPosition(dataPosition);
            this.mParcel.setReadWriteHelper(this);
        }

        public String readString(Parcel parcel) {
            return this.mStrings.get(parcel.readInt());
        }

        @Override // android.os.Parcel.ReadWriteHelper
        public String readString8(Parcel parcel) {
            return readString(parcel);
        }

        @Override // android.os.Parcel.ReadWriteHelper
        public String readString16(Parcel parcel) {
            return readString(parcel);
        }
    }

    public static class WriteHelper extends Parcel.ReadWriteHelper {
        private final Parcel mParcel;
        private final int mStartPos;
        private final ArrayList<String> mStrings = new ArrayList<>();
        private final HashMap<String, Integer> mIndexes = new HashMap<>();

        public WriteHelper(Parcel parcel) {
            this.mParcel = parcel;
            this.mStartPos = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.setReadWriteHelper(this);
        }

        public void writeString(Parcel parcel, String str) {
            Integer num = this.mIndexes.get(str);
            if (num != null) {
                parcel.writeInt(num.intValue());
                return;
            }
            int size = this.mStrings.size();
            this.mIndexes.put(str, Integer.valueOf(size));
            this.mStrings.add(str);
            parcel.writeInt(size);
        }

        @Override // android.os.Parcel.ReadWriteHelper
        public void writeString8(Parcel parcel, String str) {
            writeString(parcel, str);
        }

        @Override // android.os.Parcel.ReadWriteHelper
        public void writeString16(Parcel parcel, String str) {
            writeString(parcel, str);
        }

        public void finishAndUninstall() {
            this.mParcel.setReadWriteHelper(null);
            int dataPosition = this.mParcel.dataPosition();
            this.mParcel.writeStringList(this.mStrings);
            this.mParcel.setDataPosition(this.mStartPos);
            this.mParcel.writeInt(dataPosition);
            Parcel parcel = this.mParcel;
            parcel.setDataPosition(parcel.dataSize());
        }
    }
}
