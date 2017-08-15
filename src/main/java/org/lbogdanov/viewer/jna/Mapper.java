package org.lbogdanov.viewer.jna;

import java.util.Arrays;
import java.util.Set;

import com.sun.jna.*;

public class Mapper extends DefaultTypeMapper {
    public Mapper() {
        addToNativeConverter(Set.class, new ToNativeConverter() {
            @Override
            public Object toNative(Object value, ToNativeContext context) {
                assert value.getClass().getTypeParameters() != null;
                return ((Set<?>) value).stream()
                                       .map(JNAEnum.class::cast)
                                       .mapToInt(JNAEnum::toInt)
                                       .reduce(0, (x, y) -> x | y);
            }

            @Override
            public Class<?> nativeType() {
                return Integer.class;
            }
        });

        addTypeConverter(JNAEnum.class, new TypeConverter() {
            @Override
            public Object toNative(Object value, ToNativeContext context) {
                return ((JNAEnum) value).toInt();
            }

            @Override
            public Class<?> nativeType() {
                return Integer.class;
            }

            @Override
            public Object fromNative(Object nativeValue, FromNativeContext context) {
                return Arrays.stream(context.getTargetType().getEnumConstants())
                             .map(JNAEnum.class::cast)
                             .filter(x -> Integer.valueOf(x.toInt()).equals(nativeValue))
                             .findFirst()
                             .get();
            }
        });
    }
}
