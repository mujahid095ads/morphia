/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.morphia.mapping.codec.pojo;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.DecoderContext;
import dev.morphia.mapping.codec.EncoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecConfigurationException;
import dev.morphia.mapping.codec.MorphiaCodec;

import static java.lang.String.format;

final class AutomaticPojoCodec<T> implements MorphiaCodec<T> {
    private final MorphiaCodec<T> morphiaCodec;

    AutomaticPojoCodec(final MorphiaCodec<T> morphiaCodec) {
        this.morphiaCodec = morphiaCodec;
    }

    @Override
    public T decode(final BsonReader reader, final DecoderContext decoderContext) {
        try {
            return morphiaCodec.decode(reader, decoderContext);
        } catch (CodecConfigurationException e) {
            throw new CodecConfigurationException(
                    format("An exception occurred when decoding using the AutomaticPojoCodec.%n"
                            + "Decoding into a '%s' failed with the following exception:%n%n%s%n%n"
                            + "A custom Codec or MorphiaCodec may need to be explicitly configured and registered to handle this type.",
                            morphiaCodec.getEncoderClass().getSimpleName(), e.getMessage()), e);
        }
    }

    @Override
    public void encode(final BsonWriter writer, final T value, final EncoderContext encoderContext) {
        try {
            morphiaCodec.encode(writer, value, encoderContext);
        } catch (CodecConfigurationException e) {
            throw new CodecConfigurationException(
                    format("An exception occurred when encoding using the AutomaticPojoCodec.%n"
                            + "Encoding a %s: '%s' failed with the following exception:%n%n%s%n%n"
                            + "A custom Codec or MorphiaCodec may need to be explicitly configured and registered to handle this type.",
                            getEncoderClass().getSimpleName(), value, e.getMessage()), e);
        }
    }

    @Override
    public Class<T> getEncoderClass() {
        return morphiaCodec.getEncoderClass();
    }

    @Override
    public dev.morphia.mapping.codec.pojo.ClassModel<T> getClassModel() {
        return morphiaCodec.getClassModel();
    }
}
