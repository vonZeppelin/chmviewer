package org.lbogdanov.viewer.jna;

public interface JNAEnum {
    default int toInt() {
        assert this instanceof Enum<?>;
        return ((Enum<?>) this).ordinal();
    }
}
