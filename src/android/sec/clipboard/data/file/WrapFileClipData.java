package android.sec.clipboard.data.file;

import android.sec.clipboard.util.CompatabilityHelper;
import android.sec.clipboard.util.FileHelper;
import android.sec.clipboard.util.Log;
import android.text.TextUtils;
import com.samsung.android.content.clipboard.data.SemClipData;
import com.samsung.android.content.clipboard.data.SemHtmlClipData;
import com.samsung.android.content.clipboard.data.SemImageClipData;
import com.samsung.android.content.clipboard.data.SemTextClipData;
import java.io.File;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class WrapFileClipData implements Serializable {
    private static final String TAG = "WrapFileClipData";
    private static final long serialVersionUID = 1;
    private transient SemClipData mClip;
    private File mDir;
    private File mPath;

    public WrapFileClipData(SemClipData semClipData) {
        if (semClipData != null) {
            semClipData.setPersistableBundle(semClipData.getClipData().getDescription().getExtras());
            semClipData.setClipData(null);
        }
        this.mClip = semClipData;
        this.mPath = FileHelper.getInstance().getNullFile();
    }

    public SemClipData getClipData() {
        if (this.mClip == null) {
            load();
        }
        return this.mClip;
    }

    public void setClipData(SemClipData semClipData) {
        semClipData.setClipData(null);
        this.mClip = semClipData;
    }

    public File getFile() {
        return this.mPath;
    }

    public void setFile(File file) {
        this.mPath = file;
    }

    public File getDir() {
        return this.mDir;
    }

    public void setDir(File file) {
        this.mDir = file;
    }

    public void save() throws Throwable {
        SemClipData semClipData = this.mClip;
        if (semClipData == null) {
            return;
        }
        semClipData.toSave();
        FileHelper.getInstance().saveObjectFile(this.mPath, this.mClip);
    }

    public boolean load() {
        Object objLoadData = loadData();
        if (objLoadData == null) {
            return false;
        }
        if (objLoadData instanceof SemClipData) {
            return loadSemClipData((SemClipData) objLoadData);
        }
        Log.secD(TAG, "While loading data, no matching class found!");
        return false;
    }

    private Object loadData() {
        File file = this.mPath;
        if (file != null && file.getAbsolutePath().contains(CompatabilityHelper.OLD_CLIPBOARD_ROOT_PATH)) {
            this.mPath = new File(CompatabilityHelper.replacePathForCompatability(this.mPath.getAbsolutePath()));
            this.mDir = new File(CompatabilityHelper.replacePathForCompatability(this.mDir.getAbsolutePath()));
        }
        return FileHelper.getInstance().loadObjectFile(this.mPath);
    }

    private boolean loadSemClipData(SemClipData semClipData) {
        if (semClipData == null) {
            return false;
        }
        this.mClip = semClipData;
        semClipData.checkClipId();
        int clipType = this.mClip.getClipType();
        if (clipType == 1) {
            SemTextClipData semTextClipData = (SemTextClipData) this.mClip;
            semTextClipData.toLoad();
            if (TextUtils.isEmpty(semTextClipData.getText())) {
                Log.secD(TAG, "SemTextClipData is empty. Hence return false");
                return false;
            }
        } else if (clipType == 2) {
            SemImageClipData semImageClipData = (SemImageClipData) this.mClip;
            semImageClipData.toLoad();
            if (!new File(semImageClipData.getBitmapPath()).exists()) {
                Log.secD(TAG, "SemImageClipData is not exist. Hence return false");
                return false;
            }
        } else if (clipType == 4) {
            ((SemHtmlClipData) this.mClip).toLoad();
        }
        return true;
    }
}
