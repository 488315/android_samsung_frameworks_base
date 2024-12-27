package android.view;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class ViewRootSurfaceController {
    private static String TAG = "ViewRootSurfaceController";
    private String mInstanceTag;
    private ViewRootImpl mViewRoot;
    private boolean mSkipScreenshot = false;
    private boolean mDisableSuperHdr = false;
    final HashMap<Integer, Integer> mMetaDataMaps = new HashMap<>();
    final ArrayList<Integer> mAllowList = new ArrayList<>();

    public ViewRootSurfaceController(ViewRootImpl viewRoot) {
        this.mViewRoot = null;
        this.mViewRoot = viewRoot;
        this.mInstanceTag = this.mViewRoot.getTag();
        this.mAllowList.add(32);
    }

    boolean canApplyMetaData(int metaData) {
        if (!this.mAllowList.contains(Integer.valueOf(metaData))) {
            return false;
        }
        WindowManager.LayoutParams lp = this.mViewRoot.mWindowAttributes;
        return (lp.samsungFlags & Integer.MIN_VALUE) == 0;
    }

    public void setMetaData(SurfaceControl.Transaction transaction, int i, boolean z) {
        if (canApplyMetaData(i)) {
            SurfaceControl surfaceControl = this.mViewRoot.getSurfaceControl();
            this.mMetaDataMaps.put(Integer.valueOf(i), Integer.valueOf(z ? 1 : 0));
            transaction.setMetadata(surfaceControl, i, z ? 1 : 0).apply();
        }
    }

    public void updateAllMetaData(SurfaceControl.Transaction t) {
        for (Map.Entry<Integer, Integer> entry : this.mMetaDataMaps.entrySet()) {
            int metaData = entry.getKey().intValue();
            if (canApplyMetaData(metaData)) {
                t.setMetadata(
                        this.mViewRoot.getSurfaceControl(),
                        metaData,
                        this.mMetaDataMaps.get(Integer.valueOf(metaData)).intValue());
            }
        }
        t.apply();
    }

    public void update(SurfaceControl.Transaction t) {
        if (this.mSkipScreenshot) {
            updateSkipScreenshot(t);
        }
        if (this.mDisableSuperHdr) {
            updateDisableSuperHdr(t);
        }
        updateAllMetaData(t);
    }

    public void setSkipScreenshot(SurfaceControl.Transaction t, boolean skipScreenshot) {
        SurfaceControl sc = this.mViewRoot.getSurfaceControl();
        if (skipScreenshot == this.mSkipScreenshot || !sc.isValid()) {
            return;
        }
        this.mSkipScreenshot = skipScreenshot;
        updateSkipScreenshot(t);
    }

    private void updateSkipScreenshot(SurfaceControl.Transaction t) {
        WindowManager.LayoutParams lp = this.mViewRoot.mWindowAttributes;
        SurfaceControl sc = this.mViewRoot.getSurfaceControl();
        if (lp.type < 2000 || (lp.privateFlags & 1048576) != 0) {
            Log.e(
                    TAG,
                    NavigationBarInflaterView.SIZE_MOD_START
                            + this.mInstanceTag
                            + "]: updateSkipScreenshot not allowed on this window");
        } else {
            t.setSkipScreenshot(sc, this.mSkipScreenshot).apply();
        }
    }

    public void setDisableSuperHdr(SurfaceControl.Transaction t, boolean disableSuperHdr) {
        SurfaceControl sc = this.mViewRoot.getSurfaceControl();
        if (disableSuperHdr == this.mDisableSuperHdr || !sc.isValid()) {
            return;
        }
        this.mDisableSuperHdr = disableSuperHdr;
        updateDisableSuperHdr(t);
    }

    private void updateDisableSuperHdr(SurfaceControl.Transaction t) {
        SurfaceControl sc = this.mViewRoot.getSurfaceControl();
        t.setDisableSuperHDR(sc, this.mDisableSuperHdr).apply();
    }

    public void dump(String prefix, PrintWriter writer) {
        String innerPrefix = prefix + "  ";
        writer.println(prefix + "ViewRootSurfaceController:");
        writer.println(innerPrefix + "mSkipScreenshot=" + this.mSkipScreenshot);
        writer.println(innerPrefix + "mDisableSuperHdr=" + this.mDisableSuperHdr);
        writer.print(innerPrefix + "mMetaDataMaps=");
        if (this.mMetaDataMaps.size() > 0) {
            for (Map.Entry<Integer, Integer> entry : this.mMetaDataMaps.entrySet()) {
                writer.print(
                        " ["
                                + entry.getKey()
                                + ", "
                                + entry.getValue()
                                + NavigationBarInflaterView.SIZE_MOD_END);
            }
        } else {
            writer.print(" <empty>");
        }
        writer.println();
        writer.print(innerPrefix + "mAllowList={");
        for (int i = 0; i < this.mAllowList.size(); i++) {
            writer.print(" " + this.mAllowList.get(i));
        }
        writer.println("}");
    }
}
