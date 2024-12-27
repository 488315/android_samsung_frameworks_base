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

public class WrapFileClipData implements Serializable {
    private static final String TAG = "WrapFileClipData";
    private static final long serialVersionUID = 1;
    private transient SemClipData mClip;
    private File mDir;
    private File mPath;

    public WrapFileClipData(SemClipData clip) {
        if (clip != null) {
            clip.setPersistableBundle(clip.getClipData().getDescription().getExtras());
            clip.setClipData(null);
        }
        this.mClip = clip;
        this.mPath = FileHelper.getInstance().getNullFile();
    }

    public SemClipData getClipData() {
        if (this.mClip == null) {
            load();
        }
        return this.mClip;
    }

    public void setClipData(SemClipData clip) {
        clip.setClipData(null);
        this.mClip = clip;
    }

    public File getFile() {
        return this.mPath;
    }

    public void setFile(File path) {
        this.mPath = path;
    }

    public File getDir() {
        return this.mDir;
    }

    public void setDir(File dir) {
        this.mDir = dir;
    }

    public void save() {
        if (this.mClip == null) {
            return;
        }
        this.mClip.toSave();
        FileHelper.getInstance().saveObjectFile(this.mPath, this.mClip);
    }

    public boolean load() {
        Object loadedData = loadData();
        if (loadedData == null) {
            return false;
        }
        if (loadedData instanceof SemClipData) {
            return loadSemClipData((SemClipData) loadedData);
        }
        Log.secD(TAG, "While loading data, no matching class found!");
        return false;
    }

    private Object loadData() {
        if (this.mPath != null
                && this.mPath
                        .getAbsolutePath()
                        .contains(CompatabilityHelper.OLD_CLIPBOARD_ROOT_PATH)) {
            this.mPath =
                    new File(
                            CompatabilityHelper.replacePathForCompatability(
                                    this.mPath.getAbsolutePath()));
            this.mDir =
                    new File(
                            CompatabilityHelper.replacePathForCompatability(
                                    this.mDir.getAbsolutePath()));
        }
        return FileHelper.getInstance().loadObjectFile(this.mPath);
    }

    private boolean loadSemClipData(SemClipData data) {
        if (data == null) {
            return false;
        }
        this.mClip = data;
        this.mClip.checkClipId();
        switch (this.mClip.getClipType()) {
            case 1:
                SemTextClipData textData = (SemTextClipData) this.mClip;
                textData.toLoad();
                if (TextUtils.isEmpty(textData.getText())) {
                    Log.secD(TAG, "SemTextClipData is empty. Hence return false");
                    break;
                }
                break;
            case 2:
                SemImageClipData imageData = (SemImageClipData) this.mClip;
                imageData.toLoad();
                File tempFile = new File(imageData.getBitmapPath());
                if (!tempFile.exists()) {
                    Log.secD(TAG, "SemImageClipData is not exist. Hence return false");
                    break;
                }
                break;
            case 4:
                SemHtmlClipData htmlClipData = (SemHtmlClipData) this.mClip;
                htmlClipData.toLoad();
                break;
        }
        return false;
    }
}
