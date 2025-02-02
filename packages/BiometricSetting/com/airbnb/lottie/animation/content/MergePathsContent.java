package com.airbnb.lottie.animation.content;

import android.annotation.TargetApi;
import android.graphics.Path;
import com.airbnb.lottie.model.content.MergePaths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@TargetApi(19)
/* loaded from: classes.dex */
public final class MergePathsContent implements PathContent, GreedyContent {
    private final MergePaths mergePaths;
    private final Path firstPath = new Path();
    private final Path remainderPath = new Path();
    private final Path path = new Path();
    private final List<PathContent> pathContents = new ArrayList();

    public MergePathsContent(MergePaths mergePaths) {
        mergePaths.getClass();
        this.mergePaths = mergePaths;
    }

    @TargetApi(19)
    private void opFirstPathWithRest(Path.Op op) {
        Path path = this.remainderPath;
        path.reset();
        Path path2 = this.firstPath;
        path2.reset();
        List<PathContent> list = this.pathContents;
        for (int size = ((ArrayList) list).size() - 1; size >= 1; size--) {
            PathContent pathContent = (PathContent) ((ArrayList) list).get(size);
            if (pathContent instanceof ContentGroup) {
                ContentGroup contentGroup = (ContentGroup) pathContent;
                List<PathContent> pathList = contentGroup.getPathList();
                for (int size2 = pathList.size() - 1; size2 >= 0; size2--) {
                    Path path3 = pathList.get(size2).getPath();
                    path3.transform(contentGroup.getTransformationMatrix());
                    path.addPath(path3);
                }
            } else {
                path.addPath(pathContent.getPath());
            }
        }
        PathContent pathContent2 = (PathContent) ((ArrayList) list).get(0);
        if (pathContent2 instanceof ContentGroup) {
            ContentGroup contentGroup2 = (ContentGroup) pathContent2;
            List<PathContent> pathList2 = contentGroup2.getPathList();
            for (int i = 0; i < pathList2.size(); i++) {
                Path path4 = pathList2.get(i).getPath();
                path4.transform(contentGroup2.getTransformationMatrix());
                path2.addPath(path4);
            }
        } else {
            path2.set(pathContent2.getPath());
        }
        this.path.op(path2, path, op);
    }

    @Override // com.airbnb.lottie.animation.content.GreedyContent
    public final void absorbContent(ListIterator<Content> listIterator) {
        while (listIterator.hasPrevious() && listIterator.previous() != this) {
        }
        while (listIterator.hasPrevious()) {
            Content previous = listIterator.previous();
            if (previous instanceof PathContent) {
                ((ArrayList) this.pathContents).add((PathContent) previous);
                listIterator.remove();
            }
        }
    }

    @Override // com.airbnb.lottie.animation.content.PathContent
    public final Path getPath() {
        Path path = this.path;
        path.reset();
        MergePaths mergePaths = this.mergePaths;
        if (mergePaths.isHidden()) {
            return path;
        }
        int ordinal = mergePaths.getMode().ordinal();
        if (ordinal == 0) {
            int i = 0;
            while (true) {
                ArrayList arrayList = (ArrayList) this.pathContents;
                if (i >= arrayList.size()) {
                    break;
                }
                path.addPath(((PathContent) arrayList.get(i)).getPath());
                i++;
            }
        } else if (ordinal == 1) {
            opFirstPathWithRest(Path.Op.UNION);
        } else if (ordinal == 2) {
            opFirstPathWithRest(Path.Op.REVERSE_DIFFERENCE);
        } else if (ordinal == 3) {
            opFirstPathWithRest(Path.Op.INTERSECT);
        } else if (ordinal == 4) {
            opFirstPathWithRest(Path.Op.XOR);
        }
        return path;
    }

    @Override // com.airbnb.lottie.animation.content.Content
    public final void setContents(List<Content> list, List<Content> list2) {
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
