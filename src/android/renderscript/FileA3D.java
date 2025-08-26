package android.renderscript;

import android.content.res.AssetManager;
import android.content.res.Resources;
import java.io.File;
import java.io.InputStream;

@Deprecated
/* loaded from: classes3.dex */
public class FileA3D extends BaseObj {
    IndexEntry[] mFileEntries;
    InputStream mInputStream;

    public enum EntryType {
        UNKNOWN(0),
        MESH(1);

        int mID;

        EntryType(int i) {
            this.mID = i;
        }

        static EntryType toEntryType(int i) {
            return values()[i];
        }
    }

    public static class IndexEntry {
        EntryType mEntryType;
        long mID;
        int mIndex;
        BaseObj mLoadedObj = null;
        String mName;
        RenderScript mRS;

        public String getName() {
            return this.mName;
        }

        public EntryType getEntryType() {
            return this.mEntryType;
        }

        public BaseObj getObject() {
            this.mRS.validate();
            return internalCreate(this.mRS, this);
        }

        public Mesh getMesh() {
            return (Mesh) getObject();
        }

        static synchronized BaseObj internalCreate(RenderScript renderScript, IndexEntry indexEntry) {
            BaseObj baseObj = indexEntry.mLoadedObj;
            if (baseObj != null) {
                return baseObj;
            }
            if (indexEntry.mEntryType == EntryType.UNKNOWN) {
                return null;
            }
            long jNFileA3DGetEntryByIndex = renderScript.nFileA3DGetEntryByIndex(indexEntry.mID, indexEntry.mIndex);
            if (jNFileA3DGetEntryByIndex == 0) {
                return null;
            }
            if (indexEntry.mEntryType.ordinal() == 1) {
                Mesh mesh = new Mesh(jNFileA3DGetEntryByIndex, renderScript);
                indexEntry.mLoadedObj = mesh;
                mesh.updateFromNative();
                return indexEntry.mLoadedObj;
            }
            throw new RSRuntimeException("Unrecognized object type in file.");
        }

        IndexEntry(RenderScript renderScript, int i, long j, String str, EntryType entryType) {
            this.mRS = renderScript;
            this.mIndex = i;
            this.mID = j;
            this.mName = str;
            this.mEntryType = entryType;
        }
    }

    FileA3D(long j, RenderScript renderScript, InputStream inputStream) {
        super(j, renderScript);
        this.mInputStream = inputStream;
        this.guard.open("destroy");
    }

    private void initEntries() throws Throwable {
        int iNFileA3DGetNumIndexEntries = this.mRS.nFileA3DGetNumIndexEntries(getID(this.mRS));
        if (iNFileA3DGetNumIndexEntries <= 0) {
            return;
        }
        this.mFileEntries = new IndexEntry[iNFileA3DGetNumIndexEntries];
        int[] iArr = new int[iNFileA3DGetNumIndexEntries];
        String[] strArr = new String[iNFileA3DGetNumIndexEntries];
        this.mRS.nFileA3DGetIndexEntries(getID(this.mRS), iNFileA3DGetNumIndexEntries, iArr, strArr);
        for (int i = 0; i < iNFileA3DGetNumIndexEntries; i++) {
            this.mFileEntries[i] = new IndexEntry(this.mRS, i, getID(this.mRS), strArr[i], EntryType.toEntryType(iArr[i]));
        }
    }

    public int getIndexEntryCount() {
        IndexEntry[] indexEntryArr = this.mFileEntries;
        if (indexEntryArr == null) {
            return 0;
        }
        return indexEntryArr.length;
    }

    public IndexEntry getIndexEntry(int i) {
        if (getIndexEntryCount() == 0 || i < 0) {
            return null;
        }
        IndexEntry[] indexEntryArr = this.mFileEntries;
        if (i >= indexEntryArr.length) {
            return null;
        }
        return indexEntryArr[i];
    }

    public static FileA3D createFromAsset(RenderScript renderScript, AssetManager assetManager, String str) throws Throwable {
        renderScript.validate();
        long jNFileA3DCreateFromAsset = renderScript.nFileA3DCreateFromAsset(assetManager, str);
        if (jNFileA3DCreateFromAsset == 0) {
            throw new RSRuntimeException("Unable to create a3d file from asset " + str);
        }
        FileA3D fileA3D = new FileA3D(jNFileA3DCreateFromAsset, renderScript, null);
        fileA3D.initEntries();
        return fileA3D;
    }

    public static FileA3D createFromFile(RenderScript renderScript, String str) throws Throwable {
        long jNFileA3DCreateFromFile = renderScript.nFileA3DCreateFromFile(str);
        if (jNFileA3DCreateFromFile == 0) {
            throw new RSRuntimeException("Unable to create a3d file from " + str);
        }
        FileA3D fileA3D = new FileA3D(jNFileA3DCreateFromFile, renderScript, null);
        fileA3D.initEntries();
        return fileA3D;
    }

    public static FileA3D createFromFile(RenderScript renderScript, File file) {
        return createFromFile(renderScript, file.getAbsolutePath());
    }

    public static FileA3D createFromResource(RenderScript renderScript, Resources resources, int i) throws Throwable {
        renderScript.validate();
        try {
            InputStream inputStreamOpenRawResource = resources.openRawResource(i);
            if (inputStreamOpenRawResource instanceof AssetManager.AssetInputStream) {
                long jNFileA3DCreateFromAssetStream = renderScript.nFileA3DCreateFromAssetStream(((AssetManager.AssetInputStream) inputStreamOpenRawResource).getNativeAsset());
                if (jNFileA3DCreateFromAssetStream == 0) {
                    throw new RSRuntimeException("Unable to create a3d file from resource " + i);
                }
                FileA3D fileA3D = new FileA3D(jNFileA3DCreateFromAssetStream, renderScript, inputStreamOpenRawResource);
                fileA3D.initEntries();
                return fileA3D;
            }
            throw new RSRuntimeException("Unsupported asset stream");
        } catch (Exception unused) {
            throw new RSRuntimeException("Unable to open resource " + i);
        }
    }
}
