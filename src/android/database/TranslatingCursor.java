package android.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.CancellationSignal;
import android.util.ArraySet;
import com.android.internal.util.ArrayUtils;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes.dex */
public class TranslatingCursor extends CrossProcessCursorWrapper {
    private final int mAuxiliaryColumnIndex;
    private final Config mConfig;
    private final boolean mDropLast;
    private final ArraySet<Integer> mTranslateColumnIndices;
    private final Translator mTranslator;

    public interface Translator {
        String translate(String str, int i, String str2, Cursor cursor);
    }

    @Override // android.database.CrossProcessCursorWrapper, android.database.CrossProcessCursor
    public CursorWindow getWindow() {
        return null;
    }

    public static class Config {
        public final String auxiliaryColumn;
        public final Uri baseUri;
        public final String[] translateColumns;

        public Config(Uri uri, String str, String... strArr) {
            this.baseUri = uri;
            this.auxiliaryColumn = str;
            this.translateColumns = strArr;
        }
    }

    public TranslatingCursor(Cursor cursor, Config config, Translator translator, boolean z) {
        super(cursor);
        this.mConfig = (Config) Objects.requireNonNull(config);
        this.mTranslator = (Translator) Objects.requireNonNull(translator);
        this.mDropLast = z;
        this.mAuxiliaryColumnIndex = cursor.getColumnIndexOrThrow(config.auxiliaryColumn);
        this.mTranslateColumnIndices = new ArraySet<>();
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            if (ArrayUtils.contains(config.translateColumns, cursor.getColumnName(i))) {
                this.mTranslateColumnIndices.add(Integer.valueOf(i));
            }
        }
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getColumnCount() {
        if (this.mDropLast) {
            return super.getColumnCount() - 1;
        }
        return super.getColumnCount();
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public String[] getColumnNames() {
        if (this.mDropLast) {
            return (String[]) Arrays.copyOfRange(super.getColumnNames(), 0, super.getColumnCount() - 1);
        }
        return super.getColumnNames();
    }

    public static Cursor query(Config config, Translator translator, SQLiteQueryBuilder sQLiteQueryBuilder, SQLiteDatabase sQLiteDatabase, String[] strArr, String str, String[] strArr2, String str2, String str3, String str4, String str5, CancellationSignal cancellationSignal) {
        boolean z = ArrayUtils.isEmpty(strArr) || ArrayUtils.contains(strArr, config.auxiliaryColumn);
        if (!ArrayUtils.isEmpty(strArr) && !ArrayUtils.containsAny(strArr, config.translateColumns)) {
            return sQLiteQueryBuilder.query(sQLiteDatabase, strArr, str, strArr2, str2, str3, str4, str5, cancellationSignal);
        }
        if (!z) {
            strArr = (String[]) ArrayUtils.appendElement(String.class, strArr, config.auxiliaryColumn);
        }
        return new TranslatingCursor(sQLiteQueryBuilder.query(sQLiteDatabase, strArr, str, strArr2, str2, str3, str4), config, translator, !z);
    }

    @Override // android.database.CrossProcessCursorWrapper, android.database.CrossProcessCursor
    public void fillWindow(int i, CursorWindow cursorWindow) {
        DatabaseUtils.cursorFillWindow(this, i, cursorWindow);
    }

    @Override // android.database.CursorWrapper
    public Cursor getWrappedCursor() {
        throw new UnsupportedOperationException("Returning underlying cursor risks leaking data");
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public double getDouble(int i) {
        if (ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(i))) {
            throw new IllegalArgumentException();
        }
        return super.getDouble(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public float getFloat(int i) {
        if (ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(i))) {
            throw new IllegalArgumentException();
        }
        return super.getFloat(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getInt(int i) {
        if (ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(i))) {
            throw new IllegalArgumentException();
        }
        return super.getInt(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public long getLong(int i) {
        if (ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(i))) {
            throw new IllegalArgumentException();
        }
        return super.getLong(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public short getShort(int i) {
        if (ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(i))) {
            throw new IllegalArgumentException();
        }
        return super.getShort(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public String getString(int i) {
        if (ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(i))) {
            return this.mTranslator.translate(super.getString(i), this.mAuxiliaryColumnIndex, getColumnName(i), this);
        }
        return super.getString(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {
        if (ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(i))) {
            throw new IllegalArgumentException();
        }
        super.copyStringToBuffer(i, charArrayBuffer);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public byte[] getBlob(int i) {
        if (ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(i))) {
            throw new IllegalArgumentException();
        }
        return super.getBlob(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public int getType(int i) {
        if (ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(i))) {
            return 3;
        }
        return super.getType(i);
    }

    @Override // android.database.CursorWrapper, android.database.Cursor
    public boolean isNull(int i) {
        if (ArrayUtils.contains(this.mTranslateColumnIndices, Integer.valueOf(i))) {
            return getString(i) == null;
        }
        return super.isNull(i);
    }
}
