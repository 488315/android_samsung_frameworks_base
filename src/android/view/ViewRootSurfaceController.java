package android.view;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.WindowManager;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public class ViewRootSurfaceController {
    private static String TAG = "ViewRootSurfaceController";
    final ArrayList<Integer> mAllowList;
    private String mInstanceTag;
    private ViewRootImpl mViewRoot;
    private boolean mSkipScreenshot = false;
    private boolean mDisableSuperHdr = false;
    final HashMap<Integer, Integer> mMetaDataMaps = new HashMap<>();

    public ViewRootSurfaceController(ViewRootImpl viewRootImpl) {
        this.mViewRoot = null;
        ArrayList<Integer> arrayList = new ArrayList<>();
        this.mAllowList = arrayList;
        this.mViewRoot = viewRootImpl;
        this.mInstanceTag = viewRootImpl.getTag();
        arrayList.add(32);
    }

    boolean canApplyMetaData(int i) {
        return this.mAllowList.contains(Integer.valueOf(i)) && (this.mViewRoot.mWindowAttributes.samsungFlags & Integer.MIN_VALUE) == 0;
    }

    public void setMetaData(SurfaceControl.Transaction transaction, int i, boolean z) {
        if (canApplyMetaData(i)) {
            SurfaceControl surfaceControl = this.mViewRoot.getSurfaceControl();
            this.mMetaDataMaps.put(Integer.valueOf(i), Integer.valueOf(z ? 1 : 0));
            transaction.setMetadata(surfaceControl, i, z ? 1 : 0).apply();
        }
    }

    public void updateAllMetaData(SurfaceControl.Transaction transaction) {
        Iterator<Map.Entry<Integer, Integer>> it = this.mMetaDataMaps.entrySet().iterator();
        while (it.hasNext()) {
            Integer key = it.next().getKey();
            int intValue = key.intValue();
            if (canApplyMetaData(intValue)) {
                transaction.setMetadata(this.mViewRoot.getSurfaceControl(), intValue, this.mMetaDataMaps.get(key).intValue());
            }
        }
        transaction.apply();
    }

    public void update(SurfaceControl.Transaction transaction) {
        if (this.mSkipScreenshot) {
            updateSkipScreenshot(transaction);
        }
        if (this.mDisableSuperHdr) {
            updateDisableSuperHdr(transaction);
        }
        updateAllMetaData(transaction);
    }

    public void setSkipScreenshot(SurfaceControl.Transaction transaction, boolean z) {
        SurfaceControl surfaceControl = this.mViewRoot.getSurfaceControl();
        if (z == this.mSkipScreenshot || !surfaceControl.isValid()) {
            return;
        }
        this.mSkipScreenshot = z;
        updateSkipScreenshot(transaction);
    }

    private void updateSkipScreenshot(SurfaceControl.Transaction transaction) {
        WindowManager.LayoutParams layoutParams = this.mViewRoot.mWindowAttributes;
        SurfaceControl surfaceControl = this.mViewRoot.getSurfaceControl();
        if (layoutParams.type < 2000 || (layoutParams.privateFlags & 1048576) != 0) {
            Log.e(TAG, NavigationBarInflaterView.SIZE_MOD_START + this.mInstanceTag + "]: updateSkipScreenshot not allowed on this window");
            return;
        }
        transaction.setSkipScreenshot(surfaceControl, this.mSkipScreenshot).apply();
    }

    public void setDisableSuperHdr(SurfaceControl.Transaction transaction, boolean z) {
        SurfaceControl surfaceControl = this.mViewRoot.getSurfaceControl();
        if (z == this.mDisableSuperHdr || !surfaceControl.isValid()) {
            return;
        }
        this.mDisableSuperHdr = z;
        updateDisableSuperHdr(transaction);
    }

    private void updateDisableSuperHdr(SurfaceControl.Transaction transaction) {
        transaction.setDisableSuperHDR(this.mViewRoot.getSurfaceControl(), this.mDisableSuperHdr).apply();
    }

    public void dump(String str, PrintWriter printWriter) {
        String str2 = str + "  ";
        printWriter.println(str + "ViewRootSurfaceController:");
        printWriter.println(str2 + "mSkipScreenshot=" + this.mSkipScreenshot);
        printWriter.println(str2 + "mDisableSuperHdr=" + this.mDisableSuperHdr);
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append("mMetaDataMaps=");
        printWriter.print(sb.toString());
        if (this.mMetaDataMaps.size() > 0) {
            for (Map.Entry<Integer, Integer> entry : this.mMetaDataMaps.entrySet()) {
                printWriter.print(" [" + entry.getKey() + ", " + entry.getValue() + NavigationBarInflaterView.SIZE_MOD_END);
            }
        } else {
            printWriter.print(" <empty>");
        }
        printWriter.println();
        printWriter.print(str2 + "mAllowList={");
        for (int i = 0; i < this.mAllowList.size(); i++) {
            printWriter.print(" " + this.mAllowList.get(i));
        }
        printWriter.println("}");
    }
}
