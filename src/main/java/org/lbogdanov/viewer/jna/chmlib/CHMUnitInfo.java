package org.lbogdanov.viewer.jna.chmlib;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import org.lbogdanov.viewer.jna.JNAEnum;
import org.lbogdanov.viewer.jna.chmlib.CHMLib.Enumerate;

import com.sun.jna.Native;
import com.sun.jna.Structure;

public class CHMUnitInfo extends Structure {
    private static final int MAX_PATH_LEN = 512;

    public enum Space implements JNAEnum {
        UNCOMPRESSED, COMPRESSED
    }

    public long start;
    public long length;
    public int space;
    public int flags;
    public byte[] path = new byte[MAX_PATH_LEN + 1];

    public String getPath() {
        return Native.toString(path);
    }

    public EnumSet<Enumerate> getFlags() {
        EnumSet<Enumerate> result = EnumSet.allOf(Enumerate.class);
        result.removeIf(x -> (flags & x.toInt()) != x.toInt());
        return result;
    }

    public Space getSpace() {
        return Space.values()[space];
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("start", "length", "space", "flags", "path");
    }
}
