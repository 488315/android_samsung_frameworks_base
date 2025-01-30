package com.airbnb.lottie.animation.content;

import android.graphics.Matrix;
import android.graphics.Path;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.content.MergePaths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MergePathsContent implements PathContent, GreedyContent {
    public final MergePaths mergePaths;
    public final Path firstPath = new Path();
    public final Path remainderPath = new Path();
    public final Path path = new Path();
    public final List pathContents = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.airbnb.lottie.animation.content.MergePathsContent$1 */
    public abstract /* synthetic */ class AbstractC05921 {

        /* renamed from: $SwitchMap$com$airbnb$lottie$model$content$MergePaths$MergePathsMode */
        public static final /* synthetic */ int[] f183x7df623d1;

        static {
            int[] iArr = new int[MergePaths.MergePathsMode.values().length];
            f183x7df623d1 = iArr;
            try {
                iArr[MergePaths.MergePathsMode.MERGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f183x7df623d1[MergePaths.MergePathsMode.ADD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f183x7df623d1[MergePaths.MergePathsMode.SUBTRACT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f183x7df623d1[MergePaths.MergePathsMode.INTERSECT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f183x7df623d1[MergePaths.MergePathsMode.EXCLUDE_INTERSECTIONS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public MergePathsContent(MergePaths mergePaths) {
        mergePaths.getClass();
        this.mergePaths = mergePaths;
    }

    @Override // com.airbnb.lottie.animation.content.GreedyContent
    public final void absorbContent(ListIterator listIterator) {
        while (listIterator.hasPrevious() && listIterator.previous() != this) {
        }
        while (listIterator.hasPrevious()) {
            Content content = (Content) listIterator.previous();
            if (content instanceof PathContent) {
                ((ArrayList) this.pathContents).add((PathContent) content);
                listIterator.remove();
            }
        }
    }

    @Override // com.airbnb.lottie.animation.content.PathContent
    public final Path getPath() {
        Path path = this.path;
        path.reset();
        MergePaths mergePaths = this.mergePaths;
        if (mergePaths.hidden) {
            return path;
        }
        int i = AbstractC05921.f183x7df623d1[mergePaths.mode.ordinal()];
        if (i == 1) {
            int i2 = 0;
            while (true) {
                ArrayList arrayList = (ArrayList) this.pathContents;
                if (i2 >= arrayList.size()) {
                    break;
                }
                path.addPath(((PathContent) arrayList.get(i2)).getPath());
                i2++;
            }
        } else if (i == 2) {
            opFirstPathWithRest(Path.Op.UNION);
        } else if (i == 3) {
            opFirstPathWithRest(Path.Op.REVERSE_DIFFERENCE);
        } else if (i == 4) {
            opFirstPathWithRest(Path.Op.INTERSECT);
        } else if (i == 5) {
            opFirstPathWithRest(Path.Op.XOR);
        }
        return path;
    }

    public final void opFirstPathWithRest(Path.Op op) {
        Matrix matrix;
        Matrix matrix2;
        Path path = this.remainderPath;
        path.reset();
        Path path2 = this.firstPath;
        path2.reset();
        ArrayList arrayList = (ArrayList) this.pathContents;
        for (int size = arrayList.size() - 1; size >= 1; size--) {
            PathContent pathContent = (PathContent) arrayList.get(size);
            if (pathContent instanceof ContentGroup) {
                ContentGroup contentGroup = (ContentGroup) pathContent;
                List pathList = contentGroup.getPathList();
                for (int size2 = pathList.size() - 1; size2 >= 0; size2--) {
                    Path path3 = ((PathContent) pathList.get(size2)).getPath();
                    TransformKeyframeAnimation transformKeyframeAnimation = contentGroup.transformAnimation;
                    if (transformKeyframeAnimation != null) {
                        matrix2 = transformKeyframeAnimation.getMatrix();
                    } else {
                        matrix2 = contentGroup.matrix;
                        matrix2.reset();
                    }
                    path3.transform(matrix2);
                    path.addPath(path3);
                }
            } else {
                path.addPath(pathContent.getPath());
            }
        }
        PathContent pathContent2 = (PathContent) arrayList.get(0);
        if (pathContent2 instanceof ContentGroup) {
            ContentGroup contentGroup2 = (ContentGroup) pathContent2;
            List pathList2 = contentGroup2.getPathList();
            for (int i = 0; i < pathList2.size(); i++) {
                Path path4 = ((PathContent) pathList2.get(i)).getPath();
                TransformKeyframeAnimation transformKeyframeAnimation2 = contentGroup2.transformAnimation;
                if (transformKeyframeAnimation2 != null) {
                    matrix = transformKeyframeAnimation2.getMatrix();
                } else {
                    matrix = contentGroup2.matrix;
                    matrix.reset();
                }
                path4.transform(matrix);
                path2.addPath(path4);
            }
        } else {
            path2.set(pathContent2.getPath());
        }
        this.path.op(path2, path, op);
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final void setContents(List list, List list2) {
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) this.pathContents;
            if (i >= arrayList.size()) {
                return;
            }
            ((PathContent) arrayList.get(i)).setContents(list, list2);
            i++;
        }
    }
}
