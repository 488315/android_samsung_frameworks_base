package android.content.pm.overlay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class OverlayPaths {
    private final List<String> mOverlayPaths;
    private final List<String> mResourceDirs;

    @Deprecated
    private void __metadata() {
    }

    public static class Builder {
        final OverlayPaths mPaths;

        public Builder() {
            this.mPaths = new OverlayPaths();
        }

        public Builder(OverlayPaths overlayPaths) {
            OverlayPaths overlayPaths2 = new OverlayPaths();
            this.mPaths = overlayPaths2;
            overlayPaths2.mResourceDirs.addAll(overlayPaths.getResourceDirs());
            overlayPaths2.mOverlayPaths.addAll(overlayPaths.getOverlayPaths());
        }

        public Builder addNonApkPath(String str) {
            this.mPaths.mOverlayPaths.add(str);
            return this;
        }

        public Builder addApkPath(String str) {
            addUniquePath(this.mPaths.mResourceDirs, str);
            addUniquePath(this.mPaths.mOverlayPaths, str);
            return this;
        }

        public Builder addAll(OverlayPaths overlayPaths) {
            if (overlayPaths != null) {
                Iterator<String> it = overlayPaths.getResourceDirs().iterator();
                while (it.hasNext()) {
                    addUniquePath(this.mPaths.mResourceDirs, it.next());
                }
                Iterator<String> it2 = overlayPaths.getOverlayPaths().iterator();
                while (it2.hasNext()) {
                    addUniquePath(this.mPaths.mOverlayPaths, it2.next());
                }
            }
            return this;
        }

        public OverlayPaths build() {
            return this.mPaths;
        }

        private static void addUniquePath(List<String> list, String str) {
            if (list.contains(str)) {
                return;
            }
            list.add(str);
        }
    }

    public boolean isEmpty() {
        return this.mResourceDirs.isEmpty() && this.mOverlayPaths.isEmpty();
    }

    private OverlayPaths() {
        this.mResourceDirs = new ArrayList();
        this.mOverlayPaths = new ArrayList();
    }

    public List<String> getResourceDirs() {
        return this.mResourceDirs;
    }

    public List<String> getOverlayPaths() {
        return this.mOverlayPaths;
    }

    public String toString() {
        return "OverlayPaths { resourceDirs = " + this.mResourceDirs + ", overlayPaths = " + this.mOverlayPaths + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            OverlayPaths overlayPaths = (OverlayPaths) obj;
            if (Objects.equals(this.mResourceDirs, overlayPaths.mResourceDirs) && Objects.equals(this.mOverlayPaths, overlayPaths.mOverlayPaths)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((Objects.hashCode(this.mResourceDirs) + 31) * 31) + Objects.hashCode(this.mOverlayPaths);
    }
}
