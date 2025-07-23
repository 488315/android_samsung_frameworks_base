package android.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import java.util.List;

/* loaded from: classes.dex */
public class CursorWrapper implements Cursor {
    protected final Cursor mCursor;

    public CursorWrapper(Cursor cursor) {
        this.mCursor = cursor;
    }

    public Cursor getWrappedCursor() {
        return this.mCursor;
    }

    @Override // android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.mCursor.close();
    }

    @Override // android.database.Cursor
    public boolean isClosed() {
        return this.mCursor.isClosed();
    }

    @Override // android.database.Cursor
    public int getCount() {
        return this.mCursor.getCount();
    }

    @Override // android.database.Cursor
    @Deprecated
    public void deactivate() {
        this.mCursor.deactivate();
    }

    @Override // android.database.Cursor
    public boolean moveToFirst() {
        return this.mCursor.moveToFirst();
    }

    @Override // android.database.Cursor
    public int getColumnCount() {
        return this.mCursor.getColumnCount();
    }

    @Override // android.database.Cursor
    public int getColumnIndex(String str) {
        return this.mCursor.getColumnIndex(str);
    }

    @Override // android.database.Cursor
    public int getColumnIndexOrThrow(String str) throws IllegalArgumentException {
        return this.mCursor.getColumnIndexOrThrow(str);
    }

    @Override // android.database.Cursor
    public String getColumnName(int i) {
        return this.mCursor.getColumnName(i);
    }

    @Override // android.database.Cursor
    public String[] getColumnNames() {
        return this.mCursor.getColumnNames();
    }

    @Override // android.database.Cursor
    public double getDouble(int i) {
        return this.mCursor.getDouble(i);
    }

    @Override // android.database.Cursor
    public void setExtras(Bundle bundle) {
        this.mCursor.setExtras(bundle);
    }

    @Override // android.database.Cursor
    public Bundle getExtras() {
        return this.mCursor.getExtras();
    }

    @Override // android.database.Cursor
    public float getFloat(int i) {
        return this.mCursor.getFloat(i);
    }

    @Override // android.database.Cursor
    public int getInt(int i) {
        return this.mCursor.getInt(i);
    }

    @Override // android.database.Cursor
    public long getLong(int i) {
        return this.mCursor.getLong(i);
    }

    @Override // android.database.Cursor
    public short getShort(int i) {
        return this.mCursor.getShort(i);
    }

    @Override // android.database.Cursor
    public String getString(int i) {
        return this.mCursor.getString(i);
    }

    @Override // android.database.Cursor
    public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {
        this.mCursor.copyStringToBuffer(i, charArrayBuffer);
    }

    @Override // android.database.Cursor
    public byte[] getBlob(int i) {
        return this.mCursor.getBlob(i);
    }

    @Override // android.database.Cursor
    public boolean getWantsAllOnMoveCalls() {
        return this.mCursor.getWantsAllOnMoveCalls();
    }

    @Override // android.database.Cursor
    public boolean isAfterLast() {
        return this.mCursor.isAfterLast();
    }

    @Override // android.database.Cursor
    public boolean isBeforeFirst() {
        return this.mCursor.isBeforeFirst();
    }

    @Override // android.database.Cursor
    public boolean isFirst() {
        return this.mCursor.isFirst();
    }

    @Override // android.database.Cursor
    public boolean isLast() {
        return this.mCursor.isLast();
    }

    @Override // android.database.Cursor
    public int getType(int i) {
        return this.mCursor.getType(i);
    }

    @Override // android.database.Cursor
    public boolean isNull(int i) {
        return this.mCursor.isNull(i);
    }

    @Override // android.database.Cursor
    public boolean moveToLast() {
        return this.mCursor.moveToLast();
    }

    @Override // android.database.Cursor
    public boolean move(int i) {
        return this.mCursor.move(i);
    }

    @Override // android.database.Cursor
    public boolean moveToPosition(int i) {
        return this.mCursor.moveToPosition(i);
    }

    @Override // android.database.Cursor
    public boolean moveToNext() {
        return this.mCursor.moveToNext();
    }

    @Override // android.database.Cursor
    public int getPosition() {
        return this.mCursor.getPosition();
    }

    @Override // android.database.Cursor
    public boolean moveToPrevious() {
        return this.mCursor.moveToPrevious();
    }

    @Override // android.database.Cursor
    public void registerContentObserver(ContentObserver contentObserver) {
        this.mCursor.registerContentObserver(contentObserver);
    }

    @Override // android.database.Cursor
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.mCursor.registerDataSetObserver(dataSetObserver);
    }

    @Override // android.database.Cursor
    @Deprecated
    public boolean requery() {
        return this.mCursor.requery();
    }

    @Override // android.database.Cursor
    public Bundle respond(Bundle bundle) {
        return this.mCursor.respond(bundle);
    }

    @Override // android.database.Cursor
    public void setNotificationUri(ContentResolver contentResolver, Uri uri) {
        this.mCursor.setNotificationUri(contentResolver, uri);
    }

    @Override // android.database.Cursor
    public void setNotificationUris(ContentResolver contentResolver, List<Uri> list) {
        this.mCursor.setNotificationUris(contentResolver, list);
    }

    @Override // android.database.Cursor
    public Uri getNotificationUri() {
        return this.mCursor.getNotificationUri();
    }

    @Override // android.database.Cursor
    public List<Uri> getNotificationUris() {
        return this.mCursor.getNotificationUris();
    }

    @Override // android.database.Cursor
    public void unregisterContentObserver(ContentObserver contentObserver) {
        this.mCursor.unregisterContentObserver(contentObserver);
    }

    @Override // android.database.Cursor
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.mCursor.unregisterDataSetObserver(dataSetObserver);
    }
}
