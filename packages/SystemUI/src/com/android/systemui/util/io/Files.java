package com.android.systemui.util.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

public class Files {
    public Stream<String> lines(Path path) throws IOException {
        return java.nio.file.Files.lines(path);
    }

    public BufferedWriter newBufferedWriter(Path path, OpenOption... openOptionArr) throws IOException {
        return java.nio.file.Files.newBufferedWriter(path, StandardCharsets.UTF_8, openOptionArr);
    }

    public <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> cls, LinkOption... linkOptionArr) throws IOException {
        return (A) java.nio.file.Files.readAttributes(path, cls, linkOptionArr);
    }
}
